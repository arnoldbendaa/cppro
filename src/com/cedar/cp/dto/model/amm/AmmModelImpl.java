// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.amm.AmmDimensionMapping;
import com.cedar.cp.api.model.amm.AmmFinanceCubeMapping;
import com.cedar.cp.api.model.amm.AmmModel;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.model.amm.AmmModelPK;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AmmModelImpl implements AmmModel, Serializable, Cloneable {

   private boolean mModelLocked = false;
   private boolean mDimsLocked = false;
   private List<AmmDimensionMapping> mMappedDimensionMapping;
   private List<AmmDimensionMapping> mUnmappedDimensionMapping;
   private List<AmmDimensionMapping> mCalDimensionMapping;
   private int mDimIndex = 0;
   private List<AmmDimensionMapping> mDims;
   private List<AmmFinanceCubeMapping> mFinanceCubeMappings;
   private CalendarInfo mSourceInfo;
   private Object mPrimaryKey;
   private int mModelId;
   private int mSrcModelId;
   private Integer mInvalidatedByTaskId;
   private int mVersionNum;
   private ModelRef mTargetModelRef;
   private ModelRef mSourceModelRef;


   public AmmModelImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mModelId = 0;
      this.mSrcModelId = 0;
      this.mInvalidatedByTaskId = null;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (AmmModelPK)paramKey;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getSrcModelId() {
      return this.mSrcModelId;
   }

   public Integer getInvalidatedByTaskId() {
      return this.mInvalidatedByTaskId;
   }

   public ModelRef getTargetModelRef() {
      return this.mTargetModelRef;
   }

   public ModelRef getSourceModelRef() {
      return this.mSourceModelRef;
   }

   public void setTargetModelRef(ModelRef ref) {
      this.mTargetModelRef = ref;
   }

   public void setSourceModelRef(ModelRef ref) {
      this.mSourceModelRef = ref;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setModelId(int paramModelId) {
      this.mModelId = paramModelId;
   }

   public void setSrcModelId(int paramSrcModelId) {
      this.mSrcModelId = paramSrcModelId;
   }

   public void setInvalidatedByTaskId(Integer paramInvalidatedByTaskId) {
      this.mInvalidatedByTaskId = paramInvalidatedByTaskId;
   }

   public boolean isNew() {
      return this.mPrimaryKey == null;
   }

   public boolean isModelLocked() {
      return this.mModelLocked;
   }

   public void setModelLocked(boolean modelLocked) {
      this.mModelLocked = modelLocked;
   }

   public boolean isDimsLocked() {
      return this.mDimsLocked;
   }

   public void setDimsLocked(boolean dimsLocked) {
      this.mDimsLocked = dimsLocked;
   }

   public AmmDimensionMapping addMappedDimMapping(DimensionRef ref, DimensionRef sourceDimensionRef, HierarchyRef sourceHierarchyRef) throws ValidationException {
      if(this.mMappedDimensionMapping == null) {
         this.mMappedDimensionMapping = new ArrayList();
      }

      AmmDimensionMapping mapping = this.getAmmDimensionMapping(ref, sourceDimensionRef, sourceHierarchyRef);
      this.mMappedDimensionMapping.add(mapping);
      return mapping;
   }

   public AmmDimensionMapping addUnmappedDimMapping(DimensionRef ref) throws ValidationException {
      if(this.mUnmappedDimensionMapping == null) {
         this.mUnmappedDimensionMapping = new ArrayList();
      }

      AmmDimensionMapping mapping = this.getAmmDimensionMapping(ref, (DimensionRef)null, (HierarchyRef)null);
      this.mUnmappedDimensionMapping.add(mapping);
      return mapping;
   }

   public AmmDimensionMapping addUnmappedSourceDimMapping(DimensionRef ref, HierarchyRef hierRef) throws ValidationException {
      if(this.mUnmappedDimensionMapping == null) {
         this.mUnmappedDimensionMapping = new ArrayList();
      }

      DimensionPK dimPk = (DimensionPK)ref.getPrimaryKey();
      int dimensionId = dimPk.getDimensionId();
      HierarchyCK hierCK = (HierarchyCK)hierRef.getPrimaryKey();
      HierarchyPK hierPK = hierCK.getHierarchyPK();
      int sourceHierId = hierPK.getHierarchyId();
      AmmDimensionMapping mapping = new AmmDimensionMapping(ref, dimensionId, hierRef, sourceHierId, 3);
      this.mUnmappedDimensionMapping.add(mapping);
      return mapping;
   }

   public AmmDimensionMapping addCalDimMapping(DimensionRef ref, DimensionRef sourceDimensionRef, HierarchyRef sourceHierarchyRef) throws ValidationException {
      if(this.mCalDimensionMapping == null) {
         this.mCalDimensionMapping = new ArrayList();
      }

      if(ref != null && ref.getType() == 3) {
         AmmDimensionMapping mapping = this.getAmmDimensionMapping(ref, sourceDimensionRef, sourceHierarchyRef);
         this.mCalDimensionMapping.add(mapping);
         return mapping;
      } else {
         throw new ValidationException("Cannot add mapping of this type to cal mapping");
      }
   }

   private AmmDimensionMapping getAmmDimensionMapping(DimensionRef ref, DimensionRef sourceDimensionRef, HierarchyRef sourceHierarchyRef) {
      boolean dimensionId = false;
      boolean sourceDimId = false;
      boolean sourceHierId = false;
      DimensionPK dimPk = (DimensionPK)ref.getPrimaryKey();
      int dimensionId1 = dimPk.getDimensionId();
      if(sourceDimensionRef == null) {
         return new AmmDimensionMapping(ref, dimensionId1, 2);
      } else {
         dimPk = (DimensionPK)sourceDimensionRef.getPrimaryKey();
         int sourceDimId1 = dimPk.getDimensionId();
         HierarchyCK hierCK = (HierarchyCK)sourceHierarchyRef.getPrimaryKey();
         HierarchyPK hierPK = hierCK.getHierarchyPK();
         int sourceHierId1 = hierPK.getHierarchyId();
         return new AmmDimensionMapping(ref, dimensionId1, sourceDimensionRef, sourceDimId1, sourceHierarchyRef, sourceHierId1);
      }
   }

   public List<AmmDimensionMapping> getMappedDimensionMapping() {
      return this.mMappedDimensionMapping == null?Collections.EMPTY_LIST:this.mMappedDimensionMapping;
   }

   public List<AmmDimensionMapping> getUnmappedDimensionMapping() {
      return this.mUnmappedDimensionMapping == null?Collections.EMPTY_LIST:this.mUnmappedDimensionMapping;
   }

   public List<AmmDimensionMapping> getCalDimensionMapping() {
      return this.mCalDimensionMapping == null?Collections.EMPTY_LIST:this.mCalDimensionMapping;
   }

   private void getDims() {
      this.mDims = new ArrayList();
      Iterator i$ = this.getMappedDimensionMapping().iterator();

      AmmDimensionMapping dim;
      while(i$.hasNext()) {
         dim = (AmmDimensionMapping)i$.next();
         this.mDims.add(dim);
      }

      i$ = this.getUnmappedDimensionMapping().iterator();

      while(i$.hasNext()) {
         dim = (AmmDimensionMapping)i$.next();
         this.mDims.add(dim);
      }

      i$ = this.getCalDimensionMapping().iterator();

      while(i$.hasNext()) {
         dim = (AmmDimensionMapping)i$.next();
         this.mDims.add(dim);
      }

      Collections.sort(this.mDims);
   }

   public int getMappingIndex() {
      return this.mDimIndex;
   }

   public AmmDimensionMapping getMapping(int index) {
      if(this.mDims == null) {
         this.getDims();
      }

      return (AmmDimensionMapping)this.mDims.get(index);
   }

   public AmmDimensionMapping getNextMapping() {
      if(this.mDims == null) {
         this.getDims();
         this.mDimIndex = 0;
      }

      return (AmmDimensionMapping)this.mDims.get(this.mDimIndex++);
   }

   public List<AmmDimensionMapping> getDimMappings() {
      if(this.mDims == null) {
         this.getDims();
      }

      return this.mDims;
   }

   public List<AmmFinanceCubeMapping> getFinanceCubeMappings() {
      return this.mFinanceCubeMappings;
   }

   public void setFinanceCubeMappings(List<AmmFinanceCubeMapping> financeCubeMappings) {
      this.mFinanceCubeMappings = financeCubeMappings;
   }

   public CalendarInfo getSourceInfo() {
      return this.mSourceInfo;
   }

   public void setSourceInfo(CalendarInfo sourceInfo) {
      this.mSourceInfo = sourceInfo;
   }
}
