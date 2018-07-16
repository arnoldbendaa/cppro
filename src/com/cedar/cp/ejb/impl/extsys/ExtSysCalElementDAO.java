/*     */ package com.cedar.cp.ejb.impl.extsys;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCalElementCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCalElementPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCalElementRefImpl;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCalendarYearPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
/*     */ import com.cedar.cp.dto.extsys.ExternalSystemCK;
/*     */ import com.cedar.cp.dto.extsys.ExternalSystemPK;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.SqlBuilder;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.io.PrintWriter;
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
/*     */ public class ExtSysCalElementDAO extends AbstractDAO
/*     */ {
/*  34 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID,EXT_SYS_CAL_ELEMENT.CAL_ELEMENT_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_ID,EXT_SYS_CAL_ELEMENT.DESCRIPTION,EXT_SYS_CAL_ELEMENT.IDX";
/*     */   protected static final String SQL_LOAD = " from EXT_SYS_CAL_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? AND CAL_ELEMENT_VIS_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into EXT_SYS_CAL_ELEMENT ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,CALENDAR_YEAR_VIS_ID,CAL_ELEMENT_VIS_ID,CALENDAR_YEAR_ID,DESCRIPTION,IDX) values ( ?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update EXT_SYS_CAL_ELEMENT set CALENDAR_YEAR_ID = ?,DESCRIPTION = ?,IDX = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? AND CAL_ELEMENT_VIS_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from EXT_SYS_CAL_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? AND CAL_ELEMENT_VIS_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from EXT_SYS_CAL_ELEMENT,EXT_SYS_CALENDAR_YEAR,EXT_SYS_COMPANY where 1=1 and EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID and EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID = EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID and EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID = EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID ,EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID ,EXT_SYS_CAL_ELEMENT.CAL_ELEMENT_VIS_ID";
/*     */   protected static final String SQL_GET_ALL = " from EXT_SYS_CAL_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? ";
/*     */   protected ExtSysCalElementEVO mDetails;
/*     */ 
/*     */   public ExtSysCalElementDAO(Connection connection)
/*     */   {
/*  41 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ExtSysCalElementDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ExtSysCalElementDAO(DataSource ds)
/*     */   {
/*  57 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ExtSysCalElementPK getPK()
/*     */   {
/*  65 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ExtSysCalElementEVO details)
/*     */   {
/*  74 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private ExtSysCalElementEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  92 */     int col = 1;
/*  93 */     ExtSysCalElementEVO evo = new ExtSysCalElementEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getInt(col++));
/*     */ 
/* 103 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ExtSysCalElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 108 */     int col = startCol_;
/* 109 */     stmt_.setInt(col++, evo_.getExternalSystemId());
/* 110 */     stmt_.setString(col++, evo_.getCompanyVisId());
/* 111 */     stmt_.setString(col++, evo_.getCalendarYearVisId());
/* 112 */     stmt_.setString(col++, evo_.getCalElementVisId());
/* 113 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ExtSysCalElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 118 */     int col = startCol_;
/* 119 */     stmt_.setInt(col++, evo_.getCalendarYearId());
/* 120 */     stmt_.setString(col++, evo_.getDescription());
/* 121 */     stmt_.setInt(col++, evo_.getIdx());
/* 122 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ExtSysCalElementPK pk)
/*     */     throws ValidationException
/*     */   {
/* 141 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 143 */     PreparedStatement stmt = null;
/* 144 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 148 */       stmt = getConnection().prepareStatement("select EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID,EXT_SYS_CAL_ELEMENT.CAL_ELEMENT_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_ID,EXT_SYS_CAL_ELEMENT.DESCRIPTION,EXT_SYS_CAL_ELEMENT.IDX from EXT_SYS_CAL_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? AND CAL_ELEMENT_VIS_ID = ? ");
/*     */ 
/* 151 */       int col = 1;
/* 152 */       stmt.setInt(col++, pk.getExternalSystemId());
/* 153 */       stmt.setString(col++, pk.getCompanyVisId());
/* 154 */       stmt.setString(col++, pk.getCalendarYearVisId());
/* 155 */       stmt.setString(col++, pk.getCalElementVisId());
/*     */ 
/* 157 */       resultSet = stmt.executeQuery();
/*     */ 
/* 159 */       if (!resultSet.next()) {
/* 160 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 163 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 164 */       if (this.mDetails.isModified())
/* 165 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 169 */       throw handleSQLException(pk, "select EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID,EXT_SYS_CAL_ELEMENT.CAL_ELEMENT_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_ID,EXT_SYS_CAL_ELEMENT.DESCRIPTION,EXT_SYS_CAL_ELEMENT.IDX from EXT_SYS_CAL_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? AND CAL_ELEMENT_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 173 */       closeResultSet(resultSet);
/* 174 */       closeStatement(stmt);
/* 175 */       closeConnection();
/*     */ 
/* 177 */       if (timer != null)
/* 178 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 209 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 210 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 214 */       stmt = getConnection().prepareStatement("insert into EXT_SYS_CAL_ELEMENT ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,CALENDAR_YEAR_VIS_ID,CAL_ELEMENT_VIS_ID,CALENDAR_YEAR_ID,DESCRIPTION,IDX) values ( ?,?,?,?,?,?,?)");
/*     */ 
/* 217 */       int col = 1;
/* 218 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 219 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 222 */       int resultCount = stmt.executeUpdate();
/* 223 */       if (resultCount != 1)
/*     */       {
/* 225 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 228 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 232 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXT_SYS_CAL_ELEMENT ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,CALENDAR_YEAR_VIS_ID,CAL_ELEMENT_VIS_ID,CALENDAR_YEAR_ID,DESCRIPTION,IDX) values ( ?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 236 */       closeStatement(stmt);
/* 237 */       closeConnection();
/*     */ 
/* 239 */       if (timer != null)
/* 240 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 265 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 269 */     PreparedStatement stmt = null;
/*     */ 
/* 271 */     boolean mainChanged = this.mDetails.isModified();
/* 272 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 275 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 277 */         stmt = getConnection().prepareStatement("update EXT_SYS_CAL_ELEMENT set CALENDAR_YEAR_ID = ?,DESCRIPTION = ?,IDX = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? AND CAL_ELEMENT_VIS_ID = ? ");
/*     */ 
/* 280 */         int col = 1;
/* 281 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 282 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 285 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 288 */         if (resultCount != 1) {
/* 289 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 292 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 301 */       throw handleSQLException(getPK(), "update EXT_SYS_CAL_ELEMENT set CALENDAR_YEAR_ID = ?,DESCRIPTION = ?,IDX = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? AND CAL_ELEMENT_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 305 */       closeStatement(stmt);
/* 306 */       closeConnection();
/*     */ 
/* 308 */       if ((timer != null) && (
/* 309 */         (mainChanged) || (dependantChanged)))
/* 310 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 332 */     if (items == null) {
/* 333 */       return false;
/*     */     }
/* 335 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 336 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 338 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 343 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 344 */       while (iter2.hasNext())
/*     */       {
/* 346 */         this.mDetails = ((ExtSysCalElementEVO)iter2.next());
/*     */ 
/* 349 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 351 */         somethingChanged = true;
/*     */ 
/* 354 */         if (deleteStmt == null) {
/* 355 */           deleteStmt = getConnection().prepareStatement("delete from EXT_SYS_CAL_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? AND CAL_ELEMENT_VIS_ID = ? ");
/*     */         }
/*     */ 
/* 358 */         int col = 1;
/* 359 */         deleteStmt.setInt(col++, this.mDetails.getExternalSystemId());
/* 360 */         deleteStmt.setString(col++, this.mDetails.getCompanyVisId());
/* 361 */         deleteStmt.setString(col++, this.mDetails.getCalendarYearVisId());
/* 362 */         deleteStmt.setString(col++, this.mDetails.getCalElementVisId());
/*     */ 
/* 364 */         if (this._log.isDebugEnabled()) {
/* 365 */           this._log.debug("update", "ExtSysCalElement deleting ExternalSystemId=" + this.mDetails.getExternalSystemId() + ",CompanyVisId=" + this.mDetails.getCompanyVisId() + ",CalendarYearVisId=" + this.mDetails.getCalendarYearVisId() + ",CalElementVisId=" + this.mDetails.getCalElementVisId());
/*     */         }
/*     */ 
/* 373 */         deleteStmt.addBatch();
/*     */ 
/* 376 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 381 */       if (deleteStmt != null)
/*     */       {
/* 383 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 385 */         deleteStmt.executeBatch();
/*     */ 
/* 387 */         if (timer2 != null) {
/* 388 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 392 */       Iterator iter1 = items.values().iterator();
/* 393 */       while (iter1.hasNext())
/*     */       {
/* 395 */         this.mDetails = ((ExtSysCalElementEVO)iter1.next());
/*     */ 
/* 397 */         if (this.mDetails.insertPending())
/*     */         {
/* 399 */           somethingChanged = true;
/* 400 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 403 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 405 */         somethingChanged = true;
/* 406 */         doStore();
/*     */       }
/*     */ 
/* 417 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 421 */       throw handleSQLException("delete from EXT_SYS_CAL_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? AND CAL_ELEMENT_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 425 */       if (deleteStmt != null)
/*     */       {
/* 427 */         closeStatement(deleteStmt);
/* 428 */         closeConnection();
/*     */       }
/*     */ 
/* 431 */       this.mDetails = null;
/*     */ 
/* 433 */       if ((somethingChanged) && 
/* 434 */         (timer != null))
/* 435 */         timer.logDebug("update", "collection"); 
/* 435 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ExternalSystemPK entityPK, Collection owners, String dependants)
/*     */   {
/* 469 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 471 */     PreparedStatement stmt = null;
/* 472 */     ResultSet resultSet = null;
/*     */ 
/* 474 */     int itemCount = 0;
/*     */ 
/* 476 */     ExtSysCalendarYearEVO owningEVO = null;
/* 477 */     Iterator ownersIter = owners.iterator();
/* 478 */     while (ownersIter.hasNext())
/*     */     {
/* 480 */       owningEVO = (ExtSysCalendarYearEVO)ownersIter.next();
/* 481 */       owningEVO.setExtSysCalendarElementsAllItemsLoaded(true);
/*     */     }
/* 483 */     ownersIter = owners.iterator();
/* 484 */     owningEVO = (ExtSysCalendarYearEVO)ownersIter.next();
/* 485 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 489 */       stmt = getConnection().prepareStatement("select EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID,EXT_SYS_CAL_ELEMENT.CAL_ELEMENT_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_ID,EXT_SYS_CAL_ELEMENT.DESCRIPTION,EXT_SYS_CAL_ELEMENT.IDX from EXT_SYS_CAL_ELEMENT,EXT_SYS_CALENDAR_YEAR,EXT_SYS_COMPANY where 1=1 and EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID and EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID = EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID and EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID = EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID ,EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID ,EXT_SYS_CAL_ELEMENT.CAL_ELEMENT_VIS_ID");
/*     */ 
/* 491 */       int col = 1;
/* 492 */       stmt.setInt(col++, entityPK.getExternalSystemId());
/*     */ 
/* 494 */       resultSet = stmt.executeQuery();
/*     */ 
/* 497 */       while (resultSet.next())
/*     */       {
/* 499 */         itemCount++;
/* 500 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 505 */         while ((this.mDetails.getExternalSystemId() != owningEVO.getExternalSystemId()) || (!this.mDetails.getCompanyVisId().equals(owningEVO.getCompanyVisId())) || (!this.mDetails.getCalendarYearVisId().equals(owningEVO.getCalendarYearVisId())))
/*     */         {
/* 511 */           if (!ownersIter.hasNext())
/*     */           {
/* 513 */             this._log.debug("bulkGetAll", "can't find owning [ExternalSystemId=" + this.mDetails.getExternalSystemId() + "CompanyVisId=" + this.mDetails.getCompanyVisId() + "CalendarYearVisId=" + this.mDetails.getCalendarYearVisId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 519 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 520 */             ownersIter = owners.iterator();
/* 521 */             while (ownersIter.hasNext())
/*     */             {
/* 523 */               owningEVO = (ExtSysCalendarYearEVO)ownersIter.next();
/* 524 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 526 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 528 */           owningEVO = (ExtSysCalendarYearEVO)ownersIter.next();
/*     */         }
/* 530 */         if (owningEVO.getExtSysCalendarElements() == null)
/*     */         {
/* 532 */           theseItems = new ArrayList();
/* 533 */           owningEVO.setExtSysCalendarElements(theseItems);
/* 534 */           owningEVO.setExtSysCalendarElementsAllItemsLoaded(true);
/*     */         }
/* 536 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 539 */       if (timer != null) {
/* 540 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 545 */       throw handleSQLException("select EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID,EXT_SYS_CAL_ELEMENT.CAL_ELEMENT_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_ID,EXT_SYS_CAL_ELEMENT.DESCRIPTION,EXT_SYS_CAL_ELEMENT.IDX from EXT_SYS_CAL_ELEMENT,EXT_SYS_CALENDAR_YEAR,EXT_SYS_COMPANY where 1=1 and EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID and EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID = EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID and EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID = EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID ,EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID ,EXT_SYS_CAL_ELEMENT.CAL_ELEMENT_VIS_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 549 */       closeResultSet(resultSet);
/* 550 */       closeStatement(stmt);
/* 551 */       closeConnection();
/*     */ 
/* 553 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectExternalSystemId, String selectCompanyVisId, String selectCalendarYearVisId, String dependants, Collection currentList)
/*     */   {
/* 584 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 585 */     PreparedStatement stmt = null;
/* 586 */     ResultSet resultSet = null;
/*     */ 
/* 588 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 592 */       stmt = getConnection().prepareStatement("select EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID,EXT_SYS_CAL_ELEMENT.CAL_ELEMENT_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_ID,EXT_SYS_CAL_ELEMENT.DESCRIPTION,EXT_SYS_CAL_ELEMENT.IDX from EXT_SYS_CAL_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? ");
/*     */ 
/* 594 */       int col = 1;
/* 595 */       stmt.setInt(col++, selectExternalSystemId);
/* 596 */       stmt.setString(col++, selectCompanyVisId);
/* 597 */       stmt.setString(col++, selectCalendarYearVisId);
/*     */ 
/* 599 */       resultSet = stmt.executeQuery();
/*     */ 
/* 601 */       while (resultSet.next())
/*     */       {
/* 603 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 606 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 609 */       if (currentList != null)
/*     */       {
/* 612 */         ListIterator iter = items.listIterator();
/* 613 */         ExtSysCalElementEVO currentEVO = null;
/* 614 */         ExtSysCalElementEVO newEVO = null;
/* 615 */         while (iter.hasNext())
/*     */         {
/* 617 */           newEVO = (ExtSysCalElementEVO)iter.next();
/* 618 */           Iterator iter2 = currentList.iterator();
/* 619 */           while (iter2.hasNext())
/*     */           {
/* 621 */             currentEVO = (ExtSysCalElementEVO)iter2.next();
/* 622 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 624 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 630 */         Iterator iter2 = currentList.iterator();
/* 631 */         while (iter2.hasNext())
/*     */         {
/* 633 */           currentEVO = (ExtSysCalElementEVO)iter2.next();
/* 634 */           if (currentEVO.insertPending()) {
/* 635 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 639 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 643 */       throw handleSQLException("select EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID,EXT_SYS_CAL_ELEMENT.CAL_ELEMENT_VIS_ID,EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_ID,EXT_SYS_CAL_ELEMENT.DESCRIPTION,EXT_SYS_CAL_ELEMENT.IDX from EXT_SYS_CAL_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 647 */       closeResultSet(resultSet);
/* 648 */       closeStatement(stmt);
/* 649 */       closeConnection();
/*     */ 
/* 651 */       if (timer != null) {
/* 652 */         timer.logDebug("getAll", " ExternalSystemId=" + selectExternalSystemId + ",CompanyVisId=" + selectCompanyVisId + ",CalendarYearVisId=" + selectCalendarYearVisId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 659 */     return items;
/*     */   }
/*     */ 
/*     */   public ExtSysCalElementEVO getDetails(ExternalSystemCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 673 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 676 */     if (this.mDetails == null) {
/* 677 */       doLoad(((ExtSysCalElementCK)paramCK).getExtSysCalElementPK());
/*     */     }
/* 679 */     else if (!this.mDetails.getPK().equals(((ExtSysCalElementCK)paramCK).getExtSysCalElementPK())) {
/* 680 */       doLoad(((ExtSysCalElementCK)paramCK).getExtSysCalElementPK());
/*     */     }
/*     */ 
/* 683 */     ExtSysCalElementEVO details = new ExtSysCalElementEVO();
/* 684 */     details = this.mDetails.deepClone();
/*     */ 
/* 686 */     if (timer != null) {
/* 687 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 689 */     return details;
/*     */   }
/*     */ 
/*     */   public ExtSysCalElementEVO getDetails(ExternalSystemCK paramCK, ExtSysCalElementEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 695 */     ExtSysCalElementEVO savedEVO = this.mDetails;
/* 696 */     this.mDetails = paramEVO;
/* 697 */     ExtSysCalElementEVO newEVO = getDetails(paramCK, dependants);
/* 698 */     this.mDetails = savedEVO;
/* 699 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public ExtSysCalElementEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 705 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 709 */     ExtSysCalElementEVO details = this.mDetails.deepClone();
/*     */ 
/* 711 */     if (timer != null) {
/* 712 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 714 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 719 */     return "ExtSysCalElement";
/*     */   }
/*     */ 
/*     */   public ExtSysCalElementRefImpl getRef(ExtSysCalElementPK paramExtSysCalElementPK)
/*     */   {
/* 724 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 725 */     PreparedStatement stmt = null;
/* 726 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 729 */       stmt = getConnection().prepareStatement("select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID,EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID from EXT_SYS_CAL_ELEMENT,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_CALENDAR_YEAR where 1=1 and EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID = ? and EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID = ? and EXT_SYS_CAL_ELEMENT.CAL_ELEMENT_VIS_ID = ? and EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID and EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID = EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID and EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID = EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID = EXT_SYS_COMPANY.CALENDAR_YEAR_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID");
/* 730 */       int col = 1;
/* 731 */       stmt.setInt(col++, paramExtSysCalElementPK.getExternalSystemId());
/* 732 */       stmt.setString(col++, paramExtSysCalElementPK.getCompanyVisId());
/* 733 */       stmt.setString(col++, paramExtSysCalElementPK.getCalendarYearVisId());
/* 734 */       stmt.setString(col++, paramExtSysCalElementPK.getCalElementVisId());
/*     */ 
/* 736 */       resultSet = stmt.executeQuery();
/*     */ 
/* 738 */       if (!resultSet.next()) {
/* 739 */         throw new RuntimeException(getEntityName() + " getRef " + paramExtSysCalElementPK + " not found");
/*     */       }
/* 741 */       col = 2;
/* 742 */       ExternalSystemPK newExternalSystemPK = new ExternalSystemPK(resultSet.getInt(col++));
/*     */ 
/* 746 */       ExtSysCompanyPK newExtSysCompanyPK = new ExtSysCompanyPK(resultSet.getInt(col++), resultSet.getString(col++));
/*     */ 
/* 751 */       ExtSysCalendarYearPK newExtSysCalendarYearPK = new ExtSysCalendarYearPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++));
/*     */ 
/* 757 */       String textExtSysCalElement = "";
/* 758 */       ExtSysCalElementCK ckExtSysCalElement = new ExtSysCalElementCK(newExternalSystemPK, newExtSysCompanyPK, newExtSysCalendarYearPK, paramExtSysCalElementPK);
/*     */ 
/* 765 */       ExtSysCalElementRefImpl localExtSysCalElementRefImpl = new ExtSysCalElementRefImpl(ckExtSysCalElement, textExtSysCalElement);
/*     */       return localExtSysCalElementRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 770 */       throw handleSQLException(paramExtSysCalElementPK, "select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID,EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID from EXT_SYS_CAL_ELEMENT,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_CALENDAR_YEAR where 1=1 and EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID = ? and EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID = ? and EXT_SYS_CAL_ELEMENT.CAL_ELEMENT_VIS_ID = ? and EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID and EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID = EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID and EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID = EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID = EXT_SYS_COMPANY.CALENDAR_YEAR_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 774 */       closeResultSet(resultSet);
/* 775 */       closeStatement(stmt);
/* 776 */       closeConnection();
/*     */ 
/* 778 */       if (timer != null)
/* 779 */         timer.logDebug("getRef", paramExtSysCalElementPK); 
/* 779 */     }
/*     */   }
/*     */ 
/*     */   public int checkConstraintViolations(int externalSystemId, int maxToReport, PrintWriter log)
/*     */   {
/* 796 */     SqlExecutor sqlExecutor = null;
/* 797 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 802 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "select external_system_id, company_vis_id, calendar_year_vis_id, cal_element_vis_id", "from ext_sys_cal_element", "where external_system_id = <externalSystemId>", "group by external_system_id, company_vis_id, calendar_year_vis_id, cal_element_vis_id", "having count(*) > 1" });
/*     */ 
/* 810 */       sqlExecutor = new SqlExecutor("checkConstraintViolations", getDataSource(), sqlBuilder, this._log);
/*     */ 
/* 812 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/*     */ 
/* 814 */       rs = sqlExecutor.getResultSet();
/*     */ 
/* 816 */       int count = 0;
/* 817 */       while ((rs.next()) && (count < maxToReport))
/*     */       {
/* 819 */         log.print("Found calendar year element duplicate details: company:[" + rs.getString("COMPANY_VIS_ID") + "]" + " calendar vis id:[" + rs.getString("CALENDAR_YEAR_VIS_ID") + "]" + " calendar element:[" + rs.getString("CAL_ELEMENT_VIS_ID") + "]");
/*     */ 
/* 823 */         count++;
/*     */       }
/*     */ 
/* 826 */       int i = count;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 830 */       throw handleSQLException("checkConstraintViolations", e);
/*     */     }
/*     */     finally
/*     */     {
/* 834 */       closeResultSet(rs);
/* 835 */       sqlExecutor.close(); } //throw localObject;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extsys.ExtSysCalElementDAO
 * JD-Core Version:    0.6.0
 */