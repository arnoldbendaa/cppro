// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ReviewBudgetRow;
import java.io.Serializable;
import java.util.List;

public class ReviewBudgetRowImpl implements ReviewBudgetRow, Serializable {

   private int mLevel;
   private List mColumnData;


   public ReviewBudgetRowImpl(int level, List columnData) {
      this.mLevel = level;
      this.mColumnData = columnData;
   }

   public int getLevel() {
      return this.mLevel;
   }

   public List getColumnData() {
      return this.mColumnData;
   }

   public Object getColumnAt(int index) {
      return this.mColumnData.get(index);
   }
}
