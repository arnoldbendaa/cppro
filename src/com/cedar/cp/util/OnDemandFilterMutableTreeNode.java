// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.OnDemandMutableTreeNode;

public class OnDemandFilterMutableTreeNode extends OnDemandMutableTreeNode {

   private Object mFilterParams;


   public OnDemandFilterMutableTreeNode(Object pRoot, String pClassName, Object filterParams) {
      super(pRoot, pClassName);
      this.mFilterParams = filterParams;
   }

   public Object getFilterParams() {
      return this.mFilterParams;
   }
}
