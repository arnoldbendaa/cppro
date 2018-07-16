// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.api.report.definition.ReportDefMappedExcelRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.definition.ReportDefMappedExcelCK;
import com.cedar.cp.dto.report.definition.ReportDefMappedExcelPK;
import java.io.Serializable;

public class ReportDefMappedExcelRefImpl extends EntityRefImpl implements ReportDefMappedExcelRef, Serializable {

   public ReportDefMappedExcelRefImpl(ReportDefMappedExcelCK key, String narrative) {
      super(key, narrative);
   }

   public ReportDefMappedExcelRefImpl(ReportDefMappedExcelPK key, String narrative) {
      super(key, narrative);
   }

   public ReportDefMappedExcelPK getReportDefMappedExcelPK() {
      return this.mKey instanceof ReportDefMappedExcelCK?((ReportDefMappedExcelCK)this.mKey).getReportDefMappedExcelPK():(ReportDefMappedExcelPK)this.mKey;
   }
}
