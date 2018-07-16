package com.cedar.cp.utc.struts.admin.reset;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.reset.ChallengeQuestionsProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChallengeWordSubmit extends CPAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CPContext cpContext = getCPContext(request);
		ChallengeForm myform = (ChallengeForm) form;

		List challenges = (List) myform.getChallenges();
		List challForUpdate = new ArrayList();

		ChallengeQuestionsProcess process = null;
		try {
			int userId = cpContext.getUserContext().getUserId();
			process = cpContext.getCPConnection().getChallengeQuestionsProcess();
			process.setChallengeWord(userId, ((ChallengeDTO) challenges.get(0)).getAnswer().trim());
		} catch (CPException cpe) {
			cpe.printStackTrace();
			return mapping.findForward("fail");
		}
		cpContext.setForceWord(false);
		return mapping.findForward("success");
	}
}