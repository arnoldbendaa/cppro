// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:35
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.extsys.ExtSysLedgerRef;
import com.cedar.cp.dto.extsys.ExtSysCurrencyPK;
import com.cedar.cp.dto.extsys.ExtSysDimensionPK;
import com.cedar.cp.dto.extsys.ExtSysLedgerCK;
import com.cedar.cp.dto.extsys.ExtSysLedgerPK;
import com.cedar.cp.dto.extsys.ExtSysLedgerRefImpl;
import com.cedar.cp.dto.extsys.ExtSysValueTypePK;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysCurrencyEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysDimensionEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysValueTypeEVO;
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

public class ExtSysLedgerEVO implements Serializable {

   private transient ExtSysLedgerPK mPK;
   private int mExternalSystemId;
   private String mCompanyVisId;
   private String mLedgerVisId;
   private String mDescription;
   private boolean mDummy;
   private Map<ExtSysDimensionPK, ExtSysDimensionEVO> mExtSysDimensions;
   protected boolean mExtSysDimensionsAllItemsLoaded;
   private Map<ExtSysValueTypePK, ExtSysValueTypeEVO> mExtSysValueTypes;
   protected boolean mExtSysValueTypesAllItemsLoaded;
   private Map<ExtSysCurrencyPK, ExtSysCurrencyEVO> mExtSysCurrencies;
   protected boolean mExtSysCurrenciesAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ExtSysLedgerEVO() {}

   public ExtSysLedgerEVO(int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDescription, boolean newDummy, Collection newExtSysDimensions, Collection newExtSysValueTypes, Collection newExtSysCurrencies) {
      this.mExternalSystemId = newExternalSystemId;
      this.mCompanyVisId = newCompanyVisId;
      this.mLedgerVisId = newLedgerVisId;
      this.mDescription = newDescription;
      this.mDummy = newDummy;
      this.setExtSysDimensions(newExtSysDimensions);
      this.setExtSysValueTypes(newExtSysValueTypes);
      this.setExtSysCurrencies(newExtSysCurrencies);
   }

   public void setExtSysDimensions(Collection<ExtSysDimensionEVO> items) {
      if(items != null) {
         if(this.mExtSysDimensions == null) {
            this.mExtSysDimensions = new HashMap();
         } else {
            this.mExtSysDimensions.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ExtSysDimensionEVO child = (ExtSysDimensionEVO)i$.next();
            this.mExtSysDimensions.put(child.getPK(), child);
         }
      } else {
         this.mExtSysDimensions = null;
      }

   }

   public void setExtSysValueTypes(Collection<ExtSysValueTypeEVO> items) {
      if(items != null) {
         if(this.mExtSysValueTypes == null) {
            this.mExtSysValueTypes = new HashMap();
         } else {
            this.mExtSysValueTypes.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ExtSysValueTypeEVO child = (ExtSysValueTypeEVO)i$.next();
            this.mExtSysValueTypes.put(child.getPK(), child);
         }
      } else {
         this.mExtSysValueTypes = null;
      }

   }

   public void setExtSysCurrencies(Collection<ExtSysCurrencyEVO> items) {
      if(items != null) {
         if(this.mExtSysCurrencies == null) {
            this.mExtSysCurrencies = new HashMap();
         } else {
            this.mExtSysCurrencies.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ExtSysCurrencyEVO child = (ExtSysCurrencyEVO)i$.next();
            this.mExtSysCurrencies.put(child.getPK(), child);
         }
      } else {
         this.mExtSysCurrencies = null;
      }

   }

   public ExtSysLedgerPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExtSysLedgerPK(this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId);
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

   public String getDescription() {
      return this.mDescription;
   }

