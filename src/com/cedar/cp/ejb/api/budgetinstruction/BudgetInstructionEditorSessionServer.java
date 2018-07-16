// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.budgetinstruction;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.Hierarchy;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionEditorSessionCSO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionEditorSessionSSO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionImpl;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.budgetinstruction.BudgetInstructionEditorSessionHome;
import com.cedar.cp.ejb.api.budgetinstruction.BudgetInstructionEditorSessionLocal;
import com.cedar.cp.ejb.api.budgetinstruction.BudgetInstructionEditorSessionLocalHome;
import com.cedar.cp.ejb.api.budgetinstruction.BudgetInstructionEditorSessionRemote;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class BudgetInstructionEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/BudgetInstructionEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/BudgetInstructionEditorSessionLocalHome";
   protected BudgetInstructionEditorSessionRemote mRemote;
   protected BudgetInstructionEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());
   public static BudgetInstructionEditorSessionSEJB server = new BudgetInstructionEditorSessionSEJB();

   public BudgetInstructionEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public BudgetInstructionEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private BudgetInstructionEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            BudgetInstructionEditorSessionHome e = (BudgetInstructionEditorSessionHome)this.getHome(jndiName, BudgetInstructionEditorSessionHome.class);
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

   private BudgetInstructionEditorSessionSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            BudgetInstructionEditorSessionLocalHome e = (BudgetInstructionEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public BudgetInstructionEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         BudgetInstructionEditorSessionSSO e = null;
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

   public BudgetInstructionEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         BudgetInstructionEditorSessionSSO e = null;
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

   public BudgetInstructionPK insert(BudgetInstructionImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         BudgetInstructionPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new BudgetInstructionEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new BudgetInstructionEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public BudgetInstructionPK copy(BudgetInstructionImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         BudgetInstructionPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new BudgetInstructionEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new BudgetInstructionEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(BudgetInstructionImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new BudgetInstructionEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new BudgetInstructionEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public Hierarchy getBudgetLocationHierarchy(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         Hierarchy e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getBudgetLocationHierarchy(primaryKey);
         } else {
            e = this.getLocal().getBudgetLocationHierarchy(primaryKey);
         }

         if(timer != null) {
            timer.logDebug("getBudgetLocationHierarchy", "");
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/BudgetInstructionEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/BudgetInstructionEditorSessionLocalHome";
   }
}
