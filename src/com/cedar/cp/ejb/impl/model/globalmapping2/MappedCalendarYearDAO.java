/*     */ package com.cedar.cp.ejb.impl.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.globalmapping2.MappedCalendarElementCK;
/*     */ import com.cedar.cp.dto.model.globalmapping2.MappedCalendarElementPK;
/*     */ import com.cedar.cp.dto.model.globalmapping2.MappedCalendarYearCK;
/*     */ import com.cedar.cp.dto.model.globalmapping2.MappedCalendarYearPK;
/*     */ import com.cedar.cp.dto.model.globalmapping2.MappedCalendarYearRefImpl;
/*     */ import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2CK;
/*     */ import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
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
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class MappedCalendarYearDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID,MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID,MAPPED_CALENDAR_YEAR.YEAR,MAPPED_CALENDAR_YEAR.YEAR_VIS_ID,MAPPED_CALENDAR_YEAR.UPDATED_BY_USER_ID,MAPPED_CALENDAR_YEAR.UPDATED_TIME,MAPPED_CALENDAR_YEAR.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from MAPPED_CALENDAR_YEAR where    MAPPED_CALENDAR_YEAR_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into MAPPED_CALENDAR_YEAR ( MAPPED_CALENDAR_YEAR_ID,MAPPED_MODEL_ID,YEAR,YEAR_VIS_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update MAPPED_CALENDAR_YEAR set MAPPED_MODEL_ID = ?,YEAR = ?,YEAR_VIS_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_CALENDAR_YEAR_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from MAPPED_CALENDAR_YEAR where    MAPPED_CALENDAR_YEAR_ID = ? ";
/* 470 */   private static String[][] SQL_DELETE_CHILDREN = { { "MAPPED_CALENDAR_ELEMENT", "delete from MAPPED_CALENDAR_ELEMENT where     MAPPED_CALENDAR_ELEMENT.MAPPED_CALENDAR_YEAR_ID = ? " } };
/*     */ 
/* 479 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*     */ 
/* 483 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID = ?)";
/*     */   public static final String SQL_BULK_GET_ALL = " from MAPPED_CALENDAR_YEAR where 1=1 and MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID = ? order by  MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID";
/*     */   protected static final String SQL_GET_ALL = " from MAPPED_CALENDAR_YEAR where    MAPPED_MODEL_ID = ? ";
/*     */   protected MappedCalendarElementDAO mMappedCalendarElementDAO;
/*     */   protected MappedCalendarYearEVO mDetails;
/*     */ 
/*     */   public MappedCalendarYearDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public MappedCalendarYearDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public MappedCalendarYearDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected MappedCalendarYearPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(MappedCalendarYearEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private MappedCalendarYearEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  90 */     int col = 1;
/*  91 */     MappedCalendarYearEVO evo = new MappedCalendarYearEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), null);
/*     */ 
/*  99 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 100 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 101 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 102 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(MappedCalendarYearEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 107 */     int col = startCol_;
/* 108 */     stmt_.setInt(col++, evo_.getMappedCalendarYearId());
/* 109 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(MappedCalendarYearEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 114 */     int col = startCol_;
/* 115 */     stmt_.setInt(col++, evo_.getMappedModelId());
/* 116 */     stmt_.setInt(col++, evo_.getYear());
/* 117 */     stmt_.setString(col++, evo_.getYearVisId());
			  stmt_.setString(col++, evo_.getCompanies());
