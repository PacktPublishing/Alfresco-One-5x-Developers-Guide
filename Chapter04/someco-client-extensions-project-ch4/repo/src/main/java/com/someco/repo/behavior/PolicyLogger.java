package com.someco.repo.behavior;

import org.alfresco.repo.content.ContentServicePolicies;
import org.alfresco.repo.content.ContentServicePolicies.OnContentReadPolicy;
import org.alfresco.repo.content.ContentServicePolicies.OnContentUpdatePolicy;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.node.NodeServicePolicies.OnCreateNodePolicy;
import org.alfresco.repo.node.NodeServicePolicies.OnDeleteNodePolicy;
import org.alfresco.repo.node.NodeServicePolicies.OnUpdateNodePolicy;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
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

	public void init() {
		if (logger.isDebugEnabled())
			logger.debug("Initializing policy logger behaviour");
		// Create behaviours
		this.onContentRead = new JavaBehaviour(this, OnContentReadPolicy.QNAME.getLocalName(), NotificationFrequency.TRANSACTION_COMMIT);
		this.onContentUpdate = new JavaBehaviour(this, OnContentUpdatePolicy.QNAME.getLocalName(), NotificationFrequency.TRANSACTION_COMMIT);
		this.onCreateNode = new JavaBehaviour(this, OnCreateNodePolicy.QNAME.getLocalName(), NotificationFrequency.TRANSACTION_COMMIT);
		this.onUpdateNode = new JavaBehaviour(this, OnUpdateNodePolicy.QNAME.getLocalName(), NotificationFrequency.TRANSACTION_COMMIT);
		this.onDeleteNode = new JavaBehaviour(this, OnDeleteNodePolicy.QNAME.getLocalName(), NotificationFrequency.TRANSACTION_COMMIT);

		// Bind behaviours to node policies
		this.policyComponent.bindClassBehaviour(OnContentReadPolicy.QNAME, SomeCoModel.TYPE_HRPOLICY, this.onContentRead);
		this.policyComponent.bindClassBehaviour(OnContentUpdatePolicy.QNAME, SomeCoModel.TYPE_HRPOLICY, this.onContentUpdate);
		this.policyComponent.bindClassBehaviour(OnCreateNodePolicy.QNAME, SomeCoModel.TYPE_HRPOLICY, this.onCreateNode);
		this.policyComponent.bindClassBehaviour(OnUpdateNodePolicy.QNAME, SomeCoModel.TYPE_HRPOLICY, this.onUpdateNode);
		this.policyComponent.bindClassBehaviour(OnDeleteNodePolicy.QNAME, SomeCoModel.TYPE_HRPOLICY, this.onDeleteNode);
		this.policyComponent.bindClassBehaviour(OnContentReadPolicy.QNAME, SomeCoModel.ASPECT_CLIENTRELATED, this.onContentRead);

	}

	@Override
	public void onContentUpdate(NodeRef nodeRef, boolean flag) {
		if (logger.isDebugEnabled())
			logger.debug("Content update policy fired");
	}

	@Override
	public void onContentRead(NodeRef nodeRef) {
		if (logger.isDebugEnabled())
			logger.debug("Content read policy fired");
	}

	@Override
	public void onUpdateNode(NodeRef nodeRef) {
		if (logger.isDebugEnabled())
			logger.debug("Node update policy fired");
	}

	@Override
	public void onCreateNode(ChildAssociationRef childAssocRef) {
		if (logger.isDebugEnabled())
			logger.debug("Node create policy fired");
	}

	@Override
	public void onDeleteNode(ChildAssociationRef childAssocRef, boolean isNodeArchived) {
		if (logger.isDebugEnabled())
			logger.debug("Node delete policy fired");
	}

	public void setPolicyComponent(PolicyComponent policyComponent) {
		this.policyComponent = policyComponent;
	}

}
