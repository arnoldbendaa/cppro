// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.extsys.ExtSysHierarchyRef;
import com.cedar.cp.dto.extsys.ExtSysHierElementPK;
import com.cedar.cp.dto.extsys.ExtSysHierarchyCK;
import com.cedar.cp.dto.extsys.ExtSysHierarchyPK;
import com.cedar.cp.dto.extsys.ExtSysHierarchyRefImpl;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysDimensionEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierElementEVO;
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

public class ExtSysHierarchyEVO implements Serializable {

   private transient ExtSysHierarchyPK mPK;
   private int mExternalSystemId;
   private String mCompanyVisId;
   private String mLedgerVisId;
   private String mDimensionVisId;
   private String mHierarchyVisId;
   private String mDescription;
   private Map<ExtSysHierElementPK, ExtSysHierElementEVO> mExtSysHierElements;
   protected boolean mExtSysHierElementsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ExtSysHierarchyEVO() {}

   public ExtSysHierarchyEVO(int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDimensionVisId, String newHierarchyVisId, String newDescription, Collection newExtSysHierElements) {
      this.mExternalSystemId = newExternalSystemId;
      this.mCompanyVisId = newCompanyVisId;
      this.mLedgerVisId = newLedgerVisId;
      this.mDimensionVisId = newDimensionVisId;
      this.mHierarchyVisId = newHierarchyVisId;
      this.mDescription = newDescription;
      this.setExtSysHierElements(newExtSysHierElements);
   }

   public void setExtSysHierElements(Collection<ExtSysHierElementEVO> items) {
      if(items != null) {
         if(this.mExtSysHierElements == null) {
            this.mExtSysHierElements = new HashMap();
         } else {
            this.mExtSysHierElements.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ExtSysHierElementEVO child = (ExtSysHierElementEVO)i$.next();
            this.mExtSysHierElements.put(child.getPK(), child);
         }
      } else {
         this.mExtSysHierElements = null;
      }

   }

   public ExtSysHierarchyPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExtSysHierarchyPK(this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId, this.mDimensionVisId, this.mHierarchyVisId);
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

   public String getDescription() {
      return this.mDescription;
   }

