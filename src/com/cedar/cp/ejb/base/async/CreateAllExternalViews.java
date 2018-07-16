// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.util.Log;
import javax.naming.InitialContext;

public class CreateAllExternalViews extends AbstractTask {

   private transient Log mLog = new Log(this.getClass());
   private transient InitialContext mInitialContext;


   public void runUnitOfWork(InitialContext context) throws Exception {
      this.mInitialContext = context;
      (new ExternalSystemDAO()).createAllExternalViews(this.getTaskId());
   }

   public boolean autoDeleteWhenComplete() {
      return false;
   }

   public int getReportType() {
      return 0;
   }

   public TaskCheckpoint getCheckpoint() {
      return null;
   }

   public String getEntityName() {
      return "CreateAllExternalViews";
   }
}
