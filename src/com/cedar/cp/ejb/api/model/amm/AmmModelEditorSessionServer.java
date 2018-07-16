// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.amm;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.amm.AmmMap;
import com.cedar.cp.dto.model.amm.AmmModelEditorSessionCSO;
import com.cedar.cp.dto.model.amm.AmmModelEditorSessionSSO;
import com.cedar.cp.dto.model.amm.AmmModelImpl;
import com.cedar.cp.dto.model.amm.AmmModelPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.amm.AmmModelEditorSessionHome;
import com.cedar.cp.ejb.api.model.amm.AmmModelEditorSessionLocal;
import com.cedar.cp.ejb.api.model.amm.AmmModelEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.amm.AmmModelEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.CreateException;
import javax.naming.Context;

public class AmmModelEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/AmmModelEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/AmmModelEditorSessionLocalHome";
   protected AmmModelEditorSessionRemote mRemote;
   protected AmmModelEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public AmmModelEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public AmmModelEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private AmmModelEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            AmmModelEditorSessionHome e = (AmmModelEditorSessionHome)this.getHome(jndiName, AmmModelEditorSessionHome.class);
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

   private AmmModelEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            AmmModelEditorSessionLocalHome e = (AmmModelEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public ModelRef[] getAvailableTargetModelRefs() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ModelRef[] ret = (ModelRef[])((ModelRef[])this.getConnection().getListHelper().getAllModels().getValues("Model"));
      if(timer != null) {
         timer.logDebug("getAvailableTargetModelRefs", "");
      }

      return ret;
   }

   public ModelRef[] getAvailableSourceModelRefs() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ModelRef[] ret = (ModelRef[])((ModelRef[])this.getConnection().getListHelper().getAllModels().getValues("Model"));
      if(timer != null) {
         timer.logDebug("getAvailableSourceModelRefs", "");
      }

      return ret;
   }

   public AmmModelEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         AmmModelEditorSessionSSO e = null;
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

   public AmmModelEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         AmmModelEditorSessionSSO e = null;
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

   public AmmModelPK insert(AmmModelImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         AmmModelPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new AmmModelEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new AmmModelEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public AmmModelPK copy(AmmModelImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         AmmModelPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new AmmModelEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new AmmModelEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(AmmModelImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new AmmModelEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new AmmModelEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int issueAggregatedModelTask(int userId, List refreshList) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().issueAggregatedModelTask(userId, refreshList):this.getLocal().issueAggregatedModelTask(userId, refreshList);
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public AmmMap getAmmMap() throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().getAmmMap():this.getLocal().getAmmMap();
      } catch (Exception var2) {
         throw this.unravelException(var2);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/AmmModelEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/AmmModelEditorSessionLocalHome";
   }
}
