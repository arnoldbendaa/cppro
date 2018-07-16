// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.amm;

import com.cedar.cp.api.model.amm.AmmFinanceCubeRef;
import com.cedar.cp.dto.model.amm.AmmDataTypePK;
import com.cedar.cp.dto.model.amm.AmmFinanceCubeCK;
import com.cedar.cp.dto.model.amm.AmmFinanceCubePK;
import com.cedar.cp.dto.model.amm.AmmFinanceCubeRefImpl;
import com.cedar.cp.ejb.impl.model.amm.AmmDataTypeEVO;
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

public class AmmFinanceCubeEVO implements Serializable {

   private transient AmmFinanceCubePK mPK;
   private int mAmmFinanceCubeId;
   private int mAmmModelId;
   private int mFinanceCubeId;
   private int mSrcFinanceCubeId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<AmmDataTypePK, AmmDataTypeEVO> mAmmDataTypes;
   protected boolean mAmmDataTypesAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public AmmFinanceCubeEVO() {}

   public AmmFinanceCubeEVO(int newAmmFinanceCubeId, int newAmmModelId, int newFinanceCubeId, int newSrcFinanceCubeId, Collection newAmmDataTypes) {
      this.mAmmFinanceCubeId = newAmmFinanceCubeId;
      this.mAmmModelId = newAmmModelId;
      this.mFinanceCubeId = newFinanceCubeId;
      this.mSrcFinanceCubeId = newSrcFinanceCubeId;
      this.setAmmDataTypes(newAmmDataTypes);
   }

   public void setAmmDataTypes(Collection<AmmDataTypeEVO> items) {
      if(items != null) {
         if(this.mAmmDataTypes == null) {
            this.mAmmDataTypes = new HashMap();
         } else {
            this.mAmmDataTypes.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            AmmDataTypeEVO child = (AmmDataTypeEVO)i$.next();
            this.mAmmDataTypes.put(child.getPK(), child);
         }
      } else {
         this.mAmmDataTypes = null;
      }

   }

