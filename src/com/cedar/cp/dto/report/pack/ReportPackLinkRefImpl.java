// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.pack;

import com.cedar.cp.api.report.pack.ReportPackLinkRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.report.pack.ReportPackLinkCK;
import com.cedar.cp.dto.report.pack.ReportPackLinkPK;
import java.io.Serializable;

public class ReportPackLinkRefImpl extends EntityRefImpl implements ReportPackLinkRef, Serializable {

   public ReportPackLinkRefImpl(ReportPackLinkCK key, String narrative) {
      super(key, narrative);
   }

   public ReportPackLinkRefImpl(ReportPackLinkPK key, String narrative) {
      super(key, narrative);
   }

   public ReportPackLinkPK getReportPackLinkPK() {
      return this.mKey instanceof ReportPackLinkCK?((ReportPackLinkCK)this.mKey).getReportPackLinkPK():(ReportPackLinkPK)this.mKey;
   }
}
