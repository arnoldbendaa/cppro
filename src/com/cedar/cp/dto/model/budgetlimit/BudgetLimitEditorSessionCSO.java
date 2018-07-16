// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.budgetlimit;

import com.cedar.cp.dto.model.budgetlimit.BudgetLimitImpl;
import java.io.Serializable;

public class BudgetLimitEditorSessionCSO implements Serializable {

   private int mUserId;
   private BudgetLimitImpl mEditorData;


   public BudgetLimitEditorSessionCSO(int userId, BudgetLimitImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public BudgetLimitImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
