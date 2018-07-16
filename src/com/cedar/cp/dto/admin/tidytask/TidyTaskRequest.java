// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.admin.tidytask;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class TidyTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private String mTaskName;
   private List mCommands;
   private List mMessages;


   public TidyTaskRequest(String taskName, List commands) {
      this.mTaskName = taskName;
      this.mCommands = commands;
      this.mMessages = new ArrayList();
      this.mMessages.add("Processing " + taskName + "...");
   }

   public List getNextCommand() {
      List row = null;
      if(this.mCommands.size() > 0) {
         row = (List)this.mCommands.get(0);
         String command = row.get(1).toString();
         this.mMessages.add("Processing " + command);
      }

      return row;
   }

   public String getIdentifier() {
      return this.mTaskName;
   }

   public List getCommands() {
      return this.mCommands;
   }

   public void setCommands(List commands) {
      this.mCommands = commands;
   }

   public void pushList() {
      if(this.mCommands.size() > 0) {
         this.mCommands.remove(0);
      }

   }

   public List toDisplay() {
      return this.mMessages;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.admin.tidy.TidyTask";
   }
}
