// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.budgetinstruction;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.Hierarchy;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionEditorSessionCSO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionEditorSessionSSO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface BudgetInstructionEditorSessionRemote extends EJBObject {

   BudgetInstructionEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   BudgetInstructionEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   BudgetInstructionPK insert(BudgetInstructionEditorSessionCSO var1) throws ValidationException, RemoteException;

   BudgetInstructionPK copy(BudgetInstructionEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(BudgetInstructionEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   EntityList getAllModels() throws RemoteException;

   EntityList getAllFinanceCubes() throws RemoteException;

   Hierarchy getBudgetLocationHierarchy(Object var1) throws RemoteException;
}
