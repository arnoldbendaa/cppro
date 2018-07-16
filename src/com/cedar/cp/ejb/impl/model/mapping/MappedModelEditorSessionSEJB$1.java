// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.mapping;

import com.cedar.cp.api.model.mapping.MappedCalendarYear;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEditorSessionSEJB;
import java.util.Comparator;

class MappedModelEditorSessionSEJB$1 implements Comparator<MappedCalendarYear> {

   // $FF: synthetic field
   final MappedModelEditorSessionSEJB this$0;


   MappedModelEditorSessionSEJB$1(MappedModelEditorSessionSEJB var1) {
      this.this$0 = var1;
   }

   public int compare(MappedCalendarYear o1, MappedCalendarYear o2) {
      return o1.getYear() - o2.getYear();
   }
}
