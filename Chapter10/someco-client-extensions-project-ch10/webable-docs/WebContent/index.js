this.alfrescoJsApi = new AlfrescoApi();

this.alfrescoJsApi.login('admin', 'admin').then(function(data) {
	console.log('API called successfully Login ticket:' + data);

	var q = {
		"prop_sc_isActive" : "true",
		"datatype" : "sc:whitepaper"
	};

	this.alfrescoJsApi.webScript.executeWebScript('GET', 'slingshot/search?query=' + JSON.stringify(q) ).then(function(data) {
		console.log('# of items received:' + data.numberFound);
		var items = data.items;
		var html = "<table>";
		for (var i = 0; i < items.length; i++) {
			var item = items[i];
			var nodeId = item.nodeRef.substring(item.nodeRef.lastIndexOf('/') + 1);
			html += "<tr>";
			html += "<td><img src='" + this.alfrescoJsApi.content.getDocumentThumbnailUrl(nodeId) + "' ></td>";
			html += "<td>";
			html += "<a target='_blank' href='/share/page/document-details?nodeRef=" + item.nodeRef + "'>" + item.displayName + "</a><br/>"
			html += "<a target='_blank' href='" + this.alfrescoJsApi.content.getContentUrl(nodeId) + "'>Download</a>"
			html += "</td>";
			html += "</tr>";
		}
		html += "</table>";
		
		document.body.innerHTML += html;

	}, function(error) {
		console.error(error);
	});

}, function(error) {
	console.error(error);
});
