// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$InstallProperty;
import java.net.InetAddress;

class CPInstallProperties$HostNameProperty extends CPInstallProperties$InstallProperty {

   String mIpAddr;
   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$HostNameProperty(CPInstallProperties var1) {
      super(var1, "host", new String[0]);
      this.this$0 = var1;
      this.mIpAddr = null;
   }

   public boolean checkProperty(String newValue) {
      this.mFeedback.clear();
      InetAddress ipAddr = CPInstallProperties.accessMethod000(this.this$0, newValue);
      if(ipAddr == null) {
         this.addFeedback("<" + newValue + "> not found");
         return false;
      } else {
         this.mIpAddr = ipAddr.getHostAddress();
         this.addFeedback("ip=" + this.mIpAddr);
         return true;
      }
   }

   public String getIpAddr() {
      return this.mIpAddr;
   }
}
