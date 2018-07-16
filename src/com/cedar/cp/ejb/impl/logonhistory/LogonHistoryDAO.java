/*     */ package com.cedar.cp.ejb.impl.logonhistory;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.api.logonhistory.LogonHistoryRef;
/*     */ import com.cedar.cp.dto.logonhistory.AllLogonHistorysELO;
/*     */ import com.cedar.cp.dto.logonhistory.AllLogonHistorysReportELO;
/*     */ import com.cedar.cp.dto.logonhistory.LogonHistoryCK;
/*     */ import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
/*     */ import com.cedar.cp.dto.logonhistory.LogonHistoryRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Date;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class LogonHistoryDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select LOGON_HISTORY_ID from LOGON_HISTORY where    LOGON_HISTORY_ID = ? ";
/*     */   private static final String SQL_SELECT_COLUMNS = "select LOGON_HISTORY.LOGON_HISTORY_ID,LOGON_HISTORY.USER_NAME,LOGON_HISTORY.EVENT_DATE,LOGON_HISTORY.EVENT_TYPE,LOGON_HISTORY.VERSION_NUM,LOGON_HISTORY.UPDATED_BY_USER_ID,LOGON_HISTORY.UPDATED_TIME,LOGON_HISTORY.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from LOGON_HISTORY where    LOGON_HISTORY_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into LOGON_HISTORY ( LOGON_HISTORY_ID,USER_NAME,EVENT_DATE,EVENT_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_UPDATE_SEQ_NUM = "update LOGON_HISTORY_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*     */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from LOGON_HISTORY_SEQ";
/*     */   protected static final String SQL_STORE = "update LOGON_HISTORY set USER_NAME = ?,EVENT_DATE = ?,EVENT_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    LOGON_HISTORY_ID = ? AND VERSION_NUM = ?";
/*     */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from LOGON_HISTORY where LOGON_HISTORY_ID = ?";
/*     */   protected static final String SQL_REMOVE = "delete from LOGON_HISTORY where    LOGON_HISTORY_ID = ? ";
/* 625 */   protected static String SQL_ALL_LOGON_HISTORYS = "select 0       ,LOGON_HISTORY.LOGON_HISTORY_ID      ,LOGON_HISTORY.USER_NAME      ,LOGON_HISTORY.EVENT_DATE      ,LOGON_HISTORY.EVENT_TYPE from LOGON_HISTORY where 1=1  order by LOGON_HISTORY.EVENT_DATE desc";
/*     */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from LOGON_HISTORY where   LOGON_HISTORY_ID = ?";
/*     */   protected LogonHistoryEVO mDetails;
/*     */ 
/*     */   public LogonHistoryDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public LogonHistoryDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public LogonHistoryDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected LogonHistoryPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(LogonHistoryEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   public LogonHistoryEVO setAndGetDetails(LogonHistoryEVO details, String dependants)
/*     */   {
/*  83 */     setDetails(details);
/*  84 */     generateKeys();
/*  85 */     return this.mDetails.deepClone();
/*     */   }
/*     */ 
/*     */   public LogonHistoryPK create()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/*  94 */     doCreate();
/*     */ 
/*  96 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void load(LogonHistoryPK pk)
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
/*     */   public LogonHistoryPK findByPrimaryKey(LogonHistoryPK pk_)
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
/* 142 */     throw new ValidationException(new StringBuilder().append(pk_).append(" not found").toString());
/*     */   }
/*     */ 
/*     */   protected boolean exists(LogonHistoryPK pk)
/*     */   {
/* 160 */     PreparedStatement stmt = null;
/* 161 */     ResultSet resultSet = null;
/* 162 */     boolean returnValue = false;
/*     */     try
/*     */     {
/* 166 */       stmt = getConnection().prepareStatement("select LOGON_HISTORY_ID from LOGON_HISTORY where    LOGON_HISTORY_ID = ? ");
/*     */ 
/* 168 */       int col = 1;
/* 169 */       stmt.setInt(col++, pk.getLogonHistoryId());
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
/* 180 */       throw handleSQLException(pk, "select LOGON_HISTORY_ID from LOGON_HISTORY where    LOGON_HISTORY_ID = ? ", sqle);
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
/*     */   private LogonHistoryEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 207 */     int col = 1;
/* 208 */     LogonHistoryEVO evo = new LogonHistoryEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getTimestamp(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/* 216 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 217 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 218 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 219 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(LogonHistoryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 224 */     int col = startCol_;
/* 225 */     stmt_.setInt(col++, evo_.getLogonHistoryId());
/* 226 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(LogonHistoryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 231 */     int col = startCol_;
/* 232 */     stmt_.setString(col++, evo_.getUserName());
/* 233 */     stmt_.setTimestamp(col++, evo_.getEventDate());
/* 234 */     stmt_.setInt(col++, evo_.getEventType());
/* 235 */     stmt_.setInt(col++, evo_.getVersionNum());
/* 236 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 237 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 238 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 239 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(LogonHistoryPK pk)
/*     */     throws ValidationException
/*     */   {
/* 255 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 257 */     PreparedStatement stmt = null;
/* 258 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 262 */       stmt = getConnection().prepareStatement("select LOGON_HISTORY.LOGON_HISTORY_ID,LOGON_HISTORY.USER_NAME,LOGON_HISTORY.EVENT_DATE,LOGON_HISTORY.EVENT_TYPE,LOGON_HISTORY.VERSION_NUM,LOGON_HISTORY.UPDATED_BY_USER_ID,LOGON_HISTORY.UPDATED_TIME,LOGON_HISTORY.CREATED_TIME from LOGON_HISTORY where    LOGON_HISTORY_ID = ? ");
/*     */ 
/* 265 */       int col = 1;
/* 266 */       stmt.setInt(col++, pk.getLogonHistoryId());
/*     */ 
/* 268 */       resultSet = stmt.executeQuery();
/*     */ 
/* 270 */       if (!resultSet.next()) {
/* 271 */         throw new ValidationException(new StringBuilder().append(getEntityName()).append(" select of ").append(pk).append(" not found").toString());
/*     */       }
/*     */ 
/* 274 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 275 */       if (this.mDetails.isModified())
/* 276 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 280 */       throw handleSQLException(pk, "select LOGON_HISTORY.LOGON_HISTORY_ID,LOGON_HISTORY.USER_NAME,LOGON_HISTORY.EVENT_DATE,LOGON_HISTORY.EVENT_TYPE,LOGON_HISTORY.VERSION_NUM,LOGON_HISTORY.UPDATED_BY_USER_ID,LOGON_HISTORY.UPDATED_TIME,LOGON_HISTORY.CREATED_TIME from LOGON_HISTORY where    LOGON_HISTORY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 284 */       closeResultSet(resultSet);
/* 285 */       closeStatement(stmt);
/* 286 */       closeConnection();
/*     */ 
/* 288 */       if (timer != null)
/* 289 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 322 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 323 */     generateKeys();
/*     */ 
/* 325 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 330 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 331 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 332 */       stmt = getConnection().prepareStatement("insert into LOGON_HISTORY ( LOGON_HISTORY_ID,USER_NAME,EVENT_DATE,EVENT_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");
/*     */ 
/* 335 */       int col = 1;
/* 336 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 337 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 340 */       int resultCount = stmt.executeUpdate();
/* 341 */       if (resultCount != 1)
/*     */       {
/* 343 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" insert failed (").append(this.mDetails.getPK()).append("): resultCount=").append(resultCount).toString());
/*     */       }
/*     */ 
/* 346 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 350 */       throw handleSQLException(this.mDetails.getPK(), "insert into LOGON_HISTORY ( LOGON_HISTORY_ID,USER_NAME,EVENT_DATE,EVENT_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 354 */       closeStatement(stmt);
/* 355 */       closeConnection();
/*     */ 
/* 357 */       if (timer != null)
/* 358 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int reserveIds(int insertCount)
/*     */   {
/* 378 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 380 */     PreparedStatement stmt = null;
/* 381 */     ResultSet resultSet = null;
/* 382 */     String sqlString = null;
/*     */     try
/*     */     {
/* 387 */       sqlString = "update LOGON_HISTORY_SEQ set SEQ_NUM = SEQ_NUM + ?";
/* 388 */       stmt = getConnection().prepareStatement("update LOGON_HISTORY_SEQ set SEQ_NUM = SEQ_NUM + ?");
/* 389 */       stmt.setInt(1, insertCount);
/*     */ 
/* 391 */       int resultCount = stmt.executeUpdate();
/* 392 */       if (resultCount != 1) {
/* 393 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" reserveIds: update failed: resultCount=").append(resultCount).toString());
/*     */       }
/* 395 */       closeStatement(stmt);
/*     */ 
/* 398 */       sqlString = "select SEQ_NUM from LOGON_HISTORY_SEQ";
/* 399 */       stmt = getConnection().prepareStatement("select SEQ_NUM from LOGON_HISTORY_SEQ");
/* 400 */       resultSet = stmt.executeQuery();
/* 401 */       if (!resultSet.next())
/* 402 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" reserveIds: select failed").toString());
/* 403 */       int latestKey = resultSet.getInt(1);
/*     */ 
/* 405 */       int i = latestKey - insertCount;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 409 */       throw handleSQLException(sqlString, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 413 */       closeResultSet(resultSet);
/* 414 */       closeStatement(stmt);
/* 415 */       closeConnection();
/*     */ 
/* 417 */       if (timer != null)
/* 418 */         timer.logDebug("reserveIds", new StringBuilder().append("keys=").append(insertCount).toString()); 
/* 418 */     }
/*     */   }
/*     */ 
/*     */   public LogonHistoryPK generateKeys()
/*     */   {
/* 428 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 430 */     int insertCount = this.mDetails.getInsertCount(0);
/*     */ 
/* 433 */     if (insertCount == 0) {
/* 434 */       return this.mDetails.getPK();
/*     */     }
/* 436 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*     */ 
/* 438 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 463 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 465 */     generateKeys();
/*     */ 
/* 470 */     PreparedStatement stmt = null;
/*     */ 
/* 472 */     boolean mainChanged = this.mDetails.isModified();
/* 473 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 476 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 479 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*     */ 
/* 482 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 483 */         stmt = getConnection().prepareStatement("update LOGON_HISTORY set USER_NAME = ?,EVENT_DATE = ?,EVENT_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    LOGON_HISTORY_ID = ? AND VERSION_NUM = ?");
/*     */ 
/* 486 */         int col = 1;
/* 487 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 488 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 490 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*     */ 
/* 493 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 495 */         if (resultCount == 0) {
/* 496 */           checkVersionNum();
/*     */         }
/* 498 */         if (resultCount != 1) {
/* 499 */           throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" update failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
/*     */         }
/*     */ 
/* 502 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 511 */       throw handleSQLException(getPK(), "update LOGON_HISTORY set USER_NAME = ?,EVENT_DATE = ?,EVENT_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    LOGON_HISTORY_ID = ? AND VERSION_NUM = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 515 */       closeStatement(stmt);
/* 516 */       closeConnection();
/*     */ 
/* 518 */       if ((timer != null) && (
/* 519 */         (mainChanged) || (dependantChanged)))
/* 520 */         timer.logDebug("store", new StringBuilder().append(this.mDetails.getPK()).append("(").append(mainChanged).append(",").append(dependantChanged).append(")").toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkVersionNum()
/*     */     throws VersionValidationException
/*     */   {
/* 532 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 533 */     PreparedStatement stmt = null;
/* 534 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 538 */       stmt = getConnection().prepareStatement("select VERSION_NUM from LOGON_HISTORY where LOGON_HISTORY_ID = ?");
/*     */ 
/* 541 */       int col = 1;
/* 542 */       stmt.setInt(col++, this.mDetails.getLogonHistoryId());
/*     */ 
/* 545 */       resultSet = stmt.executeQuery();
/*     */ 
/* 547 */       if (!resultSet.next()) {
/* 548 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" checkVersionNum: select of ").append(getPK()).append(" not found").toString());
/*     */       }
/*     */ 
/* 551 */       col = 1;
/* 552 */       int dbVersionNumber = resultSet.getInt(col++);
/* 553 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/* 554 */         throw new VersionValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" expected:").append(this.mDetails.getVersionNum() - 1).append(" found:").append(dbVersionNumber).toString());
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 560 */       throw handleSQLException(getPK(), "select VERSION_NUM from LOGON_HISTORY where LOGON_HISTORY_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 564 */       closeStatement(stmt);
/* 565 */       closeResultSet(resultSet);
/*     */ 
/* 567 */       if (timer != null)
/* 568 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doRemove()
/*     */   {
/* 585 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 590 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 595 */       stmt = getConnection().prepareStatement("delete from LOGON_HISTORY where    LOGON_HISTORY_ID = ? ");
/*     */ 
/* 598 */       int col = 1;
/* 599 */       stmt.setInt(col++, this.mDetails.getLogonHistoryId());
/*     */ 
/* 601 */       int resultCount = stmt.executeUpdate();
/*     */ 
/* 603 */       if (resultCount != 1) {
/* 604 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" delete failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 610 */       throw handleSQLException(getPK(), "delete from LOGON_HISTORY where    LOGON_HISTORY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 614 */       closeStatement(stmt);
/* 615 */       closeConnection();
/*     */ 
/* 617 */       if (timer != null)
/* 618 */         timer.logDebug("remove", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllLogonHistorysELO getAllLogonHistorys()
/*     */   {
/* 649 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 650 */     PreparedStatement stmt = null;
/* 651 */     ResultSet resultSet = null;
/* 652 */     AllLogonHistorysELO results = new AllLogonHistorysELO();
/*     */     try
/*     */     {
/* 655 */       stmt = getConnection().prepareStatement(SQL_ALL_LOGON_HISTORYS);
/* 656 */       int col = 1;
/* 657 */       resultSet = stmt.executeQuery();
/* 658 */       while (resultSet.next())
/*     */       {
/* 660 */         col = 2;
/*     */ 
/* 663 */         LogonHistoryPK pkLogonHistory = new LogonHistoryPK(resultSet.getInt(col++));
/*     */ 
/* 666 */         String textLogonHistory = "";
/*     */ 
/* 670 */         LogonHistoryRefImpl erLogonHistory = new LogonHistoryRefImpl(pkLogonHistory, textLogonHistory);
/*     */ 
/* 675 */         String col1 = resultSet.getString(col++);
/* 676 */         Timestamp col2 = resultSet.getTimestamp(col++);
/* 677 */         int col3 = resultSet.getInt(col++);
/*     */ 
/* 680 */         results.add(erLogonHistory, col1, col2, col3);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 690 */       throw handleSQLException(SQL_ALL_LOGON_HISTORYS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 694 */       closeResultSet(resultSet);
/* 695 */       closeStatement(stmt);
/* 696 */       closeConnection();
/*     */     }
/*     */ 
/* 699 */     if (timer != null) {
/* 700 */       timer.logDebug("getAllLogonHistorys", new StringBuilder().append(" items=").append(results.size()).toString());
/*     */     }
/*     */ 
/* 704 */     return results;
/*     */   }
/*     */ 
/*     */   public LogonHistoryEVO getDetails(LogonHistoryPK pk, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 722 */     return getDetails(new LogonHistoryCK(pk), dependants);
/*     */   }
/*     */ 
/*     */   public LogonHistoryEVO getDetails(LogonHistoryCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 736 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 739 */     if (this.mDetails == null) {
/* 740 */       doLoad(paramCK.getLogonHistoryPK());
/*     */     }
/* 742 */     else if (!this.mDetails.getPK().equals(paramCK.getLogonHistoryPK())) {
/* 743 */       doLoad(paramCK.getLogonHistoryPK());
/*     */     }
/* 745 */     else if (!checkIfValid())
/*     */     {
/* 747 */       this._log.info("getDetails", new StringBuilder().append("[ALERT] LogonHistoryEVO ").append(this.mDetails.getPK()).append(" no longer valid - reloading").toString());
/*     */ 
/* 749 */       doLoad(paramCK.getLogonHistoryPK());
/*     */     }
/*     */ 
/* 753 */     LogonHistoryEVO details = new LogonHistoryEVO();
/* 754 */     details = this.mDetails.deepClone();
/*     */ 
/* 756 */     if (timer != null) {
/* 757 */       timer.logDebug("getDetails", new StringBuilder().append(paramCK).append(" ").append(dependants).toString());
/*     */     }
/* 759 */     return details;
/*     */   }
/*     */ 
/*     */   private boolean checkIfValid()
/*     */   {
/* 769 */     boolean stillValid = false;
/* 770 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 771 */     PreparedStatement stmt = null;
/* 772 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 775 */       stmt = getConnection().prepareStatement("select VERSION_NUM from LOGON_HISTORY where   LOGON_HISTORY_ID = ?");
/* 776 */       int col = 1;
/* 777 */       stmt.setInt(col++, this.mDetails.getLogonHistoryId());
/*     */ 
/* 779 */       resultSet = stmt.executeQuery();
/*     */ 
/* 781 */       if (!resultSet.next()) {
/* 782 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" checkIfValid ").append(this.mDetails.getPK()).append(" not found").toString());
/*     */       }
/* 784 */       col = 1;
/* 785 */       int dbVersionNum = resultSet.getInt(col++);
/*     */ 
/* 787 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 788 */         stillValid = true;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 792 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from LOGON_HISTORY where   LOGON_HISTORY_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 796 */       closeResultSet(resultSet);
/* 797 */       closeStatement(stmt);
/* 798 */       closeConnection();
/*     */ 
/* 800 */       if (timer != null) {
/* 801 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*     */       }
/*     */     }
/* 804 */     return stillValid;
/*     */   }
/*     */ 
/*     */   public LogonHistoryEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 810 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 812 */     if (!checkIfValid())
/*     */     {
/* 814 */       this._log.info("getDetails", new StringBuilder().append("LogonHistory ").append(this.mDetails.getPK()).append(" no longer valid - reloading").toString());
/* 815 */       doLoad(this.mDetails.getPK());
/*     */     }
/*     */ 
/* 819 */     LogonHistoryEVO details = this.mDetails.deepClone();
/*     */ 
/* 821 */     if (timer != null) {
/* 822 */       timer.logDebug("getDetails", new StringBuilder().append(this.mDetails.getPK()).append(" ").append(dependants).toString());
/*     */     }
/* 824 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 829 */     return "LogonHistory";
/*     */   }
/*     */ 
/*     */   public LogonHistoryRef getRef(LogonHistoryPK paramLogonHistoryPK)
/*     */     throws ValidationException
/*     */   {
/* 835 */     LogonHistoryEVO evo = getDetails(paramLogonHistoryPK, "");
/* 836 */     return evo.getEntityRef("");
/*     */   }
/*     */ 
/*     */   public AllLogonHistorysReportELO getAllLogonHistorysReport(String param1, Timestamp param2, int param3)
/*     */   {
/* 858 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 859 */     PreparedStatement stmt = null;
/* 860 */     ResultSet resultSet = null;
/* 861 */     AllLogonHistorysReportELO results = new AllLogonHistorysReportELO();
/* 862 */     StringBuilder sb = new StringBuilder();
/*     */     try
/*     */     {
/* 865 */       sb.append("SELECT USER_NAME, EVENT_DATE, EVENT_TYPE FROM LOGON_HISTORY WHERE 1=1");
/* 866 */       sb.append(" and  LOGON_HISTORY.USER_NAME LIKE ?");
/* 867 */       if (param2 != null)
/*     */       {
/* 869 */         sb.append(" AND trunc(LOGON_HISTORY.EVENT_DATE) = ?");
/*     */       }
/* 871 */       if (param3 != 0)
/*     */       {
/* 873 */         sb.append(" AND LOGON_HISTORY.EVENT_TYPE = ?");
/*     */       }
/* 875 */       sb.append(" order by LOGON_HISTORY.EVENT_DATE desc");
/* 876 */       stmt = getConnection().prepareStatement(sb.toString());
/* 877 */       int col = 1;
/*     */ 
/* 879 */       stmt.setString(col++, param1);
/* 880 */       if (param2 != null)
/* 881 */         stmt.setTimestamp(col++, param2);
/* 882 */       if (param3 != 0) {
/* 883 */         stmt.setInt(col++, param3);
/*     */       }
/* 885 */       resultSet = stmt.executeQuery();
/* 886 */       while (resultSet.next())
/*     */       {
/* 888 */         col = 1;
/*     */ 
/* 890 */         String col1 = resultSet.getString(col++);
/* 891 */         Timestamp col2 = resultSet.getTimestamp(col++);
/* 892 */         int col3 = resultSet.getInt(col++);
/*     */ 
/* 894 */         results.add(col1, col2, col3);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 903 */       throw handleSQLException(sb.toString(), sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 907 */       closeResultSet(resultSet);
/* 908 */       closeStatement(stmt);
/* 909 */       closeConnection();
/*     */     }
/*     */ 
/* 912 */     if (timer != null) {
/* 913 */       timer.logDebug("getAllLogonHistorysReport", new StringBuilder().append(" UserName=").append(param1).append(",EventDate=").append(param2).append(",EventType=").append(param3).append(" items=").append(results.size()).toString());
/*     */     }
/*     */ 
/* 920 */     return results;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.logonhistory.LogonHistoryDAO
 * JD-Core Version:    0.6.0
 */