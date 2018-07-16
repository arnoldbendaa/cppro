// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.extsys.ExtSysDimensionRef;
import com.cedar.cp.dto.extsys.ExtSysDimElementPK;
import com.cedar.cp.dto.extsys.ExtSysDimensionCK;
import com.cedar.cp.dto.extsys.ExtSysDimensionPK;
import com.cedar.cp.dto.extsys.ExtSysDimensionRefImpl;
import com.cedar.cp.dto.extsys.ExtSysHierarchyPK;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysDimElementEVO;
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

public class ExtSysDimensionEVO implements Serializable {

   private transient ExtSysDimensionPK mPK;
   private int mExternalSystemId;
   private String mCompanyVisId;
   private String mLedgerVisId;
   private String mDimensionVisId;
   private String mDescription;
   private int mDimensionType;
   private int mImportColumnIndex;
   private Map<ExtSysDimElementPK, ExtSysDimElementEVO> mExtSysDimElements;
   protected boolean mExtSysDimElementsAllItemsLoaded;
   private Map<ExtSysHierarchyPK, ExtSysHierarchyEVO> mExtSysHierarchies;
   protected boolean mExtSysHierarchiesAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ExtSysDimensionEVO() {}

   public ExtSysDimensionEVO(int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDimensionVisId, String newDescription, int newDimensionType, int newImportColumnIndex, Collection newExtSysDimElements, Collection newExtSysHierarchies) {
      this.mExternalSystemId = newExternalSystemId;
      this.mCompanyVisId = newCompanyVisId;
      this.mLedgerVisId = newLedgerVisId;
      this.mDimensionVisId = newDimensionVisId;
      this.mDescription = newDescription;
      this.mDimensionType = newDimensionType;
      this.mImportColumnIndex = newImportColumnIndex;
      this.setExtSysDimElements(newExtSysDimElements);
      this.setExtSysHierarchies(newExtSysHierarchies);
   }

   public void setExtSysDimElements(Collection<ExtSysDimElementEVO> items) {
      if(items != null) {
         if(this.mExtSysDimElements == null) {
            this.mExtSysDimElements = new HashMap();
         } else {
            this.mExtSysDimElements.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ExtSysDimElementEVO child = (ExtSysDimElementEVO)i$.next();
            this.mExtSysDimElements.put(child.getPK(), child);
         }
      } else {
         this.mExtSysDimElements = null;
      }

   }

   public void setExtSysHierarchies(Collection<ExtSysHierarchyEVO> items) {
      if(items != null) {
         if(this.mExtSysHierarchies == null) {
            this.mExtSysHierarchies = new HashMap();
         } else {
            this.mExtSysHierarchies.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ExtSysHierarchyEVO child = (ExtSysHierarchyEVO)i$.next();
            this.mExtSysHierarchies.put(child.getPK(), child);
         }
      } else {
         this.mExtSysHierarchies = null;
      }

   }

   public ExtSysDimensionPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExtSysDimensionPK(this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId, this.mDimensionVisId);
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

   public String getDescription() {
      return this.mDescription;
   }

   public int getDimensionType() {
      return this.mDimensionType;
   }

   public int getImportColumnIndex() {
      return this.mImportColumnIndex;
   }

   public void setExternalSystemId(int newExternalSystemId) {
      if(this.mExternalSystemId != newExternalSystemId) {
         this.mModified = true;
         this.mExternalSystemId = newExternalSystemId;
         this.mPK = null;
      }
   }

   public void setDimensionType(int newDimensionType) {
      if(this.mDimensionType != newDimensionType) {
         this.mModified = true;
         this.mDimensionType = newDimensionType;
      }
   }

