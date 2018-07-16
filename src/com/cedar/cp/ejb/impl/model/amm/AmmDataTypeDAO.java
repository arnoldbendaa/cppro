/*     */ package com.cedar.cp.ejb.impl.model.amm;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.amm.AmmDataTypeCK;
/*     */ import com.cedar.cp.dto.model.amm.AmmDataTypePK;
/*     */ import com.cedar.cp.dto.model.amm.AmmDataTypeRefImpl;
/*     */ import com.cedar.cp.dto.model.amm.AmmFinanceCubePK;
/*     */ import com.cedar.cp.dto.model.amm.AmmModelCK;
/*     */ import com.cedar.cp.dto.model.amm.AmmModelPK;
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
/*     */ public class AmmDataTypeDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select AMM_DATA_TYPE.AMM_DATA_TYPE_ID,AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID,AMM_DATA_TYPE.DATA_TYPE_ID,AMM_DATA_TYPE.SRC_DATA_TYPE_ID,AMM_DATA_TYPE.SRC_START_YEAR_OFFSET,AMM_DATA_TYPE.SRC_END_YEAR_OFFSET,AMM_DATA_TYPE.UPDATED_BY_USER_ID,AMM_DATA_TYPE.UPDATED_TIME,AMM_DATA_TYPE.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from AMM_DATA_TYPE where    AMM_DATA_TYPE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into AMM_DATA_TYPE ( AMM_DATA_TYPE_ID,AMM_FINANCE_CUBE_ID,DATA_TYPE_ID,SRC_DATA_TYPE_ID,SRC_START_YEAR_OFFSET,SRC_END_YEAR_OFFSET,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update AMM_DATA_TYPE set AMM_FINANCE_CUBE_ID = ?,DATA_TYPE_ID = ?,SRC_DATA_TYPE_ID = ?,SRC_START_YEAR_OFFSET = ?,SRC_END_YEAR_OFFSET = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_DATA_TYPE_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from AMM_DATA_TYPE where    AMM_DATA_TYPE_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from AMM_DATA_TYPE,AMM_FINANCE_CUBE where 1=1 and AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID = AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID and AMM_FINANCE_CUBE.AMM_MODEL_ID = ? order by  AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID ,AMM_DATA_TYPE.AMM_DATA_TYPE_ID";
/*     */   protected static final String SQL_GET_ALL = " from AMM_DATA_TYPE where    AMM_FINANCE_CUBE_ID = ? ";
/* 750 */   private static String QUERY_IS_MAPPED_DATA_TYPE = "select count(*) as nmfc from amm_data_type mfc where mfc.data_type_id = ? or    mfc.src_data_type_id = ?";
/*     */   protected AmmDataTypeEVO mDetails;
/*     */ 
/*     */   public AmmDataTypeDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public AmmDataTypeDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AmmDataTypeDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected AmmDataTypePK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(AmmDataTypeEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private AmmDataTypeEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  92 */     int col = 1;
/*  93 */     AmmDataTypeEVO evo = new AmmDataTypeEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/* 102 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 103 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 104 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 105 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(AmmDataTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 110 */     int col = startCol_;
/* 111 */     stmt_.setInt(col++, evo_.getAmmDataTypeId());
/* 112 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(AmmDataTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 117 */     int col = startCol_;
/* 118 */     stmt_.setInt(col++, evo_.getAmmFinanceCubeId());
/* 119 */     stmt_.setInt(col++, evo_.getDataTypeId());
/* 120 */     stmt_.setInt(col++, evo_.getSrcDataTypeId());
/* 121 */     stmt_.setInt(col++, evo_.getSrcStartYearOffset());
/* 122 */     stmt_.setInt(col++, evo_.getSrcEndYearOffset());
/* 123 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 124 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 125 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 126 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(AmmDataTypePK pk)
/*     */     throws ValidationException
/*     */   {
/* 142 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 144 */     PreparedStatement stmt = null;
/* 145 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 149 */       stmt = getConnection().prepareStatement("select AMM_DATA_TYPE.AMM_DATA_TYPE_ID,AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID,AMM_DATA_TYPE.DATA_TYPE_ID,AMM_DATA_TYPE.SRC_DATA_TYPE_ID,AMM_DATA_TYPE.SRC_START_YEAR_OFFSET,AMM_DATA_TYPE.SRC_END_YEAR_OFFSET,AMM_DATA_TYPE.UPDATED_BY_USER_ID,AMM_DATA_TYPE.UPDATED_TIME,AMM_DATA_TYPE.CREATED_TIME from AMM_DATA_TYPE where    AMM_DATA_TYPE_ID = ? ");
/*     */ 
/* 152 */       int col = 1;
/* 153 */       stmt.setInt(col++, pk.getAmmDataTypeId());
/*     */ 
/* 155 */       resultSet = stmt.executeQuery();
/*     */ 
/* 157 */       if (!resultSet.next()) {
/* 158 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 161 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 162 */       if (this.mDetails.isModified())
/* 163 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 167 */       throw handleSQLException(pk, "select AMM_DATA_TYPE.AMM_DATA_TYPE_ID,AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID,AMM_DATA_TYPE.DATA_TYPE_ID,AMM_DATA_TYPE.SRC_DATA_TYPE_ID,AMM_DATA_TYPE.SRC_START_YEAR_OFFSET,AMM_DATA_TYPE.SRC_END_YEAR_OFFSET,AMM_DATA_TYPE.UPDATED_BY_USER_ID,AMM_DATA_TYPE.UPDATED_TIME,AMM_DATA_TYPE.CREATED_TIME from AMM_DATA_TYPE where    AMM_DATA_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 171 */       closeResultSet(resultSet);
/* 172 */       closeStatement(stmt);
/* 173 */       closeConnection();
/*     */ 
/* 175 */       if (timer != null)
/* 176 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 211 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 212 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 217 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 218 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 219 */       stmt = getConnection().prepareStatement("insert into AMM_DATA_TYPE ( AMM_DATA_TYPE_ID,AMM_FINANCE_CUBE_ID,DATA_TYPE_ID,SRC_DATA_TYPE_ID,SRC_START_YEAR_OFFSET,SRC_END_YEAR_OFFSET,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 222 */       int col = 1;
/* 223 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 224 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 227 */       int resultCount = stmt.executeUpdate();
/* 228 */       if (resultCount != 1)
/*     */       {
/* 230 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 233 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 237 */       throw handleSQLException(this.mDetails.getPK(), "insert into AMM_DATA_TYPE ( AMM_DATA_TYPE_ID,AMM_FINANCE_CUBE_ID,DATA_TYPE_ID,SRC_DATA_TYPE_ID,SRC_START_YEAR_OFFSET,SRC_END_YEAR_OFFSET,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 241 */       closeStatement(stmt);
/* 242 */       closeConnection();
/*     */ 
/* 244 */       if (timer != null)
/* 245 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 272 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 276 */     PreparedStatement stmt = null;
/*     */ 
/* 278 */     boolean mainChanged = this.mDetails.isModified();
/* 279 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 282 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 285 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 286 */         stmt = getConnection().prepareStatement("update AMM_DATA_TYPE set AMM_FINANCE_CUBE_ID = ?,DATA_TYPE_ID = ?,SRC_DATA_TYPE_ID = ?,SRC_START_YEAR_OFFSET = ?,SRC_END_YEAR_OFFSET = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_DATA_TYPE_ID = ? ");
/*     */ 
/* 289 */         int col = 1;
/* 290 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 291 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 294 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 297 */         if (resultCount != 1) {
/* 298 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 301 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 310 */       throw handleSQLException(getPK(), "update AMM_DATA_TYPE set AMM_FINANCE_CUBE_ID = ?,DATA_TYPE_ID = ?,SRC_DATA_TYPE_ID = ?,SRC_START_YEAR_OFFSET = ?,SRC_END_YEAR_OFFSET = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_DATA_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 314 */       closeStatement(stmt);
/* 315 */       closeConnection();
/*     */ 
/* 317 */       if ((timer != null) && (
/* 318 */         (mainChanged) || (dependantChanged)))
/* 319 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 338 */     if (items == null) {
/* 339 */       return false;
/*     */     }
/* 341 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 342 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 344 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 349 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 350 */       while (iter2.hasNext())
/*     */       {
/* 352 */         this.mDetails = ((AmmDataTypeEVO)iter2.next());
/*     */ 
/* 355 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 357 */         somethingChanged = true;
/*     */ 
/* 360 */         if (deleteStmt == null) {
/* 361 */           deleteStmt = getConnection().prepareStatement("delete from AMM_DATA_TYPE where    AMM_DATA_TYPE_ID = ? ");
/*     */         }
/*     */ 
/* 364 */         int col = 1;
/* 365 */         deleteStmt.setInt(col++, this.mDetails.getAmmDataTypeId());
/*     */ 
/* 367 */         if (this._log.isDebugEnabled()) {
/* 368 */           this._log.debug("update", "AmmDataType deleting AmmDataTypeId=" + this.mDetails.getAmmDataTypeId());
/*     */         }
/*     */ 
/* 373 */         deleteStmt.addBatch();
/*     */ 
/* 376 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 381 */       if (deleteStmt != null)
/*     */       {
/* 383 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 385 */         deleteStmt.executeBatch();
/*     */ 
/* 387 */         if (timer2 != null) {
/* 388 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 392 */       Iterator iter1 = items.values().iterator();
/* 393 */       while (iter1.hasNext())
/*     */       {
/* 395 */         this.mDetails = ((AmmDataTypeEVO)iter1.next());
/*     */ 
/* 397 */         if (this.mDetails.insertPending())
/*     */         {
/* 399 */           somethingChanged = true;
/* 400 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 403 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 405 */         somethingChanged = true;
/* 406 */         doStore();
/*     */       }
/*     */ 
/* 417 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 421 */       throw handleSQLException("delete from AMM_DATA_TYPE where    AMM_DATA_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 425 */       if (deleteStmt != null)
/*     */       {
/* 427 */         closeStatement(deleteStmt);
/* 428 */         closeConnection();
/*     */       }
/*     */ 
/* 431 */       this.mDetails = null;
/*     */ 
/* 433 */       if ((somethingChanged) && 
/* 434 */         (timer != null))
/* 435 */         timer.logDebug("update", "collection"); 
/* 435 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(AmmModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 458 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 460 */     PreparedStatement stmt = null;
/* 461 */     ResultSet resultSet = null;
/*     */ 
/* 463 */     int itemCount = 0;
/*     */ 
/* 465 */     AmmFinanceCubeEVO owningEVO = null;
/* 466 */     Iterator ownersIter = owners.iterator();
/* 467 */     while (ownersIter.hasNext())
/*     */     {
/* 469 */       owningEVO = (AmmFinanceCubeEVO)ownersIter.next();
/* 470 */       owningEVO.setAmmDataTypesAllItemsLoaded(true);
/*     */     }
/* 472 */     ownersIter = owners.iterator();
/* 473 */     owningEVO = (AmmFinanceCubeEVO)ownersIter.next();
/* 474 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 478 */       stmt = getConnection().prepareStatement("select AMM_DATA_TYPE.AMM_DATA_TYPE_ID,AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID,AMM_DATA_TYPE.DATA_TYPE_ID,AMM_DATA_TYPE.SRC_DATA_TYPE_ID,AMM_DATA_TYPE.SRC_START_YEAR_OFFSET,AMM_DATA_TYPE.SRC_END_YEAR_OFFSET,AMM_DATA_TYPE.UPDATED_BY_USER_ID,AMM_DATA_TYPE.UPDATED_TIME,AMM_DATA_TYPE.CREATED_TIME from AMM_DATA_TYPE,AMM_FINANCE_CUBE where 1=1 and AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID = AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID and AMM_FINANCE_CUBE.AMM_MODEL_ID = ? order by  AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID ,AMM_DATA_TYPE.AMM_DATA_TYPE_ID");
/*     */ 
/* 480 */       int col = 1;
/* 481 */       stmt.setInt(col++, entityPK.getAmmModelId());
/*     */ 
/* 483 */       resultSet = stmt.executeQuery();
/*     */ 
/* 486 */       while (resultSet.next())
/*     */       {
/* 488 */         itemCount++;
/* 489 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 494 */         while (this.mDetails.getAmmFinanceCubeId() != owningEVO.getAmmFinanceCubeId())
/*     */         {
/* 498 */           if (!ownersIter.hasNext())
/*     */           {
/* 500 */             this._log.debug("bulkGetAll", "can't find owning [AmmFinanceCubeId=" + this.mDetails.getAmmFinanceCubeId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 504 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 505 */             ownersIter = owners.iterator();
/* 506 */             while (ownersIter.hasNext())
/*     */             {
/* 508 */               owningEVO = (AmmFinanceCubeEVO)ownersIter.next();
/* 509 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 511 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 513 */           owningEVO = (AmmFinanceCubeEVO)ownersIter.next();
/*     */         }
/* 515 */         if (owningEVO.getAmmDataTypes() == null)
/*     */         {
/* 517 */           theseItems = new ArrayList();
/* 518 */           owningEVO.setAmmDataTypes(theseItems);
/* 519 */           owningEVO.setAmmDataTypesAllItemsLoaded(true);
/*     */         }
/* 521 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 524 */       if (timer != null) {
/* 525 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 530 */       throw handleSQLException("select AMM_DATA_TYPE.AMM_DATA_TYPE_ID,AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID,AMM_DATA_TYPE.DATA_TYPE_ID,AMM_DATA_TYPE.SRC_DATA_TYPE_ID,AMM_DATA_TYPE.SRC_START_YEAR_OFFSET,AMM_DATA_TYPE.SRC_END_YEAR_OFFSET,AMM_DATA_TYPE.UPDATED_BY_USER_ID,AMM_DATA_TYPE.UPDATED_TIME,AMM_DATA_TYPE.CREATED_TIME from AMM_DATA_TYPE,AMM_FINANCE_CUBE where 1=1 and AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID = AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID and AMM_FINANCE_CUBE.AMM_MODEL_ID = ? order by  AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID ,AMM_DATA_TYPE.AMM_DATA_TYPE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 534 */       closeResultSet(resultSet);
/* 535 */       closeStatement(stmt);
/* 536 */       closeConnection();
/*     */ 
/* 538 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectAmmFinanceCubeId, String dependants, Collection currentList)
/*     */   {
/* 563 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 564 */     PreparedStatement stmt = null;
/* 565 */     ResultSet resultSet = null;
/*     */ 
/* 567 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 571 */       stmt = getConnection().prepareStatement("select AMM_DATA_TYPE.AMM_DATA_TYPE_ID,AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID,AMM_DATA_TYPE.DATA_TYPE_ID,AMM_DATA_TYPE.SRC_DATA_TYPE_ID,AMM_DATA_TYPE.SRC_START_YEAR_OFFSET,AMM_DATA_TYPE.SRC_END_YEAR_OFFSET,AMM_DATA_TYPE.UPDATED_BY_USER_ID,AMM_DATA_TYPE.UPDATED_TIME,AMM_DATA_TYPE.CREATED_TIME from AMM_DATA_TYPE where    AMM_FINANCE_CUBE_ID = ? ");
/*     */ 
/* 573 */       int col = 1;
/* 574 */       stmt.setInt(col++, selectAmmFinanceCubeId);
/*     */ 
/* 576 */       resultSet = stmt.executeQuery();
/*     */ 
/* 578 */       while (resultSet.next())
/*     */       {
/* 580 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 583 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 586 */       if (currentList != null)
/*     */       {
/* 589 */         ListIterator iter = items.listIterator();
/* 590 */         AmmDataTypeEVO currentEVO = null;
/* 591 */         AmmDataTypeEVO newEVO = null;
/* 592 */         while (iter.hasNext())
/*     */         {
/* 594 */           newEVO = (AmmDataTypeEVO)iter.next();
/* 595 */           Iterator iter2 = currentList.iterator();
/* 596 */           while (iter2.hasNext())
/*     */           {
/* 598 */             currentEVO = (AmmDataTypeEVO)iter2.next();
/* 599 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 601 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 607 */         Iterator iter2 = currentList.iterator();
/* 608 */         while (iter2.hasNext())
/*     */         {
/* 610 */           currentEVO = (AmmDataTypeEVO)iter2.next();
/* 611 */           if (currentEVO.insertPending()) {
/* 612 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 616 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 620 */       throw handleSQLException("select AMM_DATA_TYPE.AMM_DATA_TYPE_ID,AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID,AMM_DATA_TYPE.DATA_TYPE_ID,AMM_DATA_TYPE.SRC_DATA_TYPE_ID,AMM_DATA_TYPE.SRC_START_YEAR_OFFSET,AMM_DATA_TYPE.SRC_END_YEAR_OFFSET,AMM_DATA_TYPE.UPDATED_BY_USER_ID,AMM_DATA_TYPE.UPDATED_TIME,AMM_DATA_TYPE.CREATED_TIME from AMM_DATA_TYPE where    AMM_FINANCE_CUBE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 624 */       closeResultSet(resultSet);
/* 625 */       closeStatement(stmt);
/* 626 */       closeConnection();
/*     */ 
/* 628 */       if (timer != null) {
/* 629 */         timer.logDebug("getAll", " AmmFinanceCubeId=" + selectAmmFinanceCubeId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 634 */     return items;
/*     */   }
/*     */ 
/*     */   public AmmDataTypeEVO getDetails(AmmModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 648 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 651 */     if (this.mDetails == null) {
/* 652 */       doLoad(((AmmDataTypeCK)paramCK).getAmmDataTypePK());
/*     */     }
/* 654 */     else if (!this.mDetails.getPK().equals(((AmmDataTypeCK)paramCK).getAmmDataTypePK())) {
/* 655 */       doLoad(((AmmDataTypeCK)paramCK).getAmmDataTypePK());
/*     */     }
/*     */ 
/* 658 */     AmmDataTypeEVO details = new AmmDataTypeEVO();
/* 659 */     details = this.mDetails.deepClone();
/*     */ 
/* 661 */     if (timer != null) {
/* 662 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 664 */     return details;
/*     */   }
/*     */ 
/*     */   public AmmDataTypeEVO getDetails(AmmModelCK paramCK, AmmDataTypeEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 670 */     AmmDataTypeEVO savedEVO = this.mDetails;
/* 671 */     this.mDetails = paramEVO;
/* 672 */     AmmDataTypeEVO newEVO = getDetails(paramCK, dependants);
/* 673 */     this.mDetails = savedEVO;
/* 674 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public AmmDataTypeEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 680 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 684 */     AmmDataTypeEVO details = this.mDetails.deepClone();
/*     */ 
/* 686 */     if (timer != null) {
/* 687 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 689 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 694 */     return "AmmDataType";
/*     */   }
/*     */ 
/*     */   public AmmDataTypeRefImpl getRef(AmmDataTypePK paramAmmDataTypePK)
/*     */   {
/* 699 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 700 */     PreparedStatement stmt = null;
/* 701 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 704 */       stmt = getConnection().prepareStatement("select 0,AMM_MODEL.AMM_MODEL_ID,AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID from AMM_DATA_TYPE,AMM_MODEL,AMM_FINANCE_CUBE where 1=1 and AMM_DATA_TYPE.AMM_DATA_TYPE_ID = ? and AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID = AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID and AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID = AMM_MODEL.AMM_FINANCE_CUBE_ID");
/* 705 */       int col = 1;
/* 706 */       stmt.setInt(col++, paramAmmDataTypePK.getAmmDataTypeId());
/*     */ 
/* 708 */       resultSet = stmt.executeQuery();
/*     */ 
/* 710 */       if (!resultSet.next()) {
/* 711 */         throw new RuntimeException(getEntityName() + " getRef " + paramAmmDataTypePK + " not found");
/*     */       }
/* 713 */       col = 2;
/* 714 */       AmmModelPK newAmmModelPK = new AmmModelPK(resultSet.getInt(col++));
/*     */ 
/* 718 */       AmmFinanceCubePK newAmmFinanceCubePK = new AmmFinanceCubePK(resultSet.getInt(col++));
/*     */ 
/* 722 */       String textAmmDataType = "";
/* 723 */       AmmDataTypeCK ckAmmDataType = new AmmDataTypeCK(newAmmModelPK, newAmmFinanceCubePK, paramAmmDataTypePK);
/*     */ 
/* 729 */       AmmDataTypeRefImpl localAmmDataTypeRefImpl = new AmmDataTypeRefImpl(ckAmmDataType, textAmmDataType);
/*     */       return localAmmDataTypeRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 734 */       throw handleSQLException(paramAmmDataTypePK, "select 0,AMM_MODEL.AMM_MODEL_ID,AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID from AMM_DATA_TYPE,AMM_MODEL,AMM_FINANCE_CUBE where 1=1 and AMM_DATA_TYPE.AMM_DATA_TYPE_ID = ? and AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID = AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID and AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID = AMM_MODEL.AMM_FINANCE_CUBE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 738 */       closeResultSet(resultSet);
/* 739 */       closeStatement(stmt);
/* 740 */       closeConnection();
/*     */ 
/* 742 */       if (timer != null)
/* 743 */         timer.logDebug("getRef", paramAmmDataTypePK); 
/* 743 */     }
/*     */   }
/*     */ 
/*     */   public boolean isDataTypeMapped(int dataTypeId)
/*     */   {
/* 762 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 763 */     PreparedStatement stmt = null;
/* 764 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 767 */       stmt = getConnection().prepareStatement(QUERY_IS_MAPPED_DATA_TYPE);
/*     */ 
/* 769 */       stmt.setInt(1, dataTypeId);
/* 770 */       stmt.setInt(2, dataTypeId);
/*     */ 
/* 772 */       resultSet = stmt.executeQuery();
/*     */ 
/* 774 */       boolean i = (resultSet.next()) && (resultSet.getInt("nmfc") > 0) ? true : false;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 778 */       throw handleSQLException(QUERY_IS_MAPPED_DATA_TYPE, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 782 */       closeResultSet(resultSet);
/* 783 */       closeStatement(stmt);
/* 784 */       closeConnection();
/*     */ 
/* 786 */       if (timer != null)
/* 787 */         timer.logDebug("isDataTypeMapped", Integer.valueOf(dataTypeId)); 
/* 787 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.amm.AmmDataTypeDAO
 * JD-Core Version:    0.6.0
 */