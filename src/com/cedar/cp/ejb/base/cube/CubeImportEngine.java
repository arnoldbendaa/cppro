// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ModelLockedException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.impexp.ImpExpHdrPK;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.base.async.TaskReport;
import com.cedar.cp.ejb.base.cube.CubeImportEngine$1;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.base.SqlExecutor;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrDAO;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.SqlBuilder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;

public class CubeImportEngine extends AbstractDAO {

   private static final String INSERT_INTO_GLOBAL_TEMP_SQL = "insert into {tft_table_name} \n( select {dims_to_select} data_type, number_value, string_value, date_value \n  from imp_exp_row \n  where batch_id = ? and   row_no >= ? and row_no < ? )";
   private NumberFormat mNumberFormatter;
   private Log mLog = new Log(this.getClass());
   private int mUserId;
   private boolean mReplaceMode;
   private Integer mTaskId;


   public CubeImportEngine(int userId, boolean replaceMode) {
      this.mUserId = userId;
      this.mReplaceMode = replaceMode;
   }

   public String getEntityName() {
      return "CubeImportEngine";
   }

   private void checkModelLockStatus(int financeCubeId) throws ModelLockedException {}

   private ImpExpHdrEVO loadBatchHeader(int batchId) throws ValidationException {
      ImpExpHdrEVO var4;
      try {
         ImpExpHdrDAO ioHdrDAO = new ImpExpHdrDAO(this.getConnection());
         ImpExpHdrEVO impExpHdr = ioHdrDAO.getDetails(new ImpExpHdrPK(batchId), "");
         if(impExpHdr == null) {
            throw new ValidationException("Batch id " + batchId + " not found");
         }

         var4 = impExpHdr;
      } finally {
         this.closeConnection();
      }

      return var4;
   }

   private FinanceCubeEVO getFinanceCubeEVO(int financeCubeId) throws ValidationException {
      FinanceCubeDAO fcDAO = new FinanceCubeDAO();
      return fcDAO.getDetails(new FinanceCubeCK(new ModelPK(0), new FinanceCubePK(financeCubeId)), "");
   }

   public FinanceCubeEVO initialiseImport(int batchId, boolean checkModelLocked) throws ModelLockedException, ValidationException {
      ImpExpHdrEVO impExpHdr = this.loadBatchHeader(batchId);
      if(checkModelLocked) {
         this.checkModelLockStatus(impExpHdr.getFinanceCubeId());
      }

      if(impExpHdr.getBatchType() != 0) {
         throw new ValidationException("Batch type is not :0 i.e. IMPORT!");
      } else {
         CubeUpdateEngine engine = new CubeUpdateEngine();
         engine.setFinanceCubeId(impExpHdr.getFinanceCubeId());
         engine.setFinanceCubeHasDataFlag();
         return engine.getFinanceCubeEVO();
      }
   }

   private int getNumberOfDimensions(int financeCubeId) {
      int var5;
      try {
         ModelDAO modelDAO = new ModelDAO(this.getConnection());
         FinanceCubeCK fcCK = modelDAO.getFinanceCubeCK(new FinanceCubePK(financeCubeId));
         ModelDimensionsELO modelDimensions = modelDAO.getModelDimensions(fcCK.getModelPK().getModelId());
         var5 = modelDimensions.getNumRows();
      } finally {
         this.closeConnection();
      }

      return var5;
   }

   private String queryInsertTempSQL(int financeCubeId, int numDims) {
      CubeImportEngine$1 parser = new CubeImportEngine$1(this, "insert into {tft_table_name} \n( select {dims_to_select} data_type, number_value, string_value, date_value \n  from imp_exp_row \n  where batch_id = ? and   row_no >= ? and row_no < ? )", financeCubeId, numDims);
      return parser.parse();
   }

   private int insertDeltasIntoTempTable(int batchId, int financeCubeId, int numDims, int startRow, int batchSize) throws SQLException {
      PreparedStatement stmt = null;

      int var8;
      try {
         String insSQL = this.queryInsertTempSQL(financeCubeId, numDims);
         stmt = this.getConnection().prepareStatement(insSQL);
         stmt.setInt(1, batchId);
         stmt.setInt(2, startRow);
         stmt.setInt(3, startRow + batchSize);
         var8 = stmt.executeUpdate();
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }

      return var8;
   }

