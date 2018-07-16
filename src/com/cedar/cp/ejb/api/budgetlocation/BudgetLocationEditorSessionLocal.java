package com.cedar.cp.ejb.api.budgetlocation;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.budgetlocation.BudgetLocationEditorSessionCSO;
import com.cedar.cp.dto.budgetlocation.BudgetLocationEditorSessionSSO;

public interface BudgetLocationEditorSessionLocal extends EJBLocalObject {

	void delete(Object var1) throws ValidationException, EJBException;

	void update(BudgetLocationEditorSessionCSO var1) throws ValidationException, EJBException;

	BudgetLocationEditorSessionSSO getItemData(Object var1) throws ValidationException;

	void copyAssignments(int paramInt, Object paramObject, List<Object> paramList) throws ValidationException, EJBException;
}
