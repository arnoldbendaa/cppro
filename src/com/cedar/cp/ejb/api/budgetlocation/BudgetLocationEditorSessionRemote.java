package com.cedar.cp.ejb.api.budgetlocation;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.budgetlocation.BudgetLocationEditorSessionCSO;
import com.cedar.cp.dto.budgetlocation.BudgetLocationEditorSessionSSO;
import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.EJBObject;

public interface BudgetLocationEditorSessionRemote extends EJBObject {

	BudgetLocationEditorSessionSSO getItemData(Object var1) throws ValidationException, RemoteException, EJBException;

	void delete(Object var1) throws ValidationException, CPException, RemoteException;

	void update(BudgetLocationEditorSessionCSO var1) throws ValidationException, EJBException, RemoteException;

	void copyAssignments(int paramInt, Object paramObject, List<Object> paramList) throws ValidationException, RemoteException;
}
