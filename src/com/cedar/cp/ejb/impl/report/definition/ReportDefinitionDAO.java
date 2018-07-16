/*      */ package com.cedar.cp.ejb.impl.report.definition;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.report.definition.ReportDefinitionRef;
/*      */ import com.cedar.cp.dto.report.definition.AllPublicReportByTypeELO;
/*      */ import com.cedar.cp.dto.report.definition.AllReportDefinitionsELO;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefCalculatorCK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefCalculatorPK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefFormCK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefFormPK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefMappedExcelCK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefMappedExcelPK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcCK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefSummaryCalcPK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefinitionCK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefinitionForVisIdELO;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefinitionRefImpl;
/*      */ import com.cedar.cp.dto.report.type.ReportTypePK;
/*      */ import com.cedar.cp.dto.report.type.ReportTypeRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class ReportDefinitionDAO extends AbstractDAO
/*      */ {
/*   35 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select REPORT_DEFINITION_ID from REPORT_DEFINITION where    REPORT_DEFINITION_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select REPORT_DEFINITION.REPORT_DEFINITION_ID,REPORT_DEFINITION.VIS_ID,REPORT_DEFINITION.DESCRIPTION,REPORT_DEFINITION.REPORT_TYPE_ID,REPORT_DEFINITION.IS_PUBLIC,REPORT_DEFINITION.VERSION_NUM,REPORT_DEFINITION.UPDATED_BY_USER_ID,REPORT_DEFINITION.UPDATED_TIME,REPORT_DEFINITION.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from REPORT_DEFINITION where    REPORT_DEFINITION_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into REPORT_DEFINITION ( REPORT_DEFINITION_ID,VIS_ID,DESCRIPTION,REPORT_TYPE_ID,IS_PUBLIC,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update REPORT_DEFINITION_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from REPORT_DEFINITION_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_REPORTDEFINITIONNAME = "select count(*) from REPORT_DEFINITION where    VIS_ID = ? and not(    REPORT_DEFINITION_ID = ? )";
/*      */   protected static final String SQL_STORE = "update REPORT_DEFINITION set VIS_ID = ?,DESCRIPTION = ?,REPORT_TYPE_ID = ?,IS_PUBLIC = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from REPORT_DEFINITION where REPORT_DEFINITION_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from REPORT_DEFINITION where    REPORT_DEFINITION_ID = ? ";
/*  737 */   protected static String SQL_ALL_REPORT_DEFINITIONS = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_TYPE.REPORT_TYPE_ID      ,REPORT_TYPE.VIS_ID      ,REPORT_TYPE.VIS_ID      ,REPORT_DEFINITION.DESCRIPTION      ,REPORT_DEFINITION.IS_PUBLIC from REPORT_DEFINITION    ,REPORT_TYPE where 1=1  and  REPORT_DEFINITION.REPORT_TYPE_ID = REPORT_TYPE.REPORT_TYPE_ID";
			 protected static String SQL_ALL_REPORT_DEFINITIONS_FOR_USER = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_TYPE.REPORT_TYPE_ID      ,REPORT_TYPE.VIS_ID      ,REPORT_TYPE.VIS_ID      ,REPORT_DEFINITION.DESCRIPTION      ,REPORT_DEFINITION.IS_PUBLIC from REPORT_DEFINITION, REPORT_TYPE, report_def_mapped_excel e where e.model_id in (select distinct model_id from budget_user where user_id = ?) and REPORT_DEFINITION.REPORT_DEFINITION_ID=e.REPORT_DEFINITION_ID and  REPORT_DEFINITION.REPORT_TYPE_ID = REPORT_TYPE.REPORT_TYPE_ID";
/*      */ 
/*  841 */   protected static String SQL_ALL_PUBLIC_REPORT_BY_TYPE = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEFINITION.DESCRIPTION from REPORT_DEFINITION where 1=1  and  REPORT_DEFINITION.IS_PUBLIC = 'Y' AND REPORT_DEFINITION.REPORT_TYPE_ID = ?";
/*      */ 
/*  928 */   protected static String SQL_REPORT_DEFINITION_FOR_VIS_ID = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEFINITION.DESCRIPTION from REPORT_DEFINITION where 1=1  and  REPORT_DEFINITION.VIS_ID = ?";
/*      */ 
/* 1014 */   private static String[][] SQL_DELETE_CHILDREN = { { "REPORT_DEF_FORM", "delete from REPORT_DEF_FORM where     REPORT_DEF_FORM.REPORT_DEFINITION_ID = ? " }, { "REPORT_DEF_MAPPED_EXCEL", "delete from REPORT_DEF_MAPPED_EXCEL where     REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID = ? " }, { "REPORT_DEF_CALCULATOR", "delete from REPORT_DEF_CALCULATOR where     REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID = ? " }, { "REPORT_DEF_SUMMARY_CALC", "delete from REPORT_DEF_SUMMARY_CALC where     REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID = ? " } };
/*      */ 
/* 1038 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/* 1042 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and REPORT_DEFINITION.REPORT_DEFINITION_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from REPORT_DEFINITION where   REPORT_DEFINITION_ID = ?";
/*      */   public static final String SQL_GET_REPORT_DEF_FORM_REF = "select 0,REPORT_DEFINITION.REPORT_DEFINITION_ID from REPORT_DEF_FORM,REPORT_DEFINITION where 1=1 and REPORT_DEF_FORM.REPORT_DEFINITION_ID = ? and REPORT_DEF_FORM.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID";
/*      */   public static final String SQL_GET_REPORT_DEF_MAPPED_EXCEL_REF = "select 0,REPORT_DEFINITION.REPORT_DEFINITION_ID from REPORT_DEF_MAPPED_EXCEL,REPORT_DEFINITION where 1=1 and REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID = ? and REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID";
/*      */   public static final String SQL_GET_REPORT_DEF_CALCULATOR_REF = "select 0,REPORT_DEFINITION.REPORT_DEFINITION_ID from REPORT_DEF_CALCULATOR,REPORT_DEFINITION where 1=1 and REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID = ? and REPORT_DEF_CALCULATOR.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID";
/*      */   public static final String SQL_GET_REPORT_DEF_SUMMARY_CALC_REF = "select 0,REPORT_DEFINITION.REPORT_DEFINITION_ID from REPORT_DEF_SUMMARY_CALC,REPORT_DEFINITION where 1=1 and REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID = ? and REPORT_DEF_SUMMARY_CALC.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID";
/*      */   protected ReportDefFormDAO mReportDefFormDAO;
/*      */   protected ReportDefMappedExcelDAO mReportDefMappedExcelDAO;
/*      */   protected ReportDefCalculatorDAO mReportDefCalculatorDAO;
/*      */   protected ReportDefSummaryCalcDAO mReportDefSummaryCalcDAO;
/*      */   protected ReportDefinitionEVO mDetails;
/*      */ 
/*      */   public ReportDefinitionDAO(Connection connection)
/*      */   {
/*   42 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ReportDefinitionDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ReportDefinitionDAO(DataSource ds)
/*      */   {
/*   58 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ReportDefinitionPK getPK()
/*      */   {
/*   66 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ReportDefinitionEVO details)
/*      */   {
/*   75 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public ReportDefinitionEVO setAndGetDetails(ReportDefinitionEVO details, String dependants)
/*      */   {
/*   86 */     setDetails(details);
/*   87 */     generateKeys();
/*   88 */     getDependants(this.mDetails, dependants);
/*   89 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public ReportDefinitionPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   98 */     doCreate();
/*      */ 
/*  100 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(ReportDefinitionPK pk)
/*      */     throws ValidationException
/*      */   {
/*  110 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  119 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  128 */     doRemove();
/*      */   }
/*      */ 
/*      */   public ReportDefinitionPK findByPrimaryKey(ReportDefinitionPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  137 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  138 */     if (exists(pk_))
/*      */     {
/*  140 */       if (timer != null) {
/*  141 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  143 */       return pk_;
/*      */     }
/*      */ 
/*  146 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(ReportDefinitionPK pk)
/*      */   {
/*  164 */     PreparedStatement stmt = null;
/*  165 */     ResultSet resultSet = null;
/*  166 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  170 */       stmt = getConnection().prepareStatement("select REPORT_DEFINITION_ID from REPORT_DEFINITION where    REPORT_DEFINITION_ID = ? ");
/*      */ 
/*  172 */       int col = 1;
/*  173 */       stmt.setInt(col++, pk.getReportDefinitionId());
/*      */ 
/*  175 */       resultSet = stmt.executeQuery();
/*      */ 
/*  177 */       if (!resultSet.next())
/*  178 */         returnValue = false;
/*      */       else
/*  180 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  184 */       throw handleSQLException(pk, "select REPORT_DEFINITION_ID from REPORT_DEFINITION where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  188 */       closeResultSet(resultSet);
/*  189 */       closeStatement(stmt);
/*  190 */       closeConnection();
/*      */     }
/*  192 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private ReportDefinitionEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  212 */     int col = 1;
/*  213 */     ReportDefinitionEVO evo = new ReportDefinitionEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), null, null, null, null);
/*      */ 
/*  226 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  227 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  228 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  229 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ReportDefinitionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  234 */     int col = startCol_;
/*  235 */     stmt_.setInt(col++, evo_.getReportDefinitionId());
/*  236 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ReportDefinitionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  241 */     int col = startCol_;
/*  242 */     stmt_.setString(col++, evo_.getVisId());
/*  243 */     stmt_.setString(col++, evo_.getDescription());
/*  244 */     stmt_.setInt(col++, evo_.getReportTypeId());
/*  245 */     if (evo_.getIsPublic())
/*  246 */       stmt_.setString(col++, "Y");
/*      */     else
/*  248 */       stmt_.setString(col++, " ");
/*  249 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  250 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  251 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  252 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  253 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ReportDefinitionPK pk)
/*      */     throws ValidationException
/*      */   {
/*  269 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  271 */     PreparedStatement stmt = null;
/*  272 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  276 */       stmt = getConnection().prepareStatement("select REPORT_DEFINITION.REPORT_DEFINITION_ID,REPORT_DEFINITION.VIS_ID,REPORT_DEFINITION.DESCRIPTION,REPORT_DEFINITION.REPORT_TYPE_ID,REPORT_DEFINITION.IS_PUBLIC,REPORT_DEFINITION.VERSION_NUM,REPORT_DEFINITION.UPDATED_BY_USER_ID,REPORT_DEFINITION.UPDATED_TIME,REPORT_DEFINITION.CREATED_TIME from REPORT_DEFINITION where    REPORT_DEFINITION_ID = ? ");
/*      */ 
/*  279 */       int col = 1;
/*  280 */       stmt.setInt(col++, pk.getReportDefinitionId());
/*      */ 
/*  282 */       resultSet = stmt.executeQuery();
/*      */ 
/*  284 */       if (!resultSet.next()) {
/*  285 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  288 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  289 */       if (this.mDetails.isModified())
/*  290 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  294 */       throw handleSQLException(pk, "select REPORT_DEFINITION.REPORT_DEFINITION_ID,REPORT_DEFINITION.VIS_ID,REPORT_DEFINITION.DESCRIPTION,REPORT_DEFINITION.REPORT_TYPE_ID,REPORT_DEFINITION.IS_PUBLIC,REPORT_DEFINITION.VERSION_NUM,REPORT_DEFINITION.UPDATED_BY_USER_ID,REPORT_DEFINITION.UPDATED_TIME,REPORT_DEFINITION.CREATED_TIME from REPORT_DEFINITION where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  298 */       closeResultSet(resultSet);
/*  299 */       closeStatement(stmt);
/*  300 */       closeConnection();
/*      */ 
/*  302 */       if (timer != null)
/*  303 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  338 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  339 */     generateKeys();
/*      */ 
/*  341 */     this.mDetails.postCreateInit();
/*      */ 
/*  343 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  348 */       duplicateValueCheckReportDefinitionName();
/*      */ 
/*  350 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  351 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  352 */       stmt = getConnection().prepareStatement("insert into REPORT_DEFINITION ( REPORT_DEFINITION_ID,VIS_ID,DESCRIPTION,REPORT_TYPE_ID,IS_PUBLIC,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  355 */       int col = 1;
/*  356 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  357 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  360 */       int resultCount = stmt.executeUpdate();
/*  361 */       if (resultCount != 1)
/*      */       {
/*  363 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  366 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  370 */       throw handleSQLException(this.mDetails.getPK(), "insert into REPORT_DEFINITION ( REPORT_DEFINITION_ID,VIS_ID,DESCRIPTION,REPORT_TYPE_ID,IS_PUBLIC,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  374 */       closeStatement(stmt);
/*  375 */       closeConnection();
/*      */ 
/*  377 */       if (timer != null) {
/*  378 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  384 */       getReportDefFormDAO().update(this.mDetails.getReportFormMap());
/*      */ 
/*  386 */       getReportDefMappedExcelDAO().update(this.mDetails.getReportMappedExcelMap());
/*      */ 
/*  388 */       getReportDefCalculatorDAO().update(this.mDetails.getReportCalculatorMap());
/*      */ 
/*  390 */       getReportDefSummaryCalcDAO().update(this.mDetails.getSummaryCalcReportMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  396 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  416 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  418 */     PreparedStatement stmt = null;
/*  419 */     ResultSet resultSet = null;
/*  420 */     String sqlString = null;
/*      */     try
/*      */     {
/*  425 */       sqlString = "update REPORT_DEFINITION_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  426 */       stmt = getConnection().prepareStatement("update REPORT_DEFINITION_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  427 */       stmt.setInt(1, insertCount);
/*      */ 
/*  429 */       int resultCount = stmt.executeUpdate();
/*  430 */       if (resultCount != 1) {
/*  431 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  433 */       closeStatement(stmt);
/*      */ 
/*  436 */       sqlString = "select SEQ_NUM from REPORT_DEFINITION_SEQ";
/*  437 */       stmt = getConnection().prepareStatement("select SEQ_NUM from REPORT_DEFINITION_SEQ");
/*  438 */       resultSet = stmt.executeQuery();
/*  439 */       if (!resultSet.next())
/*  440 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  441 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  443 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  447 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  451 */       closeResultSet(resultSet);
/*  452 */       closeStatement(stmt);
/*  453 */       closeConnection();
/*      */ 
/*  455 */       if (timer != null)
/*  456 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  456 */     }
/*      */   }
/*      */ 
/*      */   public ReportDefinitionPK generateKeys()
/*      */   {
/*  466 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  468 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  471 */     if (insertCount == 0) {
/*  472 */       return this.mDetails.getPK();
/*      */     }
/*  474 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  476 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckReportDefinitionName()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  489 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  490 */     PreparedStatement stmt = null;
/*  491 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  495 */       stmt = getConnection().prepareStatement("select count(*) from REPORT_DEFINITION where    VIS_ID = ? and not(    REPORT_DEFINITION_ID = ? )");
/*      */ 
/*  498 */       int col = 1;
/*  499 */       stmt.setString(col++, this.mDetails.getVisId());
/*  500 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  503 */       resultSet = stmt.executeQuery();
/*      */ 
/*  505 */       if (!resultSet.next()) {
/*  506 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  510 */       col = 1;
/*  511 */       int count = resultSet.getInt(col++);
/*  512 */       if (count > 0) {
/*  513 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " ReportDefinitionName");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  519 */       throw handleSQLException(getPK(), "select count(*) from REPORT_DEFINITION where    VIS_ID = ? and not(    REPORT_DEFINITION_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  523 */       closeResultSet(resultSet);
/*  524 */       closeStatement(stmt);
/*  525 */       closeConnection();
/*      */ 
/*  527 */       if (timer != null)
/*  528 */         timer.logDebug("duplicateValueCheckReportDefinitionName", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  555 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  557 */     generateKeys();
/*      */ 
/*  562 */     PreparedStatement stmt = null;
/*      */ 
/*  564 */     boolean mainChanged = this.mDetails.isModified();
/*  565 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  569 */       if (mainChanged) {
/*  570 */         duplicateValueCheckReportDefinitionName();
/*      */       }
/*  572 */       if (getReportDefFormDAO().update(this.mDetails.getReportFormMap())) {
/*  573 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  576 */       if (getReportDefMappedExcelDAO().update(this.mDetails.getReportMappedExcelMap())) {
/*  577 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  580 */       if (getReportDefCalculatorDAO().update(this.mDetails.getReportCalculatorMap())) {
/*  581 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  584 */       if (getReportDefSummaryCalcDAO().update(this.mDetails.getSummaryCalcReportMap())) {
/*  585 */         dependantChanged = true;
/*      */       }
/*  587 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  590 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  593 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  594 */         stmt = getConnection().prepareStatement("update REPORT_DEFINITION set VIS_ID = ?,DESCRIPTION = ?,REPORT_TYPE_ID = ?,IS_PUBLIC = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  597 */         int col = 1;
/*  598 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  599 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  601 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  604 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  606 */         if (resultCount == 0) {
/*  607 */           checkVersionNum();
/*      */         }
/*  609 */         if (resultCount != 1) {
/*  610 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  613 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  622 */       throw handleSQLException(getPK(), "update REPORT_DEFINITION set VIS_ID = ?,DESCRIPTION = ?,REPORT_TYPE_ID = ?,IS_PUBLIC = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  626 */       closeStatement(stmt);
/*  627 */       closeConnection();
/*      */ 
/*  629 */       if ((timer != null) && (
/*  630 */         (mainChanged) || (dependantChanged)))
/*  631 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  643 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  644 */     PreparedStatement stmt = null;
/*  645 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  649 */       stmt = getConnection().prepareStatement("select VERSION_NUM from REPORT_DEFINITION where REPORT_DEFINITION_ID = ?");
/*      */ 
/*  652 */       int col = 1;
/*  653 */       stmt.setInt(col++, this.mDetails.getReportDefinitionId());
/*      */ 
/*  656 */       resultSet = stmt.executeQuery();
/*      */ 
/*  658 */       if (!resultSet.next()) {
/*  659 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  662 */       col = 1;
/*  663 */       int dbVersionNumber = resultSet.getInt(col++);
/*  664 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  665 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  671 */       throw handleSQLException(getPK(), "select VERSION_NUM from REPORT_DEFINITION where REPORT_DEFINITION_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  675 */       closeStatement(stmt);
/*  676 */       closeResultSet(resultSet);
/*      */ 
/*  678 */       if (timer != null)
/*  679 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  696 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  697 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  702 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  707 */       stmt = getConnection().prepareStatement("delete from REPORT_DEFINITION where    REPORT_DEFINITION_ID = ? ");
/*      */ 
/*  710 */       int col = 1;
/*  711 */       stmt.setInt(col++, this.mDetails.getReportDefinitionId());
/*      */ 
/*  713 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  715 */       if (resultCount != 1) {
/*  716 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  722 */       throw handleSQLException(getPK(), "delete from REPORT_DEFINITION where    REPORT_DEFINITION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  726 */       closeStatement(stmt);
/*  727 */       closeConnection();
/*      */ 
/*  729 */       if (timer != null)
/*  730 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllReportDefinitionsELO getAllReportDefinitions()
/*      */   {
/*  766 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  767 */     PreparedStatement stmt = null;
/*  768 */     ResultSet resultSet = null;
/*  769 */     AllReportDefinitionsELO results = new AllReportDefinitionsELO();
/*      */     try
/*      */     {
/*  772 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_DEFINITIONS);
/*  773 */       int col = 1;
/*  774 */       resultSet = stmt.executeQuery();
/*  775 */       while (resultSet.next())
/*      */       {
/*  777 */         col = 2;
/*      */ 
/*  780 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  783 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  786 */         ReportTypePK pkReportType = new ReportTypePK(resultSet.getInt(col++));
/*      */ 
/*  789 */         String textReportType = resultSet.getString(col++);
/*      */ 
/*  792 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  798 */         ReportTypeRefImpl erReportType = new ReportTypeRefImpl(pkReportType, textReportType);
/*      */ 
/*  803 */         String col1 = resultSet.getString(col++);
/*  804 */         String col2 = resultSet.getString(col++);
/*  805 */         String col3 = resultSet.getString(col++);
/*  806 */         if (resultSet.wasNull()) {
/*  807 */           col3 = "";
/*      */         }
/*      */ 
/*  810 */         results.add(erReportDefinition, erReportType, col1, col2, col3.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  821 */       throw handleSQLException(SQL_ALL_REPORT_DEFINITIONS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  825 */       closeResultSet(resultSet);
/*  826 */       closeStatement(stmt);
/*  827 */       closeConnection();
/*      */     }
/*      */ 
/*  830 */     if (timer != null) {
/*  831 */       timer.logDebug("getAllReportDefinitions", " items=" + results.size());
/*      */     }
/*      */ 
/*  835 */     return results;
/*      */   }

			 public AllReportDefinitionsELO getAllReportDefinitionsForLoggedUser(int userId)
/*      */   {
/*  766 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  767 */     PreparedStatement stmt = null;
/*  768 */     ResultSet resultSet = null;
/*  769 */     AllReportDefinitionsELO results = new AllReportDefinitionsELO();
/*      */     try
/*      */     {
/*  772 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_DEFINITIONS_FOR_USER);
/*  773 */       int col = 1;
				 stmt.setInt(1, userId);
/*  774 */       resultSet = stmt.executeQuery();
/*  775 */       while (resultSet.next())
/*      */       {
/*  777 */         col = 2;
/*      */ 
/*  780 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  783 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  786 */         ReportTypePK pkReportType = new ReportTypePK(resultSet.getInt(col++));
/*      */ 
/*  789 */         String textReportType = resultSet.getString(col++);
/*      */ 
/*  792 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  798 */         ReportTypeRefImpl erReportType = new ReportTypeRefImpl(pkReportType, textReportType);
/*      */ 
/*  803 */         String col1 = resultSet.getString(col++);
/*  804 */         String col2 = resultSet.getString(col++);
/*  805 */         String col3 = resultSet.getString(col++);
/*  806 */         if (resultSet.wasNull()) {
/*  807 */           col3 = "";
/*      */         }
/*      */ 
/*  810 */         results.add(erReportDefinition, erReportType, col1, col2, col3.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  821 */       throw handleSQLException(SQL_ALL_REPORT_DEFINITIONS_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  825 */       closeResultSet(resultSet);
/*  826 */       closeStatement(stmt);
/*  827 */       closeConnection();
/*      */     }
/*      */ 
/*  830 */     if (timer != null) {
/*  831 */       timer.logDebug("getAllReportDefinitions", " items=" + results.size());
/*      */     }
/*      */ 
/*  835 */     return results;
/*      */   }
/*      */ 
/*      */   public AllPublicReportByTypeELO getAllPublicReportByType(int param1)
/*      */   {
/*  867 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  868 */     PreparedStatement stmt = null;
/*  869 */     ResultSet resultSet = null;
/*  870 */     AllPublicReportByTypeELO results = new AllPublicReportByTypeELO();
/*      */     try
/*      */     {
/*  873 */       stmt = getConnection().prepareStatement(SQL_ALL_PUBLIC_REPORT_BY_TYPE);
/*  874 */       int col = 1;
/*  875 */       stmt.setInt(col++, param1);
/*  876 */       resultSet = stmt.executeQuery();
/*  877 */       while (resultSet.next())
/*      */       {
/*  879 */         col = 2;
/*      */ 
/*  882 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  885 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  889 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  894 */         String col1 = resultSet.getString(col++);
/*  895 */         String col2 = resultSet.getString(col++);
/*      */ 
/*  898 */         results.add(erReportDefinition, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  907 */       throw handleSQLException(SQL_ALL_PUBLIC_REPORT_BY_TYPE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  911 */       closeResultSet(resultSet);
/*  912 */       closeStatement(stmt);
/*  913 */       closeConnection();
/*      */     }
/*      */ 
/*  916 */     if (timer != null) {
/*  917 */       timer.logDebug("getAllPublicReportByType", " ReportTypeId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  922 */     return results;
/*      */   }
/*      */ 
/*      */   public ReportDefinitionForVisIdELO getReportDefinitionForVisId(String param1)
/*      */   {
/*  954 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  955 */     PreparedStatement stmt = null;
/*  956 */     ResultSet resultSet = null;
/*  957 */     ReportDefinitionForVisIdELO results = new ReportDefinitionForVisIdELO();
/*      */     try
/*      */     {
/*  960 */       stmt = getConnection().prepareStatement(SQL_REPORT_DEFINITION_FOR_VIS_ID);
/*  961 */       int col = 1;
/*  962 */       stmt.setString(col++, param1);
/*  963 */       resultSet = stmt.executeQuery();
/*  964 */       while (resultSet.next())
/*      */       {
/*  966 */         col = 2;
/*      */ 
/*  969 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  972 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  976 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  981 */         String col1 = resultSet.getString(col++);
/*  982 */         String col2 = resultSet.getString(col++);
/*      */ 
/*  985 */         results.add(erReportDefinition, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  994 */       throw handleSQLException(SQL_REPORT_DEFINITION_FOR_VIS_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  998 */       closeResultSet(resultSet);
/*  999 */       closeStatement(stmt);
/* 1000 */       closeConnection();
/*      */     }
/*      */ 
/* 1003 */     if (timer != null) {
/* 1004 */       timer.logDebug("getReportDefinitionForVisId", " VisId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1009 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ReportDefinitionPK pk)
/*      */   {
/* 1051 */     Set emptyStrings = Collections.emptySet();
/* 1052 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ReportDefinitionPK pk, Set<String> exclusionTables)
/*      */   {
/* 1058 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1060 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1062 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1064 */       PreparedStatement stmt = null;
/*      */ 
/* 1066 */       int resultCount = 0;
/* 1067 */       String s = null;
/*      */       try
/*      */       {
/* 1070 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 1072 */         if (this._log.isDebugEnabled()) {
/* 1073 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1075 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1078 */         int col = 1;
/* 1079 */         stmt.setInt(col++, pk.getReportDefinitionId());
/*      */ 
/* 1082 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1086 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1090 */         closeStatement(stmt);
/* 1091 */         closeConnection();
/*      */ 
/* 1093 */         if (timer != null) {
/* 1094 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1098 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1100 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1102 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1104 */       PreparedStatement stmt = null;
/*      */ 
/* 1106 */       int resultCount = 0;
/* 1107 */       String s = null;
/*      */       try
/*      */       {
/* 1110 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1112 */         if (this._log.isDebugEnabled()) {
/* 1113 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1115 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1118 */         int col = 1;
/* 1119 */         stmt.setInt(col++, pk.getReportDefinitionId());
/*      */ 
/* 1122 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1126 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1130 */         closeStatement(stmt);
/* 1131 */         closeConnection();
/*      */ 
/* 1133 */         if (timer != null)
/* 1134 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public ReportDefinitionEVO getDetails(ReportDefinitionPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1156 */     return getDetails(new ReportDefinitionCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public ReportDefinitionEVO getDetails(ReportDefinitionCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1179 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1182 */     if (this.mDetails == null) {
/* 1183 */       doLoad(paramCK.getReportDefinitionPK());
/*      */     }
/* 1185 */     else if (!this.mDetails.getPK().equals(paramCK.getReportDefinitionPK())) {
/* 1186 */       doLoad(paramCK.getReportDefinitionPK());
/*      */     }
/* 1188 */     else if (!checkIfValid())
/*      */     {
/* 1190 */       this._log.info("getDetails", "[ALERT] ReportDefinitionEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1192 */       doLoad(paramCK.getReportDefinitionPK());
/*      */     }
/*      */ 
/* 1220 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isReportFormAllItemsLoaded()))
/*      */     {
/* 1225 */       this.mDetails.setReportForm(getReportDefFormDAO().getAll(this.mDetails.getReportDefinitionId(), dependants, this.mDetails.getReportForm()));
/*      */ 
/* 1232 */       this.mDetails.setReportFormAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1236 */     if ((dependants.indexOf("<1>") > -1) && (!this.mDetails.isReportMappedExcelAllItemsLoaded()))
/*      */     {
/* 1241 */       this.mDetails.setReportMappedExcel(getReportDefMappedExcelDAO().getAll(this.mDetails.getReportDefinitionId(), dependants, this.mDetails.getReportMappedExcel()));
/*      */ 
/* 1248 */       this.mDetails.setReportMappedExcelAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1252 */     if ((dependants.indexOf("<2>") > -1) && (!this.mDetails.isReportCalculatorAllItemsLoaded()))
/*      */     {
/* 1257 */       this.mDetails.setReportCalculator(getReportDefCalculatorDAO().getAll(this.mDetails.getReportDefinitionId(), dependants, this.mDetails.getReportCalculator()));
/*      */ 
/* 1264 */       this.mDetails.setReportCalculatorAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1268 */     if ((dependants.indexOf("<3>") > -1) && (!this.mDetails.isSummaryCalcReportAllItemsLoaded()))
/*      */     {
/* 1273 */       this.mDetails.setSummaryCalcReport(getReportDefSummaryCalcDAO().getAll(this.mDetails.getReportDefinitionId(), dependants, this.mDetails.getSummaryCalcReport()));
/*      */ 
/* 1280 */       this.mDetails.setSummaryCalcReportAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1283 */     if ((paramCK instanceof ReportDefFormCK))
/*      */     {
/* 1285 */       if (this.mDetails.getReportForm() == null) {
/* 1286 */         this.mDetails.loadReportFormItem(getReportDefFormDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1289 */         ReportDefFormPK pk = ((ReportDefFormCK)paramCK).getReportDefFormPK();
/* 1290 */         ReportDefFormEVO evo = this.mDetails.getReportFormItem(pk);
/* 1291 */         if (evo == null) {
/* 1292 */           this.mDetails.loadReportFormItem(getReportDefFormDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/* 1296 */     else if ((paramCK instanceof ReportDefMappedExcelCK))
/*      */     {
/* 1298 */       if (this.mDetails.getReportMappedExcel() == null) {
/* 1299 */         this.mDetails.loadReportMappedExcelItem(getReportDefMappedExcelDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1302 */         ReportDefMappedExcelPK pk = ((ReportDefMappedExcelCK)paramCK).getReportDefMappedExcelPK();
/* 1303 */         ReportDefMappedExcelEVO evo = this.mDetails.getReportMappedExcelItem(pk);
/* 1304 */         if (evo == null) {
/* 1305 */           this.mDetails.loadReportMappedExcelItem(getReportDefMappedExcelDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/* 1309 */     else if ((paramCK instanceof ReportDefCalculatorCK))
/*      */     {
/* 1311 */       if (this.mDetails.getReportCalculator() == null) {
/* 1312 */         this.mDetails.loadReportCalculatorItem(getReportDefCalculatorDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1315 */         ReportDefCalculatorPK pk = ((ReportDefCalculatorCK)paramCK).getReportDefCalculatorPK();
/* 1316 */         ReportDefCalculatorEVO evo = this.mDetails.getReportCalculatorItem(pk);
/* 1317 */         if (evo == null) {
/* 1318 */           this.mDetails.loadReportCalculatorItem(getReportDefCalculatorDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/* 1322 */     else if ((paramCK instanceof ReportDefSummaryCalcCK))
/*      */     {
/* 1324 */       if (this.mDetails.getSummaryCalcReport() == null) {
/* 1325 */         this.mDetails.loadSummaryCalcReportItem(getReportDefSummaryCalcDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1328 */         ReportDefSummaryCalcPK pk = ((ReportDefSummaryCalcCK)paramCK).getReportDefSummaryCalcPK();
/* 1329 */         ReportDefSummaryCalcEVO evo = this.mDetails.getSummaryCalcReportItem(pk);
/* 1330 */         if (evo == null) {
/* 1331 */           this.mDetails.loadSummaryCalcReportItem(getReportDefSummaryCalcDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1336 */     ReportDefinitionEVO details = new ReportDefinitionEVO();
/* 1337 */     details = this.mDetails.deepClone();
/*      */ 
/* 1339 */     if (timer != null) {
/* 1340 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1342 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1352 */     boolean stillValid = false;
/* 1353 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1354 */     PreparedStatement stmt = null;
/* 1355 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1358 */       stmt = getConnection().prepareStatement("select VERSION_NUM from REPORT_DEFINITION where   REPORT_DEFINITION_ID = ?");
/* 1359 */       int col = 1;
/* 1360 */       stmt.setInt(col++, this.mDetails.getReportDefinitionId());
/*      */ 
/* 1362 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1364 */       if (!resultSet.next()) {
/* 1365 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1367 */       col = 1;
/* 1368 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1370 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1371 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1375 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from REPORT_DEFINITION where   REPORT_DEFINITION_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1379 */       closeResultSet(resultSet);
/* 1380 */       closeStatement(stmt);
/* 1381 */       closeConnection();
/*      */ 
/* 1383 */       if (timer != null) {
/* 1384 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1387 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public ReportDefinitionEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1393 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1395 */     if (!checkIfValid())
/*      */     {
/* 1397 */       this._log.info("getDetails", "ReportDefinition " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1398 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1402 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1405 */     ReportDefinitionEVO details = this.mDetails.deepClone();
/*      */ 
/* 1407 */     if (timer != null) {
/* 1408 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1410 */     return details;
/*      */   }
/*      */ 
/*      */   protected ReportDefFormDAO getReportDefFormDAO()
/*      */   {
/* 1419 */     if (this.mReportDefFormDAO == null)
/*      */     {
/* 1421 */       if (this.mDataSource != null)
/* 1422 */         this.mReportDefFormDAO = new ReportDefFormDAO(this.mDataSource);
/*      */       else {
/* 1424 */         this.mReportDefFormDAO = new ReportDefFormDAO(getConnection());
/*      */       }
/*      */     }
/* 1427 */     return this.mReportDefFormDAO;
/*      */   }
/*      */ 
/*      */   protected ReportDefMappedExcelDAO getReportDefMappedExcelDAO()
/*      */   {
/* 1436 */     if (this.mReportDefMappedExcelDAO == null)
/*      */     {
/* 1438 */       if (this.mDataSource != null)
/* 1439 */         this.mReportDefMappedExcelDAO = new ReportDefMappedExcelDAO(this.mDataSource);
/*      */       else {
/* 1441 */         this.mReportDefMappedExcelDAO = new ReportDefMappedExcelDAO(getConnection());
/*      */       }
/*      */     }
/* 1444 */     return this.mReportDefMappedExcelDAO;
/*      */   }
/*      */ 
/*      */   protected ReportDefCalculatorDAO getReportDefCalculatorDAO()
/*      */   {
/* 1453 */     if (this.mReportDefCalculatorDAO == null)
/*      */     {
/* 1455 */       if (this.mDataSource != null)
/* 1456 */         this.mReportDefCalculatorDAO = new ReportDefCalculatorDAO(this.mDataSource);
/*      */       else {
/* 1458 */         this.mReportDefCalculatorDAO = new ReportDefCalculatorDAO(getConnection());
/*      */       }
/*      */     }
/* 1461 */     return this.mReportDefCalculatorDAO;
/*      */   }
/*      */ 
/*      */   protected ReportDefSummaryCalcDAO getReportDefSummaryCalcDAO()
/*      */   {
/* 1470 */     if (this.mReportDefSummaryCalcDAO == null)
/*      */     {
/* 1472 */       if (this.mDataSource != null)
/* 1473 */         this.mReportDefSummaryCalcDAO = new ReportDefSummaryCalcDAO(this.mDataSource);
/*      */       else {
/* 1475 */         this.mReportDefSummaryCalcDAO = new ReportDefSummaryCalcDAO(getConnection());
/*      */       }
/*      */     }
/* 1478 */     return this.mReportDefSummaryCalcDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1483 */     return "ReportDefinition";
/*      */   }
/*      */ 
/*      */   public ReportDefinitionRef getRef(ReportDefinitionPK paramReportDefinitionPK)
/*      */     throws ValidationException
/*      */   {
/* 1489 */     ReportDefinitionEVO evo = getDetails(paramReportDefinitionPK, "");
/* 1490 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1550 */     if (c == null)
/* 1551 */       return;
/* 1552 */     Iterator iter = c.iterator();
/* 1553 */     while (iter.hasNext())
/*      */     {
/* 1555 */       ReportDefinitionEVO evo = (ReportDefinitionEVO)iter.next();
/* 1556 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(ReportDefinitionEVO evo, String dependants)
/*      */   {
/* 1570 */     if (evo.getReportDefinitionId() < 1) {
/* 1571 */       return;
/*      */     }
/*      */ 
/* 1591 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1594 */       if (!evo.isReportFormAllItemsLoaded())
/*      */       {
/* 1596 */         evo.setReportForm(getReportDefFormDAO().getAll(evo.getReportDefinitionId(), dependants, evo.getReportForm()));
/*      */ 
/* 1603 */         evo.setReportFormAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1608 */     if (dependants.indexOf("<1>") > -1)
/*      */     {
/* 1611 */       if (!evo.isReportMappedExcelAllItemsLoaded())
/*      */       {
/* 1613 */         evo.setReportMappedExcel(getReportDefMappedExcelDAO().getAll(evo.getReportDefinitionId(), dependants, evo.getReportMappedExcel()));
/*      */ 
/* 1620 */         evo.setReportMappedExcelAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1625 */     if (dependants.indexOf("<2>") > -1)
/*      */     {
/* 1628 */       if (!evo.isReportCalculatorAllItemsLoaded())
/*      */       {
/* 1630 */         evo.setReportCalculator(getReportDefCalculatorDAO().getAll(evo.getReportDefinitionId(), dependants, evo.getReportCalculator()));
/*      */ 
/* 1637 */         evo.setReportCalculatorAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1642 */     if (dependants.indexOf("<3>") > -1)
/*      */     {
/* 1645 */       if (!evo.isSummaryCalcReportAllItemsLoaded())
/*      */       {
/* 1647 */         evo.setSummaryCalcReport(getReportDefSummaryCalcDAO().getAll(evo.getReportDefinitionId(), dependants, evo.getSummaryCalcReport()));
/*      */ 
/* 1654 */         evo.setSummaryCalcReportAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.definition.ReportDefinitionDAO
 * JD-Core Version:    0.6.0
 */