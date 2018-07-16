// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.template;

import com.cedar.cp.api.report.template.ReportTemplateRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.template.ReportTemplatePK;
import java.io.Serializable;

public class ReportTemplateRefImpl extends EntityRefImpl implements ReportTemplateRef, Serializable {

   public ReportTemplateRefImpl(ReportTemplatePK key, String narrative) {
      super(key, narrative);
   }

   public ReportTemplatePK getReportTemplatePK() {
      return (ReportTemplatePK)this.mKey;
   }
}
