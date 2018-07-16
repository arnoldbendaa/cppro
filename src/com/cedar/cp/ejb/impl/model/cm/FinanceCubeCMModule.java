// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cm;

import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.dto.dimension.event.InsertHierarchyEvent;
import com.cedar.cp.dto.dimension.event.RemoveHierarchyEvent;
import com.cedar.cp.dto.dimension.event.UpdateDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.UpdateHierarchyElementEvent;
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
import com.cedar.cp.ejb.impl.cm.frmwrk.CMMetaDataController;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateAdapter;
import com.cedar.cp.ejb.impl.dimension.CalendarYearSpecDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;
import com.cedar.cp.util.Log;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FinanceCubeCMModule extends CMUpdateAdapter {

   private Log mLog = new Log(this.getClass());
   private CMMetaDataController mController;
   private Set mNewDimensionElements = new HashSet();
   private Set mNewHierarchyElements = new HashSet();
   private Set mNewHiearrchyElementFeeds = new HashSet();


   public FinanceCubeCMModule(CMMetaDataController controller) {
      this.mController = controller;
   }

   public void terminateProcessing(DimensionDAG dimension) {
      this.mNewDimensionElements.clear();
   }

   public void terminateProcessing(HierarchyDAG hierarchy) {
      this.checkNewHierarchyElements();
      this.mNewHierarchyElements.clear();
      this.mNewHiearrchyElementFeeds.clear();
   }

   public void processEvent(InsertHierarchyEvent event) {
      this.mController.registerAllFinanceCubesForRebuild();
   }

   public void processEvent(RemoveHierarchyEvent event) {
      this.mController.registerAllFinanceCubesForRebuild();
   }

   public void processEvent(InsertedDimensionElementEvent event) {
      this.mNewDimensionElements.add(event.getElement().getPK());
   }

   public void processEvent(UpdateDimensionElementEvent event) {}

   public void processEvent(RemovedDimensionElementEvent event) {
      DimensionElementDAG element = this.mController.getDimension().findElement(event.getElement().getPK());
      if(element == null) {
         this.mController.registerAllFinanceCubesForRebuild();
      } else {
         this.mNewDimensionElements.remove(element.getPK());
      }

   }

   public void processEvent(InsertedHierarchyElementEvent event) {
      this.mNewHierarchyElements.add(event.getElement().getPK());
   }

   public void processEvent(UpdateHierarchyElementEvent event) {}

   public void processEvent(MovedHierarchyElementEvent event) {
      this.mController.registerAllFinanceCubesForRebuild();
   }

   public void processEvent(MovedHierarchyElementFeedEvent event) {
      this.mController.registerAllFinanceCubesForRebuild();
   }

   public void processEvent(RemovedHierarchyElementEvent event) {
      HierarchyElementDAG element = event.getElement();
      this.mNewHierarchyElements.remove(element.getPK());
      List children = element.getAllChildren(false);
      Iterator cIter = children.iterator();

      while(cIter.hasNext()) {
         HierarchyNodeDAG child = (HierarchyNodeDAG)cIter.next();
         if(child.isFeeder()) {
            HierarchyElementFeedDAG heChild = (HierarchyElementFeedDAG)child;
            this.mNewHiearrchyElementFeeds.remove(heChild.getPK());
         } else {
            HierarchyElementDAG heChild1 = (HierarchyElementDAG)child;
            this.mNewHierarchyElements.remove(heChild1.getPK());
         }
      }

      this.mController.registerAllFinanceCubesForRebuild();
   }

   public void processEvent(InsertedHierarchyElementFeedEvent event) {
      if(!this.mNewDimensionElements.contains(event.getElementFeed().getDimensionElement().getPK())) {
         ;
      }

      this.mController.registerAllFinanceCubesForRebuild();
      this.mNewHiearrchyElementFeeds.add(event.getElementFeed().getPK());
   }

   public void processEvent(RemovedHierarchyElementFeedEvent event) {
      if(!this.mNewDimensionElements.contains(event.getElementFeed().getDimensionElement().getPK())) {
         this.mController.registerAllFinanceCubesForRebuild();
      }

   }

   private void checkNewHierarchyElements() {
      HierarchyDAG hierarchy = this.mController.getHierarchy();
      Iterator heIter = this.mNewHierarchyElements.iterator();

      HierarchyNodeDAG element;
      do {
         if(!heIter.hasNext()) {
            return;
         }

         HierarchyElementPK hePK = (HierarchyElementPK)heIter.next();
         element = hierarchy.find(hePK.getHierarchyElementId());
         if(element == null) {
            throw new IllegalStateException("Unable to locate new hierarchy element:" + hePK);
         }
      } while(this.areAllChildrenNew(element));

      this.mController.registerAllFinanceCubesForRebuild();
   }

   private boolean areAllChildrenNew(HierarchyNodeDAG element) {
      Iterator cIter = element.getChildren() != null?element.getChildren().iterator():Collections.EMPTY_LIST.iterator();

      while(cIter.hasNext()) {
         HierarchyNodeDAG child = (HierarchyNodeDAG)cIter.next();
         if(child instanceof HierarchyElementDAG) {
            HierarchyElementDAG hefDAG = (HierarchyElementDAG)child;
            if(!this.mNewHierarchyElements.contains(hefDAG.getPK())) {
               return false;
            }

            if(!this.areAllChildrenNew(hefDAG)) {
               return false;
            }
         } else {
            if(!(child instanceof HierarchyElementFeedDAG)) {
               throw new IllegalStateException("Unknown subclass of HierarchyNodeDAG");
            }

            HierarchyElementFeedDAG hefDAG1 = (HierarchyElementFeedDAG)child;
            if(!this.mNewHiearrchyElementFeeds.contains(hefDAG1.getDimensionElement().getPK())) {
               return false;
            }
         }
      }

      return true;
   }

   public void processEvent(UpdateCalendarYearEvent event) {
      CalendarYearSpecDAG originalYearSpec = event.getOldYearSpec();
      CalendarYearSpecImpl newYearSpec = event.getNewYearSpec();
      if(originalYearSpec != null && newYearSpec != null && originalYearSpec.getLeafLevel() != newYearSpec.getLeafLevel()) {
         this.mController.registerAllFinanceCubesForRebuild();
      }

      if(originalYearSpec != null && newYearSpec == null) {
         this.mController.registerAllFinanceCubesForRebuild();
      }

   }

   public void processEvent(RemoveCalendarYearEvent event) {
      this.mController.registerAllFinanceCubesForRebuild();
   }
}
