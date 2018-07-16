// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.cubeformula;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cubeformula.CubeFormulaCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaEditorSessionCSO;
import com.cedar.cp.dto.cubeformula.CubeFormulaEditorSessionSSO;
import com.cedar.cp.dto.cubeformula.CubeFormulaImpl;
import com.cedar.cp.dto.cubeformula.CubeFormulaRefImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.cubeformula.CubeFormulaEditorSessionHome;
import com.cedar.cp.ejb.api.cubeformula.CubeFormulaEditorSessionLocal;
import com.cedar.cp.ejb.api.cubeformula.CubeFormulaEditorSessionLocalHome;
import com.cedar.cp.ejb.api.cubeformula.CubeFormulaEditorSessionRemote;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.CreateException;
import javax.naming.Context;

public class CubeFormulaEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/CubeFormulaEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/CubeFormulaEditorSessionLocalHome";
   protected CubeFormulaEditorSessionRemote mRemote;
   protected CubeFormulaEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());
   public static CubeFormulaEditorSessionSEJB server = new CubeFormulaEditorSessionSEJB();

   public CubeFormulaEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public CubeFormulaEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private CubeFormulaEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            CubeFormulaEditorSessionHome e = (CubeFormulaEditorSessionHome)this.getHome(jndiName, CubeFormulaEditorSessionHome.class);
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

   private CubeFormulaEditorSessionSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            CubeFormulaEditorSessionLocalHome e = (CubeFormulaEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public CubeFormulaEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CubeFormulaEditorSessionSSO e = null;
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

   public CubeFormulaEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CubeFormulaEditorSessionSSO e = null;
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

   public CubeFormulaCK insert(CubeFormulaImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CubeFormulaCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new CubeFormulaEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new CubeFormulaEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public CubeFormulaCK copy(CubeFormulaImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CubeFormulaCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new CubeFormulaEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new CubeFormulaEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(CubeFormulaImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new CubeFormulaEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new CubeFormulaEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void testCompilerFormula(int financeCubeId, int cubeFormulaId, String formulaText, int formulaType) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().testCompileFormula(financeCubeId, cubeFormulaId, formulaText, formulaType);
         } else {
            this.getLocal().testCompileFormula(financeCubeId, cubeFormulaId, formulaText, formulaType);
         }

         if(timer != null) {
            timer.logDebug("testCompileFormula");
         }

      } catch (Exception var7) {
         throw this.unravelException(var7);
      }
   }

   public int issueFormulaRebuildTask(ModelRef modelRef, FinanceCubeRef financeCubeRef, List<CubeFormulaRefImpl> cubeFormulaRefs) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         int e;
         if(this.isRemoteConnection()) {
            e = this.getRemote().issueRebuildTask(this.getUserId(), modelRef, financeCubeRef, cubeFormulaRefs);
         } else {
            e = this.getLocal().issueRebuildTask(this.getUserId(), modelRef, financeCubeRef, cubeFormulaRefs);
         }

         if(timer != null) {
            timer.logDebug("issueFormulaRebuildTask");
         }

         return e;
      } catch (Exception var6) {
         throw this.unravelException(var6);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/CubeFormulaEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/CubeFormulaEditorSessionLocalHome";
   }
}
