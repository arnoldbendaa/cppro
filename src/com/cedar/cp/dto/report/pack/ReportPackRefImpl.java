// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.pack;

import com.cedar.cp.api.report.pack.ReportPackRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.pack.ReportPackPK;
import java.io.Serializable;

public class ReportPackRefImpl extends EntityRefImpl implements ReportPackRef, Serializable {

   public ReportPackRefImpl(ReportPackPK key, String narrative) {
      super(key, narrative);
   }

   public ReportPackPK getReportPackPK() {
      return (ReportPackPK)this.mKey;
   }
}
