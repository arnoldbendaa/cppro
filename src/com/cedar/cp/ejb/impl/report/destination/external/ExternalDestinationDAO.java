/*      */ package com.cedar.cp.ejb.impl.report.destination.external;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.report.destination.external.ExternalDestinationRef;
/*      */ import com.cedar.cp.dto.report.destination.external.AllExternalDestinationDetailsELO;
/*      */ import com.cedar.cp.dto.report.destination.external.AllExternalDestinationsELO;
/*      */ import com.cedar.cp.dto.report.destination.external.AllUsersForExternalDestinationIdELO;
/*      */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationCK;
/*      */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
/*      */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationRefImpl;
/*      */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersCK;
/*      */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersPK;
/*      */ import com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersRefImpl;
/*      */ import com.cedar.cp.dto.user.AllNonDisabledUsersELO;
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
/*      */ public class ExternalDestinationDAO extends AbstractDAO
/*      */ {
/*   36 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select EXTERNAL_DESTINATION_ID from EXTERNAL_DESTINATION where    EXTERNAL_DESTINATION_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID,EXTERNAL_DESTINATION.VIS_ID,EXTERNAL_DESTINATION.DESCRIPTION,EXTERNAL_DESTINATION.VERSION_NUM,EXTERNAL_DESTINATION.UPDATED_BY_USER_ID,EXTERNAL_DESTINATION.UPDATED_TIME,EXTERNAL_DESTINATION.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from EXTERNAL_DESTINATION where    EXTERNAL_DESTINATION_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into EXTERNAL_DESTINATION ( EXTERNAL_DESTINATION_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update EXTERNAL_DESTINATION_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from EXTERNAL_DESTINATION_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_IDENTIFIER = "select count(*) from EXTERNAL_DESTINATION where    VIS_ID = ? and not(    EXTERNAL_DESTINATION_ID = ? )";
/*      */   protected static final String SQL_STORE = "update EXTERNAL_DESTINATION set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    EXTERNAL_DESTINATION_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from EXTERNAL_DESTINATION where EXTERNAL_DESTINATION_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from EXTERNAL_DESTINATION where    EXTERNAL_DESTINATION_ID = ? ";
/*  702 */   protected static String SQL_ALL_EXTERNAL_DESTINATIONS = "select 0       ,EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID      ,EXTERNAL_DESTINATION.VIS_ID      ,EXTERNAL_DESTINATION.DESCRIPTION from EXTERNAL_DESTINATION where 1=1 ";
/*      */ 
/*  782 */   protected static String SQL_ALL_EXTERNAL_DESTINATION_DETAILS = "select 0       ,EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID      ,EXTERNAL_DESTINATION.VIS_ID      ,EXTERNAL_DESTINATION.DESCRIPTION      ,EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID from EXTERNAL_DESTINATION where 1=1 ";
/*      */ 
/*  865 */   protected static String SQL_ALL_USERS_FOR_EXTERNAL_DESTINATION_ID = "select 0       ,EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID      ,EXTERNAL_DESTINATION.VIS_ID      ,EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID      ,EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID      ,EXTERNAL_DESTINATION.DESCRIPTION      ,EXTERNAL_DESTINATION_USERS.EMAIL_ADDRESS from EXTERNAL_DESTINATION    ,EXTERNAL_DESTINATION_USERS where 1=1  and  EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID = ? and EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID = EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID";
/*      */ 
/*  968 */   private static String[][] SQL_DELETE_CHILDREN = { { "EXTERNAL_DESTINATION_USERS", "delete from EXTERNAL_DESTINATION_USERS where     EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID = ? " } };
/*      */ 
/*  977 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  981 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from EXTERNAL_DESTINATION where   EXTERNAL_DESTINATION_ID = ?";
/*      */   public static final String SQL_GET_EXTERNAL_DESTINATION_USERS_REF = "select 0,EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID from EXTERNAL_DESTINATION_USERS,EXTERNAL_DESTINATION where 1=1 and EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID = ? and EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_USERS_ID = ? and EXTERNAL_DESTINATION_USERS.EXTERNAL_DESTINATION_ID = EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID";
/*      */   public static final String SQL_GET_EXTERNAL_DESTINATION_DISTINCT_USERS = "select distinct    edu.EMAIL_ADDRESS  from    EXTERNAL_DESTINATION e,    EXTERNAL_DESTINATION_USERS edu  where    e.external_destination_id = edu.external_destination_id    and e.vis_id in (";
/*      */   protected ExternalDestinationUsersDAO mExternalDestinationUsersDAO;
/*      */   protected ExternalDestinationEVO mDetails;
/*      */ 
/*      */   public ExternalDestinationDAO(Connection connection)
/*      */   {
/*   43 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ExternalDestinationDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ExternalDestinationDAO(DataSource ds)
/*      */   {
/*   59 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ExternalDestinationPK getPK()
/*      */   {
/*   67 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ExternalDestinationEVO details)
/*      */   {
/*   76 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public ExternalDestinationEVO setAndGetDetails(ExternalDestinationEVO details, String dependants)
/*      */   {
/*   87 */     setDetails(details);
/*   88 */     generateKeys();
/*   89 */     getDependants(this.mDetails, dependants);
/*   90 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public ExternalDestinationPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   99 */     doCreate();
/*      */ 
/*  101 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(ExternalDestinationPK pk)
/*      */     throws ValidationException
/*      */   {
/*  111 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  120 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  129 */     doRemove();
/*      */   }
/*      */ 
/*      */   public ExternalDestinationPK findByPrimaryKey(ExternalDestinationPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  138 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  139 */     if (exists(pk_))
/*      */     {
/*  141 */       if (timer != null) {
/*  142 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  144 */       return pk_;
/*      */     }
/*      */ 
/*  147 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(ExternalDestinationPK pk)
/*      */   {
/*  165 */     PreparedStatement stmt = null;
/*  166 */     ResultSet resultSet = null;
/*  167 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  171 */       stmt = getConnection().prepareStatement("select EXTERNAL_DESTINATION_ID from EXTERNAL_DESTINATION where    EXTERNAL_DESTINATION_ID = ? ");
/*      */ 
/*  173 */       int col = 1;
/*  174 */       stmt.setInt(col++, pk.getExternalDestinationId());
/*      */ 
/*  176 */       resultSet = stmt.executeQuery();
/*      */ 
/*  178 */       if (!resultSet.next())
/*  179 */         returnValue = false;
/*      */       else
/*  181 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  185 */       throw handleSQLException(pk, "select EXTERNAL_DESTINATION_ID from EXTERNAL_DESTINATION where    EXTERNAL_DESTINATION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  189 */       closeResultSet(resultSet);
/*  190 */       closeStatement(stmt);
/*  191 */       closeConnection();
/*      */     }
/*  193 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private ExternalDestinationEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  211 */     int col = 1;
/*  212 */     ExternalDestinationEVO evo = new ExternalDestinationEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  220 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  221 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  222 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  223 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ExternalDestinationEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  228 */     int col = startCol_;
/*  229 */     stmt_.setInt(col++, evo_.getExternalDestinationId());
/*  230 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ExternalDestinationEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  235 */     int col = startCol_;
/*  236 */     stmt_.setString(col++, evo_.getVisId());
/*  237 */     stmt_.setString(col++, evo_.getDescription());
/*  238 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  239 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  240 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  241 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  242 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ExternalDestinationPK pk)
/*      */     throws ValidationException
/*      */   {
/*  258 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  260 */     PreparedStatement stmt = null;
/*  261 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  265 */       stmt = getConnection().prepareStatement("select EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID,EXTERNAL_DESTINATION.VIS_ID,EXTERNAL_DESTINATION.DESCRIPTION,EXTERNAL_DESTINATION.VERSION_NUM,EXTERNAL_DESTINATION.UPDATED_BY_USER_ID,EXTERNAL_DESTINATION.UPDATED_TIME,EXTERNAL_DESTINATION.CREATED_TIME from EXTERNAL_DESTINATION where    EXTERNAL_DESTINATION_ID = ? ");
/*      */ 
/*  268 */       int col = 1;
/*  269 */       stmt.setInt(col++, pk.getExternalDestinationId());
/*      */ 
/*  271 */       resultSet = stmt.executeQuery();
/*      */ 
/*  273 */       if (!resultSet.next()) {
/*  274 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  277 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  278 */       if (this.mDetails.isModified())
/*  279 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  283 */       throw handleSQLException(pk, "select EXTERNAL_DESTINATION.EXTERNAL_DESTINATION_ID,EXTERNAL_DESTINATION.VIS_ID,EXTERNAL_DESTINATION.DESCRIPTION,EXTERNAL_DESTINATION.VERSION_NUM,EXTERNAL_DESTINATION.UPDATED_BY_USER_ID,EXTERNAL_DESTINATION.UPDATED_TIME,EXTERNAL_DESTINATION.CREATED_TIME from EXTERNAL_DESTINATION where    EXTERNAL_DESTINATION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  287 */       closeResultSet(resultSet);
/*  288 */       closeStatement(stmt);
/*  289 */       closeConnection();
/*      */ 
/*  291 */       if (timer != null)
/*  292 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  323 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  324 */     generateKeys();
/*      */ 
/*  326 */     this.mDetails.postCreateInit();
/*      */ 
/*  328 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  333 */       duplicateValueCheckIdentifier();
/*      */ 
/*  335 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  336 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  337 */       stmt = getConnection().prepareStatement("insert into EXTERNAL_DESTINATION ( EXTERNAL_DESTINATION_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*      */ 
/*  340 */       int col = 1;
/*  341 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  342 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  345 */       int resultCount = stmt.executeUpdate();
/*  346 */       if (resultCount != 1)
/*      */       {
/*  348 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  351 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  355 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXTERNAL_DESTINATION ( EXTERNAL_DESTINATION_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  359 */       closeStatement(stmt);
/*  360 */       closeConnection();
/*      */ 
/*  362 */       if (timer != null) {
/*  363 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  369 */       getExternalDestinationUsersDAO().update(this.mDetails.getExternalUserListMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  375 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  395 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  397 */     PreparedStatement stmt = null;
/*  398 */     ResultSet resultSet = null;
/*  399 */     String sqlString = null;
/*      */     try
/*      */     {
/*  404 */       sqlString = "update EXTERNAL_DESTINATION_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  405 */       stmt = getConnection().prepareStatement("update EXTERNAL_DESTINATION_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  406 */       stmt.setInt(1, insertCount);
/*      */ 
/*  408 */       int resultCount = stmt.executeUpdate();
/*  409 */       if (resultCount != 1) {
/*  410 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  412 */       closeStatement(stmt);
/*      */ 
/*  415 */       sqlString = "select SEQ_NUM from EXTERNAL_DESTINATION_SEQ";
/*  416 */       stmt = getConnection().prepareStatement("select SEQ_NUM from EXTERNAL_DESTINATION_SEQ");
/*  417 */       resultSet = stmt.executeQuery();
/*  418 */       if (!resultSet.next())
/*  419 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  420 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  422 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  426 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  430 */       closeResultSet(resultSet);
/*  431 */       closeStatement(stmt);
/*  432 */       closeConnection();
/*      */ 
/*  434 */       if (timer != null)
/*  435 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  435 */     }
/*      */   }
/*      */ 
/*      */   public ExternalDestinationPK generateKeys()
/*      */   {
/*  445 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  447 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  450 */     if (insertCount == 0) {
/*  451 */       return this.mDetails.getPK();
/*      */     }
/*  453 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  455 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckIdentifier()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  468 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  469 */     PreparedStatement stmt = null;
/*  470 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  474 */       stmt = getConnection().prepareStatement("select count(*) from EXTERNAL_DESTINATION where    VIS_ID = ? and not(    EXTERNAL_DESTINATION_ID = ? )");
/*      */ 
/*  477 */       int col = 1;
/*  478 */       stmt.setString(col++, this.mDetails.getVisId());
/*  479 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  482 */       resultSet = stmt.executeQuery();
/*      */ 
/*  484 */       if (!resultSet.next()) {
/*  485 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  489 */       col = 1;
/*  490 */       int count = resultSet.getInt(col++);
/*  491 */       if (count > 0) {
/*  492 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " Identifier");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  498 */       throw handleSQLException(getPK(), "select count(*) from EXTERNAL_DESTINATION where    VIS_ID = ? and not(    EXTERNAL_DESTINATION_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  502 */       closeResultSet(resultSet);
/*  503 */       closeStatement(stmt);
/*  504 */       closeConnection();
/*      */ 
/*  506 */       if (timer != null)
/*  507 */         timer.logDebug("duplicateValueCheckIdentifier", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  532 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  534 */     generateKeys();
/*      */ 
/*  539 */     PreparedStatement stmt = null;
/*      */ 
/*  541 */     boolean mainChanged = this.mDetails.isModified();
/*  542 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  546 */       if (mainChanged) {
/*  547 */         duplicateValueCheckIdentifier();
/*      */       }
/*  549 */       if (getExternalDestinationUsersDAO().update(this.mDetails.getExternalUserListMap())) {
/*  550 */         dependantChanged = true;
/*      */       }
/*  552 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  555 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  558 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  559 */         stmt = getConnection().prepareStatement("update EXTERNAL_DESTINATION set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    EXTERNAL_DESTINATION_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  562 */         int col = 1;
/*  563 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  564 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  566 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  569 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  571 */         if (resultCount == 0) {
/*  572 */           checkVersionNum();
/*      */         }
/*  574 */         if (resultCount != 1) {
/*  575 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  578 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  587 */       throw handleSQLException(getPK(), "update EXTERNAL_DESTINATION set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    EXTERNAL_DESTINATION_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  591 */       closeStatement(stmt);
/*  592 */       closeConnection();
/*      */ 
/*  594 */       if ((timer != null) && (
/*  595 */         (mainChanged) || (dependantChanged)))
/*  596 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  608 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  609 */     PreparedStatement stmt = null;
/*  610 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  614 */       stmt = getConnection().prepareStatement("select VERSION_NUM from EXTERNAL_DESTINATION where EXTERNAL_DESTINATION_ID = ?");
/*      */ 
/*  617 */       int col = 1;
/*  618 */       stmt.setInt(col++, this.mDetails.getExternalDestinationId());
/*      */ 
/*  621 */       resultSet = stmt.executeQuery();
/*      */ 
/*  623 */       if (!resultSet.next()) {
/*  624 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  627 */       col = 1;
/*  628 */       int dbVersionNumber = resultSet.getInt(col++);
/*  629 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  630 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  636 */       throw handleSQLException(getPK(), "select VERSION_NUM from EXTERNAL_DESTINATION where EXTERNAL_DESTINATION_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  640 */       closeStatement(stmt);
/*  641 */       closeResultSet(resultSet);
/*      */ 
/*  643 */       if (timer != null)
/*  644 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  661 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  662 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  667 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  672 */       stmt = getConnection().prepareStatement("delete from EXTERNAL_DESTINATION where    EXTERNAL_DESTINATION_ID = ? ");
/*      */ 
/*  675 */       int col = 1;
/*  676 */       stmt.setInt(col++, this.mDetails.getExternalDestinationId());
/*      */ 
/*  678 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  680 */       if (resultCount != 1) {
/*  681 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  687 */       throw handleSQLException(getPK(), "delete from EXTERNAL_DESTINATION where    EXTERNAL_DESTINATION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  691 */       closeStatement(stmt);
/*  692 */       closeConnection();
/*      */ 
/*  694 */       if (timer != null)
/*  695 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllExternalDestinationsELO getAllExternalDestinations()
/*      */   {
/*  725 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  726 */     PreparedStatement stmt = null;
/*  727 */     ResultSet resultSet = null;
/*  728 */     AllExternalDestinationsELO results = new AllExternalDestinationsELO();
/*      */     try
/*      */     {
/*  731 */       stmt = getConnection().prepareStatement(SQL_ALL_EXTERNAL_DESTINATIONS);
/*  732 */       int col = 1;
/*  733 */       resultSet = stmt.executeQuery();
/*  734 */       while (resultSet.next())
/*      */       {
/*  736 */         col = 2;
/*      */ 
/*  739 */         ExternalDestinationPK pkExternalDestination = new ExternalDestinationPK(resultSet.getInt(col++));
/*      */ 
/*  742 */         String textExternalDestination = resultSet.getString(col++);
/*      */ 
/*  746 */         ExternalDestinationRefImpl erExternalDestination = new ExternalDestinationRefImpl(pkExternalDestination, textExternalDestination);
/*      */ 
/*  751 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  754 */         results.add(erExternalDestination, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  762 */       throw handleSQLException(SQL_ALL_EXTERNAL_DESTINATIONS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  766 */       closeResultSet(resultSet);
/*  767 */       closeStatement(stmt);
/*  768 */       closeConnection();
/*      */     }
/*      */ 
/*  771 */     if (timer != null) {
/*  772 */       timer.logDebug("getAllExternalDestinations", " items=" + results.size());
/*      */     }
/*      */ 
/*  776 */     return results;
/*      */   }
/*      */ 
/*      */   public AllExternalDestinationDetailsELO getAllExternalDestinationDetails()
/*      */   {
/*  806 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  807 */     PreparedStatement stmt = null;
/*  808 */     ResultSet resultSet = null;
/*  809 */     AllExternalDestinationDetailsELO results = new AllExternalDestinationDetailsELO();
/*      */     try
/*      */     {
/*  812 */       stmt = getConnection().prepareStatement(SQL_ALL_EXTERNAL_DESTINATION_DETAILS);
/*  813 */       int col = 1;
/*  814 */       resultSet = stmt.executeQuery();
/*  815 */       while (resultSet.next())
/*      */       {
/*  817 */         col = 2;
/*      */ 
/*  820 */         ExternalDestinationPK pkExternalDestination = new ExternalDestinationPK(resultSet.getInt(col++));
/*      */ 
/*  823 */         String textExternalDestination = resultSet.getString(col++);
/*      */ 
/*  827 */         ExternalDestinationRefImpl erExternalDestination = new ExternalDestinationRefImpl(pkExternalDestination, textExternalDestination);
/*      */ 
/*  832 */         String col1 = resultSet.getString(col++);
/*  833 */         int col2 = resultSet.getInt(col++);
/*      */ 
/*  836 */         results.add(erExternalDestination, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  845 */       throw handleSQLException(SQL_ALL_EXTERNAL_DESTINATION_DETAILS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  849 */       closeResultSet(resultSet);
/*  850 */       closeStatement(stmt);
/*  851 */       closeConnection();
/*      */     }
/*      */ 
/*  854 */     if (timer != null) {
/*  855 */       timer.logDebug("getAllExternalDestinationDetails", " items=" + results.size());
/*      */     }
/*      */ 
/*  859 */     return results;
/*      */   }
/*      */ 
/*      */   public AllUsersForExternalDestinationIdELO getAllUsersForExternalDestinationId(int param1)
/*      */   {
/*  895 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  896 */     PreparedStatement stmt = null;
/*  897 */     ResultSet resultSet = null;
/*  898 */     AllUsersForExternalDestinationIdELO results = new AllUsersForExternalDestinationIdELO();
/*      */     try
/*      */     {
/*  901 */       stmt = getConnection().prepareStatement(SQL_ALL_USERS_FOR_EXTERNAL_DESTINATION_ID);
/*  902 */       int col = 1;
/*  903 */       stmt.setInt(col++, param1);
/*  904 */       resultSet = stmt.executeQuery();
/*  905 */       while (resultSet.next())
/*      */       {
/*  907 */         col = 2;
/*      */ 
/*  910 */         ExternalDestinationPK pkExternalDestination = new ExternalDestinationPK(resultSet.getInt(col++));
/*      */ 
/*  913 */         String textExternalDestination = resultSet.getString(col++);
/*      */ 
/*  916 */         ExternalDestinationUsersPK pkExternalDestinationUsers = new ExternalDestinationUsersPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  920 */         String textExternalDestinationUsers = "";
/*      */ 
/*  923 */         ExternalDestinationRefImpl erExternalDestination = new ExternalDestinationRefImpl(pkExternalDestination, textExternalDestination);
/*      */ 
/*  929 */         ExternalDestinationUsersRefImpl erExternalDestinationUsers = new ExternalDestinationUsersRefImpl(pkExternalDestinationUsers, textExternalDestinationUsers);
/*      */ 
/*  934 */         String col1 = resultSet.getString(col++);
/*  935 */         String col2 = resultSet.getString(col++);
/*      */ 
/*  938 */         results.add(erExternalDestination, erExternalDestinationUsers, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  948 */       throw handleSQLException(SQL_ALL_USERS_FOR_EXTERNAL_DESTINATION_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  952 */       closeResultSet(resultSet);
/*  953 */       closeStatement(stmt);
/*  954 */       closeConnection();
/*      */     }
/*      */ 
/*  957 */     if (timer != null) {
/*  958 */       timer.logDebug("getAllUsersForExternalDestinationId", " ExternalDestinationId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  963 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ExternalDestinationPK pk)
/*      */   {
/*  990 */     Set emptyStrings = Collections.emptySet();
/*  991 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ExternalDestinationPK pk, Set<String> exclusionTables)
/*      */   {
/*  997 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  999 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1001 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1003 */       PreparedStatement stmt = null;
/*      */ 
/* 1005 */       int resultCount = 0;
/* 1006 */       String s = null;
/*      */       try
/*      */       {
/* 1009 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 1011 */         if (this._log.isDebugEnabled()) {
/* 1012 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1014 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1017 */         int col = 1;
/* 1018 */         stmt.setInt(col++, pk.getExternalDestinationId());
/*      */ 
/* 1021 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1025 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1029 */         closeStatement(stmt);
/* 1030 */         closeConnection();
/*      */ 
/* 1032 */         if (timer != null) {
/* 1033 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1037 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1039 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1041 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1043 */       PreparedStatement stmt = null;
/*      */ 
/* 1045 */       int resultCount = 0;
/* 1046 */       String s = null;
/*      */       try
/*      */       {
/* 1049 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1051 */         if (this._log.isDebugEnabled()) {
/* 1052 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1054 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1057 */         int col = 1;
/* 1058 */         stmt.setInt(col++, pk.getExternalDestinationId());
/*      */ 
/* 1061 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1065 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1069 */         closeStatement(stmt);
/* 1070 */         closeConnection();
/*      */ 
/* 1072 */         if (timer != null)
/* 1073 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public ExternalDestinationEVO getDetails(ExternalDestinationPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1092 */     return getDetails(new ExternalDestinationCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public ExternalDestinationEVO getDetails(ExternalDestinationCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1109 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1112 */     if (this.mDetails == null) {
/* 1113 */       doLoad(paramCK.getExternalDestinationPK());
/*      */     }
/* 1115 */     else if (!this.mDetails.getPK().equals(paramCK.getExternalDestinationPK())) {
/* 1116 */       doLoad(paramCK.getExternalDestinationPK());
/*      */     }
/* 1118 */     else if (!checkIfValid())
/*      */     {
/* 1120 */       this._log.info("getDetails", "[ALERT] ExternalDestinationEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1122 */       doLoad(paramCK.getExternalDestinationPK());
/*      */     }
/*      */ 
/* 1132 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isExternalUserListAllItemsLoaded()))
/*      */     {
/* 1137 */       this.mDetails.setExternalUserList(getExternalDestinationUsersDAO().getAll(this.mDetails.getExternalDestinationId(), dependants, this.mDetails.getExternalUserList()));
/*      */ 
/* 1144 */       this.mDetails.setExternalUserListAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1147 */     if ((paramCK instanceof ExternalDestinationUsersCK))
/*      */     {
/* 1149 */       if (this.mDetails.getExternalUserList() == null) {
/* 1150 */         this.mDetails.loadExternalUserListItem(getExternalDestinationUsersDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1153 */         ExternalDestinationUsersPK pk = ((ExternalDestinationUsersCK)paramCK).getExternalDestinationUsersPK();
/* 1154 */         ExternalDestinationUsersEVO evo = this.mDetails.getExternalUserListItem(pk);
/* 1155 */         if (evo == null) {
/* 1156 */           this.mDetails.loadExternalUserListItem(getExternalDestinationUsersDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1161 */     ExternalDestinationEVO details = new ExternalDestinationEVO();
/* 1162 */     details = this.mDetails.deepClone();
/*      */ 
/* 1164 */     if (timer != null) {
/* 1165 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1167 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1177 */     boolean stillValid = false;
/* 1178 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1179 */     PreparedStatement stmt = null;
/* 1180 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1183 */       stmt = getConnection().prepareStatement("select VERSION_NUM from EXTERNAL_DESTINATION where   EXTERNAL_DESTINATION_ID = ?");
/* 1184 */       int col = 1;
/* 1185 */       stmt.setInt(col++, this.mDetails.getExternalDestinationId());
/*      */ 
/* 1187 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1189 */       if (!resultSet.next()) {
/* 1190 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1192 */       col = 1;
/* 1193 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1195 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1196 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1200 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from EXTERNAL_DESTINATION where   EXTERNAL_DESTINATION_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1204 */       closeResultSet(resultSet);
/* 1205 */       closeStatement(stmt);
/* 1206 */       closeConnection();
/*      */ 
/* 1208 */       if (timer != null) {
/* 1209 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1212 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public ExternalDestinationEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1218 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1220 */     if (!checkIfValid())
/*      */     {
/* 1222 */       this._log.info("getDetails", "ExternalDestination " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1223 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1227 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1230 */     ExternalDestinationEVO details = this.mDetails.deepClone();
/*      */ 
/* 1232 */     if (timer != null) {
/* 1233 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1235 */     return details;
/*      */   }
/*      */ 
/*      */   protected ExternalDestinationUsersDAO getExternalDestinationUsersDAO()
/*      */   {
/* 1244 */     if (this.mExternalDestinationUsersDAO == null)
/*      */     {
/* 1246 */       if (this.mDataSource != null)
/* 1247 */         this.mExternalDestinationUsersDAO = new ExternalDestinationUsersDAO(this.mDataSource);
/*      */       else {
/* 1249 */         this.mExternalDestinationUsersDAO = new ExternalDestinationUsersDAO(getConnection());
/*      */       }
/*      */     }
/* 1252 */     return this.mExternalDestinationUsersDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1257 */     return "ExternalDestination";
/*      */   }
/*      */ 
/*      */   public ExternalDestinationRef getRef(ExternalDestinationPK paramExternalDestinationPK)
/*      */     throws ValidationException
/*      */   {
/* 1263 */     ExternalDestinationEVO evo = getDetails(paramExternalDestinationPK, "");
/* 1264 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1289 */     if (c == null)
/* 1290 */       return;
/* 1291 */     Iterator iter = c.iterator();
/* 1292 */     while (iter.hasNext())
/*      */     {
/* 1294 */       ExternalDestinationEVO evo = (ExternalDestinationEVO)iter.next();
/* 1295 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(ExternalDestinationEVO evo, String dependants)
/*      */   {
/* 1309 */     if (evo.getExternalDestinationId() < 1) {
/* 1310 */       return;
/*      */     }
/*      */ 
/* 1318 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1321 */       if (!evo.isExternalUserListAllItemsLoaded())
/*      */       {
/* 1323 */         evo.setExternalUserList(getExternalDestinationUsersDAO().getAll(evo.getExternalDestinationId(), dependants, evo.getExternalUserList()));
/*      */ 
/* 1330 */         evo.setExternalUserListAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllNonDisabledUsersELO getDistinctExternalDestinationUsers(String[] visIds)
/*      */   {
/* 1350 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1351 */     PreparedStatement stmt = null;
/* 1352 */     ResultSet resultSet = null;
/* 1353 */     AllNonDisabledUsersELO results = new AllNonDisabledUsersELO();
/*      */     try
/*      */     {
/* 1356 */       StringBuffer sql = new StringBuffer("select distinct    edu.EMAIL_ADDRESS  from    EXTERNAL_DESTINATION e,    EXTERNAL_DESTINATION_USERS edu  where    e.external_destination_id = edu.external_destination_id    and e.vis_id in (");
/* 1357 */       for (int i = 0; i < visIds.length; i++)
/*      */       {
/* 1359 */         if (i != 0)
/* 1360 */           sql.append(",");
/* 1361 */         sql.append("?");
/*      */       }
/* 1363 */       sql.append(")");
/* 1364 */       stmt = getConnection().prepareStatement(sql.toString());
/*      */ 
/* 1366 */       int col = 1;
/* 1367 */       for (int i = 0; i < visIds.length; i++)
/*      */       {
/* 1369 */         stmt.setString(col, visIds[i].trim());
/* 1370 */         col++;
/*      */       }
/*      */ 
/* 1373 */       resultSet = stmt.executeQuery();
/* 1374 */       col = 1;
/* 1375 */       while (resultSet.next())
/*      */       {
/* 1377 */         String emailAddress = resultSet.getString(col);
/*      */ 
/* 1380 */         results.add(null, emailAddress, emailAddress);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1389 */       throw handleSQLException("select distinct    edu.EMAIL_ADDRESS  from    EXTERNAL_DESTINATION e,    EXTERNAL_DESTINATION_USERS edu  where    e.external_destination_id = edu.external_destination_id    and e.vis_id in (", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1393 */       closeResultSet(resultSet);
/* 1394 */       closeStatement(stmt);
/* 1395 */       closeConnection();
/*      */     }
/*      */ 
/* 1398 */     if (timer != null) {
/* 1399 */       timer.logDebug("getAllNonDisabledUsers", " items=" + results.size());
/*      */     }
/*      */ 
/* 1403 */     return results;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationDAO
 * JD-Core Version:    0.6.0
 */