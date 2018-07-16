// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cm;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.event.InsertHierarchyEvent;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.ejb.impl.cm.event.InsertCalendarYearEvent;
import com.cedar.cp.ejb.impl.cm.event.RemoveCalendarYearEvent;
import com.cedar.cp.ejb.impl.cm.event.UpdateCalendarYearEvent;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMMetaDataController;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateAdapter;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelDAO;

public class ModelMappingCMModule extends CMUpdateAdapter {

   private boolean mCalendarUpdated;
   private boolean mHierarchyInserted;
   private CMMetaDataController mController;


   public ModelMappingCMModule(CMMetaDataController controller) {
      this.mController = controller;
   }

   public void initProcessing(ModelRef modelRef) {
      this.mCalendarUpdated = false;
      this.mHierarchyInserted = false;
   }

   public void processEvent(InsertHierarchyEvent event) {
      this.mHierarchyInserted = true;
   }

   public void processEvent(InsertCalendarYearEvent event) {
      this.mCalendarUpdated = true;
   }

   public void processEvent(UpdateCalendarYearEvent event) {
      this.mCalendarUpdated = true;
   }

   public void processEvent(RemoveCalendarYearEvent event) {
      this.mCalendarUpdated = true;
   }

   public void terminateProcessing(ModelRef modelRef) {
      MappedModelDAO mmDAO;
      MappedModelPK mmPK;
      if(this.mCalendarUpdated) {
         mmDAO = new MappedModelDAO();
         mmPK = mmDAO.getMappedModel(modelRef);
         if(mmPK != null) {
            try {
               this.mController.getMappedModelEditorSessionServer().refreshMappedModelCalendar(this.mController.getUserId(), mmPK);
            } catch (Exception var6) {
               var6.printStackTrace();
               throw new IllegalStateException("Failed to refresh mapped model calendar dimension elements", var6);
            }
         }

         this.mCalendarUpdated = false;
      }

      if(this.mHierarchyInserted) {
         mmDAO = new MappedModelDAO();
         mmPK = mmDAO.getMappedModel(modelRef);
         if(mmPK != null) {
            try {
               this.mController.getMappedModelEditorSessionServer().refreshMappedModelHierarchy(this.mController.getUserId(), mmPK);
            } catch (Exception var5) {
               var5.printStackTrace();
               throw new IllegalStateException("Failed to refresh mapped model hierarchy", var5);
            }
         }

         this.mHierarchyInserted = false;
      }

   }
}
