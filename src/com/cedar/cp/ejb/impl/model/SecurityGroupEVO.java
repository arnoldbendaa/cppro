// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.SecurityGroupRef;
import com.cedar.cp.dto.model.SecurityGroupCK;
import com.cedar.cp.dto.model.SecurityGroupPK;
import com.cedar.cp.dto.model.SecurityGroupRefImpl;
import com.cedar.cp.dto.model.SecurityGroupUserRelPK;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.SecurityGroupUserRelEVO;
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

public class SecurityGroupEVO implements Serializable {

   private transient SecurityGroupPK mPK;
   private int mSecurityGroupId;
   private int mModelId;
   private String mVisId;
   private String mDescription;
   private int mUpdateAccessId;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<SecurityGroupUserRelPK, SecurityGroupUserRelEVO> mUsersInGroup;
   protected boolean mUsersInGroupAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public SecurityGroupEVO() {}

   public SecurityGroupEVO(int newSecurityGroupId, int newModelId, String newVisId, String newDescription, int newUpdateAccessId, int newVersionNum, Collection newUsersInGroup) {
      this.mSecurityGroupId = newSecurityGroupId;
      this.mModelId = newModelId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mUpdateAccessId = newUpdateAccessId;
      this.mVersionNum = newVersionNum;
      this.setUsersInGroup(newUsersInGroup);
   }

   public void setUsersInGroup(Collection<SecurityGroupUserRelEVO> items) {
      if(items != null) {
         if(this.mUsersInGroup == null) {
            this.mUsersInGroup = new HashMap();
         } else {
            this.mUsersInGroup.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            SecurityGroupUserRelEVO child = (SecurityGroupUserRelEVO)i$.next();
            this.mUsersInGroup.put(child.getPK(), child);
         }
      } else {
         this.mUsersInGroup = null;
      }

   }

