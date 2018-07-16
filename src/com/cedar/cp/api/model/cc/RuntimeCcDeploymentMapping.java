// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import java.io.Serializable;

public class RuntimeCcDeploymentMapping implements Serializable {

   private int mCcDeploymentLineId;
   private String mSummaryFieldName;
   private int[] mExplicitStructureElementIds;
   private int[] mExplicitStructureElementPositions;
   private String mDataType;


   public int getCcDeploymentLineId() {
      return this.mCcDeploymentLineId;
   }

   public void setCcDeploymentLineId(int ccDeploymentLineId) {
      this.mCcDeploymentLineId = ccDeploymentLineId;
   }

   public String getSummaryFieldName() {
      return this.mSummaryFieldName;
   }

   public void setSummaryFieldName(String summaryFieldName) {
      this.mSummaryFieldName = summaryFieldName;
   }

   public int[] getExplicitStructureElementIds() {
      return this.mExplicitStructureElementIds;
   }

   public void setExplicitStructureElementIds(int[] explicitStructureElementIds) {
      this.mExplicitStructureElementIds = explicitStructureElementIds;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public void setDataType(String dataType) {
      this.mDataType = dataType;
   }

   public int[] getExplicitStructureElementPositions() {
      return this.mExplicitStructureElementPositions;
   }

   public void setExplicitStructureElementPositions(int[] explicitStructureElementPositions) {
      this.mExplicitStructureElementPositions = explicitStructureElementPositions;
   }
}
