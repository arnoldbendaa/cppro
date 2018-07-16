/*     */ package com.cedar.cp.ejb.impl.report.destination.internal;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationUsersELO;
/*     */ import com.cedar.cp.dto.report.destination.internal.CheckInternalDestinationUsersELO;
/*     */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationCK;
/*     */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
/*     */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationRefImpl;
/*     */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersCK;
/*     */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersPK;
/*     */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersRefImpl;
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
/*     */ public class InternalDestinationUsersDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID,INTERNAL_DESTINATION_USERS.USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_TIME,INTERNAL_DESTINATION_USERS.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from INTERNAL_DESTINATION_USERS where    INTERNAL_DESTINATION_ID = ? AND USER_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into INTERNAL_DESTINATION_USERS ( INTERNAL_DESTINATION_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update INTERNAL_DESTINATION_USERS set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    INTERNAL_DESTINATION_ID = ? AND USER_ID = ? ";
/* 305 */   protected static String SQL_ALL_INTERNAL_DESTINATION_USERS = "select 0       ,INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID      ,INTERNAL_DESTINATION.VIS_ID      ,INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID      ,INTERNAL_DESTINATION_USERS.USER_ID      ,INTERNAL_DESTINATION_USERS.USER_ID from INTERNAL_DESTINATION_USERS    ,INTERNAL_DESTINATION where 1=1   and INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID = INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID ";
/*     */ 
/* 412 */   protected static String SQL_CHECK_INTERNAL_DESTINATION_USERS = "select 0       ,INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID      ,INTERNAL_DESTINATION.VIS_ID      ,INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID      ,INTERNAL_DESTINATION_USERS.USER_ID      ,INTERNAL_DESTINATION_USERS.USER_ID from INTERNAL_DESTINATION_USERS    ,INTERNAL_DESTINATION where 1=1   and INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID = INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID  and  INTERNAL_DESTINATION_USERS.USER_ID = ?";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from INTERNAL_DESTINATION_USERS where    INTERNAL_DESTINATION_ID = ? AND USER_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from INTERNAL_DESTINATION_USERS where 1=1 and INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID = ? order by  INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID ,INTERNAL_DESTINATION_USERS.USER_ID";
/*     */   protected static final String SQL_GET_ALL = " from INTERNAL_DESTINATION_USERS where    INTERNAL_DESTINATION_ID = ? ";
/*     */   protected InternalDestinationUsersEVO mDetails;
/*     */ 
/*     */   public InternalDestinationUsersDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public InternalDestinationUsersDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public InternalDestinationUsersDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected InternalDestinationUsersPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(InternalDestinationUsersEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private InternalDestinationUsersEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  88 */     int col = 1;
/*  89 */     InternalDestinationUsersEVO evo = new InternalDestinationUsersEVO(resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  94 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  95 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  96 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  97 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(InternalDestinationUsersEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 102 */     int col = startCol_;
/* 103 */     stmt_.setInt(col++, evo_.getInternalDestinationId());
/* 104 */     stmt_.setInt(col++, evo_.getUserId());
/* 105 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(InternalDestinationUsersEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 110 */     int col = startCol_;
/* 111 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 112 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 113 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 114 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(InternalDestinationUsersPK pk)
/*     */     throws ValidationException
/*     */   {
/* 131 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 133 */     PreparedStatement stmt = null;
/* 134 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 138 */       stmt = getConnection().prepareStatement("select INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID,INTERNAL_DESTINATION_USERS.USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_TIME,INTERNAL_DESTINATION_USERS.CREATED_TIME from INTERNAL_DESTINATION_USERS where    INTERNAL_DESTINATION_ID = ? AND USER_ID = ? ");
/*     */ 
/* 141 */       int col = 1;
/* 142 */       stmt.setInt(col++, pk.getInternalDestinationId());
/* 143 */       stmt.setInt(col++, pk.getUserId());
/*     */ 
/* 145 */       resultSet = stmt.executeQuery();
/*     */ 
/* 147 */       if (!resultSet.next()) {
/* 148 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 151 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 152 */       if (this.mDetails.isModified())
/* 153 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 157 */       throw handleSQLException(pk, "select INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID,INTERNAL_DESTINATION_USERS.USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_TIME,INTERNAL_DESTINATION_USERS.CREATED_TIME from INTERNAL_DESTINATION_USERS where    INTERNAL_DESTINATION_ID = ? AND USER_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 161 */       closeResultSet(resultSet);
/* 162 */       closeStatement(stmt);
/* 163 */       closeConnection();
/*     */ 
/* 165 */       if (timer != null)
/* 166 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 193 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 194 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 199 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 200 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 201 */       stmt = getConnection().prepareStatement("insert into INTERNAL_DESTINATION_USERS ( INTERNAL_DESTINATION_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)");
/*     */ 
/* 204 */       int col = 1;
/* 205 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 206 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 209 */       int resultCount = stmt.executeUpdate();
/* 210 */       if (resultCount != 1)
/*     */       {
/* 212 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 215 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 219 */       throw handleSQLException(this.mDetails.getPK(), "insert into INTERNAL_DESTINATION_USERS ( INTERNAL_DESTINATION_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 223 */       closeStatement(stmt);
/* 224 */       closeConnection();
/*     */ 
/* 226 */       if (timer != null)
/* 227 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 250 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 254 */     PreparedStatement stmt = null;
/*     */ 
/* 256 */     boolean mainChanged = this.mDetails.isModified();
/* 257 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 260 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 263 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 264 */         stmt = getConnection().prepareStatement("update INTERNAL_DESTINATION_USERS set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    INTERNAL_DESTINATION_ID = ? AND USER_ID = ? ");
/*     */ 
/* 267 */         int col = 1;
/* 268 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 269 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 272 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 275 */         if (resultCount != 1) {
/* 276 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 279 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 288 */       throw handleSQLException(getPK(), "update INTERNAL_DESTINATION_USERS set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    INTERNAL_DESTINATION_ID = ? AND USER_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 292 */       closeStatement(stmt);
/* 293 */       closeConnection();
/*     */ 
/* 295 */       if ((timer != null) && (
/* 296 */         (mainChanged) || (dependantChanged)))
/* 297 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllInternalDestinationUsersELO getAllInternalDestinationUsers()
/*     */   {
/* 334 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 335 */     PreparedStatement stmt = null;
/* 336 */     ResultSet resultSet = null;
/* 337 */     AllInternalDestinationUsersELO results = new AllInternalDestinationUsersELO();
/*     */     try
/*     */     {
/* 340 */       stmt = getConnection().prepareStatement(SQL_ALL_INTERNAL_DESTINATION_USERS);
/* 341 */       int col = 1;
/* 342 */       resultSet = stmt.executeQuery();
/* 343 */       while (resultSet.next())
/*     */       {
/* 345 */         col = 2;
/*     */ 
/* 348 */         InternalDestinationPK pkInternalDestination = new InternalDestinationPK(resultSet.getInt(col++));
/*     */ 
/* 351 */         String textInternalDestination = resultSet.getString(col++);
/*     */ 
/* 354 */         InternalDestinationUsersPK pkInternalDestinationUsers = new InternalDestinationUsersPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 358 */         String textInternalDestinationUsers = "";
/*     */ 
/* 363 */         InternalDestinationUsersCK ckInternalDestinationUsers = new InternalDestinationUsersCK(pkInternalDestination, pkInternalDestinationUsers);
/*     */ 
/* 369 */         InternalDestinationRefImpl erInternalDestination = new InternalDestinationRefImpl(pkInternalDestination, textInternalDestination);
/*     */ 
/* 375 */         InternalDestinationUsersRefImpl erInternalDestinationUsers = new InternalDestinationUsersRefImpl(ckInternalDestinationUsers, textInternalDestinationUsers);
/*     */ 
/* 380 */         int col1 = resultSet.getInt(col++);
/*     */ 
/* 383 */         results.add(erInternalDestinationUsers, erInternalDestination, col1);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 392 */       throw handleSQLException(SQL_ALL_INTERNAL_DESTINATION_USERS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 396 */       closeResultSet(resultSet);
/* 397 */       closeStatement(stmt);
/* 398 */       closeConnection();
/*     */     }
/*     */ 
/* 401 */     if (timer != null) {
/* 402 */       timer.logDebug("getAllInternalDestinationUsers", " items=" + results.size());
/*     */     }
/*     */ 
/* 406 */     return results;
/*     */   }
/*     */ 
/*     */   public CheckInternalDestinationUsersELO getCheckInternalDestinationUsers(int param1)
/*     */   {
/* 443 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 444 */     PreparedStatement stmt = null;
/* 445 */     ResultSet resultSet = null;
/* 446 */     CheckInternalDestinationUsersELO results = new CheckInternalDestinationUsersELO();
/*     */     try
/*     */     {
/* 449 */       stmt = getConnection().prepareStatement(SQL_CHECK_INTERNAL_DESTINATION_USERS);
/* 450 */       int col = 1;
/* 451 */       stmt.setInt(col++, param1);
/* 452 */       resultSet = stmt.executeQuery();
/* 453 */       while (resultSet.next())
/*     */       {
/* 455 */         col = 2;
/*     */ 
/* 458 */         InternalDestinationPK pkInternalDestination = new InternalDestinationPK(resultSet.getInt(col++));
/*     */ 
/* 461 */         String textInternalDestination = resultSet.getString(col++);
/*     */ 
/* 464 */         InternalDestinationUsersPK pkInternalDestinationUsers = new InternalDestinationUsersPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 468 */         String textInternalDestinationUsers = "";
/*     */ 
/* 473 */         InternalDestinationUsersCK ckInternalDestinationUsers = new InternalDestinationUsersCK(pkInternalDestination, pkInternalDestinationUsers);
/*     */ 
/* 479 */         InternalDestinationRefImpl erInternalDestination = new InternalDestinationRefImpl(pkInternalDestination, textInternalDestination);
/*     */ 
/* 485 */         InternalDestinationUsersRefImpl erInternalDestinationUsers = new InternalDestinationUsersRefImpl(ckInternalDestinationUsers, textInternalDestinationUsers);
/*     */ 
/* 490 */         int col1 = resultSet.getInt(col++);
/*     */ 
/* 493 */         results.add(erInternalDestinationUsers, erInternalDestination, col1);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 502 */       throw handleSQLException(SQL_CHECK_INTERNAL_DESTINATION_USERS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 506 */       closeResultSet(resultSet);
/* 507 */       closeStatement(stmt);
/* 508 */       closeConnection();
/*     */     }
/*     */ 
/* 511 */     if (timer != null) {
/* 512 */       timer.logDebug("getCheckInternalDestinationUsers", " UserId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 517 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 535 */     if (items == null) {
/* 536 */       return false;
/*     */     }
/* 538 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 539 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 541 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 546 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 547 */       while (iter2.hasNext())
/*     */       {
/* 549 */         this.mDetails = ((InternalDestinationUsersEVO)iter2.next());
/*     */ 
/* 552 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 554 */         somethingChanged = true;
/*     */ 
/* 557 */         if (deleteStmt == null) {
/* 558 */           deleteStmt = getConnection().prepareStatement("delete from INTERNAL_DESTINATION_USERS where    INTERNAL_DESTINATION_ID = ? AND USER_ID = ? ");
/*     */         }
/*     */ 
/* 561 */         int col = 1;
/* 562 */         deleteStmt.setInt(col++, this.mDetails.getInternalDestinationId());
/* 563 */         deleteStmt.setInt(col++, this.mDetails.getUserId());
/*     */ 
/* 565 */         if (this._log.isDebugEnabled()) {
/* 566 */           this._log.debug("update", "InternalDestinationUsers deleting InternalDestinationId=" + this.mDetails.getInternalDestinationId() + ",UserId=" + this.mDetails.getUserId());
/*     */         }
/*     */ 
/* 572 */         deleteStmt.addBatch();
/*     */ 
/* 575 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 580 */       if (deleteStmt != null)
/*     */       {
/* 582 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 584 */         deleteStmt.executeBatch();
/*     */ 
/* 586 */         if (timer2 != null) {
/* 587 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 591 */       Iterator iter1 = items.values().iterator();
/* 592 */       while (iter1.hasNext())
/*     */       {
/* 594 */         this.mDetails = ((InternalDestinationUsersEVO)iter1.next());
/*     */ 
/* 596 */         if (this.mDetails.insertPending())
/*     */         {
/* 598 */           somethingChanged = true;
/* 599 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 602 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 604 */         somethingChanged = true;
/* 605 */         doStore();
/*     */       }
/*     */ 
/* 616 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 620 */       throw handleSQLException("delete from INTERNAL_DESTINATION_USERS where    INTERNAL_DESTINATION_ID = ? AND USER_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 624 */       if (deleteStmt != null)
/*     */       {
/* 626 */         closeStatement(deleteStmt);
/* 627 */         closeConnection();
/*     */       }
/*     */ 
/* 630 */       this.mDetails = null;
/*     */ 
/* 632 */       if ((somethingChanged) && 
/* 633 */         (timer != null))
/* 634 */         timer.logDebug("update", "collection"); 
/* 634 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(InternalDestinationPK entityPK, InternalDestinationEVO owningEVO, String dependants)
/*     */   {
/* 654 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 656 */     PreparedStatement stmt = null;
/* 657 */     ResultSet resultSet = null;
/*     */ 
/* 659 */     int itemCount = 0;
/*     */ 
/* 661 */     Collection theseItems = new ArrayList();
/* 662 */     owningEVO.setInternalUserList(theseItems);
/* 663 */     owningEVO.setInternalUserListAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 667 */       stmt = getConnection().prepareStatement("select INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID,INTERNAL_DESTINATION_USERS.USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_TIME,INTERNAL_DESTINATION_USERS.CREATED_TIME from INTERNAL_DESTINATION_USERS where 1=1 and INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID = ? order by  INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID ,INTERNAL_DESTINATION_USERS.USER_ID");
/*     */ 
/* 669 */       int col = 1;
/* 670 */       stmt.setInt(col++, entityPK.getInternalDestinationId());
/*     */ 
/* 672 */       resultSet = stmt.executeQuery();
/*     */ 
/* 675 */       while (resultSet.next())
/*     */       {
/* 677 */         itemCount++;
/* 678 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 680 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 683 */       if (timer != null) {
/* 684 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 689 */       throw handleSQLException("select INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID,INTERNAL_DESTINATION_USERS.USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_TIME,INTERNAL_DESTINATION_USERS.CREATED_TIME from INTERNAL_DESTINATION_USERS where 1=1 and INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID = ? order by  INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID ,INTERNAL_DESTINATION_USERS.USER_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 693 */       closeResultSet(resultSet);
/* 694 */       closeStatement(stmt);
/* 695 */       closeConnection();
/*     */ 
/* 697 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectInternalDestinationId, String dependants, Collection currentList)
/*     */   {
/* 722 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 723 */     PreparedStatement stmt = null;
/* 724 */     ResultSet resultSet = null;
/*     */ 
/* 726 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 730 */       stmt = getConnection().prepareStatement("select INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID,INTERNAL_DESTINATION_USERS.USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_TIME,INTERNAL_DESTINATION_USERS.CREATED_TIME from INTERNAL_DESTINATION_USERS where    INTERNAL_DESTINATION_ID = ? ");
/*     */ 
/* 732 */       int col = 1;
/* 733 */       stmt.setInt(col++, selectInternalDestinationId);
/*     */ 
/* 735 */       resultSet = stmt.executeQuery();
/*     */ 
/* 737 */       while (resultSet.next())
/*     */       {
/* 739 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 742 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 745 */       if (currentList != null)
/*     */       {
/* 748 */         ListIterator iter = items.listIterator();
/* 749 */         InternalDestinationUsersEVO currentEVO = null;
/* 750 */         InternalDestinationUsersEVO newEVO = null;
/* 751 */         while (iter.hasNext())
/*     */         {
/* 753 */           newEVO = (InternalDestinationUsersEVO)iter.next();
/* 754 */           Iterator iter2 = currentList.iterator();
/* 755 */           while (iter2.hasNext())
/*     */           {
/* 757 */             currentEVO = (InternalDestinationUsersEVO)iter2.next();
/* 758 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 760 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 766 */         Iterator iter2 = currentList.iterator();
/* 767 */         while (iter2.hasNext())
/*     */         {
/* 769 */           currentEVO = (InternalDestinationUsersEVO)iter2.next();
/* 770 */           if (currentEVO.insertPending()) {
/* 771 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 775 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 779 */       throw handleSQLException("select INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID,INTERNAL_DESTINATION_USERS.USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,INTERNAL_DESTINATION_USERS.UPDATED_TIME,INTERNAL_DESTINATION_USERS.CREATED_TIME from INTERNAL_DESTINATION_USERS where    INTERNAL_DESTINATION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 783 */       closeResultSet(resultSet);
/* 784 */       closeStatement(stmt);
/* 785 */       closeConnection();
/*     */ 
/* 787 */       if (timer != null) {
/* 788 */         timer.logDebug("getAll", " InternalDestinationId=" + selectInternalDestinationId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 793 */     return items;
/*     */   }
/*     */ 
/*     */   public InternalDestinationUsersEVO getDetails(InternalDestinationCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 807 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 810 */     if (this.mDetails == null) {
/* 811 */       doLoad(((InternalDestinationUsersCK)paramCK).getInternalDestinationUsersPK());
/*     */     }
/* 813 */     else if (!this.mDetails.getPK().equals(((InternalDestinationUsersCK)paramCK).getInternalDestinationUsersPK())) {
/* 814 */       doLoad(((InternalDestinationUsersCK)paramCK).getInternalDestinationUsersPK());
/*     */     }
/*     */ 
/* 817 */     InternalDestinationUsersEVO details = new InternalDestinationUsersEVO();
/* 818 */     details = this.mDetails.deepClone();
/*     */ 
/* 820 */     if (timer != null) {
/* 821 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 823 */     return details;
/*     */   }
/*     */ 
/*     */   public InternalDestinationUsersEVO getDetails(InternalDestinationCK paramCK, InternalDestinationUsersEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 829 */     InternalDestinationUsersEVO savedEVO = this.mDetails;
/* 830 */     this.mDetails = paramEVO;
/* 831 */     InternalDestinationUsersEVO newEVO = getDetails(paramCK, dependants);
/* 832 */     this.mDetails = savedEVO;
/* 833 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public InternalDestinationUsersEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 839 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 843 */     InternalDestinationUsersEVO details = this.mDetails.deepClone();
/*     */ 
/* 845 */     if (timer != null) {
/* 846 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 848 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 853 */     return "InternalDestinationUsers";
/*     */   }
/*     */ 
/*     */   public InternalDestinationUsersRefImpl getRef(InternalDestinationUsersPK paramInternalDestinationUsersPK)
/*     */   {
/* 858 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 859 */     PreparedStatement stmt = null;
/* 860 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 863 */       stmt = getConnection().prepareStatement("select 0,INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID from INTERNAL_DESTINATION_USERS,INTERNAL_DESTINATION where 1=1 and INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID = ? and INTERNAL_DESTINATION_USERS.USER_ID = ? and INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID = INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID");
/* 864 */       int col = 1;
/* 865 */       stmt.setInt(col++, paramInternalDestinationUsersPK.getInternalDestinationId());
/* 866 */       stmt.setInt(col++, paramInternalDestinationUsersPK.getUserId());
/*     */ 
/* 868 */       resultSet = stmt.executeQuery();
/*     */ 
/* 870 */       if (!resultSet.next()) {
/* 871 */         throw new RuntimeException(getEntityName() + " getRef " + paramInternalDestinationUsersPK + " not found");
/*     */       }
/* 873 */       col = 2;
/* 874 */       InternalDestinationPK newInternalDestinationPK = new InternalDestinationPK(resultSet.getInt(col++));
/*     */ 
/* 878 */       String textInternalDestinationUsers = "";
/* 879 */       InternalDestinationUsersCK ckInternalDestinationUsers = new InternalDestinationUsersCK(newInternalDestinationPK, paramInternalDestinationUsersPK);
/*     */ 
/* 884 */       InternalDestinationUsersRefImpl localInternalDestinationUsersRefImpl = new InternalDestinationUsersRefImpl(ckInternalDestinationUsers, textInternalDestinationUsers);
/*     */       return localInternalDestinationUsersRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 889 */       throw handleSQLException(paramInternalDestinationUsersPK, "select 0,INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID from INTERNAL_DESTINATION_USERS,INTERNAL_DESTINATION where 1=1 and INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID = ? and INTERNAL_DESTINATION_USERS.USER_ID = ? and INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID = INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 893 */       closeResultSet(resultSet);
/* 894 */       closeStatement(stmt);
/* 895 */       closeConnection();
/*     */ 
/* 897 */       if (timer != null)
/* 898 */         timer.logDebug("getRef", paramInternalDestinationUsersPK); 
/* 898 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationUsersDAO
 * JD-Core Version:    0.6.0
 */