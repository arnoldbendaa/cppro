// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionAssignmentDAO;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateAdapter;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class BudgetInstructionChanges extends CMUpdateAdapter {

   protected transient Log mLog = new Log(this.getClass());


   public void terminateProcessing(ModelRef modelRef) {
      this.fixBudgetInstruction(((ModelRefImpl)modelRef).getModelPK().getModelId());
   }

   private void fixBudgetInstruction(int modelId) {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      BudgetInstructionAssignmentDAO biaDao = new BudgetInstructionAssignmentDAO();
      biaDao.deleteOrphanLocations(modelId);
      if(timer != null) {
         timer.logDebug("fixBudgetCycles", "complete");
      }

   }
}
