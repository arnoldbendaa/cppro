// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.budgetinstruction;

import com.cedar.cp.api.budgetinstruction.BudgetInstructionRef;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentPK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionRefImpl;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionAssignmentEVO;
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

public class BudgetInstructionEVO implements Serializable {

   private transient BudgetInstructionPK mPK;
   private int mBudgetInstructionId;
   private String mVisId;
   private String mDocumentRef;
   private byte[] mDocument;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<BudgetInstructionAssignmentPK, BudgetInstructionAssignmentEVO> mBudgetInstructionAssignments;
   protected boolean mBudgetInstructionAssignmentsAllItemsLoaded;
   private boolean mModified;


   public BudgetInstructionEVO() {}

   public BudgetInstructionEVO(int newBudgetInstructionId, String newVisId, String newDocumentRef, byte[] newDocument, int newVersionNum, Collection newBudgetInstructionAssignments) {
      this.mBudgetInstructionId = newBudgetInstructionId;
      this.mVisId = newVisId;
      this.mDocumentRef = newDocumentRef;
      this.mDocument = newDocument;
      this.mVersionNum = newVersionNum;
      this.setBudgetInstructionAssignments(newBudgetInstructionAssignments);
   }

   public void setBudgetInstructionAssignments(Collection<BudgetInstructionAssignmentEVO> items) {
      if(items != null) {
         if(this.mBudgetInstructionAssignments == null) {
            this.mBudgetInstructionAssignments = new HashMap();
         } else {
            this.mBudgetInstructionAssignments.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            BudgetInstructionAssignmentEVO child = (BudgetInstructionAssignmentEVO)i$.next();
            this.mBudgetInstructionAssignments.put(child.getPK(), child);
         }
      } else {
         this.mBudgetInstructionAssignments = null;
      }

   }

