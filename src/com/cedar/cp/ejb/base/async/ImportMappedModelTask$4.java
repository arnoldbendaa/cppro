// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.ejb.base.async.ImportMappedModelTask;
import com.cedar.cp.ejb.impl.model.mapping.MappedDimensionElementEVO;
import java.util.Comparator;

class ImportMappedModelTask$4 implements Comparator<MappedDimensionElementEVO> {

   // $FF: synthetic field
   final ImportMappedModelTask this$0;


   ImportMappedModelTask$4(ImportMappedModelTask var1) {
      this.this$0 = var1;
   }

   public int compare(MappedDimensionElementEVO o1, MappedDimensionElementEVO o2) {
      return (o1.getMappingType() + o1.getElementVisId1() + o1.getElementVisId2() + o1.getElementVisId3()).compareTo(o2.getMappingType() + o2.getElementVisId1() + o2.getElementVisId2() + o2.getElementVisId3());
   }
}
