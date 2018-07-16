// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:42
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.cc.CcDeploymentRef;
import com.cedar.cp.api.model.cc.RuntimeCcDeployment;
import com.cedar.cp.api.model.cc.RuntimeCubeDeployment;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentCK;
import com.cedar.cp.dto.model.cc.CcDeploymentPK;
import com.cedar.cp.dto.model.cc.CcDeploymentRefImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentsForModelELO;
import com.cedar.cp.ejb.impl.model.CellCalculationDataDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEVO;
import com.cedar.cp.ejb.impl.model.cc.imp.AbstractCellCalcImportEngine;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcRowUpdate;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcUpdateType;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ColumnMappingEntry;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.DynamicCellCalcImport;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.DynamicCellCalcImportEngine$CellCalcContext;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.DynamicCellCalcImportProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ImportGridCellDAO;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ImportGridDAO;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ImportGridEVO;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcImportParser;
import com.cedar.cp.util.CSVFileReader;
import com.cedar.cp.util.InterpreterException;
import com.cedar.cp.util.InterpreterWrapper;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.xlsimport.ExcelGenericImportException;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.inputs.DefaultFormDataInputModel;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xml.sax.SAXException;

public class DynamicCellCalcImportEngine extends AbstractCellCalcImportEngine implements DynamicCellCalcImportProcessor {

   private InterpreterWrapper mInterpreterWrapper;
   private DynamicCellCalcImportParser mDynamicCellCalcImportParser;
   private List<Pair<String, String>> mClientServerURL;
   private DynamicCellCalcImport mDynamicCellCalcImport;
   private ImportGridDAO mImportGridDAO;
   private ImportGridCellDAO mImportGridCellDAO;
   private CellCalculationDataDAO mCellCalculationDataDAO;
   private Log mLog = new Log(this.getClass());


   public DynamicCellCalcImportEngine() {}

   public DynamicCellCalcImportEngine(Connection connection) {
      super(connection);
   }

   public String getEntityName() {
      return "DynamicCellCalcImportEngine";
   }

   public DynamicCellCalcImportEngine(int userId, int taskId, int absAllocThreshold) {
      this.mDynamicCellCalcImportParser = new DynamicCellCalcImportParser(this);
      this.mUserId = userId;
      this.mTaskId = taskId;
      this.mAbsAllocThreshold = absAllocThreshold;
   }

   public void createGridImportHeader(int modelId) throws ValidationException {
      this.mDynamicCellCalcImport.setImportGridId(this.getModelDAO().reserveIds(1));
      ImportGridEVO importGridEVO = new ImportGridEVO(modelId, this.mDynamicCellCalcImport.getImportGridId(), this.getUserId());
      this.getImportGridDAO().setDetails(importGridEVO);
      this.getImportGridDAO().doCreate();
   }

   public void loadCellCalcImport(List<Pair<String, String>> clientServerURLs, PrintWriter log, int cellCalcStartIndex, int batchSize) throws ValidationException, Exception {
      InputStreamReader reader = null;
      this.mClientServerURL = clientServerURLs;
      this.mCellCalcStartIndex = cellCalcStartIndex;
      this.mCellCalcBatchSize = batchSize;

      try {
         reader = new InputStreamReader(this.getInputStream((String)((Pair)clientServerURLs.get(0)).getChild2()));
         this.mDynamicCellCalcImportParser.loadCellCalcImport(reader, log);
      } finally {
         if(reader != null) {
            reader.close();
         }

      }

   }

