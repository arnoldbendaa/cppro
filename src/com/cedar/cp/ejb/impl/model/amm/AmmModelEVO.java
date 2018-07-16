// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.amm;

import com.cedar.cp.api.model.amm.AmmModelRef;
import com.cedar.cp.dto.model.amm.AmmDimensionPK;
import com.cedar.cp.dto.model.amm.AmmFinanceCubePK;
import com.cedar.cp.dto.model.amm.AmmModelPK;
import com.cedar.cp.dto.model.amm.AmmModelRefImpl;
import com.cedar.cp.ejb.impl.model.amm.AmmDimensionEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmFinanceCubeEVO;
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

public class AmmModelEVO implements Serializable {

   private transient AmmModelPK mPK;
   private int mAmmModelId;
   private int mModelId;
   private int mSrcModelId;
   private Integer mInvalidatedByTaskId;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<AmmDimensionPK, AmmDimensionEVO> mAmmDimensions;
   protected boolean mAmmDimensionsAllItemsLoaded;
   private Map<AmmFinanceCubePK, AmmFinanceCubeEVO> mAmmFinanceCubes;
   protected boolean mAmmFinanceCubesAllItemsLoaded;
   private boolean mModified;


   public AmmModelEVO() {}

   public AmmModelEVO(int newAmmModelId, int newModelId, int newSrcModelId, Integer newInvalidatedByTaskId, int newVersionNum, Collection newAmmDimensions, Collection newAmmFinanceCubes) {
      this.mAmmModelId = newAmmModelId;
      this.mModelId = newModelId;
      this.mSrcModelId = newSrcModelId;
      this.mInvalidatedByTaskId = newInvalidatedByTaskId;
      this.mVersionNum = newVersionNum;
      this.setAmmDimensions(newAmmDimensions);
      this.setAmmFinanceCubes(newAmmFinanceCubes);
   }

   public void setAmmDimensions(Collection<AmmDimensionEVO> items) {
      if(items != null) {
         if(this.mAmmDimensions == null) {
            this.mAmmDimensions = new HashMap();
         } else {
            this.mAmmDimensions.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            AmmDimensionEVO child = (AmmDimensionEVO)i$.next();
            this.mAmmDimensions.put(child.getPK(), child);
         }
      } else {
         this.mAmmDimensions = null;
      }

   }

   public void setAmmFinanceCubes(Collection<AmmFinanceCubeEVO> items) {
      if(items != null) {
         if(this.mAmmFinanceCubes == null) {
            this.mAmmFinanceCubes = new HashMap();
         } else {
            this.mAmmFinanceCubes.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            AmmFinanceCubeEVO child = (AmmFinanceCubeEVO)i$.next();
            this.mAmmFinanceCubes.put(child.getPK(), child);
         }
      } else {
         this.mAmmFinanceCubes = null;
      }

   }

