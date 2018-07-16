/*      */ package com.cedar.cp.ejb.impl.report.distribution;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.report.distribution.DistributionRef;
/*      */ import com.cedar.cp.dto.report.distribution.AllDistributionsELO;
/*      */ import com.cedar.cp.dto.report.distribution.DistributionCK;
/*      */ import com.cedar.cp.dto.report.distribution.DistributionDetailsForVisIdELO;
/*      */ import com.cedar.cp.dto.report.distribution.DistributionForVisIdELO;
/*      */ import com.cedar.cp.dto.report.distribution.DistributionLinkCK;
/*      */ import com.cedar.cp.dto.report.distribution.DistributionLinkPK;
/*      */ import com.cedar.cp.dto.report.distribution.DistributionLinkRefImpl;
/*      */ import com.cedar.cp.dto.report.distribution.DistributionPK;
/*      */ import com.cedar.cp.dto.report.distribution.DistributionRefImpl;
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
/*      */ public class DistributionDAO extends AbstractDAO
/*      */ {
/*   35 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select DISTRIBUTION_ID from DISTRIBUTION where    DISTRIBUTION_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select DISTRIBUTION.DISTRIBUTION_ID,DISTRIBUTION.VIS_ID,DISTRIBUTION.DESCRIPTION,DISTRIBUTION.RA_DISTRIBUTION,DISTRIBUTION.DIR_ROOT,DISTRIBUTION.VERSION_NUM,DISTRIBUTION.UPDATED_BY_USER_ID,DISTRIBUTION.UPDATED_TIME,DISTRIBUTION.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from DISTRIBUTION where    DISTRIBUTION_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into DISTRIBUTION ( DISTRIBUTION_ID,VIS_ID,DESCRIPTION,RA_DISTRIBUTION,DIR_ROOT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update DISTRIBUTION_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from DISTRIBUTION_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_IDENTIFIER = "select count(*) from DISTRIBUTION where    VIS_ID = ? and not(    DISTRIBUTION_ID = ? )";
/*      */   protected static final String SQL_STORE = "update DISTRIBUTION set VIS_ID = ?,DESCRIPTION = ?,RA_DISTRIBUTION = ?,DIR_ROOT = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DISTRIBUTION_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from DISTRIBUTION where DISTRIBUTION_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from DISTRIBUTION where    DISTRIBUTION_ID = ? ";
/*  716 */   protected static String SQL_ALL_DISTRIBUTIONS = "select 0       ,DISTRIBUTION.DISTRIBUTION_ID      ,DISTRIBUTION.VIS_ID      ,DISTRIBUTION.DESCRIPTION      ,DISTRIBUTION.RA_DISTRIBUTION from DISTRIBUTION where 1=1 ";
/*      */ 
/*  801 */   protected static String SQL_DISTRIBUTION_FOR_VIS_ID = "select 0       ,DISTRIBUTION.DISTRIBUTION_ID      ,DISTRIBUTION.VIS_ID      ,DISTRIBUTION.DESCRIPTION from DISTRIBUTION where 1=1  and  DISTRIBUTION.VIS_ID = ?";
/*      */ 
/*  885 */   protected static String SQL_DISTRIBUTION_DETAILS_FOR_VIS_ID = "select 0       ,DISTRIBUTION.DISTRIBUTION_ID      ,DISTRIBUTION.VIS_ID      ,DISTRIBUTION_LINK.DISTRIBUTION_ID      ,DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID      ,DISTRIBUTION.DESCRIPTION      ,DISTRIBUTION.RA_DISTRIBUTION      ,DISTRIBUTION.DIR_ROOT      ,DISTRIBUTION_LINK.DESTINATION_ID      ,DISTRIBUTION_LINK.DESTINATION_TYPE from DISTRIBUTION    ,DISTRIBUTION_LINK where 1=1  and  DISTRIBUTION.VIS_ID = ? and DISTRIBUTION_LINK.DISTRIBUTION_ID (+) = DISTRIBUTION.DISTRIBUTION_ID";
/*      */ 
/*  999 */   private static String[][] SQL_DELETE_CHILDREN = { { "DISTRIBUTION_LINK", "delete from DISTRIBUTION_LINK where     DISTRIBUTION_LINK.DISTRIBUTION_ID = ? " } };
/*      */ 
/* 1008 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/* 1012 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and DISTRIBUTION.DISTRIBUTION_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from DISTRIBUTION where   DISTRIBUTION_ID = ?";
/*      */   public static final String SQL_GET_DISTRIBUTION_LINK_REF = "select 0,DISTRIBUTION.DISTRIBUTION_ID from DISTRIBUTION_LINK,DISTRIBUTION where 1=1 and DISTRIBUTION_LINK.DISTRIBUTION_ID = ? and DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID = ? and DISTRIBUTION_LINK.DISTRIBUTION_ID = DISTRIBUTION.DISTRIBUTION_ID";
/*      */   protected DistributionLinkDAO mDistributionLinkDAO;
/*      */   protected DistributionEVO mDetails;
/*      */ 
/*      */   public DistributionDAO(Connection connection)
/*      */   {
/*   42 */     super(connection);
/*      */   }
/*      */ 
/*      */   public DistributionDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public DistributionDAO(DataSource ds)
/*      */   {
/*   58 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected DistributionPK getPK()
/*      */   {
/*   66 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(DistributionEVO details)
/*      */   {
/*   75 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public DistributionEVO setAndGetDetails(DistributionEVO details, String dependants)
/*      */   {
/*   86 */     setDetails(details);
/*   87 */     generateKeys();
/*   88 */     getDependants(this.mDetails, dependants);
/*   89 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public DistributionPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   98 */     doCreate();
/*      */ 
/*  100 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(DistributionPK pk)
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
/*      */   public DistributionPK findByPrimaryKey(DistributionPK pk_)
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
/*      */   protected boolean exists(DistributionPK pk)
/*      */   {
/*  164 */     PreparedStatement stmt = null;
/*  165 */     ResultSet resultSet = null;
/*  166 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  170 */       stmt = getConnection().prepareStatement("select DISTRIBUTION_ID from DISTRIBUTION where    DISTRIBUTION_ID = ? ");
/*      */ 
/*  172 */       int col = 1;
/*  173 */       stmt.setInt(col++, pk.getDistributionId());
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
/*  184 */       throw handleSQLException(pk, "select DISTRIBUTION_ID from DISTRIBUTION where    DISTRIBUTION_ID = ? ", sqle);
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
/*      */   private DistributionEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  212 */     int col = 1;
/*  213 */     DistributionEVO evo = new DistributionEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  223 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  224 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  225 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  226 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(DistributionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  231 */     int col = startCol_;
/*  232 */     stmt_.setInt(col++, evo_.getDistributionId());
/*  233 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(DistributionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  238 */     int col = startCol_;
/*  239 */     stmt_.setString(col++, evo_.getVisId());
/*  240 */     stmt_.setString(col++, evo_.getDescription());
/*  241 */     if (evo_.getRaDistribution())
/*  242 */       stmt_.setString(col++, "Y");
/*      */     else
/*  244 */       stmt_.setString(col++, " ");
/*  245 */     stmt_.setString(col++, evo_.getDirRoot());
/*  246 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  247 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  248 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  249 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  250 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(DistributionPK pk)
/*      */     throws ValidationException
/*      */   {
/*  266 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  268 */     PreparedStatement stmt = null;
/*  269 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  273 */       stmt = getConnection().prepareStatement("select DISTRIBUTION.DISTRIBUTION_ID,DISTRIBUTION.VIS_ID,DISTRIBUTION.DESCRIPTION,DISTRIBUTION.RA_DISTRIBUTION,DISTRIBUTION.DIR_ROOT,DISTRIBUTION.VERSION_NUM,DISTRIBUTION.UPDATED_BY_USER_ID,DISTRIBUTION.UPDATED_TIME,DISTRIBUTION.CREATED_TIME from DISTRIBUTION where    DISTRIBUTION_ID = ? ");
/*      */ 
/*  276 */       int col = 1;
/*  277 */       stmt.setInt(col++, pk.getDistributionId());
/*      */ 
/*  279 */       resultSet = stmt.executeQuery();
/*      */ 
/*  281 */       if (!resultSet.next()) {
/*  282 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  285 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  286 */       if (this.mDetails.isModified())
/*  287 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  291 */       throw handleSQLException(pk, "select DISTRIBUTION.DISTRIBUTION_ID,DISTRIBUTION.VIS_ID,DISTRIBUTION.DESCRIPTION,DISTRIBUTION.RA_DISTRIBUTION,DISTRIBUTION.DIR_ROOT,DISTRIBUTION.VERSION_NUM,DISTRIBUTION.UPDATED_BY_USER_ID,DISTRIBUTION.UPDATED_TIME,DISTRIBUTION.CREATED_TIME from DISTRIBUTION where    DISTRIBUTION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  295 */       closeResultSet(resultSet);
/*  296 */       closeStatement(stmt);
/*  297 */       closeConnection();
/*      */ 
/*  299 */       if (timer != null)
/*  300 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  335 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  336 */     generateKeys();
/*      */ 
/*  338 */     this.mDetails.postCreateInit();
/*      */ 
/*  340 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  345 */       duplicateValueCheckIdentifier();
/*      */ 
/*  347 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  348 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  349 */       stmt = getConnection().prepareStatement("insert into DISTRIBUTION ( DISTRIBUTION_ID,VIS_ID,DESCRIPTION,RA_DISTRIBUTION,DIR_ROOT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  352 */       int col = 1;
/*  353 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  354 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  357 */       int resultCount = stmt.executeUpdate();
/*  358 */       if (resultCount != 1)
/*      */       {
/*  360 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  363 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  367 */       throw handleSQLException(this.mDetails.getPK(), "insert into DISTRIBUTION ( DISTRIBUTION_ID,VIS_ID,DESCRIPTION,RA_DISTRIBUTION,DIR_ROOT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  371 */       closeStatement(stmt);
/*  372 */       closeConnection();
/*      */ 
/*  374 */       if (timer != null) {
/*  375 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  381 */       getDistributionLinkDAO().update(this.mDetails.getDistributionDestinationListMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  387 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  407 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  409 */     PreparedStatement stmt = null;
/*  410 */     ResultSet resultSet = null;
/*  411 */     String sqlString = null;
/*      */     try
/*      */     {
/*  416 */       sqlString = "update DISTRIBUTION_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  417 */       stmt = getConnection().prepareStatement("update DISTRIBUTION_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  418 */       stmt.setInt(1, insertCount);
/*      */ 
/*  420 */       int resultCount = stmt.executeUpdate();
/*  421 */       if (resultCount != 1) {
/*  422 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  424 */       closeStatement(stmt);
/*      */ 
/*  427 */       sqlString = "select SEQ_NUM from DISTRIBUTION_SEQ";
/*  428 */       stmt = getConnection().prepareStatement("select SEQ_NUM from DISTRIBUTION_SEQ");
/*  429 */       resultSet = stmt.executeQuery();
/*  430 */       if (!resultSet.next())
/*  431 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  432 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  434 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  438 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  442 */       closeResultSet(resultSet);
/*  443 */       closeStatement(stmt);
/*  444 */       closeConnection();
/*      */ 
/*  446 */       if (timer != null)
/*  447 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  447 */     }
/*      */   }
/*      */ 
/*      */   public DistributionPK generateKeys()
/*      */   {
/*  457 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  459 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  462 */     if (insertCount == 0) {
/*  463 */       return this.mDetails.getPK();
/*      */     }
/*  465 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  467 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckIdentifier()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  480 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  481 */     PreparedStatement stmt = null;
/*  482 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  486 */       stmt = getConnection().prepareStatement("select count(*) from DISTRIBUTION where    VIS_ID = ? and not(    DISTRIBUTION_ID = ? )");
/*      */ 
/*  489 */       int col = 1;
/*  490 */       stmt.setString(col++, this.mDetails.getVisId());
/*  491 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  494 */       resultSet = stmt.executeQuery();
/*      */ 
/*  496 */       if (!resultSet.next()) {
/*  497 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  501 */       col = 1;
/*  502 */       int count = resultSet.getInt(col++);
/*  503 */       if (count > 0) {
/*  504 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " Identifier");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  510 */       throw handleSQLException(getPK(), "select count(*) from DISTRIBUTION where    VIS_ID = ? and not(    DISTRIBUTION_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  514 */       closeResultSet(resultSet);
/*  515 */       closeStatement(stmt);
/*  516 */       closeConnection();
/*      */ 
/*  518 */       if (timer != null)
/*  519 */         timer.logDebug("duplicateValueCheckIdentifier", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  546 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  548 */     generateKeys();
/*      */ 
/*  553 */     PreparedStatement stmt = null;
/*      */ 
/*  555 */     boolean mainChanged = this.mDetails.isModified();
/*  556 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  560 */       if (mainChanged) {
/*  561 */         duplicateValueCheckIdentifier();
/*      */       }
/*  563 */       if (getDistributionLinkDAO().update(this.mDetails.getDistributionDestinationListMap())) {
/*  564 */         dependantChanged = true;
/*      */       }
/*  566 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  569 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  572 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  573 */         stmt = getConnection().prepareStatement("update DISTRIBUTION set VIS_ID = ?,DESCRIPTION = ?,RA_DISTRIBUTION = ?,DIR_ROOT = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DISTRIBUTION_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  576 */         int col = 1;
/*  577 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  578 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  580 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  583 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  585 */         if (resultCount == 0) {
/*  586 */           checkVersionNum();
/*      */         }
/*  588 */         if (resultCount != 1) {
/*  589 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  592 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  601 */       throw handleSQLException(getPK(), "update DISTRIBUTION set VIS_ID = ?,DESCRIPTION = ?,RA_DISTRIBUTION = ?,DIR_ROOT = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DISTRIBUTION_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  605 */       closeStatement(stmt);
/*  606 */       closeConnection();
/*      */ 
/*  608 */       if ((timer != null) && (
/*  609 */         (mainChanged) || (dependantChanged)))
/*  610 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  622 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  623 */     PreparedStatement stmt = null;
/*  624 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  628 */       stmt = getConnection().prepareStatement("select VERSION_NUM from DISTRIBUTION where DISTRIBUTION_ID = ?");
/*      */ 
/*  631 */       int col = 1;
/*  632 */       stmt.setInt(col++, this.mDetails.getDistributionId());
/*      */ 
/*  635 */       resultSet = stmt.executeQuery();
/*      */ 
/*  637 */       if (!resultSet.next()) {
/*  638 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  641 */       col = 1;
/*  642 */       int dbVersionNumber = resultSet.getInt(col++);
/*  643 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  644 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  650 */       throw handleSQLException(getPK(), "select VERSION_NUM from DISTRIBUTION where DISTRIBUTION_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  654 */       closeStatement(stmt);
/*  655 */       closeResultSet(resultSet);
/*      */ 
/*  657 */       if (timer != null)
/*  658 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  675 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  676 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  681 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  686 */       stmt = getConnection().prepareStatement("delete from DISTRIBUTION where    DISTRIBUTION_ID = ? ");
/*      */ 
/*  689 */       int col = 1;
/*  690 */       stmt.setInt(col++, this.mDetails.getDistributionId());
/*      */ 
/*  692 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  694 */       if (resultCount != 1) {
/*  695 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  701 */       throw handleSQLException(getPK(), "delete from DISTRIBUTION where    DISTRIBUTION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  705 */       closeStatement(stmt);
/*  706 */       closeConnection();
/*      */ 
/*  708 */       if (timer != null)
/*  709 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllDistributionsELO getAllDistributions()
/*      */   {
/*  740 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  741 */     PreparedStatement stmt = null;
/*  742 */     ResultSet resultSet = null;
/*  743 */     AllDistributionsELO results = new AllDistributionsELO();
/*      */     try
/*      */     {
/*  746 */       stmt = getConnection().prepareStatement(SQL_ALL_DISTRIBUTIONS);
/*  747 */       int col = 1;
/*  748 */       resultSet = stmt.executeQuery();
/*  749 */       while (resultSet.next())
/*      */       {
/*  751 */         col = 2;
/*      */ 
/*  754 */         DistributionPK pkDistribution = new DistributionPK(resultSet.getInt(col++));
/*      */ 
/*  757 */         String textDistribution = resultSet.getString(col++);
/*      */ 
/*  761 */         DistributionRefImpl erDistribution = new DistributionRefImpl(pkDistribution, textDistribution);
/*      */ 
/*  766 */         String col1 = resultSet.getString(col++);
/*  767 */         String col2 = resultSet.getString(col++);
/*  768 */         if (resultSet.wasNull()) {
/*  769 */           col2 = "";
/*      */         }
/*      */ 
/*  772 */         results.add(erDistribution, col1, col2.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  781 */       throw handleSQLException(SQL_ALL_DISTRIBUTIONS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  785 */       closeResultSet(resultSet);
/*  786 */       closeStatement(stmt);
/*  787 */       closeConnection();
/*      */     }
/*      */ 
/*  790 */     if (timer != null) {
/*  791 */       timer.logDebug("getAllDistributions", " items=" + results.size());
/*      */     }
/*      */ 
/*  795 */     return results;
/*      */   }
/*      */ 
/*      */   public DistributionForVisIdELO getDistributionForVisId(String param1)
/*      */   {
/*  826 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  827 */     PreparedStatement stmt = null;
/*  828 */     ResultSet resultSet = null;
/*  829 */     DistributionForVisIdELO results = new DistributionForVisIdELO();
/*      */     try
/*      */     {
/*  832 */       stmt = getConnection().prepareStatement(SQL_DISTRIBUTION_FOR_VIS_ID);
/*  833 */       int col = 1;
/*  834 */       stmt.setString(col++, param1);
/*  835 */       resultSet = stmt.executeQuery();
/*  836 */       while (resultSet.next())
/*      */       {
/*  838 */         col = 2;
/*      */ 
/*  841 */         DistributionPK pkDistribution = new DistributionPK(resultSet.getInt(col++));
/*      */ 
/*  844 */         String textDistribution = resultSet.getString(col++);
/*      */ 
/*  848 */         DistributionRefImpl erDistribution = new DistributionRefImpl(pkDistribution, textDistribution);
/*      */ 
/*  853 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  856 */         results.add(erDistribution, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  864 */       throw handleSQLException(SQL_DISTRIBUTION_FOR_VIS_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  868 */       closeResultSet(resultSet);
/*  869 */       closeStatement(stmt);
/*  870 */       closeConnection();
/*      */     }
/*      */ 
/*  873 */     if (timer != null) {
/*  874 */       timer.logDebug("getDistributionForVisId", " VisId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  879 */     return results;
/*      */   }
/*      */ 
/*      */   public DistributionDetailsForVisIdELO getDistributionDetailsForVisId(String param1)
/*      */   {
/*  918 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  919 */     PreparedStatement stmt = null;
/*  920 */     ResultSet resultSet = null;
/*  921 */     DistributionDetailsForVisIdELO results = new DistributionDetailsForVisIdELO();
/*      */     try
/*      */     {
/*  924 */       stmt = getConnection().prepareStatement(SQL_DISTRIBUTION_DETAILS_FOR_VIS_ID);
/*  925 */       int col = 1;
/*  926 */       stmt.setString(col++, param1);
/*  927 */       resultSet = stmt.executeQuery();
/*  928 */       while (resultSet.next())
/*      */       {
/*  930 */         col = 2;
/*      */ 
/*  933 */         DistributionPK pkDistribution = new DistributionPK(resultSet.getInt(col++));
/*      */ 
/*  936 */         String textDistribution = resultSet.getString(col++);
/*      */ 
/*  939 */         DistributionLinkPK pkDistributionLink = new DistributionLinkPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  943 */         String textDistributionLink = "";
/*      */ 
/*  946 */         DistributionRefImpl erDistribution = new DistributionRefImpl(pkDistribution, textDistribution);
/*      */ 
/*  952 */         DistributionLinkRefImpl erDistributionLink = new DistributionLinkRefImpl(pkDistributionLink, textDistributionLink);
/*      */ 
/*  957 */         String col1 = resultSet.getString(col++);
/*  958 */         String col2 = resultSet.getString(col++);
/*  959 */         if (resultSet.wasNull())
/*  960 */           col2 = "";
/*  961 */         String col3 = resultSet.getString(col++);
/*  962 */         int col4 = resultSet.getInt(col++);
/*  963 */         Integer col5 = getWrappedIntegerFromJdbc(resultSet, col++);
/*      */ 
/*  966 */         results.add(erDistribution, erDistributionLink, col1, col2.equals("Y"), col3, col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  979 */       throw handleSQLException(SQL_DISTRIBUTION_DETAILS_FOR_VIS_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  983 */       closeResultSet(resultSet);
/*  984 */       closeStatement(stmt);
/*  985 */       closeConnection();
/*      */     }
/*      */ 
/*  988 */     if (timer != null) {
/*  989 */       timer.logDebug("getDistributionDetailsForVisId", " VisId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  994 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(DistributionPK pk)
/*      */   {
/* 1021 */     Set emptyStrings = Collections.emptySet();
/* 1022 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(DistributionPK pk, Set<String> exclusionTables)
/*      */   {
/* 1028 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1030 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1032 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1034 */       PreparedStatement stmt = null;
/*      */ 
/* 1036 */       int resultCount = 0;
/* 1037 */       String s = null;
/*      */       try
/*      */       {
/* 1040 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 1042 */         if (this._log.isDebugEnabled()) {
/* 1043 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1045 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1048 */         int col = 1;
/* 1049 */         stmt.setInt(col++, pk.getDistributionId());
/*      */ 
/* 1052 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1056 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1060 */         closeStatement(stmt);
/* 1061 */         closeConnection();
/*      */ 
/* 1063 */         if (timer != null) {
/* 1064 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1068 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1070 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1072 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1074 */       PreparedStatement stmt = null;
/*      */ 
/* 1076 */       int resultCount = 0;
/* 1077 */       String s = null;
/*      */       try
/*      */       {
/* 1080 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1082 */         if (this._log.isDebugEnabled()) {
/* 1083 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1085 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1088 */         int col = 1;
/* 1089 */         stmt.setInt(col++, pk.getDistributionId());
/*      */ 
/* 1092 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1096 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1100 */         closeStatement(stmt);
/* 1101 */         closeConnection();
/*      */ 
/* 1103 */         if (timer != null)
/* 1104 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public DistributionEVO getDetails(DistributionPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1123 */     return getDetails(new DistributionCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public DistributionEVO getDetails(DistributionCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1140 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1143 */     if (this.mDetails == null) {
/* 1144 */       doLoad(paramCK.getDistributionPK());
/*      */     }
/* 1146 */     else if (!this.mDetails.getPK().equals(paramCK.getDistributionPK())) {
/* 1147 */       doLoad(paramCK.getDistributionPK());
/*      */     }
/* 1149 */     else if (!checkIfValid())
/*      */     {
/* 1151 */       this._log.info("getDetails", "[ALERT] DistributionEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1153 */       doLoad(paramCK.getDistributionPK());
/*      */     }
/*      */ 
/* 1163 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isDistributionDestinationListAllItemsLoaded()))
/*      */     {
/* 1168 */       this.mDetails.setDistributionDestinationList(getDistributionLinkDAO().getAll(this.mDetails.getDistributionId(), dependants, this.mDetails.getDistributionDestinationList()));
/*      */ 
/* 1175 */       this.mDetails.setDistributionDestinationListAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1178 */     if ((paramCK instanceof DistributionLinkCK))
/*      */     {
/* 1180 */       if (this.mDetails.getDistributionDestinationList() == null) {
/* 1181 */         this.mDetails.loadDistributionDestinationListItem(getDistributionLinkDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1184 */         DistributionLinkPK pk = ((DistributionLinkCK)paramCK).getDistributionLinkPK();
/* 1185 */         DistributionLinkEVO evo = this.mDetails.getDistributionDestinationListItem(pk);
/* 1186 */         if (evo == null) {
/* 1187 */           this.mDetails.loadDistributionDestinationListItem(getDistributionLinkDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1192 */     DistributionEVO details = new DistributionEVO();
/* 1193 */     details = this.mDetails.deepClone();
/*      */ 
/* 1195 */     if (timer != null) {
/* 1196 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1198 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1208 */     boolean stillValid = false;
/* 1209 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1210 */     PreparedStatement stmt = null;
/* 1211 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1214 */       stmt = getConnection().prepareStatement("select VERSION_NUM from DISTRIBUTION where   DISTRIBUTION_ID = ?");
/* 1215 */       int col = 1;
/* 1216 */       stmt.setInt(col++, this.mDetails.getDistributionId());
/*      */ 
/* 1218 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1220 */       if (!resultSet.next()) {
/* 1221 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1223 */       col = 1;
/* 1224 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1226 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1227 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1231 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from DISTRIBUTION where   DISTRIBUTION_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1235 */       closeResultSet(resultSet);
/* 1236 */       closeStatement(stmt);
/* 1237 */       closeConnection();
/*      */ 
/* 1239 */       if (timer != null) {
/* 1240 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1243 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public DistributionEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1249 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1251 */     if (!checkIfValid())
/*      */     {
/* 1253 */       this._log.info("getDetails", "Distribution " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1254 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1258 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1261 */     DistributionEVO details = this.mDetails.deepClone();
/*      */ 
/* 1263 */     if (timer != null) {
/* 1264 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1266 */     return details;
/*      */   }
/*      */ 
/*      */   protected DistributionLinkDAO getDistributionLinkDAO()
/*      */   {
/* 1275 */     if (this.mDistributionLinkDAO == null)
/*      */     {
/* 1277 */       if (this.mDataSource != null)
/* 1278 */         this.mDistributionLinkDAO = new DistributionLinkDAO(this.mDataSource);
/*      */       else {
/* 1280 */         this.mDistributionLinkDAO = new DistributionLinkDAO(getConnection());
/*      */       }
/*      */     }
/* 1283 */     return this.mDistributionLinkDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1288 */     return "Distribution";
/*      */   }
/*      */ 
/*      */   public DistributionRef getRef(DistributionPK paramDistributionPK)
/*      */     throws ValidationException
/*      */   {
/* 1294 */     DistributionEVO evo = getDetails(paramDistributionPK, "");
/* 1295 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1320 */     if (c == null)
/* 1321 */       return;
/* 1322 */     Iterator iter = c.iterator();
/* 1323 */     while (iter.hasNext())
/*      */     {
/* 1325 */       DistributionEVO evo = (DistributionEVO)iter.next();
/* 1326 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(DistributionEVO evo, String dependants)
/*      */   {
/* 1340 */     if (evo.getDistributionId() < 1) {
/* 1341 */       return;
/*      */     }
/*      */ 
/* 1349 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1352 */       if (!evo.isDistributionDestinationListAllItemsLoaded())
/*      */       {
/* 1354 */         evo.setDistributionDestinationList(getDistributionLinkDAO().getAll(evo.getDistributionId(), dependants, evo.getDistributionDestinationList()));
/*      */ 
/* 1361 */         evo.setDistributionDestinationListAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.distribution.DistributionDAO
 * JD-Core Version:    0.6.0
 */