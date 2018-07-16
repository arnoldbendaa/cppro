// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.sqlmon;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.sqlmon.SqlDetails;
import com.cedar.cp.api.sqlmon.SqlMonitorSession;
import com.cedar.cp.ejb.api.sqlmon.SqlMonitorProcessServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.sqlmon.SqlMonitorProcessImpl;

public class SqlMonitorSessionImpl extends BusinessSessionImpl implements SqlMonitorSession {

   private Object mKey;


   public SqlMonitorSessionImpl(SqlMonitorProcessImpl process, Object key) throws CPException {
      super(process);
      this.mKey = key;
   }

   public SqlDetails getSqlDetails() {
      try {
         SqlMonitorProcessServer e = new SqlMonitorProcessServer(this.getConnection());
         return e.getSqlDetails(this.mKey);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new CPException("Can\'t get SqlMonitorSessionImpl", var2);
      }
   }

   public Object getPrimaryKey() {
      return this.mKey;
   }

   public void terminate() {}

   public Object persistModifications(boolean cloneOnSave) {
      return null;
   }
}
