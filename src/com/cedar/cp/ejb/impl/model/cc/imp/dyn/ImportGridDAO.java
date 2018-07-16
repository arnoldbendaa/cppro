/*     */ package com.cedar.cp.ejb.impl.model.cc.imp.dyn;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCK;
/*     */ import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridPK;
/*     */ import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.ejb.impl.model.ModelEVO;
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
/*     */ public class ImportGridDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select IMPORT_GRID.MODEL_ID,IMPORT_GRID.GRID_ID,IMPORT_GRID.USER_ID";
/*     */   protected static final String SQL_LOAD = " from IMPORT_GRID where    MODEL_ID = ? AND GRID_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into IMPORT_GRID ( MODEL_ID,GRID_ID,USER_ID) values ( ?,?,?)";
/*     */   protected static final String SQL_STORE = "update IMPORT_GRID set USER_ID = ? where    MODEL_ID = ? AND GRID_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from IMPORT_GRID where    MODEL_ID = ? AND GRID_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from IMPORT_GRID where 1=1 and IMPORT_GRID.MODEL_ID = ? order by  IMPORT_GRID.MODEL_ID ,IMPORT_GRID.GRID_ID";
/*     */   protected static final String SQL_GET_ALL = " from IMPORT_GRID where    MODEL_ID = ? ";
/*     */   protected ImportGridEVO mDetails;
/*     */ 
/*     */   public ImportGridDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ImportGridDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ImportGridDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ImportGridPK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ImportGridEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private ImportGridEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  89 */     int col = 1;
/*  90 */     ImportGridEVO evo = new ImportGridEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  96 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ImportGridEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 101 */     int col = startCol_;
/* 102 */     stmt_.setInt(col++, evo_.getModelId());
/* 103 */     stmt_.setInt(col++, evo_.getGridId());
/* 104 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ImportGridEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 109 */     int col = startCol_;
/* 110 */     stmt_.setInt(col++, evo_.getUserId());
/* 111 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ImportGridPK pk)
/*     */     throws ValidationException
/*     */   {
/* 128 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 130 */     PreparedStatement stmt = null;
/* 131 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 135 */       stmt = getConnection().prepareStatement("select IMPORT_GRID.MODEL_ID,IMPORT_GRID.GRID_ID,IMPORT_GRID.USER_ID from IMPORT_GRID where    MODEL_ID = ? AND GRID_ID = ? ");
/*     */ 
/* 138 */       int col = 1;
/* 139 */       stmt.setInt(col++, pk.getModelId());
/* 140 */       stmt.setInt(col++, pk.getGridId());
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
/* 154 */       throw handleSQLException(pk, "select IMPORT_GRID.MODEL_ID,IMPORT_GRID.GRID_ID,IMPORT_GRID.USER_ID from IMPORT_GRID where    MODEL_ID = ? AND GRID_ID = ? ", sqle);
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
/* 191 */       stmt = getConnection().prepareStatement("insert into IMPORT_GRID ( MODEL_ID,GRID_ID,USER_ID) values ( ?,?,?)");
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
/* 209 */       throw handleSQLException(this.mDetails.getPK(), "insert into IMPORT_GRID ( MODEL_ID,GRID_ID,USER_ID) values ( ?,?,?)", sqle);
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
/* 250 */         stmt = getConnection().prepareStatement("update IMPORT_GRID set USER_ID = ? where    MODEL_ID = ? AND GRID_ID = ? ");
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
/* 274 */       throw handleSQLException(getPK(), "update IMPORT_GRID set USER_ID = ? where    MODEL_ID = ? AND GRID_ID = ? ", sqle);
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
/* 317 */         this.mDetails = ((ImportGridEVO)iter2.next());
/*     */ 
/* 320 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 322 */         somethingChanged = true;
/*     */ 
/* 325 */         if (deleteStmt == null) {
/* 326 */           deleteStmt = getConnection().prepareStatement("delete from IMPORT_GRID where    MODEL_ID = ? AND GRID_ID = ? ");
/*     */         }
/*     */ 
/* 329 */         int col = 1;
/* 330 */         deleteStmt.setInt(col++, this.mDetails.getModelId());
/* 331 */         deleteStmt.setInt(col++, this.mDetails.getGridId());
/*     */ 
/* 333 */         if (this._log.isDebugEnabled()) {
/* 334 */           this._log.debug("update", "ImportGrid deleting ModelId=" + this.mDetails.getModelId() + ",GridId=" + this.mDetails.getGridId());
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
/* 362 */         this.mDetails = ((ImportGridEVO)iter1.next());
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
/* 388 */       throw handleSQLException("delete from IMPORT_GRID where    MODEL_ID = ? AND GRID_ID = ? ", sqle);
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
/*     */   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants)
/*     */   {
/* 422 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 424 */     PreparedStatement stmt = null;
/* 425 */     ResultSet resultSet = null;
/*     */ 
/* 427 */     int itemCount = 0;
/*     */ 
/* 429 */     Collection theseItems = new ArrayList();
/* 430 */     owningEVO.setAssocImportGrids(theseItems);
/* 431 */     owningEVO.setAssocImportGridsAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 435 */       stmt = getConnection().prepareStatement("select IMPORT_GRID.MODEL_ID,IMPORT_GRID.GRID_ID,IMPORT_GRID.USER_ID from IMPORT_GRID where 1=1 and IMPORT_GRID.MODEL_ID = ? order by  IMPORT_GRID.MODEL_ID ,IMPORT_GRID.GRID_ID");
/*     */ 
/* 437 */       int col = 1;
/* 438 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 440 */       resultSet = stmt.executeQuery();
/*     */ 
/* 443 */       while (resultSet.next())
/*     */       {
/* 445 */         itemCount++;
/* 446 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 448 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 451 */       if (timer != null) {
/* 452 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 457 */       throw handleSQLException("select IMPORT_GRID.MODEL_ID,IMPORT_GRID.GRID_ID,IMPORT_GRID.USER_ID from IMPORT_GRID where 1=1 and IMPORT_GRID.MODEL_ID = ? order by  IMPORT_GRID.MODEL_ID ,IMPORT_GRID.GRID_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 461 */       closeResultSet(resultSet);
/* 462 */       closeStatement(stmt);
/* 463 */       closeConnection();
/*     */ 
/* 465 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectModelId, String dependants, Collection currentList)
/*     */   {
/* 490 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 491 */     PreparedStatement stmt = null;
/* 492 */     ResultSet resultSet = null;
/*     */ 
/* 494 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 498 */       stmt = getConnection().prepareStatement("select IMPORT_GRID.MODEL_ID,IMPORT_GRID.GRID_ID,IMPORT_GRID.USER_ID from IMPORT_GRID where    MODEL_ID = ? ");
/*     */ 
/* 500 */       int col = 1;
/* 501 */       stmt.setInt(col++, selectModelId);
/*     */ 
/* 503 */       resultSet = stmt.executeQuery();
/*     */ 
/* 505 */       while (resultSet.next())
/*     */       {
/* 507 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 510 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 513 */       if (currentList != null)
/*     */       {
/* 516 */         ListIterator iter = items.listIterator();
/* 517 */         ImportGridEVO currentEVO = null;
/* 518 */         ImportGridEVO newEVO = null;
/* 519 */         while (iter.hasNext())
/*     */         {
/* 521 */           newEVO = (ImportGridEVO)iter.next();
/* 522 */           Iterator iter2 = currentList.iterator();
/* 523 */           while (iter2.hasNext())
/*     */           {
/* 525 */             currentEVO = (ImportGridEVO)iter2.next();
/* 526 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 528 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 534 */         Iterator iter2 = currentList.iterator();
/* 535 */         while (iter2.hasNext())
/*     */         {
/* 537 */           currentEVO = (ImportGridEVO)iter2.next();
/* 538 */           if (currentEVO.insertPending()) {
/* 539 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 543 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 547 */       throw handleSQLException("select IMPORT_GRID.MODEL_ID,IMPORT_GRID.GRID_ID,IMPORT_GRID.USER_ID from IMPORT_GRID where    MODEL_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 551 */       closeResultSet(resultSet);
/* 552 */       closeStatement(stmt);
/* 553 */       closeConnection();
/*     */ 
/* 555 */       if (timer != null) {
/* 556 */         timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 561 */     return items;
/*     */   }
/*     */ 
/*     */   public ImportGridEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 575 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 578 */     if (this.mDetails == null) {
/* 579 */       doLoad(((ImportGridCK)paramCK).getImportGridPK());
/*     */     }
/* 581 */     else if (!this.mDetails.getPK().equals(((ImportGridCK)paramCK).getImportGridPK())) {
/* 582 */       doLoad(((ImportGridCK)paramCK).getImportGridPK());
/*     */     }
/*     */ 
/* 585 */     ImportGridEVO details = new ImportGridEVO();
/* 586 */     details = this.mDetails.deepClone();
/*     */ 
/* 588 */     if (timer != null) {
/* 589 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 591 */     return details;
/*     */   }
/*     */ 
/*     */   public ImportGridEVO getDetails(ModelCK paramCK, ImportGridEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 597 */     ImportGridEVO savedEVO = this.mDetails;
/* 598 */     this.mDetails = paramEVO;
/* 599 */     ImportGridEVO newEVO = getDetails(paramCK, dependants);
/* 600 */     this.mDetails = savedEVO;
/* 601 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public ImportGridEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 607 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 611 */     ImportGridEVO details = this.mDetails.deepClone();
/*     */ 
/* 613 */     if (timer != null) {
/* 614 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 616 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 621 */     return "ImportGrid";
/*     */   }
/*     */ 
/*     */   public ImportGridRefImpl getRef(ImportGridPK paramImportGridPK)
/*     */   {
/* 626 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 627 */     PreparedStatement stmt = null;
/* 628 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 631 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID from IMPORT_GRID,MODEL where 1=1 and IMPORT_GRID.MODEL_ID = ? and IMPORT_GRID.GRID_ID = ? and IMPORT_GRID.MODEL_ID = MODEL.MODEL_ID");
/* 632 */       int col = 1;
/* 633 */       stmt.setInt(col++, paramImportGridPK.getModelId());
/* 634 */       stmt.setInt(col++, paramImportGridPK.getGridId());
/*     */ 
/* 636 */       resultSet = stmt.executeQuery();
/*     */ 
/* 638 */       if (!resultSet.next()) {
/* 639 */         throw new RuntimeException(getEntityName() + " getRef " + paramImportGridPK + " not found");
/*     */       }
/* 641 */       col = 2;
/* 642 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 646 */       String textImportGrid = "";
/* 647 */       ImportGridCK ckImportGrid = new ImportGridCK(newModelPK, paramImportGridPK);
/*     */ 
/* 652 */       ImportGridRefImpl localImportGridRefImpl = new ImportGridRefImpl(ckImportGrid, textImportGrid);
/*     */       return localImportGridRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 657 */       throw handleSQLException(paramImportGridPK, "select 0,MODEL.MODEL_ID from IMPORT_GRID,MODEL where 1=1 and IMPORT_GRID.MODEL_ID = ? and IMPORT_GRID.GRID_ID = ? and IMPORT_GRID.MODEL_ID = MODEL.MODEL_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 661 */       closeResultSet(resultSet);
/* 662 */       closeStatement(stmt);
/* 663 */       closeConnection();
/*     */ 
/* 665 */       if (timer != null)
/* 666 */         timer.logDebug("getRef", paramImportGridPK); 
/* 666 */     }
/*     */   }
/*     */ 
/*     */   public void deleteImportGridData(int modelId, int gridId)
/*     */   {
/* 681 */     PreparedStatement ps = null;
/*     */     try
/*     */     {
/* 684 */       ps = getConnection().prepareStatement("delete from import_grid where model_id = ? and grid_id = ?");
/* 685 */       ps.setInt(1, modelId);
/* 686 */       ps.setInt(2, gridId);
/* 687 */       ps.executeUpdate();
/* 688 */       closeStatement(ps);
/*     */ 
/* 690 */       ps = getConnection().prepareStatement("delete from import_grid_cell where grid_id = ?");
/* 691 */       ps.setInt(1, gridId);
/* 692 */       ps.executeUpdate();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 696 */       throw handleSQLException("deeteImportGridData", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 700 */       closeStatement(ps);
/* 701 */       closeConnection();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.cc.imp.dyn.ImportGridDAO
 * JD-Core Version:    0.6.0
 */