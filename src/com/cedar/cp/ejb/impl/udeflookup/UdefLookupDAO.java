/*      */ package com.cedar.cp.ejb.impl.udeflookup;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.udeflookup.UdefColumn;
/*      */ import com.cedar.cp.api.udeflookup.UdefLookupRef;
/*      */ import com.cedar.cp.dto.udeflookup.AllUdefLookupsELO;
/*      */ import com.cedar.cp.dto.udeflookup.UdefLookupCK;
/*      */ import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefCK;
/*      */ import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefPK;
/*      */ import com.cedar.cp.dto.udeflookup.UdefLookupPK;
/*      */ import com.cedar.cp.dto.udeflookup.UdefLookupRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.SqlBuilder;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.BatchUpdateException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class UdefLookupDAO extends AbstractDAO
/*      */ {
/*   33 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select UDEF_LOOKUP_ID from UDEF_LOOKUP where    UDEF_LOOKUP_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select UDEF_LOOKUP.UDEF_LOOKUP_ID,UDEF_LOOKUP.VIS_ID,UDEF_LOOKUP.DESCRIPTION,UDEF_LOOKUP.GEN_TABLE_NAME,UDEF_LOOKUP.AUTO_SUBMIT,UDEF_LOOKUP.SCENARIO,UDEF_LOOKUP.TIME_LVL,UDEF_LOOKUP.YEAR_START_MONTH,UDEF_LOOKUP.TIME_RANGE,UDEF_LOOKUP.LAST_SUBMIT,UDEF_LOOKUP.DATA_UPDATED,UDEF_LOOKUP.UPDATED_BY_USER_ID,UDEF_LOOKUP.UPDATED_TIME,UDEF_LOOKUP.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from UDEF_LOOKUP where    UDEF_LOOKUP_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into UDEF_LOOKUP ( UDEF_LOOKUP_ID,VIS_ID,DESCRIPTION,GEN_TABLE_NAME,AUTO_SUBMIT,SCENARIO,TIME_LVL,YEAR_START_MONTH,TIME_RANGE,LAST_SUBMIT,DATA_UPDATED,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update UDEF_LOOKUP_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from UDEF_LOOKUP_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_NAME = "select count(*) from UDEF_LOOKUP where    VIS_ID = ? and not(    UDEF_LOOKUP_ID = ? )";
/*      */   protected static final String SQL_STORE = "update UDEF_LOOKUP set VIS_ID = ?,DESCRIPTION = ?,GEN_TABLE_NAME = ?,AUTO_SUBMIT = ?,SCENARIO = ?,TIME_LVL = ?,YEAR_START_MONTH = ?,TIME_RANGE = ?,LAST_SUBMIT = ?,DATA_UPDATED = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    UDEF_LOOKUP_ID = ? ";
/*      */   protected static final String SQL_REMOVE = "delete from UDEF_LOOKUP where    UDEF_LOOKUP_ID = ? ";
/*  695 */   protected static String SQL_ALL_UDEF_LOOKUPS = "select 0       ,UDEF_LOOKUP.UDEF_LOOKUP_ID      ,UDEF_LOOKUP.VIS_ID      ,UDEF_LOOKUP.VIS_ID      ,UDEF_LOOKUP.DESCRIPTION      ,UDEF_LOOKUP.AUTO_SUBMIT      ,UDEF_LOOKUP.LAST_SUBMIT      ,UDEF_LOOKUP.DATA_UPDATED from UDEF_LOOKUP where 1=1  order by UDEF_LOOKUP.VIS_ID";
/*      */	 protected static String SQL_ALL_UDEF_LOOKUPS_FOR_USER = "select 0       ,UDEF_LOOKUP.UDEF_LOOKUP_ID      ,UDEF_LOOKUP.VIS_ID      ,UDEF_LOOKUP.VIS_ID      ,UDEF_LOOKUP.DESCRIPTION      ,UDEF_LOOKUP.AUTO_SUBMIT      ,UDEF_LOOKUP.LAST_SUBMIT      ,UDEF_LOOKUP.DATA_UPDATED from UDEF_LOOKUP where 1=1  order by UDEF_LOOKUP.VIS_ID";
/*  788 */   private static String[][] SQL_DELETE_CHILDREN = { { "UDEF_LOOKUP_COLUMN_DEF", "delete from UDEF_LOOKUP_COLUMN_DEF where     UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID = ? " } };
/*      */ 
/*  797 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  801 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and UDEF_LOOKUP.UDEF_LOOKUP_ID = ?)";
/*      */   public static final String SQL_GET_UDEF_LOOKUP_COLUMN_DEF_REF = "select 0,UDEF_LOOKUP.UDEF_LOOKUP_ID from UDEF_LOOKUP_COLUMN_DEF,UDEF_LOOKUP where 1=1 and UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID = ? and UDEF_LOOKUP_COLUMN_DEF.COLUMN_DEF_ID = ? and UDEF_LOOKUP_COLUMN_DEF.UDEF_LOOKUP_ID = UDEF_LOOKUP.UDEF_LOOKUP_ID";
/*      */   protected UdefLookupColumnDefDAO mUdefLookupColumnDefDAO;
/*      */   protected UdefLookupEVO mDetails;
/*      */ 
/*      */   public UdefLookupDAO(Connection connection)
/*      */   {
/*   40 */     super(connection);
/*      */   }
/*      */ 
/*      */   public UdefLookupDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public UdefLookupDAO(DataSource ds)
/*      */   {
/*   56 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected UdefLookupPK getPK()
/*      */   {
/*   64 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(UdefLookupEVO details)
/*      */   {
/*   73 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public UdefLookupEVO setAndGetDetails(UdefLookupEVO details, String dependants)
/*      */   {
/*   84 */     setDetails(details);
/*   85 */     generateKeys();
/*   86 */     getDependants(this.mDetails, dependants);
/*   87 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public UdefLookupPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   96 */     doCreate();
/*      */ 
/*   98 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(UdefLookupPK pk)
/*      */     throws ValidationException
/*      */   {
/*  108 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  117 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  126 */     doRemove();
/*      */   }
/*      */ 
/*      */   public UdefLookupPK findByPrimaryKey(UdefLookupPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  135 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  136 */     if (exists(pk_))
/*      */     {
/*  138 */       if (timer != null) {
/*  139 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  141 */       return pk_;
/*      */     }
/*      */ 
/*  144 */     throw new ValidationException(new StringBuilder().append(pk_).append(" not found").toString());
/*      */   }
/*      */ 
/*      */   protected boolean exists(UdefLookupPK pk)
/*      */   {
/*  162 */     PreparedStatement stmt = null;
/*  163 */     ResultSet resultSet = null;
/*  164 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  168 */       stmt = getConnection().prepareStatement("select UDEF_LOOKUP_ID from UDEF_LOOKUP where    UDEF_LOOKUP_ID = ? ");
/*      */ 
/*  170 */       int col = 1;
/*  171 */       stmt.setInt(col++, pk.getUdefLookupId());
/*      */ 
/*  173 */       resultSet = stmt.executeQuery();
/*      */ 
/*  175 */       if (!resultSet.next())
/*  176 */         returnValue = false;
/*      */       else
/*  178 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  182 */       throw handleSQLException(pk, "select UDEF_LOOKUP_ID from UDEF_LOOKUP where    UDEF_LOOKUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  186 */       closeResultSet(resultSet);
/*  187 */       closeStatement(stmt);
/*  188 */       closeConnection();
/*      */     }
/*  190 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private UdefLookupEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  215 */     int col = 1;
/*  216 */     UdefLookupEVO evo = new UdefLookupEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getTimestamp(col++), resultSet_.getTimestamp(col++), null);
/*      */ 
/*  231 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  232 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  233 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  234 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(UdefLookupEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  239 */     int col = startCol_;
/*  240 */     stmt_.setInt(col++, evo_.getUdefLookupId());
/*  241 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(UdefLookupEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  246 */     int col = startCol_;
/*  247 */     stmt_.setString(col++, evo_.getVisId());
/*  248 */     stmt_.setString(col++, evo_.getDescription());
/*  249 */     stmt_.setString(col++, evo_.getGenTableName());
/*  250 */     if (evo_.getAutoSubmit())
/*  251 */       stmt_.setString(col++, "Y");
/*      */     else
/*  253 */       stmt_.setString(col++, " ");
/*  254 */     if (evo_.getScenario())
/*  255 */       stmt_.setString(col++, "Y");
/*      */     else
/*  257 */       stmt_.setString(col++, " ");
/*  258 */     stmt_.setInt(col++, evo_.getTimeLvl());
/*  259 */     stmt_.setInt(col++, evo_.getYearStartMonth());
/*  260 */     if (evo_.getTimeRange())
/*  261 */       stmt_.setString(col++, "Y");
/*      */     else
/*  263 */       stmt_.setString(col++, " ");
/*  264 */     stmt_.setTimestamp(col++, evo_.getLastSubmit());
/*  265 */     stmt_.setTimestamp(col++, evo_.getDataUpdated());
/*  266 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  267 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  268 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  269 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(UdefLookupPK pk)
/*      */     throws ValidationException
/*      */   {
/*  285 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  287 */     PreparedStatement stmt = null;
/*  288 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  292 */       stmt = getConnection().prepareStatement("select UDEF_LOOKUP.UDEF_LOOKUP_ID,UDEF_LOOKUP.VIS_ID,UDEF_LOOKUP.DESCRIPTION,UDEF_LOOKUP.GEN_TABLE_NAME,UDEF_LOOKUP.AUTO_SUBMIT,UDEF_LOOKUP.SCENARIO,UDEF_LOOKUP.TIME_LVL,UDEF_LOOKUP.YEAR_START_MONTH,UDEF_LOOKUP.TIME_RANGE,UDEF_LOOKUP.LAST_SUBMIT,UDEF_LOOKUP.DATA_UPDATED,UDEF_LOOKUP.UPDATED_BY_USER_ID,UDEF_LOOKUP.UPDATED_TIME,UDEF_LOOKUP.CREATED_TIME from UDEF_LOOKUP where    UDEF_LOOKUP_ID = ? ");
/*      */ 
/*  295 */       int col = 1;
/*  296 */       stmt.setInt(col++, pk.getUdefLookupId());
/*      */ 
/*  298 */       resultSet = stmt.executeQuery();
/*      */ 
/*  300 */       if (!resultSet.next()) {
/*  301 */         throw new ValidationException(new StringBuilder().append(getEntityName()).append(" select of ").append(pk).append(" not found").toString());
/*      */       }
/*      */ 
/*  304 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  305 */       if (this.mDetails.isModified())
/*  306 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  310 */       throw handleSQLException(pk, "select UDEF_LOOKUP.UDEF_LOOKUP_ID,UDEF_LOOKUP.VIS_ID,UDEF_LOOKUP.DESCRIPTION,UDEF_LOOKUP.GEN_TABLE_NAME,UDEF_LOOKUP.AUTO_SUBMIT,UDEF_LOOKUP.SCENARIO,UDEF_LOOKUP.TIME_LVL,UDEF_LOOKUP.YEAR_START_MONTH,UDEF_LOOKUP.TIME_RANGE,UDEF_LOOKUP.LAST_SUBMIT,UDEF_LOOKUP.DATA_UPDATED,UDEF_LOOKUP.UPDATED_BY_USER_ID,UDEF_LOOKUP.UPDATED_TIME,UDEF_LOOKUP.CREATED_TIME from UDEF_LOOKUP where    UDEF_LOOKUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  314 */       closeResultSet(resultSet);
/*  315 */       closeStatement(stmt);
/*  316 */       closeConnection();
/*      */ 
/*  318 */       if (timer != null)
/*  319 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  364 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  365 */     generateKeys();
/*      */ 
/*  367 */     this.mDetails.postCreateInit();
/*      */ 
/*  369 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  374 */       duplicateValueCheckName();
/*      */ 
/*  376 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  377 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  378 */       stmt = getConnection().prepareStatement("insert into UDEF_LOOKUP ( UDEF_LOOKUP_ID,VIS_ID,DESCRIPTION,GEN_TABLE_NAME,AUTO_SUBMIT,SCENARIO,TIME_LVL,YEAR_START_MONTH,TIME_RANGE,LAST_SUBMIT,DATA_UPDATED,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  381 */       int col = 1;
/*  382 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  383 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  386 */       int resultCount = stmt.executeUpdate();
/*  387 */       if (resultCount != 1)
/*      */       {
/*  389 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" insert failed (").append(this.mDetails.getPK()).append("): resultCount=").append(resultCount).toString());
/*      */       }
/*      */ 
/*  392 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  396 */       throw handleSQLException(this.mDetails.getPK(), "insert into UDEF_LOOKUP ( UDEF_LOOKUP_ID,VIS_ID,DESCRIPTION,GEN_TABLE_NAME,AUTO_SUBMIT,SCENARIO,TIME_LVL,YEAR_START_MONTH,TIME_RANGE,LAST_SUBMIT,DATA_UPDATED,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  400 */       closeStatement(stmt);
/*  401 */       closeConnection();
/*      */ 
/*  403 */       if (timer != null) {
/*  404 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  410 */       getUdefLookupColumnDefDAO().update(this.mDetails.getColumnDefMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  416 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  436 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  438 */     PreparedStatement stmt = null;
/*  439 */     ResultSet resultSet = null;
/*  440 */     String sqlString = null;
/*      */     try
/*      */     {
/*  445 */       sqlString = "update UDEF_LOOKUP_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  446 */       stmt = getConnection().prepareStatement("update UDEF_LOOKUP_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  447 */       stmt.setInt(1, insertCount);
/*      */ 
/*  449 */       int resultCount = stmt.executeUpdate();
/*  450 */       if (resultCount != 1) {
/*  451 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" reserveIds: update failed: resultCount=").append(resultCount).toString());
/*      */       }
/*  453 */       closeStatement(stmt);
/*      */ 
/*  456 */       sqlString = "select SEQ_NUM from UDEF_LOOKUP_SEQ";
/*  457 */       stmt = getConnection().prepareStatement("select SEQ_NUM from UDEF_LOOKUP_SEQ");
/*  458 */       resultSet = stmt.executeQuery();
/*  459 */       if (!resultSet.next())
/*  460 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" reserveIds: select failed").toString());
/*  461 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  463 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  467 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  471 */       closeResultSet(resultSet);
/*  472 */       closeStatement(stmt);
/*  473 */       closeConnection();
/*      */ 
/*  475 */       if (timer != null)
/*  476 */         timer.logDebug("reserveIds", new StringBuilder().append("keys=").append(insertCount).toString()); 
/*  476 */     }
/*      */   }
/*      */ 
/*      */   public UdefLookupPK generateKeys()
/*      */   {
/*  486 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  488 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  491 */     if (insertCount == 0) {
/*  492 */       return this.mDetails.getPK();
/*      */     }
/*  494 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  496 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckName()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  509 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  510 */     PreparedStatement stmt = null;
/*  511 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  515 */       stmt = getConnection().prepareStatement("select count(*) from UDEF_LOOKUP where    VIS_ID = ? and not(    UDEF_LOOKUP_ID = ? )");
/*      */ 
/*  518 */       int col = 1;
/*  519 */       stmt.setString(col++, this.mDetails.getVisId());
/*  520 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  523 */       resultSet = stmt.executeQuery();
/*      */ 
/*  525 */       if (!resultSet.next()) {
/*  526 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" select of ").append(getPK()).append(" not found").toString());
/*      */       }
/*      */ 
/*  530 */       col = 1;
/*  531 */       int count = resultSet.getInt(col++);
/*  532 */       if (count > 0) {
/*  533 */         throw new DuplicateNameValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" Name").toString());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  539 */       throw handleSQLException(getPK(), "select count(*) from UDEF_LOOKUP where    VIS_ID = ? and not(    UDEF_LOOKUP_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  543 */       closeResultSet(resultSet);
/*  544 */       closeStatement(stmt);
/*  545 */       closeConnection();
/*      */ 
/*  547 */       if (timer != null)
/*  548 */         timer.logDebug("duplicateValueCheckName", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  579 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  581 */     generateKeys();
/*      */ 
/*  586 */     PreparedStatement stmt = null;
/*      */ 
/*  588 */     boolean mainChanged = this.mDetails.isModified();
/*  589 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  593 */       if (mainChanged) {
/*  594 */         duplicateValueCheckName();
/*      */       }
/*  596 */       if (getUdefLookupColumnDefDAO().update(this.mDetails.getColumnDefMap())) {
/*  597 */         dependantChanged = true;
/*      */       }
/*  599 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  602 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  603 */         stmt = getConnection().prepareStatement("update UDEF_LOOKUP set VIS_ID = ?,DESCRIPTION = ?,GEN_TABLE_NAME = ?,AUTO_SUBMIT = ?,SCENARIO = ?,TIME_LVL = ?,YEAR_START_MONTH = ?,TIME_RANGE = ?,LAST_SUBMIT = ?,DATA_UPDATED = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    UDEF_LOOKUP_ID = ? ");
/*      */ 
/*  606 */         int col = 1;
/*  607 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  608 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  611 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  614 */         if (resultCount != 1) {
/*  615 */           throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" update failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
/*      */         }
/*      */ 
/*  618 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  627 */       throw handleSQLException(getPK(), "update UDEF_LOOKUP set VIS_ID = ?,DESCRIPTION = ?,GEN_TABLE_NAME = ?,AUTO_SUBMIT = ?,SCENARIO = ?,TIME_LVL = ?,YEAR_START_MONTH = ?,TIME_RANGE = ?,LAST_SUBMIT = ?,DATA_UPDATED = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    UDEF_LOOKUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  631 */       closeStatement(stmt);
/*  632 */       closeConnection();
/*      */ 
/*  634 */       if ((timer != null) && (
/*  635 */         (mainChanged) || (dependantChanged)))
/*  636 */         timer.logDebug("store", new StringBuilder().append(this.mDetails.getPK()).append("(").append(mainChanged).append(",").append(dependantChanged).append(")").toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  654 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  655 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  660 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  665 */       stmt = getConnection().prepareStatement("delete from UDEF_LOOKUP where    UDEF_LOOKUP_ID = ? ");
/*      */ 
/*  668 */       int col = 1;
/*  669 */       stmt.setInt(col++, this.mDetails.getUdefLookupId());
/*      */ 
/*  671 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  673 */       if (resultCount != 1) {
/*  674 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" delete failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  680 */       throw handleSQLException(getPK(), "delete from UDEF_LOOKUP where    UDEF_LOOKUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  684 */       closeStatement(stmt);
/*  685 */       closeConnection();
/*      */ 
/*  687 */       if (timer != null)
/*  688 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllUdefLookupsELO getAllUdefLookups()
/*      */   {
/*  722 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  723 */     PreparedStatement stmt = null;
/*  724 */     ResultSet resultSet = null;
/*  725 */     AllUdefLookupsELO results = new AllUdefLookupsELO();
/*      */     try
/*      */     {
/*  728 */       stmt = getConnection().prepareStatement(SQL_ALL_UDEF_LOOKUPS);
/*  729 */       int col = 1;
/*  730 */       resultSet = stmt.executeQuery();
/*  731 */       while (resultSet.next())
/*      */       {
/*  733 */         col = 2;
/*      */ 
/*  736 */         UdefLookupPK pkUdefLookup = new UdefLookupPK(resultSet.getInt(col++));
/*      */ 
/*  739 */         String textUdefLookup = resultSet.getString(col++);
/*      */ 
/*  743 */         UdefLookupRefImpl erUdefLookup = new UdefLookupRefImpl(pkUdefLookup, textUdefLookup);
/*      */ 
/*  748 */         String col1 = resultSet.getString(col++);
/*  749 */         String col2 = resultSet.getString(col++);
/*  750 */         String col3 = resultSet.getString(col++);
/*  751 */         if (resultSet.wasNull())
/*  752 */           col3 = "";
/*  753 */         Timestamp col4 = resultSet.getTimestamp(col++);
/*  754 */         Timestamp col5 = resultSet.getTimestamp(col++);
/*      */ 
/*  757 */         results.add(erUdefLookup, col1, col2, col3.equals("Y"), col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  769 */       throw handleSQLException(SQL_ALL_UDEF_LOOKUPS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  773 */       closeResultSet(resultSet);
/*  774 */       closeStatement(stmt);
/*  775 */       closeConnection();
/*      */     }
/*      */ 
/*  778 */     if (timer != null) {
/*  779 */       timer.logDebug("getAllUdefLookups", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  783 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(UdefLookupPK pk)
/*      */   {
/*  810 */     Set emptyStrings = Collections.emptySet();
/*  811 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(UdefLookupPK pk, Set<String> exclusionTables)
/*      */   {
/*  817 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  819 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  821 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  823 */       PreparedStatement stmt = null;
/*      */ 
/*  825 */       int resultCount = 0;
/*  826 */       String s = null;
/*      */       try
/*      */       {
/*  829 */         s = new StringBuilder().append(SQL_DELETE_CHILDRENS_DEPENDANTS[i][1]).append(SQL_DELETE_DEPENDANT_CRITERIA).toString();
/*      */ 
/*  831 */         if (this._log.isDebugEnabled()) {
/*  832 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  834 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  837 */         int col = 1;
/*  838 */         stmt.setInt(col++, pk.getUdefLookupId());
/*      */ 
/*  841 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  845 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  849 */         closeStatement(stmt);
/*  850 */         closeConnection();
/*      */ 
/*  852 */         if (timer != null) {
/*  853 */           timer.logDebug("deleteDependants", new StringBuilder().append("A[").append(i).append("] count=").append(resultCount).toString());
/*      */         }
/*      */       }
/*      */     }
/*  857 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  859 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  861 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  863 */       PreparedStatement stmt = null;
/*      */ 
/*  865 */       int resultCount = 0;
/*  866 */       String s = null;
/*      */       try
/*      */       {
/*  869 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  871 */         if (this._log.isDebugEnabled()) {
/*  872 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  874 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  877 */         int col = 1;
/*  878 */         stmt.setInt(col++, pk.getUdefLookupId());
/*      */ 
/*  881 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  885 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  889 */         closeStatement(stmt);
/*  890 */         closeConnection();
/*      */ 
/*  892 */         if (timer != null)
/*  893 */           timer.logDebug("deleteDependants", new StringBuilder().append("B[").append(i).append("] count=").append(resultCount).toString());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public UdefLookupEVO getDetails(UdefLookupPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  912 */     return getDetails(new UdefLookupCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public UdefLookupEVO getDetails(UdefLookupCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  929 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  932 */     if (this.mDetails == null) {
/*  933 */       doLoad(paramCK.getUdefLookupPK());
/*      */     }
/*  935 */     else if (!this.mDetails.getPK().equals(paramCK.getUdefLookupPK())) {
/*  936 */       doLoad(paramCK.getUdefLookupPK());
/*      */     }
/*      */ 
/*  945 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isColumnDefAllItemsLoaded()))
/*      */     {
/*  950 */       this.mDetails.setColumnDef(getUdefLookupColumnDefDAO().getAll(this.mDetails.getUdefLookupId(), dependants, this.mDetails.getColumnDef()));
/*      */ 
/*  957 */       this.mDetails.setColumnDefAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  960 */     if ((paramCK instanceof UdefLookupColumnDefCK))
/*      */     {
/*  962 */       if (this.mDetails.getColumnDef() == null) {
/*  963 */         this.mDetails.loadColumnDefItem(getUdefLookupColumnDefDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  966 */         UdefLookupColumnDefPK pk = ((UdefLookupColumnDefCK)paramCK).getUdefLookupColumnDefPK();
/*  967 */         UdefLookupColumnDefEVO evo = this.mDetails.getColumnDefItem(pk);
/*  968 */         if (evo == null) {
/*  969 */           this.mDetails.loadColumnDefItem(getUdefLookupColumnDefDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  974 */     UdefLookupEVO details = new UdefLookupEVO();
/*  975 */     details = this.mDetails.deepClone();
/*      */ 
/*  977 */     if (timer != null) {
/*  978 */       timer.logDebug("getDetails", new StringBuilder().append(paramCK).append(" ").append(dependants).toString());
/*      */     }
/*  980 */     return details;
/*      */   }
/*      */ 
/*      */   public UdefLookupEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  986 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  990 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  993 */     UdefLookupEVO details = this.mDetails.deepClone();
/*      */ 
/*  995 */     if (timer != null) {
/*  996 */       timer.logDebug("getDetails", new StringBuilder().append(this.mDetails.getPK()).append(" ").append(dependants).toString());
/*      */     }
/*  998 */     return details;
/*      */   }
/*      */ 
/*      */   protected UdefLookupColumnDefDAO getUdefLookupColumnDefDAO()
/*      */   {
/* 1007 */     if (this.mUdefLookupColumnDefDAO == null)
/*      */     {
/* 1009 */       if (this.mDataSource != null)
/* 1010 */         this.mUdefLookupColumnDefDAO = new UdefLookupColumnDefDAO(this.mDataSource);
/*      */       else {
/* 1012 */         this.mUdefLookupColumnDefDAO = new UdefLookupColumnDefDAO(getConnection());
/*      */       }
/*      */     }
/* 1015 */     return this.mUdefLookupColumnDefDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1020 */     return "UdefLookup";
/*      */   }
/*      */ 
/*      */   public UdefLookupRef getRef(UdefLookupPK paramUdefLookupPK)
/*      */     throws ValidationException
/*      */   {
/* 1026 */     UdefLookupEVO evo = getDetails(paramUdefLookupPK, "");
/* 1027 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1052 */     if (c == null)
/* 1053 */       return;
/* 1054 */     Iterator iter = c.iterator();
/* 1055 */     while (iter.hasNext())
/*      */     {
/* 1057 */       UdefLookupEVO evo = (UdefLookupEVO)iter.next();
/* 1058 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(UdefLookupEVO evo, String dependants)
/*      */   {
/* 1072 */     if (evo.getUdefLookupId() < 1) {
/* 1073 */       return;
/*      */     }
/*      */ 
/* 1081 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1084 */       if (!evo.isColumnDefAllItemsLoaded())
/*      */       {
/* 1086 */         evo.setColumnDef(getUdefLookupColumnDefDAO().getAll(evo.getUdefLookupId(), dependants, evo.getColumnDef()));
/*      */ 
/* 1093 */         evo.setColumnDefAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public List getTableData(String tablename, List columnDef)
/*      */   {
/* 1103 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1105 */     boolean taStartDate = false;
/* 1106 */     boolean taEndDate = false;
/* 1107 */     UdefColumn column = null;
/* 1108 */     for (int i = 0; i < columnDef.size(); i++)
/*      */     {
/* 1110 */       column = (UdefColumn)columnDef.get(i);
/* 1111 */       if (column.isRangeStart())
/* 1112 */         taStartDate = true;
/* 1113 */       else if (column.isRangeEnd()) {
/* 1114 */         taEndDate = true;
/*      */       }
/*      */     }
/* 1117 */     StringBuilder sql = new StringBuilder();
/* 1118 */     sql.append("select ");
/* 1119 */     for (int i = 0; i < columnDef.size(); i++)
/*      */     {
/* 1121 */       column = (UdefColumn)columnDef.get(i);
/* 1122 */       if (i != 0) {
/* 1123 */         sql.append(" , ");
/*      */       }
/* 1125 */       sql.append(column.getColumnName());
/*      */     }
/* 1127 */     sql.append(" from ").append(tablename);
/* 1128 */     sql.append(" order by CP_USER_SEQ");
/*      */ 
/* 1130 */     PreparedStatement stmt = null;
/* 1131 */     ResultSet resultSet = null;
/* 1132 */     List results = new ArrayList();
/*      */     try
/*      */     {
/* 1135 */       stmt = getConnection().prepareStatement(sql.toString());
/* 1136 */       resultSet = stmt.executeQuery();
/* 1137 */       while (resultSet.next())
/*      */       {
/* 1139 */         Object[] rowData = new Object[columnDef.size()];
/* 1140 */         int col = 1;
/*      */ 
/* 1142 */         for (int i = 0; i < columnDef.size(); i++)
/*      */         {
/* 1144 */           column = (UdefColumn)columnDef.get(i);
/* 1145 */           switch (column.getType())
/*      */           {
/*      */           case 1:
/* 1148 */             rowData[i] = resultSet.getBigDecimal(col++);
/* 1149 */             break;
/*      */           case 2:
/* 1151 */             String test = resultSet.getString(col++);
/* 1152 */             if ((resultSet.wasNull()) || ("N".equals(test)))
/* 1153 */               rowData[i] = Boolean.FALSE;
/*      */             else
/* 1155 */               rowData[i] = Boolean.TRUE;
/* 1156 */             break;
/*      */           case 3:
/*      */           case 4:
/*      */           case 5:
/* 1160 */             Timestamp ts = resultSet.getTimestamp(col++);
/* 1161 */             if (resultSet.wasNull()) continue;
/* 1162 */             rowData[i] = new Date(ts.getTime()); break;
/*      */           default:
/* 1165 */             rowData[i] = resultSet.getString(col++);
/*      */           }
/*      */         }
/* 1168 */         results.add(rowData);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1173 */       throw handleSQLException(sql.toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1177 */       closeResultSet(resultSet);
/* 1178 */       closeStatement(stmt);
/* 1179 */       closeConnection();
/*      */     }
/*      */ 
/* 1182 */     if (timer != null) {
/* 1183 */       timer.logDebug("getTableData", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/* 1187 */     return results;
/*      */   }
/*      */ 
/*      */   public void saveTableData(String tableName, List columnDef, List tableData)
/*      */     throws ValidationException
/*      */   {
/* 1193 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1195 */     UdefColumn column = null;
/* 1196 */     StringBuilder sql = new StringBuilder();
/* 1197 */     sql.append("delete from ").append(tableName);
/*      */ 
/* 1199 */     PreparedStatement stmt = null;
/* 1200 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1203 */       stmt = getConnection().prepareStatement(sql.toString());
/* 1204 */       stmt.executeUpdate();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1208 */       throw handleSQLException(sql.toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1212 */       closeResultSet(resultSet);
/* 1213 */       closeStatement(stmt);
/* 1214 */       closeConnection();
/*      */     }
/*      */ 
/* 1217 */     sql = new StringBuilder();
/* 1218 */     sql.append("insert into ").append(tableName).append(" ( ");
/* 1219 */     for (int i = 0; i < columnDef.size(); i++)
/*      */     {
/* 1221 */       column = (UdefColumn)columnDef.get(i);
/* 1222 */       if (i != 0)
/* 1223 */         sql.append(", ");
/* 1224 */       sql.append(column.getColumnName());
/*      */     }
/* 1226 */     sql.append(",CP_USER_SEQ) ");
/* 1227 */     sql.append("values ( ");
/* 1228 */     for (int i = 0; i < columnDef.size(); i++)
/*      */     {
/* 1230 */       if (i != 0)
/* 1231 */         sql.append(", ");
/* 1232 */       sql.append("? ");
/*      */     }
/* 1234 */     sql.append(",?)");
/*      */ 
/* 1236 */     Calendar cal = Calendar.getInstance();
/*      */ 
/* 1238 */     int[] results = null;
/*      */     try
/*      */     {
/* 1241 */       stmt = getConnection().prepareStatement(sql.toString());
/* 1242 */       for (int i = 0; i < tableData.size(); i++)
/*      */       {
/* 1244 */         Object[] rowData = (Object[])(Object[])tableData.get(i);
/* 1245 */         int col = 1;
/* 1246 */         Timestamp taStart = null;
/* 1247 */         Timestamp taEnd = null;
/* 1248 */         for (int j = 0; j < columnDef.size(); j++)
/*      */         {
/* 1250 */           Timestamp dateVal = null;
/* 1251 */           column = (UdefColumn)columnDef.get(j);
/* 1252 */           switch (column.getType())
/*      */           {
/*      */           case 1:
/* 1255 */             BigDecimal bd = (BigDecimal)rowData[j];
/*      */ 
/* 1262 */             stmt.setBigDecimal(col++, bd);
/* 1263 */             break;
/*      */           case 2:
/* 1266 */             Boolean test = (Boolean)rowData[j];
/* 1267 */             if ((test != null) && (test.booleanValue()))
/* 1268 */               stmt.setString(col++, "Y");
/*      */             else
/* 1270 */               stmt.setString(col++, "N");
/* 1271 */             break;
/*      */           case 3:
/*      */           case 4:
/*      */           case 5:
/* 1277 */             if (rowData[j] != null)
/*      */             {
/* 1279 */               dateVal = new Timestamp(((Date)rowData[j]).getTime());
/* 1280 */               if (column.isRangeStart())
/* 1281 */                 taStart = dateVal;
/* 1282 */               else if (column.isRangeEnd())
/* 1283 */                 taEnd = dateVal;
/* 1284 */               stmt.setTimestamp(col++, dateVal);
/*      */             }
/*      */             else {
/* 1287 */               stmt.setNull(col++, 93);
/* 1288 */             }break;
/*      */           default:
/* 1291 */             String str = (String)rowData[j];
/* 1292 */             if ((str != null) && (str.length() > column.getSize().intValue()))
/* 1293 */               throw new ValidationException(new StringBuilder().append("row ").append(i).append(" - ").append(column.getTitle()).append(" is too large max(").append(column.getSize()).append(")").toString());
/* 1294 */             stmt.setString(col++, str);
/*      */           }
/*      */         }
/*      */ 
/* 1298 */         stmt.setInt(columnDef.size() + 1, i + 1);
/*      */ 
/* 1300 */         if ((taStart != null) && (taEnd != null) && 
/* 1301 */           (taStart.compareTo(taEnd) > 0))
/* 1302 */           throw new ValidationException(new StringBuilder().append("row ").append(i).append(" - start date is after end date").toString());
/* 1303 */         stmt.addBatch();
/*      */       }
/* 1305 */       results = stmt.executeBatch();
/*      */     }
/*      */     catch (BatchUpdateException bue)
/*      */     {
/* 1309 */       throw new ValidationException(bue.getMessage());
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1313 */       throw handleSQLException(sql.toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1317 */       closeResultSet(resultSet);
/* 1318 */       closeStatement(stmt);
/* 1319 */       closeConnection();
/*      */     }
/*      */ 
/* 1322 */     if (timer != null)
/* 1323 */       timer.logDebug("saveTableData", "");
/*      */   }
/*      */ 
/*      */   public void setDataChanged(UdefLookupPK key)
/*      */   {
/* 1328 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1330 */     PreparedStatement stmt = null;
/* 1331 */     ResultSet resultSet = null;
/*      */ 
/* 1333 */     StringBuilder sql = new StringBuilder();
/* 1334 */     sql.append("update UDEF_LOOKUP set DATA_UPDATED = sysdate where UDEF_LOOKUP_ID = ?");
/*      */     try
/*      */     {
/* 1337 */       stmt = getConnection().prepareStatement(sql.toString());
/* 1338 */       stmt.setInt(1, key.getUdefLookupId());
/* 1339 */       stmt.executeUpdate();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1343 */       throw handleSQLException(sql.toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1347 */       closeResultSet(resultSet);
/* 1348 */       closeStatement(stmt);
/* 1349 */       closeConnection();
/*      */     }
/*      */ 
/* 1352 */     if (timer != null)
/* 1353 */       timer.logDebug("setSubmitData", "");
/*      */   }
/*      */ 
/*      */   public void setSubmitData(UdefLookupPK key)
/*      */   {
/* 1358 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1360 */     PreparedStatement stmt = null;
/* 1361 */     ResultSet resultSet = null;
/*      */ 
/* 1363 */     StringBuilder sql = new StringBuilder();
/* 1364 */     sql.append("update UDEF_LOOKUP set LAST_SUBMIT = sysdate where UDEF_LOOKUP_ID = ?");
/*      */     try
/*      */     {
/* 1367 */       stmt = getConnection().prepareStatement(sql.toString());
/* 1368 */       stmt.setInt(1, key.getUdefLookupId());
/* 1369 */       stmt.executeUpdate();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1373 */       throw handleSQLException(sql.toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1377 */       closeResultSet(resultSet);
/* 1378 */       closeStatement(stmt);
/* 1379 */       closeConnection();
/*      */     }
/*      */ 
/* 1382 */     if (timer != null)
/* 1383 */       timer.logDebug("setSubmitData", "");
/*      */   }
/*      */ 
/*      */   public EntityList getUdefForms(Object key)
/*      */   {
/* 1388 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "select  decode(form.TYPE,0,'Table',1,'Fixed',2,'Dynamic',3,'Finance') \"Form Type\"", "        ,form.VIS_ID as \"Form\"", "from    UDEF_LOOKUP lu", "join    XML_FORM form on (instr(DEFINITION,'lookupName=\"'||lu.VIS_ID||'\"') > 0)", "where   lu.UDEF_LOOKUP_ID = <udefLookupId>", "order   by form.TYPE,form.VIS_ID" });
/*      */ 
/* 1396 */     SqlExecutor sqle = new SqlExecutor("getUdefForms", getDataSource(), sqlb, this._log);
/* 1397 */     sqle.addBindVariable("<udefLookupId>", Integer.valueOf(((UdefLookupPK)key).getUdefLookupId()));
/* 1398 */     return sqle.getEntityList();
/*      */   }
/*      */ 
/*      */   public UdefLookupEVO getUdefLookup(String visId) throws ValidationException
/*      */   {
/* 1403 */     PreparedStatement stmt = null;
/* 1404 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1408 */       stmt = getConnection().prepareStatement("select UDEF_LOOKUP.UDEF_LOOKUP_ID,UDEF_LOOKUP.VIS_ID,UDEF_LOOKUP.DESCRIPTION,UDEF_LOOKUP.GEN_TABLE_NAME,UDEF_LOOKUP.AUTO_SUBMIT,UDEF_LOOKUP.SCENARIO,UDEF_LOOKUP.TIME_LVL,UDEF_LOOKUP.YEAR_START_MONTH,UDEF_LOOKUP.TIME_RANGE,UDEF_LOOKUP.LAST_SUBMIT,UDEF_LOOKUP.DATA_UPDATED,UDEF_LOOKUP.UPDATED_BY_USER_ID,UDEF_LOOKUP.UPDATED_TIME,UDEF_LOOKUP.CREATED_TIME from UDEF_LOOKUP where    VIS_ID = ? ");
/*      */ 
/* 1413 */       int col = 1;
/* 1414 */       stmt.setString(col++, visId);
/*      */ 
/* 1416 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1418 */       if (!resultSet.next()) {
/* 1419 */         throw new ValidationException(new StringBuilder().append(getEntityName()).append(" select of ").append(visId).append(" not found").toString());
/*      */       }
/*      */ 
/* 1422 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 1423 */       if (this.mDetails.isModified()) {
/* 1424 */         this._log.info("doLoad", this.mDetails);
/*      */       }
/* 1426 */       this.mDetails.setColumnDef(getUdefLookupColumnDefDAO().getAll(this.mDetails.getUdefLookupId(), "<0>", this.mDetails.getColumnDef()));
/*      */ 
/* 1433 */       this.mDetails.setColumnDefAllItemsLoaded(true);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1437 */       throw new RuntimeException(sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1441 */       closeResultSet(resultSet);
/* 1442 */       closeStatement(stmt);
/* 1443 */       closeConnection();
/*      */     }
/* 1445 */     return this.mDetails;
/*      */   }
/*      */
			public AllUdefLookupsELO getAllUdefLookupsForLoggedUser(int userId) {
				Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  723 */     PreparedStatement stmt = null;
/*  724 */     ResultSet resultSet = null;
/*  725 */     AllUdefLookupsELO results = new AllUdefLookupsELO();
/*      */     try
/*      */     {
/*  728 */       stmt = getConnection().prepareStatement(SQL_ALL_UDEF_LOOKUPS_FOR_USER);
/*  729 */       int col = 1;
/*  730 */       resultSet = stmt.executeQuery();
/*  731 */       while (resultSet.next())
/*      */       {
/*  733 */         col = 2;
/*      */ 
/*  736 */         UdefLookupPK pkUdefLookup = new UdefLookupPK(resultSet.getInt(col++));
/*      */ 
/*  739 */         String textUdefLookup = resultSet.getString(col++);
/*      */ 
/*  743 */         UdefLookupRefImpl erUdefLookup = new UdefLookupRefImpl(pkUdefLookup, textUdefLookup);
/*      */ 
/*  748 */         String col1 = resultSet.getString(col++);
/*  749 */         String col2 = resultSet.getString(col++);
/*  750 */         String col3 = resultSet.getString(col++);
/*  751 */         if (resultSet.wasNull())
/*  752 */           col3 = "";
/*  753 */         Timestamp col4 = resultSet.getTimestamp(col++);
/*  754 */         Timestamp col5 = resultSet.getTimestamp(col++);
/*      */ 
/*  757 */         results.add(erUdefLookup, col1, col2, col3.equals("Y"), col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  769 */       throw handleSQLException(SQL_ALL_UDEF_LOOKUPS_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  773 */       closeResultSet(resultSet);
/*  774 */       closeStatement(stmt);
/*  775 */       closeConnection();
/*      */     }
/*      */ 
/*  778 */     if (timer != null) {
/*  779 */       timer.logDebug("getAllUdefLookupsForLoggedUser", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  783 */     return results;
			}
}

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.udeflookup.UdefLookupDAO
 * JD-Core Version:    0.6.0
 */