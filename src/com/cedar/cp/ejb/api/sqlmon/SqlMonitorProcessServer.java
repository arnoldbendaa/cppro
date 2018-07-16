// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.sqlmon;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.sqlmon.SqlDetails;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.sqlmon.SqlMonitorProcessHome;
import com.cedar.cp.ejb.api.sqlmon.SqlMonitorProcessLocal;
import com.cedar.cp.ejb.api.sqlmon.SqlMonitorProcessLocalHome;
import com.cedar.cp.ejb.api.sqlmon.SqlMonitorProcessRemote;
import com.cedar.cp.util.Log;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class SqlMonitorProcessServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/SqlMonitorProcessRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/SqlMonitorProcessLocalHome";
   protected SqlMonitorProcessRemote mRemote;
   protected SqlMonitorProcessLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public SqlMonitorProcessServer(CPConnection conn_) {
      super(conn_);
   }

   public SqlMonitorProcessServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private SqlMonitorProcessRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            SqlMonitorProcessHome e = (SqlMonitorProcessHome)this.getHome(jndiName, SqlMonitorProcessHome.class);
            this.mRemote = e.create();
         } catch (CreateException var3) {
            this.removeFromCache(jndiName);
            var3.printStackTrace();
            throw new CPException("getRemote " + jndiName + " CreateException", var3);
         } catch (RemoteException var4) {
            this.removeFromCache(jndiName);
            var4.printStackTrace();
            throw new CPException("getRemote " + jndiName + " RemoteException", var4);
         }
      }

      return this.mRemote;
   }

   private SqlMonitorProcessLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            SqlMonitorProcessLocalHome e = (SqlMonitorProcessLocalHome)this.getLocalHome(this.getLocalJNDIName());
            this.mLocal = e.create();
         } catch (CreateException var2) {
            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
         }
      }

      return this.mLocal;
   }

   public void removeSession() throws CPException {}

   public EntityList getAllOracleSessions() throws ValidationException, CPException {
      try {
         EntityList e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getAllOracleSessions();
         } else {
            e = this.getLocal().getAllOracleSessions();
         }

         return e;
      } catch (Exception var2) {
         throw this.unravelException(var2);
      }
   }

   public SqlDetails getSqlDetails(Object key) throws ValidationException, CPException {
      try {
         SqlDetails e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getSqlDetails(key);
         } else {
            e = this.getLocal().getSqlDetails(key);
         }

         return e;
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/SqlMonitorProcessRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/SqlMonitorProcessLocalHome";
   }
}
