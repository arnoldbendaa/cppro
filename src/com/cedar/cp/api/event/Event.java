// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.event;

import java.io.Serializable;

public interface Event extends Serializable {

   int TYPE_EXCEPTION = 0;
   int TYPE_SA_MANAGE = 1;
   int TYPE_SA_TASK = 2;
   int TYPE_LOGON = 3;
   int TYPE_LOGOFF = 4;
   int SA_START = 0;
   int SA_QUIESCE = 1;
   int SA_RESTART = 2;
   int SA_ADD_ACTIVATOR_TEMP = 3;
   int SA_REMOVE_ACTIVATOR_TEMP = 4;
   int SA_ADD_ACTIVATOR_PERM = 5;
   int SA_REMOVE_ACTIVATOR_PERM = 6;
   int SA_CHANGE_POLL = 7;
   int SA_END = 9;
   int SA_TASK_START = 0;
   int SA_TASK_END = 1;
   int SA_TASK_CHECKPOINT = 2;
   int SA_TASK_RESTART = 3;
   int SA_TASK_PROGRESS = 4;

}
