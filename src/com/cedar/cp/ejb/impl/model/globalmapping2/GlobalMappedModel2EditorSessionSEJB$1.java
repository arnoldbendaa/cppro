package com.cedar.cp.ejb.impl.model.globalmapping2;

import com.cedar.cp.api.model.globalmapping2.MappedCalendarYear;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2EditorSessionSEJB;
import java.util.Comparator;

class GlobalMappedModel2EditorSessionSEJB$1 implements Comparator<MappedCalendarYear> {

   // $FF: synthetic field
   final GlobalMappedModel2EditorSessionSEJB this$0;


   GlobalMappedModel2EditorSessionSEJB$1(GlobalMappedModel2EditorSessionSEJB var1) {
      this.this$0 = var1;
   }

   public int compare(MappedCalendarYear o1, MappedCalendarYear o2) {
      return o1.getYear() - o2.getYear();
   }
}