   public AmmFinanceCubePK getPK() {
      if(this.mPK == null) {
         this.mPK = new AmmFinanceCubePK(this.mAmmFinanceCubeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getAmmFinanceCubeId() {
      return this.mAmmFinanceCubeId;
   }

   public int getAmmModelId() {
      return this.mAmmModelId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getSrcFinanceCubeId() {
      return this.mSrcFinanceCubeId;
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

   public void setAmmFinanceCubeId(int newAmmFinanceCubeId) {
      if(this.mAmmFinanceCubeId != newAmmFinanceCubeId) {
         this.mModified = true;
         this.mAmmFinanceCubeId = newAmmFinanceCubeId;
         this.mPK = null;
      }
   }

   public void setAmmModelId(int newAmmModelId) {
      if(this.mAmmModelId != newAmmModelId) {
         this.mModified = true;
         this.mAmmModelId = newAmmModelId;
      }
   }

   public void setFinanceCubeId(int newFinanceCubeId) {
      if(this.mFinanceCubeId != newFinanceCubeId) {
         this.mModified = true;
         this.mFinanceCubeId = newFinanceCubeId;
      }
   }

   public void setSrcFinanceCubeId(int newSrcFinanceCubeId) {
      if(this.mSrcFinanceCubeId != newSrcFinanceCubeId) {
         this.mModified = true;
         this.mSrcFinanceCubeId = newSrcFinanceCubeId;
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

   public void setDetails(AmmFinanceCubeEVO newDetails) {
      this.setAmmFinanceCubeId(newDetails.getAmmFinanceCubeId());
      this.setAmmModelId(newDetails.getAmmModelId());
      this.setFinanceCubeId(newDetails.getFinanceCubeId());
      this.setSrcFinanceCubeId(newDetails.getSrcFinanceCubeId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public AmmFinanceCubeEVO deepClone() {
      AmmFinanceCubeEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (AmmFinanceCubeEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(AmmModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mAmmFinanceCubeId > 0) {
         newKey = true;
         if(parent == null) {
            this.setAmmFinanceCubeId(-this.mAmmFinanceCubeId);
         } else {
            parent.changeKey(this, -this.mAmmFinanceCubeId);
         }
      } else if(this.mAmmFinanceCubeId < 1) {
         newKey = true;
      }

      AmmDataTypeEVO item;
      if(this.mAmmDataTypes != null) {
         for(Iterator iter = (new ArrayList(this.mAmmDataTypes.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (AmmDataTypeEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mAmmFinanceCubeId < 1) {
         returnCount = startCount + 1;
      }

      AmmDataTypeEVO item;
      if(this.mAmmDataTypes != null) {
         for(Iterator iter = this.mAmmDataTypes.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (AmmDataTypeEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(AmmModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mAmmFinanceCubeId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      AmmDataTypeEVO item;
      if(this.mAmmDataTypes != null) {
         for(Iterator iter = (new ArrayList(this.mAmmDataTypes.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (AmmDataTypeEVO)iter.next();
            item.setAmmFinanceCubeId(this.mAmmFinanceCubeId);
         }
      }

      return nextKey;
   }

   public Collection<AmmDataTypeEVO> getAmmDataTypes() {
      return this.mAmmDataTypes != null?this.mAmmDataTypes.values():null;
   }

   public Map<AmmDataTypePK, AmmDataTypeEVO> getAmmDataTypesMap() {
      return this.mAmmDataTypes;
   }

   public void loadAmmDataTypesItem(AmmDataTypeEVO newItem) {
      if(this.mAmmDataTypes == null) {
         this.mAmmDataTypes = new HashMap();
      }

      this.mAmmDataTypes.put(newItem.getPK(), newItem);
   }

   public void addAmmDataTypesItem(AmmDataTypeEVO newItem) {
      if(this.mAmmDataTypes == null) {
         this.mAmmDataTypes = new HashMap();
      }

      AmmDataTypePK newPK = newItem.getPK();
      if(this.getAmmDataTypesItem(newPK) != null) {
         throw new RuntimeException("addAmmDataTypesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mAmmDataTypes.put(newPK, newItem);
      }
   }

   public void changeAmmDataTypesItem(AmmDataTypeEVO changedItem) {
      if(this.mAmmDataTypes == null) {
         throw new RuntimeException("changeAmmDataTypesItem: no items in collection");
      } else {
         AmmDataTypePK changedPK = changedItem.getPK();
         AmmDataTypeEVO listItem = this.getAmmDataTypesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeAmmDataTypesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteAmmDataTypesItem(AmmDataTypePK removePK) {
      AmmDataTypeEVO listItem = this.getAmmDataTypesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeAmmDataTypesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public AmmDataTypeEVO getAmmDataTypesItem(AmmDataTypePK pk) {
      return (AmmDataTypeEVO)this.mAmmDataTypes.get(pk);
   }

   public AmmDataTypeEVO getAmmDataTypesItem() {
      if(this.mAmmDataTypes.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mAmmDataTypes.size());
      } else {
         Iterator iter = this.mAmmDataTypes.values().iterator();
         return (AmmDataTypeEVO)iter.next();
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

   public AmmFinanceCubeRef getEntityRef(AmmModelEVO evoAmmModel, String entityText) {
      return new AmmFinanceCubeRefImpl(new AmmFinanceCubeCK(evoAmmModel.getPK(), this.getPK()), entityText);
   }

   public AmmFinanceCubeCK getCK(AmmModelEVO evoAmmModel) {
      return new AmmFinanceCubeCK(evoAmmModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mAmmDataTypesAllItemsLoaded = true;
      if(this.mAmmDataTypes == null) {
         this.mAmmDataTypes = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("AmmFinanceCubeId=");
      sb.append(String.valueOf(this.mAmmFinanceCubeId));
      sb.append(' ');
      sb.append("AmmModelId=");
      sb.append(String.valueOf(this.mAmmModelId));
      sb.append(' ');
      sb.append("FinanceCubeId=");
      sb.append(String.valueOf(this.mFinanceCubeId));
      sb.append(' ');
      sb.append("SrcFinanceCubeId=");
      sb.append(String.valueOf(this.mSrcFinanceCubeId));
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

      sb.append("AmmFinanceCube: ");
      sb.append(this.toString());
      if(this.mAmmDataTypesAllItemsLoaded || this.mAmmDataTypes != null) {
         sb.delete(indent, sb.length());
         sb.append(" - AmmDataTypes: allItemsLoaded=");
         sb.append(String.valueOf(this.mAmmDataTypesAllItemsLoaded));
         sb.append(" items=");
         if(this.mAmmDataTypes == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mAmmDataTypes.size()));
         }
      }

      if(this.mAmmDataTypes != null) {
         Iterator var5 = this.mAmmDataTypes.values().iterator();

         while(var5.hasNext()) {
            AmmDataTypeEVO listItem = (AmmDataTypeEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(AmmDataTypeEVO child, int newAmmDataTypeId) {
      if(this.getAmmDataTypesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mAmmDataTypes.remove(child.getPK());
         child.setAmmDataTypeId(newAmmDataTypeId);
         this.mAmmDataTypes.put(child.getPK(), child);
      }
   }

   public void setAmmDataTypesAllItemsLoaded(boolean allItemsLoaded) {
      this.mAmmDataTypesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isAmmDataTypesAllItemsLoaded() {
      return this.mAmmDataTypesAllItemsLoaded;
   }
}
