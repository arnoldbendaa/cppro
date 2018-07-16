// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.task;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.task.ReportGrouping;

public interface ReportGroupingEditor extends BusinessEditor {

   void setParentTaskId(int var1) throws ValidationException;

   void setTaskId(int var1) throws ValidationException;

   void setDistributionType(int var1) throws ValidationException;

   void setMessageType(int var1) throws ValidationException;

   void setMessageId(String var1) throws ValidationException;

   ReportGrouping getReportGrouping();
}
