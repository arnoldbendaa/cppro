// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.budgetinstruction;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetinstruction.BudgetInstruction;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionAssignment;
import java.util.List;

public interface BudgetInstructionEditor extends BusinessEditor {

   void setVisId(String var1) throws ValidationException;

   void setDocumentRef(String var1) throws ValidationException;

   void setDocument(byte[] var1) throws ValidationException;

   BudgetInstruction getBudgetInstruction();

   List getBudgetInstructionAssignments();

   BudgetInstructionAssignment getBudgetInstructionAssignmentItem(int var1);

   void setBudgetInstructionAssignmentItems(List var1);

   EntityList getAllModels();
   
   EntityList getAllModelsForLoggedUser();

   EntityList getAllBudgetCycles();

   EntityList getAllBudgetHierarchies();

   EntityList getImmediateChildren(Object var1);

   boolean isModelSelected(EntityRef var1);

   boolean isBudgetCycleSelected(EntityRef var1, EntityRef var2);

   boolean isResponsibilityAreaSelected(EntityRef var1, EntityRef var2);

   boolean isResponsibilityAreaAndChildrenSelected(EntityRef var1, EntityRef var2);

   void addModel(EntityRef var1);

   void addModel(int var1);

   void addBudgetCycle(EntityRef var1, EntityRef var2);

   void addBudgetCycle(int var1);

   void addResponsibilityArea(EntityRef var1, EntityRef var2, List var3);

   void addResponsibilityAreaPlusChildren(EntityRef var1, EntityRef var2, List var3);

   void removeModel(EntityRef var1);

   void removeBudgetCycle(EntityRef var1, EntityRef var2);

   void removeResponsibilityArea(EntityRef var1, EntityRef var2);

   boolean isChildSelected(EntityRef var1);

   boolean isChildSelected(EntityRef var1, EntityRef var2);

   boolean isBudgetCycleSelected(EntityRef var1);

   void removeBudgetInstructionAssignmentItem(Object var1);
}
