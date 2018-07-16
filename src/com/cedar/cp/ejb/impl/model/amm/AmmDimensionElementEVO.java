// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.amm;

import com.cedar.cp.api.model.amm.AmmDimensionElementRef;
import com.cedar.cp.dto.model.amm.AmmDimensionElementCK;
import com.cedar.cp.dto.model.amm.AmmDimensionElementPK;
import com.cedar.cp.dto.model.amm.AmmDimensionElementRefImpl;
import com.cedar.cp.dto.model.amm.AmmSrcStructureElementPK;
import com.cedar.cp.ejb.impl.model.amm.AmmDimensionEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmModelEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmSrcStructureElementEVO;
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

public class AmmDimensionElementEVO implements Serializable {

   private transient AmmDimensionElementPK mPK;
   private int mAmmDimensionElementId;
   private int mAmmDimensionId;
   private Integer mDimensionElementId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<AmmSrcStructureElementPK, AmmSrcStructureElementEVO> mAmmSourceElements;
   protected boolean mAmmSourceElementsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public AmmDimensionElementEVO() {}

   public AmmDimensionElementEVO(int newAmmDimensionElementId, int newAmmDimensionId, Integer newDimensionElementId, Collection newAmmSourceElements) {
      this.mAmmDimensionElementId = newAmmDimensionElementId;
      this.mAmmDimensionId = newAmmDimensionId;
      this.mDimensionElementId = newDimensionElementId;
      this.setAmmSourceElements(newAmmSourceElements);
   }

   public void setAmmSourceElements(Collection<AmmSrcStructureElementEVO> items) {
      if(items != null) {
         if(this.mAmmSourceElements == null) {
            this.mAmmSourceElements = new HashMap();
         } else {
            this.mAmmSourceElements.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            AmmSrcStructureElementEVO child = (AmmSrcStructureElementEVO)i$.next();
            this.mAmmSourceElements.put(child.getPK(), child);
         }
      } else {
         this.mAmmSourceElements = null;
      }

   }

