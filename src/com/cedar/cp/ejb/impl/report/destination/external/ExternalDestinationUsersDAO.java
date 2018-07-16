/*     */ package com.cedar.cp.ejb.impl.report.destination.external;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationCK;
/*     */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
/*     */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersCK;
/*     */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersPK;
/*     */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersRefImpl;
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
/*     */ public class ExternalDestinationUsersDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID,EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID,EXTERNAL_DESTINATION_USERS.EMAIL_ADDRESS,EXTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,EXTERNAL_DESTINATION_USERS.UPDATED_TIME,EXTERNAL_DESTINATION_USERS.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from EXTERNAL_DESTINATION_USERS where    EXTERNAL_DESTINATION_ID = ? AND EXTERNAL_DESTINATION_USERS_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into EXTERNAL_DESTINATION_USERS ( EXTERNAL_DESTINATION_ID,EXTERNAL_DESTINATION_USERS_ID,EMAIL_ADDRESS,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update EXTERNAL_DESTINATION_USERS set EMAIL_ADDRESS = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    EXTERNAL_DESTINATION_ID = ? AND EXTERNAL_DESTINATION_USERS_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from EXTERNAL_DESTINATION_USERS where    EXTERNAL_DESTINATION_ID = ? AND EXTERNAL_DESTINATION_USERS_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from EXTERNAL_DESTINATION_USERS where 1=1 and EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID = ? order by  EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID ,EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID";
/*     */   protected static final String SQL_GET_ALL = " from EXTERNAL_DESTINATION_USERS where    EXTERNAL_DESTINATION_ID = ? ";
/*     */   protected ExternalDestinationUsersEVO mDetails;
/*     */ 
/*     */   public ExternalDestinationUsersDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ExternalDestinationUsersDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ExternalDestinationUsersDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ExternalDestinationUsersPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ExternalDestinationUsersEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private ExternalDestinationUsersEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  89 */     int col = 1;
/*  90 */     ExternalDestinationUsersEVO evo = new ExternalDestinationUsersEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++));
/*     */ 
/*  96 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  97 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  98 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  99 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ExternalDestinationUsersEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 104 */     int col = startCol_;
/* 105 */     stmt_.setInt(col++, evo_.getExternalDestinationId());
/* 106 */     stmt_.setInt(col++, evo_.getExternalDestinationUsersId());
/* 107 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ExternalDestinationUsersEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 112 */     int col = startCol_;
/* 113 */     stmt_.setString(col++, evo_.getEmailAddress());
/* 114 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 115 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 116 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 117 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ExternalDestinationUsersPK pk)
/*     */     throws ValidationException
/*     */   {
/* 134 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 136 */     PreparedStatement stmt = null;
/* 137 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 141 */       stmt = getConnection().prepareStatement("select EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID,EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID,EXTERNAL_DESTINATION_USERS.EMAIL_ADDRESS,EXTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,EXTERNAL_DESTINATION_USERS.UPDATED_TIME,EXTERNAL_DESTINATION_USERS.CREATED_TIME from EXTERNAL_DESTINATION_USERS where    EXTERNAL_DESTINATION_ID = ? AND EXTERNAL_DESTINATION_USERS_ID = ? ");
/*     */ 
/* 144 */       int col = 1;
/* 145 */       stmt.setInt(col++, pk.getExternalDestinationId());
/* 146 */       stmt.setInt(col++, pk.getExternalDestinationUsersId());
/*     */ 
/* 148 */       resultSet = stmt.executeQuery();
/*     */ 
/* 150 */       if (!resultSet.next()) {
/* 151 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 154 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 155 */       if (this.mDetails.isModified())
/* 156 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 160 */       throw handleSQLException(pk, "select EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID,EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID,EXTERNAL_DESTINATION_USERS.EMAIL_ADDRESS,EXTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,EXTERNAL_DESTINATION_USERS.UPDATED_TIME,EXTERNAL_DESTINATION_USERS.CREATED_TIME from EXTERNAL_DESTINATION_USERS where    EXTERNAL_DESTINATION_ID = ? AND EXTERNAL_DESTINATION_USERS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 164 */       closeResultSet(resultSet);
/* 165 */       closeStatement(stmt);
/* 166 */       closeConnection();
/*     */ 
/* 168 */       if (timer != null)
/* 169 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 198 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 199 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 204 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 205 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 206 */       stmt = getConnection().prepareStatement("insert into EXTERNAL_DESTINATION_USERS ( EXTERNAL_DESTINATION_ID,EXTERNAL_DESTINATION_USERS_ID,EMAIL_ADDRESS,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)");
/*     */ 
/* 209 */       int col = 1;
/* 210 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 211 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 214 */       int resultCount = stmt.executeUpdate();
/* 215 */       if (resultCount != 1)
/*     */       {
/* 217 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 220 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 224 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXTERNAL_DESTINATION_USERS ( EXTERNAL_DESTINATION_ID,EXTERNAL_DESTINATION_USERS_ID,EMAIL_ADDRESS,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 228 */       closeStatement(stmt);
/* 229 */       closeConnection();
/*     */ 
/* 231 */       if (timer != null)
/* 232 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 256 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 260 */     PreparedStatement stmt = null;
/*     */ 
/* 262 */     boolean mainChanged = this.mDetails.isModified();
/* 263 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 266 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 269 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 270 */         stmt = getConnection().prepareStatement("update EXTERNAL_DESTINATION_USERS set EMAIL_ADDRESS = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    EXTERNAL_DESTINATION_ID = ? AND EXTERNAL_DESTINATION_USERS_ID = ? ");
/*     */ 
/* 273 */         int col = 1;
/* 274 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 275 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 278 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 281 */         if (resultCount != 1) {
/* 282 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 285 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 294 */       throw handleSQLException(getPK(), "update EXTERNAL_DESTINATION_USERS set EMAIL_ADDRESS = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    EXTERNAL_DESTINATION_ID = ? AND EXTERNAL_DESTINATION_USERS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 298 */       closeStatement(stmt);
/* 299 */       closeConnection();
/*     */ 
/* 301 */       if ((timer != null) && (
/* 302 */         (mainChanged) || (dependantChanged)))
/* 303 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 323 */     if (items == null) {
/* 324 */       return false;
/*     */     }
/* 326 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 327 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 329 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 334 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 335 */       while (iter2.hasNext())
/*     */       {
/* 337 */         this.mDetails = ((ExternalDestinationUsersEVO)iter2.next());
/*     */ 
/* 340 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 342 */         somethingChanged = true;
/*     */ 
/* 345 */         if (deleteStmt == null) {
/* 346 */           deleteStmt = getConnection().prepareStatement("delete from EXTERNAL_DESTINATION_USERS where    EXTERNAL_DESTINATION_ID = ? AND EXTERNAL_DESTINATION_USERS_ID = ? ");
/*     */         }
/*     */ 
/* 349 */         int col = 1;
/* 350 */         deleteStmt.setInt(col++, this.mDetails.getExternalDestinationId());
/* 351 */         deleteStmt.setInt(col++, this.mDetails.getExternalDestinationUsersId());
/*     */ 
/* 353 */         if (this._log.isDebugEnabled()) {
/* 354 */           this._log.debug("update", "ExternalDestinationUsers deleting ExternalDestinationId=" + this.mDetails.getExternalDestinationId() + ",ExternalDestinationUsersId=" + this.mDetails.getExternalDestinationUsersId());
/*     */         }
/*     */ 
/* 360 */         deleteStmt.addBatch();
/*     */ 
/* 363 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 368 */       if (deleteStmt != null)
/*     */       {
/* 370 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 372 */         deleteStmt.executeBatch();
/*     */ 
/* 374 */         if (timer2 != null) {
/* 375 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 379 */       Iterator iter1 = items.values().iterator();
/* 380 */       while (iter1.hasNext())
/*     */       {
/* 382 */         this.mDetails = ((ExternalDestinationUsersEVO)iter1.next());
/*     */ 
/* 384 */         if (this.mDetails.insertPending())
/*     */         {
/* 386 */           somethingChanged = true;
/* 387 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 390 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 392 */         somethingChanged = true;
/* 393 */         doStore();
/*     */       }
/*     */ 
/* 404 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 408 */       throw handleSQLException("delete from EXTERNAL_DESTINATION_USERS where    EXTERNAL_DESTINATION_ID = ? AND EXTERNAL_DESTINATION_USERS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 412 */       if (deleteStmt != null)
/*     */       {
/* 414 */         closeStatement(deleteStmt);
/* 415 */         closeConnection();
/*     */       }
/*     */ 
/* 418 */       this.mDetails = null;
/*     */ 
/* 420 */       if ((somethingChanged) && 
/* 421 */         (timer != null))
/* 422 */         timer.logDebug("update", "collection"); 
/* 422 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ExternalDestinationPK entityPK, ExternalDestinationEVO owningEVO, String dependants)
/*     */   {
/* 442 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 444 */     PreparedStatement stmt = null;
/* 445 */     ResultSet resultSet = null;
/*     */ 
/* 447 */     int itemCount = 0;
/*     */ 
/* 449 */     Collection theseItems = new ArrayList();
/* 450 */     owningEVO.setExternalUserList(theseItems);
/* 451 */     owningEVO.setExternalUserListAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 455 */       stmt = getConnection().prepareStatement("select EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID,EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID,EXTERNAL_DESTINATION_USERS.EMAIL_ADDRESS,EXTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,EXTERNAL_DESTINATION_USERS.UPDATED_TIME,EXTERNAL_DESTINATION_USERS.CREATED_TIME from EXTERNAL_DESTINATION_USERS where 1=1 and EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID = ? order by  EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID ,EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID");
/*     */ 
/* 457 */       int col = 1;
/* 458 */       stmt.setInt(col++, entityPK.getExternalDestinationId());
/*     */ 
/* 460 */       resultSet = stmt.executeQuery();
/*     */ 
/* 463 */       while (resultSet.next())
/*     */       {
/* 465 */         itemCount++;
/* 466 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 468 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 471 */       if (timer != null) {
/* 472 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 477 */       throw handleSQLException("select EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID,EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID,EXTERNAL_DESTINATION_USERS.EMAIL_ADDRESS,EXTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,EXTERNAL_DESTINATION_USERS.UPDATED_TIME,EXTERNAL_DESTINATION_USERS.CREATED_TIME from EXTERNAL_DESTINATION_USERS where 1=1 and EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID = ? order by  EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID ,EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 481 */       closeResultSet(resultSet);
/* 482 */       closeStatement(stmt);
/* 483 */       closeConnection();
/*     */ 
/* 485 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectExternalDestinationId, String dependants, Collection currentList)
/*     */   {
/* 510 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 511 */     PreparedStatement stmt = null;
/* 512 */     ResultSet resultSet = null;
/*     */ 
/* 514 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 518 */       stmt = getConnection().prepareStatement("select EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID,EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID,EXTERNAL_DESTINATION_USERS.EMAIL_ADDRESS,EXTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,EXTERNAL_DESTINATION_USERS.UPDATED_TIME,EXTERNAL_DESTINATION_USERS.CREATED_TIME from EXTERNAL_DESTINATION_USERS where    EXTERNAL_DESTINATION_ID = ? ");
/*     */ 
/* 520 */       int col = 1;
/* 521 */       stmt.setInt(col++, selectExternalDestinationId);
/*     */ 
/* 523 */       resultSet = stmt.executeQuery();
/*     */ 
/* 525 */       while (resultSet.next())
/*     */       {
/* 527 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 530 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 533 */       if (currentList != null)
/*     */       {
/* 536 */         ListIterator iter = items.listIterator();
/* 537 */         ExternalDestinationUsersEVO currentEVO = null;
/* 538 */         ExternalDestinationUsersEVO newEVO = null;
/* 539 */         while (iter.hasNext())
/*     */         {
/* 541 */           newEVO = (ExternalDestinationUsersEVO)iter.next();
/* 542 */           Iterator iter2 = currentList.iterator();
/* 543 */           while (iter2.hasNext())
/*     */           {
/* 545 */             currentEVO = (ExternalDestinationUsersEVO)iter2.next();
/* 546 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 548 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 554 */         Iterator iter2 = currentList.iterator();
/* 555 */         while (iter2.hasNext())
/*     */         {
/* 557 */           currentEVO = (ExternalDestinationUsersEVO)iter2.next();
/* 558 */           if (currentEVO.insertPending()) {
/* 559 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 563 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 567 */       throw handleSQLException("select EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID,EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID,EXTERNAL_DESTINATION_USERS.EMAIL_ADDRESS,EXTERNAL_DESTINATION_USERS.UPDATED_BY_USER_ID,EXTERNAL_DESTINATION_USERS.UPDATED_TIME,EXTERNAL_DESTINATION_USERS.CREATED_TIME from EXTERNAL_DESTINATION_USERS where    EXTERNAL_DESTINATION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 571 */       closeResultSet(resultSet);
/* 572 */       closeStatement(stmt);
/* 573 */       closeConnection();
/*     */ 
/* 575 */       if (timer != null) {
/* 576 */         timer.logDebug("getAll", " ExternalDestinationId=" + selectExternalDestinationId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 581 */     return items;
/*     */   }
/*     */ 
/*     */   public ExternalDestinationUsersEVO getDetails(ExternalDestinationCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 595 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 598 */     if (this.mDetails == null) {
/* 599 */       doLoad(((ExternalDestinationUsersCK)paramCK).getExternalDestinationUsersPK());
/*     */     }
/* 601 */     else if (!this.mDetails.getPK().equals(((ExternalDestinationUsersCK)paramCK).getExternalDestinationUsersPK())) {
/* 602 */       doLoad(((ExternalDestinationUsersCK)paramCK).getExternalDestinationUsersPK());
/*     */     }
/*     */ 
/* 605 */     ExternalDestinationUsersEVO details = new ExternalDestinationUsersEVO();
/* 606 */     details = this.mDetails.deepClone();
/*     */ 
/* 608 */     if (timer != null) {
/* 609 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 611 */     return details;
/*     */   }
/*     */ 
/*     */   public ExternalDestinationUsersEVO getDetails(ExternalDestinationCK paramCK, ExternalDestinationUsersEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 617 */     ExternalDestinationUsersEVO savedEVO = this.mDetails;
/* 618 */     this.mDetails = paramEVO;
/* 619 */     ExternalDestinationUsersEVO newEVO = getDetails(paramCK, dependants);
/* 620 */     this.mDetails = savedEVO;
/* 621 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public ExternalDestinationUsersEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 627 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 631 */     ExternalDestinationUsersEVO details = this.mDetails.deepClone();
/*     */ 
/* 633 */     if (timer != null) {
/* 634 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 636 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 641 */     return "ExternalDestinationUsers";
/*     */   }
/*     */ 
/*     */   public ExternalDestinationUsersRefImpl getRef(ExternalDestinationUsersPK paramExternalDestinationUsersPK)
/*     */   {
/* 646 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 647 */     PreparedStatement stmt = null;
/* 648 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 651 */       stmt = getConnection().prepareStatement("select 0,EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID from EXTERNAL_DESTINATION_USERS,EXTERNAL_DESTINATION where 1=1 and EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID = ? and EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID = ? and EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID = EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID");
/* 652 */       int col = 1;
/* 653 */       stmt.setInt(col++, paramExternalDestinationUsersPK.getExternalDestinationId());
/* 654 */       stmt.setInt(col++, paramExternalDestinationUsersPK.getExternalDestinationUsersId());
/*     */ 
/* 656 */       resultSet = stmt.executeQuery();
/*     */ 
/* 658 */       if (!resultSet.next()) {
/* 659 */         throw new RuntimeException(getEntityName() + " getRef " + paramExternalDestinationUsersPK + " not found");
/*     */       }
/* 661 */       col = 2;
/* 662 */       ExternalDestinationPK newExternalDestinationPK = new ExternalDestinationPK(resultSet.getInt(col++));
/*     */ 
/* 666 */       String textExternalDestinationUsers = "";
/* 667 */       ExternalDestinationUsersCK ckExternalDestinationUsers = new ExternalDestinationUsersCK(newExternalDestinationPK, paramExternalDestinationUsersPK);
/*     */ 
/* 672 */       ExternalDestinationUsersRefImpl localExternalDestinationUsersRefImpl = new ExternalDestinationUsersRefImpl(ckExternalDestinationUsers, textExternalDestinationUsers);
/*     */       return localExternalDestinationUsersRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 677 */       throw handleSQLException(paramExternalDestinationUsersPK, "select 0,EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID from EXTERNAL_DESTINATION_USERS,EXTERNAL_DESTINATION where 1=1 and EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID = ? and EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID = ? and EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID = EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 681 */       closeResultSet(resultSet);
/* 682 */       closeStatement(stmt);
/* 683 */       closeConnection();
/*     */ 
/* 685 */       if (timer != null)
/* 686 */         timer.logDebug("getRef", paramExternalDestinationUsersPK); 
/* 686 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationUsersDAO
 * JD-Core Version:    0.6.0
 */