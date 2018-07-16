// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.extsys.ExtSysCompanyRef;
import com.cedar.cp.dto.extsys.ExtSysCalendarYearPK;
import com.cedar.cp.dto.extsys.ExtSysCompanyCK;
import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
import com.cedar.cp.dto.extsys.ExtSysCompanyRefImpl;
import com.cedar.cp.dto.extsys.ExtSysLedgerPK;
import com.cedar.cp.ejb.impl.extsys.ExtSysCalendarYearEVO;
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

public class ExtSysCompanyEVO implements Serializable {

   private transient ExtSysCompanyPK mPK;
   private int mExternalSystemId;
   private String mCompanyVisId;
   private String mDescription;
   private boolean mDummy;
   private int mImportColumnCalendarIndex;
   private Map<ExtSysLedgerPK, ExtSysLedgerEVO> mExtSysLedger;
   protected boolean mExtSysLedgerAllItemsLoaded;
   private Map<ExtSysCalendarYearPK, ExtSysCalendarYearEVO> mExtSysCalendarYears;
   protected boolean mExtSysCalendarYearsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ExtSysCompanyEVO() {}

   public ExtSysCompanyEVO(int newExternalSystemId, String newCompanyVisId, String newDescription, boolean newDummy, int newImportColumnCalendarIndex, Collection newExtSysLedger, Collection newExtSysCalendarYears) {
      this.mExternalSystemId = newExternalSystemId;
      this.mCompanyVisId = newCompanyVisId;
      this.mDescription = newDescription;
      this.mDummy = newDummy;
      this.mImportColumnCalendarIndex = newImportColumnCalendarIndex;
      this.setExtSysLedger(newExtSysLedger);
      this.setExtSysCalendarYears(newExtSysCalendarYears);
   }

   public void setExtSysLedger(Collection<ExtSysLedgerEVO> items) {
      if(items != null) {
         if(this.mExtSysLedger == null) {
            this.mExtSysLedger = new HashMap();
         } else {
            this.mExtSysLedger.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ExtSysLedgerEVO child = (ExtSysLedgerEVO)i$.next();
            this.mExtSysLedger.put(child.getPK(), child);
         }
      } else {
         this.mExtSysLedger = null;
      }

   }

   public void setExtSysCalendarYears(Collection<ExtSysCalendarYearEVO> items) {
      if(items != null) {
         if(this.mExtSysCalendarYears == null) {
            this.mExtSysCalendarYears = new HashMap();
         } else {
            this.mExtSysCalendarYears.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ExtSysCalendarYearEVO child = (ExtSysCalendarYearEVO)i$.next();
            this.mExtSysCalendarYears.put(child.getPK(), child);
         }
      } else {
         this.mExtSysCalendarYears = null;
      }

   }

   public ExtSysCompanyPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExtSysCompanyPK(this.mExternalSystemId, this.mCompanyVisId);
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

   public String getDescription() {
      return this.mDescription;
   }

   public boolean getDummy() {
      return this.mDummy;
   }

   public int getImportColumnCalendarIndex() {
      return this.mImportColumnCalendarIndex;
   }

   public void setExternalSystemId(int newExternalSystemId) {
      if(this.mExternalSystemId != newExternalSystemId) {
         this.mModified = true;
         this.mExternalSystemId = newExternalSystemId;
         this.mPK = null;
      }
   }

   public void setDummy(boolean newDummy) {
      if(this.mDummy != newDummy) {
         this.mModified = true;
         this.mDummy = newDummy;
      }
   }

   public void setImportColumnCalendarIndex(int newImportColumnCalendarIndex) {
      if(this.mImportColumnCalendarIndex != newImportColumnCalendarIndex) {
         this.mModified = true;
         this.mImportColumnCalendarIndex = newImportColumnCalendarIndex;
      }
   }

