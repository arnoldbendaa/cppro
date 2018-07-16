// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCycleEditorSessionCSO;
import com.cedar.cp.dto.model.BudgetCycleEditorSessionSSO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface BudgetCycleEditorSessionRemote extends EJBObject {

   BudgetCycleEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   BudgetCycleEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   BudgetCycleCK insert(BudgetCycleEditorSessionCSO var1) throws ValidationException, RemoteException;

   EntityList getOwnershipData(int var1, Object var2) throws RemoteException;

   BudgetCycleCK copy(BudgetCycleEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(BudgetCycleEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   void clearCache(int userId, Object key) throws ValidationException, RemoteException;
}
