// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.dto.dimension.CalendarSpecPK;
import com.cedar.cp.dto.dimension.CalendarYearSpecPK;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.SecurityRangePK;
import com.cedar.cp.ejb.impl.dimension.CalendarSpecEVO;
import com.cedar.cp.ejb.impl.dimension.CalendarYearSpecEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionElementEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedEVO;
import com.cedar.cp.ejb.impl.dimension.SecurityRangeEVO;
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

public class DimensionEVO implements Serializable {

   private transient DimensionPK mPK;
   private int mDimensionId;
   private String mVisId;
   private String mDescription;
   private int mType;
   private Integer mExternalSystemRef;
   private Integer mNullElementId;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<DimensionElementPK, DimensionElementEVO> mElements;
   protected boolean mElementsAllItemsLoaded;
   private Map<CalendarSpecPK, CalendarSpecEVO> mCalendarSpec;
   protected boolean mCalendarSpecAllItemsLoaded;
   private Map<CalendarYearSpecPK, CalendarYearSpecEVO> mCalendarYearSpecs;
   protected boolean mCalendarYearSpecsAllItemsLoaded;
   private Map<HierarchyPK, HierarchyEVO> mHierarchies;
   protected boolean mHierarchiesAllItemsLoaded;
   private Map<SecurityRangePK, SecurityRangeEVO> mSecurityRanges;
   protected boolean mSecurityRangesAllItemsLoaded;
   private boolean mModified;
   public static final int ACCOUNT_TYPE = 1;
   public static final int BUSINESS_TYPE = 2;
   public static final int CALENDAR_TYPE = 3;


   public DimensionEVO() {}

   public DimensionEVO(int newDimensionId, String newVisId, String newDescription, int newType, Integer newExternalSystemRef, Integer newNullElementId, int newVersionNum, Collection newElements, Collection newCalendarSpec, Collection newCalendarYearSpecs, Collection newHierarchies, Collection newSecurityRanges) {
      this.mDimensionId = newDimensionId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mType = newType;
      this.mExternalSystemRef = newExternalSystemRef;
      this.mNullElementId = newNullElementId;
      this.mVersionNum = newVersionNum;
      this.setElements(newElements);
      this.setCalendarSpec(newCalendarSpec);
      this.setCalendarYearSpecs(newCalendarYearSpecs);
      this.setHierarchies(newHierarchies);
      this.setSecurityRanges(newSecurityRanges);
   }

   public void setElements(Collection<DimensionElementEVO> items) {
      if(items != null) {
         if(this.mElements == null) {
            this.mElements = new HashMap();
         } else {
            this.mElements.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            DimensionElementEVO child = (DimensionElementEVO)i$.next();
            this.mElements.put(child.getPK(), child);
         }
      } else {
         this.mElements = null;
      }

   }

   public void setCalendarSpec(Collection<CalendarSpecEVO> items) {
      if(items != null) {
         if(this.mCalendarSpec == null) {
            this.mCalendarSpec = new HashMap();
         } else {
            this.mCalendarSpec.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CalendarSpecEVO child = (CalendarSpecEVO)i$.next();
            this.mCalendarSpec.put(child.getPK(), child);
         }
      } else {
         this.mCalendarSpec = null;
      }

   }

   public void setCalendarYearSpecs(Collection<CalendarYearSpecEVO> items) {
      if(items != null) {
         if(this.mCalendarYearSpecs == null) {
            this.mCalendarYearSpecs = new HashMap();
         } else {
            this.mCalendarYearSpecs.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CalendarYearSpecEVO child = (CalendarYearSpecEVO)i$.next();
            this.mCalendarYearSpecs.put(child.getPK(), child);
         }
      } else {
         this.mCalendarYearSpecs = null;
      }

   }

   public void setHierarchies(Collection<HierarchyEVO> items) {
      if(items != null) {
         if(this.mHierarchies == null) {
            this.mHierarchies = new HashMap();
         } else {
            this.mHierarchies.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            HierarchyEVO child = (HierarchyEVO)i$.next();
            this.mHierarchies.put(child.getPK(), child);
         }
      } else {
         this.mHierarchies = null;
      }

   }

