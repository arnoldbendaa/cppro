// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.cc.RuntimeCcDeployment;
import com.cedar.cp.api.model.cc.RuntimeCubeDeployment;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentCK;
import com.cedar.cp.dto.model.cc.CcDeploymentPK;
import com.cedar.cp.ejb.impl.base.impexp.ImpExpUtils;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEVO;
import com.cedar.cp.ejb.impl.model.cc.imp.AbstractCellCalcImportEngine;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcImport;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcImportEngine$1;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcImportProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcRowUpdate;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcUtils;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcImportParser;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.xlsimport.ExcelCellCalculationImport;
import com.cedar.cp.util.xlsimport.ExcelGenericImportException;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.inputs.DefaultFormDataInputModel;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CellCalcImportEngine extends AbstractCellCalcImportEngine implements CellCalcImportProcessor {

   private CellCalcImportParser mCellCalcImportParser = new CellCalcImportParser(this);


   public CellCalcImportEngine(int userId, int taskId, int absAllocThreshold) {
      this.mUserId = userId;
      this.mTaskId = taskId;
      this.mAbsAllocThreshold = absAllocThreshold;
   }

   public String getEntityName() {
      return "CellCalcImportEngine";
   }

   public void loadCellCalcImport(String clientURL, String srcURL, PrintWriter log, int batchStartIndex, int cellCalcStartIndex, int batchSize) throws ValidationException, Exception {
      InputStreamReader reader = null;
      this.mBatchStartIndex = batchStartIndex;
      this.mCellCalcStartIndex = cellCalcStartIndex;
      this.mCellCalcBatchSize = batchSize;

      try {
         if(ImpExpUtils.isSpreadsheet(clientURL)) {
            File newXmlFile = this.convertToXML(srcURL);
            srcURL = newXmlFile.toURI().toURL().toExternalForm();
         }

         reader = new InputStreamReader(this.getInputStream(srcURL));
         this.mCellCalcImportParser.loadCellCalcImport(reader, log);
      } finally {
         if(reader != null) {
            reader.close();
         }

      }

   }

   public void startBatch() {
      ++this.mCurrentBatchIndex;
      this.mCurrentCalcIndex = 0;
   }

   public boolean processCalcUpdate(CellCalcImport cellCalcImport) throws ValidationException, Exception {
      boolean inBatchWindow = this.isInBatchWindow();
      if(inBatchWindow) {
         this.doProcessCalcUpdate(cellCalcImport);
         this.mLastBatchProcessedIndex = this.mCurrentBatchIndex;
         this.mLastCalcProcessedIndex = this.mCurrentCalcIndex;
      }

      ++this.mCurrentCalcIndex;
      return inBatchWindow;
   }

   public void endBatch() {}

   private File convertToXML(String srcURL) throws ValidationException, IOException, ExcelGenericImportException {
      File outputFile = File.createTempFile("cpcc", ".xml");
      PrintStream ps = null;
      InputStream is = null;

      File var5;
      try {
         ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputFile)));
         is = this.getInputStream(srcURL);
         (new ExcelCellCalculationImport()).processStream(is, ps);
         var5 = outputFile;
      } finally {
         if(ps != null) {
            ps.close();
         }

         if(is != null) {
            is.close();
         }

      }

      return var5;
   }

   private void doProcessCalcUpdate(CellCalcImport cellCalcImport) throws ValidationException, Exception {
      if(cellCalcImport.getModelRef() == null) {
         ModelRefImpl addressInfo = this.queryModelRef(cellCalcImport.getModel());
         if(addressInfo == null) {
            throw new ValidationException("Model " + cellCalcImport.getModel() + " not found.");
         }

         cellCalcImport.setModelRef(addressInfo);
         cellCalcImport.setDimensionRefs(this.queryModelDimensions(addressInfo));
      }

      if(cellCalcImport.getFinanceCubeRef() == null) {
         FinanceCubeRefImpl addressInfo1 = this.queryFinanceCubeRef(cellCalcImport.getModelRef(), cellCalcImport.getFinanceCube());
         if(addressInfo1 == null) {
            throw new ValidationException("Finance cube " + cellCalcImport.getFinanceCube() + " for model " + cellCalcImport.getModel() + " not found.");
         }

         cellCalcImport.setFinanceCubeRef(addressInfo1);
      }

      if(cellCalcImport.getBudgetCycleRef() == null) {
         BudgetCycleRefImpl addressInfo2 = this.queryBudgetCycleRef(cellCalcImport.getModelRef(), cellCalcImport.getBudgetCycle());
         if(addressInfo2 == null) {
            throw new ValidationException("Budget cycle " + cellCalcImport.getBudgetCycle() + " for model " + cellCalcImport.getModel() + " not found.");
         }

         cellCalcImport.setBudgetCycleRef(addressInfo2);
      }

      if(cellCalcImport.getCalendarInfo() == null) {
         CalendarInfo addressInfo3 = this.queryCalendareInfo(cellCalcImport.getModelRef());
         if(addressInfo3 == null) {
            throw new ValidationException("Failed to query calendar info for model:" + cellCalcImport.getModelRef());
         }

         cellCalcImport.setCalendarInfo(addressInfo3);
      }

      Pair addressInfo4 = this.queryAddressDetails(cellCalcImport.getAddress(), cellCalcImport.getDimensionRefs(), cellCalcImport.getCalendarInfo());
      StructureElementRefImpl[] addressRefs = (StructureElementRefImpl[])addressInfo4.getChild1();
      cellCalcImport.setStructureElementRefs(addressRefs);
      int[] positions = (int[])addressInfo4.getChild2();
      cellCalcImport.setAddressPositions(positions);
      String dataType = cellCalcImport.getDataType();
      DataTypeRefImpl dataTypeRefImpl = this.queryDataTypeRef(dataType);
      if(dataTypeRefImpl == null) {
         throw new ValidationException("Unable to locate data type:" + dataType);
      } else {
         List runtimeDeployments = this.queryCellCalcAtCellAddress(cellCalcImport.getModelRef().getModelPK().getModelId(), cellCalcImport.getFinanceCubeRef().getFinanceCubePK().getFinanceCubeId(), cellCalcImport.getStructureElementRefs(addressRefs.length - 1), cellCalcImport.getDataType());
         int numDims = cellCalcImport.getStructureElementRefs().length;
         CalendarElementNode calendarElementNode = cellCalcImport.getCalendarInfo().getById(cellCalcImport.getStructureElementRefs()[numDims - 1]);
         RuntimeCcDeployment runtimeDeployment = null;
         Iterator financeCubeId = runtimeDeployments.iterator();

         while(financeCubeId.hasNext()) {
            RuntimeCubeDeployment calc_details = (RuntimeCubeDeployment)financeCubeId.next();
            RuntimeCcDeployment deployment = (RuntimeCcDeployment)calc_details;
            if(deployment.doesDeploymentTargetCell(cellCalcImport.getAddressIds(numDims - 1), cellCalcImport.getDataType(), cellCalcImport.getCalendarInfo(), calendarElementNode)) {
               runtimeDeployment = deployment;
               break;
            }
         }

         if(runtimeDeployment == null) {
            throw new ValidationException("No calculator deployed to cell address:" + cellCalcImport.getFormattedAddress() + " dataType:" + cellCalcImport.getDataType());
         } else {
            cellCalcImport.setRuntimeCcDeployment(runtimeDeployment);
            int financeCubeId1 = cellCalcImport.getFinanceCubeRef().getFinanceCubePK().getFinanceCubeId();
            int[] calc_details1 = this.getCcDeploymentDAO().queryCellCalcDetailsForCell(financeCubeId1, cellCalcImport.getAddressIds(), cellCalcImport.getDataType());
            if(calc_details1 != null) {
               cellCalcImport.setShortId(calc_details1[0]);
               if(calc_details1[1] != cellCalcImport.getRuntimeCcDeployment().getCcDeploymentId()) {
                  throw new ValidationException("Calculator deployment id mismatch at address:" + cellCalcImport.getFormattedAddress());
               }
            }

            switch(CellCalcImportEngine$1.$SwitchMap$com$cedar$cp$ejb$impl$model$cc$imp$CellCalcUpdateType[cellCalcImport.getUpdateType().ordinal()]) {
            case 1:
               this.insertNewCellCalc(cellCalcImport);
               break;
            case 2:
               this.mergeCellCalc(cellCalcImport);
               break;
            case 3:
               this.replaceCellCalc(cellCalcImport);
               break;
            case 4:
               this.updateCellCalc(cellCalcImport);
               break;
            case 5:
               this.deleteCellCalc(cellCalcImport);
            }

         }
      }
   }

   private void deleteCellCalc(CellCalcImport cellCalcImport) throws ValidationException, Exception {
      int shortId = cellCalcImport.getShortId();
      if(shortId == 0) {
         throw new ValidationException("No cell calc instance found to DELETE at address:" + cellCalcImport.getFormattedAddress());
      } else {
         CellCalcUtils cellCalcUtils = new CellCalcUtils();
         cellCalcUtils.deleteCalculator(cellCalcImport.getDimensionRefs().length, cellCalcImport.getFinanceCubeRef().getFinanceCubePK().getFinanceCubeId(), cellCalcImport.getRuntimeCcDeployment().getCcDeploymentId(), shortId, this.getTaskId(), this.getUserId());
      }
   }

   private void updateCellCalc(CellCalcImport cellCalcImport) throws ValidationException, Exception {
      int shortId = cellCalcImport.getShortId();
      if(shortId == 0) {
         throw new ValidationException("No cell calc instance found to UPDATE at address:" + cellCalcImport.getFormattedAddress());
      } else {
         int xmlFormId = cellCalcImport.getRuntimeCcDeployment().getXMLFormId();
         FormConfig formConfig = this.getCellCalcUtils().loadCellCalculationDetails(xmlFormId);
         int financeCubeId = cellCalcImport.getFinanceCubeRef().getFinanceCubePK().getFinanceCubeId();
         DefaultFormDataInputModel formModel = (DefaultFormDataInputModel)this.getCellCalcUtils().loadFormModel(formConfig, financeCubeId, cellCalcImport.getShortId());
         this.updateCellCalcData(cellCalcImport, formConfig, formModel);
         this.exeuteAndSaveCellCalc(cellCalcImport, formConfig, formModel);
      }
   }

   private void replaceCellCalc(CellCalcImport cellCalcImport) throws ValidationException, Exception {
      int shortId = cellCalcImport.getShortId();
      if(shortId == 0) {
         this.insertNewCellCalc(cellCalcImport);
      } else {
         int xmlFormId = cellCalcImport.getRuntimeCcDeployment().getXMLFormId();
         FormConfig formConfig = this.getCellCalcUtils().loadCellCalculationDetails(xmlFormId);
         int financeCubeId = cellCalcImport.getFinanceCubeRef().getFinanceCubePK().getFinanceCubeId();
         DefaultFormDataInputModel formModel = (DefaultFormDataInputModel)this.getCellCalcUtils().loadFormModel(formConfig, financeCubeId, cellCalcImport.getShortId());

         while(formModel.getRowCount() > 0) {
            formModel.deleteRow(0);
         }

         this.updateCellCalcData(cellCalcImport, formConfig, formModel);
         this.exeuteAndSaveCellCalc(cellCalcImport, formConfig, formModel);
      }

   }

   public void mergeCellCalc(CellCalcImport cellCalcImport) throws ValidationException, Exception {
      int shortId = cellCalcImport.getShortId();
      if(shortId == 0) {
         this.insertNewCellCalc(cellCalcImport);
      } else {
         int xmlFormId = cellCalcImport.getRuntimeCcDeployment().getXMLFormId();
         FormConfig formConfig = this.getCellCalcUtils().loadCellCalculationDetails(xmlFormId);
         int financeCubeId = cellCalcImport.getFinanceCubeRef().getFinanceCubePK().getFinanceCubeId();
         DefaultFormDataInputModel formModel = (DefaultFormDataInputModel)this.getCellCalcUtils().loadFormModel(formConfig, financeCubeId, cellCalcImport.getShortId());
         this.updateCellCalcData(cellCalcImport, formConfig, formModel);
         this.exeuteAndSaveCellCalc(cellCalcImport, formConfig, formModel);
      }

   }

   private void insertNewCellCalc(CellCalcImport cellCalcImport) throws ValidationException, Exception {
      if(cellCalcImport.getShortId() != 0) {
         throw new ValidationException("Cell calc instance already present at address " + cellCalcImport.getFormattedAddress() + " unable to INSERT");
      } else {
         int xmlFormId = cellCalcImport.getRuntimeCcDeployment().getXMLFormId();
         FormConfig formConfig = this.getCellCalcUtils().loadCellCalculationDetails(xmlFormId);
         DefaultFormDataInputModel formModel = new DefaultFormDataInputModel(formConfig);
         cellCalcImport.setShortId(this.getNewCellCalcShort());
         this.updateCellCalcData(cellCalcImport, formConfig, formModel);
         this.exeuteAndSaveCellCalc(cellCalcImport, formConfig, formModel);
      }
   }

   private void exeuteAndSaveCellCalc(CellCalcImport cellCalcImport, FormConfig formConfig, DefaultFormDataInputModel formModel) throws ValidationException, Exception {
      CcDeploymentEVO deploymentEVO = this.getCcDeploymentDAO().getDetails(new CcDeploymentCK(cellCalcImport.getModelRef().getModelPK(), new CcDeploymentPK(cellCalcImport.getRuntimeCcDeployment().getCcDeploymentId())), "");
      RuntimeCcDeployment deployment = cellCalcImport.getRuntimeCcDeployment();
      int calendarElementId = deployment.getCalendarElementIdForCell(cellCalcImport.getAddressIds(), cellCalcImport.getDataType(), cellCalcImport.getCalendarInfo());
      CalendarElementNode calendarElementNode = cellCalcImport.getCalendarInfo().getById(calendarElementId);
      int financeCubeId = cellCalcImport.getFinanceCubeRef().getFinanceCubePK().getFinanceCubeId();
      Map dataTypeEvos = this.getDataTypeDAO().getAllForFinanceCube(financeCubeId);
      HashMap cachedLookupData = new HashMap();
      String cellPostings = this.getCellCalcUtils().runCalcForUpdate(cellCalcImport.getModelRef().getModelPK().getModelId(), financeCubeId, formConfig, formModel, new HashMap(), cellCalcImport.getShortId(), deploymentEVO, cellCalcImport.getRuntimeCcDeployment(), cellCalcImport.getCalendarInfo(), cellCalcImport.getAddressIds(), cellCalcImport.getAddressPositions(), cellCalcImport.getDataType(), calendarElementNode, dataTypeEvos, cachedLookupData, (double)this.getAbsAllocThreshold());
      this.getCellCalcUtils().postChangesToFinanceCube(financeCubeId, cellCalcImport.getAddressIds().length, this.getTaskId(), this.getUserId(), cellPostings);
   }

   private void updateCellCalcData(CellCalcImport cellCalcImport, FormConfig formConfig, DefaultFormDataInputModel formModel) throws ValidationException, Exception {
      Iterator i$ = cellCalcImport.getRowUpdates().iterator();

      while(i$.hasNext()) {
         CellCalcRowUpdate rowUpdate = (CellCalcRowUpdate)i$.next();
         rowUpdate.applyUpdate(formConfig, formModel);
      }

   }
}