   public AmmModelPK getPK() {
      if(this.mPK == null) {
         this.mPK = new AmmModelPK(this.mAmmModelId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getAmmModelId() {
      return this.mAmmModelId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getSrcModelId() {
      return this.mSrcModelId;
   }

   public Integer getInvalidatedByTaskId() {
      return this.mInvalidatedByTaskId;
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

   public void setAmmModelId(int newAmmModelId) {
      if(this.mAmmModelId != newAmmModelId) {
         this.mModified = true;
         this.mAmmModelId = newAmmModelId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setSrcModelId(int newSrcModelId) {
      if(this.mSrcModelId != newSrcModelId) {
         this.mModified = true;
         this.mSrcModelId = newSrcModelId;
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

   public void setInvalidatedByTaskId(Integer newInvalidatedByTaskId) {
      if(this.mInvalidatedByTaskId != null && newInvalidatedByTaskId == null || this.mInvalidatedByTaskId == null && newInvalidatedByTaskId != null || this.mInvalidatedByTaskId != null && newInvalidatedByTaskId != null && !this.mInvalidatedByTaskId.equals(newInvalidatedByTaskId)) {
         this.mInvalidatedByTaskId = newInvalidatedByTaskId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(AmmModelEVO newDetails) {
      this.setAmmModelId(newDetails.getAmmModelId());
      this.setModelId(newDetails.getModelId());
      this.setSrcModelId(newDetails.getSrcModelId());
      this.setInvalidatedByTaskId(newDetails.getInvalidatedByTaskId());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public AmmModelEVO deepClone() {
      AmmModelEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (AmmModelEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mAmmModelId > 0) {
         newKey = true;
         this.mAmmModelId = 0;
      } else if(this.mAmmModelId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      Iterator iter;
      AmmDimensionEVO item;
      if(this.mAmmDimensions != null) {
         for(iter = (new ArrayList(this.mAmmDimensions.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (AmmDimensionEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      AmmFinanceCubeEVO item1;
      if(this.mAmmFinanceCubes != null) {
         for(iter = (new ArrayList(this.mAmmFinanceCubes.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (AmmFinanceCubeEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mAmmModelId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      AmmDimensionEVO item;
      if(this.mAmmDimensions != null) {
         for(iter = this.mAmmDimensions.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (AmmDimensionEVO)iter.next();
         }
      }

      AmmFinanceCubeEVO item1;
      if(this.mAmmFinanceCubes != null) {
         for(iter = this.mAmmFinanceCubes.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (AmmFinanceCubeEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mAmmModelId < 1) {
         this.mAmmModelId = startKey;
         nextKey = startKey + 1;
      }

      Iterator iter;
      AmmDimensionEVO item;
      if(this.mAmmDimensions != null) {
         for(iter = (new ArrayList(this.mAmmDimensions.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (AmmDimensionEVO)iter.next();
            item.setAmmModelId(this.mAmmModelId);
         }
      }

      AmmFinanceCubeEVO item1;
      if(this.mAmmFinanceCubes != null) {
         for(iter = (new ArrayList(this.mAmmFinanceCubes.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (AmmFinanceCubeEVO)iter.next();
            item1.setAmmModelId(this.mAmmModelId);
         }
      }

      return nextKey;
   }

   public Collection<AmmDimensionEVO> getAmmDimensions() {
      return this.mAmmDimensions != null?this.mAmmDimensions.values():null;
   }

   public Map<AmmDimensionPK, AmmDimensionEVO> getAmmDimensionsMap() {
      return this.mAmmDimensions;
   }

   public void loadAmmDimensionsItem(AmmDimensionEVO newItem) {
      if(this.mAmmDimensions == null) {
         this.mAmmDimensions = new HashMap();
      }

      this.mAmmDimensions.put(newItem.getPK(), newItem);
   }

   public void addAmmDimensionsItem(AmmDimensionEVO newItem) {
      if(this.mAmmDimensions == null) {
         this.mAmmDimensions = new HashMap();
      }

      AmmDimensionPK newPK = newItem.getPK();
      if(this.getAmmDimensionsItem(newPK) != null) {
         throw new RuntimeException("addAmmDimensionsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mAmmDimensions.put(newPK, newItem);
      }
   }

   public void changeAmmDimensionsItem(AmmDimensionEVO changedItem) {
      if(this.mAmmDimensions == null) {
         throw new RuntimeException("changeAmmDimensionsItem: no items in collection");
      } else {
         AmmDimensionPK changedPK = changedItem.getPK();
         AmmDimensionEVO listItem = this.getAmmDimensionsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeAmmDimensionsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteAmmDimensionsItem(AmmDimensionPK removePK) {
      AmmDimensionEVO listItem = this.getAmmDimensionsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeAmmDimensionsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public AmmDimensionEVO getAmmDimensionsItem(AmmDimensionPK pk) {
      return (AmmDimensionEVO)this.mAmmDimensions.get(pk);
   }

   public AmmDimensionEVO getAmmDimensionsItem() {
      if(this.mAmmDimensions.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mAmmDimensions.size());
      } else {
         Iterator iter = this.mAmmDimensions.values().iterator();
         return (AmmDimensionEVO)iter.next();
      }
   }

   public Collection<AmmFinanceCubeEVO> getAmmFinanceCubes() {
      return this.mAmmFinanceCubes != null?this.mAmmFinanceCubes.values():null;
   }

   public Map<AmmFinanceCubePK, AmmFinanceCubeEVO> getAmmFinanceCubesMap() {
      return this.mAmmFinanceCubes;
   }

   public void loadAmmFinanceCubesItem(AmmFinanceCubeEVO newItem) {
      if(this.mAmmFinanceCubes == null) {
         this.mAmmFinanceCubes = new HashMap();
      }

      this.mAmmFinanceCubes.put(newItem.getPK(), newItem);
   }

   public void addAmmFinanceCubesItem(AmmFinanceCubeEVO newItem) {
      if(this.mAmmFinanceCubes == null) {
         this.mAmmFinanceCubes = new HashMap();
      }

      AmmFinanceCubePK newPK = newItem.getPK();
      if(this.getAmmFinanceCubesItem(newPK) != null) {
         throw new RuntimeException("addAmmFinanceCubesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mAmmFinanceCubes.put(newPK, newItem);
      }
   }

   public void changeAmmFinanceCubesItem(AmmFinanceCubeEVO changedItem) {
      if(this.mAmmFinanceCubes == null) {
         throw new RuntimeException("changeAmmFinanceCubesItem: no items in collection");
      } else {
         AmmFinanceCubePK changedPK = changedItem.getPK();
         AmmFinanceCubeEVO listItem = this.getAmmFinanceCubesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeAmmFinanceCubesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteAmmFinanceCubesItem(AmmFinanceCubePK removePK) {
      AmmFinanceCubeEVO listItem = this.getAmmFinanceCubesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeAmmFinanceCubesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public AmmFinanceCubeEVO getAmmFinanceCubesItem(AmmFinanceCubePK pk) {
      return (AmmFinanceCubeEVO)this.mAmmFinanceCubes.get(pk);
   }

   public AmmFinanceCubeEVO getAmmFinanceCubesItem() {
      if(this.mAmmFinanceCubes.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mAmmFinanceCubes.size());
      } else {
         Iterator iter = this.mAmmFinanceCubes.values().iterator();
         return (AmmFinanceCubeEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public AmmModelRef getEntityRef(String entityText) {
      return new AmmModelRefImpl(this.getPK(), entityText);
   }

   public void postCreateInit() {
      this.mAmmDimensionsAllItemsLoaded = true;
      Iterator i$;
      if(this.mAmmDimensions == null) {
         this.mAmmDimensions = new HashMap();
      } else {
         i$ = this.mAmmDimensions.values().iterator();

         while(i$.hasNext()) {
            AmmDimensionEVO child = (AmmDimensionEVO)i$.next();
            child.postCreateInit();
         }
      }

      this.mAmmFinanceCubesAllItemsLoaded = true;
      if(this.mAmmFinanceCubes == null) {
         this.mAmmFinanceCubes = new HashMap();
      } else {
         i$ = this.mAmmFinanceCubes.values().iterator();

         while(i$.hasNext()) {
            AmmFinanceCubeEVO child1 = (AmmFinanceCubeEVO)i$.next();
            child1.postCreateInit();
         }
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("AmmModelId=");
      sb.append(String.valueOf(this.mAmmModelId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("SrcModelId=");
      sb.append(String.valueOf(this.mSrcModelId));
      sb.append(' ');
      sb.append("InvalidatedByTaskId=");
      sb.append(String.valueOf(this.mInvalidatedByTaskId));
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

      sb.append("AmmModel: ");
      sb.append(this.toString());
      if(this.mAmmDimensionsAllItemsLoaded || this.mAmmDimensions != null) {
         sb.delete(indent, sb.length());
         sb.append(" - AmmDimensions: allItemsLoaded=");
         sb.append(String.valueOf(this.mAmmDimensionsAllItemsLoaded));
         sb.append(" items=");
         if(this.mAmmDimensions == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mAmmDimensions.size()));
         }
      }

      if(this.mAmmFinanceCubesAllItemsLoaded || this.mAmmFinanceCubes != null) {
         sb.delete(indent, sb.length());
         sb.append(" - AmmFinanceCubes: allItemsLoaded=");
         sb.append(String.valueOf(this.mAmmFinanceCubesAllItemsLoaded));
         sb.append(" items=");
         if(this.mAmmFinanceCubes == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mAmmFinanceCubes.size()));
         }
      }

      Iterator var5;
      if(this.mAmmDimensions != null) {
         var5 = this.mAmmDimensions.values().iterator();

         while(var5.hasNext()) {
            AmmDimensionEVO listItem = (AmmDimensionEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mAmmFinanceCubes != null) {
         var5 = this.mAmmFinanceCubes.values().iterator();

         while(var5.hasNext()) {
            AmmFinanceCubeEVO var6 = (AmmFinanceCubeEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(AmmDimensionEVO child, int newAmmDimensionId) {
      if(this.getAmmDimensionsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mAmmDimensions.remove(child.getPK());
         child.setAmmDimensionId(newAmmDimensionId);
         this.mAmmDimensions.put(child.getPK(), child);
      }
   }

   public void changeKey(AmmFinanceCubeEVO child, int newAmmFinanceCubeId) {
      if(this.getAmmFinanceCubesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mAmmFinanceCubes.remove(child.getPK());
         child.setAmmFinanceCubeId(newAmmFinanceCubeId);
         this.mAmmFinanceCubes.put(child.getPK(), child);
      }
   }

   public void setAmmDimensionsAllItemsLoaded(boolean allItemsLoaded) {
      this.mAmmDimensionsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isAmmDimensionsAllItemsLoaded() {
      return this.mAmmDimensionsAllItemsLoaded;
   }

   public void setAmmFinanceCubesAllItemsLoaded(boolean allItemsLoaded) {
      this.mAmmFinanceCubesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isAmmFinanceCubesAllItemsLoaded() {
      return this.mAmmFinanceCubesAllItemsLoaded;
   }
}