   public void setCompanyVisId(String newCompanyVisId) {
      if(this.mCompanyVisId != null && newCompanyVisId == null || this.mCompanyVisId == null && newCompanyVisId != null || this.mCompanyVisId != null && newCompanyVisId != null && !this.mCompanyVisId.equals(newCompanyVisId)) {
         this.mCompanyVisId = newCompanyVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setDetails(ExtSysCompanyEVO newDetails) {
      this.setExternalSystemId(newDetails.getExternalSystemId());
      this.setCompanyVisId(newDetails.getCompanyVisId());
      this.setDescription(newDetails.getDescription());
      this.setDummy(newDetails.getDummy());
      this.setImportColumnCalendarIndex(newDetails.getImportColumnCalendarIndex());
   }

   public ExtSysCompanyEVO deepClone() {
      ExtSysCompanyEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ExtSysCompanyEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ExternalSystemEVO parent) {
      boolean newKey = this.insertPending();
      Iterator iter;
      ExtSysLedgerEVO item;
      if(this.mExtSysLedger != null) {
         for(iter = (new ArrayList(this.mExtSysLedger.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ExtSysLedgerEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      ExtSysCalendarYearEVO item1;
      if(this.mExtSysCalendarYears != null) {
         for(iter = (new ArrayList(this.mExtSysCalendarYears.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (ExtSysCalendarYearEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      Iterator iter;
      ExtSysLedgerEVO item;
      if(this.mExtSysLedger != null) {
         for(iter = this.mExtSysLedger.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ExtSysLedgerEVO)iter.next();
         }
      }

      ExtSysCalendarYearEVO item1;
      if(this.mExtSysCalendarYears != null) {
         for(iter = this.mExtSysCalendarYears.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (ExtSysCalendarYearEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ExternalSystemEVO parent, int startKey) {
      int nextKey = startKey;
      Iterator iter;
      ExtSysLedgerEVO item;
      if(this.mExtSysLedger != null) {
         for(iter = (new ArrayList(this.mExtSysLedger.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ExtSysLedgerEVO)iter.next();
            this.changeKey(item, this.mExternalSystemId, this.mCompanyVisId, item.getLedgerVisId());
         }
      }

      ExtSysCalendarYearEVO item1;
      if(this.mExtSysCalendarYears != null) {
         for(iter = (new ArrayList(this.mExtSysCalendarYears.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (ExtSysCalendarYearEVO)iter.next();
            this.changeKey(item1, this.mExternalSystemId, this.mCompanyVisId, item1.getCalendarYearVisId());
         }
      }

      return nextKey;
   }

   public Collection<ExtSysLedgerEVO> getExtSysLedger() {
      return this.mExtSysLedger != null?this.mExtSysLedger.values():null;
   }

   public Map<ExtSysLedgerPK, ExtSysLedgerEVO> getExtSysLedgerMap() {
      return this.mExtSysLedger;
   }

   public void loadExtSysLedgerItem(ExtSysLedgerEVO newItem) {
      if(this.mExtSysLedger == null) {
         this.mExtSysLedger = new HashMap();
      }

      this.mExtSysLedger.put(newItem.getPK(), newItem);
   }

   public void addExtSysLedgerItem(ExtSysLedgerEVO newItem) {
      if(this.mExtSysLedger == null) {
         this.mExtSysLedger = new HashMap();
      }

      ExtSysLedgerPK newPK = newItem.getPK();
      if(this.getExtSysLedgerItem(newPK) != null) {
         throw new RuntimeException("addExtSysLedgerItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mExtSysLedger.put(newPK, newItem);
      }
   }

   public void changeExtSysLedgerItem(ExtSysLedgerEVO changedItem) {
      if(this.mExtSysLedger == null) {
         throw new RuntimeException("changeExtSysLedgerItem: no items in collection");
      } else {
         ExtSysLedgerPK changedPK = changedItem.getPK();
         ExtSysLedgerEVO listItem = this.getExtSysLedgerItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeExtSysLedgerItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteExtSysLedgerItem(ExtSysLedgerPK removePK) {
      ExtSysLedgerEVO listItem = this.getExtSysLedgerItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeExtSysLedgerItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ExtSysLedgerEVO getExtSysLedgerItem(ExtSysLedgerPK pk) {
      return (ExtSysLedgerEVO)this.mExtSysLedger.get(pk);
   }

   public ExtSysLedgerEVO getExtSysLedgerItem() {
      if(this.mExtSysLedger.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mExtSysLedger.size());
      } else {
         Iterator iter = this.mExtSysLedger.values().iterator();
         return (ExtSysLedgerEVO)iter.next();
      }
   }

   public Collection<ExtSysCalendarYearEVO> getExtSysCalendarYears() {
      return this.mExtSysCalendarYears != null?this.mExtSysCalendarYears.values():null;
   }

   public Map<ExtSysCalendarYearPK, ExtSysCalendarYearEVO> getExtSysCalendarYearsMap() {
      return this.mExtSysCalendarYears;
   }

   public void loadExtSysCalendarYearsItem(ExtSysCalendarYearEVO newItem) {
      if(this.mExtSysCalendarYears == null) {
         this.mExtSysCalendarYears = new HashMap();
      }

      this.mExtSysCalendarYears.put(newItem.getPK(), newItem);
   }

   public void addExtSysCalendarYearsItem(ExtSysCalendarYearEVO newItem) {
      if(this.mExtSysCalendarYears == null) {
         this.mExtSysCalendarYears = new HashMap();
      }

      ExtSysCalendarYearPK newPK = newItem.getPK();
      if(this.getExtSysCalendarYearsItem(newPK) != null) {
         throw new RuntimeException("addExtSysCalendarYearsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mExtSysCalendarYears.put(newPK, newItem);
      }
   }

   public void changeExtSysCalendarYearsItem(ExtSysCalendarYearEVO changedItem) {
      if(this.mExtSysCalendarYears == null) {
         throw new RuntimeException("changeExtSysCalendarYearsItem: no items in collection");
      } else {
         ExtSysCalendarYearPK changedPK = changedItem.getPK();
         ExtSysCalendarYearEVO listItem = this.getExtSysCalendarYearsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeExtSysCalendarYearsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteExtSysCalendarYearsItem(ExtSysCalendarYearPK removePK) {
      ExtSysCalendarYearEVO listItem = this.getExtSysCalendarYearsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeExtSysCalendarYearsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ExtSysCalendarYearEVO getExtSysCalendarYearsItem(ExtSysCalendarYearPK pk) {
      return (ExtSysCalendarYearEVO)this.mExtSysCalendarYears.get(pk);
   }

   public ExtSysCalendarYearEVO getExtSysCalendarYearsItem() {
      if(this.mExtSysCalendarYears.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mExtSysCalendarYears.size());
      } else {
         Iterator iter = this.mExtSysCalendarYears.values().iterator();
         return (ExtSysCalendarYearEVO)iter.next();
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

   public ExtSysCompanyRef getEntityRef(ExternalSystemEVO evoExternalSystem, String entityText) {
      return new ExtSysCompanyRefImpl(new ExtSysCompanyCK(evoExternalSystem.getPK(), this.getPK()), entityText);
   }

   public ExtSysCompanyCK getCK(ExternalSystemEVO evoExternalSystem) {
      return new ExtSysCompanyCK(evoExternalSystem.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mExtSysLedgerAllItemsLoaded = true;
      Iterator i$;
      if(this.mExtSysLedger == null) {
         this.mExtSysLedger = new HashMap();
      } else {
         i$ = this.mExtSysLedger.values().iterator();

         while(i$.hasNext()) {
            ExtSysLedgerEVO child = (ExtSysLedgerEVO)i$.next();
            child.postCreateInit();
         }
      }

      this.mExtSysCalendarYearsAllItemsLoaded = true;
      if(this.mExtSysCalendarYears == null) {
         this.mExtSysCalendarYears = new HashMap();
      } else {
         i$ = this.mExtSysCalendarYears.values().iterator();

         while(i$.hasNext()) {
            ExtSysCalendarYearEVO child1 = (ExtSysCalendarYearEVO)i$.next();
            child1.postCreateInit();
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
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("Dummy=");
      sb.append(String.valueOf(this.mDummy));
      sb.append(' ');
      sb.append("ImportColumnCalendarIndex=");
      sb.append(String.valueOf(this.mImportColumnCalendarIndex));
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

      sb.append("ExtSysCompany: ");
      sb.append(this.toString());
      if(this.mExtSysLedgerAllItemsLoaded || this.mExtSysLedger != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ExtSysLedger: allItemsLoaded=");
         sb.append(String.valueOf(this.mExtSysLedgerAllItemsLoaded));
         sb.append(" items=");
         if(this.mExtSysLedger == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mExtSysLedger.size()));
         }
      }

      if(this.mExtSysCalendarYearsAllItemsLoaded || this.mExtSysCalendarYears != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ExtSysCalendarYears: allItemsLoaded=");
         sb.append(String.valueOf(this.mExtSysCalendarYearsAllItemsLoaded));
         sb.append(" items=");
         if(this.mExtSysCalendarYears == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mExtSysCalendarYears.size()));
         }
      }

      Iterator var5;
      if(this.mExtSysLedger != null) {
         var5 = this.mExtSysLedger.values().iterator();

         while(var5.hasNext()) {
            ExtSysLedgerEVO listItem = (ExtSysLedgerEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mExtSysCalendarYears != null) {
         var5 = this.mExtSysCalendarYears.values().iterator();

         while(var5.hasNext()) {
            ExtSysCalendarYearEVO var6 = (ExtSysCalendarYearEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ExtSysLedgerEVO child, int newExternalSystemId, String newCompanyVisId, String newLedgerVisId) {
      if(this.getExtSysLedgerItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mExtSysLedger.remove(child.getPK());
         child.setExternalSystemId(newExternalSystemId);
         child.setCompanyVisId(newCompanyVisId);
         child.setLedgerVisId(newLedgerVisId);
         this.mExtSysLedger.put(child.getPK(), child);
      }
   }

   public void changeKey(ExtSysCalendarYearEVO child, int newExternalSystemId, String newCompanyVisId, String newCalendarYearVisId) {
      if(this.getExtSysCalendarYearsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mExtSysCalendarYears.remove(child.getPK());
         child.setExternalSystemId(newExternalSystemId);
         child.setCompanyVisId(newCompanyVisId);
         child.setCalendarYearVisId(newCalendarYearVisId);
         this.mExtSysCalendarYears.put(child.getPK(), child);
      }
   }

   public void setExtSysLedgerAllItemsLoaded(boolean allItemsLoaded) {
      this.mExtSysLedgerAllItemsLoaded = allItemsLoaded;
   }

   public boolean isExtSysLedgerAllItemsLoaded() {
      return this.mExtSysLedgerAllItemsLoaded;
   }

   public void setExtSysCalendarYearsAllItemsLoaded(boolean allItemsLoaded) {
      this.mExtSysCalendarYearsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isExtSysCalendarYearsAllItemsLoaded() {
      return this.mExtSysCalendarYearsAllItemsLoaded;
   }
}
