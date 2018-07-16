// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.struts.approver.BudgetViolationConfirmForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BudgetViolationSetup extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      if(actionForm instanceof BudgetViolationConfirmForm) {
         BudgetViolationConfirmForm myForm = (BudgetViolationConfirmForm)actionForm;
         myForm.populateLists(httpServletRequest);
      }

      return actionMapping.findForward("success");
   }
}
