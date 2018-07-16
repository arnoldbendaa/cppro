package com.cedar.cp.ejb.api.importtask;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.importtask.ImportTaskEditorSessionCSO;
import com.cedar.cp.dto.importtask.ImportTaskEditorSessionSSO;
import com.cedar.cp.dto.importtask.ImportTaskPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ImportTaskEditorSessionRemote extends EJBObject {

	ImportTaskEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

	ImportTaskEditorSessionSSO getNewItemData(int var1) throws RemoteException;

	ImportTaskPK insert(ImportTaskEditorSessionCSO var1) throws ValidationException, RemoteException;

	ImportTaskPK copy(ImportTaskEditorSessionCSO var1) throws ValidationException, RemoteException;

	void update(ImportTaskEditorSessionCSO var1) throws ValidationException, RemoteException;

	void delete(int var1, Object var2) throws ValidationException, RemoteException;

	int issueImportTask(EntityRef var1, int var2, int var3, String externalSystemVisId) throws ValidationException, RemoteException;

	int issueTestTask(Integer var1, int var2) throws ValidationException, RemoteException;

	int issueTestRollbackTask(int var1) throws ValidationException, RemoteException;

}
