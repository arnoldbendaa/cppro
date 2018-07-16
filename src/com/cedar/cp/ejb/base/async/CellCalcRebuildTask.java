// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.dataEntry.DataEntryProcess;
import com.cedar.cp.api.model.cc.RuntimeCcDeployment;
import com.cedar.cp.api.model.cc.RuntimeCcSummaryMapping;
import com.cedar.cp.api.model.cc.RuntimeCubeDeployment;
import com.cedar.cp.api.model.udwp.Profile;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.dimension.StructureElementELO;
import com.cedar.cp.dto.dimension.calendar.CalendarInfoImpl;
import com.cedar.cp.dto.model.CellCalcRebuildTaskRequest;
import com.cedar.cp.dto.model.cc.CcDeploymentCK;
import com.cedar.cp.dto.model.udwp.WeightingProfileRefImpl;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.CellCalcRebuildTask$MyCheckpoint;
import com.cedar.cp.ejb.base.async.TaskReport;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine;
import com.cedar.cp.ejb.impl.dataentry.DataEntryContextDAO;
import com.cedar.cp.ejb.impl.dataentry.DataEntryDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementEVO;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.ejb.impl.model.CellCalculationDataDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileDAO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.performance.PerformanceDatumImpl;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.CellCalculationEngine;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;
import com.cedar.cp.util.xmlform.inputs.LookupData;
import com.cedar.cp.util.xmlform.reader.XMLReader;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.InitialContext;

public class CellCalcRebuildTask extends AbstractTask {

   private transient InitialContext mInitialContext;
   private transient NumberFormat mNumberFormat;
   private transient DataEntryProcess mDataEntryProcess;
   private transient DataEntryDAO mDataEntryDao;
   private transient StringBuffer mCellPostings = new StringBuffer();
   private transient Map<Integer, String> mStructureElementId2VisId = new HashMap();
   private transient DateFormat mTimeFormatter;
   private transient DateFormat mDateFormatter;
   private transient DateFormat mDateTimeFormatter;
   private transient Log mLog = new Log(this.getClass());
   private static final String sCubeUpdateXML = "<CubeUpdate>\n<UserId>{0}</UserId>\n<FinanceCubeId>{1}</FinanceCubeId>\n<AbsoluteValues>false</AbsoluteValues>\n<UpdateType>4</UpdateType>\n<Cells>\n{2}</Cells>\n</CubeUpdate>";
   private static final String sMeasureCellLineXML = "<Cell addr=\"{0}\" dataType=\"{1}\" {2}=\"{3}\" />\n";
   private static final String sFinancialCellLineXML = "<Cell addr=\"{0}\" dataType=\"{1}\" delta=\"{2}\"/>\n";
   private static final String sCellLinkXML = "<CellCalcLink addr=\"{0}\" dataType=\"{1}\" shortId=\"{2,number,0}\" cellCalcId=\"{3,number,0}\" />\n";
   private Map<Integer, Profile> mProfileCache = new HashMap();
   private boolean mExceptions = false;
   private StructureElementDAO mStructureElementDAO;


   public void runUnitOfWork(InitialContext context) throws Exception {
      this.mInitialContext = context;
      if(this.getCheckpoint() == null) {
         this.firstTime();
      } else {
         this.doWork();
      }

   }

   private void firstTime() throws Exception {
      this.setCheckpoint(new CellCalcRebuildTask$MyCheckpoint());
      this.getTaskReport().addReport();
      this.getTaskReport().addParamSection("PARAMETERS");
      this.getTaskReport().addParam("Cell Deployment", this.getMyRequest().getCcDeploymentVisId() + " - " + this.getMyRequest().getCcDeploymentDescription());
      this.getTaskReport().addParam("Lookup Table Changed?", String.valueOf(this.getMyRequest().isLookupChanged()));
      this.getTaskReport().addParam("Context Changed?", String.valueOf(this.getMyRequest().isContextChanged()));
      this.getTaskReport().addParam("Lines Changed?", String.valueOf(this.getMyRequest().isLinesChanged()));
      this.getTaskReport().addParam("Mappings Changed?", String.valueOf(this.getMyRequest().isMappingsChanged()));
      this.getTaskReport().addEndParamSection();
      this.getTaskReport().flushText();
   }

   private String formatDouble(double value) {
      if(this.mNumberFormat == null) {
         this.mNumberFormat = NumberFormat.getInstance();
         this.mNumberFormat.setMinimumFractionDigits(2);
         this.mNumberFormat.setMaximumFractionDigits(4);
      }

      return this.mNumberFormat.format(value);
   }

