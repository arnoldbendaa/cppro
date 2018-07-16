// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.currency.CurrencyRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.Model;
import com.cedar.cp.dto.model.ModelPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelImpl implements Model, Serializable, Cloneable {

   private Map<String, String> mProperties = new HashMap();
   private List mDimensions;
   private boolean mVirementsInUse;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private boolean mCurrencyInUse;
   private boolean mLocked;
   private boolean mVirementEntryEnabled;
   private int mVersionNum;
   private CurrencyRef mCurrencyRef;
   private DimensionRef mAccountRef;
   private DimensionRef mCalendarRef;
   private HierarchyRef mBudgetHierarchyRef;


   public ModelImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mCurrencyInUse = false;
      this.mLocked = false;
      this.mVirementEntryEnabled = false;
      this.mDimensions = new ArrayList();
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (ModelPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean isCurrencyInUse() {
      return this.mCurrencyInUse;
   }

   public boolean isLocked() {
      return this.mLocked;
   }

   public boolean isVirementEntryEnabled() {
      return this.mVirementEntryEnabled;
   }

   public CurrencyRef getCurrencyRef() {
      return this.mCurrencyRef;
   }

   public DimensionRef getAccountRef() {
      return this.mAccountRef;
   }

   public DimensionRef getCalendarRef() {
      return this.mCalendarRef;
   }

   public HierarchyRef getBudgetHierarchyRef() {
      return this.mBudgetHierarchyRef;
   }

   public void setCurrencyRef(CurrencyRef ref) {
      this.mCurrencyRef = ref;
   }

   public void setAccountRef(DimensionRef ref) {
      this.mAccountRef = ref;
   }

   public void setCalendarRef(DimensionRef ref) {
      this.mCalendarRef = ref;
   }

   public void setBudgetHierarchyRef(HierarchyRef ref) {
      this.mBudgetHierarchyRef = ref;
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

   public void setCurrencyInUse(boolean paramCurrencyInUse) {
      this.mCurrencyInUse = paramCurrencyInUse;
   }

   public void setLocked(boolean paramLocked) {
      this.mLocked = paramLocked;
   }

   public void setVirementEntryEnabled(boolean paramVirementEntryEnabled) {
      this.mVirementEntryEnabled = paramVirementEntryEnabled;
   }

   public void addSelectedDimensionRef(EntityRef businessDimensionRef) {
      this.mDimensions.add(businessDimensionRef);
   }

   public void removeSelectedDimensionRef(EntityRef businessDimensionRef) {
      this.mDimensions.remove(businessDimensionRef);
   }

   public List getSelectedDimensionRefs() {
      return this.mDimensions;
   }

   public boolean isVirementsInUse() {
      return this.mVirementsInUse;
   }

   public void setVirementsInUse(boolean virementsInUse) {
      this.mVirementsInUse = virementsInUse;
   }

   public Map<String, String> getProperties() {
      return this.mProperties;
   }

   public void setProperties(Map<String, String> properties) {
      this.mProperties = properties;
   }
}
