// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.distribution;

import com.cedar.cp.api.report.distribution.DistributionRef;
import com.cedar.cp.dto.report.distribution.DistributionLinkPK;
import com.cedar.cp.dto.report.distribution.DistributionPK;
import com.cedar.cp.dto.report.distribution.DistributionRefImpl;
import com.cedar.cp.ejb.impl.report.distribution.DistributionLinkEVO;
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

public class DistributionEVO implements Serializable {

   private transient DistributionPK mPK;
   private int mDistributionId;
   private String mVisId;
   private String mDescription;
   private boolean mRaDistribution;
   private String mDirRoot;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<DistributionLinkPK, DistributionLinkEVO> mDistributionDestinationList;
   protected boolean mDistributionDestinationListAllItemsLoaded;
   private boolean mModified;


   public DistributionEVO() {}

   public DistributionEVO(int newDistributionId, String newVisId, String newDescription, boolean newRaDistribution, String newDirRoot, int newVersionNum, Collection newDistributionDestinationList) {
      this.mDistributionId = newDistributionId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mRaDistribution = newRaDistribution;
      this.mDirRoot = newDirRoot;
      this.mVersionNum = newVersionNum;
      this.setDistributionDestinationList(newDistributionDestinationList);
   }

   public void setDistributionDestinationList(Collection<DistributionLinkEVO> items) {
      if(items != null) {
         if(this.mDistributionDestinationList == null) {
            this.mDistributionDestinationList = new HashMap();
         } else {
            this.mDistributionDestinationList.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            DistributionLinkEVO child = (DistributionLinkEVO)i$.next();
            this.mDistributionDestinationList.put(child.getPK(), child);
         }
      } else {
         this.mDistributionDestinationList = null;
      }

   }

