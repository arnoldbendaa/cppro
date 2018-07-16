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
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface BudgetCycleEditorSessionLocal extends EJBLocalObject {

   BudgetCycleEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   BudgetCycleEditorSessionSSO getNewItemData(int var1) throws EJBException;

   BudgetCycleCK insert(BudgetCycleEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   BudgetCycleCK copy(BudgetCycleEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(BudgetCycleEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   void clearCache(int userId, Object key) throws ValidationException, EJBException;
}
