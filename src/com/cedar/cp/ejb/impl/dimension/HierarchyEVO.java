// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:24
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.dto.dimension.AugHierarchyElementPK;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.ejb.impl.dimension.AugHierarchyElementEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedEVO;
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

public class HierarchyEVO implements Serializable {

   private transient HierarchyPK mPK;
   private int mHierarchyId;
   private int mDimensionId;
   private String mVisId;
   private String mDescription;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<HierarchyElementPK, HierarchyElementEVO> mHierarchyElements;
   protected boolean mHierarchyElementsAllItemsLoaded;
   private Map<AugHierarchyElementPK, AugHierarchyElementEVO> mAugHierarchyElements;
   protected boolean mAugHierarchyElementsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public HierarchyEVO() {}

   public HierarchyEVO(int newHierarchyId, int newDimensionId, String newVisId, String newDescription, int newVersionNum, Collection newHierarchyElements, Collection newAugHierarchyElements) {
      this.mHierarchyId = newHierarchyId;
      this.mDimensionId = newDimensionId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mVersionNum = newVersionNum;
      this.setHierarchyElements(newHierarchyElements);
      this.setAugHierarchyElements(newAugHierarchyElements);
   }

   public void setHierarchyElements(Collection<HierarchyElementEVO> items) {
      if(items != null) {
         if(this.mHierarchyElements == null) {
            this.mHierarchyElements = new HashMap();
         } else {
            this.mHierarchyElements.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            HierarchyElementEVO child = (HierarchyElementEVO)i$.next();
            this.mHierarchyElements.put(child.getPK(), child);
         }
      } else {
         this.mHierarchyElements = null;
      }

   }

   public void setAugHierarchyElements(Collection<AugHierarchyElementEVO> items) {
      if(items != null) {
         if(this.mAugHierarchyElements == null) {
            this.mAugHierarchyElements = new HashMap();
         } else {
            this.mAugHierarchyElements.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            AugHierarchyElementEVO child = (AugHierarchyElementEVO)i$.next();
            this.mAugHierarchyElements.put(child.getPK(), child);
         }
      } else {
         this.mAugHierarchyElements = null;
      }

   }

