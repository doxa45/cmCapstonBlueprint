package com.coremedia.lc.studio.lib.validators;

import com.coremedia.cap.content.Content;
import com.coremedia.rest.validation.Issues;
import com.coremedia.rest.validation.Severity;
import edu.umd.cs.findbugs.annotations.NonNull;

public class ExternalProductValidator extends CatalogLinkValidator {

  private static final String CODE_ISSUE_PRODUCT_EMPTY = "EmptyProduct";

  @Override
  protected void emptyPropertyValue(@NonNull Content content, @NonNull Issues issues) {
    addIssue(issues, Severity.ERROR, CODE_ISSUE_PRODUCT_EMPTY);
  }
}
