// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:44
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.mapping;

import com.cedar.cp.api.model.mapping.MappedCalendarYearRef;
import com.cedar.cp.dto.model.mapping.MappedCalendarElementPK;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearCK;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearPK;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearRefImpl;
import com.cedar.cp.ejb.impl.model.mapping.MappedCalendarElementEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEVO;
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

public class MappedCalendarYearEVO implements Serializable {

   private transient MappedCalendarYearPK mPK;
   private int mMappedCalendarYearId;
   private int mMappedModelId;
   private int mYear;
   private String mYearVisId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<MappedCalendarElementPK, MappedCalendarElementEVO> mMappedCalendarElements;
   protected boolean mMappedCalendarElementsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public MappedCalendarYearEVO() {}

   public MappedCalendarYearEVO(int newMappedCalendarYearId, int newMappedModelId, int newYear, String newYearVisId, Collection newMappedCalendarElements) {
      this.mMappedCalendarYearId = newMappedCalendarYearId;
      this.mMappedModelId = newMappedModelId;
      this.mYear = newYear;
      this.mYearVisId = newYearVisId;
      this.setMappedCalendarElements(newMappedCalendarElements);
   }

   public void setMappedCalendarElements(Collection<MappedCalendarElementEVO> items) {
      if(items != null) {
         if(this.mMappedCalendarElements == null) {
            this.mMappedCalendarElements = new HashMap();
         } else {
            this.mMappedCalendarElements.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            MappedCalendarElementEVO child = (MappedCalendarElementEVO)i$.next();
            this.mMappedCalendarElements.put(child.getPK(), child);
         }
      } else {
         this.mMappedCalendarElements = null;
      }

   }

