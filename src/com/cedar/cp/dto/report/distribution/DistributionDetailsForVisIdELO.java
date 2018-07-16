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

public class DistributionDetailsForVisIdELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Distribution", "DistributionLink", "DistributionLink"};
   private transient DistributionRef mDistributionEntityRef;
   private transient DistributionLinkRef mDistributionLinkEntityRef;
   private transient String mDescription;
   private transient boolean mRaDistribution;
   private transient String mDirRoot;
   private transient int mDestinationId;
   private transient Integer mDestinationType;


   public DistributionDetailsForVisIdELO() {
      super(new String[]{"Distribution", "DistributionLink", "Description", "RaDistribution", "DirRoot", "DestinationId", "DestinationType"});
   }

   public void add(DistributionRef eRefDistribution, DistributionLinkRef eRefDistributionLink, String col1, boolean col2, String col3, int col4, Integer col5) {
      ArrayList l = new ArrayList();
      l.add(eRefDistribution);
      l.add(eRefDistributionLink);
      l.add(col1);
      l.add(new Boolean(col2));
      l.add(col3);
      l.add(new Integer(col4));
      l.add(col5);
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
      this.mDistributionLinkEntityRef = (DistributionLinkRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mRaDistribution = ((Boolean)l.get(var4++)).booleanValue();
      this.mDirRoot = (String)l.get(var4++);
      this.mDestinationId = ((Integer)l.get(var4++)).intValue();
      this.mDestinationType = (Integer)l.get(var4++);
   }

   public DistributionRef getDistributionEntityRef() {
      return this.mDistributionEntityRef;
   }

   public DistributionLinkRef getDistributionLinkEntityRef() {
      return this.mDistributionLinkEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean getRaDistribution() {
      return this.mRaDistribution;
   }

   public String getDirRoot() {
      return this.mDirRoot;
   }

   public int getDestinationId() {
      return this.mDestinationId;
   }

   public Integer getDestinationType() {
      return this.mDestinationType;
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
