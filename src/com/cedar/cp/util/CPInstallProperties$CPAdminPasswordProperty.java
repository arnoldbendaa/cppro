// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$CPDao;
import com.cedar.cp.util.CPInstallProperties$HostNameProperty;
import com.cedar.cp.util.CPInstallProperties$OraclePasswordProperty;
import com.cedar.cp.util.CPInstallProperties$OracleServiceProperty;
import java.net.InetAddress;
import java.sql.Connection;

class CPInstallProperties$CPAdminPasswordProperty extends CPInstallProperties$OraclePasswordProperty {

   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$CPAdminPasswordProperty(CPInstallProperties var1, String prompt) {
      super(var1, prompt, "cp.db.service", "cp.db.admin.user", "cp.db.admin.user");
      this.this$0 = var1;
   }

   public boolean checkProperty(String newValue) {
      if(((CPInstallProperties$OracleServiceProperty)this.this$0.getProperty(this.mServiceNameProperty)).getHost() == null) {
         return true;
      } else if(this.this$0.isNewInstall()) {
         super.checkProperty(newValue);
         return true;
      } else if(!super.checkProperty(newValue)) {
         return false;
      } else {
         String tnspingHost = ((CPInstallProperties$OracleServiceProperty)this.this$0.getProperty("cp.db.service")).getHost();
         String tnspingPort = ((CPInstallProperties$OracleServiceProperty)this.this$0.getProperty("cp.db.service")).getPort();
         String tnspingSid = ((CPInstallProperties$OracleServiceProperty)this.this$0.getProperty("cp.db.service")).getSid();
         String cpHostIpAddr = ((CPInstallProperties$HostNameProperty)this.this$0.getProperty("cp.db.host")).getIpAddr();
         if(tnspingHost != null) {
            InetAddress hostUrlName = CPInstallProperties.accessMethod000(this.this$0, tnspingHost);
            if(hostUrlName == null) {
               this.addFeedback("service host <" + tnspingHost + "> not found");
               this.this$0.setPropertiesValid(false);
            } else if(cpHostIpAddr != null && !cpHostIpAddr.equals(hostUrlName.getHostAddress())) {
               this.addFeedback("service host <" + cpHostIpAddr + "> not matched with <" + hostUrlName.getHostAddress() + ">");
               this.this$0.setPropertiesValid(false);
            }
         }

         if(tnspingPort != null && !tnspingPort.equalsIgnoreCase(this.this$0.getPropertyValue("cp.db.port"))) {
            this.addFeedback("service specifies port <" + tnspingPort + ">");
            this.this$0.setPropertiesValid(false);
         }

         if(tnspingSid != null && !tnspingSid.equalsIgnoreCase(this.this$0.getPropertyValue("cp.db.sid"))) {
            this.addFeedback("service specifies sid <" + tnspingSid + ">");
            this.this$0.setPropertiesValid(false);
         }

         String hostUrlName1 = "jdbc:oracle:thin:@" + this.this$0.getPropertyValue("cp.db.host") + ":" + this.this$0.getPropertyValue("cp.db.port") + ":" + this.this$0.getPropertyValue("cp.db.sid");
         Connection cpConn = CPInstallProperties.accessMethod300(this.this$0, new String[]{"ojdbc14.jar"}, "oracle.jdbc.driver.OracleDriver", hostUrlName1, this.this$0.getPropertyValue("cp.db.admin.user"), newValue, this);
         if(cpConn == null) {
            return false;
         } else {
            CPInstallProperties.accessMethod402(this.this$0, new CPInstallProperties$CPDao(this.this$0, cpConn));

            try {
               this.addFeedback("jdbc: driver version=" + CPInstallProperties.accessMethod400(this.this$0).getConnection().getMetaData().getDriverVersion());
            } catch (Exception var9) {
               ;
            }

            String[] tables = new String[]{"MODEL", "DIMENSION", "DIMENSION_ELEMENT", "HIERARCHY", "HIERARCHY_ELEMENT", "STRUCTURE_ELEMENT", "FINANCE_CUBE", "TASK", "TASK_EVENT"};
            CPInstallProperties.accessMethod500(this.this$0, CPInstallProperties.accessMethod400(this.this$0), "oracle", tables, this);
            return true;
         }
      }
   }
}