   public int importCSVFile(int modelId, String clientFileName, String encoding) throws SAXException {
      String serverFileName = this.lookupServerURL(clientFileName);
      if(serverFileName == null) {
         throw new SAXException("Failed to lookup server side URL for client side URL:" + clientFileName);
      } else if(this.mDynamicCellCalcImport.getImportGridId() <= 0) {
         try {
            this.createGridImportHeader(modelId);
            return -1;
         } catch (ValidationException var26) {
            throw new SAXException("Failed to create import grid header", var26);
         }
      } else {
         CSVFileReader reader = null;
         PreparedStatement ps = null;
         int rowNumber = 0;
         short batchMaxSize = 5000;
         int batchSize = 0;

         try {
            reader = new CSVFileReader(new URL(serverFileName), encoding);
            ps = this.getConnection().prepareStatement("insert into import_grid_cell (grid_id, row_number, column_number, user_data ) values ( ?, ?, ?, ? )");

            while(reader.hasNext()) {
               String[] e = (String[])((String[])reader.next());
               int e1 = 0;
               String[] columnsNames = e;
               int arr$ = e.length;

               int len$;
               for(len$ = 0; len$ < arr$; ++len$) {
                  String i$ = columnsNames[len$];
                  byte s = 1;
                  int var35 = s + 1;
                  ps.setInt(s, this.mDynamicCellCalcImport.getImportGridId());
                  ps.setInt(var35++, rowNumber);
                  ps.setInt(var35++, e1);
                  ps.setString(var35, i$);
                  ps.addBatch();
                  ++e1;
                  ++batchSize;
               }

               if(rowNumber == 0) {
                  ArrayList var32 = new ArrayList();
                  String[] var33 = e;
                  len$ = e.length;

                  for(int var34 = 0; var34 < len$; ++var34) {
                     String var36 = var33[var34];
                     var32.add(var36);
                  }

                  this.mDynamicCellCalcImport.setImportColumnNames(var32);
               }

               ++rowNumber;
               if(batchSize >= batchMaxSize) {
                  ps.executeBatch();
                  batchSize = 0;
               }
            }

            if(batchSize > 0) {
               ps.executeBatch();
            }

            int var31 = rowNumber;
            return var31;
         } catch (IOException var27) {
            throw new SAXException("Unable to open server side file: " + serverFileName + " for client file:" + clientFileName, var27);
         } catch (SQLException var28) {
            var28.printStackTrace();
            throw new SAXException("Failed to insert cell data into import_grid_cell", var28);
         } finally {
            this.closeStatement(ps);
            this.closeConnection();
            if(reader != null) {
               try {
                  reader.close();
               } catch (Exception var25) {
                  var25.printStackTrace();
                  System.err.println("Failed to close input stream:" + var25.getMessage());
               }
            }

         }
      }
   }

   public void importExcelFile(int modelId, String clientFilename) throws SAXException {}

   public void saveInScopeCalculatorInstanceDetails(PrintWriter pw) {
      if(this.mDynamicCellCalcImport.getImportType() == CellCalcUpdateType.REPLACE) {
         CalendarInfo calendarInfo = this.mDynamicCellCalcImport.getCalendarInfo();
         int inScopeCalcInstances = this.getCcDeploymentDAO().storeCellCalculatorInstancesDetails(this.getModelId(), this.getFinanceCubeId(), calendarInfo.getCalendarId(), this.getTaskId(), this.mDynamicCellCalcImport.getDimensionRefs().length - 1, new ArrayList(this.mDynamicCellCalcImport.getDeployments()), this.mDynamicCellCalcImport.getCalendarFilter());
         if(inScopeCalcInstances > 0) {
            pw.println("Saved away instance details for " + inScopeCalcInstances + " in-scope cell calculators.");
         }
      }

   }

   public int preProcessImportRowData(PrintWriter pw, int startRowIndex, int batchSize) throws ValidationException, Exception {
      if(!this.mDynamicCellCalcImport.isPreProcessingRequired()) {
         return 0;
      } else {
         Integer[] colSeqOrder = new Integer[this.getDimCount()];
         int[] contextColumnIndexes = this.mDynamicCellCalcImport.getContextColumnIndexes();

         for(int dataTypeColumn = 0; dataTypeColumn < colSeqOrder.length; ++dataTypeColumn) {
            colSeqOrder[dataTypeColumn] = Integer.valueOf(contextColumnIndexes[dataTypeColumn]);
         }

         Integer var21 = Integer.valueOf(this.mDynamicCellCalcImport.getDataTypeImportColumnIndex());
         List columnNames = this.mDynamicCellCalcImport.getImportColumnNames();
         List rows = this.getImportGridCellDAO().loadBatch(this.getDimCount(), this.mDynamicCellCalcImport.getImportGridId(), startRowIndex, startRowIndex + batchSize - 1, colSeqOrder, var21, (String[])columnNames.toArray(new String[0]), true);
         InterpreterWrapper interpreterWrapper = this.getInterpreterWrapper();
         ArrayList updateTrans = new ArrayList();
         Iterator i$ = rows.iterator();

         while(i$.hasNext()) {
            Map row = (Map)i$.next();

            for(int columnIndex = 0; columnIndex < columnNames.size(); ++columnIndex) {
               String columnName = (String)columnNames.get(columnIndex);
               String originalValue = (String)row.get(columnName);
               ColumnMappingEntry columnMappingEntry = (ColumnMappingEntry)this.mDynamicCellCalcImport.getColumnMapping().get(columnName);
               if(columnMappingEntry != null && columnMappingEntry.getConversionCode() != null) {
                  try {
                     interpreterWrapper.setVariable("input", originalValue);
                     interpreterWrapper.setVariable("output", (Object)null);
                     interpreterWrapper.eval(columnMappingEntry.getConversionCode());
                     Object e = interpreterWrapper.getVariable("output");
                     String newValue = e != null?String.valueOf(e):"";
                     if(originalValue == null && newValue != null || originalValue != null && newValue == null || originalValue != null && newValue != null && !originalValue.equals(newValue)) {
                        int rowNumber = Integer.valueOf((String)row.get("__row_number__")).intValue();
                        updateTrans.add(new Object[]{Integer.valueOf(rowNumber), Integer.valueOf(columnIndex), newValue});
                     }
                  } catch (InterpreterException var20) {
                     var20.printStackTrace();
                     throw new ValidationException("Failed to evaluate conversion code for import column:" + columnName + ".\n - " + var20.getMessage());
                  }
               }
            }
         }

         if(!updateTrans.isEmpty()) {
            this.getImportGridCellDAO().applyUpdates(this.mDynamicCellCalcImport.getImportGridId(), updateTrans, 1000);
         }

         return rows.size();
      }
   }

