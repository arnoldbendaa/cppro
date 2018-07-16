// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc;

import com.cedar.cp.api.model.cc.CcMappingLineRef;
import com.cedar.cp.dto.model.cc.CcMappingEntryPK;
import com.cedar.cp.dto.model.cc.CcMappingLineCK;
import com.cedar.cp.dto.model.cc.CcMappingLinePK;
import com.cedar.cp.dto.model.cc.CcMappingLineRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentLineEVO;
import com.cedar.cp.ejb.impl.model.cc.CcMappingEntryEVO;
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

public class CcMappingLineEVO implements Serializable {

   private transient CcMappingLinePK mPK;
   private int mCcMappingLineId;
   private int mCcDeploymentLineId;
   private int mDataTypeId;
   private String mFormFieldName;
   private Map<CcMappingEntryPK, CcMappingEntryEVO> mCCMappingEntries;
   protected boolean mCCMappingEntriesAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public CcMappingLineEVO() {}

   public CcMappingLineEVO(int newCcMappingLineId, int newCcDeploymentLineId, int newDataTypeId, String newFormFieldName, Collection newCCMappingEntries) {
      this.mCcMappingLineId = newCcMappingLineId;
      this.mCcDeploymentLineId = newCcDeploymentLineId;
      this.mDataTypeId = newDataTypeId;
      this.mFormFieldName = newFormFieldName;
      this.setCCMappingEntries(newCCMappingEntries);
   }

   public void setCCMappingEntries(Collection<CcMappingEntryEVO> items) {
      if(items != null) {
         if(this.mCCMappingEntries == null) {
            this.mCCMappingEntries = new HashMap();
         } else {
            this.mCCMappingEntries.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CcMappingEntryEVO child = (CcMappingEntryEVO)i$.next();
            this.mCCMappingEntries.put(child.getPK(), child);
         }
      } else {
         this.mCCMappingEntries = null;
      }

   }

