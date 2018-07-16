/*     */ package com.cedar.cp.ejb.impl.extsys;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysDimElementCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysDimElementPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysDimElementRefImpl;
/*     */ import com.cedar.cp.dto.extsys.ExtSysDimensionPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysLedgerPK;
/*     */ import com.cedar.cp.dto.extsys.ExternalSystemCK;
/*     */ import com.cedar.cp.dto.extsys.ExternalSystemPK;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.SqlBuilder;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.io.PrintWriter;
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
/*     */ public class ExtSysDimElementDAO extends AbstractDAO
/*     */ {
/*  34 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID,EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID,EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_DIM_ELEMENT.DIM_ELEMENT_VIS_ID,EXT_SYS_DIM_ELEMENT.DESCRIPTION,EXT_SYS_DIM_ELEMENT.CREDIT_DEBIT,EXT_SYS_DIM_ELEMENT.DISABLED,EXT_SYS_DIM_ELEMENT.NOT_PLANNABLE";
/*     */   protected static final String SQL_LOAD = " from EXT_SYS_DIM_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into EXT_SYS_DIM_ELEMENT ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,DIM_ELEMENT_VIS_ID,DESCRIPTION,CREDIT_DEBIT,DISABLED,NOT_PLANNABLE) values ( ?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update EXT_SYS_DIM_ELEMENT set DESCRIPTION = ?,CREDIT_DEBIT = ?,DISABLED = ?,NOT_PLANNABLE = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from EXT_SYS_DIM_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from EXT_SYS_DIM_ELEMENT,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID ,EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID ,EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID ,EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID ,EXT_SYS_DIM_ELEMENT.DIM_ELEMENT_VIS_ID";
/*     */   protected static final String SQL_GET_ALL = " from EXT_SYS_DIM_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ";
/*     */   protected ExtSysDimElementEVO mDetails;
/*     */ 
/*     */   public ExtSysDimElementDAO(Connection connection)
/*     */   {
/*  41 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ExtSysDimElementDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ExtSysDimElementDAO(DataSource ds)
/*     */   {
/*  57 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ExtSysDimElementPK getPK()
/*     */   {
/*  65 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ExtSysDimElementEVO details)
/*     */   {
/*  74 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private ExtSysDimElementEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  94 */     int col = 1;
/*  95 */     ExtSysDimElementEVO evo = new ExtSysDimElementEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"));
/*     */ 
/* 107 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ExtSysDimElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 112 */     int col = startCol_;
/* 113 */     stmt_.setInt(col++, evo_.getExternalSystemId());
/* 114 */     stmt_.setString(col++, evo_.getCompanyVisId());
/* 115 */     stmt_.setString(col++, evo_.getLedgerVisId());
/* 116 */     stmt_.setString(col++, evo_.getDimensionVisId());
/* 117 */     stmt_.setString(col++, evo_.getDimElementVisId());
/* 118 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ExtSysDimElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 123 */     int col = startCol_;
/* 124 */     stmt_.setString(col++, evo_.getDescription());
/* 125 */     stmt_.setInt(col++, evo_.getCreditDebit());
/* 126 */     if (evo_.getDisabled())
/* 127 */       stmt_.setString(col++, "Y");
/*     */     else
/* 129 */       stmt_.setString(col++, " ");
/* 130 */     if (evo_.getNotPlannable())
/* 131 */       stmt_.setString(col++, "Y");
/*     */     else
/* 133 */       stmt_.setString(col++, " ");
/* 134 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ExtSysDimElementPK pk)
/*     */     throws ValidationException
/*     */   {
/* 154 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 156 */     PreparedStatement stmt = null;
/* 157 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 161 */       stmt = getConnection().prepareStatement("select EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID,EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID,EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_DIM_ELEMENT.DIM_ELEMENT_VIS_ID,EXT_SYS_DIM_ELEMENT.DESCRIPTION,EXT_SYS_DIM_ELEMENT.CREDIT_DEBIT,EXT_SYS_DIM_ELEMENT.DISABLED,EXT_SYS_DIM_ELEMENT.NOT_PLANNABLE from EXT_SYS_DIM_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ");
/*     */ 
/* 164 */       int col = 1;
/* 165 */       stmt.setInt(col++, pk.getExternalSystemId());
/* 166 */       stmt.setString(col++, pk.getCompanyVisId());
/* 167 */       stmt.setString(col++, pk.getLedgerVisId());
/* 168 */       stmt.setString(col++, pk.getDimensionVisId());
/* 169 */       stmt.setString(col++, pk.getDimElementVisId());
/*     */ 
/* 171 */       resultSet = stmt.executeQuery();
/*     */ 
/* 173 */       if (!resultSet.next()) {
/* 174 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 177 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 178 */       if (this.mDetails.isModified())
/* 179 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 183 */       throw handleSQLException(pk, "select EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID,EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID,EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_DIM_ELEMENT.DIM_ELEMENT_VIS_ID,EXT_SYS_DIM_ELEMENT.DESCRIPTION,EXT_SYS_DIM_ELEMENT.CREDIT_DEBIT,EXT_SYS_DIM_ELEMENT.DISABLED,EXT_SYS_DIM_ELEMENT.NOT_PLANNABLE from EXT_SYS_DIM_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 187 */       closeResultSet(resultSet);
/* 188 */       closeStatement(stmt);
/* 189 */       closeConnection();
/*     */ 
/* 191 */       if (timer != null)
/* 192 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 227 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 228 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 232 */       stmt = getConnection().prepareStatement("insert into EXT_SYS_DIM_ELEMENT ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,DIM_ELEMENT_VIS_ID,DESCRIPTION,CREDIT_DEBIT,DISABLED,NOT_PLANNABLE) values ( ?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 235 */       int col = 1;
/* 236 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 237 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 240 */       int resultCount = stmt.executeUpdate();
/* 241 */       if (resultCount != 1)
/*     */       {
/* 243 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 246 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 250 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXT_SYS_DIM_ELEMENT ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,DIM_ELEMENT_VIS_ID,DESCRIPTION,CREDIT_DEBIT,DISABLED,NOT_PLANNABLE) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 254 */       closeStatement(stmt);
/* 255 */       closeConnection();
/*     */ 
/* 257 */       if (timer != null)
/* 258 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 285 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 289 */     PreparedStatement stmt = null;
/*     */ 
/* 291 */     boolean mainChanged = this.mDetails.isModified();
/* 292 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 295 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 297 */         stmt = getConnection().prepareStatement("update EXT_SYS_DIM_ELEMENT set DESCRIPTION = ?,CREDIT_DEBIT = ?,DISABLED = ?,NOT_PLANNABLE = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ");
/*     */ 
/* 300 */         int col = 1;
/* 301 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 302 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 305 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 308 */         if (resultCount != 1) {
/* 309 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 312 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 321 */       throw handleSQLException(getPK(), "update EXT_SYS_DIM_ELEMENT set DESCRIPTION = ?,CREDIT_DEBIT = ?,DISABLED = ?,NOT_PLANNABLE = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 325 */       closeStatement(stmt);
/* 326 */       closeConnection();
/*     */ 
/* 328 */       if ((timer != null) && (
/* 329 */         (mainChanged) || (dependantChanged)))
/* 330 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 353 */     if (items == null) {
/* 354 */       return false;
/*     */     }
/* 356 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 357 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 359 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 364 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 365 */       while (iter2.hasNext())
/*     */       {
/* 367 */         this.mDetails = ((ExtSysDimElementEVO)iter2.next());
/*     */ 
/* 370 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 372 */         somethingChanged = true;
/*     */ 
/* 375 */         if (deleteStmt == null) {
/* 376 */           deleteStmt = getConnection().prepareStatement("delete from EXT_SYS_DIM_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ");
/*     */         }
/*     */ 
/* 379 */         int col = 1;
/* 380 */         deleteStmt.setInt(col++, this.mDetails.getExternalSystemId());
/* 381 */         deleteStmt.setString(col++, this.mDetails.getCompanyVisId());
/* 382 */         deleteStmt.setString(col++, this.mDetails.getLedgerVisId());
/* 383 */         deleteStmt.setString(col++, this.mDetails.getDimensionVisId());
/* 384 */         deleteStmt.setString(col++, this.mDetails.getDimElementVisId());
/*     */ 
/* 386 */         if (this._log.isDebugEnabled()) {
/* 387 */           this._log.debug("update", "ExtSysDimElement deleting ExternalSystemId=" + this.mDetails.getExternalSystemId() + ",CompanyVisId=" + this.mDetails.getCompanyVisId() + ",LedgerVisId=" + this.mDetails.getLedgerVisId() + ",DimensionVisId=" + this.mDetails.getDimensionVisId() + ",DimElementVisId=" + this.mDetails.getDimElementVisId());
/*     */         }
/*     */ 
/* 396 */         deleteStmt.addBatch();
/*     */ 
/* 399 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 404 */       if (deleteStmt != null)
/*     */       {
/* 406 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 408 */         deleteStmt.executeBatch();
/*     */ 
/* 410 */         if (timer2 != null) {
/* 411 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 415 */       Iterator iter1 = items.values().iterator();
/* 416 */       while (iter1.hasNext())
/*     */       {
/* 418 */         this.mDetails = ((ExtSysDimElementEVO)iter1.next());
/*     */ 
/* 420 */         if (this.mDetails.insertPending())
/*     */         {
/* 422 */           somethingChanged = true;
/* 423 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 426 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 428 */         somethingChanged = true;
/* 429 */         doStore();
/*     */       }
/*     */ 
/* 440 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 444 */       throw handleSQLException("delete from EXT_SYS_DIM_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 448 */       if (deleteStmt != null)
/*     */       {
/* 450 */         closeStatement(deleteStmt);
/* 451 */         closeConnection();
/*     */       }
/*     */ 
/* 454 */       this.mDetails = null;
/*     */ 
/* 456 */       if ((somethingChanged) && 
/* 457 */         (timer != null))
/* 458 */         timer.logDebug("update", "collection"); 
/* 458 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ExternalSystemPK entityPK, Collection owners, String dependants)
/*     */   {
/* 500 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 502 */     PreparedStatement stmt = null;
/* 503 */     ResultSet resultSet = null;
/*     */ 
/* 505 */     int itemCount = 0;
/*     */ 
/* 507 */     ExtSysDimensionEVO owningEVO = null;
/* 508 */     Iterator ownersIter = owners.iterator();
/* 509 */     while (ownersIter.hasNext())
/*     */     {
/* 511 */       owningEVO = (ExtSysDimensionEVO)ownersIter.next();
/* 512 */       owningEVO.setExtSysDimElementsAllItemsLoaded(true);
/*     */     }
/* 514 */     ownersIter = owners.iterator();
/* 515 */     owningEVO = (ExtSysDimensionEVO)ownersIter.next();
/* 516 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 520 */       stmt = getConnection().prepareStatement("select EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID,EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID,EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_DIM_ELEMENT.DIM_ELEMENT_VIS_ID,EXT_SYS_DIM_ELEMENT.DESCRIPTION,EXT_SYS_DIM_ELEMENT.CREDIT_DEBIT,EXT_SYS_DIM_ELEMENT.DISABLED,EXT_SYS_DIM_ELEMENT.NOT_PLANNABLE from EXT_SYS_DIM_ELEMENT,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID ,EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID ,EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID ,EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID ,EXT_SYS_DIM_ELEMENT.DIM_ELEMENT_VIS_ID");
/*     */ 
/* 522 */       int col = 1;
/* 523 */       stmt.setInt(col++, entityPK.getExternalSystemId());
/*     */ 
/* 525 */       resultSet = stmt.executeQuery();
/*     */ 
/* 528 */       while (resultSet.next())
/*     */       {
/* 530 */         itemCount++;
/* 531 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 536 */         while ((this.mDetails.getExternalSystemId() != owningEVO.getExternalSystemId()) || (!this.mDetails.getCompanyVisId().equals(owningEVO.getCompanyVisId())) || (!this.mDetails.getLedgerVisId().equals(owningEVO.getLedgerVisId())) || (!this.mDetails.getDimensionVisId().equals(owningEVO.getDimensionVisId())))
/*     */         {
/* 543 */           if (!ownersIter.hasNext())
/*     */           {
/* 545 */             this._log.debug("bulkGetAll", "can't find owning [ExternalSystemId=" + this.mDetails.getExternalSystemId() + "CompanyVisId=" + this.mDetails.getCompanyVisId() + "LedgerVisId=" + this.mDetails.getLedgerVisId() + "DimensionVisId=" + this.mDetails.getDimensionVisId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 552 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 553 */             ownersIter = owners.iterator();
/* 554 */             while (ownersIter.hasNext())
/*     */             {
/* 556 */               owningEVO = (ExtSysDimensionEVO)ownersIter.next();
/* 557 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 559 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 561 */           owningEVO = (ExtSysDimensionEVO)ownersIter.next();
/*     */         }
/* 563 */         if (owningEVO.getExtSysDimElements() == null)
/*     */         {
/* 565 */           theseItems = new ArrayList();
/* 566 */           owningEVO.setExtSysDimElements(theseItems);
/* 567 */           owningEVO.setExtSysDimElementsAllItemsLoaded(true);
/*     */         }
/* 569 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 572 */       if (timer != null) {
/* 573 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 578 */       throw handleSQLException("select EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID,EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID,EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_DIM_ELEMENT.DIM_ELEMENT_VIS_ID,EXT_SYS_DIM_ELEMENT.DESCRIPTION,EXT_SYS_DIM_ELEMENT.CREDIT_DEBIT,EXT_SYS_DIM_ELEMENT.DISABLED,EXT_SYS_DIM_ELEMENT.NOT_PLANNABLE from EXT_SYS_DIM_ELEMENT,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID ,EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID ,EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID ,EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID ,EXT_SYS_DIM_ELEMENT.DIM_ELEMENT_VIS_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 582 */       closeResultSet(resultSet);
/* 583 */       closeStatement(stmt);
/* 584 */       closeConnection();
/*     */ 
/* 586 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectExternalSystemId, String selectCompanyVisId, String selectLedgerVisId, String selectDimensionVisId, String dependants, Collection currentList)
/*     */   {
/* 620 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 621 */     PreparedStatement stmt = null;
/* 622 */     ResultSet resultSet = null;
/*     */ 
/* 624 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 628 */       stmt = getConnection().prepareStatement("select EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID,EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID,EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_DIM_ELEMENT.DIM_ELEMENT_VIS_ID,EXT_SYS_DIM_ELEMENT.DESCRIPTION,EXT_SYS_DIM_ELEMENT.CREDIT_DEBIT,EXT_SYS_DIM_ELEMENT.DISABLED,EXT_SYS_DIM_ELEMENT.NOT_PLANNABLE from EXT_SYS_DIM_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ");
/*     */ 
/* 630 */       int col = 1;
/* 631 */       stmt.setInt(col++, selectExternalSystemId);
/* 632 */       stmt.setString(col++, selectCompanyVisId);
/* 633 */       stmt.setString(col++, selectLedgerVisId);
/* 634 */       stmt.setString(col++, selectDimensionVisId);
/*     */ 
/* 636 */       resultSet = stmt.executeQuery();
/*     */ 
/* 638 */       while (resultSet.next())
/*     */       {
/* 640 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 643 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 646 */       if (currentList != null)
/*     */       {
/* 649 */         ListIterator iter = items.listIterator();
/* 650 */         ExtSysDimElementEVO currentEVO = null;
/* 651 */         ExtSysDimElementEVO newEVO = null;
/* 652 */         while (iter.hasNext())
/*     */         {
/* 654 */           newEVO = (ExtSysDimElementEVO)iter.next();
/* 655 */           Iterator iter2 = currentList.iterator();
/* 656 */           while (iter2.hasNext())
/*     */           {
/* 658 */             currentEVO = (ExtSysDimElementEVO)iter2.next();
/* 659 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 661 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 667 */         Iterator iter2 = currentList.iterator();
/* 668 */         while (iter2.hasNext())
/*     */         {
/* 670 */           currentEVO = (ExtSysDimElementEVO)iter2.next();
/* 671 */           if (currentEVO.insertPending()) {
/* 672 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 676 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 680 */       throw handleSQLException("select EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID,EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID,EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_DIM_ELEMENT.DIM_ELEMENT_VIS_ID,EXT_SYS_DIM_ELEMENT.DESCRIPTION,EXT_SYS_DIM_ELEMENT.CREDIT_DEBIT,EXT_SYS_DIM_ELEMENT.DISABLED,EXT_SYS_DIM_ELEMENT.NOT_PLANNABLE from EXT_SYS_DIM_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 684 */       closeResultSet(resultSet);
/* 685 */       closeStatement(stmt);
/* 686 */       closeConnection();
/*     */ 
/* 688 */       if (timer != null) {
/* 689 */         timer.logDebug("getAll", " ExternalSystemId=" + selectExternalSystemId + ",CompanyVisId=" + selectCompanyVisId + ",LedgerVisId=" + selectLedgerVisId + ",DimensionVisId=" + selectDimensionVisId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 697 */     return items;
/*     */   }
/*     */ 
/*     */   public ExtSysDimElementEVO getDetails(ExternalSystemCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 711 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 714 */     if (this.mDetails == null) {
/* 715 */       doLoad(((ExtSysDimElementCK)paramCK).getExtSysDimElementPK());
/*     */     }
/* 717 */     else if (!this.mDetails.getPK().equals(((ExtSysDimElementCK)paramCK).getExtSysDimElementPK())) {
/* 718 */       doLoad(((ExtSysDimElementCK)paramCK).getExtSysDimElementPK());
/*     */     }
/*     */ 
/* 721 */     ExtSysDimElementEVO details = new ExtSysDimElementEVO();
/* 722 */     details = this.mDetails.deepClone();
/*     */ 
/* 724 */     if (timer != null) {
/* 725 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 727 */     return details;
/*     */   }
/*     */ 
/*     */   public ExtSysDimElementEVO getDetails(ExternalSystemCK paramCK, ExtSysDimElementEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 733 */     ExtSysDimElementEVO savedEVO = this.mDetails;
/* 734 */     this.mDetails = paramEVO;
/* 735 */     ExtSysDimElementEVO newEVO = getDetails(paramCK, dependants);
/* 736 */     this.mDetails = savedEVO;
/* 737 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public ExtSysDimElementEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 743 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 747 */     ExtSysDimElementEVO details = this.mDetails.deepClone();
/*     */ 
/* 749 */     if (timer != null) {
/* 750 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 752 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 757 */     return "ExtSysDimElement";
/*     */   }
/*     */ 
/*     */   public ExtSysDimElementRefImpl getRef(ExtSysDimElementPK paramExtSysDimElementPK)
/*     */   {
/* 762 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 763 */     PreparedStatement stmt = null;
/* 764 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 767 */       stmt = getConnection().prepareStatement("select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID from EXT_SYS_DIM_ELEMENT,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_LEDGER,EXT_SYS_DIMENSION where 1=1 and EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID = ? and EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID = ? and EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID = ? and EXT_SYS_DIM_ELEMENT.DIM_ELEMENT_VIS_ID = ? and EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_DIMENSION.DIMENSION_VIS_ID = EXT_SYS_LEDGER.DIMENSION_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_LEDGER.LEDGER_VIS_ID = EXT_SYS_COMPANY.LEDGER_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID");
/* 768 */       int col = 1;
/* 769 */       stmt.setInt(col++, paramExtSysDimElementPK.getExternalSystemId());
/* 770 */       stmt.setString(col++, paramExtSysDimElementPK.getCompanyVisId());
/* 771 */       stmt.setString(col++, paramExtSysDimElementPK.getLedgerVisId());
/* 772 */       stmt.setString(col++, paramExtSysDimElementPK.getDimensionVisId());
/* 773 */       stmt.setString(col++, paramExtSysDimElementPK.getDimElementVisId());
/*     */ 
/* 775 */       resultSet = stmt.executeQuery();
/*     */ 
/* 777 */       if (!resultSet.next()) {
/* 778 */         throw new RuntimeException(getEntityName() + " getRef " + paramExtSysDimElementPK + " not found");
/*     */       }
/* 780 */       col = 2;
/* 781 */       ExternalSystemPK newExternalSystemPK = new ExternalSystemPK(resultSet.getInt(col++));
/*     */ 
/* 785 */       ExtSysCompanyPK newExtSysCompanyPK = new ExtSysCompanyPK(resultSet.getInt(col++), resultSet.getString(col++));
/*     */ 
/* 790 */       ExtSysLedgerPK newExtSysLedgerPK = new ExtSysLedgerPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++));
/*     */ 
/* 796 */       ExtSysDimensionPK newExtSysDimensionPK = new ExtSysDimensionPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++));
/*     */ 
/* 803 */       String textExtSysDimElement = "";
/* 804 */       ExtSysDimElementCK ckExtSysDimElement = new ExtSysDimElementCK(newExternalSystemPK, newExtSysCompanyPK, newExtSysLedgerPK, newExtSysDimensionPK, paramExtSysDimElementPK);
/*     */ 
/* 812 */       ExtSysDimElementRefImpl localExtSysDimElementRefImpl = new ExtSysDimElementRefImpl(ckExtSysDimElement, textExtSysDimElement);
/*     */       return localExtSysDimElementRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 817 */       throw handleSQLException(paramExtSysDimElementPK, "select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID from EXT_SYS_DIM_ELEMENT,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_LEDGER,EXT_SYS_DIMENSION where 1=1 and EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID = ? and EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID = ? and EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID = ? and EXT_SYS_DIM_ELEMENT.DIM_ELEMENT_VIS_ID = ? and EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_DIMENSION.DIMENSION_VIS_ID = EXT_SYS_LEDGER.DIMENSION_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_LEDGER.LEDGER_VIS_ID = EXT_SYS_COMPANY.LEDGER_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 821 */       closeResultSet(resultSet);
/* 822 */       closeStatement(stmt);
/* 823 */       closeConnection();
/*     */ 
/* 825 */       if (timer != null)
/* 826 */         timer.logDebug("getRef", paramExtSysDimElementPK); 
/* 826 */     }
/*     */   }
/*     */ 
/*     */   public int checkConstraintViolations(int externalSystemId, int maxToReport, PrintWriter log)
/*     */   {
/* 842 */     SqlExecutor sqlExecutor = null;
/* 843 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 847 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "select external_system_id, company_vis_id, ledger_vis_id, dimension_vis_id, dim_element_vis_id", "from ext_sys_dim_element", "where external_system_id = <externalSystemId>", "group by external_system_id, company_vis_id, ledger_vis_id, dimension_vis_id, dim_element_vis_id", "having count(*) > 1" });
/*     */ 
/* 854 */       sqlExecutor = new SqlExecutor("checkConstraintViolations", getDataSource(), sqlBuilder, this._log);
/*     */ 
/* 856 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/*     */ 
/* 858 */       rs = sqlExecutor.getResultSet();
/*     */ 
/* 860 */       int count = 0;
/* 861 */       while ((rs.next()) && (count < maxToReport))
/*     */       {
/* 863 */         log.print("Found duplicate dimension element details: company:[" + rs.getString("COMPANY_VIS_ID") + "] " + " ledger:[" + rs.getString("LEDGER_VIS_ID") + "]" + " dimension:[" + rs.getString("DIMENSION_VIS_ID") + "]" + " dimension element:[" + rs.getString("DIM_ELEMENT_VIS_ID") + "]");
/*     */ 
/* 868 */         count++;
/*     */       }
/*     */ 
/* 871 */       int i = count;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 875 */       throw handleSQLException("checkConstraintViolations", e);
/*     */     }
/*     */     finally
/*     */     {
/* 879 */       closeResultSet(rs);
/* 880 */       sqlExecutor.close(); } //throw localObject;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extsys.ExtSysDimElementDAO
 * JD-Core Version:    0.6.0
 */