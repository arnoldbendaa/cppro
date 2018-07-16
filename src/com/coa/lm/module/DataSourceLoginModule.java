// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 12:10:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lm.module;

import com.coa.lm.module.AbstractDatabaseLoginModule;
import java.sql.Connection;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.sql.DataSource;

public class DataSourceLoginModule extends AbstractDatabaseLoginModule {

   private DataSource mDataSource;


   public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
      try {
         super.initialize(subject, callbackHandler, sharedState, options);
         String e = (String)options.get("dbJNDIName");
         InitialContext ic = new InitialContext();
         this.mDataSource = (DataSource)ic.lookup("java:comp/env/" + e);
      } catch (NamingException var7) {
         throw new IllegalStateException(var7.toString());
      }
   }

   public Connection getConnection() throws Exception {
      return this.mDataSource.getConnection();
   }
}
