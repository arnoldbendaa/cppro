/*     */ package com.cedar.cp.ejb.impl.cubeformula;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.EntityList;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.base.EntityListImpl;
/*     */ import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryCK;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryPK;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryRefImpl;
/*     */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentLinePK;
/*     */ import com.cedar.cp.dto.model.FinanceCubePK;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.SqlBuilder;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import com.cedar.cp.util.common.JdbcUtils;
/*     */ import com.cedar.cp.util.common.JdbcUtils.ColType;
/*     */ import com.cedar.cp.util.common.JdbcUtils.CubeFormulaRefColType;
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
/*     */ public class FormulaDeploymentEntryDAO extends AbstractDAO
/*     */ {
/*  37 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_ENTRY_ID,FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_ENTRY.DIM_SEQ,FORMULA_DEPLOYMENT_ENTRY.STRUCTURE_ID,FORMULA_DEPLOYMENT_ENTRY.START_SE_ID,FORMULA_DEPLOYMENT_ENTRY.SELECTED_IND,FORMULA_DEPLOYMENT_ENTRY.END_SE_ID";
/*     */   protected static final String SQL_LOAD = " from FORMULA_DEPLOYMENT_ENTRY where    FORMULA_DEPLOYMENT_ENTRY_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into FORMULA_DEPLOYMENT_ENTRY ( FORMULA_DEPLOYMENT_ENTRY_ID,FORMULA_DEPLOYMENT_LINE_ID,DIM_SEQ,STRUCTURE_ID,START_SE_ID,SELECTED_IND,END_SE_ID) values ( ?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update FORMULA_DEPLOYMENT_ENTRY set FORMULA_DEPLOYMENT_LINE_ID = ?,DIM_SEQ = ?,STRUCTURE_ID = ?,START_SE_ID = ?,SELECTED_IND = ?,END_SE_ID = ? where    FORMULA_DEPLOYMENT_ENTRY_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from FORMULA_DEPLOYMENT_ENTRY where    FORMULA_DEPLOYMENT_ENTRY_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from FORMULA_DEPLOYMENT_ENTRY,FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA,FINANCE_CUBE where 1=1 and FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID ,FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_ENTRY_ID";
/*     */   protected static final String SQL_GET_ALL = " from FORMULA_DEPLOYMENT_ENTRY where    FORMULA_DEPLOYMENT_LINE_ID = ? ";
/*     */   protected FormulaDeploymentEntryEVO mDetails;
/*     */ 
/*     */   public FormulaDeploymentEntryDAO(Connection connection)
/*     */   {
/*  44 */     super(connection);
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentEntryDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentEntryDAO(DataSource ds)
/*     */   {
/*  60 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected FormulaDeploymentEntryPK getPK()
/*     */   {
/*  68 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(FormulaDeploymentEntryEVO details)
/*     */   {
/*  77 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private FormulaDeploymentEntryEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  95 */     int col = 1;
/*  96 */     FormulaDeploymentEntryEVO evo = new FormulaDeploymentEntryEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), getWrappedIntegerFromJdbc(resultSet_, col++));
/*     */ 
/* 106 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(FormulaDeploymentEntryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 111 */     int col = startCol_;
/* 112 */     stmt_.setInt(col++, evo_.getFormulaDeploymentEntryId());
/* 113 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(FormulaDeploymentEntryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 118 */     int col = startCol_;
/* 119 */     stmt_.setInt(col++, evo_.getFormulaDeploymentLineId());
/* 120 */     stmt_.setInt(col++, evo_.getDimSeq());
/* 121 */     stmt_.setInt(col++, evo_.getStructureId());
/* 122 */     stmt_.setInt(col++, evo_.getStartSeId());
/* 123 */     if (evo_.getSelectedInd())
/* 124 */       stmt_.setString(col++, "Y");
/*     */     else
/* 126 */       stmt_.setString(col++, " ");
/* 127 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getEndSeId());
/* 128 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(FormulaDeploymentEntryPK pk)
/*     */     throws ValidationException
/*     */   {
/* 144 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 146 */     PreparedStatement stmt = null;
/* 147 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 151 */       stmt = getConnection().prepareStatement("select FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_ENTRY_ID,FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_ENTRY.DIM_SEQ,FORMULA_DEPLOYMENT_ENTRY.STRUCTURE_ID,FORMULA_DEPLOYMENT_ENTRY.START_SE_ID,FORMULA_DEPLOYMENT_ENTRY.SELECTED_IND,FORMULA_DEPLOYMENT_ENTRY.END_SE_ID from FORMULA_DEPLOYMENT_ENTRY where    FORMULA_DEPLOYMENT_ENTRY_ID = ? ");
/*     */ 
/* 154 */       int col = 1;
/* 155 */       stmt.setInt(col++, pk.getFormulaDeploymentEntryId());
/*     */ 
/* 157 */       resultSet = stmt.executeQuery();
/*     */ 
/* 159 */       if (!resultSet.next()) {
/* 160 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 163 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 164 */       if (this.mDetails.isModified())
/* 165 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 169 */       throw handleSQLException(pk, "select FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_ENTRY_ID,FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_ENTRY.DIM_SEQ,FORMULA_DEPLOYMENT_ENTRY.STRUCTURE_ID,FORMULA_DEPLOYMENT_ENTRY.START_SE_ID,FORMULA_DEPLOYMENT_ENTRY.SELECTED_IND,FORMULA_DEPLOYMENT_ENTRY.END_SE_ID from FORMULA_DEPLOYMENT_ENTRY where    FORMULA_DEPLOYMENT_ENTRY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 173 */       closeResultSet(resultSet);
/* 174 */       closeStatement(stmt);
/* 175 */       closeConnection();
/*     */ 
/* 177 */       if (timer != null)
/* 178 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 209 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 210 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 214 */       stmt = getConnection().prepareStatement("insert into FORMULA_DEPLOYMENT_ENTRY ( FORMULA_DEPLOYMENT_ENTRY_ID,FORMULA_DEPLOYMENT_LINE_ID,DIM_SEQ,STRUCTURE_ID,START_SE_ID,SELECTED_IND,END_SE_ID) values ( ?,?,?,?,?,?,?)");
/*     */ 
/* 217 */       int col = 1;
/* 218 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 219 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 222 */       int resultCount = stmt.executeUpdate();
/* 223 */       if (resultCount != 1)
/*     */       {
/* 225 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 228 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 232 */       throw handleSQLException(this.mDetails.getPK(), "insert into FORMULA_DEPLOYMENT_ENTRY ( FORMULA_DEPLOYMENT_ENTRY_ID,FORMULA_DEPLOYMENT_LINE_ID,DIM_SEQ,STRUCTURE_ID,START_SE_ID,SELECTED_IND,END_SE_ID) values ( ?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 236 */       closeStatement(stmt);
/* 237 */       closeConnection();
/*     */ 
/* 239 */       if (timer != null)
/* 240 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 265 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 269 */     PreparedStatement stmt = null;
/*     */ 
/* 271 */     boolean mainChanged = this.mDetails.isModified();
/* 272 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 275 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 277 */         stmt = getConnection().prepareStatement("update FORMULA_DEPLOYMENT_ENTRY set FORMULA_DEPLOYMENT_LINE_ID = ?,DIM_SEQ = ?,STRUCTURE_ID = ?,START_SE_ID = ?,SELECTED_IND = ?,END_SE_ID = ? where    FORMULA_DEPLOYMENT_ENTRY_ID = ? ");
/*     */ 
/* 280 */         int col = 1;
/* 281 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 282 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 285 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 288 */         if (resultCount != 1) {
/* 289 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 292 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 301 */       throw handleSQLException(getPK(), "update FORMULA_DEPLOYMENT_ENTRY set FORMULA_DEPLOYMENT_LINE_ID = ?,DIM_SEQ = ?,STRUCTURE_ID = ?,START_SE_ID = ?,SELECTED_IND = ?,END_SE_ID = ? where    FORMULA_DEPLOYMENT_ENTRY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 305 */       closeStatement(stmt);
/* 306 */       closeConnection();
/*     */ 
/* 308 */       if ((timer != null) && (
/* 309 */         (mainChanged) || (dependantChanged)))
/* 310 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 329 */     if (items == null) {
/* 330 */       return false;
/*     */     }
/* 332 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 333 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 335 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 340 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 341 */       while (iter2.hasNext())
/*     */       {
/* 343 */         this.mDetails = ((FormulaDeploymentEntryEVO)iter2.next());
/*     */ 
/* 346 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 348 */         somethingChanged = true;
/*     */ 
/* 351 */         if (deleteStmt == null) {
/* 352 */           deleteStmt = getConnection().prepareStatement("delete from FORMULA_DEPLOYMENT_ENTRY where    FORMULA_DEPLOYMENT_ENTRY_ID = ? ");
/*     */         }
/*     */ 
/* 355 */         int col = 1;
/* 356 */         deleteStmt.setInt(col++, this.mDetails.getFormulaDeploymentEntryId());
/*     */ 
/* 358 */         if (this._log.isDebugEnabled()) {
/* 359 */           this._log.debug("update", "FormulaDeploymentEntry deleting FormulaDeploymentEntryId=" + this.mDetails.getFormulaDeploymentEntryId());
/*     */         }
/*     */ 
/* 364 */         deleteStmt.addBatch();
/*     */ 
/* 367 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 372 */       if (deleteStmt != null)
/*     */       {
/* 374 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 376 */         deleteStmt.executeBatch();
/*     */ 
/* 378 */         if (timer2 != null) {
/* 379 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 383 */       Iterator iter1 = items.values().iterator();
/* 384 */       while (iter1.hasNext())
/*     */       {
/* 386 */         this.mDetails = ((FormulaDeploymentEntryEVO)iter1.next());
/*     */ 
/* 388 */         if (this.mDetails.insertPending())
/*     */         {
/* 390 */           somethingChanged = true;
/* 391 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 394 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 396 */         somethingChanged = true;
/* 397 */         doStore();
/*     */       }
/*     */ 
/* 408 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 412 */       throw handleSQLException("delete from FORMULA_DEPLOYMENT_ENTRY where    FORMULA_DEPLOYMENT_ENTRY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 416 */       if (deleteStmt != null)
/*     */       {
/* 418 */         closeStatement(deleteStmt);
/* 419 */         closeConnection();
/*     */       }
/*     */ 
/* 422 */       this.mDetails = null;
/*     */ 
/* 424 */       if ((somethingChanged) && 
/* 425 */         (timer != null))
/* 426 */         timer.logDebug("update", "collection"); 
/* 426 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 455 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 457 */     PreparedStatement stmt = null;
/* 458 */     ResultSet resultSet = null;
/*     */ 
/* 460 */     int itemCount = 0;
/*     */ 
/* 462 */     FormulaDeploymentLineEVO owningEVO = null;
/* 463 */     Iterator ownersIter = owners.iterator();
/* 464 */     while (ownersIter.hasNext())
/*     */     {
/* 466 */       owningEVO = (FormulaDeploymentLineEVO)ownersIter.next();
/* 467 */       owningEVO.setDeploymentEntriesAllItemsLoaded(true);
/*     */     }
/* 469 */     ownersIter = owners.iterator();
/* 470 */     owningEVO = (FormulaDeploymentLineEVO)ownersIter.next();
/* 471 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 475 */       stmt = getConnection().prepareStatement("select FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_ENTRY_ID,FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_ENTRY.DIM_SEQ,FORMULA_DEPLOYMENT_ENTRY.STRUCTURE_ID,FORMULA_DEPLOYMENT_ENTRY.START_SE_ID,FORMULA_DEPLOYMENT_ENTRY.SELECTED_IND,FORMULA_DEPLOYMENT_ENTRY.END_SE_ID from FORMULA_DEPLOYMENT_ENTRY,FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA,FINANCE_CUBE where 1=1 and FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID ,FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_ENTRY_ID");
/*     */ 
/* 477 */       int col = 1;
/* 478 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 480 */       resultSet = stmt.executeQuery();
/*     */ 
/* 483 */       while (resultSet.next())
/*     */       {
/* 485 */         itemCount++;
/* 486 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 491 */         while (this.mDetails.getFormulaDeploymentLineId() != owningEVO.getFormulaDeploymentLineId())
/*     */         {
/* 495 */           if (!ownersIter.hasNext())
/*     */           {
/* 497 */             this._log.debug("bulkGetAll", "can't find owning [FormulaDeploymentLineId=" + this.mDetails.getFormulaDeploymentLineId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 501 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 502 */             ownersIter = owners.iterator();
/* 503 */             while (ownersIter.hasNext())
/*     */             {
/* 505 */               owningEVO = (FormulaDeploymentLineEVO)ownersIter.next();
/* 506 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 508 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 510 */           owningEVO = (FormulaDeploymentLineEVO)ownersIter.next();
/*     */         }
/* 512 */         if (owningEVO.getDeploymentEntries() == null)
/*     */         {
/* 514 */           theseItems = new ArrayList();
/* 515 */           owningEVO.setDeploymentEntries(theseItems);
/* 516 */           owningEVO.setDeploymentEntriesAllItemsLoaded(true);
/*     */         }
/* 518 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 521 */       if (timer != null) {
/* 522 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 527 */       throw handleSQLException("select FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_ENTRY_ID,FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_ENTRY.DIM_SEQ,FORMULA_DEPLOYMENT_ENTRY.STRUCTURE_ID,FORMULA_DEPLOYMENT_ENTRY.START_SE_ID,FORMULA_DEPLOYMENT_ENTRY.SELECTED_IND,FORMULA_DEPLOYMENT_ENTRY.END_SE_ID from FORMULA_DEPLOYMENT_ENTRY,FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA,FINANCE_CUBE where 1=1 and FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID ,FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_ENTRY_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 531 */       closeResultSet(resultSet);
/* 532 */       closeStatement(stmt);
/* 533 */       closeConnection();
/*     */ 
/* 535 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectFormulaDeploymentLineId, String dependants, Collection currentList)
/*     */   {
/* 560 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 561 */     PreparedStatement stmt = null;
/* 562 */     ResultSet resultSet = null;
/*     */ 
/* 564 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 568 */       stmt = getConnection().prepareStatement("select FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_ENTRY_ID,FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_ENTRY.DIM_SEQ,FORMULA_DEPLOYMENT_ENTRY.STRUCTURE_ID,FORMULA_DEPLOYMENT_ENTRY.START_SE_ID,FORMULA_DEPLOYMENT_ENTRY.SELECTED_IND,FORMULA_DEPLOYMENT_ENTRY.END_SE_ID from FORMULA_DEPLOYMENT_ENTRY where    FORMULA_DEPLOYMENT_LINE_ID = ? ");
/*     */ 
/* 570 */       int col = 1;
/* 571 */       stmt.setInt(col++, selectFormulaDeploymentLineId);
/*     */ 
/* 573 */       resultSet = stmt.executeQuery();
/*     */ 
/* 575 */       while (resultSet.next())
/*     */       {
/* 577 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 580 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 583 */       if (currentList != null)
/*     */       {
/* 586 */         ListIterator iter = items.listIterator();
/* 587 */         FormulaDeploymentEntryEVO currentEVO = null;
/* 588 */         FormulaDeploymentEntryEVO newEVO = null;
/* 589 */         while (iter.hasNext())
/*     */         {
/* 591 */           newEVO = (FormulaDeploymentEntryEVO)iter.next();
/* 592 */           Iterator iter2 = currentList.iterator();
/* 593 */           while (iter2.hasNext())
/*     */           {
/* 595 */             currentEVO = (FormulaDeploymentEntryEVO)iter2.next();
/* 596 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 598 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 604 */         Iterator iter2 = currentList.iterator();
/* 605 */         while (iter2.hasNext())
/*     */         {
/* 607 */           currentEVO = (FormulaDeploymentEntryEVO)iter2.next();
/* 608 */           if (currentEVO.insertPending()) {
/* 609 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 613 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 617 */       throw handleSQLException("select FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_ENTRY_ID,FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_ENTRY.DIM_SEQ,FORMULA_DEPLOYMENT_ENTRY.STRUCTURE_ID,FORMULA_DEPLOYMENT_ENTRY.START_SE_ID,FORMULA_DEPLOYMENT_ENTRY.SELECTED_IND,FORMULA_DEPLOYMENT_ENTRY.END_SE_ID from FORMULA_DEPLOYMENT_ENTRY where    FORMULA_DEPLOYMENT_LINE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 621 */       closeResultSet(resultSet);
/* 622 */       closeStatement(stmt);
/* 623 */       closeConnection();
/*     */ 
/* 625 */       if (timer != null) {
/* 626 */         timer.logDebug("getAll", " FormulaDeploymentLineId=" + selectFormulaDeploymentLineId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 631 */     return items;
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentEntryEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 645 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 648 */     if (this.mDetails == null) {
/* 649 */       doLoad(((FormulaDeploymentEntryCK)paramCK).getFormulaDeploymentEntryPK());
/*     */     }
/* 651 */     else if (!this.mDetails.getPK().equals(((FormulaDeploymentEntryCK)paramCK).getFormulaDeploymentEntryPK())) {
/* 652 */       doLoad(((FormulaDeploymentEntryCK)paramCK).getFormulaDeploymentEntryPK());
/*     */     }
/*     */ 
/* 655 */     FormulaDeploymentEntryEVO details = new FormulaDeploymentEntryEVO();
/* 656 */     details = this.mDetails.deepClone();
/*     */ 
/* 658 */     if (timer != null) {
/* 659 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 661 */     return details;
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentEntryEVO getDetails(ModelCK paramCK, FormulaDeploymentEntryEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 667 */     FormulaDeploymentEntryEVO savedEVO = this.mDetails;
/* 668 */     this.mDetails = paramEVO;
/* 669 */     FormulaDeploymentEntryEVO newEVO = getDetails(paramCK, dependants);
/* 670 */     this.mDetails = savedEVO;
/* 671 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentEntryEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 677 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 681 */     FormulaDeploymentEntryEVO details = this.mDetails.deepClone();
/*     */ 
/* 683 */     if (timer != null) {
/* 684 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 686 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 691 */     return "FormulaDeploymentEntry";
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentEntryRefImpl getRef(FormulaDeploymentEntryPK paramFormulaDeploymentEntryPK)
/*     */   {
/* 696 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 697 */     PreparedStatement stmt = null;
/* 698 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 701 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID,CUBE_FORMULA.CUBE_FORMULA_ID,FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID from FORMULA_DEPLOYMENT_ENTRY,MODEL,FINANCE_CUBE,CUBE_FORMULA,FORMULA_DEPLOYMENT_LINE where 1=1 and FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_ENTRY_ID = ? and FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID = CUBE_FORMULA.FORMULA_DEPLOYMENT_LINE_ID and CUBE_FORMULA.CUBE_FORMULA_ID = FINANCE_CUBE.CUBE_FORMULA_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID");
/* 702 */       int col = 1;
/* 703 */       stmt.setInt(col++, paramFormulaDeploymentEntryPK.getFormulaDeploymentEntryId());
/*     */ 
/* 705 */       resultSet = stmt.executeQuery();
/*     */ 
/* 707 */       if (!resultSet.next()) {
/* 708 */         throw new RuntimeException(getEntityName() + " getRef " + paramFormulaDeploymentEntryPK + " not found");
/*     */       }
/* 710 */       col = 2;
/* 711 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 715 */       FinanceCubePK newFinanceCubePK = new FinanceCubePK(resultSet.getInt(col++));
/*     */ 
/* 719 */       CubeFormulaPK newCubeFormulaPK = new CubeFormulaPK(resultSet.getInt(col++));
/*     */ 
/* 723 */       FormulaDeploymentLinePK newFormulaDeploymentLinePK = new FormulaDeploymentLinePK(resultSet.getInt(col++));
/*     */ 
/* 727 */       String textFormulaDeploymentEntry = "";
/* 728 */       FormulaDeploymentEntryCK ckFormulaDeploymentEntry = new FormulaDeploymentEntryCK(newModelPK, newFinanceCubePK, newCubeFormulaPK, newFormulaDeploymentLinePK, paramFormulaDeploymentEntryPK);
/*     */ 
/* 736 */       FormulaDeploymentEntryRefImpl localFormulaDeploymentEntryRefImpl = new FormulaDeploymentEntryRefImpl(ckFormulaDeploymentEntry, textFormulaDeploymentEntry);
/*     */       return localFormulaDeploymentEntryRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 741 */       throw handleSQLException(paramFormulaDeploymentEntryPK, "select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID,CUBE_FORMULA.CUBE_FORMULA_ID,FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID from FORMULA_DEPLOYMENT_ENTRY,MODEL,FINANCE_CUBE,CUBE_FORMULA,FORMULA_DEPLOYMENT_LINE where 1=1 and FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_ENTRY_ID = ? and FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID = CUBE_FORMULA.FORMULA_DEPLOYMENT_LINE_ID and CUBE_FORMULA.CUBE_FORMULA_ID = FINANCE_CUBE.CUBE_FORMULA_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 745 */       closeResultSet(resultSet);
/* 746 */       closeStatement(stmt);
/* 747 */       closeConnection();
/*     */ 
/* 749 */       if (timer != null)
/* 750 */         timer.logDebug("getRef", paramFormulaDeploymentEntryPK); 
/* 750 */     }
/*     */   }
/*     */ 
/*     */   public EntityList queryInvalidFormulaDeployments(int financeCubeId)
/*     */   {
/* 764 */     SqlBuilder builder = new SqlBuilder(new String[] { "select distinct cube_formula_id, cf.vis_id", "from cube_formula cf", "join formula_deployment_line fdl using ( cube_formula_id )", "join formula_deployment_entry fde using( formula_deployment_line_id )", "left join structure_element_view start_se_rec on", "(", "  start_se_rec.structure_id = fde.structure_id and", "  start_se_rec.structure_element_id = fde.start_se_id", ")", "left join structure_element_view end_se_rec on", "(", "  end_se_rec.structure_id = fde.structure_id and", "  start_se_rec.structure_element_id = fde.end_se_id", ")", "where cf.finance_cube_id = <financeCubeId> and", "      cf.deployment_ind = 'Y' and", "      ( ( fde.start_se_id is not null and start_se_rec.structure_element_id is null ) or", "        ( fde.end_se_id is not null and end_se_rec.structure_element_id is null ) )" });
/*     */ 
/* 783 */     SqlExecutor executor = null;
/* 784 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 787 */       executor = new SqlExecutor("queryInvalidFormula", getDataSource(), builder, this._log);
/*     */ 
/* 789 */       executor.addBindVariable("<financeCubeId>", Integer.valueOf(financeCubeId));
/*     */ 
/* 791 */       rs = executor.getResultSet();
/*     */ 
/* 793 */       EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(new JdbcUtils.ColType[] { new JdbcUtils.CubeFormulaRefColType("cubeFormula", "cube_formula_id", "vis_id") }, rs);
/*     */       return localEntityListImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 801 */       throw handleSQLException("queryInvalidFormula", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 805 */       closeResultSet(rs);
/* 806 */       executor.close(); } //throw localObject;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentEntryDAO
 * JD-Core Version:    0.6.0
 */