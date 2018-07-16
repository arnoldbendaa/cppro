// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.task;

import com.cedar.cp.api.report.task.ReportGroupingFileRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.task.ReportGroupingFileCK;
import com.cedar.cp.dto.report.task.ReportGroupingFilePK;
import java.io.Serializable;

public class ReportGroupingFileRefImpl extends EntityRefImpl implements ReportGroupingFileRef, Serializable {

   public ReportGroupingFileRefImpl(ReportGroupingFileCK key, String narrative) {
      super(key, narrative);
   }

   public ReportGroupingFileRefImpl(ReportGroupingFilePK key, String narrative) {
      super(key, narrative);
   }

   public ReportGroupingFilePK getReportGroupingFilePK() {
      return this.mKey instanceof ReportGroupingFileCK?((ReportGroupingFileCK)this.mKey).getReportGroupingFilePK():(ReportGroupingFilePK)this.mKey;
   }
}
