/*      */ package com.cedar.cp.ejb.impl.admin.tidytask;
/*      */ 
/*      */ import com.cedar.cp.api.admin.tidytask.TidyTaskRef;
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.admin.tidytask.AllTidyTasksELO;
/*      */ import com.cedar.cp.dto.admin.tidytask.TidyTaskCK;
/*      */ import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkCK;
/*      */ import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkPK;
/*      */ import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
/*      */ import com.cedar.cp.dto.admin.tidytask.TidyTaskRefImpl;
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
/*      */ public class TidyTaskDAO extends AbstractDAO
/*      */ {
/*   32 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select TIDY_TASK_ID from TIDY_TASK where    TIDY_TASK_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select TIDY_TASK.TIDY_TASK_ID,TIDY_TASK.VIS_ID,TIDY_TASK.DESCRIPTION,TIDY_TASK.VERSION_NUM,TIDY_TASK.UPDATED_BY_USER_ID,TIDY_TASK.UPDATED_TIME,TIDY_TASK.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from TIDY_TASK where    TIDY_TASK_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into TIDY_TASK ( TIDY_TASK_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update TIDY_TASK_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from TIDY_TASK_SEQ";
/*      */   protected static final String SQL_STORE = "update TIDY_TASK set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TIDY_TASK_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from TIDY_TASK where TIDY_TASK_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from TIDY_TASK where    TIDY_TASK_ID = ? ";
/*  640 */   protected static String SQL_ALL_TIDY_TASKS = "select 0       ,TIDY_TASK.TIDY_TASK_ID      ,TIDY_TASK.VIS_ID      ,TIDY_TASK.DESCRIPTION from TIDY_TASK where 1=1 ";
/*      */ 
/*  719 */   private static String[][] SQL_DELETE_CHILDREN = { { "TIDY_TASK_LINK", "delete from TIDY_TASK_LINK where     TIDY_TASK_LINK.TIDY_TASK_ID = ? " } };
/*      */ 
/*  728 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  732 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and TIDY_TASK.TIDY_TASK_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from TIDY_TASK where   TIDY_TASK_ID = ?";
/*      */   public static final String SQL_GET_TIDY_TASK_LINK_REF = "select 0,TIDY_TASK.TIDY_TASK_ID from TIDY_TASK_LINK,TIDY_TASK where 1=1 and TIDY_TASK_LINK.TIDY_TASK_ID = ? and TIDY_TASK_LINK.TIDY_TASK_LINK_ID = ? and TIDY_TASK_LINK.TIDY_TASK_ID = TIDY_TASK.TIDY_TASK_ID";
/*      */   protected TidyTaskLinkDAO mTidyTaskLinkDAO;
/*      */   protected TidyTaskEVO mDetails;
/*      */ 
/*      */   public TidyTaskDAO(Connection connection)
/*      */   {
/*   39 */     super(connection);
/*      */   }
/*      */ 
/*      */   public TidyTaskDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public TidyTaskDAO(DataSource ds)
/*      */   {
/*   55 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected TidyTaskPK getPK()
/*      */   {
/*   63 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(TidyTaskEVO details)
/*      */   {
/*   72 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public TidyTaskEVO setAndGetDetails(TidyTaskEVO details, String dependants)
/*      */   {
/*   83 */     setDetails(details);
/*   84 */     generateKeys();
/*   85 */     getDependants(this.mDetails, dependants);
/*   86 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public TidyTaskPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   95 */     doCreate();
/*      */ 
/*   97 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(TidyTaskPK pk)
/*      */     throws ValidationException
/*      */   {
/*  107 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  116 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  125 */     doRemove();
/*      */   }
/*      */ 
/*      */   public TidyTaskPK findByPrimaryKey(TidyTaskPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  134 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  135 */     if (exists(pk_))
/*      */     {
/*  137 */       if (timer != null) {
/*  138 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  140 */       return pk_;
/*      */     }
/*      */ 
/*  143 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(TidyTaskPK pk)
/*      */   {
/*  161 */     PreparedStatement stmt = null;
/*  162 */     ResultSet resultSet = null;
/*  163 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  167 */       stmt = getConnection().prepareStatement("select TIDY_TASK_ID from TIDY_TASK where    TIDY_TASK_ID = ? ");
/*      */ 
/*  169 */       int col = 1;
/*  170 */       stmt.setInt(col++, pk.getTidyTaskId());
/*      */ 
/*  172 */       resultSet = stmt.executeQuery();
/*      */ 
/*  174 */       if (!resultSet.next())
/*  175 */         returnValue = false;
/*      */       else
/*  177 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  181 */       throw handleSQLException(pk, "select TIDY_TASK_ID from TIDY_TASK where    TIDY_TASK_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  185 */       closeResultSet(resultSet);
/*  186 */       closeStatement(stmt);
/*  187 */       closeConnection();
/*      */     }
/*  189 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private TidyTaskEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  207 */     int col = 1;
/*  208 */     TidyTaskEVO evo = new TidyTaskEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  216 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  217 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  218 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  219 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(TidyTaskEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  224 */     int col = startCol_;
/*  225 */     stmt_.setInt(col++, evo_.getTidyTaskId());
/*  226 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(TidyTaskEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  231 */     int col = startCol_;
/*  232 */     stmt_.setString(col++, evo_.getVisId());
/*  233 */     stmt_.setString(col++, evo_.getDescription());
/*  234 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  235 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  236 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  237 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  238 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(TidyTaskPK pk)
/*      */     throws ValidationException
/*      */   {
/*  254 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  256 */     PreparedStatement stmt = null;
/*  257 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  261 */       stmt = getConnection().prepareStatement("select TIDY_TASK.TIDY_TASK_ID,TIDY_TASK.VIS_ID,TIDY_TASK.DESCRIPTION,TIDY_TASK.VERSION_NUM,TIDY_TASK.UPDATED_BY_USER_ID,TIDY_TASK.UPDATED_TIME,TIDY_TASK.CREATED_TIME from TIDY_TASK where    TIDY_TASK_ID = ? ");
/*      */ 
/*  264 */       int col = 1;
/*  265 */       stmt.setInt(col++, pk.getTidyTaskId());
/*      */ 
/*  267 */       resultSet = stmt.executeQuery();
/*      */ 
/*  269 */       if (!resultSet.next()) {
/*  270 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  273 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  274 */       if (this.mDetails.isModified())
/*  275 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  279 */       throw handleSQLException(pk, "select TIDY_TASK.TIDY_TASK_ID,TIDY_TASK.VIS_ID,TIDY_TASK.DESCRIPTION,TIDY_TASK.VERSION_NUM,TIDY_TASK.UPDATED_BY_USER_ID,TIDY_TASK.UPDATED_TIME,TIDY_TASK.CREATED_TIME from TIDY_TASK where    TIDY_TASK_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  283 */       closeResultSet(resultSet);
/*  284 */       closeStatement(stmt);
/*  285 */       closeConnection();
/*      */ 
/*  287 */       if (timer != null)
/*  288 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  319 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  320 */     generateKeys();
/*      */ 
/*  322 */     this.mDetails.postCreateInit();
/*      */ 
/*  324 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  329 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  330 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  331 */       stmt = getConnection().prepareStatement("insert into TIDY_TASK ( TIDY_TASK_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*      */ 
/*  334 */       int col = 1;
/*  335 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  336 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  339 */       int resultCount = stmt.executeUpdate();
/*  340 */       if (resultCount != 1)
/*      */       {
/*  342 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  345 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  349 */       throw handleSQLException(this.mDetails.getPK(), "insert into TIDY_TASK ( TIDY_TASK_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  353 */       closeStatement(stmt);
/*  354 */       closeConnection();
/*      */ 
/*  356 */       if (timer != null) {
/*  357 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  363 */       getTidyTaskLinkDAO().update(this.mDetails.getTidyTasksEventsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  369 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  389 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  391 */     PreparedStatement stmt = null;
/*  392 */     ResultSet resultSet = null;
/*  393 */     String sqlString = null;
/*      */     try
/*      */     {
/*  398 */       sqlString = "update TIDY_TASK_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  399 */       stmt = getConnection().prepareStatement("update TIDY_TASK_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  400 */       stmt.setInt(1, insertCount);
/*      */ 
/*  402 */       int resultCount = stmt.executeUpdate();
/*  403 */       if (resultCount != 1) {
/*  404 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  406 */       closeStatement(stmt);
/*      */ 
/*  409 */       sqlString = "select SEQ_NUM from TIDY_TASK_SEQ";
/*  410 */       stmt = getConnection().prepareStatement("select SEQ_NUM from TIDY_TASK_SEQ");
/*  411 */       resultSet = stmt.executeQuery();
/*  412 */       if (!resultSet.next())
/*  413 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  414 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  416 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  420 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  424 */       closeResultSet(resultSet);
/*  425 */       closeStatement(stmt);
/*  426 */       closeConnection();
/*      */ 
/*  428 */       if (timer != null)
/*  429 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  429 */     }
/*      */   }
/*      */ 
/*      */   public TidyTaskPK generateKeys()
/*      */   {
/*  439 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  441 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  444 */     if (insertCount == 0) {
/*  445 */       return this.mDetails.getPK();
/*      */     }
/*  447 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  449 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  473 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  475 */     generateKeys();
/*      */ 
/*  480 */     PreparedStatement stmt = null;
/*      */ 
/*  482 */     boolean mainChanged = this.mDetails.isModified();
/*  483 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  487 */       if (getTidyTaskLinkDAO().update(this.mDetails.getTidyTasksEventsMap())) {
/*  488 */         dependantChanged = true;
/*      */       }
/*  490 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  493 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  496 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  497 */         stmt = getConnection().prepareStatement("update TIDY_TASK set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TIDY_TASK_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  500 */         int col = 1;
/*  501 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  502 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  504 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  507 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  509 */         if (resultCount == 0) {
/*  510 */           checkVersionNum();
/*      */         }
/*  512 */         if (resultCount != 1) {
/*  513 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  516 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  525 */       throw handleSQLException(getPK(), "update TIDY_TASK set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TIDY_TASK_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  529 */       closeStatement(stmt);
/*  530 */       closeConnection();
/*      */ 
/*  532 */       if ((timer != null) && (
/*  533 */         (mainChanged) || (dependantChanged)))
/*  534 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  546 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  547 */     PreparedStatement stmt = null;
/*  548 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  552 */       stmt = getConnection().prepareStatement("select VERSION_NUM from TIDY_TASK where TIDY_TASK_ID = ?");
/*      */ 
/*  555 */       int col = 1;
/*  556 */       stmt.setInt(col++, this.mDetails.getTidyTaskId());
/*      */ 
/*  559 */       resultSet = stmt.executeQuery();
/*      */ 
/*  561 */       if (!resultSet.next()) {
/*  562 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  565 */       col = 1;
/*  566 */       int dbVersionNumber = resultSet.getInt(col++);
/*  567 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  568 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  574 */       throw handleSQLException(getPK(), "select VERSION_NUM from TIDY_TASK where TIDY_TASK_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  578 */       closeStatement(stmt);
/*  579 */       closeResultSet(resultSet);
/*      */ 
/*  581 */       if (timer != null)
/*  582 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  599 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  600 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  605 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  610 */       stmt = getConnection().prepareStatement("delete from TIDY_TASK where    TIDY_TASK_ID = ? ");
/*      */ 
/*  613 */       int col = 1;
/*  614 */       stmt.setInt(col++, this.mDetails.getTidyTaskId());
/*      */ 
/*  616 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  618 */       if (resultCount != 1) {
/*  619 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  625 */       throw handleSQLException(getPK(), "delete from TIDY_TASK where    TIDY_TASK_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  629 */       closeStatement(stmt);
/*  630 */       closeConnection();
/*      */ 
/*  632 */       if (timer != null)
/*  633 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllTidyTasksELO getAllTidyTasks()
/*      */   {
/*  663 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  664 */     PreparedStatement stmt = null;
/*  665 */     ResultSet resultSet = null;
/*  666 */     AllTidyTasksELO results = new AllTidyTasksELO();
/*      */     try
/*      */     {
/*  669 */       stmt = getConnection().prepareStatement(SQL_ALL_TIDY_TASKS);
/*  670 */       int col = 1;
/*  671 */       resultSet = stmt.executeQuery();
/*  672 */       while (resultSet.next())
/*      */       {
/*  674 */         col = 2;
/*      */ 
/*  677 */         TidyTaskPK pkTidyTask = new TidyTaskPK(resultSet.getInt(col++));
/*      */ 
/*  680 */         String textTidyTask = resultSet.getString(col++);
/*      */ 
/*  684 */         TidyTaskRefImpl erTidyTask = new TidyTaskRefImpl(pkTidyTask, textTidyTask);
/*      */ 
/*  689 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  692 */         results.add(erTidyTask, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  700 */       throw handleSQLException(SQL_ALL_TIDY_TASKS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  704 */       closeResultSet(resultSet);
/*  705 */       closeStatement(stmt);
/*  706 */       closeConnection();
/*      */     }
/*      */ 
/*  709 */     if (timer != null) {
/*  710 */       timer.logDebug("getAllTidyTasks", " items=" + results.size());
/*      */     }
/*      */ 
/*  714 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(TidyTaskPK pk)
/*      */   {
/*  741 */     Set emptyStrings = Collections.emptySet();
/*  742 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(TidyTaskPK pk, Set<String> exclusionTables)
/*      */   {
/*  748 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  750 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  752 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  754 */       PreparedStatement stmt = null;
/*      */ 
/*  756 */       int resultCount = 0;
/*  757 */       String s = null;
/*      */       try
/*      */       {
/*  760 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  762 */         if (this._log.isDebugEnabled()) {
/*  763 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  765 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  768 */         int col = 1;
/*  769 */         stmt.setInt(col++, pk.getTidyTaskId());
/*      */ 
/*  772 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  776 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  780 */         closeStatement(stmt);
/*  781 */         closeConnection();
/*      */ 
/*  783 */         if (timer != null) {
/*  784 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  788 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  790 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  792 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  794 */       PreparedStatement stmt = null;
/*      */ 
/*  796 */       int resultCount = 0;
/*  797 */       String s = null;
/*      */       try
/*      */       {
/*  800 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  802 */         if (this._log.isDebugEnabled()) {
/*  803 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  805 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  808 */         int col = 1;
/*  809 */         stmt.setInt(col++, pk.getTidyTaskId());
/*      */ 
/*  812 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  816 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  820 */         closeStatement(stmt);
/*  821 */         closeConnection();
/*      */ 
/*  823 */         if (timer != null)
/*  824 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public TidyTaskEVO getDetails(TidyTaskPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  843 */     return getDetails(new TidyTaskCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public TidyTaskEVO getDetails(TidyTaskCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  860 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  863 */     if (this.mDetails == null) {
/*  864 */       doLoad(paramCK.getTidyTaskPK());
/*      */     }
/*  866 */     else if (!this.mDetails.getPK().equals(paramCK.getTidyTaskPK())) {
/*  867 */       doLoad(paramCK.getTidyTaskPK());
/*      */     }
/*  869 */     else if (!checkIfValid())
/*      */     {
/*  871 */       this._log.info("getDetails", "[ALERT] TidyTaskEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/*  873 */       doLoad(paramCK.getTidyTaskPK());
/*      */     }
/*      */ 
/*  883 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isTidyTasksEventsAllItemsLoaded()))
/*      */     {
/*  888 */       this.mDetails.setTidyTasksEvents(getTidyTaskLinkDAO().getAll(this.mDetails.getTidyTaskId(), dependants, this.mDetails.getTidyTasksEvents()));
/*      */ 
/*  895 */       this.mDetails.setTidyTasksEventsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  898 */     if ((paramCK instanceof TidyTaskLinkCK))
/*      */     {
/*  900 */       if (this.mDetails.getTidyTasksEvents() == null) {
/*  901 */         this.mDetails.loadTidyTasksEventsItem(getTidyTaskLinkDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  904 */         TidyTaskLinkPK pk = ((TidyTaskLinkCK)paramCK).getTidyTaskLinkPK();
/*  905 */         TidyTaskLinkEVO evo = this.mDetails.getTidyTasksEventsItem(pk);
/*  906 */         if (evo == null) {
/*  907 */           this.mDetails.loadTidyTasksEventsItem(getTidyTaskLinkDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  912 */     TidyTaskEVO details = new TidyTaskEVO();
/*  913 */     details = this.mDetails.deepClone();
/*      */ 
/*  915 */     if (timer != null) {
/*  916 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  918 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/*  928 */     boolean stillValid = false;
/*  929 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  930 */     PreparedStatement stmt = null;
/*  931 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  934 */       stmt = getConnection().prepareStatement("select VERSION_NUM from TIDY_TASK where   TIDY_TASK_ID = ?");
/*  935 */       int col = 1;
/*  936 */       stmt.setInt(col++, this.mDetails.getTidyTaskId());
/*      */ 
/*  938 */       resultSet = stmt.executeQuery();
/*      */ 
/*  940 */       if (!resultSet.next()) {
/*  941 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/*  943 */       col = 1;
/*  944 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/*  946 */       if (dbVersionNum == this.mDetails.getVersionNum())
/*  947 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  951 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from TIDY_TASK where   TIDY_TASK_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  955 */       closeResultSet(resultSet);
/*  956 */       closeStatement(stmt);
/*  957 */       closeConnection();
/*      */ 
/*  959 */       if (timer != null) {
/*  960 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/*  963 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public TidyTaskEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  969 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  971 */     if (!checkIfValid())
/*      */     {
/*  973 */       this._log.info("getDetails", "TidyTask " + this.mDetails.getPK() + " no longer valid - reloading");
/*  974 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/*  978 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  981 */     TidyTaskEVO details = this.mDetails.deepClone();
/*      */ 
/*  983 */     if (timer != null) {
/*  984 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  986 */     return details;
/*      */   }
/*      */ 
/*      */   protected TidyTaskLinkDAO getTidyTaskLinkDAO()
/*      */   {
/*  995 */     if (this.mTidyTaskLinkDAO == null)
/*      */     {
/*  997 */       if (this.mDataSource != null)
/*  998 */         this.mTidyTaskLinkDAO = new TidyTaskLinkDAO(this.mDataSource);
/*      */       else {
/* 1000 */         this.mTidyTaskLinkDAO = new TidyTaskLinkDAO(getConnection());
/*      */       }
/*      */     }
/* 1003 */     return this.mTidyTaskLinkDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1008 */     return "TidyTask";
/*      */   }
/*      */ 
/*      */   public TidyTaskRef getRef(TidyTaskPK paramTidyTaskPK)
/*      */     throws ValidationException
/*      */   {
/* 1014 */     TidyTaskEVO evo = getDetails(paramTidyTaskPK, "");
/* 1015 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1040 */     if (c == null)
/* 1041 */       return;
/* 1042 */     Iterator iter = c.iterator();
/* 1043 */     while (iter.hasNext())
/*      */     {
/* 1045 */       TidyTaskEVO evo = (TidyTaskEVO)iter.next();
/* 1046 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(TidyTaskEVO evo, String dependants)
/*      */   {
/* 1060 */     if (evo.getTidyTaskId() < 1) {
/* 1061 */       return;
/*      */     }
/*      */ 
/* 1069 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1072 */       if (!evo.isTidyTasksEventsAllItemsLoaded())
/*      */       {
/* 1074 */         evo.setTidyTasksEvents(getTidyTaskLinkDAO().getAll(evo.getTidyTaskId(), dependants, evo.getTidyTasksEvents()));
/*      */ 
/* 1081 */         evo.setTidyTasksEventsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskDAO
 * JD-Core Version:    0.6.0
 */