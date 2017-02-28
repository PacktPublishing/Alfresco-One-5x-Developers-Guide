var SDKModule = require('com.alfresco.appcelerator.module.sdk');
var properties = {
	serverUrl : "http://localhost:8080/alfresco",
	serverUsername : "admin",
	serverPassword : "admin"
};
var repoSession = SDKModule.createRepositorySession(properties);

var items = [];

repoSession.addEventListener('error', function(e) {
	alert("ERROR: Cannot connect to server (" + e.errorcode + "): " + e.errorstring);
});

repoSession.addEventListener('success', function(e) {
	Ti.API.info("Connected to server: " + e.servername);

	var searchService = SDKModule.createSearchService();
	searchService.initialiseWithSession(repoSession);

	searchService.addEventListener('error', function(e) {
		alert(e.errorstring);
	});

	searchService.addEventListener('documentnode', function(e) {
		var doc = e.document;
		items.push({
			name : {
				text : doc.name
			},
			date : {
				text : String.formatDate(doc.modifiedAt, "long")
			}
		});
		$.elementsList.sections[0].setItems(items);
	});

	var query = "SELECT * " 
		+ "FROM sc:whitepaper AS wp"
		+ " JOIN sc:webable AS web"
		+ " ON wp.cmis:objectId = web.cmis:objectId"
		+ " WHERE web.sc:isActive = true";

	searchService.searchWithStatement(query);

});

repoSession.connect();
$.index.open();
