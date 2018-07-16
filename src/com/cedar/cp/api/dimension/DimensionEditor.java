// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.Dimension;
import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.DimensionElementEditor;
import com.cedar.cp.api.model.ModelRef;

public interface DimensionEditor extends BusinessEditor {

   void setType(int var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setExternalSystemRef(Integer var1) throws ValidationException;

   Dimension getDimension();

   DimensionElement insertElement(String var1, String var2, int var3, boolean var4, boolean var5, int var6, boolean var7) throws DuplicateNameValidationException, ValidationException, CPException;

   DimensionElement insertElement(String var1, String var2, int var3, boolean var4, boolean var5, int var6) throws DuplicateNameValidationException, ValidationException, CPException;

   void removeElement(Object var1, String var2) throws ValidationException, CPException;

   DimensionElementEditor getElementEditor(Object var1) throws ValidationException, CPException;

   ModelRef queryOwningModel();

   void setSubmitChangeManagementRequest(boolean var1);

   boolean isAugmentMode();
}
