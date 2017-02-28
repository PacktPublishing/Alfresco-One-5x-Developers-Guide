package com.someco.repo.scripts;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.apache.log4j.Logger;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class GetReview extends DeclarativeWebScript {

	private ProcessEngine activitiProcessEngine;

	private Logger logger = Logger.getLogger(GetReview.class);

	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status) {

		final String id = req.getParameter("id");
		final String action = req.getParameter("action");
		if (id == null || action == null) {
			status.setCode(400);
			status.setMessage("Required data has not been provided");
			status.setRedirect(true);
		}

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("response", AuthenticationUtil.runAs(new RunAsWork<String>() {
			public String doWork() throws Exception {

				logger.debug("About to update execution id:" + id + " with transition:" + action);

				// Update the variable
				activitiProcessEngine.getRuntimeService().setVariable(id, "scwf_3rdPartyOutcome", action);

				// Signal the execution
				activitiProcessEngine.getRuntimeService().signal(id);

				logger.debug("Signal sent.");

				return "Success";
			}
		}, "admin"));

		return model;
	}

	public void setActivitiProcessEngine(ProcessEngine activitiProcessEngine) {
		this.activitiProcessEngine = activitiProcessEngine;
	}

}