   public int processBatch(int batchId, int startRowNo, int chunkSize, TaskReport tr, List<String> structureNames) throws SQLException {
      int cubeUpdateEngine1;
      try {
         this.mLog.info("processBatch", "startRow=" + startRowNo + " batch=" + chunkSize);
         ImpExpHdrEVO impExpHdr = null;

         try {
            impExpHdr = this.loadBatchHeader(batchId);
         } catch (Exception var13) {
            throw new CPException(var13.getMessage(), var13);
         }

         int numDims = this.getNumberOfDimensions(impExpHdr.getFinanceCubeId());
         int rowsProcessed = this.insertDeltasIntoTempTable(batchId, impExpHdr.getFinanceCubeId(), numDims, startRowNo, chunkSize);
         if(rowsProcessed != chunkSize) {
            this.mLog.info("processBatch", "baseRows=" + rowsProcessed);
         }

         if(rowsProcessed > 0) {
            CubeUpdateEngine cubeUpdateEngine = new CubeUpdateEngine();
            cubeUpdateEngine.setFinanceCubeId(impExpHdr.getFinanceCubeId());
            cubeUpdateEngine.setTaskId(this.getTaskId());
            cubeUpdateEngine.setNumDims(numDims);
            if(tr != null) {
               this.writeDetailsToTaskReport(tr, cubeUpdateEngine, impExpHdr.getFinanceCubeId(), numDims, structureNames);
            }

            cubeUpdateEngine.executeCubeUpdate(!this.mReplaceMode, this.mUserId);
            this.mLog.info("processBatch", "merge/updated batch rows");
         }

         cubeUpdateEngine1 = startRowNo + rowsProcessed;
      } finally {
         this.closeConnection();
      }

      return cubeUpdateEngine1;
   }

