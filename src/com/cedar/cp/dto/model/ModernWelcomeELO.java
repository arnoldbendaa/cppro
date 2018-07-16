// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ModernWelcomeELO extends AbstractELO implements Serializable {

   private transient Integer mModelId;
   private transient String mModelVisId;
   private transient String mModelDescription;
   private transient Integer mBudgetCycleId;
   private transient String mBudgetCycleVisId;
   private transient String mBudgetCycleDescription;
   private transient Integer mStructureId;
   private transient Integer mStructureElementId;
   private transient String mVisId;
   private transient String mDescription;
   private transient Integer mDepth;
   private transient Boolean mLeaf;
   private transient Integer mState;
   private transient Timestamp mLastChanged;
   private transient Timestamp mPlannedEndDate;


   public ModernWelcomeELO() {
      super(new String[]{"ModelId", "ModelVisId", "ModelDescription", "BudgetCycleId", "BudgetCycleVisId", "BudgetCycleDescription", "StructureId", "SructureElementId", "VisId", "Description", "Depth", "Leaf", "State", "LastChanged", "PlannedEndDate"});
   }

   public void add(int modelId, String modelVisId, String modelDescription, int budgetCycleId, String budgetCycleVisId, String budgetCycleDescription, int structurId, int structureElementId, String visId, String description, int depth, boolean leaf, int state, Timestamp lastChanged, Timestamp plannedEndDate) {
      ArrayList l = new ArrayList();
      l.add(new Integer(modelId));
      l.add(modelVisId);
      l.add(modelDescription);
      l.add(new Integer(budgetCycleId));
      l.add(budgetCycleVisId);
      l.add(budgetCycleDescription);
      l.add(new Integer(structurId));
      l.add(new Integer(structureElementId));
      l.add(visId);
      l.add(description);
      l.add(new Integer(depth));
      l.add(new Boolean(leaf));
      l.add(new Integer(state));
      l.add(lastChanged);
      l.add(plannedEndDate);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.mCurrRowIndex = -1;
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mModelId = (Integer)l.get(index);
      this.mModelVisId = (String)l.get(var4++);
      this.mBudgetCycleId = (Integer)l.get(var4++);
      this.mBudgetCycleVisId = (String)l.get(var4++);
      this.mStructureId = (Integer)l.get(var4++);
      this.mStructureElementId = (Integer)l.get(var4++);
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mDepth = (Integer)l.get(var4++);
      this.mLeaf = (Boolean)l.get(var4++);
      this.mState = (Integer)l.get(var4++);
      this.mLastChanged = (Timestamp)l.get(var4++);
      this.mPlannedEndDate = (Timestamp)l.get(var4);
   }

   public Integer getModelId() {
      return this.mModelId;
   }

   public void setModelId(Integer modelId) {
      this.mModelId = modelId;
   }

   public String getModelVisId() {
      return this.mModelVisId;
   }

   public void setModelVisId(String modelVisId) {
      this.mModelVisId = modelVisId;
   }

   public Integer getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public void setBudgetCycleId(Integer budgetCycleId) {
      this.mBudgetCycleId = budgetCycleId;
   }

   public String getBudgetCycleVisId() {
      return this.mBudgetCycleVisId;
   }

   public void setBudgetCycleVisId(String budgetCycleVisId) {
      this.mBudgetCycleVisId = budgetCycleVisId;
   }

   public String getModelDescription() {
      return this.mModelDescription;
   }

   public void setModelDescription(String modelDescription) {
      this.mModelDescription = modelDescription;
   }

   public String getBudgetCycleDescription() {
      return this.mBudgetCycleDescription;
   }

   public void setBudgetCycleDescription(String budgetCycleDescription) {
      this.mBudgetCycleDescription = budgetCycleDescription;
   }

   public Integer getStructureId() {
      return this.mStructureId;
   }

   public Integer getStructureElementId() {
      return this.mStructureElementId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Integer getDepth() {
      return this.mDepth;
   }

   public Boolean getLeaf() {
      return this.mLeaf;
   }

   public Integer getState() {
      return this.mState;
   }

   public Timestamp getLastChanged() {
      return this.mLastChanged;
   }

   public Timestamp getPlannedEndDate() {
      return this.mPlannedEndDate;
   }

   public void setPlannedEndDate(Timestamp plannedEndDate) {
      this.mPlannedEndDate = plannedEndDate;
   }
}
