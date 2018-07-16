/*     */ package com.cedar.cp.ejb.impl.role;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.role.RoleCK;
/*     */ import com.cedar.cp.dto.role.RolePK;
/*     */ import com.cedar.cp.dto.role.RoleSecurityRelCK;
/*     */ import com.cedar.cp.dto.role.RoleSecurityRelPK;
/*     */ import com.cedar.cp.dto.role.RoleSecurityRelRefImpl;
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
/*     */ public class RoleSecurityRelDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select ROLE_SECURITY_REL.ROLE_ID,ROLE_SECURITY_REL.ROLE_SECURITY_ID,ROLE_SECURITY_REL.VERSION_NUM,ROLE_SECURITY_REL.UPDATED_BY_USER_ID,ROLE_SECURITY_REL.UPDATED_TIME,ROLE_SECURITY_REL.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from ROLE_SECURITY_REL where    ROLE_ID = ? AND ROLE_SECURITY_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into ROLE_SECURITY_REL ( ROLE_ID,ROLE_SECURITY_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update ROLE_SECURITY_REL set VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ROLE_ID = ? AND ROLE_SECURITY_ID = ? AND VERSION_NUM = ?";
/*     */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from ROLE_SECURITY_REL where ROLE_ID = ?,ROLE_SECURITY_ID = ?";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from ROLE_SECURITY_REL where    ROLE_ID = ? AND ROLE_SECURITY_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from ROLE_SECURITY_REL where 1=1 and ROLE_SECURITY_REL.ROLE_ID = ? order by  ROLE_SECURITY_REL.ROLE_ID ,ROLE_SECURITY_REL.ROLE_SECURITY_ID";
/*     */   protected static final String SQL_GET_ALL = " from ROLE_SECURITY_REL where    ROLE_ID = ? ";
/*     */   protected RoleSecurityRelEVO mDetails;
/*     */ 
/*     */   public RoleSecurityRelDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public RoleSecurityRelDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public RoleSecurityRelDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected RoleSecurityRelPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(RoleSecurityRelEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private RoleSecurityRelEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  89 */     int col = 1;
/*  90 */     RoleSecurityRelEVO evo = new RoleSecurityRelEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  96 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  97 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  98 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  99 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(RoleSecurityRelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 104 */     int col = startCol_;
/* 105 */     stmt_.setInt(col++, evo_.getRoleId());
/* 106 */     stmt_.setInt(col++, evo_.getRoleSecurityId());
/* 107 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(RoleSecurityRelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 112 */     int col = startCol_;
/* 113 */     stmt_.setInt(col++, evo_.getVersionNum());
/* 114 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 115 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 116 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 117 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(RoleSecurityRelPK pk)
/*     */     throws ValidationException
/*     */   {
/* 134 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 136 */     PreparedStatement stmt = null;
/* 137 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 141 */       stmt = getConnection().prepareStatement("select ROLE_SECURITY_REL.ROLE_ID,ROLE_SECURITY_REL.ROLE_SECURITY_ID,ROLE_SECURITY_REL.VERSION_NUM,ROLE_SECURITY_REL.UPDATED_BY_USER_ID,ROLE_SECURITY_REL.UPDATED_TIME,ROLE_SECURITY_REL.CREATED_TIME from ROLE_SECURITY_REL where    ROLE_ID = ? AND ROLE_SECURITY_ID = ? ");
/*     */ 
/* 144 */       int col = 1;
/* 145 */       stmt.setInt(col++, pk.getRoleId());
/* 146 */       stmt.setInt(col++, pk.getRoleSecurityId());
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
/* 160 */       throw handleSQLException(pk, "select ROLE_SECURITY_REL.ROLE_ID,ROLE_SECURITY_REL.ROLE_SECURITY_ID,ROLE_SECURITY_REL.VERSION_NUM,ROLE_SECURITY_REL.UPDATED_BY_USER_ID,ROLE_SECURITY_REL.UPDATED_TIME,ROLE_SECURITY_REL.CREATED_TIME from ROLE_SECURITY_REL where    ROLE_ID = ? AND ROLE_SECURITY_ID = ? ", sqle);
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
/* 206 */       stmt = getConnection().prepareStatement("insert into ROLE_SECURITY_REL ( ROLE_ID,ROLE_SECURITY_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)");
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
/* 224 */       throw handleSQLException(this.mDetails.getPK(), "insert into ROLE_SECURITY_REL ( ROLE_ID,ROLE_SECURITY_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)", sqle);
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
/* 257 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 261 */     PreparedStatement stmt = null;
/*     */ 
/* 263 */     boolean mainChanged = this.mDetails.isModified();
/* 264 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 267 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 270 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*     */ 
/* 273 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 274 */         stmt = getConnection().prepareStatement("update ROLE_SECURITY_REL set VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ROLE_ID = ? AND ROLE_SECURITY_ID = ? AND VERSION_NUM = ?");
/*     */ 
/* 277 */         int col = 1;
/* 278 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 279 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 281 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*     */ 
/* 284 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 286 */         if (resultCount == 0) {
/* 287 */           checkVersionNum();
/*     */         }
/* 289 */         if (resultCount != 1) {
/* 290 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 293 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 302 */       throw handleSQLException(getPK(), "update ROLE_SECURITY_REL set VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ROLE_ID = ? AND ROLE_SECURITY_ID = ? AND VERSION_NUM = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 306 */       closeStatement(stmt);
/* 307 */       closeConnection();
/*     */ 
/* 309 */       if ((timer != null) && (
/* 310 */         (mainChanged) || (dependantChanged)))
/* 311 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkVersionNum()
/*     */     throws VersionValidationException
/*     */   {
/* 324 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 325 */     PreparedStatement stmt = null;
/* 326 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 330 */       stmt = getConnection().prepareStatement("select VERSION_NUM from ROLE_SECURITY_REL where ROLE_ID = ?,ROLE_SECURITY_ID = ?");
/*     */ 
/* 333 */       int col = 1;
/* 334 */       stmt.setInt(col++, this.mDetails.getRoleId());
/* 335 */       stmt.setInt(col++, this.mDetails.getRoleSecurityId());
/*     */ 
/* 338 */       resultSet = stmt.executeQuery();
/*     */ 
/* 340 */       if (!resultSet.next()) {
/* 341 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 344 */       col = 1;
/* 345 */       int dbVersionNumber = resultSet.getInt(col++);
/* 346 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/* 347 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 353 */       throw handleSQLException(getPK(), "select VERSION_NUM from ROLE_SECURITY_REL where ROLE_ID = ?,ROLE_SECURITY_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 357 */       closeStatement(stmt);
/* 358 */       closeResultSet(resultSet);
/*     */ 
/* 360 */       if (timer != null)
/* 361 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 380 */     if (items == null) {
/* 381 */       return false;
/*     */     }
/* 383 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 384 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 386 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 391 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 392 */       while (iter2.hasNext())
/*     */       {
/* 394 */         this.mDetails = ((RoleSecurityRelEVO)iter2.next());
/*     */ 
/* 397 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 399 */         somethingChanged = true;
/*     */ 
/* 402 */         if (deleteStmt == null) {
/* 403 */           deleteStmt = getConnection().prepareStatement("delete from ROLE_SECURITY_REL where    ROLE_ID = ? AND ROLE_SECURITY_ID = ? ");
/*     */         }
/*     */ 
/* 406 */         int col = 1;
/* 407 */         deleteStmt.setInt(col++, this.mDetails.getRoleId());
/* 408 */         deleteStmt.setInt(col++, this.mDetails.getRoleSecurityId());
/*     */ 
/* 410 */         if (this._log.isDebugEnabled()) {
/* 411 */           this._log.debug("update", "RoleSecurityRel deleting RoleId=" + this.mDetails.getRoleId() + ",RoleSecurityId=" + this.mDetails.getRoleSecurityId());
/*     */         }
/*     */ 
/* 417 */         deleteStmt.addBatch();
/*     */ 
/* 420 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 425 */       if (deleteStmt != null)
/*     */       {
/* 427 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 429 */         deleteStmt.executeBatch();
/*     */ 
/* 431 */         if (timer2 != null) {
/* 432 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 436 */       Iterator iter1 = items.values().iterator();
/* 437 */       while (iter1.hasNext())
/*     */       {
/* 439 */         this.mDetails = ((RoleSecurityRelEVO)iter1.next());
/*     */ 
/* 441 */         if (this.mDetails.insertPending())
/*     */         {
/* 443 */           somethingChanged = true;
/* 444 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 447 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 449 */         somethingChanged = true;
/* 450 */         doStore();
/*     */       }
/*     */ 
/* 461 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 465 */       throw handleSQLException("delete from ROLE_SECURITY_REL where    ROLE_ID = ? AND ROLE_SECURITY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 469 */       if (deleteStmt != null)
/*     */       {
/* 471 */         closeStatement(deleteStmt);
/* 472 */         closeConnection();
/*     */       }
/*     */ 
/* 475 */       this.mDetails = null;
/*     */ 
/* 477 */       if ((somethingChanged) && 
/* 478 */         (timer != null))
/* 479 */         timer.logDebug("update", "collection"); 
/* 479 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(RolePK entityPK, RoleEVO owningEVO, String dependants)
/*     */   {
/* 499 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 501 */     PreparedStatement stmt = null;
/* 502 */     ResultSet resultSet = null;
/*     */ 
/* 504 */     int itemCount = 0;
/*     */ 
/* 506 */     Collection theseItems = new ArrayList();
/* 507 */     owningEVO.setRoleSecurity(theseItems);
/* 508 */     owningEVO.setRoleSecurityAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 512 */       stmt = getConnection().prepareStatement("select ROLE_SECURITY_REL.ROLE_ID,ROLE_SECURITY_REL.ROLE_SECURITY_ID,ROLE_SECURITY_REL.VERSION_NUM,ROLE_SECURITY_REL.UPDATED_BY_USER_ID,ROLE_SECURITY_REL.UPDATED_TIME,ROLE_SECURITY_REL.CREATED_TIME from ROLE_SECURITY_REL where 1=1 and ROLE_SECURITY_REL.ROLE_ID = ? order by  ROLE_SECURITY_REL.ROLE_ID ,ROLE_SECURITY_REL.ROLE_SECURITY_ID");
/*     */ 
/* 514 */       int col = 1;
/* 515 */       stmt.setInt(col++, entityPK.getRoleId());
/*     */ 
/* 517 */       resultSet = stmt.executeQuery();
/*     */ 
/* 520 */       while (resultSet.next())
/*     */       {
/* 522 */         itemCount++;
/* 523 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 525 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 528 */       if (timer != null) {
/* 529 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 534 */       throw handleSQLException("select ROLE_SECURITY_REL.ROLE_ID,ROLE_SECURITY_REL.ROLE_SECURITY_ID,ROLE_SECURITY_REL.VERSION_NUM,ROLE_SECURITY_REL.UPDATED_BY_USER_ID,ROLE_SECURITY_REL.UPDATED_TIME,ROLE_SECURITY_REL.CREATED_TIME from ROLE_SECURITY_REL where 1=1 and ROLE_SECURITY_REL.ROLE_ID = ? order by  ROLE_SECURITY_REL.ROLE_ID ,ROLE_SECURITY_REL.ROLE_SECURITY_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 538 */       closeResultSet(resultSet);
/* 539 */       closeStatement(stmt);
/* 540 */       closeConnection();
/*     */ 
/* 542 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectRoleId, String dependants, Collection currentList)
/*     */   {
/* 567 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 568 */     PreparedStatement stmt = null;
/* 569 */     ResultSet resultSet = null;
/*     */ 
/* 571 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 575 */       stmt = getConnection().prepareStatement("select ROLE_SECURITY_REL.ROLE_ID,ROLE_SECURITY_REL.ROLE_SECURITY_ID,ROLE_SECURITY_REL.VERSION_NUM,ROLE_SECURITY_REL.UPDATED_BY_USER_ID,ROLE_SECURITY_REL.UPDATED_TIME,ROLE_SECURITY_REL.CREATED_TIME from ROLE_SECURITY_REL where    ROLE_ID = ? ");
/*     */ 
/* 577 */       int col = 1;
/* 578 */       stmt.setInt(col++, selectRoleId);
/*     */ 
/* 580 */       resultSet = stmt.executeQuery();
/*     */ 
/* 582 */       while (resultSet.next())
/*     */       {
/* 584 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 587 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 590 */       if (currentList != null)
/*     */       {
/* 593 */         ListIterator iter = items.listIterator();
/* 594 */         RoleSecurityRelEVO currentEVO = null;
/* 595 */         RoleSecurityRelEVO newEVO = null;
/* 596 */         while (iter.hasNext())
/*     */         {
/* 598 */           newEVO = (RoleSecurityRelEVO)iter.next();
/* 599 */           Iterator iter2 = currentList.iterator();
/* 600 */           while (iter2.hasNext())
/*     */           {
/* 602 */             currentEVO = (RoleSecurityRelEVO)iter2.next();
/* 603 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 605 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 611 */         Iterator iter2 = currentList.iterator();
/* 612 */         while (iter2.hasNext())
/*     */         {
/* 614 */           currentEVO = (RoleSecurityRelEVO)iter2.next();
/* 615 */           if (currentEVO.insertPending()) {
/* 616 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 620 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 624 */       throw handleSQLException("select ROLE_SECURITY_REL.ROLE_ID,ROLE_SECURITY_REL.ROLE_SECURITY_ID,ROLE_SECURITY_REL.VERSION_NUM,ROLE_SECURITY_REL.UPDATED_BY_USER_ID,ROLE_SECURITY_REL.UPDATED_TIME,ROLE_SECURITY_REL.CREATED_TIME from ROLE_SECURITY_REL where    ROLE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 628 */       closeResultSet(resultSet);
/* 629 */       closeStatement(stmt);
/* 630 */       closeConnection();
/*     */ 
/* 632 */       if (timer != null) {
/* 633 */         timer.logDebug("getAll", " RoleId=" + selectRoleId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 638 */     return items;
/*     */   }
/*     */ 
/*     */   public RoleSecurityRelEVO getDetails(RoleCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 652 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 655 */     if (this.mDetails == null) {
/* 656 */       doLoad(((RoleSecurityRelCK)paramCK).getRoleSecurityRelPK());
/*     */     }
/* 658 */     else if (!this.mDetails.getPK().equals(((RoleSecurityRelCK)paramCK).getRoleSecurityRelPK())) {
/* 659 */       doLoad(((RoleSecurityRelCK)paramCK).getRoleSecurityRelPK());
/*     */     }
/*     */ 
/* 662 */     RoleSecurityRelEVO details = new RoleSecurityRelEVO();
/* 663 */     details = this.mDetails.deepClone();
/*     */ 
/* 665 */     if (timer != null) {
/* 666 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 668 */     return details;
/*     */   }
/*     */ 
/*     */   public RoleSecurityRelEVO getDetails(RoleCK paramCK, RoleSecurityRelEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 674 */     RoleSecurityRelEVO savedEVO = this.mDetails;
/* 675 */     this.mDetails = paramEVO;
/* 676 */     RoleSecurityRelEVO newEVO = getDetails(paramCK, dependants);
/* 677 */     this.mDetails = savedEVO;
/* 678 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public RoleSecurityRelEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 684 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 688 */     RoleSecurityRelEVO details = this.mDetails.deepClone();
/*     */ 
/* 690 */     if (timer != null) {
/* 691 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 693 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 698 */     return "RoleSecurityRel";
/*     */   }
/*     */ 
/*     */   public RoleSecurityRelRefImpl getRef(RoleSecurityRelPK paramRoleSecurityRelPK)
/*     */   {
/* 703 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 704 */     PreparedStatement stmt = null;
/* 705 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 708 */       stmt = getConnection().prepareStatement("select 0,ROLE.ROLE_ID from ROLE_SECURITY_REL,ROLE where 1=1 and ROLE_SECURITY_REL.ROLE_ID = ? and ROLE_SECURITY_REL.ROLE_SECURITY_ID = ? and ROLE_SECURITY_REL.ROLE_ID = ROLE.ROLE_ID");
/* 709 */       int col = 1;
/* 710 */       stmt.setInt(col++, paramRoleSecurityRelPK.getRoleId());
/* 711 */       stmt.setInt(col++, paramRoleSecurityRelPK.getRoleSecurityId());
/*     */ 
/* 713 */       resultSet = stmt.executeQuery();
/*     */ 
/* 715 */       if (!resultSet.next()) {
/* 716 */         throw new RuntimeException(getEntityName() + " getRef " + paramRoleSecurityRelPK + " not found");
/*     */       }
/* 718 */       col = 2;
/* 719 */       RolePK newRolePK = new RolePK(resultSet.getInt(col++));
/*     */ 
/* 723 */       String textRoleSecurityRel = "";
/* 724 */       RoleSecurityRelCK ckRoleSecurityRel = new RoleSecurityRelCK(newRolePK, paramRoleSecurityRelPK);
/*     */ 
/* 729 */       RoleSecurityRelRefImpl localRoleSecurityRelRefImpl = new RoleSecurityRelRefImpl(ckRoleSecurityRel, textRoleSecurityRel);
/*     */       return localRoleSecurityRelRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 734 */       throw handleSQLException(paramRoleSecurityRelPK, "select 0,ROLE.ROLE_ID from ROLE_SECURITY_REL,ROLE where 1=1 and ROLE_SECURITY_REL.ROLE_ID = ? and ROLE_SECURITY_REL.ROLE_SECURITY_ID = ? and ROLE_SECURITY_REL.ROLE_ID = ROLE.ROLE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 738 */       closeResultSet(resultSet);
/* 739 */       closeStatement(stmt);
/* 740 */       closeConnection();
/*     */ 
/* 742 */       if (timer != null)
/* 743 */         timer.logDebug("getRef", paramRoleSecurityRelPK); 
/* 743 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.role.RoleSecurityRelDAO
 * JD-Core Version:    0.6.0
 */