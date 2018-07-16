// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc;

import com.cedar.cp.api.model.cc.CcDeploymentLineRef;
import com.cedar.cp.dto.model.cc.CcDeploymentDataTypePK;
import com.cedar.cp.dto.model.cc.CcDeploymentEntryPK;
import com.cedar.cp.dto.model.cc.CcDeploymentLineCK;
import com.cedar.cp.dto.model.cc.CcDeploymentLinePK;
import com.cedar.cp.dto.model.cc.CcDeploymentLineRefImpl;
import com.cedar.cp.dto.model.cc.CcMappingLinePK;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentDataTypeEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEntryEVO;
import com.cedar.cp.ejb.impl.model.cc.CcMappingLineEVO;
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

public class CcDeploymentLineEVO implements Serializable {

   private transient CcDeploymentLinePK mPK;
   private int mCcDeploymentLineId;
   private int mCcDeploymentId;
   private int mLineIndex;
   private int mCalLevel;
   private Map<CcDeploymentEntryPK, CcDeploymentEntryEVO> mCCDeploymentEntries;
   protected boolean mCCDeploymentEntriesAllItemsLoaded;
   private Map<CcDeploymentDataTypePK, CcDeploymentDataTypeEVO> mCCDeploymentDataTypes;
   protected boolean mCCDeploymentDataTypesAllItemsLoaded;
   private Map<CcMappingLinePK, CcMappingLineEVO> mCCMappingLines;
   protected boolean mCCMappingLinesAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public CcDeploymentLineEVO() {}

   public CcDeploymentLineEVO(int newCcDeploymentLineId, int newCcDeploymentId, int newLineIndex, int newCalLevel, Collection newCCDeploymentEntries, Collection newCCDeploymentDataTypes, Collection newCCMappingLines) {
      this.mCcDeploymentLineId = newCcDeploymentLineId;
      this.mCcDeploymentId = newCcDeploymentId;
      this.mLineIndex = newLineIndex;
      this.mCalLevel = newCalLevel;
      this.setCCDeploymentEntries(newCCDeploymentEntries);
      this.setCCDeploymentDataTypes(newCCDeploymentDataTypes);
      this.setCCMappingLines(newCCMappingLines);
   }

   public void setCCDeploymentEntries(Collection<CcDeploymentEntryEVO> items) {
      if(items != null) {
         if(this.mCCDeploymentEntries == null) {
            this.mCCDeploymentEntries = new HashMap();
         } else {
            this.mCCDeploymentEntries.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CcDeploymentEntryEVO child = (CcDeploymentEntryEVO)i$.next();
            this.mCCDeploymentEntries.put(child.getPK(), child);
         }
      } else {
         this.mCCDeploymentEntries = null;
      }

   }

   public void setCCDeploymentDataTypes(Collection<CcDeploymentDataTypeEVO> items) {
      if(items != null) {
         if(this.mCCDeploymentDataTypes == null) {
            this.mCCDeploymentDataTypes = new HashMap();
         } else {
            this.mCCDeploymentDataTypes.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CcDeploymentDataTypeEVO child = (CcDeploymentDataTypeEVO)i$.next();
            this.mCCDeploymentDataTypes.put(child.getPK(), child);
         }
      } else {
         this.mCCDeploymentDataTypes = null;
      }

   }

   public void setCCMappingLines(Collection<CcMappingLineEVO> items) {
      if(items != null) {
         if(this.mCCMappingLines == null) {
            this.mCCMappingLines = new HashMap();
         } else {
            this.mCCMappingLines.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CcMappingLineEVO child = (CcMappingLineEVO)i$.next();
            this.mCCMappingLines.put(child.getPK(), child);
         }
      } else {
         this.mCCMappingLines = null;
      }

   }

