// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.api.model.virement.VirementRequestGroupRef;
import com.cedar.cp.dto.model.virement.VirementRequestGroupCK;
import com.cedar.cp.dto.model.virement.VirementRequestGroupPK;
import com.cedar.cp.dto.model.virement.VirementRequestGroupRefImpl;
import com.cedar.cp.dto.model.virement.VirementRequestLinePK;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestLineEVO;
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

public class VirementRequestGroupEVO implements Serializable {

   private transient VirementRequestGroupPK mPK;
   private int mRequestGroupId;
   private int mRequestId;
   private String mNotes;
   private int mGroupIdx;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<VirementRequestLinePK, VirementRequestLineEVO> mLines;
   protected boolean mLinesAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public VirementRequestGroupEVO() {}

   public VirementRequestGroupEVO(int newRequestGroupId, int newRequestId, String newNotes, int newGroupIdx, Collection newLines) {
      this.mRequestGroupId = newRequestGroupId;
      this.mRequestId = newRequestId;
      this.mNotes = newNotes;
      this.mGroupIdx = newGroupIdx;
      this.setLines(newLines);
   }

   public void setLines(Collection<VirementRequestLineEVO> items) {
      if(items != null) {
         if(this.mLines == null) {
            this.mLines = new HashMap();
         } else {
            this.mLines.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            VirementRequestLineEVO child = (VirementRequestLineEVO)i$.next();
            this.mLines.put(child.getPK(), child);
         }
      } else {
         this.mLines = null;
      }

   }

