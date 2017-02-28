package com.someco.repo.service;

import org.alfresco.service.cmr.repository.NodeRef;

public interface RatingService {

	NodeRef create(NodeRef document, int rating, String user);
	
	void delete(NodeRef document);

}
