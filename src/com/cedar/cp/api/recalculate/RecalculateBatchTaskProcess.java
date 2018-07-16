package com.cedar.cp.api.recalculate;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;

public interface RecalculateBatchTaskProcess extends BusinessProcess {

	EntityList getAllRecalculateBatchTasks();

	RecalculateBatchTaskEditorSession getRecalculateBatchTaskEditorSession(Object var1) throws ValidationException;

	int issueRecalculateBatchTask(EntityRef var1, int var2) throws ValidationException;

	int issueTestTask(Integer var1, int var2) throws ValidationException;

	int issueTestRollbackTask() throws ValidationException;

}
