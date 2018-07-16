/*     */ package com.cedar.cp.ejb.impl.user;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.user.AllRolesForUsersELO;
/*     */ import com.cedar.cp.dto.user.UserCK;
/*     */ import com.cedar.cp.dto.user.UserPK;
/*     */ import com.cedar.cp.dto.user.UserRefImpl;
/*     */ import com.cedar.cp.dto.user.UserRoleCK;
/*     */ import com.cedar.cp.dto.user.UserRolePK;
/*     */ import com.cedar.cp.dto.user.UserRoleRefImpl;
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
/*     */ public class UserRoleDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select USER_ROLE.USER_ID,USER_ROLE.ROLE_ID,USER_ROLE.UPDATED_BY_USER_ID,USER_ROLE.UPDATED_TIME,USER_ROLE.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from USER_ROLE where    USER_ID = ? AND ROLE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into USER_ROLE ( USER_ID,ROLE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update USER_ROLE set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    USER_ID = ? AND ROLE_ID = ? ";
/* 305 */   protected static String SQL_ALL_ROLES_FOR_USERS = "select 0       ,USR.USER_ID      ,USR.NAME      ,USER_ROLE.USER_ID      ,USER_ROLE.ROLE_ID      ,USER_ROLE.ROLE_ID from USER_ROLE    ,USR where 1=1   and USER_ROLE.USER_ID = USR.USER_ID ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from USER_ROLE where    USER_ID = ? AND ROLE_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from USER_ROLE where 1=1 and USER_ROLE.USER_ID = ? order by  USER_ROLE.USER_ID ,USER_ROLE.ROLE_ID";
/*     */   protected static final String SQL_GET_ALL = " from USER_ROLE where    USER_ID = ? ";
/*     */   protected UserRoleEVO mDetails;
/*     */ 
/*     */   public UserRoleDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public UserRoleDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public UserRoleDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected UserRolePK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(UserRoleEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private UserRoleEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  88 */     int col = 1;
/*  89 */     UserRoleEVO evo = new UserRoleEVO(resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  94 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  95 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  96 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  97 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(UserRoleEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 102 */     int col = startCol_;
/* 103 */     stmt_.setInt(col++, evo_.getUserId());
/* 104 */     stmt_.setInt(col++, evo_.getRoleId());
/* 105 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(UserRoleEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 110 */     int col = startCol_;
/* 111 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 112 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 113 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 114 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(UserRolePK pk)
/*     */     throws ValidationException
/*     */   {
/* 131 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 133 */     PreparedStatement stmt = null;
/* 134 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 138 */       stmt = getConnection().prepareStatement("select USER_ROLE.USER_ID,USER_ROLE.ROLE_ID,USER_ROLE.UPDATED_BY_USER_ID,USER_ROLE.UPDATED_TIME,USER_ROLE.CREATED_TIME from USER_ROLE where    USER_ID = ? AND ROLE_ID = ? ");
/*     */ 
/* 141 */       int col = 1;
/* 142 */       stmt.setInt(col++, pk.getUserId());
/* 143 */       stmt.setInt(col++, pk.getRoleId());
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
/* 157 */       throw handleSQLException(pk, "select USER_ROLE.USER_ID,USER_ROLE.ROLE_ID,USER_ROLE.UPDATED_BY_USER_ID,USER_ROLE.UPDATED_TIME,USER_ROLE.CREATED_TIME from USER_ROLE where    USER_ID = ? AND ROLE_ID = ? ", sqle);
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
/* 201 */       stmt = getConnection().prepareStatement("insert into USER_ROLE ( USER_ID,ROLE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)");
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
/* 219 */       throw handleSQLException(this.mDetails.getPK(), "insert into USER_ROLE ( USER_ID,ROLE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)", sqle);
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
/* 264 */         stmt = getConnection().prepareStatement("update USER_ROLE set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    USER_ID = ? AND ROLE_ID = ? ");
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
/* 288 */       throw handleSQLException(getPK(), "update USER_ROLE set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    USER_ID = ? AND ROLE_ID = ? ", sqle);
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
/*     */   public AllRolesForUsersELO getAllRolesForUsers()
/*     */   {
/* 334 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 335 */     PreparedStatement stmt = null;
/* 336 */     ResultSet resultSet = null;
/* 337 */     AllRolesForUsersELO results = new AllRolesForUsersELO();
/*     */     try
/*     */     {
/* 340 */       stmt = getConnection().prepareStatement(SQL_ALL_ROLES_FOR_USERS);
/* 341 */       int col = 1;
/* 342 */       resultSet = stmt.executeQuery();
/* 343 */       while (resultSet.next())
/*     */       {
/* 345 */         col = 2;
/*     */ 
/* 348 */         UserPK pkUser = new UserPK(resultSet.getInt(col++));
/*     */ 
/* 351 */         String textUser = resultSet.getString(col++);
/*     */ 
/* 354 */         UserRolePK pkUserRole = new UserRolePK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 358 */         String textUserRole = "";
/*     */ 
/* 363 */         UserRoleCK ckUserRole = new UserRoleCK(pkUser, pkUserRole);
/*     */ 
/* 369 */         UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
/*     */ 
/* 375 */         UserRoleRefImpl erUserRole = new UserRoleRefImpl(ckUserRole, textUserRole);
/*     */ 
/* 380 */         int col1 = resultSet.getInt(col++);
/*     */ 
/* 383 */         results.add(erUserRole, erUser, col1);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 392 */       throw handleSQLException(SQL_ALL_ROLES_FOR_USERS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 396 */       closeResultSet(resultSet);
/* 397 */       closeStatement(stmt);
/* 398 */       closeConnection();
/*     */     }
/*     */ 
/* 401 */     if (timer != null) {
/* 402 */       timer.logDebug("getAllRolesForUsers", " items=" + results.size());
/*     */     }
/*     */ 
/* 406 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 424 */     if (items == null) {
/* 425 */       return false;
/*     */     }
/* 427 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 428 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 430 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 435 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 436 */       while (iter2.hasNext())
/*     */       {
/* 438 */         this.mDetails = ((UserRoleEVO)iter2.next());
/*     */ 
/* 441 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 443 */         somethingChanged = true;
/*     */ 
/* 446 */         if (deleteStmt == null) {
/* 447 */           deleteStmt = getConnection().prepareStatement("delete from USER_ROLE where    USER_ID = ? AND ROLE_ID = ? ");
/*     */         }
/*     */ 
/* 450 */         int col = 1;
/* 451 */         deleteStmt.setInt(col++, this.mDetails.getUserId());
/* 452 */         deleteStmt.setInt(col++, this.mDetails.getRoleId());
/*     */ 
/* 454 */         if (this._log.isDebugEnabled()) {
/* 455 */           this._log.debug("update", "UserRole deleting UserId=" + this.mDetails.getUserId() + ",RoleId=" + this.mDetails.getRoleId());
/*     */         }
/*     */ 
/* 461 */         deleteStmt.addBatch();
/*     */ 
/* 464 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 469 */       if (deleteStmt != null)
/*     */       {
/* 471 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 473 */         deleteStmt.executeBatch();
/*     */ 
/* 475 */         if (timer2 != null) {
/* 476 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 480 */       Iterator iter1 = items.values().iterator();
/* 481 */       while (iter1.hasNext())
/*     */       {
/* 483 */         this.mDetails = ((UserRoleEVO)iter1.next());
/*     */ 
/* 485 */         if (this.mDetails.insertPending())
/*     */         {
/* 487 */           somethingChanged = true;
/* 488 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 491 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 493 */         somethingChanged = true;
/* 494 */         doStore();
/*     */       }
/*     */ 
/* 505 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 509 */       throw handleSQLException("delete from USER_ROLE where    USER_ID = ? AND ROLE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 513 */       if (deleteStmt != null)
/*     */       {
/* 515 */         closeStatement(deleteStmt);
/* 516 */         closeConnection();
/*     */       }
/*     */ 
/* 519 */       this.mDetails = null;
/*     */ 
/* 521 */       if ((somethingChanged) && 
/* 522 */         (timer != null))
/* 523 */         timer.logDebug("update", "collection"); 
/* 523 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(UserPK entityPK, UserEVO owningEVO, String dependants)
/*     */   {
/* 543 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 545 */     PreparedStatement stmt = null;
/* 546 */     ResultSet resultSet = null;
/*     */ 
/* 548 */     int itemCount = 0;
/*     */ 
/* 550 */     Collection theseItems = new ArrayList();
/* 551 */     owningEVO.setUserRoles(theseItems);
/* 552 */     owningEVO.setUserRolesAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 556 */       stmt = getConnection().prepareStatement("select USER_ROLE.USER_ID,USER_ROLE.ROLE_ID,USER_ROLE.UPDATED_BY_USER_ID,USER_ROLE.UPDATED_TIME,USER_ROLE.CREATED_TIME from USER_ROLE where 1=1 and USER_ROLE.USER_ID = ? order by  USER_ROLE.USER_ID ,USER_ROLE.ROLE_ID");
/*     */ 
/* 558 */       int col = 1;
/* 559 */       stmt.setInt(col++, entityPK.getUserId());
/*     */ 
/* 561 */       resultSet = stmt.executeQuery();
/*     */ 
/* 564 */       while (resultSet.next())
/*     */       {
/* 566 */         itemCount++;
/* 567 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 569 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 572 */       if (timer != null) {
/* 573 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 578 */       throw handleSQLException("select USER_ROLE.USER_ID,USER_ROLE.ROLE_ID,USER_ROLE.UPDATED_BY_USER_ID,USER_ROLE.UPDATED_TIME,USER_ROLE.CREATED_TIME from USER_ROLE where 1=1 and USER_ROLE.USER_ID = ? order by  USER_ROLE.USER_ID ,USER_ROLE.ROLE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 582 */       closeResultSet(resultSet);
/* 583 */       closeStatement(stmt);
/* 584 */       closeConnection();
/*     */ 
/* 586 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectUserId, String dependants, Collection currentList)
/*     */   {
/* 611 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 612 */     PreparedStatement stmt = null;
/* 613 */     ResultSet resultSet = null;
/*     */ 
/* 615 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 619 */       stmt = getConnection().prepareStatement("select USER_ROLE.USER_ID,USER_ROLE.ROLE_ID,USER_ROLE.UPDATED_BY_USER_ID,USER_ROLE.UPDATED_TIME,USER_ROLE.CREATED_TIME from USER_ROLE where    USER_ID = ? ");
/*     */ 
/* 621 */       int col = 1;
/* 622 */       stmt.setInt(col++, selectUserId);
/*     */ 
/* 624 */       resultSet = stmt.executeQuery();
/*     */ 
/* 626 */       while (resultSet.next())
/*     */       {
/* 628 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 631 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 634 */       if (currentList != null)
/*     */       {
/* 637 */         ListIterator iter = items.listIterator();
/* 638 */         UserRoleEVO currentEVO = null;
/* 639 */         UserRoleEVO newEVO = null;
/* 640 */         while (iter.hasNext())
/*     */         {
/* 642 */           newEVO = (UserRoleEVO)iter.next();
/* 643 */           Iterator iter2 = currentList.iterator();
/* 644 */           while (iter2.hasNext())
/*     */           {
/* 646 */             currentEVO = (UserRoleEVO)iter2.next();
/* 647 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 649 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 655 */         Iterator iter2 = currentList.iterator();
/* 656 */         while (iter2.hasNext())
/*     */         {
/* 658 */           currentEVO = (UserRoleEVO)iter2.next();
/* 659 */           if (currentEVO.insertPending()) {
/* 660 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 664 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 668 */       throw handleSQLException("select USER_ROLE.USER_ID,USER_ROLE.ROLE_ID,USER_ROLE.UPDATED_BY_USER_ID,USER_ROLE.UPDATED_TIME,USER_ROLE.CREATED_TIME from USER_ROLE where    USER_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 672 */       closeResultSet(resultSet);
/* 673 */       closeStatement(stmt);
/* 674 */       closeConnection();
/*     */ 
/* 676 */       if (timer != null) {
/* 677 */         timer.logDebug("getAll", " UserId=" + selectUserId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 682 */     return items;
/*     */   }
/*     */ 
/*     */   public UserRoleEVO getDetails(UserCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 696 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 699 */     if (this.mDetails == null) {
/* 700 */       doLoad(((UserRoleCK)paramCK).getUserRolePK());
/*     */     }
/* 702 */     else if (!this.mDetails.getPK().equals(((UserRoleCK)paramCK).getUserRolePK())) {
/* 703 */       doLoad(((UserRoleCK)paramCK).getUserRolePK());
/*     */     }
/*     */ 
/* 706 */     UserRoleEVO details = new UserRoleEVO();
/* 707 */     details = this.mDetails.deepClone();
/*     */ 
/* 709 */     if (timer != null) {
/* 710 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 712 */     return details;
/*     */   }
/*     */ 
/*     */   public UserRoleEVO getDetails(UserCK paramCK, UserRoleEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 718 */     UserRoleEVO savedEVO = this.mDetails;
/* 719 */     this.mDetails = paramEVO;
/* 720 */     UserRoleEVO newEVO = getDetails(paramCK, dependants);
/* 721 */     this.mDetails = savedEVO;
/* 722 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public UserRoleEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 728 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 732 */     UserRoleEVO details = this.mDetails.deepClone();
/*     */ 
/* 734 */     if (timer != null) {
/* 735 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 737 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 742 */     return "UserRole";
/*     */   }
/*     */ 
/*     */   public UserRoleRefImpl getRef(UserRolePK paramUserRolePK)
/*     */   {
/* 747 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 748 */     PreparedStatement stmt = null;
/* 749 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 752 */       stmt = getConnection().prepareStatement("select 0,USR.USER_ID from USER_ROLE,USR where 1=1 and USER_ROLE.USER_ID = ? and USER_ROLE.ROLE_ID = ? and USER_ROLE.USER_ID = USR.USER_ID");
/* 753 */       int col = 1;
/* 754 */       stmt.setInt(col++, paramUserRolePK.getUserId());
/* 755 */       stmt.setInt(col++, paramUserRolePK.getRoleId());
/*     */ 
/* 757 */       resultSet = stmt.executeQuery();
/*     */ 
/* 759 */       if (!resultSet.next()) {
/* 760 */         throw new RuntimeException(getEntityName() + " getRef " + paramUserRolePK + " not found");
/*     */       }
/* 762 */       col = 2;
/* 763 */       UserPK newUserPK = new UserPK(resultSet.getInt(col++));
/*     */ 
/* 767 */       String textUserRole = "";
/* 768 */       UserRoleCK ckUserRole = new UserRoleCK(newUserPK, paramUserRolePK);
/*     */ 
/* 773 */       UserRoleRefImpl localUserRoleRefImpl = new UserRoleRefImpl(ckUserRole, textUserRole);
/*     */       return localUserRoleRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 778 */       throw handleSQLException(paramUserRolePK, "select 0,USR.USER_ID from USER_ROLE,USR where 1=1 and USER_ROLE.USER_ID = ? and USER_ROLE.ROLE_ID = ? and USER_ROLE.USER_ID = USR.USER_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 782 */       closeResultSet(resultSet);
/* 783 */       closeStatement(stmt);
/* 784 */       closeConnection();
/*     */ 
/* 786 */       if (timer != null)
/* 787 */         timer.logDebug("getRef", paramUserRolePK); 
/* 787 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.user.UserRoleDAO
 * JD-Core Version:    0.6.0
 */