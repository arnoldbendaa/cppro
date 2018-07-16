// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import java.util.List;

public class Row {

   private int mLevel;
   private List mColumnData;


   public Row(int level, List columnData) {
      this.mLevel = level;
      this.mColumnData = columnData;
   }

   public int getLevel() {
      return this.mLevel;
   }

   public List getColumnData() {
      return this.mColumnData;
   }
}