   public void setImportColumnIndex(int newImportColumnIndex) {
      if(this.mImportColumnIndex != newImportColumnIndex) {
         this.mModified = true;
         this.mImportColumnIndex = newImportColumnIndex;
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

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setDetails(ExtSysDimensionEVO newDetails) {
      this.setExternalSystemId(newDetails.getExternalSystemId());
      this.setCompanyVisId(newDetails.getCompanyVisId());
      this.setLedgerVisId(newDetails.getLedgerVisId());
      this.setDimensionVisId(newDetails.getDimensionVisId());
      this.setDescription(newDetails.getDescription());
      this.setDimensionType(newDetails.getDimensionType());
      this.setImportColumnIndex(newDetails.getImportColumnIndex());
   }

   public ExtSysDimensionEVO deepClone() {
      ExtSysDimensionEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ExtSysDimensionEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ExtSysLedgerEVO parent) {
      boolean newKey = this.insertPending();
      Iterator iter;
      ExtSysDimElementEVO item;
      if(this.mExtSysDimElements != null) {
         for(iter = (new ArrayList(this.mExtSysDimElements.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ExtSysDimElementEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      ExtSysHierarchyEVO item1;
      if(this.mExtSysHierarchies != null) {
         for(iter = (new ArrayList(this.mExtSysHierarchies.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (ExtSysHierarchyEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      Iterator iter;
      ExtSysDimElementEVO item;
      if(this.mExtSysDimElements != null) {
         for(iter = this.mExtSysDimElements.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ExtSysDimElementEVO)iter.next();
         }
      }

      ExtSysHierarchyEVO item1;
      if(this.mExtSysHierarchies != null) {
         for(iter = this.mExtSysHierarchies.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (ExtSysHierarchyEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ExtSysLedgerEVO parent, int startKey) {
      int nextKey = startKey;
      Iterator iter;
      ExtSysDimElementEVO item;
      if(this.mExtSysDimElements != null) {
         for(iter = (new ArrayList(this.mExtSysDimElements.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ExtSysDimElementEVO)iter.next();
            this.changeKey(item, this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId, this.mDimensionVisId, item.getDimElementVisId());
         }
      }

      ExtSysHierarchyEVO item1;
      if(this.mExtSysHierarchies != null) {
         for(iter = (new ArrayList(this.mExtSysHierarchies.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (ExtSysHierarchyEVO)iter.next();
            this.changeKey(item1, this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId, this.mDimensionVisId, item1.getHierarchyVisId());
         }
      }

      return nextKey;
   }

   public Collection<ExtSysDimElementEVO> getExtSysDimElements() {
      return this.mExtSysDimElements != null?this.mExtSysDimElements.values():null;
   }

   public Map<ExtSysDimElementPK, ExtSysDimElementEVO> getExtSysDimElementsMap() {
      return this.mExtSysDimElements;
   }

   public void loadExtSysDimElementsItem(ExtSysDimElementEVO newItem) {
      if(this.mExtSysDimElements == null) {
         this.mExtSysDimElements = new HashMap();
      }

      this.mExtSysDimElements.put(newItem.getPK(), newItem);
   }

   public void addExtSysDimElementsItem(ExtSysDimElementEVO newItem) {
      if(this.mExtSysDimElements == null) {
         this.mExtSysDimElements = new HashMap();
      }

      ExtSysDimElementPK newPK = newItem.getPK();
      if(this.getExtSysDimElementsItem(newPK) != null) {
         throw new RuntimeException("addExtSysDimElementsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mExtSysDimElements.put(newPK, newItem);
      }
   }

   public void changeExtSysDimElementsItem(ExtSysDimElementEVO changedItem) {
      if(this.mExtSysDimElements == null) {
         throw new RuntimeException("changeExtSysDimElementsItem: no items in collection");
      } else {
         ExtSysDimElementPK changedPK = changedItem.getPK();
         ExtSysDimElementEVO listItem = this.getExtSysDimElementsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeExtSysDimElementsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteExtSysDimElementsItem(ExtSysDimElementPK removePK) {
      ExtSysDimElementEVO listItem = this.getExtSysDimElementsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeExtSysDimElementsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ExtSysDimElementEVO getExtSysDimElementsItem(ExtSysDimElementPK pk) {
      return (ExtSysDimElementEVO)this.mExtSysDimElements.get(pk);
   }

   public ExtSysDimElementEVO getExtSysDimElementsItem() {
      if(this.mExtSysDimElements.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mExtSysDimElements.size());
      } else {
         Iterator iter = this.mExtSysDimElements.values().iterator();
         return (ExtSysDimElementEVO)iter.next();
      }
   }

   public Collection<ExtSysHierarchyEVO> getExtSysHierarchies() {
      return this.mExtSysHierarchies != null?this.mExtSysHierarchies.values():null;
   }

   public Map<ExtSysHierarchyPK, ExtSysHierarchyEVO> getExtSysHierarchiesMap() {
      return this.mExtSysHierarchies;
   }

   public void loadExtSysHierarchiesItem(ExtSysHierarchyEVO newItem) {
      if(this.mExtSysHierarchies == null) {
         this.mExtSysHierarchies = new HashMap();
      }

      this.mExtSysHierarchies.put(newItem.getPK(), newItem);
   }

   public void addExtSysHierarchiesItem(ExtSysHierarchyEVO newItem) {
      if(this.mExtSysHierarchies == null) {
         this.mExtSysHierarchies = new HashMap();
      }

      ExtSysHierarchyPK newPK = newItem.getPK();
      if(this.getExtSysHierarchiesItem(newPK) != null) {
         throw new RuntimeException("addExtSysHierarchiesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mExtSysHierarchies.put(newPK, newItem);
      }
   }

   public void changeExtSysHierarchiesItem(ExtSysHierarchyEVO changedItem) {
      if(this.mExtSysHierarchies == null) {
         throw new RuntimeException("changeExtSysHierarchiesItem: no items in collection");
      } else {
         ExtSysHierarchyPK changedPK = changedItem.getPK();
         ExtSysHierarchyEVO listItem = this.getExtSysHierarchiesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeExtSysHierarchiesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteExtSysHierarchiesItem(ExtSysHierarchyPK removePK) {
      ExtSysHierarchyEVO listItem = this.getExtSysHierarchiesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeExtSysHierarchiesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ExtSysHierarchyEVO getExtSysHierarchiesItem(ExtSysHierarchyPK pk) {
      return (ExtSysHierarchyEVO)this.mExtSysHierarchies.get(pk);
   }

   public ExtSysHierarchyEVO getExtSysHierarchiesItem() {
      if(this.mExtSysHierarchies.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mExtSysHierarchies.size());
      } else {
         Iterator iter = this.mExtSysHierarchies.values().iterator();
         return (ExtSysHierarchyEVO)iter.next();
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

   public ExtSysDimensionRef getEntityRef(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysLedgerEVO evoExtSysLedger, String entityText) {
      return new ExtSysDimensionRefImpl(new ExtSysDimensionCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysLedger.getPK(), this.getPK()), entityText);
   }

   public ExtSysDimensionCK getCK(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysLedgerEVO evoExtSysLedger) {
      return new ExtSysDimensionCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysLedger.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mExtSysDimElementsAllItemsLoaded = true;
      if(this.mExtSysDimElements == null) {
         this.mExtSysDimElements = new HashMap();
      }

      this.mExtSysHierarchiesAllItemsLoaded = true;
      if(this.mExtSysHierarchies == null) {
         this.mExtSysHierarchies = new HashMap();
      } else {
         Iterator i$ = this.mExtSysHierarchies.values().iterator();

         while(i$.hasNext()) {
            ExtSysHierarchyEVO child = (ExtSysHierarchyEVO)i$.next();
            child.postCreateInit();
         }
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
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("DimensionType=");
      sb.append(String.valueOf(this.mDimensionType));
      sb.append(' ');
      sb.append("ImportColumnIndex=");
      sb.append(String.valueOf(this.mImportColumnIndex));
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

      sb.append("ExtSysDimension: ");
      sb.append(this.toString());
      if(this.mExtSysDimElementsAllItemsLoaded || this.mExtSysDimElements != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ExtSysDimElements: allItemsLoaded=");
         sb.append(String.valueOf(this.mExtSysDimElementsAllItemsLoaded));
         sb.append(" items=");
         if(this.mExtSysDimElements == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mExtSysDimElements.size()));
         }
      }

      if(this.mExtSysHierarchiesAllItemsLoaded || this.mExtSysHierarchies != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ExtSysHierarchies: allItemsLoaded=");
         sb.append(String.valueOf(this.mExtSysHierarchiesAllItemsLoaded));
         sb.append(" items=");
         if(this.mExtSysHierarchies == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mExtSysHierarchies.size()));
         }
      }

      Iterator var5;
      if(this.mExtSysDimElements != null) {
         var5 = this.mExtSysDimElements.values().iterator();

         while(var5.hasNext()) {
            ExtSysDimElementEVO listItem = (ExtSysDimElementEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mExtSysHierarchies != null) {
         var5 = this.mExtSysHierarchies.values().iterator();

         while(var5.hasNext()) {
            ExtSysHierarchyEVO var6 = (ExtSysHierarchyEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ExtSysDimElementEVO child, int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDimensionVisId, String newDimElementVisId) {
      if(this.getExtSysDimElementsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mExtSysDimElements.remove(child.getPK());
         child.setExternalSystemId(newExternalSystemId);
         child.setCompanyVisId(newCompanyVisId);
         child.setLedgerVisId(newLedgerVisId);
         child.setDimensionVisId(newDimensionVisId);
         child.setDimElementVisId(newDimElementVisId);
         this.mExtSysDimElements.put(child.getPK(), child);
      }
   }

   public void changeKey(ExtSysHierarchyEVO child, int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDimensionVisId, String newHierarchyVisId) {
      if(this.getExtSysHierarchiesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mExtSysHierarchies.remove(child.getPK());
         child.setExternalSystemId(newExternalSystemId);
         child.setCompanyVisId(newCompanyVisId);
         child.setLedgerVisId(newLedgerVisId);
         child.setDimensionVisId(newDimensionVisId);
         child.setHierarchyVisId(newHierarchyVisId);
         this.mExtSysHierarchies.put(child.getPK(), child);
      }
   }

   public void setExtSysDimElementsAllItemsLoaded(boolean allItemsLoaded) {
      this.mExtSysDimElementsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isExtSysDimElementsAllItemsLoaded() {
      return this.mExtSysDimElementsAllItemsLoaded;
   }

   public void setExtSysHierarchiesAllItemsLoaded(boolean allItemsLoaded) {
      this.mExtSysHierarchiesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isExtSysHierarchiesAllItemsLoaded() {
      return this.mExtSysHierarchiesAllItemsLoaded;
   }
}
