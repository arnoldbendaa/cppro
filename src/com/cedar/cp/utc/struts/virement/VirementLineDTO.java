// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.LazyList;
import com.cedar.cp.utc.struts.virement.VirementLineDTO$1;
import com.cedar.cp.utc.struts.virement.VirementLineDTO$2;
import com.cedar.cp.utc.struts.virement.VirementLineDTO$3;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

public class VirementLineDTO implements Serializable {

   private transient NumberFormat mNumberFormat;
   private String mKey;
   private String mDataTypeVisId;
   private String mDataTypeId;
   private String mTransferValue;
   private String mSpreadProfileVisId;
   private String mSpreadProfileId;
   private String mAllocationThreshold;
   private boolean mTo = true;
   private boolean mSummaryLine;
   private Collection mCells = new LazyList(new VirementLineDTO$1(this));
   private Collection mSpreadProfile = new LazyList(new VirementLineDTO$3(this));
   private Collection mDataTypes = new LazyList(new VirementLineDTO$2(this));


   private double convertToDouble(String value) {
      try {
         return this.getNumberFormat().parse(value).doubleValue();
      } catch (ParseException var3) {
         return 0.0D;
      }
   }

   public double getTransferValueAsDouble() {
      return this.convertToDouble(this.getTransferValue());
   }

   public String getTransferValue() {
      return this.mTransferValue;
   }

   public void setTransferValue(String transferValue) {
      this.mTransferValue = transferValue;
   }

   public boolean isTo() {
      return this.mTo;
   }

   public void setTo(boolean to) {
      this.mTo = to;
   }

   public Collection getCells() {
      return this.mCells;
   }

   public void setCells(Collection cells) {
      this.mCells = cells;
   }

   public String getFromTo() {
      return this.isTo()?"To":"From";
   }

   public String getKey() {
      return this.mKey != null && this.mKey.trim().length() == 0?null:this.mKey;
   }

   public void setKey(String key) {
      this.mKey = key;
   }

   private NumberFormat getNumberFormat() {
      if(this.mNumberFormat == null) {
         this.mNumberFormat = NumberFormat.getInstance();
      }

      return this.mNumberFormat;
   }

   public boolean isSummaryLine() {
      return this.mSummaryLine;
   }

   public void setSummaryLine(boolean summaryLine) {
      this.mSummaryLine = summaryLine;
   }

   public Collection getSpreadProfile() {
      return this.mSpreadProfile;
   }

   public void setSpreadProfile(Collection spreadProfile) {
      this.mSpreadProfile = spreadProfile;
   }

   public String getSpreadProfileId() {
      return this.mSpreadProfileId;
   }

   public void setSpreadProfileId(String spreadProfileId) {
      this.mSpreadProfileId = spreadProfileId;
   }

   public String getSpreadProfileVisId() {
      return this.mSpreadProfileVisId != null?this.mSpreadProfileVisId:"Custom";
   }

   public void setSpreadProfileVisId(String spreadProfileVisId) {
      this.mSpreadProfileVisId = spreadProfileVisId;
   }

   public String getAllocationThreshold() {
      return this.mAllocationThreshold;
   }

   public void setAllocationThreshold(String allocationThreshold) {
      this.mAllocationThreshold = allocationThreshold;
   }

   public String getDataTypeVisId() {
      return this.mDataTypeVisId;
   }

   public void setDataTypeVisId(String dataTypeVisId) {
      this.mDataTypeVisId = dataTypeVisId;
   }

   public String getDataTypeId() {
      return this.mDataTypeId;
   }

   public void setDataTypeId(String dataTypeId) {
      this.mDataTypeId = dataTypeId;
   }

   public Collection getDataTypes() {
      return this.mDataTypes;
   }

   public void setDataTypes(Collection dataTypes) {
      this.mDataTypes = dataTypes;
   }

   public void update(VirementLineDTO srcLine) {
      this.mKey = srcLine.mKey;
      this.mDataTypeVisId = srcLine.mDataTypeVisId;
      this.mDataTypeId = srcLine.mDataTypeId;
      this.mTransferValue = srcLine.mTransferValue;
      this.mSpreadProfileVisId = srcLine.mSpreadProfileVisId;
      this.mSpreadProfileId = srcLine.mSpreadProfileId;
      this.mAllocationThreshold = srcLine.mAllocationThreshold;
      this.mTo = srcLine.mTo;
      this.mSummaryLine = srcLine.mSummaryLine;
      this.mCells = new ArrayList(srcLine.mCells);
      if(this.mSummaryLine && srcLine.mSpreadProfile != null && !srcLine.mSpreadProfile.isEmpty()) {
         this.mSpreadProfile = new ArrayList(srcLine.mSpreadProfile);
      }

      this.mDataTypes = new ArrayList(srcLine.mDataTypes);
   }
}
