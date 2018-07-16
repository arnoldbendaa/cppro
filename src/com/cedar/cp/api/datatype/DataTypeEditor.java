// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.datatype;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataType;

public interface DataTypeEditor extends BusinessEditor {

   void setReadOnlyFlag(boolean var1) throws ValidationException;

   void setAvailableForImport(boolean var1) throws ValidationException;

   void setAvailableForExport(boolean var1) throws ValidationException;

   void setSubType(int var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setFormulaExpr(String var1) throws ValidationException;

   void setMeasureClass(Integer var1) throws ValidationException;

   void setMeasureLength(Integer var1) throws ValidationException;

   void setMeasureScale(Integer var1) throws ValidationException;

   void setMeasureValidation(String var1) throws ValidationException;

   DataType getDataType();
}
