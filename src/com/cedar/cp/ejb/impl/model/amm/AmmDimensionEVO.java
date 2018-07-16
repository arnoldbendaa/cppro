// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.amm;

import com.cedar.cp.api.model.amm.AmmDimensionRef;
import com.cedar.cp.dto.model.amm.AmmDimensionCK;
import com.cedar.cp.dto.model.amm.AmmDimensionElementPK;
import com.cedar.cp.dto.model.amm.AmmDimensionPK;
import com.cedar.cp.dto.model.amm.AmmDimensionRefImpl;
import com.cedar.cp.ejb.impl.model.amm.AmmDimensionElementEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmModelEVO;
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

public class AmmDimensionEVO implements Serializable {

   private transient AmmDimensionPK mPK;
   private int mAmmDimensionId;
   private int mAmmModelId;
   private Integer mDimensionId;
   private Integer mSrcDimensionId;
   private Integer mSrcHierarchyId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<AmmDimensionElementPK, AmmDimensionElementEVO> mAmmDimElements;
   protected boolean mAmmDimElementsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public AmmDimensionEVO() {}

   public AmmDimensionEVO(int newAmmDimensionId, int newAmmModelId, Integer newDimensionId, Integer newSrcDimensionId, Integer newSrcHierarchyId, Collection newAmmDimElements) {
      this.mAmmDimensionId = newAmmDimensionId;
      this.mAmmModelId = newAmmModelId;
      this.mDimensionId = newDimensionId;
      this.mSrcDimensionId = newSrcDimensionId;
      this.mSrcHierarchyId = newSrcHierarchyId;
      this.setAmmDimElements(newAmmDimElements);
   }

   public void setAmmDimElements(Collection<AmmDimensionElementEVO> items) {
      if(items != null) {
         if(this.mAmmDimElements == null) {
            this.mAmmDimElements = new HashMap();
         } else {
            this.mAmmDimElements.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            AmmDimensionElementEVO child = (AmmDimensionElementEVO)i$.next();
            this.mAmmDimElements.put(child.getPK(), child);
         }
      } else {
         this.mAmmDimElements = null;
      }

   }

