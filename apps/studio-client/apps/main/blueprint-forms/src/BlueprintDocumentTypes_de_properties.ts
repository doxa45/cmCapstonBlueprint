import CoreIcons_properties from "@coremedia/studio-client.core-icons/CoreIcons_properties";
import ResourceBundleUtil from "@jangaroo/runtime/l10n/ResourceBundleUtil";
import BlueprintDocumentTypes_properties from "./BlueprintDocumentTypes_properties";

/**
 *
 * Display labels and tool tips for Document Types
 *
 * @see BlueprintDocumentTypes_properties#INSTANCE
 */
ResourceBundleUtil.override(BlueprintDocumentTypes_properties, {
  CMAbstractCode_text: "Code",
  CMAbstractCode_toolTip: "Basistyp für Code-Objekte",
  CMAbstractCode_code_text: "Code",
  CMAbstractCode_code_toolTip: "Code-Daten",
  CMAbstractCode_code_emptyText: "Geben Sie hier Code-Daten ein.",
  CMAbstractCode_dataUrl_text: "Daten-URL",
  CMAbstractCode_dataUrl_toolTip: "URL der Datenquelle",
  CMAbstractCode_dataUrl_emptyText: "Geben Sie hier die URL der Datenquelle ein.",
  CMAbstractCode_description_text: "Beschreibung",
  CMAbstractCode_description_toolTip: "Beschreibung",
  CMAbstractCode_description_emptyText: "Geben Sie hier die Beschreibung ein.",
  CMAbstractCode_ieExpression_text: "IE include Ausdruck",
  CMAbstractCode_ieExpression_toolTip: "Internet Explorer-spezifischer Include Ausdruck, z. B. \"if lte IE 7\"",
  CMAbstractCode_ieExpression_emptyText: "",
  CMAbstractCode_ieRevealed_text: "",
  CMAbstractCode_ieRevealed_toolTip: "",
  CMAbstractCode_ieRevealed_true_text: "",
  CMAbstractCode_include_text: "Benötigter Code",
  CMAbstractCode_include_toolTip: "Benötigter Code, der zusätzlich geladen werden soll",
  CMJavaScript_inHead_text: "Im HTML Head einbinden",
  CMJavaScript_inHead_toolTip: "Diese Einstellung bewirkt die Einbindung im HTML Head.",
  CMJavaScript_inHead_true_text: "Im HTML Head einbinden",
  CMAction_text: "Aktion",
  CMAction_toolTip: "Ein Inhalt, welcher eine Aktion repräsentiert",
  CMAction_id_text: "ID",
  CMAction_id_emptyText: "Geben Sie hier die ID ein.",
  CMAction_type_text: "Typ",
  CMAction_type_emptyText: "Geben Sie hier den Aktionstyp ein.",
  CMArticle_text: "Artikel",
  CMArticle_toolTip: "Artikel",
  CMArticle_title_text: "Artikel-Titel",
  CMArticle_title_toolTip: "Titel des Artikels",
  CMArticle_title_emptyText: "Geben Sie hier den Artikeltitel ein.",
  CMArticle_detailText_text: "Artikel-Text",
  CMArticle_detailText_toolTip: "Text des Artikels",
  CMArticle_detailText_emptyText: "Geben Sie hier den Text des Artikels ein.",
  CMAudio_text: "Audio",
  CMAudio_toolTip: "Objekt mit Audio-Inhalten",
  CMAudio_data_helpText: "Sie können ein Audio-Objekt hochladen.",
  CMAudio_dataUrl_text: "Daten-URL (optional)",
  CMAudio_dataUrl_toolTip: "URL für das Audio-Objekt",
  CMAudio_dataUrl_emptyText: "Geben Sie hier die URL für das Audio-Objekt ein.",
  CMChannel_text: "Seite",
  CMChannel_toolTip: "Seite",
  CMChannel_footer_text: "Fußzeile",
  CMChannel_footer_toolTip: "Fußzeilen-Objekte",
  CMChannel_footer_emptyText: "Ziehen Sie Inhalte aus der Bibliothek hierher.",
  CMChannel_header_text: "Kopfzeile",
  CMChannel_header_toolTip: "Kopfzeilen-Objekte",
  CMChannel_header_emptyText: "Ziehen Sie Inhalte aus der Bibliothek hierher.",
  CMChannel_picture_text: "Bild der Seite",
  CMChannel_picture_toolTip: "Bild der Seite",
  CMChannel_sidebarList_text: "Sidebar-Liste",
  CMChannel_sidebarList_emptyText: "Ziehen Sie Inhalte aus der Bibliothek hierher.",
  CMChannel_primarynav_text: "Primäre Navigation",
  CMChannel_primarynav_emptyText: "Ziehen Sie Inhalte aus der Bibliothek hierher.",
  CMChannel_subnavList_text: "Subnavigations-Liste",
  CMChannel_subnavList_emptyText: "Ziehen Sie Inhalte aus der Bibliothek hierher.",
  CMChannel_title_text: "Seitentitel",
  CMChannel_title_toolTip: "Titel der Seite",
  CMChannel_title_emptyText: "Geben Sie hier den Seitentitel ein.",
  "CMChannel_contextSettings.userFeedback.enabled_text": "Benutzerfeedback",
  "CMChannel_contextSettings.userFeedback.enabled_true_text": "ein",
  "CMChannel_contextSettings.exampleStringProperty_text": "Beispiel Text Struct",
  "CMChannel_contextSettings.exampleStringProperty_emptyText": "Bitte geben Sie eine Beschreibung an.",
  CMChannel_placement_text: "Bereiche",
  "CMChannel_placement.placements_2.layout_text": "Layout",
  "CMChannel_placement.placements_2.placements.content/{placementId:[0-9]+}.extendedItems.{index:[0-9]+}.visibleFrom_text": "Sichtbar von",
  "CMChannel_placement.placements_2.placements.content/{placementId:[0-9]+}.extendedItems.{index:[0-9]+}.visibleTo_text": "Sichtbar bis",
  CMCollection_text: "Sammlung",
  CMCollection_toolTip: "Sammlung von Inhalten",
  CMCollection_items_text: "Objekte",
  CMCollection_items_toolTip: "Objekte in dieser Sammlung",
  CMCollection_title_text: "Sammlungstitel",
  CMCollection_title_toolTip: "Titel der Sammlung",
  CMCollection_title_emptyText: "Geben Sie hier den Sammlungstitel ein.",
  CMContext_text: "Navigationskontext",
  CMContext_toolTip: "Navigationskontext",
  CMCSS_text: "CSS",
  CMCSS_toolTip: "CSS (Cascading Style Sheets)",
  CMCSS_code_text: "CSS-Daten",
  CMCSS_code_toolTip: "CSS-Daten",
  CMCSS_ieRevealed_text: "IE Revealed",
  CMCSS_ieRevealed_toolTip: "CSS ist sichtbar für Internet Explorer.",
  CMCSS_ieRevealed_true_text: "CSS ist sichtbar für Internet Explorer",
  CMCSS_media_text: "CSS Media Attribut",
  CMCSS_media_toolTip: "Optionales CSS Media-Attribut, z. B. screen",
  CMCSS_include_text: "Benötigtes CSS",
  CMCSS_include_toolTip: "Weitere CSS-Objekte, die benötigt werden. z. B. framework",
  Dictionary_text: "Dictionary",
  Dictionary_toolTip: "Dictionary",
  CMDownload_text: "Download",
  CMDownload_toolTip: "Allgemeiner Download",
  CMDownload_data_text: "Binärdaten",
  CMDownload_data_toolTip: "Binärdaten",
  CMDownload_filename_text: "Dateiname",
  CMDownload_filename_toolTip: "Der Name der hochgeladenen Datei",
  CMDownload_filename_emptyText: "",
  CMDownload_title_text: "Download-Titel",
  CMDownload_title_toolTip: "Titel des Downloads",
  CMDownload_title_emptyText: "Geben Sie hier den Download-Titel ein.",
  CMDynamicList_text: "Dynamische Liste",
  CMDynamicList_toolTip: "Liste mit dynamisch berechneten Inhalten",
  CMDynamicList_maxLength_text: "Anzahl Einträge",
  CMDynamicList_maxLength_toolTip: "Maximale Anzahl von Inhalten in der Liste",
  CMDynamicList_maxLength_emptyText: "Geben Sie hier die maximale Anzahl von Inhalten in der Liste ein.",
  CMExternalLink_text: "Externer Link",
  CMExternalLink_toolTip: "Externer Link",
  CMExternalLink_url_text: "URL",
  CMExternalLink_url_toolTip: "URL für den externen Link",
  CMExternalLink_url_emptyText: "Geben Sie hier die URL für den externen Link ein.",
  CMExternalLink_openInNewTab_text: "In neuem Tab/Fenster öffnen",
  CMExternalLink_openInNewTab_toolTip: "Abhängig von der Browser-Einstellung wird der externe Link in einem neuem Tab oder Fenster geöffnet.",
  CMExternalLink_openInNewTab_true_text: "In neuem Tab/Fenster öffnen",
  CMExternalChannel_legacy_children_text: "Navigationsunterknoten (Altdaten, bitte entfernen)",
  CMFolderProperties_text: "Ordnereigenschaften",
  CMFolderProperties_toolTip: "Ordnereigenschaften",
  CMFolderProperties_contexts_text: "Kontexte",
  CMFolderProperties_contexts_toolTip: "Kontexte",
  CMFolderProperties_contexts_emptyText: "Ziehen Sie Inhalte aus der Bibliothek hierher.",
  CMGallery_text: "Galerie",
  CMGallery_toolTip: "Galerie",
  CMGallery_items_text: "Galeriebilder",
  CMGallery_items_toolTip: "Inhalte in dieser Galerie",
  CMGallery_items_emptyText: "Ziehen Sie Bilder aus der Bibliothek hierher.",
  CMGallery_title_text: "Galerie-Titel",
  CMGallery_title_toolTip: "Titel der Galerie",
  CMGallery_title_emptyText: "Geben Sie hier den Galerietitel ein.",
  CMGallery_detailText_text: "Galerie-Text",
  CMSpinner_text: "360°-Ansicht",
  CMSpinner_toolTip: "360°-Ansicht",
  CMSpinner_sequence_text: "Bildfolge",
  CMSpinner_sequence_toolTip: "Bildfolge dieser 360°-Ansicht",
  CMSpinner_sequence_emptyText: "Ziehen Sie Bilder aus der Bibliothek hierher.",
  CMSpinner_title_text: "Titel",
  CMSpinner_title_toolTip: "Titel der 360°-Ansicht",
  CMSpinner_title_emptyText: "Geben Sie hier den 360°-Ansichtstitel ein.",
  CMSpinner_detailText_text: "Beschriftung",
  CMSpinner_detailText_toolTip: "Beschriftung der 360°-Ansicht",
  CMSpinner_detailText_emptyText: "Geben Sie hier die Beschriftung ein.",
  CMHasContexts_text: "Inhalt mit Navigationskontext",
  CMHasContexts_toolTip: "Ein Inhalt, welcher einen oder mehrere Navigationskontexte hat",
  CMHasContexts_contexts_text: "Navigationskontexte",
  CMHasContexts_contexts_toolTip: "Navigationskontexte, in denen dieses Objekt verfügbar ist.",
  CMHasContexts_contexts_emptyText: "Ziehen Sie Inhalte aus der Bibliothek hierher.",
  CMHTML_text: "HTML-Fragment",
  CMHTML_toolTip: "Statisches HTML-Fragment zum Einbinden von beliebigen HTML-Daten",
  CMHTML_data_text: "HTML-Daten",
  CMHTML_data_toolTip: "HTML-Daten",
  CMImage_text: "Technisches Bild",
  CMImage_toolTip: "Ein technisches Bild ohne redaktionelle Daten",
  CMImage_data_text: "Bilddaten",
  CMImage_data_toolTip: "Bilddaten",
  CMImage_description_text: "Beschreibung",
  CMImage_description_toolTip: "Beschreibungstext",
  CMImage_description_emptyText: "Geben Sie hier den Beschreibungstext ein.",
  CMImageMap_pictures_text: "Bild",
  "CMImageMap_localSettings.overlay.displayTitle_true_text": "Zeige Titel/Name",
  "CMImageMap_localSettings.overlay.displayTitle_text": "Zeige Titel/Name",
  "CMImageMap_localSettings.overlay.displayShortText_true_text": "Zeige Kurzbeschreibung",
  "CMImageMap_localSettings.overlay.displayShortText_text": "Zeige Kurzbeschreibung",
  "CMImageMap_localSettings.overlay.displayPicture_true_text": "Zeige Bild",
  "CMImageMap_localSettings.overlay.displayPicture_text": "Zeige Bild",
  CMImageMap_overlayConfiguration_title: "Overlay/Popup Konfiguration",
  CMInteractive_text: "Interaktiv",
  CMInteractive_toolTip: "Objekt mit interaktiven Inhalten",
  CMInteractive_data_helpText: "Sie können ein interaktives Objekt hochladen.",
  CMInteractive_dataUrl_text: "Daten-URL (optional)",
  CMInteractive_dataUrl_toolTip: "URL für das interaktive Objekt",
  CMInteractive_dataUrl_emptyText: "Geben Sie hier die URL für das interaktive Objekt ein.",
  CMJavaScript_text: "JavaScript",
  CMJavaScript_toolTip: "JavaScript-Code",
  CMJavaScript_code_text: "JavaScript-Daten",
  CMJavaScript_code_toolTip: "JavaScript-Daten",
  CMJavaScript_include_text: "Benötigte JavaScript-Objekte",
  CMJavaScript_include_toolTip: "Benötigte JavaScript-Objekte",
  CMLinkable_text: "Verlinkbares Objekt",
  CMLinkable_toolTip: "Ein Objekt, das verlinkt werden kann und eine Segment-basierte URI hat.",
  CMLinkable_keywords_text: "Freie Schlagworte",
  CMLinkable_keywords_emptyText: "Geben Sie hier die freien Schlagworte ein.",
  CMLinkable_keywords_toolTip: "Freie Schlagworte z. B. für SEO Optimierungen",
  CMLinkable_linkedSettings_text: "Verlinkte Einstellungen",
  CMLinkable_linkedSettings_toolTip: "Verlinkte Einstellungen",
  CMLinkable_linkedSettings_emptyText: "Ziehen Sie Einstellungen aus der Bibliothek hierher.",
  CMLinkable_localSettings_text: "Lokale Einstellungen",
  CMLinkable_localSettings_toolTip: "Lokale Einstellungen",
  CMLinkable_localSettings_emptyText: "Fügen Sie hier Struct XML ein.",
  CMLinkable_locationTaxonomy_text: "Orte",
  CMLinkable_locationTaxonomy_toolTip: "Orte",
  CMLinkable_locationTaxonomy_emptyText: "Ziehen Sie Inhalte aus der Bibliothek hierher.",
  CMLinkable_segment_text: "URL-Segment",
  CMLinkable_segment_toolTip: "URL Segment, das in Links auf den Inhalt benutzt werden soll. Wenn dieser nicht gesetzt ist, wird der Inhaltsname verwendet.",
  CMLinkable_segment_emptyText: "Geben Sie hier das URL-Segment ein.",
  CMLinkable_subjectTaxonomy_text: "Themen",
  CMLinkable_subjectTaxonomy_toolTip: "Themen",
  CMLinkable_subjectTaxonomy_emptyText: "Ziehen Sie Inhalte aus der Bibliothek hierher.",
  CMLinkable_title_text: "Titel",
  CMLinkable_title_toolTip: "Titel",
  CMLinkable_title_emptyText: "Geben Sie hier den Titel ein.",
  CMLinkable_validFrom_text: "Gültig ab",
  CMLinkable_validFrom_toolTip: "Zeitpunkt des Beginns der Gültigkeit",
  CMLinkable_validFrom_emptyText: "Gültig ab",
  CMLinkable_validTo_text: "Gültig bis",
  CMLinkable_validTo_toolTip: "Zeitpunkt des Endes der Gültigkeit",
  CMLinkable_validTo_emptyText: "Gültig bis",
  CMLinkable_viewtype_text: "Anzeigevariante",
  CMLinkable_viewtype_toolTip: "Anzeigevariante ist eine spezielle Ansicht, die vom Standard abweicht.",
  CMLinkable_viewtype_emptyText: "Standard-Anzeigevariante",
  CMLinkable_extDisplayedDate_text: "Angezeigtes Datum",
  CMLinkable_externally_visible_date_text: "Angezeigtes Datum",
  CMLinkable_externally_visible_date_use_publication_date_text: "Publikationsdatum",
  CMLinkable_externally_visible_date_use_custom_date_text: "Anderes Datum",
  CMLinkable_htmlTitle_text: "HTML Titel",
  CMLinkable_htmlTitle_toolTip: "HTML Titel für den HTML Head Bereich",
  CMLinkable_htmlTitle_emptyText: "Geben Sie hier den HTML Titel ein.",
  CMLinkable_htmlDescription_text: "HTML Meta-Beschreibung",
  CMLinkable_htmlDescription_toolTip: "HTML Meta Tag Beschreibung für den HTML Head Bereich",
  CMLinkable_htmlDescription_emptyText: "Geben Sie hier die HTML Meta-Beschreibung ein.",
  CMLinkable_validity_text: "Gültigkeit",
  CMLinkable_pagegridLayout_text: "Bereichsvarianten",
  CMLocalized_text: "Lokalisierbares CoreMedia-Blueprint-Objekt",
  CMLocalized_toolTip: "Lokalisierbares CoreMedia-Blueprint-Objekt",
  CMLocalized_editorialState_text: "Redaktioneller Status",
  CMLocalized_editorialState_toolTip: "Redaktioneller Status",
  CMLocalized_editorialState_emptyText: "Benutzen Sie den Auswahldialog, um den Status zu setzen.",
  CMLocalized_locale_text: "Locale",
  CMLocalized_locale_toolTip: "Locale des Inhalts (Sprache, optional mit Land und Varianten)",
  CMLocalized_locale_emptyText: "Geben Sie hier die Locale des Inhalts ein.",
  CMLocalized_master_text: "Master",
  CMLocalized_master_toolTip: "Master-Inhalt, von dem dieser Inhalt erzeugt wurde",
  CMLocalized_masterVersion_text: "Version des Masters",
  CMLocalized_masterVersion_toolTip: "Version des Masters, von dem dieser Inhalt erzeugt wurde",
  CMLocalized_masterVersion_emptyText: "Geben Sie hier die Version des Master-Inhalts ein.",
  CMLocalized_ignoreUpdates_text: "Synchronisierung",
  CMLocalized_ignoreUpdates_toolTip: "Diesen Inhalt weiterhin mit dem Master-Inhalt synchronisieren?",
  CMLocalized_resourceBundles2_text: "Ressourcenbündel",
  CMLocalized_resourceBundles2_toolTip: "Ressourcenbündel",
  CMLocalized_resourceBundles2_emptyText: "Ziehen Sie Ressourcenbündel aus der Bibliothek hierher.",
  "CMLocalized_com.coremedia.cms.editor.sdk.config.derivedContentsList_text": "Abgeleitete Inhalte",
  CMLocTaxonomy_text: "Orts-Schlagwort",
  CMLocTaxonomy_toolTip: "Orts-Schlagwort",
  CMLocTaxonomy_latitudeLongitude_text: "Längen- und Breitengrad",
  CMLocTaxonomy_postcode_text: "Postleitzahl",
  CMLocTaxonomy_postcode_toolTip: "Postleitzahl",
  CMLocTaxonomy_postcode_emptyText: "Geben Sie hier die Postleitzahl ein.",
  CMMail_text: "Mail",
  CMMail_toolTip: "Mail",
  CMMedia_text: "Media",
  CMMedia_toolTip: "Multimedia-Objekt",
  CMMedia_alt_text: "Alternativtext",
  CMMedia_alt_toolTip: "Alternativer Text, der bei Darstellungsproblemen angezeigt wird.",
  CMMedia_alt_emptyText: "Geben Sie hier einen alternativen Text ein.",
  CMMedia_caption_text: "Beschriftung",
  CMMedia_caption_toolTip: "Beschriftung des Multimedia-Objekts",
  CMMedia_copyright_text: "Urheberrecht",
  CMMedia_copyright_toolTip: "Hinweis zum Urheberrecht (Copyright)",
  CMMedia_copyright_emptyText: "Geben Sie hier einen Hinweis zum Urheberrecht (Copyright) ein.",
  CMMedia_data_text: "Daten",
  CMMedia_data_toolTip: "Daten des Multimedia-Objekts",
  CMMedia_description_text: "Beschreibung",
  CMMedia_description_toolTip: "Interne Beschreibung",
  CMMedia_description_emptyText: "Geben Sie hier die Beschreibung ein.",
  CMNavigation_text: "Navigationsknoten",
  CMNavigation_toolTip: "Navigationsknoten",
  CMNavigation_children_text: "Navigationsunterknoten",
  CMNavigation_children_toolTip: "Navigationsunterknoten",
  CMNavigation_css_text: "Benötigte CSS-Dateien",
  CMNavigation_css_toolTip: "Benötigte CSS-Dateien, die zusätzlich geladen werden sollen",
  CMNavigation_css_emptyText: "Ziehen Sie eine CSS-Datei aus der Bibliothek hierher.",
  //true text and text are kept intentionally same
  CMNavigation_hidden_text: "In Navigation und Sitemap ausblenden",
  CMNavigation_hidden_true_text: "In Navigation und Sitemap ausblenden",
  CMNavigation_hidden_toolTip: "Das Setzen dieser Eigenschaft blendet den Knoten in Navigation und Sitemap aus.",
  //true text and text are kept intentionally same
  CMNavigation_hiddenInSitemap_text: "Nur in Sitemap ausblenden",
  CMNavigation_hiddenInSitemap_true_text: "Nur in Sitemap ausblenden",
  CMNavigation_hiddenInSitemap_toolTip: "Das Setzen dieser Eigenschaft blendet den Knoten in der Sitemap aus.",
  CMNavigation_isRoot_text: "Als Seite verwenden",
  CMNavigation_isRoot_toolTip: "Den Navigationsknoten als Top-Level-Seite verwenden",
  CMNavigation_javaScript_text: "Benötigte JavaScripts",
  CMNavigation_javaScript_toolTip: "Benötigte JavaScripts, die zusätzlich geladen werden sollen",
  CMNavigation_javaScript_emptyText: "Ziehen Sie eine JavaScript-Datei aus der Bibliothek hierher.",
  CMNavigation_pageGrid_text: "Seitengerüst",
  CMNavigation_pageGrid_toolTip: "Seitengerüst",
  CMNavigation_theme_text: "Theme",
  CMNavigation_theme_toolTip: "Theme, das zum Rendern von HTML Seiten genommen wird",
  CMNavigation_theme_emptyText: "Ziehen Sie eine Theme-Datei aus der Bibliothek hierher.",
  CMObject_text: "CoreMedia-Blueprint-Objekt",
  CMObject_toolTip: "CoreMedia-Blueprint-Objekt",
  "CMObject_localSettings.fq.subjecttaxonomy_text": "Der Inhalt enthält eines der Schlagwörter",
  "CMObject_localSettings.fq.locationtaxonomy_text": "Den Inhalt für einen der folgenden Orte verorten",
  CMPerson_text: "Person",
  CMPerson_toolTip: "Person",
  CMPerson_icon: CoreIcons_properties.user,
  CMPerson_firstName_text: "Vorname",
  CMPerson_firstName_toolTip: "Vorname der Person",
  CMPerson_firstName_emptyText: "Geben Sie hier den Vornamen ein.",
  CMPerson_lastName_text: "Nachname",
  CMPerson_lastName_toolTip: "Nachname der Person",
  CMPerson_lastName_emptyText: "Geben Sie hier den Nachnamen ein.",
  CMPerson_displayName_text: "Anzeigename",
  CMPerson_displayName_toolTip: "Anzeigename der Person",
  CMPerson_displayName_emptyText: "Geben Sie hier den Anzeigenamen ein.",
  CMPerson_eMail_text: "E-Mail",
  CMPerson_eMail_toolTip: "E-Mail Adresse der Person",
  CMPerson_eMail_emptyText: "Geben Sie hier die E-Mail Adresse der Person ein.",
  CMPerson_organization_text: "Unternehmen",
  CMPerson_organization_toolTip: "Unternehmen der Person",
  CMPerson_organization_emptyText: "Geben Sie hier das Unternehmen der Person ein.",
  CMPerson_jobTitle_text: "Job-Titel",
  CMPerson_jobTitle_toolTip: "Job-Titel der Person",
  CMPerson_jobTitle_emptyText: "Geben Sie hier den Job-Titel der Person ein.",
  CMPerson_teaserText_text: "Kurzbiografie",
  CMPerson_teaserText_toolTip: "Kurzbiografie der Person",
  CMPerson_teaserText_emptyText: "Geben Sie hier die Kurzbiografie ein",
  CMPerson_detailText_text: "Biografie",
  CMPerson_detailText_toolTip: "Biografie der Person",
  CMPerson_detailText_emptyText: "Geben Sie hier die Biografie ein.",
  CMPicture_text: "Bild",
  CMPicture_toolTip: "Redaktionelles Bild",
  "CMPicture_localSettings.disableCropping_text": "Originalbild benutzen",
  "CMPicture_localSettings.disableCropping_true_text": "Originalbild benutzen",
  CMPicture_data_text: "Bild",
  CMPicture_data_toolTip: "Binäre Bilddaten",
  CMPicture_title_text: "Bildtitel",
  CMPicture_title_toolTip: "Titel des Bildes",
  CMPicture_title_emptyText: "Geben Sie hier den Bildtitel ein.",
  CMPicture_detailText_text: "Beschriftung",
  CMPicture_detailText_toolTip: "Beschriftung des Bildes",
  CMPicture_detailText_emptyText: "Geben Sie hier die Beschriftung ein.",
  CMPlaceholder_text: "Platzhalter",
  CMPlaceholder_toolTip: "Ein Inhalt, das einen Platzhalter repräsentiert",
  CMPlaceholder_id_text: "ID",
  CMPlaceholder_id_emptyText: "Geben Sie hier die ID des Platzhalters ein.",
  CMPlaceholder_viewtype_text: "Funktionsvariante",
  CMPlaceholder_viewtype_toolTip: "Funktionsvariante ist eine spezielle Ansicht.",
  CMPlaceholder_viewtype_emptyText: "Ohne Funktion",
  Query_text: "Query",
  Query_toolTip: "Query Dokument",
  CMQueryList_text: "Suchliste",
  CMQueryList_toolTip: "Suchliste",
  "CMQueryList_localSettings.limit_emptyText": "Anzahl eingeben",
  "CMQueryList_localSettings.fq.documents_text": "Die Inhalte befinden sich in einem der Kontexte",
  "CMQueryList_localSettings.fq.authors_text": "Die Inhalte haben einen der folgenden Autoren",
  CMQueryList_items_title: "Inhalte mit Festen Positionen",
  CMQueryList_extendedItems_text: "Inhalte mit Festen Positionen",
  CMQueryList_extendedItems_title: "Inhalte mit Festen Positionen",
  CMQueryList_fixedIndex_title: "Feste Position",
  CMQueryList_fixedIndex_emptyText: "Position",
  CMQueryList_fixedIndex_label: "An welcher festen Position in der Suchliste soll dieser Inhalt erscheinen?",
  CMSettings_text: "Einstellungen",
  CMSettings_toolTip: "Einstellungsinhalt",
  CMSettings_settings_text: "Einstellungen",
  CMSettings_settings_toolTip: "Einstellungs-Struct",
  CMSettings_settings_emptyText: "Geben Sie hier Einstellungen als Struct-XML ein.",
  Preferences_text: "Einstellungen",
  EditorPreferences_text: "Benutzerprofil",
  EditorPreferences_data_toolTip: "Benutzerprofil Einstellungen-Struct",
  EditorPreferences_data_emptyText: "Machen Sie hier Profileinstellungen für den Benutzer.",
  CMSite_text: "Site-Definition",
  CMSite_toolTip: "Definition der Homepage, des Ordners und der Lokalisierung einer Site",
  CMSite_root_text: "Homepage",
  CMSite_root_toolTip: "Homepage der Site",
  CMSite_id_text: "ID",
  CMSite_id_toolTip: "Stabile ID der Site",
  CMSite_name_text: "Name",
  CMSite_name_toolTip: "Der Name der Site",
  CMSite_name_emptyText: "Geben Sie hier den Namen der Site ein.",
  CMSite_locale_text: "Locale",
  CMSite_locale_toolTip: "Sprache der Site, optional mit Land und Varianten",
  CMSite_locale_emptyText: "Geben Sie hier die Locale der Site ein.",
  CMSite_master_text: "Master",
  CMSite_master_toolTip: "Master-Site, von der diese Site abgeleitet wurde",
  CMSite_siteManagerGroup_text: "Site-Manager-Gruppen",
  CMSite_siteManagerGroup_toolTip: "Gruppen, welche für diese Site zuständig sind, separiert mit Kommas.",
  CMSite_siteManagerGroup_emptyText: "Geben Sie hier den Namen der für diese Site zuständigen Benutzergruppen an.",
  CMSitemap_text: "Sitemap",
  CMSitemap_toolTip: "Ein Inhalt, welcher eine Sitemap repräsentiert",
  CMSitemap_root_text: "Wurzelseite",
  CMSitemap_root_toolTip: "Die Seite ist die Wurzel der zu generierenden Sitemap.",
  CMSitemap_title_text: "Sitemap Titel",
  CMSitemap_title_toolTip: "Titel der Sitemap",
  CMSitemap_title_emptyText: "Geben Sie hier den Sitemap Titel ein.",
  "CMSitemap_localSettings.sitemap_depth_text": "Anzeigetiefe der Sitemap",
  CMSitemap_localSettings_sitemap_depth_text: "Anzeigetiefe der Sitemap",
  "CMSitemap_localSettings.sitemap_depth_emptyText": "Geben Sie hier die Anzeigetiefe der Sitemap ein.",
  CMSymbol_text: "Symbol",
  CMSymbol_toolTip: "Symbol mit einem festgelegten bekannten Namen als Wert",
  CMSymbol_description_text: "Beschreibung",
  CMSymbol_description_toolTip: "Textuelle Beschreibung des Symbols",
  CMSymbol_description_emptyText: "Geben Sie hier die Beschreibung ein.",
  CMSymbol_icon_text: "Bild",
  CMSymbol_icon_toolTip: "Ein Bild für das Symbol",
  CMTaxonomy_text: "Schlagwort",
  CMTaxonomy_toolTip: "Schlagworte",
  CMTaxonomy_children_text: "Untergeordnete Schlagworte",
  CMTaxonomy_children_toolTip: "Untergeordnete Schlagworte",
  CMTaxonomy_externalReference_text: "Externe Referenz",
  CMTaxonomy_externalReference_toolTip: "Externe Referenz",
  CMTaxonomy_externalReference_emptyText: "Geben Sie hier eine externe Referenz ein.",
  CMTaxonomy_value_text: "Titel",
  CMTaxonomy_value_toolTip: "Name des Schlagwortes",
  CMTaxonomy_value_emptyText: "Bitte geben Sie hier das Schlagwort ein.",
  CMTaxonomy_parent_text: "Übergeordnetes Schlagwort",
  CMTaxonomy_parent_toolTip: "Übergeordnetes Schlagwort",
  CMTeasable_text: "Inhalt mit Teaser",
  CMTeasable_toolTip: "Inhalt, der mit Hilfe von Teasern verlinkt werden kann.",
  CMTeasable_detailText_text: "Detailtext",
  CMTeasable_detailText_toolTip: "Detailtext des Teasers",
  CMTeasable_detailText_emptyText: "Geben Sie hier den Detailtext des Teasers ein.",
  //text and true text are kept same deliberately
  CMTeasable_notSearchable_text: "Von der Suche und der XML-Sitemap ausschließen",
  CMTeasable_notSearchable_true_text: "Von der Suche und der XML-Sitemap ausschließen",
  CMTeasable_pictures_text: "Bilder und andere Medien",
  CMTeasable_pictures_emptyText: "Ziehen Sie Inhalte aus der Bibliothek hierher.",
  CMTeasable_related_text: "Verwandter Inhalt",
  CMTeasable_related_toolTip: "Verwandter Inhalt",
  CMTeasable_related_emptyText: "Ziehen Sie Inhalte aus der Bibliothek hierher.",
  CMTeasable_teaserText_text: "Teaser-Text",
  CMTeasable_teaserText_toolTip: "Teaser-Text",
  CMTeasable_teaserText_emptyText: "Geben Sie hier den Text des Teasers ein.",
  CMTeasable_teaserTitle_text: "Teaser-Titel",
  CMTeasable_teaserTitle_toolTip: "Titel des Teasers",
  CMTeasable_teaserTitle_emptyText: "Geben Sie hier den Teaser-Titel ein.",
  CMTeasable_authors_text: "Autoren",
  "CMTeasable_localSettings.teaserOverlay.style_text": "Teaser-Stil",
  "CMTeasable_localSettings.teaserSettings.renderLinkToDetailPage_text": "Link zur Detail-Seite rendern",
  CMTeasable_callToActionConfiguration_text: "Call-to-Action-Button",
  CMTeasable_callToActionConfiguration_enable_cta_text: "Zeige Call-to-Action Button an",
  CMTeasable_CTAText_text: "Text",
  CMTeasable_CTAText_emptyText: "Geben Sie eine Call-to-Action-Button Beschriftung ein.",
  CMTeasable_CTAHash_text: "Anker Name",
  CMTeasable_CTAHash_emptyText: "Geben Sie den Namen eines Sprungziels auf der Zielseite ein.",
  CMTeasable_CTAHash_helpText: "Wenn Sie einen Anker erstellen, sind folgende Dinge zu berücksichtigen: <br/><ul><li>Beginnen Sie den Anker immer mit einem Buchstaben.<\/li><li>Ein Anker kann aus Buchstaben, Zahlen, Bindestrichen, Unterstrichen und Punkten bestehen. Wenn Sie ein unzulässiges Zeichen eingeben, wird dieses automatisch gelöscht.<\/li><li>Ein Anker verwendet Bindestriche anstelle von Leerzeichen, Tabs oder Zeilenumbrüchen. Sollten Sie ein Leerzeichen, einen Tab oder Zeilenumbruch eingeben, so wird dieser durch einen Bindestrich ersetzt.<\/li><\/ul>",
  CMTeaser_text: "Teaser",
  CMTeaser_toolTip: "Teaser",
  CMTeaser_target_text: "Teaser-Ziel",
  CMTeaser_target_toolTip: "Ziel, zu dem der Teaser verlinkt",
  CMTeaser_targets_text: "Teaser-Ziele",
  CMVideo_text: "Video",
  CMVideo_toolTip: "Video",
  CMVideo_data_text: "Video Datei",
  CMVideo_dataUrl_text: "Video-URL",
  CMVideo_dataUrl_toolTip: "URL des Videos",
  CMVideo_dataUrl_emptyText: "Geben Sie hier die Video-URL ein.",
  CMVideo_title_text: "Video-Titel",
  CMVideo_title_toolTip: "Titel des Videos",
  CMVideo_title_emptyText: "Geben Sie hier den Videotitel ein.",
  CMVideo_detailText_text: "Video-Text",
  CMVideo_timeLine_text: "Zeitlinie",
  CMVideo_data_helpText: "Sie können ein Video hochladen.",
  "CMVideo_timeLine.defaultTarget_text": "Standardprodukt",
  CMVideo_sequence_starttime: "Anzeigen bei",
  CMVideo_sequence_units: "Sekunden",
  "CMMedia_localSettings.playerSettings.muted_text": "Stumm schalten",
  "CMMedia_localSettings.playerSettings.loop_text": "Wiederholen",
  "CMMedia_localSettings.playerSettings.autoplay_text": "Autoplay",
  "CMMedia_localSettings.playerSettings.hideControls_text": "Bedienelemente ausblenden",
  CMViewtype_text: "Anzeigevariante",
  CMViewtype_toolTip: "Ein Symbol für Anzeigevarianten",
  CMViewtype_layout_text: "Anzeigevariante",
  CMViewtype_layout_toolTip: "Name der Anzeigevariante",
  CMViewtype_layout_emptyText: "Geben Sie hier den Namen der Anzeigevariante ein.",
  CMTheme_text: "Theme",
  CMTheme_toolTip: "A theme defines the look and feels of a rendered website",
  CMTheme_resourceBundles_text: "Resource Bundles",
  CMTheme_resourceBundles_toolTip: "Die resource bundles des Themes.",
  CMTheme_templateSets_text: "Template Sets",
  CMTheme_templateSets_toolTip: "Die template sets des Themes.",
  CMTheme_javaScriptGroup_text: "Benötigte JavaScripte",
  CMTheme_javaScriptLibs_text: "Externe JavaScript Bibliotheken",
  CMTheme_javaScriptLibs_toolTip: "Externe JavaScripte, die geladen werden sollen (werden vor den Theme-spezifischen JavaScripten geladen).",
  CMTheme_javaScripts_text: "Theme-spezifische JavaScripte",
  CMTheme_javaScripts_toolTip: "Theme-spezifischen JavaScripte (werden nach den externen JavaScripten geladen).",
  CMTheme_css_text: "Benötigte CSS-Dateien",
  CMTheme_css_toolTip: "Benötigte CSS-Dateien, die zusätzlich geladen werden sollen",
  CMTheme_viewRepositoryName_text: "View Repository Name",
  CMTheme_viewRepositoryName_toolTip: "View repository Name für dieses Theme.",
  CMTheme_viewRepositoryName_emptyText: "Geben Sie hier den View Repository Namen ein.",
  CMTheme_icon_text: "Vorschaubild",
  CMTheme_icon_toolTip: "Ein Vorschaubild für das Theme",
  CMTheme_description_text: "Kurzbeschreibung",
  CMTheme_description_toolTip: "Kurzbeschreibung",
  CMTheme_description_emptyText: "Geben Sie hier die Kurzbeschreibung ein.",
  CMTheme_detailText_text: "Detailbeschreibung",
  CMTheme_detailText_toolTip: "Detailbeschreibung dieses Themes",
  CMTheme_detailText_emptyText: "Geben Sie hier die Detailbeschreibung ein.",
  CMResourceBundle_localizations_text: "Lokalisierungen",
  CMResourceBundle_localizations_toolTip: "Lokalisierungen",
  CMResourceBundle_localizations_emptyText: "Geben Sie hier die Lokalisierungen ein.",
  CMVisual_text: "Visual",
  CMVisual_toolTip: "Objekt mit visuellen Inhalten",
  CMVisual_data_text: "Data",
  CMVisual_data_toolTip: "Data",
  CMVisual_dataUrl_text: "Daten-URL (optional)",
  CMVisual_dataUrl_toolTip: "URL für das Visual-Objekt",
  CMVisual_dataUrl_emptyText: "Geben Sie hier die URL für das Visual-Objekt ein.",
  CMVisual_height_text: "Höhe",
  CMVisual_height_toolTip: "Höhe der Darstellung in Pixel",
  CMVisual_height_emptyText: "Geben Sie hier die Höhe in Pixel ein.",
  CMVisual_width_text: "Breite",
  CMVisual_width_toolTip: "Breite der Darstellung in Pixel",
  CMVisual_width_emptyText: "Geben Sie hier die Breite in Pixel ein.",
  CMTemplateSet_description_text: "Beschreibung",
  CMTemplateSet_description_toolTip: "Beschreibung",
  CMTemplateSet_description_emptyText: "Geben Sie hier eine Beschreibung ein.",
  CMTemplateSet_archive_text: "Template-Archiv",
  CMTemplateSet_archive_toolTip: "Template-Archiv (jar oder zip)",
  CMTemplateSet_archive_helpText: "Sie können Zip- oder Jar-Archive hochladen",
  CMTemplateSet_metadata_files_text: "Dateien",
  CMTemplateSet_metadata_files_nameHeader_text: "Name",
  CMTemplateSet_metadata_files_sizeHeader_text: "Größe",
  CMTemplateSet_metadata_files_timeHeader_text: "Zeit",
  Meta_data_exif: "Bild Tags",
  Meta_data_id3: "Audio Daten",
});
