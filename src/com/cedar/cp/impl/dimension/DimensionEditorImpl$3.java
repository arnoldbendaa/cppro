// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionEvent;
import com.cedar.cp.api.dimension.DimensionEventListener;
import com.cedar.cp.dto.dimension.event.UpdateDimensionElementEvent;
import com.cedar.cp.impl.dimension.DimensionEditorImpl;

class DimensionEditorImpl$3 implements DimensionEventListener {

   // $FF: synthetic field
   final DimensionEditorImpl this$0;


   DimensionEditorImpl$3(DimensionEditorImpl var1) {
      this.this$0 = var1;
   }

   public void dispatchEvent(DimensionEvent e) throws CPException, ValidationException {
      DimensionEditorImpl.accessMethod200(this.this$0, (UpdateDimensionElementEvent)e);
   }
}
