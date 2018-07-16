// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.SecurityRange;
import com.cedar.cp.api.dimension.SecurityRangeRow;
import com.cedar.cp.api.dimension.SecurityRangeRowEditor;
import com.cedar.cp.api.model.ModelRef;

public interface SecurityRangeEditor extends BusinessEditor {

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setDimensionRef(DimensionRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   SecurityRange getSecurityRange();

   void setModelRef(ModelRef var1) throws ValidationException;

   SecurityRangeRowEditor getEditor(SecurityRangeRow var1) throws ValidationException;

   void removeRow(SecurityRangeRow var1) throws ValidationException;

   EntityList getModelAndDimensionOwners() throws CPException;
}
