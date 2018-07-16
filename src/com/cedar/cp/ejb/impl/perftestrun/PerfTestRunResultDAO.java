/*     */ package com.cedar.cp.ejb.impl.perftestrun;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.perftestrun.AllPerfTestRunResultsELO;
/*     */ import com.cedar.cp.dto.perftestrun.PerfTestRunCK;
/*     */ import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
/*     */ import com.cedar.cp.dto.perftestrun.PerfTestRunResultCK;
/*     */ import com.cedar.cp.dto.perftestrun.PerfTestRunResultPK;
/*     */ import com.cedar.cp.dto.perftestrun.PerfTestRunResultRefImpl;
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
/*     */ public class PerfTestRunResultDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select PERF_TEST_RUN_RESULT.PERF_TEST_RUN_RESULT_ID,PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID,PERF_TEST_RUN_RESULT.PERF_TEST_ID,PERF_TEST_RUN_RESULT.EXECUTION_TIME";
/*     */   protected static final String SQL_LOAD = " from PERF_TEST_RUN_RESULT where    PERF_TEST_RUN_RESULT_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into PERF_TEST_RUN_RESULT ( PERF_TEST_RUN_RESULT_ID,PERF_TEST_RUN_ID,PERF_TEST_ID,EXECUTION_TIME) values ( ?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update PERF_TEST_RUN_RESULT set PERF_TEST_RUN_ID = ?,PERF_TEST_ID = ?,EXECUTION_TIME = ? where    PERF_TEST_RUN_RESULT_ID = ? ";
/* 292 */   protected static String SQL_ALL_PERF_TEST_RUN_RESULTS = "select 0       ,PERF_TEST_RUN_RESULT.PERF_TEST_ID      ,PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID      ,PERF_TEST_RUN_RESULT.EXECUTION_TIME from PERF_TEST_RUN_RESULT    ,PERF_TEST_RUN where 1=1   and PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID = PERF_TEST_RUN.PERF_TEST_RUN_ID  order by PERF_TEST_ID, PERF_TEST_RUN_ID";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from PERF_TEST_RUN_RESULT where    PERF_TEST_RUN_RESULT_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from PERF_TEST_RUN_RESULT where 1=1 and PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID = ? order by  PERF_TEST_RUN_RESULT.PERF_TEST_RUN_RESULT_ID";
/*     */   protected static final String SQL_GET_ALL = " from PERF_TEST_RUN_RESULT where    PERF_TEST_RUN_ID = ? ";
/*     */   protected PerfTestRunResultEVO mDetails;
/*     */ 
/*     */   public PerfTestRunResultDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public PerfTestRunResultDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PerfTestRunResultDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected PerfTestRunResultPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(PerfTestRunResultEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private PerfTestRunResultEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  87 */     int col = 1;
/*  88 */     PerfTestRunResultEVO evo = new PerfTestRunResultEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getLong(col++));
/*     */ 
/*  95 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(PerfTestRunResultEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 100 */     int col = startCol_;
/* 101 */     stmt_.setInt(col++, evo_.getPerfTestRunResultId());
/* 102 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(PerfTestRunResultEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 107 */     int col = startCol_;
/* 108 */     stmt_.setInt(col++, evo_.getPerfTestRunId());
/* 109 */     stmt_.setInt(col++, evo_.getPerfTestId());
/* 110 */     stmt_.setLong(col++, evo_.getExecutionTime());
/* 111 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(PerfTestRunResultPK pk)
/*     */     throws ValidationException
/*     */   {
/* 127 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 129 */     PreparedStatement stmt = null;
/* 130 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 134 */       stmt = getConnection().prepareStatement("select PERF_TEST_RUN_RESULT.PERF_TEST_RUN_RESULT_ID,PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID,PERF_TEST_RUN_RESULT.PERF_TEST_ID,PERF_TEST_RUN_RESULT.EXECUTION_TIME from PERF_TEST_RUN_RESULT where    PERF_TEST_RUN_RESULT_ID = ? ");
/*     */ 
/* 137 */       int col = 1;
/* 138 */       stmt.setInt(col++, pk.getPerfTestRunResultId());
/*     */ 
/* 140 */       resultSet = stmt.executeQuery();
/*     */ 
/* 142 */       if (!resultSet.next()) {
/* 143 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 146 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 147 */       if (this.mDetails.isModified())
/* 148 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 152 */       throw handleSQLException(pk, "select PERF_TEST_RUN_RESULT.PERF_TEST_RUN_RESULT_ID,PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID,PERF_TEST_RUN_RESULT.PERF_TEST_ID,PERF_TEST_RUN_RESULT.EXECUTION_TIME from PERF_TEST_RUN_RESULT where    PERF_TEST_RUN_RESULT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 156 */       closeResultSet(resultSet);
/* 157 */       closeStatement(stmt);
/* 158 */       closeConnection();
/*     */ 
/* 160 */       if (timer != null)
/* 161 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 186 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 187 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 191 */       stmt = getConnection().prepareStatement("insert into PERF_TEST_RUN_RESULT ( PERF_TEST_RUN_RESULT_ID,PERF_TEST_RUN_ID,PERF_TEST_ID,EXECUTION_TIME) values ( ?,?,?,?)");
/*     */ 
/* 194 */       int col = 1;
/* 195 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 196 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 199 */       int resultCount = stmt.executeUpdate();
/* 200 */       if (resultCount != 1)
/*     */       {
/* 202 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 205 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 209 */       throw handleSQLException(this.mDetails.getPK(), "insert into PERF_TEST_RUN_RESULT ( PERF_TEST_RUN_RESULT_ID,PERF_TEST_RUN_ID,PERF_TEST_ID,EXECUTION_TIME) values ( ?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 213 */       closeStatement(stmt);
/* 214 */       closeConnection();
/*     */ 
/* 216 */       if (timer != null)
/* 217 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 239 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 243 */     PreparedStatement stmt = null;
/*     */ 
/* 245 */     boolean mainChanged = this.mDetails.isModified();
/* 246 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 249 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 251 */         stmt = getConnection().prepareStatement("update PERF_TEST_RUN_RESULT set PERF_TEST_RUN_ID = ?,PERF_TEST_ID = ?,EXECUTION_TIME = ? where    PERF_TEST_RUN_RESULT_ID = ? ");
/*     */ 
/* 254 */         int col = 1;
/* 255 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 256 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 259 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 262 */         if (resultCount != 1) {
/* 263 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 266 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 275 */       throw handleSQLException(getPK(), "update PERF_TEST_RUN_RESULT set PERF_TEST_RUN_ID = ?,PERF_TEST_ID = ?,EXECUTION_TIME = ? where    PERF_TEST_RUN_RESULT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 279 */       closeStatement(stmt);
/* 280 */       closeConnection();
/*     */ 
/* 282 */       if ((timer != null) && (
/* 283 */         (mainChanged) || (dependantChanged)))
/* 284 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllPerfTestRunResultsELO getAllPerfTestRunResults()
/*     */   {
/* 317 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 318 */     PreparedStatement stmt = null;
/* 319 */     ResultSet resultSet = null;
/* 320 */     AllPerfTestRunResultsELO results = new AllPerfTestRunResultsELO();
/*     */     try
/*     */     {
/* 323 */       stmt = getConnection().prepareStatement(SQL_ALL_PERF_TEST_RUN_RESULTS);
/* 324 */       int col = 1;
/* 325 */       resultSet = stmt.executeQuery();
/* 326 */       while (resultSet.next())
/*     */       {
/* 328 */         col = 2;
/*     */ 
/* 331 */         results.add(resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getLong(col++));
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 340 */       throw handleSQLException(SQL_ALL_PERF_TEST_RUN_RESULTS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 344 */       closeResultSet(resultSet);
/* 345 */       closeStatement(stmt);
/* 346 */       closeConnection();
/*     */     }
/*     */ 
/* 349 */     if (timer != null) {
/* 350 */       timer.logDebug("getAllPerfTestRunResults", " items=" + results.size());
/*     */     }
/*     */ 
/* 354 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 371 */     if (items == null) {
/* 372 */       return false;
/*     */     }
/* 374 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 375 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 377 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 382 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 383 */       while (iter2.hasNext())
/*     */       {
/* 385 */         this.mDetails = ((PerfTestRunResultEVO)iter2.next());
/*     */ 
/* 388 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 390 */         somethingChanged = true;
/*     */ 
/* 393 */         if (deleteStmt == null) {
/* 394 */           deleteStmt = getConnection().prepareStatement("delete from PERF_TEST_RUN_RESULT where    PERF_TEST_RUN_RESULT_ID = ? ");
/*     */         }
/*     */ 
/* 397 */         int col = 1;
/* 398 */         deleteStmt.setInt(col++, this.mDetails.getPerfTestRunResultId());
/*     */ 
/* 400 */         if (this._log.isDebugEnabled()) {
/* 401 */           this._log.debug("update", "PerfTestRunResult deleting PerfTestRunResultId=" + this.mDetails.getPerfTestRunResultId());
/*     */         }
/*     */ 
/* 406 */         deleteStmt.addBatch();
/*     */ 
/* 409 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 414 */       if (deleteStmt != null)
/*     */       {
/* 416 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 418 */         deleteStmt.executeBatch();
/*     */ 
/* 420 */         if (timer2 != null) {
/* 421 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 425 */       Iterator iter1 = items.values().iterator();
/* 426 */       while (iter1.hasNext())
/*     */       {
/* 428 */         this.mDetails = ((PerfTestRunResultEVO)iter1.next());
/*     */ 
/* 430 */         if (this.mDetails.insertPending())
/*     */         {
/* 432 */           somethingChanged = true;
/* 433 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 436 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 438 */         somethingChanged = true;
/* 439 */         doStore();
/*     */       }
/*     */ 
/* 450 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 454 */       throw handleSQLException("delete from PERF_TEST_RUN_RESULT where    PERF_TEST_RUN_RESULT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 458 */       if (deleteStmt != null)
/*     */       {
/* 460 */         closeStatement(deleteStmt);
/* 461 */         closeConnection();
/*     */       }
/*     */ 
/* 464 */       this.mDetails = null;
/*     */ 
/* 466 */       if ((somethingChanged) && 
/* 467 */         (timer != null))
/* 468 */         timer.logDebug("update", "collection"); 
/* 468 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(PerfTestRunPK entityPK, PerfTestRunEVO owningEVO, String dependants)
/*     */   {
/* 487 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 489 */     PreparedStatement stmt = null;
/* 490 */     ResultSet resultSet = null;
/*     */ 
/* 492 */     int itemCount = 0;
/*     */ 
/* 494 */     Collection theseItems = new ArrayList();
/* 495 */     owningEVO.setPerfTestRunResults(theseItems);
/* 496 */     owningEVO.setPerfTestRunResultsAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 500 */       stmt = getConnection().prepareStatement("select PERF_TEST_RUN_RESULT.PERF_TEST_RUN_RESULT_ID,PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID,PERF_TEST_RUN_RESULT.PERF_TEST_ID,PERF_TEST_RUN_RESULT.EXECUTION_TIME from PERF_TEST_RUN_RESULT where 1=1 and PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID = ? order by  PERF_TEST_RUN_RESULT.PERF_TEST_RUN_RESULT_ID");
/*     */ 
/* 502 */       int col = 1;
/* 503 */       stmt.setInt(col++, entityPK.getPerfTestRunId());
/*     */ 
/* 505 */       resultSet = stmt.executeQuery();
/*     */ 
/* 508 */       while (resultSet.next())
/*     */       {
/* 510 */         itemCount++;
/* 511 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 513 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 516 */       if (timer != null) {
/* 517 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 522 */       throw handleSQLException("select PERF_TEST_RUN_RESULT.PERF_TEST_RUN_RESULT_ID,PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID,PERF_TEST_RUN_RESULT.PERF_TEST_ID,PERF_TEST_RUN_RESULT.EXECUTION_TIME from PERF_TEST_RUN_RESULT where 1=1 and PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID = ? order by  PERF_TEST_RUN_RESULT.PERF_TEST_RUN_RESULT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 526 */       closeResultSet(resultSet);
/* 527 */       closeStatement(stmt);
/* 528 */       closeConnection();
/*     */ 
/* 530 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectPerfTestRunId, String dependants, Collection currentList)
/*     */   {
/* 555 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 556 */     PreparedStatement stmt = null;
/* 557 */     ResultSet resultSet = null;
/*     */ 
/* 559 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 563 */       stmt = getConnection().prepareStatement("select PERF_TEST_RUN_RESULT.PERF_TEST_RUN_RESULT_ID,PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID,PERF_TEST_RUN_RESULT.PERF_TEST_ID,PERF_TEST_RUN_RESULT.EXECUTION_TIME from PERF_TEST_RUN_RESULT where    PERF_TEST_RUN_ID = ? ");
/*     */ 
/* 565 */       int col = 1;
/* 566 */       stmt.setInt(col++, selectPerfTestRunId);
/*     */ 
/* 568 */       resultSet = stmt.executeQuery();
/*     */ 
/* 570 */       while (resultSet.next())
/*     */       {
/* 572 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 575 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 578 */       if (currentList != null)
/*     */       {
/* 581 */         ListIterator iter = items.listIterator();
/* 582 */         PerfTestRunResultEVO currentEVO = null;
/* 583 */         PerfTestRunResultEVO newEVO = null;
/* 584 */         while (iter.hasNext())
/*     */         {
/* 586 */           newEVO = (PerfTestRunResultEVO)iter.next();
/* 587 */           Iterator iter2 = currentList.iterator();
/* 588 */           while (iter2.hasNext())
/*     */           {
/* 590 */             currentEVO = (PerfTestRunResultEVO)iter2.next();
/* 591 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 593 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 599 */         Iterator iter2 = currentList.iterator();
/* 600 */         while (iter2.hasNext())
/*     */         {
/* 602 */           currentEVO = (PerfTestRunResultEVO)iter2.next();
/* 603 */           if (currentEVO.insertPending()) {
/* 604 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 608 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 612 */       throw handleSQLException("select PERF_TEST_RUN_RESULT.PERF_TEST_RUN_RESULT_ID,PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID,PERF_TEST_RUN_RESULT.PERF_TEST_ID,PERF_TEST_RUN_RESULT.EXECUTION_TIME from PERF_TEST_RUN_RESULT where    PERF_TEST_RUN_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 616 */       closeResultSet(resultSet);
/* 617 */       closeStatement(stmt);
/* 618 */       closeConnection();
/*     */ 
/* 620 */       if (timer != null) {
/* 621 */         timer.logDebug("getAll", " PerfTestRunId=" + selectPerfTestRunId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 626 */     return items;
/*     */   }
/*     */ 
/*     */   public PerfTestRunResultEVO getDetails(PerfTestRunCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 640 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 643 */     if (this.mDetails == null) {
/* 644 */       doLoad(((PerfTestRunResultCK)paramCK).getPerfTestRunResultPK());
/*     */     }
/* 646 */     else if (!this.mDetails.getPK().equals(((PerfTestRunResultCK)paramCK).getPerfTestRunResultPK())) {
/* 647 */       doLoad(((PerfTestRunResultCK)paramCK).getPerfTestRunResultPK());
/*     */     }
/*     */ 
/* 650 */     PerfTestRunResultEVO details = new PerfTestRunResultEVO();
/* 651 */     details = this.mDetails.deepClone();
/*     */ 
/* 653 */     if (timer != null) {
/* 654 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 656 */     return details;
/*     */   }
/*     */ 
/*     */   public PerfTestRunResultEVO getDetails(PerfTestRunCK paramCK, PerfTestRunResultEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 662 */     PerfTestRunResultEVO savedEVO = this.mDetails;
/* 663 */     this.mDetails = paramEVO;
/* 664 */     PerfTestRunResultEVO newEVO = getDetails(paramCK, dependants);
/* 665 */     this.mDetails = savedEVO;
/* 666 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public PerfTestRunResultEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 672 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 676 */     PerfTestRunResultEVO details = this.mDetails.deepClone();
/*     */ 
/* 678 */     if (timer != null) {
/* 679 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 681 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 686 */     return "PerfTestRunResult";
/*     */   }
/*     */ 
/*     */   public PerfTestRunResultRefImpl getRef(PerfTestRunResultPK paramPerfTestRunResultPK)
/*     */   {
/* 691 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 692 */     PreparedStatement stmt = null;
/* 693 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 696 */       stmt = getConnection().prepareStatement("select 0,PERF_TEST_RUN.PERF_TEST_RUN_ID from PERF_TEST_RUN_RESULT,PERF_TEST_RUN where 1=1 and PERF_TEST_RUN_RESULT.PERF_TEST_RUN_RESULT_ID = ? and PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID = PERF_TEST_RUN.PERF_TEST_RUN_ID");
/* 697 */       int col = 1;
/* 698 */       stmt.setInt(col++, paramPerfTestRunResultPK.getPerfTestRunResultId());
/*     */ 
/* 700 */       resultSet = stmt.executeQuery();
/*     */ 
/* 702 */       if (!resultSet.next()) {
/* 703 */         throw new RuntimeException(getEntityName() + " getRef " + paramPerfTestRunResultPK + " not found");
/*     */       }
/* 705 */       col = 2;
/* 706 */       PerfTestRunPK newPerfTestRunPK = new PerfTestRunPK(resultSet.getInt(col++));
/*     */ 
/* 710 */       String textPerfTestRunResult = "";
/* 711 */       PerfTestRunResultCK ckPerfTestRunResult = new PerfTestRunResultCK(newPerfTestRunPK, paramPerfTestRunResultPK);
/*     */ 
/* 716 */       PerfTestRunResultRefImpl localPerfTestRunResultRefImpl = new PerfTestRunResultRefImpl(ckPerfTestRunResult, textPerfTestRunResult);
/*     */       return localPerfTestRunResultRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 721 */       throw handleSQLException(paramPerfTestRunResultPK, "select 0,PERF_TEST_RUN.PERF_TEST_RUN_ID from PERF_TEST_RUN_RESULT,PERF_TEST_RUN where 1=1 and PERF_TEST_RUN_RESULT.PERF_TEST_RUN_RESULT_ID = ? and PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID = PERF_TEST_RUN.PERF_TEST_RUN_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 725 */       closeResultSet(resultSet);
/* 726 */       closeStatement(stmt);
/* 727 */       closeConnection();
/*     */ 
/* 729 */       if (timer != null)
/* 730 */         timer.logDebug("getRef", paramPerfTestRunResultPK); 
/* 730 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.perftestrun.PerfTestRunResultDAO
 * JD-Core Version:    0.6.0
 */