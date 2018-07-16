// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.sqlmon;

import com.cedar.cp.api.sqlmon.SqlDetails;
import java.io.Serializable;

public class SqlDetailsImpl implements SqlDetails, Serializable {

   private String mDetails;
   private String mSql;


   public void setDetails(String s) {
      this.mDetails = s;
   }

   public String getDetails() {
      return this.mDetails;
   }

   public void setSql(String sql) {
      this.mSql = sql;
   }

   public String getSql() {
      return this.mSql;
   }
}
