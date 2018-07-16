/*     */ package com.cedar.cp.ejb.impl.dimension;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.dimension.CalendarYearSpecCK;
/*     */ import com.cedar.cp.dto.dimension.CalendarYearSpecPK;
/*     */ import com.cedar.cp.dto.dimension.CalendarYearSpecRefImpl;
/*     */ import com.cedar.cp.dto.dimension.DimensionCK;
/*     */ import com.cedar.cp.dto.dimension.DimensionPK;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class CalendarYearSpecDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select CALENDAR_YEAR_SPEC.CALENDAR_YEAR_SPEC_ID,CALENDAR_YEAR_SPEC.DIMENSION_ID,CALENDAR_YEAR_SPEC.CALENDAR_YEAR,CALENDAR_YEAR_SPEC.YEAR_IND,CALENDAR_YEAR_SPEC.HALF_YEAR_IND,CALENDAR_YEAR_SPEC.QUARTER_IND,CALENDAR_YEAR_SPEC.MONTH_IND,CALENDAR_YEAR_SPEC.WEEK_IND,CALENDAR_YEAR_SPEC.DAY_IND,CALENDAR_YEAR_SPEC.OPENING_BALANCE_IND,CALENDAR_YEAR_SPEC.ADJUSTMENT_IND,CALENDAR_YEAR_SPEC.PERIOD_13_IND,CALENDAR_YEAR_SPEC.PERIOD_14_IND";
/*     */   protected static final String SQL_LOAD = " from CALENDAR_YEAR_SPEC where    CALENDAR_YEAR_SPEC_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into CALENDAR_YEAR_SPEC ( CALENDAR_YEAR_SPEC_ID,DIMENSION_ID,CALENDAR_YEAR,YEAR_IND,HALF_YEAR_IND,QUARTER_IND,MONTH_IND,WEEK_IND,DAY_IND,OPENING_BALANCE_IND,ADJUSTMENT_IND,PERIOD_13_IND,PERIOD_14_IND) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update CALENDAR_YEAR_SPEC set DIMENSION_ID = ?,CALENDAR_YEAR = ?,YEAR_IND = ?,HALF_YEAR_IND = ?,QUARTER_IND = ?,MONTH_IND = ?,WEEK_IND = ?,DAY_IND = ?,OPENING_BALANCE_IND = ?,ADJUSTMENT_IND = ?,PERIOD_13_IND = ?,PERIOD_14_IND = ? where    CALENDAR_YEAR_SPEC_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from CALENDAR_YEAR_SPEC where    CALENDAR_YEAR_SPEC_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from CALENDAR_YEAR_SPEC where 1=1 and CALENDAR_YEAR_SPEC.DIMENSION_ID = ? order by  CALENDAR_YEAR_SPEC.CALENDAR_YEAR_SPEC_ID";
/*     */   protected static final String SQL_GET_ALL = " from CALENDAR_YEAR_SPEC where    DIMENSION_ID = ? ";
/*     */   protected CalendarYearSpecEVO mDetails;
/*     */ 
/*     */   public CalendarYearSpecDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public CalendarYearSpecDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CalendarYearSpecDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected CalendarYearSpecPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(CalendarYearSpecEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private CalendarYearSpecEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  96 */     int col = 1;
/*  97 */     CalendarYearSpecEVO evo = new CalendarYearSpecEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"));
/*     */ 
/* 113 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(CalendarYearSpecEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 118 */     int col = startCol_;
/* 119 */     stmt_.setInt(col++, evo_.getCalendarYearSpecId());
/* 120 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(CalendarYearSpecEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 125 */     int col = startCol_;
/* 126 */     stmt_.setInt(col++, evo_.getDimensionId());
/* 127 */     stmt_.setInt(col++, evo_.getCalendarYear());
/* 128 */     if (evo_.getYearInd())
/* 129 */       stmt_.setString(col++, "Y");
/*     */     else
/* 131 */       stmt_.setString(col++, " ");
/* 132 */     if (evo_.getHalfYearInd())
/* 133 */       stmt_.setString(col++, "Y");
/*     */     else
/* 135 */       stmt_.setString(col++, " ");
/* 136 */     if (evo_.getQuarterInd())
/* 137 */       stmt_.setString(col++, "Y");
/*     */     else
/* 139 */       stmt_.setString(col++, " ");
/* 140 */     if (evo_.getMonthInd())
/* 141 */       stmt_.setString(col++, "Y");
/*     */     else
/* 143 */       stmt_.setString(col++, " ");
/* 144 */     if (evo_.getWeekInd())
/* 145 */       stmt_.setString(col++, "Y");
/*     */     else
/* 147 */       stmt_.setString(col++, " ");
/* 148 */     if (evo_.getDayInd())
/* 149 */       stmt_.setString(col++, "Y");
/*     */     else
/* 151 */       stmt_.setString(col++, " ");
/* 152 */     if (evo_.getOpeningBalanceInd())
/* 153 */       stmt_.setString(col++, "Y");
/*     */     else
/* 155 */       stmt_.setString(col++, " ");
/* 156 */     if (evo_.getAdjustmentInd())
/* 157 */       stmt_.setString(col++, "Y");
/*     */     else
/* 159 */       stmt_.setString(col++, " ");
/* 160 */     if (evo_.getPeriod13Ind())
/* 161 */       stmt_.setString(col++, "Y");
/*     */     else
/* 163 */       stmt_.setString(col++, " ");
/* 164 */     if (evo_.getPeriod14Ind())
/* 165 */       stmt_.setString(col++, "Y");
/*     */     else
/* 167 */       stmt_.setString(col++, " ");
/* 168 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(CalendarYearSpecPK pk)
/*     */     throws ValidationException
/*     */   {
/* 184 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 186 */     PreparedStatement stmt = null;
/* 187 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 191 */       stmt = getConnection().prepareStatement("select CALENDAR_YEAR_SPEC.CALENDAR_YEAR_SPEC_ID,CALENDAR_YEAR_SPEC.DIMENSION_ID,CALENDAR_YEAR_SPEC.CALENDAR_YEAR,CALENDAR_YEAR_SPEC.YEAR_IND,CALENDAR_YEAR_SPEC.HALF_YEAR_IND,CALENDAR_YEAR_SPEC.QUARTER_IND,CALENDAR_YEAR_SPEC.MONTH_IND,CALENDAR_YEAR_SPEC.WEEK_IND,CALENDAR_YEAR_SPEC.DAY_IND,CALENDAR_YEAR_SPEC.OPENING_BALANCE_IND,CALENDAR_YEAR_SPEC.ADJUSTMENT_IND,CALENDAR_YEAR_SPEC.PERIOD_13_IND,CALENDAR_YEAR_SPEC.PERIOD_14_IND from CALENDAR_YEAR_SPEC where    CALENDAR_YEAR_SPEC_ID = ? ");
/*     */ 
/* 194 */       int col = 1;
/* 195 */       stmt.setInt(col++, pk.getCalendarYearSpecId());
/*     */ 
/* 197 */       resultSet = stmt.executeQuery();
/*     */ 
/* 199 */       if (!resultSet.next()) {
/* 200 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 203 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 204 */       if (this.mDetails.isModified())
/* 205 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 209 */       throw handleSQLException(pk, "select CALENDAR_YEAR_SPEC.CALENDAR_YEAR_SPEC_ID,CALENDAR_YEAR_SPEC.DIMENSION_ID,CALENDAR_YEAR_SPEC.CALENDAR_YEAR,CALENDAR_YEAR_SPEC.YEAR_IND,CALENDAR_YEAR_SPEC.HALF_YEAR_IND,CALENDAR_YEAR_SPEC.QUARTER_IND,CALENDAR_YEAR_SPEC.MONTH_IND,CALENDAR_YEAR_SPEC.WEEK_IND,CALENDAR_YEAR_SPEC.DAY_IND,CALENDAR_YEAR_SPEC.OPENING_BALANCE_IND,CALENDAR_YEAR_SPEC.ADJUSTMENT_IND,CALENDAR_YEAR_SPEC.PERIOD_13_IND,CALENDAR_YEAR_SPEC.PERIOD_14_IND from CALENDAR_YEAR_SPEC where    CALENDAR_YEAR_SPEC_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 213 */       closeResultSet(resultSet);
/* 214 */       closeStatement(stmt);
/* 215 */       closeConnection();
/*     */ 
/* 217 */       if (timer != null)
/* 218 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 261 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 262 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 266 */       stmt = getConnection().prepareStatement("insert into CALENDAR_YEAR_SPEC ( CALENDAR_YEAR_SPEC_ID,DIMENSION_ID,CALENDAR_YEAR,YEAR_IND,HALF_YEAR_IND,QUARTER_IND,MONTH_IND,WEEK_IND,DAY_IND,OPENING_BALANCE_IND,ADJUSTMENT_IND,PERIOD_13_IND,PERIOD_14_IND) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 269 */       int col = 1;
/* 270 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 271 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 274 */       int resultCount = stmt.executeUpdate();
/* 275 */       if (resultCount != 1)
/*     */       {
/* 277 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 280 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 284 */       throw handleSQLException(this.mDetails.getPK(), "insert into CALENDAR_YEAR_SPEC ( CALENDAR_YEAR_SPEC_ID,DIMENSION_ID,CALENDAR_YEAR,YEAR_IND,HALF_YEAR_IND,QUARTER_IND,MONTH_IND,WEEK_IND,DAY_IND,OPENING_BALANCE_IND,ADJUSTMENT_IND,PERIOD_13_IND,PERIOD_14_IND) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 288 */       closeStatement(stmt);
/* 289 */       closeConnection();
/*     */ 
/* 291 */       if (timer != null)
/* 292 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 323 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 327 */     PreparedStatement stmt = null;
/*     */ 
/* 329 */     boolean mainChanged = this.mDetails.isModified();
/* 330 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 333 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 335 */         stmt = getConnection().prepareStatement("update CALENDAR_YEAR_SPEC set DIMENSION_ID = ?,CALENDAR_YEAR = ?,YEAR_IND = ?,HALF_YEAR_IND = ?,QUARTER_IND = ?,MONTH_IND = ?,WEEK_IND = ?,DAY_IND = ?,OPENING_BALANCE_IND = ?,ADJUSTMENT_IND = ?,PERIOD_13_IND = ?,PERIOD_14_IND = ? where    CALENDAR_YEAR_SPEC_ID = ? ");
/*     */ 
/* 338 */         int col = 1;
/* 339 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 340 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 343 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 346 */         if (resultCount != 1) {
/* 347 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 350 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 359 */       throw handleSQLException(getPK(), "update CALENDAR_YEAR_SPEC set DIMENSION_ID = ?,CALENDAR_YEAR = ?,YEAR_IND = ?,HALF_YEAR_IND = ?,QUARTER_IND = ?,MONTH_IND = ?,WEEK_IND = ?,DAY_IND = ?,OPENING_BALANCE_IND = ?,ADJUSTMENT_IND = ?,PERIOD_13_IND = ?,PERIOD_14_IND = ? where    CALENDAR_YEAR_SPEC_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 363 */       closeStatement(stmt);
/* 364 */       closeConnection();
/*     */ 
/* 366 */       if ((timer != null) && (
/* 367 */         (mainChanged) || (dependantChanged)))
/* 368 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 387 */     if (items == null) {
/* 388 */       return false;
/*     */     }
/* 390 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 391 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 393 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 398 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 399 */       while (iter2.hasNext())
/*     */       {
/* 401 */         this.mDetails = ((CalendarYearSpecEVO)iter2.next());
/*     */ 
/* 404 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 406 */         somethingChanged = true;
/*     */ 
/* 409 */         if (deleteStmt == null) {
/* 410 */           deleteStmt = getConnection().prepareStatement("delete from CALENDAR_YEAR_SPEC where    CALENDAR_YEAR_SPEC_ID = ? ");
/*     */         }
/*     */ 
/* 413 */         int col = 1;
/* 414 */         deleteStmt.setInt(col++, this.mDetails.getCalendarYearSpecId());
/*     */ 
/* 416 */         if (this._log.isDebugEnabled()) {
/* 417 */           this._log.debug("update", "CalendarYearSpec deleting CalendarYearSpecId=" + this.mDetails.getCalendarYearSpecId());
/*     */         }
/*     */ 
/* 422 */         deleteStmt.addBatch();
/*     */ 
/* 425 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 430 */       if (deleteStmt != null)
/*     */       {
/* 432 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 434 */         deleteStmt.executeBatch();
/*     */ 
/* 436 */         if (timer2 != null) {
/* 437 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 441 */       Iterator iter1 = items.values().iterator();
/* 442 */       while (iter1.hasNext())
/*     */       {
/* 444 */         this.mDetails = ((CalendarYearSpecEVO)iter1.next());
/*     */ 
/* 446 */         if (this.mDetails.insertPending())
/*     */         {
/* 448 */           somethingChanged = true;
/* 449 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 452 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 454 */         somethingChanged = true;
/* 455 */         doStore();
/*     */       }
/*     */ 
/* 466 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 470 */       throw handleSQLException("delete from CALENDAR_YEAR_SPEC where    CALENDAR_YEAR_SPEC_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 474 */       if (deleteStmt != null)
/*     */       {
/* 476 */         closeStatement(deleteStmt);
/* 477 */         closeConnection();
/*     */       }
/*     */ 
/* 480 */       this.mDetails = null;
/*     */ 
/* 482 */       if ((somethingChanged) && 
/* 483 */         (timer != null))
/* 484 */         timer.logDebug("update", "collection"); 
/* 484 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(DimensionPK entityPK, DimensionEVO owningEVO, String dependants)
/*     */   {
/* 503 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 505 */     PreparedStatement stmt = null;
/* 506 */     ResultSet resultSet = null;
/*     */ 
/* 508 */     int itemCount = 0;
/*     */ 
/* 510 */     Collection theseItems = new ArrayList();
/* 511 */     owningEVO.setCalendarYearSpecs(theseItems);
/* 512 */     owningEVO.setCalendarYearSpecsAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 516 */       stmt = getConnection().prepareStatement("select CALENDAR_YEAR_SPEC.CALENDAR_YEAR_SPEC_ID,CALENDAR_YEAR_SPEC.DIMENSION_ID,CALENDAR_YEAR_SPEC.CALENDAR_YEAR,CALENDAR_YEAR_SPEC.YEAR_IND,CALENDAR_YEAR_SPEC.HALF_YEAR_IND,CALENDAR_YEAR_SPEC.QUARTER_IND,CALENDAR_YEAR_SPEC.MONTH_IND,CALENDAR_YEAR_SPEC.WEEK_IND,CALENDAR_YEAR_SPEC.DAY_IND,CALENDAR_YEAR_SPEC.OPENING_BALANCE_IND,CALENDAR_YEAR_SPEC.ADJUSTMENT_IND,CALENDAR_YEAR_SPEC.PERIOD_13_IND,CALENDAR_YEAR_SPEC.PERIOD_14_IND from CALENDAR_YEAR_SPEC where 1=1 and CALENDAR_YEAR_SPEC.DIMENSION_ID = ? order by  CALENDAR_YEAR_SPEC.CALENDAR_YEAR_SPEC_ID");
/*     */ 
/* 518 */       int col = 1;
/* 519 */       stmt.setInt(col++, entityPK.getDimensionId());
/*     */ 
/* 521 */       resultSet = stmt.executeQuery();
/*     */ 
/* 524 */       while (resultSet.next())
/*     */       {
/* 526 */         itemCount++;
/* 527 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 529 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 532 */       if (timer != null) {
/* 533 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 538 */       throw handleSQLException("select CALENDAR_YEAR_SPEC.CALENDAR_YEAR_SPEC_ID,CALENDAR_YEAR_SPEC.DIMENSION_ID,CALENDAR_YEAR_SPEC.CALENDAR_YEAR,CALENDAR_YEAR_SPEC.YEAR_IND,CALENDAR_YEAR_SPEC.HALF_YEAR_IND,CALENDAR_YEAR_SPEC.QUARTER_IND,CALENDAR_YEAR_SPEC.MONTH_IND,CALENDAR_YEAR_SPEC.WEEK_IND,CALENDAR_YEAR_SPEC.DAY_IND,CALENDAR_YEAR_SPEC.OPENING_BALANCE_IND,CALENDAR_YEAR_SPEC.ADJUSTMENT_IND,CALENDAR_YEAR_SPEC.PERIOD_13_IND,CALENDAR_YEAR_SPEC.PERIOD_14_IND from CALENDAR_YEAR_SPEC where 1=1 and CALENDAR_YEAR_SPEC.DIMENSION_ID = ? order by  CALENDAR_YEAR_SPEC.CALENDAR_YEAR_SPEC_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 542 */       closeResultSet(resultSet);
/* 543 */       closeStatement(stmt);
/* 544 */       closeConnection();
/*     */ 
/* 546 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectDimensionId, String dependants, Collection currentList)
/*     */   {
/* 571 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 572 */     PreparedStatement stmt = null;
/* 573 */     ResultSet resultSet = null;
/*     */ 
/* 575 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 579 */       stmt = getConnection().prepareStatement("select CALENDAR_YEAR_SPEC.CALENDAR_YEAR_SPEC_ID,CALENDAR_YEAR_SPEC.DIMENSION_ID,CALENDAR_YEAR_SPEC.CALENDAR_YEAR,CALENDAR_YEAR_SPEC.YEAR_IND,CALENDAR_YEAR_SPEC.HALF_YEAR_IND,CALENDAR_YEAR_SPEC.QUARTER_IND,CALENDAR_YEAR_SPEC.MONTH_IND,CALENDAR_YEAR_SPEC.WEEK_IND,CALENDAR_YEAR_SPEC.DAY_IND,CALENDAR_YEAR_SPEC.OPENING_BALANCE_IND,CALENDAR_YEAR_SPEC.ADJUSTMENT_IND,CALENDAR_YEAR_SPEC.PERIOD_13_IND,CALENDAR_YEAR_SPEC.PERIOD_14_IND from CALENDAR_YEAR_SPEC where    DIMENSION_ID = ? ");
/*     */ 
/* 581 */       int col = 1;
/* 582 */       stmt.setInt(col++, selectDimensionId);
/*     */ 
/* 584 */       resultSet = stmt.executeQuery();
/*     */ 
/* 586 */       while (resultSet.next())
/*     */       {
/* 588 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 591 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 594 */       if (currentList != null)
/*     */       {
/* 597 */         ListIterator iter = items.listIterator();
/* 598 */         CalendarYearSpecEVO currentEVO = null;
/* 599 */         CalendarYearSpecEVO newEVO = null;
/* 600 */         while (iter.hasNext())
/*     */         {
/* 602 */           newEVO = (CalendarYearSpecEVO)iter.next();
/* 603 */           Iterator iter2 = currentList.iterator();
/* 604 */           while (iter2.hasNext())
/*     */           {
/* 606 */             currentEVO = (CalendarYearSpecEVO)iter2.next();
/* 607 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 609 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 615 */         Iterator iter2 = currentList.iterator();
/* 616 */         while (iter2.hasNext())
/*     */         {
/* 618 */           currentEVO = (CalendarYearSpecEVO)iter2.next();
/* 619 */           if (currentEVO.insertPending()) {
/* 620 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 624 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 628 */       throw handleSQLException("select CALENDAR_YEAR_SPEC.CALENDAR_YEAR_SPEC_ID,CALENDAR_YEAR_SPEC.DIMENSION_ID,CALENDAR_YEAR_SPEC.CALENDAR_YEAR,CALENDAR_YEAR_SPEC.YEAR_IND,CALENDAR_YEAR_SPEC.HALF_YEAR_IND,CALENDAR_YEAR_SPEC.QUARTER_IND,CALENDAR_YEAR_SPEC.MONTH_IND,CALENDAR_YEAR_SPEC.WEEK_IND,CALENDAR_YEAR_SPEC.DAY_IND,CALENDAR_YEAR_SPEC.OPENING_BALANCE_IND,CALENDAR_YEAR_SPEC.ADJUSTMENT_IND,CALENDAR_YEAR_SPEC.PERIOD_13_IND,CALENDAR_YEAR_SPEC.PERIOD_14_IND from CALENDAR_YEAR_SPEC where    DIMENSION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 632 */       closeResultSet(resultSet);
/* 633 */       closeStatement(stmt);
/* 634 */       closeConnection();
/*     */ 
/* 636 */       if (timer != null) {
/* 637 */         timer.logDebug("getAll", " DimensionId=" + selectDimensionId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 642 */     return items;
/*     */   }
/*     */ 
/*     */   public CalendarYearSpecEVO getDetails(DimensionCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 656 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 659 */     if (this.mDetails == null) {
/* 660 */       doLoad(((CalendarYearSpecCK)paramCK).getCalendarYearSpecPK());
/*     */     }
/* 662 */     else if (!this.mDetails.getPK().equals(((CalendarYearSpecCK)paramCK).getCalendarYearSpecPK())) {
/* 663 */       doLoad(((CalendarYearSpecCK)paramCK).getCalendarYearSpecPK());
/*     */     }
/*     */ 
/* 666 */     CalendarYearSpecEVO details = new CalendarYearSpecEVO();
/* 667 */     details = this.mDetails.deepClone();
/*     */ 
/* 669 */     if (timer != null) {
/* 670 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 672 */     return details;
/*     */   }
/*     */ 
/*     */   public CalendarYearSpecEVO getDetails(DimensionCK paramCK, CalendarYearSpecEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 678 */     CalendarYearSpecEVO savedEVO = this.mDetails;
/* 679 */     this.mDetails = paramEVO;
/* 680 */     CalendarYearSpecEVO newEVO = getDetails(paramCK, dependants);
/* 681 */     this.mDetails = savedEVO;
/* 682 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public CalendarYearSpecEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 688 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 692 */     CalendarYearSpecEVO details = this.mDetails.deepClone();
/*     */ 
/* 694 */     if (timer != null) {
/* 695 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 697 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 702 */     return "CalendarYearSpec";
/*     */   }
/*     */ 
/*     */   public CalendarYearSpecRefImpl getRef(CalendarYearSpecPK paramCalendarYearSpecPK)
/*     */   {
/* 707 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 708 */     PreparedStatement stmt = null;
/* 709 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 712 */       stmt = getConnection().prepareStatement("select 0,DIMENSION.DIMENSION_ID from CALENDAR_YEAR_SPEC,DIMENSION where 1=1 and CALENDAR_YEAR_SPEC.CALENDAR_YEAR_SPEC_ID = ? and CALENDAR_YEAR_SPEC.DIMENSION_ID = DIMENSION.DIMENSION_ID");
/* 713 */       int col = 1;
/* 714 */       stmt.setInt(col++, paramCalendarYearSpecPK.getCalendarYearSpecId());
/*     */ 
/* 716 */       resultSet = stmt.executeQuery();
/*     */ 
/* 718 */       if (!resultSet.next()) {
/* 719 */         throw new RuntimeException(getEntityName() + " getRef " + paramCalendarYearSpecPK + " not found");
/*     */       }
/* 721 */       col = 2;
/* 722 */       DimensionPK newDimensionPK = new DimensionPK(resultSet.getInt(col++));
/*     */ 
/* 726 */       String textCalendarYearSpec = "";
/* 727 */       CalendarYearSpecCK ckCalendarYearSpec = new CalendarYearSpecCK(newDimensionPK, paramCalendarYearSpecPK);
/*     */ 
/* 732 */       CalendarYearSpecRefImpl localCalendarYearSpecRefImpl = new CalendarYearSpecRefImpl(ckCalendarYearSpec, textCalendarYearSpec);
/*     */       return localCalendarYearSpecRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 737 */       throw handleSQLException(paramCalendarYearSpecPK, "select 0,DIMENSION.DIMENSION_ID from CALENDAR_YEAR_SPEC,DIMENSION where 1=1 and CALENDAR_YEAR_SPEC.CALENDAR_YEAR_SPEC_ID = ? and CALENDAR_YEAR_SPEC.DIMENSION_ID = DIMENSION.DIMENSION_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 741 */       closeResultSet(resultSet);
/* 742 */       closeStatement(stmt);
/* 743 */       closeConnection();
/*     */ 
/* 745 */       if (timer != null)
/* 746 */         timer.logDebug("getRef", paramCalendarYearSpecPK); 
/* 746 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.dimension.CalendarYearSpecDAO
 * JD-Core Version:    0.6.0
 */