package com.someco;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;

public class CmisTest {

	public static void main(String[] args) {
		try {

			// default factory implementation
			SessionFactory factory = SessionFactoryImpl.newInstance();
			Map<String, String> parameter = new HashMap<String, String>();

			// user credentials
			parameter.put(SessionParameter.USER, "admin");
			parameter.put(SessionParameter.PASSWORD, "admin");

			// connection settings
			parameter.put(SessionParameter.ATOMPUB_URL, "http://127.0.0.1:8080/alfresco/api/-default-/public/cmis/versions/1.1/atom");
			parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
			parameter.put(SessionParameter.REPOSITORY_ID, "-default-");

			// create session
			Session session = factory.createSession(parameter);

			createDocument(session);

			searchDocuments(session);

			deleteDocuments(session);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void createDocument(Session session) throws UnsupportedEncodingException {

		// locate the document library
		String path = "/Sites/marketing/documentLibrary";
		Folder documentLibrary = (Folder) session.getObjectByPath(path);

		// locate the marketing folder
		Folder marketingFolder = null;
		for (CmisObject child : documentLibrary.getChildren()) {
			if ("Marketing".equals(child.getName())) {
				marketingFolder = (Folder) child;
			}
		}

		// create the marketing folder if needed
		if (marketingFolder == null) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(PropertyIds.NAME, "Marketing");
			properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
			marketingFolder = documentLibrary.createFolder(properties);
		}

		// prepare properties
		String filename = "My new whitepaper.txt";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.NAME, filename);
		properties.put(PropertyIds.OBJECT_TYPE_ID, "D:sc:marketingDoc");

		// prepare content
		String content = "Hello World!";
		String mimetype = "text/plain; charset=UTF-8";
		byte[] contentBytes = content.getBytes("UTF-8");
		ByteArrayInputStream stream = new ByteArrayInputStream(contentBytes);
		ContentStream contentStream = session.getObjectFactory().createContentStream(filename, contentBytes.length, mimetype, stream);

		// create the document
		Document marketingDocument = marketingFolder.createDocument(properties, contentStream, VersioningState.MAJOR);

		// locate the whitepaper folder
		Folder whitepaperFolder = null;
		for (CmisObject child : documentLibrary.getChildren()) {
			if ("Whitepapers".equals(child.getName())) {
				whitepaperFolder = (Folder) child;
			}
		}

		if (whitepaperFolder != null) {
			// look for a whitepaper
			Document whitepaper = null;
			for (CmisObject child : whitepaperFolder.getChildren()) {
				if (child.getType().getId().equals("D:sc:whitepaper"))
					whitepaper = (Document) child;
			}

			if (whitepaper != null) {
				properties = new HashMap<String, Object>();
				properties.put(PropertyIds.NAME, "a new relationship");
				properties.put(PropertyIds.OBJECT_TYPE_ID, "R:sc:relatedDocuments");
				properties.put(PropertyIds.SOURCE_ID, marketingDocument.getId());
				properties.put(PropertyIds.TARGET_ID, whitepaper.getId());

				session.createRelationship(properties);
			}
		}
	}

	private static void searchDocuments(Session session) {
		// Look for SomeCo documents
		ItemIterable<QueryResult> results = session.query("SELECT * FROM sc:doc", false);

		for (QueryResult hit : results) {
			for (PropertyData<?> property : hit.getProperties()) {
				String queryName = property.getQueryName();
				Object value = property.getFirstValue();
				System.out.println(queryName + ": " + value);
			}
			System.out.println("--------------------------------------");
		}
	}

	private static void deleteDocuments(Session session) throws UnsupportedEncodingException {

		// locate the document library
		String path = "/Sites/marketing/documentLibrary";
		Folder documentLibrary = (Folder) session.getObjectByPath(path);

		// locate the marketing folder
		Folder marketingFolder = null;
		for (CmisObject child : documentLibrary.getChildren()) {
			if ("Marketing".equals(child.getName())) {
				marketingFolder = (Folder) child;
			}
		}

		// create the marketing folder if needed
		if (marketingFolder != null) {
			for (CmisObject child : marketingFolder.getChildren()) {
				session.delete(child);
			}
		}
	}
}
