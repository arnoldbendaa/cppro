// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.udeflookup;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.dto.udeflookup.UdefLookupEditorSessionCSO;
import com.cedar.cp.dto.udeflookup.UdefLookupEditorSessionSSO;
import com.cedar.cp.dto.udeflookup.UdefLookupImpl;
import com.cedar.cp.dto.udeflookup.UdefLookupPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.udeflookup.UdefLookupEditorSessionHome;
import com.cedar.cp.ejb.api.udeflookup.UdefLookupEditorSessionLocal;
import com.cedar.cp.ejb.api.udeflookup.UdefLookupEditorSessionLocalHome;
import com.cedar.cp.ejb.api.udeflookup.UdefLookupEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class UdefLookupEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/UdefLookupEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/UdefLookupEditorSessionLocalHome";
   protected UdefLookupEditorSessionRemote mRemote;
   protected UdefLookupEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public UdefLookupEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public UdefLookupEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private UdefLookupEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            UdefLookupEditorSessionHome e = (UdefLookupEditorSessionHome)this.getHome(jndiName, UdefLookupEditorSessionHome.class);
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

   private UdefLookupEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            UdefLookupEditorSessionLocalHome e = (UdefLookupEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public UdefLookupEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         UdefLookupEditorSessionSSO e = null;
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

   public UdefLookupEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         UdefLookupEditorSessionSSO e = null;
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

   public UdefLookupPK insert(UdefLookupImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         UdefLookupPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new UdefLookupEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new UdefLookupEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public UdefLookupPK copy(UdefLookupImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         UdefLookupPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new UdefLookupEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new UdefLookupEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(UdefLookupImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new UdefLookupEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new UdefLookupEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void saveTableData(UdefLookupImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().saveTableData(new UdefLookupEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().saveTableData(new UdefLookupEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("saveTableData", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void issueRebuild(int userId) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().issueRebuild(userId);
         } else {
            this.getLocal().issueRebuild(userId);
         }

         if(timer != null) {
            timer.logDebug("issueRebuild");
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int[] issueRebuild(int userId, String desc, UdefLookupPK pk, int issuingTaskId) throws CPException, ValidationException {
//      if(this.mLog.isDebugEnabled()) {
//         new Timer(this.mLog);
//      } else {
//         Object var10000 = null;
//      }
      
      Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

      try {
         return this.isRemoteConnection()?this.getRemote().issueRebuild(userId, desc, pk, issuingTaskId):this.getLocal().issueRebuild(userId, desc, pk, issuingTaskId);
      } catch (Exception var7) {
         throw this.unravelException(var7);
      }
   }

   public EntityList getUdefForms(Object key) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().getUdefForms(key):this.getLocal().getUdefForms(key);
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public UdefLookup getUdefLookup(String visId) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().getUdefLookup(visId):this.getLocal().getUdefLookup(visId);
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/UdefLookupEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/UdefLookupEditorSessionLocalHome";
   }
}
