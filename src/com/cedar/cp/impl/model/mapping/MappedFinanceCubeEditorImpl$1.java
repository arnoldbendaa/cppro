// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping;

import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.impl.model.mapping.MappedFinanceCubeEditorImpl;
import java.util.Comparator;

class MappedFinanceCubeEditorImpl$1 implements Comparator<DataType> {

   // $FF: synthetic field
   final MappedFinanceCubeEditorImpl this$0;


   MappedFinanceCubeEditorImpl$1(MappedFinanceCubeEditorImpl var1) {
      this.this$0 = var1;
   }

   public int compare(DataType o1, DataType o2) {
      return o1.getVisId().compareTo(o2.getVisId());
   }
}
