// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.model.BudgetCyclesProcess;
import com.cedar.cp.api.model.ReviewBudgetDetails;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CacheCopy;
import com.cedar.cp.utc.struts.approver.LoadtestForm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoadtestSetupAction extends CPAction {

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      LoadtestForm f = (LoadtestForm)form;
      CPContext context = this.getCPContext(request);
      f.setCPContextId(request);
      f.setUserId(context.getCPConnection().getUserContext().getUserId());
      CPConnection cnx = context.getCPConnection();
      HashMap contextVariables = new HashMap();
      CacheCopy.copyClientToContext(cnx.getClientCache(), contextVariables);
      int[] parsedSelections = this.parseSelections(f.getSelectionString());
      HashMap selections = new HashMap();

      for(int process = 0; process < parsedSelections.length; ++process) {
         selections.put(new Integer(process), new Integer(parsedSelections[process]));
      }

      BudgetCyclesProcess var13 = cnx.getBudgetCyclesProcess();
      ReviewBudgetDetails rbd = var13.getReviewBudgetDetails(context.getUserContext().getUserId(), "  ", f.getTopNodeId(), f.getModelId(), f.getBudgetCycleId(), (Object)null, true, selections, f.getDataType(), contextVariables);
      f.setFinanceCubeId(rbd.getFinanceCubeId());
      f.setFormData(rbd.getFormData());
      return mapping.findForward("success");
   }

   private int[] parseSelections(String selections) {
      ArrayList tokens = new ArrayList();
      StringTokenizer st = new StringTokenizer(selections, ",");

      while(st.hasMoreTokens()) {
         tokens.add(st.nextToken());
      }

      int[] result = new int[tokens.size()];

      for(int i = 0; i < tokens.size(); ++i) {
         result[i] = Integer.parseInt(tokens.get(i).toString());
      }

      return result;
   }
}
