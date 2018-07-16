/*     */ package com.cedar.cp.ejb.impl.impexp;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.impexp.ImpExpHdrCK;
/*     */ import com.cedar.cp.dto.impexp.ImpExpHdrPK;
/*     */ import com.cedar.cp.dto.impexp.ImpExpRowCK;
/*     */ import com.cedar.cp.dto.impexp.ImpExpRowPK;
/*     */ import com.cedar.cp.dto.impexp.ImpExpRowRefImpl;
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
/*     */ public class ImpExpRowDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select IMP_EXP_ROW.BATCH_ID,IMP_EXP_ROW.ROW_NO,IMP_EXP_ROW.DIM0,IMP_EXP_ROW.DIM1,IMP_EXP_ROW.DIM2,IMP_EXP_ROW.DIM3,IMP_EXP_ROW.DIM4,IMP_EXP_ROW.DIM5,IMP_EXP_ROW.DIM6,IMP_EXP_ROW.DIM7,IMP_EXP_ROW.DIM8,IMP_EXP_ROW.DIM9,IMP_EXP_ROW.DATA_TYPE,IMP_EXP_ROW.NUMBER_VALUE,IMP_EXP_ROW.STRING_VALUE,IMP_EXP_ROW.DATE_VALUE";
/*     */   protected static final String SQL_LOAD = " from IMP_EXP_ROW where    BATCH_ID = ? AND ROW_NO = ? ";
/*     */   protected static final String SQL_CREATE = "insert into IMP_EXP_ROW ( BATCH_ID,ROW_NO,DIM0,DIM1,DIM2,DIM3,DIM4,DIM5,DIM6,DIM7,DIM8,DIM9,DATA_TYPE,NUMBER_VALUE,STRING_VALUE,DATE_VALUE) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update IMP_EXP_ROW set DIM0 = ?,DIM1 = ?,DIM2 = ?,DIM3 = ?,DIM4 = ?,DIM5 = ?,DIM6 = ?,DIM7 = ?,DIM8 = ?,DIM9 = ?,DATA_TYPE = ?,NUMBER_VALUE = ?,STRING_VALUE = ?,DATE_VALUE = ? where    BATCH_ID = ? AND ROW_NO = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from IMP_EXP_ROW where    BATCH_ID = ? AND ROW_NO = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from IMP_EXP_ROW where 1=1 and IMP_EXP_ROW.BATCH_ID = ? order by  IMP_EXP_ROW.BATCH_ID ,IMP_EXP_ROW.ROW_NO";
/*     */   protected static final String SQL_GET_ALL = " from IMP_EXP_ROW where    BATCH_ID = ? ";
/*     */   protected ImpExpRowEVO mDetails;
/*     */ 
/*     */   public ImpExpRowDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ImpExpRowDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ImpExpRowDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ImpExpRowPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ImpExpRowEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private ImpExpRowEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  99 */     int col = 1;
/* 100 */     ImpExpRowEVO evo = new ImpExpRowEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getLong(col++), resultSet_.getString(col++), resultSet_.getTimestamp(col++));
/*     */ 
/* 119 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ImpExpRowEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 124 */     int col = startCol_;
/* 125 */     stmt_.setInt(col++, evo_.getBatchId());
/* 126 */     stmt_.setInt(col++, evo_.getRowNo());
/* 127 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ImpExpRowEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 132 */     int col = startCol_;
/* 133 */     stmt_.setInt(col++, evo_.getDim0());
/* 134 */     stmt_.setInt(col++, evo_.getDim1());
/* 135 */     stmt_.setInt(col++, evo_.getDim2());
/* 136 */     stmt_.setInt(col++, evo_.getDim3());
/* 137 */     stmt_.setInt(col++, evo_.getDim4());
/* 138 */     stmt_.setInt(col++, evo_.getDim5());
/* 139 */     stmt_.setInt(col++, evo_.getDim6());
/* 140 */     stmt_.setInt(col++, evo_.getDim7());
/* 141 */     stmt_.setInt(col++, evo_.getDim8());
/* 142 */     stmt_.setInt(col++, evo_.getDim9());
/* 143 */     stmt_.setString(col++, evo_.getDataType());
/* 144 */     stmt_.setLong(col++, evo_.getNumberValue());
/* 145 */     stmt_.setString(col++, evo_.getStringValue());
/* 146 */     stmt_.setTimestamp(col++, evo_.getDateValue());
/* 147 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ImpExpRowPK pk)
/*     */     throws ValidationException
/*     */   {
/* 164 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 166 */     PreparedStatement stmt = null;
/* 167 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 171 */       stmt = getConnection().prepareStatement("select IMP_EXP_ROW.BATCH_ID,IMP_EXP_ROW.ROW_NO,IMP_EXP_ROW.DIM0,IMP_EXP_ROW.DIM1,IMP_EXP_ROW.DIM2,IMP_EXP_ROW.DIM3,IMP_EXP_ROW.DIM4,IMP_EXP_ROW.DIM5,IMP_EXP_ROW.DIM6,IMP_EXP_ROW.DIM7,IMP_EXP_ROW.DIM8,IMP_EXP_ROW.DIM9,IMP_EXP_ROW.DATA_TYPE,IMP_EXP_ROW.NUMBER_VALUE,IMP_EXP_ROW.STRING_VALUE,IMP_EXP_ROW.DATE_VALUE from IMP_EXP_ROW where    BATCH_ID = ? AND ROW_NO = ? ");
/*     */ 
/* 174 */       int col = 1;
/* 175 */       stmt.setInt(col++, pk.getBatchId());
/* 176 */       stmt.setInt(col++, pk.getRowNo());
/*     */ 
/* 178 */       resultSet = stmt.executeQuery();
/*     */ 
/* 180 */       if (!resultSet.next()) {
/* 181 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 184 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 185 */       if (this.mDetails.isModified())
/* 186 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 190 */       throw handleSQLException(pk, "select IMP_EXP_ROW.BATCH_ID,IMP_EXP_ROW.ROW_NO,IMP_EXP_ROW.DIM0,IMP_EXP_ROW.DIM1,IMP_EXP_ROW.DIM2,IMP_EXP_ROW.DIM3,IMP_EXP_ROW.DIM4,IMP_EXP_ROW.DIM5,IMP_EXP_ROW.DIM6,IMP_EXP_ROW.DIM7,IMP_EXP_ROW.DIM8,IMP_EXP_ROW.DIM9,IMP_EXP_ROW.DATA_TYPE,IMP_EXP_ROW.NUMBER_VALUE,IMP_EXP_ROW.STRING_VALUE,IMP_EXP_ROW.DATE_VALUE from IMP_EXP_ROW where    BATCH_ID = ? AND ROW_NO = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 194 */       closeResultSet(resultSet);
/* 195 */       closeStatement(stmt);
/* 196 */       closeConnection();
/*     */ 
/* 198 */       if (timer != null)
/* 199 */         timer.logDebug("doLoad", pk);
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
/* 253 */       stmt = getConnection().prepareStatement("insert into IMP_EXP_ROW ( BATCH_ID,ROW_NO,DIM0,DIM1,DIM2,DIM3,DIM4,DIM5,DIM6,DIM7,DIM8,DIM9,DATA_TYPE,NUMBER_VALUE,STRING_VALUE,DATE_VALUE) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
/* 271 */       throw handleSQLException(this.mDetails.getPK(), "insert into IMP_EXP_ROW ( BATCH_ID,ROW_NO,DIM0,DIM1,DIM2,DIM3,DIM4,DIM5,DIM6,DIM7,DIM8,DIM9,DATA_TYPE,NUMBER_VALUE,STRING_VALUE,DATE_VALUE) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
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
/* 313 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 317 */     PreparedStatement stmt = null;
/*     */ 
/* 319 */     boolean mainChanged = this.mDetails.isModified();
/* 320 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 323 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 325 */         stmt = getConnection().prepareStatement("update IMP_EXP_ROW set DIM0 = ?,DIM1 = ?,DIM2 = ?,DIM3 = ?,DIM4 = ?,DIM5 = ?,DIM6 = ?,DIM7 = ?,DIM8 = ?,DIM9 = ?,DATA_TYPE = ?,NUMBER_VALUE = ?,STRING_VALUE = ?,DATE_VALUE = ? where    BATCH_ID = ? AND ROW_NO = ? ");
/*     */ 
/* 328 */         int col = 1;
/* 329 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 330 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 333 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 336 */         if (resultCount != 1) {
/* 337 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 340 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 349 */       throw handleSQLException(getPK(), "update IMP_EXP_ROW set DIM0 = ?,DIM1 = ?,DIM2 = ?,DIM3 = ?,DIM4 = ?,DIM5 = ?,DIM6 = ?,DIM7 = ?,DIM8 = ?,DIM9 = ?,DATA_TYPE = ?,NUMBER_VALUE = ?,STRING_VALUE = ?,DATE_VALUE = ? where    BATCH_ID = ? AND ROW_NO = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 353 */       closeStatement(stmt);
/* 354 */       closeConnection();
/*     */ 
/* 356 */       if ((timer != null) && (
/* 357 */         (mainChanged) || (dependantChanged)))
/* 358 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
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
/* 392 */         this.mDetails = ((ImpExpRowEVO)iter2.next());
/*     */ 
/* 395 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 397 */         somethingChanged = true;
/*     */ 
/* 400 */         if (deleteStmt == null) {
/* 401 */           deleteStmt = getConnection().prepareStatement("delete from IMP_EXP_ROW where    BATCH_ID = ? AND ROW_NO = ? ");
/*     */         }
/*     */ 
/* 404 */         int col = 1;
/* 405 */         deleteStmt.setInt(col++, this.mDetails.getBatchId());
/* 406 */         deleteStmt.setInt(col++, this.mDetails.getRowNo());
/*     */ 
/* 408 */         if (this._log.isDebugEnabled()) {
/* 409 */           this._log.debug("update", "ImpExpRow deleting BatchId=" + this.mDetails.getBatchId() + ",RowNo=" + this.mDetails.getRowNo());
/*     */         }
/*     */ 
/* 415 */         deleteStmt.addBatch();
/*     */ 
/* 418 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 423 */       if (deleteStmt != null)
/*     */       {
/* 425 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 427 */         deleteStmt.executeBatch();
/*     */ 
/* 429 */         if (timer2 != null) {
/* 430 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 434 */       Iterator iter1 = items.values().iterator();
/* 435 */       while (iter1.hasNext())
/*     */       {
/* 437 */         this.mDetails = ((ImpExpRowEVO)iter1.next());
/*     */ 
/* 439 */         if (this.mDetails.insertPending())
/*     */         {
/* 441 */           somethingChanged = true;
/* 442 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 445 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 447 */         somethingChanged = true;
/* 448 */         doStore();
/*     */       }
/*     */ 
/* 459 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 463 */       throw handleSQLException("delete from IMP_EXP_ROW where    BATCH_ID = ? AND ROW_NO = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 467 */       if (deleteStmt != null)
/*     */       {
/* 469 */         closeStatement(deleteStmt);
/* 470 */         closeConnection();
/*     */       }
/*     */ 
/* 473 */       this.mDetails = null;
/*     */ 
/* 475 */       if ((somethingChanged) && 
/* 476 */         (timer != null))
/* 477 */         timer.logDebug("update", "collection"); 
/* 477 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ImpExpHdrPK entityPK, ImpExpHdrEVO owningEVO, String dependants)
/*     */   {
/* 497 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 499 */     PreparedStatement stmt = null;
/* 500 */     ResultSet resultSet = null;
/*     */ 
/* 502 */     int itemCount = 0;
/*     */ 
/* 504 */     Collection theseItems = new ArrayList();
/* 505 */     owningEVO.setIMP_EXP_ROW(theseItems);
/* 506 */     owningEVO.setIMP_EXP_ROWAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 510 */       stmt = getConnection().prepareStatement("select IMP_EXP_ROW.BATCH_ID,IMP_EXP_ROW.ROW_NO,IMP_EXP_ROW.DIM0,IMP_EXP_ROW.DIM1,IMP_EXP_ROW.DIM2,IMP_EXP_ROW.DIM3,IMP_EXP_ROW.DIM4,IMP_EXP_ROW.DIM5,IMP_EXP_ROW.DIM6,IMP_EXP_ROW.DIM7,IMP_EXP_ROW.DIM8,IMP_EXP_ROW.DIM9,IMP_EXP_ROW.DATA_TYPE,IMP_EXP_ROW.NUMBER_VALUE,IMP_EXP_ROW.STRING_VALUE,IMP_EXP_ROW.DATE_VALUE from IMP_EXP_ROW where 1=1 and IMP_EXP_ROW.BATCH_ID = ? order by  IMP_EXP_ROW.BATCH_ID ,IMP_EXP_ROW.ROW_NO");
/*     */ 
/* 512 */       int col = 1;
/* 513 */       stmt.setInt(col++, entityPK.getBatchId());
/*     */ 
/* 515 */       resultSet = stmt.executeQuery();
/*     */ 
/* 518 */       while (resultSet.next())
/*     */       {
/* 520 */         itemCount++;
/* 521 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 523 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 526 */       if (timer != null) {
/* 527 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 532 */       throw handleSQLException("select IMP_EXP_ROW.BATCH_ID,IMP_EXP_ROW.ROW_NO,IMP_EXP_ROW.DIM0,IMP_EXP_ROW.DIM1,IMP_EXP_ROW.DIM2,IMP_EXP_ROW.DIM3,IMP_EXP_ROW.DIM4,IMP_EXP_ROW.DIM5,IMP_EXP_ROW.DIM6,IMP_EXP_ROW.DIM7,IMP_EXP_ROW.DIM8,IMP_EXP_ROW.DIM9,IMP_EXP_ROW.DATA_TYPE,IMP_EXP_ROW.NUMBER_VALUE,IMP_EXP_ROW.STRING_VALUE,IMP_EXP_ROW.DATE_VALUE from IMP_EXP_ROW where 1=1 and IMP_EXP_ROW.BATCH_ID = ? order by  IMP_EXP_ROW.BATCH_ID ,IMP_EXP_ROW.ROW_NO", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 536 */       closeResultSet(resultSet);
/* 537 */       closeStatement(stmt);
/* 538 */       closeConnection();
/*     */ 
/* 540 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectBatchId, String dependants, Collection currentList)
/*     */   {
/* 565 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 566 */     PreparedStatement stmt = null;
/* 567 */     ResultSet resultSet = null;
/*     */ 
/* 569 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 573 */       stmt = getConnection().prepareStatement("select IMP_EXP_ROW.BATCH_ID,IMP_EXP_ROW.ROW_NO,IMP_EXP_ROW.DIM0,IMP_EXP_ROW.DIM1,IMP_EXP_ROW.DIM2,IMP_EXP_ROW.DIM3,IMP_EXP_ROW.DIM4,IMP_EXP_ROW.DIM5,IMP_EXP_ROW.DIM6,IMP_EXP_ROW.DIM7,IMP_EXP_ROW.DIM8,IMP_EXP_ROW.DIM9,IMP_EXP_ROW.DATA_TYPE,IMP_EXP_ROW.NUMBER_VALUE,IMP_EXP_ROW.STRING_VALUE,IMP_EXP_ROW.DATE_VALUE from IMP_EXP_ROW where    BATCH_ID = ? ");
/*     */ 
/* 575 */       int col = 1;
/* 576 */       stmt.setInt(col++, selectBatchId);
/*     */ 
/* 578 */       resultSet = stmt.executeQuery();
/*     */ 
/* 580 */       while (resultSet.next())
/*     */       {
/* 582 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 585 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 588 */       if (currentList != null)
/*     */       {
/* 591 */         ListIterator iter = items.listIterator();
/* 592 */         ImpExpRowEVO currentEVO = null;
/* 593 */         ImpExpRowEVO newEVO = null;
/* 594 */         while (iter.hasNext())
/*     */         {
/* 596 */           newEVO = (ImpExpRowEVO)iter.next();
/* 597 */           Iterator iter2 = currentList.iterator();
/* 598 */           while (iter2.hasNext())
/*     */           {
/* 600 */             currentEVO = (ImpExpRowEVO)iter2.next();
/* 601 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 603 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 609 */         Iterator iter2 = currentList.iterator();
/* 610 */         while (iter2.hasNext())
/*     */         {
/* 612 */           currentEVO = (ImpExpRowEVO)iter2.next();
/* 613 */           if (currentEVO.insertPending()) {
/* 614 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 618 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 622 */       throw handleSQLException("select IMP_EXP_ROW.BATCH_ID,IMP_EXP_ROW.ROW_NO,IMP_EXP_ROW.DIM0,IMP_EXP_ROW.DIM1,IMP_EXP_ROW.DIM2,IMP_EXP_ROW.DIM3,IMP_EXP_ROW.DIM4,IMP_EXP_ROW.DIM5,IMP_EXP_ROW.DIM6,IMP_EXP_ROW.DIM7,IMP_EXP_ROW.DIM8,IMP_EXP_ROW.DIM9,IMP_EXP_ROW.DATA_TYPE,IMP_EXP_ROW.NUMBER_VALUE,IMP_EXP_ROW.STRING_VALUE,IMP_EXP_ROW.DATE_VALUE from IMP_EXP_ROW where    BATCH_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 626 */       closeResultSet(resultSet);
/* 627 */       closeStatement(stmt);
/* 628 */       closeConnection();
/*     */ 
/* 630 */       if (timer != null) {
/* 631 */         timer.logDebug("getAll", " BatchId=" + selectBatchId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 636 */     return items;
/*     */   }
/*     */ 
/*     */   public ImpExpRowEVO getDetails(ImpExpHdrCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 650 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 653 */     if (this.mDetails == null) {
/* 654 */       doLoad(((ImpExpRowCK)paramCK).getImpExpRowPK());
/*     */     }
/* 656 */     else if (!this.mDetails.getPK().equals(((ImpExpRowCK)paramCK).getImpExpRowPK())) {
/* 657 */       doLoad(((ImpExpRowCK)paramCK).getImpExpRowPK());
/*     */     }
/*     */ 
/* 660 */     ImpExpRowEVO details = new ImpExpRowEVO();
/* 661 */     details = this.mDetails.deepClone();
/*     */ 
/* 663 */     if (timer != null) {
/* 664 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 666 */     return details;
/*     */   }
/*     */ 
/*     */   public ImpExpRowEVO getDetails(ImpExpHdrCK paramCK, ImpExpRowEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 672 */     ImpExpRowEVO savedEVO = this.mDetails;
/* 673 */     this.mDetails = paramEVO;
/* 674 */     ImpExpRowEVO newEVO = getDetails(paramCK, dependants);
/* 675 */     this.mDetails = savedEVO;
/* 676 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public ImpExpRowEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 682 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 686 */     ImpExpRowEVO details = this.mDetails.deepClone();
/*     */ 
/* 688 */     if (timer != null) {
/* 689 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 691 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 696 */     return "ImpExpRow";
/*     */   }
/*     */ 
/*     */   public ImpExpRowRefImpl getRef(ImpExpRowPK paramImpExpRowPK)
/*     */   {
/* 701 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 702 */     PreparedStatement stmt = null;
/* 703 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 706 */       stmt = getConnection().prepareStatement("select 0,IMP_EXP_HDR.BATCH_ID from IMP_EXP_ROW,IMP_EXP_HDR where 1=1 and IMP_EXP_ROW.BATCH_ID = ? and IMP_EXP_ROW.ROW_NO = ? and IMP_EXP_ROW.BATCH_ID = IMP_EXP_HDR.BATCH_ID");
/* 707 */       int col = 1;
/* 708 */       stmt.setInt(col++, paramImpExpRowPK.getBatchId());
/* 709 */       stmt.setInt(col++, paramImpExpRowPK.getRowNo());
/*     */ 
/* 711 */       resultSet = stmt.executeQuery();
/*     */ 
/* 713 */       if (!resultSet.next()) {
/* 714 */         throw new RuntimeException(getEntityName() + " getRef " + paramImpExpRowPK + " not found");
/*     */       }
/* 716 */       col = 2;
/* 717 */       ImpExpHdrPK newImpExpHdrPK = new ImpExpHdrPK(resultSet.getInt(col++));
/*     */ 
/* 721 */       String textImpExpRow = "";
/* 722 */       ImpExpRowCK ckImpExpRow = new ImpExpRowCK(newImpExpHdrPK, paramImpExpRowPK);
/*     */ 
/* 727 */       ImpExpRowRefImpl localImpExpRowRefImpl = new ImpExpRowRefImpl(ckImpExpRow, textImpExpRow);
/*     */       return localImpExpRowRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 732 */       throw handleSQLException(paramImpExpRowPK, "select 0,IMP_EXP_HDR.BATCH_ID from IMP_EXP_ROW,IMP_EXP_HDR where 1=1 and IMP_EXP_ROW.BATCH_ID = ? and IMP_EXP_ROW.ROW_NO = ? and IMP_EXP_ROW.BATCH_ID = IMP_EXP_HDR.BATCH_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 736 */       closeResultSet(resultSet);
/* 737 */       closeStatement(stmt);
/* 738 */       closeConnection();
/*     */ 
/* 740 */       if (timer != null)
/* 741 */         timer.logDebug("getRef", paramImpExpRowPK); 
/* 741 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.impexp.ImpExpRowDAO
 * JD-Core Version:    0.6.0
 */