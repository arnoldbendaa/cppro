// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.act;

import com.cedar.cp.api.model.act.BudgetActivityRef;
import com.cedar.cp.dto.model.act.BudgetActivityCK;
import com.cedar.cp.dto.model.act.BudgetActivityLinkPK;
import com.cedar.cp.dto.model.act.BudgetActivityPK;
import com.cedar.cp.dto.model.act.BudgetActivityRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityLinkEVO;
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

public class BudgetActivityEVO implements Serializable {

   private transient BudgetActivityPK mPK;
   private int mBudgetActivityId;
   private int mModelId;
   private String mUserId;
   private Timestamp mCreated;
   private int mActivityType;
   private String mDescription;
   private String mDetails;
   private Boolean mUndoEntry;
   private int mOwnerId;
   private Map<BudgetActivityLinkPK, BudgetActivityLinkEVO> mActivityLinks;
   protected boolean mActivityLinksAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int MANUAL_DATA_ENTRY = 0;
   public static final int MASS_UPDATE = 1;
   public static final int ALLOCATION = 2;
   public static final int VIREMENT = 3;
   public static final int CELL_CALCULATION_REBUILD = 4;
   public static final int STATUS_CHANGE = 5;
   public static final int EXCEL_IMPORT = 6;


   public BudgetActivityEVO() {}

   public BudgetActivityEVO(int newBudgetActivityId, int newModelId, String newUserId, Timestamp newCreated, int newActivityType, String newDescription, String newDetails, Boolean newUndoEntry, int newOwnerId, Collection newActivityLinks) {
      this.mBudgetActivityId = newBudgetActivityId;
      this.mModelId = newModelId;
      this.mUserId = newUserId;
      this.mCreated = newCreated;
      this.mActivityType = newActivityType;
      this.mDescription = newDescription;
      this.mDetails = newDetails;
      this.mUndoEntry = newUndoEntry;
      this.mOwnerId = newOwnerId;
      this.setActivityLinks(newActivityLinks);
   }

   public void setActivityLinks(Collection<BudgetActivityLinkEVO> items) {
      if(items != null) {
         if(this.mActivityLinks == null) {
            this.mActivityLinks = new HashMap();
         } else {
            this.mActivityLinks.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            BudgetActivityLinkEVO child = (BudgetActivityLinkEVO)i$.next();
            this.mActivityLinks.put(child.getPK(), child);
         }
      } else {
         this.mActivityLinks = null;
      }

   }

