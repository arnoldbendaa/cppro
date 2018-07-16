// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.pack;

import com.cedar.cp.api.report.pack.ReportPackLinkRef;
import com.cedar.cp.api.report.pack.ReportPackRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckReportDistributionELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ReportPackLink", "ReportPack"};
   private transient ReportPackLinkRef mReportPackLinkEntityRef;
   private transient ReportPackRef mReportPackEntityRef;
   private transient int mReportPackId;


   public CheckReportDistributionELO() {
      super(new String[]{"ReportPackLink", "ReportPack", "ReportPackId"});
   }

   public void add(ReportPackLinkRef eRefReportPackLink, ReportPackRef eRefReportPack, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefReportPackLink);
      l.add(eRefReportPack);
      l.add(new Integer(col1));
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mReportPackLinkEntityRef = (ReportPackLinkRef)l.get(index);
      this.mReportPackEntityRef = (ReportPackRef)l.get(var4++);
      this.mReportPackId = ((Integer)l.get(var4++)).intValue();
   }

   public ReportPackLinkRef getReportPackLinkEntityRef() {
      return this.mReportPackLinkEntityRef;
   }

   public ReportPackRef getReportPackEntityRef() {
      return this.mReportPackEntityRef;
   }

   public int getReportPackId() {
      return this.mReportPackId;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
