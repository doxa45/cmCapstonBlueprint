package com.coremedia.ecommerce.studio.rest;

import com.coremedia.ecommerce.studio.rest.model.ChildRepresentation;
import com.coremedia.ecommerce.studio.rest.model.Store;
import com.coremedia.livecontext.ecommerce.asset.AssetService;
import com.coremedia.livecontext.ecommerce.augmentation.AugmentationService;
import com.coremedia.livecontext.ecommerce.catalog.Category;
import com.coremedia.livecontext.ecommerce.common.CommerceBean;
import com.coremedia.livecontext.ecommerce.common.CommerceConnection;
import com.coremedia.livecontext.ecommerce.common.StoreContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A catalog {@link Category} object as a RESTful resource.
 */
@Produces(MediaType.APPLICATION_JSON)
@Path(CategoryResource.LIVECONTEXT_CATEGORY_SITE_ID_WORKSPACE_ID_ID)
public class CategoryResource extends CommerceBeanResource<Category> {

  /**
   * The Studio internal logical ID of the root category.
   */
  static final String ROOT_CATEGORY_ROLE_ID = "ROOT";
  public static final String LIVECONTEXT_CATEGORY_SITE_ID_WORKSPACE_ID_ID = "livecontext/category/{siteId:[^/]+}/{workspaceId:[^/]+}/{id:.+}";

  @Override
  public CategoryRepresentation getRepresentation() {
    CategoryRepresentation categoryRepresentation = new CategoryRepresentation();
    fillRepresentation(categoryRepresentation);
    return categoryRepresentation;
  }

  protected void fillRepresentation(CategoryRepresentation representation) {
    super.fillRepresentation(representation);
    Category entity = getEntity();
    representation.setName(entity.getName());
    String shortDescription = entity.getShortDescription().asXml();
    representation.setShortDescription(shortDescription);
    String longDescription = entity.getLongDescription().asXml();
    representation.setLongDescription(longDescription);
    representation.setThumbnailUrl(RepresentationHelper.modifyAssetImageUrl(entity.getThumbnailUrl(), getContentRepositoryResource().getEntity()));
    representation.setParent(entity.getParent());
    representation.setSubCategories(entity.getChildren());
    representation.setProducts(entity.getProducts());
    representation.setStore(new Store(entity.getContext()));
    representation.setDisplayName(entity.getDisplayName());

    List<CommerceBean> children = new ArrayList<>();
    children.addAll(representation.getSubCategories());
    children.addAll(representation.getProducts());
    representation.setChildren(children);
    // get visuals directly via AssetService to avoid fallback to default picture
    AssetService assetService = getConnection().getAssetService();
    if (null != assetService) {
      representation.setVisuals(assetService.findVisuals(entity.getReference(), false));
    }
    representation.setPictures(entity.getPictures());
    representation.setDownloads(entity.getDownloads());

    Map<String, ChildRepresentation> result = new LinkedHashMap<>();
    for (CommerceBean child : children) {
      ChildRepresentation childRepresentation = new ChildRepresentation();
      childRepresentation.setChild(child);
      if (child instanceof Category) {
        childRepresentation.setDisplayName(((Category) child).getDisplayName());
      } else {
        childRepresentation.setDisplayName(child.getExternalId());
      }

      result.put(child.getId(), childRepresentation);
    }
    representation.setChildrenByName(result);

    representation.setContent(getContent());
  }

  @Override
  protected Category doGetEntity() {
    CommerceConnection commerceConnection = getConnection();
    String id = getId();
    if (ROOT_CATEGORY_ROLE_ID.equals(id)) {
      return commerceConnection.getCatalogService().findRootCategory();
    }

    String categoryId = commerceConnection.getIdProvider().formatCategoryId(id);
    return commerceConnection.getCatalogService().findCategoryById(categoryId);
  }

  @Override
  public void setEntity(Category category) {
    setId(category.isRoot() ? ROOT_CATEGORY_ROLE_ID : category.getExternalId());
    StoreContext context = category.getContext();
    setSiteId(context.getSiteId());
    setWorkspaceId(context.getWorkspaceId());
  }

  @Override
  @Autowired(required = false)
  @Qualifier("categoryAugmentationService")
  public void setAugmentationService(AugmentationService augmentationService) {
    super.setAugmentationService(augmentationService);
  }

}
