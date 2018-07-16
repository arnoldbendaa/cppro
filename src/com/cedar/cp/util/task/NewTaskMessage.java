// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.task;

import java.io.Serializable;

public class NewTaskMessage implements Serializable {

   private int mTaskId;
   private int mUserId;
   private long mOriginalSendTime;
   private int mRetryCount = 0;


   public NewTaskMessage(int taskId, int userId, long originalSendTime) {
      this.mTaskId = taskId;
      this.mUserId = userId;
      this.mOriginalSendTime = originalSendTime;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public long getOriginalSendTime() {
      return this.mOriginalSendTime;
   }

   public int getRetryCount() {
      return this.mRetryCount;
   }

   public void incrementRetryCount() {
      ++this.mRetryCount;
   }
}
