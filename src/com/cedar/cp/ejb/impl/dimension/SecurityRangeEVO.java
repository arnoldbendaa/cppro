// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.dimension.SecurityRangeRef;
import com.cedar.cp.dto.dimension.SecurityRangeCK;
import com.cedar.cp.dto.dimension.SecurityRangePK;
import com.cedar.cp.dto.dimension.SecurityRangeRefImpl;
import com.cedar.cp.dto.dimension.SecurityRangeRowPK;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.SecurityRangeRowEVO;
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

public class SecurityRangeEVO implements Serializable {

   private transient SecurityRangePK mPK;
   private int mSecurityRangeId;
   private int mDimensionId;
   private String mVisId;
   private String mDescription;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<SecurityRangeRowPK, SecurityRangeRowEVO> mRangeRows;
   protected boolean mRangeRowsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public SecurityRangeEVO() {}

   public SecurityRangeEVO(int newSecurityRangeId, int newDimensionId, String newVisId, String newDescription, int newVersionNum, Collection newRangeRows) {
      this.mSecurityRangeId = newSecurityRangeId;
      this.mDimensionId = newDimensionId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mVersionNum = newVersionNum;
      this.setRangeRows(newRangeRows);
   }

   public void setRangeRows(Collection<SecurityRangeRowEVO> items) {
      if(items != null) {
         if(this.mRangeRows == null) {
            this.mRangeRows = new HashMap();
         } else {
            this.mRangeRows.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            SecurityRangeRowEVO child = (SecurityRangeRowEVO)i$.next();
            this.mRangeRows.put(child.getPK(), child);
         }
      } else {
         this.mRangeRows = null;
      }

   }

   public SecurityRangePK getPK() {
      if(this.mPK == null) {
         this.mPK = new SecurityRangePK(this.mSecurityRangeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getSecurityRangeId() {
      return this.mSecurityRangeId;
   }

   public int getDimensionId() {
      return this.mDimensionId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
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

   public void setSecurityRangeId(int newSecurityRangeId) {
      if(this.mSecurityRangeId != newSecurityRangeId) {
         this.mModified = true;
         this.mSecurityRangeId = newSecurityRangeId;
         this.mPK = null;
      }
   }

   public void setDimensionId(int newDimensionId) {
      if(this.mDimensionId != newDimensionId) {
         this.mModified = true;
         this.mDimensionId = newDimensionId;
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

   public void setDetails(SecurityRangeEVO newDetails) {
      this.setSecurityRangeId(newDetails.getSecurityRangeId());
      this.setDimensionId(newDetails.getDimensionId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public SecurityRangeEVO deepClone() {
      SecurityRangeEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (SecurityRangeEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(DimensionEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mSecurityRangeId > 0) {
         newKey = true;
         if(parent == null) {
            this.setSecurityRangeId(-this.mSecurityRangeId);
         } else {
            parent.changeKey(this, -this.mSecurityRangeId);
         }
      } else if(this.mSecurityRangeId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      SecurityRangeRowEVO item;
      if(this.mRangeRows != null) {
         for(Iterator iter = (new ArrayList(this.mRangeRows.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (SecurityRangeRowEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mSecurityRangeId < 1) {
         returnCount = startCount + 1;
      }

      SecurityRangeRowEVO item;
      if(this.mRangeRows != null) {
         for(Iterator iter = this.mRangeRows.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (SecurityRangeRowEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(DimensionEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mSecurityRangeId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      SecurityRangeRowEVO item;
      if(this.mRangeRows != null) {
         for(Iterator iter = (new ArrayList(this.mRangeRows.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (SecurityRangeRowEVO)iter.next();
            item.setSecurityRangeId(this.mSecurityRangeId);
         }
      }

      return nextKey;
   }

   public Collection<SecurityRangeRowEVO> getRangeRows() {
      return this.mRangeRows != null?this.mRangeRows.values():null;
   }

   public Map<SecurityRangeRowPK, SecurityRangeRowEVO> getRangeRowsMap() {
      return this.mRangeRows;
   }

   public void loadRangeRowsItem(SecurityRangeRowEVO newItem) {
      if(this.mRangeRows == null) {
         this.mRangeRows = new HashMap();
      }

      this.mRangeRows.put(newItem.getPK(), newItem);
   }

   public void addRangeRowsItem(SecurityRangeRowEVO newItem) {
      if(this.mRangeRows == null) {
         this.mRangeRows = new HashMap();
      }

      SecurityRangeRowPK newPK = newItem.getPK();
      if(this.getRangeRowsItem(newPK) != null) {
         throw new RuntimeException("addRangeRowsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mRangeRows.put(newPK, newItem);
      }
   }

   public void changeRangeRowsItem(SecurityRangeRowEVO changedItem) {
      if(this.mRangeRows == null) {
         throw new RuntimeException("changeRangeRowsItem: no items in collection");
      } else {
         SecurityRangeRowPK changedPK = changedItem.getPK();
         SecurityRangeRowEVO listItem = this.getRangeRowsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeRangeRowsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteRangeRowsItem(SecurityRangeRowPK removePK) {
      SecurityRangeRowEVO listItem = this.getRangeRowsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeRangeRowsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public SecurityRangeRowEVO getRangeRowsItem(SecurityRangeRowPK pk) {
      return (SecurityRangeRowEVO)this.mRangeRows.get(pk);
   }

   public SecurityRangeRowEVO getRangeRowsItem() {
      if(this.mRangeRows.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mRangeRows.size());
      } else {
         Iterator iter = this.mRangeRows.values().iterator();
         return (SecurityRangeRowEVO)iter.next();
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

   public SecurityRangeRef getEntityRef(DimensionEVO evoDimension) {
      return new SecurityRangeRefImpl(new SecurityRangeCK(evoDimension.getPK(), this.getPK()), this.mVisId);
   }

   public SecurityRangeCK getCK(DimensionEVO evoDimension) {
      return new SecurityRangeCK(evoDimension.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mRangeRowsAllItemsLoaded = true;
      if(this.mRangeRows == null) {
         this.mRangeRows = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("SecurityRangeId=");
      sb.append(String.valueOf(this.mSecurityRangeId));
      sb.append(' ');
      sb.append("DimensionId=");
      sb.append(String.valueOf(this.mDimensionId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
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

      sb.append("SecurityRange: ");
      sb.append(this.toString());
      if(this.mRangeRowsAllItemsLoaded || this.mRangeRows != null) {
         sb.delete(indent, sb.length());
         sb.append(" - RangeRows: allItemsLoaded=");
         sb.append(String.valueOf(this.mRangeRowsAllItemsLoaded));
         sb.append(" items=");
         if(this.mRangeRows == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mRangeRows.size()));
         }
      }

      if(this.mRangeRows != null) {
         Iterator var5 = this.mRangeRows.values().iterator();

         while(var5.hasNext()) {
            SecurityRangeRowEVO listItem = (SecurityRangeRowEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(SecurityRangeRowEVO child, int newSecurityRangeRowId) {
      if(this.getRangeRowsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mRangeRows.remove(child.getPK());
         child.setSecurityRangeRowId(newSecurityRangeRowId);
         this.mRangeRows.put(child.getPK(), child);
      }
   }

   public void setRangeRowsAllItemsLoaded(boolean allItemsLoaded) {
      this.mRangeRowsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isRangeRowsAllItemsLoaded() {
      return this.mRangeRowsAllItemsLoaded;
   }
}
