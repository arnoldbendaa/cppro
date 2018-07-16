// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.extsys.ExtSysHierElementRef;
import com.cedar.cp.dto.extsys.ExtSysHierElemFeedPK;
import com.cedar.cp.dto.extsys.ExtSysHierElementCK;
import com.cedar.cp.dto.extsys.ExtSysHierElementPK;
import com.cedar.cp.dto.extsys.ExtSysHierElementRefImpl;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysDimensionEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierElemFeedEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierarchyEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysLedgerEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
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

public class ExtSysHierElementEVO implements Serializable {

   private transient ExtSysHierElementPK mPK;
   private int mExternalSystemId;
   private String mCompanyVisId;
   private String mLedgerVisId;
   private String mDimensionVisId;
   private String mHierarchyVisId;
   private String mHierElementVisId;
   private String mDescription;
   private String mParentVisId;
   private int mIdx;
   private int mCreditDebit;
   private Map<ExtSysHierElemFeedPK, ExtSysHierElemFeedEVO> mExtSysHierElemFeeds;
   protected boolean mExtSysHierElemFeedsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ExtSysHierElementEVO() {}

   public ExtSysHierElementEVO(int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDimensionVisId, String newHierarchyVisId, String newHierElementVisId, String newDescription, String newParentVisId, int newIdx, int newCreditDebit, Collection newExtSysHierElemFeeds) {
      this.mExternalSystemId = newExternalSystemId;
      this.mCompanyVisId = newCompanyVisId;
      this.mLedgerVisId = newLedgerVisId;
      this.mDimensionVisId = newDimensionVisId;
      this.mHierarchyVisId = newHierarchyVisId;
      this.mHierElementVisId = newHierElementVisId;
      this.mDescription = newDescription;
      this.mParentVisId = newParentVisId;
      this.mIdx = newIdx;
      this.mCreditDebit = newCreditDebit;
      this.setExtSysHierElemFeeds(newExtSysHierElemFeeds);
   }

   public void setExtSysHierElemFeeds(Collection<ExtSysHierElemFeedEVO> items) {
      if(items != null) {
         if(this.mExtSysHierElemFeeds == null) {
            this.mExtSysHierElemFeeds = new HashMap();
         } else {
            this.mExtSysHierElemFeeds.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ExtSysHierElemFeedEVO child = (ExtSysHierElemFeedEVO)i$.next();
            this.mExtSysHierElemFeeds.put(child.getPK(), child);
         }
      } else {
         this.mExtSysHierElemFeeds = null;
      }

   }

