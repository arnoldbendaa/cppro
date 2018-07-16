/*      */ package com.cedar.cp.ejb.impl.report.definition;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.report.definition.AllReportDefFormcByModelIdELO;
/*      */ import com.cedar.cp.dto.report.definition.AllReportDefFormcByReportTemplateIdELO;
/*      */ import com.cedar.cp.dto.report.definition.CheckFormIsUsedELO;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefFormCK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefFormPK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefFormRefImpl;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefinitionCK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefinitionRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class ReportDefFormDAO extends AbstractDAO
/*      */ {
/*   32 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select REPORT_DEF_FORM.REPORT_DEFINITION_ID,REPORT_DEF_FORM.MODEL_ID,REPORT_DEF_FORM.FORM_ID,REPORT_DEF_FORM.DATA_TYPE_ID,REPORT_DEF_FORM.STRUCTURE_ID0,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID0,REPORT_DEF_FORM.STRUCTURE_ID1,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID1,REPORT_DEF_FORM.STRUCTURE_ID2,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID2,REPORT_DEF_FORM.STRUCTURE_ID3,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID3,REPORT_DEF_FORM.STRUCTURE_ID4,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID4,REPORT_DEF_FORM.STRUCTURE_ID5,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID5,REPORT_DEF_FORM.STRUCTURE_ID6,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID6,REPORT_DEF_FORM.STRUCTURE_ID7,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID7,REPORT_DEF_FORM.STRUCTURE_ID8,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID8,REPORT_DEF_FORM.STRUCTURE_ID9,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID9,REPORT_DEF_FORM.REPORT_DEPTH,REPORT_DEF_FORM.AUTO_EXPAND_DEPTH,REPORT_DEF_FORM.REPORT_TEMPLATE_ID,REPORT_DEF_FORM.UPDATED_BY_USER_ID,REPORT_DEF_FORM.UPDATED_TIME,REPORT_DEF_FORM.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from REPORT_DEF_FORM where    REPORT_DEFINITION_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into REPORT_DEF_FORM ( REPORT_DEFINITION_ID,MODEL_ID,FORM_ID,DATA_TYPE_ID,STRUCTURE_ID0,STRUCTURE_ELEMENT_ID0,STRUCTURE_ID1,STRUCTURE_ELEMENT_ID1,STRUCTURE_ID2,STRUCTURE_ELEMENT_ID2,STRUCTURE_ID3,STRUCTURE_ELEMENT_ID3,STRUCTURE_ID4,STRUCTURE_ELEMENT_ID4,STRUCTURE_ID5,STRUCTURE_ELEMENT_ID5,STRUCTURE_ID6,STRUCTURE_ELEMENT_ID6,STRUCTURE_ID7,STRUCTURE_ELEMENT_ID7,STRUCTURE_ID8,STRUCTURE_ELEMENT_ID8,STRUCTURE_ID9,STRUCTURE_ELEMENT_ID9,REPORT_DEPTH,AUTO_EXPAND_DEPTH,REPORT_TEMPLATE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update REPORT_DEF_FORM set MODEL_ID = ?,FORM_ID = ?,DATA_TYPE_ID = ?,STRUCTURE_ID0 = ?,STRUCTURE_ELEMENT_ID0 = ?,STRUCTURE_ID1 = ?,STRUCTURE_ELEMENT_ID1 = ?,STRUCTURE_ID2 = ?,STRUCTURE_ELEMENT_ID2 = ?,STRUCTURE_ID3 = ?,STRUCTURE_ELEMENT_ID3 = ?,STRUCTURE_ID4 = ?,STRUCTURE_ELEMENT_ID4 = ?,STRUCTURE_ID5 = ?,STRUCTURE_ELEMENT_ID5 = ?,STRUCTURE_ID6 = ?,STRUCTURE_ELEMENT_ID6 = ?,STRUCTURE_ID7 = ?,STRUCTURE_ELEMENT_ID7 = ?,STRUCTURE_ID8 = ?,STRUCTURE_ELEMENT_ID8 = ?,STRUCTURE_ID9 = ?,STRUCTURE_ELEMENT_ID9 = ?,REPORT_DEPTH = ?,AUTO_EXPAND_DEPTH = ?,REPORT_TEMPLATE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? ";
/*  453 */   protected static String SQL_ALL_REPORT_DEF_FORMC_BY_REPORT_TEMPLATE_ID = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEF_FORM.REPORT_DEFINITION_ID      ,REPORT_DEF_FORM.REPORT_DEFINITION_ID      ,REPORT_DEF_FORM.MODEL_ID      ,REPORT_DEF_FORM.FORM_ID      ,REPORT_DEF_FORM.DATA_TYPE_ID      ,REPORT_DEF_FORM.REPORT_TEMPLATE_ID from REPORT_DEF_FORM    ,REPORT_DEFINITION where 1=1   and REPORT_DEF_FORM.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID  and  REPORT_TEMPLATE_ID = ?";
/*      */ 
/*  574 */   protected static String SQL_ALL_REPORT_DEF_FORMC_BY_MODEL_ID = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEF_FORM.REPORT_DEFINITION_ID      ,REPORT_DEF_FORM.REPORT_DEFINITION_ID      ,REPORT_DEF_FORM.MODEL_ID      ,REPORT_DEF_FORM.FORM_ID      ,REPORT_DEF_FORM.DATA_TYPE_ID      ,REPORT_DEF_FORM.REPORT_TEMPLATE_ID from REPORT_DEF_FORM    ,REPORT_DEFINITION where 1=1   and REPORT_DEF_FORM.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID  and  MODEL_ID = ?";
/*      */ 
/*  695 */   protected static String SQL_CHECK_FORM_IS_USED = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEF_FORM.REPORT_DEFINITION_ID      ,REPORT_DEF_FORM.REPORT_DEFINITION_ID      ,REPORT_DEF_FORM.MODEL_ID      ,REPORT_DEF_FORM.FORM_ID from REPORT_DEF_FORM    ,REPORT_DEFINITION where 1=1   and REPORT_DEF_FORM.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID  and  FORM_ID = ?";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from REPORT_DEF_FORM where    REPORT_DEFINITION_ID = ? ";
/*      */   public static final String SQL_BULK_GET_ALL = " from REPORT_DEF_FORM where 1=1 and REPORT_DEF_FORM.REPORT_DEFINITION_ID = ? order by  REPORT_DEF_FORM.REPORT_DEFINITION_ID";
/*      */   protected static final String SQL_GET_ALL = " from REPORT_DEF_FORM where    REPORT_DEFINITION_ID = ? ";
/*      */   protected ReportDefFormEVO mDetails;
/*      */ 
/*      */   public ReportDefFormDAO(Connection connection)
/*      */   {
/*   39 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ReportDefFormDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ReportDefFormDAO(DataSource ds)
/*      */   {
/*   55 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ReportDefFormPK getPK()
/*      */   {
/*   63 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ReportDefFormEVO details)
/*      */   {
/*   72 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private ReportDefFormEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  113 */     int col = 1;
/*  114 */     ReportDefFormEVO evo = new ReportDefFormEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*      */ 
/*  144 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  145 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  146 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  147 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ReportDefFormEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  152 */     int col = startCol_;
/*  153 */     stmt_.setInt(col++, evo_.getReportDefinitionId());
/*  154 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ReportDefFormEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  159 */     int col = startCol_;
/*  160 */     stmt_.setInt(col++, evo_.getModelId());
/*  161 */     stmt_.setInt(col++, evo_.getFormId());
/*  162 */     stmt_.setInt(col++, evo_.getDataTypeId());
/*  163 */     stmt_.setInt(col++, evo_.getStructureId0());
/*  164 */     stmt_.setInt(col++, evo_.getStructureElementId0());
/*  165 */     stmt_.setInt(col++, evo_.getStructureId1());
/*  166 */     stmt_.setInt(col++, evo_.getStructureElementId1());
/*  167 */     stmt_.setInt(col++, evo_.getStructureId2());
/*  168 */     stmt_.setInt(col++, evo_.getStructureElementId2());
/*  169 */     stmt_.setInt(col++, evo_.getStructureId3());
/*  170 */     stmt_.setInt(col++, evo_.getStructureElementId3());
/*  171 */     stmt_.setInt(col++, evo_.getStructureId4());
/*  172 */     stmt_.setInt(col++, evo_.getStructureElementId4());
/*  173 */     stmt_.setInt(col++, evo_.getStructureId5());
/*  174 */     stmt_.setInt(col++, evo_.getStructureElementId5());
/*  175 */     stmt_.setInt(col++, evo_.getStructureId6());
/*  176 */     stmt_.setInt(col++, evo_.getStructureElementId6());
/*  177 */     stmt_.setInt(col++, evo_.getStructureId7());
/*  178 */     stmt_.setInt(col++, evo_.getStructureElementId7());
/*  179 */     stmt_.setInt(col++, evo_.getStructureId8());
/*  180 */     stmt_.setInt(col++, evo_.getStructureElementId8());
/*  181 */     stmt_.setInt(col++, evo_.getStructureId9());
/*  182 */     stmt_.setInt(col++, evo_.getStructureElementId9());
/*  183 */     stmt_.setInt(col++, evo_.getReportDepth());
/*  184 */     stmt_.setInt(col++, evo_.getAutoExpandDepth());
/*  185 */     stmt_.setInt(col++, evo_.getReportTemplateId());
/*  186 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  187 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  188 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  189 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ReportDefFormPK pk)
/*      */     throws ValidationException
/*      */   {
/*  205 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  207 */     PreparedStatement stmt = null;
/*  208 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  212 */       stmt = getConnection().prepareStatement("select REPORT_DEF_FORM.REPORT_DEFINITION_ID,REPORT_DEF_FORM.MODEL_ID,REPORT_DEF_FORM.FORM_ID,REPORT_DEF_FORM.DATA_TYPE_ID,REPORT_DEF_FORM.STRUCTURE_ID0,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID0,REPORT_DEF_FORM.STRUCTURE_ID1,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID1,REPORT_DEF_FORM.STRUCTURE_ID2,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID2,REPORT_DEF_FORM.STRUCTURE_ID3,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID3,REPORT_DEF_FORM.STRUCTURE_ID4,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID4,REPORT_DEF_FORM.STRUCTURE_ID5,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID5,REPORT_DEF_FORM.STRUCTURE_ID6,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID6,REPORT_DEF_FORM.STRUCTURE_ID7,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID7,REPORT_DEF_FORM.STRUCTURE_ID8,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID8,REPORT_DEF_FORM.STRUCTURE_ID9,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID9,REPORT_DEF_FORM.REPORT_DEPTH,REPORT_DEF_FORM.AUTO_EXPAND_DEPTH,REPORT_DEF_FORM.REPORT_TEMPLATE_ID,REPORT_DEF_FORM.UPDATED_BY_USER_ID,REPORT_DEF_FORM.UPDATED_TIME,REPORT_DEF_FORM.CREATED_TIME from REPORT_DEF_FORM where    REPORT_DEFINITION_ID = ? ");
/*      */ 
/*  215 */       int col = 1;
/*  216 */       stmt.setInt(col++, pk.getReportDefinitionId());
/*      */ 
/*  218 */       resultSet = stmt.executeQuery();
/*      */ 
/*  220 */       if (!resultSet.next()) {
/*  221 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  224 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  225 */       if (this.mDetails.isModified())
/*  226 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  230 */       throw handleSQLException(pk, "select REPORT_DEF_FORM.REPORT_DEFINITION_ID,REPORT_DEF_FORM.MODEL_ID,REPORT_DEF_FORM.FORM_ID,REPORT_DEF_FORM.DATA_TYPE_ID,REPORT_DEF_FORM.STRUCTURE_ID0,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID0,REPORT_DEF_FORM.STRUCTURE_ID1,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID1,REPORT_DEF_FORM.STRUCTURE_ID2,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID2,REPORT_DEF_FORM.STRUCTURE_ID3,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID3,REPORT_DEF_FORM.STRUCTURE_ID4,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID4,REPORT_DEF_FORM.STRUCTURE_ID5,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID5,REPORT_DEF_FORM.STRUCTURE_ID6,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID6,REPORT_DEF_FORM.STRUCTURE_ID7,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID7,REPORT_DEF_FORM.STRUCTURE_ID8,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID8,REPORT_DEF_FORM.STRUCTURE_ID9,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID9,REPORT_DEF_FORM.REPORT_DEPTH,REPORT_DEF_FORM.AUTO_EXPAND_DEPTH,REPORT_DEF_FORM.REPORT_TEMPLATE_ID,REPORT_DEF_FORM.UPDATED_BY_USER_ID,REPORT_DEF_FORM.UPDATED_TIME,REPORT_DEF_FORM.CREATED_TIME from REPORT_DEF_FORM where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  234 */       closeResultSet(resultSet);
/*  235 */       closeStatement(stmt);
/*  236 */       closeConnection();
/*      */ 
/*  238 */       if (timer != null)
/*  239 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  316 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  317 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  322 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  323 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  324 */       stmt = getConnection().prepareStatement("insert into REPORT_DEF_FORM ( REPORT_DEFINITION_ID,MODEL_ID,FORM_ID,DATA_TYPE_ID,STRUCTURE_ID0,STRUCTURE_ELEMENT_ID0,STRUCTURE_ID1,STRUCTURE_ELEMENT_ID1,STRUCTURE_ID2,STRUCTURE_ELEMENT_ID2,STRUCTURE_ID3,STRUCTURE_ELEMENT_ID3,STRUCTURE_ID4,STRUCTURE_ELEMENT_ID4,STRUCTURE_ID5,STRUCTURE_ELEMENT_ID5,STRUCTURE_ID6,STRUCTURE_ELEMENT_ID6,STRUCTURE_ID7,STRUCTURE_ELEMENT_ID7,STRUCTURE_ID8,STRUCTURE_ELEMENT_ID8,STRUCTURE_ID9,STRUCTURE_ELEMENT_ID9,REPORT_DEPTH,AUTO_EXPAND_DEPTH,REPORT_TEMPLATE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  327 */       int col = 1;
/*  328 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  329 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  332 */       int resultCount = stmt.executeUpdate();
/*  333 */       if (resultCount != 1)
/*      */       {
/*  335 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  338 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  342 */       throw handleSQLException(this.mDetails.getPK(), "insert into REPORT_DEF_FORM ( REPORT_DEFINITION_ID,MODEL_ID,FORM_ID,DATA_TYPE_ID,STRUCTURE_ID0,STRUCTURE_ELEMENT_ID0,STRUCTURE_ID1,STRUCTURE_ELEMENT_ID1,STRUCTURE_ID2,STRUCTURE_ELEMENT_ID2,STRUCTURE_ID3,STRUCTURE_ELEMENT_ID3,STRUCTURE_ID4,STRUCTURE_ELEMENT_ID4,STRUCTURE_ID5,STRUCTURE_ELEMENT_ID5,STRUCTURE_ID6,STRUCTURE_ELEMENT_ID6,STRUCTURE_ID7,STRUCTURE_ELEMENT_ID7,STRUCTURE_ID8,STRUCTURE_ELEMENT_ID8,STRUCTURE_ID9,STRUCTURE_ELEMENT_ID9,REPORT_DEPTH,AUTO_EXPAND_DEPTH,REPORT_TEMPLATE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  346 */       closeStatement(stmt);
/*  347 */       closeConnection();
/*      */ 
/*  349 */       if (timer != null)
/*  350 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  398 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  402 */     PreparedStatement stmt = null;
/*      */ 
/*  404 */     boolean mainChanged = this.mDetails.isModified();
/*  405 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  408 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  411 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  412 */         stmt = getConnection().prepareStatement("update REPORT_DEF_FORM set MODEL_ID = ?,FORM_ID = ?,DATA_TYPE_ID = ?,STRUCTURE_ID0 = ?,STRUCTURE_ELEMENT_ID0 = ?,STRUCTURE_ID1 = ?,STRUCTURE_ELEMENT_ID1 = ?,STRUCTURE_ID2 = ?,STRUCTURE_ELEMENT_ID2 = ?,STRUCTURE_ID3 = ?,STRUCTURE_ELEMENT_ID3 = ?,STRUCTURE_ID4 = ?,STRUCTURE_ELEMENT_ID4 = ?,STRUCTURE_ID5 = ?,STRUCTURE_ELEMENT_ID5 = ?,STRUCTURE_ID6 = ?,STRUCTURE_ELEMENT_ID6 = ?,STRUCTURE_ID7 = ?,STRUCTURE_ELEMENT_ID7 = ?,STRUCTURE_ID8 = ?,STRUCTURE_ELEMENT_ID8 = ?,STRUCTURE_ID9 = ?,STRUCTURE_ELEMENT_ID9 = ?,REPORT_DEPTH = ?,AUTO_EXPAND_DEPTH = ?,REPORT_TEMPLATE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? ");
/*      */ 
/*  415 */         int col = 1;
/*  416 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  417 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  420 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  423 */         if (resultCount != 1) {
/*  424 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  427 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  436 */       throw handleSQLException(getPK(), "update REPORT_DEF_FORM set MODEL_ID = ?,FORM_ID = ?,DATA_TYPE_ID = ?,STRUCTURE_ID0 = ?,STRUCTURE_ELEMENT_ID0 = ?,STRUCTURE_ID1 = ?,STRUCTURE_ELEMENT_ID1 = ?,STRUCTURE_ID2 = ?,STRUCTURE_ELEMENT_ID2 = ?,STRUCTURE_ID3 = ?,STRUCTURE_ELEMENT_ID3 = ?,STRUCTURE_ID4 = ?,STRUCTURE_ELEMENT_ID4 = ?,STRUCTURE_ID5 = ?,STRUCTURE_ELEMENT_ID5 = ?,STRUCTURE_ID6 = ?,STRUCTURE_ELEMENT_ID6 = ?,STRUCTURE_ID7 = ?,STRUCTURE_ELEMENT_ID7 = ?,STRUCTURE_ID8 = ?,STRUCTURE_ELEMENT_ID8 = ?,STRUCTURE_ID9 = ?,STRUCTURE_ELEMENT_ID9 = ?,REPORT_DEPTH = ?,AUTO_EXPAND_DEPTH = ?,REPORT_TEMPLATE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  440 */       closeStatement(stmt);
/*  441 */       closeConnection();
/*      */ 
/*  443 */       if ((timer != null) && (
/*  444 */         (mainChanged) || (dependantChanged)))
/*  445 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllReportDefFormcByReportTemplateIdELO getAllReportDefFormcByReportTemplateId(int param1)
/*      */   {
/*  487 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  488 */     PreparedStatement stmt = null;
/*  489 */     ResultSet resultSet = null;
/*  490 */     AllReportDefFormcByReportTemplateIdELO results = new AllReportDefFormcByReportTemplateIdELO();
/*      */     try
/*      */     {
/*  493 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_DEF_FORMC_BY_REPORT_TEMPLATE_ID);
/*  494 */       int col = 1;
/*  495 */       stmt.setInt(col++, param1);
/*  496 */       resultSet = stmt.executeQuery();
/*  497 */       while (resultSet.next())
/*      */       {
/*  499 */         col = 2;
/*      */ 
/*  502 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  505 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  508 */         ReportDefFormPK pkReportDefForm = new ReportDefFormPK(resultSet.getInt(col++));
/*      */ 
/*  511 */         String textReportDefForm = "";
/*      */ 
/*  516 */         ReportDefFormCK ckReportDefForm = new ReportDefFormCK(pkReportDefinition, pkReportDefForm);
/*      */ 
/*  522 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  528 */         ReportDefFormRefImpl erReportDefForm = new ReportDefFormRefImpl(ckReportDefForm, textReportDefForm);
/*      */ 
/*  533 */         int col1 = resultSet.getInt(col++);
/*  534 */         int col2 = resultSet.getInt(col++);
/*  535 */         int col3 = resultSet.getInt(col++);
/*  536 */         int col4 = resultSet.getInt(col++);
/*  537 */         int col5 = resultSet.getInt(col++);
/*      */ 
/*  540 */         results.add(erReportDefForm, erReportDefinition, col1, col2, col3, col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  553 */       throw handleSQLException(SQL_ALL_REPORT_DEF_FORMC_BY_REPORT_TEMPLATE_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  557 */       closeResultSet(resultSet);
/*  558 */       closeStatement(stmt);
/*  559 */       closeConnection();
/*      */     }
/*      */ 
/*  562 */     if (timer != null) {
/*  563 */       timer.logDebug("getAllReportDefFormcByReportTemplateId", " ReportTemplateId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  568 */     return results;
/*      */   }
/*      */ 
/*      */   public AllReportDefFormcByModelIdELO getAllReportDefFormcByModelId(int param1)
/*      */   {
/*  608 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  609 */     PreparedStatement stmt = null;
/*  610 */     ResultSet resultSet = null;
/*  611 */     AllReportDefFormcByModelIdELO results = new AllReportDefFormcByModelIdELO();
/*      */     try
/*      */     {
/*  614 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_DEF_FORMC_BY_MODEL_ID);
/*  615 */       int col = 1;
/*  616 */       stmt.setInt(col++, param1);
/*  617 */       resultSet = stmt.executeQuery();
/*  618 */       while (resultSet.next())
/*      */       {
/*  620 */         col = 2;
/*      */ 
/*  623 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  626 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  629 */         ReportDefFormPK pkReportDefForm = new ReportDefFormPK(resultSet.getInt(col++));
/*      */ 
/*  632 */         String textReportDefForm = "";
/*      */ 
/*  637 */         ReportDefFormCK ckReportDefForm = new ReportDefFormCK(pkReportDefinition, pkReportDefForm);
/*      */ 
/*  643 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  649 */         ReportDefFormRefImpl erReportDefForm = new ReportDefFormRefImpl(ckReportDefForm, textReportDefForm);
/*      */ 
/*  654 */         int col1 = resultSet.getInt(col++);
/*  655 */         int col2 = resultSet.getInt(col++);
/*  656 */         int col3 = resultSet.getInt(col++);
/*  657 */         int col4 = resultSet.getInt(col++);
/*  658 */         int col5 = resultSet.getInt(col++);
/*      */ 
/*  661 */         results.add(erReportDefForm, erReportDefinition, col1, col2, col3, col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  674 */       throw handleSQLException(SQL_ALL_REPORT_DEF_FORMC_BY_MODEL_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  678 */       closeResultSet(resultSet);
/*  679 */       closeStatement(stmt);
/*  680 */       closeConnection();
/*      */     }
/*      */ 
/*  683 */     if (timer != null) {
/*  684 */       timer.logDebug("getAllReportDefFormcByModelId", " ModelId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  689 */     return results;
/*      */   }
/*      */ 
/*      */   public CheckFormIsUsedELO getCheckFormIsUsed(int param1)
/*      */   {
/*  727 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  728 */     PreparedStatement stmt = null;
/*  729 */     ResultSet resultSet = null;
/*  730 */     CheckFormIsUsedELO results = new CheckFormIsUsedELO();
/*      */     try
/*      */     {
/*  733 */       stmt = getConnection().prepareStatement(SQL_CHECK_FORM_IS_USED);
/*  734 */       int col = 1;
/*  735 */       stmt.setInt(col++, param1);
/*  736 */       resultSet = stmt.executeQuery();
/*  737 */       while (resultSet.next())
/*      */       {
/*  739 */         col = 2;
/*      */ 
/*  742 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  745 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  748 */         ReportDefFormPK pkReportDefForm = new ReportDefFormPK(resultSet.getInt(col++));
/*      */ 
/*  751 */         String textReportDefForm = "";
/*      */ 
/*  756 */         ReportDefFormCK ckReportDefForm = new ReportDefFormCK(pkReportDefinition, pkReportDefForm);
/*      */ 
/*  762 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  768 */         ReportDefFormRefImpl erReportDefForm = new ReportDefFormRefImpl(ckReportDefForm, textReportDefForm);
/*      */ 
/*  773 */         int col1 = resultSet.getInt(col++);
/*  774 */         int col2 = resultSet.getInt(col++);
/*  775 */         int col3 = resultSet.getInt(col++);
/*      */ 
/*  778 */         results.add(erReportDefForm, erReportDefinition, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  789 */       throw handleSQLException(SQL_CHECK_FORM_IS_USED, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  793 */       closeResultSet(resultSet);
/*  794 */       closeStatement(stmt);
/*  795 */       closeConnection();
/*      */     }
/*      */ 
/*  798 */     if (timer != null) {
/*  799 */       timer.logDebug("getCheckFormIsUsed", " FormId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  804 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  821 */     if (items == null) {
/*  822 */       return false;
/*      */     }
/*  824 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  825 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  827 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  832 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  833 */       while (iter2.hasNext())
/*      */       {
/*  835 */         this.mDetails = ((ReportDefFormEVO)iter2.next());
/*      */ 
/*  838 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  840 */         somethingChanged = true;
/*      */ 
/*  843 */         if (deleteStmt == null) {
/*  844 */           deleteStmt = getConnection().prepareStatement("delete from REPORT_DEF_FORM where    REPORT_DEFINITION_ID = ? ");
/*      */         }
/*      */ 
/*  847 */         int col = 1;
/*  848 */         deleteStmt.setInt(col++, this.mDetails.getReportDefinitionId());
/*      */ 
/*  850 */         if (this._log.isDebugEnabled()) {
/*  851 */           this._log.debug("update", "ReportDefForm deleting ReportDefinitionId=" + this.mDetails.getReportDefinitionId());
/*      */         }
/*      */ 
/*  856 */         deleteStmt.addBatch();
/*      */ 
/*  859 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  864 */       if (deleteStmt != null)
/*      */       {
/*  866 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  868 */         deleteStmt.executeBatch();
/*      */ 
/*  870 */         if (timer2 != null) {
/*  871 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  875 */       Iterator iter1 = items.values().iterator();
/*  876 */       while (iter1.hasNext())
/*      */       {
/*  878 */         this.mDetails = ((ReportDefFormEVO)iter1.next());
/*      */ 
/*  880 */         if (this.mDetails.insertPending())
/*      */         {
/*  882 */           somethingChanged = true;
/*  883 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  886 */         if (!this.mDetails.isModified())
/*      */           continue;
/*  888 */         somethingChanged = true;
/*  889 */         doStore();
/*      */       }
/*      */ 
/*  900 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  904 */       throw handleSQLException("delete from REPORT_DEF_FORM where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  908 */       if (deleteStmt != null)
/*      */       {
/*  910 */         closeStatement(deleteStmt);
/*  911 */         closeConnection();
/*      */       }
/*      */ 
/*  914 */       this.mDetails = null;
/*      */ 
/*  916 */       if ((somethingChanged) && 
/*  917 */         (timer != null))
/*  918 */         timer.logDebug("update", "collection"); 
/*  918 */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ReportDefinitionPK entityPK, ReportDefinitionEVO owningEVO, String dependants)
/*      */   {
/*  937 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  939 */     PreparedStatement stmt = null;
/*  940 */     ResultSet resultSet = null;
/*      */ 
/*  942 */     int itemCount = 0;
/*      */ 
/*  944 */     Collection theseItems = new ArrayList();
/*  945 */     owningEVO.setReportForm(theseItems);
/*  946 */     owningEVO.setReportFormAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  950 */       stmt = getConnection().prepareStatement("select REPORT_DEF_FORM.REPORT_DEFINITION_ID,REPORT_DEF_FORM.MODEL_ID,REPORT_DEF_FORM.FORM_ID,REPORT_DEF_FORM.DATA_TYPE_ID,REPORT_DEF_FORM.STRUCTURE_ID0,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID0,REPORT_DEF_FORM.STRUCTURE_ID1,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID1,REPORT_DEF_FORM.STRUCTURE_ID2,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID2,REPORT_DEF_FORM.STRUCTURE_ID3,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID3,REPORT_DEF_FORM.STRUCTURE_ID4,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID4,REPORT_DEF_FORM.STRUCTURE_ID5,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID5,REPORT_DEF_FORM.STRUCTURE_ID6,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID6,REPORT_DEF_FORM.STRUCTURE_ID7,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID7,REPORT_DEF_FORM.STRUCTURE_ID8,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID8,REPORT_DEF_FORM.STRUCTURE_ID9,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID9,REPORT_DEF_FORM.REPORT_DEPTH,REPORT_DEF_FORM.AUTO_EXPAND_DEPTH,REPORT_DEF_FORM.REPORT_TEMPLATE_ID,REPORT_DEF_FORM.UPDATED_BY_USER_ID,REPORT_DEF_FORM.UPDATED_TIME,REPORT_DEF_FORM.CREATED_TIME from REPORT_DEF_FORM where 1=1 and REPORT_DEF_FORM.REPORT_DEFINITION_ID = ? order by  REPORT_DEF_FORM.REPORT_DEFINITION_ID");
/*      */ 
/*  952 */       int col = 1;
/*  953 */       stmt.setInt(col++, entityPK.getReportDefinitionId());
/*      */ 
/*  955 */       resultSet = stmt.executeQuery();
/*      */ 
/*  958 */       while (resultSet.next())
/*      */       {
/*  960 */         itemCount++;
/*  961 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  963 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  966 */       if (timer != null) {
/*  967 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  972 */       throw handleSQLException("select REPORT_DEF_FORM.REPORT_DEFINITION_ID,REPORT_DEF_FORM.MODEL_ID,REPORT_DEF_FORM.FORM_ID,REPORT_DEF_FORM.DATA_TYPE_ID,REPORT_DEF_FORM.STRUCTURE_ID0,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID0,REPORT_DEF_FORM.STRUCTURE_ID1,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID1,REPORT_DEF_FORM.STRUCTURE_ID2,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID2,REPORT_DEF_FORM.STRUCTURE_ID3,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID3,REPORT_DEF_FORM.STRUCTURE_ID4,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID4,REPORT_DEF_FORM.STRUCTURE_ID5,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID5,REPORT_DEF_FORM.STRUCTURE_ID6,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID6,REPORT_DEF_FORM.STRUCTURE_ID7,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID7,REPORT_DEF_FORM.STRUCTURE_ID8,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID8,REPORT_DEF_FORM.STRUCTURE_ID9,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID9,REPORT_DEF_FORM.REPORT_DEPTH,REPORT_DEF_FORM.AUTO_EXPAND_DEPTH,REPORT_DEF_FORM.REPORT_TEMPLATE_ID,REPORT_DEF_FORM.UPDATED_BY_USER_ID,REPORT_DEF_FORM.UPDATED_TIME,REPORT_DEF_FORM.CREATED_TIME from REPORT_DEF_FORM where 1=1 and REPORT_DEF_FORM.REPORT_DEFINITION_ID = ? order by  REPORT_DEF_FORM.REPORT_DEFINITION_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  976 */       closeResultSet(resultSet);
/*  977 */       closeStatement(stmt);
/*  978 */       closeConnection();
/*      */ 
/*  980 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectReportDefinitionId, String dependants, Collection currentList)
/*      */   {
/* 1005 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1006 */     PreparedStatement stmt = null;
/* 1007 */     ResultSet resultSet = null;
/*      */ 
/* 1009 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/* 1013 */       stmt = getConnection().prepareStatement("select REPORT_DEF_FORM.REPORT_DEFINITION_ID,REPORT_DEF_FORM.MODEL_ID,REPORT_DEF_FORM.FORM_ID,REPORT_DEF_FORM.DATA_TYPE_ID,REPORT_DEF_FORM.STRUCTURE_ID0,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID0,REPORT_DEF_FORM.STRUCTURE_ID1,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID1,REPORT_DEF_FORM.STRUCTURE_ID2,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID2,REPORT_DEF_FORM.STRUCTURE_ID3,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID3,REPORT_DEF_FORM.STRUCTURE_ID4,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID4,REPORT_DEF_FORM.STRUCTURE_ID5,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID5,REPORT_DEF_FORM.STRUCTURE_ID6,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID6,REPORT_DEF_FORM.STRUCTURE_ID7,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID7,REPORT_DEF_FORM.STRUCTURE_ID8,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID8,REPORT_DEF_FORM.STRUCTURE_ID9,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID9,REPORT_DEF_FORM.REPORT_DEPTH,REPORT_DEF_FORM.AUTO_EXPAND_DEPTH,REPORT_DEF_FORM.REPORT_TEMPLATE_ID,REPORT_DEF_FORM.UPDATED_BY_USER_ID,REPORT_DEF_FORM.UPDATED_TIME,REPORT_DEF_FORM.CREATED_TIME from REPORT_DEF_FORM where    REPORT_DEFINITION_ID = ? ");
/*      */ 
/* 1015 */       int col = 1;
/* 1016 */       stmt.setInt(col++, selectReportDefinitionId);
/*      */ 
/* 1018 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1020 */       while (resultSet.next())
/*      */       {
/* 1022 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1025 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1028 */       if (currentList != null)
/*      */       {
/* 1031 */         ListIterator iter = items.listIterator();
/* 1032 */         ReportDefFormEVO currentEVO = null;
/* 1033 */         ReportDefFormEVO newEVO = null;
/* 1034 */         while (iter.hasNext())
/*      */         {
/* 1036 */           newEVO = (ReportDefFormEVO)iter.next();
/* 1037 */           Iterator iter2 = currentList.iterator();
/* 1038 */           while (iter2.hasNext())
/*      */           {
/* 1040 */             currentEVO = (ReportDefFormEVO)iter2.next();
/* 1041 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1043 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1049 */         Iterator iter2 = currentList.iterator();
/* 1050 */         while (iter2.hasNext())
/*      */         {
/* 1052 */           currentEVO = (ReportDefFormEVO)iter2.next();
/* 1053 */           if (currentEVO.insertPending()) {
/* 1054 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1058 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1062 */       throw handleSQLException("select REPORT_DEF_FORM.REPORT_DEFINITION_ID,REPORT_DEF_FORM.MODEL_ID,REPORT_DEF_FORM.FORM_ID,REPORT_DEF_FORM.DATA_TYPE_ID,REPORT_DEF_FORM.STRUCTURE_ID0,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID0,REPORT_DEF_FORM.STRUCTURE_ID1,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID1,REPORT_DEF_FORM.STRUCTURE_ID2,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID2,REPORT_DEF_FORM.STRUCTURE_ID3,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID3,REPORT_DEF_FORM.STRUCTURE_ID4,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID4,REPORT_DEF_FORM.STRUCTURE_ID5,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID5,REPORT_DEF_FORM.STRUCTURE_ID6,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID6,REPORT_DEF_FORM.STRUCTURE_ID7,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID7,REPORT_DEF_FORM.STRUCTURE_ID8,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID8,REPORT_DEF_FORM.STRUCTURE_ID9,REPORT_DEF_FORM.STRUCTURE_ELEMENT_ID9,REPORT_DEF_FORM.REPORT_DEPTH,REPORT_DEF_FORM.AUTO_EXPAND_DEPTH,REPORT_DEF_FORM.REPORT_TEMPLATE_ID,REPORT_DEF_FORM.UPDATED_BY_USER_ID,REPORT_DEF_FORM.UPDATED_TIME,REPORT_DEF_FORM.CREATED_TIME from REPORT_DEF_FORM where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1066 */       closeResultSet(resultSet);
/* 1067 */       closeStatement(stmt);
/* 1068 */       closeConnection();
/*      */ 
/* 1070 */       if (timer != null) {
/* 1071 */         timer.logDebug("getAll", " ReportDefinitionId=" + selectReportDefinitionId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1076 */     return items;
/*      */   }
/*      */ 
/*      */   public ReportDefFormEVO getDetails(ReportDefinitionCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1090 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1093 */     if (this.mDetails == null) {
/* 1094 */       doLoad(((ReportDefFormCK)paramCK).getReportDefFormPK());
/*      */     }
/* 1096 */     else if (!this.mDetails.getPK().equals(((ReportDefFormCK)paramCK).getReportDefFormPK())) {
/* 1097 */       doLoad(((ReportDefFormCK)paramCK).getReportDefFormPK());
/*      */     }
/*      */ 
/* 1100 */     ReportDefFormEVO details = new ReportDefFormEVO();
/* 1101 */     details = this.mDetails.deepClone();
/*      */ 
/* 1103 */     if (timer != null) {
/* 1104 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1106 */     return details;
/*      */   }
/*      */ 
/*      */   public ReportDefFormEVO getDetails(ReportDefinitionCK paramCK, ReportDefFormEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1112 */     ReportDefFormEVO savedEVO = this.mDetails;
/* 1113 */     this.mDetails = paramEVO;
/* 1114 */     ReportDefFormEVO newEVO = getDetails(paramCK, dependants);
/* 1115 */     this.mDetails = savedEVO;
/* 1116 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public ReportDefFormEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1122 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1126 */     ReportDefFormEVO details = this.mDetails.deepClone();
/*      */ 
/* 1128 */     if (timer != null) {
/* 1129 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1131 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1136 */     return "ReportDefForm";
/*      */   }
/*      */ 
/*      */   public ReportDefFormRefImpl getRef(ReportDefFormPK paramReportDefFormPK)
/*      */   {
/* 1141 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1142 */     PreparedStatement stmt = null;
/* 1143 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1146 */       stmt = getConnection().prepareStatement("select 0,REPORT_DEFINITION.REPORT_DEFINITION_ID from REPORT_DEF_FORM,REPORT_DEFINITION where 1=1 and REPORT_DEF_FORM.REPORT_DEFINITION_ID = ? and REPORT_DEF_FORM.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID");
/* 1147 */       int col = 1;
/* 1148 */       stmt.setInt(col++, paramReportDefFormPK.getReportDefinitionId());
/*      */ 
/* 1150 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1152 */       if (!resultSet.next()) {
/* 1153 */         throw new RuntimeException(getEntityName() + " getRef " + paramReportDefFormPK + " not found");
/*      */       }
/* 1155 */       col = 2;
/* 1156 */       ReportDefinitionPK newReportDefinitionPK = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/* 1160 */       String textReportDefForm = "";
/* 1161 */       ReportDefFormCK ckReportDefForm = new ReportDefFormCK(newReportDefinitionPK, paramReportDefFormPK);
/*      */ 
/* 1166 */       ReportDefFormRefImpl localReportDefFormRefImpl = new ReportDefFormRefImpl(ckReportDefForm, textReportDefForm);
/*      */       return localReportDefFormRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1171 */       throw handleSQLException(paramReportDefFormPK, "select 0,REPORT_DEFINITION.REPORT_DEFINITION_ID from REPORT_DEF_FORM,REPORT_DEFINITION where 1=1 and REPORT_DEF_FORM.REPORT_DEFINITION_ID = ? and REPORT_DEF_FORM.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1175 */       closeResultSet(resultSet);
/* 1176 */       closeStatement(stmt);
/* 1177 */       closeConnection();
/*      */ 
/* 1179 */       if (timer != null)
/* 1180 */         timer.logDebug("getRef", paramReportDefFormPK); 
/* 1180 */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.definition.ReportDefFormDAO
 * JD-Core Version:    0.6.0
 */