// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.task;


public interface ReportGrouping {

   Object getPrimaryKey();

   int getParentTaskId();

   int getTaskId();

   int getDistributionType();

   int getMessageType();

   String getMessageId();
}
