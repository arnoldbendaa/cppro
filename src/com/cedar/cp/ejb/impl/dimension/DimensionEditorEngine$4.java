// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionEvent;
import com.cedar.cp.dto.dimension.event.ContextEvent;
import com.cedar.cp.ejb.impl.dimension.DimensionEditorEngine;
import com.cedar.cp.ejb.impl.dimension.ServerDimensionEventListener;

class DimensionEditorEngine$4 implements ServerDimensionEventListener {

   // $FF: synthetic field
   final DimensionEditorEngine this$0;


   DimensionEditorEngine$4(DimensionEditorEngine var1) {
      this.this$0 = var1;
   }

   public void dispatchEvent(DimensionEvent e) throws CPException, ValidationException {
      DimensionEditorEngine.accessMethod300(this.this$0, (ContextEvent)e);
   }
}