   public VirementRequestGroupPK getPK() {
      if(this.mPK == null) {
         this.mPK = new VirementRequestGroupPK(this.mRequestGroupId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getRequestGroupId() {
      return this.mRequestGroupId;
   }

   public int getRequestId() {
      return this.mRequestId;
   }

   public String getNotes() {
      return this.mNotes;
   }

   public int getGroupIdx() {
      return this.mGroupIdx;
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

   public void setRequestGroupId(int newRequestGroupId) {
      if(this.mRequestGroupId != newRequestGroupId) {
         this.mModified = true;
         this.mRequestGroupId = newRequestGroupId;
         this.mPK = null;
      }
   }

   public void setRequestId(int newRequestId) {
      if(this.mRequestId != newRequestId) {
         this.mModified = true;
         this.mRequestId = newRequestId;
      }
   }

   public void setGroupIdx(int newGroupIdx) {
      if(this.mGroupIdx != newGroupIdx) {
         this.mModified = true;
         this.mGroupIdx = newGroupIdx;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
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

   public void setDetails(VirementRequestGroupEVO newDetails) {
      this.setRequestGroupId(newDetails.getRequestGroupId());
      this.setRequestId(newDetails.getRequestId());
      this.setNotes(newDetails.getNotes());
      this.setGroupIdx(newDetails.getGroupIdx());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public VirementRequestGroupEVO deepClone() {
      VirementRequestGroupEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (VirementRequestGroupEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(VirementRequestEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mRequestGroupId > 0) {
         newKey = true;
         if(parent == null) {
            this.setRequestGroupId(-this.mRequestGroupId);
         } else {
            parent.changeKey(this, -this.mRequestGroupId);
         }
      } else if(this.mRequestGroupId < 1) {
         newKey = true;
      }

      VirementRequestLineEVO item;
      if(this.mLines != null) {
         for(Iterator iter = (new ArrayList(this.mLines.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (VirementRequestLineEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mRequestGroupId < 1) {
         returnCount = startCount + 1;
      }

      VirementRequestLineEVO item;
      if(this.mLines != null) {
         for(Iterator iter = this.mLines.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (VirementRequestLineEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(VirementRequestEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mRequestGroupId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      VirementRequestLineEVO item;
      if(this.mLines != null) {
         for(Iterator iter = (new ArrayList(this.mLines.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (VirementRequestLineEVO)iter.next();
            item.setRequestGroupId(this.mRequestGroupId);
         }
      }

      return nextKey;
   }

   public Collection<VirementRequestLineEVO> getLines() {
      return this.mLines != null?this.mLines.values():null;
   }

   public Map<VirementRequestLinePK, VirementRequestLineEVO> getLinesMap() {
      return this.mLines;
   }

   public void loadLinesItem(VirementRequestLineEVO newItem) {
      if(this.mLines == null) {
         this.mLines = new HashMap();
      }

      this.mLines.put(newItem.getPK(), newItem);
   }

   public void addLinesItem(VirementRequestLineEVO newItem) {
      if(this.mLines == null) {
         this.mLines = new HashMap();
      }

      VirementRequestLinePK newPK = newItem.getPK();
      if(this.getLinesItem(newPK) != null) {
         throw new RuntimeException("addLinesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mLines.put(newPK, newItem);
      }
   }

   public void changeLinesItem(VirementRequestLineEVO changedItem) {
      if(this.mLines == null) {
         throw new RuntimeException("changeLinesItem: no items in collection");
      } else {
         VirementRequestLinePK changedPK = changedItem.getPK();
         VirementRequestLineEVO listItem = this.getLinesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeLinesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteLinesItem(VirementRequestLinePK removePK) {
      VirementRequestLineEVO listItem = this.getLinesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeLinesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public VirementRequestLineEVO getLinesItem(VirementRequestLinePK pk) {
      return (VirementRequestLineEVO)this.mLines.get(pk);
   }

   public VirementRequestLineEVO getLinesItem() {
      if(this.mLines.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mLines.size());
      } else {
         Iterator iter = this.mLines.values().iterator();
         return (VirementRequestLineEVO)iter.next();
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

   public VirementRequestGroupRef getEntityRef(ModelEVO evoModel, VirementRequestEVO evoVirementRequest, String entityText) {
      return new VirementRequestGroupRefImpl(new VirementRequestGroupCK(evoModel.getPK(), evoVirementRequest.getPK(), this.getPK()), entityText);
   }

   public VirementRequestGroupCK getCK(ModelEVO evoModel, VirementRequestEVO evoVirementRequest) {
      return new VirementRequestGroupCK(evoModel.getPK(), evoVirementRequest.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mLinesAllItemsLoaded = true;
      if(this.mLines == null) {
         this.mLines = new HashMap();
      } else {
         Iterator i$ = this.mLines.values().iterator();

         while(i$.hasNext()) {
            VirementRequestLineEVO child = (VirementRequestLineEVO)i$.next();
            child.postCreateInit();
         }
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RequestGroupId=");
      sb.append(String.valueOf(this.mRequestGroupId));
      sb.append(' ');
      sb.append("RequestId=");
      sb.append(String.valueOf(this.mRequestId));
      sb.append(' ');
      sb.append("Notes=");
      sb.append(String.valueOf(this.mNotes));
      sb.append(' ');
      sb.append("GroupIdx=");
      sb.append(String.valueOf(this.mGroupIdx));
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

      sb.append("VirementRequestGroup: ");
      sb.append(this.toString());
      if(this.mLinesAllItemsLoaded || this.mLines != null) {
         sb.delete(indent, sb.length());
         sb.append(" - Lines: allItemsLoaded=");
         sb.append(String.valueOf(this.mLinesAllItemsLoaded));
         sb.append(" items=");
         if(this.mLines == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mLines.size()));
         }
      }

      if(this.mLines != null) {
         Iterator var5 = this.mLines.values().iterator();

         while(var5.hasNext()) {
            VirementRequestLineEVO listItem = (VirementRequestLineEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(VirementRequestLineEVO child, int newRequestLineId) {
      if(this.getLinesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mLines.remove(child.getPK());
         child.setRequestLineId(newRequestLineId);
         this.mLines.put(child.getPK(), child);
      }
   }

   public void setLinesAllItemsLoaded(boolean allItemsLoaded) {
      this.mLinesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isLinesAllItemsLoaded() {
      return this.mLinesAllItemsLoaded;
   }
}
