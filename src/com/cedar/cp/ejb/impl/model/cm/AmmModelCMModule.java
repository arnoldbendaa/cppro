// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cm;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.event.RemoveDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.RemoveHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.RemoveHierarchyEvent;
import com.cedar.cp.dto.model.amm.AllAmmModelsELO;
import com.cedar.cp.dto.model.amm.AmmModelPK;
import com.cedar.cp.ejb.impl.cm.event.RemoveCalendarYearEvent;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMMetaDataController;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateAdapter;
import com.cedar.cp.ejb.impl.model.amm.AmmModelDAO;
import com.cedar.cp.util.Log;

public class AmmModelCMModule extends CMUpdateAdapter {

   private transient Log mLog = new Log(this.getClass());
   private CMMetaDataController mController;
   private AmmModelDAO mAmmModelDAO;
   private boolean mDoReferentialIntegrityChecks;


   public AmmModelCMModule(CMMetaDataController controller) {
      this.mController = controller;
   }

   public void initProcessing(ModelRef modelRef) {
      this.mDoReferentialIntegrityChecks = false;
   }

   public void processEvent(RemoveHierarchyEvent event) {
      this.mDoReferentialIntegrityChecks = true;
   }

   public void processEvent(RemoveDimensionElementEvent event) {
      this.mDoReferentialIntegrityChecks = true;
   }

   public void processEvent(RemoveHierarchyElementEvent event) {
      this.mDoReferentialIntegrityChecks = true;
   }

   public void processEvent(RemoveCalendarYearEvent event) {
      this.mDoReferentialIntegrityChecks = true;
   }

   public void terminateProcessing(ModelRef modelRef) {
      if(this.mDoReferentialIntegrityChecks) {
         Integer thisModelId = Integer.valueOf(this.mController.getModelEVO().getPK().getModelId());
         AllAmmModelsELO mappingList = this.getAmmModelDAO().getAllAmmModels();

         for(int i = 0; i < mappingList.getNumRows(); ++i) {
            EntityRef ammModelRef = (EntityRef)mappingList.getValueAt(i, "AmmModel");
            int ammModelId = ((AmmModelPK)ammModelRef.getPrimaryKey()).getAmmModelId();
            Integer targetModelId = (Integer)mappingList.getValueAt(i, "ModelId");
            String targetVisId = (String)mappingList.getValueAt(i, "col2");
            Integer sourceModelId = (Integer)mappingList.getValueAt(i, "SrcModelId");
            String sourceVisId = (String)mappingList.getValueAt(i, "col5");
            boolean isTarget = thisModelId.equals(targetModelId);
            boolean isSource = thisModelId.equals(sourceModelId);
            if(isSource || isTarget) {
               boolean mappingInvalidated = this.getAmmModelDAO().checkReferentialIntegrity(ammModelId, this.mController.getTaskId().intValue());
               if(mappingInvalidated) {
                  this.logInfo("Aggregated Model Mapping: " + targetVisId + " <-- " + sourceVisId + " has been invalidated");
                  this.logInfo("  - please correct the mapping via the Aggregated Models application.");
               }
            }
         }

      }
   }

   private void log(String msg) {
      try {
         this.mController.getTaskMessageLogger().log(msg);
      } catch (Exception var3) {
         this.mLog.debug("unable to log task message");
         throw new RuntimeException(var3);
      }
   }

   private void logInfo(String msg) {
      this.mController.getTaskMessageLogger().logInfo(msg);
   }

   private AmmModelDAO getAmmModelDAO() {
      if(this.mAmmModelDAO == null) {
         this.mAmmModelDAO = new AmmModelDAO();
      }

      return this.mAmmModelDAO;
   }
}
