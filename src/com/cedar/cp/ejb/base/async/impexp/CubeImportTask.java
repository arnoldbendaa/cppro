// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.impexp;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.datatype.AllDataTypeForFinanceCubeELO;
import com.cedar.cp.dto.dimension.AllDimensionsForModelELO;
import com.cedar.cp.dto.impexp.CubeImportTaskRequest;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.task.Task;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.TaskReport;
import com.cedar.cp.ejb.base.async.impexp.CubeImportTask$DataTypeDetails;
import com.cedar.cp.ejb.base.async.impexp.CubeImportTask$MyCheckpoint;
import com.cedar.cp.ejb.base.cube.CellNote;
import com.cedar.cp.ejb.base.cube.CellPosting;
import com.cedar.cp.ejb.base.cube.CubeUpdate;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine;
import com.cedar.cp.ejb.impl.base.SqlExecutor;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.task.TaskDAO;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.util.SqlBuilder;
import com.cedar.cp.util.Timer;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.naming.InitialContext;
import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.DatumWithConnection;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class CubeImportTask extends AbstractTask implements Task {

   private FinanceCubeEVO mFinanceCubeEVO;
   private List<String> mDimensionNames;
   private NumberFormat mNumberFormat;
   private DateFormat mDateFormatIn = new SimpleDateFormat("yyyy-MM-dd");
   private DateFormat mDateFormatOut = new SimpleDateFormat("dd/MM/yyyy");
   private DateFormat mDateTimeFormatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   private DateFormat mDateTimeFormatOut = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
   private DateFormat mTimeFormatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   private DateFormat mTimeFormatOut = new SimpleDateFormat("HH:mm:ss");


   public CubeImportTaskRequest getRequest() {
      return (CubeImportTaskRequest)super.getRequest();
   }

   public CubeImportTask$MyCheckpoint getCheckpoint() {
      return (CubeImportTask$MyCheckpoint)super.getCheckpoint();
   }

   public String getEntityName() {
      return "CubeImportTask";
   }

   public void runUnitOfWork(InitialContext context) throws Exception {
      this.setCheckpoint(new CubeImportTask$MyCheckpoint());
      FinanceCubeEVO fcEVO = this.getFinanceCubeEVO();
      this.queryDimensions(fcEVO.getModelId());
      this.log(fcEVO.getPK() + " (" + fcEVO.getDescription() + ")" + " replace=" + this.getRequest().getDataExtract().isReplaceMode());
      TaskReport taskReport = this.prepareReport();
      this.doWork(taskReport);
      this.completeReport(taskReport);
      this.setCheckpoint((TaskCheckpoint)null);
   }

   private TaskReport prepareReport() throws Exception {
      TaskReport taskReport = new TaskReport(this.getUserId(), this.getTaskId(), 6, this.getCheckpoint());
      taskReport.addReport();
      taskReport.addParamSection("CONTEXT");
      taskReport.addParam("Finance Cube", this.getFinanceCubeEVO().getVisId() + " - " + this.getFinanceCubeEVO().getDescription());
      taskReport.addParam("Replace", this.getRequest().getDataExtract().isReplaceMode()?"true":"false");
      taskReport.addEndParamSection();
      return taskReport;
   }

   private void completeReport(TaskReport taskReport) {
      taskReport.addTaskSection("TASK MESSAGES");
      EntityList events = (new TaskDAO()).getEvents(this.getTaskId());
      SimpleDateFormat sdf = new SimpleDateFormat("ddMMM HH:mm:ss");

      for(int i = 0; i < events.getNumRows(); ++i) {
         String eventLine = "";
         int eventType = ((BigDecimal)events.getValueAt(i, "EVENT_TYPE")).intValue();
         Timestamp eventDate = (Timestamp)events.getValueAt(i, "EVENT_TIME");
         String eventText = (String)events.getValueAt(i, "EVENT_TEXT");
         if(eventType != 2) {
            eventLine = eventLine + sdf.format(eventDate) + " " + eventText;
            taskReport.addTaskMessage(eventLine);
         }
      }

      taskReport.addEndTaskSection();
      taskReport.addEndReport();
      taskReport.setCompleted();
   }

   private void doWork(TaskReport taskReport) throws Exception {
      Map dataTypeMap = this.loadDataTypes(this.getRequest().getFinanceCubeId());
      CubeUpdate cubeUpdate = new CubeUpdate();
      cubeUpdate.setFinanceCubeId(this.getRequest().getFinanceCubeId());
      cubeUpdate.setAbsoluteValues(this.getRequest().getDataExtract().isReplaceMode());
      cubeUpdate.setUpdateType(6);
      cubeUpdate.setUserId(this.getUserId());
      cubeUpdate.setBudgetCycleId(-1);
      this.log("creating " + this.getRequest().getDataExtract().getData().size() + " postings");

      CellPosting t;
      for(Iterator newNotes = this.getRequest().getDataExtract().getData().iterator(); newNotes.hasNext(); cubeUpdate.addCell(t)) {
         Object[] duplicateNotes = (Object[])newNotes.next();
         t = new CellPosting();
         StringBuilder cubeUpdateEngine = new StringBuilder();

         for(int i$ = 0; i$ < duplicateNotes.length - 2; ++i$) {
            if(i$ > 0) {
               cubeUpdateEngine.append(",");
            }

            cubeUpdateEngine.append(duplicateNotes[i$]);
         }

         t.setAddr(cubeUpdateEngine.toString());
         t.setDataType(duplicateNotes[duplicateNotes.length - 2].toString());
         Object[] var18 = (Object[])((Object[])duplicateNotes[duplicateNotes.length - 1]);
         CubeImportTask$DataTypeDetails row = (CubeImportTask$DataTypeDetails)dataTypeMap.get(t.getDataType());
         switch(row.getSubType()) {
         case 0:
            t.setNumberValue(var18[0].toString());
            break;
         case 1:
            t.setNumberValue(var18[0].toString());
            break;
         case 2:
            t.setNumberValue(var18[0].toString());
         case 3:
         default:
            break;
         case 4:
            switch(row.getMeasureClass()) {
            case 0:
               t.setStringValue(var18[1].toString());
               break;
            case 1:
               t.setNumberValue(var18[0].toString());
               break;
            case 2:
               Timestamp cellAddr = new Timestamp(this.getTimeFormatIn().parse(var18[2].toString()).getTime());
               t.setTimeValue(this.getTimeFormatOut().format(cellAddr));
               break;
            case 3:
               Date note = new Date(this.getDateFormatIn().parse(var18[2].toString()).getTime());
               t.setDateValue(this.getDateFormatOut().format(note));
               break;
            case 4:
               Timestamp i = new Timestamp(this.getDateTimeFormatIn().parse(var18[2].toString()).getTime());
               t.setDateTimeValue(this.getDateTimeFormatOut().format(i));
               break;
            case 5:
               t.setStringValue(var18[1].toString());
            }
         }
      }

      int var13 = 0;
      int var14 = 0;
      Timer var15 = new Timer(this.mLog);
      if(this.getRequest().getDataExtract().getNoteData() != null) {
         String userName = (new UserDAO()).getDetails(new UserPK(this.getUserId()), "").getName();
         Iterator it = this.getRequest().getDataExtract().getNoteData().iterator();

         while(it.hasNext()) {
            Object[] noteData = (Object[])it.next();
            if(this.isNoteSame(noteData)) {
               ++var14;
            } else {
               ++var13;
               CellNote note = new CellNote();
               StringBuilder var21 = new StringBuilder();

               for(int i = 0; i < noteData.length - 2; ++i) {
                  if(i > 0) {
                     var21.append(",");
                  }

                  var21.append(noteData[i]);
               }

               note.setAddr(var21.toString());
               note.setDataType(noteData[noteData.length - 2].toString());
               note.setNote(noteData[noteData.length - 1].toString());
               note.setUserName(userName);
               cubeUpdate.addCellNote(note);
            }
         }
      }

      if(var13 > 0 || var14 > 0) {
         var15.logDebug("processTransactions", "newNotes=" + var13 + " duplicateNotes=" + var14);
         this.log("newNotes=" + var13 + " duplicateNotes=" + var14);
      }

      CubeUpdateEngine var17 = new CubeUpdateEngine();
      var17.setFinanceCubeId(this.getRequest().getFinanceCubeId());
      var17.setTaskId(Integer.valueOf(this.getTaskId()));
      var17.setNumDims(this.getNumDims());
      var17.updateCube(cubeUpdate, false);
      this.writePostingsToTaskReport(taskReport);
      if(var13 > 0) {
         this.writeNotesToTaskReport(taskReport, cubeUpdate.getCellNotes());
      }

   }

   private boolean isNoteSame(Object[] row) throws SQLException {
      int[] cellAddr = new int[this.getNumDims()];

      for(int dataType = 0; dataType < cellAddr.length; ++dataType) {
         cellAddr[dataType] = (new Integer(row[dataType].toString())).intValue();
      }

      String var14 = row[this.getNumDims()].toString();
      String noteText = row[this.getNumDims() + 1].toString();
      SqlBuilder sqlb = new SqlBuilder(new String[]{"select  1", "from    (", "        select  ${dims}, DATA_TYPE, max(CREATED) CREATED", "        from    SFT${financeCubeId}", "        where", "        ${matchDims}", "        and     DATA_TYPE = <dataType>", "        group   by ${dims}, DATA_TYPE", "        )", "join    SFT${financeCubeId} using (${dims}, DATA_TYPE, CREATED)", "where   STRING_VALUE = <noteText>"});
      sqlb.substitute(new String[]{"${financeCubeId}", String.valueOf(this.getRequest().getFinanceCubeId())});
      sqlb.substitute(new String[]{"${dims}", SqlBuilder.repeatString("${separator}DIM${index}", ",", this.getNumDims())});
      sqlb.substitute(new String[]{"${dimMarkers}", SqlBuilder.repeatString("${separator}?", ",", this.getNumDims())});
      sqlb.substituteRepeatingLines("${matchDims}", this.getNumDims(), "and    ", new String[]{"${separator} DIM${index} = <dim.${index}>"});
      SqlExecutor sqle = new SqlExecutor("getLatestCellNote", this.getDataSource(), sqlb, this.mLog);

      for(int rs = 0; rs < this.getNumDims(); ++rs) {
         sqle.addBindVariable("<dim." + rs + ">", Integer.valueOf(cellAddr[rs]));
      }

      sqle.addBindVariable("<dataType>", var14);
      sqle.addBindVariable("<noteText>", noteText);
      ResultSet var15 = sqle.getResultSet();

      boolean e;
      try {
         e = var15.next();
      } catch (SQLException var12) {
         var12.printStackTrace();
         throw var12;
      } finally {
         sqle.close();
      }

      return e;
   }

   public Map<String, CubeImportTask$DataTypeDetails> loadDataTypes(int financeCubeId) {
      HashMap dataTypeMap = new HashMap();
      AllDataTypeForFinanceCubeELO dataTypes = (new DataTypeDAO()).getAllDataTypeForFinanceCube(financeCubeId);

      for(int i = 0; i < dataTypes.getNumRows(); ++i) {
         DataTypeRef dtRef = (DataTypeRef)dataTypes.getValueAt(i, "DataType");
         int subType = ((Integer)dataTypes.getValueAt(i, "SubType")).intValue();
         Integer measureClass = (Integer)dataTypes.getValueAt(i, "MeasureClass");
         Integer measureScale = (Integer)dataTypes.getValueAt(i, "MeasureScale");
         Integer measureLength = (Integer)dataTypes.getValueAt(i, "MeasureLength");
         dataTypeMap.put(dtRef.getNarrative(), new CubeImportTask$DataTypeDetails(dtRef, subType, measureClass != null?measureClass.intValue():-1, measureScale != null?measureScale.intValue():-1, measureLength != null?measureLength.intValue():-1));
      }

      return dataTypeMap;
   }

   private void writePostingsToTaskReport(TaskReport taskReport) throws SQLException {
      SqlBuilder sqlb = new SqlBuilder();
      sqlb.addLines(this.getReportSqlTemplate());
      sqlb.substituteLines("${valueColumns}", new String[]{"        ,case SUB_TYPE", "         when 0 then to_char(NUMBER_VALUE / 10000)", "         when 4 then", "             case MEASURE_CLASS", "             when 1 then to_char(NUMBER_VALUE / power(10,MEASURE_SCALE))", "             when 0 then STRING_VALUE", "             when 2 then to_char(DATE_VALUE,\'HH24:MI:SS\')", "             when 3 then to_char(DATE_VALUE,\'DD/MM/YYYY\')", "             when 4 then to_char(DATE_VALUE,\'DD/MM/YYYY HH24:MI:SS\')", "             when 5 then decode(STRING_VALUE,\'Y\',\'true\',\' \',\'false\')", "             end", "         end VALUE", "        ,case when SUB_TYPE <> 4 or MEASURE_CLASS = 1", "              then \'Y\' else \'N\'", "         end as IS_NUMBER"});
      sqlb.substitute(new String[]{"${reportSource}", "TX1_${financeCubeId}"});
      sqlb.substitute(new String[]{"${financeCubeId}", String.valueOf(this.getRequest().getFinanceCubeId())});
      SqlExecutor sqle = new SqlExecutor("writeDetailsToTaskReport", this.getDataSource(), sqlb, this._log);

      try {
         List hierarchyNames = this.getRequest().getDataExtract().getHierarchyList();
         if(hierarchyNames != null) {
            for(int rs = 0; rs < hierarchyNames.size(); ++rs) {
               sqle.addBindVariable("<structureName." + rs + ">", hierarchyNames.get(rs));
            }
         }

         ResultSet var9 = sqle.getResultSet();
         this.addReportRows("POSTINGS", var9, taskReport);
      } finally {
         sqle.close();
      }

   }

   private void writeNotesToTaskReport(TaskReport taskReport, List<CellNote> cellKeys) throws SQLException {
      PreparedStatement pstat = null;
      ResultSet resultSet = null;
      OracleConnection oconn = null;

      try {
         pstat = this.getConnection().prepareStatement("select empty_clob() from dual");
         resultSet = pstat.executeQuery();
         resultSet.next();
         DatumWithConnection cellDataRowDesc = (DatumWithConnection)resultSet.getClob(1);
         oconn = cellDataRowDesc.getOracleConnection();
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(pstat);
      }

      StructDescriptor var21 = new StructDescriptor("TYPE_CELLDATAROW", oconn);
      ArrayDescriptor cellDataArrayDesc = new ArrayDescriptor("TYPE_CELLDATATABLE", oconn);
      STRUCT[] structArray = new STRUCT[cellKeys.size()];

      int rs;
      for(int cellDataTable = 0; cellDataTable < cellKeys.size(); ++cellDataTable) {
         Object[] sqlb = new Object[16];
         sqlb[0] = new Integer(cellDataTable);
         CellNote sqle = (CellNote)cellKeys.get(cellDataTable);
         sqlb[1] = "N";
         StringTokenizer hierarchyNames = new StringTokenizer(sqle.getAddr(), ",");

         for(rs = 0; rs < this.getNumDims(); ++rs) {
            sqlb[rs + 2] = Integer.valueOf(Integer.parseInt(hierarchyNames.nextToken()));
         }

         sqlb[12] = sqle.getDataType();
         sqlb[14] = sqle.getNote();
         structArray[cellDataTable] = new STRUCT(var21, oconn, sqlb);
      }

      ARRAY var22 = new ARRAY(cellDataArrayDesc, oconn, structArray);
      SqlBuilder var24 = new SqlBuilder();
      var24.addLines(this.getReportSqlTemplate());
      var24.substituteLines("${valueColumns}", new String[]{"        ,STRING_VALUE as VALUE", "        ,\'N\'          as IS_NUMBER"});
      var24.substitute(new String[]{"${reportSource}", "table(cast (<cellDataTable> as TYPE_cellDataTable))"});
      SqlExecutor var23 = null;

      try {
         var23 = new SqlExecutor("writeDetailsToTaskReport", this.getDataSource(), var24, this._log);
         List var25 = this.getRequest().getDataExtract().getHierarchyList();
         if(var25 != null) {
            for(rs = 0; rs < var25.size(); ++rs) {
               var23.addBindVariable("<structureName." + rs + ">", var25.get(rs));
            }
         }

         var23.addBindVariable("<cellDataTable>", (Object)var22);
         ResultSet var26 = var23.getResultSet();
         this.addReportRows("NOTES", var26, taskReport);
      } finally {
         var23.close();
      }

   }

   private SqlBuilder getReportSqlTemplate() {
      SqlBuilder sqlb = new SqlBuilder(new String[]{"with getCalDim as", "-- calendar structure, with path to root instead of vis_id", "(", "select  STRUCTURE_ELEMENT_ID, POSITION, LEAF, se.DESCRIPTION", "        ,calendar_utils.queryPathToRoot(STRUCTURE_ELEMENT_ID) as VIS_ID", "from    FINANCE_CUBE", "join    MODEL_DIMENSION_REL using (MODEL_ID)", "join    HIERARCHY using (DIMENSION_ID)", "join    STRUCTURE_ELEMENT se on (STRUCTURE_ID = HIERARCHY_ID)", "where   FINANCE_CUBE_ID = ${financeCubeId}", "and     DIMENSION_TYPE = 3", ")", ",getStructures as", "-- structures used by the model", "(", "select  DIMENSION_SEQ_NUM, DIMENSION_ID, HIERARCHY_ID as STRUCTURE_ID", "from    FINANCE_CUBE", "join    MODEL_DIMENSION_REL using (MODEL_ID)", "join    HIERARCHY h using (DIMENSION_ID)", "where   FINANCE_CUBE_ID = ${financeCubeId}", "${matchStructureNames}", "and     DIMENSION_TYPE <> 3", ")", ",getElems as", "(", "-- non-leaf structure elements", "select  DIMENSION_SEQ_NUM, STRUCTURE_ELEMENT_ID, VIS_ID,POSITION,LEAF,DESCRIPTION", "from    getStructures", "join    STRUCTURE_ELEMENT using (STRUCTURE_ID)", "where   LEAF <> \'Y\'", "union all", "-- leaf dimension elements", "select  DIMENSION_SEQ_NUM, DIMENSION_ELEMENT_ID, VIS_ID, 0, \'Y\', DESCRIPTION", "from    (select distinct DIMENSION_SEQ_NUM, DIMENSION_ID from getStructures)", "join    DIMENSION_ELEMENT using (DIMENSION_ID)", "order   by DIMENSION_SEQ_NUM, STRUCTURE_ELEMENT_ID", ")", "select  /*+ NO_QUERY_TRANSFORMATION */", "         ${leadVisIds}", "        ,d${calDimIndex}.VIS_ID as VIS${calDimIndex}", "        ,DATA_TYPE", "        ${valueColumns}", "from    ${reportSource} tx", "join    DATA_TYPE dt on (dt.VIS_ID = DATA_TYPE)", "${joinLeadDims}", "join    getCalDim d${calDimIndex}", "        on (d${calDimIndex}.STRUCTURE_ELEMENT_ID = DIM${calDimIndex})", "order   by", "        ${orderBy}", "        ,d${calDimIndex}.POSITION"});
      sqlb.substituteRepeatingLines("${leadVisIds}", this.getNumDims() - 1, ",", new String[]{"${separator}d${index}.VIS_ID||\' - \'||d${index}.DESCRIPTION as VIS${index}"});
      sqlb.substituteRepeatingLines("${joinLeadDims}", this.getNumDims() - 1, (String)null, new String[]{"join    getElems d${index}", "        on (d${index}.DIMENSION_SEQ_NUM = ${index} and", "            d${index}.STRUCTURE_ELEMENT_ID = DIM${index})"});
      sqlb.substituteRepeatingLines("${orderBy}", this.getNumDims() - 1, ",", new String[]{"${separator}decode(d${index}.LEAF,\' \',\'0\'||to_char(1000000+d${index}.POSITION),\'1\'||d${index}.VIS_ID)"});
      sqlb.substitute(new String[]{"${financeCubeId}", String.valueOf(this.getRequest().getFinanceCubeId()), "${calDimIndex}", String.valueOf(this.getNumDims() - 1)});
      List hierarchyNames = this.getRequest().getDataExtract().getHierarchyList();
      if(hierarchyNames != null && hierarchyNames.size() > 0) {
         sqlb.substitute(new String[]{"${matchStructureNames}", "and     h.VIS_ID in (${matchStructureName})"});
         sqlb.substitute(new String[]{"${matchStructureName}", SqlBuilder.repeatString("${separator}<structureName.${index}>", ",", hierarchyNames.size())});
      } else {
         sqlb.substitute(new String[]{"${matchStructureNames}", null});
      }

      return sqlb;
   }

   private void addReportRows(String sectionName, ResultSet rs, TaskReport taskReport) throws SQLException {
      String[] prevVisId = new String[this.getNumDims()];
      taskReport.addMatrixSection(sectionName);
      taskReport.addRow();
      Iterator dataType = this.getDimensionNames().iterator();

      String colValue;
      while(dataType.hasNext()) {
         colValue = (String)dataType.next();
         taskReport.addCellHeading(colValue, "left");
      }

      taskReport.addCellHeading("Data Type", "left");
      taskReport.addCellHeading("Value", "centre");
      taskReport.addEndRow();

      for(; rs.next(); taskReport.addEndRow()) {
         taskReport.addRow();

         for(int var8 = 0; var8 < this.getNumDims(); ++var8) {
            colValue = rs.getString("VIS" + var8);
            if(prevVisId[var8] != null && prevVisId[var8].equals(colValue)) {
               taskReport.addCellText("");
            } else {
               for(int numberValue = var8; numberValue < this.getNumDims(); ++numberValue) {
                  prevVisId[numberValue] = "";
               }

               prevVisId[var8] = colValue;
               taskReport.addCellText(colValue);
            }
         }

         String var9 = rs.getString("DATA_TYPE");
         taskReport.addCellText(var9);
         Object var10 = rs.getObject("VALUE");
         if(var10 != null) {
            if(rs.getString("IS_NUMBER").equals("Y")) {
               Double var11 = new Double(var10.toString());
               taskReport.addCellText(this.getNumberFormatter().format(var11), "right");
            } else {
               taskReport.addCellText(var10 == null?"":var10.toString());
            }
         }
      }

      taskReport.addEndMatrixSection();
   }

   private FinanceCubeEVO getFinanceCubeEVO() throws ValidationException {
      if(this.mFinanceCubeEVO == null) {
         FinanceCubeDAO fcDAO = new FinanceCubeDAO();
         FinanceCubeRefImpl ref = fcDAO.getRef(new FinanceCubePK(this.getRequest().getFinanceCubeId()));
         this.mFinanceCubeEVO = fcDAO.getDetails((FinanceCubeCK)ref.getPrimaryKey(), "");
      }

      return this.mFinanceCubeEVO;
   }

   private void queryDimensions(int modelId) throws Exception {
      AllDimensionsForModelELO dims = (new DimensionDAO()).getAllDimensionsForModel(modelId);
      this.mDimensionNames = new ArrayList();

      for(int i = 0; i < dims.getNumRows(); ++i) {
         this.mDimensionNames.add(((EntityRef)dims.getValueAt(i, "Dimension")).getNarrative());
      }

   }

   private int getNumDims() {
      return this.mDimensionNames.size();
   }

   private List<String> getDimensionNames() {
      return this.mDimensionNames;
   }

   public int getReportType() {
      return 6;
   }

   private NumberFormat getNumberFormatter() {
      if(this.mNumberFormat == null) {
         this.mNumberFormat = NumberFormat.getInstance();
         this.mNumberFormat.setMaximumFractionDigits(2);
         this.mNumberFormat.setMinimumFractionDigits(2);
      }

      return this.mNumberFormat;
   }

   private DateFormat getDateFormatIn() {
      return this.mDateFormatIn;
   }

   private DateFormat getDateFormatOut() {
      return this.mDateFormatOut;
   }

   private DateFormat getDateTimeFormatIn() {
      return this.mDateTimeFormatIn;
   }

   private DateFormat getDateTimeFormatOut() {
      return this.mDateTimeFormatOut;
   }

   private DateFormat getTimeFormatIn() {
      return this.mTimeFormatIn;
   }

   private DateFormat getTimeFormatOut() {
      return this.mTimeFormatOut;
   }
}
