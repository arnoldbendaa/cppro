// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.udeflookup;


public interface UdefColumn {

   int TYPE_STRING = 0;
   int TYPE_NUMBER = 1;
   int TYPE_BOOLEAN = 2;
   int TYPE_DATE = 3;
   int TYPE_TA_YEAR = 4;
   int TYPE_TA_YEAR_MONTH = 5;
   String NAME_SCENARIO = "SCENARIO";
   String NAME_TA_DATE = "TA_DATE";
   String NAME_TA_END_DATE = "TA_END_DATE";


   Object getKey();

   String getName();

   void setName(String var1);

   String getTitle();

   void setTitle(String var1);

   int getType();

   void setType(int var1);

   Integer getSize();

   void setSize(Integer var1);

   Integer getDP();

   void setDP(Integer var1);

   boolean isOptional();

   void setOptional(boolean var1);

   boolean isTimeAwareColumn();

   void setState(int var1);

   String getColumnName();

   boolean isRangeStart();

   boolean isRangeEnd();

   int getState();
}
