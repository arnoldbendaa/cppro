// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.dto.dimension.DimensionImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DimensionEditorSessionCSO implements Serializable {

   private List mEvents = new ArrayList();
   private int mUserId;
   private DimensionImpl mEditorData;


   public DimensionEditorSessionCSO(int userId, DimensionImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public DimensionImpl getEditorData() {
      return this.mEditorData;
   }

   public List getEvents() {
      return this.mEvents;
   }

   public void addEvent(Object event) {
      this.mEvents.add(event);
   }

   public int getUserId() {
      return this.mUserId;
   }
}
