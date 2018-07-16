package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarElement;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarYear;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.globalmapping2.extsys.FinancePeriod;
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