   private InterpreterWrapper getInterpreterWrapper() {
      if(this.mInterpreterWrapper == null) {
         this.mInterpreterWrapper = new InterpreterWrapper();
      }

      return this.mInterpreterWrapper;
   }

   public int processImportRowData(PrintWriter log, int startRowIndex, int batchSize) throws ValidationException, Exception {
      log.println("Processing import rows, start row:" + startRowIndex + " batch size:" + batchSize + ".");
      int sampleDeploymentId;
      if(this.mDynamicCellCalcImport.getNextCellCalcShortId() == 0) {
         sampleDeploymentId = this.getCellCalculationDataDAO().peekNextCellCalcInstanceNumber(this.getFinanceCubeId());
         this.mDynamicCellCalcImport.setNextCellCalcShortId(sampleDeploymentId);
      }

      sampleDeploymentId = this.querySampleDeploymentId();
      Integer[] ccDeploymentDimContext;
      if(sampleDeploymentId > 0) {
         CcDeploymentEVO dimRefs = this.getCcDeploymentDAO().getDetails(new CcDeploymentCK(new ModelPK(this.getModelId()), new CcDeploymentPK(sampleDeploymentId)), "");
         ccDeploymentDimContext = dimRefs.getDimContextArray();
      } else {
         ccDeploymentDimContext = new Integer[this.mDynamicCellCalcImport.getDimensionRefs().length - 1];

         for(int var27 = 0; var27 < ccDeploymentDimContext.length; ++var27) {
            ccDeploymentDimContext[var27] = Integer.valueOf(0);
         }
      }

      DimensionRefImpl[] var28 = this.mDynamicCellCalcImport.getDimensionRefs();
      String[] columnNames = (String[])this.mDynamicCellCalcImport.getImportColumnNames().toArray(new String[0]);
      Integer[] colSeqOrder = new Integer[var28.length];
      int colSeqIndex = 0;
      int[] contextColumnIndexes = this.mDynamicCellCalcImport.getContextColumnIndexes();

      int dataTypeColumn;
      for(dataTypeColumn = 0; dataTypeColumn < contextColumnIndexes.length - 1; ++dataTypeColumn) {
         if(ccDeploymentDimContext[dataTypeColumn].intValue() == 0) {
            colSeqOrder[colSeqIndex++] = Integer.valueOf(contextColumnIndexes[dataTypeColumn]);
         }
      }

      for(dataTypeColumn = 0; dataTypeColumn < contextColumnIndexes.length - 1; ++dataTypeColumn) {
         if(ccDeploymentDimContext[dataTypeColumn].intValue() == 1) {
            colSeqOrder[colSeqIndex++] = Integer.valueOf(contextColumnIndexes[dataTypeColumn]);
         }
      }

      Integer var29 = Integer.valueOf(this.mDynamicCellCalcImport.getDataTypeImportColumnIndex());
      HashSet updateCalcIdsInThisRunUnit = new HashSet();
      List rows = this.getImportGridCellDAO().loadBatch(var28.length, this.mDynamicCellCalcImport.getImportGridId(), startRowIndex, startRowIndex + batchSize - 1, colSeqOrder, var29, columnNames, false);
      CalendarInfo calendarInfo = this.mDynamicCellCalcImport.getCalendarInfo();
      int currentCalcShortId = -1;
      boolean currentXmlFormId = true;
      FormConfig currentFormConfig = null;
      DynamicCellCalcImportEngine$CellCalcContext currentContext = null;
      DefaultFormDataInputModel currentFormModel = null;
      DynamicCellCalcImportEngine$CellCalcContext context = null;
      CellCalcUpdateType updateType = this.mDynamicCellCalcImport.getImportType();

      for(int row = 0; row < rows.size(); ++row) {
         Map rowData = (Map)rows.get(row);
         Pair targetAddress = this.getTargetCellAddress(rowData);
         context = this.queryCellCalcDetails(this.getModelId(), this.getFinanceCubeId(), var28, (List)targetAddress.getChild1(), (String)targetAddress.getChild2(), calendarInfo);
         if(currentCalcShortId != context.mCellShortId) {
            if(currentCalcShortId > 0) {
               this.executeAndSaveCellCalc(currentFormConfig, currentFormModel, currentContext);
            }

            int var30 = context.mCellRuntimeDeployment.getXMLFormId();
            currentFormConfig = this.getCellCalcUtils().loadCellCalculationDetails(var30);
            currentContext = context;
            if(context.mCellShortId == 0) {
               currentFormModel = new DefaultFormDataInputModel(currentFormConfig);
               context.mCellShortId = this.getNewCellCalcShort();
            } else {
               currentFormModel = (DefaultFormDataInputModel)this.getCellCalcUtils().loadFormModel(currentFormConfig, this.getFinanceCubeId(), context.mCellShortId);
            }

            currentCalcShortId = context.mCellShortId;
         }

         CellCalcRowUpdate rowUpdate = new CellCalcRowUpdate();
         Pair calcCacheKey = new Pair(Integer.valueOf(context.mCellDeploymentId), Integer.valueOf(context.mCellShortId));
         if(updateType == CellCalcUpdateType.REPLACE && (context.mCellShortId >= this.mDynamicCellCalcImport.getNextCellCalcShortId() || this.mDynamicCellCalcImport.getUpdatedCellCalcIds().contains(calcCacheKey) || updateCalcIdsInThisRunUnit.contains(calcCacheKey))) {
            rowUpdate.setUpdateType(CellCalcUpdateType.MERGE);
         } else {
            if(updateType == CellCalcUpdateType.REPLACE) {
               while(currentFormModel.getRowCount() > 0) {
                  currentFormModel.deleteRow(0);
               }
            }

            rowUpdate.setUpdateType(this.mDynamicCellCalcImport.getImportType());
         }

         rowUpdate.setValues(this.mDynamicCellCalcImport.getCellCalcRowDataMap(rowData));
         rowUpdate.setAddress(this.mDynamicCellCalcImport.getCellCalcRowAddressMap(rowData));
         rowUpdate.applyUpdate(currentFormConfig, currentFormModel);
         if(context.mCellShortId < 0) {
            this.executeAndSaveCellCalc(currentFormConfig, currentFormModel, context);
         }

         if(context.mCellShortId > 0) {
            updateCalcIdsInThisRunUnit.add(new Pair(Integer.valueOf(context.mCellDeploymentId), Integer.valueOf(context.mCellShortId)));
         }
      }

      if(context != null) {
         this.executeAndSaveCellCalc(currentFormConfig, currentFormModel, context);
         if(context.mCellShortId > 0) {
            updateCalcIdsInThisRunUnit.add(new Pair(Integer.valueOf(context.mCellDeploymentId), Integer.valueOf(context.mCellShortId)));
         }
      }

      if(this.mDynamicCellCalcImport.getImportType() == CellCalcUpdateType.REPLACE && !updateCalcIdsInThisRunUnit.isEmpty()) {
         this.getCcDeploymentDAO().removeCalculatorInstanceDetails(this.getFinanceCubeId(), this.getTaskId(), updateCalcIdsInThisRunUnit, 500);
      }

      this.mDynamicCellCalcImport.getUpdatedCellCalcIds().addAll(updateCalcIdsInThisRunUnit);
      if(rows.size() > 0) {
         log.println("Imported " + rows.size() + " rows.");
      }

      return rows.size();
   }