   public SecurityGroupPK getPK() {
      if(this.mPK == null) {
         this.mPK = new SecurityGroupPK(this.mSecurityGroupId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getSecurityGroupId() {
      return this.mSecurityGroupId;
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

   public int getUpdateAccessId() {
      return this.mUpdateAccessId;
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

   public void setSecurityGroupId(int newSecurityGroupId) {
      if(this.mSecurityGroupId != newSecurityGroupId) {
         this.mModified = true;
         this.mSecurityGroupId = newSecurityGroupId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setUpdateAccessId(int newUpdateAccessId) {
      if(this.mUpdateAccessId != newUpdateAccessId) {
         this.mModified = true;
         this.mUpdateAccessId = newUpdateAccessId;
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

   public void setDetails(SecurityGroupEVO newDetails) {
      this.setSecurityGroupId(newDetails.getSecurityGroupId());
      this.setModelId(newDetails.getModelId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setUpdateAccessId(newDetails.getUpdateAccessId());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public SecurityGroupEVO deepClone() {
      SecurityGroupEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (SecurityGroupEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mSecurityGroupId > 0) {
         newKey = true;
         if(parent == null) {
            this.setSecurityGroupId(-this.mSecurityGroupId);
         } else {
            parent.changeKey(this, -this.mSecurityGroupId);
         }
      } else if(this.mSecurityGroupId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      SecurityGroupUserRelEVO item;
      if(this.mUsersInGroup != null) {
         for(Iterator iter = (new ArrayList(this.mUsersInGroup.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (SecurityGroupUserRelEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mSecurityGroupId < 1) {
         returnCount = startCount + 1;
      }

      SecurityGroupUserRelEVO item;
      if(this.mUsersInGroup != null) {
         for(Iterator iter = this.mUsersInGroup.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (SecurityGroupUserRelEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mSecurityGroupId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      SecurityGroupUserRelEVO item;
      if(this.mUsersInGroup != null) {
         for(Iterator iter = (new ArrayList(this.mUsersInGroup.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (SecurityGroupUserRelEVO)iter.next();
            this.changeKey(item, this.mSecurityGroupId, item.getUserId());
         }
      }

      return nextKey;
   }

   public Collection<SecurityGroupUserRelEVO> getUsersInGroup() {
      return this.mUsersInGroup != null?this.mUsersInGroup.values():null;
   }

   public Map<SecurityGroupUserRelPK, SecurityGroupUserRelEVO> getUsersInGroupMap() {
      return this.mUsersInGroup;
   }

   public void loadUsersInGroupItem(SecurityGroupUserRelEVO newItem) {
      if(this.mUsersInGroup == null) {
         this.mUsersInGroup = new HashMap();
      }

      this.mUsersInGroup.put(newItem.getPK(), newItem);
   }

   public void addUsersInGroupItem(SecurityGroupUserRelEVO newItem) {
      if(this.mUsersInGroup == null) {
         this.mUsersInGroup = new HashMap();
      }

      SecurityGroupUserRelPK newPK = newItem.getPK();
      if(this.getUsersInGroupItem(newPK) != null) {
         throw new RuntimeException("addUsersInGroupItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mUsersInGroup.put(newPK, newItem);
      }
   }

   public void changeUsersInGroupItem(SecurityGroupUserRelEVO changedItem) {
      if(this.mUsersInGroup == null) {
         throw new RuntimeException("changeUsersInGroupItem: no items in collection");
      } else {
         SecurityGroupUserRelPK changedPK = changedItem.getPK();
         SecurityGroupUserRelEVO listItem = this.getUsersInGroupItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeUsersInGroupItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteUsersInGroupItem(SecurityGroupUserRelPK removePK) {
      SecurityGroupUserRelEVO listItem = this.getUsersInGroupItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeUsersInGroupItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public SecurityGroupUserRelEVO getUsersInGroupItem(SecurityGroupUserRelPK pk) {
      return (SecurityGroupUserRelEVO)this.mUsersInGroup.get(pk);
   }

   public SecurityGroupUserRelEVO getUsersInGroupItem() {
      if(this.mUsersInGroup.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mUsersInGroup.size());
      } else {
         Iterator iter = this.mUsersInGroup.values().iterator();
         return (SecurityGroupUserRelEVO)iter.next();
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

   public SecurityGroupRef getEntityRef(ModelEVO evoModel) {
      return new SecurityGroupRefImpl(new SecurityGroupCK(evoModel.getPK(), this.getPK()), this.mVisId);
   }

   public SecurityGroupCK getCK(ModelEVO evoModel) {
      return new SecurityGroupCK(evoModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mUsersInGroupAllItemsLoaded = true;
      if(this.mUsersInGroup == null) {
         this.mUsersInGroup = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("SecurityGroupId=");
      sb.append(String.valueOf(this.mSecurityGroupId));
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
      sb.append("UpdateAccessId=");
      sb.append(String.valueOf(this.mUpdateAccessId));
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

      sb.append("SecurityGroup: ");
      sb.append(this.toString());
      if(this.mUsersInGroupAllItemsLoaded || this.mUsersInGroup != null) {
         sb.delete(indent, sb.length());
         sb.append(" - UsersInGroup: allItemsLoaded=");
         sb.append(String.valueOf(this.mUsersInGroupAllItemsLoaded));
         sb.append(" items=");
         if(this.mUsersInGroup == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mUsersInGroup.size()));
         }
      }

      if(this.mUsersInGroup != null) {
         Iterator var5 = this.mUsersInGroup.values().iterator();

         while(var5.hasNext()) {
            SecurityGroupUserRelEVO listItem = (SecurityGroupUserRelEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(SecurityGroupUserRelEVO child, int newSecurityGroupId, int newUserId) {
      if(this.getUsersInGroupItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mUsersInGroup.remove(child.getPK());
         child.setSecurityGroupId(newSecurityGroupId);
         child.setUserId(newUserId);
         this.mUsersInGroup.put(child.getPK(), child);
      }
   }

   public void setUsersInGroupAllItemsLoaded(boolean allItemsLoaded) {
      this.mUsersInGroupAllItemsLoaded = allItemsLoaded;
   }

   public boolean isUsersInGroupAllItemsLoaded() {
      return this.mUsersInGroupAllItemsLoaded;
   }
}
