// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.mapping;

import com.cedar.cp.api.model.mapping.MappedModelRef;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearPK;
import com.cedar.cp.dto.model.mapping.MappedDimensionPK;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubePK;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.dto.model.mapping.MappedModelRefImpl;
import com.cedar.cp.ejb.impl.model.mapping.MappedCalendarYearEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedDimensionEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedFinanceCubeEVO;
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

public class MappedModelEVO implements Serializable {

   private transient MappedModelPK mPK;
   private int mMappedModelId;
   private int mModelId;
   private int mExternalSystemId;
   private String mCompanyVisId;
   private String mLedgerVisId;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<MappedCalendarYearPK, MappedCalendarYearEVO> mMappedCalendarYears;
   protected boolean mMappedCalendarYearsAllItemsLoaded;
   private Map<MappedFinanceCubePK, MappedFinanceCubeEVO> mMappedFinanceCubes;
   protected boolean mMappedFinanceCubesAllItemsLoaded;
   private Map<MappedDimensionPK, MappedDimensionEVO> mMappedDimensions;
   protected boolean mMappedDimensionsAllItemsLoaded;
   private boolean mModified;


   public MappedModelEVO() {}

   public MappedModelEVO(int newMappedModelId, int newModelId, int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, int newVersionNum, Collection newMappedCalendarYears, Collection newMappedFinanceCubes, Collection newMappedDimensions) {
      this.mMappedModelId = newMappedModelId;
      this.mModelId = newModelId;
      this.mExternalSystemId = newExternalSystemId;
      this.mCompanyVisId = newCompanyVisId;
      this.mLedgerVisId = newLedgerVisId;
      this.mVersionNum = newVersionNum;
      this.setMappedCalendarYears(newMappedCalendarYears);
      this.setMappedFinanceCubes(newMappedFinanceCubes);
      this.setMappedDimensions(newMappedDimensions);
   }

   public void setMappedCalendarYears(Collection<MappedCalendarYearEVO> items) {
      if(items != null) {
         if(this.mMappedCalendarYears == null) {
            this.mMappedCalendarYears = new HashMap();
         } else {
            this.mMappedCalendarYears.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            MappedCalendarYearEVO child = (MappedCalendarYearEVO)i$.next();
            this.mMappedCalendarYears.put(child.getPK(), child);
         }
      } else {
         this.mMappedCalendarYears = null;
      }

   }

   public void setMappedFinanceCubes(Collection<MappedFinanceCubeEVO> items) {
      if(items != null) {
         if(this.mMappedFinanceCubes == null) {
            this.mMappedFinanceCubes = new HashMap();
         } else {
            this.mMappedFinanceCubes.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            MappedFinanceCubeEVO child = (MappedFinanceCubeEVO)i$.next();
            this.mMappedFinanceCubes.put(child.getPK(), child);
         }
      } else {
         this.mMappedFinanceCubes = null;
      }

   }

   public void setMappedDimensions(Collection<MappedDimensionEVO> items) {
      if(items != null) {
         if(this.mMappedDimensions == null) {
            this.mMappedDimensions = new HashMap();
         } else {
            this.mMappedDimensions.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            MappedDimensionEVO child = (MappedDimensionEVO)i$.next();
            this.mMappedDimensions.put(child.getPK(), child);
         }
      } else {
         this.mMappedDimensions = null;
      }

   }

