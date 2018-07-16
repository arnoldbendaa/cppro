// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.CPAuthenticationPolicy;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.util.db.DBAccessor;
import java.sql.ResultSet;
import java.sql.SQLException;

class CPSystemProperties$DBHelper extends DBAccessor {

   private int mProcessing;
   // $FF: synthetic field
   final CPSystemProperties this$0;


   public CPSystemProperties$DBHelper(CPSystemProperties var1) {
      this.this$0 = var1;
      this.mProcessing = 0;
   }

   void loadProperties() {
      this.mProcessing = 1;
      CPSystemProperties.access$002(this.this$0, new CPAuthenticationPolicy());
      StringBuffer query = new StringBuffer();
      query.append("SELECT PRPRTY, VALUE ");
      query.append("  FROM SYSTEM_PROPERTY");
      query.append(" WHERE PRPRTY LIKE \'WEB:%\'");
      query.append("    OR PRPRTY LIKE \'SYS:%\'");
      query.append("    OR PRPRTY LIKE \'RST:%\'");
      query.append(" ORDER BY 1");
      this.executeQuery(query.toString());
   }

   void loadActiveAuthenticationPolicy() {
      this.mProcessing = 2;
      StringBuffer query = new StringBuffer();
      query.append("SELECT AUTHENTICATION_TECHNIQUE ");
      query.append("      ,COSIGN_CONFIGURATION_FILE");
      query.append("      ,NTLM_DOMAIN");
      query.append("      ,NTLM_DOMAIN_CONTROLLER");
      query.append("      ,NTLM_LOG_LEVEL");
      query.append("      ,NTLM_NETBIOS_WINS");
      query.append("      ,MAXIMUM_LOGON_ATTEMPTS");
      query.append("  FROM AUTHENTICATION_POLICY");
      query.append(" WHERE ACTIVE = \'Y\'");
      this.executeQuery(query.toString());
   }

   protected void processResultSet(ResultSet rs) throws SQLException {
      if(this.mProcessing == 1) {
         this.mLog.info("loaded properties:", "");

         String index;
         String value;
         for(; rs != null && rs.next(); CPSystemProperties.access$100(this.this$0).put(index, value)) {
            index = rs.getString(1);
            value = rs.getString(2);
            this.mLog.info("    " + index + "=" + value, "");
            if(value == null) {
               value = "";
            }
         }
      } else {
         if(rs == null || !rs.next()) {
            throw new SQLException("No Active Authetication Policy found");
         }

         byte var5 = 1;
         CPAuthenticationPolicy var10000 = CPSystemProperties.access$000(this.this$0);
         int var6 = var5 + 1;
         var10000.setAuthenticationTechnique(rs.getInt(var5));
         CPSystemProperties.access$000(this.this$0).setCosignConfigurationFile(rs.getString(var6++));
         CPSystemProperties.access$000(this.this$0).setNtlmDomain(rs.getString(var6++));
         CPSystemProperties.access$000(this.this$0).setNtlmDomainController(rs.getString(var6++));
         CPSystemProperties.access$000(this.this$0).setNtlmLogLevel(rs.getInt(var6++));
         CPSystemProperties.access$000(this.this$0).setNtlmNetbiosWins(rs.getString(var6++));
         CPSystemProperties.access$000(this.this$0).setMaximumLogonAttempts(rs.getInt(var6++));
         if(rs.next()) {
            throw new SQLException("More than one Active Authetication Policy found");
         }
      }

   }
}
