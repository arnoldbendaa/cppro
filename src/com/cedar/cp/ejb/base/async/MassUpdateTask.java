// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.dataEntry.MassUpdateTaskRequest;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.calendar.CalendarInfoImpl;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.MassUpdateTask$ActivityWriter;
import com.cedar.cp.ejb.base.async.MassUpdateTask$MyCheckpoint;
import com.cedar.cp.ejb.base.async.TaskReport;
import com.cedar.cp.ejb.base.async.XMLReportUtils;
import com.cedar.cp.ejb.impl.datatype.DataTypeAccessor;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDimensionRelEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityEVO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityLinkEVO;
import com.cedar.cp.ejb.impl.task.TaskDAO;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.util.Timer;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;

public class MassUpdateTask extends AbstractTask {

   private transient InitialContext mInitialContext;
   private transient ModelAccessor mModelAccessor;
   private transient DimensionAccessor mDimensionAccessor;
   private transient DataTypeAccessor mDataTypeAccessor;
   private transient StructureElementDAO mStructureElementDAO;
   private transient NumberFormat mNumberFormat;
   private transient NumberFormat mDecimalFormat;
   private transient MassUpdateTask$ActivityWriter mActivityWriter;


   public void runUnitOfWork(InitialContext context) throws Exception {
      this.mInitialContext = context;
      if(this.getCheckpoint() == null) {
         this.firstTime();
      } else {
         this.doWork();
      }

   }

   private void firstTime() throws Exception {
      this.setCheckpoint(new MassUpdateTask$MyCheckpoint());
      this.getTargets();
      this.printControls(this.getTaskReport(), true);
      if(this.getCheckpoint().getPosting() == 0L) {
         this.getTaskReport().addEndReport();
         this.getTaskReport().setCompleted();
         this.setCheckpoint((TaskCheckpoint)null);
      } else {
         this.getTaskReport().flushText();
      }

   }

   public TaskReport createTaskReport() {
      return new TaskReport(this.getUserId(), this.getTaskId(), this.getReportType(), this.getCheckpoint(), true, !this.getMyRequest().isReport());
   }

