/*     */ package com.cedar.cp.ejb.impl.udeflookup;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.udeflookup.UdefLookupCK;
/*     */ import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefCK;
/*     */ import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefPK;
/*     */ import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefRefImpl;
/*     */ import com.cedar.cp.dto.udeflookup.UdefLookupPK;
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
/*     */ public class UdefLookupColumnDefDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_INDEX,UDEF_LOOKUP_COLUMN_DEF.NAME,UDEF_LOOKUP_COLUMN_DEF.TITLE,UDEF_LOOKUP_COLUMN_DEF.TYPE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_SIZE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DP,UDEF_LOOKUP_COLUMN_DEF.OPTIONAL";
/*     */   protected static final String SQL_LOAD = " from UDEF_LOOKUP_COLUMN_DEF where    UDEF_LOOKUP_ID = ? AND COLUMN_DEF_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into UDEF_LOOKUP_COLUMN_DEF ( UDEF_LOOKUP_ID,COLUMN_DEF_ID,COLUMN_DEF_INDEX,NAME,TITLE,TYPE,COLUMN_SIZE,COLUMN_DP,OPTIONAL) values ( ?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update UDEF_LOOKUP_COLUMN_DEF set COLUMN_DEF_INDEX = ?,NAME = ?,TITLE = ?,TYPE = ?,COLUMN_SIZE = ?,COLUMN_DP = ?,OPTIONAL = ? where    UDEF_LOOKUP_ID = ? AND COLUMN_DEF_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from UDEF_LOOKUP_COLUMN_DEF where    UDEF_LOOKUP_ID = ? AND COLUMN_DEF_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from UDEF_LOOKUP_COLUMN_DEF where 1=1 and UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID = ? order by  UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID ,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_ID";
/*     */   protected static final String SQL_GET_ALL = " from UDEF_LOOKUP_COLUMN_DEF where    UDEF_LOOKUP_ID = ? ";
/*     */   protected UdefLookupColumnDefEVO mDetails;
/*     */ 
/*     */   public UdefLookupColumnDefDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public UdefLookupColumnDefDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public UdefLookupColumnDefDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected UdefLookupColumnDefPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(UdefLookupColumnDefEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private UdefLookupColumnDefEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  92 */     int col = 1;
/*  93 */     UdefLookupColumnDefEVO evo = new UdefLookupColumnDefEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), resultSet_.getString(col++).equals("Y"));
/*     */ 
/* 105 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(UdefLookupColumnDefEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 110 */     int col = startCol_;
/* 111 */     stmt_.setInt(col++, evo_.getUdefLookupId());
/* 112 */     stmt_.setInt(col++, evo_.getColumnDefId());
/* 113 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(UdefLookupColumnDefEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 118 */     int col = startCol_;
/* 119 */     stmt_.setInt(col++, evo_.getColumnDefIndex());
/* 120 */     stmt_.setString(col++, evo_.getName());
/* 121 */     stmt_.setString(col++, evo_.getTitle());
/* 122 */     stmt_.setInt(col++, evo_.getType());
/* 123 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getColumnSize());
/* 124 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getColumnDp());
/* 125 */     if (evo_.getOptional())
/* 126 */       stmt_.setString(col++, "Y");
/*     */     else
/* 128 */       stmt_.setString(col++, " ");
/* 129 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(UdefLookupColumnDefPK pk)
/*     */     throws ValidationException
/*     */   {
/* 146 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 148 */     PreparedStatement stmt = null;
/* 149 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 153 */       stmt = getConnection().prepareStatement("select UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_INDEX,UDEF_LOOKUP_COLUMN_DEF.NAME,UDEF_LOOKUP_COLUMN_DEF.TITLE,UDEF_LOOKUP_COLUMN_DEF.TYPE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_SIZE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DP,UDEF_LOOKUP_COLUMN_DEF.OPTIONAL from UDEF_LOOKUP_COLUMN_DEF where    UDEF_LOOKUP_ID = ? AND COLUMN_DEF_ID = ? ");
/*     */ 
/* 156 */       int col = 1;
/* 157 */       stmt.setInt(col++, pk.getUdefLookupId());
/* 158 */       stmt.setInt(col++, pk.getColumnDefId());
/*     */ 
/* 160 */       resultSet = stmt.executeQuery();
/*     */ 
/* 162 */       if (!resultSet.next()) {
/* 163 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 166 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 167 */       if (this.mDetails.isModified())
/* 168 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 172 */       throw handleSQLException(pk, "select UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_INDEX,UDEF_LOOKUP_COLUMN_DEF.NAME,UDEF_LOOKUP_COLUMN_DEF.TITLE,UDEF_LOOKUP_COLUMN_DEF.TYPE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_SIZE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DP,UDEF_LOOKUP_COLUMN_DEF.OPTIONAL from UDEF_LOOKUP_COLUMN_DEF where    UDEF_LOOKUP_ID = ? AND COLUMN_DEF_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 176 */       closeResultSet(resultSet);
/* 177 */       closeStatement(stmt);
/* 178 */       closeConnection();
/*     */ 
/* 180 */       if (timer != null)
/* 181 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 216 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 217 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 221 */       stmt = getConnection().prepareStatement("insert into UDEF_LOOKUP_COLUMN_DEF ( UDEF_LOOKUP_ID,COLUMN_DEF_ID,COLUMN_DEF_INDEX,NAME,TITLE,TYPE,COLUMN_SIZE,COLUMN_DP,OPTIONAL) values ( ?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 224 */       int col = 1;
/* 225 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 226 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 229 */       int resultCount = stmt.executeUpdate();
/* 230 */       if (resultCount != 1)
/*     */       {
/* 232 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 235 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 239 */       throw handleSQLException(this.mDetails.getPK(), "insert into UDEF_LOOKUP_COLUMN_DEF ( UDEF_LOOKUP_ID,COLUMN_DEF_ID,COLUMN_DEF_INDEX,NAME,TITLE,TYPE,COLUMN_SIZE,COLUMN_DP,OPTIONAL) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 243 */       closeStatement(stmt);
/* 244 */       closeConnection();
/*     */ 
/* 246 */       if (timer != null)
/* 247 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 274 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 278 */     PreparedStatement stmt = null;
/*     */ 
/* 280 */     boolean mainChanged = this.mDetails.isModified();
/* 281 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 284 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 286 */         stmt = getConnection().prepareStatement("update UDEF_LOOKUP_COLUMN_DEF set COLUMN_DEF_INDEX = ?,NAME = ?,TITLE = ?,TYPE = ?,COLUMN_SIZE = ?,COLUMN_DP = ?,OPTIONAL = ? where    UDEF_LOOKUP_ID = ? AND COLUMN_DEF_ID = ? ");
/*     */ 
/* 289 */         int col = 1;
/* 290 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 291 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 294 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 297 */         if (resultCount != 1) {
/* 298 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 301 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 310 */       throw handleSQLException(getPK(), "update UDEF_LOOKUP_COLUMN_DEF set COLUMN_DEF_INDEX = ?,NAME = ?,TITLE = ?,TYPE = ?,COLUMN_SIZE = ?,COLUMN_DP = ?,OPTIONAL = ? where    UDEF_LOOKUP_ID = ? AND COLUMN_DEF_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 314 */       closeStatement(stmt);
/* 315 */       closeConnection();
/*     */ 
/* 317 */       if ((timer != null) && (
/* 318 */         (mainChanged) || (dependantChanged)))
/* 319 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 339 */     if (items == null) {
/* 340 */       return false;
/*     */     }
/* 342 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 343 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 345 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 350 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 351 */       while (iter2.hasNext())
/*     */       {
/* 353 */         this.mDetails = ((UdefLookupColumnDefEVO)iter2.next());
/*     */ 
/* 356 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 358 */         somethingChanged = true;
/*     */ 
/* 361 */         if (deleteStmt == null) {
/* 362 */           deleteStmt = getConnection().prepareStatement("delete from UDEF_LOOKUP_COLUMN_DEF where    UDEF_LOOKUP_ID = ? AND COLUMN_DEF_ID = ? ");
/*     */         }
/*     */ 
/* 365 */         int col = 1;
/* 366 */         deleteStmt.setInt(col++, this.mDetails.getUdefLookupId());
/* 367 */         deleteStmt.setInt(col++, this.mDetails.getColumnDefId());
/*     */ 
/* 369 */         if (this._log.isDebugEnabled()) {
/* 370 */           this._log.debug("update", "UdefLookupColumnDef deleting UdefLookupId=" + this.mDetails.getUdefLookupId() + ",ColumnDefId=" + this.mDetails.getColumnDefId());
/*     */         }
/*     */ 
/* 376 */         deleteStmt.addBatch();
/*     */ 
/* 379 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 384 */       if (deleteStmt != null)
/*     */       {
/* 386 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 388 */         deleteStmt.executeBatch();
/*     */ 
/* 390 */         if (timer2 != null) {
/* 391 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 395 */       Iterator iter1 = items.values().iterator();
/* 396 */       while (iter1.hasNext())
/*     */       {
/* 398 */         this.mDetails = ((UdefLookupColumnDefEVO)iter1.next());
/*     */ 
/* 400 */         if (this.mDetails.insertPending())
/*     */         {
/* 402 */           somethingChanged = true;
/* 403 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 406 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 408 */         somethingChanged = true;
/* 409 */         doStore();
/*     */       }
/*     */ 
/* 420 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 424 */       throw handleSQLException("delete from UDEF_LOOKUP_COLUMN_DEF where    UDEF_LOOKUP_ID = ? AND COLUMN_DEF_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 428 */       if (deleteStmt != null)
/*     */       {
/* 430 */         closeStatement(deleteStmt);
/* 431 */         closeConnection();
/*     */       }
/*     */ 
/* 434 */       this.mDetails = null;
/*     */ 
/* 436 */       if ((somethingChanged) && 
/* 437 */         (timer != null))
/* 438 */         timer.logDebug("update", "collection"); 
/* 438 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(UdefLookupPK entityPK, UdefLookupEVO owningEVO, String dependants)
/*     */   {
/* 458 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 460 */     PreparedStatement stmt = null;
/* 461 */     ResultSet resultSet = null;
/*     */ 
/* 463 */     int itemCount = 0;
/*     */ 
/* 465 */     Collection theseItems = new ArrayList();
/* 466 */     owningEVO.setColumnDef(theseItems);
/* 467 */     owningEVO.setColumnDefAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 471 */       stmt = getConnection().prepareStatement("select UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_INDEX,UDEF_LOOKUP_COLUMN_DEF.NAME,UDEF_LOOKUP_COLUMN_DEF.TITLE,UDEF_LOOKUP_COLUMN_DEF.TYPE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_SIZE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DP,UDEF_LOOKUP_COLUMN_DEF.OPTIONAL from UDEF_LOOKUP_COLUMN_DEF where 1=1 and UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID = ? order by  UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID ,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_ID");
/*     */ 
/* 473 */       int col = 1;
/* 474 */       stmt.setInt(col++, entityPK.getUdefLookupId());
/*     */ 
/* 476 */       resultSet = stmt.executeQuery();
/*     */ 
/* 479 */       while (resultSet.next())
/*     */       {
/* 481 */         itemCount++;
/* 482 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 484 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 487 */       if (timer != null) {
/* 488 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 493 */       throw handleSQLException("select UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_INDEX,UDEF_LOOKUP_COLUMN_DEF.NAME,UDEF_LOOKUP_COLUMN_DEF.TITLE,UDEF_LOOKUP_COLUMN_DEF.TYPE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_SIZE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DP,UDEF_LOOKUP_COLUMN_DEF.OPTIONAL from UDEF_LOOKUP_COLUMN_DEF where 1=1 and UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID = ? order by  UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID ,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 497 */       closeResultSet(resultSet);
/* 498 */       closeStatement(stmt);
/* 499 */       closeConnection();
/*     */ 
/* 501 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectUdefLookupId, String dependants, Collection currentList)
/*     */   {
/* 526 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 527 */     PreparedStatement stmt = null;
/* 528 */     ResultSet resultSet = null;
/*     */ 
/* 530 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 534 */       stmt = getConnection().prepareStatement("select UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_INDEX,UDEF_LOOKUP_COLUMN_DEF.NAME,UDEF_LOOKUP_COLUMN_DEF.TITLE,UDEF_LOOKUP_COLUMN_DEF.TYPE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_SIZE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DP,UDEF_LOOKUP_COLUMN_DEF.OPTIONAL from UDEF_LOOKUP_COLUMN_DEF where    UDEF_LOOKUP_ID = ? ");
/*     */ 
/* 536 */       int col = 1;
/* 537 */       stmt.setInt(col++, selectUdefLookupId);
/*     */ 
/* 539 */       resultSet = stmt.executeQuery();
/*     */ 
/* 541 */       while (resultSet.next())
/*     */       {
/* 543 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 546 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 549 */       if (currentList != null)
/*     */       {
/* 552 */         ListIterator iter = items.listIterator();
/* 553 */         UdefLookupColumnDefEVO currentEVO = null;
/* 554 */         UdefLookupColumnDefEVO newEVO = null;
/* 555 */         while (iter.hasNext())
/*     */         {
/* 557 */           newEVO = (UdefLookupColumnDefEVO)iter.next();
/* 558 */           Iterator iter2 = currentList.iterator();
/* 559 */           while (iter2.hasNext())
/*     */           {
/* 561 */             currentEVO = (UdefLookupColumnDefEVO)iter2.next();
/* 562 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 564 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 570 */         Iterator iter2 = currentList.iterator();
/* 571 */         while (iter2.hasNext())
/*     */         {
/* 573 */           currentEVO = (UdefLookupColumnDefEVO)iter2.next();
/* 574 */           if (currentEVO.insertPending()) {
/* 575 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 579 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 583 */       throw handleSQLException("select UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_ID,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_INDEX,UDEF_LOOKUP_COLUMN_DEF.NAME,UDEF_LOOKUP_COLUMN_DEF.TITLE,UDEF_LOOKUP_COLUMN_DEF.TYPE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_SIZE,UDEF_LOOKUP_COLUMN_DEF.COLUMN_DP,UDEF_LOOKUP_COLUMN_DEF.OPTIONAL from UDEF_LOOKUP_COLUMN_DEF where    UDEF_LOOKUP_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 587 */       closeResultSet(resultSet);
/* 588 */       closeStatement(stmt);
/* 589 */       closeConnection();
/*     */ 
/* 591 */       if (timer != null) {
/* 592 */         timer.logDebug("getAll", " UdefLookupId=" + selectUdefLookupId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 597 */     return items;
/*     */   }
/*     */ 
/*     */   public UdefLookupColumnDefEVO getDetails(UdefLookupCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 611 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 614 */     if (this.mDetails == null) {
/* 615 */       doLoad(((UdefLookupColumnDefCK)paramCK).getUdefLookupColumnDefPK());
/*     */     }
/* 617 */     else if (!this.mDetails.getPK().equals(((UdefLookupColumnDefCK)paramCK).getUdefLookupColumnDefPK())) {
/* 618 */       doLoad(((UdefLookupColumnDefCK)paramCK).getUdefLookupColumnDefPK());
/*     */     }
/*     */ 
/* 621 */     UdefLookupColumnDefEVO details = new UdefLookupColumnDefEVO();
/* 622 */     details = this.mDetails.deepClone();
/*     */ 
/* 624 */     if (timer != null) {
/* 625 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 627 */     return details;
/*     */   }
/*     */ 
/*     */   public UdefLookupColumnDefEVO getDetails(UdefLookupCK paramCK, UdefLookupColumnDefEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 633 */     UdefLookupColumnDefEVO savedEVO = this.mDetails;
/* 634 */     this.mDetails = paramEVO;
/* 635 */     UdefLookupColumnDefEVO newEVO = getDetails(paramCK, dependants);
/* 636 */     this.mDetails = savedEVO;
/* 637 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public UdefLookupColumnDefEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 643 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 647 */     UdefLookupColumnDefEVO details = this.mDetails.deepClone();
/*     */ 
/* 649 */     if (timer != null) {
/* 650 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 652 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 657 */     return "UdefLookupColumnDef";
/*     */   }
/*     */ 
/*     */   public UdefLookupColumnDefRefImpl getRef(UdefLookupColumnDefPK paramUdefLookupColumnDefPK)
/*     */   {
/* 662 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 663 */     PreparedStatement stmt = null;
/* 664 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 667 */       stmt = getConnection().prepareStatement("select 0,UDEF_LOOKUP.UDEF_LOOKUP_ID from UDEF_LOOKUP_COLUMN_DEF,UDEF_LOOKUP where 1=1 and UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID = ? and UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_ID = ? and UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID = UDEF_LOOKUP.UDEF_LOOKUP_ID");
/* 668 */       int col = 1;
/* 669 */       stmt.setInt(col++, paramUdefLookupColumnDefPK.getUdefLookupId());
/* 670 */       stmt.setInt(col++, paramUdefLookupColumnDefPK.getColumnDefId());
/*     */ 
/* 672 */       resultSet = stmt.executeQuery();
/*     */ 
/* 674 */       if (!resultSet.next()) {
/* 675 */         throw new RuntimeException(getEntityName() + " getRef " + paramUdefLookupColumnDefPK + " not found");
/*     */       }
/* 677 */       col = 2;
/* 678 */       UdefLookupPK newUdefLookupPK = new UdefLookupPK(resultSet.getInt(col++));
/*     */ 
/* 682 */       String textUdefLookupColumnDef = "";
/* 683 */       UdefLookupColumnDefCK ckUdefLookupColumnDef = new UdefLookupColumnDefCK(newUdefLookupPK, paramUdefLookupColumnDefPK);
/*     */ 
/* 688 */       UdefLookupColumnDefRefImpl localUdefLookupColumnDefRefImpl = new UdefLookupColumnDefRefImpl(ckUdefLookupColumnDef, textUdefLookupColumnDef);
/*     */       return localUdefLookupColumnDefRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 693 */       throw handleSQLException(paramUdefLookupColumnDefPK, "select 0,UDEF_LOOKUP.UDEF_LOOKUP_ID from UDEF_LOOKUP_COLUMN_DEF,UDEF_LOOKUP where 1=1 and UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID = ? and UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_ID = ? and UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID = UDEF_LOOKUP.UDEF_LOOKUP_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 697 */       closeResultSet(resultSet);
/* 698 */       closeStatement(stmt);
/* 699 */       closeConnection();
/*     */ 
/* 701 */       if (timer != null)
/* 702 */         timer.logDebug("getRef", paramUdefLookupColumnDefPK); 
/* 702 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.udeflookup.UdefLookupColumnDefDAO
 * JD-Core Version:    0.6.0
 */