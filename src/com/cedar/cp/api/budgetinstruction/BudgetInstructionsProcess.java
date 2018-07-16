// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.budgetinstruction;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionEditorSession;

public interface BudgetInstructionsProcess extends BusinessProcess {

   EntityList getAllBudgetInstructions();

   EntityList getAllBudgetInstructionsWeb();

   EntityList getAllBudgetInstructionsForModel(int var1);

   EntityList getAllBudgetInstructionsForCycle(int var1);

   EntityList getAllBudgetInstructionsForLocation(int var1);

   BudgetInstructionEditorSession getBudgetInstructionEditorSession(Object var1) throws ValidationException;

   BudgetInstructionEditorSession getBudgetInstructionEditorSession(int var1) throws ValidationException;

   EntityList getAllBudgetInstructionsForModel(Object var1);

   void deleteObject(int var1) throws ValidationException;
}
