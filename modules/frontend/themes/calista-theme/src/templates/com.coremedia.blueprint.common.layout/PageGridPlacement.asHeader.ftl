<#-- @ftlvariable name="self" type="com.coremedia.blueprint.common.layout.PageGridPlacement" -->

<#assign numberOfItems=self.items?size />
<#assign localizations=cmpage.content.localizations![] />
<#assign cartAction=bp.setting(self,"cartAction", {})/>
<#assign searchAction=bp.setting(self,"shopSearchAction", {})/>

<div class="container">
  <header id="cm-${self.name!""}" class="cm-header navbar">
    <#-- mobile hamburger menu -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed pull-left" data-toggle="collapse" data-target="#navbar" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <#-- logo -->
      <a class="cm-header__logo navbar-brand" href="${cm.getLink(cmpage.navigation.rootNavigation!cm.UNDEFINED)}"></a>
    </div>

    <#-- cart widget -->
    <#if cartAction?has_content>
      <div class="cm-header__cart navbar-right" <@preview.metadata [cartAction.content]/>>
        <@cm.include self=cartAction view="asHeader" />
      </div>
    </#if>

    <#-- search widget -->
    <#if searchAction?has_content>
      <div class="cm-search__open-mobile-search-button">
        <span></span>
      </div>
      <div id="cmSearchWrapper" class="cm-search navbar-form navbar-right" <@preview.metadata [searchAction.content]/>>
        <button type="button" class="navbar-toggle pull-left cm-search-form__close">
          <span class="sr-only">${cm.getMessage('search_close')}</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <#-- substitute to CommerceSearchActionState -->
        <#assign substitution=cm.substitute(searchAction.id!"", searchAction) />
        <@cm.include self=substitution view="asHeader" params={
          "formMethod": "POST",
          "renderAsPopup": false
        }/>
      </div>
    </#if>

    <div id="navbar" class="cm-header-navbar collapse navbar-collapse navbar-right">
      <ul class="nav navbar-nav">

        <#-- login/logout -->
        <li class="cm-header__login" data-cm-loginstatus="${lc.getStatusUrl()}">
          <a id="cm-login" class="cm-header__login-status" href="${lc.getLoginFormUrl()}" title="${cm.getMessage("login_title")}">${cm.getMessage("login_title")}</a>
          <a id="cm-logout" class="cm-header__login-status" href="${lc.getLogoutUrl()}" title="${cm.getMessage("logout_title")}">${cm.getMessage("logout_title")}</a>
        </li>

        <#-- navigation -->
        <li class="cm-header-navbar__divider"></li>
          <@cm.include self=cmpage view="navigation"/>
        <li class="cm-header-navbar__divider"></li>

        <#-- language/country chooser widget -->
        <#if (localizations?size > 1) >
          <li class="dropdown cm-language-chooser">
            <a href="#" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              ${cmpage.locale.displayCountry} (${cmpage.locale.language?upper_case})
            </a>
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              <span class="caret"></span>
            </a>
            <#list localizations>
              <ul class="dropdown-menu">
                <#items as localization>
                  <#assign variantLink=cm.getLink(localization)!"" />
                  <#if localization.locale != cmpage.content.locale && variantLink?has_content>
                    <li><a href="${variantLink}">${localization.locale.displayCountry} (${localization.locale.language?upper_case})</a></li>
                  </#if>
                </#items>
              </ul>
            </#list>
          </li>
        </#if>

        <#-- additional header items -->
        <#if (numberOfItems > 0)>
          <#list self.items![] as item>
            <li class="cm-header__item" <@preview.metadata [cmpage.context.getContentId(), bp.getPlacementPropertyName(self)!"",bp.getPlacementHighlightingMetaData(self)!"", item.content]/>>
              <@cm.include self=item view="asHeader" />
            </li>
          </#list>
        </#if>
      </ul>
    </div>
  </header>
</div>
