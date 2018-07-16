// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.approver.BudgetCycleStatusSetupAction;
import com.cedar.cp.utc.struts.approver.BudgetWorkspaceForm;
import com.cedar.cp.util.Log;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BudgetWorkspaceSetupAction extends BudgetCycleStatusSetupAction {

   Log mLog = new Log(this.getClass());


   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      CPContext context = this.getCPContext(request);
      CPConnection cnx = context.getCPConnection();
      String forward = "success";
      BudgetWorkspaceForm bcForm = (BudgetWorkspaceForm)form;
      if(bcForm.getTopNodeId() == 0) {
         bcForm.setTopNodeId(bcForm.getSelectedStructureElementId());
      }

      this.doCrumbs(form, "test", "test");
      if(this.getCPSystemProperties(request).isDev()) {
         forward = "dojo";
      } else {
         bcForm.setCPContextId(request);
         bcForm.setState(2);
         EntityList ra = cnx.getListHelper().getStructureElement(bcForm.getSelectedStructureElementId());
         bcForm.setRaName(ra.getValueAt(0, "VisId") + " - " + ra.getValueAt(0, "Description"));
         EntityList profiles = cnx.getListHelper().getAllDataEntryProfilesForUser(context.getUserContext().getUserId(), bcForm.getModelId(), bcForm.getBudgetCycleId());
         bcForm.setDataEntryProfiles(profiles);
         EntityList reports = cnx.getListHelper().getAllXmlReportsForUser(context.getUserContext().getUserId());
         bcForm.setXmlReports(reports);
      }

      return mapping.findForward(forward);
   }
}
