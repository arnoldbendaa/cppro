/*     */ package com.cedar.cp.ejb.impl.user;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.user.UserCK;
/*     */ import com.cedar.cp.dto.user.UserPK;
/*     */ import com.cedar.cp.dto.user.UserPreferenceCK;
/*     */ import com.cedar.cp.dto.user.UserPreferencePK;
/*     */ import com.cedar.cp.dto.user.UserPreferenceRefImpl;
/*     */ import com.cedar.cp.dto.user.UserPreferencesForUserELO;
/*     */ import com.cedar.cp.dto.user.UserRefImpl;
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
/*     */ public class UserPreferenceDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select USER_PREFERENCE.USER_PREF_ID,USER_PREFERENCE.USER_ID,USER_PREFERENCE.PREF_NAME,USER_PREFERENCE.PREF_VALUE,USER_PREFERENCE.PREF_TYPE,USER_PREFERENCE.HELP_ID,USER_PREFERENCE.VERSION_NUM,USER_PREFERENCE.UPDATED_BY_USER_ID,USER_PREFERENCE.UPDATED_TIME,USER_PREFERENCE.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from USER_PREFERENCE where    USER_PREF_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into USER_PREFERENCE ( USER_PREF_ID,USER_ID,PREF_NAME,PREF_VALUE,PREF_TYPE,HELP_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update USER_PREFERENCE set USER_ID = ?,PREF_NAME = ?,PREF_VALUE = ?,PREF_TYPE = ?,HELP_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    USER_PREF_ID = ? AND VERSION_NUM = ?";
/*     */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from USER_PREFERENCE where USER_PREF_ID = ?";
/* 388 */   protected static String SQL_USER_PREFERENCES_FOR_USER = "select 0       ,USR.USER_ID      ,USR.NAME      ,USER_PREFERENCE.USER_PREF_ID      ,USER_PREFERENCE.USER_ID      ,USER_PREFERENCE.PREF_NAME      ,USER_PREFERENCE.PREF_VALUE      ,USER_PREFERENCE.PREF_TYPE      ,USER_PREFERENCE.HELP_ID from USER_PREFERENCE    ,USR where 1=1   and USER_PREFERENCE.USER_ID = USR.USER_ID  and  USER_PREFERENCE.USER_ID = ? order by USER_PREFERENCE.USER_ID";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from USER_PREFERENCE where    USER_PREF_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from USER_PREFERENCE where 1=1 and USER_PREFERENCE.USER_ID = ? order by  USER_PREFERENCE.USER_PREF_ID";
/*     */   protected static final String SQL_GET_ALL = " from USER_PREFERENCE where    USER_ID = ? ";
/*     */   protected UserPreferenceEVO mDetails;
/*     */ 
/*     */   public UserPreferenceDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public UserPreferenceDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public UserPreferenceDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected UserPreferencePK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(UserPreferenceEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private UserPreferenceEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  93 */     int col = 1;
/*  94 */     UserPreferenceEVO evo = new UserPreferenceEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getInt(col++));
/*     */ 
/* 104 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 105 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 106 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 107 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(UserPreferenceEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 112 */     int col = startCol_;
/* 113 */     stmt_.setInt(col++, evo_.getUserPrefId());
/* 114 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(UserPreferenceEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 119 */     int col = startCol_;
/* 120 */     stmt_.setInt(col++, evo_.getUserId());
/* 121 */     stmt_.setString(col++, evo_.getPrefName());
/* 122 */     stmt_.setString(col++, evo_.getPrefValue());
/* 123 */     stmt_.setInt(col++, evo_.getPrefType());
/* 124 */     stmt_.setString(col++, evo_.getHelpId());
/* 125 */     stmt_.setInt(col++, evo_.getVersionNum());
/* 126 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 127 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 128 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 129 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(UserPreferencePK pk)
/*     */     throws ValidationException
/*     */   {
/* 145 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 147 */     PreparedStatement stmt = null;
/* 148 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 152 */       stmt = getConnection().prepareStatement("select USER_PREFERENCE.USER_PREF_ID,USER_PREFERENCE.USER_ID,USER_PREFERENCE.PREF_NAME,USER_PREFERENCE.PREF_VALUE,USER_PREFERENCE.PREF_TYPE,USER_PREFERENCE.HELP_ID,USER_PREFERENCE.VERSION_NUM,USER_PREFERENCE.UPDATED_BY_USER_ID,USER_PREFERENCE.UPDATED_TIME,USER_PREFERENCE.CREATED_TIME from USER_PREFERENCE where    USER_PREF_ID = ? ");
/*     */ 
/* 155 */       int col = 1;
/* 156 */       stmt.setInt(col++, pk.getUserPrefId());
/*     */ 
/* 158 */       resultSet = stmt.executeQuery();
/*     */ 
/* 160 */       if (!resultSet.next()) {
/* 161 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 164 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 165 */       if (this.mDetails.isModified())
/* 166 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 170 */       throw handleSQLException(pk, "select USER_PREFERENCE.USER_PREF_ID,USER_PREFERENCE.USER_ID,USER_PREFERENCE.PREF_NAME,USER_PREFERENCE.PREF_VALUE,USER_PREFERENCE.PREF_TYPE,USER_PREFERENCE.HELP_ID,USER_PREFERENCE.VERSION_NUM,USER_PREFERENCE.UPDATED_BY_USER_ID,USER_PREFERENCE.UPDATED_TIME,USER_PREFERENCE.CREATED_TIME from USER_PREFERENCE where    USER_PREF_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 174 */       closeResultSet(resultSet);
/* 175 */       closeStatement(stmt);
/* 176 */       closeConnection();
/*     */ 
/* 178 */       if (timer != null)
/* 179 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 216 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 217 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 222 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 223 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 224 */       stmt = getConnection().prepareStatement("insert into USER_PREFERENCE ( USER_PREF_ID,USER_ID,PREF_NAME,PREF_VALUE,PREF_TYPE,HELP_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 227 */       int col = 1;
/* 228 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 229 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 232 */       int resultCount = stmt.executeUpdate();
/* 233 */       if (resultCount != 1)
/*     */       {
/* 235 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 238 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 242 */       throw handleSQLException(this.mDetails.getPK(), "insert into USER_PREFERENCE ( USER_PREF_ID,USER_ID,PREF_NAME,PREF_VALUE,PREF_TYPE,HELP_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 246 */       closeStatement(stmt);
/* 247 */       closeConnection();
/*     */ 
/* 249 */       if (timer != null)
/* 250 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 279 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 283 */     PreparedStatement stmt = null;
/*     */ 
/* 285 */     boolean mainChanged = this.mDetails.isModified();
/* 286 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 289 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 292 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*     */ 
/* 295 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 296 */         stmt = getConnection().prepareStatement("update USER_PREFERENCE set USER_ID = ?,PREF_NAME = ?,PREF_VALUE = ?,PREF_TYPE = ?,HELP_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    USER_PREF_ID = ? AND VERSION_NUM = ?");
/*     */ 
/* 299 */         int col = 1;
/* 300 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 301 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 303 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*     */ 
/* 306 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 308 */         if (resultCount == 0) {
/* 309 */           checkVersionNum();
/*     */         }
/* 311 */         if (resultCount != 1) {
/* 312 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 315 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 324 */       throw handleSQLException(getPK(), "update USER_PREFERENCE set USER_ID = ?,PREF_NAME = ?,PREF_VALUE = ?,PREF_TYPE = ?,HELP_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    USER_PREF_ID = ? AND VERSION_NUM = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 328 */       closeStatement(stmt);
/* 329 */       closeConnection();
/*     */ 
/* 331 */       if ((timer != null) && (
/* 332 */         (mainChanged) || (dependantChanged)))
/* 333 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkVersionNum()
/*     */     throws VersionValidationException
/*     */   {
/* 345 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 346 */     PreparedStatement stmt = null;
/* 347 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 351 */       stmt = getConnection().prepareStatement("select VERSION_NUM from USER_PREFERENCE where USER_PREF_ID = ?");
/*     */ 
/* 354 */       int col = 1;
/* 355 */       stmt.setInt(col++, this.mDetails.getUserPrefId());
/*     */ 
/* 358 */       resultSet = stmt.executeQuery();
/*     */ 
/* 360 */       if (!resultSet.next()) {
/* 361 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 364 */       col = 1;
/* 365 */       int dbVersionNumber = resultSet.getInt(col++);
/* 366 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/* 367 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 373 */       throw handleSQLException(getPK(), "select VERSION_NUM from USER_PREFERENCE where USER_PREF_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 377 */       closeStatement(stmt);
/* 378 */       closeResultSet(resultSet);
/*     */ 
/* 380 */       if (timer != null)
/* 381 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public UserPreferencesForUserELO getUserPreferencesForUser(int param1)
/*     */   {
/* 422 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 423 */     PreparedStatement stmt = null;
/* 424 */     ResultSet resultSet = null;
/* 425 */     UserPreferencesForUserELO results = new UserPreferencesForUserELO();
/*     */     try
/*     */     {
/* 428 */       stmt = getConnection().prepareStatement(SQL_USER_PREFERENCES_FOR_USER);
/* 429 */       int col = 1;
/* 430 */       stmt.setInt(col++, param1);
/* 431 */       resultSet = stmt.executeQuery();
/* 432 */       while (resultSet.next())
/*     */       {
/* 434 */         col = 2;
/*     */ 
/* 437 */         UserPK pkUser = new UserPK(resultSet.getInt(col++));
/*     */ 
/* 440 */         String textUser = resultSet.getString(col++);
/*     */ 
/* 443 */         UserPreferencePK pkUserPreference = new UserPreferencePK(resultSet.getInt(col++));
/*     */ 
/* 446 */         String textUserPreference = "";
/*     */ 
/* 451 */         UserPreferenceCK ckUserPreference = new UserPreferenceCK(pkUser, pkUserPreference);
/*     */ 
/* 457 */         UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
/*     */ 
/* 463 */         UserPreferenceRefImpl erUserPreference = new UserPreferenceRefImpl(ckUserPreference, textUserPreference);
/*     */ 
/* 468 */         int col1 = resultSet.getInt(col++);
/* 469 */         String col2 = resultSet.getString(col++);
/* 470 */         String col3 = resultSet.getString(col++);
/* 471 */         int col4 = resultSet.getInt(col++);
/* 472 */         String col5 = resultSet.getString(col++);
/*     */ 
/* 475 */         results.add(erUserPreference, erUser, col1, col2, col3, col4, col5);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 488 */       throw handleSQLException(SQL_USER_PREFERENCES_FOR_USER, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 492 */       closeResultSet(resultSet);
/* 493 */       closeStatement(stmt);
/* 494 */       closeConnection();
/*     */     }
/*     */ 
/* 497 */     if (timer != null) {
/* 498 */       timer.logDebug("getUserPreferencesForUser", " UserId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 503 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 520 */     if (items == null) {
/* 521 */       return false;
/*     */     }
/* 523 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 524 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 526 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 531 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 532 */       while (iter2.hasNext())
/*     */       {
/* 534 */         this.mDetails = ((UserPreferenceEVO)iter2.next());
/*     */ 
/* 537 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 539 */         somethingChanged = true;
/*     */ 
/* 542 */         if (deleteStmt == null) {
/* 543 */           deleteStmt = getConnection().prepareStatement("delete from USER_PREFERENCE where    USER_PREF_ID = ? ");
/*     */         }
/*     */ 
/* 546 */         int col = 1;
/* 547 */         deleteStmt.setInt(col++, this.mDetails.getUserPrefId());
/*     */ 
/* 549 */         if (this._log.isDebugEnabled()) {
/* 550 */           this._log.debug("update", "UserPreference deleting UserPrefId=" + this.mDetails.getUserPrefId());
/*     */         }
/*     */ 
/* 555 */         deleteStmt.addBatch();
/*     */ 
/* 558 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 563 */       if (deleteStmt != null)
/*     */       {
/* 565 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 567 */         deleteStmt.executeBatch();
/*     */ 
/* 569 */         if (timer2 != null) {
/* 570 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 574 */       Iterator iter1 = items.values().iterator();
/* 575 */       while (iter1.hasNext())
/*     */       {
/* 577 */         this.mDetails = ((UserPreferenceEVO)iter1.next());
/*     */ 
/* 579 */         if (this.mDetails.insertPending())
/*     */         {
/* 581 */           somethingChanged = true;
/* 582 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 585 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 587 */         somethingChanged = true;
/* 588 */         doStore();
/*     */       }
/*     */ 
/* 599 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 603 */       throw handleSQLException("delete from USER_PREFERENCE where    USER_PREF_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 607 */       if (deleteStmt != null)
/*     */       {
/* 609 */         closeStatement(deleteStmt);
/* 610 */         closeConnection();
/*     */       }
/*     */ 
/* 613 */       this.mDetails = null;
/*     */ 
/* 615 */       if ((somethingChanged) && 
/* 616 */         (timer != null))
/* 617 */         timer.logDebug("update", "collection"); 
/* 617 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(UserPK entityPK, UserEVO owningEVO, String dependants)
/*     */   {
/* 636 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 638 */     PreparedStatement stmt = null;
/* 639 */     ResultSet resultSet = null;
/*     */ 
/* 641 */     int itemCount = 0;
/*     */ 
/* 643 */     Collection theseItems = new ArrayList();
/* 644 */     owningEVO.setUserPreferences(theseItems);
/* 645 */     owningEVO.setUserPreferencesAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 649 */       stmt = getConnection().prepareStatement("select USER_PREFERENCE.USER_PREF_ID,USER_PREFERENCE.USER_ID,USER_PREFERENCE.PREF_NAME,USER_PREFERENCE.PREF_VALUE,USER_PREFERENCE.PREF_TYPE,USER_PREFERENCE.HELP_ID,USER_PREFERENCE.VERSION_NUM,USER_PREFERENCE.UPDATED_BY_USER_ID,USER_PREFERENCE.UPDATED_TIME,USER_PREFERENCE.CREATED_TIME from USER_PREFERENCE where 1=1 and USER_PREFERENCE.USER_ID = ? order by  USER_PREFERENCE.USER_PREF_ID");
/*     */ 
/* 651 */       int col = 1;
/* 652 */       stmt.setInt(col++, entityPK.getUserId());
/*     */ 
/* 654 */       resultSet = stmt.executeQuery();
/*     */ 
/* 657 */       while (resultSet.next())
/*     */       {
/* 659 */         itemCount++;
/* 660 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 662 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 665 */       if (timer != null) {
/* 666 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 671 */       throw handleSQLException("select USER_PREFERENCE.USER_PREF_ID,USER_PREFERENCE.USER_ID,USER_PREFERENCE.PREF_NAME,USER_PREFERENCE.PREF_VALUE,USER_PREFERENCE.PREF_TYPE,USER_PREFERENCE.HELP_ID,USER_PREFERENCE.VERSION_NUM,USER_PREFERENCE.UPDATED_BY_USER_ID,USER_PREFERENCE.UPDATED_TIME,USER_PREFERENCE.CREATED_TIME from USER_PREFERENCE where 1=1 and USER_PREFERENCE.USER_ID = ? order by  USER_PREFERENCE.USER_PREF_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 675 */       closeResultSet(resultSet);
/* 676 */       closeStatement(stmt);
/* 677 */       closeConnection();
/*     */ 
/* 679 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectUserId, String dependants, Collection currentList)
/*     */   {
/* 704 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 705 */     PreparedStatement stmt = null;
/* 706 */     ResultSet resultSet = null;
/*     */ 
/* 708 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 712 */       stmt = getConnection().prepareStatement("select USER_PREFERENCE.USER_PREF_ID,USER_PREFERENCE.USER_ID,USER_PREFERENCE.PREF_NAME,USER_PREFERENCE.PREF_VALUE,USER_PREFERENCE.PREF_TYPE,USER_PREFERENCE.HELP_ID,USER_PREFERENCE.VERSION_NUM,USER_PREFERENCE.UPDATED_BY_USER_ID,USER_PREFERENCE.UPDATED_TIME,USER_PREFERENCE.CREATED_TIME from USER_PREFERENCE where    USER_ID = ? ");
/*     */ 
/* 714 */       int col = 1;
/* 715 */       stmt.setInt(col++, selectUserId);
/*     */ 
/* 717 */       resultSet = stmt.executeQuery();
/*     */ 
/* 719 */       while (resultSet.next())
/*     */       {
/* 721 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 724 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 727 */       if (currentList != null)
/*     */       {
/* 730 */         ListIterator iter = items.listIterator();
/* 731 */         UserPreferenceEVO currentEVO = null;
/* 732 */         UserPreferenceEVO newEVO = null;
/* 733 */         while (iter.hasNext())
/*     */         {
/* 735 */           newEVO = (UserPreferenceEVO)iter.next();
/* 736 */           Iterator iter2 = currentList.iterator();
/* 737 */           while (iter2.hasNext())
/*     */           {
/* 739 */             currentEVO = (UserPreferenceEVO)iter2.next();
/* 740 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 742 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 748 */         Iterator iter2 = currentList.iterator();
/* 749 */         while (iter2.hasNext())
/*     */         {
/* 751 */           currentEVO = (UserPreferenceEVO)iter2.next();
/* 752 */           if (currentEVO.insertPending()) {
/* 753 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 757 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 761 */       throw handleSQLException("select USER_PREFERENCE.USER_PREF_ID,USER_PREFERENCE.USER_ID,USER_PREFERENCE.PREF_NAME,USER_PREFERENCE.PREF_VALUE,USER_PREFERENCE.PREF_TYPE,USER_PREFERENCE.HELP_ID,USER_PREFERENCE.VERSION_NUM,USER_PREFERENCE.UPDATED_BY_USER_ID,USER_PREFERENCE.UPDATED_TIME,USER_PREFERENCE.CREATED_TIME from USER_PREFERENCE where    USER_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 765 */       closeResultSet(resultSet);
/* 766 */       closeStatement(stmt);
/* 767 */       closeConnection();
/*     */ 
/* 769 */       if (timer != null) {
/* 770 */         timer.logDebug("getAll", " UserId=" + selectUserId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 775 */     return items;
/*     */   }
/*     */ 
/*     */   public UserPreferenceEVO getDetails(UserCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 789 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 792 */     if (this.mDetails == null) {
/* 793 */       doLoad(((UserPreferenceCK)paramCK).getUserPreferencePK());
/*     */     }
/* 795 */     else if (!this.mDetails.getPK().equals(((UserPreferenceCK)paramCK).getUserPreferencePK())) {
/* 796 */       doLoad(((UserPreferenceCK)paramCK).getUserPreferencePK());
/*     */     }
/*     */ 
/* 799 */     UserPreferenceEVO details = new UserPreferenceEVO();
/* 800 */     details = this.mDetails.deepClone();
/*     */ 
/* 802 */     if (timer != null) {
/* 803 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 805 */     return details;
/*     */   }
/*     */ 
/*     */   public UserPreferenceEVO getDetails(UserCK paramCK, UserPreferenceEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 811 */     UserPreferenceEVO savedEVO = this.mDetails;
/* 812 */     this.mDetails = paramEVO;
/* 813 */     UserPreferenceEVO newEVO = getDetails(paramCK, dependants);
/* 814 */     this.mDetails = savedEVO;
/* 815 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public UserPreferenceEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 821 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 825 */     UserPreferenceEVO details = this.mDetails.deepClone();
/*     */ 
/* 827 */     if (timer != null) {
/* 828 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 830 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 835 */     return "UserPreference";
/*     */   }
/*     */ 
/*     */   public UserPreferenceRefImpl getRef(UserPreferencePK paramUserPreferencePK)
/*     */   {
/* 840 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 841 */     PreparedStatement stmt = null;
/* 842 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 845 */       stmt = getConnection().prepareStatement("select 0,USR.USER_ID from USER_PREFERENCE,USR where 1=1 and USER_PREFERENCE.USER_PREF_ID = ? and USER_PREFERENCE.USER_ID = USR.USER_ID");
/* 846 */       int col = 1;
/* 847 */       stmt.setInt(col++, paramUserPreferencePK.getUserPrefId());
/*     */ 
/* 849 */       resultSet = stmt.executeQuery();
/*     */ 
/* 851 */       if (!resultSet.next()) {
/* 852 */         throw new RuntimeException(getEntityName() + " getRef " + paramUserPreferencePK + " not found");
/*     */       }
/* 854 */       col = 2;
/* 855 */       UserPK newUserPK = new UserPK(resultSet.getInt(col++));
/*     */ 
/* 859 */       String textUserPreference = "";
/* 860 */       UserPreferenceCK ckUserPreference = new UserPreferenceCK(newUserPK, paramUserPreferencePK);
/*     */ 
/* 865 */       UserPreferenceRefImpl localUserPreferenceRefImpl = new UserPreferenceRefImpl(ckUserPreference, textUserPreference);
/*     */       return localUserPreferenceRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 870 */       throw handleSQLException(paramUserPreferencePK, "select 0,USR.USER_ID from USER_PREFERENCE,USR where 1=1 and USER_PREFERENCE.USER_PREF_ID = ? and USER_PREFERENCE.USER_ID = USR.USER_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 874 */       closeResultSet(resultSet);
/* 875 */       closeStatement(stmt);
/* 876 */       closeConnection();
/*     */ 
/* 878 */       if (timer != null)
/* 879 */         timer.logDebug("getRef", paramUserPreferencePK); 
/* 879 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.user.UserPreferenceDAO
 * JD-Core Version:    0.6.0
 */