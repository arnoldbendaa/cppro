/*     */ package com.cedar.cp.ejb.impl.model.amm;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.amm.AmmDimensionElementPK;
/*     */ import com.cedar.cp.dto.model.amm.AmmDimensionPK;
/*     */ import com.cedar.cp.dto.model.amm.AmmModelCK;
/*     */ import com.cedar.cp.dto.model.amm.AmmModelPK;
/*     */ import com.cedar.cp.dto.model.amm.AmmSrcStructureElementCK;
/*     */ import com.cedar.cp.dto.model.amm.AmmSrcStructureElementPK;
/*     */ import com.cedar.cp.dto.model.amm.AmmSrcStructureElementRefImpl;
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
/*     */ public class AmmSrcStructureElementDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select AMM_SRC_STRUCTURE_ELEMENT.AMM_SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_BY_USER_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_TIME,AMM_SRC_STRUCTURE_ELEMENT.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from AMM_SRC_STRUCTURE_ELEMENT where    AMM_SRC_STRUCTURE_ELEMENT_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into AMM_SRC_STRUCTURE_ELEMENT ( AMM_SRC_STRUCTURE_ELEMENT_ID,AMM_DIMENSION_ELEMENT_ID,SRC_STRUCTURE_ELEMENT_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update AMM_SRC_STRUCTURE_ELEMENT set AMM_DIMENSION_ELEMENT_ID = ?,SRC_STRUCTURE_ELEMENT_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_SRC_STRUCTURE_ELEMENT_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from AMM_SRC_STRUCTURE_ELEMENT where    AMM_SRC_STRUCTURE_ELEMENT_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from AMM_SRC_STRUCTURE_ELEMENT,AMM_DIMENSION_ELEMENT,AMM_DIMENSION where 1=1 and AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID = AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID = AMM_DIMENSION.AMM_DIMENSION_ID and AMM_DIMENSION.AMM_MODEL_ID = ? order by  AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID ,AMM_SRC_STRUCTURE_ELEMENT.AMM_SRC_STRUCTURE_ELEMENT_ID";
/*     */   protected static final String SQL_GET_ALL = " from AMM_SRC_STRUCTURE_ELEMENT where    AMM_DIMENSION_ELEMENT_ID = ? ";
/*     */   protected AmmSrcStructureElementEVO mDetails;
/*     */ 
/*     */   public AmmSrcStructureElementDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public AmmSrcStructureElementDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AmmSrcStructureElementDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected AmmSrcStructureElementPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(AmmSrcStructureElementEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private AmmSrcStructureElementEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  89 */     int col = 1;
/*  90 */     AmmSrcStructureElementEVO evo = new AmmSrcStructureElementEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  96 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  97 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  98 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  99 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(AmmSrcStructureElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 104 */     int col = startCol_;
/* 105 */     stmt_.setInt(col++, evo_.getAmmSrcStructureElementId());
/* 106 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(AmmSrcStructureElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 111 */     int col = startCol_;
/* 112 */     stmt_.setInt(col++, evo_.getAmmDimensionElementId());
/* 113 */     stmt_.setInt(col++, evo_.getSrcStructureElementId());
/* 114 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 115 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 116 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 117 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(AmmSrcStructureElementPK pk)
/*     */     throws ValidationException
/*     */   {
/* 133 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 135 */     PreparedStatement stmt = null;
/* 136 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 140 */       stmt = getConnection().prepareStatement("select AMM_SRC_STRUCTURE_ELEMENT.AMM_SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_BY_USER_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_TIME,AMM_SRC_STRUCTURE_ELEMENT.CREATED_TIME from AMM_SRC_STRUCTURE_ELEMENT where    AMM_SRC_STRUCTURE_ELEMENT_ID = ? ");
/*     */ 
/* 143 */       int col = 1;
/* 144 */       stmt.setInt(col++, pk.getAmmSrcStructureElementId());
/*     */ 
/* 146 */       resultSet = stmt.executeQuery();
/*     */ 
/* 148 */       if (!resultSet.next()) {
/* 149 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 152 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 153 */       if (this.mDetails.isModified())
/* 154 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 158 */       throw handleSQLException(pk, "select AMM_SRC_STRUCTURE_ELEMENT.AMM_SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_BY_USER_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_TIME,AMM_SRC_STRUCTURE_ELEMENT.CREATED_TIME from AMM_SRC_STRUCTURE_ELEMENT where    AMM_SRC_STRUCTURE_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 162 */       closeResultSet(resultSet);
/* 163 */       closeStatement(stmt);
/* 164 */       closeConnection();
/*     */ 
/* 166 */       if (timer != null)
/* 167 */         timer.logDebug("doLoad", pk);
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
/* 204 */       stmt = getConnection().prepareStatement("insert into AMM_SRC_STRUCTURE_ELEMENT ( AMM_SRC_STRUCTURE_ELEMENT_ID,AMM_DIMENSION_ELEMENT_ID,SRC_STRUCTURE_ELEMENT_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)");
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
/* 222 */       throw handleSQLException(this.mDetails.getPK(), "insert into AMM_SRC_STRUCTURE_ELEMENT ( AMM_SRC_STRUCTURE_ELEMENT_ID,AMM_DIMENSION_ELEMENT_ID,SRC_STRUCTURE_ELEMENT_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)", sqle);
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
/* 254 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 258 */     PreparedStatement stmt = null;
/*     */ 
/* 260 */     boolean mainChanged = this.mDetails.isModified();
/* 261 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 264 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 267 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 268 */         stmt = getConnection().prepareStatement("update AMM_SRC_STRUCTURE_ELEMENT set AMM_DIMENSION_ELEMENT_ID = ?,SRC_STRUCTURE_ELEMENT_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_SRC_STRUCTURE_ELEMENT_ID = ? ");
/*     */ 
/* 271 */         int col = 1;
/* 272 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 273 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 276 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 279 */         if (resultCount != 1) {
/* 280 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 283 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 292 */       throw handleSQLException(getPK(), "update AMM_SRC_STRUCTURE_ELEMENT set AMM_DIMENSION_ELEMENT_ID = ?,SRC_STRUCTURE_ELEMENT_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_SRC_STRUCTURE_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 296 */       closeStatement(stmt);
/* 297 */       closeConnection();
/*     */ 
/* 299 */       if ((timer != null) && (
/* 300 */         (mainChanged) || (dependantChanged)))
/* 301 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
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
/* 334 */         this.mDetails = ((AmmSrcStructureElementEVO)iter2.next());
/*     */ 
/* 337 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 339 */         somethingChanged = true;
/*     */ 
/* 342 */         if (deleteStmt == null) {
/* 343 */           deleteStmt = getConnection().prepareStatement("delete from AMM_SRC_STRUCTURE_ELEMENT where    AMM_SRC_STRUCTURE_ELEMENT_ID = ? ");
/*     */         }
/*     */ 
/* 346 */         int col = 1;
/* 347 */         deleteStmt.setInt(col++, this.mDetails.getAmmSrcStructureElementId());
/*     */ 
/* 349 */         if (this._log.isDebugEnabled()) {
/* 350 */           this._log.debug("update", "AmmSrcStructureElement deleting AmmSrcStructureElementId=" + this.mDetails.getAmmSrcStructureElementId());
/*     */         }
/*     */ 
/* 355 */         deleteStmt.addBatch();
/*     */ 
/* 358 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 363 */       if (deleteStmt != null)
/*     */       {
/* 365 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 367 */         deleteStmt.executeBatch();
/*     */ 
/* 369 */         if (timer2 != null) {
/* 370 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 374 */       Iterator iter1 = items.values().iterator();
/* 375 */       while (iter1.hasNext())
/*     */       {
/* 377 */         this.mDetails = ((AmmSrcStructureElementEVO)iter1.next());
/*     */ 
/* 379 */         if (this.mDetails.insertPending())
/*     */         {
/* 381 */           somethingChanged = true;
/* 382 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 385 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 387 */         somethingChanged = true;
/* 388 */         doStore();
/*     */       }
/*     */ 
/* 399 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 403 */       throw handleSQLException("delete from AMM_SRC_STRUCTURE_ELEMENT where    AMM_SRC_STRUCTURE_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 407 */       if (deleteStmt != null)
/*     */       {
/* 409 */         closeStatement(deleteStmt);
/* 410 */         closeConnection();
/*     */       }
/*     */ 
/* 413 */       this.mDetails = null;
/*     */ 
/* 415 */       if ((somethingChanged) && 
/* 416 */         (timer != null))
/* 417 */         timer.logDebug("update", "collection"); 
/* 417 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(AmmModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 443 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 445 */     PreparedStatement stmt = null;
/* 446 */     ResultSet resultSet = null;
/*     */ 
/* 448 */     int itemCount = 0;
/*     */ 
/* 450 */     AmmDimensionElementEVO owningEVO = null;
/* 451 */     Iterator ownersIter = owners.iterator();
/* 452 */     while (ownersIter.hasNext())
/*     */     {
/* 454 */       owningEVO = (AmmDimensionElementEVO)ownersIter.next();
/* 455 */       owningEVO.setAmmSourceElementsAllItemsLoaded(true);
/*     */     }
/* 457 */     ownersIter = owners.iterator();
/* 458 */     owningEVO = (AmmDimensionElementEVO)ownersIter.next();
/* 459 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 463 */       stmt = getConnection().prepareStatement("select AMM_SRC_STRUCTURE_ELEMENT.AMM_SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_BY_USER_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_TIME,AMM_SRC_STRUCTURE_ELEMENT.CREATED_TIME from AMM_SRC_STRUCTURE_ELEMENT,AMM_DIMENSION_ELEMENT,AMM_DIMENSION where 1=1 and AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID = AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID = AMM_DIMENSION.AMM_DIMENSION_ID and AMM_DIMENSION.AMM_MODEL_ID = ? order by  AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID ,AMM_SRC_STRUCTURE_ELEMENT.AMM_SRC_STRUCTURE_ELEMENT_ID");
/*     */ 
/* 465 */       int col = 1;
/* 466 */       stmt.setInt(col++, entityPK.getAmmModelId());
/*     */ 
/* 468 */       resultSet = stmt.executeQuery();
/*     */ 
/* 471 */       while (resultSet.next())
/*     */       {
/* 473 */         itemCount++;
/* 474 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 479 */         while (this.mDetails.getAmmDimensionElementId() != owningEVO.getAmmDimensionElementId())
/*     */         {
/* 483 */           if (!ownersIter.hasNext())
/*     */           {
/* 485 */             this._log.debug("bulkGetAll", "can't find owning [AmmDimensionElementId=" + this.mDetails.getAmmDimensionElementId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 489 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 490 */             ownersIter = owners.iterator();
/* 491 */             while (ownersIter.hasNext())
/*     */             {
/* 493 */               owningEVO = (AmmDimensionElementEVO)ownersIter.next();
/* 494 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 496 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 498 */           owningEVO = (AmmDimensionElementEVO)ownersIter.next();
/*     */         }
/* 500 */         if (owningEVO.getAmmSourceElements() == null)
/*     */         {
/* 502 */           theseItems = new ArrayList();
/* 503 */           owningEVO.setAmmSourceElements(theseItems);
/* 504 */           owningEVO.setAmmSourceElementsAllItemsLoaded(true);
/*     */         }
/* 506 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 509 */       if (timer != null) {
/* 510 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 515 */       throw handleSQLException("select AMM_SRC_STRUCTURE_ELEMENT.AMM_SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_BY_USER_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_TIME,AMM_SRC_STRUCTURE_ELEMENT.CREATED_TIME from AMM_SRC_STRUCTURE_ELEMENT,AMM_DIMENSION_ELEMENT,AMM_DIMENSION where 1=1 and AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID = AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID = AMM_DIMENSION.AMM_DIMENSION_ID and AMM_DIMENSION.AMM_MODEL_ID = ? order by  AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID ,AMM_SRC_STRUCTURE_ELEMENT.AMM_SRC_STRUCTURE_ELEMENT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 519 */       closeResultSet(resultSet);
/* 520 */       closeStatement(stmt);
/* 521 */       closeConnection();
/*     */ 
/* 523 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectAmmDimensionElementId, String dependants, Collection currentList)
/*     */   {
/* 548 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 549 */     PreparedStatement stmt = null;
/* 550 */     ResultSet resultSet = null;
/*     */ 
/* 552 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 556 */       stmt = getConnection().prepareStatement("select AMM_SRC_STRUCTURE_ELEMENT.AMM_SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_BY_USER_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_TIME,AMM_SRC_STRUCTURE_ELEMENT.CREATED_TIME from AMM_SRC_STRUCTURE_ELEMENT where    AMM_DIMENSION_ELEMENT_ID = ? ");
/*     */ 
/* 558 */       int col = 1;
/* 559 */       stmt.setInt(col++, selectAmmDimensionElementId);
/*     */ 
/* 561 */       resultSet = stmt.executeQuery();
/*     */ 
/* 563 */       while (resultSet.next())
/*     */       {
/* 565 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 568 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 571 */       if (currentList != null)
/*     */       {
/* 574 */         ListIterator iter = items.listIterator();
/* 575 */         AmmSrcStructureElementEVO currentEVO = null;
/* 576 */         AmmSrcStructureElementEVO newEVO = null;
/* 577 */         while (iter.hasNext())
/*     */         {
/* 579 */           newEVO = (AmmSrcStructureElementEVO)iter.next();
/* 580 */           Iterator iter2 = currentList.iterator();
/* 581 */           while (iter2.hasNext())
/*     */           {
/* 583 */             currentEVO = (AmmSrcStructureElementEVO)iter2.next();
/* 584 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 586 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 592 */         Iterator iter2 = currentList.iterator();
/* 593 */         while (iter2.hasNext())
/*     */         {
/* 595 */           currentEVO = (AmmSrcStructureElementEVO)iter2.next();
/* 596 */           if (currentEVO.insertPending()) {
/* 597 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 601 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 605 */       throw handleSQLException("select AMM_SRC_STRUCTURE_ELEMENT.AMM_SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.SRC_STRUCTURE_ELEMENT_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_BY_USER_ID,AMM_SRC_STRUCTURE_ELEMENT.UPDATED_TIME,AMM_SRC_STRUCTURE_ELEMENT.CREATED_TIME from AMM_SRC_STRUCTURE_ELEMENT where    AMM_DIMENSION_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 609 */       closeResultSet(resultSet);
/* 610 */       closeStatement(stmt);
/* 611 */       closeConnection();
/*     */ 
/* 613 */       if (timer != null) {
/* 614 */         timer.logDebug("getAll", " AmmDimensionElementId=" + selectAmmDimensionElementId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 619 */     return items;
/*     */   }
/*     */ 
/*     */   public AmmSrcStructureElementEVO getDetails(AmmModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 633 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 636 */     if (this.mDetails == null) {
/* 637 */       doLoad(((AmmSrcStructureElementCK)paramCK).getAmmSrcStructureElementPK());
/*     */     }
/* 639 */     else if (!this.mDetails.getPK().equals(((AmmSrcStructureElementCK)paramCK).getAmmSrcStructureElementPK())) {
/* 640 */       doLoad(((AmmSrcStructureElementCK)paramCK).getAmmSrcStructureElementPK());
/*     */     }
/*     */ 
/* 643 */     AmmSrcStructureElementEVO details = new AmmSrcStructureElementEVO();
/* 644 */     details = this.mDetails.deepClone();
/*     */ 
/* 646 */     if (timer != null) {
/* 647 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 649 */     return details;
/*     */   }
/*     */ 
/*     */   public AmmSrcStructureElementEVO getDetails(AmmModelCK paramCK, AmmSrcStructureElementEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 655 */     AmmSrcStructureElementEVO savedEVO = this.mDetails;
/* 656 */     this.mDetails = paramEVO;
/* 657 */     AmmSrcStructureElementEVO newEVO = getDetails(paramCK, dependants);
/* 658 */     this.mDetails = savedEVO;
/* 659 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public AmmSrcStructureElementEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 665 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 669 */     AmmSrcStructureElementEVO details = this.mDetails.deepClone();
/*     */ 
/* 671 */     if (timer != null) {
/* 672 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 674 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 679 */     return "AmmSrcStructureElement";
/*     */   }
/*     */ 
/*     */   public AmmSrcStructureElementRefImpl getRef(AmmSrcStructureElementPK paramAmmSrcStructureElementPK)
/*     */   {
/* 684 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 685 */     PreparedStatement stmt = null;
/* 686 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 689 */       stmt = getConnection().prepareStatement("select 0,AMM_MODEL.AMM_MODEL_ID,AMM_DIMENSION.AMM_DIMENSION_ID,AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID from AMM_SRC_STRUCTURE_ELEMENT,AMM_MODEL,AMM_DIMENSION,AMM_DIMENSION_ELEMENT where 1=1 and AMM_SRC_STRUCTURE_ELEMENT.AMM_SRC_STRUCTURE_ELEMENT_ID = ? and AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID = AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID = AMM_DIMENSION.AMM_DIMENSION_ELEMENT_ID and AMM_DIMENSION.AMM_DIMENSION_ID = AMM_MODEL.AMM_DIMENSION_ID");
/* 690 */       int col = 1;
/* 691 */       stmt.setInt(col++, paramAmmSrcStructureElementPK.getAmmSrcStructureElementId());
/*     */ 
/* 693 */       resultSet = stmt.executeQuery();
/*     */ 
/* 695 */       if (!resultSet.next()) {
/* 696 */         throw new RuntimeException(getEntityName() + " getRef " + paramAmmSrcStructureElementPK + " not found");
/*     */       }
/* 698 */       col = 2;
/* 699 */       AmmModelPK newAmmModelPK = new AmmModelPK(resultSet.getInt(col++));
/*     */ 
/* 703 */       AmmDimensionPK newAmmDimensionPK = new AmmDimensionPK(resultSet.getInt(col++));
/*     */ 
/* 707 */       AmmDimensionElementPK newAmmDimensionElementPK = new AmmDimensionElementPK(resultSet.getInt(col++));
/*     */ 
/* 711 */       String textAmmSrcStructureElement = "";
/* 712 */       AmmSrcStructureElementCK ckAmmSrcStructureElement = new AmmSrcStructureElementCK(newAmmModelPK, newAmmDimensionPK, newAmmDimensionElementPK, paramAmmSrcStructureElementPK);
/*     */ 
/* 719 */       AmmSrcStructureElementRefImpl localAmmSrcStructureElementRefImpl = new AmmSrcStructureElementRefImpl(ckAmmSrcStructureElement, textAmmSrcStructureElement);
/*     */       return localAmmSrcStructureElementRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 724 */       throw handleSQLException(paramAmmSrcStructureElementPK, "select 0,AMM_MODEL.AMM_MODEL_ID,AMM_DIMENSION.AMM_DIMENSION_ID,AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID from AMM_SRC_STRUCTURE_ELEMENT,AMM_MODEL,AMM_DIMENSION,AMM_DIMENSION_ELEMENT where 1=1 and AMM_SRC_STRUCTURE_ELEMENT.AMM_SRC_STRUCTURE_ELEMENT_ID = ? and AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID = AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID = AMM_DIMENSION.AMM_DIMENSION_ELEMENT_ID and AMM_DIMENSION.AMM_DIMENSION_ID = AMM_MODEL.AMM_DIMENSION_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 728 */       closeResultSet(resultSet);
/* 729 */       closeStatement(stmt);
/* 730 */       closeConnection();
/*     */ 
/* 732 */       if (timer != null)
/* 733 */         timer.logDebug("getRef", paramAmmSrcStructureElementPK); 
/* 733 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.amm.AmmSrcStructureElementDAO
 * JD-Core Version:    0.6.0
 */