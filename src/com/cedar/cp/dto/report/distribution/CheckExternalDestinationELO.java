// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.distribution;

import com.cedar.cp.api.report.distribution.DistributionLinkRef;
import com.cedar.cp.api.report.distribution.DistributionRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckExternalDestinationELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"DistributionLink", "Distribution"};
   private transient DistributionLinkRef mDistributionLinkEntityRef;
   private transient DistributionRef mDistributionEntityRef;
   private transient int mDestinationId;


   public CheckExternalDestinationELO() {
      super(new String[]{"DistributionLink", "Distribution", "DestinationId"});
   }

   public void add(DistributionLinkRef eRefDistributionLink, DistributionRef eRefDistribution, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefDistributionLink);
      l.add(eRefDistribution);
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
      this.mDistributionLinkEntityRef = (DistributionLinkRef)l.get(index);
      this.mDistributionEntityRef = (DistributionRef)l.get(var4++);
      this.mDestinationId = ((Integer)l.get(var4++)).intValue();
   }

   public DistributionLinkRef getDistributionLinkEntityRef() {
      return this.mDistributionLinkEntityRef;
   }

   public DistributionRef getDistributionEntityRef() {
      return this.mDistributionEntityRef;
   }

   public int getDestinationId() {
      return this.mDestinationId;
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
