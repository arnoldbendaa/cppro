// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$InstallProperty;
import java.net.InetAddress;
import java.util.Scanner;

class CPInstallProperties$OracleServiceProperty extends CPInstallProperties$InstallProperty {

   public String mPort;
   public String mHost;
   public String mSid;
   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$OracleServiceProperty(CPInstallProperties var1) {
      super(var1, "service", new String[0]);
      this.this$0 = var1;
      this.mPort = null;
      this.mHost = null;
      this.mSid = null;
   }

   public boolean checkProperty(String newValue) {
      this.mFeedback.clear();
      String stdout = CPInstallProperties.accessMethod100(this.this$0, new String[]{"tnsping", newValue}, this);
      Scanner scanner = (new Scanner(stdout)).useDelimiter("\\(\\s*|\\)\\s*|\\s*\\=\\s*|\\s");

      while(scanner.hasNext()) {
         String ipAddr = scanner.next();
         if("PORT".equalsIgnoreCase(ipAddr) && scanner.hasNext()) {
            this.mPort = scanner.next();
         } else if("HOST".equalsIgnoreCase(ipAddr) && scanner.hasNext()) {
            this.mHost = scanner.next();
         } else if("SID".equalsIgnoreCase(ipAddr) && scanner.hasNext()) {
            this.mSid = scanner.next();
         } else {
            if("TNS-03505:".equalsIgnoreCase(ipAddr)) {
               this.addFeedback("tnsping: TNS-03505 failed to resolve name");
               return false;
            }

            if("TNS-12545:".equalsIgnoreCase(ipAddr)) {
               this.addFeedback("tnsping: TNS-12545 " + this.mHost + " does not exist");
               this.mHost = null;
               return false;
            }

            if("timed out".equalsIgnoreCase(ipAddr)) {
               this.mHost = null;
               return false;
            }
         }
      }

      if(this.mHost != null) {
         InetAddress ipAddr1 = CPInstallProperties.accessMethod000(this.this$0, this.mHost);
         if(ipAddr1 == null) {
            this.addFeedback("tnsping: host=" + this.mHost + " - not found");
            return false;
         }

         this.addFeedback("tnsping: host=" + this.mHost + " (ip=" + ipAddr1.getHostAddress() + ")" + (this.mPort == null?"":" port=" + this.mPort) + (this.mSid == null?"":" sid=" + this.mSid));
      }

      return true;
   }

   public String getHost() {
      return this.mHost;
   }

   public String getPort() {
      return this.mPort;
   }

   public String getSid() {
      return this.mSid;
   }
}
