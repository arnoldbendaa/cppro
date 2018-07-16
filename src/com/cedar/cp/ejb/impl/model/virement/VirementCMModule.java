// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMMetaDataController;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateAdapter;
import com.cedar.cp.ejb.impl.model.virement.VirementAccountDAO;
import com.cedar.cp.ejb.impl.model.virement.VirementLocationDAO;

public class VirementCMModule extends CMUpdateAdapter {

   private transient VirementLocationDAO mVirementLocationDAO;
   private transient VirementAccountDAO mVirementAccountDAO;
   private CMMetaDataController mController;


   public VirementCMModule(CMMetaDataController controller) {
      this.mController = controller;
   }

   public void terminateProcessing(ModelRef modelRef) {
      this.getVirementLocationDAO().eraseOrphanedLocations(this.mController.getModelEVO().getModelId());
      this.getVirementAccountDAO().eraseOrphanedAccounts(this.mController.getModelEVO().getModelId());
   }

   private VirementLocationDAO getVirementLocationDAO() {
      if(this.mVirementLocationDAO == null) {
         this.mVirementLocationDAO = new VirementLocationDAO();
      }

      return this.mVirementLocationDAO;
   }

   private VirementAccountDAO getVirementAccountDAO() {
      if(this.mVirementAccountDAO == null) {
         this.mVirementAccountDAO = new VirementAccountDAO();
      }

      return this.mVirementAccountDAO;
   }
}
