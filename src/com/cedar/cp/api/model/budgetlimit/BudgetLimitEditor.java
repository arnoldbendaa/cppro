// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.budgetlimit;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.budgetlimit.BudgetLimit;

public interface BudgetLimitEditor extends BusinessEditor {

   void setBudgetLocationElementId(int var1) throws ValidationException;

   void setDim0(int var1) throws ValidationException;

   void setDim1(int var1) throws ValidationException;

   void setDim2(int var1) throws ValidationException;

   void setDim3(int var1) throws ValidationException;

   void setDim4(int var1) throws ValidationException;

   void setDim5(int var1) throws ValidationException;

   void setDim6(int var1) throws ValidationException;

   void setDim7(int var1) throws ValidationException;

   void setDim8(int var1) throws ValidationException;

   void setDim9(int var1) throws ValidationException;

   void setDataType(String var1) throws ValidationException;

   void setMinValue(Long var1) throws ValidationException;

   void setMaxValue(Long var1) throws ValidationException;

   void setFinanceCubeRef(FinanceCubeRef var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   BudgetLimit getBudgetLimit();

   void setFinanceCubeRef(int var1) throws ValidationException;
}
