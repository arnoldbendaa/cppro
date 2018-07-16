// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.mapping;

import com.cedar.cp.api.model.mapping.MappedFinanceCubeRef;
import com.cedar.cp.dto.model.mapping.MappedDataTypePK;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubeCK;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubePK;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubeRefImpl;
import com.cedar.cp.ejb.impl.model.mapping.MappedDataTypeEVO;
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

public class MappedFinanceCubeEVO implements Serializable {

   private transient MappedFinanceCubePK mPK;
   private int mMappedFinanceCubeId;
   private int mMappedModelId;
   private int mFinanceCubeId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<MappedDataTypePK, MappedDataTypeEVO> mMappedDataTypes;
   protected boolean mMappedDataTypesAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public MappedFinanceCubeEVO() {}

   public MappedFinanceCubeEVO(int newMappedFinanceCubeId, int newMappedModelId, int newFinanceCubeId, Collection newMappedDataTypes) {
      this.mMappedFinanceCubeId = newMappedFinanceCubeId;
      this.mMappedModelId = newMappedModelId;
      this.mFinanceCubeId = newFinanceCubeId;
      this.setMappedDataTypes(newMappedDataTypes);
   }

   public void setMappedDataTypes(Collection<MappedDataTypeEVO> items) {
      if(items != null) {
         if(this.mMappedDataTypes == null) {
            this.mMappedDataTypes = new HashMap();
         } else {
            this.mMappedDataTypes.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            MappedDataTypeEVO child = (MappedDataTypeEVO)i$.next();
            this.mMappedDataTypes.put(child.getPK(), child);
         }
      } else {
         this.mMappedDataTypes = null;
      }

   }

