// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.frmwrk;

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
import com.cedar.cp.dto.dimension.event.RemoveHierarchyEvent;
import com.cedar.cp.dto.dimension.event.UpdateDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.UpdateHierarchyElementEvent;
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
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateListener;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;

public class CMUpdateAdapter implements CMUpdateListener {

   public void initProcessing(ModelRef modelRef) {}

   public void terminateProcessing(ModelRef modelRef) {}

   public void initProcessing(DimensionDAG dimension) {}

   public void terminateProcessing(DimensionDAG dimension) {}

   public void processEvent(InsertHierarchyEvent event) {}

   public void processEvent(RemoveHierarchyEvent event) {}

   public void initProcessing(HierarchyDAG dimension) {}

   public void terminateProcessing(HierarchyDAG dimension) {}

   public void processEvent(InsertDimensionElementEvent event) {}

   public void processEvent(RemoveDimensionElementEvent event) {}

   public void processEvent(UpdateDimensionElementEvent event) {}

   public void processEvent(InsertHierarchyElementEvent event) {}

   public void processEvent(RemoveHierarchyElementEvent event) {}

   public void processEvent(MoveHierarchyElementEvent event) {}

   public void processEvent(UpdateHierarchyElementEvent event) {}

   public void processEvent(InsertHierarchyElementFeedEvent event) {}

   public void processEvent(RemoveHierarchyElementFeedEvent event) {}

   public void processEvent(MoveHierarchyElementFeedEvent event) {}

   public void processEvent(InsertedDimensionElementEvent event) {}

   public void processEvent(RemovedDimensionElementEvent event) {}

   public void processEvent(UpdatedDimensionElementEvent event) {}

   public void processEvent(InsertedHierarchyElementEvent event) {}

   public void processEvent(RemovedHierarchyElementEvent event) {}

   public void processEvent(MovedHierarchyElementEvent event) {}

   public void processEvent(UpdatedHierarchyElementEvent event) {}

   public void processEvent(InsertedHierarchyElementFeedEvent event) {}

   public void processEvent(RemovedHierarchyElementFeedEvent event) {}

   public void processEvent(MovedHierarchyElementFeedEvent event) {}

   public void processEvent(InsertCalendarYearEvent event) {}

   public void processEvent(UpdateCalendarYearEvent event) {}

   public void processEvent(RemoveCalendarYearEvent event) {}
}
