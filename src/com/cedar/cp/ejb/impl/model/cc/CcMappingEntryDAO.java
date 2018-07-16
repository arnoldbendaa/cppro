/*     */ package com.cedar.cp.ejb.impl.model.cc;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentLinePK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentPK;
/*     */ import com.cedar.cp.dto.model.cc.CcMappingEntryCK;
/*     */ import com.cedar.cp.dto.model.cc.CcMappingEntryPK;
/*     */ import com.cedar.cp.dto.model.cc.CcMappingEntryRefImpl;
/*     */ import com.cedar.cp.dto.model.cc.CcMappingLinePK;
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
/*     */ public class CcMappingEntryDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select CC_MAPPING_ENTRY.CC_MAPPING_ENTRY_ID,CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID,CC_MAPPING_ENTRY.STRUCTURE_ID,CC_MAPPING_ENTRY.STRUCTURE_ELEMENT_ID,CC_MAPPING_ENTRY.DIM_SEQ";
/*     */   protected static final String SQL_LOAD = " from CC_MAPPING_ENTRY where    CC_MAPPING_ENTRY_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into CC_MAPPING_ENTRY ( CC_MAPPING_ENTRY_ID,CC_MAPPING_LINE_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,DIM_SEQ) values ( ?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update CC_MAPPING_ENTRY set CC_MAPPING_LINE_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,DIM_SEQ = ? where    CC_MAPPING_ENTRY_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from CC_MAPPING_ENTRY where    CC_MAPPING_ENTRY_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from CC_MAPPING_ENTRY,CC_MAPPING_LINE,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID = CC_MAPPING_LINE.CC_MAPPING_LINE_ID and CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID ,CC_MAPPING_ENTRY.CC_MAPPING_ENTRY_ID";
/*     */   protected static final String SQL_GET_ALL = " from CC_MAPPING_ENTRY where    CC_MAPPING_LINE_ID = ? ";
/*     */   protected CcMappingEntryEVO mDetails;
/*     */ 
/*     */   public CcMappingEntryDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public CcMappingEntryDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CcMappingEntryDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected CcMappingEntryPK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(CcMappingEntryEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private CcMappingEntryEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  91 */     int col = 1;
/*  92 */     CcMappingEntryEVO evo = new CcMappingEntryEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/* 100 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(CcMappingEntryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 105 */     int col = startCol_;
/* 106 */     stmt_.setInt(col++, evo_.getCcMappingEntryId());
/* 107 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(CcMappingEntryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 112 */     int col = startCol_;
/* 113 */     stmt_.setInt(col++, evo_.getCcMappingLineId());
/* 114 */     stmt_.setInt(col++, evo_.getStructureId());
/* 115 */     stmt_.setInt(col++, evo_.getStructureElementId());
/* 116 */     stmt_.setInt(col++, evo_.getDimSeq());
/* 117 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(CcMappingEntryPK pk)
/*     */     throws ValidationException
/*     */   {
/* 133 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 135 */     PreparedStatement stmt = null;
/* 136 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 140 */       stmt = getConnection().prepareStatement("select CC_MAPPING_ENTRY.CC_MAPPING_ENTRY_ID,CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID,CC_MAPPING_ENTRY.STRUCTURE_ID,CC_MAPPING_ENTRY.STRUCTURE_ELEMENT_ID,CC_MAPPING_ENTRY.DIM_SEQ from CC_MAPPING_ENTRY where    CC_MAPPING_ENTRY_ID = ? ");
/*     */ 
/* 143 */       int col = 1;
/* 144 */       stmt.setInt(col++, pk.getCcMappingEntryId());
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
/* 158 */       throw handleSQLException(pk, "select CC_MAPPING_ENTRY.CC_MAPPING_ENTRY_ID,CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID,CC_MAPPING_ENTRY.STRUCTURE_ID,CC_MAPPING_ENTRY.STRUCTURE_ELEMENT_ID,CC_MAPPING_ENTRY.DIM_SEQ from CC_MAPPING_ENTRY where    CC_MAPPING_ENTRY_ID = ? ", sqle);
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
/* 194 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 195 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 199 */       stmt = getConnection().prepareStatement("insert into CC_MAPPING_ENTRY ( CC_MAPPING_ENTRY_ID,CC_MAPPING_LINE_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,DIM_SEQ) values ( ?,?,?,?,?)");
/*     */ 
/* 202 */       int col = 1;
/* 203 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 204 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 207 */       int resultCount = stmt.executeUpdate();
/* 208 */       if (resultCount != 1)
/*     */       {
/* 210 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 213 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 217 */       throw handleSQLException(this.mDetails.getPK(), "insert into CC_MAPPING_ENTRY ( CC_MAPPING_ENTRY_ID,CC_MAPPING_LINE_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,DIM_SEQ) values ( ?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 221 */       closeStatement(stmt);
/* 222 */       closeConnection();
/*     */ 
/* 224 */       if (timer != null)
/* 225 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 248 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 252 */     PreparedStatement stmt = null;
/*     */ 
/* 254 */     boolean mainChanged = this.mDetails.isModified();
/* 255 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 258 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 260 */         stmt = getConnection().prepareStatement("update CC_MAPPING_ENTRY set CC_MAPPING_LINE_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,DIM_SEQ = ? where    CC_MAPPING_ENTRY_ID = ? ");
/*     */ 
/* 263 */         int col = 1;
/* 264 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 265 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 268 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 271 */         if (resultCount != 1) {
/* 272 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 275 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 284 */       throw handleSQLException(getPK(), "update CC_MAPPING_ENTRY set CC_MAPPING_LINE_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,DIM_SEQ = ? where    CC_MAPPING_ENTRY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 288 */       closeStatement(stmt);
/* 289 */       closeConnection();
/*     */ 
/* 291 */       if ((timer != null) && (
/* 292 */         (mainChanged) || (dependantChanged)))
/* 293 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 312 */     if (items == null) {
/* 313 */       return false;
/*     */     }
/* 315 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 316 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 318 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 323 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 324 */       while (iter2.hasNext())
/*     */       {
/* 326 */         this.mDetails = ((CcMappingEntryEVO)iter2.next());
/*     */ 
/* 329 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 331 */         somethingChanged = true;
/*     */ 
/* 334 */         if (deleteStmt == null) {
/* 335 */           deleteStmt = getConnection().prepareStatement("delete from CC_MAPPING_ENTRY where    CC_MAPPING_ENTRY_ID = ? ");
/*     */         }
/*     */ 
/* 338 */         int col = 1;
/* 339 */         deleteStmt.setInt(col++, this.mDetails.getCcMappingEntryId());
/*     */ 
/* 341 */         if (this._log.isDebugEnabled()) {
/* 342 */           this._log.debug("update", "CcMappingEntry deleting CcMappingEntryId=" + this.mDetails.getCcMappingEntryId());
/*     */         }
/*     */ 
/* 347 */         deleteStmt.addBatch();
/*     */ 
/* 350 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 355 */       if (deleteStmt != null)
/*     */       {
/* 357 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 359 */         deleteStmt.executeBatch();
/*     */ 
/* 361 */         if (timer2 != null) {
/* 362 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 366 */       Iterator iter1 = items.values().iterator();
/* 367 */       while (iter1.hasNext())
/*     */       {
/* 369 */         this.mDetails = ((CcMappingEntryEVO)iter1.next());
/*     */ 
/* 371 */         if (this.mDetails.insertPending())
/*     */         {
/* 373 */           somethingChanged = true;
/* 374 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 377 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 379 */         somethingChanged = true;
/* 380 */         doStore();
/*     */       }
/*     */ 
/* 391 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 395 */       throw handleSQLException("delete from CC_MAPPING_ENTRY where    CC_MAPPING_ENTRY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 399 */       if (deleteStmt != null)
/*     */       {
/* 401 */         closeStatement(deleteStmt);
/* 402 */         closeConnection();
/*     */       }
/*     */ 
/* 405 */       this.mDetails = null;
/*     */ 
/* 407 */       if ((somethingChanged) && 
/* 408 */         (timer != null))
/* 409 */         timer.logDebug("update", "collection"); 
/* 409 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 438 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 440 */     PreparedStatement stmt = null;
/* 441 */     ResultSet resultSet = null;
/*     */ 
/* 443 */     int itemCount = 0;
/*     */ 
/* 445 */     CcMappingLineEVO owningEVO = null;
/* 446 */     Iterator ownersIter = owners.iterator();
/* 447 */     while (ownersIter.hasNext())
/*     */     {
/* 449 */       owningEVO = (CcMappingLineEVO)ownersIter.next();
/* 450 */       owningEVO.setCCMappingEntriesAllItemsLoaded(true);
/*     */     }
/* 452 */     ownersIter = owners.iterator();
/* 453 */     owningEVO = (CcMappingLineEVO)ownersIter.next();
/* 454 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 458 */       stmt = getConnection().prepareStatement("select CC_MAPPING_ENTRY.CC_MAPPING_ENTRY_ID,CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID,CC_MAPPING_ENTRY.STRUCTURE_ID,CC_MAPPING_ENTRY.STRUCTURE_ELEMENT_ID,CC_MAPPING_ENTRY.DIM_SEQ from CC_MAPPING_ENTRY,CC_MAPPING_LINE,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID = CC_MAPPING_LINE.CC_MAPPING_LINE_ID and CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID ,CC_MAPPING_ENTRY.CC_MAPPING_ENTRY_ID");
/*     */ 
/* 460 */       int col = 1;
/* 461 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 463 */       resultSet = stmt.executeQuery();
/*     */ 
/* 466 */       while (resultSet.next())
/*     */       {
/* 468 */         itemCount++;
/* 469 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 474 */         while (this.mDetails.getCcMappingLineId() != owningEVO.getCcMappingLineId())
/*     */         {
/* 478 */           if (!ownersIter.hasNext())
/*     */           {
/* 480 */             this._log.debug("bulkGetAll", "can't find owning [CcMappingLineId=" + this.mDetails.getCcMappingLineId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 484 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 485 */             ownersIter = owners.iterator();
/* 486 */             while (ownersIter.hasNext())
/*     */             {
/* 488 */               owningEVO = (CcMappingLineEVO)ownersIter.next();
/* 489 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 491 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 493 */           owningEVO = (CcMappingLineEVO)ownersIter.next();
/*     */         }
/* 495 */         if (owningEVO.getCCMappingEntries() == null)
/*     */         {
/* 497 */           theseItems = new ArrayList();
/* 498 */           owningEVO.setCCMappingEntries(theseItems);
/* 499 */           owningEVO.setCCMappingEntriesAllItemsLoaded(true);
/*     */         }
/* 501 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 504 */       if (timer != null) {
/* 505 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 510 */       throw handleSQLException("select CC_MAPPING_ENTRY.CC_MAPPING_ENTRY_ID,CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID,CC_MAPPING_ENTRY.STRUCTURE_ID,CC_MAPPING_ENTRY.STRUCTURE_ELEMENT_ID,CC_MAPPING_ENTRY.DIM_SEQ from CC_MAPPING_ENTRY,CC_MAPPING_LINE,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID = CC_MAPPING_LINE.CC_MAPPING_LINE_ID and CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID ,CC_MAPPING_ENTRY.CC_MAPPING_ENTRY_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 514 */       closeResultSet(resultSet);
/* 515 */       closeStatement(stmt);
/* 516 */       closeConnection();
/*     */ 
/* 518 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectCcMappingLineId, String dependants, Collection currentList)
/*     */   {
/* 543 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 544 */     PreparedStatement stmt = null;
/* 545 */     ResultSet resultSet = null;
/*     */ 
/* 547 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 551 */       stmt = getConnection().prepareStatement("select CC_MAPPING_ENTRY.CC_MAPPING_ENTRY_ID,CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID,CC_MAPPING_ENTRY.STRUCTURE_ID,CC_MAPPING_ENTRY.STRUCTURE_ELEMENT_ID,CC_MAPPING_ENTRY.DIM_SEQ from CC_MAPPING_ENTRY where    CC_MAPPING_LINE_ID = ? ");
/*     */ 
/* 553 */       int col = 1;
/* 554 */       stmt.setInt(col++, selectCcMappingLineId);
/*     */ 
/* 556 */       resultSet = stmt.executeQuery();
/*     */ 
/* 558 */       while (resultSet.next())
/*     */       {
/* 560 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 563 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 566 */       if (currentList != null)
/*     */       {
/* 569 */         ListIterator iter = items.listIterator();
/* 570 */         CcMappingEntryEVO currentEVO = null;
/* 571 */         CcMappingEntryEVO newEVO = null;
/* 572 */         while (iter.hasNext())
/*     */         {
/* 574 */           newEVO = (CcMappingEntryEVO)iter.next();
/* 575 */           Iterator iter2 = currentList.iterator();
/* 576 */           while (iter2.hasNext())
/*     */           {
/* 578 */             currentEVO = (CcMappingEntryEVO)iter2.next();
/* 579 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 581 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 587 */         Iterator iter2 = currentList.iterator();
/* 588 */         while (iter2.hasNext())
/*     */         {
/* 590 */           currentEVO = (CcMappingEntryEVO)iter2.next();
/* 591 */           if (currentEVO.insertPending()) {
/* 592 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 596 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 600 */       throw handleSQLException("select CC_MAPPING_ENTRY.CC_MAPPING_ENTRY_ID,CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID,CC_MAPPING_ENTRY.STRUCTURE_ID,CC_MAPPING_ENTRY.STRUCTURE_ELEMENT_ID,CC_MAPPING_ENTRY.DIM_SEQ from CC_MAPPING_ENTRY where    CC_MAPPING_LINE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 604 */       closeResultSet(resultSet);
/* 605 */       closeStatement(stmt);
/* 606 */       closeConnection();
/*     */ 
/* 608 */       if (timer != null) {
/* 609 */         timer.logDebug("getAll", " CcMappingLineId=" + selectCcMappingLineId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 614 */     return items;
/*     */   }
/*     */ 
/*     */   public CcMappingEntryEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 628 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 631 */     if (this.mDetails == null) {
/* 632 */       doLoad(((CcMappingEntryCK)paramCK).getCcMappingEntryPK());
/*     */     }
/* 634 */     else if (!this.mDetails.getPK().equals(((CcMappingEntryCK)paramCK).getCcMappingEntryPK())) {
/* 635 */       doLoad(((CcMappingEntryCK)paramCK).getCcMappingEntryPK());
/*     */     }
/*     */ 
/* 638 */     CcMappingEntryEVO details = new CcMappingEntryEVO();
/* 639 */     details = this.mDetails.deepClone();
/*     */ 
/* 641 */     if (timer != null) {
/* 642 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 644 */     return details;
/*     */   }
/*     */ 
/*     */   public CcMappingEntryEVO getDetails(ModelCK paramCK, CcMappingEntryEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 650 */     CcMappingEntryEVO savedEVO = this.mDetails;
/* 651 */     this.mDetails = paramEVO;
/* 652 */     CcMappingEntryEVO newEVO = getDetails(paramCK, dependants);
/* 653 */     this.mDetails = savedEVO;
/* 654 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public CcMappingEntryEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 660 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 664 */     CcMappingEntryEVO details = this.mDetails.deepClone();
/*     */ 
/* 666 */     if (timer != null) {
/* 667 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 669 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 674 */     return "CcMappingEntry";
/*     */   }
/*     */ 
/*     */   public CcMappingEntryRefImpl getRef(CcMappingEntryPK paramCcMappingEntryPK)
/*     */   {
/* 679 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 680 */     PreparedStatement stmt = null;
/* 681 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 684 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID,CC_MAPPING_LINE.CC_MAPPING_LINE_ID from CC_MAPPING_ENTRY,MODEL,CC_DEPLOYMENT,CC_DEPLOYMENT_LINE,CC_MAPPING_LINE where 1=1 and CC_MAPPING_ENTRY.CC_MAPPING_ENTRY_ID = ? and CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID = CC_MAPPING_LINE.CC_MAPPING_LINE_ID and CC_MAPPING_LINE.CC_MAPPING_LINE_ID = CC_DEPLOYMENT_LINE.CC_MAPPING_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT.CC_DEPLOYMENT_ID = MODEL.CC_DEPLOYMENT_ID");
/* 685 */       int col = 1;
/* 686 */       stmt.setInt(col++, paramCcMappingEntryPK.getCcMappingEntryId());
/*     */ 
/* 688 */       resultSet = stmt.executeQuery();
/*     */ 
/* 690 */       if (!resultSet.next()) {
/* 691 */         throw new RuntimeException(getEntityName() + " getRef " + paramCcMappingEntryPK + " not found");
/*     */       }
/* 693 */       col = 2;
/* 694 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 698 */       CcDeploymentPK newCcDeploymentPK = new CcDeploymentPK(resultSet.getInt(col++));
/*     */ 
/* 702 */       CcDeploymentLinePK newCcDeploymentLinePK = new CcDeploymentLinePK(resultSet.getInt(col++));
/*     */ 
/* 706 */       CcMappingLinePK newCcMappingLinePK = new CcMappingLinePK(resultSet.getInt(col++));
/*     */ 
/* 710 */       String textCcMappingEntry = "";
/* 711 */       CcMappingEntryCK ckCcMappingEntry = new CcMappingEntryCK(newModelPK, newCcDeploymentPK, newCcDeploymentLinePK, newCcMappingLinePK, paramCcMappingEntryPK);
/*     */ 
/* 719 */       CcMappingEntryRefImpl localCcMappingEntryRefImpl = new CcMappingEntryRefImpl(ckCcMappingEntry, textCcMappingEntry);
/*     */       return localCcMappingEntryRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 724 */       throw handleSQLException(paramCcMappingEntryPK, "select 0,MODEL.MODEL_ID,CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID,CC_MAPPING_LINE.CC_MAPPING_LINE_ID from CC_MAPPING_ENTRY,MODEL,CC_DEPLOYMENT,CC_DEPLOYMENT_LINE,CC_MAPPING_LINE where 1=1 and CC_MAPPING_ENTRY.CC_MAPPING_ENTRY_ID = ? and CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID = CC_MAPPING_LINE.CC_MAPPING_LINE_ID and CC_MAPPING_LINE.CC_MAPPING_LINE_ID = CC_DEPLOYMENT_LINE.CC_MAPPING_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT.CC_DEPLOYMENT_ID = MODEL.CC_DEPLOYMENT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 728 */       closeResultSet(resultSet);
/* 729 */       closeStatement(stmt);
/* 730 */       closeConnection();
/*     */ 
/* 732 */       if (timer != null)
/* 733 */         timer.logDebug("getRef", paramCcMappingEntryPK); 
/* 733 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.cc.CcMappingEntryDAO
 * JD-Core Version:    0.6.0
 */