   private void doWork() throws Exception {
      CcDeploymentCK ck = this.getMyRequest().getCcDeploymentCK();
      ModelDAO modelDAO = new ModelDAO();
      ModelEVO modelEvo = modelDAO.getDetails(ck.getModelPK(), "<9><0><41>");
      this.log("model=" + modelEvo.getVisId() + "\t" + modelEvo.getPK());
      CcDeploymentEVO deploymentEvo = modelEvo.getCellCalcDeploymentsItem(this.getMyRequest().getCcDeploymentCK().getCcDeploymentPK());
      if(deploymentEvo == null) {
         this.getTaskReport().addParamSection("WARNING");
         this.getTaskReport().addParam(this.getMyRequest().getCcDeploymentVisId() + " - " + this.getMyRequest().getCcDeploymentDescription(), "Cannot be found");
         this.getTaskReport().addEndParamSection();
      } else {
         this.log("ccdeployment=" + deploymentEvo.getVisId() + "\t" + deploymentEvo.getPK());
         Iterator iter = modelEvo.getFinanceCubes().iterator();

         while(iter.hasNext()) {
            FinanceCubeEVO fcubeEvo = (FinanceCubeEVO)iter.next();
            this.log("cube=" + fcubeEvo.getVisId() + "\t " + fcubeEvo.getPK());
            this.mLog.debug("doWork", "Processing fcube " + fcubeEvo.getVisId() + " - " + fcubeEvo.getDescription());
            this.getTaskReport().addMatrixSection("FINANCE CUBE PROCESSING");
            this.getTaskReport().addRow();
            this.getTaskReport().addCellHeading(fcubeEvo.getVisId() + " - " + fcubeEvo.getDescription());
            this.getTaskReport().addEndRow();
            this.processFinanceCube(modelEvo, fcubeEvo, deploymentEvo);
            this.getTaskReport().addEndMatrixSection();
         }
      }

      this.getTaskReport().addEndReport();
      this.getTaskReport().setCompleted();
      this.setCheckpoint((TaskCheckpoint)null);
      if(this.mExceptions) {
         this.setCompletionExceptionMessage("shortId/deployment mismatches found");
      }

   }

   private void processFinanceCube(ModelEVO modelEvo, FinanceCubeEVO fcubeEvo, CcDeploymentEVO deploymentEvo) throws Exception {
      if(fcubeEvo.getLockedByTaskId() != null) {
         this.getTaskReport().addRow();
         this.getTaskReport().addCellText("Finance Cube is locked by taskId=" + fcubeEvo.getLockedByTaskId());
         this.getTaskReport().addEndRow();
      } else if(!fcubeEvo.getHasData()) {
         this.getTaskReport().addRow();
         this.getTaskReport().addCellText("Finance Cube has no data");
         this.getTaskReport().addEndRow();
      } else {
         this.mCellPostings.setLength(0);
         StructureElementDAO structureDAO = new StructureElementDAO();
         CalendarInfoImpl calendarInfo = structureDAO.getCalendarInfoForModel(modelEvo.getModelId());
         CellCalcRebuildTaskRequest request = this.getMyRequest();
         CellCalculationDataDAO calcDataDAO = new CellCalculationDataDAO();
         int financeCubeId = fcubeEvo.getFinanceCubeId();
         int cellDeploymentId = request.getCcDeploymentCK().getCcDeploymentPK().getCcDeploymentId();
         if(request.isContextChanged() || request.isLinesChanged() || request.isMappingsChanged()) {
            this.checkCellsInFinanceCube(modelEvo, financeCubeId, cellDeploymentId, calendarInfo);
         }

         SystemPropertyDAO systemPropertyDAO = new SystemPropertyDAO();
         String propStr = systemPropertyDAO.getValue("ALLOC: Threshold");
         double allocationThreshold = 100.0D;

         try {
            allocationThreshold = Double.parseDouble(propStr);
         } catch (NumberFormatException var17) {
            this.mLog.warn("processFinanceCube", "Unable to parse ALLOC: Threshold:" + propStr);
         }

         this.runCellCalculations(modelEvo, financeCubeId, deploymentEvo, calendarInfo, allocationThreshold);
         calcDataDAO.deleteAllNonReferencedCellData(financeCubeId, cellDeploymentId);
         this.log("numCellPostings=" + this.mCellPostings.length());
         if(this.mCellPostings.length() > 0) {
            int nDimensions = modelEvo.getModelDimensionRels().size();
            CubeUpdateEngine updateEngine = new CubeUpdateEngine();
            updateEngine.setFinanceCubeId(fcubeEvo.getFinanceCubeId());
            updateEngine.setNumDims(nDimensions);
            updateEngine.setTaskId(Integer.valueOf(this.getTaskId()));
            String completeXML = MessageFormat.format("<CubeUpdate>\n<UserId>{0}</UserId>\n<FinanceCubeId>{1}</FinanceCubeId>\n<AbsoluteValues>false</AbsoluteValues>\n<UpdateType>4</UpdateType>\n<Cells>\n{2}</Cells>\n</CubeUpdate>", new Object[]{String.valueOf(this.getUserId()), String.valueOf(fcubeEvo.getFinanceCubeId()), this.mCellPostings.toString()});
            if(this.mLog.isDebugEnabled()) {
               this.mLog.debug("Submitting xml " + completeXML);
            }

            updateEngine.updateCube(new StringReader(completeXML.toString()));
            updateEngine.setFinanceCubeHasDataFlag();
         }

      }
   }

