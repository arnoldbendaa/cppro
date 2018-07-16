// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.ejb.base.async.ImportMappedModelTask;
import com.cedar.cp.ejb.impl.model.mapping.MappedDataTypeEVO;
import java.util.Comparator;

class ImportMappedModelTask$5 implements Comparator<MappedDataTypeEVO> {

   // $FF: synthetic field
   final ImportMappedModelTask this$0;


   ImportMappedModelTask$5(ImportMappedModelTask var1) {
      this.this$0 = var1;
   }

   public int compare(MappedDataTypeEVO o1, MappedDataTypeEVO o2) {
      return (o1.getValueTypeVisId1() + o1.getValueTypeVisId2() + o1.getValueTypeVisId3()).compareTo(o2.getValueTypeVisId1() + o2.getValueTypeVisId2() + o2.getValueTypeVisId3());
   }
}
