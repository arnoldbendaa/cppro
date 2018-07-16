// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.dimension.HierarchyEditor;

public interface HierarchyEditorSession extends BusinessSession {

   HierarchyEditor getHierarchyEditor();

   EntityList getAvailableDimensions();

   EntityList getOwnershipRefs();

   EntityList getAvailableDimensionsForInsert(int var1);
}
