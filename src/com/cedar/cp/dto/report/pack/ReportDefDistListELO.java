// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.pack;

import com.cedar.cp.api.report.definition.ReportDefinitionRef;
import com.cedar.cp.api.report.distribution.DistributionRef;
import com.cedar.cp.api.report.pack.ReportPackLinkRef;
import com.cedar.cp.api.report.pack.ReportPackRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportDefDistListELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ReportPack", "ReportPackLink", "ReportPackLink", "ReportDefinition", "Distribution"};
   private transient ReportPackRef mReportPackEntityRef;
   private transient ReportPackLinkRef mReportPackLinkEntityRef;
   private transient ReportDefinitionRef mReportDefinitionEntityRef;
   private transient DistributionRef mDistributionEntityRef;
   private transient boolean mGroupAttachment;


   public ReportDefDistListELO() {
      super(new String[]{"ReportPack", "ReportPackLink", "ReportDefinition", "Distribution", "GroupAttachment"});
   }

   public void add(ReportPackRef eRefReportPack, ReportPackLinkRef eRefReportPackLink, ReportDefinitionRef eRefReportDefinition, DistributionRef eRefDistribution, boolean col1) {
      ArrayList l = new ArrayList();
      l.add(eRefReportPack);
      l.add(eRefReportPackLink);
      l.add(eRefReportDefinition);
      l.add(eRefDistribution);
      l.add(new Boolean(col1));
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
      this.mReportPackEntityRef = (ReportPackRef)l.get(index);
      this.mReportPackLinkEntityRef = (ReportPackLinkRef)l.get(var4++);
      this.mReportDefinitionEntityRef = (ReportDefinitionRef)l.get(var4++);
      this.mDistributionEntityRef = (DistributionRef)l.get(var4++);
      this.mGroupAttachment = ((Boolean)l.get(var4++)).booleanValue();
   }

   public ReportPackRef getReportPackEntityRef() {
      return this.mReportPackEntityRef;
   }

   public ReportPackLinkRef getReportPackLinkEntityRef() {
      return this.mReportPackLinkEntityRef;
   }

   public ReportDefinitionRef getReportDefinitionEntityRef() {
      return this.mReportDefinitionEntityRef;
   }

   public DistributionRef getDistributionEntityRef() {
      return this.mDistributionEntityRef;
   }

   public boolean getGroupAttachment() {
      return this.mGroupAttachment;
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
