// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.api.model.virement.VirementAuthPointRef;
import com.cedar.cp.dto.model.virement.VirementAuthPointCK;
import com.cedar.cp.dto.model.virement.VirementAuthPointLinkPK;
import com.cedar.cp.dto.model.virement.VirementAuthPointPK;
import com.cedar.cp.dto.model.virement.VirementAuthPointRefImpl;
import com.cedar.cp.dto.model.virement.VirementAuthorisersPK;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementAuthPointLinkEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementAuthorisersEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEVO;
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

public class VirementAuthPointEVO implements Serializable {

   private transient VirementAuthPointPK mPK;
   private int mAuthPointId;
   private int mRequestId;
   private int mPointStatus;
   private Integer mAuthUserId;
   private String mNotes;
   private int mStructureElementId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<VirementAuthorisersPK, VirementAuthorisersEVO> mAuthUsers;
   protected boolean mAuthUsersAllItemsLoaded;
   private Map<VirementAuthPointLinkPK, VirementAuthPointLinkEVO> mAuthPointLinks;
   protected boolean mAuthPointLinksAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int NOT_AUTHORISED = 0;
   public static final int AUTHORISED = 1;
   public static final int REJECTED = 2;


   public VirementAuthPointEVO() {}

   public VirementAuthPointEVO(int newAuthPointId, int newRequestId, int newPointStatus, Integer newAuthUserId, String newNotes, int newStructureElementId, Collection newAuthUsers, Collection newAuthPointLinks) {
      this.mAuthPointId = newAuthPointId;
      this.mRequestId = newRequestId;
      this.mPointStatus = newPointStatus;
      this.mAuthUserId = newAuthUserId;
      this.mNotes = newNotes;
      this.mStructureElementId = newStructureElementId;
      this.setAuthUsers(newAuthUsers);
      this.setAuthPointLinks(newAuthPointLinks);
   }

   public void setAuthUsers(Collection<VirementAuthorisersEVO> items) {
      if(items != null) {
         if(this.mAuthUsers == null) {
            this.mAuthUsers = new HashMap();
         } else {
            this.mAuthUsers.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            VirementAuthorisersEVO child = (VirementAuthorisersEVO)i$.next();
            this.mAuthUsers.put(child.getPK(), child);
         }
      } else {
         this.mAuthUsers = null;
      }

   }

   public void setAuthPointLinks(Collection<VirementAuthPointLinkEVO> items) {
      if(items != null) {
         if(this.mAuthPointLinks == null) {
            this.mAuthPointLinks = new HashMap();
         } else {
            this.mAuthPointLinks.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            VirementAuthPointLinkEVO child = (VirementAuthPointLinkEVO)i$.next();
            this.mAuthPointLinks.put(child.getPK(), child);
         }
      } else {
         this.mAuthPointLinks = null;
      }

   }

