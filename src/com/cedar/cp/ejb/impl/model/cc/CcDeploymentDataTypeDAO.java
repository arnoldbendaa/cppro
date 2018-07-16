/*     */ package com.cedar.cp.ejb.impl.model.cc;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentDataTypeCK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentDataTypePK;
/*     */ import com.cedar.cp.dto.model.cc.CcDeploymentDataTypeRefImpl;
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
/*     */ public class CcDeploymentDataTypeDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_DATA_TYPE_ID,CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_DATA_TYPE.DATA_TYPE_ID";
/*     */   protected static final String SQL_LOAD = " from CC_DEPLOYMENT_DATA_TYPE where    CC_DEPLOYMENT_DATA_TYPE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into CC_DEPLOYMENT_DATA_TYPE ( CC_DEPLOYMENT_DATA_TYPE_ID,CC_DEPLOYMENT_LINE_ID,DATA_TYPE_ID) values ( ?,?,?)";
/*     */   protected static final String SQL_STORE = "update CC_DEPLOYMENT_DATA_TYPE set CC_DEPLOYMENT_LINE_ID = ?,DATA_TYPE_ID = ? where    CC_DEPLOYMENT_DATA_TYPE_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from CC_DEPLOYMENT_DATA_TYPE where    CC_DEPLOYMENT_DATA_TYPE_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from CC_DEPLOYMENT_DATA_TYPE,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID ,CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_DATA_TYPE_ID";
/*     */   protected static final String SQL_GET_ALL = " from CC_DEPLOYMENT_DATA_TYPE where    CC_DEPLOYMENT_LINE_ID = ? ";
/*     */   protected CcDeploymentDataTypeEVO mDetails;
/*     */ 
/*     */   public CcDeploymentDataTypeDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public CcDeploymentDataTypeDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CcDeploymentDataTypeDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected CcDeploymentDataTypePK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(CcDeploymentDataTypeEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private CcDeploymentDataTypeEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  89 */     int col = 1;
/*  90 */     CcDeploymentDataTypeEVO evo = new CcDeploymentDataTypeEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  96 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(CcDeploymentDataTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 101 */     int col = startCol_;
/* 102 */     stmt_.setInt(col++, evo_.getCcDeploymentDataTypeId());
/* 103 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(CcDeploymentDataTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 108 */     int col = startCol_;
/* 109 */     stmt_.setInt(col++, evo_.getCcDeploymentLineId());
/* 110 */     stmt_.setInt(col++, evo_.getDataTypeId());
/* 111 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(CcDeploymentDataTypePK pk)
/*     */     throws ValidationException
/*     */   {
/* 127 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 129 */     PreparedStatement stmt = null;
/* 130 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 134 */       stmt = getConnection().prepareStatement("select CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_DATA_TYPE_ID,CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_DATA_TYPE.DATA_TYPE_ID from CC_DEPLOYMENT_DATA_TYPE where    CC_DEPLOYMENT_DATA_TYPE_ID = ? ");
/*     */ 
/* 137 */       int col = 1;
/* 138 */       stmt.setInt(col++, pk.getCcDeploymentDataTypeId());
/*     */ 
/* 140 */       resultSet = stmt.executeQuery();
/*     */ 
/* 142 */       if (!resultSet.next()) {
/* 143 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 146 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 147 */       if (this.mDetails.isModified())
/* 148 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 152 */       throw handleSQLException(pk, "select CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_DATA_TYPE_ID,CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_DATA_TYPE.DATA_TYPE_ID from CC_DEPLOYMENT_DATA_TYPE where    CC_DEPLOYMENT_DATA_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 156 */       closeResultSet(resultSet);
/* 157 */       closeStatement(stmt);
/* 158 */       closeConnection();
/*     */ 
/* 160 */       if (timer != null)
/* 161 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 184 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 185 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 189 */       stmt = getConnection().prepareStatement("insert into CC_DEPLOYMENT_DATA_TYPE ( CC_DEPLOYMENT_DATA_TYPE_ID,CC_DEPLOYMENT_LINE_ID,DATA_TYPE_ID) values ( ?,?,?)");
/*     */ 
/* 192 */       int col = 1;
/* 193 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 194 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 197 */       int resultCount = stmt.executeUpdate();
/* 198 */       if (resultCount != 1)
/*     */       {
/* 200 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 203 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 207 */       throw handleSQLException(this.mDetails.getPK(), "insert into CC_DEPLOYMENT_DATA_TYPE ( CC_DEPLOYMENT_DATA_TYPE_ID,CC_DEPLOYMENT_LINE_ID,DATA_TYPE_ID) values ( ?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 211 */       closeStatement(stmt);
/* 212 */       closeConnection();
/*     */ 
/* 214 */       if (timer != null)
/* 215 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 236 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 240 */     PreparedStatement stmt = null;
/*     */ 
/* 242 */     boolean mainChanged = this.mDetails.isModified();
/* 243 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 246 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 248 */         stmt = getConnection().prepareStatement("update CC_DEPLOYMENT_DATA_TYPE set CC_DEPLOYMENT_LINE_ID = ?,DATA_TYPE_ID = ? where    CC_DEPLOYMENT_DATA_TYPE_ID = ? ");
/*     */ 
/* 251 */         int col = 1;
/* 252 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 253 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 256 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 259 */         if (resultCount != 1) {
/* 260 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 263 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 272 */       throw handleSQLException(getPK(), "update CC_DEPLOYMENT_DATA_TYPE set CC_DEPLOYMENT_LINE_ID = ?,DATA_TYPE_ID = ? where    CC_DEPLOYMENT_DATA_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 276 */       closeStatement(stmt);
/* 277 */       closeConnection();
/*     */ 
/* 279 */       if ((timer != null) && (
/* 280 */         (mainChanged) || (dependantChanged)))
/* 281 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 300 */     if (items == null) {
/* 301 */       return false;
/*     */     }
/* 303 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 304 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 306 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 311 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 312 */       while (iter2.hasNext())
/*     */       {
/* 314 */         this.mDetails = ((CcDeploymentDataTypeEVO)iter2.next());
/*     */ 
/* 317 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 319 */         somethingChanged = true;
/*     */ 
/* 322 */         if (deleteStmt == null) {
/* 323 */           deleteStmt = getConnection().prepareStatement("delete from CC_DEPLOYMENT_DATA_TYPE where    CC_DEPLOYMENT_DATA_TYPE_ID = ? ");
/*     */         }
/*     */ 
/* 326 */         int col = 1;
/* 327 */         deleteStmt.setInt(col++, this.mDetails.getCcDeploymentDataTypeId());
/*     */ 
/* 329 */         if (this._log.isDebugEnabled()) {
/* 330 */           this._log.debug("update", "CcDeploymentDataType deleting CcDeploymentDataTypeId=" + this.mDetails.getCcDeploymentDataTypeId());
/*     */         }
/*     */ 
/* 335 */         deleteStmt.addBatch();
/*     */ 
/* 338 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 343 */       if (deleteStmt != null)
/*     */       {
/* 345 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 347 */         deleteStmt.executeBatch();
/*     */ 
/* 349 */         if (timer2 != null) {
/* 350 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 354 */       Iterator iter1 = items.values().iterator();
/* 355 */       while (iter1.hasNext())
/*     */       {
/* 357 */         this.mDetails = ((CcDeploymentDataTypeEVO)iter1.next());
/*     */ 
/* 359 */         if (this.mDetails.insertPending())
/*     */         {
/* 361 */           somethingChanged = true;
/* 362 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 365 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 367 */         somethingChanged = true;
/* 368 */         doStore();
/*     */       }
/*     */ 
/* 379 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 383 */       throw handleSQLException("delete from CC_DEPLOYMENT_DATA_TYPE where    CC_DEPLOYMENT_DATA_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 387 */       if (deleteStmt != null)
/*     */       {
/* 389 */         closeStatement(deleteStmt);
/* 390 */         closeConnection();
/*     */       }
/*     */ 
/* 393 */       this.mDetails = null;
/*     */ 
/* 395 */       if ((somethingChanged) && 
/* 396 */         (timer != null))
/* 397 */         timer.logDebug("update", "collection"); 
/* 397 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 423 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 425 */     PreparedStatement stmt = null;
/* 426 */     ResultSet resultSet = null;
/*     */ 
/* 428 */     int itemCount = 0;
/*     */ 
/* 430 */     CcDeploymentLineEVO owningEVO = null;
/* 431 */     Iterator ownersIter = owners.iterator();
/* 432 */     while (ownersIter.hasNext())
/*     */     {
/* 434 */       owningEVO = (CcDeploymentLineEVO)ownersIter.next();
/* 435 */       owningEVO.setCCDeploymentDataTypesAllItemsLoaded(true);
/*     */     }
/* 437 */     ownersIter = owners.iterator();
/* 438 */     owningEVO = (CcDeploymentLineEVO)ownersIter.next();
/* 439 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 443 */       stmt = getConnection().prepareStatement("select CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_DATA_TYPE_ID,CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_DATA_TYPE.DATA_TYPE_ID from CC_DEPLOYMENT_DATA_TYPE,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID ,CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_DATA_TYPE_ID");
/*     */ 
/* 445 */       int col = 1;
/* 446 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 448 */       resultSet = stmt.executeQuery();
/*     */ 
/* 451 */       while (resultSet.next())
/*     */       {
/* 453 */         itemCount++;
/* 454 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 459 */         while (this.mDetails.getCcDeploymentLineId() != owningEVO.getCcDeploymentLineId())
/*     */         {
/* 463 */           if (!ownersIter.hasNext())
/*     */           {
/* 465 */             this._log.debug("bulkGetAll", "can't find owning [CcDeploymentLineId=" + this.mDetails.getCcDeploymentLineId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 469 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 470 */             ownersIter = owners.iterator();
/* 471 */             while (ownersIter.hasNext())
/*     */             {
/* 473 */               owningEVO = (CcDeploymentLineEVO)ownersIter.next();
/* 474 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 476 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 478 */           owningEVO = (CcDeploymentLineEVO)ownersIter.next();
/*     */         }
/* 480 */         if (owningEVO.getCCDeploymentDataTypes() == null)
/*     */         {
/* 482 */           theseItems = new ArrayList();
/* 483 */           owningEVO.setCCDeploymentDataTypes(theseItems);
/* 484 */           owningEVO.setCCDeploymentDataTypesAllItemsLoaded(true);
/*     */         }
/* 486 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 489 */       if (timer != null) {
/* 490 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 495 */       throw handleSQLException("select CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_DATA_TYPE_ID,CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_DATA_TYPE.DATA_TYPE_ID from CC_DEPLOYMENT_DATA_TYPE,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID ,CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_DATA_TYPE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 499 */       closeResultSet(resultSet);
/* 500 */       closeStatement(stmt);
/* 501 */       closeConnection();
/*     */ 
/* 503 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectCcDeploymentLineId, String dependants, Collection currentList)
/*     */   {
/* 528 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 529 */     PreparedStatement stmt = null;
/* 530 */     ResultSet resultSet = null;
/*     */ 
/* 532 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 536 */       stmt = getConnection().prepareStatement("select CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_DATA_TYPE_ID,CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_DATA_TYPE.DATA_TYPE_ID from CC_DEPLOYMENT_DATA_TYPE where    CC_DEPLOYMENT_LINE_ID = ? ");
/*     */ 
/* 538 */       int col = 1;
/* 539 */       stmt.setInt(col++, selectCcDeploymentLineId);
/*     */ 
/* 541 */       resultSet = stmt.executeQuery();
/*     */ 
/* 543 */       while (resultSet.next())
/*     */       {
/* 545 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 548 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 551 */       if (currentList != null)
/*     */       {
/* 554 */         ListIterator iter = items.listIterator();
/* 555 */         CcDeploymentDataTypeEVO currentEVO = null;
/* 556 */         CcDeploymentDataTypeEVO newEVO = null;
/* 557 */         while (iter.hasNext())
/*     */         {
/* 559 */           newEVO = (CcDeploymentDataTypeEVO)iter.next();
/* 560 */           Iterator iter2 = currentList.iterator();
/* 561 */           while (iter2.hasNext())
/*     */           {
/* 563 */             currentEVO = (CcDeploymentDataTypeEVO)iter2.next();
/* 564 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 566 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 572 */         Iterator iter2 = currentList.iterator();
/* 573 */         while (iter2.hasNext())
/*     */         {
/* 575 */           currentEVO = (CcDeploymentDataTypeEVO)iter2.next();
/* 576 */           if (currentEVO.insertPending()) {
/* 577 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 581 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 585 */       throw handleSQLException("select CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_DATA_TYPE_ID,CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_DATA_TYPE.DATA_TYPE_ID from CC_DEPLOYMENT_DATA_TYPE where    CC_DEPLOYMENT_LINE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 589 */       closeResultSet(resultSet);
/* 590 */       closeStatement(stmt);
/* 591 */       closeConnection();
/*     */ 
/* 593 */       if (timer != null) {
/* 594 */         timer.logDebug("getAll", " CcDeploymentLineId=" + selectCcDeploymentLineId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 599 */     return items;
/*     */   }
/*     */ 
/*     */   public CcDeploymentDataTypeEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 613 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 616 */     if (this.mDetails == null) {
/* 617 */       doLoad(((CcDeploymentDataTypeCK)paramCK).getCcDeploymentDataTypePK());
/*     */     }
/* 619 */     else if (!this.mDetails.getPK().equals(((CcDeploymentDataTypeCK)paramCK).getCcDeploymentDataTypePK())) {
/* 620 */       doLoad(((CcDeploymentDataTypeCK)paramCK).getCcDeploymentDataTypePK());
/*     */     }
/*     */ 
/* 623 */     CcDeploymentDataTypeEVO details = new CcDeploymentDataTypeEVO();
/* 624 */     details = this.mDetails.deepClone();
/*     */ 
/* 626 */     if (timer != null) {
/* 627 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 629 */     return details;
/*     */   }
/*     */ 
/*     */   public CcDeploymentDataTypeEVO getDetails(ModelCK paramCK, CcDeploymentDataTypeEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 635 */     CcDeploymentDataTypeEVO savedEVO = this.mDetails;
/* 636 */     this.mDetails = paramEVO;
/* 637 */     CcDeploymentDataTypeEVO newEVO = getDetails(paramCK, dependants);
/* 638 */     this.mDetails = savedEVO;
/* 639 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public CcDeploymentDataTypeEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 645 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 649 */     CcDeploymentDataTypeEVO details = this.mDetails.deepClone();
/*     */ 
/* 651 */     if (timer != null) {
/* 652 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 654 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 659 */     return "CcDeploymentDataType";
/*     */   }
/*     */ 
/*     */   public CcDeploymentDataTypeRefImpl getRef(CcDeploymentDataTypePK paramCcDeploymentDataTypePK)
/*     */   {
/* 664 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 665 */     PreparedStatement stmt = null;
/* 666 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 669 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID from CC_DEPLOYMENT_DATA_TYPE,MODEL,CC_DEPLOYMENT,CC_DEPLOYMENT_LINE where 1=1 and CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_DATA_TYPE_ID = ? and CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT.CC_DEPLOYMENT_ID = MODEL.CC_DEPLOYMENT_ID");
/* 670 */       int col = 1;
/* 671 */       stmt.setInt(col++, paramCcDeploymentDataTypePK.getCcDeploymentDataTypeId());
/*     */ 
/* 673 */       resultSet = stmt.executeQuery();
/*     */ 
/* 675 */       if (!resultSet.next()) {
/* 676 */         throw new RuntimeException(getEntityName() + " getRef " + paramCcDeploymentDataTypePK + " not found");
/*     */       }
/* 678 */       col = 2;
/* 679 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 683 */       CcDeploymentPK newCcDeploymentPK = new CcDeploymentPK(resultSet.getInt(col++));
/*     */ 
/* 687 */       CcDeploymentLinePK newCcDeploymentLinePK = new CcDeploymentLinePK(resultSet.getInt(col++));
/*     */ 
/* 691 */       String textCcDeploymentDataType = "";
/* 692 */       CcDeploymentDataTypeCK ckCcDeploymentDataType = new CcDeploymentDataTypeCK(newModelPK, newCcDeploymentPK, newCcDeploymentLinePK, paramCcDeploymentDataTypePK);
/*     */ 
/* 699 */       CcDeploymentDataTypeRefImpl localCcDeploymentDataTypeRefImpl = new CcDeploymentDataTypeRefImpl(ckCcDeploymentDataType, textCcDeploymentDataType);
/*     */       return localCcDeploymentDataTypeRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 704 */       throw handleSQLException(paramCcDeploymentDataTypePK, "select 0,MODEL.MODEL_ID,CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID from CC_DEPLOYMENT_DATA_TYPE,MODEL,CC_DEPLOYMENT,CC_DEPLOYMENT_LINE where 1=1 and CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_DATA_TYPE_ID = ? and CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT.CC_DEPLOYMENT_ID = MODEL.CC_DEPLOYMENT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 708 */       closeResultSet(resultSet);
/* 709 */       closeStatement(stmt);
/* 710 */       closeConnection();
/*     */ 
/* 712 */       if (timer != null)
/* 713 */         timer.logDebug("getRef", paramCcDeploymentDataTypePK); 
/* 713 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.cc.CcDeploymentDataTypeDAO
 * JD-Core Version:    0.6.0
 */