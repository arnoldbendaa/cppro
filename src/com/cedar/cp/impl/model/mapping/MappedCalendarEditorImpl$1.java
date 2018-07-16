// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping;

import com.cedar.cp.api.model.mapping.MappedCalendarYear;
import com.cedar.cp.impl.model.mapping.MappedCalendarEditorImpl;
import java.util.Comparator;

class MappedCalendarEditorImpl$1 implements Comparator<MappedCalendarYear> {

   // $FF: synthetic field
   final MappedCalendarEditorImpl this$0;


   MappedCalendarEditorImpl$1(MappedCalendarEditorImpl var1) {
      this.this$0 = var1;
   }

   public int compare(MappedCalendarYear o1, MappedCalendarYear o2) {
      return o1.getYear() - o2.getYear();
   }
}
