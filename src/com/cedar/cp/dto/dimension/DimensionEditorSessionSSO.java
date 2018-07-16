// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DimensionEditorSessionSSO implements Serializable {

   private List mEvents = new ArrayList();
   private ModelRefImpl mModelRef;
   private DimensionImpl mEditorData;


   public DimensionEditorSessionSSO() {}

   public DimensionEditorSessionSSO(DimensionImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(DimensionImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public DimensionImpl getEditorData() {
      return this.mEditorData;
   }

   public List getEvents() {
      return this.mEvents;
   }

   public void setEvents(List events) {
      this.mEvents = events;
   }

   public ModelRefImpl getModelRef() {
      return this.mModelRef;
   }

   public void setModelRef(ModelRefImpl modelRef) {
      this.mModelRef = modelRef;
   }
}
