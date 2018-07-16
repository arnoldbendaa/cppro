// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.admin.tidytask;

import com.cedar.cp.api.admin.tidytask.TidyTaskEditorSession;
import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;

public interface TidyTasksProcess extends BusinessProcess {

   EntityList getAllTidyTasks();

   TidyTaskEditorSession getTidyTaskEditorSession(Object var1) throws ValidationException;

   int issueTidyTask(EntityRef var1, int var2) throws ValidationException;

   int issueTestTask(Integer var1, int var2) throws ValidationException;

   int issueTestRollbackTask() throws ValidationException;
}
