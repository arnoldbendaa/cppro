// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cm;

import com.cedar.cp.ejb.impl.cm.frmwrk.CMMetaDataController;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateAdapter;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.model.BudgetStateDAO;
import com.cedar.cp.ejb.impl.model.BudgetStateHistoryDAO;
import com.cedar.cp.ejb.impl.model.BudgetUserDAO;

public class BudgetLocationCMModule extends CMUpdateAdapter {

   private BudgetStateHistoryDAO mBudgetStateHistoryDAO;
   private BudgetStateDAO mBudgetStateDAO;
   private BudgetUserDAO mBudgetUserDAO;
   private CMMetaDataController mController;


   public BudgetLocationCMModule(CMMetaDataController controller) {
      this.mController = controller;
   }

   public void terminateProcessing(HierarchyDAG hierarchy) {
      int structureId = this.mController.getModelEVO().getBudgetHierarchyId();
      if(structureId == hierarchy.getHierarchyId()) {
         int modelId = this.mController.getModelEVO().getModelId();
         this.getBudgetUserDAO().tidyOrphanBudgetUsers(modelId, structureId);
         this.getBudgetStateDAO().tidyOrphanBudgetStates(modelId, structureId);
         this.getBudgetStateHistoryDAO().tidyOrphanBudgetStates(modelId, structureId);
      }

   }

   public CMMetaDataController getController() {
      return this.mController;
   }

   private BudgetUserDAO getBudgetUserDAO() {
      if(this.mBudgetUserDAO == null) {
         this.mBudgetUserDAO = new BudgetUserDAO();
      }

      return this.mBudgetUserDAO;
   }

   private BudgetStateDAO getBudgetStateDAO() {
      if(this.mBudgetStateDAO == null) {
         this.mBudgetStateDAO = new BudgetStateDAO();
      }

      return this.mBudgetStateDAO;
   }

   private BudgetStateHistoryDAO getBudgetStateHistoryDAO() {
      if(this.mBudgetStateHistoryDAO == null) {
         this.mBudgetStateHistoryDAO = new BudgetStateHistoryDAO();
      }

      return this.mBudgetStateHistoryDAO;
   }
}
