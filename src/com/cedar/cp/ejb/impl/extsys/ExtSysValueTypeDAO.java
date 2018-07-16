/*     */ package com.cedar.cp.ejb.impl.extsys;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysLedgerPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysValueTypeCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysValueTypePK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysValueTypeRefImpl;
/*     */ import com.cedar.cp.dto.extsys.ExternalSystemCK;
/*     */ import com.cedar.cp.dto.extsys.ExternalSystemPK;
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
/*     */ public class ExtSysValueTypeDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID,EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID,EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID,EXT_SYS_VALUE_TYPE.VALUE_TYPE_VIS_ID,EXT_SYS_VALUE_TYPE.DESCRIPTION";
/*     */   protected static final String SQL_LOAD = " from EXT_SYS_VALUE_TYPE where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND VALUE_TYPE_VIS_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into EXT_SYS_VALUE_TYPE ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,VALUE_TYPE_VIS_ID,DESCRIPTION) values ( ?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update EXT_SYS_VALUE_TYPE set DESCRIPTION = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND VALUE_TYPE_VIS_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from EXT_SYS_VALUE_TYPE where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND VALUE_TYPE_VIS_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from EXT_SYS_VALUE_TYPE,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID ,EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID ,EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID ,EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID ,EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID ,EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID ,EXT_SYS_VALUE_TYPE.VALUE_TYPE_VIS_ID";
/*     */   protected static final String SQL_GET_ALL = " from EXT_SYS_VALUE_TYPE where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ";
/*     */   protected ExtSysValueTypeEVO mDetails;
/*     */ 
/*     */   public ExtSysValueTypeDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ExtSysValueTypeDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ExtSysValueTypeDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ExtSysValueTypePK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ExtSysValueTypeEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private ExtSysValueTypeEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  88 */     int col = 1;
/*  89 */     ExtSysValueTypeEVO evo = new ExtSysValueTypeEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++));
/*     */ 
/*  97 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ExtSysValueTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 102 */     int col = startCol_;
/* 103 */     stmt_.setInt(col++, evo_.getExternalSystemId());
/* 104 */     stmt_.setString(col++, evo_.getCompanyVisId());
/* 105 */     stmt_.setString(col++, evo_.getLedgerVisId());
/* 106 */     stmt_.setString(col++, evo_.getValueTypeVisId());
/* 107 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ExtSysValueTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 112 */     int col = startCol_;
/* 113 */     stmt_.setString(col++, evo_.getDescription());
/* 114 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ExtSysValueTypePK pk)
/*     */     throws ValidationException
/*     */   {
/* 133 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 135 */     PreparedStatement stmt = null;
/* 136 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 140 */       stmt = getConnection().prepareStatement("select EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID,EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID,EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID,EXT_SYS_VALUE_TYPE.VALUE_TYPE_VIS_ID,EXT_SYS_VALUE_TYPE.DESCRIPTION from EXT_SYS_VALUE_TYPE where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND VALUE_TYPE_VIS_ID = ? ");
/*     */ 
/* 143 */       int col = 1;
/* 144 */       stmt.setInt(col++, pk.getExternalSystemId());
/* 145 */       stmt.setString(col++, pk.getCompanyVisId());
/* 146 */       stmt.setString(col++, pk.getLedgerVisId());
/* 147 */       stmt.setString(col++, pk.getValueTypeVisId());
/*     */ 
/* 149 */       resultSet = stmt.executeQuery();
/*     */ 
/* 151 */       if (!resultSet.next()) {
/* 152 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 155 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 156 */       if (this.mDetails.isModified())
/* 157 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 161 */       throw handleSQLException(pk, "select EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID,EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID,EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID,EXT_SYS_VALUE_TYPE.VALUE_TYPE_VIS_ID,EXT_SYS_VALUE_TYPE.DESCRIPTION from EXT_SYS_VALUE_TYPE where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND VALUE_TYPE_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 165 */       closeResultSet(resultSet);
/* 166 */       closeStatement(stmt);
/* 167 */       closeConnection();
/*     */ 
/* 169 */       if (timer != null)
/* 170 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 197 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 198 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 202 */       stmt = getConnection().prepareStatement("insert into EXT_SYS_VALUE_TYPE ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,VALUE_TYPE_VIS_ID,DESCRIPTION) values ( ?,?,?,?,?)");
/*     */ 
/* 205 */       int col = 1;
/* 206 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 207 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 210 */       int resultCount = stmt.executeUpdate();
/* 211 */       if (resultCount != 1)
/*     */       {
/* 213 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 216 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 220 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXT_SYS_VALUE_TYPE ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,VALUE_TYPE_VIS_ID,DESCRIPTION) values ( ?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 224 */       closeStatement(stmt);
/* 225 */       closeConnection();
/*     */ 
/* 227 */       if (timer != null)
/* 228 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 251 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 255 */     PreparedStatement stmt = null;
/*     */ 
/* 257 */     boolean mainChanged = this.mDetails.isModified();
/* 258 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 261 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 263 */         stmt = getConnection().prepareStatement("update EXT_SYS_VALUE_TYPE set DESCRIPTION = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND VALUE_TYPE_VIS_ID = ? ");
/*     */ 
/* 266 */         int col = 1;
/* 267 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 268 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 271 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 274 */         if (resultCount != 1) {
/* 275 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 278 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 287 */       throw handleSQLException(getPK(), "update EXT_SYS_VALUE_TYPE set DESCRIPTION = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND VALUE_TYPE_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 291 */       closeStatement(stmt);
/* 292 */       closeConnection();
/*     */ 
/* 294 */       if ((timer != null) && (
/* 295 */         (mainChanged) || (dependantChanged)))
/* 296 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 318 */     if (items == null) {
/* 319 */       return false;
/*     */     }
/* 321 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 322 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 324 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 329 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 330 */       while (iter2.hasNext())
/*     */       {
/* 332 */         this.mDetails = ((ExtSysValueTypeEVO)iter2.next());
/*     */ 
/* 335 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 337 */         somethingChanged = true;
/*     */ 
/* 340 */         if (deleteStmt == null) {
/* 341 */           deleteStmt = getConnection().prepareStatement("delete from EXT_SYS_VALUE_TYPE where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND VALUE_TYPE_VIS_ID = ? ");
/*     */         }
/*     */ 
/* 344 */         int col = 1;
/* 345 */         deleteStmt.setInt(col++, this.mDetails.getExternalSystemId());
/* 346 */         deleteStmt.setString(col++, this.mDetails.getCompanyVisId());
/* 347 */         deleteStmt.setString(col++, this.mDetails.getLedgerVisId());
/* 348 */         deleteStmt.setString(col++, this.mDetails.getValueTypeVisId());
/*     */ 
/* 350 */         if (this._log.isDebugEnabled()) {
/* 351 */           this._log.debug("update", "ExtSysValueType deleting ExternalSystemId=" + this.mDetails.getExternalSystemId() + ",CompanyVisId=" + this.mDetails.getCompanyVisId() + ",LedgerVisId=" + this.mDetails.getLedgerVisId() + ",ValueTypeVisId=" + this.mDetails.getValueTypeVisId());
/*     */         }
/*     */ 
/* 359 */         deleteStmt.addBatch();
/*     */ 
/* 362 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 367 */       if (deleteStmt != null)
/*     */       {
/* 369 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 371 */         deleteStmt.executeBatch();
/*     */ 
/* 373 */         if (timer2 != null) {
/* 374 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 378 */       Iterator iter1 = items.values().iterator();
/* 379 */       while (iter1.hasNext())
/*     */       {
/* 381 */         this.mDetails = ((ExtSysValueTypeEVO)iter1.next());
/*     */ 
/* 383 */         if (this.mDetails.insertPending())
/*     */         {
/* 385 */           somethingChanged = true;
/* 386 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 389 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 391 */         somethingChanged = true;
/* 392 */         doStore();
/*     */       }
/*     */ 
/* 403 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 407 */       throw handleSQLException("delete from EXT_SYS_VALUE_TYPE where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND VALUE_TYPE_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 411 */       if (deleteStmt != null)
/*     */       {
/* 413 */         closeStatement(deleteStmt);
/* 414 */         closeConnection();
/*     */       }
/*     */ 
/* 417 */       this.mDetails = null;
/*     */ 
/* 419 */       if ((somethingChanged) && 
/* 420 */         (timer != null))
/* 421 */         timer.logDebug("update", "collection"); 
/* 421 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ExternalSystemPK entityPK, Collection owners, String dependants)
/*     */   {
/* 455 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 457 */     PreparedStatement stmt = null;
/* 458 */     ResultSet resultSet = null;
/*     */ 
/* 460 */     int itemCount = 0;
/*     */ 
/* 462 */     ExtSysLedgerEVO owningEVO = null;
/* 463 */     Iterator ownersIter = owners.iterator();
/* 464 */     while (ownersIter.hasNext())
/*     */     {
/* 466 */       owningEVO = (ExtSysLedgerEVO)ownersIter.next();
/* 467 */       owningEVO.setExtSysValueTypesAllItemsLoaded(true);
/*     */     }
/* 469 */     ownersIter = owners.iterator();
/* 470 */     owningEVO = (ExtSysLedgerEVO)ownersIter.next();
/* 471 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 475 */       stmt = getConnection().prepareStatement("select EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID,EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID,EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID,EXT_SYS_VALUE_TYPE.VALUE_TYPE_VIS_ID,EXT_SYS_VALUE_TYPE.DESCRIPTION from EXT_SYS_VALUE_TYPE,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID ,EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID ,EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID ,EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID ,EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID ,EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID ,EXT_SYS_VALUE_TYPE.VALUE_TYPE_VIS_ID");
/*     */ 
/* 477 */       int col = 1;
/* 478 */       stmt.setInt(col++, entityPK.getExternalSystemId());
/*     */ 
/* 480 */       resultSet = stmt.executeQuery();
/*     */ 
/* 483 */       while (resultSet.next())
/*     */       {
/* 485 */         itemCount++;
/* 486 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 491 */         while ((this.mDetails.getExternalSystemId() != owningEVO.getExternalSystemId()) || (!this.mDetails.getCompanyVisId().equals(owningEVO.getCompanyVisId())) || (!this.mDetails.getLedgerVisId().equals(owningEVO.getLedgerVisId())))
/*     */         {
/* 497 */           if (!ownersIter.hasNext())
/*     */           {
/* 499 */             this._log.debug("bulkGetAll", "can't find owning [ExternalSystemId=" + this.mDetails.getExternalSystemId() + "CompanyVisId=" + this.mDetails.getCompanyVisId() + "LedgerVisId=" + this.mDetails.getLedgerVisId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 505 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 506 */             ownersIter = owners.iterator();
/* 507 */             while (ownersIter.hasNext())
/*     */             {
/* 509 */               owningEVO = (ExtSysLedgerEVO)ownersIter.next();
/* 510 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 512 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 514 */           owningEVO = (ExtSysLedgerEVO)ownersIter.next();
/*     */         }
/* 516 */         if (owningEVO.getExtSysValueTypes() == null)
/*     */         {
/* 518 */           theseItems = new ArrayList();
/* 519 */           owningEVO.setExtSysValueTypes(theseItems);
/* 520 */           owningEVO.setExtSysValueTypesAllItemsLoaded(true);
/*     */         }
/* 522 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 525 */       if (timer != null) {
/* 526 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 531 */       throw handleSQLException("select EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID,EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID,EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID,EXT_SYS_VALUE_TYPE.VALUE_TYPE_VIS_ID,EXT_SYS_VALUE_TYPE.DESCRIPTION from EXT_SYS_VALUE_TYPE,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID ,EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID ,EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID ,EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID ,EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID ,EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID ,EXT_SYS_VALUE_TYPE.VALUE_TYPE_VIS_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 535 */       closeResultSet(resultSet);
/* 536 */       closeStatement(stmt);
/* 537 */       closeConnection();
/*     */ 
/* 539 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectExternalSystemId, String selectCompanyVisId, String selectLedgerVisId, String dependants, Collection currentList)
/*     */   {
/* 570 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 571 */     PreparedStatement stmt = null;
/* 572 */     ResultSet resultSet = null;
/*     */ 
/* 574 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 578 */       stmt = getConnection().prepareStatement("select EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID,EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID,EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID,EXT_SYS_VALUE_TYPE.VALUE_TYPE_VIS_ID,EXT_SYS_VALUE_TYPE.DESCRIPTION from EXT_SYS_VALUE_TYPE where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ");
/*     */ 
/* 580 */       int col = 1;
/* 581 */       stmt.setInt(col++, selectExternalSystemId);
/* 582 */       stmt.setString(col++, selectCompanyVisId);
/* 583 */       stmt.setString(col++, selectLedgerVisId);
/*     */ 
/* 585 */       resultSet = stmt.executeQuery();
/*     */ 
/* 587 */       while (resultSet.next())
/*     */       {
/* 589 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 592 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 595 */       if (currentList != null)
/*     */       {
/* 598 */         ListIterator iter = items.listIterator();
/* 599 */         ExtSysValueTypeEVO currentEVO = null;
/* 600 */         ExtSysValueTypeEVO newEVO = null;
/* 601 */         while (iter.hasNext())
/*     */         {
/* 603 */           newEVO = (ExtSysValueTypeEVO)iter.next();
/* 604 */           Iterator iter2 = currentList.iterator();
/* 605 */           while (iter2.hasNext())
/*     */           {
/* 607 */             currentEVO = (ExtSysValueTypeEVO)iter2.next();
/* 608 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 610 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 616 */         Iterator iter2 = currentList.iterator();
/* 617 */         while (iter2.hasNext())
/*     */         {
/* 619 */           currentEVO = (ExtSysValueTypeEVO)iter2.next();
/* 620 */           if (currentEVO.insertPending()) {
/* 621 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 625 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 629 */       throw handleSQLException("select EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID,EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID,EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID,EXT_SYS_VALUE_TYPE.VALUE_TYPE_VIS_ID,EXT_SYS_VALUE_TYPE.DESCRIPTION from EXT_SYS_VALUE_TYPE where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 633 */       closeResultSet(resultSet);
/* 634 */       closeStatement(stmt);
/* 635 */       closeConnection();
/*     */ 
/* 637 */       if (timer != null) {
/* 638 */         timer.logDebug("getAll", " ExternalSystemId=" + selectExternalSystemId + ",CompanyVisId=" + selectCompanyVisId + ",LedgerVisId=" + selectLedgerVisId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 645 */     return items;
/*     */   }
/*     */ 
/*     */   public ExtSysValueTypeEVO getDetails(ExternalSystemCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 659 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 662 */     if (this.mDetails == null) {
/* 663 */       doLoad(((ExtSysValueTypeCK)paramCK).getExtSysValueTypePK());
/*     */     }
/* 665 */     else if (!this.mDetails.getPK().equals(((ExtSysValueTypeCK)paramCK).getExtSysValueTypePK())) {
/* 666 */       doLoad(((ExtSysValueTypeCK)paramCK).getExtSysValueTypePK());
/*     */     }
/*     */ 
/* 669 */     ExtSysValueTypeEVO details = new ExtSysValueTypeEVO();
/* 670 */     details = this.mDetails.deepClone();
/*     */ 
/* 672 */     if (timer != null) {
/* 673 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 675 */     return details;
/*     */   }
/*     */ 
/*     */   public ExtSysValueTypeEVO getDetails(ExternalSystemCK paramCK, ExtSysValueTypeEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 681 */     ExtSysValueTypeEVO savedEVO = this.mDetails;
/* 682 */     this.mDetails = paramEVO;
/* 683 */     ExtSysValueTypeEVO newEVO = getDetails(paramCK, dependants);
/* 684 */     this.mDetails = savedEVO;
/* 685 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public ExtSysValueTypeEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 691 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 695 */     ExtSysValueTypeEVO details = this.mDetails.deepClone();
/*     */ 
/* 697 */     if (timer != null) {
/* 698 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 700 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 705 */     return "ExtSysValueType";
/*     */   }
/*     */ 
/*     */   public ExtSysValueTypeRefImpl getRef(ExtSysValueTypePK paramExtSysValueTypePK)
/*     */   {
/* 710 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 711 */     PreparedStatement stmt = null;
/* 712 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 715 */       stmt = getConnection().prepareStatement("select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID from EXT_SYS_VALUE_TYPE,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_LEDGER where 1=1 and EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID = ? and EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID = ? and EXT_SYS_VALUE_TYPE.VALUE_TYPE_VIS_ID = ? and EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_LEDGER.LEDGER_VIS_ID = EXT_SYS_COMPANY.LEDGER_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID");
/* 716 */       int col = 1;
/* 717 */       stmt.setInt(col++, paramExtSysValueTypePK.getExternalSystemId());
/* 718 */       stmt.setString(col++, paramExtSysValueTypePK.getCompanyVisId());
/* 719 */       stmt.setString(col++, paramExtSysValueTypePK.getLedgerVisId());
/* 720 */       stmt.setString(col++, paramExtSysValueTypePK.getValueTypeVisId());
/*     */ 
/* 722 */       resultSet = stmt.executeQuery();
/*     */ 
/* 724 */       if (!resultSet.next()) {
/* 725 */         throw new RuntimeException(getEntityName() + " getRef " + paramExtSysValueTypePK + " not found");
/*     */       }
/* 727 */       col = 2;
/* 728 */       ExternalSystemPK newExternalSystemPK = new ExternalSystemPK(resultSet.getInt(col++));
/*     */ 
/* 732 */       ExtSysCompanyPK newExtSysCompanyPK = new ExtSysCompanyPK(resultSet.getInt(col++), resultSet.getString(col++));
/*     */ 
/* 737 */       ExtSysLedgerPK newExtSysLedgerPK = new ExtSysLedgerPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++));
/*     */ 
/* 743 */       String textExtSysValueType = "";
/* 744 */       ExtSysValueTypeCK ckExtSysValueType = new ExtSysValueTypeCK(newExternalSystemPK, newExtSysCompanyPK, newExtSysLedgerPK, paramExtSysValueTypePK);
/*     */ 
/* 751 */       ExtSysValueTypeRefImpl localExtSysValueTypeRefImpl = new ExtSysValueTypeRefImpl(ckExtSysValueType, textExtSysValueType);
/*     */       return localExtSysValueTypeRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 756 */       throw handleSQLException(paramExtSysValueTypePK, "select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID from EXT_SYS_VALUE_TYPE,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_LEDGER where 1=1 and EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID = ? and EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID = ? and EXT_SYS_VALUE_TYPE.VALUE_TYPE_VIS_ID = ? and EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_LEDGER.LEDGER_VIS_ID = EXT_SYS_COMPANY.LEDGER_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 760 */       closeResultSet(resultSet);
/* 761 */       closeStatement(stmt);
/* 762 */       closeConnection();
/*     */ 
/* 764 */       if (timer != null)
/* 765 */         timer.logDebug("getRef", paramExtSysValueTypePK); 
/* 765 */     }
/*     */   }
/*     */ 
/*     */   public void insert(ExtSysValueTypeEVO evo)
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 782 */     setDetails(evo);
/* 783 */     doCreate();
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extsys.ExtSysValueTypeDAO
 * JD-Core Version:    0.6.0
 */