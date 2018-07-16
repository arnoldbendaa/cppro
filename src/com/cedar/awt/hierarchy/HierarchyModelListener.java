// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt.hierarchy;

import com.cedar.awt.hierarchy.HierarchySearchEvent;
import java.util.EventListener;

public interface HierarchyModelListener extends EventListener {

   void hierarchySearchResult(HierarchySearchEvent var1);
}
