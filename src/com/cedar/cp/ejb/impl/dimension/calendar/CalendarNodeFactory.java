// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension.calendar;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;

public interface CalendarNodeFactory {

   HierarchyNodeDAG newNode(String var1, String var2, int var3) throws ValidationException;

   HierarchyNodeDAG newNode(String var1, String var2, boolean var3, int var4) throws ValidationException;

   void registerRemovedElement(HierarchyNodeDAG var1);

   void registerNewYearNode(HierarchyNodeDAG var1, HierarchyNodeDAG var2, int var3) throws ValidationException;
}
