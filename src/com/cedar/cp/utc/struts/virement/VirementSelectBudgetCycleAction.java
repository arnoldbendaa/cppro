// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.struts.virement.VirementBudgetCycleDTO;
import com.cedar.cp.utc.struts.virement.VirementBudgetCycleForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VirementSelectBudgetCycleAction extends CPAction {

   public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
      CPConnection connection = this.getCPContext(request).getCPConnection();
      EntityList budgetCycles = connection.getBudgetCyclesProcess().getBudgetTransferBudgetCycles();
      VirementBudgetCycleForm form = (VirementBudgetCycleForm)actionForm;

      for(int row = 0; row < budgetCycles.getNumRows(); ++row) {
         int modelId = ((Integer)budgetCycles.getValueAt(row, "ModelId")).intValue();
         int financeCubeId = ((Integer)budgetCycles.getValueAt(row, "FinanceCubeId")).intValue();
         int budgetCycleId = ((Integer)budgetCycles.getValueAt(row, "BudgetCycleId")).intValue();
         ModelRef modelRef = (ModelRef)budgetCycles.getValueAt(row, "Model");
         FinanceCubeRef financeCubeRef = (FinanceCubeRef)budgetCycles.getValueAt(row, "FinanceCube");
         BudgetCycleRef budgetCycleRef = (BudgetCycleRef)budgetCycles.getValueAt(row, "BudgetCycle");
         String budgetCycleDescription = (String)budgetCycles.getValueAt(row, "Description");
         VirementBudgetCycleDTO dto = new VirementBudgetCycleDTO(modelId, modelRef.getNarrative(), financeCubeId, financeCubeRef.getNarrative(), budgetCycleId, budgetCycleRef.getNarrative(), budgetCycleDescription);
         form.getBudgetCycles().add(dto);
      }

      return mapping.findForward("success");
   }
}
