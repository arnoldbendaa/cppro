// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.ejb.impl.dimension.CalendarYearSpecDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import java.util.Comparator;

class DimensionDAG$1 implements Comparator<CalendarYearSpecDAG> {

   // $FF: synthetic field
   final DimensionDAG this$0;


   DimensionDAG$1(DimensionDAG var1) {
      this.this$0 = var1;
   }

   public int compare(CalendarYearSpecDAG o1, CalendarYearSpecDAG o2) {
      return o1.getCalendarYear() - o2.getCalendarYear();
   }
}
