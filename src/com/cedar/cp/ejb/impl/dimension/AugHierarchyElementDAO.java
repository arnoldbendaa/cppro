/*     */ package com.cedar.cp.ejb.impl.dimension;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.dimension.AugHierarachyElementELO;
/*     */ import com.cedar.cp.dto.dimension.AugHierarchyElementCK;
/*     */ import com.cedar.cp.dto.dimension.AugHierarchyElementPK;
/*     */ import com.cedar.cp.dto.dimension.AugHierarchyElementRefImpl;
/*     */ import com.cedar.cp.dto.dimension.DimensionCK;
/*     */ import com.cedar.cp.dto.dimension.DimensionPK;
/*     */ import com.cedar.cp.dto.dimension.DimensionRefImpl;
/*     */ import com.cedar.cp.dto.dimension.HierarchyCK;
/*     */ import com.cedar.cp.dto.dimension.HierarchyPK;
/*     */ import com.cedar.cp.dto.dimension.HierarchyRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class AugHierarchyElementDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,AUG_HIERARCHY_ELEMENT.HIERARCHY_ID,AUG_HIERARCHY_ELEMENT.PARENT_ID,AUG_HIERARCHY_ELEMENT.CHILD_INDEX,AUG_HIERARCHY_ELEMENT.VIS_ID,AUG_HIERARCHY_ELEMENT.DESCRIPTION,AUG_HIERARCHY_ELEMENT.CREDIT_DEBIT";
/*     */   protected static final String SQL_LOAD = " from AUG_HIERARCHY_ELEMENT where    HIERARCHY_ELEMENT_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into AUG_HIERARCHY_ELEMENT ( HIERARCHY_ELEMENT_ID,HIERARCHY_ID,PARENT_ID,CHILD_INDEX,VIS_ID,DESCRIPTION,CREDIT_DEBIT) values ( ?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update AUG_HIERARCHY_ELEMENT set HIERARCHY_ID = ?,PARENT_ID = ?,CHILD_INDEX = ?,VIS_ID = ?,DESCRIPTION = ?,CREDIT_DEBIT = ? where    HIERARCHY_ELEMENT_ID = ? ";
/* 310 */   protected static String SQL_AUG_HIERARACHY_ELEMENT = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID      ,AUG_HIERARCHY_ELEMENT.VIS_ID from AUG_HIERARCHY_ELEMENT    ,DIMENSION    ,HIERARCHY where 1=1   and AUG_HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID   and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  HIERARCHY_ELEMENT_ID = ?";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from AUG_HIERARCHY_ELEMENT where    HIERARCHY_ELEMENT_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from AUG_HIERARCHY_ELEMENT,HIERARCHY where 1=1 and AUG_HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.DIMENSION_ID = ? order by  AUG_HIERARCHY_ELEMENT.HIERARCHY_ID ,AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID";
/*     */   protected static final String SQL_GET_ALL = " from AUG_HIERARCHY_ELEMENT where    HIERARCHY_ID = ? ";
/*     */   protected AugHierarchyElementEVO mDetails;
/*     */ 
/*     */   public AugHierarchyElementDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public AugHierarchyElementDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AugHierarchyElementDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected AugHierarchyElementPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(AugHierarchyElementEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private AugHierarchyElementEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  90 */     int col = 1;
/*  91 */     AugHierarchyElementEVO evo = new AugHierarchyElementEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++));
/*     */ 
/* 101 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(AugHierarchyElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 106 */     int col = startCol_;
/* 107 */     stmt_.setInt(col++, evo_.getHierarchyElementId());
/* 108 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(AugHierarchyElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 113 */     int col = startCol_;
/* 114 */     stmt_.setInt(col++, evo_.getHierarchyId());
/* 115 */     stmt_.setInt(col++, evo_.getParentId());
/* 116 */     stmt_.setInt(col++, evo_.getChildIndex());
/* 117 */     stmt_.setString(col++, evo_.getVisId());
/* 118 */     stmt_.setString(col++, evo_.getDescription());
/* 119 */     stmt_.setInt(col++, evo_.getCreditDebit());
/* 120 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(AugHierarchyElementPK pk)
/*     */     throws ValidationException
/*     */   {
/* 136 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 138 */     PreparedStatement stmt = null;
/* 139 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 143 */       stmt = getConnection().prepareStatement("select AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,AUG_HIERARCHY_ELEMENT.HIERARCHY_ID,AUG_HIERARCHY_ELEMENT.PARENT_ID,AUG_HIERARCHY_ELEMENT.CHILD_INDEX,AUG_HIERARCHY_ELEMENT.VIS_ID,AUG_HIERARCHY_ELEMENT.DESCRIPTION,AUG_HIERARCHY_ELEMENT.CREDIT_DEBIT from AUG_HIERARCHY_ELEMENT where    HIERARCHY_ELEMENT_ID = ? ");
/*     */ 
/* 146 */       int col = 1;
/* 147 */       stmt.setInt(col++, pk.getHierarchyElementId());
/*     */ 
/* 149 */       resultSet = stmt.executeQuery();
/*     */ 
/* 151 */       if (!resultSet.next()) {
/* 152 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 155 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 156 */       if (this.mDetails.isModified())
/* 157 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 161 */       throw handleSQLException(pk, "select AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,AUG_HIERARCHY_ELEMENT.HIERARCHY_ID,AUG_HIERARCHY_ELEMENT.PARENT_ID,AUG_HIERARCHY_ELEMENT.CHILD_INDEX,AUG_HIERARCHY_ELEMENT.VIS_ID,AUG_HIERARCHY_ELEMENT.DESCRIPTION,AUG_HIERARCHY_ELEMENT.CREDIT_DEBIT from AUG_HIERARCHY_ELEMENT where    HIERARCHY_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 165 */       closeResultSet(resultSet);
/* 166 */       closeStatement(stmt);
/* 167 */       closeConnection();
/*     */ 
/* 169 */       if (timer != null)
/* 170 */         timer.logDebug("doLoad", pk);
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
/* 206 */       stmt = getConnection().prepareStatement("insert into AUG_HIERARCHY_ELEMENT ( HIERARCHY_ELEMENT_ID,HIERARCHY_ID,PARENT_ID,CHILD_INDEX,VIS_ID,DESCRIPTION,CREDIT_DEBIT) values ( ?,?,?,?,?,?,?)");
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
/* 224 */       throw handleSQLException(this.mDetails.getPK(), "insert into AUG_HIERARCHY_ELEMENT ( HIERARCHY_ELEMENT_ID,HIERARCHY_ID,PARENT_ID,CHILD_INDEX,VIS_ID,DESCRIPTION,CREDIT_DEBIT) values ( ?,?,?,?,?,?,?)", sqle);
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
/* 269 */         stmt = getConnection().prepareStatement("update AUG_HIERARCHY_ELEMENT set HIERARCHY_ID = ?,PARENT_ID = ?,CHILD_INDEX = ?,VIS_ID = ?,DESCRIPTION = ?,CREDIT_DEBIT = ? where    HIERARCHY_ELEMENT_ID = ? ");
/*     */ 
/* 272 */         int col = 1;
/* 273 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 274 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 277 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 280 */         if (resultCount != 1) {
/* 281 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 284 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 293 */       throw handleSQLException(getPK(), "update AUG_HIERARCHY_ELEMENT set HIERARCHY_ID = ?,PARENT_ID = ?,CHILD_INDEX = ?,VIS_ID = ?,DESCRIPTION = ?,CREDIT_DEBIT = ? where    HIERARCHY_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 297 */       closeStatement(stmt);
/* 298 */       closeConnection();
/*     */ 
/* 300 */       if ((timer != null) && (
/* 301 */         (mainChanged) || (dependantChanged)))
/* 302 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public AugHierarachyElementELO getAugHierarachyElement(int param1)
/*     */   {
/* 347 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 348 */     PreparedStatement stmt = null;
/* 349 */     ResultSet resultSet = null;
/* 350 */     AugHierarachyElementELO results = new AugHierarachyElementELO();
/*     */     try
/*     */     {
/* 353 */       stmt = getConnection().prepareStatement(SQL_AUG_HIERARACHY_ELEMENT);
/* 354 */       int col = 1;
/* 355 */       stmt.setInt(col++, param1);
/* 356 */       resultSet = stmt.executeQuery();
/* 357 */       while (resultSet.next())
/*     */       {
/* 359 */         col = 2;
/*     */ 
/* 362 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*     */ 
/* 365 */         String textDimension = resultSet.getString(col++);
/* 366 */         int erDimensionType = resultSet.getInt(col++);
/*     */ 
/* 368 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*     */ 
/* 371 */         String textHierarchy = resultSet.getString(col++);
/*     */ 
/* 374 */         AugHierarchyElementPK pkAugHierarchyElement = new AugHierarchyElementPK(resultSet.getInt(col++));
/*     */ 
/* 377 */         String textAugHierarchyElement = resultSet.getString(col++);
/*     */ 
/* 382 */         HierarchyCK ckHierarchy = new HierarchyCK(pkDimension, pkHierarchy);
/*     */ 
/* 388 */         AugHierarchyElementCK ckAugHierarchyElement = new AugHierarchyElementCK(pkDimension, pkHierarchy, pkAugHierarchyElement);
/*     */ 
/* 395 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*     */ 
/* 402 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(ckHierarchy, textHierarchy);
/*     */ 
/* 408 */         AugHierarchyElementRefImpl erAugHierarchyElement = new AugHierarchyElementRefImpl(ckAugHierarchyElement, textAugHierarchyElement);
/*     */ 
/* 415 */         results.add(erAugHierarchyElement, erHierarchy, erDimension);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 424 */       throw handleSQLException(SQL_AUG_HIERARACHY_ELEMENT, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 428 */       closeResultSet(resultSet);
/* 429 */       closeStatement(stmt);
/* 430 */       closeConnection();
/*     */     }
/*     */ 
/* 433 */     if (timer != null) {
/* 434 */       timer.logDebug("getAugHierarachyElement", " HierarchyElementId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 439 */     return results;
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
/* 470 */         this.mDetails = ((AugHierarchyElementEVO)iter2.next());
/*     */ 
/* 473 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 475 */         somethingChanged = true;
/*     */ 
/* 478 */         if (deleteStmt == null) {
/* 479 */           deleteStmt = getConnection().prepareStatement("delete from AUG_HIERARCHY_ELEMENT where    HIERARCHY_ELEMENT_ID = ? ");
/*     */         }
/*     */ 
/* 482 */         int col = 1;
/* 483 */         deleteStmt.setInt(col++, this.mDetails.getHierarchyElementId());
/*     */ 
/* 485 */         if (this._log.isDebugEnabled()) {
/* 486 */           this._log.debug("update", "AugHierarchyElement deleting HierarchyElementId=" + this.mDetails.getHierarchyElementId());
/*     */         }
/*     */ 
/* 491 */         deleteStmt.addBatch();
/*     */ 
/* 494 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 499 */       if (deleteStmt != null)
/*     */       {
/* 501 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 503 */         deleteStmt.executeBatch();
/*     */ 
/* 505 */         if (timer2 != null) {
/* 506 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 510 */       Iterator iter1 = items.values().iterator();
/* 511 */       while (iter1.hasNext())
/*     */       {
/* 513 */         this.mDetails = ((AugHierarchyElementEVO)iter1.next());
/*     */ 
/* 515 */         if (this.mDetails.insertPending())
/*     */         {
/* 517 */           somethingChanged = true;
/* 518 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 521 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 523 */         somethingChanged = true;
/* 524 */         doStore();
/*     */       }
/*     */ 
/* 535 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 539 */       throw handleSQLException("delete from AUG_HIERARCHY_ELEMENT where    HIERARCHY_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 543 */       if (deleteStmt != null)
/*     */       {
/* 545 */         closeStatement(deleteStmt);
/* 546 */         closeConnection();
/*     */       }
/*     */ 
/* 549 */       this.mDetails = null;
/*     */ 
/* 551 */       if ((somethingChanged) && 
/* 552 */         (timer != null))
/* 553 */         timer.logDebug("update", "collection"); 
/* 553 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(DimensionPK entityPK, Collection owners, String dependants)
/*     */   {
/* 576 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 578 */     PreparedStatement stmt = null;
/* 579 */     ResultSet resultSet = null;
/*     */ 
/* 581 */     int itemCount = 0;
/*     */ 
/* 583 */     HierarchyEVO owningEVO = null;
/* 584 */     Iterator ownersIter = owners.iterator();
/* 585 */     while (ownersIter.hasNext())
/*     */     {
/* 587 */       owningEVO = (HierarchyEVO)ownersIter.next();
/* 588 */       owningEVO.setAugHierarchyElementsAllItemsLoaded(true);
/*     */     }
/* 590 */     ownersIter = owners.iterator();
/* 591 */     owningEVO = (HierarchyEVO)ownersIter.next();
/* 592 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 596 */       stmt = getConnection().prepareStatement("select AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,AUG_HIERARCHY_ELEMENT.HIERARCHY_ID,AUG_HIERARCHY_ELEMENT.PARENT_ID,AUG_HIERARCHY_ELEMENT.CHILD_INDEX,AUG_HIERARCHY_ELEMENT.VIS_ID,AUG_HIERARCHY_ELEMENT.DESCRIPTION,AUG_HIERARCHY_ELEMENT.CREDIT_DEBIT from AUG_HIERARCHY_ELEMENT,HIERARCHY where 1=1 and AUG_HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.DIMENSION_ID = ? order by  AUG_HIERARCHY_ELEMENT.HIERARCHY_ID ,AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID");
/*     */ 
/* 598 */       int col = 1;
/* 599 */       stmt.setInt(col++, entityPK.getDimensionId());
/*     */ 
/* 601 */       resultSet = stmt.executeQuery();
/*     */ 
/* 604 */       while (resultSet.next())
/*     */       {
/* 606 */         itemCount++;
/* 607 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 612 */         while (this.mDetails.getHierarchyId() != owningEVO.getHierarchyId())
/*     */         {
/* 616 */           if (!ownersIter.hasNext())
/*     */           {
/* 618 */             this._log.debug("bulkGetAll", "can't find owning [HierarchyId=" + this.mDetails.getHierarchyId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 622 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 623 */             ownersIter = owners.iterator();
/* 624 */             while (ownersIter.hasNext())
/*     */             {
/* 626 */               owningEVO = (HierarchyEVO)ownersIter.next();
/* 627 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 629 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 631 */           owningEVO = (HierarchyEVO)ownersIter.next();
/*     */         }
/* 633 */         if (owningEVO.getAugHierarchyElements() == null)
/*     */         {
/* 635 */           theseItems = new ArrayList();
/* 636 */           owningEVO.setAugHierarchyElements(theseItems);
/* 637 */           owningEVO.setAugHierarchyElementsAllItemsLoaded(true);
/*     */         }
/* 639 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 642 */       if (timer != null) {
/* 643 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 648 */       throw handleSQLException("select AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,AUG_HIERARCHY_ELEMENT.HIERARCHY_ID,AUG_HIERARCHY_ELEMENT.PARENT_ID,AUG_HIERARCHY_ELEMENT.CHILD_INDEX,AUG_HIERARCHY_ELEMENT.VIS_ID,AUG_HIERARCHY_ELEMENT.DESCRIPTION,AUG_HIERARCHY_ELEMENT.CREDIT_DEBIT from AUG_HIERARCHY_ELEMENT,HIERARCHY where 1=1 and AUG_HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.DIMENSION_ID = ? order by  AUG_HIERARCHY_ELEMENT.HIERARCHY_ID ,AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 652 */       closeResultSet(resultSet);
/* 653 */       closeStatement(stmt);
/* 654 */       closeConnection();
/*     */ 
/* 656 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectHierarchyId, String dependants, Collection currentList)
/*     */   {
/* 681 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 682 */     PreparedStatement stmt = null;
/* 683 */     ResultSet resultSet = null;
/*     */ 
/* 685 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 689 */       stmt = getConnection().prepareStatement("select AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,AUG_HIERARCHY_ELEMENT.HIERARCHY_ID,AUG_HIERARCHY_ELEMENT.PARENT_ID,AUG_HIERARCHY_ELEMENT.CHILD_INDEX,AUG_HIERARCHY_ELEMENT.VIS_ID,AUG_HIERARCHY_ELEMENT.DESCRIPTION,AUG_HIERARCHY_ELEMENT.CREDIT_DEBIT from AUG_HIERARCHY_ELEMENT where    HIERARCHY_ID = ? ");
/*     */ 
/* 691 */       int col = 1;
/* 692 */       stmt.setInt(col++, selectHierarchyId);
/*     */ 
/* 694 */       resultSet = stmt.executeQuery();
/*     */ 
/* 696 */       while (resultSet.next())
/*     */       {
/* 698 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 701 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 704 */       if (currentList != null)
/*     */       {
/* 707 */         ListIterator iter = items.listIterator();
/* 708 */         AugHierarchyElementEVO currentEVO = null;
/* 709 */         AugHierarchyElementEVO newEVO = null;
/* 710 */         while (iter.hasNext())
/*     */         {
/* 712 */           newEVO = (AugHierarchyElementEVO)iter.next();
/* 713 */           Iterator iter2 = currentList.iterator();
/* 714 */           while (iter2.hasNext())
/*     */           {
/* 716 */             currentEVO = (AugHierarchyElementEVO)iter2.next();
/* 717 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 719 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 725 */         Iterator iter2 = currentList.iterator();
/* 726 */         while (iter2.hasNext())
/*     */         {
/* 728 */           currentEVO = (AugHierarchyElementEVO)iter2.next();
/* 729 */           if (currentEVO.insertPending()) {
/* 730 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 734 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 738 */       throw handleSQLException("select AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,AUG_HIERARCHY_ELEMENT.HIERARCHY_ID,AUG_HIERARCHY_ELEMENT.PARENT_ID,AUG_HIERARCHY_ELEMENT.CHILD_INDEX,AUG_HIERARCHY_ELEMENT.VIS_ID,AUG_HIERARCHY_ELEMENT.DESCRIPTION,AUG_HIERARCHY_ELEMENT.CREDIT_DEBIT from AUG_HIERARCHY_ELEMENT where    HIERARCHY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 742 */       closeResultSet(resultSet);
/* 743 */       closeStatement(stmt);
/* 744 */       closeConnection();
/*     */ 
/* 746 */       if (timer != null) {
/* 747 */         timer.logDebug("getAll", " HierarchyId=" + selectHierarchyId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 752 */     return items;
/*     */   }
/*     */ 
/*     */   public AugHierarchyElementEVO getDetails(DimensionCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 766 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 769 */     if (this.mDetails == null) {
/* 770 */       doLoad(((AugHierarchyElementCK)paramCK).getAugHierarchyElementPK());
/*     */     }
/* 772 */     else if (!this.mDetails.getPK().equals(((AugHierarchyElementCK)paramCK).getAugHierarchyElementPK())) {
/* 773 */       doLoad(((AugHierarchyElementCK)paramCK).getAugHierarchyElementPK());
/*     */     }
/*     */ 
/* 776 */     AugHierarchyElementEVO details = new AugHierarchyElementEVO();
/* 777 */     details = this.mDetails.deepClone();
/*     */ 
/* 779 */     if (timer != null) {
/* 780 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 782 */     return details;
/*     */   }
/*     */ 
/*     */   public AugHierarchyElementEVO getDetails(DimensionCK paramCK, AugHierarchyElementEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 788 */     AugHierarchyElementEVO savedEVO = this.mDetails;
/* 789 */     this.mDetails = paramEVO;
/* 790 */     AugHierarchyElementEVO newEVO = getDetails(paramCK, dependants);
/* 791 */     this.mDetails = savedEVO;
/* 792 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public AugHierarchyElementEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 798 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 802 */     AugHierarchyElementEVO details = this.mDetails.deepClone();
/*     */ 
/* 804 */     if (timer != null) {
/* 805 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 807 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 812 */     return "AugHierarchyElement";
/*     */   }
/*     */ 
/*     */   public AugHierarchyElementRefImpl getRef(AugHierarchyElementPK paramAugHierarchyElementPK)
/*     */   {
/* 817 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 818 */     PreparedStatement stmt = null;
/* 819 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 822 */       stmt = getConnection().prepareStatement("select 0,DIMENSION.DIMENSION_ID,HIERARCHY.HIERARCHY_ID,AUG_HIERARCHY_ELEMENT.VIS_ID from AUG_HIERARCHY_ELEMENT,DIMENSION,HIERARCHY where 1=1 and AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID = ? and AUG_HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.HIERARCHY_ID = DIMENSION.HIERARCHY_ID");
/* 823 */       int col = 1;
/* 824 */       stmt.setInt(col++, paramAugHierarchyElementPK.getHierarchyElementId());
/*     */ 
/* 826 */       resultSet = stmt.executeQuery();
/*     */ 
/* 828 */       if (!resultSet.next()) {
/* 829 */         throw new RuntimeException(getEntityName() + " getRef " + paramAugHierarchyElementPK + " not found");
/*     */       }
/* 831 */       col = 2;
/* 832 */       DimensionPK newDimensionPK = new DimensionPK(resultSet.getInt(col++));
/*     */ 
/* 836 */       HierarchyPK newHierarchyPK = new HierarchyPK(resultSet.getInt(col++));
/*     */ 
/* 840 */       String textAugHierarchyElement = resultSet.getString(col++);
/* 841 */       AugHierarchyElementCK ckAugHierarchyElement = new AugHierarchyElementCK(newDimensionPK, newHierarchyPK, paramAugHierarchyElementPK);
/*     */ 
/* 847 */       AugHierarchyElementRefImpl localAugHierarchyElementRefImpl = new AugHierarchyElementRefImpl(ckAugHierarchyElement, textAugHierarchyElement);
/*     */       return localAugHierarchyElementRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 852 */       throw handleSQLException(paramAugHierarchyElementPK, "select 0,DIMENSION.DIMENSION_ID,HIERARCHY.HIERARCHY_ID,AUG_HIERARCHY_ELEMENT.VIS_ID from AUG_HIERARCHY_ELEMENT,DIMENSION,HIERARCHY where 1=1 and AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID = ? and AUG_HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.HIERARCHY_ID = DIMENSION.HIERARCHY_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 856 */       closeResultSet(resultSet);
/* 857 */       closeStatement(stmt);
/* 858 */       closeConnection();
/*     */ 
/* 860 */       if (timer != null)
/* 861 */         timer.logDebug("getRef", paramAugHierarchyElementPK); 
/* 861 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.dimension.AugHierarchyElementDAO
 * JD-Core Version:    0.6.0
 */