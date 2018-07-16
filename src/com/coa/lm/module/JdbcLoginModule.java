// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 12:10:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lm.module;

import com.coa.lm.module.AbstractDatabaseLoginModule;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;

public class JdbcLoginModule extends AbstractDatabaseLoginModule {

   private String dbDriver;
   private String dbUrl;
   private String dbUserName;
   private String dbPassword;


   public Connection getConnection() throws Exception {
      if(this.dbDriver != null && this.dbUrl != null) {
         return DriverManager.getConnection(this.dbUrl, this.dbUserName, this.dbPassword);
      } else {
         throw new IllegalStateException("Database connection information not configured");
      }
   }

   public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
      try {
         super.initialize(subject, callbackHandler, sharedState, options);
         this.dbDriver = (String)options.get("dbDriver");
         this.dbUrl = (String)options.get("dbUrl");
         this.dbUserName = (String)options.get("dbUserName");
         this.dbPassword = (String)options.get("dbPassword");
         if(this.dbUserName == null) {
            this.dbUserName = "";
         }

         if(this.dbPassword == null) {
            this.dbPassword = "";
         }

         if(this.dbDriver != null) {
            Class.forName(this.dbDriver);
         }

      } catch (ClassNotFoundException var6) {
         throw new IllegalStateException(var6.toString());
      }
   }
}
