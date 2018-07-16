// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.api.model.budgetlimit.BudgetLimitCheck;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.struts.approver.StatusConfirmForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class StatusMessageConfirm extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      String forward = "cancel";
      if(actionForm instanceof StatusConfirmForm) {
         StatusConfirmForm myForm = (StatusConfirmForm)actionForm;
         String action = myForm.getStatusMessageAction();
         if(action == null) {
            action = "";
         }

         if(action.equals("Ok")) {
            BudgetLimitCheck check = new BudgetLimitCheck();
            check.setPerformCheck(false);
            StringBuffer sb = new StringBuffer();
            check.setForceMessage(true);
            check.setReason(sb.toString());
            check.setOriginalReason(myForm.getMessage());
            httpServletRequest.getSession().setAttribute("BudgetLimitCheck", check);
            forward = "ok";
         }
      }

      return actionMapping.findForward(forward);
   }
}
