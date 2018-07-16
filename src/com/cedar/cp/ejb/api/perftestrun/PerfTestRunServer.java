// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.perftestrun;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.perftestrun.PerfTestRunHome;
import com.cedar.cp.ejb.api.perftestrun.PerfTestRunLocal;
import com.cedar.cp.ejb.api.perftestrun.PerfTestRunLocalHome;
import com.cedar.cp.ejb.api.perftestrun.PerfTestRunRemote;
import com.cedar.cp.util.Log;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class PerfTestRunServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/PerfTestRunRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/PerfTestRunLocalHome";
   protected PerfTestRunRemote mRemote;
   protected PerfTestRunLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public PerfTestRunServer(CPConnection conn_) {
      super(conn_);
   }

   public PerfTestRunServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private PerfTestRunRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            PerfTestRunHome e = (PerfTestRunHome)this.getHome(jndiName, PerfTestRunHome.class);
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

   private PerfTestRunLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            PerfTestRunLocalHome e = (PerfTestRunLocalHome)this.getLocalHome(this.getLocalJNDIName());
            this.mLocal = e.create();
         } catch (CreateException var2) {
            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
         }
      }

      return this.mLocal;
   }

   public void removeSession() throws CPException {}

   public long executePerfTest(String className) throws ValidationException {
      long result = -1L;

      try {
         if(this.isRemoteConnection()) {
            result = this.getRemote().executePerfTest(className);
         } else {
            result = this.getLocal().executePerfTest(className);
         }

         return result;
      } catch (Exception var5) {
         throw this.unravelException(var5);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/PerfTestRunRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/PerfTestRunLocalHome";
   }
}
