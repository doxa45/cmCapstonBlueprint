package com.coremedia.ecommerce.studio.rest;

import com.coremedia.ecommerce.studio.rest.model.Workspaces;
import com.coremedia.livecontext.ecommerce.workspace.WorkspaceService;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The resource handles the top level store node "Segments".
 * It is not showed in the catalog tree but used to invalidate the list of available commerce segments
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("livecontext/workspaces/{siteId:[^/]+}")
public class WorkspacesResource extends AbstractCatalogResource<Workspaces> {

  @Override
  public WorkspacesRepresentation getRepresentation() {
    WorkspacesRepresentation representation = new WorkspacesRepresentation();
    fillRepresentation(representation);
    return representation;
  }

  private void fillRepresentation(WorkspacesRepresentation representation) {
    Workspaces workspaces = getEntity();

    if (workspaces == null) {
      LOG.warn("Error loading workspaces bean");
      throw new CatalogRestException(Response.Status.NOT_FOUND, CatalogRestErrorCodes.COULD_NOT_FIND_CATALOG_BEAN, "Could not load workspaces bean");
    }

    representation.setId(workspaces.getId());
    WorkspaceService workspaceService = getConnection().getWorkspaceService();
    if (workspaceService != null) {
      representation.setWorkspaces(workspaceService.findAllWorkspaces());
    }
  }

  @Override
  protected Workspaces doGetEntity() {
    return new Workspaces(getStoreContext());
  }

  @Override
  public void setEntity(Workspaces workspaces) {
    setSiteId(workspaces.getContext().getSiteId());
  }
}
