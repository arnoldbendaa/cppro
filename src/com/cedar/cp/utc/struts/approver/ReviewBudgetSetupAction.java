// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.BudgetCyclesProcess;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitCheck;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.approver.BudgetCycleStatusSetupAction;
import com.cedar.cp.utc.struts.approver.ReviewBudgetForm;
import com.cedar.cp.util.Log;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReviewBudgetSetupAction extends BudgetCycleStatusSetupAction {

   Log mLog = new Log(this.getClass());


   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    CPContext context = getCPContext(request);
	    CPConnection cnx = context.getCPConnection();

	    BudgetCyclesProcess process = cnx.getBudgetCyclesProcess();

	    ReviewBudgetForm bcForm = (ReviewBudgetForm)form;

	    if (bcForm.getTopNodeId() == 0) {
	      bcForm.setTopNodeId(bcForm.getSelectedStructureElementId());
	    }

	    bcForm.setCPContextId(request);

	    String forward = "success";
	    if ("true".equals(bcForm.getFull())) {
	      forward = "success_full";
	    } else {
	      if ((bcForm.getSubmitModelName() == null) || (bcForm.getSubmitModelName().trim().length() == 0)) {
	        EntityList cycleList = process.getBudgetCycleDetailedForId(bcForm.getBudgetCycleId());
	        bcForm.setSubmitModelName(cycleList.getValueAt(0, "Model").toString());
	        bcForm.setSubmitCycleName(cycleList.getValueAt(0, "BudgetCycle").toString() + " " + cycleList.getValueAt(0, "Description").toString());
	        
	      }

	      doCrumbs(form, "test", "test");
	    }

	    EntityList list = context.getCPConnection().getListHelper().getCheckIfHasState(bcForm.getBudgetCycleId(), bcForm.getSelectedStructureElementId());
	    int size = list.getNumRows();
	    if (size == 0) {
	      int userId = context.getCPConnection().getUserContext().getUserId();

	      BudgetLimitCheck check = new BudgetLimitCheck();
	      process.setBudgetState(bcForm.getModelId(), userId, bcForm.getBudgetCycleId(), bcForm.getSelectedStructureElementId(), 1, check, null, null);
	    }
	    // get dimensions for setting selected cell
	    if (request.getParameter("dimensions") != null) {
	    	bcForm.setDimensions(request.getParameter("dimensions"));
	    } else {
	    	bcForm.setDimensions(""); // prevents error
	    }
	    return mapping.findForward(forward);
   }
}
