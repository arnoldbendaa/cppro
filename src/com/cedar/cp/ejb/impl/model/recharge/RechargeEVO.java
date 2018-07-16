// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.recharge;

import com.cedar.cp.api.model.recharge.RechargeRef;
import com.cedar.cp.dto.model.recharge.RechargeCK;
import com.cedar.cp.dto.model.recharge.RechargeCellsPK;
import com.cedar.cp.dto.model.recharge.RechargePK;
import com.cedar.cp.dto.model.recharge.RechargeRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeCellsEVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RechargeEVO implements Serializable {

   private transient RechargePK mPK;
   private int mRechargeId;
   private int mModelId;
   private String mVisId;
   private String mDescription;
   private String mReason;
   private String mReference;
   private BigDecimal mAllocationPercentage;
   private boolean mManualRatios;
   private int mAllocationDataTypeId;
   private boolean mDiffAccount;
   private int mAccountStructureId;
   private int mAccountStructureElementId;
   private int mRatioType;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<RechargeCellsPK, RechargeCellsEVO> mRechargeCells;
   protected boolean mRechargeCellsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int PERIOD_MOVEMENT = 0;
   public static final int PERIOD_BALANCE = 1;


   public RechargeEVO() {}

   public RechargeEVO(int newRechargeId, int newModelId, String newVisId, String newDescription, String newReason, String newReference, BigDecimal newAllocationPercentage, boolean newManualRatios, int newAllocationDataTypeId, boolean newDiffAccount, int newAccountStructureId, int newAccountStructureElementId, int newRatioType, int newVersionNum, Collection newRechargeCells) {
      this.mRechargeId = newRechargeId;
      this.mModelId = newModelId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mReason = newReason;
      this.mReference = newReference;
      this.mAllocationPercentage = newAllocationPercentage;
      this.mManualRatios = newManualRatios;
      this.mAllocationDataTypeId = newAllocationDataTypeId;
      this.mDiffAccount = newDiffAccount;
      this.mAccountStructureId = newAccountStructureId;
      this.mAccountStructureElementId = newAccountStructureElementId;
      this.mRatioType = newRatioType;
      this.mVersionNum = newVersionNum;
      this.setRechargeCells(newRechargeCells);
   }

   public void setRechargeCells(Collection<RechargeCellsEVO> items) {
      if(items != null) {
         if(this.mRechargeCells == null) {
            this.mRechargeCells = new HashMap();
         } else {
            this.mRechargeCells.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            RechargeCellsEVO child = (RechargeCellsEVO)i$.next();
            this.mRechargeCells.put(child.getPK(), child);
         }
      } else {
         this.mRechargeCells = null;
      }

   }

   public RechargePK getPK() {
      if(this.mPK == null) {
         this.mPK = new RechargePK(this.mRechargeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getRechargeId() {
      return this.mRechargeId;
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

   public String getReason() {
      return this.mReason;
   }

   public String getReference() {
      return this.mReference;
   }

   public BigDecimal getAllocationPercentage() {
      return this.mAllocationPercentage;
   }

   public boolean getManualRatios() {
      return this.mManualRatios;
   }

   public int getAllocationDataTypeId() {
      return this.mAllocationDataTypeId;
   }

   public boolean getDiffAccount() {
      return this.mDiffAccount;
   }

   public int getAccountStructureId() {
      return this.mAccountStructureId;
   }

   public int getAccountStructureElementId() {
      return this.mAccountStructureElementId;
   }

   public int getRatioType() {
      return this.mRatioType;
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

   public void setRechargeId(int newRechargeId) {
      if(this.mRechargeId != newRechargeId) {
         this.mModified = true;
         this.mRechargeId = newRechargeId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setManualRatios(boolean newManualRatios) {
      if(this.mManualRatios != newManualRatios) {
         this.mModified = true;
         this.mManualRatios = newManualRatios;
      }
   }

   public void setAllocationDataTypeId(int newAllocationDataTypeId) {
      if(this.mAllocationDataTypeId != newAllocationDataTypeId) {
         this.mModified = true;
         this.mAllocationDataTypeId = newAllocationDataTypeId;
      }
   }

   public void setDiffAccount(boolean newDiffAccount) {
      if(this.mDiffAccount != newDiffAccount) {
         this.mModified = true;
         this.mDiffAccount = newDiffAccount;
      }
   }

   public void setAccountStructureId(int newAccountStructureId) {
      if(this.mAccountStructureId != newAccountStructureId) {
         this.mModified = true;
         this.mAccountStructureId = newAccountStructureId;
      }
   }

   public void setAccountStructureElementId(int newAccountStructureElementId) {
      if(this.mAccountStructureElementId != newAccountStructureElementId) {
         this.mModified = true;
         this.mAccountStructureElementId = newAccountStructureElementId;
      }
   }

   public void setRatioType(int newRatioType) {
      if(this.mRatioType != newRatioType) {
         this.mModified = true;
         this.mRatioType = newRatioType;
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

   public void setReason(String newReason) {
      if(this.mReason != null && newReason == null || this.mReason == null && newReason != null || this.mReason != null && newReason != null && !this.mReason.equals(newReason)) {
         this.mReason = newReason;
         this.mModified = true;
      }

   }

   public void setReference(String newReference) {
      if(this.mReference != null && newReference == null || this.mReference == null && newReference != null || this.mReference != null && newReference != null && !this.mReference.equals(newReference)) {
         this.mReference = newReference;
         this.mModified = true;
      }

   }

   public void setAllocationPercentage(BigDecimal newAllocationPercentage) {
      if(this.mAllocationPercentage != null && newAllocationPercentage == null || this.mAllocationPercentage == null && newAllocationPercentage != null || this.mAllocationPercentage != null && newAllocationPercentage != null && !this.mAllocationPercentage.equals(newAllocationPercentage)) {
         this.mAllocationPercentage = newAllocationPercentage;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(RechargeEVO newDetails) {
      this.setRechargeId(newDetails.getRechargeId());
      this.setModelId(newDetails.getModelId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setReason(newDetails.getReason());
      this.setReference(newDetails.getReference());
      this.setAllocationPercentage(newDetails.getAllocationPercentage());
      this.setManualRatios(newDetails.getManualRatios());
      this.setAllocationDataTypeId(newDetails.getAllocationDataTypeId());
      this.setDiffAccount(newDetails.getDiffAccount());
      this.setAccountStructureId(newDetails.getAccountStructureId());
      this.setAccountStructureElementId(newDetails.getAccountStructureElementId());
      this.setRatioType(newDetails.getRatioType());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public RechargeEVO deepClone() {
      RechargeEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (RechargeEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mRechargeId > 0) {
         newKey = true;
         if(parent == null) {
            this.setRechargeId(-this.mRechargeId);
         } else {
            parent.changeKey(this, -this.mRechargeId);
         }
      } else if(this.mRechargeId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      RechargeCellsEVO item;
      if(this.mRechargeCells != null) {
         for(Iterator iter = (new ArrayList(this.mRechargeCells.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (RechargeCellsEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mRechargeId < 1) {
         returnCount = startCount + 1;
      }

      RechargeCellsEVO item;
      if(this.mRechargeCells != null) {
         for(Iterator iter = this.mRechargeCells.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (RechargeCellsEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mRechargeId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      RechargeCellsEVO item;
      if(this.mRechargeCells != null) {
         for(Iterator iter = (new ArrayList(this.mRechargeCells.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (RechargeCellsEVO)iter.next();
            this.changeKey(item, item.getRechargeCellId(), this.mRechargeId);
         }
      }

      return nextKey;
   }

   public Collection<RechargeCellsEVO> getRechargeCells() {
      return this.mRechargeCells != null?this.mRechargeCells.values():null;
   }

   public Map<RechargeCellsPK, RechargeCellsEVO> getRechargeCellsMap() {
      return this.mRechargeCells;
   }

   public void loadRechargeCellsItem(RechargeCellsEVO newItem) {
      if(this.mRechargeCells == null) {
         this.mRechargeCells = new HashMap();
      }

      this.mRechargeCells.put(newItem.getPK(), newItem);
   }

   public void addRechargeCellsItem(RechargeCellsEVO newItem) {
      if(this.mRechargeCells == null) {
         this.mRechargeCells = new HashMap();
      }

      RechargeCellsPK newPK = newItem.getPK();
      if(this.getRechargeCellsItem(newPK) != null) {
         throw new RuntimeException("addRechargeCellsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mRechargeCells.put(newPK, newItem);
      }
   }

   public void changeRechargeCellsItem(RechargeCellsEVO changedItem) {
      if(this.mRechargeCells == null) {
         throw new RuntimeException("changeRechargeCellsItem: no items in collection");
      } else {
         RechargeCellsPK changedPK = changedItem.getPK();
         RechargeCellsEVO listItem = this.getRechargeCellsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeRechargeCellsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteRechargeCellsItem(RechargeCellsPK removePK) {
      RechargeCellsEVO listItem = this.getRechargeCellsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeRechargeCellsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public RechargeCellsEVO getRechargeCellsItem(RechargeCellsPK pk) {
      return (RechargeCellsEVO)this.mRechargeCells.get(pk);
   }

   public RechargeCellsEVO getRechargeCellsItem() {
      if(this.mRechargeCells.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mRechargeCells.size());
      } else {
         Iterator iter = this.mRechargeCells.values().iterator();
         return (RechargeCellsEVO)iter.next();
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

   public RechargeRef getEntityRef(ModelEVO evoModel) {
      return new RechargeRefImpl(new RechargeCK(evoModel.getPK(), this.getPK()), this.mVisId);
   }

   public RechargeCK getCK(ModelEVO evoModel) {
      return new RechargeCK(evoModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mRechargeCellsAllItemsLoaded = true;
      if(this.mRechargeCells == null) {
         this.mRechargeCells = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RechargeId=");
      sb.append(String.valueOf(this.mRechargeId));
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
      sb.append("Reason=");
      sb.append(String.valueOf(this.mReason));
      sb.append(' ');
      sb.append("Reference=");
      sb.append(String.valueOf(this.mReference));
      sb.append(' ');
      sb.append("AllocationPercentage=");
      sb.append(String.valueOf(this.mAllocationPercentage));
      sb.append(' ');
      sb.append("ManualRatios=");
      sb.append(String.valueOf(this.mManualRatios));
      sb.append(' ');
      sb.append("AllocationDataTypeId=");
      sb.append(String.valueOf(this.mAllocationDataTypeId));
      sb.append(' ');
      sb.append("DiffAccount=");
      sb.append(String.valueOf(this.mDiffAccount));
      sb.append(' ');
      sb.append("AccountStructureId=");
      sb.append(String.valueOf(this.mAccountStructureId));
      sb.append(' ');
      sb.append("AccountStructureElementId=");
      sb.append(String.valueOf(this.mAccountStructureElementId));
      sb.append(' ');
      sb.append("RatioType=");
      sb.append(String.valueOf(this.mRatioType));
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

      sb.append("Recharge: ");
      sb.append(this.toString());
      if(this.mRechargeCellsAllItemsLoaded || this.mRechargeCells != null) {
         sb.delete(indent, sb.length());
         sb.append(" - RechargeCells: allItemsLoaded=");
         sb.append(String.valueOf(this.mRechargeCellsAllItemsLoaded));
         sb.append(" items=");
         if(this.mRechargeCells == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mRechargeCells.size()));
         }
      }

      if(this.mRechargeCells != null) {
         Iterator var5 = this.mRechargeCells.values().iterator();

         while(var5.hasNext()) {
            RechargeCellsEVO listItem = (RechargeCellsEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(RechargeCellsEVO child, int newRechargeCellId, int newRechargeId) {
      if(this.getRechargeCellsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mRechargeCells.remove(child.getPK());
         child.setRechargeCellId(newRechargeCellId);
         child.setRechargeId(newRechargeId);
         this.mRechargeCells.put(child.getPK(), child);
      }
   }

   public void setRechargeCellsAllItemsLoaded(boolean allItemsLoaded) {
      this.mRechargeCellsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isRechargeCellsAllItemsLoaded() {
      return this.mRechargeCellsAllItemsLoaded;
   }
}
