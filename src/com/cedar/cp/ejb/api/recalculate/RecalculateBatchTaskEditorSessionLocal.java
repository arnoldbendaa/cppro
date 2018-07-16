package com.cedar.cp.ejb.api.recalculate;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskEditorSessionCSO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskEditorSessionSSO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;

import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface RecalculateBatchTaskEditorSessionLocal extends EJBLocalObject {

	RecalculateBatchTaskEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

	RecalculateBatchTaskEditorSessionSSO getNewItemData(int var1) throws EJBException;

	RecalculateBatchTaskPK insert(RecalculateBatchTaskEditorSessionCSO var1) throws ValidationException, EJBException;

	RecalculateBatchTaskPK copy(RecalculateBatchTaskEditorSessionCSO var1) throws ValidationException, EJBException;

	void update(RecalculateBatchTaskEditorSessionCSO var1) throws ValidationException, EJBException;

	void delete(int var1, Object var2) throws ValidationException, EJBException;

	int issueRecalculateBatchTask(EntityRef var1, int var2, int var3) throws ValidationException, EJBException;

	int issueTestTask(Integer var1, int var2) throws ValidationException, EJBException;

	int issueTestRollbackTask(int var1) throws ValidationException, EJBException;
}
