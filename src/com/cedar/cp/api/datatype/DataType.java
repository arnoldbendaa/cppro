// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.datatype;

import com.cedar.cp.api.datatype.DataTypeRef;
import java.util.List;

public interface DataType {

   int FINANCIAL_VALUE = 0;
   int TEMPORARY_VIREMENT = 1;
   int PERMANENT_VIREMENT = 2;
   int VIRTUAL = 3;
   int MEASURE = 4;
   int STRING_CLASS = 0;
   int NUMERIC_CLASS = 1;
   int TIME_CLASS = 2;
   int DATE_CLASS = 3;
   int DATE_TIME_CLASS = 4;
   int BOOLEAN_CLASS = 5;


   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   boolean isReadOnlyFlag();

   boolean isAvailableForImport();

   boolean isAvailableForExport();

   int getSubType();

   String getFormulaExpr();

   Integer getMeasureClass();

   Integer getMeasureLength();

   Integer getMeasureScale();

   String getMeasureValidation();

   List getDataTypeRefs();

   DataTypeRef getDataTypeRef();

   boolean propagatesInDimension(int var1) throws IllegalStateException;

   boolean isFinanceValue();

   boolean isMeasure();

   boolean isMeasureNumeric();

   boolean isMeasureString();

   boolean isMeasureBoolean();

   boolean isMeasureDate();

   boolean isMeasureDateTime();

   boolean isMeasureTime();
}
