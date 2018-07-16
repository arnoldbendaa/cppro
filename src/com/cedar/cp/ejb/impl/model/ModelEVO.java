// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetUserPK;
import com.cedar.cp.dto.model.CellCalcPK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelDimensionRelPK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelPropertyPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.SecurityAccessDefPK;
import com.cedar.cp.dto.model.SecurityGroupPK;
import com.cedar.cp.dto.model.act.BudgetActivityPK;
import com.cedar.cp.dto.model.cc.CcDeploymentPK;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridPK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaPK;
import com.cedar.cp.dto.model.recharge.RechargePK;
import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
import com.cedar.cp.dto.model.virement.VirementCategoryPK;
import com.cedar.cp.dto.model.virement.VirementRequestPK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildPK;
import com.cedar.cp.ejb.impl.model.BudgetCycleEVO;
import com.cedar.cp.ejb.impl.model.BudgetUserEVO;
import com.cedar.cp.ejb.impl.model.CellCalcEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelDimensionRelEVO;
import com.cedar.cp.ejb.impl.model.ModelPropertyEVO;
import com.cedar.cp.ejb.impl.model.SecurityAccessDefEVO;
import com.cedar.cp.ejb.impl.model.SecurityGroupEVO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEVO;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ImportGridEVO;
import com.cedar.cp.ejb.impl.model.ra.ResponsibilityAreaEVO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementCategoryEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEVO;
import com.cedar.cp.ejb.impl.xmlform.rebuild.FormRebuildEVO;
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

public class ModelEVO implements Serializable {

   private transient ModelPK mPK;
   private int mModelId;
   private String mVisId;
   private String mDescription;
   private int mAccountId;
   private int mCalendarId;
   private int mBudgetHierarchyId;
   private boolean mCurrencyInUse;
   private int mCurrencyId;
   private boolean mLocked;
   private boolean mVirementEntryEnabled;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<FinanceCubePK, FinanceCubeEVO> mFinanceCubes;
   protected boolean mFinanceCubesAllItemsLoaded;
   private Map<ModelDimensionRelPK, ModelDimensionRelEVO> mModelDimensionRels;
   protected boolean mModelDimensionRelsAllItemsLoaded;
   private Map<ModelPropertyPK, ModelPropertyEVO> mModelProperties;
   protected boolean mModelPropertiesAllItemsLoaded;
   private Map<BudgetCyclePK, BudgetCycleEVO> mBudgetCycles;
   protected boolean mBudgetCyclesAllItemsLoaded;
   private Map<BudgetUserPK, BudgetUserEVO> mBudgetUsers;
   protected boolean mBudgetUsersAllItemsLoaded;
   private Map<SecurityGroupPK, SecurityGroupEVO> mSecurityGroups;
   protected boolean mSecurityGroupsAllItemsLoaded;
   private Map<SecurityAccessDefPK, SecurityAccessDefEVO> mSecurityAccessDefs;
   protected boolean mSecurityAccessDefsAllItemsLoaded;
   private Map<CellCalcPK, CellCalcEVO> mCellCalculations;
   protected boolean mCellCalculationsAllItemsLoaded;
   private Map<VirementCategoryPK, VirementCategoryEVO> mVirementGroups;
   protected boolean mVirementGroupsAllItemsLoaded;
   private Map<RechargePK, RechargeEVO> mRecharge;
   protected boolean mRechargeAllItemsLoaded;
   private Map<BudgetActivityPK, BudgetActivityEVO> mBudgetActivities;
   protected boolean mBudgetActivitiesAllItemsLoaded;
   private Map<VirementRequestPK, VirementRequestEVO> mVirementRequests;
   protected boolean mVirementRequestsAllItemsLoaded;
   private Map<ResponsibilityAreaPK, ResponsibilityAreaEVO> mResponsibilityAreas;
   protected boolean mResponsibilityAreasAllItemsLoaded;
   private Map<WeightingProfilePK, WeightingProfileEVO> mUserDefinedWeightingProfiles;
   protected boolean mUserDefinedWeightingProfilesAllItemsLoaded;
   private Map<CcDeploymentPK, CcDeploymentEVO> mCellCalcDeployments;
   protected boolean mCellCalcDeploymentsAllItemsLoaded;
   private Map<FormRebuildPK, FormRebuildEVO> mFormRebuilds;
   protected boolean mFormRebuildsAllItemsLoaded;
   private Map<ImportGridPK, ImportGridEVO> mAssocImportGrids;
   protected boolean mAssocImportGridsAllItemsLoaded;
   private boolean mModified;


   public ModelEVO() {}

   public ModelEVO(int newModelId, String newVisId, String newDescription, int newAccountId, int newCalendarId, int newBudgetHierarchyId, boolean newCurrencyInUse, int newCurrencyId, boolean newLocked, boolean newVirementEntryEnabled, int newVersionNum, Collection newFinanceCubes, Collection newModelDimensionRels, Collection newModelProperties, Collection newBudgetCycles, Collection newBudgetUsers, Collection newSecurityGroups, Collection newSecurityAccessDefs, Collection newCellCalculations, Collection newVirementGroups, Collection newRecharge, Collection newBudgetActivities, Collection newVirementRequests, Collection newResponsibilityAreas, Collection newUserDefinedWeightingProfiles, Collection newCellCalcDeployments, Collection newFormRebuilds, Collection newAssocImportGrids) {
      this.mModelId = newModelId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mAccountId = newAccountId;
      this.mCalendarId = newCalendarId;
      this.mBudgetHierarchyId = newBudgetHierarchyId;
      this.mCurrencyInUse = newCurrencyInUse;
      this.mCurrencyId = newCurrencyId;
      this.mLocked = newLocked;
      this.mVirementEntryEnabled = newVirementEntryEnabled;
      this.mVersionNum = newVersionNum;
      this.setFinanceCubes(newFinanceCubes);
      this.setModelDimensionRels(newModelDimensionRels);
      this.setModelProperties(newModelProperties);
      this.setBudgetCycles(newBudgetCycles);
      this.setBudgetUsers(newBudgetUsers);
      this.setSecurityGroups(newSecurityGroups);
      this.setSecurityAccessDefs(newSecurityAccessDefs);
      this.setCellCalculations(newCellCalculations);
      this.setVirementGroups(newVirementGroups);
      this.setRecharge(newRecharge);
      this.setBudgetActivities(newBudgetActivities);
      this.setVirementRequests(newVirementRequests);
      this.setResponsibilityAreas(newResponsibilityAreas);
      this.setUserDefinedWeightingProfiles(newUserDefinedWeightingProfiles);
      this.setCellCalcDeployments(newCellCalcDeployments);
      this.setFormRebuilds(newFormRebuilds);
      this.setAssocImportGrids(newAssocImportGrids);
   }

   public void setFinanceCubes(Collection<FinanceCubeEVO> items) {
      if(items != null) {
         if(this.mFinanceCubes == null) {
            this.mFinanceCubes = new HashMap();
         } else {
            this.mFinanceCubes.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            FinanceCubeEVO child = (FinanceCubeEVO)i$.next();
            this.mFinanceCubes.put(child.getPK(), child);
         }
      } else {
         this.mFinanceCubes = null;
      }

   }

   public void setModelDimensionRels(Collection<ModelDimensionRelEVO> items) {
      if(items != null) {
         if(this.mModelDimensionRels == null) {
            this.mModelDimensionRels = new HashMap();
         } else {
            this.mModelDimensionRels.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ModelDimensionRelEVO child = (ModelDimensionRelEVO)i$.next();
            this.mModelDimensionRels.put(child.getPK(), child);
         }
      } else {
         this.mModelDimensionRels = null;
      }

   }

   public void setModelProperties(Collection<ModelPropertyEVO> items) {
      if(items != null) {
         if(this.mModelProperties == null) {
            this.mModelProperties = new HashMap();
         } else {
            this.mModelProperties.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ModelPropertyEVO child = (ModelPropertyEVO)i$.next();
            this.mModelProperties.put(child.getPK(), child);
         }
      } else {
         this.mModelProperties = null;
      }

   }

   public void setBudgetCycles(Collection<BudgetCycleEVO> items) {
      if(items != null) {
         if(this.mBudgetCycles == null) {
            this.mBudgetCycles = new HashMap();
         } else {
            this.mBudgetCycles.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            BudgetCycleEVO child = (BudgetCycleEVO)i$.next();
            this.mBudgetCycles.put(child.getPK(), child);
         }
      } else {
         this.mBudgetCycles = null;
      }

   }

   public void setBudgetUsers(Collection<BudgetUserEVO> items) {
      if(items != null) {
         if(this.mBudgetUsers == null) {
            this.mBudgetUsers = new HashMap();
         } else {
            this.mBudgetUsers.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            BudgetUserEVO child = (BudgetUserEVO)i$.next();
            this.mBudgetUsers.put(child.getPK(), child);
         }
      } else {
         this.mBudgetUsers = null;
      }

   }

   public void setSecurityGroups(Collection<SecurityGroupEVO> items) {
      if(items != null) {
         if(this.mSecurityGroups == null) {
            this.mSecurityGroups = new HashMap();
         } else {
            this.mSecurityGroups.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            SecurityGroupEVO child = (SecurityGroupEVO)i$.next();
            this.mSecurityGroups.put(child.getPK(), child);
         }
      } else {
         this.mSecurityGroups = null;
      }

   }

   public void setSecurityAccessDefs(Collection<SecurityAccessDefEVO> items) {
      if(items != null) {
         if(this.mSecurityAccessDefs == null) {
            this.mSecurityAccessDefs = new HashMap();
         } else {
            this.mSecurityAccessDefs.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            SecurityAccessDefEVO child = (SecurityAccessDefEVO)i$.next();
            this.mSecurityAccessDefs.put(child.getPK(), child);
         }
      } else {
         this.mSecurityAccessDefs = null;
      }

   }

   public void setCellCalculations(Collection<CellCalcEVO> items) {
      if(items != null) {
         if(this.mCellCalculations == null) {
            this.mCellCalculations = new HashMap();
         } else {
            this.mCellCalculations.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CellCalcEVO child = (CellCalcEVO)i$.next();
            this.mCellCalculations.put(child.getPK(), child);
         }
      } else {
         this.mCellCalculations = null;
      }

   }

   public void setVirementGroups(Collection<VirementCategoryEVO> items) {
      if(items != null) {
         if(this.mVirementGroups == null) {
            this.mVirementGroups = new HashMap();
         } else {
            this.mVirementGroups.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            VirementCategoryEVO child = (VirementCategoryEVO)i$.next();
            this.mVirementGroups.put(child.getPK(), child);
         }
      } else {
         this.mVirementGroups = null;
      }

   }

   public void setRecharge(Collection<RechargeEVO> items) {
      if(items != null) {
         if(this.mRecharge == null) {
            this.mRecharge = new HashMap();
         } else {
            this.mRecharge.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            RechargeEVO child = (RechargeEVO)i$.next();
            this.mRecharge.put(child.getPK(), child);
         }
      } else {
         this.mRecharge = null;
      }

   }

   public void setBudgetActivities(Collection<BudgetActivityEVO> items) {
      if(items != null) {
         if(this.mBudgetActivities == null) {
            this.mBudgetActivities = new HashMap();
         } else {
            this.mBudgetActivities.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            BudgetActivityEVO child = (BudgetActivityEVO)i$.next();
            this.mBudgetActivities.put(child.getPK(), child);
         }
      } else {
         this.mBudgetActivities = null;
      }

   }

