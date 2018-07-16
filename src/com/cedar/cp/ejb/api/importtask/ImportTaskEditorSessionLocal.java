package com.cedar.cp.ejb.api.importtask;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.importtask.ImportTaskEditorSessionCSO;
import com.cedar.cp.dto.importtask.ImportTaskEditorSessionSSO;
import com.cedar.cp.dto.importtask.ImportTaskPK;

import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface ImportTaskEditorSessionLocal extends EJBLocalObject {

	ImportTaskEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

	ImportTaskEditorSessionSSO getNewItemData(int var1) throws EJBException;

	ImportTaskPK insert(ImportTaskEditorSessionCSO var1) throws ValidationException, EJBException;

	ImportTaskPK copy(ImportTaskEditorSessionCSO var1) throws ValidationException, EJBException;

	void update(ImportTaskEditorSessionCSO var1) throws ValidationException, EJBException;

	void delete(int var1, Object var2) throws ValidationException, EJBException;

	int issueImportTask(EntityRef var1, int var2, int var3, String externalSystemVisId) throws ValidationException, EJBException;

	int issueTestTask(Integer var1, int var2) throws ValidationException, EJBException;

	int issueTestRollbackTask(int var1) throws ValidationException, EJBException;
}
