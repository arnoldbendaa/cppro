/*     */ package com.cedar.cp.ejb.impl.model.act;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.act.BudgetActivityLinkCK;
/*     */ import com.cedar.cp.dto.model.act.BudgetActivityLinkPK;
/*     */ import com.cedar.cp.dto.model.act.BudgetActivityLinkRefImpl;
/*     */ import com.cedar.cp.dto.model.act.BudgetActivityPK;
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
/*     */ public class BudgetActivityLinkDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID,BUDGET_ACTIVITY_LINK.BUDGET_CYCLE_ID";
/*     */   protected static final String SQL_LOAD = " from BUDGET_ACTIVITY_LINK where    BUDGET_ACTIVITY_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into BUDGET_ACTIVITY_LINK ( BUDGET_ACTIVITY_ID,STRUCTURE_ELEMENT_ID,BUDGET_CYCLE_ID) values ( ?,?,?)";
/*     */   protected static final String SQL_STORE = "update BUDGET_ACTIVITY_LINK set BUDGET_CYCLE_ID = ? where    BUDGET_ACTIVITY_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from BUDGET_ACTIVITY_LINK where    BUDGET_ACTIVITY_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from BUDGET_ACTIVITY_LINK,BUDGET_ACTIVITY where 1=1 and BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID = BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID and BUDGET_ACTIVITY.MODEL_ID = ? order by  BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID ,BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID ,BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID";
/*     */   protected static final String SQL_GET_ALL = " from BUDGET_ACTIVITY_LINK where    BUDGET_ACTIVITY_ID = ? ";
/*     */   protected BudgetActivityLinkEVO mDetails;
/*     */ 
/*     */   public BudgetActivityLinkDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public BudgetActivityLinkDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BudgetActivityLinkDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected BudgetActivityLinkPK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(BudgetActivityLinkEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private BudgetActivityLinkEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  89 */     int col = 1;
/*  90 */     BudgetActivityLinkEVO evo = new BudgetActivityLinkEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++));
/*     */ 
/*  96 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(BudgetActivityLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 101 */     int col = startCol_;
/* 102 */     stmt_.setInt(col++, evo_.getBudgetActivityId());
/* 103 */     stmt_.setInt(col++, evo_.getStructureElementId());
/* 104 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(BudgetActivityLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 109 */     int col = startCol_;
/* 110 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getBudgetCycleId());
/* 111 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(BudgetActivityLinkPK pk)
/*     */     throws ValidationException
/*     */   {
/* 128 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 130 */     PreparedStatement stmt = null;
/* 131 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 135 */       stmt = getConnection().prepareStatement("select BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID,BUDGET_ACTIVITY_LINK.BUDGET_CYCLE_ID from BUDGET_ACTIVITY_LINK where    BUDGET_ACTIVITY_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*     */ 
/* 138 */       int col = 1;
/* 139 */       stmt.setInt(col++, pk.getBudgetActivityId());
/* 140 */       stmt.setInt(col++, pk.getStructureElementId());
/*     */ 
/* 142 */       resultSet = stmt.executeQuery();
/*     */ 
/* 144 */       if (!resultSet.next()) {
/* 145 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 148 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 149 */       if (this.mDetails.isModified())
/* 150 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 154 */       throw handleSQLException(pk, "select BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID,BUDGET_ACTIVITY_LINK.BUDGET_CYCLE_ID from BUDGET_ACTIVITY_LINK where    BUDGET_ACTIVITY_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 158 */       closeResultSet(resultSet);
/* 159 */       closeStatement(stmt);
/* 160 */       closeConnection();
/*     */ 
/* 162 */       if (timer != null)
/* 163 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 186 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 187 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 191 */       stmt = getConnection().prepareStatement("insert into BUDGET_ACTIVITY_LINK ( BUDGET_ACTIVITY_ID,STRUCTURE_ELEMENT_ID,BUDGET_CYCLE_ID) values ( ?,?,?)");
/*     */ 
/* 194 */       int col = 1;
/* 195 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 196 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 199 */       int resultCount = stmt.executeUpdate();
/* 200 */       if (resultCount != 1)
/*     */       {
/* 202 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 205 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 209 */       throw handleSQLException(this.mDetails.getPK(), "insert into BUDGET_ACTIVITY_LINK ( BUDGET_ACTIVITY_ID,STRUCTURE_ELEMENT_ID,BUDGET_CYCLE_ID) values ( ?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 213 */       closeStatement(stmt);
/* 214 */       closeConnection();
/*     */ 
/* 216 */       if (timer != null)
/* 217 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 238 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 242 */     PreparedStatement stmt = null;
/*     */ 
/* 244 */     boolean mainChanged = this.mDetails.isModified();
/* 245 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 248 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 250 */         stmt = getConnection().prepareStatement("update BUDGET_ACTIVITY_LINK set BUDGET_CYCLE_ID = ? where    BUDGET_ACTIVITY_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*     */ 
/* 253 */         int col = 1;
/* 254 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 255 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 258 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 261 */         if (resultCount != 1) {
/* 262 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 265 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 274 */       throw handleSQLException(getPK(), "update BUDGET_ACTIVITY_LINK set BUDGET_CYCLE_ID = ? where    BUDGET_ACTIVITY_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 278 */       closeStatement(stmt);
/* 279 */       closeConnection();
/*     */ 
/* 281 */       if ((timer != null) && (
/* 282 */         (mainChanged) || (dependantChanged)))
/* 283 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 303 */     if (items == null) {
/* 304 */       return false;
/*     */     }
/* 306 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 307 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 309 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 314 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 315 */       while (iter2.hasNext())
/*     */       {
/* 317 */         this.mDetails = ((BudgetActivityLinkEVO)iter2.next());
/*     */ 
/* 320 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 322 */         somethingChanged = true;
/*     */ 
/* 325 */         if (deleteStmt == null) {
/* 326 */           deleteStmt = getConnection().prepareStatement("delete from BUDGET_ACTIVITY_LINK where    BUDGET_ACTIVITY_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*     */         }
/*     */ 
/* 329 */         int col = 1;
/* 330 */         deleteStmt.setInt(col++, this.mDetails.getBudgetActivityId());
/* 331 */         deleteStmt.setInt(col++, this.mDetails.getStructureElementId());
/*     */ 
/* 333 */         if (this._log.isDebugEnabled()) {
/* 334 */           this._log.debug("update", "BudgetActivityLink deleting BudgetActivityId=" + this.mDetails.getBudgetActivityId() + ",StructureElementId=" + this.mDetails.getStructureElementId());
/*     */         }
/*     */ 
/* 340 */         deleteStmt.addBatch();
/*     */ 
/* 343 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 348 */       if (deleteStmt != null)
/*     */       {
/* 350 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 352 */         deleteStmt.executeBatch();
/*     */ 
/* 354 */         if (timer2 != null) {
/* 355 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 359 */       Iterator iter1 = items.values().iterator();
/* 360 */       while (iter1.hasNext())
/*     */       {
/* 362 */         this.mDetails = ((BudgetActivityLinkEVO)iter1.next());
/*     */ 
/* 364 */         if (this.mDetails.insertPending())
/*     */         {
/* 366 */           somethingChanged = true;
/* 367 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 370 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 372 */         somethingChanged = true;
/* 373 */         doStore();
/*     */       }
/*     */ 
/* 384 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 388 */       throw handleSQLException("delete from BUDGET_ACTIVITY_LINK where    BUDGET_ACTIVITY_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 392 */       if (deleteStmt != null)
/*     */       {
/* 394 */         closeStatement(deleteStmt);
/* 395 */         closeConnection();
/*     */       }
/*     */ 
/* 398 */       this.mDetails = null;
/*     */ 
/* 400 */       if ((somethingChanged) && 
/* 401 */         (timer != null))
/* 402 */         timer.logDebug("update", "collection"); 
/* 402 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 426 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 428 */     PreparedStatement stmt = null;
/* 429 */     ResultSet resultSet = null;
/*     */ 
/* 431 */     int itemCount = 0;
/*     */ 
/* 433 */     BudgetActivityEVO owningEVO = null;
/* 434 */     Iterator ownersIter = owners.iterator();
/* 435 */     while (ownersIter.hasNext())
/*     */     {
/* 437 */       owningEVO = (BudgetActivityEVO)ownersIter.next();
/* 438 */       owningEVO.setActivityLinksAllItemsLoaded(true);
/*     */     }
/* 440 */     ownersIter = owners.iterator();
/* 441 */     owningEVO = (BudgetActivityEVO)ownersIter.next();
/* 442 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 446 */       stmt = getConnection().prepareStatement("select BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID,BUDGET_ACTIVITY_LINK.BUDGET_CYCLE_ID from BUDGET_ACTIVITY_LINK,BUDGET_ACTIVITY where 1=1 and BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID = BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID and BUDGET_ACTIVITY.MODEL_ID = ? order by  BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID ,BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID ,BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID");
/*     */ 
/* 448 */       int col = 1;
/* 449 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 451 */       resultSet = stmt.executeQuery();
/*     */ 
/* 454 */       while (resultSet.next())
/*     */       {
/* 456 */         itemCount++;
/* 457 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 462 */         while (this.mDetails.getBudgetActivityId() != owningEVO.getBudgetActivityId())
/*     */         {
/* 466 */           if (!ownersIter.hasNext())
/*     */           {
/* 468 */             this._log.debug("bulkGetAll", "can't find owning [BudgetActivityId=" + this.mDetails.getBudgetActivityId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 472 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 473 */             ownersIter = owners.iterator();
/* 474 */             while (ownersIter.hasNext())
/*     */             {
/* 476 */               owningEVO = (BudgetActivityEVO)ownersIter.next();
/* 477 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 479 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 481 */           owningEVO = (BudgetActivityEVO)ownersIter.next();
/*     */         }
/* 483 */         if (owningEVO.getActivityLinks() == null)
/*     */         {
/* 485 */           theseItems = new ArrayList();
/* 486 */           owningEVO.setActivityLinks(theseItems);
/* 487 */           owningEVO.setActivityLinksAllItemsLoaded(true);
/*     */         }
/* 489 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 492 */       if (timer != null) {
/* 493 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 498 */       throw handleSQLException("select BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID,BUDGET_ACTIVITY_LINK.BUDGET_CYCLE_ID from BUDGET_ACTIVITY_LINK,BUDGET_ACTIVITY where 1=1 and BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID = BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID and BUDGET_ACTIVITY.MODEL_ID = ? order by  BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID ,BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID ,BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 502 */       closeResultSet(resultSet);
/* 503 */       closeStatement(stmt);
/* 504 */       closeConnection();
/*     */ 
/* 506 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectBudgetActivityId, String dependants, Collection currentList)
/*     */   {
/* 531 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 532 */     PreparedStatement stmt = null;
/* 533 */     ResultSet resultSet = null;
/*     */ 
/* 535 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 539 */       stmt = getConnection().prepareStatement("select BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID,BUDGET_ACTIVITY_LINK.BUDGET_CYCLE_ID from BUDGET_ACTIVITY_LINK where    BUDGET_ACTIVITY_ID = ? ");
/*     */ 
/* 541 */       int col = 1;
/* 542 */       stmt.setInt(col++, selectBudgetActivityId);
/*     */ 
/* 544 */       resultSet = stmt.executeQuery();
/*     */ 
/* 546 */       while (resultSet.next())
/*     */       {
/* 548 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 551 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 554 */       if (currentList != null)
/*     */       {
/* 557 */         ListIterator iter = items.listIterator();
/* 558 */         BudgetActivityLinkEVO currentEVO = null;
/* 559 */         BudgetActivityLinkEVO newEVO = null;
/* 560 */         while (iter.hasNext())
/*     */         {
/* 562 */           newEVO = (BudgetActivityLinkEVO)iter.next();
/* 563 */           Iterator iter2 = currentList.iterator();
/* 564 */           while (iter2.hasNext())
/*     */           {
/* 566 */             currentEVO = (BudgetActivityLinkEVO)iter2.next();
/* 567 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 569 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 575 */         Iterator iter2 = currentList.iterator();
/* 576 */         while (iter2.hasNext())
/*     */         {
/* 578 */           currentEVO = (BudgetActivityLinkEVO)iter2.next();
/* 579 */           if (currentEVO.insertPending()) {
/* 580 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 584 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 588 */       throw handleSQLException("select BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID,BUDGET_ACTIVITY_LINK.BUDGET_CYCLE_ID from BUDGET_ACTIVITY_LINK where    BUDGET_ACTIVITY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 592 */       closeResultSet(resultSet);
/* 593 */       closeStatement(stmt);
/* 594 */       closeConnection();
/*     */ 
/* 596 */       if (timer != null) {
/* 597 */         timer.logDebug("getAll", " BudgetActivityId=" + selectBudgetActivityId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 602 */     return items;
/*     */   }
/*     */ 
/*     */   public BudgetActivityLinkEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 616 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 619 */     if (this.mDetails == null) {
/* 620 */       doLoad(((BudgetActivityLinkCK)paramCK).getBudgetActivityLinkPK());
/*     */     }
/* 622 */     else if (!this.mDetails.getPK().equals(((BudgetActivityLinkCK)paramCK).getBudgetActivityLinkPK())) {
/* 623 */       doLoad(((BudgetActivityLinkCK)paramCK).getBudgetActivityLinkPK());
/*     */     }
/*     */ 
/* 626 */     BudgetActivityLinkEVO details = new BudgetActivityLinkEVO();
/* 627 */     details = this.mDetails.deepClone();
/*     */ 
/* 629 */     if (timer != null) {
/* 630 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 632 */     return details;
/*     */   }
/*     */ 
/*     */   public BudgetActivityLinkEVO getDetails(ModelCK paramCK, BudgetActivityLinkEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 638 */     BudgetActivityLinkEVO savedEVO = this.mDetails;
/* 639 */     this.mDetails = paramEVO;
/* 640 */     BudgetActivityLinkEVO newEVO = getDetails(paramCK, dependants);
/* 641 */     this.mDetails = savedEVO;
/* 642 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public BudgetActivityLinkEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 648 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 652 */     BudgetActivityLinkEVO details = this.mDetails.deepClone();
/*     */ 
/* 654 */     if (timer != null) {
/* 655 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 657 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 662 */     return "BudgetActivityLink";
/*     */   }
/*     */ 
/*     */   public BudgetActivityLinkRefImpl getRef(BudgetActivityLinkPK paramBudgetActivityLinkPK)
/*     */   {
/* 667 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 668 */     PreparedStatement stmt = null;
/* 669 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 672 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID from BUDGET_ACTIVITY_LINK,MODEL,BUDGET_ACTIVITY where 1=1 and BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID = ? and BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID = ? and BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID = BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID and BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID = MODEL.BUDGET_ACTIVITY_ID");
/* 673 */       int col = 1;
/* 674 */       stmt.setInt(col++, paramBudgetActivityLinkPK.getBudgetActivityId());
/* 675 */       stmt.setInt(col++, paramBudgetActivityLinkPK.getStructureElementId());
/*     */ 
/* 677 */       resultSet = stmt.executeQuery();
/*     */ 
/* 679 */       if (!resultSet.next()) {
/* 680 */         throw new RuntimeException(getEntityName() + " getRef " + paramBudgetActivityLinkPK + " not found");
/*     */       }
/* 682 */       col = 2;
/* 683 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 687 */       BudgetActivityPK newBudgetActivityPK = new BudgetActivityPK(resultSet.getInt(col++));
/*     */ 
/* 691 */       String textBudgetActivityLink = "";
/* 692 */       BudgetActivityLinkCK ckBudgetActivityLink = new BudgetActivityLinkCK(newModelPK, newBudgetActivityPK, paramBudgetActivityLinkPK);
/*     */ 
/* 698 */       BudgetActivityLinkRefImpl localBudgetActivityLinkRefImpl = new BudgetActivityLinkRefImpl(ckBudgetActivityLink, textBudgetActivityLink);
/*     */       return localBudgetActivityLinkRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 703 */       throw handleSQLException(paramBudgetActivityLinkPK, "select 0,MODEL.MODEL_ID,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID from BUDGET_ACTIVITY_LINK,MODEL,BUDGET_ACTIVITY where 1=1 and BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID = ? and BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID = ? and BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID = BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID and BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID = MODEL.BUDGET_ACTIVITY_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 707 */       closeResultSet(resultSet);
/* 708 */       closeStatement(stmt);
/* 709 */       closeConnection();
/*     */ 
/* 711 */       if (timer != null)
/* 712 */         timer.logDebug("getRef", paramBudgetActivityLinkPK); 
/* 712 */     }
/*     */   }
/*     */ 
/*     */   public void insertLink(BudgetActivityLinkEVO activityLinkEVO)
/*     */   {
/* 722 */     this.mDetails = activityLinkEVO;
/* 723 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 724 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 728 */       stmt = getConnection().prepareStatement("insert into BUDGET_ACTIVITY_LINK ( BUDGET_ACTIVITY_ID,STRUCTURE_ELEMENT_ID,BUDGET_CYCLE_ID) values ( ?,?,?)");
/*     */ 
/* 731 */       int col = 1;
/* 732 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 733 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 736 */       int resultCount = stmt.executeUpdate();
/* 737 */       if (resultCount != 1)
/*     */       {
/* 739 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 744 */       if (sqle.getErrorCode() != 1)
/* 745 */         throw handleSQLException(this.mDetails.getPK(), "insert into BUDGET_ACTIVITY_LINK ( BUDGET_ACTIVITY_ID,STRUCTURE_ELEMENT_ID,BUDGET_CYCLE_ID) values ( ?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 749 */       closeStatement(stmt);
/* 750 */       closeConnection();
/*     */ 
/* 752 */       if (timer != null)
/* 753 */         timer.logDebug("insertLink", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.act.BudgetActivityLinkDAO
 * JD-Core Version:    0.6.0
 */