   public VirementAuthPointPK getPK() {
      if(this.mPK == null) {
         this.mPK = new VirementAuthPointPK(this.mAuthPointId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getAuthPointId() {
      return this.mAuthPointId;
   }

   public int getRequestId() {
      return this.mRequestId;
   }

   public int getPointStatus() {
      return this.mPointStatus;
   }

   public Integer getAuthUserId() {
      return this.mAuthUserId;
   }

   public String getNotes() {
      return this.mNotes;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
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

   public void setAuthPointId(int newAuthPointId) {
      if(this.mAuthPointId != newAuthPointId) {
         this.mModified = true;
         this.mAuthPointId = newAuthPointId;
         this.mPK = null;
      }
   }

   public void setRequestId(int newRequestId) {
      if(this.mRequestId != newRequestId) {
         this.mModified = true;
         this.mRequestId = newRequestId;
      }
   }

   public void setPointStatus(int newPointStatus) {
      if(this.mPointStatus != newPointStatus) {
         this.mModified = true;
         this.mPointStatus = newPointStatus;
      }
   }

   public void setStructureElementId(int newStructureElementId) {
      if(this.mStructureElementId != newStructureElementId) {
         this.mModified = true;
         this.mStructureElementId = newStructureElementId;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setAuthUserId(Integer newAuthUserId) {
      if(this.mAuthUserId != null && newAuthUserId == null || this.mAuthUserId == null && newAuthUserId != null || this.mAuthUserId != null && newAuthUserId != null && !this.mAuthUserId.equals(newAuthUserId)) {
         this.mAuthUserId = newAuthUserId;
         this.mModified = true;
      }

   }

   public void setNotes(String newNotes) {
      if(this.mNotes != null && newNotes == null || this.mNotes == null && newNotes != null || this.mNotes != null && newNotes != null && !this.mNotes.equals(newNotes)) {
         this.mNotes = newNotes;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(VirementAuthPointEVO newDetails) {
      this.setAuthPointId(newDetails.getAuthPointId());
      this.setRequestId(newDetails.getRequestId());
      this.setPointStatus(newDetails.getPointStatus());
      this.setAuthUserId(newDetails.getAuthUserId());
      this.setNotes(newDetails.getNotes());
      this.setStructureElementId(newDetails.getStructureElementId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public VirementAuthPointEVO deepClone() {
      VirementAuthPointEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (VirementAuthPointEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(VirementRequestEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mAuthPointId > 0) {
         newKey = true;
         if(parent == null) {
            this.setAuthPointId(-this.mAuthPointId);
         } else {
            parent.changeKey(this, -this.mAuthPointId);
         }
      } else if(this.mAuthPointId < 1) {
         newKey = true;
      }

      Iterator iter;
      VirementAuthorisersEVO item;
      if(this.mAuthUsers != null) {
         for(iter = (new ArrayList(this.mAuthUsers.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (VirementAuthorisersEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      VirementAuthPointLinkEVO item1;
      if(this.mAuthPointLinks != null) {
         for(iter = (new ArrayList(this.mAuthPointLinks.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (VirementAuthPointLinkEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mAuthPointId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      VirementAuthorisersEVO item;
      if(this.mAuthUsers != null) {
         for(iter = this.mAuthUsers.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (VirementAuthorisersEVO)iter.next();
         }
      }

      VirementAuthPointLinkEVO item1;
      if(this.mAuthPointLinks != null) {
         for(iter = this.mAuthPointLinks.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (VirementAuthPointLinkEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(VirementRequestEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mAuthPointId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      Iterator iter;
      VirementAuthorisersEVO item;
      if(this.mAuthUsers != null) {
         for(iter = (new ArrayList(this.mAuthUsers.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (VirementAuthorisersEVO)iter.next();
            this.changeKey(item, this.mAuthPointId, item.getUserId());
         }
      }

      VirementAuthPointLinkEVO item1;
      if(this.mAuthPointLinks != null) {
         for(iter = (new ArrayList(this.mAuthPointLinks.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (VirementAuthPointLinkEVO)iter.next();
            this.changeKey(item1, this.mAuthPointId, item1.getVirementLineId());
         }
      }

      return nextKey;
   }

   public Collection<VirementAuthorisersEVO> getAuthUsers() {
      return this.mAuthUsers != null?this.mAuthUsers.values():null;
   }

   public Map<VirementAuthorisersPK, VirementAuthorisersEVO> getAuthUsersMap() {
      return this.mAuthUsers;
   }

   public void loadAuthUsersItem(VirementAuthorisersEVO newItem) {
      if(this.mAuthUsers == null) {
         this.mAuthUsers = new HashMap();
      }

      this.mAuthUsers.put(newItem.getPK(), newItem);
   }

   public void addAuthUsersItem(VirementAuthorisersEVO newItem) {
      if(this.mAuthUsers == null) {
         this.mAuthUsers = new HashMap();
      }

      VirementAuthorisersPK newPK = newItem.getPK();
      if(this.getAuthUsersItem(newPK) != null) {
         throw new RuntimeException("addAuthUsersItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mAuthUsers.put(newPK, newItem);
      }
   }

   public void changeAuthUsersItem(VirementAuthorisersEVO changedItem) {
      if(this.mAuthUsers == null) {
         throw new RuntimeException("changeAuthUsersItem: no items in collection");
      } else {
         VirementAuthorisersPK changedPK = changedItem.getPK();
         VirementAuthorisersEVO listItem = this.getAuthUsersItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeAuthUsersItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteAuthUsersItem(VirementAuthorisersPK removePK) {
      VirementAuthorisersEVO listItem = this.getAuthUsersItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeAuthUsersItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public VirementAuthorisersEVO getAuthUsersItem(VirementAuthorisersPK pk) {
      return (VirementAuthorisersEVO)this.mAuthUsers.get(pk);
   }

   public VirementAuthorisersEVO getAuthUsersItem() {
      if(this.mAuthUsers.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mAuthUsers.size());
      } else {
         Iterator iter = this.mAuthUsers.values().iterator();
         return (VirementAuthorisersEVO)iter.next();
      }
   }

   public Collection<VirementAuthPointLinkEVO> getAuthPointLinks() {
      return this.mAuthPointLinks != null?this.mAuthPointLinks.values():null;
   }

   public Map<VirementAuthPointLinkPK, VirementAuthPointLinkEVO> getAuthPointLinksMap() {
      return this.mAuthPointLinks;
   }

   public void loadAuthPointLinksItem(VirementAuthPointLinkEVO newItem) {
      if(this.mAuthPointLinks == null) {
         this.mAuthPointLinks = new HashMap();
      }

      this.mAuthPointLinks.put(newItem.getPK(), newItem);
   }

   public void addAuthPointLinksItem(VirementAuthPointLinkEVO newItem) {
      if(this.mAuthPointLinks == null) {
         this.mAuthPointLinks = new HashMap();
      }

      VirementAuthPointLinkPK newPK = newItem.getPK();
      if(this.getAuthPointLinksItem(newPK) != null) {
         throw new RuntimeException("addAuthPointLinksItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mAuthPointLinks.put(newPK, newItem);
      }
   }

   public void changeAuthPointLinksItem(VirementAuthPointLinkEVO changedItem) {
      if(this.mAuthPointLinks == null) {
         throw new RuntimeException("changeAuthPointLinksItem: no items in collection");
      } else {
         VirementAuthPointLinkPK changedPK = changedItem.getPK();
         VirementAuthPointLinkEVO listItem = this.getAuthPointLinksItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeAuthPointLinksItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteAuthPointLinksItem(VirementAuthPointLinkPK removePK) {
      VirementAuthPointLinkEVO listItem = this.getAuthPointLinksItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeAuthPointLinksItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public VirementAuthPointLinkEVO getAuthPointLinksItem(VirementAuthPointLinkPK pk) {
      return (VirementAuthPointLinkEVO)this.mAuthPointLinks.get(pk);
   }

   public VirementAuthPointLinkEVO getAuthPointLinksItem() {
      if(this.mAuthPointLinks.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mAuthPointLinks.size());
      } else {
         Iterator iter = this.mAuthPointLinks.values().iterator();
         return (VirementAuthPointLinkEVO)iter.next();
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

   public VirementAuthPointRef getEntityRef(ModelEVO evoModel, VirementRequestEVO evoVirementRequest, String entityText) {
      return new VirementAuthPointRefImpl(new VirementAuthPointCK(evoModel.getPK(), evoVirementRequest.getPK(), this.getPK()), entityText);
   }

   public VirementAuthPointCK getCK(ModelEVO evoModel, VirementRequestEVO evoVirementRequest) {
      return new VirementAuthPointCK(evoModel.getPK(), evoVirementRequest.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mAuthUsersAllItemsLoaded = true;
      if(this.mAuthUsers == null) {
         this.mAuthUsers = new HashMap();
      }

      this.mAuthPointLinksAllItemsLoaded = true;
      if(this.mAuthPointLinks == null) {
         this.mAuthPointLinks = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("AuthPointId=");
      sb.append(String.valueOf(this.mAuthPointId));
      sb.append(' ');
      sb.append("RequestId=");
      sb.append(String.valueOf(this.mRequestId));
      sb.append(' ');
      sb.append("PointStatus=");
      sb.append(String.valueOf(this.mPointStatus));
      sb.append(' ');
      sb.append("AuthUserId=");
      sb.append(String.valueOf(this.mAuthUserId));
      sb.append(' ');
      sb.append("Notes=");
      sb.append(String.valueOf(this.mNotes));
      sb.append(' ');
      sb.append("StructureElementId=");
      sb.append(String.valueOf(this.mStructureElementId));
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

      sb.append("VirementAuthPoint: ");
      sb.append(this.toString());
      if(this.mAuthUsersAllItemsLoaded || this.mAuthUsers != null) {
         sb.delete(indent, sb.length());
         sb.append(" - AuthUsers: allItemsLoaded=");
         sb.append(String.valueOf(this.mAuthUsersAllItemsLoaded));
         sb.append(" items=");
         if(this.mAuthUsers == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mAuthUsers.size()));
         }
      }

      if(this.mAuthPointLinksAllItemsLoaded || this.mAuthPointLinks != null) {
         sb.delete(indent, sb.length());
         sb.append(" - AuthPointLinks: allItemsLoaded=");
         sb.append(String.valueOf(this.mAuthPointLinksAllItemsLoaded));
         sb.append(" items=");
         if(this.mAuthPointLinks == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mAuthPointLinks.size()));
         }
      }

      Iterator var5;
      if(this.mAuthUsers != null) {
         var5 = this.mAuthUsers.values().iterator();

         while(var5.hasNext()) {
            VirementAuthorisersEVO listItem = (VirementAuthorisersEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mAuthPointLinks != null) {
         var5 = this.mAuthPointLinks.values().iterator();

         while(var5.hasNext()) {
            VirementAuthPointLinkEVO var6 = (VirementAuthPointLinkEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(VirementAuthorisersEVO child, int newAuthPointId, int newUserId) {
      if(this.getAuthUsersItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mAuthUsers.remove(child.getPK());
         child.setAuthPointId(newAuthPointId);
         child.setUserId(newUserId);
         this.mAuthUsers.put(child.getPK(), child);
      }
   }

   public void changeKey(VirementAuthPointLinkEVO child, int newAuthPointId, int newVirementLineId) {
      if(this.getAuthPointLinksItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mAuthPointLinks.remove(child.getPK());
         child.setAuthPointId(newAuthPointId);
         child.setVirementLineId(newVirementLineId);
         this.mAuthPointLinks.put(child.getPK(), child);
      }
   }

   public void setAuthUsersAllItemsLoaded(boolean allItemsLoaded) {
      this.mAuthUsersAllItemsLoaded = allItemsLoaded;
   }

   public boolean isAuthUsersAllItemsLoaded() {
      return this.mAuthUsersAllItemsLoaded;
   }

   public void setAuthPointLinksAllItemsLoaded(boolean allItemsLoaded) {
      this.mAuthPointLinksAllItemsLoaded = allItemsLoaded;
   }

   public boolean isAuthPointLinksAllItemsLoaded() {
      return this.mAuthPointLinksAllItemsLoaded;
   }
}
