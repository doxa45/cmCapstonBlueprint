package com.coremedia.livecontext.ecommerce.hybris;

import com.coremedia.blueprint.base.livecontext.ecommerce.common.CatalogAliasTranslationService;
import com.coremedia.blueprint.lc.test.TestConfig;
import com.coremedia.livecontext.ecommerce.catalog.CatalogId;
import com.coremedia.livecontext.ecommerce.common.StoreContext;
import com.coremedia.livecontext.ecommerce.hybris.common.HybrisStoreContextBuilder;
import com.coremedia.springframework.xml.ResourceAwareXmlBeanDefinitionReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
@PropertySource(
        value = {
                "classpath:/com/coremedia/livecontext/ecommerce/hybris/test-hybris-services.properties",
                "classpath:/com/coremedia/livecontext/ecommerce/hybris/hybris-example-catalog.properties"
        }
)
@ImportResource(
        value = {
                "classpath:/com/coremedia/livecontext/ecommerce/hybris/test-hybris-services.xml",
                "classpath:/framework/spring/livecontext-hybris-commercebeans.xml"
        },
        reader = ResourceAwareXmlBeanDefinitionReader.class
)
public class HybrisTestConfig implements TestConfig {

  private static final CatalogId CATALOG_ID = CatalogId.of("apparelProductCatalog");

  @Override
  public StoreContext getStoreContext() {
    return HybrisStoreContextBuilder
            .from("theSiteId")
            .withStoreId("apparel-uk")
            .withStoreName("Apparel-Catalog")
            .withCatalogId(CATALOG_ID)
            .withCatalogVersion("Staged")
            .withCurrency(Currency.getInstance("GBP"))
            .withLocale(Locale.ENGLISH)
            .build();
  }

  @Override
  public StoreContext getGermanStoreContext() {
    return HybrisStoreContextBuilder
            .from("theSiteId")
            .withStoreId("apparel-de")
            .withStoreName("Apparel-Catalog")
            .withCatalogId(CATALOG_ID)
            .withCatalogVersion("Staged")
            .withCurrency(Currency.getInstance("EUR"))
            .withLocale(Locale.GERMAN)
            .build();
  }

  @Override
  public String getConnectionId() {
    return "hybris1";
  }

  @Override
  public String getCatalogName() {
    return null;
  }

  @Override
  public String getStoreName() {
    return null;
  }

  @Bean
  @Primary
  CatalogAliasTranslationService theCatalogAliasTranslationService() {
    CatalogAliasTranslationService catalogAliasTranslationService = mock(CatalogAliasTranslationService.class);
    when(catalogAliasTranslationService.getCatalogIdForAlias(any(), any())).thenReturn(Optional.of(CATALOG_ID));
    return catalogAliasTranslationService;
  }
}
