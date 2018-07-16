// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.api.report.definition.ReportDefCalculatorRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.definition.ReportDefCalculatorCK;
import com.cedar.cp.dto.report.definition.ReportDefCalculatorPK;
import java.io.Serializable;

public class ReportDefCalculatorRefImpl extends EntityRefImpl implements ReportDefCalculatorRef, Serializable {

   public ReportDefCalculatorRefImpl(ReportDefCalculatorCK key, String narrative) {
      super(key, narrative);
   }

   public ReportDefCalculatorRefImpl(ReportDefCalculatorPK key, String narrative) {
      super(key, narrative);
   }

   public ReportDefCalculatorPK getReportDefCalculatorPK() {
      return this.mKey instanceof ReportDefCalculatorCK?((ReportDefCalculatorCK)this.mKey).getReportDefCalculatorPK():(ReportDefCalculatorPK)this.mKey;
   }
}
