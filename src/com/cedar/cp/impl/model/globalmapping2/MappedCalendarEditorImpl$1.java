package com.cedar.cp.impl.model.globalmapping2;

import com.cedar.cp.api.model.globalmapping2.MappedCalendarYear;
import com.cedar.cp.impl.model.globalmapping2.MappedCalendarEditorImpl;
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
