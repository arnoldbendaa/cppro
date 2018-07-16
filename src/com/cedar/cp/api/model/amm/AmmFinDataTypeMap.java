// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.amm;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import java.io.Serializable;

public class AmmFinDataTypeMap implements Serializable {

   private DataTypeRef mSourceRef;
   private String mSourceDescription;
   private int mSourceSubType;
   private DataTypeRef mTargetRef;
   private String mTargetDescription;
   private int mTargetSubType;
   private CalendarElementNode mStartYear;
   private int mStartOffset;
   private CalendarElementNode mEndYear;
   private int mEndOffset;


   public DataTypeRef getSourceRef() {
      return this.mSourceRef;
   }

   public void setSourceRef(DataTypeRef sourceRef) {
      this.mSourceRef = sourceRef;
   }

   public String getSourceDescription() {
      return this.mSourceDescription;
   }

   public void setSourceDescription(String sourceDescription) {
      this.mSourceDescription = sourceDescription;
   }

   public int getSourceSubType() {
      return this.mSourceSubType;
   }

   public void setSourceSubType(int sourceSubType) {
      this.mSourceSubType = sourceSubType;
   }

   public DataTypeRef getTargetRef() {
      return this.mTargetRef;
   }

   public void setTargetRef(DataTypeRef targetRef) {
      this.mTargetRef = targetRef;
   }

   public String getTargetDescription() {
      return this.mTargetDescription;
   }

   public void setTargetDescription(String targetDescription) {
      this.mTargetDescription = targetDescription;
   }

   public int getTargetSubType() {
      return this.mTargetSubType;
   }

   public void setTargetSubType(int targetSubType) {
      this.mTargetSubType = targetSubType;
   }

   public CalendarElementNode getStartYear() {
      return this.mStartYear;
   }

   public void setStartYear(CalendarElementNode startYear) {
      this.mStartYear = startYear;
   }

   public int getStartOffset() {
      return this.mStartOffset;
   }

   public void setStartOffset(int startOffset) {
      this.mStartOffset = startOffset;
   }

   public CalendarElementNode getEndYear() {
      return this.mEndYear;
   }

   public void setEndYear(CalendarElementNode endYear) {
      this.mEndYear = endYear;
   }

   public int getEndOffset() {
      return this.mEndOffset;
   }

   public void setEndOffset(int endOffset) {
      this.mEndOffset = endOffset;
   }

   public String getSourceDisplayText() {
      StringBuilder sb = new StringBuilder();
      if(this.getSourceRef() != null) {
         sb.append(this.getSourceRef());
      }

      if(this.getSourceDescription() != null && this.getSourceRef() != null) {
         sb.append(" - ").append(this.getSourceDescription());
      }

      return sb.toString();
   }

   public String getTargetDisplayText() {
      StringBuilder sb = new StringBuilder();
      if(this.getTargetRef() != null) {
         sb.append(this.getTargetRef());
      }

      if(this.getTargetDescription() != null && this.getTargetRef() != null) {
         sb.append(" - ").append(this.getTargetDescription());
      }

      return sb.toString();
   }
}
