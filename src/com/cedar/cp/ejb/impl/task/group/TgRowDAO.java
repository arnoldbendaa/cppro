/*     */ package com.cedar.cp.ejb.impl.task.group;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.task.group.TaskGroupCK;
/*     */ import com.cedar.cp.dto.task.group.TaskGroupPK;
/*     */ import com.cedar.cp.dto.task.group.TgRowCK;
/*     */ import com.cedar.cp.dto.task.group.TgRowPK;
/*     */ import com.cedar.cp.dto.task.group.TgRowParamCK;
/*     */ import com.cedar.cp.dto.task.group.TgRowParamPK;
/*     */ import com.cedar.cp.dto.task.group.TgRowRefImpl;
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
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class TgRowDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select TG_ROW.TG_ROW_ID,TG_ROW.GROUP_ID,TG_ROW.TG_ROW_INDEX,TG_ROW.ROW_TYPE,TG_ROW.UPDATED_BY_USER_ID,TG_ROW.UPDATED_TIME,TG_ROW.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from TG_ROW where    TG_ROW_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into TG_ROW ( TG_ROW_ID,GROUP_ID,TG_ROW_INDEX,ROW_TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update TG_ROW set GROUP_ID = ?,TG_ROW_INDEX = ?,ROW_TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TG_ROW_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from TG_ROW where    TG_ROW_ID = ? ";
/* 470 */   private static String[][] SQL_DELETE_CHILDREN = { { "TG_ROW_PARAM", "delete from TG_ROW_PARAM where     TG_ROW_PARAM.TG_ROW_ID = ? " } };
/*     */ 
/* 479 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*     */ 
/* 483 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and TG_ROW.TG_ROW_ID = ?)";
/*     */   public static final String SQL_BULK_GET_ALL = " from TG_ROW where 1=1 and TG_ROW.GROUP_ID = ? order by  TG_ROW.TG_ROW_ID";
/*     */   protected static final String SQL_GET_ALL = " from TG_ROW where    GROUP_ID = ? ";
/*     */   protected TgRowParamDAO mTgRowParamDAO;
/*     */   protected TgRowEVO mDetails;
/*     */ 
/*     */   public TgRowDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public TgRowDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TgRowDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected TgRowPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(TgRowEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private TgRowEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  90 */     int col = 1;
/*  91 */     TgRowEVO evo = new TgRowEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), null);
/*     */ 
/*  99 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 100 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 101 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 102 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(TgRowEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 107 */     int col = startCol_;
/* 108 */     stmt_.setInt(col++, evo_.getTgRowId());
/* 109 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(TgRowEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 114 */     int col = startCol_;
/* 115 */     stmt_.setInt(col++, evo_.getGroupId());
/* 116 */     stmt_.setInt(col++, evo_.getTgRowIndex());
/* 117 */     stmt_.setInt(col++, evo_.getRowType());
/* 118 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 119 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 120 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 121 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(TgRowPK pk)
/*     */     throws ValidationException
/*     */   {
/* 137 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 139 */     PreparedStatement stmt = null;
/* 140 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 144 */       stmt = getConnection().prepareStatement("select TG_ROW.TG_ROW_ID,TG_ROW.GROUP_ID,TG_ROW.TG_ROW_INDEX,TG_ROW.ROW_TYPE,TG_ROW.UPDATED_BY_USER_ID,TG_ROW.UPDATED_TIME,TG_ROW.CREATED_TIME from TG_ROW where    TG_ROW_ID = ? ");
/*     */ 
/* 147 */       int col = 1;
/* 148 */       stmt.setInt(col++, pk.getTgRowId());
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
/* 162 */       throw handleSQLException(pk, "select TG_ROW.TG_ROW_ID,TG_ROW.GROUP_ID,TG_ROW.TG_ROW_INDEX,TG_ROW.ROW_TYPE,TG_ROW.UPDATED_BY_USER_ID,TG_ROW.UPDATED_TIME,TG_ROW.CREATED_TIME from TG_ROW where    TG_ROW_ID = ? ", sqle);
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
/* 203 */     this.mDetails.postCreateInit();
/*     */ 
/* 205 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 210 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 211 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 212 */       stmt = getConnection().prepareStatement("insert into TG_ROW ( TG_ROW_ID,GROUP_ID,TG_ROW_INDEX,ROW_TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*     */ 
/* 215 */       int col = 1;
/* 216 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 217 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 220 */       int resultCount = stmt.executeUpdate();
/* 221 */       if (resultCount != 1)
/*     */       {
/* 223 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 226 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 230 */       throw handleSQLException(this.mDetails.getPK(), "insert into TG_ROW ( TG_ROW_ID,GROUP_ID,TG_ROW_INDEX,ROW_TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 234 */       closeStatement(stmt);
/* 235 */       closeConnection();
/*     */ 
/* 237 */       if (timer != null) {
/* 238 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 244 */       getTgRowParamDAO().update(this.mDetails.getTGRowsParamsMap());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 250 */       throw new RuntimeException("unexpected exception", e);
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
/* 286 */       if (getTgRowParamDAO().update(this.mDetails.getTGRowsParamsMap())) {
/* 287 */         dependantChanged = true;
/*     */       }
/* 289 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 292 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 293 */         stmt = getConnection().prepareStatement("update TG_ROW set GROUP_ID = ?,TG_ROW_INDEX = ?,ROW_TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TG_ROW_ID = ? ");
/*     */ 
/* 296 */         int col = 1;
/* 297 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 298 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 301 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 304 */         if (resultCount != 1) {
/* 305 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 308 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 317 */       throw handleSQLException(getPK(), "update TG_ROW set GROUP_ID = ?,TG_ROW_INDEX = ?,ROW_TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TG_ROW_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 321 */       closeStatement(stmt);
/* 322 */       closeConnection();
/*     */ 
/* 324 */       if ((timer != null) && (
/* 325 */         (mainChanged) || (dependantChanged)))
/* 326 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 345 */     if (items == null) {
/* 346 */       return false;
/*     */     }
/* 348 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 349 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 351 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 355 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/* 356 */       while (iter3.hasNext())
/*     */       {
/* 358 */         this.mDetails = ((TgRowEVO)iter3.next());
/* 359 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 361 */         somethingChanged = true;
/*     */ 
/* 364 */         deleteDependants(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 368 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 369 */       while (iter2.hasNext())
/*     */       {
/* 371 */         this.mDetails = ((TgRowEVO)iter2.next());
/*     */ 
/* 374 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 376 */         somethingChanged = true;
/*     */ 
/* 379 */         if (deleteStmt == null) {
/* 380 */           deleteStmt = getConnection().prepareStatement("delete from TG_ROW where    TG_ROW_ID = ? ");
/*     */         }
/*     */ 
/* 383 */         int col = 1;
/* 384 */         deleteStmt.setInt(col++, this.mDetails.getTgRowId());
/*     */ 
/* 386 */         if (this._log.isDebugEnabled()) {
/* 387 */           this._log.debug("update", "TgRow deleting TgRowId=" + this.mDetails.getTgRowId());
/*     */         }
/*     */ 
/* 392 */         deleteStmt.addBatch();
/*     */ 
/* 395 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 400 */       if (deleteStmt != null)
/*     */       {
/* 402 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 404 */         deleteStmt.executeBatch();
/*     */ 
/* 406 */         if (timer2 != null) {
/* 407 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 411 */       Iterator iter1 = items.values().iterator();
/* 412 */       while (iter1.hasNext())
/*     */       {
/* 414 */         this.mDetails = ((TgRowEVO)iter1.next());
/*     */ 
/* 416 */         if (this.mDetails.insertPending())
/*     */         {
/* 418 */           somethingChanged = true;
/* 419 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 422 */         if (this.mDetails.isModified())
/*     */         {
/* 424 */           somethingChanged = true;
/* 425 */           doStore(); continue;
/*     */         }
/*     */ 
/* 429 */         if ((this.mDetails.deletePending()) || 
/* 435 */           (!getTgRowParamDAO().update(this.mDetails.getTGRowsParamsMap()))) continue;
/* 436 */         somethingChanged = true;
/*     */       }
/*     */ 
/* 448 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 452 */       throw handleSQLException("delete from TG_ROW where    TG_ROW_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 456 */       if (deleteStmt != null)
/*     */       {
/* 458 */         closeStatement(deleteStmt);
/* 459 */         closeConnection();
/*     */       }
/*     */ 
/* 462 */       this.mDetails = null;
/*     */ 
/* 464 */       if ((somethingChanged) && 
/* 465 */         (timer != null))
/* 466 */         timer.logDebug("update", "collection"); 
/* 466 */     }
/*     */   }
/*     */ 
/*     */   private void deleteDependants(TgRowPK pk)
/*     */   {
/* 492 */     Set emptyStrings = Collections.emptySet();
/* 493 */     deleteDependants(pk, emptyStrings);
/*     */   }
/*     */ 
/*     */   private void deleteDependants(TgRowPK pk, Set<String> exclusionTables)
/*     */   {
/* 499 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*     */     {
/* 501 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*     */         continue;
/* 503 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 505 */       PreparedStatement stmt = null;
/*     */ 
/* 507 */       int resultCount = 0;
/* 508 */       String s = null;
/*     */       try
/*     */       {
/* 511 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*     */ 
/* 513 */         if (this._log.isDebugEnabled()) {
/* 514 */           this._log.debug("deleteDependants", s);
/*     */         }
/* 516 */         stmt = getConnection().prepareStatement(s);
/*     */ 
/* 519 */         int col = 1;
/* 520 */         stmt.setInt(col++, pk.getTgRowId());
/*     */ 
/* 523 */         resultCount = stmt.executeUpdate();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 527 */         throw handleSQLException(pk, s, sqle);
/*     */       }
/*     */       finally
/*     */       {
/* 531 */         closeStatement(stmt);
/* 532 */         closeConnection();
/*     */ 
/* 534 */         if (timer != null) {
/* 535 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*     */         }
/*     */       }
/*     */     }
/* 539 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*     */     {
/* 541 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*     */         continue;
/* 543 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 545 */       PreparedStatement stmt = null;
/*     */ 
/* 547 */       int resultCount = 0;
/* 548 */       String s = null;
/*     */       try
/*     */       {
/* 551 */         s = SQL_DELETE_CHILDREN[i][1];
/*     */ 
/* 553 */         if (this._log.isDebugEnabled()) {
/* 554 */           this._log.debug("deleteDependants", s);
/*     */         }
/* 556 */         stmt = getConnection().prepareStatement(s);
/*     */ 
/* 559 */         int col = 1;
/* 560 */         stmt.setInt(col++, pk.getTgRowId());
/*     */ 
/* 563 */         resultCount = stmt.executeUpdate();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 567 */         throw handleSQLException(pk, s, sqle);
/*     */       }
/*     */       finally
/*     */       {
/* 571 */         closeStatement(stmt);
/* 572 */         closeConnection();
/*     */ 
/* 574 */         if (timer != null)
/* 575 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(TaskGroupPK entityPK, TaskGroupEVO owningEVO, String dependants)
/*     */   {
/* 595 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 597 */     PreparedStatement stmt = null;
/* 598 */     ResultSet resultSet = null;
/*     */ 
/* 600 */     int itemCount = 0;
/*     */ 
/* 602 */     Collection theseItems = new ArrayList();
/* 603 */     owningEVO.setTaskGroupRows(theseItems);
/* 604 */     owningEVO.setTaskGroupRowsAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 608 */       stmt = getConnection().prepareStatement("select TG_ROW.TG_ROW_ID,TG_ROW.GROUP_ID,TG_ROW.TG_ROW_INDEX,TG_ROW.ROW_TYPE,TG_ROW.UPDATED_BY_USER_ID,TG_ROW.UPDATED_TIME,TG_ROW.CREATED_TIME from TG_ROW where 1=1 and TG_ROW.GROUP_ID = ? order by  TG_ROW.TG_ROW_ID");
/*     */ 
/* 610 */       int col = 1;
/* 611 */       stmt.setInt(col++, entityPK.getGroupId());
/*     */ 
/* 613 */       resultSet = stmt.executeQuery();
/*     */ 
/* 616 */       while (resultSet.next())
/*     */       {
/* 618 */         itemCount++;
/* 619 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 621 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 624 */       if (timer != null) {
/* 625 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */ 
/* 628 */       if ((itemCount > 0) && (dependants.indexOf("<1>") > -1))
/*     */       {
/* 630 */         getTgRowParamDAO().bulkGetAll(entityPK, theseItems, dependants);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle) {
/* 634 */       throw handleSQLException("select TG_ROW.TG_ROW_ID,TG_ROW.GROUP_ID,TG_ROW.TG_ROW_INDEX,TG_ROW.ROW_TYPE,TG_ROW.UPDATED_BY_USER_ID,TG_ROW.UPDATED_TIME,TG_ROW.CREATED_TIME from TG_ROW where 1=1 and TG_ROW.GROUP_ID = ? order by  TG_ROW.TG_ROW_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 638 */       closeResultSet(resultSet);
/* 639 */       closeStatement(stmt);
/* 640 */       closeConnection();
/*     */ 
/* 642 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectGroupId, String dependants, Collection currentList)
/*     */   {
/* 667 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 668 */     PreparedStatement stmt = null;
/* 669 */     ResultSet resultSet = null;
/*     */ 
/* 671 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 675 */       stmt = getConnection().prepareStatement("select TG_ROW.TG_ROW_ID,TG_ROW.GROUP_ID,TG_ROW.TG_ROW_INDEX,TG_ROW.ROW_TYPE,TG_ROW.UPDATED_BY_USER_ID,TG_ROW.UPDATED_TIME,TG_ROW.CREATED_TIME from TG_ROW where    GROUP_ID = ? ");
/*     */ 
/* 677 */       int col = 1;
/* 678 */       stmt.setInt(col++, selectGroupId);
/*     */ 
/* 680 */       resultSet = stmt.executeQuery();
/*     */ 
/* 682 */       while (resultSet.next())
/*     */       {
/* 684 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 687 */         getDependants(this.mDetails, dependants);
/*     */ 
/* 690 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 693 */       if (currentList != null)
/*     */       {
/* 696 */         ListIterator iter = items.listIterator();
/* 697 */         TgRowEVO currentEVO = null;
/* 698 */         TgRowEVO newEVO = null;
/* 699 */         while (iter.hasNext())
/*     */         {
/* 701 */           newEVO = (TgRowEVO)iter.next();
/* 702 */           Iterator iter2 = currentList.iterator();
/* 703 */           while (iter2.hasNext())
/*     */           {
/* 705 */             currentEVO = (TgRowEVO)iter2.next();
/* 706 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 708 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 714 */         Iterator iter2 = currentList.iterator();
/* 715 */         while (iter2.hasNext())
/*     */         {
/* 717 */           currentEVO = (TgRowEVO)iter2.next();
/* 718 */           if (currentEVO.insertPending()) {
/* 719 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 723 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 727 */       throw handleSQLException("select TG_ROW.TG_ROW_ID,TG_ROW.GROUP_ID,TG_ROW.TG_ROW_INDEX,TG_ROW.ROW_TYPE,TG_ROW.UPDATED_BY_USER_ID,TG_ROW.UPDATED_TIME,TG_ROW.CREATED_TIME from TG_ROW where    GROUP_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 731 */       closeResultSet(resultSet);
/* 732 */       closeStatement(stmt);
/* 733 */       closeConnection();
/*     */ 
/* 735 */       if (timer != null) {
/* 736 */         timer.logDebug("getAll", " GroupId=" + selectGroupId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 741 */     return items;
/*     */   }
/*     */ 
/*     */   public TgRowEVO getDetails(TaskGroupCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 758 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 761 */     if (this.mDetails == null) {
/* 762 */       doLoad(((TgRowCK)paramCK).getTgRowPK());
/*     */     }
/* 764 */     else if (!this.mDetails.getPK().equals(((TgRowCK)paramCK).getTgRowPK())) {
/* 765 */       doLoad(((TgRowCK)paramCK).getTgRowPK());
/*     */     }
/*     */ 
/* 768 */     if ((dependants.indexOf("<1>") > -1) && (!this.mDetails.isTGRowsParamsAllItemsLoaded()))
/*     */     {
/* 773 */       this.mDetails.setTGRowsParams(getTgRowParamDAO().getAll(this.mDetails.getTgRowId(), dependants, this.mDetails.getTGRowsParams()));
/*     */ 
/* 780 */       this.mDetails.setTGRowsParamsAllItemsLoaded(true);
/*     */     }
/*     */ 
/* 783 */     if ((paramCK instanceof TgRowParamCK))
/*     */     {
/* 785 */       if (this.mDetails.getTGRowsParams() == null) {
/* 786 */         this.mDetails.loadTGRowsParamsItem(getTgRowParamDAO().getDetails(paramCK, dependants));
/*     */       }
/*     */       else {
/* 789 */         TgRowParamPK pk = ((TgRowParamCK)paramCK).getTgRowParamPK();
/* 790 */         TgRowParamEVO evo = this.mDetails.getTGRowsParamsItem(pk);
/* 791 */         if (evo == null) {
/* 792 */           this.mDetails.loadTGRowsParamsItem(getTgRowParamDAO().getDetails(paramCK, dependants));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 797 */     TgRowEVO details = new TgRowEVO();
/* 798 */     details = this.mDetails.deepClone();
/*     */ 
/* 800 */     if (timer != null) {
/* 801 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 803 */     return details;
/*     */   }
/*     */ 
/*     */   public TgRowEVO getDetails(TaskGroupCK paramCK, TgRowEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 809 */     TgRowEVO savedEVO = this.mDetails;
/* 810 */     this.mDetails = paramEVO;
/* 811 */     TgRowEVO newEVO = getDetails(paramCK, dependants);
/* 812 */     this.mDetails = savedEVO;
/* 813 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public TgRowEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 819 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 823 */     getDependants(this.mDetails, dependants);
/*     */ 
/* 826 */     TgRowEVO details = this.mDetails.deepClone();
/*     */ 
/* 828 */     if (timer != null) {
/* 829 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 831 */     return details;
/*     */   }
/*     */ 
/*     */   protected TgRowParamDAO getTgRowParamDAO()
/*     */   {
/* 840 */     if (this.mTgRowParamDAO == null)
/*     */     {
/* 842 */       if (this.mDataSource != null)
/* 843 */         this.mTgRowParamDAO = new TgRowParamDAO(this.mDataSource);
/*     */       else {
/* 845 */         this.mTgRowParamDAO = new TgRowParamDAO(getConnection());
/*     */       }
/*     */     }
/* 848 */     return this.mTgRowParamDAO;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 853 */     return "TgRow";
/*     */   }
/*     */ 
/*     */   public TgRowRefImpl getRef(TgRowPK paramTgRowPK)
/*     */   {
/* 858 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 859 */     PreparedStatement stmt = null;
/* 860 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 863 */       stmt = getConnection().prepareStatement("select 0,TASK_GROUP.GROUP_ID from TG_ROW,TASK_GROUP where 1=1 and TG_ROW.TG_ROW_ID = ? and TG_ROW.GROUP_ID = TASK_GROUP.GROUP_ID");
/* 864 */       int col = 1;
/* 865 */       stmt.setInt(col++, paramTgRowPK.getTgRowId());
/*     */ 
/* 867 */       resultSet = stmt.executeQuery();
/*     */ 
/* 869 */       if (!resultSet.next()) {
/* 870 */         throw new RuntimeException(getEntityName() + " getRef " + paramTgRowPK + " not found");
/*     */       }
/* 872 */       col = 2;
/* 873 */       TaskGroupPK newTaskGroupPK = new TaskGroupPK(resultSet.getInt(col++));
/*     */ 
/* 877 */       String textTgRow = "";
/* 878 */       TgRowCK ckTgRow = new TgRowCK(newTaskGroupPK, paramTgRowPK);
/*     */ 
/* 883 */       TgRowRefImpl localTgRowRefImpl = new TgRowRefImpl(ckTgRow, textTgRow);
/*     */       return localTgRowRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 888 */       throw handleSQLException(paramTgRowPK, "select 0,TASK_GROUP.GROUP_ID from TG_ROW,TASK_GROUP where 1=1 and TG_ROW.TG_ROW_ID = ? and TG_ROW.GROUP_ID = TASK_GROUP.GROUP_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 892 */       closeResultSet(resultSet);
/* 893 */       closeStatement(stmt);
/* 894 */       closeConnection();
/*     */ 
/* 896 */       if (timer != null)
/* 897 */         timer.logDebug("getRef", paramTgRowPK); 
/* 897 */     }
/*     */   }
/*     */ 
/*     */   public void getDependants(Collection c, String dependants)
/*     */   {
/* 909 */     if (c == null)
/* 910 */       return;
/* 911 */     Iterator iter = c.iterator();
/* 912 */     while (iter.hasNext())
/*     */     {
/* 914 */       TgRowEVO evo = (TgRowEVO)iter.next();
/* 915 */       getDependants(evo, dependants);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void getDependants(TgRowEVO evo, String dependants)
/*     */   {
/* 929 */     if (evo.insertPending()) {
/* 930 */       return;
/*     */     }
/* 932 */     if (evo.getTgRowId() < 1) {
/* 933 */       return;
/*     */     }
/*     */ 
/* 937 */     if (dependants.indexOf("<1>") > -1)
/*     */     {
/* 940 */       if (!evo.isTGRowsParamsAllItemsLoaded())
/*     */       {
/* 942 */         evo.setTGRowsParams(getTgRowParamDAO().getAll(evo.getTgRowId(), dependants, evo.getTGRowsParams()));
/*     */ 
/* 949 */         evo.setTGRowsParamsAllItemsLoaded(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.task.group.TgRowDAO
 * JD-Core Version:    0.6.0
 */