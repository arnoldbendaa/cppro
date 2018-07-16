/*     */ package com.cedar.cp.ejb.impl.model;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.FinanceCubePK;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.RollUpRuleCK;
/*     */ import com.cedar.cp.dto.model.RollUpRulePK;
/*     */ import com.cedar.cp.dto.model.RollUpRuleRefImpl;
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
/*     */ public class RollUpRuleDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select ROLL_UP_RULE.ROLL_UP_RULE_ID,ROLL_UP_RULE.FINANCE_CUBE_ID,ROLL_UP_RULE.DATA_TYPE_ID,ROLL_UP_RULE.DIMENSION_ID,ROLL_UP_RULE.ROLL_UP,ROLL_UP_RULE.UPDATED_BY_USER_ID,ROLL_UP_RULE.UPDATED_TIME,ROLL_UP_RULE.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from ROLL_UP_RULE where    ROLL_UP_RULE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into ROLL_UP_RULE ( ROLL_UP_RULE_ID,FINANCE_CUBE_ID,DATA_TYPE_ID,DIMENSION_ID,ROLL_UP,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update ROLL_UP_RULE set FINANCE_CUBE_ID = ?,DATA_TYPE_ID = ?,DIMENSION_ID = ?,ROLL_UP = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ROLL_UP_RULE_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from ROLL_UP_RULE where    ROLL_UP_RULE_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from ROLL_UP_RULE,FINANCE_CUBE where 1=1 and ROLL_UP_RULE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  ROLL_UP_RULE.FINANCE_CUBE_ID ,ROLL_UP_RULE.ROLL_UP_RULE_ID";
/*     */   protected static final String SQL_GET_ALL = " from ROLL_UP_RULE where    FINANCE_CUBE_ID = ? ";
/*     */   protected RollUpRuleEVO mDetails;
/*     */ 
/*     */   public RollUpRuleDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public RollUpRuleDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public RollUpRuleDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected RollUpRulePK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(RollUpRuleEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private RollUpRuleEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  91 */     int col = 1;
/*  92 */     RollUpRuleEVO evo = new RollUpRuleEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"));
/*     */ 
/* 100 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 101 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 102 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 103 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(RollUpRuleEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 108 */     int col = startCol_;
/* 109 */     stmt_.setInt(col++, evo_.getRollUpRuleId());
/* 110 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(RollUpRuleEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 115 */     int col = startCol_;
/* 116 */     stmt_.setInt(col++, evo_.getFinanceCubeId());
/* 117 */     stmt_.setInt(col++, evo_.getDataTypeId());
/* 118 */     stmt_.setInt(col++, evo_.getDimensionId());
/* 119 */     if (evo_.getRollUp())
/* 120 */       stmt_.setString(col++, "Y");
/*     */     else
/* 122 */       stmt_.setString(col++, " ");
/* 123 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 124 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 125 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 126 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(RollUpRulePK pk)
/*     */     throws ValidationException
/*     */   {
/* 142 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 144 */     PreparedStatement stmt = null;
/* 145 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 149 */       stmt = getConnection().prepareStatement("select ROLL_UP_RULE.ROLL_UP_RULE_ID,ROLL_UP_RULE.FINANCE_CUBE_ID,ROLL_UP_RULE.DATA_TYPE_ID,ROLL_UP_RULE.DIMENSION_ID,ROLL_UP_RULE.ROLL_UP,ROLL_UP_RULE.UPDATED_BY_USER_ID,ROLL_UP_RULE.UPDATED_TIME,ROLL_UP_RULE.CREATED_TIME from ROLL_UP_RULE where    ROLL_UP_RULE_ID = ? ");
/*     */ 
/* 152 */       int col = 1;
/* 153 */       stmt.setInt(col++, pk.getRollUpRuleId());
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
/* 167 */       throw handleSQLException(pk, "select ROLL_UP_RULE.ROLL_UP_RULE_ID,ROLL_UP_RULE.FINANCE_CUBE_ID,ROLL_UP_RULE.DATA_TYPE_ID,ROLL_UP_RULE.DIMENSION_ID,ROLL_UP_RULE.ROLL_UP,ROLL_UP_RULE.UPDATED_BY_USER_ID,ROLL_UP_RULE.UPDATED_TIME,ROLL_UP_RULE.CREATED_TIME from ROLL_UP_RULE where    ROLL_UP_RULE_ID = ? ", sqle);
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
/* 209 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 210 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 215 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 216 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 217 */       stmt = getConnection().prepareStatement("insert into ROLL_UP_RULE ( ROLL_UP_RULE_ID,FINANCE_CUBE_ID,DATA_TYPE_ID,DIMENSION_ID,ROLL_UP,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");
/*     */ 
/* 220 */       int col = 1;
/* 221 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 222 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 225 */       int resultCount = stmt.executeUpdate();
/* 226 */       if (resultCount != 1)
/*     */       {
/* 228 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 231 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 235 */       throw handleSQLException(this.mDetails.getPK(), "insert into ROLL_UP_RULE ( ROLL_UP_RULE_ID,FINANCE_CUBE_ID,DATA_TYPE_ID,DIMENSION_ID,ROLL_UP,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 239 */       closeStatement(stmt);
/* 240 */       closeConnection();
/*     */ 
/* 242 */       if (timer != null)
/* 243 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 269 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 273 */     PreparedStatement stmt = null;
/*     */ 
/* 275 */     boolean mainChanged = this.mDetails.isModified();
/* 276 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 279 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 282 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 283 */         stmt = getConnection().prepareStatement("update ROLL_UP_RULE set FINANCE_CUBE_ID = ?,DATA_TYPE_ID = ?,DIMENSION_ID = ?,ROLL_UP = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ROLL_UP_RULE_ID = ? ");
/*     */ 
/* 286 */         int col = 1;
/* 287 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 288 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 291 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 294 */         if (resultCount != 1) {
/* 295 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 298 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 307 */       throw handleSQLException(getPK(), "update ROLL_UP_RULE set FINANCE_CUBE_ID = ?,DATA_TYPE_ID = ?,DIMENSION_ID = ?,ROLL_UP = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ROLL_UP_RULE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 311 */       closeStatement(stmt);
/* 312 */       closeConnection();
/*     */ 
/* 314 */       if ((timer != null) && (
/* 315 */         (mainChanged) || (dependantChanged)))
/* 316 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 335 */     if (items == null) {
/* 336 */       return false;
/*     */     }
/* 338 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 339 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 341 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 346 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 347 */       while (iter2.hasNext())
/*     */       {
/* 349 */         this.mDetails = ((RollUpRuleEVO)iter2.next());
/*     */ 
/* 352 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 354 */         somethingChanged = true;
/*     */ 
/* 357 */         if (deleteStmt == null) {
/* 358 */           deleteStmt = getConnection().prepareStatement("delete from ROLL_UP_RULE where    ROLL_UP_RULE_ID = ? ");
/*     */         }
/*     */ 
/* 361 */         int col = 1;
/* 362 */         deleteStmt.setInt(col++, this.mDetails.getRollUpRuleId());
/*     */ 
/* 364 */         if (this._log.isDebugEnabled()) {
/* 365 */           this._log.debug("update", "RollUpRule deleting RollUpRuleId=" + this.mDetails.getRollUpRuleId());
/*     */         }
/*     */ 
/* 370 */         deleteStmt.addBatch();
/*     */ 
/* 373 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 378 */       if (deleteStmt != null)
/*     */       {
/* 380 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 382 */         deleteStmt.executeBatch();
/*     */ 
/* 384 */         if (timer2 != null) {
/* 385 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 389 */       Iterator iter1 = items.values().iterator();
/* 390 */       while (iter1.hasNext())
/*     */       {
/* 392 */         this.mDetails = ((RollUpRuleEVO)iter1.next());
/*     */ 
/* 394 */         if (this.mDetails.insertPending())
/*     */         {
/* 396 */           somethingChanged = true;
/* 397 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 400 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 402 */         somethingChanged = true;
/* 403 */         doStore();
/*     */       }
/*     */ 
/* 414 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 418 */       throw handleSQLException("delete from ROLL_UP_RULE where    ROLL_UP_RULE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 422 */       if (deleteStmt != null)
/*     */       {
/* 424 */         closeStatement(deleteStmt);
/* 425 */         closeConnection();
/*     */       }
/*     */ 
/* 428 */       this.mDetails = null;
/*     */ 
/* 430 */       if ((somethingChanged) && 
/* 431 */         (timer != null))
/* 432 */         timer.logDebug("update", "collection"); 
/* 432 */     }
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
/* 462 */     FinanceCubeEVO owningEVO = null;
/* 463 */     Iterator ownersIter = owners.iterator();
/* 464 */     while (ownersIter.hasNext())
/*     */     {
/* 466 */       owningEVO = (FinanceCubeEVO)ownersIter.next();
/* 467 */       owningEVO.setRollUpRulesAllItemsLoaded(true);
/*     */     }
/* 469 */     ownersIter = owners.iterator();
/* 470 */     owningEVO = (FinanceCubeEVO)ownersIter.next();
/* 471 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 475 */       stmt = getConnection().prepareStatement("select ROLL_UP_RULE.ROLL_UP_RULE_ID,ROLL_UP_RULE.FINANCE_CUBE_ID,ROLL_UP_RULE.DATA_TYPE_ID,ROLL_UP_RULE.DIMENSION_ID,ROLL_UP_RULE.ROLL_UP,ROLL_UP_RULE.UPDATED_BY_USER_ID,ROLL_UP_RULE.UPDATED_TIME,ROLL_UP_RULE.CREATED_TIME from ROLL_UP_RULE,FINANCE_CUBE where 1=1 and ROLL_UP_RULE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  ROLL_UP_RULE.FINANCE_CUBE_ID ,ROLL_UP_RULE.ROLL_UP_RULE_ID");
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
/* 491 */         while (this.mDetails.getFinanceCubeId() != owningEVO.getFinanceCubeId())
/*     */         {
/* 495 */           if (!ownersIter.hasNext())
/*     */           {
/* 497 */             this._log.debug("bulkGetAll", "can't find owning [FinanceCubeId=" + this.mDetails.getFinanceCubeId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 501 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 502 */             ownersIter = owners.iterator();
/* 503 */             while (ownersIter.hasNext())
/*     */             {
/* 505 */               owningEVO = (FinanceCubeEVO)ownersIter.next();
/* 506 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 508 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 510 */           owningEVO = (FinanceCubeEVO)ownersIter.next();
/*     */         }
/* 512 */         if (owningEVO.getRollUpRules() == null)
/*     */         {
/* 514 */           theseItems = new ArrayList();
/* 515 */           owningEVO.setRollUpRules(theseItems);
/* 516 */           owningEVO.setRollUpRulesAllItemsLoaded(true);
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
/* 527 */       throw handleSQLException("select ROLL_UP_RULE.ROLL_UP_RULE_ID,ROLL_UP_RULE.FINANCE_CUBE_ID,ROLL_UP_RULE.DATA_TYPE_ID,ROLL_UP_RULE.DIMENSION_ID,ROLL_UP_RULE.ROLL_UP,ROLL_UP_RULE.UPDATED_BY_USER_ID,ROLL_UP_RULE.UPDATED_TIME,ROLL_UP_RULE.CREATED_TIME from ROLL_UP_RULE,FINANCE_CUBE where 1=1 and ROLL_UP_RULE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  ROLL_UP_RULE.FINANCE_CUBE_ID ,ROLL_UP_RULE.ROLL_UP_RULE_ID", sqle);
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
/*     */   public Collection getAll(int selectFinanceCubeId, String dependants, Collection currentList)
/*     */   {
/* 560 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 561 */     PreparedStatement stmt = null;
/* 562 */     ResultSet resultSet = null;
/*     */ 
/* 564 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 568 */       stmt = getConnection().prepareStatement("select ROLL_UP_RULE.ROLL_UP_RULE_ID,ROLL_UP_RULE.FINANCE_CUBE_ID,ROLL_UP_RULE.DATA_TYPE_ID,ROLL_UP_RULE.DIMENSION_ID,ROLL_UP_RULE.ROLL_UP,ROLL_UP_RULE.UPDATED_BY_USER_ID,ROLL_UP_RULE.UPDATED_TIME,ROLL_UP_RULE.CREATED_TIME from ROLL_UP_RULE where    FINANCE_CUBE_ID = ? ");
/*     */ 
/* 570 */       int col = 1;
/* 571 */       stmt.setInt(col++, selectFinanceCubeId);
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
/* 587 */         RollUpRuleEVO currentEVO = null;
/* 588 */         RollUpRuleEVO newEVO = null;
/* 589 */         while (iter.hasNext())
/*     */         {
/* 591 */           newEVO = (RollUpRuleEVO)iter.next();
/* 592 */           Iterator iter2 = currentList.iterator();
/* 593 */           while (iter2.hasNext())
/*     */           {
/* 595 */             currentEVO = (RollUpRuleEVO)iter2.next();
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
/* 607 */           currentEVO = (RollUpRuleEVO)iter2.next();
/* 608 */           if (currentEVO.insertPending()) {
/* 609 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 613 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 617 */       throw handleSQLException("select ROLL_UP_RULE.ROLL_UP_RULE_ID,ROLL_UP_RULE.FINANCE_CUBE_ID,ROLL_UP_RULE.DATA_TYPE_ID,ROLL_UP_RULE.DIMENSION_ID,ROLL_UP_RULE.ROLL_UP,ROLL_UP_RULE.UPDATED_BY_USER_ID,ROLL_UP_RULE.UPDATED_TIME,ROLL_UP_RULE.CREATED_TIME from ROLL_UP_RULE where    FINANCE_CUBE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 621 */       closeResultSet(resultSet);
/* 622 */       closeStatement(stmt);
/* 623 */       closeConnection();
/*     */ 
/* 625 */       if (timer != null) {
/* 626 */         timer.logDebug("getAll", " FinanceCubeId=" + selectFinanceCubeId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 631 */     return items;
/*     */   }
/*     */ 
/*     */   public RollUpRuleEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 645 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 648 */     if (this.mDetails == null) {
/* 649 */       doLoad(((RollUpRuleCK)paramCK).getRollUpRulePK());
/*     */     }
/* 651 */     else if (!this.mDetails.getPK().equals(((RollUpRuleCK)paramCK).getRollUpRulePK())) {
/* 652 */       doLoad(((RollUpRuleCK)paramCK).getRollUpRulePK());
/*     */     }
/*     */ 
/* 655 */     RollUpRuleEVO details = new RollUpRuleEVO();
/* 656 */     details = this.mDetails.deepClone();
/*     */ 
/* 658 */     if (timer != null) {
/* 659 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 661 */     return details;
/*     */   }
/*     */ 
/*     */   public RollUpRuleEVO getDetails(ModelCK paramCK, RollUpRuleEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 667 */     RollUpRuleEVO savedEVO = this.mDetails;
/* 668 */     this.mDetails = paramEVO;
/* 669 */     RollUpRuleEVO newEVO = getDetails(paramCK, dependants);
/* 670 */     this.mDetails = savedEVO;
/* 671 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public RollUpRuleEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 677 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 681 */     RollUpRuleEVO details = this.mDetails.deepClone();
/*     */ 
/* 683 */     if (timer != null) {
/* 684 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 686 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 691 */     return "RollUpRule";
/*     */   }
/*     */ 
/*     */   public RollUpRuleRefImpl getRef(RollUpRulePK paramRollUpRulePK)
/*     */   {
/* 696 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 697 */     PreparedStatement stmt = null;
/* 698 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 701 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID from ROLL_UP_RULE,MODEL,FINANCE_CUBE where 1=1 and ROLL_UP_RULE.ROLL_UP_RULE_ID = ? and ROLL_UP_RULE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID");
/* 702 */       int col = 1;
/* 703 */       stmt.setInt(col++, paramRollUpRulePK.getRollUpRuleId());
/*     */ 
/* 705 */       resultSet = stmt.executeQuery();
/*     */ 
/* 707 */       if (!resultSet.next()) {
/* 708 */         throw new RuntimeException(getEntityName() + " getRef " + paramRollUpRulePK + " not found");
/*     */       }
/* 710 */       col = 2;
/* 711 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 715 */       FinanceCubePK newFinanceCubePK = new FinanceCubePK(resultSet.getInt(col++));
/*     */ 
/* 719 */       String textRollUpRule = "";
/* 720 */       RollUpRuleCK ckRollUpRule = new RollUpRuleCK(newModelPK, newFinanceCubePK, paramRollUpRulePK);
/*     */ 
/* 726 */       RollUpRuleRefImpl localRollUpRuleRefImpl = new RollUpRuleRefImpl(ckRollUpRule, textRollUpRule);
/*     */       return localRollUpRuleRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 731 */       throw handleSQLException(paramRollUpRulePK, "select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID from ROLL_UP_RULE,MODEL,FINANCE_CUBE where 1=1 and ROLL_UP_RULE.ROLL_UP_RULE_ID = ? and ROLL_UP_RULE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 735 */       closeResultSet(resultSet);
/* 736 */       closeStatement(stmt);
/* 737 */       closeConnection();
/*     */ 
/* 739 */       if (timer != null)
/* 740 */         timer.logDebug("getRef", paramRollUpRulePK); 
/* 740 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.RollUpRuleDAO
 * JD-Core Version:    0.6.0
 */