   private void printControls(XMLReportUtils xml, boolean firstTime) throws Exception {
      ModelEVO modelEvo = this.getModelAccessor().getDetails(new ModelPK(this.getMyRequest().getModelId()), "<9><0>");
      int numDims = modelEvo.getModelDimensionRels().size();
      if(firstTime) {
         this.getCheckpoint().setNumDimensions(numDims);
      }

      FinanceCubeEVO fcubeEvo = modelEvo.getFinanceCubesItem(new FinanceCubePK(this.getMyRequest().getFinanceCubeId()));
      CalendarInfoImpl calinfo = this.getStructureElementDAO().getCalendarInfoForFinanceCube(fcubeEvo.getFinanceCubeId());
      DimensionEVO[] dimEvo = new DimensionEVO[this.getCheckpoint().getNumDimensions()];
      String[] dimNames = new String[numDims];
      String[] dimDescrs = new String[numDims];

      for(int dtEvo = 0; dtEvo < numDims; ++dtEvo) {
         ModelDimensionRelEVO i = this.getModelDimRelBySequence(modelEvo, dtEvo);
         int seidList = i.getDimensionId();
         dimEvo[dtEvo] = this.getDimensionAccessor().getDetails(new DimensionPK(seidList), "<3>");
         dimNames[dtEvo] = dimEvo[dtEvo].getVisId();
         dimDescrs[dtEvo] = dimEvo[dtEvo].getDescription();
      }

      if(firstTime) {
         this.getCheckpoint().setDimNames(dimNames);
         this.getCheckpoint().setDimDescrs(dimDescrs);
      }

      DataTypeEVO var17 = this.getDataTypeAccessor().getDetails(new DataTypePK((short)this.getMyRequest().getDataTypeId()), "");
      xml.addReport();
      xml.addParamSection("WHY");
      xml.addParam("Model", this.getMyRequest().getModelVisId() + " - " + modelEvo.getDescription());
      xml.addParam("Finance Cube", this.getMyRequest().getFinanceCubeVisId() + " - " + fcubeEvo.getDescription());
      xml.addParam("Reason", this.getMyRequest().getReason());
      xml.addParam("Reference", this.getMyRequest().getReference());
      xml.addEndParamSection();
      xml.addParamSection("WHAT");

      int j;
      StructureElementKey o;
      HierarchyEVO hierEvo;
      int var16;
      List var18;
      for(var16 = 0; var16 < this.getMyRequest().getChangeCells().size(); ++var16) {
         xml.addParam("Change Cell");
         xml.addParamIndent(0);
         var18 = (List)this.getMyRequest().getChangeCells().get(var16);
         if(!this.isSameSourceCellAsPrevious(var16)) {
            for(j = 0; j < numDims; ++j) {
               o = (StructureElementKey)var18.get(j);
               xml.addParam("dimension", dimNames[j] + " - " + dimDescrs[j]);
               hierEvo = dimEvo[j].getHierarchiesItem(new HierarchyPK(o.getStructureId()));
               xml.addParam("hierarchy", hierEvo.getVisId() + " - " + hierEvo.getDescription());
               if(j < numDims - 1) {
                  xml.addParam("element", o.toString());
               } else {
                  xml.addParam("element", calinfo.getById(o.getId()).getFullPathVisId());
               }
            }
         }

         StructureElementKey var19 = (StructureElementKey)var18.get(numDims);
         xml.addParam("data type", var19.toString());
         if(firstTime) {
            this.getCheckpoint().addSourceDataType(var19.toString());
         }

         xml.addEndParamIndent();
         xml.addEndParam();
      }

      xml.addParam("Current Value", this.formatLong(this.getCheckpoint().getCurrentValue()));
      if(this.getMyRequest().getChangeType().equalsIgnoreCase("Percentage")) {
         xml.addParam("Update By", this.getMyRequest().getChangePercent() + "%");
      } else if(this.getMyRequest().getChangeTo().equals("0")) {
         xml.addParam("Update By", this.formatBigDecimal(this.getMyRequest().getChangeBy()));
      } else {
         xml.addParam("Update To", this.getMyRequest().getChangeTo());
      }

      xml.addParam("Posting", this.formatLong(this.getCheckpoint().getPosting()));
      if(this.getCheckpoint().isDataTypeInSource(this.getMyRequest().getDataTypeVisId())) {
         xml.addParam("Target Value", this.formatLong(this.getCheckpoint().getCurrentValue() + this.getCheckpoint().getPosting()));
      }

      xml.addEndParamSection();
      xml.addParamSection("WHERE");
      xml.addParam("Post to Data Type", this.getMyRequest().getDataTypeVisId() + " - " + var17.getDescription());
      xml.addParam("Post to Calendar Element", calinfo.getById(this.getMyRequest().getCalId()).getFullPathVisId());
      xml.addParam("Round to nearest", String.valueOf(Math.pow(10.0D, (double)this.getMyRequest().getRoundUnits())));
      xml.addEndParamSection();
      xml.addParamSection("HOLD CELLS");
      xml.addParam("Hold all negative values", this.getMyRequest().isHoldNegative()?"yes":"no");
      xml.addParam("Hold all positive values", this.getMyRequest().isHoldPositive()?"yes":"no");

      for(var16 = 0; var16 < this.getMyRequest().getHoldCells().size(); ++var16) {
         xml.addParam("Hold Cells");
         xml.addParamIndent(0);
         var18 = (List)this.getMyRequest().getHoldCells().get(var16);

         for(j = 0; j < var18.size(); ++j) {
            o = (StructureElementKey)var18.get(j);
            hierEvo = dimEvo[j].getHierarchiesItem(new HierarchyPK(o.getStructureId()));
            xml.addParam("hierarchy", hierEvo.getVisId() + " - " + hierEvo.getDescription());
            xml.addParam("element", o.toString());
         }

         xml.addEndParamIndent();
         xml.addEndParam();
      }

      xml.addEndParamSection();
      xml.addParamSection("FINISH");
      xml.addParam("Generate report only", this.getMyRequest().isReport()?"yes":"no");
      xml.addParam("Output cell postings to the report", this.getMyRequest().isCellPosting()?"yes":"no");
      xml.addEndParamSection();
   }

