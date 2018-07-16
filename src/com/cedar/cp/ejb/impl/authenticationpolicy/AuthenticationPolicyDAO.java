/*      */ package com.cedar.cp.ejb.impl.authenticationpolicy;
/*      */ 
/*      */ import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.sql.DataSource;

import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicyRef;
import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.authenticationpolicy.ActiveAuthenticationPolicyForLogonELO;
import com.cedar.cp.dto.authenticationpolicy.ActiveAuthenticationPolicysELO;
import com.cedar.cp.dto.authenticationpolicy.AllAuthenticationPolicysELO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyCK;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
/*      */ 
/*      */ public class AuthenticationPolicyDAO extends AbstractDAO
/*      */ {
/*   32 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select AUTHENTICATION_POLICY_ID from AUTHENTICATION_POLICY where    AUTHENTICATION_POLICY_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select AUTHENTICATION_POLICY.AUTHENTICATION_POLICY_ID,AUTHENTICATION_POLICY.VIS_ID,AUTHENTICATION_POLICY.DESCRIPTION,AUTHENTICATION_POLICY.AUTHENTICATION_TECHNIQUE,AUTHENTICATION_POLICY.SECURITY_ADMINISTRATOR,AUTHENTICATION_POLICY.MINIMUM_PASSWORD_LENGTH,AUTHENTICATION_POLICY.MINIMUM_ALPHAS,AUTHENTICATION_POLICY.MINIMUM_DIGITS,AUTHENTICATION_POLICY.MAXIMUM_REPETITION,AUTHENTICATION_POLICY.MINIMUM_CHANGES,AUTHENTICATION_POLICY.PASSWORD_USERID_DIFFER,AUTHENTICATION_POLICY.PASSWORD_MASK,AUTHENTICATION_POLICY.PASSWORD_REUSE_DELTA,AUTHENTICATION_POLICY.MAXIMUM_LOGON_ATTEMPTS,AUTHENTICATION_POLICY.PASSWORD_EXPIRY,AUTHENTICATION_POLICY.SECURITY_LOG,AUTHENTICATION_POLICY.ACTIVE,AUTHENTICATION_POLICY.JAAS_ENTRY_NAME,AUTHENTICATION_POLICY.COSIGN_CONFIGURATION_FILE,AUTHENTICATION_POLICY.NTLM_NETBIOS_WINS,AUTHENTICATION_POLICY.NTLM_DOMAIN,AUTHENTICATION_POLICY.NTLM_DOMAIN_CONTROLLER,AUTHENTICATION_POLICY.NTLM_LOG_LEVEL,AUTHENTICATION_POLICY.VERSION_NUM,AUTHENTICATION_POLICY.UPDATED_BY_USER_ID,AUTHENTICATION_POLICY.UPDATED_TIME,AUTHENTICATION_POLICY.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from AUTHENTICATION_POLICY where    AUTHENTICATION_POLICY_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into AUTHENTICATION_POLICY ( AUTHENTICATION_POLICY_ID,VIS_ID,DESCRIPTION,AUTHENTICATION_TECHNIQUE,SECURITY_ADMINISTRATOR,MINIMUM_PASSWORD_LENGTH,MINIMUM_ALPHAS,MINIMUM_DIGITS,MAXIMUM_REPETITION,MINIMUM_CHANGES,PASSWORD_USERID_DIFFER,PASSWORD_MASK,PASSWORD_REUSE_DELTA,MAXIMUM_LOGON_ATTEMPTS,PASSWORD_EXPIRY,SECURITY_LOG,ACTIVE,JAAS_ENTRY_NAME,COSIGN_CONFIGURATION_FILE,NTLM_NETBIOS_WINS,NTLM_DOMAIN,NTLM_DOMAIN_CONTROLLER,NTLM_LOG_LEVEL,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update AUTHENTICATION_POLICY_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from AUTHENTICATION_POLICY_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_VISID = "select count(*) from AUTHENTICATION_POLICY where    VIS_ID = ? and not(    AUTHENTICATION_POLICY_ID = ? )";
/*      */   protected static final String SQL_STORE = "update AUTHENTICATION_POLICY set VIS_ID = ?,DESCRIPTION = ?,AUTHENTICATION_TECHNIQUE = ?,SECURITY_ADMINISTRATOR = ?,MINIMUM_PASSWORD_LENGTH = ?,MINIMUM_ALPHAS = ?,MINIMUM_DIGITS = ?,MAXIMUM_REPETITION = ?,MINIMUM_CHANGES = ?,PASSWORD_USERID_DIFFER = ?,PASSWORD_MASK = ?,PASSWORD_REUSE_DELTA = ?,MAXIMUM_LOGON_ATTEMPTS = ?,PASSWORD_EXPIRY = ?,SECURITY_LOG = ?,ACTIVE = ?,JAAS_ENTRY_NAME = ?,COSIGN_CONFIGURATION_FILE = ?,NTLM_NETBIOS_WINS = ?,NTLM_DOMAIN = ?,NTLM_DOMAIN_CONTROLLER = ?,NTLM_LOG_LEVEL = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AUTHENTICATION_POLICY_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from AUTHENTICATION_POLICY where AUTHENTICATION_POLICY_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from AUTHENTICATION_POLICY where    AUTHENTICATION_POLICY_ID = ? ";
/*  803 */   protected static String SQL_ALL_AUTHENTICATION_POLICYS = "select 0       ,AUTHENTICATION_POLICY.AUTHENTICATION_POLICY_ID      ,AUTHENTICATION_POLICY.VIS_ID      ,AUTHENTICATION_POLICY.VIS_ID      ,AUTHENTICATION_POLICY.DESCRIPTION      ,AUTHENTICATION_POLICY.AUTHENTICATION_TECHNIQUE      ,AUTHENTICATION_POLICY.ACTIVE from AUTHENTICATION_POLICY where 1=1 ";
/*      */ 
/*  894 */   protected static String SQL_ACTIVE_AUTHENTICATION_POLICYS = "select 0       ,AUTHENTICATION_POLICY.AUTHENTICATION_POLICY_ID      ,AUTHENTICATION_POLICY.VIS_ID      ,AUTHENTICATION_POLICY.VIS_ID      ,AUTHENTICATION_POLICY.DESCRIPTION      ,AUTHENTICATION_POLICY.AUTHENTICATION_TECHNIQUE from AUTHENTICATION_POLICY where 1=1  and  AUTHENTICATION_POLICY.ACTIVE = 'Y'";
/*      */ 
/*  980 */   protected static String SQL_ACTIVE_AUTHENTICATION_POLICY_FOR_LOGON = "select 0       ,AUTHENTICATION_POLICY.AUTHENTICATION_POLICY_ID      ,AUTHENTICATION_POLICY.VIS_ID      ,AUTHENTICATION_POLICY.VIS_ID      ,AUTHENTICATION_POLICY.DESCRIPTION      ,AUTHENTICATION_POLICY.AUTHENTICATION_TECHNIQUE      ,AUTHENTICATION_POLICY.MINIMUM_PASSWORD_LENGTH      ,AUTHENTICATION_POLICY.SECURITY_LOG      ,AUTHENTICATION_POLICY.SECURITY_ADMINISTRATOR      ,AUTHENTICATION_POLICY.PASSWORD_EXPIRY      ,AUTHENTICATION_POLICY.JAAS_ENTRY_NAME from AUTHENTICATION_POLICY where 1=1  and  AUTHENTICATION_POLICY.ACTIVE = 'Y'";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from AUTHENTICATION_POLICY where   AUTHENTICATION_POLICY_ID = ?";
/*      */   protected AuthenticationPolicyEVO mDetails;
/*      */ 
/*      */   public AuthenticationPolicyDAO(Connection connection)
/*      */   {
/*   39 */     super(connection);
/*      */   }
/*      */ 
/*      */   public AuthenticationPolicyDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public AuthenticationPolicyDAO(DataSource ds)
/*      */   {
/*   55 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected AuthenticationPolicyPK getPK()
/*      */   {
/*   63 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(AuthenticationPolicyEVO details)
/*      */   {
/*   72 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public AuthenticationPolicyEVO setAndGetDetails(AuthenticationPolicyEVO details, String dependants)
/*      */   {
/*   83 */     setDetails(details);
/*   84 */     generateKeys();
/*   85 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public AuthenticationPolicyPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   94 */     doCreate();
/*      */ 
/*   96 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(AuthenticationPolicyPK pk)
/*      */     throws ValidationException
/*      */   {
/*  106 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  115 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  124 */     doRemove();
/*      */   }
/*      */ 
/*      */   public AuthenticationPolicyPK findByPrimaryKey(AuthenticationPolicyPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  133 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  134 */     if (exists(pk_))
/*      */     {
/*  136 */       if (timer != null) {
/*  137 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  139 */       return pk_;
/*      */     }
/*      */ 
/*  142 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(AuthenticationPolicyPK pk)
/*      */   {
/*  160 */     PreparedStatement stmt = null;
/*  161 */     ResultSet resultSet = null;
/*  162 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  166 */       stmt = getConnection().prepareStatement("select AUTHENTICATION_POLICY_ID from AUTHENTICATION_POLICY where    AUTHENTICATION_POLICY_ID = ? ");
/*      */ 
/*  168 */       int col = 1;
/*  169 */       stmt.setInt(col++, pk.getAuthenticationPolicyId());
/*      */ 
/*  171 */       resultSet = stmt.executeQuery();
/*      */ 
/*  173 */       if (!resultSet.next())
/*  174 */         returnValue = false;
/*      */       else
/*  176 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  180 */       throw handleSQLException(pk, "select AUTHENTICATION_POLICY_ID from AUTHENTICATION_POLICY where    AUTHENTICATION_POLICY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  184 */       closeResultSet(resultSet);
/*  185 */       closeStatement(stmt);
/*  186 */       closeConnection();
/*      */     }
/*  188 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private AuthenticationPolicyEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  226 */     int col = 1;
/*  227 */     AuthenticationPolicyEVO evo = new AuthenticationPolicyEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*      */ 
/*  254 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  255 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  256 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  257 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(AuthenticationPolicyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  262 */     int col = startCol_;
/*  263 */     stmt_.setInt(col++, evo_.getAuthenticationPolicyId());
/*  264 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(AuthenticationPolicyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  269 */     int col = startCol_;
/*  270 */     stmt_.setString(col++, evo_.getVisId());
/*  271 */     stmt_.setString(col++, evo_.getDescription());
/*  272 */     stmt_.setInt(col++, evo_.getAuthenticationTechnique());
/*  273 */     stmt_.setInt(col++, evo_.getSecurityAdministrator());
/*  274 */     stmt_.setInt(col++, evo_.getMinimumPasswordLength());
/*  275 */     stmt_.setInt(col++, evo_.getMinimumAlphas());
/*  276 */     stmt_.setInt(col++, evo_.getMinimumDigits());
/*  277 */     stmt_.setInt(col++, evo_.getMaximumRepetition());
/*  278 */     stmt_.setInt(col++, evo_.getMinimumChanges());
/*  279 */     if (evo_.getPasswordUseridDiffer())
/*  280 */       stmt_.setString(col++, "Y");
/*      */     else
/*  282 */       stmt_.setString(col++, " ");
/*  283 */     stmt_.setString(col++, evo_.getPasswordMask());
/*  284 */     stmt_.setInt(col++, evo_.getPasswordReuseDelta());
/*  285 */     stmt_.setInt(col++, evo_.getMaximumLogonAttempts());
/*  286 */     stmt_.setInt(col++, evo_.getPasswordExpiry());
/*  287 */     stmt_.setInt(col++, evo_.getSecurityLog());
/*  288 */     if (evo_.getActive())
/*  289 */       stmt_.setString(col++, "Y");
/*      */     else
/*  291 */       stmt_.setString(col++, " ");
/*  292 */     stmt_.setString(col++, evo_.getJaasEntryName());
/*  293 */     stmt_.setString(col++, evo_.getCosignConfigurationFile());
/*  294 */     stmt_.setString(col++, evo_.getNtlmNetbiosWins());
/*  295 */     stmt_.setString(col++, evo_.getNtlmDomain());
/*  296 */     stmt_.setString(col++, evo_.getNtlmDomainController());
/*  297 */     stmt_.setInt(col++, evo_.getNtlmLogLevel());
/*  298 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  299 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  300 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  301 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  302 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(AuthenticationPolicyPK pk)
/*      */     throws ValidationException
/*      */   {
/*  318 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  320 */     PreparedStatement stmt = null;
/*  321 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  325 */       stmt = getConnection().prepareStatement("select AUTHENTICATION_POLICY.AUTHENTICATION_POLICY_ID,AUTHENTICATION_POLICY.VIS_ID,AUTHENTICATION_POLICY.DESCRIPTION,AUTHENTICATION_POLICY.AUTHENTICATION_TECHNIQUE,AUTHENTICATION_POLICY.SECURITY_ADMINISTRATOR,AUTHENTICATION_POLICY.MINIMUM_PASSWORD_LENGTH,AUTHENTICATION_POLICY.MINIMUM_ALPHAS,AUTHENTICATION_POLICY.MINIMUM_DIGITS,AUTHENTICATION_POLICY.MAXIMUM_REPETITION,AUTHENTICATION_POLICY.MINIMUM_CHANGES,AUTHENTICATION_POLICY.PASSWORD_USERID_DIFFER,AUTHENTICATION_POLICY.PASSWORD_MASK,AUTHENTICATION_POLICY.PASSWORD_REUSE_DELTA,AUTHENTICATION_POLICY.MAXIMUM_LOGON_ATTEMPTS,AUTHENTICATION_POLICY.PASSWORD_EXPIRY,AUTHENTICATION_POLICY.SECURITY_LOG,AUTHENTICATION_POLICY.ACTIVE,AUTHENTICATION_POLICY.JAAS_ENTRY_NAME,AUTHENTICATION_POLICY.COSIGN_CONFIGURATION_FILE,AUTHENTICATION_POLICY.NTLM_NETBIOS_WINS,AUTHENTICATION_POLICY.NTLM_DOMAIN,AUTHENTICATION_POLICY.NTLM_DOMAIN_CONTROLLER,AUTHENTICATION_POLICY.NTLM_LOG_LEVEL,AUTHENTICATION_POLICY.VERSION_NUM,AUTHENTICATION_POLICY.UPDATED_BY_USER_ID,AUTHENTICATION_POLICY.UPDATED_TIME,AUTHENTICATION_POLICY.CREATED_TIME from AUTHENTICATION_POLICY where    AUTHENTICATION_POLICY_ID = ? ");
/*      */ 
/*  328 */       int col = 1;
/*  329 */       stmt.setInt(col++, pk.getAuthenticationPolicyId());
/*      */ 
/*  331 */       resultSet = stmt.executeQuery();
/*      */ 
/*  333 */       if (!resultSet.next()) {
/*  334 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  337 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  338 */       if (this.mDetails.isModified())
/*  339 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  343 */       throw handleSQLException(pk, "select AUTHENTICATION_POLICY.AUTHENTICATION_POLICY_ID,AUTHENTICATION_POLICY.VIS_ID,AUTHENTICATION_POLICY.DESCRIPTION,AUTHENTICATION_POLICY.AUTHENTICATION_TECHNIQUE,AUTHENTICATION_POLICY.SECURITY_ADMINISTRATOR,AUTHENTICATION_POLICY.MINIMUM_PASSWORD_LENGTH,AUTHENTICATION_POLICY.MINIMUM_ALPHAS,AUTHENTICATION_POLICY.MINIMUM_DIGITS,AUTHENTICATION_POLICY.MAXIMUM_REPETITION,AUTHENTICATION_POLICY.MINIMUM_CHANGES,AUTHENTICATION_POLICY.PASSWORD_USERID_DIFFER,AUTHENTICATION_POLICY.PASSWORD_MASK,AUTHENTICATION_POLICY.PASSWORD_REUSE_DELTA,AUTHENTICATION_POLICY.MAXIMUM_LOGON_ATTEMPTS,AUTHENTICATION_POLICY.PASSWORD_EXPIRY,AUTHENTICATION_POLICY.SECURITY_LOG,AUTHENTICATION_POLICY.ACTIVE,AUTHENTICATION_POLICY.JAAS_ENTRY_NAME,AUTHENTICATION_POLICY.COSIGN_CONFIGURATION_FILE,AUTHENTICATION_POLICY.NTLM_NETBIOS_WINS,AUTHENTICATION_POLICY.NTLM_DOMAIN,AUTHENTICATION_POLICY.NTLM_DOMAIN_CONTROLLER,AUTHENTICATION_POLICY.NTLM_LOG_LEVEL,AUTHENTICATION_POLICY.VERSION_NUM,AUTHENTICATION_POLICY.UPDATED_BY_USER_ID,AUTHENTICATION_POLICY.UPDATED_TIME,AUTHENTICATION_POLICY.CREATED_TIME from AUTHENTICATION_POLICY where    AUTHENTICATION_POLICY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  347 */       closeResultSet(resultSet);
/*  348 */       closeStatement(stmt);
/*  349 */       closeConnection();
/*      */ 
/*  351 */       if (timer != null)
/*  352 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  423 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  424 */     generateKeys();
/*      */ 
/*  426 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  431 */       duplicateValueCheckVisId();
/*      */ 
/*  433 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  434 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  435 */       stmt = getConnection().prepareStatement("insert into AUTHENTICATION_POLICY ( AUTHENTICATION_POLICY_ID,VIS_ID,DESCRIPTION,AUTHENTICATION_TECHNIQUE,SECURITY_ADMINISTRATOR,MINIMUM_PASSWORD_LENGTH,MINIMUM_ALPHAS,MINIMUM_DIGITS,MAXIMUM_REPETITION,MINIMUM_CHANGES,PASSWORD_USERID_DIFFER,PASSWORD_MASK,PASSWORD_REUSE_DELTA,MAXIMUM_LOGON_ATTEMPTS,PASSWORD_EXPIRY,SECURITY_LOG,ACTIVE,JAAS_ENTRY_NAME,COSIGN_CONFIGURATION_FILE,NTLM_NETBIOS_WINS,NTLM_DOMAIN,NTLM_DOMAIN_CONTROLLER,NTLM_LOG_LEVEL,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  438 */       int col = 1;
/*  439 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  440 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  443 */       int resultCount = stmt.executeUpdate();
/*  444 */       if (resultCount != 1)
/*      */       {
/*  446 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  449 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  453 */       throw handleSQLException(this.mDetails.getPK(), "insert into AUTHENTICATION_POLICY ( AUTHENTICATION_POLICY_ID,VIS_ID,DESCRIPTION,AUTHENTICATION_TECHNIQUE,SECURITY_ADMINISTRATOR,MINIMUM_PASSWORD_LENGTH,MINIMUM_ALPHAS,MINIMUM_DIGITS,MAXIMUM_REPETITION,MINIMUM_CHANGES,PASSWORD_USERID_DIFFER,PASSWORD_MASK,PASSWORD_REUSE_DELTA,MAXIMUM_LOGON_ATTEMPTS,PASSWORD_EXPIRY,SECURITY_LOG,ACTIVE,JAAS_ENTRY_NAME,COSIGN_CONFIGURATION_FILE,NTLM_NETBIOS_WINS,NTLM_DOMAIN,NTLM_DOMAIN_CONTROLLER,NTLM_LOG_LEVEL,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  457 */       closeStatement(stmt);
/*  458 */       closeConnection();
/*      */ 
/*  460 */       if (timer != null)
/*  461 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  481 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  483 */     PreparedStatement stmt = null;
/*  484 */     ResultSet resultSet = null;
/*  485 */     String sqlString = null;
/*      */     try
/*      */     {
/*  490 */       sqlString = "update AUTHENTICATION_POLICY_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  491 */       stmt = getConnection().prepareStatement("update AUTHENTICATION_POLICY_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  492 */       stmt.setInt(1, insertCount);
/*      */ 
/*  494 */       int resultCount = stmt.executeUpdate();
/*  495 */       if (resultCount != 1) {
/*  496 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  498 */       closeStatement(stmt);
/*      */ 
/*  501 */       sqlString = "select SEQ_NUM from AUTHENTICATION_POLICY_SEQ";
/*  502 */       stmt = getConnection().prepareStatement("select SEQ_NUM from AUTHENTICATION_POLICY_SEQ");
/*  503 */       resultSet = stmt.executeQuery();
/*  504 */       if (!resultSet.next())
/*  505 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  506 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  508 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  512 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  516 */       closeResultSet(resultSet);
/*  517 */       closeStatement(stmt);
/*  518 */       closeConnection();
/*      */ 
/*  520 */       if (timer != null)
/*  521 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  521 */     }
/*      */   }
/*      */ 
/*      */   public AuthenticationPolicyPK generateKeys()
/*      */   {
/*  531 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  533 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  536 */     if (insertCount == 0) {
/*  537 */       return this.mDetails.getPK();
/*      */     }
/*  539 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  541 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckVisId()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  554 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  555 */     PreparedStatement stmt = null;
/*  556 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  560 */       stmt = getConnection().prepareStatement("select count(*) from AUTHENTICATION_POLICY where    VIS_ID = ? and not(    AUTHENTICATION_POLICY_ID = ? )");
/*      */ 
/*  563 */       int col = 1;
/*  564 */       stmt.setString(col++, this.mDetails.getVisId());
/*  565 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  568 */       resultSet = stmt.executeQuery();
/*      */ 
/*  570 */       if (!resultSet.next()) {
/*  571 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  575 */       col = 1;
/*  576 */       int count = resultSet.getInt(col++);
/*  577 */       if (count > 0) {
/*  578 */         throw new DuplicateNameValidationException("Duplicate name (" + this.mDetails.getVisId() + "). Please re-name and retry");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  584 */       throw handleSQLException(getPK(), "select count(*) from AUTHENTICATION_POLICY where    VIS_ID = ? and not(    AUTHENTICATION_POLICY_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  588 */       closeResultSet(resultSet);
/*  589 */       closeStatement(stmt);
/*  590 */       closeConnection();
/*      */ 
/*  592 */       if (timer != null)
/*  593 */         timer.logDebug("duplicateValueCheckVisId", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  638 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  640 */     generateKeys();
/*      */ 
/*  645 */     PreparedStatement stmt = null;
/*      */ 
/*  647 */     boolean mainChanged = this.mDetails.isModified();
/*  648 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  652 */       if (mainChanged)
/*  653 */         duplicateValueCheckVisId();
/*  654 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  657 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  660 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  661 */         stmt = getConnection().prepareStatement("update AUTHENTICATION_POLICY set VIS_ID = ?,DESCRIPTION = ?,AUTHENTICATION_TECHNIQUE = ?,SECURITY_ADMINISTRATOR = ?,MINIMUM_PASSWORD_LENGTH = ?,MINIMUM_ALPHAS = ?,MINIMUM_DIGITS = ?,MAXIMUM_REPETITION = ?,MINIMUM_CHANGES = ?,PASSWORD_USERID_DIFFER = ?,PASSWORD_MASK = ?,PASSWORD_REUSE_DELTA = ?,MAXIMUM_LOGON_ATTEMPTS = ?,PASSWORD_EXPIRY = ?,SECURITY_LOG = ?,ACTIVE = ?,JAAS_ENTRY_NAME = ?,COSIGN_CONFIGURATION_FILE = ?,NTLM_NETBIOS_WINS = ?,NTLM_DOMAIN = ?,NTLM_DOMAIN_CONTROLLER = ?,NTLM_LOG_LEVEL = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AUTHENTICATION_POLICY_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  664 */         int col = 1;
/*  665 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  666 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  668 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  671 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  673 */         if (resultCount == 0) {
/*  674 */           checkVersionNum();
/*      */         }
/*  676 */         if (resultCount != 1) {
/*  677 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  680 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  689 */       throw handleSQLException(getPK(), "update AUTHENTICATION_POLICY set VIS_ID = ?,DESCRIPTION = ?,AUTHENTICATION_TECHNIQUE = ?,SECURITY_ADMINISTRATOR = ?,MINIMUM_PASSWORD_LENGTH = ?,MINIMUM_ALPHAS = ?,MINIMUM_DIGITS = ?,MAXIMUM_REPETITION = ?,MINIMUM_CHANGES = ?,PASSWORD_USERID_DIFFER = ?,PASSWORD_MASK = ?,PASSWORD_REUSE_DELTA = ?,MAXIMUM_LOGON_ATTEMPTS = ?,PASSWORD_EXPIRY = ?,SECURITY_LOG = ?,ACTIVE = ?,JAAS_ENTRY_NAME = ?,COSIGN_CONFIGURATION_FILE = ?,NTLM_NETBIOS_WINS = ?,NTLM_DOMAIN = ?,NTLM_DOMAIN_CONTROLLER = ?,NTLM_LOG_LEVEL = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AUTHENTICATION_POLICY_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  693 */       closeStatement(stmt);
/*  694 */       closeConnection();
/*      */ 
/*  696 */       if ((timer != null) && (
/*  697 */         (mainChanged) || (dependantChanged)))
/*  698 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  710 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  711 */     PreparedStatement stmt = null;
/*  712 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  716 */       stmt = getConnection().prepareStatement("select VERSION_NUM from AUTHENTICATION_POLICY where AUTHENTICATION_POLICY_ID = ?");
/*      */ 
/*  719 */       int col = 1;
/*  720 */       stmt.setInt(col++, this.mDetails.getAuthenticationPolicyId());
/*      */ 
/*  723 */       resultSet = stmt.executeQuery();
/*      */ 
/*  725 */       if (!resultSet.next()) {
/*  726 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  729 */       col = 1;
/*  730 */       int dbVersionNumber = resultSet.getInt(col++);
/*  731 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  732 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  738 */       throw handleSQLException(getPK(), "select VERSION_NUM from AUTHENTICATION_POLICY where AUTHENTICATION_POLICY_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  742 */       closeStatement(stmt);
/*  743 */       closeResultSet(resultSet);
/*      */ 
/*  745 */       if (timer != null)
/*  746 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  763 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  768 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  773 */       stmt = getConnection().prepareStatement("delete from AUTHENTICATION_POLICY where    AUTHENTICATION_POLICY_ID = ? ");
/*      */ 
/*  776 */       int col = 1;
/*  777 */       stmt.setInt(col++, this.mDetails.getAuthenticationPolicyId());
/*      */ 
/*  779 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  781 */       if (resultCount != 1) {
/*  782 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  788 */       throw handleSQLException(getPK(), "delete from AUTHENTICATION_POLICY where    AUTHENTICATION_POLICY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  792 */       closeStatement(stmt);
/*  793 */       closeConnection();
/*      */ 
/*  795 */       if (timer != null)
/*  796 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllAuthenticationPolicysELO getAllAuthenticationPolicys()
/*      */   {
/*  829 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  830 */     PreparedStatement stmt = null;
/*  831 */     ResultSet resultSet = null;
/*  832 */     AllAuthenticationPolicysELO results = new AllAuthenticationPolicysELO();
/*      */     try
/*      */     {
/*  835 */       stmt = getConnection().prepareStatement(SQL_ALL_AUTHENTICATION_POLICYS);
/*  836 */       int col = 1;
/*  837 */       resultSet = stmt.executeQuery();
/*  838 */       while (resultSet.next())
/*      */       {
/*  840 */         col = 2;
/*      */ 
/*  843 */         AuthenticationPolicyPK pkAuthenticationPolicy = new AuthenticationPolicyPK(resultSet.getInt(col++));
/*      */ 
/*  846 */         String textAuthenticationPolicy = resultSet.getString(col++);
/*      */ 
/*  850 */         AuthenticationPolicyRefImpl erAuthenticationPolicy = new AuthenticationPolicyRefImpl(pkAuthenticationPolicy, textAuthenticationPolicy);
/*      */ 
/*  855 */         String col1 = resultSet.getString(col++);
/*  856 */         String col2 = resultSet.getString(col++);
/*  857 */         int col3 = resultSet.getInt(col++);
/*  858 */         String col4 = resultSet.getString(col++);
/*  859 */         if (resultSet.wasNull()) {
/*  860 */           col4 = "";
/*      */         }
/*      */ 
/*  863 */         results.add(erAuthenticationPolicy, col1, col2, col3, col4.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  874 */       throw handleSQLException(SQL_ALL_AUTHENTICATION_POLICYS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  878 */       closeResultSet(resultSet);
/*  879 */       closeStatement(stmt);
/*  880 */       closeConnection();
/*      */     }
/*      */ 
/*  883 */     if (timer != null) {
/*  884 */       timer.logDebug("getAllAuthenticationPolicys", " items=" + results.size());
/*      */     }
/*      */ 
/*  888 */     return results;
/*      */   }
/*      */ 
/*      */   public ActiveAuthenticationPolicysELO getActiveAuthenticationPolicys()
/*      */   {
/*  919 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  920 */     PreparedStatement stmt = null;
/*  921 */     ResultSet resultSet = null;
/*  922 */     ActiveAuthenticationPolicysELO results = new ActiveAuthenticationPolicysELO();
/*      */     try
/*      */     {
/*  925 */       stmt = getConnection().prepareStatement(SQL_ACTIVE_AUTHENTICATION_POLICYS);
/*  926 */       int col = 1;
/*  927 */       resultSet = stmt.executeQuery();
/*  928 */       while (resultSet.next())
/*      */       {
/*  930 */         col = 2;
/*      */ 
/*  933 */         AuthenticationPolicyPK pkAuthenticationPolicy = new AuthenticationPolicyPK(resultSet.getInt(col++));
/*      */ 
/*  936 */         String textAuthenticationPolicy = resultSet.getString(col++);
/*      */ 
/*  940 */         AuthenticationPolicyRefImpl erAuthenticationPolicy = new AuthenticationPolicyRefImpl(pkAuthenticationPolicy, textAuthenticationPolicy);
/*      */ 
/*  945 */         String col1 = resultSet.getString(col++);
/*  946 */         String col2 = resultSet.getString(col++);
/*  947 */         int col3 = resultSet.getInt(col++);
/*      */ 
/*  950 */         results.add(erAuthenticationPolicy, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  960 */       throw handleSQLException(SQL_ACTIVE_AUTHENTICATION_POLICYS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  964 */       closeResultSet(resultSet);
/*  965 */       closeStatement(stmt);
/*  966 */       closeConnection();
/*      */     }
/*      */ 
/*  969 */     if (timer != null) {
/*  970 */       timer.logDebug("getActiveAuthenticationPolicys", " items=" + results.size());
/*      */     }
/*      */ 
/*  974 */     return results;
/*      */   }
/*      */ 
/*      */   public ActiveAuthenticationPolicyForLogonELO getActiveAuthenticationPolicyForLogon()
/*      */   {
/* 1010 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1011 */     PreparedStatement stmt = null;
/* 1012 */     ResultSet resultSet = null;
/* 1013 */     ActiveAuthenticationPolicyForLogonELO results = new ActiveAuthenticationPolicyForLogonELO();
/*      */     try
/*      */     {
/* 1016 */       stmt = getConnection().prepareStatement(SQL_ACTIVE_AUTHENTICATION_POLICY_FOR_LOGON);
/* 1017 */       int col = 1;
/* 1018 */       resultSet = stmt.executeQuery();
/* 1019 */       while (resultSet.next())
/*      */       {
/* 1021 */         col = 2;
/*      */ 
/* 1024 */         AuthenticationPolicyPK pkAuthenticationPolicy = new AuthenticationPolicyPK(resultSet.getInt(col++));
/*      */ 
/* 1027 */         String textAuthenticationPolicy = resultSet.getString(col++);
/*      */ 
/* 1031 */         AuthenticationPolicyRefImpl erAuthenticationPolicy = new AuthenticationPolicyRefImpl(pkAuthenticationPolicy, textAuthenticationPolicy);
/*      */ 
/* 1036 */         String col1 = resultSet.getString(col++);
/* 1037 */         String col2 = resultSet.getString(col++);
/* 1038 */         int col3 = resultSet.getInt(col++);
/* 1039 */         int col4 = resultSet.getInt(col++);
/* 1040 */         int col5 = resultSet.getInt(col++);
/* 1041 */         int col6 = resultSet.getInt(col++);
/* 1042 */         int col7 = resultSet.getInt(col++);
/* 1043 */         String col8 = resultSet.getString(col++);
/*      */ 
/* 1046 */         results.add(erAuthenticationPolicy, col1, col2, col3, col4, col5, col6, col7, col8);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1061 */       throw handleSQLException(SQL_ACTIVE_AUTHENTICATION_POLICY_FOR_LOGON, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1065 */       closeResultSet(resultSet);
/* 1066 */       closeStatement(stmt);
/* 1067 */       closeConnection();
/*      */     }
/*      */ 
/* 1070 */     if (timer != null) {
/* 1071 */       timer.logDebug("getActiveAuthenticationPolicyForLogon", " items=" + results.size());
/*      */     }
/*      */ 
/* 1075 */     return results;
/*      */   }
/*      */ 
/*      */   public AuthenticationPolicyEVO getDetails(AuthenticationPolicyPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1093 */     return getDetails(new AuthenticationPolicyCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public AuthenticationPolicyEVO getDetails(AuthenticationPolicyCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1107 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1110 */     if (this.mDetails == null) {
/* 1111 */       doLoad(paramCK.getAuthenticationPolicyPK());
/*      */     }
/* 1113 */     else if (!this.mDetails.getPK().equals(paramCK.getAuthenticationPolicyPK())) {
/* 1114 */       doLoad(paramCK.getAuthenticationPolicyPK());
/*      */     }
/* 1116 */     else if (!checkIfValid())
/*      */     {
/* 1118 */       this._log.info("getDetails", "[ALERT] AuthenticationPolicyEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1120 */       doLoad(paramCK.getAuthenticationPolicyPK());
/*      */     }
/*      */ 
/* 1124 */     AuthenticationPolicyEVO details = new AuthenticationPolicyEVO();
/* 1125 */     details = this.mDetails.deepClone();
/*      */ 
/* 1127 */     if (timer != null) {
/* 1128 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1130 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1140 */     boolean stillValid = false;
/* 1141 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1142 */     PreparedStatement stmt = null;
/* 1143 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1146 */       stmt = getConnection().prepareStatement("select VERSION_NUM from AUTHENTICATION_POLICY where   AUTHENTICATION_POLICY_ID = ?");
/* 1147 */       int col = 1;
/* 1148 */       stmt.setInt(col++, this.mDetails.getAuthenticationPolicyId());
/*      */ 
/* 1150 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1152 */       if (!resultSet.next()) {
/* 1153 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1155 */       col = 1;
/* 1156 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1158 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1159 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1163 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from AUTHENTICATION_POLICY where   AUTHENTICATION_POLICY_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1167 */       closeResultSet(resultSet);
/* 1168 */       closeStatement(stmt);
/* 1169 */       closeConnection();
/*      */ 
/* 1171 */       if (timer != null) {
/* 1172 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1175 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public AuthenticationPolicyEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1181 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1183 */     if (!checkIfValid())
/*      */     {
/* 1185 */       this._log.info("getDetails", "AuthenticationPolicy " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1186 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1190 */     AuthenticationPolicyEVO details = this.mDetails.deepClone();
/*      */ 
/* 1192 */     if (timer != null) {
/* 1193 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1195 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1200 */     return "AuthenticationPolicy";
/*      */   }
/*      */ 
/*      */   public AuthenticationPolicyRef getRef(AuthenticationPolicyPK paramAuthenticationPolicyPK)
/*      */     throws ValidationException
/*      */   {
/* 1206 */     AuthenticationPolicyEVO evo = getDetails(paramAuthenticationPolicyPK, "");
/* 1207 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public static boolean isActiveAuthenticationPolicyInternal()
/*      */   {
/* 1221 */     AuthenticationPolicyDAO dao = new AuthenticationPolicyDAO();
/* 1222 */     ActiveAuthenticationPolicysELO elo = dao.getActiveAuthenticationPolicys();
/* 1223 */     Integer technique = (Integer)elo.getValueAt(0, "AuthenticationTechnique");
/* 1224 */     return technique.intValue() == 1;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyDAO
 * JD-Core Version:    0.6.0
 */