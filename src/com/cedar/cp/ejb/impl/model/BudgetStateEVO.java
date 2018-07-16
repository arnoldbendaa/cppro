// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.BudgetStateRef;
import com.cedar.cp.dto.model.BudgetStateCK;
import com.cedar.cp.dto.model.BudgetStateHistoryPK;
import com.cedar.cp.dto.model.BudgetStatePK;
import com.cedar.cp.dto.model.BudgetStateRefImpl;
import com.cedar.cp.ejb.impl.model.BudgetCycleEVO;
import com.cedar.cp.ejb.impl.model.BudgetStateHistoryEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
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

public class BudgetStateEVO implements Serializable {

   private transient BudgetStatePK mPK;
   private int mBudgetCycleId;
   private int mStructureElementId;
   private int mState;
   private boolean mSubmitable;
   private boolean mRejectable;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<BudgetStateHistoryPK, BudgetStateHistoryEVO> mBudgetCycleHistory;
   protected boolean mBudgetCycleHistoryAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int STATE_INITIATED = 1;
   public static final int STATE_PREPARING = 2;
   public static final int STATE_SUBMITTED = 3;
   public static final int STATE_AGREED = 4;
   public static final int STATE_DISABLED = 5;
   public static final int STATE_NOT_PLANNABLE = 6;


   public BudgetStateEVO() {}

   public BudgetStateEVO(int newBudgetCycleId, int newStructureElementId, int newState, boolean newSubmitable, boolean newRejectable, int newVersionNum, Collection newBudgetCycleHistory) {
      this.mBudgetCycleId = newBudgetCycleId;
      this.mStructureElementId = newStructureElementId;
      this.mState = newState;
      this.mSubmitable = newSubmitable;
      this.mRejectable = newRejectable;
      this.mVersionNum = newVersionNum;
      this.setBudgetCycleHistory(newBudgetCycleHistory);
   }

   public void setBudgetCycleHistory(Collection<BudgetStateHistoryEVO> items) {
      if(items != null) {
         if(this.mBudgetCycleHistory == null) {
            this.mBudgetCycleHistory = new HashMap();
         } else {
            this.mBudgetCycleHistory.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            BudgetStateHistoryEVO child = (BudgetStateHistoryEVO)i$.next();
            this.mBudgetCycleHistory.put(child.getPK(), child);
         }
      } else {
         this.mBudgetCycleHistory = null;
      }

   }

