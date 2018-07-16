// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.distribution;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.report.distribution.Distribution;
import com.cedar.cp.dto.report.distribution.DistributionPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DistributionImpl implements Distribution, Serializable, Cloneable {

   private List mInternalDestinationList;
   private List mExternalDestinationList;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private boolean mRaDistribution;
   private String mDirRoot;
   private int mVersionNum;


   public DistributionImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mRaDistribution = false;
      this.mDirRoot = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (DistributionPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean isRaDistribution() {
      return this.mRaDistribution;
   }

   public String getDirRoot() {
      return this.mDirRoot;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setRaDistribution(boolean paramRaDistribution) {
      this.mRaDistribution = paramRaDistribution;
   }

   public void setDirRoot(String paramDirRoot) {
      this.mDirRoot = paramDirRoot;
   }

   public List getInternalDestinationList() {
      if(this.mInternalDestinationList == null) {
         this.mInternalDestinationList = new ArrayList();
      }

      return this.mInternalDestinationList;
   }

   public List getExternalDestinationList() {
      if(this.mExternalDestinationList == null) {
         this.mExternalDestinationList = new ArrayList();
      }

      return this.mExternalDestinationList;
   }

   public void setInternalDestinationList(List internalDestinationList) {
      this.mInternalDestinationList = internalDestinationList;
   }

   public void setExternalDestinationList(List externalDestinationList) {
      this.mExternalDestinationList = externalDestinationList;
   }

   public EntityList getAvailAbleInternalDestination() {
      return null;
   }

   public EntityList getAvailAbleExternalDestination() {
      return null;
   }
}
