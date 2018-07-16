/*      */ package com.cedar.cp.ejb.impl.model;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.AllSecurityGroupsELO;
/*      */ import com.cedar.cp.dto.model.AllSecurityGroupsForUserELO;
/*      */ import com.cedar.cp.dto.model.AllSecurityGroupsUsingAccessDefELO;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.SecurityGroupCK;
/*      */ import com.cedar.cp.dto.model.SecurityGroupPK;
/*      */ import com.cedar.cp.dto.model.SecurityGroupRefImpl;
/*      */ import com.cedar.cp.dto.model.SecurityGroupUserRelCK;
/*      */ import com.cedar.cp.dto.model.SecurityGroupUserRelPK;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class SecurityGroupDAO extends AbstractDAO
/*      */ {
/*   35 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select SECURITY_GROUP.SECURITY_GROUP_ID,SECURITY_GROUP.MODEL_ID,SECURITY_GROUP.VIS_ID,SECURITY_GROUP.DESCRIPTION,SECURITY_GROUP.UPDATE_ACCESS_ID,SECURITY_GROUP.VERSION_NUM,SECURITY_GROUP.UPDATED_BY_USER_ID,SECURITY_GROUP.UPDATED_TIME,SECURITY_GROUP.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from SECURITY_GROUP where    SECURITY_GROUP_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into SECURITY_GROUP ( SECURITY_GROUP_ID,MODEL_ID,VIS_ID,DESCRIPTION,UPDATE_ACCESS_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_SECURITYGROUPNAME = "select count(*) from SECURITY_GROUP where    MODEL_ID = ? AND VIS_ID = ? and not(    SECURITY_GROUP_ID = ? )";
/*      */   protected static final String SQL_STORE = "update SECURITY_GROUP set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,UPDATE_ACCESS_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_GROUP_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from SECURITY_GROUP where SECURITY_GROUP_ID = ?";
/*  464 */   protected static String SQL_ALL_SECURITY_GROUPS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,SECURITY_GROUP.SECURITY_GROUP_ID      ,SECURITY_GROUP.VIS_ID      ,SECURITY_GROUP.DESCRIPTION from SECURITY_GROUP    ,MODEL where 1=1   and SECURITY_GROUP.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID, SECURITY_GROUP.VIS_ID";
/*      */ 
/*  570 */   protected static String SQL_ALL_SECURITY_GROUPS_USING_ACCESS_DEF = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,SECURITY_GROUP.SECURITY_GROUP_ID      ,SECURITY_GROUP.VIS_ID      ,SECURITY_GROUP.SECURITY_GROUP_ID from SECURITY_GROUP    ,MODEL where 1=1   and SECURITY_GROUP.MODEL_ID = MODEL.MODEL_ID  and  SECURITY_GROUP.UPDATE_ACCESS_ID = ?";
/*      */ 
/*  680 */   protected static String SQL_ALL_SECURITY_GROUPS_FOR_USER = "select 0       ,SECURITY_GROUP.SECURITY_GROUP_ID      ,SECURITY_GROUP.UPDATE_ACCESS_ID from SECURITY_GROUP    ,MODEL    ,SECURITY_GROUP_USER_REL where 1=1   and SECURITY_GROUP.MODEL_ID = MODEL.MODEL_ID  and  SECURITY_GROUP.SECURITY_GROUP_ID = SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID AND SECURITY_GROUP_USER_REL.USER_ID = ?";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from SECURITY_GROUP where    SECURITY_GROUP_ID = ? ";
/*  887 */   private static String[][] SQL_DELETE_CHILDREN = { { "SECURITY_GROUP_USER_REL", "delete from SECURITY_GROUP_USER_REL where     SECURITY_GROUP_USER_REL.SECURITY_GROUP_ID = ? " } };
/*      */ 
/*  896 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  900 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and SECURITY_GROUP.SECURITY_GROUP_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from SECURITY_GROUP where 1=1 and SECURITY_GROUP.MODEL_ID = ? order by  SECURITY_GROUP.SECURITY_GROUP_ID";
/*      */   protected static final String SQL_GET_ALL = " from SECURITY_GROUP where    MODEL_ID = ? ";
/*      */   protected SecurityGroupUserRelDAO mSecurityGroupUserRelDAO;
/*      */   protected SecurityGroupEVO mDetails;
/*      */ 
/*      */   public SecurityGroupDAO(Connection connection)
/*      */   {
/*   42 */     super(connection);
/*      */   }
/*      */ 
/*      */   public SecurityGroupDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public SecurityGroupDAO(DataSource ds)
/*      */   {
/*   58 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected SecurityGroupPK getPK()
/*      */   {
/*   66 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(SecurityGroupEVO details)
/*      */   {
/*   75 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private SecurityGroupEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   95 */     int col = 1;
/*   96 */     SecurityGroupEVO evo = new SecurityGroupEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  106 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  107 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  108 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  109 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(SecurityGroupEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  114 */     int col = startCol_;
/*  115 */     stmt_.setInt(col++, evo_.getSecurityGroupId());
/*  116 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(SecurityGroupEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  121 */     int col = startCol_;
/*  122 */     stmt_.setInt(col++, evo_.getModelId());
/*  123 */     stmt_.setString(col++, evo_.getVisId());
/*  124 */     stmt_.setString(col++, evo_.getDescription());
/*  125 */     stmt_.setInt(col++, evo_.getUpdateAccessId());
/*  126 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  127 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  128 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  129 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  130 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(SecurityGroupPK pk)
/*      */     throws ValidationException
/*      */   {
/*  146 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  148 */     PreparedStatement stmt = null;
/*  149 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  153 */       stmt = getConnection().prepareStatement("select SECURITY_GROUP.SECURITY_GROUP_ID,SECURITY_GROUP.MODEL_ID,SECURITY_GROUP.VIS_ID,SECURITY_GROUP.DESCRIPTION,SECURITY_GROUP.UPDATE_ACCESS_ID,SECURITY_GROUP.VERSION_NUM,SECURITY_GROUP.UPDATED_BY_USER_ID,SECURITY_GROUP.UPDATED_TIME,SECURITY_GROUP.CREATED_TIME from SECURITY_GROUP where    SECURITY_GROUP_ID = ? ");
/*      */ 
/*  156 */       int col = 1;
/*  157 */       stmt.setInt(col++, pk.getSecurityGroupId());
/*      */ 
/*  159 */       resultSet = stmt.executeQuery();
/*      */ 
/*  161 */       if (!resultSet.next()) {
/*  162 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  165 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  166 */       if (this.mDetails.isModified())
/*  167 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  171 */       throw handleSQLException(pk, "select SECURITY_GROUP.SECURITY_GROUP_ID,SECURITY_GROUP.MODEL_ID,SECURITY_GROUP.VIS_ID,SECURITY_GROUP.DESCRIPTION,SECURITY_GROUP.UPDATE_ACCESS_ID,SECURITY_GROUP.VERSION_NUM,SECURITY_GROUP.UPDATED_BY_USER_ID,SECURITY_GROUP.UPDATED_TIME,SECURITY_GROUP.CREATED_TIME from SECURITY_GROUP where    SECURITY_GROUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  175 */       closeResultSet(resultSet);
/*  176 */       closeStatement(stmt);
/*  177 */       closeConnection();
/*      */ 
/*  179 */       if (timer != null)
/*  180 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  215 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  216 */     this.mDetails.postCreateInit();
/*      */ 
/*  218 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  223 */       duplicateValueCheckSecurityGroupName();
/*      */ 
/*  225 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  226 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  227 */       stmt = getConnection().prepareStatement("insert into SECURITY_GROUP ( SECURITY_GROUP_ID,MODEL_ID,VIS_ID,DESCRIPTION,UPDATE_ACCESS_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  230 */       int col = 1;
/*  231 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  232 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  235 */       int resultCount = stmt.executeUpdate();
/*  236 */       if (resultCount != 1)
/*      */       {
/*  238 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  241 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  245 */       throw handleSQLException(this.mDetails.getPK(), "insert into SECURITY_GROUP ( SECURITY_GROUP_ID,MODEL_ID,VIS_ID,DESCRIPTION,UPDATE_ACCESS_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  249 */       closeStatement(stmt);
/*  250 */       closeConnection();
/*      */ 
/*  252 */       if (timer != null) {
/*  253 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  259 */       getSecurityGroupUserRelDAO().update(this.mDetails.getUsersInGroupMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  265 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckSecurityGroupName()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  281 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  282 */     PreparedStatement stmt = null;
/*  283 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  287 */       stmt = getConnection().prepareStatement("select count(*) from SECURITY_GROUP where    MODEL_ID = ? AND VIS_ID = ? and not(    SECURITY_GROUP_ID = ? )");
/*      */ 
/*  290 */       int col = 1;
/*  291 */       stmt.setInt(col++, this.mDetails.getModelId());
/*  292 */       stmt.setString(col++, this.mDetails.getVisId());
/*  293 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  296 */       resultSet = stmt.executeQuery();
/*      */ 
/*  298 */       if (!resultSet.next()) {
/*  299 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  303 */       col = 1;
/*  304 */       int count = resultSet.getInt(col++);
/*  305 */       if (count > 0) {
/*  306 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " SecurityGroupName");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  312 */       throw handleSQLException(getPK(), "select count(*) from SECURITY_GROUP where    MODEL_ID = ? AND VIS_ID = ? and not(    SECURITY_GROUP_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  316 */       closeResultSet(resultSet);
/*  317 */       closeStatement(stmt);
/*  318 */       closeConnection();
/*      */ 
/*  320 */       if (timer != null)
/*  321 */         timer.logDebug("duplicateValueCheckSecurityGroupName", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  348 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  352 */     PreparedStatement stmt = null;
/*      */ 
/*  354 */     boolean mainChanged = this.mDetails.isModified();
/*  355 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  359 */       if (mainChanged) {
/*  360 */         duplicateValueCheckSecurityGroupName();
/*      */       }
/*  362 */       if (getSecurityGroupUserRelDAO().update(this.mDetails.getUsersInGroupMap())) {
/*  363 */         dependantChanged = true;
/*      */       }
/*  365 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  368 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  371 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  372 */         stmt = getConnection().prepareStatement("update SECURITY_GROUP set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,UPDATE_ACCESS_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_GROUP_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  375 */         int col = 1;
/*  376 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  377 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  379 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  382 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  384 */         if (resultCount == 0) {
/*  385 */           checkVersionNum();
/*      */         }
/*  387 */         if (resultCount != 1) {
/*  388 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  391 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  400 */       throw handleSQLException(getPK(), "update SECURITY_GROUP set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,UPDATE_ACCESS_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_GROUP_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  404 */       closeStatement(stmt);
/*  405 */       closeConnection();
/*      */ 
/*  407 */       if ((timer != null) && (
/*  408 */         (mainChanged) || (dependantChanged)))
/*  409 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  421 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  422 */     PreparedStatement stmt = null;
/*  423 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  427 */       stmt = getConnection().prepareStatement("select VERSION_NUM from SECURITY_GROUP where SECURITY_GROUP_ID = ?");
/*      */ 
/*  430 */       int col = 1;
/*  431 */       stmt.setInt(col++, this.mDetails.getSecurityGroupId());
/*      */ 
/*  434 */       resultSet = stmt.executeQuery();
/*      */ 
/*  436 */       if (!resultSet.next()) {
/*  437 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  440 */       col = 1;
/*  441 */       int dbVersionNumber = resultSet.getInt(col++);
/*  442 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  443 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  449 */       throw handleSQLException(getPK(), "select VERSION_NUM from SECURITY_GROUP where SECURITY_GROUP_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  453 */       closeStatement(stmt);
/*  454 */       closeResultSet(resultSet);
/*      */ 
/*  456 */       if (timer != null)
/*  457 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllSecurityGroupsELO getAllSecurityGroups()
/*      */   {
/*  493 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  494 */     PreparedStatement stmt = null;
/*  495 */     ResultSet resultSet = null;
/*  496 */     AllSecurityGroupsELO results = new AllSecurityGroupsELO();
/*      */     try
/*      */     {
/*  499 */       stmt = getConnection().prepareStatement(SQL_ALL_SECURITY_GROUPS);
/*  500 */       int col = 1;
/*  501 */       resultSet = stmt.executeQuery();
/*  502 */       while (resultSet.next())
/*      */       {
/*  504 */         col = 2;
/*      */ 
/*  507 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  510 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  513 */         SecurityGroupPK pkSecurityGroup = new SecurityGroupPK(resultSet.getInt(col++));
/*      */ 
/*  516 */         String textSecurityGroup = resultSet.getString(col++);
/*      */ 
/*  521 */         SecurityGroupCK ckSecurityGroup = new SecurityGroupCK(pkModel, pkSecurityGroup);
/*      */ 
/*  527 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  533 */         SecurityGroupRefImpl erSecurityGroup = new SecurityGroupRefImpl(ckSecurityGroup, textSecurityGroup);
/*      */ 
/*  538 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  541 */         results.add(erSecurityGroup, erModel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  550 */       throw handleSQLException(SQL_ALL_SECURITY_GROUPS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  554 */       closeResultSet(resultSet);
/*  555 */       closeStatement(stmt);
/*  556 */       closeConnection();
/*      */     }
/*      */ 
/*  559 */     if (timer != null) {
/*  560 */       timer.logDebug("getAllSecurityGroups", " items=" + results.size());
/*      */     }
/*      */ 
/*  564 */     return results;
/*      */   }
/*      */ 
/*      */   public AllSecurityGroupsUsingAccessDefELO getAllSecurityGroupsUsingAccessDef(int param1)
/*      */   {
/*  601 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  602 */     PreparedStatement stmt = null;
/*  603 */     ResultSet resultSet = null;
/*  604 */     AllSecurityGroupsUsingAccessDefELO results = new AllSecurityGroupsUsingAccessDefELO();
/*      */     try
/*      */     {
/*  607 */       stmt = getConnection().prepareStatement(SQL_ALL_SECURITY_GROUPS_USING_ACCESS_DEF);
/*  608 */       int col = 1;
/*  609 */       stmt.setInt(col++, param1);
/*  610 */       resultSet = stmt.executeQuery();
/*  611 */       while (resultSet.next())
/*      */       {
/*  613 */         col = 2;
/*      */ 
/*  616 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  619 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  622 */         SecurityGroupPK pkSecurityGroup = new SecurityGroupPK(resultSet.getInt(col++));
/*      */ 
/*  625 */         String textSecurityGroup = resultSet.getString(col++);
/*      */ 
/*  630 */         SecurityGroupCK ckSecurityGroup = new SecurityGroupCK(pkModel, pkSecurityGroup);
/*      */ 
/*  636 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  642 */         SecurityGroupRefImpl erSecurityGroup = new SecurityGroupRefImpl(ckSecurityGroup, textSecurityGroup);
/*      */ 
/*  647 */         int col1 = resultSet.getInt(col++);
/*      */ 
/*  650 */         results.add(erSecurityGroup, erModel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  659 */       throw handleSQLException(SQL_ALL_SECURITY_GROUPS_USING_ACCESS_DEF, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  663 */       closeResultSet(resultSet);
/*  664 */       closeStatement(stmt);
/*  665 */       closeConnection();
/*      */     }
/*      */ 
/*  668 */     if (timer != null) {
/*  669 */       timer.logDebug("getAllSecurityGroupsUsingAccessDef", " UpdateAccessId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  674 */     return results;
/*      */   }
/*      */ 
/*      */   public AllSecurityGroupsForUserELO getAllSecurityGroupsForUser(int param1)
/*      */   {
/*  707 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  708 */     PreparedStatement stmt = null;
/*  709 */     ResultSet resultSet = null;
/*  710 */     AllSecurityGroupsForUserELO results = new AllSecurityGroupsForUserELO();
/*      */     try
/*      */     {
/*  713 */       stmt = getConnection().prepareStatement(SQL_ALL_SECURITY_GROUPS_FOR_USER);
/*  714 */       int col = 1;
/*  715 */       stmt.setInt(col++, param1);
/*  716 */       resultSet = stmt.executeQuery();
/*  717 */       while (resultSet.next())
/*      */       {
/*  719 */         col = 2;
/*      */ 
/*  722 */         results.add(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  730 */       throw handleSQLException(SQL_ALL_SECURITY_GROUPS_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  734 */       closeResultSet(resultSet);
/*  735 */       closeStatement(stmt);
/*  736 */       closeConnection();
/*      */     }
/*      */ 
/*  739 */     if (timer != null) {
/*  740 */       timer.logDebug("getAllSecurityGroupsForUser", " UserId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  745 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  762 */     if (items == null) {
/*  763 */       return false;
/*      */     }
/*  765 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  766 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  768 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  772 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  773 */       while (iter3.hasNext())
/*      */       {
/*  775 */         this.mDetails = ((SecurityGroupEVO)iter3.next());
/*  776 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  778 */         somethingChanged = true;
/*      */ 
/*  781 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  785 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  786 */       while (iter2.hasNext())
/*      */       {
/*  788 */         this.mDetails = ((SecurityGroupEVO)iter2.next());
/*      */ 
/*  791 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  793 */         somethingChanged = true;
/*      */ 
/*  796 */         if (deleteStmt == null) {
/*  797 */           deleteStmt = getConnection().prepareStatement("delete from SECURITY_GROUP where    SECURITY_GROUP_ID = ? ");
/*      */         }
/*      */ 
/*  800 */         int col = 1;
/*  801 */         deleteStmt.setInt(col++, this.mDetails.getSecurityGroupId());
/*      */ 
/*  803 */         if (this._log.isDebugEnabled()) {
/*  804 */           this._log.debug("update", "SecurityGroup deleting SecurityGroupId=" + this.mDetails.getSecurityGroupId());
/*      */         }
/*      */ 
/*  809 */         deleteStmt.addBatch();
/*      */ 
/*  812 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  817 */       if (deleteStmt != null)
/*      */       {
/*  819 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  821 */         deleteStmt.executeBatch();
/*      */ 
/*  823 */         if (timer2 != null) {
/*  824 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  828 */       Iterator iter1 = items.values().iterator();
/*  829 */       while (iter1.hasNext())
/*      */       {
/*  831 */         this.mDetails = ((SecurityGroupEVO)iter1.next());
/*      */ 
/*  833 */         if (this.mDetails.insertPending())
/*      */         {
/*  835 */           somethingChanged = true;
/*  836 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  839 */         if (this.mDetails.isModified())
/*      */         {
/*  841 */           somethingChanged = true;
/*  842 */           doStore(); continue;
/*      */         }
/*      */ 
/*  846 */         if ((this.mDetails.deletePending()) || 
/*  852 */           (!getSecurityGroupUserRelDAO().update(this.mDetails.getUsersInGroupMap()))) continue;
/*  853 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  865 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  869 */       throw handleSQLException("delete from SECURITY_GROUP where    SECURITY_GROUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  873 */       if (deleteStmt != null)
/*      */       {
/*  875 */         closeStatement(deleteStmt);
/*  876 */         closeConnection();
/*      */       }
/*      */ 
/*  879 */       this.mDetails = null;
/*      */ 
/*  881 */       if ((somethingChanged) && 
/*  882 */         (timer != null))
/*  883 */         timer.logDebug("update", "collection"); 
/*  883 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(SecurityGroupPK pk)
/*      */   {
/*  909 */     Set emptyStrings = Collections.emptySet();
/*  910 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(SecurityGroupPK pk, Set<String> exclusionTables)
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
/*  937 */         stmt.setInt(col++, pk.getSecurityGroupId());
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
/*  977 */         stmt.setInt(col++, pk.getSecurityGroupId());
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
/*      */   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants)
/*      */   {
/* 1012 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1014 */     PreparedStatement stmt = null;
/* 1015 */     ResultSet resultSet = null;
/*      */ 
/* 1017 */     int itemCount = 0;
/*      */ 
/* 1019 */     Collection theseItems = new ArrayList();
/* 1020 */     owningEVO.setSecurityGroups(theseItems);
/* 1021 */     owningEVO.setSecurityGroupsAllItemsLoaded(true);
/*      */     try
/*      */     {
/* 1025 */       stmt = getConnection().prepareStatement("select SECURITY_GROUP.SECURITY_GROUP_ID,SECURITY_GROUP.MODEL_ID,SECURITY_GROUP.VIS_ID,SECURITY_GROUP.DESCRIPTION,SECURITY_GROUP.UPDATE_ACCESS_ID,SECURITY_GROUP.VERSION_NUM,SECURITY_GROUP.UPDATED_BY_USER_ID,SECURITY_GROUP.UPDATED_TIME,SECURITY_GROUP.CREATED_TIME from SECURITY_GROUP where 1=1 and SECURITY_GROUP.MODEL_ID = ? order by  SECURITY_GROUP.SECURITY_GROUP_ID");
/*      */ 
/* 1027 */       int col = 1;
/* 1028 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/* 1030 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1033 */       while (resultSet.next())
/*      */       {
/* 1035 */         itemCount++;
/* 1036 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1038 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/* 1041 */       if (timer != null) {
/* 1042 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/* 1045 */       if ((itemCount > 0) && (dependants.indexOf("<17>") > -1))
/*      */       {
/* 1047 */         getSecurityGroupUserRelDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/* 1051 */       throw handleSQLException("select SECURITY_GROUP.SECURITY_GROUP_ID,SECURITY_GROUP.MODEL_ID,SECURITY_GROUP.VIS_ID,SECURITY_GROUP.DESCRIPTION,SECURITY_GROUP.UPDATE_ACCESS_ID,SECURITY_GROUP.VERSION_NUM,SECURITY_GROUP.UPDATED_BY_USER_ID,SECURITY_GROUP.UPDATED_TIME,SECURITY_GROUP.CREATED_TIME from SECURITY_GROUP where 1=1 and SECURITY_GROUP.MODEL_ID = ? order by  SECURITY_GROUP.SECURITY_GROUP_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1055 */       closeResultSet(resultSet);
/* 1056 */       closeStatement(stmt);
/* 1057 */       closeConnection();
/*      */ 
/* 1059 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectModelId, String dependants, Collection currentList)
/*      */   {
/* 1084 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1085 */     PreparedStatement stmt = null;
/* 1086 */     ResultSet resultSet = null;
/*      */ 
/* 1088 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/* 1092 */       stmt = getConnection().prepareStatement("select SECURITY_GROUP.SECURITY_GROUP_ID,SECURITY_GROUP.MODEL_ID,SECURITY_GROUP.VIS_ID,SECURITY_GROUP.DESCRIPTION,SECURITY_GROUP.UPDATE_ACCESS_ID,SECURITY_GROUP.VERSION_NUM,SECURITY_GROUP.UPDATED_BY_USER_ID,SECURITY_GROUP.UPDATED_TIME,SECURITY_GROUP.CREATED_TIME from SECURITY_GROUP where    MODEL_ID = ? ");
/*      */ 
/* 1094 */       int col = 1;
/* 1095 */       stmt.setInt(col++, selectModelId);
/*      */ 
/* 1097 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1099 */       while (resultSet.next())
/*      */       {
/* 1101 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1104 */         getDependants(this.mDetails, dependants);
/*      */ 
/* 1107 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1110 */       if (currentList != null)
/*      */       {
/* 1113 */         ListIterator iter = items.listIterator();
/* 1114 */         SecurityGroupEVO currentEVO = null;
/* 1115 */         SecurityGroupEVO newEVO = null;
/* 1116 */         while (iter.hasNext())
/*      */         {
/* 1118 */           newEVO = (SecurityGroupEVO)iter.next();
/* 1119 */           Iterator iter2 = currentList.iterator();
/* 1120 */           while (iter2.hasNext())
/*      */           {
/* 1122 */             currentEVO = (SecurityGroupEVO)iter2.next();
/* 1123 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1125 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1131 */         Iterator iter2 = currentList.iterator();
/* 1132 */         while (iter2.hasNext())
/*      */         {
/* 1134 */           currentEVO = (SecurityGroupEVO)iter2.next();
/* 1135 */           if (currentEVO.insertPending()) {
/* 1136 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1140 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1144 */       throw handleSQLException("select SECURITY_GROUP.SECURITY_GROUP_ID,SECURITY_GROUP.MODEL_ID,SECURITY_GROUP.VIS_ID,SECURITY_GROUP.DESCRIPTION,SECURITY_GROUP.UPDATE_ACCESS_ID,SECURITY_GROUP.VERSION_NUM,SECURITY_GROUP.UPDATED_BY_USER_ID,SECURITY_GROUP.UPDATED_TIME,SECURITY_GROUP.CREATED_TIME from SECURITY_GROUP where    MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1148 */       closeResultSet(resultSet);
/* 1149 */       closeStatement(stmt);
/* 1150 */       closeConnection();
/*      */ 
/* 1152 */       if (timer != null) {
/* 1153 */         timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1158 */     return items;
/*      */   }
/*      */ 
/*      */   public SecurityGroupEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1175 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1178 */     if (this.mDetails == null) {
/* 1179 */       doLoad(((SecurityGroupCK)paramCK).getSecurityGroupPK());
/*      */     }
/* 1181 */     else if (!this.mDetails.getPK().equals(((SecurityGroupCK)paramCK).getSecurityGroupPK())) {
/* 1182 */       doLoad(((SecurityGroupCK)paramCK).getSecurityGroupPK());
/*      */     }
/*      */ 
/* 1185 */     if ((dependants.indexOf("<17>") > -1) && (!this.mDetails.isUsersInGroupAllItemsLoaded()))
/*      */     {
/* 1190 */       this.mDetails.setUsersInGroup(getSecurityGroupUserRelDAO().getAll(this.mDetails.getSecurityGroupId(), dependants, this.mDetails.getUsersInGroup()));
/*      */ 
/* 1197 */       this.mDetails.setUsersInGroupAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1200 */     if ((paramCK instanceof SecurityGroupUserRelCK))
/*      */     {
/* 1202 */       if (this.mDetails.getUsersInGroup() == null) {
/* 1203 */         this.mDetails.loadUsersInGroupItem(getSecurityGroupUserRelDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1206 */         SecurityGroupUserRelPK pk = ((SecurityGroupUserRelCK)paramCK).getSecurityGroupUserRelPK();
/* 1207 */         SecurityGroupUserRelEVO evo = this.mDetails.getUsersInGroupItem(pk);
/* 1208 */         if (evo == null) {
/* 1209 */           this.mDetails.loadUsersInGroupItem(getSecurityGroupUserRelDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1214 */     SecurityGroupEVO details = new SecurityGroupEVO();
/* 1215 */     details = this.mDetails.deepClone();
/*      */ 
/* 1217 */     if (timer != null) {
/* 1218 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1220 */     return details;
/*      */   }
/*      */ 
/*      */   public SecurityGroupEVO getDetails(ModelCK paramCK, SecurityGroupEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1226 */     SecurityGroupEVO savedEVO = this.mDetails;
/* 1227 */     this.mDetails = paramEVO;
/* 1228 */     SecurityGroupEVO newEVO = getDetails(paramCK, dependants);
/* 1229 */     this.mDetails = savedEVO;
/* 1230 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public SecurityGroupEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1236 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1240 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1243 */     SecurityGroupEVO details = this.mDetails.deepClone();
/*      */ 
/* 1245 */     if (timer != null) {
/* 1246 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1248 */     return details;
/*      */   }
/*      */ 
/*      */   protected SecurityGroupUserRelDAO getSecurityGroupUserRelDAO()
/*      */   {
/* 1257 */     if (this.mSecurityGroupUserRelDAO == null)
/*      */     {
/* 1259 */       if (this.mDataSource != null)
/* 1260 */         this.mSecurityGroupUserRelDAO = new SecurityGroupUserRelDAO(this.mDataSource);
/*      */       else {
/* 1262 */         this.mSecurityGroupUserRelDAO = new SecurityGroupUserRelDAO(getConnection());
/*      */       }
/*      */     }
/* 1265 */     return this.mSecurityGroupUserRelDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1270 */     return "SecurityGroup";
/*      */   }
/*      */ 
/*      */   public SecurityGroupRefImpl getRef(SecurityGroupPK paramSecurityGroupPK)
/*      */   {
/* 1275 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1276 */     PreparedStatement stmt = null;
/* 1277 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1280 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,SECURITY_GROUP.VIS_ID from SECURITY_GROUP,MODEL where 1=1 and SECURITY_GROUP.SECURITY_GROUP_ID = ? and SECURITY_GROUP.MODEL_ID = MODEL.MODEL_ID");
/* 1281 */       int col = 1;
/* 1282 */       stmt.setInt(col++, paramSecurityGroupPK.getSecurityGroupId());
/*      */ 
/* 1284 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1286 */       if (!resultSet.next()) {
/* 1287 */         throw new RuntimeException(getEntityName() + " getRef " + paramSecurityGroupPK + " not found");
/*      */       }
/* 1289 */       col = 2;
/* 1290 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1294 */       String textSecurityGroup = resultSet.getString(col++);
/* 1295 */       SecurityGroupCK ckSecurityGroup = new SecurityGroupCK(newModelPK, paramSecurityGroupPK);
/*      */ 
/* 1300 */       SecurityGroupRefImpl localSecurityGroupRefImpl = new SecurityGroupRefImpl(ckSecurityGroup, textSecurityGroup);
/*      */       return localSecurityGroupRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1305 */       throw handleSQLException(paramSecurityGroupPK, "select 0,MODEL.MODEL_ID,SECURITY_GROUP.VIS_ID from SECURITY_GROUP,MODEL where 1=1 and SECURITY_GROUP.SECURITY_GROUP_ID = ? and SECURITY_GROUP.MODEL_ID = MODEL.MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1309 */       closeResultSet(resultSet);
/* 1310 */       closeStatement(stmt);
/* 1311 */       closeConnection();
/*      */ 
/* 1313 */       if (timer != null)
/* 1314 */         timer.logDebug("getRef", paramSecurityGroupPK); 
/* 1314 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1326 */     if (c == null)
/* 1327 */       return;
/* 1328 */     Iterator iter = c.iterator();
/* 1329 */     while (iter.hasNext())
/*      */     {
/* 1331 */       SecurityGroupEVO evo = (SecurityGroupEVO)iter.next();
/* 1332 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(SecurityGroupEVO evo, String dependants)
/*      */   {
/* 1346 */     if (evo.insertPending()) {
/* 1347 */       return;
/*      */     }
/* 1349 */     if (evo.getSecurityGroupId() < 1) {
/* 1350 */       return;
/*      */     }
/*      */ 
/* 1354 */     if (dependants.indexOf("<17>") > -1)
/*      */     {
/* 1357 */       if (!evo.isUsersInGroupAllItemsLoaded())
/*      */       {
/* 1359 */         evo.setUsersInGroup(getSecurityGroupUserRelDAO().getAll(evo.getSecurityGroupId(), dependants, evo.getUsersInGroup()));
/*      */ 
/* 1366 */         evo.setUsersInGroupAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.SecurityGroupDAO
 * JD-Core Version:    0.6.0
 */