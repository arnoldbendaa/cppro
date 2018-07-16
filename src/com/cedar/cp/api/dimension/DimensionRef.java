// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.base.EntityRef;

public interface DimensionRef extends EntityRef {

   int ACCOUNT_TYPE = 1;
   int BUSINESS_TYPE = 2;
   int CALENDAR_TYPE = 3;


   int getType();
}
