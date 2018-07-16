// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.task;

import com.cedar.cp.api.base.EntityRef;

public interface TaskRef extends EntityRef {

   int TASK_TYPE_SIMPLE = 0;
   int TASK_TYPE_GROUP = 1;
   int CREATED = 0;
   int RECEIVED = 1;
   int DESPATCHED = 2;
   int STARTED = 3;
   int FAILED = 4;
   int COMPLETE = 5;
   int WAITING_FOR_TASK = 6;
   int COMMIT_STATE_STARTED = 7;
   int COMMIT_STATE_COMPLETE = 8;
   int COMPLETE_WITH_EXCEPTION = 9;
   int UNSAFE_DELETED = 10;

}
