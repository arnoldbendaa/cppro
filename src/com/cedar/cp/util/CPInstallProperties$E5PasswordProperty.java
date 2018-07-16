// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$CPDao;
import com.cedar.cp.util.CPInstallProperties$OraclePasswordProperty;
import com.cedar.cp.util.CPInstallProperties$OracleServiceProperty;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;

class CPInstallProperties$E5PasswordProperty extends CPInstallProperties$OraclePasswordProperty {

   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$E5PasswordProperty(CPInstallProperties var1) {
      super(var1, "password", "e5.db.service", "e5.db.user", "e5.db.schema");
      this.this$0 = var1;
   }

   public boolean checkProperty(String newValue) {
      this.mFeedback.clear();
      return this.this$0.getPropertyValue("e5.db.type").equalsIgnoreCase("oracle")?this.checkOracle(newValue):this.checkDB2(newValue);
   }

   private boolean checkOracle(String newValue) {
      if(((CPInstallProperties$OracleServiceProperty)this.this$0.getProperty(this.mServiceNameProperty)).getHost() == null) {
         return true;
      } else if(!super.checkProperty(newValue)) {
         return false;
      } else {
         this.this$0.checkSchema(this, newValue, "TAACNTRL");
         if(CPInstallProperties.accessMethod400(this.this$0) == null) {
            return true;
         } else {
            CPInstallProperties.accessMethod400(this.this$0).checkCPToFmsLink("e5", "e5database", this.this$0.getPropertyValue("e5.db.user"), this.this$0.getPropertyValue("e5.db.password"), this.this$0.getPropertyValue("e5.db.service"), this.this$0.getPropertyValue("e5.db.type"), this);
            CPInstallProperties.accessMethod400(this.this$0).checkFmsLinkToCP("e5", "e5database", "cplink", "export_list_for_e5", "vsecpids", this);
            return true;
         }
      }
   }

   private boolean checkDB2(String newValue) {
      CPInstallProperties.accessMethod400(this.this$0).checkCPToFmsLink("e5", "e5database", this.this$0.getPropertyValue("e5.db.user"), this.this$0.getPropertyValue("e5.db.password"), this.this$0.getPropertyValue("e5.db.service"), this.this$0.getPropertyValue("e5.db.type"), this);
      String[] urlSplit = this.this$0.getPropertyValue("e5.db.url").split(":");
      String e5Host = urlSplit.length > 2?urlSplit[2]:"";
      if(e5Host.startsWith("//")) {
         e5Host = e5Host.substring(2);
      }

      InetAddress hostIp = CPInstallProperties.accessMethod000(this.this$0, e5Host);
      if(hostIp != null) {
         this.addFeedback("IP address=" + e5Host + "=" + hostIp.getHostAddress());
         String hostUrlName = this.this$0.getPropertyValue("e5.db.url");
         hostUrlName = hostUrlName + ":currentSQLID=" + CPInstallProperties.accessMethod600(this.this$0, this.this$0.getPropertyValue("e5.db.schema"), 8) + ";";
         Connection conn = CPInstallProperties.accessMethod300(this.this$0, new String[]{"db2jcc.jar", "db2jcc_license_cisuz.jar"}, "com.ibm.db2.jcc.DB2Driver", hostUrlName, this.this$0.getPropertyValue("e5.db.user"), newValue, this);
         if(conn != null) {
            try {
               this.addFeedback("jdbc driver version=" + conn.getMetaData().getDriverVersion());
               this.addFeedback("e5 db2 version=" + conn.getMetaData().getDatabaseMajorVersion() + "." + conn.getMetaData().getDatabaseMinorVersion());
            } catch (SQLException var8) {
               ;
            }

            CPInstallProperties$CPDao e5Dao = new CPInstallProperties$CPDao(this.this$0, conn);
            e5Dao.checkE5onDB2Objects(this);
            e5Dao.closeConnection();
         }

         return true;
      } else {
         this.addFeedback("e5 db host " + e5Host + " not found");
         return false;
      }
   }
}
