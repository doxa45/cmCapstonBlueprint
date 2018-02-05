package com.coremedia.livecontext.ecommerce.ibm.catalog;

import com.coremedia.blueprint.base.livecontext.ecommerce.common.CommerceConnectionInitializer;
import com.coremedia.blueprint.base.livecontext.ecommerce.common.CurrentCommerceConnection;
import com.coremedia.blueprint.lc.test.CatalogServiceBaseTest;
import com.coremedia.cap.multisite.Site;
import com.coremedia.livecontext.ecommerce.catalog.AxisFilter;
import com.coremedia.livecontext.ecommerce.catalog.CatalogAlias;
import com.coremedia.livecontext.ecommerce.catalog.Category;
import com.coremedia.livecontext.ecommerce.catalog.Product;
import com.coremedia.livecontext.ecommerce.catalog.ProductAttribute;
import com.coremedia.livecontext.ecommerce.catalog.ProductVariant;
import com.coremedia.livecontext.ecommerce.common.CommerceId;
import com.coremedia.livecontext.ecommerce.common.StoreContext;
import com.coremedia.livecontext.ecommerce.contract.Contract;
import com.coremedia.livecontext.ecommerce.contract.ContractService;
import com.coremedia.livecontext.ecommerce.ibm.IbmServiceTestBase;
import com.coremedia.livecontext.ecommerce.ibm.common.IbmCommerceIdProvider;
import com.coremedia.livecontext.ecommerce.ibm.common.IbmTestConfig;
import com.coremedia.livecontext.ecommerce.ibm.common.StoreContextHelper;
import com.coremedia.livecontext.ecommerce.ibm.storeinfo.StoreInfoService;
import com.coremedia.livecontext.ecommerce.ibm.user.UserContextHelper;
import com.coremedia.livecontext.ecommerce.user.UserContext;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.coremedia.blueprint.lc.test.BetamaxTestHelper.useBetamaxTapes;
import static com.coremedia.livecontext.ecommerce.common.BaseCommerceBeanType.PRODUCT;
import static com.coremedia.livecontext.ecommerce.ibm.common.WcsVersion.WCS_VERSION_7_8;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IbmServiceTestBase.LocalConfig.class)
@ActiveProfiles(IbmServiceTestBase.LocalConfig.PROFILE)
public abstract class IbmCatalogServiceBaseTest extends CatalogServiceBaseTest {

  protected static final String BEAN_NAME_CATALOG_SERVICE = "catalogService";

  private static final String PRODUCT_VARIANT_CODE_B2B = "FB041_410101";

  private static final String PRODUCT1_WITH_MULTI_SEO = "PC_WALL_CLOCK";
  private static final String PRODUCT2_WITH_MULTI_SEO = "PC_CANDELABRA";
  private static final String PRODUCT3_WITH_MULTI_SEO = "PC_FRUITBOWL";

  protected static final String CATEGORY1_WITH_MULTI_SEO = "PC_Magazines";
  private static final String CATEGORY2_WITH_MULTI_SEO = "PC_ToEat";
  private static final String CATEGORY3_WITH_MULTI_SEO = "PC_Glasses";

  @Inject
  @Named(BEAN_NAME_CATALOG_SERVICE)
  protected CatalogServiceImpl testling;

  @Inject
  protected ContractService contractService;

  @Inject
  protected IbmTestConfig testConfig;

  @Inject
  protected StoreInfoService storeInfoService;

  @Inject
  private IbmCommerceIdProvider ibmCommerceIdProvider;

  @MockBean
  private CommerceConnectionInitializer commerceConnectionInitializer;

  @Before
  @Override
  public void setup() {
    doAnswer(invocationOnMock -> Optional.of(CurrentCommerceConnection.get())).when(commerceConnectionInitializer).findConnectionForSite(any(Site.class));
    testConfig.setWcsVersion(storeInfoService.getWcsVersion());
    super.setup();
  }

