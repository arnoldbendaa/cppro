// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.Hierarchy;
import com.cedar.cp.api.dimension.HierarchyElement;
import com.cedar.cp.api.dimension.HierarchyElementEditor;
import com.cedar.cp.api.model.ModelRef;
import javax.swing.ListModel;

public interface HierarchyEditor extends BusinessEditor {

   void setDimensionId(int var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setDimensionRef(DimensionRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   Hierarchy getHierarchy();

   ModelRef getModel();

   void insertElement(Object var1, int var2) throws ValidationException, CPException;

   void removeElement(Object var1) throws ValidationException, CPException;

   void moveElement(Object var1, Object var2, int var3) throws ValidationException, CPException;

   HierarchyElementEditor getElementEditor(HierarchyElement var1) throws ValidationException, CPException;

   ListModel getAvailableDimensionElementRefs();

   void insertDimensionElement(Object var1, int var2, DimensionElementRef var3) throws ValidationException, CPException;

   void setSubmitChangeManagementRequest(boolean var1);

   boolean isAugmentMode();
}
