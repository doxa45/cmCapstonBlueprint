package com.coremedia.ecommerce.studio.rest;

import com.coremedia.blueprint.base.livecontext.ecommerce.common.CurrentCommerceConnection;
import com.coremedia.livecontext.ecommerce.catalog.CatalogAlias;
import com.coremedia.livecontext.ecommerce.catalog.CatalogService;
import com.coremedia.livecontext.ecommerce.common.CommerceBean;
import com.coremedia.livecontext.ecommerce.common.CommerceConnection;
import com.coremedia.livecontext.ecommerce.common.StoreContext;
import com.coremedia.livecontext.ecommerce.p13n.MarketingSpotService;
import com.coremedia.livecontext.ecommerce.search.SearchResult;
import com.coremedia.livecontext.ecommerce.workspace.WorkspaceId;
import com.coremedia.rest.cap.common.represent.SuggestionResultRepresentation;
import com.coremedia.rest.cap.content.SearchParameterNames;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.coremedia.blueprint.base.livecontext.ecommerce.common.CatalogAliasTranslationService.DEFAULT_CATALOG_ALIAS;
import static com.coremedia.blueprint.base.livecontext.ecommerce.common.StoreContextImpl.WORKSPACE_ID_NONE;
import static java.util.Collections.emptyList;

