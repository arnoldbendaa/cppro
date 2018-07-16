// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.api.dimension.DimensionEvent;

public class ContextEvent implements DimensionEvent {

   private ConnectionContext mContext;


   public ContextEvent(ConnectionContext context) {
      this.mContext = context;
   }

   public ConnectionContext getContext() {
      return this.mContext;
   }
}
