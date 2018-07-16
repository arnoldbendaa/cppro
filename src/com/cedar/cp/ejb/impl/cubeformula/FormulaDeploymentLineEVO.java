// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cubeformula;

import com.cedar.cp.api.cubeformula.FormulaDeploymentLineRef;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtPK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryPK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLineCK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLinePK;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLineRefImpl;
import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaEVO;
import com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentDtEVO;
import com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentEntryEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
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

public class FormulaDeploymentLineEVO implements Serializable {

   private transient FormulaDeploymentLinePK mPK;
   private int mFormulaDeploymentLineId;
   private int mCubeFormulaId;
   private int mLineIndex;
   private String mContext;
   private Map<FormulaDeploymentEntryPK, FormulaDeploymentEntryEVO> mDeploymentEntries;
   protected boolean mDeploymentEntriesAllItemsLoaded;
   private Map<FormulaDeploymentDtPK, FormulaDeploymentDtEVO> mDeploymentDataTypes;
   protected boolean mDeploymentDataTypesAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public FormulaDeploymentLineEVO() {}

   public FormulaDeploymentLineEVO(int newFormulaDeploymentLineId, int newCubeFormulaId, int newLineIndex, String newContext, Collection newDeploymentEntries, Collection newDeploymentDataTypes) {
      this.mFormulaDeploymentLineId = newFormulaDeploymentLineId;
      this.mCubeFormulaId = newCubeFormulaId;
      this.mLineIndex = newLineIndex;
      this.mContext = newContext;
      this.setDeploymentEntries(newDeploymentEntries);
      this.setDeploymentDataTypes(newDeploymentDataTypes);
   }

   public void setDeploymentEntries(Collection<FormulaDeploymentEntryEVO> items) {
      if(items != null) {
         if(this.mDeploymentEntries == null) {
            this.mDeploymentEntries = new HashMap();
         } else {
            this.mDeploymentEntries.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            FormulaDeploymentEntryEVO child = (FormulaDeploymentEntryEVO)i$.next();
            this.mDeploymentEntries.put(child.getPK(), child);
         }
      } else {
         this.mDeploymentEntries = null;
      }

   }

   public void setDeploymentDataTypes(Collection<FormulaDeploymentDtEVO> items) {
      if(items != null) {
         if(this.mDeploymentDataTypes == null) {
            this.mDeploymentDataTypes = new HashMap();
         } else {
            this.mDeploymentDataTypes.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            FormulaDeploymentDtEVO child = (FormulaDeploymentDtEVO)i$.next();
            this.mDeploymentDataTypes.put(child.getPK(), child);
         }
      } else {
         this.mDeploymentDataTypes = null;
      }

   }

