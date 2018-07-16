/*     */ package com.cedar.cp.ejb.impl.model.udwp;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingProfileLineCK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingProfileLinePK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingProfileLineRefImpl;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
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
/*     */ public class WeightingProfileLineDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select WEIGHTING_PROFILE_LINE.PROFILE_ID,WEIGHTING_PROFILE_LINE.LINE_IDX,WEIGHTING_PROFILE_LINE.WEIGHTING,WEIGHTING_PROFILE_LINE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE_LINE.UPDATED_TIME,WEIGHTING_PROFILE_LINE.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from WEIGHTING_PROFILE_LINE where    PROFILE_ID = ? AND LINE_IDX = ? ";
/*     */   protected static final String SQL_CREATE = "insert into WEIGHTING_PROFILE_LINE ( PROFILE_ID,LINE_IDX,WEIGHTING,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update WEIGHTING_PROFILE_LINE set WEIGHTING = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    PROFILE_ID = ? AND LINE_IDX = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from WEIGHTING_PROFILE_LINE where    PROFILE_ID = ? AND LINE_IDX = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from WEIGHTING_PROFILE_LINE,WEIGHTING_PROFILE where 1=1 and WEIGHTING_PROFILE_LINE.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WEIGHTING_PROFILE.MODEL_ID = ? order by  WEIGHTING_PROFILE_LINE.PROFILE_ID ,WEIGHTING_PROFILE_LINE.PROFILE_ID ,WEIGHTING_PROFILE_LINE.LINE_IDX";
/*     */   protected static final String SQL_GET_ALL = " from WEIGHTING_PROFILE_LINE where    PROFILE_ID = ? ";
/*     */   protected WeightingProfileLineEVO mDetails;
/*     */ 
/*     */   public WeightingProfileLineDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public WeightingProfileLineDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public WeightingProfileLineDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected WeightingProfileLinePK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(WeightingProfileLineEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private WeightingProfileLineEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  92 */     int col = 1;
/*  93 */     WeightingProfileLineEVO evo = new WeightingProfileLineEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  99 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 100 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 101 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 102 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(WeightingProfileLineEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 107 */     int col = startCol_;
/* 108 */     stmt_.setInt(col++, evo_.getProfileId());
/* 109 */     stmt_.setInt(col++, evo_.getLineIdx());
/* 110 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(WeightingProfileLineEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 115 */     int col = startCol_;
/* 116 */     stmt_.setInt(col++, evo_.getWeighting());
/* 117 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 118 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 119 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 120 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(WeightingProfileLinePK pk)
/*     */     throws ValidationException
/*     */   {
/* 137 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 139 */     PreparedStatement stmt = null;
/* 140 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 144 */       stmt = getConnection().prepareStatement("select WEIGHTING_PROFILE_LINE.PROFILE_ID,WEIGHTING_PROFILE_LINE.LINE_IDX,WEIGHTING_PROFILE_LINE.WEIGHTING,WEIGHTING_PROFILE_LINE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE_LINE.UPDATED_TIME,WEIGHTING_PROFILE_LINE.CREATED_TIME from WEIGHTING_PROFILE_LINE where    PROFILE_ID = ? AND LINE_IDX = ? ");
/*     */ 
/* 147 */       int col = 1;
/* 148 */       stmt.setInt(col++, pk.getProfileId());
/* 149 */       stmt.setInt(col++, pk.getLineIdx());
/*     */ 
/* 151 */       resultSet = stmt.executeQuery();
/*     */ 
/* 153 */       if (!resultSet.next()) {
/* 154 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 157 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 158 */       if (this.mDetails.isModified())
/* 159 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 163 */       throw handleSQLException(pk, "select WEIGHTING_PROFILE_LINE.PROFILE_ID,WEIGHTING_PROFILE_LINE.LINE_IDX,WEIGHTING_PROFILE_LINE.WEIGHTING,WEIGHTING_PROFILE_LINE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE_LINE.UPDATED_TIME,WEIGHTING_PROFILE_LINE.CREATED_TIME from WEIGHTING_PROFILE_LINE where    PROFILE_ID = ? AND LINE_IDX = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 167 */       closeResultSet(resultSet);
/* 168 */       closeStatement(stmt);
/* 169 */       closeConnection();
/*     */ 
/* 171 */       if (timer != null)
/* 172 */         timer.logDebug("doLoad", pk);
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
/* 209 */       stmt = getConnection().prepareStatement("insert into WEIGHTING_PROFILE_LINE ( PROFILE_ID,LINE_IDX,WEIGHTING,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)");
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
/* 227 */       throw handleSQLException(this.mDetails.getPK(), "insert into WEIGHTING_PROFILE_LINE ( PROFILE_ID,LINE_IDX,WEIGHTING,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)", sqle);
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
/* 259 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 263 */     PreparedStatement stmt = null;
/*     */ 
/* 265 */     boolean mainChanged = this.mDetails.isModified();
/* 266 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 269 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 272 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 273 */         stmt = getConnection().prepareStatement("update WEIGHTING_PROFILE_LINE set WEIGHTING = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    PROFILE_ID = ? AND LINE_IDX = ? ");
/*     */ 
/* 276 */         int col = 1;
/* 277 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 278 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 281 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 284 */         if (resultCount != 1) {
/* 285 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 288 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 297 */       throw handleSQLException(getPK(), "update WEIGHTING_PROFILE_LINE set WEIGHTING = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    PROFILE_ID = ? AND LINE_IDX = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 301 */       closeStatement(stmt);
/* 302 */       closeConnection();
/*     */ 
/* 304 */       if ((timer != null) && (
/* 305 */         (mainChanged) || (dependantChanged)))
/* 306 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
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
/* 340 */         this.mDetails = ((WeightingProfileLineEVO)iter2.next());
/*     */ 
/* 343 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 345 */         somethingChanged = true;
/*     */ 
/* 348 */         if (deleteStmt == null) {
/* 349 */           deleteStmt = getConnection().prepareStatement("delete from WEIGHTING_PROFILE_LINE where    PROFILE_ID = ? AND LINE_IDX = ? ");
/*     */         }
/*     */ 
/* 352 */         int col = 1;
/* 353 */         deleteStmt.setInt(col++, this.mDetails.getProfileId());
/* 354 */         deleteStmt.setInt(col++, this.mDetails.getLineIdx());
/*     */ 
/* 356 */         if (this._log.isDebugEnabled()) {
/* 357 */           this._log.debug("update", "WeightingProfileLine deleting ProfileId=" + this.mDetails.getProfileId() + ",LineIdx=" + this.mDetails.getLineIdx());
/*     */         }
/*     */ 
/* 363 */         deleteStmt.addBatch();
/*     */ 
/* 366 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 371 */       if (deleteStmt != null)
/*     */       {
/* 373 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 375 */         deleteStmt.executeBatch();
/*     */ 
/* 377 */         if (timer2 != null) {
/* 378 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 382 */       Iterator iter1 = items.values().iterator();
/* 383 */       while (iter1.hasNext())
/*     */       {
/* 385 */         this.mDetails = ((WeightingProfileLineEVO)iter1.next());
/*     */ 
/* 387 */         if (this.mDetails.insertPending())
/*     */         {
/* 389 */           somethingChanged = true;
/* 390 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 393 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 395 */         somethingChanged = true;
/* 396 */         doStore();
/*     */       }
/*     */ 
/* 407 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 411 */       throw handleSQLException("delete from WEIGHTING_PROFILE_LINE where    PROFILE_ID = ? AND LINE_IDX = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 415 */       if (deleteStmt != null)
/*     */       {
/* 417 */         closeStatement(deleteStmt);
/* 418 */         closeConnection();
/*     */       }
/*     */ 
/* 421 */       this.mDetails = null;
/*     */ 
/* 423 */       if ((somethingChanged) && 
/* 424 */         (timer != null))
/* 425 */         timer.logDebug("update", "collection"); 
/* 425 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 449 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 451 */     PreparedStatement stmt = null;
/* 452 */     ResultSet resultSet = null;
/*     */ 
/* 454 */     int itemCount = 0;
/*     */ 
/* 456 */     WeightingProfileEVO owningEVO = null;
/* 457 */     Iterator ownersIter = owners.iterator();
/* 458 */     while (ownersIter.hasNext())
/*     */     {
/* 460 */       owningEVO = (WeightingProfileEVO)ownersIter.next();
/* 461 */       owningEVO.setWeightingLinesAllItemsLoaded(true);
/*     */     }
/* 463 */     ownersIter = owners.iterator();
/* 464 */     owningEVO = (WeightingProfileEVO)ownersIter.next();
/* 465 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 469 */       stmt = getConnection().prepareStatement("select WEIGHTING_PROFILE_LINE.PROFILE_ID,WEIGHTING_PROFILE_LINE.LINE_IDX,WEIGHTING_PROFILE_LINE.WEIGHTING,WEIGHTING_PROFILE_LINE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE_LINE.UPDATED_TIME,WEIGHTING_PROFILE_LINE.CREATED_TIME from WEIGHTING_PROFILE_LINE,WEIGHTING_PROFILE where 1=1 and WEIGHTING_PROFILE_LINE.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WEIGHTING_PROFILE.MODEL_ID = ? order by  WEIGHTING_PROFILE_LINE.PROFILE_ID ,WEIGHTING_PROFILE_LINE.PROFILE_ID ,WEIGHTING_PROFILE_LINE.LINE_IDX");
/*     */ 
/* 471 */       int col = 1;
/* 472 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 474 */       resultSet = stmt.executeQuery();
/*     */ 
/* 477 */       while (resultSet.next())
/*     */       {
/* 479 */         itemCount++;
/* 480 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 485 */         while (this.mDetails.getProfileId() != owningEVO.getProfileId())
/*     */         {
/* 489 */           if (!ownersIter.hasNext())
/*     */           {
/* 491 */             this._log.debug("bulkGetAll", "can't find owning [ProfileId=" + this.mDetails.getProfileId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 495 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 496 */             ownersIter = owners.iterator();
/* 497 */             while (ownersIter.hasNext())
/*     */             {
/* 499 */               owningEVO = (WeightingProfileEVO)ownersIter.next();
/* 500 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 502 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 504 */           owningEVO = (WeightingProfileEVO)ownersIter.next();
/*     */         }
/* 506 */         if (owningEVO.getWeightingLines() == null)
/*     */         {
/* 508 */           theseItems = new ArrayList();
/* 509 */           owningEVO.setWeightingLines(theseItems);
/* 510 */           owningEVO.setWeightingLinesAllItemsLoaded(true);
/*     */         }
/* 512 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 515 */       if (timer != null) {
/* 516 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 521 */       throw handleSQLException("select WEIGHTING_PROFILE_LINE.PROFILE_ID,WEIGHTING_PROFILE_LINE.LINE_IDX,WEIGHTING_PROFILE_LINE.WEIGHTING,WEIGHTING_PROFILE_LINE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE_LINE.UPDATED_TIME,WEIGHTING_PROFILE_LINE.CREATED_TIME from WEIGHTING_PROFILE_LINE,WEIGHTING_PROFILE where 1=1 and WEIGHTING_PROFILE_LINE.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WEIGHTING_PROFILE.MODEL_ID = ? order by  WEIGHTING_PROFILE_LINE.PROFILE_ID ,WEIGHTING_PROFILE_LINE.PROFILE_ID ,WEIGHTING_PROFILE_LINE.LINE_IDX", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 525 */       closeResultSet(resultSet);
/* 526 */       closeStatement(stmt);
/* 527 */       closeConnection();
/*     */ 
/* 529 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectProfileId, String dependants, Collection currentList)
/*     */   {
/* 554 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 555 */     PreparedStatement stmt = null;
/* 556 */     ResultSet resultSet = null;
/*     */ 
/* 558 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 562 */       stmt = getConnection().prepareStatement("select WEIGHTING_PROFILE_LINE.PROFILE_ID,WEIGHTING_PROFILE_LINE.LINE_IDX,WEIGHTING_PROFILE_LINE.WEIGHTING,WEIGHTING_PROFILE_LINE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE_LINE.UPDATED_TIME,WEIGHTING_PROFILE_LINE.CREATED_TIME from WEIGHTING_PROFILE_LINE where    PROFILE_ID = ? ");
/*     */ 
/* 564 */       int col = 1;
/* 565 */       stmt.setInt(col++, selectProfileId);
/*     */ 
/* 567 */       resultSet = stmt.executeQuery();
/*     */ 
/* 569 */       while (resultSet.next())
/*     */       {
/* 571 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 574 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 577 */       if (currentList != null)
/*     */       {
/* 580 */         ListIterator iter = items.listIterator();
/* 581 */         WeightingProfileLineEVO currentEVO = null;
/* 582 */         WeightingProfileLineEVO newEVO = null;
/* 583 */         while (iter.hasNext())
/*     */         {
/* 585 */           newEVO = (WeightingProfileLineEVO)iter.next();
/* 586 */           Iterator iter2 = currentList.iterator();
/* 587 */           while (iter2.hasNext())
/*     */           {
/* 589 */             currentEVO = (WeightingProfileLineEVO)iter2.next();
/* 590 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 592 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 598 */         Iterator iter2 = currentList.iterator();
/* 599 */         while (iter2.hasNext())
/*     */         {
/* 601 */           currentEVO = (WeightingProfileLineEVO)iter2.next();
/* 602 */           if (currentEVO.insertPending()) {
/* 603 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 607 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 611 */       throw handleSQLException("select WEIGHTING_PROFILE_LINE.PROFILE_ID,WEIGHTING_PROFILE_LINE.LINE_IDX,WEIGHTING_PROFILE_LINE.WEIGHTING,WEIGHTING_PROFILE_LINE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE_LINE.UPDATED_TIME,WEIGHTING_PROFILE_LINE.CREATED_TIME from WEIGHTING_PROFILE_LINE where    PROFILE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 615 */       closeResultSet(resultSet);
/* 616 */       closeStatement(stmt);
/* 617 */       closeConnection();
/*     */ 
/* 619 */       if (timer != null) {
/* 620 */         timer.logDebug("getAll", " ProfileId=" + selectProfileId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 625 */     return items;
/*     */   }
/*     */ 
/*     */   public WeightingProfileLineEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 639 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 642 */     if (this.mDetails == null) {
/* 643 */       doLoad(((WeightingProfileLineCK)paramCK).getWeightingProfileLinePK());
/*     */     }
/* 645 */     else if (!this.mDetails.getPK().equals(((WeightingProfileLineCK)paramCK).getWeightingProfileLinePK())) {
/* 646 */       doLoad(((WeightingProfileLineCK)paramCK).getWeightingProfileLinePK());
/*     */     }
/*     */ 
/* 649 */     WeightingProfileLineEVO details = new WeightingProfileLineEVO();
/* 650 */     details = this.mDetails.deepClone();
/*     */ 
/* 652 */     if (timer != null) {
/* 653 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 655 */     return details;
/*     */   }
/*     */ 
/*     */   public WeightingProfileLineEVO getDetails(ModelCK paramCK, WeightingProfileLineEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 661 */     WeightingProfileLineEVO savedEVO = this.mDetails;
/* 662 */     this.mDetails = paramEVO;
/* 663 */     WeightingProfileLineEVO newEVO = getDetails(paramCK, dependants);
/* 664 */     this.mDetails = savedEVO;
/* 665 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public WeightingProfileLineEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 671 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 675 */     WeightingProfileLineEVO details = this.mDetails.deepClone();
/*     */ 
/* 677 */     if (timer != null) {
/* 678 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 680 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 685 */     return "WeightingProfileLine";
/*     */   }
/*     */ 
/*     */   public WeightingProfileLineRefImpl getRef(WeightingProfileLinePK paramWeightingProfileLinePK)
/*     */   {
/* 690 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 691 */     PreparedStatement stmt = null;
/* 692 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 695 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,WEIGHTING_PROFILE.PROFILE_ID from WEIGHTING_PROFILE_LINE,MODEL,WEIGHTING_PROFILE where 1=1 and WEIGHTING_PROFILE_LINE.PROFILE_ID = ? and WEIGHTING_PROFILE_LINE.LINE_IDX = ? and WEIGHTING_PROFILE_LINE.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WEIGHTING_PROFILE.PROFILE_ID = MODEL.PROFILE_ID");
/* 696 */       int col = 1;
/* 697 */       stmt.setInt(col++, paramWeightingProfileLinePK.getProfileId());
/* 698 */       stmt.setInt(col++, paramWeightingProfileLinePK.getLineIdx());
/*     */ 
/* 700 */       resultSet = stmt.executeQuery();
/*     */ 
/* 702 */       if (!resultSet.next()) {
/* 703 */         throw new RuntimeException(getEntityName() + " getRef " + paramWeightingProfileLinePK + " not found");
/*     */       }
/* 705 */       col = 2;
/* 706 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 710 */       WeightingProfilePK newWeightingProfilePK = new WeightingProfilePK(resultSet.getInt(col++));
/*     */ 
/* 714 */       String textWeightingProfileLine = "";
/* 715 */       WeightingProfileLineCK ckWeightingProfileLine = new WeightingProfileLineCK(newModelPK, newWeightingProfilePK, paramWeightingProfileLinePK);
/*     */ 
/* 721 */       WeightingProfileLineRefImpl localWeightingProfileLineRefImpl = new WeightingProfileLineRefImpl(ckWeightingProfileLine, textWeightingProfileLine);
/*     */       return localWeightingProfileLineRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 726 */       throw handleSQLException(paramWeightingProfileLinePK, "select 0,MODEL.MODEL_ID,WEIGHTING_PROFILE.PROFILE_ID from WEIGHTING_PROFILE_LINE,MODEL,WEIGHTING_PROFILE where 1=1 and WEIGHTING_PROFILE_LINE.PROFILE_ID = ? and WEIGHTING_PROFILE_LINE.LINE_IDX = ? and WEIGHTING_PROFILE_LINE.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WEIGHTING_PROFILE.PROFILE_ID = MODEL.PROFILE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 730 */       closeResultSet(resultSet);
/* 731 */       closeStatement(stmt);
/* 732 */       closeConnection();
/*     */ 
/* 734 */       if (timer != null)
/* 735 */         timer.logDebug("getRef", paramWeightingProfileLinePK); 
/* 735 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.udwp.WeightingProfileLineDAO
 * JD-Core Version:    0.6.0
 */