// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.dimension;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyEditorSessionCSO;
import com.cedar.cp.dto.dimension.HierarchyEditorSessionSSO;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.dimension.HierarchyEditorSessionHome;
import com.cedar.cp.ejb.api.dimension.HierarchyEditorSessionLocal;
import com.cedar.cp.ejb.api.dimension.HierarchyEditorSessionLocalHome;
import com.cedar.cp.ejb.api.dimension.HierarchyEditorSessionRemote;
import com.cedar.cp.ejb.impl.dimension.HierarchyEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import javax.naming.Context;

public class HierarchyEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/HierarchyEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/HierarchyEditorSessionLocalHome";
   protected HierarchyEditorSessionRemote mRemote;
   protected HierarchyEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());
   public static HierarchyEditorSessionSEJB server = new HierarchyEditorSessionSEJB();

   public HierarchyEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public HierarchyEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private HierarchyEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            HierarchyEditorSessionHome e = (HierarchyEditorSessionHome)this.getHome(jndiName, HierarchyEditorSessionHome.class);
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

   private HierarchyEditorSessionSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            HierarchyEditorSessionLocalHome e = (HierarchyEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public HierarchyEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         HierarchyEditorSessionSSO e = null;
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

   public HierarchyEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         HierarchyEditorSessionSSO e = null;
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

   public HierarchyCK insert(HierarchyImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         HierarchyCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new HierarchyEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new HierarchyEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public HierarchyCK copy(HierarchyImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         HierarchyCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new HierarchyEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new HierarchyEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(HierarchyImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new HierarchyEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new HierarchyEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public List processEvents(List clientEvents) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         List e = this.getRemote().processEvents(clientEvents);
         if(timer != null) {
            timer.logDebug("HierarchyEditorSessionSSO:processEvents");
         }

         return e;
      } catch (CreateException var4) {
         throw new CPException("Failed on processEvenets()", var4);
      } catch (RemoteException var5) {
         throw this.unravelException(var5);
      }
   }

   public EntityList getAvailableDimensionsForInsert(int dimensionType) throws CPException {
      try {
         return this.getRemote().getAvailableDimensionsForInsert(dimensionType);
      } catch (CreateException var3) {
         throw new CPException("getAvailableDimensionsForInsert", var3);
      } catch (RemoteException var4) {
         throw new CPException("getAvailableDimensionsForInsert", var4);
      }
   }

   public EntityList queryPathToRoots(List<StructureElementKey> elements) {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         EntityList e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().queryPathToRoots(elements);
         } else {
            e = this.getLocal().queryPathToRoots(elements);
         }

         if(timer != null) {
            timer.logDebug("queryPathToRoots");
         }

         return e;
      } catch (Exception var4) {
         throw new CPException("queryPathToRoots", var4);
      }
   }

   public Map<DimensionRef, EntityList> getFilteredTreeRoots(Map<DimensionRef, Map<StructureElementRef, Boolean>> filters) {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         Map e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getFilteredTreeRoots(filters);
         } else {
            e = this.getLocal().getFilteredTreeRoots(filters);
         }

         if(timer != null) {
            timer.logDebug("getFilteredTreeRoots");
         }

         return e;
      } catch (Exception var4) {
         throw new CPException("getFilteredTreeRoots", var4);
      }
   }

   public EntityList getImmediateChildrenWithFilter(StructureElementRef seRef, Map<StructureElementRef, Boolean> filters) {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         EntityList e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getImmediateChildrenWithFilter(seRef, filters);
         } else {
            e = this.getLocal().getImmediateChildrenWithFilter(seRef, filters);
         }

         if(timer != null) {
            timer.logDebug("getImmediateChildrenWithFilter");
         }

         return e;
      } catch (Exception var5) {
         throw new CPException("getImmediateChildrenWithFilter", var5);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/HierarchyEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/HierarchyEditorSessionLocalHome";
   }
}
