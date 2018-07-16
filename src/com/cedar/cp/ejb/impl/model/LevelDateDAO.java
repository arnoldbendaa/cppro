/*     */ package com.cedar.cp.ejb.impl.model;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.BudgetCyclePK;
/*     */ import com.cedar.cp.dto.model.LevelDateCK;
/*     */ import com.cedar.cp.dto.model.LevelDatePK;
/*     */ import com.cedar.cp.dto.model.LevelDateRefImpl;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class LevelDateDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select LEVEL_DATE.BUDGET_CYCLE_ID,LEVEL_DATE.DEPTH,LEVEL_DATE.PLANNED_END_DATE,LEVEL_DATE.VERSION_NUM,LEVEL_DATE.UPDATED_BY_USER_ID,LEVEL_DATE.UPDATED_TIME,LEVEL_DATE.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from LEVEL_DATE where    BUDGET_CYCLE_ID = ? AND DEPTH = ? ";
/*     */   protected static final String SQL_CREATE = "insert into LEVEL_DATE ( BUDGET_CYCLE_ID,DEPTH,PLANNED_END_DATE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update LEVEL_DATE set PLANNED_END_DATE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    BUDGET_CYCLE_ID = ? AND DEPTH = ? AND VERSION_NUM = ?";
/*     */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from LEVEL_DATE where BUDGET_CYCLE_ID = ?,DEPTH = ?";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from LEVEL_DATE where    BUDGET_CYCLE_ID = ? AND DEPTH = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from LEVEL_DATE,BUDGET_CYCLE where 1=1 and LEVEL_DATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.MODEL_ID = ? order by  LEVEL_DATE.BUDGET_CYCLE_ID ,LEVEL_DATE.BUDGET_CYCLE_ID ,LEVEL_DATE.DEPTH";
/*     */   protected static final String SQL_GET_ALL = " from LEVEL_DATE where    BUDGET_CYCLE_ID = ? ";
/*     */   protected LevelDateEVO mDetails;
/*     */ 
/*     */   public LevelDateDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public LevelDateDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public LevelDateDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected LevelDatePK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(LevelDateEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private LevelDateEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  90 */     int col = 1;
/*  91 */     LevelDateEVO evo = new LevelDateEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getTimestamp(col++), resultSet_.getInt(col++));
/*     */ 
/*  98 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  99 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 100 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 101 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(LevelDateEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 106 */     int col = startCol_;
/* 107 */     stmt_.setInt(col++, evo_.getBudgetCycleId());
/* 108 */     stmt_.setInt(col++, evo_.getDepth());
/* 109 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(LevelDateEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 114 */     int col = startCol_;
/* 115 */     stmt_.setTimestamp(col++, evo_.getPlannedEndDate());
/* 116 */     stmt_.setInt(col++, evo_.getVersionNum());
/* 117 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 118 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 119 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 120 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(LevelDatePK pk)
/*     */     throws ValidationException
/*     */   {
/* 137 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 139 */     PreparedStatement stmt = null;
/* 140 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 144 */       stmt = getConnection().prepareStatement("select LEVEL_DATE.BUDGET_CYCLE_ID,LEVEL_DATE.DEPTH,LEVEL_DATE.PLANNED_END_DATE,LEVEL_DATE.VERSION_NUM,LEVEL_DATE.UPDATED_BY_USER_ID,LEVEL_DATE.UPDATED_TIME,LEVEL_DATE.CREATED_TIME from LEVEL_DATE where    BUDGET_CYCLE_ID = ? AND DEPTH = ? ");
/*     */ 
/* 147 */       int col = 1;
/* 148 */       stmt.setInt(col++, pk.getBudgetCycleId());
/* 149 */       stmt.setInt(col++, pk.getDepth());
/*     */ 
/* 151 */       resultSet = stmt.executeQuery();
/*     */ 
/* 153 */       if (!resultSet.next()) {
/* 154 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 157 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 158 */       if (this.mDetails.isModified())
/* 159 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 163 */       throw handleSQLException(pk, "select LEVEL_DATE.BUDGET_CYCLE_ID,LEVEL_DATE.DEPTH,LEVEL_DATE.PLANNED_END_DATE,LEVEL_DATE.VERSION_NUM,LEVEL_DATE.UPDATED_BY_USER_ID,LEVEL_DATE.UPDATED_TIME,LEVEL_DATE.CREATED_TIME from LEVEL_DATE where    BUDGET_CYCLE_ID = ? AND DEPTH = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 167 */       closeResultSet(resultSet);
/* 168 */       closeStatement(stmt);
/* 169 */       closeConnection();
/*     */ 
/* 171 */       if (timer != null)
/* 172 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 203 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 204 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 209 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 210 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 211 */       stmt = getConnection().prepareStatement("insert into LEVEL_DATE ( BUDGET_CYCLE_ID,DEPTH,PLANNED_END_DATE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*     */ 
/* 214 */       int col = 1;
/* 215 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 216 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 219 */       int resultCount = stmt.executeUpdate();
/* 220 */       if (resultCount != 1)
/*     */       {
/* 222 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 225 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 229 */       throw handleSQLException(this.mDetails.getPK(), "insert into LEVEL_DATE ( BUDGET_CYCLE_ID,DEPTH,PLANNED_END_DATE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 233 */       closeStatement(stmt);
/* 234 */       closeConnection();
/*     */ 
/* 236 */       if (timer != null)
/* 237 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 263 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 267 */     PreparedStatement stmt = null;
/*     */ 
/* 269 */     boolean mainChanged = this.mDetails.isModified();
/* 270 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 273 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 276 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*     */ 
/* 279 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 280 */         stmt = getConnection().prepareStatement("update LEVEL_DATE set PLANNED_END_DATE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    BUDGET_CYCLE_ID = ? AND DEPTH = ? AND VERSION_NUM = ?");
/*     */ 
/* 283 */         int col = 1;
/* 284 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 285 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 287 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*     */ 
/* 290 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 292 */         if (resultCount == 0) {
/* 293 */           checkVersionNum();
/*     */         }
/* 295 */         if (resultCount != 1) {
/* 296 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 299 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 308 */       throw handleSQLException(getPK(), "update LEVEL_DATE set PLANNED_END_DATE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    BUDGET_CYCLE_ID = ? AND DEPTH = ? AND VERSION_NUM = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 312 */       closeStatement(stmt);
/* 313 */       closeConnection();
/*     */ 
/* 315 */       if ((timer != null) && (
/* 316 */         (mainChanged) || (dependantChanged)))
/* 317 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkVersionNum()
/*     */     throws VersionValidationException
/*     */   {
/* 330 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 331 */     PreparedStatement stmt = null;
/* 332 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 336 */       stmt = getConnection().prepareStatement("select VERSION_NUM from LEVEL_DATE where BUDGET_CYCLE_ID = ?,DEPTH = ?");
/*     */ 
/* 339 */       int col = 1;
/* 340 */       stmt.setInt(col++, this.mDetails.getBudgetCycleId());
/* 341 */       stmt.setInt(col++, this.mDetails.getDepth());
/*     */ 
/* 344 */       resultSet = stmt.executeQuery();
/*     */ 
/* 346 */       if (!resultSet.next()) {
/* 347 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 350 */       col = 1;
/* 351 */       int dbVersionNumber = resultSet.getInt(col++);
/* 352 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/* 353 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 359 */       throw handleSQLException(getPK(), "select VERSION_NUM from LEVEL_DATE where BUDGET_CYCLE_ID = ?,DEPTH = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 363 */       closeStatement(stmt);
/* 364 */       closeResultSet(resultSet);
/*     */ 
/* 366 */       if (timer != null)
/* 367 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 386 */     if (items == null) {
/* 387 */       return false;
/*     */     }
/* 389 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 390 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 392 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 397 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 398 */       while (iter2.hasNext())
/*     */       {
/* 400 */         this.mDetails = ((LevelDateEVO)iter2.next());
/*     */ 
/* 403 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 405 */         somethingChanged = true;
/*     */ 
/* 408 */         if (deleteStmt == null) {
/* 409 */           deleteStmt = getConnection().prepareStatement("delete from LEVEL_DATE where    BUDGET_CYCLE_ID = ? AND DEPTH = ? ");
/*     */         }
/*     */ 
/* 412 */         int col = 1;
/* 413 */         deleteStmt.setInt(col++, this.mDetails.getBudgetCycleId());
/* 414 */         deleteStmt.setInt(col++, this.mDetails.getDepth());
/*     */ 
/* 416 */         if (this._log.isDebugEnabled()) {
/* 417 */           this._log.debug("update", "LevelDate deleting BudgetCycleId=" + this.mDetails.getBudgetCycleId() + ",Depth=" + this.mDetails.getDepth());
/*     */         }
/*     */ 
/* 423 */         deleteStmt.addBatch();
/*     */ 
/* 426 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 431 */       if (deleteStmt != null)
/*     */       {
/* 433 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 435 */         deleteStmt.executeBatch();
/*     */ 
/* 437 */         if (timer2 != null) {
/* 438 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 442 */       Iterator iter1 = items.values().iterator();
/* 443 */       while (iter1.hasNext())
/*     */       {
/* 445 */         this.mDetails = ((LevelDateEVO)iter1.next());
/*     */ 
/* 447 */         if (this.mDetails.insertPending())
/*     */         {
/* 449 */           somethingChanged = true;
/* 450 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 453 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 455 */         somethingChanged = true;
/* 456 */         doStore();
/*     */       }
/*     */ 
/* 467 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 471 */       throw handleSQLException("delete from LEVEL_DATE where    BUDGET_CYCLE_ID = ? AND DEPTH = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 475 */       if (deleteStmt != null)
/*     */       {
/* 477 */         closeStatement(deleteStmt);
/* 478 */         closeConnection();
/*     */       }
/*     */ 
/* 481 */       this.mDetails = null;
/*     */ 
/* 483 */       if ((somethingChanged) && 
/* 484 */         (timer != null))
/* 485 */         timer.logDebug("update", "collection"); 
/* 485 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 509 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 511 */     PreparedStatement stmt = null;
/* 512 */     ResultSet resultSet = null;
/*     */ 
/* 514 */     int itemCount = 0;
/*     */ 
/* 516 */     BudgetCycleEVO owningEVO = null;
/* 517 */     Iterator ownersIter = owners.iterator();
/* 518 */     while (ownersIter.hasNext())
/*     */     {
/* 520 */       owningEVO = (BudgetCycleEVO)ownersIter.next();
/* 521 */       owningEVO.setBudgetCycleLevlDatesAllItemsLoaded(true);
/*     */     }
/* 523 */     ownersIter = owners.iterator();
/* 524 */     owningEVO = (BudgetCycleEVO)ownersIter.next();
/* 525 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 529 */       stmt = getConnection().prepareStatement("select LEVEL_DATE.BUDGET_CYCLE_ID,LEVEL_DATE.DEPTH,LEVEL_DATE.PLANNED_END_DATE,LEVEL_DATE.VERSION_NUM,LEVEL_DATE.UPDATED_BY_USER_ID,LEVEL_DATE.UPDATED_TIME,LEVEL_DATE.CREATED_TIME from LEVEL_DATE,BUDGET_CYCLE where 1=1 and LEVEL_DATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.MODEL_ID = ? order by  LEVEL_DATE.BUDGET_CYCLE_ID ,LEVEL_DATE.BUDGET_CYCLE_ID ,LEVEL_DATE.DEPTH");
/*     */ 
/* 531 */       int col = 1;
/* 532 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 534 */       resultSet = stmt.executeQuery();
/*     */ 
/* 537 */       while (resultSet.next())
/*     */       {
/* 539 */         itemCount++;
/* 540 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 545 */         while (this.mDetails.getBudgetCycleId() != owningEVO.getBudgetCycleId())
/*     */         {
/* 549 */           if (!ownersIter.hasNext())
/*     */           {
/* 551 */             this._log.debug("bulkGetAll", "can't find owning [BudgetCycleId=" + this.mDetails.getBudgetCycleId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 555 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 556 */             ownersIter = owners.iterator();
/* 557 */             while (ownersIter.hasNext())
/*     */             {
/* 559 */               owningEVO = (BudgetCycleEVO)ownersIter.next();
/* 560 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 562 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 564 */           owningEVO = (BudgetCycleEVO)ownersIter.next();
/*     */         }
/* 566 */         if (owningEVO.getBudgetCycleLevlDates() == null)
/*     */         {
/* 568 */           theseItems = new ArrayList();
/* 569 */           owningEVO.setBudgetCycleLevlDates(theseItems);
/* 570 */           owningEVO.setBudgetCycleLevlDatesAllItemsLoaded(true);
/*     */         }
/* 572 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 575 */       if (timer != null) {
/* 576 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 581 */       throw handleSQLException("select LEVEL_DATE.BUDGET_CYCLE_ID,LEVEL_DATE.DEPTH,LEVEL_DATE.PLANNED_END_DATE,LEVEL_DATE.VERSION_NUM,LEVEL_DATE.UPDATED_BY_USER_ID,LEVEL_DATE.UPDATED_TIME,LEVEL_DATE.CREATED_TIME from LEVEL_DATE,BUDGET_CYCLE where 1=1 and LEVEL_DATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.MODEL_ID = ? order by  LEVEL_DATE.BUDGET_CYCLE_ID ,LEVEL_DATE.BUDGET_CYCLE_ID ,LEVEL_DATE.DEPTH", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 585 */       closeResultSet(resultSet);
/* 586 */       closeStatement(stmt);
/* 587 */       closeConnection();
/*     */ 
/* 589 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectBudgetCycleId, String dependants, Collection currentList)
/*     */   {
/* 614 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 615 */     PreparedStatement stmt = null;
/* 616 */     ResultSet resultSet = null;
/*     */ 
/* 618 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 622 */       stmt = getConnection().prepareStatement("select LEVEL_DATE.BUDGET_CYCLE_ID,LEVEL_DATE.DEPTH,LEVEL_DATE.PLANNED_END_DATE,LEVEL_DATE.VERSION_NUM,LEVEL_DATE.UPDATED_BY_USER_ID,LEVEL_DATE.UPDATED_TIME,LEVEL_DATE.CREATED_TIME from LEVEL_DATE where    BUDGET_CYCLE_ID = ? order by PLANNED_END_DATE ");
/*     */ 
/* 624 */       int col = 1;
/* 625 */       stmt.setInt(col++, selectBudgetCycleId);
/*     */ 
/* 627 */       resultSet = stmt.executeQuery();
/*     */ 
/* 629 */       while (resultSet.next())
/*     */       {
/* 631 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 634 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 637 */       if (currentList != null)
/*     */       {
/* 640 */         ListIterator iter = items.listIterator();
/* 641 */         LevelDateEVO currentEVO = null;
/* 642 */         LevelDateEVO newEVO = null;
/* 643 */         while (iter.hasNext())
/*     */         {
/* 645 */           newEVO = (LevelDateEVO)iter.next();
/* 646 */           Iterator iter2 = currentList.iterator();
/* 647 */           while (iter2.hasNext())
/*     */           {
/* 649 */             currentEVO = (LevelDateEVO)iter2.next();
/* 650 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 652 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 658 */         Iterator iter2 = currentList.iterator();
/* 659 */         while (iter2.hasNext())
/*     */         {
/* 661 */           currentEVO = (LevelDateEVO)iter2.next();
/* 662 */           if (currentEVO.insertPending()) {
/* 663 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 667 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 671 */       throw handleSQLException("select LEVEL_DATE.BUDGET_CYCLE_ID,LEVEL_DATE.DEPTH,LEVEL_DATE.PLANNED_END_DATE,LEVEL_DATE.VERSION_NUM,LEVEL_DATE.UPDATED_BY_USER_ID,LEVEL_DATE.UPDATED_TIME,LEVEL_DATE.CREATED_TIME from LEVEL_DATE where    BUDGET_CYCLE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 675 */       closeResultSet(resultSet);
/* 676 */       closeStatement(stmt);
/* 677 */       closeConnection();
/*     */ 
/* 679 */       if (timer != null) {
/* 680 */         timer.logDebug("getAll", " BudgetCycleId=" + selectBudgetCycleId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 685 */     return items;
/*     */   }
/*     */ 
/*     */   public LevelDateEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 699 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 702 */     if (this.mDetails == null) {
/* 703 */       doLoad(((LevelDateCK)paramCK).getLevelDatePK());
/*     */     }
/* 705 */     else if (!this.mDetails.getPK().equals(((LevelDateCK)paramCK).getLevelDatePK())) {
/* 706 */       doLoad(((LevelDateCK)paramCK).getLevelDatePK());
/*     */     }
/*     */ 
/* 709 */     LevelDateEVO details = new LevelDateEVO();
/* 710 */     details = this.mDetails.deepClone();
/*     */ 
/* 712 */     if (timer != null) {
/* 713 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 715 */     return details;
/*     */   }
/*     */ 
/*     */   public LevelDateEVO getDetails(ModelCK paramCK, LevelDateEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 721 */     LevelDateEVO savedEVO = this.mDetails;
/* 722 */     this.mDetails = paramEVO;
/* 723 */     LevelDateEVO newEVO = getDetails(paramCK, dependants);
/* 724 */     this.mDetails = savedEVO;
/* 725 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public LevelDateEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 731 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 735 */     LevelDateEVO details = this.mDetails.deepClone();
/*     */ 
/* 737 */     if (timer != null) {
/* 738 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 740 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 745 */     return "LevelDate";
/*     */   }
/*     */ 
/*     */   public LevelDateRefImpl getRef(LevelDatePK paramLevelDatePK)
/*     */   {
/* 750 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 751 */     PreparedStatement stmt = null;
/* 752 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 755 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,BUDGET_CYCLE.BUDGET_CYCLE_ID from LEVEL_DATE,MODEL,BUDGET_CYCLE where 1=1 and LEVEL_DATE.BUDGET_CYCLE_ID = ? and LEVEL_DATE.DEPTH = ? and LEVEL_DATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.BUDGET_CYCLE_ID = MODEL.BUDGET_CYCLE_ID");
/* 756 */       int col = 1;
/* 757 */       stmt.setInt(col++, paramLevelDatePK.getBudgetCycleId());
/* 758 */       stmt.setInt(col++, paramLevelDatePK.getDepth());
/*     */ 
/* 760 */       resultSet = stmt.executeQuery();
/*     */ 
/* 762 */       if (!resultSet.next()) {
/* 763 */         throw new RuntimeException(getEntityName() + " getRef " + paramLevelDatePK + " not found");
/*     */       }
/* 765 */       col = 2;
/* 766 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 770 */       BudgetCyclePK newBudgetCyclePK = new BudgetCyclePK(resultSet.getInt(col++));
/*     */ 
/* 774 */       String textLevelDate = "";
/* 775 */       LevelDateCK ckLevelDate = new LevelDateCK(newModelPK, newBudgetCyclePK, paramLevelDatePK);
/*     */ 
/* 781 */       LevelDateRefImpl localLevelDateRefImpl = new LevelDateRefImpl(ckLevelDate, textLevelDate);
/*     */       return localLevelDateRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 786 */       throw handleSQLException(paramLevelDatePK, "select 0,MODEL.MODEL_ID,BUDGET_CYCLE.BUDGET_CYCLE_ID from LEVEL_DATE,MODEL,BUDGET_CYCLE where 1=1 and LEVEL_DATE.BUDGET_CYCLE_ID = ? and LEVEL_DATE.DEPTH = ? and LEVEL_DATE.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.BUDGET_CYCLE_ID = MODEL.BUDGET_CYCLE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 790 */       closeResultSet(resultSet);
/* 791 */       closeStatement(stmt);
/* 792 */       closeConnection();
/*     */ 
/* 794 */       if (timer != null)
/* 795 */         timer.logDebug("getRef", paramLevelDatePK); 
/* 795 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.LevelDateDAO
 * JD-Core Version:    0.6.0
 */