/**
 * Catalog configuration helpter as a RESTful resource.
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("livecontext")
public class CatalogServiceResource {

  private static final String DEFAULT_SUGGESTIONS_LIMIT = "10";
  private static final String DEFAULT_SEARCH_LIMIT = "-1";

  private static final String SEARCH_PARAM_CATEGORY = "category";
  private static final String SEARCH_PARAM_CATALOG_ALIAS = "catalogAlias";
  private static final String SEARCH_PARAM_SITE_ID = "siteId";
  private static final String SEARCH_PARAM_QUERY = "query";
  private static final String SEARCH_PARAM_LIMIT = "limit";
  private static final String SEARCH_PARAM_ORDER_BY = "orderBy";
  private static final String SEARCH_PARAM_WORKSPACE_ID = "workspaceId";

  private static final String SEARCH_PARAM_SEARCH_TYPE = "searchType";
  public static final String SEARCH_TYPE_PRODUCT_VARIANT = "ProductVariant";
  private static final String SEARCH_TYPE_MARKETING_SPOTS = "MarketingSpot";

  @GET
  @Path("search/{siteId:[^/]+}")
  @Nullable
  public CatalogSearchResultRepresentation search(@PathParam(SEARCH_PARAM_SITE_ID) String siteId,
                                                  @QueryParam(SEARCH_PARAM_QUERY) String query,
                                                  @QueryParam(SEARCH_PARAM_LIMIT) @DefaultValue(DEFAULT_SEARCH_LIMIT) int limit,
                                                  @QueryParam(SEARCH_PARAM_ORDER_BY) String orderBy,
                                                  @QueryParam(SEARCH_PARAM_SEARCH_TYPE) String searchType,
                                                  @QueryParam(SEARCH_PARAM_CATEGORY) String category,
                                                  @QueryParam(SEARCH_PARAM_CATALOG_ALIAS) String catalogAlias,
                                                  @QueryParam(SEARCH_PARAM_WORKSPACE_ID) String workspaceId) {
    // The site ID in the URL is ignored here, but the `SiteFilter`
    // should have picked it up so the `CommerceConnectionFilter`
    // provides a commerce connection based on the site ID.

    CommerceConnection commerceConnection = CurrentCommerceConnection.find().orElse(null);
    if (commerceConnection == null) {
      return null;
    }

    StoreContext currentStoreContext = commerceConnection.getStoreContext();
    if (currentStoreContext == null) {
      return null;
    }

    StoreContext newStoreContextForSite = commerceConnection
            .getStoreContextProvider()
            .buildContext(currentStoreContext)
            .withWorkspaceId(workspaceId != null ? WorkspaceId.of(workspaceId) : WORKSPACE_ID_NONE)
            .build();

    Map<String, String> params = getParams(category, catalogAlias, newStoreContextForSite, limit);
    SearchResult<? extends CommerceBean> searchResult = search(query, searchType, newStoreContextForSite, params);

    return new CatalogSearchResultRepresentation(searchResult.getSearchResult(), searchResult.getTotalCount());
  }

  @NonNull
  private Map<String, String> getParams(String category, String catalogAlias, @NonNull StoreContext storeContext,
                                        int limit) {
    Map<String, String> params = new HashMap<>();

    if (!StringUtils.isEmpty(category) && !isRootCategory(category, catalogAlias, storeContext)) {
      params.put(CatalogService.SEARCH_PARAM_CATEGORYID, category);
    }

    if (!StringUtils.isEmpty(catalogAlias)) {
      params.put(CatalogService.SEARCH_PARAM_CATALOG_ALIAS, catalogAlias);
    }

    if (limit > 0) {
      params.put(CatalogService.SEARCH_PARAM_PAGESIZE, String.valueOf(limit));
    }

    return params;
  }

  private boolean isRootCategory(@NonNull String categoryParam, String catalogAlias, @NonNull StoreContext storeContext) {
    // check if it is the actual id of the root category
    // (depends on the commerce implementation)
    CatalogAlias myAlias = StringUtils.isEmpty(catalogAlias) ? DEFAULT_CATALOG_ALIAS : CatalogAlias.of(catalogAlias);
    CommerceConnection commerceConnection = storeContext.getConnection();
    CatalogService catalogService = commerceConnection.getCatalogService();
    String rootCategoryId = catalogService.findRootCategory(myAlias, storeContext).getExternalId();
    return categoryParam.equals(rootCategoryId);
  }

  private SearchResult<? extends CommerceBean> search(String query, String searchType,
                                                      @NonNull StoreContext newStoreContextForSite,
                                                      @NonNull Map<String, String> params) {
    CommerceConnection commerceConnection = newStoreContextForSite.getConnection();

    if (searchType != null && searchType.equals(SEARCH_TYPE_PRODUCT_VARIANT)) {
      CatalogService catalogService = commerceConnection.getCatalogService();
      return catalogService.searchProductVariants(query, params, newStoreContextForSite);
    } else if (searchType != null && searchType.equals(SEARCH_TYPE_MARKETING_SPOTS)) {
      MarketingSpotService marketingSpotService = commerceConnection.getMarketingSpotService();
      if (marketingSpotService == null) {
        SearchResult<? extends CommerceBean> searchResult = new SearchResult<>();
        searchResult.setSearchResult(emptyList());
        searchResult.setTotalCount(0);
        return searchResult;
      } else {
        return marketingSpotService.searchMarketingSpots(query, params, newStoreContextForSite);
      }
    } else {// default: Product
      CatalogService catalogService = commerceConnection.getCatalogService();
      return catalogService.searchProducts(query, params, newStoreContextForSite);
    }
  }

  @GET
  @Path("suggestions")
  @NonNull
  public SuggestionResultRepresentation searchSuggestions(@QueryParam(SearchParameterNames.QUERY) String query,
                                                          @QueryParam(SearchParameterNames.LIMIT) @DefaultValue(DEFAULT_SUGGESTIONS_LIMIT) int limit,
                                                          @QueryParam(SEARCH_PARAM_SEARCH_TYPE) String searchType,
                                                          @QueryParam(SEARCH_PARAM_SITE_ID) String siteId,
                                                          @QueryParam(SEARCH_PARAM_CATEGORY) String category,
                                                          @QueryParam(SEARCH_PARAM_WORKSPACE_ID) String workspaceId) {
    //TODO not supported yet
    return new SuggestionResultRepresentation(new ArrayList<>());
  }
}
