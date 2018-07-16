package com.cedar.cp.utc.struts.login;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPAuthenticationPolicy;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPContextCache;
import com.cedar.cp.utc.common.CPSystemProperties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class Login extends CPAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		LoginForm login = (LoginForm) form;
		boolean forceUponMe = false;

		HttpSession session = request.getSession();
		session.setAttribute("cpContext", login.getCPContext());

		int logonCount = 0;

		Integer lCount = (Integer) session.getAttribute("cpLogonCount");
		if (lCount != null) {
			logonCount = lCount.intValue();
		}
		CPSystemProperties sysProps = getCPSystemProperties(request);
		CPAuthenticationPolicy authPolicy = sysProps.getActiveAuthenticationPolicy();
		
		/**
		 * After limit of failure logins has been expired and account will be blocked show 
		 * reset password option.
		 */

		if (logonCount >= authPolicy.getMaximumLogonAttempts())
		{
			session.setAttribute("cpLogonCount", 0);
			
			getLogger().warn("execute", "Logon attempt count allmost exceeded - user can try restore password after logon attempts exceeded the system limit (last login name = '" + login.getUserId() + "'");
            
			return mapping.findForward("resetAfterlogonTriesExceeded");
		}
		
//		if (logonCount == authPolicy.getMaximumLogonAttempts()+1)
//		{
//			return mapping.findForward("notLoggedIn");
//		}

/*		if (logonCount > authPolicy.getMaximumLogonAttempts()+1)
		{
			getLogger().warn("execute", "Logon attempt count exceeded (last login name = '" + login.getUserId() + "'");

			return mapping.findForward("logonTriesExceeded");
		}*/

		session.removeAttribute("cpLogonCount");

		CPContext cpContext = login.getCPContext();
		if ((cpContext != null) && (cpContext.getUserContext().userMustChangePassword()))
		{
			if ((sysProps.getActiveAuthenticationPolicy().isInternalTechnique()) && (!sysProps.getResetMethod().equals("None")))
			{
//				if (sysProps.isForceSecurityQuestions()) {
//					
//					EntityList el = cpContext.getCPConnection().getChallengeQuestionsProcess().getAllQuestionsAndAnswersByUserID(cpContext.getIntUserId());	
//					if (el.getNumRows() < sysProps.getNumberOfChallengesForSetup())
//						cpContext.setForceQuestions(true);		
//				}
				
				if (sysProps.isForceSecurityWord()) {
					
					EntityList elw = cpContext.getCPConnection().getListHelper().getChallengeWord(cpContext.getIntUserId());
					if (elw.getNumRows() < 1)
						cpContext.setForceWord(true);
				}
			}
			return mapping.findForward("passwordExpired");
		}

		if ((sysProps.getActiveAuthenticationPolicy().isInternalTechnique()) && (!sysProps.getResetMethod().equals("None")))
		{
			
//			if (sysProps.isForceSecurityQuestions()) {
//				EntityList el = cpContext.getCPConnection().getChallengeQuestionsProcess().getAllQuestionsAndAnswersByUserID(cpContext.getIntUserId());
//	
//				if (el.getNumRows() < sysProps.getNumberOfChallengesForSetup()) {
//					cpContext.setForceQuestions(true);
//					return mapping.findForward("forceQuestions");
//				}
//			}
			
			if (sysProps.isForceSecurityWord()) {
				
				EntityList elw = cpContext.getCPConnection().getListHelper().getChallengeWord(cpContext.getIntUserId());
				if (elw.getNumRows() < 1) {
					cpContext.setForceWord(true);
					return mapping.findForward("forceWord");
				}
			}
		}
		
		if (logonCount > 1)
		{

			return mapping.findForward("resetAfterlogonTriesExceeded");
		}

		CPContextCache.getCPContextId(cpContext);
		
		String link = (String) session.getAttribute("cp30.login.redirect");
		if (link != null)
		{
			session.removeAttribute("cp30.login.redirect");
			if (link.equals("/adminPanel") || link.equals("/flatFormEditor") || link.equals("/flatFormTemplate")) {
			    link = "/adminPanel.do";
			} else if (link.equals("/reviewBudget")) {
                link = "/homePage.do";
            }
			return new ActionForward(link);
		}

		if (cpContext == null) {
			return mapping.findForward("notLoggedIn");
		}
		return mapping.findForward("success");
		

	}
}