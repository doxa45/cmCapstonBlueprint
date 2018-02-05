<#-- @ftlvariable name="self" type="com.coremedia.livecontext.context.LiveContextNavigation" -->
<#assign blockClass=cm.localParameters().blockClass!"cm-teaser--megamenu" />

<#if self.picture?has_content>
  <#assign picture=self.picture/>
<#elseif self.category.picture?has_content>
  <#assign picture=bp.createBeanFor(self.category.picture)/>
<#elseif self.category.catalogPicture?has_content>
  <#assign picture=bp.createBeanFor(self.category.catalogPicture)/>
</#if>
<@bp.responsiveImage self=picture!cm.UNDEFINED classPrefix=blockClass limitAspectRatios=bp.setting(cmpage.navigation, "default_aspect_ratios_for_megamenu_teaser", [])/>
