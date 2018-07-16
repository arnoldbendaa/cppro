// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.type;

import com.cedar.cp.api.report.type.ReportTypeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.type.ReportTypePK;
import java.io.Serializable;

public class ReportTypeRefImpl extends EntityRefImpl implements ReportTypeRef, Serializable {

   public ReportTypeRefImpl(ReportTypePK key, String narrative) {
      super(key, narrative);
   }

   public ReportTypePK getReportTypePK() {
      return (ReportTypePK)this.mKey;
   }
}
