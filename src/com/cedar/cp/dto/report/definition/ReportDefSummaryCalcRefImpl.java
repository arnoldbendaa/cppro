// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.api.report.definition.ReportDefSummaryCalcRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcCK;
import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcPK;
import java.io.Serializable;

public class ReportDefSummaryCalcRefImpl extends EntityRefImpl implements ReportDefSummaryCalcRef, Serializable {

   public ReportDefSummaryCalcRefImpl(ReportDefSummaryCalcCK key, String narrative) {
      super(key, narrative);
   }

   public ReportDefSummaryCalcRefImpl(ReportDefSummaryCalcPK key, String narrative) {
      super(key, narrative);
   }

   public ReportDefSummaryCalcPK getReportDefSummaryCalcPK() {
      return this.mKey instanceof ReportDefSummaryCalcCK?((ReportDefSummaryCalcCK)this.mKey).getReportDefSummaryCalcPK():(ReportDefSummaryCalcPK)this.mKey;
   }
}
