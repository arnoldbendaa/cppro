// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.recharge;

import com.cedar.cp.api.model.recharge.RechargeGroupRef;
import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
import com.cedar.cp.dto.model.recharge.RechargeGroupRefImpl;
import com.cedar.cp.dto.rechargegroup.RechargeGroupRelPK;
import com.cedar.cp.ejb.impl.rechargegroup.RechargeGroupRelEVO;
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

public class RechargeGroupEVO implements Serializable {

   private transient RechargeGroupPK mPK;
   private int mRechargeGroupId;
   private String mVisId;
   private String mDescription;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<RechargeGroupRelPK, RechargeGroupRelEVO> mRecharges;
   protected boolean mRechargesAllItemsLoaded;
   private boolean mModified;


   public RechargeGroupEVO() {}

   public RechargeGroupEVO(int newRechargeGroupId, String newVisId, String newDescription, int newVersionNum, Collection newRecharges) {
      this.mRechargeGroupId = newRechargeGroupId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mVersionNum = newVersionNum;
      this.setRecharges(newRecharges);
   }

   public void setRecharges(Collection<RechargeGroupRelEVO> items) {
      if(items != null) {
         if(this.mRecharges == null) {
            this.mRecharges = new HashMap();
         } else {
            this.mRecharges.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            RechargeGroupRelEVO child = (RechargeGroupRelEVO)i$.next();
            this.mRecharges.put(child.getPK(), child);
         }
      } else {
         this.mRecharges = null;
      }

   }

   public RechargeGroupPK getPK() {
      if(this.mPK == null) {
         this.mPK = new RechargeGroupPK(this.mRechargeGroupId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getRechargeGroupId() {
      return this.mRechargeGroupId;
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

   public void setRechargeGroupId(int newRechargeGroupId) {
      if(this.mRechargeGroupId != newRechargeGroupId) {
         this.mModified = true;
         this.mRechargeGroupId = newRechargeGroupId;
         this.mPK = null;
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

   public void setDetails(RechargeGroupEVO newDetails) {
      this.setRechargeGroupId(newDetails.getRechargeGroupId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public RechargeGroupEVO deepClone() {
      RechargeGroupEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (RechargeGroupEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mRechargeGroupId > 0) {
         newKey = true;
         this.mRechargeGroupId = 0;
      } else if(this.mRechargeGroupId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      RechargeGroupRelEVO item;
      if(this.mRecharges != null) {
         for(Iterator iter = (new ArrayList(this.mRecharges.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (RechargeGroupRelEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mRechargeGroupId < 1) {
         returnCount = startCount + 1;
      }

      RechargeGroupRelEVO item;
      if(this.mRecharges != null) {
         for(Iterator iter = this.mRecharges.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (RechargeGroupRelEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mRechargeGroupId < 1) {
         this.mRechargeGroupId = startKey;
         nextKey = startKey + 1;
      }

      RechargeGroupRelEVO item;
      if(this.mRecharges != null) {
         for(Iterator iter = (new ArrayList(this.mRecharges.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (RechargeGroupRelEVO)iter.next();
            this.changeKey(item, item.getRechargeGroupRelId(), this.mRechargeGroupId);
         }
      }

      return nextKey;
   }

   public Collection<RechargeGroupRelEVO> getRecharges() {
      return this.mRecharges != null?this.mRecharges.values():null;
   }

   public Map<RechargeGroupRelPK, RechargeGroupRelEVO> getRechargesMap() {
      return this.mRecharges;
   }

   public void loadRechargesItem(RechargeGroupRelEVO newItem) {
      if(this.mRecharges == null) {
         this.mRecharges = new HashMap();
      }

      this.mRecharges.put(newItem.getPK(), newItem);
   }

   public void addRechargesItem(RechargeGroupRelEVO newItem) {
      if(this.mRecharges == null) {
         this.mRecharges = new HashMap();
      }

      RechargeGroupRelPK newPK = newItem.getPK();
      if(this.getRechargesItem(newPK) != null) {
         throw new RuntimeException("addRechargesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mRecharges.put(newPK, newItem);
      }
   }

   public void changeRechargesItem(RechargeGroupRelEVO changedItem) {
      if(this.mRecharges == null) {
         throw new RuntimeException("changeRechargesItem: no items in collection");
      } else {
         RechargeGroupRelPK changedPK = changedItem.getPK();
         RechargeGroupRelEVO listItem = this.getRechargesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeRechargesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteRechargesItem(RechargeGroupRelPK removePK) {
      RechargeGroupRelEVO listItem = this.getRechargesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeRechargesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public RechargeGroupRelEVO getRechargesItem(RechargeGroupRelPK pk) {
      return (RechargeGroupRelEVO)this.mRecharges.get(pk);
   }

   public RechargeGroupRelEVO getRechargesItem() {
      if(this.mRecharges.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mRecharges.size());
      } else {
         Iterator iter = this.mRecharges.values().iterator();
         return (RechargeGroupRelEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public RechargeGroupRef getEntityRef() {
      return new RechargeGroupRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mRechargesAllItemsLoaded = true;
      if(this.mRecharges == null) {
         this.mRecharges = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RechargeGroupId=");
      sb.append(String.valueOf(this.mRechargeGroupId));
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

      sb.append("RechargeGroup: ");
      sb.append(this.toString());
      if(this.mRechargesAllItemsLoaded || this.mRecharges != null) {
         sb.delete(indent, sb.length());
         sb.append(" - Recharges: allItemsLoaded=");
         sb.append(String.valueOf(this.mRechargesAllItemsLoaded));
         sb.append(" items=");
         if(this.mRecharges == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mRecharges.size()));
         }
      }

      if(this.mRecharges != null) {
         Iterator var5 = this.mRecharges.values().iterator();

         while(var5.hasNext()) {
            RechargeGroupRelEVO listItem = (RechargeGroupRelEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(RechargeGroupRelEVO child, int newRechargeGroupRelId, int newRechargeGroupId) {
      if(this.getRechargesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mRecharges.remove(child.getPK());
         child.setRechargeGroupRelId(newRechargeGroupRelId);
         child.setRechargeGroupId(newRechargeGroupId);
         this.mRecharges.put(child.getPK(), child);
      }
   }

   public void setRechargesAllItemsLoaded(boolean allItemsLoaded) {
      this.mRechargesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isRechargesAllItemsLoaded() {
      return this.mRechargesAllItemsLoaded;
   }
}
