// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.destination.internal;

import com.cedar.cp.api.report.destination.internal.InternalDestinationRef;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationRefImpl;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersPK;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationUsersEVO;
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

public class InternalDestinationEVO implements Serializable {

   private transient InternalDestinationPK mPK;
   private int mInternalDestinationId;
   private String mVisId;
   private String mDescription;
   private int mMessageType;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<InternalDestinationUsersPK, InternalDestinationUsersEVO> mInternalUserList;
   protected boolean mInternalUserListAllItemsLoaded;
   private boolean mModified;


   public InternalDestinationEVO() {}

   public InternalDestinationEVO(int newInternalDestinationId, String newVisId, String newDescription, int newMessageType, int newVersionNum, Collection newInternalUserList) {
      this.mInternalDestinationId = newInternalDestinationId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mMessageType = newMessageType;
      this.mVersionNum = newVersionNum;
      this.setInternalUserList(newInternalUserList);
   }

   public void setInternalUserList(Collection<InternalDestinationUsersEVO> items) {
      if(items != null) {
         if(this.mInternalUserList == null) {
            this.mInternalUserList = new HashMap();
         } else {
            this.mInternalUserList.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            InternalDestinationUsersEVO child = (InternalDestinationUsersEVO)i$.next();
            this.mInternalUserList.put(child.getPK(), child);
         }
      } else {
         this.mInternalUserList = null;
      }

   }

   public InternalDestinationPK getPK() {
      if(this.mPK == null) {
         this.mPK = new InternalDestinationPK(this.mInternalDestinationId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getInternalDestinationId() {
      return this.mInternalDestinationId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getMessageType() {
      return this.mMessageType;
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

   public void setInternalDestinationId(int newInternalDestinationId) {
      if(this.mInternalDestinationId != newInternalDestinationId) {
         this.mModified = true;
         this.mInternalDestinationId = newInternalDestinationId;
         this.mPK = null;
      }
   }

   public void setMessageType(int newMessageType) {
      if(this.mMessageType != newMessageType) {
         this.mModified = true;
         this.mMessageType = newMessageType;
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

   public void setDetails(InternalDestinationEVO newDetails) {
      this.setInternalDestinationId(newDetails.getInternalDestinationId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setMessageType(newDetails.getMessageType());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public InternalDestinationEVO deepClone() {
      InternalDestinationEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (InternalDestinationEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mInternalDestinationId > 0) {
         newKey = true;
         this.mInternalDestinationId = 0;
      } else if(this.mInternalDestinationId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      InternalDestinationUsersEVO item;
      if(this.mInternalUserList != null) {
         for(Iterator iter = (new ArrayList(this.mInternalUserList.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (InternalDestinationUsersEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mInternalDestinationId < 1) {
         returnCount = startCount + 1;
      }

      InternalDestinationUsersEVO item;
      if(this.mInternalUserList != null) {
         for(Iterator iter = this.mInternalUserList.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (InternalDestinationUsersEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mInternalDestinationId < 1) {
         this.mInternalDestinationId = startKey;
         nextKey = startKey + 1;
      }

      InternalDestinationUsersEVO item;
      if(this.mInternalUserList != null) {
         for(Iterator iter = (new ArrayList(this.mInternalUserList.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (InternalDestinationUsersEVO)iter.next();
            this.changeKey(item, this.mInternalDestinationId, item.getUserId());
         }
      }

      return nextKey;
   }

   public Collection<InternalDestinationUsersEVO> getInternalUserList() {
      return this.mInternalUserList != null?this.mInternalUserList.values():null;
   }

   public Map<InternalDestinationUsersPK, InternalDestinationUsersEVO> getInternalUserListMap() {
      return this.mInternalUserList;
   }

   public void loadInternalUserListItem(InternalDestinationUsersEVO newItem) {
      if(this.mInternalUserList == null) {
         this.mInternalUserList = new HashMap();
      }

      this.mInternalUserList.put(newItem.getPK(), newItem);
   }

   public void addInternalUserListItem(InternalDestinationUsersEVO newItem) {
      if(this.mInternalUserList == null) {
         this.mInternalUserList = new HashMap();
      }

      InternalDestinationUsersPK newPK = newItem.getPK();
      if(this.getInternalUserListItem(newPK) != null) {
         throw new RuntimeException("addInternalUserListItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mInternalUserList.put(newPK, newItem);
      }
   }

   public void changeInternalUserListItem(InternalDestinationUsersEVO changedItem) {
      if(this.mInternalUserList == null) {
         throw new RuntimeException("changeInternalUserListItem: no items in collection");
      } else {
         InternalDestinationUsersPK changedPK = changedItem.getPK();
         InternalDestinationUsersEVO listItem = this.getInternalUserListItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeInternalUserListItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteInternalUserListItem(InternalDestinationUsersPK removePK) {
      InternalDestinationUsersEVO listItem = this.getInternalUserListItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeInternalUserListItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public InternalDestinationUsersEVO getInternalUserListItem(InternalDestinationUsersPK pk) {
      return (InternalDestinationUsersEVO)this.mInternalUserList.get(pk);
   }

   public InternalDestinationUsersEVO getInternalUserListItem() {
      if(this.mInternalUserList.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mInternalUserList.size());
      } else {
         Iterator iter = this.mInternalUserList.values().iterator();
         return (InternalDestinationUsersEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public InternalDestinationRef getEntityRef() {
      return new InternalDestinationRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mInternalUserListAllItemsLoaded = true;
      if(this.mInternalUserList == null) {
         this.mInternalUserList = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("InternalDestinationId=");
      sb.append(String.valueOf(this.mInternalDestinationId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("MessageType=");
      sb.append(String.valueOf(this.mMessageType));
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

      sb.append("InternalDestination: ");
      sb.append(this.toString());
      if(this.mInternalUserListAllItemsLoaded || this.mInternalUserList != null) {
         sb.delete(indent, sb.length());
         sb.append(" - InternalUserList: allItemsLoaded=");
         sb.append(String.valueOf(this.mInternalUserListAllItemsLoaded));
         sb.append(" items=");
         if(this.mInternalUserList == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mInternalUserList.size()));
         }
      }

      if(this.mInternalUserList != null) {
         Iterator var5 = this.mInternalUserList.values().iterator();

         while(var5.hasNext()) {
            InternalDestinationUsersEVO listItem = (InternalDestinationUsersEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(InternalDestinationUsersEVO child, int newInternalDestinationId, int newUserId) {
      if(this.getInternalUserListItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mInternalUserList.remove(child.getPK());
         child.setInternalDestinationId(newInternalDestinationId);
         child.setUserId(newUserId);
         this.mInternalUserList.put(child.getPK(), child);
      }
   }

   public void setInternalUserListAllItemsLoaded(boolean allItemsLoaded) {
      this.mInternalUserListAllItemsLoaded = allItemsLoaded;
   }

   public boolean isInternalUserListAllItemsLoaded() {
      return this.mInternalUserListAllItemsLoaded;
   }
}
