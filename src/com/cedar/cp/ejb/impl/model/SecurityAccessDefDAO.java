/*      */ package com.cedar.cp.ejb.impl.model;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.AllSecurityAccessDefsELO;
/*      */ import com.cedar.cp.dto.model.AllSecurityAccessDefsForModelELO;
/*      */ import com.cedar.cp.dto.model.CellCalcExpressionDetails;
/*      */ import com.cedar.cp.dto.model.CellCalcRangeDetails;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.SecurityAccRngRelCK;
/*      */ import com.cedar.cp.dto.model.SecurityAccRngRelPK;
/*      */ import com.cedar.cp.dto.model.SecurityAccessDefCK;
/*      */ import com.cedar.cp.dto.model.SecurityAccessDefPK;
/*      */ import com.cedar.cp.dto.model.SecurityAccessDefRefImpl;
/*      */ import com.cedar.cp.dto.model.SecurityRangeRowDetails;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.SqlBuilder;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.io.PrintStream;
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
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class SecurityAccessDefDAO extends AbstractDAO
/*      */ {
/*   33 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID,SECURITY_ACCESS_DEF.MODEL_ID,SECURITY_ACCESS_DEF.VIS_ID,SECURITY_ACCESS_DEF.DESCRIPTION,SECURITY_ACCESS_DEF.ACCESS_MODE,SECURITY_ACCESS_DEF.EXPRESSION,SECURITY_ACCESS_DEF.VERSION_NUM,SECURITY_ACCESS_DEF.UPDATED_BY_USER_ID,SECURITY_ACCESS_DEF.UPDATED_TIME,SECURITY_ACCESS_DEF.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from SECURITY_ACCESS_DEF where    SECURITY_ACCESS_DEF_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into SECURITY_ACCESS_DEF ( SECURITY_ACCESS_DEF_ID,MODEL_ID,VIS_ID,DESCRIPTION,ACCESS_MODE,EXPRESSION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_SECURITYACCESSDEFNAME = "select count(*) from SECURITY_ACCESS_DEF where    VIS_ID = ? and not(    SECURITY_ACCESS_DEF_ID = ? )";
/*      */   protected static final String SQL_STORE = "update SECURITY_ACCESS_DEF set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,ACCESS_MODE = ?,EXPRESSION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_ACCESS_DEF_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from SECURITY_ACCESS_DEF where SECURITY_ACCESS_DEF_ID = ?";
/*  466 */   protected static String SQL_ALL_SECURITY_ACCESS_DEFS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID      ,SECURITY_ACCESS_DEF.VIS_ID      ,SECURITY_ACCESS_DEF.DESCRIPTION from SECURITY_ACCESS_DEF    ,MODEL where 1=1   and SECURITY_ACCESS_DEF.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID, SECURITY_ACCESS_DEF.VIS_ID";
/*      */ 
/*  572 */   protected static String SQL_ALL_SECURITY_ACCESS_DEFS_FOR_MODEL = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID      ,SECURITY_ACCESS_DEF.VIS_ID      ,SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID      ,SECURITY_ACCESS_DEF.DESCRIPTION from SECURITY_ACCESS_DEF    ,MODEL where 1=1   and SECURITY_ACCESS_DEF.MODEL_ID = MODEL.MODEL_ID  and  SECURITY_ACCESS_DEF.MODEL_ID = ? order by SECURITY_ACCESS_DEF.VIS_ID";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from SECURITY_ACCESS_DEF where    SECURITY_ACCESS_DEF_ID = ? ";
/*  821 */   private static String[][] SQL_DELETE_CHILDREN = { { "SECURITY_ACC_RNG_REL", "delete from SECURITY_ACC_RNG_REL where     SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID = ? " } };
/*      */ 
/*  830 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  834 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from SECURITY_ACCESS_DEF where 1=1 and SECURITY_ACCESS_DEF.MODEL_ID = ? order by  SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID";
/*      */   protected static final String SQL_GET_ALL = " from SECURITY_ACCESS_DEF where    MODEL_ID = ? ";
/* 1308 */   protected String SQL_CELL_CALC_SELECT = "SELECT \tCELL_CALC.CELL_CALC_ID \t,SECURITY_ACCESS_DEF.EXPRESSION \t,CURSOR ( \t\tSELECT \t\t\tREL.SECURITY_RANGE_ID \t\t\t,HIER.HIERARCHY_ID \t\t\t,SEC_RANGE.VIS_ID \t\t\t,MOD_REL.DIMENSION_SEQ_NUM \t\t\t,CURSOR( \t\t\t\tSELECT  \t\t\t\t\tSEC_RNG_ROW.FROM_ID \t\t\t\t\t, SEC_RNG_ROW.TO_ID \t\t\t\tFROM \t\t\t\t\tSECURITY_RANGE_ROW SEC_RNG_ROW \t\t\t\tWHERE \t\t\t\t\tSEC_RNG_ROW.SECURITY_RANGE_ID = SEC_RANGE.SECURITY_RANGE_ID \t\t\t\t) RANGE_ROWS \t\tFROM \t\t\tSECURITY_ACC_RNG_REL REL \t\t\t,SECURITY_RANGE SEC_RANGE \t\t\t,HIERARCHY HIER \t\t\t,MODEL_DIMENSION_REL MOD_REL \t\tWHERE \t\t\tREL.SECURITY_ACCESS_DEF_ID = SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID \t\t\tAND REL.SECURITY_RANGE_ID = SEC_RANGE.SECURITY_RANGE_ID \t\t\tAND \t\t\t( ";
/*      */ 
/* 1339 */   protected String SQL_CELL_CALC_WHERE = "\t\t\t) \t\t\tAND HIER.DIMENSION_ID = SEC_RANGE.DIMENSION_ID \t\t\tAND MOD_REL.MODEL_ID = MODEL.MODEL_ID \t\t\tAND MOD_REL.DIMENSION_ID = SEC_RANGE.DIMENSION_ID \t) RANGES  FROM \tMODEL \t,SECURITY_ACCESS_DEF \t,CELL_CALC WHERE \tMODEL.MODEL_ID = ? \tAND MODEL.MODEL_ID = SECURITY_ACCESS_DEF.MODEL_ID \tAND CELL_CALC.MODEL_ID = MODEL.MODEL_ID \tAND CELL_CALC.ACCESS_DEFINITION_ID = SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID ";
/*      */ 
/* 1452 */   protected final String SQL_CELL_CALC_DEFS = "select MODEL.MODEL_ID,MODEL.VIS_ID,SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID ,SECURITY_ACCESS_DEF.VIS_ID ,SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID ,SECURITY_ACCESS_DEF.DESCRIPTION from security_access_def ,model where security_access_def.security_access_def_id not in ( select SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID from SECURITY_RANGE ,SECURITY_ACC_RNG_REL ,model_dimension_rel mdr where mdr.model_id = ? and mdr.DIMENSION_SEQ_NUM = 0 and SECURITY_RANGE.DIMENSION_ID <> mdr.dimension_id and SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID = SECURITY_RANGE.SECURITY_RANGE_ID ) and SECURITY_ACCESS_DEF.MODEL_ID = MODEL.MODEL_ID";
/*      */   protected SecurityAccRngRelDAO mSecurityAccRngRelDAO;
/*      */   protected SecurityAccessDefEVO mDetails;
/*      */ 
/*      */   public SecurityAccessDefDAO(Connection connection)
/*      */   {
/*   40 */     super(connection);
/*      */   }
/*      */ 
/*      */   public SecurityAccessDefDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public SecurityAccessDefDAO(DataSource ds)
/*      */   {
/*   56 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected SecurityAccessDefPK getPK()
/*      */   {
/*   64 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(SecurityAccessDefEVO details)
/*      */   {
/*   73 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private SecurityAccessDefEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   94 */     int col = 1;
/*   95 */     SecurityAccessDefEVO evo = new SecurityAccessDefEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  106 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  107 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  108 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  109 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(SecurityAccessDefEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  114 */     int col = startCol_;
/*  115 */     stmt_.setInt(col++, evo_.getSecurityAccessDefId());
/*  116 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(SecurityAccessDefEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  121 */     int col = startCol_;
/*  122 */     stmt_.setInt(col++, evo_.getModelId());
/*  123 */     stmt_.setString(col++, evo_.getVisId());
/*  124 */     stmt_.setString(col++, evo_.getDescription());
/*  125 */     stmt_.setInt(col++, evo_.getAccessMode());
/*  126 */     stmt_.setString(col++, evo_.getExpression());
/*  127 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  128 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  129 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  130 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  131 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(SecurityAccessDefPK pk)
/*      */     throws ValidationException
/*      */   {
/*  147 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  149 */     PreparedStatement stmt = null;
/*  150 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  154 */       stmt = getConnection().prepareStatement("select SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID,SECURITY_ACCESS_DEF.MODEL_ID,SECURITY_ACCESS_DEF.VIS_ID,SECURITY_ACCESS_DEF.DESCRIPTION,SECURITY_ACCESS_DEF.ACCESS_MODE,SECURITY_ACCESS_DEF.EXPRESSION,SECURITY_ACCESS_DEF.VERSION_NUM,SECURITY_ACCESS_DEF.UPDATED_BY_USER_ID,SECURITY_ACCESS_DEF.UPDATED_TIME,SECURITY_ACCESS_DEF.CREATED_TIME from SECURITY_ACCESS_DEF where    SECURITY_ACCESS_DEF_ID = ? ");
/*      */ 
/*  157 */       int col = 1;
/*  158 */       stmt.setInt(col++, pk.getSecurityAccessDefId());
/*      */ 
/*  160 */       resultSet = stmt.executeQuery();
/*      */ 
/*  162 */       if (!resultSet.next()) {
/*  163 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  166 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  167 */       if (this.mDetails.isModified())
/*  168 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  172 */       throw handleSQLException(pk, "select SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID,SECURITY_ACCESS_DEF.MODEL_ID,SECURITY_ACCESS_DEF.VIS_ID,SECURITY_ACCESS_DEF.DESCRIPTION,SECURITY_ACCESS_DEF.ACCESS_MODE,SECURITY_ACCESS_DEF.EXPRESSION,SECURITY_ACCESS_DEF.VERSION_NUM,SECURITY_ACCESS_DEF.UPDATED_BY_USER_ID,SECURITY_ACCESS_DEF.UPDATED_TIME,SECURITY_ACCESS_DEF.CREATED_TIME from SECURITY_ACCESS_DEF where    SECURITY_ACCESS_DEF_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  176 */       closeResultSet(resultSet);
/*  177 */       closeStatement(stmt);
/*  178 */       closeConnection();
/*      */ 
/*  180 */       if (timer != null)
/*  181 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  218 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  219 */     this.mDetails.postCreateInit();
/*      */ 
/*  221 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  226 */       duplicateValueCheckSecurityAccessDefName();
/*      */ 
/*  228 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  229 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  230 */       stmt = getConnection().prepareStatement("insert into SECURITY_ACCESS_DEF ( SECURITY_ACCESS_DEF_ID,MODEL_ID,VIS_ID,DESCRIPTION,ACCESS_MODE,EXPRESSION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  233 */       int col = 1;
/*  234 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  235 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  238 */       int resultCount = stmt.executeUpdate();
/*  239 */       if (resultCount != 1)
/*      */       {
/*  241 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  244 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  248 */       throw handleSQLException(this.mDetails.getPK(), "insert into SECURITY_ACCESS_DEF ( SECURITY_ACCESS_DEF_ID,MODEL_ID,VIS_ID,DESCRIPTION,ACCESS_MODE,EXPRESSION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  252 */       closeStatement(stmt);
/*  253 */       closeConnection();
/*      */ 
/*  255 */       if (timer != null) {
/*  256 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  262 */       getSecurityAccRngRelDAO().update(this.mDetails.getSecurityRangesForAccessDefMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  268 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckSecurityAccessDefName()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  283 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  284 */     PreparedStatement stmt = null;
/*  285 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  289 */       stmt = getConnection().prepareStatement("select count(*) from SECURITY_ACCESS_DEF where    VIS_ID = ? and not(    SECURITY_ACCESS_DEF_ID = ? )");
/*      */ 
/*  292 */       int col = 1;
/*  293 */       stmt.setString(col++, this.mDetails.getVisId());
/*  294 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  297 */       resultSet = stmt.executeQuery();
/*      */ 
/*  299 */       if (!resultSet.next()) {
/*  300 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  304 */       col = 1;
/*  305 */       int count = resultSet.getInt(col++);
/*  306 */       if (count > 0) {
/*  307 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " SecurityAccessDefName");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  313 */       throw handleSQLException(getPK(), "select count(*) from SECURITY_ACCESS_DEF where    VIS_ID = ? and not(    SECURITY_ACCESS_DEF_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  317 */       closeResultSet(resultSet);
/*  318 */       closeStatement(stmt);
/*  319 */       closeConnection();
/*      */ 
/*  321 */       if (timer != null)
/*  322 */         timer.logDebug("duplicateValueCheckSecurityAccessDefName", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  350 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  354 */     PreparedStatement stmt = null;
/*      */ 
/*  356 */     boolean mainChanged = this.mDetails.isModified();
/*  357 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  361 */       if (mainChanged) {
/*  362 */         duplicateValueCheckSecurityAccessDefName();
/*      */       }
/*  364 */       if (getSecurityAccRngRelDAO().update(this.mDetails.getSecurityRangesForAccessDefMap())) {
/*  365 */         dependantChanged = true;
/*      */       }
/*  367 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  370 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  373 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  374 */         stmt = getConnection().prepareStatement("update SECURITY_ACCESS_DEF set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,ACCESS_MODE = ?,EXPRESSION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_ACCESS_DEF_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  377 */         int col = 1;
/*  378 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  379 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  381 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  384 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  386 */         if (resultCount == 0) {
/*  387 */           checkVersionNum();
/*      */         }
/*  389 */         if (resultCount != 1) {
/*  390 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  393 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  402 */       throw handleSQLException(getPK(), "update SECURITY_ACCESS_DEF set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,ACCESS_MODE = ?,EXPRESSION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SECURITY_ACCESS_DEF_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  406 */       closeStatement(stmt);
/*  407 */       closeConnection();
/*      */ 
/*  409 */       if ((timer != null) && (
/*  410 */         (mainChanged) || (dependantChanged)))
/*  411 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  423 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  424 */     PreparedStatement stmt = null;
/*  425 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  429 */       stmt = getConnection().prepareStatement("select VERSION_NUM from SECURITY_ACCESS_DEF where SECURITY_ACCESS_DEF_ID = ?");
/*      */ 
/*  432 */       int col = 1;
/*  433 */       stmt.setInt(col++, this.mDetails.getSecurityAccessDefId());
/*      */ 
/*  436 */       resultSet = stmt.executeQuery();
/*      */ 
/*  438 */       if (!resultSet.next()) {
/*  439 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  442 */       col = 1;
/*  443 */       int dbVersionNumber = resultSet.getInt(col++);
/*  444 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  445 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  451 */       throw handleSQLException(getPK(), "select VERSION_NUM from SECURITY_ACCESS_DEF where SECURITY_ACCESS_DEF_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  455 */       closeStatement(stmt);
/*  456 */       closeResultSet(resultSet);
/*      */ 
/*  458 */       if (timer != null)
/*  459 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllSecurityAccessDefsELO getAllSecurityAccessDefs()
/*      */   {
/*  495 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  496 */     PreparedStatement stmt = null;
/*  497 */     ResultSet resultSet = null;
/*  498 */     AllSecurityAccessDefsELO results = new AllSecurityAccessDefsELO();
/*      */     try
/*      */     {
/*  501 */       stmt = getConnection().prepareStatement(SQL_ALL_SECURITY_ACCESS_DEFS);
/*  502 */       int col = 1;
/*  503 */       resultSet = stmt.executeQuery();
/*  504 */       while (resultSet.next())
/*      */       {
/*  506 */         col = 2;
/*      */ 
/*  509 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  512 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  515 */         SecurityAccessDefPK pkSecurityAccessDef = new SecurityAccessDefPK(resultSet.getInt(col++));
/*      */ 
/*  518 */         String textSecurityAccessDef = resultSet.getString(col++);
/*      */ 
/*  523 */         SecurityAccessDefCK ckSecurityAccessDef = new SecurityAccessDefCK(pkModel, pkSecurityAccessDef);
/*      */ 
/*  529 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  535 */         SecurityAccessDefRefImpl erSecurityAccessDef = new SecurityAccessDefRefImpl(ckSecurityAccessDef, textSecurityAccessDef);
/*      */ 
/*  540 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  543 */         results.add(erSecurityAccessDef, erModel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  552 */       throw handleSQLException(SQL_ALL_SECURITY_ACCESS_DEFS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  556 */       closeResultSet(resultSet);
/*  557 */       closeStatement(stmt);
/*  558 */       closeConnection();
/*      */     }
/*      */ 
/*  561 */     if (timer != null) {
/*  562 */       timer.logDebug("getAllSecurityAccessDefs", " items=" + results.size());
/*      */     }
/*      */ 
/*  566 */     return results;
/*      */   }
/*      */ 
/*      */   public AllSecurityAccessDefsForModelELO getAllSecurityAccessDefsForModel(int param1)
/*      */   {
/*  604 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  605 */     PreparedStatement stmt = null;
/*  606 */     ResultSet resultSet = null;
/*  607 */     AllSecurityAccessDefsForModelELO results = new AllSecurityAccessDefsForModelELO();
/*      */     try
/*      */     {
/*  610 */       stmt = getConnection().prepareStatement(SQL_ALL_SECURITY_ACCESS_DEFS_FOR_MODEL);
/*  611 */       int col = 1;
/*  612 */       stmt.setInt(col++, param1);
/*  613 */       resultSet = stmt.executeQuery();
/*  614 */       while (resultSet.next())
/*      */       {
/*  616 */         col = 2;
/*      */ 
/*  619 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  622 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  625 */         SecurityAccessDefPK pkSecurityAccessDef = new SecurityAccessDefPK(resultSet.getInt(col++));
/*      */ 
/*  628 */         String textSecurityAccessDef = resultSet.getString(col++);
/*      */ 
/*  633 */         SecurityAccessDefCK ckSecurityAccessDef = new SecurityAccessDefCK(pkModel, pkSecurityAccessDef);
/*      */ 
/*  639 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  645 */         SecurityAccessDefRefImpl erSecurityAccessDef = new SecurityAccessDefRefImpl(ckSecurityAccessDef, textSecurityAccessDef);
/*      */ 
/*  650 */         int col1 = resultSet.getInt(col++);
/*  651 */         String col2 = resultSet.getString(col++);
/*      */ 
/*  654 */         results.add(erSecurityAccessDef, erModel, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  664 */       throw handleSQLException(SQL_ALL_SECURITY_ACCESS_DEFS_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  668 */       closeResultSet(resultSet);
/*  669 */       closeStatement(stmt);
/*  670 */       closeConnection();
/*      */     }
/*      */ 
/*  673 */     if (timer != null) {
/*  674 */       timer.logDebug("getAllSecurityAccessDefsForModel", " ModelId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  679 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  696 */     if (items == null) {
/*  697 */       return false;
/*      */     }
/*  699 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  700 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  702 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  706 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  707 */       while (iter3.hasNext())
/*      */       {
/*  709 */         this.mDetails = ((SecurityAccessDefEVO)iter3.next());
/*  710 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  712 */         somethingChanged = true;
/*      */ 
/*  715 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  719 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  720 */       while (iter2.hasNext())
/*      */       {
/*  722 */         this.mDetails = ((SecurityAccessDefEVO)iter2.next());
/*      */ 
/*  725 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  727 */         somethingChanged = true;
/*      */ 
/*  730 */         if (deleteStmt == null) {
/*  731 */           deleteStmt = getConnection().prepareStatement("delete from SECURITY_ACCESS_DEF where    SECURITY_ACCESS_DEF_ID = ? ");
/*      */         }
/*      */ 
/*  734 */         int col = 1;
/*  735 */         deleteStmt.setInt(col++, this.mDetails.getSecurityAccessDefId());
/*      */ 
/*  737 */         if (this._log.isDebugEnabled()) {
/*  738 */           this._log.debug("update", "SecurityAccessDef deleting SecurityAccessDefId=" + this.mDetails.getSecurityAccessDefId());
/*      */         }
/*      */ 
/*  743 */         deleteStmt.addBatch();
/*      */ 
/*  746 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  751 */       if (deleteStmt != null)
/*      */       {
/*  753 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  755 */         deleteStmt.executeBatch();
/*      */ 
/*  757 */         if (timer2 != null) {
/*  758 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  762 */       Iterator iter1 = items.values().iterator();
/*  763 */       while (iter1.hasNext())
/*      */       {
/*  765 */         this.mDetails = ((SecurityAccessDefEVO)iter1.next());
/*      */ 
/*  767 */         if (this.mDetails.insertPending())
/*      */         {
/*  769 */           somethingChanged = true;
/*  770 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  773 */         if (this.mDetails.isModified())
/*      */         {
/*  775 */           somethingChanged = true;
/*  776 */           doStore(); continue;
/*      */         }
/*      */ 
/*  780 */         if ((this.mDetails.deletePending()) || 
/*  786 */           (!getSecurityAccRngRelDAO().update(this.mDetails.getSecurityRangesForAccessDefMap()))) continue;
/*  787 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  799 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  803 */       throw handleSQLException("delete from SECURITY_ACCESS_DEF where    SECURITY_ACCESS_DEF_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  807 */       if (deleteStmt != null)
/*      */       {
/*  809 */         closeStatement(deleteStmt);
/*  810 */         closeConnection();
/*      */       }
/*      */ 
/*  813 */       this.mDetails = null;
/*      */ 
/*  815 */       if ((somethingChanged) && 
/*  816 */         (timer != null))
/*  817 */         timer.logDebug("update", "collection"); 
/*  817 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(SecurityAccessDefPK pk)
/*      */   {
/*  843 */     Set emptyStrings = Collections.emptySet();
/*  844 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(SecurityAccessDefPK pk, Set<String> exclusionTables)
/*      */   {
/*  850 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  852 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  854 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  856 */       PreparedStatement stmt = null;
/*      */ 
/*  858 */       int resultCount = 0;
/*  859 */       String s = null;
/*      */       try
/*      */       {
/*  862 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  864 */         if (this._log.isDebugEnabled()) {
/*  865 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  867 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  870 */         int col = 1;
/*  871 */         stmt.setInt(col++, pk.getSecurityAccessDefId());
/*      */ 
/*  874 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  878 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  882 */         closeStatement(stmt);
/*  883 */         closeConnection();
/*      */ 
/*  885 */         if (timer != null) {
/*  886 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  890 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  892 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  894 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  896 */       PreparedStatement stmt = null;
/*      */ 
/*  898 */       int resultCount = 0;
/*  899 */       String s = null;
/*      */       try
/*      */       {
/*  902 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  904 */         if (this._log.isDebugEnabled()) {
/*  905 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  907 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  910 */         int col = 1;
/*  911 */         stmt.setInt(col++, pk.getSecurityAccessDefId());
/*      */ 
/*  914 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  918 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  922 */         closeStatement(stmt);
/*  923 */         closeConnection();
/*      */ 
/*  925 */         if (timer != null)
/*  926 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants)
/*      */   {
/*  946 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  948 */     PreparedStatement stmt = null;
/*  949 */     ResultSet resultSet = null;
/*      */ 
/*  951 */     int itemCount = 0;
/*      */ 
/*  953 */     Collection theseItems = new ArrayList();
/*  954 */     owningEVO.setSecurityAccessDefs(theseItems);
/*  955 */     owningEVO.setSecurityAccessDefsAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  959 */       stmt = getConnection().prepareStatement("select SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID,SECURITY_ACCESS_DEF.MODEL_ID,SECURITY_ACCESS_DEF.VIS_ID,SECURITY_ACCESS_DEF.DESCRIPTION,SECURITY_ACCESS_DEF.ACCESS_MODE,SECURITY_ACCESS_DEF.EXPRESSION,SECURITY_ACCESS_DEF.VERSION_NUM,SECURITY_ACCESS_DEF.UPDATED_BY_USER_ID,SECURITY_ACCESS_DEF.UPDATED_TIME,SECURITY_ACCESS_DEF.CREATED_TIME from SECURITY_ACCESS_DEF where 1=1 and SECURITY_ACCESS_DEF.MODEL_ID = ? order by  SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID");
/*      */ 
/*  961 */       int col = 1;
/*  962 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  964 */       resultSet = stmt.executeQuery();
/*      */ 
/*  967 */       while (resultSet.next())
/*      */       {
/*  969 */         itemCount++;
/*  970 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  972 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  975 */       if (timer != null) {
/*  976 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  979 */       if ((itemCount > 0) && (dependants.indexOf("<19>") > -1))
/*      */       {
/*  981 */         getSecurityAccRngRelDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  985 */       throw handleSQLException("select SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID,SECURITY_ACCESS_DEF.MODEL_ID,SECURITY_ACCESS_DEF.VIS_ID,SECURITY_ACCESS_DEF.DESCRIPTION,SECURITY_ACCESS_DEF.ACCESS_MODE,SECURITY_ACCESS_DEF.EXPRESSION,SECURITY_ACCESS_DEF.VERSION_NUM,SECURITY_ACCESS_DEF.UPDATED_BY_USER_ID,SECURITY_ACCESS_DEF.UPDATED_TIME,SECURITY_ACCESS_DEF.CREATED_TIME from SECURITY_ACCESS_DEF where 1=1 and SECURITY_ACCESS_DEF.MODEL_ID = ? order by  SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  989 */       closeResultSet(resultSet);
/*  990 */       closeStatement(stmt);
/*  991 */       closeConnection();
/*      */ 
/*  993 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectModelId, String dependants, Collection currentList)
/*      */   {
/* 1018 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1019 */     PreparedStatement stmt = null;
/* 1020 */     ResultSet resultSet = null;
/*      */ 
/* 1022 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/* 1026 */       stmt = getConnection().prepareStatement("select SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID,SECURITY_ACCESS_DEF.MODEL_ID,SECURITY_ACCESS_DEF.VIS_ID,SECURITY_ACCESS_DEF.DESCRIPTION,SECURITY_ACCESS_DEF.ACCESS_MODE,SECURITY_ACCESS_DEF.EXPRESSION,SECURITY_ACCESS_DEF.VERSION_NUM,SECURITY_ACCESS_DEF.UPDATED_BY_USER_ID,SECURITY_ACCESS_DEF.UPDATED_TIME,SECURITY_ACCESS_DEF.CREATED_TIME from SECURITY_ACCESS_DEF where    MODEL_ID = ? ");
/*      */ 
/* 1028 */       int col = 1;
/* 1029 */       stmt.setInt(col++, selectModelId);
/*      */ 
/* 1031 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1033 */       while (resultSet.next())
/*      */       {
/* 1035 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1038 */         getDependants(this.mDetails, dependants);
/*      */ 
/* 1041 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1044 */       if (currentList != null)
/*      */       {
/* 1047 */         ListIterator iter = items.listIterator();
/* 1048 */         SecurityAccessDefEVO currentEVO = null;
/* 1049 */         SecurityAccessDefEVO newEVO = null;
/* 1050 */         while (iter.hasNext())
/*      */         {
/* 1052 */           newEVO = (SecurityAccessDefEVO)iter.next();
/* 1053 */           Iterator iter2 = currentList.iterator();
/* 1054 */           while (iter2.hasNext())
/*      */           {
/* 1056 */             currentEVO = (SecurityAccessDefEVO)iter2.next();
/* 1057 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1059 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1065 */         Iterator iter2 = currentList.iterator();
/* 1066 */         while (iter2.hasNext())
/*      */         {
/* 1068 */           currentEVO = (SecurityAccessDefEVO)iter2.next();
/* 1069 */           if (currentEVO.insertPending()) {
/* 1070 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1074 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1078 */       throw handleSQLException("select SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID,SECURITY_ACCESS_DEF.MODEL_ID,SECURITY_ACCESS_DEF.VIS_ID,SECURITY_ACCESS_DEF.DESCRIPTION,SECURITY_ACCESS_DEF.ACCESS_MODE,SECURITY_ACCESS_DEF.EXPRESSION,SECURITY_ACCESS_DEF.VERSION_NUM,SECURITY_ACCESS_DEF.UPDATED_BY_USER_ID,SECURITY_ACCESS_DEF.UPDATED_TIME,SECURITY_ACCESS_DEF.CREATED_TIME from SECURITY_ACCESS_DEF where    MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1082 */       closeResultSet(resultSet);
/* 1083 */       closeStatement(stmt);
/* 1084 */       closeConnection();
/*      */ 
/* 1086 */       if (timer != null) {
/* 1087 */         timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1092 */     return items;
/*      */   }
/*      */ 
/*      */   public SecurityAccessDefEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1109 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1112 */     if (this.mDetails == null) {
/* 1113 */       doLoad(((SecurityAccessDefCK)paramCK).getSecurityAccessDefPK());
/*      */     }
/* 1115 */     else if (!this.mDetails.getPK().equals(((SecurityAccessDefCK)paramCK).getSecurityAccessDefPK())) {
/* 1116 */       doLoad(((SecurityAccessDefCK)paramCK).getSecurityAccessDefPK());
/*      */     }
/*      */ 
/* 1119 */     if ((dependants.indexOf("<19>") > -1) && (!this.mDetails.isSecurityRangesForAccessDefAllItemsLoaded()))
/*      */     {
/* 1124 */       this.mDetails.setSecurityRangesForAccessDef(getSecurityAccRngRelDAO().getAll(this.mDetails.getSecurityAccessDefId(), dependants, this.mDetails.getSecurityRangesForAccessDef()));
/*      */ 
/* 1131 */       this.mDetails.setSecurityRangesForAccessDefAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1134 */     if ((paramCK instanceof SecurityAccRngRelCK))
/*      */     {
/* 1136 */       if (this.mDetails.getSecurityRangesForAccessDef() == null) {
/* 1137 */         this.mDetails.loadSecurityRangesForAccessDefItem(getSecurityAccRngRelDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1140 */         SecurityAccRngRelPK pk = ((SecurityAccRngRelCK)paramCK).getSecurityAccRngRelPK();
/* 1141 */         SecurityAccRngRelEVO evo = this.mDetails.getSecurityRangesForAccessDefItem(pk);
/* 1142 */         if (evo == null) {
/* 1143 */           this.mDetails.loadSecurityRangesForAccessDefItem(getSecurityAccRngRelDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1148 */     SecurityAccessDefEVO details = new SecurityAccessDefEVO();
/* 1149 */     details = this.mDetails.deepClone();
/*      */ 
/* 1151 */     if (timer != null) {
/* 1152 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1154 */     return details;
/*      */   }
/*      */ 
/*      */   public SecurityAccessDefEVO getDetails(ModelCK paramCK, SecurityAccessDefEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1160 */     SecurityAccessDefEVO savedEVO = this.mDetails;
/* 1161 */     this.mDetails = paramEVO;
/* 1162 */     SecurityAccessDefEVO newEVO = getDetails(paramCK, dependants);
/* 1163 */     this.mDetails = savedEVO;
/* 1164 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public SecurityAccessDefEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1170 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1174 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1177 */     SecurityAccessDefEVO details = this.mDetails.deepClone();
/*      */ 
/* 1179 */     if (timer != null) {
/* 1180 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1182 */     return details;
/*      */   }
/*      */ 
/*      */   protected SecurityAccRngRelDAO getSecurityAccRngRelDAO()
/*      */   {
/* 1191 */     if (this.mSecurityAccRngRelDAO == null)
/*      */     {
/* 1193 */       if (this.mDataSource != null)
/* 1194 */         this.mSecurityAccRngRelDAO = new SecurityAccRngRelDAO(this.mDataSource);
/*      */       else {
/* 1196 */         this.mSecurityAccRngRelDAO = new SecurityAccRngRelDAO(getConnection());
/*      */       }
/*      */     }
/* 1199 */     return this.mSecurityAccRngRelDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1204 */     return "SecurityAccessDef";
/*      */   }
/*      */ 
/*      */   public SecurityAccessDefRefImpl getRef(SecurityAccessDefPK paramSecurityAccessDefPK)
/*      */   {
/* 1209 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1210 */     PreparedStatement stmt = null;
/* 1211 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1214 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,SECURITY_ACCESS_DEF.VIS_ID from SECURITY_ACCESS_DEF,MODEL where 1=1 and SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID = ? and SECURITY_ACCESS_DEF.MODEL_ID = MODEL.MODEL_ID");
/* 1215 */       int col = 1;
/* 1216 */       stmt.setInt(col++, paramSecurityAccessDefPK.getSecurityAccessDefId());
/*      */ 
/* 1218 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1220 */       if (!resultSet.next()) {
/* 1221 */         throw new RuntimeException(getEntityName() + " getRef " + paramSecurityAccessDefPK + " not found");
/*      */       }
/* 1223 */       col = 2;
/* 1224 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1228 */       String textSecurityAccessDef = resultSet.getString(col++);
/* 1229 */       SecurityAccessDefCK ckSecurityAccessDef = new SecurityAccessDefCK(newModelPK, paramSecurityAccessDefPK);
/*      */ 
/* 1234 */       SecurityAccessDefRefImpl localSecurityAccessDefRefImpl = new SecurityAccessDefRefImpl(ckSecurityAccessDef, textSecurityAccessDef);
/*      */       return localSecurityAccessDefRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1239 */       throw handleSQLException(paramSecurityAccessDefPK, "select 0,MODEL.MODEL_ID,SECURITY_ACCESS_DEF.VIS_ID from SECURITY_ACCESS_DEF,MODEL where 1=1 and SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID = ? and SECURITY_ACCESS_DEF.MODEL_ID = MODEL.MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1243 */       closeResultSet(resultSet);
/* 1244 */       closeStatement(stmt);
/* 1245 */       closeConnection();
/*      */ 
/* 1247 */       if (timer != null)
/* 1248 */         timer.logDebug("getRef", paramSecurityAccessDefPK); 
/* 1248 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1260 */     if (c == null)
/* 1261 */       return;
/* 1262 */     Iterator iter = c.iterator();
/* 1263 */     while (iter.hasNext())
/*      */     {
/* 1265 */       SecurityAccessDefEVO evo = (SecurityAccessDefEVO)iter.next();
/* 1266 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(SecurityAccessDefEVO evo, String dependants)
/*      */   {
/* 1280 */     if (evo.insertPending()) {
/* 1281 */       return;
/*      */     }
/* 1283 */     if (evo.getSecurityAccessDefId() < 1) {
/* 1284 */       return;
/*      */     }
/*      */ 
/* 1288 */     if (dependants.indexOf("<19>") > -1)
/*      */     {
/* 1291 */       if (!evo.isSecurityRangesForAccessDefAllItemsLoaded())
/*      */       {
/* 1293 */         evo.setSecurityRangesForAccessDef(getSecurityAccRngRelDAO().getAll(evo.getSecurityAccessDefId(), dependants, evo.getSecurityRangesForAccessDef()));
/*      */ 
/* 1300 */         evo.setSecurityRangesForAccessDefAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public List getCellCalcList(int modelId, int[] selectedDims)
/*      */   {
/* 1357 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1358 */     PreparedStatement stmt = null;
/* 1359 */     ResultSet resultSet = null;
/* 1360 */     List results = new ArrayList();
/*      */     try
/*      */     {
/* 1363 */       StringBuffer sql = new StringBuffer();
/* 1364 */       sql.append(this.SQL_CELL_CALC_SELECT);
/* 1365 */       for (int i = 0; i < selectedDims.length; i++)
/*      */       {
/* 1367 */         if (i > 0) {
/* 1368 */           sql.append(" or ");
/*      */         }
/* 1370 */         sql.append(" SEC_RANGE.DIMENSION_ID = (SELECT DIMENSION_ID FROM MODEL_DIMENSION_REL WHERE MODEL_ID = MODEL.MODEL_ID AND DIMENSION_SEQ_NUM = ? ) ");
/*      */       }
/* 1372 */       sql.append(this.SQL_CELL_CALC_WHERE);
/* 1373 */       stmt = getConnection().prepareStatement(sql.toString());
/*      */ 
/* 1375 */       int col = 1;
/* 1376 */       for (int i = 0; i < selectedDims.length; i++) {
/* 1377 */         stmt.setInt(col++, i);
/*      */       }
/* 1379 */       stmt.setInt(col++, modelId);
/*      */ 
/* 1381 */       resultSet = stmt.executeQuery();
/* 1382 */       while (resultSet.next())
/*      */       {
/* 1384 */         CellCalcExpressionDetails cellcalc = new CellCalcExpressionDetails();
/* 1385 */         col = 1;
/* 1386 */         int cellCalcId = resultSet.getInt(col++);
/*      */ 
/* 1388 */         String expression = resultSet.getString(col++);
/*      */ 
/* 1390 */         ResultSet rs2 = (ResultSet)resultSet.getObject(col++);
/* 1391 */         List ranges = new ArrayList();
/* 1392 */         while (rs2.next())
/*      */         {
/* 1394 */           int col2 = 1;
/* 1395 */           CellCalcRangeDetails rangeDetails = new CellCalcRangeDetails();
/* 1396 */           int rangeId = rs2.getInt(col2++);
/* 1397 */           int dimId = rs2.getInt(col2++);
/* 1398 */           String visId = rs2.getString(col2++);
/* 1399 */           int seqId = rs2.getInt(col2++);
/*      */ 
/* 1401 */           ResultSet rs3 = (ResultSet)rs2.getObject(col2++);
/* 1402 */           List rows = new ArrayList();
/* 1403 */           while (rs3.next())
/*      */           {
/* 1405 */             int col3 = 1;
/* 1406 */             SecurityRangeRowDetails rowDetails = new SecurityRangeRowDetails();
/* 1407 */             String from = rs3.getString(col3++);
/* 1408 */             String to = rs3.getString(col3++);
/*      */ 
/* 1410 */             rowDetails.setFrom(from);
/* 1411 */             rowDetails.setTo(to);
/*      */ 
/* 1413 */             rows.add(rowDetails);
/*      */           }
/*      */ 
/* 1416 */           rangeDetails.setRangeId(rangeId);
/* 1417 */           rangeDetails.setDimensionId(dimId);
/* 1418 */           rangeDetails.setIdentifier(visId);
/* 1419 */           rangeDetails.setSeqId(seqId);
/* 1420 */           rangeDetails.setRow(rows);
/*      */ 
/* 1422 */           ranges.add(rangeDetails);
/*      */         }
/*      */ 
/* 1425 */         cellcalc.setCellCalcId(cellCalcId);
/* 1426 */         cellcalc.setExpression(expression);
/* 1427 */         cellcalc.setRange(ranges);
/*      */ 
/* 1429 */         results.add(cellcalc);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1434 */       System.err.println(sqle);
/* 1435 */       sqle.printStackTrace();
/* 1436 */       throw new RuntimeException(getEntityName() + " getCellCalcList", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1440 */       closeResultSet(resultSet);
/* 1441 */       closeStatement(stmt);
/* 1442 */       closeConnection();
/*      */     }
/*      */ 
/* 1445 */     if (timer != null) {
/* 1446 */       timer.logDebug("getCellCalcList", "");
/*      */     }
/*      */ 
/* 1449 */     return results;
/*      */   }
/*      */ 
/*      */   public AllSecurityAccessDefsForModelELO getCellCalcAccesDefs(int modelId)
/*      */   {
/* 1479 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1480 */     PreparedStatement stmt = null;
/* 1481 */     ResultSet resultSet = null;
/* 1482 */     AllSecurityAccessDefsForModelELO results = new AllSecurityAccessDefsForModelELO();
/*      */     try
/*      */     {
/* 1485 */       stmt = getConnection().prepareStatement("select MODEL.MODEL_ID,MODEL.VIS_ID,SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID ,SECURITY_ACCESS_DEF.VIS_ID ,SECURITY_ACCESS_DEF.SECURITY_ACCESS_DEF_ID ,SECURITY_ACCESS_DEF.DESCRIPTION from security_access_def ,model where security_access_def.security_access_def_id not in ( select SECURITY_ACC_RNG_REL.SECURITY_ACCESS_DEF_ID from SECURITY_RANGE ,SECURITY_ACC_RNG_REL ,model_dimension_rel mdr where mdr.model_id = ? and mdr.DIMENSION_SEQ_NUM = 0 and SECURITY_RANGE.DIMENSION_ID <> mdr.dimension_id and SECURITY_ACC_RNG_REL.SECURITY_RANGE_ID = SECURITY_RANGE.SECURITY_RANGE_ID ) and SECURITY_ACCESS_DEF.MODEL_ID = MODEL.MODEL_ID");
/* 1486 */       int col = 1;
/* 1487 */       stmt.setInt(col++, modelId);
/*      */ 
/* 1489 */       resultSet = stmt.executeQuery();
/* 1490 */       while (resultSet.next())
/*      */       {
/* 1492 */         col = 1;
/*      */ 
/* 1495 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1498 */         String textModel = resultSet.getString(col++);
/*      */ 
/* 1501 */         SecurityAccessDefPK pkSecurityAccessDef = new SecurityAccessDefPK(resultSet.getInt(col++));
/*      */ 
/* 1504 */         String textSecurityAccessDef = resultSet.getString(col++);
/*      */ 
/* 1507 */         SecurityAccessDefCK ckSecurityAccessDef = new SecurityAccessDefCK(pkModel, pkSecurityAccessDef);
/*      */ 
/* 1513 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/* 1519 */         SecurityAccessDefRefImpl erSecurityAccessDef = new SecurityAccessDefRefImpl(ckSecurityAccessDef, textSecurityAccessDef);
/*      */ 
/* 1524 */         int col1 = resultSet.getInt(col++);
/* 1525 */         String col2 = resultSet.getString(col++);
/*      */ 
/* 1528 */         results.add(erSecurityAccessDef, erModel, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1538 */       throw handleSQLException(SQL_ALL_SECURITY_ACCESS_DEFS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1542 */       closeResultSet(resultSet);
/* 1543 */       closeStatement(stmt);
/* 1544 */       closeConnection();
/*      */     }
/*      */ 
/* 1547 */     if (timer != null) {
/* 1548 */       timer.logDebug("getCellCalcAccesDefs", " items=" + results.size());
/*      */     }
/*      */ 
/* 1552 */     return results;
/*      */   }
/*      */ 
/*      */   public EntityList getSecurityAccessDetailsForModel(int modelId)
/*      */   {
/* 1557 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "select  sa.VIS_ID as ACCESS_NAME, sa.EXPRESSION", "        ,cursor", "        (", "        select  sr.VIS_ID as RANGE_NAME, DIMENSION_SEQ_NUM", "        from    SECURITY_ACC_RNG_REL sar", "        join    SECURITY_RANGE sr        using (SECURITY_RANGE_ID)", "        join    MODEL_DIMENSION_REL      using (DIMENSION_ID)", "        where   sar.SECURITY_ACCESS_DEF_ID = sa.SECURITY_ACCESS_DEF_ID", "        order   by length(sr.VIS_ID) desc, sr.VIS_ID", "        ) ranges", "from    SECURITY_ACCESS_DEF sa", "where   MODEL_ID = <modelId>", "order   by sa.VIS_ID" });
/*      */ 
/* 1572 */     SqlExecutor sqle = new SqlExecutor("getRangeSecurityForCube", getConnection(), sqlb, this._log);
/*      */     try
/*      */     {
/* 1575 */       sqle.addBindVariable("<modelId>", Integer.valueOf(modelId));
/* 1576 */       EntityList localEntityList = sqle.getEntityList();
/*      */       return localEntityList; } finally { closeConnection(); } //throw localObject;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.SecurityAccessDefDAO
 * JD-Core Version:    0.6.0
 */