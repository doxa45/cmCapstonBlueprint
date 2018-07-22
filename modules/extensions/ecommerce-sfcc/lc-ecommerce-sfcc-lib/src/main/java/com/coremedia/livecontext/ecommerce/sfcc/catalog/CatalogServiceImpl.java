package com.coremedia.livecontext.ecommerce.sfcc.catalog;

import com.coremedia.blueprint.base.livecontext.ecommerce.common.CommerceCache;
import com.coremedia.livecontext.ecommerce.catalog.Catalog;
import com.coremedia.livecontext.ecommerce.catalog.CatalogAlias;
import com.coremedia.livecontext.ecommerce.catalog.CatalogId;
import com.coremedia.livecontext.ecommerce.catalog.CatalogService;
import com.coremedia.livecontext.ecommerce.catalog.Category;
import com.coremedia.livecontext.ecommerce.catalog.Product;
import com.coremedia.livecontext.ecommerce.catalog.ProductVariant;
import com.coremedia.livecontext.ecommerce.common.BaseCommerceBeanType;
import com.coremedia.livecontext.ecommerce.common.CommerceBean;
import com.coremedia.livecontext.ecommerce.common.CommerceBeanFactory;
import com.coremedia.livecontext.ecommerce.common.CommerceId;
import com.coremedia.livecontext.ecommerce.common.StoreContext;
import com.coremedia.livecontext.ecommerce.search.SearchFacet;
import com.coremedia.livecontext.ecommerce.search.SearchResult;
import com.coremedia.livecontext.ecommerce.sfcc.common.CommerceBeanUtils;
import com.coremedia.livecontext.ecommerce.sfcc.common.SfccCommerceIdProvider;
import com.coremedia.livecontext.ecommerce.sfcc.ocapi.data.AbstractOCSearchResultDocument;
import com.coremedia.livecontext.ecommerce.sfcc.ocapi.data.documents.ProductDocument;
import com.coremedia.livecontext.ecommerce.sfcc.ocapi.data.documents.ProductSearchResultDocument;
import com.coremedia.livecontext.ecommerce.sfcc.ocapi.data.resources.CategoryProductAssignmentSearchResource;
import com.coremedia.livecontext.ecommerce.sfcc.ocapi.data.resources.ProductSearchResource;
import com.coremedia.livecontext.ecommerce.sfcc.ocapi.data.resources.ProductsResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

/**
 * {@link com.coremedia.livecontext.ecommerce.catalog.CatalogService} implementation for Salesforce Commerce Cloud Commerce.
 */
public class CatalogServiceImpl implements CatalogService {

  private static final Logger LOG = LoggerFactory.getLogger(CatalogServiceImpl.class);

  public static final String ROOT_CATEGORY_ID = "root";

  private final ProductsResource productsResource;
  private final CategoryProductAssignmentSearchResource categoryProductAssignmentSearchResource;
  private final ProductSearchResource productSearchResource;

  private final CommerceCache commerceCache;
  private final CommerceBeanFactory commerceBeanFactory;

  public CatalogServiceImpl(@NonNull ProductsResource productsResource,
                            @NonNull CategoryProductAssignmentSearchResource categoryProductAssignmentSearchResource,
                            @NonNull ProductSearchResource productSearchResource,
                            @NonNull CommerceCache commerceCache,
                            @NonNull CommerceBeanFactory commerceBeanFactory) {
    this.productsResource = productsResource;
    this.categoryProductAssignmentSearchResource = categoryProductAssignmentSearchResource;
    this.productSearchResource = productSearchResource;
    this.commerceCache = commerceCache;
    this.commerceBeanFactory = commerceBeanFactory;
  }

  @Override
  @Nullable
  public Product findProductById(@NonNull CommerceId commerceId, @NonNull StoreContext storeContext) {
    ProductCacheKey cacheKey = new ProductCacheKey(commerceId, storeContext, productsResource, commerceCache);
    ProductDocument delegate = commerceCache.get(cacheKey);
    if (delegate == null) {
      return null;
    }

    if (delegate.getType().isVariant()) {
      return CommerceBeanUtils.createBeanFor(commerceBeanFactory, delegate, storeContext, BaseCommerceBeanType.SKU, ProductVariant.class);
    } else {
      return CommerceBeanUtils.createBeanFor(commerceBeanFactory, delegate, storeContext, BaseCommerceBeanType.PRODUCT, Product.class);
    }
  }

