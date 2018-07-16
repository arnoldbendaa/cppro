/*     */ package com.cedar.cp.ejb.impl.model.virement;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementAuthPointPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementAuthorisersCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementAuthorisersPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementAuthorisersRefImpl;
/*     */ import com.cedar.cp.dto.model.virement.VirementRequestPK;
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
/*     */ public class VirementAuthorisersDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select VIREMENT_AUTHORISERS.AUTH_POINT_ID,VIREMENT_AUTHORISERS.USER_ID,VIREMENT_AUTHORISERS.UPDATED_BY_USER_ID,VIREMENT_AUTHORISERS.UPDATED_TIME,VIREMENT_AUTHORISERS.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from VIREMENT_AUTHORISERS where    AUTH_POINT_ID = ? AND USER_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into VIREMENT_AUTHORISERS ( AUTH_POINT_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update VIREMENT_AUTHORISERS set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AUTH_POINT_ID = ? AND USER_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from VIREMENT_AUTHORISERS where    AUTH_POINT_ID = ? AND USER_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from VIREMENT_AUTHORISERS,VIREMENT_AUTH_POINT,VIREMENT_REQUEST where 1=1 and VIREMENT_AUTHORISERS.AUTH_POINT_ID = VIREMENT_AUTH_POINT.AUTH_POINT_ID and VIREMENT_AUTH_POINT.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_AUTHORISERS.AUTH_POINT_ID ,VIREMENT_AUTHORISERS.AUTH_POINT_ID ,VIREMENT_AUTHORISERS.USER_ID";
/*     */   protected static final String SQL_GET_ALL = " from VIREMENT_AUTHORISERS where    AUTH_POINT_ID = ? ";
/*     */   protected VirementAuthorisersEVO mDetails;
/*     */ 
/*     */   public VirementAuthorisersDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public VirementAuthorisersDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public VirementAuthorisersDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected VirementAuthorisersPK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(VirementAuthorisersEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private VirementAuthorisersEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  91 */     int col = 1;
/*  92 */     VirementAuthorisersEVO evo = new VirementAuthorisersEVO(resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  97 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  98 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  99 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 100 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(VirementAuthorisersEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 105 */     int col = startCol_;
/* 106 */     stmt_.setInt(col++, evo_.getAuthPointId());
/* 107 */     stmt_.setInt(col++, evo_.getUserId());
/* 108 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(VirementAuthorisersEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 113 */     int col = startCol_;
/* 114 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 115 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 116 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 117 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(VirementAuthorisersPK pk)
/*     */     throws ValidationException
/*     */   {
/* 134 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 136 */     PreparedStatement stmt = null;
/* 137 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 141 */       stmt = getConnection().prepareStatement("select VIREMENT_AUTHORISERS.AUTH_POINT_ID,VIREMENT_AUTHORISERS.USER_ID,VIREMENT_AUTHORISERS.UPDATED_BY_USER_ID,VIREMENT_AUTHORISERS.UPDATED_TIME,VIREMENT_AUTHORISERS.CREATED_TIME from VIREMENT_AUTHORISERS where    AUTH_POINT_ID = ? AND USER_ID = ? ");
/*     */ 
/* 144 */       int col = 1;
/* 145 */       stmt.setInt(col++, pk.getAuthPointId());
/* 146 */       stmt.setInt(col++, pk.getUserId());
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
/* 160 */       throw handleSQLException(pk, "select VIREMENT_AUTHORISERS.AUTH_POINT_ID,VIREMENT_AUTHORISERS.USER_ID,VIREMENT_AUTHORISERS.UPDATED_BY_USER_ID,VIREMENT_AUTHORISERS.UPDATED_TIME,VIREMENT_AUTHORISERS.CREATED_TIME from VIREMENT_AUTHORISERS where    AUTH_POINT_ID = ? AND USER_ID = ? ", sqle);
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
/* 196 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 197 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 202 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 203 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 204 */       stmt = getConnection().prepareStatement("insert into VIREMENT_AUTHORISERS ( AUTH_POINT_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)");
/*     */ 
/* 207 */       int col = 1;
/* 208 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 209 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 212 */       int resultCount = stmt.executeUpdate();
/* 213 */       if (resultCount != 1)
/*     */       {
/* 215 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 218 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 222 */       throw handleSQLException(this.mDetails.getPK(), "insert into VIREMENT_AUTHORISERS ( AUTH_POINT_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 226 */       closeStatement(stmt);
/* 227 */       closeConnection();
/*     */ 
/* 229 */       if (timer != null)
/* 230 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 253 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 257 */     PreparedStatement stmt = null;
/*     */ 
/* 259 */     boolean mainChanged = this.mDetails.isModified();
/* 260 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 263 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 266 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 267 */         stmt = getConnection().prepareStatement("update VIREMENT_AUTHORISERS set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AUTH_POINT_ID = ? AND USER_ID = ? ");
/*     */ 
/* 270 */         int col = 1;
/* 271 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 272 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 275 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 278 */         if (resultCount != 1) {
/* 279 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 282 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 291 */       throw handleSQLException(getPK(), "update VIREMENT_AUTHORISERS set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AUTH_POINT_ID = ? AND USER_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 295 */       closeStatement(stmt);
/* 296 */       closeConnection();
/*     */ 
/* 298 */       if ((timer != null) && (
/* 299 */         (mainChanged) || (dependantChanged)))
/* 300 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 320 */     if (items == null) {
/* 321 */       return false;
/*     */     }
/* 323 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 324 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 326 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 331 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 332 */       while (iter2.hasNext())
/*     */       {
/* 334 */         this.mDetails = ((VirementAuthorisersEVO)iter2.next());
/*     */ 
/* 337 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 339 */         somethingChanged = true;
/*     */ 
/* 342 */         if (deleteStmt == null) {
/* 343 */           deleteStmt = getConnection().prepareStatement("delete from VIREMENT_AUTHORISERS where    AUTH_POINT_ID = ? AND USER_ID = ? ");
/*     */         }
/*     */ 
/* 346 */         int col = 1;
/* 347 */         deleteStmt.setInt(col++, this.mDetails.getAuthPointId());
/* 348 */         deleteStmt.setInt(col++, this.mDetails.getUserId());
/*     */ 
/* 350 */         if (this._log.isDebugEnabled()) {
/* 351 */           this._log.debug("update", "VirementAuthorisers deleting AuthPointId=" + this.mDetails.getAuthPointId() + ",UserId=" + this.mDetails.getUserId());
/*     */         }
/*     */ 
/* 357 */         deleteStmt.addBatch();
/*     */ 
/* 360 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 365 */       if (deleteStmt != null)
/*     */       {
/* 367 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 369 */         deleteStmt.executeBatch();
/*     */ 
/* 371 */         if (timer2 != null) {
/* 372 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 376 */       Iterator iter1 = items.values().iterator();
/* 377 */       while (iter1.hasNext())
/*     */       {
/* 379 */         this.mDetails = ((VirementAuthorisersEVO)iter1.next());
/*     */ 
/* 381 */         if (this.mDetails.insertPending())
/*     */         {
/* 383 */           somethingChanged = true;
/* 384 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 387 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 389 */         somethingChanged = true;
/* 390 */         doStore();
/*     */       }
/*     */ 
/* 401 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 405 */       throw handleSQLException("delete from VIREMENT_AUTHORISERS where    AUTH_POINT_ID = ? AND USER_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 409 */       if (deleteStmt != null)
/*     */       {
/* 411 */         closeStatement(deleteStmt);
/* 412 */         closeConnection();
/*     */       }
/*     */ 
/* 415 */       this.mDetails = null;
/*     */ 
/* 417 */       if ((somethingChanged) && 
/* 418 */         (timer != null))
/* 419 */         timer.logDebug("update", "collection"); 
/* 419 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 446 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 448 */     PreparedStatement stmt = null;
/* 449 */     ResultSet resultSet = null;
/*     */ 
/* 451 */     int itemCount = 0;
/*     */ 
/* 453 */     VirementAuthPointEVO owningEVO = null;
/* 454 */     Iterator ownersIter = owners.iterator();
/* 455 */     while (ownersIter.hasNext())
/*     */     {
/* 457 */       owningEVO = (VirementAuthPointEVO)ownersIter.next();
/* 458 */       owningEVO.setAuthUsersAllItemsLoaded(true);
/*     */     }
/* 460 */     ownersIter = owners.iterator();
/* 461 */     owningEVO = (VirementAuthPointEVO)ownersIter.next();
/* 462 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 466 */       stmt = getConnection().prepareStatement("select VIREMENT_AUTHORISERS.AUTH_POINT_ID,VIREMENT_AUTHORISERS.USER_ID,VIREMENT_AUTHORISERS.UPDATED_BY_USER_ID,VIREMENT_AUTHORISERS.UPDATED_TIME,VIREMENT_AUTHORISERS.CREATED_TIME from VIREMENT_AUTHORISERS,VIREMENT_AUTH_POINT,VIREMENT_REQUEST where 1=1 and VIREMENT_AUTHORISERS.AUTH_POINT_ID = VIREMENT_AUTH_POINT.AUTH_POINT_ID and VIREMENT_AUTH_POINT.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_AUTHORISERS.AUTH_POINT_ID ,VIREMENT_AUTHORISERS.AUTH_POINT_ID ,VIREMENT_AUTHORISERS.USER_ID");
/*     */ 
/* 468 */       int col = 1;
/* 469 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 471 */       resultSet = stmt.executeQuery();
/*     */ 
/* 474 */       while (resultSet.next())
/*     */       {
/* 476 */         itemCount++;
/* 477 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 482 */         while (this.mDetails.getAuthPointId() != owningEVO.getAuthPointId())
/*     */         {
/* 486 */           if (!ownersIter.hasNext())
/*     */           {
/* 488 */             this._log.debug("bulkGetAll", "can't find owning [AuthPointId=" + this.mDetails.getAuthPointId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 492 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 493 */             ownersIter = owners.iterator();
/* 494 */             while (ownersIter.hasNext())
/*     */             {
/* 496 */               owningEVO = (VirementAuthPointEVO)ownersIter.next();
/* 497 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 499 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 501 */           owningEVO = (VirementAuthPointEVO)ownersIter.next();
/*     */         }
/* 503 */         if (owningEVO.getAuthUsers() == null)
/*     */         {
/* 505 */           theseItems = new ArrayList();
/* 506 */           owningEVO.setAuthUsers(theseItems);
/* 507 */           owningEVO.setAuthUsersAllItemsLoaded(true);
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
/* 518 */       throw handleSQLException("select VIREMENT_AUTHORISERS.AUTH_POINT_ID,VIREMENT_AUTHORISERS.USER_ID,VIREMENT_AUTHORISERS.UPDATED_BY_USER_ID,VIREMENT_AUTHORISERS.UPDATED_TIME,VIREMENT_AUTHORISERS.CREATED_TIME from VIREMENT_AUTHORISERS,VIREMENT_AUTH_POINT,VIREMENT_REQUEST where 1=1 and VIREMENT_AUTHORISERS.AUTH_POINT_ID = VIREMENT_AUTH_POINT.AUTH_POINT_ID and VIREMENT_AUTH_POINT.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_AUTHORISERS.AUTH_POINT_ID ,VIREMENT_AUTHORISERS.AUTH_POINT_ID ,VIREMENT_AUTHORISERS.USER_ID", sqle);
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
/*     */   public Collection getAll(int selectAuthPointId, String dependants, Collection currentList)
/*     */   {
/* 551 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 552 */     PreparedStatement stmt = null;
/* 553 */     ResultSet resultSet = null;
/*     */ 
/* 555 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 559 */       stmt = getConnection().prepareStatement("select VIREMENT_AUTHORISERS.AUTH_POINT_ID,VIREMENT_AUTHORISERS.USER_ID,VIREMENT_AUTHORISERS.UPDATED_BY_USER_ID,VIREMENT_AUTHORISERS.UPDATED_TIME,VIREMENT_AUTHORISERS.CREATED_TIME from VIREMENT_AUTHORISERS where    AUTH_POINT_ID = ? ");
/*     */ 
/* 561 */       int col = 1;
/* 562 */       stmt.setInt(col++, selectAuthPointId);
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
/* 578 */         VirementAuthorisersEVO currentEVO = null;
/* 579 */         VirementAuthorisersEVO newEVO = null;
/* 580 */         while (iter.hasNext())
/*     */         {
/* 582 */           newEVO = (VirementAuthorisersEVO)iter.next();
/* 583 */           Iterator iter2 = currentList.iterator();
/* 584 */           while (iter2.hasNext())
/*     */           {
/* 586 */             currentEVO = (VirementAuthorisersEVO)iter2.next();
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
/* 598 */           currentEVO = (VirementAuthorisersEVO)iter2.next();
/* 599 */           if (currentEVO.insertPending()) {
/* 600 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 604 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 608 */       throw handleSQLException("select VIREMENT_AUTHORISERS.AUTH_POINT_ID,VIREMENT_AUTHORISERS.USER_ID,VIREMENT_AUTHORISERS.UPDATED_BY_USER_ID,VIREMENT_AUTHORISERS.UPDATED_TIME,VIREMENT_AUTHORISERS.CREATED_TIME from VIREMENT_AUTHORISERS where    AUTH_POINT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 612 */       closeResultSet(resultSet);
/* 613 */       closeStatement(stmt);
/* 614 */       closeConnection();
/*     */ 
/* 616 */       if (timer != null) {
/* 617 */         timer.logDebug("getAll", " AuthPointId=" + selectAuthPointId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 622 */     return items;
/*     */   }
/*     */ 
/*     */   public VirementAuthorisersEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 636 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 639 */     if (this.mDetails == null) {
/* 640 */       doLoad(((VirementAuthorisersCK)paramCK).getVirementAuthorisersPK());
/*     */     }
/* 642 */     else if (!this.mDetails.getPK().equals(((VirementAuthorisersCK)paramCK).getVirementAuthorisersPK())) {
/* 643 */       doLoad(((VirementAuthorisersCK)paramCK).getVirementAuthorisersPK());
/*     */     }
/*     */ 
/* 646 */     VirementAuthorisersEVO details = new VirementAuthorisersEVO();
/* 647 */     details = this.mDetails.deepClone();
/*     */ 
/* 649 */     if (timer != null) {
/* 650 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 652 */     return details;
/*     */   }
/*     */ 
/*     */   public VirementAuthorisersEVO getDetails(ModelCK paramCK, VirementAuthorisersEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 658 */     VirementAuthorisersEVO savedEVO = this.mDetails;
/* 659 */     this.mDetails = paramEVO;
/* 660 */     VirementAuthorisersEVO newEVO = getDetails(paramCK, dependants);
/* 661 */     this.mDetails = savedEVO;
/* 662 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public VirementAuthorisersEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 668 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 672 */     VirementAuthorisersEVO details = this.mDetails.deepClone();
/*     */ 
/* 674 */     if (timer != null) {
/* 675 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 677 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 682 */     return "VirementAuthorisers";
/*     */   }
/*     */ 
/*     */   public VirementAuthorisersRefImpl getRef(VirementAuthorisersPK paramVirementAuthorisersPK)
/*     */   {
/* 687 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 688 */     PreparedStatement stmt = null;
/* 689 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 692 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,VIREMENT_REQUEST.REQUEST_ID,VIREMENT_AUTH_POINT.AUTH_POINT_ID from VIREMENT_AUTHORISERS,MODEL,VIREMENT_REQUEST,VIREMENT_AUTH_POINT where 1=1 and VIREMENT_AUTHORISERS.AUTH_POINT_ID = ? and VIREMENT_AUTHORISERS.USER_ID = ? and VIREMENT_AUTHORISERS.AUTH_POINT_ID = VIREMENT_AUTH_POINT.AUTH_POINT_ID and VIREMENT_AUTH_POINT.AUTH_POINT_ID = VIREMENT_REQUEST.AUTH_POINT_ID and VIREMENT_REQUEST.REQUEST_ID = MODEL.REQUEST_ID");
/* 693 */       int col = 1;
/* 694 */       stmt.setInt(col++, paramVirementAuthorisersPK.getAuthPointId());
/* 695 */       stmt.setInt(col++, paramVirementAuthorisersPK.getUserId());
/*     */ 
/* 697 */       resultSet = stmt.executeQuery();
/*     */ 
/* 699 */       if (!resultSet.next()) {
/* 700 */         throw new RuntimeException(getEntityName() + " getRef " + paramVirementAuthorisersPK + " not found");
/*     */       }
/* 702 */       col = 2;
/* 703 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 707 */       VirementRequestPK newVirementRequestPK = new VirementRequestPK(resultSet.getInt(col++));
/*     */ 
/* 711 */       VirementAuthPointPK newVirementAuthPointPK = new VirementAuthPointPK(resultSet.getInt(col++));
/*     */ 
/* 715 */       String textVirementAuthorisers = "";
/* 716 */       VirementAuthorisersCK ckVirementAuthorisers = new VirementAuthorisersCK(newModelPK, newVirementRequestPK, newVirementAuthPointPK, paramVirementAuthorisersPK);
/*     */ 
/* 723 */       VirementAuthorisersRefImpl localVirementAuthorisersRefImpl = new VirementAuthorisersRefImpl(ckVirementAuthorisers, textVirementAuthorisers);
/*     */       return localVirementAuthorisersRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 728 */       throw handleSQLException(paramVirementAuthorisersPK, "select 0,MODEL.MODEL_ID,VIREMENT_REQUEST.REQUEST_ID,VIREMENT_AUTH_POINT.AUTH_POINT_ID from VIREMENT_AUTHORISERS,MODEL,VIREMENT_REQUEST,VIREMENT_AUTH_POINT where 1=1 and VIREMENT_AUTHORISERS.AUTH_POINT_ID = ? and VIREMENT_AUTHORISERS.USER_ID = ? and VIREMENT_AUTHORISERS.AUTH_POINT_ID = VIREMENT_AUTH_POINT.AUTH_POINT_ID and VIREMENT_AUTH_POINT.AUTH_POINT_ID = VIREMENT_REQUEST.AUTH_POINT_ID and VIREMENT_REQUEST.REQUEST_ID = MODEL.REQUEST_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 732 */       closeResultSet(resultSet);
/* 733 */       closeStatement(stmt);
/* 734 */       closeConnection();
/*     */ 
/* 736 */       if (timer != null)
/* 737 */         timer.logDebug("getRef", paramVirementAuthorisersPK); 
/* 737 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.virement.VirementAuthorisersDAO
 * JD-Core Version:    0.6.0
 */