   private void writeDetailsToTaskReport(TaskReport tr, CubeUpdateEngine cue, int financeCubeId, int numDims, List<String> hierarchyNames) throws SQLException {
      SqlBuilder sqlb = new SqlBuilder(new String[]{"with getCalDim as", "-- calendar structure, with path to root instead of vis_id", "(", "select  STRUCTURE_ELEMENT_ID, POSITION, LEAF, DESCRIPTION", "        ,calendar_utils.queryPathToRoot(STRUCTURE_ELEMENT_ID) as VIS_ID", "from    FINANCE_CUBE", "join    MODEL_DIMENSION_REL using (MODEL_ID)", "join    HIERARCHY using (DIMENSION_ID)", "join    STRUCTURE_ELEMENT on (STRUCTURE_ID = HIERARCHY_ID)", "where   FINANCE_CUBE_ID = ${financeCubeId}", "and     DIMENSION_TYPE = 3", ")", ",getStructures as", "-- structures used by the model", "(", "select  DIMENSION_SEQ_NUM, DIMENSION_ID, HIERARCHY_ID as STRUCTURE_ID", "from    FINANCE_CUBE", "join    MODEL_DIMENSION_REL using (MODEL_ID)", "join    HIERARCHY h using (DIMENSION_ID)", "where   FINANCE_CUBE_ID = ${financeCubeId}", "${matchStructureNames}", "and     DIMENSION_TYPE <> 3", ")", ",getElems as", "(", "-- non-leaf structure elements", "select  DIMENSION_SEQ_NUM, STRUCTURE_ELEMENT_ID, VIS_ID,POSITION,LEAF,DESCRIPTION", "from    getStructures", "join    STRUCTURE_ELEMENT using (STRUCTURE_ID)", "where   LEAF <> \'Y\'", "union all", "-- leaf dimension elements", "select  DIMENSION_SEQ_NUM, DIMENSION_ELEMENT_ID, VIS_ID, 0, \'Y\', DESCRIPTION", "from    (select distinct DIMENSION_SEQ_NUM, DIMENSION_ID from getStructures)", "join    DIMENSION_ELEMENT using (DIMENSION_ID)", "order   by DIMENSION_SEQ_NUM, STRUCTURE_ELEMENT_ID", ")", "select  /*+ NO_QUERY_TRANSFORMATION */", "         ${leadVisIds}", "        ,d${calDimIndex}.VIS_ID as VIS${calDimIndex}", "        ,DATA_TYPE", "        ,case SUB_TYPE", "         when 0 then to_char(NUMBER_VALUE / 10000)", "         when 4 then", "             case MEASURE_CLASS", "             when 1 then to_char(NUMBER_VALUE / power(10,MEASURE_SCALE))", "             when 0 then STRING_VALUE", "             when 2 then to_char(DATE_VALUE,\'HH24:MI:SS\')", "             when 3 then to_char(DATE_VALUE,\'DD/MM/YYYY\')", "             when 4 then to_char(DATE_VALUE,\'DD/MM/YYYY HH24:MI:SS\')", "             when 5 then decode(STRING_VALUE,\'Y\',\'true\',\' \',\'false\')", "             end", "         end VALUE", "        ,case when SUB_TYPE <> 4 or MEASURE_CLASS = 1", "              then \'Y\' else \'N\'", "         end as IS_NUMBER", "from    TX1_${financeCubeId} tx", "join    DATA_TYPE dt on (dt.VIS_ID = DATA_TYPE)", "${joinLeadDims}", "join    getCalDim d${calDimIndex}", "        on (d${calDimIndex}.STRUCTURE_ELEMENT_ID = DIM${calDimIndex})", "order   by", "        ${orderBy}", "        ,d${calDimIndex}.POSITION"});
      sqlb.substituteRepeatingLines("${leadVisIds}", numDims - 1, ",", new String[]{"${separator}d${index}.VIS_ID||\' - \'||d${index}.DESCRIPTION as VIS${index}"});
      sqlb.substituteRepeatingLines("${joinLeadDims}", numDims - 1, (String)null, new String[]{"join    getElems d${index}", "        on (d${index}.DIMENSION_SEQ_NUM = ${index} and", "            d${index}.STRUCTURE_ELEMENT_ID = DIM${index})"});
      sqlb.substituteRepeatingLines("${orderBy}", numDims - 1, ",", new String[]{"${separator}decode(d${index}.LEAF,\' \',\'0\'||to_char(1000000+d${index}.POSITION),\'1\'||d${index}.VIS_ID)"});
      sqlb.substitute(new String[]{"${financeCubeId}", String.valueOf(financeCubeId), "${calDimIndex}", String.valueOf(numDims - 1)});
      if(hierarchyNames != null && hierarchyNames.size() > 0) {
         sqlb.substitute(new String[]{"${matchStructureNames}", "and     h.VIS_ID in (${matchStructureName})"});
         sqlb.substitute(new String[]{"${matchStructureName}", SqlBuilder.repeatString("${separator}<structureName.${index}>", ",", hierarchyNames.size())});
      } else {
         sqlb.substitute(new String[]{"${matchStructureNames}", null});
      }

      SqlExecutor sqle = new SqlExecutor("writeDetailsToTaskReport", this.getDataSource(), sqlb, this._log);
      if(hierarchyNames != null) {
         for(int rs = 0; rs < hierarchyNames.size(); ++rs) {
            sqle.addBindVariable("<structureName." + rs + ">", (String)hierarchyNames.get(rs));
         }
      }

      ResultSet var16 = sqle.getResultSet();
      String[] prevVisId = new String[numDims];
      this.getNumberFormatter().setMaximumFractionDigits(14);

      try {
         for(; var16.next(); tr.addEndRow()) {
            tr.addRow();

            for(int dataType = 0; dataType < numDims; ++dataType) {
               String colValue = var16.getString("VIS" + dataType);
               if(prevVisId[dataType] != null && prevVisId[dataType].equals(colValue)) {
                  tr.addCellText("");
               } else {
                  for(int numberValue = dataType; numberValue < numDims; ++numberValue) {
                     prevVisId[numberValue] = "";
                  }

                  prevVisId[dataType] = colValue;
                  tr.addCellText(colValue);
               }
            }

            String var18 = var16.getString("DATA_TYPE");
            tr.addCellText(var18);
            Object var17 = var16.getObject("VALUE");
            if(var16.getString("IS_NUMBER").equals("Y")) {
               Double var19 = new Double(var17.toString());
               tr.addCellText(this.getNumberFormatter().format(var19), "right");
            } else {
               tr.addCellText(var17 == null?"":var17.toString());
            }
         }
      } finally {
         sqle.close();
      }

   }

   public void terminateImport(int batchId) throws Exception {
      try {
         ImpExpHdrDAO impExpHdrDAO = new ImpExpHdrDAO(this.getConnection());
         impExpHdrDAO.getDetails(new ImpExpHdrPK(batchId), "");
         impExpHdrDAO.remove();
      } finally {
         this.closeConnection();
      }

   }

   private NumberFormat getNumberFormatter() {
      if(this.mNumberFormatter == null) {
         this.mNumberFormatter = NumberFormat.getInstance();
         this.mNumberFormatter.setMaximumFractionDigits(2);
         this.mNumberFormatter.setMinimumFractionDigits(2);
      }

      return this.mNumberFormatter;
   }

   public void setTaskId(Integer taskId) {
      this.mTaskId = taskId;
   }

   public Integer getTaskId() {
      return this.mTaskId;
   }
}
