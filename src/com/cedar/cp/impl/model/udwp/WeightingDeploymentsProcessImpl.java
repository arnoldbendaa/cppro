// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.udwp;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.udwp.WeightingDeploymentEditorSession;
import com.cedar.cp.api.model.udwp.WeightingDeploymentsProcess;
import com.cedar.cp.ejb.api.model.udwp.WeightingDeploymentEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.udwp.WeightingDeploymentEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class WeightingDeploymentsProcessImpl extends BusinessProcessImpl implements WeightingDeploymentsProcess {

   private Log mLog = new Log(this.getClass());


   public WeightingDeploymentsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      WeightingDeploymentEditorSessionServer es = new WeightingDeploymentEditorSessionServer(this.getConnection());

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

   public WeightingDeploymentEditorSession getWeightingDeploymentEditorSession(Object key) throws ValidationException {
      WeightingDeploymentEditorSessionImpl sess = new WeightingDeploymentEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllWeightingDeployments() {
      try {
         return this.getConnection().getListHelper().getAllWeightingDeployments();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllWeightingDeployments", var2);
      }
   }
   
   public EntityList getAllWeightingDeploymentsForLoggedUser() {
      try {
         return this.getConnection().getListHelper().getAllWeightingDeploymentsForLoggedUser();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllWeightingDeploymentsForLoggedUser", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing WeightingDeployment";
      return ret;
   }

   protected int getProcessID() {
      return 82;
   }

   public EntityList queryDeployments() {
      WeightingDeploymentEditorSessionServer ss = new WeightingDeploymentEditorSessionServer(this.getConnection());
      return ss.queryDeployments();
   }
}
