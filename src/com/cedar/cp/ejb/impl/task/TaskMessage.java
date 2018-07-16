// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task;

import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.ejb.impl.task.TaskEVO;
import java.io.Serializable;
import java.util.List;

public class TaskMessage implements Serializable {

   public static final int TYPE_NEW_TASK = 1;
   public static final int TYPE_TASK_DESPATCHED = 2;
   public static final int TYPE_RUN_TASK = 3;
   public static final int TYPE_TASK_FINISHED = 4;
   public static final int TYPE_WAKE_UP = 5;
   public static final int TYPE_FAILED_TASK = 6;
   public static final int TYPE_TASK_EVENT = 7;
   public static final int TYPE_TASK_FINISHED_AUTO_DELETE = 8;
   public static final int TYPE_TASK_GROUP_WAITING = 9;
   private int _msgType;
   private TaskPK _taskPK;
   private TaskEVO _taskEVO;
   private String _userName;
   private int _retryCount = 0;
   private String _step;
   private int _userId;
   private String _taskName;
   private Throwable _exception;
   private List _eventList;
   private int _checkpointNumber;


   public TaskMessage(int msgType_, TaskEVO taskEVO_) {
      this._msgType = msgType_;
      this._taskEVO = taskEVO_;
   }

   public TaskMessage(int msgType_, TaskPK taskPK_, String userName_) {
      this._msgType = msgType_;
      this._taskPK = taskPK_;
      this._userName = userName_;
   }

   public TaskMessage(int msgType_, TaskPK taskPK_, String userName_, String step_, int userId_, String taskName_, Throwable e_) {
      this._msgType = msgType_;
      this._taskPK = taskPK_;
      this._userName = userName_;
      this._step = step_;
      this._userId = userId_;
      this._taskName = taskName_;
      this._exception = e_;
   }

   public TaskMessage(int msgType_, int taskId_, List eventList_) {
      this._msgType = msgType_;
      this._taskPK = new TaskPK(taskId_);
      this._eventList = eventList_;
   }

   public TaskMessage(int msgType_) {
      this._msgType = msgType_;
   }

   public int getMessageType() {
      return this._msgType;
   }

   public String toString() {
      switch(this._msgType) {
      case 1:
         return "new task " + this.getTaskPK();
      case 2:
         return "task despatched " + this.getTaskPK();
      case 3:
         return "run task " + this.getTaskPK();
      case 4:
         return "task finished " + this.getTaskPK();
      case 5:
         return "wake up " + (this.getTaskPK() != null?this.getTaskName():"");
      case 6:
         return "task failed " + this.getTaskPK();
      case 7:
         return "task event " + this.getTaskPK() + " events=" + this.getEventList().size();
      case 8:
         return "task finished auto-delete " + this.getTaskPK();
      case 9:
         return "task group waiting " + this.getTaskPK();
      default:
         return "TaskMessage - unexpected message type:" + this._msgType;
      }
   }

   public TaskPK getTaskPK() {
      return this._taskPK;
   }

   public TaskEVO getTaskEVO() {
      return this._taskEVO;
   }

   public String getUserName() {
      return this._userName;
   }

   public String getStep() {
      return this._step;
   }

   public int getUserId() {
      return this._userId;
   }

   public String getTaskName() {
      return this._taskName;
   }

   public Throwable getException() {
      return this._exception;
   }

   public int getRetryCount() {
      return this._retryCount;
   }

   public void incrementRetryCount() {
      ++this._retryCount;
   }

   public void clearRetryCount() {
      this._retryCount = 0;
   }

   public List getEventList() {
      return this._eventList;
   }

   public int getCheckpointNumber() {
      return this._checkpointNumber;
   }

   public void setCheckpointNumber(int checkpointNumber) {
      this._checkpointNumber = checkpointNumber;
   }
}
