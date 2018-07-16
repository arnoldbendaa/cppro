// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.dimension;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.SecurityRangeCK;
import com.cedar.cp.dto.dimension.SecurityRangeEditorSessionCSO;
import com.cedar.cp.dto.dimension.SecurityRangeEditorSessionSSO;
import com.cedar.cp.dto.dimension.SecurityRangeImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.dimension.SecurityRangeEditorSessionHome;
import com.cedar.cp.ejb.api.dimension.SecurityRangeEditorSessionLocal;
import com.cedar.cp.ejb.api.dimension.SecurityRangeEditorSessionLocalHome;
import com.cedar.cp.ejb.api.dimension.SecurityRangeEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class SecurityRangeEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/SecurityRangeEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/SecurityRangeEditorSessionLocalHome";
   protected SecurityRangeEditorSessionRemote mRemote;
   protected SecurityRangeEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public SecurityRangeEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public SecurityRangeEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private SecurityRangeEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            SecurityRangeEditorSessionHome e = (SecurityRangeEditorSessionHome)this.getHome(jndiName, SecurityRangeEditorSessionHome.class);
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

   private SecurityRangeEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            SecurityRangeEditorSessionLocalHome e = (SecurityRangeEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public EntityList getAvailableDimensions() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      EntityList ret = this.getConnection().getListHelper().getAllDimensions();
      if(timer != null) {
         timer.logDebug("getDimensionList", "");
      }

      return ret;
   }

   public EntityList getOwnershipRefs(Object pk_) throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         EntityList e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getOwnershipData(this.getUserId(), pk_);
         } else {
            e = this.getLocal().getOwnershipData(this.getUserId(), pk_);
         }

         if(timer != null) {
            timer.logDebug("getOwnershipRefs", pk_ != null?pk_.toString():"null");
         }

         return e;
      } catch (Exception var4) {
         throw new CPException("unable to getOwnershipRefs(" + pk_ + ") from server " + var4.getMessage(), var4);
      }
   }

   public SecurityRangeEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         SecurityRangeEditorSessionSSO e = null;
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

   public SecurityRangeEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         SecurityRangeEditorSessionSSO e = null;
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

   public SecurityRangeCK insert(SecurityRangeImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         SecurityRangeCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new SecurityRangeEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new SecurityRangeEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public SecurityRangeCK copy(SecurityRangeImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         SecurityRangeCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new SecurityRangeEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new SecurityRangeEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(SecurityRangeImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new SecurityRangeEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new SecurityRangeEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/SecurityRangeEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/SecurityRangeEditorSessionLocalHome";
   }
}
