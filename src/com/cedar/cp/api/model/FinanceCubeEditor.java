// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCube;
import com.cedar.cp.api.model.ModelRef;

public interface FinanceCubeEditor extends BusinessEditor {

   void setHasData(boolean var1) throws ValidationException;

   void setAudited(boolean var1) throws ValidationException;

   void setCubeFormulaEnabled(boolean var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setLockedByTaskId(Integer var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   FinanceCube getFinanceCube();

   void addSelectedDataTypeRef(DataTypeRef var1) throws ValidationException;

   void removeSelectedDataTypeRef(DataTypeRef var1) throws ValidationException;

   void addCellNote(int var1);

   void setRollUpRule(DataTypeRef var1, DimensionRef var2, boolean var3) throws ValidationException;

   void setSubmitChangeManagement(boolean var1);
}
