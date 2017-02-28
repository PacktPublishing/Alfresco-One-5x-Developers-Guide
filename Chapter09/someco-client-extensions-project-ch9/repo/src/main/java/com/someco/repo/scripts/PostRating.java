package com.someco.repo.scripts;

import java.util.HashMap;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.apache.log4j.Logger;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.someco.repo.service.RatingService;

public class PostRating extends DeclarativeWebScript {

	private RatingService ratingService;
	private NodeService nodeService;

	private Logger logger = Logger.getLogger(PostRating.class);

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status) {
		int ratingValue = -1;
		String id = req.getParameter("id");
		String rating = req.getParameter("rating");
		String user = req.getParameter("user");

		try {
			ratingValue = Integer.parseInt(rating);
		} catch (NumberFormatException nfe) {
		}

		if (id == null || rating == null || rating.equals("0") || user == null) {
			logger.debug("ID, rating, or user not set");
			status.setCode(400);
			status.setMessage("Required data has not been provided");
			status.setRedirect(true);
		} else if ((ratingValue < 1) || (ratingValue > 5)) {
			logger.debug("Rating out of range");
			status.setCode(400);
			status.setMessage("Rating value must be between 1 and 5 inclusive");
			status.setRedirect(true);
		} else {
			NodeRef curNode = new NodeRef("workspace://SpacesStore/" + id);
			if (!nodeService.exists(curNode)) {
				logger.debug("Node not found");
				status.setCode(404);
				status.setMessage("No node found for id:" + id);
				status.setRedirect(true);
			} else {
				ratingService.create(curNode, Integer.parseInt(rating), user);
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("node", id);
		model.put("rating", rating);
		model.put("user", user);

		return model;
	}
	
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}
	
	public void setRatingService(RatingService ratingService) {
		this.ratingService = ratingService;
	}
}