  protected void testFindProductVariantByExternalIdWithContractSupport() throws Exception {
    if (useBetamaxTapes() || StoreContextHelper.getWcsVersion(testConfig.getStoreContext()).lessThan(WCS_VERSION_7_8)) {
      return;
    }

    StoreContext storeContext = testConfig.getB2BStoreContext();
    initStoreContext(storeContext);
    CommerceId commerceId = ibmCommerceIdProvider.formatProductVariantId(storeContext.getCatalogAlias(),
            PRODUCT_VARIANT_CODE_B2B);

    ProductVariant productVariantWithoutContract = testling.findProductVariantById(commerceId, storeContext);
    assertNotNull(productVariantWithoutContract);

    BigDecimal offerPriceWithoutContract = productVariantWithoutContract.getOfferPrice();
    assertNotNull(offerPriceWithoutContract);

    // Add contract IDs to store context.
    storeContext = prepareContextsForContractBasedPreview(storeContext);

    ProductVariant productVariantWithContract = testling.findProductVariantById(commerceId, storeContext);
    BigDecimal offerPriceWithContract = productVariantWithContract.getOfferPrice();
    assertNotNull(offerPriceWithContract);

    assertTrue("Contract price for product should be lower",
            offerPriceWithoutContract.floatValue() > offerPriceWithContract.floatValue());
  }

  protected void testFindProductByExternalTechId() throws Exception {
    Product product1 = getTestProductByExternalId(PRODUCT_CODE);
    Product product2 = testling.findProductByExternalTechId(product1.getExternalTechId());
    assertProduct(product2);
  }

  private Product getTestProductByExternalId(String externalId) {
    return testling.findProductById(IbmCommerceIdProvider.commerceId(PRODUCT).withExternalId(externalId).build(), getStoreContext());
  }

  protected void testFindProductByExternalTechIdIsNull() throws Exception {
    Product product = testling.findProductByExternalTechId("blablablub");
    assertNull(product);
  }

  protected void testFindProductMultiSEOByExternalTechId() throws Exception {
    Product product = getTestProductByExternalId(PRODUCT1_WITH_MULTI_SEO);
    assertNotNull(product.getSeoSegment());
    assertTrue(product.getSeoSegment().contains(testConfig.getStoreName().toLowerCase()));
    product = getTestProductByExternalId(PRODUCT2_WITH_MULTI_SEO);
    assertNotNull(product.getSeoSegment());
    assertTrue(product.getSeoSegment().contains(testConfig.getStoreName().toLowerCase()));
    product = getTestProductByExternalId(PRODUCT3_WITH_MULTI_SEO);
    assertNotNull(product.getSeoSegment());
    assertFalse(product.getSeoSegment().contains(testConfig.getStoreName().toLowerCase()));
    ;
  }

  protected void testFindProductVariantByExternalTechId() throws Exception {
    StoreContext storeContext = getStoreContext();
    CommerceId productVariantId = ibmCommerceIdProvider.formatProductVariantId(storeContext.getCatalogAlias(), PRODUCT_VARIANT_CODE);
    ProductVariant productVariant = testling.findProductVariantById(productVariantId, getStoreContext());
    assertEquals(PRODUCT_VARIANT_CODE, productVariant.getExternalId());
    String techId = productVariant.getExternalTechId();
    CommerceId productVariantTechId = ibmCommerceIdProvider.formatProductVariantTechId(storeContext.getCatalogAlias(), techId);
    ProductVariant productVariant2 = testling.findProductVariantById(productVariantTechId, getStoreContext());
    assertEquals(productVariant, productVariant2);
    assertProductVariant(productVariant2);
  }

  protected void testFindTopCategoriesWithContractSupport() throws Exception {
    if (useBetamaxTapes() || StoreContextHelper.getWcsVersion(testConfig.getStoreContext()).lessThan(WCS_VERSION_7_8)) {
      return;
    }

    StoreContext storeContext = testConfig.getB2BStoreContext();
    StoreContextHelper.setCurrentContext(storeContext);

    CatalogAlias catalogAlias = storeContext.getCatalogAlias();
    List<Category> topCategories = testling.findTopCategories(catalogAlias, storeContext);
    int topCategoriesCount = topCategories.size();

    storeContext = prepareContextsForContractBasedPreview(storeContext);

    List<Category> topCategoriesContract = testling.findTopCategories(catalogAlias, storeContext);
    int topCategoriesContractCount = topCategoriesContract.size();

    assertTrue("Contract filter for b2b topcategories not working", topCategoriesCount > topCategoriesContractCount);
  }

