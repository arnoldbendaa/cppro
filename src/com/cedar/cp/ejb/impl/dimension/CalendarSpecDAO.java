/*     */ package com.cedar.cp.ejb.impl.dimension;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.dimension.CalendarSpecCK;
/*     */ import com.cedar.cp.dto.dimension.CalendarSpecPK;
/*     */ import com.cedar.cp.dto.dimension.CalendarSpecRefImpl;
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
/*     */ public class CalendarSpecDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select CALENDAR_SPEC.CALENDAR_SPEC_ID,CALENDAR_SPEC.DIMENSION_ID,CALENDAR_SPEC.YEAR_START_MONTH,CALENDAR_SPEC.YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.YEAR_DESCR_FORMAT,CALENDAR_SPEC.HALF_YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.HALF_YEAR_DESCR_FORMAT,CALENDAR_SPEC.QUARTER_VIS_ID_FORMAT,CALENDAR_SPEC.QUARTER_DESCR_FORMAT,CALENDAR_SPEC.MONTH_VIS_ID_FORMAT,CALENDAR_SPEC.MONTH_DESCR_FORMAT,CALENDAR_SPEC.WEEK_VIS_ID_FORMAT,CALENDAR_SPEC.WEEK_DESCR_FORMAT,CALENDAR_SPEC.DAY_VIS_ID_FORMAT,CALENDAR_SPEC.DAY_DESCR_FORMAT,CALENDAR_SPEC.OPEN_VIS_ID_FORMAT,CALENDAR_SPEC.OPEN_DESCR_FORMAT,CALENDAR_SPEC.ADJ_VIS_ID_FORMAT,CALENDAR_SPEC.ADJ_DESCR_FORMAT,CALENDAR_SPEC.P13_VIS_ID_FORMAT,CALENDAR_SPEC.P13_DESCR_FORMAT,CALENDAR_SPEC.P14_VIS_ID_FORMAT,CALENDAR_SPEC.P14_DESCR_FORMAT";
/*     */   protected static final String SQL_LOAD = " from CALENDAR_SPEC where    CALENDAR_SPEC_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into CALENDAR_SPEC ( CALENDAR_SPEC_ID,DIMENSION_ID,YEAR_START_MONTH,YEAR_VIS_ID_FORMAT,YEAR_DESCR_FORMAT,HALF_YEAR_VIS_ID_FORMAT,HALF_YEAR_DESCR_FORMAT,QUARTER_VIS_ID_FORMAT,QUARTER_DESCR_FORMAT,MONTH_VIS_ID_FORMAT,MONTH_DESCR_FORMAT,WEEK_VIS_ID_FORMAT,WEEK_DESCR_FORMAT,DAY_VIS_ID_FORMAT,DAY_DESCR_FORMAT,OPEN_VIS_ID_FORMAT,OPEN_DESCR_FORMAT,ADJ_VIS_ID_FORMAT,ADJ_DESCR_FORMAT,P13_VIS_ID_FORMAT,P13_DESCR_FORMAT,P14_VIS_ID_FORMAT,P14_DESCR_FORMAT) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update CALENDAR_SPEC set DIMENSION_ID = ?,YEAR_START_MONTH = ?,YEAR_VIS_ID_FORMAT = ?,YEAR_DESCR_FORMAT = ?,HALF_YEAR_VIS_ID_FORMAT = ?,HALF_YEAR_DESCR_FORMAT = ?,QUARTER_VIS_ID_FORMAT = ?,QUARTER_DESCR_FORMAT = ?,MONTH_VIS_ID_FORMAT = ?,MONTH_DESCR_FORMAT = ?,WEEK_VIS_ID_FORMAT = ?,WEEK_DESCR_FORMAT = ?,DAY_VIS_ID_FORMAT = ?,DAY_DESCR_FORMAT = ?,OPEN_VIS_ID_FORMAT = ?,OPEN_DESCR_FORMAT = ?,ADJ_VIS_ID_FORMAT = ?,ADJ_DESCR_FORMAT = ?,P13_VIS_ID_FORMAT = ?,P13_DESCR_FORMAT = ?,P14_VIS_ID_FORMAT = ?,P14_DESCR_FORMAT = ? where    CALENDAR_SPEC_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from CALENDAR_SPEC where    CALENDAR_SPEC_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from CALENDAR_SPEC where 1=1 and CALENDAR_SPEC.DIMENSION_ID = ? order by  CALENDAR_SPEC.CALENDAR_SPEC_ID";
/*     */   protected static final String SQL_GET_ALL = " from CALENDAR_SPEC where    DIMENSION_ID = ? ";
/*     */   protected CalendarSpecEVO mDetails;
/*     */ 
/*     */   public CalendarSpecDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public CalendarSpecDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CalendarSpecDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected CalendarSpecPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(CalendarSpecEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private CalendarSpecEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 106 */     int col = 1;
/* 107 */     CalendarSpecEVO evo = new CalendarSpecEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++));
/*     */ 
/* 133 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(CalendarSpecEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 138 */     int col = startCol_;
/* 139 */     stmt_.setInt(col++, evo_.getCalendarSpecId());
/* 140 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(CalendarSpecEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 145 */     int col = startCol_;
/* 146 */     stmt_.setInt(col++, evo_.getDimensionId());
/* 147 */     stmt_.setInt(col++, evo_.getYearStartMonth());
/* 148 */     stmt_.setString(col++, evo_.getYearVisIdFormat());
/* 149 */     stmt_.setString(col++, evo_.getYearDescrFormat());
/* 150 */     stmt_.setString(col++, evo_.getHalfYearVisIdFormat());
/* 151 */     stmt_.setString(col++, evo_.getHalfYearDescrFormat());
/* 152 */     stmt_.setString(col++, evo_.getQuarterVisIdFormat());
/* 153 */     stmt_.setString(col++, evo_.getQuarterDescrFormat());
/* 154 */     stmt_.setString(col++, evo_.getMonthVisIdFormat());
/* 155 */     stmt_.setString(col++, evo_.getMonthDescrFormat());
/* 156 */     stmt_.setString(col++, evo_.getWeekVisIdFormat());
/* 157 */     stmt_.setString(col++, evo_.getWeekDescrFormat());
/* 158 */     stmt_.setString(col++, evo_.getDayVisIdFormat());
/* 159 */     stmt_.setString(col++, evo_.getDayDescrFormat());
/* 160 */     stmt_.setString(col++, evo_.getOpenVisIdFormat());
/* 161 */     stmt_.setString(col++, evo_.getOpenDescrFormat());
/* 162 */     stmt_.setString(col++, evo_.getAdjVisIdFormat());
/* 163 */     stmt_.setString(col++, evo_.getAdjDescrFormat());
/* 164 */     stmt_.setString(col++, evo_.getP13VisIdFormat());
/* 165 */     stmt_.setString(col++, evo_.getP13DescrFormat());
/* 166 */     stmt_.setString(col++, evo_.getP14VisIdFormat());
/* 167 */     stmt_.setString(col++, evo_.getP14DescrFormat());
/* 168 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(CalendarSpecPK pk)
/*     */     throws ValidationException
/*     */   {
/* 184 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 186 */     PreparedStatement stmt = null;
/* 187 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 191 */       stmt = getConnection().prepareStatement("select CALENDAR_SPEC.CALENDAR_SPEC_ID,CALENDAR_SPEC.DIMENSION_ID,CALENDAR_SPEC.YEAR_START_MONTH,CALENDAR_SPEC.YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.YEAR_DESCR_FORMAT,CALENDAR_SPEC.HALF_YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.HALF_YEAR_DESCR_FORMAT,CALENDAR_SPEC.QUARTER_VIS_ID_FORMAT,CALENDAR_SPEC.QUARTER_DESCR_FORMAT,CALENDAR_SPEC.MONTH_VIS_ID_FORMAT,CALENDAR_SPEC.MONTH_DESCR_FORMAT,CALENDAR_SPEC.WEEK_VIS_ID_FORMAT,CALENDAR_SPEC.WEEK_DESCR_FORMAT,CALENDAR_SPEC.DAY_VIS_ID_FORMAT,CALENDAR_SPEC.DAY_DESCR_FORMAT,CALENDAR_SPEC.OPEN_VIS_ID_FORMAT,CALENDAR_SPEC.OPEN_DESCR_FORMAT,CALENDAR_SPEC.ADJ_VIS_ID_FORMAT,CALENDAR_SPEC.ADJ_DESCR_FORMAT,CALENDAR_SPEC.P13_VIS_ID_FORMAT,CALENDAR_SPEC.P13_DESCR_FORMAT,CALENDAR_SPEC.P14_VIS_ID_FORMAT,CALENDAR_SPEC.P14_DESCR_FORMAT from CALENDAR_SPEC where    CALENDAR_SPEC_ID = ? ");
/*     */ 
/* 194 */       int col = 1;
/* 195 */       stmt.setInt(col++, pk.getCalendarSpecId());
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
/* 209 */       throw handleSQLException(pk, "select CALENDAR_SPEC.CALENDAR_SPEC_ID,CALENDAR_SPEC.DIMENSION_ID,CALENDAR_SPEC.YEAR_START_MONTH,CALENDAR_SPEC.YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.YEAR_DESCR_FORMAT,CALENDAR_SPEC.HALF_YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.HALF_YEAR_DESCR_FORMAT,CALENDAR_SPEC.QUARTER_VIS_ID_FORMAT,CALENDAR_SPEC.QUARTER_DESCR_FORMAT,CALENDAR_SPEC.MONTH_VIS_ID_FORMAT,CALENDAR_SPEC.MONTH_DESCR_FORMAT,CALENDAR_SPEC.WEEK_VIS_ID_FORMAT,CALENDAR_SPEC.WEEK_DESCR_FORMAT,CALENDAR_SPEC.DAY_VIS_ID_FORMAT,CALENDAR_SPEC.DAY_DESCR_FORMAT,CALENDAR_SPEC.OPEN_VIS_ID_FORMAT,CALENDAR_SPEC.OPEN_DESCR_FORMAT,CALENDAR_SPEC.ADJ_VIS_ID_FORMAT,CALENDAR_SPEC.ADJ_DESCR_FORMAT,CALENDAR_SPEC.P13_VIS_ID_FORMAT,CALENDAR_SPEC.P13_DESCR_FORMAT,CALENDAR_SPEC.P14_VIS_ID_FORMAT,CALENDAR_SPEC.P14_DESCR_FORMAT from CALENDAR_SPEC where    CALENDAR_SPEC_ID = ? ", sqle);
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
/* 281 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 282 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 286 */       stmt = getConnection().prepareStatement("insert into CALENDAR_SPEC ( CALENDAR_SPEC_ID,DIMENSION_ID,YEAR_START_MONTH,YEAR_VIS_ID_FORMAT,YEAR_DESCR_FORMAT,HALF_YEAR_VIS_ID_FORMAT,HALF_YEAR_DESCR_FORMAT,QUARTER_VIS_ID_FORMAT,QUARTER_DESCR_FORMAT,MONTH_VIS_ID_FORMAT,MONTH_DESCR_FORMAT,WEEK_VIS_ID_FORMAT,WEEK_DESCR_FORMAT,DAY_VIS_ID_FORMAT,DAY_DESCR_FORMAT,OPEN_VIS_ID_FORMAT,OPEN_DESCR_FORMAT,ADJ_VIS_ID_FORMAT,ADJ_DESCR_FORMAT,P13_VIS_ID_FORMAT,P13_DESCR_FORMAT,P14_VIS_ID_FORMAT,P14_DESCR_FORMAT) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 289 */       int col = 1;
/* 290 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 291 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 294 */       int resultCount = stmt.executeUpdate();
/* 295 */       if (resultCount != 1)
/*     */       {
/* 297 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 300 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 304 */       throw handleSQLException(this.mDetails.getPK(), "insert into CALENDAR_SPEC ( CALENDAR_SPEC_ID,DIMENSION_ID,YEAR_START_MONTH,YEAR_VIS_ID_FORMAT,YEAR_DESCR_FORMAT,HALF_YEAR_VIS_ID_FORMAT,HALF_YEAR_DESCR_FORMAT,QUARTER_VIS_ID_FORMAT,QUARTER_DESCR_FORMAT,MONTH_VIS_ID_FORMAT,MONTH_DESCR_FORMAT,WEEK_VIS_ID_FORMAT,WEEK_DESCR_FORMAT,DAY_VIS_ID_FORMAT,DAY_DESCR_FORMAT,OPEN_VIS_ID_FORMAT,OPEN_DESCR_FORMAT,ADJ_VIS_ID_FORMAT,ADJ_DESCR_FORMAT,P13_VIS_ID_FORMAT,P13_DESCR_FORMAT,P14_VIS_ID_FORMAT,P14_DESCR_FORMAT) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 308 */       closeStatement(stmt);
/* 309 */       closeConnection();
/*     */ 
/* 311 */       if (timer != null)
/* 312 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 353 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 357 */     PreparedStatement stmt = null;
/*     */ 
/* 359 */     boolean mainChanged = this.mDetails.isModified();
/* 360 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 363 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 365 */         stmt = getConnection().prepareStatement("update CALENDAR_SPEC set DIMENSION_ID = ?,YEAR_START_MONTH = ?,YEAR_VIS_ID_FORMAT = ?,YEAR_DESCR_FORMAT = ?,HALF_YEAR_VIS_ID_FORMAT = ?,HALF_YEAR_DESCR_FORMAT = ?,QUARTER_VIS_ID_FORMAT = ?,QUARTER_DESCR_FORMAT = ?,MONTH_VIS_ID_FORMAT = ?,MONTH_DESCR_FORMAT = ?,WEEK_VIS_ID_FORMAT = ?,WEEK_DESCR_FORMAT = ?,DAY_VIS_ID_FORMAT = ?,DAY_DESCR_FORMAT = ?,OPEN_VIS_ID_FORMAT = ?,OPEN_DESCR_FORMAT = ?,ADJ_VIS_ID_FORMAT = ?,ADJ_DESCR_FORMAT = ?,P13_VIS_ID_FORMAT = ?,P13_DESCR_FORMAT = ?,P14_VIS_ID_FORMAT = ?,P14_DESCR_FORMAT = ? where    CALENDAR_SPEC_ID = ? ");
/*     */ 
/* 368 */         int col = 1;
/* 369 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 370 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 373 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 376 */         if (resultCount != 1) {
/* 377 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 380 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 389 */       throw handleSQLException(getPK(), "update CALENDAR_SPEC set DIMENSION_ID = ?,YEAR_START_MONTH = ?,YEAR_VIS_ID_FORMAT = ?,YEAR_DESCR_FORMAT = ?,HALF_YEAR_VIS_ID_FORMAT = ?,HALF_YEAR_DESCR_FORMAT = ?,QUARTER_VIS_ID_FORMAT = ?,QUARTER_DESCR_FORMAT = ?,MONTH_VIS_ID_FORMAT = ?,MONTH_DESCR_FORMAT = ?,WEEK_VIS_ID_FORMAT = ?,WEEK_DESCR_FORMAT = ?,DAY_VIS_ID_FORMAT = ?,DAY_DESCR_FORMAT = ?,OPEN_VIS_ID_FORMAT = ?,OPEN_DESCR_FORMAT = ?,ADJ_VIS_ID_FORMAT = ?,ADJ_DESCR_FORMAT = ?,P13_VIS_ID_FORMAT = ?,P13_DESCR_FORMAT = ?,P14_VIS_ID_FORMAT = ?,P14_DESCR_FORMAT = ? where    CALENDAR_SPEC_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 393 */       closeStatement(stmt);
/* 394 */       closeConnection();
/*     */ 
/* 396 */       if ((timer != null) && (
/* 397 */         (mainChanged) || (dependantChanged)))
/* 398 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 417 */     if (items == null) {
/* 418 */       return false;
/*     */     }
/* 420 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 421 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 423 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 428 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 429 */       while (iter2.hasNext())
/*     */       {
/* 431 */         this.mDetails = ((CalendarSpecEVO)iter2.next());
/*     */ 
/* 434 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 436 */         somethingChanged = true;
/*     */ 
/* 439 */         if (deleteStmt == null) {
/* 440 */           deleteStmt = getConnection().prepareStatement("delete from CALENDAR_SPEC where    CALENDAR_SPEC_ID = ? ");
/*     */         }
/*     */ 
/* 443 */         int col = 1;
/* 444 */         deleteStmt.setInt(col++, this.mDetails.getCalendarSpecId());
/*     */ 
/* 446 */         if (this._log.isDebugEnabled()) {
/* 447 */           this._log.debug("update", "CalendarSpec deleting CalendarSpecId=" + this.mDetails.getCalendarSpecId());
/*     */         }
/*     */ 
/* 452 */         deleteStmt.addBatch();
/*     */ 
/* 455 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 460 */       if (deleteStmt != null)
/*     */       {
/* 462 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 464 */         deleteStmt.executeBatch();
/*     */ 
/* 466 */         if (timer2 != null) {
/* 467 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 471 */       Iterator iter1 = items.values().iterator();
/* 472 */       while (iter1.hasNext())
/*     */       {
/* 474 */         this.mDetails = ((CalendarSpecEVO)iter1.next());
/*     */ 
/* 476 */         if (this.mDetails.insertPending())
/*     */         {
/* 478 */           somethingChanged = true;
/* 479 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 482 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 484 */         somethingChanged = true;
/* 485 */         doStore();
/*     */       }
/*     */ 
/* 496 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 500 */       throw handleSQLException("delete from CALENDAR_SPEC where    CALENDAR_SPEC_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 504 */       if (deleteStmt != null)
/*     */       {
/* 506 */         closeStatement(deleteStmt);
/* 507 */         closeConnection();
/*     */       }
/*     */ 
/* 510 */       this.mDetails = null;
/*     */ 
/* 512 */       if ((somethingChanged) && 
/* 513 */         (timer != null))
/* 514 */         timer.logDebug("update", "collection"); 
/* 514 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(DimensionPK entityPK, DimensionEVO owningEVO, String dependants)
/*     */   {
/* 533 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 535 */     PreparedStatement stmt = null;
/* 536 */     ResultSet resultSet = null;
/*     */ 
/* 538 */     int itemCount = 0;
/*     */ 
/* 540 */     Collection theseItems = new ArrayList();
/* 541 */     owningEVO.setCalendarSpec(theseItems);
/* 542 */     owningEVO.setCalendarSpecAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 546 */       stmt = getConnection().prepareStatement("select CALENDAR_SPEC.CALENDAR_SPEC_ID,CALENDAR_SPEC.DIMENSION_ID,CALENDAR_SPEC.YEAR_START_MONTH,CALENDAR_SPEC.YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.YEAR_DESCR_FORMAT,CALENDAR_SPEC.HALF_YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.HALF_YEAR_DESCR_FORMAT,CALENDAR_SPEC.QUARTER_VIS_ID_FORMAT,CALENDAR_SPEC.QUARTER_DESCR_FORMAT,CALENDAR_SPEC.MONTH_VIS_ID_FORMAT,CALENDAR_SPEC.MONTH_DESCR_FORMAT,CALENDAR_SPEC.WEEK_VIS_ID_FORMAT,CALENDAR_SPEC.WEEK_DESCR_FORMAT,CALENDAR_SPEC.DAY_VIS_ID_FORMAT,CALENDAR_SPEC.DAY_DESCR_FORMAT,CALENDAR_SPEC.OPEN_VIS_ID_FORMAT,CALENDAR_SPEC.OPEN_DESCR_FORMAT,CALENDAR_SPEC.ADJ_VIS_ID_FORMAT,CALENDAR_SPEC.ADJ_DESCR_FORMAT,CALENDAR_SPEC.P13_VIS_ID_FORMAT,CALENDAR_SPEC.P13_DESCR_FORMAT,CALENDAR_SPEC.P14_VIS_ID_FORMAT,CALENDAR_SPEC.P14_DESCR_FORMAT from CALENDAR_SPEC where 1=1 and CALENDAR_SPEC.DIMENSION_ID = ? order by  CALENDAR_SPEC.CALENDAR_SPEC_ID");
/*     */ 
/* 548 */       int col = 1;
/* 549 */       stmt.setInt(col++, entityPK.getDimensionId());
/*     */ 
/* 551 */       resultSet = stmt.executeQuery();
/*     */ 
/* 554 */       while (resultSet.next())
/*     */       {
/* 556 */         itemCount++;
/* 557 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 559 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 562 */       if (timer != null) {
/* 563 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 568 */       throw handleSQLException("select CALENDAR_SPEC.CALENDAR_SPEC_ID,CALENDAR_SPEC.DIMENSION_ID,CALENDAR_SPEC.YEAR_START_MONTH,CALENDAR_SPEC.YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.YEAR_DESCR_FORMAT,CALENDAR_SPEC.HALF_YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.HALF_YEAR_DESCR_FORMAT,CALENDAR_SPEC.QUARTER_VIS_ID_FORMAT,CALENDAR_SPEC.QUARTER_DESCR_FORMAT,CALENDAR_SPEC.MONTH_VIS_ID_FORMAT,CALENDAR_SPEC.MONTH_DESCR_FORMAT,CALENDAR_SPEC.WEEK_VIS_ID_FORMAT,CALENDAR_SPEC.WEEK_DESCR_FORMAT,CALENDAR_SPEC.DAY_VIS_ID_FORMAT,CALENDAR_SPEC.DAY_DESCR_FORMAT,CALENDAR_SPEC.OPEN_VIS_ID_FORMAT,CALENDAR_SPEC.OPEN_DESCR_FORMAT,CALENDAR_SPEC.ADJ_VIS_ID_FORMAT,CALENDAR_SPEC.ADJ_DESCR_FORMAT,CALENDAR_SPEC.P13_VIS_ID_FORMAT,CALENDAR_SPEC.P13_DESCR_FORMAT,CALENDAR_SPEC.P14_VIS_ID_FORMAT,CALENDAR_SPEC.P14_DESCR_FORMAT from CALENDAR_SPEC where 1=1 and CALENDAR_SPEC.DIMENSION_ID = ? order by  CALENDAR_SPEC.CALENDAR_SPEC_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 572 */       closeResultSet(resultSet);
/* 573 */       closeStatement(stmt);
/* 574 */       closeConnection();
/*     */ 
/* 576 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectDimensionId, String dependants, Collection currentList)
/*     */   {
/* 601 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 602 */     PreparedStatement stmt = null;
/* 603 */     ResultSet resultSet = null;
/*     */ 
/* 605 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 609 */       stmt = getConnection().prepareStatement("select CALENDAR_SPEC.CALENDAR_SPEC_ID,CALENDAR_SPEC.DIMENSION_ID,CALENDAR_SPEC.YEAR_START_MONTH,CALENDAR_SPEC.YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.YEAR_DESCR_FORMAT,CALENDAR_SPEC.HALF_YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.HALF_YEAR_DESCR_FORMAT,CALENDAR_SPEC.QUARTER_VIS_ID_FORMAT,CALENDAR_SPEC.QUARTER_DESCR_FORMAT,CALENDAR_SPEC.MONTH_VIS_ID_FORMAT,CALENDAR_SPEC.MONTH_DESCR_FORMAT,CALENDAR_SPEC.WEEK_VIS_ID_FORMAT,CALENDAR_SPEC.WEEK_DESCR_FORMAT,CALENDAR_SPEC.DAY_VIS_ID_FORMAT,CALENDAR_SPEC.DAY_DESCR_FORMAT,CALENDAR_SPEC.OPEN_VIS_ID_FORMAT,CALENDAR_SPEC.OPEN_DESCR_FORMAT,CALENDAR_SPEC.ADJ_VIS_ID_FORMAT,CALENDAR_SPEC.ADJ_DESCR_FORMAT,CALENDAR_SPEC.P13_VIS_ID_FORMAT,CALENDAR_SPEC.P13_DESCR_FORMAT,CALENDAR_SPEC.P14_VIS_ID_FORMAT,CALENDAR_SPEC.P14_DESCR_FORMAT from CALENDAR_SPEC where    DIMENSION_ID = ? ");
/*     */ 
/* 611 */       int col = 1;
/* 612 */       stmt.setInt(col++, selectDimensionId);
/*     */ 
/* 614 */       resultSet = stmt.executeQuery();
/*     */ 
/* 616 */       while (resultSet.next())
/*     */       {
/* 618 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 621 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 624 */       if (currentList != null)
/*     */       {
/* 627 */         ListIterator iter = items.listIterator();
/* 628 */         CalendarSpecEVO currentEVO = null;
/* 629 */         CalendarSpecEVO newEVO = null;
/* 630 */         while (iter.hasNext())
/*     */         {
/* 632 */           newEVO = (CalendarSpecEVO)iter.next();
/* 633 */           Iterator iter2 = currentList.iterator();
/* 634 */           while (iter2.hasNext())
/*     */           {
/* 636 */             currentEVO = (CalendarSpecEVO)iter2.next();
/* 637 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 639 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 645 */         Iterator iter2 = currentList.iterator();
/* 646 */         while (iter2.hasNext())
/*     */         {
/* 648 */           currentEVO = (CalendarSpecEVO)iter2.next();
/* 649 */           if (currentEVO.insertPending()) {
/* 650 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 654 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 658 */       throw handleSQLException("select CALENDAR_SPEC.CALENDAR_SPEC_ID,CALENDAR_SPEC.DIMENSION_ID,CALENDAR_SPEC.YEAR_START_MONTH,CALENDAR_SPEC.YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.YEAR_DESCR_FORMAT,CALENDAR_SPEC.HALF_YEAR_VIS_ID_FORMAT,CALENDAR_SPEC.HALF_YEAR_DESCR_FORMAT,CALENDAR_SPEC.QUARTER_VIS_ID_FORMAT,CALENDAR_SPEC.QUARTER_DESCR_FORMAT,CALENDAR_SPEC.MONTH_VIS_ID_FORMAT,CALENDAR_SPEC.MONTH_DESCR_FORMAT,CALENDAR_SPEC.WEEK_VIS_ID_FORMAT,CALENDAR_SPEC.WEEK_DESCR_FORMAT,CALENDAR_SPEC.DAY_VIS_ID_FORMAT,CALENDAR_SPEC.DAY_DESCR_FORMAT,CALENDAR_SPEC.OPEN_VIS_ID_FORMAT,CALENDAR_SPEC.OPEN_DESCR_FORMAT,CALENDAR_SPEC.ADJ_VIS_ID_FORMAT,CALENDAR_SPEC.ADJ_DESCR_FORMAT,CALENDAR_SPEC.P13_VIS_ID_FORMAT,CALENDAR_SPEC.P13_DESCR_FORMAT,CALENDAR_SPEC.P14_VIS_ID_FORMAT,CALENDAR_SPEC.P14_DESCR_FORMAT from CALENDAR_SPEC where    DIMENSION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 662 */       closeResultSet(resultSet);
/* 663 */       closeStatement(stmt);
/* 664 */       closeConnection();
/*     */ 
/* 666 */       if (timer != null) {
/* 667 */         timer.logDebug("getAll", " DimensionId=" + selectDimensionId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 672 */     return items;
/*     */   }
/*     */ 
/*     */   public CalendarSpecEVO getDetails(DimensionCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 686 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 689 */     if (this.mDetails == null) {
/* 690 */       doLoad(((CalendarSpecCK)paramCK).getCalendarSpecPK());
/*     */     }
/* 692 */     else if (!this.mDetails.getPK().equals(((CalendarSpecCK)paramCK).getCalendarSpecPK())) {
/* 693 */       doLoad(((CalendarSpecCK)paramCK).getCalendarSpecPK());
/*     */     }
/*     */ 
/* 696 */     CalendarSpecEVO details = new CalendarSpecEVO();
/* 697 */     details = this.mDetails.deepClone();
/*     */ 
/* 699 */     if (timer != null) {
/* 700 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 702 */     return details;
/*     */   }
/*     */ 
/*     */   public CalendarSpecEVO getDetails(DimensionCK paramCK, CalendarSpecEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 708 */     CalendarSpecEVO savedEVO = this.mDetails;
/* 709 */     this.mDetails = paramEVO;
/* 710 */     CalendarSpecEVO newEVO = getDetails(paramCK, dependants);
/* 711 */     this.mDetails = savedEVO;
/* 712 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public CalendarSpecEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 718 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 722 */     CalendarSpecEVO details = this.mDetails.deepClone();
/*     */ 
/* 724 */     if (timer != null) {
/* 725 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 727 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 732 */     return "CalendarSpec";
/*     */   }
/*     */ 
/*     */   public CalendarSpecRefImpl getRef(CalendarSpecPK paramCalendarSpecPK)
/*     */   {
/* 737 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 738 */     PreparedStatement stmt = null;
/* 739 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 742 */       stmt = getConnection().prepareStatement("select 0,DIMENSION.DIMENSION_ID from CALENDAR_SPEC,DIMENSION where 1=1 and CALENDAR_SPEC.CALENDAR_SPEC_ID = ? and CALENDAR_SPEC.DIMENSION_ID = DIMENSION.DIMENSION_ID");
/* 743 */       int col = 1;
/* 744 */       stmt.setInt(col++, paramCalendarSpecPK.getCalendarSpecId());
/*     */ 
/* 746 */       resultSet = stmt.executeQuery();
/*     */ 
/* 748 */       if (!resultSet.next()) {
/* 749 */         throw new RuntimeException(getEntityName() + " getRef " + paramCalendarSpecPK + " not found");
/*     */       }
/* 751 */       col = 2;
/* 752 */       DimensionPK newDimensionPK = new DimensionPK(resultSet.getInt(col++));
/*     */ 
/* 756 */       String textCalendarSpec = "";
/* 757 */       CalendarSpecCK ckCalendarSpec = new CalendarSpecCK(newDimensionPK, paramCalendarSpecPK);
/*     */ 
/* 762 */       CalendarSpecRefImpl localCalendarSpecRefImpl = new CalendarSpecRefImpl(ckCalendarSpec, textCalendarSpec);
/*     */       return localCalendarSpecRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 767 */       throw handleSQLException(paramCalendarSpecPK, "select 0,DIMENSION.DIMENSION_ID from CALENDAR_SPEC,DIMENSION where 1=1 and CALENDAR_SPEC.CALENDAR_SPEC_ID = ? and CALENDAR_SPEC.DIMENSION_ID = DIMENSION.DIMENSION_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 771 */       closeResultSet(resultSet);
/* 772 */       closeStatement(stmt);
/* 773 */       closeConnection();
/*     */ 
/* 775 */       if (timer != null)
/* 776 */         timer.logDebug("getRef", paramCalendarSpecPK); 
/* 776 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.dimension.CalendarSpecDAO
 * JD-Core Version:    0.6.0
 */