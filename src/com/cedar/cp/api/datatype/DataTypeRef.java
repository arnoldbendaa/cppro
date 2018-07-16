// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.datatype;

import com.cedar.cp.api.base.EntityRef;

public interface DataTypeRef extends EntityRef {

   int FINANCIAL_VALUE = 0;
   int TEMPORARY_VIREMENT = 1;
   int PERMANENT_VIREMENT = 2;
   int VIRTUAL = 3;
   int MEASURE = 4;
   Integer STRING_CLASS = Integer.valueOf(0);
   Integer NUMERIC_CLASS = Integer.valueOf(1);
   Integer TIME_CLASS = Integer.valueOf(2);
   Integer DATE_CLASS = Integer.valueOf(3);
   Integer DATE_TIME_CLASS = Integer.valueOf(4);
   Integer BOOLEAN_CLASS = Integer.valueOf(5);


   String getDescription();

   int getSubType();

   Integer getMeasureClass();

   Integer getMeasureLength();

   boolean allowsConfigrableRollUp();

}
