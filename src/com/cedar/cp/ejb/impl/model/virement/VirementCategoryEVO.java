// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.api.model.virement.VirementCategoryRef;
import com.cedar.cp.dto.model.virement.VirementAccountPK;
import com.cedar.cp.dto.model.virement.VirementCategoryCK;
import com.cedar.cp.dto.model.virement.VirementCategoryPK;
import com.cedar.cp.dto.model.virement.VirementCategoryRefImpl;
import com.cedar.cp.dto.model.virement.VirementLocationPK;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementAccountEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementLocationEVO;
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

public class VirementCategoryEVO implements Serializable {

   private transient VirementCategoryPK mPK;
   private int mVirementCategoryId;
   private int mModelId;
   private String mVisId;
   private String mDescription;
   private long mTranLimit;
   private long mTotalLimitIn;
   private long mTotalLimitOut;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<VirementLocationPK, VirementLocationEVO> mVirementResponsibilityAreas;
   protected boolean mVirementResponsibilityAreasAllItemsLoaded;
   private Map<VirementAccountPK, VirementAccountEVO> mVirementAccounts;
   protected boolean mVirementAccountsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public VirementCategoryEVO() {}

   public VirementCategoryEVO(int newVirementCategoryId, int newModelId, String newVisId, String newDescription, long newTranLimit, long newTotalLimitIn, long newTotalLimitOut, int newVersionNum, Collection newVirementResponsibilityAreas, Collection newVirementAccounts) {
      this.mVirementCategoryId = newVirementCategoryId;
      this.mModelId = newModelId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mTranLimit = newTranLimit;
      this.mTotalLimitIn = newTotalLimitIn;
      this.mTotalLimitOut = newTotalLimitOut;
      this.mVersionNum = newVersionNum;
      this.setVirementResponsibilityAreas(newVirementResponsibilityAreas);
      this.setVirementAccounts(newVirementAccounts);
   }

   public void setVirementResponsibilityAreas(Collection<VirementLocationEVO> items) {
      if(items != null) {
         if(this.mVirementResponsibilityAreas == null) {
            this.mVirementResponsibilityAreas = new HashMap();
         } else {
            this.mVirementResponsibilityAreas.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            VirementLocationEVO child = (VirementLocationEVO)i$.next();
            this.mVirementResponsibilityAreas.put(child.getPK(), child);
         }
      } else {
         this.mVirementResponsibilityAreas = null;
      }

   }

   public void setVirementAccounts(Collection<VirementAccountEVO> items) {
      if(items != null) {
         if(this.mVirementAccounts == null) {
            this.mVirementAccounts = new HashMap();
         } else {
            this.mVirementAccounts.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            VirementAccountEVO child = (VirementAccountEVO)i$.next();
            this.mVirementAccounts.put(child.getPK(), child);
         }
      } else {
         this.mVirementAccounts = null;
      }

   }

