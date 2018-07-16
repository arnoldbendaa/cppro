// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.extsys;

import com.cedar.cp.dto.extsys.ExternalSystemExportTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.extsys.ExternalSystemExportTask$1;
import com.cedar.cp.ejb.base.async.extsys.ExternalSystemExportTask$ExternalSystemExportCheckpoint;
import com.cedar.cp.ejb.impl.extsys.xmlexp.ExternalSystemExportEngine;
import com.cedar.cp.util.Log;
import java.io.PrintWriter;
import javax.naming.InitialContext;

public class ExternalSystemExportTask extends AbstractTask {

   private transient InitialContext mInitialContext;
   private transient Log mLog = new Log(this.getClass());
   private transient ExternalSystemExportTaskRequest mRequest;


   public int getReportType() {
      return 10;
   }

   public ExternalSystemExportTask$ExternalSystemExportCheckpoint getCheckpoint() {
      return (ExternalSystemExportTask$ExternalSystemExportCheckpoint)super.getCheckpoint();
   }

   public String getEntityName() {
      return "External System Export";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      (new ExternalSystemExportEngine()).exportMappedModel(this.getRequest().getMappedModelId(), this.getRequest().getFinanceCubes(), new PrintWriter(new ExternalSystemExportTask$1(this)));
   }

   public ExternalSystemExportTaskRequest getRequest() {
      return (ExternalSystemExportTaskRequest)super.getRequest();
   }
}
