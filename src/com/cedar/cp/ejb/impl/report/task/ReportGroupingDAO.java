/*      */ package com.cedar.cp.ejb.impl.report.task;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.report.task.ReportGroupingRef;
/*      */ import com.cedar.cp.dto.report.task.ReportGroupingCK;
/*      */ import com.cedar.cp.dto.report.task.ReportGroupingFileCK;
/*      */ import com.cedar.cp.dto.report.task.ReportGroupingFilePK;
/*      */ import com.cedar.cp.dto.report.task.ReportGroupingPK;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.SqlBuilder;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.CallableStatement;
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
/*      */ public class ReportGroupingDAO extends AbstractDAO
/*      */ {
/*   33 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select REPORT_GROUPING_ID from REPORT_GROUPING where    REPORT_GROUPING_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select REPORT_GROUPING.REPORT_GROUPING_ID,REPORT_GROUPING.PARENT_TASK_ID,REPORT_GROUPING.TASK_ID,REPORT_GROUPING.DISTRIBUTION_TYPE,REPORT_GROUPING.MESSAGE_TYPE,REPORT_GROUPING.MESSAGE_ID,REPORT_GROUPING.UPDATED_BY_USER_ID,REPORT_GROUPING.UPDATED_TIME,REPORT_GROUPING.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from REPORT_GROUPING where    REPORT_GROUPING_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into REPORT_GROUPING ( REPORT_GROUPING_ID,PARENT_TASK_ID,TASK_ID,DISTRIBUTION_TYPE,MESSAGE_TYPE,MESSAGE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update REPORT_GROUPING_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from REPORT_GROUPING_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_FULLKEY = "select count(*) from REPORT_GROUPING where    TASK_ID = ? AND DISTRIBUTION_TYPE = ? AND MESSAGE_TYPE = ? AND MESSAGE_ID = ? and not(    REPORT_GROUPING_ID = ? )";
/*      */   protected static final String SQL_STORE = "update REPORT_GROUPING set PARENT_TASK_ID = ?,TASK_ID = ?,DISTRIBUTION_TYPE = ?,MESSAGE_TYPE = ?,MESSAGE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_GROUPING_ID = ? ";
/*      */   protected static final String SQL_REMOVE = "delete from REPORT_GROUPING where    REPORT_GROUPING_ID = ? ";
/*  661 */   private static String[][] SQL_DELETE_CHILDREN = { { "REPORT_GROUPING_FILE", "delete from REPORT_GROUPING_FILE where     REPORT_GROUPING_FILE.REPORT_GROUPING_ID = ? " } };
/*      */ 
/*  670 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  674 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and REPORT_GROUPING.REPORT_GROUPING_ID = ?)";
/*      */   public static final String SQL_GET_REPORT_GROUPING_FILE_REF = "select 0,REPORT_GROUPING.REPORT_GROUPING_ID from REPORT_GROUPING_FILE,REPORT_GROUPING where 1=1 and REPORT_GROUPING_FILE.REPORT_GROUPING_ID = ? and REPORT_GROUPING_FILE.REPORT_GROUPING_FILE_ID = ? and REPORT_GROUPING_FILE.REPORT_GROUPING_ID = REPORT_GROUPING.REPORT_GROUPING_ID";
/*      */   public static final String SQL_DELETE_ALL_FOR_TASK_1 = "delete from REPORT_GROUPING_FILE where REPORT_GROUPING_ID in (select REPORT_GROUPING_ID from REPORT_GROUPING where PARENT_TASK_ID = ? )";
/*      */   public static final String SQL_DELETE_ALL_FOR_TASK_2 = "delete from REPORT_GROUPING where PARENT_TASK_ID = ?";
/*      */   public static final String SQL_GET_GROUPING_DATA = "with init as\n(\nselect distinct parent_task_id, distribution_type, message_type, message_id\nfrom report_grouping\njoin report_grouping_file using (report_grouping_id)\nwhere parent_task_id = <parent_task_id> \n)\nselect  distribution_type, message_type, message_id\n        ,cursor\n        (\n        select  file_name, file_data\n        from    report_grouping\n        join    report_grouping_file using (report_grouping_id)\n        where   parent_task_id = init.parent_task_id\n        and     message_id = init.message_id\n        ) as data\nfrom init";
/*      */   protected ReportGroupingFileDAO mReportGroupingFileDAO;
/*      */   protected ReportGroupingEVO mDetails;
/*      */ 
/*      */   public ReportGroupingDAO(Connection connection)
/*      */   {
/*   40 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ReportGroupingDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ReportGroupingDAO(DataSource ds)
/*      */   {
/*   56 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ReportGroupingPK getPK()
/*      */   {
/*   64 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ReportGroupingEVO details)
/*      */   {
/*   73 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public ReportGroupingEVO setAndGetDetails(ReportGroupingEVO details, String dependants)
/*      */   {
/*   84 */     setDetails(details);
/*   85 */     generateKeys();
/*   86 */     getDependants(this.mDetails, dependants);
/*   87 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public ReportGroupingPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   96 */     doCreate();
/*      */ 
/*   98 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(ReportGroupingPK pk)
/*      */     throws ValidationException
/*      */   {
/*  108 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  117 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  126 */     doRemove();
/*      */   }
/*      */ 
/*      */   public ReportGroupingPK findByPrimaryKey(ReportGroupingPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  135 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  136 */     if (exists(pk_))
/*      */     {
/*  138 */       if (timer != null) {
/*  139 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  141 */       return pk_;
/*      */     }
/*      */ 
/*  144 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(ReportGroupingPK pk)
/*      */   {
/*  162 */     PreparedStatement stmt = null;
/*  163 */     ResultSet resultSet = null;
/*  164 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  168 */       stmt = getConnection().prepareStatement("select REPORT_GROUPING_ID from REPORT_GROUPING where    REPORT_GROUPING_ID = ? ");
/*      */ 
/*  170 */       int col = 1;
/*  171 */       stmt.setInt(col++, pk.getReportGroupingId());
/*      */ 
/*  173 */       resultSet = stmt.executeQuery();
/*      */ 
/*  175 */       if (!resultSet.next())
/*  176 */         returnValue = false;
/*      */       else
/*  178 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  182 */       throw handleSQLException(pk, "select REPORT_GROUPING_ID from REPORT_GROUPING where    REPORT_GROUPING_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  186 */       closeResultSet(resultSet);
/*  187 */       closeStatement(stmt);
/*  188 */       closeConnection();
/*      */     }
/*  190 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private ReportGroupingEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  210 */     int col = 1;
/*  211 */     ReportGroupingEVO evo = new ReportGroupingEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), null);
/*      */ 
/*  221 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  222 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  223 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  224 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ReportGroupingEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  229 */     int col = startCol_;
/*  230 */     stmt_.setInt(col++, evo_.getReportGroupingId());
/*  231 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ReportGroupingEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  236 */     int col = startCol_;
/*  237 */     stmt_.setInt(col++, evo_.getParentTaskId());
/*  238 */     stmt_.setInt(col++, evo_.getTaskId());
/*  239 */     stmt_.setInt(col++, evo_.getDistributionType());
/*  240 */     stmt_.setInt(col++, evo_.getMessageType());
/*  241 */     stmt_.setString(col++, evo_.getMessageId());
/*  242 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  243 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  244 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  245 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ReportGroupingPK pk)
/*      */     throws ValidationException
/*      */   {
/*  261 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  263 */     PreparedStatement stmt = null;
/*  264 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  268 */       stmt = getConnection().prepareStatement("select REPORT_GROUPING.REPORT_GROUPING_ID,REPORT_GROUPING.PARENT_TASK_ID,REPORT_GROUPING.TASK_ID,REPORT_GROUPING.DISTRIBUTION_TYPE,REPORT_GROUPING.MESSAGE_TYPE,REPORT_GROUPING.MESSAGE_ID,REPORT_GROUPING.UPDATED_BY_USER_ID,REPORT_GROUPING.UPDATED_TIME,REPORT_GROUPING.CREATED_TIME from REPORT_GROUPING where    REPORT_GROUPING_ID = ? ");
/*      */ 
/*  271 */       int col = 1;
/*  272 */       stmt.setInt(col++, pk.getReportGroupingId());
/*      */ 
/*  274 */       resultSet = stmt.executeQuery();
/*      */ 
/*  276 */       if (!resultSet.next()) {
/*  277 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  280 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  281 */       if (this.mDetails.isModified())
/*  282 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  286 */       throw handleSQLException(pk, "select REPORT_GROUPING.REPORT_GROUPING_ID,REPORT_GROUPING.PARENT_TASK_ID,REPORT_GROUPING.TASK_ID,REPORT_GROUPING.DISTRIBUTION_TYPE,REPORT_GROUPING.MESSAGE_TYPE,REPORT_GROUPING.MESSAGE_ID,REPORT_GROUPING.UPDATED_BY_USER_ID,REPORT_GROUPING.UPDATED_TIME,REPORT_GROUPING.CREATED_TIME from REPORT_GROUPING where    REPORT_GROUPING_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  290 */       closeResultSet(resultSet);
/*  291 */       closeStatement(stmt);
/*  292 */       closeConnection();
/*      */ 
/*  294 */       if (timer != null)
/*  295 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  330 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  331 */     generateKeys();
/*      */ 
/*  333 */     this.mDetails.postCreateInit();
/*      */ 
/*  335 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  340 */       duplicateValueCheckFullKey();
/*      */ 
/*  342 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  343 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  344 */       stmt = getConnection().prepareStatement("insert into REPORT_GROUPING ( REPORT_GROUPING_ID,PARENT_TASK_ID,TASK_ID,DISTRIBUTION_TYPE,MESSAGE_TYPE,MESSAGE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  347 */       int col = 1;
/*  348 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  349 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  352 */       int resultCount = stmt.executeUpdate();
/*  353 */       if (resultCount != 1)
/*      */       {
/*  355 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  358 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  362 */       throw handleSQLException(this.mDetails.getPK(), "insert into REPORT_GROUPING ( REPORT_GROUPING_ID,PARENT_TASK_ID,TASK_ID,DISTRIBUTION_TYPE,MESSAGE_TYPE,MESSAGE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  366 */       closeStatement(stmt);
/*  367 */       closeConnection();
/*      */ 
/*  369 */       if (timer != null) {
/*  370 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  376 */       getReportGroupingFileDAO().update(this.mDetails.getReportGroupFilesMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  382 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  402 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  404 */     PreparedStatement stmt = null;
/*  405 */     ResultSet resultSet = null;
/*  406 */     String sqlString = null;
/*      */     try
/*      */     {
/*  411 */       sqlString = "update REPORT_GROUPING_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  412 */       stmt = getConnection().prepareStatement("update REPORT_GROUPING_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  413 */       stmt.setInt(1, insertCount);
/*      */ 
/*  415 */       int resultCount = stmt.executeUpdate();
/*  416 */       if (resultCount != 1) {
/*  417 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  419 */       closeStatement(stmt);
/*      */ 
/*  422 */       sqlString = "select SEQ_NUM from REPORT_GROUPING_SEQ";
/*  423 */       stmt = getConnection().prepareStatement("select SEQ_NUM from REPORT_GROUPING_SEQ");
/*  424 */       resultSet = stmt.executeQuery();
/*  425 */       if (!resultSet.next())
/*  426 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  427 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  429 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  433 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  437 */       closeResultSet(resultSet);
/*  438 */       closeStatement(stmt);
/*  439 */       closeConnection();
/*      */ 
/*  441 */       if (timer != null)
/*  442 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  442 */     }
/*      */   }
/*      */ 
/*      */   public ReportGroupingPK generateKeys()
/*      */   {
/*  452 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  454 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  457 */     if (insertCount == 0) {
/*  458 */       return this.mDetails.getPK();
/*      */     }
/*  460 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  462 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckFullKey()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  478 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  479 */     PreparedStatement stmt = null;
/*  480 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  484 */       stmt = getConnection().prepareStatement("select count(*) from REPORT_GROUPING where    TASK_ID = ? AND DISTRIBUTION_TYPE = ? AND MESSAGE_TYPE = ? AND MESSAGE_ID = ? and not(    REPORT_GROUPING_ID = ? )");
/*      */ 
/*  487 */       int col = 1;
/*  488 */       stmt.setInt(col++, this.mDetails.getTaskId());
/*  489 */       stmt.setInt(col++, this.mDetails.getDistributionType());
/*  490 */       stmt.setInt(col++, this.mDetails.getMessageType());
/*  491 */       stmt.setString(col++, this.mDetails.getMessageId());
/*  492 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  495 */       resultSet = stmt.executeQuery();
/*      */ 
/*  497 */       if (!resultSet.next()) {
/*  498 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  502 */       col = 1;
/*  503 */       int count = resultSet.getInt(col++);
/*  504 */       if (count > 0) {
/*  505 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " FullKey");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  511 */       throw handleSQLException(getPK(), "select count(*) from REPORT_GROUPING where    TASK_ID = ? AND DISTRIBUTION_TYPE = ? AND MESSAGE_TYPE = ? AND MESSAGE_ID = ? and not(    REPORT_GROUPING_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  515 */       closeResultSet(resultSet);
/*  516 */       closeStatement(stmt);
/*  517 */       closeConnection();
/*      */ 
/*  519 */       if (timer != null)
/*  520 */         timer.logDebug("duplicateValueCheckFullKey", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  546 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  548 */     generateKeys();
/*      */ 
/*  553 */     PreparedStatement stmt = null;
/*      */ 
/*  555 */     boolean mainChanged = this.mDetails.isModified();
/*  556 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  560 */       if (mainChanged) {
/*  561 */         duplicateValueCheckFullKey();
/*      */       }
/*  563 */       if (getReportGroupingFileDAO().update(this.mDetails.getReportGroupFilesMap())) {
/*  564 */         dependantChanged = true;
/*      */       }
/*  566 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  569 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  570 */         stmt = getConnection().prepareStatement("update REPORT_GROUPING set PARENT_TASK_ID = ?,TASK_ID = ?,DISTRIBUTION_TYPE = ?,MESSAGE_TYPE = ?,MESSAGE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_GROUPING_ID = ? ");
/*      */ 
/*  573 */         int col = 1;
/*  574 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  575 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  578 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  581 */         if (resultCount != 1) {
/*  582 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  585 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  594 */       throw handleSQLException(getPK(), "update REPORT_GROUPING set PARENT_TASK_ID = ?,TASK_ID = ?,DISTRIBUTION_TYPE = ?,MESSAGE_TYPE = ?,MESSAGE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_GROUPING_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  598 */       closeStatement(stmt);
/*  599 */       closeConnection();
/*      */ 
/*  601 */       if ((timer != null) && (
/*  602 */         (mainChanged) || (dependantChanged)))
/*  603 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  621 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  622 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  627 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  632 */       stmt = getConnection().prepareStatement("delete from REPORT_GROUPING where    REPORT_GROUPING_ID = ? ");
/*      */ 
/*  635 */       int col = 1;
/*  636 */       stmt.setInt(col++, this.mDetails.getReportGroupingId());
/*      */ 
/*  638 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  640 */       if (resultCount != 1) {
/*  641 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  647 */       throw handleSQLException(getPK(), "delete from REPORT_GROUPING where    REPORT_GROUPING_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  651 */       closeStatement(stmt);
/*  652 */       closeConnection();
/*      */ 
/*  654 */       if (timer != null)
/*  655 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ReportGroupingPK pk)
/*      */   {
/*  683 */     Set emptyStrings = Collections.emptySet();
/*  684 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ReportGroupingPK pk, Set<String> exclusionTables)
/*      */   {
/*  690 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  692 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  694 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  696 */       PreparedStatement stmt = null;
/*      */ 
/*  698 */       int resultCount = 0;
/*  699 */       String s = null;
/*      */       try
/*      */       {
/*  702 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  704 */         if (this._log.isDebugEnabled()) {
/*  705 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  707 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  710 */         int col = 1;
/*  711 */         stmt.setInt(col++, pk.getReportGroupingId());
/*      */ 
/*  714 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  718 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  722 */         closeStatement(stmt);
/*  723 */         closeConnection();
/*      */ 
/*  725 */         if (timer != null) {
/*  726 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  730 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  732 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  734 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  736 */       PreparedStatement stmt = null;
/*      */ 
/*  738 */       int resultCount = 0;
/*  739 */       String s = null;
/*      */       try
/*      */       {
/*  742 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  744 */         if (this._log.isDebugEnabled()) {
/*  745 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  747 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  750 */         int col = 1;
/*  751 */         stmt.setInt(col++, pk.getReportGroupingId());
/*      */ 
/*  754 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  758 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  762 */         closeStatement(stmt);
/*  763 */         closeConnection();
/*      */ 
/*  765 */         if (timer != null)
/*  766 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public ReportGroupingEVO getDetails(ReportGroupingPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  785 */     return getDetails(new ReportGroupingCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public ReportGroupingEVO getDetails(ReportGroupingCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  802 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  805 */     if (this.mDetails == null) {
/*  806 */       doLoad(paramCK.getReportGroupingPK());
/*      */     }
/*  808 */     else if (!this.mDetails.getPK().equals(paramCK.getReportGroupingPK())) {
/*  809 */       doLoad(paramCK.getReportGroupingPK());
/*      */     }
/*      */ 
/*  818 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isReportGroupFilesAllItemsLoaded()))
/*      */     {
/*  823 */       this.mDetails.setReportGroupFiles(getReportGroupingFileDAO().getAll(this.mDetails.getReportGroupingId(), dependants, this.mDetails.getReportGroupFiles()));
/*      */ 
/*  830 */       this.mDetails.setReportGroupFilesAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  833 */     if ((paramCK instanceof ReportGroupingFileCK))
/*      */     {
/*  835 */       if (this.mDetails.getReportGroupFiles() == null) {
/*  836 */         this.mDetails.loadReportGroupFilesItem(getReportGroupingFileDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  839 */         ReportGroupingFilePK pk = ((ReportGroupingFileCK)paramCK).getReportGroupingFilePK();
/*  840 */         ReportGroupingFileEVO evo = this.mDetails.getReportGroupFilesItem(pk);
/*  841 */         if (evo == null) {
/*  842 */           this.mDetails.loadReportGroupFilesItem(getReportGroupingFileDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  847 */     ReportGroupingEVO details = new ReportGroupingEVO();
/*  848 */     details = this.mDetails.deepClone();
/*      */ 
/*  850 */     if (timer != null) {
/*  851 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  853 */     return details;
/*      */   }
/*      */ 
/*      */   public ReportGroupingEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  859 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  863 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  866 */     ReportGroupingEVO details = this.mDetails.deepClone();
/*      */ 
/*  868 */     if (timer != null) {
/*  869 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  871 */     return details;
/*      */   }
/*      */ 
/*      */   protected ReportGroupingFileDAO getReportGroupingFileDAO()
/*      */   {
/*  880 */     if (this.mReportGroupingFileDAO == null)
/*      */     {
/*  882 */       if (this.mDataSource != null)
/*  883 */         this.mReportGroupingFileDAO = new ReportGroupingFileDAO(this.mDataSource);
/*      */       else {
/*  885 */         this.mReportGroupingFileDAO = new ReportGroupingFileDAO(getConnection());
/*      */       }
/*      */     }
/*  888 */     return this.mReportGroupingFileDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  893 */     return "ReportGrouping";
/*      */   }
/*      */ 
/*      */   public ReportGroupingRef getRef(ReportGroupingPK paramReportGroupingPK)
/*      */     throws ValidationException
/*      */   {
/*  899 */     ReportGroupingEVO evo = getDetails(paramReportGroupingPK, "");
/*  900 */     return evo.getEntityRef("");
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/*  926 */     if (c == null)
/*  927 */       return;
/*  928 */     Iterator iter = c.iterator();
/*  929 */     while (iter.hasNext())
/*      */     {
/*  931 */       ReportGroupingEVO evo = (ReportGroupingEVO)iter.next();
/*  932 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(ReportGroupingEVO evo, String dependants)
/*      */   {
/*  946 */     if (evo.getReportGroupingId() < 1) {
/*  947 */       return;
/*      */     }
/*      */ 
/*  955 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/*  958 */       if (!evo.isReportGroupFilesAllItemsLoaded())
/*      */       {
/*  960 */         evo.setReportGroupFiles(getReportGroupingFileDAO().getAll(evo.getReportGroupingId(), dependants, evo.getReportGroupFiles()));
/*      */ 
/*  967 */         evo.setReportGroupFilesAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteGroupings(int taskID)
/*      */   {
/*  983 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  984 */     CallableStatement stmt = null;
/*  985 */     PreparedStatement pstat = null;
/*      */     try
/*      */     {
/*  988 */       pstat = getConnection().prepareStatement("delete from REPORT_GROUPING_FILE where REPORT_GROUPING_ID in (select REPORT_GROUPING_ID from REPORT_GROUPING where PARENT_TASK_ID = ? )");
/*  989 */       pstat.setInt(1, taskID);
/*      */ 
/*  991 */       pstat.executeUpdate();
/*      */ 
/*  993 */       pstat = getConnection().prepareStatement("delete from REPORT_GROUPING where PARENT_TASK_ID = ?");
/*  994 */       pstat.setInt(1, taskID);
/*      */ 
/*  996 */       pstat.executeUpdate();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1000 */       throw handleSQLException("queryVisualIds", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1004 */       closeStatement(pstat);
/* 1005 */       closeStatement(stmt);
/* 1006 */       closeConnection();
/*      */     }
/*      */ 
/* 1009 */     if (timer != null)
/* 1010 */       timer.logDebug("deleteGroupings");
/*      */   }
/*      */ 
/*      */   public EntityList getGroupingData(int taskID)
/*      */   {
/* 1034 */     SqlBuilder sqlb = new SqlBuilder();
/* 1035 */     sqlb.addLines(new String[] { "with init as\n(\nselect distinct parent_task_id, distribution_type, message_type, message_id\nfrom report_grouping\njoin report_grouping_file using (report_grouping_id)\nwhere parent_task_id = <parent_task_id> \n)\nselect  distribution_type, message_type, message_id\n        ,cursor\n        (\n        select  file_name, file_data\n        from    report_grouping\n        join    report_grouping_file using (report_grouping_id)\n        where   parent_task_id = init.parent_task_id\n        and     message_id = init.message_id\n        ) as data\nfrom init" });
/*      */ 
/* 1037 */     SqlExecutor sqle = new SqlExecutor("getGroupingData(taskId)", getDataSource(), sqlb, this._log);
/* 1038 */     sqle.setLogSql(false);
/* 1039 */     sqle.addBindVariable("<parent_task_id>", Integer.valueOf(taskID));
/*      */ 
/* 1041 */     return sqle.getEntityList();
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.task.ReportGroupingDAO
 * JD-Core Version:    0.6.0
 */