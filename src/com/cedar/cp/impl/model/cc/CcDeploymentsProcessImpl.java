// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.cc;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.cc.CcDeploymentEditorSession;
import com.cedar.cp.api.model.cc.CcDeploymentsProcess;
import com.cedar.cp.ejb.api.model.cc.CcDeploymentEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.cc.CcDeploymentEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.List;

public class CcDeploymentsProcessImpl extends BusinessProcessImpl implements CcDeploymentsProcess {

   private Log mLog = new Log(this.getClass());


   public CcDeploymentsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      CcDeploymentEditorSessionServer es = new CcDeploymentEditorSessionServer(this.getConnection());

      try {
         es.delete(primaryKey);
      } catch (ValidationException var5) {
         throw var5;
      } catch (CPException var6) {
         throw new RuntimeException("can\'t delete " + primaryKey, var6);
      }

      if(timer != null) {
         timer.logDebug("deleteObject", primaryKey);
      }

   }

   public CcDeploymentEditorSession getCcDeploymentEditorSession(Object key) throws ValidationException {
      CcDeploymentEditorSessionImpl sess = new CcDeploymentEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllCcDeployments() {
      try {
         return this.getConnection().getListHelper().getAllCcDeployments();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllCcDeployments", var2);
      }
   }

   public EntityList getCcDeploymentsForLookupTable(String param1) {
      try {
         return this.getConnection().getListHelper().getCcDeploymentsForLookupTable(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get CcDeploymentsForLookupTable", var3);
      }
   }

   public EntityList getCcDeploymentsForXmlForm(int param1) {
      try {
         return this.getConnection().getListHelper().getCcDeploymentsForXmlForm(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get CcDeploymentsForXmlForm", var3);
      }
   }

   public EntityList getCcDeploymentsForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getCcDeploymentsForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get CcDeploymentsForModel", var3);
      }
   }

   public EntityList getCcDeploymentCellPickerInfo(int param1) {
      try {
         return this.getConnection().getListHelper().getCcDeploymentCellPickerInfo(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get CcDeploymentCellPickerInfo", var3);
      }
   }

   public EntityList getCcDeploymentXMLFormType(int param1) {
      try {
         return this.getConnection().getListHelper().getCcDeploymentXMLFormType(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get CcDeploymentXMLFormType", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing CcDeployment";
      return ret;
   }

   protected int getProcessID() {
      return 90;
   }

   public EntityList getAvailableModels() {
      return null;
   }

   public int[] issueCellCalcRebuildTask(List<Object[]> rebuildList) throws ValidationException {
      CcDeploymentEditorSessionServer es = new CcDeploymentEditorSessionServer(this.getConnection());

      try {
         return es.issueCellCalcRebuildTask(rebuildList);
      } catch (ValidationException var4) {
         throw var4;
      } catch (CPException var5) {
         throw new RuntimeException("can\'t submit", var5);
      }
   }
}
