// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementCategoryEditorSession;
import com.cedar.cp.api.model.virement.VirementCategorysProcess;
import com.cedar.cp.ejb.api.model.virement.VirementCategoryEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.virement.VirementCategoryEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class VirementCategorysProcessImpl extends BusinessProcessImpl implements VirementCategorysProcess {

   private Log mLog = new Log(this.getClass());


   public VirementCategorysProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      VirementCategoryEditorSessionServer es = new VirementCategoryEditorSessionServer(this.getConnection());

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

   public VirementCategoryEditorSession getVirementCategoryEditorSession(Object key) throws ValidationException {
      VirementCategoryEditorSessionImpl sess = new VirementCategoryEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllVirementCategorys() {
      try {
         return this.getConnection().getListHelper().getAllVirementCategorys();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllVirementCategorys", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing VirementCategory";
      return ret;
   }

   protected int getProcessID() {
      return 61;
   }

   public void validateVirementPosting(boolean to, ModelRef modelRef, FinanceCubeRef financeCubeRef, String respobsibilityAreaVisId, String accountElementVisId, double temporaryVirement, double permanentVirement) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      VirementCategoryEditorSessionServer es = new VirementCategoryEditorSessionServer(this.getConnection());

      try {
         es.validateVirementPosting(to, modelRef, financeCubeRef, respobsibilityAreaVisId, accountElementVisId, temporaryVirement, permanentVirement);
      } catch (ValidationException var13) {
         throw var13;
      } catch (CPException var14) {
         throw new RuntimeException("can\'t validateVirementPosting", var14);
      }

      if(timer != null) {
         timer.logDebug("validateVirementPosting");
      }

   }
}
