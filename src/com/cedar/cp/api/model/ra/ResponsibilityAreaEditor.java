// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.ra;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.ra.ResponsibilityArea;

public interface ResponsibilityAreaEditor extends BusinessEditor {

   void setModelId(int var1) throws ValidationException;

   void setStructureId(int var1) throws ValidationException;

   void setStructureElementId(int var1) throws ValidationException;

   void setVirementAuthStatus(int var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   void setOwningStructureElementRef(StructureElementRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   ResponsibilityArea getResponsibilityArea();
}
