/*      */ package com.cedar.cp.ejb.impl.model.recharge;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.model.recharge.RechargeGroupRef;
/*      */ import com.cedar.cp.dto.model.recharge.AllRechargeGroupsELO;
/*      */ import com.cedar.cp.dto.model.recharge.RechargeGroupCK;
/*      */ import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
/*      */ import com.cedar.cp.dto.model.recharge.RechargeGroupRefImpl;
/*      */ import com.cedar.cp.dto.rechargegroup.RechargeGroupRelCK;
/*      */ import com.cedar.cp.dto.rechargegroup.RechargeGroupRelPK;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.rechargegroup.RechargeGroupRelDAO;
/*      */ import com.cedar.cp.ejb.impl.rechargegroup.RechargeGroupRelEVO;
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
/*      */ public class RechargeGroupDAO extends AbstractDAO
/*      */ {
/*   35 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select RECHARGE_GROUP_ID from RECHARGE_GROUP where    RECHARGE_GROUP_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select RECHARGE_GROUP.RECHARGE_GROUP_ID,RECHARGE_GROUP.VIS_ID,RECHARGE_GROUP.DESCRIPTION,RECHARGE_GROUP.VERSION_NUM,RECHARGE_GROUP.UPDATED_BY_USER_ID,RECHARGE_GROUP.UPDATED_TIME,RECHARGE_GROUP.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from RECHARGE_GROUP where    RECHARGE_GROUP_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into RECHARGE_GROUP ( RECHARGE_GROUP_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update RECHARGE_GROUP_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from RECHARGE_GROUP_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_RECHARGEGROUPNAME = "select count(*) from RECHARGE_GROUP where    VIS_ID = ? and not(    RECHARGE_GROUP_ID = ? )";
/*      */   protected static final String SQL_STORE = "update RECHARGE_GROUP set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RECHARGE_GROUP_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from RECHARGE_GROUP where RECHARGE_GROUP_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from RECHARGE_GROUP where    RECHARGE_GROUP_ID = ? ";
/*  701 */   protected static String SQL_ALL_RECHARGE_GROUPS = "select 0       ,RECHARGE_GROUP.RECHARGE_GROUP_ID      ,RECHARGE_GROUP.VIS_ID      ,RECHARGE_GROUP.RECHARGE_GROUP_ID      ,RECHARGE_GROUP.DESCRIPTION from RECHARGE_GROUP where 1=1 ";
/*      */ 
/*  783 */   private static String[][] SQL_DELETE_CHILDREN = { { "RECHARGE_GROUP_REL", "delete from RECHARGE_GROUP_REL where     RECHARGE_GROUP_REL.RECHARGE_GROUP_ID = ? " } };
/*      */ 
/*  792 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  796 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and RECHARGE_GROUP.RECHARGE_GROUP_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from RECHARGE_GROUP where   RECHARGE_GROUP_ID = ?";
/*      */   public static final String SQL_GET_RECHARGE_GROUP_REL_REF = "select 0,RECHARGE_GROUP.RECHARGE_GROUP_ID from RECHARGE_GROUP_REL,RECHARGE_GROUP where 1=1 and RECHARGE_GROUP_REL.RECHARGE_GROUP_REL_ID = ? and RECHARGE_GROUP_REL.RECHARGE_GROUP_ID = ? and RECHARGE_GROUP_REL.RECHARGE_GROUP_ID = RECHARGE_GROUP.RECHARGE_GROUP_ID";
/*      */   protected RechargeGroupRelDAO mRechargeGroupRelDAO;
/*      */   protected RechargeGroupEVO mDetails;
/*      */ 
/*      */   public RechargeGroupDAO(Connection connection)
/*      */   {
/*   42 */     super(connection);
/*      */   }
/*      */ 
/*      */   public RechargeGroupDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public RechargeGroupDAO(DataSource ds)
/*      */   {
/*   58 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected RechargeGroupPK getPK()
/*      */   {
/*   66 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(RechargeGroupEVO details)
/*      */   {
/*   75 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public RechargeGroupEVO setAndGetDetails(RechargeGroupEVO details, String dependants)
/*      */   {
/*   86 */     setDetails(details);
/*   87 */     generateKeys();
/*   88 */     getDependants(this.mDetails, dependants);
/*   89 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public RechargeGroupPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   98 */     doCreate();
/*      */ 
/*  100 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(RechargeGroupPK pk)
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
/*      */   public RechargeGroupPK findByPrimaryKey(RechargeGroupPK pk_)
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
/*      */   protected boolean exists(RechargeGroupPK pk)
/*      */   {
/*  164 */     PreparedStatement stmt = null;
/*  165 */     ResultSet resultSet = null;
/*  166 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  170 */       stmt = getConnection().prepareStatement("select RECHARGE_GROUP_ID from RECHARGE_GROUP where    RECHARGE_GROUP_ID = ? ");
/*      */ 
/*  172 */       int col = 1;
/*  173 */       stmt.setInt(col++, pk.getRechargeGroupId());
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
/*  184 */       throw handleSQLException(pk, "select RECHARGE_GROUP_ID from RECHARGE_GROUP where    RECHARGE_GROUP_ID = ? ", sqle);
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
/*      */   private RechargeGroupEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  210 */     int col = 1;
/*  211 */     RechargeGroupEVO evo = new RechargeGroupEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  219 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  220 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  221 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  222 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(RechargeGroupEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  227 */     int col = startCol_;
/*  228 */     stmt_.setInt(col++, evo_.getRechargeGroupId());
/*  229 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(RechargeGroupEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
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
/*      */   protected void doLoad(RechargeGroupPK pk)
/*      */     throws ValidationException
/*      */   {
/*  257 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  259 */     PreparedStatement stmt = null;
/*  260 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  264 */       stmt = getConnection().prepareStatement("select RECHARGE_GROUP.RECHARGE_GROUP_ID,RECHARGE_GROUP.VIS_ID,RECHARGE_GROUP.DESCRIPTION,RECHARGE_GROUP.VERSION_NUM,RECHARGE_GROUP.UPDATED_BY_USER_ID,RECHARGE_GROUP.UPDATED_TIME,RECHARGE_GROUP.CREATED_TIME from RECHARGE_GROUP where    RECHARGE_GROUP_ID = ? ");
/*      */ 
/*  267 */       int col = 1;
/*  268 */       stmt.setInt(col++, pk.getRechargeGroupId());
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
/*  282 */       throw handleSQLException(pk, "select RECHARGE_GROUP.RECHARGE_GROUP_ID,RECHARGE_GROUP.VIS_ID,RECHARGE_GROUP.DESCRIPTION,RECHARGE_GROUP.VERSION_NUM,RECHARGE_GROUP.UPDATED_BY_USER_ID,RECHARGE_GROUP.UPDATED_TIME,RECHARGE_GROUP.CREATED_TIME from RECHARGE_GROUP where    RECHARGE_GROUP_ID = ? ", sqle);
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
/*  332 */       duplicateValueCheckRechargeGroupName();
/*      */ 
/*  334 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  335 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  336 */       stmt = getConnection().prepareStatement("insert into RECHARGE_GROUP ( RECHARGE_GROUP_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
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
/*  354 */       throw handleSQLException(this.mDetails.getPK(), "insert into RECHARGE_GROUP ( RECHARGE_GROUP_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
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
/*  368 */       getRechargeGroupRelDAO().update(this.mDetails.getRechargesMap());
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
/*  403 */       sqlString = "update RECHARGE_GROUP_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  404 */       stmt = getConnection().prepareStatement("update RECHARGE_GROUP_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  405 */       stmt.setInt(1, insertCount);
/*      */ 
/*  407 */       int resultCount = stmt.executeUpdate();
/*  408 */       if (resultCount != 1) {
/*  409 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  411 */       closeStatement(stmt);
/*      */ 
/*  414 */       sqlString = "select SEQ_NUM from RECHARGE_GROUP_SEQ";
/*  415 */       stmt = getConnection().prepareStatement("select SEQ_NUM from RECHARGE_GROUP_SEQ");
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
/*      */   public RechargeGroupPK generateKeys()
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
/*      */   protected void duplicateValueCheckRechargeGroupName()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  467 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  468 */     PreparedStatement stmt = null;
/*  469 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  473 */       stmt = getConnection().prepareStatement("select count(*) from RECHARGE_GROUP where    VIS_ID = ? and not(    RECHARGE_GROUP_ID = ? )");
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
/*  491 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " RechargeGroupName");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  497 */       throw handleSQLException(getPK(), "select count(*) from RECHARGE_GROUP where    VIS_ID = ? and not(    RECHARGE_GROUP_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  501 */       closeResultSet(resultSet);
/*  502 */       closeStatement(stmt);
/*  503 */       closeConnection();
/*      */ 
/*  505 */       if (timer != null)
/*  506 */         timer.logDebug("duplicateValueCheckRechargeGroupName", "");
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
/*  546 */         duplicateValueCheckRechargeGroupName();
/*      */       }
/*  548 */       if (getRechargeGroupRelDAO().update(this.mDetails.getRechargesMap())) {
/*  549 */         dependantChanged = true;
/*      */       }
/*  551 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  554 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  557 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  558 */         stmt = getConnection().prepareStatement("update RECHARGE_GROUP set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RECHARGE_GROUP_ID = ? AND VERSION_NUM = ?");
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
/*  586 */       throw handleSQLException(getPK(), "update RECHARGE_GROUP set VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RECHARGE_GROUP_ID = ? AND VERSION_NUM = ?", sqle);
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
/*  613 */       stmt = getConnection().prepareStatement("select VERSION_NUM from RECHARGE_GROUP where RECHARGE_GROUP_ID = ?");
/*      */ 
/*  616 */       int col = 1;
/*  617 */       stmt.setInt(col++, this.mDetails.getRechargeGroupId());
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
/*  635 */       throw handleSQLException(getPK(), "select VERSION_NUM from RECHARGE_GROUP where RECHARGE_GROUP_ID = ?", sqle);
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
/*  671 */       stmt = getConnection().prepareStatement("delete from RECHARGE_GROUP where    RECHARGE_GROUP_ID = ? ");
/*      */ 
/*  674 */       int col = 1;
/*  675 */       stmt.setInt(col++, this.mDetails.getRechargeGroupId());
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
/*  686 */       throw handleSQLException(getPK(), "delete from RECHARGE_GROUP where    RECHARGE_GROUP_ID = ? ", sqle);
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
/*      */   public AllRechargeGroupsELO getAllRechargeGroups()
/*      */   {
/*  725 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  726 */     PreparedStatement stmt = null;
/*  727 */     ResultSet resultSet = null;
/*  728 */     AllRechargeGroupsELO results = new AllRechargeGroupsELO();
/*      */     try
/*      */     {
/*  731 */       stmt = getConnection().prepareStatement(SQL_ALL_RECHARGE_GROUPS);
/*  732 */       int col = 1;
/*  733 */       resultSet = stmt.executeQuery();
/*  734 */       while (resultSet.next())
/*      */       {
/*  736 */         col = 2;
/*      */ 
/*  739 */         RechargeGroupPK pkRechargeGroup = new RechargeGroupPK(resultSet.getInt(col++));
/*      */ 
/*  742 */         String textRechargeGroup = resultSet.getString(col++);
/*      */ 
/*  746 */         RechargeGroupRefImpl erRechargeGroup = new RechargeGroupRefImpl(pkRechargeGroup, textRechargeGroup);
/*      */ 
/*  751 */         int col1 = resultSet.getInt(col++);
/*  752 */         String col2 = resultSet.getString(col++);
/*      */ 
/*  755 */         results.add(erRechargeGroup, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  764 */       throw handleSQLException(SQL_ALL_RECHARGE_GROUPS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  768 */       closeResultSet(resultSet);
/*  769 */       closeStatement(stmt);
/*  770 */       closeConnection();
/*      */     }
/*      */ 
/*  773 */     if (timer != null) {
/*  774 */       timer.logDebug("getAllRechargeGroups", " items=" + results.size());
/*      */     }
/*      */ 
/*  778 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(RechargeGroupPK pk)
/*      */   {
/*  805 */     Set emptyStrings = Collections.emptySet();
/*  806 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(RechargeGroupPK pk, Set<String> exclusionTables)
/*      */   {
/*  812 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  814 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  816 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  818 */       PreparedStatement stmt = null;
/*      */ 
/*  820 */       int resultCount = 0;
/*  821 */       String s = null;
/*      */       try
/*      */       {
/*  824 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  826 */         if (this._log.isDebugEnabled()) {
/*  827 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  829 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  832 */         int col = 1;
/*  833 */         stmt.setInt(col++, pk.getRechargeGroupId());
/*      */ 
/*  836 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  840 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  844 */         closeStatement(stmt);
/*  845 */         closeConnection();
/*      */ 
/*  847 */         if (timer != null) {
/*  848 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  852 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  854 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  856 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  858 */       PreparedStatement stmt = null;
/*      */ 
/*  860 */       int resultCount = 0;
/*  861 */       String s = null;
/*      */       try
/*      */       {
/*  864 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  866 */         if (this._log.isDebugEnabled()) {
/*  867 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  869 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  872 */         int col = 1;
/*  873 */         stmt.setInt(col++, pk.getRechargeGroupId());
/*      */ 
/*  876 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  880 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  884 */         closeStatement(stmt);
/*  885 */         closeConnection();
/*      */ 
/*  887 */         if (timer != null)
/*  888 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public RechargeGroupEVO getDetails(RechargeGroupPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  907 */     return getDetails(new RechargeGroupCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public RechargeGroupEVO getDetails(RechargeGroupCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  924 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  927 */     if (this.mDetails == null) {
/*  928 */       doLoad(paramCK.getRechargeGroupPK());
/*      */     }
/*  930 */     else if (!this.mDetails.getPK().equals(paramCK.getRechargeGroupPK())) {
/*  931 */       doLoad(paramCK.getRechargeGroupPK());
/*      */     }
/*  933 */     else if (!checkIfValid())
/*      */     {
/*  935 */       this._log.info("getDetails", "[ALERT] RechargeGroupEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/*  937 */       doLoad(paramCK.getRechargeGroupPK());
/*      */     }
/*      */ 
/*  947 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isRechargesAllItemsLoaded()))
/*      */     {
/*  952 */       this.mDetails.setRecharges(getRechargeGroupRelDAO().getAll(this.mDetails.getRechargeGroupId(), dependants, this.mDetails.getRecharges()));
/*      */ 
/*  959 */       this.mDetails.setRechargesAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  962 */     if ((paramCK instanceof RechargeGroupRelCK))
/*      */     {
/*  964 */       if (this.mDetails.getRecharges() == null) {
/*  965 */         this.mDetails.loadRechargesItem(getRechargeGroupRelDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  968 */         RechargeGroupRelPK pk = ((RechargeGroupRelCK)paramCK).getRechargeGroupRelPK();
/*  969 */         RechargeGroupRelEVO evo = this.mDetails.getRechargesItem(pk);
/*  970 */         if (evo == null) {
/*  971 */           this.mDetails.loadRechargesItem(getRechargeGroupRelDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  976 */     RechargeGroupEVO details = new RechargeGroupEVO();
/*  977 */     details = this.mDetails.deepClone();
/*      */ 
/*  979 */     if (timer != null) {
/*  980 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  982 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/*  992 */     boolean stillValid = false;
/*  993 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  994 */     PreparedStatement stmt = null;
/*  995 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  998 */       stmt = getConnection().prepareStatement("select VERSION_NUM from RECHARGE_GROUP where   RECHARGE_GROUP_ID = ?");
/*  999 */       int col = 1;
/* 1000 */       stmt.setInt(col++, this.mDetails.getRechargeGroupId());
/*      */ 
/* 1002 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1004 */       if (!resultSet.next()) {
/* 1005 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1007 */       col = 1;
/* 1008 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1010 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1011 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1015 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from RECHARGE_GROUP where   RECHARGE_GROUP_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1019 */       closeResultSet(resultSet);
/* 1020 */       closeStatement(stmt);
/* 1021 */       closeConnection();
/*      */ 
/* 1023 */       if (timer != null) {
/* 1024 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1027 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public RechargeGroupEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1033 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1035 */     if (!checkIfValid())
/*      */     {
/* 1037 */       this._log.info("getDetails", "RechargeGroup " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1038 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1042 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1045 */     RechargeGroupEVO details = this.mDetails.deepClone();
/*      */ 
/* 1047 */     if (timer != null) {
/* 1048 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1050 */     return details;
/*      */   }
/*      */ 
/*      */   protected RechargeGroupRelDAO getRechargeGroupRelDAO()
/*      */   {
/* 1059 */     if (this.mRechargeGroupRelDAO == null)
/*      */     {
/* 1061 */       if (this.mDataSource != null)
/* 1062 */         this.mRechargeGroupRelDAO = new RechargeGroupRelDAO(this.mDataSource);
/*      */       else {
/* 1064 */         this.mRechargeGroupRelDAO = new RechargeGroupRelDAO(getConnection());
/*      */       }
/*      */     }
/* 1067 */     return this.mRechargeGroupRelDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1072 */     return "RechargeGroup";
/*      */   }
/*      */ 
/*      */   public RechargeGroupRef getRef(RechargeGroupPK paramRechargeGroupPK)
/*      */     throws ValidationException
/*      */   {
/* 1078 */     RechargeGroupEVO evo = getDetails(paramRechargeGroupPK, "");
/* 1079 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1104 */     if (c == null)
/* 1105 */       return;
/* 1106 */     Iterator iter = c.iterator();
/* 1107 */     while (iter.hasNext())
/*      */     {
/* 1109 */       RechargeGroupEVO evo = (RechargeGroupEVO)iter.next();
/* 1110 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(RechargeGroupEVO evo, String dependants)
/*      */   {
/* 1124 */     if (evo.getRechargeGroupId() < 1) {
/* 1125 */       return;
/*      */     }
/*      */ 
/* 1133 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1136 */       if (!evo.isRechargesAllItemsLoaded())
/*      */       {
/* 1138 */         evo.setRecharges(getRechargeGroupRelDAO().getAll(evo.getRechargeGroupId(), dependants, evo.getRecharges()));
/*      */ 
/* 1145 */         evo.setRechargesAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.recharge.RechargeGroupDAO
 * JD-Core Version:    0.6.0
 */