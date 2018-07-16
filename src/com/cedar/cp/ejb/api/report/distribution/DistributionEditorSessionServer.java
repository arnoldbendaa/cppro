// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.distribution;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.distribution.DistributionDetails;
import com.cedar.cp.dto.report.distribution.DistributionEditorSessionCSO;
import com.cedar.cp.dto.report.distribution.DistributionEditorSessionSSO;
import com.cedar.cp.dto.report.distribution.DistributionImpl;
import com.cedar.cp.dto.report.distribution.DistributionPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.report.distribution.DistributionEditorSessionHome;
import com.cedar.cp.ejb.api.report.distribution.DistributionEditorSessionLocal;
import com.cedar.cp.ejb.api.report.distribution.DistributionEditorSessionLocalHome;
import com.cedar.cp.ejb.api.report.distribution.DistributionEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class DistributionEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/DistributionEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/DistributionEditorSessionLocalHome";
   protected DistributionEditorSessionRemote mRemote;
   protected DistributionEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public DistributionEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public DistributionEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private DistributionEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            DistributionEditorSessionHome e = (DistributionEditorSessionHome)this.getHome(jndiName, DistributionEditorSessionHome.class);
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

   private DistributionEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            DistributionEditorSessionLocalHome e = (DistributionEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public DistributionEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DistributionEditorSessionSSO e = null;
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

   public DistributionEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DistributionEditorSessionSSO e = null;
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

   public DistributionPK insert(DistributionImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DistributionPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new DistributionEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new DistributionEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public DistributionPK copy(DistributionImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DistributionPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new DistributionEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new DistributionEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(DistributionImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new DistributionEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new DistributionEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public DistributionDetails getDistributionDetailList(int modelId, int structureElementId, EntityRef ref) throws CPException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().getDistributionDetailList(modelId, structureElementId, ref):this.getLocal().getDistributionDetailList(modelId, structureElementId, ref);
      } catch (Exception var5) {
         throw this.unravelException(var5);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/DistributionEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/DistributionEditorSessionLocalHome";
   }
}