   public int processExtraneousCalculators(PrintWriter pw, int startRow, int batchSize) throws ValidationException, Exception {
      if(this.mDynamicCellCalcImport.getImportType() != CellCalcUpdateType.REPLACE) {
         return 0;
      } else {
         pw.println("Processing extraneous calculator instances, start row:" + startRow + " batch size:" + batchSize + ".");
         List calcsToDelete = this.getCcDeploymentDAO().loadCalculatorInstanceDetailsBatch(this.getFinanceCubeId(), this.getTaskId(), startRow, batchSize);
         int dimCount = this.getDimCount();
         Iterator i$ = calcsToDelete.iterator();

         while(i$.hasNext()) {
            Pair entry = (Pair)i$.next();
            int ccDeploymentId = ((Integer)entry.getChild1()).intValue();
            int ccShortId = ((Integer)entry.getChild2()).intValue();
            this.getCellCalcUtils().deleteCalculator(dimCount, this.getFinanceCubeId(), ccDeploymentId, ccShortId, this.getTaskId(), this.getUserId());
         }

         pw.println("Removed " + calcsToDelete.size() + " cell calculator instance(s).");
         return calcsToDelete.size();
      }
   }

   public void deleteWorkTableData() throws ExcelGenericImportException {
      this.getImportGridDAO().deleteImportGridData(this.getModelId(), this.mDynamicCellCalcImport.getImportGridId());
      this.getCcDeploymentDAO().deleteImportCalcWorkRows(this.getFinanceCubeId(), this.getTaskId());
   }