  @Nullable
  @Override
  public Product findProductBySeoSegment(@NonNull String seoSegment, @NonNull StoreContext storeContext) {
    return null;
  }

  @Nullable
  @Override
  public ProductVariant findProductVariantById(@NonNull CommerceId commerceId, @NonNull StoreContext storeContext) {
    ProductCacheKey cacheKey = new ProductCacheKey(commerceId, storeContext, productsResource, commerceCache);

    return commerceCache.find(cacheKey)
            .map(delegate -> CommerceBeanUtils
                    .createBeanFor(commerceBeanFactory, delegate, storeContext, BaseCommerceBeanType.SKU, ProductVariant.class))
            .orElse(null);
  }

  @NonNull
  @Override
  public List<Product> findProductsByCategory(@NonNull Category category) {
    CommerceId categoryId = category.getId();
    StoreContext storeContext = category.getContext();

    LOG.debug("Searching products for category {}", categoryId);

    ProductsByCategoryCacheKey productsByCategoryCacheKey = new ProductsByCategoryCacheKey(categoryId, storeContext,
            categoryProductAssignmentSearchResource, commerceCache);

    List<ProductDocument> productDocuments = commerceCache.find(productsByCategoryCacheKey)
            .orElseGet(Collections::emptyList);

    return CommerceBeanUtils.createLightweightBeansFor(commerceBeanFactory, productDocuments, storeContext,
            BaseCommerceBeanType.PRODUCT, Product.class);
  }

  @NonNull
  @Override
  public Category findRootCategory(@NonNull CatalogAlias catalogAlias, @NonNull StoreContext storeContext) {
    CommerceId commerceId = SfccCommerceIdProvider
            .commerceId(BaseCommerceBeanType.CATEGORY)
            .withExternalId(ROOT_CATEGORY_ID)
            .build();

    return findCategoryById(commerceId, storeContext);
  }

  @NonNull
  @Override
  public List<Category> findTopCategories(@NonNull CatalogAlias catalogAlias, @NonNull StoreContext storeContext) {
    Category rootCategory = findRootCategory(catalogAlias, storeContext);
    return rootCategory.getChildren();
  }

  @NonNull
  @Override
  public List<Category> findSubCategories(@NonNull Category parentCategory) {
    return parentCategory.getChildren();
  }

  @Nullable
  @Override
  public Category findCategoryById(@NonNull CommerceId id, @NonNull StoreContext storeContext) {
    return (Category) commerceBeanFactory.loadBeanFor(id, storeContext);
  }

  @Nullable
  @Override
  public Category findCategoryBySeoSegment(@NonNull String seoSegment, @NonNull StoreContext storeContext) {
    CommerceId commerceId = SfccCommerceIdProvider
            .commerceId(BaseCommerceBeanType.CATEGORY)
            .withSeo(seoSegment)
            .build();

    return findCategoryById(commerceId, storeContext);
  }

  @NonNull
  @Override
  public SearchResult<Product> searchProducts(@NonNull String searchTerm,
                                              @NonNull Map<String, String> searchParams,
                                              @NonNull StoreContext storeContext) {
    Set<String> categoryIdsForSearch = emptySet();
    if (searchParams.containsKey(SEARCH_PARAM_CATEGORYID)) {
      //Epand with all subcategories, since SFCC OC Data Api connot search recursively below a category.
      //All underlying categories must be computed before.
      String categoryId = searchParams.get(SEARCH_PARAM_CATEGORYID);
      categoryIdsForSearch = prepareCategoryIdsForSearch(storeContext, categoryId);
    }

    Optional<ProductSearchResultDocument> productSearchResultDocument = productSearchResource
            .searchProducts(searchTerm, searchParams, categoryIdsForSearch, storeContext);

    List<ProductDocument> hits = productSearchResultDocument
            .map(AbstractOCSearchResultDocument::getHits)
            .orElseGet(Collections::emptyList);

    List<Product> products = CommerceBeanUtils.createLightweightBeansFor(commerceBeanFactory, hits, storeContext,
            BaseCommerceBeanType.PRODUCT, Product.class);

    int totalCount = productSearchResultDocument
            .map(AbstractOCSearchResultDocument::getTotal)
            .orElse(0);

    SearchResult<Product> result = new SearchResult<>();
    result.setSearchResult(products);
    result.setTotalCount(totalCount);
    return result;
  }

