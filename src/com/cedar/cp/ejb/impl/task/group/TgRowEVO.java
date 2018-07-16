// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task.group;

import com.cedar.cp.api.task.group.TgRowRef;
import com.cedar.cp.dto.task.group.TgRowCK;
import com.cedar.cp.dto.task.group.TgRowPK;
import com.cedar.cp.dto.task.group.TgRowParamPK;
import com.cedar.cp.dto.task.group.TgRowRefImpl;
import com.cedar.cp.ejb.impl.task.group.TaskGroupEVO;
import com.cedar.cp.ejb.impl.task.group.TgRowParamEVO;
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

public class TgRowEVO implements Serializable {

   private transient TgRowPK mPK;
   private int mTgRowId;
   private int mGroupId;
   private int mTgRowIndex;
   private int mRowType;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<TgRowParamPK, TgRowParamEVO> mTGRowsParams;
   protected boolean mTGRowsParamsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int BUDGET_STATE = 1;
   public static final int TIDY_TASK = 2;
   public static final int AMM_MODEL = 3;
   public static final int MAPPED_MODEL = 4;
   public static final int REPOT_PACK = 5;
   public static final int LOOKUP_REBUILD = 6;
   public static final int EXTERNAL_SYSTEM = 7;
   public static final int FORM_REBUILD = 8;
   public static final int TASK_GROUP = 100;


   public TgRowEVO() {}

   public TgRowEVO(int newTgRowId, int newGroupId, int newTgRowIndex, int newRowType, Collection newTGRowsParams) {
      this.mTgRowId = newTgRowId;
      this.mGroupId = newGroupId;
      this.mTgRowIndex = newTgRowIndex;
      this.mRowType = newRowType;
      this.setTGRowsParams(newTGRowsParams);
   }

   public void setTGRowsParams(Collection<TgRowParamEVO> items) {
      if(items != null) {
         if(this.mTGRowsParams == null) {
            this.mTGRowsParams = new HashMap();
         } else {
            this.mTGRowsParams.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            TgRowParamEVO child = (TgRowParamEVO)i$.next();
            this.mTGRowsParams.put(child.getPK(), child);
         }
      } else {
         this.mTGRowsParams = null;
      }

   }

