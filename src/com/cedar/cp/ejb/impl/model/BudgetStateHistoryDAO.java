/*      */ package com.cedar.cp.ejb.impl.model;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.BudgetCyclePK;
/*      */ import com.cedar.cp.dto.model.BudgetStateHistoryCK;
/*      */ import com.cedar.cp.dto.model.BudgetStateHistoryELO;
/*      */ import com.cedar.cp.dto.model.BudgetStateHistoryPK;
/*      */ import com.cedar.cp.dto.model.BudgetStateHistoryRefImpl;
/*      */ import com.cedar.cp.dto.model.BudgetStatePK;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModernWelcomeELO;
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
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class BudgetStateHistoryDAO extends AbstractDAO
/*      */ {
/*   32 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select BUDGET_STATE_HISTORY.BUDGET_STATE_HISTORY_ID,BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID,BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID,BUDGET_STATE_HISTORY.PREVIOUS_STATE,BUDGET_STATE_HISTORY.NEW_STATE,BUDGET_STATE_HISTORY.CHANGED_TIME,BUDGET_STATE_HISTORY.USER_ID";
/*      */   protected static final String SQL_LOAD = " from BUDGET_STATE_HISTORY where    BUDGET_STATE_HISTORY_ID = ? AND BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into BUDGET_STATE_HISTORY ( BUDGET_STATE_HISTORY_ID,BUDGET_CYCLE_ID,STRUCTURE_ELEMENT_ID,PREVIOUS_STATE,NEW_STATE,CHANGED_TIME,USER_ID) values ( ?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update BUDGET_STATE_HISTORY set PREVIOUS_STATE = ?,NEW_STATE = ?,CHANGED_TIME = ?,USER_ID = ? where    BUDGET_STATE_HISTORY_ID = ? AND BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from BUDGET_STATE_HISTORY where    BUDGET_STATE_HISTORY_ID = ? AND BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*      */   public static final String SQL_BULK_GET_ALL = " from BUDGET_STATE_HISTORY,BUDGET_STATE,BUDGET_CYCLE where 1=1 and BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID = BUDGET_STATE.BUDGET_CYCLE_ID and BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID = BUDGET_STATE.STRUCTURE_ELEMENT_ID and BUDGET_STATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.MODEL_ID = ? order by  BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID ,BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID ,BUDGET_STATE_HISTORY.BUDGET_STATE_HISTORY_ID ,BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID ,BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID";
/*      */   protected static final String SQL_GET_ALL = " from BUDGET_STATE_HISTORY where    BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*      */   private static final String STATE_HISTORY = "select st_lst.budget_cycle_id   ,st_lst.structure_id  ,st_lst.structure_element_id  ,st_lst.vis_id  ,st_lst.description  ,st_lst.depth  ,st_lst.leaf  ,st_lst.position  ,bshm.new_state  ,bshm.changed_time   ,(select distinct 'Y' from budget_user bu where st_lst.structure_element_id = bu.structure_element_id and bu.model_id = st_lst.model_id ) contact  ,(select ld.planned_end_date from level_date ld where ld.budget_cycle_id = st_lst.budget_cycle_id and ld.depth = st_lst.depth) end_date from  (  select    se.structure_id   ,se.structure_element_id   ,se.vis_id   ,se.description   ,se.depth   ,se.leaf   ,se.position   ,bc.budget_cycle_id   ,m.model_id  from    model m   ,budget_cycle bc   ,structure_element se  where m.model_id = bc.model_id and     m.budget_hierarchy_id = se.structure_id and     bc.budget_cycle_id = ?  ) st_lst  ,(   select        budget_cycle_id,        structure_element_id,        new_state,        changed_time         from    (    select         budget_cycle_id,        structure_element_id,        new_state,        changed_time,           rank() over ( partition by budget_cycle_id, structure_element_id order by changed_time desc ) rk     from       budget_state_history    where        budget_cycle_id = ?     ) where rk = 1  ) bshm,  (    select lse.position, lse.end_position from structure_element lse where lse.structure_id = ? and lse.structure_element_id = ?  )  root_node  where     st_lst.budget_cycle_id = bshm.budget_cycle_id (+)    and st_lst.structure_element_id = bshm.structure_element_id (+)    AND st_lst.position >= root_node.position    AND st_lst.position <= root_node.end_position order by st_lst.depth, st_lst.structure_element_id  ";
/*      */   private static final String CONTACT_DUE_LOCATIONS = "select * from (  select st_lst.budget_cycle_id   ,st_lst.structure_id  ,st_lst.structure_element_id  ,st_lst.vis_id  ,st_lst.description  ,st_lst.depth  ,st_lst.leaf  ,st_lst.position   ,st_lst.disabled   ,st_lst.not_plannable   ,bshm.new_state  ,bshm.changed_time   ,(select distinct 'Y' from budget_user bu where st_lst.structure_element_id = bu.structure_element_id and bu.model_id = st_lst.model_id ) contact  ,(select ld.planned_end_date from level_date ld where ld.budget_cycle_id = st_lst.budget_cycle_id and ld.depth = st_lst.depth) end_date from  (  select    se.structure_id   ,se.structure_element_id   ,se.vis_id   ,se.description   ,se.depth   ,se.leaf   ,se.position   ,se.disabled   ,se.not_plannable   ,bc.budget_cycle_id   ,m.model_id  from    model m   ,budget_cycle bc   ,structure_element se  where m.model_id = bc.model_id and     m.budget_hierarchy_id = se.structure_id and     bc.budget_cycle_id = ?  ) st_lst  ,(   select        budget_cycle_id,        structure_element_id,        new_state,        changed_time         from    (    select         budget_cycle_id,        structure_element_id,        new_state,        changed_time,           rank() over ( partition by budget_cycle_id, structure_element_id order by changed_time desc ) rk     from       budget_state_history    where        budget_cycle_id = ?     ) where rk = 1  ) bshm,  (  select   lse.position,   lse.end_position  from   structure_element lse  where   lse.structure_id = (SELECT budget_hierarchy_id FROM MODEL m, budget_cycle bc WHERE m.model_id = bc.model_id and bc.budget_cycle_id = ?) AND lse.parent_id = 0  )  root_node where     st_lst.budget_cycle_id = bshm.budget_cycle_id (+)    and st_lst.structure_element_id = bshm.structure_element_id (+)    AND st_lst.position >= root_node.position    AND st_lst.position <= root_node.end_position    AND st_lst.disabled <> 'Y'    AND st_lst.not_plannable <> 'Y' order by st_lst.depth, st_lst.structure_element_id   ) WHERE contact = 'Y'    AND (new_state IS NULL OR new_state < 4)    AND (end_date < sysdate + ?) ";
/*      */   private static final String TIDY_ORPHAN_STATE_HISTORY_SQL = "delete\nfrom budget_state_history\nwhere budget_state_history_id in\n\t( select budget_state_history_id\n\t  from budget_state_history shist, budget_cycle cycle\n\t  where cycle.model_id = ? and\n\t\t\tcycle.budget_cycle_id = shist.budget_cycle_id and\n\t\t\tshist.structure_element_id not in ( select structure_element_id \n\t\t\t\t\t\t\t\t\t\t\t\tfrom structure_element \n\t\t\t\t\t\t\t\t\t\t\t\twhere structure_id = ? ) )";
/* 1119 */   private static String MODERN_WELCOME_PAGE_INFO = "select * from ( select   st_lst.model_id   ,st_lst.m_visid  ,st_lst.m_description  ,st_lst.budget_cycle_id   ,st_lst.bc_visId  ,st_lst.bc_description  ,st_lst.structure_id   ,st_lst.structure_element_id   ,st_lst.vis_id   ,st_lst.description   ,st_lst.depth   ,st_lst.leaf   ,st_lst.position   ,st_lst.disabled   ,st_lst.not_plannable  ,bshm.new_state   ,bshm.changed_time   ,(select ld.planned_end_date from level_date ld where ld.budget_cycle_id = st_lst.budget_cycle_id and ld.depth = st_lst.depth) end_date  from   (   select     se.structure_id    ,se.structure_element_id    ,se.vis_id    ,se.description    ,se.depth    ,se.leaf    ,se.position    ,se.disabled    ,se.not_plannable    ,bc.budget_cycle_id    ,bc.vis_id bc_visId   ,bc.description bc_description   ,m.model_id    ,m.vis_id m_visid   ,m.description m_description  from    model m    ,budget_cycle bc   ,structure_element se   where m.model_id = bc.model_id and      m.budget_hierarchy_id = se.structure_id  ) st_lst   ,(    select       budget_cycle_id,       structure_element_id,       new_state,        changed_time   from    (    select        budget_cycle_id,        structure_element_id,        new_state,        changed_time,        rank() over ( partition by budget_cycle_id, structure_element_id order by changed_time desc ) rk    from       budget_state_history    ) where rk = 1   ) bshm where    st_lst.budget_cycle_id = bshm.budget_cycle_id (+)     and st_lst.structure_element_id = bshm.structure_element_id (+)     and st_lst.structure_element_id in (    select     STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID from BUDGET_USER    ,MODEL    ,STRUCTURE_ELEMENT where 1=1   and BUDGET_USER.MODEL_ID = MODEL.MODEL_ID   and BUDGET_USER.MODEL_ID in (select distinct m.model_id from  model m  ,budget_cycle bc where m.model_id = bc.model_id and  bc.status = 1)   AND BUDGET_USER.USER_ID = ?   and BUDGET_USER.STRUCTURE_ELEMENT_ID = STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID  )    AND st_lst.disabled <> 'Y'     AND st_lst.not_plannable <> 'Y'   order by st_lst.model_id, st_lst.budget_cycle_id, st_lst.depth, st_lst.structure_element_id   ) WHERE (new_state IS NULL OR new_state < 4)  AND (end_date < sysdate + ?)";
/*      */   protected BudgetStateHistoryEVO mDetails;
/*      */ 
/*      */   public BudgetStateHistoryDAO(Connection connection)
/*      */   {
/*   39 */     super(connection);
/*      */   }
/*      */ 
/*      */   public BudgetStateHistoryDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public BudgetStateHistoryDAO(DataSource ds)
/*      */   {
/*   55 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected BudgetStateHistoryPK getPK()
/*      */   {
/*   63 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(BudgetStateHistoryEVO details)
/*      */   {
/*   72 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private BudgetStateHistoryEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   90 */     int col = 1;
/*   91 */     BudgetStateHistoryEVO evo = new BudgetStateHistoryEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getTimestamp(col++), resultSet_.getInt(col++));
/*      */ 
/*  101 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(BudgetStateHistoryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  106 */     int col = startCol_;
/*  107 */     stmt_.setInt(col++, evo_.getBudgetStateHistoryId());
/*  108 */     stmt_.setInt(col++, evo_.getBudgetCycleId());
/*  109 */     stmt_.setInt(col++, evo_.getStructureElementId());
/*  110 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(BudgetStateHistoryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  115 */     int col = startCol_;
/*  116 */     stmt_.setInt(col++, evo_.getPreviousState());
/*  117 */     stmt_.setInt(col++, evo_.getNewState());
/*  118 */     stmt_.setTimestamp(col++, evo_.getChangedTime());
/*  119 */     stmt_.setInt(col++, evo_.getUserId());
/*  120 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(BudgetStateHistoryPK pk)
/*      */     throws ValidationException
/*      */   {
/*  138 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  140 */     PreparedStatement stmt = null;
/*  141 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  145 */       stmt = getConnection().prepareStatement("select BUDGET_STATE_HISTORY.BUDGET_STATE_HISTORY_ID,BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID,BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID,BUDGET_STATE_HISTORY.PREVIOUS_STATE,BUDGET_STATE_HISTORY.NEW_STATE,BUDGET_STATE_HISTORY.CHANGED_TIME,BUDGET_STATE_HISTORY.USER_ID from BUDGET_STATE_HISTORY where    BUDGET_STATE_HISTORY_ID = ? AND BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*      */ 
/*  148 */       int col = 1;
/*  149 */       stmt.setInt(col++, pk.getBudgetStateHistoryId());
/*  150 */       stmt.setInt(col++, pk.getBudgetCycleId());
/*  151 */       stmt.setInt(col++, pk.getStructureElementId());
/*      */ 
/*  153 */       resultSet = stmt.executeQuery();
/*      */ 
/*  155 */       if (!resultSet.next()) {
/*  156 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  159 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  160 */       if (this.mDetails.isModified())
/*  161 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  165 */       throw handleSQLException(pk, "select BUDGET_STATE_HISTORY.BUDGET_STATE_HISTORY_ID,BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID,BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID,BUDGET_STATE_HISTORY.PREVIOUS_STATE,BUDGET_STATE_HISTORY.NEW_STATE,BUDGET_STATE_HISTORY.CHANGED_TIME,BUDGET_STATE_HISTORY.USER_ID from BUDGET_STATE_HISTORY where    BUDGET_STATE_HISTORY_ID = ? AND BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  169 */       closeResultSet(resultSet);
/*  170 */       closeStatement(stmt);
/*  171 */       closeConnection();
/*      */ 
/*  173 */       if (timer != null)
/*  174 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  205 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  206 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  210 */       stmt = getConnection().prepareStatement("insert into BUDGET_STATE_HISTORY ( BUDGET_STATE_HISTORY_ID,BUDGET_CYCLE_ID,STRUCTURE_ELEMENT_ID,PREVIOUS_STATE,NEW_STATE,CHANGED_TIME,USER_ID) values ( ?,?,?,?,?,?,?)");
/*      */ 
/*  213 */       int col = 1;
/*  214 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  215 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  218 */       int resultCount = stmt.executeUpdate();
/*  219 */       if (resultCount != 1)
/*      */       {
/*  221 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  224 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  228 */       throw handleSQLException(this.mDetails.getPK(), "insert into BUDGET_STATE_HISTORY ( BUDGET_STATE_HISTORY_ID,BUDGET_CYCLE_ID,STRUCTURE_ELEMENT_ID,PREVIOUS_STATE,NEW_STATE,CHANGED_TIME,USER_ID) values ( ?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  232 */       closeStatement(stmt);
/*  233 */       closeConnection();
/*      */ 
/*  235 */       if (timer != null)
/*  236 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  261 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  265 */     PreparedStatement stmt = null;
/*      */ 
/*  267 */     boolean mainChanged = this.mDetails.isModified();
/*  268 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  271 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  273 */         stmt = getConnection().prepareStatement("update BUDGET_STATE_HISTORY set PREVIOUS_STATE = ?,NEW_STATE = ?,CHANGED_TIME = ?,USER_ID = ? where    BUDGET_STATE_HISTORY_ID = ? AND BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*      */ 
/*  276 */         int col = 1;
/*  277 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  278 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  281 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  284 */         if (resultCount != 1) {
/*  285 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  288 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  297 */       throw handleSQLException(getPK(), "update BUDGET_STATE_HISTORY set PREVIOUS_STATE = ?,NEW_STATE = ?,CHANGED_TIME = ?,USER_ID = ? where    BUDGET_STATE_HISTORY_ID = ? AND BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  301 */       closeStatement(stmt);
/*  302 */       closeConnection();
/*      */ 
/*  304 */       if ((timer != null) && (
/*  305 */         (mainChanged) || (dependantChanged)))
/*  306 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  327 */     if (items == null) {
/*  328 */       return false;
/*      */     }
/*  330 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  331 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  333 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  338 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  339 */       while (iter2.hasNext())
/*      */       {
/*  341 */         this.mDetails = ((BudgetStateHistoryEVO)iter2.next());
/*      */ 
/*  344 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  346 */         somethingChanged = true;
/*      */ 
/*  349 */         if (deleteStmt == null) {
/*  350 */           deleteStmt = getConnection().prepareStatement("delete from BUDGET_STATE_HISTORY where    BUDGET_STATE_HISTORY_ID = ? AND BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*      */         }
/*      */ 
/*  353 */         int col = 1;
/*  354 */         deleteStmt.setInt(col++, this.mDetails.getBudgetStateHistoryId());
/*  355 */         deleteStmt.setInt(col++, this.mDetails.getBudgetCycleId());
/*  356 */         deleteStmt.setInt(col++, this.mDetails.getStructureElementId());
/*      */ 
/*  358 */         if (this._log.isDebugEnabled()) {
/*  359 */           this._log.debug("update", "BudgetStateHistory deleting BudgetStateHistoryId=" + this.mDetails.getBudgetStateHistoryId() + ",BudgetCycleId=" + this.mDetails.getBudgetCycleId() + ",StructureElementId=" + this.mDetails.getStructureElementId());
/*      */         }
/*      */ 
/*  366 */         deleteStmt.addBatch();
/*      */ 
/*  369 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  374 */       if (deleteStmt != null)
/*      */       {
/*  376 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  378 */         deleteStmt.executeBatch();
/*      */ 
/*  380 */         if (timer2 != null) {
/*  381 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  385 */       Iterator iter1 = items.values().iterator();
/*  386 */       while (iter1.hasNext())
/*      */       {
/*  388 */         this.mDetails = ((BudgetStateHistoryEVO)iter1.next());
/*      */ 
/*  390 */         if (this.mDetails.insertPending())
/*      */         {
/*  392 */           somethingChanged = true;
/*  393 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  396 */         if (!this.mDetails.isModified())
/*      */           continue;
/*  398 */         somethingChanged = true;
/*  399 */         doStore();
/*      */       }
/*      */ 
/*  410 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  414 */       throw handleSQLException("delete from BUDGET_STATE_HISTORY where    BUDGET_STATE_HISTORY_ID = ? AND BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  418 */       if (deleteStmt != null)
/*      */       {
/*  420 */         closeStatement(deleteStmt);
/*  421 */         closeConnection();
/*      */       }
/*      */ 
/*  424 */       this.mDetails = null;
/*      */ 
/*  426 */       if ((somethingChanged) && 
/*  427 */         (timer != null))
/*  428 */         timer.logDebug("update", "collection"); 
/*  428 */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*      */   {
/*  458 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  460 */     PreparedStatement stmt = null;
/*  461 */     ResultSet resultSet = null;
/*      */ 
/*  463 */     int itemCount = 0;
/*      */ 
/*  465 */     BudgetStateEVO owningEVO = null;
/*  466 */     Iterator ownersIter = owners.iterator();
/*  467 */     while (ownersIter.hasNext())
/*      */     {
/*  469 */       owningEVO = (BudgetStateEVO)ownersIter.next();
/*  470 */       owningEVO.setBudgetCycleHistoryAllItemsLoaded(true);
/*      */     }
/*  472 */     ownersIter = owners.iterator();
/*  473 */     owningEVO = (BudgetStateEVO)ownersIter.next();
/*  474 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  478 */       stmt = getConnection().prepareStatement("select BUDGET_STATE_HISTORY.BUDGET_STATE_HISTORY_ID,BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID,BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID,BUDGET_STATE_HISTORY.PREVIOUS_STATE,BUDGET_STATE_HISTORY.NEW_STATE,BUDGET_STATE_HISTORY.CHANGED_TIME,BUDGET_STATE_HISTORY.USER_ID from BUDGET_STATE_HISTORY,BUDGET_STATE,BUDGET_CYCLE where 1=1 and BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID = BUDGET_STATE.BUDGET_CYCLE_ID and BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID = BUDGET_STATE.STRUCTURE_ELEMENT_ID and BUDGET_STATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.MODEL_ID = ? order by  BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID ,BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID ,BUDGET_STATE_HISTORY.BUDGET_STATE_HISTORY_ID ,BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID ,BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID");
/*      */ 
/*  480 */       int col = 1;
/*  481 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  483 */       resultSet = stmt.executeQuery();
/*      */ 
/*  486 */       while (resultSet.next())
/*      */       {
/*  488 */         itemCount++;
/*  489 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  494 */         while ((this.mDetails.getBudgetCycleId() != owningEVO.getBudgetCycleId()) || (this.mDetails.getStructureElementId() != owningEVO.getStructureElementId()))
/*      */         {
/*  499 */           if (!ownersIter.hasNext())
/*      */           {
/*  501 */             this._log.debug("bulkGetAll", "can't find owning [BudgetCycleId=" + this.mDetails.getBudgetCycleId() + "StructureElementId=" + this.mDetails.getStructureElementId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  506 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  507 */             ownersIter = owners.iterator();
/*  508 */             while (ownersIter.hasNext())
/*      */             {
/*  510 */               owningEVO = (BudgetStateEVO)ownersIter.next();
/*  511 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  513 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  515 */           owningEVO = (BudgetStateEVO)ownersIter.next();
/*      */         }
/*  517 */         if (owningEVO.getBudgetCycleHistory() == null)
/*      */         {
/*  519 */           theseItems = new ArrayList();
/*  520 */           owningEVO.setBudgetCycleHistory(theseItems);
/*  521 */           owningEVO.setBudgetCycleHistoryAllItemsLoaded(true);
/*      */         }
/*  523 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  526 */       if (timer != null) {
/*  527 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  532 */       throw handleSQLException("select BUDGET_STATE_HISTORY.BUDGET_STATE_HISTORY_ID,BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID,BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID,BUDGET_STATE_HISTORY.PREVIOUS_STATE,BUDGET_STATE_HISTORY.NEW_STATE,BUDGET_STATE_HISTORY.CHANGED_TIME,BUDGET_STATE_HISTORY.USER_ID from BUDGET_STATE_HISTORY,BUDGET_STATE,BUDGET_CYCLE where 1=1 and BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID = BUDGET_STATE.BUDGET_CYCLE_ID and BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID = BUDGET_STATE.STRUCTURE_ELEMENT_ID and BUDGET_STATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.MODEL_ID = ? order by  BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID ,BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID ,BUDGET_STATE_HISTORY.BUDGET_STATE_HISTORY_ID ,BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID ,BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  536 */       closeResultSet(resultSet);
/*  537 */       closeStatement(stmt);
/*  538 */       closeConnection();
/*      */ 
/*  540 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectBudgetCycleId, int selectStructureElementId, String dependants, Collection currentList)
/*      */   {
/*  568 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  569 */     PreparedStatement stmt = null;
/*  570 */     ResultSet resultSet = null;
/*      */ 
/*  572 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  576 */       stmt = getConnection().prepareStatement("select BUDGET_STATE_HISTORY.BUDGET_STATE_HISTORY_ID,BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID,BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID,BUDGET_STATE_HISTORY.PREVIOUS_STATE,BUDGET_STATE_HISTORY.NEW_STATE,BUDGET_STATE_HISTORY.CHANGED_TIME,BUDGET_STATE_HISTORY.USER_ID from BUDGET_STATE_HISTORY where    BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*      */ 
/*  578 */       int col = 1;
/*  579 */       stmt.setInt(col++, selectBudgetCycleId);
/*  580 */       stmt.setInt(col++, selectStructureElementId);
/*      */ 
/*  582 */       resultSet = stmt.executeQuery();
/*      */ 
/*  584 */       while (resultSet.next())
/*      */       {
/*  586 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  589 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  592 */       if (currentList != null)
/*      */       {
/*  595 */         ListIterator iter = items.listIterator();
/*  596 */         BudgetStateHistoryEVO currentEVO = null;
/*  597 */         BudgetStateHistoryEVO newEVO = null;
/*  598 */         while (iter.hasNext())
/*      */         {
/*  600 */           newEVO = (BudgetStateHistoryEVO)iter.next();
/*  601 */           Iterator iter2 = currentList.iterator();
/*  602 */           while (iter2.hasNext())
/*      */           {
/*  604 */             currentEVO = (BudgetStateHistoryEVO)iter2.next();
/*  605 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  607 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  613 */         Iterator iter2 = currentList.iterator();
/*  614 */         while (iter2.hasNext())
/*      */         {
/*  616 */           currentEVO = (BudgetStateHistoryEVO)iter2.next();
/*  617 */           if (currentEVO.insertPending()) {
/*  618 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  622 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  626 */       throw handleSQLException("select BUDGET_STATE_HISTORY.BUDGET_STATE_HISTORY_ID,BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID,BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID,BUDGET_STATE_HISTORY.PREVIOUS_STATE,BUDGET_STATE_HISTORY.NEW_STATE,BUDGET_STATE_HISTORY.CHANGED_TIME,BUDGET_STATE_HISTORY.USER_ID from BUDGET_STATE_HISTORY where    BUDGET_CYCLE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  630 */       closeResultSet(resultSet);
/*  631 */       closeStatement(stmt);
/*  632 */       closeConnection();
/*      */ 
/*  634 */       if (timer != null) {
/*  635 */         timer.logDebug("getAll", " BudgetCycleId=" + selectBudgetCycleId + ",StructureElementId=" + selectStructureElementId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  641 */     return items;
/*      */   }
/*      */ 
/*      */   public BudgetStateHistoryEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  655 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  658 */     if (this.mDetails == null) {
/*  659 */       doLoad(((BudgetStateHistoryCK)paramCK).getBudgetStateHistoryPK());
/*      */     }
/*  661 */     else if (!this.mDetails.getPK().equals(((BudgetStateHistoryCK)paramCK).getBudgetStateHistoryPK())) {
/*  662 */       doLoad(((BudgetStateHistoryCK)paramCK).getBudgetStateHistoryPK());
/*      */     }
/*      */ 
/*  665 */     BudgetStateHistoryEVO details = new BudgetStateHistoryEVO();
/*  666 */     details = this.mDetails.deepClone();
/*      */ 
/*  668 */     if (timer != null) {
/*  669 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  671 */     return details;
/*      */   }
/*      */ 
/*      */   public BudgetStateHistoryEVO getDetails(ModelCK paramCK, BudgetStateHistoryEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  677 */     BudgetStateHistoryEVO savedEVO = this.mDetails;
/*  678 */     this.mDetails = paramEVO;
/*  679 */     BudgetStateHistoryEVO newEVO = getDetails(paramCK, dependants);
/*  680 */     this.mDetails = savedEVO;
/*  681 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public BudgetStateHistoryEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  687 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  691 */     BudgetStateHistoryEVO details = this.mDetails.deepClone();
/*      */ 
/*  693 */     if (timer != null) {
/*  694 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  696 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  701 */     return "BudgetStateHistory";
/*      */   }
/*      */ 
/*      */   public BudgetStateHistoryRefImpl getRef(BudgetStateHistoryPK paramBudgetStateHistoryPK)
/*      */   {
/*  706 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  707 */     PreparedStatement stmt = null;
/*  708 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  711 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,BUDGET_CYCLE.BUDGET_CYCLE_ID,BUDGET_STATE.BUDGET_CYCLE_ID,BUDGET_STATE.STRUCTURE_ELEMENT_ID from BUDGET_STATE_HISTORY,MODEL,BUDGET_CYCLE,BUDGET_STATE where 1=1 and BUDGET_STATE_HISTORY.BUDGET_STATE_HISTORY_ID = ? and BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID = ? and BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID = ? and BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID = BUDGET_STATE.BUDGET_CYCLE_ID and BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID = BUDGET_STATE.STRUCTURE_ELEMENT_ID and BUDGET_STATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_STATE.STRUCTURE_ELEMENT_ID = BUDGET_CYCLE.STRUCTURE_ELEMENT_ID and BUDGET_CYCLE.BUDGET_CYCLE_ID = MODEL.BUDGET_CYCLE_ID");
/*  712 */       int col = 1;
/*  713 */       stmt.setInt(col++, paramBudgetStateHistoryPK.getBudgetStateHistoryId());
/*  714 */       stmt.setInt(col++, paramBudgetStateHistoryPK.getBudgetCycleId());
/*  715 */       stmt.setInt(col++, paramBudgetStateHistoryPK.getStructureElementId());
/*      */ 
/*  717 */       resultSet = stmt.executeQuery();
/*      */ 
/*  719 */       if (!resultSet.next()) {
/*  720 */         throw new RuntimeException(getEntityName() + " getRef " + paramBudgetStateHistoryPK + " not found");
/*      */       }
/*  722 */       col = 2;
/*  723 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  727 */       BudgetCyclePK newBudgetCyclePK = new BudgetCyclePK(resultSet.getInt(col++));
/*      */ 
/*  731 */       BudgetStatePK newBudgetStatePK = new BudgetStatePK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  736 */       String textBudgetStateHistory = "";
/*  737 */       BudgetStateHistoryCK ckBudgetStateHistory = new BudgetStateHistoryCK(newModelPK, newBudgetCyclePK, newBudgetStatePK, paramBudgetStateHistoryPK);
/*      */ 
/*  744 */       BudgetStateHistoryRefImpl localBudgetStateHistoryRefImpl = new BudgetStateHistoryRefImpl(ckBudgetStateHistory, textBudgetStateHistory);
/*      */       return localBudgetStateHistoryRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  749 */       throw handleSQLException(paramBudgetStateHistoryPK, "select 0,MODEL.MODEL_ID,BUDGET_CYCLE.BUDGET_CYCLE_ID,BUDGET_STATE.BUDGET_CYCLE_ID,BUDGET_STATE.STRUCTURE_ELEMENT_ID from BUDGET_STATE_HISTORY,MODEL,BUDGET_CYCLE,BUDGET_STATE where 1=1 and BUDGET_STATE_HISTORY.BUDGET_STATE_HISTORY_ID = ? and BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID = ? and BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID = ? and BUDGET_STATE_HISTORY.BUDGET_CYCLE_ID = BUDGET_STATE.BUDGET_CYCLE_ID and BUDGET_STATE_HISTORY.STRUCTURE_ELEMENT_ID = BUDGET_STATE.STRUCTURE_ELEMENT_ID and BUDGET_STATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_STATE.STRUCTURE_ELEMENT_ID = BUDGET_CYCLE.STRUCTURE_ELEMENT_ID and BUDGET_CYCLE.BUDGET_CYCLE_ID = MODEL.BUDGET_CYCLE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  753 */       closeResultSet(resultSet);
/*  754 */       closeStatement(stmt);
/*  755 */       closeConnection();
/*      */ 
/*  757 */       if (timer != null)
/*  758 */         timer.logDebug("getRef", paramBudgetStateHistoryPK); 
/*  758 */     }
/*      */   }
/*      */ 
/*      */   public EntityList getHistoryDetails(int budgetCycleId, int structure_id, int structure_elementid)
/*      */   {
/*  830 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  831 */     PreparedStatement stmt = null;
/*  832 */     ResultSet resultSet = null;
/*      */ 
/*  834 */     BudgetStateHistoryELO results = new BudgetStateHistoryELO();
/*      */ 
/*  841 */     int state = 0;
/*      */ 
/*  844 */     String contact = "";
/*      */     try
/*      */     {
/*  850 */       int col = 1;
/*      */ 
/*  852 */       stmt = getConnection().prepareStatement("select st_lst.budget_cycle_id   ,st_lst.structure_id  ,st_lst.structure_element_id  ,st_lst.vis_id  ,st_lst.description  ,st_lst.depth  ,st_lst.leaf  ,st_lst.position  ,bshm.new_state  ,bshm.changed_time   ,(select distinct 'Y' from budget_user bu where st_lst.structure_element_id = bu.structure_element_id and bu.model_id = st_lst.model_id ) contact  ,(select ld.planned_end_date from level_date ld where ld.budget_cycle_id = st_lst.budget_cycle_id and ld.depth = st_lst.depth) end_date from  (  select    se.structure_id   ,se.structure_element_id   ,se.vis_id   ,se.description   ,se.depth   ,se.leaf   ,se.position   ,bc.budget_cycle_id   ,m.model_id  from    model m   ,budget_cycle bc   ,structure_element se  where m.model_id = bc.model_id and     m.budget_hierarchy_id = se.structure_id and     bc.budget_cycle_id = ?  ) st_lst  ,(   select        budget_cycle_id,        structure_element_id,        new_state,        changed_time         from    (    select         budget_cycle_id,        structure_element_id,        new_state,        changed_time,           rank() over ( partition by budget_cycle_id, structure_element_id order by changed_time desc ) rk     from       budget_state_history    where        budget_cycle_id = ?     ) where rk = 1  ) bshm,  (    select lse.position, lse.end_position from structure_element lse where lse.structure_id = ? and lse.structure_element_id = ?  )  root_node  where     st_lst.budget_cycle_id = bshm.budget_cycle_id (+)    and st_lst.structure_element_id = bshm.structure_element_id (+)    AND st_lst.position >= root_node.position    AND st_lst.position <= root_node.end_position order by st_lst.depth, st_lst.structure_element_id  ");
/*      */ 
/*  854 */       stmt.setInt(col++, budgetCycleId);
/*  855 */       stmt.setInt(col++, budgetCycleId);
/*  856 */       stmt.setInt(col++, structure_id);
/*  857 */       stmt.setInt(col, structure_elementid);
/*      */ 
/*  859 */       resultSet = stmt.executeQuery();
/*  860 */       while (resultSet.next())
/*      */       {
/*  862 */         col = 2;
/*  863 */         int structureId = resultSet.getInt(col++);
/*  864 */         int structureElementId = resultSet.getInt(col++);
/*  865 */         String visId = resultSet.getString(col++);
/*  866 */         String description = resultSet.getString(col++);
/*  867 */         int depth = resultSet.getInt(col++);
/*  868 */         String leaf = resultSet.getString(col++);
/*  869 */         col++;
/*  870 */         state = resultSet.getInt(col++);
/*  871 */         Timestamp lastChanged = resultSet.getTimestamp(col++);
/*  872 */         contact = resultSet.getString(col++);
/*  873 */         Timestamp plannedEndDate = resultSet.getTimestamp(col);
/*      */         boolean leafBoolean;
/*  875 */         if (leaf.equals("Y"))
/*  876 */           leafBoolean = true;
/*      */         else
/*  878 */           leafBoolean = false;
/*      */         boolean contactBoolean;
/*  880 */         if ((contact != null) && (contact.equals("Y")))
/*  881 */           contactBoolean = true;
/*      */         else {
/*  883 */           contactBoolean = false;
/*      */         }
/*  885 */         results.add(structureId, structureElementId, visId, description, depth, leafBoolean, state, lastChanged, contactBoolean, plannedEndDate);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  890 */       System.err.println(sqle);
/*  891 */       sqle.printStackTrace();
/*  892 */       throw new RuntimeException(getEntityName() + " BudgetDetailsForUser", sqle);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  896 */       e.printStackTrace();
/*  897 */       throw new RuntimeException(getEntityName() + " BudgetDetailsForUser", e);
/*      */     }
/*      */     finally
/*      */     {
/*  901 */       closeResultSet(resultSet);
/*  902 */       closeStatement(stmt);
/*  903 */       closeConnection();
/*      */     }
/*      */ 
/*  906 */     if (timer != null) {
/*  907 */       timer.logDebug("getHistoryDetails", "budgetCycleId=" + budgetCycleId);
/*      */     }
/*  909 */     return results;
/*      */   }
/*      */ 
/*      */   public EntityList getContactLocations(int budgetCycleId, int daysBefore)
/*      */   {
/*  994 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  995 */     PreparedStatement stmt = null;
/*  996 */     ResultSet resultSet = null;
/*      */ 
/*  998 */     BudgetStateHistoryELO results = new BudgetStateHistoryELO();
/*  999 */     int structureId = 0;
/* 1000 */     int structureElementId = 0;
/* 1001 */     String visId = "";
/* 1002 */     String description = "";
/* 1003 */     int depth = 0;
/* 1004 */     String leaf = "";
/* 1005 */     int state = 0;
/* 1006 */     Timestamp lastChanged = null;
/* 1007 */     boolean leafBoolean = false;
/* 1008 */     String contact = "";
/* 1009 */     Timestamp plannedEndDate = null;
/* 1010 */     boolean contactBoolean = false;
/*      */     try
/*      */     {
/* 1017 */       int col = 1;
/*      */ 
/* 1019 */       stmt = getConnection().prepareStatement("select * from (  select st_lst.budget_cycle_id   ,st_lst.structure_id  ,st_lst.structure_element_id  ,st_lst.vis_id  ,st_lst.description  ,st_lst.depth  ,st_lst.leaf  ,st_lst.position   ,st_lst.disabled   ,st_lst.not_plannable   ,bshm.new_state  ,bshm.changed_time   ,(select distinct 'Y' from budget_user bu where st_lst.structure_element_id = bu.structure_element_id and bu.model_id = st_lst.model_id ) contact  ,(select ld.planned_end_date from level_date ld where ld.budget_cycle_id = st_lst.budget_cycle_id and ld.depth = st_lst.depth) end_date from  (  select    se.structure_id   ,se.structure_element_id   ,se.vis_id   ,se.description   ,se.depth   ,se.leaf   ,se.position   ,se.disabled   ,se.not_plannable   ,bc.budget_cycle_id   ,m.model_id  from    model m   ,budget_cycle bc   ,structure_element se  where m.model_id = bc.model_id and     m.budget_hierarchy_id = se.structure_id and     bc.budget_cycle_id = ?  ) st_lst  ,(   select        budget_cycle_id,        structure_element_id,        new_state,        changed_time         from    (    select         budget_cycle_id,        structure_element_id,        new_state,        changed_time,           rank() over ( partition by budget_cycle_id, structure_element_id order by changed_time desc ) rk     from       budget_state_history    where        budget_cycle_id = ?     ) where rk = 1  ) bshm,  (  select   lse.position,   lse.end_position  from   structure_element lse  where   lse.structure_id = (SELECT budget_hierarchy_id FROM MODEL m, budget_cycle bc WHERE m.model_id = bc.model_id and bc.budget_cycle_id = ?) AND lse.parent_id = 0  )  root_node where     st_lst.budget_cycle_id = bshm.budget_cycle_id (+)    and st_lst.structure_element_id = bshm.structure_element_id (+)    AND st_lst.position >= root_node.position    AND st_lst.position <= root_node.end_position    AND st_lst.disabled <> 'Y'    AND st_lst.not_plannable <> 'Y' order by st_lst.depth, st_lst.structure_element_id   ) WHERE contact = 'Y'    AND (new_state IS NULL OR new_state < 4)    AND (end_date < sysdate + ?) ");
/*      */ 
/* 1021 */       stmt.setInt(col++, budgetCycleId);
/* 1022 */       stmt.setInt(col++, budgetCycleId);
/* 1023 */       stmt.setInt(col++, budgetCycleId);
/* 1024 */       stmt.setInt(col++, daysBefore);
/*      */ 
/* 1026 */       resultSet = stmt.executeQuery();
/* 1027 */       while (resultSet.next())
/*      */       {
/* 1029 */         col = 2;
/* 1030 */         structureId = resultSet.getInt(col++);
/* 1031 */         structureElementId = resultSet.getInt(col++);
/* 1032 */         visId = resultSet.getString(col++);
/* 1033 */         description = resultSet.getString(col++);
/* 1034 */         depth = resultSet.getInt(col++);
/* 1035 */         leaf = resultSet.getString(col++);
/* 1036 */         int position = resultSet.getInt(col++);
/* 1037 */         String disabled = resultSet.getString(col++);
/* 1038 */         String notPlannable = resultSet.getString(col++);
/* 1039 */         state = resultSet.getInt(col++);
/* 1040 */         lastChanged = resultSet.getTimestamp(col++);
/* 1041 */         contact = resultSet.getString(col++);
/* 1042 */         plannedEndDate = resultSet.getTimestamp(col);
/*      */ 
/* 1044 */         if (leaf.equals("Y"))
/* 1045 */           leafBoolean = true;
/*      */         else {
/* 1047 */           leafBoolean = false;
/*      */         }
/* 1049 */         if ((contact != null) && (contact.equals("Y")))
/* 1050 */           contactBoolean = true;
/*      */         else {
/* 1052 */           contactBoolean = false;
/*      */         }
/* 1054 */         results.add(structureId, structureElementId, visId, description, depth, leafBoolean, state, lastChanged, contactBoolean, plannedEndDate);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1059 */       System.err.println(sqle);
/* 1060 */       sqle.printStackTrace();
/* 1061 */       throw new RuntimeException(getEntityName() + " ContactLocations", sqle);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1065 */       e.printStackTrace();
/* 1066 */       throw new RuntimeException(getEntityName() + " ContactLocations", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1070 */       closeResultSet(resultSet);
/* 1071 */       closeStatement(stmt);
/* 1072 */       closeConnection();
/*      */     }
/*      */ 
/* 1075 */     if (timer != null) {
/* 1076 */       timer.logDebug("getContactLocations", "budgetCycleId=" + budgetCycleId);
/*      */     }
/* 1078 */     return results;
/*      */   }
/*      */ 
/*      */   public void tidyOrphanBudgetStates(int modelId, int structureId)
/*      */   {
/* 1100 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/* 1103 */       ps = getConnection().prepareStatement("delete\nfrom budget_state_history\nwhere budget_state_history_id in\n\t( select budget_state_history_id\n\t  from budget_state_history shist, budget_cycle cycle\n\t  where cycle.model_id = ? and\n\t\t\tcycle.budget_cycle_id = shist.budget_cycle_id and\n\t\t\tshist.structure_element_id not in ( select structure_element_id \n\t\t\t\t\t\t\t\t\t\t\t\tfrom structure_element \n\t\t\t\t\t\t\t\t\t\t\t\twhere structure_id = ? ) )");
/* 1104 */       ps.setInt(1, modelId);
/* 1105 */       ps.setInt(2, structureId);
/* 1106 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1110 */       throw handleSQLException("Failed to tidy orphan budget state history", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1114 */       closeStatement(ps);
/* 1115 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public EntityList getModernWelcomeInfo(int userId, int daysBefore)
/*      */   {
/* 1207 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1209 */     PreparedStatement stmt = null;
/* 1210 */     ResultSet resultSet = null;
/*      */ 
/* 1212 */     ModernWelcomeELO results = new ModernWelcomeELO();
/* 1213 */     int modelId = 0;
/* 1214 */     String modelVisId = "";
/* 1215 */     String modelDescription = "";
/* 1216 */     int budgetCycleId = 0;
/* 1217 */     String budgetCycleVisId = "";
/* 1218 */     String budgetCycleDescription = "";
/*      */ 
/* 1220 */     int structureId = 0;
/* 1221 */     int structureElementId = 0;
/* 1222 */     String visId = "";
/* 1223 */     String description = "";
/* 1224 */     int depth = 0;
/* 1225 */     int state = 0;
/* 1226 */     Timestamp lastChanged = null;
/* 1227 */     boolean leafBoolean = false;
/* 1228 */     Timestamp plannedEndDate = null;
/*      */ 
/* 1230 */     String leaf = "";
/*      */     try
/*      */     {
/* 1237 */       int col = 1;
/*      */ 
/* 1239 */       stmt = getConnection().prepareStatement(MODERN_WELCOME_PAGE_INFO);
/*      */ 
/* 1241 */       stmt.setInt(col++, userId);
/* 1242 */       stmt.setInt(col++, daysBefore);
/*      */ 
/* 1244 */       resultSet = stmt.executeQuery();
/* 1245 */       while (resultSet.next())
/*      */       {
/* 1247 */         col = 1;
/* 1248 */         modelId = resultSet.getInt(col++);
/* 1249 */         modelVisId = resultSet.getString(col++);
/* 1250 */         modelDescription = resultSet.getString(col++);
/* 1251 */         budgetCycleId = resultSet.getInt(col++);
/* 1252 */         budgetCycleVisId = resultSet.getString(col++);
/* 1253 */         budgetCycleDescription = resultSet.getString(col++);
/* 1254 */         structureId = resultSet.getInt(col++);
/* 1255 */         structureElementId = resultSet.getInt(col++);
/* 1256 */         visId = resultSet.getString(col++);
/* 1257 */         description = resultSet.getString(col++);
/* 1258 */         depth = resultSet.getInt(col++);
/* 1259 */         leaf = resultSet.getString(col++);
/* 1260 */         int position = resultSet.getInt(col++);
/* 1261 */         String disabled = resultSet.getString(col++);
/* 1262 */         String notPlannable = resultSet.getString(col++);
/* 1263 */         state = resultSet.getInt(col++);
/* 1264 */         lastChanged = resultSet.getTimestamp(col++);
/* 1265 */         plannedEndDate = resultSet.getTimestamp(col);
/*      */ 
/* 1268 */         results.add(modelId, modelVisId, modelDescription, budgetCycleId, budgetCycleVisId, budgetCycleDescription, structureId, structureElementId, visId, description, depth, leafBoolean, state, lastChanged, plannedEndDate);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1273 */       System.err.println(sqle);
/* 1274 */       sqle.printStackTrace();
/* 1275 */       throw new RuntimeException(getEntityName() + " getModernWelcomeInfo", sqle);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1279 */       e.printStackTrace();
/* 1280 */       throw new RuntimeException(getEntityName() + " getModernWelcomeInfo", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1284 */       closeResultSet(resultSet);
/* 1285 */       closeStatement(stmt);
/* 1286 */       closeConnection();
/*      */     }
/*      */ 
/* 1289 */     if (timer != null) {
/* 1290 */       timer.logDebug("getModernWelcomeInfo", "userId=" + userId);
/*      */     }
/* 1292 */     return results;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.BudgetStateHistoryDAO
 * JD-Core Version:    0.6.0
 */