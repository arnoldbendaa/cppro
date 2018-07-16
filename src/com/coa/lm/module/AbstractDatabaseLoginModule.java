// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 12:10:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lm.module;

import com.coa.lm.module.AbstractLoginModule;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;

public abstract class AbstractDatabaseLoginModule extends AbstractLoginModule {

   private String mUserQuery;


   public abstract Connection getConnection() throws Exception;

   public boolean authenticateUser(String userName, char[] password) throws Exception {
      Connection connection = null;

      boolean var14;
      try {
         connection = this.getConnection();
         PreparedStatement statement = connection.prepareStatement(this.mUserQuery);
         statement.setString(1, userName);
         ResultSet results = statement.executeQuery();
         String dbCredential = null;
         if(results.next()) {
            dbCredential = results.getString(1);
         }

         results.close();
         statement.close();
         boolean valid = false;
         if(dbCredential != null) {
            char[] credentials = dbCredential.toCharArray();
            if(credentials.length == password.length) {
               for(int i = 0; i < credentials.length; ++i) {
                  if(credentials[i] != password[i]) {
                     boolean var10 = false;
                     return var10;
                  }
               }

               valid = true;
            }
         }

         var14 = valid;
      } finally {
         if(connection != null) {
            connection.close();
         }

      }

      return var14;
   }

   public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
      super.initialize(subject, callbackHandler, sharedState, options);
      String dbUserTable = (String)options.get("userTable");
      String dbUserTableUserField = (String)options.get("userField");
      String dbUserTableCredentialField = (String)options.get("credentialField");
      this.mUserQuery = "select " + dbUserTableCredentialField + "  from " + dbUserTable + " where " + dbUserTableUserField + " = ? ";
   }
}