   private void checkCellsInFinanceCube(ModelEVO modelEvo, int financeCubeId, int cellDeploymentId, CalendarInfo calendarInfo) {
      int nDimensions = modelEvo.getModelDimensionRels().size();
      CellCalculationDataDAO calcDataDAO = new CellCalculationDataDAO();
      CcDeploymentDAO deploymentDAO = new CcDeploymentDAO();
      List shortIds = calcDataDAO.getCellCalcShortIds(financeCubeId, cellDeploymentId);
      this.log("checkCellsInFinanceCube: numShortIds=" + shortIds.size());
      Iterator i$ = shortIds.iterator();

      while(i$.hasNext()) {
         Integer shortId = (Integer)i$.next();
         List cells = calcDataDAO.getCFTCellsForShortId(financeCubeId, shortId.intValue(), nDimensions);
         Iterator i$1 = cells.iterator();

         while(i$1.hasNext()) {
            Object[] cell = (Object[])i$1.next();
            int[] cellAddress = new int[cell.length - 1];

            for(int dataType = 0; dataType < cellAddress.length; ++dataType) {
               cellAddress[dataType] = ((Integer)cell[dataType]).intValue();
            }

            String var24 = cell[cell.length - 1].toString();
            HashSet dataTypeSet = new HashSet();
            dataTypeSet.add(var24);
            ArrayList dimIndexes = new ArrayList();

            for(int deployments = 0; deployments < cellAddress.length; ++deployments) {
               dimIndexes.add(Integer.valueOf(deployments));
            }

            List var25 = deploymentDAO.getRelevantCubeDeployments(modelEvo.getModelId(), financeCubeId, cellAddress, dataTypeSet, dimIndexes, false);
            boolean targetted = false;
            if(!var25.isEmpty()) {
               Iterator i$2 = var25.iterator();

               while(i$2.hasNext()) {
                  RuntimeCubeDeployment cubeDeployment = (RuntimeCubeDeployment)i$2.next();
                  RuntimeCcDeployment testDeployment = (RuntimeCcDeployment)cubeDeployment;
                  if(testDeployment.getCcDeploymentId() != cellDeploymentId) {
                     targetted = true;
                     break;
                  }

                  CalendarElementNode periodNode = calendarInfo.getById(cellAddress[cell.length - 2]);
                  if(testDeployment.doesDeploymentTargetCell(cellAddress, var24, calendarInfo, periodNode)) {
                     targetted = true;
                     break;
                  }
               }
            }

            if(!targetted) {
               calcDataDAO.deleteCFTCell(financeCubeId, shortId.intValue(), cellAddress, var24);
            }
         }
      }

   }

