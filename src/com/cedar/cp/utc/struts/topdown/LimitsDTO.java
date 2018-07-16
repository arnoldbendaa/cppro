// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.util.NumberFormatter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LimitsDTO {

   private Integer mLimitId;
   private Integer mRespAreaId;
   private List mStructureElement;
   private BigDecimal mMinValue;
   private BigDecimal mMaxValue;
   private double mCurrentValue;


   public Integer getLimitId() {
      return this.mLimitId;
   }

   public void setLimitId(Integer limitId) {
      this.mLimitId = limitId;
   }

   public int getBudgetLimitId() {
      return this.mLimitId == null?0:this.mLimitId.intValue();
   }

   public Integer getRespAreaId() {
      return this.mRespAreaId;
   }

   public void setRespAreaId(Integer respAreaId) {
      this.mRespAreaId = respAreaId;
   }

   public List getStructureElement() {
      return this.mStructureElement;
   }

   public void setStructureElement(List structureElement) {
      this.mStructureElement = structureElement;
   }

   public void addStructureElement(String s) {
      if(this.mStructureElement == null) {
         this.mStructureElement = new ArrayList();
      }

      this.mStructureElement.add(s);
   }

   public BigDecimal getMinValue() {
      return this.mMinValue;
   }

   public String getMinString() {
      String s = "";
      if(this.mMinValue != null) {
         s = NumberFormatter.format(this.mMinValue.doubleValue());
      }

      return s;
   }

   public void setMinValue(BigDecimal minValue) {
      this.mMinValue = minValue;
   }

   public BigDecimal getMaxValue() {
      return this.mMaxValue;
   }

   public String getMaxString() {
      String s = "";
      if(this.mMaxValue != null) {
         s = NumberFormatter.format(this.mMaxValue.doubleValue());
      }

      return s;
   }

   public void setMaxValue(BigDecimal maxValue) {
      this.mMaxValue = maxValue;
   }

   public double getCurrentValue() {
      return this.mCurrentValue;
   }

   public void setCurrentValue(double currentValue) {
      this.mCurrentValue = currentValue;
   }

   public boolean isViolated() {
      return this.mMinValue != null && this.mCurrentValue < this.mMinValue.doubleValue()?true:this.mMaxValue != null && this.mCurrentValue > this.mMaxValue.doubleValue();
   }

   public String getRespArea() {
      return this.getStructureElement() != null && this.getStructureElement().size() >= 1?this.getStructureElement().get(0).toString():"";
   }

   public String getAccount() {
      return this.getStructureElement() != null && this.getStructureElement().size() >= 2?this.getStructureElement().get(1).toString():"";
   }

   public String getCal() {
      return this.getStructureElement() != null && this.getStructureElement().size() - 2 >= 2?this.getStructureElement().get(this.getStructureElement().size() - 2).toString():"";
   }

   public String getDataType() {
      return this.getStructureElement() != null && this.getStructureElement().size() - 1 >= 3?this.getStructureElement().get(this.getStructureElement().size() - 1).toString():"";
   }

   public String getOtherDims() {
      if(this.getStructureElement() != null && this.getStructureElement().size() - 2 != 2) {
         String value = "";

         for(int i = 2; i < this.getStructureElement().size() - 2; ++i) {
            if(i != 2) {
               value = value + " / ";
            }

            value = value + this.getStructureElement().get(i).toString();
         }

         return value;
      } else {
         return "";
      }
   }
}
