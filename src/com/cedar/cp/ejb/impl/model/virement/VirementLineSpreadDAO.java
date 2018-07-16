/*     */ package com.cedar.cp.ejb.impl.model.virement;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementLineSpreadCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementLineSpreadPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementLineSpreadRefImpl;
/*     */ import com.cedar.cp.dto.model.virement.VirementRequestGroupPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementRequestLinePK;
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
/*     */ public class VirementLineSpreadDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select VIREMENT_LINE_SPREAD.LINE_SPREAD_ID,VIREMENT_LINE_SPREAD.STRUCTURE_ELEMENT_ID,VIREMENT_LINE_SPREAD.REQUEST_LINE_ID,VIREMENT_LINE_SPREAD.LINE_IDX,VIREMENT_LINE_SPREAD.HELD,VIREMENT_LINE_SPREAD.WEIGHTING,VIREMENT_LINE_SPREAD.UPDATED_BY_USER_ID,VIREMENT_LINE_SPREAD.UPDATED_TIME,VIREMENT_LINE_SPREAD.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from VIREMENT_LINE_SPREAD where    LINE_SPREAD_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into VIREMENT_LINE_SPREAD ( LINE_SPREAD_ID,STRUCTURE_ELEMENT_ID,REQUEST_LINE_ID,LINE_IDX,HELD,WEIGHTING,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update VIREMENT_LINE_SPREAD set STRUCTURE_ELEMENT_ID = ?,REQUEST_LINE_ID = ?,LINE_IDX = ?,HELD = ?,WEIGHTING = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    LINE_SPREAD_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from VIREMENT_LINE_SPREAD where    LINE_SPREAD_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from VIREMENT_LINE_SPREAD,VIREMENT_REQUEST_LINE,VIREMENT_REQUEST_GROUP,VIREMENT_REQUEST where 1=1 and VIREMENT_LINE_SPREAD.REQUEST_LINE_ID = VIREMENT_REQUEST_LINE.REQUEST_LINE_ID and VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID = VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_LINE_SPREAD.REQUEST_LINE_ID ,VIREMENT_LINE_SPREAD.LINE_SPREAD_ID";
/*     */   protected static final String SQL_GET_ALL = " from VIREMENT_LINE_SPREAD where    REQUEST_LINE_ID = ? ";
/*     */   protected VirementLineSpreadEVO mDetails;
/*     */ 
/*     */   public VirementLineSpreadDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public VirementLineSpreadDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public VirementLineSpreadDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected VirementLineSpreadPK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(VirementLineSpreadEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private VirementLineSpreadEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  95 */     int col = 1;
/*  96 */     VirementLineSpreadEVO evo = new VirementLineSpreadEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++));
/*     */ 
/* 105 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 106 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 107 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 108 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(VirementLineSpreadEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 113 */     int col = startCol_;
/* 114 */     stmt_.setInt(col++, evo_.getLineSpreadId());
/* 115 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(VirementLineSpreadEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 120 */     int col = startCol_;
/* 121 */     stmt_.setInt(col++, evo_.getStructureElementId());
/* 122 */     stmt_.setInt(col++, evo_.getRequestLineId());
/* 123 */     stmt_.setInt(col++, evo_.getLineIdx());
/* 124 */     if (evo_.getHeld())
/* 125 */       stmt_.setString(col++, "Y");
/*     */     else
/* 127 */       stmt_.setString(col++, " ");
/* 128 */     stmt_.setInt(col++, evo_.getWeighting());
/* 129 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 130 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 131 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 132 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(VirementLineSpreadPK pk)
/*     */     throws ValidationException
/*     */   {
/* 148 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 150 */     PreparedStatement stmt = null;
/* 151 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 155 */       stmt = getConnection().prepareStatement("select VIREMENT_LINE_SPREAD.LINE_SPREAD_ID,VIREMENT_LINE_SPREAD.STRUCTURE_ELEMENT_ID,VIREMENT_LINE_SPREAD.REQUEST_LINE_ID,VIREMENT_LINE_SPREAD.LINE_IDX,VIREMENT_LINE_SPREAD.HELD,VIREMENT_LINE_SPREAD.WEIGHTING,VIREMENT_LINE_SPREAD.UPDATED_BY_USER_ID,VIREMENT_LINE_SPREAD.UPDATED_TIME,VIREMENT_LINE_SPREAD.CREATED_TIME from VIREMENT_LINE_SPREAD where    LINE_SPREAD_ID = ? ");
/*     */ 
/* 158 */       int col = 1;
/* 159 */       stmt.setInt(col++, pk.getLineSpreadId());
/*     */ 
/* 161 */       resultSet = stmt.executeQuery();
/*     */ 
/* 163 */       if (!resultSet.next()) {
/* 164 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 167 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 168 */       if (this.mDetails.isModified())
/* 169 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 173 */       throw handleSQLException(pk, "select VIREMENT_LINE_SPREAD.LINE_SPREAD_ID,VIREMENT_LINE_SPREAD.STRUCTURE_ELEMENT_ID,VIREMENT_LINE_SPREAD.REQUEST_LINE_ID,VIREMENT_LINE_SPREAD.LINE_IDX,VIREMENT_LINE_SPREAD.HELD,VIREMENT_LINE_SPREAD.WEIGHTING,VIREMENT_LINE_SPREAD.UPDATED_BY_USER_ID,VIREMENT_LINE_SPREAD.UPDATED_TIME,VIREMENT_LINE_SPREAD.CREATED_TIME from VIREMENT_LINE_SPREAD where    LINE_SPREAD_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 177 */       closeResultSet(resultSet);
/* 178 */       closeStatement(stmt);
/* 179 */       closeConnection();
/*     */ 
/* 181 */       if (timer != null)
/* 182 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 217 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 218 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 223 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 224 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 225 */       stmt = getConnection().prepareStatement("insert into VIREMENT_LINE_SPREAD ( LINE_SPREAD_ID,STRUCTURE_ELEMENT_ID,REQUEST_LINE_ID,LINE_IDX,HELD,WEIGHTING,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 228 */       int col = 1;
/* 229 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 230 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 233 */       int resultCount = stmt.executeUpdate();
/* 234 */       if (resultCount != 1)
/*     */       {
/* 236 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 239 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 243 */       throw handleSQLException(this.mDetails.getPK(), "insert into VIREMENT_LINE_SPREAD ( LINE_SPREAD_ID,STRUCTURE_ELEMENT_ID,REQUEST_LINE_ID,LINE_IDX,HELD,WEIGHTING,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 247 */       closeStatement(stmt);
/* 248 */       closeConnection();
/*     */ 
/* 250 */       if (timer != null)
/* 251 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 278 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 282 */     PreparedStatement stmt = null;
/*     */ 
/* 284 */     boolean mainChanged = this.mDetails.isModified();
/* 285 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 288 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 291 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 292 */         stmt = getConnection().prepareStatement("update VIREMENT_LINE_SPREAD set STRUCTURE_ELEMENT_ID = ?,REQUEST_LINE_ID = ?,LINE_IDX = ?,HELD = ?,WEIGHTING = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    LINE_SPREAD_ID = ? ");
/*     */ 
/* 295 */         int col = 1;
/* 296 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 297 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 300 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 303 */         if (resultCount != 1) {
/* 304 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 307 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 316 */       throw handleSQLException(getPK(), "update VIREMENT_LINE_SPREAD set STRUCTURE_ELEMENT_ID = ?,REQUEST_LINE_ID = ?,LINE_IDX = ?,HELD = ?,WEIGHTING = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    LINE_SPREAD_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 320 */       closeStatement(stmt);
/* 321 */       closeConnection();
/*     */ 
/* 323 */       if ((timer != null) && (
/* 324 */         (mainChanged) || (dependantChanged)))
/* 325 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 344 */     if (items == null) {
/* 345 */       return false;
/*     */     }
/* 347 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 348 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 350 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 355 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 356 */       while (iter2.hasNext())
/*     */       {
/* 358 */         this.mDetails = ((VirementLineSpreadEVO)iter2.next());
/*     */ 
/* 361 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 363 */         somethingChanged = true;
/*     */ 
/* 366 */         if (deleteStmt == null) {
/* 367 */           deleteStmt = getConnection().prepareStatement("delete from VIREMENT_LINE_SPREAD where    LINE_SPREAD_ID = ? ");
/*     */         }
/*     */ 
/* 370 */         int col = 1;
/* 371 */         deleteStmt.setInt(col++, this.mDetails.getLineSpreadId());
/*     */ 
/* 373 */         if (this._log.isDebugEnabled()) {
/* 374 */           this._log.debug("update", "VirementLineSpread deleting LineSpreadId=" + this.mDetails.getLineSpreadId());
/*     */         }
/*     */ 
/* 379 */         deleteStmt.addBatch();
/*     */ 
/* 382 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 387 */       if (deleteStmt != null)
/*     */       {
/* 389 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 391 */         deleteStmt.executeBatch();
/*     */ 
/* 393 */         if (timer2 != null) {
/* 394 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 398 */       Iterator iter1 = items.values().iterator();
/* 399 */       while (iter1.hasNext())
/*     */       {
/* 401 */         this.mDetails = ((VirementLineSpreadEVO)iter1.next());
/*     */ 
/* 403 */         if (this.mDetails.insertPending())
/*     */         {
/* 405 */           somethingChanged = true;
/* 406 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 409 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 411 */         somethingChanged = true;
/* 412 */         doStore();
/*     */       }
/*     */ 
/* 423 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 427 */       throw handleSQLException("delete from VIREMENT_LINE_SPREAD where    LINE_SPREAD_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 431 */       if (deleteStmt != null)
/*     */       {
/* 433 */         closeStatement(deleteStmt);
/* 434 */         closeConnection();
/*     */       }
/*     */ 
/* 437 */       this.mDetails = null;
/*     */ 
/* 439 */       if ((somethingChanged) && 
/* 440 */         (timer != null))
/* 441 */         timer.logDebug("update", "collection"); 
/* 441 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 470 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 472 */     PreparedStatement stmt = null;
/* 473 */     ResultSet resultSet = null;
/*     */ 
/* 475 */     int itemCount = 0;
/*     */ 
/* 477 */     VirementRequestLineEVO owningEVO = null;
/* 478 */     Iterator ownersIter = owners.iterator();
/* 479 */     while (ownersIter.hasNext())
/*     */     {
/* 481 */       owningEVO = (VirementRequestLineEVO)ownersIter.next();
/* 482 */       owningEVO.setSpreadsAllItemsLoaded(true);
/*     */     }
/* 484 */     ownersIter = owners.iterator();
/* 485 */     owningEVO = (VirementRequestLineEVO)ownersIter.next();
/* 486 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 490 */       stmt = getConnection().prepareStatement("select VIREMENT_LINE_SPREAD.LINE_SPREAD_ID,VIREMENT_LINE_SPREAD.STRUCTURE_ELEMENT_ID,VIREMENT_LINE_SPREAD.REQUEST_LINE_ID,VIREMENT_LINE_SPREAD.LINE_IDX,VIREMENT_LINE_SPREAD.HELD,VIREMENT_LINE_SPREAD.WEIGHTING,VIREMENT_LINE_SPREAD.UPDATED_BY_USER_ID,VIREMENT_LINE_SPREAD.UPDATED_TIME,VIREMENT_LINE_SPREAD.CREATED_TIME from VIREMENT_LINE_SPREAD,VIREMENT_REQUEST_LINE,VIREMENT_REQUEST_GROUP,VIREMENT_REQUEST where 1=1 and VIREMENT_LINE_SPREAD.REQUEST_LINE_ID = VIREMENT_REQUEST_LINE.REQUEST_LINE_ID and VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID = VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_LINE_SPREAD.REQUEST_LINE_ID ,VIREMENT_LINE_SPREAD.LINE_SPREAD_ID");
/*     */ 
/* 492 */       int col = 1;
/* 493 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 495 */       resultSet = stmt.executeQuery();
/*     */ 
/* 498 */       while (resultSet.next())
/*     */       {
/* 500 */         itemCount++;
/* 501 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 506 */         while (this.mDetails.getRequestLineId() != owningEVO.getRequestLineId())
/*     */         {
/* 510 */           if (!ownersIter.hasNext())
/*     */           {
/* 512 */             this._log.debug("bulkGetAll", "can't find owning [RequestLineId=" + this.mDetails.getRequestLineId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 516 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 517 */             ownersIter = owners.iterator();
/* 518 */             while (ownersIter.hasNext())
/*     */             {
/* 520 */               owningEVO = (VirementRequestLineEVO)ownersIter.next();
/* 521 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 523 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 525 */           owningEVO = (VirementRequestLineEVO)ownersIter.next();
/*     */         }
/* 527 */         if (owningEVO.getSpreads() == null)
/*     */         {
/* 529 */           theseItems = new ArrayList();
/* 530 */           owningEVO.setSpreads(theseItems);
/* 531 */           owningEVO.setSpreadsAllItemsLoaded(true);
/*     */         }
/* 533 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 536 */       if (timer != null) {
/* 537 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 542 */       throw handleSQLException("select VIREMENT_LINE_SPREAD.LINE_SPREAD_ID,VIREMENT_LINE_SPREAD.STRUCTURE_ELEMENT_ID,VIREMENT_LINE_SPREAD.REQUEST_LINE_ID,VIREMENT_LINE_SPREAD.LINE_IDX,VIREMENT_LINE_SPREAD.HELD,VIREMENT_LINE_SPREAD.WEIGHTING,VIREMENT_LINE_SPREAD.UPDATED_BY_USER_ID,VIREMENT_LINE_SPREAD.UPDATED_TIME,VIREMENT_LINE_SPREAD.CREATED_TIME from VIREMENT_LINE_SPREAD,VIREMENT_REQUEST_LINE,VIREMENT_REQUEST_GROUP,VIREMENT_REQUEST where 1=1 and VIREMENT_LINE_SPREAD.REQUEST_LINE_ID = VIREMENT_REQUEST_LINE.REQUEST_LINE_ID and VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID = VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_LINE_SPREAD.REQUEST_LINE_ID ,VIREMENT_LINE_SPREAD.LINE_SPREAD_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 546 */       closeResultSet(resultSet);
/* 547 */       closeStatement(stmt);
/* 548 */       closeConnection();
/*     */ 
/* 550 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectRequestLineId, String dependants, Collection currentList)
/*     */   {
/* 575 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 576 */     PreparedStatement stmt = null;
/* 577 */     ResultSet resultSet = null;
/*     */ 
/* 579 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 583 */       stmt = getConnection().prepareStatement("select VIREMENT_LINE_SPREAD.LINE_SPREAD_ID,VIREMENT_LINE_SPREAD.STRUCTURE_ELEMENT_ID,VIREMENT_LINE_SPREAD.REQUEST_LINE_ID,VIREMENT_LINE_SPREAD.LINE_IDX,VIREMENT_LINE_SPREAD.HELD,VIREMENT_LINE_SPREAD.WEIGHTING,VIREMENT_LINE_SPREAD.UPDATED_BY_USER_ID,VIREMENT_LINE_SPREAD.UPDATED_TIME,VIREMENT_LINE_SPREAD.CREATED_TIME from VIREMENT_LINE_SPREAD where    REQUEST_LINE_ID = ? ");
/*     */ 
/* 585 */       int col = 1;
/* 586 */       stmt.setInt(col++, selectRequestLineId);
/*     */ 
/* 588 */       resultSet = stmt.executeQuery();
/*     */ 
/* 590 */       while (resultSet.next())
/*     */       {
/* 592 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 595 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 598 */       if (currentList != null)
/*     */       {
/* 601 */         ListIterator iter = items.listIterator();
/* 602 */         VirementLineSpreadEVO currentEVO = null;
/* 603 */         VirementLineSpreadEVO newEVO = null;
/* 604 */         while (iter.hasNext())
/*     */         {
/* 606 */           newEVO = (VirementLineSpreadEVO)iter.next();
/* 607 */           Iterator iter2 = currentList.iterator();
/* 608 */           while (iter2.hasNext())
/*     */           {
/* 610 */             currentEVO = (VirementLineSpreadEVO)iter2.next();
/* 611 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 613 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 619 */         Iterator iter2 = currentList.iterator();
/* 620 */         while (iter2.hasNext())
/*     */         {
/* 622 */           currentEVO = (VirementLineSpreadEVO)iter2.next();
/* 623 */           if (currentEVO.insertPending()) {
/* 624 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 628 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 632 */       throw handleSQLException("select VIREMENT_LINE_SPREAD.LINE_SPREAD_ID,VIREMENT_LINE_SPREAD.STRUCTURE_ELEMENT_ID,VIREMENT_LINE_SPREAD.REQUEST_LINE_ID,VIREMENT_LINE_SPREAD.LINE_IDX,VIREMENT_LINE_SPREAD.HELD,VIREMENT_LINE_SPREAD.WEIGHTING,VIREMENT_LINE_SPREAD.UPDATED_BY_USER_ID,VIREMENT_LINE_SPREAD.UPDATED_TIME,VIREMENT_LINE_SPREAD.CREATED_TIME from VIREMENT_LINE_SPREAD where    REQUEST_LINE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 636 */       closeResultSet(resultSet);
/* 637 */       closeStatement(stmt);
/* 638 */       closeConnection();
/*     */ 
/* 640 */       if (timer != null) {
/* 641 */         timer.logDebug("getAll", " RequestLineId=" + selectRequestLineId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 646 */     return items;
/*     */   }
/*     */ 
/*     */   public VirementLineSpreadEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 660 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 663 */     if (this.mDetails == null) {
/* 664 */       doLoad(((VirementLineSpreadCK)paramCK).getVirementLineSpreadPK());
/*     */     }
/* 666 */     else if (!this.mDetails.getPK().equals(((VirementLineSpreadCK)paramCK).getVirementLineSpreadPK())) {
/* 667 */       doLoad(((VirementLineSpreadCK)paramCK).getVirementLineSpreadPK());
/*     */     }
/*     */ 
/* 670 */     VirementLineSpreadEVO details = new VirementLineSpreadEVO();
/* 671 */     details = this.mDetails.deepClone();
/*     */ 
/* 673 */     if (timer != null) {
/* 674 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 676 */     return details;
/*     */   }
/*     */ 
/*     */   public VirementLineSpreadEVO getDetails(ModelCK paramCK, VirementLineSpreadEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 682 */     VirementLineSpreadEVO savedEVO = this.mDetails;
/* 683 */     this.mDetails = paramEVO;
/* 684 */     VirementLineSpreadEVO newEVO = getDetails(paramCK, dependants);
/* 685 */     this.mDetails = savedEVO;
/* 686 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public VirementLineSpreadEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 692 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 696 */     VirementLineSpreadEVO details = this.mDetails.deepClone();
/*     */ 
/* 698 */     if (timer != null) {
/* 699 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 701 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 706 */     return "VirementLineSpread";
/*     */   }
/*     */ 
/*     */   public VirementLineSpreadRefImpl getRef(VirementLineSpreadPK paramVirementLineSpreadPK)
/*     */   {
/* 711 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 712 */     PreparedStatement stmt = null;
/* 713 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 716 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,VIREMENT_REQUEST.REQUEST_ID,VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID,VIREMENT_REQUEST_LINE.REQUEST_LINE_ID from VIREMENT_LINE_SPREAD,MODEL,VIREMENT_REQUEST,VIREMENT_REQUEST_GROUP,VIREMENT_REQUEST_LINE where 1=1 and VIREMENT_LINE_SPREAD.LINE_SPREAD_ID = ? and VIREMENT_LINE_SPREAD.REQUEST_LINE_ID = VIREMENT_REQUEST_LINE.REQUEST_LINE_ID and VIREMENT_REQUEST_LINE.REQUEST_LINE_ID = VIREMENT_REQUEST_GROUP.REQUEST_LINE_ID and VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID = VIREMENT_REQUEST.REQUEST_GROUP_ID and VIREMENT_REQUEST.REQUEST_ID = MODEL.REQUEST_ID");
/* 717 */       int col = 1;
/* 718 */       stmt.setInt(col++, paramVirementLineSpreadPK.getLineSpreadId());
/*     */ 
/* 720 */       resultSet = stmt.executeQuery();
/*     */ 
/* 722 */       if (!resultSet.next()) {
/* 723 */         throw new RuntimeException(getEntityName() + " getRef " + paramVirementLineSpreadPK + " not found");
/*     */       }
/* 725 */       col = 2;
/* 726 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 730 */       VirementRequestPK newVirementRequestPK = new VirementRequestPK(resultSet.getInt(col++));
/*     */ 
/* 734 */       VirementRequestGroupPK newVirementRequestGroupPK = new VirementRequestGroupPK(resultSet.getInt(col++));
/*     */ 
/* 738 */       VirementRequestLinePK newVirementRequestLinePK = new VirementRequestLinePK(resultSet.getInt(col++));
/*     */ 
/* 742 */       String textVirementLineSpread = "";
/* 743 */       VirementLineSpreadCK ckVirementLineSpread = new VirementLineSpreadCK(newModelPK, newVirementRequestPK, newVirementRequestGroupPK, newVirementRequestLinePK, paramVirementLineSpreadPK);
/*     */ 
/* 751 */       VirementLineSpreadRefImpl localVirementLineSpreadRefImpl = new VirementLineSpreadRefImpl(ckVirementLineSpread, textVirementLineSpread);
/*     */       return localVirementLineSpreadRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 756 */       throw handleSQLException(paramVirementLineSpreadPK, "select 0,MODEL.MODEL_ID,VIREMENT_REQUEST.REQUEST_ID,VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID,VIREMENT_REQUEST_LINE.REQUEST_LINE_ID from VIREMENT_LINE_SPREAD,MODEL,VIREMENT_REQUEST,VIREMENT_REQUEST_GROUP,VIREMENT_REQUEST_LINE where 1=1 and VIREMENT_LINE_SPREAD.LINE_SPREAD_ID = ? and VIREMENT_LINE_SPREAD.REQUEST_LINE_ID = VIREMENT_REQUEST_LINE.REQUEST_LINE_ID and VIREMENT_REQUEST_LINE.REQUEST_LINE_ID = VIREMENT_REQUEST_GROUP.REQUEST_LINE_ID and VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID = VIREMENT_REQUEST.REQUEST_GROUP_ID and VIREMENT_REQUEST.REQUEST_ID = MODEL.REQUEST_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 760 */       closeResultSet(resultSet);
/* 761 */       closeStatement(stmt);
/* 762 */       closeConnection();
/*     */ 
/* 764 */       if (timer != null)
/* 765 */         timer.logDebug("getRef", paramVirementLineSpreadPK); 
/* 765 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.virement.VirementLineSpreadDAO
 * JD-Core Version:    0.6.0
 */