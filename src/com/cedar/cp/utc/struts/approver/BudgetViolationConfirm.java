// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitCheck;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.struts.approver.BudgetViolationConfirmForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BudgetViolationConfirm extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      String forward = "cancel";
      if(actionForm instanceof BudgetViolationConfirmForm) {
         BudgetViolationConfirmForm myForm = (BudgetViolationConfirmForm)actionForm;
         String action = myForm.getViolationAction();
         if(action == null) {
            action = "";
         }

         if(action.equals("Ok")) {
            BudgetLimitCheck check = new BudgetLimitCheck();
            check.setPerformCheck(false);
            check.setReason(myForm.getReason());
            check.setViolationHeadings((EntityList)httpServletRequest.getSession().getAttribute("BudgetViolationHeadings"));
            check.setOriginalReason(myForm.getReason());
            check.setViolations((List)httpServletRequest.getSession().getAttribute("BudgetViolations"));
            httpServletRequest.getSession().setAttribute("BudgetLimitCheck", check);
            forward = "ok";
         }
      }

      httpServletRequest.getSession().removeAttribute("BudgetViolations");
      httpServletRequest.getSession().removeAttribute("BudgetViolationHeadings");
      return actionMapping.findForward(forward);
   }
}
