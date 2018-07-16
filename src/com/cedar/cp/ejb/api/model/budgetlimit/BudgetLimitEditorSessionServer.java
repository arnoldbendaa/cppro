// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.budgetlimit;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitCK;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitEditorSessionCSO;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitEditorSessionSSO;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.budgetlimit.BudgetLimitEditorSessionHome;
import com.cedar.cp.ejb.api.model.budgetlimit.BudgetLimitEditorSessionLocal;
import com.cedar.cp.ejb.api.model.budgetlimit.BudgetLimitEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.budgetlimit.BudgetLimitEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.CreateException;
import javax.naming.Context;

public class BudgetLimitEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/BudgetLimitEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/BudgetLimitEditorSessionLocalHome";
   protected BudgetLimitEditorSessionRemote mRemote;
   protected BudgetLimitEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public BudgetLimitEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public BudgetLimitEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private BudgetLimitEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            BudgetLimitEditorSessionHome e = (BudgetLimitEditorSessionHome)this.getHome(jndiName, BudgetLimitEditorSessionHome.class);
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

   private BudgetLimitEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            BudgetLimitEditorSessionLocalHome e = (BudgetLimitEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public EntityList getAvailableFinanceCubes() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      EntityList ret = this.getConnection().getListHelper().getAllFinanceCubes();
      if(timer != null) {
         timer.logDebug("getFinanceCubeList", "");
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

   public BudgetLimitEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         BudgetLimitEditorSessionSSO e = null;
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

   public BudgetLimitEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         BudgetLimitEditorSessionSSO e = null;
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

   public BudgetLimitCK insert(BudgetLimitImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         BudgetLimitCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new BudgetLimitEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new BudgetLimitEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public BudgetLimitCK copy(BudgetLimitImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         BudgetLimitCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new BudgetLimitEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new BudgetLimitEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(BudgetLimitImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new BudgetLimitEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new BudgetLimitEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public List getImposedLimits(int numDims, int fcId, int budgetLocation) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      List value = null;

      try {
         if(this.isRemoteConnection()) {
            value = this.getRemote().getImposedLimits(numDims, fcId, budgetLocation);
         } else {
            value = this.getLocal().getImposedLimits(numDims, fcId, budgetLocation);
         }

         if(timer != null) {
            timer.logDebug("getImposedLimits", "dims " + numDims + " fc " + fcId + " budgetlocation " + budgetLocation);
         }

         return value;
      } catch (Exception var7) {
         throw this.unravelException(var7);
      }
   }

   public List getBudgetLimitsSetByBudgetLocation(int numDims, int fcId, int budgetLocation) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      List value = null;

      try {
         if(this.isRemoteConnection()) {
            value = this.getRemote().getBudgetLimitsSetByBudgetLocation(numDims, fcId, budgetLocation);
         } else {
            value = this.getLocal().getBudgetLimitsSetByBudgetLocation(numDims, fcId, budgetLocation);
         }

         if(timer != null) {
            timer.logDebug("getBudgetLimitsSetByBudgetLocation", "dims " + numDims + " fc " + fcId + " budgetlocation " + budgetLocation);
         }

         return value;
      } catch (Exception var7) {
         throw this.unravelException(var7);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/BudgetLimitEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/BudgetLimitEditorSessionLocalHome";
   }
}