   private boolean isSameSourceCellAsPrevious(int index) {
      if(index == 0) {
         return false;
      } else {
         List prevSeidList = (List)this.getMyRequest().getChangeCells().get(index - 1);
         List thisSeidList = (List)this.getMyRequest().getChangeCells().get(index);

         for(int j = 0; j < prevSeidList.size() - 1; ++j) {
            StructureElementKey prevElem = (StructureElementKey)prevSeidList.get(j);
            StructureElementKey thisElem = (StructureElementKey)thisSeidList.get(j);
            if(prevElem.getId() != thisElem.getId()) {
               return false;
            }
         }

         return true;
      }
   }

   private void getTargets() {
      int sysScale = 0;

      for(int currentValueBD = 10000; currentValueBD != 0; ++sysScale) {
         currentValueBD /= 10;
      }

      BigDecimal var6 = new BigDecimal(this.getMyRequest().getCurrentValue());
      BigDecimal changeByBD = null;
      BigDecimal targetValueBD;
      if(this.getMyRequest().getChangeType().equalsIgnoreCase("Percentage")) {
         targetValueBD = new BigDecimal(this.getMyRequest().getChangePercent());
         changeByBD = var6.multiply(targetValueBD).movePointLeft(2);
      } else {
         changeByBD = (new BigDecimal(this.getMyRequest().getChangeBy())).setScale(sysScale, 4);
         if(changeByBD.unscaledValue().longValue() == 0L) {
            targetValueBD = (new BigDecimal(this.getMyRequest().getChangeTo())).setScale(sysScale, 4);
            changeByBD = targetValueBD.subtract(var6);
         }
      }

      targetValueBD = var6.add(changeByBD);
      targetValueBD = targetValueBD.setScale(0, 4);
      targetValueBD = targetValueBD.divide(new BigDecimal(String.valueOf(Math.pow(10.0D, (double)this.getMyRequest().getRoundUnits()))), 4);
      targetValueBD = targetValueBD.multiply(new BigDecimal(String.valueOf(Math.pow(10.0D, (double)this.getMyRequest().getRoundUnits()))));
      changeByBD = targetValueBD.subtract(var6);
      BigDecimal scaleFactorBD = new BigDecimal(10000);
      this.getCheckpoint().setCurrentValue(var6.multiply(scaleFactorBD).longValue());
      this.getCheckpoint().setPosting(changeByBD.multiply(scaleFactorBD).longValue());
   }

   private ModelDimensionRelEVO getModelDimRelBySequence(ModelEVO modelEvo, int seq) {
      Iterator i = modelEvo.getModelDimensionRels().iterator();

      ModelDimensionRelEVO dimRelEvo;
      do {
         if(!i.hasNext()) {
            throw new IllegalStateException("can\'t find dimRel seqnum " + seq);
         }

         dimRelEvo = (ModelDimensionRelEVO)i.next();
      } while(dimRelEvo.getDimensionSeqNum() != seq);

      return dimRelEvo;
   }

   private String formatLong(long num) {
      if(this.mNumberFormat == null) {
         this.mNumberFormat = NumberFormat.getInstance();
         this.mNumberFormat.setMinimumFractionDigits(2);
         this.mNumberFormat.setMaximumFractionDigits(4);
      }

      double numDouble = (double)num;
      String s = this.mNumberFormat.format(numDouble / 10000.0D);
      return s;
   }

   private String formatBigDecimal(String str) {
      return this.formatBigDecimal(new BigDecimal(str));
   }

   private String formatBigDecimal(BigDecimal num) {
      if(this.mDecimalFormat == null) {
         this.mDecimalFormat = DecimalFormat.getInstance();
         this.mDecimalFormat.setMinimumFractionDigits(2);
         this.mDecimalFormat.setMaximumFractionDigits(8);
      }

      String s = this.mNumberFormat.format(num);
      return s;
   }

