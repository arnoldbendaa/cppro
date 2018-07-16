// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.budgetlimit;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitEditorSession;
import java.util.List;

public interface BudgetLimitsProcess extends BusinessProcess {

   EntityList getAllBudgetLimits();

   BudgetLimitEditorSession getBudgetLimitEditorSession(Object var1) throws ValidationException;

   List getImposedLimits(int var1, int var2, int var3) throws ValidationException;

   List getBudgetLimitsSetByBudgetLocation(int var1, int var2, int var3) throws ValidationException;

   void deleteObjectWithId(int var1, int var2, int var3) throws ValidationException;

   BudgetLimitEditorSession getBudgetLimitEditorSession(int var1, int var2, int var3) throws ValidationException;
}
