// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.task;

import com.cedar.cp.api.report.task.ReportGroupingRef;
import com.cedar.cp.dto.report.task.ReportGroupingFilePK;
import com.cedar.cp.dto.report.task.ReportGroupingPK;
import com.cedar.cp.dto.report.task.ReportGroupingRefImpl;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingFileEVO;
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

public class ReportGroupingEVO implements Serializable {

   private transient ReportGroupingPK mPK;
   private int mReportGroupingId;
   private int mParentTaskId;
   private int mTaskId;
   private int mDistributionType;
   private int mMessageType;
   private String mMessageId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<ReportGroupingFilePK, ReportGroupingFileEVO> mReportGroupFiles;
   protected boolean mReportGroupFilesAllItemsLoaded;
   private boolean mModified;


   public ReportGroupingEVO() {}

   public ReportGroupingEVO(int newReportGroupingId, int newParentTaskId, int newTaskId, int newDistributionType, int newMessageType, String newMessageId, Collection newReportGroupFiles) {
      this.mReportGroupingId = newReportGroupingId;
      this.mParentTaskId = newParentTaskId;
      this.mTaskId = newTaskId;
      this.mDistributionType = newDistributionType;
      this.mMessageType = newMessageType;
      this.mMessageId = newMessageId;
      this.setReportGroupFiles(newReportGroupFiles);
   }

   public void setReportGroupFiles(Collection<ReportGroupingFileEVO> items) {
      if(items != null) {
         if(this.mReportGroupFiles == null) {
            this.mReportGroupFiles = new HashMap();
         } else {
            this.mReportGroupFiles.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ReportGroupingFileEVO child = (ReportGroupingFileEVO)i$.next();
            this.mReportGroupFiles.put(child.getPK(), child);
         }
      } else {
         this.mReportGroupFiles = null;
      }

   }

