// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.budgetinstruction;

import com.cedar.cp.api.budgetinstruction.BudgetInstructionAssignment;
import java.util.List;

public interface BudgetInstruction {

   Object getPrimaryKey();

   String getVisId();

   String getDocumentRef();

   byte[] getDocument();

   List getBudgetInstructionAssignments();

   BudgetInstructionAssignment getBudgetInstructionAssignmentItem(Object var1);
}