   private void doWork() throws SQLException, Exception {
      Object info = null;
      if(this.getCheckpoint().getStepNumber() != 2) {
         this.massUpdateSimpleStep();
      } else {
         int events;
         List sdf;
         for(events = 0; events < this.getMyRequest().getChangeCells().size(); ++events) {
            sdf = (List)this.getMyRequest().getChangeCells().get(events);
            this.massUpdateStep2A(sdf);
         }

         for(events = 0; events < this.getMyRequest().getHoldCells().size(); ++events) {
            sdf = (List)this.getMyRequest().getHoldCells().get(events);
            this.massUpdateStep2B(sdf);
         }

         this.massUpdateStep2C();
      }

      if(this.getCheckpoint().getStepNumber() == 3) {
         this.getTaskReport().addTaskSection("TASK MESSAGES");
         EntityList var9 = (new TaskDAO()).getEvents(this.getTaskId());
         SimpleDateFormat var10 = new SimpleDateFormat("ddMMM HH:mm:ss");

         for(int i = 0; i < var9.getNumRows(); ++i) {
            String eventLine = "";
            int eventType = ((BigDecimal)var9.getValueAt(i, "EVENT_TYPE")).intValue();
            Timestamp eventDate = (Timestamp)var9.getValueAt(i, "EVENT_TIME");
            String eventText = (String)var9.getValueAt(i, "EVENT_TEXT");
            if(eventType != 2) {
               eventLine = eventLine + var10.format(eventDate) + " " + eventText;
               this.getTaskReport().addTaskMessage(eventLine);
            }
         }

         this.getTaskReport().addEndTaskSection();
         this.getTaskReport().addEndReport();
         this.getTaskReport().setCompleted();
         this.setCheckpoint((TaskCheckpoint)null);
      } else {
         this.getTaskReport().flushText();
         this.getCheckpoint().setStepNumberUp();
      }

   }

   private void massUpdateStep2A(List seidList) {
      CallableStatement stmt = null;

      try {
         StringBuffer e = new StringBuffer();
         StringBuffer elementIds = new StringBuffer();
         int numDims = this.getCheckpoint().getNumDimensions();

         for(int dataTypeVisId = 0; dataTypeVisId < numDims; ++dataTypeVisId) {
            StructureElementKey seKey = (StructureElementKey)seidList.get(dataTypeVisId);
            if(dataTypeVisId > 0) {
               e.append(' ');
               elementIds.append(' ');
            }

            e.append(seKey.getStructureId());
            elementIds.append(seKey.getId());
         }

         String var13 = seidList.get(numDims).toString().substring(0, 2);
         this.mLog.info("massUpdateStep2A", this.getTaskId() + "," + this.getMyRequest().getFinanceCubeId() + "," + e.toString() + "," + elementIds.toString() + "," + var13 + "," + (this.getMyRequest().isHoldNegative()?"-":(this.getMyRequest().isHoldPositive()?"+":null)));
         stmt = this.getConnection().prepareCall("{ call mass_update.step_2A(?,?,?,?,?) }");
         stmt.setInt(1, this.getTaskId());
         stmt.setInt(2, this.getMyRequest().getFinanceCubeId());
         stmt.setString(3, e.toString());
         stmt.setString(4, elementIds.toString());
         stmt.setString(5, var13);
         stmt.execute();
      } catch (SQLException var11) {
         var11.printStackTrace();
         throw new RuntimeException(var11);
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }
   }