   public AmmDimensionPK getPK() {
      if(this.mPK == null) {
         this.mPK = new AmmDimensionPK(this.mAmmDimensionId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getAmmDimensionId() {
      return this.mAmmDimensionId;
   }

   public int getAmmModelId() {
      return this.mAmmModelId;
   }

   public Integer getDimensionId() {
      return this.mDimensionId;
   }

   public Integer getSrcDimensionId() {
      return this.mSrcDimensionId;
   }

   public Integer getSrcHierarchyId() {
      return this.mSrcHierarchyId;
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

   public void setAmmDimensionId(int newAmmDimensionId) {
      if(this.mAmmDimensionId != newAmmDimensionId) {
         this.mModified = true;
         this.mAmmDimensionId = newAmmDimensionId;
         this.mPK = null;
      }
   }

   public void setAmmModelId(int newAmmModelId) {
      if(this.mAmmModelId != newAmmModelId) {
         this.mModified = true;
         this.mAmmModelId = newAmmModelId;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setDimensionId(Integer newDimensionId) {
      if(this.mDimensionId != null && newDimensionId == null || this.mDimensionId == null && newDimensionId != null || this.mDimensionId != null && newDimensionId != null && !this.mDimensionId.equals(newDimensionId)) {
         this.mDimensionId = newDimensionId;
         this.mModified = true;
      }

   }

   public void setSrcDimensionId(Integer newSrcDimensionId) {
      if(this.mSrcDimensionId != null && newSrcDimensionId == null || this.mSrcDimensionId == null && newSrcDimensionId != null || this.mSrcDimensionId != null && newSrcDimensionId != null && !this.mSrcDimensionId.equals(newSrcDimensionId)) {
         this.mSrcDimensionId = newSrcDimensionId;
         this.mModified = true;
      }

   }

   public void setSrcHierarchyId(Integer newSrcHierarchyId) {
      if(this.mSrcHierarchyId != null && newSrcHierarchyId == null || this.mSrcHierarchyId == null && newSrcHierarchyId != null || this.mSrcHierarchyId != null && newSrcHierarchyId != null && !this.mSrcHierarchyId.equals(newSrcHierarchyId)) {
         this.mSrcHierarchyId = newSrcHierarchyId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(AmmDimensionEVO newDetails) {
      this.setAmmDimensionId(newDetails.getAmmDimensionId());
      this.setAmmModelId(newDetails.getAmmModelId());
      this.setDimensionId(newDetails.getDimensionId());
      this.setSrcDimensionId(newDetails.getSrcDimensionId());
      this.setSrcHierarchyId(newDetails.getSrcHierarchyId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public AmmDimensionEVO deepClone() {
      AmmDimensionEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (AmmDimensionEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(AmmModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mAmmDimensionId > 0) {
         newKey = true;
         if(parent == null) {
            this.setAmmDimensionId(-this.mAmmDimensionId);
         } else {
            parent.changeKey(this, -this.mAmmDimensionId);
         }
      } else if(this.mAmmDimensionId < 1) {
         newKey = true;
      }

      AmmDimensionElementEVO item;
      if(this.mAmmDimElements != null) {
         for(Iterator iter = (new ArrayList(this.mAmmDimElements.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (AmmDimensionElementEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mAmmDimensionId < 1) {
         returnCount = startCount + 1;
      }

      AmmDimensionElementEVO item;
      if(this.mAmmDimElements != null) {
         for(Iterator iter = this.mAmmDimElements.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (AmmDimensionElementEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(AmmModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mAmmDimensionId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      AmmDimensionElementEVO item;
      if(this.mAmmDimElements != null) {
         for(Iterator iter = (new ArrayList(this.mAmmDimElements.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (AmmDimensionElementEVO)iter.next();
            item.setAmmDimensionId(this.mAmmDimensionId);
         }
      }

      return nextKey;
   }

   public Collection<AmmDimensionElementEVO> getAmmDimElements() {
      return this.mAmmDimElements != null?this.mAmmDimElements.values():null;
   }

   public Map<AmmDimensionElementPK, AmmDimensionElementEVO> getAmmDimElementsMap() {
      return this.mAmmDimElements;
   }

   public void loadAmmDimElementsItem(AmmDimensionElementEVO newItem) {
      if(this.mAmmDimElements == null) {
         this.mAmmDimElements = new HashMap();
      }

      this.mAmmDimElements.put(newItem.getPK(), newItem);
   }

   public void addAmmDimElementsItem(AmmDimensionElementEVO newItem) {
      if(this.mAmmDimElements == null) {
         this.mAmmDimElements = new HashMap();
      }

      AmmDimensionElementPK newPK = newItem.getPK();
      if(this.getAmmDimElementsItem(newPK) != null) {
         throw new RuntimeException("addAmmDimElementsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mAmmDimElements.put(newPK, newItem);
      }
   }

   public void changeAmmDimElementsItem(AmmDimensionElementEVO changedItem) {
      if(this.mAmmDimElements == null) {
         throw new RuntimeException("changeAmmDimElementsItem: no items in collection");
      } else {
         AmmDimensionElementPK changedPK = changedItem.getPK();
         AmmDimensionElementEVO listItem = this.getAmmDimElementsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeAmmDimElementsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteAmmDimElementsItem(AmmDimensionElementPK removePK) {
      AmmDimensionElementEVO listItem = this.getAmmDimElementsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeAmmDimElementsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public AmmDimensionElementEVO getAmmDimElementsItem(AmmDimensionElementPK pk) {
      return (AmmDimensionElementEVO)this.mAmmDimElements.get(pk);
   }

   public AmmDimensionElementEVO getAmmDimElementsItem() {
      if(this.mAmmDimElements.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mAmmDimElements.size());
      } else {
         Iterator iter = this.mAmmDimElements.values().iterator();
         return (AmmDimensionElementEVO)iter.next();
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

   public AmmDimensionRef getEntityRef(AmmModelEVO evoAmmModel, String entityText) {
      return new AmmDimensionRefImpl(new AmmDimensionCK(evoAmmModel.getPK(), this.getPK()), entityText);
   }

   public AmmDimensionCK getCK(AmmModelEVO evoAmmModel) {
      return new AmmDimensionCK(evoAmmModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mAmmDimElementsAllItemsLoaded = true;
      if(this.mAmmDimElements == null) {
         this.mAmmDimElements = new HashMap();
      } else {
         Iterator i$ = this.mAmmDimElements.values().iterator();

         while(i$.hasNext()) {
            AmmDimensionElementEVO child = (AmmDimensionElementEVO)i$.next();
            child.postCreateInit();
         }
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("AmmDimensionId=");
      sb.append(String.valueOf(this.mAmmDimensionId));
      sb.append(' ');
      sb.append("AmmModelId=");
      sb.append(String.valueOf(this.mAmmModelId));
      sb.append(' ');
      sb.append("DimensionId=");
      sb.append(String.valueOf(this.mDimensionId));
      sb.append(' ');
      sb.append("SrcDimensionId=");
      sb.append(String.valueOf(this.mSrcDimensionId));
      sb.append(' ');
      sb.append("SrcHierarchyId=");
      sb.append(String.valueOf(this.mSrcHierarchyId));
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

      sb.append("AmmDimension: ");
      sb.append(this.toString());
      if(this.mAmmDimElementsAllItemsLoaded || this.mAmmDimElements != null) {
         sb.delete(indent, sb.length());
         sb.append(" - AmmDimElements: allItemsLoaded=");
         sb.append(String.valueOf(this.mAmmDimElementsAllItemsLoaded));
         sb.append(" items=");
         if(this.mAmmDimElements == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mAmmDimElements.size()));
         }
      }

      if(this.mAmmDimElements != null) {
         Iterator var5 = this.mAmmDimElements.values().iterator();

         while(var5.hasNext()) {
            AmmDimensionElementEVO listItem = (AmmDimensionElementEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(AmmDimensionElementEVO child, int newAmmDimensionElementId) {
      if(this.getAmmDimElementsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mAmmDimElements.remove(child.getPK());
         child.setAmmDimensionElementId(newAmmDimensionElementId);
         this.mAmmDimElements.put(child.getPK(), child);
      }
   }

   public void setAmmDimElementsAllItemsLoaded(boolean allItemsLoaded) {
      this.mAmmDimElementsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isAmmDimElementsAllItemsLoaded() {
      return this.mAmmDimElementsAllItemsLoaded;
   }
}
