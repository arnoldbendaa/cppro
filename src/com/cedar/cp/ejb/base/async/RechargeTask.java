// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.dataEntry.RechargeTaskRequest;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.recharge.RechargePK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.RechargeTask$ActivityWriter;
import com.cedar.cp.ejb.base.async.RechargeTask$MyCheckpoint;
import com.cedar.cp.ejb.base.async.TaskReport;
import com.cedar.cp.ejb.base.async.XMLReportUtils;
import com.cedar.cp.ejb.impl.datatype.DataTypeAccessor;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelDimensionRelEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityDAO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityEVO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityLinkDAO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityLinkEVO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeCellsEVO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeEVO;
import com.cedar.cp.ejb.impl.task.TaskDAO;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.util.Timer;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.naming.InitialContext;

public class RechargeTask extends AbstractTask {

   private transient InitialContext mInitialContext;
   private transient ModelDAO mModelDAO;
   private transient DimensionAccessor mDimensionAccessor;
   private transient DataTypeAccessor mDataTypeAccessor;
   private transient NumberFormat mNumberFormat;


   public void runUnitOfWork(InitialContext context) throws Exception {
      this.mInitialContext = context;
      if(this.getCheckpoint() == null) {
         this.firstTime();
      } else {
         this.doWork();
      }

   }

   private void firstTime() throws Exception {
      this.setCheckpoint(new RechargeTask$MyCheckpoint());
      ModelEVO modelEvo = this.getModelDAO().getDetails(new ModelPK(this.getMyRequest().getModelId()), "<9><0><25><26>");
      this.getCheckpoint().setModelDescription(modelEvo.getDescription());
      int numDims = modelEvo.getModelDimensionRels().size();
      this.getCheckpoint().setNumDimensions(numDims);
      FinanceCubeEVO fcubeEvo = modelEvo.getFinanceCubesItem(new FinanceCubePK(this.getMyRequest().getFinanceCubeId()));
      this.getCheckpoint().setFinanceCubeDescription(fcubeEvo.getDescription());
      DimensionEVO[] dimEvo = new DimensionEVO[this.getCheckpoint().getNumDimensions()];
      String[] dimNames = new String[numDims];
      String[] dimDescrs = new String[numDims];

      int calStructureId;
      for(calStructureId = 0; calStructureId < numDims; ++calStructureId) {
         ModelDimensionRelEVO dimRelEvo = this.getModelDimRelBySequence(modelEvo, calStructureId);
         int dimensionId = dimRelEvo.getDimensionId();
         dimEvo[calStructureId] = this.getDimensionAccessor().getDetails(new DimensionPK(dimensionId), "<3>");
         dimNames[calStructureId] = dimEvo[calStructureId].getVisId();
         dimDescrs[calStructureId] = dimEvo[calStructureId].getDescription();
      }

      this.getCheckpoint().setDimNames(dimNames);
      this.getCheckpoint().setDimDescrs(dimDescrs);
      calStructureId = dimEvo[numDims - 1].getHierarchiesItem().getHierarchyId();
      this.getCheckpoint().setCalendarStructureId(calStructureId);
      this.getTaskReport().addReport();
      this.printControls(this.getTaskReport(), modelEvo);
      this.getTaskReport().flushText();
   }

   public TaskReport createTaskReport() {
      return new TaskReport(this.getUserId(), this.getTaskId(), this.getReportType(), this.getCheckpoint(), true, !this.getMyRequest().isReportOnly());
   }

