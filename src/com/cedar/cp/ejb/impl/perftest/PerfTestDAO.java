/*     */ package com.cedar.cp.ejb.impl.perftest;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.api.perftest.PerfTestRef;
/*     */ import com.cedar.cp.dto.perftest.AllPerfTestsELO;
/*     */ import com.cedar.cp.dto.perftest.PerfTestCK;
/*     */ import com.cedar.cp.dto.perftest.PerfTestPK;
/*     */ import com.cedar.cp.dto.perftest.PerfTestRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class PerfTestDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select PERF_TEST_ID from PERF_TEST where    PERF_TEST_ID = ? ";
/*     */   private static final String SQL_SELECT_COLUMNS = "select PERF_TEST.PERF_TEST_ID,PERF_TEST.VIS_ID,PERF_TEST.DESCRIPTION,PERF_TEST.CLASS_NAME";
/*     */   protected static final String SQL_LOAD = " from PERF_TEST where    PERF_TEST_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into PERF_TEST ( PERF_TEST_ID,VIS_ID,DESCRIPTION,CLASS_NAME) values ( ?,?,?,?)";
/*     */   protected static final String SQL_UPDATE_SEQ_NUM = "update PERF_TEST_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*     */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from PERF_TEST_SEQ";
/*     */   protected static final String SQL_DUPLICATE_VALUE_CHECK_PERFTESTNAME = "select count(*) from PERF_TEST where    VIS_ID = ? and not(    PERF_TEST_ID = ? )";
/*     */   protected static final String SQL_STORE = "update PERF_TEST set VIS_ID = ?,DESCRIPTION = ?,CLASS_NAME = ? where    PERF_TEST_ID = ? ";
/*     */   protected static final String SQL_REMOVE = "delete from PERF_TEST where    PERF_TEST_ID = ? ";
/* 599 */   protected static String SQL_ALL_PERF_TESTS = "select 0       ,PERF_TEST.PERF_TEST_ID      ,PERF_TEST.PERF_TEST_ID      ,PERF_TEST.VIS_ID      ,PERF_TEST.DESCRIPTION      ,PERF_TEST.CLASS_NAME from PERF_TEST where 1=1  order by PERF_TEST.PERF_TEST_ID";
/*     */   protected PerfTestEVO mDetails;
/*     */ 
/*     */   public PerfTestDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public PerfTestDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PerfTestDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected PerfTestPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(PerfTestEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   public PerfTestEVO setAndGetDetails(PerfTestEVO details, String dependants)
/*     */   {
/*  83 */     setDetails(details);
/*  84 */     generateKeys();
/*  85 */     return this.mDetails.deepClone();
/*     */   }
/*     */ 
/*     */   public PerfTestPK create()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/*  94 */     doCreate();
/*     */ 
/*  96 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void load(PerfTestPK pk)
/*     */     throws ValidationException
/*     */   {
/* 106 */     doLoad(pk);
/*     */   }
/*     */ 
/*     */   public void store()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 115 */     doStore();
/*     */   }
/*     */ 
/*     */   public void remove()
/*     */   {
/* 124 */     doRemove();
/*     */   }
/*     */ 
/*     */   public PerfTestPK findByPrimaryKey(PerfTestPK pk_)
/*     */     throws ValidationException
/*     */   {
/* 133 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 134 */     if (exists(pk_))
/*     */     {
/* 136 */       if (timer != null) {
/* 137 */         timer.logDebug("findByPrimaryKey", pk_);
/*     */       }
/* 139 */       return pk_;
/*     */     }
/*     */ 
/* 142 */     throw new ValidationException(pk_ + " not found");
/*     */   }
/*     */ 
/*     */   protected boolean exists(PerfTestPK pk)
/*     */   {
/* 160 */     PreparedStatement stmt = null;
/* 161 */     ResultSet resultSet = null;
/* 162 */     boolean returnValue = false;
/*     */     try
/*     */     {
/* 166 */       stmt = getConnection().prepareStatement("select PERF_TEST_ID from PERF_TEST where    PERF_TEST_ID = ? ");
/*     */ 
/* 168 */       int col = 1;
/* 169 */       stmt.setInt(col++, pk.getPerfTestId());
/*     */ 
/* 171 */       resultSet = stmt.executeQuery();
/*     */ 
/* 173 */       if (!resultSet.next())
/* 174 */         returnValue = false;
/*     */       else
/* 176 */         returnValue = true;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 180 */       throw handleSQLException(pk, "select PERF_TEST_ID from PERF_TEST where    PERF_TEST_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 184 */       closeResultSet(resultSet);
/* 185 */       closeStatement(stmt);
/* 186 */       closeConnection();
/*     */     }
/* 188 */     return returnValue;
/*     */   }
/*     */ 
/*     */   private PerfTestEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 203 */     int col = 1;
/* 204 */     PerfTestEVO evo = new PerfTestEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++));
/*     */ 
/* 211 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(PerfTestEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 216 */     int col = startCol_;
/* 217 */     stmt_.setInt(col++, evo_.getPerfTestId());
/* 218 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(PerfTestEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 223 */     int col = startCol_;
/* 224 */     stmt_.setString(col++, evo_.getVisId());
/* 225 */     stmt_.setString(col++, evo_.getDescription());
/* 226 */     stmt_.setString(col++, evo_.getClassName());
/* 227 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(PerfTestPK pk)
/*     */     throws ValidationException
/*     */   {
/* 243 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 245 */     PreparedStatement stmt = null;
/* 246 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 250 */       stmt = getConnection().prepareStatement("select PERF_TEST.PERF_TEST_ID,PERF_TEST.VIS_ID,PERF_TEST.DESCRIPTION,PERF_TEST.CLASS_NAME from PERF_TEST where    PERF_TEST_ID = ? ");
/*     */ 
/* 253 */       int col = 1;
/* 254 */       stmt.setInt(col++, pk.getPerfTestId());
/*     */ 
/* 256 */       resultSet = stmt.executeQuery();
/*     */ 
/* 258 */       if (!resultSet.next()) {
/* 259 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 262 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 263 */       if (this.mDetails.isModified())
/* 264 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 268 */       throw handleSQLException(pk, "select PERF_TEST.PERF_TEST_ID,PERF_TEST.VIS_ID,PERF_TEST.DESCRIPTION,PERF_TEST.CLASS_NAME from PERF_TEST where    PERF_TEST_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 272 */       closeResultSet(resultSet);
/* 273 */       closeStatement(stmt);
/* 274 */       closeConnection();
/*     */ 
/* 276 */       if (timer != null)
/* 277 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 302 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 303 */     generateKeys();
/*     */ 
/* 305 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 310 */       duplicateValueCheckPerfTestName();
/* 311 */       stmt = getConnection().prepareStatement("insert into PERF_TEST ( PERF_TEST_ID,VIS_ID,DESCRIPTION,CLASS_NAME) values ( ?,?,?,?)");
/*     */ 
/* 314 */       int col = 1;
/* 315 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 316 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 319 */       int resultCount = stmt.executeUpdate();
/* 320 */       if (resultCount != 1)
/*     */       {
/* 322 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 325 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 329 */       throw handleSQLException(this.mDetails.getPK(), "insert into PERF_TEST ( PERF_TEST_ID,VIS_ID,DESCRIPTION,CLASS_NAME) values ( ?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 333 */       closeStatement(stmt);
/* 334 */       closeConnection();
/*     */ 
/* 336 */       if (timer != null)
/* 337 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int reserveIds(int insertCount)
/*     */   {
/* 357 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 359 */     PreparedStatement stmt = null;
/* 360 */     ResultSet resultSet = null;
/* 361 */     String sqlString = null;
/*     */     try
/*     */     {
/* 366 */       sqlString = "update PERF_TEST_SEQ set SEQ_NUM = SEQ_NUM + ?";
/* 367 */       stmt = getConnection().prepareStatement("update PERF_TEST_SEQ set SEQ_NUM = SEQ_NUM + ?");
/* 368 */       stmt.setInt(1, insertCount);
/*     */ 
/* 370 */       int resultCount = stmt.executeUpdate();
/* 371 */       if (resultCount != 1) {
/* 372 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*     */       }
/* 374 */       closeStatement(stmt);
/*     */ 
/* 377 */       sqlString = "select SEQ_NUM from PERF_TEST_SEQ";
/* 378 */       stmt = getConnection().prepareStatement("select SEQ_NUM from PERF_TEST_SEQ");
/* 379 */       resultSet = stmt.executeQuery();
/* 380 */       if (!resultSet.next())
/* 381 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/* 382 */       int latestKey = resultSet.getInt(1);
/*     */ 
/* 384 */       int i = latestKey - insertCount;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 388 */       throw handleSQLException(sqlString, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 392 */       closeResultSet(resultSet);
/* 393 */       closeStatement(stmt);
/* 394 */       closeConnection();
/*     */ 
/* 396 */       if (timer != null)
/* 397 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/* 397 */     }
/*     */   }
/*     */ 
/*     */   public PerfTestPK generateKeys()
/*     */   {
/* 407 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 409 */     int insertCount = this.mDetails.getInsertCount(0);
/*     */ 
/* 412 */     if (insertCount == 0) {
/* 413 */       return this.mDetails.getPK();
/*     */     }
/* 415 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*     */ 
/* 417 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   protected void duplicateValueCheckPerfTestName()
/*     */     throws DuplicateNameValidationException
/*     */   {
/* 430 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 431 */     PreparedStatement stmt = null;
/* 432 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 436 */       stmt = getConnection().prepareStatement("select count(*) from PERF_TEST where    VIS_ID = ? and not(    PERF_TEST_ID = ? )");
/*     */ 
/* 439 */       int col = 1;
/* 440 */       stmt.setString(col++, this.mDetails.getVisId());
/* 441 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 444 */       resultSet = stmt.executeQuery();
/*     */ 
/* 446 */       if (!resultSet.next()) {
/* 447 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 451 */       col = 1;
/* 452 */       int count = resultSet.getInt(col++);
/* 453 */       if (count > 0) {
/* 454 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " PerfTestName");
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 460 */       throw handleSQLException(getPK(), "select count(*) from PERF_TEST where    VIS_ID = ? and not(    PERF_TEST_ID = ? )", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 464 */       closeResultSet(resultSet);
/* 465 */       closeStatement(stmt);
/* 466 */       closeConnection();
/*     */ 
/* 468 */       if (timer != null)
/* 469 */         timer.logDebug("duplicateValueCheckPerfTestName", "");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 490 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 492 */     generateKeys();
/*     */ 
/* 497 */     PreparedStatement stmt = null;
/*     */ 
/* 499 */     boolean mainChanged = this.mDetails.isModified();
/* 500 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 504 */       if (mainChanged)
/* 505 */         duplicateValueCheckPerfTestName();
/* 506 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 508 */         stmt = getConnection().prepareStatement("update PERF_TEST set VIS_ID = ?,DESCRIPTION = ?,CLASS_NAME = ? where    PERF_TEST_ID = ? ");
/*     */ 
/* 511 */         int col = 1;
/* 512 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 513 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 516 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 519 */         if (resultCount != 1) {
/* 520 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 523 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 532 */       throw handleSQLException(getPK(), "update PERF_TEST set VIS_ID = ?,DESCRIPTION = ?,CLASS_NAME = ? where    PERF_TEST_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 536 */       closeStatement(stmt);
/* 537 */       closeConnection();
/*     */ 
/* 539 */       if ((timer != null) && (
/* 540 */         (mainChanged) || (dependantChanged)))
/* 541 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doRemove()
/*     */   {
/* 559 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 564 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 569 */       stmt = getConnection().prepareStatement("delete from PERF_TEST where    PERF_TEST_ID = ? ");
/*     */ 
/* 572 */       int col = 1;
/* 573 */       stmt.setInt(col++, this.mDetails.getPerfTestId());
/*     */ 
/* 575 */       int resultCount = stmt.executeUpdate();
/*     */ 
/* 577 */       if (resultCount != 1) {
/* 578 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 584 */       throw handleSQLException(getPK(), "delete from PERF_TEST where    PERF_TEST_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 588 */       closeStatement(stmt);
/* 589 */       closeConnection();
/*     */ 
/* 591 */       if (timer != null)
/* 592 */         timer.logDebug("remove", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllPerfTestsELO getAllPerfTests()
/*     */   {
/* 624 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 625 */     PreparedStatement stmt = null;
/* 626 */     ResultSet resultSet = null;
/* 627 */     AllPerfTestsELO results = new AllPerfTestsELO();
/*     */     try
/*     */     {
/* 630 */       stmt = getConnection().prepareStatement(SQL_ALL_PERF_TESTS);
/* 631 */       int col = 1;
/* 632 */       resultSet = stmt.executeQuery();
/* 633 */       while (resultSet.next())
/*     */       {
/* 635 */         col = 2;
/*     */ 
/* 638 */         PerfTestPK pkPerfTest = new PerfTestPK(resultSet.getInt(col++));
/*     */ 
/* 641 */         String textPerfTest = "";
/*     */ 
/* 645 */         PerfTestRefImpl erPerfTest = new PerfTestRefImpl(pkPerfTest, textPerfTest);
/*     */ 
/* 650 */         int col1 = resultSet.getInt(col++);
/* 651 */         String col2 = resultSet.getString(col++);
/* 652 */         String col3 = resultSet.getString(col++);
/* 653 */         String col4 = resultSet.getString(col++);
/*     */ 
/* 656 */         results.add(erPerfTest, col1, col2, col3, col4);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 667 */       throw handleSQLException(SQL_ALL_PERF_TESTS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 671 */       closeResultSet(resultSet);
/* 672 */       closeStatement(stmt);
/* 673 */       closeConnection();
/*     */     }
/*     */ 
/* 676 */     if (timer != null) {
/* 677 */       timer.logDebug("getAllPerfTests", " items=" + results.size());
/*     */     }
/*     */ 
/* 681 */     return results;
/*     */   }
/*     */ 
/*     */   public PerfTestEVO getDetails(PerfTestPK pk, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 699 */     return getDetails(new PerfTestCK(pk), dependants);
/*     */   }
/*     */ 
/*     */   public PerfTestEVO getDetails(PerfTestCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 713 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 716 */     if (this.mDetails == null) {
/* 717 */       doLoad(paramCK.getPerfTestPK());
/*     */     }
/* 719 */     else if (!this.mDetails.getPK().equals(paramCK.getPerfTestPK())) {
/* 720 */       doLoad(paramCK.getPerfTestPK());
/*     */     }
/*     */ 
/* 723 */     PerfTestEVO details = new PerfTestEVO();
/* 724 */     details = this.mDetails.deepClone();
/*     */ 
/* 726 */     if (timer != null) {
/* 727 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 729 */     return details;
/*     */   }
/*     */ 
/*     */   public PerfTestEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 735 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 739 */     PerfTestEVO details = this.mDetails.deepClone();
/*     */ 
/* 741 */     if (timer != null) {
/* 742 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 744 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 749 */     return "PerfTest";
/*     */   }
/*     */ 
/*     */   public PerfTestRef getRef(PerfTestPK paramPerfTestPK)
/*     */     throws ValidationException
/*     */   {
/* 755 */     PerfTestEVO evo = getDetails(paramPerfTestPK, "");
/* 756 */     return evo.getEntityRef("");
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.perftest.PerfTestDAO
 * JD-Core Version:    0.6.0
 */