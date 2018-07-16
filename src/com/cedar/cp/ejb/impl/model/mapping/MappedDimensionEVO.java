// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.mapping;

import com.cedar.cp.api.model.mapping.MappedDimensionRef;
import com.cedar.cp.dto.model.mapping.MappedDimensionCK;
import com.cedar.cp.dto.model.mapping.MappedDimensionElementPK;
import com.cedar.cp.dto.model.mapping.MappedDimensionPK;
import com.cedar.cp.dto.model.mapping.MappedDimensionRefImpl;
import com.cedar.cp.dto.model.mapping.MappedHierarchyPK;
import com.cedar.cp.ejb.impl.model.mapping.MappedDimensionElementEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedHierarchyEVO;
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

public class MappedDimensionEVO implements Serializable {

   private transient MappedDimensionPK mPK;
   private int mMappedDimensionId;
   private int mMappedModelId;
   private int mDimensionId;
   private String mPathVisId;
   private boolean mExcludeDisabledLeafNodes;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<MappedDimensionElementPK, MappedDimensionElementEVO> mMappedDimensionElements;
   protected boolean mMappedDimensionElementsAllItemsLoaded;
   private Map<MappedHierarchyPK, MappedHierarchyEVO> mMappedHierarchys;
   protected boolean mMappedHierarchysAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public MappedDimensionEVO() {}

   public MappedDimensionEVO(int newMappedDimensionId, int newMappedModelId, int newDimensionId, String newPathVisId, boolean newExcludeDisabledLeafNodes, Collection newMappedDimensionElements, Collection newMappedHierarchys) {
      this.mMappedDimensionId = newMappedDimensionId;
      this.mMappedModelId = newMappedModelId;
      this.mDimensionId = newDimensionId;
      this.mPathVisId = newPathVisId;
      this.mExcludeDisabledLeafNodes = newExcludeDisabledLeafNodes;
      this.setMappedDimensionElements(newMappedDimensionElements);
      this.setMappedHierarchys(newMappedHierarchys);
   }

   public void setMappedDimensionElements(Collection<MappedDimensionElementEVO> items) {
      if(items != null) {
         if(this.mMappedDimensionElements == null) {
            this.mMappedDimensionElements = new HashMap();
         } else {
            this.mMappedDimensionElements.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            MappedDimensionElementEVO child = (MappedDimensionElementEVO)i$.next();
            this.mMappedDimensionElements.put(child.getPK(), child);
         }
      } else {
         this.mMappedDimensionElements = null;
      }

   }

   public void setMappedHierarchys(Collection<MappedHierarchyEVO> items) {
      if(items != null) {
         if(this.mMappedHierarchys == null) {
            this.mMappedHierarchys = new HashMap();
         } else {
            this.mMappedHierarchys.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            MappedHierarchyEVO child = (MappedHierarchyEVO)i$.next();
            this.mMappedHierarchys.put(child.getPK(), child);
         }
      } else {
         this.mMappedHierarchys = null;
      }

   }