   public MappedCalendarYearPK getPK() {
      if(this.mPK == null) {
         this.mPK = new MappedCalendarYearPK(this.mMappedCalendarYearId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getMappedCalendarYearId() {
      return this.mMappedCalendarYearId;
   }

   public int getMappedModelId() {
      return this.mMappedModelId;
   }

   public int getYear() {
      return this.mYear;
   }

   public String getYearVisId() {
      return this.mYearVisId;
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

   public void setMappedCalendarYearId(int newMappedCalendarYearId) {
      if(this.mMappedCalendarYearId != newMappedCalendarYearId) {
         this.mModified = true;
         this.mMappedCalendarYearId = newMappedCalendarYearId;
         this.mPK = null;
      }
   }

   public void setMappedModelId(int newMappedModelId) {
      if(this.mMappedModelId != newMappedModelId) {
         this.mModified = true;
         this.mMappedModelId = newMappedModelId;
      }
   }

   public void setYear(int newYear) {
      if(this.mYear != newYear) {
         this.mModified = true;
         this.mYear = newYear;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setYearVisId(String newYearVisId) {
      if(this.mYearVisId != null && newYearVisId == null || this.mYearVisId == null && newYearVisId != null || this.mYearVisId != null && newYearVisId != null && !this.mYearVisId.equals(newYearVisId)) {
         this.mYearVisId = newYearVisId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(MappedCalendarYearEVO newDetails) {
      this.setMappedCalendarYearId(newDetails.getMappedCalendarYearId());
      this.setMappedModelId(newDetails.getMappedModelId());
      this.setYear(newDetails.getYear());
      this.setYearVisId(newDetails.getYearVisId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public MappedCalendarYearEVO deepClone() {
      MappedCalendarYearEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (MappedCalendarYearEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(MappedModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mMappedCalendarYearId > 0) {
         newKey = true;
         if(parent == null) {
            this.setMappedCalendarYearId(-this.mMappedCalendarYearId);
         } else {
            parent.changeKey(this, -this.mMappedCalendarYearId);
         }
      } else if(this.mMappedCalendarYearId < 1) {
         newKey = true;
      }

      MappedCalendarElementEVO item;
      if(this.mMappedCalendarElements != null) {
         for(Iterator iter = (new ArrayList(this.mMappedCalendarElements.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (MappedCalendarElementEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mMappedCalendarYearId < 1) {
         returnCount = startCount + 1;
      }

      MappedCalendarElementEVO item;
      if(this.mMappedCalendarElements != null) {
         for(Iterator iter = this.mMappedCalendarElements.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (MappedCalendarElementEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(MappedModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mMappedCalendarYearId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      MappedCalendarElementEVO item;
      if(this.mMappedCalendarElements != null) {
         for(Iterator iter = (new ArrayList(this.mMappedCalendarElements.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (MappedCalendarElementEVO)iter.next();
            item.setMappedCalendarYearId(this.mMappedCalendarYearId);
         }
      }

      return nextKey;
   }

   public Collection<MappedCalendarElementEVO> getMappedCalendarElements() {
      return this.mMappedCalendarElements != null?this.mMappedCalendarElements.values():null;
   }

   public Map<MappedCalendarElementPK, MappedCalendarElementEVO> getMappedCalendarElementsMap() {
      return this.mMappedCalendarElements;
   }

   public void loadMappedCalendarElementsItem(MappedCalendarElementEVO newItem) {
      if(this.mMappedCalendarElements == null) {
         this.mMappedCalendarElements = new HashMap();
      }

      this.mMappedCalendarElements.put(newItem.getPK(), newItem);
   }

   public void addMappedCalendarElementsItem(MappedCalendarElementEVO newItem) {
      if(this.mMappedCalendarElements == null) {
         this.mMappedCalendarElements = new HashMap();
      }

      MappedCalendarElementPK newPK = newItem.getPK();
      if(this.getMappedCalendarElementsItem(newPK) != null) {
         throw new RuntimeException("addMappedCalendarElementsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mMappedCalendarElements.put(newPK, newItem);
      }
   }

   public void changeMappedCalendarElementsItem(MappedCalendarElementEVO changedItem) {
      if(this.mMappedCalendarElements == null) {
         throw new RuntimeException("changeMappedCalendarElementsItem: no items in collection");
      } else {
         MappedCalendarElementPK changedPK = changedItem.getPK();
         MappedCalendarElementEVO listItem = this.getMappedCalendarElementsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeMappedCalendarElementsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteMappedCalendarElementsItem(MappedCalendarElementPK removePK) {
      MappedCalendarElementEVO listItem = this.getMappedCalendarElementsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeMappedCalendarElementsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public MappedCalendarElementEVO getMappedCalendarElementsItem(MappedCalendarElementPK pk) {
      return (MappedCalendarElementEVO)this.mMappedCalendarElements.get(pk);
   }

   public MappedCalendarElementEVO getMappedCalendarElementsItem() {
      if(this.mMappedCalendarElements.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mMappedCalendarElements.size());
      } else {
         Iterator iter = this.mMappedCalendarElements.values().iterator();
         return (MappedCalendarElementEVO)iter.next();
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

   public MappedCalendarYearRef getEntityRef(MappedModelEVO evoMappedModel, String entityText) {
      return new MappedCalendarYearRefImpl(new MappedCalendarYearCK(evoMappedModel.getPK(), this.getPK()), entityText);
   }

   public MappedCalendarYearCK getCK(MappedModelEVO evoMappedModel) {
      return new MappedCalendarYearCK(evoMappedModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mMappedCalendarElementsAllItemsLoaded = true;
      if(this.mMappedCalendarElements == null) {
         this.mMappedCalendarElements = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("MappedCalendarYearId=");
      sb.append(String.valueOf(this.mMappedCalendarYearId));
      sb.append(' ');
      sb.append("MappedModelId=");
      sb.append(String.valueOf(this.mMappedModelId));
      sb.append(' ');
      sb.append("Year=");
      sb.append(String.valueOf(this.mYear));
      sb.append(' ');
      sb.append("YearVisId=");
      sb.append(String.valueOf(this.mYearVisId));
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

      sb.append("MappedCalendarYear: ");
      sb.append(this.toString());
      if(this.mMappedCalendarElementsAllItemsLoaded || this.mMappedCalendarElements != null) {
         sb.delete(indent, sb.length());
         sb.append(" - MappedCalendarElements: allItemsLoaded=");
         sb.append(String.valueOf(this.mMappedCalendarElementsAllItemsLoaded));
         sb.append(" items=");
         if(this.mMappedCalendarElements == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mMappedCalendarElements.size()));
         }
      }

      if(this.mMappedCalendarElements != null) {
         Iterator var5 = this.mMappedCalendarElements.values().iterator();

         while(var5.hasNext()) {
            MappedCalendarElementEVO listItem = (MappedCalendarElementEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(MappedCalendarElementEVO child, int newMappedCalendarElementId) {
      if(this.getMappedCalendarElementsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mMappedCalendarElements.remove(child.getPK());
         child.setMappedCalendarElementId(newMappedCalendarElementId);
         this.mMappedCalendarElements.put(child.getPK(), child);
      }
   }

   public void setMappedCalendarElementsAllItemsLoaded(boolean allItemsLoaded) {
      this.mMappedCalendarElementsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isMappedCalendarElementsAllItemsLoaded() {
      return this.mMappedCalendarElementsAllItemsLoaded;
   }
}
