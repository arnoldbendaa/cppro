package com.cedar.cp.api.importtask;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;

public interface ImportTasksProcess extends BusinessProcess {

	EntityList getAllImportTasks();

	ImportTaskEditorSession getImportTaskEditorSession(Object var1) throws ValidationException;

	int issueImportTask(EntityRef var1, int var2, String externalSystemVisId) throws ValidationException;

	int issueTestTask(Integer var1, int var2) throws ValidationException;

	int issueTestRollbackTask() throws ValidationException;

}
