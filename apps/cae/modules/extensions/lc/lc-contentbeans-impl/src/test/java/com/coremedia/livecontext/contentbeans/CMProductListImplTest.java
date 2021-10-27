package com.coremedia.livecontext.contentbeans;

import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.test.xmlrepo.XmlRepoConfiguration;
import com.coremedia.cms.delivery.configuration.DeliveryConfigurationProperties;
import com.coremedia.livecontext.augmentation.config.LcAugmentationLegacyAutoConfiguration;
import com.coremedia.livecontext.ecommerce.search.SearchQueryFacet;
import com.coremedia.objectserver.beans.ContentBeanFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static com.coremedia.livecontext.contentbeans.CMProductListImpl.FILTER_FACETS_DEFAULT;
import static com.coremedia.livecontext.contentbeans.CMProductListImpl.MAX_LENGTH_DEFAULT;
import static com.coremedia.livecontext.contentbeans.CMProductListImpl.OFFSET_DEFAULT;
import static com.coremedia.livecontext.contentbeans.CMProductListImpl.ORDER_BY_DEFAULT;
import static com.coremedia.livecontext.contentbeans.CMProductListImpl.PROP_MAX_LENGTH;
import static com.coremedia.livecontext.contentbeans.CMProductListImpl.PROP_OFFSET;
import static com.coremedia.livecontext.contentbeans.CMProductListImpl.PROP_ORDER_BY;
import static com.coremedia.livecontext.contentbeans.CMProductListImpl.SETTING_FILTER_FACETS;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        CMProductListImplTest.LocalConfig.class,
        XmlRepoConfiguration.class,
})
@TestPropertySource(properties = {
        "repository.factoryClassName=com.coremedia.cap.xmlrepo.XmlCapConnectionFactory",
        "repository.params.contentxml=classpath:/com/coremedia/livecontext/contentbeans/contenttest.xml",
})
class CMProductListImplTest {

  private CMProductListImpl productListWithOldFacetStruct;
  private CMProductListImpl productListWithNewFacetsStruct;

  @Autowired
  private ContentRepository contentRepository;

  @Autowired
  private ContentBeanFactory contentBeanFactory;

  @BeforeEach
  void setUp() {
    productListWithOldFacetStruct = getProductListBeanForContentId(200);
    productListWithNewFacetsStruct = getProductListBeanForContentId(202);
  }

  private CMProductListImpl getProductListBeanForContentId(int contentId) {
    Content content = contentRepository.getContent(IdHelper.formatContentId(contentId));
    return contentBeanFactory.createBeanFor(content, CMProductListImpl.class);
  }

  @Test
  void productListSettingsGetterWithNullValues() {
    // GIVEN
    CMProductListImpl productListWithNullValues = getProductListBeanForContentId(666);

    // WHEN
    Map<String, Object> productListSettings = productListWithNullValues.getProductListSettings();

    //THEN
    assertThat(productListSettings)
            .hasSize(4)
            .containsEntry(PROP_ORDER_BY, "")
            .containsEntry(PROP_OFFSET, null)
            .containsEntry(PROP_MAX_LENGTH, null)
            .containsEntry(SETTING_FILTER_FACETS, null);
    assertThat(productListWithNullValues)
            .returns(OFFSET_DEFAULT, CMProductListImpl::getOffset)
            .returns(ORDER_BY_DEFAULT, CMProductListImpl::getOrderBy)
            .returns(MAX_LENGTH_DEFAULT, CMProductListImpl::getMaxLength)
            .returns(FILTER_FACETS_DEFAULT, CMProductListImpl::getFilterFacets);
  }

  @Test
  void getSingleFacetFromOldStruct() {
    String facetFromOld = productListWithOldFacetStruct.getFacet();
    assertThat(facetFromOld)
            .isNotNull()
            .isEqualTo("price=(0..20)");
  }

  @Test
  void getMultiFacetsFromOldStruct() {
    List<SearchQueryFacet> facetsFromOld = productListWithOldFacetStruct.getFilterFacetQueries();
    assertThat(facetsFromOld)
            .extracting(SearchQueryFacet::value)
            .isEmpty();
  }

  @Test
  void getMultiFacetsFromNewStruct() {
    List<SearchQueryFacet> facetsFromNew = productListWithNewFacetsStruct.getFilterFacetQueries();
    assertThat(facetsFromNew)
            .extracting(SearchQueryFacet::value)
            .contains("price=(20..50)", "color=brown", "color=white");
  }

  @Test
  void getLimitsFromStruct() {
    assertThat(productListWithNewFacetsStruct).satisfies(struct -> {
      assertThat(struct.getMaxLength()).isEqualTo(42);
      // The UI (and thus productList settings) work with an offset based
      // on 1, whereas the API works with a technical offset based on 0.
      assertThat(struct.getOffset()).isEqualTo(5);
    });
  }

  @Configuration(proxyBeanMethods = false)
  @EnableConfigurationProperties(DeliveryConfigurationProperties.class)
  @ComponentScan("com.coremedia.blueprint.base.livecontext.augmentation")
  @Import(LcAugmentationLegacyAutoConfiguration.class)
  @ImportResource({
          "classpath:/framework/spring/blueprint-contentbeans.xml",
          "classpath:META-INF/coremedia/livecontext-contentbeans.xml",
  })
  static class LocalConfig {
  }
}