   public ExtSysHierElementPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExtSysHierElementPK(this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId, this.mDimensionVisId, this.mHierarchyVisId, this.mHierElementVisId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
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

   public String getDimensionVisId() {
      return this.mDimensionVisId;
   }

   public String getHierarchyVisId() {
      return this.mHierarchyVisId;
   }

   public String getHierElementVisId() {
      return this.mHierElementVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getParentVisId() {
      return this.mParentVisId;
   }

   public int getIdx() {
      return this.mIdx;
   }

   public int getCreditDebit() {
      return this.mCreditDebit;
   }

   public void setExternalSystemId(int newExternalSystemId) {
      if(this.mExternalSystemId != newExternalSystemId) {
         this.mModified = true;
         this.mExternalSystemId = newExternalSystemId;
         this.mPK = null;
      }
   }

   public void setIdx(int newIdx) {
      if(this.mIdx != newIdx) {
         this.mModified = true;
         this.mIdx = newIdx;
      }
   }

   public void setCreditDebit(int newCreditDebit) {
      if(this.mCreditDebit != newCreditDebit) {
         this.mModified = true;
         this.mCreditDebit = newCreditDebit;
      }
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

   public void setDimensionVisId(String newDimensionVisId) {
      if(this.mDimensionVisId != null && newDimensionVisId == null || this.mDimensionVisId == null && newDimensionVisId != null || this.mDimensionVisId != null && newDimensionVisId != null && !this.mDimensionVisId.equals(newDimensionVisId)) {
         this.mDimensionVisId = newDimensionVisId;
         this.mModified = true;
      }

   }

   public void setHierarchyVisId(String newHierarchyVisId) {
      if(this.mHierarchyVisId != null && newHierarchyVisId == null || this.mHierarchyVisId == null && newHierarchyVisId != null || this.mHierarchyVisId != null && newHierarchyVisId != null && !this.mHierarchyVisId.equals(newHierarchyVisId)) {
         this.mHierarchyVisId = newHierarchyVisId;
         this.mModified = true;
      }

   }

   public void setHierElementVisId(String newHierElementVisId) {
      if(this.mHierElementVisId != null && newHierElementVisId == null || this.mHierElementVisId == null && newHierElementVisId != null || this.mHierElementVisId != null && newHierElementVisId != null && !this.mHierElementVisId.equals(newHierElementVisId)) {
         this.mHierElementVisId = newHierElementVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setParentVisId(String newParentVisId) {
      if(this.mParentVisId != null && newParentVisId == null || this.mParentVisId == null && newParentVisId != null || this.mParentVisId != null && newParentVisId != null && !this.mParentVisId.equals(newParentVisId)) {
         this.mParentVisId = newParentVisId;
         this.mModified = true;
      }

   }

   public void setDetails(ExtSysHierElementEVO newDetails) {
      this.setExternalSystemId(newDetails.getExternalSystemId());
      this.setCompanyVisId(newDetails.getCompanyVisId());
      this.setLedgerVisId(newDetails.getLedgerVisId());
      this.setDimensionVisId(newDetails.getDimensionVisId());
      this.setHierarchyVisId(newDetails.getHierarchyVisId());
      this.setHierElementVisId(newDetails.getHierElementVisId());
      this.setDescription(newDetails.getDescription());
      this.setParentVisId(newDetails.getParentVisId());
      this.setIdx(newDetails.getIdx());
      this.setCreditDebit(newDetails.getCreditDebit());
   }

   public ExtSysHierElementEVO deepClone() {
      ExtSysHierElementEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ExtSysHierElementEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ExtSysHierarchyEVO parent) {
      boolean newKey = this.insertPending();
      ExtSysHierElemFeedEVO item;
      if(this.mExtSysHierElemFeeds != null) {
         for(Iterator iter = (new ArrayList(this.mExtSysHierElemFeeds.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ExtSysHierElemFeedEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      ExtSysHierElemFeedEVO item;
      if(this.mExtSysHierElemFeeds != null) {
         for(Iterator iter = this.mExtSysHierElemFeeds.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ExtSysHierElemFeedEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ExtSysHierarchyEVO parent, int startKey) {
      int nextKey = startKey;
      ExtSysHierElemFeedEVO item;
      if(this.mExtSysHierElemFeeds != null) {
         for(Iterator iter = (new ArrayList(this.mExtSysHierElemFeeds.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ExtSysHierElemFeedEVO)iter.next();
            this.changeKey(item, this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId, this.mDimensionVisId, this.mHierElementVisId, item.getDimElementVisId());
         }
      }

      return nextKey;
   }

   public Collection<ExtSysHierElemFeedEVO> getExtSysHierElemFeeds() {
      return this.mExtSysHierElemFeeds != null?this.mExtSysHierElemFeeds.values():null;
   }

   public Map<ExtSysHierElemFeedPK, ExtSysHierElemFeedEVO> getExtSysHierElemFeedsMap() {
      return this.mExtSysHierElemFeeds;
   }

   public void loadExtSysHierElemFeedsItem(ExtSysHierElemFeedEVO newItem) {
      if(this.mExtSysHierElemFeeds == null) {
         this.mExtSysHierElemFeeds = new HashMap();
      }

      this.mExtSysHierElemFeeds.put(newItem.getPK(), newItem);
   }

   public void addExtSysHierElemFeedsItem(ExtSysHierElemFeedEVO newItem) {
      if(this.mExtSysHierElemFeeds == null) {
         this.mExtSysHierElemFeeds = new HashMap();
      }

      ExtSysHierElemFeedPK newPK = newItem.getPK();
      if(this.getExtSysHierElemFeedsItem(newPK) != null) {
         throw new RuntimeException("addExtSysHierElemFeedsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mExtSysHierElemFeeds.put(newPK, newItem);
      }
   }

   public void changeExtSysHierElemFeedsItem(ExtSysHierElemFeedEVO changedItem) {
      if(this.mExtSysHierElemFeeds == null) {
         throw new RuntimeException("changeExtSysHierElemFeedsItem: no items in collection");
      } else {
         ExtSysHierElemFeedPK changedPK = changedItem.getPK();
         ExtSysHierElemFeedEVO listItem = this.getExtSysHierElemFeedsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeExtSysHierElemFeedsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteExtSysHierElemFeedsItem(ExtSysHierElemFeedPK removePK) {
      ExtSysHierElemFeedEVO listItem = this.getExtSysHierElemFeedsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeExtSysHierElemFeedsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ExtSysHierElemFeedEVO getExtSysHierElemFeedsItem(ExtSysHierElemFeedPK pk) {
      return (ExtSysHierElemFeedEVO)this.mExtSysHierElemFeeds.get(pk);
   }

   public ExtSysHierElemFeedEVO getExtSysHierElemFeedsItem() {
      if(this.mExtSysHierElemFeeds.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mExtSysHierElemFeeds.size());
      } else {
         Iterator iter = this.mExtSysHierElemFeeds.values().iterator();
         return (ExtSysHierElemFeedEVO)iter.next();
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

   public ExtSysHierElementRef getEntityRef(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysLedgerEVO evoExtSysLedger, ExtSysDimensionEVO evoExtSysDimension, ExtSysHierarchyEVO evoExtSysHierarchy, String entityText) {
      return new ExtSysHierElementRefImpl(new ExtSysHierElementCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysLedger.getPK(), evoExtSysDimension.getPK(), evoExtSysHierarchy.getPK(), this.getPK()), entityText);
   }

   public ExtSysHierElementCK getCK(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysLedgerEVO evoExtSysLedger, ExtSysDimensionEVO evoExtSysDimension, ExtSysHierarchyEVO evoExtSysHierarchy) {
      return new ExtSysHierElementCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysLedger.getPK(), evoExtSysDimension.getPK(), evoExtSysHierarchy.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mExtSysHierElemFeedsAllItemsLoaded = true;
      if(this.mExtSysHierElemFeeds == null) {
         this.mExtSysHierElemFeeds = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ExternalSystemId=");
      sb.append(String.valueOf(this.mExternalSystemId));
      sb.append(' ');
      sb.append("CompanyVisId=");
      sb.append(String.valueOf(this.mCompanyVisId));
      sb.append(' ');
      sb.append("LedgerVisId=");
      sb.append(String.valueOf(this.mLedgerVisId));
      sb.append(' ');
      sb.append("DimensionVisId=");
      sb.append(String.valueOf(this.mDimensionVisId));
      sb.append(' ');
      sb.append("HierarchyVisId=");
      sb.append(String.valueOf(this.mHierarchyVisId));
      sb.append(' ');
      sb.append("HierElementVisId=");
      sb.append(String.valueOf(this.mHierElementVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("ParentVisId=");
      sb.append(String.valueOf(this.mParentVisId));
      sb.append(' ');
      sb.append("Idx=");
      sb.append(String.valueOf(this.mIdx));
      sb.append(' ');
      sb.append("CreditDebit=");
      sb.append(String.valueOf(this.mCreditDebit));
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

      sb.append("ExtSysHierElement: ");
      sb.append(this.toString());
      if(this.mExtSysHierElemFeedsAllItemsLoaded || this.mExtSysHierElemFeeds != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ExtSysHierElemFeeds: allItemsLoaded=");
         sb.append(String.valueOf(this.mExtSysHierElemFeedsAllItemsLoaded));
         sb.append(" items=");
         if(this.mExtSysHierElemFeeds == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mExtSysHierElemFeeds.size()));
         }
      }

      if(this.mExtSysHierElemFeeds != null) {
         Iterator var5 = this.mExtSysHierElemFeeds.values().iterator();

         while(var5.hasNext()) {
            ExtSysHierElemFeedEVO listItem = (ExtSysHierElemFeedEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ExtSysHierElemFeedEVO child, int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDimensionVisId, String newHierElementVisId, String newDimElementVisId) {
      if(this.getExtSysHierElemFeedsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mExtSysHierElemFeeds.remove(child.getPK());
         child.setExternalSystemId(newExternalSystemId);
         child.setCompanyVisId(newCompanyVisId);
         child.setLedgerVisId(newLedgerVisId);
         child.setDimensionVisId(newDimensionVisId);
         child.setHierElementVisId(newHierElementVisId);
         child.setDimElementVisId(newDimElementVisId);
         this.mExtSysHierElemFeeds.put(child.getPK(), child);
      }
   }

   public void setExtSysHierElemFeedsAllItemsLoaded(boolean allItemsLoaded) {
      this.mExtSysHierElemFeedsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isExtSysHierElemFeedsAllItemsLoaded() {
      return this.mExtSysHierElemFeedsAllItemsLoaded;
   }
}
