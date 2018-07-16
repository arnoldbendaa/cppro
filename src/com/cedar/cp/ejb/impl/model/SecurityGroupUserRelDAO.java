/*     */ package com.cedar.cp.ejb.impl.model;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.AllUsersForASecurityGroupELO;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.ModelRefImpl;
/*     */ import com.cedar.cp.dto.model.SecurityGroupCK;
/*     */ import com.cedar.cp.dto.model.SecurityGroupPK;
/*     */ import com.cedar.cp.dto.model.SecurityGroupRefImpl;
/*     */ import com.cedar.cp.dto.model.SecurityGroupUserRelCK;
/*     */ import com.cedar.cp.dto.model.SecurityGroupUserRelPK;
/*     */ import com.cedar.cp.dto.model.SecurityGroupUserRelRefImpl;
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
/*     */ public class SecurityGroupUserRelDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID,SECURITY_GROUP_USER_REL.USER_ID,SECURITY_GROUP_USER_REL.UPDATED_BY_USER_ID,SECURITY_GROUP_USER_REL.UPDATED_TIME,SECURITY_GROUP_USER_REL.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from SECURITY_GROUP_USER_REL where    SECURITY_GROUP_ID = ? AND USER_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into SECURITY_GROUP_USER_REL ( SECURITY_GROUP_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update SECURITY_GROUP_USER_REL set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_GROUP_ID = ? AND USER_ID = ? ";
/* 305 */   protected static String SQL_ALL_USERS_FOR_A_SECURITY_GROUP = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,SECURITY_GROUP.SECURITY_GROUP_ID      ,SECURITY_GROUP.VIS_ID      ,SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID      ,SECURITY_GROUP_USER_REL.USER_ID      ,SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID      ,SECURITY_GROUP_USER_REL.USER_ID from SECURITY_GROUP_USER_REL    ,MODEL    ,SECURITY_GROUP where 1=1   and SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID = SECURITY_GROUP.SECURITY_GROUP_ID   and SECURITY_GROUP.MODEL_ID = MODEL.MODEL_ID  and  SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID = ?";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from SECURITY_GROUP_USER_REL where    SECURITY_GROUP_ID = ? AND USER_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from SECURITY_GROUP_USER_REL,SECURITY_GROUP where 1=1 and SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID = SECURITY_GROUP.SECURITY_GROUP_ID and SECURITY_GROUP.MODEL_ID = ? order by  SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID ,SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID ,SECURITY_GROUP_USER_REL.USER_ID";
/*     */   protected static final String SQL_GET_ALL = " from SECURITY_GROUP_USER_REL where    SECURITY_GROUP_ID = ? ";
/*     */   protected SecurityGroupUserRelEVO mDetails;
/*     */ 
/*     */   public SecurityGroupUserRelDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public SecurityGroupUserRelDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SecurityGroupUserRelDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected SecurityGroupUserRelPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(SecurityGroupUserRelEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private SecurityGroupUserRelEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  88 */     int col = 1;
/*  89 */     SecurityGroupUserRelEVO evo = new SecurityGroupUserRelEVO(resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  94 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  95 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  96 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  97 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(SecurityGroupUserRelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 102 */     int col = startCol_;
/* 103 */     stmt_.setInt(col++, evo_.getSecurityGroupId());
/* 104 */     stmt_.setInt(col++, evo_.getUserId());
/* 105 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(SecurityGroupUserRelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 110 */     int col = startCol_;
/* 111 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 112 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 113 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 114 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(SecurityGroupUserRelPK pk)
/*     */     throws ValidationException
/*     */   {
/* 131 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 133 */     PreparedStatement stmt = null;
/* 134 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 138 */       stmt = getConnection().prepareStatement("select SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID,SECURITY_GROUP_USER_REL.USER_ID,SECURITY_GROUP_USER_REL.UPDATED_BY_USER_ID,SECURITY_GROUP_USER_REL.UPDATED_TIME,SECURITY_GROUP_USER_REL.CREATED_TIME from SECURITY_GROUP_USER_REL where    SECURITY_GROUP_ID = ? AND USER_ID = ? ");
/*     */ 
/* 141 */       int col = 1;
/* 142 */       stmt.setInt(col++, pk.getSecurityGroupId());
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
/* 157 */       throw handleSQLException(pk, "select SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID,SECURITY_GROUP_USER_REL.USER_ID,SECURITY_GROUP_USER_REL.UPDATED_BY_USER_ID,SECURITY_GROUP_USER_REL.UPDATED_TIME,SECURITY_GROUP_USER_REL.CREATED_TIME from SECURITY_GROUP_USER_REL where    SECURITY_GROUP_ID = ? AND USER_ID = ? ", sqle);
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
/* 201 */       stmt = getConnection().prepareStatement("insert into SECURITY_GROUP_USER_REL ( SECURITY_GROUP_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)");
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
/* 219 */       throw handleSQLException(this.mDetails.getPK(), "insert into SECURITY_GROUP_USER_REL ( SECURITY_GROUP_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)", sqle);
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
/* 264 */         stmt = getConnection().prepareStatement("update SECURITY_GROUP_USER_REL set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_GROUP_ID = ? AND USER_ID = ? ");
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
/* 288 */       throw handleSQLException(getPK(), "update SECURITY_GROUP_USER_REL set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_GROUP_ID = ? AND USER_ID = ? ", sqle);
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
/*     */   public AllUsersForASecurityGroupELO getAllUsersForASecurityGroup(int param1)
/*     */   {
/* 343 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 344 */     PreparedStatement stmt = null;
/* 345 */     ResultSet resultSet = null;
/* 346 */     AllUsersForASecurityGroupELO results = new AllUsersForASecurityGroupELO();
/*     */     try
/*     */     {
/* 349 */       stmt = getConnection().prepareStatement(SQL_ALL_USERS_FOR_A_SECURITY_GROUP);
/* 350 */       int col = 1;
/* 351 */       stmt.setInt(col++, param1);
/* 352 */       resultSet = stmt.executeQuery();
/* 353 */       while (resultSet.next())
/*     */       {
/* 355 */         col = 2;
/*     */ 
/* 358 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 361 */         String textModel = resultSet.getString(col++);
/*     */ 
/* 363 */         SecurityGroupPK pkSecurityGroup = new SecurityGroupPK(resultSet.getInt(col++));
/*     */ 
/* 366 */         String textSecurityGroup = resultSet.getString(col++);
/*     */ 
/* 369 */         SecurityGroupUserRelPK pkSecurityGroupUserRel = new SecurityGroupUserRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 373 */         String textSecurityGroupUserRel = "";
/*     */ 
/* 378 */         SecurityGroupCK ckSecurityGroup = new SecurityGroupCK(pkModel, pkSecurityGroup);
/*     */ 
/* 384 */         SecurityGroupUserRelCK ckSecurityGroupUserRel = new SecurityGroupUserRelCK(pkModel, pkSecurityGroup, pkSecurityGroupUserRel);
/*     */ 
/* 391 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*     */ 
/* 397 */         SecurityGroupRefImpl erSecurityGroup = new SecurityGroupRefImpl(ckSecurityGroup, textSecurityGroup);
/*     */ 
/* 403 */         SecurityGroupUserRelRefImpl erSecurityGroupUserRel = new SecurityGroupUserRelRefImpl(ckSecurityGroupUserRel, textSecurityGroupUserRel);
/*     */ 
/* 408 */         int col1 = resultSet.getInt(col++);
/* 409 */         int col2 = resultSet.getInt(col++);
/*     */ 
/* 412 */         results.add(erSecurityGroupUserRel, erSecurityGroup, erModel, col1, col2);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 423 */       throw handleSQLException(SQL_ALL_USERS_FOR_A_SECURITY_GROUP, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 427 */       closeResultSet(resultSet);
/* 428 */       closeStatement(stmt);
/* 429 */       closeConnection();
/*     */     }
/*     */ 
/* 432 */     if (timer != null) {
/* 433 */       timer.logDebug("getAllUsersForASecurityGroup", " SecurityGroupId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 438 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 456 */     if (items == null) {
/* 457 */       return false;
/*     */     }
/* 459 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 460 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 462 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 467 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 468 */       while (iter2.hasNext())
/*     */       {
/* 470 */         this.mDetails = ((SecurityGroupUserRelEVO)iter2.next());
/*     */ 
/* 473 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 475 */         somethingChanged = true;
/*     */ 
/* 478 */         if (deleteStmt == null) {
/* 479 */           deleteStmt = getConnection().prepareStatement("delete from SECURITY_GROUP_USER_REL where    SECURITY_GROUP_ID = ? AND USER_ID = ? ");
/*     */         }
/*     */ 
/* 482 */         int col = 1;
/* 483 */         deleteStmt.setInt(col++, this.mDetails.getSecurityGroupId());
/* 484 */         deleteStmt.setInt(col++, this.mDetails.getUserId());
/*     */ 
/* 486 */         if (this._log.isDebugEnabled()) {
/* 487 */           this._log.debug("update", "SecurityGroupUserRel deleting SecurityGroupId=" + this.mDetails.getSecurityGroupId() + ",UserId=" + this.mDetails.getUserId());
/*     */         }
/*     */ 
/* 493 */         deleteStmt.addBatch();
/*     */ 
/* 496 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 501 */       if (deleteStmt != null)
/*     */       {
/* 503 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 505 */         deleteStmt.executeBatch();
/*     */ 
/* 507 */         if (timer2 != null) {
/* 508 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 512 */       Iterator iter1 = items.values().iterator();
/* 513 */       while (iter1.hasNext())
/*     */       {
/* 515 */         this.mDetails = ((SecurityGroupUserRelEVO)iter1.next());
/*     */ 
/* 517 */         if (this.mDetails.insertPending())
/*     */         {
/* 519 */           somethingChanged = true;
/* 520 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 523 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 525 */         somethingChanged = true;
/* 526 */         doStore();
/*     */       }
/*     */ 
/* 537 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 541 */       throw handleSQLException("delete from SECURITY_GROUP_USER_REL where    SECURITY_GROUP_ID = ? AND USER_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 545 */       if (deleteStmt != null)
/*     */       {
/* 547 */         closeStatement(deleteStmt);
/* 548 */         closeConnection();
/*     */       }
/*     */ 
/* 551 */       this.mDetails = null;
/*     */ 
/* 553 */       if ((somethingChanged) && 
/* 554 */         (timer != null))
/* 555 */         timer.logDebug("update", "collection"); 
/* 555 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 579 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 581 */     PreparedStatement stmt = null;
/* 582 */     ResultSet resultSet = null;
/*     */ 
/* 584 */     int itemCount = 0;
/*     */ 
/* 586 */     SecurityGroupEVO owningEVO = null;
/* 587 */     Iterator ownersIter = owners.iterator();
/* 588 */     while (ownersIter.hasNext())
/*     */     {
/* 590 */       owningEVO = (SecurityGroupEVO)ownersIter.next();
/* 591 */       owningEVO.setUsersInGroupAllItemsLoaded(true);
/*     */     }
/* 593 */     ownersIter = owners.iterator();
/* 594 */     owningEVO = (SecurityGroupEVO)ownersIter.next();
/* 595 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 599 */       stmt = getConnection().prepareStatement("select SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID,SECURITY_GROUP_USER_REL.USER_ID,SECURITY_GROUP_USER_REL.UPDATED_BY_USER_ID,SECURITY_GROUP_USER_REL.UPDATED_TIME,SECURITY_GROUP_USER_REL.CREATED_TIME from SECURITY_GROUP_USER_REL,SECURITY_GROUP where 1=1 and SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID = SECURITY_GROUP.SECURITY_GROUP_ID and SECURITY_GROUP.MODEL_ID = ? order by  SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID ,SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID ,SECURITY_GROUP_USER_REL.USER_ID");
/*     */ 
/* 601 */       int col = 1;
/* 602 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 604 */       resultSet = stmt.executeQuery();
/*     */ 
/* 607 */       while (resultSet.next())
/*     */       {
/* 609 */         itemCount++;
/* 610 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 615 */         while (this.mDetails.getSecurityGroupId() != owningEVO.getSecurityGroupId())
/*     */         {
/* 619 */           if (!ownersIter.hasNext())
/*     */           {
/* 621 */             this._log.debug("bulkGetAll", "can't find owning [SecurityGroupId=" + this.mDetails.getSecurityGroupId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 625 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 626 */             ownersIter = owners.iterator();
/* 627 */             while (ownersIter.hasNext())
/*     */             {
/* 629 */               owningEVO = (SecurityGroupEVO)ownersIter.next();
/* 630 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 632 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 634 */           owningEVO = (SecurityGroupEVO)ownersIter.next();
/*     */         }
/* 636 */         if (owningEVO.getUsersInGroup() == null)
/*     */         {
/* 638 */           theseItems = new ArrayList();
/* 639 */           owningEVO.setUsersInGroup(theseItems);
/* 640 */           owningEVO.setUsersInGroupAllItemsLoaded(true);
/*     */         }
/* 642 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 645 */       if (timer != null) {
/* 646 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 651 */       throw handleSQLException("select SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID,SECURITY_GROUP_USER_REL.USER_ID,SECURITY_GROUP_USER_REL.UPDATED_BY_USER_ID,SECURITY_GROUP_USER_REL.UPDATED_TIME,SECURITY_GROUP_USER_REL.CREATED_TIME from SECURITY_GROUP_USER_REL,SECURITY_GROUP where 1=1 and SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID = SECURITY_GROUP.SECURITY_GROUP_ID and SECURITY_GROUP.MODEL_ID = ? order by  SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID ,SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID ,SECURITY_GROUP_USER_REL.USER_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 655 */       closeResultSet(resultSet);
/* 656 */       closeStatement(stmt);
/* 657 */       closeConnection();
/*     */ 
/* 659 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectSecurityGroupId, String dependants, Collection currentList)
/*     */   {
/* 684 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 685 */     PreparedStatement stmt = null;
/* 686 */     ResultSet resultSet = null;
/*     */ 
/* 688 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 692 */       stmt = getConnection().prepareStatement("select SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID,SECURITY_GROUP_USER_REL.USER_ID,SECURITY_GROUP_USER_REL.UPDATED_BY_USER_ID,SECURITY_GROUP_USER_REL.UPDATED_TIME,SECURITY_GROUP_USER_REL.CREATED_TIME from SECURITY_GROUP_USER_REL where    SECURITY_GROUP_ID = ? ");
/*     */ 
/* 694 */       int col = 1;
/* 695 */       stmt.setInt(col++, selectSecurityGroupId);
/*     */ 
/* 697 */       resultSet = stmt.executeQuery();
/*     */ 
/* 699 */       while (resultSet.next())
/*     */       {
/* 701 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 704 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 707 */       if (currentList != null)
/*     */       {
/* 710 */         ListIterator iter = items.listIterator();
/* 711 */         SecurityGroupUserRelEVO currentEVO = null;
/* 712 */         SecurityGroupUserRelEVO newEVO = null;
/* 713 */         while (iter.hasNext())
/*     */         {
/* 715 */           newEVO = (SecurityGroupUserRelEVO)iter.next();
/* 716 */           Iterator iter2 = currentList.iterator();
/* 717 */           while (iter2.hasNext())
/*     */           {
/* 719 */             currentEVO = (SecurityGroupUserRelEVO)iter2.next();
/* 720 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 722 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 728 */         Iterator iter2 = currentList.iterator();
/* 729 */         while (iter2.hasNext())
/*     */         {
/* 731 */           currentEVO = (SecurityGroupUserRelEVO)iter2.next();
/* 732 */           if (currentEVO.insertPending()) {
/* 733 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 737 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 741 */       throw handleSQLException("select SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID,SECURITY_GROUP_USER_REL.USER_ID,SECURITY_GROUP_USER_REL.UPDATED_BY_USER_ID,SECURITY_GROUP_USER_REL.UPDATED_TIME,SECURITY_GROUP_USER_REL.CREATED_TIME from SECURITY_GROUP_USER_REL where    SECURITY_GROUP_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 745 */       closeResultSet(resultSet);
/* 746 */       closeStatement(stmt);
/* 747 */       closeConnection();
/*     */ 
/* 749 */       if (timer != null) {
/* 750 */         timer.logDebug("getAll", " SecurityGroupId=" + selectSecurityGroupId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 755 */     return items;
/*     */   }
/*     */ 
/*     */   public SecurityGroupUserRelEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 769 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 772 */     if (this.mDetails == null) {
/* 773 */       doLoad(((SecurityGroupUserRelCK)paramCK).getSecurityGroupUserRelPK());
/*     */     }
/* 775 */     else if (!this.mDetails.getPK().equals(((SecurityGroupUserRelCK)paramCK).getSecurityGroupUserRelPK())) {
/* 776 */       doLoad(((SecurityGroupUserRelCK)paramCK).getSecurityGroupUserRelPK());
/*     */     }
/*     */ 
/* 779 */     SecurityGroupUserRelEVO details = new SecurityGroupUserRelEVO();
/* 780 */     details = this.mDetails.deepClone();
/*     */ 
/* 782 */     if (timer != null) {
/* 783 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 785 */     return details;
/*     */   }
/*     */ 
/*     */   public SecurityGroupUserRelEVO getDetails(ModelCK paramCK, SecurityGroupUserRelEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 791 */     SecurityGroupUserRelEVO savedEVO = this.mDetails;
/* 792 */     this.mDetails = paramEVO;
/* 793 */     SecurityGroupUserRelEVO newEVO = getDetails(paramCK, dependants);
/* 794 */     this.mDetails = savedEVO;
/* 795 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public SecurityGroupUserRelEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 801 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 805 */     SecurityGroupUserRelEVO details = this.mDetails.deepClone();
/*     */ 
/* 807 */     if (timer != null) {
/* 808 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 810 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 815 */     return "SecurityGroupUserRel";
/*     */   }
/*     */ 
/*     */   public SecurityGroupUserRelRefImpl getRef(SecurityGroupUserRelPK paramSecurityGroupUserRelPK)
/*     */   {
/* 820 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 821 */     PreparedStatement stmt = null;
/* 822 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 825 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,SECURITY_GROUP.SECURITY_GROUP_ID from SECURITY_GROUP_USER_REL,MODEL,SECURITY_GROUP where 1=1 and SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID = ? and SECURITY_GROUP_USER_REL.USER_ID = ? and SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID = SECURITY_GROUP.SECURITY_GROUP_ID and SECURITY_GROUP.SECURITY_GROUP_ID = MODEL.SECURITY_GROUP_ID");
/* 826 */       int col = 1;
/* 827 */       stmt.setInt(col++, paramSecurityGroupUserRelPK.getSecurityGroupId());
/* 828 */       stmt.setInt(col++, paramSecurityGroupUserRelPK.getUserId());
/*     */ 
/* 830 */       resultSet = stmt.executeQuery();
/*     */ 
/* 832 */       if (!resultSet.next()) {
/* 833 */         throw new RuntimeException(getEntityName() + " getRef " + paramSecurityGroupUserRelPK + " not found");
/*     */       }
/* 835 */       col = 2;
/* 836 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 840 */       SecurityGroupPK newSecurityGroupPK = new SecurityGroupPK(resultSet.getInt(col++));
/*     */ 
/* 844 */       String textSecurityGroupUserRel = "";
/* 845 */       SecurityGroupUserRelCK ckSecurityGroupUserRel = new SecurityGroupUserRelCK(newModelPK, newSecurityGroupPK, paramSecurityGroupUserRelPK);
/*     */ 
/* 851 */       SecurityGroupUserRelRefImpl localSecurityGroupUserRelRefImpl = new SecurityGroupUserRelRefImpl(ckSecurityGroupUserRel, textSecurityGroupUserRel);
/*     */       return localSecurityGroupUserRelRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 856 */       throw handleSQLException(paramSecurityGroupUserRelPK, "select 0,MODEL.MODEL_ID,SECURITY_GROUP.SECURITY_GROUP_ID from SECURITY_GROUP_USER_REL,MODEL,SECURITY_GROUP where 1=1 and SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID = ? and SECURITY_GROUP_USER_REL.USER_ID = ? and SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID = SECURITY_GROUP.SECURITY_GROUP_ID and SECURITY_GROUP.SECURITY_GROUP_ID = MODEL.SECURITY_GROUP_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 860 */       closeResultSet(resultSet);
/* 861 */       closeStatement(stmt);
/* 862 */       closeConnection();
/*     */ 
/* 864 */       if (timer != null)
/* 865 */         timer.logDebug("getRef", paramSecurityGroupUserRelPK); 
/* 865 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.SecurityGroupUserRelDAO
 * JD-Core Version:    0.6.0
 */