   private void printControls(XMLReportUtils xml, ModelEVO modelEvo) throws Exception {
      xml.addParamSection("SUBMIT");
      xml.addParam("Model", this.getMyRequest().getModelVisId() + " - " + this.getCheckpoint().getModelDescription());
      xml.addParam("Finance Cube", this.getMyRequest().getFinanceCubeVisId() + " - " + this.getCheckpoint().getFinanceCubeDescription());
      StructureElementEVO calEvo = (new StructureElementDAO()).getDetails(new StructureElementPK(this.getCheckpoint().getCalendarStructureId(), this.getMyRequest().getCalendarStructureElementId()), "");
      xml.addParam("Calendar Period", calEvo.getCalVisIdPrefix() + calEvo.getVisId() + " - " + calEvo.getDescription());
      xml.addParam("Generate report only", this.getMyRequest().isReportOnly()?"yes":"no");
      xml.addParam("Report details on source cell values", this.getMyRequest().isReportSourceCells()?"yes":"no");
      xml.addParam("Report details on target cell values", this.getMyRequest().isReportTargetCells()?"yes":"no");
      xml.addParam("Recharge Allocation(s)");
      xml.addParamIndent(0);

      for(int i = 0; i < this.getMyRequest().getRechargeIds().length; ++i) {
         RechargeEVO rcEvo = modelEvo.getRechargeItem(new RechargePK(this.getMyRequest().getRechargeIds()[i]));
         xml.addParam(rcEvo.getVisId(), rcEvo.getDescription());
      }

      xml.addEndParamIndent();
      xml.addEndParam();
      xml.addEndParamSection();
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
      return this.mNumberFormat.format(numDouble / 10000.0D);
   }

