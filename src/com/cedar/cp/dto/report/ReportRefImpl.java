// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report;

import com.cedar.cp.api.report.ReportRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.ReportPK;
import java.io.Serializable;

public class ReportRefImpl extends EntityRefImpl implements ReportRef, Serializable {

   public ReportRefImpl(ReportPK key, String narrative) {
      super(key, narrative);
   }

   public ReportPK getReportPK() {
      return (ReportPK)this.mKey;
   }
}