   public void setExternalSystemId(int newExternalSystemId) {
      if(this.mExternalSystemId != newExternalSystemId) {
         this.mModified = true;
         this.mExternalSystemId = newExternalSystemId;
         this.mPK = null;
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

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setDetails(ExtSysHierarchyEVO newDetails) {
      this.setExternalSystemId(newDetails.getExternalSystemId());
      this.setCompanyVisId(newDetails.getCompanyVisId());
      this.setLedgerVisId(newDetails.getLedgerVisId());
      this.setDimensionVisId(newDetails.getDimensionVisId());
      this.setHierarchyVisId(newDetails.getHierarchyVisId());
      this.setDescription(newDetails.getDescription());
   }

   public ExtSysHierarchyEVO deepClone() {
      ExtSysHierarchyEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ExtSysHierarchyEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ExtSysDimensionEVO parent) {
      boolean newKey = this.insertPending();
      ExtSysHierElementEVO item;
      if(this.mExtSysHierElements != null) {
         for(Iterator iter = (new ArrayList(this.mExtSysHierElements.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ExtSysHierElementEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      ExtSysHierElementEVO item;
      if(this.mExtSysHierElements != null) {
         for(Iterator iter = this.mExtSysHierElements.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ExtSysHierElementEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ExtSysDimensionEVO parent, int startKey) {
      int nextKey = startKey;
      ExtSysHierElementEVO item;
      if(this.mExtSysHierElements != null) {
         for(Iterator iter = (new ArrayList(this.mExtSysHierElements.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ExtSysHierElementEVO)iter.next();
            this.changeKey(item, this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId, this.mDimensionVisId, this.mHierarchyVisId, item.getHierElementVisId());
         }
      }

      return nextKey;
   }

   public Collection<ExtSysHierElementEVO> getExtSysHierElements() {
      return this.mExtSysHierElements != null?this.mExtSysHierElements.values():null;
   }

   public Map<ExtSysHierElementPK, ExtSysHierElementEVO> getExtSysHierElementsMap() {
      return this.mExtSysHierElements;
   }

   public void loadExtSysHierElementsItem(ExtSysHierElementEVO newItem) {
      if(this.mExtSysHierElements == null) {
         this.mExtSysHierElements = new HashMap();
      }

      this.mExtSysHierElements.put(newItem.getPK(), newItem);
   }

   public void addExtSysHierElementsItem(ExtSysHierElementEVO newItem) {
      if(this.mExtSysHierElements == null) {
         this.mExtSysHierElements = new HashMap();
      }

      ExtSysHierElementPK newPK = newItem.getPK();
      if(this.getExtSysHierElementsItem(newPK) != null) {
         throw new RuntimeException("addExtSysHierElementsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mExtSysHierElements.put(newPK, newItem);
      }
   }

   public void changeExtSysHierElementsItem(ExtSysHierElementEVO changedItem) {
      if(this.mExtSysHierElements == null) {
         throw new RuntimeException("changeExtSysHierElementsItem: no items in collection");
      } else {
         ExtSysHierElementPK changedPK = changedItem.getPK();
         ExtSysHierElementEVO listItem = this.getExtSysHierElementsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeExtSysHierElementsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteExtSysHierElementsItem(ExtSysHierElementPK removePK) {
      ExtSysHierElementEVO listItem = this.getExtSysHierElementsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeExtSysHierElementsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ExtSysHierElementEVO getExtSysHierElementsItem(ExtSysHierElementPK pk) {
      return (ExtSysHierElementEVO)this.mExtSysHierElements.get(pk);
   }

   public ExtSysHierElementEVO getExtSysHierElementsItem() {
      if(this.mExtSysHierElements.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mExtSysHierElements.size());
      } else {
         Iterator iter = this.mExtSysHierElements.values().iterator();
         return (ExtSysHierElementEVO)iter.next();
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

   public ExtSysHierarchyRef getEntityRef(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysLedgerEVO evoExtSysLedger, ExtSysDimensionEVO evoExtSysDimension, String entityText) {
      return new ExtSysHierarchyRefImpl(new ExtSysHierarchyCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysLedger.getPK(), evoExtSysDimension.getPK(), this.getPK()), entityText);
   }

   public ExtSysHierarchyCK getCK(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, ExtSysLedgerEVO evoExtSysLedger, ExtSysDimensionEVO evoExtSysDimension) {
      return new ExtSysHierarchyCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), evoExtSysLedger.getPK(), evoExtSysDimension.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mExtSysHierElementsAllItemsLoaded = true;
      if(this.mExtSysHierElements == null) {
         this.mExtSysHierElements = new HashMap();
      } else {
         Iterator i$ = this.mExtSysHierElements.values().iterator();

         while(i$.hasNext()) {
            ExtSysHierElementEVO child = (ExtSysHierElementEVO)i$.next();
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
      sb.append("HierarchyVisId=");
      sb.append(String.valueOf(this.mHierarchyVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
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

      sb.append("ExtSysHierarchy: ");
      sb.append(this.toString());
      if(this.mExtSysHierElementsAllItemsLoaded || this.mExtSysHierElements != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ExtSysHierElements: allItemsLoaded=");
         sb.append(String.valueOf(this.mExtSysHierElementsAllItemsLoaded));
         sb.append(" items=");
         if(this.mExtSysHierElements == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mExtSysHierElements.size()));
         }
      }

      if(this.mExtSysHierElements != null) {
         Iterator var5 = this.mExtSysHierElements.values().iterator();

         while(var5.hasNext()) {
            ExtSysHierElementEVO listItem = (ExtSysHierElementEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ExtSysHierElementEVO child, int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDimensionVisId, String newHierarchyVisId, String newHierElementVisId) {
      if(this.getExtSysHierElementsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mExtSysHierElements.remove(child.getPK());
         child.setExternalSystemId(newExternalSystemId);
         child.setCompanyVisId(newCompanyVisId);
         child.setLedgerVisId(newLedgerVisId);
         child.setDimensionVisId(newDimensionVisId);
         child.setHierarchyVisId(newHierarchyVisId);
         child.setHierElementVisId(newHierElementVisId);
         this.mExtSysHierElements.put(child.getPK(), child);
      }
   }

   public void setExtSysHierElementsAllItemsLoaded(boolean allItemsLoaded) {
      this.mExtSysHierElementsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isExtSysHierElementsAllItemsLoaded() {
      return this.mExtSysHierElementsAllItemsLoaded;
   }
}