   public VirementCategoryPK getPK() {
      if(this.mPK == null) {
         this.mPK = new VirementCategoryPK(this.mVirementCategoryId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getVirementCategoryId() {
      return this.mVirementCategoryId;
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

   public long getTranLimit() {
      return this.mTranLimit;
   }

   public long getTotalLimitIn() {
      return this.mTotalLimitIn;
   }

   public long getTotalLimitOut() {
      return this.mTotalLimitOut;
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

   public void setVirementCategoryId(int newVirementCategoryId) {
      if(this.mVirementCategoryId != newVirementCategoryId) {
         this.mModified = true;
         this.mVirementCategoryId = newVirementCategoryId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setTranLimit(long newTranLimit) {
      if(this.mTranLimit != newTranLimit) {
         this.mModified = true;
         this.mTranLimit = newTranLimit;
      }
   }

   public void setTotalLimitIn(long newTotalLimitIn) {
      if(this.mTotalLimitIn != newTotalLimitIn) {
         this.mModified = true;
         this.mTotalLimitIn = newTotalLimitIn;
      }
   }

   public void setTotalLimitOut(long newTotalLimitOut) {
      if(this.mTotalLimitOut != newTotalLimitOut) {
         this.mModified = true;
         this.mTotalLimitOut = newTotalLimitOut;
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

   public void setDetails(VirementCategoryEVO newDetails) {
      this.setVirementCategoryId(newDetails.getVirementCategoryId());
      this.setModelId(newDetails.getModelId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setTranLimit(newDetails.getTranLimit());
      this.setTotalLimitIn(newDetails.getTotalLimitIn());
      this.setTotalLimitOut(newDetails.getTotalLimitOut());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public VirementCategoryEVO deepClone() {
      VirementCategoryEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (VirementCategoryEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mVirementCategoryId > 0) {
         newKey = true;
         if(parent == null) {
            this.setVirementCategoryId(-this.mVirementCategoryId);
         } else {
            parent.changeKey(this, -this.mVirementCategoryId);
         }
      } else if(this.mVirementCategoryId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      Iterator iter;
      VirementLocationEVO item;
      if(this.mVirementResponsibilityAreas != null) {
         for(iter = (new ArrayList(this.mVirementResponsibilityAreas.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (VirementLocationEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      VirementAccountEVO item1;
      if(this.mVirementAccounts != null) {
         for(iter = (new ArrayList(this.mVirementAccounts.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (VirementAccountEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mVirementCategoryId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      VirementLocationEVO item;
      if(this.mVirementResponsibilityAreas != null) {
         for(iter = this.mVirementResponsibilityAreas.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (VirementLocationEVO)iter.next();
         }
      }

      VirementAccountEVO item1;
      if(this.mVirementAccounts != null) {
         for(iter = this.mVirementAccounts.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (VirementAccountEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mVirementCategoryId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      Iterator iter;
      VirementLocationEVO item;
      if(this.mVirementResponsibilityAreas != null) {
         for(iter = (new ArrayList(this.mVirementResponsibilityAreas.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (VirementLocationEVO)iter.next();
            this.changeKey(item, this.mVirementCategoryId, item.getStructureId(), item.getStructureElementId());
         }
      }

      VirementAccountEVO item1;
      if(this.mVirementAccounts != null) {
         for(iter = (new ArrayList(this.mVirementAccounts.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (VirementAccountEVO)iter.next();
            this.changeKey(item1, this.mVirementCategoryId, item1.getStructureId(), item1.getStructureElementId());
         }
      }

      return nextKey;
   }

   public Collection<VirementLocationEVO> getVirementResponsibilityAreas() {
      return this.mVirementResponsibilityAreas != null?this.mVirementResponsibilityAreas.values():null;
   }

   public Map<VirementLocationPK, VirementLocationEVO> getVirementResponsibilityAreasMap() {
      return this.mVirementResponsibilityAreas;
   }

   public void loadVirementResponsibilityAreasItem(VirementLocationEVO newItem) {
      if(this.mVirementResponsibilityAreas == null) {
         this.mVirementResponsibilityAreas = new HashMap();
      }

      this.mVirementResponsibilityAreas.put(newItem.getPK(), newItem);
   }

   public void addVirementResponsibilityAreasItem(VirementLocationEVO newItem) {
      if(this.mVirementResponsibilityAreas == null) {
         this.mVirementResponsibilityAreas = new HashMap();
      }

      VirementLocationPK newPK = newItem.getPK();
      if(this.getVirementResponsibilityAreasItem(newPK) != null) {
         throw new RuntimeException("addVirementResponsibilityAreasItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mVirementResponsibilityAreas.put(newPK, newItem);
      }
   }

   public void changeVirementResponsibilityAreasItem(VirementLocationEVO changedItem) {
      if(this.mVirementResponsibilityAreas == null) {
         throw new RuntimeException("changeVirementResponsibilityAreasItem: no items in collection");
      } else {
         VirementLocationPK changedPK = changedItem.getPK();
         VirementLocationEVO listItem = this.getVirementResponsibilityAreasItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeVirementResponsibilityAreasItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteVirementResponsibilityAreasItem(VirementLocationPK removePK) {
      VirementLocationEVO listItem = this.getVirementResponsibilityAreasItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeVirementResponsibilityAreasItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public VirementLocationEVO getVirementResponsibilityAreasItem(VirementLocationPK pk) {
      return (VirementLocationEVO)this.mVirementResponsibilityAreas.get(pk);
   }

   public VirementLocationEVO getVirementResponsibilityAreasItem() {
      if(this.mVirementResponsibilityAreas.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mVirementResponsibilityAreas.size());
      } else {
         Iterator iter = this.mVirementResponsibilityAreas.values().iterator();
         return (VirementLocationEVO)iter.next();
      }
   }

   public Collection<VirementAccountEVO> getVirementAccounts() {
      return this.mVirementAccounts != null?this.mVirementAccounts.values():null;
   }

   public Map<VirementAccountPK, VirementAccountEVO> getVirementAccountsMap() {
      return this.mVirementAccounts;
   }

   public void loadVirementAccountsItem(VirementAccountEVO newItem) {
      if(this.mVirementAccounts == null) {
         this.mVirementAccounts = new HashMap();
      }

      this.mVirementAccounts.put(newItem.getPK(), newItem);
   }

   public void addVirementAccountsItem(VirementAccountEVO newItem) {
      if(this.mVirementAccounts == null) {
         this.mVirementAccounts = new HashMap();
      }

      VirementAccountPK newPK = newItem.getPK();
      if(this.getVirementAccountsItem(newPK) != null) {
         throw new RuntimeException("addVirementAccountsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mVirementAccounts.put(newPK, newItem);
      }
   }

   public void changeVirementAccountsItem(VirementAccountEVO changedItem) {
      if(this.mVirementAccounts == null) {
         throw new RuntimeException("changeVirementAccountsItem: no items in collection");
      } else {
         VirementAccountPK changedPK = changedItem.getPK();
         VirementAccountEVO listItem = this.getVirementAccountsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeVirementAccountsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteVirementAccountsItem(VirementAccountPK removePK) {
      VirementAccountEVO listItem = this.getVirementAccountsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeVirementAccountsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public VirementAccountEVO getVirementAccountsItem(VirementAccountPK pk) {
      return (VirementAccountEVO)this.mVirementAccounts.get(pk);
   }

   public VirementAccountEVO getVirementAccountsItem() {
      if(this.mVirementAccounts.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mVirementAccounts.size());
      } else {
         Iterator iter = this.mVirementAccounts.values().iterator();
         return (VirementAccountEVO)iter.next();
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

   public VirementCategoryRef getEntityRef(ModelEVO evoModel) {
      return new VirementCategoryRefImpl(new VirementCategoryCK(evoModel.getPK(), this.getPK()), this.mVisId);
   }

   public VirementCategoryCK getCK(ModelEVO evoModel) {
      return new VirementCategoryCK(evoModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mVirementResponsibilityAreasAllItemsLoaded = true;
      if(this.mVirementResponsibilityAreas == null) {
         this.mVirementResponsibilityAreas = new HashMap();
      }

      this.mVirementAccountsAllItemsLoaded = true;
      if(this.mVirementAccounts == null) {
         this.mVirementAccounts = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("VirementCategoryId=");
      sb.append(String.valueOf(this.mVirementCategoryId));
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
      sb.append("TranLimit=");
      sb.append(String.valueOf(this.mTranLimit));
      sb.append(' ');
      sb.append("TotalLimitIn=");
      sb.append(String.valueOf(this.mTotalLimitIn));
      sb.append(' ');
      sb.append("TotalLimitOut=");
      sb.append(String.valueOf(this.mTotalLimitOut));
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

      sb.append("VirementCategory: ");
      sb.append(this.toString());
      if(this.mVirementResponsibilityAreasAllItemsLoaded || this.mVirementResponsibilityAreas != null) {
         sb.delete(indent, sb.length());
         sb.append(" - VirementResponsibilityAreas: allItemsLoaded=");
         sb.append(String.valueOf(this.mVirementResponsibilityAreasAllItemsLoaded));
         sb.append(" items=");
         if(this.mVirementResponsibilityAreas == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mVirementResponsibilityAreas.size()));
         }
      }

      if(this.mVirementAccountsAllItemsLoaded || this.mVirementAccounts != null) {
         sb.delete(indent, sb.length());
         sb.append(" - VirementAccounts: allItemsLoaded=");
         sb.append(String.valueOf(this.mVirementAccountsAllItemsLoaded));
         sb.append(" items=");
         if(this.mVirementAccounts == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mVirementAccounts.size()));
         }
      }

      Iterator var5;
      if(this.mVirementResponsibilityAreas != null) {
         var5 = this.mVirementResponsibilityAreas.values().iterator();

         while(var5.hasNext()) {
            VirementLocationEVO listItem = (VirementLocationEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mVirementAccounts != null) {
         var5 = this.mVirementAccounts.values().iterator();

         while(var5.hasNext()) {
            VirementAccountEVO var6 = (VirementAccountEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(VirementLocationEVO child, int newVirementCategoryId, int newStructureId, int newStructureElementId) {
      if(this.getVirementResponsibilityAreasItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mVirementResponsibilityAreas.remove(child.getPK());
         child.setVirementCategoryId(newVirementCategoryId);
         child.setStructureId(newStructureId);
         child.setStructureElementId(newStructureElementId);
         this.mVirementResponsibilityAreas.put(child.getPK(), child);
      }
   }

   public void changeKey(VirementAccountEVO child, int newVirementCategoryId, int newStructureId, int newStructureElementId) {
      if(this.getVirementAccountsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mVirementAccounts.remove(child.getPK());
         child.setVirementCategoryId(newVirementCategoryId);
         child.setStructureId(newStructureId);
         child.setStructureElementId(newStructureElementId);
         this.mVirementAccounts.put(child.getPK(), child);
      }
   }

   public void setVirementResponsibilityAreasAllItemsLoaded(boolean allItemsLoaded) {
      this.mVirementResponsibilityAreasAllItemsLoaded = allItemsLoaded;
   }

   public boolean isVirementResponsibilityAreasAllItemsLoaded() {
      return this.mVirementResponsibilityAreasAllItemsLoaded;
   }

   public void setVirementAccountsAllItemsLoaded(boolean allItemsLoaded) {
      this.mVirementAccountsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isVirementAccountsAllItemsLoaded() {
      return this.mVirementAccountsAllItemsLoaded;
   }
}
