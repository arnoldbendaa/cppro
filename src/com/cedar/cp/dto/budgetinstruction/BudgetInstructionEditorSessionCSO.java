// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.budgetinstruction;

import com.cedar.cp.dto.budgetinstruction.BudgetInstructionImpl;
import java.io.Serializable;

public class BudgetInstructionEditorSessionCSO implements Serializable {

   private int mUserId;
   private BudgetInstructionImpl mEditorData;


   public BudgetInstructionEditorSessionCSO(int userId, BudgetInstructionImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public BudgetInstructionImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
