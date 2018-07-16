// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.type.param;

import com.cedar.cp.api.report.type.param.ReportTypeParamRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.type.param.ReportTypeParamCK;
import com.cedar.cp.dto.report.type.param.ReportTypeParamPK;
import java.io.Serializable;

public class ReportTypeParamRefImpl extends EntityRefImpl implements ReportTypeParamRef, Serializable {

   public ReportTypeParamRefImpl(ReportTypeParamCK key, String narrative) {
      super(key, narrative);
   }

   public ReportTypeParamRefImpl(ReportTypeParamPK key, String narrative) {
      super(key, narrative);
   }

   public ReportTypeParamPK getReportTypeParamPK() {
      return this.mKey instanceof ReportTypeParamCK?((ReportTypeParamCK)this.mKey).getReportTypeParamPK():(ReportTypeParamPK)this.mKey;
   }
}
