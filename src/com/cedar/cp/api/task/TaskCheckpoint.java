// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.task;

import java.io.Serializable;
import java.util.List;

public interface TaskCheckpoint extends Serializable {

   List toDisplay();

   int setCheckpointNumberUp();

   int getCheckpointNumber();

   int getReportId();

   void setReportId(int var1);

   int getNextReportLineNumber();
}
