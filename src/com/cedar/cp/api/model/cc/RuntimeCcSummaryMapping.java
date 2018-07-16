// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.model.cc.RuntimeCcDeploymentLine;

public class RuntimeCcSummaryMapping {

   private RuntimeCcDeploymentLine mDeploymentLine;
   private int[] mCellAddress;
   private int[] mPositions;
   private String mDataType;
   private String mSummaryField;


   public RuntimeCcSummaryMapping(RuntimeCcDeploymentLine deploymentLine, int[] cellAddress, int[] positions, String dataType, String summaryField) {
      this.mDeploymentLine = deploymentLine;
      this.mCellAddress = cellAddress;
      this.mPositions = positions;
      this.mDataType = dataType;
      this.mSummaryField = summaryField;
   }

   public int[] getCellAddress() {
      return this.mCellAddress;
   }

   public int[] getPositions() {
      return this.mPositions;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public String getSummaryField() {
      return this.mSummaryField;
   }

   public RuntimeCcDeploymentLine getDeploymentLine() {
      return this.mDeploymentLine;
   }

   public void setDeploymentLine(RuntimeCcDeploymentLine deploymentLine) {
      this.mDeploymentLine = deploymentLine;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("RuntimeCcSummaryMapping[cell=");
      int[] arr$ = this.mCellAddress;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int i = arr$[i$];
         builder.append(i);
         builder.append(',');
      }

      builder.append(",dataType=" + this.mDataType);
      builder.append(",field=" + this.mSummaryField);
      return builder.toString();
   }

   public boolean equals(Object other) {
      if(!(other instanceof RuntimeCcSummaryMapping)) {
         return false;
      } else {
         RuntimeCcSummaryMapping mapping = (RuntimeCcSummaryMapping)other;
         if(!mapping.getDataType().equals(this.mDataType)) {
            return false;
         } else {
            int[] otherAddress = mapping.getCellAddress();
            if(otherAddress.length != this.mCellAddress.length) {
               return false;
            } else {
               for(int i = 0; i < otherAddress.length; ++i) {
                  if(otherAddress[i] != this.mCellAddress[i]) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   public int hashCode() {
      return this.mDataType.hashCode();
   }
}
