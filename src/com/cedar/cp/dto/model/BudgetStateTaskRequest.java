// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class BudgetStateTaskRequest extends AbstractTaskRequest {

   private int mDaysBefore;


   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("mDaysBefore=" + this.getDaysBefore());
      return l;
   }

   public int getDaysBefore() {
      return this.mDaysBefore;
   }

   public void setDaysBefore(int daysBefore) {
      this.mDaysBefore = daysBefore;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.budgetcycle.BudgetStateTask";
   }
}
