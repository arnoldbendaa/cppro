// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.ArrayList;
import java.util.List;

public class RechargeTask$MyCheckpoint extends AbstractTaskCheckpoint {

   private int mStepNumber;
   private int mNumDimensions;
   private String[] mDimNames;
   private String[] mDimDescrs;
   private int mCalendarStructureId;
   private int mRechargeIndex = 0;
   private String mModelDescription;
   private String mFinanceCubeDescription;
   private List mSourceDataTypes;
   private List mTargetDataTypes;
   private Integer mBudgetActivityId = null;
   private boolean[] mSourceAllLeaf;
   private boolean[] mTargetAllLeaf;
   private EntityRef mAllocationDataTypeRef;
   private boolean mManualRatios;


   public List toDisplay() {
      ArrayList l = new ArrayList();
      if(this.getStepNumber() == 0) {
         l.add("check worktables don\'t exist");
      } else if(this.getStepNumber() == 1) {
         l.add("create worktables");
      } else if(this.getStepNumber() == 2) {
         l.add("update");
      } else {
         l.add("drop worktables");
      }

      return l;
   }

   public int getStepNumber() {
      return this.mStepNumber;
   }

   public void setStepNumberUp() {
      ++this.mStepNumber;
   }

   public void setNumDimensions(int numDims) {
      this.mNumDimensions = numDims;
      this.mSourceAllLeaf = new boolean[this.mNumDimensions];
      this.mTargetAllLeaf = new boolean[this.mNumDimensions];

      for(int i = 0; i < numDims; ++i) {
         this.mSourceAllLeaf[i] = true;
         this.mTargetAllLeaf[i] = true;
      }

   }

   public int getNumDimensions() {
      return this.mNumDimensions;
   }

   public void setDimNames(String[] dimNames) {
      this.mDimNames = dimNames;
   }

   public String[] getDimNames() {
      return this.mDimNames;
   }

   public void setDimDescrs(String[] dimDescrs) {
      this.mDimDescrs = dimDescrs;
   }

   public String[] getDimDescrs() {
      return this.mDimDescrs;
   }

   public void setSourceAllLeaf(int dimIndex, boolean b) {
      this.mSourceAllLeaf[dimIndex] = b;
   }

   public boolean isSourceAllLeaf(int dimIndex) {
      return this.mSourceAllLeaf[dimIndex];
   }

   public void setTargetAllLeaf(int dimIndex, boolean b) {
      this.mTargetAllLeaf[dimIndex] = b;
   }

   public boolean isTargetAllLeaf(int dimIndex) {
      return this.mTargetAllLeaf[dimIndex];
   }

   public void resetSourceDataTypes() {
      this.mSourceDataTypes = new ArrayList();
   }

   public void addSourceDataType(String dataTypeDescr) {
      String dataTypeVisId = dataTypeDescr.substring(0, 2);
      if(!this.mSourceDataTypes.contains(dataTypeVisId)) {
         this.mSourceDataTypes.add(dataTypeVisId);
      }

   }

   public List getSourceDataTypes() {
      return this.mSourceDataTypes;
   }

   public void resetTargetDataTypes() {
      this.mTargetDataTypes = new ArrayList();
   }

   public void addTargetDataType(String dataTypeDescr) {
      String dataTypeVisId = dataTypeDescr.substring(0, 2);
      if(!this.mTargetDataTypes.contains(dataTypeVisId)) {
         this.mTargetDataTypes.add(dataTypeVisId);
      }

   }

   public List getTargetDataTypes() {
      return this.mTargetDataTypes;
   }

   public void setCalendarStructureId(int calStructureId) {
      this.mCalendarStructureId = calStructureId;
   }

   public int getCalendarStructureId() {
      return this.mCalendarStructureId;
   }

   public void setRechargeIndexUp() {
      ++this.mRechargeIndex;
   }

   public int getRechargeIndex() {
      return this.mRechargeIndex;
   }

   public void setFinanceCubeDescription(String descr) {
      this.mFinanceCubeDescription = descr;
   }

   public String getFinanceCubeDescription() {
      return this.mFinanceCubeDescription;
   }

   public void setModelDescription(String descr) {
      this.mModelDescription = descr;
   }

   public String getModelDescription() {
      return this.mModelDescription;
   }

   public void setAllocationDataTypeRef(DataTypeRef entityRef) {
      this.mAllocationDataTypeRef = entityRef;
   }

   public EntityRef getAllocationDataTypeRef() {
      return this.mAllocationDataTypeRef;
   }

   public void setBudgetActivityId(int nextSeq) {
      this.mBudgetActivityId = Integer.valueOf(nextSeq);
   }

   public Integer getBudgetActivityId() {
      return this.mBudgetActivityId;
   }

   public void setManualRatios(boolean b) {
      this.mManualRatios = b;
   }

   public boolean isManualRatios() {
      return this.mManualRatios;
   }
}
