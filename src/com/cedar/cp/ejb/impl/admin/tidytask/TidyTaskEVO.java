// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.admin.tidytask;

import com.cedar.cp.api.admin.tidytask.TidyTaskRef;
import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkPK;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import com.cedar.cp.dto.admin.tidytask.TidyTaskRefImpl;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskLinkEVO;
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

public class TidyTaskEVO implements Serializable {

   private transient TidyTaskPK mPK;
   private int mTidyTaskId;
   private String mVisId;
   private String mDescription;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<TidyTaskLinkPK, TidyTaskLinkEVO> mTidyTasksEvents;
   protected boolean mTidyTasksEventsAllItemsLoaded;
   private boolean mModified;


   public TidyTaskEVO() {}

   public TidyTaskEVO(int newTidyTaskId, String newVisId, String newDescription, int newVersionNum, Collection newTidyTasksEvents) {
      this.mTidyTaskId = newTidyTaskId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mVersionNum = newVersionNum;
      this.setTidyTasksEvents(newTidyTasksEvents);
   }

   public void setTidyTasksEvents(Collection<TidyTaskLinkEVO> items) {
      if(items != null) {
         if(this.mTidyTasksEvents == null) {
            this.mTidyTasksEvents = new HashMap();
         } else {
            this.mTidyTasksEvents.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            TidyTaskLinkEVO child = (TidyTaskLinkEVO)i$.next();
            this.mTidyTasksEvents.put(child.getPK(), child);
         }
      } else {
         this.mTidyTasksEvents = null;
      }

   }

   public TidyTaskPK getPK() {
      if(this.mPK == null) {
         this.mPK = new TidyTaskPK(this.mTidyTaskId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getTidyTaskId() {
      return this.mTidyTaskId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
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

   public void setTidyTaskId(int newTidyTaskId) {
      if(this.mTidyTaskId != newTidyTaskId) {
         this.mModified = true;
         this.mTidyTaskId = newTidyTaskId;
         this.mPK = null;
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

   public void setDetails(TidyTaskEVO newDetails) {
      this.setTidyTaskId(newDetails.getTidyTaskId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public TidyTaskEVO deepClone() {
      TidyTaskEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (TidyTaskEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mTidyTaskId > 0) {
         newKey = true;
         this.mTidyTaskId = 0;
      } else if(this.mTidyTaskId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      TidyTaskLinkEVO item;
      if(this.mTidyTasksEvents != null) {
         for(Iterator iter = (new ArrayList(this.mTidyTasksEvents.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (TidyTaskLinkEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mTidyTaskId < 1) {
         returnCount = startCount + 1;
      }

      TidyTaskLinkEVO item;
      if(this.mTidyTasksEvents != null) {
         for(Iterator iter = this.mTidyTasksEvents.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (TidyTaskLinkEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mTidyTaskId < 1) {
         this.mTidyTaskId = startKey;
         nextKey = startKey + 1;
      }

      TidyTaskLinkEVO item;
      if(this.mTidyTasksEvents != null) {
         for(Iterator iter = (new ArrayList(this.mTidyTasksEvents.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (TidyTaskLinkEVO)iter.next();
            this.changeKey(item, this.mTidyTaskId, item.getTidyTaskLinkId());
         }
      }

      return nextKey;
   }

   public Collection<TidyTaskLinkEVO> getTidyTasksEvents() {
      return this.mTidyTasksEvents != null?this.mTidyTasksEvents.values():null;
   }

   public Map<TidyTaskLinkPK, TidyTaskLinkEVO> getTidyTasksEventsMap() {
      return this.mTidyTasksEvents;
   }

   public void loadTidyTasksEventsItem(TidyTaskLinkEVO newItem) {
      if(this.mTidyTasksEvents == null) {
         this.mTidyTasksEvents = new HashMap();
      }

      this.mTidyTasksEvents.put(newItem.getPK(), newItem);
   }

   public void addTidyTasksEventsItem(TidyTaskLinkEVO newItem) {
      if(this.mTidyTasksEvents == null) {
         this.mTidyTasksEvents = new HashMap();
      }

      TidyTaskLinkPK newPK = newItem.getPK();
      if(this.getTidyTasksEventsItem(newPK) != null) {
         throw new RuntimeException("addTidyTasksEventsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mTidyTasksEvents.put(newPK, newItem);
      }
   }

   public void changeTidyTasksEventsItem(TidyTaskLinkEVO changedItem) {
      if(this.mTidyTasksEvents == null) {
         throw new RuntimeException("changeTidyTasksEventsItem: no items in collection");
      } else {
         TidyTaskLinkPK changedPK = changedItem.getPK();
         TidyTaskLinkEVO listItem = this.getTidyTasksEventsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeTidyTasksEventsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteTidyTasksEventsItem(TidyTaskLinkPK removePK) {
      TidyTaskLinkEVO listItem = this.getTidyTasksEventsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeTidyTasksEventsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public TidyTaskLinkEVO getTidyTasksEventsItem(TidyTaskLinkPK pk) {
      return (TidyTaskLinkEVO)this.mTidyTasksEvents.get(pk);
   }

   public TidyTaskLinkEVO getTidyTasksEventsItem() {
      if(this.mTidyTasksEvents.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mTidyTasksEvents.size());
      } else {
         Iterator iter = this.mTidyTasksEvents.values().iterator();
         return (TidyTaskLinkEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public TidyTaskRef getEntityRef() {
      return new TidyTaskRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mTidyTasksEventsAllItemsLoaded = true;
      if(this.mTidyTasksEvents == null) {
         this.mTidyTasksEvents = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("TidyTaskId=");
      sb.append(String.valueOf(this.mTidyTaskId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
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

      sb.append("TidyTask: ");
      sb.append(this.toString());
      if(this.mTidyTasksEventsAllItemsLoaded || this.mTidyTasksEvents != null) {
         sb.delete(indent, sb.length());
         sb.append(" - TidyTasksEvents: allItemsLoaded=");
         sb.append(String.valueOf(this.mTidyTasksEventsAllItemsLoaded));
         sb.append(" items=");
         if(this.mTidyTasksEvents == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mTidyTasksEvents.size()));
         }
      }

      if(this.mTidyTasksEvents != null) {
         Iterator var5 = this.mTidyTasksEvents.values().iterator();

         while(var5.hasNext()) {
            TidyTaskLinkEVO listItem = (TidyTaskLinkEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(TidyTaskLinkEVO child, int newTidyTaskId, int newTidyTaskLinkId) {
      if(this.getTidyTasksEventsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mTidyTasksEvents.remove(child.getPK());
         child.setTidyTaskId(newTidyTaskId);
         child.setTidyTaskLinkId(newTidyTaskLinkId);
         this.mTidyTasksEvents.put(child.getPK(), child);
      }
   }

   public void setTidyTasksEventsAllItemsLoaded(boolean allItemsLoaded) {
      this.mTidyTasksEventsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isTidyTasksEventsAllItemsLoaded() {
      return this.mTidyTasksEventsAllItemsLoaded;
   }
}
