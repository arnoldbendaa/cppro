// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.lookup;

import com.cedar.cp.ejb.base.async.lookup.LookupTableAdminTask;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupColumnDefEVO;
import java.util.Comparator;

class LookupTableAdminTask$1 implements Comparator<UdefLookupColumnDefEVO> {

   // $FF: synthetic field
   final LookupTableAdminTask this$0;


   LookupTableAdminTask$1(LookupTableAdminTask var1) {
      this.this$0 = var1;
   }

   public int compare(UdefLookupColumnDefEVO o1, UdefLookupColumnDefEVO o2) {
      return o1.getColumnDefIndex() - o2.getColumnDefIndex();
   }
}
