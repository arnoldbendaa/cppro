// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report;

import com.cedar.cp.api.report.ReportRef;
import com.cedar.cp.dto.report.ReportPK;
import com.cedar.cp.dto.report.ReportRefImpl;
import com.cedar.cp.dto.report.tran.CubePendingTranPK;
import com.cedar.cp.ejb.impl.report.tran.CubePendingTranEVO;
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

public class ReportEVO implements Serializable {

   private transient ReportPK mPK;
   private int mReportId;
   private int mUserId;
   private int mReportType;
   private int mTaskId;
   private boolean mComplete;
   private String mReportText;
   private boolean mHasUpdates;
   private boolean mUpdatesApplied;
   private Integer mUpdateTaskId;
   private Integer mBudgetCycleId;
   private Integer mActivityType;
   private String mActivityDetail;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<CubePendingTranPK, CubePendingTranEVO> mCUBE_PENDING_TRAN;
   protected boolean mCUBE_PENDING_TRANAllItemsLoaded;
   private boolean mModified;
   public static final int COST_ALLOCATION = 1;
   public static final int TOP_DOWN_BUDGETING = 2;
   public static final int VIREMENT_TRANSFER = 3;
   public static final int IMPORT_DIMENSIONS = 4;
   public static final int CHANGE_MANAGEMENT = 5;
   public static final int CUBE_IMPORT = 6;
   public static final int CELL_CALC_REBUILD = 7;
   public static final int TIDY_DB = 8;
   public static final int REPORT_UPDATE = 9;
   public static final int EXTERNAL_SYSTEM_IMPORT = 10;
   public static final int E5_DB2_PUSH_TASK = 11;
   public static final int CUBE_FORMULA_REBUILD = 12;
   public static final int CELL_CALC_IMPORT = 13;
   public static final int DYNAMIC_CELL_CALC_IMPORT = 14;


   public ReportEVO() {}

   public ReportEVO(int newReportId, int newUserId, int newReportType, int newTaskId, boolean newComplete, String newReportText, boolean newHasUpdates, boolean newUpdatesApplied, Integer newUpdateTaskId, Integer newBudgetCycleId, Integer newActivityType, String newActivityDetail, int newVersionNum, Collection newCUBE_PENDING_TRAN) {
      this.mReportId = newReportId;
      this.mUserId = newUserId;
      this.mReportType = newReportType;
      this.mTaskId = newTaskId;
      this.mComplete = newComplete;
      this.mReportText = newReportText;
      this.mHasUpdates = newHasUpdates;
      this.mUpdatesApplied = newUpdatesApplied;
      this.mUpdateTaskId = newUpdateTaskId;
      this.mBudgetCycleId = newBudgetCycleId;
      this.mActivityType = newActivityType;
      this.mActivityDetail = newActivityDetail;
      this.mVersionNum = newVersionNum;
      this.setCUBE_PENDING_TRAN(newCUBE_PENDING_TRAN);
   }

   public void setCUBE_PENDING_TRAN(Collection<CubePendingTranEVO> items) {
      if(items != null) {
         if(this.mCUBE_PENDING_TRAN == null) {
            this.mCUBE_PENDING_TRAN = new HashMap();
         } else {
            this.mCUBE_PENDING_TRAN.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CubePendingTranEVO child = (CubePendingTranEVO)i$.next();
            this.mCUBE_PENDING_TRAN.put(child.getPK(), child);
         }
      } else {
         this.mCUBE_PENDING_TRAN = null;
      }

   }

