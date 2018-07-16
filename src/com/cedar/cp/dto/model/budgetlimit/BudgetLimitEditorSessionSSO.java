// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.budgetlimit;

import com.cedar.cp.dto.model.budgetlimit.BudgetLimitImpl;
import java.io.Serializable;

public class BudgetLimitEditorSessionSSO implements Serializable {

   private BudgetLimitImpl mEditorData;


   public BudgetLimitEditorSessionSSO() {}

   public BudgetLimitEditorSessionSSO(BudgetLimitImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(BudgetLimitImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public BudgetLimitImpl getEditorData() {
      return this.mEditorData;
   }
}