   private int querySampleDeploymentId() throws ValidationException {
      if(!this.mDynamicCellCalcImport.getDeployments().isEmpty()) {
         String var5 = (String)this.mDynamicCellCalcImport.getDeployments().iterator().next();
         CcDeploymentsForModelELO var6 = this.getCcDeploymentDAO().getCcDeploymentsForModel(this.getModelId());

         for(int var7 = 0; var7 < var6.getNumRows(); ++var7) {
            CcDeploymentRefImpl ccDeploymentRef = (CcDeploymentRefImpl)var6.getValueAt(var7, "CcDeployment");
            if(ccDeploymentRef.getNarrative().equals(var5)) {
               return ccDeploymentRef.getCcDeploymentPK().getCcDeploymentId();
            }
         }

         throw new ValidationException("Unable to locate calculator deployment:" + var5);
      } else {
         HashMap lineData = new HashMap();
         lineData.putAll(this.getImportGridCellDAO().readFirstRowOfImport(this.mDynamicCellCalcImport.getImportGridId()));
         if(lineData.isEmpty()) {
            return -1;
         } else {
            Pair cellAddress = this.getTargetCellAddress(lineData);
            DynamicCellCalcImportEngine$CellCalcContext context = this.queryCellCalcDetails(this.getModelId(), this.getFinanceCubeId(), this.mDynamicCellCalcImport.getDimensionRefs(), (List)cellAddress.getChild1(), (String)cellAddress.getChild2(), this.mDynamicCellCalcImport.getCalendarInfo());
            return context.mCellDeploymentId;
         }
      }
   }

   private DynamicCellCalcImportEngine$CellCalcContext queryCellCalcDetails(int modelId, int financeCubeId, DimensionRefImpl[] dimRefs, List<String> cellAddress, String dataType, CalendarInfo calendarInfo) throws ValidationException {
      Pair addressInfo = this.queryAddressDetails(cellAddress, dimRefs, calendarInfo);
      StructureElementRefImpl[] addressRefs = (StructureElementRefImpl[])addressInfo.getChild1();
      int[] positions = (int[])addressInfo.getChild2();
      DataTypeRefImpl dataTypeRefImpl = this.queryDataTypeRef(dataType);
      if(dataTypeRefImpl == null) {
         throw new ValidationException("Unable to locate data type:" + dataType);
      } else {
         List runtimeDeployments = this.queryCellCalcAtCellAddress(modelId, financeCubeId, (StructureElementRefImpl[])Arrays.copyOf(addressRefs, addressRefs.length - 1), dataType);
         int numDims = dimRefs.length;
         CalendarElementNode calendarElementNode = calendarInfo.getById(addressRefs[numDims - 1]);
         int[] cellIds = new int[addressRefs.length];

         for(int runtimeDeployment = 0; runtimeDeployment < addressRefs.length; ++runtimeDeployment) {
            cellIds[runtimeDeployment] = addressRefs[runtimeDeployment].getId();
         }

         RuntimeCcDeployment var20 = null;
         Iterator calc_details = runtimeDeployments.iterator();

         while(calc_details.hasNext()) {
            RuntimeCubeDeployment context = (RuntimeCubeDeployment)calc_details.next();
            RuntimeCcDeployment deployment = (RuntimeCcDeployment)context;
            if(deployment.doesDeploymentTargetCell(Arrays.copyOf(cellIds, numDims - 1), dataType, calendarInfo, calendarElementNode)) {
               var20 = deployment;
               break;
            }
         }

         if(var20 == null) {
            throw new ValidationException("No calculator deployed to cell address:" + this.getFormattedAddress(cellAddress, dataType));
         } else {
            int[] var19 = this.getCcDeploymentDAO().queryCellCalcDetailsForCell(financeCubeId, cellIds, dataType);
            DynamicCellCalcImportEngine$CellCalcContext var21 = new DynamicCellCalcImportEngine$CellCalcContext();
            var21.mCellAddress = cellIds;
            var21.mCellAddressPositions = positions;
            var21.mCellDataType = dataType;
            var21.mCellRuntimeDeployment = var20;
            var21.mCellDeploymentId = var20.getCcDeploymentId();
            var21.mCellShortId = var19 != null?var19[0]:0;
            return var21;
         }
      }
   }

