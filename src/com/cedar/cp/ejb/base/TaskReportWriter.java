// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base;

import com.cedar.cp.ejb.base.async.TaskReport;

public interface TaskReportWriter {

   TaskReport getTaskReport();

   int getReportType();
}