   public HierarchyPK getPK() {
      if(this.mPK == null) {
         this.mPK = new HierarchyPK(this.mHierarchyId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getHierarchyId() {
      return this.mHierarchyId;
   }

   public int getDimensionId() {
      return this.mDimensionId;
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

   public void setHierarchyId(int newHierarchyId) {
      if(this.mHierarchyId != newHierarchyId) {
         this.mModified = true;
         this.mHierarchyId = newHierarchyId;
         this.mPK = null;
      }
   }

   public void setDimensionId(int newDimensionId) {
      if(this.mDimensionId != newDimensionId) {
         this.mModified = true;
         this.mDimensionId = newDimensionId;
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

   public void setDetails(HierarchyEVO newDetails) {
      this.setHierarchyId(newDetails.getHierarchyId());
      this.setDimensionId(newDetails.getDimensionId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public HierarchyEVO deepClone() {
      HierarchyEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (HierarchyEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(DimensionEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mHierarchyId > 0) {
         newKey = true;
         if(parent == null) {
            this.setHierarchyId(-this.mHierarchyId);
         } else {
            parent.changeKey(this, -this.mHierarchyId);
         }
      } else if(this.mHierarchyId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      Iterator iter;
      HierarchyElementEVO item;
      if(this.mHierarchyElements != null) {
         for(iter = (new ArrayList(this.mHierarchyElements.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (HierarchyElementEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      AugHierarchyElementEVO item1;
      if(this.mAugHierarchyElements != null) {
         for(iter = (new ArrayList(this.mAugHierarchyElements.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (AugHierarchyElementEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mHierarchyId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      HierarchyElementEVO item;
      if(this.mHierarchyElements != null) {
         for(iter = this.mHierarchyElements.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (HierarchyElementEVO)iter.next();
         }
      }

      AugHierarchyElementEVO item1;
      if(this.mAugHierarchyElements != null) {
         for(iter = this.mAugHierarchyElements.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (AugHierarchyElementEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(DimensionEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mHierarchyId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      Iterator iter;
      if(this.mHierarchyElements != null) {
         iter = (new ArrayList(this.mHierarchyElements.values())).iterator();

         while(iter.hasNext()) {
            HierarchyElementEVO item = (HierarchyElementEVO)iter.next();
            item.setHierarchyId(this.mHierarchyId);
            HierarchyElementPK origPK = item.getPK();
            nextKey = item.assignNextKey(this, nextKey);
            HierarchyElementPK newPK = item.getPK();
            if(!origPK.equals(newPK)) {
               this.handleNewHierarchyElementKey(origPK, newPK);
            }
         }
      }

      if(this.mAugHierarchyElements != null) {
         iter = (new ArrayList(this.mAugHierarchyElements.values())).iterator();

         while(iter.hasNext()) {
            AugHierarchyElementEVO item1 = (AugHierarchyElementEVO)iter.next();
            item1.setHierarchyId(this.mHierarchyId);
            AugHierarchyElementPK origPK1 = item1.getPK();
            nextKey = item1.assignNextKey(this, nextKey);
            AugHierarchyElementPK newPK1 = item1.getPK();
            if(!origPK1.equals(newPK1)) {
               this.handleNewAugHierarchyElementKey(origPK1, newPK1);
            }
         }
      }

      return nextKey;
   }

   private void handleNewHierarchyElementKey(HierarchyElementPK origPK, HierarchyElementPK newPK) {
      this.changeHierarchyElementKey(origPK.getHierarchyElementId(), newPK.getHierarchyElementId());
   }

   private void handleNewAugHierarchyElementKey(AugHierarchyElementPK origPK, AugHierarchyElementPK newPK) {
      this.changeAugHierarchyElementKey(origPK.getHierarchyElementId(), newPK.getHierarchyElementId());
   }

   public Collection<HierarchyElementEVO> getHierarchyElements() {
      return this.mHierarchyElements != null?this.mHierarchyElements.values():null;
   }

   public Map<HierarchyElementPK, HierarchyElementEVO> getHierarchyElementsMap() {
      return this.mHierarchyElements;
   }

   public void loadHierarchyElementsItem(HierarchyElementEVO newItem) {
      if(this.mHierarchyElements == null) {
         this.mHierarchyElements = new HashMap();
      }

      this.mHierarchyElements.put(newItem.getPK(), newItem);
   }

   public void addHierarchyElementsItem(HierarchyElementEVO newItem) {
      if(this.mHierarchyElements == null) {
         this.mHierarchyElements = new HashMap();
      }

      HierarchyElementPK newPK = newItem.getPK();
      if(this.getHierarchyElementsItem(newPK) != null) {
         throw new RuntimeException("addHierarchyElementsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mHierarchyElements.put(newPK, newItem);
      }
   }

   public void changeHierarchyElementsItem(HierarchyElementEVO changedItem) {
      if(this.mHierarchyElements == null) {
         throw new RuntimeException("changeHierarchyElementsItem: no items in collection");
      } else {
         HierarchyElementPK changedPK = changedItem.getPK();
         HierarchyElementEVO listItem = this.getHierarchyElementsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeHierarchyElementsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteHierarchyElementsItem(HierarchyElementPK removePK) {
      HierarchyElementEVO listItem = this.getHierarchyElementsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeHierarchyElementsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public HierarchyElementEVO getHierarchyElementsItem(HierarchyElementPK pk) {
      return (HierarchyElementEVO)this.mHierarchyElements.get(pk);
   }

   public HierarchyElementEVO getHierarchyElementsItem() {
      if(this.mHierarchyElements.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mHierarchyElements.size());
      } else {
         Iterator iter = this.mHierarchyElements.values().iterator();
         return (HierarchyElementEVO)iter.next();
      }
   }

   public Collection<AugHierarchyElementEVO> getAugHierarchyElements() {
      return this.mAugHierarchyElements != null?this.mAugHierarchyElements.values():null;
   }

   public Map<AugHierarchyElementPK, AugHierarchyElementEVO> getAugHierarchyElementsMap() {
      return this.mAugHierarchyElements;
   }

   public void loadAugHierarchyElementsItem(AugHierarchyElementEVO newItem) {
      if(this.mAugHierarchyElements == null) {
         this.mAugHierarchyElements = new HashMap();
      }

      this.mAugHierarchyElements.put(newItem.getPK(), newItem);
   }

   public void addAugHierarchyElementsItem(AugHierarchyElementEVO newItem) {
      if(this.mAugHierarchyElements == null) {
         this.mAugHierarchyElements = new HashMap();
      }

      AugHierarchyElementPK newPK = newItem.getPK();
      if(this.getAugHierarchyElementsItem(newPK) != null) {
         throw new RuntimeException("addAugHierarchyElementsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mAugHierarchyElements.put(newPK, newItem);
      }
   }

   public void changeAugHierarchyElementsItem(AugHierarchyElementEVO changedItem) {
      if(this.mAugHierarchyElements == null) {
         throw new RuntimeException("changeAugHierarchyElementsItem: no items in collection");
      } else {
         AugHierarchyElementPK changedPK = changedItem.getPK();
         AugHierarchyElementEVO listItem = this.getAugHierarchyElementsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeAugHierarchyElementsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteAugHierarchyElementsItem(AugHierarchyElementPK removePK) {
      AugHierarchyElementEVO listItem = this.getAugHierarchyElementsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeAugHierarchyElementsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public AugHierarchyElementEVO getAugHierarchyElementsItem(AugHierarchyElementPK pk) {
      return (AugHierarchyElementEVO)this.mAugHierarchyElements.get(pk);
   }

   public AugHierarchyElementEVO getAugHierarchyElementsItem() {
      if(this.mAugHierarchyElements.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mAugHierarchyElements.size());
      } else {
         Iterator iter = this.mAugHierarchyElements.values().iterator();
         return (AugHierarchyElementEVO)iter.next();
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

   public HierarchyRef getEntityRef(DimensionEVO evoDimension) {
      return new HierarchyRefImpl(new HierarchyCK(evoDimension.getPK(), this.getPK()), this.mVisId);
   }

   public HierarchyCK getCK(DimensionEVO evoDimension) {
      return new HierarchyCK(evoDimension.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mHierarchyElementsAllItemsLoaded = true;
      if(this.mHierarchyElements == null) {
         this.mHierarchyElements = new HashMap();
      } else {
         Iterator i$ = this.mHierarchyElements.values().iterator();

         while(i$.hasNext()) {
            HierarchyElementEVO child = (HierarchyElementEVO)i$.next();
            child.postCreateInit();
         }
      }

      this.mAugHierarchyElementsAllItemsLoaded = true;
      if(this.mAugHierarchyElements == null) {
         this.mAugHierarchyElements = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("HierarchyId=");
      sb.append(String.valueOf(this.mHierarchyId));
      sb.append(' ');
      sb.append("DimensionId=");
      sb.append(String.valueOf(this.mDimensionId));
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

      sb.append("Hierarchy: ");
      sb.append(this.toString());
      if(this.mHierarchyElementsAllItemsLoaded || this.mHierarchyElements != null) {
         sb.delete(indent, sb.length());
         sb.append(" - HierarchyElements: allItemsLoaded=");
         sb.append(String.valueOf(this.mHierarchyElementsAllItemsLoaded));
         sb.append(" items=");
         if(this.mHierarchyElements == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mHierarchyElements.size()));
         }
      }

      if(this.mAugHierarchyElementsAllItemsLoaded || this.mAugHierarchyElements != null) {
         sb.delete(indent, sb.length());
         sb.append(" - AugHierarchyElements: allItemsLoaded=");
         sb.append(String.valueOf(this.mAugHierarchyElementsAllItemsLoaded));
         sb.append(" items=");
         if(this.mAugHierarchyElements == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mAugHierarchyElements.size()));
         }
      }

      Iterator var5;
      if(this.mHierarchyElements != null) {
         var5 = this.mHierarchyElements.values().iterator();

         while(var5.hasNext()) {
            HierarchyElementEVO listItem = (HierarchyElementEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mAugHierarchyElements != null) {
         var5 = this.mAugHierarchyElements.values().iterator();

         while(var5.hasNext()) {
            AugHierarchyElementEVO var6 = (AugHierarchyElementEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(HierarchyElementEVO child, int newHierarchyElementId) {
      if(this.getHierarchyElementsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mHierarchyElements.remove(child.getPK());
         child.setHierarchyElementId(newHierarchyElementId);
         this.mHierarchyElements.put(child.getPK(), child);
      }
   }

   public void changeKey(AugHierarchyElementEVO child, int newHierarchyElementId) {
      if(this.getAugHierarchyElementsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mAugHierarchyElements.remove(child.getPK());
         child.setHierarchyElementId(newHierarchyElementId);
         this.mAugHierarchyElements.put(child.getPK(), child);
      }
   }

   public void changeHierarchyElementKey(int oldId, int newId) {
      Iterator iter = this.mHierarchyElements.values().iterator();

      while(iter.hasNext()) {
         HierarchyElementEVO he = (HierarchyElementEVO)iter.next();
         if(he.getParentId() == oldId && he.getParentId() != 0) {
            he.setParentId(newId);
         }

         if(he.getAugParentId() != null && he.getAugParentId().intValue() == oldId) {
            he.setAugParentId(new Integer(newId));
         }
      }

   }

   public void changeAugHierarchyElementKey(int oldId, int newId) {
      Iterator iter = this.mHierarchyElements.values().iterator();

      while(iter.hasNext()) {
         HierarchyElementEVO ahe = (HierarchyElementEVO)iter.next();
         if(ahe.getAugParentId() != null && ahe.getAugParentId().intValue() == oldId) {
            ahe.setAugParentId(new Integer(newId));
         }

         if(ahe.getFeederElements() != null) {
            Iterator hefIter = ahe.getFeederElements().iterator();

            while(hefIter.hasNext()) {
               HierarchyElementFeedEVO hefEVO = (HierarchyElementFeedEVO)hefIter.next();
               if(hefEVO.getAugHierarchyElementId() != null && hefEVO.getAugHierarchyElementId().intValue() == oldId) {
                  hefEVO.setAugHierarchyElementId(new Integer(newId));
               }
            }
         }
      }

      iter = this.mAugHierarchyElements.values().iterator();

      while(iter.hasNext()) {
         AugHierarchyElementEVO ahe1 = (AugHierarchyElementEVO)iter.next();
         if(ahe1.getParentId() == oldId && ahe1.getParentId() != 0) {
            ahe1.setParentId(newId);
         }
      }

   }

   public void setModified(boolean b) {
      this.mModified = b;
   }

   public void setHierarchyElementsAllItemsLoaded(boolean allItemsLoaded) {
      this.mHierarchyElementsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isHierarchyElementsAllItemsLoaded() {
      return this.mHierarchyElementsAllItemsLoaded;
   }

   public void setAugHierarchyElementsAllItemsLoaded(boolean allItemsLoaded) {
      this.mAugHierarchyElementsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isAugHierarchyElementsAllItemsLoaded() {
      return this.mAugHierarchyElementsAllItemsLoaded;
   }
}