   private Pair<List<String>, String> getTargetCellAddress(Map<String, String> rowData) throws ValidationException {
      DimensionRefImpl[] modelDims = this.mDynamicCellCalcImport.getDimensionRefs();
      ArrayList addressStringValues = new ArrayList();

      for(int dataType = 0; dataType < modelDims.length; ++dataType) {
         String importColumnName = this.mDynamicCellCalcImport.getContextColumnForDimension(modelDims[dataType]);
         if(importColumnName == null) {
            throw new ValidationException("Unable to determine import context column for dimension:" + modelDims[dataType]);
         }

         addressStringValues.add(rowData.get(importColumnName));
         if(addressStringValues.get(dataType) == null || ((String)addressStringValues.get(dataType)).trim().length() == 0) {
            throw new ValidationException("Found null or empty key for context column on row 1 column:" + importColumnName);
         }
      }

      String var6 = (String)rowData.get(this.mDynamicCellCalcImport.getContextColumnDataType());
      if(var6 != null && var6.trim().length() != 0) {
         return new Pair(addressStringValues, var6);
      } else {
         throw new ValidationException("Found null or empty key for context data type column on row 1 column:" + this.mDynamicCellCalcImport.getContextColumnDataType());
      }
   }

   private void executeAndSaveCellCalc(FormConfig formConfig, DefaultFormDataInputModel formModel, DynamicCellCalcImportEngine$CellCalcContext context) throws ValidationException, Exception {
      CcDeploymentEVO deploymentEVO = this.getCcDeploymentDAO().getDetails(new CcDeploymentCK(this.mDynamicCellCalcImport.getModelRef().getModelPK(), new CcDeploymentPK(context.mCellDeploymentId)), "");
      int calendarElementId = context.mCellRuntimeDeployment.getCalendarElementIdForCell(context.mCellAddress, context.mCellDataType, this.mDynamicCellCalcImport.getCalendarInfo());
      CalendarElementNode calendarElementNode = this.mDynamicCellCalcImport.getCalendarInfo().getById(calendarElementId);
      Map dataTypeEvos = this.getDataTypeDAO().getAllForFinanceCube(this.getFinanceCubeId());
      HashMap cachedLookupData = new HashMap();
      String cellPostings = this.getCellCalcUtils().runCalcForUpdate(this.getModelId(), this.getFinanceCubeId(), formConfig, formModel, new HashMap(), context.mCellShortId, deploymentEVO, context.mCellRuntimeDeployment, this.mDynamicCellCalcImport.getCalendarInfo(), context.mCellAddress, context.mCellAddressPositions, context.mCellDataType, calendarElementNode, dataTypeEvos, cachedLookupData, (double)this.getAbsAllocThreshold());
      this.getCellCalcUtils().postChangesToFinanceCube(this.getFinanceCubeId(), context.mCellAddress.length, this.getTaskId(), this.getUserId(), cellPostings);
   }

   public void setModel(String modelVisId) throws ValidationException {
      if(this.mDynamicCellCalcImport == null) {
         this.mDynamicCellCalcImport = new DynamicCellCalcImport();
      }

      ModelRefImpl modelRef = this.queryModelRef(modelVisId);
      if(modelRef == null) {
         throw new ValidationException("Unable to locate model with visual Id:" + modelVisId);
      } else {
         this.mDynamicCellCalcImport.setModelRef(modelRef);
         this.mDynamicCellCalcImport.setDimensionRefs(this.queryModelDimensions(modelRef));
      }
   }

   public int getModelId() {
      return this.mDynamicCellCalcImport.getModelRef().getModelPK().getModelId();
   }

