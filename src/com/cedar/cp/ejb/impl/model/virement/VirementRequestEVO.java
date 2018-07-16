// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.api.model.virement.VirementRequestRef;
import com.cedar.cp.dto.model.virement.VirementAuthPointPK;
import com.cedar.cp.dto.model.virement.VirementRequestCK;
import com.cedar.cp.dto.model.virement.VirementRequestGroupPK;
import com.cedar.cp.dto.model.virement.VirementRequestPK;
import com.cedar.cp.dto.model.virement.VirementRequestRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementAuthPointEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestGroupEVO;
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

public class VirementRequestEVO implements Serializable {

   private transient VirementRequestPK mPK;
   private int mRequestId;
   private int mModelId;
   private int mFinanceCubeId;
   private int mBudgetCycleId;
   private int mRequestStatus;
   private int mUserId;
   private String mReason;
   private String mReference;
   private Timestamp mDateSubmitted;
   private int mBudgetActivityId;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<VirementRequestGroupPK, VirementRequestGroupEVO> mGroups;
   protected boolean mGroupsAllItemsLoaded;
   private Map<VirementAuthPointPK, VirementAuthPointEVO> mAuthPoints;
   protected boolean mAuthPointsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int NOT_SUBMITTED = 0;
   public static final int NOT_AUTHORISED = 1;
   public static final int AUTHORISED = 2;
   public static final int PROCESSED = 3;


   public VirementRequestEVO() {}

   public VirementRequestEVO(int newRequestId, int newModelId, int newFinanceCubeId, int newBudgetCycleId, int newRequestStatus, int newUserId, String newReason, String newReference, Timestamp newDateSubmitted, int newBudgetActivityId, int newVersionNum, Collection newGroups, Collection newAuthPoints) {
      this.mRequestId = newRequestId;
      this.mModelId = newModelId;
      this.mFinanceCubeId = newFinanceCubeId;
      this.mBudgetCycleId = newBudgetCycleId;
      this.mRequestStatus = newRequestStatus;
      this.mUserId = newUserId;
      this.mReason = newReason;
      this.mReference = newReference;
      this.mDateSubmitted = newDateSubmitted;
      this.mBudgetActivityId = newBudgetActivityId;
      this.mVersionNum = newVersionNum;
      this.setGroups(newGroups);
      this.setAuthPoints(newAuthPoints);
   }

   public void setGroups(Collection<VirementRequestGroupEVO> items) {
      if(items != null) {
         if(this.mGroups == null) {
            this.mGroups = new HashMap();
         } else {
            this.mGroups.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            VirementRequestGroupEVO child = (VirementRequestGroupEVO)i$.next();
            this.mGroups.put(child.getPK(), child);
         }
      } else {
         this.mGroups = null;
      }

   }

   public void setAuthPoints(Collection<VirementAuthPointEVO> items) {
      if(items != null) {
         if(this.mAuthPoints == null) {
            this.mAuthPoints = new HashMap();
         } else {
            this.mAuthPoints.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            VirementAuthPointEVO child = (VirementAuthPointEVO)i$.next();
            this.mAuthPoints.put(child.getPK(), child);
         }
      } else {
         this.mAuthPoints = null;
      }

   }