  @NonNull
  @Override
  public Map<String, List<SearchFacet>> getFacetsForProductSearch(@NonNull Category category,
                                                                  @NonNull StoreContext storeContext) {
    return emptyMap();
  }

  /**
   * Since the SFCC Search for Product Variants
   * (additional Term:{"term_query":{"values":["variant"],  "fields":["type" ], "operator":"one_of"}})
   * does not return results as expected, we shipped around as follows:
   * <p>
   * 1) try to load a a product with the searchTerm (externalId) and if so, return its product variants
   * 2) search for products with the given searchTerm and iterate them, collect and return its variants.
   * Do so as long as the result size does not exceed 500.
   */
  @NonNull
  @Override
  public SearchResult<ProductVariant> searchProductVariants(@NonNull String searchTerm,
                                                            @NonNull Map<String, String> searchParams,
                                                            @NonNull StoreContext storeContext) {
    SearchResult<ProductVariant> result = new SearchResult<>();

    // Lookup product variants via product id first.
    CommerceId commerceId = SfccCommerceIdProvider
            .commerceId(BaseCommerceBeanType.PRODUCT)
            .withExternalId(searchTerm)
            .build();
    Product masterProduct = findProductById(commerceId, storeContext);
    if (masterProduct != null) {
      List<ProductVariant> productVariants = masterProduct.getVariants();
      result.setSearchResult(productVariants);
      result.setTotalCount(productVariants.size());
    } else {
      //search products and return all its variants. maximum hits are limited to 500
      SearchResult<Product> productSearchResult = searchProducts(searchTerm, searchParams, storeContext);
      if (productSearchResult.getTotalCount() > 0) {
        List<ProductVariant> productVariants = new ArrayList<>();
        int countHits = 0;
        for (int i = 0; i < productSearchResult.getTotalCount() && countHits < 500; i++) {
          Product master = productSearchResult.getSearchResult().get(i);
          List<ProductVariant> variants = master.getVariants();
          productVariants.addAll(variants);
          countHits += variants.size();
        }
        result.setSearchResult(productVariants);
        result.setTotalCount(productVariants.size());
      }
    }
    return result;
  }

  @NonNull
  private Set<String> prepareCategoryIdsForSearch(@NonNull StoreContext storeContext, String categoryId) {
    Set<String> setOfSearchCategoryIds = new HashSet<>();
    setOfSearchCategoryIds.add(categoryId);

    CommerceId commerceId = SfccCommerceIdProvider
            .commerceId(BaseCommerceBeanType.CATEGORY)
            .withExternalId(categoryId)
            .build();

    CommerceBean category = commerceBeanFactory.loadBeanFor(commerceId, storeContext);

    if (category != null) {
      setOfSearchCategoryIds.addAll(
              getSubCategoriesRecursively((Category) category).stream()
                      .map(Category::getExternalId)
                      .filter(Objects::nonNull)
                      .collect(toSet())
      );
    }

    return setOfSearchCategoryIds;
  }

  @NonNull
  private static Set<Category> getSubCategoriesRecursively(@NonNull Category category) {
    Set<Category> allChildren = new HashSet<>();
    List<Category> children = category.getChildren();
    allChildren.addAll(children);
    for (Category child : children) {
      allChildren.addAll(getSubCategoriesRecursively(child));
    }
    return allChildren;
  }

  @NonNull
  @Override
  public List<Catalog> getCatalogs(@NonNull StoreContext storeContext) {
    return emptyList();
  }

  @NonNull
  @Override
  public Optional<Catalog> getCatalog(@NonNull CatalogId catalogId, @NonNull StoreContext storeContext) {
    return Optional.empty();
  }

  @NonNull
  @Override
  public Optional<Catalog> getCatalog(@NonNull CatalogAlias alias, @NonNull StoreContext storeContext) {
    return Optional.empty();
  }

  @NonNull
  @Override
  public Optional<Catalog> getDefaultCatalog(@NonNull StoreContext storeContext) {
    return Optional.empty();
  }

}
