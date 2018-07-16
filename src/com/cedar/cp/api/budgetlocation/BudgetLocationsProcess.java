package com.cedar.cp.api.budgetlocation;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;

public interface BudgetLocationsProcess extends BusinessProcess {

   BudgetLocationEditorSession getBudgetLocationEditorSession(Object var1) throws ValidationException;

   EntityList getModelUserSecurity();
}
