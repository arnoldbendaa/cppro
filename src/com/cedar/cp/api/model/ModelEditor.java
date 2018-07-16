// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.currency.CurrencyRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.Model;

public interface ModelEditor extends BusinessEditor {

   void setCurrencyInUse(boolean var1) throws ValidationException;

   void setLocked(boolean var1) throws ValidationException;

   void setVirementEntryEnabled(boolean var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setCurrencyRef(CurrencyRef var1) throws ValidationException;

   void setAccountRef(DimensionRef var1) throws ValidationException;

   void setCalendarRef(DimensionRef var1) throws ValidationException;

   void setBudgetHierarchyRef(HierarchyRef var1) throws ValidationException;

   Model getModel();

   void addSelectedDimensionRef(EntityRef var1);

   boolean isDimensionRefRemovable(EntityRef var1);

   void removeSelectedDimensionRef(EntityRef var1);

   boolean isAccountRefEditable();

   boolean isCalendarRefEditable();

   boolean isBudgetHierarchyRefEditable();

   void setProperty(String var1, String var2);
}