   public ReportGroupingPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ReportGroupingPK(this.mReportGroupingId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getReportGroupingId() {
      return this.mReportGroupingId;
   }

   public int getParentTaskId() {
      return this.mParentTaskId;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public int getDistributionType() {
      return this.mDistributionType;
   }

   public int getMessageType() {
      return this.mMessageType;
   }

   public String getMessageId() {
      return this.mMessageId;
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

   public void setReportGroupingId(int newReportGroupingId) {
      if(this.mReportGroupingId != newReportGroupingId) {
         this.mModified = true;
         this.mReportGroupingId = newReportGroupingId;
         this.mPK = null;
      }
   }

   public void setParentTaskId(int newParentTaskId) {
      if(this.mParentTaskId != newParentTaskId) {
         this.mModified = true;
         this.mParentTaskId = newParentTaskId;
      }
   }

   public void setTaskId(int newTaskId) {
      if(this.mTaskId != newTaskId) {
         this.mModified = true;
         this.mTaskId = newTaskId;
      }
   }

   public void setDistributionType(int newDistributionType) {
      if(this.mDistributionType != newDistributionType) {
         this.mModified = true;
         this.mDistributionType = newDistributionType;
      }
   }

   public void setMessageType(int newMessageType) {
      if(this.mMessageType != newMessageType) {
         this.mModified = true;
         this.mMessageType = newMessageType;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setMessageId(String newMessageId) {
      if(this.mMessageId != null && newMessageId == null || this.mMessageId == null && newMessageId != null || this.mMessageId != null && newMessageId != null && !this.mMessageId.equals(newMessageId)) {
         this.mMessageId = newMessageId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ReportGroupingEVO newDetails) {
      this.setReportGroupingId(newDetails.getReportGroupingId());
      this.setParentTaskId(newDetails.getParentTaskId());
      this.setTaskId(newDetails.getTaskId());
      this.setDistributionType(newDetails.getDistributionType());
      this.setMessageType(newDetails.getMessageType());
      this.setMessageId(newDetails.getMessageId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ReportGroupingEVO deepClone() {
      ReportGroupingEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ReportGroupingEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mReportGroupingId > 0) {
         newKey = true;
         this.mReportGroupingId = 0;
      } else if(this.mReportGroupingId < 1) {
         newKey = true;
      }

      ReportGroupingFileEVO item;
      if(this.mReportGroupFiles != null) {
         for(Iterator iter = (new ArrayList(this.mReportGroupFiles.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ReportGroupingFileEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mReportGroupingId < 1) {
         returnCount = startCount + 1;
      }

      ReportGroupingFileEVO item;
      if(this.mReportGroupFiles != null) {
         for(Iterator iter = this.mReportGroupFiles.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ReportGroupingFileEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mReportGroupingId < 1) {
         this.mReportGroupingId = startKey;
         nextKey = startKey + 1;
      }

      ReportGroupingFileEVO item;
      if(this.mReportGroupFiles != null) {
         for(Iterator iter = (new ArrayList(this.mReportGroupFiles.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ReportGroupingFileEVO)iter.next();
            this.changeKey(item, this.mReportGroupingId, item.getReportGroupingFileId());
         }
      }

      return nextKey;
   }

   public Collection<ReportGroupingFileEVO> getReportGroupFiles() {
      return this.mReportGroupFiles != null?this.mReportGroupFiles.values():null;
   }

   public Map<ReportGroupingFilePK, ReportGroupingFileEVO> getReportGroupFilesMap() {
      return this.mReportGroupFiles;
   }

   public void loadReportGroupFilesItem(ReportGroupingFileEVO newItem) {
      if(this.mReportGroupFiles == null) {
         this.mReportGroupFiles = new HashMap();
      }

      this.mReportGroupFiles.put(newItem.getPK(), newItem);
   }

   public void addReportGroupFilesItem(ReportGroupingFileEVO newItem) {
      if(this.mReportGroupFiles == null) {
         this.mReportGroupFiles = new HashMap();
      }

      ReportGroupingFilePK newPK = newItem.getPK();
      if(this.getReportGroupFilesItem(newPK) != null) {
         throw new RuntimeException("addReportGroupFilesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mReportGroupFiles.put(newPK, newItem);
      }
   }

   public void changeReportGroupFilesItem(ReportGroupingFileEVO changedItem) {
      if(this.mReportGroupFiles == null) {
         throw new RuntimeException("changeReportGroupFilesItem: no items in collection");
      } else {
         ReportGroupingFilePK changedPK = changedItem.getPK();
         ReportGroupingFileEVO listItem = this.getReportGroupFilesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeReportGroupFilesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteReportGroupFilesItem(ReportGroupingFilePK removePK) {
      ReportGroupingFileEVO listItem = this.getReportGroupFilesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeReportGroupFilesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ReportGroupingFileEVO getReportGroupFilesItem(ReportGroupingFilePK pk) {
      return (ReportGroupingFileEVO)this.mReportGroupFiles.get(pk);
   }

   public ReportGroupingFileEVO getReportGroupFilesItem() {
      if(this.mReportGroupFiles.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mReportGroupFiles.size());
      } else {
         Iterator iter = this.mReportGroupFiles.values().iterator();
         return (ReportGroupingFileEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public ReportGroupingRef getEntityRef(String entityText) {
      return new ReportGroupingRefImpl(this.getPK(), entityText);
   }

   public void postCreateInit() {
      this.mReportGroupFilesAllItemsLoaded = true;
      if(this.mReportGroupFiles == null) {
         this.mReportGroupFiles = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ReportGroupingId=");
      sb.append(String.valueOf(this.mReportGroupingId));
      sb.append(' ');
      sb.append("ParentTaskId=");
      sb.append(String.valueOf(this.mParentTaskId));
      sb.append(' ');
      sb.append("TaskId=");
      sb.append(String.valueOf(this.mTaskId));
      sb.append(' ');
      sb.append("DistributionType=");
      sb.append(String.valueOf(this.mDistributionType));
      sb.append(' ');
      sb.append("MessageType=");
      sb.append(String.valueOf(this.mMessageType));
      sb.append(' ');
      sb.append("MessageId=");
      sb.append(String.valueOf(this.mMessageId));
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

      sb.append("ReportGrouping: ");
      sb.append(this.toString());
      if(this.mReportGroupFilesAllItemsLoaded || this.mReportGroupFiles != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ReportGroupFiles: allItemsLoaded=");
         sb.append(String.valueOf(this.mReportGroupFilesAllItemsLoaded));
         sb.append(" items=");
         if(this.mReportGroupFiles == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mReportGroupFiles.size()));
         }
      }

      if(this.mReportGroupFiles != null) {
         Iterator var5 = this.mReportGroupFiles.values().iterator();

         while(var5.hasNext()) {
            ReportGroupingFileEVO listItem = (ReportGroupingFileEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ReportGroupingFileEVO child, int newReportGroupingId, int newReportGroupingFileId) {
      if(this.getReportGroupFilesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mReportGroupFiles.remove(child.getPK());
         child.setReportGroupingId(newReportGroupingId);
         child.setReportGroupingFileId(newReportGroupingFileId);
         this.mReportGroupFiles.put(child.getPK(), child);
      }
   }

   public void setReportGroupFilesAllItemsLoaded(boolean allItemsLoaded) {
      this.mReportGroupFilesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isReportGroupFilesAllItemsLoaded() {
      return this.mReportGroupFilesAllItemsLoaded;
   }
}
