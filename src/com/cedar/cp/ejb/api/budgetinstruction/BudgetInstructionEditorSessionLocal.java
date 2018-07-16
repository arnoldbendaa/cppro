// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.budgetinstruction;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.Hierarchy;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionEditorSessionCSO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionEditorSessionSSO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface BudgetInstructionEditorSessionLocal extends EJBLocalObject {

   BudgetInstructionEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   BudgetInstructionEditorSessionSSO getNewItemData(int var1) throws EJBException;

   BudgetInstructionPK insert(BudgetInstructionEditorSessionCSO var1) throws ValidationException, EJBException;

   BudgetInstructionPK copy(BudgetInstructionEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(BudgetInstructionEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   Hierarchy getBudgetLocationHierarchy(Object var1);
}
