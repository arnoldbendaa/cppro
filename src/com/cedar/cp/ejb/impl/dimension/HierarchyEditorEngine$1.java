// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:24
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyEvent;
import com.cedar.cp.dto.dimension.event.InsertHierarchyElementEvent;
import com.cedar.cp.ejb.impl.dimension.HierarchyEditorEngine;
import com.cedar.cp.ejb.impl.dimension.ServerHierarchyEventListener;

class HierarchyEditorEngine$1 implements ServerHierarchyEventListener {

   // $FF: synthetic field
   final HierarchyEditorEngine this$0;


   HierarchyEditorEngine$1(HierarchyEditorEngine var1) {
      this.this$0 = var1;
   }

   public void dispatchEvent(HierarchyEvent e) throws CPException, ValidationException {
      HierarchyEditorEngine.accessMethod000(this.this$0, (InsertHierarchyElementEvent)e);
   }
}