   public MappedDimensionPK getPK() {
      if(this.mPK == null) {
         this.mPK = new MappedDimensionPK(this.mMappedDimensionId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getMappedDimensionId() {
      return this.mMappedDimensionId;
   }

   public int getMappedModelId() {
      return this.mMappedModelId;
   }

   public int getDimensionId() {
      return this.mDimensionId;
   }

   public String getPathVisId() {
      return this.mPathVisId;
   }

   public boolean getExcludeDisabledLeafNodes() {
      return this.mExcludeDisabledLeafNodes;
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

   public void setMappedDimensionId(int newMappedDimensionId) {
      if(this.mMappedDimensionId != newMappedDimensionId) {
         this.mModified = true;
         this.mMappedDimensionId = newMappedDimensionId;
         this.mPK = null;
      }
   }

   public void setMappedModelId(int newMappedModelId) {
      if(this.mMappedModelId != newMappedModelId) {
         this.mModified = true;
         this.mMappedModelId = newMappedModelId;
      }
   }

   public void setDimensionId(int newDimensionId) {
      if(this.mDimensionId != newDimensionId) {
         this.mModified = true;
         this.mDimensionId = newDimensionId;
      }
   }

   public void setExcludeDisabledLeafNodes(boolean newExcludeDisabledLeafNodes) {
      if(this.mExcludeDisabledLeafNodes != newExcludeDisabledLeafNodes) {
         this.mModified = true;
         this.mExcludeDisabledLeafNodes = newExcludeDisabledLeafNodes;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setPathVisId(String newPathVisId) {
      if(this.mPathVisId != null && newPathVisId == null || this.mPathVisId == null && newPathVisId != null || this.mPathVisId != null && newPathVisId != null && !this.mPathVisId.equals(newPathVisId)) {
         this.mPathVisId = newPathVisId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(MappedDimensionEVO newDetails) {
      this.setMappedDimensionId(newDetails.getMappedDimensionId());
      this.setMappedModelId(newDetails.getMappedModelId());
      this.setDimensionId(newDetails.getDimensionId());
      this.setPathVisId(newDetails.getPathVisId());
      this.setExcludeDisabledLeafNodes(newDetails.getExcludeDisabledLeafNodes());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public MappedDimensionEVO deepClone() {
      MappedDimensionEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (MappedDimensionEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(MappedModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mMappedDimensionId > 0) {
         newKey = true;
         if(parent == null) {
            this.setMappedDimensionId(-this.mMappedDimensionId);
         } else {
            parent.changeKey(this, -this.mMappedDimensionId);
         }
      } else if(this.mMappedDimensionId < 1) {
         newKey = true;
      }

      Iterator iter;
      MappedDimensionElementEVO item;
      if(this.mMappedDimensionElements != null) {
         for(iter = (new ArrayList(this.mMappedDimensionElements.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (MappedDimensionElementEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      MappedHierarchyEVO item1;
      if(this.mMappedHierarchys != null) {
         for(iter = (new ArrayList(this.mMappedHierarchys.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (MappedHierarchyEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mMappedDimensionId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      MappedDimensionElementEVO item;
      if(this.mMappedDimensionElements != null) {
         for(iter = this.mMappedDimensionElements.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (MappedDimensionElementEVO)iter.next();
         }
      }

      MappedHierarchyEVO item1;
      if(this.mMappedHierarchys != null) {
         for(iter = this.mMappedHierarchys.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (MappedHierarchyEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(MappedModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mMappedDimensionId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      Iterator iter;
      MappedDimensionElementEVO item;
      if(this.mMappedDimensionElements != null) {
         for(iter = (new ArrayList(this.mMappedDimensionElements.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (MappedDimensionElementEVO)iter.next();
            item.setMappedDimensionId(this.mMappedDimensionId);
         }
      }

      MappedHierarchyEVO item1;
      if(this.mMappedHierarchys != null) {
         for(iter = (new ArrayList(this.mMappedHierarchys.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (MappedHierarchyEVO)iter.next();
            item1.setMappedDimensionId(this.mMappedDimensionId);
         }
      }

      return nextKey;
   }

   public Collection<MappedDimensionElementEVO> getMappedDimensionElements() {
      return this.mMappedDimensionElements != null?this.mMappedDimensionElements.values():null;
   }

   public Map<MappedDimensionElementPK, MappedDimensionElementEVO> getMappedDimensionElementsMap() {
      return this.mMappedDimensionElements;
   }

   public void loadMappedDimensionElementsItem(MappedDimensionElementEVO newItem) {
      if(this.mMappedDimensionElements == null) {
         this.mMappedDimensionElements = new HashMap();
      }

      this.mMappedDimensionElements.put(newItem.getPK(), newItem);
   }

   public void addMappedDimensionElementsItem(MappedDimensionElementEVO newItem) {
      if(this.mMappedDimensionElements == null) {
         this.mMappedDimensionElements = new HashMap();
      }

      MappedDimensionElementPK newPK = newItem.getPK();
      if(this.getMappedDimensionElementsItem(newPK) != null) {
         throw new RuntimeException("addMappedDimensionElementsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mMappedDimensionElements.put(newPK, newItem);
      }
   }

   public void changeMappedDimensionElementsItem(MappedDimensionElementEVO changedItem) {
      if(this.mMappedDimensionElements == null) {
         throw new RuntimeException("changeMappedDimensionElementsItem: no items in collection");
      } else {
         MappedDimensionElementPK changedPK = changedItem.getPK();
         MappedDimensionElementEVO listItem = this.getMappedDimensionElementsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeMappedDimensionElementsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteMappedDimensionElementsItem(MappedDimensionElementPK removePK) {
      MappedDimensionElementEVO listItem = this.getMappedDimensionElementsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeMappedDimensionElementsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public MappedDimensionElementEVO getMappedDimensionElementsItem(MappedDimensionElementPK pk) {
      return (MappedDimensionElementEVO)this.mMappedDimensionElements.get(pk);
   }

   public MappedDimensionElementEVO getMappedDimensionElementsItem() {
      if(this.mMappedDimensionElements.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mMappedDimensionElements.size());
      } else {
         Iterator iter = this.mMappedDimensionElements.values().iterator();
         return (MappedDimensionElementEVO)iter.next();
      }
   }

   public Collection<MappedHierarchyEVO> getMappedHierarchys() {
      return this.mMappedHierarchys != null?this.mMappedHierarchys.values():null;
   }

   public Map<MappedHierarchyPK, MappedHierarchyEVO> getMappedHierarchysMap() {
      return this.mMappedHierarchys;
   }

   public void loadMappedHierarchysItem(MappedHierarchyEVO newItem) {
      if(this.mMappedHierarchys == null) {
         this.mMappedHierarchys = new HashMap();
      }

      this.mMappedHierarchys.put(newItem.getPK(), newItem);
   }

   public void addMappedHierarchysItem(MappedHierarchyEVO newItem) {
      if(this.mMappedHierarchys == null) {
         this.mMappedHierarchys = new HashMap();
      }

      MappedHierarchyPK newPK = newItem.getPK();
      if(this.getMappedHierarchysItem(newPK) != null) {
         throw new RuntimeException("addMappedHierarchysItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mMappedHierarchys.put(newPK, newItem);
      }
   }

   public void changeMappedHierarchysItem(MappedHierarchyEVO changedItem) {
      if(this.mMappedHierarchys == null) {
         throw new RuntimeException("changeMappedHierarchysItem: no items in collection");
      } else {
         MappedHierarchyPK changedPK = changedItem.getPK();
         MappedHierarchyEVO listItem = this.getMappedHierarchysItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeMappedHierarchysItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteMappedHierarchysItem(MappedHierarchyPK removePK) {
      MappedHierarchyEVO listItem = this.getMappedHierarchysItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeMappedHierarchysItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public MappedHierarchyEVO getMappedHierarchysItem(MappedHierarchyPK pk) {
      return (MappedHierarchyEVO)this.mMappedHierarchys.get(pk);
   }

   public MappedHierarchyEVO getMappedHierarchysItem() {
      if(this.mMappedHierarchys.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mMappedHierarchys.size());
      } else {
         Iterator iter = this.mMappedHierarchys.values().iterator();
         return (MappedHierarchyEVO)iter.next();
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

   public MappedDimensionRef getEntityRef(MappedModelEVO evoMappedModel, String entityText) {
      return new MappedDimensionRefImpl(new MappedDimensionCK(evoMappedModel.getPK(), this.getPK()), entityText);
   }

   public MappedDimensionCK getCK(MappedModelEVO evoMappedModel) {
      return new MappedDimensionCK(evoMappedModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mMappedDimensionElementsAllItemsLoaded = true;
      if(this.mMappedDimensionElements == null) {
         this.mMappedDimensionElements = new HashMap();
      }

      this.mMappedHierarchysAllItemsLoaded = true;
      if(this.mMappedHierarchys == null) {
         this.mMappedHierarchys = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("MappedDimensionId=");
      sb.append(String.valueOf(this.mMappedDimensionId));
      sb.append(' ');
      sb.append("MappedModelId=");
      sb.append(String.valueOf(this.mMappedModelId));
      sb.append(' ');
      sb.append("DimensionId=");
      sb.append(String.valueOf(this.mDimensionId));
      sb.append(' ');
      sb.append("PathVisId=");
      sb.append(String.valueOf(this.mPathVisId));
      sb.append(' ');
      sb.append("ExcludeDisabledLeafNodes=");
      sb.append(String.valueOf(this.mExcludeDisabledLeafNodes));
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

      sb.append("MappedDimension: ");
      sb.append(this.toString());
      if(this.mMappedDimensionElementsAllItemsLoaded || this.mMappedDimensionElements != null) {
         sb.delete(indent, sb.length());
         sb.append(" - MappedDimensionElements: allItemsLoaded=");
         sb.append(String.valueOf(this.mMappedDimensionElementsAllItemsLoaded));
         sb.append(" items=");
         if(this.mMappedDimensionElements == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mMappedDimensionElements.size()));
         }
      }

      if(this.mMappedHierarchysAllItemsLoaded || this.mMappedHierarchys != null) {
         sb.delete(indent, sb.length());
         sb.append(" - MappedHierarchys: allItemsLoaded=");
         sb.append(String.valueOf(this.mMappedHierarchysAllItemsLoaded));
         sb.append(" items=");
         if(this.mMappedHierarchys == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mMappedHierarchys.size()));
         }
      }

      Iterator var5;
      if(this.mMappedDimensionElements != null) {
         var5 = this.mMappedDimensionElements.values().iterator();

         while(var5.hasNext()) {
            MappedDimensionElementEVO listItem = (MappedDimensionElementEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mMappedHierarchys != null) {
         var5 = this.mMappedHierarchys.values().iterator();

         while(var5.hasNext()) {
            MappedHierarchyEVO var6 = (MappedHierarchyEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(MappedDimensionElementEVO child, int newMappedDimensionElementId) {
      if(this.getMappedDimensionElementsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mMappedDimensionElements.remove(child.getPK());
         child.setMappedDimensionElementId(newMappedDimensionElementId);
         this.mMappedDimensionElements.put(child.getPK(), child);
      }
   }

   public void changeKey(MappedHierarchyEVO child, int newMappedHierarchyId) {
      if(this.getMappedHierarchysItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mMappedHierarchys.remove(child.getPK());
         child.setMappedHierarchyId(newMappedHierarchyId);
         this.mMappedHierarchys.put(child.getPK(), child);
      }
   }

   public void setMappedDimensionElementsAllItemsLoaded(boolean allItemsLoaded) {
      this.mMappedDimensionElementsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isMappedDimensionElementsAllItemsLoaded() {
      return this.mMappedDimensionElementsAllItemsLoaded;
   }

   public void setMappedHierarchysAllItemsLoaded(boolean allItemsLoaded) {
      this.mMappedHierarchysAllItemsLoaded = allItemsLoaded;
   }

   public boolean isMappedHierarchysAllItemsLoaded() {
      return this.mMappedHierarchysAllItemsLoaded;
   }
}
