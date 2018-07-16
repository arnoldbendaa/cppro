// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.report;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.struts.report.ReportUpdateForm;
import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DoReportUpdatesAction extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      ReportUpdateForm reportUpdateForm = (ReportUpdateForm)actionForm;

      try {
         CPConnection e = this.getCPContext(httpServletRequest).getCPConnection();
         int taskId = e.getReportsProcess().issueReportUpdateTask(new Integer(reportUpdateForm.getReportId()), false);
         String msg = MessageFormat.format("The task {0} has been submitted to apply the updates for report {1}.", new Object[]{new Integer(taskId), reportUpdateForm.getReportId()});
         reportUpdateForm.setMessage(msg);
      } catch (ValidationException var9) {
         reportUpdateForm.setMessage(var9.getMessage());
      }

      return actionMapping.findForward("success");
   }
}