/* 118 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 119 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 120 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 121 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(MappedCalendarYearPK pk)
/*     */     throws ValidationException
/*     */   {
/* 137 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 139 */     PreparedStatement stmt = null;
/* 140 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 144 */       stmt = getConnection().prepareStatement("select MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID,MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID,MAPPED_CALENDAR_YEAR.YEAR, MAPPED_CALENDAR_YEAR.YEAR_VIS_ID, MAPPED_CALENDAR_YEAR.COMPANIES, MAPPED_CALENDAR_YEAR.UPDATED_BY_USER_ID,MAPPED_CALENDAR_YEAR.UPDATED_TIME,MAPPED_CALENDAR_YEAR.CREATED_TIME from MAPPED_CALENDAR_YEAR where    MAPPED_CALENDAR_YEAR_ID = ? ");
/*     */ 
/* 147 */       int col = 1;
/* 148 */       stmt.setInt(col++, pk.getMappedCalendarYearId());
/*     */ 
/* 150 */       resultSet = stmt.executeQuery();
/*     */ 
/* 152 */       if (!resultSet.next()) {
/* 153 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 156 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 157 */       if (this.mDetails.isModified())
/* 158 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 162 */       throw handleSQLException(pk, "select MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID,MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID,MAPPED_CALENDAR_YEAR.YEAR,MAPPED_CALENDAR_YEAR.YEAR_VIS_ID, MAPPED_CALENDAR_YEAR.COMPANIES, MAPPED_CALENDAR_YEAR.UPDATED_BY_USER_ID,MAPPED_CALENDAR_YEAR.UPDATED_TIME,MAPPED_CALENDAR_YEAR.CREATED_TIME from MAPPED_CALENDAR_YEAR where    MAPPED_CALENDAR_YEAR_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 166 */       closeResultSet(resultSet);
/* 167 */       closeStatement(stmt);
/* 168 */       closeConnection();
/*     */ 
/* 170 */       if (timer != null)
/* 171 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 202 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 203 */     this.mDetails.postCreateInit();
/*     */ 
/* 205 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 210 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 211 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 212 */       stmt = getConnection().prepareStatement("insert into MAPPED_CALENDAR_YEAR ( MAPPED_CALENDAR_YEAR_ID, MAPPED_MODEL_ID, YEAR,YEAR_VIS_ID, COMPANIES, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME ) values ( ?,?,?,?,?,?,?,? )");
/*     */ 
/* 215 */       int col = 1;
/* 216 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 217 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 220 */       int resultCount = stmt.executeUpdate();
/* 221 */       if (resultCount != 1)
/*     */       {
/* 223 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 226 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 230 */       throw handleSQLException(this.mDetails.getPK(), "insert into MAPPED_CALENDAR_YEAR ( MAPPED_CALENDAR_YEAR_ID,MAPPED_MODEL_ID,YEAR,YEAR_VIS_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 234 */       closeStatement(stmt);
/* 235 */       closeConnection();
/*     */ 
/* 237 */       if (timer != null) {
/* 238 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 244 */       getMappedCalendarElementDAO().update(this.mDetails.getMappedCalendarElementsMap());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 250 */       throw new RuntimeException("unexpected exception", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 275 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 279 */     PreparedStatement stmt = null;
/*     */ 
/* 281 */     boolean mainChanged = this.mDetails.isModified();
/* 282 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 286 */       if (getMappedCalendarElementDAO().update(this.mDetails.getMappedCalendarElementsMap())) {
/* 287 */         dependantChanged = true;
/*     */       }
/* 289 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 292 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 293 */         stmt = getConnection().prepareStatement("update MAPPED_CALENDAR_YEAR set MAPPED_MODEL_ID = ?, YEAR = ?, YEAR_VIS_ID = ?, COMPANIES = ?, UPDATED_BY_USER_ID = ?, UPDATED_TIME = ?, CREATED_TIME = ?   where  MAPPED_CALENDAR_YEAR_ID = ? ");
/*     */ 
/* 296 */         int col = 1;
/* 297 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 298 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 301 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 304 */         if (resultCount != 1) {
/* 305 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 308 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 317 */       throw handleSQLException(getPK(), "update MAPPED_CALENDAR_YEAR set MAPPED_MODEL_ID = ?,YEAR = ?,YEAR_VIS_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_CALENDAR_YEAR_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 321 */       closeStatement(stmt);
/* 322 */       closeConnection();
/*     */ 
/* 324 */       if ((timer != null) && (
/* 325 */         (mainChanged) || (dependantChanged)))
/* 326 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 345 */     if (items == null) {
/* 346 */       return false;
/*     */     }
/* 348 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 349 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 351 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 355 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/* 356 */       while (iter3.hasNext())
/*     */       {
/* 358 */         this.mDetails = ((MappedCalendarYearEVO)iter3.next());
/* 359 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 361 */         somethingChanged = true;
/*     */ 
/* 364 */         deleteDependants(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 368 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 369 */       while (iter2.hasNext())
/*     */       {
/* 371 */         this.mDetails = ((MappedCalendarYearEVO)iter2.next());
/*     */ 
/* 374 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 376 */         somethingChanged = true;
/*     */ 
/* 379 */         if (deleteStmt == null) {
/* 380 */           deleteStmt = getConnection().prepareStatement("delete from MAPPED_CALENDAR_YEAR where    MAPPED_CALENDAR_YEAR_ID = ? ");
/*     */         }
/*     */ 
/* 383 */         int col = 1;
/* 384 */         deleteStmt.setInt(col++, this.mDetails.getMappedCalendarYearId());
/*     */ 
/* 386 */         if (this._log.isDebugEnabled()) {
/* 387 */           this._log.debug("update", "MappedCalendarYear deleting MappedCalendarYearId=" + this.mDetails.getMappedCalendarYearId());
/*     */         }
/*     */ 
/* 392 */         deleteStmt.addBatch();
/*     */ 
/* 395 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 400 */       if (deleteStmt != null)
/*     */       {
/* 402 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 404 */         deleteStmt.executeBatch();
/*     */ 
/* 406 */         if (timer2 != null) {
/* 407 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 411 */       Iterator iter1 = items.values().iterator();
/* 412 */       while (iter1.hasNext())
/*     */       {
/* 414 */         this.mDetails = ((MappedCalendarYearEVO)iter1.next());
/*     */ 
/* 416 */         if (this.mDetails.insertPending())
/*     */         {
/* 418 */           somethingChanged = true;
/* 419 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 422 */         if (this.mDetails.isModified())
/*     */         {
/* 424 */           somethingChanged = true;
/* 425 */           doStore(); continue;
/*     */         }
/*     */ 
/* 429 */         if ((this.mDetails.deletePending()) || 
/* 435 */           (!getMappedCalendarElementDAO().update(this.mDetails.getMappedCalendarElementsMap()))) continue;
/* 436 */         somethingChanged = true;
/*     */       }
/*     */ 
/* 448 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 452 */       throw handleSQLException("delete from MAPPED_CALENDAR_YEAR where    MAPPED_CALENDAR_YEAR_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 456 */       if (deleteStmt != null)
/*     */       {
/* 458 */         closeStatement(deleteStmt);
/* 459 */         closeConnection();
/*     */       }
/*     */ 
/* 462 */       this.mDetails = null;
/*     */ 
/* 464 */       if ((somethingChanged) && 
/* 465 */         (timer != null))
/* 466 */         timer.logDebug("update", "collection"); 
/* 466 */     }
/*     */   }
/*     */ 
/*     */   private void deleteDependants(MappedCalendarYearPK pk)
/*     */   {
/* 492 */     Set emptyStrings = Collections.emptySet();
/* 493 */     deleteDependants(pk, emptyStrings);
/*     */   }
/*     */ 
/*     */   private void deleteDependants(MappedCalendarYearPK pk, Set<String> exclusionTables)
/*     */   {
/* 499 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*     */     {
/* 501 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*     */         continue;
/* 503 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 505 */       PreparedStatement stmt = null;
/*     */ 
/* 507 */       int resultCount = 0;
/* 508 */       String s = null;
/*     */       try
/*     */       {
/* 511 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*     */ 
/* 513 */         if (this._log.isDebugEnabled()) {
/* 514 */           this._log.debug("deleteDependants", s);
/*     */         }
/* 516 */         stmt = getConnection().prepareStatement(s);
/*     */ 
/* 519 */         int col = 1;
/* 520 */         stmt.setInt(col++, pk.getMappedCalendarYearId());
/*     */ 
/* 523 */         resultCount = stmt.executeUpdate();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 527 */         throw handleSQLException(pk, s, sqle);
/*     */       }
/*     */       finally
/*     */       {
/* 531 */         closeStatement(stmt);
/* 532 */         closeConnection();
/*     */ 
/* 534 */         if (timer != null) {
/* 535 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*     */         }
/*     */       }
/*     */     }
/* 539 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*     */     {
/* 541 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*     */         continue;
/* 543 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 545 */       PreparedStatement stmt = null;
/*     */ 
/* 547 */       int resultCount = 0;
/* 548 */       String s = null;
/*     */       try
/*     */       {
/* 551 */         s = SQL_DELETE_CHILDREN[i][1];
/*     */ 
/* 553 */         if (this._log.isDebugEnabled()) {
/* 554 */           this._log.debug("deleteDependants", s);
/*     */         }
/* 556 */         stmt = getConnection().prepareStatement(s);
/*     */ 
/* 559 */         int col = 1;
/* 560 */         stmt.setInt(col++, pk.getMappedCalendarYearId());
/*     */ 
/* 563 */         resultCount = stmt.executeUpdate();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 567 */         throw handleSQLException(pk, s, sqle);
/*     */       }
/*     */       finally
/*     */       {
/* 571 */         closeStatement(stmt);
/* 572 */         closeConnection();
/*     */ 
/* 574 */         if (timer != null)
/* 575 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(GlobalMappedModel2PK entityPK, GlobalMappedModel2EVO owningEVO, String dependants)
/*     */   {
/* 595 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 597 */     PreparedStatement stmt = null;
/* 598 */     ResultSet resultSet = null;
/*     */ 
/* 600 */     int itemCount = 0;
/*     */ 
/* 602 */     Collection theseItems = new ArrayList();
/* 603 */     owningEVO.setMappedCalendarYears(theseItems);
/* 604 */     owningEVO.setMappedCalendarYearsAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 608 */       stmt = getConnection().prepareStatement("select MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID,MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID,MAPPED_CALENDAR_YEAR.YEAR, MAPPED_CALENDAR_YEAR.YEAR_VIS_ID, MAPPED_CALENDAR_YEAR.COMPANIES, MAPPED_CALENDAR_YEAR.UPDATED_BY_USER_ID,MAPPED_CALENDAR_YEAR.UPDATED_TIME,MAPPED_CALENDAR_YEAR.CREATED_TIME from MAPPED_CALENDAR_YEAR where 1=1 and MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID = ? order by  MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID");
/*     */ 
/* 610 */       int col = 1;
/* 611 */       stmt.setInt(col++, entityPK.getMappedModelId());
/*     */ 
/* 613 */       resultSet = stmt.executeQuery();
/*     */ 
/* 616 */       while (resultSet.next())
/*     */       {
/* 618 */         itemCount++;
/* 619 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 621 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 624 */       if (timer != null) {
/* 625 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */ 
/* 628 */       if ((itemCount > 0) && (dependants.indexOf("<1>") > -1))
/*     */       {
/* 630 */         getMappedCalendarElementDAO().bulkGetAll(entityPK, theseItems, dependants);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle) {
/* 634 */       throw handleSQLException("select MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID,MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID,MAPPED_CALENDAR_YEAR.YEAR, MAPPED_CALENDAR_YEAR.YEAR_VIS_ID, MAPPED_CALENDAR_YEAR.COMPANIES, MAPPED_CALENDAR_YEAR.UPDATED_BY_USER_ID,MAPPED_CALENDAR_YEAR.UPDATED_TIME,MAPPED_CALENDAR_YEAR.CREATED_TIME from MAPPED_CALENDAR_YEAR where 1=1 and MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID = ? order by  MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 638 */       closeResultSet(resultSet);
/* 639 */       closeStatement(stmt);
/* 640 */       closeConnection();
/*     */ 
/* 642 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectMappedModelId, String dependants, Collection currentList)
/*     */   {
/* 667 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 668 */     PreparedStatement stmt = null;
/* 669 */     ResultSet resultSet = null;
/*     */ 
/* 671 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 675 */       stmt = getConnection().prepareStatement("select MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID,MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID,MAPPED_CALENDAR_YEAR.YEAR, MAPPED_CALENDAR_YEAR.YEAR_VIS_ID, MAPPED_CALENDAR_YEAR.COMPANIES, MAPPED_CALENDAR_YEAR.UPDATED_BY_USER_ID,MAPPED_CALENDAR_YEAR.UPDATED_TIME,MAPPED_CALENDAR_YEAR.CREATED_TIME from MAPPED_CALENDAR_YEAR where    MAPPED_MODEL_ID = ? ");
/*     */ 
/* 677 */       int col = 1;
/* 678 */       stmt.setInt(col++, selectMappedModelId);
/*     */ 
/* 680 */       resultSet = stmt.executeQuery();
/*     */ 
/* 682 */       while (resultSet.next())
/*     */       {
/* 684 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 687 */         getDependants(this.mDetails, dependants);
/*     */ 
/* 690 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 693 */       if (currentList != null)
/*     */       {
/* 696 */         ListIterator iter = items.listIterator();
/* 697 */         MappedCalendarYearEVO currentEVO = null;
/* 698 */         MappedCalendarYearEVO newEVO = null;
/* 699 */         while (iter.hasNext())
/*     */         {
/* 701 */           newEVO = (MappedCalendarYearEVO)iter.next();
/* 702 */           Iterator iter2 = currentList.iterator();
/* 703 */           while (iter2.hasNext())
/*     */           {
/* 705 */             currentEVO = (MappedCalendarYearEVO)iter2.next();
/* 706 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 708 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 714 */         Iterator iter2 = currentList.iterator();
/* 715 */         while (iter2.hasNext())
/*     */         {
/* 717 */           currentEVO = (MappedCalendarYearEVO)iter2.next();
/* 718 */           if (currentEVO.insertPending()) {
/* 719 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 723 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 727 */       throw handleSQLException("select MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID,MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID,MAPPED_CALENDAR_YEAR.YEAR,MAPPED_CALENDAR_YEAR.YEAR_VIS_ID,MAPPED_CALENDAR_YEAR.UPDATED_BY_USER_ID,MAPPED_CALENDAR_YEAR.UPDATED_TIME,MAPPED_CALENDAR_YEAR.CREATED_TIME from MAPPED_CALENDAR_YEAR where    MAPPED_MODEL_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 731 */       closeResultSet(resultSet);
/* 732 */       closeStatement(stmt);
/* 733 */       closeConnection();
/*     */ 
/* 735 */       if (timer != null) {
/* 736 */         timer.logDebug("getAll", " MappedModelId=" + selectMappedModelId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 741 */     return items;
/*     */   }
/*     */ 
/*     */   public MappedCalendarYearEVO getDetails(GlobalMappedModel2CK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 758 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 761 */     if (this.mDetails == null) {
/* 762 */       doLoad(((MappedCalendarYearCK)paramCK).getMappedCalendarYearPK());
/*     */     }
/* 764 */     else if (!this.mDetails.getPK().equals(((MappedCalendarYearCK)paramCK).getMappedCalendarYearPK())) {
/* 765 */       doLoad(((MappedCalendarYearCK)paramCK).getMappedCalendarYearPK());
/*     */     }
/*     */ 
/* 768 */     if ((dependants.indexOf("<1>") > -1) && (!this.mDetails.isMappedCalendarElementsAllItemsLoaded()))
/*     */     {
/* 773 */       this.mDetails.setMappedCalendarElements(getMappedCalendarElementDAO().getAll(this.mDetails.getMappedCalendarYearId(), dependants, this.mDetails.getMappedCalendarElements()));
/*     */ 
/* 780 */       this.mDetails.setMappedCalendarElementsAllItemsLoaded(true);
/*     */     }
/*     */ 
/* 783 */     if ((paramCK instanceof MappedCalendarElementCK))
/*     */     {
/* 785 */       if (this.mDetails.getMappedCalendarElements() == null) {
/* 786 */         this.mDetails.loadMappedCalendarElementsItem(getMappedCalendarElementDAO().getDetails(paramCK, dependants));
/*     */       }
/*     */       else {
/* 789 */         MappedCalendarElementPK pk = ((MappedCalendarElementCK)paramCK).getMappedCalendarElementPK();
/* 790 */         MappedCalendarElementEVO evo = this.mDetails.getMappedCalendarElementsItem(pk);
/* 791 */         if (evo == null) {
/* 792 */           this.mDetails.loadMappedCalendarElementsItem(getMappedCalendarElementDAO().getDetails(paramCK, dependants));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 797 */     MappedCalendarYearEVO details = new MappedCalendarYearEVO();
/* 798 */     details = this.mDetails.deepClone();
/*     */ 
/* 800 */     if (timer != null) {
/* 801 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 803 */     return details;
/*     */   }
/*     */ 
/*     */   public MappedCalendarYearEVO getDetails(GlobalMappedModel2CK paramCK, MappedCalendarYearEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 809 */     MappedCalendarYearEVO savedEVO = this.mDetails;
/* 810 */     this.mDetails = paramEVO;
/* 811 */     MappedCalendarYearEVO newEVO = getDetails(paramCK, dependants);
/* 812 */     this.mDetails = savedEVO;
/* 813 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public MappedCalendarYearEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 819 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 823 */     getDependants(this.mDetails, dependants);
/*     */ 
/* 826 */     MappedCalendarYearEVO details = this.mDetails.deepClone();
/*     */ 
/* 828 */     if (timer != null) {
/* 829 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 831 */     return details;
/*     */   }
/*     */ 
/*     */   protected MappedCalendarElementDAO getMappedCalendarElementDAO()
/*     */   {
/* 840 */     if (this.mMappedCalendarElementDAO == null)
/*     */     {
/* 842 */       if (this.mDataSource != null)
/* 843 */         this.mMappedCalendarElementDAO = new MappedCalendarElementDAO(this.mDataSource);
/*     */       else {
/* 845 */         this.mMappedCalendarElementDAO = new MappedCalendarElementDAO(getConnection());
/*     */       }
/*     */     }
/* 848 */     return this.mMappedCalendarElementDAO;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 853 */     return "MappedCalendarYear";
/*     */   }
/*     */ 
/*     */   public MappedCalendarYearRefImpl getRef(MappedCalendarYearPK paramMappedCalendarYearPK)
/*     */   {
/* 858 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 859 */     PreparedStatement stmt = null;
/* 860 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 863 */       stmt = getConnection().prepareStatement("select 0,MAPPED_MODEL.MAPPED_MODEL_ID from MAPPED_CALENDAR_YEAR,MAPPED_MODEL where 1=1 and MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID = ? and MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID");
/* 864 */       int col = 1;
/* 865 */       stmt.setInt(col++, paramMappedCalendarYearPK.getMappedCalendarYearId());
/*     */ 
/* 867 */       resultSet = stmt.executeQuery();
/*     */ 
/* 869 */       if (!resultSet.next()) {
/* 870 */         throw new RuntimeException(getEntityName() + " getRef " + paramMappedCalendarYearPK + " not found");
/*     */       }
/* 872 */       col = 2;
/* 873 */       GlobalMappedModel2PK newMappedModelPK = new GlobalMappedModel2PK(resultSet.getInt(col++));
/*     */ 
/* 877 */       String textMappedCalendarYear = "";
/* 878 */       MappedCalendarYearCK ckMappedCalendarYear = new MappedCalendarYearCK(newMappedModelPK, paramMappedCalendarYearPK);
/*     */ 
/* 883 */       MappedCalendarYearRefImpl localMappedCalendarYearRefImpl = new MappedCalendarYearRefImpl(ckMappedCalendarYear, textMappedCalendarYear);
/*     */       return localMappedCalendarYearRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 888 */       throw handleSQLException(paramMappedCalendarYearPK, "select 0,MAPPED_MODEL.MAPPED_MODEL_ID from MAPPED_CALENDAR_YEAR,MAPPED_MODEL where 1=1 and MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID = ? and MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 892 */       closeResultSet(resultSet);
/* 893 */       closeStatement(stmt);
/* 894 */       closeConnection();
/*     */ 
/* 896 */       if (timer != null)
/* 897 */         timer.logDebug("getRef", paramMappedCalendarYearPK); 
/* 897 */     }
/*     */   }
/*     */ 
/*     */   public void getDependants(Collection c, String dependants)
/*     */   {
/* 909 */     if (c == null)
/* 910 */       return;
/* 911 */     Iterator iter = c.iterator();
/* 912 */     while (iter.hasNext())
/*     */     {
/* 914 */       MappedCalendarYearEVO evo = (MappedCalendarYearEVO)iter.next();
/* 915 */       getDependants(evo, dependants);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void getDependants(MappedCalendarYearEVO evo, String dependants)
/*     */   {
/* 929 */     if (evo.insertPending()) {
/* 930 */       return;
/*     */     }
/* 932 */     if (evo.getMappedCalendarYearId() < 1) {
/* 933 */       return;
/*     */     }
/*     */ 
/* 937 */     if (dependants.indexOf("<1>") > -1)
/*     */     {
/* 940 */       if (!evo.isMappedCalendarElementsAllItemsLoaded())
/*     */       {
/* 942 */         evo.setMappedCalendarElements(getMappedCalendarElementDAO().getAll(evo.getMappedCalendarYearId(), dependants, evo.getMappedCalendarElements()));
/*     */ 
/* 949 */         evo.setMappedCalendarElementsAllItemsLoaded(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.mapping.MappedCalendarYearDAO
 * JD-Core Version:    0.6.0
 */