/*      */ package com.cedar.cp.ejb.impl.report.definition;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.report.definition.AllReportDefCalcByCCDeploymentIdELO;
/*      */ import com.cedar.cp.dto.report.definition.AllReportDefCalcByModelIdELO;
/*      */ import com.cedar.cp.dto.report.definition.AllReportDefCalcByReportTemplateIdELO;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefCalculatorCK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefCalculatorPK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefCalculatorRefImpl;
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
/*      */ public class ReportDefCalculatorDAO extends AbstractDAO
/*      */ {
/*   32 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID,REPORT_DEF_CALCULATOR.MODEL_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ELEMENT_ID,REPORT_DEF_CALCULATOR.CC_DEPLOYMENT_ID,REPORT_DEF_CALCULATOR.REPORT_TEMPLATE_ID,REPORT_DEF_CALCULATOR.UPDATED_BY_USER_ID,REPORT_DEF_CALCULATOR.UPDATED_TIME,REPORT_DEF_CALCULATOR.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from REPORT_DEF_CALCULATOR where    REPORT_DEFINITION_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into REPORT_DEF_CALCULATOR ( REPORT_DEFINITION_ID,MODEL_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,CC_DEPLOYMENT_ID,REPORT_TEMPLATE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update REPORT_DEF_CALCULATOR set MODEL_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,CC_DEPLOYMENT_ID = ?,REPORT_TEMPLATE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? ";
/*  327 */   protected static String SQL_ALL_REPORT_DEF_CALC_BY_C_C_DEPLOYMENT_ID = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID      ,REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID      ,REPORT_DEF_CALCULATOR.MODEL_ID      ,REPORT_DEF_CALCULATOR.STRUCTURE_ELEMENT_ID      ,REPORT_DEF_CALCULATOR.CC_DEPLOYMENT_ID      ,REPORT_DEF_CALCULATOR.REPORT_TEMPLATE_ID from REPORT_DEF_CALCULATOR    ,REPORT_DEFINITION where 1=1   and REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID  and  CC_DEPLOYMENT_ID = ?";
/*      */ 
/*  448 */   protected static String SQL_ALL_REPORT_DEF_CALC_BY_REPORT_TEMPLATE_ID = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID      ,REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID      ,REPORT_DEF_CALCULATOR.MODEL_ID      ,REPORT_DEF_CALCULATOR.STRUCTURE_ELEMENT_ID      ,REPORT_DEF_CALCULATOR.CC_DEPLOYMENT_ID      ,REPORT_DEF_CALCULATOR.REPORT_TEMPLATE_ID from REPORT_DEF_CALCULATOR    ,REPORT_DEFINITION where 1=1   and REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID  and  REPORT_TEMPLATE_ID = ?";
/*      */ 
/*  569 */   protected static String SQL_ALL_REPORT_DEF_CALC_BY_MODEL_ID = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID      ,REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID      ,REPORT_DEF_CALCULATOR.MODEL_ID      ,REPORT_DEF_CALCULATOR.STRUCTURE_ELEMENT_ID      ,REPORT_DEF_CALCULATOR.CC_DEPLOYMENT_ID      ,REPORT_DEF_CALCULATOR.REPORT_TEMPLATE_ID from REPORT_DEF_CALCULATOR    ,REPORT_DEFINITION where 1=1   and REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID  and  MODEL_ID = ?";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from REPORT_DEF_CALCULATOR where    REPORT_DEFINITION_ID = ? ";
/*      */   public static final String SQL_BULK_GET_ALL = " from REPORT_DEF_CALCULATOR where 1=1 and REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID = ? order by  REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID";
/*      */   protected static final String SQL_GET_ALL = " from REPORT_DEF_CALCULATOR where    REPORT_DEFINITION_ID = ? ";
/*      */   protected ReportDefCalculatorEVO mDetails;
/*      */ 
/*      */   public ReportDefCalculatorDAO(Connection connection)
/*      */   {
/*   39 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ReportDefCalculatorDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ReportDefCalculatorDAO(DataSource ds)
/*      */   {
/*   55 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ReportDefCalculatorPK getPK()
/*      */   {
/*   63 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ReportDefCalculatorEVO details)
/*      */   {
/*   72 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private ReportDefCalculatorEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   92 */     int col = 1;
/*   93 */     ReportDefCalculatorEVO evo = new ReportDefCalculatorEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*      */ 
/*  102 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  103 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  104 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  105 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ReportDefCalculatorEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  110 */     int col = startCol_;
/*  111 */     stmt_.setInt(col++, evo_.getReportDefinitionId());
/*  112 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ReportDefCalculatorEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  117 */     int col = startCol_;
/*  118 */     stmt_.setInt(col++, evo_.getModelId());
/*  119 */     stmt_.setInt(col++, evo_.getStructureId());
/*  120 */     stmt_.setInt(col++, evo_.getStructureElementId());
/*  121 */     stmt_.setInt(col++, evo_.getCcDeploymentId());
/*  122 */     stmt_.setInt(col++, evo_.getReportTemplateId());
/*  123 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  124 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  125 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  126 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ReportDefCalculatorPK pk)
/*      */     throws ValidationException
/*      */   {
/*  142 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  144 */     PreparedStatement stmt = null;
/*  145 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  149 */       stmt = getConnection().prepareStatement("select REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID,REPORT_DEF_CALCULATOR.MODEL_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ELEMENT_ID,REPORT_DEF_CALCULATOR.CC_DEPLOYMENT_ID,REPORT_DEF_CALCULATOR.REPORT_TEMPLATE_ID,REPORT_DEF_CALCULATOR.UPDATED_BY_USER_ID,REPORT_DEF_CALCULATOR.UPDATED_TIME,REPORT_DEF_CALCULATOR.CREATED_TIME from REPORT_DEF_CALCULATOR where    REPORT_DEFINITION_ID = ? ");
/*      */ 
/*  152 */       int col = 1;
/*  153 */       stmt.setInt(col++, pk.getReportDefinitionId());
/*      */ 
/*  155 */       resultSet = stmt.executeQuery();
/*      */ 
/*  157 */       if (!resultSet.next()) {
/*  158 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  161 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  162 */       if (this.mDetails.isModified())
/*  163 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  167 */       throw handleSQLException(pk, "select REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID,REPORT_DEF_CALCULATOR.MODEL_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ELEMENT_ID,REPORT_DEF_CALCULATOR.CC_DEPLOYMENT_ID,REPORT_DEF_CALCULATOR.REPORT_TEMPLATE_ID,REPORT_DEF_CALCULATOR.UPDATED_BY_USER_ID,REPORT_DEF_CALCULATOR.UPDATED_TIME,REPORT_DEF_CALCULATOR.CREATED_TIME from REPORT_DEF_CALCULATOR where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  171 */       closeResultSet(resultSet);
/*  172 */       closeStatement(stmt);
/*  173 */       closeConnection();
/*      */ 
/*  175 */       if (timer != null)
/*  176 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  211 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  212 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  217 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  218 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  219 */       stmt = getConnection().prepareStatement("insert into REPORT_DEF_CALCULATOR ( REPORT_DEFINITION_ID,MODEL_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,CC_DEPLOYMENT_ID,REPORT_TEMPLATE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  222 */       int col = 1;
/*  223 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  224 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  227 */       int resultCount = stmt.executeUpdate();
/*  228 */       if (resultCount != 1)
/*      */       {
/*  230 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  233 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  237 */       throw handleSQLException(this.mDetails.getPK(), "insert into REPORT_DEF_CALCULATOR ( REPORT_DEFINITION_ID,MODEL_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,CC_DEPLOYMENT_ID,REPORT_TEMPLATE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  241 */       closeStatement(stmt);
/*  242 */       closeConnection();
/*      */ 
/*  244 */       if (timer != null)
/*  245 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  272 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  276 */     PreparedStatement stmt = null;
/*      */ 
/*  278 */     boolean mainChanged = this.mDetails.isModified();
/*  279 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  282 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  285 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  286 */         stmt = getConnection().prepareStatement("update REPORT_DEF_CALCULATOR set MODEL_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,CC_DEPLOYMENT_ID = ?,REPORT_TEMPLATE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? ");
/*      */ 
/*  289 */         int col = 1;
/*  290 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  291 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  294 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  297 */         if (resultCount != 1) {
/*  298 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  301 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  310 */       throw handleSQLException(getPK(), "update REPORT_DEF_CALCULATOR set MODEL_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,CC_DEPLOYMENT_ID = ?,REPORT_TEMPLATE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  314 */       closeStatement(stmt);
/*  315 */       closeConnection();
/*      */ 
/*  317 */       if ((timer != null) && (
/*  318 */         (mainChanged) || (dependantChanged)))
/*  319 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllReportDefCalcByCCDeploymentIdELO getAllReportDefCalcByCCDeploymentId(int param1)
/*      */   {
/*  361 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  362 */     PreparedStatement stmt = null;
/*  363 */     ResultSet resultSet = null;
/*  364 */     AllReportDefCalcByCCDeploymentIdELO results = new AllReportDefCalcByCCDeploymentIdELO();
/*      */     try
/*      */     {
/*  367 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_DEF_CALC_BY_C_C_DEPLOYMENT_ID);
/*  368 */       int col = 1;
/*  369 */       stmt.setInt(col++, param1);
/*  370 */       resultSet = stmt.executeQuery();
/*  371 */       while (resultSet.next())
/*      */       {
/*  373 */         col = 2;
/*      */ 
/*  376 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  379 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  382 */         ReportDefCalculatorPK pkReportDefCalculator = new ReportDefCalculatorPK(resultSet.getInt(col++));
/*      */ 
/*  385 */         String textReportDefCalculator = "";
/*      */ 
/*  390 */         ReportDefCalculatorCK ckReportDefCalculator = new ReportDefCalculatorCK(pkReportDefinition, pkReportDefCalculator);
/*      */ 
/*  396 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  402 */         ReportDefCalculatorRefImpl erReportDefCalculator = new ReportDefCalculatorRefImpl(ckReportDefCalculator, textReportDefCalculator);
/*      */ 
/*  407 */         int col1 = resultSet.getInt(col++);
/*  408 */         int col2 = resultSet.getInt(col++);
/*  409 */         int col3 = resultSet.getInt(col++);
/*  410 */         int col4 = resultSet.getInt(col++);
/*  411 */         int col5 = resultSet.getInt(col++);
/*      */ 
/*  414 */         results.add(erReportDefCalculator, erReportDefinition, col1, col2, col3, col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  427 */       throw handleSQLException(SQL_ALL_REPORT_DEF_CALC_BY_C_C_DEPLOYMENT_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  431 */       closeResultSet(resultSet);
/*  432 */       closeStatement(stmt);
/*  433 */       closeConnection();
/*      */     }
/*      */ 
/*  436 */     if (timer != null) {
/*  437 */       timer.logDebug("getAllReportDefCalcByCCDeploymentId", " CcDeploymentId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  442 */     return results;
/*      */   }
/*      */ 
/*      */   public AllReportDefCalcByReportTemplateIdELO getAllReportDefCalcByReportTemplateId(int param1)
/*      */   {
/*  482 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  483 */     PreparedStatement stmt = null;
/*  484 */     ResultSet resultSet = null;
/*  485 */     AllReportDefCalcByReportTemplateIdELO results = new AllReportDefCalcByReportTemplateIdELO();
/*      */     try
/*      */     {
/*  488 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_DEF_CALC_BY_REPORT_TEMPLATE_ID);
/*  489 */       int col = 1;
/*  490 */       stmt.setInt(col++, param1);
/*  491 */       resultSet = stmt.executeQuery();
/*  492 */       while (resultSet.next())
/*      */       {
/*  494 */         col = 2;
/*      */ 
/*  497 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  500 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  503 */         ReportDefCalculatorPK pkReportDefCalculator = new ReportDefCalculatorPK(resultSet.getInt(col++));
/*      */ 
/*  506 */         String textReportDefCalculator = "";
/*      */ 
/*  511 */         ReportDefCalculatorCK ckReportDefCalculator = new ReportDefCalculatorCK(pkReportDefinition, pkReportDefCalculator);
/*      */ 
/*  517 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  523 */         ReportDefCalculatorRefImpl erReportDefCalculator = new ReportDefCalculatorRefImpl(ckReportDefCalculator, textReportDefCalculator);
/*      */ 
/*  528 */         int col1 = resultSet.getInt(col++);
/*  529 */         int col2 = resultSet.getInt(col++);
/*  530 */         int col3 = resultSet.getInt(col++);
/*  531 */         int col4 = resultSet.getInt(col++);
/*  532 */         int col5 = resultSet.getInt(col++);
/*      */ 
/*  535 */         results.add(erReportDefCalculator, erReportDefinition, col1, col2, col3, col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  548 */       throw handleSQLException(SQL_ALL_REPORT_DEF_CALC_BY_REPORT_TEMPLATE_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  552 */       closeResultSet(resultSet);
/*  553 */       closeStatement(stmt);
/*  554 */       closeConnection();
/*      */     }
/*      */ 
/*  557 */     if (timer != null) {
/*  558 */       timer.logDebug("getAllReportDefCalcByReportTemplateId", " ReportTemplateId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  563 */     return results;
/*      */   }
/*      */ 
/*      */   public AllReportDefCalcByModelIdELO getAllReportDefCalcByModelId(int param1)
/*      */   {
/*  603 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  604 */     PreparedStatement stmt = null;
/*  605 */     ResultSet resultSet = null;
/*  606 */     AllReportDefCalcByModelIdELO results = new AllReportDefCalcByModelIdELO();
/*      */     try
/*      */     {
/*  609 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_DEF_CALC_BY_MODEL_ID);
/*  610 */       int col = 1;
/*  611 */       stmt.setInt(col++, param1);
/*  612 */       resultSet = stmt.executeQuery();
/*  613 */       while (resultSet.next())
/*      */       {
/*  615 */         col = 2;
/*      */ 
/*  618 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  621 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  624 */         ReportDefCalculatorPK pkReportDefCalculator = new ReportDefCalculatorPK(resultSet.getInt(col++));
/*      */ 
/*  627 */         String textReportDefCalculator = "";
/*      */ 
/*  632 */         ReportDefCalculatorCK ckReportDefCalculator = new ReportDefCalculatorCK(pkReportDefinition, pkReportDefCalculator);
/*      */ 
/*  638 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  644 */         ReportDefCalculatorRefImpl erReportDefCalculator = new ReportDefCalculatorRefImpl(ckReportDefCalculator, textReportDefCalculator);
/*      */ 
/*  649 */         int col1 = resultSet.getInt(col++);
/*  650 */         int col2 = resultSet.getInt(col++);
/*  651 */         int col3 = resultSet.getInt(col++);
/*  652 */         int col4 = resultSet.getInt(col++);
/*  653 */         int col5 = resultSet.getInt(col++);
/*      */ 
/*  656 */         results.add(erReportDefCalculator, erReportDefinition, col1, col2, col3, col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  669 */       throw handleSQLException(SQL_ALL_REPORT_DEF_CALC_BY_MODEL_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  673 */       closeResultSet(resultSet);
/*  674 */       closeStatement(stmt);
/*  675 */       closeConnection();
/*      */     }
/*      */ 
/*  678 */     if (timer != null) {
/*  679 */       timer.logDebug("getAllReportDefCalcByModelId", " ModelId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  684 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  701 */     if (items == null) {
/*  702 */       return false;
/*      */     }
/*  704 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  705 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  707 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  712 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  713 */       while (iter2.hasNext())
/*      */       {
/*  715 */         this.mDetails = ((ReportDefCalculatorEVO)iter2.next());
/*      */ 
/*  718 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  720 */         somethingChanged = true;
/*      */ 
/*  723 */         if (deleteStmt == null) {
/*  724 */           deleteStmt = getConnection().prepareStatement("delete from REPORT_DEF_CALCULATOR where    REPORT_DEFINITION_ID = ? ");
/*      */         }
/*      */ 
/*  727 */         int col = 1;
/*  728 */         deleteStmt.setInt(col++, this.mDetails.getReportDefinitionId());
/*      */ 
/*  730 */         if (this._log.isDebugEnabled()) {
/*  731 */           this._log.debug("update", "ReportDefCalculator deleting ReportDefinitionId=" + this.mDetails.getReportDefinitionId());
/*      */         }
/*      */ 
/*  736 */         deleteStmt.addBatch();
/*      */ 
/*  739 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  744 */       if (deleteStmt != null)
/*      */       {
/*  746 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  748 */         deleteStmt.executeBatch();
/*      */ 
/*  750 */         if (timer2 != null) {
/*  751 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  755 */       Iterator iter1 = items.values().iterator();
/*  756 */       while (iter1.hasNext())
/*      */       {
/*  758 */         this.mDetails = ((ReportDefCalculatorEVO)iter1.next());
/*      */ 
/*  760 */         if (this.mDetails.insertPending())
/*      */         {
/*  762 */           somethingChanged = true;
/*  763 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  766 */         if (!this.mDetails.isModified())
/*      */           continue;
/*  768 */         somethingChanged = true;
/*  769 */         doStore();
/*      */       }
/*      */ 
/*  780 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  784 */       throw handleSQLException("delete from REPORT_DEF_CALCULATOR where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  788 */       if (deleteStmt != null)
/*      */       {
/*  790 */         closeStatement(deleteStmt);
/*  791 */         closeConnection();
/*      */       }
/*      */ 
/*  794 */       this.mDetails = null;
/*      */ 
/*  796 */       if ((somethingChanged) && 
/*  797 */         (timer != null))
/*  798 */         timer.logDebug("update", "collection"); 
/*  798 */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ReportDefinitionPK entityPK, ReportDefinitionEVO owningEVO, String dependants)
/*      */   {
/*  817 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  819 */     PreparedStatement stmt = null;
/*  820 */     ResultSet resultSet = null;
/*      */ 
/*  822 */     int itemCount = 0;
/*      */ 
/*  824 */     Collection theseItems = new ArrayList();
/*  825 */     owningEVO.setReportCalculator(theseItems);
/*  826 */     owningEVO.setReportCalculatorAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  830 */       stmt = getConnection().prepareStatement("select REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID,REPORT_DEF_CALCULATOR.MODEL_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ELEMENT_ID,REPORT_DEF_CALCULATOR.CC_DEPLOYMENT_ID,REPORT_DEF_CALCULATOR.REPORT_TEMPLATE_ID,REPORT_DEF_CALCULATOR.UPDATED_BY_USER_ID,REPORT_DEF_CALCULATOR.UPDATED_TIME,REPORT_DEF_CALCULATOR.CREATED_TIME from REPORT_DEF_CALCULATOR where 1=1 and REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID = ? order by  REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID");
/*      */ 
/*  832 */       int col = 1;
/*  833 */       stmt.setInt(col++, entityPK.getReportDefinitionId());
/*      */ 
/*  835 */       resultSet = stmt.executeQuery();
/*      */ 
/*  838 */       while (resultSet.next())
/*      */       {
/*  840 */         itemCount++;
/*  841 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  843 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  846 */       if (timer != null) {
/*  847 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  852 */       throw handleSQLException("select REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID,REPORT_DEF_CALCULATOR.MODEL_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ELEMENT_ID,REPORT_DEF_CALCULATOR.CC_DEPLOYMENT_ID,REPORT_DEF_CALCULATOR.REPORT_TEMPLATE_ID,REPORT_DEF_CALCULATOR.UPDATED_BY_USER_ID,REPORT_DEF_CALCULATOR.UPDATED_TIME,REPORT_DEF_CALCULATOR.CREATED_TIME from REPORT_DEF_CALCULATOR where 1=1 and REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID = ? order by  REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  856 */       closeResultSet(resultSet);
/*  857 */       closeStatement(stmt);
/*  858 */       closeConnection();
/*      */ 
/*  860 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectReportDefinitionId, String dependants, Collection currentList)
/*      */   {
/*  885 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  886 */     PreparedStatement stmt = null;
/*  887 */     ResultSet resultSet = null;
/*      */ 
/*  889 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  893 */       stmt = getConnection().prepareStatement("select REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID,REPORT_DEF_CALCULATOR.MODEL_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ELEMENT_ID,REPORT_DEF_CALCULATOR.CC_DEPLOYMENT_ID,REPORT_DEF_CALCULATOR.REPORT_TEMPLATE_ID,REPORT_DEF_CALCULATOR.UPDATED_BY_USER_ID,REPORT_DEF_CALCULATOR.UPDATED_TIME,REPORT_DEF_CALCULATOR.CREATED_TIME from REPORT_DEF_CALCULATOR where    REPORT_DEFINITION_ID = ? ");
/*      */ 
/*  895 */       int col = 1;
/*  896 */       stmt.setInt(col++, selectReportDefinitionId);
/*      */ 
/*  898 */       resultSet = stmt.executeQuery();
/*      */ 
/*  900 */       while (resultSet.next())
/*      */       {
/*  902 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  905 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  908 */       if (currentList != null)
/*      */       {
/*  911 */         ListIterator iter = items.listIterator();
/*  912 */         ReportDefCalculatorEVO currentEVO = null;
/*  913 */         ReportDefCalculatorEVO newEVO = null;
/*  914 */         while (iter.hasNext())
/*      */         {
/*  916 */           newEVO = (ReportDefCalculatorEVO)iter.next();
/*  917 */           Iterator iter2 = currentList.iterator();
/*  918 */           while (iter2.hasNext())
/*      */           {
/*  920 */             currentEVO = (ReportDefCalculatorEVO)iter2.next();
/*  921 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  923 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  929 */         Iterator iter2 = currentList.iterator();
/*  930 */         while (iter2.hasNext())
/*      */         {
/*  932 */           currentEVO = (ReportDefCalculatorEVO)iter2.next();
/*  933 */           if (currentEVO.insertPending()) {
/*  934 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  938 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  942 */       throw handleSQLException("select REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID,REPORT_DEF_CALCULATOR.MODEL_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ID,REPORT_DEF_CALCULATOR.STRUCTURE_ELEMENT_ID,REPORT_DEF_CALCULATOR.CC_DEPLOYMENT_ID,REPORT_DEF_CALCULATOR.REPORT_TEMPLATE_ID,REPORT_DEF_CALCULATOR.UPDATED_BY_USER_ID,REPORT_DEF_CALCULATOR.UPDATED_TIME,REPORT_DEF_CALCULATOR.CREATED_TIME from REPORT_DEF_CALCULATOR where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  946 */       closeResultSet(resultSet);
/*  947 */       closeStatement(stmt);
/*  948 */       closeConnection();
/*      */ 
/*  950 */       if (timer != null) {
/*  951 */         timer.logDebug("getAll", " ReportDefinitionId=" + selectReportDefinitionId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  956 */     return items;
/*      */   }
/*      */ 
/*      */   public ReportDefCalculatorEVO getDetails(ReportDefinitionCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  970 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  973 */     if (this.mDetails == null) {
/*  974 */       doLoad(((ReportDefCalculatorCK)paramCK).getReportDefCalculatorPK());
/*      */     }
/*  976 */     else if (!this.mDetails.getPK().equals(((ReportDefCalculatorCK)paramCK).getReportDefCalculatorPK())) {
/*  977 */       doLoad(((ReportDefCalculatorCK)paramCK).getReportDefCalculatorPK());
/*      */     }
/*      */ 
/*  980 */     ReportDefCalculatorEVO details = new ReportDefCalculatorEVO();
/*  981 */     details = this.mDetails.deepClone();
/*      */ 
/*  983 */     if (timer != null) {
/*  984 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  986 */     return details;
/*      */   }
/*      */ 
/*      */   public ReportDefCalculatorEVO getDetails(ReportDefinitionCK paramCK, ReportDefCalculatorEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  992 */     ReportDefCalculatorEVO savedEVO = this.mDetails;
/*  993 */     this.mDetails = paramEVO;
/*  994 */     ReportDefCalculatorEVO newEVO = getDetails(paramCK, dependants);
/*  995 */     this.mDetails = savedEVO;
/*  996 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public ReportDefCalculatorEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1002 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1006 */     ReportDefCalculatorEVO details = this.mDetails.deepClone();
/*      */ 
/* 1008 */     if (timer != null) {
/* 1009 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1011 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1016 */     return "ReportDefCalculator";
/*      */   }
/*      */ 
/*      */   public ReportDefCalculatorRefImpl getRef(ReportDefCalculatorPK paramReportDefCalculatorPK)
/*      */   {
/* 1021 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1022 */     PreparedStatement stmt = null;
/* 1023 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1026 */       stmt = getConnection().prepareStatement("select 0,REPORT_DEFINITION.REPORT_DEFINITION_ID from REPORT_DEF_CALCULATOR,REPORT_DEFINITION where 1=1 and REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID = ? and REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID");
/* 1027 */       int col = 1;
/* 1028 */       stmt.setInt(col++, paramReportDefCalculatorPK.getReportDefinitionId());
/*      */ 
/* 1030 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1032 */       if (!resultSet.next()) {
/* 1033 */         throw new RuntimeException(getEntityName() + " getRef " + paramReportDefCalculatorPK + " not found");
/*      */       }
/* 1035 */       col = 2;
/* 1036 */       ReportDefinitionPK newReportDefinitionPK = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/* 1040 */       String textReportDefCalculator = "";
/* 1041 */       ReportDefCalculatorCK ckReportDefCalculator = new ReportDefCalculatorCK(newReportDefinitionPK, paramReportDefCalculatorPK);
/*      */ 
/* 1046 */       ReportDefCalculatorRefImpl localReportDefCalculatorRefImpl = new ReportDefCalculatorRefImpl(ckReportDefCalculator, textReportDefCalculator);
/*      */       return localReportDefCalculatorRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1051 */       throw handleSQLException(paramReportDefCalculatorPK, "select 0,REPORT_DEFINITION.REPORT_DEFINITION_ID from REPORT_DEF_CALCULATOR,REPORT_DEFINITION where 1=1 and REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID = ? and REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1055 */       closeResultSet(resultSet);
/* 1056 */       closeStatement(stmt);
/* 1057 */       closeConnection();
/*      */ 
/* 1059 */       if (timer != null)
/* 1060 */         timer.logDebug("getRef", paramReportDefCalculatorPK); 
/* 1060 */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.definition.ReportDefCalculatorDAO
 * JD-Core Version:    0.6.0
 */