// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.task;

import java.io.Serializable;
import java.util.List;

public interface TaskRequest extends Serializable {

   List getExclusiveAccessList();

   String getIdentifier();

   List toDisplay();

   String getService();

   int getTaskId();
}
