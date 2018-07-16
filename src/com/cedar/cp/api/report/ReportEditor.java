// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.Report;

public interface ReportEditor extends BusinessEditor {

   void setUserId(int var1) throws ValidationException;

   void setReportType(int var1) throws ValidationException;

   void setTaskId(int var1) throws ValidationException;

   void setComplete(boolean var1) throws ValidationException;

   void setHasUpdates(boolean var1) throws ValidationException;

   void setUpdatesApplied(boolean var1) throws ValidationException;

   void setReportText(String var1) throws ValidationException;

   void setUpdateTaskId(Integer var1) throws ValidationException;

   void setBudgetCycleId(Integer var1) throws ValidationException;

   void setActivityType(Integer var1) throws ValidationException;

   void setActivityDetail(String var1) throws ValidationException;

   Report getReport();
}
