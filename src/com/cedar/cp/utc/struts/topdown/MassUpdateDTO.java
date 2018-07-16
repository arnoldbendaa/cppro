// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.api.dataEntry.MassUpdateParameters;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MassUpdateDTO implements MassUpdateParameters, Serializable {

   private int mModelId;
   private String mModelVisId;
   private int mFinanceCubeId;
   private String mFinanceCubeVisId;
   private String mReason;
   private String mReference;
   private String mChangeType;
   private List mChangeCells;
   private String mCurrentValue;
   private String mCurrentValueForDisplay;
   private String mChangeBy;
   private String mChangeTo;
   private String mChangePercent;
   private int mCalSeqId;
   private int mSrcCalId;
   private int mCalId;
   private String mCalVisId;
   private int mDataTypeId;
   private String mDataTypeVisId;
   private int mRoundUnits;
   private boolean mHoldNegative;
   private boolean mHoldPositive;
   private List mHoldCells;
   private boolean mReport;
   private boolean mCellPosting;
   private List mDimensionHeader;
   private String mLastAction;


   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public String getModelVisId() {
      return this.mModelVisId;
   }

   public void setModelVisId(String modelVisId) {
      this.mModelVisId = modelVisId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   public String getFinanceCubeVisId() {
      return this.mFinanceCubeVisId;
   }

   public void setFinanceCubeVisId(String financeCubeVisId) {
      this.mFinanceCubeVisId = financeCubeVisId;
   }

   public String getReason() {
      return this.mReason;
   }

   public void setReason(String reason) {
      this.mReason = reason;
   }

   public String getReference() {
      return this.mReference;
   }

   public void setReference(String reference) {
      this.mReference = reference;
   }

   public String getChangeType() {
      return this.mChangeType;
   }

   public void setChangeType(String changeType) {
      this.mChangeType = changeType;
   }

   public List getChangeCells() {
      if(this.mChangeCells == null) {
         this.mChangeCells = new ArrayList();
      }

      return this.mChangeCells;
   }

   public void setChangeCells(List changeCells) {
      this.mChangeCells = changeCells;
   }

   public void addChangedCells(List row) {
      this.getChangeCells().add(row);
   }

   public String getCurrentValue() {
      return this.mCurrentValue;
   }

   public void setCurrentValue(String currentValue) {
      this.mCurrentValue = currentValue;
   }

   public String getCurrentValueForDisplay() {
      return this.mCurrentValueForDisplay;
   }

   public void setCurrentValueForDisplay(String currentValueForDisplay) {
      this.mCurrentValueForDisplay = currentValueForDisplay;
   }

   public String getChangeBy() {
      return this.mChangeBy;
   }

   public void setChangeBy(String changeBy) {
      this.mChangeBy = changeBy;
   }

   public String getChangeTo() {
      return this.mChangeTo;
   }

   public void setChangeTo(String changeTo) {
      this.mChangeTo = changeTo;
   }

   public String getChangePercent() {
      return this.mChangePercent;
   }

   public void setChangePercent(String changePercent) {
      this.mChangePercent = changePercent;
   }

   public int getCalSeqId() {
      return this.mCalSeqId;
   }

   public void setCalSeqId(int calSeqId) {
      this.mCalSeqId = calSeqId;
   }

   public int getSrcCalId() {
      return this.mSrcCalId;
   }

   public void setSrcCalId(int srcCalId) {
      this.mSrcCalId = srcCalId;
   }

   public int getCalId() {
      return this.mCalId;
   }

   public void setCalId(int calId) {
      this.mCalId = calId;
   }

   public String getCalVisId() {
      return this.mCalVisId;
   }

   public void setCalVisId(String calVisId) {
      this.mCalVisId = calVisId;
   }

   public int getDataTypeId() {
      return this.mDataTypeId;
   }

   public void setDataTypeId(int dataTypeId) {
      this.mDataTypeId = dataTypeId;
   }

   public String getDataTypeVisId() {
      return this.mDataTypeVisId;
   }

   public void setDataTypeVisId(String dataTypeVisId) {
      this.mDataTypeVisId = dataTypeVisId;
   }

   public int getRoundUnits() {
      return this.mRoundUnits;
   }

   public void setRoundUnits(int roundUnits) {
      this.mRoundUnits = roundUnits;
   }

   public boolean isHoldNegative() {
      return this.mHoldNegative;
   }

   public void setHoldNegative(boolean holdNegative) {
      this.mHoldNegative = holdNegative;
   }

   public boolean isHoldPositive() {
      return this.mHoldPositive;
   }

   public void setHoldPositive(boolean holdPositive) {
      this.mHoldPositive = holdPositive;
   }

   public List getHoldCells() {
      if(this.mHoldCells == null) {
         this.mHoldCells = new ArrayList();
      }

      return this.mHoldCells;
   }

   public void setHoldCells(List holdCells) {
      this.mHoldCells = holdCells;
   }

   public void addHoldCells(List row) {
      this.getHoldCells().add(row);
   }

   public boolean isReport() {
      return this.mReport;
   }

   public void setReport(boolean report) {
      this.mReport = report;
   }

   public boolean isCellPosting() {
      return this.mCellPosting;
   }

   public void setCellPosting(boolean cellPosting) {
      this.mCellPosting = cellPosting;
   }

   public List getDimensionHeader() {
      return this.mDimensionHeader;
   }

   public void setDimensionHeader(List dimensionHeader) {
      this.mDimensionHeader = dimensionHeader;
   }

   public String getLastAction() {
      return this.mLastAction;
   }

   public void setLastAction(String lastAction) {
      this.mLastAction = lastAction;
   }
}
