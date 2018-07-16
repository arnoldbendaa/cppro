/*     */ package com.cedar.cp.ejb.impl.model.amm;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.amm.AmmDimensionCK;
/*     */ import com.cedar.cp.dto.model.amm.AmmDimensionElementCK;
/*     */ import com.cedar.cp.dto.model.amm.AmmDimensionElementPK;
/*     */ import com.cedar.cp.dto.model.amm.AmmDimensionPK;
/*     */ import com.cedar.cp.dto.model.amm.AmmDimensionRefImpl;
/*     */ import com.cedar.cp.dto.model.amm.AmmModelCK;
/*     */ import com.cedar.cp.dto.model.amm.AmmModelPK;
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
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class AmmDimensionDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select AMM_DIMENSION.AMM_DIMENSION_ID,AMM_DIMENSION.AMM_MODEL_ID,AMM_DIMENSION.DIMENSION_ID,AMM_DIMENSION.SRC_DIMENSION_ID,AMM_DIMENSION.SRC_HIERARCHY_ID,AMM_DIMENSION.UPDATED_BY_USER_ID,AMM_DIMENSION.UPDATED_TIME,AMM_DIMENSION.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from AMM_DIMENSION where    AMM_DIMENSION_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into AMM_DIMENSION ( AMM_DIMENSION_ID,AMM_MODEL_ID,DIMENSION_ID,SRC_DIMENSION_ID,SRC_HIERARCHY_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update AMM_DIMENSION set AMM_MODEL_ID = ?,DIMENSION_ID = ?,SRC_DIMENSION_ID = ?,SRC_HIERARCHY_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_DIMENSION_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from AMM_DIMENSION where    AMM_DIMENSION_ID = ? ";
/* 476 */   private static String[][] SQL_DELETE_CHILDREN = { { "AMM_DIMENSION_ELEMENT", "delete from AMM_DIMENSION_ELEMENT where     AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID = ? " } };
/*     */ 
/* 485 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "AMM_SRC_STRUCTURE_ELEMENT", "delete from AMM_SRC_STRUCTURE_ELEMENT AmmSrcStructureElement where exists (select * from AMM_SRC_STRUCTURE_ELEMENT,AMM_DIMENSION_ELEMENT,AMM_DIMENSION where     AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID = AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID = AMM_DIMENSION.AMM_DIMENSION_ID and AmmSrcStructureElement.AMM_DIMENSION_ELEMENT_ID = AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID " } };
/*     */ 
/* 500 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and AMM_DIMENSION.AMM_DIMENSION_ID = ?)";
/*     */   public static final String SQL_BULK_GET_ALL = " from AMM_DIMENSION where 1=1 and AMM_DIMENSION.AMM_MODEL_ID = ? order by  AMM_DIMENSION.AMM_DIMENSION_ID";
/*     */   protected static final String SQL_GET_ALL = " from AMM_DIMENSION where    AMM_MODEL_ID = ? ";
/*     */   protected AmmDimensionElementDAO mAmmDimensionElementDAO;
/*     */   protected AmmDimensionEVO mDetails;
/*     */ 
/*     */   public AmmDimensionDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public AmmDimensionDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AmmDimensionDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected AmmDimensionPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(AmmDimensionEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private AmmDimensionEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  91 */     int col = 1;
/*  92 */     AmmDimensionEVO evo = new AmmDimensionEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), null);
/*     */ 
/* 101 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 102 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 103 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 104 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(AmmDimensionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 109 */     int col = startCol_;
/* 110 */     stmt_.setInt(col++, evo_.getAmmDimensionId());
/* 111 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(AmmDimensionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 116 */     int col = startCol_;
/* 117 */     stmt_.setInt(col++, evo_.getAmmModelId());
/* 118 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDimensionId());
/* 119 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getSrcDimensionId());
/* 120 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getSrcHierarchyId());
/* 121 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 122 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 123 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 124 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(AmmDimensionPK pk)
/*     */     throws ValidationException
/*     */   {
/* 140 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 142 */     PreparedStatement stmt = null;
/* 143 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 147 */       stmt = getConnection().prepareStatement("select AMM_DIMENSION.AMM_DIMENSION_ID,AMM_DIMENSION.AMM_MODEL_ID,AMM_DIMENSION.DIMENSION_ID,AMM_DIMENSION.SRC_DIMENSION_ID,AMM_DIMENSION.SRC_HIERARCHY_ID,AMM_DIMENSION.UPDATED_BY_USER_ID,AMM_DIMENSION.UPDATED_TIME,AMM_DIMENSION.CREATED_TIME from AMM_DIMENSION where    AMM_DIMENSION_ID = ? ");
/*     */ 
/* 150 */       int col = 1;
/* 151 */       stmt.setInt(col++, pk.getAmmDimensionId());
/*     */ 
/* 153 */       resultSet = stmt.executeQuery();
/*     */ 
/* 155 */       if (!resultSet.next()) {
/* 156 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 159 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 160 */       if (this.mDetails.isModified())
/* 161 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 165 */       throw handleSQLException(pk, "select AMM_DIMENSION.AMM_DIMENSION_ID,AMM_DIMENSION.AMM_MODEL_ID,AMM_DIMENSION.DIMENSION_ID,AMM_DIMENSION.SRC_DIMENSION_ID,AMM_DIMENSION.SRC_HIERARCHY_ID,AMM_DIMENSION.UPDATED_BY_USER_ID,AMM_DIMENSION.UPDATED_TIME,AMM_DIMENSION.CREATED_TIME from AMM_DIMENSION where    AMM_DIMENSION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 169 */       closeResultSet(resultSet);
/* 170 */       closeStatement(stmt);
/* 171 */       closeConnection();
/*     */ 
/* 173 */       if (timer != null)
/* 174 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 207 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 208 */     this.mDetails.postCreateInit();
/*     */ 
/* 210 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 215 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 216 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 217 */       stmt = getConnection().prepareStatement("insert into AMM_DIMENSION ( AMM_DIMENSION_ID,AMM_MODEL_ID,DIMENSION_ID,SRC_DIMENSION_ID,SRC_HIERARCHY_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");
/*     */ 
/* 220 */       int col = 1;
/* 221 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 222 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 225 */       int resultCount = stmt.executeUpdate();
/* 226 */       if (resultCount != 1)
/*     */       {
/* 228 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 231 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 235 */       throw handleSQLException(this.mDetails.getPK(), "insert into AMM_DIMENSION ( AMM_DIMENSION_ID,AMM_MODEL_ID,DIMENSION_ID,SRC_DIMENSION_ID,SRC_HIERARCHY_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 239 */       closeStatement(stmt);
/* 240 */       closeConnection();
/*     */ 
/* 242 */       if (timer != null) {
/* 243 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 249 */       getAmmDimensionElementDAO().update(this.mDetails.getAmmDimElementsMap());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 255 */       throw new RuntimeException("unexpected exception", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 281 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 285 */     PreparedStatement stmt = null;
/*     */ 
/* 287 */     boolean mainChanged = this.mDetails.isModified();
/* 288 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 292 */       if (getAmmDimensionElementDAO().update(this.mDetails.getAmmDimElementsMap())) {
/* 293 */         dependantChanged = true;
/*     */       }
/* 295 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 298 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 299 */         stmt = getConnection().prepareStatement("update AMM_DIMENSION set AMM_MODEL_ID = ?,DIMENSION_ID = ?,SRC_DIMENSION_ID = ?,SRC_HIERARCHY_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_DIMENSION_ID = ? ");
/*     */ 
/* 302 */         int col = 1;
/* 303 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 304 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 307 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 310 */         if (resultCount != 1) {
/* 311 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 314 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 323 */       throw handleSQLException(getPK(), "update AMM_DIMENSION set AMM_MODEL_ID = ?,DIMENSION_ID = ?,SRC_DIMENSION_ID = ?,SRC_HIERARCHY_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_DIMENSION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 327 */       closeStatement(stmt);
/* 328 */       closeConnection();
/*     */ 
/* 330 */       if ((timer != null) && (
/* 331 */         (mainChanged) || (dependantChanged)))
/* 332 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 351 */     if (items == null) {
/* 352 */       return false;
/*     */     }
/* 354 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 355 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 357 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 361 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/* 362 */       while (iter3.hasNext())
/*     */       {
/* 364 */         this.mDetails = ((AmmDimensionEVO)iter3.next());
/* 365 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 367 */         somethingChanged = true;
/*     */ 
/* 370 */         deleteDependants(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 374 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 375 */       while (iter2.hasNext())
/*     */       {
/* 377 */         this.mDetails = ((AmmDimensionEVO)iter2.next());
/*     */ 
/* 380 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 382 */         somethingChanged = true;
/*     */ 
/* 385 */         if (deleteStmt == null) {
/* 386 */           deleteStmt = getConnection().prepareStatement("delete from AMM_DIMENSION where    AMM_DIMENSION_ID = ? ");
/*     */         }
/*     */ 
/* 389 */         int col = 1;
/* 390 */         deleteStmt.setInt(col++, this.mDetails.getAmmDimensionId());
/*     */ 
/* 392 */         if (this._log.isDebugEnabled()) {
/* 393 */           this._log.debug("update", "AmmDimension deleting AmmDimensionId=" + this.mDetails.getAmmDimensionId());
/*     */         }
/*     */ 
/* 398 */         deleteStmt.addBatch();
/*     */ 
/* 401 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 406 */       if (deleteStmt != null)
/*     */       {
/* 408 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 410 */         deleteStmt.executeBatch();
/*     */ 
/* 412 */         if (timer2 != null) {
/* 413 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 417 */       Iterator iter1 = items.values().iterator();
/* 418 */       while (iter1.hasNext())
/*     */       {
/* 420 */         this.mDetails = ((AmmDimensionEVO)iter1.next());
/*     */ 
/* 422 */         if (this.mDetails.insertPending())
/*     */         {
/* 424 */           somethingChanged = true;
/* 425 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 428 */         if (this.mDetails.isModified())
/*     */         {
/* 430 */           somethingChanged = true;
/* 431 */           doStore(); continue;
/*     */         }
/*     */ 
/* 435 */         if ((this.mDetails.deletePending()) || 
/* 441 */           (!getAmmDimensionElementDAO().update(this.mDetails.getAmmDimElementsMap()))) continue;
/* 442 */         somethingChanged = true;
/*     */       }
/*     */ 
/* 454 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 458 */       throw handleSQLException("delete from AMM_DIMENSION where    AMM_DIMENSION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 462 */       if (deleteStmt != null)
/*     */       {
/* 464 */         closeStatement(deleteStmt);
/* 465 */         closeConnection();
/*     */       }
/*     */ 
/* 468 */       this.mDetails = null;
/*     */ 
/* 470 */       if ((somethingChanged) && 
/* 471 */         (timer != null))
/* 472 */         timer.logDebug("update", "collection"); 
/* 472 */     }
/*     */   }
/*     */ 
/*     */   private void deleteDependants(AmmDimensionPK pk)
/*     */   {
/* 509 */     Set emptyStrings = Collections.emptySet();
/* 510 */     deleteDependants(pk, emptyStrings);
/*     */   }
/*     */ 
/*     */   private void deleteDependants(AmmDimensionPK pk, Set<String> exclusionTables)
/*     */   {
/* 516 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*     */     {
/* 518 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*     */         continue;
/* 520 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 522 */       PreparedStatement stmt = null;
/*     */ 
/* 524 */       int resultCount = 0;
/* 525 */       String s = null;
/*     */       try
/*     */       {
/* 528 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*     */ 
/* 530 */         if (this._log.isDebugEnabled()) {
/* 531 */           this._log.debug("deleteDependants", s);
/*     */         }
/* 533 */         stmt = getConnection().prepareStatement(s);
/*     */ 
/* 536 */         int col = 1;
/* 537 */         stmt.setInt(col++, pk.getAmmDimensionId());
/*     */ 
/* 540 */         resultCount = stmt.executeUpdate();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 544 */         throw handleSQLException(pk, s, sqle);
/*     */       }
/*     */       finally
/*     */       {
/* 548 */         closeStatement(stmt);
/* 549 */         closeConnection();
/*     */ 
/* 551 */         if (timer != null) {
/* 552 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*     */         }
/*     */       }
/*     */     }
/* 556 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*     */     {
/* 558 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*     */         continue;
/* 560 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 562 */       PreparedStatement stmt = null;
/*     */ 
/* 564 */       int resultCount = 0;
/* 565 */       String s = null;
/*     */       try
/*     */       {
/* 568 */         s = SQL_DELETE_CHILDREN[i][1];
/*     */ 
/* 570 */         if (this._log.isDebugEnabled()) {
/* 571 */           this._log.debug("deleteDependants", s);
/*     */         }
/* 573 */         stmt = getConnection().prepareStatement(s);
/*     */ 
/* 576 */         int col = 1;
/* 577 */         stmt.setInt(col++, pk.getAmmDimensionId());
/*     */ 
/* 580 */         resultCount = stmt.executeUpdate();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 584 */         throw handleSQLException(pk, s, sqle);
/*     */       }
/*     */       finally
/*     */       {
/* 588 */         closeStatement(stmt);
/* 589 */         closeConnection();
/*     */ 
/* 591 */         if (timer != null)
/* 592 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(AmmModelPK entityPK, AmmModelEVO owningEVO, String dependants)
/*     */   {
/* 612 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 614 */     PreparedStatement stmt = null;
/* 615 */     ResultSet resultSet = null;
/*     */ 
/* 617 */     int itemCount = 0;
/*     */ 
/* 619 */     Collection theseItems = new ArrayList();
/* 620 */     owningEVO.setAmmDimensions(theseItems);
/* 621 */     owningEVO.setAmmDimensionsAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 625 */       stmt = getConnection().prepareStatement("select AMM_DIMENSION.AMM_DIMENSION_ID,AMM_DIMENSION.AMM_MODEL_ID,AMM_DIMENSION.DIMENSION_ID,AMM_DIMENSION.SRC_DIMENSION_ID,AMM_DIMENSION.SRC_HIERARCHY_ID,AMM_DIMENSION.UPDATED_BY_USER_ID,AMM_DIMENSION.UPDATED_TIME,AMM_DIMENSION.CREATED_TIME from AMM_DIMENSION where 1=1 and AMM_DIMENSION.AMM_MODEL_ID = ? order by  AMM_DIMENSION.AMM_DIMENSION_ID");
/*     */ 
/* 627 */       int col = 1;
/* 628 */       stmt.setInt(col++, entityPK.getAmmModelId());
/*     */ 
/* 630 */       resultSet = stmt.executeQuery();
/*     */ 
/* 633 */       while (resultSet.next())
/*     */       {
/* 635 */         itemCount++;
/* 636 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 638 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 641 */       if (timer != null) {
/* 642 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */ 
/* 645 */       if ((itemCount > 0) && (dependants.indexOf("<1>") > -1))
/*     */       {
/* 647 */         getAmmDimensionElementDAO().bulkGetAll(entityPK, theseItems, dependants);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle) {
/* 651 */       throw handleSQLException("select AMM_DIMENSION.AMM_DIMENSION_ID,AMM_DIMENSION.AMM_MODEL_ID,AMM_DIMENSION.DIMENSION_ID,AMM_DIMENSION.SRC_DIMENSION_ID,AMM_DIMENSION.SRC_HIERARCHY_ID,AMM_DIMENSION.UPDATED_BY_USER_ID,AMM_DIMENSION.UPDATED_TIME,AMM_DIMENSION.CREATED_TIME from AMM_DIMENSION where 1=1 and AMM_DIMENSION.AMM_MODEL_ID = ? order by  AMM_DIMENSION.AMM_DIMENSION_ID", sqle);
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
/*     */   public Collection getAll(int selectAmmModelId, String dependants, Collection currentList)
/*     */   {
/* 684 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 685 */     PreparedStatement stmt = null;
/* 686 */     ResultSet resultSet = null;
/*     */ 
/* 688 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 692 */       stmt = getConnection().prepareStatement("select AMM_DIMENSION.AMM_DIMENSION_ID,AMM_DIMENSION.AMM_MODEL_ID,AMM_DIMENSION.DIMENSION_ID,AMM_DIMENSION.SRC_DIMENSION_ID,AMM_DIMENSION.SRC_HIERARCHY_ID,AMM_DIMENSION.UPDATED_BY_USER_ID,AMM_DIMENSION.UPDATED_TIME,AMM_DIMENSION.CREATED_TIME from AMM_DIMENSION where    AMM_MODEL_ID = ? ");
/*     */ 
/* 694 */       int col = 1;
/* 695 */       stmt.setInt(col++, selectAmmModelId);
/*     */ 
/* 697 */       resultSet = stmt.executeQuery();
/*     */ 
/* 699 */       while (resultSet.next())
/*     */       {
/* 701 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 704 */         getDependants(this.mDetails, dependants);
/*     */ 
/* 707 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 710 */       if (currentList != null)
/*     */       {
/* 713 */         ListIterator iter = items.listIterator();
/* 714 */         AmmDimensionEVO currentEVO = null;
/* 715 */         AmmDimensionEVO newEVO = null;
/* 716 */         while (iter.hasNext())
/*     */         {
/* 718 */           newEVO = (AmmDimensionEVO)iter.next();
/* 719 */           Iterator iter2 = currentList.iterator();
/* 720 */           while (iter2.hasNext())
/*     */           {
/* 722 */             currentEVO = (AmmDimensionEVO)iter2.next();
/* 723 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 725 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 731 */         Iterator iter2 = currentList.iterator();
/* 732 */         while (iter2.hasNext())
/*     */         {
/* 734 */           currentEVO = (AmmDimensionEVO)iter2.next();
/* 735 */           if (currentEVO.insertPending()) {
/* 736 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 740 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 744 */       throw handleSQLException("select AMM_DIMENSION.AMM_DIMENSION_ID,AMM_DIMENSION.AMM_MODEL_ID,AMM_DIMENSION.DIMENSION_ID,AMM_DIMENSION.SRC_DIMENSION_ID,AMM_DIMENSION.SRC_HIERARCHY_ID,AMM_DIMENSION.UPDATED_BY_USER_ID,AMM_DIMENSION.UPDATED_TIME,AMM_DIMENSION.CREATED_TIME from AMM_DIMENSION where    AMM_MODEL_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 748 */       closeResultSet(resultSet);
/* 749 */       closeStatement(stmt);
/* 750 */       closeConnection();
/*     */ 
/* 752 */       if (timer != null) {
/* 753 */         timer.logDebug("getAll", " AmmModelId=" + selectAmmModelId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 758 */     return items;
/*     */   }
/*     */ 
/*     */   public AmmDimensionEVO getDetails(AmmModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 777 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 780 */     if (this.mDetails == null) {
/* 781 */       doLoad(((AmmDimensionCK)paramCK).getAmmDimensionPK());
/*     */     }
/* 783 */     else if (!this.mDetails.getPK().equals(((AmmDimensionCK)paramCK).getAmmDimensionPK())) {
/* 784 */       doLoad(((AmmDimensionCK)paramCK).getAmmDimensionPK());
/*     */     }
/*     */ 
/* 787 */     if ((dependants.indexOf("<1>") > -1) && (!this.mDetails.isAmmDimElementsAllItemsLoaded()))
/*     */     {
/* 792 */       this.mDetails.setAmmDimElements(getAmmDimensionElementDAO().getAll(this.mDetails.getAmmDimensionId(), dependants, this.mDetails.getAmmDimElements()));
/*     */ 
/* 799 */       this.mDetails.setAmmDimElementsAllItemsLoaded(true);
/*     */     }
/*     */ 
/* 802 */     if ((paramCK instanceof AmmDimensionElementCK))
/*     */     {
/* 804 */       if (this.mDetails.getAmmDimElements() == null) {
/* 805 */         this.mDetails.loadAmmDimElementsItem(getAmmDimensionElementDAO().getDetails(paramCK, dependants));
/*     */       }
/*     */       else {
/* 808 */         AmmDimensionElementPK pk = ((AmmDimensionElementCK)paramCK).getAmmDimensionElementPK();
/* 809 */         AmmDimensionElementEVO evo = this.mDetails.getAmmDimElementsItem(pk);
/* 810 */         if (evo == null)
/* 811 */           this.mDetails.loadAmmDimElementsItem(getAmmDimensionElementDAO().getDetails(paramCK, dependants));
/*     */         else {
/* 813 */           getAmmDimensionElementDAO().getDetails(paramCK, evo, dependants);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 818 */     AmmDimensionEVO details = new AmmDimensionEVO();
/* 819 */     details = this.mDetails.deepClone();
/*     */ 
/* 821 */     if (timer != null) {
/* 822 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 824 */     return details;
/*     */   }
/*     */ 
/*     */   public AmmDimensionEVO getDetails(AmmModelCK paramCK, AmmDimensionEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 830 */     AmmDimensionEVO savedEVO = this.mDetails;
/* 831 */     this.mDetails = paramEVO;
/* 832 */     AmmDimensionEVO newEVO = getDetails(paramCK, dependants);
/* 833 */     this.mDetails = savedEVO;
/* 834 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public AmmDimensionEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 840 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 844 */     getDependants(this.mDetails, dependants);
/*     */ 
/* 847 */     AmmDimensionEVO details = this.mDetails.deepClone();
/*     */ 
/* 849 */     if (timer != null) {
/* 850 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 852 */     return details;
/*     */   }
/*     */ 
/*     */   protected AmmDimensionElementDAO getAmmDimensionElementDAO()
/*     */   {
/* 861 */     if (this.mAmmDimensionElementDAO == null)
/*     */     {
/* 863 */       if (this.mDataSource != null)
/* 864 */         this.mAmmDimensionElementDAO = new AmmDimensionElementDAO(this.mDataSource);
/*     */       else {
/* 866 */         this.mAmmDimensionElementDAO = new AmmDimensionElementDAO(getConnection());
/*     */       }
/*     */     }
/* 869 */     return this.mAmmDimensionElementDAO;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 874 */     return "AmmDimension";
/*     */   }
/*     */ 
/*     */   public AmmDimensionRefImpl getRef(AmmDimensionPK paramAmmDimensionPK)
/*     */   {
/* 879 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 880 */     PreparedStatement stmt = null;
/* 881 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 884 */       stmt = getConnection().prepareStatement("select 0,AMM_MODEL.AMM_MODEL_ID from AMM_DIMENSION,AMM_MODEL where 1=1 and AMM_DIMENSION.AMM_DIMENSION_ID = ? and AMM_DIMENSION.AMM_MODEL_ID = AMM_MODEL.AMM_MODEL_ID");
/* 885 */       int col = 1;
/* 886 */       stmt.setInt(col++, paramAmmDimensionPK.getAmmDimensionId());
/*     */ 
/* 888 */       resultSet = stmt.executeQuery();
/*     */ 
/* 890 */       if (!resultSet.next()) {
/* 891 */         throw new RuntimeException(getEntityName() + " getRef " + paramAmmDimensionPK + " not found");
/*     */       }
/* 893 */       col = 2;
/* 894 */       AmmModelPK newAmmModelPK = new AmmModelPK(resultSet.getInt(col++));
/*     */ 
/* 898 */       String textAmmDimension = "";
/* 899 */       AmmDimensionCK ckAmmDimension = new AmmDimensionCK(newAmmModelPK, paramAmmDimensionPK);
/*     */ 
/* 904 */       AmmDimensionRefImpl localAmmDimensionRefImpl = new AmmDimensionRefImpl(ckAmmDimension, textAmmDimension);
/*     */       return localAmmDimensionRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 909 */       throw handleSQLException(paramAmmDimensionPK, "select 0,AMM_MODEL.AMM_MODEL_ID from AMM_DIMENSION,AMM_MODEL where 1=1 and AMM_DIMENSION.AMM_DIMENSION_ID = ? and AMM_DIMENSION.AMM_MODEL_ID = AMM_MODEL.AMM_MODEL_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 913 */       closeResultSet(resultSet);
/* 914 */       closeStatement(stmt);
/* 915 */       closeConnection();
/*     */ 
/* 917 */       if (timer != null)
/* 918 */         timer.logDebug("getRef", paramAmmDimensionPK); 
/* 918 */     }
/*     */   }
/*     */ 
/*     */   public void getDependants(Collection c, String dependants)
/*     */   {
/* 930 */     if (c == null)
/* 931 */       return;
/* 932 */     Iterator iter = c.iterator();
/* 933 */     while (iter.hasNext())
/*     */     {
/* 935 */       AmmDimensionEVO evo = (AmmDimensionEVO)iter.next();
/* 936 */       getDependants(evo, dependants);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void getDependants(AmmDimensionEVO evo, String dependants)
/*     */   {
/* 950 */     if (evo.insertPending()) {
/* 951 */       return;
/*     */     }
/* 953 */     if (evo.getAmmDimensionId() < 1) {
/* 954 */       return;
/*     */     }
/*     */ 
/* 958 */     if ((dependants.indexOf("<1>") > -1) || (dependants.indexOf("<2>") > -1))
/*     */     {
/* 962 */       if (!evo.isAmmDimElementsAllItemsLoaded())
/*     */       {
/* 964 */         evo.setAmmDimElements(getAmmDimensionElementDAO().getAll(evo.getAmmDimensionId(), dependants, evo.getAmmDimElements()));
/*     */ 
/* 971 */         evo.setAmmDimElementsAllItemsLoaded(true);
/*     */       }
/* 973 */       getAmmDimensionElementDAO().getDependants(evo.getAmmDimElements(), dependants);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.amm.AmmDimensionDAO
 * JD-Core Version:    0.6.0
 */