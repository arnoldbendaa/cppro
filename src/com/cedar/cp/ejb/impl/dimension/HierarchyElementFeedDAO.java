/*     */ package com.cedar.cp.ejb.impl.dimension;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.dimension.AllFeedsForDimensionElementELO;
/*     */ import com.cedar.cp.dto.dimension.DimensionCK;
/*     */ import com.cedar.cp.dto.dimension.DimensionPK;
/*     */ import com.cedar.cp.dto.dimension.DimensionRefImpl;
/*     */ import com.cedar.cp.dto.dimension.HierarchyCK;
/*     */ import com.cedar.cp.dto.dimension.HierarchyElementCK;
/*     */ import com.cedar.cp.dto.dimension.HierarchyElementFeedCK;
/*     */ import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
/*     */ import com.cedar.cp.dto.dimension.HierarchyElementFeedRefImpl;
/*     */ import com.cedar.cp.dto.dimension.HierarchyElementPK;
/*     */ import com.cedar.cp.dto.dimension.HierarchyElementRefImpl;
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
/*     */ public class HierarchyElementFeedDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.CHILD_INDEX,HIERARCHY_ELEMENT_FEED.AUG_HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.AUG_CHILD_INDEX,HIERARCHY_ELEMENT_FEED.CAL_ELEM_TYPE";
/*     */   protected static final String SQL_LOAD = " from HIERARCHY_ELEMENT_FEED where    HIERARCHY_ELEMENT_ID = ? AND DIMENSION_ELEMENT_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into HIERARCHY_ELEMENT_FEED ( HIERARCHY_ELEMENT_ID,DIMENSION_ELEMENT_ID,CHILD_INDEX,AUG_HIERARCHY_ELEMENT_ID,AUG_CHILD_INDEX,CAL_ELEM_TYPE) values ( ?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update HIERARCHY_ELEMENT_FEED set CHILD_INDEX = ?,AUG_HIERARCHY_ELEMENT_ID = ?,AUG_CHILD_INDEX = ?,CAL_ELEM_TYPE = ? where    HIERARCHY_ELEMENT_ID = ? AND DIMENSION_ELEMENT_ID = ? ";
/* 306 */   protected static String SQL_ALL_FEEDS_FOR_DIMENSION_ELEMENT = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID      ,HIERARCHY_ELEMENT.VIS_ID      ,HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID      ,HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID from HIERARCHY_ELEMENT_FEED    ,DIMENSION    ,HIERARCHY    ,HIERARCHY_ELEMENT where 1=1   and HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID = HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID   and HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID   and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  DIMENSION_ELEMENT_ID = ?";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from HIERARCHY_ELEMENT_FEED where    HIERARCHY_ELEMENT_ID = ? AND DIMENSION_ELEMENT_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from HIERARCHY_ELEMENT_FEED,HIERARCHY_ELEMENT,HIERARCHY where 1=1 and HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID = HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID and HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.DIMENSION_ID = ? order by  HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID ,HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID ,HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID";
/*     */   protected static final String SQL_GET_ALL = " from HIERARCHY_ELEMENT_FEED where    HIERARCHY_ELEMENT_ID = ? ";
/*     */   protected HierarchyElementFeedEVO mDetails;
/*     */ 
/*     */   public HierarchyElementFeedDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public HierarchyElementFeedDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HierarchyElementFeedDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected HierarchyElementFeedPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(HierarchyElementFeedEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private HierarchyElementFeedEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  89 */     int col = 1;
/*  90 */     HierarchyElementFeedEVO evo = new HierarchyElementFeedEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++));
/*     */ 
/*  99 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(HierarchyElementFeedEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 104 */     int col = startCol_;
/* 105 */     stmt_.setInt(col++, evo_.getHierarchyElementId());
/* 106 */     stmt_.setInt(col++, evo_.getDimensionElementId());
/* 107 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(HierarchyElementFeedEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 112 */     int col = startCol_;
/* 113 */     stmt_.setInt(col++, evo_.getChildIndex());
/* 114 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getAugHierarchyElementId());
/* 115 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getAugChildIndex());
/* 116 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getCalElemType());
/* 117 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(HierarchyElementFeedPK pk)
/*     */     throws ValidationException
/*     */   {
/* 134 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 136 */     PreparedStatement stmt = null;
/* 137 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 141 */       stmt = getConnection().prepareStatement("select HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.CHILD_INDEX,HIERARCHY_ELEMENT_FEED.AUG_HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.AUG_CHILD_INDEX,HIERARCHY_ELEMENT_FEED.CAL_ELEM_TYPE from HIERARCHY_ELEMENT_FEED where    HIERARCHY_ELEMENT_ID = ? AND DIMENSION_ELEMENT_ID = ? ");
/*     */ 
/* 144 */       int col = 1;
/* 145 */       stmt.setInt(col++, pk.getHierarchyElementId());
/* 146 */       stmt.setInt(col++, pk.getDimensionElementId());
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
/* 160 */       throw handleSQLException(pk, "select HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.CHILD_INDEX,HIERARCHY_ELEMENT_FEED.AUG_HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.AUG_CHILD_INDEX,HIERARCHY_ELEMENT_FEED.CAL_ELEM_TYPE from HIERARCHY_ELEMENT_FEED where    HIERARCHY_ELEMENT_ID = ? AND DIMENSION_ELEMENT_ID = ? ", sqle);
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
/* 203 */       stmt = getConnection().prepareStatement("insert into HIERARCHY_ELEMENT_FEED ( HIERARCHY_ELEMENT_ID,DIMENSION_ELEMENT_ID,CHILD_INDEX,AUG_HIERARCHY_ELEMENT_ID,AUG_CHILD_INDEX,CAL_ELEM_TYPE) values ( ?,?,?,?,?,?)");
/*     */ 
/* 206 */       int col = 1;
/* 207 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 208 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 211 */       int resultCount = stmt.executeUpdate();
/* 212 */       if (resultCount != 1)
/*     */       {
/* 214 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 217 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 221 */       throw handleSQLException(this.mDetails.getPK(), "insert into HIERARCHY_ELEMENT_FEED ( HIERARCHY_ELEMENT_ID,DIMENSION_ELEMENT_ID,CHILD_INDEX,AUG_HIERARCHY_ELEMENT_ID,AUG_CHILD_INDEX,CAL_ELEM_TYPE) values ( ?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 225 */       closeStatement(stmt);
/* 226 */       closeConnection();
/*     */ 
/* 228 */       if (timer != null)
/* 229 */         timer.logDebug("doCreate", this.mDetails.toString());
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
/* 265 */         stmt = getConnection().prepareStatement("update HIERARCHY_ELEMENT_FEED set CHILD_INDEX = ?,AUG_HIERARCHY_ELEMENT_ID = ?,AUG_CHILD_INDEX = ?,CAL_ELEM_TYPE = ? where    HIERARCHY_ELEMENT_ID = ? AND DIMENSION_ELEMENT_ID = ? ");
/*     */ 
/* 268 */         int col = 1;
/* 269 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 270 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 273 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 276 */         if (resultCount != 1) {
/* 277 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 280 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 289 */       throw handleSQLException(getPK(), "update HIERARCHY_ELEMENT_FEED set CHILD_INDEX = ?,AUG_HIERARCHY_ELEMENT_ID = ?,AUG_CHILD_INDEX = ?,CAL_ELEM_TYPE = ? where    HIERARCHY_ELEMENT_ID = ? AND DIMENSION_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 293 */       closeStatement(stmt);
/* 294 */       closeConnection();
/*     */ 
/* 296 */       if ((timer != null) && (
/* 297 */         (mainChanged) || (dependantChanged)))
/* 298 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllFeedsForDimensionElementELO getAllFeedsForDimensionElement(int param1)
/*     */   {
/* 349 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 350 */     PreparedStatement stmt = null;
/* 351 */     ResultSet resultSet = null;
/* 352 */     AllFeedsForDimensionElementELO results = new AllFeedsForDimensionElementELO();
/*     */     try
/*     */     {
/* 355 */       stmt = getConnection().prepareStatement(SQL_ALL_FEEDS_FOR_DIMENSION_ELEMENT);
/* 356 */       int col = 1;
/* 357 */       stmt.setInt(col++, param1);
/* 358 */       resultSet = stmt.executeQuery();
/* 359 */       while (resultSet.next())
/*     */       {
/* 361 */         col = 2;
/*     */ 
/* 364 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*     */ 
/* 367 */         String textDimension = resultSet.getString(col++);
/* 368 */         int erDimensionType = resultSet.getInt(col++);
/*     */ 
/* 370 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*     */ 
/* 373 */         String textHierarchy = resultSet.getString(col++);
/*     */ 
/* 375 */         HierarchyElementPK pkHierarchyElement = new HierarchyElementPK(resultSet.getInt(col++));
/*     */ 
/* 378 */         String textHierarchyElement = resultSet.getString(col++);
/*     */ 
/* 381 */         HierarchyElementFeedPK pkHierarchyElementFeed = new HierarchyElementFeedPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 385 */         String textHierarchyElementFeed = "";
/*     */ 
/* 390 */         HierarchyCK ckHierarchy = new HierarchyCK(pkDimension, pkHierarchy);
/*     */ 
/* 396 */         HierarchyElementCK ckHierarchyElement = new HierarchyElementCK(pkDimension, pkHierarchy, pkHierarchyElement);
/*     */ 
/* 403 */         HierarchyElementFeedCK ckHierarchyElementFeed = new HierarchyElementFeedCK(pkDimension, pkHierarchy, pkHierarchyElement, pkHierarchyElementFeed);
/*     */ 
/* 411 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*     */ 
/* 418 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(ckHierarchy, textHierarchy);
/*     */ 
/* 424 */         HierarchyElementRefImpl erHierarchyElement = new HierarchyElementRefImpl(ckHierarchyElement, textHierarchyElement);
/*     */ 
/* 430 */         HierarchyElementFeedRefImpl erHierarchyElementFeed = new HierarchyElementFeedRefImpl(ckHierarchyElementFeed, textHierarchyElementFeed);
/*     */ 
/* 437 */         results.add(erHierarchyElementFeed, erHierarchyElement, erHierarchy, erDimension);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 447 */       throw handleSQLException(SQL_ALL_FEEDS_FOR_DIMENSION_ELEMENT, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 451 */       closeResultSet(resultSet);
/* 452 */       closeStatement(stmt);
/* 453 */       closeConnection();
/*     */     }
/*     */ 
/* 456 */     if (timer != null) {
/* 457 */       timer.logDebug("getAllFeedsForDimensionElement", " DimensionElementId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 462 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 480 */     if (items == null) {
/* 481 */       return false;
/*     */     }
/* 483 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 484 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 486 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 491 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 492 */       while (iter2.hasNext())
/*     */       {
/* 494 */         this.mDetails = ((HierarchyElementFeedEVO)iter2.next());
/*     */ 
/* 497 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 499 */         somethingChanged = true;
/*     */ 
/* 502 */         if (deleteStmt == null) {
/* 503 */           deleteStmt = getConnection().prepareStatement("delete from HIERARCHY_ELEMENT_FEED where    HIERARCHY_ELEMENT_ID = ? AND DIMENSION_ELEMENT_ID = ? ");
/*     */         }
/*     */ 
/* 506 */         int col = 1;
/* 507 */         deleteStmt.setInt(col++, this.mDetails.getHierarchyElementId());
/* 508 */         deleteStmt.setInt(col++, this.mDetails.getDimensionElementId());
/*     */ 
/* 510 */         if (this._log.isDebugEnabled()) {
/* 511 */           this._log.debug("update", "HierarchyElementFeed deleting HierarchyElementId=" + this.mDetails.getHierarchyElementId() + ",DimensionElementId=" + this.mDetails.getDimensionElementId());
/*     */         }
/*     */ 
/* 517 */         deleteStmt.addBatch();
/*     */ 
/* 520 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 525 */       if (deleteStmt != null)
/*     */       {
/* 527 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 529 */         deleteStmt.executeBatch();
/*     */ 
/* 531 */         if (timer2 != null) {
/* 532 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 536 */       Iterator iter1 = items.values().iterator();
/* 537 */       while (iter1.hasNext())
/*     */       {
/* 539 */         this.mDetails = ((HierarchyElementFeedEVO)iter1.next());
/*     */ 
/* 541 */         if (this.mDetails.insertPending())
/*     */         {
/* 543 */           somethingChanged = true;
/* 544 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 547 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 549 */         somethingChanged = true;
/* 550 */         doStore();
/*     */       }
/*     */ 
/* 561 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 565 */       throw handleSQLException("delete from HIERARCHY_ELEMENT_FEED where    HIERARCHY_ELEMENT_ID = ? AND DIMENSION_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 569 */       if (deleteStmt != null)
/*     */       {
/* 571 */         closeStatement(deleteStmt);
/* 572 */         closeConnection();
/*     */       }
/*     */ 
/* 575 */       this.mDetails = null;
/*     */ 
/* 577 */       if ((somethingChanged) && 
/* 578 */         (timer != null))
/* 579 */         timer.logDebug("update", "collection"); 
/* 579 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(DimensionPK entityPK, Collection owners, String dependants)
/*     */   {
/* 606 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 608 */     PreparedStatement stmt = null;
/* 609 */     ResultSet resultSet = null;
/*     */ 
/* 611 */     int itemCount = 0;
/*     */ 
/* 613 */     HierarchyElementEVO owningEVO = null;
/* 614 */     Iterator ownersIter = owners.iterator();
/* 615 */     while (ownersIter.hasNext())
/*     */     {
/* 617 */       owningEVO = (HierarchyElementEVO)ownersIter.next();
/* 618 */       owningEVO.setFeederElementsAllItemsLoaded(true);
/*     */     }
/* 620 */     ownersIter = owners.iterator();
/* 621 */     owningEVO = (HierarchyElementEVO)ownersIter.next();
/* 622 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 626 */       stmt = getConnection().prepareStatement("select HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.CHILD_INDEX,HIERARCHY_ELEMENT_FEED.AUG_HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.AUG_CHILD_INDEX,HIERARCHY_ELEMENT_FEED.CAL_ELEM_TYPE from HIERARCHY_ELEMENT_FEED,HIERARCHY_ELEMENT,HIERARCHY where 1=1 and HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID = HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID and HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.DIMENSION_ID = ? order by  HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID ,HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID ,HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID");
/*     */ 
/* 628 */       int col = 1;
/* 629 */       stmt.setInt(col++, entityPK.getDimensionId());
/*     */ 
/* 631 */       resultSet = stmt.executeQuery();
/*     */ 
/* 634 */       while (resultSet.next())
/*     */       {
/* 636 */         itemCount++;
/* 637 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 642 */         while (this.mDetails.getHierarchyElementId() != owningEVO.getHierarchyElementId())
/*     */         {
/* 646 */           if (!ownersIter.hasNext())
/*     */           {
/* 648 */             this._log.debug("bulkGetAll", "can't find owning [HierarchyElementId=" + this.mDetails.getHierarchyElementId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 652 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 653 */             ownersIter = owners.iterator();
/* 654 */             while (ownersIter.hasNext())
/*     */             {
/* 656 */               owningEVO = (HierarchyElementEVO)ownersIter.next();
/* 657 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 659 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 661 */           owningEVO = (HierarchyElementEVO)ownersIter.next();
/*     */         }
/* 663 */         if (owningEVO.getFeederElements() == null)
/*     */         {
/* 665 */           theseItems = new ArrayList();
/* 666 */           owningEVO.setFeederElements(theseItems);
/* 667 */           owningEVO.setFeederElementsAllItemsLoaded(true);
/*     */         }
/* 669 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 672 */       if (timer != null) {
/* 673 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 678 */       throw handleSQLException("select HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.CHILD_INDEX,HIERARCHY_ELEMENT_FEED.AUG_HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.AUG_CHILD_INDEX,HIERARCHY_ELEMENT_FEED.CAL_ELEM_TYPE from HIERARCHY_ELEMENT_FEED,HIERARCHY_ELEMENT,HIERARCHY where 1=1 and HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID = HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID and HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.DIMENSION_ID = ? order by  HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID ,HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID ,HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 682 */       closeResultSet(resultSet);
/* 683 */       closeStatement(stmt);
/* 684 */       closeConnection();
/*     */ 
/* 686 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectHierarchyElementId, String dependants, Collection currentList)
/*     */   {
/* 711 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 712 */     PreparedStatement stmt = null;
/* 713 */     ResultSet resultSet = null;
/*     */ 
/* 715 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 719 */       stmt = getConnection().prepareStatement("select HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.CHILD_INDEX,HIERARCHY_ELEMENT_FEED.AUG_HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.AUG_CHILD_INDEX,HIERARCHY_ELEMENT_FEED.CAL_ELEM_TYPE from HIERARCHY_ELEMENT_FEED where    HIERARCHY_ELEMENT_ID = ? ");
/*     */ 
/* 721 */       int col = 1;
/* 722 */       stmt.setInt(col++, selectHierarchyElementId);
/*     */ 
/* 724 */       resultSet = stmt.executeQuery();
/*     */ 
/* 726 */       while (resultSet.next())
/*     */       {
/* 728 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 731 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 734 */       if (currentList != null)
/*     */       {
/* 737 */         ListIterator iter = items.listIterator();
/* 738 */         HierarchyElementFeedEVO currentEVO = null;
/* 739 */         HierarchyElementFeedEVO newEVO = null;
/* 740 */         while (iter.hasNext())
/*     */         {
/* 742 */           newEVO = (HierarchyElementFeedEVO)iter.next();
/* 743 */           Iterator iter2 = currentList.iterator();
/* 744 */           while (iter2.hasNext())
/*     */           {
/* 746 */             currentEVO = (HierarchyElementFeedEVO)iter2.next();
/* 747 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 749 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 755 */         Iterator iter2 = currentList.iterator();
/* 756 */         while (iter2.hasNext())
/*     */         {
/* 758 */           currentEVO = (HierarchyElementFeedEVO)iter2.next();
/* 759 */           if (currentEVO.insertPending()) {
/* 760 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 764 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 768 */       throw handleSQLException("select HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.CHILD_INDEX,HIERARCHY_ELEMENT_FEED.AUG_HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT_FEED.AUG_CHILD_INDEX,HIERARCHY_ELEMENT_FEED.CAL_ELEM_TYPE from HIERARCHY_ELEMENT_FEED where    HIERARCHY_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 772 */       closeResultSet(resultSet);
/* 773 */       closeStatement(stmt);
/* 774 */       closeConnection();
/*     */ 
/* 776 */       if (timer != null) {
/* 777 */         timer.logDebug("getAll", " HierarchyElementId=" + selectHierarchyElementId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 782 */     return items;
/*     */   }
/*     */ 
/*     */   public HierarchyElementFeedEVO getDetails(DimensionCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 796 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 799 */     if (this.mDetails == null) {
/* 800 */       doLoad(((HierarchyElementFeedCK)paramCK).getHierarchyElementFeedPK());
/*     */     }
/* 802 */     else if (!this.mDetails.getPK().equals(((HierarchyElementFeedCK)paramCK).getHierarchyElementFeedPK())) {
/* 803 */       doLoad(((HierarchyElementFeedCK)paramCK).getHierarchyElementFeedPK());
/*     */     }
/*     */ 
/* 806 */     HierarchyElementFeedEVO details = new HierarchyElementFeedEVO();
/* 807 */     details = this.mDetails.deepClone();
/*     */ 
/* 809 */     if (timer != null) {
/* 810 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 812 */     return details;
/*     */   }
/*     */ 
/*     */   public HierarchyElementFeedEVO getDetails(DimensionCK paramCK, HierarchyElementFeedEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 818 */     HierarchyElementFeedEVO savedEVO = this.mDetails;
/* 819 */     this.mDetails = paramEVO;
/* 820 */     HierarchyElementFeedEVO newEVO = getDetails(paramCK, dependants);
/* 821 */     this.mDetails = savedEVO;
/* 822 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public HierarchyElementFeedEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 828 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 832 */     HierarchyElementFeedEVO details = this.mDetails.deepClone();
/*     */ 
/* 834 */     if (timer != null) {
/* 835 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 837 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 842 */     return "HierarchyElementFeed";
/*     */   }
/*     */ 
/*     */   public HierarchyElementFeedRefImpl getRef(HierarchyElementFeedPK paramHierarchyElementFeedPK)
/*     */   {
/* 847 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 848 */     PreparedStatement stmt = null;
/* 849 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 852 */       stmt = getConnection().prepareStatement("select 0,DIMENSION.DIMENSION_ID,HIERARCHY.HIERARCHY_ID,HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID from HIERARCHY_ELEMENT_FEED,DIMENSION,HIERARCHY,HIERARCHY_ELEMENT where 1=1 and HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID = ? and HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID = ? and HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID = HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID and HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID = HIERARCHY.HIERARCHY_ELEMENT_ID and HIERARCHY.HIERARCHY_ID = DIMENSION.HIERARCHY_ID");
/* 853 */       int col = 1;
/* 854 */       stmt.setInt(col++, paramHierarchyElementFeedPK.getHierarchyElementId());
/* 855 */       stmt.setInt(col++, paramHierarchyElementFeedPK.getDimensionElementId());
/*     */ 
/* 857 */       resultSet = stmt.executeQuery();
/*     */ 
/* 859 */       if (!resultSet.next()) {
/* 860 */         throw new RuntimeException(getEntityName() + " getRef " + paramHierarchyElementFeedPK + " not found");
/*     */       }
/* 862 */       col = 2;
/* 863 */       DimensionPK newDimensionPK = new DimensionPK(resultSet.getInt(col++));
/*     */ 
/* 867 */       HierarchyPK newHierarchyPK = new HierarchyPK(resultSet.getInt(col++));
/*     */ 
/* 871 */       HierarchyElementPK newHierarchyElementPK = new HierarchyElementPK(resultSet.getInt(col++));
/*     */ 
/* 875 */       String textHierarchyElementFeed = "";
/* 876 */       HierarchyElementFeedCK ckHierarchyElementFeed = new HierarchyElementFeedCK(newDimensionPK, newHierarchyPK, newHierarchyElementPK, paramHierarchyElementFeedPK);
/*     */ 
/* 883 */       HierarchyElementFeedRefImpl localHierarchyElementFeedRefImpl = new HierarchyElementFeedRefImpl(ckHierarchyElementFeed, textHierarchyElementFeed);
/*     */       return localHierarchyElementFeedRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 888 */       throw handleSQLException(paramHierarchyElementFeedPK, "select 0,DIMENSION.DIMENSION_ID,HIERARCHY.HIERARCHY_ID,HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID from HIERARCHY_ELEMENT_FEED,DIMENSION,HIERARCHY,HIERARCHY_ELEMENT where 1=1 and HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID = ? and HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID = ? and HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID = HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID and HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID = HIERARCHY.HIERARCHY_ELEMENT_ID and HIERARCHY.HIERARCHY_ID = DIMENSION.HIERARCHY_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 892 */       closeResultSet(resultSet);
/* 893 */       closeStatement(stmt);
/* 894 */       closeConnection();
/*     */ 
/* 896 */       if (timer != null)
/* 897 */         timer.logDebug("getRef", paramHierarchyElementFeedPK); 
/* 897 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAO
 * JD-Core Version:    0.6.0
 */