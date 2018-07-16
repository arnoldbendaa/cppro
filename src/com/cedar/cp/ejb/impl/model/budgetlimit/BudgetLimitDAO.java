/*      */ package com.cedar.cp.ejb.impl.model.budgetlimit;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.FinanceCubeCK;
/*      */ import com.cedar.cp.dto.model.FinanceCubePK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeRefImpl;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.budgetlimit.AllBudgetLimitsELO;
/*      */ import com.cedar.cp.dto.model.budgetlimit.BudgetLimitCK;
/*      */ import com.cedar.cp.dto.model.budgetlimit.BudgetLimitPK;
/*      */ import com.cedar.cp.dto.model.budgetlimit.BudgetLimitRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*      */ import com.cedar.cp.ejb.impl.dimension.DimensionDAO;
/*      */ import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
/*      */ import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.SqlBuilder;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class BudgetLimitDAO extends AbstractDAO
/*      */ {
/*   38 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select BUDGET_LIMIT.BUDGET_LIMIT_ID,BUDGET_LIMIT.FINANCE_CUBE_ID,BUDGET_LIMIT.BUDGET_LOCATION_ELEMENT_ID,BUDGET_LIMIT.DIM0,BUDGET_LIMIT.DIM1,BUDGET_LIMIT.DIM2,BUDGET_LIMIT.DIM3,BUDGET_LIMIT.DIM4,BUDGET_LIMIT.DIM5,BUDGET_LIMIT.DIM6,BUDGET_LIMIT.DIM7,BUDGET_LIMIT.DIM8,BUDGET_LIMIT.DIM9,BUDGET_LIMIT.DATA_TYPE,BUDGET_LIMIT.MIN_VALUE,BUDGET_LIMIT.MAX_VALUE,BUDGET_LIMIT.VERSION_NUM,BUDGET_LIMIT.UPDATED_BY_USER_ID,BUDGET_LIMIT.UPDATED_TIME,BUDGET_LIMIT.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from BUDGET_LIMIT where    BUDGET_LIMIT_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into BUDGET_LIMIT ( BUDGET_LIMIT_ID,FINANCE_CUBE_ID,BUDGET_LOCATION_ELEMENT_ID,DIM0,DIM1,DIM2,DIM3,DIM4,DIM5,DIM6,DIM7,DIM8,DIM9,DATA_TYPE,MIN_VALUE,MAX_VALUE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update BUDGET_LIMIT set FINANCE_CUBE_ID = ?,BUDGET_LOCATION_ELEMENT_ID = ?,DIM0 = ?,DIM1 = ?,DIM2 = ?,DIM3 = ?,DIM4 = ?,DIM5 = ?,DIM6 = ?,DIM7 = ?,DIM8 = ?,DIM9 = ?,DATA_TYPE = ?,MIN_VALUE = ?,MAX_VALUE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    BUDGET_LIMIT_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from BUDGET_LIMIT where BUDGET_LIMIT_ID = ?";
/*  454 */   protected static String SQL_ALL_BUDGET_LIMITS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,BUDGET_LIMIT.BUDGET_LIMIT_ID      ,BUDGET_LIMIT.DATA_TYPE      ,BUDGET_LIMIT.MIN_VALUE      ,BUDGET_LIMIT.MAX_VALUE from BUDGET_LIMIT    ,MODEL    ,FINANCE_CUBE where 1=1   and BUDGET_LIMIT.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  order by BUDGET_LIMIT_ID";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from BUDGET_LIMIT where    BUDGET_LIMIT_ID = ? ";
/*      */   public static final String SQL_BULK_GET_ALL = " from BUDGET_LIMIT,FINANCE_CUBE where 1=1 and BUDGET_LIMIT.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  BUDGET_LIMIT.FINANCE_CUBE_ID ,BUDGET_LIMIT.BUDGET_LIMIT_ID";
/*      */   protected static final String SQL_GET_ALL = " from BUDGET_LIMIT where    FINANCE_CUBE_ID = ? ";
/*      */   protected BudgetLimitEVO mDetails;
/*      */ 
/*      */   public BudgetLimitDAO(Connection connection)
/*      */   {
/*   45 */     super(connection);
/*      */   }
/*      */ 
/*      */   public BudgetLimitDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public BudgetLimitDAO(DataSource ds)
/*      */   {
/*   61 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected BudgetLimitPK getPK()
/*      */   {
/*   69 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(BudgetLimitEVO details)
/*      */   {
/*   78 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private BudgetLimitEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  109 */     int col = 1;
/*  110 */     BudgetLimitEVO evo = new BudgetLimitEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), getWrappedLongFromJdbc(resultSet_, col++), getWrappedLongFromJdbc(resultSet_, col++), resultSet_.getInt(col++));
/*      */ 
/*  130 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  131 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  132 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  133 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(BudgetLimitEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  138 */     int col = startCol_;
/*  139 */     stmt_.setInt(col++, evo_.getBudgetLimitId());
/*  140 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(BudgetLimitEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  145 */     int col = startCol_;
/*  146 */     stmt_.setInt(col++, evo_.getFinanceCubeId());
/*  147 */     stmt_.setInt(col++, evo_.getBudgetLocationElementId());
/*  148 */     stmt_.setInt(col++, evo_.getDim0());
/*  149 */     stmt_.setInt(col++, evo_.getDim1());
/*  150 */     stmt_.setInt(col++, evo_.getDim2());
/*  151 */     stmt_.setInt(col++, evo_.getDim3());
/*  152 */     stmt_.setInt(col++, evo_.getDim4());
/*  153 */     stmt_.setInt(col++, evo_.getDim5());
/*  154 */     stmt_.setInt(col++, evo_.getDim6());
/*  155 */     stmt_.setInt(col++, evo_.getDim7());
/*  156 */     stmt_.setInt(col++, evo_.getDim8());
/*  157 */     stmt_.setInt(col++, evo_.getDim9());
/*  158 */     stmt_.setString(col++, evo_.getDataType());
/*  159 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getMinValue());
/*  160 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getMaxValue());
/*  161 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  162 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  163 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  164 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  165 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(BudgetLimitPK pk)
/*      */     throws ValidationException
/*      */   {
/*  181 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  183 */     PreparedStatement stmt = null;
/*  184 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  188 */       stmt = getConnection().prepareStatement("select BUDGET_LIMIT.BUDGET_LIMIT_ID,BUDGET_LIMIT.FINANCE_CUBE_ID,BUDGET_LIMIT.BUDGET_LOCATION_ELEMENT_ID,BUDGET_LIMIT.DIM0,BUDGET_LIMIT.DIM1,BUDGET_LIMIT.DIM2,BUDGET_LIMIT.DIM3,BUDGET_LIMIT.DIM4,BUDGET_LIMIT.DIM5,BUDGET_LIMIT.DIM6,BUDGET_LIMIT.DIM7,BUDGET_LIMIT.DIM8,BUDGET_LIMIT.DIM9,BUDGET_LIMIT.DATA_TYPE,BUDGET_LIMIT.MIN_VALUE,BUDGET_LIMIT.MAX_VALUE,BUDGET_LIMIT.VERSION_NUM,BUDGET_LIMIT.UPDATED_BY_USER_ID,BUDGET_LIMIT.UPDATED_TIME,BUDGET_LIMIT.CREATED_TIME from BUDGET_LIMIT where    BUDGET_LIMIT_ID = ? ");
/*      */ 
/*  191 */       int col = 1;
/*  192 */       stmt.setInt(col++, pk.getBudgetLimitId());
/*      */ 
/*  194 */       resultSet = stmt.executeQuery();
/*      */ 
/*  196 */       if (!resultSet.next()) {
/*  197 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  200 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  201 */       if (this.mDetails.isModified())
/*  202 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  206 */       throw handleSQLException(pk, "select BUDGET_LIMIT.BUDGET_LIMIT_ID,BUDGET_LIMIT.FINANCE_CUBE_ID,BUDGET_LIMIT.BUDGET_LOCATION_ELEMENT_ID,BUDGET_LIMIT.DIM0,BUDGET_LIMIT.DIM1,BUDGET_LIMIT.DIM2,BUDGET_LIMIT.DIM3,BUDGET_LIMIT.DIM4,BUDGET_LIMIT.DIM5,BUDGET_LIMIT.DIM6,BUDGET_LIMIT.DIM7,BUDGET_LIMIT.DIM8,BUDGET_LIMIT.DIM9,BUDGET_LIMIT.DATA_TYPE,BUDGET_LIMIT.MIN_VALUE,BUDGET_LIMIT.MAX_VALUE,BUDGET_LIMIT.VERSION_NUM,BUDGET_LIMIT.UPDATED_BY_USER_ID,BUDGET_LIMIT.UPDATED_TIME,BUDGET_LIMIT.CREATED_TIME from BUDGET_LIMIT where    BUDGET_LIMIT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  210 */       closeResultSet(resultSet);
/*  211 */       closeStatement(stmt);
/*  212 */       closeConnection();
/*      */ 
/*  214 */       if (timer != null)
/*  215 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  272 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  273 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  278 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  279 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  280 */       stmt = getConnection().prepareStatement("insert into BUDGET_LIMIT ( BUDGET_LIMIT_ID,FINANCE_CUBE_ID,BUDGET_LOCATION_ELEMENT_ID,DIM0,DIM1,DIM2,DIM3,DIM4,DIM5,DIM6,DIM7,DIM8,DIM9,DATA_TYPE,MIN_VALUE,MAX_VALUE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  283 */       int col = 1;
/*  284 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  285 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  288 */       int resultCount = stmt.executeUpdate();
/*  289 */       if (resultCount != 1)
/*      */       {
/*  291 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  294 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  298 */       throw handleSQLException(this.mDetails.getPK(), "insert into BUDGET_LIMIT ( BUDGET_LIMIT_ID,FINANCE_CUBE_ID,BUDGET_LOCATION_ELEMENT_ID,DIM0,DIM1,DIM2,DIM3,DIM4,DIM5,DIM6,DIM7,DIM8,DIM9,DATA_TYPE,MIN_VALUE,MAX_VALUE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  302 */       closeStatement(stmt);
/*  303 */       closeConnection();
/*      */ 
/*  305 */       if (timer != null)
/*  306 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  345 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  349 */     PreparedStatement stmt = null;
/*      */ 
/*  351 */     boolean mainChanged = this.mDetails.isModified();
/*  352 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  355 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  358 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  361 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  362 */         stmt = getConnection().prepareStatement("update BUDGET_LIMIT set FINANCE_CUBE_ID = ?,BUDGET_LOCATION_ELEMENT_ID = ?,DIM0 = ?,DIM1 = ?,DIM2 = ?,DIM3 = ?,DIM4 = ?,DIM5 = ?,DIM6 = ?,DIM7 = ?,DIM8 = ?,DIM9 = ?,DATA_TYPE = ?,MIN_VALUE = ?,MAX_VALUE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    BUDGET_LIMIT_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  365 */         int col = 1;
/*  366 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  367 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  369 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  372 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  374 */         if (resultCount == 0) {
/*  375 */           checkVersionNum();
/*      */         }
/*  377 */         if (resultCount != 1) {
/*  378 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  381 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  390 */       throw handleSQLException(getPK(), "update BUDGET_LIMIT set FINANCE_CUBE_ID = ?,BUDGET_LOCATION_ELEMENT_ID = ?,DIM0 = ?,DIM1 = ?,DIM2 = ?,DIM3 = ?,DIM4 = ?,DIM5 = ?,DIM6 = ?,DIM7 = ?,DIM8 = ?,DIM9 = ?,DATA_TYPE = ?,MIN_VALUE = ?,MAX_VALUE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    BUDGET_LIMIT_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  394 */       closeStatement(stmt);
/*  395 */       closeConnection();
/*      */ 
/*  397 */       if ((timer != null) && (
/*  398 */         (mainChanged) || (dependantChanged)))
/*  399 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  411 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  412 */     PreparedStatement stmt = null;
/*  413 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  417 */       stmt = getConnection().prepareStatement("select VERSION_NUM from BUDGET_LIMIT where BUDGET_LIMIT_ID = ?");
/*      */ 
/*  420 */       int col = 1;
/*  421 */       stmt.setInt(col++, this.mDetails.getBudgetLimitId());
/*      */ 
/*  424 */       resultSet = stmt.executeQuery();
/*      */ 
/*  426 */       if (!resultSet.next()) {
/*  427 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  430 */       col = 1;
/*  431 */       int dbVersionNumber = resultSet.getInt(col++);
/*  432 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  433 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  439 */       throw handleSQLException(getPK(), "select VERSION_NUM from BUDGET_LIMIT where BUDGET_LIMIT_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  443 */       closeStatement(stmt);
/*  444 */       closeResultSet(resultSet);
/*      */ 
/*  446 */       if (timer != null)
/*  447 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllBudgetLimitsELO getAllBudgetLimits()
/*      */   {
/*  490 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  491 */     PreparedStatement stmt = null;
/*  492 */     ResultSet resultSet = null;
/*  493 */     AllBudgetLimitsELO results = new AllBudgetLimitsELO();
/*      */     try
/*      */     {
/*  496 */       stmt = getConnection().prepareStatement(SQL_ALL_BUDGET_LIMITS);
/*  497 */       int col = 1;
/*  498 */       resultSet = stmt.executeQuery();
/*  499 */       while (resultSet.next())
/*      */       {
/*  501 */         col = 2;
/*      */ 
/*  504 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  507 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  509 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  512 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  515 */         BudgetLimitPK pkBudgetLimit = new BudgetLimitPK(resultSet.getInt(col++));
/*      */ 
/*  518 */         String textBudgetLimit = "";
/*      */ 
/*  523 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/*  529 */         BudgetLimitCK ckBudgetLimit = new BudgetLimitCK(pkModel, pkFinanceCube, pkBudgetLimit);
/*      */ 
/*  536 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  542 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/*  548 */         BudgetLimitRefImpl erBudgetLimit = new BudgetLimitRefImpl(ckBudgetLimit, textBudgetLimit);
/*      */ 
/*  553 */         String col1 = resultSet.getString(col++);
/*  554 */         Long col2 = getWrappedLongFromJdbc(resultSet, col++);
/*  555 */         Long col3 = getWrappedLongFromJdbc(resultSet, col++);
/*      */ 
/*  558 */         results.add(erBudgetLimit, erFinanceCube, erModel, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  570 */       throw handleSQLException(SQL_ALL_BUDGET_LIMITS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  574 */       closeResultSet(resultSet);
/*  575 */       closeStatement(stmt);
/*  576 */       closeConnection();
/*      */     }
/*      */ 
/*  579 */     if (timer != null) {
/*  580 */       timer.logDebug("getAllBudgetLimits", " items=" + results.size());
/*      */     }
/*      */ 
/*  584 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  601 */     if (items == null) {
/*  602 */       return false;
/*      */     }
/*  604 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  605 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  607 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  612 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  613 */       while (iter2.hasNext())
/*      */       {
/*  615 */         this.mDetails = ((BudgetLimitEVO)iter2.next());
/*      */ 
/*  618 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  620 */         somethingChanged = true;
/*      */ 
/*  623 */         if (deleteStmt == null) {
/*  624 */           deleteStmt = getConnection().prepareStatement("delete from BUDGET_LIMIT where    BUDGET_LIMIT_ID = ? ");
/*      */         }
/*      */ 
/*  627 */         int col = 1;
/*  628 */         deleteStmt.setInt(col++, this.mDetails.getBudgetLimitId());
/*      */ 
/*  630 */         if (this._log.isDebugEnabled()) {
/*  631 */           this._log.debug("update", "BudgetLimit deleting BudgetLimitId=" + this.mDetails.getBudgetLimitId());
/*      */         }
/*      */ 
/*  636 */         deleteStmt.addBatch();
/*      */ 
/*  639 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  644 */       if (deleteStmt != null)
/*      */       {
/*  646 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  648 */         deleteStmt.executeBatch();
/*      */ 
/*  650 */         if (timer2 != null) {
/*  651 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  655 */       Iterator iter1 = items.values().iterator();
/*  656 */       while (iter1.hasNext())
/*      */       {
/*  658 */         this.mDetails = ((BudgetLimitEVO)iter1.next());
/*      */ 
/*  660 */         if (this.mDetails.insertPending())
/*      */         {
/*  662 */           somethingChanged = true;
/*  663 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  666 */         if (!this.mDetails.isModified())
/*      */           continue;
/*  668 */         somethingChanged = true;
/*  669 */         doStore();
/*      */       }
/*      */ 
/*  680 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  684 */       throw handleSQLException("delete from BUDGET_LIMIT where    BUDGET_LIMIT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  688 */       if (deleteStmt != null)
/*      */       {
/*  690 */         closeStatement(deleteStmt);
/*  691 */         closeConnection();
/*      */       }
/*      */ 
/*  694 */       this.mDetails = null;
/*      */ 
/*  696 */       if ((somethingChanged) && 
/*  697 */         (timer != null))
/*  698 */         timer.logDebug("update", "collection"); 
/*  698 */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*      */   {
/*  721 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  723 */     PreparedStatement stmt = null;
/*  724 */     ResultSet resultSet = null;
/*      */ 
/*  726 */     int itemCount = 0;
/*      */ 
/*  728 */     FinanceCubeEVO owningEVO = null;
/*  729 */     Iterator ownersIter = owners.iterator();
/*  730 */     while (ownersIter.hasNext())
/*      */     {
/*  732 */       owningEVO = (FinanceCubeEVO)ownersIter.next();
/*  733 */       owningEVO.setBudgetLimitsAllItemsLoaded(true);
/*      */     }
/*  735 */     ownersIter = owners.iterator();
/*  736 */     owningEVO = (FinanceCubeEVO)ownersIter.next();
/*  737 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  741 */       stmt = getConnection().prepareStatement("select BUDGET_LIMIT.BUDGET_LIMIT_ID,BUDGET_LIMIT.FINANCE_CUBE_ID,BUDGET_LIMIT.BUDGET_LOCATION_ELEMENT_ID,BUDGET_LIMIT.DIM0,BUDGET_LIMIT.DIM1,BUDGET_LIMIT.DIM2,BUDGET_LIMIT.DIM3,BUDGET_LIMIT.DIM4,BUDGET_LIMIT.DIM5,BUDGET_LIMIT.DIM6,BUDGET_LIMIT.DIM7,BUDGET_LIMIT.DIM8,BUDGET_LIMIT.DIM9,BUDGET_LIMIT.DATA_TYPE,BUDGET_LIMIT.MIN_VALUE,BUDGET_LIMIT.MAX_VALUE,BUDGET_LIMIT.VERSION_NUM,BUDGET_LIMIT.UPDATED_BY_USER_ID,BUDGET_LIMIT.UPDATED_TIME,BUDGET_LIMIT.CREATED_TIME from BUDGET_LIMIT,FINANCE_CUBE where 1=1 and BUDGET_LIMIT.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  BUDGET_LIMIT.FINANCE_CUBE_ID ,BUDGET_LIMIT.BUDGET_LIMIT_ID");
/*      */ 
/*  743 */       int col = 1;
/*  744 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  746 */       resultSet = stmt.executeQuery();
/*      */ 
/*  749 */       while (resultSet.next())
/*      */       {
/*  751 */         itemCount++;
/*  752 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  757 */         while (this.mDetails.getFinanceCubeId() != owningEVO.getFinanceCubeId())
/*      */         {
/*  761 */           if (!ownersIter.hasNext())
/*      */           {
/*  763 */             this._log.debug("bulkGetAll", "can't find owning [FinanceCubeId=" + this.mDetails.getFinanceCubeId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  767 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  768 */             ownersIter = owners.iterator();
/*  769 */             while (ownersIter.hasNext())
/*      */             {
/*  771 */               owningEVO = (FinanceCubeEVO)ownersIter.next();
/*  772 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  774 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  776 */           owningEVO = (FinanceCubeEVO)ownersIter.next();
/*      */         }
/*  778 */         if (owningEVO.getBudgetLimits() == null)
/*      */         {
/*  780 */           theseItems = new ArrayList();
/*  781 */           owningEVO.setBudgetLimits(theseItems);
/*  782 */           owningEVO.setBudgetLimitsAllItemsLoaded(true);
/*      */         }
/*  784 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  787 */       if (timer != null) {
/*  788 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  793 */       throw handleSQLException("select BUDGET_LIMIT.BUDGET_LIMIT_ID,BUDGET_LIMIT.FINANCE_CUBE_ID,BUDGET_LIMIT.BUDGET_LOCATION_ELEMENT_ID,BUDGET_LIMIT.DIM0,BUDGET_LIMIT.DIM1,BUDGET_LIMIT.DIM2,BUDGET_LIMIT.DIM3,BUDGET_LIMIT.DIM4,BUDGET_LIMIT.DIM5,BUDGET_LIMIT.DIM6,BUDGET_LIMIT.DIM7,BUDGET_LIMIT.DIM8,BUDGET_LIMIT.DIM9,BUDGET_LIMIT.DATA_TYPE,BUDGET_LIMIT.MIN_VALUE,BUDGET_LIMIT.MAX_VALUE,BUDGET_LIMIT.VERSION_NUM,BUDGET_LIMIT.UPDATED_BY_USER_ID,BUDGET_LIMIT.UPDATED_TIME,BUDGET_LIMIT.CREATED_TIME from BUDGET_LIMIT,FINANCE_CUBE where 1=1 and BUDGET_LIMIT.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  BUDGET_LIMIT.FINANCE_CUBE_ID ,BUDGET_LIMIT.BUDGET_LIMIT_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  797 */       closeResultSet(resultSet);
/*  798 */       closeStatement(stmt);
/*  799 */       closeConnection();
/*      */ 
/*  801 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectFinanceCubeId, String dependants, Collection currentList)
/*      */   {
/*  826 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  827 */     PreparedStatement stmt = null;
/*  828 */     ResultSet resultSet = null;
/*      */ 
/*  830 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  834 */       stmt = getConnection().prepareStatement("select BUDGET_LIMIT.BUDGET_LIMIT_ID,BUDGET_LIMIT.FINANCE_CUBE_ID,BUDGET_LIMIT.BUDGET_LOCATION_ELEMENT_ID,BUDGET_LIMIT.DIM0,BUDGET_LIMIT.DIM1,BUDGET_LIMIT.DIM2,BUDGET_LIMIT.DIM3,BUDGET_LIMIT.DIM4,BUDGET_LIMIT.DIM5,BUDGET_LIMIT.DIM6,BUDGET_LIMIT.DIM7,BUDGET_LIMIT.DIM8,BUDGET_LIMIT.DIM9,BUDGET_LIMIT.DATA_TYPE,BUDGET_LIMIT.MIN_VALUE,BUDGET_LIMIT.MAX_VALUE,BUDGET_LIMIT.VERSION_NUM,BUDGET_LIMIT.UPDATED_BY_USER_ID,BUDGET_LIMIT.UPDATED_TIME,BUDGET_LIMIT.CREATED_TIME from BUDGET_LIMIT where    FINANCE_CUBE_ID = ? ");
/*      */ 
/*  836 */       int col = 1;
/*  837 */       stmt.setInt(col++, selectFinanceCubeId);
/*      */ 
/*  839 */       resultSet = stmt.executeQuery();
/*      */ 
/*  841 */       while (resultSet.next())
/*      */       {
/*  843 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  846 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  849 */       if (currentList != null)
/*      */       {
/*  852 */         ListIterator iter = items.listIterator();
/*  853 */         BudgetLimitEVO currentEVO = null;
/*  854 */         BudgetLimitEVO newEVO = null;
/*  855 */         while (iter.hasNext())
/*      */         {
/*  857 */           newEVO = (BudgetLimitEVO)iter.next();
/*  858 */           Iterator iter2 = currentList.iterator();
/*  859 */           while (iter2.hasNext())
/*      */           {
/*  861 */             currentEVO = (BudgetLimitEVO)iter2.next();
/*  862 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  864 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  870 */         Iterator iter2 = currentList.iterator();
/*  871 */         while (iter2.hasNext())
/*      */         {
/*  873 */           currentEVO = (BudgetLimitEVO)iter2.next();
/*  874 */           if (currentEVO.insertPending()) {
/*  875 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  879 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  883 */       throw handleSQLException("select BUDGET_LIMIT.BUDGET_LIMIT_ID,BUDGET_LIMIT.FINANCE_CUBE_ID,BUDGET_LIMIT.BUDGET_LOCATION_ELEMENT_ID,BUDGET_LIMIT.DIM0,BUDGET_LIMIT.DIM1,BUDGET_LIMIT.DIM2,BUDGET_LIMIT.DIM3,BUDGET_LIMIT.DIM4,BUDGET_LIMIT.DIM5,BUDGET_LIMIT.DIM6,BUDGET_LIMIT.DIM7,BUDGET_LIMIT.DIM8,BUDGET_LIMIT.DIM9,BUDGET_LIMIT.DATA_TYPE,BUDGET_LIMIT.MIN_VALUE,BUDGET_LIMIT.MAX_VALUE,BUDGET_LIMIT.VERSION_NUM,BUDGET_LIMIT.UPDATED_BY_USER_ID,BUDGET_LIMIT.UPDATED_TIME,BUDGET_LIMIT.CREATED_TIME from BUDGET_LIMIT where    FINANCE_CUBE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  887 */       closeResultSet(resultSet);
/*  888 */       closeStatement(stmt);
/*  889 */       closeConnection();
/*      */ 
/*  891 */       if (timer != null) {
/*  892 */         timer.logDebug("getAll", " FinanceCubeId=" + selectFinanceCubeId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  897 */     return items;
/*      */   }
/*      */ 
/*      */   public BudgetLimitEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  911 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  914 */     if (this.mDetails == null) {
/*  915 */       doLoad(((BudgetLimitCK)paramCK).getBudgetLimitPK());
/*      */     }
/*  917 */     else if (!this.mDetails.getPK().equals(((BudgetLimitCK)paramCK).getBudgetLimitPK())) {
/*  918 */       doLoad(((BudgetLimitCK)paramCK).getBudgetLimitPK());
/*      */     }
/*      */ 
/*  921 */     BudgetLimitEVO details = new BudgetLimitEVO();
/*  922 */     details = this.mDetails.deepClone();
/*      */ 
/*  924 */     if (timer != null) {
/*  925 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  927 */     return details;
/*      */   }
/*      */ 
/*      */   public BudgetLimitEVO getDetails(ModelCK paramCK, BudgetLimitEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  933 */     BudgetLimitEVO savedEVO = this.mDetails;
/*  934 */     this.mDetails = paramEVO;
/*  935 */     BudgetLimitEVO newEVO = getDetails(paramCK, dependants);
/*  936 */     this.mDetails = savedEVO;
/*  937 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public BudgetLimitEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  943 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  947 */     BudgetLimitEVO details = this.mDetails.deepClone();
/*      */ 
/*  949 */     if (timer != null) {
/*  950 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  952 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  957 */     return "BudgetLimit";
/*      */   }
/*      */ 
/*      */   public BudgetLimitRefImpl getRef(BudgetLimitPK paramBudgetLimitPK)
/*      */   {
/*  962 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  963 */     PreparedStatement stmt = null;
/*  964 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  967 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID from BUDGET_LIMIT,MODEL,FINANCE_CUBE where 1=1 and BUDGET_LIMIT.BUDGET_LIMIT_ID = ? and BUDGET_LIMIT.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID");
/*  968 */       int col = 1;
/*  969 */       stmt.setInt(col++, paramBudgetLimitPK.getBudgetLimitId());
/*      */ 
/*  971 */       resultSet = stmt.executeQuery();
/*      */ 
/*  973 */       if (!resultSet.next()) {
/*  974 */         throw new RuntimeException(getEntityName() + " getRef " + paramBudgetLimitPK + " not found");
/*      */       }
/*  976 */       col = 2;
/*  977 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  981 */       FinanceCubePK newFinanceCubePK = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  985 */       String textBudgetLimit = "";
/*  986 */       BudgetLimitCK ckBudgetLimit = new BudgetLimitCK(newModelPK, newFinanceCubePK, paramBudgetLimitPK);
/*      */ 
/*  992 */       BudgetLimitRefImpl localBudgetLimitRefImpl = new BudgetLimitRefImpl(ckBudgetLimit, textBudgetLimit);
/*      */       return localBudgetLimitRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  997 */       throw handleSQLException(paramBudgetLimitPK, "select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID from BUDGET_LIMIT,MODEL,FINANCE_CUBE where 1=1 and BUDGET_LIMIT.BUDGET_LIMIT_ID = ? and BUDGET_LIMIT.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1001 */       closeResultSet(resultSet);
/* 1002 */       closeStatement(stmt);
/* 1003 */       closeConnection();
/*      */ 
/* 1005 */       if (timer != null)
/* 1006 */         timer.logDebug("getRef", paramBudgetLimitPK); 
/* 1006 */     }
/*      */   }
/*      */ 
/*      */   public List getBudgetLimitsForBudgetLocation(int modelId, int budgetLocation)
/*      */   {
/* 1016 */     List returnValues = new ArrayList();
/*      */ 
/* 1018 */     DimensionDAO dimDao = new DimensionDAO();
/* 1019 */     EntityList list = dimDao.getAllDimensionsForModel(modelId);
/* 1020 */     int noOfDims = list.getNumRows();
/*      */ 
/* 1022 */     FinanceCubeDAO finDAO = new FinanceCubeDAO();
/* 1023 */     list = finDAO.getAllFinanceCubesWebForModel(modelId);
/* 1024 */     int fcId = 0;
/* 1025 */     int size = list.getNumRows();
/*      */ 
/* 1027 */     for (int i = 0; i < size; i++)
/*      */     {
/* 1029 */       fcId = ((Integer)list.getValueAt(i, "FinanceCubeId")).intValue();
/* 1030 */       returnValues.addAll(getBudgetLimitsForBudgetLocation(noOfDims, fcId, budgetLocation, true));
/*      */     }
/*      */ 
/* 1033 */     return returnValues;
/*      */   }
/*      */ 
/*      */   public List getBudgetLimitsForBudgetLocation(int dimCount, int financeCubeId, int budgetLocation, boolean onlyViolations)
/*      */   {
/* 1049 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1050 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "select  distinct", "        bl.BUDGET_LIMIT_ID", "       ,s0.STRUCTURE_ELEMENT_ID", "       ${visIdAndDescrs}", "       ,dt.VIS_ID || ' ' || dt.DESCRIPTION", "       ,bl.MIN_VALUE / ${scaleFactor}", "       ,bl.MAX_VALUE / ${scaleFactor}", "       ,nft.PUBLIC_VALUE / ${scaleFactor}", "from    BUDGET_LIMIT bl", "join    STRUCTURE_ELEMENT s0 on (s0.STRUCTURE_ELEMENT_ID = BUDGET_LOCATION_ELEMENT_ID)", "${joinStructureElements}", "join    DATA_TYPE dt on (dt.VIS_ID = DATA_TYPE)", "left", "join    NFT${financeCubeId} nft using (${dims},DATA_TYPE)", "where   bl.FINANCE_CUBE_ID = ${financeCubeId}", "and     DIM0 = <dim0Id>", "and     (nvl(nft.PUBLIC_VALUE,0) < bl.MIN_VALUE or nvl(nft.PUBLIC_VALUE,0) > bl.MAX_VALUE)" });
/*      */ 
/* 1070 */     sqlb.substituteRepeatingLines("${visIdAndDescrs}", dimCount, "", new String[] { ", s${index}.CAL_VIS_ID_PREFIX || s${index}.VIS_ID || ' ' ||s${index}.DESCRIPTION" });
/*      */ 
/* 1073 */     sqlb.substituteRepeatingLines("${joinStructureElements}", dimCount - 1, "", new String[] { "join    STRUCTURE_ELEMENT s${num} on (s${num}.STRUCTURE_ELEMENT_ID = DIM${num})" });
/*      */ 
/* 1076 */     sqlb.substitute(new String[] { "${dims}", SqlBuilder.repeatString("${separator}DIM${index}", ",", dimCount) });
/* 1077 */     sqlb.substitute(new String[] { "${financeCubeId}", String.valueOf(financeCubeId) });
/* 1078 */     sqlb.substitute(new String[] { "${scaleFactor}", String.valueOf(10000) });
/*      */ 
/* 1080 */     SqlExecutor sqle = new SqlExecutor("getBudgetLimitsForBudgetLocation", getDataSource(), sqlb, this._log);
/* 1081 */     sqle.addBindVariable("<dim0Id>", Integer.valueOf(budgetLocation));
/*      */ 
/* 1083 */     List results = new ArrayList();
/*      */     try
/*      */     {
/* 1086 */       ResultSet resultSet = sqle.getResultSet();
/* 1087 */       while (resultSet.next())
/*      */       {
/* 1089 */         List row = new ArrayList();
/* 1090 */         int resIndex = 1;
/* 1091 */         row.add(new Integer(resultSet.getInt(resIndex++)));
/* 1092 */         row.add(new Integer(resultSet.getInt(resIndex++)));
/* 1093 */         for (int i = 0; i < dimCount; i++)
/*      */         {
/* 1095 */           row.add(resultSet.getString(resIndex++));
/*      */         }
/* 1097 */         row.add(resultSet.getString(resIndex++));
/* 1098 */         row.add(resultSet.getBigDecimal(resIndex++));
/* 1099 */         row.add(resultSet.getBigDecimal(resIndex++));
/* 1100 */         row.add(resultSet.getBigDecimal(resIndex++));
/*      */ 
/* 1102 */         results.add(row);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1107 */       System.err.println(e);
/*      */       throw new RuntimeException(getEntityName() + " getBudgetLimitsForBudgetLocation", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1113 */       sqle.close();
/*      */     }
/*      */ 
/* 1116 */     if (timer != null) {
/* 1117 */       timer.logDebug("getBudgetLimitsForBudgetLocation", " items=" + results.size());
/*      */     }
/* 1119 */     return results;
/*      */   }
/*      */ 
/*      */   public List getBudgetLimitsSetByBudgetLocation(int dimCount, int financeCubeId, int budgetLocation)
/*      */   {
/* 1133 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1134 */     PreparedStatement stmt = null;
/* 1135 */     ResultSet resultSet = null;
/* 1136 */     List results = new ArrayList();
/*      */     try
/*      */     {
/* 1139 */       StringBuffer sql = new StringBuffer();
/* 1140 */       sql.append("select distinct bl.budget_limit_id");
/* 1141 */       for (int i = 0; i < dimCount; i++)
/*      */       {
/* 1143 */         sql.append(',');
/* 1144 */         sql.append('s');
/* 1145 */         sql.append(i);
/* 1146 */         sql.append(".vis_id || ' ' || s");
/* 1147 */         sql.append(i);
/* 1148 */         sql.append(".description");
/*      */       }
/* 1150 */       sql.append(",(select dt.vis_id || ' ' || dt.description from data_type dt where dt.vis_id = bl.data_type) ");
/* 1151 */       sql.append(" ,bl.min_value/");
/* 1152 */       sql.append(10000);
/* 1153 */       sql.append(",bl.max_value/");
/* 1154 */       sql.append(10000);
/* 1155 */       sql.append(" from budget_limit bl");
/* 1156 */       for (int i = 0; i < dimCount; i++)
/*      */       {
/* 1158 */         sql.append(",structure_element s" + i);
/*      */       }
/* 1160 */       sql.append(" where bl.finance_cube_id = ? ");
/* 1161 */       sql.append("   and bl.budget_location_element_id = ?");
/* 1162 */       for (int i = 0; i < dimCount; i++)
/*      */       {
/* 1164 */         sql.append("   and bl.dim" + i + " = s" + i + ".structure_element_id (+)");
/*      */       }
/*      */ 
/* 1167 */       stmt = getConnection().prepareStatement(sql.toString());
/* 1168 */       if (this._log.isDebugEnabled()) {
/* 1169 */         this._log.debug("getBudgetLimitsSetByBudgetLocation", "sql is:\n" + sql);
/*      */       }
/*      */ 
/* 1172 */       int index = 1;
/* 1173 */       stmt.setInt(index++, financeCubeId);
/* 1174 */       if (this._log.isDebugEnabled())
/* 1175 */         this._log.debug("getBudgetLimitsSetByBudgetLocation", "Bind var = " + financeCubeId);
/* 1176 */       stmt.setInt(index++, budgetLocation);
/* 1177 */       if (this._log.isDebugEnabled()) {
/* 1178 */         this._log.debug("getBudgetLimitsSetByBudgetLocation", "Bind var = " + budgetLocation);
/*      */       }
/* 1180 */       resultSet = stmt.executeQuery();
/* 1181 */       while (resultSet.next())
/*      */       {
/* 1183 */         List row = new ArrayList();
/* 1184 */         int resIndex = 1;
/* 1185 */         row.add(new Integer(resultSet.getInt(resIndex++)));
/* 1186 */         for (int i = 0; i < dimCount; i++)
/*      */         {
/* 1188 */           row.add(resultSet.getString(resIndex++));
/*      */         }
/* 1190 */         row.add(resultSet.getString(resIndex++));
/* 1191 */         row.add(resultSet.getBigDecimal(resIndex++));
/* 1192 */         row.add(resultSet.getBigDecimal(resIndex++));
/*      */ 
/* 1194 */         results.add(row);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1199 */       System.err.println(sqle);
/* 1200 */       sqle.printStackTrace();
/* 1201 */       throw new RuntimeException(getEntityName() + " getBudgetLimitsSetByBudgetLocation", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1205 */       closeResultSet(resultSet);
/* 1206 */       closeStatement(stmt);
/* 1207 */       closeConnection();
/*      */     }
/*      */ 
/* 1210 */     if (timer != null) {
/* 1211 */       timer.logDebug("getBudgetLimitsSetByBudgetLocation", " items=" + results.size());
/*      */     }
/* 1213 */     return results;
/*      */   }
/*      */ 
/*      */   public List getBudgetLimitViolationsForBudgetLocation(int modelId, int budgetLocation)
/*      */   {
/* 1218 */     List returnValues = new ArrayList();
/*      */ 
/* 1220 */     DimensionDAO dimDao = new DimensionDAO();
/* 1221 */     EntityList list = dimDao.getAllDimensionsForModel(modelId);
/* 1222 */     int noOfDims = list.getNumRows();
/*      */ 
/* 1224 */     FinanceCubeDAO finDAO = new FinanceCubeDAO();
/* 1225 */     list = finDAO.getAllFinanceCubesWebForModel(modelId);
/* 1226 */     int fcId = 0;
/* 1227 */     int size = list.getNumRows();
/*      */ 
/* 1229 */     for (int i = 0; i < size; i++)
/*      */     {
/* 1231 */       fcId = ((Integer)list.getValueAt(i, "FinanceCubeId")).intValue();
/* 1232 */       returnValues.addAll(getBudgetLimitViolationsForBudgetLocation(noOfDims, fcId, budgetLocation));
/*      */     }
/*      */ 
/* 1235 */     return returnValues;
/*      */   }
/*      */ 
/*      */   public List getBudgetLimitViolationsForBudgetLocation(int dimCount, int financeCubeId, int budgetLocation)
/*      */   {
/* 1241 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1242 */     PreparedStatement stmt = null;
/* 1243 */     ResultSet resultSet = null;
/* 1244 */     List results = new ArrayList();
/*      */     try
/*      */     {
/* 1247 */       StringBuffer sql = new StringBuffer();
/* 1248 */       sql.append("select distinct bl.budget_limit_id");
/* 1249 */       for (int i = 0; i < dimCount; i++)
/*      */       {
/* 1251 */         sql.append(',');
/* 1252 */         sql.append('s');
/* 1253 */         sql.append(i);
/* 1254 */         sql.append(".structure_element_id");
/*      */       }
/*      */ 
/* 1257 */       sql.append(",bl.data_type,bl.min_value/");
/* 1258 */       sql.append(10000);
/* 1259 */       sql.append(",bl.max_value/");
/* 1260 */       sql.append(10000);
/* 1261 */       sql.append(",nft.public_value/");
/* 1262 */       sql.append(10000);
/* 1263 */       sql.append(" from budget_limit bl");
/* 1264 */       sql.append("     ,nft" + financeCubeId + " nft");
/*      */ 
/* 1266 */       for (int i = 0; i < dimCount; i++)
/*      */       {
/* 1268 */         sql.append(",structure_element s" + i);
/*      */       }
/*      */ 
/* 1271 */       sql.append(" where bl.finance_cube_id = ? ");
/* 1272 */       sql.append("   and bl.dim0 = ?");
/* 1273 */       sql.append("   and bl.data_type = nft.data_type (+)");
/* 1274 */       sql.append("   and ((nft.public_value is null and bl.min_value is not null) or nft.public_value < bl.min_value or nft.public_value > bl.max_value)");
/*      */ 
/* 1276 */       for (int i = 0; i < dimCount; i++)
/*      */       {
/* 1278 */         sql.append("   and bl.dim" + i + " = nft.dim" + i + " (+)");
/* 1279 */         if (i == 0)
/* 1280 */           sql.append("   and bl.budget_location_element_id = s0.structure_element_id ");
/*      */         else {
/* 1282 */           sql.append("   and bl.dim" + i + " = s" + i + ".structure_element_id ");
/*      */         }
/*      */       }
/* 1285 */       stmt = getConnection().prepareStatement(sql.toString());
/* 1286 */       if (this._log.isDebugEnabled()) {
/* 1287 */         this._log.debug("getBudgetLimitViolationsForBudgetLocation", "sql is:\n" + sql);
/*      */       }
/*      */ 
/* 1290 */       int index = 1;
/* 1291 */       stmt.setInt(index++, financeCubeId);
/* 1292 */       if (this._log.isDebugEnabled())
/* 1293 */         this._log.debug("getBudgetLimitViolationsForBudgetLocation", "Bind var = " + financeCubeId);
/* 1294 */       stmt.setInt(index++, budgetLocation);
/* 1295 */       if (this._log.isDebugEnabled()) {
/* 1296 */         this._log.debug("getBudgetLimitViolationsForBudgetLocation", "Bind var = " + budgetLocation);
/*      */       }
/* 1298 */       resultSet = stmt.executeQuery();
/* 1299 */       while (resultSet.next())
/*      */       {
/* 1301 */         List row = new ArrayList();
/* 1302 */         int resIndex = 1;
/* 1303 */         row.add(new Integer(financeCubeId));
/* 1304 */         resultSet.getInt(resIndex++);
/* 1305 */         for (int i = 0; i < dimCount; i++)
/*      */         {
/* 1307 */           if (i == 0)
/* 1308 */             resultSet.getInt(resIndex++);
/*      */           else
/* 1310 */             row.add(new Integer(resultSet.getInt(resIndex++)));
/*      */         }
/* 1312 */         row.add(resultSet.getString(resIndex++));
/*      */ 
/* 1314 */         results.add(row);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1319 */       System.err.println(sqle);
/* 1320 */       sqle.printStackTrace();
/* 1321 */       throw new RuntimeException(getEntityName() + " getBudgetLimitViolationsForBudgetLocation", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1325 */       closeResultSet(resultSet);
/* 1326 */       closeStatement(stmt);
/* 1327 */       closeConnection();
/*      */     }
/*      */ 
/* 1330 */     if (timer != null) {
/* 1331 */       timer.logDebug("getBudgetLimitViolationsForBudgetLocation", " items=" + results.size());
/*      */     }
/* 1333 */     return results;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.budgetlimit.BudgetLimitDAO
 * JD-Core Version:    0.6.0
 */