   public MappedModelPK getPK() {
      if(this.mPK == null) {
         this.mPK = new MappedModelPK(this.mMappedModelId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getMappedModelId() {
      return this.mMappedModelId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getExternalSystemId() {
      return this.mExternalSystemId;
   }

   public String getCompanyVisId() {
      return this.mCompanyVisId;
   }

   public String getLedgerVisId() {
      return this.mLedgerVisId;
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

   public void setMappedModelId(int newMappedModelId) {
      if(this.mMappedModelId != newMappedModelId) {
         this.mModified = true;
         this.mMappedModelId = newMappedModelId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setExternalSystemId(int newExternalSystemId) {
      if(this.mExternalSystemId != newExternalSystemId) {
         this.mModified = true;
         this.mExternalSystemId = newExternalSystemId;
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

   public void setCompanyVisId(String newCompanyVisId) {
      if(this.mCompanyVisId != null && newCompanyVisId == null || this.mCompanyVisId == null && newCompanyVisId != null || this.mCompanyVisId != null && newCompanyVisId != null && !this.mCompanyVisId.equals(newCompanyVisId)) {
         this.mCompanyVisId = newCompanyVisId;
         this.mModified = true;
      }

   }

   public void setLedgerVisId(String newLedgerVisId) {
      if(this.mLedgerVisId != null && newLedgerVisId == null || this.mLedgerVisId == null && newLedgerVisId != null || this.mLedgerVisId != null && newLedgerVisId != null && !this.mLedgerVisId.equals(newLedgerVisId)) {
         this.mLedgerVisId = newLedgerVisId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(MappedModelEVO newDetails) {
      this.setMappedModelId(newDetails.getMappedModelId());
      this.setModelId(newDetails.getModelId());
      this.setExternalSystemId(newDetails.getExternalSystemId());
      this.setCompanyVisId(newDetails.getCompanyVisId());
      this.setLedgerVisId(newDetails.getLedgerVisId());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public MappedModelEVO deepClone() {
      MappedModelEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (MappedModelEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mMappedModelId > 0) {
         newKey = true;
         this.mMappedModelId = 0;
      } else if(this.mMappedModelId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      Iterator iter;
      MappedCalendarYearEVO item;
      if(this.mMappedCalendarYears != null) {
         for(iter = (new ArrayList(this.mMappedCalendarYears.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (MappedCalendarYearEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      MappedFinanceCubeEVO item1;
      if(this.mMappedFinanceCubes != null) {
         for(iter = (new ArrayList(this.mMappedFinanceCubes.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (MappedFinanceCubeEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

      MappedDimensionEVO item2;
      if(this.mMappedDimensions != null) {
         for(iter = (new ArrayList(this.mMappedDimensions.values())).iterator(); iter.hasNext(); item2.prepareForInsert(this)) {
            item2 = (MappedDimensionEVO)iter.next();
            if(newKey) {
               item2.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mMappedModelId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      MappedCalendarYearEVO item;
      if(this.mMappedCalendarYears != null) {
         for(iter = this.mMappedCalendarYears.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (MappedCalendarYearEVO)iter.next();
         }
      }

      MappedFinanceCubeEVO item1;
      if(this.mMappedFinanceCubes != null) {
         for(iter = this.mMappedFinanceCubes.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (MappedFinanceCubeEVO)iter.next();
         }
      }

      MappedDimensionEVO item2;
      if(this.mMappedDimensions != null) {
         for(iter = this.mMappedDimensions.values().iterator(); iter.hasNext(); returnCount = item2.getInsertCount(returnCount)) {
            item2 = (MappedDimensionEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mMappedModelId < 1) {
         this.mMappedModelId = startKey;
         nextKey = startKey + 1;
      }

      Iterator iter;
      MappedCalendarYearEVO item;
      if(this.mMappedCalendarYears != null) {
         for(iter = (new ArrayList(this.mMappedCalendarYears.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (MappedCalendarYearEVO)iter.next();
            item.setMappedModelId(this.mMappedModelId);
         }
      }

      MappedFinanceCubeEVO item1;
      if(this.mMappedFinanceCubes != null) {
         for(iter = (new ArrayList(this.mMappedFinanceCubes.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (MappedFinanceCubeEVO)iter.next();
            item1.setMappedModelId(this.mMappedModelId);
         }
      }

      MappedDimensionEVO item2;
      if(this.mMappedDimensions != null) {
         for(iter = (new ArrayList(this.mMappedDimensions.values())).iterator(); iter.hasNext(); nextKey = item2.assignNextKey(this, nextKey)) {
            item2 = (MappedDimensionEVO)iter.next();
            item2.setMappedModelId(this.mMappedModelId);
         }
      }

      return nextKey;
   }

   public Collection<MappedCalendarYearEVO> getMappedCalendarYears() {
      return this.mMappedCalendarYears != null?this.mMappedCalendarYears.values():null;
   }

   public Map<MappedCalendarYearPK, MappedCalendarYearEVO> getMappedCalendarYearsMap() {
      return this.mMappedCalendarYears;
   }

   public void loadMappedCalendarYearsItem(MappedCalendarYearEVO newItem) {
      if(this.mMappedCalendarYears == null) {
         this.mMappedCalendarYears = new HashMap();
      }

      this.mMappedCalendarYears.put(newItem.getPK(), newItem);
   }

   public void addMappedCalendarYearsItem(MappedCalendarYearEVO newItem) {
      if(this.mMappedCalendarYears == null) {
         this.mMappedCalendarYears = new HashMap();
      }

      MappedCalendarYearPK newPK = newItem.getPK();
      if(this.getMappedCalendarYearsItem(newPK) != null) {
         throw new RuntimeException("addMappedCalendarYearsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mMappedCalendarYears.put(newPK, newItem);
      }
   }

   public void changeMappedCalendarYearsItem(MappedCalendarYearEVO changedItem) {
      if(this.mMappedCalendarYears == null) {
         throw new RuntimeException("changeMappedCalendarYearsItem: no items in collection");
      } else {
         MappedCalendarYearPK changedPK = changedItem.getPK();
         MappedCalendarYearEVO listItem = this.getMappedCalendarYearsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeMappedCalendarYearsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteMappedCalendarYearsItem(MappedCalendarYearPK removePK) {
      MappedCalendarYearEVO listItem = this.getMappedCalendarYearsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeMappedCalendarYearsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public MappedCalendarYearEVO getMappedCalendarYearsItem(MappedCalendarYearPK pk) {
      return (MappedCalendarYearEVO)this.mMappedCalendarYears.get(pk);
   }

   public MappedCalendarYearEVO getMappedCalendarYearsItem() {
      if(this.mMappedCalendarYears.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mMappedCalendarYears.size());
      } else {
         Iterator iter = this.mMappedCalendarYears.values().iterator();
         return (MappedCalendarYearEVO)iter.next();
      }
   }

   public Collection<MappedFinanceCubeEVO> getMappedFinanceCubes() {
      return this.mMappedFinanceCubes != null?this.mMappedFinanceCubes.values():null;
   }

   public Map<MappedFinanceCubePK, MappedFinanceCubeEVO> getMappedFinanceCubesMap() {
      return this.mMappedFinanceCubes;
   }

   public void loadMappedFinanceCubesItem(MappedFinanceCubeEVO newItem) {
      if(this.mMappedFinanceCubes == null) {
         this.mMappedFinanceCubes = new HashMap();
      }

      this.mMappedFinanceCubes.put(newItem.getPK(), newItem);
   }

   public void addMappedFinanceCubesItem(MappedFinanceCubeEVO newItem) {
      if(this.mMappedFinanceCubes == null) {
         this.mMappedFinanceCubes = new HashMap();
      }

      MappedFinanceCubePK newPK = newItem.getPK();
      if(this.getMappedFinanceCubesItem(newPK) != null) {
         throw new RuntimeException("addMappedFinanceCubesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mMappedFinanceCubes.put(newPK, newItem);
      }
   }

   public void changeMappedFinanceCubesItem(MappedFinanceCubeEVO changedItem) {
      if(this.mMappedFinanceCubes == null) {
         throw new RuntimeException("changeMappedFinanceCubesItem: no items in collection");
      } else {
         MappedFinanceCubePK changedPK = changedItem.getPK();
         MappedFinanceCubeEVO listItem = this.getMappedFinanceCubesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeMappedFinanceCubesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteMappedFinanceCubesItem(MappedFinanceCubePK removePK) {
      MappedFinanceCubeEVO listItem = this.getMappedFinanceCubesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeMappedFinanceCubesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public MappedFinanceCubeEVO getMappedFinanceCubesItem(MappedFinanceCubePK pk) {
      return (MappedFinanceCubeEVO)this.mMappedFinanceCubes.get(pk);
   }

   public MappedFinanceCubeEVO getMappedFinanceCubesItem() {
      if(this.mMappedFinanceCubes.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mMappedFinanceCubes.size());
      } else {
         Iterator iter = this.mMappedFinanceCubes.values().iterator();
         return (MappedFinanceCubeEVO)iter.next();
      }
   }

   public Collection<MappedDimensionEVO> getMappedDimensions() {
      return this.mMappedDimensions != null?this.mMappedDimensions.values():null;
   }

   public Map<MappedDimensionPK, MappedDimensionEVO> getMappedDimensionsMap() {
      return this.mMappedDimensions;
   }

   public void loadMappedDimensionsItem(MappedDimensionEVO newItem) {
      if(this.mMappedDimensions == null) {
         this.mMappedDimensions = new HashMap();
      }

      this.mMappedDimensions.put(newItem.getPK(), newItem);
   }

   public void addMappedDimensionsItem(MappedDimensionEVO newItem) {
      if(this.mMappedDimensions == null) {
         this.mMappedDimensions = new HashMap();
      }

      MappedDimensionPK newPK = newItem.getPK();
      if(this.getMappedDimensionsItem(newPK) != null) {
         throw new RuntimeException("addMappedDimensionsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mMappedDimensions.put(newPK, newItem);
      }
   }

   public void changeMappedDimensionsItem(MappedDimensionEVO changedItem) {
      if(this.mMappedDimensions == null) {
         throw new RuntimeException("changeMappedDimensionsItem: no items in collection");
      } else {
         MappedDimensionPK changedPK = changedItem.getPK();
         MappedDimensionEVO listItem = this.getMappedDimensionsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeMappedDimensionsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteMappedDimensionsItem(MappedDimensionPK removePK) {
      MappedDimensionEVO listItem = this.getMappedDimensionsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeMappedDimensionsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public MappedDimensionEVO getMappedDimensionsItem(MappedDimensionPK pk) {
      return (MappedDimensionEVO)this.mMappedDimensions.get(pk);
   }

   public MappedDimensionEVO getMappedDimensionsItem() {
      if(this.mMappedDimensions.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mMappedDimensions.size());
      } else {
         Iterator iter = this.mMappedDimensions.values().iterator();
         return (MappedDimensionEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public MappedModelRef getEntityRef(String entityText) {
      return new MappedModelRefImpl(this.getPK(), entityText);
   }

   public void postCreateInit() {
      this.mMappedCalendarYearsAllItemsLoaded = true;
      Iterator i$;
      if(this.mMappedCalendarYears == null) {
         this.mMappedCalendarYears = new HashMap();
      } else {
         i$ = this.mMappedCalendarYears.values().iterator();

         while(i$.hasNext()) {
            MappedCalendarYearEVO child = (MappedCalendarYearEVO)i$.next();
            child.postCreateInit();
         }
      }

      this.mMappedFinanceCubesAllItemsLoaded = true;
      if(this.mMappedFinanceCubes == null) {
         this.mMappedFinanceCubes = new HashMap();
      } else {
         i$ = this.mMappedFinanceCubes.values().iterator();

         while(i$.hasNext()) {
            MappedFinanceCubeEVO child1 = (MappedFinanceCubeEVO)i$.next();
            child1.postCreateInit();
         }
      }

      this.mMappedDimensionsAllItemsLoaded = true;
      if(this.mMappedDimensions == null) {
         this.mMappedDimensions = new HashMap();
      } else {
         i$ = this.mMappedDimensions.values().iterator();

         while(i$.hasNext()) {
            MappedDimensionEVO child2 = (MappedDimensionEVO)i$.next();
            child2.postCreateInit();
         }
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("MappedModelId=");
      sb.append(String.valueOf(this.mMappedModelId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("ExternalSystemId=");
      sb.append(String.valueOf(this.mExternalSystemId));
      sb.append(' ');
      sb.append("CompanyVisId=");
      sb.append(String.valueOf(this.mCompanyVisId));
      sb.append(' ');
      sb.append("LedgerVisId=");
      sb.append(String.valueOf(this.mLedgerVisId));
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

      sb.append("MappedModel: ");
      sb.append(this.toString());
      if(this.mMappedCalendarYearsAllItemsLoaded || this.mMappedCalendarYears != null) {
         sb.delete(indent, sb.length());
         sb.append(" - MappedCalendarYears: allItemsLoaded=");
         sb.append(String.valueOf(this.mMappedCalendarYearsAllItemsLoaded));
         sb.append(" items=");
         if(this.mMappedCalendarYears == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mMappedCalendarYears.size()));
         }
      }

      if(this.mMappedFinanceCubesAllItemsLoaded || this.mMappedFinanceCubes != null) {
         sb.delete(indent, sb.length());
         sb.append(" - MappedFinanceCubes: allItemsLoaded=");
         sb.append(String.valueOf(this.mMappedFinanceCubesAllItemsLoaded));
         sb.append(" items=");
         if(this.mMappedFinanceCubes == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mMappedFinanceCubes.size()));
         }
      }

      if(this.mMappedDimensionsAllItemsLoaded || this.mMappedDimensions != null) {
         sb.delete(indent, sb.length());
         sb.append(" - MappedDimensions: allItemsLoaded=");
         sb.append(String.valueOf(this.mMappedDimensionsAllItemsLoaded));
         sb.append(" items=");
         if(this.mMappedDimensions == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mMappedDimensions.size()));
         }
      }

      Iterator var5;
      if(this.mMappedCalendarYears != null) {
         var5 = this.mMappedCalendarYears.values().iterator();

         while(var5.hasNext()) {
            MappedCalendarYearEVO listItem = (MappedCalendarYearEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mMappedFinanceCubes != null) {
         var5 = this.mMappedFinanceCubes.values().iterator();

         while(var5.hasNext()) {
            MappedFinanceCubeEVO var6 = (MappedFinanceCubeEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      if(this.mMappedDimensions != null) {
         var5 = this.mMappedDimensions.values().iterator();

         while(var5.hasNext()) {
            MappedDimensionEVO var7 = (MappedDimensionEVO)var5.next();
            var7.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(MappedCalendarYearEVO child, int newMappedCalendarYearId) {
      if(this.getMappedCalendarYearsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mMappedCalendarYears.remove(child.getPK());
         child.setMappedCalendarYearId(newMappedCalendarYearId);
         this.mMappedCalendarYears.put(child.getPK(), child);
      }
   }

   public void changeKey(MappedFinanceCubeEVO child, int newMappedFinanceCubeId) {
      if(this.getMappedFinanceCubesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mMappedFinanceCubes.remove(child.getPK());
         child.setMappedFinanceCubeId(newMappedFinanceCubeId);
         this.mMappedFinanceCubes.put(child.getPK(), child);
      }
   }

   public void changeKey(MappedDimensionEVO child, int newMappedDimensionId) {
      if(this.getMappedDimensionsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mMappedDimensions.remove(child.getPK());
         child.setMappedDimensionId(newMappedDimensionId);
         this.mMappedDimensions.put(child.getPK(), child);
      }
   }

   public void setMappedCalendarYearsAllItemsLoaded(boolean allItemsLoaded) {
      this.mMappedCalendarYearsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isMappedCalendarYearsAllItemsLoaded() {
      return this.mMappedCalendarYearsAllItemsLoaded;
   }

   public void setMappedFinanceCubesAllItemsLoaded(boolean allItemsLoaded) {
      this.mMappedFinanceCubesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isMappedFinanceCubesAllItemsLoaded() {
      return this.mMappedFinanceCubesAllItemsLoaded;
   }

   public void setMappedDimensionsAllItemsLoaded(boolean allItemsLoaded) {
      this.mMappedDimensionsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isMappedDimensionsAllItemsLoaded() {
      return this.mMappedDimensionsAllItemsLoaded;
   }
}