   public FormulaDeploymentLinePK getPK() {
      if(this.mPK == null) {
         this.mPK = new FormulaDeploymentLinePK(this.mFormulaDeploymentLineId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getFormulaDeploymentLineId() {
      return this.mFormulaDeploymentLineId;
   }

   public int getCubeFormulaId() {
      return this.mCubeFormulaId;
   }

   public int getLineIndex() {
      return this.mLineIndex;
   }

   public String getContext() {
      return this.mContext;
   }

   public void setFormulaDeploymentLineId(int newFormulaDeploymentLineId) {
      if(this.mFormulaDeploymentLineId != newFormulaDeploymentLineId) {
         this.mModified = true;
         this.mFormulaDeploymentLineId = newFormulaDeploymentLineId;
         this.mPK = null;
      }
   }

   public void setCubeFormulaId(int newCubeFormulaId) {
      if(this.mCubeFormulaId != newCubeFormulaId) {
         this.mModified = true;
         this.mCubeFormulaId = newCubeFormulaId;
      }
   }

   public void setLineIndex(int newLineIndex) {
      if(this.mLineIndex != newLineIndex) {
         this.mModified = true;
         this.mLineIndex = newLineIndex;
      }
   }

   public void setContext(String newContext) {
      if(this.mContext != null && newContext == null || this.mContext == null && newContext != null || this.mContext != null && newContext != null && !this.mContext.equals(newContext)) {
         this.mContext = newContext;
         this.mModified = true;
      }

   }

   public void setDetails(FormulaDeploymentLineEVO newDetails) {
      this.setFormulaDeploymentLineId(newDetails.getFormulaDeploymentLineId());
      this.setCubeFormulaId(newDetails.getCubeFormulaId());
      this.setLineIndex(newDetails.getLineIndex());
      this.setContext(newDetails.getContext());
   }

   public FormulaDeploymentLineEVO deepClone() {
      FormulaDeploymentLineEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (FormulaDeploymentLineEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(CubeFormulaEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mFormulaDeploymentLineId > 0) {
         newKey = true;
         if(parent == null) {
            this.setFormulaDeploymentLineId(-this.mFormulaDeploymentLineId);
         } else {
            parent.changeKey(this, -this.mFormulaDeploymentLineId);
         }
      } else if(this.mFormulaDeploymentLineId < 1) {
         newKey = true;
      }

      Iterator iter;
      FormulaDeploymentEntryEVO item;
      if(this.mDeploymentEntries != null) {
         for(iter = (new ArrayList(this.mDeploymentEntries.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (FormulaDeploymentEntryEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      FormulaDeploymentDtEVO item1;
      if(this.mDeploymentDataTypes != null) {
         for(iter = (new ArrayList(this.mDeploymentDataTypes.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (FormulaDeploymentDtEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mFormulaDeploymentLineId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      FormulaDeploymentEntryEVO item;
      if(this.mDeploymentEntries != null) {
         for(iter = this.mDeploymentEntries.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (FormulaDeploymentEntryEVO)iter.next();
         }
      }

      FormulaDeploymentDtEVO item1;
      if(this.mDeploymentDataTypes != null) {
         for(iter = this.mDeploymentDataTypes.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (FormulaDeploymentDtEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(CubeFormulaEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mFormulaDeploymentLineId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      Iterator iter;
      FormulaDeploymentEntryEVO item;
      if(this.mDeploymentEntries != null) {
         for(iter = (new ArrayList(this.mDeploymentEntries.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (FormulaDeploymentEntryEVO)iter.next();
            item.setFormulaDeploymentLineId(this.mFormulaDeploymentLineId);
         }
      }

      FormulaDeploymentDtEVO item1;
      if(this.mDeploymentDataTypes != null) {
         for(iter = (new ArrayList(this.mDeploymentDataTypes.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (FormulaDeploymentDtEVO)iter.next();
            item1.setFormulaDeploymentLineId(this.mFormulaDeploymentLineId);
         }
      }

      return nextKey;
   }

   public Collection<FormulaDeploymentEntryEVO> getDeploymentEntries() {
      return this.mDeploymentEntries != null?this.mDeploymentEntries.values():null;
   }

   public Map<FormulaDeploymentEntryPK, FormulaDeploymentEntryEVO> getDeploymentEntriesMap() {
      return this.mDeploymentEntries;
   }

   public void loadDeploymentEntriesItem(FormulaDeploymentEntryEVO newItem) {
      if(this.mDeploymentEntries == null) {
         this.mDeploymentEntries = new HashMap();
      }

      this.mDeploymentEntries.put(newItem.getPK(), newItem);
   }

   public void addDeploymentEntriesItem(FormulaDeploymentEntryEVO newItem) {
      if(this.mDeploymentEntries == null) {
         this.mDeploymentEntries = new HashMap();
      }

      FormulaDeploymentEntryPK newPK = newItem.getPK();
      if(this.getDeploymentEntriesItem(newPK) != null) {
         throw new RuntimeException("addDeploymentEntriesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mDeploymentEntries.put(newPK, newItem);
      }
   }

   public void changeDeploymentEntriesItem(FormulaDeploymentEntryEVO changedItem) {
      if(this.mDeploymentEntries == null) {
         throw new RuntimeException("changeDeploymentEntriesItem: no items in collection");
      } else {
         FormulaDeploymentEntryPK changedPK = changedItem.getPK();
         FormulaDeploymentEntryEVO listItem = this.getDeploymentEntriesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeDeploymentEntriesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteDeploymentEntriesItem(FormulaDeploymentEntryPK removePK) {
      FormulaDeploymentEntryEVO listItem = this.getDeploymentEntriesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeDeploymentEntriesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public FormulaDeploymentEntryEVO getDeploymentEntriesItem(FormulaDeploymentEntryPK pk) {
      return (FormulaDeploymentEntryEVO)this.mDeploymentEntries.get(pk);
   }

   public FormulaDeploymentEntryEVO getDeploymentEntriesItem() {
      if(this.mDeploymentEntries.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mDeploymentEntries.size());
      } else {
         Iterator iter = this.mDeploymentEntries.values().iterator();
         return (FormulaDeploymentEntryEVO)iter.next();
      }
   }

   public Collection<FormulaDeploymentDtEVO> getDeploymentDataTypes() {
      return this.mDeploymentDataTypes != null?this.mDeploymentDataTypes.values():null;
   }

   public Map<FormulaDeploymentDtPK, FormulaDeploymentDtEVO> getDeploymentDataTypesMap() {
      return this.mDeploymentDataTypes;
   }

   public void loadDeploymentDataTypesItem(FormulaDeploymentDtEVO newItem) {
      if(this.mDeploymentDataTypes == null) {
         this.mDeploymentDataTypes = new HashMap();
      }

      this.mDeploymentDataTypes.put(newItem.getPK(), newItem);
   }

   public void addDeploymentDataTypesItem(FormulaDeploymentDtEVO newItem) {
      if(this.mDeploymentDataTypes == null) {
         this.mDeploymentDataTypes = new HashMap();
      }

      FormulaDeploymentDtPK newPK = newItem.getPK();
      if(this.getDeploymentDataTypesItem(newPK) != null) {
         throw new RuntimeException("addDeploymentDataTypesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mDeploymentDataTypes.put(newPK, newItem);
      }
   }

   public void changeDeploymentDataTypesItem(FormulaDeploymentDtEVO changedItem) {
      if(this.mDeploymentDataTypes == null) {
         throw new RuntimeException("changeDeploymentDataTypesItem: no items in collection");
      } else {
         FormulaDeploymentDtPK changedPK = changedItem.getPK();
         FormulaDeploymentDtEVO listItem = this.getDeploymentDataTypesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeDeploymentDataTypesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteDeploymentDataTypesItem(FormulaDeploymentDtPK removePK) {
      FormulaDeploymentDtEVO listItem = this.getDeploymentDataTypesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeDeploymentDataTypesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public FormulaDeploymentDtEVO getDeploymentDataTypesItem(FormulaDeploymentDtPK pk) {
      return (FormulaDeploymentDtEVO)this.mDeploymentDataTypes.get(pk);
   }

   public FormulaDeploymentDtEVO getDeploymentDataTypesItem() {
      if(this.mDeploymentDataTypes.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mDeploymentDataTypes.size());
      } else {
         Iterator iter = this.mDeploymentDataTypes.values().iterator();
         return (FormulaDeploymentDtEVO)iter.next();
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

   public FormulaDeploymentLineRef getEntityRef(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube, CubeFormulaEVO evoCubeFormula, String entityText) {
      return new FormulaDeploymentLineRefImpl(new FormulaDeploymentLineCK(evoModel.getPK(), evoFinanceCube.getPK(), evoCubeFormula.getPK(), this.getPK()), entityText);
   }

   public FormulaDeploymentLineCK getCK(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube, CubeFormulaEVO evoCubeFormula) {
      return new FormulaDeploymentLineCK(evoModel.getPK(), evoFinanceCube.getPK(), evoCubeFormula.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mDeploymentEntriesAllItemsLoaded = true;
      if(this.mDeploymentEntries == null) {
         this.mDeploymentEntries = new HashMap();
      }

      this.mDeploymentDataTypesAllItemsLoaded = true;
      if(this.mDeploymentDataTypes == null) {
         this.mDeploymentDataTypes = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("FormulaDeploymentLineId=");
      sb.append(String.valueOf(this.mFormulaDeploymentLineId));
      sb.append(' ');
      sb.append("CubeFormulaId=");
      sb.append(String.valueOf(this.mCubeFormulaId));
      sb.append(' ');
      sb.append("LineIndex=");
      sb.append(String.valueOf(this.mLineIndex));
      sb.append(' ');
      sb.append("Context=");
      sb.append(String.valueOf(this.mContext));
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

      sb.append("FormulaDeploymentLine: ");
      sb.append(this.toString());
      if(this.mDeploymentEntriesAllItemsLoaded || this.mDeploymentEntries != null) {
         sb.delete(indent, sb.length());
         sb.append(" - DeploymentEntries: allItemsLoaded=");
         sb.append(String.valueOf(this.mDeploymentEntriesAllItemsLoaded));
         sb.append(" items=");
         if(this.mDeploymentEntries == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mDeploymentEntries.size()));
         }
      }

      if(this.mDeploymentDataTypesAllItemsLoaded || this.mDeploymentDataTypes != null) {
         sb.delete(indent, sb.length());
         sb.append(" - DeploymentDataTypes: allItemsLoaded=");
         sb.append(String.valueOf(this.mDeploymentDataTypesAllItemsLoaded));
         sb.append(" items=");
         if(this.mDeploymentDataTypes == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mDeploymentDataTypes.size()));
         }
      }

      Iterator var5;
      if(this.mDeploymentEntries != null) {
         var5 = this.mDeploymentEntries.values().iterator();

         while(var5.hasNext()) {
            FormulaDeploymentEntryEVO listItem = (FormulaDeploymentEntryEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mDeploymentDataTypes != null) {
         var5 = this.mDeploymentDataTypes.values().iterator();

         while(var5.hasNext()) {
            FormulaDeploymentDtEVO var6 = (FormulaDeploymentDtEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(FormulaDeploymentEntryEVO child, int newFormulaDeploymentEntryId) {
      if(this.getDeploymentEntriesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mDeploymentEntries.remove(child.getPK());
         child.setFormulaDeploymentEntryId(newFormulaDeploymentEntryId);
         this.mDeploymentEntries.put(child.getPK(), child);
      }
   }

   public void changeKey(FormulaDeploymentDtEVO child, int newFormulaDeploymentDtId) {
      if(this.getDeploymentDataTypesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mDeploymentDataTypes.remove(child.getPK());
         child.setFormulaDeploymentDtId(newFormulaDeploymentDtId);
         this.mDeploymentDataTypes.put(child.getPK(), child);
      }
   }

   public FormulaDeploymentEntryEVO findFormulaDeploymentEntry(int dimIndex, int structureId, int startSeId, Integer endSeId) {
      if(this.mDeploymentEntries == null) {
         return null;
      } else {
         Iterator i$ = this.mDeploymentEntries.values().iterator();

         FormulaDeploymentEntryEVO entry;
         do {
            do {
               do {
                  do {
                     if(!i$.hasNext()) {
                        return null;
                     }

                     entry = (FormulaDeploymentEntryEVO)i$.next();
                  } while(entry.getDimSeq() != dimIndex);
               } while(entry.getStructureId() != structureId);
            } while(entry.getStartSeId() != startSeId);
         } while((entry.getEndSeId() == null || endSeId == null || !entry.getEndSeId().equals(endSeId)) && (entry.getEndSeId() != null || endSeId != null));

         return entry;
      }
   }

   public void setDeploymentEntriesAllItemsLoaded(boolean allItemsLoaded) {
      this.mDeploymentEntriesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isDeploymentEntriesAllItemsLoaded() {
      return this.mDeploymentEntriesAllItemsLoaded;
   }

   public void setDeploymentDataTypesAllItemsLoaded(boolean allItemsLoaded) {
      this.mDeploymentDataTypesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isDeploymentDataTypesAllItemsLoaded() {
      return this.mDeploymentDataTypesAllItemsLoaded;
   }
}
