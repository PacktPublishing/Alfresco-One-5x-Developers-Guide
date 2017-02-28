package com.someco.repo.behavior;

import org.alfresco.repo.content.ContentServicePolicies;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;

import com.someco.repo.common.SomeCoModel;

public class PolicyLogger implements ContentServicePolicies.OnContentUpdatePolicy, ContentServicePolicies.OnContentReadPolicy, NodeServicePolicies.OnCreateNodePolicy, NodeServicePolicies.OnUpdateNodePolicy, NodeServicePolicies.OnDeleteNodePolicy {

	private Logger logger = Logger.getLogger(PolicyLogger.class);

	// Behaviours
	private Behaviour onContentRead;
	private Behaviour onContentUpdate;
	private Behaviour onCreateNode;
	private Behaviour onUpdateNode;
	private Behaviour onDeleteNode;

	// Dependencies
	private PolicyComponent policyComponent;

	private void init() {
		if (logger.isDebugEnabled())
			logger.debug("Initializing policy logger behaviour");
		// Create behaviours
		this.onContentRead = new JavaBehaviour(this, "onContentRead", NotificationFrequency.EVERY_EVENT);
		this.onContentUpdate = new JavaBehaviour(this, "onContentUpdate", NotificationFrequency.EVERY_EVENT);
		this.onCreateNode = new JavaBehaviour(this, "onCreateNode", NotificationFrequency.EVERY_EVENT);
		this.onUpdateNode = new JavaBehaviour(this, "onUpdateNode", NotificationFrequency.EVERY_EVENT);
		this.onDeleteNode = new JavaBehaviour(this, "onDeleteNode", NotificationFrequency.EVERY_EVENT);

		// Bind behaviours to node policies
		this.policyComponent.bindClassBehaviour(QName.createQName(NamespaceService.ALFRESCO_URI, "onContentRead"), SomeCoModel.TYPE_HRPOLICY, this.onContentRead);
		this.policyComponent.bindClassBehaviour(QName.createQName(NamespaceService.ALFRESCO_URI, "onContentUpdate"), SomeCoModel.TYPE_HRPOLICY, this.onContentUpdate);
		this.policyComponent.bindClassBehaviour(QName.createQName(NamespaceService.ALFRESCO_URI, "onCreateNode"), SomeCoModel.TYPE_HRPOLICY, this.onCreateNode);
		this.policyComponent.bindClassBehaviour(QName.createQName(NamespaceService.ALFRESCO_URI, "onUpdateNode"), SomeCoModel.TYPE_HRPOLICY, this.onUpdateNode);
		this.policyComponent.bindClassBehaviour(QName.createQName(NamespaceService.ALFRESCO_URI, "onDeleteNode"), SomeCoModel.TYPE_HRPOLICY, this.onDeleteNode);
		this.policyComponent.bindClassBehaviour(QName.createQName(NamespaceService.ALFRESCO_URI, "onContentRead"),SomeCoModel.ASPECT_CLIENTRELATED, this.onContentRead);

	}

	public void onContentUpdate(NodeRef nodeRef, boolean flag) {
		if (logger.isDebugEnabled())
			logger.debug("Content update policy fired");
	}

	public void onContentRead(NodeRef nodeRef) {
		if (logger.isDebugEnabled())
			logger.debug("Content read policy fired");
	}

	public void onUpdateNode(NodeRef nodeRef) {
		if (logger.isDebugEnabled())
			logger.debug("Node update policy fired");
	}

	public void onCreateNode(ChildAssociationRef childAssocRef) {
		if (logger.isDebugEnabled())
			logger.debug("Node create policy fired");
	}

	public void onDeleteNode(ChildAssociationRef childAssocRef, boolean isNodeArchived) {
		if (logger.isDebugEnabled())
			logger.debug("Node delete policy fired");
	}

	public void setPolicyComponent(PolicyComponent policyComponent) {
		this.policyComponent = policyComponent;
	}

}
