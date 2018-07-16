/*     */ package com.cedar.cp.ejb.impl.currency;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.api.currency.CurrencyRef;
/*     */ import com.cedar.cp.dto.currency.AllCurrencysELO;
/*     */ import com.cedar.cp.dto.currency.CurrencyCK;
/*     */ import com.cedar.cp.dto.currency.CurrencyPK;
/*     */ import com.cedar.cp.dto.currency.CurrencyRefImpl;
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
/*     */ public class CurrencyDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select CURRENCY_ID from CURRENCY where    CURRENCY_ID = ? ";
/*     */   private static final String SQL_SELECT_COLUMNS = "select CURRENCY.CURRENCY_ID,CURRENCY.VIS_ID,CURRENCY.DESCRIPTION,CURRENCY.VERSION_NUM,CURRENCY.UPDATED_BY_USER_ID,CURRENCY.UPDATED_TIME,CURRENCY.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from CURRENCY where    CURRENCY_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into CURRENCY ( CURRENCY_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_UPDATE_SEQ_NUM = "update CURRENCY_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*     */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from CURRENCY_SEQ";
/*     */   protected static final String SQL_DUPLICATE_VALUE_CHECK_VISID = "select count(*) from CURRENCY where    VIS_ID = ? and not(    CURRENCY_ID = ? )";
/*     */   protected static final String SQL_STORE = "update CURRENCY set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CURRENCY_ID = ? AND VERSION_NUM = ?";
/*     */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from CURRENCY where CURRENCY_ID = ?";
/*     */   protected static final String SQL_REMOVE = "delete from CURRENCY where    CURRENCY_ID = ? ";
/* 677 */   protected static String SQL_ALL_CURRENCYS = "select 0       ,CURRENCY.CURRENCY_ID      ,CURRENCY.VIS_ID      ,CURRENCY.DESCRIPTION from CURRENCY where 1=1  order by VIS_ID";
/*     */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from CURRENCY where   CURRENCY_ID = ?";
/*     */   protected CurrencyEVO mDetails;
/*     */ 
/*     */   public CurrencyDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public CurrencyDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CurrencyDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected CurrencyPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(CurrencyEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   public CurrencyEVO setAndGetDetails(CurrencyEVO details, String dependants)
/*     */   {
/*  83 */     setDetails(details);
/*  84 */     generateKeys();
/*  85 */     return this.mDetails.deepClone();
/*     */   }
/*     */ 
/*     */   public CurrencyPK create()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/*  94 */     doCreate();
/*     */ 
/*  96 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void load(CurrencyPK pk)
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
/*     */   public CurrencyPK findByPrimaryKey(CurrencyPK pk_)
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
/*     */   protected boolean exists(CurrencyPK pk)
/*     */   {
/* 160 */     PreparedStatement stmt = null;
/* 161 */     ResultSet resultSet = null;
/* 162 */     boolean returnValue = false;
/*     */     try
/*     */     {
/* 166 */       stmt = getConnection().prepareStatement("select CURRENCY_ID from CURRENCY where    CURRENCY_ID = ? ");
/*     */ 
/* 168 */       int col = 1;
/* 169 */       stmt.setInt(col++, pk.getCurrencyId());
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
/* 180 */       throw handleSQLException(pk, "select CURRENCY_ID from CURRENCY where    CURRENCY_ID = ? ", sqle);
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
/*     */   private CurrencyEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 206 */     int col = 1;
/* 207 */     CurrencyEVO evo = new CurrencyEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++));
/*     */ 
/* 214 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 215 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 216 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 217 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(CurrencyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 222 */     int col = startCol_;
/* 223 */     stmt_.setInt(col++, evo_.getCurrencyId());
/* 224 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(CurrencyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 229 */     int col = startCol_;
/* 230 */     stmt_.setString(col++, evo_.getVisId());
/* 231 */     stmt_.setString(col++, evo_.getDescription());
/* 232 */     stmt_.setInt(col++, evo_.getVersionNum());
/* 233 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 234 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 235 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 236 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(CurrencyPK pk)
/*     */     throws ValidationException
/*     */   {
/* 252 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 254 */     PreparedStatement stmt = null;
/* 255 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 259 */       stmt = getConnection().prepareStatement("select CURRENCY.CURRENCY_ID,CURRENCY.VIS_ID,CURRENCY.DESCRIPTION,CURRENCY.VERSION_NUM,CURRENCY.UPDATED_BY_USER_ID,CURRENCY.UPDATED_TIME,CURRENCY.CREATED_TIME from CURRENCY where    CURRENCY_ID = ? ");
/*     */ 
/* 262 */       int col = 1;
/* 263 */       stmt.setInt(col++, pk.getCurrencyId());
/*     */ 
/* 265 */       resultSet = stmt.executeQuery();
/*     */ 
/* 267 */       if (!resultSet.next()) {
/* 268 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 271 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 272 */       if (this.mDetails.isModified())
/* 273 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 277 */       throw handleSQLException(pk, "select CURRENCY.CURRENCY_ID,CURRENCY.VIS_ID,CURRENCY.DESCRIPTION,CURRENCY.VERSION_NUM,CURRENCY.UPDATED_BY_USER_ID,CURRENCY.UPDATED_TIME,CURRENCY.CREATED_TIME from CURRENCY where    CURRENCY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 281 */       closeResultSet(resultSet);
/* 282 */       closeStatement(stmt);
/* 283 */       closeConnection();
/*     */ 
/* 285 */       if (timer != null)
/* 286 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 317 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 318 */     generateKeys();
/*     */ 
/* 320 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 325 */       duplicateValueCheckVisId();
/*     */ 
/* 327 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 328 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 329 */       stmt = getConnection().prepareStatement("insert into CURRENCY ( CURRENCY_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*     */ 
/* 332 */       int col = 1;
/* 333 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 334 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 337 */       int resultCount = stmt.executeUpdate();
/* 338 */       if (resultCount != 1)
/*     */       {
/* 340 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 343 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 347 */       throw handleSQLException(this.mDetails.getPK(), "insert into CURRENCY ( CURRENCY_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 351 */       closeStatement(stmt);
/* 352 */       closeConnection();
/*     */ 
/* 354 */       if (timer != null)
/* 355 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int reserveIds(int insertCount)
/*     */   {
/* 375 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 377 */     PreparedStatement stmt = null;
/* 378 */     ResultSet resultSet = null;
/* 379 */     String sqlString = null;
/*     */     try
/*     */     {
/* 384 */       sqlString = "update CURRENCY_SEQ set SEQ_NUM = SEQ_NUM + ?";
/* 385 */       stmt = getConnection().prepareStatement("update CURRENCY_SEQ set SEQ_NUM = SEQ_NUM + ?");
/* 386 */       stmt.setInt(1, insertCount);
/*     */ 
/* 388 */       int resultCount = stmt.executeUpdate();
/* 389 */       if (resultCount != 1) {
/* 390 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*     */       }
/* 392 */       closeStatement(stmt);
/*     */ 
/* 395 */       sqlString = "select SEQ_NUM from CURRENCY_SEQ";
/* 396 */       stmt = getConnection().prepareStatement("select SEQ_NUM from CURRENCY_SEQ");
/* 397 */       resultSet = stmt.executeQuery();
/* 398 */       if (!resultSet.next())
/* 399 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/* 400 */       int latestKey = resultSet.getInt(1);
/*     */ 
/* 402 */       int i = latestKey - insertCount;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 406 */       throw handleSQLException(sqlString, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 410 */       closeResultSet(resultSet);
/* 411 */       closeStatement(stmt);
/* 412 */       closeConnection();
/*     */ 
/* 414 */       if (timer != null)
/* 415 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/* 415 */     }
/*     */   }
/*     */ 
/*     */   public CurrencyPK generateKeys()
/*     */   {
/* 425 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 427 */     int insertCount = this.mDetails.getInsertCount(0);
/*     */ 
/* 430 */     if (insertCount == 0) {
/* 431 */       return this.mDetails.getPK();
/*     */     }
/* 433 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*     */ 
/* 435 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   protected void duplicateValueCheckVisId()
/*     */     throws DuplicateNameValidationException
/*     */   {
/* 448 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 449 */     PreparedStatement stmt = null;
/* 450 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 454 */       stmt = getConnection().prepareStatement("select count(*) from CURRENCY where    VIS_ID = ? and not(    CURRENCY_ID = ? )");
/*     */ 
/* 457 */       int col = 1;
/* 458 */       stmt.setString(col++, this.mDetails.getVisId());
/* 459 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 462 */       resultSet = stmt.executeQuery();
/*     */ 
/* 464 */       if (!resultSet.next()) {
/* 465 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 469 */       col = 1;
/* 470 */       int count = resultSet.getInt(col++);
/* 471 */       if (count > 0) {
/* 472 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " VisId");
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 478 */       throw handleSQLException(getPK(), "select count(*) from CURRENCY where    VIS_ID = ? and not(    CURRENCY_ID = ? )", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 482 */       closeResultSet(resultSet);
/* 483 */       closeStatement(stmt);
/* 484 */       closeConnection();
/*     */ 
/* 486 */       if (timer != null)
/* 487 */         timer.logDebug("duplicateValueCheckVisId", "");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 512 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 514 */     generateKeys();
/*     */ 
/* 519 */     PreparedStatement stmt = null;
/*     */ 
/* 521 */     boolean mainChanged = this.mDetails.isModified();
/* 522 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 526 */       if (mainChanged)
/* 527 */         duplicateValueCheckVisId();
/* 528 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 531 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*     */ 
/* 534 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 535 */         stmt = getConnection().prepareStatement("update CURRENCY set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CURRENCY_ID = ? AND VERSION_NUM = ?");
/*     */ 
/* 538 */         int col = 1;
/* 539 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 540 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 542 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*     */ 
/* 545 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 547 */         if (resultCount == 0) {
/* 548 */           checkVersionNum();
/*     */         }
/* 550 */         if (resultCount != 1) {
/* 551 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 554 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 563 */       throw handleSQLException(getPK(), "update CURRENCY set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CURRENCY_ID = ? AND VERSION_NUM = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 567 */       closeStatement(stmt);
/* 568 */       closeConnection();
/*     */ 
/* 570 */       if ((timer != null) && (
/* 571 */         (mainChanged) || (dependantChanged)))
/* 572 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkVersionNum()
/*     */     throws VersionValidationException
/*     */   {
/* 584 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 585 */     PreparedStatement stmt = null;
/* 586 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 590 */       stmt = getConnection().prepareStatement("select VERSION_NUM from CURRENCY where CURRENCY_ID = ?");
/*     */ 
/* 593 */       int col = 1;
/* 594 */       stmt.setInt(col++, this.mDetails.getCurrencyId());
/*     */ 
/* 597 */       resultSet = stmt.executeQuery();
/*     */ 
/* 599 */       if (!resultSet.next()) {
/* 600 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 603 */       col = 1;
/* 604 */       int dbVersionNumber = resultSet.getInt(col++);
/* 605 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/* 606 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 612 */       throw handleSQLException(getPK(), "select VERSION_NUM from CURRENCY where CURRENCY_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 616 */       closeStatement(stmt);
/* 617 */       closeResultSet(resultSet);
/*     */ 
/* 619 */       if (timer != null)
/* 620 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doRemove()
/*     */   {
/* 637 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 642 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 647 */       stmt = getConnection().prepareStatement("delete from CURRENCY where    CURRENCY_ID = ? ");
/*     */ 
/* 650 */       int col = 1;
/* 651 */       stmt.setInt(col++, this.mDetails.getCurrencyId());
/*     */ 
/* 653 */       int resultCount = stmt.executeUpdate();
/*     */ 
/* 655 */       if (resultCount != 1) {
/* 656 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 662 */       throw handleSQLException(getPK(), "delete from CURRENCY where    CURRENCY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 666 */       closeStatement(stmt);
/* 667 */       closeConnection();
/*     */ 
/* 669 */       if (timer != null)
/* 670 */         timer.logDebug("remove", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllCurrencysELO getAllCurrencys()
/*     */   {
/* 700 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 701 */     PreparedStatement stmt = null;
/* 702 */     ResultSet resultSet = null;
/* 703 */     AllCurrencysELO results = new AllCurrencysELO();
/*     */     try
/*     */     {
/* 706 */       stmt = getConnection().prepareStatement(SQL_ALL_CURRENCYS);
/* 707 */       int col = 1;
/* 708 */       resultSet = stmt.executeQuery();
/* 709 */       while (resultSet.next())
/*     */       {
/* 711 */         col = 2;
/*     */ 
/* 714 */         CurrencyPK pkCurrency = new CurrencyPK(resultSet.getInt(col++));
/*     */ 
/* 717 */         String textCurrency = resultSet.getString(col++);
/*     */ 
/* 721 */         CurrencyRefImpl erCurrency = new CurrencyRefImpl(pkCurrency, textCurrency);
/*     */ 
/* 726 */         String col1 = resultSet.getString(col++);
/*     */ 
/* 729 */         results.add(erCurrency, col1);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 737 */       throw handleSQLException(SQL_ALL_CURRENCYS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 741 */       closeResultSet(resultSet);
/* 742 */       closeStatement(stmt);
/* 743 */       closeConnection();
/*     */     }
/*     */ 
/* 746 */     if (timer != null) {
/* 747 */       timer.logDebug("getAllCurrencys", " items=" + results.size());
/*     */     }
/*     */ 
/* 751 */     return results;
/*     */   }
/*     */ 
/*     */   public CurrencyEVO getDetails(CurrencyPK pk, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 769 */     return getDetails(new CurrencyCK(pk), dependants);
/*     */   }
/*     */ 
/*     */   public CurrencyEVO getDetails(CurrencyCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 783 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 786 */     if (this.mDetails == null) {
/* 787 */       doLoad(paramCK.getCurrencyPK());
/*     */     }
/* 789 */     else if (!this.mDetails.getPK().equals(paramCK.getCurrencyPK())) {
/* 790 */       doLoad(paramCK.getCurrencyPK());
/*     */     }
/* 792 */     else if (!checkIfValid())
/*     */     {
/* 794 */       this._log.info("getDetails", "[ALERT] CurrencyEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*     */ 
/* 796 */       doLoad(paramCK.getCurrencyPK());
/*     */     }
/*     */ 
/* 800 */     CurrencyEVO details = new CurrencyEVO();
/* 801 */     details = this.mDetails.deepClone();
/*     */ 
/* 803 */     if (timer != null) {
/* 804 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 806 */     return details;
/*     */   }
/*     */ 
/*     */   private boolean checkIfValid()
/*     */   {
/* 816 */     boolean stillValid = false;
/* 817 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 818 */     PreparedStatement stmt = null;
/* 819 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 822 */       stmt = getConnection().prepareStatement("select VERSION_NUM from CURRENCY where   CURRENCY_ID = ?");
/* 823 */       int col = 1;
/* 824 */       stmt.setInt(col++, this.mDetails.getCurrencyId());
/*     */ 
/* 826 */       resultSet = stmt.executeQuery();
/*     */ 
/* 828 */       if (!resultSet.next()) {
/* 829 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*     */       }
/* 831 */       col = 1;
/* 832 */       int dbVersionNum = resultSet.getInt(col++);
/*     */ 
/* 834 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 835 */         stillValid = true;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 839 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from CURRENCY where   CURRENCY_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 843 */       closeResultSet(resultSet);
/* 844 */       closeStatement(stmt);
/* 845 */       closeConnection();
/*     */ 
/* 847 */       if (timer != null) {
/* 848 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*     */       }
/*     */     }
/* 851 */     return stillValid;
/*     */   }
/*     */ 
/*     */   public CurrencyEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 857 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 859 */     if (!checkIfValid())
/*     */     {
/* 861 */       this._log.info("getDetails", "Currency " + this.mDetails.getPK() + " no longer valid - reloading");
/* 862 */       doLoad(this.mDetails.getPK());
/*     */     }
/*     */ 
/* 866 */     CurrencyEVO details = this.mDetails.deepClone();
/*     */ 
/* 868 */     if (timer != null) {
/* 869 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 871 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 876 */     return "Currency";
/*     */   }
/*     */ 
/*     */   public CurrencyRef getRef(CurrencyPK paramCurrencyPK)
/*     */     throws ValidationException
/*     */   {
/* 882 */     CurrencyEVO evo = getDetails(paramCurrencyPK, "");
/* 883 */     return evo.getEntityRef();
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.currency.CurrencyDAO
 * JD-Core Version:    0.6.0
 */