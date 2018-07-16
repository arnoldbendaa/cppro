// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dataEntry;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import com.cedar.cp.dto.model.ModelPK;
import java.util.ArrayList;
import java.util.List;

public class MassUpdateTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private int mModelId;
   private String mModelVisId;
   private int mFinanceCubeId;
   private String mFinanceCubeVisId;
   private String mReason;
   private String mReference;
   private String mChangeType;
   private List mChangeCells;
   private String mCurrentValue;
   private String mChangePercent;
   private String mChangeBy;
   private String mChangeTo;
   private int mDataTypeId;
   private String mDataTypeVisId;
   private int mRoundUnits;
   private int mCalId;
   private boolean mHoldNegative;
   private boolean mHoldPositive;
   private List mHoldCells;
   private boolean mReport;
   private List mDimensionHeader;
   private String mLastAction;
   private boolean mCellPosting;


   public MassUpdateTaskRequest(ModelPK pk) {
      this.addExclusiveAccess(pk.toString());
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("FinanceCube=" + this.getFinanceCubeVisId());
      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.MassUpdateTask";
   }

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
      return this.mCurrentValue != null && this.mCurrentValue.length() != 0?this.mCurrentValue:"0";
   }

   public void setCurrentValue(String currentValue) {
      this.mCurrentValue = currentValue;
   }

   public String getChangePercent() {
      return this.mChangePercent != null && this.mChangePercent.length() != 0?this.mChangePercent:"0";
   }

   public void setChangePercent(String changePercent) {
      this.mChangePercent = changePercent;
   }

   public String getChangeBy() {
      return this.mChangeBy != null && this.mChangeBy.length() != 0?this.mChangeBy:"0";
   }

   public void setChangeBy(String changeBy) {
      this.mChangeBy = changeBy;
   }

   public String getChangeTo() {
      return this.mChangeTo != null && this.mChangeTo.length() != 0?this.mChangeTo:"0";
   }

   public void setChangeTo(String changeTo) {
      this.mChangeTo = changeTo;
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

   public void setCalId(int calId) {
      this.mCalId = calId;
   }

   public int getCalId() {
      return this.mCalId;
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

   public void setCellPosting(boolean cellPosting) {
      this.mCellPosting = cellPosting;
   }

   public boolean isCellPosting() {
      return this.mCellPosting;
   }
}