   public boolean getDummy() {
      return this.mDummy;
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

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setDetails(ExtSysLedgerEVO newDetails) {
      this.setExternalSystemId(newDetails.getExternalSystemId());
      this.setCompanyVisId(newDetails.getCompanyVisId());
      this.setLedgerVisId(newDetails.getLedgerVisId());
      this.setDescription(newDetails.getDescription());
      this.setDummy(newDetails.getDummy());
   }

   public ExtSysLedgerEVO deepClone() {
      ExtSysLedgerEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ExtSysLedgerEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ExtSysCompanyEVO parent) {
      boolean newKey = this.insertPending();
      Iterator iter;
      ExtSysDimensionEVO item;
      if(this.mExtSysDimensions != null) {
         for(iter = (new ArrayList(this.mExtSysDimensions.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ExtSysDimensionEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      ExtSysValueTypeEVO item1;
      if(this.mExtSysValueTypes != null) {
         for(iter = (new ArrayList(this.mExtSysValueTypes.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (ExtSysValueTypeEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

      ExtSysCurrencyEVO item2;
      if(this.mExtSysCurrencies != null) {
         for(iter = (new ArrayList(this.mExtSysCurrencies.values())).iterator(); iter.hasNext(); item2.prepareForInsert(this)) {
            item2 = (ExtSysCurrencyEVO)iter.next();
            if(newKey) {
               item2.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      Iterator iter;
      ExtSysDimensionEVO item;
      if(this.mExtSysDimensions != null) {
         for(iter = this.mExtSysDimensions.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ExtSysDimensionEVO)iter.next();
         }
      }

      ExtSysValueTypeEVO item1;
      if(this.mExtSysValueTypes != null) {
         for(iter = this.mExtSysValueTypes.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (ExtSysValueTypeEVO)iter.next();
         }
      }

      ExtSysCurrencyEVO item2;
      if(this.mExtSysCurrencies != null) {
         for(iter = this.mExtSysCurrencies.values().iterator(); iter.hasNext(); returnCount = item2.getInsertCount(returnCount)) {
            item2 = (ExtSysCurrencyEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ExtSysCompanyEVO parent, int startKey) {
      int nextKey = startKey;
      Iterator iter;
      ExtSysDimensionEVO item;
      if(this.mExtSysDimensions != null) {
         for(iter = (new ArrayList(this.mExtSysDimensions.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ExtSysDimensionEVO)iter.next();
            this.changeKey(item, this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId, item.getDimensionVisId());
         }
      }

      ExtSysValueTypeEVO item1;
      if(this.mExtSysValueTypes != null) {
         for(iter = (new ArrayList(this.mExtSysValueTypes.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (ExtSysValueTypeEVO)iter.next();
            this.changeKey(item1, this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId, item1.getValueTypeVisId());
         }
      }

      ExtSysCurrencyEVO item2;
      if(this.mExtSysCurrencies != null) {
         for(iter = (new ArrayList(this.mExtSysCurrencies.values())).iterator(); iter.hasNext(); nextKey = item2.assignNextKey(this, nextKey)) {
            item2 = (ExtSysCurrencyEVO)iter.next();
            this.changeKey(item2, this.mExternalSystemId, this.mCompanyVisId, this.mLedgerVisId, item2.getCurrencyVisId());
         }
      }

      return nextKey;
   }

   public Collection<ExtSysDimensionEVO> getExtSysDimensions() {
      return this.mExtSysDimensions != null?this.mExtSysDimensions.values():null;
   }

   public Map<ExtSysDimensionPK, ExtSysDimensionEVO> getExtSysDimensionsMap() {
      return this.mExtSysDimensions;
   }

   public void loadExtSysDimensionsItem(ExtSysDimensionEVO newItem) {
      if(this.mExtSysDimensions == null) {
         this.mExtSysDimensions = new HashMap();
      }

      this.mExtSysDimensions.put(newItem.getPK(), newItem);
   }

   public void addExtSysDimensionsItem(ExtSysDimensionEVO newItem) {
      if(this.mExtSysDimensions == null) {
         this.mExtSysDimensions = new HashMap();
      }

      ExtSysDimensionPK newPK = newItem.getPK();
      if(this.getExtSysDimensionsItem(newPK) != null) {
         throw new RuntimeException("addExtSysDimensionsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mExtSysDimensions.put(newPK, newItem);
      }
   }

   public void changeExtSysDimensionsItem(ExtSysDimensionEVO changedItem) {
      if(this.mExtSysDimensions == null) {
         throw new RuntimeException("changeExtSysDimensionsItem: no items in collection");
      } else {
         ExtSysDimensionPK changedPK = changedItem.getPK();
         ExtSysDimensionEVO listItem = this.getExtSysDimensionsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeExtSysDimensionsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteExtSysDimensionsItem(ExtSysDimensionPK removePK) {
      ExtSysDimensionEVO listItem = this.getExtSysDimensionsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeExtSysDimensionsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ExtSysDimensionEVO getExtSysDimensionsItem(ExtSysDimensionPK pk) {
      return (ExtSysDimensionEVO)this.mExtSysDimensions.get(pk);
   }

   public ExtSysDimensionEVO getExtSysDimensionsItem() {
      if(this.mExtSysDimensions.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mExtSysDimensions.size());
      } else {
         Iterator iter = this.mExtSysDimensions.values().iterator();
         return (ExtSysDimensionEVO)iter.next();
      }
   }

   public Collection<ExtSysValueTypeEVO> getExtSysValueTypes() {
      return this.mExtSysValueTypes != null?this.mExtSysValueTypes.values():null;
   }

   public Map<ExtSysValueTypePK, ExtSysValueTypeEVO> getExtSysValueTypesMap() {
      return this.mExtSysValueTypes;
   }

   public void loadExtSysValueTypesItem(ExtSysValueTypeEVO newItem) {
      if(this.mExtSysValueTypes == null) {
         this.mExtSysValueTypes = new HashMap();
      }

      this.mExtSysValueTypes.put(newItem.getPK(), newItem);
   }

   public void addExtSysValueTypesItem(ExtSysValueTypeEVO newItem) {
      if(this.mExtSysValueTypes == null) {
         this.mExtSysValueTypes = new HashMap();
      }

      ExtSysValueTypePK newPK = newItem.getPK();
      if(this.getExtSysValueTypesItem(newPK) != null) {
         throw new RuntimeException("addExtSysValueTypesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mExtSysValueTypes.put(newPK, newItem);
      }
   }

   public void changeExtSysValueTypesItem(ExtSysValueTypeEVO changedItem) {
      if(this.mExtSysValueTypes == null) {
         throw new RuntimeException("changeExtSysValueTypesItem: no items in collection");
      } else {
         ExtSysValueTypePK changedPK = changedItem.getPK();
         ExtSysValueTypeEVO listItem = this.getExtSysValueTypesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeExtSysValueTypesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteExtSysValueTypesItem(ExtSysValueTypePK removePK) {
      ExtSysValueTypeEVO listItem = this.getExtSysValueTypesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeExtSysValueTypesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ExtSysValueTypeEVO getExtSysValueTypesItem(ExtSysValueTypePK pk) {
      return (ExtSysValueTypeEVO)this.mExtSysValueTypes.get(pk);
   }

   public ExtSysValueTypeEVO getExtSysValueTypesItem() {
      if(this.mExtSysValueTypes.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mExtSysValueTypes.size());
      } else {
         Iterator iter = this.mExtSysValueTypes.values().iterator();
         return (ExtSysValueTypeEVO)iter.next();
      }
   }

   public Collection<ExtSysCurrencyEVO> getExtSysCurrencies() {
      return this.mExtSysCurrencies != null?this.mExtSysCurrencies.values():null;
   }

   public Map<ExtSysCurrencyPK, ExtSysCurrencyEVO> getExtSysCurrenciesMap() {
      return this.mExtSysCurrencies;
   }

   public void loadExtSysCurrenciesItem(ExtSysCurrencyEVO newItem) {
      if(this.mExtSysCurrencies == null) {
         this.mExtSysCurrencies = new HashMap();
      }

      this.mExtSysCurrencies.put(newItem.getPK(), newItem);
   }

   public void addExtSysCurrenciesItem(ExtSysCurrencyEVO newItem) {
      if(this.mExtSysCurrencies == null) {
         this.mExtSysCurrencies = new HashMap();
      }

      ExtSysCurrencyPK newPK = newItem.getPK();
      if(this.getExtSysCurrenciesItem(newPK) != null) {
         throw new RuntimeException("addExtSysCurrenciesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mExtSysCurrencies.put(newPK, newItem);
      }
   }

   public void changeExtSysCurrenciesItem(ExtSysCurrencyEVO changedItem) {
      if(this.mExtSysCurrencies == null) {
         throw new RuntimeException("changeExtSysCurrenciesItem: no items in collection");
      } else {
         ExtSysCurrencyPK changedPK = changedItem.getPK();
         ExtSysCurrencyEVO listItem = this.getExtSysCurrenciesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeExtSysCurrenciesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteExtSysCurrenciesItem(ExtSysCurrencyPK removePK) {
      ExtSysCurrencyEVO listItem = this.getExtSysCurrenciesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeExtSysCurrenciesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ExtSysCurrencyEVO getExtSysCurrenciesItem(ExtSysCurrencyPK pk) {
      return (ExtSysCurrencyEVO)this.mExtSysCurrencies.get(pk);
   }

   public ExtSysCurrencyEVO getExtSysCurrenciesItem() {
      if(this.mExtSysCurrencies.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mExtSysCurrencies.size());
      } else {
         Iterator iter = this.mExtSysCurrencies.values().iterator();
         return (ExtSysCurrencyEVO)iter.next();
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

   public ExtSysLedgerRef getEntityRef(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, String entityText) {
      return new ExtSysLedgerRefImpl(new ExtSysLedgerCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), this.getPK()), entityText);
   }

   public ExtSysLedgerCK getCK(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany) {
      return new ExtSysLedgerCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mExtSysDimensionsAllItemsLoaded = true;
      if(this.mExtSysDimensions == null) {
         this.mExtSysDimensions = new HashMap();
      } else {
         Iterator i$ = this.mExtSysDimensions.values().iterator();

         while(i$.hasNext()) {
            ExtSysDimensionEVO child = (ExtSysDimensionEVO)i$.next();
            child.postCreateInit();
         }
      }

      this.mExtSysValueTypesAllItemsLoaded = true;
      if(this.mExtSysValueTypes == null) {
         this.mExtSysValueTypes = new HashMap();
      }

      this.mExtSysCurrenciesAllItemsLoaded = true;
      if(this.mExtSysCurrencies == null) {
         this.mExtSysCurrencies = new HashMap();
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
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("Dummy=");
      sb.append(String.valueOf(this.mDummy));
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

      sb.append("ExtSysLedger: ");
      sb.append(this.toString());
      if(this.mExtSysDimensionsAllItemsLoaded || this.mExtSysDimensions != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ExtSysDimensions: allItemsLoaded=");
         sb.append(String.valueOf(this.mExtSysDimensionsAllItemsLoaded));
         sb.append(" items=");
         if(this.mExtSysDimensions == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mExtSysDimensions.size()));
         }
      }

      if(this.mExtSysValueTypesAllItemsLoaded || this.mExtSysValueTypes != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ExtSysValueTypes: allItemsLoaded=");
         sb.append(String.valueOf(this.mExtSysValueTypesAllItemsLoaded));
         sb.append(" items=");
         if(this.mExtSysValueTypes == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mExtSysValueTypes.size()));
         }
      }

      if(this.mExtSysCurrenciesAllItemsLoaded || this.mExtSysCurrencies != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ExtSysCurrencies: allItemsLoaded=");
         sb.append(String.valueOf(this.mExtSysCurrenciesAllItemsLoaded));
         sb.append(" items=");
         if(this.mExtSysCurrencies == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mExtSysCurrencies.size()));
         }
      }

      Iterator var5;
      if(this.mExtSysDimensions != null) {
         var5 = this.mExtSysDimensions.values().iterator();

         while(var5.hasNext()) {
            ExtSysDimensionEVO listItem = (ExtSysDimensionEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mExtSysValueTypes != null) {
         var5 = this.mExtSysValueTypes.values().iterator();

         while(var5.hasNext()) {
            ExtSysValueTypeEVO var6 = (ExtSysValueTypeEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      if(this.mExtSysCurrencies != null) {
         var5 = this.mExtSysCurrencies.values().iterator();

         while(var5.hasNext()) {
            ExtSysCurrencyEVO var7 = (ExtSysCurrencyEVO)var5.next();
            var7.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ExtSysDimensionEVO child, int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDimensionVisId) {
      if(this.getExtSysDimensionsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mExtSysDimensions.remove(child.getPK());
         child.setExternalSystemId(newExternalSystemId);
         child.setCompanyVisId(newCompanyVisId);
         child.setLedgerVisId(newLedgerVisId);
         child.setDimensionVisId(newDimensionVisId);
         this.mExtSysDimensions.put(child.getPK(), child);
      }
   }

   public void changeKey(ExtSysValueTypeEVO child, int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newValueTypeVisId) {
      if(this.getExtSysValueTypesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mExtSysValueTypes.remove(child.getPK());
         child.setExternalSystemId(newExternalSystemId);
         child.setCompanyVisId(newCompanyVisId);
         child.setLedgerVisId(newLedgerVisId);
         child.setValueTypeVisId(newValueTypeVisId);
         this.mExtSysValueTypes.put(child.getPK(), child);
      }
   }

   public void changeKey(ExtSysCurrencyEVO child, int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newCurrencyVisId) {
      if(this.getExtSysCurrenciesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mExtSysCurrencies.remove(child.getPK());
         child.setExternalSystemId(newExternalSystemId);
         child.setCompanyVisId(newCompanyVisId);
         child.setLedgerVisId(newLedgerVisId);
         child.setCurrencyVisId(newCurrencyVisId);
         this.mExtSysCurrencies.put(child.getPK(), child);
      }
   }

   public void setExtSysDimensionsAllItemsLoaded(boolean allItemsLoaded) {
      this.mExtSysDimensionsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isExtSysDimensionsAllItemsLoaded() {
      return this.mExtSysDimensionsAllItemsLoaded;
   }

   public void setExtSysValueTypesAllItemsLoaded(boolean allItemsLoaded) {
      this.mExtSysValueTypesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isExtSysValueTypesAllItemsLoaded() {
      return this.mExtSysValueTypesAllItemsLoaded;
   }

   public void setExtSysCurrenciesAllItemsLoaded(boolean allItemsLoaded) {
      this.mExtSysCurrenciesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isExtSysCurrenciesAllItemsLoaded() {
      return this.mExtSysCurrenciesAllItemsLoaded;
   }
}
