// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.api.report.definition.ReportDefFormRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.definition.ReportDefFormCK;
import com.cedar.cp.dto.report.definition.ReportDefFormPK;
import java.io.Serializable;

public class ReportDefFormRefImpl extends EntityRefImpl implements ReportDefFormRef, Serializable {

   public ReportDefFormRefImpl(ReportDefFormCK key, String narrative) {
      super(key, narrative);
   }

   public ReportDefFormRefImpl(ReportDefFormPK key, String narrative) {
      super(key, narrative);
   }

   public ReportDefFormPK getReportDefFormPK() {
      return this.mKey instanceof ReportDefFormCK?((ReportDefFormCK)this.mKey).getReportDefFormPK():(ReportDefFormPK)this.mKey;
   }
}
