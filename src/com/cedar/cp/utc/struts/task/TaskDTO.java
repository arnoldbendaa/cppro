// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.task;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class TaskDTO {

   private int mTaskId;
   private String mTaskName;
   private int mUserId;
   private String mUserName;
   private int mOriginalTaskId;
   private int mStatus;
   private Timestamp mCreatedDate;
   private Timestamp mEndDate;
   private String mStep;
   private static Map<Integer, String> mStatusMap = null;
   public static final String sCreatedStatus = "cp.task.list.status.key.0";
   public static final String sReceivedStatus = "cp.task.list.status.key.1";
   public static final String sDespatchedStatus = "cp.task.list.status.key.2";
   public static final String sStartedStatus = "cp.task.list.status.key.3";
   public static final String sFailedStatus = "cp.task.list.status.key.4";
   public static final String sCompleteStatus = "cp.task.list.status.key.5";
   public static final String sWaitingStatus = "cp.task.list.status.key.6";
   public static final String sCommitStarted = "cp.task.list.status.key.7";
   public static final String sCommitComplete = "cp.task.list.status.key.8";
   public static final String sCompleteWithException = "cp.task.list.status.key.9";
   public static final String sUnsafeDelete = "cp.task.list.status.key.10";


   public int getTaskId() {
      return this.mTaskId;
   }

   public void setTaskId(int taskId) {
      this.mTaskId = taskId;
   }

   public String getTaskName() {
      return this.mTaskName;
   }

   public void setTaskName(String taskName) {
      this.mTaskName = taskName;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public void setUserId(int userId) {
      this.mUserId = userId;
   }

   public String getUserName() {
      return this.mUserName;
   }

   public void setUserName(String userName) {
      this.mUserName = userName;
   }

   public int getOriginalTaskId() {
      return this.mOriginalTaskId;
   }

   public void setOriginalTaskId(int originalTaskId) {
      this.mOriginalTaskId = originalTaskId;
   }

   public int getStatus() {
      return this.mStatus;
   }

   public void setStatus(int status) {
      this.mStatus = status;
   }

   public Timestamp getCreatedDate() {
      return this.mCreatedDate;
   }

   public void setCreatedDate(Timestamp createdDate) {
      this.mCreatedDate = createdDate;
   }

   public Timestamp getEndDate() {
      return this.mEndDate;
   }

   public void setEndDate(Timestamp endDate) {
      this.mEndDate = endDate;
   }

   public String getStep() {
      return this.mStep;
   }

   public void setStep(String step) {
      this.mStep = step;
   }

   public String getStatusKey() {
      return (String)mStatusMap.get(Integer.valueOf(this.getStatus()));
   }

   static {
      mStatusMap = new HashMap();
      mStatusMap.put(Integer.valueOf(0), "cp.task.list.status.key.0");
      mStatusMap.put(Integer.valueOf(1), "cp.task.list.status.key.1");
      mStatusMap.put(Integer.valueOf(2), "cp.task.list.status.key.2");
      mStatusMap.put(Integer.valueOf(3), "cp.task.list.status.key.3");
      mStatusMap.put(Integer.valueOf(4), "cp.task.list.status.key.4");
      mStatusMap.put(Integer.valueOf(5), "cp.task.list.status.key.5");
      mStatusMap.put(Integer.valueOf(6), "cp.task.list.status.key.6");
      mStatusMap.put(Integer.valueOf(7), "cp.task.list.status.key.7");
      mStatusMap.put(Integer.valueOf(8), "cp.task.list.status.key.8");
      mStatusMap.put(Integer.valueOf(9), "cp.task.list.status.key.9");
      mStatusMap.put(Integer.valueOf(10), "cp.task.list.status.key.10");
   }
}
