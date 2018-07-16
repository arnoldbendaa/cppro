// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.dimension.HierarchyElementRef;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.HierarchyElementRefImpl;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedEVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HierarchyElementEVO implements Serializable {

   private transient HierarchyElementPK mPK;
   private int mHierarchyElementId;
   private int mHierarchyId;
   private int mParentId;
   private int mChildIndex;
   private String mVisId;
   private String mDescription;
   private int mCreditDebit;
   private Integer mAugParentId;
   private Integer mAugChildIndex;
   private Integer mAugCreditDebit;
   private Integer mCalElemType;
   private Map<HierarchyElementFeedPK, HierarchyElementFeedEVO> mFeederElements;
   protected boolean mFeederElementsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int NOT_APPLICABLE = 0;
   public static final int CREDIT_TYPE = 1;
   public static final int DEBIT_TYPE = 2;


   public HierarchyElementEVO() {}

   public HierarchyElementEVO(int newHierarchyElementId, int newHierarchyId, int newParentId, int newChildIndex, String newVisId, String newDescription, int newCreditDebit, Integer newAugParentId, Integer newAugChildIndex, Integer newAugCreditDebit, Integer newCalElemType, Collection newFeederElements) {
      this.mHierarchyElementId = newHierarchyElementId;
      this.mHierarchyId = newHierarchyId;
      this.mParentId = newParentId;
      this.mChildIndex = newChildIndex;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mCreditDebit = newCreditDebit;
      this.mAugParentId = newAugParentId;
      this.mAugChildIndex = newAugChildIndex;
      this.mAugCreditDebit = newAugCreditDebit;
      this.mCalElemType = newCalElemType;
      this.setFeederElements(newFeederElements);
   }

   public void setFeederElements(Collection<HierarchyElementFeedEVO> items) {
      if(items != null) {
         if(this.mFeederElements == null) {
            this.mFeederElements = new HashMap();
         } else {
            this.mFeederElements.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            HierarchyElementFeedEVO child = (HierarchyElementFeedEVO)i$.next();
            this.mFeederElements.put(child.getPK(), child);
         }
      } else {
         this.mFeederElements = null;
      }

   }

   public HierarchyElementPK getPK() {
      if(this.mPK == null) {
         this.mPK = new HierarchyElementPK(this.mHierarchyElementId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getHierarchyElementId() {
      return this.mHierarchyElementId;
   }

   public int getHierarchyId() {
      return this.mHierarchyId;
   }

   public int getParentId() {
      return this.mParentId;
   }

   public int getChildIndex() {
      return this.mChildIndex;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getCreditDebit() {
      return this.mCreditDebit;
   }

   public Integer getAugParentId() {
      return this.mAugParentId;
   }

   public Integer getAugChildIndex() {
      return this.mAugChildIndex;
   }

   public Integer getAugCreditDebit() {
      return this.mAugCreditDebit;
   }

   public Integer getCalElemType() {
      return this.mCalElemType;
   }

   public void setHierarchyElementId(int newHierarchyElementId) {
      if(this.mHierarchyElementId != newHierarchyElementId) {
         this.mModified = true;
         this.mHierarchyElementId = newHierarchyElementId;
         this.mPK = null;
      }
   }

   public void setHierarchyId(int newHierarchyId) {
      if(this.mHierarchyId != newHierarchyId) {
         this.mModified = true;
         this.mHierarchyId = newHierarchyId;
      }
   }

   public void setParentId(int newParentId) {
      if(this.mParentId != newParentId) {
         this.mModified = true;
         this.mParentId = newParentId;
      }
   }

   public void setChildIndex(int newChildIndex) {
      if(this.mChildIndex != newChildIndex) {
         this.mModified = true;
         this.mChildIndex = newChildIndex;
      }
   }

   public void setCreditDebit(int newCreditDebit) {
      if(this.mCreditDebit != newCreditDebit) {
         this.mModified = true;
         this.mCreditDebit = newCreditDebit;
      }
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

   public void setAugParentId(Integer newAugParentId) {
      if(this.mAugParentId != null && newAugParentId == null || this.mAugParentId == null && newAugParentId != null || this.mAugParentId != null && newAugParentId != null && !this.mAugParentId.equals(newAugParentId)) {
         this.mAugParentId = newAugParentId;
         this.mModified = true;
      }

   }

   public void setAugChildIndex(Integer newAugChildIndex) {
      if(this.mAugChildIndex != null && newAugChildIndex == null || this.mAugChildIndex == null && newAugChildIndex != null || this.mAugChildIndex != null && newAugChildIndex != null && !this.mAugChildIndex.equals(newAugChildIndex)) {
         this.mAugChildIndex = newAugChildIndex;
         this.mModified = true;
      }

   }

   public void setAugCreditDebit(Integer newAugCreditDebit) {
      if(this.mAugCreditDebit != null && newAugCreditDebit == null || this.mAugCreditDebit == null && newAugCreditDebit != null || this.mAugCreditDebit != null && newAugCreditDebit != null && !this.mAugCreditDebit.equals(newAugCreditDebit)) {
         this.mAugCreditDebit = newAugCreditDebit;
         this.mModified = true;
      }

   }

   public void setCalElemType(Integer newCalElemType) {
      if(this.mCalElemType != null && newCalElemType == null || this.mCalElemType == null && newCalElemType != null || this.mCalElemType != null && newCalElemType != null && !this.mCalElemType.equals(newCalElemType)) {
         this.mCalElemType = newCalElemType;
         this.mModified = true;
      }

   }

   public void setDetails(HierarchyElementEVO newDetails) {
      this.setHierarchyElementId(newDetails.getHierarchyElementId());
      this.setHierarchyId(newDetails.getHierarchyId());
      this.setParentId(newDetails.getParentId());
      this.setChildIndex(newDetails.getChildIndex());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setCreditDebit(newDetails.getCreditDebit());
      this.setAugParentId(newDetails.getAugParentId());
      this.setAugChildIndex(newDetails.getAugChildIndex());
      this.setAugCreditDebit(newDetails.getAugCreditDebit());
      this.setCalElemType(newDetails.getCalElemType());
   }

   public HierarchyElementEVO deepClone() {
      HierarchyElementEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (HierarchyElementEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(HierarchyEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mHierarchyElementId > 0) {
         newKey = true;
         if(parent == null) {
            this.setHierarchyElementId(-this.mHierarchyElementId);
         } else {
            parent.changeKey(this, -this.mHierarchyElementId);
         }
      } else if(this.mHierarchyElementId < 1) {
         newKey = true;
      }

      HierarchyElementFeedEVO item;
      if(this.mFeederElements != null) {
         for(Iterator iter = (new ArrayList(this.mFeederElements.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (HierarchyElementFeedEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mHierarchyElementId < 1) {
         returnCount = startCount + 1;
      }

      HierarchyElementFeedEVO item;
      if(this.mFeederElements != null) {
         for(Iterator iter = this.mFeederElements.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (HierarchyElementFeedEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(HierarchyEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mHierarchyElementId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      HierarchyElementFeedEVO item;
      if(this.mFeederElements != null) {
         for(Iterator iter = (new ArrayList(this.mFeederElements.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (HierarchyElementFeedEVO)iter.next();
            this.changeKey(item, this.mHierarchyElementId, item.getDimensionElementId());
         }
      }

      return nextKey;
   }

   public Collection<HierarchyElementFeedEVO> getFeederElements() {
      return this.mFeederElements != null?this.mFeederElements.values():null;
   }

   public Map<HierarchyElementFeedPK, HierarchyElementFeedEVO> getFeederElementsMap() {
      return this.mFeederElements;
   }

   public void loadFeederElementsItem(HierarchyElementFeedEVO newItem) {
      if(this.mFeederElements == null) {
         this.mFeederElements = new HashMap();
      }

      this.mFeederElements.put(newItem.getPK(), newItem);
   }

   public void addFeederElementsItem(HierarchyElementFeedEVO newItem) {
      if(this.mFeederElements == null) {
         this.mFeederElements = new HashMap();
      }

      HierarchyElementFeedPK newPK = newItem.getPK();
      if(this.getFeederElementsItem(newPK) != null) {
         throw new RuntimeException("addFeederElementsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mFeederElements.put(newPK, newItem);
      }
   }

   public void changeFeederElementsItem(HierarchyElementFeedEVO changedItem) {
      if(this.mFeederElements == null) {
         throw new RuntimeException("changeFeederElementsItem: no items in collection");
      } else {
         HierarchyElementFeedPK changedPK = changedItem.getPK();
         HierarchyElementFeedEVO listItem = this.getFeederElementsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeFeederElementsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteFeederElementsItem(HierarchyElementFeedPK removePK) {
      HierarchyElementFeedEVO listItem = this.getFeederElementsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeFeederElementsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public HierarchyElementFeedEVO getFeederElementsItem(HierarchyElementFeedPK pk) {
      return (HierarchyElementFeedEVO)this.mFeederElements.get(pk);
   }

   public HierarchyElementFeedEVO getFeederElementsItem() {
      if(this.mFeederElements.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mFeederElements.size());
      } else {
         Iterator iter = this.mFeederElements.values().iterator();
         return (HierarchyElementFeedEVO)iter.next();
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

   public HierarchyElementRef getEntityRef(DimensionEVO evoDimension, HierarchyEVO evoHierarchy) {
      return new HierarchyElementRefImpl(new HierarchyElementCK(evoDimension.getPK(), evoHierarchy.getPK(), this.getPK()), this.mVisId);
   }

   public HierarchyElementCK getCK(DimensionEVO evoDimension, HierarchyEVO evoHierarchy) {
      return new HierarchyElementCK(evoDimension.getPK(), evoHierarchy.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mFeederElementsAllItemsLoaded = true;
      if(this.mFeederElements == null) {
         this.mFeederElements = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("HierarchyElementId=");
      sb.append(String.valueOf(this.mHierarchyElementId));
      sb.append(' ');
      sb.append("HierarchyId=");
      sb.append(String.valueOf(this.mHierarchyId));
      sb.append(' ');
      sb.append("ParentId=");
      sb.append(String.valueOf(this.mParentId));
      sb.append(' ');
      sb.append("ChildIndex=");
      sb.append(String.valueOf(this.mChildIndex));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("CreditDebit=");
      sb.append(String.valueOf(this.mCreditDebit));
      sb.append(' ');
      sb.append("AugParentId=");
      sb.append(String.valueOf(this.mAugParentId));
      sb.append(' ');
      sb.append("AugChildIndex=");
      sb.append(String.valueOf(this.mAugChildIndex));
      sb.append(' ');
      sb.append("AugCreditDebit=");
      sb.append(String.valueOf(this.mAugCreditDebit));
      sb.append(' ');
      sb.append("CalElemType=");
      sb.append(String.valueOf(this.mCalElemType));
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

      sb.append("HierarchyElement: ");
      sb.append(this.toString());
      if(this.mFeederElementsAllItemsLoaded || this.mFeederElements != null) {
         sb.delete(indent, sb.length());
         sb.append(" - FeederElements: allItemsLoaded=");
         sb.append(String.valueOf(this.mFeederElementsAllItemsLoaded));
         sb.append(" items=");
         if(this.mFeederElements == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mFeederElements.size()));
         }
      }

      if(this.mFeederElements != null) {
         Iterator var5 = this.mFeederElements.values().iterator();

         while(var5.hasNext()) {
            HierarchyElementFeedEVO listItem = (HierarchyElementFeedEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(HierarchyElementFeedEVO child, int newHierarchyElementId, int newDimensionElementId) {
      if(this.getFeederElementsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mFeederElements.remove(child.getPK());
         child.setHierarchyElementId(newHierarchyElementId);
         child.setDimensionElementId(newDimensionElementId);
         this.mFeederElements.put(child.getPK(), child);
      }
   }

   public void setFeederElementsAllItemsLoaded(boolean allItemsLoaded) {
      this.mFeederElementsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isFeederElementsAllItemsLoaded() {
      return this.mFeederElementsAllItemsLoaded;
   }
}
