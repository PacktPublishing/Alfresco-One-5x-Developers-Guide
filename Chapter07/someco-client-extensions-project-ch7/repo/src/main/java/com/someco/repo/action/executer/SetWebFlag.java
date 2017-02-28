package com.someco.repo.action.executer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;

import com.someco.repo.common.SomeCoModel;

public class SetWebFlag extends ActionExecuterAbstractBase {

	protected NodeService nodeService;
	public final static String NAME = "set-web-flag";
	public final static String PARAM_ACTIVE = "active";
	public final static String PARAM_PUBLISHED = "published";

	@Override
	protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
		boolean activeFlag = true;
		if (action.getParameterValue(PARAM_ACTIVE) != null)
			activeFlag = (Boolean) action.getParameterValue(PARAM_ACTIVE);
		Map<QName, Serializable> properties = nodeService.getProperties(actionedUponNodeRef);
		properties.put(SomeCoModel.PROP_ISACTIVE, activeFlag);
		if (activeFlag) {
			Date published = new Date();
			if (action.getParameterValue(PARAM_PUBLISHED) != null)
				published = (Date) action.getParameterValue(PARAM_PUBLISHED);
			properties.put(SomeCoModel.PROP_PUBLISHED, published);
		} else {
			properties.put(SomeCoModel.PROP_PUBLISHED, null);
		}
		// if the aspect has already been added, set the properties
		if (nodeService.hasAspect(actionedUponNodeRef, SomeCoModel.ASPECT_WEBABLE)) {
			nodeService.setProperties(actionedUponNodeRef, properties);
		} else {
			// otherwise, add the aspect and set the properties
			nodeService.addAspect(actionedUponNodeRef, SomeCoModel.ASPECT_WEBABLE, properties);
		}
	}

	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		// Create a new parameter defintion to add to the list
		paramList.add(new ParameterDefinitionImpl(PARAM_ACTIVE, DataTypeDefinition.BOOLEAN, false, getParamDisplayLabel(PARAM_ACTIVE)));
		paramList.add(new ParameterDefinitionImpl(PARAM_PUBLISHED, DataTypeDefinition.DATE, false, getParamDisplayLabel(PARAM_PUBLISHED)));
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

}