   private void runCellCalculations(ModelEVO modelEvo, int financeCubeId, CcDeploymentEVO deploymentEvo, CalendarInfo calendarInfo, double absAllocThreshold) throws Exception {
      TaskReport report = this.getTaskReport();
      int nDimensions = modelEvo.getModelDimensionRels().size();
      CellCalculationDataDAO calcDataDAO = new CellCalculationDataDAO();
      CcDeploymentDAO deploymentDAO = new CcDeploymentDAO();
      BudgetCycleDAO budgetCycleDao = new BudgetCycleDAO();
      new StructureElementDAO();
      HashMap cachedLookupData = new HashMap();
      FormConfig formConfig = this.loadCellCalculationDetails(deploymentEvo.getXmlformId());
      Map dataTypeEvos = (new DataTypeDAO()).getAllForFinanceCube(financeCubeId);
      List shortIds = calcDataDAO.getCellCalcShortIds(financeCubeId, deploymentEvo.getCcDeploymentId());
      this.log("runCellCalculations: numShortIds=" + shortIds.size());
      Iterator i$ = shortIds.iterator();

      while(i$.hasNext()) {
         Integer shortId = (Integer)i$.next();
         report.addRow();
         report.addCellHeading("Running Cell Calculation Short ID " + shortId);
         report.addEndRow();
         List cells = calcDataDAO.getCFTCellsForShortId(financeCubeId, shortId.intValue(), nDimensions);
         if(!cells.isEmpty()) {
            report.addRow();

            for(int contextVariables = 0; contextVariables < nDimensions; ++contextVariables) {
               if(contextVariables == 0) {
                  report.addCellHeading("RA", "left");
               } else if(contextVariables == nDimensions - 2) {
                  report.addCellHeading("Account", "left");
               } else if(contextVariables == nDimensions - 1) {
                  report.addCellHeading("Calendar", "left");
               } else {
                  report.addCellHeading(" ");
               }
            }

            report.addCellHeading("Data Type", "left");
            report.addCellHeading("Old Value", "right");
            report.addCellHeading("New Value", "right");
            report.addCellHeading("Posting", "right");
            report.addEndRow();
            HashMap var51 = new HashMap();
            var51.put(WorkbookProperties.MODEL_ID.toString(), Integer.valueOf(modelEvo.getModelId()));
            var51.put(WorkbookProperties.CALENDAR_INFO.toString() + modelEvo.getModelId(), calendarInfo);
            Object[] aCell = (Object[])cells.get(0);
            DataEntryDAO dao = this.getDataEntryDao();
            List elements = dao.getCellCalculationContext(financeCubeId, shortId.intValue());
            int dataType;
            if(elements != null) {
               int cellAddress = 0;
               dataType = elements.size() / 3;

               for(int deployment = 0; deployment < dataType; ++deployment) {
                  int contextDataTypes = ((Integer)elements.get(cellAddress++)).intValue();
                  var51.put(WorkbookProperties.DIM_$.toString().replace("$", String.valueOf(deployment)), Integer.valueOf(contextDataTypes));
                  String deployments = (String)elements.get(cellAddress++);
                  var51.put(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(deployment)), deployments);
                  String calendarElementId = (String)elements.get(cellAddress++);
                  var51.put(WorkbookProperties.DIMENSION_$_DESCRIPTION.toString().replace("$", String.valueOf(deployment)), calendarElementId);
               }
            }

            var51.putAll(cachedLookupData);
            int[] var52 = new int[aCell.length - 1];

            for(dataType = 0; dataType < var52.length; ++dataType) {
               var52[dataType] = ((Integer)aCell[dataType]).intValue();
            }

            String var53 = aCell[aCell.length - 1].toString();
            RuntimeCcDeployment var55 = null;
            HashSet var56 = new HashSet();
            var56.add(var53);
            List var54 = deploymentDAO.getRelevantCubeDeployments(modelEvo.getModelId(), financeCubeId, var52, var56, Arrays.asList(new Integer[]{Integer.valueOf(var52.length - 1)}), false);
            Iterator var60 = var54.iterator();

            while(var60.hasNext()) {
               RuntimeCubeDeployment calendarNode = (RuntimeCubeDeployment)var60.next();
               RuntimeCcDeployment formContext = (RuntimeCcDeployment)calendarNode;
               if(formContext.getCcDeploymentId() == deploymentEvo.getCcDeploymentId()) {
                  var55 = formContext;
                  break;
               }
            }

            CalendarElementNode var58;
            if(var55 == null) {
               StringBuilder var61 = new StringBuilder();

               for(int var57 = 0; var57 < var52.length - 1; ++var57) {
                  var61.append(var57 == 0?"":",").append(this.getStructureElementVisId(var52[var57]));
               }

               var58 = calendarInfo.getById(var52[var52.length - 1]);
               var61.append(",").append(var58.getFullPathVisId());
               var61.append(",").append(var53);
               StringBuilder var66 = new StringBuilder();

               for(int var65 = 0; var65 < var52.length; ++var65) {
                  var66.append(var65 == 0?"":",").append(String.valueOf(var52[var65]));
               }

               var66.append(",").append(var53);
               this.logInfo("not in deployment: " + var61.toString() + " (" + var66.toString() + ") shortId=" + shortId);
               this.mExceptions = true;
            } else {
               int var62 = var55.getCalendarElementIdForCell(var52, var53, calendarInfo);
               if(var62 != -1) {
                  var52[var52.length - 1] = var62;
                  var58 = calendarInfo.getById(var62);
                  var51.put("lookupDate", var58.getActualDate());
                  var51.put("columnDate", var58.getActualDate());
                  DataEntryContextDAO var59 = new DataEntryContextDAO(var51);
                  formConfig.buildVariables((PerformanceDatumImpl)null, var59);
                  Iterator previousData = formConfig.getVariables().keySet().iterator();

                  while(previousData.hasNext()) {
                     Object summaryData = previousData.next();
                     Object engine = formConfig.getVariables().get(summaryData);
                     if(engine instanceof LookupData && cachedLookupData.get(summaryData) == null) {
                        cachedLookupData.put(summaryData, (LookupData)engine);
                     }
                  }

                  FormDataInputModel var64 = this.getDataEntryDao().getCalculationPreviousData(formConfig, financeCubeId, shortId.intValue());
                  Map var63 = this.getDataEntryDao().getCalculationSummaryData(formConfig, financeCubeId, shortId.intValue());
                  CellCalculationEngine var67 = new CellCalculationEngine(formConfig);
                  Map newSummaries = var67.runCalculator(formConfig.getVariables(), var64, var63);
                  int[] cellPositionsTemplate = new int[var52.length];
                  List cellMappings = var55.expandCellAddressMappings(var52, cellPositionsTemplate, -1);
                  Map accountElementMap = this.loadAccountStructureElements(cellMappings);
                  Iterator i$1 = cellMappings.iterator();

                  while(i$1.hasNext()) {
                     RuntimeCcSummaryMapping mapping = (RuntimeCcSummaryMapping)i$1.next();
                     int[] cellIds = mapping.getCellAddress();
                     StructureElementEVO accountStructureElementEVO = (StructureElementEVO)accountElementMap.get(Integer.valueOf(cellIds[cellIds.length - 2]));
                     boolean isCredit = accountStructureElementEVO.getIsCredit();
                     String summaryKey = mapping.getSummaryField();
                     Object value = newSummaries.get(summaryKey);
                     Map requiredValues = var67.getSummarySpreads(summaryKey, calendarInfo.getById(var62));
                     if(!var58.isLeaf()) {
                        if(requiredValues == null && value instanceof Double) {
                           requiredValues = this.calculateSpreads(modelEvo.getModelId(), cellIds[0], cellIds[cellIds.length - 2], mapping.getDataType(), var58, (Double)value, Double.valueOf(absAllocThreshold));
                        }

                        Enumeration cellLink = var58.depthFirstEnumeration();

                        while(cellLink.hasMoreElements()) {
                           CalendarElementNode calNode = (CalendarElementNode)cellLink.nextElement();
                           if(calNode.isLeaf()) {
                              int[] cloneCellAddress = new int[cellIds.length];

                              for(int i = 0; i < cellIds.length - 1; ++i) {
                                 cloneCellAddress[i] = cellIds[i];
                              }

                              cloneCellAddress[cellIds.length - 1] = calNode.getStructureElementId();
                              var51.put("lookupDate", calNode.getActualDate());
                              this.processSingleCell(financeCubeId, cloneCellAddress, value, requiredValues, calNode, (DataTypeEVO)dataTypeEvos.get(mapping.getDataType()), shortId.intValue(), var55.getCcDeploymentId(), isCredit);
                           }
                        }
                     } else if(budgetCycleDao.isCalendarIdWithinOpenBudgetCycle(modelEvo.getModelId(), var58.getStructureElementId())) {
                        this.processSingleCell(financeCubeId, cellIds, value, (Map)null, var58, (DataTypeEVO)dataTypeEvos.get(mapping.getDataType()), shortId.intValue(), var55.getCcDeploymentId(), isCredit);
                     }

                     String var68 = MessageFormat.format("<CellCalcLink addr=\"{0}\" dataType=\"{1}\" shortId=\"{2,number,0}\" cellCalcId=\"{3,number,0}\" />\n", new Object[]{this.getCommaSeperatedAddress(cellIds), mapping.getDataType(), shortId, Integer.valueOf(var55.getCcDeploymentId())});
                     this.mCellPostings.append(var68);
                  }

                  var59.getSqlConnection().close();
               }
            }
         }
      }

   }

   private void processSingleCell(int financeCubeId, int[] cellAddress, Object value, Map<Integer, Double> requiredValues, CalendarElementNode calNode, DataTypeEVO dataTypeEvo, int shortId, int deploymentId, boolean isCredit) {
      String dataType = dataTypeEvo.getVisId();
      Object currentValue = this.getCurrentCellValue(financeCubeId, cellAddress, dataType);
      if(isCredit) {
         if(value instanceof BigDecimal) {
            value = ((BigDecimal)value).negate();
         } else if(value instanceof Double) {
            value = Double.valueOf(-((Double)value).doubleValue());
         }

         if(requiredValues != null) {
            HashMap report = new HashMap();
            Iterator original = ((Map)requiredValues).entrySet().iterator();

            while(original.hasNext()) {
               Entry entry = (Entry)original.next();
               report.put(entry.getKey(), Double.valueOf(-((Double)entry.getValue()).doubleValue()));
            }

            requiredValues = report;
         }
      }

      TaskReport var21 = this.getTaskReport();
      var21.addRow();

      for(int var23 = 0; var23 < cellAddress.length - 1; ++var23) {
         var21.addCellText(this.getStructureElementVisId(cellAddress[var23]));
      }

      var21.addCellText(calNode.getFullPathVisId());
      var21.addCellText(dataType);
      double required;
      Double posting;
      double var22;
      double var24;
      if(dataTypeEvo.isMeasure()) {
         if(dataTypeEvo.isMeasureNumeric()) {
            var22 = currentValue != null?((BigDecimal)currentValue).doubleValue():0.0D;
            var21.addCellNumber(this.formatDouble(var22));
            required = ((Double)value).doubleValue();
            if(requiredValues != null) {
               posting = (Double)((Map)requiredValues).get(Integer.valueOf(calNode.getStructureElementId()));
               required = posting != null?posting.doubleValue():0.0D;
            }

            var21.addCellNumber(this.formatDouble(required));
            var24 = required - var22;
            var21.addCellNumber(this.formatDouble(var24));
            if(var24 != 0.0D) {
               this.addMeasureCellPosting(cellAddress, dataTypeEvo, Double.valueOf(var24), shortId, deploymentId);
            }
         } else {
            var21.addCellText(currentValue != null?currentValue.toString():" ");
            var21.addCellText(value.toString());
            var21.addCellText(" ");
            this.addMeasureCellPosting(cellAddress, dataTypeEvo, value, shortId, deploymentId);
         }
      } else if(value instanceof Double) {
         var22 = currentValue != null?((BigDecimal)currentValue).doubleValue():0.0D;
         var21.addCellNumber(this.formatDouble(var22));
         required = ((Double)value).doubleValue();
         if(requiredValues != null) {
            posting = (Double)((Map)requiredValues).get(Integer.valueOf(calNode.getStructureElementId()));
            required = posting != null?posting.doubleValue():0.0D;
         }

         var21.addCellNumber(this.formatDouble(required));
         var24 = required - var22;
         double bounds = 1.0E-4D;
         if(Math.abs(var24) > bounds) {
            var21.addCellNumber(this.formatDouble(var24));
            this.addFinancialCellPosting(cellAddress, dataTypeEvo, var24, shortId, deploymentId);
         }
      }

      var21.addEndRow();
   }

   private Map<Integer, Double> calculateSpreads(int modelId, int cc, int acct, String dataType, CalendarElementNode summaryNode, Double value, Double absAllocThreshold) {
      HashMap spreads = new HashMap();
      ArrayList leaves = new ArrayList();
      Enumeration e = summaryNode.depthFirstEnumeration();

      while(e.hasMoreElements()) {
         CalendarElementNode weightings = (CalendarElementNode)e.nextElement();
         if(weightings.isLeaf()) {
            leaves.add(Integer.valueOf(weightings.getStructureElementId()));
         }
      }

      if(!leaves.isEmpty()) {
         WeightingProfileDAO weightingProfileDAO = new WeightingProfileDAO();
         EntityList profiles = weightingProfileDAO.queryProfiles(modelId, summaryNode.getElemType(), this.queryTargetLeafType(summaryNode), acct, cc, dataType);
         int[] weightings1;
         if(profiles.getNumRows() == 0) {
            weightings1 = new int[leaves.size()];
            Arrays.fill(weightings1, 1);
         } else {
            WeightingProfileRef wpRef = (WeightingProfileRef)profiles.getValueAt(0, "WeightingProfile");
            Profile profile = this.getWeightingProfile(wpRef, summaryNode);
            if(profile == null) {
               weightings1 = new int[leaves.size()];
               Arrays.fill(weightings1, 1);
            } else {
               weightings1 = profile.getWeightings();
            }
         }

         this.spreadValue(value.doubleValue(), weightings1, leaves, spreads, absAllocThreshold.doubleValue());
      }

      return spreads;
   }

   private Profile getWeightingProfile(WeightingProfileRef weightingProfileRef, CalendarElementNode summaryNode) {
      int weightingProfileId = ((WeightingProfileRefImpl)weightingProfileRef).getWeightingProfilePK().getProfileId();
      Profile profile = (Profile)this.mProfileCache.get(Integer.valueOf(weightingProfileId));
      if(profile == null) {
         WeightingProfileDAO weightingProfileDAO = new WeightingProfileDAO();
         profile = weightingProfileDAO.getWeightingProfileDetail(weightingProfileId, summaryNode.getStructureElementId());
         if(profile != null) {
            this.mProfileCache.put(Integer.valueOf(weightingProfileId), profile);
         }
      }

      return (Profile)profile;
   }

   private void spreadValue(double value, int[] weightings, List<Integer> leaves, Map<Integer, Double> spreadMap, double absAllocThreshold) {
      double allocated = 0.0D;
      double toAllocate = value;
      byte spreadPrecision = 1;
      if(Math.abs(value) < absAllocThreshold) {
         spreadPrecision = 100;
      }

      int sumOfWeightings = 0;
      int[] remainder = weightings;
      int weighting = weightings.length;

      int weighting1;
      int i;
      for(i = 0; i < weighting; ++i) {
         weighting1 = remainder[i];
         sumOfWeightings += weighting1;
      }

      if(sumOfWeightings != 0) {
         for(int var24 = 0; var24 < Math.min(weightings.length, leaves.size()); ++var24) {
            weighting = weightings[var24];
            i = ((Integer)leaves.get(var24)).intValue();
            if((double)weighting != 0.0D) {
               double var25 = toAllocate * (double)spreadPrecision * (double)weighting / (double)sumOfWeightings;
               long l = (long)(var25 < 0.0D?Math.floor(var25):Math.ceil(var25));
               double alloc = (double)l / (double)spreadPrecision;
               spreadMap.put(Integer.valueOf(i), Double.valueOf(alloc));
               allocated += alloc;
            } else {
               spreadMap.put(Integer.valueOf(i), Double.valueOf(0.0D));
            }
         }
      }

      double var23 = toAllocate - allocated;
      if(var23 != 0.0D) {
         var23 = var23 * 100.0D / 100.0D;

         for(i = 0; i < Math.min(weightings.length, leaves.size()); ++i) {
            weighting1 = weightings[i];
            int leafId = ((Integer)leaves.get(i)).intValue();
            if(weighting1 != 0) {
               spreadMap.put(Integer.valueOf(leafId), Double.valueOf(((Double)spreadMap.get(Integer.valueOf(leafId))).doubleValue() + var23));
               double var10000 = allocated + var23;
               break;
            }
         }
      }

   }

   private int queryTargetLeafType(CalendarElementNode node) {
      while(!node.isLeaf()) {
         node = (CalendarElementNode)node.getChildAt(0);
      }

      return node.getElemType();
   }

   private Map<Integer, StructureElementEVO> loadAccountStructureElements(List<RuntimeCcSummaryMapping> cellMappings) {
      HashSet accountsSet = new HashSet();
      Iterator accounts = cellMappings.iterator();

      while(accounts.hasNext()) {
         RuntimeCcSummaryMapping i = (RuntimeCcSummaryMapping)accounts.next();
         int[] i$ = i.getCellAddress();
         int account = i$[i$.length - 2];
         accountsSet.add(Integer.valueOf(account));
      }

      int[] var7 = new int[accountsSet.size()];
      int var8 = 0;

      Integer var10;
      for(Iterator var9 = accountsSet.iterator(); var9.hasNext(); var7[var8++] = var10.intValue()) {
         var10 = (Integer)var9.next();
      }

      return this.getStructureElementDAO().getStructureElements(var7);
   }

   private FormConfig loadCellCalculationDetails(int xmlFormId) throws Exception {
      DataEntryDAO dao = this.getDataEntryDao();
      String[] config = dao.getXMLFormConfig(xmlFormId);
      String visId = config[0];
      String definition = config[1];
      this.mLog.debug("loaded xml form id=" + xmlFormId + " visId=" + visId);
      this.log("xmlForm=" + visId + "\t(XmlFormId=" + xmlFormId + ")");
      XMLReader reader = new XMLReader();
      reader.init();
      StringReader sr = new StringReader(definition);
      reader.parseConfigFile(sr);
      FormConfig formConfig = reader.getFormConfig();
      return formConfig;
   }

   private String getStructureElementVisId(int structureElementId) {
      Integer key = Integer.valueOf(structureElementId);
      String visId = (String)this.mStructureElementId2VisId.get(key);
      if(visId == null) {
         StructureElementDAO dao = new StructureElementDAO();
         StructureElementELO elo = dao.getStructureElement(structureElementId);
         visId = (String)elo.getValueAt(0, "VisId");
         this.mStructureElementId2VisId.put(key, visId);
      }

      return visId;
   }

   private void addMeasureCellPosting(int[] cellAddress, DataTypeEVO dataTypeEvo, Object value, int shortId, int deploymentId) {
      String dataType = dataTypeEvo.getVisId();
      String cellAddressString = this.getCommaSeperatedAddress(cellAddress);
      String xml = "";
      if(dataTypeEvo.isMeasureBoolean()) {
         xml = MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\" {2}=\"{3}\" />\n", new Object[]{cellAddressString, dataType, "booleanValue", value});
      } else if(dataTypeEvo.isMeasureDate()) {
         xml = MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\" {2}=\"{3}\" />\n", new Object[]{cellAddressString, dataType, "dateValue", this.getDateFormatter().format(value)});
      } else if(dataTypeEvo.isMeasureDateTime()) {
         xml = MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\" {2}=\"{3}\" />\n", new Object[]{cellAddressString, dataType, "dateTimeValue", this.getDateTimeFormatter().format(value)});
      } else if(dataTypeEvo.isMeasureNumeric()) {
         xml = MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\" {2}=\"{3}\" />\n", new Object[]{cellAddressString, dataType, "numberValue", value});
      } else if(dataTypeEvo.isMeasureString()) {
         xml = MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\" {2}=\"{3}\" />\n", new Object[]{cellAddressString, dataType, "stringValue", value});
      } else if(dataTypeEvo.isMeasureTime()) {
         xml = MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\" {2}=\"{3}\" />\n", new Object[]{cellAddressString, dataType, "timeValue", this.getTimeFormatter().format(value)});
      }

      this.mCellPostings.append(xml);
   }

   private void addFinancialCellPosting(int[] cellAddress, DataTypeEVO dataTypeEvo, double delta, int shortId, int deploymentId) {
      String dataType = dataTypeEvo.getVisId();
      String cellAddressString = this.getCommaSeperatedAddress(cellAddress);
      String posting = MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\" delta=\"{2}\"/>\n", new Object[]{cellAddressString, dataType, String.valueOf(delta)});
      this.mCellPostings.append(posting);
   }

   private String getCommaSeperatedAddress(int[] cellAddress) {
      StringBuilder cellAddressBuffer = new StringBuilder();

      for(int i = 0; i < cellAddress.length; ++i) {
         if(i > 0) {
            cellAddressBuffer.append(',');
         }

         cellAddressBuffer.append(String.valueOf(cellAddress[i]));
      }

      return cellAddressBuffer.toString();
   }

   private Object getCurrentCellValue(int fcId, int[] cellAddress, String dataType) {
      DataEntryDAO dao = this.getDataEntryDao();
      return dao.getCellValue(fcId, cellAddress, dataType);
   }

   private DataEntryDAO getDataEntryDao() {
      if(this.mDataEntryDao == null) {
         this.mDataEntryDao = new DataEntryDAO();
      }

      return this.mDataEntryDao;
   }

   public int getReportType() {
      return 7;
   }

   public CellCalcRebuildTask$MyCheckpoint getCheckpoint() {
      return (CellCalcRebuildTask$MyCheckpoint)super.getCheckpoint();
   }

   public CellCalcRebuildTaskRequest getMyRequest() {
      return (CellCalcRebuildTaskRequest)this.getRequest();
   }

   public String getEntityName() {
      return "CellCalcRebuildTask";
   }

   private DateFormat getTimeFormatter() {
      if(this.mTimeFormatter == null) {
         this.mTimeFormatter = new SimpleDateFormat("HH:mm:ss");
      }

      return this.mTimeFormatter;
   }

   private DateFormat getDateFormatter() {
      if(this.mDateFormatter == null) {
         this.mDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
      }

      return this.mDateFormatter;
   }

   private DateFormat getDateTimeFormatter() {
      if(this.mDateTimeFormatter == null) {
         this.mDateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      }

      return this.mDateTimeFormatter;
   }

   private StructureElementDAO getStructureElementDAO() {
      if(this.mStructureElementDAO == null) {
         this.mStructureElementDAO = new StructureElementDAO();
      }

      return this.mStructureElementDAO;
   }
}
