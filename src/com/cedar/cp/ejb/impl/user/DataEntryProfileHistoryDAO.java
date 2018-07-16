/*     */ package com.cedar.cp.ejb.impl.user;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.user.AllDataEntryProfileHistorysELO;
/*     */ import com.cedar.cp.dto.user.DataEntryProfileCK;
/*     */ import com.cedar.cp.dto.user.DataEntryProfileHistoryCK;
/*     */ import com.cedar.cp.dto.user.DataEntryProfileHistoryPK;
/*     */ import com.cedar.cp.dto.user.DataEntryProfileHistoryRefImpl;
/*     */ import com.cedar.cp.dto.user.DataEntryProfilePK;
/*     */ import com.cedar.cp.dto.user.DataEntryProfileRefImpl;
/*     */ import com.cedar.cp.dto.user.UserCK;
/*     */ import com.cedar.cp.dto.user.UserPK;
/*     */ import com.cedar.cp.dto.user.UserRefImpl;
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
/*     */ public class DataEntryProfileHistoryDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select DATA_ENTRY_PROFILE_HISTORY.USER_ID,DATA_ENTRY_PROFILE_HISTORY.MODEL_ID,DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID,DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID";
/*     */   protected static final String SQL_LOAD = " from DATA_ENTRY_PROFILE_HISTORY where    USER_ID = ? AND MODEL_ID = ? AND BUDGET_LOCATION_ELEMENT_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into DATA_ENTRY_PROFILE_HISTORY ( USER_ID,MODEL_ID,BUDGET_LOCATION_ELEMENT_ID,DATA_ENTRY_PROFILE_ID) values ( ?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update DATA_ENTRY_PROFILE_HISTORY set DATA_ENTRY_PROFILE_ID = ? where    USER_ID = ? AND MODEL_ID = ? AND BUDGET_LOCATION_ELEMENT_ID = ? ";
/* 296 */   protected static String SQL_ALL_DATA_ENTRY_PROFILE_HISTORYS = "select 0       ,USR.USER_ID      ,USR.NAME      ,DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID      ,DATA_ENTRY_PROFILE.VIS_ID      ,DATA_ENTRY_PROFILE_HISTORY.USER_ID      ,DATA_ENTRY_PROFILE_HISTORY.MODEL_ID      ,DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID      ,DATA_ENTRY_PROFILE_HISTORY.BUDGET_CYCLE_ID from DATA_ENTRY_PROFILE_HISTORY    ,USR    ,DATA_ENTRY_PROFILE where 1=1   and DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID = DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID   and DATA_ENTRY_PROFILE.USER_ID = USR.USER_ID ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from DATA_ENTRY_PROFILE_HISTORY where    USER_ID = ? AND MODEL_ID = ? AND BUDGET_LOCATION_ELEMENT_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from DATA_ENTRY_PROFILE_HISTORY,DATA_ENTRY_PROFILE where 1=1 and DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID = DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID and DATA_ENTRY_PROFILE.USER_ID = ? order by  DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID ,DATA_ENTRY_PROFILE_HISTORY.USER_ID ,DATA_ENTRY_PROFILE_HISTORY.MODEL_ID ,DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID";
/*     */   protected static final String SQL_GET_ALL = " from DATA_ENTRY_PROFILE_HISTORY where    DATA_ENTRY_PROFILE_ID = ? ";
/*     */   protected DataEntryProfileHistoryEVO mDetails;
/*     */ 
/*     */   public DataEntryProfileHistoryDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public DataEntryProfileHistoryDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DataEntryProfileHistoryDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected DataEntryProfileHistoryPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(DataEntryProfileHistoryEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private DataEntryProfileHistoryEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  87 */     int col = 1;
/*  88 */     DataEntryProfileHistoryEVO evo = new DataEntryProfileHistoryEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  95 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(DataEntryProfileHistoryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 100 */     int col = startCol_;
/* 101 */     stmt_.setInt(col++, evo_.getUserId());
/* 102 */     stmt_.setInt(col++, evo_.getModelId());
/* 103 */     stmt_.setInt(col++, evo_.getBudgetLocationElementId());
			  stmt_.setInt(col++, evo_.getBudgetCycleId());
/* 104 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(DataEntryProfileHistoryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 109 */     int col = startCol_;
/* 110 */     stmt_.setInt(col++, evo_.getDataEntryProfileId());
/* 111 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(DataEntryProfileHistoryPK pk)
/*     */     throws ValidationException
/*     */   {
/* 129 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 131 */     PreparedStatement stmt = null;
/* 132 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 136 */       stmt = getConnection().prepareStatement("select DATA_ENTRY_PROFILE_HISTORY.USER_ID, DATA_ENTRY_PROFILE_HISTORY.MODEL_ID, DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID, DATA_ENTRY_PROFILE_HISTORY.BUDGET_CYCLE_ID, DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID from DATA_ENTRY_PROFILE_HISTORY where    USER_ID = ? AND MODEL_ID = ? AND BUDGET_LOCATION_ELEMENT_ID = ? AND BUDGET_CYCLE_ID = ? ");
/*     */ 
/* 139 */       int col = 1;
/* 140 */       stmt.setInt(col++, pk.getUserId());
/* 141 */       stmt.setInt(col++, pk.getModelId());
/* 142 */       stmt.setInt(col++, pk.getBudgetLocationElementId());
				stmt.setInt(col++, pk.getBudgetCycleId());
/*     */ 
/* 144 */       resultSet = stmt.executeQuery();
/*     */ 
/* 146 */       if (!resultSet.next()) {
/* 147 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 150 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 151 */       if (this.mDetails.isModified())
/* 152 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 156 */       throw handleSQLException(pk, "select DATA_ENTRY_PROFILE_HISTORY.USER_ID, DATA_ENTRY_PROFILE_HISTORY.MODEL_ID, DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID, DATA_ENTRY_PROFILE_HISTORY.BUDGET_CYCLE_ID, DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID from DATA_ENTRY_PROFILE_HISTORY where    USER_ID = ? AND MODEL_ID = ? AND BUDGET_LOCATION_ELEMENT_ID = ? AND BUDGET_CYCLE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 160 */       closeResultSet(resultSet);
/* 161 */       closeStatement(stmt);
/* 162 */       closeConnection();
/*     */ 
/* 164 */       if (timer != null)
/* 165 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 190 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 191 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 195 */       stmt = getConnection().prepareStatement("insert into DATA_ENTRY_PROFILE_HISTORY ( USER_ID,MODEL_ID,BUDGET_LOCATION_ELEMENT_ID,BUDGET_CYCLE_ID,DATA_ENTRY_PROFILE_ID) values ( ?,?,?,?,? )");
/*     */ 
/* 198 */       int col = 1;
/* 199 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 200 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 203 */       int resultCount = stmt.executeUpdate();
/* 204 */       if (resultCount != 1)
/*     */       {
/* 206 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 209 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 213 */       throw handleSQLException(this.mDetails.getPK(), "insert into DATA_ENTRY_PROFILE_HISTORY ( USER_ID,MODEL_ID,BUDGET_LOCATION_ELEMENT_ID,BUDGET_CYCLE_ID,DATA_ENTRY_PROFILE_ID) values ( ?,?,?,?,? )", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 217 */       closeStatement(stmt);
/* 218 */       closeConnection();
/*     */ 
/* 220 */       if (timer != null)
/* 221 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 243 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 247 */     PreparedStatement stmt = null;
/*     */ 
/* 249 */     boolean mainChanged = this.mDetails.isModified();
/* 250 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 253 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 255 */         stmt = getConnection().prepareStatement("update DATA_ENTRY_PROFILE_HISTORY set DATA_ENTRY_PROFILE_ID = ? where    USER_ID = ? AND MODEL_ID = ? AND BUDGET_LOCATION_ELEMENT_ID = ? AND BUDGET_CYCLE_ID = ?");
/*     */ 
/* 258 */         int col = 1;
/* 259 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 260 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 263 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 266 */         if (resultCount != 1) {
/* 267 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 270 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 279 */       throw handleSQLException(getPK(), "update DATA_ENTRY_PROFILE_HISTORY set DATA_ENTRY_PROFILE_ID = ? where    USER_ID = ? AND MODEL_ID = ? AND BUDGET_LOCATION_ELEMENT_ID = ? AND BUDGET_CYCLE_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 283 */       closeStatement(stmt);
/* 284 */       closeConnection();
/*     */ 
/* 286 */       if ((timer != null) && (
/* 287 */         (mainChanged) || (dependantChanged)))
/* 288 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllDataEntryProfileHistorysELO getAllDataEntryProfileHistorys()
/*     */   {
/* 331 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 332 */     PreparedStatement stmt = null;
/* 333 */     ResultSet resultSet = null;
/* 334 */     AllDataEntryProfileHistorysELO results = new AllDataEntryProfileHistorysELO();
/*     */     try
/*     */     {
/* 337 */       stmt = getConnection().prepareStatement(SQL_ALL_DATA_ENTRY_PROFILE_HISTORYS);
/* 338 */       int col = 1;
/* 339 */       resultSet = stmt.executeQuery();
/* 340 */       while (resultSet.next())
/*     */       {
/* 342 */         col = 2;
/*     */ 
/* 345 */         UserPK pkUser = new UserPK(resultSet.getInt(col++));
/*     */ 
/* 348 */         String textUser = resultSet.getString(col++);
/*     */ 
/* 350 */         DataEntryProfilePK pkDataEntryProfile = new DataEntryProfilePK(resultSet.getInt(col++));
/*     */ 
/* 353 */         String textDataEntryProfile = resultSet.getString(col++);
/*     */ 
/* 356 */         DataEntryProfileHistoryPK pkDataEntryProfileHistory = new DataEntryProfileHistoryPK(resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 361 */         String textDataEntryProfileHistory = "";
/*     */ 
/* 366 */         DataEntryProfileCK ckDataEntryProfile = new DataEntryProfileCK(pkUser, pkDataEntryProfile);
/*     */ 
/* 372 */         DataEntryProfileHistoryCK ckDataEntryProfileHistory = new DataEntryProfileHistoryCK(pkUser, pkDataEntryProfile, pkDataEntryProfileHistory);
/*     */ 
/* 379 */         UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
/*     */ 
/* 385 */         DataEntryProfileRefImpl erDataEntryProfile = new DataEntryProfileRefImpl(ckDataEntryProfile, textDataEntryProfile);
/*     */ 
/* 391 */         DataEntryProfileHistoryRefImpl erDataEntryProfileHistory = new DataEntryProfileHistoryRefImpl(ckDataEntryProfileHistory, textDataEntryProfileHistory);
/*     */ 
/* 398 */         results.add(erDataEntryProfileHistory, erDataEntryProfile, erUser);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 407 */       throw handleSQLException(SQL_ALL_DATA_ENTRY_PROFILE_HISTORYS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 411 */       closeResultSet(resultSet);
/* 412 */       closeStatement(stmt);
/* 413 */       closeConnection();
/*     */     }
/*     */ 
/* 416 */     if (timer != null) {
/* 417 */       timer.logDebug("getAllDataEntryProfileHistorys", " items=" + results.size());
/*     */     }
/*     */ 
/* 421 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 440 */     if (items == null) {
/* 441 */       return false;
/*     */     }
/* 443 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 444 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 446 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 451 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 452 */       while (iter2.hasNext())
/*     */       {
/* 454 */         this.mDetails = ((DataEntryProfileHistoryEVO)iter2.next());
/*     */ 
/* 457 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 459 */         somethingChanged = true;
/*     */ 
/* 462 */         if (deleteStmt == null) {
/* 463 */           deleteStmt = getConnection().prepareStatement("delete from DATA_ENTRY_PROFILE_HISTORY where    USER_ID = ? AND MODEL_ID = ? AND BUDGET_LOCATION_ELEMENT_ID = ? AND BUDGET_CYCLE_ID = ?");
/*     */         }
/*     */ 
/* 466 */         int col = 1;
/* 467 */         deleteStmt.setInt(col++, this.mDetails.getUserId());
/* 468 */         deleteStmt.setInt(col++, this.mDetails.getModelId());
/* 469 */         deleteStmt.setInt(col++, this.mDetails.getBudgetLocationElementId());
				  deleteStmt.setInt(col++, this.mDetails.getBudgetCycleId());
/*     */ 
/* 471 */         if (this._log.isDebugEnabled()) {
/* 472 */           this._log.debug("update", "DataEntryProfileHistory deleting UserId=" + this.mDetails.getUserId() + ",ModelId=" + this.mDetails.getModelId() + ",BudgetLocationElementId=" + this.mDetails.getBudgetLocationElementId()+ ",BudgetCycleId=" + this.mDetails.getBudgetCycleId());
/*     */         }
/*     */ 
/* 479 */         deleteStmt.addBatch();
/*     */ 
/* 482 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 487 */       if (deleteStmt != null)
/*     */       {
/* 489 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 491 */         deleteStmt.executeBatch();
/*     */ 
/* 493 */         if (timer2 != null) {
/* 494 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 498 */       Iterator iter1 = items.values().iterator();
/* 499 */       while (iter1.hasNext())
/*     */       {
/* 501 */         this.mDetails = ((DataEntryProfileHistoryEVO)iter1.next());
/*     */ 
/* 503 */         if (this.mDetails.insertPending())
/*     */         {
/* 505 */           somethingChanged = true;
/* 506 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 509 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 511 */         somethingChanged = true;
/* 512 */         doStore();
/*     */       }
/*     */ 
/* 523 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 527 */       throw handleSQLException("delete from DATA_ENTRY_PROFILE_HISTORY where    USER_ID = ? AND MODEL_ID = ? AND BUDGET_LOCATION_ELEMENT_ID = ? AND BUDGET_CYCLE_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 531 */       if (deleteStmt != null)
/*     */       {
/* 533 */         closeStatement(deleteStmt);
/* 534 */         closeConnection();
/*     */       }
/*     */ 
/* 537 */       this.mDetails = null;
/*     */ 
/* 539 */       if ((somethingChanged) && 
/* 540 */         (timer != null))
/* 541 */         timer.logDebug("update", "collection"); 
/* 541 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(UserPK entityPK, Collection owners, String dependants)
/*     */   {
/* 566 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 568 */     PreparedStatement stmt = null;
/* 569 */     ResultSet resultSet = null;
/*     */ 
/* 571 */     int itemCount = 0;
/*     */ 
/* 573 */     DataEntryProfileEVO owningEVO = null;
/* 574 */     Iterator ownersIter = owners.iterator();
/* 575 */     while (ownersIter.hasNext())
/*     */     {
/* 577 */       owningEVO = (DataEntryProfileEVO)ownersIter.next();
/* 578 */       owningEVO.setDataEntryProfilesHistoryAllItemsLoaded(true);
/*     */     }
/* 580 */     ownersIter = owners.iterator();
/* 581 */     owningEVO = (DataEntryProfileEVO)ownersIter.next();
/* 582 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 586 */       stmt = getConnection().prepareStatement("select DATA_ENTRY_PROFILE_HISTORY.USER_ID,DATA_ENTRY_PROFILE_HISTORY.MODEL_ID,DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID,DATA_ENTRY_PROFILE_HISTORY.BUDGET_CYCLE_ID,DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID from DATA_ENTRY_PROFILE_HISTORY,DATA_ENTRY_PROFILE where 1=1 and DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID = DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID and DATA_ENTRY_PROFILE.USER_ID = ? order by  DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID ,DATA_ENTRY_PROFILE_HISTORY.USER_ID ,DATA_ENTRY_PROFILE_HISTORY.MODEL_ID ,DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID");
/*     */ 
/* 588 */       int col = 1;
/* 589 */       stmt.setInt(col++, entityPK.getUserId());
/*     */ 
/* 591 */       resultSet = stmt.executeQuery();
/*     */ 
/* 594 */       while (resultSet.next())
/*     */       {
/* 596 */         itemCount++;
/* 597 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 602 */         while (this.mDetails.getDataEntryProfileId() != owningEVO.getDataEntryProfileId())
/*     */         {
/* 606 */           if (!ownersIter.hasNext())
/*     */           {
/* 608 */             this._log.debug("bulkGetAll", "can't find owning [DataEntryProfileId=" + this.mDetails.getDataEntryProfileId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 612 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 613 */             ownersIter = owners.iterator();
/* 614 */             while (ownersIter.hasNext())
/*     */             {
/* 616 */               owningEVO = (DataEntryProfileEVO)ownersIter.next();
/* 617 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 619 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 621 */           owningEVO = (DataEntryProfileEVO)ownersIter.next();
/*     */         }
/* 623 */         if (owningEVO.getDataEntryProfilesHistory() == null)
/*     */         {
/* 625 */           theseItems = new ArrayList();
/* 626 */           owningEVO.setDataEntryProfilesHistory(theseItems);
/* 627 */           owningEVO.setDataEntryProfilesHistoryAllItemsLoaded(true);
/*     */         }
/* 629 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 632 */       if (timer != null) {
/* 633 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 638 */       throw handleSQLException("select DATA_ENTRY_PROFILE_HISTORY.USER_ID,DATA_ENTRY_PROFILE_HISTORY.MODEL_ID,DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID,DATA_ENTRY_PROFILE_HISTORY.BUDGET_CYCLE_ID,DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID from DATA_ENTRY_PROFILE_HISTORY,DATA_ENTRY_PROFILE where 1=1 and DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID = DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID and DATA_ENTRY_PROFILE.USER_ID = ? order by  DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID ,DATA_ENTRY_PROFILE_HISTORY.USER_ID ,DATA_ENTRY_PROFILE_HISTORY.MODEL_ID ,DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 642 */       closeResultSet(resultSet);
/* 643 */       closeStatement(stmt);
/* 644 */       closeConnection();
/*     */ 
/* 646 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectDataEntryProfileId, String dependants, Collection currentList)
/*     */   {
/* 671 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 672 */     PreparedStatement stmt = null;
/* 673 */     ResultSet resultSet = null;
/*     */ 
/* 675 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 679 */       stmt = getConnection().prepareStatement("select DATA_ENTRY_PROFILE_HISTORY.USER_ID,DATA_ENTRY_PROFILE_HISTORY.MODEL_ID,DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID, DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID, DATA_ENTRY_PROFILE_HISTORY.BUDGET_CYCLE_ID from DATA_ENTRY_PROFILE_HISTORY where    DATA_ENTRY_PROFILE_ID = ? ");
/*     */
/* 681 */       int col = 1;
/* 682 */       stmt.setInt(col++, selectDataEntryProfileId);
/*     */ 
/* 684 */       resultSet = stmt.executeQuery();
/*     */ 
/* 686 */       while (resultSet.next())
/*     */       {
/* 688 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 691 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 694 */       if (currentList != null)
/*     */       {
/* 697 */         ListIterator iter = items.listIterator();
/* 698 */         DataEntryProfileHistoryEVO currentEVO = null;
/* 699 */         DataEntryProfileHistoryEVO newEVO = null;
/* 700 */         while (iter.hasNext())
/*     */         {
/* 702 */           newEVO = (DataEntryProfileHistoryEVO)iter.next();
/* 703 */           Iterator iter2 = currentList.iterator();
/* 704 */           while (iter2.hasNext())
/*     */           {
/* 706 */             currentEVO = (DataEntryProfileHistoryEVO)iter2.next();
/* 707 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 709 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 715 */         Iterator iter2 = currentList.iterator();
/* 716 */         while (iter2.hasNext())
/*     */         {
/* 718 */           currentEVO = (DataEntryProfileHistoryEVO)iter2.next();
/* 719 */           if (currentEVO.insertPending()) {
/* 720 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 724 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 728 */       throw handleSQLException("select DATA_ENTRY_PROFILE_HISTORY.USER_ID,DATA_ENTRY_PROFILE_HISTORY.MODEL_ID,DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID,DATA_ENTRY_PROFILE_HISTORY.BUDGET_CYCLE_ID,DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID from DATA_ENTRY_PROFILE_HISTORY where    DATA_ENTRY_PROFILE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 732 */       closeResultSet(resultSet);
/* 733 */       closeStatement(stmt);
/* 734 */       closeConnection();
/*     */ 
/* 736 */       if (timer != null) {
/* 737 */         timer.logDebug("getAll", " DataEntryProfileId=" + selectDataEntryProfileId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 742 */     return items;
/*     */   }
/*     */ 
/*     */   public DataEntryProfileHistoryEVO getDetails(UserCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 756 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 759 */     if (this.mDetails == null) {
/* 760 */       doLoad(((DataEntryProfileHistoryCK)paramCK).getDataEntryProfileHistoryPK());
/*     */     }
/* 762 */     else if (!this.mDetails.getPK().equals(((DataEntryProfileHistoryCK)paramCK).getDataEntryProfileHistoryPK())) {
/* 763 */       doLoad(((DataEntryProfileHistoryCK)paramCK).getDataEntryProfileHistoryPK());
/*     */     }
/*     */ 
/* 766 */     DataEntryProfileHistoryEVO details = new DataEntryProfileHistoryEVO();
/* 767 */     details = this.mDetails.deepClone();
/*     */ 
/* 769 */     if (timer != null) {
/* 770 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 772 */     return details;
/*     */   }
/*     */ 
/*     */   public DataEntryProfileHistoryEVO getDetails(UserCK paramCK, DataEntryProfileHistoryEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 778 */     DataEntryProfileHistoryEVO savedEVO = this.mDetails;
/* 779 */     this.mDetails = paramEVO;
/* 780 */     DataEntryProfileHistoryEVO newEVO = getDetails(paramCK, dependants);
/* 781 */     this.mDetails = savedEVO;
/* 782 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public DataEntryProfileHistoryEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 788 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 792 */     DataEntryProfileHistoryEVO details = this.mDetails.deepClone();
/*     */ 
/* 794 */     if (timer != null) {
/* 795 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 797 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 802 */     return "DataEntryProfileHistory";
/*     */   }
/*     */ 
/*     */   public DataEntryProfileHistoryRefImpl getRef(DataEntryProfileHistoryPK paramDataEntryProfileHistoryPK)
/*     */   {
/* 807 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 808 */     PreparedStatement stmt = null;
/* 809 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 812 */       stmt = getConnection().prepareStatement("select 0,USR.USER_ID,DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID from DATA_ENTRY_PROFILE_HISTORY,USR,DATA_ENTRY_PROFILE where 1=1 and DATA_ENTRY_PROFILE_HISTORY.USER_ID = ? and DATA_ENTRY_PROFILE_HISTORY.MODEL_ID = ? and DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID = ? and DATA_ENTRY_PROFILE_HISTORY.BUDGET_CYCLE_ID = ? and DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID = DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID and DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID = USR.DATA_ENTRY_PROFILE_ID");
/* 813 */       int col = 1;
/* 814 */       stmt.setInt(col++, paramDataEntryProfileHistoryPK.getUserId());
/* 815 */       stmt.setInt(col++, paramDataEntryProfileHistoryPK.getModelId());
/* 816 */       stmt.setInt(col++, paramDataEntryProfileHistoryPK.getBudgetLocationElementId());
				stmt.setInt(col++, paramDataEntryProfileHistoryPK.getBudgetCycleId());
/*     */ 
/* 818 */       resultSet = stmt.executeQuery();
/*     */ 
/* 820 */       if (!resultSet.next()) {
/* 821 */         throw new RuntimeException(getEntityName() + " getRef " + paramDataEntryProfileHistoryPK + " not found");
/*     */       }
/* 823 */       col = 2;
/* 824 */       UserPK newUserPK = new UserPK(resultSet.getInt(col++));
/*     */ 
/* 828 */       DataEntryProfilePK newDataEntryProfilePK = new DataEntryProfilePK(resultSet.getInt(col++));
/*     */ 
/* 832 */       String textDataEntryProfileHistory = "";
/* 833 */       DataEntryProfileHistoryCK ckDataEntryProfileHistory = new DataEntryProfileHistoryCK(newUserPK, newDataEntryProfilePK, paramDataEntryProfileHistoryPK);
/*     */ 
/* 839 */       DataEntryProfileHistoryRefImpl localDataEntryProfileHistoryRefImpl = new DataEntryProfileHistoryRefImpl(ckDataEntryProfileHistory, textDataEntryProfileHistory);
/*     */       return localDataEntryProfileHistoryRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 844 */       throw handleSQLException(paramDataEntryProfileHistoryPK, "select 0,USR.USER_ID,DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID from DATA_ENTRY_PROFILE_HISTORY,USR,DATA_ENTRY_PROFILE where 1=1 and DATA_ENTRY_PROFILE_HISTORY.USER_ID = ? and DATA_ENTRY_PROFILE_HISTORY.MODEL_ID = ? and DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID = ? and DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID = DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID and DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID = USR.DATA_ENTRY_PROFILE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 848 */       closeResultSet(resultSet);
/* 849 */       closeStatement(stmt);
/* 850 */       closeConnection();
/*     */ 
/* 852 */       if (timer != null)
/* 853 */         timer.logDebug("getRef", paramDataEntryProfileHistoryPK); 
/* 853 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.user.DataEntryProfileHistoryDAO
 * JD-Core Version:    0.6.0
 */