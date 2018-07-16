/*      */ package com.cedar.cp.ejb.impl.report;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.report.ReportRef;
/*      */ import com.cedar.cp.dto.report.AllReportsELO;
/*      */ import com.cedar.cp.dto.report.AllReportsForAdminELO;
/*      */ import com.cedar.cp.dto.report.AllReportsForUserELO;
/*      */ import com.cedar.cp.dto.report.ReportCK;
/*      */ import com.cedar.cp.dto.report.ReportPK;
/*      */ import com.cedar.cp.dto.report.ReportRefImpl;
/*      */ import com.cedar.cp.dto.report.WebReportDetailsELO;
/*      */ import com.cedar.cp.dto.report.tran.CubePendingTranCK;
/*      */ import com.cedar.cp.dto.report.tran.CubePendingTranPK;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.model.act.BudgetActivityEVO;
/*      */ import com.cedar.cp.ejb.impl.model.act.BudgetActivityLinkEVO;
/*      */ import com.cedar.cp.ejb.impl.report.tran.CubePendingTranDAO;
/*      */ import com.cedar.cp.ejb.impl.report.tran.CubePendingTranEVO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.Reader;
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
/*      */ import oracle.sql.CLOB;
/*      */ 
/*      */ public class ReportDAO extends AbstractDAO
/*      */ {
/*   39 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select REPORT_ID from REPORT where    REPORT_ID = ? ";
/*      */   private static final String SQL_SELECT_LOBS = "select  REPORT_TEXT,ACTIVITY_DETAIL from REPORT where    REPORT_ID = ? for update";
/*      */   private static final String SQL_SELECT_COLUMNS = "select REPORT.REPORT_TEXT,REPORT.ACTIVITY_DETAIL,REPORT.REPORT_ID,REPORT.USER_ID,REPORT.REPORT_TYPE,REPORT.TASK_ID,REPORT.COMPLETE,REPORT.HAS_UPDATES,REPORT.UPDATES_APPLIED,REPORT.UPDATE_TASK_ID,REPORT.BUDGET_CYCLE_ID,REPORT.ACTIVITY_TYPE,REPORT.VERSION_NUM,REPORT.UPDATED_BY_USER_ID,REPORT.UPDATED_TIME,REPORT.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from REPORT where    REPORT_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into REPORT ( REPORT_ID,USER_ID,REPORT_TYPE,TASK_ID,COMPLETE,REPORT_TEXT,HAS_UPDATES,UPDATES_APPLIED,UPDATE_TASK_ID,BUDGET_CYCLE_ID,ACTIVITY_TYPE,ACTIVITY_DETAIL,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,empty_clob(),?,?,?,?,?,empty_clob(),?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update REPORT_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from REPORT_SEQ";
/*      */   protected static final String SQL_STORE = "update REPORT set USER_ID = ?,REPORT_TYPE = ?,TASK_ID = ?,COMPLETE = ?,HAS_UPDATES = ?,UPDATES_APPLIED = ?,UPDATE_TASK_ID = ?,BUDGET_CYCLE_ID = ?,ACTIVITY_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from REPORT where REPORT_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from REPORT where    REPORT_ID = ? ";
/*  769 */   protected static String SQL_ALL_REPORTS = "select 0       ,REPORT.REPORT_ID      ,REPORT.REPORT_TYPE      ,REPORT.TASK_ID from REPORT where 1=1  and  COMPLETE = 'Y' order by REPORT.REPORT_ID";
/*      */ 
/*  851 */   protected static String SQL_ALL_REPORTS_FOR_USER = "select 0       ,REPORT.REPORT_ID      ,REPORT.REPORT_ID      ,REPORT.REPORT_TYPE      ,REPORT.TASK_ID      ,REPORT.CREATED_TIME      ,REPORT.HAS_UPDATES      ,REPORT.UPDATES_APPLIED from REPORT where 1=1  and  COMPLETE = 'Y' and REPORT.USER_ID = ? order by (0 - REPORT.REPORT_ID)";
/*      */ 
/*  953 */   protected static String SQL_ALL_REPORTS_FOR_ADMIN = "select 0       ,REPORT.REPORT_ID      ,REPORT.REPORT_ID      ,REPORT.REPORT_TYPE      ,REPORT.TASK_ID      ,REPORT.CREATED_TIME      ,REPORT.USER_ID      ,REPORT.HAS_UPDATES      ,REPORT.UPDATES_APPLIED from REPORT where 1=1  and  COMPLETE = 'Y' order by (0 - REPORT.REPORT_ID)";
/*      */ 
/* 1054 */   protected static String SQL_WEB_REPORT_DETAILS = "select 0       ,REPORT.REPORT_ID      ,REPORT.REPORT_ID      ,REPORT.REPORT_TYPE      ,REPORT.TASK_ID      ,REPORT.CREATED_TIME      ,REPORT.HAS_UPDATES      ,REPORT.UPDATES_APPLIED from REPORT where 1=1  and  REPORT.REPORT_ID = ?";
/*      */ 
/* 1155 */   private static String[][] SQL_DELETE_CHILDREN = { { "CUBE_PENDING_TRAN", "delete from CUBE_PENDING_TRAN where     CUBE_PENDING_TRAN.REPORT_ID = ? " } };
/*      */ 
/* 1164 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/* 1168 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and REPORT.REPORT_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from REPORT where   REPORT_ID = ?";
/*      */   public static final String SQL_GET_CUBE_PENDING_TRAN_REF = "select 0,REPORT.REPORT_ID from CUBE_PENDING_TRAN,REPORT where 1=1 and CUBE_PENDING_TRAN.REPORT_ID = ? and CUBE_PENDING_TRAN.FINANCE_CUBE_ID = ? and CUBE_PENDING_TRAN.ROW_NO = ? and CUBE_PENDING_TRAN.REPORT_ID = REPORT.REPORT_ID";
/*      */   protected static final String SQL_CLOSE_REPORT = "update REPORT set COMPLETE = ? where REPORT_ID = ? ";
/*      */   private static final String QUERY_FINANCE_CUBE_ID_SQL = "select finance_cube_id from cube_pending_tran cpt where cpt.report_id = ? and rownum = 1";
/*      */   private static final String QUERT_RESP_AREA_IDS_FOR_REPORT = "select distinct dim0 from cube_pending_tran where report_id = ?";
/*      */   protected CubePendingTranDAO mCubePendingTranDAO;
/*      */   protected ReportEVO mDetails;
/*      */   private CLOB mReportTextClob;
/*      */   private CLOB mActivityDetailClob;
/*      */ 
/*      */   public ReportDAO(Connection connection)
/*      */   {
/*   46 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ReportDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ReportDAO(DataSource ds)
/*      */   {
/*   62 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ReportPK getPK()
/*      */   {
/*   70 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ReportEVO details)
/*      */   {
/*   79 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public ReportEVO setAndGetDetails(ReportEVO details, String dependants)
/*      */   {
/*   90 */     setDetails(details);
/*   91 */     generateKeys();
/*   92 */     getDependants(this.mDetails, dependants);
/*   93 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public ReportPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  102 */     doCreate();
/*      */ 
/*  104 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(ReportPK pk)
/*      */     throws ValidationException
/*      */   {
/*  114 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  123 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  132 */     doRemove();
/*      */   }
/*      */ 
/*      */   public ReportPK findByPrimaryKey(ReportPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  141 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  142 */     if (exists(pk_))
/*      */     {
/*  144 */       if (timer != null) {
/*  145 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  147 */       return pk_;
/*      */     }
/*      */ 
/*  150 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(ReportPK pk)
/*      */   {
/*  168 */     PreparedStatement stmt = null;
/*  169 */     ResultSet resultSet = null;
/*  170 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  174 */       stmt = getConnection().prepareStatement("select REPORT_ID from REPORT where    REPORT_ID = ? ");
/*      */ 
/*  176 */       int col = 1;
/*  177 */       stmt.setInt(col++, pk.getReportId());
/*      */ 
/*  179 */       resultSet = stmt.executeQuery();
/*      */ 
/*  181 */       if (!resultSet.next())
/*  182 */         returnValue = false;
/*      */       else
/*  184 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  188 */       throw handleSQLException(pk, "select REPORT_ID from REPORT where    REPORT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  192 */       closeResultSet(resultSet);
/*  193 */       closeStatement(stmt);
/*  194 */       closeConnection();
/*      */     }
/*  196 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private void selectLobs(ReportEVO evo_)
/*      */   {
/*  211 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  213 */     PreparedStatement stmt = null;
/*  214 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  218 */       stmt = getConnection().prepareStatement("select  REPORT_TEXT,ACTIVITY_DETAIL from REPORT where    REPORT_ID = ? for update");
/*      */ 
/*  220 */       putEvoKeysToJdbc(evo_, stmt, 1);
/*      */ 
/*  222 */       resultSet = stmt.executeQuery();
/*      */ 
/*  224 */       int col = 1;
/*  225 */       while (resultSet.next())
/*      */       {
/*  227 */         this.mReportTextClob = ((CLOB)resultSet.getClob(col++));
/*  228 */         this.mActivityDetailClob = ((CLOB)resultSet.getClob(col++));
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  233 */       throw handleSQLException(evo_.getPK(), "select  REPORT_TEXT,ACTIVITY_DETAIL from REPORT where    REPORT_ID = ? for update", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  237 */       closeResultSet(resultSet);
/*  238 */       closeStatement(stmt);
/*      */ 
/*  240 */       if (timer != null)
/*  241 */         timer.logDebug("selectLobs", evo_.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void putLobs(ReportEVO evo_) throws SQLException
/*      */   {
/*  247 */     updateClob(this.mReportTextClob, evo_.getReportText());
/*  248 */     updateClob(this.mActivityDetailClob, evo_.getActivityDetail());
/*      */   }
/*      */ 
/*      */   private ReportEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  273 */     int col = 1;
/*  274 */     this.mReportTextClob = ((CLOB)resultSet_.getClob(col++));
/*  275 */     this.mActivityDetailClob = ((CLOB)resultSet_.getClob(col++));
/*  276 */     ReportEVO evo = new ReportEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), clobToString(this.mReportTextClob), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), clobToString(this.mActivityDetailClob), resultSet_.getInt(col++), null);
/*      */ 
/*  293 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  294 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  295 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  296 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ReportEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  301 */     int col = startCol_;
/*  302 */     stmt_.setInt(col++, evo_.getReportId());
/*  303 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ReportEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  308 */     int col = startCol_;
/*  309 */     stmt_.setInt(col++, evo_.getUserId());
/*  310 */     stmt_.setInt(col++, evo_.getReportType());
/*  311 */     stmt_.setInt(col++, evo_.getTaskId());
/*  312 */     if (evo_.getComplete())
/*  313 */       stmt_.setString(col++, "Y");
/*      */     else {
/*  315 */       stmt_.setString(col++, " ");
/*      */     }
/*  317 */     if (evo_.getHasUpdates())
/*  318 */       stmt_.setString(col++, "Y");
/*      */     else
/*  320 */       stmt_.setString(col++, " ");
/*  321 */     if (evo_.getUpdatesApplied())
/*  322 */       stmt_.setString(col++, "Y");
/*      */     else
/*  324 */       stmt_.setString(col++, " ");
/*  325 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getUpdateTaskId());
/*  326 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getBudgetCycleId());
/*  327 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getActivityType());
/*      */ 
/*  329 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  330 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  331 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  332 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  333 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ReportPK pk)
/*      */     throws ValidationException
/*      */   {
/*  349 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  351 */     PreparedStatement stmt = null;
/*  352 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  356 */       stmt = getConnection().prepareStatement("select REPORT.REPORT_TEXT,REPORT.ACTIVITY_DETAIL,REPORT.REPORT_ID,REPORT.USER_ID,REPORT.REPORT_TYPE,REPORT.TASK_ID,REPORT.COMPLETE,REPORT.HAS_UPDATES,REPORT.UPDATES_APPLIED,REPORT.UPDATE_TASK_ID,REPORT.BUDGET_CYCLE_ID,REPORT.ACTIVITY_TYPE,REPORT.VERSION_NUM,REPORT.UPDATED_BY_USER_ID,REPORT.UPDATED_TIME,REPORT.CREATED_TIME from REPORT where    REPORT_ID = ? ");
/*      */ 
/*  359 */       int col = 1;
/*  360 */       stmt.setInt(col++, pk.getReportId());
/*      */ 
/*  362 */       resultSet = stmt.executeQuery();
/*      */ 
/*  364 */       if (!resultSet.next()) {
/*  365 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  368 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  369 */       if (this.mDetails.isModified())
/*  370 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  374 */       throw handleSQLException(pk, "select REPORT.REPORT_TEXT,REPORT.ACTIVITY_DETAIL,REPORT.REPORT_ID,REPORT.USER_ID,REPORT.REPORT_TYPE,REPORT.TASK_ID,REPORT.COMPLETE,REPORT.HAS_UPDATES,REPORT.UPDATES_APPLIED,REPORT.UPDATE_TASK_ID,REPORT.BUDGET_CYCLE_ID,REPORT.ACTIVITY_TYPE,REPORT.VERSION_NUM,REPORT.UPDATED_BY_USER_ID,REPORT.UPDATED_TIME,REPORT.CREATED_TIME from REPORT where    REPORT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  378 */       closeResultSet(resultSet);
/*  379 */       closeStatement(stmt);
/*  380 */       closeConnection();
/*      */ 
/*  382 */       if (timer != null)
/*  383 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  432 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  433 */     generateKeys();
/*      */ 
/*  435 */     this.mDetails.postCreateInit();
/*      */ 
/*  437 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  442 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  443 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  444 */       stmt = getConnection().prepareStatement("insert into REPORT ( REPORT_ID,USER_ID,REPORT_TYPE,TASK_ID,COMPLETE,REPORT_TEXT,HAS_UPDATES,UPDATES_APPLIED,UPDATE_TASK_ID,BUDGET_CYCLE_ID,ACTIVITY_TYPE,ACTIVITY_DETAIL,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,empty_clob(),?,?,?,?,?,empty_clob(),?,?,?,?)");
/*      */ 
/*  447 */       int col = 1;
/*  448 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  449 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  452 */       int resultCount = stmt.executeUpdate();
/*  453 */       if (resultCount != 1)
/*      */       {
/*  455 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  460 */       selectLobs(this.mDetails);
/*  461 */       this._log.debug("doCreate", "calling putLobs");
/*  462 */       putLobs(this.mDetails);
/*      */ 
/*  464 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  468 */       throw handleSQLException(this.mDetails.getPK(), "insert into REPORT ( REPORT_ID,USER_ID,REPORT_TYPE,TASK_ID,COMPLETE,REPORT_TEXT,HAS_UPDATES,UPDATES_APPLIED,UPDATE_TASK_ID,BUDGET_CYCLE_ID,ACTIVITY_TYPE,ACTIVITY_DETAIL,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,empty_clob(),?,?,?,?,?,empty_clob(),?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  472 */       closeStatement(stmt);
/*  473 */       closeConnection();
/*      */ 
/*  475 */       if (timer != null) {
/*  476 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  482 */       getCubePendingTranDAO().update(this.mDetails.getCUBE_PENDING_TRANMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  488 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  508 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  510 */     PreparedStatement stmt = null;
/*  511 */     ResultSet resultSet = null;
/*  512 */     String sqlString = null;
/*      */     try
/*      */     {
/*  517 */       sqlString = "update REPORT_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  518 */       stmt = getConnection().prepareStatement("update REPORT_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  519 */       stmt.setInt(1, insertCount);
/*      */ 
/*  521 */       int resultCount = stmt.executeUpdate();
/*  522 */       if (resultCount != 1) {
/*  523 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  525 */       closeStatement(stmt);
/*      */ 
/*  528 */       sqlString = "select SEQ_NUM from REPORT_SEQ";
/*  529 */       stmt = getConnection().prepareStatement("select SEQ_NUM from REPORT_SEQ");
/*  530 */       resultSet = stmt.executeQuery();
/*  531 */       if (!resultSet.next())
/*  532 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  533 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  535 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  539 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  543 */       closeResultSet(resultSet);
/*  544 */       closeStatement(stmt);
/*  545 */       closeConnection();
/*      */ 
/*  547 */       if (timer != null)
/*  548 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  548 */     }
/*      */   }
/*      */ 
/*      */   public ReportPK generateKeys()
/*      */   {
/*  558 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  560 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  563 */     if (insertCount == 0) {
/*  564 */       return this.mDetails.getPK();
/*      */     }
/*  566 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  568 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  599 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  601 */     generateKeys();
/*      */ 
/*  606 */     PreparedStatement stmt = null;
/*      */ 
/*  608 */     boolean mainChanged = this.mDetails.isModified();
/*  609 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  613 */       if (getCubePendingTranDAO().update(this.mDetails.getCUBE_PENDING_TRANMap())) {
/*  614 */         dependantChanged = true;
/*      */       }
/*  616 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  619 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  622 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  623 */         stmt = getConnection().prepareStatement("update REPORT set USER_ID = ?,REPORT_TYPE = ?,TASK_ID = ?,COMPLETE = ?,HAS_UPDATES = ?,UPDATES_APPLIED = ?,UPDATE_TASK_ID = ?,BUDGET_CYCLE_ID = ?,ACTIVITY_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  625 */         selectLobs(this.mDetails);
/*  626 */         putLobs(this.mDetails);
/*      */ 
/*  629 */         int col = 1;
/*  630 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  631 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  633 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  636 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  638 */         if (resultCount == 0) {
/*  639 */           checkVersionNum();
/*      */         }
/*  641 */         if (resultCount != 1) {
/*  642 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  645 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  654 */       throw handleSQLException(getPK(), "update REPORT set USER_ID = ?,REPORT_TYPE = ?,TASK_ID = ?,COMPLETE = ?,HAS_UPDATES = ?,UPDATES_APPLIED = ?,UPDATE_TASK_ID = ?,BUDGET_CYCLE_ID = ?,ACTIVITY_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  658 */       closeStatement(stmt);
/*  659 */       closeConnection();
/*      */ 
/*  661 */       if ((timer != null) && (
/*  662 */         (mainChanged) || (dependantChanged)))
/*  663 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  675 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  676 */     PreparedStatement stmt = null;
/*  677 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  681 */       stmt = getConnection().prepareStatement("select VERSION_NUM from REPORT where REPORT_ID = ?");
/*      */ 
/*  684 */       int col = 1;
/*  685 */       stmt.setInt(col++, this.mDetails.getReportId());
/*      */ 
/*  688 */       resultSet = stmt.executeQuery();
/*      */ 
/*  690 */       if (!resultSet.next()) {
/*  691 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  694 */       col = 1;
/*  695 */       int dbVersionNumber = resultSet.getInt(col++);
/*  696 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  697 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  703 */       throw handleSQLException(getPK(), "select VERSION_NUM from REPORT where REPORT_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  707 */       closeStatement(stmt);
/*  708 */       closeResultSet(resultSet);
/*      */ 
/*  710 */       if (timer != null)
/*  711 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  728 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  729 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  734 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  739 */       stmt = getConnection().prepareStatement("delete from REPORT where    REPORT_ID = ? ");
/*      */ 
/*  742 */       int col = 1;
/*  743 */       stmt.setInt(col++, this.mDetails.getReportId());
/*      */ 
/*  745 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  747 */       if (resultCount != 1) {
/*  748 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  754 */       throw handleSQLException(getPK(), "delete from REPORT where    REPORT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  758 */       closeStatement(stmt);
/*  759 */       closeConnection();
/*      */ 
/*  761 */       if (timer != null)
/*  762 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllReportsELO getAllReports()
/*      */   {
/*  792 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  793 */     PreparedStatement stmt = null;
/*  794 */     ResultSet resultSet = null;
/*  795 */     AllReportsELO results = new AllReportsELO();
/*      */     try
/*      */     {
/*  798 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORTS);
/*  799 */       int col = 1;
/*  800 */       resultSet = stmt.executeQuery();
/*  801 */       while (resultSet.next())
/*      */       {
/*  803 */         col = 2;
/*      */ 
/*  806 */         ReportPK pkReport = new ReportPK(resultSet.getInt(col++));
/*      */ 
/*  809 */         String textReport = "";
/*      */ 
/*  813 */         ReportRefImpl erReport = new ReportRefImpl(pkReport, textReport);
/*      */ 
/*  818 */         int col1 = resultSet.getInt(col++);
/*  819 */         int col2 = resultSet.getInt(col++);
/*      */ 
/*  822 */         results.add(erReport, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  831 */       throw handleSQLException(SQL_ALL_REPORTS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  835 */       closeResultSet(resultSet);
/*  836 */       closeStatement(stmt);
/*  837 */       closeConnection();
/*      */     }
/*      */ 
/*  840 */     if (timer != null) {
/*  841 */       timer.logDebug("getAllReports", " items=" + results.size());
/*      */     }
/*      */ 
/*  845 */     return results;
/*      */   }
/*      */ 
/*      */   public AllReportsForUserELO getAllReportsForUser(int param1)
/*      */   {
/*  880 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  881 */     PreparedStatement stmt = null;
/*  882 */     ResultSet resultSet = null;
/*  883 */     AllReportsForUserELO results = new AllReportsForUserELO();
/*      */     try
/*      */     {
/*  886 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORTS_FOR_USER);
/*  887 */       int col = 1;
/*  888 */       stmt.setInt(col++, param1);
/*  889 */       resultSet = stmt.executeQuery();
/*  890 */       while (resultSet.next())
/*      */       {
/*  892 */         col = 2;
/*      */ 
/*  895 */         ReportPK pkReport = new ReportPK(resultSet.getInt(col++));
/*      */ 
/*  898 */         String textReport = "";
/*      */ 
/*  902 */         ReportRefImpl erReport = new ReportRefImpl(pkReport, textReport);
/*      */ 
/*  907 */         int col1 = resultSet.getInt(col++);
/*  908 */         int col2 = resultSet.getInt(col++);
/*  909 */         int col3 = resultSet.getInt(col++);
/*  910 */         Timestamp col4 = resultSet.getTimestamp(col++);
/*  911 */         String col5 = resultSet.getString(col++);
/*  912 */         if (resultSet.wasNull())
/*  913 */           col5 = "";
/*  914 */         String col6 = resultSet.getString(col++);
/*  915 */         if (resultSet.wasNull()) {
/*  916 */           col6 = "";
/*      */         }
/*      */ 
/*  919 */         results.add(erReport, col1, col2, col3, col4, col5.equals("Y"), col6.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  932 */       throw handleSQLException(SQL_ALL_REPORTS_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  936 */       closeResultSet(resultSet);
/*  937 */       closeStatement(stmt);
/*  938 */       closeConnection();
/*      */     }
/*      */ 
/*  941 */     if (timer != null) {
/*  942 */       timer.logDebug("getAllReportsForUser", " UserId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  947 */     return results;
/*      */   }
/*      */ 
/*      */   public AllReportsForAdminELO getAllReportsForAdmin()
/*      */   {
/*  981 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  982 */     PreparedStatement stmt = null;
/*  983 */     ResultSet resultSet = null;
/*  984 */     AllReportsForAdminELO results = new AllReportsForAdminELO();
/*      */     try
/*      */     {
/*  987 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORTS_FOR_ADMIN);
/*  988 */       int col = 1;
/*  989 */       resultSet = stmt.executeQuery();
/*  990 */       while (resultSet.next())
/*      */       {
/*  992 */         col = 2;
/*      */ 
/*  995 */         ReportPK pkReport = new ReportPK(resultSet.getInt(col++));
/*      */ 
/*  998 */         String textReport = "";
/*      */ 
/* 1002 */         ReportRefImpl erReport = new ReportRefImpl(pkReport, textReport);
/*      */ 
/* 1007 */         int col1 = resultSet.getInt(col++);
/* 1008 */         int col2 = resultSet.getInt(col++);
/* 1009 */         int col3 = resultSet.getInt(col++);
/* 1010 */         Timestamp col4 = resultSet.getTimestamp(col++);
/* 1011 */         int col5 = resultSet.getInt(col++);
/* 1012 */         String col6 = resultSet.getString(col++);
/* 1013 */         if (resultSet.wasNull())
/* 1014 */           col6 = "";
/* 1015 */         String col7 = resultSet.getString(col++);
/* 1016 */         if (resultSet.wasNull()) {
/* 1017 */           col7 = "";
/*      */         }
/*      */ 
/* 1020 */         results.add(erReport, col1, col2, col3, col4, col5, col6.equals("Y"), col7.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1034 */       throw handleSQLException(SQL_ALL_REPORTS_FOR_ADMIN, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1038 */       closeResultSet(resultSet);
/* 1039 */       closeStatement(stmt);
/* 1040 */       closeConnection();
/*      */     }
/*      */ 
/* 1043 */     if (timer != null) {
/* 1044 */       timer.logDebug("getAllReportsForAdmin", " items=" + results.size());
/*      */     }
/*      */ 
/* 1048 */     return results;
/*      */   }
/*      */ 
/*      */   public WebReportDetailsELO getWebReportDetails(int param1)
/*      */   {
/* 1083 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1084 */     PreparedStatement stmt = null;
/* 1085 */     ResultSet resultSet = null;
/* 1086 */     WebReportDetailsELO results = new WebReportDetailsELO();
/*      */     try
/*      */     {
/* 1089 */       stmt = getConnection().prepareStatement(SQL_WEB_REPORT_DETAILS);
/* 1090 */       int col = 1;
/* 1091 */       stmt.setInt(col++, param1);
/* 1092 */       resultSet = stmt.executeQuery();
/* 1093 */       while (resultSet.next())
/*      */       {
/* 1095 */         col = 2;
/*      */ 
/* 1098 */         ReportPK pkReport = new ReportPK(resultSet.getInt(col++));
/*      */ 
/* 1101 */         String textReport = "";
/*      */ 
/* 1105 */         ReportRefImpl erReport = new ReportRefImpl(pkReport, textReport);
/*      */ 
/* 1110 */         int col1 = resultSet.getInt(col++);
/* 1111 */         int col2 = resultSet.getInt(col++);
/* 1112 */         int col3 = resultSet.getInt(col++);
/* 1113 */         Timestamp col4 = resultSet.getTimestamp(col++);
/* 1114 */         String col5 = resultSet.getString(col++);
/* 1115 */         if (resultSet.wasNull())
/* 1116 */           col5 = "";
/* 1117 */         String col6 = resultSet.getString(col++);
/* 1118 */         if (resultSet.wasNull()) {
/* 1119 */           col6 = "";
/*      */         }
/*      */ 
/* 1122 */         results.add(erReport, col1, col2, col3, col4, col5.equals("Y"), col6.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1135 */       throw handleSQLException(SQL_WEB_REPORT_DETAILS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1139 */       closeResultSet(resultSet);
/* 1140 */       closeStatement(stmt);
/* 1141 */       closeConnection();
/*      */     }
/*      */ 
/* 1144 */     if (timer != null) {
/* 1145 */       timer.logDebug("getWebReportDetails", " ReportId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1150 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ReportPK pk)
/*      */   {
/* 1177 */     Set emptyStrings = Collections.emptySet();
/* 1178 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ReportPK pk, Set<String> exclusionTables)
/*      */   {
/* 1184 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1186 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1188 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1190 */       PreparedStatement stmt = null;
/*      */ 
/* 1192 */       int resultCount = 0;
/* 1193 */       String s = null;
/*      */       try
/*      */       {
/* 1196 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 1198 */         if (this._log.isDebugEnabled()) {
/* 1199 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1201 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1204 */         int col = 1;
/* 1205 */         stmt.setInt(col++, pk.getReportId());
/*      */ 
/* 1208 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1212 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1216 */         closeStatement(stmt);
/* 1217 */         closeConnection();
/*      */ 
/* 1219 */         if (timer != null) {
/* 1220 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1224 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1226 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1228 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1230 */       PreparedStatement stmt = null;
/*      */ 
/* 1232 */       int resultCount = 0;
/* 1233 */       String s = null;
/*      */       try
/*      */       {
/* 1236 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1238 */         if (this._log.isDebugEnabled()) {
/* 1239 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1241 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1244 */         int col = 1;
/* 1245 */         stmt.setInt(col++, pk.getReportId());
/*      */ 
/* 1248 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1252 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1256 */         closeStatement(stmt);
/* 1257 */         closeConnection();
/*      */ 
/* 1259 */         if (timer != null)
/* 1260 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public ReportEVO getDetails(ReportPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1279 */     return getDetails(new ReportCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public ReportEVO getDetails(ReportCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1296 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1299 */     if (this.mDetails == null) {
/* 1300 */       doLoad(paramCK.getReportPK());
/*      */     }
/* 1302 */     else if (!this.mDetails.getPK().equals(paramCK.getReportPK())) {
/* 1303 */       doLoad(paramCK.getReportPK());
/*      */     }
/* 1305 */     else if (!checkIfValid())
/*      */     {
/* 1307 */       this._log.info("getDetails", "[ALERT] ReportEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1309 */       doLoad(paramCK.getReportPK());
/*      */     }
/*      */ 
/* 1319 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isCUBE_PENDING_TRANAllItemsLoaded()))
/*      */     {
/* 1324 */       this.mDetails.setCUBE_PENDING_TRAN(getCubePendingTranDAO().getAll(this.mDetails.getReportId(), dependants, this.mDetails.getCUBE_PENDING_TRAN()));
/*      */ 
/* 1331 */       this.mDetails.setCUBE_PENDING_TRANAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1334 */     if ((paramCK instanceof CubePendingTranCK))
/*      */     {
/* 1336 */       if (this.mDetails.getCUBE_PENDING_TRAN() == null) {
/* 1337 */         this.mDetails.loadCUBE_PENDING_TRANItem(getCubePendingTranDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1340 */         CubePendingTranPK pk = ((CubePendingTranCK)paramCK).getCubePendingTranPK();
/* 1341 */         CubePendingTranEVO evo = this.mDetails.getCUBE_PENDING_TRANItem(pk);
/* 1342 */         if (evo == null) {
/* 1343 */           this.mDetails.loadCUBE_PENDING_TRANItem(getCubePendingTranDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1348 */     ReportEVO details = new ReportEVO();
/* 1349 */     details = this.mDetails.deepClone();
/*      */ 
/* 1351 */     if (timer != null) {
/* 1352 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1354 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1364 */     boolean stillValid = false;
/* 1365 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1366 */     PreparedStatement stmt = null;
/* 1367 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1370 */       stmt = getConnection().prepareStatement("select VERSION_NUM from REPORT where   REPORT_ID = ?");
/* 1371 */       int col = 1;
/* 1372 */       stmt.setInt(col++, this.mDetails.getReportId());
/*      */ 
/* 1374 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1376 */       if (!resultSet.next()) {
/* 1377 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1379 */       col = 1;
/* 1380 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1382 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1383 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1387 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from REPORT where   REPORT_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1391 */       closeResultSet(resultSet);
/* 1392 */       closeStatement(stmt);
/* 1393 */       closeConnection();
/*      */ 
/* 1395 */       if (timer != null) {
/* 1396 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1399 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public ReportEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1405 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1407 */     if (!checkIfValid())
/*      */     {
/* 1409 */       this._log.info("getDetails", "Report " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1410 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1414 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1417 */     ReportEVO details = this.mDetails.deepClone();
/*      */ 
/* 1419 */     if (timer != null) {
/* 1420 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1422 */     return details;
/*      */   }
/*      */ 
/*      */   protected CubePendingTranDAO getCubePendingTranDAO()
/*      */   {
/* 1431 */     if (this.mCubePendingTranDAO == null)
/*      */     {
/* 1433 */       if (this.mDataSource != null)
/* 1434 */         this.mCubePendingTranDAO = new CubePendingTranDAO(this.mDataSource);
/*      */       else {
/* 1436 */         this.mCubePendingTranDAO = new CubePendingTranDAO(getConnection());
/*      */       }
/*      */     }
/* 1439 */     return this.mCubePendingTranDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1444 */     return "Report";
/*      */   }
/*      */ 
/*      */   public ReportRef getRef(ReportPK paramReportPK)
/*      */     throws ValidationException
/*      */   {
/* 1450 */     ReportEVO evo = getDetails(paramReportPK, "");
/* 1451 */     return evo.getEntityRef("");
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1478 */     if (c == null)
/* 1479 */       return;
/* 1480 */     Iterator iter = c.iterator();
/* 1481 */     while (iter.hasNext())
/*      */     {
/* 1483 */       ReportEVO evo = (ReportEVO)iter.next();
/* 1484 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(ReportEVO evo, String dependants)
/*      */   {
/* 1498 */     if (evo.getReportId() < 1) {
/* 1499 */       return;
/*      */     }
/*      */ 
/* 1507 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1510 */       if (!evo.isCUBE_PENDING_TRANAllItemsLoaded())
/*      */       {
/* 1512 */         evo.setCUBE_PENDING_TRAN(getCubePendingTranDAO().getAll(evo.getReportId(), dependants, evo.getCUBE_PENDING_TRAN()));
/*      */ 
/* 1519 */         evo.setCUBE_PENDING_TRANAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Reader getReportTextReader(int reportId)
/*      */   {
/* 1529 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1531 */     PreparedStatement stmt = null;
/* 1532 */     ResultSet resultSet = null;
/*      */ 
/* 1534 */     long textLength = 0L;
/*      */     try
/*      */     {
/* 1537 */       stmt = getConnection().prepareStatement("select  REPORT_TEXT,ACTIVITY_DETAIL from REPORT where    REPORT_ID = ? for update");
/* 1538 */       stmt.setInt(1, reportId);
/* 1539 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1541 */       while (resultSet.next())
/*      */       {
/* 1543 */         this.mReportTextClob = ((CLOB)resultSet.getClob(1));
/*      */       }
/*      */ 
/* 1546 */       textLength = this.mReportTextClob.length();
/* 1547 */       Reader localReader = this.mReportTextClob.getCharacterStream();
/*      */       return localReader;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1551 */       throw handleSQLException(new ReportPK(reportId), "select  REPORT_TEXT,ACTIVITY_DETAIL from REPORT where    REPORT_ID = ? for update", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1555 */       closeResultSet(resultSet);
/* 1556 */       closeStatement(stmt);
/* 1557 */       closeConnection();
/*      */ 
/* 1559 */       if (timer != null)
/* 1560 */         timer.logDebug("getReportTextReader", new ReportPK(reportId) + " length=" + textLength); 
/* 1560 */     }
/*      */   }
/*      */ 
/*      */   public long updateReportText(int reportId, String text)
/*      */   {
/* 1567 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1569 */     PreparedStatement stmt = null;
/* 1570 */     ResultSet resultSet = null;
/*      */ 
/* 1572 */     long textLength = 0L;
/*      */     try
/*      */     {
/* 1575 */       stmt = getConnection().prepareStatement("select  REPORT_TEXT,ACTIVITY_DETAIL from REPORT where    REPORT_ID = ? for update");
/* 1576 */       stmt.setInt(1, reportId);
/* 1577 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1579 */       while (resultSet.next())
/*      */       {
/* 1581 */         this.mReportTextClob = ((CLOB)resultSet.getClob(1));
/*      */       }
/*      */ 
/* 1585 */       textLength = this.mReportTextClob.length();
/* 1586 */       BufferedWriter br = new BufferedWriter(this.mReportTextClob.getCharacterOutputStream(textLength + 1L));
/* 1587 */       textLength += text.length();
/*      */       try
/*      */       {
/* 1590 */         br.write(text);
/*      */       }
/*      */       catch (IOException e)
/*      */       {
/* 1594 */         throw new SQLException(e.getMessage());
/*      */       }
/*      */       finally
/*      */       {
/*      */         try
/*      */         {
/* 1600 */           br.flush();
/* 1601 */           br.close();
/*      */         }
/*      */         catch (IOException e)
/*      */         {
/* 1605 */           throw new SQLException(e.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1611 */       throw handleSQLException(new ReportPK(reportId), "select  REPORT_TEXT,ACTIVITY_DETAIL from REPORT where    REPORT_ID = ? for update", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1615 */       closeResultSet(resultSet);
/* 1616 */       closeStatement(stmt);
/* 1617 */       closeConnection();
/*      */ 
/* 1619 */       if (timer != null) {
/* 1620 */         timer.logDebug("updateReportText", new ReportPK(reportId) + " new length=" + textLength);
/*      */       }
/*      */     }
/* 1623 */     return textLength;
/*      */   }
/*      */ 
/*      */   public void closeReport(int reportId)
/*      */   {
/* 1631 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1632 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/* 1635 */       stmt = getConnection().prepareStatement("update REPORT set COMPLETE = ? where REPORT_ID = ? ");
/*      */ 
/* 1637 */       stmt.setString(1, "Y");
/* 1638 */       stmt.setInt(2, reportId);
/*      */ 
/* 1641 */       int resultCount = stmt.executeUpdate();
/*      */ 
/* 1643 */       if (resultCount != 1)
/* 1644 */         throw new RuntimeException(getEntityName() + " update failed (" + new ReportPK(reportId) + "): resultCount=" + resultCount);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1648 */       throw handleSQLException(new ReportPK(reportId), "update REPORT set COMPLETE = ? where REPORT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1652 */       closeStatement(stmt);
/* 1653 */       closeConnection();
/*      */ 
/* 1655 */       if (timer != null)
/* 1656 */         timer.logDebug("closeReport", new ReportPK(reportId));
/*      */     }
/*      */   }
/*      */ 
/*      */   public int queryFinanceCubeId(int reportId)
/*      */     throws ValidationException
/*      */   {
/* 1671 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1672 */     PreparedStatement stmt = null;
/* 1673 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1676 */       stmt = getConnection().prepareStatement("select finance_cube_id from cube_pending_tran cpt where cpt.report_id = ? and rownum = 1");
/*      */ 
/* 1678 */       stmt.setInt(1, reportId);
/*      */ 
/* 1680 */       rs = stmt.executeQuery();
/*      */ 
/* 1682 */       if (rs.next()) {
/* 1683 */         int i = rs.getInt(1);
/*      */         return i;
/*      */       }
/* 1685 */       throw new ValidationException("Failed to locate any update rows for report:" + reportId);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1689 */       throw handleSQLException(new ReportPK(reportId), "select finance_cube_id from cube_pending_tran cpt where cpt.report_id = ? and rownum = 1", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1693 */       closeResultSet(rs);
/* 1694 */       closeStatement(stmt);
/* 1695 */       closeConnection();
/* 1696 */       if (timer != null)
/* 1697 */         timer.logDebug("queryFinanceCubeId", new ReportPK(reportId)); 
/* 1697 */     }
/*      */   }
/*      */ 
/*      */   public BudgetActivityEVO createBudgetActvity(int modelId, int financeCubeId, int reportId, String userId)
/*      */     throws ValidationException
/*      */   {
/* 1707 */     ReportEVO reportEVO = new ReportDAO().getDetails(new ReportPK(reportId), "");
/*      */ 
/* 1710 */     Boolean undo_entry = !reportEVO.getUpdatesApplied() ? Boolean.TRUE : Boolean.FALSE;
/*      */ 
/* 1712 */     BudgetActivityEVO baEVO = new BudgetActivityEVO(-1, modelId, userId, new Timestamp(System.currentTimeMillis()), reportEVO.getActivityType().intValue(), "Do/Undo/Redo from report update", reportEVO.getActivityDetail(), undo_entry, -1, null);
/*      */ 
/* 1720 */     ResultSet rs = null;
/* 1721 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/* 1724 */       ps = getConnection().prepareStatement("select distinct dim0 from cube_pending_tran where report_id = ?");
/* 1725 */       ps.setInt(1, reportId);
/* 1726 */       rs = ps.executeQuery();
/*      */ 
/* 1728 */       while (rs.next())
/*      */       {
/* 1730 */         int deId = rs.getInt(1);
/* 1731 */         baEVO.addActivityLinksItem(new BudgetActivityLinkEVO(-1, deId, reportEVO.getBudgetCycleId()));
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1736 */       throw handleSQLException("select finance_cube_id from cube_pending_tran cpt where cpt.report_id = ? and rownum = 1", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1740 */       closeResultSet(rs);
/* 1741 */       closeStatement(ps);
/*      */     }
/*      */ 
/* 1744 */     return baEVO;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.ReportDAO
 * JD-Core Version:    0.6.0
 */