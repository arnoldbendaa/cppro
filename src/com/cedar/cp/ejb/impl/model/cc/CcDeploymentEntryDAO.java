/*     */ package com.cedar.cp.ejb.impl.model.cc;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentEntryCK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentEntryPK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentEntryRefImpl;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentLinePK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentPK;
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
/*     */ public class CcDeploymentEntryDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_ENTRY_ID,CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ELEMENT_ID,CC_DEPLOYMENT_ENTRY.DIM_SEQ,CC_DEPLOYMENT_ENTRY.SELECTED_IND";
/*     */   protected static final String SQL_LOAD = " from CC_DEPLOYMENT_ENTRY where    CC_DEPLOYMENT_ENTRY_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into CC_DEPLOYMENT_ENTRY ( CC_DEPLOYMENT_ENTRY_ID,CC_DEPLOYMENT_LINE_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,DIM_SEQ,SELECTED_IND) values ( ?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update CC_DEPLOYMENT_ENTRY set CC_DEPLOYMENT_LINE_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,DIM_SEQ = ?,SELECTED_IND = ? where    CC_DEPLOYMENT_ENTRY_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from CC_DEPLOYMENT_ENTRY where    CC_DEPLOYMENT_ENTRY_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from CC_DEPLOYMENT_ENTRY,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID ,CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_ENTRY_ID";
/*     */   protected static final String SQL_GET_ALL = " from CC_DEPLOYMENT_ENTRY where    CC_DEPLOYMENT_LINE_ID = ? ";
/*     */   protected CcDeploymentEntryEVO mDetails;
/*     */ 
/*     */   public CcDeploymentEntryDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public CcDeploymentEntryDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CcDeploymentEntryDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected CcDeploymentEntryPK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(CcDeploymentEntryEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private CcDeploymentEntryEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  92 */     int col = 1;
/*  93 */     CcDeploymentEntryEVO evo = new CcDeploymentEntryEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"));
/*     */ 
/* 102 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(CcDeploymentEntryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 107 */     int col = startCol_;
/* 108 */     stmt_.setInt(col++, evo_.getCcDeploymentEntryId());
/* 109 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(CcDeploymentEntryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 114 */     int col = startCol_;
/* 115 */     stmt_.setInt(col++, evo_.getCcDeploymentLineId());
/* 116 */     stmt_.setInt(col++, evo_.getStructureId());
/* 117 */     stmt_.setInt(col++, evo_.getStructureElementId());
/* 118 */     stmt_.setInt(col++, evo_.getDimSeq());
/* 119 */     if (evo_.getSelectedInd())
/* 120 */       stmt_.setString(col++, "Y");
/*     */     else
/* 122 */       stmt_.setString(col++, " ");
/* 123 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(CcDeploymentEntryPK pk)
/*     */     throws ValidationException
/*     */   {
/* 139 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 141 */     PreparedStatement stmt = null;
/* 142 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 146 */       stmt = getConnection().prepareStatement("select CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_ENTRY_ID,CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ELEMENT_ID,CC_DEPLOYMENT_ENTRY.DIM_SEQ,CC_DEPLOYMENT_ENTRY.SELECTED_IND from CC_DEPLOYMENT_ENTRY where    CC_DEPLOYMENT_ENTRY_ID = ? ");
/*     */ 
/* 149 */       int col = 1;
/* 150 */       stmt.setInt(col++, pk.getCcDeploymentEntryId());
/*     */ 
/* 152 */       resultSet = stmt.executeQuery();
/*     */ 
/* 154 */       if (!resultSet.next()) {
/* 155 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 158 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 159 */       if (this.mDetails.isModified())
/* 160 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 164 */       throw handleSQLException(pk, "select CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_ENTRY_ID,CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ELEMENT_ID,CC_DEPLOYMENT_ENTRY.DIM_SEQ,CC_DEPLOYMENT_ENTRY.SELECTED_IND from CC_DEPLOYMENT_ENTRY where    CC_DEPLOYMENT_ENTRY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 168 */       closeResultSet(resultSet);
/* 169 */       closeStatement(stmt);
/* 170 */       closeConnection();
/*     */ 
/* 172 */       if (timer != null)
/* 173 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 202 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 203 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 207 */       stmt = getConnection().prepareStatement("insert into CC_DEPLOYMENT_ENTRY ( CC_DEPLOYMENT_ENTRY_ID,CC_DEPLOYMENT_LINE_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,DIM_SEQ,SELECTED_IND) values ( ?,?,?,?,?,?)");
/*     */ 
/* 210 */       int col = 1;
/* 211 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 212 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 215 */       int resultCount = stmt.executeUpdate();
/* 216 */       if (resultCount != 1)
/*     */       {
/* 218 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 221 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 225 */       throw handleSQLException(this.mDetails.getPK(), "insert into CC_DEPLOYMENT_ENTRY ( CC_DEPLOYMENT_ENTRY_ID,CC_DEPLOYMENT_LINE_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,DIM_SEQ,SELECTED_IND) values ( ?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 229 */       closeStatement(stmt);
/* 230 */       closeConnection();
/*     */ 
/* 232 */       if (timer != null)
/* 233 */         timer.logDebug("doCreate", this.mDetails.toString());
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
/* 269 */         stmt = getConnection().prepareStatement("update CC_DEPLOYMENT_ENTRY set CC_DEPLOYMENT_LINE_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,DIM_SEQ = ?,SELECTED_IND = ? where    CC_DEPLOYMENT_ENTRY_ID = ? ");
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
/* 293 */       throw handleSQLException(getPK(), "update CC_DEPLOYMENT_ENTRY set CC_DEPLOYMENT_LINE_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,DIM_SEQ = ?,SELECTED_IND = ? where    CC_DEPLOYMENT_ENTRY_ID = ? ", sqle);
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
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 321 */     if (items == null) {
/* 322 */       return false;
/*     */     }
/* 324 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 325 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 327 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 332 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 333 */       while (iter2.hasNext())
/*     */       {
/* 335 */         this.mDetails = ((CcDeploymentEntryEVO)iter2.next());
/*     */ 
/* 338 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 340 */         somethingChanged = true;
/*     */ 
/* 343 */         if (deleteStmt == null) {
/* 344 */           deleteStmt = getConnection().prepareStatement("delete from CC_DEPLOYMENT_ENTRY where    CC_DEPLOYMENT_ENTRY_ID = ? ");
/*     */         }
/*     */ 
/* 347 */         int col = 1;
/* 348 */         deleteStmt.setInt(col++, this.mDetails.getCcDeploymentEntryId());
/*     */ 
/* 350 */         if (this._log.isDebugEnabled()) {
/* 351 */           this._log.debug("update", "CcDeploymentEntry deleting CcDeploymentEntryId=" + this.mDetails.getCcDeploymentEntryId());
/*     */         }
/*     */ 
/* 356 */         deleteStmt.addBatch();
/*     */ 
/* 359 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 364 */       if (deleteStmt != null)
/*     */       {
/* 366 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 368 */         deleteStmt.executeBatch();
/*     */ 
/* 370 */         if (timer2 != null) {
/* 371 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 375 */       Iterator iter1 = items.values().iterator();
/* 376 */       while (iter1.hasNext())
/*     */       {
/* 378 */         this.mDetails = ((CcDeploymentEntryEVO)iter1.next());
/*     */ 
/* 380 */         if (this.mDetails.insertPending())
/*     */         {
/* 382 */           somethingChanged = true;
/* 383 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 386 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 388 */         somethingChanged = true;
/* 389 */         doStore();
/*     */       }
/*     */ 
/* 400 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 404 */       throw handleSQLException("delete from CC_DEPLOYMENT_ENTRY where    CC_DEPLOYMENT_ENTRY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 408 */       if (deleteStmt != null)
/*     */       {
/* 410 */         closeStatement(deleteStmt);
/* 411 */         closeConnection();
/*     */       }
/*     */ 
/* 414 */       this.mDetails = null;
/*     */ 
/* 416 */       if ((somethingChanged) && 
/* 417 */         (timer != null))
/* 418 */         timer.logDebug("update", "collection"); 
/* 418 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 444 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 446 */     PreparedStatement stmt = null;
/* 447 */     ResultSet resultSet = null;
/*     */ 
/* 449 */     int itemCount = 0;
/*     */ 
/* 451 */     CcDeploymentLineEVO owningEVO = null;
/* 452 */     Iterator ownersIter = owners.iterator();
/* 453 */     while (ownersIter.hasNext())
/*     */     {
/* 455 */       owningEVO = (CcDeploymentLineEVO)ownersIter.next();
/* 456 */       owningEVO.setCCDeploymentEntriesAllItemsLoaded(true);
/*     */     }
/* 458 */     ownersIter = owners.iterator();
/* 459 */     owningEVO = (CcDeploymentLineEVO)ownersIter.next();
/* 460 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 464 */       stmt = getConnection().prepareStatement("select CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_ENTRY_ID,CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ELEMENT_ID,CC_DEPLOYMENT_ENTRY.DIM_SEQ,CC_DEPLOYMENT_ENTRY.SELECTED_IND from CC_DEPLOYMENT_ENTRY,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID ,CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_ENTRY_ID");
/*     */ 
/* 466 */       int col = 1;
/* 467 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 469 */       resultSet = stmt.executeQuery();
/*     */ 
/* 472 */       while (resultSet.next())
/*     */       {
/* 474 */         itemCount++;
/* 475 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 480 */         while (this.mDetails.getCcDeploymentLineId() != owningEVO.getCcDeploymentLineId())
/*     */         {
/* 484 */           if (!ownersIter.hasNext())
/*     */           {
/* 486 */             this._log.debug("bulkGetAll", "can't find owning [CcDeploymentLineId=" + this.mDetails.getCcDeploymentLineId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 490 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 491 */             ownersIter = owners.iterator();
/* 492 */             while (ownersIter.hasNext())
/*     */             {
/* 494 */               owningEVO = (CcDeploymentLineEVO)ownersIter.next();
/* 495 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 497 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 499 */           owningEVO = (CcDeploymentLineEVO)ownersIter.next();
/*     */         }
/* 501 */         if (owningEVO.getCCDeploymentEntries() == null)
/*     */         {
/* 503 */           theseItems = new ArrayList();
/* 504 */           owningEVO.setCCDeploymentEntries(theseItems);
/* 505 */           owningEVO.setCCDeploymentEntriesAllItemsLoaded(true);
/*     */         }
/* 507 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 510 */       if (timer != null) {
/* 511 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 516 */       throw handleSQLException("select CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_ENTRY_ID,CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ELEMENT_ID,CC_DEPLOYMENT_ENTRY.DIM_SEQ,CC_DEPLOYMENT_ENTRY.SELECTED_IND from CC_DEPLOYMENT_ENTRY,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID ,CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_ENTRY_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 520 */       closeResultSet(resultSet);
/* 521 */       closeStatement(stmt);
/* 522 */       closeConnection();
/*     */ 
/* 524 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectCcDeploymentLineId, String dependants, Collection currentList)
/*     */   {
/* 549 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 550 */     PreparedStatement stmt = null;
/* 551 */     ResultSet resultSet = null;
/*     */ 
/* 553 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 557 */       stmt = getConnection().prepareStatement("select CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_ENTRY_ID,CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ELEMENT_ID,CC_DEPLOYMENT_ENTRY.DIM_SEQ,CC_DEPLOYMENT_ENTRY.SELECTED_IND from CC_DEPLOYMENT_ENTRY where    CC_DEPLOYMENT_LINE_ID = ? ");
/*     */ 
/* 559 */       int col = 1;
/* 560 */       stmt.setInt(col++, selectCcDeploymentLineId);
/*     */ 
/* 562 */       resultSet = stmt.executeQuery();
/*     */ 
/* 564 */       while (resultSet.next())
/*     */       {
/* 566 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 569 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 572 */       if (currentList != null)
/*     */       {
/* 575 */         ListIterator iter = items.listIterator();
/* 576 */         CcDeploymentEntryEVO currentEVO = null;
/* 577 */         CcDeploymentEntryEVO newEVO = null;
/* 578 */         while (iter.hasNext())
/*     */         {
/* 580 */           newEVO = (CcDeploymentEntryEVO)iter.next();
/* 581 */           Iterator iter2 = currentList.iterator();
/* 582 */           while (iter2.hasNext())
/*     */           {
/* 584 */             currentEVO = (CcDeploymentEntryEVO)iter2.next();
/* 585 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 587 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 593 */         Iterator iter2 = currentList.iterator();
/* 594 */         while (iter2.hasNext())
/*     */         {
/* 596 */           currentEVO = (CcDeploymentEntryEVO)iter2.next();
/* 597 */           if (currentEVO.insertPending()) {
/* 598 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 602 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 606 */       throw handleSQLException("select CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_ENTRY_ID,CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ID,CC_DEPLOYMENT_ENTRY.STRUCTURE_ELEMENT_ID,CC_DEPLOYMENT_ENTRY.DIM_SEQ,CC_DEPLOYMENT_ENTRY.SELECTED_IND from CC_DEPLOYMENT_ENTRY where    CC_DEPLOYMENT_LINE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 610 */       closeResultSet(resultSet);
/* 611 */       closeStatement(stmt);
/* 612 */       closeConnection();
/*     */ 
/* 614 */       if (timer != null) {
/* 615 */         timer.logDebug("getAll", " CcDeploymentLineId=" + selectCcDeploymentLineId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 620 */     return items;
/*     */   }
/*     */ 
/*     */   public CcDeploymentEntryEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 634 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 637 */     if (this.mDetails == null) {
/* 638 */       doLoad(((CcDeploymentEntryCK)paramCK).getCcDeploymentEntryPK());
/*     */     }
/* 640 */     else if (!this.mDetails.getPK().equals(((CcDeploymentEntryCK)paramCK).getCcDeploymentEntryPK())) {
/* 641 */       doLoad(((CcDeploymentEntryCK)paramCK).getCcDeploymentEntryPK());
/*     */     }
/*     */ 
/* 644 */     CcDeploymentEntryEVO details = new CcDeploymentEntryEVO();
/* 645 */     details = this.mDetails.deepClone();
/*     */ 
/* 647 */     if (timer != null) {
/* 648 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 650 */     return details;
/*     */   }
/*     */ 
/*     */   public CcDeploymentEntryEVO getDetails(ModelCK paramCK, CcDeploymentEntryEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 656 */     CcDeploymentEntryEVO savedEVO = this.mDetails;
/* 657 */     this.mDetails = paramEVO;
/* 658 */     CcDeploymentEntryEVO newEVO = getDetails(paramCK, dependants);
/* 659 */     this.mDetails = savedEVO;
/* 660 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public CcDeploymentEntryEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 666 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 670 */     CcDeploymentEntryEVO details = this.mDetails.deepClone();
/*     */ 
/* 672 */     if (timer != null) {
/* 673 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 675 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 680 */     return "CcDeploymentEntry";
/*     */   }
/*     */ 
/*     */   public CcDeploymentEntryRefImpl getRef(CcDeploymentEntryPK paramCcDeploymentEntryPK)
/*     */   {
/* 685 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 686 */     PreparedStatement stmt = null;
/* 687 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 690 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID from CC_DEPLOYMENT_ENTRY,MODEL,CC_DEPLOYMENT,CC_DEPLOYMENT_LINE where 1=1 and CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_ENTRY_ID = ? and CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT.CC_DEPLOYMENT_ID = MODEL.CC_DEPLOYMENT_ID");
/* 691 */       int col = 1;
/* 692 */       stmt.setInt(col++, paramCcDeploymentEntryPK.getCcDeploymentEntryId());
/*     */ 
/* 694 */       resultSet = stmt.executeQuery();
/*     */ 
/* 696 */       if (!resultSet.next()) {
/* 697 */         throw new RuntimeException(getEntityName() + " getRef " + paramCcDeploymentEntryPK + " not found");
/*     */       }
/* 699 */       col = 2;
/* 700 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 704 */       CcDeploymentPK newCcDeploymentPK = new CcDeploymentPK(resultSet.getInt(col++));
/*     */ 
/* 708 */       CcDeploymentLinePK newCcDeploymentLinePK = new CcDeploymentLinePK(resultSet.getInt(col++));
/*     */ 
/* 712 */       String textCcDeploymentEntry = "";
/* 713 */       CcDeploymentEntryCK ckCcDeploymentEntry = new CcDeploymentEntryCK(newModelPK, newCcDeploymentPK, newCcDeploymentLinePK, paramCcDeploymentEntryPK);
/*     */ 
/* 720 */       CcDeploymentEntryRefImpl localCcDeploymentEntryRefImpl = new CcDeploymentEntryRefImpl(ckCcDeploymentEntry, textCcDeploymentEntry);
/*     */       return localCcDeploymentEntryRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 725 */       throw handleSQLException(paramCcDeploymentEntryPK, "select 0,MODEL.MODEL_ID,CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID from CC_DEPLOYMENT_ENTRY,MODEL,CC_DEPLOYMENT,CC_DEPLOYMENT_LINE where 1=1 and CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_ENTRY_ID = ? and CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT.CC_DEPLOYMENT_ID = MODEL.CC_DEPLOYMENT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 729 */       closeResultSet(resultSet);
/* 730 */       closeStatement(stmt);
/* 731 */       closeConnection();
/*     */ 
/* 733 */       if (timer != null)
/* 734 */         timer.logDebug("getRef", paramCcDeploymentEntryPK); 
/* 734 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.cc.CcDeploymentEntryDAO
 * JD-Core Version:    0.6.0
 */