/*      */ package com.cedar.cp.ejb.impl.report.pack;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.report.pack.ReportPackRef;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
/*      */ import com.cedar.cp.dto.report.definition.ReportDefinitionRefImpl;
/*      */ import com.cedar.cp.dto.report.distribution.DistributionPK;
/*      */ import com.cedar.cp.dto.report.distribution.DistributionRefImpl;
/*      */ import com.cedar.cp.dto.report.pack.AllReportPacksELO;
/*      */ import com.cedar.cp.dto.report.pack.ReportDefDistListELO;
/*      */ import com.cedar.cp.dto.report.pack.ReportPackCK;
/*      */ import com.cedar.cp.dto.report.pack.ReportPackLinkCK;
/*      */ import com.cedar.cp.dto.report.pack.ReportPackLinkPK;
/*      */ import com.cedar.cp.dto.report.pack.ReportPackLinkRefImpl;
/*      */ import com.cedar.cp.dto.report.pack.ReportPackPK;
/*      */ import com.cedar.cp.dto.report.pack.ReportPackRefImpl;
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
/*      */ public class ReportPackDAO extends AbstractDAO
/*      */ {
/*   41 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select REPORT_PACK_ID from REPORT_PACK where    REPORT_PACK_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select REPORT_PACK.REPORT_PACK_ID,REPORT_PACK.VIS_ID,REPORT_PACK.DESCRIPTION,REPORT_PACK.GROUP_ATTACHMENT,REPORT_PACK.PARAM_EXAMPLE,REPORT_PACK.VERSION_NUM,REPORT_PACK.UPDATED_BY_USER_ID,REPORT_PACK.UPDATED_TIME,REPORT_PACK.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from REPORT_PACK where    REPORT_PACK_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into REPORT_PACK ( REPORT_PACK_ID,VIS_ID,DESCRIPTION,GROUP_ATTACHMENT,PARAM_EXAMPLE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update REPORT_PACK_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from REPORT_PACK_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_IDENTIFIER = "select count(*) from REPORT_PACK where    VIS_ID = ? and not(    REPORT_PACK_ID = ? )";
/*      */   protected static final String SQL_STORE = "update REPORT_PACK set VIS_ID = ?,DESCRIPTION = ?,GROUP_ATTACHMENT = ?,PARAM_EXAMPLE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_PACK_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from REPORT_PACK where REPORT_PACK_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from REPORT_PACK where    REPORT_PACK_ID = ? ";
/*  722 */   protected static String SQL_ALL_REPORT_PACKS = "select 0       ,REPORT_PACK.REPORT_PACK_ID      ,REPORT_PACK.VIS_ID      ,REPORT_PACK.DESCRIPTION      ,REPORT_PACK.PARAM_EXAMPLE from REPORT_PACK where 1=1 ";
/*      */ 
/*  805 */   protected static String SQL_REPORT_DEF_DIST_LIST = "select 0       ,REPORT_PACK.REPORT_PACK_ID      ,REPORT_PACK.VIS_ID      ,REPORT_PACK_LINK.REPORT_PACK_ID      ,REPORT_PACK_LINK.REPORT_PACK_LINK_ID      ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,DISTRIBUTION.DISTRIBUTION_ID      ,DISTRIBUTION.VIS_ID      ,REPORT_PACK.GROUP_ATTACHMENT from REPORT_PACK    ,REPORT_PACK_LINK    ,REPORT_DEFINITION    ,DISTRIBUTION where 1=1  and  REPORT_PACK.VIS_ID = ? and REPORT_PACK.REPORT_PACK_ID = REPORT_PACK_LINK.REPORT_PACK_ID and REPORT_PACK_LINK.REPORT_DEF_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID and REPORT_PACK_LINK.DISTRIBUTION_ID = DISTRIBUTION.DISTRIBUTION_ID order by REPORT_PACK_LINK.REPORT_PACK_LINK_ID";
/*      */ 
/*  939 */   private static String[][] SQL_DELETE_CHILDREN = { { "REPORT_PACK_LINK", "delete from REPORT_PACK_LINK where     REPORT_PACK_LINK.REPORT_PACK_ID = ? " } };
/*      */ 
/*  948 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  952 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and REPORT_PACK.REPORT_PACK_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from REPORT_PACK where   REPORT_PACK_ID = ?";
/*      */   public static final String SQL_GET_REPORT_PACK_LINK_REF = "select 0,REPORT_PACK.REPORT_PACK_ID from REPORT_PACK_LINK,REPORT_PACK where 1=1 and REPORT_PACK_LINK.REPORT_PACK_ID = ? and REPORT_PACK_LINK.REPORT_PACK_LINK_ID = ? and REPORT_PACK_LINK.REPORT_PACK_ID = REPORT_PACK.REPORT_PACK_ID";
/*      */   protected ReportPackLinkDAO mReportPackLinkDAO;
/*      */   protected ReportPackEVO mDetails;
/*      */ 
/*      */   public ReportPackDAO(Connection connection)
/*      */   {
/*   48 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ReportPackDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ReportPackDAO(DataSource ds)
/*      */   {
/*   64 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ReportPackPK getPK()
/*      */   {
/*   72 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ReportPackEVO details)
/*      */   {
/*   81 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public ReportPackEVO setAndGetDetails(ReportPackEVO details, String dependants)
/*      */   {
/*   92 */     setDetails(details);
/*   93 */     generateKeys();
/*   94 */     getDependants(this.mDetails, dependants);
/*   95 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public ReportPackPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  104 */     doCreate();
/*      */ 
/*  106 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(ReportPackPK pk)
/*      */     throws ValidationException
/*      */   {
/*  116 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  125 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  134 */     doRemove();
/*      */   }
/*      */ 
/*      */   public ReportPackPK findByPrimaryKey(ReportPackPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  143 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  144 */     if (exists(pk_))
/*      */     {
/*  146 */       if (timer != null) {
/*  147 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  149 */       return pk_;
/*      */     }
/*      */ 
/*  152 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(ReportPackPK pk)
/*      */   {
/*  170 */     PreparedStatement stmt = null;
/*  171 */     ResultSet resultSet = null;
/*  172 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  176 */       stmt = getConnection().prepareStatement("select REPORT_PACK_ID from REPORT_PACK where    REPORT_PACK_ID = ? ");
/*      */ 
/*  178 */       int col = 1;
/*  179 */       stmt.setInt(col++, pk.getReportPackId());
/*      */ 
/*  181 */       resultSet = stmt.executeQuery();
/*      */ 
/*  183 */       if (!resultSet.next())
/*  184 */         returnValue = false;
/*      */       else
/*  186 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  190 */       throw handleSQLException(pk, "select REPORT_PACK_ID from REPORT_PACK where    REPORT_PACK_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  194 */       closeResultSet(resultSet);
/*  195 */       closeStatement(stmt);
/*  196 */       closeConnection();
/*      */     }
/*  198 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private ReportPackEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  218 */     int col = 1;
/*  219 */     ReportPackEVO evo = new ReportPackEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  229 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  230 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  231 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  232 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ReportPackEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  237 */     int col = startCol_;
/*  238 */     stmt_.setInt(col++, evo_.getReportPackId());
/*  239 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ReportPackEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  244 */     int col = startCol_;
/*  245 */     stmt_.setString(col++, evo_.getVisId());
/*  246 */     stmt_.setString(col++, evo_.getDescription());
/*  247 */     if (evo_.getGroupAttachment())
/*  248 */       stmt_.setString(col++, "Y");
/*      */     else
/*  250 */       stmt_.setString(col++, " ");
/*  251 */     stmt_.setString(col++, evo_.getParamExample());
/*  252 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  253 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  254 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  255 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  256 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ReportPackPK pk)
/*      */     throws ValidationException
/*      */   {
/*  272 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  274 */     PreparedStatement stmt = null;
/*  275 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  279 */       stmt = getConnection().prepareStatement("select REPORT_PACK.REPORT_PACK_ID,REPORT_PACK.VIS_ID,REPORT_PACK.DESCRIPTION,REPORT_PACK.GROUP_ATTACHMENT,REPORT_PACK.PARAM_EXAMPLE,REPORT_PACK.VERSION_NUM,REPORT_PACK.UPDATED_BY_USER_ID,REPORT_PACK.UPDATED_TIME,REPORT_PACK.CREATED_TIME from REPORT_PACK where    REPORT_PACK_ID = ? ");
/*      */ 
/*  282 */       int col = 1;
/*  283 */       stmt.setInt(col++, pk.getReportPackId());
/*      */ 
/*  285 */       resultSet = stmt.executeQuery();
/*      */ 
/*  287 */       if (!resultSet.next()) {
/*  288 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  291 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  292 */       if (this.mDetails.isModified())
/*  293 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  297 */       throw handleSQLException(pk, "select REPORT_PACK.REPORT_PACK_ID,REPORT_PACK.VIS_ID,REPORT_PACK.DESCRIPTION,REPORT_PACK.GROUP_ATTACHMENT,REPORT_PACK.PARAM_EXAMPLE,REPORT_PACK.VERSION_NUM,REPORT_PACK.UPDATED_BY_USER_ID,REPORT_PACK.UPDATED_TIME,REPORT_PACK.CREATED_TIME from REPORT_PACK where    REPORT_PACK_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  301 */       closeResultSet(resultSet);
/*  302 */       closeStatement(stmt);
/*  303 */       closeConnection();
/*      */ 
/*  305 */       if (timer != null)
/*  306 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  341 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  342 */     generateKeys();
/*      */ 
/*  344 */     this.mDetails.postCreateInit();
/*      */ 
/*  346 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  351 */       duplicateValueCheckIdentifier();
/*      */ 
/*  353 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  354 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  355 */       stmt = getConnection().prepareStatement("insert into REPORT_PACK ( REPORT_PACK_ID,VIS_ID,DESCRIPTION,GROUP_ATTACHMENT,PARAM_EXAMPLE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  358 */       int col = 1;
/*  359 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  360 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  363 */       int resultCount = stmt.executeUpdate();
/*  364 */       if (resultCount != 1)
/*      */       {
/*  366 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  369 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  373 */       throw handleSQLException(this.mDetails.getPK(), "insert into REPORT_PACK ( REPORT_PACK_ID,VIS_ID,DESCRIPTION,GROUP_ATTACHMENT,PARAM_EXAMPLE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  377 */       closeStatement(stmt);
/*  378 */       closeConnection();
/*      */ 
/*  380 */       if (timer != null) {
/*  381 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  387 */       getReportPackLinkDAO().update(this.mDetails.getReportPackDefinitionDistributionListMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  393 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  413 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  415 */     PreparedStatement stmt = null;
/*  416 */     ResultSet resultSet = null;
/*  417 */     String sqlString = null;
/*      */     try
/*      */     {
/*  422 */       sqlString = "update REPORT_PACK_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  423 */       stmt = getConnection().prepareStatement("update REPORT_PACK_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  424 */       stmt.setInt(1, insertCount);
/*      */ 
/*  426 */       int resultCount = stmt.executeUpdate();
/*  427 */       if (resultCount != 1) {
/*  428 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  430 */       closeStatement(stmt);
/*      */ 
/*  433 */       sqlString = "select SEQ_NUM from REPORT_PACK_SEQ";
/*  434 */       stmt = getConnection().prepareStatement("select SEQ_NUM from REPORT_PACK_SEQ");
/*  435 */       resultSet = stmt.executeQuery();
/*  436 */       if (!resultSet.next())
/*  437 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  438 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  440 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  444 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  448 */       closeResultSet(resultSet);
/*  449 */       closeStatement(stmt);
/*  450 */       closeConnection();
/*      */ 
/*  452 */       if (timer != null)
/*  453 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  453 */     }
/*      */   }
/*      */ 
/*      */   public ReportPackPK generateKeys()
/*      */   {
/*  463 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  465 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  468 */     if (insertCount == 0) {
/*  469 */       return this.mDetails.getPK();
/*      */     }
/*  471 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  473 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckIdentifier()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  486 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  487 */     PreparedStatement stmt = null;
/*  488 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  492 */       stmt = getConnection().prepareStatement("select count(*) from REPORT_PACK where    VIS_ID = ? and not(    REPORT_PACK_ID = ? )");
/*      */ 
/*  495 */       int col = 1;
/*  496 */       stmt.setString(col++, this.mDetails.getVisId());
/*  497 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  500 */       resultSet = stmt.executeQuery();
/*      */ 
/*  502 */       if (!resultSet.next()) {
/*  503 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  507 */       col = 1;
/*  508 */       int count = resultSet.getInt(col++);
/*  509 */       if (count > 0) {
/*  510 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " Identifier");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  516 */       throw handleSQLException(getPK(), "select count(*) from REPORT_PACK where    VIS_ID = ? and not(    REPORT_PACK_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  520 */       closeResultSet(resultSet);
/*  521 */       closeStatement(stmt);
/*  522 */       closeConnection();
/*      */ 
/*  524 */       if (timer != null)
/*  525 */         timer.logDebug("duplicateValueCheckIdentifier", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  552 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  554 */     generateKeys();
/*      */ 
/*  559 */     PreparedStatement stmt = null;
/*      */ 
/*  561 */     boolean mainChanged = this.mDetails.isModified();
/*  562 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  566 */       if (mainChanged) {
/*  567 */         duplicateValueCheckIdentifier();
/*      */       }
/*  569 */       if (getReportPackLinkDAO().update(this.mDetails.getReportPackDefinitionDistributionListMap())) {
/*  570 */         dependantChanged = true;
/*      */       }
/*  572 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  575 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  578 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  579 */         stmt = getConnection().prepareStatement("update REPORT_PACK set VIS_ID = ?,DESCRIPTION = ?,GROUP_ATTACHMENT = ?,PARAM_EXAMPLE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_PACK_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  582 */         int col = 1;
/*  583 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  584 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  586 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  589 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  591 */         if (resultCount == 0) {
/*  592 */           checkVersionNum();
/*      */         }
/*  594 */         if (resultCount != 1) {
/*  595 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  598 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  607 */       throw handleSQLException(getPK(), "update REPORT_PACK set VIS_ID = ?,DESCRIPTION = ?,GROUP_ATTACHMENT = ?,PARAM_EXAMPLE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_PACK_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  611 */       closeStatement(stmt);
/*  612 */       closeConnection();
/*      */ 
/*  614 */       if ((timer != null) && (
/*  615 */         (mainChanged) || (dependantChanged)))
/*  616 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  628 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  629 */     PreparedStatement stmt = null;
/*  630 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  634 */       stmt = getConnection().prepareStatement("select VERSION_NUM from REPORT_PACK where REPORT_PACK_ID = ?");
/*      */ 
/*  637 */       int col = 1;
/*  638 */       stmt.setInt(col++, this.mDetails.getReportPackId());
/*      */ 
/*  641 */       resultSet = stmt.executeQuery();
/*      */ 
/*  643 */       if (!resultSet.next()) {
/*  644 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  647 */       col = 1;
/*  648 */       int dbVersionNumber = resultSet.getInt(col++);
/*  649 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  650 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  656 */       throw handleSQLException(getPK(), "select VERSION_NUM from REPORT_PACK where REPORT_PACK_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  660 */       closeStatement(stmt);
/*  661 */       closeResultSet(resultSet);
/*      */ 
/*  663 */       if (timer != null)
/*  664 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  681 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  682 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  687 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  692 */       stmt = getConnection().prepareStatement("delete from REPORT_PACK where    REPORT_PACK_ID = ? ");
/*      */ 
/*  695 */       int col = 1;
/*  696 */       stmt.setInt(col++, this.mDetails.getReportPackId());
/*      */ 
/*  698 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  700 */       if (resultCount != 1) {
/*  701 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  707 */       throw handleSQLException(getPK(), "delete from REPORT_PACK where    REPORT_PACK_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  711 */       closeStatement(stmt);
/*  712 */       closeConnection();
/*      */ 
/*  714 */       if (timer != null)
/*  715 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllReportPacksELO getAllReportPacks()
/*      */   {
/*  746 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  747 */     PreparedStatement stmt = null;
/*  748 */     ResultSet resultSet = null;
/*  749 */     AllReportPacksELO results = new AllReportPacksELO();
/*      */     try
/*      */     {
/*  752 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_PACKS);
/*  753 */       int col = 1;
/*  754 */       resultSet = stmt.executeQuery();
/*  755 */       while (resultSet.next())
/*      */       {
/*  757 */         col = 2;
/*      */ 
/*  760 */         ReportPackPK pkReportPack = new ReportPackPK(resultSet.getInt(col++));
/*      */ 
/*  763 */         String textReportPack = resultSet.getString(col++);
/*      */ 
/*  767 */         ReportPackRefImpl erReportPack = new ReportPackRefImpl(pkReportPack, textReportPack);
/*      */ 
/*  772 */         String col1 = resultSet.getString(col++);
/*  773 */         String col2 = resultSet.getString(col++);
/*      */ 
/*  776 */         results.add(erReportPack, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  785 */       throw handleSQLException(SQL_ALL_REPORT_PACKS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  789 */       closeResultSet(resultSet);
/*  790 */       closeStatement(stmt);
/*  791 */       closeConnection();
/*      */     }
/*      */ 
/*  794 */     if (timer != null) {
/*  795 */       timer.logDebug("getAllReportPacks", " items=" + results.size());
/*      */     }
/*      */ 
/*  799 */     return results;
/*      */   }
/*      */ 
/*      */   public ReportDefDistListELO getReportDefDistList(String param1)
/*      */   {
/*  842 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  843 */     PreparedStatement stmt = null;
/*  844 */     ResultSet resultSet = null;
/*  845 */     ReportDefDistListELO results = new ReportDefDistListELO();
/*      */     try
/*      */     {
/*  848 */       stmt = getConnection().prepareStatement(SQL_REPORT_DEF_DIST_LIST);
/*  849 */       int col = 1;
/*  850 */       stmt.setString(col++, param1);
/*  851 */       resultSet = stmt.executeQuery();
/*  852 */       while (resultSet.next())
/*      */       {
/*  854 */         col = 2;
/*      */ 
/*  857 */         ReportPackPK pkReportPack = new ReportPackPK(resultSet.getInt(col++));
/*      */ 
/*  860 */         String textReportPack = resultSet.getString(col++);
/*      */ 
/*  863 */         ReportPackLinkPK pkReportPackLink = new ReportPackLinkPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  867 */         String textReportPackLink = "";
/*      */ 
/*  869 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*      */ 
/*  872 */         String textReportDefinition = resultSet.getString(col++);
/*      */ 
/*  874 */         DistributionPK pkDistribution = new DistributionPK(resultSet.getInt(col++));
/*      */ 
/*  877 */         String textDistribution = resultSet.getString(col++);
/*      */ 
/*  880 */         ReportPackRefImpl erReportPack = new ReportPackRefImpl(pkReportPack, textReportPack);
/*      */ 
/*  886 */         ReportPackLinkRefImpl erReportPackLink = new ReportPackLinkRefImpl(pkReportPackLink, textReportPackLink);
/*      */ 
/*  892 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*      */ 
/*  898 */         DistributionRefImpl erDistribution = new DistributionRefImpl(pkDistribution, textDistribution);
/*      */ 
/*  903 */         String col1 = resultSet.getString(col++);
/*  904 */         if (resultSet.wasNull()) {
/*  905 */           col1 = "";
/*      */         }
/*      */ 
/*  908 */         results.add(erReportPack, erReportPackLink, erReportDefinition, erDistribution, col1.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  919 */       throw handleSQLException(SQL_REPORT_DEF_DIST_LIST, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  923 */       closeResultSet(resultSet);
/*  924 */       closeStatement(stmt);
/*  925 */       closeConnection();
/*      */     }
/*      */ 
/*  928 */     if (timer != null) {
/*  929 */       timer.logDebug("getReportDefDistList", " VisId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  934 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ReportPackPK pk)
/*      */   {
/*  961 */     Set emptyStrings = Collections.emptySet();
/*  962 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ReportPackPK pk, Set<String> exclusionTables)
/*      */   {
/*  968 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  970 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  972 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  974 */       PreparedStatement stmt = null;
/*      */ 
/*  976 */       int resultCount = 0;
/*  977 */       String s = null;
/*      */       try
/*      */       {
/*  980 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  982 */         if (this._log.isDebugEnabled()) {
/*  983 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  985 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  988 */         int col = 1;
/*  989 */         stmt.setInt(col++, pk.getReportPackId());
/*      */ 
/*  992 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  996 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1000 */         closeStatement(stmt);
/* 1001 */         closeConnection();
/*      */ 
/* 1003 */         if (timer != null) {
/* 1004 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1008 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1010 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1012 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1014 */       PreparedStatement stmt = null;
/*      */ 
/* 1016 */       int resultCount = 0;
/* 1017 */       String s = null;
/*      */       try
/*      */       {
/* 1020 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1022 */         if (this._log.isDebugEnabled()) {
/* 1023 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1025 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1028 */         int col = 1;
/* 1029 */         stmt.setInt(col++, pk.getReportPackId());
/*      */ 
/* 1032 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1036 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1040 */         closeStatement(stmt);
/* 1041 */         closeConnection();
/*      */ 
/* 1043 */         if (timer != null)
/* 1044 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public ReportPackEVO getDetails(ReportPackPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1063 */     return getDetails(new ReportPackCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public ReportPackEVO getDetails(ReportPackCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1080 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1083 */     if (this.mDetails == null) {
/* 1084 */       doLoad(paramCK.getReportPackPK());
/*      */     }
/* 1086 */     else if (!this.mDetails.getPK().equals(paramCK.getReportPackPK())) {
/* 1087 */       doLoad(paramCK.getReportPackPK());
/*      */     }
/* 1089 */     else if (!checkIfValid())
/*      */     {
/* 1091 */       this._log.info("getDetails", "[ALERT] ReportPackEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1093 */       doLoad(paramCK.getReportPackPK());
/*      */     }
/*      */ 
/* 1103 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isReportPackDefinitionDistributionListAllItemsLoaded()))
/*      */     {
/* 1108 */       this.mDetails.setReportPackDefinitionDistributionList(getReportPackLinkDAO().getAll(this.mDetails.getReportPackId(), dependants, this.mDetails.getReportPackDefinitionDistributionList()));
/*      */ 
/* 1115 */       this.mDetails.setReportPackDefinitionDistributionListAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1118 */     if ((paramCK instanceof ReportPackLinkCK))
/*      */     {
/* 1120 */       if (this.mDetails.getReportPackDefinitionDistributionList() == null) {
/* 1121 */         this.mDetails.loadReportPackDefinitionDistributionListItem(getReportPackLinkDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1124 */         ReportPackLinkPK pk = ((ReportPackLinkCK)paramCK).getReportPackLinkPK();
/* 1125 */         ReportPackLinkEVO evo = this.mDetails.getReportPackDefinitionDistributionListItem(pk);
/* 1126 */         if (evo == null) {
/* 1127 */           this.mDetails.loadReportPackDefinitionDistributionListItem(getReportPackLinkDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1132 */     ReportPackEVO details = new ReportPackEVO();
/* 1133 */     details = this.mDetails.deepClone();
/*      */ 
/* 1135 */     if (timer != null) {
/* 1136 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1138 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1148 */     boolean stillValid = false;
/* 1149 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1150 */     PreparedStatement stmt = null;
/* 1151 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1154 */       stmt = getConnection().prepareStatement("select VERSION_NUM from REPORT_PACK where   REPORT_PACK_ID = ?");
/* 1155 */       int col = 1;
/* 1156 */       stmt.setInt(col++, this.mDetails.getReportPackId());
/*      */ 
/* 1158 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1160 */       if (!resultSet.next()) {
/* 1161 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1163 */       col = 1;
/* 1164 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1166 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1167 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1171 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from REPORT_PACK where   REPORT_PACK_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1175 */       closeResultSet(resultSet);
/* 1176 */       closeStatement(stmt);
/* 1177 */       closeConnection();
/*      */ 
/* 1179 */       if (timer != null) {
/* 1180 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1183 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public ReportPackEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1189 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1191 */     if (!checkIfValid())
/*      */     {
/* 1193 */       this._log.info("getDetails", "ReportPack " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1194 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1198 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1201 */     ReportPackEVO details = this.mDetails.deepClone();
/*      */ 
/* 1203 */     if (timer != null) {
/* 1204 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1206 */     return details;
/*      */   }
/*      */ 
/*      */   protected ReportPackLinkDAO getReportPackLinkDAO()
/*      */   {
/* 1215 */     if (this.mReportPackLinkDAO == null)
/*      */     {
/* 1217 */       if (this.mDataSource != null)
/* 1218 */         this.mReportPackLinkDAO = new ReportPackLinkDAO(this.mDataSource);
/*      */       else {
/* 1220 */         this.mReportPackLinkDAO = new ReportPackLinkDAO(getConnection());
/*      */       }
/*      */     }
/* 1223 */     return this.mReportPackLinkDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1228 */     return "ReportPack";
/*      */   }
/*      */ 
/*      */   public ReportPackRef getRef(ReportPackPK paramReportPackPK)
/*      */     throws ValidationException
/*      */   {
/* 1234 */     ReportPackEVO evo = getDetails(paramReportPackPK, "");
/* 1235 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1260 */     if (c == null)
/* 1261 */       return;
/* 1262 */     Iterator iter = c.iterator();
/* 1263 */     while (iter.hasNext())
/*      */     {
/* 1265 */       ReportPackEVO evo = (ReportPackEVO)iter.next();
/* 1266 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(ReportPackEVO evo, String dependants)
/*      */   {
/* 1280 */     if (evo.getReportPackId() < 1) {
/* 1281 */       return;
/*      */     }
/*      */ 
/* 1289 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1292 */       if (!evo.isReportPackDefinitionDistributionListAllItemsLoaded())
/*      */       {
/* 1294 */         evo.setReportPackDefinitionDistributionList(getReportPackLinkDAO().getAll(evo.getReportPackId(), dependants, evo.getReportPackDefinitionDistributionList()));
/*      */ 
/* 1301 */         evo.setReportPackDefinitionDistributionListAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.pack.ReportPackDAO
 * JD-Core Version:    0.6.0
 */