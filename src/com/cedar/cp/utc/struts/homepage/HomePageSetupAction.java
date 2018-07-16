package com.cedar.cp.utc.struts.homepage;

import com.cedar.cp.utc.common.BrowserUtil;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.utc.struts.homepage.BaseHomePageAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HomePageSetupAction extends BaseHomePageAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CPContext context = this.getCPContext(request);
        this.removeWebProcess(request);
        if (BrowserUtil.isUnsupportedBrowser(request)) {
            return mapping.findForward("unsupportedBrowser");
        } else if ((context == null || context.getUserContext().userMustChangePassword() || context.isPortalUser()) && (context == null || !context.isSingleSignon() && !context.isCosignSignon() && !context.isPortalSignon() && !context.isNtlmSignon())) {
            return mapping.findForward("notLoggedOn");
        } else {
            this.populateHomeForm(form, request, context);
            return mapping.findForward("loggedOn");
        }
    }
}
