/*     */ package com.cedar.cp.ejb.impl.cubeformula;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtCK;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtPK;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtRefImpl;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentLinePK;
/*     */ import com.cedar.cp.dto.model.FinanceCubePK;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
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
/*     */ public class FormulaDeploymentDtDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_DT_ID,FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_DT.DATA_TYPE_ID";
/*     */   protected static final String SQL_LOAD = " from FORMULA_DEPLOYMENT_DT where    FORMULA_DEPLOYMENT_DT_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into FORMULA_DEPLOYMENT_DT ( FORMULA_DEPLOYMENT_DT_ID,FORMULA_DEPLOYMENT_LINE_ID,DATA_TYPE_ID) values ( ?,?,?)";
/*     */   protected static final String SQL_STORE = "update FORMULA_DEPLOYMENT_DT set FORMULA_DEPLOYMENT_LINE_ID = ?,DATA_TYPE_ID = ? where    FORMULA_DEPLOYMENT_DT_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from FORMULA_DEPLOYMENT_DT where    FORMULA_DEPLOYMENT_DT_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from FORMULA_DEPLOYMENT_DT,FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA,FINANCE_CUBE where 1=1 and FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID ,FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_DT_ID";
/*     */   protected static final String SQL_GET_ALL = " from FORMULA_DEPLOYMENT_DT where    FORMULA_DEPLOYMENT_LINE_ID = ? ";
/*     */   protected FormulaDeploymentDtEVO mDetails;
/*     */ 
/*     */   public FormulaDeploymentDtDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentDtDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentDtDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected FormulaDeploymentDtPK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(FormulaDeploymentDtEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private FormulaDeploymentDtEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  89 */     int col = 1;
/*  90 */     FormulaDeploymentDtEVO evo = new FormulaDeploymentDtEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  96 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(FormulaDeploymentDtEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 101 */     int col = startCol_;
/* 102 */     stmt_.setInt(col++, evo_.getFormulaDeploymentDtId());
/* 103 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(FormulaDeploymentDtEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 108 */     int col = startCol_;
/* 109 */     stmt_.setInt(col++, evo_.getFormulaDeploymentLineId());
/* 110 */     stmt_.setInt(col++, evo_.getDataTypeId());
/* 111 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(FormulaDeploymentDtPK pk)
/*     */     throws ValidationException
/*     */   {
/* 127 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 129 */     PreparedStatement stmt = null;
/* 130 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 134 */       stmt = getConnection().prepareStatement("select FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_DT_ID,FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_DT.DATA_TYPE_ID from FORMULA_DEPLOYMENT_DT where    FORMULA_DEPLOYMENT_DT_ID = ? ");
/*     */ 
/* 137 */       int col = 1;
/* 138 */       stmt.setInt(col++, pk.getFormulaDeploymentDtId());
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
/* 152 */       throw handleSQLException(pk, "select FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_DT_ID,FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_DT.DATA_TYPE_ID from FORMULA_DEPLOYMENT_DT where    FORMULA_DEPLOYMENT_DT_ID = ? ", sqle);
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
/* 189 */       stmt = getConnection().prepareStatement("insert into FORMULA_DEPLOYMENT_DT ( FORMULA_DEPLOYMENT_DT_ID,FORMULA_DEPLOYMENT_LINE_ID,DATA_TYPE_ID) values ( ?,?,?)");
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
/* 207 */       throw handleSQLException(this.mDetails.getPK(), "insert into FORMULA_DEPLOYMENT_DT ( FORMULA_DEPLOYMENT_DT_ID,FORMULA_DEPLOYMENT_LINE_ID,DATA_TYPE_ID) values ( ?,?,?)", sqle);
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
/* 248 */         stmt = getConnection().prepareStatement("update FORMULA_DEPLOYMENT_DT set FORMULA_DEPLOYMENT_LINE_ID = ?,DATA_TYPE_ID = ? where    FORMULA_DEPLOYMENT_DT_ID = ? ");
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
/* 272 */       throw handleSQLException(getPK(), "update FORMULA_DEPLOYMENT_DT set FORMULA_DEPLOYMENT_LINE_ID = ?,DATA_TYPE_ID = ? where    FORMULA_DEPLOYMENT_DT_ID = ? ", sqle);
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
/* 314 */         this.mDetails = ((FormulaDeploymentDtEVO)iter2.next());
/*     */ 
/* 317 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 319 */         somethingChanged = true;
/*     */ 
/* 322 */         if (deleteStmt == null) {
/* 323 */           deleteStmt = getConnection().prepareStatement("delete from FORMULA_DEPLOYMENT_DT where    FORMULA_DEPLOYMENT_DT_ID = ? ");
/*     */         }
/*     */ 
/* 326 */         int col = 1;
/* 327 */         deleteStmt.setInt(col++, this.mDetails.getFormulaDeploymentDtId());
/*     */ 
/* 329 */         if (this._log.isDebugEnabled()) {
/* 330 */           this._log.debug("update", "FormulaDeploymentDt deleting FormulaDeploymentDtId=" + this.mDetails.getFormulaDeploymentDtId());
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
/* 357 */         this.mDetails = ((FormulaDeploymentDtEVO)iter1.next());
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
/* 383 */       throw handleSQLException("delete from FORMULA_DEPLOYMENT_DT where    FORMULA_DEPLOYMENT_DT_ID = ? ", sqle);
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
/* 426 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 428 */     PreparedStatement stmt = null;
/* 429 */     ResultSet resultSet = null;
/*     */ 
/* 431 */     int itemCount = 0;
/*     */ 
/* 433 */     FormulaDeploymentLineEVO owningEVO = null;
/* 434 */     Iterator ownersIter = owners.iterator();
/* 435 */     while (ownersIter.hasNext())
/*     */     {
/* 437 */       owningEVO = (FormulaDeploymentLineEVO)ownersIter.next();
/* 438 */       owningEVO.setDeploymentDataTypesAllItemsLoaded(true);
/*     */     }
/* 440 */     ownersIter = owners.iterator();
/* 441 */     owningEVO = (FormulaDeploymentLineEVO)ownersIter.next();
/* 442 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 446 */       stmt = getConnection().prepareStatement("select FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_DT_ID,FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_DT.DATA_TYPE_ID from FORMULA_DEPLOYMENT_DT,FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA,FINANCE_CUBE where 1=1 and FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID ,FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_DT_ID");
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
/* 462 */         while (this.mDetails.getFormulaDeploymentLineId() != owningEVO.getFormulaDeploymentLineId())
/*     */         {
/* 466 */           if (!ownersIter.hasNext())
/*     */           {
/* 468 */             this._log.debug("bulkGetAll", "can't find owning [FormulaDeploymentLineId=" + this.mDetails.getFormulaDeploymentLineId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 472 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 473 */             ownersIter = owners.iterator();
/* 474 */             while (ownersIter.hasNext())
/*     */             {
/* 476 */               owningEVO = (FormulaDeploymentLineEVO)ownersIter.next();
/* 477 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 479 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 481 */           owningEVO = (FormulaDeploymentLineEVO)ownersIter.next();
/*     */         }
/* 483 */         if (owningEVO.getDeploymentDataTypes() == null)
/*     */         {
/* 485 */           theseItems = new ArrayList();
/* 486 */           owningEVO.setDeploymentDataTypes(theseItems);
/* 487 */           owningEVO.setDeploymentDataTypesAllItemsLoaded(true);
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
/* 498 */       throw handleSQLException("select FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_DT_ID,FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_DT.DATA_TYPE_ID from FORMULA_DEPLOYMENT_DT,FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA,FINANCE_CUBE where 1=1 and FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID ,FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_DT_ID", sqle);
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
/*     */   public Collection getAll(int selectFormulaDeploymentLineId, String dependants, Collection currentList)
/*     */   {
/* 531 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 532 */     PreparedStatement stmt = null;
/* 533 */     ResultSet resultSet = null;
/*     */ 
/* 535 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 539 */       stmt = getConnection().prepareStatement("select FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_DT_ID,FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_DT.DATA_TYPE_ID from FORMULA_DEPLOYMENT_DT where    FORMULA_DEPLOYMENT_LINE_ID = ? ");
/*     */ 
/* 541 */       int col = 1;
/* 542 */       stmt.setInt(col++, selectFormulaDeploymentLineId);
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
/* 558 */         FormulaDeploymentDtEVO currentEVO = null;
/* 559 */         FormulaDeploymentDtEVO newEVO = null;
/* 560 */         while (iter.hasNext())
/*     */         {
/* 562 */           newEVO = (FormulaDeploymentDtEVO)iter.next();
/* 563 */           Iterator iter2 = currentList.iterator();
/* 564 */           while (iter2.hasNext())
/*     */           {
/* 566 */             currentEVO = (FormulaDeploymentDtEVO)iter2.next();
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
/* 578 */           currentEVO = (FormulaDeploymentDtEVO)iter2.next();
/* 579 */           if (currentEVO.insertPending()) {
/* 580 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 584 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 588 */       throw handleSQLException("select FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_DT_ID,FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_DT.DATA_TYPE_ID from FORMULA_DEPLOYMENT_DT where    FORMULA_DEPLOYMENT_LINE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 592 */       closeResultSet(resultSet);
/* 593 */       closeStatement(stmt);
/* 594 */       closeConnection();
/*     */ 
/* 596 */       if (timer != null) {
/* 597 */         timer.logDebug("getAll", " FormulaDeploymentLineId=" + selectFormulaDeploymentLineId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 602 */     return items;
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentDtEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 616 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 619 */     if (this.mDetails == null) {
/* 620 */       doLoad(((FormulaDeploymentDtCK)paramCK).getFormulaDeploymentDtPK());
/*     */     }
/* 622 */     else if (!this.mDetails.getPK().equals(((FormulaDeploymentDtCK)paramCK).getFormulaDeploymentDtPK())) {
/* 623 */       doLoad(((FormulaDeploymentDtCK)paramCK).getFormulaDeploymentDtPK());
/*     */     }
/*     */ 
/* 626 */     FormulaDeploymentDtEVO details = new FormulaDeploymentDtEVO();
/* 627 */     details = this.mDetails.deepClone();
/*     */ 
/* 629 */     if (timer != null) {
/* 630 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 632 */     return details;
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentDtEVO getDetails(ModelCK paramCK, FormulaDeploymentDtEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 638 */     FormulaDeploymentDtEVO savedEVO = this.mDetails;
/* 639 */     this.mDetails = paramEVO;
/* 640 */     FormulaDeploymentDtEVO newEVO = getDetails(paramCK, dependants);
/* 641 */     this.mDetails = savedEVO;
/* 642 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentDtEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 648 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 652 */     FormulaDeploymentDtEVO details = this.mDetails.deepClone();
/*     */ 
/* 654 */     if (timer != null) {
/* 655 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 657 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 662 */     return "FormulaDeploymentDt";
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentDtRefImpl getRef(FormulaDeploymentDtPK paramFormulaDeploymentDtPK)
/*     */   {
/* 667 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 668 */     PreparedStatement stmt = null;
/* 669 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 672 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID,CUBE_FORMULA.CUBE_FORMULA_ID,FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID from FORMULA_DEPLOYMENT_DT,MODEL,FINANCE_CUBE,CUBE_FORMULA,FORMULA_DEPLOYMENT_LINE where 1=1 and FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_DT_ID = ? and FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID = CUBE_FORMULA.FORMULA_DEPLOYMENT_LINE_ID and CUBE_FORMULA.CUBE_FORMULA_ID = FINANCE_CUBE.CUBE_FORMULA_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID");
/* 673 */       int col = 1;
/* 674 */       stmt.setInt(col++, paramFormulaDeploymentDtPK.getFormulaDeploymentDtId());
/*     */ 
/* 676 */       resultSet = stmt.executeQuery();
/*     */ 
/* 678 */       if (!resultSet.next()) {
/* 679 */         throw new RuntimeException(getEntityName() + " getRef " + paramFormulaDeploymentDtPK + " not found");
/*     */       }
/* 681 */       col = 2;
/* 682 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 686 */       FinanceCubePK newFinanceCubePK = new FinanceCubePK(resultSet.getInt(col++));
/*     */ 
/* 690 */       CubeFormulaPK newCubeFormulaPK = new CubeFormulaPK(resultSet.getInt(col++));
/*     */ 
/* 694 */       FormulaDeploymentLinePK newFormulaDeploymentLinePK = new FormulaDeploymentLinePK(resultSet.getInt(col++));
/*     */ 
/* 698 */       String textFormulaDeploymentDt = "";
/* 699 */       FormulaDeploymentDtCK ckFormulaDeploymentDt = new FormulaDeploymentDtCK(newModelPK, newFinanceCubePK, newCubeFormulaPK, newFormulaDeploymentLinePK, paramFormulaDeploymentDtPK);
/*     */ 
/* 707 */       FormulaDeploymentDtRefImpl localFormulaDeploymentDtRefImpl = new FormulaDeploymentDtRefImpl(ckFormulaDeploymentDt, textFormulaDeploymentDt);
/*     */       return localFormulaDeploymentDtRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 712 */       throw handleSQLException(paramFormulaDeploymentDtPK, "select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID,CUBE_FORMULA.CUBE_FORMULA_ID,FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID from FORMULA_DEPLOYMENT_DT,MODEL,FINANCE_CUBE,CUBE_FORMULA,FORMULA_DEPLOYMENT_LINE where 1=1 and FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_DT_ID = ? and FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID = CUBE_FORMULA.FORMULA_DEPLOYMENT_LINE_ID and CUBE_FORMULA.CUBE_FORMULA_ID = FINANCE_CUBE.CUBE_FORMULA_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 716 */       closeResultSet(resultSet);
/* 717 */       closeStatement(stmt);
/* 718 */       closeConnection();
/*     */ 
/* 720 */       if (timer != null)
/* 721 */         timer.logDebug("getRef", paramFormulaDeploymentDtPK); 
/* 721 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentDtDAO
 * JD-Core Version:    0.6.0
 */