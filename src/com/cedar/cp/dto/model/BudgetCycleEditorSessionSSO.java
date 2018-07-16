// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.model.BudgetCycleImpl;
import java.io.Serializable;

public class BudgetCycleEditorSessionSSO implements Serializable {

   private BudgetCycleImpl mEditorData;


   public BudgetCycleEditorSessionSSO() {}

   public BudgetCycleEditorSessionSSO(BudgetCycleImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(BudgetCycleImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public BudgetCycleImpl getEditorData() {
      return this.mEditorData;
   }
}