   public CcMappingLinePK getPK() {
      if(this.mPK == null) {
         this.mPK = new CcMappingLinePK(this.mCcMappingLineId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getCcMappingLineId() {
      return this.mCcMappingLineId;
   }

   public int getCcDeploymentLineId() {
      return this.mCcDeploymentLineId;
   }

   public int getDataTypeId() {
      return this.mDataTypeId;
   }

   public String getFormFieldName() {
      return this.mFormFieldName;
   }

   public void setCcMappingLineId(int newCcMappingLineId) {
      if(this.mCcMappingLineId != newCcMappingLineId) {
         this.mModified = true;
         this.mCcMappingLineId = newCcMappingLineId;
         this.mPK = null;
      }
   }

   public void setCcDeploymentLineId(int newCcDeploymentLineId) {
      if(this.mCcDeploymentLineId != newCcDeploymentLineId) {
         this.mModified = true;
         this.mCcDeploymentLineId = newCcDeploymentLineId;
      }
   }

   public void setDataTypeId(int newDataTypeId) {
      if(this.mDataTypeId != newDataTypeId) {
         this.mModified = true;
         this.mDataTypeId = newDataTypeId;
      }
   }

   public void setFormFieldName(String newFormFieldName) {
      if(this.mFormFieldName != null && newFormFieldName == null || this.mFormFieldName == null && newFormFieldName != null || this.mFormFieldName != null && newFormFieldName != null && !this.mFormFieldName.equals(newFormFieldName)) {
         this.mFormFieldName = newFormFieldName;
         this.mModified = true;
      }

   }

   public void setDetails(CcMappingLineEVO newDetails) {
      this.setCcMappingLineId(newDetails.getCcMappingLineId());
      this.setCcDeploymentLineId(newDetails.getCcDeploymentLineId());
      this.setDataTypeId(newDetails.getDataTypeId());
      this.setFormFieldName(newDetails.getFormFieldName());
   }

   public CcMappingLineEVO deepClone() {
      CcMappingLineEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (CcMappingLineEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(CcDeploymentLineEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mCcMappingLineId > 0) {
         newKey = true;
         if(parent == null) {
            this.setCcMappingLineId(-this.mCcMappingLineId);
         } else {
            parent.changeKey(this, -this.mCcMappingLineId);
         }
      } else if(this.mCcMappingLineId < 1) {
         newKey = true;
      }

      CcMappingEntryEVO item;
      if(this.mCCMappingEntries != null) {
         for(Iterator iter = (new ArrayList(this.mCCMappingEntries.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (CcMappingEntryEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mCcMappingLineId < 1) {
         returnCount = startCount + 1;
      }

      CcMappingEntryEVO item;
      if(this.mCCMappingEntries != null) {
         for(Iterator iter = this.mCCMappingEntries.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (CcMappingEntryEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(CcDeploymentLineEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mCcMappingLineId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      CcMappingEntryEVO item;
      if(this.mCCMappingEntries != null) {
         for(Iterator iter = (new ArrayList(this.mCCMappingEntries.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (CcMappingEntryEVO)iter.next();
            item.setCcMappingLineId(this.mCcMappingLineId);
         }
      }

      return nextKey;
   }

   public Collection<CcMappingEntryEVO> getCCMappingEntries() {
      return this.mCCMappingEntries != null?this.mCCMappingEntries.values():null;
   }

   public Map<CcMappingEntryPK, CcMappingEntryEVO> getCCMappingEntriesMap() {
      return this.mCCMappingEntries;
   }

   public void loadCCMappingEntriesItem(CcMappingEntryEVO newItem) {
      if(this.mCCMappingEntries == null) {
         this.mCCMappingEntries = new HashMap();
      }

      this.mCCMappingEntries.put(newItem.getPK(), newItem);
   }

   public void addCCMappingEntriesItem(CcMappingEntryEVO newItem) {
      if(this.mCCMappingEntries == null) {
         this.mCCMappingEntries = new HashMap();
      }

      CcMappingEntryPK newPK = newItem.getPK();
      if(this.getCCMappingEntriesItem(newPK) != null) {
         throw new RuntimeException("addCCMappingEntriesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mCCMappingEntries.put(newPK, newItem);
      }
   }

   public void changeCCMappingEntriesItem(CcMappingEntryEVO changedItem) {
      if(this.mCCMappingEntries == null) {
         throw new RuntimeException("changeCCMappingEntriesItem: no items in collection");
      } else {
         CcMappingEntryPK changedPK = changedItem.getPK();
         CcMappingEntryEVO listItem = this.getCCMappingEntriesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeCCMappingEntriesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteCCMappingEntriesItem(CcMappingEntryPK removePK) {
      CcMappingEntryEVO listItem = this.getCCMappingEntriesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeCCMappingEntriesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public CcMappingEntryEVO getCCMappingEntriesItem(CcMappingEntryPK pk) {
      return (CcMappingEntryEVO)this.mCCMappingEntries.get(pk);
   }

   public CcMappingEntryEVO getCCMappingEntriesItem() {
      if(this.mCCMappingEntries.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mCCMappingEntries.size());
      } else {
         Iterator iter = this.mCCMappingEntries.values().iterator();
         return (CcMappingEntryEVO)iter.next();
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

   public CcMappingLineRef getEntityRef(ModelEVO evoModel, CcDeploymentEVO evoCcDeployment, CcDeploymentLineEVO evoCcDeploymentLine, String entityText) {
      return new CcMappingLineRefImpl(new CcMappingLineCK(evoModel.getPK(), evoCcDeployment.getPK(), evoCcDeploymentLine.getPK(), this.getPK()), entityText);
   }

   public CcMappingLineCK getCK(ModelEVO evoModel, CcDeploymentEVO evoCcDeployment, CcDeploymentLineEVO evoCcDeploymentLine) {
      return new CcMappingLineCK(evoModel.getPK(), evoCcDeployment.getPK(), evoCcDeploymentLine.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mCCMappingEntriesAllItemsLoaded = true;
      if(this.mCCMappingEntries == null) {
         this.mCCMappingEntries = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("CcMappingLineId=");
      sb.append(String.valueOf(this.mCcMappingLineId));
      sb.append(' ');
      sb.append("CcDeploymentLineId=");
      sb.append(String.valueOf(this.mCcDeploymentLineId));
      sb.append(' ');
      sb.append("DataTypeId=");
      sb.append(String.valueOf(this.mDataTypeId));
      sb.append(' ');
      sb.append("FormFieldName=");
      sb.append(String.valueOf(this.mFormFieldName));
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

      sb.append("CcMappingLine: ");
      sb.append(this.toString());
      if(this.mCCMappingEntriesAllItemsLoaded || this.mCCMappingEntries != null) {
         sb.delete(indent, sb.length());
         sb.append(" - CCMappingEntries: allItemsLoaded=");
         sb.append(String.valueOf(this.mCCMappingEntriesAllItemsLoaded));
         sb.append(" items=");
         if(this.mCCMappingEntries == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mCCMappingEntries.size()));
         }
      }

      if(this.mCCMappingEntries != null) {
         Iterator var5 = this.mCCMappingEntries.values().iterator();

         while(var5.hasNext()) {
            CcMappingEntryEVO listItem = (CcMappingEntryEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(CcMappingEntryEVO child, int newCcMappingEntryId) {
      if(this.getCCMappingEntriesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mCCMappingEntries.remove(child.getPK());
         child.setCcMappingEntryId(newCcMappingEntryId);
         this.mCCMappingEntries.put(child.getPK(), child);
      }
   }

   public CcMappingEntryEVO findMappingEntry(int dimensionSequence) {
      if(this.mCCMappingEntries != null) {
         Iterator i$ = this.mCCMappingEntries.values().iterator();

         while(i$.hasNext()) {
            CcMappingEntryEVO entryEVO = (CcMappingEntryEVO)i$.next();
            if(entryEVO.getDimSeq() == dimensionSequence) {
               return entryEVO;
            }
         }
      }

      return null;
   }

   public void setCCMappingEntriesAllItemsLoaded(boolean allItemsLoaded) {
      this.mCCMappingEntriesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isCCMappingEntriesAllItemsLoaded() {
      return this.mCCMappingEntriesAllItemsLoaded;
   }
}
