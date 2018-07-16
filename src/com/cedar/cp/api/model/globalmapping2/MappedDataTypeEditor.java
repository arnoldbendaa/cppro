package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarYear;
import com.cedar.cp.api.model.globalmapping2.MappedDataType;
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
