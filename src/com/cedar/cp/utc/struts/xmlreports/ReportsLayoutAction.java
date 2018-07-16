// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.xmlreports;

import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.struts.report.ReportForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReportsLayoutAction extends CPAction {

   private static String REPORTS_LAYOUT_ATTRIBUTE = "reportsLayoutFlat";


   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      HttpSession session = httpServletRequest.getSession();
      if(actionForm instanceof ReportForm) {
         ReportForm form = (ReportForm)actionForm;
         session.setAttribute(REPORTS_LAYOUT_ATTRIBUTE, Boolean.valueOf(form.isFlatLayout()));
      }

      return actionMapping.findForward("success");
   }

}
