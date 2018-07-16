// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cm;

import com.cedar.cp.dto.cm.ChangeManagementTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.cm.ChangeManagementCheckPoint;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEngine;
import com.cedar.cp.util.Log;
import javax.naming.InitialContext;

public class ChangeManagementTask extends AbstractTask {

   private transient InitialContext mInitialContext;
   private transient Log mLog = new Log(this.getClass());


   public void runUnitOfWork(InitialContext context) throws Exception {
      this.mInitialContext = context;
      if(this.getCheckpoint() == null) {
         this.setCheckpoint(new ChangeManagementCheckPoint());
      }

      this.setCheckpoint(this.doWork());
   }

   public int getReportType() {
      return 5;
   }

   public ChangeManagementCheckPoint getCheckpoint() {
      return (ChangeManagementCheckPoint)super.getCheckpoint();
   }

   public ChangeManagementTaskRequest getRequest() {
      return (ChangeManagementTaskRequest)super.getRequest();
   }

   private ChangeManagementCheckPoint doWork() throws Exception {
      ChangeMgmtEngine engine = new ChangeMgmtEngine(this.mInitialContext, this.getRequest(), this.getCheckpoint(), this, this);
      engine.setTaskId(this.getTaskId());
      engine.setUserId(this.getUserId());
      ChangeManagementCheckPoint cp = engine.runChangeManagement();
      return cp;
   }

   public String getEntityName() {
      return "ChangeManagementTask";
   }
}
