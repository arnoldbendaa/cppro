/*     */ package com.cedar.cp.ejb.impl.model;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.AllAccessDefsUsingRangeELO;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.ModelRefImpl;
/*     */ import com.cedar.cp.dto.model.SecurityAccRngRelCK;
/*     */ import com.cedar.cp.dto.model.SecurityAccRngRelPK;
/*     */ import com.cedar.cp.dto.model.SecurityAccRngRelRefImpl;
/*     */ import com.cedar.cp.dto.model.SecurityAccessDefCK;
/*     */ import com.cedar.cp.dto.model.SecurityAccessDefPK;
/*     */ import com.cedar.cp.dto.model.SecurityAccessDefRefImpl;
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
/*     */ public class SecurityAccRngRelDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID,SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID,SECURITY_ACC_RNG_REL.VERSION_NUM,SECURITY_ACC_RNG_REL.UPDATED_BY_USER_ID,SECURITY_ACC_RNG_REL.UPDATED_TIME,SECURITY_ACC_RNG_REL.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from SECURITY_ACC_RNG_REL where    SECURITY_ACCESS_DEF_ID = ? AND SECURITY_RANGE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into SECURITY_ACC_RNG_REL ( SECURITY_ACCESS_DEF_ID,SECURITY_RANGE_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update SECURITY_ACC_RNG_REL set VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_ACCESS_DEF_ID = ? AND SECURITY_RANGE_ID = ? AND VERSION_NUM = ?";
/*     */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from SECURITY_ACC_RNG_REL where SECURITY_ACCESS_DEF_ID = ?,SECURITY_RANGE_ID = ?";
/* 368 */   protected static String SQL_ALL_ACCESS_DEFS_USING_RANGE = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID      ,SECURITY_ACCESS_DEF.VIS_ID      ,SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID      ,SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID      ,SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID from SECURITY_ACC_RNG_REL    ,MODEL    ,SECURITY_ACCESS_DEF where 1=1   and SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID = SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID   and SECURITY_ACCESS_DEF.MODEL_ID = MODEL.MODEL_ID  and  SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID = ?";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from SECURITY_ACC_RNG_REL where    SECURITY_ACCESS_DEF_ID = ? AND SECURITY_RANGE_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from SECURITY_ACC_RNG_REL,SECURITY_ACCESS_DEF where 1=1 and SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID = SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID and SECURITY_ACCESS_DEF.MODEL_ID = ? order by  SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID ,SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID ,SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID";
/*     */   protected static final String SQL_GET_ALL = " from SECURITY_ACC_RNG_REL where    SECURITY_ACCESS_DEF_ID = ? ";
/*     */   protected SecurityAccRngRelEVO mDetails;
/*     */ 
/*     */   public SecurityAccRngRelDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public SecurityAccRngRelDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SecurityAccRngRelDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected SecurityAccRngRelPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(SecurityAccRngRelEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private SecurityAccRngRelEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  89 */     int col = 1;
/*  90 */     SecurityAccRngRelEVO evo = new SecurityAccRngRelEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  96 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  97 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  98 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  99 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(SecurityAccRngRelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 104 */     int col = startCol_;
/* 105 */     stmt_.setInt(col++, evo_.getSecurityAccessDefId());
/* 106 */     stmt_.setInt(col++, evo_.getSecurityRangeId());
/* 107 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(SecurityAccRngRelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 112 */     int col = startCol_;
/* 113 */     stmt_.setInt(col++, evo_.getVersionNum());
/* 114 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 115 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 116 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 117 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(SecurityAccRngRelPK pk)
/*     */     throws ValidationException
/*     */   {
/* 134 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 136 */     PreparedStatement stmt = null;
/* 137 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 141 */       stmt = getConnection().prepareStatement("select SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID,SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID,SECURITY_ACC_RNG_REL.VERSION_NUM,SECURITY_ACC_RNG_REL.UPDATED_BY_USER_ID,SECURITY_ACC_RNG_REL.UPDATED_TIME,SECURITY_ACC_RNG_REL.CREATED_TIME from SECURITY_ACC_RNG_REL where    SECURITY_ACCESS_DEF_ID = ? AND SECURITY_RANGE_ID = ? ");
/*     */ 
/* 144 */       int col = 1;
/* 145 */       stmt.setInt(col++, pk.getSecurityAccessDefId());
/* 146 */       stmt.setInt(col++, pk.getSecurityRangeId());
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
/* 160 */       throw handleSQLException(pk, "select SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID,SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID,SECURITY_ACC_RNG_REL.VERSION_NUM,SECURITY_ACC_RNG_REL.UPDATED_BY_USER_ID,SECURITY_ACC_RNG_REL.UPDATED_TIME,SECURITY_ACC_RNG_REL.CREATED_TIME from SECURITY_ACC_RNG_REL where    SECURITY_ACCESS_DEF_ID = ? AND SECURITY_RANGE_ID = ? ", sqle);
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
/* 206 */       stmt = getConnection().prepareStatement("insert into SECURITY_ACC_RNG_REL ( SECURITY_ACCESS_DEF_ID,SECURITY_RANGE_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)");
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
/* 224 */       throw handleSQLException(this.mDetails.getPK(), "insert into SECURITY_ACC_RNG_REL ( SECURITY_ACCESS_DEF_ID,SECURITY_RANGE_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)", sqle);
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
/* 274 */         stmt = getConnection().prepareStatement("update SECURITY_ACC_RNG_REL set VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_ACCESS_DEF_ID = ? AND SECURITY_RANGE_ID = ? AND VERSION_NUM = ?");
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
/* 302 */       throw handleSQLException(getPK(), "update SECURITY_ACC_RNG_REL set VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_ACCESS_DEF_ID = ? AND SECURITY_RANGE_ID = ? AND VERSION_NUM = ?", sqle);
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
/* 330 */       stmt = getConnection().prepareStatement("select VERSION_NUM from SECURITY_ACC_RNG_REL where SECURITY_ACCESS_DEF_ID = ?,SECURITY_RANGE_ID = ?");
/*     */ 
/* 333 */       int col = 1;
/* 334 */       stmt.setInt(col++, this.mDetails.getSecurityAccessDefId());
/* 335 */       stmt.setInt(col++, this.mDetails.getSecurityRangeId());
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
/* 353 */       throw handleSQLException(getPK(), "select VERSION_NUM from SECURITY_ACC_RNG_REL where SECURITY_ACCESS_DEF_ID = ?,SECURITY_RANGE_ID = ?", sqle);
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
/*     */   public AllAccessDefsUsingRangeELO getAllAccessDefsUsingRange(int param1)
/*     */   {
/* 405 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 406 */     PreparedStatement stmt = null;
/* 407 */     ResultSet resultSet = null;
/* 408 */     AllAccessDefsUsingRangeELO results = new AllAccessDefsUsingRangeELO();
/*     */     try
/*     */     {
/* 411 */       stmt = getConnection().prepareStatement(SQL_ALL_ACCESS_DEFS_USING_RANGE);
/* 412 */       int col = 1;
/* 413 */       stmt.setInt(col++, param1);
/* 414 */       resultSet = stmt.executeQuery();
/* 415 */       while (resultSet.next())
/*     */       {
/* 417 */         col = 2;
/*     */ 
/* 420 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 423 */         String textModel = resultSet.getString(col++);
/*     */ 
/* 425 */         SecurityAccessDefPK pkSecurityAccessDef = new SecurityAccessDefPK(resultSet.getInt(col++));
/*     */ 
/* 428 */         String textSecurityAccessDef = resultSet.getString(col++);
/*     */ 
/* 431 */         SecurityAccRngRelPK pkSecurityAccRngRel = new SecurityAccRngRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 435 */         String textSecurityAccRngRel = "";
/*     */ 
/* 440 */         SecurityAccessDefCK ckSecurityAccessDef = new SecurityAccessDefCK(pkModel, pkSecurityAccessDef);
/*     */ 
/* 446 */         SecurityAccRngRelCK ckSecurityAccRngRel = new SecurityAccRngRelCK(pkModel, pkSecurityAccessDef, pkSecurityAccRngRel);
/*     */ 
/* 453 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*     */ 
/* 459 */         SecurityAccessDefRefImpl erSecurityAccessDef = new SecurityAccessDefRefImpl(ckSecurityAccessDef, textSecurityAccessDef);
/*     */ 
/* 465 */         SecurityAccRngRelRefImpl erSecurityAccRngRel = new SecurityAccRngRelRefImpl(ckSecurityAccRngRel, textSecurityAccRngRel);
/*     */ 
/* 470 */         int col1 = resultSet.getInt(col++);
/*     */ 
/* 473 */         results.add(erSecurityAccRngRel, erSecurityAccessDef, erModel, col1);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 483 */       throw handleSQLException(SQL_ALL_ACCESS_DEFS_USING_RANGE, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 487 */       closeResultSet(resultSet);
/* 488 */       closeStatement(stmt);
/* 489 */       closeConnection();
/*     */     }
/*     */ 
/* 492 */     if (timer != null) {
/* 493 */       timer.logDebug("getAllAccessDefsUsingRange", " SecurityRangeId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 498 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 516 */     if (items == null) {
/* 517 */       return false;
/*     */     }
/* 519 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 520 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 522 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 527 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 528 */       while (iter2.hasNext())
/*     */       {
/* 530 */         this.mDetails = ((SecurityAccRngRelEVO)iter2.next());
/*     */ 
/* 533 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 535 */         somethingChanged = true;
/*     */ 
/* 538 */         if (deleteStmt == null) {
/* 539 */           deleteStmt = getConnection().prepareStatement("delete from SECURITY_ACC_RNG_REL where    SECURITY_ACCESS_DEF_ID = ? AND SECURITY_RANGE_ID = ? ");
/*     */         }
/*     */ 
/* 542 */         int col = 1;
/* 543 */         deleteStmt.setInt(col++, this.mDetails.getSecurityAccessDefId());
/* 544 */         deleteStmt.setInt(col++, this.mDetails.getSecurityRangeId());
/*     */ 
/* 546 */         if (this._log.isDebugEnabled()) {
/* 547 */           this._log.debug("update", "SecurityAccRngRel deleting SecurityAccessDefId=" + this.mDetails.getSecurityAccessDefId() + ",SecurityRangeId=" + this.mDetails.getSecurityRangeId());
/*     */         }
/*     */ 
/* 553 */         deleteStmt.addBatch();
/*     */ 
/* 556 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 561 */       if (deleteStmt != null)
/*     */       {
/* 563 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 565 */         deleteStmt.executeBatch();
/*     */ 
/* 567 */         if (timer2 != null) {
/* 568 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 572 */       Iterator iter1 = items.values().iterator();
/* 573 */       while (iter1.hasNext())
/*     */       {
/* 575 */         this.mDetails = ((SecurityAccRngRelEVO)iter1.next());
/*     */ 
/* 577 */         if (this.mDetails.insertPending())
/*     */         {
/* 579 */           somethingChanged = true;
/* 580 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 583 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 585 */         somethingChanged = true;
/* 586 */         doStore();
/*     */       }
/*     */ 
/* 597 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 601 */       throw handleSQLException("delete from SECURITY_ACC_RNG_REL where    SECURITY_ACCESS_DEF_ID = ? AND SECURITY_RANGE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 605 */       if (deleteStmt != null)
/*     */       {
/* 607 */         closeStatement(deleteStmt);
/* 608 */         closeConnection();
/*     */       }
/*     */ 
/* 611 */       this.mDetails = null;
/*     */ 
/* 613 */       if ((somethingChanged) && 
/* 614 */         (timer != null))
/* 615 */         timer.logDebug("update", "collection"); 
/* 615 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 639 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 641 */     PreparedStatement stmt = null;
/* 642 */     ResultSet resultSet = null;
/*     */ 
/* 644 */     int itemCount = 0;
/*     */ 
/* 646 */     SecurityAccessDefEVO owningEVO = null;
/* 647 */     Iterator ownersIter = owners.iterator();
/* 648 */     while (ownersIter.hasNext())
/*     */     {
/* 650 */       owningEVO = (SecurityAccessDefEVO)ownersIter.next();
/* 651 */       owningEVO.setSecurityRangesForAccessDefAllItemsLoaded(true);
/*     */     }
/* 653 */     ownersIter = owners.iterator();
/* 654 */     owningEVO = (SecurityAccessDefEVO)ownersIter.next();
/* 655 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 659 */       stmt = getConnection().prepareStatement("select SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID,SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID,SECURITY_ACC_RNG_REL.VERSION_NUM,SECURITY_ACC_RNG_REL.UPDATED_BY_USER_ID,SECURITY_ACC_RNG_REL.UPDATED_TIME,SECURITY_ACC_RNG_REL.CREATED_TIME from SECURITY_ACC_RNG_REL,SECURITY_ACCESS_DEF where 1=1 and SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID = SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID and SECURITY_ACCESS_DEF.MODEL_ID = ? order by  SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID ,SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID ,SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID");
/*     */ 
/* 661 */       int col = 1;
/* 662 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 664 */       resultSet = stmt.executeQuery();
/*     */ 
/* 667 */       while (resultSet.next())
/*     */       {
/* 669 */         itemCount++;
/* 670 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 675 */         while (this.mDetails.getSecurityAccessDefId() != owningEVO.getSecurityAccessDefId())
/*     */         {
/* 679 */           if (!ownersIter.hasNext())
/*     */           {
/* 681 */             this._log.debug("bulkGetAll", "can't find owning [SecurityAccessDefId=" + this.mDetails.getSecurityAccessDefId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 685 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 686 */             ownersIter = owners.iterator();
/* 687 */             while (ownersIter.hasNext())
/*     */             {
/* 689 */               owningEVO = (SecurityAccessDefEVO)ownersIter.next();
/* 690 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 692 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 694 */           owningEVO = (SecurityAccessDefEVO)ownersIter.next();
/*     */         }
/* 696 */         if (owningEVO.getSecurityRangesForAccessDef() == null)
/*     */         {
/* 698 */           theseItems = new ArrayList();
/* 699 */           owningEVO.setSecurityRangesForAccessDef(theseItems);
/* 700 */           owningEVO.setSecurityRangesForAccessDefAllItemsLoaded(true);
/*     */         }
/* 702 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 705 */       if (timer != null) {
/* 706 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 711 */       throw handleSQLException("select SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID,SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID,SECURITY_ACC_RNG_REL.VERSION_NUM,SECURITY_ACC_RNG_REL.UPDATED_BY_USER_ID,SECURITY_ACC_RNG_REL.UPDATED_TIME,SECURITY_ACC_RNG_REL.CREATED_TIME from SECURITY_ACC_RNG_REL,SECURITY_ACCESS_DEF where 1=1 and SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID = SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID and SECURITY_ACCESS_DEF.MODEL_ID = ? order by  SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID ,SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID ,SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 715 */       closeResultSet(resultSet);
/* 716 */       closeStatement(stmt);
/* 717 */       closeConnection();
/*     */ 
/* 719 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectSecurityAccessDefId, String dependants, Collection currentList)
/*     */   {
/* 744 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 745 */     PreparedStatement stmt = null;
/* 746 */     ResultSet resultSet = null;
/*     */ 
/* 748 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 752 */       stmt = getConnection().prepareStatement("select SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID,SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID,SECURITY_ACC_RNG_REL.VERSION_NUM,SECURITY_ACC_RNG_REL.UPDATED_BY_USER_ID,SECURITY_ACC_RNG_REL.UPDATED_TIME,SECURITY_ACC_RNG_REL.CREATED_TIME from SECURITY_ACC_RNG_REL where    SECURITY_ACCESS_DEF_ID = ? ");
/*     */ 
/* 754 */       int col = 1;
/* 755 */       stmt.setInt(col++, selectSecurityAccessDefId);
/*     */ 
/* 757 */       resultSet = stmt.executeQuery();
/*     */ 
/* 759 */       while (resultSet.next())
/*     */       {
/* 761 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 764 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 767 */       if (currentList != null)
/*     */       {
/* 770 */         ListIterator iter = items.listIterator();
/* 771 */         SecurityAccRngRelEVO currentEVO = null;
/* 772 */         SecurityAccRngRelEVO newEVO = null;
/* 773 */         while (iter.hasNext())
/*     */         {
/* 775 */           newEVO = (SecurityAccRngRelEVO)iter.next();
/* 776 */           Iterator iter2 = currentList.iterator();
/* 777 */           while (iter2.hasNext())
/*     */           {
/* 779 */             currentEVO = (SecurityAccRngRelEVO)iter2.next();
/* 780 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 782 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 788 */         Iterator iter2 = currentList.iterator();
/* 789 */         while (iter2.hasNext())
/*     */         {
/* 791 */           currentEVO = (SecurityAccRngRelEVO)iter2.next();
/* 792 */           if (currentEVO.insertPending()) {
/* 793 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 797 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 801 */       throw handleSQLException("select SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID,SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID,SECURITY_ACC_RNG_REL.VERSION_NUM,SECURITY_ACC_RNG_REL.UPDATED_BY_USER_ID,SECURITY_ACC_RNG_REL.UPDATED_TIME,SECURITY_ACC_RNG_REL.CREATED_TIME from SECURITY_ACC_RNG_REL where    SECURITY_ACCESS_DEF_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 805 */       closeResultSet(resultSet);
/* 806 */       closeStatement(stmt);
/* 807 */       closeConnection();
/*     */ 
/* 809 */       if (timer != null) {
/* 810 */         timer.logDebug("getAll", " SecurityAccessDefId=" + selectSecurityAccessDefId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 815 */     return items;
/*     */   }
/*     */ 
/*     */   public SecurityAccRngRelEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 829 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 832 */     if (this.mDetails == null) {
/* 833 */       doLoad(((SecurityAccRngRelCK)paramCK).getSecurityAccRngRelPK());
/*     */     }
/* 835 */     else if (!this.mDetails.getPK().equals(((SecurityAccRngRelCK)paramCK).getSecurityAccRngRelPK())) {
/* 836 */       doLoad(((SecurityAccRngRelCK)paramCK).getSecurityAccRngRelPK());
/*     */     }
/*     */ 
/* 839 */     SecurityAccRngRelEVO details = new SecurityAccRngRelEVO();
/* 840 */     details = this.mDetails.deepClone();
/*     */ 
/* 842 */     if (timer != null) {
/* 843 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 845 */     return details;
/*     */   }
/*     */ 
/*     */   public SecurityAccRngRelEVO getDetails(ModelCK paramCK, SecurityAccRngRelEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 851 */     SecurityAccRngRelEVO savedEVO = this.mDetails;
/* 852 */     this.mDetails = paramEVO;
/* 853 */     SecurityAccRngRelEVO newEVO = getDetails(paramCK, dependants);
/* 854 */     this.mDetails = savedEVO;
/* 855 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public SecurityAccRngRelEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 861 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 865 */     SecurityAccRngRelEVO details = this.mDetails.deepClone();
/*     */ 
/* 867 */     if (timer != null) {
/* 868 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 870 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 875 */     return "SecurityAccRngRel";
/*     */   }
/*     */ 
/*     */   public SecurityAccRngRelRefImpl getRef(SecurityAccRngRelPK paramSecurityAccRngRelPK)
/*     */   {
/* 880 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 881 */     PreparedStatement stmt = null;
/* 882 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 885 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID from SECURITY_ACC_RNG_REL,MODEL,SECURITY_ACCESS_DEF where 1=1 and SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID = ? and SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID = ? and SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID = SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID and SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID = MODEL.SECURITY_ACCESS_DEF_ID");
/* 886 */       int col = 1;
/* 887 */       stmt.setInt(col++, paramSecurityAccRngRelPK.getSecurityAccessDefId());
/* 888 */       stmt.setInt(col++, paramSecurityAccRngRelPK.getSecurityRangeId());
/*     */ 
/* 890 */       resultSet = stmt.executeQuery();
/*     */ 
/* 892 */       if (!resultSet.next()) {
/* 893 */         throw new RuntimeException(getEntityName() + " getRef " + paramSecurityAccRngRelPK + " not found");
/*     */       }
/* 895 */       col = 2;
/* 896 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 900 */       SecurityAccessDefPK newSecurityAccessDefPK = new SecurityAccessDefPK(resultSet.getInt(col++));
/*     */ 
/* 904 */       String textSecurityAccRngRel = "";
/* 905 */       SecurityAccRngRelCK ckSecurityAccRngRel = new SecurityAccRngRelCK(newModelPK, newSecurityAccessDefPK, paramSecurityAccRngRelPK);
/*     */ 
/* 911 */       SecurityAccRngRelRefImpl localSecurityAccRngRelRefImpl = new SecurityAccRngRelRefImpl(ckSecurityAccRngRel, textSecurityAccRngRel);
/*     */       return localSecurityAccRngRelRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 916 */       throw handleSQLException(paramSecurityAccRngRelPK, "select 0,MODEL.MODEL_ID,SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID from SECURITY_ACC_RNG_REL,MODEL,SECURITY_ACCESS_DEF where 1=1 and SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID = ? and SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID = ? and SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID = SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID and SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID = MODEL.SECURITY_ACCESS_DEF_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 920 */       closeResultSet(resultSet);
/* 921 */       closeStatement(stmt);
/* 922 */       closeConnection();
/*     */ 
/* 924 */       if (timer != null)
/* 925 */         timer.logDebug("getRef", paramSecurityAccRngRelPK); 
/* 925 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.SecurityAccRngRelDAO
 * JD-Core Version:    0.6.0
 */