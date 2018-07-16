// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.budgetinstruction;

import com.cedar.cp.dto.budgetinstruction.BudgetInstructionImpl;
import java.io.Serializable;

public class BudgetInstructionEditorSessionSSO implements Serializable {

   private BudgetInstructionImpl mEditorData;


   public BudgetInstructionEditorSessionSSO() {}

   public BudgetInstructionEditorSessionSSO(BudgetInstructionImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(BudgetInstructionImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public BudgetInstructionImpl getEditorData() {
      return this.mEditorData;
   }
}
