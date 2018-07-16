// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.cm;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cm.ChangeMgmtEditorSessionCSO;
import com.cedar.cp.dto.cm.ChangeMgmtEditorSessionSSO;
import com.cedar.cp.dto.cm.ChangeMgmtImpl;
import com.cedar.cp.dto.cm.ChangeMgmtPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.cm.ChangeMgmtEditorSessionHome;
import com.cedar.cp.ejb.api.cm.ChangeMgmtEditorSessionLocal;
import com.cedar.cp.ejb.api.cm.ChangeMgmtEditorSessionLocalHome;
import com.cedar.cp.ejb.api.cm.ChangeMgmtEditorSessionRemote;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class ChangeMgmtEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/ChangeMgmtEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/ChangeMgmtEditorSessionLocalHome";
   protected ChangeMgmtEditorSessionRemote mRemote;
   protected ChangeMgmtEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());
   public static ChangeMgmtEditorSessionSEJB server = new ChangeMgmtEditorSessionSEJB();

   public ChangeMgmtEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public ChangeMgmtEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private ChangeMgmtEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            ChangeMgmtEditorSessionHome e = (ChangeMgmtEditorSessionHome)this.getHome(jndiName, ChangeMgmtEditorSessionHome.class);
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

   private ChangeMgmtEditorSessionSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            ChangeMgmtEditorSessionLocalHome e = (ChangeMgmtEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public ModelRef[] getAvailableRelatedModelRefs() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ModelRef[] ret = (ModelRef[])((ModelRef[])this.getConnection().getListHelper().getAllModels().getValues("Model"));
      if(timer != null) {
         timer.logDebug("getAvailableRelatedModelRefs", "");
      }

      return ret;
   }

   public ChangeMgmtEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ChangeMgmtEditorSessionSSO e = null;
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

   public ChangeMgmtEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ChangeMgmtEditorSessionSSO e = null;
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

   public ChangeMgmtPK insert(ChangeMgmtImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ChangeMgmtPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new ChangeMgmtEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new ChangeMgmtEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public ChangeMgmtPK copy(ChangeMgmtImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         ChangeMgmtPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new ChangeMgmtEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new ChangeMgmtEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(ChangeMgmtImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new ChangeMgmtEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new ChangeMgmtEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void tidyBudgetState(int modelId) throws CPException, ValidationException {
      try {
         if(this.isRemoteConnection()) {
            this.getRemote().tidyBudgetState(modelId);
         } else {
            this.getLocal().tidyBudgetState(modelId);
         }

      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/ChangeMgmtEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/ChangeMgmtEditorSessionLocalHome";
   }
}
