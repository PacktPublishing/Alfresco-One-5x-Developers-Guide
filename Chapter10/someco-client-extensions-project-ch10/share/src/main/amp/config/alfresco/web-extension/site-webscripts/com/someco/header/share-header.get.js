var headerMenu = widgetUtils.findObject(model.jsonModel, "id", "HEADER_APP_MENU_BAR");
if (headerMenu != null) {
	headerMenu.config.widgets.push({
		id : "HEADER_SOMECO_LINKS",
		name : "alfresco/header/AlfMenuBarPopup",
		config : {
			id : "HEADER_SOMECO_LINKS",
			label : "SomeCo Links",
			widgets : [ {
				id : "HEADER_SOMECO_LINKS_GROUP",
				name : "alfresco/menus/AlfMenuGroup",
				config : {
					widgets : [ {
						id : "HEADER_SOMECO_LINKS_CORPORATE",
						name : "alfresco/header/AlfMenuItem",
						config : {
							label : "Corporate Website",
							targetUrl : "http://www.alfresco.com",
							targetUrlType : "FULL_PATH",
							targetUrlLocation : "NEW"
						}
					}, {
						id : "HEADER_SOMECO_LINKS_SEARCHENGINE",
						name : "alfresco/header/AlfMenuItem",
						config : {
							label : "Google",
							targetUrl : "http://www.google.com",
							targetUrlType : "FULL_PATH",
							targetUrlLocation : "NEW"
						}
					}, {
						id : "HEADER_SOMECO_LINKS_DOCUMENTATION",
						name : "alfresco/header/AlfMenuItem",
						config : {
							label : "Alfresco Documentation",
							targetUrl : "http://docs.alfresco.com",
							targetUrlType : "FULL_PATH",
							targetUrlLocation : "NEW"
						}
					} ]
				}
			} ]
		}
	});
}
