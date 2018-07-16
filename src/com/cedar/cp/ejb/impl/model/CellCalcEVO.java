// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.CellCalcRef;
import com.cedar.cp.dto.model.CellCalcAssocPK;
import com.cedar.cp.dto.model.CellCalcCK;
import com.cedar.cp.dto.model.CellCalcPK;
import com.cedar.cp.dto.model.CellCalcRefImpl;
import com.cedar.cp.ejb.impl.model.CellCalcAssocEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
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

public class CellCalcEVO implements Serializable {

   private transient CellCalcPK mPK;
   private int mCellCalcId;
   private int mModelId;
   private String mVisId;
   private String mDescription;
   private int mXmlformId;
   private int mAccessDefinitionId;
   private int mDataTypeId;
   private boolean mSummaryPeriodAssociation;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<CellCalcAssocPK, CellCalcAssocEVO> mCellCalculationAccounts;
   protected boolean mCellCalculationAccountsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public CellCalcEVO() {}

   public CellCalcEVO(int newCellCalcId, int newModelId, String newVisId, String newDescription, int newXmlformId, int newAccessDefinitionId, int newDataTypeId, boolean newSummaryPeriodAssociation, int newVersionNum, Collection newCellCalculationAccounts) {
      this.mCellCalcId = newCellCalcId;
      this.mModelId = newModelId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mXmlformId = newXmlformId;
      this.mAccessDefinitionId = newAccessDefinitionId;
      this.mDataTypeId = newDataTypeId;
      this.mSummaryPeriodAssociation = newSummaryPeriodAssociation;
      this.mVersionNum = newVersionNum;
      this.setCellCalculationAccounts(newCellCalculationAccounts);
   }

   public void setCellCalculationAccounts(Collection<CellCalcAssocEVO> items) {
      if(items != null) {
         if(this.mCellCalculationAccounts == null) {
            this.mCellCalculationAccounts = new HashMap();
         } else {
            this.mCellCalculationAccounts.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CellCalcAssocEVO child = (CellCalcAssocEVO)i$.next();
            this.mCellCalculationAccounts.put(child.getPK(), child);
         }
      } else {
         this.mCellCalculationAccounts = null;
      }

   }

