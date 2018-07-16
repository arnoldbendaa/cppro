/*     */ package com.cedar.cp.ejb.impl.passwordhistory;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.api.passwordhistory.PasswordHistoryRef;
/*     */ import com.cedar.cp.dto.passwordhistory.AllPasswordHistorysELO;
/*     */ import com.cedar.cp.dto.passwordhistory.PasswordHistoryCK;
/*     */ import com.cedar.cp.dto.passwordhistory.PasswordHistoryPK;
/*     */ import com.cedar.cp.dto.passwordhistory.PasswordHistoryRefImpl;
/*     */ import com.cedar.cp.dto.passwordhistory.UserPasswordHistoryELO;
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
/*     */ public class PasswordHistoryDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select PASSWORD_HISTORY_ID from PASSWORD_HISTORY where    PASSWORD_HISTORY_ID = ? ";
/*     */   private static final String SQL_SELECT_COLUMNS = "select PASSWORD_HISTORY.PASSWORD_HISTORY_ID,PASSWORD_HISTORY.USER_ID,PASSWORD_HISTORY.PASSWORD_BYTES,PASSWORD_HISTORY.PASSWORD_DATE,PASSWORD_HISTORY.VERSION_NUM,PASSWORD_HISTORY.UPDATED_BY_USER_ID,PASSWORD_HISTORY.UPDATED_TIME,PASSWORD_HISTORY.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from PASSWORD_HISTORY where    PASSWORD_HISTORY_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into PASSWORD_HISTORY ( PASSWORD_HISTORY_ID,USER_ID,PASSWORD_BYTES,PASSWORD_DATE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_UPDATE_SEQ_NUM = "update PASSWORD_HISTORY_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*     */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from PASSWORD_HISTORY_SEQ";
/*     */   protected static final String SQL_STORE = "update PASSWORD_HISTORY set USER_ID = ?,PASSWORD_BYTES = ?,PASSWORD_DATE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    PASSWORD_HISTORY_ID = ? AND VERSION_NUM = ?";
/*     */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from PASSWORD_HISTORY where PASSWORD_HISTORY_ID = ?";
/*     */   protected static final String SQL_REMOVE = "delete from PASSWORD_HISTORY where    PASSWORD_HISTORY_ID = ? ";
/* 625 */   protected static String SQL_ALL_PASSWORD_HISTORYS = "select 0       ,PASSWORD_HISTORY.PASSWORD_HISTORY_ID      ,PASSWORD_HISTORY.USER_ID      ,PASSWORD_HISTORY.PASSWORD_BYTES      ,PASSWORD_HISTORY.PASSWORD_DATE from PASSWORD_HISTORY where 1=1 ";
/*     */ 
/* 710 */   protected static String SQL_USER_PASSWORD_HISTORY = "select 0       ,PASSWORD_HISTORY.PASSWORD_HISTORY_ID      ,PASSWORD_HISTORY.PASSWORD_BYTES from PASSWORD_HISTORY where 1=1  and  PASSWORD_HISTORY.USER_ID = ? order by PASSWORD_HISTORY.PASSWORD_HISTORY_ID DESC";
/*     */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from PASSWORD_HISTORY where   PASSWORD_HISTORY_ID = ?";
/*     */   protected PasswordHistoryEVO mDetails;
/*     */ 
/*     */   public PasswordHistoryDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public PasswordHistoryDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PasswordHistoryDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected PasswordHistoryPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(PasswordHistoryEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   public PasswordHistoryEVO setAndGetDetails(PasswordHistoryEVO details, String dependants)
/*     */   {
/*  83 */     setDetails(details);
/*  84 */     generateKeys();
/*  85 */     return this.mDetails.deepClone();
/*     */   }
/*     */ 
/*     */   public PasswordHistoryPK create()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/*  94 */     doCreate();
/*     */ 
/*  96 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void load(PasswordHistoryPK pk)
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
/*     */   public PasswordHistoryPK findByPrimaryKey(PasswordHistoryPK pk_)
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
/*     */   protected boolean exists(PasswordHistoryPK pk)
/*     */   {
/* 160 */     PreparedStatement stmt = null;
/* 161 */     ResultSet resultSet = null;
/* 162 */     boolean returnValue = false;
/*     */     try
/*     */     {
/* 166 */       stmt = getConnection().prepareStatement("select PASSWORD_HISTORY_ID from PASSWORD_HISTORY where    PASSWORD_HISTORY_ID = ? ");
/*     */ 
/* 168 */       int col = 1;
/* 169 */       stmt.setInt(col++, pk.getPasswordHistoryId());
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
/* 180 */       throw handleSQLException(pk, "select PASSWORD_HISTORY_ID from PASSWORD_HISTORY where    PASSWORD_HISTORY_ID = ? ", sqle);
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
/*     */   private PasswordHistoryEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 207 */     int col = 1;
/* 208 */     PasswordHistoryEVO evo = new PasswordHistoryEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getTimestamp(col++), resultSet_.getInt(col++));
/*     */ 
/* 216 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 217 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 218 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 219 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(PasswordHistoryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 224 */     int col = startCol_;
/* 225 */     stmt_.setInt(col++, evo_.getPasswordHistoryId());
/* 226 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(PasswordHistoryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 231 */     int col = startCol_;
/* 232 */     stmt_.setInt(col++, evo_.getUserId());
/* 233 */     stmt_.setString(col++, evo_.getPasswordBytes());
/* 234 */     stmt_.setTimestamp(col++, evo_.getPasswordDate());
/* 235 */     stmt_.setInt(col++, evo_.getVersionNum());
/* 236 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 237 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 238 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 239 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(PasswordHistoryPK pk)
/*     */     throws ValidationException
/*     */   {
/* 255 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 257 */     PreparedStatement stmt = null;
/* 258 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 262 */       stmt = getConnection().prepareStatement("select PASSWORD_HISTORY.PASSWORD_HISTORY_ID,PASSWORD_HISTORY.USER_ID,PASSWORD_HISTORY.PASSWORD_BYTES,PASSWORD_HISTORY.PASSWORD_DATE,PASSWORD_HISTORY.VERSION_NUM,PASSWORD_HISTORY.UPDATED_BY_USER_ID,PASSWORD_HISTORY.UPDATED_TIME,PASSWORD_HISTORY.CREATED_TIME from PASSWORD_HISTORY where    PASSWORD_HISTORY_ID = ? ");
/*     */ 
/* 265 */       int col = 1;
/* 266 */       stmt.setInt(col++, pk.getPasswordHistoryId());
/*     */ 
/* 268 */       resultSet = stmt.executeQuery();
/*     */ 
/* 270 */       if (!resultSet.next()) {
/* 271 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 274 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 275 */       if (this.mDetails.isModified())
/* 276 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 280 */       throw handleSQLException(pk, "select PASSWORD_HISTORY.PASSWORD_HISTORY_ID,PASSWORD_HISTORY.USER_ID,PASSWORD_HISTORY.PASSWORD_BYTES,PASSWORD_HISTORY.PASSWORD_DATE,PASSWORD_HISTORY.VERSION_NUM,PASSWORD_HISTORY.UPDATED_BY_USER_ID,PASSWORD_HISTORY.UPDATED_TIME,PASSWORD_HISTORY.CREATED_TIME from PASSWORD_HISTORY where    PASSWORD_HISTORY_ID = ? ", sqle);
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
/* 332 */       stmt = getConnection().prepareStatement("insert into PASSWORD_HISTORY ( PASSWORD_HISTORY_ID,USER_ID,PASSWORD_BYTES,PASSWORD_DATE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");
/*     */ 
/* 335 */       int col = 1;
/* 336 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 337 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 340 */       int resultCount = stmt.executeUpdate();
/* 341 */       if (resultCount != 1)
/*     */       {
/* 343 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 346 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 350 */       throw handleSQLException(this.mDetails.getPK(), "insert into PASSWORD_HISTORY ( PASSWORD_HISTORY_ID,USER_ID,PASSWORD_BYTES,PASSWORD_DATE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
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
/* 387 */       sqlString = "update PASSWORD_HISTORY_SEQ set SEQ_NUM = SEQ_NUM + ?";
/* 388 */       stmt = getConnection().prepareStatement("update PASSWORD_HISTORY_SEQ set SEQ_NUM = SEQ_NUM + ?");
/* 389 */       stmt.setInt(1, insertCount);
/*     */ 
/* 391 */       int resultCount = stmt.executeUpdate();
/* 392 */       if (resultCount != 1) {
/* 393 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*     */       }
/* 395 */       closeStatement(stmt);
/*     */ 
/* 398 */       sqlString = "select SEQ_NUM from PASSWORD_HISTORY_SEQ";
/* 399 */       stmt = getConnection().prepareStatement("select SEQ_NUM from PASSWORD_HISTORY_SEQ");
/* 400 */       resultSet = stmt.executeQuery();
/* 401 */       if (!resultSet.next())
/* 402 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
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
/* 418 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/* 418 */     }
/*     */   }
/*     */ 
/*     */   public PasswordHistoryPK generateKeys()
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
/* 483 */         stmt = getConnection().prepareStatement("update PASSWORD_HISTORY set USER_ID = ?,PASSWORD_BYTES = ?,PASSWORD_DATE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    PASSWORD_HISTORY_ID = ? AND VERSION_NUM = ?");
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
/* 499 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 502 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 511 */       throw handleSQLException(getPK(), "update PASSWORD_HISTORY set USER_ID = ?,PASSWORD_BYTES = ?,PASSWORD_DATE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    PASSWORD_HISTORY_ID = ? AND VERSION_NUM = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 515 */       closeStatement(stmt);
/* 516 */       closeConnection();
/*     */ 
/* 518 */       if ((timer != null) && (
/* 519 */         (mainChanged) || (dependantChanged)))
/* 520 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
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
/* 538 */       stmt = getConnection().prepareStatement("select VERSION_NUM from PASSWORD_HISTORY where PASSWORD_HISTORY_ID = ?");
/*     */ 
/* 541 */       int col = 1;
/* 542 */       stmt.setInt(col++, this.mDetails.getPasswordHistoryId());
/*     */ 
/* 545 */       resultSet = stmt.executeQuery();
/*     */ 
/* 547 */       if (!resultSet.next()) {
/* 548 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 551 */       col = 1;
/* 552 */       int dbVersionNumber = resultSet.getInt(col++);
/* 553 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/* 554 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 560 */       throw handleSQLException(getPK(), "select VERSION_NUM from PASSWORD_HISTORY where PASSWORD_HISTORY_ID = ?", sqle);
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
/* 595 */       stmt = getConnection().prepareStatement("delete from PASSWORD_HISTORY where    PASSWORD_HISTORY_ID = ? ");
/*     */ 
/* 598 */       int col = 1;
/* 599 */       stmt.setInt(col++, this.mDetails.getPasswordHistoryId());
/*     */ 
/* 601 */       int resultCount = stmt.executeUpdate();
/*     */ 
/* 603 */       if (resultCount != 1) {
/* 604 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 610 */       throw handleSQLException(getPK(), "delete from PASSWORD_HISTORY where    PASSWORD_HISTORY_ID = ? ", sqle);
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
/*     */   public AllPasswordHistorysELO getAllPasswordHistorys()
/*     */   {
/* 649 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 650 */     PreparedStatement stmt = null;
/* 651 */     ResultSet resultSet = null;
/* 652 */     AllPasswordHistorysELO results = new AllPasswordHistorysELO();
/*     */     try
/*     */     {
/* 655 */       stmt = getConnection().prepareStatement(SQL_ALL_PASSWORD_HISTORYS);
/* 656 */       int col = 1;
/* 657 */       resultSet = stmt.executeQuery();
/* 658 */       while (resultSet.next())
/*     */       {
/* 660 */         col = 2;
/*     */ 
/* 663 */         PasswordHistoryPK pkPasswordHistory = new PasswordHistoryPK(resultSet.getInt(col++));
/*     */ 
/* 666 */         String textPasswordHistory = "";
/*     */ 
/* 670 */         PasswordHistoryRefImpl erPasswordHistory = new PasswordHistoryRefImpl(pkPasswordHistory, textPasswordHistory);
/*     */ 
/* 675 */         int col1 = resultSet.getInt(col++);
/* 676 */         String col2 = resultSet.getString(col++);
/* 677 */         Timestamp col3 = resultSet.getTimestamp(col++);
/*     */ 
/* 680 */         results.add(erPasswordHistory, col1, col2, col3);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 690 */       throw handleSQLException(SQL_ALL_PASSWORD_HISTORYS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 694 */       closeResultSet(resultSet);
/* 695 */       closeStatement(stmt);
/* 696 */       closeConnection();
/*     */     }
/*     */ 
/* 699 */     if (timer != null) {
/* 700 */       timer.logDebug("getAllPasswordHistorys", " items=" + results.size());
/*     */     }
/*     */ 
/* 704 */     return results;
/*     */   }
/*     */ 
/*     */   public UserPasswordHistoryELO getUserPasswordHistory(int param1)
/*     */   {
/* 734 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 735 */     PreparedStatement stmt = null;
/* 736 */     ResultSet resultSet = null;
/* 737 */     UserPasswordHistoryELO results = new UserPasswordHistoryELO();
/*     */     try
/*     */     {
/* 740 */       stmt = getConnection().prepareStatement(SQL_USER_PASSWORD_HISTORY);
/* 741 */       int col = 1;
/* 742 */       stmt.setInt(col++, param1);
/* 743 */       resultSet = stmt.executeQuery();
/* 744 */       while (resultSet.next())
/*     */       {
/* 746 */         col = 2;
/*     */ 
/* 749 */         PasswordHistoryPK pkPasswordHistory = new PasswordHistoryPK(resultSet.getInt(col++));
/*     */ 
/* 752 */         String textPasswordHistory = "";
/*     */ 
/* 756 */         PasswordHistoryRefImpl erPasswordHistory = new PasswordHistoryRefImpl(pkPasswordHistory, textPasswordHistory);
/*     */ 
/* 761 */         String col1 = resultSet.getString(col++);
/*     */ 
/* 764 */         results.add(erPasswordHistory, col1);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 772 */       throw handleSQLException(SQL_USER_PASSWORD_HISTORY, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 776 */       closeResultSet(resultSet);
/* 777 */       closeStatement(stmt);
/* 778 */       closeConnection();
/*     */     }
/*     */ 
/* 781 */     if (timer != null) {
/* 782 */       timer.logDebug("getUserPasswordHistory", " UserId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 787 */     return results;
/*     */   }
/*     */ 
/*     */   public PasswordHistoryEVO getDetails(PasswordHistoryPK pk, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 805 */     return getDetails(new PasswordHistoryCK(pk), dependants);
/*     */   }
/*     */ 
/*     */   public PasswordHistoryEVO getDetails(PasswordHistoryCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 819 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 822 */     if (this.mDetails == null) {
/* 823 */       doLoad(paramCK.getPasswordHistoryPK());
/*     */     }
/* 825 */     else if (!this.mDetails.getPK().equals(paramCK.getPasswordHistoryPK())) {
/* 826 */       doLoad(paramCK.getPasswordHistoryPK());
/*     */     }
/* 828 */     else if (!checkIfValid())
/*     */     {
/* 830 */       this._log.info("getDetails", "[ALERT] PasswordHistoryEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*     */ 
/* 832 */       doLoad(paramCK.getPasswordHistoryPK());
/*     */     }
/*     */ 
/* 836 */     PasswordHistoryEVO details = new PasswordHistoryEVO();
/* 837 */     details = this.mDetails.deepClone();
/*     */ 
/* 839 */     if (timer != null) {
/* 840 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 842 */     return details;
/*     */   }
/*     */ 
/*     */   private boolean checkIfValid()
/*     */   {
/* 852 */     boolean stillValid = false;
/* 853 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 854 */     PreparedStatement stmt = null;
/* 855 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 858 */       stmt = getConnection().prepareStatement("select VERSION_NUM from PASSWORD_HISTORY where   PASSWORD_HISTORY_ID = ?");
/* 859 */       int col = 1;
/* 860 */       stmt.setInt(col++, this.mDetails.getPasswordHistoryId());
/*     */ 
/* 862 */       resultSet = stmt.executeQuery();
/*     */ 
/* 864 */       if (!resultSet.next()) {
/* 865 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*     */       }
/* 867 */       col = 1;
/* 868 */       int dbVersionNum = resultSet.getInt(col++);
/*     */ 
/* 870 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 871 */         stillValid = true;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 875 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from PASSWORD_HISTORY where   PASSWORD_HISTORY_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 879 */       closeResultSet(resultSet);
/* 880 */       closeStatement(stmt);
/* 881 */       closeConnection();
/*     */ 
/* 883 */       if (timer != null) {
/* 884 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*     */       }
/*     */     }
/* 887 */     return stillValid;
/*     */   }
/*     */ 
/*     */   public PasswordHistoryEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 893 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 895 */     if (!checkIfValid())
/*     */     {
/* 897 */       this._log.info("getDetails", "PasswordHistory " + this.mDetails.getPK() + " no longer valid - reloading");
/* 898 */       doLoad(this.mDetails.getPK());
/*     */     }
/*     */ 
/* 902 */     PasswordHistoryEVO details = this.mDetails.deepClone();
/*     */ 
/* 904 */     if (timer != null) {
/* 905 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 907 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 912 */     return "PasswordHistory";
/*     */   }
/*     */ 
/*     */   public PasswordHistoryRef getRef(PasswordHistoryPK paramPasswordHistoryPK)
/*     */     throws ValidationException
/*     */   {
/* 918 */     PasswordHistoryEVO evo = getDetails(paramPasswordHistoryPK, "");
/* 919 */     return evo.getEntityRef("");
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryDAO
 * JD-Core Version:    0.6.0
 */