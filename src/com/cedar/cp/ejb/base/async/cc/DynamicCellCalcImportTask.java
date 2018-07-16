// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cc;

import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.model.cc.DynamicCellCalcImportTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.cc.DynamicCellCalcImportCheckPoint;
import com.cedar.cp.ejb.base.async.cc.DynamicCellCalcImportTask$1;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcUpdateType;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.DynamicCellCalcImport;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.DynamicCellCalcImportEngine;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.naming.InitialContext;

public class DynamicCellCalcImportTask extends AbstractTask {

   public int getReportType() {
      return 14;
   }

   public String getEntityName() {
      return "DynamicCellCalcImport";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      if(this.getCheckpoint() == null) {
         this.setCheckpoint(new DynamicCellCalcImportCheckPoint());
      }

      DynamicCellCalcImportCheckPoint checkPoint = this.getCellCalcImportCheckPoint();
      switch(checkPoint.getStep()) {
      case 0:
      case 1:
         this.parseAndLoadCellCalcImport();
         checkPoint.setStep(checkPoint.getStep() + 1);
         break;
      case 2:
         this.saveInScopeCalculatorInstanceDetails();
         checkPoint.setStep(checkPoint.getStep() + 1);
         break;
      case 3:
         this.preprocessCellGridData();
         break;
      case 4:
         this.processCellData();
         break;
      case 5:
         if(checkPoint.getDynamicCellCalcImport().getImportType() == CellCalcUpdateType.REPLACE) {
            this.processExtraneousCalculators();
         } else {
            this.getCellCalcImportCheckPoint().setStep(this.getCellCalcImportCheckPoint().getStep() + 1);
         }
         break;
      case 6:
         this.deleteCellDataWS();
         this.setCheckpoint((TaskCheckpoint)null);
      }

   }

   private void saveInScopeCalculatorInstanceDetails() throws Exception {
      this.getEngine().saveInScopeCalculatorInstanceDetails(this.getPrintWriter());
   }

   private void processCellData() throws Exception {
      short batchSize = 500;
      DynamicCellCalcImportEngine engine = this.getEngine();
      DynamicCellCalcImportCheckPoint checkPoint = this.getCellCalcImportCheckPoint();
      int startRow = checkPoint.getLinesImported() + 1;
      int rowsImported = engine.processImportRowData(this.getPrintWriter(), startRow, batchSize);
      checkPoint.setLinesImported(checkPoint.getLinesImported() + rowsImported);
      if(rowsImported < batchSize) {
         this.getCellCalcImportCheckPoint().setStep(this.getCellCalcImportCheckPoint().getStep() + 1);
      }

   }

   private void processExtraneousCalculators() throws Exception {
      short batchSize = 250;
      DynamicCellCalcImportCheckPoint checkPoint = this.getCellCalcImportCheckPoint();
      int startRow = checkPoint.getCalcsRemoved() + 1;
      int calcsProcessed = this.getEngine().processExtraneousCalculators(this.getPrintWriter(), startRow, batchSize);
      checkPoint.setCalcsRemoved(calcsProcessed + checkPoint.getCalcsRemoved());
      if(calcsProcessed < batchSize) {
         this.getCellCalcImportCheckPoint().setStep(this.getCellCalcImportCheckPoint().getStep() + 1);
      }

   }

   private void deleteCellDataWS() throws Exception {
      this.getEngine().deleteWorkTableData();
   }

   public void parseAndLoadCellCalcImport() throws Exception {
      int absAllocThreshold = SystemPropertyHelper.queryIntegerSystemProperty((Connection)null, "ALLOC: Threshold", 100);
      DynamicCellCalcImportEngine engine = new DynamicCellCalcImportEngine(this.getUserId(), this.getTaskId(), absAllocThreshold);
      DynamicCellCalcImportCheckPoint checkPoint = this.getCellCalcImportCheckPoint();
      engine.setDynamicCellCalcImport(checkPoint.getDynamicCellCalcImport() == null?new DynamicCellCalcImport():checkPoint.getDynamicCellCalcImport());
      engine.loadCellCalcImport(this.getImportRequest().getClientServerURLs(), this.getPrintWriter(), checkPoint.getLinesImported(), 250);
      checkPoint.setDynamicCellCalcImport(engine.getDynamicCellCalcImport());
   }

   private void preprocessCellGridData() throws Exception {
      short batchSize = 1000;
      DynamicCellCalcImportCheckPoint checkPoint = this.getCellCalcImportCheckPoint();
      int startRow = checkPoint.getLinesPreProcessed() + 1;
      int rowsPreProcessed = this.getEngine().preProcessImportRowData(this.getPrintWriter(), startRow, batchSize);
      checkPoint.setLinesPreProcessed(startRow + rowsPreProcessed);
      if(rowsPreProcessed > 0) {
         this.getPrintWriter().println("Preprocessed " + rowsPreProcessed + " import line(s).");
      }

      if(rowsPreProcessed < batchSize) {
         checkPoint.setStep(checkPoint.getStep() + 1);
      }

   }

   private DynamicCellCalcImportEngine getEngine() {
      int absAllocThreshold = SystemPropertyHelper.queryIntegerSystemProperty((Connection)null, "ALLOC: Threshold", 100);
      DynamicCellCalcImportEngine engine = new DynamicCellCalcImportEngine(this.getUserId(), this.getTaskId(), absAllocThreshold);
      DynamicCellCalcImportCheckPoint checkPoint = this.getCellCalcImportCheckPoint();
      engine.setDynamicCellCalcImport(checkPoint.getDynamicCellCalcImport());
      return engine;
   }

   private PrintWriter getPrintWriter() {
      return new PrintWriter(new DynamicCellCalcImportTask$1(this));
   }

   private DynamicCellCalcImportTaskRequest getImportRequest() {
      return (DynamicCellCalcImportTaskRequest)super.getRequest();
   }

   private DynamicCellCalcImportCheckPoint getCellCalcImportCheckPoint() {
      return (DynamicCellCalcImportCheckPoint)this.getCheckpoint();
   }
}
