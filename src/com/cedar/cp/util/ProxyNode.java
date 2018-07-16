// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.OnDemandMutableTreeNode;

public interface ProxyNode {

   void setNode(OnDemandMutableTreeNode var1);

   boolean hasChildren();

   void populateChildren();
}
