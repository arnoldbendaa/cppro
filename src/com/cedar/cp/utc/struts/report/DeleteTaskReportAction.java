// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.report;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityKeyFactory;
import com.cedar.cp.api.report.ReportsProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.report.ReportDetailForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeleteTaskReportAction extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      ReportDetailForm myForm = (ReportDetailForm)actionForm;
      CPContext cpContext = this.getCPContext(httpServletRequest);
      CPConnection cpConnection = cpContext.getCPConnection();
      if(myForm.getReportKey() != null) {
         ReportsProcess process = cpConnection.getReportsProcess();
         EntityKeyFactory factory = cpConnection.getEntityKeyFactory();
         Object key = factory.getKeyFromTokens(myForm.getReportKey());
         process.deleteObject(key);
      }

      return actionMapping.findForward("success");
   }
}
