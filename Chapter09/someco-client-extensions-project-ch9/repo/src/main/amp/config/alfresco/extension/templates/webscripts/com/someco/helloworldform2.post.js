for each (field in formdata.fields) {
	if (field.name == "name") {
		model.name = field.value;
	}
	if (field.name == "file" && field.isFile) {
		filename = field.filename;
		content = field.content;
		mimetype = field.mimetype;
	}
}

var results = search.luceneSearch("+PATH:\"app:company_home/*\" +TYPE:\"cm:folder\" +@cm\\:name:\"Someco\"");
var targetFolder = results[0];
var newDoc = targetFolder.createFile(filename);
newDoc.properties.content.write(content);
newDoc.properties.content.mimetype = mimetype;
newDoc.save();

model.node = newDoc;
