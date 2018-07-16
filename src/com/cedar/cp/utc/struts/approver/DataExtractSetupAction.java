// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.struts.approver.DataExtractForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DataExtractSetupAction extends CPAction {

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
      DataExtractForm deForm = (DataExtractForm)form;
      deForm.setCPContextId(request);
      return mapping.findForward("success");
   }
}