   public BudgetActivityPK getPK() {
      if(this.mPK == null) {
         this.mPK = new BudgetActivityPK(this.mBudgetActivityId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getBudgetActivityId() {
      return this.mBudgetActivityId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public String getUserId() {
      return this.mUserId;
   }

   public Timestamp getCreated() {
      return this.mCreated;
   }

   public int getActivityType() {
      return this.mActivityType;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getDetails() {
      return this.mDetails;
   }

   public Boolean getUndoEntry() {
      return this.mUndoEntry;
   }

   public int getOwnerId() {
      return this.mOwnerId;
   }

   public void setBudgetActivityId(int newBudgetActivityId) {
      if(this.mBudgetActivityId != newBudgetActivityId) {
         this.mModified = true;
         this.mBudgetActivityId = newBudgetActivityId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setActivityType(int newActivityType) {
      if(this.mActivityType != newActivityType) {
         this.mModified = true;
         this.mActivityType = newActivityType;
      }
   }

   public void setOwnerId(int newOwnerId) {
      if(this.mOwnerId != newOwnerId) {
         this.mModified = true;
         this.mOwnerId = newOwnerId;
      }
   }

   public void setUserId(String newUserId) {
      if(this.mUserId != null && newUserId == null || this.mUserId == null && newUserId != null || this.mUserId != null && newUserId != null && !this.mUserId.equals(newUserId)) {
         this.mUserId = newUserId;
         this.mModified = true;
      }

   }

   public void setCreated(Timestamp newCreated) {
      if(this.mCreated != null && newCreated == null || this.mCreated == null && newCreated != null || this.mCreated != null && newCreated != null && !this.mCreated.equals(newCreated)) {
         this.mCreated = newCreated;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setDetails(String newDetails) {
      if(this.mDetails != null && newDetails == null || this.mDetails == null && newDetails != null || this.mDetails != null && newDetails != null && !this.mDetails.equals(newDetails)) {
         this.mDetails = newDetails;
         this.mModified = true;
      }

   }

   public void setUndoEntry(Boolean newUndoEntry) {
      if(this.mUndoEntry != null && newUndoEntry == null || this.mUndoEntry == null && newUndoEntry != null || this.mUndoEntry != null && newUndoEntry != null && !this.mUndoEntry.equals(newUndoEntry)) {
         this.mUndoEntry = newUndoEntry;
         this.mModified = true;
      }

   }

   public void setDetails(BudgetActivityEVO newDetails) {
      this.setBudgetActivityId(newDetails.getBudgetActivityId());
      this.setModelId(newDetails.getModelId());
      this.setUserId(newDetails.getUserId());
      this.setCreated(newDetails.getCreated());
      this.setActivityType(newDetails.getActivityType());
      this.setDescription(newDetails.getDescription());
      this.setDetails(newDetails.getDetails());
      this.setUndoEntry(newDetails.getUndoEntry());
      this.setOwnerId(newDetails.getOwnerId());
   }

   public BudgetActivityEVO deepClone() {
      BudgetActivityEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (BudgetActivityEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mBudgetActivityId > 0) {
         newKey = true;
         if(parent == null) {
            this.setBudgetActivityId(-this.mBudgetActivityId);
         } else {
            parent.changeKey(this, -this.mBudgetActivityId);
         }
      } else if(this.mBudgetActivityId < 1) {
         newKey = true;
      }

      BudgetActivityLinkEVO item;
      if(this.mActivityLinks != null) {
         for(Iterator iter = (new ArrayList(this.mActivityLinks.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (BudgetActivityLinkEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mBudgetActivityId < 1) {
         returnCount = startCount + 1;
      }

      BudgetActivityLinkEVO item;
      if(this.mActivityLinks != null) {
         for(Iterator iter = this.mActivityLinks.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (BudgetActivityLinkEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mBudgetActivityId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      BudgetActivityLinkEVO item;
      if(this.mActivityLinks != null) {
         for(Iterator iter = (new ArrayList(this.mActivityLinks.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (BudgetActivityLinkEVO)iter.next();
            this.changeKey(item, this.mBudgetActivityId, item.getStructureElementId());
         }
      }

      return nextKey;
   }

   public Collection<BudgetActivityLinkEVO> getActivityLinks() {
      return this.mActivityLinks != null?this.mActivityLinks.values():null;
   }

   public Map<BudgetActivityLinkPK, BudgetActivityLinkEVO> getActivityLinksMap() {
      return this.mActivityLinks;
   }

   public void loadActivityLinksItem(BudgetActivityLinkEVO newItem) {
      if(this.mActivityLinks == null) {
         this.mActivityLinks = new HashMap();
      }

      this.mActivityLinks.put(newItem.getPK(), newItem);
   }

   public void addActivityLinksItem(BudgetActivityLinkEVO newItem) {
      if(this.mActivityLinks == null) {
         this.mActivityLinks = new HashMap();
      }

      BudgetActivityLinkPK newPK = newItem.getPK();
      if(this.getActivityLinksItem(newPK) != null) {
         throw new RuntimeException("addActivityLinksItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mActivityLinks.put(newPK, newItem);
      }
   }

   public void changeActivityLinksItem(BudgetActivityLinkEVO changedItem) {
      if(this.mActivityLinks == null) {
         throw new RuntimeException("changeActivityLinksItem: no items in collection");
      } else {
         BudgetActivityLinkPK changedPK = changedItem.getPK();
         BudgetActivityLinkEVO listItem = this.getActivityLinksItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeActivityLinksItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteActivityLinksItem(BudgetActivityLinkPK removePK) {
      BudgetActivityLinkEVO listItem = this.getActivityLinksItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeActivityLinksItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public BudgetActivityLinkEVO getActivityLinksItem(BudgetActivityLinkPK pk) {
      return (BudgetActivityLinkEVO)this.mActivityLinks.get(pk);
   }

   public BudgetActivityLinkEVO getActivityLinksItem() {
      if(this.mActivityLinks.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mActivityLinks.size());
      } else {
         Iterator iter = this.mActivityLinks.values().iterator();
         return (BudgetActivityLinkEVO)iter.next();
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

   public BudgetActivityRef getEntityRef(ModelEVO evoModel, String entityText) {
      return new BudgetActivityRefImpl(new BudgetActivityCK(evoModel.getPK(), this.getPK()), entityText);
   }

   public BudgetActivityCK getCK(ModelEVO evoModel) {
      return new BudgetActivityCK(evoModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mActivityLinksAllItemsLoaded = true;
      if(this.mActivityLinks == null) {
         this.mActivityLinks = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("BudgetActivityId=");
      sb.append(String.valueOf(this.mBudgetActivityId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      sb.append("Created=");
      sb.append(String.valueOf(this.mCreated));
      sb.append(' ');
      sb.append("ActivityType=");
      sb.append(String.valueOf(this.mActivityType));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("Details=");
      sb.append(String.valueOf(this.mDetails));
      sb.append(' ');
      sb.append("UndoEntry=");
      sb.append(String.valueOf(this.mUndoEntry));
      sb.append(' ');
      sb.append("OwnerId=");
      sb.append(String.valueOf(this.mOwnerId));
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

      sb.append("BudgetActivity: ");
      sb.append(this.toString());
      if(this.mActivityLinksAllItemsLoaded || this.mActivityLinks != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ActivityLinks: allItemsLoaded=");
         sb.append(String.valueOf(this.mActivityLinksAllItemsLoaded));
         sb.append(" items=");
         if(this.mActivityLinks == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mActivityLinks.size()));
         }
      }

      if(this.mActivityLinks != null) {
         Iterator var5 = this.mActivityLinks.values().iterator();

         while(var5.hasNext()) {
            BudgetActivityLinkEVO listItem = (BudgetActivityLinkEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(BudgetActivityLinkEVO child, int newBudgetActivityId, int newStructureElementId) {
      if(this.getActivityLinksItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mActivityLinks.remove(child.getPK());
         child.setBudgetActivityId(newBudgetActivityId);
         child.setStructureElementId(newStructureElementId);
         this.mActivityLinks.put(child.getPK(), child);
      }
   }

   public void setActivityLinksAllItemsLoaded(boolean allItemsLoaded) {
      this.mActivityLinksAllItemsLoaded = allItemsLoaded;
   }

   public boolean isActivityLinksAllItemsLoaded() {
      return this.mActivityLinksAllItemsLoaded;
   }
}
