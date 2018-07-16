// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.udeflookup;

import com.cedar.cp.ejb.impl.udeflookup.UdefLookupColumnDefEVO;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupEditorSessionSEJB;
import java.util.Comparator;

class UdefLookupEditorSessionSEJB$1 implements Comparator<UdefLookupColumnDefEVO> {

   // $FF: synthetic field
   final UdefLookupEditorSessionSEJB this$0;


   UdefLookupEditorSessionSEJB$1(UdefLookupEditorSessionSEJB var1) {
      this.this$0 = var1;
   }

   public int compare(UdefLookupColumnDefEVO o1, UdefLookupColumnDefEVO o2) {
      return o1.getColumnDefIndex() - o2.getColumnDefIndex();
   }
}
