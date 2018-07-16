// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.BudgetCycle;
import com.cedar.cp.api.model.ModelRef;
import java.sql.Timestamp;

public interface BudgetCycleEditor extends BusinessEditor {

   void setModelId(int var1) throws ValidationException;

   void setType(int var1) throws ValidationException;

   void setXmlFormId(int var1) throws ValidationException;

   void setPeriodId(int var1) throws ValidationException;

   void setStatus(int var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setXmlFormDataType(String var1) throws ValidationException;

   void setPlannedEndDate(Timestamp var1) throws ValidationException;

   void setStartDate(Timestamp var1) throws ValidationException;

   void setEndDate(Timestamp var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   BudgetCycle getBudgetCycle();

   void setXmlFormId(EntityRef var1) throws ValidationException;

   void setPeriodRef(EntityRef var1) throws ValidationException;
   
   void setPeriodToId(EntityRef ref) throws ValidationException;
   
   void setPeriodFromVisId(String newPeriodFromVisId) throws ValidationException;

   void setPeriodToVisId(String newPeriodToVisId) throws ValidationException;

   void setDateChanged(boolean var1);
   
   void addXmlForm(Object[] element);

   void removeXmlForm(Object[] element);
   
   void removeXmlForm(int index);
   
   void setXmlForm(int index, Object[] element);
   
   String[] getCalendarDetails();
}
