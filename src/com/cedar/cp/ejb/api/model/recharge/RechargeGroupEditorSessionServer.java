// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.recharge;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.recharge.RechargeGroupEditorSessionCSO;
import com.cedar.cp.dto.model.recharge.RechargeGroupEditorSessionSSO;
import com.cedar.cp.dto.model.recharge.RechargeGroupImpl;
import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.recharge.RechargeGroupEditorSessionHome;
import com.cedar.cp.ejb.api.model.recharge.RechargeGroupEditorSessionLocal;
import com.cedar.cp.ejb.api.model.recharge.RechargeGroupEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.recharge.RechargeGroupEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class RechargeGroupEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/RechargeGroupEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/RechargeGroupEditorSessionLocalHome";
   protected RechargeGroupEditorSessionRemote mRemote;
   protected RechargeGroupEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public RechargeGroupEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public RechargeGroupEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private RechargeGroupEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            RechargeGroupEditorSessionHome e = (RechargeGroupEditorSessionHome)this.getHome(jndiName, RechargeGroupEditorSessionHome.class);
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

   private RechargeGroupEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            RechargeGroupEditorSessionLocalHome e = (RechargeGroupEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
            this.mLocal = e.create();
         } catch (CreateException var2) {
            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
         }
      }

      return this.mLocal;
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

   public RechargeGroupEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         RechargeGroupEditorSessionSSO e = null;
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

   public RechargeGroupEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         RechargeGroupEditorSessionSSO e = null;
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

   public RechargeGroupPK insert(RechargeGroupImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         RechargeGroupPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new RechargeGroupEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new RechargeGroupEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public RechargeGroupPK copy(RechargeGroupImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         RechargeGroupPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new RechargeGroupEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new RechargeGroupEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(RechargeGroupImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new RechargeGroupEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new RechargeGroupEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/RechargeGroupEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/RechargeGroupEditorSessionLocalHome";
   }
}
