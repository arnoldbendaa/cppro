/*     */ package com.cedar.cp.ejb.impl.dimension;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.dimension.DimensionCK;
/*     */ import com.cedar.cp.dto.dimension.DimensionPK;
/*     */ import com.cedar.cp.dto.dimension.SecurityRangePK;
/*     */ import com.cedar.cp.dto.dimension.SecurityRangeRowCK;
/*     */ import com.cedar.cp.dto.dimension.SecurityRangeRowPK;
/*     */ import com.cedar.cp.dto.dimension.SecurityRangeRowRefImpl;
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
/*     */ public class SecurityRangeRowDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select SECURITY_RANGE_ROW.SECURITY_RANGE_ROW_ID,SECURITY_RANGE_ROW.SECURITY_RANGE_ID,SECURITY_RANGE_ROW.SEQUENCE,SECURITY_RANGE_ROW.FROM_ID,SECURITY_RANGE_ROW.TO_ID,SECURITY_RANGE_ROW.VERSION_NUM,SECURITY_RANGE_ROW.UPDATED_BY_USER_ID,SECURITY_RANGE_ROW.UPDATED_TIME,SECURITY_RANGE_ROW.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from SECURITY_RANGE_ROW where    SECURITY_RANGE_ROW_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into SECURITY_RANGE_ROW ( SECURITY_RANGE_ROW_ID,SECURITY_RANGE_ID,SEQUENCE,FROM_ID,TO_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update SECURITY_RANGE_ROW set SECURITY_RANGE_ID = ?,SEQUENCE = ?,FROM_ID = ?,TO_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_RANGE_ROW_ID = ? AND VERSION_NUM = ?";
/*     */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from SECURITY_RANGE_ROW where SECURITY_RANGE_ROW_ID = ?";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from SECURITY_RANGE_ROW where    SECURITY_RANGE_ROW_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from SECURITY_RANGE_ROW,SECURITY_RANGE where 1=1 and SECURITY_RANGE_ROW.SECURITY_RANGE_ID = SECURITY_RANGE.SECURITY_RANGE_ID and SECURITY_RANGE.DIMENSION_ID = ? order by  SECURITY_RANGE_ROW.SECURITY_RANGE_ID ,SECURITY_RANGE_ROW.SECURITY_RANGE_ROW_ID";
/*     */   protected static final String SQL_GET_ALL = " from SECURITY_RANGE_ROW where    SECURITY_RANGE_ID = ? ";
/*     */   protected SecurityRangeRowEVO mDetails;
/*     */ 
/*     */   public SecurityRangeRowDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public SecurityRangeRowDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SecurityRangeRowDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected SecurityRangeRowPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(SecurityRangeRowEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private SecurityRangeRowEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  92 */     int col = 1;
/*  93 */     SecurityRangeRowEVO evo = new SecurityRangeRowEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++));
/*     */ 
/* 102 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 103 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 104 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 105 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(SecurityRangeRowEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 110 */     int col = startCol_;
/* 111 */     stmt_.setInt(col++, evo_.getSecurityRangeRowId());
/* 112 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(SecurityRangeRowEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 117 */     int col = startCol_;
/* 118 */     stmt_.setInt(col++, evo_.getSecurityRangeId());
/* 119 */     stmt_.setInt(col++, evo_.getSequence());
/* 120 */     stmt_.setString(col++, evo_.getFromId());
/* 121 */     stmt_.setString(col++, evo_.getToId());
/* 122 */     stmt_.setInt(col++, evo_.getVersionNum());
/* 123 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 124 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 125 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 126 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(SecurityRangeRowPK pk)
/*     */     throws ValidationException
/*     */   {
/* 142 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 144 */     PreparedStatement stmt = null;
/* 145 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 149 */       stmt = getConnection().prepareStatement("select SECURITY_RANGE_ROW.SECURITY_RANGE_ROW_ID,SECURITY_RANGE_ROW.SECURITY_RANGE_ID,SECURITY_RANGE_ROW.SEQUENCE,SECURITY_RANGE_ROW.FROM_ID,SECURITY_RANGE_ROW.TO_ID,SECURITY_RANGE_ROW.VERSION_NUM,SECURITY_RANGE_ROW.UPDATED_BY_USER_ID,SECURITY_RANGE_ROW.UPDATED_TIME,SECURITY_RANGE_ROW.CREATED_TIME from SECURITY_RANGE_ROW where    SECURITY_RANGE_ROW_ID = ? ");
/*     */ 
/* 152 */       int col = 1;
/* 153 */       stmt.setInt(col++, pk.getSecurityRangeRowId());
/*     */ 
/* 155 */       resultSet = stmt.executeQuery();
/*     */ 
/* 157 */       if (!resultSet.next()) {
/* 158 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 161 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 162 */       if (this.mDetails.isModified())
/* 163 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 167 */       throw handleSQLException(pk, "select SECURITY_RANGE_ROW.SECURITY_RANGE_ROW_ID,SECURITY_RANGE_ROW.SECURITY_RANGE_ID,SECURITY_RANGE_ROW.SEQUENCE,SECURITY_RANGE_ROW.FROM_ID,SECURITY_RANGE_ROW.TO_ID,SECURITY_RANGE_ROW.VERSION_NUM,SECURITY_RANGE_ROW.UPDATED_BY_USER_ID,SECURITY_RANGE_ROW.UPDATED_TIME,SECURITY_RANGE_ROW.CREATED_TIME from SECURITY_RANGE_ROW where    SECURITY_RANGE_ROW_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 171 */       closeResultSet(resultSet);
/* 172 */       closeStatement(stmt);
/* 173 */       closeConnection();
/*     */ 
/* 175 */       if (timer != null)
/* 176 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 211 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 212 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 217 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 218 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 219 */       stmt = getConnection().prepareStatement("insert into SECURITY_RANGE_ROW ( SECURITY_RANGE_ROW_ID,SECURITY_RANGE_ID,SEQUENCE,FROM_ID,TO_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 222 */       int col = 1;
/* 223 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 224 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 227 */       int resultCount = stmt.executeUpdate();
/* 228 */       if (resultCount != 1)
/*     */       {
/* 230 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 233 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 237 */       throw handleSQLException(this.mDetails.getPK(), "insert into SECURITY_RANGE_ROW ( SECURITY_RANGE_ROW_ID,SECURITY_RANGE_ID,SEQUENCE,FROM_ID,TO_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 241 */       closeStatement(stmt);
/* 242 */       closeConnection();
/*     */ 
/* 244 */       if (timer != null)
/* 245 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 273 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 277 */     PreparedStatement stmt = null;
/*     */ 
/* 279 */     boolean mainChanged = this.mDetails.isModified();
/* 280 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 283 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 286 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*     */ 
/* 289 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 290 */         stmt = getConnection().prepareStatement("update SECURITY_RANGE_ROW set SECURITY_RANGE_ID = ?,SEQUENCE = ?,FROM_ID = ?,TO_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_RANGE_ROW_ID = ? AND VERSION_NUM = ?");
/*     */ 
/* 293 */         int col = 1;
/* 294 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 295 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 297 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*     */ 
/* 300 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 302 */         if (resultCount == 0) {
/* 303 */           checkVersionNum();
/*     */         }
/* 305 */         if (resultCount != 1) {
/* 306 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 309 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 318 */       throw handleSQLException(getPK(), "update SECURITY_RANGE_ROW set SECURITY_RANGE_ID = ?,SEQUENCE = ?,FROM_ID = ?,TO_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_RANGE_ROW_ID = ? AND VERSION_NUM = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 322 */       closeStatement(stmt);
/* 323 */       closeConnection();
/*     */ 
/* 325 */       if ((timer != null) && (
/* 326 */         (mainChanged) || (dependantChanged)))
/* 327 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkVersionNum()
/*     */     throws VersionValidationException
/*     */   {
/* 339 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 340 */     PreparedStatement stmt = null;
/* 341 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 345 */       stmt = getConnection().prepareStatement("select VERSION_NUM from SECURITY_RANGE_ROW where SECURITY_RANGE_ROW_ID = ?");
/*     */ 
/* 348 */       int col = 1;
/* 349 */       stmt.setInt(col++, this.mDetails.getSecurityRangeRowId());
/*     */ 
/* 352 */       resultSet = stmt.executeQuery();
/*     */ 
/* 354 */       if (!resultSet.next()) {
/* 355 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 358 */       col = 1;
/* 359 */       int dbVersionNumber = resultSet.getInt(col++);
/* 360 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/* 361 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 367 */       throw handleSQLException(getPK(), "select VERSION_NUM from SECURITY_RANGE_ROW where SECURITY_RANGE_ROW_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 371 */       closeStatement(stmt);
/* 372 */       closeResultSet(resultSet);
/*     */ 
/* 374 */       if (timer != null)
/* 375 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 393 */     if (items == null) {
/* 394 */       return false;
/*     */     }
/* 396 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 397 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 399 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 404 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 405 */       while (iter2.hasNext())
/*     */       {
/* 407 */         this.mDetails = ((SecurityRangeRowEVO)iter2.next());
/*     */ 
/* 410 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 412 */         somethingChanged = true;
/*     */ 
/* 415 */         if (deleteStmt == null) {
/* 416 */           deleteStmt = getConnection().prepareStatement("delete from SECURITY_RANGE_ROW where    SECURITY_RANGE_ROW_ID = ? ");
/*     */         }
/*     */ 
/* 419 */         int col = 1;
/* 420 */         deleteStmt.setInt(col++, this.mDetails.getSecurityRangeRowId());
/*     */ 
/* 422 */         if (this._log.isDebugEnabled()) {
/* 423 */           this._log.debug("update", "SecurityRangeRow deleting SecurityRangeRowId=" + this.mDetails.getSecurityRangeRowId());
/*     */         }
/*     */ 
/* 428 */         deleteStmt.addBatch();
/*     */ 
/* 431 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 436 */       if (deleteStmt != null)
/*     */       {
/* 438 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 440 */         deleteStmt.executeBatch();
/*     */ 
/* 442 */         if (timer2 != null) {
/* 443 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 447 */       Iterator iter1 = items.values().iterator();
/* 448 */       while (iter1.hasNext())
/*     */       {
/* 450 */         this.mDetails = ((SecurityRangeRowEVO)iter1.next());
/*     */ 
/* 452 */         if (this.mDetails.insertPending())
/*     */         {
/* 454 */           somethingChanged = true;
/* 455 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 458 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 460 */         somethingChanged = true;
/* 461 */         doStore();
/*     */       }
/*     */ 
/* 472 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 476 */       throw handleSQLException("delete from SECURITY_RANGE_ROW where    SECURITY_RANGE_ROW_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 480 */       if (deleteStmt != null)
/*     */       {
/* 482 */         closeStatement(deleteStmt);
/* 483 */         closeConnection();
/*     */       }
/*     */ 
/* 486 */       this.mDetails = null;
/*     */ 
/* 488 */       if ((somethingChanged) && 
/* 489 */         (timer != null))
/* 490 */         timer.logDebug("update", "collection"); 
/* 490 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(DimensionPK entityPK, Collection owners, String dependants)
/*     */   {
/* 513 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 515 */     PreparedStatement stmt = null;
/* 516 */     ResultSet resultSet = null;
/*     */ 
/* 518 */     int itemCount = 0;
/*     */ 
/* 520 */     SecurityRangeEVO owningEVO = null;
/* 521 */     Iterator ownersIter = owners.iterator();
/* 522 */     while (ownersIter.hasNext())
/*     */     {
/* 524 */       owningEVO = (SecurityRangeEVO)ownersIter.next();
/* 525 */       owningEVO.setRangeRowsAllItemsLoaded(true);
/*     */     }
/* 527 */     ownersIter = owners.iterator();
/* 528 */     owningEVO = (SecurityRangeEVO)ownersIter.next();
/* 529 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 533 */       stmt = getConnection().prepareStatement("select SECURITY_RANGE_ROW.SECURITY_RANGE_ROW_ID,SECURITY_RANGE_ROW.SECURITY_RANGE_ID,SECURITY_RANGE_ROW.SEQUENCE,SECURITY_RANGE_ROW.FROM_ID,SECURITY_RANGE_ROW.TO_ID,SECURITY_RANGE_ROW.VERSION_NUM,SECURITY_RANGE_ROW.UPDATED_BY_USER_ID,SECURITY_RANGE_ROW.UPDATED_TIME,SECURITY_RANGE_ROW.CREATED_TIME from SECURITY_RANGE_ROW,SECURITY_RANGE where 1=1 and SECURITY_RANGE_ROW.SECURITY_RANGE_ID = SECURITY_RANGE.SECURITY_RANGE_ID and SECURITY_RANGE.DIMENSION_ID = ? order by  SECURITY_RANGE_ROW.SECURITY_RANGE_ID ,SECURITY_RANGE_ROW.SECURITY_RANGE_ROW_ID");
/*     */ 
/* 535 */       int col = 1;
/* 536 */       stmt.setInt(col++, entityPK.getDimensionId());
/*     */ 
/* 538 */       resultSet = stmt.executeQuery();
/*     */ 
/* 541 */       while (resultSet.next())
/*     */       {
/* 543 */         itemCount++;
/* 544 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 549 */         while (this.mDetails.getSecurityRangeId() != owningEVO.getSecurityRangeId())
/*     */         {
/* 553 */           if (!ownersIter.hasNext())
/*     */           {
/* 555 */             this._log.debug("bulkGetAll", "can't find owning [SecurityRangeId=" + this.mDetails.getSecurityRangeId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 559 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 560 */             ownersIter = owners.iterator();
/* 561 */             while (ownersIter.hasNext())
/*     */             {
/* 563 */               owningEVO = (SecurityRangeEVO)ownersIter.next();
/* 564 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 566 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 568 */           owningEVO = (SecurityRangeEVO)ownersIter.next();
/*     */         }
/* 570 */         if (owningEVO.getRangeRows() == null)
/*     */         {
/* 572 */           theseItems = new ArrayList();
/* 573 */           owningEVO.setRangeRows(theseItems);
/* 574 */           owningEVO.setRangeRowsAllItemsLoaded(true);
/*     */         }
/* 576 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 579 */       if (timer != null) {
/* 580 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 585 */       throw handleSQLException("select SECURITY_RANGE_ROW.SECURITY_RANGE_ROW_ID,SECURITY_RANGE_ROW.SECURITY_RANGE_ID,SECURITY_RANGE_ROW.SEQUENCE,SECURITY_RANGE_ROW.FROM_ID,SECURITY_RANGE_ROW.TO_ID,SECURITY_RANGE_ROW.VERSION_NUM,SECURITY_RANGE_ROW.UPDATED_BY_USER_ID,SECURITY_RANGE_ROW.UPDATED_TIME,SECURITY_RANGE_ROW.CREATED_TIME from SECURITY_RANGE_ROW,SECURITY_RANGE where 1=1 and SECURITY_RANGE_ROW.SECURITY_RANGE_ID = SECURITY_RANGE.SECURITY_RANGE_ID and SECURITY_RANGE.DIMENSION_ID = ? order by  SECURITY_RANGE_ROW.SECURITY_RANGE_ID ,SECURITY_RANGE_ROW.SECURITY_RANGE_ROW_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 589 */       closeResultSet(resultSet);
/* 590 */       closeStatement(stmt);
/* 591 */       closeConnection();
/*     */ 
/* 593 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectSecurityRangeId, String dependants, Collection currentList)
/*     */   {
/* 618 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 619 */     PreparedStatement stmt = null;
/* 620 */     ResultSet resultSet = null;
/*     */ 
/* 622 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 626 */       stmt = getConnection().prepareStatement("select SECURITY_RANGE_ROW.SECURITY_RANGE_ROW_ID,SECURITY_RANGE_ROW.SECURITY_RANGE_ID,SECURITY_RANGE_ROW.SEQUENCE,SECURITY_RANGE_ROW.FROM_ID,SECURITY_RANGE_ROW.TO_ID,SECURITY_RANGE_ROW.VERSION_NUM,SECURITY_RANGE_ROW.UPDATED_BY_USER_ID,SECURITY_RANGE_ROW.UPDATED_TIME,SECURITY_RANGE_ROW.CREATED_TIME from SECURITY_RANGE_ROW where    SECURITY_RANGE_ID = ? ");
/*     */ 
/* 628 */       int col = 1;
/* 629 */       stmt.setInt(col++, selectSecurityRangeId);
/*     */ 
/* 631 */       resultSet = stmt.executeQuery();
/*     */ 
/* 633 */       while (resultSet.next())
/*     */       {
/* 635 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 638 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 641 */       if (currentList != null)
/*     */       {
/* 644 */         ListIterator iter = items.listIterator();
/* 645 */         SecurityRangeRowEVO currentEVO = null;
/* 646 */         SecurityRangeRowEVO newEVO = null;
/* 647 */         while (iter.hasNext())
/*     */         {
/* 649 */           newEVO = (SecurityRangeRowEVO)iter.next();
/* 650 */           Iterator iter2 = currentList.iterator();
/* 651 */           while (iter2.hasNext())
/*     */           {
/* 653 */             currentEVO = (SecurityRangeRowEVO)iter2.next();
/* 654 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 656 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 662 */         Iterator iter2 = currentList.iterator();
/* 663 */         while (iter2.hasNext())
/*     */         {
/* 665 */           currentEVO = (SecurityRangeRowEVO)iter2.next();
/* 666 */           if (currentEVO.insertPending()) {
/* 667 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 671 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 675 */       throw handleSQLException("select SECURITY_RANGE_ROW.SECURITY_RANGE_ROW_ID,SECURITY_RANGE_ROW.SECURITY_RANGE_ID,SECURITY_RANGE_ROW.SEQUENCE,SECURITY_RANGE_ROW.FROM_ID,SECURITY_RANGE_ROW.TO_ID,SECURITY_RANGE_ROW.VERSION_NUM,SECURITY_RANGE_ROW.UPDATED_BY_USER_ID,SECURITY_RANGE_ROW.UPDATED_TIME,SECURITY_RANGE_ROW.CREATED_TIME from SECURITY_RANGE_ROW where    SECURITY_RANGE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 679 */       closeResultSet(resultSet);
/* 680 */       closeStatement(stmt);
/* 681 */       closeConnection();
/*     */ 
/* 683 */       if (timer != null) {
/* 684 */         timer.logDebug("getAll", " SecurityRangeId=" + selectSecurityRangeId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 689 */     return items;
/*     */   }
/*     */ 
/*     */   public SecurityRangeRowEVO getDetails(DimensionCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 703 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 706 */     if (this.mDetails == null) {
/* 707 */       doLoad(((SecurityRangeRowCK)paramCK).getSecurityRangeRowPK());
/*     */     }
/* 709 */     else if (!this.mDetails.getPK().equals(((SecurityRangeRowCK)paramCK).getSecurityRangeRowPK())) {
/* 710 */       doLoad(((SecurityRangeRowCK)paramCK).getSecurityRangeRowPK());
/*     */     }
/*     */ 
/* 713 */     SecurityRangeRowEVO details = new SecurityRangeRowEVO();
/* 714 */     details = this.mDetails.deepClone();
/*     */ 
/* 716 */     if (timer != null) {
/* 717 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 719 */     return details;
/*     */   }
/*     */ 
/*     */   public SecurityRangeRowEVO getDetails(DimensionCK paramCK, SecurityRangeRowEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 725 */     SecurityRangeRowEVO savedEVO = this.mDetails;
/* 726 */     this.mDetails = paramEVO;
/* 727 */     SecurityRangeRowEVO newEVO = getDetails(paramCK, dependants);
/* 728 */     this.mDetails = savedEVO;
/* 729 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public SecurityRangeRowEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 735 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 739 */     SecurityRangeRowEVO details = this.mDetails.deepClone();
/*     */ 
/* 741 */     if (timer != null) {
/* 742 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 744 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 749 */     return "SecurityRangeRow";
/*     */   }
/*     */ 
/*     */   public SecurityRangeRowRefImpl getRef(SecurityRangeRowPK paramSecurityRangeRowPK)
/*     */   {
/* 754 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 755 */     PreparedStatement stmt = null;
/* 756 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 759 */       stmt = getConnection().prepareStatement("select 0,DIMENSION.DIMENSION_ID,SECURITY_RANGE.SECURITY_RANGE_ID from SECURITY_RANGE_ROW,DIMENSION,SECURITY_RANGE where 1=1 and SECURITY_RANGE_ROW.SECURITY_RANGE_ROW_ID = ? and SECURITY_RANGE_ROW.SECURITY_RANGE_ID = SECURITY_RANGE.SECURITY_RANGE_ID and SECURITY_RANGE.SECURITY_RANGE_ID = DIMENSION.SECURITY_RANGE_ID");
/* 760 */       int col = 1;
/* 761 */       stmt.setInt(col++, paramSecurityRangeRowPK.getSecurityRangeRowId());
/*     */ 
/* 763 */       resultSet = stmt.executeQuery();
/*     */ 
/* 765 */       if (!resultSet.next()) {
/* 766 */         throw new RuntimeException(getEntityName() + " getRef " + paramSecurityRangeRowPK + " not found");
/*     */       }
/* 768 */       col = 2;
/* 769 */       DimensionPK newDimensionPK = new DimensionPK(resultSet.getInt(col++));
/*     */ 
/* 773 */       SecurityRangePK newSecurityRangePK = new SecurityRangePK(resultSet.getInt(col++));
/*     */ 
/* 777 */       String textSecurityRangeRow = "";
/* 778 */       SecurityRangeRowCK ckSecurityRangeRow = new SecurityRangeRowCK(newDimensionPK, newSecurityRangePK, paramSecurityRangeRowPK);
/*     */ 
/* 784 */       SecurityRangeRowRefImpl localSecurityRangeRowRefImpl = new SecurityRangeRowRefImpl(ckSecurityRangeRow, textSecurityRangeRow);
/*     */       return localSecurityRangeRowRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 789 */       throw handleSQLException(paramSecurityRangeRowPK, "select 0,DIMENSION.DIMENSION_ID,SECURITY_RANGE.SECURITY_RANGE_ID from SECURITY_RANGE_ROW,DIMENSION,SECURITY_RANGE where 1=1 and SECURITY_RANGE_ROW.SECURITY_RANGE_ROW_ID = ? and SECURITY_RANGE_ROW.SECURITY_RANGE_ID = SECURITY_RANGE.SECURITY_RANGE_ID and SECURITY_RANGE.SECURITY_RANGE_ID = DIMENSION.SECURITY_RANGE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 793 */       closeResultSet(resultSet);
/* 794 */       closeStatement(stmt);
/* 795 */       closeConnection();
/*     */ 
/* 797 */       if (timer != null)
/* 798 */         timer.logDebug("getRef", paramSecurityRangeRowPK); 
/* 798 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.dimension.SecurityRangeRowDAO
 * JD-Core Version:    0.6.0
 */