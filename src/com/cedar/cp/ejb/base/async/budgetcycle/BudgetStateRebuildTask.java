// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.budgetcycle;

import com.cedar.cp.dto.model.BudgetStateRebuildTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.impl.cm.BudgetStateChanges;
import javax.naming.InitialContext;

public class BudgetStateRebuildTask extends AbstractTask {

   public int getReportType() {
      return 0;
   }

   public String getEntityName() {
      return "BudgetStateTask";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      BudgetStateRebuildTaskRequest request = (BudgetStateRebuildTaskRequest)this.getRequest();
      BudgetStateChanges changes = new BudgetStateChanges();
      if(request.getBudgetCycleId() == null) {
         changes.tidyBudgetState(request.getModelId().intValue());
      } else {
         changes.tidyBudgetState(request.getModelId().intValue(), request.getBudgetCycleId().intValue());
      }

   }
}