   public TgRowPK getPK() {
      if(this.mPK == null) {
         this.mPK = new TgRowPK(this.mTgRowId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getTgRowId() {
      return this.mTgRowId;
   }

   public int getGroupId() {
      return this.mGroupId;
   }

   public int getTgRowIndex() {
      return this.mTgRowIndex;
   }

   public int getRowType() {
      return this.mRowType;
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

   public void setTgRowId(int newTgRowId) {
      if(this.mTgRowId != newTgRowId) {
         this.mModified = true;
         this.mTgRowId = newTgRowId;
         this.mPK = null;
      }
   }

   public void setGroupId(int newGroupId) {
      if(this.mGroupId != newGroupId) {
         this.mModified = true;
         this.mGroupId = newGroupId;
      }
   }

   public void setTgRowIndex(int newTgRowIndex) {
      if(this.mTgRowIndex != newTgRowIndex) {
         this.mModified = true;
         this.mTgRowIndex = newTgRowIndex;
      }
   }

   public void setRowType(int newRowType) {
      if(this.mRowType != newRowType) {
         this.mModified = true;
         this.mRowType = newRowType;
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

   public void setDetails(TgRowEVO newDetails) {
      this.setTgRowId(newDetails.getTgRowId());
      this.setGroupId(newDetails.getGroupId());
      this.setTgRowIndex(newDetails.getTgRowIndex());
      this.setRowType(newDetails.getRowType());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public TgRowEVO deepClone() {
      TgRowEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (TgRowEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(TaskGroupEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mTgRowId > 0) {
         newKey = true;
         if(parent == null) {
            this.setTgRowId(-this.mTgRowId);
         } else {
            parent.changeKey(this, -this.mTgRowId);
         }
      } else if(this.mTgRowId < 1) {
         newKey = true;
      }

      TgRowParamEVO item;
      if(this.mTGRowsParams != null) {
         for(Iterator iter = (new ArrayList(this.mTGRowsParams.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (TgRowParamEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mTgRowId < 1) {
         returnCount = startCount + 1;
      }

      TgRowParamEVO item;
      if(this.mTGRowsParams != null) {
         for(Iterator iter = this.mTGRowsParams.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (TgRowParamEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(TaskGroupEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mTgRowId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      TgRowParamEVO item;
      if(this.mTGRowsParams != null) {
         for(Iterator iter = (new ArrayList(this.mTGRowsParams.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (TgRowParamEVO)iter.next();
            item.setTgRowId(this.mTgRowId);
         }
      }

      return nextKey;
   }

   public Collection<TgRowParamEVO> getTGRowsParams() {
      return this.mTGRowsParams != null?this.mTGRowsParams.values():null;
   }

   public Map<TgRowParamPK, TgRowParamEVO> getTGRowsParamsMap() {
      return this.mTGRowsParams;
   }

   public void loadTGRowsParamsItem(TgRowParamEVO newItem) {
      if(this.mTGRowsParams == null) {
         this.mTGRowsParams = new HashMap();
      }

      this.mTGRowsParams.put(newItem.getPK(), newItem);
   }

   public void addTGRowsParamsItem(TgRowParamEVO newItem) {
      if(this.mTGRowsParams == null) {
         this.mTGRowsParams = new HashMap();
      }

      TgRowParamPK newPK = newItem.getPK();
      if(this.getTGRowsParamsItem(newPK) != null) {
         throw new RuntimeException("addTGRowsParamsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mTGRowsParams.put(newPK, newItem);
      }
   }

   public void changeTGRowsParamsItem(TgRowParamEVO changedItem) {
      if(this.mTGRowsParams == null) {
         throw new RuntimeException("changeTGRowsParamsItem: no items in collection");
      } else {
         TgRowParamPK changedPK = changedItem.getPK();
         TgRowParamEVO listItem = this.getTGRowsParamsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeTGRowsParamsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteTGRowsParamsItem(TgRowParamPK removePK) {
      TgRowParamEVO listItem = this.getTGRowsParamsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeTGRowsParamsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public TgRowParamEVO getTGRowsParamsItem(TgRowParamPK pk) {
      return (TgRowParamEVO)this.mTGRowsParams.get(pk);
   }

   public TgRowParamEVO getTGRowsParamsItem() {
      if(this.mTGRowsParams.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mTGRowsParams.size());
      } else {
         Iterator iter = this.mTGRowsParams.values().iterator();
         return (TgRowParamEVO)iter.next();
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

   public TgRowRef getEntityRef(TaskGroupEVO evoTaskGroup, String entityText) {
      return new TgRowRefImpl(new TgRowCK(evoTaskGroup.getPK(), this.getPK()), entityText);
   }

   public TgRowCK getCK(TaskGroupEVO evoTaskGroup) {
      return new TgRowCK(evoTaskGroup.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mTGRowsParamsAllItemsLoaded = true;
      if(this.mTGRowsParams == null) {
         this.mTGRowsParams = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("TgRowId=");
      sb.append(String.valueOf(this.mTgRowId));
      sb.append(' ');
      sb.append("GroupId=");
      sb.append(String.valueOf(this.mGroupId));
      sb.append(' ');
      sb.append("TgRowIndex=");
      sb.append(String.valueOf(this.mTgRowIndex));
      sb.append(' ');
      sb.append("RowType=");
      sb.append(String.valueOf(this.mRowType));
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

      sb.append("TgRow: ");
      sb.append(this.toString());
      if(this.mTGRowsParamsAllItemsLoaded || this.mTGRowsParams != null) {
         sb.delete(indent, sb.length());
         sb.append(" - TGRowsParams: allItemsLoaded=");
         sb.append(String.valueOf(this.mTGRowsParamsAllItemsLoaded));
         sb.append(" items=");
         if(this.mTGRowsParams == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mTGRowsParams.size()));
         }
      }

      if(this.mTGRowsParams != null) {
         Iterator var5 = this.mTGRowsParams.values().iterator();

         while(var5.hasNext()) {
            TgRowParamEVO listItem = (TgRowParamEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(TgRowParamEVO child, int newTgRowParamId) {
      if(this.getTGRowsParamsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mTGRowsParams.remove(child.getPK());
         child.setTgRowParamId(newTgRowParamId);
         this.mTGRowsParams.put(child.getPK(), child);
      }
   }

   public void setTGRowsParamsAllItemsLoaded(boolean allItemsLoaded) {
      this.mTGRowsParamsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isTGRowsParamsAllItemsLoaded() {
      return this.mTGRowsParamsAllItemsLoaded;
   }
}
