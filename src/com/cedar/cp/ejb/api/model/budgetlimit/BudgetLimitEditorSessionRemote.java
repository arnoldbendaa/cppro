// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.budgetlimit;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitCK;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitEditorSessionCSO;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitEditorSessionSSO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface BudgetLimitEditorSessionRemote extends EJBObject {

   BudgetLimitEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   BudgetLimitEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   BudgetLimitCK insert(BudgetLimitEditorSessionCSO var1) throws ValidationException, RemoteException;

   EntityList getOwnershipData(int var1, Object var2) throws RemoteException;

   BudgetLimitCK copy(BudgetLimitEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(BudgetLimitEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   List getImposedLimits(int var1, int var2, int var3) throws ValidationException, RemoteException;

   List getBudgetLimitsSetByBudgetLocation(int var1, int var2, int var3) throws ValidationException, RemoteException;
}
