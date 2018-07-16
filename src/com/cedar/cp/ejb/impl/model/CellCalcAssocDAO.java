/*     */ package com.cedar.cp.ejb.impl.model;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.AllCellCalcAssocsELO;
/*     */ import com.cedar.cp.dto.model.CellCalcAssocCK;
/*     */ import com.cedar.cp.dto.model.CellCalcAssocPK;
/*     */ import com.cedar.cp.dto.model.CellCalcAssocRefImpl;
/*     */ import com.cedar.cp.dto.model.CellCalcCK;
/*     */ import com.cedar.cp.dto.model.CellCalcPK;
/*     */ import com.cedar.cp.dto.model.CellCalcRefImpl;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.ModelRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class CellCalcAssocDAO extends AbstractDAO
/*     */ {
/*  33 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select CELL_CALC_ASSOC.CELL_CALC_ASSOC_ID,CELL_CALC_ASSOC.CELL_CALC_ID,CELL_CALC_ASSOC.ACCOUNT_ELEMENT_ID,CELL_CALC_ASSOC.FORM_FIELD,CELL_CALC_ASSOC.UPDATED_BY_USER_ID,CELL_CALC_ASSOC.UPDATED_TIME,CELL_CALC_ASSOC.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from CELL_CALC_ASSOC where    CELL_CALC_ASSOC_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into CELL_CALC_ASSOC ( CELL_CALC_ASSOC_ID,CELL_CALC_ID,ACCOUNT_ELEMENT_ID,FORM_FIELD,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update CELL_CALC_ASSOC set CELL_CALC_ID = ?,ACCOUNT_ELEMENT_ID = ?,FORM_FIELD = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CELL_CALC_ASSOC_ID = ? ";
/* 316 */   protected static String SQL_ALL_CELL_CALC_ASSOCS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,CELL_CALC.CELL_CALC_ID      ,CELL_CALC.VIS_ID      ,CELL_CALC_ASSOC.CELL_CALC_ASSOC_ID      ,CELL_CALC_ASSOC.CELL_CALC_ID      ,CELL_CALC_ASSOC.ACCOUNT_ELEMENT_ID from CELL_CALC_ASSOC    ,MODEL    ,CELL_CALC where 1=1   and CELL_CALC_ASSOC.CELL_CALC_ID = CELL_CALC.CELL_CALC_ID   and CELL_CALC.MODEL_ID = MODEL.MODEL_ID  order by CELL_CALC_ASSOC.ACCOUNT_ELEMENT_ID";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from CELL_CALC_ASSOC where    CELL_CALC_ASSOC_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from CELL_CALC_ASSOC,CELL_CALC where 1=1 and CELL_CALC_ASSOC.CELL_CALC_ID = CELL_CALC.CELL_CALC_ID and CELL_CALC.MODEL_ID = ? order by  CELL_CALC_ASSOC.CELL_CALC_ID ,CELL_CALC_ASSOC.CELL_CALC_ASSOC_ID";
/*     */   protected static final String SQL_GET_ALL = " from CELL_CALC_ASSOC where    CELL_CALC_ID = ? ";
/* 874 */   private static String REMOVE_CELL_CALCASSOC_DATA_SQL = "delete \nfrom cell_calc_assoc \nwhere (cell_calc_id,account_element_id) \n\t\t   in ( select assoc.cell_calc_id, assoc.account_element_id \n\t\t        from cell_calc_assoc assoc, cell_calc cc \n\t\t        where cc.model_id = ? and \n\t\t\t\t\t  assoc.cell_calc_id = cc.cell_calc_id and \n\t\t\t\t\t  assoc.account_element_id not in ( select dimension_element_id \n\t\t\t\t\t\t\t\t\t\t\t\t\t\tfrom dimension_element \n\t\t\t\t\t\t\t\t\t\t\t\t\t\twhere dimension_id = ? ) )";
/*     */   protected CellCalcAssocEVO mDetails;
/*     */ 
/*     */   public CellCalcAssocDAO(Connection connection)
/*     */   {
/*  40 */     super(connection);
/*     */   }
/*     */ 
/*     */   public CellCalcAssocDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CellCalcAssocDAO(DataSource ds)
/*     */   {
/*  56 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected CellCalcAssocPK getPK()
/*     */   {
/*  64 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(CellCalcAssocEVO details)
/*     */   {
/*  73 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private CellCalcAssocEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  91 */     int col = 1;
/*  92 */     CellCalcAssocEVO evo = new CellCalcAssocEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++));
/*     */ 
/*  99 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 100 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 101 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 102 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(CellCalcAssocEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 107 */     int col = startCol_;
/* 108 */     stmt_.setInt(col++, evo_.getCellCalcAssocId());
/* 109 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(CellCalcAssocEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 114 */     int col = startCol_;
/* 115 */     stmt_.setInt(col++, evo_.getCellCalcId());
/* 116 */     stmt_.setInt(col++, evo_.getAccountElementId());
/* 117 */     stmt_.setString(col++, evo_.getFormField());
/* 118 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 119 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 120 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 121 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(CellCalcAssocPK pk)
/*     */     throws ValidationException
/*     */   {
/* 137 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 139 */     PreparedStatement stmt = null;
/* 140 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 144 */       stmt = getConnection().prepareStatement("select CELL_CALC_ASSOC.CELL_CALC_ASSOC_ID,CELL_CALC_ASSOC.CELL_CALC_ID,CELL_CALC_ASSOC.ACCOUNT_ELEMENT_ID,CELL_CALC_ASSOC.FORM_FIELD,CELL_CALC_ASSOC.UPDATED_BY_USER_ID,CELL_CALC_ASSOC.UPDATED_TIME,CELL_CALC_ASSOC.CREATED_TIME from CELL_CALC_ASSOC where    CELL_CALC_ASSOC_ID = ? ");
/*     */ 
/* 147 */       int col = 1;
/* 148 */       stmt.setInt(col++, pk.getCellCalcAssocId());
/*     */ 
/* 150 */       resultSet = stmt.executeQuery();
/*     */ 
/* 152 */       if (!resultSet.next()) {
/* 153 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 156 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 157 */       if (this.mDetails.isModified())
/* 158 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 162 */       throw handleSQLException(pk, "select CELL_CALC_ASSOC.CELL_CALC_ASSOC_ID,CELL_CALC_ASSOC.CELL_CALC_ID,CELL_CALC_ASSOC.ACCOUNT_ELEMENT_ID,CELL_CALC_ASSOC.FORM_FIELD,CELL_CALC_ASSOC.UPDATED_BY_USER_ID,CELL_CALC_ASSOC.UPDATED_TIME,CELL_CALC_ASSOC.CREATED_TIME from CELL_CALC_ASSOC where    CELL_CALC_ASSOC_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 166 */       closeResultSet(resultSet);
/* 167 */       closeStatement(stmt);
/* 168 */       closeConnection();
/*     */ 
/* 170 */       if (timer != null)
/* 171 */         timer.logDebug("doLoad", pk);
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
/* 208 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 209 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 210 */       stmt = getConnection().prepareStatement("insert into CELL_CALC_ASSOC ( CELL_CALC_ASSOC_ID,CELL_CALC_ID,ACCOUNT_ELEMENT_ID,FORM_FIELD,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*     */ 
/* 213 */       int col = 1;
/* 214 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 215 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 218 */       int resultCount = stmt.executeUpdate();
/* 219 */       if (resultCount != 1)
/*     */       {
/* 221 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 224 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 228 */       throw handleSQLException(this.mDetails.getPK(), "insert into CELL_CALC_ASSOC ( CELL_CALC_ASSOC_ID,CELL_CALC_ID,ACCOUNT_ELEMENT_ID,FORM_FIELD,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 232 */       closeStatement(stmt);
/* 233 */       closeConnection();
/*     */ 
/* 235 */       if (timer != null)
/* 236 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 261 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 265 */     PreparedStatement stmt = null;
/*     */ 
/* 267 */     boolean mainChanged = this.mDetails.isModified();
/* 268 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 271 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 274 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 275 */         stmt = getConnection().prepareStatement("update CELL_CALC_ASSOC set CELL_CALC_ID = ?,ACCOUNT_ELEMENT_ID = ?,FORM_FIELD = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CELL_CALC_ASSOC_ID = ? ");
/*     */ 
/* 278 */         int col = 1;
/* 279 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 280 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 283 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 286 */         if (resultCount != 1) {
/* 287 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 290 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 299 */       throw handleSQLException(getPK(), "update CELL_CALC_ASSOC set CELL_CALC_ID = ?,ACCOUNT_ELEMENT_ID = ?,FORM_FIELD = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CELL_CALC_ASSOC_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 303 */       closeStatement(stmt);
/* 304 */       closeConnection();
/*     */ 
/* 306 */       if ((timer != null) && (
/* 307 */         (mainChanged) || (dependantChanged)))
/* 308 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllCellCalcAssocsELO getAllCellCalcAssocs()
/*     */   {
/* 351 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 352 */     PreparedStatement stmt = null;
/* 353 */     ResultSet resultSet = null;
/* 354 */     AllCellCalcAssocsELO results = new AllCellCalcAssocsELO();
/*     */     try
/*     */     {
/* 357 */       stmt = getConnection().prepareStatement(SQL_ALL_CELL_CALC_ASSOCS);
/* 358 */       int col = 1;
/* 359 */       resultSet = stmt.executeQuery();
/* 360 */       while (resultSet.next())
/*     */       {
/* 362 */         col = 2;
/*     */ 
/* 365 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 368 */         String textModel = resultSet.getString(col++);
/*     */ 
/* 370 */         CellCalcPK pkCellCalc = new CellCalcPK(resultSet.getInt(col++));
/*     */ 
/* 373 */         String textCellCalc = resultSet.getString(col++);
/*     */ 
/* 376 */         CellCalcAssocPK pkCellCalcAssoc = new CellCalcAssocPK(resultSet.getInt(col++));
/*     */ 
/* 379 */         String textCellCalcAssoc = "";
/*     */ 
/* 384 */         CellCalcCK ckCellCalc = new CellCalcCK(pkModel, pkCellCalc);
/*     */ 
/* 390 */         CellCalcAssocCK ckCellCalcAssoc = new CellCalcAssocCK(pkModel, pkCellCalc, pkCellCalcAssoc);
/*     */ 
/* 397 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*     */ 
/* 403 */         CellCalcRefImpl erCellCalc = new CellCalcRefImpl(ckCellCalc, textCellCalc);
/*     */ 
/* 409 */         CellCalcAssocRefImpl erCellCalcAssoc = new CellCalcAssocRefImpl(ckCellCalcAssoc, textCellCalcAssoc);
/*     */ 
/* 414 */         int col1 = resultSet.getInt(col++);
/* 415 */         int col2 = resultSet.getInt(col++);
/*     */ 
/* 418 */         results.add(erCellCalcAssoc, erCellCalc, erModel, col1, col2);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 429 */       throw handleSQLException(SQL_ALL_CELL_CALC_ASSOCS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 433 */       closeResultSet(resultSet);
/* 434 */       closeStatement(stmt);
/* 435 */       closeConnection();
/*     */     }
/*     */ 
/* 438 */     if (timer != null) {
/* 439 */       timer.logDebug("getAllCellCalcAssocs", " items=" + results.size());
/*     */     }
/*     */ 
/* 443 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 460 */     if (items == null) {
/* 461 */       return false;
/*     */     }
/* 463 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 464 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 466 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 471 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 472 */       while (iter2.hasNext())
/*     */       {
/* 474 */         this.mDetails = ((CellCalcAssocEVO)iter2.next());
/*     */ 
/* 477 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 479 */         somethingChanged = true;
/*     */ 
/* 482 */         if (deleteStmt == null) {
/* 483 */           deleteStmt = getConnection().prepareStatement("delete from CELL_CALC_ASSOC where    CELL_CALC_ASSOC_ID = ? ");
/*     */         }
/*     */ 
/* 486 */         int col = 1;
/* 487 */         deleteStmt.setInt(col++, this.mDetails.getCellCalcAssocId());
/*     */ 
/* 489 */         if (this._log.isDebugEnabled()) {
/* 490 */           this._log.debug("update", "CellCalcAssoc deleting CellCalcAssocId=" + this.mDetails.getCellCalcAssocId());
/*     */         }
/*     */ 
/* 495 */         deleteStmt.addBatch();
/*     */ 
/* 498 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 503 */       if (deleteStmt != null)
/*     */       {
/* 505 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 507 */         deleteStmt.executeBatch();
/*     */ 
/* 509 */         if (timer2 != null) {
/* 510 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 514 */       Iterator iter1 = items.values().iterator();
/* 515 */       while (iter1.hasNext())
/*     */       {
/* 517 */         this.mDetails = ((CellCalcAssocEVO)iter1.next());
/*     */ 
/* 519 */         if (this.mDetails.insertPending())
/*     */         {
/* 521 */           somethingChanged = true;
/* 522 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 525 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 527 */         somethingChanged = true;
/* 528 */         doStore();
/*     */       }
/*     */ 
/* 539 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 543 */       throw handleSQLException("delete from CELL_CALC_ASSOC where    CELL_CALC_ASSOC_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 547 */       if (deleteStmt != null)
/*     */       {
/* 549 */         closeStatement(deleteStmt);
/* 550 */         closeConnection();
/*     */       }
/*     */ 
/* 553 */       this.mDetails = null;
/*     */ 
/* 555 */       if ((somethingChanged) && 
/* 556 */         (timer != null))
/* 557 */         timer.logDebug("update", "collection"); 
/* 557 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 580 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 582 */     PreparedStatement stmt = null;
/* 583 */     ResultSet resultSet = null;
/*     */ 
/* 585 */     int itemCount = 0;
/*     */ 
/* 587 */     CellCalcEVO owningEVO = null;
/* 588 */     Iterator ownersIter = owners.iterator();
/* 589 */     while (ownersIter.hasNext())
/*     */     {
/* 591 */       owningEVO = (CellCalcEVO)ownersIter.next();
/* 592 */       owningEVO.setCellCalculationAccountsAllItemsLoaded(true);
/*     */     }
/* 594 */     ownersIter = owners.iterator();
/* 595 */     owningEVO = (CellCalcEVO)ownersIter.next();
/* 596 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 600 */       stmt = getConnection().prepareStatement("select CELL_CALC_ASSOC.CELL_CALC_ASSOC_ID,CELL_CALC_ASSOC.CELL_CALC_ID,CELL_CALC_ASSOC.ACCOUNT_ELEMENT_ID,CELL_CALC_ASSOC.FORM_FIELD,CELL_CALC_ASSOC.UPDATED_BY_USER_ID,CELL_CALC_ASSOC.UPDATED_TIME,CELL_CALC_ASSOC.CREATED_TIME from CELL_CALC_ASSOC,CELL_CALC where 1=1 and CELL_CALC_ASSOC.CELL_CALC_ID = CELL_CALC.CELL_CALC_ID and CELL_CALC.MODEL_ID = ? order by  CELL_CALC_ASSOC.CELL_CALC_ID ,CELL_CALC_ASSOC.CELL_CALC_ASSOC_ID");
/*     */ 
/* 602 */       int col = 1;
/* 603 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 605 */       resultSet = stmt.executeQuery();
/*     */ 
/* 608 */       while (resultSet.next())
/*     */       {
/* 610 */         itemCount++;
/* 611 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 616 */         while (this.mDetails.getCellCalcId() != owningEVO.getCellCalcId())
/*     */         {
/* 620 */           if (!ownersIter.hasNext())
/*     */           {
/* 622 */             this._log.debug("bulkGetAll", "can't find owning [CellCalcId=" + this.mDetails.getCellCalcId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 626 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 627 */             ownersIter = owners.iterator();
/* 628 */             while (ownersIter.hasNext())
/*     */             {
/* 630 */               owningEVO = (CellCalcEVO)ownersIter.next();
/* 631 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 633 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 635 */           owningEVO = (CellCalcEVO)ownersIter.next();
/*     */         }
/* 637 */         if (owningEVO.getCellCalculationAccounts() == null)
/*     */         {
/* 639 */           theseItems = new ArrayList();
/* 640 */           owningEVO.setCellCalculationAccounts(theseItems);
/* 641 */           owningEVO.setCellCalculationAccountsAllItemsLoaded(true);
/*     */         }
/* 643 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 646 */       if (timer != null) {
/* 647 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 652 */       throw handleSQLException("select CELL_CALC_ASSOC.CELL_CALC_ASSOC_ID,CELL_CALC_ASSOC.CELL_CALC_ID,CELL_CALC_ASSOC.ACCOUNT_ELEMENT_ID,CELL_CALC_ASSOC.FORM_FIELD,CELL_CALC_ASSOC.UPDATED_BY_USER_ID,CELL_CALC_ASSOC.UPDATED_TIME,CELL_CALC_ASSOC.CREATED_TIME from CELL_CALC_ASSOC,CELL_CALC where 1=1 and CELL_CALC_ASSOC.CELL_CALC_ID = CELL_CALC.CELL_CALC_ID and CELL_CALC.MODEL_ID = ? order by  CELL_CALC_ASSOC.CELL_CALC_ID ,CELL_CALC_ASSOC.CELL_CALC_ASSOC_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 656 */       closeResultSet(resultSet);
/* 657 */       closeStatement(stmt);
/* 658 */       closeConnection();
/*     */ 
/* 660 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectCellCalcId, String dependants, Collection currentList)
/*     */   {
/* 685 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 686 */     PreparedStatement stmt = null;
/* 687 */     ResultSet resultSet = null;
/*     */ 
/* 689 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 693 */       stmt = getConnection().prepareStatement("select CELL_CALC_ASSOC.CELL_CALC_ASSOC_ID,CELL_CALC_ASSOC.CELL_CALC_ID,CELL_CALC_ASSOC.ACCOUNT_ELEMENT_ID,CELL_CALC_ASSOC.FORM_FIELD,CELL_CALC_ASSOC.UPDATED_BY_USER_ID,CELL_CALC_ASSOC.UPDATED_TIME,CELL_CALC_ASSOC.CREATED_TIME from CELL_CALC_ASSOC where    CELL_CALC_ID = ? ");
/*     */ 
/* 695 */       int col = 1;
/* 696 */       stmt.setInt(col++, selectCellCalcId);
/*     */ 
/* 698 */       resultSet = stmt.executeQuery();
/*     */ 
/* 700 */       while (resultSet.next())
/*     */       {
/* 702 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 705 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 708 */       if (currentList != null)
/*     */       {
/* 711 */         ListIterator iter = items.listIterator();
/* 712 */         CellCalcAssocEVO currentEVO = null;
/* 713 */         CellCalcAssocEVO newEVO = null;
/* 714 */         while (iter.hasNext())
/*     */         {
/* 716 */           newEVO = (CellCalcAssocEVO)iter.next();
/* 717 */           Iterator iter2 = currentList.iterator();
/* 718 */           while (iter2.hasNext())
/*     */           {
/* 720 */             currentEVO = (CellCalcAssocEVO)iter2.next();
/* 721 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 723 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 729 */         Iterator iter2 = currentList.iterator();
/* 730 */         while (iter2.hasNext())
/*     */         {
/* 732 */           currentEVO = (CellCalcAssocEVO)iter2.next();
/* 733 */           if (currentEVO.insertPending()) {
/* 734 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 738 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 742 */       throw handleSQLException("select CELL_CALC_ASSOC.CELL_CALC_ASSOC_ID,CELL_CALC_ASSOC.CELL_CALC_ID,CELL_CALC_ASSOC.ACCOUNT_ELEMENT_ID,CELL_CALC_ASSOC.FORM_FIELD,CELL_CALC_ASSOC.UPDATED_BY_USER_ID,CELL_CALC_ASSOC.UPDATED_TIME,CELL_CALC_ASSOC.CREATED_TIME from CELL_CALC_ASSOC where    CELL_CALC_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 746 */       closeResultSet(resultSet);
/* 747 */       closeStatement(stmt);
/* 748 */       closeConnection();
/*     */ 
/* 750 */       if (timer != null) {
/* 751 */         timer.logDebug("getAll", " CellCalcId=" + selectCellCalcId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 756 */     return items;
/*     */   }
/*     */ 
/*     */   public CellCalcAssocEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 770 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 773 */     if (this.mDetails == null) {
/* 774 */       doLoad(((CellCalcAssocCK)paramCK).getCellCalcAssocPK());
/*     */     }
/* 776 */     else if (!this.mDetails.getPK().equals(((CellCalcAssocCK)paramCK).getCellCalcAssocPK())) {
/* 777 */       doLoad(((CellCalcAssocCK)paramCK).getCellCalcAssocPK());
/*     */     }
/*     */ 
/* 780 */     CellCalcAssocEVO details = new CellCalcAssocEVO();
/* 781 */     details = this.mDetails.deepClone();
/*     */ 
/* 783 */     if (timer != null) {
/* 784 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 786 */     return details;
/*     */   }
/*     */ 
/*     */   public CellCalcAssocEVO getDetails(ModelCK paramCK, CellCalcAssocEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 792 */     CellCalcAssocEVO savedEVO = this.mDetails;
/* 793 */     this.mDetails = paramEVO;
/* 794 */     CellCalcAssocEVO newEVO = getDetails(paramCK, dependants);
/* 795 */     this.mDetails = savedEVO;
/* 796 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public CellCalcAssocEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 802 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 806 */     CellCalcAssocEVO details = this.mDetails.deepClone();
/*     */ 
/* 808 */     if (timer != null) {
/* 809 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 811 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 816 */     return "CellCalcAssoc";
/*     */   }
/*     */ 
/*     */   public CellCalcAssocRefImpl getRef(CellCalcAssocPK paramCellCalcAssocPK)
/*     */   {
/* 821 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 822 */     PreparedStatement stmt = null;
/* 823 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 826 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,CELL_CALC.CELL_CALC_ID from CELL_CALC_ASSOC,MODEL,CELL_CALC where 1=1 and CELL_CALC_ASSOC.CELL_CALC_ASSOC_ID = ? and CELL_CALC_ASSOC.CELL_CALC_ID = CELL_CALC.CELL_CALC_ID and CELL_CALC.CELL_CALC_ID = MODEL.CELL_CALC_ID");
/* 827 */       int col = 1;
/* 828 */       stmt.setInt(col++, paramCellCalcAssocPK.getCellCalcAssocId());
/*     */ 
/* 830 */       resultSet = stmt.executeQuery();
/*     */ 
/* 832 */       if (!resultSet.next()) {
/* 833 */         throw new RuntimeException(getEntityName() + " getRef " + paramCellCalcAssocPK + " not found");
/*     */       }
/* 835 */       col = 2;
/* 836 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 840 */       CellCalcPK newCellCalcPK = new CellCalcPK(resultSet.getInt(col++));
/*     */ 
/* 844 */       String textCellCalcAssoc = "";
/* 845 */       CellCalcAssocCK ckCellCalcAssoc = new CellCalcAssocCK(newModelPK, newCellCalcPK, paramCellCalcAssocPK);
/*     */ 
/* 851 */       CellCalcAssocRefImpl localCellCalcAssocRefImpl = new CellCalcAssocRefImpl(ckCellCalcAssoc, textCellCalcAssoc);
/*     */       return localCellCalcAssocRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 856 */       throw handleSQLException(paramCellCalcAssocPK, "select 0,MODEL.MODEL_ID,CELL_CALC.CELL_CALC_ID from CELL_CALC_ASSOC,MODEL,CELL_CALC where 1=1 and CELL_CALC_ASSOC.CELL_CALC_ASSOC_ID = ? and CELL_CALC_ASSOC.CELL_CALC_ID = CELL_CALC.CELL_CALC_ID and CELL_CALC.CELL_CALC_ID = MODEL.CELL_CALC_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 860 */       closeResultSet(resultSet);
/* 861 */       closeStatement(stmt);
/* 862 */       closeConnection();
/*     */ 
/* 864 */       if (timer != null)
/* 865 */         timer.logDebug("getRef", paramCellCalcAssocPK); 
/* 865 */     }
/*     */   }
/*     */ 
/*     */   public void dimensionElementsRemoved(int modelId, int dimensionId)
/*     */     throws SQLException
/*     */   {
/* 896 */     PreparedStatement ps = null;
/*     */     try
/*     */     {
/* 900 */       ps = getConnection().prepareStatement(REMOVE_CELL_CALCASSOC_DATA_SQL);
/* 901 */       ps.setInt(1, modelId);
/* 902 */       ps.setInt(2, dimensionId);
/* 903 */       ps.executeUpdate();
/*     */     }
/*     */     finally
/*     */     {
/* 907 */       closeStatement(ps);
/* 908 */       closeConnection();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.CellCalcAssocDAO
 * JD-Core Version:    0.6.0
 */