   public CellCalcPK getPK() {
      if(this.mPK == null) {
         this.mPK = new CellCalcPK(this.mCellCalcId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getCellCalcId() {
      return this.mCellCalcId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getXmlformId() {
      return this.mXmlformId;
   }

   public int getAccessDefinitionId() {
      return this.mAccessDefinitionId;
   }

   public int getDataTypeId() {
      return this.mDataTypeId;
   }

   public boolean getSummaryPeriodAssociation() {
      return this.mSummaryPeriodAssociation;
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

   public void setCellCalcId(int newCellCalcId) {
      if(this.mCellCalcId != newCellCalcId) {
         this.mModified = true;
         this.mCellCalcId = newCellCalcId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setXmlformId(int newXmlformId) {
      if(this.mXmlformId != newXmlformId) {
         this.mModified = true;
         this.mXmlformId = newXmlformId;
      }
   }

   public void setAccessDefinitionId(int newAccessDefinitionId) {
      if(this.mAccessDefinitionId != newAccessDefinitionId) {
         this.mModified = true;
         this.mAccessDefinitionId = newAccessDefinitionId;
      }
   }

   public void setDataTypeId(int newDataTypeId) {
      if(this.mDataTypeId != newDataTypeId) {
         this.mModified = true;
         this.mDataTypeId = newDataTypeId;
      }
   }

   public void setSummaryPeriodAssociation(boolean newSummaryPeriodAssociation) {
      if(this.mSummaryPeriodAssociation != newSummaryPeriodAssociation) {
         this.mModified = true;
         this.mSummaryPeriodAssociation = newSummaryPeriodAssociation;
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

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(CellCalcEVO newDetails) {
      this.setCellCalcId(newDetails.getCellCalcId());
      this.setModelId(newDetails.getModelId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setXmlformId(newDetails.getXmlformId());
      this.setAccessDefinitionId(newDetails.getAccessDefinitionId());
      this.setDataTypeId(newDetails.getDataTypeId());
      this.setSummaryPeriodAssociation(newDetails.getSummaryPeriodAssociation());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public CellCalcEVO deepClone() {
      CellCalcEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (CellCalcEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mCellCalcId > 0) {
         newKey = true;
         if(parent == null) {
            this.setCellCalcId(-this.mCellCalcId);
         } else {
            parent.changeKey(this, -this.mCellCalcId);
         }
      } else if(this.mCellCalcId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      CellCalcAssocEVO item;
      if(this.mCellCalculationAccounts != null) {
         for(Iterator iter = (new ArrayList(this.mCellCalculationAccounts.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (CellCalcAssocEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mCellCalcId < 1) {
         returnCount = startCount + 1;
      }

      CellCalcAssocEVO item;
      if(this.mCellCalculationAccounts != null) {
         for(Iterator iter = this.mCellCalculationAccounts.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (CellCalcAssocEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mCellCalcId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      CellCalcAssocEVO item;
      if(this.mCellCalculationAccounts != null) {
         for(Iterator iter = (new ArrayList(this.mCellCalculationAccounts.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (CellCalcAssocEVO)iter.next();
            item.setCellCalcId(this.mCellCalcId);
         }
      }

      return nextKey;
   }

   public Collection<CellCalcAssocEVO> getCellCalculationAccounts() {
      return this.mCellCalculationAccounts != null?this.mCellCalculationAccounts.values():null;
   }

   public Map<CellCalcAssocPK, CellCalcAssocEVO> getCellCalculationAccountsMap() {
      return this.mCellCalculationAccounts;
   }

   public void loadCellCalculationAccountsItem(CellCalcAssocEVO newItem) {
      if(this.mCellCalculationAccounts == null) {
         this.mCellCalculationAccounts = new HashMap();
      }

      this.mCellCalculationAccounts.put(newItem.getPK(), newItem);
   }

   public void addCellCalculationAccountsItem(CellCalcAssocEVO newItem) {
      if(this.mCellCalculationAccounts == null) {
         this.mCellCalculationAccounts = new HashMap();
      }

      CellCalcAssocPK newPK = newItem.getPK();
      if(this.getCellCalculationAccountsItem(newPK) != null) {
         throw new RuntimeException("addCellCalculationAccountsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mCellCalculationAccounts.put(newPK, newItem);
      }
   }

   public void changeCellCalculationAccountsItem(CellCalcAssocEVO changedItem) {
      if(this.mCellCalculationAccounts == null) {
         throw new RuntimeException("changeCellCalculationAccountsItem: no items in collection");
      } else {
         CellCalcAssocPK changedPK = changedItem.getPK();
         CellCalcAssocEVO listItem = this.getCellCalculationAccountsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeCellCalculationAccountsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteCellCalculationAccountsItem(CellCalcAssocPK removePK) {
      CellCalcAssocEVO listItem = this.getCellCalculationAccountsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeCellCalculationAccountsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public CellCalcAssocEVO getCellCalculationAccountsItem(CellCalcAssocPK pk) {
      return (CellCalcAssocEVO)this.mCellCalculationAccounts.get(pk);
   }

   public CellCalcAssocEVO getCellCalculationAccountsItem() {
      if(this.mCellCalculationAccounts.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mCellCalculationAccounts.size());
      } else {
         Iterator iter = this.mCellCalculationAccounts.values().iterator();
         return (CellCalcAssocEVO)iter.next();
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

   public CellCalcRef getEntityRef(ModelEVO evoModel) {
      return new CellCalcRefImpl(new CellCalcCK(evoModel.getPK(), this.getPK()), this.mVisId);
   }

   public CellCalcCK getCK(ModelEVO evoModel) {
      return new CellCalcCK(evoModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mCellCalculationAccountsAllItemsLoaded = true;
      if(this.mCellCalculationAccounts == null) {
         this.mCellCalculationAccounts = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("CellCalcId=");
      sb.append(String.valueOf(this.mCellCalcId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("XmlformId=");
      sb.append(String.valueOf(this.mXmlformId));
      sb.append(' ');
      sb.append("AccessDefinitionId=");
      sb.append(String.valueOf(this.mAccessDefinitionId));
      sb.append(' ');
      sb.append("DataTypeId=");
      sb.append(String.valueOf(this.mDataTypeId));
      sb.append(' ');
      sb.append("SummaryPeriodAssociation=");
      sb.append(String.valueOf(this.mSummaryPeriodAssociation));
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

      sb.append("CellCalc: ");
      sb.append(this.toString());
      if(this.mCellCalculationAccountsAllItemsLoaded || this.mCellCalculationAccounts != null) {
         sb.delete(indent, sb.length());
         sb.append(" - CellCalculationAccounts: allItemsLoaded=");
         sb.append(String.valueOf(this.mCellCalculationAccountsAllItemsLoaded));
         sb.append(" items=");
         if(this.mCellCalculationAccounts == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mCellCalculationAccounts.size()));
         }
      }

      if(this.mCellCalculationAccounts != null) {
         Iterator var5 = this.mCellCalculationAccounts.values().iterator();

         while(var5.hasNext()) {
            CellCalcAssocEVO listItem = (CellCalcAssocEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(CellCalcAssocEVO child, int newCellCalcAssocId) {
      if(this.getCellCalculationAccountsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mCellCalculationAccounts.remove(child.getPK());
         child.setCellCalcAssocId(newCellCalcAssocId);
         this.mCellCalculationAccounts.put(child.getPK(), child);
      }
   }

   public void setCellCalculationAccountsAllItemsLoaded(boolean allItemsLoaded) {
      this.mCellCalculationAccountsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isCellCalculationAccountsAllItemsLoaded() {
      return this.mCellCalculationAccountsAllItemsLoaded;
   }
}
