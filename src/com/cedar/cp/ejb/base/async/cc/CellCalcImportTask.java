// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cc;

import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.model.cc.CellCalcImportTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.cc.CellCalcImportCheckPoint;
import com.cedar.cp.ejb.base.async.cc.CellCalcImportTask$1;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcImportEngine;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import javax.naming.InitialContext;

public class CellCalcImportTask extends AbstractTask {

   public int getReportType() {
      return 13;
   }

   public String getEntityName() {
      return "CellCalcImport";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      if(this.getCheckpoint() == null) {
         this.setCheckpoint(new CellCalcImportCheckPoint());
      }

      this.executeCalcImportChunk();
   }

   public void executeCalcImportChunk() throws Exception {
      Object reader = null;

      try {
         String clientURL = this.getImportRequest().getClientURL();
         String sourceURL = this.getImportRequest().getSourceURL();
         int absAllocThreshold = SystemPropertyHelper.queryIntegerSystemProperty((Connection)null, "ALLOC: Threshold", 100);
         CellCalcImportEngine engine = new CellCalcImportEngine(this.getUserId(), this.getTaskId(), absAllocThreshold);
         CellCalcImportCheckPoint checkPoint = this.getCellCalcImportChckPoint();
         engine.loadCellCalcImport(clientURL, sourceURL, new PrintWriter(new CellCalcImportTask$1(this)), checkPoint.getBatchesProcessed(), checkPoint.getCalcsProcessed(), 250);
         checkPoint.setBatchesProcessed(engine.getLastBatchProcessedIndex());
         checkPoint.setCalcsProcessed(engine.getLastCalcProcessedIndex() + 1);
         if(engine.noWorkLeftToDo()) {
            this.setCheckpoint((TaskCheckpoint)null);
         }
      } finally {
         if(reader != null) {
            ((Reader)reader).close();
         }

      }

   }

   private CellCalcImportTaskRequest getImportRequest() {
      return (CellCalcImportTaskRequest)super.getRequest();
   }

   private CellCalcImportCheckPoint getCellCalcImportChckPoint() {
      return (CellCalcImportCheckPoint)this.getCheckpoint();
   }
}