   public void setSecurityRanges(Collection<SecurityRangeEVO> items) {
      if(items != null) {
         if(this.mSecurityRanges == null) {
            this.mSecurityRanges = new HashMap();
         } else {
            this.mSecurityRanges.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            SecurityRangeEVO child = (SecurityRangeEVO)i$.next();
            this.mSecurityRanges.put(child.getPK(), child);
         }
      } else {
         this.mSecurityRanges = null;
      }

   }

   public DimensionPK getPK() {
      if(this.mPK == null) {
         this.mPK = new DimensionPK(this.mDimensionId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
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

   public int getType() {
      return this.mType;
   }

   public Integer getExternalSystemRef() {
      return this.mExternalSystemRef;
   }

   public Integer getNullElementId() {
      return this.mNullElementId;
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

   public void setDimensionId(int newDimensionId) {
      if(this.mDimensionId != newDimensionId) {
         this.mModified = true;
         this.mDimensionId = newDimensionId;
         this.mPK = null;
      }
   }

   public void setType(int newType) {
      if(this.mType != newType) {
         this.mModified = true;
         this.mType = newType;
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

   public void setExternalSystemRef(Integer newExternalSystemRef) {
      if(this.mExternalSystemRef != null && newExternalSystemRef == null || this.mExternalSystemRef == null && newExternalSystemRef != null || this.mExternalSystemRef != null && newExternalSystemRef != null && !this.mExternalSystemRef.equals(newExternalSystemRef)) {
         this.mExternalSystemRef = newExternalSystemRef;
         this.mModified = true;
      }

   }

   public void setNullElementId(Integer newNullElementId) {
      if(this.mNullElementId != null && newNullElementId == null || this.mNullElementId == null && newNullElementId != null || this.mNullElementId != null && newNullElementId != null && !this.mNullElementId.equals(newNullElementId)) {
         this.mNullElementId = newNullElementId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(DimensionEVO newDetails) {
      this.setDimensionId(newDetails.getDimensionId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setType(newDetails.getType());
      this.setExternalSystemRef(newDetails.getExternalSystemRef());
      this.setNullElementId(newDetails.getNullElementId());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public DimensionEVO deepClone() {
      DimensionEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (DimensionEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mDimensionId > 0) {
         newKey = true;
         this.mDimensionId = 0;
      } else if(this.mDimensionId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      Iterator iter;
      DimensionElementEVO item;
      if(this.mElements != null) {
         for(iter = (new ArrayList(this.mElements.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (DimensionElementEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      CalendarSpecEVO item1;
      if(this.mCalendarSpec != null) {
         for(iter = (new ArrayList(this.mCalendarSpec.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (CalendarSpecEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

      CalendarYearSpecEVO item2;
      if(this.mCalendarYearSpecs != null) {
         for(iter = (new ArrayList(this.mCalendarYearSpecs.values())).iterator(); iter.hasNext(); item2.prepareForInsert(this)) {
            item2 = (CalendarYearSpecEVO)iter.next();
            if(newKey) {
               item2.setInsertPending();
            }
         }
      }

      HierarchyEVO item3;
      if(this.mHierarchies != null) {
         for(iter = (new ArrayList(this.mHierarchies.values())).iterator(); iter.hasNext(); item3.prepareForInsert(this)) {
            item3 = (HierarchyEVO)iter.next();
            if(newKey) {
               item3.setInsertPending();
            }
         }
      }

      SecurityRangeEVO item4;
      if(this.mSecurityRanges != null) {
         for(iter = (new ArrayList(this.mSecurityRanges.values())).iterator(); iter.hasNext(); item4.prepareForInsert(this)) {
            item4 = (SecurityRangeEVO)iter.next();
            if(newKey) {
               item4.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mDimensionId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      DimensionElementEVO item;
      if(this.mElements != null) {
         for(iter = this.mElements.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (DimensionElementEVO)iter.next();
         }
      }

      CalendarSpecEVO item1;
      if(this.mCalendarSpec != null) {
         for(iter = this.mCalendarSpec.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (CalendarSpecEVO)iter.next();
         }
      }

      CalendarYearSpecEVO item2;
      if(this.mCalendarYearSpecs != null) {
         for(iter = this.mCalendarYearSpecs.values().iterator(); iter.hasNext(); returnCount = item2.getInsertCount(returnCount)) {
            item2 = (CalendarYearSpecEVO)iter.next();
         }
      }

      HierarchyEVO item4;
      if(this.mHierarchies != null) {
         for(iter = this.mHierarchies.values().iterator(); iter.hasNext(); returnCount = item4.getInsertCount(returnCount)) {
            item4 = (HierarchyEVO)iter.next();
         }
      }

      SecurityRangeEVO item3;
      if(this.mSecurityRanges != null) {
         for(iter = this.mSecurityRanges.values().iterator(); iter.hasNext(); returnCount = item3.getInsertCount(returnCount)) {
            item3 = (SecurityRangeEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mDimensionId < 1) {
         this.mDimensionId = startKey;
         nextKey = startKey + 1;
      }

      Iterator iter;
      if(this.mElements != null) {
         iter = (new ArrayList(this.mElements.values())).iterator();

         while(iter.hasNext()) {
            DimensionElementEVO item = (DimensionElementEVO)iter.next();
            item.setDimensionId(this.mDimensionId);
            DimensionElementPK origPK = item.getPK();
            nextKey = item.assignNextKey(this, nextKey);
            DimensionElementPK newPK = item.getPK();
            if(!origPK.equals(newPK)) {
               this.handleNewDimensionElementKey(origPK, newPK);
            }
         }
      }

      CalendarSpecEVO item1;
      if(this.mCalendarSpec != null) {
         for(iter = (new ArrayList(this.mCalendarSpec.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (CalendarSpecEVO)iter.next();
            item1.setDimensionId(this.mDimensionId);
         }
      }

      CalendarYearSpecEVO item2;
      if(this.mCalendarYearSpecs != null) {
         for(iter = (new ArrayList(this.mCalendarYearSpecs.values())).iterator(); iter.hasNext(); nextKey = item2.assignNextKey(this, nextKey)) {
            item2 = (CalendarYearSpecEVO)iter.next();
            item2.setDimensionId(this.mDimensionId);
         }
      }

      HierarchyEVO item4;
      if(this.mHierarchies != null) {
         for(iter = (new ArrayList(this.mHierarchies.values())).iterator(); iter.hasNext(); nextKey = item4.assignNextKey(this, nextKey)) {
            item4 = (HierarchyEVO)iter.next();
            item4.setDimensionId(this.mDimensionId);
         }
      }

      SecurityRangeEVO item3;
      if(this.mSecurityRanges != null) {
         for(iter = (new ArrayList(this.mSecurityRanges.values())).iterator(); iter.hasNext(); nextKey = item3.assignNextKey(this, nextKey)) {
            item3 = (SecurityRangeEVO)iter.next();
            item3.setDimensionId(this.mDimensionId);
         }
      }

      return nextKey;
   }

   private void handleNewDimensionElementKey(DimensionElementPK origPK, DimensionElementPK newPK) {
      this.changeDimensionElementKey(origPK.getDimensionElementId(), newPK.getDimensionElementId());
   }

   public Collection<DimensionElementEVO> getElements() {
      return this.mElements != null?this.mElements.values():null;
   }

   public Map<DimensionElementPK, DimensionElementEVO> getElementsMap() {
      return this.mElements;
   }

   public void loadElementsItem(DimensionElementEVO newItem) {
      if(this.mElements == null) {
         this.mElements = new HashMap();
      }

      this.mElements.put(newItem.getPK(), newItem);
   }

   public void addElementsItem(DimensionElementEVO newItem) {
      if(this.mElements == null) {
         this.mElements = new HashMap();
      }

      DimensionElementPK newPK = newItem.getPK();
      if(this.getElementsItem(newPK) != null) {
         throw new RuntimeException("addElementsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mElements.put(newPK, newItem);
      }
   }

   public void changeElementsItem(DimensionElementEVO changedItem) {
      if(this.mElements == null) {
         throw new RuntimeException("changeElementsItem: no items in collection");
      } else {
         DimensionElementPK changedPK = changedItem.getPK();
         DimensionElementEVO listItem = this.getElementsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeElementsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteElementsItem(DimensionElementPK removePK) {
      DimensionElementEVO listItem = this.getElementsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeElementsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public DimensionElementEVO getElementsItem(DimensionElementPK pk) {
      return (DimensionElementEVO)this.mElements.get(pk);
   }

   public DimensionElementEVO getElementsItem() {
      if(this.mElements.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mElements.size());
      } else {
         Iterator iter = this.mElements.values().iterator();
         return (DimensionElementEVO)iter.next();
      }
   }

   public Collection<CalendarSpecEVO> getCalendarSpec() {
      return this.mCalendarSpec != null?this.mCalendarSpec.values():null;
   }

   public Map<CalendarSpecPK, CalendarSpecEVO> getCalendarSpecMap() {
      return this.mCalendarSpec;
   }

   public void loadCalendarSpecItem(CalendarSpecEVO newItem) {
      if(this.mCalendarSpec == null) {
         this.mCalendarSpec = new HashMap();
      }

      this.mCalendarSpec.put(newItem.getPK(), newItem);
   }

   public void addCalendarSpecItem(CalendarSpecEVO newItem) {
      if(this.mCalendarSpec == null) {
         this.mCalendarSpec = new HashMap();
      }

      CalendarSpecPK newPK = newItem.getPK();
      if(this.getCalendarSpecItem(newPK) != null) {
         throw new RuntimeException("addCalendarSpecItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mCalendarSpec.put(newPK, newItem);
      }
   }

   public void changeCalendarSpecItem(CalendarSpecEVO changedItem) {
      if(this.mCalendarSpec == null) {
         throw new RuntimeException("changeCalendarSpecItem: no items in collection");
      } else {
         CalendarSpecPK changedPK = changedItem.getPK();
         CalendarSpecEVO listItem = this.getCalendarSpecItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeCalendarSpecItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteCalendarSpecItem(CalendarSpecPK removePK) {
      CalendarSpecEVO listItem = this.getCalendarSpecItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeCalendarSpecItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public CalendarSpecEVO getCalendarSpecItem(CalendarSpecPK pk) {
      return (CalendarSpecEVO)this.mCalendarSpec.get(pk);
   }

   public CalendarSpecEVO getCalendarSpecItem() {
      if(this.mCalendarSpec.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mCalendarSpec.size());
      } else {
         Iterator iter = this.mCalendarSpec.values().iterator();
         return (CalendarSpecEVO)iter.next();
      }
   }

   public Collection<CalendarYearSpecEVO> getCalendarYearSpecs() {
      return this.mCalendarYearSpecs != null?this.mCalendarYearSpecs.values():null;
   }

   public Map<CalendarYearSpecPK, CalendarYearSpecEVO> getCalendarYearSpecsMap() {
      return this.mCalendarYearSpecs;
   }

   public void loadCalendarYearSpecsItem(CalendarYearSpecEVO newItem) {
      if(this.mCalendarYearSpecs == null) {
         this.mCalendarYearSpecs = new HashMap();
      }

      this.mCalendarYearSpecs.put(newItem.getPK(), newItem);
   }

   public void addCalendarYearSpecsItem(CalendarYearSpecEVO newItem) {
      if(this.mCalendarYearSpecs == null) {
         this.mCalendarYearSpecs = new HashMap();
      }

      CalendarYearSpecPK newPK = newItem.getPK();
      if(this.getCalendarYearSpecsItem(newPK) != null) {
         throw new RuntimeException("addCalendarYearSpecsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mCalendarYearSpecs.put(newPK, newItem);
      }
   }

   public void changeCalendarYearSpecsItem(CalendarYearSpecEVO changedItem) {
      if(this.mCalendarYearSpecs == null) {
         throw new RuntimeException("changeCalendarYearSpecsItem: no items in collection");
      } else {
         CalendarYearSpecPK changedPK = changedItem.getPK();
         CalendarYearSpecEVO listItem = this.getCalendarYearSpecsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeCalendarYearSpecsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteCalendarYearSpecsItem(CalendarYearSpecPK removePK) {
      CalendarYearSpecEVO listItem = this.getCalendarYearSpecsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeCalendarYearSpecsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public CalendarYearSpecEVO getCalendarYearSpecsItem(CalendarYearSpecPK pk) {
      return (CalendarYearSpecEVO)this.mCalendarYearSpecs.get(pk);
   }

   public CalendarYearSpecEVO getCalendarYearSpecsItem() {
      if(this.mCalendarYearSpecs.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mCalendarYearSpecs.size());
      } else {
         Iterator iter = this.mCalendarYearSpecs.values().iterator();
         return (CalendarYearSpecEVO)iter.next();
      }
   }

   public Collection<HierarchyEVO> getHierarchies() {
      return this.mHierarchies != null?this.mHierarchies.values():null;
   }

   public Map<HierarchyPK, HierarchyEVO> getHierarchiesMap() {
      return this.mHierarchies;
   }

   public void loadHierarchiesItem(HierarchyEVO newItem) {
      if(this.mHierarchies == null) {
         this.mHierarchies = new HashMap();
      }

      this.mHierarchies.put(newItem.getPK(), newItem);
   }

   public void addHierarchiesItem(HierarchyEVO newItem) {
      if(this.mHierarchies == null) {
         this.mHierarchies = new HashMap();
      }

      HierarchyPK newPK = newItem.getPK();
      if(this.getHierarchiesItem(newPK) != null) {
         throw new RuntimeException("addHierarchiesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mHierarchies.put(newPK, newItem);
      }
   }

   public void changeHierarchiesItem(HierarchyEVO changedItem) {
      if(this.mHierarchies == null) {
         throw new RuntimeException("changeHierarchiesItem: no items in collection");
      } else {
         HierarchyPK changedPK = changedItem.getPK();
         HierarchyEVO listItem = this.getHierarchiesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeHierarchiesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteHierarchiesItem(HierarchyPK removePK) {
      HierarchyEVO listItem = this.getHierarchiesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeHierarchiesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public HierarchyEVO getHierarchiesItem(HierarchyPK pk) {
      return (HierarchyEVO)this.mHierarchies.get(pk);
   }

   public HierarchyEVO getHierarchiesItem() {
      if(this.mHierarchies.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mHierarchies.size());
      } else {
         Iterator iter = this.mHierarchies.values().iterator();
         return (HierarchyEVO)iter.next();
      }
   }

   public Collection<SecurityRangeEVO> getSecurityRanges() {
      return this.mSecurityRanges != null?this.mSecurityRanges.values():null;
   }

   public Map<SecurityRangePK, SecurityRangeEVO> getSecurityRangesMap() {
      return this.mSecurityRanges;
   }

   public void loadSecurityRangesItem(SecurityRangeEVO newItem) {
      if(this.mSecurityRanges == null) {
         this.mSecurityRanges = new HashMap();
      }

      this.mSecurityRanges.put(newItem.getPK(), newItem);
   }

   public void addSecurityRangesItem(SecurityRangeEVO newItem) {
      if(this.mSecurityRanges == null) {
         this.mSecurityRanges = new HashMap();
      }

      SecurityRangePK newPK = newItem.getPK();
      if(this.getSecurityRangesItem(newPK) != null) {
         throw new RuntimeException("addSecurityRangesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mSecurityRanges.put(newPK, newItem);
      }
   }

   public void changeSecurityRangesItem(SecurityRangeEVO changedItem) {
      if(this.mSecurityRanges == null) {
         throw new RuntimeException("changeSecurityRangesItem: no items in collection");
      } else {
         SecurityRangePK changedPK = changedItem.getPK();
         SecurityRangeEVO listItem = this.getSecurityRangesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeSecurityRangesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteSecurityRangesItem(SecurityRangePK removePK) {
      SecurityRangeEVO listItem = this.getSecurityRangesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeSecurityRangesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public SecurityRangeEVO getSecurityRangesItem(SecurityRangePK pk) {
      return (SecurityRangeEVO)this.mSecurityRanges.get(pk);
   }

   public SecurityRangeEVO getSecurityRangesItem() {
      if(this.mSecurityRanges.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mSecurityRanges.size());
      } else {
         Iterator iter = this.mSecurityRanges.values().iterator();
         return (SecurityRangeEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public DimensionRef getEntityRef() {
      return new DimensionRefImpl(this.getPK(), this.mVisId, this.mType);
   }

   public void postCreateInit() {
      this.mElementsAllItemsLoaded = true;
      if(this.mElements == null) {
         this.mElements = new HashMap();
      }

      this.mCalendarSpecAllItemsLoaded = true;
      if(this.mCalendarSpec == null) {
         this.mCalendarSpec = new HashMap();
      }

      this.mCalendarYearSpecsAllItemsLoaded = true;
      if(this.mCalendarYearSpecs == null) {
         this.mCalendarYearSpecs = new HashMap();
      }

      this.mHierarchiesAllItemsLoaded = true;
      Iterator i$;
      if(this.mHierarchies == null) {
         this.mHierarchies = new HashMap();
      } else {
         i$ = this.mHierarchies.values().iterator();

         while(i$.hasNext()) {
            HierarchyEVO child = (HierarchyEVO)i$.next();
            child.postCreateInit();
         }
      }

      this.mSecurityRangesAllItemsLoaded = true;
      if(this.mSecurityRanges == null) {
         this.mSecurityRanges = new HashMap();
      } else {
         i$ = this.mSecurityRanges.values().iterator();

         while(i$.hasNext()) {
            SecurityRangeEVO child1 = (SecurityRangeEVO)i$.next();
            child1.postCreateInit();
         }
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("DimensionId=");
      sb.append(String.valueOf(this.mDimensionId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("Type=");
      sb.append(String.valueOf(this.mType));
      sb.append(' ');
      sb.append("ExternalSystemRef=");
      sb.append(String.valueOf(this.mExternalSystemRef));
      sb.append(' ');
      sb.append("NullElementId=");
      sb.append(String.valueOf(this.mNullElementId));
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

      sb.append("Dimension: ");
      sb.append(this.toString());
      if(this.mElementsAllItemsLoaded || this.mElements != null) {
         sb.delete(indent, sb.length());
         sb.append(" - Elements: allItemsLoaded=");
         sb.append(String.valueOf(this.mElementsAllItemsLoaded));
         sb.append(" items=");
         if(this.mElements == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mElements.size()));
         }
      }

      if(this.mCalendarSpecAllItemsLoaded || this.mCalendarSpec != null) {
         sb.delete(indent, sb.length());
         sb.append(" - CalendarSpec: allItemsLoaded=");
         sb.append(String.valueOf(this.mCalendarSpecAllItemsLoaded));
         sb.append(" items=");
         if(this.mCalendarSpec == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mCalendarSpec.size()));
         }
      }

      if(this.mCalendarYearSpecsAllItemsLoaded || this.mCalendarYearSpecs != null) {
         sb.delete(indent, sb.length());
         sb.append(" - CalendarYearSpecs: allItemsLoaded=");
         sb.append(String.valueOf(this.mCalendarYearSpecsAllItemsLoaded));
         sb.append(" items=");
         if(this.mCalendarYearSpecs == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mCalendarYearSpecs.size()));
         }
      }

      if(this.mHierarchiesAllItemsLoaded || this.mHierarchies != null) {
         sb.delete(indent, sb.length());
         sb.append(" - Hierarchies: allItemsLoaded=");
         sb.append(String.valueOf(this.mHierarchiesAllItemsLoaded));
         sb.append(" items=");
         if(this.mHierarchies == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mHierarchies.size()));
         }
      }

      if(this.mSecurityRangesAllItemsLoaded || this.mSecurityRanges != null) {
         sb.delete(indent, sb.length());
         sb.append(" - SecurityRanges: allItemsLoaded=");
         sb.append(String.valueOf(this.mSecurityRangesAllItemsLoaded));
         sb.append(" items=");
         if(this.mSecurityRanges == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mSecurityRanges.size()));
         }
      }

      Iterator var5;
      if(this.mElements != null) {
         var5 = this.mElements.values().iterator();

         while(var5.hasNext()) {
            DimensionElementEVO listItem = (DimensionElementEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mCalendarSpec != null) {
         var5 = this.mCalendarSpec.values().iterator();

         while(var5.hasNext()) {
            CalendarSpecEVO var6 = (CalendarSpecEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      if(this.mCalendarYearSpecs != null) {
         var5 = this.mCalendarYearSpecs.values().iterator();

         while(var5.hasNext()) {
            CalendarYearSpecEVO var7 = (CalendarYearSpecEVO)var5.next();
            var7.print(indent + 4);
         }
      }

      if(this.mHierarchies != null) {
         var5 = this.mHierarchies.values().iterator();

         while(var5.hasNext()) {
            HierarchyEVO var9 = (HierarchyEVO)var5.next();
            var9.print(indent + 4);
         }
      }

      if(this.mSecurityRanges != null) {
         var5 = this.mSecurityRanges.values().iterator();

         while(var5.hasNext()) {
            SecurityRangeEVO var8 = (SecurityRangeEVO)var5.next();
            var8.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(DimensionElementEVO child, int newDimensionElementId) {
      if(this.getElementsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mElements.remove(child.getPK());
         child.setDimensionElementId(newDimensionElementId);
         this.mElements.put(child.getPK(), child);
      }
   }

   public void changeKey(CalendarSpecEVO child, int newCalendarSpecId) {
      if(this.getCalendarSpecItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mCalendarSpec.remove(child.getPK());
         child.setCalendarSpecId(newCalendarSpecId);
         this.mCalendarSpec.put(child.getPK(), child);
      }
   }

   public void changeKey(CalendarYearSpecEVO child, int newCalendarYearSpecId) {
      if(this.getCalendarYearSpecsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mCalendarYearSpecs.remove(child.getPK());
         child.setCalendarYearSpecId(newCalendarYearSpecId);
         this.mCalendarYearSpecs.put(child.getPK(), child);
      }
   }

   public void changeKey(HierarchyEVO child, int newHierarchyId) {
      if(this.getHierarchiesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mHierarchies.remove(child.getPK());
         child.setHierarchyId(newHierarchyId);
         this.mHierarchies.put(child.getPK(), child);
      }
   }

   public void changeKey(SecurityRangeEVO child, int newSecurityRangeId) {
      if(this.getSecurityRangesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mSecurityRanges.remove(child.getPK());
         child.setSecurityRangeId(newSecurityRangeId);
         this.mSecurityRanges.put(child.getPK(), child);
      }
   }

   public void changeDimensionElementKey(int oldId, int newId) {
      if(this.mHierarchies != null) {
         Iterator hIter = this.mHierarchies.values().iterator();

         while(hIter.hasNext()) {
            HierarchyEVO hEVO = (HierarchyEVO)hIter.next();
            if(hEVO.getHierarchyElements() != null) {
               Iterator heIter = hEVO.getHierarchyElements().iterator();

               while(heIter.hasNext()) {
                  HierarchyElementEVO heEVO = (HierarchyElementEVO)heIter.next();
                  if(heEVO.getFeederElements() != null) {
                     Iterator hefIter = (new ArrayList(heEVO.getFeederElements())).iterator();

                     while(hefIter.hasNext()) {
                        HierarchyElementFeedEVO hefEVO = (HierarchyElementFeedEVO)hefIter.next();
                        if(hefEVO.getDimensionElementId() == oldId) {
                           heEVO.changeKey(hefEVO, hefEVO.getHierarchyElementId(), newId);
                        }
                     }
                  }
               }
            }
         }
      }

      if(this.mNullElementId != null && this.mNullElementId.intValue() == oldId) {
         this.mNullElementId = Integer.valueOf(newId);
      }

   }

   public DimensionEVO(int newDimensionId, String newVisId, String newDescription, int newType, Integer newExternalSystemRef, int newVersionNum, Collection newElements, Collection newHierarchies, Collection newSecurityRanges) {
      this(newDimensionId, newVisId, newDescription, newType, newExternalSystemRef, (Integer)null, newVersionNum, newElements, new ArrayList(), new ArrayList(), newHierarchies, newSecurityRanges);
   }

   public void setElementsAllItemsLoaded(boolean allItemsLoaded) {
      this.mElementsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isElementsAllItemsLoaded() {
      return this.mElementsAllItemsLoaded;
   }

   public void setCalendarSpecAllItemsLoaded(boolean allItemsLoaded) {
      this.mCalendarSpecAllItemsLoaded = allItemsLoaded;
   }

   public boolean isCalendarSpecAllItemsLoaded() {
      return this.mCalendarSpecAllItemsLoaded;
   }

   public void setCalendarYearSpecsAllItemsLoaded(boolean allItemsLoaded) {
      this.mCalendarYearSpecsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isCalendarYearSpecsAllItemsLoaded() {
      return this.mCalendarYearSpecsAllItemsLoaded;
   }

   public void setHierarchiesAllItemsLoaded(boolean allItemsLoaded) {
      this.mHierarchiesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isHierarchiesAllItemsLoaded() {
      return this.mHierarchiesAllItemsLoaded;
   }

   public void setSecurityRangesAllItemsLoaded(boolean allItemsLoaded) {
      this.mSecurityRangesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isSecurityRangesAllItemsLoaded() {
      return this.mSecurityRangesAllItemsLoaded;
   }
}