   public void setVirementRequests(Collection<VirementRequestEVO> items) {
      if(items != null) {
         if(this.mVirementRequests == null) {
            this.mVirementRequests = new HashMap();
         } else {
            this.mVirementRequests.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            VirementRequestEVO child = (VirementRequestEVO)i$.next();
            this.mVirementRequests.put(child.getPK(), child);
         }
      } else {
         this.mVirementRequests = null;
      }

   }

   public void setResponsibilityAreas(Collection<ResponsibilityAreaEVO> items) {
      if(items != null) {
         if(this.mResponsibilityAreas == null) {
            this.mResponsibilityAreas = new HashMap();
         } else {
            this.mResponsibilityAreas.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ResponsibilityAreaEVO child = (ResponsibilityAreaEVO)i$.next();
            this.mResponsibilityAreas.put(child.getPK(), child);
         }
      } else {
         this.mResponsibilityAreas = null;
      }

   }

   public void setUserDefinedWeightingProfiles(Collection<WeightingProfileEVO> items) {
      if(items != null) {
         if(this.mUserDefinedWeightingProfiles == null) {
            this.mUserDefinedWeightingProfiles = new HashMap();
         } else {
            this.mUserDefinedWeightingProfiles.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            WeightingProfileEVO child = (WeightingProfileEVO)i$.next();
            this.mUserDefinedWeightingProfiles.put(child.getPK(), child);
         }
      } else {
         this.mUserDefinedWeightingProfiles = null;
      }

   }

   public void setCellCalcDeployments(Collection<CcDeploymentEVO> items) {
      if(items != null) {
         if(this.mCellCalcDeployments == null) {
            this.mCellCalcDeployments = new HashMap();
         } else {
            this.mCellCalcDeployments.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CcDeploymentEVO child = (CcDeploymentEVO)i$.next();
            this.mCellCalcDeployments.put(child.getPK(), child);
         }
      } else {
         this.mCellCalcDeployments = null;
      }

   }

   public void setFormRebuilds(Collection<FormRebuildEVO> items) {
      if(items != null) {
         if(this.mFormRebuilds == null) {
            this.mFormRebuilds = new HashMap();
         } else {
            this.mFormRebuilds.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            FormRebuildEVO child = (FormRebuildEVO)i$.next();
            this.mFormRebuilds.put(child.getPK(), child);
         }
      } else {
         this.mFormRebuilds = null;
      }

   }

   public void setAssocImportGrids(Collection<ImportGridEVO> items) {
      if(items != null) {
         if(this.mAssocImportGrids == null) {
            this.mAssocImportGrids = new HashMap();
         } else {
            this.mAssocImportGrids.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ImportGridEVO child = (ImportGridEVO)i$.next();
            this.mAssocImportGrids.put(child.getPK(), child);
         }
      } else {
         this.mAssocImportGrids = null;
      }

   }

   public ModelPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ModelPK(this.mModelId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
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

   public int getAccountId() {
      return this.mAccountId;
   }

   public int getCalendarId() {
      return this.mCalendarId;
   }

   public int getBudgetHierarchyId() {
      return this.mBudgetHierarchyId;
   }

   public boolean getCurrencyInUse() {
      return this.mCurrencyInUse;
   }

   public int getCurrencyId() {
      return this.mCurrencyId;
   }

   public boolean getLocked() {
      return this.mLocked;
   }

   public boolean getVirementEntryEnabled() {
      return this.mVirementEntryEnabled;
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

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
         this.mPK = null;
      }
   }

   public void setAccountId(int newAccountId) {
      if(this.mAccountId != newAccountId) {
         this.mModified = true;
         this.mAccountId = newAccountId;
      }
   }

   public void setCalendarId(int newCalendarId) {
      if(this.mCalendarId != newCalendarId) {
         this.mModified = true;
         this.mCalendarId = newCalendarId;
      }
   }

   public void setBudgetHierarchyId(int newBudgetHierarchyId) {
      if(this.mBudgetHierarchyId != newBudgetHierarchyId) {
         this.mModified = true;
         this.mBudgetHierarchyId = newBudgetHierarchyId;
      }
   }

   public void setCurrencyInUse(boolean newCurrencyInUse) {
      if(this.mCurrencyInUse != newCurrencyInUse) {
         this.mModified = true;
         this.mCurrencyInUse = newCurrencyInUse;
      }
   }

   public void setCurrencyId(int newCurrencyId) {
      if(this.mCurrencyId != newCurrencyId) {
         this.mModified = true;
         this.mCurrencyId = newCurrencyId;
      }
   }

   public void setLocked(boolean newLocked) {
      if(this.mLocked != newLocked) {
         this.mModified = true;
         this.mLocked = newLocked;
      }
   }

