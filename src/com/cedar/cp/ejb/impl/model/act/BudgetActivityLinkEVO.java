// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.act;

import com.cedar.cp.api.model.act.BudgetActivityLinkRef;
import com.cedar.cp.dto.model.act.BudgetActivityLinkCK;
import com.cedar.cp.dto.model.act.BudgetActivityLinkPK;
import com.cedar.cp.dto.model.act.BudgetActivityLinkRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityEVO;
import java.io.Serializable;

public class BudgetActivityLinkEVO implements Serializable {

   private transient BudgetActivityLinkPK mPK;
   private int mBudgetActivityId;
   private int mStructureElementId;
   private Integer mBudgetCycleId;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public BudgetActivityLinkEVO() {}

   public BudgetActivityLinkEVO(int newBudgetActivityId, int newStructureElementId, Integer newBudgetCycleId) {
      this.mBudgetActivityId = newBudgetActivityId;
      this.mStructureElementId = newStructureElementId;
      this.mBudgetCycleId = newBudgetCycleId;
   }

   public BudgetActivityLinkPK getPK() {
      if(this.mPK == null) {
         this.mPK = new BudgetActivityLinkPK(this.mBudgetActivityId, this.mStructureElementId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getBudgetActivityId() {
      return this.mBudgetActivityId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public Integer getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public void setBudgetActivityId(int newBudgetActivityId) {
      if(this.mBudgetActivityId != newBudgetActivityId) {
         this.mModified = true;
         this.mBudgetActivityId = newBudgetActivityId;
         this.mPK = null;
      }
   }

   public void setStructureElementId(int newStructureElementId) {
      if(this.mStructureElementId != newStructureElementId) {
         this.mModified = true;
         this.mStructureElementId = newStructureElementId;
         this.mPK = null;
      }
   }

   public void setBudgetCycleId(Integer newBudgetCycleId) {
      if(this.mBudgetCycleId != null && newBudgetCycleId == null || this.mBudgetCycleId == null && newBudgetCycleId != null || this.mBudgetCycleId != null && newBudgetCycleId != null && !this.mBudgetCycleId.equals(newBudgetCycleId)) {
         this.mBudgetCycleId = newBudgetCycleId;
         this.mModified = true;
      }

   }

   public void setDetails(BudgetActivityLinkEVO newDetails) {
      this.setBudgetActivityId(newDetails.getBudgetActivityId());
      this.setStructureElementId(newDetails.getStructureElementId());
      this.setBudgetCycleId(newDetails.getBudgetCycleId());
   }

   public BudgetActivityLinkEVO deepClone() {
      BudgetActivityLinkEVO cloned = new BudgetActivityLinkEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mBudgetActivityId = this.mBudgetActivityId;
      cloned.mStructureElementId = this.mStructureElementId;
      if(this.mBudgetCycleId != null) {
         cloned.mBudgetCycleId = Integer.valueOf(this.mBudgetCycleId.toString());
      }

      return cloned;
   }

   public void prepareForInsert(BudgetActivityEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(BudgetActivityEVO parent, int startKey) {
      return startKey;
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

   public BudgetActivityLinkRef getEntityRef(ModelEVO evoModel, BudgetActivityEVO evoBudgetActivity, String entityText) {
      return new BudgetActivityLinkRefImpl(new BudgetActivityLinkCK(evoModel.getPK(), evoBudgetActivity.getPK(), this.getPK()), entityText);
   }

   public BudgetActivityLinkCK getCK(ModelEVO evoModel, BudgetActivityEVO evoBudgetActivity) {
      return new BudgetActivityLinkCK(evoModel.getPK(), evoBudgetActivity.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("BudgetActivityId=");
      sb.append(String.valueOf(this.mBudgetActivityId));
      sb.append(' ');
      sb.append("StructureElementId=");
      sb.append(String.valueOf(this.mStructureElementId));
      sb.append(' ');
      sb.append("BudgetCycleId=");
      sb.append(String.valueOf(this.mBudgetCycleId));
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

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("BudgetActivityLink: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