   public DistributionPK getPK() {
      if(this.mPK == null) {
         this.mPK = new DistributionPK(this.mDistributionId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getDistributionId() {
      return this.mDistributionId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean getRaDistribution() {
      return this.mRaDistribution;
   }

   public String getDirRoot() {
      return this.mDirRoot;
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

   public void setDistributionId(int newDistributionId) {
      if(this.mDistributionId != newDistributionId) {
         this.mModified = true;
         this.mDistributionId = newDistributionId;
         this.mPK = null;
      }
   }

   public void setRaDistribution(boolean newRaDistribution) {
      if(this.mRaDistribution != newRaDistribution) {
         this.mModified = true;
         this.mRaDistribution = newRaDistribution;
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

   public void setDirRoot(String newDirRoot) {
      if(this.mDirRoot != null && newDirRoot == null || this.mDirRoot == null && newDirRoot != null || this.mDirRoot != null && newDirRoot != null && !this.mDirRoot.equals(newDirRoot)) {
         this.mDirRoot = newDirRoot;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(DistributionEVO newDetails) {
      this.setDistributionId(newDetails.getDistributionId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setRaDistribution(newDetails.getRaDistribution());
      this.setDirRoot(newDetails.getDirRoot());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public DistributionEVO deepClone() {
      DistributionEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (DistributionEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mDistributionId > 0) {
         newKey = true;
         this.mDistributionId = 0;
      } else if(this.mDistributionId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      DistributionLinkEVO item;
      if(this.mDistributionDestinationList != null) {
         for(Iterator iter = (new ArrayList(this.mDistributionDestinationList.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (DistributionLinkEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mDistributionId < 1) {
         returnCount = startCount + 1;
      }

      DistributionLinkEVO item;
      if(this.mDistributionDestinationList != null) {
         for(Iterator iter = this.mDistributionDestinationList.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (DistributionLinkEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mDistributionId < 1) {
         this.mDistributionId = startKey;
         nextKey = startKey + 1;
      }

      DistributionLinkEVO item;
      if(this.mDistributionDestinationList != null) {
         for(Iterator iter = (new ArrayList(this.mDistributionDestinationList.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (DistributionLinkEVO)iter.next();
            this.changeKey(item, this.mDistributionId, item.getDistributionLinkId());
         }
      }

      return nextKey;
   }

   public Collection<DistributionLinkEVO> getDistributionDestinationList() {
      return this.mDistributionDestinationList != null?this.mDistributionDestinationList.values():null;
   }

   public Map<DistributionLinkPK, DistributionLinkEVO> getDistributionDestinationListMap() {
      return this.mDistributionDestinationList;
   }

   public void loadDistributionDestinationListItem(DistributionLinkEVO newItem) {
      if(this.mDistributionDestinationList == null) {
         this.mDistributionDestinationList = new HashMap();
      }

      this.mDistributionDestinationList.put(newItem.getPK(), newItem);
   }

   public void addDistributionDestinationListItem(DistributionLinkEVO newItem) {
      if(this.mDistributionDestinationList == null) {
         this.mDistributionDestinationList = new HashMap();
      }

      DistributionLinkPK newPK = newItem.getPK();
      if(this.getDistributionDestinationListItem(newPK) != null) {
         throw new RuntimeException("addDistributionDestinationListItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mDistributionDestinationList.put(newPK, newItem);
      }
   }

   public void changeDistributionDestinationListItem(DistributionLinkEVO changedItem) {
      if(this.mDistributionDestinationList == null) {
         throw new RuntimeException("changeDistributionDestinationListItem: no items in collection");
      } else {
         DistributionLinkPK changedPK = changedItem.getPK();
         DistributionLinkEVO listItem = this.getDistributionDestinationListItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeDistributionDestinationListItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteDistributionDestinationListItem(DistributionLinkPK removePK) {
      DistributionLinkEVO listItem = this.getDistributionDestinationListItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeDistributionDestinationListItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public DistributionLinkEVO getDistributionDestinationListItem(DistributionLinkPK pk) {
      return (DistributionLinkEVO)this.mDistributionDestinationList.get(pk);
   }

   public DistributionLinkEVO getDistributionDestinationListItem() {
      if(this.mDistributionDestinationList.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mDistributionDestinationList.size());
      } else {
         Iterator iter = this.mDistributionDestinationList.values().iterator();
         return (DistributionLinkEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public DistributionRef getEntityRef() {
      return new DistributionRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mDistributionDestinationListAllItemsLoaded = true;
      if(this.mDistributionDestinationList == null) {
         this.mDistributionDestinationList = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("DistributionId=");
      sb.append(String.valueOf(this.mDistributionId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("RaDistribution=");
      sb.append(String.valueOf(this.mRaDistribution));
      sb.append(' ');
      sb.append("DirRoot=");
      sb.append(String.valueOf(this.mDirRoot));
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

      sb.append("Distribution: ");
      sb.append(this.toString());
      if(this.mDistributionDestinationListAllItemsLoaded || this.mDistributionDestinationList != null) {
         sb.delete(indent, sb.length());
         sb.append(" - DistributionDestinationList: allItemsLoaded=");
         sb.append(String.valueOf(this.mDistributionDestinationListAllItemsLoaded));
         sb.append(" items=");
         if(this.mDistributionDestinationList == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mDistributionDestinationList.size()));
         }
      }

      if(this.mDistributionDestinationList != null) {
         Iterator var5 = this.mDistributionDestinationList.values().iterator();

         while(var5.hasNext()) {
            DistributionLinkEVO listItem = (DistributionLinkEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(DistributionLinkEVO child, int newDistributionId, int newDistributionLinkId) {
      if(this.getDistributionDestinationListItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mDistributionDestinationList.remove(child.getPK());
         child.setDistributionId(newDistributionId);
         child.setDistributionLinkId(newDistributionLinkId);
         this.mDistributionDestinationList.put(child.getPK(), child);
      }
   }

   public void setDistributionDestinationListAllItemsLoaded(boolean allItemsLoaded) {
      this.mDistributionDestinationListAllItemsLoaded = allItemsLoaded;
   }

   public boolean isDistributionDestinationListAllItemsLoaded() {
      return this.mDistributionDestinationListAllItemsLoaded;
   }
}
