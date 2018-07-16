// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.model.cc.RuntimeCcDeployment;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import java.util.Comparator;

class RuntimeCcDeployment$1 implements Comparator<CalendarElementNode> {

   // $FF: synthetic field
   final RuntimeCcDeployment this$0;


   RuntimeCcDeployment$1(RuntimeCcDeployment var1) {
      this.this$0 = var1;
   }

   public int compare(CalendarElementNode o1, CalendarElementNode o2) {
      return o1.getPosition() - o2.getPosition();
   }
}