   public BudgetInstructionPK getPK() {
      if(this.mPK == null) {
         this.mPK = new BudgetInstructionPK(this.mBudgetInstructionId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getBudgetInstructionId() {
      return this.mBudgetInstructionId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDocumentRef() {
      return this.mDocumentRef;
   }

   public byte[] getDocument() {
      return this.mDocument;
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

   public void setBudgetInstructionId(int newBudgetInstructionId) {
      if(this.mBudgetInstructionId != newBudgetInstructionId) {
         this.mModified = true;
         this.mBudgetInstructionId = newBudgetInstructionId;
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

   public void setDocumentRef(String newDocumentRef) {
      if(this.mDocumentRef != null && newDocumentRef == null || this.mDocumentRef == null && newDocumentRef != null || this.mDocumentRef != null && newDocumentRef != null && !this.mDocumentRef.equals(newDocumentRef)) {
         this.mDocumentRef = newDocumentRef;
         this.mModified = true;
      }

   }

   public void setDocument(byte[] newDocument) {
      if(this.mDocument != null && newDocument == null || this.mDocument == null && newDocument != null || this.mDocument != null && newDocument != null && !this.mDocument.equals(newDocument)) {
         this.mDocument = newDocument;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(BudgetInstructionEVO newDetails) {
      this.setBudgetInstructionId(newDetails.getBudgetInstructionId());
      this.setVisId(newDetails.getVisId());
      this.setDocumentRef(newDetails.getDocumentRef());
      this.setDocument(newDetails.getDocument());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public BudgetInstructionEVO deepClone() {
      BudgetInstructionEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (BudgetInstructionEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mBudgetInstructionId > 0) {
         newKey = true;
         this.mBudgetInstructionId = 0;
      } else if(this.mBudgetInstructionId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      BudgetInstructionAssignmentEVO item;
      if(this.mBudgetInstructionAssignments != null) {
         for(Iterator iter = (new ArrayList(this.mBudgetInstructionAssignments.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (BudgetInstructionAssignmentEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mBudgetInstructionId < 1) {
         returnCount = startCount + 1;
      }

      BudgetInstructionAssignmentEVO item;
      if(this.mBudgetInstructionAssignments != null) {
         for(Iterator iter = this.mBudgetInstructionAssignments.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (BudgetInstructionAssignmentEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mBudgetInstructionId < 1) {
         this.mBudgetInstructionId = startKey;
         nextKey = startKey + 1;
      }

      BudgetInstructionAssignmentEVO item;
      if(this.mBudgetInstructionAssignments != null) {
         for(Iterator iter = (new ArrayList(this.mBudgetInstructionAssignments.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (BudgetInstructionAssignmentEVO)iter.next();
            item.setBudgetInstructionId(this.mBudgetInstructionId);
         }
      }

      return nextKey;
   }

   public Collection<BudgetInstructionAssignmentEVO> getBudgetInstructionAssignments() {
      return this.mBudgetInstructionAssignments != null?this.mBudgetInstructionAssignments.values():null;
   }

   public Map<BudgetInstructionAssignmentPK, BudgetInstructionAssignmentEVO> getBudgetInstructionAssignmentsMap() {
      return this.mBudgetInstructionAssignments;
   }

   public void loadBudgetInstructionAssignmentsItem(BudgetInstructionAssignmentEVO newItem) {
      if(this.mBudgetInstructionAssignments == null) {
         this.mBudgetInstructionAssignments = new HashMap();
      }

      this.mBudgetInstructionAssignments.put(newItem.getPK(), newItem);
   }

   public void addBudgetInstructionAssignmentsItem(BudgetInstructionAssignmentEVO newItem) {
      if(this.mBudgetInstructionAssignments == null) {
         this.mBudgetInstructionAssignments = new HashMap();
      }

      BudgetInstructionAssignmentPK newPK = newItem.getPK();
      if(this.getBudgetInstructionAssignmentsItem(newPK) != null) {
         throw new RuntimeException("addBudgetInstructionAssignmentsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mBudgetInstructionAssignments.put(newPK, newItem);
      }
   }

   public void changeBudgetInstructionAssignmentsItem(BudgetInstructionAssignmentEVO changedItem) {
      if(this.mBudgetInstructionAssignments == null) {
         throw new RuntimeException("changeBudgetInstructionAssignmentsItem: no items in collection");
      } else {
         BudgetInstructionAssignmentPK changedPK = changedItem.getPK();
         BudgetInstructionAssignmentEVO listItem = this.getBudgetInstructionAssignmentsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeBudgetInstructionAssignmentsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteBudgetInstructionAssignmentsItem(BudgetInstructionAssignmentPK removePK) {
      BudgetInstructionAssignmentEVO listItem = this.getBudgetInstructionAssignmentsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeBudgetInstructionAssignmentsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public BudgetInstructionAssignmentEVO getBudgetInstructionAssignmentsItem(BudgetInstructionAssignmentPK pk) {
      return (BudgetInstructionAssignmentEVO)this.mBudgetInstructionAssignments.get(pk);
   }

   public BudgetInstructionAssignmentEVO getBudgetInstructionAssignmentsItem() {
      if(this.mBudgetInstructionAssignments.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mBudgetInstructionAssignments.size());
      } else {
         Iterator iter = this.mBudgetInstructionAssignments.values().iterator();
         return (BudgetInstructionAssignmentEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public BudgetInstructionRef getEntityRef() {
      return new BudgetInstructionRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mBudgetInstructionAssignmentsAllItemsLoaded = true;
      if(this.mBudgetInstructionAssignments == null) {
         this.mBudgetInstructionAssignments = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("BudgetInstructionId=");
      sb.append(String.valueOf(this.mBudgetInstructionId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("DocumentRef=");
      sb.append(String.valueOf(this.mDocumentRef));
      sb.append(' ');
      sb.append("Document=");
      sb.append(String.valueOf(this.mDocument));
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

      sb.append("BudgetInstruction: ");
      sb.append(this.toString());
      if(this.mBudgetInstructionAssignmentsAllItemsLoaded || this.mBudgetInstructionAssignments != null) {
         sb.delete(indent, sb.length());
         sb.append(" - BudgetInstructionAssignments: allItemsLoaded=");
         sb.append(String.valueOf(this.mBudgetInstructionAssignmentsAllItemsLoaded));
         sb.append(" items=");
         if(this.mBudgetInstructionAssignments == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mBudgetInstructionAssignments.size()));
         }
      }

      if(this.mBudgetInstructionAssignments != null) {
         Iterator var5 = this.mBudgetInstructionAssignments.values().iterator();

         while(var5.hasNext()) {
            BudgetInstructionAssignmentEVO listItem = (BudgetInstructionAssignmentEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(BudgetInstructionAssignmentEVO child, int newAssignmentId) {
      if(this.getBudgetInstructionAssignmentsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mBudgetInstructionAssignments.remove(child.getPK());
         child.setAssignmentId(newAssignmentId);
         this.mBudgetInstructionAssignments.put(child.getPK(), child);
      }
   }

   public void setBudgetInstructionAssignmentsAllItemsLoaded(boolean allItemsLoaded) {
      this.mBudgetInstructionAssignmentsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isBudgetInstructionAssignmentsAllItemsLoaded() {
      return this.mBudgetInstructionAssignmentsAllItemsLoaded;
   }
}
