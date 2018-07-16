// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.mapping.MappedCalendarElement;
import com.cedar.cp.api.model.mapping.MappedCalendarYear;
import com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.mapping.extsys.FinancePeriod;
import java.util.List;

public interface MappedCalendarYearEditor extends SubBusinessEditor {

   MappedCalendarYear getMappedCalendarYear();

   List<EntityRef> getCPLeafElements();

   FinanceCalendarYear getFinanceCalendarYear();

   MappedCalendarElement findElementByCPRef(EntityRef var1);

   MappedCalendarElement findElementByExtSysRef(FinancePeriod var1);

   void updateMappedCalendarElement(Object var1, FinancePeriod var2, EntityRef var3) throws ValidationException;

   void doAutoMap() throws ValidationException;
}
