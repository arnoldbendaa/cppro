// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class BudgetStateRebuildTaskRequest extends AbstractTaskRequest {

   private Integer mModelId;
   private Integer mBudgetCycleId;


   public List toDisplay() {
      ArrayList l = new ArrayList();
      if(this.getModelId() != null) {
         l.add("Rebuild from Model");
      } else {
         l.add("Rebuild from BudgetCycle");
      }

      return l;
   }

   public Integer getModelId() {
      return this.mModelId;
   }

   public void setModelId(Integer modelId) {
      this.mModelId = modelId;
   }

   public Integer getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public void setBudgetCycleId(Integer budgetCycleId) {
      this.mBudgetCycleId = budgetCycleId;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.budgetcycle.BudgetStateRebuildTask";
   }
}
