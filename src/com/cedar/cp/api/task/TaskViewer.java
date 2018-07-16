// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.task;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import java.sql.Timestamp;
import java.util.List;

public interface TaskViewer extends BusinessEditor {

   int getTaskId();

   String getTaskName();

   int getOriginalTaskId();

   String getUser();

   List getEvents();

   int getStatus();

   String getServiceStep();

   Exception getException();

   Timestamp getCreateDate();

   void refresh();

   EntityList getTaskEvents();

   List getExclusiveAccessList();
}
