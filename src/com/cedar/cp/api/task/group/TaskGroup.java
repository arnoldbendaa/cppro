// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.task.group;

import com.cedar.cp.api.task.group.TaskGroupRow;
import java.sql.Timestamp;
import java.util.List;

public interface TaskGroup {

   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   Timestamp getLastSubmit();

   List<TaskGroupRow> getTGRows();
}
