// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyEvent;
import com.cedar.cp.api.dimension.HierarchyEventListener;
import com.cedar.cp.dto.dimension.event.InsertHierarchyElementFeedEvent;
import com.cedar.cp.impl.dimension.HierarchyEditorImpl;

class HierarchyEditorImpl$8 implements HierarchyEventListener {

   // $FF: synthetic field
   final HierarchyEditorImpl this$0;


   HierarchyEditorImpl$8(HierarchyEditorImpl var1) {
      this.this$0 = var1;
   }

   public void dispatchEvent(HierarchyEvent e) throws CPException, ValidationException {
      HierarchyEditorImpl.accessMethod700(this.this$0, (InsertHierarchyElementFeedEvent)e);
   }
}
