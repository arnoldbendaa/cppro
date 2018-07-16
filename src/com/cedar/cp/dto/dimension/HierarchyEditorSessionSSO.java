// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.HierarchyEvent;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HierarchyEditorSessionSSO implements Serializable {

   private List mEvents = new ArrayList();
   private DimensionRefImpl mDimensionRef;
   private HierarchyImpl mEditorData;


   public HierarchyEditorSessionSSO() {}

   public HierarchyEditorSessionSSO(HierarchyImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(HierarchyImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public HierarchyImpl getEditorData() {
      return this.mEditorData;
   }

   public List getEvents() {
      return this.mEvents;
   }

   public void setEvents(List events) {
      this.mEvents = events;
   }

   public void addEvent(HierarchyEvent event) {
      this.mEvents.add(event);
   }

   public DimensionRefImpl getDimensionRef() {
      return this.mDimensionRef;
   }

   public void setModelRef(DimensionRefImpl dimensionRef) {
      this.mDimensionRef = dimensionRef;
   }
}