   public MappedFinanceCubePK getPK() {
      if(this.mPK == null) {
         this.mPK = new MappedFinanceCubePK(this.mMappedFinanceCubeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getMappedFinanceCubeId() {
      return this.mMappedFinanceCubeId;
   }

   public int getMappedModelId() {
      return this.mMappedModelId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
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

   public void setMappedFinanceCubeId(int newMappedFinanceCubeId) {
      if(this.mMappedFinanceCubeId != newMappedFinanceCubeId) {
         this.mModified = true;
         this.mMappedFinanceCubeId = newMappedFinanceCubeId;
         this.mPK = null;
      }
   }

   public void setMappedModelId(int newMappedModelId) {
      if(this.mMappedModelId != newMappedModelId) {
         this.mModified = true;
         this.mMappedModelId = newMappedModelId;
      }
   }

   public void setFinanceCubeId(int newFinanceCubeId) {
      if(this.mFinanceCubeId != newFinanceCubeId) {
         this.mModified = true;
         this.mFinanceCubeId = newFinanceCubeId;
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

   public void setDetails(MappedFinanceCubeEVO newDetails) {
      this.setMappedFinanceCubeId(newDetails.getMappedFinanceCubeId());
      this.setMappedModelId(newDetails.getMappedModelId());
      this.setFinanceCubeId(newDetails.getFinanceCubeId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public MappedFinanceCubeEVO deepClone() {
      MappedFinanceCubeEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (MappedFinanceCubeEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(MappedModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mMappedFinanceCubeId > 0) {
         newKey = true;
         if(parent == null) {
            this.setMappedFinanceCubeId(-this.mMappedFinanceCubeId);
         } else {
            parent.changeKey(this, -this.mMappedFinanceCubeId);
         }
      } else if(this.mMappedFinanceCubeId < 1) {
         newKey = true;
      }

      MappedDataTypeEVO item;
      if(this.mMappedDataTypes != null) {
         for(Iterator iter = (new ArrayList(this.mMappedDataTypes.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (MappedDataTypeEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mMappedFinanceCubeId < 1) {
         returnCount = startCount + 1;
      }

      MappedDataTypeEVO item;
      if(this.mMappedDataTypes != null) {
         for(Iterator iter = this.mMappedDataTypes.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (MappedDataTypeEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(MappedModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mMappedFinanceCubeId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      MappedDataTypeEVO item;
      if(this.mMappedDataTypes != null) {
         for(Iterator iter = (new ArrayList(this.mMappedDataTypes.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (MappedDataTypeEVO)iter.next();
            item.setMappedFinanceCubeId(this.mMappedFinanceCubeId);
         }
      }

      return nextKey;
   }

   public Collection<MappedDataTypeEVO> getMappedDataTypes() {
      return this.mMappedDataTypes != null?this.mMappedDataTypes.values():null;
   }

   public Map<MappedDataTypePK, MappedDataTypeEVO> getMappedDataTypesMap() {
      return this.mMappedDataTypes;
   }

   public void loadMappedDataTypesItem(MappedDataTypeEVO newItem) {
      if(this.mMappedDataTypes == null) {
         this.mMappedDataTypes = new HashMap();
      }

      this.mMappedDataTypes.put(newItem.getPK(), newItem);
   }

   public void addMappedDataTypesItem(MappedDataTypeEVO newItem) {
      if(this.mMappedDataTypes == null) {
         this.mMappedDataTypes = new HashMap();
      }

      MappedDataTypePK newPK = newItem.getPK();
      if(this.getMappedDataTypesItem(newPK) != null) {
         throw new RuntimeException("addMappedDataTypesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mMappedDataTypes.put(newPK, newItem);
      }
   }

   public void changeMappedDataTypesItem(MappedDataTypeEVO changedItem) {
      if(this.mMappedDataTypes == null) {
         throw new RuntimeException("changeMappedDataTypesItem: no items in collection");
      } else {
         MappedDataTypePK changedPK = changedItem.getPK();
         MappedDataTypeEVO listItem = this.getMappedDataTypesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeMappedDataTypesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteMappedDataTypesItem(MappedDataTypePK removePK) {
      MappedDataTypeEVO listItem = this.getMappedDataTypesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeMappedDataTypesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public MappedDataTypeEVO getMappedDataTypesItem(MappedDataTypePK pk) {
      return (MappedDataTypeEVO)this.mMappedDataTypes.get(pk);
   }

   public MappedDataTypeEVO getMappedDataTypesItem() {
      if(this.mMappedDataTypes.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mMappedDataTypes.size());
      } else {
         Iterator iter = this.mMappedDataTypes.values().iterator();
         return (MappedDataTypeEVO)iter.next();
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

   public MappedFinanceCubeRef getEntityRef(MappedModelEVO evoMappedModel, String entityText) {
      return new MappedFinanceCubeRefImpl(new MappedFinanceCubeCK(evoMappedModel.getPK(), this.getPK()), entityText);
   }

   public MappedFinanceCubeCK getCK(MappedModelEVO evoMappedModel) {
      return new MappedFinanceCubeCK(evoMappedModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mMappedDataTypesAllItemsLoaded = true;
      if(this.mMappedDataTypes == null) {
         this.mMappedDataTypes = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("MappedFinanceCubeId=");
      sb.append(String.valueOf(this.mMappedFinanceCubeId));
      sb.append(' ');
      sb.append("MappedModelId=");
      sb.append(String.valueOf(this.mMappedModelId));
      sb.append(' ');
      sb.append("FinanceCubeId=");
      sb.append(String.valueOf(this.mFinanceCubeId));
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

      sb.append("MappedFinanceCube: ");
      sb.append(this.toString());
      if(this.mMappedDataTypesAllItemsLoaded || this.mMappedDataTypes != null) {
         sb.delete(indent, sb.length());
         sb.append(" - MappedDataTypes: allItemsLoaded=");
         sb.append(String.valueOf(this.mMappedDataTypesAllItemsLoaded));
         sb.append(" items=");
         if(this.mMappedDataTypes == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mMappedDataTypes.size()));
         }
      }

      if(this.mMappedDataTypes != null) {
         Iterator var5 = this.mMappedDataTypes.values().iterator();

         while(var5.hasNext()) {
            MappedDataTypeEVO listItem = (MappedDataTypeEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(MappedDataTypeEVO child, int newMappedDataTypeId) {
      if(this.getMappedDataTypesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mMappedDataTypes.remove(child.getPK());
         child.setMappedDataTypeId(newMappedDataTypeId);
         this.mMappedDataTypes.put(child.getPK(), child);
      }
   }

   public void setMappedDataTypesAllItemsLoaded(boolean allItemsLoaded) {
      this.mMappedDataTypesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isMappedDataTypesAllItemsLoaded() {
      return this.mMappedDataTypesAllItemsLoaded;
   }
}
