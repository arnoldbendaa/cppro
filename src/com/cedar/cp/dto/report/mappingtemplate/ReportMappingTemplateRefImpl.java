// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.mappingtemplate;

import com.cedar.cp.api.report.mappingtemplate.ReportMappingTemplateRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
import java.io.Serializable;

public class ReportMappingTemplateRefImpl extends EntityRefImpl implements ReportMappingTemplateRef, Serializable {

   public ReportMappingTemplateRefImpl(ReportMappingTemplatePK key, String narrative) {
      super(key, narrative);
   }

   public ReportMappingTemplatePK getReportMappingTemplatePK() {
      return (ReportMappingTemplatePK)this.mKey;
   }
}
