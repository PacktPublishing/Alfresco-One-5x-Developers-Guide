package com.someco.repo.scripts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.service.cmr.security.MutableAuthenticationService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.namespace.QName;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class InitializeRepository extends DeclarativeWebScript {

	private ServiceRegistry serviceRegistry;
	private Properties alfrescoGlobalProperties;

	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status) {

		PersonService personService = serviceRegistry.getPersonService();
		AuthorityService authorityService = serviceRegistry.getAuthorityService();
		MutableAuthenticationService authenticationService = serviceRegistry.getAuthenticationService();

		// Create the list of messages that will be passed to the Freemarker
		// template
		List<String> messages = new ArrayList<String>();
		Map<String, Object> model = new HashMap<String, Object>();

		// Create SomeCo root group
		messages.addAll(checkGroup("SOMECO", "Someco User Group"));

		if (alfrescoGlobalProperties.containsKey("someco.groups")) {
			// Get all groups
			String[] groupNames = alfrescoGlobalProperties.getProperty("someco.groups").split(",");

			// Create it if it doesn't exist
			for (String groupName : groupNames) {
				String groupDisplayName = alfrescoGlobalProperties.getProperty("someco.groupDisplayName." + groupName);
				messages.addAll(checkGroup(groupName, groupDisplayName, "SOMECO"));

				// Create the username
				String username = "_" + groupName + "_";

				// If the user doesn't exist
				if (personService.personExists(username)) {
					messages.add("The user " + username + " already exists.");
				} else {
					// Create the user
					HashMap<QName, Serializable> properties = new HashMap<QName, Serializable>();
					properties.put(ContentModel.PROP_USERNAME, username);
					properties.put(ContentModel.PROP_FIRSTNAME, "Service Account");
					properties.put(ContentModel.PROP_LASTNAME, groupName);
					properties.put(ContentModel.PROP_ORGANIZATION, "SomeCo");
					personService.createPerson(properties);

					// Create the authentication
					authenticationService.createAuthentication(username, username.toCharArray());
					authenticationService.setAuthenticationEnabled(username, true);

					// Add the user to the group
					authorityService.addAuthority("GROUP_" + groupName.toUpperCase(), username);

					messages.add("The user " + username + " has been created.");
				}
			}

		}

		model.put("messages", messages);
		return model;
	}

	private List<String> checkGroup(String groupName, String groupDisplayName) {
		return checkGroup(groupName, groupDisplayName, null);
	}

	private List<String> checkGroup(String groupName, String groupDisplayName, String parentName) {
		Set<String> zones = Collections.singleton("APP.DEFAULT");

		List<String> messages = new ArrayList<String>();

		AuthorityService authorityService = serviceRegistry.getAuthorityService();

		if (authorityService.authorityExists("GROUP_" + groupName.toUpperCase()))
			messages.add("The group " + groupName + " already exists.");
		else {
			String groupInfo = authorityService.createAuthority(AuthorityType.GROUP, groupName.toUpperCase(), groupDisplayName, zones);
			if (parentName != null)
				authorityService.addAuthority("GROUP_" + parentName.toUpperCase(), "GROUP_" + groupName.toUpperCase());
			messages.add("The group " + groupInfo + " has been created.");
		}

		return messages;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setAlfrescoGlobalProperties(Properties alfrescoGlobalProperties) {
		this.alfrescoGlobalProperties = alfrescoGlobalProperties;
	}

}