   public AmmDimensionElementPK getPK() {
      if(this.mPK == null) {
         this.mPK = new AmmDimensionElementPK(this.mAmmDimensionElementId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getAmmDimensionElementId() {
      return this.mAmmDimensionElementId;
   }

   public int getAmmDimensionId() {
      return this.mAmmDimensionId;
   }

   public Integer getDimensionElementId() {
      return this.mDimensionElementId;
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

   public void setAmmDimensionElementId(int newAmmDimensionElementId) {
      if(this.mAmmDimensionElementId != newAmmDimensionElementId) {
         this.mModified = true;
         this.mAmmDimensionElementId = newAmmDimensionElementId;
         this.mPK = null;
      }
   }

   public void setAmmDimensionId(int newAmmDimensionId) {
      if(this.mAmmDimensionId != newAmmDimensionId) {
         this.mModified = true;
         this.mAmmDimensionId = newAmmDimensionId;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setDimensionElementId(Integer newDimensionElementId) {
      if(this.mDimensionElementId != null && newDimensionElementId == null || this.mDimensionElementId == null && newDimensionElementId != null || this.mDimensionElementId != null && newDimensionElementId != null && !this.mDimensionElementId.equals(newDimensionElementId)) {
         this.mDimensionElementId = newDimensionElementId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(AmmDimensionElementEVO newDetails) {
      this.setAmmDimensionElementId(newDetails.getAmmDimensionElementId());
      this.setAmmDimensionId(newDetails.getAmmDimensionId());
      this.setDimensionElementId(newDetails.getDimensionElementId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public AmmDimensionElementEVO deepClone() {
      AmmDimensionElementEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (AmmDimensionElementEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(AmmDimensionEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mAmmDimensionElementId > 0) {
         newKey = true;
         if(parent == null) {
            this.setAmmDimensionElementId(-this.mAmmDimensionElementId);
         } else {
            parent.changeKey(this, -this.mAmmDimensionElementId);
         }
      } else if(this.mAmmDimensionElementId < 1) {
         newKey = true;
      }

      AmmSrcStructureElementEVO item;
      if(this.mAmmSourceElements != null) {
         for(Iterator iter = (new ArrayList(this.mAmmSourceElements.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (AmmSrcStructureElementEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mAmmDimensionElementId < 1) {
         returnCount = startCount + 1;
      }

      AmmSrcStructureElementEVO item;
      if(this.mAmmSourceElements != null) {
         for(Iterator iter = this.mAmmSourceElements.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (AmmSrcStructureElementEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(AmmDimensionEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mAmmDimensionElementId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      AmmSrcStructureElementEVO item;
      if(this.mAmmSourceElements != null) {
         for(Iterator iter = (new ArrayList(this.mAmmSourceElements.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (AmmSrcStructureElementEVO)iter.next();
            item.setAmmDimensionElementId(this.mAmmDimensionElementId);
         }
      }

      return nextKey;
   }

   public Collection<AmmSrcStructureElementEVO> getAmmSourceElements() {
      return this.mAmmSourceElements != null?this.mAmmSourceElements.values():null;
   }

   public Map<AmmSrcStructureElementPK, AmmSrcStructureElementEVO> getAmmSourceElementsMap() {
      return this.mAmmSourceElements;
   }

   public void loadAmmSourceElementsItem(AmmSrcStructureElementEVO newItem) {
      if(this.mAmmSourceElements == null) {
         this.mAmmSourceElements = new HashMap();
      }

      this.mAmmSourceElements.put(newItem.getPK(), newItem);
   }

   public void addAmmSourceElementsItem(AmmSrcStructureElementEVO newItem) {
      if(this.mAmmSourceElements == null) {
         this.mAmmSourceElements = new HashMap();
      }

      AmmSrcStructureElementPK newPK = newItem.getPK();
      if(this.getAmmSourceElementsItem(newPK) != null) {
         throw new RuntimeException("addAmmSourceElementsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mAmmSourceElements.put(newPK, newItem);
      }
   }

   public void changeAmmSourceElementsItem(AmmSrcStructureElementEVO changedItem) {
      if(this.mAmmSourceElements == null) {
         throw new RuntimeException("changeAmmSourceElementsItem: no items in collection");
      } else {
         AmmSrcStructureElementPK changedPK = changedItem.getPK();
         AmmSrcStructureElementEVO listItem = this.getAmmSourceElementsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeAmmSourceElementsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteAmmSourceElementsItem(AmmSrcStructureElementPK removePK) {
      AmmSrcStructureElementEVO listItem = this.getAmmSourceElementsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeAmmSourceElementsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public AmmSrcStructureElementEVO getAmmSourceElementsItem(AmmSrcStructureElementPK pk) {
      return (AmmSrcStructureElementEVO)this.mAmmSourceElements.get(pk);
   }

   public AmmSrcStructureElementEVO getAmmSourceElementsItem() {
      if(this.mAmmSourceElements.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mAmmSourceElements.size());
      } else {
         Iterator iter = this.mAmmSourceElements.values().iterator();
         return (AmmSrcStructureElementEVO)iter.next();
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

   public AmmDimensionElementRef getEntityRef(AmmModelEVO evoAmmModel, AmmDimensionEVO evoAmmDimension, String entityText) {
      return new AmmDimensionElementRefImpl(new AmmDimensionElementCK(evoAmmModel.getPK(), evoAmmDimension.getPK(), this.getPK()), entityText);
   }

   public AmmDimensionElementCK getCK(AmmModelEVO evoAmmModel, AmmDimensionEVO evoAmmDimension) {
      return new AmmDimensionElementCK(evoAmmModel.getPK(), evoAmmDimension.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mAmmSourceElementsAllItemsLoaded = true;
      if(this.mAmmSourceElements == null) {
         this.mAmmSourceElements = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("AmmDimensionElementId=");
      sb.append(String.valueOf(this.mAmmDimensionElementId));
      sb.append(' ');
      sb.append("AmmDimensionId=");
      sb.append(String.valueOf(this.mAmmDimensionId));
      sb.append(' ');
      sb.append("DimensionElementId=");
      sb.append(String.valueOf(this.mDimensionElementId));
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

      sb.append("AmmDimensionElement: ");
      sb.append(this.toString());
      if(this.mAmmSourceElementsAllItemsLoaded || this.mAmmSourceElements != null) {
         sb.delete(indent, sb.length());
         sb.append(" - AmmSourceElements: allItemsLoaded=");
         sb.append(String.valueOf(this.mAmmSourceElementsAllItemsLoaded));
         sb.append(" items=");
         if(this.mAmmSourceElements == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mAmmSourceElements.size()));
         }
      }

      if(this.mAmmSourceElements != null) {
         Iterator var5 = this.mAmmSourceElements.values().iterator();

         while(var5.hasNext()) {
            AmmSrcStructureElementEVO listItem = (AmmSrcStructureElementEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(AmmSrcStructureElementEVO child, int newAmmSrcStructureElementId) {
      if(this.getAmmSourceElementsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mAmmSourceElements.remove(child.getPK());
         child.setAmmSrcStructureElementId(newAmmSrcStructureElementId);
         this.mAmmSourceElements.put(child.getPK(), child);
      }
   }

   public void setAmmSourceElementsAllItemsLoaded(boolean allItemsLoaded) {
      this.mAmmSourceElementsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isAmmSourceElementsAllItemsLoaded() {
      return this.mAmmSourceElementsAllItemsLoaded;
   }
}
