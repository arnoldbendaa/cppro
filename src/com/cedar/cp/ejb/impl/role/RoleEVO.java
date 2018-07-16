// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.role;

import com.cedar.cp.api.role.RoleRef;
import com.cedar.cp.dto.role.RolePK;
import com.cedar.cp.dto.role.RoleRefImpl;
import com.cedar.cp.dto.role.RoleSecurityRelPK;
import com.cedar.cp.ejb.impl.role.RoleSecurityRelEVO;
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

public class RoleEVO implements Serializable {

   private transient RolePK mPK;
   private int mRoleId;
   private String mVisId;
   private String mDescription;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<RoleSecurityRelPK, RoleSecurityRelEVO> mRoleSecurity;
   protected boolean mRoleSecurityAllItemsLoaded;
   private boolean mModified;


   public RoleEVO() {}

   public RoleEVO(int newRoleId, String newVisId, String newDescription, int newVersionNum, Collection newRoleSecurity) {
      this.mRoleId = newRoleId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mVersionNum = newVersionNum;
      this.setRoleSecurity(newRoleSecurity);
   }

   public void setRoleSecurity(Collection<RoleSecurityRelEVO> items) {
      if(items != null) {
         if(this.mRoleSecurity == null) {
            this.mRoleSecurity = new HashMap();
         } else {
            this.mRoleSecurity.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            RoleSecurityRelEVO child = (RoleSecurityRelEVO)i$.next();
            this.mRoleSecurity.put(child.getPK(), child);
         }
      } else {
         this.mRoleSecurity = null;
      }

   }

   public RolePK getPK() {
      if(this.mPK == null) {
         this.mPK = new RolePK(this.mRoleId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getRoleId() {
      return this.mRoleId;
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

   public void setRoleId(int newRoleId) {
      if(this.mRoleId != newRoleId) {
         this.mModified = true;
         this.mRoleId = newRoleId;
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

   public void setDetails(RoleEVO newDetails) {
      this.setRoleId(newDetails.getRoleId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public RoleEVO deepClone() {
      RoleEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (RoleEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mRoleId > 0) {
         newKey = true;
         this.mRoleId = 0;
      } else if(this.mRoleId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      RoleSecurityRelEVO item;
      if(this.mRoleSecurity != null) {
         for(Iterator iter = (new ArrayList(this.mRoleSecurity.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (RoleSecurityRelEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mRoleId < 1) {
         returnCount = startCount + 1;
      }

      RoleSecurityRelEVO item;
      if(this.mRoleSecurity != null) {
         for(Iterator iter = this.mRoleSecurity.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (RoleSecurityRelEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mRoleId < 1) {
         this.mRoleId = startKey;
         nextKey = startKey + 1;
      }

      RoleSecurityRelEVO item;
      if(this.mRoleSecurity != null) {
         for(Iterator iter = (new ArrayList(this.mRoleSecurity.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (RoleSecurityRelEVO)iter.next();
            this.changeKey(item, this.mRoleId, item.getRoleSecurityId());
         }
      }

      return nextKey;
   }

   public Collection<RoleSecurityRelEVO> getRoleSecurity() {
      return this.mRoleSecurity != null?this.mRoleSecurity.values():null;
   }

   public Map<RoleSecurityRelPK, RoleSecurityRelEVO> getRoleSecurityMap() {
      return this.mRoleSecurity;
   }

   public void loadRoleSecurityItem(RoleSecurityRelEVO newItem) {
      if(this.mRoleSecurity == null) {
         this.mRoleSecurity = new HashMap();
      }

      this.mRoleSecurity.put(newItem.getPK(), newItem);
   }

   public void addRoleSecurityItem(RoleSecurityRelEVO newItem) {
      if(this.mRoleSecurity == null) {
         this.mRoleSecurity = new HashMap();
      }

      RoleSecurityRelPK newPK = newItem.getPK();
      if(this.getRoleSecurityItem(newPK) != null) {
         throw new RuntimeException("addRoleSecurityItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mRoleSecurity.put(newPK, newItem);
      }
   }

   public void changeRoleSecurityItem(RoleSecurityRelEVO changedItem) {
      if(this.mRoleSecurity == null) {
         throw new RuntimeException("changeRoleSecurityItem: no items in collection");
      } else {
         RoleSecurityRelPK changedPK = changedItem.getPK();
         RoleSecurityRelEVO listItem = this.getRoleSecurityItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeRoleSecurityItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteRoleSecurityItem(RoleSecurityRelPK removePK) {
      RoleSecurityRelEVO listItem = this.getRoleSecurityItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeRoleSecurityItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public RoleSecurityRelEVO getRoleSecurityItem(RoleSecurityRelPK pk) {
      return (RoleSecurityRelEVO)this.mRoleSecurity.get(pk);
   }

   public RoleSecurityRelEVO getRoleSecurityItem() {
      if(this.mRoleSecurity.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mRoleSecurity.size());
      } else {
         Iterator iter = this.mRoleSecurity.values().iterator();
         return (RoleSecurityRelEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public RoleRef getEntityRef() {
      return new RoleRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mRoleSecurityAllItemsLoaded = true;
      if(this.mRoleSecurity == null) {
         this.mRoleSecurity = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RoleId=");
      sb.append(String.valueOf(this.mRoleId));
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

      sb.append("Role: ");
      sb.append(this.toString());
      if(this.mRoleSecurityAllItemsLoaded || this.mRoleSecurity != null) {
         sb.delete(indent, sb.length());
         sb.append(" - RoleSecurity: allItemsLoaded=");
         sb.append(String.valueOf(this.mRoleSecurityAllItemsLoaded));
         sb.append(" items=");
         if(this.mRoleSecurity == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mRoleSecurity.size()));
         }
      }

      if(this.mRoleSecurity != null) {
         Iterator var5 = this.mRoleSecurity.values().iterator();

         while(var5.hasNext()) {
            RoleSecurityRelEVO listItem = (RoleSecurityRelEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(RoleSecurityRelEVO child, int newRoleId, int newRoleSecurityId) {
      if(this.getRoleSecurityItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mRoleSecurity.remove(child.getPK());
         child.setRoleId(newRoleId);
         child.setRoleSecurityId(newRoleSecurityId);
         this.mRoleSecurity.put(child.getPK(), child);
      }
   }

   public void setRoleSecurityAllItemsLoaded(boolean allItemsLoaded) {
      this.mRoleSecurityAllItemsLoaded = allItemsLoaded;
   }

   public boolean isRoleSecurityAllItemsLoaded() {
      return this.mRoleSecurityAllItemsLoaded;
   }
}
