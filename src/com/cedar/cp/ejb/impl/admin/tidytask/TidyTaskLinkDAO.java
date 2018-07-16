/*     */ package com.cedar.cp.ejb.impl.admin.tidytask;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.admin.tidytask.OrderedChildrenELO;
/*     */ import com.cedar.cp.dto.admin.tidytask.TidyTaskCK;
/*     */ import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkCK;
/*     */ import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkPK;
/*     */ import com.cedar.cp.dto.admin.tidytask.TidyTaskLinkRefImpl;
/*     */ import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
/*     */ import com.cedar.cp.dto.admin.tidytask.TidyTaskRefImpl;
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
/*     */ public class TidyTaskLinkDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from TIDY_TASK_LINK where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into TIDY_TASK_LINK ( TIDY_TASK_ID,TIDY_TASK_LINK_ID,SEQ,TYPE,CMD,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update TIDY_TASK_LINK set SEQ = ?,TYPE = ?,CMD = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ";
/* 323 */   protected static String SQL_ORDERED_CHILDREN = "select 0       ,TIDY_TASK.TIDY_TASK_ID      ,TIDY_TASK.VIS_ID      ,TIDY_TASK_LINK.TIDY_TASK_ID      ,TIDY_TASK_LINK.TIDY_TASK_LINK_ID      ,TIDY_TASK_LINK.TYPE      ,TIDY_TASK_LINK.CMD from TIDY_TASK_LINK    ,TIDY_TASK where 1=1   and TIDY_TASK_LINK.TIDY_TASK_ID = TIDY_TASK.TIDY_TASK_ID  and  TIDY_TASK_LINK.TIDY_TASK_ID = ? order by SEQ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from TIDY_TASK_LINK where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from TIDY_TASK_LINK where 1=1 and TIDY_TASK_LINK.TIDY_TASK_ID = ? order by  TIDY_TASK_LINK.TIDY_TASK_ID ,TIDY_TASK_LINK.TIDY_TASK_LINK_ID";
/*     */   protected static final String SQL_GET_ALL = " from TIDY_TASK_LINK where    TIDY_TASK_ID = ? ";
/*     */   protected TidyTaskLinkEVO mDetails;
/*     */ 
/*     */   public TidyTaskLinkDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public TidyTaskLinkDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TidyTaskLinkDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected TidyTaskLinkPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(TidyTaskLinkEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private TidyTaskLinkEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  91 */     int col = 1;
/*  92 */     TidyTaskLinkEVO evo = new TidyTaskLinkEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++));
/*     */ 
/* 100 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 101 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 102 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 103 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(TidyTaskLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 108 */     int col = startCol_;
/* 109 */     stmt_.setInt(col++, evo_.getTidyTaskId());
/* 110 */     stmt_.setInt(col++, evo_.getTidyTaskLinkId());
/* 111 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(TidyTaskLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 116 */     int col = startCol_;
/* 117 */     stmt_.setInt(col++, evo_.getSeq());
/* 118 */     stmt_.setInt(col++, evo_.getType());
/* 119 */     stmt_.setString(col++, evo_.getCmd());
/* 120 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 121 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 122 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 123 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(TidyTaskLinkPK pk)
/*     */     throws ValidationException
/*     */   {
/* 140 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 142 */     PreparedStatement stmt = null;
/* 143 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 147 */       stmt = getConnection().prepareStatement("select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME from TIDY_TASK_LINK where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ");
/*     */ 
/* 150 */       int col = 1;
/* 151 */       stmt.setInt(col++, pk.getTidyTaskId());
/* 152 */       stmt.setInt(col++, pk.getTidyTaskLinkId());
/*     */ 
/* 154 */       resultSet = stmt.executeQuery();
/*     */ 
/* 156 */       if (!resultSet.next()) {
/* 157 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 160 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 161 */       if (this.mDetails.isModified())
/* 162 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 166 */       throw handleSQLException(pk, "select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME from TIDY_TASK_LINK where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 170 */       closeResultSet(resultSet);
/* 171 */       closeStatement(stmt);
/* 172 */       closeConnection();
/*     */ 
/* 174 */       if (timer != null)
/* 175 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 208 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 209 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 214 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 215 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 216 */       stmt = getConnection().prepareStatement("insert into TIDY_TASK_LINK ( TIDY_TASK_ID,TIDY_TASK_LINK_ID,SEQ,TYPE,CMD,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");
/*     */ 
/* 219 */       int col = 1;
/* 220 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 221 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 224 */       int resultCount = stmt.executeUpdate();
/* 225 */       if (resultCount != 1)
/*     */       {
/* 227 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 230 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 234 */       throw handleSQLException(this.mDetails.getPK(), "insert into TIDY_TASK_LINK ( TIDY_TASK_ID,TIDY_TASK_LINK_ID,SEQ,TYPE,CMD,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 238 */       closeStatement(stmt);
/* 239 */       closeConnection();
/*     */ 
/* 241 */       if (timer != null)
/* 242 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 268 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 272 */     PreparedStatement stmt = null;
/*     */ 
/* 274 */     boolean mainChanged = this.mDetails.isModified();
/* 275 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 278 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 281 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 282 */         stmt = getConnection().prepareStatement("update TIDY_TASK_LINK set SEQ = ?,TYPE = ?,CMD = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ");
/*     */ 
/* 285 */         int col = 1;
/* 286 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 287 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 290 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 293 */         if (resultCount != 1) {
/* 294 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 297 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 306 */       throw handleSQLException(getPK(), "update TIDY_TASK_LINK set SEQ = ?,TYPE = ?,CMD = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 310 */       closeStatement(stmt);
/* 311 */       closeConnection();
/*     */ 
/* 313 */       if ((timer != null) && (
/* 314 */         (mainChanged) || (dependantChanged)))
/* 315 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public OrderedChildrenELO getOrderedChildren(int param1)
/*     */   {
/* 355 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 356 */     PreparedStatement stmt = null;
/* 357 */     ResultSet resultSet = null;
/* 358 */     OrderedChildrenELO results = new OrderedChildrenELO();
/*     */     try
/*     */     {
/* 361 */       stmt = getConnection().prepareStatement(SQL_ORDERED_CHILDREN);
/* 362 */       int col = 1;
/* 363 */       stmt.setInt(col++, param1);
/* 364 */       resultSet = stmt.executeQuery();
/* 365 */       while (resultSet.next())
/*     */       {
/* 367 */         col = 2;
/*     */ 
/* 370 */         TidyTaskPK pkTidyTask = new TidyTaskPK(resultSet.getInt(col++));
/*     */ 
/* 373 */         String textTidyTask = resultSet.getString(col++);
/*     */ 
/* 376 */         TidyTaskLinkPK pkTidyTaskLink = new TidyTaskLinkPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 380 */         String textTidyTaskLink = "";
/*     */ 
/* 385 */         TidyTaskLinkCK ckTidyTaskLink = new TidyTaskLinkCK(pkTidyTask, pkTidyTaskLink);
/*     */ 
/* 391 */         TidyTaskRefImpl erTidyTask = new TidyTaskRefImpl(pkTidyTask, textTidyTask);
/*     */ 
/* 397 */         TidyTaskLinkRefImpl erTidyTaskLink = new TidyTaskLinkRefImpl(ckTidyTaskLink, textTidyTaskLink);
/*     */ 
/* 402 */         int col1 = resultSet.getInt(col++);
/* 403 */         String col2 = resultSet.getString(col++);
/*     */ 
/* 406 */         results.add(erTidyTaskLink, erTidyTask, col1, col2);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 416 */       throw handleSQLException(SQL_ORDERED_CHILDREN, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 420 */       closeResultSet(resultSet);
/* 421 */       closeStatement(stmt);
/* 422 */       closeConnection();
/*     */     }
/*     */ 
/* 425 */     if (timer != null) {
/* 426 */       timer.logDebug("getOrderedChildren", " TidyTaskId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 431 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 449 */     if (items == null) {
/* 450 */       return false;
/*     */     }
/* 452 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 453 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 455 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 460 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 461 */       while (iter2.hasNext())
/*     */       {
/* 463 */         this.mDetails = ((TidyTaskLinkEVO)iter2.next());
/*     */ 
/* 466 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 468 */         somethingChanged = true;
/*     */ 
/* 471 */         if (deleteStmt == null) {
/* 472 */           deleteStmt = getConnection().prepareStatement("delete from TIDY_TASK_LINK where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ");
/*     */         }
/*     */ 
/* 475 */         int col = 1;
/* 476 */         deleteStmt.setInt(col++, this.mDetails.getTidyTaskId());
/* 477 */         deleteStmt.setInt(col++, this.mDetails.getTidyTaskLinkId());
/*     */ 
/* 479 */         if (this._log.isDebugEnabled()) {
/* 480 */           this._log.debug("update", "TidyTaskLink deleting TidyTaskId=" + this.mDetails.getTidyTaskId() + ",TidyTaskLinkId=" + this.mDetails.getTidyTaskLinkId());
/*     */         }
/*     */ 
/* 486 */         deleteStmt.addBatch();
/*     */ 
/* 489 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 494 */       if (deleteStmt != null)
/*     */       {
/* 496 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 498 */         deleteStmt.executeBatch();
/*     */ 
/* 500 */         if (timer2 != null) {
/* 501 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 505 */       Iterator iter1 = items.values().iterator();
/* 506 */       while (iter1.hasNext())
/*     */       {
/* 508 */         this.mDetails = ((TidyTaskLinkEVO)iter1.next());
/*     */ 
/* 510 */         if (this.mDetails.insertPending())
/*     */         {
/* 512 */           somethingChanged = true;
/* 513 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 516 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 518 */         somethingChanged = true;
/* 519 */         doStore();
/*     */       }
/*     */ 
/* 530 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 534 */       throw handleSQLException("delete from TIDY_TASK_LINK where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 538 */       if (deleteStmt != null)
/*     */       {
/* 540 */         closeStatement(deleteStmt);
/* 541 */         closeConnection();
/*     */       }
/*     */ 
/* 544 */       this.mDetails = null;
/*     */ 
/* 546 */       if ((somethingChanged) && 
/* 547 */         (timer != null))
/* 548 */         timer.logDebug("update", "collection"); 
/* 548 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(TidyTaskPK entityPK, TidyTaskEVO owningEVO, String dependants)
/*     */   {
/* 568 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 570 */     PreparedStatement stmt = null;
/* 571 */     ResultSet resultSet = null;
/*     */ 
/* 573 */     int itemCount = 0;
/*     */ 
/* 575 */     Collection theseItems = new ArrayList();
/* 576 */     owningEVO.setTidyTasksEvents(theseItems);
/* 577 */     owningEVO.setTidyTasksEventsAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 581 */       stmt = getConnection().prepareStatement("select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME from TIDY_TASK_LINK where 1=1 and TIDY_TASK_LINK.TIDY_TASK_ID = ? order by  TIDY_TASK_LINK.TIDY_TASK_ID ,TIDY_TASK_LINK.TIDY_TASK_LINK_ID");
/*     */ 
/* 583 */       int col = 1;
/* 584 */       stmt.setInt(col++, entityPK.getTidyTaskId());
/*     */ 
/* 586 */       resultSet = stmt.executeQuery();
/*     */ 
/* 589 */       while (resultSet.next())
/*     */       {
/* 591 */         itemCount++;
/* 592 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 594 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 597 */       if (timer != null) {
/* 598 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 603 */       throw handleSQLException("select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME from TIDY_TASK_LINK where 1=1 and TIDY_TASK_LINK.TIDY_TASK_ID = ? order by  TIDY_TASK_LINK.TIDY_TASK_ID ,TIDY_TASK_LINK.TIDY_TASK_LINK_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 607 */       closeResultSet(resultSet);
/* 608 */       closeStatement(stmt);
/* 609 */       closeConnection();
/*     */ 
/* 611 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectTidyTaskId, String dependants, Collection currentList)
/*     */   {
/* 636 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 637 */     PreparedStatement stmt = null;
/* 638 */     ResultSet resultSet = null;
/*     */ 
/* 640 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 644 */       stmt = getConnection().prepareStatement("select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME from TIDY_TASK_LINK where    TIDY_TASK_ID = ? ");
/*     */ 
/* 646 */       int col = 1;
/* 647 */       stmt.setInt(col++, selectTidyTaskId);
/*     */ 
/* 649 */       resultSet = stmt.executeQuery();
/*     */ 
/* 651 */       while (resultSet.next())
/*     */       {
/* 653 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 656 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 659 */       if (currentList != null)
/*     */       {
/* 662 */         ListIterator iter = items.listIterator();
/* 663 */         TidyTaskLinkEVO currentEVO = null;
/* 664 */         TidyTaskLinkEVO newEVO = null;
/* 665 */         while (iter.hasNext())
/*     */         {
/* 667 */           newEVO = (TidyTaskLinkEVO)iter.next();
/* 668 */           Iterator iter2 = currentList.iterator();
/* 669 */           while (iter2.hasNext())
/*     */           {
/* 671 */             currentEVO = (TidyTaskLinkEVO)iter2.next();
/* 672 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 674 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 680 */         Iterator iter2 = currentList.iterator();
/* 681 */         while (iter2.hasNext())
/*     */         {
/* 683 */           currentEVO = (TidyTaskLinkEVO)iter2.next();
/* 684 */           if (currentEVO.insertPending()) {
/* 685 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 689 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 693 */       throw handleSQLException("select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME from TIDY_TASK_LINK where    TIDY_TASK_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 697 */       closeResultSet(resultSet);
/* 698 */       closeStatement(stmt);
/* 699 */       closeConnection();
/*     */ 
/* 701 */       if (timer != null) {
/* 702 */         timer.logDebug("getAll", " TidyTaskId=" + selectTidyTaskId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 707 */     return items;
/*     */   }
/*     */ 
/*     */   public TidyTaskLinkEVO getDetails(TidyTaskCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 721 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 724 */     if (this.mDetails == null) {
/* 725 */       doLoad(((TidyTaskLinkCK)paramCK).getTidyTaskLinkPK());
/*     */     }
/* 727 */     else if (!this.mDetails.getPK().equals(((TidyTaskLinkCK)paramCK).getTidyTaskLinkPK())) {
/* 728 */       doLoad(((TidyTaskLinkCK)paramCK).getTidyTaskLinkPK());
/*     */     }
/*     */ 
/* 731 */     TidyTaskLinkEVO details = new TidyTaskLinkEVO();
/* 732 */     details = this.mDetails.deepClone();
/*     */ 
/* 734 */     if (timer != null) {
/* 735 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 737 */     return details;
/*     */   }
/*     */ 
/*     */   public TidyTaskLinkEVO getDetails(TidyTaskCK paramCK, TidyTaskLinkEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 743 */     TidyTaskLinkEVO savedEVO = this.mDetails;
/* 744 */     this.mDetails = paramEVO;
/* 745 */     TidyTaskLinkEVO newEVO = getDetails(paramCK, dependants);
/* 746 */     this.mDetails = savedEVO;
/* 747 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public TidyTaskLinkEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 753 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 757 */     TidyTaskLinkEVO details = this.mDetails.deepClone();
/*     */ 
/* 759 */     if (timer != null) {
/* 760 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 762 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 767 */     return "TidyTaskLink";
/*     */   }
/*     */ 
/*     */   public TidyTaskLinkRefImpl getRef(TidyTaskLinkPK paramTidyTaskLinkPK)
/*     */   {
/* 772 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 773 */     PreparedStatement stmt = null;
/* 774 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 777 */       stmt = getConnection().prepareStatement("select 0,TIDY_TASK.TIDY_TASK_ID from TIDY_TASK_LINK,TIDY_TASK where 1=1 and TIDY_TASK_LINK.TIDY_TASK_ID = ? and TIDY_TASK_LINK.TIDY_TASK_LINK_ID = ? and TIDY_TASK_LINK.TIDY_TASK_ID = TIDY_TASK.TIDY_TASK_ID");
/* 778 */       int col = 1;
/* 779 */       stmt.setInt(col++, paramTidyTaskLinkPK.getTidyTaskId());
/* 780 */       stmt.setInt(col++, paramTidyTaskLinkPK.getTidyTaskLinkId());
/*     */ 
/* 782 */       resultSet = stmt.executeQuery();
/*     */ 
/* 784 */       if (!resultSet.next()) {
/* 785 */         throw new RuntimeException(getEntityName() + " getRef " + paramTidyTaskLinkPK + " not found");
/*     */       }
/* 787 */       col = 2;
/* 788 */       TidyTaskPK newTidyTaskPK = new TidyTaskPK(resultSet.getInt(col++));
/*     */ 
/* 792 */       String textTidyTaskLink = "";
/* 793 */       TidyTaskLinkCK ckTidyTaskLink = new TidyTaskLinkCK(newTidyTaskPK, paramTidyTaskLinkPK);
/*     */ 
/* 798 */       TidyTaskLinkRefImpl localTidyTaskLinkRefImpl = new TidyTaskLinkRefImpl(ckTidyTaskLink, textTidyTaskLink);
/*     */       return localTidyTaskLinkRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 803 */       throw handleSQLException(paramTidyTaskLinkPK, "select 0,TIDY_TASK.TIDY_TASK_ID from TIDY_TASK_LINK,TIDY_TASK where 1=1 and TIDY_TASK_LINK.TIDY_TASK_ID = ? and TIDY_TASK_LINK.TIDY_TASK_LINK_ID = ? and TIDY_TASK_LINK.TIDY_TASK_ID = TIDY_TASK.TIDY_TASK_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 807 */       closeResultSet(resultSet);
/* 808 */       closeStatement(stmt);
/* 809 */       closeConnection();
/*     */ 
/* 811 */       if (timer != null)
/* 812 */         timer.logDebug("getRef", paramTidyTaskLinkPK); 
/* 812 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskLinkDAO
 * JD-Core Version:    0.6.0
 */