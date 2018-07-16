/*      */ package com.cedar.cp.ejb.impl.task.group;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.task.group.TaskGroupRef;
/*      */ import com.cedar.cp.dto.task.group.AllTaskGroupsELO;
/*      */ import com.cedar.cp.dto.task.group.TaskGroupCK;
/*      */ import com.cedar.cp.dto.task.group.TaskGroupPK;
/*      */ import com.cedar.cp.dto.task.group.TaskGroupRICheckELO;
/*      */ import com.cedar.cp.dto.task.group.TaskGroupRefImpl;
/*      */ import com.cedar.cp.dto.task.group.TgRowCK;
/*      */ import com.cedar.cp.dto.task.group.TgRowPK;
/*      */ import com.cedar.cp.dto.task.group.TgRowParamPK;
/*      */ import com.cedar.cp.dto.task.group.TgRowParamRefImpl;
/*      */ import com.cedar.cp.dto.task.group.TgRowRefImpl;
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
/*      */ public class TaskGroupDAO extends AbstractDAO
/*      */ {
/*   38 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select GROUP_ID from TASK_GROUP where    GROUP_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select TASK_GROUP.GROUP_ID,TASK_GROUP.VIS_ID,TASK_GROUP.DESCRIPTION,TASK_GROUP.LAST_SUBMIT,TASK_GROUP.UPDATED_BY_USER_ID,TASK_GROUP.UPDATED_TIME,TASK_GROUP.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from TASK_GROUP where    GROUP_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into TASK_GROUP ( GROUP_ID,VIS_ID,DESCRIPTION,LAST_SUBMIT,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update TASK_GROUP_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from TASK_GROUP_SEQ";
/*      */   protected static final String SQL_STORE = "update TASK_GROUP set VIS_ID = ?,DESCRIPTION = ?,LAST_SUBMIT = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    GROUP_ID = ? ";
/*      */   protected static final String SQL_REMOVE = "delete from TASK_GROUP where    GROUP_ID = ? ";
/*  591 */   protected static String SQL_ALL_TASK_GROUPS = "select 0       ,TASK_GROUP.GROUP_ID      ,TASK_GROUP.VIS_ID      ,TASK_GROUP.DESCRIPTION      ,TASK_GROUP.LAST_SUBMIT from TASK_GROUP where 1=1 ";
/*      */ 
/*  674 */   protected static String SQL_TASK_GROUP_R_I_CHECK = "select 0       ,TASK_GROUP.GROUP_ID      ,TASK_GROUP.VIS_ID      ,TG_ROW.TG_ROW_ID      ,TG_ROW_PARAM.TG_ROW_PARAM_ID      ,TASK_GROUP.DESCRIPTION      ,TG_ROW.ROW_TYPE      ,TG_ROW_PARAM.KEY      ,TG_ROW_PARAM.PARAM from TASK_GROUP    ,TG_ROW    ,TG_ROW_PARAM where 1=1  and  TASK_GROUP.GROUP_ID = TG_ROW.GROUP_ID and TG_ROW.TG_ROW_ID = TG_ROW_PARAM.TG_ROW_ID and TG_ROW.ROW_TYPE = ?";
/*      */ 
/*  796 */   private static String[][] SQL_DELETE_CHILDREN = { { "TG_ROW", "delete from TG_ROW where     TG_ROW.GROUP_ID = ? " } };
/*      */ 
/*  805 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "TG_ROW_PARAM", "delete from TG_ROW_PARAM TgRowParam where exists (select * from TG_ROW_PARAM,TG_ROW,TASK_GROUP where     TG_ROW_PARAM.TG_ROW_ID = TG_ROW.TG_ROW_ID and TG_ROW.GROUP_ID = TASK_GROUP.GROUP_ID and TgRowParam.TG_ROW_ID = TG_ROW_PARAM.TG_ROW_ID " } };
/*      */ 
/*  820 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and TASK_GROUP.GROUP_ID = ?)";
/*      */   public static final String SQL_GET_TG_ROW_REF = "select 0,TASK_GROUP.GROUP_ID from TG_ROW,TASK_GROUP where 1=1 and TG_ROW.TG_ROW_ID = ? and TG_ROW.GROUP_ID = TASK_GROUP.GROUP_ID";
/*      */   public static final String SQL_GET_TG_ROW_PARAM_REF = "select 0,TASK_GROUP.GROUP_ID,TG_ROW.TG_ROW_ID from TG_ROW_PARAM,TASK_GROUP,TG_ROW where 1=1 and TG_ROW_PARAM.TG_ROW_PARAM_ID = ? and TG_ROW_PARAM.TG_ROW_ID = TG_ROW.TG_ROW_ID and TG_ROW.TG_ROW_ID = TASK_GROUP.TG_ROW_ID";
/*      */   protected TgRowDAO mTgRowDAO;
/*      */   protected TaskGroupEVO mDetails;
/*      */ 
/*      */   public TaskGroupDAO(Connection connection)
/*      */   {
/*   45 */     super(connection);
/*      */   }
/*      */ 
/*      */   public TaskGroupDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public TaskGroupDAO(DataSource ds)
/*      */   {
/*   61 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected TaskGroupPK getPK()
/*      */   {
/*   69 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(TaskGroupEVO details)
/*      */   {
/*   78 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public TaskGroupEVO setAndGetDetails(TaskGroupEVO details, String dependants)
/*      */   {
/*   89 */     setDetails(details);
/*   90 */     generateKeys();
/*   91 */     getDependants(this.mDetails, dependants);
/*   92 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public TaskGroupPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  101 */     doCreate();
/*      */ 
/*  103 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(TaskGroupPK pk)
/*      */     throws ValidationException
/*      */   {
/*  113 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  122 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  131 */     doRemove();
/*      */   }
/*      */ 
/*      */   public TaskGroupPK findByPrimaryKey(TaskGroupPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  140 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  141 */     if (exists(pk_))
/*      */     {
/*  143 */       if (timer != null) {
/*  144 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  146 */       return pk_;
/*      */     }
/*      */ 
/*  149 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(TaskGroupPK pk)
/*      */   {
/*  167 */     PreparedStatement stmt = null;
/*  168 */     ResultSet resultSet = null;
/*  169 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  173 */       stmt = getConnection().prepareStatement("select GROUP_ID from TASK_GROUP where    GROUP_ID = ? ");
/*      */ 
/*  175 */       int col = 1;
/*  176 */       stmt.setInt(col++, pk.getGroupId());
/*      */ 
/*  178 */       resultSet = stmt.executeQuery();
/*      */ 
/*  180 */       if (!resultSet.next())
/*  181 */         returnValue = false;
/*      */       else
/*  183 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  187 */       throw handleSQLException(pk, "select GROUP_ID from TASK_GROUP where    GROUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  191 */       closeResultSet(resultSet);
/*  192 */       closeStatement(stmt);
/*  193 */       closeConnection();
/*      */     }
/*  195 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private TaskGroupEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  213 */     int col = 1;
/*  214 */     TaskGroupEVO evo = new TaskGroupEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getTimestamp(col++), null);
/*      */ 
/*  222 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  223 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  224 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  225 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(TaskGroupEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  230 */     int col = startCol_;
/*  231 */     stmt_.setInt(col++, evo_.getGroupId());
/*  232 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(TaskGroupEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  237 */     int col = startCol_;
/*  238 */     stmt_.setString(col++, evo_.getVisId());
/*  239 */     stmt_.setString(col++, evo_.getDescription());
/*  240 */     stmt_.setTimestamp(col++, evo_.getLastSubmit());
/*  241 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  242 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  243 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  244 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(TaskGroupPK pk)
/*      */     throws ValidationException
/*      */   {
/*  260 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  262 */     PreparedStatement stmt = null;
/*  263 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  267 */       stmt = getConnection().prepareStatement("select TASK_GROUP.GROUP_ID,TASK_GROUP.VIS_ID,TASK_GROUP.DESCRIPTION,TASK_GROUP.LAST_SUBMIT,TASK_GROUP.UPDATED_BY_USER_ID,TASK_GROUP.UPDATED_TIME,TASK_GROUP.CREATED_TIME from TASK_GROUP where    GROUP_ID = ? ");
/*      */ 
/*  270 */       int col = 1;
/*  271 */       stmt.setInt(col++, pk.getGroupId());
/*      */ 
/*  273 */       resultSet = stmt.executeQuery();
/*      */ 
/*  275 */       if (!resultSet.next()) {
/*  276 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  279 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  280 */       if (this.mDetails.isModified())
/*  281 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  285 */       throw handleSQLException(pk, "select TASK_GROUP.GROUP_ID,TASK_GROUP.VIS_ID,TASK_GROUP.DESCRIPTION,TASK_GROUP.LAST_SUBMIT,TASK_GROUP.UPDATED_BY_USER_ID,TASK_GROUP.UPDATED_TIME,TASK_GROUP.CREATED_TIME from TASK_GROUP where    GROUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  289 */       closeResultSet(resultSet);
/*  290 */       closeStatement(stmt);
/*  291 */       closeConnection();
/*      */ 
/*  293 */       if (timer != null)
/*  294 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  325 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  326 */     generateKeys();
/*      */ 
/*  328 */     this.mDetails.postCreateInit();
/*      */ 
/*  330 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  335 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  336 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  337 */       stmt = getConnection().prepareStatement("insert into TASK_GROUP ( GROUP_ID,VIS_ID,DESCRIPTION,LAST_SUBMIT,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*      */ 
/*  340 */       int col = 1;
/*  341 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  342 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  345 */       int resultCount = stmt.executeUpdate();
/*  346 */       if (resultCount != 1)
/*      */       {
/*  348 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  351 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  355 */       throw handleSQLException(this.mDetails.getPK(), "insert into TASK_GROUP ( GROUP_ID,VIS_ID,DESCRIPTION,LAST_SUBMIT,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  359 */       closeStatement(stmt);
/*  360 */       closeConnection();
/*      */ 
/*  362 */       if (timer != null) {
/*  363 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  369 */       getTgRowDAO().update(this.mDetails.getTaskGroupRowsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  375 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  395 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  397 */     PreparedStatement stmt = null;
/*  398 */     ResultSet resultSet = null;
/*  399 */     String sqlString = null;
/*      */     try
/*      */     {
/*  404 */       sqlString = "update TASK_GROUP_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  405 */       stmt = getConnection().prepareStatement("update TASK_GROUP_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  406 */       stmt.setInt(1, insertCount);
/*      */ 
/*  408 */       int resultCount = stmt.executeUpdate();
/*  409 */       if (resultCount != 1) {
/*  410 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  412 */       closeStatement(stmt);
/*      */ 
/*  415 */       sqlString = "select SEQ_NUM from TASK_GROUP_SEQ";
/*  416 */       stmt = getConnection().prepareStatement("select SEQ_NUM from TASK_GROUP_SEQ");
/*  417 */       resultSet = stmt.executeQuery();
/*  418 */       if (!resultSet.next())
/*  419 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  420 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  422 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  426 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  430 */       closeResultSet(resultSet);
/*  431 */       closeStatement(stmt);
/*  432 */       closeConnection();
/*      */ 
/*  434 */       if (timer != null)
/*  435 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  435 */     }
/*      */   }
/*      */ 
/*      */   public TaskGroupPK generateKeys()
/*      */   {
/*  445 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  447 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  450 */     if (insertCount == 0) {
/*  451 */       return this.mDetails.getPK();
/*      */     }
/*  453 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  455 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  478 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  480 */     generateKeys();
/*      */ 
/*  485 */     PreparedStatement stmt = null;
/*      */ 
/*  487 */     boolean mainChanged = this.mDetails.isModified();
/*  488 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  492 */       if (getTgRowDAO().update(this.mDetails.getTaskGroupRowsMap())) {
/*  493 */         dependantChanged = true;
/*      */       }
/*  495 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  498 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  499 */         stmt = getConnection().prepareStatement("update TASK_GROUP set VIS_ID = ?,DESCRIPTION = ?,LAST_SUBMIT = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    GROUP_ID = ? ");
/*      */ 
/*  502 */         int col = 1;
/*  503 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  504 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  507 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  510 */         if (resultCount != 1) {
/*  511 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  514 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  523 */       throw handleSQLException(getPK(), "update TASK_GROUP set VIS_ID = ?,DESCRIPTION = ?,LAST_SUBMIT = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    GROUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  527 */       closeStatement(stmt);
/*  528 */       closeConnection();
/*      */ 
/*  530 */       if ((timer != null) && (
/*  531 */         (mainChanged) || (dependantChanged)))
/*  532 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  550 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  551 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  556 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  561 */       stmt = getConnection().prepareStatement("delete from TASK_GROUP where    GROUP_ID = ? ");
/*      */ 
/*  564 */       int col = 1;
/*  565 */       stmt.setInt(col++, this.mDetails.getGroupId());
/*      */ 
/*  567 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  569 */       if (resultCount != 1) {
/*  570 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  576 */       throw handleSQLException(getPK(), "delete from TASK_GROUP where    GROUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  580 */       closeStatement(stmt);
/*  581 */       closeConnection();
/*      */ 
/*  583 */       if (timer != null)
/*  584 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllTaskGroupsELO getAllTaskGroups()
/*      */   {
/*  615 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  616 */     PreparedStatement stmt = null;
/*  617 */     ResultSet resultSet = null;
/*  618 */     AllTaskGroupsELO results = new AllTaskGroupsELO();
/*      */     try
/*      */     {
/*  621 */       stmt = getConnection().prepareStatement(SQL_ALL_TASK_GROUPS);
/*  622 */       int col = 1;
/*  623 */       resultSet = stmt.executeQuery();
/*  624 */       while (resultSet.next())
/*      */       {
/*  626 */         col = 2;
/*      */ 
/*  629 */         TaskGroupPK pkTaskGroup = new TaskGroupPK(resultSet.getInt(col++));
/*      */ 
/*  632 */         String textTaskGroup = resultSet.getString(col++);
/*      */ 
/*  636 */         TaskGroupRefImpl erTaskGroup = new TaskGroupRefImpl(pkTaskGroup, textTaskGroup);
/*      */ 
/*  641 */         String col1 = resultSet.getString(col++);
/*  642 */         Timestamp col2 = resultSet.getTimestamp(col++);
/*      */ 
/*  645 */         results.add(erTaskGroup, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  654 */       throw handleSQLException(SQL_ALL_TASK_GROUPS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  658 */       closeResultSet(resultSet);
/*  659 */       closeStatement(stmt);
/*  660 */       closeConnection();
/*      */     }
/*      */ 
/*  663 */     if (timer != null) {
/*  664 */       timer.logDebug("getAllTaskGroups", " items=" + results.size());
/*      */     }
/*      */ 
/*  668 */     return results;
/*      */   }
/*      */ 
/*      */   public TaskGroupRICheckELO getTaskGroupRICheck(int param1)
/*      */   {
/*  708 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  709 */     PreparedStatement stmt = null;
/*  710 */     ResultSet resultSet = null;
/*  711 */     TaskGroupRICheckELO results = new TaskGroupRICheckELO();
/*      */     try
/*      */     {
/*  714 */       stmt = getConnection().prepareStatement(SQL_TASK_GROUP_R_I_CHECK);
/*  715 */       int col = 1;
/*  716 */       stmt.setInt(col++, param1);
/*  717 */       resultSet = stmt.executeQuery();
/*  718 */       while (resultSet.next())
/*      */       {
/*  720 */         col = 2;
/*      */ 
/*  723 */         TaskGroupPK pkTaskGroup = new TaskGroupPK(resultSet.getInt(col++));
/*      */ 
/*  726 */         String textTaskGroup = resultSet.getString(col++);
/*      */ 
/*  729 */         TgRowPK pkTgRow = new TgRowPK(resultSet.getInt(col++));
/*      */ 
/*  732 */         String textTgRow = "";
/*      */ 
/*  734 */         TgRowParamPK pkTgRowParam = new TgRowParamPK(resultSet.getInt(col++));
/*      */ 
/*  737 */         String textTgRowParam = "";
/*      */ 
/*  740 */         TaskGroupRefImpl erTaskGroup = new TaskGroupRefImpl(pkTaskGroup, textTaskGroup);
/*      */ 
/*  746 */         TgRowRefImpl erTgRow = new TgRowRefImpl(pkTgRow, textTgRow);
/*      */ 
/*  752 */         TgRowParamRefImpl erTgRowParam = new TgRowParamRefImpl(pkTgRowParam, textTgRowParam);
/*      */ 
/*  757 */         String col1 = resultSet.getString(col++);
/*  758 */         int col2 = resultSet.getInt(col++);
/*  759 */         String col3 = resultSet.getString(col++);
/*  760 */         String col4 = resultSet.getString(col++);
/*      */ 
/*  763 */         results.add(erTaskGroup, erTgRow, erTgRowParam, col1, col2, col3, col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  776 */       throw handleSQLException(SQL_TASK_GROUP_R_I_CHECK, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  780 */       closeResultSet(resultSet);
/*  781 */       closeStatement(stmt);
/*  782 */       closeConnection();
/*      */     }
/*      */ 
/*  785 */     if (timer != null) {
/*  786 */       timer.logDebug("getTaskGroupRICheck", " RowType=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  791 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(TaskGroupPK pk)
/*      */   {
/*  829 */     Set emptyStrings = Collections.emptySet();
/*  830 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(TaskGroupPK pk, Set<String> exclusionTables)
/*      */   {
/*  836 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  838 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  840 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  842 */       PreparedStatement stmt = null;
/*      */ 
/*  844 */       int resultCount = 0;
/*  845 */       String s = null;
/*      */       try
/*      */       {
/*  848 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  850 */         if (this._log.isDebugEnabled()) {
/*  851 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  853 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  856 */         int col = 1;
/*  857 */         stmt.setInt(col++, pk.getGroupId());
/*      */ 
/*  860 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  864 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  868 */         closeStatement(stmt);
/*  869 */         closeConnection();
/*      */ 
/*  871 */         if (timer != null) {
/*  872 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  876 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  878 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  880 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  882 */       PreparedStatement stmt = null;
/*      */ 
/*  884 */       int resultCount = 0;
/*  885 */       String s = null;
/*      */       try
/*      */       {
/*  888 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  890 */         if (this._log.isDebugEnabled()) {
/*  891 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  893 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  896 */         int col = 1;
/*  897 */         stmt.setInt(col++, pk.getGroupId());
/*      */ 
/*  900 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  904 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  908 */         closeStatement(stmt);
/*  909 */         closeConnection();
/*      */ 
/*  911 */         if (timer != null)
/*  912 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public TaskGroupEVO getDetails(TaskGroupPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  932 */     return getDetails(new TaskGroupCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public TaskGroupEVO getDetails(TaskGroupCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  951 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  954 */     if (this.mDetails == null) {
/*  955 */       doLoad(paramCK.getTaskGroupPK());
/*      */     }
/*  957 */     else if (!this.mDetails.getPK().equals(paramCK.getTaskGroupPK())) {
/*  958 */       doLoad(paramCK.getTaskGroupPK());
/*      */     }
/*      */ 
/*  967 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isTaskGroupRowsAllItemsLoaded()))
/*      */     {
/*  972 */       this.mDetails.setTaskGroupRows(getTgRowDAO().getAll(this.mDetails.getGroupId(), dependants, this.mDetails.getTaskGroupRows()));
/*      */ 
/*  979 */       this.mDetails.setTaskGroupRowsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  982 */     if ((paramCK instanceof TgRowCK))
/*      */     {
/*  984 */       if (this.mDetails.getTaskGroupRows() == null) {
/*  985 */         this.mDetails.loadTaskGroupRowsItem(getTgRowDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  988 */         TgRowPK pk = ((TgRowCK)paramCK).getTgRowPK();
/*  989 */         TgRowEVO evo = this.mDetails.getTaskGroupRowsItem(pk);
/*  990 */         if (evo == null)
/*  991 */           this.mDetails.loadTaskGroupRowsItem(getTgRowDAO().getDetails(paramCK, dependants));
/*      */         else {
/*  993 */           getTgRowDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  998 */     TaskGroupEVO details = new TaskGroupEVO();
/*  999 */     details = this.mDetails.deepClone();
/*      */ 
/* 1001 */     if (timer != null) {
/* 1002 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1004 */     return details;
/*      */   }
/*      */ 
/*      */   public TaskGroupEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1010 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1014 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1017 */     TaskGroupEVO details = this.mDetails.deepClone();
/*      */ 
/* 1019 */     if (timer != null) {
/* 1020 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1022 */     return details;
/*      */   }
/*      */ 
/*      */   protected TgRowDAO getTgRowDAO()
/*      */   {
/* 1031 */     if (this.mTgRowDAO == null)
/*      */     {
/* 1033 */       if (this.mDataSource != null)
/* 1034 */         this.mTgRowDAO = new TgRowDAO(this.mDataSource);
/*      */       else {
/* 1036 */         this.mTgRowDAO = new TgRowDAO(getConnection());
/*      */       }
/*      */     }
/* 1039 */     return this.mTgRowDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1044 */     return "TaskGroup";
/*      */   }
/*      */ 
/*      */   public TaskGroupRef getRef(TaskGroupPK paramTaskGroupPK)
/*      */     throws ValidationException
/*      */   {
/* 1050 */     TaskGroupEVO evo = getDetails(paramTaskGroupPK, "");
/* 1051 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1091 */     if (c == null)
/* 1092 */       return;
/* 1093 */     Iterator iter = c.iterator();
/* 1094 */     while (iter.hasNext())
/*      */     {
/* 1096 */       TaskGroupEVO evo = (TaskGroupEVO)iter.next();
/* 1097 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(TaskGroupEVO evo, String dependants)
/*      */   {
/* 1111 */     if (evo.getGroupId() < 1) {
/* 1112 */       return;
/*      */     }
/*      */ 
/* 1120 */     if ((dependants.indexOf("<0>") > -1) || (dependants.indexOf("<1>") > -1))
/*      */     {
/* 1124 */       if (!evo.isTaskGroupRowsAllItemsLoaded())
/*      */       {
/* 1126 */         evo.setTaskGroupRows(getTgRowDAO().getAll(evo.getGroupId(), dependants, evo.getTaskGroupRows()));
/*      */ 
/* 1133 */         evo.setTaskGroupRowsAllItemsLoaded(true);
/*      */       }
/* 1135 */       getTgRowDAO().getDependants(evo.getTaskGroupRows(), dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllTaskGroupsELO getAllTaskGroups(Object key)
/*      */   {
/* 1149 */     AllTaskGroupsELO fullList = getAllTaskGroups();
/* 1150 */     if (key == null) {
/* 1151 */       return fullList;
/*      */     }
/* 1153 */     AllTaskGroupsELO filteredList = new AllTaskGroupsELO();
/*      */ 
/* 1155 */     while (fullList.hasNext())
/*      */     {
/* 1157 */       fullList.next();
/* 1158 */       TaskGroupRef ref = fullList.getTaskGroupEntityRef();
/* 1159 */       if (ref.getPrimaryKey().equals(key)) {
/*      */         continue;
/*      */       }
/* 1162 */       filteredList.add(ref, fullList.getDescription(), fullList.getLastSubmit());
/*      */     }
/*      */ 
/* 1165 */     return filteredList;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.task.group.TaskGroupDAO
 * JD-Core Version:    0.6.0
 */