   private void doWork() throws Exception {
      if(this.getCheckpoint().getStepNumber() != 2) {
         this.centralRechargeSimpleStep();
         if(this.getCheckpoint().getStepNumber() == 3) {
            this.getTaskReport().addTaskSection("TASK MESSAGES");
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMM HH:mm:ss");
            EntityList events = (new TaskDAO()).getEvents(this.getTaskId());

            for(int i = 0; i < events.getNumRows(); ++i) {
               String eventLine = "";
               int eventType = ((BigDecimal)events.getValueAt(i, "EVENT_TYPE")).intValue();
               Timestamp eventDate = (Timestamp)events.getValueAt(i, "EVENT_TIME");
               String eventText = (String)events.getValueAt(i, "EVENT_TEXT");
               if(eventType != 2) {
                  eventLine = eventLine + sdf.format(eventDate) + " " + eventText;
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
      } else {
         this.doRecharge(this.getMyRequest().getRechargeIds()[this.getCheckpoint().getRechargeIndex()]);
         this.getCheckpoint().setRechargeIndexUp();
         this.getTaskReport().flushText();
         if(this.getCheckpoint().getRechargeIndex() >= this.getMyRequest().getRechargeIds().length) {
            this.getCheckpoint().setStepNumberUp();
         }
      }

   }

   private void doRecharge(int rechargeId) throws Exception {
      CallableStatement stmt = null;
      BigDecimal roundUnits = new BigDecimal("1.00");

      try {
         this.mLog.info("doRecharge", this.getTaskId() + "," + this.getMyRequest().getFinanceCubeId() + "," + rechargeId + "," + this.getMyRequest().getCalendarStructureElementId() + "," + (this.getMyRequest().isReportOnly()?"Y":"N") + "," + roundUnits + "," + 10000);
         stmt = this.getConnection().prepareCall("{ call central_recharge.doRecharge(?,?,?,?,?,?,?,?,?,?,?) }");
         stmt.setInt(1, this.getTaskId());
         stmt.setInt(2, this.getMyRequest().getFinanceCubeId());
         stmt.setInt(3, rechargeId);
         stmt.setInt(4, this.getMyRequest().getCalendarStructureElementId());
         stmt.setString(5, this.getMyRequest().isReportOnly()?"Y":"N");
         stmt.setBigDecimal(6, roundUnits);
         stmt.setInt(7, 10000);
         stmt.setInt(8, this.getCheckpoint().getReportId());
         stmt.registerOutParameter(9, -10);
         stmt.registerOutParameter(10, -10);
         stmt.registerOutParameter(11, -10);
         stmt.execute();
         this.printRechargeHeader(this.getTaskReport(), rechargeId);
         this.printSourceCells((ResultSet)stmt.getObject(9));
         this.printTargetCells((ResultSet)stmt.getObject(10));
         this.writeBudgetActivity((ResultSet)stmt.getObject(11));
      } catch (SQLException var8) {
         var8.printStackTrace();
         throw new RuntimeException(var8);
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   private void printRechargeHeader(XMLReportUtils xml, int rechargeId) throws Exception {
      ModelEVO modelEvo = this.getModelDAO().getDetails(new ModelPK(this.getMyRequest().getModelId()), "<9><0><25><26>");
      RechargeEVO rcEvo = modelEvo.getRechargeItem(new RechargePK(rechargeId));
      xml.addParamSection("RECHARGE " + rcEvo.getVisId());
      xml.addParam("Recharge", rcEvo.getVisId() + " - " + rcEvo.getDescription());
      xml.addParam("Reason", rcEvo.getReason());
      xml.addParam("Reference", rcEvo.getReference());
      xml.addParam("Percentage", rcEvo.getAllocationPercentage().toString() + "%");
      xml.addParam("Manual Ratios", rcEvo.getManualRatios()?"yes":"no");
      this.getCheckpoint().setManualRatios(rcEvo.getManualRatios());
      if(!rcEvo.getManualRatios()) {
         DataTypeEVO j = this.getDataTypeAccessor().getDetails(new DataTypePK((short)rcEvo.getAllocationDataTypeId()), "");
         xml.addParam("Allocation Ratio Data Type", j.getVisId() + " - " + j.getDescription());
         this.getCheckpoint().setAllocationDataTypeRef(j.getEntityRef());
         if(rcEvo.getDiffAccount()) {
            StructureElementEVO seEvo = (new StructureElementDAO()).getDetails(new StructureElementPK(rcEvo.getAccountStructureId(), rcEvo.getAccountStructureElementId()), "");
            xml.addParam("Allocation Ratio Account", seEvo.getVisId() + " - " + seEvo.getDescription());
         }

         xml.addParam("Allocation Ratio Type", rcEvo.getRatioType() == 1?"Base the ratio on the period balance (YTD) value":"Base the ratio on period movement");
      }

      xml.addEndParamSection();
      xml.addMatrixSection("RECHARGE " + rcEvo.getVisId() + " CELLS");
      xml.addRow();
      xml.addCellHeading("Type", "left");

      for(int var7 = 0; var7 < this.getCheckpoint().getNumDimensions() - 1; ++var7) {
         xml.addCellHeading(this.getCheckpoint().getDimDescrs()[var7], "left");
      }

      xml.addCellHeading("Data Type", "center");
      if(rcEvo.getManualRatios()) {
         xml.addCellHeading("Manual Ratio", "center");
      }

      xml.addEndRow();
      this.getCheckpoint().resetSourceDataTypes();
      this.getCheckpoint().resetTargetDataTypes();
      this.printRechargeCells(xml, rcEvo, 0);
      this.printRechargeCells(xml, rcEvo, 1);
      this.printRechargeCells(xml, rcEvo, 2);
      xml.addEndMatrixSection();
   }

   private void printRechargeCells(XMLReportUtils xml, RechargeEVO rcEvo, int type) throws ValidationException {
      int numDims = this.getCheckpoint().getDimNames().length;
      StructureElementDAO sedao = new StructureElementDAO();
      Iterator rcIter = rcEvo.getRechargeCells().iterator();

      while(rcIter.hasNext()) {
         RechargeCellsEVO rcellEvo = (RechargeCellsEVO)rcIter.next();
         if(rcellEvo.getCellType() == type) {
            xml.addRow();
            if(type == 0) {
               xml.addCellText("Source");
            } else if(type == 1) {
               xml.addCellText("Target");
            } else {
               xml.addCellText("Offset");
            }

            for(int i = 0; i < numDims - 1; ++i) {
               int structureId = rcellEvo.getStructureId(i);
               int structureElementId = rcellEvo.getStructureElementId(i);
               if(structureId == 0) {
                  xml.addCellText("");
               } else {
                  StructureElementEVO seEvo = sedao.getDetails(new StructureElementPK(structureId, structureElementId), "");
                  if(!seEvo.getLeaf()) {
                     if(type == 0) {
                        this.getCheckpoint().setSourceAllLeaf(i, false);
                     } else {
                        this.getCheckpoint().setTargetAllLeaf(i, false);
                     }
                  }

                  xml.addCellText(seEvo.getVisId() + " - " + seEvo.getDescription());
               }
            }

            xml.addCellText(rcellEvo.getDataType() == null?"":rcellEvo.getDataType(), "center");
            if(type == 1) {
               xml.addCellText(rcellEvo.getRatio() == null?"":String.valueOf(rcellEvo.getRatio()), "center");
            }

            xml.addEndRow();
            if(type == 0) {
               this.getCheckpoint().addSourceDataType(rcellEvo.getDataType());
            } else if(type == 1 && rcellEvo.getDataType() != null) {
               this.getCheckpoint().addTargetDataType(rcellEvo.getDataType());
            }
         }
      }

   }

   private void printSourceCells(ResultSet resultSet) throws SQLException {
      int numDims = this.getCheckpoint().getNumDimensions() - 1;
      this.getTaskReport().addMatrixSection("Source Recharges");
      if(this.getMyRequest().isReportSourceCells()) {
         this.getTaskReport().addRow();

         for(int prevVisId = 0; prevVisId < numDims; ++prevVisId) {
            this.getTaskReport().addCellHeading(this.getCheckpoint().getDimDescrs()[prevVisId], "left");
         }

         this.getTaskReport().addCellHeading("Data Type", "center");
         this.getTaskReport().addCellHeading("Value", "right");
         this.getTaskReport().addCellHeading("Recharge", "right");
         this.getTaskReport().addEndRow();
      }

      String[] var21 = new String[numDims];
      long totalPublicValue = 0L;
      long totalPosting = 0L;
      int numRows = 0;
      boolean cellCalcTargetFound = false;

      try {
         while(resultSet.next()) {
            ++numRows;
            String[] i = new String[numDims];
            String[] descr = new String[numDims];

            for(int dataType = 0; dataType < numDims; ++dataType) {
               i[dataType] = resultSet.getString("VISID" + dataType);
               descr[dataType] = resultSet.getString("DESCR" + dataType);
            }

            String var23 = resultSet.getString("DATA_TYPE");
            Long publicValue = Long.valueOf(resultSet.getLong("NUMBER_VALUE"));
            if(resultSet.wasNull()) {
               publicValue = null;
            }

            Long postingValue = Long.valueOf(resultSet.getLong("POSTING"));
            if(resultSet.wasNull()) {
               postingValue = null;
            }

            String isCalc = resultSet.getString("IS_CALC");
            if(publicValue != null) {
               totalPublicValue += publicValue.longValue();
            }

            if(postingValue != null) {
               totalPosting += postingValue.longValue();
            }

            if(!this.getMyRequest().isReportSourceCells()) {
               if(isCalc != null && isCalc.equals("Y")) {
                  cellCalcTargetFound = true;
               }
            } else {
               this.getTaskReport().addRow();

               for(int i1 = 0; i1 < numDims; ++i1) {
                  if(var21[i1] != null && var21[i1].equals(i[i1])) {
                     this.getTaskReport().addCellText("");
                  } else {
                     var21[i1] = i[i1];

                     for(int j = i1 + 1; j < i.length; ++j) {
                        var21[j] = null;
                     }

                     if(this.getCheckpoint().isSourceAllLeaf(i1)) {
                        this.getTaskReport().addCellText(i[i1]);
                     } else {
                        this.getTaskReport().addCellText(i[i1] + " - " + descr[i1]);
                     }
                  }
               }

               this.getTaskReport().addCellText(var23, "center");
               if(publicValue != null) {
                  this.getTaskReport().addCellNumber(this.formatLong(publicValue.longValue()));
               } else {
                  this.getTaskReport().addCellText("");
               }

               if(postingValue != null) {
                  this.getTaskReport().addCellNumber(this.formatLong(postingValue.longValue()));
               } else {
                  this.getTaskReport().addCellText("");
               }

               this.getTaskReport().addEndRow();
               if(isCalc != null && isCalc.equals("Y")) {
                  this.getTaskReport().addRow();
                  this.getTaskReport().addCellText("Row above is a cell calc target.");
                  this.getTaskReport().addEndRow();
                  this.getTaskReport().addRow();
                  this.getTaskReport().addCellText("Updates not possible.");
                  this.getTaskReport().addEndRow();
               }
            }
         }

         if(numRows == 0) {
            this.getTaskReport().addRow();
            this.getTaskReport().addCellText("no cells found");
            this.getTaskReport().addEndRow();
         } else if(!this.getMyRequest().isReportSourceCells()) {
            if(cellCalcTargetFound) {
               this.getTaskReport().addRow();
               this.getTaskReport().addCellText("At least one row is a cell calc target.  Updates not possible.");
               this.getTaskReport().addEndRow();
            } else {
               this.getTaskReport().addRow();
               this.getTaskReport().addCellText("source value " + this.formatLong(totalPublicValue));
               this.getTaskReport().addEndRow();
               this.getTaskReport().addRow();
               this.getTaskReport().addCellText("recharge value " + this.formatLong(totalPosting));
               this.getTaskReport().addEndRow();
            }
         } else {
            this.getTaskReport().addRow();

            int var22;
            for(var22 = 0; var22 < numDims; ++var22) {
               this.getTaskReport().addCellHeading("");
            }

            this.getTaskReport().addCellHeading("");
            this.getTaskReport().addCellHeading(this.fill('-', this.formatLong(totalPublicValue).length()), "right");
            this.getTaskReport().addCellHeading(this.fill('-', this.formatLong(totalPosting).length()), "right");
            this.getTaskReport().addEndRow();
            this.getTaskReport().addRow();

            for(var22 = 0; var22 < numDims; ++var22) {
               this.getTaskReport().addCellText("");
            }

            this.getTaskReport().addCellText("");
            this.getTaskReport().addCellNumber(this.formatLong(totalPublicValue));
            this.getTaskReport().addCellNumber(this.formatLong(totalPosting));
            this.getTaskReport().addEndRow();
         }

         this.getTaskReport().addEndMatrixSection();
      } finally {
         this.closeResultSet(resultSet);
      }

   }

   private String fill(char c, int len) {
      StringBuilder sb = new StringBuilder(len);

      while(sb.length() < len) {
         sb.append(c);
      }

      return sb.toString();
   }

   private void printTargetCells(ResultSet resultSet) throws SQLException {
      int numDims = this.getCheckpoint().getNumDimensions() - 1;
      boolean printDataType = this.getCheckpoint().getTargetDataTypes().size() != 1;
      this.getTaskReport().addMatrixSection("Target Postings");
      if(this.getMyRequest().isReportTargetCells()) {
         this.getTaskReport().addRow();

         int prevVisId;
         for(prevVisId = 0; prevVisId < numDims; ++prevVisId) {
            this.getTaskReport().addCellHeading("", "left");
         }

         this.getTaskReport().addCellHeading("", "center");
         if(!this.getCheckpoint().isManualRatios()) {
            this.getTaskReport().addCellHeading("Ratio", "left");
            this.getTaskReport().addCellHeading("Ratio");
            this.getTaskReport().addCellHeading("");
         } else {
            this.getTaskReport().addCellHeading("");
         }

         this.getTaskReport().addCellHeading("");
         this.getTaskReport().addEndRow();
         this.getTaskReport().addRow();

         for(prevVisId = 0; prevVisId < numDims; ++prevVisId) {
            this.getTaskReport().addCellHeading(this.getCheckpoint().getDimDescrs()[prevVisId], "left");
         }

         this.getTaskReport().addCellHeading("Data Type", "center");
         if(!this.getCheckpoint().isManualRatios()) {
            this.getTaskReport().addCellHeading(this.getCheckpoint().getDimDescrs()[numDims - 1], "left");
            this.getTaskReport().addCellHeading("(" + this.getCheckpoint().getAllocationDataTypeRef().getNarrative() + ")");
            this.getTaskReport().addCellHeading("Basis");
         } else {
            this.getTaskReport().addCellHeading("Ratio", "center");
         }

         this.getTaskReport().addCellHeading("Posting", "right");
         this.getTaskReport().addEndRow();
      }

      String[] var30 = new String[numDims];
      long totalPosting = 0L;
      int numRows = 0;
      boolean cellCalcTargetFound = false;

      try {
         while(resultSet.next()) {
            ++numRows;
            String[] i = new String[numDims];
            String[] descr = new String[numDims];

            for(int ratioVisId = 0; ratioVisId < numDims; ++ratioVisId) {
               i[ratioVisId] = resultSet.getString("VISID" + ratioVisId);
               descr[ratioVisId] = resultSet.getString("DESCR" + ratioVisId);
            }

            String var32 = resultSet.getString("RATIO_VIS_ID");
            String ratioDescr = resultSet.getString("RATIO_DESCR");
            String dataType = resultSet.getString("DATA_TYPE");
            long ratioAmount = resultSet.getLong("RATIO_AMOUNT");
            long ratioTotal = resultSet.getLong("RATIO_TOTAL");
            long postingValue = resultSet.getLong("POSTING");
            long ratioCount = resultSet.getLong("RATIO_COUNT");
            long targetCount = resultSet.getLong("TARGET_COUNT");
            String isCalc = resultSet.getString("IS_CALC");
            totalPosting += postingValue;
            if(!this.getMyRequest().isReportTargetCells()) {
               if(isCalc != null && isCalc.equals("Y")) {
                  cellCalcTargetFound = true;
               }
            } else {
               this.getTaskReport().addRow();

               for(int i1 = 0; i1 < numDims; ++i1) {
                  if(var30[i1] != null && var30[i1].equals(i[i1])) {
                     this.getTaskReport().addCellText("");
                  } else {
                     var30[i1] = i[i1];

                     for(int j = i1 + 1; j < i.length; ++j) {
                        var30[j] = null;
                     }

                     this.getTaskReport().addCellText(i[i1] + " - " + descr[i1]);
                  }
               }

               this.getTaskReport().addCellText(dataType, "center");
               if(!this.getCheckpoint().isManualRatios()) {
                  this.getTaskReport().addCellText(var32);
                  this.getTaskReport().addCellNumber(this.formatLong(ratioAmount));
                  this.getTaskReport().addCellNumber(this.formatLong(ratioTotal));
               } else {
                  this.getTaskReport().addCellText(ratioAmount + "/" + ratioTotal, "center");
               }

               this.getTaskReport().addCellNumber(this.formatLong(postingValue));
               this.getTaskReport().addEndRow();
               if(isCalc != null && isCalc.equals("Y")) {
                  this.getTaskReport().addRow();
                  this.getTaskReport().addCellText("**** Row above is a cell calc target.");
                  this.getTaskReport().addEndRow();
                  this.getTaskReport().addRow();
                  this.getTaskReport().addCellText("**** Updates not possible.");
                  this.getTaskReport().addEndRow();
               }
            }
         }

         if(numRows == 0) {
            this.getTaskReport().addRow();
            this.getTaskReport().addCellText("no cells found");
            this.getTaskReport().addEndRow();
         } else if(!this.getMyRequest().isReportSourceCells()) {
            if(cellCalcTargetFound) {
               this.getTaskReport().addRow();
               this.getTaskReport().addCellText("At least one row is a cell calc target.  Updates not possible.");
               this.getTaskReport().addEndRow();
            } else {
               this.getTaskReport().addRow();
               this.getTaskReport().addCellText("posting value " + this.formatLong(totalPosting));
               this.getTaskReport().addEndRow();
            }
         } else {
            this.getTaskReport().addRow();

            int var31;
            for(var31 = 0; var31 < numDims; ++var31) {
               this.getTaskReport().addCellHeading("");
            }

            this.getTaskReport().addCellHeading("");
            if(!this.getCheckpoint().isManualRatios()) {
               this.getTaskReport().addCellHeading("");
               this.getTaskReport().addCellHeading("");
               this.getTaskReport().addCellHeading("");
            } else {
               this.getTaskReport().addCellHeading("");
            }

            this.getTaskReport().addCellHeading(this.fill('-', this.formatLong(totalPosting).length()), "right");
            this.getTaskReport().addEndRow();
            this.getTaskReport().addRow();

            for(var31 = 0; var31 < numDims; ++var31) {
               this.getTaskReport().addCellText("");
            }

            this.getTaskReport().addCellText("");
            if(!this.getCheckpoint().isManualRatios()) {
               this.getTaskReport().addCellHeading("");
               this.getTaskReport().addCellHeading("");
               this.getTaskReport().addCellHeading("");
            } else {
               this.getTaskReport().addCellHeading("");
            }

            this.getTaskReport().addCellNumber(this.formatLong(totalPosting));
            this.getTaskReport().addEndRow();
         }

         this.getTaskReport().addEndMatrixSection();
      } finally {
         this.closeResultSet(resultSet);
      }

   }

   private void writeBudgetActivity(ResultSet resultSet) throws SQLException, Exception {
      ModelEVO modelEvo = this.getModelDAO().getDetails(new ModelPK(this.getMyRequest().getModelId()), "<9><0><25><26>");
      RechargeTask$ActivityWriter aw = new RechargeTask$ActivityWriter(this);
      aw.addReport();
      this.printControls(aw, modelEvo);

      for(int baldao = 0; baldao < this.getMyRequest().getRechargeIds().length; ++baldao) {
         this.printRechargeHeader(aw, this.getMyRequest().getRechargeIds()[baldao]);
      }

      aw.addEndReport();
      if(this.getCheckpoint().getBudgetActivityId() == null && !this.getMyRequest().isReportOnly()) {
         this.getCheckpoint().setBudgetActivityId(this.getModelDAO().getNextSeq());
         BudgetActivityEVO var10 = new BudgetActivityEVO();
         var10.setBudgetActivityId(this.getCheckpoint().getBudgetActivityId().intValue());
         var10.setModelId(this.getMyRequest().getModelId());
         UserEVO costCentreElemId = (new UserAccessor(this.mInitialContext)).getDetails(new UserPK(this.getUserId()), "");
         var10.setUserId(costCentreElemId.getName());
         var10.setCreated(new Timestamp(System.currentTimeMillis()));
         var10.setActivityType(2);
         var10.setDescription("Recharge");
         var10.setDetails(aw.getXMLTxt());
         var10.setActivityLinks(new ArrayList());
         BudgetActivityDAO balEvo = new BudgetActivityDAO();
         balEvo.setDetails(var10);
      }

      if(!this.getMyRequest().isReportOnly()) {
         BudgetActivityLinkDAO var12 = new BudgetActivityLinkDAO();

         try {
            while(resultSet.next()) {
               int var11 = resultSet.getInt(1);
               BudgetActivityLinkEVO var13 = new BudgetActivityLinkEVO(this.getCheckpoint().getBudgetActivityId().intValue(), var11, (Integer)null);
               var12.insertLink(var13);
            }
         } finally {
            this.closeResultSet(resultSet);
         }
      }

      this.getTaskReport().setActivityDetail((Integer)null, 2, new StringBuffer(aw.getXMLTxt()));
   }

   private void centralRechargeSimpleStep() {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      CallableStatement stmt = null;

      try {
         stmt = this.getConnection().prepareCall("{ call central_recharge.runStep(?,?,?) }");
         stmt.setInt(1, this.getCheckpoint().getStepNumber());
         stmt.setInt(2, this.getTaskId());
         stmt.setInt(3, this.getMyRequest().getFinanceCubeId());
         stmt.execute();
      } catch (SQLException var7) {
         var7.printStackTrace();
         throw new RuntimeException(var7);
      } finally {
         if(timer != null) {
            timer.logDebug("centralRechargeSimpleStep");
         }

         this.closeStatement(stmt);
         this.closeConnection();
      }

   }

   public ModelDAO getModelDAO() {
      if(this.mModelDAO == null) {
         this.mModelDAO = new ModelDAO();
      }

      return this.mModelDAO;
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

   public int getReportType() {
      return 1;
   }

   public RechargeTask$MyCheckpoint getCheckpoint() {
      return (RechargeTask$MyCheckpoint)super.getCheckpoint();
   }

   public RechargeTaskRequest getMyRequest() {
      return (RechargeTaskRequest)this.getRequest();
   }

   public String getEntityName() {
      return "MassUpdateTask";
   }
}
