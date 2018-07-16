/*      */ package com.cedar.cp.ejb.impl.report.type;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.report.type.ReportTypeRef;
/*      */ import com.cedar.cp.dto.report.type.AllReportTypesELO;
/*      */ import com.cedar.cp.dto.report.type.ReportTypeCK;
/*      */ import com.cedar.cp.dto.report.type.ReportTypePK;
/*      */ import com.cedar.cp.dto.report.type.ReportTypeRefImpl;
/*      */ import com.cedar.cp.dto.report.type.param.ReportTypeParamCK;
/*      */ import com.cedar.cp.dto.report.type.param.ReportTypeParamPK;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.report.type.param.ReportTypeParamDAO;
/*      */ import com.cedar.cp.ejb.impl.report.type.param.ReportTypeParamEVO;
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
/*      */ public class ReportTypeDAO extends AbstractDAO
/*      */ {
/*   35 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select REPORT_TYPE_ID from REPORT_TYPE where    REPORT_TYPE_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select REPORT_TYPE.REPORT_TYPE_ID,REPORT_TYPE.VIS_ID,REPORT_TYPE.DESCRIPTION,REPORT_TYPE.TYPE,REPORT_TYPE.VERSION_NUM,REPORT_TYPE.UPDATED_BY_USER_ID,REPORT_TYPE.UPDATED_TIME,REPORT_TYPE.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from REPORT_TYPE where    REPORT_TYPE_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into REPORT_TYPE ( REPORT_TYPE_ID,VIS_ID,DESCRIPTION,TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update REPORT_TYPE_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from REPORT_TYPE_SEQ";
/*      */   protected static final String SQL_STORE = "update REPORT_TYPE set VIS_ID = ?,DESCRIPTION = ?,TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_TYPE_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from REPORT_TYPE where REPORT_TYPE_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from REPORT_TYPE where    REPORT_TYPE_ID = ? ";
/*  649 */   protected static String SQL_ALL_REPORT_TYPES = "select 0       ,REPORT_TYPE.REPORT_TYPE_ID      ,REPORT_TYPE.VIS_ID      ,REPORT_TYPE.REPORT_TYPE_ID      ,REPORT_TYPE.DESCRIPTION      ,REPORT_TYPE.TYPE from REPORT_TYPE where 1=1 ";
/*      */ 
/*  734 */   private static String[][] SQL_DELETE_CHILDREN = { { "REPORT_TYPE_PARAM", "delete from REPORT_TYPE_PARAM where     REPORT_TYPE_PARAM.REPORT_TYPE_ID = ? " } };
/*      */ 
/*  743 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  747 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and REPORT_TYPE.REPORT_TYPE_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from REPORT_TYPE where   REPORT_TYPE_ID = ?";
/*      */   public static final String SQL_GET_REPORT_TYPE_PARAM_REF = "select 0,REPORT_TYPE.REPORT_TYPE_ID from REPORT_TYPE_PARAM,REPORT_TYPE where 1=1 and REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID = ? and REPORT_TYPE_PARAM.REPORT_TYPE_ID = ? and REPORT_TYPE_PARAM.REPORT_TYPE_ID = REPORT_TYPE.REPORT_TYPE_ID";
/*      */   protected ReportTypeParamDAO mReportTypeParamDAO;
/*      */   protected ReportTypeEVO mDetails;
/*      */ 
/*      */   public ReportTypeDAO(Connection connection)
/*      */   {
/*   42 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ReportTypeDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ReportTypeDAO(DataSource ds)
/*      */   {
/*   58 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ReportTypePK getPK()
/*      */   {
/*   66 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ReportTypeEVO details)
/*      */   {
/*   75 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public ReportTypeEVO setAndGetDetails(ReportTypeEVO details, String dependants)
/*      */   {
/*   86 */     setDetails(details);
/*   87 */     generateKeys();
/*   88 */     getDependants(this.mDetails, dependants);
/*   89 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public ReportTypePK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   98 */     doCreate();
/*      */ 
/*  100 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(ReportTypePK pk)
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
/*      */   public ReportTypePK findByPrimaryKey(ReportTypePK pk_)
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
/*      */   protected boolean exists(ReportTypePK pk)
/*      */   {
/*  164 */     PreparedStatement stmt = null;
/*  165 */     ResultSet resultSet = null;
/*  166 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  170 */       stmt = getConnection().prepareStatement("select REPORT_TYPE_ID from REPORT_TYPE where    REPORT_TYPE_ID = ? ");
/*      */ 
/*  172 */       int col = 1;
/*  173 */       stmt.setInt(col++, pk.getReportTypeId());
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
/*  184 */       throw handleSQLException(pk, "select REPORT_TYPE_ID from REPORT_TYPE where    REPORT_TYPE_ID = ? ", sqle);
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
/*      */   private ReportTypeEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  211 */     int col = 1;
/*  212 */     ReportTypeEVO evo = new ReportTypeEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  221 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  222 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  223 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  224 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ReportTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  229 */     int col = startCol_;
/*  230 */     stmt_.setInt(col++, evo_.getReportTypeId());
/*  231 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ReportTypeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  236 */     int col = startCol_;
/*  237 */     stmt_.setString(col++, evo_.getVisId());
/*  238 */     stmt_.setString(col++, evo_.getDescription());
/*  239 */     stmt_.setInt(col++, evo_.getType());
/*  240 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  241 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  242 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  243 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  244 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ReportTypePK pk)
/*      */     throws ValidationException
/*      */   {
/*  260 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  262 */     PreparedStatement stmt = null;
/*  263 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  267 */       stmt = getConnection().prepareStatement("select REPORT_TYPE.REPORT_TYPE_ID,REPORT_TYPE.VIS_ID,REPORT_TYPE.DESCRIPTION,REPORT_TYPE.TYPE,REPORT_TYPE.VERSION_NUM,REPORT_TYPE.UPDATED_BY_USER_ID,REPORT_TYPE.UPDATED_TIME,REPORT_TYPE.CREATED_TIME from REPORT_TYPE where    REPORT_TYPE_ID = ? ");
/*      */ 
/*  270 */       int col = 1;
/*  271 */       stmt.setInt(col++, pk.getReportTypeId());
/*      */ 
/*  273 */       resultSet = stmt.executeQuery();
/*      */ 
/*  275 */       if (!resultSet.next()) {
/*  276 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  279 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  280 */       if (this.mDetails.isModified())
/*  281 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  285 */       throw handleSQLException(pk, "select REPORT_TYPE.REPORT_TYPE_ID,REPORT_TYPE.VIS_ID,REPORT_TYPE.DESCRIPTION,REPORT_TYPE.TYPE,REPORT_TYPE.VERSION_NUM,REPORT_TYPE.UPDATED_BY_USER_ID,REPORT_TYPE.UPDATED_TIME,REPORT_TYPE.CREATED_TIME from REPORT_TYPE where    REPORT_TYPE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  289 */       closeResultSet(resultSet);
/*  290 */       closeStatement(stmt);
/*  291 */       closeConnection();
/*      */ 
/*  293 */       if (timer != null)
/*  294 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  327 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  328 */     generateKeys();
/*      */ 
/*  330 */     this.mDetails.postCreateInit();
/*      */ 
/*  332 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  337 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  338 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  339 */       stmt = getConnection().prepareStatement("insert into REPORT_TYPE ( REPORT_TYPE_ID,VIS_ID,DESCRIPTION,TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");
/*      */ 
/*  342 */       int col = 1;
/*  343 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  344 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  347 */       int resultCount = stmt.executeUpdate();
/*  348 */       if (resultCount != 1)
/*      */       {
/*  350 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  353 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  357 */       throw handleSQLException(this.mDetails.getPK(), "insert into REPORT_TYPE ( REPORT_TYPE_ID,VIS_ID,DESCRIPTION,TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  361 */       closeStatement(stmt);
/*  362 */       closeConnection();
/*      */ 
/*  364 */       if (timer != null) {
/*  365 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  371 */       getReportTypeParamDAO().update(this.mDetails.getReportTypeParamsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  377 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  397 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  399 */     PreparedStatement stmt = null;
/*  400 */     ResultSet resultSet = null;
/*  401 */     String sqlString = null;
/*      */     try
/*      */     {
/*  406 */       sqlString = "update REPORT_TYPE_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  407 */       stmt = getConnection().prepareStatement("update REPORT_TYPE_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  408 */       stmt.setInt(1, insertCount);
/*      */ 
/*  410 */       int resultCount = stmt.executeUpdate();
/*  411 */       if (resultCount != 1) {
/*  412 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  414 */       closeStatement(stmt);
/*      */ 
/*  417 */       sqlString = "select SEQ_NUM from REPORT_TYPE_SEQ";
/*  418 */       stmt = getConnection().prepareStatement("select SEQ_NUM from REPORT_TYPE_SEQ");
/*  419 */       resultSet = stmt.executeQuery();
/*  420 */       if (!resultSet.next())
/*  421 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  422 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  424 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  428 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  432 */       closeResultSet(resultSet);
/*  433 */       closeStatement(stmt);
/*  434 */       closeConnection();
/*      */ 
/*  436 */       if (timer != null)
/*  437 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  437 */     }
/*      */   }
/*      */ 
/*      */   public ReportTypePK generateKeys()
/*      */   {
/*  447 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  449 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  452 */     if (insertCount == 0) {
/*  453 */       return this.mDetails.getPK();
/*      */     }
/*  455 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  457 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  482 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  484 */     generateKeys();
/*      */ 
/*  489 */     PreparedStatement stmt = null;
/*      */ 
/*  491 */     boolean mainChanged = this.mDetails.isModified();
/*  492 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  496 */       if (getReportTypeParamDAO().update(this.mDetails.getReportTypeParamsMap())) {
/*  497 */         dependantChanged = true;
/*      */       }
/*  499 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  502 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  505 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  506 */         stmt = getConnection().prepareStatement("update REPORT_TYPE set VIS_ID = ?,DESCRIPTION = ?,TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_TYPE_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  509 */         int col = 1;
/*  510 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  511 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  513 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  516 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  518 */         if (resultCount == 0) {
/*  519 */           checkVersionNum();
/*      */         }
/*  521 */         if (resultCount != 1) {
/*  522 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  525 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  534 */       throw handleSQLException(getPK(), "update REPORT_TYPE set VIS_ID = ?,DESCRIPTION = ?,TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_TYPE_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  538 */       closeStatement(stmt);
/*  539 */       closeConnection();
/*      */ 
/*  541 */       if ((timer != null) && (
/*  542 */         (mainChanged) || (dependantChanged)))
/*  543 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  555 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  556 */     PreparedStatement stmt = null;
/*  557 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  561 */       stmt = getConnection().prepareStatement("select VERSION_NUM from REPORT_TYPE where REPORT_TYPE_ID = ?");
/*      */ 
/*  564 */       int col = 1;
/*  565 */       stmt.setInt(col++, this.mDetails.getReportTypeId());
/*      */ 
/*  568 */       resultSet = stmt.executeQuery();
/*      */ 
/*  570 */       if (!resultSet.next()) {
/*  571 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  574 */       col = 1;
/*  575 */       int dbVersionNumber = resultSet.getInt(col++);
/*  576 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  577 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  583 */       throw handleSQLException(getPK(), "select VERSION_NUM from REPORT_TYPE where REPORT_TYPE_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  587 */       closeStatement(stmt);
/*  588 */       closeResultSet(resultSet);
/*      */ 
/*  590 */       if (timer != null)
/*  591 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  608 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  609 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  614 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  619 */       stmt = getConnection().prepareStatement("delete from REPORT_TYPE where    REPORT_TYPE_ID = ? ");
/*      */ 
/*  622 */       int col = 1;
/*  623 */       stmt.setInt(col++, this.mDetails.getReportTypeId());
/*      */ 
/*  625 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  627 */       if (resultCount != 1) {
/*  628 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  634 */       throw handleSQLException(getPK(), "delete from REPORT_TYPE where    REPORT_TYPE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  638 */       closeStatement(stmt);
/*  639 */       closeConnection();
/*      */ 
/*  641 */       if (timer != null)
/*  642 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllReportTypesELO getAllReportTypes()
/*      */   {
/*  674 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  675 */     PreparedStatement stmt = null;
/*  676 */     ResultSet resultSet = null;
/*  677 */     AllReportTypesELO results = new AllReportTypesELO();
/*      */     try
/*      */     {
/*  680 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_TYPES);
/*  681 */       int col = 1;
/*  682 */       resultSet = stmt.executeQuery();
/*  683 */       while (resultSet.next())
/*      */       {
/*  685 */         col = 2;
/*      */ 
/*  688 */         ReportTypePK pkReportType = new ReportTypePK(resultSet.getInt(col++));
/*      */ 
/*  691 */         String textReportType = resultSet.getString(col++);
/*      */ 
/*  695 */         ReportTypeRefImpl erReportType = new ReportTypeRefImpl(pkReportType, textReportType);
/*      */ 
/*  700 */         int col1 = resultSet.getInt(col++);
/*  701 */         String col2 = resultSet.getString(col++);
/*  702 */         int col3 = resultSet.getInt(col++);
/*      */ 
/*  705 */         results.add(erReportType, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  715 */       throw handleSQLException(SQL_ALL_REPORT_TYPES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  719 */       closeResultSet(resultSet);
/*  720 */       closeStatement(stmt);
/*  721 */       closeConnection();
/*      */     }
/*      */ 
/*  724 */     if (timer != null) {
/*  725 */       timer.logDebug("getAllReportTypes", " items=" + results.size());
/*      */     }
/*      */ 
/*  729 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ReportTypePK pk)
/*      */   {
/*  756 */     Set emptyStrings = Collections.emptySet();
/*  757 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ReportTypePK pk, Set<String> exclusionTables)
/*      */   {
/*  763 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  765 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  767 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  769 */       PreparedStatement stmt = null;
/*      */ 
/*  771 */       int resultCount = 0;
/*  772 */       String s = null;
/*      */       try
/*      */       {
/*  775 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  777 */         if (this._log.isDebugEnabled()) {
/*  778 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  780 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  783 */         int col = 1;
/*  784 */         stmt.setInt(col++, pk.getReportTypeId());
/*      */ 
/*  787 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  791 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  795 */         closeStatement(stmt);
/*  796 */         closeConnection();
/*      */ 
/*  798 */         if (timer != null) {
/*  799 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  803 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  805 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  807 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  809 */       PreparedStatement stmt = null;
/*      */ 
/*  811 */       int resultCount = 0;
/*  812 */       String s = null;
/*      */       try
/*      */       {
/*  815 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  817 */         if (this._log.isDebugEnabled()) {
/*  818 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  820 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  823 */         int col = 1;
/*  824 */         stmt.setInt(col++, pk.getReportTypeId());
/*      */ 
/*  827 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  831 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  835 */         closeStatement(stmt);
/*  836 */         closeConnection();
/*      */ 
/*  838 */         if (timer != null)
/*  839 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public ReportTypeEVO getDetails(ReportTypePK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  858 */     return getDetails(new ReportTypeCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public ReportTypeEVO getDetails(ReportTypeCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  875 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  878 */     if (this.mDetails == null) {
/*  879 */       doLoad(paramCK.getReportTypePK());
/*      */     }
/*  881 */     else if (!this.mDetails.getPK().equals(paramCK.getReportTypePK())) {
/*  882 */       doLoad(paramCK.getReportTypePK());
/*      */     }
/*  884 */     else if (!checkIfValid())
/*      */     {
/*  886 */       this._log.info("getDetails", "[ALERT] ReportTypeEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/*  888 */       doLoad(paramCK.getReportTypePK());
/*      */     }
/*      */ 
/*  898 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isReportTypeParamsAllItemsLoaded()))
/*      */     {
/*  903 */       this.mDetails.setReportTypeParams(getReportTypeParamDAO().getAll(this.mDetails.getReportTypeId(), dependants, this.mDetails.getReportTypeParams()));
/*      */ 
/*  910 */       this.mDetails.setReportTypeParamsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  913 */     if ((paramCK instanceof ReportTypeParamCK))
/*      */     {
/*  915 */       if (this.mDetails.getReportTypeParams() == null) {
/*  916 */         this.mDetails.loadReportTypeParamsItem(getReportTypeParamDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  919 */         ReportTypeParamPK pk = ((ReportTypeParamCK)paramCK).getReportTypeParamPK();
/*  920 */         ReportTypeParamEVO evo = this.mDetails.getReportTypeParamsItem(pk);
/*  921 */         if (evo == null) {
/*  922 */           this.mDetails.loadReportTypeParamsItem(getReportTypeParamDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  927 */     ReportTypeEVO details = new ReportTypeEVO();
/*  928 */     details = this.mDetails.deepClone();
/*      */ 
/*  930 */     if (timer != null) {
/*  931 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  933 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/*  943 */     boolean stillValid = false;
/*  944 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  945 */     PreparedStatement stmt = null;
/*  946 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  949 */       stmt = getConnection().prepareStatement("select VERSION_NUM from REPORT_TYPE where   REPORT_TYPE_ID = ?");
/*  950 */       int col = 1;
/*  951 */       stmt.setInt(col++, this.mDetails.getReportTypeId());
/*      */ 
/*  953 */       resultSet = stmt.executeQuery();
/*      */ 
/*  955 */       if (!resultSet.next()) {
/*  956 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/*  958 */       col = 1;
/*  959 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/*  961 */       if (dbVersionNum == this.mDetails.getVersionNum())
/*  962 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  966 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from REPORT_TYPE where   REPORT_TYPE_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  970 */       closeResultSet(resultSet);
/*  971 */       closeStatement(stmt);
/*  972 */       closeConnection();
/*      */ 
/*  974 */       if (timer != null) {
/*  975 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/*  978 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public ReportTypeEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  984 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  986 */     if (!checkIfValid())
/*      */     {
/*  988 */       this._log.info("getDetails", "ReportType " + this.mDetails.getPK() + " no longer valid - reloading");
/*  989 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/*  993 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  996 */     ReportTypeEVO details = this.mDetails.deepClone();
/*      */ 
/*  998 */     if (timer != null) {
/*  999 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1001 */     return details;
/*      */   }
/*      */ 
/*      */   protected ReportTypeParamDAO getReportTypeParamDAO()
/*      */   {
/* 1010 */     if (this.mReportTypeParamDAO == null)
/*      */     {
/* 1012 */       if (this.mDataSource != null)
/* 1013 */         this.mReportTypeParamDAO = new ReportTypeParamDAO(this.mDataSource);
/*      */       else {
/* 1015 */         this.mReportTypeParamDAO = new ReportTypeParamDAO(getConnection());
/*      */       }
/*      */     }
/* 1018 */     return this.mReportTypeParamDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1023 */     return "ReportType";
/*      */   }
/*      */ 
/*      */   public ReportTypeRef getRef(ReportTypePK paramReportTypePK)
/*      */     throws ValidationException
/*      */   {
/* 1029 */     ReportTypeEVO evo = getDetails(paramReportTypePK, "");
/* 1030 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1055 */     if (c == null)
/* 1056 */       return;
/* 1057 */     Iterator iter = c.iterator();
/* 1058 */     while (iter.hasNext())
/*      */     {
/* 1060 */       ReportTypeEVO evo = (ReportTypeEVO)iter.next();
/* 1061 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(ReportTypeEVO evo, String dependants)
/*      */   {
/* 1075 */     if (evo.getReportTypeId() < 1) {
/* 1076 */       return;
/*      */     }
/*      */ 
/* 1084 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1087 */       if (!evo.isReportTypeParamsAllItemsLoaded())
/*      */       {
/* 1089 */         evo.setReportTypeParams(getReportTypeParamDAO().getAll(evo.getReportTypeId(), dependants, evo.getReportTypeParams()));
/*      */ 
/* 1096 */         evo.setReportTypeParamsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.type.ReportTypeDAO
 * JD-Core Version:    0.6.0
 */