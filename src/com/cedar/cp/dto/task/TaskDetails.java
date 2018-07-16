// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task;

import com.cedar.cp.dto.task.TaskPK;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class TaskDetails implements Serializable {

   private int mTaskId;
   private long mSystemTimeMillis;
   private String mTaskName;
   private int mUserId;
   private int mOriginalTaskId;
   private int mStatus;
   private Exception mException;
   private List mExclusiveAccessList;
   private Timestamp mCreateDate;
   private Timestamp mEndDate;
   private String mStep;
   public static final int CREATED = 0;
   public static final int RECEIVED = 1;
   public static final int DESPATCHED = 2;
   public static final int STARTED = 3;
   public static final int FAILED = 4;
   public static final int COMPLETE = 5;


   public TaskDetails(int taskId, long systemTimeMillis, String taskName, int userId, int originalTaskId, int status, Exception exception, List eal, Timestamp createDate, Timestamp endDate, String step) {
      this.mTaskId = taskId;
      this.mTaskName = taskName;
      this.mUserId = userId;
      this.mOriginalTaskId = originalTaskId;
      this.mStatus = status;
      this.mException = exception;
      this.mExclusiveAccessList = eal;
      this.mCreateDate = createDate;
      this.mEndDate = endDate;
      this.mStep = step;
   }

   public TaskPK getPK() {
      return new TaskPK(this.mTaskId);
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public long getSystemTimeMillis() {
      return this.mSystemTimeMillis;
   }

   public String getTaskName() {
      return this.mTaskName;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public int getOriginalTaskId() {
      return this.mOriginalTaskId;
   }

   public int getStatus() {
      return this.mStatus;
   }

   public Exception getException() {
      return this.mException;
   }

   public List getExclusiveAccessList() {
      return this.mExclusiveAccessList;
   }

   public Timestamp getCreateDate() {
      return this.mCreateDate;
   }

   public Timestamp getEndDate() {
      return this.mEndDate;
   }

   public String getStep() {
      return this.mStep;
   }
}
