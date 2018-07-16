// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.SecurityRange;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.SecurityRangeCK;
import com.cedar.cp.dto.dimension.SecurityRangePK;
import com.cedar.cp.dto.model.ModelRefImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SecurityRangeImpl implements SecurityRange, Serializable, Cloneable {

   private ModelRefImpl mModelRef;
   private List mRanges;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private int mVersionNum;
   private DimensionRef mDimensionRef;


   public SecurityRangeImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mRanges = new ArrayList();
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (SecurityRangePK)paramKey;
   }

   public void setPrimaryKey(SecurityRangeCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public DimensionRef getDimensionRef() {
      return this.mDimensionRef;
   }

   public void setDimensionRef(DimensionRef ref) {
      this.mDimensionRef = ref;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public List getRanges() {
      return this.mRanges;
   }

   public void setRanges(List ranges) {
      this.mRanges = ranges;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public void setModelRef(ModelRefImpl model) {
      this.mModelRef = model;
   }
}