   private void massUpdateStep2B(List seidList) {
      Object info = null;
      CallableStatement stmt = null;

      try {
         int e = this.getCheckpoint().getNumDimensions();
         StringBuffer structureIds = new StringBuffer();
         StringBuffer elementIds = new StringBuffer();

         for(int j = 0; j < e; ++j) {
            StructureElementKey seKey = (StructureElementKey)seidList.get(j);
            if(j > 0) {
               structureIds.append(' ');
               elementIds.append(' ');
            }

            structureIds.append(seKey.getStructureId());
            elementIds.append(seKey.getId());
         }

         this.mLog.info("massUpdateStep2B", this.getTaskId() + "," + this.getMyRequest().getFinanceCubeId() + "," + structureIds.toString() + "," + elementIds.toString());
         stmt = this.getConnection().prepareCall("{ call mass_update.step_2B(?,?,?,?) }");
         stmt.setInt(1, this.getTaskId());
         stmt.setInt(2, this.getMyRequest().getFinanceCubeId());
         stmt.setString(3, structureIds.toString());
         stmt.setString(4, elementIds.toString());
         stmt.execute();
      } catch (SQLException var12) {
         throw new RuntimeException(var12);
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   private void massUpdateStep2C() throws Exception {
      List seidList = (List)this.getMyRequest().getChangeCells().get(0);
      StructureElementKey seKey = (StructureElementKey)seidList.get(this.getCheckpoint().getNumDimensions() - 1);
      int srcCalId = seKey.getId();
      this.mLog.info("massUpdateStep2C", this.getTaskId() + "," + this.getMyRequest().getFinanceCubeId() + "," + (this.getMyRequest().isHoldNegative()?"-":(this.getMyRequest().isHoldPositive()?"+":null)) + "," + this.getMyRequest().getDataTypeVisId() + "," + srcCalId + "," + this.getMyRequest().getCalId() + "," + this.getCheckpoint().getPosting() + "," + this.getMyRequest().getRoundUnits() + "," + this.getMyRequest().isCellPosting() + "," + this.getCheckpoint().getReportId());
      Object info = null;
      CallableStatement stmt = null;

      try {
         stmt = this.getConnection().prepareCall("{ call mass_update.step_2C(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
         stmt.setInt(1, this.getTaskId());
         stmt.setInt(2, this.getMyRequest().getFinanceCubeId());
         stmt.setString(3, this.getMyRequest().isHoldNegative()?"-":(this.getMyRequest().isHoldPositive()?"+":null));
         stmt.setString(4, this.getMyRequest().getDataTypeVisId());
         stmt.setInt(5, srcCalId);
         stmt.setInt(6, this.getMyRequest().getCalId());
         stmt.setLong(7, this.getCheckpoint().getPosting());
         stmt.setInt(8, this.getMyRequest().getRoundUnits());
         stmt.setString(9, "N");
         stmt.setString(10, this.getMyRequest().isReport()?"Y":"N");
         stmt.setInt(11, this.getCheckpoint().getSourceDataTypes().size());
         stmt.setInt(12, this.getCheckpoint().getReportId());
         boolean e = true;
         boolean DIFFERENCE_VALUE = true;
         boolean REPORT_CURSOR = true;
         boolean LOCATIONS_CURSOR = true;
         stmt.registerOutParameter(13, 2);
         stmt.registerOutParameter(14, 2);
         stmt.registerOutParameter(15, -10);
         stmt.registerOutParameter(16, -10);
         stmt.execute();
         this.printReport((ResultSet)stmt.getObject(15), stmt.getLong(13), stmt.getLong(14));
         if(!this.getMyRequest().isReport()) {
            this.writeBudgetActivity((ResultSet)stmt.getObject(16));
         } else {
            this.getTaskReport().setActivityDetail((Integer)null, 1, new StringBuffer());
         }
      } catch (SQLException var13) {
         throw var13;
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   private void printReport(ResultSet resultSet, long heldCellsValue, long differenceValue) throws SQLException {
      int numDims = this.getCheckpoint().getNumDimensions();
      boolean showSourceDataType = this.getCheckpoint().getSourceDataTypes().size() > 1;
      this.getTaskReport().addMatrixSection(this.getMyRequest().isReport()?"PREVIEW":"CHANGES");
      this.getTaskReport().addRow();
      this.getTaskReport().addMultiHeading();

      for(int prevVisId = 0; prevVisId < numDims; ++prevVisId) {
         this.getTaskReport().addMultiPart(prevVisId, this.getCheckpoint().getDimNames()[prevVisId] + " - " + this.getCheckpoint().getDimDescrs()[prevVisId]);
      }

      this.getTaskReport().addEndMultiHeading();
      if(showSourceDataType) {
         this.getTaskReport().addCellHeading("Data Type", "center");
         this.getTaskReport().addCellHeading("Source");
      }

      this.getTaskReport().addCellHeading("Value");
      this.getTaskReport().addCellHeading("Posting");
      if(this.getCheckpoint().isDataTypeInSource(this.getMyRequest().getDataTypeVisId())) {
         this.getTaskReport().addCellHeading("New Value");
      }

      this.getTaskReport().addEndRow();
      String[] var32 = new String[numDims];
      long totalOldValue = 0L;
      long totalPosting = 0L;
      long totalNewValue = 0L;
      int numRows = 0;
      long itemPublicValue = 0L;

      try {
         while(resultSet.next()) {
            ++numRows;
            String[] visId = new String[numDims];
            String[] descr = new String[numDims];

            for(int dataType = 0; dataType < numDims; ++dataType) {
               visId[dataType] = resultSet.getString("VISID" + dataType);
               if(dataType < numDims - 1) {
                  descr[dataType] = resultSet.getString("DESCR" + dataType);
               } else {
                  descr[dataType] = "";
               }
            }

            String var33 = resultSet.getString("DATA_TYPE");
            long publicValue = resultSet.getLong("NUMBER_VALUE");
            long postingValue = resultSet.getLong("WORK_VALUE");
            boolean isTarget = var33.equals(this.getMyRequest().getDataTypeVisId());
            if(isTarget) {
               totalOldValue += publicValue;
               totalPosting += postingValue;
               if(this.getCheckpoint().isDataTypeInSource(this.getMyRequest().getDataTypeVisId())) {
                  totalNewValue += publicValue + postingValue;
               } else {
                  totalNewValue += postingValue;
               }
            }

            if(this.getMyRequest().isCellPosting()) {
               boolean isFirst = true;

               for(int i = 0; i < numDims - 1; ++i) {
                  if(var32[i] == null || !var32[i].equals(visId[i])) {
                     var32[i] = visId[i];

                     for(int j = i + 1; j < visId.length; ++j) {
                        var32[j] = null;
                     }

                     if(isFirst) {
                        this.getTaskReport().addRow();
                        this.getTaskReport().addMulti();
                        isFirst = false;
                     }

                     this.getTaskReport().addMultiPart(i, visId[i] + " - " + descr[i]);
                  }
               }

               if(!isFirst) {
                  this.getTaskReport().addEndMulti();
                  this.getTaskReport().addEndRow();
               }

               this.getTaskReport().addRow();
               this.getTaskReport().addMulti();
               if(var32[numDims - 1] == null || !var32[numDims - 1].equals(visId[numDims - 1])) {
                  var32[numDims - 1] = visId[numDims - 1];
                  this.getTaskReport().addMultiPart(numDims - 1, visId[numDims - 1]);
               }

               this.getTaskReport().addEndMulti();
               if(showSourceDataType) {
                  this.getTaskReport().addCellText(var33, "center");
                  if(!isTarget) {
                     this.getTaskReport().addCellNumber(this.formatLong(publicValue));
                     itemPublicValue += publicValue;
                     this.getTaskReport().addCellText("");
                     this.getTaskReport().addCellText("");
                     if(this.getCheckpoint().isDataTypeInSource(this.getMyRequest().getDataTypeVisId())) {
                        this.getTaskReport().addCellText("");
                     }
                  } else {
                     if(this.getCheckpoint().isDataTypeInSource(this.getMyRequest().getDataTypeVisId())) {
                        this.getTaskReport().addCellNumber(this.formatLong(publicValue - itemPublicValue));
                     } else {
                        this.getTaskReport().addCellText("");
                     }

                     this.getTaskReport().addCellNumber(this.formatLong(publicValue));
                     this.getTaskReport().addCellNumber(this.formatLong(postingValue));
                     if(this.getCheckpoint().isDataTypeInSource(this.getMyRequest().getDataTypeVisId())) {
                        this.getTaskReport().addCellNumber(this.formatLong(publicValue - itemPublicValue + postingValue));
                     }

                     itemPublicValue = 0L;
                  }
               } else {
                  this.getTaskReport().addCellNumber(this.formatLong(publicValue));
                  this.getTaskReport().addCellNumber(this.formatLong(postingValue));
                  if(this.getCheckpoint().isDataTypeInSource(this.getMyRequest().getDataTypeVisId())) {
                     this.getTaskReport().addCellNumber(this.formatLong(publicValue + postingValue));
                  }
               }

               this.getTaskReport().addEndRow();
            }
         }

         if(numRows == 0) {
            this.getTaskReport().addRow();
            this.getTaskReport().addMulti();
            this.getTaskReport().addMultiPart(0, "no cells found");
            this.getTaskReport().addEndMulti();
            this.getTaskReport().addEndRow();
         }

         this.getTaskReport().addRow();
         this.getTaskReport().addCellHeading("");
         if(showSourceDataType) {
            this.getTaskReport().addCellHeading("");
            this.getTaskReport().addCellHeading("");
         }

         this.getTaskReport().addCellHeading("Value");
         this.getTaskReport().addCellHeading("Posting");
         if(this.getCheckpoint().isDataTypeInSource(this.getMyRequest().getDataTypeVisId())) {
            this.getTaskReport().addCellHeading("New Value");
         }

         this.getTaskReport().addEndRow();
         this.addTotalRow(showSourceDataType, numDims, "Change Cells", this.formatLong(totalOldValue), this.formatLong(totalPosting), totalNewValue);
         this.addTotalRow(showSourceDataType, numDims, "Held Cells", this.formatLong(heldCellsValue), "", heldCellsValue);
         this.addTotalRow(showSourceDataType, numDims, "Allocation Variance", "", this.formatLong(differenceValue), differenceValue);
         this.addTotalRow(showSourceDataType, numDims, "Total", this.formatLong(totalOldValue + heldCellsValue), this.formatLong(totalPosting + differenceValue), totalOldValue + totalPosting + heldCellsValue + differenceValue);
         this.addTotalRow(showSourceDataType, numDims, "Target", this.formatLong(this.getCheckpoint().getCurrentValue()), this.formatLong(this.getCheckpoint().getPosting()), this.getCheckpoint().getCurrentValue() + this.getCheckpoint().getPosting());
         this.addTotalRow(showSourceDataType, numDims, "Differences", this.formatLong(totalOldValue + heldCellsValue - this.getCheckpoint().getCurrentValue()), this.formatLong(totalPosting + differenceValue - this.getCheckpoint().getPosting()), totalOldValue + totalPosting + heldCellsValue + differenceValue - (this.getCheckpoint().getCurrentValue() + this.getCheckpoint().getPosting()));
         this.getTaskReport().addEndMatrixSection();
      } finally {
         this.closeResultSet(resultSet);
      }

   }

   private void addTotalRow(boolean showDataType, int numDims, String name, String num1, String num2, long num3) {
      this.getTaskReport().addRow();
      this.getTaskReport().addMulti();
      this.getTaskReport().addMultiPart(numDims - 1, name);
      this.getTaskReport().addEndMulti();
      if(showDataType) {
         this.getTaskReport().addCellText("");
      }

      if(showDataType) {
         this.getTaskReport().addCellNumber("");
      }

      this.getTaskReport().addCellNumber(num1);
      this.getTaskReport().addCellNumber(num2);
      if(this.getCheckpoint().isDataTypeInSource(this.getMyRequest().getDataTypeVisId())) {
         this.getTaskReport().addCellNumber(this.formatLong(num3));
      }

      this.getTaskReport().addEndRow();
   }

   private void writeBudgetActivity(ResultSet resultSet) throws SQLException, Exception {
      BudgetActivityEVO baEvo = new BudgetActivityEVO();
      baEvo.setBudgetActivityId(-1);
      baEvo.setModelId(this.getMyRequest().getModelId());
      UserEVO userEvo = (new UserAccessor(this.mInitialContext)).getDetails(new UserPK(this.getUserId()), "");
      baEvo.setUserId(userEvo.getName());
      baEvo.setCreated(new Timestamp(System.currentTimeMillis()));
      baEvo.setActivityType(1);
      baEvo.setDescription("Mass Update");
      MassUpdateTask$ActivityWriter aw = new MassUpdateTask$ActivityWriter(this);
      this.printControls(aw, false);
      aw.addEndReport();
      baEvo.setDetails(aw.getXMLTxt());
      ArrayList links = new ArrayList();
      baEvo.setActivityLinks(links);
      Iterator i = this.getMyRequest().getChangeCells().iterator();

      while(i.hasNext()) {
         List modelEvo = (List)i.next();
         StructureElementKey balEvo = (StructureElementKey)modelEvo.get(0);
         int found = balEvo.getId();
         this.mLog.debug("writeBudgetActivity", "seId=" + found);
         BudgetActivityLinkEVO balIter = new BudgetActivityLinkEVO(-1, found, (Integer)null);
         balIter.setInsertPending();
         Iterator balEvo2 = links.iterator();
         boolean found1 = false;

         while(true) {
            if(balEvo2.hasNext()) {
               BudgetActivityLinkEVO balEvo21 = (BudgetActivityLinkEVO)balEvo2.next();
               if(found != balEvo21.getStructureElementId()) {
                  continue;
               }

               found1 = true;
            }

            if(!found1) {
               links.add(balIter);
            }
            break;
         }
      }

      try {
         while(resultSet.next()) {
            int modelEvo1 = resultSet.getInt(1);
            this.mLog.debug("writeBudgetActivity", "seId=" + modelEvo1);
            BudgetActivityLinkEVO balEvo1 = new BudgetActivityLinkEVO(-1, modelEvo1, (Integer)null);
            balEvo1.setInsertPending();
            boolean found2 = false;
            Iterator balIter1 = links.iterator();

            while(balIter1.hasNext()) {
               BudgetActivityLinkEVO balEvo22 = (BudgetActivityLinkEVO)balIter1.next();
               if(modelEvo1 == balEvo22.getStructureElementId()) {
                  found2 = true;
                  break;
               }
            }

            if(!found2) {
               links.add(balEvo1);
            }
         }
      } finally {
         this.closeResultSet(resultSet);
      }

      ModelEVO modelEvo2 = this.getModelAccessor().getDetails(new ModelPK(this.getMyRequest().getModelId()), "");
      modelEvo2.addBudgetActivitiesItem(baEvo);
      this.getModelAccessor().setDetails(modelEvo2);
      this.getTaskReport().setActivityDetail((Integer)null, 1, new StringBuffer(aw.getXMLTxt()));
   }

   private void massUpdateSimpleStep() {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      CallableStatement stmt = null;

      try {
         stmt = this.getConnection().prepareCall("{ call mass_update.runStep(?,?,?) }");
         stmt.setInt(1, this.getCheckpoint().getStepNumber());
         stmt.setInt(2, this.getTaskId());
         stmt.setInt(3, this.getMyRequest().getFinanceCubeId());
         stmt.execute();
      } catch (SQLException var7) {
         var7.printStackTrace();
         throw new RuntimeException(var7);
      } finally {
         if(timer != null) {
            timer.logDebug("massUpdateSimpleStep");
         }

         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   private MassUpdateTask$ActivityWriter getActivityWriter() {
      if(this.mActivityWriter == null) {
         this.mActivityWriter = new MassUpdateTask$ActivityWriter(this);
         this.mActivityWriter.addReport();
      }

      return this.mActivityWriter;
   }

   public ModelAccessor getModelAccessor() {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.mInitialContext);
      }

      return this.mModelAccessor;
   }

   public DimensionAccessor getDimensionAccessor() {
      if(this.mDimensionAccessor == null) {
         this.mDimensionAccessor = new DimensionAccessor(this.mInitialContext);
      }

      return this.mDimensionAccessor;
   }

   public DataTypeAccessor getDataTypeAccessor() {
      if(this.mDataTypeAccessor == null) {
         this.mDataTypeAccessor = new DataTypeAccessor(this.mInitialContext);
      }

      return this.mDataTypeAccessor;
   }

   private StructureElementDAO getStructureElementDAO() {
      if(this.mStructureElementDAO == null) {
         this.mStructureElementDAO = new StructureElementDAO();
      }

      return this.mStructureElementDAO;
   }

   public int getReportType() {
      return 2;
   }

   public MassUpdateTask$MyCheckpoint getCheckpoint() {
      return (MassUpdateTask$MyCheckpoint)super.getCheckpoint();
   }

   public MassUpdateTaskRequest getMyRequest() {
      return (MassUpdateTaskRequest)this.getRequest();
   }

   public String getEntityName() {
      return "MassUpdateTask";
   }
}
