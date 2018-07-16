/*     */ package com.cedar.cp.ejb.impl.task.group;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.task.group.TaskGroupCK;
/*     */ import com.cedar.cp.dto.task.group.TaskGroupPK;
/*     */ import com.cedar.cp.dto.task.group.TgRowPK;
/*     */ import com.cedar.cp.dto.task.group.TgRowParamCK;
/*     */ import com.cedar.cp.dto.task.group.TgRowParamPK;
/*     */ import com.cedar.cp.dto.task.group.TgRowParamRefImpl;
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
/*     */ public class TgRowParamDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select TG_ROW_PARAM.TG_ROW_PARAM_ID,TG_ROW_PARAM.TG_ROW_ID,TG_ROW_PARAM.KEY,TG_ROW_PARAM.PARAM,TG_ROW_PARAM.UPDATED_BY_USER_ID,TG_ROW_PARAM.UPDATED_TIME,TG_ROW_PARAM.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from TG_ROW_PARAM where    TG_ROW_PARAM_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into TG_ROW_PARAM ( TG_ROW_PARAM_ID,TG_ROW_ID,KEY,PARAM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update TG_ROW_PARAM set TG_ROW_ID = ?,KEY = ?,PARAM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TG_ROW_PARAM_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from TG_ROW_PARAM where    TG_ROW_PARAM_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from TG_ROW_PARAM,TG_ROW where 1=1 and TG_ROW_PARAM.TG_ROW_ID = TG_ROW.TG_ROW_ID and TG_ROW.GROUP_ID = ? order by  TG_ROW_PARAM.TG_ROW_ID ,TG_ROW_PARAM.TG_ROW_PARAM_ID";
/*     */   protected static final String SQL_GET_ALL = " from TG_ROW_PARAM where    TG_ROW_ID = ? ";
/*     */   protected TgRowParamEVO mDetails;
/*     */ 
/*     */   public TgRowParamDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public TgRowParamDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TgRowParamDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected TgRowParamPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(TgRowParamEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private TgRowParamEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  90 */     int col = 1;
/*  91 */     TgRowParamEVO evo = new TgRowParamEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++));
/*     */ 
/*  98 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  99 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 100 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 101 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(TgRowParamEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 106 */     int col = startCol_;
/* 107 */     stmt_.setInt(col++, evo_.getTgRowParamId());
/* 108 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(TgRowParamEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 113 */     int col = startCol_;
/* 114 */     stmt_.setInt(col++, evo_.getTgRowId());
/* 115 */     stmt_.setString(col++, evo_.getKey());
/* 116 */     stmt_.setString(col++, evo_.getParam());
/* 117 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 118 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 119 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 120 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(TgRowParamPK pk)
/*     */     throws ValidationException
/*     */   {
/* 136 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 138 */     PreparedStatement stmt = null;
/* 139 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 143 */       stmt = getConnection().prepareStatement("select TG_ROW_PARAM.TG_ROW_PARAM_ID,TG_ROW_PARAM.TG_ROW_ID,TG_ROW_PARAM.KEY,TG_ROW_PARAM.PARAM,TG_ROW_PARAM.UPDATED_BY_USER_ID,TG_ROW_PARAM.UPDATED_TIME,TG_ROW_PARAM.CREATED_TIME from TG_ROW_PARAM where    TG_ROW_PARAM_ID = ? ");
/*     */ 
/* 146 */       int col = 1;
/* 147 */       stmt.setInt(col++, pk.getTgRowParamId());
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
/* 161 */       throw handleSQLException(pk, "select TG_ROW_PARAM.TG_ROW_PARAM_ID,TG_ROW_PARAM.TG_ROW_ID,TG_ROW_PARAM.KEY,TG_ROW_PARAM.PARAM,TG_ROW_PARAM.UPDATED_BY_USER_ID,TG_ROW_PARAM.UPDATED_TIME,TG_ROW_PARAM.CREATED_TIME from TG_ROW_PARAM where    TG_ROW_PARAM_ID = ? ", sqle);
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
/* 201 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 202 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 207 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 208 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 209 */       stmt = getConnection().prepareStatement("insert into TG_ROW_PARAM ( TG_ROW_PARAM_ID,TG_ROW_ID,KEY,PARAM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*     */ 
/* 212 */       int col = 1;
/* 213 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 214 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 217 */       int resultCount = stmt.executeUpdate();
/* 218 */       if (resultCount != 1)
/*     */       {
/* 220 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 223 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 227 */       throw handleSQLException(this.mDetails.getPK(), "insert into TG_ROW_PARAM ( TG_ROW_PARAM_ID,TG_ROW_ID,KEY,PARAM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 231 */       closeStatement(stmt);
/* 232 */       closeConnection();
/*     */ 
/* 234 */       if (timer != null)
/* 235 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 260 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 264 */     PreparedStatement stmt = null;
/*     */ 
/* 266 */     boolean mainChanged = this.mDetails.isModified();
/* 267 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 270 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 273 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 274 */         stmt = getConnection().prepareStatement("update TG_ROW_PARAM set TG_ROW_ID = ?,KEY = ?,PARAM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TG_ROW_PARAM_ID = ? ");
/*     */ 
/* 277 */         int col = 1;
/* 278 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 279 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 282 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 285 */         if (resultCount != 1) {
/* 286 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 289 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 298 */       throw handleSQLException(getPK(), "update TG_ROW_PARAM set TG_ROW_ID = ?,KEY = ?,PARAM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TG_ROW_PARAM_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 302 */       closeStatement(stmt);
/* 303 */       closeConnection();
/*     */ 
/* 305 */       if ((timer != null) && (
/* 306 */         (mainChanged) || (dependantChanged)))
/* 307 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 326 */     if (items == null) {
/* 327 */       return false;
/*     */     }
/* 329 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 330 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 332 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 337 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 338 */       while (iter2.hasNext())
/*     */       {
/* 340 */         this.mDetails = ((TgRowParamEVO)iter2.next());
/*     */ 
/* 343 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 345 */         somethingChanged = true;
/*     */ 
/* 348 */         if (deleteStmt == null) {
/* 349 */           deleteStmt = getConnection().prepareStatement("delete from TG_ROW_PARAM where    TG_ROW_PARAM_ID = ? ");
/*     */         }
/*     */ 
/* 352 */         int col = 1;
/* 353 */         deleteStmt.setInt(col++, this.mDetails.getTgRowParamId());
/*     */ 
/* 355 */         if (this._log.isDebugEnabled()) {
/* 356 */           this._log.debug("update", "TgRowParam deleting TgRowParamId=" + this.mDetails.getTgRowParamId());
/*     */         }
/*     */ 
/* 361 */         deleteStmt.addBatch();
/*     */ 
/* 364 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 369 */       if (deleteStmt != null)
/*     */       {
/* 371 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 373 */         deleteStmt.executeBatch();
/*     */ 
/* 375 */         if (timer2 != null) {
/* 376 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 380 */       Iterator iter1 = items.values().iterator();
/* 381 */       while (iter1.hasNext())
/*     */       {
/* 383 */         this.mDetails = ((TgRowParamEVO)iter1.next());
/*     */ 
/* 385 */         if (this.mDetails.insertPending())
/*     */         {
/* 387 */           somethingChanged = true;
/* 388 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 391 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 393 */         somethingChanged = true;
/* 394 */         doStore();
/*     */       }
/*     */ 
/* 405 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 409 */       throw handleSQLException("delete from TG_ROW_PARAM where    TG_ROW_PARAM_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 413 */       if (deleteStmt != null)
/*     */       {
/* 415 */         closeStatement(deleteStmt);
/* 416 */         closeConnection();
/*     */       }
/*     */ 
/* 419 */       this.mDetails = null;
/*     */ 
/* 421 */       if ((somethingChanged) && 
/* 422 */         (timer != null))
/* 423 */         timer.logDebug("update", "collection"); 
/* 423 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(TaskGroupPK entityPK, Collection owners, String dependants)
/*     */   {
/* 446 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 448 */     PreparedStatement stmt = null;
/* 449 */     ResultSet resultSet = null;
/*     */ 
/* 451 */     int itemCount = 0;
/*     */ 
/* 453 */     TgRowEVO owningEVO = null;
/* 454 */     Iterator ownersIter = owners.iterator();
/* 455 */     while (ownersIter.hasNext())
/*     */     {
/* 457 */       owningEVO = (TgRowEVO)ownersIter.next();
/* 458 */       owningEVO.setTGRowsParamsAllItemsLoaded(true);
/*     */     }
/* 460 */     ownersIter = owners.iterator();
/* 461 */     owningEVO = (TgRowEVO)ownersIter.next();
/* 462 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 466 */       stmt = getConnection().prepareStatement("select TG_ROW_PARAM.TG_ROW_PARAM_ID,TG_ROW_PARAM.TG_ROW_ID,TG_ROW_PARAM.KEY,TG_ROW_PARAM.PARAM,TG_ROW_PARAM.UPDATED_BY_USER_ID,TG_ROW_PARAM.UPDATED_TIME,TG_ROW_PARAM.CREATED_TIME from TG_ROW_PARAM,TG_ROW where 1=1 and TG_ROW_PARAM.TG_ROW_ID = TG_ROW.TG_ROW_ID and TG_ROW.GROUP_ID = ? order by  TG_ROW_PARAM.TG_ROW_ID ,TG_ROW_PARAM.TG_ROW_PARAM_ID");
/*     */ 
/* 468 */       int col = 1;
/* 469 */       stmt.setInt(col++, entityPK.getGroupId());
/*     */ 
/* 471 */       resultSet = stmt.executeQuery();
/*     */ 
/* 474 */       while (resultSet.next())
/*     */       {
/* 476 */         itemCount++;
/* 477 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 482 */         while (this.mDetails.getTgRowId() != owningEVO.getTgRowId())
/*     */         {
/* 486 */           if (!ownersIter.hasNext())
/*     */           {
/* 488 */             this._log.debug("bulkGetAll", "can't find owning [TgRowId=" + this.mDetails.getTgRowId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 492 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 493 */             ownersIter = owners.iterator();
/* 494 */             while (ownersIter.hasNext())
/*     */             {
/* 496 */               owningEVO = (TgRowEVO)ownersIter.next();
/* 497 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 499 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 501 */           owningEVO = (TgRowEVO)ownersIter.next();
/*     */         }
/* 503 */         if (owningEVO.getTGRowsParams() == null)
/*     */         {
/* 505 */           theseItems = new ArrayList();
/* 506 */           owningEVO.setTGRowsParams(theseItems);
/* 507 */           owningEVO.setTGRowsParamsAllItemsLoaded(true);
/*     */         }
/* 509 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 512 */       if (timer != null) {
/* 513 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 518 */       throw handleSQLException("select TG_ROW_PARAM.TG_ROW_PARAM_ID,TG_ROW_PARAM.TG_ROW_ID,TG_ROW_PARAM.KEY,TG_ROW_PARAM.PARAM,TG_ROW_PARAM.UPDATED_BY_USER_ID,TG_ROW_PARAM.UPDATED_TIME,TG_ROW_PARAM.CREATED_TIME from TG_ROW_PARAM,TG_ROW where 1=1 and TG_ROW_PARAM.TG_ROW_ID = TG_ROW.TG_ROW_ID and TG_ROW.GROUP_ID = ? order by  TG_ROW_PARAM.TG_ROW_ID ,TG_ROW_PARAM.TG_ROW_PARAM_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 522 */       closeResultSet(resultSet);
/* 523 */       closeStatement(stmt);
/* 524 */       closeConnection();
/*     */ 
/* 526 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectTgRowId, String dependants, Collection currentList)
/*     */   {
/* 551 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 552 */     PreparedStatement stmt = null;
/* 553 */     ResultSet resultSet = null;
/*     */ 
/* 555 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 559 */       stmt = getConnection().prepareStatement("select TG_ROW_PARAM.TG_ROW_PARAM_ID,TG_ROW_PARAM.TG_ROW_ID,TG_ROW_PARAM.KEY,TG_ROW_PARAM.PARAM,TG_ROW_PARAM.UPDATED_BY_USER_ID,TG_ROW_PARAM.UPDATED_TIME,TG_ROW_PARAM.CREATED_TIME from TG_ROW_PARAM where    TG_ROW_ID = ? ");
/*     */ 
/* 561 */       int col = 1;
/* 562 */       stmt.setInt(col++, selectTgRowId);
/*     */ 
/* 564 */       resultSet = stmt.executeQuery();
/*     */ 
/* 566 */       while (resultSet.next())
/*     */       {
/* 568 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 571 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 574 */       if (currentList != null)
/*     */       {
/* 577 */         ListIterator iter = items.listIterator();
/* 578 */         TgRowParamEVO currentEVO = null;
/* 579 */         TgRowParamEVO newEVO = null;
/* 580 */         while (iter.hasNext())
/*     */         {
/* 582 */           newEVO = (TgRowParamEVO)iter.next();
/* 583 */           Iterator iter2 = currentList.iterator();
/* 584 */           while (iter2.hasNext())
/*     */           {
/* 586 */             currentEVO = (TgRowParamEVO)iter2.next();
/* 587 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 589 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 595 */         Iterator iter2 = currentList.iterator();
/* 596 */         while (iter2.hasNext())
/*     */         {
/* 598 */           currentEVO = (TgRowParamEVO)iter2.next();
/* 599 */           if (currentEVO.insertPending()) {
/* 600 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 604 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 608 */       throw handleSQLException("select TG_ROW_PARAM.TG_ROW_PARAM_ID,TG_ROW_PARAM.TG_ROW_ID,TG_ROW_PARAM.KEY,TG_ROW_PARAM.PARAM,TG_ROW_PARAM.UPDATED_BY_USER_ID,TG_ROW_PARAM.UPDATED_TIME,TG_ROW_PARAM.CREATED_TIME from TG_ROW_PARAM where    TG_ROW_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 612 */       closeResultSet(resultSet);
/* 613 */       closeStatement(stmt);
/* 614 */       closeConnection();
/*     */ 
/* 616 */       if (timer != null) {
/* 617 */         timer.logDebug("getAll", " TgRowId=" + selectTgRowId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 622 */     return items;
/*     */   }
/*     */ 
/*     */   public TgRowParamEVO getDetails(TaskGroupCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 636 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 639 */     if (this.mDetails == null) {
/* 640 */       doLoad(((TgRowParamCK)paramCK).getTgRowParamPK());
/*     */     }
/* 642 */     else if (!this.mDetails.getPK().equals(((TgRowParamCK)paramCK).getTgRowParamPK())) {
/* 643 */       doLoad(((TgRowParamCK)paramCK).getTgRowParamPK());
/*     */     }
/*     */ 
/* 646 */     TgRowParamEVO details = new TgRowParamEVO();
/* 647 */     details = this.mDetails.deepClone();
/*     */ 
/* 649 */     if (timer != null) {
/* 650 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 652 */     return details;
/*     */   }
/*     */ 
/*     */   public TgRowParamEVO getDetails(TaskGroupCK paramCK, TgRowParamEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 658 */     TgRowParamEVO savedEVO = this.mDetails;
/* 659 */     this.mDetails = paramEVO;
/* 660 */     TgRowParamEVO newEVO = getDetails(paramCK, dependants);
/* 661 */     this.mDetails = savedEVO;
/* 662 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public TgRowParamEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 668 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 672 */     TgRowParamEVO details = this.mDetails.deepClone();
/*     */ 
/* 674 */     if (timer != null) {
/* 675 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 677 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 682 */     return "TgRowParam";
/*     */   }
/*     */ 
/*     */   public TgRowParamRefImpl getRef(TgRowParamPK paramTgRowParamPK)
/*     */   {
/* 687 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 688 */     PreparedStatement stmt = null;
/* 689 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 692 */       stmt = getConnection().prepareStatement("select 0,TASK_GROUP.GROUP_ID,TG_ROW.TG_ROW_ID from TG_ROW_PARAM,TASK_GROUP,TG_ROW where 1=1 and TG_ROW_PARAM.TG_ROW_PARAM_ID = ? and TG_ROW_PARAM.TG_ROW_ID = TG_ROW.TG_ROW_ID and TG_ROW.TG_ROW_ID = TASK_GROUP.TG_ROW_ID");
/* 693 */       int col = 1;
/* 694 */       stmt.setInt(col++, paramTgRowParamPK.getTgRowParamId());
/*     */ 
/* 696 */       resultSet = stmt.executeQuery();
/*     */ 
/* 698 */       if (!resultSet.next()) {
/* 699 */         throw new RuntimeException(getEntityName() + " getRef " + paramTgRowParamPK + " not found");
/*     */       }
/* 701 */       col = 2;
/* 702 */       TaskGroupPK newTaskGroupPK = new TaskGroupPK(resultSet.getInt(col++));
/*     */ 
/* 706 */       TgRowPK newTgRowPK = new TgRowPK(resultSet.getInt(col++));
/*     */ 
/* 710 */       String textTgRowParam = "";
/* 711 */       TgRowParamCK ckTgRowParam = new TgRowParamCK(newTaskGroupPK, newTgRowPK, paramTgRowParamPK);
/*     */ 
/* 717 */       TgRowParamRefImpl localTgRowParamRefImpl = new TgRowParamRefImpl(ckTgRowParam, textTgRowParam);
/*     */       return localTgRowParamRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 722 */       throw handleSQLException(paramTgRowParamPK, "select 0,TASK_GROUP.GROUP_ID,TG_ROW.TG_ROW_ID from TG_ROW_PARAM,TASK_GROUP,TG_ROW where 1=1 and TG_ROW_PARAM.TG_ROW_PARAM_ID = ? and TG_ROW_PARAM.TG_ROW_ID = TG_ROW.TG_ROW_ID and TG_ROW.TG_ROW_ID = TASK_GROUP.TG_ROW_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 726 */       closeResultSet(resultSet);
/* 727 */       closeStatement(stmt);
/* 728 */       closeConnection();
/*     */ 
/* 730 */       if (timer != null)
/* 731 */         timer.logDebug("getRef", paramTgRowParamPK); 
/* 731 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.task.group.TgRowParamDAO
 * JD-Core Version:    0.6.0
 */