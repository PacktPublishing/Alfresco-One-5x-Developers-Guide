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

public class DeleteRating extends DeclarativeWebScript {

	private RatingService ratingService;
	private NodeService nodeService;

	private Logger logger = Logger.getLogger(DeleteRating.class);

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status) {
		String id = req.getParameter("id");

		if (id == null) {
			logger.debug("ID not set");
			status.setCode(400);
			status.setMessage("Required data has not been provided");
			status.setRedirect(true);
		} else {
			NodeRef curNode = new NodeRef("workspace://SpacesStore/" + id);
			if (!nodeService.exists(curNode)) {
				logger.debug("Node not found");
				status.setCode(404);
				status.setMessage("No node found for id:" + id);
				status.setRedirect(true);
			} else {
				ratingService.delete(curNode);
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("node", id);

		return model;
	}
	
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}
	
	public void setRatingService(RatingService ratingService) {
		this.ratingService = ratingService;
	}
}
