// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.dimension;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.DimensionEditorSessionCSO;
import com.cedar.cp.dto.dimension.DimensionEditorSessionSSO;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.dimension.DimensionEditorSessionHome;
import com.cedar.cp.ejb.api.dimension.DimensionEditorSessionLocal;
import com.cedar.cp.ejb.api.dimension.DimensionEditorSessionLocalHome;
import com.cedar.cp.ejb.api.dimension.DimensionEditorSessionRemote;
import com.cedar.cp.ejb.impl.dimension.DimensionEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import javax.naming.Context;

public class DimensionEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/DimensionEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/DimensionEditorSessionLocalHome";
   protected DimensionEditorSessionRemote mRemote;
   protected DimensionEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());
   DimensionEditorSessionSEJB server = new DimensionEditorSessionSEJB();


   public DimensionEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public DimensionEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private DimensionEditorSessionSEJB getRemote() {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            DimensionEditorSessionHome e = (DimensionEditorSessionHome)this.getHome(jndiName, DimensionEditorSessionHome.class);
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

   private DimensionEditorSessionSEJB getLocal() {
//      if(this.mLocal == null) {
//         try {
//            DimensionEditorSessionLocalHome e = (DimensionEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
//            this.mLocal = e.create();
//         } catch (CreateException var2) {
//            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
//         }
//      }
//
//      return this.mLocal;
	   return this.server;
   }

   public void removeSession() throws CPException {
      try {
         if(this.mRemote != null) {
            this.mRemote.remove();
            this.mRemote = null;
         } else if(this.mLocal != null) {
            this.mLocal.remove();
            this.mLocal = null;
         }
      } catch (RemoveException var2) {
         ;
      } catch (RemoteException var3) {
         ;
      }

   }

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

   public DimensionEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DimensionEditorSessionSSO e = null;
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

   public DimensionEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DimensionEditorSessionSSO e = null;
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

   public DimensionPK insert(DimensionImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DimensionPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new DimensionEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new DimensionEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public DimensionPK copy(DimensionImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DimensionPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new DimensionEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new DimensionEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(DimensionImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new DimensionEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new DimensionEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public List processEvents(List events) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      List e;
	 if(this.isRemoteConnection()) {
	    e = this.getRemote().processEvents(events);
	 } else {
	    e = this.getLocal().processEvents(events);
	 }

	 if(timer != null) {
	    timer.logDebug("DimensionEditorSessionSSO:processEvents");
	 }

	 return e;
   }

   public String getRemoteJNDIName() {
      return "ejb/DimensionEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/DimensionEditorSessionLocalHome";
   }
}
