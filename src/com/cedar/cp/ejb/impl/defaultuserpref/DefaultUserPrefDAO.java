/*     */ package com.cedar.cp.ejb.impl.defaultuserpref;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.api.defaultuserpref.DefaultUserPrefRef;
/*     */ import com.cedar.cp.dto.defaultuserpref.DefaultUserPrefCK;
/*     */ import com.cedar.cp.dto.defaultuserpref.DefaultUserPrefPK;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class DefaultUserPrefDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select NAME from DEFAULT_USER_PREF where    NAME = ? ";
/*     */   private static final String SQL_SELECT_COLUMNS = "select DEFAULT_USER_PREF.NAME,DEFAULT_USER_PREF.VALUE,DEFAULT_USER_PREF.TYPE,DEFAULT_USER_PREF.HELP_ID,DEFAULT_USER_PREF.VERSION_NUM";
/*     */   protected static final String SQL_LOAD = " from DEFAULT_USER_PREF where    NAME = ? ";
/*     */   protected static final String SQL_CREATE = "insert into DEFAULT_USER_PREF ( NAME,VALUE,TYPE,HELP_ID,VERSION_NUM) values ( ?,?,?,?,?)";
/*     */   protected static final String SQL_UPDATE_SEQ_NUM = "update DEFAULT_USER_PREF_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*     */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from DEFAULT_USER_PREF_SEQ";
/*     */   protected static final String SQL_DUPLICATE_VALUE_CHECK_PROPERTYNAME = "select count(*) from DEFAULT_USER_PREF where    NAME = ? and not(    NAME = ? )";
/*     */   protected static final String SQL_STORE = "update DEFAULT_USER_PREF set VALUE = ?,TYPE = ?,HELP_ID = ?,VERSION_NUM = ? where    NAME = ? AND VERSION_NUM = ?";
/*     */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from DEFAULT_USER_PREF where NAME = ?";
/*     */   protected static final String SQL_REMOVE = "delete from DEFAULT_USER_PREF where    NAME = ? ";
/*     */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from DEFAULT_USER_PREF where   NAME = ?";
/*     */   protected static final String SQL_GET_ALL = "select NAME,VALUE,TYPE,HELP_ID,VERSION_NUM from DEFAULT_USER_PREF";
/*     */   protected DefaultUserPrefEVO mDetails;
/*     */ 
/*     */   public DefaultUserPrefDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public DefaultUserPrefDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DefaultUserPrefDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected DefaultUserPrefPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(DefaultUserPrefEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   public DefaultUserPrefEVO setAndGetDetails(DefaultUserPrefEVO details, String dependants)
/*     */   {
/*  83 */     setDetails(details);
/*  84 */     generateKeys();
/*  85 */     return this.mDetails.deepClone();
/*     */   }
/*     */ 
/*     */   public DefaultUserPrefPK create()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/*  94 */     doCreate();
/*     */ 
/*  96 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void load(DefaultUserPrefPK pk)
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
/*     */   public DefaultUserPrefPK findByPrimaryKey(DefaultUserPrefPK pk_)
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
/*     */   protected boolean exists(DefaultUserPrefPK pk)
/*     */   {
/* 160 */     PreparedStatement stmt = null;
/* 161 */     ResultSet resultSet = null;
/* 162 */     boolean returnValue = false;
/*     */     try
/*     */     {
/* 166 */       stmt = getConnection().prepareStatement("select NAME from DEFAULT_USER_PREF where    NAME = ? ");
/*     */ 
/* 168 */       int col = 1;
/* 169 */       stmt.setString(col++, pk.getName());
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
/* 180 */       throw handleSQLException(pk, "select NAME from DEFAULT_USER_PREF where    NAME = ? ", sqle);
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
/*     */   private DefaultUserPrefEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 204 */     int col = 1;
/* 205 */     DefaultUserPrefEVO evo = new DefaultUserPrefEVO(resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getInt(col++));
/*     */ 
/* 213 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(DefaultUserPrefEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 218 */     int col = startCol_;
/* 219 */     stmt_.setString(col++, evo_.getName());
/* 220 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(DefaultUserPrefEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 225 */     int col = startCol_;
/* 226 */     stmt_.setString(col++, evo_.getValue());
/* 227 */     stmt_.setInt(col++, evo_.getType());
/* 228 */     stmt_.setString(col++, evo_.getHelpId());
/* 229 */     stmt_.setInt(col++, evo_.getVersionNum());
/* 230 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(DefaultUserPrefPK pk)
/*     */     throws ValidationException
/*     */   {
/* 246 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 248 */     PreparedStatement stmt = null;
/* 249 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 253 */       stmt = getConnection().prepareStatement("select DEFAULT_USER_PREF.NAME,DEFAULT_USER_PREF.VALUE,DEFAULT_USER_PREF.TYPE,DEFAULT_USER_PREF.HELP_ID,DEFAULT_USER_PREF.VERSION_NUM from DEFAULT_USER_PREF where    NAME = ? ");
/*     */ 
/* 256 */       int col = 1;
/* 257 */       stmt.setString(col++, pk.getName());
/*     */ 
/* 259 */       resultSet = stmt.executeQuery();
/*     */ 
/* 261 */       if (!resultSet.next()) {
/* 262 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 265 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 266 */       if (this.mDetails.isModified())
/* 267 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 271 */       throw handleSQLException(pk, "select DEFAULT_USER_PREF.NAME,DEFAULT_USER_PREF.VALUE,DEFAULT_USER_PREF.TYPE,DEFAULT_USER_PREF.HELP_ID,DEFAULT_USER_PREF.VERSION_NUM from DEFAULT_USER_PREF where    NAME = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 275 */       closeResultSet(resultSet);
/* 276 */       closeStatement(stmt);
/* 277 */       closeConnection();
/*     */ 
/* 279 */       if (timer != null)
/* 280 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 307 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 308 */     generateKeys();
/*     */ 
/* 310 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 315 */       duplicateValueCheckPropertyName();
/* 316 */       stmt = getConnection().prepareStatement("insert into DEFAULT_USER_PREF ( NAME,VALUE,TYPE,HELP_ID,VERSION_NUM) values ( ?,?,?,?,?)");
/*     */ 
/* 319 */       int col = 1;
/* 320 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 321 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 324 */       int resultCount = stmt.executeUpdate();
/* 325 */       if (resultCount != 1)
/*     */       {
/* 327 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 330 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 334 */       throw handleSQLException(this.mDetails.getPK(), "insert into DEFAULT_USER_PREF ( NAME,VALUE,TYPE,HELP_ID,VERSION_NUM) values ( ?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 338 */       closeStatement(stmt);
/* 339 */       closeConnection();
/*     */ 
/* 341 */       if (timer != null)
/* 342 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int reserveIds(int insertCount)
/*     */   {
/* 362 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 364 */     PreparedStatement stmt = null;
/* 365 */     ResultSet resultSet = null;
/* 366 */     String sqlString = null;
/*     */     try
/*     */     {
/* 371 */       sqlString = "update DEFAULT_USER_PREF_SEQ set SEQ_NUM = SEQ_NUM + ?";
/* 372 */       stmt = getConnection().prepareStatement("update DEFAULT_USER_PREF_SEQ set SEQ_NUM = SEQ_NUM + ?");
/* 373 */       stmt.setInt(1, insertCount);
/*     */ 
/* 375 */       int resultCount = stmt.executeUpdate();
/* 376 */       if (resultCount != 1) {
/* 377 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*     */       }
/* 379 */       closeStatement(stmt);
/*     */ 
/* 382 */       sqlString = "select SEQ_NUM from DEFAULT_USER_PREF_SEQ";
/* 383 */       stmt = getConnection().prepareStatement("select SEQ_NUM from DEFAULT_USER_PREF_SEQ");
/* 384 */       resultSet = stmt.executeQuery();
/* 385 */       if (!resultSet.next())
/* 386 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/* 387 */       int latestKey = resultSet.getInt(1);
/*     */ 
/* 389 */       int i = latestKey - insertCount;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 393 */       throw handleSQLException(sqlString, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 397 */       closeResultSet(resultSet);
/* 398 */       closeStatement(stmt);
/* 399 */       closeConnection();
/*     */ 
/* 401 */       if (timer != null)
/* 402 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/* 402 */     }
/*     */   }
/*     */ 
/*     */   public DefaultUserPrefPK generateKeys()
/*     */   {
/* 412 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 414 */     int insertCount = this.mDetails.getInsertCount(0);
/*     */ 
/* 417 */     if (insertCount == 0) {
/* 418 */       return this.mDetails.getPK();
/*     */     }
/* 420 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*     */ 
/* 422 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   protected void duplicateValueCheckPropertyName()
/*     */     throws DuplicateNameValidationException
/*     */   {
/* 435 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 436 */     PreparedStatement stmt = null;
/* 437 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 441 */       stmt = getConnection().prepareStatement("select count(*) from DEFAULT_USER_PREF where    NAME = ? and not(    NAME = ? )");
/*     */ 
/* 444 */       int col = 1;
/* 445 */       stmt.setString(col++, this.mDetails.getName());
/* 446 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 449 */       resultSet = stmt.executeQuery();
/*     */ 
/* 451 */       if (!resultSet.next()) {
/* 452 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 456 */       col = 1;
/* 457 */       int count = resultSet.getInt(col++);
/* 458 */       if (count > 0) {
/* 459 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " PropertyName");
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 465 */       throw handleSQLException(getPK(), "select count(*) from DEFAULT_USER_PREF where    NAME = ? and not(    NAME = ? )", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 469 */       closeResultSet(resultSet);
/* 470 */       closeStatement(stmt);
/* 471 */       closeConnection();
/*     */ 
/* 473 */       if (timer != null)
/* 474 */         timer.logDebug("duplicateValueCheckPropertyName", "");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 497 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 499 */     generateKeys();
/*     */ 
/* 504 */     PreparedStatement stmt = null;
/*     */ 
/* 506 */     boolean mainChanged = this.mDetails.isModified();
/* 507 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 511 */       if (mainChanged)
/* 512 */         duplicateValueCheckPropertyName();
/* 513 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 516 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*     */ 
/* 518 */         stmt = getConnection().prepareStatement("update DEFAULT_USER_PREF set VALUE = ?,TYPE = ?,HELP_ID = ?,VERSION_NUM = ? where    NAME = ? AND VERSION_NUM = ?");
/*     */ 
/* 521 */         int col = 1;
/* 522 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 523 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 525 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*     */ 
/* 528 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 530 */         if (resultCount == 0) {
/* 531 */           checkVersionNum();
/*     */         }
/* 533 */         if (resultCount != 1) {
/* 534 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 537 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 546 */       throw handleSQLException(getPK(), "update DEFAULT_USER_PREF set VALUE = ?,TYPE = ?,HELP_ID = ?,VERSION_NUM = ? where    NAME = ? AND VERSION_NUM = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 550 */       closeStatement(stmt);
/* 551 */       closeConnection();
/*     */ 
/* 553 */       if ((timer != null) && (
/* 554 */         (mainChanged) || (dependantChanged)))
/* 555 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkVersionNum()
/*     */     throws VersionValidationException
/*     */   {
/* 567 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 568 */     PreparedStatement stmt = null;
/* 569 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 573 */       stmt = getConnection().prepareStatement("select VERSION_NUM from DEFAULT_USER_PREF where NAME = ?");
/*     */ 
/* 576 */       int col = 1;
/* 577 */       stmt.setString(col++, this.mDetails.getName());
/*     */ 
/* 580 */       resultSet = stmt.executeQuery();
/*     */ 
/* 582 */       if (!resultSet.next()) {
/* 583 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 586 */       col = 1;
/* 587 */       int dbVersionNumber = resultSet.getInt(col++);
/* 588 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/* 589 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 595 */       throw handleSQLException(getPK(), "select VERSION_NUM from DEFAULT_USER_PREF where NAME = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 599 */       closeStatement(stmt);
/* 600 */       closeResultSet(resultSet);
/*     */ 
/* 602 */       if (timer != null)
/* 603 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doRemove()
/*     */   {
/* 620 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 625 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 630 */       stmt = getConnection().prepareStatement("delete from DEFAULT_USER_PREF where    NAME = ? ");
/*     */ 
/* 633 */       int col = 1;
/* 634 */       stmt.setString(col++, this.mDetails.getName());
/*     */ 
/* 636 */       int resultCount = stmt.executeUpdate();
/*     */ 
/* 638 */       if (resultCount != 1) {
/* 639 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 645 */       throw handleSQLException(getPK(), "delete from DEFAULT_USER_PREF where    NAME = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 649 */       closeStatement(stmt);
/* 650 */       closeConnection();
/*     */ 
/* 652 */       if (timer != null)
/* 653 */         timer.logDebug("remove", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public DefaultUserPrefEVO getDetails(DefaultUserPrefPK pk, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 672 */     return getDetails(new DefaultUserPrefCK(pk), dependants);
/*     */   }
/*     */ 
/*     */   public DefaultUserPrefEVO getDetails(DefaultUserPrefCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 686 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 689 */     if (this.mDetails == null) {
/* 690 */       doLoad(paramCK.getDefaultUserPrefPK());
/*     */     }
/* 692 */     else if (!this.mDetails.getPK().equals(paramCK.getDefaultUserPrefPK())) {
/* 693 */       doLoad(paramCK.getDefaultUserPrefPK());
/*     */     }
/* 695 */     else if (!checkIfValid())
/*     */     {
/* 697 */       this._log.info("getDetails", "[ALERT] DefaultUserPrefEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*     */ 
/* 699 */       doLoad(paramCK.getDefaultUserPrefPK());
/*     */     }
/*     */ 
/* 703 */     DefaultUserPrefEVO details = new DefaultUserPrefEVO();
/* 704 */     details = this.mDetails.deepClone();
/*     */ 
/* 706 */     if (timer != null) {
/* 707 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 709 */     return details;
/*     */   }
/*     */ 
/*     */   private boolean checkIfValid()
/*     */   {
/* 719 */     boolean stillValid = false;
/* 720 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 721 */     PreparedStatement stmt = null;
/* 722 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 725 */       stmt = getConnection().prepareStatement("select VERSION_NUM from DEFAULT_USER_PREF where   NAME = ?");
/* 726 */       int col = 1;
/* 727 */       stmt.setString(col++, this.mDetails.getName());
/*     */ 
/* 729 */       resultSet = stmt.executeQuery();
/*     */ 
/* 731 */       if (!resultSet.next()) {
/* 732 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*     */       }
/* 734 */       col = 1;
/* 735 */       int dbVersionNum = resultSet.getInt(col++);
/*     */ 
/* 737 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 738 */         stillValid = true;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 742 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from DEFAULT_USER_PREF where   NAME = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 746 */       closeResultSet(resultSet);
/* 747 */       closeStatement(stmt);
/* 748 */       closeConnection();
/*     */ 
/* 750 */       if (timer != null) {
/* 751 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*     */       }
/*     */     }
/* 754 */     return stillValid;
/*     */   }
/*     */ 
/*     */   public DefaultUserPrefEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 760 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 762 */     if (!checkIfValid())
/*     */     {
/* 764 */       this._log.info("getDetails", "DefaultUserPref " + this.mDetails.getPK() + " no longer valid - reloading");
/* 765 */       doLoad(this.mDetails.getPK());
/*     */     }
/*     */ 
/* 769 */     DefaultUserPrefEVO details = this.mDetails.deepClone();
/*     */ 
/* 771 */     if (timer != null) {
/* 772 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 774 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 779 */     return "DefaultUserPref";
/*     */   }
/*     */ 
/*     */   public DefaultUserPrefRef getRef(DefaultUserPrefPK paramDefaultUserPrefPK)
/*     */     throws ValidationException
/*     */   {
/* 785 */     DefaultUserPrefEVO evo = getDetails(paramDefaultUserPrefPK, "");
/* 786 */     return evo.getEntityRef();
/*     */   }
/*     */ 
/*     */   public Collection getAllDefaultUserPreferences()
/*     */   {
/* 804 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 806 */     PreparedStatement stmt = null;
/* 807 */     ResultSet resultSet = null;
/* 808 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 811 */       stmt = getConnection().prepareStatement("select NAME,VALUE,TYPE,HELP_ID,VERSION_NUM from DEFAULT_USER_PREF");
/* 812 */       resultSet = stmt.executeQuery();
/*     */ 
/* 815 */       while (resultSet.next())
/*     */       {
/* 817 */         int col = 1;
/* 818 */         this.mDetails = new DefaultUserPrefEVO(resultSet.getString(col++), resultSet.getString(col++), resultSet.getInt(col++), resultSet.getString(col++), resultSet.getInt(col++));
/*     */ 
/* 827 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 830 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 834 */       throw new RuntimeException(getEntityName() + " getAll", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 838 */       closeResultSet(resultSet);
/* 839 */       closeStatement(stmt);
/* 840 */       closeConnection();
/*     */ 
/* 842 */       if (timer != null)
/* 843 */         this._log.debug("getAllDefaultUserPreferences", "items=" + items.size());
/*     */     }
/* 845 */     return items;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.defaultuserpref.DefaultUserPrefDAO
 * JD-Core Version:    0.6.0
 */