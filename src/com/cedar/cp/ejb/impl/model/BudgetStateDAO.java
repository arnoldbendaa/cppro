/*      */ package com.cedar.cp.ejb.impl.model;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.BudgetCycleCK;
/*      */ import com.cedar.cp.dto.model.BudgetCyclePK;
/*      */ import com.cedar.cp.dto.model.BudgetCycleRefImpl;
/*      */ import com.cedar.cp.dto.model.BudgetStateCK;
/*      */ import com.cedar.cp.dto.model.BudgetStateHistoryCK;
/*      */ import com.cedar.cp.dto.model.BudgetStateHistoryPK;
/*      */ import com.cedar.cp.dto.model.BudgetStatePK;
/*      */ import com.cedar.cp.dto.model.BudgetStateRefImpl;
/*      */ import com.cedar.cp.dto.model.CheckIfHasStateELO;
/*      */ import com.cedar.cp.dto.model.CycleStateDetailsELO;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class BudgetStateDAO extends AbstractDAO
/*      */ {
/*   32 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select BUDGET_STATE.BUDGET_CYCLE_ID,BUDGET_STATE.STRUCTURE_ELEMENT_ID,BUDGET_STATE.STATE,BUDGET_STATE.SUBMITABLE,BUDGET_STATE.REJECTABLE,BUDGET_STATE.VERSION_NUM,BUDGET_STATE.UPDATED_BY_USER_ID,BUDGET_STATE.UPDATED_TIME,BUDGET_STATE.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from BUDGET_STATE where    BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into BUDGET_STATE ( BUDGET_CYCLE_ID,STRUCTURE_ELEMENT_ID,STATE,SUBMITABLE,REJECTABLE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update BUDGET_STATE set STATE = ?,SUBMITABLE = ?,REJECTABLE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from BUDGET_STATE where BUDGET_CYCLE_ID = ?,STRUCTURE_ELEMENT_ID = ?";
/*  411 */   protected static String SQL_CHECK_IF_HAS_STATE = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,BUDGET_STATE.BUDGET_CYCLE_ID      ,BUDGET_STATE.STRUCTURE_ELEMENT_ID      ,BUDGET_STATE.BUDGET_CYCLE_ID      ,BUDGET_STATE.STRUCTURE_ELEMENT_ID      ,BUDGET_STATE.STATE from BUDGET_STATE    ,MODEL    ,BUDGET_CYCLE where 1=1   and BUDGET_STATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID   and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_STATE.BUDGET_CYCLE_ID = ? and BUDGET_STATE.STRUCTURE_ELEMENT_ID = ?";
/*      */ 
/*  557 */   protected static String SQL_CYCLE_STATE_DETAILS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,BUDGET_STATE.BUDGET_CYCLE_ID      ,BUDGET_STATE.STRUCTURE_ELEMENT_ID      ,BUDGET_STATE.STRUCTURE_ELEMENT_ID      ,BUDGET_STATE.STATE      ,BUDGET_STATE.SUBMITABLE      ,BUDGET_STATE.REJECTABLE from BUDGET_STATE    ,MODEL    ,BUDGET_CYCLE where 1=1   and BUDGET_STATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID   and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_STATE.BUDGET_CYCLE_ID = ?";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from BUDGET_STATE where    BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*  845 */   private static String[][] SQL_DELETE_CHILDREN = { { "BUDGET_STATE_HISTORY", "delete from BUDGET_STATE_HISTORY where     BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID = ? and BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID = ? " } };
/*      */ 
/*  855 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  859 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and BUDGET_STATE.BUDGET_CYCLE_ID = ?and BUDGET_STATE.STRUCTURE_ELEMENT_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from BUDGET_STATE,BUDGET_CYCLE where 1=1 and BUDGET_STATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.MODEL_ID = ? order by  BUDGET_STATE.BUDGET_CYCLE_ID ,BUDGET_STATE.BUDGET_CYCLE_ID ,BUDGET_STATE.STRUCTURE_ELEMENT_ID";
/*      */   protected static final String SQL_GET_ALL = " from BUDGET_STATE where    BUDGET_CYCLE_ID = ? ";
/*      */   protected static final String SQL_CURRENT_STATE = "select state   from budget_state  where budget_cycle_id = ?    and structure_element_id = ? ";
/*      */   protected static final String SQL_AGREE_COUNT = "select count(*) agree_count from structure_element ,budget_state where structure_element.structure_id = ? and structure_element.parent_id = ? and structure_element.structure_element_id = budget_state.structure_element_id and ( budget_state.state = 4 or budget_state.state = 5 or budget_state.state = 6 ) and budget_state.budget_cycle_id = ?";
/*      */   private static final String TIDY_ORPHAN_STATE_SQL = "delete \nfrom budget_state \nwhere ( budget_cycle_id, structure_element_id ) in \n\t\t( select state.budget_cycle_id, state.structure_element_id \n\t\t  from budget_state state, budget_cycle cycle \n\t\t  where cycle.model_id = ? and \n\t\t\t\tcycle.budget_cycle_id = state.budget_cycle_id and \n\t\t\t\tstate.structure_element_id not in ( select structure_element_id \n\t\t\t\t\t\t\t\t\t\t\t\t\tfrom structure_element \n\t\t\t\t\t\t\t\t\t\t\t\t\twhere structure_id = ? ) )";
/*      */   private static final String BATCH_STATE_INSERT = "insert into budget_state (BUDGET_CYCLE_ID, STRUCTURE_ELEMENT_ID, STATE, SUBMITABLE, REJECTABLE, VERSION_NUM, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME) values(?, ?, ?, ?, ?, 0, 0, sysdate, sysdate) ";
/*      */   private static final String BATCH_STATE_UPDATE = "update budget_state set STATE = ?, SUBMITABLE = ?, REJECTABLE = ?, UPDATED_TIME = sysdate where BUDGET_CYCLE_ID = ? and STRUCTURE_ELEMENT_ID = ?";
/*      */   private static final String BATCH_STATE_HISTORY_INSERT = "insert into budget_state_history (BUDGET_STATE_HISTORY_ID, BUDGET_CYCLE_ID, STRUCTURE_ELEMENT_ID, PREVIOUS_STATE, NEW_STATE, CHANGED_TIME, USER_ID) values(?, ?, ?, ?, ?, sysdate, 0) ";
/*      */   private static final String GET_BUDGET_STATE_SEQUENCE = "select SEQ_NUM from MODEL_SEQ for update";
/*      */   private static final String UPDATE_BUDGET_STATE_SEQUENCE = "update MODEL_SEQ set SEQ_NUM = ?";
/*      */   protected BudgetStateHistoryDAO mBudgetStateHistoryDAO;
/*      */   protected BudgetStateEVO mDetails;
/*      */ 
/*      */   public BudgetStateDAO(Connection connection)
/*      */   {
/*   39 */     super(connection);
/*      */   }
/*      */ 
/*      */   public BudgetStateDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public BudgetStateDAO(DataSource ds)
/*      */   {
/*   55 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected BudgetStatePK getPK()
/*      */   {
/*   63 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(BudgetStateEVO details)
/*      */   {
/*   72 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private BudgetStateEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   92 */     int col = 1;
/*   93 */     BudgetStateEVO evo = new BudgetStateEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), null);
/*      */ 
/*  103 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  104 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  105 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  106 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(BudgetStateEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  111 */     int col = startCol_;
/*  112 */     stmt_.setInt(col++, evo_.getBudgetCycleId());
/*  113 */     stmt_.setInt(col++, evo_.getStructureElementId());
/*  114 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(BudgetStateEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  119 */     int col = startCol_;
/*  120 */     stmt_.setInt(col++, evo_.getState());
/*  121 */     if (evo_.getSubmitable())
/*  122 */       stmt_.setString(col++, "Y");
/*      */     else
/*  124 */       stmt_.setString(col++, " ");
/*  125 */     if (evo_.getRejectable())
/*  126 */       stmt_.setString(col++, "Y");
/*      */     else
/*  128 */       stmt_.setString(col++, " ");
/*  129 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  130 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  131 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  132 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  133 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(BudgetStatePK pk)
/*      */     throws ValidationException
/*      */   {
/*  150 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  152 */     PreparedStatement stmt = null;
/*  153 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  157 */       stmt = getConnection().prepareStatement("select BUDGET_STATE.BUDGET_CYCLE_ID,BUDGET_STATE.STRUCTURE_ELEMENT_ID,BUDGET_STATE.STATE,BUDGET_STATE.SUBMITABLE,BUDGET_STATE.REJECTABLE,BUDGET_STATE.VERSION_NUM,BUDGET_STATE.UPDATED_BY_USER_ID,BUDGET_STATE.UPDATED_TIME,BUDGET_STATE.CREATED_TIME from BUDGET_STATE where    BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*      */ 
/*  160 */       int col = 1;
/*  161 */       stmt.setInt(col++, pk.getBudgetCycleId());
/*  162 */       stmt.setInt(col++, pk.getStructureElementId());
/*      */ 
/*  164 */       resultSet = stmt.executeQuery();
/*      */ 
/*  166 */       if (!resultSet.next()) {
/*  167 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  170 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  171 */       if (this.mDetails.isModified())
/*  172 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  176 */       throw handleSQLException(pk, "select BUDGET_STATE.BUDGET_CYCLE_ID,BUDGET_STATE.STRUCTURE_ELEMENT_ID,BUDGET_STATE.STATE,BUDGET_STATE.SUBMITABLE,BUDGET_STATE.REJECTABLE,BUDGET_STATE.VERSION_NUM,BUDGET_STATE.UPDATED_BY_USER_ID,BUDGET_STATE.UPDATED_TIME,BUDGET_STATE.CREATED_TIME from BUDGET_STATE where    BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  180 */       closeResultSet(resultSet);
/*  181 */       closeStatement(stmt);
/*  182 */       closeConnection();
/*      */ 
/*  184 */       if (timer != null)
/*  185 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  220 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  221 */     this.mDetails.postCreateInit();
/*      */ 
/*  223 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  228 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  229 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  230 */       stmt = getConnection().prepareStatement("insert into BUDGET_STATE ( BUDGET_CYCLE_ID,STRUCTURE_ELEMENT_ID,STATE,SUBMITABLE,REJECTABLE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  233 */       int col = 1;
/*  234 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  235 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  238 */       int resultCount = stmt.executeUpdate();
/*  239 */       if (resultCount != 1)
/*      */       {
/*  241 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  244 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  248 */       throw handleSQLException(this.mDetails.getPK(), "insert into BUDGET_STATE ( BUDGET_CYCLE_ID,STRUCTURE_ELEMENT_ID,STATE,SUBMITABLE,REJECTABLE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  252 */       closeStatement(stmt);
/*  253 */       closeConnection();
/*      */ 
/*  255 */       if (timer != null) {
/*  256 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  262 */       getBudgetStateHistoryDAO().update(this.mDetails.getBudgetCycleHistoryMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  268 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  296 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  300 */     PreparedStatement stmt = null;
/*      */ 
/*  302 */     boolean mainChanged = this.mDetails.isModified();
/*  303 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  307 */       if (getBudgetStateHistoryDAO().update(this.mDetails.getBudgetCycleHistoryMap())) {
/*  308 */         dependantChanged = true;
/*      */       }
/*  310 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  313 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  316 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  317 */         stmt = getConnection().prepareStatement("update BUDGET_STATE set STATE = ?,SUBMITABLE = ?,REJECTABLE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  320 */         int col = 1;
/*  321 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  322 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  324 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  327 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  329 */         if (resultCount == 0) {
/*  330 */           checkVersionNum();
/*      */         }
/*  332 */         if (resultCount != 1) {
/*  333 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  336 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  345 */       throw handleSQLException(getPK(), "update BUDGET_STATE set STATE = ?,SUBMITABLE = ?,REJECTABLE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  349 */       closeStatement(stmt);
/*  350 */       closeConnection();
/*      */ 
/*  352 */       if ((timer != null) && (
/*  353 */         (mainChanged) || (dependantChanged)))
/*  354 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  367 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  368 */     PreparedStatement stmt = null;
/*  369 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  373 */       stmt = getConnection().prepareStatement("select VERSION_NUM from BUDGET_STATE where BUDGET_CYCLE_ID = ?,STRUCTURE_ELEMENT_ID = ?");
/*      */ 
/*  376 */       int col = 1;
/*  377 */       stmt.setInt(col++, this.mDetails.getBudgetCycleId());
/*  378 */       stmt.setInt(col++, this.mDetails.getStructureElementId());
/*      */ 
/*  381 */       resultSet = stmt.executeQuery();
/*      */ 
/*  383 */       if (!resultSet.next()) {
/*  384 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  387 */       col = 1;
/*  388 */       int dbVersionNumber = resultSet.getInt(col++);
/*  389 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  390 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  396 */       throw handleSQLException(getPK(), "select VERSION_NUM from BUDGET_STATE where BUDGET_CYCLE_ID = ?,STRUCTURE_ELEMENT_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  400 */       closeStatement(stmt);
/*  401 */       closeResultSet(resultSet);
/*      */ 
/*  403 */       if (timer != null)
/*  404 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public CheckIfHasStateELO getCheckIfHasState(int param1, int param2)
/*      */   {
/*  452 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  453 */     PreparedStatement stmt = null;
/*  454 */     ResultSet resultSet = null;
/*  455 */     CheckIfHasStateELO results = new CheckIfHasStateELO();
/*      */     try
/*      */     {
/*  458 */       stmt = getConnection().prepareStatement(SQL_CHECK_IF_HAS_STATE);
/*  459 */       int col = 1;
/*  460 */       stmt.setInt(col++, param1);
/*  461 */       stmt.setInt(col++, param2);
/*  462 */       resultSet = stmt.executeQuery();
/*  463 */       while (resultSet.next())
/*      */       {
/*  465 */         col = 2;
/*      */ 
/*  468 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  471 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  473 */         BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(resultSet.getInt(col++));
/*      */ 
/*  476 */         String textBudgetCycle = resultSet.getString(col++);
/*      */ 
/*  479 */         BudgetStatePK pkBudgetState = new BudgetStatePK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  483 */         String textBudgetState = "";
/*      */ 
/*  488 */         BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
/*      */ 
/*  494 */         BudgetStateCK ckBudgetState = new BudgetStateCK(pkModel, pkBudgetCycle, pkBudgetState);
/*      */ 
/*  501 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  507 */         BudgetCycleRefImpl erBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);
/*      */ 
/*  513 */         BudgetStateRefImpl erBudgetState = new BudgetStateRefImpl(ckBudgetState, textBudgetState);
/*      */ 
/*  518 */         int col1 = resultSet.getInt(col++);
/*  519 */         int col2 = resultSet.getInt(col++);
/*  520 */         int col3 = resultSet.getInt(col++);
/*      */ 
/*  523 */         results.add(erBudgetState, erBudgetCycle, erModel, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  535 */       throw handleSQLException(SQL_CHECK_IF_HAS_STATE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  539 */       closeResultSet(resultSet);
/*  540 */       closeStatement(stmt);
/*  541 */       closeConnection();
/*      */     }
/*      */ 
/*  544 */     if (timer != null) {
/*  545 */       timer.logDebug("getCheckIfHasState", " BudgetCycleId=" + param1 + ",StructureElementId=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/*  551 */     return results;
/*      */   }
/*      */ 
/*      */   public CycleStateDetailsELO getCycleStateDetails(int param1)
/*      */   {
/*  597 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  598 */     PreparedStatement stmt = null;
/*  599 */     ResultSet resultSet = null;
/*  600 */     CycleStateDetailsELO results = new CycleStateDetailsELO();
/*      */     try
/*      */     {
/*  603 */       stmt = getConnection().prepareStatement(SQL_CYCLE_STATE_DETAILS);
/*  604 */       int col = 1;
/*  605 */       stmt.setInt(col++, param1);
/*  606 */       resultSet = stmt.executeQuery();
/*  607 */       while (resultSet.next())
/*      */       {
/*  609 */         col = 2;
/*      */ 
/*  612 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  615 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  617 */         BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(resultSet.getInt(col++));
/*      */ 
/*  620 */         String textBudgetCycle = resultSet.getString(col++);
/*      */ 
/*  623 */         BudgetStatePK pkBudgetState = new BudgetStatePK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  627 */         String textBudgetState = "";
/*      */ 
/*  632 */         BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
/*      */ 
/*  638 */         BudgetStateCK ckBudgetState = new BudgetStateCK(pkModel, pkBudgetCycle, pkBudgetState);
/*      */ 
/*  645 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  651 */         BudgetCycleRefImpl erBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);
/*      */ 
/*  657 */         BudgetStateRefImpl erBudgetState = new BudgetStateRefImpl(ckBudgetState, textBudgetState);
/*      */ 
/*  662 */         int col1 = resultSet.getInt(col++);
/*  663 */         int col2 = resultSet.getInt(col++);
/*  664 */         String col3 = resultSet.getString(col++);
/*  665 */         if (resultSet.wasNull())
/*  666 */           col3 = "";
/*  667 */         String col4 = resultSet.getString(col++);
/*  668 */         if (resultSet.wasNull()) {
/*  669 */           col4 = "";
/*      */         }
/*      */ 
/*  672 */         results.add(erBudgetState, erBudgetCycle, erModel, col1, col2, col3.equals("Y"), col4.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  685 */       throw handleSQLException(SQL_CYCLE_STATE_DETAILS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  689 */       closeResultSet(resultSet);
/*  690 */       closeStatement(stmt);
/*  691 */       closeConnection();
/*      */     }
/*      */ 
/*  694 */     if (timer != null) {
/*  695 */       timer.logDebug("getCycleStateDetails", " BudgetCycleId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  700 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  718 */     if (items == null) {
/*  719 */       return false;
/*      */     }
/*  721 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  722 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  724 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  728 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  729 */       while (iter3.hasNext())
/*      */       {
/*  731 */         this.mDetails = ((BudgetStateEVO)iter3.next());
/*  732 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  734 */         somethingChanged = true;
/*      */ 
/*  737 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  741 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  742 */       while (iter2.hasNext())
/*      */       {
/*  744 */         this.mDetails = ((BudgetStateEVO)iter2.next());
/*      */ 
/*  747 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  749 */         somethingChanged = true;
/*      */ 
/*  752 */         if (deleteStmt == null) {
/*  753 */           deleteStmt = getConnection().prepareStatement("delete from BUDGET_STATE where    BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*      */         }
/*      */ 
/*  756 */         int col = 1;
/*  757 */         deleteStmt.setInt(col++, this.mDetails.getBudgetCycleId());
/*  758 */         deleteStmt.setInt(col++, this.mDetails.getStructureElementId());
/*      */ 
/*  760 */         if (this._log.isDebugEnabled()) {
/*  761 */           this._log.debug("update", "BudgetState deleting BudgetCycleId=" + this.mDetails.getBudgetCycleId() + ",StructureElementId=" + this.mDetails.getStructureElementId());
/*      */         }
/*      */ 
/*  767 */         deleteStmt.addBatch();
/*      */ 
/*  770 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  775 */       if (deleteStmt != null)
/*      */       {
/*  777 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  779 */         deleteStmt.executeBatch();
/*      */ 
/*  781 */         if (timer2 != null) {
/*  782 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  786 */       Iterator iter1 = items.values().iterator();
/*  787 */       while (iter1.hasNext())
/*      */       {
/*  789 */         this.mDetails = ((BudgetStateEVO)iter1.next());
/*      */ 
/*  791 */         if (this.mDetails.insertPending())
/*      */         {
/*  793 */           somethingChanged = true;
/*  794 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  797 */         if (this.mDetails.isModified())
/*      */         {
/*  799 */           somethingChanged = true;
/*  800 */           doStore(); continue;
/*      */         }
/*      */ 
/*  804 */         if ((this.mDetails.deletePending()) || 
/*  810 */           (!getBudgetStateHistoryDAO().update(this.mDetails.getBudgetCycleHistoryMap()))) continue;
/*  811 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  823 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  827 */       throw handleSQLException("delete from BUDGET_STATE where    BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  831 */       if (deleteStmt != null)
/*      */       {
/*  833 */         closeStatement(deleteStmt);
/*  834 */         closeConnection();
/*      */       }
/*      */ 
/*  837 */       this.mDetails = null;
/*      */ 
/*  839 */       if ((somethingChanged) && 
/*  840 */         (timer != null))
/*  841 */         timer.logDebug("update", "collection"); 
/*  841 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(BudgetStatePK pk)
/*      */   {
/*  869 */     Set emptyStrings = Collections.emptySet();
/*  870 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(BudgetStatePK pk, Set<String> exclusionTables)
/*      */   {
/*  876 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  878 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  880 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  882 */       PreparedStatement stmt = null;
/*      */ 
/*  884 */       int resultCount = 0;
/*  885 */       String s = null;
/*      */       try
/*      */       {
/*  888 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  890 */         if (this._log.isDebugEnabled()) {
/*  891 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  893 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  896 */         int col = 1;
/*  897 */         stmt.setInt(col++, pk.getBudgetCycleId());
/*  898 */         stmt.setInt(col++, pk.getStructureElementId());
/*      */ 
/*  901 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  905 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  909 */         closeStatement(stmt);
/*  910 */         closeConnection();
/*      */ 
/*  912 */         if (timer != null) {
/*  913 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  917 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  919 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  921 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  923 */       PreparedStatement stmt = null;
/*      */ 
/*  925 */       int resultCount = 0;
/*  926 */       String s = null;
/*      */       try
/*      */       {
/*  929 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  931 */         if (this._log.isDebugEnabled()) {
/*  932 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  934 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  937 */         int col = 1;
/*  938 */         stmt.setInt(col++, pk.getBudgetCycleId());
/*  939 */         stmt.setInt(col++, pk.getStructureElementId());
/*      */ 
/*  942 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  946 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  950 */         closeStatement(stmt);
/*  951 */         closeConnection();
/*      */ 
/*  953 */         if (timer != null)
/*  954 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*      */   {
/*  979 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  981 */     PreparedStatement stmt = null;
/*  982 */     ResultSet resultSet = null;
/*      */ 
/*  984 */     int itemCount = 0;
/*      */ 
/*  986 */     BudgetCycleEVO owningEVO = null;
/*  987 */     Iterator ownersIter = owners.iterator();
/*  988 */     while (ownersIter.hasNext())
/*      */     {
/*  990 */       owningEVO = (BudgetCycleEVO)ownersIter.next();
/*  991 */       owningEVO.setBudgetCycleStatesAllItemsLoaded(true);
/*      */     }
/*  993 */     ownersIter = owners.iterator();
/*  994 */     owningEVO = (BudgetCycleEVO)ownersIter.next();
/*  995 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  999 */       stmt = getConnection().prepareStatement("select BUDGET_STATE.BUDGET_CYCLE_ID,BUDGET_STATE.STRUCTURE_ELEMENT_ID,BUDGET_STATE.STATE,BUDGET_STATE.SUBMITABLE,BUDGET_STATE.REJECTABLE,BUDGET_STATE.VERSION_NUM,BUDGET_STATE.UPDATED_BY_USER_ID,BUDGET_STATE.UPDATED_TIME,BUDGET_STATE.CREATED_TIME from BUDGET_STATE,BUDGET_CYCLE where 1=1 and BUDGET_STATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.MODEL_ID = ? order by  BUDGET_STATE.BUDGET_CYCLE_ID ,BUDGET_STATE.BUDGET_CYCLE_ID ,BUDGET_STATE.STRUCTURE_ELEMENT_ID");
/*      */ 
/* 1001 */       int col = 1;
/* 1002 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/* 1004 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1007 */       while (resultSet.next())
/*      */       {
/* 1009 */         itemCount++;
/* 1010 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1015 */         while (this.mDetails.getBudgetCycleId() != owningEVO.getBudgetCycleId())
/*      */         {
/* 1019 */           if (!ownersIter.hasNext())
/*      */           {
/* 1021 */             this._log.debug("bulkGetAll", "can't find owning [BudgetCycleId=" + this.mDetails.getBudgetCycleId() + "] for " + this.mDetails.getPK());
/*      */ 
/* 1025 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 1026 */             ownersIter = owners.iterator();
/* 1027 */             while (ownersIter.hasNext())
/*      */             {
/* 1029 */               owningEVO = (BudgetCycleEVO)ownersIter.next();
/* 1030 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/* 1032 */             throw new IllegalStateException("can't find owner");
/*      */           }
/* 1034 */           owningEVO = (BudgetCycleEVO)ownersIter.next();
/*      */         }
/* 1036 */         if (owningEVO.getBudgetCycleStates() == null)
/*      */         {
/* 1038 */           theseItems = new ArrayList();
/* 1039 */           owningEVO.setBudgetCycleStates(theseItems);
/* 1040 */           owningEVO.setBudgetCycleStatesAllItemsLoaded(true);
/*      */         }
/* 1042 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/* 1045 */       if (timer != null) {
/* 1046 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/* 1049 */       if ((itemCount > 0) && (dependants.indexOf("<13>") > -1))
/*      */       {
/* 1051 */         getBudgetStateHistoryDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/* 1055 */       throw handleSQLException("select BUDGET_STATE.BUDGET_CYCLE_ID,BUDGET_STATE.STRUCTURE_ELEMENT_ID,BUDGET_STATE.STATE,BUDGET_STATE.SUBMITABLE,BUDGET_STATE.REJECTABLE,BUDGET_STATE.VERSION_NUM,BUDGET_STATE.UPDATED_BY_USER_ID,BUDGET_STATE.UPDATED_TIME,BUDGET_STATE.CREATED_TIME from BUDGET_STATE,BUDGET_CYCLE where 1=1 and BUDGET_STATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.MODEL_ID = ? order by  BUDGET_STATE.BUDGET_CYCLE_ID ,BUDGET_STATE.BUDGET_CYCLE_ID ,BUDGET_STATE.STRUCTURE_ELEMENT_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1059 */       closeResultSet(resultSet);
/* 1060 */       closeStatement(stmt);
/* 1061 */       closeConnection();
/*      */ 
/* 1063 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectBudgetCycleId, String dependants, Collection currentList)
/*      */   {
/* 1088 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1089 */     PreparedStatement stmt = null;
/* 1090 */     ResultSet resultSet = null;
/*      */ 
/* 1092 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/* 1096 */       stmt = getConnection().prepareStatement("select BUDGET_STATE.BUDGET_CYCLE_ID,BUDGET_STATE.STRUCTURE_ELEMENT_ID,BUDGET_STATE.STATE,BUDGET_STATE.SUBMITABLE,BUDGET_STATE.REJECTABLE,BUDGET_STATE.VERSION_NUM,BUDGET_STATE.UPDATED_BY_USER_ID,BUDGET_STATE.UPDATED_TIME,BUDGET_STATE.CREATED_TIME from BUDGET_STATE where    BUDGET_CYCLE_ID = ? ");
/*      */ 
/* 1098 */       int col = 1;
/* 1099 */       stmt.setInt(col++, selectBudgetCycleId);
/*      */ 
/* 1101 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1103 */       while (resultSet.next())
/*      */       {
/* 1105 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1108 */         getDependants(this.mDetails, dependants);
/*      */ 
/* 1111 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1114 */       if (currentList != null)
/*      */       {
/* 1117 */         ListIterator iter = items.listIterator();
/* 1118 */         BudgetStateEVO currentEVO = null;
/* 1119 */         BudgetStateEVO newEVO = null;
/* 1120 */         while (iter.hasNext())
/*      */         {
/* 1122 */           newEVO = (BudgetStateEVO)iter.next();
/* 1123 */           Iterator iter2 = currentList.iterator();
/* 1124 */           while (iter2.hasNext())
/*      */           {
/* 1126 */             currentEVO = (BudgetStateEVO)iter2.next();
/* 1127 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1129 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1135 */         Iterator iter2 = currentList.iterator();
/* 1136 */         while (iter2.hasNext())
/*      */         {
/* 1138 */           currentEVO = (BudgetStateEVO)iter2.next();
/* 1139 */           if (currentEVO.insertPending()) {
/* 1140 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1144 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1148 */       throw handleSQLException("select BUDGET_STATE.BUDGET_CYCLE_ID,BUDGET_STATE.STRUCTURE_ELEMENT_ID,BUDGET_STATE.STATE,BUDGET_STATE.SUBMITABLE,BUDGET_STATE.REJECTABLE,BUDGET_STATE.VERSION_NUM,BUDGET_STATE.UPDATED_BY_USER_ID,BUDGET_STATE.UPDATED_TIME,BUDGET_STATE.CREATED_TIME from BUDGET_STATE where    BUDGET_CYCLE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1152 */       closeResultSet(resultSet);
/* 1153 */       closeStatement(stmt);
/* 1154 */       closeConnection();
/*      */ 
/* 1156 */       if (timer != null) {
/* 1157 */         timer.logDebug("getAll", " BudgetCycleId=" + selectBudgetCycleId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1162 */     return items;
/*      */   }
/*      */ 
/*      */   public BudgetStateEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1179 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1182 */     if (this.mDetails == null) {
/* 1183 */       doLoad(((BudgetStateCK)paramCK).getBudgetStatePK());
/*      */     }
/* 1185 */     else if (!this.mDetails.getPK().equals(((BudgetStateCK)paramCK).getBudgetStatePK())) {
/* 1186 */       doLoad(((BudgetStateCK)paramCK).getBudgetStatePK());
/*      */     }
/*      */ 
/* 1189 */     if ((dependants.indexOf("<13>") > -1) && (!this.mDetails.isBudgetCycleHistoryAllItemsLoaded()))
/*      */     {
/* 1194 */       this.mDetails.setBudgetCycleHistory(getBudgetStateHistoryDAO().getAll(this.mDetails.getBudgetCycleId(), this.mDetails.getStructureElementId(), dependants, this.mDetails.getBudgetCycleHistory()));
/*      */ 
/* 1202 */       this.mDetails.setBudgetCycleHistoryAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1205 */     if ((paramCK instanceof BudgetStateHistoryCK))
/*      */     {
/* 1207 */       if (this.mDetails.getBudgetCycleHistory() == null) {
/* 1208 */         this.mDetails.loadBudgetCycleHistoryItem(getBudgetStateHistoryDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1211 */         BudgetStateHistoryPK pk = ((BudgetStateHistoryCK)paramCK).getBudgetStateHistoryPK();
/* 1212 */         BudgetStateHistoryEVO evo = this.mDetails.getBudgetCycleHistoryItem(pk);
/* 1213 */         if (evo == null) {
/* 1214 */           this.mDetails.loadBudgetCycleHistoryItem(getBudgetStateHistoryDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1219 */     BudgetStateEVO details = new BudgetStateEVO();
/* 1220 */     details = this.mDetails.deepClone();
/*      */ 
/* 1222 */     if (timer != null) {
/* 1223 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1225 */     return details;
/*      */   }
/*      */ 
/*      */   public BudgetStateEVO getDetails(ModelCK paramCK, BudgetStateEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1231 */     BudgetStateEVO savedEVO = this.mDetails;
/* 1232 */     this.mDetails = paramEVO;
/* 1233 */     BudgetStateEVO newEVO = getDetails(paramCK, dependants);
/* 1234 */     this.mDetails = savedEVO;
/* 1235 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public BudgetStateEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1241 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1245 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1248 */     BudgetStateEVO details = this.mDetails.deepClone();
/*      */ 
/* 1250 */     if (timer != null) {
/* 1251 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1253 */     return details;
/*      */   }
/*      */ 
/*      */   protected BudgetStateHistoryDAO getBudgetStateHistoryDAO()
/*      */   {
/* 1262 */     if (this.mBudgetStateHistoryDAO == null)
/*      */     {
/* 1264 */       if (this.mDataSource != null)
/* 1265 */         this.mBudgetStateHistoryDAO = new BudgetStateHistoryDAO(this.mDataSource);
/*      */       else {
/* 1267 */         this.mBudgetStateHistoryDAO = new BudgetStateHistoryDAO(getConnection());
/*      */       }
/*      */     }
/* 1270 */     return this.mBudgetStateHistoryDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1275 */     return "BudgetState";
/*      */   }
/*      */ 
/*      */   public BudgetStateRefImpl getRef(BudgetStatePK paramBudgetStatePK)
/*      */   {
/* 1280 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1281 */     PreparedStatement stmt = null;
/* 1282 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1285 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,BUDGET_CYCLE.BUDGET_CYCLE_ID from BUDGET_STATE,MODEL,BUDGET_CYCLE where 1=1 and BUDGET_STATE.BUDGET_CYCLE_ID = ? and BUDGET_STATE.STRUCTURE_ELEMENT_ID = ? and BUDGET_STATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.BUDGET_CYCLE_ID = MODEL.BUDGET_CYCLE_ID");
/* 1286 */       int col = 1;
/* 1287 */       stmt.setInt(col++, paramBudgetStatePK.getBudgetCycleId());
/* 1288 */       stmt.setInt(col++, paramBudgetStatePK.getStructureElementId());
/*      */ 
/* 1290 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1292 */       if (!resultSet.next()) {
/* 1293 */         throw new RuntimeException(getEntityName() + " getRef " + paramBudgetStatePK + " not found");
/*      */       }
/* 1295 */       col = 2;
/* 1296 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1300 */       BudgetCyclePK newBudgetCyclePK = new BudgetCyclePK(resultSet.getInt(col++));
/*      */ 
/* 1304 */       String textBudgetState = "";
/* 1305 */       BudgetStateCK ckBudgetState = new BudgetStateCK(newModelPK, newBudgetCyclePK, paramBudgetStatePK);
/*      */ 
/* 1311 */       BudgetStateRefImpl localBudgetStateRefImpl = new BudgetStateRefImpl(ckBudgetState, textBudgetState);
/*      */       return localBudgetStateRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1316 */       throw handleSQLException(paramBudgetStatePK, "select 0,MODEL.MODEL_ID,BUDGET_CYCLE.BUDGET_CYCLE_ID from BUDGET_STATE,MODEL,BUDGET_CYCLE where 1=1 and BUDGET_STATE.BUDGET_CYCLE_ID = ? and BUDGET_STATE.STRUCTURE_ELEMENT_ID = ? and BUDGET_STATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.BUDGET_CYCLE_ID = MODEL.BUDGET_CYCLE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1320 */       closeResultSet(resultSet);
/* 1321 */       closeStatement(stmt);
/* 1322 */       closeConnection();
/*      */ 
/* 1324 */       if (timer != null)
/* 1325 */         timer.logDebug("getRef", paramBudgetStatePK); 
/* 1325 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1337 */     if (c == null)
/* 1338 */       return;
/* 1339 */     Iterator iter = c.iterator();
/* 1340 */     while (iter.hasNext())
/*      */     {
/* 1342 */       BudgetStateEVO evo = (BudgetStateEVO)iter.next();
/* 1343 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(BudgetStateEVO evo, String dependants)
/*      */   {
/* 1357 */     if (evo.insertPending()) {
/* 1358 */       return;
/*      */     }
/*      */ 
/* 1362 */     if (dependants.indexOf("<13>") > -1)
/*      */     {
/* 1365 */       if (!evo.isBudgetCycleHistoryAllItemsLoaded())
/*      */       {
/* 1367 */         evo.setBudgetCycleHistory(getBudgetStateHistoryDAO().getAll(evo.getBudgetCycleId(), evo.getStructureElementId(), dependants, evo.getBudgetCycleHistory()));
/*      */ 
/* 1375 */         evo.setBudgetCycleHistoryAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getCurrentState(int budgetCycleId, int structureElementId)
/*      */   {
/* 1391 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1392 */     PreparedStatement stmt = null;
/* 1393 */     ResultSet resultSet = null;
/*      */ 
/* 1395 */     int result = 0;
/*      */     try
/*      */     {
/* 1398 */       int col = 1;
/*      */ 
/* 1400 */       stmt = getConnection().prepareStatement("select state   from budget_state  where budget_cycle_id = ?    and structure_element_id = ? ");
/* 1401 */       stmt.setInt(col++, budgetCycleId);
/* 1402 */       stmt.setInt(col++, structureElementId);
/*      */ 
/* 1404 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1406 */       if (resultSet.next())
/*      */       {
/* 1408 */         result = resultSet.getInt(1);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1413 */       System.err.println(sqle);
/* 1414 */       sqle.printStackTrace();
/* 1415 */       throw new RuntimeException(getEntityName() + " getCurrentState", sqle);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1419 */       e.printStackTrace();
/* 1420 */       throw new RuntimeException(getEntityName() + " getCurrentState", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1424 */       closeResultSet(resultSet);
/* 1425 */       closeStatement(stmt);
/* 1426 */       closeConnection();
/*      */     }
/*      */ 
/* 1429 */     if (timer != null) {
/* 1430 */       timer.logDebug("getCurrentState", "");
/*      */     }
/* 1432 */     return result;
/*      */   }
/*      */ 
/*      */   public int getAgreeCount(int structure_id, int parent_id, int budgetCycleId)
/*      */   {
/* 1448 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1449 */     PreparedStatement stmt = null;
/* 1450 */     ResultSet resultSet = null;
/*      */ 
/* 1452 */     int result = 0;
/*      */     try
/*      */     {
/* 1455 */       int col = 1;
/*      */ 
/* 1457 */       stmt = getConnection().prepareStatement("select count(*) agree_count from structure_element ,budget_state where structure_element.structure_id = ? and structure_element.parent_id = ? and structure_element.structure_element_id = budget_state.structure_element_id and ( budget_state.state = 4 or budget_state.state = 5 or budget_state.state = 6 ) and budget_state.budget_cycle_id = ?");
/* 1458 */       stmt.setInt(col++, structure_id);
/* 1459 */       stmt.setInt(col++, parent_id);
/* 1460 */       stmt.setInt(col++, budgetCycleId);
/*      */ 
/* 1462 */       resultSet = stmt.executeQuery();
/* 1463 */       while (resultSet.next())
/*      */       {
/* 1465 */         col = 1;
/*      */ 
/* 1467 */         result = resultSet.getInt(col++);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1472 */       System.err.println(sqle);
/* 1473 */       sqle.printStackTrace();
/* 1474 */       throw new RuntimeException(getEntityName() + " BudgetDetailsForUser", sqle);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1478 */       e.printStackTrace();
/* 1479 */       throw new RuntimeException(getEntityName() + " BudgetDetailsForUser", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1483 */       closeResultSet(resultSet);
/* 1484 */       closeStatement(stmt);
/* 1485 */       closeConnection();
/*      */     }
/*      */ 
/* 1488 */     if (timer != null) {
/* 1489 */       timer.logDebug("getAgreedCount", "");
/*      */     }
/* 1491 */     return result;
/*      */   }
/*      */ 
/*      */   public void tidyOrphanBudgetStates(int modelId, int structureId)
/*      */   {
/* 1513 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/* 1516 */       ps = getConnection().prepareStatement("delete \nfrom budget_state \nwhere ( budget_cycle_id, structure_element_id ) in \n\t\t( select state.budget_cycle_id, state.structure_element_id \n\t\t  from budget_state state, budget_cycle cycle \n\t\t  where cycle.model_id = ? and \n\t\t\t\tcycle.budget_cycle_id = state.budget_cycle_id and \n\t\t\t\tstate.structure_element_id not in ( select structure_element_id \n\t\t\t\t\t\t\t\t\t\t\t\t\tfrom structure_element \n\t\t\t\t\t\t\t\t\t\t\t\t\twhere structure_id = ? ) )");
/* 1517 */       ps.setInt(1, modelId);
/* 1518 */       ps.setInt(2, structureId);
/* 1519 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1523 */       throw handleSQLException("Failed to tidy orphan budget states", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1527 */       closeStatement(ps);
/* 1528 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void batchInsert(Map<Integer, BudgetStateEVO> insertMap)
/*      */   {
/* 1540 */     PreparedStatement ps = null;
/* 1541 */     BudgetStateEVO evo = null;
/* 1542 */     Iterator iter = insertMap.values().iterator();
/*      */     try
/*      */     {
/* 1545 */       ps = getConnection().prepareStatement("insert into budget_state (BUDGET_CYCLE_ID, STRUCTURE_ELEMENT_ID, STATE, SUBMITABLE, REJECTABLE, VERSION_NUM, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME) values(?, ?, ?, ?, ?, 0, 0, sysdate, sysdate) ");
/* 1546 */       while (iter.hasNext())
/*      */       {
/* 1548 */         int col = 1;
/* 1549 */         evo = (BudgetStateEVO)iter.next();
/* 1550 */         ps.setInt(col++, evo.getBudgetCycleId());
/* 1551 */         ps.setInt(col++, evo.getStructureElementId());
/* 1552 */         ps.setInt(col++, evo.getState());
/* 1553 */         if (evo.getSubmitable())
/* 1554 */           ps.setString(col++, "Y");
/*      */         else
/* 1556 */           ps.setString(col++, " ");
/* 1557 */         if (evo.getRejectable())
/* 1558 */           ps.setString(col++, "Y");
/*      */         else
/* 1560 */           ps.setString(col++, " ");
/* 1561 */         ps.addBatch();
/*      */       }
/* 1563 */       ps.executeBatch();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1567 */       throw handleSQLException("batchInsert failed", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1571 */       closeStatement(ps);
/* 1572 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void batchUpdate(Map<Integer, BudgetStateEVO> updateMap)
/*      */   {
/* 1583 */     PreparedStatement ps = null;
/* 1584 */     BudgetStateEVO evo = null;
/* 1585 */     Iterator iter = updateMap.values().iterator();
/*      */     try
/*      */     {
/* 1588 */       ps = getConnection().prepareStatement("update budget_state set STATE = ?, SUBMITABLE = ?, REJECTABLE = ?, UPDATED_TIME = sysdate where BUDGET_CYCLE_ID = ? and STRUCTURE_ELEMENT_ID = ?");
/* 1589 */       while (iter.hasNext())
/*      */       {
/* 1591 */         int col = 1;
/* 1592 */         evo = (BudgetStateEVO)iter.next();
/* 1593 */         ps.setInt(col++, evo.getState());
/* 1594 */         if (evo.getSubmitable())
/* 1595 */           ps.setString(col++, "Y");
/*      */         else
/* 1597 */           ps.setString(col++, " ");
/* 1598 */         if (evo.getRejectable())
/* 1599 */           ps.setString(col++, "Y");
/*      */         else {
/* 1601 */           ps.setString(col++, " ");
/*      */         }
/*      */ 
/* 1604 */         ps.setInt(col++, evo.getBudgetCycleId());
/* 1605 */         ps.setInt(col++, evo.getStructureElementId());
/*      */ 
/* 1607 */         ps.addBatch();
/*      */       }
/* 1609 */       ps.executeBatch();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1614 */       throw handleSQLException("batchUpdate failed", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1618 */       closeStatement(ps);
/* 1619 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void batchInsertHistory(Map<Integer, BudgetStateEVO> insertMap, Map<Integer, BudgetStateEVO> updateMap, Map<Integer, EntityList> originalMap)
/*      */   {
/* 1631 */     int seqNum = getBudgetStateSequence();
/*      */ 
/* 1633 */     PreparedStatement ps = null;
/* 1634 */     BudgetStateEVO evo = null;
/*      */     try
/*      */     {
/* 1638 */       ps = getConnection().prepareStatement("insert into budget_state_history (BUDGET_STATE_HISTORY_ID, BUDGET_CYCLE_ID, STRUCTURE_ELEMENT_ID, PREVIOUS_STATE, NEW_STATE, CHANGED_TIME, USER_ID) values(?, ?, ?, ?, ?, sysdate, 0) ");
/*      */ 
/* 1640 */       Iterator iter = insertMap.values().iterator();
/* 1641 */       while (iter.hasNext())
/*      */       {
/* 1643 */         int col = 1;
/* 1644 */         evo = (BudgetStateEVO)iter.next();
/* 1645 */         ps.setInt(col++, seqNum++);
/* 1646 */         ps.setInt(col++, evo.getBudgetCycleId());
/* 1647 */         ps.setInt(col++, evo.getStructureElementId());
/* 1648 */         ps.setInt(col++, 0);
/* 1649 */         ps.setInt(col++, evo.getState());
/* 1650 */         ps.addBatch();
/*      */       }
/*      */ 
/* 1654 */       Integer key = Integer.valueOf(0);
/* 1655 */       iter = updateMap.keySet().iterator();
/* 1656 */       while (iter.hasNext())
/*      */       {
/* 1658 */         key = (Integer)iter.next();
/* 1659 */         int col = 1;
/* 1660 */         evo = (BudgetStateEVO)updateMap.get(key);
/* 1661 */         ps.setInt(col++, seqNum++);
/* 1662 */         ps.setInt(col++, evo.getBudgetCycleId());
/* 1663 */         ps.setInt(col++, evo.getStructureElementId());
/*      */ 
/* 1665 */         ps.setInt(col++, ((Integer)((EntityList)originalMap.get(key)).getValueAt(0, "State")).intValue());
/* 1666 */         ps.setInt(col++, evo.getState());
/* 1667 */         ps.addBatch();
/*      */       }
/*      */ 
/* 1670 */       ps.executeBatch();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1674 */       throw handleSQLException("batchInsert failed", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1678 */       closeStatement(ps);
/* 1679 */       closeConnection();
/*      */     }
/*      */ 
/* 1682 */     updateBudgetStateSequence(seqNum);
/*      */   }
/*      */ 
/*      */   private int getBudgetStateSequence()
/*      */   {
/* 1693 */     PreparedStatement ps = null;
/* 1694 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1697 */       ps = getConnection().prepareStatement("select SEQ_NUM from MODEL_SEQ for update");
/* 1698 */       resultSet = ps.executeQuery();
/* 1699 */       resultSet.next();
/* 1700 */       int seqNum = resultSet.getInt(1);
/* 1701 */       int i = seqNum;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1705 */       throw handleSQLException("getBudgetStateSequence failed", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1709 */       closeResultSet(resultSet);
/* 1710 */       closeStatement(ps);
/* 1711 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   private void updateBudgetStateSequence(int seqNum)
/*      */   {
/* 1717 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/* 1720 */       ps = getConnection().prepareStatement("update MODEL_SEQ set SEQ_NUM = ?");
/* 1721 */       ps.setInt(1, seqNum);
/* 1722 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1726 */       throw handleSQLException("updateBudgetStateSequence failed", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1730 */       closeStatement(ps);
/* 1731 */       closeConnection();
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.BudgetStateDAO
 * JD-Core Version:    0.6.0
 */