   public VirementRequestPK getPK() {
      if(this.mPK == null) {
         this.mPK = new VirementRequestPK(this.mRequestId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getRequestId() {
      return this.mRequestId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public int getRequestStatus() {
      return this.mRequestStatus;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public String getReason() {
      return this.mReason;
   }

   public String getReference() {
      return this.mReference;
   }

   public Timestamp getDateSubmitted() {
      return this.mDateSubmitted;
   }

   public int getBudgetActivityId() {
      return this.mBudgetActivityId;
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

   public void setRequestId(int newRequestId) {
      if(this.mRequestId != newRequestId) {
         this.mModified = true;
         this.mRequestId = newRequestId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setFinanceCubeId(int newFinanceCubeId) {
      if(this.mFinanceCubeId != newFinanceCubeId) {
         this.mModified = true;
         this.mFinanceCubeId = newFinanceCubeId;
      }
   }

   public void setBudgetCycleId(int newBudgetCycleId) {
      if(this.mBudgetCycleId != newBudgetCycleId) {
         this.mModified = true;
         this.mBudgetCycleId = newBudgetCycleId;
      }
   }

   public void setRequestStatus(int newRequestStatus) {
      if(this.mRequestStatus != newRequestStatus) {
         this.mModified = true;
         this.mRequestStatus = newRequestStatus;
      }
   }

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
      }
   }

   public void setBudgetActivityId(int newBudgetActivityId) {
      if(this.mBudgetActivityId != newBudgetActivityId) {
         this.mModified = true;
         this.mBudgetActivityId = newBudgetActivityId;
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

   public void setDateSubmitted(Timestamp newDateSubmitted) {
      if(this.mDateSubmitted != null && newDateSubmitted == null || this.mDateSubmitted == null && newDateSubmitted != null || this.mDateSubmitted != null && newDateSubmitted != null && !this.mDateSubmitted.equals(newDateSubmitted)) {
         this.mDateSubmitted = newDateSubmitted;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(VirementRequestEVO newDetails) {
      this.setRequestId(newDetails.getRequestId());
      this.setModelId(newDetails.getModelId());
      this.setFinanceCubeId(newDetails.getFinanceCubeId());
      this.setBudgetCycleId(newDetails.getBudgetCycleId());
      this.setRequestStatus(newDetails.getRequestStatus());
      this.setUserId(newDetails.getUserId());
      this.setReason(newDetails.getReason());
      this.setReference(newDetails.getReference());
      this.setDateSubmitted(newDetails.getDateSubmitted());
      this.setBudgetActivityId(newDetails.getBudgetActivityId());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public VirementRequestEVO deepClone() {
      VirementRequestEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (VirementRequestEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mRequestId > 0) {
         newKey = true;
         if(parent == null) {
            this.setRequestId(-this.mRequestId);
         } else {
            parent.changeKey(this, -this.mRequestId);
         }
      } else if(this.mRequestId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      Iterator iter;
      VirementRequestGroupEVO item;
      if(this.mGroups != null) {
         for(iter = (new ArrayList(this.mGroups.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (VirementRequestGroupEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      VirementAuthPointEVO item1;
      if(this.mAuthPoints != null) {
         for(iter = (new ArrayList(this.mAuthPoints.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (VirementAuthPointEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mRequestId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      VirementRequestGroupEVO item;
      if(this.mGroups != null) {
         for(iter = this.mGroups.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (VirementRequestGroupEVO)iter.next();
         }
      }

      VirementAuthPointEVO item1;
      if(this.mAuthPoints != null) {
         for(iter = this.mAuthPoints.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (VirementAuthPointEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mRequestId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      Iterator iter;
      VirementRequestGroupEVO item;
      if(this.mGroups != null) {
         for(iter = (new ArrayList(this.mGroups.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (VirementRequestGroupEVO)iter.next();
            item.setRequestId(this.mRequestId);
         }
      }

      VirementAuthPointEVO item1;
      if(this.mAuthPoints != null) {
         for(iter = (new ArrayList(this.mAuthPoints.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (VirementAuthPointEVO)iter.next();
            item1.setRequestId(this.mRequestId);
         }
      }

      return nextKey;
   }

   public Collection<VirementRequestGroupEVO> getGroups() {
      return this.mGroups != null?this.mGroups.values():null;
   }

   public Map<VirementRequestGroupPK, VirementRequestGroupEVO> getGroupsMap() {
      return this.mGroups;
   }

   public void loadGroupsItem(VirementRequestGroupEVO newItem) {
      if(this.mGroups == null) {
         this.mGroups = new HashMap();
      }

      this.mGroups.put(newItem.getPK(), newItem);
   }

   public void addGroupsItem(VirementRequestGroupEVO newItem) {
      if(this.mGroups == null) {
         this.mGroups = new HashMap();
      }

      VirementRequestGroupPK newPK = newItem.getPK();
      if(this.getGroupsItem(newPK) != null) {
         throw new RuntimeException("addGroupsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mGroups.put(newPK, newItem);
      }
   }

   public void changeGroupsItem(VirementRequestGroupEVO changedItem) {
      if(this.mGroups == null) {
         throw new RuntimeException("changeGroupsItem: no items in collection");
      } else {
         VirementRequestGroupPK changedPK = changedItem.getPK();
         VirementRequestGroupEVO listItem = this.getGroupsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeGroupsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteGroupsItem(VirementRequestGroupPK removePK) {
      VirementRequestGroupEVO listItem = this.getGroupsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeGroupsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public VirementRequestGroupEVO getGroupsItem(VirementRequestGroupPK pk) {
      return (VirementRequestGroupEVO)this.mGroups.get(pk);
   }

   public VirementRequestGroupEVO getGroupsItem() {
      if(this.mGroups.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mGroups.size());
      } else {
         Iterator iter = this.mGroups.values().iterator();
         return (VirementRequestGroupEVO)iter.next();
      }
   }

   public Collection<VirementAuthPointEVO> getAuthPoints() {
      return this.mAuthPoints != null?this.mAuthPoints.values():null;
   }

   public Map<VirementAuthPointPK, VirementAuthPointEVO> getAuthPointsMap() {
      return this.mAuthPoints;
   }

   public void loadAuthPointsItem(VirementAuthPointEVO newItem) {
      if(this.mAuthPoints == null) {
         this.mAuthPoints = new HashMap();
      }

      this.mAuthPoints.put(newItem.getPK(), newItem);
   }

   public void addAuthPointsItem(VirementAuthPointEVO newItem) {
      if(this.mAuthPoints == null) {
         this.mAuthPoints = new HashMap();
      }

      VirementAuthPointPK newPK = newItem.getPK();
      if(this.getAuthPointsItem(newPK) != null) {
         throw new RuntimeException("addAuthPointsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mAuthPoints.put(newPK, newItem);
      }
   }

   public void changeAuthPointsItem(VirementAuthPointEVO changedItem) {
      if(this.mAuthPoints == null) {
         throw new RuntimeException("changeAuthPointsItem: no items in collection");
      } else {
         VirementAuthPointPK changedPK = changedItem.getPK();
         VirementAuthPointEVO listItem = this.getAuthPointsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeAuthPointsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteAuthPointsItem(VirementAuthPointPK removePK) {
      VirementAuthPointEVO listItem = this.getAuthPointsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeAuthPointsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public VirementAuthPointEVO getAuthPointsItem(VirementAuthPointPK pk) {
      return (VirementAuthPointEVO)this.mAuthPoints.get(pk);
   }

   public VirementAuthPointEVO getAuthPointsItem() {
      if(this.mAuthPoints.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mAuthPoints.size());
      } else {
         Iterator iter = this.mAuthPoints.values().iterator();
         return (VirementAuthPointEVO)iter.next();
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

   public VirementRequestRef getEntityRef(ModelEVO evoModel, String entityText) {
      return new VirementRequestRefImpl(new VirementRequestCK(evoModel.getPK(), this.getPK()), entityText);
   }

   public VirementRequestCK getCK(ModelEVO evoModel) {
      return new VirementRequestCK(evoModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mGroupsAllItemsLoaded = true;
      Iterator i$;
      if(this.mGroups == null) {
         this.mGroups = new HashMap();
      } else {
         i$ = this.mGroups.values().iterator();

         while(i$.hasNext()) {
            VirementRequestGroupEVO child = (VirementRequestGroupEVO)i$.next();
            child.postCreateInit();
         }
      }

      this.mAuthPointsAllItemsLoaded = true;
      if(this.mAuthPoints == null) {
         this.mAuthPoints = new HashMap();
      } else {
         i$ = this.mAuthPoints.values().iterator();

         while(i$.hasNext()) {
            VirementAuthPointEVO child1 = (VirementAuthPointEVO)i$.next();
            child1.postCreateInit();
         }
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RequestId=");
      sb.append(String.valueOf(this.mRequestId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("FinanceCubeId=");
      sb.append(String.valueOf(this.mFinanceCubeId));
      sb.append(' ');
      sb.append("BudgetCycleId=");
      sb.append(String.valueOf(this.mBudgetCycleId));
      sb.append(' ');
      sb.append("RequestStatus=");
      sb.append(String.valueOf(this.mRequestStatus));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      sb.append("Reason=");
      sb.append(String.valueOf(this.mReason));
      sb.append(' ');
      sb.append("Reference=");
      sb.append(String.valueOf(this.mReference));
      sb.append(' ');
      sb.append("DateSubmitted=");
      sb.append(String.valueOf(this.mDateSubmitted));
      sb.append(' ');
      sb.append("BudgetActivityId=");
      sb.append(String.valueOf(this.mBudgetActivityId));
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

      sb.append("VirementRequest: ");
      sb.append(this.toString());
      if(this.mGroupsAllItemsLoaded || this.mGroups != null) {
         sb.delete(indent, sb.length());
         sb.append(" - Groups: allItemsLoaded=");
         sb.append(String.valueOf(this.mGroupsAllItemsLoaded));
         sb.append(" items=");
         if(this.mGroups == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mGroups.size()));
         }
      }

      if(this.mAuthPointsAllItemsLoaded || this.mAuthPoints != null) {
         sb.delete(indent, sb.length());
         sb.append(" - AuthPoints: allItemsLoaded=");
         sb.append(String.valueOf(this.mAuthPointsAllItemsLoaded));
         sb.append(" items=");
         if(this.mAuthPoints == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mAuthPoints.size()));
         }
      }

      Iterator var5;
      if(this.mGroups != null) {
         var5 = this.mGroups.values().iterator();

         while(var5.hasNext()) {
            VirementRequestGroupEVO listItem = (VirementRequestGroupEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mAuthPoints != null) {
         var5 = this.mAuthPoints.values().iterator();

         while(var5.hasNext()) {
            VirementAuthPointEVO var6 = (VirementAuthPointEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(VirementRequestGroupEVO child, int newRequestGroupId) {
      if(this.getGroupsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mGroups.remove(child.getPK());
         child.setRequestGroupId(newRequestGroupId);
         this.mGroups.put(child.getPK(), child);
      }
   }

   public void changeKey(VirementAuthPointEVO child, int newAuthPointId) {
      if(this.getAuthPointsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mAuthPoints.remove(child.getPK());
         child.setAuthPointId(newAuthPointId);
         this.mAuthPoints.put(child.getPK(), child);
      }
   }

   public void setGroupsAllItemsLoaded(boolean allItemsLoaded) {
      this.mGroupsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isGroupsAllItemsLoaded() {
      return this.mGroupsAllItemsLoaded;
   }

   public void setAuthPointsAllItemsLoaded(boolean allItemsLoaded) {
      this.mAuthPointsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isAuthPointsAllItemsLoaded() {
      return this.mAuthPointsAllItemsLoaded;
   }
}
