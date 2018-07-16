package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarYear;
import com.cedar.cp.util.xmlform.XMLWritable;

public interface MappedDataType extends XMLWritable {

   int IMPORT_ONLY = 0;
   int EXPORT_ONLY = 1;
   int IMPORT_EXPORT = 2;


   Object getKey();

   DataTypeRef getDataTypeRef();

   int getImpExpStatus();

   String getExtSysValueType();

   String getExtSysCurrency();

   String getExtSysBalType();

   Integer getImpStartYearOffset();

   Integer getImpEndYearOffset();

   Integer getExpStartYearOffset();

   Integer getExpEndYearOffset();

   MappedCalendarYear getImpStartYear();

   MappedCalendarYear getImpEndYear();

   MappedCalendarYear getExpStartYear();

   MappedCalendarYear getExpEndYear();
}
