// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.base.ValidationException;

public class VirementRowValidationException extends ValidationException {

   private int mGroupIndex;
   private int mRowIndex;
   private int mDimIndex;


   public VirementRowValidationException(String reason, int groupIndex, int rowIndex, int dimIndex) {
      super(reason);
      this.mGroupIndex = groupIndex;
      this.mRowIndex = rowIndex;
      this.mDimIndex = dimIndex;
   }

   public int getGroupIndex() {
      return this.mGroupIndex;
   }

   public int getRowIndex() {
      return this.mRowIndex;
   }

   public int getDimIndex() {
      return this.mDimIndex;
   }

   public void setGroupIndex(int groupIndex) {
      this.mGroupIndex = groupIndex;
   }

   public void setRowIndex(int rowIndex) {
      this.mRowIndex = rowIndex;
   }

   public String toString() {
      return this.getMessage();
   }

   public boolean equals(Object obj) {
      if(!(obj instanceof VirementRowValidationException)) {
         return false;
      } else {
         VirementRowValidationException other = (VirementRowValidationException)obj;
         return this.getMessage().equals(other.getMessage()) && this.getGroupIndex() == other.getGroupIndex() && this.getRowIndex() == other.getRowIndex() && this.getDimIndex() == other.getDimIndex();
      }
   }

   public int hashCode() {
      return this.mGroupIndex + this.mRowIndex + this.mDimIndex;
   }
}
