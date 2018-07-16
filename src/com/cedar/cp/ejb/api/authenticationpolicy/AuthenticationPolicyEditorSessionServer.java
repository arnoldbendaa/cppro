// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.authenticationpolicy;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyEditorSessionCSO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyEditorSessionSSO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyImpl;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.ejb.api.authenticationpolicy.AuthenticationPolicyEditorSessionHome;
import com.cedar.cp.ejb.api.authenticationpolicy.AuthenticationPolicyEditorSessionLocal;
import com.cedar.cp.ejb.api.authenticationpolicy.AuthenticationPolicyEditorSessionLocalHome;
import com.cedar.cp.ejb.api.authenticationpolicy.AuthenticationPolicyEditorSessionRemote;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class AuthenticationPolicyEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/AuthenticationPolicyEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/AuthenticationPolicyEditorSessionLocalHome";
   protected AuthenticationPolicyEditorSessionRemote mRemote;
   protected AuthenticationPolicyEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());
   public static AuthenticationPolicyEditorSessionSEJB server = new AuthenticationPolicyEditorSessionSEJB();

   public AuthenticationPolicyEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public AuthenticationPolicyEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private AuthenticationPolicyEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            AuthenticationPolicyEditorSessionHome e = (AuthenticationPolicyEditorSessionHome)this.getHome(jndiName, AuthenticationPolicyEditorSessionHome.class);
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

   private AuthenticationPolicyEditorSessionSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            AuthenticationPolicyEditorSessionLocalHome e = (AuthenticationPolicyEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public void delete(Object primaryKey_) throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().delete(this.getUserId(), primaryKey_);
         } else {
            this.getLocal().delete(this.getUserId(), primaryKey_);
         }

         if(timer != null) {
            timer.logDebug("delete", primaryKey_.toString());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public AuthenticationPolicyEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         AuthenticationPolicyEditorSessionSSO e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getNewItemData(this.getUserId());
         } else {
            e = this.getLocal().getNewItemData(this.getUserId());
         }

         if(timer != null) {
            timer.logDebug("getNewItemData", "");
         }

         return e;
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public AuthenticationPolicyEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         AuthenticationPolicyEditorSessionSSO e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getItemData(this.getUserId(), key);
         } else {
            e = this.getLocal().getItemData(this.getUserId(), key);
         }

         if(timer != null) {
            timer.logDebug("getItemData", key.toString());
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public AuthenticationPolicyPK insert(AuthenticationPolicyImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         AuthenticationPolicyPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new AuthenticationPolicyEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new AuthenticationPolicyEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public AuthenticationPolicyPK copy(AuthenticationPolicyImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         AuthenticationPolicyPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new AuthenticationPolicyEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new AuthenticationPolicyEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(AuthenticationPolicyImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new AuthenticationPolicyEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new AuthenticationPolicyEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/AuthenticationPolicyEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/AuthenticationPolicyEditorSessionLocalHome";
   }
}
