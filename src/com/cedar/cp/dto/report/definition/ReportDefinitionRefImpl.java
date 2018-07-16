// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.api.report.definition.ReportDefinitionRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import java.io.Serializable;

public class ReportDefinitionRefImpl extends EntityRefImpl implements ReportDefinitionRef, Serializable {

   public ReportDefinitionRefImpl(ReportDefinitionPK key, String narrative) {
      super(key, narrative);
   }

   public ReportDefinitionPK getReportDefinitionPK() {
      return (ReportDefinitionPK)this.mKey;
   }
}
