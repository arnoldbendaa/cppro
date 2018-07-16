// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.virement;

import com.cedar.cp.dto.model.virement.VirementRequestCK;
import com.cedar.cp.dto.model.virement.VirementRequestImpl;
import com.cedar.cp.dto.model.virement.VirementTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.virement.VirementTask$VirementCheckpoint;
import com.cedar.cp.ejb.impl.virement.VirementUpdateEngine;
import com.cedar.cp.util.Log;
import java.text.MessageFormat;
import javax.naming.InitialContext;

public class VirementTask extends AbstractTask {

   private transient InitialContext mInitialContext;
   private transient Log mLog = new Log(this.getClass());


   public VirementTask$VirementCheckpoint getCheckpoint() {
      return (VirementTask$VirementCheckpoint)super.getCheckpoint();
   }

   public VirementTaskRequest getRequest() {
      return (VirementTaskRequest)super.getRequest();
   }

   public VirementRequestImpl getVirementRequest() {
      return this.getRequest().getVirementRequest();
   }

   public int getReportType() {
      return 3;
   }

   public String getEntityName() {
      return "Budget Transfer";
   }

   public void runUnitOfWork(InitialContext context) throws Exception {
      this.mInitialContext = context;
      if(this.getCheckpoint() == null) {
         this.firstTime();
      } else {
         this.setCheckpoint(this.doWork());
      }

   }

   private void firstTime() throws Exception {
      this.setCheckpoint(new VirementTask$VirementCheckpoint());
      VirementUpdateEngine engine = new VirementUpdateEngine(this.getUserId(), this.getVirementRequest(), this.getTaskReport());
      engine.setTaskId(Integer.valueOf(this.getTaskId()));
      engine.setMessageLogger(this);
      engine.setReportWriter(this);
      engine.performUpdate();
      engine.markRequestAsProcessed();
      this.sendEntityEventMessage(this.mInitialContext, "VirementRequest", ((VirementRequestCK)this.getVirementRequest().getPrimaryKey()).getVirementRequestPK(), 2);
   }

   private VirementTask$VirementCheckpoint doWork() throws Exception {
      this.getTaskReport().setCompleted();
      return null;
   }

   public String getCompletionMessage() {
      return MessageFormat.format("Your budget transfer request number [{0}] has been processed successfully.", new Object[]{String.valueOf(this.getVirementRequest().getRequestId())});
   }
}
