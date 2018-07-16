/*     */ package com.cedar.cp.ejb.impl.role;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.api.role.RoleSecurityRef;
/*     */ import com.cedar.cp.dto.role.AllSecurityRolesELO;
/*     */ import com.cedar.cp.dto.role.AllSecurityRolesForRoleELO;
/*     */ import com.cedar.cp.dto.role.RoleSecurityCK;
/*     */ import com.cedar.cp.dto.role.RoleSecurityPK;
/*     */ import com.cedar.cp.dto.role.RoleSecurityRefImpl;
/*     */ import com.cedar.cp.dto.role.RoleSecurityRelPK;
/*     */ import com.cedar.cp.dto.role.RoleSecurityRelRefImpl;
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
/*     */ public class RoleSecurityDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select ROLE_SECURITY_ID from ROLE_SECURITY where    ROLE_SECURITY_ID = ? ";
/*     */   private static final String SQL_SELECT_COLUMNS = "select ROLE_SECURITY.ROLE_SECURITY_ID,ROLE_SECURITY.SECURITY_STRING,ROLE_SECURITY.DESCRIPTION,ROLE_SECURITY.UPDATED_BY_USER_ID,ROLE_SECURITY.UPDATED_TIME,ROLE_SECURITY.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from ROLE_SECURITY where    ROLE_SECURITY_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into ROLE_SECURITY ( ROLE_SECURITY_ID,SECURITY_STRING,DESCRIPTION,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)";
/*     */   protected static final String SQL_UPDATE_SEQ_NUM = "update ROLE_SECURITY_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*     */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from ROLE_SECURITY_SEQ";
/*     */   protected static final String SQL_STORE = "update ROLE_SECURITY set SECURITY_STRING = ?,DESCRIPTION = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ROLE_SECURITY_ID = ? ";
/*     */   protected static final String SQL_REMOVE = "delete from ROLE_SECURITY where    ROLE_SECURITY_ID = ? ";
/* 561 */   protected static String SQL_ALL_SECURITY_ROLES = "select 0       ,ROLE_SECURITY.ROLE_SECURITY_ID      ,ROLE_SECURITY.SECURITY_STRING      ,ROLE_SECURITY.SECURITY_STRING      ,ROLE_SECURITY.DESCRIPTION from ROLE_SECURITY where 1=1  order by ROLE_SECURITY.SECURITY_STRING";
/*     */ 
/* 644 */   protected static String SQL_ALL_SECURITY_ROLES_FOR_ROLE = "select 0       ,ROLE_SECURITY.ROLE_SECURITY_ID      ,ROLE_SECURITY.SECURITY_STRING      ,ROLE_SECURITY_REL.ROLE_ID      ,ROLE_SECURITY_REL.ROLE_SECURITY_ID      ,ROLE_SECURITY.SECURITY_STRING      ,ROLE_SECURITY.DESCRIPTION from ROLE_SECURITY    ,ROLE_SECURITY_REL where 1=1  and  ROLE_SECURITY_REL.ROLE_SECURITY_ID = ROLE_SECURITY.ROLE_SECURITY_ID AND ROLE_SECURITY_REL.ROLE_ID = ?";
/*     */   protected RoleSecurityEVO mDetails;
/*     */ 
/*     */   public RoleSecurityDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public RoleSecurityDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public RoleSecurityDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected RoleSecurityPK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(RoleSecurityEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   public RoleSecurityEVO setAndGetDetails(RoleSecurityEVO details, String dependants)
/*     */   {
/*  86 */     setDetails(details);
/*  87 */     generateKeys();
/*  88 */     return this.mDetails.deepClone();
/*     */   }
/*     */ 
/*     */   public RoleSecurityPK create()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/*  97 */     doCreate();
/*     */ 
/*  99 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void load(RoleSecurityPK pk)
/*     */     throws ValidationException
/*     */   {
/* 109 */     doLoad(pk);
/*     */   }
/*     */ 
/*     */   public void store()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 118 */     doStore();
/*     */   }
/*     */ 
/*     */   public void remove()
/*     */   {
/* 127 */     doRemove();
/*     */   }
/*     */ 
/*     */   public RoleSecurityPK findByPrimaryKey(RoleSecurityPK pk_)
/*     */     throws ValidationException
/*     */   {
/* 136 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 137 */     if (exists(pk_))
/*     */     {
/* 139 */       if (timer != null) {
/* 140 */         timer.logDebug("findByPrimaryKey", pk_);
/*     */       }
/* 142 */       return pk_;
/*     */     }
/*     */ 
/* 145 */     throw new ValidationException(pk_ + " not found");
/*     */   }
/*     */ 
/*     */   protected boolean exists(RoleSecurityPK pk)
/*     */   {
/* 163 */     PreparedStatement stmt = null;
/* 164 */     ResultSet resultSet = null;
/* 165 */     boolean returnValue = false;
/*     */     try
/*     */     {
/* 169 */       stmt = getConnection().prepareStatement("select ROLE_SECURITY_ID from ROLE_SECURITY where    ROLE_SECURITY_ID = ? ");
/*     */ 
/* 171 */       int col = 1;
/* 172 */       stmt.setInt(col++, pk.getRoleSecurityId());
/*     */ 
/* 174 */       resultSet = stmt.executeQuery();
/*     */ 
/* 176 */       if (!resultSet.next())
/* 177 */         returnValue = false;
/*     */       else
/* 179 */         returnValue = true;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 183 */       throw handleSQLException(pk, "select ROLE_SECURITY_ID from ROLE_SECURITY where    ROLE_SECURITY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 187 */       closeResultSet(resultSet);
/* 188 */       closeStatement(stmt);
/* 189 */       closeConnection();
/*     */     }
/* 191 */     return returnValue;
/*     */   }
/*     */ 
/*     */   private RoleSecurityEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 208 */     int col = 1;
/* 209 */     RoleSecurityEVO evo = new RoleSecurityEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++));
/*     */ 
/* 215 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 216 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 217 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 218 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(RoleSecurityEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 223 */     int col = startCol_;
/* 224 */     stmt_.setInt(col++, evo_.getRoleSecurityId());
/* 225 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(RoleSecurityEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 230 */     int col = startCol_;
/* 231 */     stmt_.setString(col++, evo_.getSecurityString());
/* 232 */     stmt_.setString(col++, evo_.getDescription());
/* 233 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 234 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 235 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 236 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(RoleSecurityPK pk)
/*     */     throws ValidationException
/*     */   {
/* 252 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 254 */     PreparedStatement stmt = null;
/* 255 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 259 */       stmt = getConnection().prepareStatement("select ROLE_SECURITY.ROLE_SECURITY_ID,ROLE_SECURITY.SECURITY_STRING,ROLE_SECURITY.DESCRIPTION,ROLE_SECURITY.UPDATED_BY_USER_ID,ROLE_SECURITY.UPDATED_TIME,ROLE_SECURITY.CREATED_TIME from ROLE_SECURITY where    ROLE_SECURITY_ID = ? ");
/*     */ 
/* 262 */       int col = 1;
/* 263 */       stmt.setInt(col++, pk.getRoleSecurityId());
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
/* 277 */       throw handleSQLException(pk, "select ROLE_SECURITY.ROLE_SECURITY_ID,ROLE_SECURITY.SECURITY_STRING,ROLE_SECURITY.DESCRIPTION,ROLE_SECURITY.UPDATED_BY_USER_ID,ROLE_SECURITY.UPDATED_TIME,ROLE_SECURITY.CREATED_TIME from ROLE_SECURITY where    ROLE_SECURITY_ID = ? ", sqle);
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
/* 315 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 316 */     generateKeys();
/*     */ 
/* 318 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 323 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 324 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 325 */       stmt = getConnection().prepareStatement("insert into ROLE_SECURITY ( ROLE_SECURITY_ID,SECURITY_STRING,DESCRIPTION,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)");
/*     */ 
/* 328 */       int col = 1;
/* 329 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 330 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 333 */       int resultCount = stmt.executeUpdate();
/* 334 */       if (resultCount != 1)
/*     */       {
/* 336 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 339 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 343 */       throw handleSQLException(this.mDetails.getPK(), "insert into ROLE_SECURITY ( ROLE_SECURITY_ID,SECURITY_STRING,DESCRIPTION,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 347 */       closeStatement(stmt);
/* 348 */       closeConnection();
/*     */ 
/* 350 */       if (timer != null)
/* 351 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int reserveIds(int insertCount)
/*     */   {
/* 371 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 373 */     PreparedStatement stmt = null;
/* 374 */     ResultSet resultSet = null;
/* 375 */     String sqlString = null;
/*     */     try
/*     */     {
/* 380 */       sqlString = "update ROLE_SECURITY_SEQ set SEQ_NUM = SEQ_NUM + ?";
/* 381 */       stmt = getConnection().prepareStatement("update ROLE_SECURITY_SEQ set SEQ_NUM = SEQ_NUM + ?");
/* 382 */       stmt.setInt(1, insertCount);
/*     */ 
/* 384 */       int resultCount = stmt.executeUpdate();
/* 385 */       if (resultCount != 1) {
/* 386 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*     */       }
/* 388 */       closeStatement(stmt);
/*     */ 
/* 391 */       sqlString = "select SEQ_NUM from ROLE_SECURITY_SEQ";
/* 392 */       stmt = getConnection().prepareStatement("select SEQ_NUM from ROLE_SECURITY_SEQ");
/* 393 */       resultSet = stmt.executeQuery();
/* 394 */       if (!resultSet.next())
/* 395 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/* 396 */       int latestKey = resultSet.getInt(1);
/*     */ 
/* 398 */       int i = latestKey - insertCount;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 402 */       throw handleSQLException(sqlString, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 406 */       closeResultSet(resultSet);
/* 407 */       closeStatement(stmt);
/* 408 */       closeConnection();
/*     */ 
/* 410 */       if (timer != null)
/* 411 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/* 411 */     }
/*     */   }
/*     */ 
/*     */   public RoleSecurityPK generateKeys()
/*     */   {
/* 421 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 423 */     int insertCount = this.mDetails.getInsertCount(0);
/*     */ 
/* 426 */     if (insertCount == 0) {
/* 427 */       return this.mDetails.getPK();
/*     */     }
/* 429 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*     */ 
/* 431 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 453 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 455 */     generateKeys();
/*     */ 
/* 460 */     PreparedStatement stmt = null;
/*     */ 
/* 462 */     boolean mainChanged = this.mDetails.isModified();
/* 463 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 466 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 469 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 470 */         stmt = getConnection().prepareStatement("update ROLE_SECURITY set SECURITY_STRING = ?,DESCRIPTION = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ROLE_SECURITY_ID = ? ");
/*     */ 
/* 473 */         int col = 1;
/* 474 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 475 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 478 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 481 */         if (resultCount != 1) {
/* 482 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 485 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 494 */       throw handleSQLException(getPK(), "update ROLE_SECURITY set SECURITY_STRING = ?,DESCRIPTION = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ROLE_SECURITY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 498 */       closeStatement(stmt);
/* 499 */       closeConnection();
/*     */ 
/* 501 */       if ((timer != null) && (
/* 502 */         (mainChanged) || (dependantChanged)))
/* 503 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doRemove()
/*     */   {
/* 521 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 526 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 531 */       stmt = getConnection().prepareStatement("delete from ROLE_SECURITY where    ROLE_SECURITY_ID = ? ");
/*     */ 
/* 534 */       int col = 1;
/* 535 */       stmt.setInt(col++, this.mDetails.getRoleSecurityId());
/*     */ 
/* 537 */       int resultCount = stmt.executeUpdate();
/*     */ 
/* 539 */       if (resultCount != 1) {
/* 540 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 546 */       throw handleSQLException(getPK(), "delete from ROLE_SECURITY where    ROLE_SECURITY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 550 */       closeStatement(stmt);
/* 551 */       closeConnection();
/*     */ 
/* 553 */       if (timer != null)
/* 554 */         timer.logDebug("remove", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllSecurityRolesELO getAllSecurityRoles()
/*     */   {
/* 585 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 586 */     PreparedStatement stmt = null;
/* 587 */     ResultSet resultSet = null;
/* 588 */     AllSecurityRolesELO results = new AllSecurityRolesELO();
/*     */     try
/*     */     {
/* 591 */       stmt = getConnection().prepareStatement(SQL_ALL_SECURITY_ROLES);
/* 592 */       int col = 1;
/* 593 */       resultSet = stmt.executeQuery();
/* 594 */       while (resultSet.next())
/*     */       {
/* 596 */         col = 2;
/*     */ 
/* 599 */         RoleSecurityPK pkRoleSecurity = new RoleSecurityPK(resultSet.getInt(col++));
/*     */ 
/* 602 */         String textRoleSecurity = resultSet.getString(col++);
/*     */ 
/* 606 */         RoleSecurityRefImpl erRoleSecurity = new RoleSecurityRefImpl(pkRoleSecurity, textRoleSecurity);
/*     */ 
/* 611 */         String col1 = resultSet.getString(col++);
/* 612 */         String col2 = resultSet.getString(col++);
/*     */ 
/* 615 */         results.add(erRoleSecurity, col1, col2);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 624 */       throw handleSQLException(SQL_ALL_SECURITY_ROLES, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 628 */       closeResultSet(resultSet);
/* 629 */       closeStatement(stmt);
/* 630 */       closeConnection();
/*     */     }
/*     */ 
/* 633 */     if (timer != null) {
/* 634 */       timer.logDebug("getAllSecurityRoles", " items=" + results.size());
/*     */     }
/*     */ 
/* 638 */     return results;
/*     */   }
/*     */ 
/*     */   public AllSecurityRolesForRoleELO getAllSecurityRolesForRole(int param1)
/*     */   {
/* 674 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 675 */     PreparedStatement stmt = null;
/* 676 */     ResultSet resultSet = null;
/* 677 */     AllSecurityRolesForRoleELO results = new AllSecurityRolesForRoleELO();
/*     */     try
/*     */     {
/* 680 */       stmt = getConnection().prepareStatement(SQL_ALL_SECURITY_ROLES_FOR_ROLE);
/* 681 */       int col = 1;
/* 682 */       stmt.setInt(col++, param1);
/* 683 */       resultSet = stmt.executeQuery();
/* 684 */       while (resultSet.next())
/*     */       {
/* 686 */         col = 2;
/*     */ 
/* 689 */         RoleSecurityPK pkRoleSecurity = new RoleSecurityPK(resultSet.getInt(col++));
/*     */ 
/* 692 */         String textRoleSecurity = resultSet.getString(col++);
/*     */ 
/* 695 */         RoleSecurityRelPK pkRoleSecurityRel = new RoleSecurityRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 699 */         String textRoleSecurityRel = "";
/*     */ 
/* 702 */         RoleSecurityRefImpl erRoleSecurity = new RoleSecurityRefImpl(pkRoleSecurity, textRoleSecurity);
/*     */ 
/* 708 */         RoleSecurityRelRefImpl erRoleSecurityRel = new RoleSecurityRelRefImpl(pkRoleSecurityRel, textRoleSecurityRel);
/*     */ 
/* 713 */         String col1 = resultSet.getString(col++);
/* 714 */         String col2 = resultSet.getString(col++);
/*     */ 
/* 717 */         results.add(erRoleSecurity, erRoleSecurityRel, col1, col2);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 727 */       throw handleSQLException(SQL_ALL_SECURITY_ROLES_FOR_ROLE, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 731 */       closeResultSet(resultSet);
/* 732 */       closeStatement(stmt);
/* 733 */       closeConnection();
/*     */     }
/*     */ 
/* 736 */     if (timer != null) {
/* 737 */       timer.logDebug("getAllSecurityRolesForRole", " RoleId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 742 */     return results;
/*     */   }
/*     */ 
/*     */   public RoleSecurityEVO getDetails(RoleSecurityPK pk, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 760 */     return getDetails(new RoleSecurityCK(pk), dependants);
/*     */   }
/*     */ 
/*     */   public RoleSecurityEVO getDetails(RoleSecurityCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 774 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 777 */     if (this.mDetails == null) {
/* 778 */       doLoad(paramCK.getRoleSecurityPK());
/*     */     }
/* 780 */     else if (!this.mDetails.getPK().equals(paramCK.getRoleSecurityPK())) {
/* 781 */       doLoad(paramCK.getRoleSecurityPK());
/*     */     }
/*     */ 
/* 784 */     RoleSecurityEVO details = new RoleSecurityEVO();
/* 785 */     details = this.mDetails.deepClone();
/*     */ 
/* 787 */     if (timer != null) {
/* 788 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 790 */     return details;
/*     */   }
/*     */ 
/*     */   public RoleSecurityEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 796 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 800 */     RoleSecurityEVO details = this.mDetails.deepClone();
/*     */ 
/* 802 */     if (timer != null) {
/* 803 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 805 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 810 */     return "RoleSecurity";
/*     */   }
/*     */ 
/*     */   public RoleSecurityRef getRef(RoleSecurityPK paramRoleSecurityPK)
/*     */     throws ValidationException
/*     */   {
/* 816 */     RoleSecurityEVO evo = getDetails(paramRoleSecurityPK, "");
/* 817 */     return evo.getEntityRef();
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.role.RoleSecurityDAO
 * JD-Core Version:    0.6.0
 */