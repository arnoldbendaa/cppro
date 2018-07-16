// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.cc.RuntimeCcDeployment;
import com.cedar.cp.api.model.cc.RuntimeCcDeploymentLine;
import com.cedar.cp.api.model.cc.RuntimeCcSummaryMapping;
import com.cedar.cp.api.model.udwp.Profile;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.dto.dimension.StructureElementELO;
import com.cedar.cp.dto.model.udwp.WeightingProfileRefImpl;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine;
import com.cedar.cp.ejb.impl.dataentry.DataEntryContextDAO;
import com.cedar.cp.ejb.impl.dataentry.DataEntryDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.ejb.impl.model.CellCalcDAO;
import com.cedar.cp.ejb.impl.model.CellCalculationDataDAO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileDAO;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.performance.PerformanceDatumImpl;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.CellCalculationEngine;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;
import com.cedar.cp.util.xmlform.inputs.LookupData;
import com.cedar.cp.util.xmlform.reader.XMLReader;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CellCalcUtils {

   private CcDeploymentDAO mCcDeploymentDAO;
   private CellCalcDAO mCellCalcDAO;
   private CellCalculationDataDAO mCellCalculationDataDAO;
   private DataEntryDAO mDataEntryDAO;
   private ModelDAO mModelDAO;
   private StructureElementDAO mStructureElementDAO;
   private BudgetCycleDAO mBudgetCycleDAO;
   private WeightingProfileDAO mWeightingProfileDAO;
   private CubeUpdateEngine mCubeUpdateEngine;
   private static final String sCubeUpdateXML = "<CubeUpdate>\n<UserId>{0}</UserId>\n<FinanceCubeId>{1}</FinanceCubeId>\n<AbsoluteValues>false</AbsoluteValues>\n<UpdateType>4</UpdateType>\n<Cells>\n{2}</Cells>\n</CubeUpdate>";
   private static final String sMeasureCellLineXML = "<Cell addr=\"{0}\" dataType=\"{1}\" {2}=\"{3}\" />\n";
   private static final String sFinancialCellLineXML = "<Cell addr=\"{0}\" dataType=\"{1}\" delta=\"{2}\"/>\n";
   private static final String sCellLinkXML = "<CellCalcLink addr=\"{0}\" dataType=\"{1}\" shortId=\"{2,number,0}\" cellCalcId=\"{3,number,0}\" />\n";
   private Map<Integer, Profile> mProfileCache = new HashMap();
   private transient DateFormat mTimeFormatter;
   private transient DateFormat mDateFormatter;
   private transient DateFormat mDateTimeFormatter;
   private transient StringBuffer mCellPostings = new StringBuffer();


   public void postChangesToFinanceCube(int financeCubeId, int numDims, int taskId, int userId, String cellPostings) throws Exception {
      CubeUpdateEngine updateEngine = this.getCubeUpdateEngine();
      updateEngine.setFinanceCubeId(financeCubeId);
      updateEngine.setNumDims(numDims);
      updateEngine.setTaskId(Integer.valueOf(taskId));
      String completeXML = MessageFormat.format("<CubeUpdate>\n<UserId>{0}</UserId>\n<FinanceCubeId>{1}</FinanceCubeId>\n<AbsoluteValues>false</AbsoluteValues>\n<UpdateType>4</UpdateType>\n<Cells>\n{2}</Cells>\n</CubeUpdate>", new Object[]{String.valueOf(userId), String.valueOf(financeCubeId), cellPostings});
      updateEngine.updateCube((Reader)(new StringReader(completeXML.toString())), true);
      updateEngine.setFinanceCubeHasDataFlag();
   }

   public void deleteCalculator(int dimCount, int financeCubeId, int cellCalcDeploymentId, int cellCalcShortId, int taskId, int userId) throws Exception {
      this.getCellCalculationDataDAO().deleteCellCalculatorData(financeCubeId, cellCalcShortId);
      EntityList counterPostings = this.getCellCalcDAO().queryCellCalculationInstanceBalances(dimCount, financeCubeId, cellCalcDeploymentId, cellCalcShortId);
      StringBuilder cellUpdatesBuffer = new StringBuilder();

      for(int row = 0; row < counterPostings.getNumRows(); ++row) {
         StringBuilder addressBuffer = new StringBuilder();

         for(int dataType = 0; dataType < dimCount; ++dataType) {
            addressBuffer.append(counterPostings.getValueAt(row, "dim" + dataType));
            if(dataType < dimCount - 1) {
               addressBuffer.append(",");
            }
         }

         String var15 = (String)counterPostings.getValueAt(row, "data_type");
         double delta = (double)(-1 * ((Integer)counterPostings.getValueAt(row, "number_value")).intValue());
         delta /= 10000.0D;
         String cellUpdateXML = MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\" delta=\"{2}\"/>\n", new Object[]{addressBuffer.toString(), var15, String.valueOf(delta)});
         cellUpdatesBuffer.append(cellUpdateXML);
      }

      this.getCellCalcDAO().deleteCellCalculatorLinks(financeCubeId, cellCalcDeploymentId, cellCalcShortId);
      if(cellUpdatesBuffer.length() > 0) {
         this.postChangesToFinanceCube(financeCubeId, dimCount, taskId, userId, cellUpdatesBuffer.toString());
      }

   }

   public Pair<CellCalculationEngine, Map<String, Object>> executeCalc(int modelId, int financeCubeId, FormConfig formConfig, FormDataInputModel formModel, Map summaryData, int shortId, CcDeploymentEVO deploymentEVO, CalendarInfo calendarInfo, int[] cellAddress, CalendarElementNode calendarNode, Map<Object, LookupData> cachedLookupData) throws Exception {
      Integer[] context = deploymentEVO.getDimContextArray();
      Map contextVariables = this.setupContextVariables(modelId, calendarInfo, calendarNode, cellAddress, context, cachedLookupData);
      DataEntryContextDAO formContext = new DataEntryContextDAO(this.getDataEntryDAO().getConnection(), contextVariables);
      formConfig.buildVariables((PerformanceDatumImpl)null, formContext);
      Iterator engine = formConfig.getVariables().keySet().iterator();

      while(engine.hasNext()) {
         Object newSummaries = engine.next();
         Object o = formConfig.getVariables().get(newSummaries);
         if(o instanceof LookupData && cachedLookupData.get(newSummaries) == null) {
            cachedLookupData.put(newSummaries, (LookupData)o);
         }
      }

      if(shortId >= 0 && formModel == null && summaryData == null) {
         formModel = this.getDataEntryDAO().getCalculationPreviousData(formConfig, financeCubeId, shortId);
         summaryData = this.getDataEntryDAO().getCalculationSummaryData(formConfig, financeCubeId, shortId);
      }

      CellCalculationEngine engine1 = new CellCalculationEngine(formConfig);
      Map newSummaries1 = engine1.runCalculator(formConfig.getVariables(), formModel, summaryData);
      return new Pair(engine1, newSummaries1);
   }

   private Map setupContextVariables(int modelId, CalendarInfo calendarInfo, CalendarElementNode calendarNode, int[] cellAddress, Integer[] dimContext, Map cachedLookupData) {
      HashMap contextVariables = new HashMap();
      this.mCellPostings = new StringBuffer();
      contextVariables.put(WorkbookProperties.MODEL_ID.toString(), Integer.valueOf(modelId));
      contextVariables.put(WorkbookProperties.CALENDAR_INFO.toString() + modelId, calendarInfo);

      for(int i = 0; i < cellAddress.length; ++i) {
         Integer elementId = Integer.valueOf(cellAddress[i]);
         contextVariables.put(WorkbookProperties.DIM_$.toString().replace("$", String.valueOf(i)), elementId);
         Object visId = null;
         if(i < cellAddress.length - 1) {
            StructureElementELO node = this.getStructureElementDAO().getStructureElement(elementId.intValue());
            visId = node.getValueAt(0, "VisId");
         } else {
            CalendarElementNode var12 = calendarInfo.getById(elementId);
            visId = var12.getFullPathVisId();
         }

         contextVariables.put(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(i)), visId);
      }

      contextVariables.put("lookupDate", calendarNode.getActualDate());
      contextVariables.put("columnDate", calendarNode.getActualDate());
      contextVariables.putAll(cachedLookupData);
      return contextVariables;
   }

   public String runCalcForUpdate(int modelId, int financeCubeId, FormConfig formConfig, FormDataInputModel formModel, Map summaryData, int shortId, CcDeploymentEVO deploymentEVO, RuntimeCcDeployment deployment, CalendarInfo calendarInfo, int[] cellAddress, int[] cellPositions, String dataType, CalendarElementNode calendarNode, Map<String, DataTypeEVO> dataTypeEvos, Map<Object, LookupData> cachedLookupData, double absAllocThreshold) throws Exception {
      this.mCellPostings.setLength(0);
      Integer[] context = deploymentEVO.getDimContextArray();
      Map contextVariables = this.setupContextVariables(modelId, calendarInfo, calendarNode, cellAddress, context, cachedLookupData);
      DataEntryContextDAO formContext = new DataEntryContextDAO(this.getDataEntryDAO().getConnection(), contextVariables);
      formConfig.buildVariables((PerformanceDatumImpl)null, formContext);
      Iterator engine = formConfig.getVariables().keySet().iterator();

      while(engine.hasNext()) {
         Object newSummaries = engine.next();
         Object cellCalcDataUpdatesXML = formConfig.getVariables().get(newSummaries);
         if(cellCalcDataUpdatesXML instanceof LookupData && cachedLookupData.get(newSummaries) == null) {
            cachedLookupData.put(newSummaries, (LookupData)cellCalcDataUpdatesXML);
         }
      }

      if(shortId >= 0 && formModel == null && summaryData == null) {
         formModel = this.getDataEntryDAO().getCalculationPreviousData(formConfig, financeCubeId, shortId);
         summaryData = this.getDataEntryDAO().getCalculationSummaryData(formConfig, financeCubeId, shortId);
      }

      CellCalculationEngine var38 = new CellCalculationEngine(formConfig);
      Map var36 = var38.runCalculator(formConfig.getVariables(), formModel, summaryData);
      String var37 = this.createCellCalcDataUpdateXML(shortId, var38.getFormModel().getNonProtectedValues());
      RuntimeCcDeploymentLine deploymentLine = deployment.queryDeploymentLine(cellAddress, dataType, calendarInfo, calendarNode);
      if(deploymentLine == null) {
         throw new IllegalStateException("Unable to locate deployment line for cell : [" + Arrays.toString(cellAddress) + "] dataType:" + dataType + " calNode:" + calendarNode.getFullPathVisId());
      } else {
         List cellMappings = deployment.expandCellAddressMappings(cellAddress, cellPositions, deployment.getDeploymentLineId(deploymentLine));
         Iterator i$ = cellMappings.iterator();

         while(i$.hasNext()) {
            RuntimeCcSummaryMapping mapping = (RuntimeCcSummaryMapping)i$.next();
            int[] cellIds = mapping.getCellAddress();
            String summaryKey = mapping.getSummaryField();
            Object value = var36.get(summaryKey);
            Map requiredValues = var38.getSummarySpreads(summaryKey, calendarInfo.getById(calendarNode.getStructureElementId()));
            if(!calendarNode.isLeaf()) {
               if(requiredValues == null && value instanceof Double) {
                  requiredValues = this.calculateSpreads(modelId, cellIds[0], cellIds[cellIds.length - 2], mapping.getDataType(), calendarNode, (Double)value, Double.valueOf(absAllocThreshold));
               }

               Enumeration cellLink = calendarNode.depthFirstEnumeration();

               while(cellLink.hasMoreElements()) {
                  CalendarElementNode calNode = (CalendarElementNode)cellLink.nextElement();
                  if(calNode.isLeaf()) {
                     int[] cloneCellAddress = new int[cellIds.length];

                     for(int i = 0; i < cellIds.length - 1; ++i) {
                        cloneCellAddress[i] = cellIds[i];
                     }

                     cloneCellAddress[cellIds.length - 1] = calNode.getStructureElementId();
                     this.processSingleCell(financeCubeId, cloneCellAddress, value, requiredValues, calNode, (DataTypeEVO)dataTypeEvos.get(mapping.getDataType()), shortId, deployment.getCcDeploymentId());
                  }
               }
            } else if(this.getBudgetCycleDAO().isCalendarIdWithinOpenBudgetCycle(modelId, calendarNode.getStructureElementId())) {
               this.processSingleCell(financeCubeId, cellIds, value, (Map)null, calendarNode, (DataTypeEVO)dataTypeEvos.get(mapping.getDataType()), shortId, deployment.getCcDeploymentId());
            }

            String var39 = MessageFormat.format("<CellCalcLink addr=\"{0}\" dataType=\"{1}\" shortId=\"{2,number,0}\" cellCalcId=\"{3,number,0}\" />\n", new Object[]{this.getCommaSeperatedAddress(cellIds), mapping.getDataType(), Integer.valueOf(shortId), Integer.valueOf(deployment.getCcDeploymentId())});
            this.mCellPostings.append(var39);
         }

         this.mCellPostings.append(var37);
         formContext.getSqlConnection().close();
         return this.mCellPostings.toString();
      }
   }

   public FormDataInputModel loadFormModel(FormConfig formConfig, int financeCubeId, int shortId) {
      return this.getDataEntryDAO().getCalculationPreviousData(formConfig, financeCubeId, shortId);
   }

   public Map loadFormSummaryMap(FormConfig formConfig, int financeCubeId, int shortId) {
      return this.getDataEntryDAO().getCalculationSummaryData(formConfig, financeCubeId, shortId);
   }

   private void processSingleCell(int financeCubeId, int[] cellAddress, Object value, Map<Integer, Double> requiredValues, CalendarElementNode calNode, DataTypeEVO dataTypeEvo, int shortId, int deploymentId) {
      String dataType = dataTypeEvo.getVisId();
      Object currentValue = this.getCurrentCellValue(financeCubeId, cellAddress, dataType);
      double original;
      double required;
      Double posting;
      double posting1;
      if(dataTypeEvo.isMeasure()) {
         if(dataTypeEvo.isMeasureNumeric()) {
            original = currentValue != null?((BigDecimal)currentValue).doubleValue():0.0D;
            required = ((Double)value).doubleValue();
            if(requiredValues != null) {
               posting = (Double)requiredValues.get(Integer.valueOf(calNode.getStructureElementId()));
               required = posting != null?posting.doubleValue():0.0D;
            }

            posting1 = required - original;
            if(posting1 != 0.0D) {
               this.addMeasureCellPosting(cellAddress, dataTypeEvo, Double.valueOf(posting1), shortId, deploymentId);
            }
         } else {
            this.addMeasureCellPosting(cellAddress, dataTypeEvo, value, shortId, deploymentId);
         }
      } else if(value instanceof Double) {
         original = currentValue != null?((BigDecimal)currentValue).doubleValue():0.0D;
         required = ((Double)value).doubleValue();
         if(requiredValues != null) {
            posting = (Double)requiredValues.get(Integer.valueOf(calNode.getStructureElementId()));
            required = posting != null?posting.doubleValue():0.0D;
         }

         posting1 = required - original;
         double bounds = 1.0E-4D;
         if(Math.abs(posting1) > bounds) {
            this.addFinancialCellPosting(cellAddress, dataTypeEvo, posting1, shortId, deploymentId);
         }
      }

   }

   public FormConfig loadCellCalculationDetails(int xmlFormId) throws Exception {
      String[] config = this.getDataEntryDAO().getXMLFormConfig(xmlFormId);
      String definition = config[1];
      XMLReader reader = new XMLReader();
      reader.init();
      StringReader sr = new StringReader(definition);
      reader.parseConfigFile(sr);
      FormConfig formConfig = reader.getFormConfig();
      return formConfig;
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
         WeightingProfileDAO weightingProfileDAO = this.getWeightingProfileDAO();
         EntityList profiles = weightingProfileDAO.queryProfiles(modelId, summaryNode.getElemType(), this.queryTargetLeafType(summaryNode), acct, cc, dataType);
         int[] weightings1;
         if(profiles.getNumRows() == 0) {
            weightings1 = new int[leaves.size()];
            Arrays.fill(weightings1, 1);
         } else {
            WeightingProfileRef wpRef = (WeightingProfileRef)profiles.getValueAt(0, "WeightingProfile");
            Profile profile = this.getWeightingProfile(wpRef, summaryNode);
            if(profile != null && this.sumWeightings(profile.getWeightings()) != 0) {
               weightings1 = profile.getWeightings();
            } else {
               weightings1 = new int[leaves.size()];
               Arrays.fill(weightings1, 1);
            }
         }

         this.spreadValue(value.doubleValue(), weightings1, leaves, spreads, absAllocThreshold.doubleValue());
      }

      return spreads;
   }

   private int sumWeightings(int[] weightings) {
      int total = 0;
      int[] arr$ = weightings;
      int len$ = weightings.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int i = arr$[i$];
         total += i;
      }

      return total;
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

   public String createCellCalcDataUpdateXML(int shortId, FormDataInputModel model) {
      StringBuilder xml = new StringBuilder();
      xml.append("<CellCalc shortId=\"" + shortId + "\" >");

      for(int i = 0; i < model.getRowCount(); ++i) {
         for(int j = 0; j < model.getColumnCount(); ++j) {
            String colId = model.getColumnId(j);
            Object value = model.getValueAt(i, j);
            if(value != null) {
               xml.append("<CellCalcData rowId=\"" + i + "\" colId=\"" + colId + "\" >");
               if(value instanceof BigDecimal) {
                  xml.append("<NumericValue>" + value + "</NumericValue>");
               } else {
                  xml.append("<StringValue><![CDATA[" + value + "]]></StringValue>");
               }

               xml.append("</CellCalcData>");
            }
         }
      }

      xml.append("</CellCalc>");
      return xml.toString();
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

   public CellCalcDAO getCellCalcDAO() {
      if(this.mCellCalcDAO == null) {
         this.mCellCalcDAO = new CellCalcDAO();
      }

      return this.mCellCalcDAO;
   }

   public void setCellCalcDAO(CellCalcDAO cellCalcDAO) {
      this.mCellCalcDAO = cellCalcDAO;
   }

   public DataEntryDAO getDataEntryDAO() {
      if(this.mDataEntryDAO == null) {
         this.mDataEntryDAO = new DataEntryDAO();
      }

      return this.mDataEntryDAO;
   }

   public void setDataEntryDAO(DataEntryDAO dataEntryDAO) {
      this.mDataEntryDAO = dataEntryDAO;
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

   public ModelDAO getModelDAO() {
      if(this.mModelDAO == null) {
         this.mModelDAO = new ModelDAO();
      }

      return this.mModelDAO;
   }

   public void setModelDAO(ModelDAO modelDAO) {
      this.mModelDAO = modelDAO;
   }

   public CcDeploymentDAO getCcDeploymentDAO() {
      if(this.mCcDeploymentDAO == null) {
         this.mCcDeploymentDAO = new CcDeploymentDAO();
      }

      return this.mCcDeploymentDAO;
   }

   public void setCcDeploymentDAO(CcDeploymentDAO ccDeploymentDAO) {
      this.mCcDeploymentDAO = ccDeploymentDAO;
   }

   public StructureElementDAO getStructureElementDAO() {
      if(this.mStructureElementDAO == null) {
         this.mStructureElementDAO = new StructureElementDAO();
      }

      return this.mStructureElementDAO;
   }

   public void setStructureElementDAO(StructureElementDAO structureElementDAO) {
      this.mStructureElementDAO = structureElementDAO;
   }

   public BudgetCycleDAO getBudgetCycleDAO() {
      if(this.mBudgetCycleDAO == null) {
         this.mBudgetCycleDAO = new BudgetCycleDAO();
      }

      return this.mBudgetCycleDAO;
   }

   public void setBudgetCycleDAO(BudgetCycleDAO budgetCycleDAO) {
      this.mBudgetCycleDAO = budgetCycleDAO;
   }

   public WeightingProfileDAO getWeightingProfileDAO() {
      if(this.mWeightingProfileDAO == null) {
         this.mWeightingProfileDAO = new WeightingProfileDAO();
      }

      return this.mWeightingProfileDAO;
   }

   public void setWeightingProfileDAO(WeightingProfileDAO weightingProfileDAO) {
      this.mWeightingProfileDAO = weightingProfileDAO;
   }

   public CubeUpdateEngine getCubeUpdateEngine() {
      if(this.mCubeUpdateEngine == null) {
         this.mCubeUpdateEngine = new CubeUpdateEngine();
      }

      return this.mCubeUpdateEngine;
   }

   public void setCubeUpdateEngine(CubeUpdateEngine cubeUpdateEngine) {
      this.mCubeUpdateEngine = cubeUpdateEngine;
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

   private Object getCurrentCellValue(int fcId, int[] cellAddress, String dataType) {
      DataEntryDAO dao = this.getDataEntryDAO();
      return dao.getCellValue(fcId, cellAddress, dataType);
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
}
