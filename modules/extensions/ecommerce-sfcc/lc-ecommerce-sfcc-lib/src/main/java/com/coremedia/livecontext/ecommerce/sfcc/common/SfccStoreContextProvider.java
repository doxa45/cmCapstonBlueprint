package com.coremedia.livecontext.ecommerce.sfcc.common;

import com.coremedia.blueprint.base.livecontext.ecommerce.common.AbstractStoreContextProvider;
import com.coremedia.cap.multisite.Site;
import com.coremedia.livecontext.ecommerce.catalog.CatalogAlias;
import com.coremedia.livecontext.ecommerce.catalog.CatalogId;
import com.coremedia.livecontext.ecommerce.common.CommerceConfigKeys;
import com.coremedia.livecontext.ecommerce.common.InvalidContextException;
import com.coremedia.livecontext.ecommerce.common.StoreContext;
import com.coremedia.livecontext.ecommerce.common.StoreContextBuilder;
import com.coremedia.livecontext.ecommerce.sfcc.configuration.SfccStoreContextProperties;
import com.coremedia.livecontext.ecommerce.sfcc.ocapi.data.resources.CatalogsResource;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.lang3.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.coremedia.blueprint.base.livecontext.ecommerce.common.CatalogAliasTranslationService.DEFAULT_CATALOG_ALIAS;
import static com.coremedia.blueprint.base.livecontext.ecommerce.common.StoreContextImpl.CATALOG_ID;
import static com.coremedia.blueprint.base.livecontext.ecommerce.common.StoreContextImpl.CURRENCY;
import static com.coremedia.blueprint.base.livecontext.ecommerce.common.StoreContextImpl.LOCALE;
import static com.coremedia.blueprint.base.livecontext.ecommerce.common.StoreContextImpl.SITE;
import static com.coremedia.blueprint.base.livecontext.ecommerce.common.StoreContextImpl.STORE_ID;
import static com.coremedia.blueprint.base.livecontext.ecommerce.common.StoreContextImpl.STORE_NAME;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class SfccStoreContextProvider extends AbstractStoreContextProvider {

  private static final Logger LOG = LoggerFactory.getLogger(SfccStoreContextProvider.class);

  private static final int SITE_TO_CATALOG_CACHE_TIME_IN_SECONDS = 300;

  private CatalogsResource catalogsResource;

  private List<SfccStoreContextProperties> storeContextConfigurations;

  private Map<String, SfccStoreContextProperties> storeContextConfigurationsByName;

  @NonNull
  @Override
  protected Optional<StoreContext> internalCreateContext(@NonNull Site site) {
    // Only create store context if settings are found for current site.
    return Optional.of(findRepositoryStoreConfig(site))
            .filter(config -> !config.isEmpty())
            .map(config -> buildContextFromRepositoryStoreConfig(site, config));
  }

  @NonNull
  private StoreContext buildContextFromRepositoryStoreConfig(@NonNull Site site,
                                                             @NonNull Map<String, Object> repositoryStoreConfig) {
    Map<String, Object> targetConfig = new HashMap<>();

    // Read store context configuration from Spring and property files.
    findConfigId(repositoryStoreConfig)
            .map(this::readStoreConfigFromSpring)
            .ifPresent(targetConfig::putAll);

    // Update store context configuration from LiveContext settings.
    updateStoreConfigFromRepository(repositoryStoreConfig, targetConfig);

    return createContext(site, targetConfig);
  }

  @NonNull
  @Override
  protected Map<String, Object> readStoreConfigFromSpring(@NonNull String configId) {
    SfccStoreContextProperties sfccStoreContextProperties = storeContextConfigurationsByName.get(configId);

    if (sfccStoreContextProperties == null) {
      return emptyMap();
    }

    return readStoreConfigProperties(sfccStoreContextProperties);
  }

  @NonNull
  private static Map<String, Object> readStoreConfigProperties(@NonNull SfccStoreContextProperties config) {
    Map<String, Object> targetConfig = new HashMap<>();

    putValueIfNotNull(CommerceConfigKeys.STORE_ID, config.getStoreId(), targetConfig);
    putValueIfNotNull(CommerceConfigKeys.STORE_NAME, config.getStoreName(), targetConfig);
    putValueIfNotNull(CommerceConfigKeys.CATALOG_ID, config.getCatalogId(), targetConfig);
    putValueIfNotNull(CommerceConfigKeys.CURRENCY, config.getCurrency(), targetConfig);

    return targetConfig;
  }

  @NonNull
  private StoreContext createContext(@NonNull Site site, @NonNull Map<String, Object> storeConfig) {
    StoreContextValuesHolder valuesHolder = new StoreContextValuesHolder();

    valuesHolder.siteId = site.getId();

    // adds site locale to values holder and its replacement map
    handleSiteLocale(site, valuesHolder);

    // Store all provided values in the context.
    for (Map.Entry<String, Object> configEntry : storeConfig.entrySet()) {
      String configEntryKey = configEntry.getKey();
      Object configEntryValue = configEntry.getValue();

      if (configEntryValue == null) {
        LOG.warn("Skipping null value for store config with key '{}'", configEntryKey);
        continue;
      }

      if (configEntryValue instanceof String) {
        String configValueString = (String) configEntryValue;
        handleConfigStringValueEntry(configEntryKey, configValueString, valuesHolder);
      } else {
        handleConfigNonStringValueEntry(configEntryKey, configEntryValue, valuesHolder);
      }
    }

    // Obtain catalog if not set yet.
    if (valuesHolder.catalogId == null) {
      valuesHolder.catalogId = getCatalogIdForStoreId(valuesHolder.storeId);
      valuesHolder.catalogAlias = DEFAULT_CATALOG_ALIAS;
    }

    return createStoreContext(valuesHolder);
  }

  private static void handleSiteLocale(@NonNull Site site, @NonNull StoreContextValuesHolder valuesHolder) {
    Locale locale = site.getLocale();
    valuesHolder.locale = locale;
    valuesHolder.replacements.put(LOCALE, locale.toLanguageTag().replaceAll("-", "_"));
  }

  private static void handleConfigStringValueEntry(@NonNull String configEntryKey, @NonNull String configValueString,
                                                   @NonNull StoreContextValuesHolder valuesHolder) {
    if (isBlank(configValueString)) {
      LOG.warn("Skipping invalid value for store config with key '{}'", configEntryKey);
      return;
    }

    try {
      switch (configEntryKey) {
        case CommerceConfigKeys.LOCALE:
          valuesHolder.locale = LocaleUtils.toLocale(configValueString);
          valuesHolder.replacements.put(LOCALE, configValueString);
          return;

        case CommerceConfigKeys.CURRENCY:
          valuesHolder.currency = Currency.getInstance(configValueString);
          valuesHolder.replacements.put(CURRENCY, configValueString);
          return;

        case CommerceConfigKeys.STORE_ID:
          valuesHolder.storeId = configValueString;
          valuesHolder.replacements.put(STORE_ID, configValueString);
          return;

        case CommerceConfigKeys.STORE_NAME:
          valuesHolder.storeName = configValueString;
          valuesHolder.replacements.put(STORE_NAME, configValueString);
          return;

        case CommerceConfigKeys.CATALOG_ID:
          valuesHolder.catalogId = CatalogId.of(configValueString);
          valuesHolder.catalogAlias = DEFAULT_CATALOG_ALIAS;
          valuesHolder.replacements.put(CATALOG_ID, configValueString);
          return;

        case CommerceConfigKeys.SITE:
          valuesHolder.siteId = configValueString;
          valuesHolder.replacements.put(SITE, configValueString);
          return;

        default:
          valuesHolder.replacements.put(configEntryKey, configValueString);
      }
    } catch (IllegalArgumentException e) {
      throw new InvalidContextException(configEntryKey + " has wrong format.", e);
    }
  }

  private static void handleConfigNonStringValueEntry(@NonNull String configEntryKey, @NonNull Object configEntryValue,
                                                      @NonNull StoreContextValuesHolder valuesHolder) {
    switch (configEntryKey) {
      case CommerceConfigKeys.CURRENCY:
        if (configEntryValue instanceof Currency) {
          Currency currency = (Currency) configEntryValue;
          valuesHolder.currency = currency;
          valuesHolder.replacements.put(CURRENCY, currency.getCurrencyCode());
        }
        return;

      case CommerceConfigKeys.REPLACEMENTS:
        if (configEntryValue instanceof Map) {
          valuesHolder.replacements.putAll((Map<? extends String, ? extends String>) configEntryValue);
        }
        return;

      default:
    }
  }

  @NonNull
  private CatalogId getCatalogIdForStoreId(@Nullable String storeId) {
    SiteToCatalogCacheKey cacheKey = new SiteToCatalogCacheKey("SiteToCatalogCacheKey", catalogsResource,
            SITE_TO_CATALOG_CACHE_TIME_IN_SECONDS);

    String catalogId = getCache().get(cacheKey).get(storeId);
    if (isBlank(catalogId)) {
      throw new IllegalStateException("Unable to fetch 'catalogId' for store " + storeId);
    }

    return CatalogId.of(catalogId);
  }

  @NonNull
  private static StoreContext createStoreContext(@NonNull StoreContextValuesHolder valuesHolder) {
    return SfccStoreContextBuilder
            .from(
                    valuesHolder.replacements,
                    valuesHolder.siteId,
                    valuesHolder.storeId,
                    valuesHolder.storeName,
                    valuesHolder.catalogId,
                    valuesHolder.catalogAlias,
                    valuesHolder.currency,
                    valuesHolder.locale
            )
            .build();
  }

  private static class StoreContextValuesHolder {

    private Map<String, String> replacements = new HashMap<>();
    private String siteId;
    private String storeId;
    private String storeName;
    private CatalogId catalogId;
    private CatalogAlias catalogAlias;
    private Currency currency;
    private Locale locale;
  }

  @NonNull
  @Override
  public StoreContextBuilder buildContext(@NonNull StoreContext source) {
    return SfccStoreContextBuilder.from(source);
  }

  @Autowired
  public void setCatalogsResource(CatalogsResource catalogsResource) {
    this.catalogsResource = catalogsResource;
  }

  @Autowired
  public void setStoreContextConfigurations(List<SfccStoreContextProperties> storeContextConfigurations) {
    this.storeContextConfigurations = storeContextConfigurations;
  }

  @PostConstruct
  void initialize() {
    storeContextConfigurationsByName = storeContextConfigurations.stream()
            .collect(Collectors.toMap(SfccStoreContextProperties::getConfigId, Function.identity()));
  }
}

