/*     */ package com.cedar.cp.ejb.impl.report.tran;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.report.ReportCK;
/*     */ import com.cedar.cp.dto.report.ReportPK;
/*     */ import com.cedar.cp.dto.report.tran.CubePendingTranCK;
/*     */ import com.cedar.cp.dto.report.tran.CubePendingTranPK;
/*     */ import com.cedar.cp.dto.report.tran.CubePendingTranRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.ejb.impl.report.ReportEVO;
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
/*     */ public class CubePendingTranDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select CUBE_PENDING_TRAN.REPORT_ID,CUBE_PENDING_TRAN.FINANCE_CUBE_ID,CUBE_PENDING_TRAN.ROW_NO,CUBE_PENDING_TRAN.DIM0,CUBE_PENDING_TRAN.DIM1,CUBE_PENDING_TRAN.DIM2,CUBE_PENDING_TRAN.DIM3,CUBE_PENDING_TRAN.DIM4,CUBE_PENDING_TRAN.DIM5,CUBE_PENDING_TRAN.DIM6,CUBE_PENDING_TRAN.DIM7,CUBE_PENDING_TRAN.DIM8,CUBE_PENDING_TRAN.DIM9,CUBE_PENDING_TRAN.DATA_TYPE,CUBE_PENDING_TRAN.VALUE";
/*     */   protected static final String SQL_LOAD = " from CUBE_PENDING_TRAN where    REPORT_ID = ? AND FINANCE_CUBE_ID = ? AND ROW_NO = ? ";
/*     */   protected static final String SQL_CREATE = "insert into CUBE_PENDING_TRAN ( REPORT_ID,FINANCE_CUBE_ID,ROW_NO,DIM0,DIM1,DIM2,DIM3,DIM4,DIM5,DIM6,DIM7,DIM8,DIM9,DATA_TYPE,VALUE) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update CUBE_PENDING_TRAN set DIM0 = ?,DIM1 = ?,DIM2 = ?,DIM3 = ?,DIM4 = ?,DIM5 = ?,DIM6 = ?,DIM7 = ?,DIM8 = ?,DIM9 = ?,DATA_TYPE = ?,VALUE = ? where    REPORT_ID = ? AND FINANCE_CUBE_ID = ? AND ROW_NO = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from CUBE_PENDING_TRAN where    REPORT_ID = ? AND FINANCE_CUBE_ID = ? AND ROW_NO = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from CUBE_PENDING_TRAN where 1=1 and CUBE_PENDING_TRAN.REPORT_ID = ? order by  CUBE_PENDING_TRAN.REPORT_ID ,CUBE_PENDING_TRAN.FINANCE_CUBE_ID ,CUBE_PENDING_TRAN.ROW_NO";
/*     */   protected static final String SQL_GET_ALL = " from CUBE_PENDING_TRAN where    REPORT_ID = ? ";
/*     */   protected CubePendingTranEVO mDetails;
/*     */ 
/*     */   public CubePendingTranDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public CubePendingTranDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CubePendingTranDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected CubePendingTranPK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(CubePendingTranEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private CubePendingTranEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 101 */     int col = 1;
/* 102 */     CubePendingTranEVO evo = new CubePendingTranEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getLong(col++));
/*     */ 
/* 120 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(CubePendingTranEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 125 */     int col = startCol_;
/* 126 */     stmt_.setInt(col++, evo_.getReportId());
/* 127 */     stmt_.setInt(col++, evo_.getFinanceCubeId());
/* 128 */     stmt_.setInt(col++, evo_.getRowNo());
/* 129 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(CubePendingTranEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 134 */     int col = startCol_;
/* 135 */     stmt_.setInt(col++, evo_.getDim0());
/* 136 */     stmt_.setInt(col++, evo_.getDim1());
/* 137 */     stmt_.setInt(col++, evo_.getDim2());
/* 138 */     stmt_.setInt(col++, evo_.getDim3());
/* 139 */     stmt_.setInt(col++, evo_.getDim4());
/* 140 */     stmt_.setInt(col++, evo_.getDim5());
/* 141 */     stmt_.setInt(col++, evo_.getDim6());
/* 142 */     stmt_.setInt(col++, evo_.getDim7());
/* 143 */     stmt_.setInt(col++, evo_.getDim8());
/* 144 */     stmt_.setInt(col++, evo_.getDim9());
/* 145 */     stmt_.setString(col++, evo_.getDataType());
/* 146 */     stmt_.setLong(col++, evo_.getValue());
/* 147 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(CubePendingTranPK pk)
/*     */     throws ValidationException
/*     */   {
/* 165 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 167 */     PreparedStatement stmt = null;
/* 168 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 172 */       stmt = getConnection().prepareStatement("select CUBE_PENDING_TRAN.REPORT_ID,CUBE_PENDING_TRAN.FINANCE_CUBE_ID,CUBE_PENDING_TRAN.ROW_NO,CUBE_PENDING_TRAN.DIM0,CUBE_PENDING_TRAN.DIM1,CUBE_PENDING_TRAN.DIM2,CUBE_PENDING_TRAN.DIM3,CUBE_PENDING_TRAN.DIM4,CUBE_PENDING_TRAN.DIM5,CUBE_PENDING_TRAN.DIM6,CUBE_PENDING_TRAN.DIM7,CUBE_PENDING_TRAN.DIM8,CUBE_PENDING_TRAN.DIM9,CUBE_PENDING_TRAN.DATA_TYPE,CUBE_PENDING_TRAN.VALUE from CUBE_PENDING_TRAN where    REPORT_ID = ? AND FINANCE_CUBE_ID = ? AND ROW_NO = ? ");
/*     */ 
/* 175 */       int col = 1;
/* 176 */       stmt.setInt(col++, pk.getReportId());
/* 177 */       stmt.setInt(col++, pk.getFinanceCubeId());
/* 178 */       stmt.setInt(col++, pk.getRowNo());
/*     */ 
/* 180 */       resultSet = stmt.executeQuery();
/*     */ 
/* 182 */       if (!resultSet.next()) {
/* 183 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 186 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 187 */       if (this.mDetails.isModified())
/* 188 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 192 */       throw handleSQLException(pk, "select CUBE_PENDING_TRAN.REPORT_ID,CUBE_PENDING_TRAN.FINANCE_CUBE_ID,CUBE_PENDING_TRAN.ROW_NO,CUBE_PENDING_TRAN.DIM0,CUBE_PENDING_TRAN.DIM1,CUBE_PENDING_TRAN.DIM2,CUBE_PENDING_TRAN.DIM3,CUBE_PENDING_TRAN.DIM4,CUBE_PENDING_TRAN.DIM5,CUBE_PENDING_TRAN.DIM6,CUBE_PENDING_TRAN.DIM7,CUBE_PENDING_TRAN.DIM8,CUBE_PENDING_TRAN.DIM9,CUBE_PENDING_TRAN.DATA_TYPE,CUBE_PENDING_TRAN.VALUE from CUBE_PENDING_TRAN where    REPORT_ID = ? AND FINANCE_CUBE_ID = ? AND ROW_NO = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 196 */       closeResultSet(resultSet);
/* 197 */       closeStatement(stmt);
/* 198 */       closeConnection();
/*     */ 
/* 200 */       if (timer != null)
/* 201 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 248 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 249 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 253 */       stmt = getConnection().prepareStatement("insert into CUBE_PENDING_TRAN ( REPORT_ID,FINANCE_CUBE_ID,ROW_NO,DIM0,DIM1,DIM2,DIM3,DIM4,DIM5,DIM6,DIM7,DIM8,DIM9,DATA_TYPE,VALUE) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 256 */       int col = 1;
/* 257 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 258 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 261 */       int resultCount = stmt.executeUpdate();
/* 262 */       if (resultCount != 1)
/*     */       {
/* 264 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 267 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 271 */       throw handleSQLException(this.mDetails.getPK(), "insert into CUBE_PENDING_TRAN ( REPORT_ID,FINANCE_CUBE_ID,ROW_NO,DIM0,DIM1,DIM2,DIM3,DIM4,DIM5,DIM6,DIM7,DIM8,DIM9,DATA_TYPE,VALUE) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 275 */       closeStatement(stmt);
/* 276 */       closeConnection();
/*     */ 
/* 278 */       if (timer != null)
/* 279 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 312 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 316 */     PreparedStatement stmt = null;
/*     */ 
/* 318 */     boolean mainChanged = this.mDetails.isModified();
/* 319 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 322 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 324 */         stmt = getConnection().prepareStatement("update CUBE_PENDING_TRAN set DIM0 = ?,DIM1 = ?,DIM2 = ?,DIM3 = ?,DIM4 = ?,DIM5 = ?,DIM6 = ?,DIM7 = ?,DIM8 = ?,DIM9 = ?,DATA_TYPE = ?,VALUE = ? where    REPORT_ID = ? AND FINANCE_CUBE_ID = ? AND ROW_NO = ? ");
/*     */ 
/* 327 */         int col = 1;
/* 328 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 329 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 332 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 335 */         if (resultCount != 1) {
/* 336 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 339 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 348 */       throw handleSQLException(getPK(), "update CUBE_PENDING_TRAN set DIM0 = ?,DIM1 = ?,DIM2 = ?,DIM3 = ?,DIM4 = ?,DIM5 = ?,DIM6 = ?,DIM7 = ?,DIM8 = ?,DIM9 = ?,DATA_TYPE = ?,VALUE = ? where    REPORT_ID = ? AND FINANCE_CUBE_ID = ? AND ROW_NO = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 352 */       closeStatement(stmt);
/* 353 */       closeConnection();
/*     */ 
/* 355 */       if ((timer != null) && (
/* 356 */         (mainChanged) || (dependantChanged)))
/* 357 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 378 */     if (items == null) {
/* 379 */       return false;
/*     */     }
/* 381 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 382 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 384 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 389 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 390 */       while (iter2.hasNext())
/*     */       {
/* 392 */         this.mDetails = ((CubePendingTranEVO)iter2.next());
/*     */ 
/* 395 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 397 */         somethingChanged = true;
/*     */ 
/* 400 */         if (deleteStmt == null) {
/* 401 */           deleteStmt = getConnection().prepareStatement("delete from CUBE_PENDING_TRAN where    REPORT_ID = ? AND FINANCE_CUBE_ID = ? AND ROW_NO = ? ");
/*     */         }
/*     */ 
/* 404 */         int col = 1;
/* 405 */         deleteStmt.setInt(col++, this.mDetails.getReportId());
/* 406 */         deleteStmt.setInt(col++, this.mDetails.getFinanceCubeId());
/* 407 */         deleteStmt.setInt(col++, this.mDetails.getRowNo());
/*     */ 
/* 409 */         if (this._log.isDebugEnabled()) {
/* 410 */           this._log.debug("update", "CubePendingTran deleting ReportId=" + this.mDetails.getReportId() + ",FinanceCubeId=" + this.mDetails.getFinanceCubeId() + ",RowNo=" + this.mDetails.getRowNo());
/*     */         }
/*     */ 
/* 417 */         deleteStmt.addBatch();
/*     */ 
/* 420 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 425 */       if (deleteStmt != null)
/*     */       {
/* 427 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 429 */         deleteStmt.executeBatch();
/*     */ 
/* 431 */         if (timer2 != null) {
/* 432 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 436 */       Iterator iter1 = items.values().iterator();
/* 437 */       while (iter1.hasNext())
/*     */       {
/* 439 */         this.mDetails = ((CubePendingTranEVO)iter1.next());
/*     */ 
/* 441 */         if (this.mDetails.insertPending())
/*     */         {
/* 443 */           somethingChanged = true;
/* 444 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 447 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 449 */         somethingChanged = true;
/* 450 */         doStore();
/*     */       }
/*     */ 
/* 461 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 465 */       throw handleSQLException("delete from CUBE_PENDING_TRAN where    REPORT_ID = ? AND FINANCE_CUBE_ID = ? AND ROW_NO = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 469 */       if (deleteStmt != null)
/*     */       {
/* 471 */         closeStatement(deleteStmt);
/* 472 */         closeConnection();
/*     */       }
/*     */ 
/* 475 */       this.mDetails = null;
/*     */ 
/* 477 */       if ((somethingChanged) && 
/* 478 */         (timer != null))
/* 479 */         timer.logDebug("update", "collection"); 
/* 479 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ReportPK entityPK, ReportEVO owningEVO, String dependants)
/*     */   {
/* 500 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 502 */     PreparedStatement stmt = null;
/* 503 */     ResultSet resultSet = null;
/*     */ 
/* 505 */     int itemCount = 0;
/*     */ 
/* 507 */     Collection theseItems = new ArrayList();
/* 508 */     owningEVO.setCUBE_PENDING_TRAN(theseItems);
/* 509 */     owningEVO.setCUBE_PENDING_TRANAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 513 */       stmt = getConnection().prepareStatement("select CUBE_PENDING_TRAN.REPORT_ID,CUBE_PENDING_TRAN.FINANCE_CUBE_ID,CUBE_PENDING_TRAN.ROW_NO,CUBE_PENDING_TRAN.DIM0,CUBE_PENDING_TRAN.DIM1,CUBE_PENDING_TRAN.DIM2,CUBE_PENDING_TRAN.DIM3,CUBE_PENDING_TRAN.DIM4,CUBE_PENDING_TRAN.DIM5,CUBE_PENDING_TRAN.DIM6,CUBE_PENDING_TRAN.DIM7,CUBE_PENDING_TRAN.DIM8,CUBE_PENDING_TRAN.DIM9,CUBE_PENDING_TRAN.DATA_TYPE,CUBE_PENDING_TRAN.VALUE from CUBE_PENDING_TRAN where 1=1 and CUBE_PENDING_TRAN.REPORT_ID = ? order by  CUBE_PENDING_TRAN.REPORT_ID ,CUBE_PENDING_TRAN.FINANCE_CUBE_ID ,CUBE_PENDING_TRAN.ROW_NO");
/*     */ 
/* 515 */       int col = 1;
/* 516 */       stmt.setInt(col++, entityPK.getReportId());
/*     */ 
/* 518 */       resultSet = stmt.executeQuery();
/*     */ 
/* 521 */       while (resultSet.next())
/*     */       {
/* 523 */         itemCount++;
/* 524 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 526 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 529 */       if (timer != null) {
/* 530 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 535 */       throw handleSQLException("select CUBE_PENDING_TRAN.REPORT_ID,CUBE_PENDING_TRAN.FINANCE_CUBE_ID,CUBE_PENDING_TRAN.ROW_NO,CUBE_PENDING_TRAN.DIM0,CUBE_PENDING_TRAN.DIM1,CUBE_PENDING_TRAN.DIM2,CUBE_PENDING_TRAN.DIM3,CUBE_PENDING_TRAN.DIM4,CUBE_PENDING_TRAN.DIM5,CUBE_PENDING_TRAN.DIM6,CUBE_PENDING_TRAN.DIM7,CUBE_PENDING_TRAN.DIM8,CUBE_PENDING_TRAN.DIM9,CUBE_PENDING_TRAN.DATA_TYPE,CUBE_PENDING_TRAN.VALUE from CUBE_PENDING_TRAN where 1=1 and CUBE_PENDING_TRAN.REPORT_ID = ? order by  CUBE_PENDING_TRAN.REPORT_ID ,CUBE_PENDING_TRAN.FINANCE_CUBE_ID ,CUBE_PENDING_TRAN.ROW_NO", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 539 */       closeResultSet(resultSet);
/* 540 */       closeStatement(stmt);
/* 541 */       closeConnection();
/*     */ 
/* 543 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectReportId, String dependants, Collection currentList)
/*     */   {
/* 568 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 569 */     PreparedStatement stmt = null;
/* 570 */     ResultSet resultSet = null;
/*     */ 
/* 572 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 576 */       stmt = getConnection().prepareStatement("select CUBE_PENDING_TRAN.REPORT_ID,CUBE_PENDING_TRAN.FINANCE_CUBE_ID,CUBE_PENDING_TRAN.ROW_NO,CUBE_PENDING_TRAN.DIM0,CUBE_PENDING_TRAN.DIM1,CUBE_PENDING_TRAN.DIM2,CUBE_PENDING_TRAN.DIM3,CUBE_PENDING_TRAN.DIM4,CUBE_PENDING_TRAN.DIM5,CUBE_PENDING_TRAN.DIM6,CUBE_PENDING_TRAN.DIM7,CUBE_PENDING_TRAN.DIM8,CUBE_PENDING_TRAN.DIM9,CUBE_PENDING_TRAN.DATA_TYPE,CUBE_PENDING_TRAN.VALUE from CUBE_PENDING_TRAN where    REPORT_ID = ? ");
/*     */ 
/* 578 */       int col = 1;
/* 579 */       stmt.setInt(col++, selectReportId);
/*     */ 
/* 581 */       resultSet = stmt.executeQuery();
/*     */ 
/* 583 */       while (resultSet.next())
/*     */       {
/* 585 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 588 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 591 */       if (currentList != null)
/*     */       {
/* 594 */         ListIterator iter = items.listIterator();
/* 595 */         CubePendingTranEVO currentEVO = null;
/* 596 */         CubePendingTranEVO newEVO = null;
/* 597 */         while (iter.hasNext())
/*     */         {
/* 599 */           newEVO = (CubePendingTranEVO)iter.next();
/* 600 */           Iterator iter2 = currentList.iterator();
/* 601 */           while (iter2.hasNext())
/*     */           {
/* 603 */             currentEVO = (CubePendingTranEVO)iter2.next();
/* 604 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 606 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 612 */         Iterator iter2 = currentList.iterator();
/* 613 */         while (iter2.hasNext())
/*     */         {
/* 615 */           currentEVO = (CubePendingTranEVO)iter2.next();
/* 616 */           if (currentEVO.insertPending()) {
/* 617 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 621 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 625 */       throw handleSQLException("select CUBE_PENDING_TRAN.REPORT_ID,CUBE_PENDING_TRAN.FINANCE_CUBE_ID,CUBE_PENDING_TRAN.ROW_NO,CUBE_PENDING_TRAN.DIM0,CUBE_PENDING_TRAN.DIM1,CUBE_PENDING_TRAN.DIM2,CUBE_PENDING_TRAN.DIM3,CUBE_PENDING_TRAN.DIM4,CUBE_PENDING_TRAN.DIM5,CUBE_PENDING_TRAN.DIM6,CUBE_PENDING_TRAN.DIM7,CUBE_PENDING_TRAN.DIM8,CUBE_PENDING_TRAN.DIM9,CUBE_PENDING_TRAN.DATA_TYPE,CUBE_PENDING_TRAN.VALUE from CUBE_PENDING_TRAN where    REPORT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 629 */       closeResultSet(resultSet);
/* 630 */       closeStatement(stmt);
/* 631 */       closeConnection();
/*     */ 
/* 633 */       if (timer != null) {
/* 634 */         timer.logDebug("getAll", " ReportId=" + selectReportId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 639 */     return items;
/*     */   }
/*     */ 
/*     */   public CubePendingTranEVO getDetails(ReportCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 653 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 656 */     if (this.mDetails == null) {
/* 657 */       doLoad(((CubePendingTranCK)paramCK).getCubePendingTranPK());
/*     */     }
/* 659 */     else if (!this.mDetails.getPK().equals(((CubePendingTranCK)paramCK).getCubePendingTranPK())) {
/* 660 */       doLoad(((CubePendingTranCK)paramCK).getCubePendingTranPK());
/*     */     }
/*     */ 
/* 663 */     CubePendingTranEVO details = new CubePendingTranEVO();
/* 664 */     details = this.mDetails.deepClone();
/*     */ 
/* 666 */     if (timer != null) {
/* 667 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 669 */     return details;
/*     */   }
/*     */ 
/*     */   public CubePendingTranEVO getDetails(ReportCK paramCK, CubePendingTranEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 675 */     CubePendingTranEVO savedEVO = this.mDetails;
/* 676 */     this.mDetails = paramEVO;
/* 677 */     CubePendingTranEVO newEVO = getDetails(paramCK, dependants);
/* 678 */     this.mDetails = savedEVO;
/* 679 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public CubePendingTranEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 685 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 689 */     CubePendingTranEVO details = this.mDetails.deepClone();
/*     */ 
/* 691 */     if (timer != null) {
/* 692 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 694 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 699 */     return "CubePendingTran";
/*     */   }
/*     */ 
/*     */   public CubePendingTranRefImpl getRef(CubePendingTranPK paramCubePendingTranPK)
/*     */   {
/* 704 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 705 */     PreparedStatement stmt = null;
/* 706 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 709 */       stmt = getConnection().prepareStatement("select 0,REPORT.REPORT_ID from CUBE_PENDING_TRAN,REPORT where 1=1 and CUBE_PENDING_TRAN.REPORT_ID = ? and CUBE_PENDING_TRAN.FINANCE_CUBE_ID = ? and CUBE_PENDING_TRAN.ROW_NO = ? and CUBE_PENDING_TRAN.REPORT_ID = REPORT.REPORT_ID");
/* 710 */       int col = 1;
/* 711 */       stmt.setInt(col++, paramCubePendingTranPK.getReportId());
/* 712 */       stmt.setInt(col++, paramCubePendingTranPK.getFinanceCubeId());
/* 713 */       stmt.setInt(col++, paramCubePendingTranPK.getRowNo());
/*     */ 
/* 715 */       resultSet = stmt.executeQuery();
/*     */ 
/* 717 */       if (!resultSet.next()) {
/* 718 */         throw new RuntimeException(getEntityName() + " getRef " + paramCubePendingTranPK + " not found");
/*     */       }
/* 720 */       col = 2;
/* 721 */       ReportPK newReportPK = new ReportPK(resultSet.getInt(col++));
/*     */ 
/* 725 */       String textCubePendingTran = "";
/* 726 */       CubePendingTranCK ckCubePendingTran = new CubePendingTranCK(newReportPK, paramCubePendingTranPK);
/*     */ 
/* 731 */       CubePendingTranRefImpl localCubePendingTranRefImpl = new CubePendingTranRefImpl(ckCubePendingTran, textCubePendingTran);
/*     */       return localCubePendingTranRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 736 */       throw handleSQLException(paramCubePendingTranPK, "select 0,REPORT.REPORT_ID from CUBE_PENDING_TRAN,REPORT where 1=1 and CUBE_PENDING_TRAN.REPORT_ID = ? and CUBE_PENDING_TRAN.FINANCE_CUBE_ID = ? and CUBE_PENDING_TRAN.ROW_NO = ? and CUBE_PENDING_TRAN.REPORT_ID = REPORT.REPORT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 740 */       closeResultSet(resultSet);
/* 741 */       closeStatement(stmt);
/* 742 */       closeConnection();
/*     */ 
/* 744 */       if (timer != null)
/* 745 */         timer.logDebug("getRef", paramCubePendingTranPK); 
/* 745 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.tran.CubePendingTranDAO
 * JD-Core Version:    0.6.0
 */