// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.distribution;

import com.cedar.cp.api.report.distribution.DistributionRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllDistributionsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Distribution", "DistributionLink"};
   private transient DistributionRef mDistributionEntityRef;
   private transient String mDescription;
   private transient boolean mRaDistribution;


   public AllDistributionsELO() {
      super(new String[]{"Distribution", "Description", "RaDistribution"});
   }

   public void add(DistributionRef eRefDistribution, String col1, boolean col2) {
      ArrayList l = new ArrayList();
      l.add(eRefDistribution);
      l.add(col1);
      l.add(new Boolean(col2));
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
      this.mDistributionEntityRef = (DistributionRef)l.get(index);
      this.mDescription = (String)l.get(var4++);
      this.mRaDistribution = ((Boolean)l.get(var4++)).booleanValue();
   }

   public DistributionRef getDistributionEntityRef() {
      return this.mDistributionEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean getRaDistribution() {
      return this.mRaDistribution;
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
