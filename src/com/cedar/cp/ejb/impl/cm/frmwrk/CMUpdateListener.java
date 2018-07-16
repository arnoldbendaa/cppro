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
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;

public interface CMUpdateListener {

   void initProcessing(ModelRef var1);

   void terminateProcessing(ModelRef var1);

   void initProcessing(DimensionDAG var1);

   void terminateProcessing(DimensionDAG var1);

   void initProcessing(HierarchyDAG var1);

   void terminateProcessing(HierarchyDAG var1);

   void processEvent(InsertHierarchyEvent var1);

   void processEvent(RemoveHierarchyEvent var1);

   void processEvent(InsertDimensionElementEvent var1);

   void processEvent(RemoveDimensionElementEvent var1);

   void processEvent(UpdateDimensionElementEvent var1);

   void processEvent(InsertHierarchyElementEvent var1);

   void processEvent(RemoveHierarchyElementEvent var1);

   void processEvent(MoveHierarchyElementEvent var1);

   void processEvent(UpdateHierarchyElementEvent var1);

   void processEvent(InsertHierarchyElementFeedEvent var1);

   void processEvent(RemoveHierarchyElementFeedEvent var1);

   void processEvent(MoveHierarchyElementFeedEvent var1);

   void processEvent(InsertedDimensionElementEvent var1);

   void processEvent(RemovedDimensionElementEvent var1);

   void processEvent(UpdatedDimensionElementEvent var1);

   void processEvent(InsertedHierarchyElementEvent var1);

   void processEvent(RemovedHierarchyElementEvent var1);

   void processEvent(MovedHierarchyElementEvent var1);

   void processEvent(UpdatedHierarchyElementEvent var1);

   void processEvent(InsertedHierarchyElementFeedEvent var1);

   void processEvent(RemovedHierarchyElementFeedEvent var1);

   void processEvent(MovedHierarchyElementFeedEvent var1);

   void processEvent(InsertCalendarYearEvent var1);

   void processEvent(UpdateCalendarYearEvent var1);

   void processEvent(RemoveCalendarYearEvent var1);
}
