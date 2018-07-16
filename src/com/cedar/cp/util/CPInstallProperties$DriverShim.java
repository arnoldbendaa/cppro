// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

  class  CPInstallProperties$DriverShim  implements   Driver {

   private Driver driver;
   // $FF: synthetic field
   final CPInstallProperties this$0;
 
 public java.util.logging.Logger getParentLogger()
                       throws java.sql.SQLFeatureNotSupportedException {
      throw new java.sql.SQLFeatureNotSupportedException("getParentLogger not supported");
    }
 
   CPInstallProperties$DriverShim(CPInstallProperties var1, Driver d) {
      this.this$0 = var1;
      this.driver = d;
     
   }

   public boolean acceptsURL(String u) throws SQLException {
      return this.driver.acceptsURL(u);
   }

   public Connection connect(String u, Properties p) throws SQLException {
      return this.driver.connect(u, p);
   }

   public int getMajorVersion() {
      return this.driver.getMajorVersion();
   }

   public int getMinorVersion() {
      return this.driver.getMinorVersion();
   }

   public DriverPropertyInfo[] getPropertyInfo(String u, Properties p) throws SQLException {
      return this.driver.getPropertyInfo(u, p);
   }

   public boolean jdbcCompliant() {
      return this.driver.jdbcCompliant();
   }
}
