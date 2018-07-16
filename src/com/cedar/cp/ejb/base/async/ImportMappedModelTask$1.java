// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.ejb.base.async.ImportMappedModelTask;
import com.cedar.cp.ejb.impl.model.mapping.MappedCalendarYearEVO;
import java.util.Comparator;

class ImportMappedModelTask$1 implements Comparator<MappedCalendarYearEVO> {

   // $FF: synthetic field
   final ImportMappedModelTask this$0;


   ImportMappedModelTask$1(ImportMappedModelTask var1) {
      this.this$0 = var1;
   }

   public int compare(MappedCalendarYearEVO o1, MappedCalendarYearEVO o2) {
      return o1.getYear() - o2.getYear();
   }
}
