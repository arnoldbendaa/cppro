// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.mapping.MappedCalendarYear;
import com.cedar.cp.api.model.mapping.MappedDataType;
import java.util.List;

public interface MappedDataTypeEditor extends SubBusinessEditor {

   MappedDataType getMappedDataType();

   List<DataType> getAvailableDataTypes();

   void setImpExpState(int var1) throws ValidationException;

   void setDataTypeRef(DataTypeRef var1);

   void setExtSysValueType(String var1) throws ValidationException;

   void setExtSysCurrency(String var1);

   void setExtSysBalType(String var1);

   List<MappedCalendarYear> getMappedCalendarYears();

   void setImpYearRange(MappedCalendarYear var1, MappedCalendarYear var2) throws ValidationException;

   void setExpYearRange(MappedCalendarYear var1, MappedCalendarYear var2) throws ValidationException;
}
