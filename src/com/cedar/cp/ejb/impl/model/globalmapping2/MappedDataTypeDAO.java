/*     */ package com.cedar.cp.ejb.impl.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.globalmapping2.MappedDataTypeCK;
/*     */ import com.cedar.cp.dto.model.globalmapping2.MappedDataTypePK;
/*     */ import com.cedar.cp.dto.model.globalmapping2.MappedDataTypeRefImpl;
/*     */ import com.cedar.cp.dto.model.globalmapping2.MappedFinanceCubePK;
/*     */ import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2CK;
/*     */ import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
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
/*     */ public class MappedDataTypeDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select MAPPED_DATA_TYPE.MAPPED_DATA_TYPE_ID,MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID,MAPPED_DATA_TYPE.DATA_TYPE_ID,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_1,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_2,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_3,MAPPED_DATA_TYPE.IMP_EXP_STATUS,MAPPED_DATA_TYPE.IMP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.IMP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.UPDATED_BY_USER_ID,MAPPED_DATA_TYPE.UPDATED_TIME,MAPPED_DATA_TYPE.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from MAPPED_DATA_TYPE where    MAPPED_DATA_TYPE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into MAPPED_DATA_TYPE ( MAPPED_DATA_TYPE_ID,MAPPED_FINANCE_CUBE_ID,DATA_TYPE_ID,VALUE_TYPE_VIS_ID_1,VALUE_TYPE_VIS_ID_2,VALUE_TYPE_VIS_ID_3,IMP_EXP_STATUS,IMP_START_YEAR_OFFSET,IMP_END_YEAR_OFFSET,EXP_START_YEAR_OFFSET,EXP_END_YEAR_OFFSET,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update MAPPED_DATA_TYPE set MAPPED_FINANCE_CUBE_ID = ?,DATA_TYPE_ID = ?,VALUE_TYPE_VIS_ID_1 = ?,VALUE_TYPE_VIS_ID_2 = ?,VALUE_TYPE_VIS_ID_3 = ?,IMP_EXP_STATUS = ?,IMP_START_YEAR_OFFSET = ?,IMP_END_YEAR_OFFSET = ?,EXP_START_YEAR_OFFSET = ?,EXP_END_YEAR_OFFSET = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_DATA_TYPE_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from MAPPED_DATA_TYPE where    MAPPED_DATA_TYPE_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from MAPPED_DATA_TYPE,MAPPED_FINANCE_CUBE where 1=1 and MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID = MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID and MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID = ? order by  MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID ,MAPPED_DATA_TYPE.MAPPED_DATA_TYPE_ID";
/*     */   protected static final String SQL_GET_ALL = " from MAPPED_DATA_TYPE where    MAPPED_FINANCE_CUBE_ID = ? ";
/*     */   protected MappedDataTypeEVO mDetails;
/*     */ 
/*     */   public MappedDataTypeDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public MappedDataTypeDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public MappedDataTypeDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected MappedDataTypePK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(MappedDataTypeEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private MappedDataTypeEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  97 */     int col = 1;
/*  98 */     MappedDataTypeEVO evo = new MappedDataTypeEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++));
/*     */ 
/* 112 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 113 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 114 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 115 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(MappedDataTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 120 */     int col = startCol_;
/* 121 */     stmt_.setInt(col++, evo_.getMappedDataTypeId());
/* 122 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(MappedDataTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 127 */     int col = startCol_;
/* 128 */     stmt_.setInt(col++, evo_.getMappedFinanceCubeId());
/* 129 */     stmt_.setInt(col++, evo_.getDataTypeId());
/* 130 */     stmt_.setString(col++, evo_.getValueTypeVisId1());
/* 131 */     stmt_.setString(col++, evo_.getValueTypeVisId2());
/* 132 */     stmt_.setString(col++, evo_.getValueTypeVisId3());
/* 133 */     stmt_.setInt(col++, evo_.getImpExpStatus());
/* 134 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getImpStartYearOffset());
/* 135 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getImpEndYearOffset());
/* 136 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getExpStartYearOffset());
/* 137 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getExpEndYearOffset());
/* 138 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 139 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 140 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 141 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(MappedDataTypePK pk)
/*     */     throws ValidationException
/*     */   {
/* 157 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 159 */     PreparedStatement stmt = null;
/* 160 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 164 */       stmt = getConnection().prepareStatement("select MAPPED_DATA_TYPE.MAPPED_DATA_TYPE_ID,MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID,MAPPED_DATA_TYPE.DATA_TYPE_ID,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_1,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_2,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_3,MAPPED_DATA_TYPE.IMP_EXP_STATUS,MAPPED_DATA_TYPE.IMP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.IMP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.UPDATED_BY_USER_ID,MAPPED_DATA_TYPE.UPDATED_TIME,MAPPED_DATA_TYPE.CREATED_TIME from MAPPED_DATA_TYPE where    MAPPED_DATA_TYPE_ID = ? ");
/*     */ 
/* 167 */       int col = 1;
/* 168 */       stmt.setInt(col++, pk.getMappedDataTypeId());
/*     */ 
/* 170 */       resultSet = stmt.executeQuery();
/*     */ 
/* 172 */       if (!resultSet.next()) {
/* 173 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 176 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 177 */       if (this.mDetails.isModified())
/* 178 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 182 */       throw handleSQLException(pk, "select MAPPED_DATA_TYPE.MAPPED_DATA_TYPE_ID,MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID,MAPPED_DATA_TYPE.DATA_TYPE_ID,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_1,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_2,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_3,MAPPED_DATA_TYPE.IMP_EXP_STATUS,MAPPED_DATA_TYPE.IMP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.IMP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.UPDATED_BY_USER_ID,MAPPED_DATA_TYPE.UPDATED_TIME,MAPPED_DATA_TYPE.CREATED_TIME from MAPPED_DATA_TYPE where    MAPPED_DATA_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 186 */       closeResultSet(resultSet);
/* 187 */       closeStatement(stmt);
/* 188 */       closeConnection();
/*     */ 
/* 190 */       if (timer != null)
/* 191 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 236 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 237 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 242 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 243 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 244 */       stmt = getConnection().prepareStatement("insert into MAPPED_DATA_TYPE ( MAPPED_DATA_TYPE_ID,MAPPED_FINANCE_CUBE_ID,DATA_TYPE_ID,VALUE_TYPE_VIS_ID_1,VALUE_TYPE_VIS_ID_2,VALUE_TYPE_VIS_ID_3,IMP_EXP_STATUS,IMP_START_YEAR_OFFSET,IMP_END_YEAR_OFFSET,EXP_START_YEAR_OFFSET,EXP_END_YEAR_OFFSET,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 247 */       int col = 1;
/* 248 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 249 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 252 */       int resultCount = stmt.executeUpdate();
/* 253 */       if (resultCount != 1)
/*     */       {
/* 255 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 258 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 262 */       throw handleSQLException(this.mDetails.getPK(), "insert into MAPPED_DATA_TYPE ( MAPPED_DATA_TYPE_ID,MAPPED_FINANCE_CUBE_ID,DATA_TYPE_ID,VALUE_TYPE_VIS_ID_1,VALUE_TYPE_VIS_ID_2,VALUE_TYPE_VIS_ID_3,IMP_EXP_STATUS,IMP_START_YEAR_OFFSET,IMP_END_YEAR_OFFSET,EXP_START_YEAR_OFFSET,EXP_END_YEAR_OFFSET,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 266 */       closeStatement(stmt);
/* 267 */       closeConnection();
/*     */ 
/* 269 */       if (timer != null)
/* 270 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 302 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 306 */     PreparedStatement stmt = null;
/*     */ 
/* 308 */     boolean mainChanged = this.mDetails.isModified();
/* 309 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 312 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 315 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 316 */         stmt = getConnection().prepareStatement("update MAPPED_DATA_TYPE set MAPPED_FINANCE_CUBE_ID = ?,DATA_TYPE_ID = ?,VALUE_TYPE_VIS_ID_1 = ?,VALUE_TYPE_VIS_ID_2 = ?,VALUE_TYPE_VIS_ID_3 = ?,IMP_EXP_STATUS = ?,IMP_START_YEAR_OFFSET = ?,IMP_END_YEAR_OFFSET = ?,EXP_START_YEAR_OFFSET = ?,EXP_END_YEAR_OFFSET = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_DATA_TYPE_ID = ? ");
/*     */ 
/* 319 */         int col = 1;
/* 320 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 321 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 324 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 327 */         if (resultCount != 1) {
/* 328 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 331 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 340 */       throw handleSQLException(getPK(), "update MAPPED_DATA_TYPE set MAPPED_FINANCE_CUBE_ID = ?,DATA_TYPE_ID = ?,VALUE_TYPE_VIS_ID_1 = ?,VALUE_TYPE_VIS_ID_2 = ?,VALUE_TYPE_VIS_ID_3 = ?,IMP_EXP_STATUS = ?,IMP_START_YEAR_OFFSET = ?,IMP_END_YEAR_OFFSET = ?,EXP_START_YEAR_OFFSET = ?,EXP_END_YEAR_OFFSET = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_DATA_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 344 */       closeStatement(stmt);
/* 345 */       closeConnection();
/*     */ 
/* 347 */       if ((timer != null) && (
/* 348 */         (mainChanged) || (dependantChanged)))
/* 349 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 368 */     if (items == null) {
/* 369 */       return false;
/*     */     }
/* 371 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 372 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 374 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 379 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 380 */       while (iter2.hasNext())
/*     */       {
/* 382 */         this.mDetails = ((MappedDataTypeEVO)iter2.next());
/*     */ 
/* 385 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 387 */         somethingChanged = true;
/*     */ 
/* 390 */         if (deleteStmt == null) {
/* 391 */           deleteStmt = getConnection().prepareStatement("delete from MAPPED_DATA_TYPE where    MAPPED_DATA_TYPE_ID = ? ");
/*     */         }
/*     */ 
/* 394 */         int col = 1;
/* 395 */         deleteStmt.setInt(col++, this.mDetails.getMappedDataTypeId());
/*     */ 
/* 397 */         if (this._log.isDebugEnabled()) {
/* 398 */           this._log.debug("update", "MappedDataType deleting MappedDataTypeId=" + this.mDetails.getMappedDataTypeId());
/*     */         }
/*     */ 
/* 403 */         deleteStmt.addBatch();
/*     */ 
/* 406 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 411 */       if (deleteStmt != null)
/*     */       {
/* 413 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 415 */         deleteStmt.executeBatch();
/*     */ 
/* 417 */         if (timer2 != null) {
/* 418 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 422 */       Iterator iter1 = items.values().iterator();
/* 423 */       while (iter1.hasNext())
/*     */       {
/* 425 */         this.mDetails = ((MappedDataTypeEVO)iter1.next());
/*     */ 
/* 427 */         if (this.mDetails.insertPending())
/*     */         {
/* 429 */           somethingChanged = true;
/* 430 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 433 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 435 */         somethingChanged = true;
/* 436 */         doStore();
/*     */       }
/*     */ 
/* 447 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 451 */       throw handleSQLException("delete from MAPPED_DATA_TYPE where    MAPPED_DATA_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 455 */       if (deleteStmt != null)
/*     */       {
/* 457 */         closeStatement(deleteStmt);
/* 458 */         closeConnection();
/*     */       }
/*     */ 
/* 461 */       this.mDetails = null;
/*     */ 
/* 463 */       if ((somethingChanged) && 
/* 464 */         (timer != null))
/* 465 */         timer.logDebug("update", "collection"); 
/* 465 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(GlobalMappedModel2PK entityPK, Collection owners, String dependants)
/*     */   {
/* 488 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 490 */     PreparedStatement stmt = null;
/* 491 */     ResultSet resultSet = null;
/*     */ 
/* 493 */     int itemCount = 0;
/*     */ 
/* 495 */     MappedFinanceCubeEVO owningEVO = null;
/* 496 */     Iterator ownersIter = owners.iterator();
/* 497 */     while (ownersIter.hasNext())
/*     */     {
/* 499 */       owningEVO = (MappedFinanceCubeEVO)ownersIter.next();
/* 500 */       owningEVO.setMappedDataTypesAllItemsLoaded(true);
/*     */     }
/* 502 */     ownersIter = owners.iterator();
/* 503 */     owningEVO = (MappedFinanceCubeEVO)ownersIter.next();
/* 504 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 508 */       stmt = getConnection().prepareStatement("select MAPPED_DATA_TYPE.MAPPED_DATA_TYPE_ID,MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID,MAPPED_DATA_TYPE.DATA_TYPE_ID,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_1,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_2,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_3,MAPPED_DATA_TYPE.IMP_EXP_STATUS,MAPPED_DATA_TYPE.IMP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.IMP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.UPDATED_BY_USER_ID,MAPPED_DATA_TYPE.UPDATED_TIME,MAPPED_DATA_TYPE.CREATED_TIME from MAPPED_DATA_TYPE,MAPPED_FINANCE_CUBE where 1=1 and MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID = MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID and MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID = ? order by  MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID ,MAPPED_DATA_TYPE.MAPPED_DATA_TYPE_ID");
/*     */ 
/* 510 */       int col = 1;
/* 511 */       stmt.setInt(col++, entityPK.getMappedModelId());
/*     */ 
/* 513 */       resultSet = stmt.executeQuery();
/*     */ 
/* 516 */       while (resultSet.next())
/*     */       {
/* 518 */         itemCount++;
/* 519 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 524 */         while (this.mDetails.getMappedFinanceCubeId() != owningEVO.getMappedFinanceCubeId())
/*     */         {
/* 528 */           if (!ownersIter.hasNext())
/*     */           {
/* 530 */             this._log.debug("bulkGetAll", "can't find owning [MappedFinanceCubeId=" + this.mDetails.getMappedFinanceCubeId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 534 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 535 */             ownersIter = owners.iterator();
/* 536 */             while (ownersIter.hasNext())
/*     */             {
/* 538 */               owningEVO = (MappedFinanceCubeEVO)ownersIter.next();
/* 539 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 541 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 543 */           owningEVO = (MappedFinanceCubeEVO)ownersIter.next();
/*     */         }
/* 545 */         if (owningEVO.getMappedDataTypes() == null)
/*     */         {
/* 547 */           theseItems = new ArrayList();
/* 548 */           owningEVO.setMappedDataTypes(theseItems);
/* 549 */           owningEVO.setMappedDataTypesAllItemsLoaded(true);
/*     */         }
/* 551 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 554 */       if (timer != null) {
/* 555 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 560 */       throw handleSQLException("select MAPPED_DATA_TYPE.MAPPED_DATA_TYPE_ID,MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID,MAPPED_DATA_TYPE.DATA_TYPE_ID,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_1,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_2,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_3,MAPPED_DATA_TYPE.IMP_EXP_STATUS,MAPPED_DATA_TYPE.IMP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.IMP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.UPDATED_BY_USER_ID,MAPPED_DATA_TYPE.UPDATED_TIME,MAPPED_DATA_TYPE.CREATED_TIME from MAPPED_DATA_TYPE,MAPPED_FINANCE_CUBE where 1=1 and MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID = MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID and MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID = ? order by  MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID ,MAPPED_DATA_TYPE.MAPPED_DATA_TYPE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 564 */       closeResultSet(resultSet);
/* 565 */       closeStatement(stmt);
/* 566 */       closeConnection();
/*     */ 
/* 568 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectMappedFinanceCubeId, String dependants, Collection currentList)
/*     */   {
/* 593 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 594 */     PreparedStatement stmt = null;
/* 595 */     ResultSet resultSet = null;
/*     */ 
/* 597 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 601 */       stmt = getConnection().prepareStatement("select MAPPED_DATA_TYPE.MAPPED_DATA_TYPE_ID,MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID,MAPPED_DATA_TYPE.DATA_TYPE_ID,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_1,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_2,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_3,MAPPED_DATA_TYPE.IMP_EXP_STATUS,MAPPED_DATA_TYPE.IMP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.IMP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.UPDATED_BY_USER_ID,MAPPED_DATA_TYPE.UPDATED_TIME,MAPPED_DATA_TYPE.CREATED_TIME from MAPPED_DATA_TYPE where    MAPPED_FINANCE_CUBE_ID = ? ");
/*     */ 
/* 603 */       int col = 1;
/* 604 */       stmt.setInt(col++, selectMappedFinanceCubeId);
/*     */ 
/* 606 */       resultSet = stmt.executeQuery();
/*     */ 
/* 608 */       while (resultSet.next())
/*     */       {
/* 610 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 613 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 616 */       if (currentList != null)
/*     */       {
/* 619 */         ListIterator iter = items.listIterator();
/* 620 */         MappedDataTypeEVO currentEVO = null;
/* 621 */         MappedDataTypeEVO newEVO = null;
/* 622 */         while (iter.hasNext())
/*     */         {
/* 624 */           newEVO = (MappedDataTypeEVO)iter.next();
/* 625 */           Iterator iter2 = currentList.iterator();
/* 626 */           while (iter2.hasNext())
/*     */           {
/* 628 */             currentEVO = (MappedDataTypeEVO)iter2.next();
/* 629 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 631 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 637 */         Iterator iter2 = currentList.iterator();
/* 638 */         while (iter2.hasNext())
/*     */         {
/* 640 */           currentEVO = (MappedDataTypeEVO)iter2.next();
/* 641 */           if (currentEVO.insertPending()) {
/* 642 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 646 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 650 */       throw handleSQLException("select MAPPED_DATA_TYPE.MAPPED_DATA_TYPE_ID,MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID,MAPPED_DATA_TYPE.DATA_TYPE_ID,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_1,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_2,MAPPED_DATA_TYPE.VALUE_TYPE_VIS_ID_3,MAPPED_DATA_TYPE.IMP_EXP_STATUS,MAPPED_DATA_TYPE.IMP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.IMP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_START_YEAR_OFFSET,MAPPED_DATA_TYPE.EXP_END_YEAR_OFFSET,MAPPED_DATA_TYPE.UPDATED_BY_USER_ID,MAPPED_DATA_TYPE.UPDATED_TIME,MAPPED_DATA_TYPE.CREATED_TIME from MAPPED_DATA_TYPE where    MAPPED_FINANCE_CUBE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 654 */       closeResultSet(resultSet);
/* 655 */       closeStatement(stmt);
/* 656 */       closeConnection();
/*     */ 
/* 658 */       if (timer != null) {
/* 659 */         timer.logDebug("getAll", " MappedFinanceCubeId=" + selectMappedFinanceCubeId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 664 */     return items;
/*     */   }
/*     */ 
/*     */   public MappedDataTypeEVO getDetails(GlobalMappedModel2CK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 678 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 681 */     if (this.mDetails == null) {
/* 682 */       doLoad(((MappedDataTypeCK)paramCK).getMappedDataTypePK());
/*     */     }
/* 684 */     else if (!this.mDetails.getPK().equals(((MappedDataTypeCK)paramCK).getMappedDataTypePK())) {
/* 685 */       doLoad(((MappedDataTypeCK)paramCK).getMappedDataTypePK());
/*     */     }
/*     */ 
/* 688 */     MappedDataTypeEVO details = new MappedDataTypeEVO();
/* 689 */     details = this.mDetails.deepClone();
/*     */ 
/* 691 */     if (timer != null) {
/* 692 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 694 */     return details;
/*     */   }
/*     */ 
/*     */   public MappedDataTypeEVO getDetails(GlobalMappedModel2CK paramCK, MappedDataTypeEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 700 */     MappedDataTypeEVO savedEVO = this.mDetails;
/* 701 */     this.mDetails = paramEVO;
/* 702 */     MappedDataTypeEVO newEVO = getDetails(paramCK, dependants);
/* 703 */     this.mDetails = savedEVO;
/* 704 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public MappedDataTypeEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 710 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 714 */     MappedDataTypeEVO details = this.mDetails.deepClone();
/*     */ 
/* 716 */     if (timer != null) {
/* 717 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 719 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 724 */     return "MappedDataType";
/*     */   }
/*     */ 
/*     */   public MappedDataTypeRefImpl getRef(MappedDataTypePK paramMappedDataTypePK)
/*     */   {
/* 729 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 730 */     PreparedStatement stmt = null;
/* 731 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 734 */       stmt = getConnection().prepareStatement("select 0,MAPPED_MODEL.MAPPED_MODEL_ID,MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID from MAPPED_DATA_TYPE,MAPPED_MODEL,MAPPED_FINANCE_CUBE where 1=1 and MAPPED_DATA_TYPE.MAPPED_DATA_TYPE_ID = ? and MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID = MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID and MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID = MAPPED_MODEL.MAPPED_FINANCE_CUBE_ID");
/* 735 */       int col = 1;
/* 736 */       stmt.setInt(col++, paramMappedDataTypePK.getMappedDataTypeId());
/*     */ 
/* 738 */       resultSet = stmt.executeQuery();
/*     */ 
/* 740 */       if (!resultSet.next()) {
/* 741 */         throw new RuntimeException(getEntityName() + " getRef " + paramMappedDataTypePK + " not found");
/*     */       }
/* 743 */       col = 2;
/* 744 */       GlobalMappedModel2PK newMappedModelPK = new GlobalMappedModel2PK(resultSet.getInt(col++));
/*     */ 
/* 748 */       MappedFinanceCubePK newMappedFinanceCubePK = new MappedFinanceCubePK(resultSet.getInt(col++));
/*     */ 
/* 752 */       String textMappedDataType = "";
/* 753 */       MappedDataTypeCK ckMappedDataType = new MappedDataTypeCK(newMappedModelPK, newMappedFinanceCubePK, paramMappedDataTypePK);
/*     */ 
/* 759 */       MappedDataTypeRefImpl localMappedDataTypeRefImpl = new MappedDataTypeRefImpl(ckMappedDataType, textMappedDataType);
/*     */       return localMappedDataTypeRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 764 */       throw handleSQLException(paramMappedDataTypePK, "select 0,MAPPED_MODEL.MAPPED_MODEL_ID,MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID from MAPPED_DATA_TYPE,MAPPED_MODEL,MAPPED_FINANCE_CUBE where 1=1 and MAPPED_DATA_TYPE.MAPPED_DATA_TYPE_ID = ? and MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID = MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID and MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID = MAPPED_MODEL.MAPPED_FINANCE_CUBE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 768 */       closeResultSet(resultSet);
/* 769 */       closeStatement(stmt);
/* 770 */       closeConnection();
/*     */ 
/* 772 */       if (timer != null)
/* 773 */         timer.logDebug("getRef", paramMappedDataTypePK); 
/* 773 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.mapping.MappedDataTypeDAO
 * JD-Core Version:    0.6.0
 */