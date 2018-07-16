// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlform;

import com.cedar.cp.util.ValueMapping;
import com.cedar.cp.util.xmlform.CalendarInfo;

public interface XmlFormWizardParameters {

   int getAccountDimensionIndex();

   ValueMapping getAccountHierarchies();

   int getSecondaryDimensionIndex();

   ValueMapping getSecondaryHierarchies();

   ValueMapping getDataTypes();

   ValueMapping getPeriodLeaves();

   CalendarInfo getCalendarInfo();
}