  protected void testFindCategoryByExternalTechId() throws Exception {
    StoreContext storeContext = getStoreContext();
    CommerceId categoryId = ibmCommerceIdProvider.formatCategoryId(storeContext.getCatalogAlias(), CATEGORY_CODE);
    Category category1 = testling.findCategoryById(categoryId, storeContext);
    CommerceId categoryTechId = ibmCommerceIdProvider.formatCategoryTechId(storeContext.getCatalogAlias(), category1.getExternalTechId());
    Category category2 = testling.findCategoryById(categoryTechId, storeContext);
    assertCategory(category2);
  }

  protected void testFindCategoryMultiSEOByExternalTechId() throws Exception {
    StoreContext storeContext = getStoreContext();
    CommerceId categoryId = ibmCommerceIdProvider.formatCategoryId(storeContext.getCatalogAlias(), CATEGORY1_WITH_MULTI_SEO);
    Category category = testling.findCategoryById(categoryId, getStoreContext());
    assertNotNull(category.getSeoSegment());
    assertTrue(category.getSeoSegment().contains(testConfig.getStoreName().toLowerCase()));
    CommerceId categoryId1 = ibmCommerceIdProvider.formatCategoryId(storeContext.getCatalogAlias(), CATEGORY2_WITH_MULTI_SEO);
    category = testling.findCategoryById(categoryId1, getStoreContext());
    assertNotNull(category.getSeoSegment());
    assertTrue(category.getSeoSegment().contains(testConfig.getStoreName().toLowerCase()));
    CommerceId categoryId2 = ibmCommerceIdProvider.formatCategoryId(storeContext.getCatalogAlias(), CATEGORY3_WITH_MULTI_SEO);
    category = testling.findCategoryById(categoryId2, getStoreContext());
    assertNotNull(category.getSeoSegment());
    assertFalse(category.getSeoSegment().contains(testConfig.getStoreName().toLowerCase()));
  }

  protected void testFindCategoryByExternalTechIdIsNull() throws Exception {
    CommerceId categoryTechId = ibmCommerceIdProvider.formatCategoryTechId(getStoreContext().getCatalogAlias(), "blablablub");
    Category category = testling.findCategoryById(categoryTechId, getStoreContext());
    assertNull(category);
  }

  private StoreContext prepareContextsForContractBasedPreview(@Nonnull StoreContext storeContext) {
    UserContext userContext = UserContext.builder().withUserName(testConfig.getPreviewUserName()).build();
    UserContextHelper.setCurrentContext(userContext);

    Collection<Contract> contracts = contractService.findContractIdsForUser(UserContextHelper.getCurrentContext(),
            storeContext);
    assertNotNull(contracts);

    Iterator<Contract> iterator = contracts.iterator();
    Contract contract = null;
    while (iterator.hasNext()) {
      contract = iterator.next();
      String contractName = contract.getName();
      if (contractName != null && contractName.contains("Applicances Expert")) {
        break;
      }
    }
    assertNotNull(contract);

    storeContext.setContractIdsForPreview(new String[]{contract.getExternalTechId()});

    return storeContext;
  }

  @Override
  protected void assertProduct(Product product) {
    super.assertProduct(product);
    //test attributes
    List<ProductAttribute> definingAttributes = product.getDefiningAttributes();
    assertNotNull(definingAttributes);
    assertThat(definingAttributes.isEmpty(), is(false));

    List<ProductAttribute> describingAttributes = product.getDescribingAttributes();
    assertNotNull(describingAttributes);
    assertThat(describingAttributes.isEmpty(), is(false));

    //test variants
    List<ProductVariant> variants = product.getVariants();
    assertNotNull(variants);
    assertThat(variants.isEmpty(), is(false));

    //test axis filter
    List<String> variantAxisNames = product.getVariantAxisNames();
    if (!variantAxisNames.isEmpty()) {
      List<ProductVariant> filteredVariants = product.getVariants(new AxisFilter(variantAxisNames.get(0), "*"));
      assertTrue(variants.size() >= filteredVariants.size());
    }
  }

  @Override
  protected void assertProductVariant(ProductVariant productVariant) {
    super.assertProductVariant(productVariant);
    List<ProductAttribute> describingAttributes = productVariant.getDescribingAttributes();
    assertThat(describingAttributes.isEmpty(), is(false));
    List<ProductAttribute> definingAttributes = productVariant.getDefiningAttributes();
    assertThat(definingAttributes.isEmpty(), is(false));
  }
}
