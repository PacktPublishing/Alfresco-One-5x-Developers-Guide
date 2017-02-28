package com.someco.repo.action.executer;

import java.util.List;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;

public class MoveReplacedActionExecuter extends ActionExecuterAbstractBase {

	private static Logger logger = Logger.getLogger(MoveReplacedActionExecuter.class);

	private NodeService nodeService;
	private FileFolderService fileFolderService;

	public static final String NAME = "move-replaced";
	public static final String PARAM_DESTINATION_FOLDER = "destination-folder";

	@Override
	public void executeImpl(Action ruleAction, NodeRef actionedUponNodeRef) {
		// get the replaces associations for this node
		QName assocReplacesQname = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, "replaces");
		List<AssociationRef> assocRefs = nodeService.getTargetAssocs(actionedUponNodeRef, assocReplacesQname);
		// if we find some association
		if (!assocRefs.isEmpty()) {
			NodeRef destinationParent = (NodeRef) ruleAction.getParameterValue(PARAM_DESTINATION_FOLDER);
			for (AssociationRef assocNode : assocRefs) {
				try {
					// create a noderef for the replaces association
					NodeRef replacedDocument = assocNode.getTargetRef();
					// if the node exists
					if (nodeService.exists(replacedDocument) == true) {
						fileFolderService.move(replacedDocument, destinationParent, null);
					}
				} catch (Exception e) {
					logger.error("Error moving the document: " + assocNode.getTargetRef());
				}
			}
		}
	}

	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		paramList.add(new ParameterDefinitionImpl(PARAM_DESTINATION_FOLDER, DataTypeDefinition.NODE_REF, true, getParamDisplayLabel(PARAM_DESTINATION_FOLDER)));
	}
	
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setFileFolderService(FileFolderService fileFolderService) {
		this.fileFolderService = fileFolderService;
	}
	
}