   public void setVirementEntryEnabled(boolean newVirementEntryEnabled) {
      if(this.mVirementEntryEnabled != newVirementEntryEnabled) {
         this.mModified = true;
         this.mVirementEntryEnabled = newVirementEntryEnabled;
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

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ModelEVO newDetails) {
      this.setModelId(newDetails.getModelId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setAccountId(newDetails.getAccountId());
      this.setCalendarId(newDetails.getCalendarId());
      this.setBudgetHierarchyId(newDetails.getBudgetHierarchyId());
      this.setCurrencyInUse(newDetails.getCurrencyInUse());
      this.setCurrencyId(newDetails.getCurrencyId());
      this.setLocked(newDetails.getLocked());
      this.setVirementEntryEnabled(newDetails.getVirementEntryEnabled());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ModelEVO deepClone() {
      ModelEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ModelEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mModelId > 0) {
         newKey = true;
         this.mModelId = 0;
      } else if(this.mModelId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      Iterator iter;
      FinanceCubeEVO item;
      if(this.mFinanceCubes != null) {
         for(iter = (new ArrayList(this.mFinanceCubes.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (FinanceCubeEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      ModelDimensionRelEVO item1;
      if(this.mModelDimensionRels != null) {
         for(iter = (new ArrayList(this.mModelDimensionRels.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (ModelDimensionRelEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

      ModelPropertyEVO item2;
      if(this.mModelProperties != null) {
         for(iter = (new ArrayList(this.mModelProperties.values())).iterator(); iter.hasNext(); item2.prepareForInsert(this)) {
            item2 = (ModelPropertyEVO)iter.next();
            if(newKey) {
               item2.setInsertPending();
            }
         }
      }

      BudgetCycleEVO item3;
      if(this.mBudgetCycles != null) {
         for(iter = (new ArrayList(this.mBudgetCycles.values())).iterator(); iter.hasNext(); item3.prepareForInsert(this)) {
            item3 = (BudgetCycleEVO)iter.next();
            if(newKey) {
               item3.setInsertPending();
            }
         }
      }

      BudgetUserEVO item4;
      if(this.mBudgetUsers != null) {
         for(iter = (new ArrayList(this.mBudgetUsers.values())).iterator(); iter.hasNext(); item4.prepareForInsert(this)) {
            item4 = (BudgetUserEVO)iter.next();
            if(newKey) {
               item4.setInsertPending();
            }
         }
      }

      SecurityGroupEVO item5;
      if(this.mSecurityGroups != null) {
         for(iter = (new ArrayList(this.mSecurityGroups.values())).iterator(); iter.hasNext(); item5.prepareForInsert(this)) {
            item5 = (SecurityGroupEVO)iter.next();
            if(newKey) {
               item5.setInsertPending();
            }
         }
      }

      SecurityAccessDefEVO item7;
      if(this.mSecurityAccessDefs != null) {
         for(iter = (new ArrayList(this.mSecurityAccessDefs.values())).iterator(); iter.hasNext(); item7.prepareForInsert(this)) {
            item7 = (SecurityAccessDefEVO)iter.next();
            if(newKey) {
               item7.setInsertPending();
            }
         }
      }

      CellCalcEVO item6;
      if(this.mCellCalculations != null) {
         for(iter = (new ArrayList(this.mCellCalculations.values())).iterator(); iter.hasNext(); item6.prepareForInsert(this)) {
            item6 = (CellCalcEVO)iter.next();
            if(newKey) {
               item6.setInsertPending();
            }
         }
      }

      VirementCategoryEVO item9;
      if(this.mVirementGroups != null) {
         for(iter = (new ArrayList(this.mVirementGroups.values())).iterator(); iter.hasNext(); item9.prepareForInsert(this)) {
            item9 = (VirementCategoryEVO)iter.next();
            if(newKey) {
               item9.setInsertPending();
            }
         }
      }

      RechargeEVO item8;
      if(this.mRecharge != null) {
         for(iter = (new ArrayList(this.mRecharge.values())).iterator(); iter.hasNext(); item8.prepareForInsert(this)) {
            item8 = (RechargeEVO)iter.next();
            if(newKey) {
               item8.setInsertPending();
            }
         }
      }

      BudgetActivityEVO item11;
      if(this.mBudgetActivities != null) {
         for(iter = (new ArrayList(this.mBudgetActivities.values())).iterator(); iter.hasNext(); item11.prepareForInsert(this)) {
            item11 = (BudgetActivityEVO)iter.next();
            if(newKey) {
               item11.setInsertPending();
            }
         }
      }

      VirementRequestEVO item10;
      if(this.mVirementRequests != null) {
         for(iter = (new ArrayList(this.mVirementRequests.values())).iterator(); iter.hasNext(); item10.prepareForInsert(this)) {
            item10 = (VirementRequestEVO)iter.next();
            if(newKey) {
               item10.setInsertPending();
            }
         }
      }

      ResponsibilityAreaEVO item13;
      if(this.mResponsibilityAreas != null) {
         for(iter = (new ArrayList(this.mResponsibilityAreas.values())).iterator(); iter.hasNext(); item13.prepareForInsert(this)) {
            item13 = (ResponsibilityAreaEVO)iter.next();
            if(newKey) {
               item13.setInsertPending();
            }
         }
      }

      WeightingProfileEVO item12;
      if(this.mUserDefinedWeightingProfiles != null) {
         for(iter = (new ArrayList(this.mUserDefinedWeightingProfiles.values())).iterator(); iter.hasNext(); item12.prepareForInsert(this)) {
            item12 = (WeightingProfileEVO)iter.next();
            if(newKey) {
               item12.setInsertPending();
            }
         }
      }

      CcDeploymentEVO item15;
      if(this.mCellCalcDeployments != null) {
         for(iter = (new ArrayList(this.mCellCalcDeployments.values())).iterator(); iter.hasNext(); item15.prepareForInsert(this)) {
            item15 = (CcDeploymentEVO)iter.next();
            if(newKey) {
               item15.setInsertPending();
            }
         }
      }

      FormRebuildEVO item14;
      if(this.mFormRebuilds != null) {
         for(iter = (new ArrayList(this.mFormRebuilds.values())).iterator(); iter.hasNext(); item14.prepareForInsert(this)) {
            item14 = (FormRebuildEVO)iter.next();
            if(newKey) {
               item14.setInsertPending();
            }
         }
      }

      ImportGridEVO item16;
      if(this.mAssocImportGrids != null) {
         for(iter = (new ArrayList(this.mAssocImportGrids.values())).iterator(); iter.hasNext(); item16.prepareForInsert(this)) {
            item16 = (ImportGridEVO)iter.next();
            if(newKey) {
               item16.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mModelId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      FinanceCubeEVO item;
      if(this.mFinanceCubes != null) {
         for(iter = this.mFinanceCubes.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (FinanceCubeEVO)iter.next();
         }
      }

      ModelDimensionRelEVO item1;
      if(this.mModelDimensionRels != null) {
         for(iter = this.mModelDimensionRels.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (ModelDimensionRelEVO)iter.next();
         }
      }

      ModelPropertyEVO item2;
      if(this.mModelProperties != null) {
         for(iter = this.mModelProperties.values().iterator(); iter.hasNext(); returnCount = item2.getInsertCount(returnCount)) {
            item2 = (ModelPropertyEVO)iter.next();
         }
      }

      BudgetCycleEVO item4;
      if(this.mBudgetCycles != null) {
         for(iter = this.mBudgetCycles.values().iterator(); iter.hasNext(); returnCount = item4.getInsertCount(returnCount)) {
            item4 = (BudgetCycleEVO)iter.next();
         }
      }

      BudgetUserEVO item3;
      if(this.mBudgetUsers != null) {
         for(iter = this.mBudgetUsers.values().iterator(); iter.hasNext(); returnCount = item3.getInsertCount(returnCount)) {
            item3 = (BudgetUserEVO)iter.next();
         }
      }

      SecurityGroupEVO item6;
      if(this.mSecurityGroups != null) {
         for(iter = this.mSecurityGroups.values().iterator(); iter.hasNext(); returnCount = item6.getInsertCount(returnCount)) {
            item6 = (SecurityGroupEVO)iter.next();
         }
      }

      SecurityAccessDefEVO item5;
      if(this.mSecurityAccessDefs != null) {
         for(iter = this.mSecurityAccessDefs.values().iterator(); iter.hasNext(); returnCount = item5.getInsertCount(returnCount)) {
            item5 = (SecurityAccessDefEVO)iter.next();
         }
      }

      CellCalcEVO item8;
      if(this.mCellCalculations != null) {
         for(iter = this.mCellCalculations.values().iterator(); iter.hasNext(); returnCount = item8.getInsertCount(returnCount)) {
            item8 = (CellCalcEVO)iter.next();
         }
      }

      VirementCategoryEVO item7;
      if(this.mVirementGroups != null) {
         for(iter = this.mVirementGroups.values().iterator(); iter.hasNext(); returnCount = item7.getInsertCount(returnCount)) {
            item7 = (VirementCategoryEVO)iter.next();
         }
      }

      RechargeEVO item10;
      if(this.mRecharge != null) {
         for(iter = this.mRecharge.values().iterator(); iter.hasNext(); returnCount = item10.getInsertCount(returnCount)) {
            item10 = (RechargeEVO)iter.next();
         }
      }

      BudgetActivityEVO item9;
      if(this.mBudgetActivities != null) {
         for(iter = this.mBudgetActivities.values().iterator(); iter.hasNext(); returnCount = item9.getInsertCount(returnCount)) {
            item9 = (BudgetActivityEVO)iter.next();
         }
      }

      VirementRequestEVO item12;
      if(this.mVirementRequests != null) {
         for(iter = this.mVirementRequests.values().iterator(); iter.hasNext(); returnCount = item12.getInsertCount(returnCount)) {
            item12 = (VirementRequestEVO)iter.next();
         }
      }

      ResponsibilityAreaEVO item11;
      if(this.mResponsibilityAreas != null) {
         for(iter = this.mResponsibilityAreas.values().iterator(); iter.hasNext(); returnCount = item11.getInsertCount(returnCount)) {
            item11 = (ResponsibilityAreaEVO)iter.next();
         }
      }

      WeightingProfileEVO item14;
      if(this.mUserDefinedWeightingProfiles != null) {
         for(iter = this.mUserDefinedWeightingProfiles.values().iterator(); iter.hasNext(); returnCount = item14.getInsertCount(returnCount)) {
            item14 = (WeightingProfileEVO)iter.next();
         }
      }

      CcDeploymentEVO item13;
      if(this.mCellCalcDeployments != null) {
         for(iter = this.mCellCalcDeployments.values().iterator(); iter.hasNext(); returnCount = item13.getInsertCount(returnCount)) {
            item13 = (CcDeploymentEVO)iter.next();
         }
      }

      FormRebuildEVO item16;
      if(this.mFormRebuilds != null) {
         for(iter = this.mFormRebuilds.values().iterator(); iter.hasNext(); returnCount = item16.getInsertCount(returnCount)) {
            item16 = (FormRebuildEVO)iter.next();
         }
      }

      ImportGridEVO item15;
      if(this.mAssocImportGrids != null) {
         for(iter = this.mAssocImportGrids.values().iterator(); iter.hasNext(); returnCount = item15.getInsertCount(returnCount)) {
            item15 = (ImportGridEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mModelId < 1) {
         this.mModelId = startKey;
         nextKey = startKey + 1;
      }

      Iterator iter;
      FinanceCubeEVO item;
      if(this.mFinanceCubes != null) {
         for(iter = (new ArrayList(this.mFinanceCubes.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (FinanceCubeEVO)iter.next();
            item.setModelId(this.mModelId);
         }
      }

      ModelDimensionRelEVO item1;
      if(this.mModelDimensionRels != null) {
         for(iter = (new ArrayList(this.mModelDimensionRels.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (ModelDimensionRelEVO)iter.next();
            this.changeKey(item1, this.mModelId, item1.getDimensionId());
         }
      }

      ModelPropertyEVO item2;
      if(this.mModelProperties != null) {
         for(iter = (new ArrayList(this.mModelProperties.values())).iterator(); iter.hasNext(); nextKey = item2.assignNextKey(this, nextKey)) {
            item2 = (ModelPropertyEVO)iter.next();
            this.changeKey(item2, this.mModelId, item2.getPropertyName());
         }
      }

      BudgetCycleEVO item4;
      if(this.mBudgetCycles != null) {
         for(iter = (new ArrayList(this.mBudgetCycles.values())).iterator(); iter.hasNext(); nextKey = item4.assignNextKey(this, nextKey)) {
            item4 = (BudgetCycleEVO)iter.next();
            item4.setModelId(this.mModelId);
         }
      }

      BudgetUserEVO item3;
      if(this.mBudgetUsers != null) {
         for(iter = (new ArrayList(this.mBudgetUsers.values())).iterator(); iter.hasNext(); nextKey = item3.assignNextKey(this, nextKey)) {
            item3 = (BudgetUserEVO)iter.next();
            this.changeKey(item3, this.mModelId, item3.getStructureElementId(), item3.getUserId());
         }
      }

      SecurityGroupEVO item6;
      if(this.mSecurityGroups != null) {
         for(iter = (new ArrayList(this.mSecurityGroups.values())).iterator(); iter.hasNext(); nextKey = item6.assignNextKey(this, nextKey)) {
            item6 = (SecurityGroupEVO)iter.next();
            item6.setModelId(this.mModelId);
         }
      }

      SecurityAccessDefEVO item5;
      if(this.mSecurityAccessDefs != null) {
         for(iter = (new ArrayList(this.mSecurityAccessDefs.values())).iterator(); iter.hasNext(); nextKey = item5.assignNextKey(this, nextKey)) {
            item5 = (SecurityAccessDefEVO)iter.next();
            item5.setModelId(this.mModelId);
         }
      }

      CellCalcEVO item8;
      if(this.mCellCalculations != null) {
         for(iter = (new ArrayList(this.mCellCalculations.values())).iterator(); iter.hasNext(); nextKey = item8.assignNextKey(this, nextKey)) {
            item8 = (CellCalcEVO)iter.next();
            item8.setModelId(this.mModelId);
         }
      }

      VirementCategoryEVO item7;
      if(this.mVirementGroups != null) {
         for(iter = (new ArrayList(this.mVirementGroups.values())).iterator(); iter.hasNext(); nextKey = item7.assignNextKey(this, nextKey)) {
            item7 = (VirementCategoryEVO)iter.next();
            item7.setModelId(this.mModelId);
         }
      }

      RechargeEVO item10;
      if(this.mRecharge != null) {
         for(iter = (new ArrayList(this.mRecharge.values())).iterator(); iter.hasNext(); nextKey = item10.assignNextKey(this, nextKey)) {
            item10 = (RechargeEVO)iter.next();
            item10.setModelId(this.mModelId);
         }
      }

      BudgetActivityEVO item9;
      if(this.mBudgetActivities != null) {
         for(iter = (new ArrayList(this.mBudgetActivities.values())).iterator(); iter.hasNext(); nextKey = item9.assignNextKey(this, nextKey)) {
            item9 = (BudgetActivityEVO)iter.next();
            item9.setModelId(this.mModelId);
         }
      }

      VirementRequestEVO item12;
      if(this.mVirementRequests != null) {
         for(iter = (new ArrayList(this.mVirementRequests.values())).iterator(); iter.hasNext(); nextKey = item12.assignNextKey(this, nextKey)) {
            item12 = (VirementRequestEVO)iter.next();
            item12.setModelId(this.mModelId);
         }
      }

      ResponsibilityAreaEVO item11;
      if(this.mResponsibilityAreas != null) {
         for(iter = (new ArrayList(this.mResponsibilityAreas.values())).iterator(); iter.hasNext(); nextKey = item11.assignNextKey(this, nextKey)) {
            item11 = (ResponsibilityAreaEVO)iter.next();
            item11.setModelId(this.mModelId);
         }
      }

      WeightingProfileEVO item14;
      if(this.mUserDefinedWeightingProfiles != null) {
         for(iter = (new ArrayList(this.mUserDefinedWeightingProfiles.values())).iterator(); iter.hasNext(); nextKey = item14.assignNextKey(this, nextKey)) {
            item14 = (WeightingProfileEVO)iter.next();
            item14.setModelId(this.mModelId);
         }
      }

      CcDeploymentEVO item13;
      if(this.mCellCalcDeployments != null) {
         for(iter = (new ArrayList(this.mCellCalcDeployments.values())).iterator(); iter.hasNext(); nextKey = item13.assignNextKey(this, nextKey)) {
            item13 = (CcDeploymentEVO)iter.next();
            item13.setModelId(this.mModelId);
         }
      }

      FormRebuildEVO item16;
      if(this.mFormRebuilds != null) {
         for(iter = (new ArrayList(this.mFormRebuilds.values())).iterator(); iter.hasNext(); nextKey = item16.assignNextKey(this, nextKey)) {
            item16 = (FormRebuildEVO)iter.next();
            item16.setModelId(this.mModelId);
         }
      }

      ImportGridEVO item15;
      if(this.mAssocImportGrids != null) {
         for(iter = (new ArrayList(this.mAssocImportGrids.values())).iterator(); iter.hasNext(); nextKey = item15.assignNextKey(this, nextKey)) {
            item15 = (ImportGridEVO)iter.next();
            this.changeKey(item15, this.mModelId, item15.getGridId());
         }
      }

      return nextKey;
   }

   public Collection<FinanceCubeEVO> getFinanceCubes() {
      return this.mFinanceCubes != null?this.mFinanceCubes.values():null;
   }

   public Map<FinanceCubePK, FinanceCubeEVO> getFinanceCubesMap() {
      return this.mFinanceCubes;
   }

   public void loadFinanceCubesItem(FinanceCubeEVO newItem) {
      if(this.mFinanceCubes == null) {
         this.mFinanceCubes = new HashMap();
      }

      this.mFinanceCubes.put(newItem.getPK(), newItem);
   }

   public void addFinanceCubesItem(FinanceCubeEVO newItem) {
      if(this.mFinanceCubes == null) {
         this.mFinanceCubes = new HashMap();
      }

      FinanceCubePK newPK = newItem.getPK();
      if(this.getFinanceCubesItem(newPK) != null) {
         throw new RuntimeException("addFinanceCubesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mFinanceCubes.put(newPK, newItem);
      }
   }

   public void changeFinanceCubesItem(FinanceCubeEVO changedItem) {
      if(this.mFinanceCubes == null) {
         throw new RuntimeException("changeFinanceCubesItem: no items in collection");
      } else {
         FinanceCubePK changedPK = changedItem.getPK();
         FinanceCubeEVO listItem = this.getFinanceCubesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeFinanceCubesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteFinanceCubesItem(FinanceCubePK removePK) {
      FinanceCubeEVO listItem = this.getFinanceCubesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeFinanceCubesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public FinanceCubeEVO getFinanceCubesItem(FinanceCubePK pk) {
      return (FinanceCubeEVO)this.mFinanceCubes.get(pk);
   }

   public FinanceCubeEVO getFinanceCubesItem() {
      if(this.mFinanceCubes.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mFinanceCubes.size());
      } else {
         Iterator iter = this.mFinanceCubes.values().iterator();
         return (FinanceCubeEVO)iter.next();
      }
   }

   public Collection<ModelDimensionRelEVO> getModelDimensionRels() {
      return this.mModelDimensionRels != null?this.mModelDimensionRels.values():null;
   }

   public Map<ModelDimensionRelPK, ModelDimensionRelEVO> getModelDimensionRelsMap() {
      return this.mModelDimensionRels;
   }

   public void loadModelDimensionRelsItem(ModelDimensionRelEVO newItem) {
      if(this.mModelDimensionRels == null) {
         this.mModelDimensionRels = new HashMap();
      }

      this.mModelDimensionRels.put(newItem.getPK(), newItem);
   }

   public void addModelDimensionRelsItem(ModelDimensionRelEVO newItem) {
      if(this.mModelDimensionRels == null) {
         this.mModelDimensionRels = new HashMap();
      }

      ModelDimensionRelPK newPK = newItem.getPK();
      if(this.getModelDimensionRelsItem(newPK) != null) {
         throw new RuntimeException("addModelDimensionRelsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mModelDimensionRels.put(newPK, newItem);
      }
   }

   public void changeModelDimensionRelsItem(ModelDimensionRelEVO changedItem) {
      if(this.mModelDimensionRels == null) {
         throw new RuntimeException("changeModelDimensionRelsItem: no items in collection");
      } else {
         ModelDimensionRelPK changedPK = changedItem.getPK();
         ModelDimensionRelEVO listItem = this.getModelDimensionRelsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeModelDimensionRelsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteModelDimensionRelsItem(ModelDimensionRelPK removePK) {
      ModelDimensionRelEVO listItem = this.getModelDimensionRelsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeModelDimensionRelsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ModelDimensionRelEVO getModelDimensionRelsItem(ModelDimensionRelPK pk) {
      return (ModelDimensionRelEVO)this.mModelDimensionRels.get(pk);
   }

   public ModelDimensionRelEVO getModelDimensionRelsItem() {
      if(this.mModelDimensionRels.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mModelDimensionRels.size());
      } else {
         Iterator iter = this.mModelDimensionRels.values().iterator();
         return (ModelDimensionRelEVO)iter.next();
      }
   }

   public Collection<ModelPropertyEVO> getModelProperties() {
      return this.mModelProperties != null?this.mModelProperties.values():null;
   }

   public Map<ModelPropertyPK, ModelPropertyEVO> getModelPropertiesMap() {
      return this.mModelProperties;
   }

   public void loadModelPropertiesItem(ModelPropertyEVO newItem) {
      if(this.mModelProperties == null) {
         this.mModelProperties = new HashMap();
      }

      this.mModelProperties.put(newItem.getPK(), newItem);
   }

   public void addModelPropertiesItem(ModelPropertyEVO newItem) {
      if(this.mModelProperties == null) {
         this.mModelProperties = new HashMap();
      }

      ModelPropertyPK newPK = newItem.getPK();
      if(this.getModelPropertiesItem(newPK) != null) {
         throw new RuntimeException("addModelPropertiesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mModelProperties.put(newPK, newItem);
      }
   }

   public void changeModelPropertiesItem(ModelPropertyEVO changedItem) {
      if(this.mModelProperties == null) {
         throw new RuntimeException("changeModelPropertiesItem: no items in collection");
      } else {
         ModelPropertyPK changedPK = changedItem.getPK();
         ModelPropertyEVO listItem = this.getModelPropertiesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeModelPropertiesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteModelPropertiesItem(ModelPropertyPK removePK) {
      ModelPropertyEVO listItem = this.getModelPropertiesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeModelPropertiesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ModelPropertyEVO getModelPropertiesItem(ModelPropertyPK pk) {
      return (ModelPropertyEVO)this.mModelProperties.get(pk);
   }

   public ModelPropertyEVO getModelPropertiesItem() {
      if(this.mModelProperties.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mModelProperties.size());
      } else {
         Iterator iter = this.mModelProperties.values().iterator();
         return (ModelPropertyEVO)iter.next();
      }
   }

   public Collection<BudgetCycleEVO> getBudgetCycles() {
      return this.mBudgetCycles != null?this.mBudgetCycles.values():null;
   }

   public Map<BudgetCyclePK, BudgetCycleEVO> getBudgetCyclesMap() {
      return this.mBudgetCycles;
   }

   public void loadBudgetCyclesItem(BudgetCycleEVO newItem) {
      if(this.mBudgetCycles == null) {
         this.mBudgetCycles = new HashMap();
      }

      this.mBudgetCycles.put(newItem.getPK(), newItem);
   }

   public void addBudgetCyclesItem(BudgetCycleEVO newItem) {
      if(this.mBudgetCycles == null) {
         this.mBudgetCycles = new HashMap();
      }

      BudgetCyclePK newPK = newItem.getPK();
      if(this.getBudgetCyclesItem(newPK) != null) {
         throw new RuntimeException("addBudgetCyclesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mBudgetCycles.put(newPK, newItem);
      }
   }

   public void changeBudgetCyclesItem(BudgetCycleEVO changedItem) {
      if(this.mBudgetCycles == null) {
         throw new RuntimeException("changeBudgetCyclesItem: no items in collection");
      } else {
         BudgetCyclePK changedPK = changedItem.getPK();
         BudgetCycleEVO listItem = this.getBudgetCyclesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeBudgetCyclesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteBudgetCyclesItem(BudgetCyclePK removePK) {
      BudgetCycleEVO listItem = this.getBudgetCyclesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeBudgetCyclesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public BudgetCycleEVO getBudgetCyclesItem(BudgetCyclePK pk) {
      return (BudgetCycleEVO)this.mBudgetCycles.get(pk);
   }

   public BudgetCycleEVO getBudgetCyclesItem() {
      if(this.mBudgetCycles.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mBudgetCycles.size());
      } else {
         Iterator iter = this.mBudgetCycles.values().iterator();
         return (BudgetCycleEVO)iter.next();
      }
   }

   public Collection<BudgetUserEVO> getBudgetUsers() {
      return this.mBudgetUsers != null?this.mBudgetUsers.values():null;
   }

   public Map<BudgetUserPK, BudgetUserEVO> getBudgetUsersMap() {
      return this.mBudgetUsers;
   }

   public void loadBudgetUsersItem(BudgetUserEVO newItem) {
      if(this.mBudgetUsers == null) {
         this.mBudgetUsers = new HashMap();
      }

      this.mBudgetUsers.put(newItem.getPK(), newItem);
   }

   public void addBudgetUsersItem(BudgetUserEVO newItem) {
      if(this.mBudgetUsers == null) {
         this.mBudgetUsers = new HashMap();
      }

      BudgetUserPK newPK = newItem.getPK();
      if(this.getBudgetUsersItem(newPK) != null) {
         throw new RuntimeException("addBudgetUsersItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mBudgetUsers.put(newPK, newItem);
      }
   }

   public void changeBudgetUsersItem(BudgetUserEVO changedItem) {
      if(this.mBudgetUsers == null) {
         throw new RuntimeException("changeBudgetUsersItem: no items in collection");
      } else {
         BudgetUserPK changedPK = changedItem.getPK();
         BudgetUserEVO listItem = this.getBudgetUsersItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeBudgetUsersItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteBudgetUsersItem(BudgetUserPK removePK) {
      BudgetUserEVO listItem = this.getBudgetUsersItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeBudgetUsersItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public BudgetUserEVO getBudgetUsersItem(BudgetUserPK pk) {
      return (BudgetUserEVO)this.mBudgetUsers.get(pk);
   }

   public BudgetUserEVO getBudgetUsersItem() {
      if(this.mBudgetUsers.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mBudgetUsers.size());
      } else {
         Iterator iter = this.mBudgetUsers.values().iterator();
         return (BudgetUserEVO)iter.next();
      }
   }

   public Collection<SecurityGroupEVO> getSecurityGroups() {
      return this.mSecurityGroups != null?this.mSecurityGroups.values():null;
   }

   public Map<SecurityGroupPK, SecurityGroupEVO> getSecurityGroupsMap() {
      return this.mSecurityGroups;
   }

   public void loadSecurityGroupsItem(SecurityGroupEVO newItem) {
      if(this.mSecurityGroups == null) {
         this.mSecurityGroups = new HashMap();
      }

      this.mSecurityGroups.put(newItem.getPK(), newItem);
   }

   public void addSecurityGroupsItem(SecurityGroupEVO newItem) {
      if(this.mSecurityGroups == null) {
         this.mSecurityGroups = new HashMap();
      }

      SecurityGroupPK newPK = newItem.getPK();
      if(this.getSecurityGroupsItem(newPK) != null) {
         throw new RuntimeException("addSecurityGroupsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mSecurityGroups.put(newPK, newItem);
      }
   }

   public void changeSecurityGroupsItem(SecurityGroupEVO changedItem) {
      if(this.mSecurityGroups == null) {
         throw new RuntimeException("changeSecurityGroupsItem: no items in collection");
      } else {
         SecurityGroupPK changedPK = changedItem.getPK();
         SecurityGroupEVO listItem = this.getSecurityGroupsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeSecurityGroupsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteSecurityGroupsItem(SecurityGroupPK removePK) {
      SecurityGroupEVO listItem = this.getSecurityGroupsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeSecurityGroupsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public SecurityGroupEVO getSecurityGroupsItem(SecurityGroupPK pk) {
      return (SecurityGroupEVO)this.mSecurityGroups.get(pk);
   }

   public SecurityGroupEVO getSecurityGroupsItem() {
      if(this.mSecurityGroups.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mSecurityGroups.size());
      } else {
         Iterator iter = this.mSecurityGroups.values().iterator();
         return (SecurityGroupEVO)iter.next();
      }
   }

   public Collection<SecurityAccessDefEVO> getSecurityAccessDefs() {
      return this.mSecurityAccessDefs != null?this.mSecurityAccessDefs.values():null;
   }

   public Map<SecurityAccessDefPK, SecurityAccessDefEVO> getSecurityAccessDefsMap() {
      return this.mSecurityAccessDefs;
   }

   public void loadSecurityAccessDefsItem(SecurityAccessDefEVO newItem) {
      if(this.mSecurityAccessDefs == null) {
         this.mSecurityAccessDefs = new HashMap();
      }

      this.mSecurityAccessDefs.put(newItem.getPK(), newItem);
   }

   public void addSecurityAccessDefsItem(SecurityAccessDefEVO newItem) {
      if(this.mSecurityAccessDefs == null) {
         this.mSecurityAccessDefs = new HashMap();
      }

      SecurityAccessDefPK newPK = newItem.getPK();
      if(this.getSecurityAccessDefsItem(newPK) != null) {
         throw new RuntimeException("addSecurityAccessDefsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mSecurityAccessDefs.put(newPK, newItem);
      }
   }

   public void changeSecurityAccessDefsItem(SecurityAccessDefEVO changedItem) {
      if(this.mSecurityAccessDefs == null) {
         throw new RuntimeException("changeSecurityAccessDefsItem: no items in collection");
      } else {
         SecurityAccessDefPK changedPK = changedItem.getPK();
         SecurityAccessDefEVO listItem = this.getSecurityAccessDefsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeSecurityAccessDefsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteSecurityAccessDefsItem(SecurityAccessDefPK removePK) {
      SecurityAccessDefEVO listItem = this.getSecurityAccessDefsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeSecurityAccessDefsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public SecurityAccessDefEVO getSecurityAccessDefsItem(SecurityAccessDefPK pk) {
      return (SecurityAccessDefEVO)this.mSecurityAccessDefs.get(pk);
   }

   public SecurityAccessDefEVO getSecurityAccessDefsItem() {
      if(this.mSecurityAccessDefs.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mSecurityAccessDefs.size());
      } else {
         Iterator iter = this.mSecurityAccessDefs.values().iterator();
         return (SecurityAccessDefEVO)iter.next();
      }
   }

   public Collection<CellCalcEVO> getCellCalculations() {
      return this.mCellCalculations != null?this.mCellCalculations.values():null;
   }

   public Map<CellCalcPK, CellCalcEVO> getCellCalculationsMap() {
      return this.mCellCalculations;
   }

   public void loadCellCalculationsItem(CellCalcEVO newItem) {
      if(this.mCellCalculations == null) {
         this.mCellCalculations = new HashMap();
      }

      this.mCellCalculations.put(newItem.getPK(), newItem);
   }

   public void addCellCalculationsItem(CellCalcEVO newItem) {
      if(this.mCellCalculations == null) {
         this.mCellCalculations = new HashMap();
      }

      CellCalcPK newPK = newItem.getPK();
      if(this.getCellCalculationsItem(newPK) != null) {
         throw new RuntimeException("addCellCalculationsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mCellCalculations.put(newPK, newItem);
      }
   }

   public void changeCellCalculationsItem(CellCalcEVO changedItem) {
      if(this.mCellCalculations == null) {
         throw new RuntimeException("changeCellCalculationsItem: no items in collection");
      } else {
         CellCalcPK changedPK = changedItem.getPK();
         CellCalcEVO listItem = this.getCellCalculationsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeCellCalculationsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteCellCalculationsItem(CellCalcPK removePK) {
      CellCalcEVO listItem = this.getCellCalculationsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeCellCalculationsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public CellCalcEVO getCellCalculationsItem(CellCalcPK pk) {
      return (CellCalcEVO)this.mCellCalculations.get(pk);
   }

   public CellCalcEVO getCellCalculationsItem() {
      if(this.mCellCalculations.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mCellCalculations.size());
      } else {
         Iterator iter = this.mCellCalculations.values().iterator();
         return (CellCalcEVO)iter.next();
      }
   }

   public Collection<VirementCategoryEVO> getVirementGroups() {
      return this.mVirementGroups != null?this.mVirementGroups.values():null;
   }

   public Map<VirementCategoryPK, VirementCategoryEVO> getVirementGroupsMap() {
      return this.mVirementGroups;
   }

   public void loadVirementGroupsItem(VirementCategoryEVO newItem) {
      if(this.mVirementGroups == null) {
         this.mVirementGroups = new HashMap();
      }

      this.mVirementGroups.put(newItem.getPK(), newItem);
   }

   public void addVirementGroupsItem(VirementCategoryEVO newItem) {
      if(this.mVirementGroups == null) {
         this.mVirementGroups = new HashMap();
      }

      VirementCategoryPK newPK = newItem.getPK();
      if(this.getVirementGroupsItem(newPK) != null) {
         throw new RuntimeException("addVirementGroupsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mVirementGroups.put(newPK, newItem);
      }
   }

   public void changeVirementGroupsItem(VirementCategoryEVO changedItem) {
      if(this.mVirementGroups == null) {
         throw new RuntimeException("changeVirementGroupsItem: no items in collection");
      } else {
         VirementCategoryPK changedPK = changedItem.getPK();
         VirementCategoryEVO listItem = this.getVirementGroupsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeVirementGroupsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteVirementGroupsItem(VirementCategoryPK removePK) {
      VirementCategoryEVO listItem = this.getVirementGroupsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeVirementGroupsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public VirementCategoryEVO getVirementGroupsItem(VirementCategoryPK pk) {
      return (VirementCategoryEVO)this.mVirementGroups.get(pk);
   }

   public VirementCategoryEVO getVirementGroupsItem() {
      if(this.mVirementGroups.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mVirementGroups.size());
      } else {
         Iterator iter = this.mVirementGroups.values().iterator();
         return (VirementCategoryEVO)iter.next();
      }
   }

   public Collection<RechargeEVO> getRecharge() {
      return this.mRecharge != null?this.mRecharge.values():null;
   }

   public Map<RechargePK, RechargeEVO> getRechargeMap() {
      return this.mRecharge;
   }

   public void loadRechargeItem(RechargeEVO newItem) {
      if(this.mRecharge == null) {
         this.mRecharge = new HashMap();
      }

      this.mRecharge.put(newItem.getPK(), newItem);
   }

   public void addRechargeItem(RechargeEVO newItem) {
      if(this.mRecharge == null) {
         this.mRecharge = new HashMap();
      }

      RechargePK newPK = newItem.getPK();
      if(this.getRechargeItem(newPK) != null) {
         throw new RuntimeException("addRechargeItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mRecharge.put(newPK, newItem);
      }
   }

   public void changeRechargeItem(RechargeEVO changedItem) {
      if(this.mRecharge == null) {
         throw new RuntimeException("changeRechargeItem: no items in collection");
      } else {
         RechargePK changedPK = changedItem.getPK();
         RechargeEVO listItem = this.getRechargeItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeRechargeItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteRechargeItem(RechargePK removePK) {
      RechargeEVO listItem = this.getRechargeItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeRechargeItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public RechargeEVO getRechargeItem(RechargePK pk) {
      return (RechargeEVO)this.mRecharge.get(pk);
   }

   public RechargeEVO getRechargeItem() {
      if(this.mRecharge.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mRecharge.size());
      } else {
         Iterator iter = this.mRecharge.values().iterator();
         return (RechargeEVO)iter.next();
      }
   }

   public Collection<BudgetActivityEVO> getBudgetActivities() {
      return this.mBudgetActivities != null?this.mBudgetActivities.values():null;
   }

   public Map<BudgetActivityPK, BudgetActivityEVO> getBudgetActivitiesMap() {
      return this.mBudgetActivities;
   }

   public void loadBudgetActivitiesItem(BudgetActivityEVO newItem) {
      if(this.mBudgetActivities == null) {
         this.mBudgetActivities = new HashMap();
      }

      this.mBudgetActivities.put(newItem.getPK(), newItem);
   }

   public void addBudgetActivitiesItem(BudgetActivityEVO newItem) {
      if(this.mBudgetActivities == null) {
         this.mBudgetActivities = new HashMap();
      }

      BudgetActivityPK newPK = newItem.getPK();
      if(this.getBudgetActivitiesItem(newPK) != null) {
         throw new RuntimeException("addBudgetActivitiesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mBudgetActivities.put(newPK, newItem);
      }
   }

   public void changeBudgetActivitiesItem(BudgetActivityEVO changedItem) {
      if(this.mBudgetActivities == null) {
         throw new RuntimeException("changeBudgetActivitiesItem: no items in collection");
      } else {
         BudgetActivityPK changedPK = changedItem.getPK();
         BudgetActivityEVO listItem = this.getBudgetActivitiesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeBudgetActivitiesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteBudgetActivitiesItem(BudgetActivityPK removePK) {
      BudgetActivityEVO listItem = this.getBudgetActivitiesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeBudgetActivitiesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public BudgetActivityEVO getBudgetActivitiesItem(BudgetActivityPK pk) {
      return (BudgetActivityEVO)this.mBudgetActivities.get(pk);
   }

   public BudgetActivityEVO getBudgetActivitiesItem() {
      if(this.mBudgetActivities.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mBudgetActivities.size());
      } else {
         Iterator iter = this.mBudgetActivities.values().iterator();
         return (BudgetActivityEVO)iter.next();
      }
   }

   public Collection<VirementRequestEVO> getVirementRequests() {
      return this.mVirementRequests != null?this.mVirementRequests.values():null;
   }

   public Map<VirementRequestPK, VirementRequestEVO> getVirementRequestsMap() {
      return this.mVirementRequests;
   }

   public void loadVirementRequestsItem(VirementRequestEVO newItem) {
      if(this.mVirementRequests == null) {
         this.mVirementRequests = new HashMap();
      }

      this.mVirementRequests.put(newItem.getPK(), newItem);
   }

   public void addVirementRequestsItem(VirementRequestEVO newItem) {
      if(this.mVirementRequests == null) {
         this.mVirementRequests = new HashMap();
      }

      VirementRequestPK newPK = newItem.getPK();
      if(this.getVirementRequestsItem(newPK) != null) {
         throw new RuntimeException("addVirementRequestsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mVirementRequests.put(newPK, newItem);
      }
   }

   public void changeVirementRequestsItem(VirementRequestEVO changedItem) {
      if(this.mVirementRequests == null) {
         throw new RuntimeException("changeVirementRequestsItem: no items in collection");
      } else {
         VirementRequestPK changedPK = changedItem.getPK();
         VirementRequestEVO listItem = this.getVirementRequestsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeVirementRequestsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteVirementRequestsItem(VirementRequestPK removePK) {
      VirementRequestEVO listItem = this.getVirementRequestsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeVirementRequestsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public VirementRequestEVO getVirementRequestsItem(VirementRequestPK pk) {
      return (VirementRequestEVO)this.mVirementRequests.get(pk);
   }

   public VirementRequestEVO getVirementRequestsItem() {
      if(this.mVirementRequests.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mVirementRequests.size());
      } else {
         Iterator iter = this.mVirementRequests.values().iterator();
         return (VirementRequestEVO)iter.next();
      }
   }

   public Collection<ResponsibilityAreaEVO> getResponsibilityAreas() {
      return this.mResponsibilityAreas != null?this.mResponsibilityAreas.values():null;
   }

   public Map<ResponsibilityAreaPK, ResponsibilityAreaEVO> getResponsibilityAreasMap() {
      return this.mResponsibilityAreas;
   }

   public void loadResponsibilityAreasItem(ResponsibilityAreaEVO newItem) {
      if(this.mResponsibilityAreas == null) {
         this.mResponsibilityAreas = new HashMap();
      }

      this.mResponsibilityAreas.put(newItem.getPK(), newItem);
   }

   public void addResponsibilityAreasItem(ResponsibilityAreaEVO newItem) {
      if(this.mResponsibilityAreas == null) {
         this.mResponsibilityAreas = new HashMap();
      }

      ResponsibilityAreaPK newPK = newItem.getPK();
      if(this.getResponsibilityAreasItem(newPK) != null) {
         throw new RuntimeException("addResponsibilityAreasItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mResponsibilityAreas.put(newPK, newItem);
      }
   }

   public void changeResponsibilityAreasItem(ResponsibilityAreaEVO changedItem) {
      if(this.mResponsibilityAreas == null) {
         throw new RuntimeException("changeResponsibilityAreasItem: no items in collection");
      } else {
         ResponsibilityAreaPK changedPK = changedItem.getPK();
         ResponsibilityAreaEVO listItem = this.getResponsibilityAreasItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeResponsibilityAreasItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteResponsibilityAreasItem(ResponsibilityAreaPK removePK) {
      ResponsibilityAreaEVO listItem = this.getResponsibilityAreasItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeResponsibilityAreasItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ResponsibilityAreaEVO getResponsibilityAreasItem(ResponsibilityAreaPK pk) {
      return (ResponsibilityAreaEVO)this.mResponsibilityAreas.get(pk);
   }

   public ResponsibilityAreaEVO getResponsibilityAreasItem() {
      if(this.mResponsibilityAreas.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mResponsibilityAreas.size());
      } else {
         Iterator iter = this.mResponsibilityAreas.values().iterator();
         return (ResponsibilityAreaEVO)iter.next();
      }
   }

   public Collection<WeightingProfileEVO> getUserDefinedWeightingProfiles() {
      return this.mUserDefinedWeightingProfiles != null?this.mUserDefinedWeightingProfiles.values():null;
   }

   public Map<WeightingProfilePK, WeightingProfileEVO> getUserDefinedWeightingProfilesMap() {
      return this.mUserDefinedWeightingProfiles;
   }

   public void loadUserDefinedWeightingProfilesItem(WeightingProfileEVO newItem) {
      if(this.mUserDefinedWeightingProfiles == null) {
         this.mUserDefinedWeightingProfiles = new HashMap();
      }

      this.mUserDefinedWeightingProfiles.put(newItem.getPK(), newItem);
   }

   public void addUserDefinedWeightingProfilesItem(WeightingProfileEVO newItem) {
      if(this.mUserDefinedWeightingProfiles == null) {
         this.mUserDefinedWeightingProfiles = new HashMap();
      }

      WeightingProfilePK newPK = newItem.getPK();
      if(this.getUserDefinedWeightingProfilesItem(newPK) != null) {
         throw new RuntimeException("addUserDefinedWeightingProfilesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mUserDefinedWeightingProfiles.put(newPK, newItem);
      }
   }

   public void changeUserDefinedWeightingProfilesItem(WeightingProfileEVO changedItem) {
      if(this.mUserDefinedWeightingProfiles == null) {
         throw new RuntimeException("changeUserDefinedWeightingProfilesItem: no items in collection");
      } else {
         WeightingProfilePK changedPK = changedItem.getPK();
         WeightingProfileEVO listItem = this.getUserDefinedWeightingProfilesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeUserDefinedWeightingProfilesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteUserDefinedWeightingProfilesItem(WeightingProfilePK removePK) {
      WeightingProfileEVO listItem = this.getUserDefinedWeightingProfilesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeUserDefinedWeightingProfilesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public WeightingProfileEVO getUserDefinedWeightingProfilesItem(WeightingProfilePK pk) {
      return (WeightingProfileEVO)this.mUserDefinedWeightingProfiles.get(pk);
   }

   public WeightingProfileEVO getUserDefinedWeightingProfilesItem() {
      if(this.mUserDefinedWeightingProfiles.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mUserDefinedWeightingProfiles.size());
      } else {
         Iterator iter = this.mUserDefinedWeightingProfiles.values().iterator();
         return (WeightingProfileEVO)iter.next();
      }
   }

   public Collection<CcDeploymentEVO> getCellCalcDeployments() {
      return this.mCellCalcDeployments != null?this.mCellCalcDeployments.values():null;
   }

   public Map<CcDeploymentPK, CcDeploymentEVO> getCellCalcDeploymentsMap() {
      return this.mCellCalcDeployments;
   }

   public void loadCellCalcDeploymentsItem(CcDeploymentEVO newItem) {
      if(this.mCellCalcDeployments == null) {
         this.mCellCalcDeployments = new HashMap();
      }

      this.mCellCalcDeployments.put(newItem.getPK(), newItem);
   }

   public void addCellCalcDeploymentsItem(CcDeploymentEVO newItem) {
      if(this.mCellCalcDeployments == null) {
         this.mCellCalcDeployments = new HashMap();
      }

      CcDeploymentPK newPK = newItem.getPK();
      if(this.getCellCalcDeploymentsItem(newPK) != null) {
         throw new RuntimeException("addCellCalcDeploymentsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mCellCalcDeployments.put(newPK, newItem);
      }
   }

   public void changeCellCalcDeploymentsItem(CcDeploymentEVO changedItem) {
      if(this.mCellCalcDeployments == null) {
         throw new RuntimeException("changeCellCalcDeploymentsItem: no items in collection");
      } else {
         CcDeploymentPK changedPK = changedItem.getPK();
         CcDeploymentEVO listItem = this.getCellCalcDeploymentsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeCellCalcDeploymentsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteCellCalcDeploymentsItem(CcDeploymentPK removePK) {
      CcDeploymentEVO listItem = this.getCellCalcDeploymentsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeCellCalcDeploymentsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public CcDeploymentEVO getCellCalcDeploymentsItem(CcDeploymentPK pk) {
      return (CcDeploymentEVO)this.mCellCalcDeployments.get(pk);
   }

   public CcDeploymentEVO getCellCalcDeploymentsItem() {
      if(this.mCellCalcDeployments.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mCellCalcDeployments.size());
      } else {
         Iterator iter = this.mCellCalcDeployments.values().iterator();
         return (CcDeploymentEVO)iter.next();
      }
   }

   public Collection<FormRebuildEVO> getFormRebuilds() {
      return this.mFormRebuilds != null?this.mFormRebuilds.values():null;
   }

   public Map<FormRebuildPK, FormRebuildEVO> getFormRebuildsMap() {
      return this.mFormRebuilds;
   }

   public void loadFormRebuildsItem(FormRebuildEVO newItem) {
      if(this.mFormRebuilds == null) {
         this.mFormRebuilds = new HashMap();
      }

      this.mFormRebuilds.put(newItem.getPK(), newItem);
   }

   public void addFormRebuildsItem(FormRebuildEVO newItem) {
      if(this.mFormRebuilds == null) {
         this.mFormRebuilds = new HashMap();
      }

      FormRebuildPK newPK = newItem.getPK();
      if(this.getFormRebuildsItem(newPK) != null) {
         throw new RuntimeException("addFormRebuildsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mFormRebuilds.put(newPK, newItem);
      }
   }

   public void changeFormRebuildsItem(FormRebuildEVO changedItem) {
      if(this.mFormRebuilds == null) {
         throw new RuntimeException("changeFormRebuildsItem: no items in collection");
      } else {
         FormRebuildPK changedPK = changedItem.getPK();
         FormRebuildEVO listItem = this.getFormRebuildsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeFormRebuildsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteFormRebuildsItem(FormRebuildPK removePK) {
      FormRebuildEVO listItem = this.getFormRebuildsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeFormRebuildsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public FormRebuildEVO getFormRebuildsItem(FormRebuildPK pk) {
      return (FormRebuildEVO)this.mFormRebuilds.get(pk);
   }

   public FormRebuildEVO getFormRebuildsItem() {
      if(this.mFormRebuilds.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mFormRebuilds.size());
      } else {
         Iterator iter = this.mFormRebuilds.values().iterator();
         return (FormRebuildEVO)iter.next();
      }
   }

   public Collection<ImportGridEVO> getAssocImportGrids() {
      return this.mAssocImportGrids != null?this.mAssocImportGrids.values():null;
   }

   public Map<ImportGridPK, ImportGridEVO> getAssocImportGridsMap() {
      return this.mAssocImportGrids;
   }

   public void loadAssocImportGridsItem(ImportGridEVO newItem) {
      if(this.mAssocImportGrids == null) {
         this.mAssocImportGrids = new HashMap();
      }

      this.mAssocImportGrids.put(newItem.getPK(), newItem);
   }

   public void addAssocImportGridsItem(ImportGridEVO newItem) {
      if(this.mAssocImportGrids == null) {
         this.mAssocImportGrids = new HashMap();
      }

      ImportGridPK newPK = newItem.getPK();
      if(this.getAssocImportGridsItem(newPK) != null) {
         throw new RuntimeException("addAssocImportGridsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mAssocImportGrids.put(newPK, newItem);
      }
   }

   public void changeAssocImportGridsItem(ImportGridEVO changedItem) {
      if(this.mAssocImportGrids == null) {
         throw new RuntimeException("changeAssocImportGridsItem: no items in collection");
      } else {
         ImportGridPK changedPK = changedItem.getPK();
         ImportGridEVO listItem = this.getAssocImportGridsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeAssocImportGridsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteAssocImportGridsItem(ImportGridPK removePK) {
      ImportGridEVO listItem = this.getAssocImportGridsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeAssocImportGridsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ImportGridEVO getAssocImportGridsItem(ImportGridPK pk) {
      return (ImportGridEVO)this.mAssocImportGrids.get(pk);
   }

   public ImportGridEVO getAssocImportGridsItem() {
      if(this.mAssocImportGrids.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mAssocImportGrids.size());
      } else {
         Iterator iter = this.mAssocImportGrids.values().iterator();
         return (ImportGridEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public ModelRef getEntityRef() {
      return new ModelRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mFinanceCubesAllItemsLoaded = true;
      Iterator i$;
      if(this.mFinanceCubes == null) {
         this.mFinanceCubes = new HashMap();
      } else {
         i$ = this.mFinanceCubes.values().iterator();

         while(i$.hasNext()) {
            FinanceCubeEVO child = (FinanceCubeEVO)i$.next();
            child.postCreateInit();
         }
      }

      this.mModelDimensionRelsAllItemsLoaded = true;
      if(this.mModelDimensionRels == null) {
         this.mModelDimensionRels = new HashMap();
      }

      this.mModelPropertiesAllItemsLoaded = true;
      if(this.mModelProperties == null) {
         this.mModelProperties = new HashMap();
      }

      this.mBudgetCyclesAllItemsLoaded = true;
      if(this.mBudgetCycles == null) {
         this.mBudgetCycles = new HashMap();
      } else {
         i$ = this.mBudgetCycles.values().iterator();

         while(i$.hasNext()) {
            BudgetCycleEVO child1 = (BudgetCycleEVO)i$.next();
            child1.postCreateInit();
         }
      }

      this.mBudgetUsersAllItemsLoaded = true;
      if(this.mBudgetUsers == null) {
         this.mBudgetUsers = new HashMap();
      }

      this.mSecurityGroupsAllItemsLoaded = true;
      if(this.mSecurityGroups == null) {
         this.mSecurityGroups = new HashMap();
      } else {
         i$ = this.mSecurityGroups.values().iterator();

         while(i$.hasNext()) {
            SecurityGroupEVO child2 = (SecurityGroupEVO)i$.next();
            child2.postCreateInit();
         }
      }

      this.mSecurityAccessDefsAllItemsLoaded = true;
      if(this.mSecurityAccessDefs == null) {
         this.mSecurityAccessDefs = new HashMap();
      } else {
         i$ = this.mSecurityAccessDefs.values().iterator();

         while(i$.hasNext()) {
            SecurityAccessDefEVO child3 = (SecurityAccessDefEVO)i$.next();
            child3.postCreateInit();
         }
      }

      this.mCellCalculationsAllItemsLoaded = true;
      if(this.mCellCalculations == null) {
         this.mCellCalculations = new HashMap();
      } else {
         i$ = this.mCellCalculations.values().iterator();

         while(i$.hasNext()) {
            CellCalcEVO child4 = (CellCalcEVO)i$.next();
            child4.postCreateInit();
         }
      }

      this.mVirementGroupsAllItemsLoaded = true;
      if(this.mVirementGroups == null) {
         this.mVirementGroups = new HashMap();
      } else {
         i$ = this.mVirementGroups.values().iterator();

         while(i$.hasNext()) {
            VirementCategoryEVO child5 = (VirementCategoryEVO)i$.next();
            child5.postCreateInit();
         }
      }

      this.mRechargeAllItemsLoaded = true;
      if(this.mRecharge == null) {
         this.mRecharge = new HashMap();
      } else {
         i$ = this.mRecharge.values().iterator();

         while(i$.hasNext()) {
            RechargeEVO child6 = (RechargeEVO)i$.next();
            child6.postCreateInit();
         }
      }

      this.mBudgetActivitiesAllItemsLoaded = true;
      if(this.mBudgetActivities == null) {
         this.mBudgetActivities = new HashMap();
      } else {
         i$ = this.mBudgetActivities.values().iterator();

         while(i$.hasNext()) {
            BudgetActivityEVO child7 = (BudgetActivityEVO)i$.next();
            child7.postCreateInit();
         }
      }

      this.mVirementRequestsAllItemsLoaded = true;
      if(this.mVirementRequests == null) {
         this.mVirementRequests = new HashMap();
      } else {
         i$ = this.mVirementRequests.values().iterator();

         while(i$.hasNext()) {
            VirementRequestEVO child8 = (VirementRequestEVO)i$.next();
            child8.postCreateInit();
         }
      }

      this.mResponsibilityAreasAllItemsLoaded = true;
      if(this.mResponsibilityAreas == null) {
         this.mResponsibilityAreas = new HashMap();
      }

      this.mUserDefinedWeightingProfilesAllItemsLoaded = true;
      if(this.mUserDefinedWeightingProfiles == null) {
         this.mUserDefinedWeightingProfiles = new HashMap();
      } else {
         i$ = this.mUserDefinedWeightingProfiles.values().iterator();

         while(i$.hasNext()) {
            WeightingProfileEVO child10 = (WeightingProfileEVO)i$.next();
            child10.postCreateInit();
         }
      }

      this.mCellCalcDeploymentsAllItemsLoaded = true;
      if(this.mCellCalcDeployments == null) {
         this.mCellCalcDeployments = new HashMap();
      } else {
         i$ = this.mCellCalcDeployments.values().iterator();

         while(i$.hasNext()) {
            CcDeploymentEVO child9 = (CcDeploymentEVO)i$.next();
            child9.postCreateInit();
         }
      }

      this.mFormRebuildsAllItemsLoaded = true;
      if(this.mFormRebuilds == null) {
         this.mFormRebuilds = new HashMap();
      }

      this.mAssocImportGridsAllItemsLoaded = true;
      if(this.mAssocImportGrids == null) {
         this.mAssocImportGrids = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("AccountId=");
      sb.append(String.valueOf(this.mAccountId));
      sb.append(' ');
      sb.append("CalendarId=");
      sb.append(String.valueOf(this.mCalendarId));
      sb.append(' ');
      sb.append("BudgetHierarchyId=");
      sb.append(String.valueOf(this.mBudgetHierarchyId));
      sb.append(' ');
      sb.append("CurrencyInUse=");
      sb.append(String.valueOf(this.mCurrencyInUse));
      sb.append(' ');
      sb.append("CurrencyId=");
      sb.append(String.valueOf(this.mCurrencyId));
      sb.append(' ');
      sb.append("Locked=");
      sb.append(String.valueOf(this.mLocked));
      sb.append(' ');
      sb.append("VirementEntryEnabled=");
      sb.append(String.valueOf(this.mVirementEntryEnabled));
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

      sb.append("Model: ");
      sb.append(this.toString());
      if(this.mFinanceCubesAllItemsLoaded || this.mFinanceCubes != null) {
         sb.delete(indent, sb.length());
         sb.append(" - FinanceCubes: allItemsLoaded=");
         sb.append(String.valueOf(this.mFinanceCubesAllItemsLoaded));
         sb.append(" items=");
         if(this.mFinanceCubes == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mFinanceCubes.size()));
         }
      }

      if(this.mModelDimensionRelsAllItemsLoaded || this.mModelDimensionRels != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ModelDimensionRels: allItemsLoaded=");
         sb.append(String.valueOf(this.mModelDimensionRelsAllItemsLoaded));
         sb.append(" items=");
         if(this.mModelDimensionRels == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mModelDimensionRels.size()));
         }
      }

      if(this.mModelPropertiesAllItemsLoaded || this.mModelProperties != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ModelProperties: allItemsLoaded=");
         sb.append(String.valueOf(this.mModelPropertiesAllItemsLoaded));
         sb.append(" items=");
         if(this.mModelProperties == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mModelProperties.size()));
         }
      }

      if(this.mBudgetCyclesAllItemsLoaded || this.mBudgetCycles != null) {
         sb.delete(indent, sb.length());
         sb.append(" - BudgetCycles: allItemsLoaded=");
         sb.append(String.valueOf(this.mBudgetCyclesAllItemsLoaded));
         sb.append(" items=");
         if(this.mBudgetCycles == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mBudgetCycles.size()));
         }
      }

      if(this.mBudgetUsersAllItemsLoaded || this.mBudgetUsers != null) {
         sb.delete(indent, sb.length());
         sb.append(" - BudgetUsers: allItemsLoaded=");
         sb.append(String.valueOf(this.mBudgetUsersAllItemsLoaded));
         sb.append(" items=");
         if(this.mBudgetUsers == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mBudgetUsers.size()));
         }
      }

      if(this.mSecurityGroupsAllItemsLoaded || this.mSecurityGroups != null) {
         sb.delete(indent, sb.length());
         sb.append(" - SecurityGroups: allItemsLoaded=");
         sb.append(String.valueOf(this.mSecurityGroupsAllItemsLoaded));
         sb.append(" items=");
         if(this.mSecurityGroups == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mSecurityGroups.size()));
         }
      }

      if(this.mSecurityAccessDefsAllItemsLoaded || this.mSecurityAccessDefs != null) {
         sb.delete(indent, sb.length());
         sb.append(" - SecurityAccessDefs: allItemsLoaded=");
         sb.append(String.valueOf(this.mSecurityAccessDefsAllItemsLoaded));
         sb.append(" items=");
         if(this.mSecurityAccessDefs == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mSecurityAccessDefs.size()));
         }
      }

      if(this.mCellCalculationsAllItemsLoaded || this.mCellCalculations != null) {
         sb.delete(indent, sb.length());
         sb.append(" - CellCalculations: allItemsLoaded=");
         sb.append(String.valueOf(this.mCellCalculationsAllItemsLoaded));
         sb.append(" items=");
         if(this.mCellCalculations == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mCellCalculations.size()));
         }
      }

      if(this.mVirementGroupsAllItemsLoaded || this.mVirementGroups != null) {
         sb.delete(indent, sb.length());
         sb.append(" - VirementGroups: allItemsLoaded=");
         sb.append(String.valueOf(this.mVirementGroupsAllItemsLoaded));
         sb.append(" items=");
         if(this.mVirementGroups == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mVirementGroups.size()));
         }
      }

      if(this.mRechargeAllItemsLoaded || this.mRecharge != null) {
         sb.delete(indent, sb.length());
         sb.append(" - Recharge: allItemsLoaded=");
         sb.append(String.valueOf(this.mRechargeAllItemsLoaded));
         sb.append(" items=");
         if(this.mRecharge == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mRecharge.size()));
         }
      }

      if(this.mBudgetActivitiesAllItemsLoaded || this.mBudgetActivities != null) {
         sb.delete(indent, sb.length());
         sb.append(" - BudgetActivities: allItemsLoaded=");
         sb.append(String.valueOf(this.mBudgetActivitiesAllItemsLoaded));
         sb.append(" items=");
         if(this.mBudgetActivities == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mBudgetActivities.size()));
         }
      }

      if(this.mVirementRequestsAllItemsLoaded || this.mVirementRequests != null) {
         sb.delete(indent, sb.length());
         sb.append(" - VirementRequests: allItemsLoaded=");
         sb.append(String.valueOf(this.mVirementRequestsAllItemsLoaded));
         sb.append(" items=");
         if(this.mVirementRequests == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mVirementRequests.size()));
         }
      }

      if(this.mResponsibilityAreasAllItemsLoaded || this.mResponsibilityAreas != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ResponsibilityAreas: allItemsLoaded=");
         sb.append(String.valueOf(this.mResponsibilityAreasAllItemsLoaded));
         sb.append(" items=");
         if(this.mResponsibilityAreas == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mResponsibilityAreas.size()));
         }
      }

      if(this.mUserDefinedWeightingProfilesAllItemsLoaded || this.mUserDefinedWeightingProfiles != null) {
         sb.delete(indent, sb.length());
         sb.append(" - UserDefinedWeightingProfiles: allItemsLoaded=");
         sb.append(String.valueOf(this.mUserDefinedWeightingProfilesAllItemsLoaded));
         sb.append(" items=");
         if(this.mUserDefinedWeightingProfiles == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mUserDefinedWeightingProfiles.size()));
         }
      }

      if(this.mCellCalcDeploymentsAllItemsLoaded || this.mCellCalcDeployments != null) {
         sb.delete(indent, sb.length());
         sb.append(" - CellCalcDeployments: allItemsLoaded=");
         sb.append(String.valueOf(this.mCellCalcDeploymentsAllItemsLoaded));
         sb.append(" items=");
         if(this.mCellCalcDeployments == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mCellCalcDeployments.size()));
         }
      }

      if(this.mFormRebuildsAllItemsLoaded || this.mFormRebuilds != null) {
         sb.delete(indent, sb.length());
         sb.append(" - FormRebuilds: allItemsLoaded=");
         sb.append(String.valueOf(this.mFormRebuildsAllItemsLoaded));
         sb.append(" items=");
         if(this.mFormRebuilds == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mFormRebuilds.size()));
         }
      }

      if(this.mAssocImportGridsAllItemsLoaded || this.mAssocImportGrids != null) {
         sb.delete(indent, sb.length());
         sb.append(" - AssocImportGrids: allItemsLoaded=");
         sb.append(String.valueOf(this.mAssocImportGridsAllItemsLoaded));
         sb.append(" items=");
         if(this.mAssocImportGrids == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mAssocImportGrids.size()));
         }
      }

      Iterator var5;
      if(this.mFinanceCubes != null) {
         var5 = this.mFinanceCubes.values().iterator();

         while(var5.hasNext()) {
            FinanceCubeEVO listItem = (FinanceCubeEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mModelDimensionRels != null) {
         var5 = this.mModelDimensionRels.values().iterator();

         while(var5.hasNext()) {
            ModelDimensionRelEVO var6 = (ModelDimensionRelEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      if(this.mModelProperties != null) {
         var5 = this.mModelProperties.values().iterator();

         while(var5.hasNext()) {
            ModelPropertyEVO var7 = (ModelPropertyEVO)var5.next();
            var7.print(indent + 4);
         }
      }

      if(this.mBudgetCycles != null) {
         var5 = this.mBudgetCycles.values().iterator();

         while(var5.hasNext()) {
            BudgetCycleEVO var9 = (BudgetCycleEVO)var5.next();
            var9.print(indent + 4);
         }
      }

      if(this.mBudgetUsers != null) {
         var5 = this.mBudgetUsers.values().iterator();

         while(var5.hasNext()) {
            BudgetUserEVO var8 = (BudgetUserEVO)var5.next();
            var8.print(indent + 4);
         }
      }

      if(this.mSecurityGroups != null) {
         var5 = this.mSecurityGroups.values().iterator();

         while(var5.hasNext()) {
            SecurityGroupEVO var11 = (SecurityGroupEVO)var5.next();
            var11.print(indent + 4);
         }
      }

      if(this.mSecurityAccessDefs != null) {
         var5 = this.mSecurityAccessDefs.values().iterator();

         while(var5.hasNext()) {
            SecurityAccessDefEVO var10 = (SecurityAccessDefEVO)var5.next();
            var10.print(indent + 4);
         }
      }

      if(this.mCellCalculations != null) {
         var5 = this.mCellCalculations.values().iterator();

         while(var5.hasNext()) {
            CellCalcEVO var13 = (CellCalcEVO)var5.next();
            var13.print(indent + 4);
         }
      }

      if(this.mVirementGroups != null) {
         var5 = this.mVirementGroups.values().iterator();

         while(var5.hasNext()) {
            VirementCategoryEVO var12 = (VirementCategoryEVO)var5.next();
            var12.print(indent + 4);
         }
      }

      if(this.mRecharge != null) {
         var5 = this.mRecharge.values().iterator();

         while(var5.hasNext()) {
            RechargeEVO var15 = (RechargeEVO)var5.next();
            var15.print(indent + 4);
         }
      }

      if(this.mBudgetActivities != null) {
         var5 = this.mBudgetActivities.values().iterator();

         while(var5.hasNext()) {
            BudgetActivityEVO var14 = (BudgetActivityEVO)var5.next();
            var14.print(indent + 4);
         }
      }

      if(this.mVirementRequests != null) {
         var5 = this.mVirementRequests.values().iterator();

         while(var5.hasNext()) {
            VirementRequestEVO var17 = (VirementRequestEVO)var5.next();
            var17.print(indent + 4);
         }
      }

      if(this.mResponsibilityAreas != null) {
         var5 = this.mResponsibilityAreas.values().iterator();

         while(var5.hasNext()) {
            ResponsibilityAreaEVO var16 = (ResponsibilityAreaEVO)var5.next();
            var16.print(indent + 4);
         }
      }

      if(this.mUserDefinedWeightingProfiles != null) {
         var5 = this.mUserDefinedWeightingProfiles.values().iterator();

         while(var5.hasNext()) {
            WeightingProfileEVO var19 = (WeightingProfileEVO)var5.next();
            var19.print(indent + 4);
         }
      }

      if(this.mCellCalcDeployments != null) {
         var5 = this.mCellCalcDeployments.values().iterator();

         while(var5.hasNext()) {
            CcDeploymentEVO var18 = (CcDeploymentEVO)var5.next();
            var18.print(indent + 4);
         }
      }

      if(this.mFormRebuilds != null) {
         var5 = this.mFormRebuilds.values().iterator();

         while(var5.hasNext()) {
            FormRebuildEVO var21 = (FormRebuildEVO)var5.next();
            var21.print(indent + 4);
         }
      }

      if(this.mAssocImportGrids != null) {
         var5 = this.mAssocImportGrids.values().iterator();

         while(var5.hasNext()) {
            ImportGridEVO var20 = (ImportGridEVO)var5.next();
            var20.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(FinanceCubeEVO child, int newFinanceCubeId) {
      if(this.getFinanceCubesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mFinanceCubes.remove(child.getPK());
         child.setFinanceCubeId(newFinanceCubeId);
         this.mFinanceCubes.put(child.getPK(), child);
      }
   }

   public void changeKey(ModelDimensionRelEVO child, int newModelId, int newDimensionId) {
      if(this.getModelDimensionRelsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mModelDimensionRels.remove(child.getPK());
         child.setModelId(newModelId);
         child.setDimensionId(newDimensionId);
         this.mModelDimensionRels.put(child.getPK(), child);
      }
   }

   public void changeKey(ModelPropertyEVO child, int newModelId, String newPropertyName) {
      if(this.getModelPropertiesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mModelProperties.remove(child.getPK());
         child.setModelId(newModelId);
         child.setPropertyName(newPropertyName);
         this.mModelProperties.put(child.getPK(), child);
      }
   }

   public void changeKey(BudgetCycleEVO child, int newBudgetCycleId) {
      if(this.getBudgetCyclesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mBudgetCycles.remove(child.getPK());
         child.setBudgetCycleId(newBudgetCycleId);
         this.mBudgetCycles.put(child.getPK(), child);
      }
   }

   public void changeKey(BudgetUserEVO child, int newModelId, int newStructureElementId, int newUserId) {
      if(this.getBudgetUsersItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mBudgetUsers.remove(child.getPK());
         child.setModelId(newModelId);
         child.setStructureElementId(newStructureElementId);
         child.setUserId(newUserId);
         this.mBudgetUsers.put(child.getPK(), child);
      }
   }

   public void changeKey(SecurityGroupEVO child, int newSecurityGroupId) {
      if(this.getSecurityGroupsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mSecurityGroups.remove(child.getPK());
         child.setSecurityGroupId(newSecurityGroupId);
         this.mSecurityGroups.put(child.getPK(), child);
      }
   }

   public void changeKey(SecurityAccessDefEVO child, int newSecurityAccessDefId) {
      if(this.getSecurityAccessDefsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mSecurityAccessDefs.remove(child.getPK());
         child.setSecurityAccessDefId(newSecurityAccessDefId);
         this.mSecurityAccessDefs.put(child.getPK(), child);
      }
   }

   public void changeKey(CellCalcEVO child, int newCellCalcId) {
      if(this.getCellCalculationsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mCellCalculations.remove(child.getPK());
         child.setCellCalcId(newCellCalcId);
         this.mCellCalculations.put(child.getPK(), child);
      }
   }

   public void changeKey(VirementCategoryEVO child, int newVirementCategoryId) {
      if(this.getVirementGroupsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mVirementGroups.remove(child.getPK());
         child.setVirementCategoryId(newVirementCategoryId);
         this.mVirementGroups.put(child.getPK(), child);
      }
   }

   public void changeKey(RechargeEVO child, int newRechargeId) {
      if(this.getRechargeItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mRecharge.remove(child.getPK());
         child.setRechargeId(newRechargeId);
         this.mRecharge.put(child.getPK(), child);
      }
   }

   public void changeKey(BudgetActivityEVO child, int newBudgetActivityId) {
      if(this.getBudgetActivitiesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mBudgetActivities.remove(child.getPK());
         child.setBudgetActivityId(newBudgetActivityId);
         this.mBudgetActivities.put(child.getPK(), child);
      }
   }

   public void changeKey(VirementRequestEVO child, int newRequestId) {
      if(this.getVirementRequestsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mVirementRequests.remove(child.getPK());
         child.setRequestId(newRequestId);
         this.mVirementRequests.put(child.getPK(), child);
      }
   }

   public void changeKey(ResponsibilityAreaEVO child, int newResponsibilityAreaId) {
      if(this.getResponsibilityAreasItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mResponsibilityAreas.remove(child.getPK());
         child.setResponsibilityAreaId(newResponsibilityAreaId);
         this.mResponsibilityAreas.put(child.getPK(), child);
      }
   }

   public void changeKey(WeightingProfileEVO child, int newProfileId) {
      if(this.getUserDefinedWeightingProfilesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mUserDefinedWeightingProfiles.remove(child.getPK());
         child.setProfileId(newProfileId);
         this.mUserDefinedWeightingProfiles.put(child.getPK(), child);
      }
   }

   public void changeKey(CcDeploymentEVO child, int newCcDeploymentId) {
      if(this.getCellCalcDeploymentsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mCellCalcDeployments.remove(child.getPK());
         child.setCcDeploymentId(newCcDeploymentId);
         this.mCellCalcDeployments.put(child.getPK(), child);
      }
   }

   public void changeKey(FormRebuildEVO child, int newFormRebuildId) {
      if(this.getFormRebuildsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mFormRebuilds.remove(child.getPK());
         child.setFormRebuildId(newFormRebuildId);
         this.mFormRebuilds.put(child.getPK(), child);
      }
   }

   public void changeKey(ImportGridEVO child, int newModelId, int newGridId) {
      if(this.getAssocImportGridsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mAssocImportGrids.remove(child.getPK());
         child.setModelId(newModelId);
         child.setGridId(newGridId);
         this.mAssocImportGrids.put(child.getPK(), child);
      }
   }

   public void setFinanceCubesAllItemsLoaded(boolean allItemsLoaded) {
      this.mFinanceCubesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isFinanceCubesAllItemsLoaded() {
      return this.mFinanceCubesAllItemsLoaded;
   }

   public void setModelDimensionRelsAllItemsLoaded(boolean allItemsLoaded) {
      this.mModelDimensionRelsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isModelDimensionRelsAllItemsLoaded() {
      return this.mModelDimensionRelsAllItemsLoaded;
   }

   public void setModelPropertiesAllItemsLoaded(boolean allItemsLoaded) {
      this.mModelPropertiesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isModelPropertiesAllItemsLoaded() {
      return this.mModelPropertiesAllItemsLoaded;
   }

   public void setBudgetCyclesAllItemsLoaded(boolean allItemsLoaded) {
      this.mBudgetCyclesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isBudgetCyclesAllItemsLoaded() {
      return this.mBudgetCyclesAllItemsLoaded;
   }

   public void setBudgetUsersAllItemsLoaded(boolean allItemsLoaded) {
      this.mBudgetUsersAllItemsLoaded = allItemsLoaded;
   }

   public boolean isBudgetUsersAllItemsLoaded() {
      return this.mBudgetUsersAllItemsLoaded;
   }

   public void setSecurityGroupsAllItemsLoaded(boolean allItemsLoaded) {
      this.mSecurityGroupsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isSecurityGroupsAllItemsLoaded() {
      return this.mSecurityGroupsAllItemsLoaded;
   }

   public void setSecurityAccessDefsAllItemsLoaded(boolean allItemsLoaded) {
      this.mSecurityAccessDefsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isSecurityAccessDefsAllItemsLoaded() {
      return this.mSecurityAccessDefsAllItemsLoaded;
   }

   public void setCellCalculationsAllItemsLoaded(boolean allItemsLoaded) {
      this.mCellCalculationsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isCellCalculationsAllItemsLoaded() {
      return this.mCellCalculationsAllItemsLoaded;
   }

   public void setVirementGroupsAllItemsLoaded(boolean allItemsLoaded) {
      this.mVirementGroupsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isVirementGroupsAllItemsLoaded() {
      return this.mVirementGroupsAllItemsLoaded;
   }

   public void setRechargeAllItemsLoaded(boolean allItemsLoaded) {
      this.mRechargeAllItemsLoaded = allItemsLoaded;
   }

   public boolean isRechargeAllItemsLoaded() {
      return this.mRechargeAllItemsLoaded;
   }

   public void setBudgetActivitiesAllItemsLoaded(boolean allItemsLoaded) {
      this.mBudgetActivitiesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isBudgetActivitiesAllItemsLoaded() {
      return this.mBudgetActivitiesAllItemsLoaded;
   }

   public void setVirementRequestsAllItemsLoaded(boolean allItemsLoaded) {
      this.mVirementRequestsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isVirementRequestsAllItemsLoaded() {
      return this.mVirementRequestsAllItemsLoaded;
   }

   public void setResponsibilityAreasAllItemsLoaded(boolean allItemsLoaded) {
      this.mResponsibilityAreasAllItemsLoaded = allItemsLoaded;
   }

   public boolean isResponsibilityAreasAllItemsLoaded() {
      return this.mResponsibilityAreasAllItemsLoaded;
   }

   public void setUserDefinedWeightingProfilesAllItemsLoaded(boolean allItemsLoaded) {
      this.mUserDefinedWeightingProfilesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isUserDefinedWeightingProfilesAllItemsLoaded() {
      return this.mUserDefinedWeightingProfilesAllItemsLoaded;
   }

   public void setCellCalcDeploymentsAllItemsLoaded(boolean allItemsLoaded) {
      this.mCellCalcDeploymentsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isCellCalcDeploymentsAllItemsLoaded() {
      return this.mCellCalcDeploymentsAllItemsLoaded;
   }

   public void setFormRebuildsAllItemsLoaded(boolean allItemsLoaded) {
      this.mFormRebuildsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isFormRebuildsAllItemsLoaded() {
      return this.mFormRebuildsAllItemsLoaded;
   }

   public void setAssocImportGridsAllItemsLoaded(boolean allItemsLoaded) {
      this.mAssocImportGridsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isAssocImportGridsAllItemsLoaded() {
      return this.mAssocImportGridsAllItemsLoaded;
   }
}
