/*      */ package com.cedar.cp.ejb.impl.perftestrun;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.perftestrun.PerfTestRunRef;
/*      */ import com.cedar.cp.dto.perftestrun.AllPerfTestRunsELO;
/*      */ import com.cedar.cp.dto.perftestrun.PerfTestRunCK;
/*      */ import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
/*      */ import com.cedar.cp.dto.perftestrun.PerfTestRunRefImpl;
/*      */ import com.cedar.cp.dto.perftestrun.PerfTestRunResultCK;
/*      */ import com.cedar.cp.dto.perftestrun.PerfTestRunResultPK;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class PerfTestRunDAO extends AbstractDAO
/*      */ {
/*   32 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select PERF_TEST_RUN_ID from PERF_TEST_RUN where    PERF_TEST_RUN_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select PERF_TEST_RUN.PERF_TEST_RUN_ID,PERF_TEST_RUN.VIS_ID,PERF_TEST_RUN.DESCRIPTION,PERF_TEST_RUN.SHIPPED,PERF_TEST_RUN.WHEN_RAN";
/*      */   protected static final String SQL_LOAD = " from PERF_TEST_RUN where    PERF_TEST_RUN_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into PERF_TEST_RUN ( PERF_TEST_RUN_ID,VIS_ID,DESCRIPTION,SHIPPED,WHEN_RAN) values ( ?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update PERF_TEST_RUN_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from PERF_TEST_RUN_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_PERFTESTRUNNAME = "select count(*) from PERF_TEST_RUN where    VIS_ID = ? and not(    PERF_TEST_RUN_ID = ? )";
/*      */   protected static final String SQL_STORE = "update PERF_TEST_RUN set VIS_ID = ?,DESCRIPTION = ?,SHIPPED = ?,WHEN_RAN = ? where    PERF_TEST_RUN_ID = ? ";
/*      */   protected static final String SQL_REMOVE = "delete from PERF_TEST_RUN where    PERF_TEST_RUN_ID = ? ";
/*  629 */   protected static String SQL_ALL_PERF_TEST_RUNS = "select 0       ,PERF_TEST_RUN.PERF_TEST_RUN_ID      ,PERF_TEST_RUN.PERF_TEST_RUN_ID      ,PERF_TEST_RUN.VIS_ID      ,PERF_TEST_RUN.DESCRIPTION      ,PERF_TEST_RUN.SHIPPED      ,PERF_TEST_RUN.WHEN_RAN from PERF_TEST_RUN where 1=1  order by PERF_TEST_RUN.PERF_TEST_RUN_ID";
/*      */ 
/*  721 */   private static String[][] SQL_DELETE_CHILDREN = { { "PERF_TEST_RUN_RESULT", "delete from PERF_TEST_RUN_RESULT where     PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID = ? " } };
/*      */ 
/*  730 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  734 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and PERF_TEST_RUN.PERF_TEST_RUN_ID = ?)";
/*      */   public static final String SQL_GET_PERF_TEST_RUN_RESULT_REF = "select 0,PERF_TEST_RUN.PERF_TEST_RUN_ID from PERF_TEST_RUN_RESULT,PERF_TEST_RUN where 1=1 and PERF_TEST_RUN_RESULT.PERF_TEST_RUN_RESULT_ID = ? and PERF_TEST_RUN_RESULT.PERF_TEST_RUN_ID = PERF_TEST_RUN.PERF_TEST_RUN_ID";
/*      */   protected PerfTestRunResultDAO mPerfTestRunResultDAO;
/*      */   protected PerfTestRunEVO mDetails;
/*      */ 
/*      */   public PerfTestRunDAO(Connection connection)
/*      */   {
/*   39 */     super(connection);
/*      */   }
/*      */ 
/*      */   public PerfTestRunDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public PerfTestRunDAO(DataSource ds)
/*      */   {
/*   55 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected PerfTestRunPK getPK()
/*      */   {
/*   63 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(PerfTestRunEVO details)
/*      */   {
/*   72 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public PerfTestRunEVO setAndGetDetails(PerfTestRunEVO details, String dependants)
/*      */   {
/*   83 */     setDetails(details);
/*   84 */     generateKeys();
/*   85 */     getDependants(this.mDetails, dependants);
/*   86 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public PerfTestRunPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   95 */     doCreate();
/*      */ 
/*   97 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(PerfTestRunPK pk)
/*      */     throws ValidationException
/*      */   {
/*  107 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  116 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  125 */     doRemove();
/*      */   }
/*      */ 
/*      */   public PerfTestRunPK findByPrimaryKey(PerfTestRunPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  134 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  135 */     if (exists(pk_))
/*      */     {
/*  137 */       if (timer != null) {
/*  138 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  140 */       return pk_;
/*      */     }
/*      */ 
/*  143 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(PerfTestRunPK pk)
/*      */   {
/*  161 */     PreparedStatement stmt = null;
/*  162 */     ResultSet resultSet = null;
/*  163 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  167 */       stmt = getConnection().prepareStatement("select PERF_TEST_RUN_ID from PERF_TEST_RUN where    PERF_TEST_RUN_ID = ? ");
/*      */ 
/*  169 */       int col = 1;
/*  170 */       stmt.setInt(col++, pk.getPerfTestRunId());
/*      */ 
/*  172 */       resultSet = stmt.executeQuery();
/*      */ 
/*  174 */       if (!resultSet.next())
/*  175 */         returnValue = false;
/*      */       else
/*  177 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  181 */       throw handleSQLException(pk, "select PERF_TEST_RUN_ID from PERF_TEST_RUN where    PERF_TEST_RUN_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  185 */       closeResultSet(resultSet);
/*  186 */       closeStatement(stmt);
/*  187 */       closeConnection();
/*      */     }
/*  189 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private PerfTestRunEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  205 */     int col = 1;
/*  206 */     PerfTestRunEVO evo = new PerfTestRunEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getTimestamp(col++), null);
/*      */ 
/*  215 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(PerfTestRunEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  220 */     int col = startCol_;
/*  221 */     stmt_.setInt(col++, evo_.getPerfTestRunId());
/*  222 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(PerfTestRunEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  227 */     int col = startCol_;
/*  228 */     stmt_.setString(col++, evo_.getVisId());
/*  229 */     stmt_.setString(col++, evo_.getDescription());
/*  230 */     if (evo_.getShipped())
/*  231 */       stmt_.setString(col++, "Y");
/*      */     else
/*  233 */       stmt_.setString(col++, " ");
/*  234 */     stmt_.setTimestamp(col++, evo_.getWhenRan());
/*  235 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(PerfTestRunPK pk)
/*      */     throws ValidationException
/*      */   {
/*  251 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  253 */     PreparedStatement stmt = null;
/*  254 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  258 */       stmt = getConnection().prepareStatement("select PERF_TEST_RUN.PERF_TEST_RUN_ID,PERF_TEST_RUN.VIS_ID,PERF_TEST_RUN.DESCRIPTION,PERF_TEST_RUN.SHIPPED,PERF_TEST_RUN.WHEN_RAN from PERF_TEST_RUN where    PERF_TEST_RUN_ID = ? ");
/*      */ 
/*  261 */       int col = 1;
/*  262 */       stmt.setInt(col++, pk.getPerfTestRunId());
/*      */ 
/*  264 */       resultSet = stmt.executeQuery();
/*      */ 
/*  266 */       if (!resultSet.next()) {
/*  267 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  270 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  271 */       if (this.mDetails.isModified())
/*  272 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  276 */       throw handleSQLException(pk, "select PERF_TEST_RUN.PERF_TEST_RUN_ID,PERF_TEST_RUN.VIS_ID,PERF_TEST_RUN.DESCRIPTION,PERF_TEST_RUN.SHIPPED,PERF_TEST_RUN.WHEN_RAN from PERF_TEST_RUN where    PERF_TEST_RUN_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  280 */       closeResultSet(resultSet);
/*  281 */       closeStatement(stmt);
/*  282 */       closeConnection();
/*      */ 
/*  284 */       if (timer != null)
/*  285 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  312 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  313 */     generateKeys();
/*      */ 
/*  315 */     this.mDetails.postCreateInit();
/*      */ 
/*  317 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  322 */       duplicateValueCheckPerfTestRunName();
/*  323 */       stmt = getConnection().prepareStatement("insert into PERF_TEST_RUN ( PERF_TEST_RUN_ID,VIS_ID,DESCRIPTION,SHIPPED,WHEN_RAN) values ( ?,?,?,?,?)");
/*      */ 
/*  326 */       int col = 1;
/*  327 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  328 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  331 */       int resultCount = stmt.executeUpdate();
/*  332 */       if (resultCount != 1)
/*      */       {
/*  334 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  337 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  341 */       throw handleSQLException(this.mDetails.getPK(), "insert into PERF_TEST_RUN ( PERF_TEST_RUN_ID,VIS_ID,DESCRIPTION,SHIPPED,WHEN_RAN) values ( ?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  345 */       closeStatement(stmt);
/*  346 */       closeConnection();
/*      */ 
/*  348 */       if (timer != null) {
/*  349 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  355 */       getPerfTestRunResultDAO().update(this.mDetails.getPerfTestRunResultsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  361 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  381 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  383 */     PreparedStatement stmt = null;
/*  384 */     ResultSet resultSet = null;
/*  385 */     String sqlString = null;
/*      */     try
/*      */     {
/*  390 */       sqlString = "update PERF_TEST_RUN_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  391 */       stmt = getConnection().prepareStatement("update PERF_TEST_RUN_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  392 */       stmt.setInt(1, insertCount);
/*      */ 
/*  394 */       int resultCount = stmt.executeUpdate();
/*  395 */       if (resultCount != 1) {
/*  396 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  398 */       closeStatement(stmt);
/*      */ 
/*  401 */       sqlString = "select SEQ_NUM from PERF_TEST_RUN_SEQ";
/*  402 */       stmt = getConnection().prepareStatement("select SEQ_NUM from PERF_TEST_RUN_SEQ");
/*  403 */       resultSet = stmt.executeQuery();
/*  404 */       if (!resultSet.next())
/*  405 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  406 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  408 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  412 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  416 */       closeResultSet(resultSet);
/*  417 */       closeStatement(stmt);
/*  418 */       closeConnection();
/*      */ 
/*  420 */       if (timer != null)
/*  421 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  421 */     }
/*      */   }
/*      */ 
/*      */   public PerfTestRunPK generateKeys()
/*      */   {
/*  431 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  433 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  436 */     if (insertCount == 0) {
/*  437 */       return this.mDetails.getPK();
/*      */     }
/*  439 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  441 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckPerfTestRunName()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  454 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  455 */     PreparedStatement stmt = null;
/*  456 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  460 */       stmt = getConnection().prepareStatement("select count(*) from PERF_TEST_RUN where    VIS_ID = ? and not(    PERF_TEST_RUN_ID = ? )");
/*      */ 
/*  463 */       int col = 1;
/*  464 */       stmt.setString(col++, this.mDetails.getVisId());
/*  465 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  468 */       resultSet = stmt.executeQuery();
/*      */ 
/*  470 */       if (!resultSet.next()) {
/*  471 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  475 */       col = 1;
/*  476 */       int count = resultSet.getInt(col++);
/*  477 */       if (count > 0) {
/*  478 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " PerfTestRunName");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  484 */       throw handleSQLException(getPK(), "select count(*) from PERF_TEST_RUN where    VIS_ID = ? and not(    PERF_TEST_RUN_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  488 */       closeResultSet(resultSet);
/*  489 */       closeStatement(stmt);
/*  490 */       closeConnection();
/*      */ 
/*  492 */       if (timer != null)
/*  493 */         timer.logDebug("duplicateValueCheckPerfTestRunName", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  515 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  517 */     generateKeys();
/*      */ 
/*  522 */     PreparedStatement stmt = null;
/*      */ 
/*  524 */     boolean mainChanged = this.mDetails.isModified();
/*  525 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  529 */       if (mainChanged) {
/*  530 */         duplicateValueCheckPerfTestRunName();
/*      */       }
/*  532 */       if (getPerfTestRunResultDAO().update(this.mDetails.getPerfTestRunResultsMap())) {
/*  533 */         dependantChanged = true;
/*      */       }
/*  535 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  537 */         stmt = getConnection().prepareStatement("update PERF_TEST_RUN set VIS_ID = ?,DESCRIPTION = ?,SHIPPED = ?,WHEN_RAN = ? where    PERF_TEST_RUN_ID = ? ");
/*      */ 
/*  540 */         int col = 1;
/*  541 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  542 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  545 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  548 */         if (resultCount != 1) {
/*  549 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  552 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  561 */       throw handleSQLException(getPK(), "update PERF_TEST_RUN set VIS_ID = ?,DESCRIPTION = ?,SHIPPED = ?,WHEN_RAN = ? where    PERF_TEST_RUN_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  565 */       closeStatement(stmt);
/*  566 */       closeConnection();
/*      */ 
/*  568 */       if ((timer != null) && (
/*  569 */         (mainChanged) || (dependantChanged)))
/*  570 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  588 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  589 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  594 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  599 */       stmt = getConnection().prepareStatement("delete from PERF_TEST_RUN where    PERF_TEST_RUN_ID = ? ");
/*      */ 
/*  602 */       int col = 1;
/*  603 */       stmt.setInt(col++, this.mDetails.getPerfTestRunId());
/*      */ 
/*  605 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  607 */       if (resultCount != 1) {
/*  608 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  614 */       throw handleSQLException(getPK(), "delete from PERF_TEST_RUN where    PERF_TEST_RUN_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  618 */       closeStatement(stmt);
/*  619 */       closeConnection();
/*      */ 
/*  621 */       if (timer != null)
/*  622 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllPerfTestRunsELO getAllPerfTestRuns()
/*      */   {
/*  655 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  656 */     PreparedStatement stmt = null;
/*  657 */     ResultSet resultSet = null;
/*  658 */     AllPerfTestRunsELO results = new AllPerfTestRunsELO();
/*      */     try
/*      */     {
/*  661 */       stmt = getConnection().prepareStatement(SQL_ALL_PERF_TEST_RUNS);
/*  662 */       int col = 1;
/*  663 */       resultSet = stmt.executeQuery();
/*  664 */       while (resultSet.next())
/*      */       {
/*  666 */         col = 2;
/*      */ 
/*  669 */         PerfTestRunPK pkPerfTestRun = new PerfTestRunPK(resultSet.getInt(col++));
/*      */ 
/*  672 */         String textPerfTestRun = "";
/*      */ 
/*  676 */         PerfTestRunRefImpl erPerfTestRun = new PerfTestRunRefImpl(pkPerfTestRun, textPerfTestRun);
/*      */ 
/*  681 */         int col1 = resultSet.getInt(col++);
/*  682 */         String col2 = resultSet.getString(col++);
/*  683 */         String col3 = resultSet.getString(col++);
/*  684 */         String col4 = resultSet.getString(col++);
/*  685 */         if (resultSet.wasNull())
/*  686 */           col4 = "";
/*  687 */         Timestamp col5 = resultSet.getTimestamp(col++);
/*      */ 
/*  690 */         results.add(erPerfTestRun, col1, col2, col3, col4.equals("Y"), col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  702 */       throw handleSQLException(SQL_ALL_PERF_TEST_RUNS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  706 */       closeResultSet(resultSet);
/*  707 */       closeStatement(stmt);
/*  708 */       closeConnection();
/*      */     }
/*      */ 
/*  711 */     if (timer != null) {
/*  712 */       timer.logDebug("getAllPerfTestRuns", " items=" + results.size());
/*      */     }
/*      */ 
/*  716 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(PerfTestRunPK pk)
/*      */   {
/*  743 */     Set emptyStrings = Collections.emptySet();
/*  744 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(PerfTestRunPK pk, Set<String> exclusionTables)
/*      */   {
/*  750 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  752 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  754 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  756 */       PreparedStatement stmt = null;
/*      */ 
/*  758 */       int resultCount = 0;
/*  759 */       String s = null;
/*      */       try
/*      */       {
/*  762 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  764 */         if (this._log.isDebugEnabled()) {
/*  765 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  767 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  770 */         int col = 1;
/*  771 */         stmt.setInt(col++, pk.getPerfTestRunId());
/*      */ 
/*  774 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  778 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  782 */         closeStatement(stmt);
/*  783 */         closeConnection();
/*      */ 
/*  785 */         if (timer != null) {
/*  786 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  790 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  792 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  794 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  796 */       PreparedStatement stmt = null;
/*      */ 
/*  798 */       int resultCount = 0;
/*  799 */       String s = null;
/*      */       try
/*      */       {
/*  802 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  804 */         if (this._log.isDebugEnabled()) {
/*  805 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  807 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  810 */         int col = 1;
/*  811 */         stmt.setInt(col++, pk.getPerfTestRunId());
/*      */ 
/*  814 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  818 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  822 */         closeStatement(stmt);
/*  823 */         closeConnection();
/*      */ 
/*  825 */         if (timer != null)
/*  826 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public PerfTestRunEVO getDetails(PerfTestRunPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  845 */     return getDetails(new PerfTestRunCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public PerfTestRunEVO getDetails(PerfTestRunCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  862 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  865 */     if (this.mDetails == null) {
/*  866 */       doLoad(paramCK.getPerfTestRunPK());
/*      */     }
/*  868 */     else if (!this.mDetails.getPK().equals(paramCK.getPerfTestRunPK())) {
/*  869 */       doLoad(paramCK.getPerfTestRunPK());
/*      */     }
/*      */ 
/*  878 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isPerfTestRunResultsAllItemsLoaded()))
/*      */     {
/*  883 */       this.mDetails.setPerfTestRunResults(getPerfTestRunResultDAO().getAll(this.mDetails.getPerfTestRunId(), dependants, this.mDetails.getPerfTestRunResults()));
/*      */ 
/*  890 */       this.mDetails.setPerfTestRunResultsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  893 */     if ((paramCK instanceof PerfTestRunResultCK))
/*      */     {
/*  895 */       if (this.mDetails.getPerfTestRunResults() == null) {
/*  896 */         this.mDetails.loadPerfTestRunResultsItem(getPerfTestRunResultDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  899 */         PerfTestRunResultPK pk = ((PerfTestRunResultCK)paramCK).getPerfTestRunResultPK();
/*  900 */         PerfTestRunResultEVO evo = this.mDetails.getPerfTestRunResultsItem(pk);
/*  901 */         if (evo == null) {
/*  902 */           this.mDetails.loadPerfTestRunResultsItem(getPerfTestRunResultDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  907 */     PerfTestRunEVO details = new PerfTestRunEVO();
/*  908 */     details = this.mDetails.deepClone();
/*      */ 
/*  910 */     if (timer != null) {
/*  911 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  913 */     return details;
/*      */   }
/*      */ 
/*      */   public PerfTestRunEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  919 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  923 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  926 */     PerfTestRunEVO details = this.mDetails.deepClone();
/*      */ 
/*  928 */     if (timer != null) {
/*  929 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  931 */     return details;
/*      */   }
/*      */ 
/*      */   protected PerfTestRunResultDAO getPerfTestRunResultDAO()
/*      */   {
/*  940 */     if (this.mPerfTestRunResultDAO == null)
/*      */     {
/*  942 */       if (this.mDataSource != null)
/*  943 */         this.mPerfTestRunResultDAO = new PerfTestRunResultDAO(this.mDataSource);
/*      */       else {
/*  945 */         this.mPerfTestRunResultDAO = new PerfTestRunResultDAO(getConnection());
/*      */       }
/*      */     }
/*  948 */     return this.mPerfTestRunResultDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  953 */     return "PerfTestRun";
/*      */   }
/*      */ 
/*      */   public PerfTestRunRef getRef(PerfTestRunPK paramPerfTestRunPK)
/*      */     throws ValidationException
/*      */   {
/*  959 */     PerfTestRunEVO evo = getDetails(paramPerfTestRunPK, "");
/*  960 */     return evo.getEntityRef("");
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/*  985 */     if (c == null)
/*  986 */       return;
/*  987 */     Iterator iter = c.iterator();
/*  988 */     while (iter.hasNext())
/*      */     {
/*  990 */       PerfTestRunEVO evo = (PerfTestRunEVO)iter.next();
/*  991 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(PerfTestRunEVO evo, String dependants)
/*      */   {
/* 1005 */     if (evo.getPerfTestRunId() < 1) {
/* 1006 */       return;
/*      */     }
/*      */ 
/* 1014 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1017 */       if (!evo.isPerfTestRunResultsAllItemsLoaded())
/*      */       {
/* 1019 */         evo.setPerfTestRunResults(getPerfTestRunResultDAO().getAll(evo.getPerfTestRunId(), dependants, evo.getPerfTestRunResults()));
/*      */ 
/* 1026 */         evo.setPerfTestRunResultsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void justConnect()
/*      */   {
/* 1039 */     PreparedStatement stmt = null;
/* 1040 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1044 */       stmt = getConnection().prepareStatement("SELECT 0 FROM DUAL");
/* 1045 */       resultSet = stmt.executeQuery();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1049 */       throw handleSQLException("SELECT 0 FROM DUAL", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1053 */       closeResultSet(resultSet);
/* 1054 */       closeStatement(stmt);
/* 1055 */       closeConnection();
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.perftestrun.PerfTestRunDAO
 * JD-Core Version:    0.6.0
 */