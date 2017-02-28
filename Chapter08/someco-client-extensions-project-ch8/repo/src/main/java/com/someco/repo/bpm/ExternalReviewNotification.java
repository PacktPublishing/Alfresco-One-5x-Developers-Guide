package com.someco.repo.bpm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.alfresco.repo.action.executer.MailActionExecuter;
import org.alfresco.repo.workflow.activiti.ActivitiConstants;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ActionService;
import org.alfresco.util.UrlUtil;

public class ExternalReviewNotification implements ExecutionListener {

	private static final String FROM_ADDRESS = "alfresco@localhost";
	private static final String SUBJECT = "Workflow task requires action";
	private static final String RECIP_PROCESS_VARIABLE = "scwf_reviewerEmail";

	protected ServiceRegistry getServiceRegistry() {
		ProcessEngineConfigurationImpl config = Context.getProcessEngineConfiguration();
		if (config != null) {
			// Fetch the registry that is injected in the activiti
			// spring-configuration
			ServiceRegistry registry = (ServiceRegistry) config.getBeans().get(ActivitiConstants.SERVICE_REGISTRY_BEAN_KEY);
			if (registry == null) {
				throw new RuntimeException("Service-registry not present in ProcessEngineConfiguration beans, "
						+ "expected ServiceRegistry with key" + ActivitiConstants.SERVICE_REGISTRY_BEAN_KEY);
			}
			return registry;
		}
		throw new IllegalStateException("No ProcessEngineConfiguration found in active context");
	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		String recipient = (String) execution.getVariable(ExternalReviewNotification.RECIP_PROCESS_VARIABLE);

		ActionService actionService = getServiceRegistry().getActionService();
		Action mailAction = actionService.createAction(MailActionExecuter.NAME);

		Map<String, String> model = new HashMap<String, String>();
		model.put("taskName", execution.getCurrentActivityName());
		model.put("executionId", execution.getId());
		model.put("shareUrl", UrlUtil.getShareUrl(getServiceRegistry().getSysAdminParams()));
		model.put("alfrescoUrl", UrlUtil.getAlfrescoUrl(getServiceRegistry().getSysAdminParams()));

		mailAction.setParameterValue(MailActionExecuter.PARAM_SUBJECT, ExternalReviewNotification.SUBJECT);
		mailAction.setParameterValue(MailActionExecuter.PARAM_TO, recipient);
		mailAction.setParameterValue(MailActionExecuter.PARAM_FROM, ExternalReviewNotification.FROM_ADDRESS);
		mailAction.setParameterValue(MailActionExecuter.PARAM_TEMPLATE, "alfresco/module/repo/resources/thirdpartyreview-notification.html.ftl");
		mailAction.setParameterValue(MailActionExecuter.PARAM_TEMPLATE_MODEL, (Serializable) model);

		actionService.executeAction(mailAction, null);

		return;
	}

}
