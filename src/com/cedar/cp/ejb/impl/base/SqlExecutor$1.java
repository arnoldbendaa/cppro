// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.base;

import com.cedar.cp.ejb.impl.base.SqlExecutor;
import com.cedar.cp.ejb.impl.base.SqlExecutor$BindVariable;
import java.util.Comparator;

class SqlExecutor$1 implements Comparator<SqlExecutor$BindVariable> {

   // $FF: synthetic field
   final SqlExecutor this$0;


   SqlExecutor$1(SqlExecutor var1) {
      this.this$0 = var1;
   }

   public int compare(SqlExecutor$BindVariable o1, SqlExecutor$BindVariable o2) {
      return o1.getPosition() - o2.getPosition();
   }
}
