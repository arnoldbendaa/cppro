// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.performance;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.performance.PerformanceHome;
import com.cedar.cp.ejb.api.performance.PerformanceLocal;
import com.cedar.cp.ejb.api.performance.PerformanceLocalHome;
import com.cedar.cp.ejb.api.performance.PerformanceRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.performance.PerformanceData;
import com.cedar.cp.util.performance.PerformanceDatum;
import com.cedar.cp.util.performance.PerformanceType;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class PerformanceServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/PerformanceRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/PerformanceLocalHome";
   protected PerformanceRemote mRemote;
   protected PerformanceLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public PerformanceServer(CPConnection conn_) {
      super(conn_);
   }

   public PerformanceServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private PerformanceRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            PerformanceHome e = (PerformanceHome)this.getHome(jndiName, PerformanceHome.class);
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

   private PerformanceLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            PerformanceLocalHome e = (PerformanceLocalHome)this.getLocalHome(this.getLocalJNDIName());
            this.mLocal = e.create();
         } catch (CreateException var2) {
            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
         }
      }

      return this.mLocal;
   }

   public void removeSession() throws CPException {}

   public PerformanceData getPerformanceData() throws ValidationException {
      PerformanceData result = null;

      try {
         if(this.isRemoteConnection()) {
            result = this.getRemote().getPerformanceData();
         } else {
            result = this.getLocal().getPerformanceData();
         }

         return result;
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public PerformanceType getPerformanceType(String type) throws ValidationException {
      PerformanceType result = null;

      try {
         if(this.isRemoteConnection()) {
            result = this.getRemote().getPerformanceType(type);
         } else {
            result = this.getLocal().getPerformanceType(type);
         }

         return result;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public PerformanceDatum getPerformanceDatum(String id) throws ValidationException {
      PerformanceDatum result = null;

      try {
         if(this.isRemoteConnection()) {
            result = this.getRemote().getPerformanceDatum(id);
         } else {
            result = this.getLocal().getPerformanceDatum(id);
         }

         return result;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/PerformanceRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/PerformanceLocalHome";
   }
}
