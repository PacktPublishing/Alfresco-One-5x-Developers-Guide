package com.someco.repo.service.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;

import com.someco.repo.common.SomeCoModel;
import com.someco.repo.service.RatingService;

public class RatingServiceImpl implements RatingService {

	private NodeService nodeService;

	@Override
	public NodeRef create(NodeRef document, int rating, String user) {

		// add the aspect to this document if it needs it
		if (!nodeService.hasAspect(document, SomeCoModel.ASPECT_RATEABLE)) {
			nodeService.addAspect(document, SomeCoModel.ASPECT_RATEABLE, null);
		}

		Map<QName, Serializable> props = new HashMap<QName, Serializable>();
		props.put(SomeCoModel.PROP_RATING, rating);
		props.put(SomeCoModel.PROP_RATER, user);

		ChildAssociationRef ratingAssoc = nodeService.createNode(document, SomeCoModel.ASSOC_RATINGS, QName.createQName(SomeCoModel.SOMECO_MODEL_URI, SomeCoModel.PROP_RATING.getLocalName() + new Date().getTime()), SomeCoModel.TYPE_RATING, props);

		return ratingAssoc.getChildRef();
	}
	
	@Override
	public void delete(NodeRef document) {
		// get child associations to ratings
		List<ChildAssociationRef> ratingAssocs = nodeService.getChildAssocs(document, Collections.singleton(SomeCoModel.TYPE_RATING));
		// delete all of them
		for (ChildAssociationRef childAssociationRef : ratingAssocs) {
			nodeService.deleteNode(childAssociationRef.getChildRef());
		}
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

}
