<#-- @ftlvariable name="self" type="com.coremedia.blueprint.common.contentbeans.CMDownload" -->

<#import "*/node_modules/@coremedia/ftl-utils/src/freemarkerLibs/utils.ftl" as utils />
<#import "../../freemarkerLibs/defaultTeaser.ftl" as defaultTeaser />

<#assign renderTeaserTitle=cm.localParameters().renderTeaserTitle!true />
<#assign renderTeaserText=cm.localParameters().renderTeaserText!true />
<#assign link=(cm.localParameters().renderLink!true)?then(cm.getLink(self.target!cm.UNDEFINED), "") />
<#assign title=renderTeaserTitle?then(self.teaserTitle!"", "") />
<#assign text=renderTeaserText?then(self.teaserText!"", "") />

<#assign titleHtml>
  <#if link?has_content && renderTeaserTitle?has_content>
    <i class="glyphicon glyphicon-download" aria-hidden="true"></i>
  </#if>
  ${title}
</#assign>

<#assign textHtml>
  <#if self.data?has_content>
    <span<@preview.metadata "properties.data" />>(${cm.getLink(self)?keep_after_last(".")?keep_before("?") + ", "} ${bp.getDisplaySize(self.data.size)})</span>
    <br>
  </#if>
  <@utils.renderWithLineBreaks text=bp.truncateText(text, bp.setting(self, "teaser.max.length", 200)) />
</#assign>

<@defaultTeaser.renderCaption title=titleHtml?no_esc
                              text=textHtml?no_esc
                              link=link
                              openInNewTab=self.openInNewTab
                              ctaButtons=self.callToActionSettings
                              teaserBlockClass=cm.localParameters().teaserBlockClass!cm.UNDEFINED
                              metadataTitle=["properties.title"]
                              metadataText=["properties.text"] />
