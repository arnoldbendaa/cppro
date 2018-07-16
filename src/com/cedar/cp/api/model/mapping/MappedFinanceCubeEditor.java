// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.model.mapping.MappedCalendarYear;
import com.cedar.cp.api.model.mapping.MappedDataTypeEditor;
import com.cedar.cp.api.model.mapping.MappedFinanceCube;
import java.util.List;

public interface MappedFinanceCubeEditor extends SubBusinessEditor {

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void deleteMappedDataType(Object var1) throws ValidationException;

   MappedDataTypeEditor getMappedDataTypeEditor(Object var1) throws ValidationException;

   MappedFinanceCube getMappedFinanceCube();

   List<DataType> getAvailableDataTypes(Object var1);

   List<MappedCalendarYear> getMappedCalendarYears();
}