   public BudgetStatePK getPK() {
      if(this.mPK == null) {
         this.mPK = new BudgetStatePK(this.mBudgetCycleId, this.mStructureElementId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public int getState() {
      return this.mState;
   }

   public boolean getSubmitable() {
      return this.mSubmitable;
   }

   public boolean getRejectable() {
      return this.mRejectable;
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

   public void setBudgetCycleId(int newBudgetCycleId) {
      if(this.mBudgetCycleId != newBudgetCycleId) {
         this.mModified = true;
         this.mBudgetCycleId = newBudgetCycleId;
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

   public void setState(int newState) {
      if(this.mState != newState) {
         this.mModified = true;
         this.mState = newState;
      }
   }

   public void setSubmitable(boolean newSubmitable) {
      if(this.mSubmitable != newSubmitable) {
         this.mModified = true;
         this.mSubmitable = newSubmitable;
      }
   }

   public void setRejectable(boolean newRejectable) {
      if(this.mRejectable != newRejectable) {
         this.mModified = true;
         this.mRejectable = newRejectable;
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

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(BudgetStateEVO newDetails) {
      this.setBudgetCycleId(newDetails.getBudgetCycleId());
      this.setStructureElementId(newDetails.getStructureElementId());
      this.setState(newDetails.getState());
      this.setSubmitable(newDetails.getSubmitable());
      this.setRejectable(newDetails.getRejectable());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public BudgetStateEVO deepClone() {
      BudgetStateEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (BudgetStateEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(BudgetCycleEVO parent) {
      boolean newKey = this.insertPending();
      this.setVersionNum(0);
      BudgetStateHistoryEVO item;
      if(this.mBudgetCycleHistory != null) {
         for(Iterator iter = (new ArrayList(this.mBudgetCycleHistory.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (BudgetStateHistoryEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      BudgetStateHistoryEVO item;
      if(this.mBudgetCycleHistory != null) {
         for(Iterator iter = this.mBudgetCycleHistory.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (BudgetStateHistoryEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(BudgetCycleEVO parent, int startKey) {
      int nextKey = startKey;
      BudgetStateHistoryEVO item;
      if(this.mBudgetCycleHistory != null) {
         for(Iterator iter = (new ArrayList(this.mBudgetCycleHistory.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (BudgetStateHistoryEVO)iter.next();
            this.changeKey(item, item.getBudgetStateHistoryId(), this.mBudgetCycleId, this.mStructureElementId);
         }
      }

      return nextKey;
   }

   public Collection<BudgetStateHistoryEVO> getBudgetCycleHistory() {
      return this.mBudgetCycleHistory != null?this.mBudgetCycleHistory.values():null;
   }

   public Map<BudgetStateHistoryPK, BudgetStateHistoryEVO> getBudgetCycleHistoryMap() {
      return this.mBudgetCycleHistory;
   }

   public void loadBudgetCycleHistoryItem(BudgetStateHistoryEVO newItem) {
      if(this.mBudgetCycleHistory == null) {
         this.mBudgetCycleHistory = new HashMap();
      }

      this.mBudgetCycleHistory.put(newItem.getPK(), newItem);
   }

   public void addBudgetCycleHistoryItem(BudgetStateHistoryEVO newItem) {
      if(this.mBudgetCycleHistory == null) {
         this.mBudgetCycleHistory = new HashMap();
      }

      BudgetStateHistoryPK newPK = newItem.getPK();
      if(this.getBudgetCycleHistoryItem(newPK) != null) {
         throw new RuntimeException("addBudgetCycleHistoryItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mBudgetCycleHistory.put(newPK, newItem);
      }
   }

   public void changeBudgetCycleHistoryItem(BudgetStateHistoryEVO changedItem) {
      if(this.mBudgetCycleHistory == null) {
         throw new RuntimeException("changeBudgetCycleHistoryItem: no items in collection");
      } else {
         BudgetStateHistoryPK changedPK = changedItem.getPK();
         BudgetStateHistoryEVO listItem = this.getBudgetCycleHistoryItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeBudgetCycleHistoryItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteBudgetCycleHistoryItem(BudgetStateHistoryPK removePK) {
      BudgetStateHistoryEVO listItem = this.getBudgetCycleHistoryItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeBudgetCycleHistoryItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public BudgetStateHistoryEVO getBudgetCycleHistoryItem(BudgetStateHistoryPK pk) {
      return (BudgetStateHistoryEVO)this.mBudgetCycleHistory.get(pk);
   }

   public BudgetStateHistoryEVO getBudgetCycleHistoryItem() {
      if(this.mBudgetCycleHistory.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mBudgetCycleHistory.size());
      } else {
         Iterator iter = this.mBudgetCycleHistory.values().iterator();
         return (BudgetStateHistoryEVO)iter.next();
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

   public BudgetStateRef getEntityRef(ModelEVO evoModel, BudgetCycleEVO evoBudgetCycle, String entityText) {
      return new BudgetStateRefImpl(new BudgetStateCK(evoModel.getPK(), evoBudgetCycle.getPK(), this.getPK()), entityText);
   }

   public BudgetStateCK getCK(ModelEVO evoModel, BudgetCycleEVO evoBudgetCycle) {
      return new BudgetStateCK(evoModel.getPK(), evoBudgetCycle.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mBudgetCycleHistoryAllItemsLoaded = true;
      if(this.mBudgetCycleHistory == null) {
         this.mBudgetCycleHistory = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("BudgetCycleId=");
      sb.append(String.valueOf(this.mBudgetCycleId));
      sb.append(' ');
      sb.append("StructureElementId=");
      sb.append(String.valueOf(this.mStructureElementId));
      sb.append(' ');
      sb.append("State=");
      sb.append(String.valueOf(this.mState));
      sb.append(' ');
      sb.append("Submitable=");
      sb.append(String.valueOf(this.mSubmitable));
      sb.append(' ');
      sb.append("Rejectable=");
      sb.append(String.valueOf(this.mRejectable));
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

      sb.append("BudgetState: ");
      sb.append(this.toString());
      if(this.mBudgetCycleHistoryAllItemsLoaded || this.mBudgetCycleHistory != null) {
         sb.delete(indent, sb.length());
         sb.append(" - BudgetCycleHistory: allItemsLoaded=");
         sb.append(String.valueOf(this.mBudgetCycleHistoryAllItemsLoaded));
         sb.append(" items=");
         if(this.mBudgetCycleHistory == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mBudgetCycleHistory.size()));
         }
      }

      if(this.mBudgetCycleHistory != null) {
         Iterator var5 = this.mBudgetCycleHistory.values().iterator();

         while(var5.hasNext()) {
            BudgetStateHistoryEVO listItem = (BudgetStateHistoryEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(BudgetStateHistoryEVO child, int newBudgetStateHistoryId, int newBudgetCycleId, int newStructureElementId) {
      if(this.getBudgetCycleHistoryItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mBudgetCycleHistory.remove(child.getPK());
         child.setBudgetStateHistoryId(newBudgetStateHistoryId);
         child.setBudgetCycleId(newBudgetCycleId);
         child.setStructureElementId(newStructureElementId);
         this.mBudgetCycleHistory.put(child.getPK(), child);
      }
   }

   public void setBudgetCycleHistoryAllItemsLoaded(boolean allItemsLoaded) {
      this.mBudgetCycleHistoryAllItemsLoaded = allItemsLoaded;
   }

   public boolean isBudgetCycleHistoryAllItemsLoaded() {
      return this.mBudgetCycleHistoryAllItemsLoaded;
   }
}
