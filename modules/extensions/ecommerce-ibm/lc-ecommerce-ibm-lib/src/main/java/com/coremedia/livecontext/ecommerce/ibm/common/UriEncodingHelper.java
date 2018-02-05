package com.coremedia.livecontext.ecommerce.ibm.common;

import com.google.common.annotations.VisibleForTesting;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.regex.Pattern;

class UriEncodingHelper {

  private static final Pattern PLUS = Pattern.compile("\\+");

  private UriEncodingHelper() {
  }

  @Nonnull
  @VisibleForTesting
  static UriComponents fixPlusEncoding(@Nonnull UriComponents encodedUriComponents) {
    UriComponentsBuilder builder = UriComponentsBuilder.newInstance().uriComponents(encodedUriComponents);

    encodedUriComponents.getQueryParams().forEach((key, value) -> replaceEncodedQueryParam(builder, key, value));

    return builder.build(true);
  }

  private static void replaceEncodedQueryParam(UriComponentsBuilder builder, String paramName, List<String> paramValues) {

    Object[] strings = paramValues.stream()
            .map(s -> PLUS.matcher(s).replaceAll("%2B"))
            .toArray();
    builder.replaceQueryParam(paramName, strings);
  }

}
