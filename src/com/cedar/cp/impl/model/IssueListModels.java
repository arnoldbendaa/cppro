// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.dto.model.ListModelsTaskRequest;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.impl.base.IssueTaskBase;
import com.cedar.cp.util.ParamHelper;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.task.TaskMessageFactory;
import javax.naming.InitialContext;

public class IssueListModels extends IssueTaskBase {

   String mX1;
   int mX2;


   public static void main(String[] args) {
      IssueListModels app = new IssueListModels();
      int exitCode = app.processArgs(args);
      System.exit(exitCode);
   }

   public String[][] getParameterInfo() {
      return new String[][]{{"-s{erver}", "String", "Host name"}, {"-u{ser}", "string", "User name"}, {"-pass{word}", "string", "Password"}, {"-x1", "string", "X1 value", "default"}, {"-x2", "int", "X2 value", "99"}};
   }

   public void checkSpecificParams(ParamHelper ph) {
      this.mX1 = ph.getString("-x1");
      this.mX2 = ph.getInt("-x2");
   }

   public void issueRequest() {
      try {
         ListModelsTaskRequest e = new ListModelsTaskRequest(this.mX1, this.mX2);
         Timer timer = new Timer(this.mLog);
         int taskId = TaskMessageFactory.issueNewTask((InitialContext)this.mConnection.getServerContext().getContext(), true, e, ((UserPK)this.mConnection.getUserContext().getPrimaryKey()).getUserId());
         timer.logInfo("issueRequest ", "issued task " + taskId);
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new RuntimeException(var4.getMessage());
      }
   }
}
