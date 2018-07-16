// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.WebworkDispatcher;
import webwork.action.Action;
import webwork.action.factory.ActionFactory;

class WebworkDispatcher$1 extends ActionFactory {

   // $FF: synthetic field
   final Action val$action;
   // $FF: synthetic field
   final WebworkDispatcher this$0;


   WebworkDispatcher$1(WebworkDispatcher var1, Action var2) {
      this.this$0 = var1;
      this.val$action = var2;
   }

   public Action getActionImpl(String aName) throws Exception {
      return this.val$action;
   }
}
