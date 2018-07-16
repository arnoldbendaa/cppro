// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.cc;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.impl.model.cc.CcMappingLineEditorImpl;
import java.util.Comparator;

class CcMappingLineEditorImpl$1 implements Comparator<DataTypeRef> {

   // $FF: synthetic field
   final CcMappingLineEditorImpl this$0;


   CcMappingLineEditorImpl$1(CcMappingLineEditorImpl var1) {
      this.this$0 = var1;
   }

   public int compare(DataTypeRef o1, DataTypeRef o2) {
      return o1.getDisplayText().compareTo(o2.getDisplayText());
   }
}
