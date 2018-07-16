// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$InstallProperty;
import com.cedar.cp.util.CPInstallProperties$OracleServiceProperty;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CPInstallProperties$OraclePasswordProperty extends CPInstallProperties$InstallProperty {

   String mServiceNameProperty;
   String mUserNameProperty;
   String mSchemaNameProperty;
   boolean mConnectable;
   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$OraclePasswordProperty(CPInstallProperties var1, String prompt, String serviceName, String userName, String schemaName) {
      super(var1, prompt, new String[0]);
      this.this$0 = var1;
      this.mConnectable = false;
      this.mServiceNameProperty = serviceName;
      this.mUserNameProperty = userName;
      this.mSchemaNameProperty = schemaName;
   }

   public boolean checkProperty(String newValue) {
      this.mFeedback.clear();
      if(((CPInstallProperties$OracleServiceProperty)this.this$0.getProperty(this.mServiceNameProperty)).getHost() == null) {
         return true;
      } else {
         try {
            PrintWriter userName = new PrintWriter(new FileWriter("temp.sql"));
            userName.println("set head off");
            userName.println("select \'VERSION=\'||PROPERTY_VALUE from DATABASE_PROPERTIES");
            userName.println("where  PROPERTY_NAME=\'NLS_RDBMS_VERSION\';");
            userName.println("exit");
            userName.flush();
            userName.close();
         } catch (IOException var8) {
            var8.printStackTrace();
            return false;
         }

         String userName1 = this.this$0.getProperty(this.mUserNameProperty).getValue();
         String serviceName = this.this$0.getProperty(this.mServiceNameProperty).getValue();
         this.addFeedback("sqlplus: " + userName1 + "/" + newValue + "@" + serviceName);
         String stdout = CPInstallProperties.accessMethod100(this.this$0, new String[]{"sqlplus", "-L", userName1 + "/" + newValue + "@" + serviceName, "@temp.sql"}, this);
         Scanner scanner = new Scanner(stdout);
         String serverVersion = null;

         while(scanner.hasNext()) {
            String token = scanner.next();
            if("SQL*Plus:".equals(token)) {
               scanner.next();
            } else if(token.startsWith("VERSION=")) {
               serverVersion = token.substring(8);
            } else {
               if("ORA-01017:".equals(token)) {
                  this.addFeedback("sqlplus: invalid username/password");
                  return false;
               }

               if("ORA-12154:".equals(token)) {
                  this.addFeedback("sqlplus: service not found");
                  return false;
               }

               if("timed out".equalsIgnoreCase(token)) {
                  this.addFeedback("sqlplus: timed out");
                  return false;
               }
            }
         }

         if(serverVersion != null && !CPInstallProperties.accessMethod200(this.this$0, serverVersion)) {
            this.addFeedback("server version=" + serverVersion + " - not supported");
         } else {
            this.addFeedback("server version=" + serverVersion);
         }

         this.mConnectable = true;
         return true;
      }
   }

   public boolean isConnectable() {
      return this.mConnectable;
   }
}
