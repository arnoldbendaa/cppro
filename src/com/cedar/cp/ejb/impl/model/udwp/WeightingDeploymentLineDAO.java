/*     */ package com.cedar.cp.ejb.impl.model.udwp;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingDeploymentLineCK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingDeploymentLinePK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingDeploymentLineRefImpl;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingDeploymentPK;
/*     */ import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
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
/*     */ public class WeightingDeploymentLineDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT_LINE.LINE_IDX,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.DATA_TYPE_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_TIME,WEIGHTING_DEPLOYMENT_LINE.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from WEIGHTING_DEPLOYMENT_LINE where    DEPLOYMENT_ID = ? AND LINE_IDX = ? ";
/*     */   protected static final String SQL_CREATE = "insert into WEIGHTING_DEPLOYMENT_LINE ( DEPLOYMENT_ID,LINE_IDX,ACCOUNT_STRUCTURE_ID,ACCOUNT_STRUCTURE_ELEMENT_ID,ACCOUNT_SELECTION_FLAG,BUSINESS_STRUCTURE_ID,BUSINESS_STRUCTURE_ELEMENT_ID,BUSINESS_SELECTION_FLAG,DATA_TYPE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update WEIGHTING_DEPLOYMENT_LINE set ACCOUNT_STRUCTURE_ID = ?,ACCOUNT_STRUCTURE_ELEMENT_ID = ?,ACCOUNT_SELECTION_FLAG = ?,BUSINESS_STRUCTURE_ID = ?,BUSINESS_STRUCTURE_ELEMENT_ID = ?,BUSINESS_SELECTION_FLAG = ?,DATA_TYPE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DEPLOYMENT_ID = ? AND LINE_IDX = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from WEIGHTING_DEPLOYMENT_LINE where    DEPLOYMENT_ID = ? AND LINE_IDX = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from WEIGHTING_DEPLOYMENT_LINE,WEIGHTING_DEPLOYMENT,WEIGHTING_PROFILE where 1=1 and WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID = WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID and WEIGHTING_DEPLOYMENT.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WEIGHTING_PROFILE.MODEL_ID = ? order by  WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID ,WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID ,WEIGHTING_DEPLOYMENT_LINE.LINE_IDX";
/*     */   protected static final String SQL_GET_ALL = " from WEIGHTING_DEPLOYMENT_LINE where    DEPLOYMENT_ID = ? ";
/*     */   protected WeightingDeploymentLineEVO mDetails;
/*     */ 
/*     */   public WeightingDeploymentLineDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public WeightingDeploymentLineDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public WeightingDeploymentLineDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected WeightingDeploymentLinePK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(WeightingDeploymentLineEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private WeightingDeploymentLineEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  98 */     int col = 1;
/*  99 */     WeightingDeploymentLineEVO evo = new WeightingDeploymentLineEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedBooleanFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedBooleanFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++));
/*     */ 
/* 111 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 112 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 113 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 114 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(WeightingDeploymentLineEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 119 */     int col = startCol_;
/* 120 */     stmt_.setInt(col++, evo_.getDeploymentId());
/* 121 */     stmt_.setInt(col++, evo_.getLineIdx());
/* 122 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(WeightingDeploymentLineEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 127 */     int col = startCol_;
/* 128 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getAccountStructureId());
/* 129 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getAccountStructureElementId());
/* 130 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getAccountSelectionFlag());
/* 131 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getBusinessStructureId());
/* 132 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getBusinessStructureElementId());
/* 133 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getBusinessSelectionFlag());
/* 134 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDataTypeId());
/* 135 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 136 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 137 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 138 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(WeightingDeploymentLinePK pk)
/*     */     throws ValidationException
/*     */   {
/* 155 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 157 */     PreparedStatement stmt = null;
/* 158 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 162 */       stmt = getConnection().prepareStatement("select WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT_LINE.LINE_IDX,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.DATA_TYPE_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_TIME,WEIGHTING_DEPLOYMENT_LINE.CREATED_TIME from WEIGHTING_DEPLOYMENT_LINE where    DEPLOYMENT_ID = ? AND LINE_IDX = ? ");
/*     */ 
/* 165 */       int col = 1;
/* 166 */       stmt.setInt(col++, pk.getDeploymentId());
/* 167 */       stmt.setInt(col++, pk.getLineIdx());
/*     */ 
/* 169 */       resultSet = stmt.executeQuery();
/*     */ 
/* 171 */       if (!resultSet.next()) {
/* 172 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 175 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 176 */       if (this.mDetails.isModified())
/* 177 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 181 */       throw handleSQLException(pk, "select WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT_LINE.LINE_IDX,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.DATA_TYPE_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_TIME,WEIGHTING_DEPLOYMENT_LINE.CREATED_TIME from WEIGHTING_DEPLOYMENT_LINE where    DEPLOYMENT_ID = ? AND LINE_IDX = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 185 */       closeResultSet(resultSet);
/* 186 */       closeStatement(stmt);
/* 187 */       closeConnection();
/*     */ 
/* 189 */       if (timer != null)
/* 190 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 231 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 232 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 237 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 238 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 239 */       stmt = getConnection().prepareStatement("insert into WEIGHTING_DEPLOYMENT_LINE ( DEPLOYMENT_ID,LINE_IDX,ACCOUNT_STRUCTURE_ID,ACCOUNT_STRUCTURE_ELEMENT_ID,ACCOUNT_SELECTION_FLAG,BUSINESS_STRUCTURE_ID,BUSINESS_STRUCTURE_ELEMENT_ID,BUSINESS_SELECTION_FLAG,DATA_TYPE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 242 */       int col = 1;
/* 243 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 244 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 247 */       int resultCount = stmt.executeUpdate();
/* 248 */       if (resultCount != 1)
/*     */       {
/* 250 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 253 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 257 */       throw handleSQLException(this.mDetails.getPK(), "insert into WEIGHTING_DEPLOYMENT_LINE ( DEPLOYMENT_ID,LINE_IDX,ACCOUNT_STRUCTURE_ID,ACCOUNT_STRUCTURE_ELEMENT_ID,ACCOUNT_SELECTION_FLAG,BUSINESS_STRUCTURE_ID,BUSINESS_STRUCTURE_ELEMENT_ID,BUSINESS_SELECTION_FLAG,DATA_TYPE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 261 */       closeStatement(stmt);
/* 262 */       closeConnection();
/*     */ 
/* 264 */       if (timer != null)
/* 265 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 295 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 299 */     PreparedStatement stmt = null;
/*     */ 
/* 301 */     boolean mainChanged = this.mDetails.isModified();
/* 302 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 305 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 308 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 309 */         stmt = getConnection().prepareStatement("update WEIGHTING_DEPLOYMENT_LINE set ACCOUNT_STRUCTURE_ID = ?,ACCOUNT_STRUCTURE_ELEMENT_ID = ?,ACCOUNT_SELECTION_FLAG = ?,BUSINESS_STRUCTURE_ID = ?,BUSINESS_STRUCTURE_ELEMENT_ID = ?,BUSINESS_SELECTION_FLAG = ?,DATA_TYPE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DEPLOYMENT_ID = ? AND LINE_IDX = ? ");
/*     */ 
/* 312 */         int col = 1;
/* 313 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 314 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 317 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 320 */         if (resultCount != 1) {
/* 321 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 324 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 333 */       throw handleSQLException(getPK(), "update WEIGHTING_DEPLOYMENT_LINE set ACCOUNT_STRUCTURE_ID = ?,ACCOUNT_STRUCTURE_ELEMENT_ID = ?,ACCOUNT_SELECTION_FLAG = ?,BUSINESS_STRUCTURE_ID = ?,BUSINESS_STRUCTURE_ELEMENT_ID = ?,BUSINESS_SELECTION_FLAG = ?,DATA_TYPE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DEPLOYMENT_ID = ? AND LINE_IDX = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 337 */       closeStatement(stmt);
/* 338 */       closeConnection();
/*     */ 
/* 340 */       if ((timer != null) && (
/* 341 */         (mainChanged) || (dependantChanged)))
/* 342 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 362 */     if (items == null) {
/* 363 */       return false;
/*     */     }
/* 365 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 366 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 368 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 373 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 374 */       while (iter2.hasNext())
/*     */       {
/* 376 */         this.mDetails = ((WeightingDeploymentLineEVO)iter2.next());
/*     */ 
/* 379 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 381 */         somethingChanged = true;
/*     */ 
/* 384 */         if (deleteStmt == null) {
/* 385 */           deleteStmt = getConnection().prepareStatement("delete from WEIGHTING_DEPLOYMENT_LINE where    DEPLOYMENT_ID = ? AND LINE_IDX = ? ");
/*     */         }
/*     */ 
/* 388 */         int col = 1;
/* 389 */         deleteStmt.setInt(col++, this.mDetails.getDeploymentId());
/* 390 */         deleteStmt.setInt(col++, this.mDetails.getLineIdx());
/*     */ 
/* 392 */         if (this._log.isDebugEnabled()) {
/* 393 */           this._log.debug("update", "WeightingDeploymentLine deleting DeploymentId=" + this.mDetails.getDeploymentId() + ",LineIdx=" + this.mDetails.getLineIdx());
/*     */         }
/*     */ 
/* 399 */         deleteStmt.addBatch();
/*     */ 
/* 402 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 407 */       if (deleteStmt != null)
/*     */       {
/* 409 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 411 */         deleteStmt.executeBatch();
/*     */ 
/* 413 */         if (timer2 != null) {
/* 414 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 418 */       Iterator iter1 = items.values().iterator();
/* 419 */       while (iter1.hasNext())
/*     */       {
/* 421 */         this.mDetails = ((WeightingDeploymentLineEVO)iter1.next());
/*     */ 
/* 423 */         if (this.mDetails.insertPending())
/*     */         {
/* 425 */           somethingChanged = true;
/* 426 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 429 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 431 */         somethingChanged = true;
/* 432 */         doStore();
/*     */       }
/*     */ 
/* 443 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 447 */       throw handleSQLException("delete from WEIGHTING_DEPLOYMENT_LINE where    DEPLOYMENT_ID = ? AND LINE_IDX = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 451 */       if (deleteStmt != null)
/*     */       {
/* 453 */         closeStatement(deleteStmt);
/* 454 */         closeConnection();
/*     */       }
/*     */ 
/* 457 */       this.mDetails = null;
/*     */ 
/* 459 */       if ((somethingChanged) && 
/* 460 */         (timer != null))
/* 461 */         timer.logDebug("update", "collection"); 
/* 461 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 488 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 490 */     PreparedStatement stmt = null;
/* 491 */     ResultSet resultSet = null;
/*     */ 
/* 493 */     int itemCount = 0;
/*     */ 
/* 495 */     WeightingDeploymentEVO owningEVO = null;
/* 496 */     Iterator ownersIter = owners.iterator();
/* 497 */     while (ownersIter.hasNext())
/*     */     {
/* 499 */       owningEVO = (WeightingDeploymentEVO)ownersIter.next();
/* 500 */       owningEVO.setDeploymentLinesAllItemsLoaded(true);
/*     */     }
/* 502 */     ownersIter = owners.iterator();
/* 503 */     owningEVO = (WeightingDeploymentEVO)ownersIter.next();
/* 504 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 508 */       stmt = getConnection().prepareStatement("select WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT_LINE.LINE_IDX,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.DATA_TYPE_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_TIME,WEIGHTING_DEPLOYMENT_LINE.CREATED_TIME from WEIGHTING_DEPLOYMENT_LINE,WEIGHTING_DEPLOYMENT,WEIGHTING_PROFILE where 1=1 and WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID = WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID and WEIGHTING_DEPLOYMENT.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WEIGHTING_PROFILE.MODEL_ID = ? order by  WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID ,WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID ,WEIGHTING_DEPLOYMENT_LINE.LINE_IDX");
/*     */ 
/* 510 */       int col = 1;
/* 511 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 513 */       resultSet = stmt.executeQuery();
/*     */ 
/* 516 */       while (resultSet.next())
/*     */       {
/* 518 */         itemCount++;
/* 519 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 524 */         while (this.mDetails.getDeploymentId() != owningEVO.getDeploymentId())
/*     */         {
/* 528 */           if (!ownersIter.hasNext())
/*     */           {
/* 530 */             this._log.debug("bulkGetAll", "can't find owning [DeploymentId=" + this.mDetails.getDeploymentId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 534 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 535 */             ownersIter = owners.iterator();
/* 536 */             while (ownersIter.hasNext())
/*     */             {
/* 538 */               owningEVO = (WeightingDeploymentEVO)ownersIter.next();
/* 539 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 541 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 543 */           owningEVO = (WeightingDeploymentEVO)ownersIter.next();
/*     */         }
/* 545 */         if (owningEVO.getDeploymentLines() == null)
/*     */         {
/* 547 */           theseItems = new ArrayList();
/* 548 */           owningEVO.setDeploymentLines(theseItems);
/* 549 */           owningEVO.setDeploymentLinesAllItemsLoaded(true);
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
/* 560 */       throw handleSQLException("select WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT_LINE.LINE_IDX,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.DATA_TYPE_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_TIME,WEIGHTING_DEPLOYMENT_LINE.CREATED_TIME from WEIGHTING_DEPLOYMENT_LINE,WEIGHTING_DEPLOYMENT,WEIGHTING_PROFILE where 1=1 and WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID = WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID and WEIGHTING_DEPLOYMENT.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WEIGHTING_PROFILE.MODEL_ID = ? order by  WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID ,WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID ,WEIGHTING_DEPLOYMENT_LINE.LINE_IDX", sqle);
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
/*     */   public Collection getAll(int selectDeploymentId, String dependants, Collection currentList)
/*     */   {
/* 593 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 594 */     PreparedStatement stmt = null;
/* 595 */     ResultSet resultSet = null;
/*     */ 
/* 597 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 601 */       stmt = getConnection().prepareStatement("select WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT_LINE.LINE_IDX,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.DATA_TYPE_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_TIME,WEIGHTING_DEPLOYMENT_LINE.CREATED_TIME from WEIGHTING_DEPLOYMENT_LINE where    DEPLOYMENT_ID = ? ");
/*     */ 
/* 603 */       int col = 1;
/* 604 */       stmt.setInt(col++, selectDeploymentId);
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
/* 620 */         WeightingDeploymentLineEVO currentEVO = null;
/* 621 */         WeightingDeploymentLineEVO newEVO = null;
/* 622 */         while (iter.hasNext())
/*     */         {
/* 624 */           newEVO = (WeightingDeploymentLineEVO)iter.next();
/* 625 */           Iterator iter2 = currentList.iterator();
/* 626 */           while (iter2.hasNext())
/*     */           {
/* 628 */             currentEVO = (WeightingDeploymentLineEVO)iter2.next();
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
/* 640 */           currentEVO = (WeightingDeploymentLineEVO)iter2.next();
/* 641 */           if (currentEVO.insertPending()) {
/* 642 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 646 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 650 */       throw handleSQLException("select WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT_LINE.LINE_IDX,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.ACCOUNT_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_STRUCTURE_ELEMENT_ID,WEIGHTING_DEPLOYMENT_LINE.BUSINESS_SELECTION_FLAG,WEIGHTING_DEPLOYMENT_LINE.DATA_TYPE_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT_LINE.UPDATED_TIME,WEIGHTING_DEPLOYMENT_LINE.CREATED_TIME from WEIGHTING_DEPLOYMENT_LINE where    DEPLOYMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 654 */       closeResultSet(resultSet);
/* 655 */       closeStatement(stmt);
/* 656 */       closeConnection();
/*     */ 
/* 658 */       if (timer != null) {
/* 659 */         timer.logDebug("getAll", " DeploymentId=" + selectDeploymentId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 664 */     return items;
/*     */   }
/*     */ 
/*     */   public WeightingDeploymentLineEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 678 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 681 */     if (this.mDetails == null) {
/* 682 */       doLoad(((WeightingDeploymentLineCK)paramCK).getWeightingDeploymentLinePK());
/*     */     }
/* 684 */     else if (!this.mDetails.getPK().equals(((WeightingDeploymentLineCK)paramCK).getWeightingDeploymentLinePK())) {
/* 685 */       doLoad(((WeightingDeploymentLineCK)paramCK).getWeightingDeploymentLinePK());
/*     */     }
/*     */ 
/* 688 */     WeightingDeploymentLineEVO details = new WeightingDeploymentLineEVO();
/* 689 */     details = this.mDetails.deepClone();
/*     */ 
/* 691 */     if (timer != null) {
/* 692 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 694 */     return details;
/*     */   }
/*     */ 
/*     */   public WeightingDeploymentLineEVO getDetails(ModelCK paramCK, WeightingDeploymentLineEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 700 */     WeightingDeploymentLineEVO savedEVO = this.mDetails;
/* 701 */     this.mDetails = paramEVO;
/* 702 */     WeightingDeploymentLineEVO newEVO = getDetails(paramCK, dependants);
/* 703 */     this.mDetails = savedEVO;
/* 704 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public WeightingDeploymentLineEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 710 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 714 */     WeightingDeploymentLineEVO details = this.mDetails.deepClone();
/*     */ 
/* 716 */     if (timer != null) {
/* 717 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 719 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 724 */     return "WeightingDeploymentLine";
/*     */   }
/*     */ 
/*     */   public WeightingDeploymentLineRefImpl getRef(WeightingDeploymentLinePK paramWeightingDeploymentLinePK)
/*     */   {
/* 729 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 730 */     PreparedStatement stmt = null;
/* 731 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 734 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,WEIGHTING_PROFILE.PROFILE_ID,WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID from WEIGHTING_DEPLOYMENT_LINE,MODEL,WEIGHTING_PROFILE,WEIGHTING_DEPLOYMENT where 1=1 and WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID = ? and WEIGHTING_DEPLOYMENT_LINE.LINE_IDX = ? and WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID = WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID and WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID = WEIGHTING_PROFILE.DEPLOYMENT_ID and WEIGHTING_PROFILE.PROFILE_ID = MODEL.PROFILE_ID");
/* 735 */       int col = 1;
/* 736 */       stmt.setInt(col++, paramWeightingDeploymentLinePK.getDeploymentId());
/* 737 */       stmt.setInt(col++, paramWeightingDeploymentLinePK.getLineIdx());
/*     */ 
/* 739 */       resultSet = stmt.executeQuery();
/*     */ 
/* 741 */       if (!resultSet.next()) {
/* 742 */         throw new RuntimeException(getEntityName() + " getRef " + paramWeightingDeploymentLinePK + " not found");
/*     */       }
/* 744 */       col = 2;
/* 745 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 749 */       WeightingProfilePK newWeightingProfilePK = new WeightingProfilePK(resultSet.getInt(col++));
/*     */ 
/* 753 */       WeightingDeploymentPK newWeightingDeploymentPK = new WeightingDeploymentPK(resultSet.getInt(col++));
/*     */ 
/* 757 */       String textWeightingDeploymentLine = "";
/* 758 */       WeightingDeploymentLineCK ckWeightingDeploymentLine = new WeightingDeploymentLineCK(newModelPK, newWeightingProfilePK, newWeightingDeploymentPK, paramWeightingDeploymentLinePK);
/*     */ 
/* 765 */       WeightingDeploymentLineRefImpl localWeightingDeploymentLineRefImpl = new WeightingDeploymentLineRefImpl(ckWeightingDeploymentLine, textWeightingDeploymentLine);
/*     */       return localWeightingDeploymentLineRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 770 */       throw handleSQLException(paramWeightingDeploymentLinePK, "select 0,MODEL.MODEL_ID,WEIGHTING_PROFILE.PROFILE_ID,WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID from WEIGHTING_DEPLOYMENT_LINE,MODEL,WEIGHTING_PROFILE,WEIGHTING_DEPLOYMENT where 1=1 and WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID = ? and WEIGHTING_DEPLOYMENT_LINE.LINE_IDX = ? and WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID = WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID and WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID = WEIGHTING_PROFILE.DEPLOYMENT_ID and WEIGHTING_PROFILE.PROFILE_ID = MODEL.PROFILE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 774 */       closeResultSet(resultSet);
/* 775 */       closeStatement(stmt);
/* 776 */       closeConnection();
/*     */ 
/* 778 */       if (timer != null)
/* 779 */         timer.logDebug("getRef", paramWeightingDeploymentLinePK); 
/* 779 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.udwp.WeightingDeploymentLineDAO
 * JD-Core Version:    0.6.0
 */