   public CcDeploymentLinePK getPK() {
      if(this.mPK == null) {
         this.mPK = new CcDeploymentLinePK(this.mCcDeploymentLineId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getCcDeploymentLineId() {
      return this.mCcDeploymentLineId;
   }

   public int getCcDeploymentId() {
      return this.mCcDeploymentId;
   }

   public int getLineIndex() {
      return this.mLineIndex;
   }

   public int getCalLevel() {
      return this.mCalLevel;
   }

   public void setCcDeploymentLineId(int newCcDeploymentLineId) {
      if(this.mCcDeploymentLineId != newCcDeploymentLineId) {
         this.mModified = true;
         this.mCcDeploymentLineId = newCcDeploymentLineId;
         this.mPK = null;
      }
   }

   public void setCcDeploymentId(int newCcDeploymentId) {
      if(this.mCcDeploymentId != newCcDeploymentId) {
         this.mModified = true;
         this.mCcDeploymentId = newCcDeploymentId;
      }
   }

   public void setLineIndex(int newLineIndex) {
      if(this.mLineIndex != newLineIndex) {
         this.mModified = true;
         this.mLineIndex = newLineIndex;
      }
   }

   public void setCalLevel(int newCalLevel) {
      if(this.mCalLevel != newCalLevel) {
         this.mModified = true;
         this.mCalLevel = newCalLevel;
      }
   }

   public void setDetails(CcDeploymentLineEVO newDetails) {
      this.setCcDeploymentLineId(newDetails.getCcDeploymentLineId());
      this.setCcDeploymentId(newDetails.getCcDeploymentId());
      this.setLineIndex(newDetails.getLineIndex());
      this.setCalLevel(newDetails.getCalLevel());
   }

   public CcDeploymentLineEVO deepClone() {
      CcDeploymentLineEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (CcDeploymentLineEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(CcDeploymentEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mCcDeploymentLineId > 0) {
         newKey = true;
         if(parent == null) {
            this.setCcDeploymentLineId(-this.mCcDeploymentLineId);
         } else {
            parent.changeKey(this, -this.mCcDeploymentLineId);
         }
      } else if(this.mCcDeploymentLineId < 1) {
         newKey = true;
      }

      Iterator iter;
      CcDeploymentEntryEVO item;
      if(this.mCCDeploymentEntries != null) {
         for(iter = (new ArrayList(this.mCCDeploymentEntries.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (CcDeploymentEntryEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      CcDeploymentDataTypeEVO item1;
      if(this.mCCDeploymentDataTypes != null) {
         for(iter = (new ArrayList(this.mCCDeploymentDataTypes.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (CcDeploymentDataTypeEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

      CcMappingLineEVO item2;
      if(this.mCCMappingLines != null) {
         for(iter = (new ArrayList(this.mCCMappingLines.values())).iterator(); iter.hasNext(); item2.prepareForInsert(this)) {
            item2 = (CcMappingLineEVO)iter.next();
            if(newKey) {
               item2.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mCcDeploymentLineId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      CcDeploymentEntryEVO item;
      if(this.mCCDeploymentEntries != null) {
         for(iter = this.mCCDeploymentEntries.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (CcDeploymentEntryEVO)iter.next();
         }
      }

      CcDeploymentDataTypeEVO item1;
      if(this.mCCDeploymentDataTypes != null) {
         for(iter = this.mCCDeploymentDataTypes.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (CcDeploymentDataTypeEVO)iter.next();
         }
      }

      CcMappingLineEVO item2;
      if(this.mCCMappingLines != null) {
         for(iter = this.mCCMappingLines.values().iterator(); iter.hasNext(); returnCount = item2.getInsertCount(returnCount)) {
            item2 = (CcMappingLineEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(CcDeploymentEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mCcDeploymentLineId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      Iterator iter;
      CcDeploymentEntryEVO item;
      if(this.mCCDeploymentEntries != null) {
         for(iter = (new ArrayList(this.mCCDeploymentEntries.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (CcDeploymentEntryEVO)iter.next();
            item.setCcDeploymentLineId(this.mCcDeploymentLineId);
         }
      }

      CcDeploymentDataTypeEVO item1;
      if(this.mCCDeploymentDataTypes != null) {
         for(iter = (new ArrayList(this.mCCDeploymentDataTypes.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (CcDeploymentDataTypeEVO)iter.next();
            item1.setCcDeploymentLineId(this.mCcDeploymentLineId);
         }
      }

      CcMappingLineEVO item2;
      if(this.mCCMappingLines != null) {
         for(iter = (new ArrayList(this.mCCMappingLines.values())).iterator(); iter.hasNext(); nextKey = item2.assignNextKey(this, nextKey)) {
            item2 = (CcMappingLineEVO)iter.next();
            item2.setCcDeploymentLineId(this.mCcDeploymentLineId);
         }
      }

      return nextKey;
   }

   public Collection<CcDeploymentEntryEVO> getCCDeploymentEntries() {
      return this.mCCDeploymentEntries != null?this.mCCDeploymentEntries.values():null;
   }

   public Map<CcDeploymentEntryPK, CcDeploymentEntryEVO> getCCDeploymentEntriesMap() {
      return this.mCCDeploymentEntries;
   }

   public void loadCCDeploymentEntriesItem(CcDeploymentEntryEVO newItem) {
      if(this.mCCDeploymentEntries == null) {
         this.mCCDeploymentEntries = new HashMap();
      }

      this.mCCDeploymentEntries.put(newItem.getPK(), newItem);
   }

   public void addCCDeploymentEntriesItem(CcDeploymentEntryEVO newItem) {
      if(this.mCCDeploymentEntries == null) {
         this.mCCDeploymentEntries = new HashMap();
      }

      CcDeploymentEntryPK newPK = newItem.getPK();
      if(this.getCCDeploymentEntriesItem(newPK) != null) {
         throw new RuntimeException("addCCDeploymentEntriesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mCCDeploymentEntries.put(newPK, newItem);
      }
   }

   public void changeCCDeploymentEntriesItem(CcDeploymentEntryEVO changedItem) {
      if(this.mCCDeploymentEntries == null) {
         throw new RuntimeException("changeCCDeploymentEntriesItem: no items in collection");
      } else {
         CcDeploymentEntryPK changedPK = changedItem.getPK();
         CcDeploymentEntryEVO listItem = this.getCCDeploymentEntriesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeCCDeploymentEntriesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteCCDeploymentEntriesItem(CcDeploymentEntryPK removePK) {
      CcDeploymentEntryEVO listItem = this.getCCDeploymentEntriesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeCCDeploymentEntriesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public CcDeploymentEntryEVO getCCDeploymentEntriesItem(CcDeploymentEntryPK pk) {
      return (CcDeploymentEntryEVO)this.mCCDeploymentEntries.get(pk);
   }

   public CcDeploymentEntryEVO getCCDeploymentEntriesItem() {
      if(this.mCCDeploymentEntries.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mCCDeploymentEntries.size());
      } else {
         Iterator iter = this.mCCDeploymentEntries.values().iterator();
         return (CcDeploymentEntryEVO)iter.next();
      }
   }

   public Collection<CcDeploymentDataTypeEVO> getCCDeploymentDataTypes() {
      return this.mCCDeploymentDataTypes != null?this.mCCDeploymentDataTypes.values():null;
   }

   public Map<CcDeploymentDataTypePK, CcDeploymentDataTypeEVO> getCCDeploymentDataTypesMap() {
      return this.mCCDeploymentDataTypes;
   }

   public void loadCCDeploymentDataTypesItem(CcDeploymentDataTypeEVO newItem) {
      if(this.mCCDeploymentDataTypes == null) {
         this.mCCDeploymentDataTypes = new HashMap();
      }

      this.mCCDeploymentDataTypes.put(newItem.getPK(), newItem);
   }

   public void addCCDeploymentDataTypesItem(CcDeploymentDataTypeEVO newItem) {
      if(this.mCCDeploymentDataTypes == null) {
         this.mCCDeploymentDataTypes = new HashMap();
      }

      CcDeploymentDataTypePK newPK = newItem.getPK();
      if(this.getCCDeploymentDataTypesItem(newPK) != null) {
         throw new RuntimeException("addCCDeploymentDataTypesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mCCDeploymentDataTypes.put(newPK, newItem);
      }
   }

   public void changeCCDeploymentDataTypesItem(CcDeploymentDataTypeEVO changedItem) {
      if(this.mCCDeploymentDataTypes == null) {
         throw new RuntimeException("changeCCDeploymentDataTypesItem: no items in collection");
      } else {
         CcDeploymentDataTypePK changedPK = changedItem.getPK();
         CcDeploymentDataTypeEVO listItem = this.getCCDeploymentDataTypesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeCCDeploymentDataTypesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteCCDeploymentDataTypesItem(CcDeploymentDataTypePK removePK) {
      CcDeploymentDataTypeEVO listItem = this.getCCDeploymentDataTypesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeCCDeploymentDataTypesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public CcDeploymentDataTypeEVO getCCDeploymentDataTypesItem(CcDeploymentDataTypePK pk) {
      return (CcDeploymentDataTypeEVO)this.mCCDeploymentDataTypes.get(pk);
   }

   public CcDeploymentDataTypeEVO getCCDeploymentDataTypesItem() {
      if(this.mCCDeploymentDataTypes.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mCCDeploymentDataTypes.size());
      } else {
         Iterator iter = this.mCCDeploymentDataTypes.values().iterator();
         return (CcDeploymentDataTypeEVO)iter.next();
      }
   }

   public Collection<CcMappingLineEVO> getCCMappingLines() {
      return this.mCCMappingLines != null?this.mCCMappingLines.values():null;
   }

   public Map<CcMappingLinePK, CcMappingLineEVO> getCCMappingLinesMap() {
      return this.mCCMappingLines;
   }

   public void loadCCMappingLinesItem(CcMappingLineEVO newItem) {
      if(this.mCCMappingLines == null) {
         this.mCCMappingLines = new HashMap();
      }

      this.mCCMappingLines.put(newItem.getPK(), newItem);
   }

   public void addCCMappingLinesItem(CcMappingLineEVO newItem) {
      if(this.mCCMappingLines == null) {
         this.mCCMappingLines = new HashMap();
      }

      CcMappingLinePK newPK = newItem.getPK();
      if(this.getCCMappingLinesItem(newPK) != null) {
         throw new RuntimeException("addCCMappingLinesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mCCMappingLines.put(newPK, newItem);
      }
   }

   public void changeCCMappingLinesItem(CcMappingLineEVO changedItem) {
      if(this.mCCMappingLines == null) {
         throw new RuntimeException("changeCCMappingLinesItem: no items in collection");
      } else {
         CcMappingLinePK changedPK = changedItem.getPK();
         CcMappingLineEVO listItem = this.getCCMappingLinesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeCCMappingLinesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteCCMappingLinesItem(CcMappingLinePK removePK) {
      CcMappingLineEVO listItem = this.getCCMappingLinesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeCCMappingLinesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public CcMappingLineEVO getCCMappingLinesItem(CcMappingLinePK pk) {
      return (CcMappingLineEVO)this.mCCMappingLines.get(pk);
   }

   public CcMappingLineEVO getCCMappingLinesItem() {
      if(this.mCCMappingLines.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mCCMappingLines.size());
      } else {
         Iterator iter = this.mCCMappingLines.values().iterator();
         return (CcMappingLineEVO)iter.next();
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

   public CcDeploymentLineRef getEntityRef(ModelEVO evoModel, CcDeploymentEVO evoCcDeployment, String entityText) {
      return new CcDeploymentLineRefImpl(new CcDeploymentLineCK(evoModel.getPK(), evoCcDeployment.getPK(), this.getPK()), entityText);
   }

   public CcDeploymentLineCK getCK(ModelEVO evoModel, CcDeploymentEVO evoCcDeployment) {
      return new CcDeploymentLineCK(evoModel.getPK(), evoCcDeployment.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mCCDeploymentEntriesAllItemsLoaded = true;
      if(this.mCCDeploymentEntries == null) {
         this.mCCDeploymentEntries = new HashMap();
      }

      this.mCCDeploymentDataTypesAllItemsLoaded = true;
      if(this.mCCDeploymentDataTypes == null) {
         this.mCCDeploymentDataTypes = new HashMap();
      }

      this.mCCMappingLinesAllItemsLoaded = true;
      if(this.mCCMappingLines == null) {
         this.mCCMappingLines = new HashMap();
      } else {
         Iterator i$ = this.mCCMappingLines.values().iterator();

         while(i$.hasNext()) {
            CcMappingLineEVO child = (CcMappingLineEVO)i$.next();
            child.postCreateInit();
         }
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("CcDeploymentLineId=");
      sb.append(String.valueOf(this.mCcDeploymentLineId));
      sb.append(' ');
      sb.append("CcDeploymentId=");
      sb.append(String.valueOf(this.mCcDeploymentId));
      sb.append(' ');
      sb.append("LineIndex=");
      sb.append(String.valueOf(this.mLineIndex));
      sb.append(' ');
      sb.append("CalLevel=");
      sb.append(String.valueOf(this.mCalLevel));
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

      sb.append("CcDeploymentLine: ");
      sb.append(this.toString());
      if(this.mCCDeploymentEntriesAllItemsLoaded || this.mCCDeploymentEntries != null) {
         sb.delete(indent, sb.length());
         sb.append(" - CCDeploymentEntries: allItemsLoaded=");
         sb.append(String.valueOf(this.mCCDeploymentEntriesAllItemsLoaded));
         sb.append(" items=");
         if(this.mCCDeploymentEntries == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mCCDeploymentEntries.size()));
         }
      }

      if(this.mCCDeploymentDataTypesAllItemsLoaded || this.mCCDeploymentDataTypes != null) {
         sb.delete(indent, sb.length());
         sb.append(" - CCDeploymentDataTypes: allItemsLoaded=");
         sb.append(String.valueOf(this.mCCDeploymentDataTypesAllItemsLoaded));
         sb.append(" items=");
         if(this.mCCDeploymentDataTypes == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mCCDeploymentDataTypes.size()));
         }
      }

      if(this.mCCMappingLinesAllItemsLoaded || this.mCCMappingLines != null) {
         sb.delete(indent, sb.length());
         sb.append(" - CCMappingLines: allItemsLoaded=");
         sb.append(String.valueOf(this.mCCMappingLinesAllItemsLoaded));
         sb.append(" items=");
         if(this.mCCMappingLines == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mCCMappingLines.size()));
         }
      }

      Iterator var5;
      if(this.mCCDeploymentEntries != null) {
         var5 = this.mCCDeploymentEntries.values().iterator();

         while(var5.hasNext()) {
            CcDeploymentEntryEVO listItem = (CcDeploymentEntryEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mCCDeploymentDataTypes != null) {
         var5 = this.mCCDeploymentDataTypes.values().iterator();

         while(var5.hasNext()) {
            CcDeploymentDataTypeEVO var6 = (CcDeploymentDataTypeEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      if(this.mCCMappingLines != null) {
         var5 = this.mCCMappingLines.values().iterator();

         while(var5.hasNext()) {
            CcMappingLineEVO var7 = (CcMappingLineEVO)var5.next();
            var7.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(CcDeploymentEntryEVO child, int newCcDeploymentEntryId) {
      if(this.getCCDeploymentEntriesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mCCDeploymentEntries.remove(child.getPK());
         child.setCcDeploymentEntryId(newCcDeploymentEntryId);
         this.mCCDeploymentEntries.put(child.getPK(), child);
      }
   }

   public void changeKey(CcDeploymentDataTypeEVO child, int newCcDeploymentDataTypeId) {
      if(this.getCCDeploymentDataTypesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mCCDeploymentDataTypes.remove(child.getPK());
         child.setCcDeploymentDataTypeId(newCcDeploymentDataTypeId);
         this.mCCDeploymentDataTypes.put(child.getPK(), child);
      }
   }

   public void changeKey(CcMappingLineEVO child, int newCcMappingLineId) {
      if(this.getCCMappingLinesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mCCMappingLines.remove(child.getPK());
         child.setCcMappingLineId(newCcMappingLineId);
         this.mCCMappingLines.put(child.getPK(), child);
      }
   }

   public CcDeploymentEntryEVO findDeploymentEntry(int dimSeq, int structureId, int structureElementId) {
      if(this.mCCDeploymentEntries != null) {
         Iterator i$ = this.mCCDeploymentEntries.values().iterator();

         while(i$.hasNext()) {
            CcDeploymentEntryEVO entryEVO = (CcDeploymentEntryEVO)i$.next();
            if(entryEVO.getDimSeq() == dimSeq && entryEVO.getStructureId() == structureId && entryEVO.getStructureElementId() == structureElementId) {
               return entryEVO;
            }
         }
      }

      return null;
   }

   public void setCCDeploymentEntriesAllItemsLoaded(boolean allItemsLoaded) {
      this.mCCDeploymentEntriesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isCCDeploymentEntriesAllItemsLoaded() {
      return this.mCCDeploymentEntriesAllItemsLoaded;
   }

   public void setCCDeploymentDataTypesAllItemsLoaded(boolean allItemsLoaded) {
      this.mCCDeploymentDataTypesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isCCDeploymentDataTypesAllItemsLoaded() {
      return this.mCCDeploymentDataTypesAllItemsLoaded;
   }

   public void setCCMappingLinesAllItemsLoaded(boolean allItemsLoaded) {
      this.mCCMappingLinesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isCCMappingLinesAllItemsLoaded() {
      return this.mCCMappingLinesAllItemsLoaded;
   }
}
