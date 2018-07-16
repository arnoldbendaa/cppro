// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import java.sql.SQLException;

public class SqlExecutorException extends RuntimeException {

   boolean mIsSqlException;


   public SqlExecutorException(SQLException e) {
      super(e);
      this.mIsSqlException = true;
   }

   public SqlExecutorException(Exception e) {
      super(e);
      this.mIsSqlException = false;
   }

   public SqlExecutorException(String s) {
      super(s);
      this.mIsSqlException = false;
   }

   public boolean isSQLException() {
      return this.mIsSqlException;
   }

   public Integer getSqlCode() {
      return this.isSQLException()?Integer.valueOf(((SQLException)this.getCause()).getErrorCode()):null;
   }
}
