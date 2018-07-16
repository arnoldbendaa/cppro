package com.cedar.cp.ejb.api.recalculate;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskEditorSessionCSO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskEditorSessionSSO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface RecalculateBatchTaskEditorSessionRemote extends EJBObject {

	RecalculateBatchTaskEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

	RecalculateBatchTaskEditorSessionSSO getNewItemData(int var1) throws RemoteException;

	RecalculateBatchTaskPK insert(RecalculateBatchTaskEditorSessionCSO var1) throws ValidationException, RemoteException;

	RecalculateBatchTaskPK copy(RecalculateBatchTaskEditorSessionCSO var1) throws ValidationException, RemoteException;

	void update(RecalculateBatchTaskEditorSessionCSO var1) throws ValidationException, RemoteException;

	void delete(int var1, Object var2) throws ValidationException, RemoteException;

	int issueRecalculateBatchTask(EntityRef var1, int var2, int var3) throws ValidationException, RemoteException;

	int issueTestTask(Integer var1, int var2) throws ValidationException, RemoteException;

	int issueTestRollbackTask(int var1) throws ValidationException, RemoteException;

}
