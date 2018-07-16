// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.CubeUpdateHome;
import com.cedar.cp.ejb.api.model.CubeUpdateLocal;
import com.cedar.cp.ejb.api.model.CubeUpdateLocalHome;
import com.cedar.cp.ejb.api.model.CubeUpdateRemote;
import com.cedar.cp.ejb.impl.model.CubeUpdateSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class CubeUpdateServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/CubeUpdateRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/CubeUpdateLocalHome";
   protected CubeUpdateRemote mRemote;
   protected CubeUpdateLocal mLocal;
   private Log mLog = new Log(this.getClass());
   public CubeUpdateSEJB server = new CubeUpdateSEJB();

   public CubeUpdateServer(CPConnection conn_) {
      super(conn_);
   }

   public CubeUpdateServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private CubeUpdateSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            CubeUpdateHome e = (CubeUpdateHome)this.getHome(jndiName, CubeUpdateHome.class);
//            this.mRemote = e.create();
//         } catch (CreateException var3) {
//            this.removeFromCache(jndiName);
//            var3.printStackTrace();
//            throw new CPException("getRemote " + jndiName + " CreateException", var3);
//         } catch (RemoteException var4) {
//            this.removeFromCache(jndiName);
//            var4.printStackTrace();
//            throw new CPException("getRemote " + jndiName + " RemoteException", var4);
//         }
//      }
//
//      return this.mRemote;
	   return this.server;
   }

   private CubeUpdateSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            CubeUpdateLocalHome e = (CubeUpdateLocalHome)this.getLocalHome(this.getLocalJNDIName());
//            this.mLocal = e.create();
//         } catch (CreateException var2) {
//            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
//         }
//      }
//
//      return this.mLocal;
	   return this.server;
   }

   public void removeSession() throws CPException {}

   public void executeCubeUpdate(String xmlUpdate) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().executeCubeUpdate(xmlUpdate);
         } else {
            this.getLocal().executeCubeUpdate(xmlUpdate);
         }

         if(timer != null) {
            timer.logDebug("executeCubeUpdate", "");
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void executeFlatFormUpdate(String xmlUpdate) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().executeFlatFormUpdate(xmlUpdate);
         } else {
            this.getLocal().executeFlatFormUpdate(xmlUpdate);
         }

         if(timer != null) {
            timer.logDebug("executeFlatFormUpdate", "");
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/CubeUpdateRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/CubeUpdateLocalHome";
   }
}
