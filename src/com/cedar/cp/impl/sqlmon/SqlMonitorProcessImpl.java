// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.sqlmon;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.sqlmon.SqlDetails;
import com.cedar.cp.api.sqlmon.SqlMonitorProcess;
import com.cedar.cp.api.sqlmon.SqlMonitorSession;
import com.cedar.cp.ejb.api.sqlmon.SqlMonitorProcessServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.base.CPConnectionImpl;
import com.cedar.cp.impl.sqlmon.SqlMonitorSessionImpl;

public class SqlMonitorProcessImpl extends BusinessProcessImpl implements SqlMonitorProcess {

   private EntityList mAllOracleSessions;


   public SqlMonitorProcessImpl(CPConnectionImpl connection) {
      super(connection);
   }

   public EntityList getAllOracleSessions() {
      this.refresh();
      this.ensureListsBuilt();
      return this.mAllOracleSessions;
   }

   public SqlDetails getSqlDetails(Object key) {
      try {
         SqlMonitorProcessServer e = new SqlMonitorProcessServer(this.getConnection());
         return e.getSqlDetails(key);
      } catch (Exception var3) {
         throw new RuntimeException("exception in getSqlDetails", var3);
      }
   }

   public void deleteObject(Object primaryKey) throws ValidationException {}

   public void refresh() {
      this.mAllOracleSessions = null;
   }

   public SqlMonitorSession getSqlMonitorSession(Object key) {
      try {
         return new SqlMonitorSessionImpl(this, key);
      } catch (CPException var3) {
         var3.printStackTrace();
         throw new RuntimeException("SqlMonitorSession", var3);
      }
   }

   private void ensureListsBuilt() {
      try {
         if(this.mAllOracleSessions == null) {
            SqlMonitorProcessServer e = new SqlMonitorProcessServer(this.getConnection());
            this.mAllOracleSessions = e.getAllOracleSessions();
         }

      } catch (Exception var2) {
         throw new RuntimeException("exception in ensureListsBuilt", var2);
      }
   }

   protected int getProcessID() {
      return 24;
   }
}
