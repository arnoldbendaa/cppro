/*     */ package com.cedar.cp.ejb.impl.extsys;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysDimensionPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysHierElemFeedCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysHierElemFeedPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysHierElemFeedRefImpl;
/*     */ import com.cedar.cp.dto.extsys.ExtSysHierElementPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysHierarchyPK;
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
/*     */ public class ExtSysHierElemFeedDAO extends AbstractDAO
/*     */ {
/*  34 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIM_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.IDX";
/*     */   protected static final String SQL_LOAD = " from EXT_SYS_HIER_ELEM_FEED where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into EXT_SYS_HIER_ELEM_FEED ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,HIERARCHY_VIS_ID,HIER_ELEMENT_VIS_ID,DIM_ELEMENT_VIS_ID,IDX) values ( ?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update EXT_SYS_HIER_ELEM_FEED set HIERARCHY_VIS_ID = ?,IDX = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from EXT_SYS_HIER_ELEM_FEED where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from EXT_SYS_HIER_ELEM_FEED,EXT_SYS_HIER_ELEMENT,EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID = EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID = EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID and EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID = EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.DIM_ELEMENT_VIS_ID";
/*     */   protected static final String SQL_GET_ALL = " from EXT_SYS_HIER_ELEM_FEED where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? ";
/*     */   protected ExtSysHierElemFeedEVO mDetails;
/*     */ 
/*     */   public ExtSysHierElemFeedDAO(Connection connection)
/*     */   {
/*  41 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ExtSysHierElemFeedDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ExtSysHierElemFeedDAO(DataSource ds)
/*     */   {
/*  57 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ExtSysHierElemFeedPK getPK()
/*     */   {
/*  65 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ExtSysHierElemFeedEVO details)
/*     */   {
/*  74 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private ExtSysHierElemFeedEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  93 */     int col = 1;
/*  94 */     ExtSysHierElemFeedEVO evo = new ExtSysHierElemFeedEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++));
/*     */ 
/* 105 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ExtSysHierElemFeedEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 110 */     int col = startCol_;
/* 111 */     stmt_.setInt(col++, evo_.getExternalSystemId());
/* 112 */     stmt_.setString(col++, evo_.getCompanyVisId());
/* 113 */     stmt_.setString(col++, evo_.getLedgerVisId());
/* 114 */     stmt_.setString(col++, evo_.getDimensionVisId());
/* 115 */     stmt_.setString(col++, evo_.getHierElementVisId());
/* 116 */     stmt_.setString(col++, evo_.getDimElementVisId());
/* 117 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ExtSysHierElemFeedEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 122 */     int col = startCol_;
/* 123 */     stmt_.setString(col++, evo_.getHierarchyVisId());
/* 124 */     stmt_.setInt(col++, evo_.getIdx());
/* 125 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ExtSysHierElemFeedPK pk)
/*     */     throws ValidationException
/*     */   {
/* 146 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 148 */     PreparedStatement stmt = null;
/* 149 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 153 */       stmt = getConnection().prepareStatement("select EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIM_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.IDX from EXT_SYS_HIER_ELEM_FEED where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ");
/*     */ 
/* 156 */       int col = 1;
/* 157 */       stmt.setInt(col++, pk.getExternalSystemId());
/* 158 */       stmt.setString(col++, pk.getCompanyVisId());
/* 159 */       stmt.setString(col++, pk.getLedgerVisId());
/* 160 */       stmt.setString(col++, pk.getDimensionVisId());
/* 161 */       stmt.setString(col++, pk.getHierElementVisId());
/* 162 */       stmt.setString(col++, pk.getDimElementVisId());
/*     */ 
/* 164 */       resultSet = stmt.executeQuery();
/*     */ 
/* 166 */       if (!resultSet.next()) {
/* 167 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 170 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 171 */       if (this.mDetails.isModified())
/* 172 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 176 */       throw handleSQLException(pk, "select EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIM_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.IDX from EXT_SYS_HIER_ELEM_FEED where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 180 */       closeResultSet(resultSet);
/* 181 */       closeStatement(stmt);
/* 182 */       closeConnection();
/*     */ 
/* 184 */       if (timer != null)
/* 185 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 218 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 219 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 223 */       stmt = getConnection().prepareStatement("insert into EXT_SYS_HIER_ELEM_FEED ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,HIERARCHY_VIS_ID,HIER_ELEMENT_VIS_ID,DIM_ELEMENT_VIS_ID,IDX) values ( ?,?,?,?,?,?,?,?)");
/*     */ 
/* 226 */       int col = 1;
/* 227 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 228 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 231 */       int resultCount = stmt.executeUpdate();
/* 232 */       if (resultCount != 1)
/*     */       {
/* 234 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 237 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 241 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXT_SYS_HIER_ELEM_FEED ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,HIERARCHY_VIS_ID,HIER_ELEMENT_VIS_ID,DIM_ELEMENT_VIS_ID,IDX) values ( ?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 245 */       closeStatement(stmt);
/* 246 */       closeConnection();
/*     */ 
/* 248 */       if (timer != null)
/* 249 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 275 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 279 */     PreparedStatement stmt = null;
/*     */ 
/* 281 */     boolean mainChanged = this.mDetails.isModified();
/* 282 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 285 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 287 */         stmt = getConnection().prepareStatement("update EXT_SYS_HIER_ELEM_FEED set HIERARCHY_VIS_ID = ?,IDX = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ");
/*     */ 
/* 290 */         int col = 1;
/* 291 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 292 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 295 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 298 */         if (resultCount != 1) {
/* 299 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 302 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 311 */       throw handleSQLException(getPK(), "update EXT_SYS_HIER_ELEM_FEED set HIERARCHY_VIS_ID = ?,IDX = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 315 */       closeStatement(stmt);
/* 316 */       closeConnection();
/*     */ 
/* 318 */       if ((timer != null) && (
/* 319 */         (mainChanged) || (dependantChanged)))
/* 320 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 344 */     if (items == null) {
/* 345 */       return false;
/*     */     }
/* 347 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 348 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 350 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 355 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 356 */       while (iter2.hasNext())
/*     */       {
/* 358 */         this.mDetails = ((ExtSysHierElemFeedEVO)iter2.next());
/*     */ 
/* 361 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 363 */         somethingChanged = true;
/*     */ 
/* 366 */         if (deleteStmt == null) {
/* 367 */           deleteStmt = getConnection().prepareStatement("delete from EXT_SYS_HIER_ELEM_FEED where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ");
/*     */         }
/*     */ 
/* 370 */         int col = 1;
/* 371 */         deleteStmt.setInt(col++, this.mDetails.getExternalSystemId());
/* 372 */         deleteStmt.setString(col++, this.mDetails.getCompanyVisId());
/* 373 */         deleteStmt.setString(col++, this.mDetails.getLedgerVisId());
/* 374 */         deleteStmt.setString(col++, this.mDetails.getDimensionVisId());
/* 375 */         deleteStmt.setString(col++, this.mDetails.getHierElementVisId());
/* 376 */         deleteStmt.setString(col++, this.mDetails.getDimElementVisId());
/*     */ 
/* 378 */         if (this._log.isDebugEnabled()) {
/* 379 */           this._log.debug("update", "ExtSysHierElemFeed deleting ExternalSystemId=" + this.mDetails.getExternalSystemId() + ",CompanyVisId=" + this.mDetails.getCompanyVisId() + ",LedgerVisId=" + this.mDetails.getLedgerVisId() + ",DimensionVisId=" + this.mDetails.getDimensionVisId() + ",HierElementVisId=" + this.mDetails.getHierElementVisId() + ",DimElementVisId=" + this.mDetails.getDimElementVisId());
/*     */         }
/*     */ 
/* 389 */         deleteStmt.addBatch();
/*     */ 
/* 392 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 397 */       if (deleteStmt != null)
/*     */       {
/* 399 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 401 */         deleteStmt.executeBatch();
/*     */ 
/* 403 */         if (timer2 != null) {
/* 404 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 408 */       Iterator iter1 = items.values().iterator();
/* 409 */       while (iter1.hasNext())
/*     */       {
/* 411 */         this.mDetails = ((ExtSysHierElemFeedEVO)iter1.next());
/*     */ 
/* 413 */         if (this.mDetails.insertPending())
/*     */         {
/* 415 */           somethingChanged = true;
/* 416 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 419 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 421 */         somethingChanged = true;
/* 422 */         doStore();
/*     */       }
/*     */ 
/* 433 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 437 */       throw handleSQLException("delete from EXT_SYS_HIER_ELEM_FEED where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? AND DIM_ELEMENT_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 441 */       if (deleteStmt != null)
/*     */       {
/* 443 */         closeStatement(deleteStmt);
/* 444 */         closeConnection();
/*     */       }
/*     */ 
/* 447 */       this.mDetails = null;
/*     */ 
/* 449 */       if ((somethingChanged) && 
/* 450 */         (timer != null))
/* 451 */         timer.logDebug("update", "collection"); 
/* 451 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ExternalSystemPK entityPK, Collection owners, String dependants)
/*     */   {
/* 511 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 513 */     PreparedStatement stmt = null;
/* 514 */     ResultSet resultSet = null;
/*     */ 
/* 516 */     int itemCount = 0;
/*     */ 
/* 518 */     ExtSysHierElementEVO owningEVO = null;
/* 519 */     Iterator ownersIter = owners.iterator();
/* 520 */     while (ownersIter.hasNext())
/*     */     {
/* 522 */       owningEVO = (ExtSysHierElementEVO)ownersIter.next();
/* 523 */       owningEVO.setExtSysHierElemFeedsAllItemsLoaded(true);
/*     */     }
/* 525 */     ownersIter = owners.iterator();
/* 526 */     owningEVO = (ExtSysHierElementEVO)ownersIter.next();
/* 527 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 531 */       stmt = getConnection().prepareStatement("select EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIM_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.IDX from EXT_SYS_HIER_ELEM_FEED,EXT_SYS_HIER_ELEMENT,EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID = EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID = EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID and EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID = EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.DIM_ELEMENT_VIS_ID");
/*     */ 
/* 533 */       int col = 1;
/* 534 */       stmt.setInt(col++, entityPK.getExternalSystemId());
/*     */ 
/* 536 */       resultSet = stmt.executeQuery();
/*     */ 
/* 539 */       while (resultSet.next())
/*     */       {
/* 541 */         itemCount++;
/* 542 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 547 */         while ((this.mDetails.getExternalSystemId() != owningEVO.getExternalSystemId()) || (!this.mDetails.getCompanyVisId().equals(owningEVO.getCompanyVisId())) || (!this.mDetails.getLedgerVisId().equals(owningEVO.getLedgerVisId())) || (!this.mDetails.getDimensionVisId().equals(owningEVO.getDimensionVisId())) || (!this.mDetails.getHierarchyVisId().equals(owningEVO.getHierarchyVisId())) || (!this.mDetails.getHierElementVisId().equals(owningEVO.getHierElementVisId())))
/*     */         {
/* 556 */           if (!ownersIter.hasNext())
/*     */           {
/* 558 */             this._log.debug("bulkGetAll", "can't find owning [ExternalSystemId=" + this.mDetails.getExternalSystemId() + "CompanyVisId=" + this.mDetails.getCompanyVisId() + "LedgerVisId=" + this.mDetails.getLedgerVisId() + "DimensionVisId=" + this.mDetails.getDimensionVisId() + "HierarchyVisId=" + this.mDetails.getHierarchyVisId() + "HierElementVisId=" + this.mDetails.getHierElementVisId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 567 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 568 */             ownersIter = owners.iterator();
/* 569 */             while (ownersIter.hasNext())
/*     */             {
/* 571 */               owningEVO = (ExtSysHierElementEVO)ownersIter.next();
/* 572 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 574 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 576 */           owningEVO = (ExtSysHierElementEVO)ownersIter.next();
/*     */         }
/* 578 */         if (owningEVO.getExtSysHierElemFeeds() == null)
/*     */         {
/* 580 */           theseItems = new ArrayList();
/* 581 */           owningEVO.setExtSysHierElemFeeds(theseItems);
/* 582 */           owningEVO.setExtSysHierElemFeedsAllItemsLoaded(true);
/*     */         }
/* 584 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 587 */       if (timer != null) {
/* 588 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 593 */       throw handleSQLException("select EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIM_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.IDX from EXT_SYS_HIER_ELEM_FEED,EXT_SYS_HIER_ELEMENT,EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID = EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID = EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID and EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID = EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID ,EXT_SYS_HIER_ELEM_FEED.DIM_ELEMENT_VIS_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 597 */       closeResultSet(resultSet);
/* 598 */       closeStatement(stmt);
/* 599 */       closeConnection();
/*     */ 
/* 601 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectExternalSystemId, String selectCompanyVisId, String selectLedgerVisId, String selectDimensionVisId, String selectHierarchyVisId, String selectHierElementVisId, String dependants, Collection currentList)
/*     */   {
/* 641 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 642 */     PreparedStatement stmt = null;
/* 643 */     ResultSet resultSet = null;
/*     */ 
/* 645 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 649 */       stmt = getConnection().prepareStatement("select EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIM_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.IDX from EXT_SYS_HIER_ELEM_FEED where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? ");
/*     */ 
/* 651 */       int col = 1;
/* 652 */       stmt.setInt(col++, selectExternalSystemId);
/* 653 */       stmt.setString(col++, selectCompanyVisId);
/* 654 */       stmt.setString(col++, selectLedgerVisId);
/* 655 */       stmt.setString(col++, selectDimensionVisId);
/* 656 */       stmt.setString(col++, selectHierarchyVisId);
/* 657 */       stmt.setString(col++, selectHierElementVisId);
/*     */ 
/* 659 */       resultSet = stmt.executeQuery();
/*     */ 
/* 661 */       while (resultSet.next())
/*     */       {
/* 663 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 666 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 669 */       if (currentList != null)
/*     */       {
/* 672 */         ListIterator iter = items.listIterator();
/* 673 */         ExtSysHierElemFeedEVO currentEVO = null;
/* 674 */         ExtSysHierElemFeedEVO newEVO = null;
/* 675 */         while (iter.hasNext())
/*     */         {
/* 677 */           newEVO = (ExtSysHierElemFeedEVO)iter.next();
/* 678 */           Iterator iter2 = currentList.iterator();
/* 679 */           while (iter2.hasNext())
/*     */           {
/* 681 */             currentEVO = (ExtSysHierElemFeedEVO)iter2.next();
/* 682 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 684 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 690 */         Iterator iter2 = currentList.iterator();
/* 691 */         while (iter2.hasNext())
/*     */         {
/* 693 */           currentEVO = (ExtSysHierElemFeedEVO)iter2.next();
/* 694 */           if (currentEVO.insertPending()) {
/* 695 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 699 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 703 */       throw handleSQLException("select EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.DIM_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEM_FEED.IDX from EXT_SYS_HIER_ELEM_FEED where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 707 */       closeResultSet(resultSet);
/* 708 */       closeStatement(stmt);
/* 709 */       closeConnection();
/*     */ 
/* 711 */       if (timer != null) {
/* 712 */         timer.logDebug("getAll", " ExternalSystemId=" + selectExternalSystemId + ",CompanyVisId=" + selectCompanyVisId + ",LedgerVisId=" + selectLedgerVisId + ",DimensionVisId=" + selectDimensionVisId + ",HierarchyVisId=" + selectHierarchyVisId + ",HierElementVisId=" + selectHierElementVisId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 722 */     return items;
/*     */   }
/*     */ 
/*     */   public ExtSysHierElemFeedEVO getDetails(ExternalSystemCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 736 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 739 */     if (this.mDetails == null) {
/* 740 */       doLoad(((ExtSysHierElemFeedCK)paramCK).getExtSysHierElemFeedPK());
/*     */     }
/* 742 */     else if (!this.mDetails.getPK().equals(((ExtSysHierElemFeedCK)paramCK).getExtSysHierElemFeedPK())) {
/* 743 */       doLoad(((ExtSysHierElemFeedCK)paramCK).getExtSysHierElemFeedPK());
/*     */     }
/*     */ 
/* 746 */     ExtSysHierElemFeedEVO details = new ExtSysHierElemFeedEVO();
/* 747 */     details = this.mDetails.deepClone();
/*     */ 
/* 749 */     if (timer != null) {
/* 750 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 752 */     return details;
/*     */   }
/*     */ 
/*     */   public ExtSysHierElemFeedEVO getDetails(ExternalSystemCK paramCK, ExtSysHierElemFeedEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 758 */     ExtSysHierElemFeedEVO savedEVO = this.mDetails;
/* 759 */     this.mDetails = paramEVO;
/* 760 */     ExtSysHierElemFeedEVO newEVO = getDetails(paramCK, dependants);
/* 761 */     this.mDetails = savedEVO;
/* 762 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public ExtSysHierElemFeedEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 768 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 772 */     ExtSysHierElemFeedEVO details = this.mDetails.deepClone();
/*     */ 
/* 774 */     if (timer != null) {
/* 775 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 777 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 782 */     return "ExtSysHierElemFeed";
/*     */   }
/*     */ 
/*     */   public ExtSysHierElemFeedRefImpl getRef(ExtSysHierElemFeedPK paramExtSysHierElemFeedPK)
/*     */   {
/* 787 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 788 */     PreparedStatement stmt = null;
/* 789 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 792 */       stmt = getConnection().prepareStatement("select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID,EXT_SYS_HIERARCHY.COMPANY_VIS_ID,EXT_SYS_HIERARCHY.LEDGER_VIS_ID,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID from EXT_SYS_HIER_ELEM_FEED,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_LEDGER,EXT_SYS_DIMENSION,EXT_SYS_HIERARCHY,EXT_SYS_HIER_ELEMENT where 1=1 and EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.DIM_ELEMENT_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID = EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID = EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID and EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID = EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID = EXT_SYS_HIERARCHY.HIER_ELEMENT_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID = EXT_SYS_DIMENSION.HIERARCHY_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_DIMENSION.DIMENSION_VIS_ID = EXT_SYS_LEDGER.DIMENSION_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_LEDGER.LEDGER_VIS_ID = EXT_SYS_COMPANY.LEDGER_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID");
/* 793 */       int col = 1;
/* 794 */       stmt.setInt(col++, paramExtSysHierElemFeedPK.getExternalSystemId());
/* 795 */       stmt.setString(col++, paramExtSysHierElemFeedPK.getCompanyVisId());
/* 796 */       stmt.setString(col++, paramExtSysHierElemFeedPK.getLedgerVisId());
/* 797 */       stmt.setString(col++, paramExtSysHierElemFeedPK.getDimensionVisId());
/* 798 */       stmt.setString(col++, paramExtSysHierElemFeedPK.getHierElementVisId());
/* 799 */       stmt.setString(col++, paramExtSysHierElemFeedPK.getDimElementVisId());
/*     */ 
/* 801 */       resultSet = stmt.executeQuery();
/*     */ 
/* 803 */       if (!resultSet.next()) {
/* 804 */         throw new RuntimeException(getEntityName() + " getRef " + paramExtSysHierElemFeedPK + " not found");
/*     */       }
/* 806 */       col = 2;
/* 807 */       ExternalSystemPK newExternalSystemPK = new ExternalSystemPK(resultSet.getInt(col++));
/*     */ 
/* 811 */       ExtSysCompanyPK newExtSysCompanyPK = new ExtSysCompanyPK(resultSet.getInt(col++), resultSet.getString(col++));
/*     */ 
/* 816 */       ExtSysLedgerPK newExtSysLedgerPK = new ExtSysLedgerPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++));
/*     */ 
/* 822 */       ExtSysDimensionPK newExtSysDimensionPK = new ExtSysDimensionPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++));
/*     */ 
/* 829 */       ExtSysHierarchyPK newExtSysHierarchyPK = new ExtSysHierarchyPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++));
/*     */ 
/* 837 */       ExtSysHierElementPK newExtSysHierElementPK = new ExtSysHierElementPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++));
/*     */ 
/* 846 */       String textExtSysHierElemFeed = "";
/* 847 */       ExtSysHierElemFeedCK ckExtSysHierElemFeed = new ExtSysHierElemFeedCK(newExternalSystemPK, newExtSysCompanyPK, newExtSysLedgerPK, newExtSysDimensionPK, newExtSysHierarchyPK, newExtSysHierElementPK, paramExtSysHierElemFeedPK);
/*     */ 
/* 857 */       ExtSysHierElemFeedRefImpl localExtSysHierElemFeedRefImpl = new ExtSysHierElemFeedRefImpl(ckExtSysHierElemFeed, textExtSysHierElemFeed);
/*     */       return localExtSysHierElemFeedRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 862 */       throw handleSQLException(paramExtSysHierElemFeedPK, "select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID,EXT_SYS_HIERARCHY.COMPANY_VIS_ID,EXT_SYS_HIERARCHY.LEDGER_VIS_ID,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID from EXT_SYS_HIER_ELEM_FEED,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_LEDGER,EXT_SYS_DIMENSION,EXT_SYS_HIERARCHY,EXT_SYS_HIER_ELEMENT where 1=1 and EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.DIM_ELEMENT_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID = EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID = EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID and EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID = EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID = EXT_SYS_HIERARCHY.HIER_ELEMENT_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID = EXT_SYS_DIMENSION.HIERARCHY_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_DIMENSION.DIMENSION_VIS_ID = EXT_SYS_LEDGER.DIMENSION_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_LEDGER.LEDGER_VIS_ID = EXT_SYS_COMPANY.LEDGER_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 866 */       closeResultSet(resultSet);
/* 867 */       closeStatement(stmt);
/* 868 */       closeConnection();
/*     */ 
/* 870 */       if (timer != null)
/* 871 */         timer.logDebug("getRef", paramExtSysHierElemFeedPK); 
/* 871 */     }
/*     */   }
/*     */ 
/*     */   public int checkConstraintViolations(int externalSystemId, int maxToReport, PrintWriter log)
/*     */   {
/* 889 */     SqlExecutor sqlExecutor = null;
/* 890 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 895 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "select hef.external_system_id,", "       hef.company_vis_id,", "       hef.ledger_vis_id,", "       hef.dimension_vis_id,", "       hef.dim_element_vis_id,", "\t\thef.hierarchy_vis_id,", "\t\tdecode(de.dim_element_vis_id,null,'N','Y') as de_found,", "       hef.hier_element_vis_id,", "\t\tdecode(he.hier_element_vis_id,null,'N','Y') as he_found", "from ext_sys_hier_elem_feed hef", "left join ext_sys_dim_element de on", "(", "  hef.external_system_id = de.external_system_id and", "  hef.company_vis_id = de.company_vis_id and", "  hef.ledger_vis_id = de.ledger_vis_id and", "  hef.dimension_vis_id = de.dimension_vis_id and", "  hef.dim_element_vis_id = de.dim_element_vis_id", ")", "left join ext_sys_hier_element he on", "(", "  hef.external_system_id = he.external_system_id and", "  hef.company_vis_id = he.company_vis_id and", "  hef.ledger_vis_id = he.ledger_vis_id and", "  hef.dimension_vis_id = he.dimension_vis_id and", "  hef.hier_element_vis_id = he.hier_element_vis_id", ")", "where hef.external_system_id = <externalSystemId> and ", "      ( de.dim_element_vis_id is null or", "        he.hier_element_vis_id is null )" });
/*     */ 
/* 927 */       sqlExecutor = new SqlExecutor("checkConstraintViolations", getDataSource(), sqlBuilder, this._log);
/*     */ 
/* 929 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/*     */ 
/* 931 */       rs = sqlExecutor.getResultSet();
/*     */ 
/* 933 */       int count = 0;
/* 934 */       while ((rs.next()) && (count < maxToReport))
/*     */       {
/* 936 */         log.print("Found hierarchy element feed referencing missing dimension element or hierarchy element: company:[" + rs.getString("COMPANY_VIS_ID") + "]" + " ledger vis id:[" + rs.getString("LEDGER_VIS_ID") + "]" + " dimension vis id:[" + rs.getString("DIMENSION_VIS_ID") + "]" + " hierarchy vis id:[" + rs.getString("DIMENSION_VIS_ID") + "]" + " hiererchy element vis id:[" + rs.getString("HIER_ELEMENT_VIS_ID") + "]" + " valid :[" + rs.getString("HE_FOUND") + "]" + " dimension element vis id:[" + rs.getString("DIM_ELEMENT_VIS_ID") + "]" + " valid :[" + rs.getString("DE_FOUND") + "]");
/*     */ 
/* 945 */         count++;
/*     */       }
/*     */ 
/* 948 */       int i = count;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 952 */       throw handleSQLException("checkConstraintViolations", e);
/*     */     }
/*     */     finally
/*     */     {
/* 956 */       closeResultSet(rs);
/* 957 */       sqlExecutor.close(); } //throw localObject;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extsys.ExtSysHierElemFeedDAO
 * JD-Core Version:    0.6.0
 */