   public void setFinanceCube(String financeCubeVisId) throws ValidationException {
      FinanceCubeRefImpl financeCubeRef = this.queryFinanceCubeRef(this.mDynamicCellCalcImport.getModelRef(), financeCubeVisId);
      if(financeCubeRef == null) {
         throw new ValidationException("Failed to locate finance cube with visual Id:" + financeCubeVisId + " for model:" + this.mDynamicCellCalcImport.getModelRef());
      } else {
         this.mDynamicCellCalcImport.setFinanceCubeRef(financeCubeRef);
      }
   }

   public int getFinanceCubeId() {
      return this.mDynamicCellCalcImport.getFinanceCubeRef().getFinanceCubePK().getFinanceCubeId();
   }

   public void setBudgetCycle(String budgetCycleVisId) throws ValidationException {
      BudgetCycleRefImpl budgetCycleRef = this.queryBudgetCycleRef(this.mDynamicCellCalcImport.getModelRef(), budgetCycleVisId);
      if(budgetCycleRef == null) {
         throw new ValidationException("Failed to locate budget cycle with visual Id:" + budgetCycleVisId + " for model:" + this.mDynamicCellCalcImport.getModelRef());
      } else {
         this.mDynamicCellCalcImport.setBudgetCycleRef(budgetCycleRef);
      }
   }

   public void setUpdateType(String updateType) throws ValidationException {
      if(!updateType.equalsIgnoreCase("REPLACE") && !updateType.equalsIgnoreCase("MERGE")) {
         throw new ValidationException("Only update types of MERGE or REPLACE are supported not:" + updateType);
      } else {
         this.mDynamicCellCalcImport.setImportType(CellCalcUpdateType.parse(updateType));
      }
   }

   public void setDeployments(Set<String> deployments) throws ValidationException {
      CcDeploymentsForModelELO modelDeployments = this.getCcDeploymentDAO().getCcDeploymentsForModel(this.getModelId());
      HashMap modelDeploymentsMap = new HashMap();

      int importTargetXmlFormId;
      for(importTargetXmlFormId = 0; importTargetXmlFormId < modelDeployments.getNumRows(); ++importTargetXmlFormId) {
         modelDeploymentsMap.put(((CcDeploymentRef)modelDeployments.getValueAt(importTargetXmlFormId, "CcDeployment")).getNarrative(), Integer.valueOf(importTargetXmlFormId));
      }

      importTargetXmlFormId = -1;

      int xmlFormId;
      for(Iterator i$ = deployments.iterator(); i$.hasNext(); importTargetXmlFormId = xmlFormId) {
         String importDepVisId = (String)i$.next();
         Integer row = (Integer)modelDeploymentsMap.get(importDepVisId);
         if(row == null) {
            throw new ValidationException("Unable to locate deployment [" + importDepVisId + "] for model:" + this.mDynamicCellCalcImport.getModelRef());
         }

         xmlFormId = ((Integer)modelDeployments.getValueAt(row.intValue(), "XmlFormId")).intValue();
         if(importTargetXmlFormId != -1 && xmlFormId != importTargetXmlFormId) {
            throw new ValidationException("All calculator deployments referenced in the <deployments/> tag must target the same XML form Id");
         }
      }

      this.mDynamicCellCalcImport.setDeployments(deployments);
   }

   public void setCalendarFilters(List<Pair<String, String>> calendarFilters) throws ValidationException {
      if(this.mDynamicCellCalcImport.getCalendarInfo() == null) {
         this.mDynamicCellCalcImport.setCalendarInfo(this.queryCalendareInfo(this.mDynamicCellCalcImport.getModelRef()));
      }

      CalendarInfo calendarInfo = this.mDynamicCellCalcImport.getCalendarInfo();
      Iterator i$ = calendarFilters.iterator();

      while(i$.hasNext()) {
         Pair entry = (Pair)i$.next();
         CalendarElementNode from = calendarInfo.findElement((String)entry.getChild1());
         CalendarElementNode to = calendarInfo.findElement((String)entry.getChild2());
         if(from == null) {
            throw new ValidationException("Unable to locate calendar node for filter entry:" + (String)entry.getChild1());
         }

         if(to == null) {
            throw new ValidationException("Unable to locate calendar node for filter entry:" + (String)entry.getChild2());
         }

         if(from.getPosition() > to.getPosition()) {
            throw new ValidationException("In calendar filters, from entry:" + (String)entry.getChild2() + " occurs before to " + "entry:" + (String)entry.getChild1() + " in the calendar.");
         }

         this.mDynamicCellCalcImport.getCalendarFilter().add(new Pair(from, to));
      }

   }

