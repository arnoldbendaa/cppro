/*      */ package com.cedar.cp.ejb.impl.report.definition;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.report.definition.AllReportDefSummaryCalcByCCDeploymentIdELO;
/*      */ import com.cedar.cp.dto.report.definition.AllReportDefSummaryCalcByModelIdELO;
/*      */ import com.cedar.cp.dto.report.definition.AllReportDefSummaryCalcByReportTemplateIdELO;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcCK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcPK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcRefImpl;
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
/*      */ public class ReportDefSummaryCalcDAO extends AbstractDAO
/*      */ {
/*   32 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID,REPORT_DEF_SUMMARY_CALC.MODEL_ID,REPORT_DEF_SUMMARY_CALC.HIERARCHY_DEPTH,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ID,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ELEMENT_ID,REPORT_DEF_SUMMARY_CALC.CC_DEPLOYMENT_ID,REPORT_DEF_SUMMARY_CALC.REPORT_TEMPLATE_ID,REPORT_DEF_SUMMARY_CALC.COLUMN_MAP,REPORT_DEF_SUMMARY_CALC.UPDATED_BY_USER_ID,REPORT_DEF_SUMMARY_CALC.UPDATED_TIME,REPORT_DEF_SUMMARY_CALC.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from REPORT_DEF_SUMMARY_CALC where    REPORT_DEFINITION_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into REPORT_DEF_SUMMARY_CALC ( REPORT_DEFINITION_ID,MODEL_ID,HIERARCHY_DEPTH,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,CC_DEPLOYMENT_ID,REPORT_TEMPLATE_ID,COLUMN_MAP,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update REPORT_DEF_SUMMARY_CALC set MODEL_ID = ?,HIERARCHY_DEPTH = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,CC_DEPLOYMENT_ID = ?,REPORT_TEMPLATE_ID = ?,COLUMN_MAP = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? ";
/*  339 */   protected static String SQL_ALL_REPORT_DEF_SUMMARY_CALC_BY_C_C_DEPLOYMENT_ID = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID      ,REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID      ,REPORT_DEF_SUMMARY_CALC.MODEL_ID      ,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ELEMENT_ID      ,REPORT_DEF_SUMMARY_CALC.CC_DEPLOYMENT_ID      ,REPORT_DEF_SUMMARY_CALC.REPORT_TEMPLATE_ID      ,REPORT_DEF_SUMMARY_CALC.COLUMN_MAP from REPORT_DEF_SUMMARY_CALC    ,REPORT_DEFINITION where 1=1   and REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID  and  CC_DEPLOYMENT_ID = ?";
/*      */ 
/*  463 */   protected static String SQL_ALL_REPORT_DEF_SUMMARY_CALC_BY_REPORT_TEMPLATE_ID = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID      ,REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID      ,REPORT_DEF_SUMMARY_CALC.MODEL_ID      ,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ELEMENT_ID      ,REPORT_DEF_SUMMARY_CALC.CC_DEPLOYMENT_ID      ,REPORT_DEF_SUMMARY_CALC.REPORT_TEMPLATE_ID      ,REPORT_DEF_SUMMARY_CALC.COLUMN_MAP from REPORT_DEF_SUMMARY_CALC    ,REPORT_DEFINITION where 1=1   and REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID  and  REPORT_TEMPLATE_ID = ?";
/*      */ 
/*  587 */   protected static String SQL_ALL_REPORT_DEF_SUMMARY_CALC_BY_MODEL_ID = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID      ,REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID      ,REPORT_DEF_SUMMARY_CALC.MODEL_ID      ,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ELEMENT_ID      ,REPORT_DEF_SUMMARY_CALC.CC_DEPLOYMENT_ID      ,REPORT_DEF_SUMMARY_CALC.REPORT_TEMPLATE_ID      ,REPORT_DEF_SUMMARY_CALC.COLUMN_MAP from REPORT_DEF_SUMMARY_CALC    ,REPORT_DEFINITION where 1=1   and REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID  and  MODEL_ID = ?";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from REPORT_DEF_SUMMARY_CALC where    REPORT_DEFINITION_ID = ? ";
/*      */   public static final String SQL_BULK_GET_ALL = " from REPORT_DEF_SUMMARY_CALC where 1=1 and REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID = ? order by  REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID";
/*      */   protected static final String SQL_GET_ALL = " from REPORT_DEF_SUMMARY_CALC where    REPORT_DEFINITION_ID = ? ";
/*      */   protected ReportDefSummaryCalcEVO mDetails;
/*      */ 
/*      */   public ReportDefSummaryCalcDAO(Connection connection)
/*      */   {
/*   39 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ReportDefSummaryCalcDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ReportDefSummaryCalcDAO(DataSource ds)
/*      */   {
/*   55 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ReportDefSummaryCalcPK getPK()
/*      */   {
/*   63 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ReportDefSummaryCalcEVO details)
/*      */   {
/*   72 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private ReportDefSummaryCalcEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   94 */     int col = 1;
/*   95 */     ReportDefSummaryCalcEVO evo = new ReportDefSummaryCalcEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++));
/*      */ 
/*  106 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  107 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  108 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  109 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ReportDefSummaryCalcEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  114 */     int col = startCol_;
/*  115 */     stmt_.setInt(col++, evo_.getReportDefinitionId());
/*  116 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ReportDefSummaryCalcEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  121 */     int col = startCol_;
/*  122 */     stmt_.setInt(col++, evo_.getModelId());
/*  123 */     stmt_.setInt(col++, evo_.getHierarchyDepth());
/*  124 */     stmt_.setInt(col++, evo_.getStructureId());
/*  125 */     stmt_.setInt(col++, evo_.getStructureElementId());
/*  126 */     stmt_.setInt(col++, evo_.getCcDeploymentId());
/*  127 */     stmt_.setInt(col++, evo_.getReportTemplateId());
/*  128 */     stmt_.setString(col++, evo_.getColumnMap());
/*  129 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  130 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  131 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  132 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ReportDefSummaryCalcPK pk)
/*      */     throws ValidationException
/*      */   {
/*  148 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  150 */     PreparedStatement stmt = null;
/*  151 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  155 */       stmt = getConnection().prepareStatement("select REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID,REPORT_DEF_SUMMARY_CALC.MODEL_ID,REPORT_DEF_SUMMARY_CALC.HIERARCHY_DEPTH,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ID,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ELEMENT_ID,REPORT_DEF_SUMMARY_CALC.CC_DEPLOYMENT_ID,REPORT_DEF_SUMMARY_CALC.REPORT_TEMPLATE_ID,REPORT_DEF_SUMMARY_CALC.COLUMN_MAP,REPORT_DEF_SUMMARY_CALC.UPDATED_BY_USER_ID,REPORT_DEF_SUMMARY_CALC.UPDATED_TIME,REPORT_DEF_SUMMARY_CALC.CREATED_TIME from REPORT_DEF_SUMMARY_CALC where    REPORT_DEFINITION_ID = ? ");
/*      */ 
/*  158 */       int col = 1;
/*  159 */       stmt.setInt(col++, pk.getReportDefinitionId());
/*      */ 
/*  161 */       resultSet = stmt.executeQuery();
/*      */ 
/*  163 */       if (!resultSet.next()) {
/*  164 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  167 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  168 */       if (this.mDetails.isModified())
/*  169 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  173 */       throw handleSQLException(pk, "select REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID,REPORT_DEF_SUMMARY_CALC.MODEL_ID,REPORT_DEF_SUMMARY_CALC.HIERARCHY_DEPTH,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ID,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ELEMENT_ID,REPORT_DEF_SUMMARY_CALC.CC_DEPLOYMENT_ID,REPORT_DEF_SUMMARY_CALC.REPORT_TEMPLATE_ID,REPORT_DEF_SUMMARY_CALC.COLUMN_MAP,REPORT_DEF_SUMMARY_CALC.UPDATED_BY_USER_ID,REPORT_DEF_SUMMARY_CALC.UPDATED_TIME,REPORT_DEF_SUMMARY_CALC.CREATED_TIME from REPORT_DEF_SUMMARY_CALC where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  177 */       closeResultSet(resultSet);
/*  178 */       closeStatement(stmt);
/*  179 */       closeConnection();
/*      */ 
/*  181 */       if (timer != null)
/*  182 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  221 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  222 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  227 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  228 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  229 */       stmt = getConnection().prepareStatement("insert into REPORT_DEF_SUMMARY_CALC ( REPORT_DEFINITION_ID,MODEL_ID,HIERARCHY_DEPTH,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,CC_DEPLOYMENT_ID,REPORT_TEMPLATE_ID,COLUMN_MAP,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  232 */       int col = 1;
/*  233 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  234 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  237 */       int resultCount = stmt.executeUpdate();
/*  238 */       if (resultCount != 1)
/*      */       {
/*  240 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  243 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  247 */       throw handleSQLException(this.mDetails.getPK(), "insert into REPORT_DEF_SUMMARY_CALC ( REPORT_DEFINITION_ID,MODEL_ID,HIERARCHY_DEPTH,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,CC_DEPLOYMENT_ID,REPORT_TEMPLATE_ID,COLUMN_MAP,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  251 */       closeStatement(stmt);
/*  252 */       closeConnection();
/*      */ 
/*  254 */       if (timer != null)
/*  255 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  284 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  288 */     PreparedStatement stmt = null;
/*      */ 
/*  290 */     boolean mainChanged = this.mDetails.isModified();
/*  291 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  294 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  297 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  298 */         stmt = getConnection().prepareStatement("update REPORT_DEF_SUMMARY_CALC set MODEL_ID = ?,HIERARCHY_DEPTH = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,CC_DEPLOYMENT_ID = ?,REPORT_TEMPLATE_ID = ?,COLUMN_MAP = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? ");
/*      */ 
/*  301 */         int col = 1;
/*  302 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  303 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  306 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  309 */         if (resultCount != 1) {
/*  310 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  313 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  322 */       throw handleSQLException(getPK(), "update REPORT_DEF_SUMMARY_CALC set MODEL_ID = ?,HIERARCHY_DEPTH = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,CC_DEPLOYMENT_ID = ?,REPORT_TEMPLATE_ID = ?,COLUMN_MAP = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  326 */       closeStatement(stmt);
/*  327 */       closeConnection();
/*      */ 
/*  329 */       if ((timer != null) && (
/*  330 */         (mainChanged) || (dependantChanged)))
/*  331 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllReportDefSummaryCalcByCCDeploymentIdELO getAllReportDefSummaryCalcByCCDeploymentId(int param1)
/*      */   {
/*  374 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  375 */     PreparedStatement stmt = null;
/*  376 */     ResultSet resultSet = null;
/*  377 */     AllReportDefSummaryCalcByCCDeploymentIdELO results = new AllReportDefSummaryCalcByCCDeploymentIdELO();
/*      */     try
/*      */     {
/*  380 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_DEF_SUMMARY_CALC_BY_C_C_DEPLOYMENT_ID);
/*  381 */       int col = 1;
/*  382 */       stmt.setInt(col++, param1);
/*  383 */       resultSet = stmt.executeQuery();
/*  384 */       while (resultSet.next())
/*      */       {
/*  386 */         col = 2;
/*      */ 
/*  389 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  392 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  395 */         ReportDefSummaryCalcPK pkReportDefSummaryCalc = new ReportDefSummaryCalcPK(resultSet.getInt(col++));
/*      */ 
/*  398 */         String textReportDefSummaryCalc = "";
/*      */ 
/*  403 */         ReportDefSummaryCalcCK ckReportDefSummaryCalc = new ReportDefSummaryCalcCK(pkReportDefinition, pkReportDefSummaryCalc);
/*      */ 
/*  409 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  415 */         ReportDefSummaryCalcRefImpl erReportDefSummaryCalc = new ReportDefSummaryCalcRefImpl(ckReportDefSummaryCalc, textReportDefSummaryCalc);
/*      */ 
/*  420 */         int col1 = resultSet.getInt(col++);
/*  421 */         int col2 = resultSet.getInt(col++);
/*  422 */         int col3 = resultSet.getInt(col++);
/*  423 */         int col4 = resultSet.getInt(col++);
/*  424 */         int col5 = resultSet.getInt(col++);
/*  425 */         String col6 = resultSet.getString(col++);
/*      */ 
/*  428 */         results.add(erReportDefSummaryCalc, erReportDefinition, col1, col2, col3, col4, col5, col6);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  442 */       throw handleSQLException(SQL_ALL_REPORT_DEF_SUMMARY_CALC_BY_C_C_DEPLOYMENT_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  446 */       closeResultSet(resultSet);
/*  447 */       closeStatement(stmt);
/*  448 */       closeConnection();
/*      */     }
/*      */ 
/*  451 */     if (timer != null) {
/*  452 */       timer.logDebug("getAllReportDefSummaryCalcByCCDeploymentId", " CcDeploymentId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  457 */     return results;
/*      */   }
/*      */ 
/*      */   public AllReportDefSummaryCalcByReportTemplateIdELO getAllReportDefSummaryCalcByReportTemplateId(int param1)
/*      */   {
/*  498 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  499 */     PreparedStatement stmt = null;
/*  500 */     ResultSet resultSet = null;
/*  501 */     AllReportDefSummaryCalcByReportTemplateIdELO results = new AllReportDefSummaryCalcByReportTemplateIdELO();
/*      */     try
/*      */     {
/*  504 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_DEF_SUMMARY_CALC_BY_REPORT_TEMPLATE_ID);
/*  505 */       int col = 1;
/*  506 */       stmt.setInt(col++, param1);
/*  507 */       resultSet = stmt.executeQuery();
/*  508 */       while (resultSet.next())
/*      */       {
/*  510 */         col = 2;
/*      */ 
/*  513 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  516 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  519 */         ReportDefSummaryCalcPK pkReportDefSummaryCalc = new ReportDefSummaryCalcPK(resultSet.getInt(col++));
/*      */ 
/*  522 */         String textReportDefSummaryCalc = "";
/*      */ 
/*  527 */         ReportDefSummaryCalcCK ckReportDefSummaryCalc = new ReportDefSummaryCalcCK(pkReportDefinition, pkReportDefSummaryCalc);
/*      */ 
/*  533 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  539 */         ReportDefSummaryCalcRefImpl erReportDefSummaryCalc = new ReportDefSummaryCalcRefImpl(ckReportDefSummaryCalc, textReportDefSummaryCalc);
/*      */ 
/*  544 */         int col1 = resultSet.getInt(col++);
/*  545 */         int col2 = resultSet.getInt(col++);
/*  546 */         int col3 = resultSet.getInt(col++);
/*  547 */         int col4 = resultSet.getInt(col++);
/*  548 */         int col5 = resultSet.getInt(col++);
/*  549 */         String col6 = resultSet.getString(col++);
/*      */ 
/*  552 */         results.add(erReportDefSummaryCalc, erReportDefinition, col1, col2, col3, col4, col5, col6);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  566 */       throw handleSQLException(SQL_ALL_REPORT_DEF_SUMMARY_CALC_BY_REPORT_TEMPLATE_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  570 */       closeResultSet(resultSet);
/*  571 */       closeStatement(stmt);
/*  572 */       closeConnection();
/*      */     }
/*      */ 
/*  575 */     if (timer != null) {
/*  576 */       timer.logDebug("getAllReportDefSummaryCalcByReportTemplateId", " ReportTemplateId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  581 */     return results;
/*      */   }
/*      */ 
/*      */   public AllReportDefSummaryCalcByModelIdELO getAllReportDefSummaryCalcByModelId(int param1)
/*      */   {
/*  622 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  623 */     PreparedStatement stmt = null;
/*  624 */     ResultSet resultSet = null;
/*  625 */     AllReportDefSummaryCalcByModelIdELO results = new AllReportDefSummaryCalcByModelIdELO();
/*      */     try
/*      */     {
/*  628 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_DEF_SUMMARY_CALC_BY_MODEL_ID);
/*  629 */       int col = 1;
/*  630 */       stmt.setInt(col++, param1);
/*  631 */       resultSet = stmt.executeQuery();
/*  632 */       while (resultSet.next())
/*      */       {
/*  634 */         col = 2;
/*      */ 
/*  637 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  640 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  643 */         ReportDefSummaryCalcPK pkReportDefSummaryCalc = new ReportDefSummaryCalcPK(resultSet.getInt(col++));
/*      */ 
/*  646 */         String textReportDefSummaryCalc = "";
/*      */ 
/*  651 */         ReportDefSummaryCalcCK ckReportDefSummaryCalc = new ReportDefSummaryCalcCK(pkReportDefinition, pkReportDefSummaryCalc);
/*      */ 
/*  657 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  663 */         ReportDefSummaryCalcRefImpl erReportDefSummaryCalc = new ReportDefSummaryCalcRefImpl(ckReportDefSummaryCalc, textReportDefSummaryCalc);
/*      */ 
/*  668 */         int col1 = resultSet.getInt(col++);
/*  669 */         int col2 = resultSet.getInt(col++);
/*  670 */         int col3 = resultSet.getInt(col++);
/*  671 */         int col4 = resultSet.getInt(col++);
/*  672 */         int col5 = resultSet.getInt(col++);
/*  673 */         String col6 = resultSet.getString(col++);
/*      */ 
/*  676 */         results.add(erReportDefSummaryCalc, erReportDefinition, col1, col2, col3, col4, col5, col6);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  690 */       throw handleSQLException(SQL_ALL_REPORT_DEF_SUMMARY_CALC_BY_MODEL_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  694 */       closeResultSet(resultSet);
/*  695 */       closeStatement(stmt);
/*  696 */       closeConnection();
/*      */     }
/*      */ 
/*  699 */     if (timer != null) {
/*  700 */       timer.logDebug("getAllReportDefSummaryCalcByModelId", " ModelId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  705 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  722 */     if (items == null) {
/*  723 */       return false;
/*      */     }
/*  725 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  726 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  728 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  733 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  734 */       while (iter2.hasNext())
/*      */       {
/*  736 */         this.mDetails = ((ReportDefSummaryCalcEVO)iter2.next());
/*      */ 
/*  739 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  741 */         somethingChanged = true;
/*      */ 
/*  744 */         if (deleteStmt == null) {
/*  745 */           deleteStmt = getConnection().prepareStatement("delete from REPORT_DEF_SUMMARY_CALC where    REPORT_DEFINITION_ID = ? ");
/*      */         }
/*      */ 
/*  748 */         int col = 1;
/*  749 */         deleteStmt.setInt(col++, this.mDetails.getReportDefinitionId());
/*      */ 
/*  751 */         if (this._log.isDebugEnabled()) {
/*  752 */           this._log.debug("update", "ReportDefSummaryCalc deleting ReportDefinitionId=" + this.mDetails.getReportDefinitionId());
/*      */         }
/*      */ 
/*  757 */         deleteStmt.addBatch();
/*      */ 
/*  760 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  765 */       if (deleteStmt != null)
/*      */       {
/*  767 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  769 */         deleteStmt.executeBatch();
/*      */ 
/*  771 */         if (timer2 != null) {
/*  772 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  776 */       Iterator iter1 = items.values().iterator();
/*  777 */       while (iter1.hasNext())
/*      */       {
/*  779 */         this.mDetails = ((ReportDefSummaryCalcEVO)iter1.next());
/*      */ 
/*  781 */         if (this.mDetails.insertPending())
/*      */         {
/*  783 */           somethingChanged = true;
/*  784 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  787 */         if (!this.mDetails.isModified())
/*      */           continue;
/*  789 */         somethingChanged = true;
/*  790 */         doStore();
/*      */       }
/*      */ 
/*  801 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  805 */       throw handleSQLException("delete from REPORT_DEF_SUMMARY_CALC where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  809 */       if (deleteStmt != null)
/*      */       {
/*  811 */         closeStatement(deleteStmt);
/*  812 */         closeConnection();
/*      */       }
/*      */ 
/*  815 */       this.mDetails = null;
/*      */ 
/*  817 */       if ((somethingChanged) && 
/*  818 */         (timer != null))
/*  819 */         timer.logDebug("update", "collection"); 
/*  819 */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ReportDefinitionPK entityPK, ReportDefinitionEVO owningEVO, String dependants)
/*      */   {
/*  838 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  840 */     PreparedStatement stmt = null;
/*  841 */     ResultSet resultSet = null;
/*      */ 
/*  843 */     int itemCount = 0;
/*      */ 
/*  845 */     Collection theseItems = new ArrayList();
/*  846 */     owningEVO.setSummaryCalcReport(theseItems);
/*  847 */     owningEVO.setSummaryCalcReportAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  851 */       stmt = getConnection().prepareStatement("select REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID,REPORT_DEF_SUMMARY_CALC.MODEL_ID,REPORT_DEF_SUMMARY_CALC.HIERARCHY_DEPTH,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ID,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ELEMENT_ID,REPORT_DEF_SUMMARY_CALC.CC_DEPLOYMENT_ID,REPORT_DEF_SUMMARY_CALC.REPORT_TEMPLATE_ID,REPORT_DEF_SUMMARY_CALC.COLUMN_MAP,REPORT_DEF_SUMMARY_CALC.UPDATED_BY_USER_ID,REPORT_DEF_SUMMARY_CALC.UPDATED_TIME,REPORT_DEF_SUMMARY_CALC.CREATED_TIME from REPORT_DEF_SUMMARY_CALC where 1=1 and REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID = ? order by  REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID");
/*      */ 
/*  853 */       int col = 1;
/*  854 */       stmt.setInt(col++, entityPK.getReportDefinitionId());
/*      */ 
/*  856 */       resultSet = stmt.executeQuery();
/*      */ 
/*  859 */       while (resultSet.next())
/*      */       {
/*  861 */         itemCount++;
/*  862 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  864 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  867 */       if (timer != null) {
/*  868 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  873 */       throw handleSQLException("select REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID,REPORT_DEF_SUMMARY_CALC.MODEL_ID,REPORT_DEF_SUMMARY_CALC.HIERARCHY_DEPTH,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ID,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ELEMENT_ID,REPORT_DEF_SUMMARY_CALC.CC_DEPLOYMENT_ID,REPORT_DEF_SUMMARY_CALC.REPORT_TEMPLATE_ID,REPORT_DEF_SUMMARY_CALC.COLUMN_MAP,REPORT_DEF_SUMMARY_CALC.UPDATED_BY_USER_ID,REPORT_DEF_SUMMARY_CALC.UPDATED_TIME,REPORT_DEF_SUMMARY_CALC.CREATED_TIME from REPORT_DEF_SUMMARY_CALC where 1=1 and REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID = ? order by  REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  877 */       closeResultSet(resultSet);
/*  878 */       closeStatement(stmt);
/*  879 */       closeConnection();
/*      */ 
/*  881 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectReportDefinitionId, String dependants, Collection currentList)
/*      */   {
/*  906 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  907 */     PreparedStatement stmt = null;
/*  908 */     ResultSet resultSet = null;
/*      */ 
/*  910 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  914 */       stmt = getConnection().prepareStatement("select REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID,REPORT_DEF_SUMMARY_CALC.MODEL_ID,REPORT_DEF_SUMMARY_CALC.HIERARCHY_DEPTH,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ID,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ELEMENT_ID,REPORT_DEF_SUMMARY_CALC.CC_DEPLOYMENT_ID,REPORT_DEF_SUMMARY_CALC.REPORT_TEMPLATE_ID,REPORT_DEF_SUMMARY_CALC.COLUMN_MAP,REPORT_DEF_SUMMARY_CALC.UPDATED_BY_USER_ID,REPORT_DEF_SUMMARY_CALC.UPDATED_TIME,REPORT_DEF_SUMMARY_CALC.CREATED_TIME from REPORT_DEF_SUMMARY_CALC where    REPORT_DEFINITION_ID = ? ");
/*      */ 
/*  916 */       int col = 1;
/*  917 */       stmt.setInt(col++, selectReportDefinitionId);
/*      */ 
/*  919 */       resultSet = stmt.executeQuery();
/*      */ 
/*  921 */       while (resultSet.next())
/*      */       {
/*  923 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  926 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  929 */       if (currentList != null)
/*      */       {
/*  932 */         ListIterator iter = items.listIterator();
/*  933 */         ReportDefSummaryCalcEVO currentEVO = null;
/*  934 */         ReportDefSummaryCalcEVO newEVO = null;
/*  935 */         while (iter.hasNext())
/*      */         {
/*  937 */           newEVO = (ReportDefSummaryCalcEVO)iter.next();
/*  938 */           Iterator iter2 = currentList.iterator();
/*  939 */           while (iter2.hasNext())
/*      */           {
/*  941 */             currentEVO = (ReportDefSummaryCalcEVO)iter2.next();
/*  942 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  944 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  950 */         Iterator iter2 = currentList.iterator();
/*  951 */         while (iter2.hasNext())
/*      */         {
/*  953 */           currentEVO = (ReportDefSummaryCalcEVO)iter2.next();
/*  954 */           if (currentEVO.insertPending()) {
/*  955 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  959 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  963 */       throw handleSQLException("select REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID,REPORT_DEF_SUMMARY_CALC.MODEL_ID,REPORT_DEF_SUMMARY_CALC.HIERARCHY_DEPTH,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ID,REPORT_DEF_SUMMARY_CALC.STRUCTURE_ELEMENT_ID,REPORT_DEF_SUMMARY_CALC.CC_DEPLOYMENT_ID,REPORT_DEF_SUMMARY_CALC.REPORT_TEMPLATE_ID,REPORT_DEF_SUMMARY_CALC.COLUMN_MAP,REPORT_DEF_SUMMARY_CALC.UPDATED_BY_USER_ID,REPORT_DEF_SUMMARY_CALC.UPDATED_TIME,REPORT_DEF_SUMMARY_CALC.CREATED_TIME from REPORT_DEF_SUMMARY_CALC where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  967 */       closeResultSet(resultSet);
/*  968 */       closeStatement(stmt);
/*  969 */       closeConnection();
/*      */ 
/*  971 */       if (timer != null) {
/*  972 */         timer.logDebug("getAll", " ReportDefinitionId=" + selectReportDefinitionId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  977 */     return items;
/*      */   }
/*      */ 
/*      */   public ReportDefSummaryCalcEVO getDetails(ReportDefinitionCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  991 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  994 */     if (this.mDetails == null) {
/*  995 */       doLoad(((ReportDefSummaryCalcCK)paramCK).getReportDefSummaryCalcPK());
/*      */     }
/*  997 */     else if (!this.mDetails.getPK().equals(((ReportDefSummaryCalcCK)paramCK).getReportDefSummaryCalcPK())) {
/*  998 */       doLoad(((ReportDefSummaryCalcCK)paramCK).getReportDefSummaryCalcPK());
/*      */     }
/*      */ 
/* 1001 */     ReportDefSummaryCalcEVO details = new ReportDefSummaryCalcEVO();
/* 1002 */     details = this.mDetails.deepClone();
/*      */ 
/* 1004 */     if (timer != null) {
/* 1005 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1007 */     return details;
/*      */   }
/*      */ 
/*      */   public ReportDefSummaryCalcEVO getDetails(ReportDefinitionCK paramCK, ReportDefSummaryCalcEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1013 */     ReportDefSummaryCalcEVO savedEVO = this.mDetails;
/* 1014 */     this.mDetails = paramEVO;
/* 1015 */     ReportDefSummaryCalcEVO newEVO = getDetails(paramCK, dependants);
/* 1016 */     this.mDetails = savedEVO;
/* 1017 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public ReportDefSummaryCalcEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1023 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1027 */     ReportDefSummaryCalcEVO details = this.mDetails.deepClone();
/*      */ 
/* 1029 */     if (timer != null) {
/* 1030 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1032 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1037 */     return "ReportDefSummaryCalc";
/*      */   }
/*      */ 
/*      */   public ReportDefSummaryCalcRefImpl getRef(ReportDefSummaryCalcPK paramReportDefSummaryCalcPK)
/*      */   {
/* 1042 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1043 */     PreparedStatement stmt = null;
/* 1044 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1047 */       stmt = getConnection().prepareStatement("select 0,REPORT_DEFINITION.REPORT_DEFINITION_ID from REPORT_DEF_SUMMARY_CALC,REPORT_DEFINITION where 1=1 and REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID = ? and REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID");
/* 1048 */       int col = 1;
/* 1049 */       stmt.setInt(col++, paramReportDefSummaryCalcPK.getReportDefinitionId());
/*      */ 
/* 1051 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1053 */       if (!resultSet.next()) {
/* 1054 */         throw new RuntimeException(getEntityName() + " getRef " + paramReportDefSummaryCalcPK + " not found");
/*      */       }
/* 1056 */       col = 2;
/* 1057 */       ReportDefinitionPK newReportDefinitionPK = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/* 1061 */       String textReportDefSummaryCalc = "";
/* 1062 */       ReportDefSummaryCalcCK ckReportDefSummaryCalc = new ReportDefSummaryCalcCK(newReportDefinitionPK, paramReportDefSummaryCalcPK);
/*      */ 
/* 1067 */       ReportDefSummaryCalcRefImpl localReportDefSummaryCalcRefImpl = new ReportDefSummaryCalcRefImpl(ckReportDefSummaryCalc, textReportDefSummaryCalc);
/*      */       return localReportDefSummaryCalcRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1072 */       throw handleSQLException(paramReportDefSummaryCalcPK, "select 0,REPORT_DEFINITION.REPORT_DEFINITION_ID from REPORT_DEF_SUMMARY_CALC,REPORT_DEFINITION where 1=1 and REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID = ? and REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1076 */       closeResultSet(resultSet);
/* 1077 */       closeStatement(stmt);
/* 1078 */       closeConnection();
/*      */ 
/* 1080 */       if (timer != null)
/* 1081 */         timer.logDebug("getRef", paramReportDefSummaryCalcPK); 
/* 1081 */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.definition.ReportDefSummaryCalcDAO
 * JD-Core Version:    0.6.0
 */