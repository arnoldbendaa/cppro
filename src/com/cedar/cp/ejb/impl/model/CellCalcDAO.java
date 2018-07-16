/*      */ package com.cedar.cp.ejb.impl.model;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.AllCellCalcsELO;
/*      */ import com.cedar.cp.dto.model.CellCalcAssocCK;
/*      */ import com.cedar.cp.dto.model.CellCalcAssocPK;
/*      */ import com.cedar.cp.dto.model.CellCalcCK;
/*      */ import com.cedar.cp.dto.model.CellCalcIntegrityELO;
/*      */ import com.cedar.cp.dto.model.CellCalcPK;
/*      */ import com.cedar.cp.dto.model.CellCalcRefImpl;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.SqlBuilder;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import com.cedar.cp.util.common.JdbcUtils;
/*      */ import com.cedar.cp.util.common.JdbcUtils.ColType;
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
/*      */ import org.antlr.stringtemplate.StringTemplate;
/*      */ 
/*      */ public class CellCalcDAO extends AbstractDAO
/*      */ {
/*   35 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select CELL_CALC.CELL_CALC_ID,CELL_CALC.MODEL_ID,CELL_CALC.VIS_ID,CELL_CALC.DESCRIPTION,CELL_CALC.XMLFORM_ID,CELL_CALC.ACCESS_DEFINITION_ID,CELL_CALC.DATA_TYPE_ID,CELL_CALC.SUMMARY_PERIOD_ASSOCIATION,CELL_CALC.VERSION_NUM,CELL_CALC.UPDATED_BY_USER_ID,CELL_CALC.UPDATED_TIME,CELL_CALC.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from CELL_CALC where    CELL_CALC_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into CELL_CALC ( CELL_CALC_ID,MODEL_ID,VIS_ID,DESCRIPTION,XMLFORM_ID,ACCESS_DEFINITION_ID,DATA_TYPE_ID,SUMMARY_PERIOD_ASSOCIATION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_CELLCALCULATIONNAME = "select count(*) from CELL_CALC where    VIS_ID = ? and not(    CELL_CALC_ID = ? )";
/*      */   protected static final String SQL_STORE = "update CELL_CALC set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,XMLFORM_ID = ?,ACCESS_DEFINITION_ID = ?,DATA_TYPE_ID = ?,SUMMARY_PERIOD_ASSOCIATION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CELL_CALC_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from CELL_CALC where CELL_CALC_ID = ?";
/*  483 */   protected static String SQL_ALL_CELL_CALCS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,CELL_CALC.CELL_CALC_ID      ,CELL_CALC.VIS_ID      ,CELL_CALC.DESCRIPTION from CELL_CALC    ,MODEL where 1=1   and CELL_CALC.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID, CELL_CALC.VIS_ID";
/*      */ 
/*  589 */   protected static String SQL_CELL_CALC_INTEGRITY = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,CELL_CALC.CELL_CALC_ID      ,CELL_CALC.VIS_ID      ,CELL_CALC.MODEL_ID      ,CELL_CALC.XMLFORM_ID      ,CELL_CALC.ACCESS_DEFINITION_ID      ,CELL_CALC.DATA_TYPE_ID from CELL_CALC    ,MODEL where 1=1   and CELL_CALC.MODEL_ID = MODEL.MODEL_ID ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from CELL_CALC where    CELL_CALC_ID = ? ";
/*  840 */   private static String[][] SQL_DELETE_CHILDREN = { { "CELL_CALC_ASSOC", "delete from CELL_CALC_ASSOC where     CELL_CALC_ASSOC.CELL_CALC_ID = ? " } };
/*      */ 
/*  849 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  853 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and CELL_CALC.CELL_CALC_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from CELL_CALC where 1=1 and CELL_CALC.MODEL_ID = ? order by  CELL_CALC.CELL_CALC_ID";
/*      */   protected static final String SQL_GET_ALL = " from CELL_CALC where    MODEL_ID = ? ";
/*      */   protected CellCalcAssocDAO mCellCalcAssocDAO;
/*      */   protected CellCalcEVO mDetails;
/*      */ 
/*      */   public CellCalcDAO(Connection connection)
/*      */   {
/*   42 */     super(connection);
/*      */   }
/*      */ 
/*      */   public CellCalcDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public CellCalcDAO(DataSource ds)
/*      */   {
/*   58 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected CellCalcPK getPK()
/*      */   {
/*   66 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(CellCalcEVO details)
/*      */   {
/*   75 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private CellCalcEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   98 */     int col = 1;
/*   99 */     CellCalcEVO evo = new CellCalcEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), null);
/*      */ 
/*  112 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  113 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  114 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  115 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(CellCalcEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  120 */     int col = startCol_;
/*  121 */     stmt_.setInt(col++, evo_.getCellCalcId());
/*  122 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(CellCalcEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  127 */     int col = startCol_;
/*  128 */     stmt_.setInt(col++, evo_.getModelId());
/*  129 */     stmt_.setString(col++, evo_.getVisId());
/*  130 */     stmt_.setString(col++, evo_.getDescription());
/*  131 */     stmt_.setInt(col++, evo_.getXmlformId());
/*  132 */     stmt_.setInt(col++, evo_.getAccessDefinitionId());
/*  133 */     stmt_.setInt(col++, evo_.getDataTypeId());
/*  134 */     if (evo_.getSummaryPeriodAssociation())
/*  135 */       stmt_.setString(col++, "Y");
/*      */     else
/*  137 */       stmt_.setString(col++, " ");
/*  138 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  139 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  140 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  141 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  142 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(CellCalcPK pk)
/*      */     throws ValidationException
/*      */   {
/*  158 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  160 */     PreparedStatement stmt = null;
/*  161 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  165 */       stmt = getConnection().prepareStatement("select CELL_CALC.CELL_CALC_ID,CELL_CALC.MODEL_ID,CELL_CALC.VIS_ID,CELL_CALC.DESCRIPTION,CELL_CALC.XMLFORM_ID,CELL_CALC.ACCESS_DEFINITION_ID,CELL_CALC.DATA_TYPE_ID,CELL_CALC.SUMMARY_PERIOD_ASSOCIATION,CELL_CALC.VERSION_NUM,CELL_CALC.UPDATED_BY_USER_ID,CELL_CALC.UPDATED_TIME,CELL_CALC.CREATED_TIME from CELL_CALC where    CELL_CALC_ID = ? ");
/*      */ 
/*  168 */       int col = 1;
/*  169 */       stmt.setInt(col++, pk.getCellCalcId());
/*      */ 
/*  171 */       resultSet = stmt.executeQuery();
/*      */ 
/*  173 */       if (!resultSet.next()) {
/*  174 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  177 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  178 */       if (this.mDetails.isModified())
/*  179 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  183 */       throw handleSQLException(pk, "select CELL_CALC.CELL_CALC_ID,CELL_CALC.MODEL_ID,CELL_CALC.VIS_ID,CELL_CALC.DESCRIPTION,CELL_CALC.XMLFORM_ID,CELL_CALC.ACCESS_DEFINITION_ID,CELL_CALC.DATA_TYPE_ID,CELL_CALC.SUMMARY_PERIOD_ASSOCIATION,CELL_CALC.VERSION_NUM,CELL_CALC.UPDATED_BY_USER_ID,CELL_CALC.UPDATED_TIME,CELL_CALC.CREATED_TIME from CELL_CALC where    CELL_CALC_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  187 */       closeResultSet(resultSet);
/*  188 */       closeStatement(stmt);
/*  189 */       closeConnection();
/*      */ 
/*  191 */       if (timer != null)
/*  192 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  233 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  234 */     this.mDetails.postCreateInit();
/*      */ 
/*  236 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  241 */       duplicateValueCheckCellCalculationName();
/*      */ 
/*  243 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  244 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  245 */       stmt = getConnection().prepareStatement("insert into CELL_CALC ( CELL_CALC_ID,MODEL_ID,VIS_ID,DESCRIPTION,XMLFORM_ID,ACCESS_DEFINITION_ID,DATA_TYPE_ID,SUMMARY_PERIOD_ASSOCIATION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  248 */       int col = 1;
/*  249 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  250 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  253 */       int resultCount = stmt.executeUpdate();
/*  254 */       if (resultCount != 1)
/*      */       {
/*  256 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  259 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  263 */       throw handleSQLException(this.mDetails.getPK(), "insert into CELL_CALC ( CELL_CALC_ID,MODEL_ID,VIS_ID,DESCRIPTION,XMLFORM_ID,ACCESS_DEFINITION_ID,DATA_TYPE_ID,SUMMARY_PERIOD_ASSOCIATION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  267 */       closeStatement(stmt);
/*  268 */       closeConnection();
/*      */ 
/*  270 */       if (timer != null) {
/*  271 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  277 */       getCellCalcAssocDAO().update(this.mDetails.getCellCalculationAccountsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  283 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckCellCalculationName()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  298 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  299 */     PreparedStatement stmt = null;
/*  300 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  304 */       stmt = getConnection().prepareStatement("select count(*) from CELL_CALC where    VIS_ID = ? and not(    CELL_CALC_ID = ? )");
/*      */ 
/*  307 */       int col = 1;
/*  308 */       stmt.setString(col++, this.mDetails.getVisId());
/*  309 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  312 */       resultSet = stmt.executeQuery();
/*      */ 
/*  314 */       if (!resultSet.next()) {
/*  315 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  319 */       col = 1;
/*  320 */       int count = resultSet.getInt(col++);
/*  321 */       if (count > 0) {
/*  322 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " CellCalculationName");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  328 */       throw handleSQLException(getPK(), "select count(*) from CELL_CALC where    VIS_ID = ? and not(    CELL_CALC_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  332 */       closeResultSet(resultSet);
/*  333 */       closeStatement(stmt);
/*  334 */       closeConnection();
/*      */ 
/*  336 */       if (timer != null)
/*  337 */         timer.logDebug("duplicateValueCheckCellCalculationName", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  367 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  371 */     PreparedStatement stmt = null;
/*      */ 
/*  373 */     boolean mainChanged = this.mDetails.isModified();
/*  374 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  378 */       if (mainChanged) {
/*  379 */         duplicateValueCheckCellCalculationName();
/*      */       }
/*  381 */       if (getCellCalcAssocDAO().update(this.mDetails.getCellCalculationAccountsMap())) {
/*  382 */         dependantChanged = true;
/*      */       }
/*  384 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  387 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  390 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  391 */         stmt = getConnection().prepareStatement("update CELL_CALC set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,XMLFORM_ID = ?,ACCESS_DEFINITION_ID = ?,DATA_TYPE_ID = ?,SUMMARY_PERIOD_ASSOCIATION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CELL_CALC_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  394 */         int col = 1;
/*  395 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  396 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  398 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  401 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  403 */         if (resultCount == 0) {
/*  404 */           checkVersionNum();
/*      */         }
/*  406 */         if (resultCount != 1) {
/*  407 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  410 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  419 */       throw handleSQLException(getPK(), "update CELL_CALC set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,XMLFORM_ID = ?,ACCESS_DEFINITION_ID = ?,DATA_TYPE_ID = ?,SUMMARY_PERIOD_ASSOCIATION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CELL_CALC_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  423 */       closeStatement(stmt);
/*  424 */       closeConnection();
/*      */ 
/*  426 */       if ((timer != null) && (
/*  427 */         (mainChanged) || (dependantChanged)))
/*  428 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  440 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  441 */     PreparedStatement stmt = null;
/*  442 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  446 */       stmt = getConnection().prepareStatement("select VERSION_NUM from CELL_CALC where CELL_CALC_ID = ?");
/*      */ 
/*  449 */       int col = 1;
/*  450 */       stmt.setInt(col++, this.mDetails.getCellCalcId());
/*      */ 
/*  453 */       resultSet = stmt.executeQuery();
/*      */ 
/*  455 */       if (!resultSet.next()) {
/*  456 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  459 */       col = 1;
/*  460 */       int dbVersionNumber = resultSet.getInt(col++);
/*  461 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  462 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  468 */       throw handleSQLException(getPK(), "select VERSION_NUM from CELL_CALC where CELL_CALC_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  472 */       closeStatement(stmt);
/*  473 */       closeResultSet(resultSet);
/*      */ 
/*  475 */       if (timer != null)
/*  476 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllCellCalcsELO getAllCellCalcs()
/*      */   {
/*  512 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  513 */     PreparedStatement stmt = null;
/*  514 */     ResultSet resultSet = null;
/*  515 */     AllCellCalcsELO results = new AllCellCalcsELO();
/*      */     try
/*      */     {
/*  518 */       stmt = getConnection().prepareStatement(SQL_ALL_CELL_CALCS);
/*  519 */       int col = 1;
/*  520 */       resultSet = stmt.executeQuery();
/*  521 */       while (resultSet.next())
/*      */       {
/*  523 */         col = 2;
/*      */ 
/*  526 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  529 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  532 */         CellCalcPK pkCellCalc = new CellCalcPK(resultSet.getInt(col++));
/*      */ 
/*  535 */         String textCellCalc = resultSet.getString(col++);
/*      */ 
/*  540 */         CellCalcCK ckCellCalc = new CellCalcCK(pkModel, pkCellCalc);
/*      */ 
/*  546 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  552 */         CellCalcRefImpl erCellCalc = new CellCalcRefImpl(ckCellCalc, textCellCalc);
/*      */ 
/*  557 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  560 */         results.add(erCellCalc, erModel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  569 */       throw handleSQLException(SQL_ALL_CELL_CALCS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  573 */       closeResultSet(resultSet);
/*  574 */       closeStatement(stmt);
/*  575 */       closeConnection();
/*      */     }
/*      */ 
/*  578 */     if (timer != null) {
/*  579 */       timer.logDebug("getAllCellCalcs", " items=" + results.size());
/*      */     }
/*      */ 
/*  583 */     return results;
/*      */   }
/*      */ 
/*      */   public CellCalcIntegrityELO getCellCalcIntegrity()
/*      */   {
/*  621 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  622 */     PreparedStatement stmt = null;
/*  623 */     ResultSet resultSet = null;
/*  624 */     CellCalcIntegrityELO results = new CellCalcIntegrityELO();
/*      */     try
/*      */     {
/*  627 */       stmt = getConnection().prepareStatement(SQL_CELL_CALC_INTEGRITY);
/*  628 */       int col = 1;
/*  629 */       resultSet = stmt.executeQuery();
/*  630 */       while (resultSet.next())
/*      */       {
/*  632 */         col = 2;
/*      */ 
/*  635 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  638 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  641 */         CellCalcPK pkCellCalc = new CellCalcPK(resultSet.getInt(col++));
/*      */ 
/*  644 */         String textCellCalc = resultSet.getString(col++);
/*      */ 
/*  649 */         CellCalcCK ckCellCalc = new CellCalcCK(pkModel, pkCellCalc);
/*      */ 
/*  655 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  661 */         CellCalcRefImpl erCellCalc = new CellCalcRefImpl(ckCellCalc, textCellCalc);
/*      */ 
/*  666 */         int col1 = resultSet.getInt(col++);
/*  667 */         int col2 = resultSet.getInt(col++);
/*  668 */         int col3 = resultSet.getInt(col++);
/*  669 */         int col4 = resultSet.getInt(col++);
/*      */ 
/*  672 */         results.add(erCellCalc, erModel, col1, col2, col3, col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  684 */       throw handleSQLException(SQL_CELL_CALC_INTEGRITY, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  688 */       closeResultSet(resultSet);
/*  689 */       closeStatement(stmt);
/*  690 */       closeConnection();
/*      */     }
/*      */ 
/*  693 */     if (timer != null) {
/*  694 */       timer.logDebug("getCellCalcIntegrity", " items=" + results.size());
/*      */     }
/*      */ 
/*  698 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  715 */     if (items == null) {
/*  716 */       return false;
/*      */     }
/*  718 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  719 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  721 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  725 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  726 */       while (iter3.hasNext())
/*      */       {
/*  728 */         this.mDetails = ((CellCalcEVO)iter3.next());
/*  729 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  731 */         somethingChanged = true;
/*      */ 
/*  734 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  738 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  739 */       while (iter2.hasNext())
/*      */       {
/*  741 */         this.mDetails = ((CellCalcEVO)iter2.next());
/*      */ 
/*  744 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  746 */         somethingChanged = true;
/*      */ 
/*  749 */         if (deleteStmt == null) {
/*  750 */           deleteStmt = getConnection().prepareStatement("delete from CELL_CALC where    CELL_CALC_ID = ? ");
/*      */         }
/*      */ 
/*  753 */         int col = 1;
/*  754 */         deleteStmt.setInt(col++, this.mDetails.getCellCalcId());
/*      */ 
/*  756 */         if (this._log.isDebugEnabled()) {
/*  757 */           this._log.debug("update", "CellCalc deleting CellCalcId=" + this.mDetails.getCellCalcId());
/*      */         }
/*      */ 
/*  762 */         deleteStmt.addBatch();
/*      */ 
/*  765 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  770 */       if (deleteStmt != null)
/*      */       {
/*  772 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  774 */         deleteStmt.executeBatch();
/*      */ 
/*  776 */         if (timer2 != null) {
/*  777 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  781 */       Iterator iter1 = items.values().iterator();
/*  782 */       while (iter1.hasNext())
/*      */       {
/*  784 */         this.mDetails = ((CellCalcEVO)iter1.next());
/*      */ 
/*  786 */         if (this.mDetails.insertPending())
/*      */         {
/*  788 */           somethingChanged = true;
/*  789 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  792 */         if (this.mDetails.isModified())
/*      */         {
/*  794 */           somethingChanged = true;
/*  795 */           doStore(); continue;
/*      */         }
/*      */ 
/*  799 */         if ((this.mDetails.deletePending()) || 
/*  805 */           (!getCellCalcAssocDAO().update(this.mDetails.getCellCalculationAccountsMap()))) continue;
/*  806 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  818 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  822 */       throw handleSQLException("delete from CELL_CALC where    CELL_CALC_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  826 */       if (deleteStmt != null)
/*      */       {
/*  828 */         closeStatement(deleteStmt);
/*  829 */         closeConnection();
/*      */       }
/*      */ 
/*  832 */       this.mDetails = null;
/*      */ 
/*  834 */       if ((somethingChanged) && 
/*  835 */         (timer != null))
/*  836 */         timer.logDebug("update", "collection"); 
/*  836 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(CellCalcPK pk)
/*      */   {
/*  862 */     Set emptyStrings = Collections.emptySet();
/*  863 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(CellCalcPK pk, Set<String> exclusionTables)
/*      */   {
/*  869 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  871 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  873 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  875 */       PreparedStatement stmt = null;
/*      */ 
/*  877 */       int resultCount = 0;
/*  878 */       String s = null;
/*      */       try
/*      */       {
/*  881 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  883 */         if (this._log.isDebugEnabled()) {
/*  884 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  886 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  889 */         int col = 1;
/*  890 */         stmt.setInt(col++, pk.getCellCalcId());
/*      */ 
/*  893 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  897 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  901 */         closeStatement(stmt);
/*  902 */         closeConnection();
/*      */ 
/*  904 */         if (timer != null) {
/*  905 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  909 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  911 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  913 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  915 */       PreparedStatement stmt = null;
/*      */ 
/*  917 */       int resultCount = 0;
/*  918 */       String s = null;
/*      */       try
/*      */       {
/*  921 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  923 */         if (this._log.isDebugEnabled()) {
/*  924 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  926 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  929 */         int col = 1;
/*  930 */         stmt.setInt(col++, pk.getCellCalcId());
/*      */ 
/*  933 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  937 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  941 */         closeStatement(stmt);
/*  942 */         closeConnection();
/*      */ 
/*  944 */         if (timer != null)
/*  945 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants)
/*      */   {
/*  965 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  967 */     PreparedStatement stmt = null;
/*  968 */     ResultSet resultSet = null;
/*      */ 
/*  970 */     int itemCount = 0;
/*      */ 
/*  972 */     Collection theseItems = new ArrayList();
/*  973 */     owningEVO.setCellCalculations(theseItems);
/*  974 */     owningEVO.setCellCalculationsAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  978 */       stmt = getConnection().prepareStatement("select CELL_CALC.CELL_CALC_ID,CELL_CALC.MODEL_ID,CELL_CALC.VIS_ID,CELL_CALC.DESCRIPTION,CELL_CALC.XMLFORM_ID,CELL_CALC.ACCESS_DEFINITION_ID,CELL_CALC.DATA_TYPE_ID,CELL_CALC.SUMMARY_PERIOD_ASSOCIATION,CELL_CALC.VERSION_NUM,CELL_CALC.UPDATED_BY_USER_ID,CELL_CALC.UPDATED_TIME,CELL_CALC.CREATED_TIME from CELL_CALC where 1=1 and CELL_CALC.MODEL_ID = ? order by  CELL_CALC.CELL_CALC_ID");
/*      */ 
/*  980 */       int col = 1;
/*  981 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  983 */       resultSet = stmt.executeQuery();
/*      */ 
/*  986 */       while (resultSet.next())
/*      */       {
/*  988 */         itemCount++;
/*  989 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  991 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  994 */       if (timer != null) {
/*  995 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  998 */       if ((itemCount > 0) && (dependants.indexOf("<21>") > -1))
/*      */       {
/* 1000 */         getCellCalcAssocDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/* 1004 */       throw handleSQLException("select CELL_CALC.CELL_CALC_ID,CELL_CALC.MODEL_ID,CELL_CALC.VIS_ID,CELL_CALC.DESCRIPTION,CELL_CALC.XMLFORM_ID,CELL_CALC.ACCESS_DEFINITION_ID,CELL_CALC.DATA_TYPE_ID,CELL_CALC.SUMMARY_PERIOD_ASSOCIATION,CELL_CALC.VERSION_NUM,CELL_CALC.UPDATED_BY_USER_ID,CELL_CALC.UPDATED_TIME,CELL_CALC.CREATED_TIME from CELL_CALC where 1=1 and CELL_CALC.MODEL_ID = ? order by  CELL_CALC.CELL_CALC_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1008 */       closeResultSet(resultSet);
/* 1009 */       closeStatement(stmt);
/* 1010 */       closeConnection();
/*      */ 
/* 1012 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectModelId, String dependants, Collection currentList)
/*      */   {
/* 1037 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1038 */     PreparedStatement stmt = null;
/* 1039 */     ResultSet resultSet = null;
/*      */ 
/* 1041 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/* 1045 */       stmt = getConnection().prepareStatement("select CELL_CALC.CELL_CALC_ID,CELL_CALC.MODEL_ID,CELL_CALC.VIS_ID,CELL_CALC.DESCRIPTION,CELL_CALC.XMLFORM_ID,CELL_CALC.ACCESS_DEFINITION_ID,CELL_CALC.DATA_TYPE_ID,CELL_CALC.SUMMARY_PERIOD_ASSOCIATION,CELL_CALC.VERSION_NUM,CELL_CALC.UPDATED_BY_USER_ID,CELL_CALC.UPDATED_TIME,CELL_CALC.CREATED_TIME from CELL_CALC where    MODEL_ID = ? ");
/*      */ 
/* 1047 */       int col = 1;
/* 1048 */       stmt.setInt(col++, selectModelId);
/*      */ 
/* 1050 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1052 */       while (resultSet.next())
/*      */       {
/* 1054 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1057 */         getDependants(this.mDetails, dependants);
/*      */ 
/* 1060 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1063 */       if (currentList != null)
/*      */       {
/* 1066 */         ListIterator iter = items.listIterator();
/* 1067 */         CellCalcEVO currentEVO = null;
/* 1068 */         CellCalcEVO newEVO = null;
/* 1069 */         while (iter.hasNext())
/*      */         {
/* 1071 */           newEVO = (CellCalcEVO)iter.next();
/* 1072 */           Iterator iter2 = currentList.iterator();
/* 1073 */           while (iter2.hasNext())
/*      */           {
/* 1075 */             currentEVO = (CellCalcEVO)iter2.next();
/* 1076 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1078 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1084 */         Iterator iter2 = currentList.iterator();
/* 1085 */         while (iter2.hasNext())
/*      */         {
/* 1087 */           currentEVO = (CellCalcEVO)iter2.next();
/* 1088 */           if (currentEVO.insertPending()) {
/* 1089 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1093 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1097 */       throw handleSQLException("select CELL_CALC.CELL_CALC_ID,CELL_CALC.MODEL_ID,CELL_CALC.VIS_ID,CELL_CALC.DESCRIPTION,CELL_CALC.XMLFORM_ID,CELL_CALC.ACCESS_DEFINITION_ID,CELL_CALC.DATA_TYPE_ID,CELL_CALC.SUMMARY_PERIOD_ASSOCIATION,CELL_CALC.VERSION_NUM,CELL_CALC.UPDATED_BY_USER_ID,CELL_CALC.UPDATED_TIME,CELL_CALC.CREATED_TIME from CELL_CALC where    MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1101 */       closeResultSet(resultSet);
/* 1102 */       closeStatement(stmt);
/* 1103 */       closeConnection();
/*      */ 
/* 1105 */       if (timer != null) {
/* 1106 */         timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1111 */     return items;
/*      */   }
/*      */ 
/*      */   public CellCalcEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1128 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1131 */     if (this.mDetails == null) {
/* 1132 */       doLoad(((CellCalcCK)paramCK).getCellCalcPK());
/*      */     }
/* 1134 */     else if (!this.mDetails.getPK().equals(((CellCalcCK)paramCK).getCellCalcPK())) {
/* 1135 */       doLoad(((CellCalcCK)paramCK).getCellCalcPK());
/*      */     }
/*      */ 
/* 1138 */     if ((dependants.indexOf("<21>") > -1) && (!this.mDetails.isCellCalculationAccountsAllItemsLoaded()))
/*      */     {
/* 1143 */       this.mDetails.setCellCalculationAccounts(getCellCalcAssocDAO().getAll(this.mDetails.getCellCalcId(), dependants, this.mDetails.getCellCalculationAccounts()));
/*      */ 
/* 1150 */       this.mDetails.setCellCalculationAccountsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1153 */     if ((paramCK instanceof CellCalcAssocCK))
/*      */     {
/* 1155 */       if (this.mDetails.getCellCalculationAccounts() == null) {
/* 1156 */         this.mDetails.loadCellCalculationAccountsItem(getCellCalcAssocDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1159 */         CellCalcAssocPK pk = ((CellCalcAssocCK)paramCK).getCellCalcAssocPK();
/* 1160 */         CellCalcAssocEVO evo = this.mDetails.getCellCalculationAccountsItem(pk);
/* 1161 */         if (evo == null) {
/* 1162 */           this.mDetails.loadCellCalculationAccountsItem(getCellCalcAssocDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1167 */     CellCalcEVO details = new CellCalcEVO();
/* 1168 */     details = this.mDetails.deepClone();
/*      */ 
/* 1170 */     if (timer != null) {
/* 1171 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1173 */     return details;
/*      */   }
/*      */ 
/*      */   public CellCalcEVO getDetails(ModelCK paramCK, CellCalcEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1179 */     CellCalcEVO savedEVO = this.mDetails;
/* 1180 */     this.mDetails = paramEVO;
/* 1181 */     CellCalcEVO newEVO = getDetails(paramCK, dependants);
/* 1182 */     this.mDetails = savedEVO;
/* 1183 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public CellCalcEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1189 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1193 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1196 */     CellCalcEVO details = this.mDetails.deepClone();
/*      */ 
/* 1198 */     if (timer != null) {
/* 1199 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1201 */     return details;
/*      */   }
/*      */ 
/*      */   protected CellCalcAssocDAO getCellCalcAssocDAO()
/*      */   {
/* 1210 */     if (this.mCellCalcAssocDAO == null)
/*      */     {
/* 1212 */       if (this.mDataSource != null)
/* 1213 */         this.mCellCalcAssocDAO = new CellCalcAssocDAO(this.mDataSource);
/*      */       else {
/* 1215 */         this.mCellCalcAssocDAO = new CellCalcAssocDAO(getConnection());
/*      */       }
/*      */     }
/* 1218 */     return this.mCellCalcAssocDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1223 */     return "CellCalc";
/*      */   }
/*      */ 
/*      */   public CellCalcRefImpl getRef(CellCalcPK paramCellCalcPK)
/*      */   {
/* 1228 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1229 */     PreparedStatement stmt = null;
/* 1230 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1233 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,CELL_CALC.VIS_ID from CELL_CALC,MODEL where 1=1 and CELL_CALC.CELL_CALC_ID = ? and CELL_CALC.MODEL_ID = MODEL.MODEL_ID");
/* 1234 */       int col = 1;
/* 1235 */       stmt.setInt(col++, paramCellCalcPK.getCellCalcId());
/*      */ 
/* 1237 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1239 */       if (!resultSet.next()) {
/* 1240 */         throw new RuntimeException(getEntityName() + " getRef " + paramCellCalcPK + " not found");
/*      */       }
/* 1242 */       col = 2;
/* 1243 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1247 */       String textCellCalc = resultSet.getString(col++);
/* 1248 */       CellCalcCK ckCellCalc = new CellCalcCK(newModelPK, paramCellCalcPK);
/*      */ 
/* 1253 */       CellCalcRefImpl localCellCalcRefImpl = new CellCalcRefImpl(ckCellCalc, textCellCalc);
/*      */       return localCellCalcRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1258 */       throw handleSQLException(paramCellCalcPK, "select 0,MODEL.MODEL_ID,CELL_CALC.VIS_ID from CELL_CALC,MODEL where 1=1 and CELL_CALC.CELL_CALC_ID = ? and CELL_CALC.MODEL_ID = MODEL.MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1262 */       closeResultSet(resultSet);
/* 1263 */       closeStatement(stmt);
/* 1264 */       closeConnection();
/*      */ 
/* 1266 */       if (timer != null)
/* 1267 */         timer.logDebug("getRef", paramCellCalcPK); 
/* 1267 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1279 */     if (c == null)
/* 1280 */       return;
/* 1281 */     Iterator iter = c.iterator();
/* 1282 */     while (iter.hasNext())
/*      */     {
/* 1284 */       CellCalcEVO evo = (CellCalcEVO)iter.next();
/* 1285 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(CellCalcEVO evo, String dependants)
/*      */   {
/* 1299 */     if (evo.insertPending()) {
/* 1300 */       return;
/*      */     }
/* 1302 */     if (evo.getCellCalcId() < 1) {
/* 1303 */       return;
/*      */     }
/*      */ 
/* 1307 */     if (dependants.indexOf("<21>") > -1)
/*      */     {
/* 1310 */       if (!evo.isCellCalculationAccountsAllItemsLoaded())
/*      */       {
/* 1312 */         evo.setCellCalculationAccounts(getCellCalcAssocDAO().getAll(evo.getCellCalcId(), dependants, evo.getCellCalculationAccounts()));
/*      */ 
/* 1319 */         evo.setCellCalculationAccountsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public EntityList queryCellCalculationInstanceBalances(int numDims, int financeCubeId, int cellCalcId, int cellCalcShortId)
/*      */   {
/* 1338 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1339 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/* 1344 */       StringTemplate st = getTemplate("queryCellCalculationInstanceBalances");
/*      */ 
/* 1346 */       st.setAttribute("calDim", String.valueOf(numDims - 1));
/* 1347 */       int[] dims = new int[numDims];
/* 1348 */       for (int i = 0; i < numDims; i++)
/* 1349 */         dims[i] = i;
/* 1350 */       st.setAttribute("dims", dims);
/* 1351 */       int[] badims = new int[numDims - 1];
/* 1352 */       for (int i = 0; i < numDims - 1; i++)
/* 1353 */         badims[i] = i;
/* 1354 */       st.setAttribute("badims", badims);
/* 1355 */       st.setAttribute("financeCubeId", financeCubeId);
/*      */ 
/* 1357 */       SqlExecutor sqlExecutor = new SqlExecutor("queryCellCalculationInstanceBalances", getConnection(), new SqlBuilder(new String[] { st.toString() }), this._log);
/*      */ 
/* 1361 */       sqlExecutor.addBindVariable("<cell_calc_id>", Integer.valueOf(cellCalcId));
/* 1362 */       sqlExecutor.addBindVariable("<short_id>", Integer.valueOf(cellCalcShortId));
/*      */ 
/* 1364 */       JdbcUtils.ColType[] resultSetMetaData = new JdbcUtils.ColType[numDims + 2];
/* 1365 */       int index = 0;
/* 1366 */       for (int i = 0; i < numDims; i++)
/* 1367 */         resultSetMetaData[(index++)] = new JdbcUtils.ColType("dim" + i, 0);
/* 1368 */       resultSetMetaData[(index++)] = new JdbcUtils.ColType("data_type", 1);
/* 1369 */       resultSetMetaData[(index++)] = new JdbcUtils.ColType("number_value", 0);
/*      */ 
/* 1371 */       EntityList el = JdbcUtils.extractToEntityListImpl(resultSetMetaData, sqlExecutor.getResultSet());
/*      */       return el;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1375 */       throw handleSQLException("queryCellCalculationInstanceBalances", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1379 */       closeConnection();
/* 1380 */       if (timer != null)
/* 1381 */         timer.logDebug("queryCellCalculationInstanceBalances"); 
/* 1381 */     }
/*      */   }
/*      */ 
/*      */   public void deleteCellCalculatorLinks(int financeCubeId, int cellCalcId, int cellCalcShortId)
/*      */   {
/* 1393 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1394 */     PreparedStatement ps = null;
/*      */ 
/* 1396 */     SqlBuilder builder = new SqlBuilder(new String[] { "delete from cft${financeCubeId} cft", "where cft.cell_calc_id = <cell_calc_id> and", "      cft.short_id = <short_id>" });
/*      */     try
/*      */     {
/* 1405 */       builder.substitute(new String[] { "${financeCubeId}", String.valueOf(financeCubeId) });
/*      */ 
/* 1407 */       SqlExecutor sqlExecutor = new SqlExecutor("deleteCellCalculatorLinks", getConnection(), builder, this._log);
/*      */ 
/* 1411 */       sqlExecutor.addBindVariable("<cell_calc_id>", Integer.valueOf(cellCalcId));
/* 1412 */       sqlExecutor.addBindVariable("<short_id>", Integer.valueOf(cellCalcShortId));
/*      */ 
/* 1414 */       sqlExecutor.executeUpdate();
/*      */     }
/*      */     finally
/*      */     {
/* 1418 */       closeConnection();
/* 1419 */       if (timer != null)
/* 1420 */         timer.logDebug("deleteCellCalculatorLinks");
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.CellCalcDAO
 * JD-Core Version:    0.6.0
 */