package com.someco.repo.behavior;

import java.util.List;

import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;

import com.someco.repo.common.SomeCoModel;

public class Rating implements NodeServicePolicies.OnDeleteNodePolicy, NodeServicePolicies.OnCreateNodePolicy {

	// Dependencies
	private NodeService nodeService;
	private PolicyComponent policyComponent;

	// Behaviors
	private Behaviour onCreateNode;
	private Behaviour onDeleteNode;

	private static Logger logger = Logger.getLogger(Rating.class);

	public void init() {
		this.onCreateNode = new JavaBehaviour(this, "onCreateNode", NotificationFrequency.TRANSACTION_COMMIT);
		this.onDeleteNode = new JavaBehaviour(this, "onDeleteNode", NotificationFrequency.TRANSACTION_COMMIT);

		this.policyComponent.bindClassBehaviour(QName.createQName(NamespaceService.ALFRESCO_URI, "onCreateNode"), SomeCoModel.TYPE_RATING, this.onCreateNode);
		this.policyComponent.bindClassBehaviour(QName.createQName(NamespaceService.ALFRESCO_URI, "onDeleteNode"), SomeCoModel.TYPE_RATING, this.onDeleteNode);
	}

	public void onCreateNode(ChildAssociationRef childAssocRef) {
		addRating(childAssocRef);
	}

	public void onDeleteNode(ChildAssociationRef childAssocRef, boolean isNodeArchived) {
		recalculateAverage(childAssocRef.getParentRef());
	}

	public void addRating(ChildAssociationRef childAssocRef) {
		// get the parent node
		NodeRef parentRef = childAssocRef.getParentRef();
		NodeRef childRef = childAssocRef.getChildRef();

		Integer total = (Integer) nodeService.getProperty(parentRef, SomeCoModel.PROP_TOTALRATING);
		if (total == null)
			total = 0;
		Integer count = (Integer) nodeService.getProperty(parentRef, SomeCoModel.PROP_RATINGCOUNT);
		if (count == null)
			count = 0;
		Integer rating = (Integer) nodeService.getProperty(childRef, SomeCoModel.PROP_RATING);
		if (rating == null)
			rating = 0;

		Double average = 0d;
		total = total + rating;
		count = count + 1;
		average = total / new Double(count);

		// store the average on the parent node
		nodeService.setProperty(parentRef, SomeCoModel.PROP_AVERAGERATING, average);
		nodeService.setProperty(parentRef, SomeCoModel.PROP_TOTALRATING, total);
		nodeService.setProperty(parentRef, SomeCoModel.PROP_RATINGCOUNT, count);
	}

	public void recalculateAverage(NodeRef parentRef) {
		if (logger.isDebugEnabled())
			logger.debug("Inside computeAverage");
		// check the parent to make sure it has the right aspect
		if (nodeService.hasAspect(parentRef, SomeCoModel.ASPECT_RATEABLE)) {
			// continue, this is what we want
		} else {
			if (logger.isDebugEnabled())
				logger.debug("Rating's parent ref did not have rateable aspect.");
			return;
		}
		// get the parent node's children
		List<ChildAssociationRef> children = nodeService.getChildAssocs(parentRef);
		Double average = 0d;
		int total = 0;
		// This actually happens when the last rating is deleted
		if (children.size() == 0) {
			// No children so no work to do
			if (logger.isDebugEnabled())
				logger.debug("No children found");
		} else {
			// iterate through the children to compute the total
			for (ChildAssociationRef child : children) {
				if (nodeService.getType(child.getChildRef()).equals(SomeCoModel.TYPE_RATING)) {
					int rating = (Integer) nodeService.getProperty(child.getChildRef(), SomeCoModel.PROP_RATING);
					total += rating;
				}
			}
			// compute the average
			average = total / (children.size() / 1.0d);
			if (logger.isDebugEnabled())
				logger.debug("Computed average:" + average);
		}
		// store the average on the parent node
		nodeService.setProperty(parentRef, SomeCoModel.PROP_AVERAGERATING, average);
		nodeService.setProperty(parentRef, SomeCoModel.PROP_TOTALRATING, total);
		nodeService.setProperty(parentRef, SomeCoModel.PROP_RATINGCOUNT, children.size());
		if (logger.isDebugEnabled())
			logger.debug("Property set");
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setPolicyComponent(PolicyComponent policyComponent) {
		this.policyComponent = policyComponent;
	}

}
