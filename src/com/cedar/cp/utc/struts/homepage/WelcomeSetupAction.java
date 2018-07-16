package com.cedar.cp.utc.struts.homepage;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.BrowserUtil;
import com.cedar.cp.utc.common.CPContext;

public class WelcomeSetupAction extends BaseHomePageAction {

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        CPContext context = this.getCPContext(httpServletRequest);
        this.removeWebProcess(httpServletRequest);
        if (BrowserUtil.isUnsupportedBrowser(httpServletRequest)) {
            return actionMapping.findForward("unsupportedBrowser");
        } else if ((context == null || context.getUserContext().userMustChangePassword() || context.isPortalUser()) && (context == null || !context.isPortalUser())) {
            return actionMapping.findForward("notLoggedOn");
        } else {
            HomePageForm myForm = (HomePageForm) actionForm;
            Calendar cal = Calendar.getInstance();
            CPConnection conn = context.getCPConnection();
            EntityList welcomeList = conn.getListHelper().getModernWelcomeDetails(context.getIntUserId(), this.getCPSystemProperties(httpServletRequest).getStatusWarningDays());
            if (welcomeList.getNumRows() > 0) {
                for (int i = 0; i < welcomeList.getNumRows(); ++i) {
                    Timestamp plannedEndDate = (Timestamp) welcomeList.getValueAt(i, "PlannedEndDate");
                    if (plannedEndDate.before(cal.getTime())) {
                        myForm.setOverdueItems(true);
                    } else {
                        myForm.setDueItems(true);
                    }
                }
            }

            return actionMapping.findForward("loggedOn");
        }
    }
}
