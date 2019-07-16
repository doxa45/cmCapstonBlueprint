package com.coremedia.livecontext.fragment.links.transformers.resolvers;

import com.coremedia.blueprint.common.contentbeans.CMNavigation;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Base class for all LiveContext link resolvers.
 */
public abstract class AbstractLiveContextLinkResolver implements LiveContextLinkResolver {

  protected static final Logger LOG = LoggerFactory.getLogger(AbstractLiveContextLinkResolver.class);

  public static final String KEY_PLAIN_LINK = "--PLAIN_LINK--";

  public static final String LIVECONTEXT_COMMENT_PREFIX = "<!--CM ";
  public static final String LIVECONTEXT_COMMENT_SUFFIX = "CM-->";

  @NonNull
  @Override
  public Optional<String> resolveUrl(@NonNull String source, @Nullable Object bean, @Nullable String variant,
                                     @Nullable CMNavigation navigation, @NonNull HttpServletRequest request) {
    try {
      JSONObject json = resolveUrlInternal(source, bean, variant, navigation, request);

      // Special handling for links that are resolved directly,
      // i.e. for com.coremedia.blueprint.common.contentbeans.CMExternalLink
      if (json.has(KEY_PLAIN_LINK)) {
        String plainLink = json.getString(KEY_PLAIN_LINK);
        return Optional.of(plainLink);
      }

      return Optional.of(LIVECONTEXT_COMMENT_PREFIX + json.toString() + LIVECONTEXT_COMMENT_SUFFIX);
    } catch (JSONException e) {
      String beanStr = bean != null ? bean.toString() : "null";
      LOG.error("Could not build URL JSON for {}", beanStr.concat("#").concat("variant"), e);
      return Optional.empty();
    }
  }

  /**
   * @param source     source link
   * @param bean       Bean for which URL is to be rendered
   * @param variant    Link variant
   * @param navigation Current navigation of bean for which URL is to be rendered
   * @param request    request
   * @return JSON object containing all relevant details for URL rendering, except for "type":"URL", which
   * will be added by {@link AbstractLiveContextLinkResolver} automatically.
   * @throws JSONException if something JSON-related goes wrong
   */
  @NonNull
  protected abstract JSONObject resolveUrlInternal(@NonNull String source, @Nullable Object bean,
                                                   @Nullable String variant, @Nullable CMNavigation navigation,
                                                   @NonNull HttpServletRequest request);

  @NonNull
  public static String deabsolutizeLink(@NonNull String cmsLink) {
    // example: https://preview.host.name/bla/servlet/dynamic/placement/p13n/sitegenesis-en-gb/130/main?targetView=%5Bcarousel%5D
    if (cmsLink.startsWith("http") || cmsLink.startsWith("//")) {
      int index = StringUtils.ordinalIndexOf(cmsLink, "/", 3);
      if (index != -1) {
        return cmsLink.substring(index);
      }
    }
    return cmsLink;
  }
}