   public ReportPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ReportPK(this.mReportId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getReportId() {
      return this.mReportId;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public int getReportType() {
      return this.mReportType;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public boolean getComplete() {
      return this.mComplete;
   }

   public String getReportText() {
      return this.mReportText;
   }

   public boolean getHasUpdates() {
      return this.mHasUpdates;
   }

   public boolean getUpdatesApplied() {
      return this.mUpdatesApplied;
   }

   public Integer getUpdateTaskId() {
      return this.mUpdateTaskId;
   }

   public Integer getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public Integer getActivityType() {
      return this.mActivityType;
   }

   public String getActivityDetail() {
      return this.mActivityDetail;
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

   public void setReportId(int newReportId) {
      if(this.mReportId != newReportId) {
         this.mModified = true;
         this.mReportId = newReportId;
         this.mPK = null;
      }
   }

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
      }
   }

   public void setReportType(int newReportType) {
      if(this.mReportType != newReportType) {
         this.mModified = true;
         this.mReportType = newReportType;
      }
   }

   public void setTaskId(int newTaskId) {
      if(this.mTaskId != newTaskId) {
         this.mModified = true;
         this.mTaskId = newTaskId;
      }
   }

   public void setComplete(boolean newComplete) {
      if(this.mComplete != newComplete) {
         this.mModified = true;
         this.mComplete = newComplete;
      }
   }

   public void setHasUpdates(boolean newHasUpdates) {
      if(this.mHasUpdates != newHasUpdates) {
         this.mModified = true;
         this.mHasUpdates = newHasUpdates;
      }
   }

   public void setUpdatesApplied(boolean newUpdatesApplied) {
      if(this.mUpdatesApplied != newUpdatesApplied) {
         this.mModified = true;
         this.mUpdatesApplied = newUpdatesApplied;
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

   public void setReportText(String newReportText) {
      if(this.mReportText != null && newReportText == null || this.mReportText == null && newReportText != null || this.mReportText != null && newReportText != null && !this.mReportText.equals(newReportText)) {
         this.mReportText = newReportText;
         this.mModified = true;
      }

   }

   public void setUpdateTaskId(Integer newUpdateTaskId) {
      if(this.mUpdateTaskId != null && newUpdateTaskId == null || this.mUpdateTaskId == null && newUpdateTaskId != null || this.mUpdateTaskId != null && newUpdateTaskId != null && !this.mUpdateTaskId.equals(newUpdateTaskId)) {
         this.mUpdateTaskId = newUpdateTaskId;
         this.mModified = true;
      }

   }

   public void setBudgetCycleId(Integer newBudgetCycleId) {
      if(this.mBudgetCycleId != null && newBudgetCycleId == null || this.mBudgetCycleId == null && newBudgetCycleId != null || this.mBudgetCycleId != null && newBudgetCycleId != null && !this.mBudgetCycleId.equals(newBudgetCycleId)) {
         this.mBudgetCycleId = newBudgetCycleId;
         this.mModified = true;
      }

   }

   public void setActivityType(Integer newActivityType) {
      if(this.mActivityType != null && newActivityType == null || this.mActivityType == null && newActivityType != null || this.mActivityType != null && newActivityType != null && !this.mActivityType.equals(newActivityType)) {
         this.mActivityType = newActivityType;
         this.mModified = true;
      }

   }

   public void setActivityDetail(String newActivityDetail) {
      if(this.mActivityDetail != null && newActivityDetail == null || this.mActivityDetail == null && newActivityDetail != null || this.mActivityDetail != null && newActivityDetail != null && !this.mActivityDetail.equals(newActivityDetail)) {
         this.mActivityDetail = newActivityDetail;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ReportEVO newDetails) {
      this.setReportId(newDetails.getReportId());
      this.setUserId(newDetails.getUserId());
      this.setReportType(newDetails.getReportType());
      this.setTaskId(newDetails.getTaskId());
      this.setComplete(newDetails.getComplete());
      this.setReportText(newDetails.getReportText());
      this.setHasUpdates(newDetails.getHasUpdates());
      this.setUpdatesApplied(newDetails.getUpdatesApplied());
      this.setUpdateTaskId(newDetails.getUpdateTaskId());
      this.setBudgetCycleId(newDetails.getBudgetCycleId());
      this.setActivityType(newDetails.getActivityType());
      this.setActivityDetail(newDetails.getActivityDetail());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ReportEVO deepClone() {
      ReportEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ReportEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mReportId > 0) {
         newKey = true;
         this.mReportId = 0;
      } else if(this.mReportId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      CubePendingTranEVO item;
      if(this.mCUBE_PENDING_TRAN != null) {
         for(Iterator iter = (new ArrayList(this.mCUBE_PENDING_TRAN.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (CubePendingTranEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mReportId < 1) {
         returnCount = startCount + 1;
      }

      CubePendingTranEVO item;
      if(this.mCUBE_PENDING_TRAN != null) {
         for(Iterator iter = this.mCUBE_PENDING_TRAN.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (CubePendingTranEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mReportId < 1) {
         this.mReportId = startKey;
         nextKey = startKey + 1;
      }

      CubePendingTranEVO item;
      if(this.mCUBE_PENDING_TRAN != null) {
         for(Iterator iter = (new ArrayList(this.mCUBE_PENDING_TRAN.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (CubePendingTranEVO)iter.next();
            this.changeKey(item, this.mReportId, item.getFinanceCubeId(), item.getRowNo());
         }
      }

      return nextKey;
   }

   public Collection<CubePendingTranEVO> getCUBE_PENDING_TRAN() {
      return this.mCUBE_PENDING_TRAN != null?this.mCUBE_PENDING_TRAN.values():null;
   }

   public Map<CubePendingTranPK, CubePendingTranEVO> getCUBE_PENDING_TRANMap() {
      return this.mCUBE_PENDING_TRAN;
   }

   public void loadCUBE_PENDING_TRANItem(CubePendingTranEVO newItem) {
      if(this.mCUBE_PENDING_TRAN == null) {
         this.mCUBE_PENDING_TRAN = new HashMap();
      }

      this.mCUBE_PENDING_TRAN.put(newItem.getPK(), newItem);
   }

   public void addCUBE_PENDING_TRANItem(CubePendingTranEVO newItem) {
      if(this.mCUBE_PENDING_TRAN == null) {
         this.mCUBE_PENDING_TRAN = new HashMap();
      }

      CubePendingTranPK newPK = newItem.getPK();
      if(this.getCUBE_PENDING_TRANItem(newPK) != null) {
         throw new RuntimeException("addCUBE_PENDING_TRANItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mCUBE_PENDING_TRAN.put(newPK, newItem);
      }
   }

   public void changeCUBE_PENDING_TRANItem(CubePendingTranEVO changedItem) {
      if(this.mCUBE_PENDING_TRAN == null) {
         throw new RuntimeException("changeCUBE_PENDING_TRANItem: no items in collection");
      } else {
         CubePendingTranPK changedPK = changedItem.getPK();
         CubePendingTranEVO listItem = this.getCUBE_PENDING_TRANItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeCUBE_PENDING_TRANItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteCUBE_PENDING_TRANItem(CubePendingTranPK removePK) {
      CubePendingTranEVO listItem = this.getCUBE_PENDING_TRANItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeCUBE_PENDING_TRANItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public CubePendingTranEVO getCUBE_PENDING_TRANItem(CubePendingTranPK pk) {
      return (CubePendingTranEVO)this.mCUBE_PENDING_TRAN.get(pk);
   }

   public CubePendingTranEVO getCUBE_PENDING_TRANItem() {
      if(this.mCUBE_PENDING_TRAN.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mCUBE_PENDING_TRAN.size());
      } else {
         Iterator iter = this.mCUBE_PENDING_TRAN.values().iterator();
         return (CubePendingTranEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public ReportRef getEntityRef(String entityText) {
      return new ReportRefImpl(this.getPK(), entityText);
   }

   public void postCreateInit() {
      this.mCUBE_PENDING_TRANAllItemsLoaded = true;
      if(this.mCUBE_PENDING_TRAN == null) {
         this.mCUBE_PENDING_TRAN = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ReportId=");
      sb.append(String.valueOf(this.mReportId));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      sb.append("ReportType=");
      sb.append(String.valueOf(this.mReportType));
      sb.append(' ');
      sb.append("TaskId=");
      sb.append(String.valueOf(this.mTaskId));
      sb.append(' ');
      sb.append("Complete=");
      sb.append(String.valueOf(this.mComplete));
      sb.append(' ');
      sb.append("ReportText=");
      sb.append(String.valueOf(this.mReportText));
      sb.append(' ');
      sb.append("HasUpdates=");
      sb.append(String.valueOf(this.mHasUpdates));
      sb.append(' ');
      sb.append("UpdatesApplied=");
      sb.append(String.valueOf(this.mUpdatesApplied));
      sb.append(' ');
      sb.append("UpdateTaskId=");
      sb.append(String.valueOf(this.mUpdateTaskId));
      sb.append(' ');
      sb.append("BudgetCycleId=");
      sb.append(String.valueOf(this.mBudgetCycleId));
      sb.append(' ');
      sb.append("ActivityType=");
      sb.append(String.valueOf(this.mActivityType));
      sb.append(' ');
      sb.append("ActivityDetail=");
      sb.append(String.valueOf(this.mActivityDetail));
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

      sb.append("Report: ");
      sb.append(this.toString());
      if(this.mCUBE_PENDING_TRANAllItemsLoaded || this.mCUBE_PENDING_TRAN != null) {
         sb.delete(indent, sb.length());
         sb.append(" - CUBE_PENDING_TRAN: allItemsLoaded=");
         sb.append(String.valueOf(this.mCUBE_PENDING_TRANAllItemsLoaded));
         sb.append(" items=");
         if(this.mCUBE_PENDING_TRAN == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mCUBE_PENDING_TRAN.size()));
         }
      }

      if(this.mCUBE_PENDING_TRAN != null) {
         Iterator var5 = this.mCUBE_PENDING_TRAN.values().iterator();

         while(var5.hasNext()) {
            CubePendingTranEVO listItem = (CubePendingTranEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(CubePendingTranEVO child, int newReportId, int newFinanceCubeId, int newRowNo) {
      if(this.getCUBE_PENDING_TRANItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mCUBE_PENDING_TRAN.remove(child.getPK());
         child.setReportId(newReportId);
         child.setFinanceCubeId(newFinanceCubeId);
         child.setRowNo(newRowNo);
         this.mCUBE_PENDING_TRAN.put(child.getPK(), child);
      }
   }

   public void setCUBE_PENDING_TRANAllItemsLoaded(boolean allItemsLoaded) {
      this.mCUBE_PENDING_TRANAllItemsLoaded = allItemsLoaded;
   }

   public boolean isCUBE_PENDING_TRANAllItemsLoaded() {
      return this.mCUBE_PENDING_TRANAllItemsLoaded;
   }
}
