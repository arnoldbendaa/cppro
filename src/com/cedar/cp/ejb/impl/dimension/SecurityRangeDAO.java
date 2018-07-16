/*      */ package com.cedar.cp.ejb.impl.dimension;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.dimension.AllSecurityRangesELO;
/*      */ import com.cedar.cp.dto.dimension.AllSecurityRangesForModelELO;
/*      */ import com.cedar.cp.dto.dimension.DimensionCK;
/*      */ import com.cedar.cp.dto.dimension.DimensionPK;
/*      */ import com.cedar.cp.dto.dimension.DimensionRefImpl;
/*      */ import com.cedar.cp.dto.dimension.SecurityRangeCK;
/*      */ import com.cedar.cp.dto.dimension.SecurityRangePK;
/*      */ import com.cedar.cp.dto.dimension.SecurityRangeRefImpl;
/*      */ import com.cedar.cp.dto.dimension.SecurityRangeRowCK;
/*      */ import com.cedar.cp.dto.dimension.SecurityRangeRowPK;
/*      */ import com.cedar.cp.dto.model.ModelDimensionRelPK;
/*      */ import com.cedar.cp.dto.model.ModelDimensionRelRefImpl;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
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
/*      */ public class SecurityRangeDAO extends AbstractDAO
/*      */ {
/*   44 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select SECURITY_RANGE.SECURITY_RANGE_ID,SECURITY_RANGE.DIMENSION_ID,SECURITY_RANGE.VIS_ID,SECURITY_RANGE.DESCRIPTION,SECURITY_RANGE.VERSION_NUM,SECURITY_RANGE.UPDATED_BY_USER_ID,SECURITY_RANGE.UPDATED_TIME,SECURITY_RANGE.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from SECURITY_RANGE where    SECURITY_RANGE_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into SECURITY_RANGE ( SECURITY_RANGE_ID,DIMENSION_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_SECURITYRANGENAME = "select count(*) from SECURITY_RANGE where    DIMENSION_ID = ? AND VIS_ID = ? and not(    SECURITY_RANGE_ID = ? )";
/*      */   protected static final String SQL_STORE = "update SECURITY_RANGE set DIMENSION_ID = ?,VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_RANGE_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from SECURITY_RANGE where SECURITY_RANGE_ID = ?";
/*  467 */   protected static String SQL_ALL_SECURITY_RANGES = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,SECURITY_RANGE.SECURITY_RANGE_ID      ,SECURITY_RANGE.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,SECURITY_RANGE.DESCRIPTION from SECURITY_RANGE    ,DIMENSION    ,MODEL_DIMENSION_REL    ,MODEL where 1=1   and SECURITY_RANGE.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  SECURITY_RANGE.DIMENSION_ID = MODEL_DIMENSION_REL.DIMENSION_ID AND MODEL.MODEL_ID = MODEL_DIMENSION_REL.MODEL_ID AND DIMENSION.DIMENSION_ID = MODEL_DIMENSION_REL.DIMENSION_ID order by MODEL.VIS_ID, DIMENSION.VIS_ID, SECURITY_RANGE.VIS_ID";
/*      */ 
/*  609 */   protected static String SQL_ALL_SECURITY_RANGES_FOR_MODEL = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,SECURITY_RANGE.SECURITY_RANGE_ID      ,SECURITY_RANGE.VIS_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,MODEL.MODEL_ID      ,DIMENSION.DIMENSION_ID      ,SECURITY_RANGE.DESCRIPTION from SECURITY_RANGE    ,DIMENSION    ,MODEL    ,MODEL_DIMENSION_REL where 1=1   and SECURITY_RANGE.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  SECURITY_RANGE.DIMENSION_ID = MODEL_DIMENSION_REL.DIMENSION_ID AND MODEL.MODEL_ID = MODEL_DIMENSION_REL.MODEL_ID AND DIMENSION.DIMENSION_ID = MODEL_DIMENSION_REL.DIMENSION_ID AND MODEL.MODEL_ID = ? order by SECURITY_RANGE.VIS_ID";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from SECURITY_RANGE where    SECURITY_RANGE_ID = ? ";
/*  897 */   private static String[][] SQL_DELETE_CHILDREN = { { "SECURITY_RANGE_ROW", "delete from SECURITY_RANGE_ROW where     SECURITY_RANGE_ROW.SECURITY_RANGE_ID = ? " } };
/*      */ 
/*  906 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  910 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and SECURITY_RANGE.SECURITY_RANGE_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from SECURITY_RANGE where 1=1 and SECURITY_RANGE.DIMENSION_ID = ? order by  SECURITY_RANGE.SECURITY_RANGE_ID";
/*      */   protected static final String SQL_GET_ALL = " from SECURITY_RANGE where    DIMENSION_ID = ? ";
/*      */   protected SecurityRangeRowDAO mSecurityRangeRowDAO;
/*      */   protected SecurityRangeEVO mDetails;
/*      */ 
/*      */   public SecurityRangeDAO(Connection connection)
/*      */   {
/*   51 */     super(connection);
/*      */   }
/*      */ 
/*      */   public SecurityRangeDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public SecurityRangeDAO(DataSource ds)
/*      */   {
/*   67 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected SecurityRangePK getPK()
/*      */   {
/*   75 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(SecurityRangeEVO details)
/*      */   {
/*   84 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private SecurityRangeEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  103 */     int col = 1;
/*  104 */     SecurityRangeEVO evo = new SecurityRangeEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  113 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  114 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  115 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  116 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(SecurityRangeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  121 */     int col = startCol_;
/*  122 */     stmt_.setInt(col++, evo_.getSecurityRangeId());
/*  123 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(SecurityRangeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  128 */     int col = startCol_;
/*  129 */     stmt_.setInt(col++, evo_.getDimensionId());
/*  130 */     stmt_.setString(col++, evo_.getVisId());
/*  131 */     stmt_.setString(col++, evo_.getDescription());
/*  132 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  133 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  134 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  135 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  136 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(SecurityRangePK pk)
/*      */     throws ValidationException
/*      */   {
/*  152 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  154 */     PreparedStatement stmt = null;
/*  155 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  159 */       stmt = getConnection().prepareStatement("select SECURITY_RANGE.SECURITY_RANGE_ID,SECURITY_RANGE.DIMENSION_ID,SECURITY_RANGE.VIS_ID,SECURITY_RANGE.DESCRIPTION,SECURITY_RANGE.VERSION_NUM,SECURITY_RANGE.UPDATED_BY_USER_ID,SECURITY_RANGE.UPDATED_TIME,SECURITY_RANGE.CREATED_TIME from SECURITY_RANGE where    SECURITY_RANGE_ID = ? ");
/*      */ 
/*  162 */       int col = 1;
/*  163 */       stmt.setInt(col++, pk.getSecurityRangeId());
/*      */ 
/*  165 */       resultSet = stmt.executeQuery();
/*      */ 
/*  167 */       if (!resultSet.next()) {
/*  168 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  171 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  172 */       if (this.mDetails.isModified())
/*  173 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  177 */       throw handleSQLException(pk, "select SECURITY_RANGE.SECURITY_RANGE_ID,SECURITY_RANGE.DIMENSION_ID,SECURITY_RANGE.VIS_ID,SECURITY_RANGE.DESCRIPTION,SECURITY_RANGE.VERSION_NUM,SECURITY_RANGE.UPDATED_BY_USER_ID,SECURITY_RANGE.UPDATED_TIME,SECURITY_RANGE.CREATED_TIME from SECURITY_RANGE where    SECURITY_RANGE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  181 */       closeResultSet(resultSet);
/*  182 */       closeStatement(stmt);
/*  183 */       closeConnection();
/*      */ 
/*  185 */       if (timer != null)
/*  186 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  219 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  220 */     this.mDetails.postCreateInit();
/*      */ 
/*  222 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  227 */       duplicateValueCheckSecurityRangeName();
/*      */ 
/*  229 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  230 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  231 */       stmt = getConnection().prepareStatement("insert into SECURITY_RANGE ( SECURITY_RANGE_ID,DIMENSION_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");
/*      */ 
/*  234 */       int col = 1;
/*  235 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  236 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  239 */       int resultCount = stmt.executeUpdate();
/*  240 */       if (resultCount != 1)
/*      */       {
/*  242 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  245 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  249 */       throw handleSQLException(this.mDetails.getPK(), "insert into SECURITY_RANGE ( SECURITY_RANGE_ID,DIMENSION_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  253 */       closeStatement(stmt);
/*  254 */       closeConnection();
/*      */ 
/*  256 */       if (timer != null) {
/*  257 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  263 */       getSecurityRangeRowDAO().update(this.mDetails.getRangeRowsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  269 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckSecurityRangeName()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  285 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  286 */     PreparedStatement stmt = null;
/*  287 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  291 */       stmt = getConnection().prepareStatement("select count(*) from SECURITY_RANGE where    DIMENSION_ID = ? AND VIS_ID = ? and not(    SECURITY_RANGE_ID = ? )");
/*      */ 
/*  294 */       int col = 1;
/*  295 */       stmt.setInt(col++, this.mDetails.getDimensionId());
/*  296 */       stmt.setString(col++, this.mDetails.getVisId());
/*  297 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  300 */       resultSet = stmt.executeQuery();
/*      */ 
/*  302 */       if (!resultSet.next()) {
/*  303 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  307 */       col = 1;
/*  308 */       int count = resultSet.getInt(col++);
/*  309 */       if (count > 0) {
/*  310 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " SecurityRangeName");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  316 */       throw handleSQLException(getPK(), "select count(*) from SECURITY_RANGE where    DIMENSION_ID = ? AND VIS_ID = ? and not(    SECURITY_RANGE_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  320 */       closeResultSet(resultSet);
/*  321 */       closeStatement(stmt);
/*  322 */       closeConnection();
/*      */ 
/*  324 */       if (timer != null)
/*  325 */         timer.logDebug("duplicateValueCheckSecurityRangeName", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  351 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  355 */     PreparedStatement stmt = null;
/*      */ 
/*  357 */     boolean mainChanged = this.mDetails.isModified();
/*  358 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  362 */       if (mainChanged) {
/*  363 */         duplicateValueCheckSecurityRangeName();
/*      */       }
/*  365 */       if (getSecurityRangeRowDAO().update(this.mDetails.getRangeRowsMap())) {
/*  366 */         dependantChanged = true;
/*      */       }
/*  368 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  371 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  374 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  375 */         stmt = getConnection().prepareStatement("update SECURITY_RANGE set DIMENSION_ID = ?,VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_RANGE_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  378 */         int col = 1;
/*  379 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  380 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  382 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  385 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  387 */         if (resultCount == 0) {
/*  388 */           checkVersionNum();
/*      */         }
/*  390 */         if (resultCount != 1) {
/*  391 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  394 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  403 */       throw handleSQLException(getPK(), "update SECURITY_RANGE set DIMENSION_ID = ?,VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_RANGE_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  407 */       closeStatement(stmt);
/*  408 */       closeConnection();
/*      */ 
/*  410 */       if ((timer != null) && (
/*  411 */         (mainChanged) || (dependantChanged)))
/*  412 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  424 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  425 */     PreparedStatement stmt = null;
/*  426 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  430 */       stmt = getConnection().prepareStatement("select VERSION_NUM from SECURITY_RANGE where SECURITY_RANGE_ID = ?");
/*      */ 
/*  433 */       int col = 1;
/*  434 */       stmt.setInt(col++, this.mDetails.getSecurityRangeId());
/*      */ 
/*  437 */       resultSet = stmt.executeQuery();
/*      */ 
/*  439 */       if (!resultSet.next()) {
/*  440 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  443 */       col = 1;
/*  444 */       int dbVersionNumber = resultSet.getInt(col++);
/*  445 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  446 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  452 */       throw handleSQLException(getPK(), "select VERSION_NUM from SECURITY_RANGE where SECURITY_RANGE_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  456 */       closeStatement(stmt);
/*  457 */       closeResultSet(resultSet);
/*      */ 
/*  459 */       if (timer != null)
/*  460 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllSecurityRangesELO getAllSecurityRanges()
/*      */   {
/*  505 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  506 */     PreparedStatement stmt = null;
/*  507 */     ResultSet resultSet = null;
/*  508 */     AllSecurityRangesELO results = new AllSecurityRangesELO();
/*      */     try
/*      */     {
/*  511 */       stmt = getConnection().prepareStatement(SQL_ALL_SECURITY_RANGES);
/*  512 */       int col = 1;
/*  513 */       resultSet = stmt.executeQuery();
/*  514 */       while (resultSet.next())
/*      */       {
/*  516 */         col = 2;
/*      */ 
/*  519 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  522 */         String textDimension = resultSet.getString(col++);
/*  523 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/*  526 */         SecurityRangePK pkSecurityRange = new SecurityRangePK(resultSet.getInt(col++));
/*      */ 
/*  529 */         String textSecurityRange = resultSet.getString(col++);
/*      */ 
/*  532 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  536 */         String textModelDimensionRel = "";
/*      */ 
/*  538 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  541 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  545 */         SecurityRangeCK ckSecurityRange = new SecurityRangeCK(pkDimension, pkSecurityRange);
/*      */ 
/*  551 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/*  558 */         SecurityRangeRefImpl erSecurityRange = new SecurityRangeRefImpl(ckSecurityRange, textSecurityRange);
/*      */ 
/*  564 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
/*      */ 
/*  570 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  575 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  578 */         results.add(erSecurityRange, erDimension, erModelDimensionRel, erModel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  589 */       throw handleSQLException(SQL_ALL_SECURITY_RANGES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  593 */       closeResultSet(resultSet);
/*  594 */       closeStatement(stmt);
/*  595 */       closeConnection();
/*      */     }
/*      */ 
/*  598 */     if (timer != null) {
/*  599 */       timer.logDebug("getAllSecurityRanges", " items=" + results.size());
/*      */     }
/*      */ 
/*  603 */     return results;
/*      */   }
/*      */ 
/*      */   public AllSecurityRangesForModelELO getAllSecurityRangesForModel(int param1)
/*      */   {
/*  651 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  652 */     PreparedStatement stmt = null;
/*  653 */     ResultSet resultSet = null;
/*  654 */     AllSecurityRangesForModelELO results = new AllSecurityRangesForModelELO();
/*      */     try
/*      */     {
/*  657 */       stmt = getConnection().prepareStatement(SQL_ALL_SECURITY_RANGES_FOR_MODEL);
/*  658 */       int col = 1;
/*  659 */       stmt.setInt(col++, param1);
/*  660 */       resultSet = stmt.executeQuery();
/*  661 */       while (resultSet.next())
/*      */       {
/*  663 */         col = 2;
/*      */ 
/*  666 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  669 */         String textDimension = resultSet.getString(col++);
/*  670 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/*  673 */         SecurityRangePK pkSecurityRange = new SecurityRangePK(resultSet.getInt(col++));
/*      */ 
/*  676 */         String textSecurityRange = resultSet.getString(col++);
/*      */ 
/*  679 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  682 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  684 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  688 */         String textModelDimensionRel = "";
/*      */ 
/*  692 */         SecurityRangeCK ckSecurityRange = new SecurityRangeCK(pkDimension, pkSecurityRange);
/*      */ 
/*  698 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/*  705 */         SecurityRangeRefImpl erSecurityRange = new SecurityRangeRefImpl(ckSecurityRange, textSecurityRange);
/*      */ 
/*  711 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  717 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
/*      */ 
/*  722 */         int col1 = resultSet.getInt(col++);
/*  723 */         int col2 = resultSet.getInt(col++);
/*  724 */         String col3 = resultSet.getString(col++);
/*      */ 
/*  727 */         results.add(erSecurityRange, erDimension, erModel, erModelDimensionRel, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  740 */       throw handleSQLException(SQL_ALL_SECURITY_RANGES_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  744 */       closeResultSet(resultSet);
/*  745 */       closeStatement(stmt);
/*  746 */       closeConnection();
/*      */     }
/*      */ 
/*  749 */     if (timer != null) {
/*  750 */       timer.logDebug("getAllSecurityRangesForModel", " ModelId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  755 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  772 */     if (items == null) {
/*  773 */       return false;
/*      */     }
/*  775 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  776 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  778 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  782 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  783 */       while (iter3.hasNext())
/*      */       {
/*  785 */         this.mDetails = ((SecurityRangeEVO)iter3.next());
/*  786 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  788 */         somethingChanged = true;
/*      */ 
/*  791 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  795 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  796 */       while (iter2.hasNext())
/*      */       {
/*  798 */         this.mDetails = ((SecurityRangeEVO)iter2.next());
/*      */ 
/*  801 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  803 */         somethingChanged = true;
/*      */ 
/*  806 */         if (deleteStmt == null) {
/*  807 */           deleteStmt = getConnection().prepareStatement("delete from SECURITY_RANGE where    SECURITY_RANGE_ID = ? ");
/*      */         }
/*      */ 
/*  810 */         int col = 1;
/*  811 */         deleteStmt.setInt(col++, this.mDetails.getSecurityRangeId());
/*      */ 
/*  813 */         if (this._log.isDebugEnabled()) {
/*  814 */           this._log.debug("update", "SecurityRange deleting SecurityRangeId=" + this.mDetails.getSecurityRangeId());
/*      */         }
/*      */ 
/*  819 */         deleteStmt.addBatch();
/*      */ 
/*  822 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  827 */       if (deleteStmt != null)
/*      */       {
/*  829 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  831 */         deleteStmt.executeBatch();
/*      */ 
/*  833 */         if (timer2 != null) {
/*  834 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  838 */       Iterator iter1 = items.values().iterator();
/*  839 */       while (iter1.hasNext())
/*      */       {
/*  841 */         this.mDetails = ((SecurityRangeEVO)iter1.next());
/*      */ 
/*  843 */         if (this.mDetails.insertPending())
/*      */         {
/*  845 */           somethingChanged = true;
/*  846 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  849 */         if (this.mDetails.isModified())
/*      */         {
/*  851 */           somethingChanged = true;
/*  852 */           doStore(); continue;
/*      */         }
/*      */ 
/*  856 */         if ((this.mDetails.deletePending()) || 
/*  862 */           (!getSecurityRangeRowDAO().update(this.mDetails.getRangeRowsMap()))) continue;
/*  863 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  875 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  879 */       throw handleSQLException("delete from SECURITY_RANGE where    SECURITY_RANGE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  883 */       if (deleteStmt != null)
/*      */       {
/*  885 */         closeStatement(deleteStmt);
/*  886 */         closeConnection();
/*      */       }
/*      */ 
/*  889 */       this.mDetails = null;
/*      */ 
/*  891 */       if ((somethingChanged) && 
/*  892 */         (timer != null))
/*  893 */         timer.logDebug("update", "collection"); 
/*  893 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(SecurityRangePK pk)
/*      */   {
/*  919 */     Set emptyStrings = Collections.emptySet();
/*  920 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(SecurityRangePK pk, Set<String> exclusionTables)
/*      */   {
/*  926 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  928 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  930 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  932 */       PreparedStatement stmt = null;
/*      */ 
/*  934 */       int resultCount = 0;
/*  935 */       String s = null;
/*      */       try
/*      */       {
/*  938 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  940 */         if (this._log.isDebugEnabled()) {
/*  941 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  943 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  946 */         int col = 1;
/*  947 */         stmt.setInt(col++, pk.getSecurityRangeId());
/*      */ 
/*  950 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  954 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  958 */         closeStatement(stmt);
/*  959 */         closeConnection();
/*      */ 
/*  961 */         if (timer != null) {
/*  962 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  966 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  968 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  970 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  972 */       PreparedStatement stmt = null;
/*      */ 
/*  974 */       int resultCount = 0;
/*  975 */       String s = null;
/*      */       try
/*      */       {
/*  978 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  980 */         if (this._log.isDebugEnabled()) {
/*  981 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  983 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  986 */         int col = 1;
/*  987 */         stmt.setInt(col++, pk.getSecurityRangeId());
/*      */ 
/*  990 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  994 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  998 */         closeStatement(stmt);
/*  999 */         closeConnection();
/*      */ 
/* 1001 */         if (timer != null)
/* 1002 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(DimensionPK entityPK, DimensionEVO owningEVO, String dependants)
/*      */   {
/* 1022 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1024 */     PreparedStatement stmt = null;
/* 1025 */     ResultSet resultSet = null;
/*      */ 
/* 1027 */     int itemCount = 0;
/*      */ 
/* 1029 */     Collection theseItems = new ArrayList();
/* 1030 */     owningEVO.setSecurityRanges(theseItems);
/* 1031 */     owningEVO.setSecurityRangesAllItemsLoaded(true);
/*      */     try
/*      */     {
/* 1035 */       stmt = getConnection().prepareStatement("select SECURITY_RANGE.SECURITY_RANGE_ID,SECURITY_RANGE.DIMENSION_ID,SECURITY_RANGE.VIS_ID,SECURITY_RANGE.DESCRIPTION,SECURITY_RANGE.VERSION_NUM,SECURITY_RANGE.UPDATED_BY_USER_ID,SECURITY_RANGE.UPDATED_TIME,SECURITY_RANGE.CREATED_TIME from SECURITY_RANGE where 1=1 and SECURITY_RANGE.DIMENSION_ID = ? order by  SECURITY_RANGE.SECURITY_RANGE_ID");
/*      */ 
/* 1037 */       int col = 1;
/* 1038 */       stmt.setInt(col++, entityPK.getDimensionId());
/*      */ 
/* 1040 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1043 */       while (resultSet.next())
/*      */       {
/* 1045 */         itemCount++;
/* 1046 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1048 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/* 1051 */       if (timer != null) {
/* 1052 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/* 1055 */       if ((itemCount > 0) && (dependants.indexOf("<8>") > -1))
/*      */       {
/* 1057 */         getSecurityRangeRowDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/* 1061 */       throw handleSQLException("select SECURITY_RANGE.SECURITY_RANGE_ID,SECURITY_RANGE.DIMENSION_ID,SECURITY_RANGE.VIS_ID,SECURITY_RANGE.DESCRIPTION,SECURITY_RANGE.VERSION_NUM,SECURITY_RANGE.UPDATED_BY_USER_ID,SECURITY_RANGE.UPDATED_TIME,SECURITY_RANGE.CREATED_TIME from SECURITY_RANGE where 1=1 and SECURITY_RANGE.DIMENSION_ID = ? order by  SECURITY_RANGE.SECURITY_RANGE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1065 */       closeResultSet(resultSet);
/* 1066 */       closeStatement(stmt);
/* 1067 */       closeConnection();
/*      */ 
/* 1069 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectDimensionId, String dependants, Collection currentList)
/*      */   {
/* 1094 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1095 */     PreparedStatement stmt = null;
/* 1096 */     ResultSet resultSet = null;
/*      */ 
/* 1098 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/* 1102 */       stmt = getConnection().prepareStatement("select SECURITY_RANGE.SECURITY_RANGE_ID,SECURITY_RANGE.DIMENSION_ID,SECURITY_RANGE.VIS_ID,SECURITY_RANGE.DESCRIPTION,SECURITY_RANGE.VERSION_NUM,SECURITY_RANGE.UPDATED_BY_USER_ID,SECURITY_RANGE.UPDATED_TIME,SECURITY_RANGE.CREATED_TIME from SECURITY_RANGE where    DIMENSION_ID = ? ");
/*      */ 
/* 1104 */       int col = 1;
/* 1105 */       stmt.setInt(col++, selectDimensionId);
/*      */ 
/* 1107 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1109 */       while (resultSet.next())
/*      */       {
/* 1111 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1114 */         getDependants(this.mDetails, dependants);
/*      */ 
/* 1117 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1120 */       if (currentList != null)
/*      */       {
/* 1123 */         ListIterator iter = items.listIterator();
/* 1124 */         SecurityRangeEVO currentEVO = null;
/* 1125 */         SecurityRangeEVO newEVO = null;
/* 1126 */         while (iter.hasNext())
/*      */         {
/* 1128 */           newEVO = (SecurityRangeEVO)iter.next();
/* 1129 */           Iterator iter2 = currentList.iterator();
/* 1130 */           while (iter2.hasNext())
/*      */           {
/* 1132 */             currentEVO = (SecurityRangeEVO)iter2.next();
/* 1133 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1135 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1141 */         Iterator iter2 = currentList.iterator();
/* 1142 */         while (iter2.hasNext())
/*      */         {
/* 1144 */           currentEVO = (SecurityRangeEVO)iter2.next();
/* 1145 */           if (currentEVO.insertPending()) {
/* 1146 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1150 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1154 */       throw handleSQLException("select SECURITY_RANGE.SECURITY_RANGE_ID,SECURITY_RANGE.DIMENSION_ID,SECURITY_RANGE.VIS_ID,SECURITY_RANGE.DESCRIPTION,SECURITY_RANGE.VERSION_NUM,SECURITY_RANGE.UPDATED_BY_USER_ID,SECURITY_RANGE.UPDATED_TIME,SECURITY_RANGE.CREATED_TIME from SECURITY_RANGE where    DIMENSION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1158 */       closeResultSet(resultSet);
/* 1159 */       closeStatement(stmt);
/* 1160 */       closeConnection();
/*      */ 
/* 1162 */       if (timer != null) {
/* 1163 */         timer.logDebug("getAll", " DimensionId=" + selectDimensionId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1168 */     return items;
/*      */   }
/*      */ 
/*      */   public SecurityRangeEVO getDetails(DimensionCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1185 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1188 */     if (this.mDetails == null) {
/* 1189 */       doLoad(((SecurityRangeCK)paramCK).getSecurityRangePK());
/*      */     }
/* 1191 */     else if (!this.mDetails.getPK().equals(((SecurityRangeCK)paramCK).getSecurityRangePK())) {
/* 1192 */       doLoad(((SecurityRangeCK)paramCK).getSecurityRangePK());
/*      */     }
/*      */ 
/* 1195 */     if ((dependants.indexOf("<8>") > -1) && (!this.mDetails.isRangeRowsAllItemsLoaded()))
/*      */     {
/* 1200 */       this.mDetails.setRangeRows(getSecurityRangeRowDAO().getAll(this.mDetails.getSecurityRangeId(), dependants, this.mDetails.getRangeRows()));
/*      */ 
/* 1207 */       this.mDetails.setRangeRowsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1210 */     if ((paramCK instanceof SecurityRangeRowCK))
/*      */     {
/* 1212 */       if (this.mDetails.getRangeRows() == null) {
/* 1213 */         this.mDetails.loadRangeRowsItem(getSecurityRangeRowDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1216 */         SecurityRangeRowPK pk = ((SecurityRangeRowCK)paramCK).getSecurityRangeRowPK();
/* 1217 */         SecurityRangeRowEVO evo = this.mDetails.getRangeRowsItem(pk);
/* 1218 */         if (evo == null) {
/* 1219 */           this.mDetails.loadRangeRowsItem(getSecurityRangeRowDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1224 */     SecurityRangeEVO details = new SecurityRangeEVO();
/* 1225 */     details = this.mDetails.deepClone();
/*      */ 
/* 1227 */     if (timer != null) {
/* 1228 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1230 */     return details;
/*      */   }
/*      */ 
/*      */   public SecurityRangeEVO getDetails(DimensionCK paramCK, SecurityRangeEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1236 */     SecurityRangeEVO savedEVO = this.mDetails;
/* 1237 */     this.mDetails = paramEVO;
/* 1238 */     SecurityRangeEVO newEVO = getDetails(paramCK, dependants);
/* 1239 */     this.mDetails = savedEVO;
/* 1240 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public SecurityRangeEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1246 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1250 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1253 */     SecurityRangeEVO details = this.mDetails.deepClone();
/*      */ 
/* 1255 */     if (timer != null) {
/* 1256 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1258 */     return details;
/*      */   }
/*      */ 
/*      */   protected SecurityRangeRowDAO getSecurityRangeRowDAO()
/*      */   {
/* 1267 */     if (this.mSecurityRangeRowDAO == null)
/*      */     {
/* 1269 */       if (this.mDataSource != null)
/* 1270 */         this.mSecurityRangeRowDAO = new SecurityRangeRowDAO(this.mDataSource);
/*      */       else {
/* 1272 */         this.mSecurityRangeRowDAO = new SecurityRangeRowDAO(getConnection());
/*      */       }
/*      */     }
/* 1275 */     return this.mSecurityRangeRowDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1280 */     return "SecurityRange";
/*      */   }
/*      */ 
/*      */   public SecurityRangeRefImpl getRef(SecurityRangePK paramSecurityRangePK)
/*      */   {
/* 1285 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1286 */     PreparedStatement stmt = null;
/* 1287 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1290 */       stmt = getConnection().prepareStatement("select 0,DIMENSION.DIMENSION_ID,SECURITY_RANGE.VIS_ID from SECURITY_RANGE,DIMENSION where 1=1 and SECURITY_RANGE.SECURITY_RANGE_ID = ? and SECURITY_RANGE.DIMENSION_ID = DIMENSION.DIMENSION_ID");
/* 1291 */       int col = 1;
/* 1292 */       stmt.setInt(col++, paramSecurityRangePK.getSecurityRangeId());
/*      */ 
/* 1294 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1296 */       if (!resultSet.next()) {
/* 1297 */         throw new RuntimeException(getEntityName() + " getRef " + paramSecurityRangePK + " not found");
/*      */       }
/* 1299 */       col = 2;
/* 1300 */       DimensionPK newDimensionPK = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/* 1304 */       String textSecurityRange = resultSet.getString(col++);
/* 1305 */       SecurityRangeCK ckSecurityRange = new SecurityRangeCK(newDimensionPK, paramSecurityRangePK);
/*      */ 
/* 1310 */       SecurityRangeRefImpl localSecurityRangeRefImpl = new SecurityRangeRefImpl(ckSecurityRange, textSecurityRange);
/*      */       return localSecurityRangeRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1315 */       throw handleSQLException(paramSecurityRangePK, "select 0,DIMENSION.DIMENSION_ID,SECURITY_RANGE.VIS_ID from SECURITY_RANGE,DIMENSION where 1=1 and SECURITY_RANGE.SECURITY_RANGE_ID = ? and SECURITY_RANGE.DIMENSION_ID = DIMENSION.DIMENSION_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1319 */       closeResultSet(resultSet);
/* 1320 */       closeStatement(stmt);
/* 1321 */       closeConnection();
/*      */ 
/* 1323 */       if (timer != null)
/* 1324 */         timer.logDebug("getRef", paramSecurityRangePK); 
/* 1324 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1336 */     if (c == null)
/* 1337 */       return;
/* 1338 */     Iterator iter = c.iterator();
/* 1339 */     while (iter.hasNext())
/*      */     {
/* 1341 */       SecurityRangeEVO evo = (SecurityRangeEVO)iter.next();
/* 1342 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(SecurityRangeEVO evo, String dependants)
/*      */   {
/* 1356 */     if (evo.insertPending()) {
/* 1357 */       return;
/*      */     }
/* 1359 */     if (evo.getSecurityRangeId() < 1) {
/* 1360 */       return;
/*      */     }
/*      */ 
/* 1364 */     if (dependants.indexOf("<8>") > -1)
/*      */     {
/* 1367 */       if (!evo.isRangeRowsAllItemsLoaded())
/*      */       {
/* 1369 */         evo.setRangeRows(getSecurityRangeRowDAO().getAll(evo.getSecurityRangeId(), dependants, evo.getRangeRows()));
/*      */ 
/* 1376 */         evo.setRangeRowsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.dimension.SecurityRangeDAO
 * JD-Core Version:    0.6.0
 */