/*      */ package com.cedar.cp.ejb.impl.role;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.role.RoleRef;
import com.cedar.cp.dto.role.AllHiddenRolesELO;
/*      */ import com.cedar.cp.dto.role.AllRolesELO;
/*      */ import com.cedar.cp.dto.role.AllRolesForUserELO;
/*      */ import com.cedar.cp.dto.role.RoleCK;
/*      */ import com.cedar.cp.dto.role.RolePK;
/*      */ import com.cedar.cp.dto.role.RoleRefImpl;
/*      */ import com.cedar.cp.dto.role.RoleSecurityRelCK;
/*      */ import com.cedar.cp.dto.role.RoleSecurityRelPK;
/*      */ import com.cedar.cp.dto.user.UserRolePK;
/*      */ import com.cedar.cp.dto.user.UserRoleRefImpl;
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
/*      */ public class RoleDAO extends AbstractDAO
/*      */ {
/*   35 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select ROLE_ID from ROLE where    ROLE_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select ROLE.ROLE_ID,ROLE.VIS_ID,ROLE.DESCRIPTION,ROLE.VERSION_NUM,ROLE.UPDATED_BY_USER_ID,ROLE.UPDATED_TIME,ROLE.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from ROLE where    ROLE_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into ROLE ( ROLE_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update ROLE_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from ROLE_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_VISIDDUPLICATECHECK = "select count(*) from ROLE where    VIS_ID = ? and not(    ROLE_ID = ? )";
/*      */   protected static final String SQL_STORE = "update ROLE set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ROLE_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from ROLE where ROLE_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from ROLE where    ROLE_ID = ? ";
/*  701 */   protected static String SQL_ALL_ROLES = "select 0       ,ROLE.ROLE_ID      ,ROLE.VIS_ID      ,ROLE.VIS_ID      ,ROLE.DESCRIPTION from ROLE where MENU IS NULL  order by ROLE.ROLE_ID";
/*      */ 	 protected static String SQL_ALL_HIDDEN_ROLES = "select ROLE.ROLE_ID, ROLE.VIS_ID, ROLE.DESCRIPTION, ROLE.version_num from ROLE where MENU IS NOT NULL order by ROLE.VERSION_NUM, ROLE.SORT, ROLE.ROLE_ID";
/*  784 */   protected static String SQL_ALL_ROLES_FOR_USER = "select 0       ,ROLE.ROLE_ID      ,ROLE.VIS_ID      ,USER_ROLE.USER_ID      ,USER_ROLE.ROLE_ID      ,ROLE.VIS_ID      ,ROLE.DESCRIPTION from ROLE    ,USER_ROLE where MENU IS NULL  and  USER_ROLE.ROLE_ID = ROLE.ROLE_ID AND USER_ROLE.USER_ID = ? order by ROLE.VIS_ID";
/*      */   protected static String SQL_ALL_HIDDEN_ROLES_FOR_USER = "select 0       ,ROLE.ROLE_ID      ,ROLE.VIS_ID      ,USER_ROLE.USER_ID      ,USER_ROLE.ROLE_ID      ,ROLE.VIS_ID      ,ROLE.DESCRIPTION from ROLE    ,USER_ROLE where MENU IS NOT NULL  and  USER_ROLE.ROLE_ID = ROLE.ROLE_ID AND USER_ROLE.USER_ID = ? order by ROLE.VIS_ID";
/*  887 */   private static String[][] SQL_DELETE_CHILDREN = { { "ROLE_SECURITY_REL", "delete from ROLE_SECURITY_REL where     ROLE_SECURITY_REL.ROLE_ID = ? " } };
/*      */ 
/*  896 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  900 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and ROLE.ROLE_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from ROLE where   ROLE_ID = ?";
/*      */   public static final String SQL_GET_ROLE_SECURITY_REL_REF = "select 0,ROLE.ROLE_ID from ROLE_SECURITY_REL,ROLE where 1=1 and ROLE_SECURITY_REL.ROLE_ID = ? and ROLE_SECURITY_REL.ROLE_SECURITY_ID = ? and ROLE_SECURITY_REL.ROLE_ID = ROLE.ROLE_ID";
/*      */   protected RoleSecurityRelDAO mRoleSecurityRelDAO;
/*      */   protected RoleEVO mDetails;
/*      */ 
/*      */   public RoleDAO(Connection connection)
/*      */   {
/*   42 */     super(connection);
/*      */   }
/*      */ 
/*      */   public RoleDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public RoleDAO(DataSource ds)
/*      */   {
/*   58 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected RolePK getPK()
/*      */   {
/*   66 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(RoleEVO details)
/*      */   {
/*   75 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public RoleEVO setAndGetDetails(RoleEVO details, String dependants)
/*      */   {
/*   86 */     setDetails(details);
/*   87 */     generateKeys();
/*   88 */     getDependants(this.mDetails, dependants);
/*   89 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public RolePK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   98 */     doCreate();
/*      */ 
/*  100 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(RolePK pk)
/*      */     throws ValidationException
/*      */   {
/*  110 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  119 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  128 */     doRemove();
/*      */   }
/*      */ 
/*      */   public RolePK findByPrimaryKey(RolePK pk_)
/*      */     throws ValidationException
/*      */   {
/*  137 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  138 */     if (exists(pk_))
/*      */     {
/*  140 */       if (timer != null) {
/*  141 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  143 */       return pk_;
/*      */     }
/*      */ 
/*  146 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(RolePK pk)
/*      */   {
/*  164 */     PreparedStatement stmt = null;
/*  165 */     ResultSet resultSet = null;
/*  166 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  170 */       stmt = getConnection().prepareStatement("select ROLE_ID from ROLE where    ROLE_ID = ? ");
/*      */ 
/*  172 */       int col = 1;
/*  173 */       stmt.setInt(col++, pk.getRoleId());
/*      */ 
/*  175 */       resultSet = stmt.executeQuery();
/*      */ 
/*  177 */       if (!resultSet.next())
/*  178 */         returnValue = false;
/*      */       else
/*  180 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  184 */       throw handleSQLException(pk, "select ROLE_ID from ROLE where    ROLE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  188 */       closeResultSet(resultSet);
/*  189 */       closeStatement(stmt);
/*  190 */       closeConnection();
/*      */     }
/*  192 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private RoleEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  210 */     int col = 1;
/*  211 */     RoleEVO evo = new RoleEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  219 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  220 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  221 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  222 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(RoleEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  227 */     int col = startCol_;
/*  228 */     stmt_.setInt(col++, evo_.getRoleId());
/*  229 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(RoleEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  234 */     int col = startCol_;
/*  235 */     stmt_.setString(col++, evo_.getVisId());
/*  236 */     stmt_.setString(col++, evo_.getDescription());
/*  237 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  238 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  239 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  240 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  241 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(RolePK pk)
/*      */     throws ValidationException
/*      */   {
/*  257 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  259 */     PreparedStatement stmt = null;
/*  260 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  264 */       stmt = getConnection().prepareStatement("select ROLE.ROLE_ID,ROLE.VIS_ID,ROLE.DESCRIPTION,ROLE.VERSION_NUM,ROLE.UPDATED_BY_USER_ID,ROLE.UPDATED_TIME,ROLE.CREATED_TIME from ROLE where    ROLE_ID = ? ");
/*      */ 
/*  267 */       int col = 1;
/*  268 */       stmt.setInt(col++, pk.getRoleId());
/*      */ 
/*  270 */       resultSet = stmt.executeQuery();
/*      */ 
/*  272 */       if (!resultSet.next()) {
/*  273 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  276 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  277 */       if (this.mDetails.isModified())
/*  278 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  282 */       throw handleSQLException(pk, "select ROLE.ROLE_ID,ROLE.VIS_ID,ROLE.DESCRIPTION,ROLE.VERSION_NUM,ROLE.UPDATED_BY_USER_ID,ROLE.UPDATED_TIME,ROLE.CREATED_TIME from ROLE where    ROLE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  286 */       closeResultSet(resultSet);
/*  287 */       closeStatement(stmt);
/*  288 */       closeConnection();
/*      */ 
/*  290 */       if (timer != null)
/*  291 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  322 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  323 */     generateKeys();
/*      */ 
/*  325 */     this.mDetails.postCreateInit();
/*      */ 
/*  327 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  332 */       duplicateValueCheckVisIdDuplicateCheck();
/*      */ 
/*  334 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  335 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  336 */       stmt = getConnection().prepareStatement("insert into ROLE ( ROLE_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*      */ 
/*  339 */       int col = 1;
/*  340 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  341 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  344 */       int resultCount = stmt.executeUpdate();
/*  345 */       if (resultCount != 1)
/*      */       {
/*  347 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  350 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  354 */       throw handleSQLException(this.mDetails.getPK(), "insert into ROLE ( ROLE_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  358 */       closeStatement(stmt);
/*  359 */       closeConnection();
/*      */ 
/*  361 */       if (timer != null) {
/*  362 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  368 */       getRoleSecurityRelDAO().update(this.mDetails.getRoleSecurityMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  374 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  394 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  396 */     PreparedStatement stmt = null;
/*  397 */     ResultSet resultSet = null;
/*  398 */     String sqlString = null;
/*      */     try
/*      */     {
/*  403 */       sqlString = "update ROLE_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  404 */       stmt = getConnection().prepareStatement("update ROLE_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  405 */       stmt.setInt(1, insertCount);
/*      */ 
/*  407 */       int resultCount = stmt.executeUpdate();
/*  408 */       if (resultCount != 1) {
/*  409 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  411 */       closeStatement(stmt);
/*      */ 
/*  414 */       sqlString = "select SEQ_NUM from ROLE_SEQ";
/*  415 */       stmt = getConnection().prepareStatement("select SEQ_NUM from ROLE_SEQ");
/*  416 */       resultSet = stmt.executeQuery();
/*  417 */       if (!resultSet.next())
/*  418 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  419 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  421 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  425 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  429 */       closeResultSet(resultSet);
/*  430 */       closeStatement(stmt);
/*  431 */       closeConnection();
/*      */ 
/*  433 */       if (timer != null)
/*  434 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  434 */     }
/*      */   }
/*      */ 
/*      */   public RolePK generateKeys()
/*      */   {
/*  444 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  446 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  449 */     if (insertCount == 0) {
/*  450 */       return this.mDetails.getPK();
/*      */     }
/*  452 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  454 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckVisIdDuplicateCheck()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  467 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  468 */     PreparedStatement stmt = null;
/*  469 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  473 */       stmt = getConnection().prepareStatement("select count(*) from ROLE where    VIS_ID = ? and not(    ROLE_ID = ? )");
/*      */ 
/*  476 */       int col = 1;
/*  477 */       stmt.setString(col++, this.mDetails.getVisId());
/*  478 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  481 */       resultSet = stmt.executeQuery();
/*      */ 
/*  483 */       if (!resultSet.next()) {
/*  484 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  488 */       col = 1;
/*  489 */       int count = resultSet.getInt(col++);
/*  490 */       if (count > 0) {
/*  491 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " VisIdDuplicateCheck");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  497 */       throw handleSQLException(getPK(), "select count(*) from ROLE where    VIS_ID = ? and not(    ROLE_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  501 */       closeResultSet(resultSet);
/*  502 */       closeStatement(stmt);
/*  503 */       closeConnection();
/*      */ 
/*  505 */       if (timer != null)
/*  506 */         timer.logDebug("duplicateValueCheckVisIdDuplicateCheck", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  531 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  533 */     generateKeys();
/*      */ 
/*  538 */     PreparedStatement stmt = null;
/*      */ 
/*  540 */     boolean mainChanged = this.mDetails.isModified();
/*  541 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  545 */       if (mainChanged) {
/*  546 */         duplicateValueCheckVisIdDuplicateCheck();
/*      */       }
/*  548 */       if (getRoleSecurityRelDAO().update(this.mDetails.getRoleSecurityMap())) {
/*  549 */         dependantChanged = true;
/*      */       }
/*  551 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  554 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  557 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  558 */         stmt = getConnection().prepareStatement("update ROLE set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ROLE_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  561 */         int col = 1;
/*  562 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  563 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  565 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  568 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  570 */         if (resultCount == 0) {
/*  571 */           checkVersionNum();
/*      */         }
/*  573 */         if (resultCount != 1) {
/*  574 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  577 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  586 */       throw handleSQLException(getPK(), "update ROLE set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ROLE_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  590 */       closeStatement(stmt);
/*  591 */       closeConnection();
/*      */ 
/*  593 */       if ((timer != null) && (
/*  594 */         (mainChanged) || (dependantChanged)))
/*  595 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  607 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  608 */     PreparedStatement stmt = null;
/*  609 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  613 */       stmt = getConnection().prepareStatement("select VERSION_NUM from ROLE where ROLE_ID = ?");
/*      */ 
/*  616 */       int col = 1;
/*  617 */       stmt.setInt(col++, this.mDetails.getRoleId());
/*      */ 
/*  620 */       resultSet = stmt.executeQuery();
/*      */ 
/*  622 */       if (!resultSet.next()) {
/*  623 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  626 */       col = 1;
/*  627 */       int dbVersionNumber = resultSet.getInt(col++);
/*  628 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  629 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  635 */       throw handleSQLException(getPK(), "select VERSION_NUM from ROLE where ROLE_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  639 */       closeStatement(stmt);
/*  640 */       closeResultSet(resultSet);
/*      */ 
/*  642 */       if (timer != null)
/*  643 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  660 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  661 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  666 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  671 */       stmt = getConnection().prepareStatement("delete from ROLE where    ROLE_ID = ? ");
/*      */ 
/*  674 */       int col = 1;
/*  675 */       stmt.setInt(col++, this.mDetails.getRoleId());
/*      */ 
/*  677 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  679 */       if (resultCount != 1) {
/*  680 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  686 */       throw handleSQLException(getPK(), "delete from ROLE where    ROLE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  690 */       closeStatement(stmt);
/*  691 */       closeConnection();
/*      */ 
/*  693 */       if (timer != null)
/*  694 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllRolesELO getAllRoles()
/*      */   {
/*  725 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  726 */     PreparedStatement stmt = null;
/*  727 */     ResultSet resultSet = null;
/*  728 */     AllRolesELO results = new AllRolesELO();
/*      */     try
/*      */     {
/*  731 */       stmt = getConnection().prepareStatement(SQL_ALL_ROLES);
/*  732 */       int col = 1;
/*  733 */       resultSet = stmt.executeQuery();
/*  734 */       while (resultSet.next())
/*      */       {
/*  736 */         col = 2;
/*      */ 
/*  739 */         RolePK pkRole = new RolePK(resultSet.getInt(col++));
/*      */ 
/*  742 */         String textRole = resultSet.getString(col++);
/*      */ 
/*  746 */         RoleRefImpl erRole = new RoleRefImpl(pkRole, textRole);
/*      */ 
/*  751 */         String col1 = resultSet.getString(col++);
/*  752 */         String col2 = resultSet.getString(col++);
/*      */ 
/*  755 */         results.add(erRole, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  764 */       throw handleSQLException(SQL_ALL_ROLES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  768 */       closeResultSet(resultSet);
/*  769 */       closeStatement(stmt);
/*  770 */       closeConnection();
/*      */     }
/*      */ 
/*  773 */     if (timer != null) {
/*  774 */       timer.logDebug("getAllRoles", " items=" + results.size());
/*      */     }
/*      */ 
/*  778 */     return results;
/*      */   }

			 public AllHiddenRolesELO getAllHiddenRoles()
/*      */   {
/*  725 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  726 */     PreparedStatement stmt = null;
/*  727 */     ResultSet resultSet = null;
/*  728 */     AllHiddenRolesELO results = new AllHiddenRolesELO();
/*      */     try
/*      */     {
/*  731 */       stmt = getConnection().prepareStatement(SQL_ALL_HIDDEN_ROLES);
/*  732 */       
/*  733 */       resultSet = stmt.executeQuery();
/*  734 */       while (resultSet.next())
/*      */       {
/*      */ 		   int col = 1;
/*  739 */         RolePK pkRole = new RolePK(resultSet.getInt(col++));
/*      */ 
/*  742 */         String visId = resultSet.getString(col++);
/*      */ 
/*  746 */         RoleRefImpl erRole = new RoleRefImpl(pkRole, visId);
/*      */ 
/*  751 */         String description = resultSet.getString(col++);
/*  752 */         int parent = resultSet.getInt(col++);
/*      */ 
/*  755 */         results.add(erRole, description, parent);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  764 */       throw handleSQLException(SQL_ALL_HIDDEN_ROLES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  768 */       closeResultSet(resultSet);
/*  769 */       closeStatement(stmt);
/*  770 */       closeConnection();
/*      */     }
/*      */ 
/*  773 */     if (timer != null) {
/*  774 */       timer.logDebug("getAllHiddenRoles", " items=" + results.size());
/*      */     }
/*      */ 
/*  778 */     return results;
/*      */   }
/*      */ 
/*      */   public AllRolesForUserELO getAllRolesForUser(int param1)
/*      */   {
/*  814 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  815 */     PreparedStatement stmt = null;
/*  816 */     ResultSet resultSet = null;
/*  817 */     AllRolesForUserELO results = new AllRolesForUserELO();
/*      */     try
/*      */     {
/*  820 */       stmt = getConnection().prepareStatement(SQL_ALL_ROLES_FOR_USER);
/*  821 */       int col = 1;
/*  822 */       stmt.setInt(col++, param1);
/*  823 */       resultSet = stmt.executeQuery();
/*  824 */       while (resultSet.next())
/*      */       {
/*  826 */         col = 2;
/*      */ 
/*  829 */         RolePK pkRole = new RolePK(resultSet.getInt(col++));
/*      */ 
/*  832 */         String textRole = resultSet.getString(col++);
/*      */ 
/*  835 */         UserRolePK pkUserRole = new UserRolePK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  839 */         String textUserRole = "";
/*      */ 
/*  842 */         RoleRefImpl erRole = new RoleRefImpl(pkRole, textRole);
/*      */ 
/*  848 */         UserRoleRefImpl erUserRole = new UserRoleRefImpl(pkUserRole, textUserRole);
/*      */ 
/*  853 */         String col1 = resultSet.getString(col++);
/*  854 */         String col2 = resultSet.getString(col++);
/*      */ 
/*  857 */         results.add(erRole, erUserRole, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  867 */       throw handleSQLException(SQL_ALL_ROLES_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  871 */       closeResultSet(resultSet);
/*  872 */       closeStatement(stmt);
/*  873 */       closeConnection();
/*      */     }
/*      */ 
/*  876 */     if (timer != null) {
/*  877 */       timer.logDebug("getAllRolesForUser", " UserId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  882 */     return results;
/*      */   }

			 public AllRolesForUserELO getAllHiddenRolesForUser(int param1)
/*      */   {
/*  814 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  815 */     PreparedStatement stmt = null;
/*  816 */     ResultSet resultSet = null;
/*  817 */     AllRolesForUserELO results = new AllRolesForUserELO();
/*      */     try
/*      */     {
/*  820 */       stmt = getConnection().prepareStatement(SQL_ALL_HIDDEN_ROLES_FOR_USER);
/*  821 */       int col = 1;
/*  822 */       stmt.setInt(col++, param1);
/*  823 */       resultSet = stmt.executeQuery();
/*  824 */       while (resultSet.next())
/*      */       {
/*  826 */         col = 2;
/*      */ 
/*  829 */         RolePK pkRole = new RolePK(resultSet.getInt(col++));
/*      */ 
/*  832 */         String textRole = resultSet.getString(col++);
/*      */ 
/*  835 */         UserRolePK pkUserRole = new UserRolePK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  839 */         String textUserRole = "";
/*      */ 
/*  842 */         RoleRefImpl erRole = new RoleRefImpl(pkRole, textRole);
/*      */ 
/*  848 */         UserRoleRefImpl erUserRole = new UserRoleRefImpl(pkUserRole, textUserRole);
/*      */ 
/*  853 */         String col1 = resultSet.getString(col++);
/*  854 */         String col2 = resultSet.getString(col++);
/*      */ 
/*  857 */         results.add(erRole, erUserRole, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  867 */       throw handleSQLException(SQL_ALL_HIDDEN_ROLES_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  871 */       closeResultSet(resultSet);
/*  872 */       closeStatement(stmt);
/*  873 */       closeConnection();
/*      */     }
/*      */ 
/*  876 */     if (timer != null) {
/*  877 */       timer.logDebug("getAllHiddenRolesForUser", " UserId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  882 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(RolePK pk)
/*      */   {
/*  909 */     Set emptyStrings = Collections.emptySet();
/*  910 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(RolePK pk, Set<String> exclusionTables)
/*      */   {
/*  916 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  918 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  920 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  922 */       PreparedStatement stmt = null;
/*      */ 
/*  924 */       int resultCount = 0;
/*  925 */       String s = null;
/*      */       try
/*      */       {
/*  928 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  930 */         if (this._log.isDebugEnabled()) {
/*  931 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  933 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  936 */         int col = 1;
/*  937 */         stmt.setInt(col++, pk.getRoleId());
/*      */ 
/*  940 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  944 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  948 */         closeStatement(stmt);
/*  949 */         closeConnection();
/*      */ 
/*  951 */         if (timer != null) {
/*  952 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  956 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  958 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  960 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  962 */       PreparedStatement stmt = null;
/*      */ 
/*  964 */       int resultCount = 0;
/*  965 */       String s = null;
/*      */       try
/*      */       {
/*  968 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  970 */         if (this._log.isDebugEnabled()) {
/*  971 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  973 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  976 */         int col = 1;
/*  977 */         stmt.setInt(col++, pk.getRoleId());
/*      */ 
/*  980 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  984 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  988 */         closeStatement(stmt);
/*  989 */         closeConnection();
/*      */ 
/*  991 */         if (timer != null)
/*  992 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public RoleEVO getDetails(RolePK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1011 */     return getDetails(new RoleCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public RoleEVO getDetails(RoleCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1028 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1031 */     if (this.mDetails == null) {
/* 1032 */       doLoad(paramCK.getRolePK());
/*      */     }
/* 1034 */     else if (!this.mDetails.getPK().equals(paramCK.getRolePK())) {
/* 1035 */       doLoad(paramCK.getRolePK());
/*      */     }
/* 1037 */     else if (!checkIfValid())
/*      */     {
/* 1039 */       this._log.info("getDetails", "[ALERT] RoleEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1041 */       doLoad(paramCK.getRolePK());
/*      */     }
/*      */ 
/* 1051 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isRoleSecurityAllItemsLoaded()))
/*      */     {
/* 1056 */       this.mDetails.setRoleSecurity(getRoleSecurityRelDAO().getAll(this.mDetails.getRoleId(), dependants, this.mDetails.getRoleSecurity()));
/*      */ 
/* 1063 */       this.mDetails.setRoleSecurityAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1066 */     if ((paramCK instanceof RoleSecurityRelCK))
/*      */     {
/* 1068 */       if (this.mDetails.getRoleSecurity() == null) {
/* 1069 */         this.mDetails.loadRoleSecurityItem(getRoleSecurityRelDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1072 */         RoleSecurityRelPK pk = ((RoleSecurityRelCK)paramCK).getRoleSecurityRelPK();
/* 1073 */         RoleSecurityRelEVO evo = this.mDetails.getRoleSecurityItem(pk);
/* 1074 */         if (evo == null) {
/* 1075 */           this.mDetails.loadRoleSecurityItem(getRoleSecurityRelDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1080 */     RoleEVO details = new RoleEVO();
/* 1081 */     details = this.mDetails.deepClone();
/*      */ 
/* 1083 */     if (timer != null) {
/* 1084 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1086 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1096 */     boolean stillValid = false;
/* 1097 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1098 */     PreparedStatement stmt = null;
/* 1099 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1102 */       stmt = getConnection().prepareStatement("select VERSION_NUM from ROLE where   ROLE_ID = ?");
/* 1103 */       int col = 1;
/* 1104 */       stmt.setInt(col++, this.mDetails.getRoleId());
/*      */ 
/* 1106 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1108 */       if (!resultSet.next()) {
/* 1109 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1111 */       col = 1;
/* 1112 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1114 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1115 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1119 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from ROLE where   ROLE_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1123 */       closeResultSet(resultSet);
/* 1124 */       closeStatement(stmt);
/* 1125 */       closeConnection();
/*      */ 
/* 1127 */       if (timer != null) {
/* 1128 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1131 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public RoleEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1137 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1139 */     if (!checkIfValid())
/*      */     {
/* 1141 */       this._log.info("getDetails", "Role " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1142 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1146 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1149 */     RoleEVO details = this.mDetails.deepClone();
/*      */ 
/* 1151 */     if (timer != null) {
/* 1152 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1154 */     return details;
/*      */   }
/*      */ 
/*      */   protected RoleSecurityRelDAO getRoleSecurityRelDAO()
/*      */   {
/* 1163 */     if (this.mRoleSecurityRelDAO == null)
/*      */     {
/* 1165 */       if (this.mDataSource != null)
/* 1166 */         this.mRoleSecurityRelDAO = new RoleSecurityRelDAO(this.mDataSource);
/*      */       else {
/* 1168 */         this.mRoleSecurityRelDAO = new RoleSecurityRelDAO(getConnection());
/*      */       }
/*      */     }
/* 1171 */     return this.mRoleSecurityRelDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1176 */     return "Role";
/*      */   }
/*      */ 
/*      */   public RoleRef getRef(RolePK paramRolePK)
/*      */     throws ValidationException
/*      */   {
/* 1182 */     RoleEVO evo = getDetails(paramRolePK, "");
/* 1183 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1208 */     if (c == null)
/* 1209 */       return;
/* 1210 */     Iterator iter = c.iterator();
/* 1211 */     while (iter.hasNext())
/*      */     {
/* 1213 */       RoleEVO evo = (RoleEVO)iter.next();
/* 1214 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(RoleEVO evo, String dependants)
/*      */   {
/* 1228 */     if (evo.getRoleId() < 1) {
/* 1229 */       return;
/*      */     }
/*      */ 
/* 1237 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1240 */       if (!evo.isRoleSecurityAllItemsLoaded())
/*      */       {
/* 1242 */         evo.setRoleSecurity(getRoleSecurityRelDAO().getAll(evo.getRoleId(), dependants, evo.getRoleSecurity()));
/*      */ 
/* 1249 */         evo.setRoleSecurityAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.role.RoleDAO
 * JD-Core Version:    0.6.0
 */