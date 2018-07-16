/*      */ package com.cedar.cp.ejb.impl.report.destination.internal;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.report.destination.internal.InternalDestinationRef;
/*      */ import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationDetailsELO;
/*      */ import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationsELO;
/*      */ import com.cedar.cp.dto.report.destination.internal.AllUsersForInternalDestinationIdELO;
/*      */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationCK;
/*      */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
/*      */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationRefImpl;
/*      */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersCK;
/*      */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersPK;
/*      */ import com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersRefImpl;
/*      */ import com.cedar.cp.dto.user.AllNonDisabledUsersELO;
/*      */ import com.cedar.cp.dto.user.UserPK;
/*      */ import com.cedar.cp.dto.user.UserRefImpl;
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
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class InternalDestinationDAO extends AbstractDAO
/*      */ {
/*   38 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select INTERNAL_DESTINATION_ID from INTERNAL_DESTINATION where    INTERNAL_DESTINATION_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID,INTERNAL_DESTINATION.VIS_ID,INTERNAL_DESTINATION.DESCRIPTION,INTERNAL_DESTINATION.MESSAGE_TYPE,INTERNAL_DESTINATION.VERSION_NUM,INTERNAL_DESTINATION.UPDATED_BY_USER_ID,INTERNAL_DESTINATION.UPDATED_TIME,INTERNAL_DESTINATION.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from INTERNAL_DESTINATION where    INTERNAL_DESTINATION_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into INTERNAL_DESTINATION ( INTERNAL_DESTINATION_ID,VIS_ID,DESCRIPTION,MESSAGE_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update INTERNAL_DESTINATION_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from INTERNAL_DESTINATION_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_IDENTIFIER = "select count(*) from INTERNAL_DESTINATION where    VIS_ID = ? and not(    INTERNAL_DESTINATION_ID = ? )";
/*      */   protected static final String SQL_STORE = "update INTERNAL_DESTINATION set VIS_ID = ?,DESCRIPTION = ?,MESSAGE_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    INTERNAL_DESTINATION_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from INTERNAL_DESTINATION where INTERNAL_DESTINATION_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from INTERNAL_DESTINATION where    INTERNAL_DESTINATION_ID = ? ";
/*  710 */   protected static String SQL_ALL_INTERNAL_DESTINATIONS = "select 0       ,INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID      ,INTERNAL_DESTINATION.VIS_ID      ,INTERNAL_DESTINATION.DESCRIPTION from INTERNAL_DESTINATION where 1=1 ";
/*      */ 
/*  790 */   protected static String SQL_ALL_INTERNAL_DESTINATION_DETAILS = "select 0       ,INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID      ,INTERNAL_DESTINATION.VIS_ID      ,INTERNAL_DESTINATION.DESCRIPTION      ,INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID from INTERNAL_DESTINATION where 1=1 ";
/*      */ 
/*  873 */   protected static String SQL_ALL_USERS_FOR_INTERNAL_DESTINATION_ID = "select 0       ,INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID      ,INTERNAL_DESTINATION.VIS_ID      ,USR.USER_ID      ,USR.NAME      ,INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID      ,INTERNAL_DESTINATION_USERS.USER_ID      ,INTERNAL_DESTINATION.DESCRIPTION      ,INTERNAL_DESTINATION.MESSAGE_TYPE      ,USR.NAME      ,USR.FULL_NAME from INTERNAL_DESTINATION    ,USR    ,INTERNAL_DESTINATION_USERS where 1=1  and  INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID = ? and INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID = INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID and INTERNAL_DESTINATION_USERS.USER_ID = USR.USER_ID";
/*      */ 
/*  998 */   private static String[][] SQL_DELETE_CHILDREN = { { "INTERNAL_DESTINATION_USERS", "delete from INTERNAL_DESTINATION_USERS where     INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID = ? " } };
/*      */ 
/* 1007 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/* 1011 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from INTERNAL_DESTINATION where   INTERNAL_DESTINATION_ID = ?";
/*      */   public static final String SQL_GET_INTERNAL_DESTINATION_USERS_REF = "select 0,INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID from INTERNAL_DESTINATION_USERS,INTERNAL_DESTINATION where 1=1 and INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID = ? and INTERNAL_DESTINATION_USERS.USER_ID = ? and INTERNAL_DESTINATION_USERS.INTERNAL_DESTINATION_ID = INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID";
/*      */   public static final String SQL_GET_INTERNAL_DESTINATION_DISTINCT_USERS = "select distinct    u.user_id,    u.name,    u.full_name,    u.E_MAIL_ADDRESS  from    INTERNAL_DESTINATION i,    INTERNAL_DESTINATION_users idu,    usr u  where    i.internal_destination_id = idu.internal_destination_id    and idu.user_id = u.user_id    and i.vis_id in (";
/*      */   protected InternalDestinationUsersDAO mInternalDestinationUsersDAO;
/*      */   protected InternalDestinationEVO mDetails;
/*      */ 
/*      */   public InternalDestinationDAO(Connection connection)
/*      */   {
/*   45 */     super(connection);
/*      */   }
/*      */ 
/*      */   public InternalDestinationDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public InternalDestinationDAO(DataSource ds)
/*      */   {
/*   61 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected InternalDestinationPK getPK()
/*      */   {
/*   69 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(InternalDestinationEVO details)
/*      */   {
/*   78 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public InternalDestinationEVO setAndGetDetails(InternalDestinationEVO details, String dependants)
/*      */   {
/*   89 */     setDetails(details);
/*   90 */     generateKeys();
/*   91 */     getDependants(this.mDetails, dependants);
/*   92 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public InternalDestinationPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  101 */     doCreate();
/*      */ 
/*  103 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(InternalDestinationPK pk)
/*      */     throws ValidationException
/*      */   {
/*  113 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  122 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  131 */     doRemove();
/*      */   }
/*      */ 
/*      */   public InternalDestinationPK findByPrimaryKey(InternalDestinationPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  140 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  141 */     if (exists(pk_))
/*      */     {
/*  143 */       if (timer != null) {
/*  144 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  146 */       return pk_;
/*      */     }
/*      */ 
/*  149 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(InternalDestinationPK pk)
/*      */   {
/*  167 */     PreparedStatement stmt = null;
/*  168 */     ResultSet resultSet = null;
/*  169 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  173 */       stmt = getConnection().prepareStatement("select INTERNAL_DESTINATION_ID from INTERNAL_DESTINATION where    INTERNAL_DESTINATION_ID = ? ");
/*      */ 
/*  175 */       int col = 1;
/*  176 */       stmt.setInt(col++, pk.getInternalDestinationId());
/*      */ 
/*  178 */       resultSet = stmt.executeQuery();
/*      */ 
/*  180 */       if (!resultSet.next())
/*  181 */         returnValue = false;
/*      */       else
/*  183 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  187 */       throw handleSQLException(pk, "select INTERNAL_DESTINATION_ID from INTERNAL_DESTINATION where    INTERNAL_DESTINATION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  191 */       closeResultSet(resultSet);
/*  192 */       closeStatement(stmt);
/*  193 */       closeConnection();
/*      */     }
/*  195 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private InternalDestinationEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  214 */     int col = 1;
/*  215 */     InternalDestinationEVO evo = new InternalDestinationEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  224 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  225 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  226 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  227 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(InternalDestinationEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  232 */     int col = startCol_;
/*  233 */     stmt_.setInt(col++, evo_.getInternalDestinationId());
/*  234 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(InternalDestinationEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  239 */     int col = startCol_;
/*  240 */     stmt_.setString(col++, evo_.getVisId());
/*  241 */     stmt_.setString(col++, evo_.getDescription());
/*  242 */     stmt_.setInt(col++, evo_.getMessageType());
/*  243 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  244 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  245 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  246 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  247 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(InternalDestinationPK pk)
/*      */     throws ValidationException
/*      */   {
/*  263 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  265 */     PreparedStatement stmt = null;
/*  266 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  270 */       stmt = getConnection().prepareStatement("select INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID,INTERNAL_DESTINATION.VIS_ID,INTERNAL_DESTINATION.DESCRIPTION,INTERNAL_DESTINATION.MESSAGE_TYPE,INTERNAL_DESTINATION.VERSION_NUM,INTERNAL_DESTINATION.UPDATED_BY_USER_ID,INTERNAL_DESTINATION.UPDATED_TIME,INTERNAL_DESTINATION.CREATED_TIME from INTERNAL_DESTINATION where    INTERNAL_DESTINATION_ID = ? ");
/*      */ 
/*  273 */       int col = 1;
/*  274 */       stmt.setInt(col++, pk.getInternalDestinationId());
/*      */ 
/*  276 */       resultSet = stmt.executeQuery();
/*      */ 
/*  278 */       if (!resultSet.next()) {
/*  279 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  282 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  283 */       if (this.mDetails.isModified())
/*  284 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  288 */       throw handleSQLException(pk, "select INTERNAL_DESTINATION.INTERNAL_DESTINATION_ID,INTERNAL_DESTINATION.VIS_ID,INTERNAL_DESTINATION.DESCRIPTION,INTERNAL_DESTINATION.MESSAGE_TYPE,INTERNAL_DESTINATION.VERSION_NUM,INTERNAL_DESTINATION.UPDATED_BY_USER_ID,INTERNAL_DESTINATION.UPDATED_TIME,INTERNAL_DESTINATION.CREATED_TIME from INTERNAL_DESTINATION where    INTERNAL_DESTINATION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  292 */       closeResultSet(resultSet);
/*  293 */       closeStatement(stmt);
/*  294 */       closeConnection();
/*      */ 
/*  296 */       if (timer != null)
/*  297 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  330 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  331 */     generateKeys();
/*      */ 
/*  333 */     this.mDetails.postCreateInit();
/*      */ 
/*  335 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  340 */       duplicateValueCheckIdentifier();
/*      */ 
/*  342 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  343 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  344 */       stmt = getConnection().prepareStatement("insert into INTERNAL_DESTINATION ( INTERNAL_DESTINATION_ID,VIS_ID,DESCRIPTION,MESSAGE_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");
/*      */ 
/*  347 */       int col = 1;
/*  348 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  349 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  352 */       int resultCount = stmt.executeUpdate();
/*  353 */       if (resultCount != 1)
/*      */       {
/*  355 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  358 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  362 */       throw handleSQLException(this.mDetails.getPK(), "insert into INTERNAL_DESTINATION ( INTERNAL_DESTINATION_ID,VIS_ID,DESCRIPTION,MESSAGE_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  366 */       closeStatement(stmt);
/*  367 */       closeConnection();
/*      */ 
/*  369 */       if (timer != null) {
/*  370 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  376 */       getInternalDestinationUsersDAO().update(this.mDetails.getInternalUserListMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  382 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  402 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  404 */     PreparedStatement stmt = null;
/*  405 */     ResultSet resultSet = null;
/*  406 */     String sqlString = null;
/*      */     try
/*      */     {
/*  411 */       sqlString = "update INTERNAL_DESTINATION_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  412 */       stmt = getConnection().prepareStatement("update INTERNAL_DESTINATION_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  413 */       stmt.setInt(1, insertCount);
/*      */ 
/*  415 */       int resultCount = stmt.executeUpdate();
/*  416 */       if (resultCount != 1) {
/*  417 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  419 */       closeStatement(stmt);
/*      */ 
/*  422 */       sqlString = "select SEQ_NUM from INTERNAL_DESTINATION_SEQ";
/*  423 */       stmt = getConnection().prepareStatement("select SEQ_NUM from INTERNAL_DESTINATION_SEQ");
/*  424 */       resultSet = stmt.executeQuery();
/*  425 */       if (!resultSet.next())
/*  426 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  427 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  429 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  433 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  437 */       closeResultSet(resultSet);
/*  438 */       closeStatement(stmt);
/*  439 */       closeConnection();
/*      */ 
/*  441 */       if (timer != null)
/*  442 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  442 */     }
/*      */   }
/*      */ 
/*      */   public InternalDestinationPK generateKeys()
/*      */   {
/*  452 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  454 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  457 */     if (insertCount == 0) {
/*  458 */       return this.mDetails.getPK();
/*      */     }
/*  460 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  462 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckIdentifier()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  475 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  476 */     PreparedStatement stmt = null;
/*  477 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  481 */       stmt = getConnection().prepareStatement("select count(*) from INTERNAL_DESTINATION where    VIS_ID = ? and not(    INTERNAL_DESTINATION_ID = ? )");
/*      */ 
/*  484 */       int col = 1;
/*  485 */       stmt.setString(col++, this.mDetails.getVisId());
/*  486 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  489 */       resultSet = stmt.executeQuery();
/*      */ 
/*  491 */       if (!resultSet.next()) {
/*  492 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  496 */       col = 1;
/*  497 */       int count = resultSet.getInt(col++);
/*  498 */       if (count > 0) {
/*  499 */         //throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " Identifier");
                   throw new DuplicateNameValidationException("Duplicate name (" + this.mDetails.getVisId() + "). Please re-name and retry");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  505 */       throw handleSQLException(getPK(), "select count(*) from INTERNAL_DESTINATION where    VIS_ID = ? and not(    INTERNAL_DESTINATION_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  509 */       closeResultSet(resultSet);
/*  510 */       closeStatement(stmt);
/*  511 */       closeConnection();
/*      */ 
/*  513 */       if (timer != null)
/*  514 */         timer.logDebug("duplicateValueCheckIdentifier", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  540 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  542 */     generateKeys();
/*      */ 
/*  547 */     PreparedStatement stmt = null;
/*      */ 
/*  549 */     boolean mainChanged = this.mDetails.isModified();
/*  550 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  554 */       if (mainChanged) {
/*  555 */         duplicateValueCheckIdentifier();
/*      */       }
/*  557 */       if (getInternalDestinationUsersDAO().update(this.mDetails.getInternalUserListMap())) {
/*  558 */         dependantChanged = true;
/*      */       }
/*  560 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  563 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  566 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  567 */         stmt = getConnection().prepareStatement("update INTERNAL_DESTINATION set VIS_ID = ?,DESCRIPTION = ?,MESSAGE_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    INTERNAL_DESTINATION_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  570 */         int col = 1;
/*  571 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  572 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  574 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  577 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  579 */         if (resultCount == 0) {
/*  580 */           checkVersionNum();
/*      */         }
/*  582 */         if (resultCount != 1) {
/*  583 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  586 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  595 */       throw handleSQLException(getPK(), "update INTERNAL_DESTINATION set VIS_ID = ?,DESCRIPTION = ?,MESSAGE_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    INTERNAL_DESTINATION_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  599 */       closeStatement(stmt);
/*  600 */       closeConnection();
/*      */ 
/*  602 */       if ((timer != null) && (
/*  603 */         (mainChanged) || (dependantChanged)))
/*  604 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  616 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  617 */     PreparedStatement stmt = null;
/*  618 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  622 */       stmt = getConnection().prepareStatement("select VERSION_NUM from INTERNAL_DESTINATION where INTERNAL_DESTINATION_ID = ?");
/*      */ 
/*  625 */       int col = 1;
/*  626 */       stmt.setInt(col++, this.mDetails.getInternalDestinationId());
/*      */ 
/*  629 */       resultSet = stmt.executeQuery();
/*      */ 
/*  631 */       if (!resultSet.next()) {
/*  632 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  635 */       col = 1;
/*  636 */       int dbVersionNumber = resultSet.getInt(col++);
/*  637 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  638 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  644 */       throw handleSQLException(getPK(), "select VERSION_NUM from INTERNAL_DESTINATION where INTERNAL_DESTINATION_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  648 */       closeStatement(stmt);
/*  649 */       closeResultSet(resultSet);
/*      */ 
/*  651 */       if (timer != null)
/*  652 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  669 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  670 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  675 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  680 */       stmt = getConnection().prepareStatement("delete from INTERNAL_DESTINATION where    INTERNAL_DESTINATION_ID = ? ");
/*      */ 
/*  683 */       int col = 1;
/*  684 */       stmt.setInt(col++, this.mDetails.getInternalDestinationId());
/*      */ 
/*  686 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  688 */       if (resultCount != 1) {
/*  689 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  695 */       throw handleSQLException(getPK(), "delete from INTERNAL_DESTINATION where    INTERNAL_DESTINATION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  699 */       closeStatement(stmt);
/*  700 */       closeConnection();
/*      */ 
/*  702 */       if (timer != null)
/*  703 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllInternalDestinationsELO getAllInternalDestinations()
/*      */   {
/*  733 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  734 */     PreparedStatement stmt = null;
/*  735 */     ResultSet resultSet = null;
/*  736 */     AllInternalDestinationsELO results = new AllInternalDestinationsELO();
/*      */     try
/*      */     {
/*  739 */       stmt = getConnection().prepareStatement(SQL_ALL_INTERNAL_DESTINATIONS);
/*  740 */       int col = 1;
/*  741 */       resultSet = stmt.executeQuery();
/*  742 */       while (resultSet.next())
/*      */       {
/*  744 */         col = 2;
/*      */ 
/*  747 */         InternalDestinationPK pkInternalDestination = new InternalDestinationPK(resultSet.getInt(col++));
/*      */ 
/*  750 */         String textInternalDestination = resultSet.getString(col++);
/*      */ 
/*  754 */         InternalDestinationRefImpl erInternalDestination = new InternalDestinationRefImpl(pkInternalDestination, textInternalDestination);
/*      */ 
/*  759 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  762 */         results.add(erInternalDestination, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  770 */       throw handleSQLException(SQL_ALL_INTERNAL_DESTINATIONS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  774 */       closeResultSet(resultSet);
/*  775 */       closeStatement(stmt);
/*  776 */       closeConnection();
/*      */     }
/*      */ 
/*  779 */     if (timer != null) {
/*  780 */       timer.logDebug("getAllInternalDestinations", " items=" + results.size());
/*      */     }
/*      */ 
/*  784 */     return results;
/*      */   }
/*      */ 
/*      */   public AllInternalDestinationDetailsELO getAllInternalDestinationDetails()
/*      */   {
/*  814 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  815 */     PreparedStatement stmt = null;
/*  816 */     ResultSet resultSet = null;
/*  817 */     AllInternalDestinationDetailsELO results = new AllInternalDestinationDetailsELO();
/*      */     try
/*      */     {
/*  820 */       stmt = getConnection().prepareStatement(SQL_ALL_INTERNAL_DESTINATION_DETAILS);
/*  821 */       int col = 1;
/*  822 */       resultSet = stmt.executeQuery();
/*  823 */       while (resultSet.next())
/*      */       {
/*  825 */         col = 2;
/*      */ 
/*  828 */         InternalDestinationPK pkInternalDestination = new InternalDestinationPK(resultSet.getInt(col++));
/*      */ 
/*  831 */         String textInternalDestination = resultSet.getString(col++);
/*      */ 
/*  835 */         InternalDestinationRefImpl erInternalDestination = new InternalDestinationRefImpl(pkInternalDestination, textInternalDestination);
/*      */ 
/*  840 */         String col1 = resultSet.getString(col++);
/*  841 */         int col2 = resultSet.getInt(col++);
/*      */ 
/*  844 */         results.add(erInternalDestination, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  853 */       throw handleSQLException(SQL_ALL_INTERNAL_DESTINATION_DETAILS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  857 */       closeResultSet(resultSet);
/*  858 */       closeStatement(stmt);
/*  859 */       closeConnection();
/*      */     }
/*      */ 
/*  862 */     if (timer != null) {
/*  863 */       timer.logDebug("getAllInternalDestinationDetails", " items=" + results.size());
/*      */     }
/*      */ 
/*  867 */     return results;
/*      */   }
/*      */ 
/*      */   public AllUsersForInternalDestinationIdELO getAllUsersForInternalDestinationId(int param1)
/*      */   {
/*  909 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  910 */     PreparedStatement stmt = null;
/*  911 */     ResultSet resultSet = null;
/*  912 */     AllUsersForInternalDestinationIdELO results = new AllUsersForInternalDestinationIdELO();
/*      */     try
/*      */     {
/*  915 */       stmt = getConnection().prepareStatement(SQL_ALL_USERS_FOR_INTERNAL_DESTINATION_ID);
/*  916 */       int col = 1;
/*  917 */       stmt.setInt(col++, param1);
/*  918 */       resultSet = stmt.executeQuery();
/*  919 */       while (resultSet.next())
/*      */       {
/*  921 */         col = 2;
/*      */ 
/*  924 */         InternalDestinationPK pkInternalDestination = new InternalDestinationPK(resultSet.getInt(col++));
/*      */ 
/*  927 */         String textInternalDestination = resultSet.getString(col++);
/*      */ 
/*  930 */         UserPK pkUser = new UserPK(resultSet.getInt(col++));
/*      */ 
/*  933 */         String textUser = resultSet.getString(col++);
/*      */ 
/*  935 */         InternalDestinationUsersPK pkInternalDestinationUsers = new InternalDestinationUsersPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  939 */         String textInternalDestinationUsers = "";
/*      */ 
/*  942 */         InternalDestinationRefImpl erInternalDestination = new InternalDestinationRefImpl(pkInternalDestination, textInternalDestination);
/*      */ 
/*  948 */         UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
/*      */ 
/*  954 */         InternalDestinationUsersRefImpl erInternalDestinationUsers = new InternalDestinationUsersRefImpl(pkInternalDestinationUsers, textInternalDestinationUsers);
/*      */ 
/*  959 */         String col1 = resultSet.getString(col++);
/*  960 */         int col2 = resultSet.getInt(col++);
/*  961 */         String col3 = resultSet.getString(col++);
/*  962 */         String col4 = resultSet.getString(col++);
/*      */ 
/*  965 */         results.add(erInternalDestination, erUser, erInternalDestinationUsers, col1, col2, col3, col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  978 */       throw handleSQLException(SQL_ALL_USERS_FOR_INTERNAL_DESTINATION_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  982 */       closeResultSet(resultSet);
/*  983 */       closeStatement(stmt);
/*  984 */       closeConnection();
/*      */     }
/*      */ 
/*  987 */     if (timer != null) {
/*  988 */       timer.logDebug("getAllUsersForInternalDestinationId", " InternalDestinationId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  993 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(InternalDestinationPK pk)
/*      */   {
/* 1020 */     Set emptyStrings = Collections.emptySet();
/* 1021 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(InternalDestinationPK pk, Set<String> exclusionTables)
/*      */   {
/* 1027 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1029 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1031 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1033 */       PreparedStatement stmt = null;
/*      */ 
/* 1035 */       int resultCount = 0;
/* 1036 */       String s = null;
/*      */       try
/*      */       {
/* 1039 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 1041 */         if (this._log.isDebugEnabled()) {
/* 1042 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1044 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1047 */         int col = 1;
/* 1048 */         stmt.setInt(col++, pk.getInternalDestinationId());
/*      */ 
/* 1051 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1055 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1059 */         closeStatement(stmt);
/* 1060 */         closeConnection();
/*      */ 
/* 1062 */         if (timer != null) {
/* 1063 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1067 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1069 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1071 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1073 */       PreparedStatement stmt = null;
/*      */ 
/* 1075 */       int resultCount = 0;
/* 1076 */       String s = null;
/*      */       try
/*      */       {
/* 1079 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1081 */         if (this._log.isDebugEnabled()) {
/* 1082 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1084 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1087 */         int col = 1;
/* 1088 */         stmt.setInt(col++, pk.getInternalDestinationId());
/*      */ 
/* 1091 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1095 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1099 */         closeStatement(stmt);
/* 1100 */         closeConnection();
/*      */ 
/* 1102 */         if (timer != null)
/* 1103 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public InternalDestinationEVO getDetails(InternalDestinationPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1122 */     return getDetails(new InternalDestinationCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public InternalDestinationEVO getDetails(InternalDestinationCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1139 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1142 */     if (this.mDetails == null) {
/* 1143 */       doLoad(paramCK.getInternalDestinationPK());
/*      */     }
/* 1145 */     else if (!this.mDetails.getPK().equals(paramCK.getInternalDestinationPK())) {
/* 1146 */       doLoad(paramCK.getInternalDestinationPK());
/*      */     }
/* 1148 */     else if (!checkIfValid())
/*      */     {
/* 1150 */       this._log.info("getDetails", "[ALERT] InternalDestinationEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1152 */       doLoad(paramCK.getInternalDestinationPK());
/*      */     }
/*      */ 
/* 1162 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isInternalUserListAllItemsLoaded()))
/*      */     {
/* 1167 */       this.mDetails.setInternalUserList(getInternalDestinationUsersDAO().getAll(this.mDetails.getInternalDestinationId(), dependants, this.mDetails.getInternalUserList()));
/*      */ 
/* 1174 */       this.mDetails.setInternalUserListAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1177 */     if ((paramCK instanceof InternalDestinationUsersCK))
/*      */     {
/* 1179 */       if (this.mDetails.getInternalUserList() == null) {
/* 1180 */         this.mDetails.loadInternalUserListItem(getInternalDestinationUsersDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1183 */         InternalDestinationUsersPK pk = ((InternalDestinationUsersCK)paramCK).getInternalDestinationUsersPK();
/* 1184 */         InternalDestinationUsersEVO evo = this.mDetails.getInternalUserListItem(pk);
/* 1185 */         if (evo == null) {
/* 1186 */           this.mDetails.loadInternalUserListItem(getInternalDestinationUsersDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1191 */     InternalDestinationEVO details = new InternalDestinationEVO();
/* 1192 */     details = this.mDetails.deepClone();
/*      */ 
/* 1194 */     if (timer != null) {
/* 1195 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1197 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1207 */     boolean stillValid = false;
/* 1208 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1209 */     PreparedStatement stmt = null;
/* 1210 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1213 */       stmt = getConnection().prepareStatement("select VERSION_NUM from INTERNAL_DESTINATION where   INTERNAL_DESTINATION_ID = ?");
/* 1214 */       int col = 1;
/* 1215 */       stmt.setInt(col++, this.mDetails.getInternalDestinationId());
/*      */ 
/* 1217 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1219 */       if (!resultSet.next()) {
/* 1220 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1222 */       col = 1;
/* 1223 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1225 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1226 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1230 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from INTERNAL_DESTINATION where   INTERNAL_DESTINATION_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1234 */       closeResultSet(resultSet);
/* 1235 */       closeStatement(stmt);
/* 1236 */       closeConnection();
/*      */ 
/* 1238 */       if (timer != null) {
/* 1239 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1242 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public InternalDestinationEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1248 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1250 */     if (!checkIfValid())
/*      */     {
/* 1252 */       this._log.info("getDetails", "InternalDestination " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1253 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1257 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1260 */     InternalDestinationEVO details = this.mDetails.deepClone();
/*      */ 
/* 1262 */     if (timer != null) {
/* 1263 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1265 */     return details;
/*      */   }
/*      */ 
/*      */   protected InternalDestinationUsersDAO getInternalDestinationUsersDAO()
/*      */   {
/* 1274 */     if (this.mInternalDestinationUsersDAO == null)
/*      */     {
/* 1276 */       if (this.mDataSource != null)
/* 1277 */         this.mInternalDestinationUsersDAO = new InternalDestinationUsersDAO(this.mDataSource);
/*      */       else {
/* 1279 */         this.mInternalDestinationUsersDAO = new InternalDestinationUsersDAO(getConnection());
/*      */       }
/*      */     }
/* 1282 */     return this.mInternalDestinationUsersDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1287 */     return "InternalDestination";
/*      */   }
/*      */ 
/*      */   public InternalDestinationRef getRef(InternalDestinationPK paramInternalDestinationPK)
/*      */     throws ValidationException
/*      */   {
/* 1293 */     InternalDestinationEVO evo = getDetails(paramInternalDestinationPK, "");
/* 1294 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1319 */     if (c == null)
/* 1320 */       return;
/* 1321 */     Iterator iter = c.iterator();
/* 1322 */     while (iter.hasNext())
/*      */     {
/* 1324 */       InternalDestinationEVO evo = (InternalDestinationEVO)iter.next();
/* 1325 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(InternalDestinationEVO evo, String dependants)
/*      */   {
/* 1339 */     if (evo.getInternalDestinationId() < 1) {
/* 1340 */       return;
/*      */     }
/*      */ 
/* 1348 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1351 */       if (!evo.isInternalUserListAllItemsLoaded())
/*      */       {
/* 1353 */         evo.setInternalUserList(getInternalDestinationUsersDAO().getAll(evo.getInternalDestinationId(), dependants, evo.getInternalUserList()));
/*      */ 
/* 1360 */         evo.setInternalUserListAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllNonDisabledUsersELO getDistinctInternalDestinationUsers(String[] visIds)
/*      */   {
/* 1385 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1386 */     PreparedStatement stmt = null;
/* 1387 */     ResultSet resultSet = null;
/* 1388 */     AllNonDisabledUsersELO results = new AllNonDisabledUsersELO();
/*      */     try
/*      */     {
/* 1391 */       StringBuffer sql = new StringBuffer("select distinct    u.user_id,    u.name,    u.full_name,    u.E_MAIL_ADDRESS  from    INTERNAL_DESTINATION i,    INTERNAL_DESTINATION_users idu,    usr u  where    i.internal_destination_id = idu.internal_destination_id    and idu.user_id = u.user_id    and i.vis_id in (");
/* 1392 */       for (int i = 0; i < visIds.length; i++)
/*      */       {
/* 1394 */         if (i != 0)
/* 1395 */           sql.append(",");
/* 1396 */         sql.append("?");
/*      */       }
/* 1398 */       sql.append(")");
/* 1399 */       stmt = getConnection().prepareStatement(sql.toString());
/*      */ 
/* 1401 */       int col = 1;
/* 1402 */       for (int i = 0; i < visIds.length; i++)
/*      */       {
/* 1404 */         stmt.setString(col, visIds[i].trim());
/* 1405 */         col++;
/*      */       }
/*      */ 
/* 1408 */       resultSet = stmt.executeQuery();
/* 1409 */       while (resultSet.next())
/*      */       {
/* 1411 */         col = 1;
/*      */ 
/* 1414 */         UserPK pkUser = new UserPK(resultSet.getInt(col++));
/*      */ 
/* 1417 */         String textUser = resultSet.getString(col++);
/*      */ 
/* 1421 */         UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
/*      */ 
/* 1426 */         String col1 = resultSet.getString(col++);
/* 1427 */         String col2 = resultSet.getString(col++);
/*      */ 
/* 1430 */         results.add(erUser, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1439 */       throw handleSQLException("select distinct    u.user_id,    u.name,    u.full_name,    u.E_MAIL_ADDRESS  from    INTERNAL_DESTINATION i,    INTERNAL_DESTINATION_users idu,    usr u  where    i.internal_destination_id = idu.internal_destination_id    and idu.user_id = u.user_id    and i.vis_id in (", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1443 */       closeResultSet(resultSet);
/* 1444 */       closeStatement(stmt);
/* 1445 */       closeConnection();
/*      */     }
/*      */ 
/* 1448 */     if (timer != null) {
/* 1449 */       timer.logDebug("getAllNonDisabledUsers", " items=" + results.size());
/*      */     }
/*      */ 
/* 1453 */     return results;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationDAO
 * JD-Core Version:    0.6.0
 */