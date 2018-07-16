// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPackagePK;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubeDataTypePK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.RollUpRulePK;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitPK;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaEVO;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaPackageEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDataTypeEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.RollUpRuleEVO;
import com.cedar.cp.ejb.impl.model.budgetlimit.BudgetLimitEVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FinanceCubeEVO implements Serializable {

   private transient FinanceCubePK mPK;
   private int mFinanceCubeId;
   private int mModelId;
   private String mVisId;
   private String mDescription;
   private Integer mLockedByTaskId;
   private boolean mHasData;
   private boolean mAudited;
   private boolean mCubeFormulaEnabled;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<FinanceCubeDataTypePK, FinanceCubeDataTypeEVO> mFinanceCubeDataTypes;
   protected boolean mFinanceCubeDataTypesAllItemsLoaded;
   private Map<BudgetLimitPK, BudgetLimitEVO> mBudgetLimits;
   protected boolean mBudgetLimitsAllItemsLoaded;
   private Map<RollUpRulePK, RollUpRuleEVO> mRollUpRules;
   protected boolean mRollUpRulesAllItemsLoaded;
   private Map<CubeFormulaPK, CubeFormulaEVO> mCubeFormula;
   protected boolean mCubeFormulaAllItemsLoaded;
   private Map<CubeFormulaPackagePK, CubeFormulaPackageEVO> mCubeFormulaPackages;
   protected boolean mCubeFormulaPackagesAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public FinanceCubeEVO() {}

   public FinanceCubeEVO(int newFinanceCubeId, int newModelId, String newVisId, String newDescription, Integer newLockedByTaskId, boolean newHasData, boolean newAudited, boolean newCubeFormulaEnabled, int newVersionNum, Collection newFinanceCubeDataTypes, Collection newBudgetLimits, Collection newRollUpRules, Collection newCubeFormula, Collection newCubeFormulaPackages) {
      this.mFinanceCubeId = newFinanceCubeId;
      this.mModelId = newModelId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mLockedByTaskId = newLockedByTaskId;
      this.mHasData = newHasData;
      this.mAudited = newAudited;
      this.mCubeFormulaEnabled = newCubeFormulaEnabled;
      this.mVersionNum = newVersionNum;
      this.setFinanceCubeDataTypes(newFinanceCubeDataTypes);
      this.setBudgetLimits(newBudgetLimits);
      this.setRollUpRules(newRollUpRules);
      this.setCubeFormula(newCubeFormula);
      this.setCubeFormulaPackages(newCubeFormulaPackages);
   }

   public void setFinanceCubeDataTypes(Collection<FinanceCubeDataTypeEVO> items) {
      if(items != null) {
         if(this.mFinanceCubeDataTypes == null) {
            this.mFinanceCubeDataTypes = new HashMap();
         } else {
            this.mFinanceCubeDataTypes.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            FinanceCubeDataTypeEVO child = (FinanceCubeDataTypeEVO)i$.next();
            this.mFinanceCubeDataTypes.put(child.getPK(), child);
         }
      } else {
         this.mFinanceCubeDataTypes = null;
      }

   }

   public void setBudgetLimits(Collection<BudgetLimitEVO> items) {
      if(items != null) {
         if(this.mBudgetLimits == null) {
            this.mBudgetLimits = new HashMap();
         } else {
            this.mBudgetLimits.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            BudgetLimitEVO child = (BudgetLimitEVO)i$.next();
            this.mBudgetLimits.put(child.getPK(), child);
         }
      } else {
         this.mBudgetLimits = null;
      }

   }

   public void setRollUpRules(Collection<RollUpRuleEVO> items) {
      if(items != null) {
         if(this.mRollUpRules == null) {
            this.mRollUpRules = new HashMap();
         } else {
            this.mRollUpRules.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            RollUpRuleEVO child = (RollUpRuleEVO)i$.next();
            this.mRollUpRules.put(child.getPK(), child);
         }
      } else {
         this.mRollUpRules = null;
      }

   }

   public void setCubeFormula(Collection<CubeFormulaEVO> items) {
      if(items != null) {
         if(this.mCubeFormula == null) {
            this.mCubeFormula = new HashMap();
         } else {
            this.mCubeFormula.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CubeFormulaEVO child = (CubeFormulaEVO)i$.next();
            this.mCubeFormula.put(child.getPK(), child);
         }
      } else {
         this.mCubeFormula = null;
      }

   }

   public void setCubeFormulaPackages(Collection<CubeFormulaPackageEVO> items) {
      if(items != null) {
         if(this.mCubeFormulaPackages == null) {
            this.mCubeFormulaPackages = new HashMap();
         } else {
            this.mCubeFormulaPackages.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CubeFormulaPackageEVO child = (CubeFormulaPackageEVO)i$.next();
            this.mCubeFormulaPackages.put(child.getPK(), child);
         }
      } else {
         this.mCubeFormulaPackages = null;
      }

   }

   public FinanceCubePK getPK() {
      if(this.mPK == null) {
         this.mPK = new FinanceCubePK(this.mFinanceCubeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Integer getLockedByTaskId() {
      return this.mLockedByTaskId;
   }

   public boolean getHasData() {
      return this.mHasData;
   }

   public boolean getAudited() {
      return this.mAudited;
   }

   public boolean getCubeFormulaEnabled() {
      return this.mCubeFormulaEnabled;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public int getUpdatedByUserId() {
      return this.mUpdatedByUserId;
   }

   public Timestamp getUpdatedTime() {
      return this.mUpdatedTime;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public void setFinanceCubeId(int newFinanceCubeId) {
      if(this.mFinanceCubeId != newFinanceCubeId) {
         this.mModified = true;
         this.mFinanceCubeId = newFinanceCubeId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setHasData(boolean newHasData) {
      if(this.mHasData != newHasData) {
         this.mModified = true;
         this.mHasData = newHasData;
      }
   }

   public void setAudited(boolean newAudited) {
      if(this.mAudited != newAudited) {
         this.mModified = true;
         this.mAudited = newAudited;
      }
   }

   public void setCubeFormulaEnabled(boolean newCubeFormulaEnabled) {
      if(this.mCubeFormulaEnabled != newCubeFormulaEnabled) {
         this.mModified = true;
         this.mCubeFormulaEnabled = newCubeFormulaEnabled;
      }
   }

   public void setVersionNum(int newVersionNum) {
      if(this.mVersionNum != newVersionNum) {
         this.mModified = true;
         this.mVersionNum = newVersionNum;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setVisId(String newVisId) {
      if(this.mVisId != null && newVisId == null || this.mVisId == null && newVisId != null || this.mVisId != null && newVisId != null && !this.mVisId.equals(newVisId)) {
         this.mVisId = newVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setLockedByTaskId(Integer newLockedByTaskId) {
      if(this.mLockedByTaskId != null && newLockedByTaskId == null || this.mLockedByTaskId == null && newLockedByTaskId != null || this.mLockedByTaskId != null && newLockedByTaskId != null && !this.mLockedByTaskId.equals(newLockedByTaskId)) {
         this.mLockedByTaskId = newLockedByTaskId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(FinanceCubeEVO newDetails) {
      this.setFinanceCubeId(newDetails.getFinanceCubeId());
      this.setModelId(newDetails.getModelId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setLockedByTaskId(newDetails.getLockedByTaskId());
      this.setHasData(newDetails.getHasData());
      this.setAudited(newDetails.getAudited());
      this.setCubeFormulaEnabled(newDetails.getCubeFormulaEnabled());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public FinanceCubeEVO deepClone() {
      FinanceCubeEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (FinanceCubeEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mFinanceCubeId > 0) {
         newKey = true;
         if(parent == null) {
            this.setFinanceCubeId(-this.mFinanceCubeId);
         } else {
            parent.changeKey(this, -this.mFinanceCubeId);
         }
      } else if(this.mFinanceCubeId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      Iterator iter;
      FinanceCubeDataTypeEVO item;
      if(this.mFinanceCubeDataTypes != null) {
         for(iter = (new ArrayList(this.mFinanceCubeDataTypes.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (FinanceCubeDataTypeEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      BudgetLimitEVO item1;
      if(this.mBudgetLimits != null) {
         for(iter = (new ArrayList(this.mBudgetLimits.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (BudgetLimitEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

      RollUpRuleEVO item2;
      if(this.mRollUpRules != null) {
         for(iter = (new ArrayList(this.mRollUpRules.values())).iterator(); iter.hasNext(); item2.prepareForInsert(this)) {
            item2 = (RollUpRuleEVO)iter.next();
            if(newKey) {
               item2.setInsertPending();
            }
         }
      }

      CubeFormulaEVO item4;
      if(this.mCubeFormula != null) {
         for(iter = (new ArrayList(this.mCubeFormula.values())).iterator(); iter.hasNext(); item4.prepareForInsert(this)) {
            item4 = (CubeFormulaEVO)iter.next();
            if(newKey) {
               item4.setInsertPending();
            }
         }
      }

      CubeFormulaPackageEVO item3;
      if(this.mCubeFormulaPackages != null) {
         for(iter = (new ArrayList(this.mCubeFormulaPackages.values())).iterator(); iter.hasNext(); item3.prepareForInsert(this)) {
            item3 = (CubeFormulaPackageEVO)iter.next();
            if(newKey) {
               item3.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mFinanceCubeId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      FinanceCubeDataTypeEVO item;
      if(this.mFinanceCubeDataTypes != null) {
         for(iter = this.mFinanceCubeDataTypes.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (FinanceCubeDataTypeEVO)iter.next();
         }
      }

      BudgetLimitEVO item1;
      if(this.mBudgetLimits != null) {
         for(iter = this.mBudgetLimits.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (BudgetLimitEVO)iter.next();
         }
      }

      RollUpRuleEVO item2;
      if(this.mRollUpRules != null) {
         for(iter = this.mRollUpRules.values().iterator(); iter.hasNext(); returnCount = item2.getInsertCount(returnCount)) {
            item2 = (RollUpRuleEVO)iter.next();
         }
      }

      CubeFormulaEVO item4;
      if(this.mCubeFormula != null) {
         for(iter = this.mCubeFormula.values().iterator(); iter.hasNext(); returnCount = item4.getInsertCount(returnCount)) {
            item4 = (CubeFormulaEVO)iter.next();
         }
      }

      CubeFormulaPackageEVO item3;
      if(this.mCubeFormulaPackages != null) {
         for(iter = this.mCubeFormulaPackages.values().iterator(); iter.hasNext(); returnCount = item3.getInsertCount(returnCount)) {
            item3 = (CubeFormulaPackageEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mFinanceCubeId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      Iterator iter;
      FinanceCubeDataTypeEVO item;
      if(this.mFinanceCubeDataTypes != null) {
         for(iter = (new ArrayList(this.mFinanceCubeDataTypes.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (FinanceCubeDataTypeEVO)iter.next();
            this.changeKey(item, this.mFinanceCubeId, item.getDataTypeId());
         }
      }

      BudgetLimitEVO item1;
      if(this.mBudgetLimits != null) {
         for(iter = (new ArrayList(this.mBudgetLimits.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (BudgetLimitEVO)iter.next();
            item1.setFinanceCubeId(this.mFinanceCubeId);
         }
      }

      RollUpRuleEVO item3;
      if(this.mRollUpRules != null) {
         for(iter = (new ArrayList(this.mRollUpRules.values())).iterator(); iter.hasNext(); nextKey = item3.assignNextKey(this, nextKey)) {
            item3 = (RollUpRuleEVO)iter.next();
            item3.setFinanceCubeId(this.mFinanceCubeId);
         }
      }

      CubeFormulaEVO item2;
      if(this.mCubeFormula != null) {
         for(iter = (new ArrayList(this.mCubeFormula.values())).iterator(); iter.hasNext(); nextKey = item2.assignNextKey(this, nextKey)) {
            item2 = (CubeFormulaEVO)iter.next();
            item2.setFinanceCubeId(this.mFinanceCubeId);
         }
      }

      CubeFormulaPackageEVO item4;
      if(this.mCubeFormulaPackages != null) {
         for(iter = (new ArrayList(this.mCubeFormulaPackages.values())).iterator(); iter.hasNext(); nextKey = item4.assignNextKey(this, nextKey)) {
            item4 = (CubeFormulaPackageEVO)iter.next();
            item4.setFinanceCubeId(this.mFinanceCubeId);
         }
      }

      return nextKey;
   }

   public Collection<FinanceCubeDataTypeEVO> getFinanceCubeDataTypes() {
      return this.mFinanceCubeDataTypes != null?this.mFinanceCubeDataTypes.values():null;
   }

   public Map<FinanceCubeDataTypePK, FinanceCubeDataTypeEVO> getFinanceCubeDataTypesMap() {
      return this.mFinanceCubeDataTypes;
   }

   public void loadFinanceCubeDataTypesItem(FinanceCubeDataTypeEVO newItem) {
      if(this.mFinanceCubeDataTypes == null) {
         this.mFinanceCubeDataTypes = new HashMap();
      }

      this.mFinanceCubeDataTypes.put(newItem.getPK(), newItem);
   }

   public void addFinanceCubeDataTypesItem(FinanceCubeDataTypeEVO newItem) {
      if(this.mFinanceCubeDataTypes == null) {
         this.mFinanceCubeDataTypes = new HashMap();
      }

      FinanceCubeDataTypePK newPK = newItem.getPK();
      if(this.getFinanceCubeDataTypesItem(newPK) != null) {
         throw new RuntimeException("addFinanceCubeDataTypesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mFinanceCubeDataTypes.put(newPK, newItem);
      }
   }

   public void changeFinanceCubeDataTypesItem(FinanceCubeDataTypeEVO changedItem) {
      if(this.mFinanceCubeDataTypes == null) {
         throw new RuntimeException("changeFinanceCubeDataTypesItem: no items in collection");
      } else {
         FinanceCubeDataTypePK changedPK = changedItem.getPK();
         FinanceCubeDataTypeEVO listItem = this.getFinanceCubeDataTypesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeFinanceCubeDataTypesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteFinanceCubeDataTypesItem(FinanceCubeDataTypePK removePK) {
      FinanceCubeDataTypeEVO listItem = this.getFinanceCubeDataTypesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeFinanceCubeDataTypesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public FinanceCubeDataTypeEVO getFinanceCubeDataTypesItem(FinanceCubeDataTypePK pk) {
      return (FinanceCubeDataTypeEVO)this.mFinanceCubeDataTypes.get(pk);
   }

   public FinanceCubeDataTypeEVO getFinanceCubeDataTypesItem() {
      if(this.mFinanceCubeDataTypes.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mFinanceCubeDataTypes.size());
      } else {
         Iterator iter = this.mFinanceCubeDataTypes.values().iterator();
         return (FinanceCubeDataTypeEVO)iter.next();
      }
   }

   public Collection<BudgetLimitEVO> getBudgetLimits() {
      return this.mBudgetLimits != null?this.mBudgetLimits.values():null;
   }

   public Map<BudgetLimitPK, BudgetLimitEVO> getBudgetLimitsMap() {
      return this.mBudgetLimits;
   }

   public void loadBudgetLimitsItem(BudgetLimitEVO newItem) {
      if(this.mBudgetLimits == null) {
         this.mBudgetLimits = new HashMap();
      }

      this.mBudgetLimits.put(newItem.getPK(), newItem);
   }

   public void addBudgetLimitsItem(BudgetLimitEVO newItem) {
      if(this.mBudgetLimits == null) {
         this.mBudgetLimits = new HashMap();
      }

      BudgetLimitPK newPK = newItem.getPK();
      if(this.getBudgetLimitsItem(newPK) != null) {
         throw new RuntimeException("addBudgetLimitsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mBudgetLimits.put(newPK, newItem);
      }
   }

   public void changeBudgetLimitsItem(BudgetLimitEVO changedItem) {
      if(this.mBudgetLimits == null) {
         throw new RuntimeException("changeBudgetLimitsItem: no items in collection");
      } else {
         BudgetLimitPK changedPK = changedItem.getPK();
         BudgetLimitEVO listItem = this.getBudgetLimitsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeBudgetLimitsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteBudgetLimitsItem(BudgetLimitPK removePK) {
      BudgetLimitEVO listItem = this.getBudgetLimitsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeBudgetLimitsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public BudgetLimitEVO getBudgetLimitsItem(BudgetLimitPK pk) {
      return (BudgetLimitEVO)this.mBudgetLimits.get(pk);
   }

   public BudgetLimitEVO getBudgetLimitsItem() {
      if(this.mBudgetLimits.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mBudgetLimits.size());
      } else {
         Iterator iter = this.mBudgetLimits.values().iterator();
         return (BudgetLimitEVO)iter.next();
      }
   }

   public Collection<RollUpRuleEVO> getRollUpRules() {
      return this.mRollUpRules != null?this.mRollUpRules.values():null;
   }

   public Map<RollUpRulePK, RollUpRuleEVO> getRollUpRulesMap() {
      return this.mRollUpRules;
   }

   public void loadRollUpRulesItem(RollUpRuleEVO newItem) {
      if(this.mRollUpRules == null) {
         this.mRollUpRules = new HashMap();
      }

      this.mRollUpRules.put(newItem.getPK(), newItem);
   }

   public void addRollUpRulesItem(RollUpRuleEVO newItem) {
      if(this.mRollUpRules == null) {
         this.mRollUpRules = new HashMap();
      }

      RollUpRulePK newPK = newItem.getPK();
      if(this.getRollUpRulesItem(newPK) != null) {
         throw new RuntimeException("addRollUpRulesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mRollUpRules.put(newPK, newItem);
      }
   }

   public void changeRollUpRulesItem(RollUpRuleEVO changedItem) {
      if(this.mRollUpRules == null) {
         throw new RuntimeException("changeRollUpRulesItem: no items in collection");
      } else {
         RollUpRulePK changedPK = changedItem.getPK();
         RollUpRuleEVO listItem = this.getRollUpRulesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeRollUpRulesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteRollUpRulesItem(RollUpRulePK removePK) {
      RollUpRuleEVO listItem = this.getRollUpRulesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeRollUpRulesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public RollUpRuleEVO getRollUpRulesItem(RollUpRulePK pk) {
      return (RollUpRuleEVO)this.mRollUpRules.get(pk);
   }

   public RollUpRuleEVO getRollUpRulesItem() {
      if(this.mRollUpRules.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mRollUpRules.size());
      } else {
         Iterator iter = this.mRollUpRules.values().iterator();
         return (RollUpRuleEVO)iter.next();
      }
   }

   public Collection<CubeFormulaEVO> getCubeFormula() {
      return this.mCubeFormula != null?this.mCubeFormula.values():null;
   }

   public Map<CubeFormulaPK, CubeFormulaEVO> getCubeFormulaMap() {
      return this.mCubeFormula;
   }

   public void loadCubeFormulaItem(CubeFormulaEVO newItem) {
      if(this.mCubeFormula == null) {
         this.mCubeFormula = new HashMap();
      }

      this.mCubeFormula.put(newItem.getPK(), newItem);
   }

   public void addCubeFormulaItem(CubeFormulaEVO newItem) {
      if(this.mCubeFormula == null) {
         this.mCubeFormula = new HashMap();
      }

      CubeFormulaPK newPK = newItem.getPK();
      if(this.getCubeFormulaItem(newPK) != null) {
         throw new RuntimeException("addCubeFormulaItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mCubeFormula.put(newPK, newItem);
      }
   }

   public void changeCubeFormulaItem(CubeFormulaEVO changedItem) {
      if(this.mCubeFormula == null) {
         throw new RuntimeException("changeCubeFormulaItem: no items in collection");
      } else {
         CubeFormulaPK changedPK = changedItem.getPK();
         CubeFormulaEVO listItem = this.getCubeFormulaItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeCubeFormulaItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteCubeFormulaItem(CubeFormulaPK removePK) {
      CubeFormulaEVO listItem = this.getCubeFormulaItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeCubeFormulaItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public CubeFormulaEVO getCubeFormulaItem(CubeFormulaPK pk) {
      return (CubeFormulaEVO)this.mCubeFormula.get(pk);
   }

   public CubeFormulaEVO getCubeFormulaItem() {
      if(this.mCubeFormula.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mCubeFormula.size());
      } else {
         Iterator iter = this.mCubeFormula.values().iterator();
         return (CubeFormulaEVO)iter.next();
      }
   }

   public Collection<CubeFormulaPackageEVO> getCubeFormulaPackages() {
      return this.mCubeFormulaPackages != null?this.mCubeFormulaPackages.values():null;
   }

   public Map<CubeFormulaPackagePK, CubeFormulaPackageEVO> getCubeFormulaPackagesMap() {
      return this.mCubeFormulaPackages;
   }

   public void loadCubeFormulaPackagesItem(CubeFormulaPackageEVO newItem) {
      if(this.mCubeFormulaPackages == null) {
         this.mCubeFormulaPackages = new HashMap();
      }

      this.mCubeFormulaPackages.put(newItem.getPK(), newItem);
   }

   public void addCubeFormulaPackagesItem(CubeFormulaPackageEVO newItem) {
      if(this.mCubeFormulaPackages == null) {
         this.mCubeFormulaPackages = new HashMap();
      }

      CubeFormulaPackagePK newPK = newItem.getPK();
      if(this.getCubeFormulaPackagesItem(newPK) != null) {
         throw new RuntimeException("addCubeFormulaPackagesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mCubeFormulaPackages.put(newPK, newItem);
      }
   }

   public void changeCubeFormulaPackagesItem(CubeFormulaPackageEVO changedItem) {
      if(this.mCubeFormulaPackages == null) {
         throw new RuntimeException("changeCubeFormulaPackagesItem: no items in collection");
      } else {
         CubeFormulaPackagePK changedPK = changedItem.getPK();
         CubeFormulaPackageEVO listItem = this.getCubeFormulaPackagesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeCubeFormulaPackagesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteCubeFormulaPackagesItem(CubeFormulaPackagePK removePK) {
      CubeFormulaPackageEVO listItem = this.getCubeFormulaPackagesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeCubeFormulaPackagesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public CubeFormulaPackageEVO getCubeFormulaPackagesItem(CubeFormulaPackagePK pk) {
      return (CubeFormulaPackageEVO)this.mCubeFormulaPackages.get(pk);
   }

   public CubeFormulaPackageEVO getCubeFormulaPackagesItem() {
      if(this.mCubeFormulaPackages.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mCubeFormulaPackages.size());
      } else {
         Iterator iter = this.mCubeFormulaPackages.values().iterator();
         return (CubeFormulaPackageEVO)iter.next();
      }
   }

   public void setInsertPending() {
      this.mInsertPending = true;
   }

   public boolean insertPending() {
      return this.mInsertPending;
   }

   public void setDeletePending() {
      this.mDeletePending = true;
   }

   public boolean deletePending() {
      return this.mDeletePending;
   }

   protected void reset() {
      this.mModified = false;
      this.mInsertPending = false;
   }

   public FinanceCubeRef getEntityRef(ModelEVO evoModel) {
      return new FinanceCubeRefImpl(new FinanceCubeCK(evoModel.getPK(), this.getPK()), this.mVisId);
   }

   public FinanceCubeCK getCK(ModelEVO evoModel) {
      return new FinanceCubeCK(evoModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mFinanceCubeDataTypesAllItemsLoaded = true;
      if(this.mFinanceCubeDataTypes == null) {
         this.mFinanceCubeDataTypes = new HashMap();
      }

      this.mBudgetLimitsAllItemsLoaded = true;
      if(this.mBudgetLimits == null) {
         this.mBudgetLimits = new HashMap();
      }

      this.mRollUpRulesAllItemsLoaded = true;
      if(this.mRollUpRules == null) {
         this.mRollUpRules = new HashMap();
      }

      this.mCubeFormulaAllItemsLoaded = true;
      if(this.mCubeFormula == null) {
         this.mCubeFormula = new HashMap();
      } else {
         Iterator i$ = this.mCubeFormula.values().iterator();

         while(i$.hasNext()) {
            CubeFormulaEVO child = (CubeFormulaEVO)i$.next();
            child.postCreateInit();
         }
      }

      this.mCubeFormulaPackagesAllItemsLoaded = true;
      if(this.mCubeFormulaPackages == null) {
         this.mCubeFormulaPackages = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("FinanceCubeId=");
      sb.append(String.valueOf(this.mFinanceCubeId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("LockedByTaskId=");
      sb.append(String.valueOf(this.mLockedByTaskId));
      sb.append(' ');
      sb.append("HasData=");
      sb.append(String.valueOf(this.mHasData));
      sb.append(' ');
      sb.append("Audited=");
      sb.append(String.valueOf(this.mAudited));
      sb.append(' ');
      sb.append("CubeFormulaEnabled=");
      sb.append(String.valueOf(this.mCubeFormulaEnabled));
      sb.append(' ');
      sb.append("VersionNum=");
      sb.append(String.valueOf(this.mVersionNum));
      sb.append(' ');
      sb.append("UpdatedByUserId=");
      sb.append(String.valueOf(this.mUpdatedByUserId));
      sb.append(' ');
      sb.append("UpdatedTime=");
      sb.append(String.valueOf(this.mUpdatedTime));
      sb.append(' ');
      sb.append("CreatedTime=");
      sb.append(String.valueOf(this.mCreatedTime));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      if(this.mInsertPending) {
         sb.append("insertPending ");
      }

      if(this.mDeletePending) {
         sb.append("deletePending ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i$ = 0; i$ < indent; ++i$) {
         sb.append(' ');
      }

      sb.append("FinanceCube: ");
      sb.append(this.toString());
      if(this.mFinanceCubeDataTypesAllItemsLoaded || this.mFinanceCubeDataTypes != null) {
         sb.delete(indent, sb.length());
         sb.append(" - FinanceCubeDataTypes: allItemsLoaded=");
         sb.append(String.valueOf(this.mFinanceCubeDataTypesAllItemsLoaded));
         sb.append(" items=");
         if(this.mFinanceCubeDataTypes == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mFinanceCubeDataTypes.size()));
         }
      }

      if(this.mBudgetLimitsAllItemsLoaded || this.mBudgetLimits != null) {
         sb.delete(indent, sb.length());
         sb.append(" - BudgetLimits: allItemsLoaded=");
         sb.append(String.valueOf(this.mBudgetLimitsAllItemsLoaded));
         sb.append(" items=");
         if(this.mBudgetLimits == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mBudgetLimits.size()));
         }
      }

      if(this.mRollUpRulesAllItemsLoaded || this.mRollUpRules != null) {
         sb.delete(indent, sb.length());
         sb.append(" - RollUpRules: allItemsLoaded=");
         sb.append(String.valueOf(this.mRollUpRulesAllItemsLoaded));
         sb.append(" items=");
         if(this.mRollUpRules == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mRollUpRules.size()));
         }
      }

      if(this.mCubeFormulaAllItemsLoaded || this.mCubeFormula != null) {
         sb.delete(indent, sb.length());
         sb.append(" - CubeFormula: allItemsLoaded=");
         sb.append(String.valueOf(this.mCubeFormulaAllItemsLoaded));
         sb.append(" items=");
         if(this.mCubeFormula == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mCubeFormula.size()));
         }
      }

      if(this.mCubeFormulaPackagesAllItemsLoaded || this.mCubeFormulaPackages != null) {
         sb.delete(indent, sb.length());
         sb.append(" - CubeFormulaPackages: allItemsLoaded=");
         sb.append(String.valueOf(this.mCubeFormulaPackagesAllItemsLoaded));
         sb.append(" items=");
         if(this.mCubeFormulaPackages == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mCubeFormulaPackages.size()));
         }
      }

      Iterator var5;
      if(this.mFinanceCubeDataTypes != null) {
         var5 = this.mFinanceCubeDataTypes.values().iterator();

         while(var5.hasNext()) {
            FinanceCubeDataTypeEVO listItem = (FinanceCubeDataTypeEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mBudgetLimits != null) {
         var5 = this.mBudgetLimits.values().iterator();

         while(var5.hasNext()) {
            BudgetLimitEVO var6 = (BudgetLimitEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      if(this.mRollUpRules != null) {
         var5 = this.mRollUpRules.values().iterator();

         while(var5.hasNext()) {
            RollUpRuleEVO var7 = (RollUpRuleEVO)var5.next();
            var7.print(indent + 4);
         }
      }

      if(this.mCubeFormula != null) {
         var5 = this.mCubeFormula.values().iterator();

         while(var5.hasNext()) {
            CubeFormulaEVO var9 = (CubeFormulaEVO)var5.next();
            var9.print(indent + 4);
         }
      }

      if(this.mCubeFormulaPackages != null) {
         var5 = this.mCubeFormulaPackages.values().iterator();

         while(var5.hasNext()) {
            CubeFormulaPackageEVO var8 = (CubeFormulaPackageEVO)var5.next();
            var8.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(FinanceCubeDataTypeEVO child, int newFinanceCubeId, short newDataTypeId) {
      if(this.getFinanceCubeDataTypesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mFinanceCubeDataTypes.remove(child.getPK());
         child.setFinanceCubeId(newFinanceCubeId);
         child.setDataTypeId(newDataTypeId);
         this.mFinanceCubeDataTypes.put(child.getPK(), child);
      }
   }

   public void changeKey(BudgetLimitEVO child, int newBudgetLimitId) {
      if(this.getBudgetLimitsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mBudgetLimits.remove(child.getPK());
         child.setBudgetLimitId(newBudgetLimitId);
         this.mBudgetLimits.put(child.getPK(), child);
      }
   }

   public void changeKey(RollUpRuleEVO child, int newRollUpRuleId) {
      if(this.getRollUpRulesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mRollUpRules.remove(child.getPK());
         child.setRollUpRuleId(newRollUpRuleId);
         this.mRollUpRules.put(child.getPK(), child);
      }
   }

   public void changeKey(CubeFormulaEVO child, int newCubeFormulaId) {
      if(this.getCubeFormulaItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mCubeFormula.remove(child.getPK());
         child.setCubeFormulaId(newCubeFormulaId);
         this.mCubeFormula.put(child.getPK(), child);
      }
   }

   public void changeKey(CubeFormulaPackageEVO child, int newCubeFormulaPackageId) {
      if(this.getCubeFormulaPackagesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mCubeFormulaPackages.remove(child.getPK());
         child.setCubeFormulaPackageId(newCubeFormulaPackageId);
         this.mCubeFormulaPackages.put(child.getPK(), child);
      }
   }

   public RollUpRuleEVO getRollUpRule(int dataTypeId, int dimensionId) {
      if(this.mRollUpRules != null) {
         Iterator i$ = this.mRollUpRules.values().iterator();

         while(i$.hasNext()) {
            RollUpRuleEVO rurEVO = (RollUpRuleEVO)i$.next();
            if(rurEVO.getDataTypeId() == dataTypeId && rurEVO.getDimensionId() == dimensionId) {
               return rurEVO;
            }
         }
      }

      return null;
   }

   public void setFinanceCubeDataTypesAllItemsLoaded(boolean allItemsLoaded) {
      this.mFinanceCubeDataTypesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isFinanceCubeDataTypesAllItemsLoaded() {
      return this.mFinanceCubeDataTypesAllItemsLoaded;
   }

   public void setBudgetLimitsAllItemsLoaded(boolean allItemsLoaded) {
      this.mBudgetLimitsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isBudgetLimitsAllItemsLoaded() {
      return this.mBudgetLimitsAllItemsLoaded;
   }

   public void setRollUpRulesAllItemsLoaded(boolean allItemsLoaded) {
      this.mRollUpRulesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isRollUpRulesAllItemsLoaded() {
      return this.mRollUpRulesAllItemsLoaded;
   }

   public void setCubeFormulaAllItemsLoaded(boolean allItemsLoaded) {
      this.mCubeFormulaAllItemsLoaded = allItemsLoaded;
   }

   public boolean isCubeFormulaAllItemsLoaded() {
      return this.mCubeFormulaAllItemsLoaded;
   }

   public void setCubeFormulaPackagesAllItemsLoaded(boolean allItemsLoaded) {
      this.mCubeFormulaPackagesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isCubeFormulaPackagesAllItemsLoaded() {
      return this.mCubeFormulaPackagesAllItemsLoaded;
   }
}
