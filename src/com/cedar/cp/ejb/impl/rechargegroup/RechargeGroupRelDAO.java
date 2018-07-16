/*     */ package com.cedar.cp.ejb.impl.rechargegroup;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.recharge.RechargeGroupCK;
/*     */ import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
/*     */ import com.cedar.cp.dto.rechargegroup.RechargeGroupRelCK;
/*     */ import com.cedar.cp.dto.rechargegroup.RechargeGroupRelPK;
/*     */ import com.cedar.cp.dto.rechargegroup.RechargeGroupRelRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupEVO;
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
/*     */ public class RechargeGroupRelDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select RECHARGE_GROUP_REL.RECHARGE_GROUP_REL_ID,RECHARGE_GROUP_REL.RECHARGE_GROUP_ID,RECHARGE_GROUP_REL.RECHARGE_ID,RECHARGE_GROUP_REL.PROCESS_ORDER,RECHARGE_GROUP_REL.UPDATED_BY_USER_ID,RECHARGE_GROUP_REL.UPDATED_TIME,RECHARGE_GROUP_REL.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from RECHARGE_GROUP_REL where    RECHARGE_GROUP_REL_ID = ? AND RECHARGE_GROUP_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into RECHARGE_GROUP_REL ( RECHARGE_GROUP_REL_ID,RECHARGE_GROUP_ID,RECHARGE_ID,PROCESS_ORDER,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update RECHARGE_GROUP_REL set RECHARGE_ID = ?,PROCESS_ORDER = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RECHARGE_GROUP_REL_ID = ? AND RECHARGE_GROUP_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from RECHARGE_GROUP_REL where    RECHARGE_GROUP_REL_ID = ? AND RECHARGE_GROUP_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from RECHARGE_GROUP_REL where 1=1 and RECHARGE_GROUP_REL.RECHARGE_GROUP_ID = ? order by  RECHARGE_GROUP_REL.RECHARGE_GROUP_REL_ID ,RECHARGE_GROUP_REL.RECHARGE_GROUP_ID";
/*     */   protected static final String SQL_GET_ALL = " from RECHARGE_GROUP_REL where    RECHARGE_GROUP_ID = ? ";
/*     */   protected RechargeGroupRelEVO mDetails;
/*     */ 
/*     */   public RechargeGroupRelDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public RechargeGroupRelDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public RechargeGroupRelDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected RechargeGroupRelPK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(RechargeGroupRelEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private RechargeGroupRelEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  93 */     int col = 1;
/*  94 */     RechargeGroupRelEVO evo = new RechargeGroupRelEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/* 101 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 102 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 103 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 104 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(RechargeGroupRelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 109 */     int col = startCol_;
/* 110 */     stmt_.setInt(col++, evo_.getRechargeGroupRelId());
/* 111 */     stmt_.setInt(col++, evo_.getRechargeGroupId());
/* 112 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(RechargeGroupRelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 117 */     int col = startCol_;
/* 118 */     stmt_.setInt(col++, evo_.getRechargeId());
/* 119 */     stmt_.setInt(col++, evo_.getProcessOrder());
/* 120 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 121 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 122 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 123 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(RechargeGroupRelPK pk)
/*     */     throws ValidationException
/*     */   {
/* 140 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 142 */     PreparedStatement stmt = null;
/* 143 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 147 */       stmt = getConnection().prepareStatement("select RECHARGE_GROUP_REL.RECHARGE_GROUP_REL_ID,RECHARGE_GROUP_REL.RECHARGE_GROUP_ID,RECHARGE_GROUP_REL.RECHARGE_ID,RECHARGE_GROUP_REL.PROCESS_ORDER,RECHARGE_GROUP_REL.UPDATED_BY_USER_ID,RECHARGE_GROUP_REL.UPDATED_TIME,RECHARGE_GROUP_REL.CREATED_TIME from RECHARGE_GROUP_REL where    RECHARGE_GROUP_REL_ID = ? AND RECHARGE_GROUP_ID = ? ");
/*     */ 
/* 150 */       int col = 1;
/* 151 */       stmt.setInt(col++, pk.getRechargeGroupRelId());
/* 152 */       stmt.setInt(col++, pk.getRechargeGroupId());
/*     */ 
/* 154 */       resultSet = stmt.executeQuery();
/*     */ 
/* 156 */       if (!resultSet.next()) {
/* 157 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 160 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 161 */       if (this.mDetails.isModified())
/* 162 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 166 */       throw handleSQLException(pk, "select RECHARGE_GROUP_REL.RECHARGE_GROUP_REL_ID,RECHARGE_GROUP_REL.RECHARGE_GROUP_ID,RECHARGE_GROUP_REL.RECHARGE_ID,RECHARGE_GROUP_REL.PROCESS_ORDER,RECHARGE_GROUP_REL.UPDATED_BY_USER_ID,RECHARGE_GROUP_REL.UPDATED_TIME,RECHARGE_GROUP_REL.CREATED_TIME from RECHARGE_GROUP_REL where    RECHARGE_GROUP_REL_ID = ? AND RECHARGE_GROUP_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 170 */       closeResultSet(resultSet);
/* 171 */       closeStatement(stmt);
/* 172 */       closeConnection();
/*     */ 
/* 174 */       if (timer != null)
/* 175 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 206 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 207 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 212 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 213 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 214 */       stmt = getConnection().prepareStatement("insert into RECHARGE_GROUP_REL ( RECHARGE_GROUP_REL_ID,RECHARGE_GROUP_ID,RECHARGE_ID,PROCESS_ORDER,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
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
/* 232 */       throw handleSQLException(this.mDetails.getPK(), "insert into RECHARGE_GROUP_REL ( RECHARGE_GROUP_REL_ID,RECHARGE_GROUP_ID,RECHARGE_ID,PROCESS_ORDER,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
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
/* 278 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 279 */         stmt = getConnection().prepareStatement("update RECHARGE_GROUP_REL set RECHARGE_ID = ?,PROCESS_ORDER = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RECHARGE_GROUP_REL_ID = ? AND RECHARGE_GROUP_ID = ? ");
/*     */ 
/* 282 */         int col = 1;
/* 283 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 284 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 287 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 290 */         if (resultCount != 1) {
/* 291 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 294 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 303 */       throw handleSQLException(getPK(), "update RECHARGE_GROUP_REL set RECHARGE_ID = ?,PROCESS_ORDER = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RECHARGE_GROUP_REL_ID = ? AND RECHARGE_GROUP_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 307 */       closeStatement(stmt);
/* 308 */       closeConnection();
/*     */ 
/* 310 */       if ((timer != null) && (
/* 311 */         (mainChanged) || (dependantChanged)))
/* 312 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
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
/* 346 */         this.mDetails = ((RechargeGroupRelEVO)iter2.next());
/*     */ 
/* 349 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 351 */         somethingChanged = true;
/*     */ 
/* 354 */         if (deleteStmt == null) {
/* 355 */           deleteStmt = getConnection().prepareStatement("delete from RECHARGE_GROUP_REL where    RECHARGE_GROUP_REL_ID = ? AND RECHARGE_GROUP_ID = ? ");
/*     */         }
/*     */ 
/* 358 */         int col = 1;
/* 359 */         deleteStmt.setInt(col++, this.mDetails.getRechargeGroupRelId());
/* 360 */         deleteStmt.setInt(col++, this.mDetails.getRechargeGroupId());
/*     */ 
/* 362 */         if (this._log.isDebugEnabled()) {
/* 363 */           this._log.debug("update", "RechargeGroupRel deleting RechargeGroupRelId=" + this.mDetails.getRechargeGroupRelId() + ",RechargeGroupId=" + this.mDetails.getRechargeGroupId());
/*     */         }
/*     */ 
/* 369 */         deleteStmt.addBatch();
/*     */ 
/* 372 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 377 */       if (deleteStmt != null)
/*     */       {
/* 379 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 381 */         deleteStmt.executeBatch();
/*     */ 
/* 383 */         if (timer2 != null) {
/* 384 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 388 */       Iterator iter1 = items.values().iterator();
/* 389 */       while (iter1.hasNext())
/*     */       {
/* 391 */         this.mDetails = ((RechargeGroupRelEVO)iter1.next());
/*     */ 
/* 393 */         if (this.mDetails.insertPending())
/*     */         {
/* 395 */           somethingChanged = true;
/* 396 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 399 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 401 */         somethingChanged = true;
/* 402 */         doStore();
/*     */       }
/*     */ 
/* 413 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 417 */       throw handleSQLException("delete from RECHARGE_GROUP_REL where    RECHARGE_GROUP_REL_ID = ? AND RECHARGE_GROUP_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 421 */       if (deleteStmt != null)
/*     */       {
/* 423 */         closeStatement(deleteStmt);
/* 424 */         closeConnection();
/*     */       }
/*     */ 
/* 427 */       this.mDetails = null;
/*     */ 
/* 429 */       if ((somethingChanged) && 
/* 430 */         (timer != null))
/* 431 */         timer.logDebug("update", "collection"); 
/* 431 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(RechargeGroupPK entityPK, RechargeGroupEVO owningEVO, String dependants)
/*     */   {
/* 451 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 453 */     PreparedStatement stmt = null;
/* 454 */     ResultSet resultSet = null;
/*     */ 
/* 456 */     int itemCount = 0;
/*     */ 
/* 458 */     Collection theseItems = new ArrayList();
/* 459 */     owningEVO.setRecharges(theseItems);
/* 460 */     owningEVO.setRechargesAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 464 */       stmt = getConnection().prepareStatement("select RECHARGE_GROUP_REL.RECHARGE_GROUP_REL_ID,RECHARGE_GROUP_REL.RECHARGE_GROUP_ID,RECHARGE_GROUP_REL.RECHARGE_ID,RECHARGE_GROUP_REL.PROCESS_ORDER,RECHARGE_GROUP_REL.UPDATED_BY_USER_ID,RECHARGE_GROUP_REL.UPDATED_TIME,RECHARGE_GROUP_REL.CREATED_TIME from RECHARGE_GROUP_REL where 1=1 and RECHARGE_GROUP_REL.RECHARGE_GROUP_ID = ? order by  RECHARGE_GROUP_REL.RECHARGE_GROUP_REL_ID ,RECHARGE_GROUP_REL.RECHARGE_GROUP_ID");
/*     */ 
/* 466 */       int col = 1;
/* 467 */       stmt.setInt(col++, entityPK.getRechargeGroupId());
/*     */ 
/* 469 */       resultSet = stmt.executeQuery();
/*     */ 
/* 472 */       while (resultSet.next())
/*     */       {
/* 474 */         itemCount++;
/* 475 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 477 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 480 */       if (timer != null) {
/* 481 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 486 */       throw handleSQLException("select RECHARGE_GROUP_REL.RECHARGE_GROUP_REL_ID,RECHARGE_GROUP_REL.RECHARGE_GROUP_ID,RECHARGE_GROUP_REL.RECHARGE_ID,RECHARGE_GROUP_REL.PROCESS_ORDER,RECHARGE_GROUP_REL.UPDATED_BY_USER_ID,RECHARGE_GROUP_REL.UPDATED_TIME,RECHARGE_GROUP_REL.CREATED_TIME from RECHARGE_GROUP_REL where 1=1 and RECHARGE_GROUP_REL.RECHARGE_GROUP_ID = ? order by  RECHARGE_GROUP_REL.RECHARGE_GROUP_REL_ID ,RECHARGE_GROUP_REL.RECHARGE_GROUP_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 490 */       closeResultSet(resultSet);
/* 491 */       closeStatement(stmt);
/* 492 */       closeConnection();
/*     */ 
/* 494 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectRechargeGroupId, String dependants, Collection currentList)
/*     */   {
/* 519 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 520 */     PreparedStatement stmt = null;
/* 521 */     ResultSet resultSet = null;
/*     */ 
/* 523 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 527 */       stmt = getConnection().prepareStatement("select RECHARGE_GROUP_REL.RECHARGE_GROUP_REL_ID,RECHARGE_GROUP_REL.RECHARGE_GROUP_ID,RECHARGE_GROUP_REL.RECHARGE_ID,RECHARGE_GROUP_REL.PROCESS_ORDER,RECHARGE_GROUP_REL.UPDATED_BY_USER_ID,RECHARGE_GROUP_REL.UPDATED_TIME,RECHARGE_GROUP_REL.CREATED_TIME from RECHARGE_GROUP_REL where    RECHARGE_GROUP_ID = ? ");
/*     */ 
/* 529 */       int col = 1;
/* 530 */       stmt.setInt(col++, selectRechargeGroupId);
/*     */ 
/* 532 */       resultSet = stmt.executeQuery();
/*     */ 
/* 534 */       while (resultSet.next())
/*     */       {
/* 536 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 539 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 542 */       if (currentList != null)
/*     */       {
/* 545 */         ListIterator iter = items.listIterator();
/* 546 */         RechargeGroupRelEVO currentEVO = null;
/* 547 */         RechargeGroupRelEVO newEVO = null;
/* 548 */         while (iter.hasNext())
/*     */         {
/* 550 */           newEVO = (RechargeGroupRelEVO)iter.next();
/* 551 */           Iterator iter2 = currentList.iterator();
/* 552 */           while (iter2.hasNext())
/*     */           {
/* 554 */             currentEVO = (RechargeGroupRelEVO)iter2.next();
/* 555 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 557 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 563 */         Iterator iter2 = currentList.iterator();
/* 564 */         while (iter2.hasNext())
/*     */         {
/* 566 */           currentEVO = (RechargeGroupRelEVO)iter2.next();
/* 567 */           if (currentEVO.insertPending()) {
/* 568 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 572 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 576 */       throw handleSQLException("select RECHARGE_GROUP_REL.RECHARGE_GROUP_REL_ID,RECHARGE_GROUP_REL.RECHARGE_GROUP_ID,RECHARGE_GROUP_REL.RECHARGE_ID,RECHARGE_GROUP_REL.PROCESS_ORDER,RECHARGE_GROUP_REL.UPDATED_BY_USER_ID,RECHARGE_GROUP_REL.UPDATED_TIME,RECHARGE_GROUP_REL.CREATED_TIME from RECHARGE_GROUP_REL where    RECHARGE_GROUP_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 580 */       closeResultSet(resultSet);
/* 581 */       closeStatement(stmt);
/* 582 */       closeConnection();
/*     */ 
/* 584 */       if (timer != null) {
/* 585 */         timer.logDebug("getAll", " RechargeGroupId=" + selectRechargeGroupId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 590 */     return items;
/*     */   }
/*     */ 
/*     */   public RechargeGroupRelEVO getDetails(RechargeGroupCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 604 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 607 */     if (this.mDetails == null) {
/* 608 */       doLoad(((RechargeGroupRelCK)paramCK).getRechargeGroupRelPK());
/*     */     }
/* 610 */     else if (!this.mDetails.getPK().equals(((RechargeGroupRelCK)paramCK).getRechargeGroupRelPK())) {
/* 611 */       doLoad(((RechargeGroupRelCK)paramCK).getRechargeGroupRelPK());
/*     */     }
/*     */ 
/* 614 */     RechargeGroupRelEVO details = new RechargeGroupRelEVO();
/* 615 */     details = this.mDetails.deepClone();
/*     */ 
/* 617 */     if (timer != null) {
/* 618 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 620 */     return details;
/*     */   }
/*     */ 
/*     */   public RechargeGroupRelEVO getDetails(RechargeGroupCK paramCK, RechargeGroupRelEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 626 */     RechargeGroupRelEVO savedEVO = this.mDetails;
/* 627 */     this.mDetails = paramEVO;
/* 628 */     RechargeGroupRelEVO newEVO = getDetails(paramCK, dependants);
/* 629 */     this.mDetails = savedEVO;
/* 630 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public RechargeGroupRelEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 636 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 640 */     RechargeGroupRelEVO details = this.mDetails.deepClone();
/*     */ 
/* 642 */     if (timer != null) {
/* 643 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 645 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 650 */     return "RechargeGroupRel";
/*     */   }
/*     */ 
/*     */   public RechargeGroupRelRefImpl getRef(RechargeGroupRelPK paramRechargeGroupRelPK)
/*     */   {
/* 655 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 656 */     PreparedStatement stmt = null;
/* 657 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 660 */       stmt = getConnection().prepareStatement("select 0,RECHARGE_GROUP.RECHARGE_GROUP_ID from RECHARGE_GROUP_REL,RECHARGE_GROUP where 1=1 and RECHARGE_GROUP_REL.RECHARGE_GROUP_REL_ID = ? and RECHARGE_GROUP_REL.RECHARGE_GROUP_ID = ? and RECHARGE_GROUP_REL.RECHARGE_GROUP_ID = RECHARGE_GROUP.RECHARGE_GROUP_ID");
/* 661 */       int col = 1;
/* 662 */       stmt.setInt(col++, paramRechargeGroupRelPK.getRechargeGroupRelId());
/* 663 */       stmt.setInt(col++, paramRechargeGroupRelPK.getRechargeGroupId());
/*     */ 
/* 665 */       resultSet = stmt.executeQuery();
/*     */ 
/* 667 */       if (!resultSet.next()) {
/* 668 */         throw new RuntimeException(getEntityName() + " getRef " + paramRechargeGroupRelPK + " not found");
/*     */       }
/* 670 */       col = 2;
/* 671 */       RechargeGroupPK newRechargeGroupPK = new RechargeGroupPK(resultSet.getInt(col++));
/*     */ 
/* 675 */       String textRechargeGroupRel = "";
/* 676 */       RechargeGroupRelCK ckRechargeGroupRel = new RechargeGroupRelCK(newRechargeGroupPK, paramRechargeGroupRelPK);
/*     */ 
/* 681 */       RechargeGroupRelRefImpl localRechargeGroupRelRefImpl = new RechargeGroupRelRefImpl(ckRechargeGroupRel, textRechargeGroupRel);
/*     */       return localRechargeGroupRelRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 686 */       throw handleSQLException(paramRechargeGroupRelPK, "select 0,RECHARGE_GROUP.RECHARGE_GROUP_ID from RECHARGE_GROUP_REL,RECHARGE_GROUP where 1=1 and RECHARGE_GROUP_REL.RECHARGE_GROUP_REL_ID = ? and RECHARGE_GROUP_REL.RECHARGE_GROUP_ID = ? and RECHARGE_GROUP_REL.RECHARGE_GROUP_ID = RECHARGE_GROUP.RECHARGE_GROUP_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 690 */       closeResultSet(resultSet);
/* 691 */       closeStatement(stmt);
/* 692 */       closeConnection();
/*     */ 
/* 694 */       if (timer != null)
/* 695 */         timer.logDebug("getRef", paramRechargeGroupRelPK); 
/* 695 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.rechargegroup.RechargeGroupRelDAO
 * JD-Core Version:    0.6.0
 */