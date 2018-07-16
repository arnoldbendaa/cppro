// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.model.BudgetCycleImpl;
import java.io.Serializable;

public class BudgetCycleEditorSessionCSO implements Serializable {

   private int mUserId;
   private BudgetCycleImpl mEditorData;


   public BudgetCycleEditorSessionCSO(int userId, BudgetCycleImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public BudgetCycleImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
