// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.mapping.MappedCalendarYear;
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
