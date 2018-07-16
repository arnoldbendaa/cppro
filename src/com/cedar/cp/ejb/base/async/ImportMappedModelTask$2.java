// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.ejb.base.async.ImportMappedModelTask;
import com.cedar.cp.ejb.impl.model.mapping.MappedDimensionEVO;
import java.util.Comparator;

class ImportMappedModelTask$2 implements Comparator<MappedDimensionEVO> {

   // $FF: synthetic field
   final ImportMappedModelTask this$0;


   ImportMappedModelTask$2(ImportMappedModelTask var1) {
      this.this$0 = var1;
   }

   public int compare(MappedDimensionEVO o1, MappedDimensionEVO o2) {
      return o1.getPathVisId().compareTo(o2.getPathVisId());
   }
}