   public void setContextColumns(List<Pair<String, String>> contextColumns, String dataTypeColumn) throws ValidationException {
      DimensionRefImpl[] modelDims = this.queryModelDimensions(this.mDynamicCellCalcImport.getModelRef());
      if(contextColumns.size() != modelDims.length) {
         throw new ValidationException("Model " + this.mDynamicCellCalcImport.getModelRef() + " contains " + modelDims.length + " dimensions, but the imports\' <context-columns> element defines " + contextColumns.size() + " columns. These values should be equal.");
      } else {
         Iterator i$ = contextColumns.iterator();

         while(i$.hasNext()) {
            Pair entry = (Pair)i$.next();
            DimensionRefImpl dim = this.findDimension(modelDims, (String)entry.getChild2());
            if(dim == null) {
               throw new ValidationException("Failed to locate dimension " + (String)entry.getChild2() + " referenced under " + "<context-columns> element.");
            }

            this.mDynamicCellCalcImport.registerContextColumn((String)entry.getChild1(), dim, false);
         }

         if(dataTypeColumn == null) {
            throw new ValidationException("The import must define which import column should be used to determine the target data type - see <context-columns/> element.");
         } else {
            this.mDynamicCellCalcImport.registerContextColumn(dataTypeColumn, (DimensionRefImpl)null, true);
         }
      }
   }

   private DimensionRefImpl findDimension(DimensionRefImpl[] dims, String name) {
      DimensionRefImpl[] arr$ = dims;
      int len$ = dims.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         DimensionRefImpl dim = arr$[i$];
         if(dim.getNarrative().equals(name)) {
            return dim;
         }
      }

      return null;
   }

   public void setRowKeys(Set<String> rowKeys) throws ValidationException {
      this.mDynamicCellCalcImport.setCellCalcRowKeys(rowKeys);
   }

   public void setColumnMappingsAutoMap(String autoMap) throws ValidationException {
      if(!autoMap.equalsIgnoreCase("all") && !autoMap.equalsIgnoreCase("mapped")) {
         throw new ValidationException("autoMap property must be set to all or mapped.");
      } else {
         this.mDynamicCellCalcImport.setAutoMap(autoMap);
      }
   }

   public void setColumnMappings(Map<String, ColumnMappingEntry> columnMappings) throws ValidationException {
      this.mDynamicCellCalcImport.setColumnMapping(columnMappings);
   }

   private String lookupServerURL(String clientURL) {
      Iterator i$ = this.mClientServerURL.iterator();

      Pair entry;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         entry = (Pair)i$.next();
      } while(!((String)entry.getChild1()).equals(clientURL) && !((String)entry.getChild1()).endsWith(clientURL));

      return (String)entry.getChild2();
   }

   public ImportGridDAO getImportGridDAO() {
      if(this.mImportGridDAO == null) {
         this.mImportGridDAO = new ImportGridDAO();
      }

      return this.mImportGridDAO;
   }

   public void setImportGridDAO(ImportGridDAO importGridDAO) {
      this.mImportGridDAO = importGridDAO;
   }

   public DynamicCellCalcImport getDynamicCellCalcImport() {
      return this.mDynamicCellCalcImport;
   }

   public void setDynamicCellCalcImport(DynamicCellCalcImport dynamicCellCalcImport) {
      this.mDynamicCellCalcImport = dynamicCellCalcImport;
   }

   public ImportGridCellDAO getImportGridCellDAO() {
      if(this.mImportGridCellDAO == null) {
         this.mImportGridCellDAO = new ImportGridCellDAO();
      }

      return this.mImportGridCellDAO;
   }

   public void setImportGridCellDAO(ImportGridCellDAO importGridCellDAO) {
      this.mImportGridCellDAO = importGridCellDAO;
   }

   public String getFormattedAddress(List<String> address, String dataType) {
      StringBuilder sb = new StringBuilder();
      Iterator i$ = address.iterator();

      while(i$.hasNext()) {
         String s = (String)i$.next();
         sb.append(s);
         sb.append(", ");
      }

      sb.append(dataType);
      return sb.toString();
   }

   private int getDimCount() {
      return this.mDynamicCellCalcImport.getDimensionRefs().length;
   }

   public CellCalculationDataDAO getCellCalculationDataDAO() {
      if(this.mCellCalculationDataDAO == null) {
         this.mCellCalculationDataDAO = new CellCalculationDataDAO();
      }

      return this.mCellCalculationDataDAO;
   }

   public void setCellCalculationDataDAO(CellCalculationDataDAO cellCalculationDataDAO) {
      this.mCellCalculationDataDAO = cellCalculationDataDAO;
   }
}
