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
/*     */ import com.cedar.cp.dto.model.cc.CcMappingLineCK;
/*     */ import com.cedar.cp.dto.model.cc.CcMappingLinePK;
/*     */ import com.cedar.cp.dto.model.cc.CcMappingLineRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class CcMappingLineDAO extends AbstractDAO
/*     */ {
/*  38 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select CC_MAPPING_LINE.CC_MAPPING_LINE_ID,CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID,CC_MAPPING_LINE.DATA_TYPE_ID,CC_MAPPING_LINE.FORM_FIELD_NAME";
/*     */   protected static final String SQL_LOAD = " from CC_MAPPING_LINE where    CC_MAPPING_LINE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into CC_MAPPING_LINE ( CC_MAPPING_LINE_ID,CC_DEPLOYMENT_LINE_ID,DATA_TYPE_ID,FORM_FIELD_NAME) values ( ?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update CC_MAPPING_LINE set CC_DEPLOYMENT_LINE_ID = ?,DATA_TYPE_ID = ?,FORM_FIELD_NAME = ? where    CC_MAPPING_LINE_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from CC_MAPPING_LINE where    CC_MAPPING_LINE_ID = ? ";
/* 453 */   private static String[][] SQL_DELETE_CHILDREN = { { "CC_MAPPING_ENTRY", "delete from CC_MAPPING_ENTRY where     CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID = ? " } };
/*     */ 
/* 462 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*     */ 
/* 466 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and CC_MAPPING_LINE.CC_MAPPING_LINE_ID = ?)";
/*     */   public static final String SQL_BULK_GET_ALL = " from CC_MAPPING_LINE,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID ,CC_MAPPING_LINE.CC_MAPPING_LINE_ID";
/*     */   protected static final String SQL_GET_ALL = " from CC_MAPPING_LINE where    CC_DEPLOYMENT_LINE_ID = ? ";
/*     */   protected CcMappingEntryDAO mCcMappingEntryDAO;
/*     */   protected CcMappingLineEVO mDetails;
/*     */ 
/*     */   public CcMappingLineDAO(Connection connection)
/*     */   {
/*  45 */     super(connection);
/*     */   }
/*     */ 
/*     */   public CcMappingLineDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CcMappingLineDAO(DataSource ds)
/*     */   {
/*  61 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected CcMappingLinePK getPK()
/*     */   {
/*  69 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(CcMappingLineEVO details)
/*     */   {
/*  78 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private CcMappingLineEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  93 */     int col = 1;
/*  94 */     CcMappingLineEVO evo = new CcMappingLineEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), null);
/*     */ 
/* 102 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(CcMappingLineEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 107 */     int col = startCol_;
/* 108 */     stmt_.setInt(col++, evo_.getCcMappingLineId());
/* 109 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(CcMappingLineEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 114 */     int col = startCol_;
/* 115 */     stmt_.setInt(col++, evo_.getCcDeploymentLineId());
/* 116 */     stmt_.setInt(col++, evo_.getDataTypeId());
/* 117 */     stmt_.setString(col++, evo_.getFormFieldName());
/* 118 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(CcMappingLinePK pk)
/*     */     throws ValidationException
/*     */   {
/* 134 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 136 */     PreparedStatement stmt = null;
/* 137 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 141 */       stmt = getConnection().prepareStatement("select CC_MAPPING_LINE.CC_MAPPING_LINE_ID,CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID,CC_MAPPING_LINE.DATA_TYPE_ID,CC_MAPPING_LINE.FORM_FIELD_NAME from CC_MAPPING_LINE where    CC_MAPPING_LINE_ID = ? ");
/*     */ 
/* 144 */       int col = 1;
/* 145 */       stmt.setInt(col++, pk.getCcMappingLineId());
/*     */ 
/* 147 */       resultSet = stmt.executeQuery();
/*     */ 
/* 149 */       if (!resultSet.next()) {
/* 150 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 153 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 154 */       if (this.mDetails.isModified())
/* 155 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 159 */       throw handleSQLException(pk, "select CC_MAPPING_LINE.CC_MAPPING_LINE_ID,CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID,CC_MAPPING_LINE.DATA_TYPE_ID,CC_MAPPING_LINE.FORM_FIELD_NAME from CC_MAPPING_LINE where    CC_MAPPING_LINE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 163 */       closeResultSet(resultSet);
/* 164 */       closeStatement(stmt);
/* 165 */       closeConnection();
/*     */ 
/* 167 */       if (timer != null)
/* 168 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 193 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 194 */     this.mDetails.postCreateInit();
/*     */ 
/* 196 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 200 */       stmt = getConnection().prepareStatement("insert into CC_MAPPING_LINE ( CC_MAPPING_LINE_ID,CC_DEPLOYMENT_LINE_ID,DATA_TYPE_ID,FORM_FIELD_NAME) values ( ?,?,?,?)");
/*     */ 
/* 203 */       int col = 1;
/* 204 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 205 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 208 */       int resultCount = stmt.executeUpdate();
/* 209 */       if (resultCount != 1)
/*     */       {
/* 211 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 214 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 218 */       throw handleSQLException(this.mDetails.getPK(), "insert into CC_MAPPING_LINE ( CC_MAPPING_LINE_ID,CC_DEPLOYMENT_LINE_ID,DATA_TYPE_ID,FORM_FIELD_NAME) values ( ?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 222 */       closeStatement(stmt);
/* 223 */       closeConnection();
/*     */ 
/* 225 */       if (timer != null) {
/* 226 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 232 */       getCcMappingEntryDAO().update(this.mDetails.getCCMappingEntriesMap());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 238 */       throw new RuntimeException("unexpected exception", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 260 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 264 */     PreparedStatement stmt = null;
/*     */ 
/* 266 */     boolean mainChanged = this.mDetails.isModified();
/* 267 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 271 */       if (getCcMappingEntryDAO().update(this.mDetails.getCCMappingEntriesMap())) {
/* 272 */         dependantChanged = true;
/*     */       }
/* 274 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 276 */         stmt = getConnection().prepareStatement("update CC_MAPPING_LINE set CC_DEPLOYMENT_LINE_ID = ?,DATA_TYPE_ID = ?,FORM_FIELD_NAME = ? where    CC_MAPPING_LINE_ID = ? ");
/*     */ 
/* 279 */         int col = 1;
/* 280 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 281 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 284 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 287 */         if (resultCount != 1) {
/* 288 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 291 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 300 */       throw handleSQLException(getPK(), "update CC_MAPPING_LINE set CC_DEPLOYMENT_LINE_ID = ?,DATA_TYPE_ID = ?,FORM_FIELD_NAME = ? where    CC_MAPPING_LINE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 304 */       closeStatement(stmt);
/* 305 */       closeConnection();
/*     */ 
/* 307 */       if ((timer != null) && (
/* 308 */         (mainChanged) || (dependantChanged)))
/* 309 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 328 */     if (items == null) {
/* 329 */       return false;
/*     */     }
/* 331 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 332 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 334 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 338 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/* 339 */       while (iter3.hasNext())
/*     */       {
/* 341 */         this.mDetails = ((CcMappingLineEVO)iter3.next());
/* 342 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 344 */         somethingChanged = true;
/*     */ 
/* 347 */         deleteDependants(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 351 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 352 */       while (iter2.hasNext())
/*     */       {
/* 354 */         this.mDetails = ((CcMappingLineEVO)iter2.next());
/*     */ 
/* 357 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 359 */         somethingChanged = true;
/*     */ 
/* 362 */         if (deleteStmt == null) {
/* 363 */           deleteStmt = getConnection().prepareStatement("delete from CC_MAPPING_LINE where    CC_MAPPING_LINE_ID = ? ");
/*     */         }
/*     */ 
/* 366 */         int col = 1;
/* 367 */         deleteStmt.setInt(col++, this.mDetails.getCcMappingLineId());
/*     */ 
/* 369 */         if (this._log.isDebugEnabled()) {
/* 370 */           this._log.debug("update", "CcMappingLine deleting CcMappingLineId=" + this.mDetails.getCcMappingLineId());
/*     */         }
/*     */ 
/* 375 */         deleteStmt.addBatch();
/*     */ 
/* 378 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 383 */       if (deleteStmt != null)
/*     */       {
/* 385 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 387 */         deleteStmt.executeBatch();
/*     */ 
/* 389 */         if (timer2 != null) {
/* 390 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 394 */       Iterator iter1 = items.values().iterator();
/* 395 */       while (iter1.hasNext())
/*     */       {
/* 397 */         this.mDetails = ((CcMappingLineEVO)iter1.next());
/*     */ 
/* 399 */         if (this.mDetails.insertPending())
/*     */         {
/* 401 */           somethingChanged = true;
/* 402 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 405 */         if (this.mDetails.isModified())
/*     */         {
/* 407 */           somethingChanged = true;
/* 408 */           doStore(); continue;
/*     */         }
/*     */ 
/* 412 */         if ((this.mDetails.deletePending()) || 
/* 418 */           (!getCcMappingEntryDAO().update(this.mDetails.getCCMappingEntriesMap()))) continue;
/* 419 */         somethingChanged = true;
/*     */       }
/*     */ 
/* 431 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 435 */       throw handleSQLException("delete from CC_MAPPING_LINE where    CC_MAPPING_LINE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 439 */       if (deleteStmt != null)
/*     */       {
/* 441 */         closeStatement(deleteStmt);
/* 442 */         closeConnection();
/*     */       }
/*     */ 
/* 445 */       this.mDetails = null;
/*     */ 
/* 447 */       if ((somethingChanged) && 
/* 448 */         (timer != null))
/* 449 */         timer.logDebug("update", "collection"); 
/* 449 */     }
/*     */   }
/*     */ 
/*     */   private void deleteDependants(CcMappingLinePK pk)
/*     */   {
/* 475 */     Set emptyStrings = Collections.emptySet();
/* 476 */     deleteDependants(pk, emptyStrings);
/*     */   }
/*     */ 
/*     */   private void deleteDependants(CcMappingLinePK pk, Set<String> exclusionTables)
/*     */   {
/* 482 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*     */     {
/* 484 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*     */         continue;
/* 486 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 488 */       PreparedStatement stmt = null;
/*     */ 
/* 490 */       int resultCount = 0;
/* 491 */       String s = null;
/*     */       try
/*     */       {
/* 494 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*     */ 
/* 496 */         if (this._log.isDebugEnabled()) {
/* 497 */           this._log.debug("deleteDependants", s);
/*     */         }
/* 499 */         stmt = getConnection().prepareStatement(s);
/*     */ 
/* 502 */         int col = 1;
/* 503 */         stmt.setInt(col++, pk.getCcMappingLineId());
/*     */ 
/* 506 */         resultCount = stmt.executeUpdate();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 510 */         throw handleSQLException(pk, s, sqle);
/*     */       }
/*     */       finally
/*     */       {
/* 514 */         closeStatement(stmt);
/* 515 */         closeConnection();
/*     */ 
/* 517 */         if (timer != null) {
/* 518 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*     */         }
/*     */       }
/*     */     }
/* 522 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*     */     {
/* 524 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*     */         continue;
/* 526 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 528 */       PreparedStatement stmt = null;
/*     */ 
/* 530 */       int resultCount = 0;
/* 531 */       String s = null;
/*     */       try
/*     */       {
/* 534 */         s = SQL_DELETE_CHILDREN[i][1];
/*     */ 
/* 536 */         if (this._log.isDebugEnabled()) {
/* 537 */           this._log.debug("deleteDependants", s);
/*     */         }
/* 539 */         stmt = getConnection().prepareStatement(s);
/*     */ 
/* 542 */         int col = 1;
/* 543 */         stmt.setInt(col++, pk.getCcMappingLineId());
/*     */ 
/* 546 */         resultCount = stmt.executeUpdate();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 550 */         throw handleSQLException(pk, s, sqle);
/*     */       }
/*     */       finally
/*     */       {
/* 554 */         closeStatement(stmt);
/* 555 */         closeConnection();
/*     */ 
/* 557 */         if (timer != null)
/* 558 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 585 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 587 */     PreparedStatement stmt = null;
/* 588 */     ResultSet resultSet = null;
/*     */ 
/* 590 */     int itemCount = 0;
/*     */ 
/* 592 */     CcDeploymentLineEVO owningEVO = null;
/* 593 */     Iterator ownersIter = owners.iterator();
/* 594 */     while (ownersIter.hasNext())
/*     */     {
/* 596 */       owningEVO = (CcDeploymentLineEVO)ownersIter.next();
/* 597 */       owningEVO.setCCMappingLinesAllItemsLoaded(true);
/*     */     }
/* 599 */     ownersIter = owners.iterator();
/* 600 */     owningEVO = (CcDeploymentLineEVO)ownersIter.next();
/* 601 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 605 */       stmt = getConnection().prepareStatement("select CC_MAPPING_LINE.CC_MAPPING_LINE_ID,CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID,CC_MAPPING_LINE.DATA_TYPE_ID,CC_MAPPING_LINE.FORM_FIELD_NAME from CC_MAPPING_LINE,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID ,CC_MAPPING_LINE.CC_MAPPING_LINE_ID");
/*     */ 
/* 607 */       int col = 1;
/* 608 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 610 */       resultSet = stmt.executeQuery();
/*     */ 
/* 613 */       while (resultSet.next())
/*     */       {
/* 615 */         itemCount++;
/* 616 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 621 */         while (this.mDetails.getCcDeploymentLineId() != owningEVO.getCcDeploymentLineId())
/*     */         {
/* 625 */           if (!ownersIter.hasNext())
/*     */           {
/* 627 */             this._log.debug("bulkGetAll", "can't find owning [CcDeploymentLineId=" + this.mDetails.getCcDeploymentLineId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 631 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 632 */             ownersIter = owners.iterator();
/* 633 */             while (ownersIter.hasNext())
/*     */             {
/* 635 */               owningEVO = (CcDeploymentLineEVO)ownersIter.next();
/* 636 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 638 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 640 */           owningEVO = (CcDeploymentLineEVO)ownersIter.next();
/*     */         }
/* 642 */         if (owningEVO.getCCMappingLines() == null)
/*     */         {
/* 644 */           theseItems = new ArrayList();
/* 645 */           owningEVO.setCCMappingLines(theseItems);
/* 646 */           owningEVO.setCCMappingLinesAllItemsLoaded(true);
/*     */         }
/* 648 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 651 */       if (timer != null) {
/* 652 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */ 
/* 655 */       if ((itemCount > 0) && (dependants.indexOf("<46>") > -1))
/*     */       {
/* 657 */         getCcMappingEntryDAO().bulkGetAll(entityPK, theseItems, dependants);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle) {
/* 661 */       throw handleSQLException("select CC_MAPPING_LINE.CC_MAPPING_LINE_ID,CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID,CC_MAPPING_LINE.DATA_TYPE_ID,CC_MAPPING_LINE.FORM_FIELD_NAME from CC_MAPPING_LINE,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID ,CC_MAPPING_LINE.CC_MAPPING_LINE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 665 */       closeResultSet(resultSet);
/* 666 */       closeStatement(stmt);
/* 667 */       closeConnection();
/*     */ 
/* 669 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectCcDeploymentLineId, String dependants, Collection currentList)
/*     */   {
/* 694 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 695 */     PreparedStatement stmt = null;
/* 696 */     ResultSet resultSet = null;
/*     */ 
/* 698 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 702 */       stmt = getConnection().prepareStatement("select CC_MAPPING_LINE.CC_MAPPING_LINE_ID,CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID,CC_MAPPING_LINE.DATA_TYPE_ID,CC_MAPPING_LINE.FORM_FIELD_NAME from CC_MAPPING_LINE where    CC_DEPLOYMENT_LINE_ID = ? ");
/*     */ 
/* 704 */       int col = 1;
/* 705 */       stmt.setInt(col++, selectCcDeploymentLineId);
/*     */ 
/* 707 */       resultSet = stmt.executeQuery();
/*     */ 
/* 709 */       while (resultSet.next())
/*     */       {
/* 711 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 714 */         getDependants(this.mDetails, dependants);
/*     */ 
/* 717 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 720 */       if (currentList != null)
/*     */       {
/* 723 */         ListIterator iter = items.listIterator();
/* 724 */         CcMappingLineEVO currentEVO = null;
/* 725 */         CcMappingLineEVO newEVO = null;
/* 726 */         while (iter.hasNext())
/*     */         {
/* 728 */           newEVO = (CcMappingLineEVO)iter.next();
/* 729 */           Iterator iter2 = currentList.iterator();
/* 730 */           while (iter2.hasNext())
/*     */           {
/* 732 */             currentEVO = (CcMappingLineEVO)iter2.next();
/* 733 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 735 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 741 */         Iterator iter2 = currentList.iterator();
/* 742 */         while (iter2.hasNext())
/*     */         {
/* 744 */           currentEVO = (CcMappingLineEVO)iter2.next();
/* 745 */           if (currentEVO.insertPending()) {
/* 746 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 750 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 754 */       throw handleSQLException("select CC_MAPPING_LINE.CC_MAPPING_LINE_ID,CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID,CC_MAPPING_LINE.DATA_TYPE_ID,CC_MAPPING_LINE.FORM_FIELD_NAME from CC_MAPPING_LINE where    CC_DEPLOYMENT_LINE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 758 */       closeResultSet(resultSet);
/* 759 */       closeStatement(stmt);
/* 760 */       closeConnection();
/*     */ 
/* 762 */       if (timer != null) {
/* 763 */         timer.logDebug("getAll", " CcDeploymentLineId=" + selectCcDeploymentLineId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 768 */     return items;
/*     */   }
/*     */ 
/*     */   public CcMappingLineEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 785 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 788 */     if (this.mDetails == null) {
/* 789 */       doLoad(((CcMappingLineCK)paramCK).getCcMappingLinePK());
/*     */     }
/* 791 */     else if (!this.mDetails.getPK().equals(((CcMappingLineCK)paramCK).getCcMappingLinePK())) {
/* 792 */       doLoad(((CcMappingLineCK)paramCK).getCcMappingLinePK());
/*     */     }
/*     */ 
/* 795 */     if ((dependants.indexOf("<46>") > -1) && (!this.mDetails.isCCMappingEntriesAllItemsLoaded()))
/*     */     {
/* 800 */       this.mDetails.setCCMappingEntries(getCcMappingEntryDAO().getAll(this.mDetails.getCcMappingLineId(), dependants, this.mDetails.getCCMappingEntries()));
/*     */ 
/* 807 */       this.mDetails.setCCMappingEntriesAllItemsLoaded(true);
/*     */     }
/*     */ 
/* 810 */     if ((paramCK instanceof CcMappingEntryCK))
/*     */     {
/* 812 */       if (this.mDetails.getCCMappingEntries() == null) {
/* 813 */         this.mDetails.loadCCMappingEntriesItem(getCcMappingEntryDAO().getDetails(paramCK, dependants));
/*     */       }
/*     */       else {
/* 816 */         CcMappingEntryPK pk = ((CcMappingEntryCK)paramCK).getCcMappingEntryPK();
/* 817 */         CcMappingEntryEVO evo = this.mDetails.getCCMappingEntriesItem(pk);
/* 818 */         if (evo == null) {
/* 819 */           this.mDetails.loadCCMappingEntriesItem(getCcMappingEntryDAO().getDetails(paramCK, dependants));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 824 */     CcMappingLineEVO details = new CcMappingLineEVO();
/* 825 */     details = this.mDetails.deepClone();
/*     */ 
/* 827 */     if (timer != null) {
/* 828 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 830 */     return details;
/*     */   }
/*     */ 
/*     */   public CcMappingLineEVO getDetails(ModelCK paramCK, CcMappingLineEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 836 */     CcMappingLineEVO savedEVO = this.mDetails;
/* 837 */     this.mDetails = paramEVO;
/* 838 */     CcMappingLineEVO newEVO = getDetails(paramCK, dependants);
/* 839 */     this.mDetails = savedEVO;
/* 840 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public CcMappingLineEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 846 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 850 */     getDependants(this.mDetails, dependants);
/*     */ 
/* 853 */     CcMappingLineEVO details = this.mDetails.deepClone();
/*     */ 
/* 855 */     if (timer != null) {
/* 856 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 858 */     return details;
/*     */   }
/*     */ 
/*     */   protected CcMappingEntryDAO getCcMappingEntryDAO()
/*     */   {
/* 867 */     if (this.mCcMappingEntryDAO == null)
/*     */     {
/* 869 */       if (this.mDataSource != null)
/* 870 */         this.mCcMappingEntryDAO = new CcMappingEntryDAO(this.mDataSource);
/*     */       else {
/* 872 */         this.mCcMappingEntryDAO = new CcMappingEntryDAO(getConnection());
/*     */       }
/*     */     }
/* 875 */     return this.mCcMappingEntryDAO;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 880 */     return "CcMappingLine";
/*     */   }
/*     */ 
/*     */   public CcMappingLineRefImpl getRef(CcMappingLinePK paramCcMappingLinePK)
/*     */   {
/* 885 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 886 */     PreparedStatement stmt = null;
/* 887 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 890 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID from CC_MAPPING_LINE,MODEL,CC_DEPLOYMENT,CC_DEPLOYMENT_LINE where 1=1 and CC_MAPPING_LINE.CC_MAPPING_LINE_ID = ? and CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT.CC_DEPLOYMENT_ID = MODEL.CC_DEPLOYMENT_ID");
/* 891 */       int col = 1;
/* 892 */       stmt.setInt(col++, paramCcMappingLinePK.getCcMappingLineId());
/*     */ 
/* 894 */       resultSet = stmt.executeQuery();
/*     */ 
/* 896 */       if (!resultSet.next()) {
/* 897 */         throw new RuntimeException(getEntityName() + " getRef " + paramCcMappingLinePK + " not found");
/*     */       }
/* 899 */       col = 2;
/* 900 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 904 */       CcDeploymentPK newCcDeploymentPK = new CcDeploymentPK(resultSet.getInt(col++));
/*     */ 
/* 908 */       CcDeploymentLinePK newCcDeploymentLinePK = new CcDeploymentLinePK(resultSet.getInt(col++));
/*     */ 
/* 912 */       String textCcMappingLine = "";
/* 913 */       CcMappingLineCK ckCcMappingLine = new CcMappingLineCK(newModelPK, newCcDeploymentPK, newCcDeploymentLinePK, paramCcMappingLinePK);
/*     */ 
/* 920 */       CcMappingLineRefImpl localCcMappingLineRefImpl = new CcMappingLineRefImpl(ckCcMappingLine, textCcMappingLine);
/*     */       return localCcMappingLineRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 925 */       throw handleSQLException(paramCcMappingLinePK, "select 0,MODEL.MODEL_ID,CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID from CC_MAPPING_LINE,MODEL,CC_DEPLOYMENT,CC_DEPLOYMENT_LINE where 1=1 and CC_MAPPING_LINE.CC_MAPPING_LINE_ID = ? and CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT.CC_DEPLOYMENT_ID = MODEL.CC_DEPLOYMENT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 929 */       closeResultSet(resultSet);
/* 930 */       closeStatement(stmt);
/* 931 */       closeConnection();
/*     */ 
/* 933 */       if (timer != null)
/* 934 */         timer.logDebug("getRef", paramCcMappingLinePK); 
/* 934 */     }
/*     */   }
/*     */ 
/*     */   public void getDependants(Collection c, String dependants)
/*     */   {
/* 946 */     if (c == null)
/* 947 */       return;
/* 948 */     Iterator iter = c.iterator();
/* 949 */     while (iter.hasNext())
/*     */     {
/* 951 */       CcMappingLineEVO evo = (CcMappingLineEVO)iter.next();
/* 952 */       getDependants(evo, dependants);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void getDependants(CcMappingLineEVO evo, String dependants)
/*     */   {
/* 966 */     if (evo.insertPending()) {
/* 967 */       return;
/*     */     }
/* 969 */     if (evo.getCcMappingLineId() < 1) {
/* 970 */       return;
/*     */     }
/*     */ 
/* 974 */     if (dependants.indexOf("<46>") > -1)
/*     */     {
/* 977 */       if (!evo.isCCMappingEntriesAllItemsLoaded())
/*     */       {
/* 979 */         evo.setCCMappingEntries(getCcMappingEntryDAO().getAll(evo.getCcMappingLineId(), dependants, evo.getCCMappingEntries()));
/*     */ 
/* 986 */         evo.setCCMappingEntriesAllItemsLoaded(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.cc.CcMappingLineDAO
 * JD-Core Version:    0.6.0
 */