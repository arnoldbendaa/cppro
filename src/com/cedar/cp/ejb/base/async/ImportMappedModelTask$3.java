// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.ejb.base.async.ImportMappedModelTask;
import com.cedar.cp.ejb.impl.model.mapping.MappedHierarchyEVO;
import java.util.Comparator;

class ImportMappedModelTask$3 implements Comparator<MappedHierarchyEVO> {

   // $FF: synthetic field
   final ImportMappedModelTask this$0;


   ImportMappedModelTask$3(ImportMappedModelTask var1) {
      this.this$0 = var1;
   }

   public int compare(MappedHierarchyEVO o1, MappedHierarchyEVO o2) {
      return (o1.getHierarchyVisId1() + o1.getHierarchyVisId2()).compareTo(o2.getHierarchyVisId1() + o2.getHierarchyVisId2());
   }
}
