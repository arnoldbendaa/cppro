// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task.group;

import com.cedar.cp.api.task.group.TaskGroupRef;
import com.cedar.cp.dto.task.group.TaskGroupPK;
import com.cedar.cp.dto.task.group.TaskGroupRefImpl;
import com.cedar.cp.dto.task.group.TgRowPK;
import com.cedar.cp.ejb.impl.task.group.TgRowEVO;
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

public class TaskGroupEVO implements Serializable {

   private transient TaskGroupPK mPK;
   private int mGroupId;
   private String mVisId;
   private String mDescription;
   private Timestamp mLastSubmit;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<TgRowPK, TgRowEVO> mTaskGroupRows;
   protected boolean mTaskGroupRowsAllItemsLoaded;
   private boolean mModified;


   public TaskGroupEVO() {}

   public TaskGroupEVO(int newGroupId, String newVisId, String newDescription, Timestamp newLastSubmit, Collection newTaskGroupRows) {
      this.mGroupId = newGroupId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mLastSubmit = newLastSubmit;
      this.setTaskGroupRows(newTaskGroupRows);
   }

   public void setTaskGroupRows(Collection<TgRowEVO> items) {
      if(items != null) {
         if(this.mTaskGroupRows == null) {
            this.mTaskGroupRows = new HashMap();
         } else {
            this.mTaskGroupRows.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            TgRowEVO child = (TgRowEVO)i$.next();
            this.mTaskGroupRows.put(child.getPK(), child);
         }
      } else {
         this.mTaskGroupRows = null;
      }

   }

   public TaskGroupPK getPK() {
      if(this.mPK == null) {
         this.mPK = new TaskGroupPK(this.mGroupId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getGroupId() {
      return this.mGroupId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Timestamp getLastSubmit() {
      return this.mLastSubmit;
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

   public void setGroupId(int newGroupId) {
      if(this.mGroupId != newGroupId) {
         this.mModified = true;
         this.mGroupId = newGroupId;
         this.mPK = null;
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

   public void setLastSubmit(Timestamp newLastSubmit) {
      if(this.mLastSubmit != null && newLastSubmit == null || this.mLastSubmit == null && newLastSubmit != null || this.mLastSubmit != null && newLastSubmit != null && !this.mLastSubmit.equals(newLastSubmit)) {
         this.mLastSubmit = newLastSubmit;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(TaskGroupEVO newDetails) {
      this.setGroupId(newDetails.getGroupId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setLastSubmit(newDetails.getLastSubmit());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public TaskGroupEVO deepClone() {
      TaskGroupEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (TaskGroupEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mGroupId > 0) {
         newKey = true;
         this.mGroupId = 0;
      } else if(this.mGroupId < 1) {
         newKey = true;
      }

      TgRowEVO item;
      if(this.mTaskGroupRows != null) {
         for(Iterator iter = (new ArrayList(this.mTaskGroupRows.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (TgRowEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mGroupId < 1) {
         returnCount = startCount + 1;
      }

      TgRowEVO item;
      if(this.mTaskGroupRows != null) {
         for(Iterator iter = this.mTaskGroupRows.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (TgRowEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mGroupId < 1) {
         this.mGroupId = startKey;
         nextKey = startKey + 1;
      }

      TgRowEVO item;
      if(this.mTaskGroupRows != null) {
         for(Iterator iter = (new ArrayList(this.mTaskGroupRows.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (TgRowEVO)iter.next();
            item.setGroupId(this.mGroupId);
         }
      }

      return nextKey;
   }

   public Collection<TgRowEVO> getTaskGroupRows() {
      return this.mTaskGroupRows != null?this.mTaskGroupRows.values():null;
   }

   public Map<TgRowPK, TgRowEVO> getTaskGroupRowsMap() {
      return this.mTaskGroupRows;
   }

   public void loadTaskGroupRowsItem(TgRowEVO newItem) {
      if(this.mTaskGroupRows == null) {
         this.mTaskGroupRows = new HashMap();
      }

      this.mTaskGroupRows.put(newItem.getPK(), newItem);
   }

   public void addTaskGroupRowsItem(TgRowEVO newItem) {
      if(this.mTaskGroupRows == null) {
         this.mTaskGroupRows = new HashMap();
      }

      TgRowPK newPK = newItem.getPK();
      if(this.getTaskGroupRowsItem(newPK) != null) {
         throw new RuntimeException("addTaskGroupRowsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mTaskGroupRows.put(newPK, newItem);
      }
   }

   public void changeTaskGroupRowsItem(TgRowEVO changedItem) {
      if(this.mTaskGroupRows == null) {
         throw new RuntimeException("changeTaskGroupRowsItem: no items in collection");
      } else {
         TgRowPK changedPK = changedItem.getPK();
         TgRowEVO listItem = this.getTaskGroupRowsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeTaskGroupRowsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteTaskGroupRowsItem(TgRowPK removePK) {
      TgRowEVO listItem = this.getTaskGroupRowsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeTaskGroupRowsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public TgRowEVO getTaskGroupRowsItem(TgRowPK pk) {
      return (TgRowEVO)this.mTaskGroupRows.get(pk);
   }

   public TgRowEVO getTaskGroupRowsItem() {
      if(this.mTaskGroupRows.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mTaskGroupRows.size());
      } else {
         Iterator iter = this.mTaskGroupRows.values().iterator();
         return (TgRowEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public TaskGroupRef getEntityRef() {
      return new TaskGroupRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mTaskGroupRowsAllItemsLoaded = true;
      if(this.mTaskGroupRows == null) {
         this.mTaskGroupRows = new HashMap();
      } else {
         Iterator i$ = this.mTaskGroupRows.values().iterator();

         while(i$.hasNext()) {
            TgRowEVO child = (TgRowEVO)i$.next();
            child.postCreateInit();
         }
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("GroupId=");
      sb.append(String.valueOf(this.mGroupId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("LastSubmit=");
      sb.append(String.valueOf(this.mLastSubmit));
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

      sb.append("TaskGroup: ");
      sb.append(this.toString());
      if(this.mTaskGroupRowsAllItemsLoaded || this.mTaskGroupRows != null) {
         sb.delete(indent, sb.length());
         sb.append(" - TaskGroupRows: allItemsLoaded=");
         sb.append(String.valueOf(this.mTaskGroupRowsAllItemsLoaded));
         sb.append(" items=");
         if(this.mTaskGroupRows == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mTaskGroupRows.size()));
         }
      }

      if(this.mTaskGroupRows != null) {
         Iterator var5 = this.mTaskGroupRows.values().iterator();

         while(var5.hasNext()) {
            TgRowEVO listItem = (TgRowEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(TgRowEVO child, int newTgRowId) {
      if(this.getTaskGroupRowsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mTaskGroupRows.remove(child.getPK());
         child.setTgRowId(newTgRowId);
         this.mTaskGroupRows.put(child.getPK(), child);
      }
   }

   public void setTaskGroupRowsAllItemsLoaded(boolean allItemsLoaded) {
      this.mTaskGroupRowsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isTaskGroupRowsAllItemsLoaded() {
      return this.mTaskGroupRowsAllItemsLoaded;
   }
}
