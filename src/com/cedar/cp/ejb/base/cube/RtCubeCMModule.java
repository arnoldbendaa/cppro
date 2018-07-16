// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.event.InsertDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.InsertHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.InsertHierarchyElementFeedEvent;
import com.cedar.cp.dto.dimension.event.InsertHierarchyEvent;
import com.cedar.cp.dto.dimension.event.MoveHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.MoveHierarchyElementFeedEvent;
import com.cedar.cp.dto.dimension.event.RemoveDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.RemoveHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.RemoveHierarchyElementFeedEvent;
import com.cedar.cp.dto.dimension.event.UpdateDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.UpdateHierarchyElementEvent;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.base.cube.RtCubeUtilsDAO;
import com.cedar.cp.ejb.impl.cm.event.InsertCalendarYearEvent;
import com.cedar.cp.ejb.impl.cm.event.InsertedDimensionElementEvent;
import com.cedar.cp.ejb.impl.cm.event.InsertedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.InsertedHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.MovedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.MovedHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.RemoveCalendarYearEvent;
import com.cedar.cp.ejb.impl.cm.event.RemovedDimensionElementEvent;
import com.cedar.cp.ejb.impl.cm.event.RemovedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.RemovedHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.UpdateCalendarYearEvent;
import com.cedar.cp.ejb.impl.cm.event.UpdatedDimensionElementEvent;
import com.cedar.cp.ejb.impl.cm.event.UpdatedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMMetaDataController;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateAdapter;

public class RtCubeCMModule extends CMUpdateAdapter {

   private boolean mNeedToRebuild;
   private CMMetaDataController mController;


   public RtCubeCMModule(CMMetaDataController controller) {
      this.mController = controller;
   }

   public void initProcessing(ModelRef modelRef) {
      this.mNeedToRebuild = false;
   }

   public void processEvent(InsertHierarchyEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(InsertDimensionElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(RemoveDimensionElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(UpdateDimensionElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(InsertHierarchyElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(RemoveHierarchyElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(MoveHierarchyElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(UpdateHierarchyElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(InsertHierarchyElementFeedEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(RemoveHierarchyElementFeedEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(MoveHierarchyElementFeedEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(InsertedDimensionElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(RemovedDimensionElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(UpdatedDimensionElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(InsertedHierarchyElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(RemovedHierarchyElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(MovedHierarchyElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(UpdatedHierarchyElementEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(InsertedHierarchyElementFeedEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(RemovedHierarchyElementFeedEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(MovedHierarchyElementFeedEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(InsertCalendarYearEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(UpdateCalendarYearEvent event) {
      this.mNeedToRebuild = true;
   }

   public void processEvent(RemoveCalendarYearEvent event) {
      this.mNeedToRebuild = true;
   }

   public void terminateProcessing(ModelRef modelRef) {
      if(this.mNeedToRebuild) {
         this.mNeedToRebuild = false;
         int modelId = ((ModelRefImpl)modelRef).getModelPK().getModelId();
         (new RtCubeUtilsDAO()).updateRtCubeDeployments(modelId, -1, -1, -1);
      }

   }
}
