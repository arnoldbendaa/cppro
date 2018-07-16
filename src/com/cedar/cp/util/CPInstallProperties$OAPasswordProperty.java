// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$CPDao;
import com.cedar.cp.util.CPInstallProperties$InstallProperty;
import java.net.InetAddress;
import java.sql.Connection;

class CPInstallProperties$OAPasswordProperty extends CPInstallProperties$InstallProperty {

   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$OAPasswordProperty(CPInstallProperties var1) {
      super(var1, "password", new String[0]);
      this.this$0 = var1;
   }

   public boolean checkProperty(String newValue) {
      this.mFeedback.clear();
      if(this.this$0.getPropertyValue("oa.db.type").equalsIgnoreCase("progress")) {
         ((CPInstallProperties$InstallProperty)CPInstallProperties.accessMethod700(this.this$0).get("oa.db.jdbc.driver")).setValue("com.progress.sql.jdbc.JdbcProgressDriver");
      } else if(this.this$0.getPropertyValue("oa.db.type").equalsIgnoreCase("openedge")) {
         ((CPInstallProperties$InstallProperty)CPInstallProperties.accessMethod700(this.this$0).get("oa.db.jdbc.driver")).setValue("com.ddtek.jdbc.openedge.OpenEdgeDriver");
      } else if(this.this$0.getPropertyValue("oa.db.type").equalsIgnoreCase("sqlserver")) {
         ((CPInstallProperties$InstallProperty)CPInstallProperties.accessMethod700(this.this$0).get("oa.db.jdbc.driver")).setValue("net.sourceforge.jtds.jdbc.Driver");
      } else {
         ((CPInstallProperties$InstallProperty)CPInstallProperties.accessMethod700(this.this$0).get("oa.db.jdbc.driver")).setValue("oracle.jdbc.driver.OracleDriver");
      }

      if("progress".equalsIgnoreCase(this.this$0.getPropertyValue("oa.db.type"))) {
         String jars = "";
         if(!"/".equals(System.getProperty("file.separator"))) {
            jars = "\\bin";
         }

         if(System.getProperty("java.library.path").indexOf(this.this$0.getPropertyValue("oa.db.progress.home") + jars) == -1) {
            this.addFeedback("can\'t check oa connection: " + this.this$0.getPropertyValue("oa.db.progress.home") + jars + " is not in runtime path");
            return false;
         }

         this.addFeedback(this.this$0.getPropertyValue("oa.db.progress.home") + jars + " directory is in runtime path");
      }

      String[] jars1 = null;
      if("OpenEdge".equalsIgnoreCase(this.this$0.getPropertyValue("oa.db.type"))) {
         jars1 = new String[]{"base.jar", "openedge.jar", "progress.jar", "util.jar"};
      } else if("Progress".equalsIgnoreCase(this.this$0.getPropertyValue("oa.db.type"))) {
         jars1 = new String[]{"jdbc.jar", "progress.jar"};
      } else if("sqlserver".equalsIgnoreCase(this.this$0.getPropertyValue("oa.db.type"))) {
         jars1 = new String[]{"jtds-1.2.jar"};
      } else if("oracle".equalsIgnoreCase(this.this$0.getPropertyValue("oa.db.type"))) {
         jars1 = new String[]{"ojdbc14.jar"};
      }

      Connection conn = CPInstallProperties.accessMethod300(this.this$0, jars1, this.this$0.getPropertyValue("oa.db.jdbc.driver"), this.this$0.getPropertyValue("oa.db.url"), this.this$0.getPropertyValue("oa.db.user"), newValue, this);
      if(conn == null) {
         return false;
      } else {
         CPInstallProperties$CPDao oaDao = new CPInstallProperties$CPDao(this.this$0, conn);
         String[] tables = new String[]{"pub.oa_expensecodes", "pub.oa_costcentres", "pub.oa_pcproject", "pub.oa_pcworkstage", "pub.oa_pcanalcode"};
         CPInstallProperties.accessMethod500(this.this$0, oaDao, this.this$0.getPropertyValue("oa.db.type"), tables, this);
         oaDao.closeConnection();
         String hostUrlName = this.this$0.getPropertyValue("oa.db.url");
         String[] urlSplit = hostUrlName.split(":|;");
         String oaHost = urlSplit.length > 3?urlSplit[3]:null;
         String oaPort = urlSplit.length > 4?urlSplit[4]:null;
         if(oaHost != null && oaPort != null) {
            if(oaHost.startsWith("//")) {
               oaHost = oaHost.substring(2);
            }

            InetAddress hostIp = CPInstallProperties.accessMethod000(this.this$0, oaHost);
            if(hostIp != null) {
               this.addFeedback("IP address for " + oaHost + "=" + hostIp.getHostAddress());
               return true;
            } else {
               this.addFeedback("oa host " + oaHost + " not found");
               return false;
            }
         } else {
            this.addFeedback("oa.db.url - missing host or port");
            return false;
         }
      }
   }
}
