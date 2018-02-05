package com.coremedia.livecontext.ecommerce.ibm.catalog;

import com.coremedia.blueprint.base.livecontext.ecommerce.common.CommerceCache;
import com.coremedia.cache.Cache;
import com.coremedia.livecontext.ecommerce.common.BaseCommerceBeanType;
import com.coremedia.livecontext.ecommerce.common.CommerceId;
import com.coremedia.livecontext.ecommerce.common.InvalidIdException;
import com.coremedia.livecontext.ecommerce.common.StoreContext;
import com.coremedia.livecontext.ecommerce.ibm.common.AbstractIbmDocumentCacheKey;
import com.coremedia.livecontext.ecommerce.ibm.common.DataMapHelper;
import com.coremedia.livecontext.ecommerce.user.UserContext;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;

class CategoryCacheKey extends AbstractIbmDocumentCacheKey<Map<String, Object>> {

  private WcCatalogWrapperService wrapperService;

  CategoryCacheKey(@Nonnull CommerceId id,
                   StoreContext storeContext,
                   UserContext userContext,
                   WcCatalogWrapperService wrapperService,
                   CommerceCache commerceCache) {
    super(id, storeContext, userContext, CONFIG_KEY_CATEGORY, commerceCache);
    this.wrapperService = wrapperService;
    if (!BaseCommerceBeanType.CATEGORY.equals(id.getCommerceBeanType())) {
      throw new InvalidIdException(id + " is not a category id.");
    }
  }

  @Override
  public Map<String, Object> computeValue(Cache cache) {
    return wrapperService.findCategoryById(getCommerceId(), storeContext, userContext);
  }

  @Override
  public void addExplicitDependency(Map<String, Object> wcCategory) {
    if (wcCategory != null){
      Cache.dependencyOn(DataMapHelper.findStringValue(wcCategory, "uniqueID").orElse(null));
    }
  }

  @Override
  protected String getCacheIdentifier() {
    return id + ":" + configKey + ":" + storeContext.getSiteId() + ":" +
            storeContext.getStoreId() + ":" + storeContext.getLocale() + ":" +
            storeContext.getWorkspaceId() + ":" + Arrays.toString(storeContext.getContractIds()) + ":" +
            Arrays.toString(storeContext.getContractIdsForPreview());
  }
}
