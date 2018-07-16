// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.task.group;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.group.TaskGroupEditorSession;

public interface TaskGroupsProcess extends BusinessProcess {

   EntityList getAllTaskGroups();

   EntityList getTaskGroupRICheck(int var1);

   TaskGroupEditorSession getTaskGroupEditorSession(Object var1) throws ValidationException;

   int submitGroup(EntityRef var1, int var2) throws ValidationException;
}
