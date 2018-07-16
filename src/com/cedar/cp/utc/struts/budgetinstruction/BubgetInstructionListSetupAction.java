// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.budgetinstruction;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.budgetinstruction.BudgetInstructionForm;
import com.cedar.cp.utc.struts.homepage.BudgetInstructionDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BubgetInstructionListSetupAction extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      CPContext context = this.getCPContext(httpServletRequest);
      CPConnection cnx = context.getCPConnection();
      BudgetInstructionForm myForm = (BudgetInstructionForm)actionForm;
      ArrayList modelInstructions = null;
      ArrayList cycleInstructions = null;
      ArrayList seInstructions = null;
      EntityList instructions;
      if(myForm.getModelId() > 0) {
         modelInstructions = new ArrayList();
         instructions = cnx.getBudgetInstructionsProcess().getAllBudgetInstructionsForModel(myForm.getModelId());
         this.populateList(modelInstructions, instructions);
      }

      if(myForm.getBudgetCycleId() > 0) {
         cycleInstructions = new ArrayList();
         instructions = cnx.getBudgetInstructionsProcess().getAllBudgetInstructionsForCycle(myForm.getBudgetCycleId());
         this.populateList(cycleInstructions, instructions);
      }

      if(myForm.getStructureElementId() > 0) {
         seInstructions = new ArrayList();
         instructions = cnx.getBudgetInstructionsProcess().getAllBudgetInstructionsForCycle(myForm.getStructureElementId());
         this.populateList(seInstructions, instructions);
      }

      if(modelInstructions == null) {
         myForm.setModelInstructions(Collections.EMPTY_LIST);
      } else {
         myForm.setModelInstructions(modelInstructions);
      }

      if(cycleInstructions == null) {
         myForm.setCycleInstructions(Collections.EMPTY_LIST);
      } else {
         myForm.setCycleInstructions(cycleInstructions);
      }

      if(seInstructions == null) {
         myForm.setStructureElementInstructions(Collections.EMPTY_LIST);
      } else {
         myForm.setStructureElementInstructions(seInstructions);
      }

      return actionMapping.findForward("success");
   }

   private void populateList(List<BudgetInstructionDTO> iList, EntityList instructions) {
      int size = instructions.getNumRows();

      for(int i = 0; i < size; ++i) {
         BudgetInstructionDTO biDTO = new BudgetInstructionDTO();
         biDTO.setIdentifier(instructions.getValueAt(i, "VisId").toString());
         biDTO.setId(((Integer)instructions.getValueAt(i, "BudgetInstructionId")).intValue());
         iList.add(biDTO);
      }

   }
}
