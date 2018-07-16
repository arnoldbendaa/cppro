// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.destination.external;

import com.cedar.cp.api.report.destination.external.ExternalDestinationRef;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationRefImpl;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersPK;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationUsersEVO;
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

public class ExternalDestinationEVO implements Serializable {

   private transient ExternalDestinationPK mPK;
   private int mExternalDestinationId;
   private String mVisId;
   private String mDescription;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<ExternalDestinationUsersPK, ExternalDestinationUsersEVO> mExternalUserList;
   protected boolean mExternalUserListAllItemsLoaded;
   private boolean mModified;


   public ExternalDestinationEVO() {}

   public ExternalDestinationEVO(int newExternalDestinationId, String newVisId, String newDescription, int newVersionNum, Collection newExternalUserList) {
      this.mExternalDestinationId = newExternalDestinationId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mVersionNum = newVersionNum;
      this.setExternalUserList(newExternalUserList);
   }

   public void setExternalUserList(Collection<ExternalDestinationUsersEVO> items) {
      if(items != null) {
         if(this.mExternalUserList == null) {
            this.mExternalUserList = new HashMap();
         } else {
            this.mExternalUserList.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ExternalDestinationUsersEVO child = (ExternalDestinationUsersEVO)i$.next();
            this.mExternalUserList.put(child.getPK(), child);
         }
      } else {
         this.mExternalUserList = null;
      }

   }

   public ExternalDestinationPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExternalDestinationPK(this.mExternalDestinationId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getExternalDestinationId() {
      return this.mExternalDestinationId;
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

   public void setExternalDestinationId(int newExternalDestinationId) {
      if(this.mExternalDestinationId != newExternalDestinationId) {
         this.mModified = true;
         this.mExternalDestinationId = newExternalDestinationId;
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

   public void setDetails(ExternalDestinationEVO newDetails) {
      this.setExternalDestinationId(newDetails.getExternalDestinationId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ExternalDestinationEVO deepClone() {
      ExternalDestinationEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ExternalDestinationEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mExternalDestinationId > 0) {
         newKey = true;
         this.mExternalDestinationId = 0;
      } else if(this.mExternalDestinationId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      ExternalDestinationUsersEVO item;
      if(this.mExternalUserList != null) {
         for(Iterator iter = (new ArrayList(this.mExternalUserList.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ExternalDestinationUsersEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mExternalDestinationId < 1) {
         returnCount = startCount + 1;
      }

      ExternalDestinationUsersEVO item;
      if(this.mExternalUserList != null) {
         for(Iterator iter = this.mExternalUserList.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ExternalDestinationUsersEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mExternalDestinationId < 1) {
         this.mExternalDestinationId = startKey;
         nextKey = startKey + 1;
      }

      ExternalDestinationUsersEVO item;
      if(this.mExternalUserList != null) {
         for(Iterator iter = (new ArrayList(this.mExternalUserList.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ExternalDestinationUsersEVO)iter.next();
            this.changeKey(item, this.mExternalDestinationId, item.getExternalDestinationUsersId());
         }
      }

      return nextKey;
   }

   public Collection<ExternalDestinationUsersEVO> getExternalUserList() {
      return this.mExternalUserList != null?this.mExternalUserList.values():null;
   }

   public Map<ExternalDestinationUsersPK, ExternalDestinationUsersEVO> getExternalUserListMap() {
      return this.mExternalUserList;
   }

   public void loadExternalUserListItem(ExternalDestinationUsersEVO newItem) {
      if(this.mExternalUserList == null) {
         this.mExternalUserList = new HashMap();
      }

      this.mExternalUserList.put(newItem.getPK(), newItem);
   }

   public void addExternalUserListItem(ExternalDestinationUsersEVO newItem) {
      if(this.mExternalUserList == null) {
         this.mExternalUserList = new HashMap();
      }

      ExternalDestinationUsersPK newPK = newItem.getPK();
      if(this.getExternalUserListItem(newPK) != null) {
         throw new RuntimeException("addExternalUserListItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mExternalUserList.put(newPK, newItem);
      }
   }

   public void changeExternalUserListItem(ExternalDestinationUsersEVO changedItem) {
      if(this.mExternalUserList == null) {
         throw new RuntimeException("changeExternalUserListItem: no items in collection");
      } else {
         ExternalDestinationUsersPK changedPK = changedItem.getPK();
         ExternalDestinationUsersEVO listItem = this.getExternalUserListItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeExternalUserListItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteExternalUserListItem(ExternalDestinationUsersPK removePK) {
      ExternalDestinationUsersEVO listItem = this.getExternalUserListItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeExternalUserListItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ExternalDestinationUsersEVO getExternalUserListItem(ExternalDestinationUsersPK pk) {
      return (ExternalDestinationUsersEVO)this.mExternalUserList.get(pk);
   }

   public ExternalDestinationUsersEVO getExternalUserListItem() {
      if(this.mExternalUserList.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mExternalUserList.size());
      } else {
         Iterator iter = this.mExternalUserList.values().iterator();
         return (ExternalDestinationUsersEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public ExternalDestinationRef getEntityRef() {
      return new ExternalDestinationRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mExternalUserListAllItemsLoaded = true;
      if(this.mExternalUserList == null) {
         this.mExternalUserList = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ExternalDestinationId=");
      sb.append(String.valueOf(this.mExternalDestinationId));
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

      sb.append("ExternalDestination: ");
      sb.append(this.toString());
      if(this.mExternalUserListAllItemsLoaded || this.mExternalUserList != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ExternalUserList: allItemsLoaded=");
         sb.append(String.valueOf(this.mExternalUserListAllItemsLoaded));
         sb.append(" items=");
         if(this.mExternalUserList == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mExternalUserList.size()));
         }
      }

      if(this.mExternalUserList != null) {
         Iterator var5 = this.mExternalUserList.values().iterator();

         while(var5.hasNext()) {
            ExternalDestinationUsersEVO listItem = (ExternalDestinationUsersEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ExternalDestinationUsersEVO child, int newExternalDestinationId, int newExternalDestinationUsersId) {
      if(this.getExternalUserListItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mExternalUserList.remove(child.getPK());
         child.setExternalDestinationId(newExternalDestinationId);
         child.setExternalDestinationUsersId(newExternalDestinationUsersId);
         this.mExternalUserList.put(child.getPK(), child);
      }
   }

   public void setExternalUserListAllItemsLoaded(boolean allItemsLoaded) {
      this.mExternalUserListAllItemsLoaded = allItemsLoaded;
   }

   public boolean isExternalUserListAllItemsLoaded() {
      return this.mExternalUserListAllItemsLoaded;
   }
}
