// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.task;

import com.cedar.cp.api.report.task.ReportGroupingRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.task.ReportGroupingPK;
import java.io.Serializable;

public class ReportGroupingRefImpl extends EntityRefImpl implements ReportGroupingRef, Serializable {

   public ReportGroupingRefImpl(ReportGroupingPK key, String narrative) {
      super(key, narrative);
   }

   public ReportGroupingPK getReportGroupingPK() {
      return (ReportGroupingPK)this.mKey;
   }
}
