// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report;

import com.cedar.cp.api.report.Report;
import com.cedar.cp.dto.report.ReportPK;
import java.io.Serializable;

public class ReportImpl implements Report, Serializable, Cloneable {

   private Object mPrimaryKey;
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


   public ReportImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mUserId = 0;
      this.mReportType = 0;
      this.mTaskId = 0;
      this.mComplete = false;
      this.mReportText = "";
      this.mHasUpdates = false;
      this.mUpdatesApplied = false;
      this.mUpdateTaskId = null;
      this.mBudgetCycleId = null;
      this.mActivityType = null;
      this.mActivityDetail = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (ReportPK)paramKey;
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

   public boolean isComplete() {
      return this.mComplete;
   }

   public String getReportText() {
      return this.mReportText;
   }

   public boolean isHasUpdates() {
      return this.mHasUpdates;
   }

   public boolean isUpdatesApplied() {
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

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setUserId(int paramUserId) {
      this.mUserId = paramUserId;
   }

   public void setReportType(int paramReportType) {
      this.mReportType = paramReportType;
   }

   public void setTaskId(int paramTaskId) {
      this.mTaskId = paramTaskId;
   }

   public void setComplete(boolean paramComplete) {
      this.mComplete = paramComplete;
   }

   public void setReportText(String paramReportText) {
      this.mReportText = paramReportText;
   }

   public void setHasUpdates(boolean paramHasUpdates) {
      this.mHasUpdates = paramHasUpdates;
   }

   public void setUpdatesApplied(boolean paramUpdatesApplied) {
      this.mUpdatesApplied = paramUpdatesApplied;
   }

   public void setUpdateTaskId(Integer paramUpdateTaskId) {
      this.mUpdateTaskId = paramUpdateTaskId;
   }

   public void setBudgetCycleId(Integer paramBudgetCycleId) {
      this.mBudgetCycleId = paramBudgetCycleId;
   }

   public void setActivityType(Integer paramActivityType) {
      this.mActivityType = paramActivityType;
   }

   public void setActivityDetail(String paramActivityDetail) {
      this.mActivityDetail = paramActivityDetail;
   }
}
