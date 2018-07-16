/*      */ package com.cedar.cp.ejb.impl.cubeformula;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.cubeformula.AllCubeFormulasELO;
/*      */ import com.cedar.cp.dto.cubeformula.CubeFormulaCK;
/*      */ import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
/*      */ import com.cedar.cp.dto.cubeformula.CubeFormulaRefImpl;
/*      */ import com.cedar.cp.dto.cubeformula.CubeFormulaeForFinanceCubeELO;
/*      */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentLineCK;
/*      */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentLinePK;
import com.cedar.cp.dto.model.AllFinanceCubesELO;
/*      */ import com.cedar.cp.dto.model.FinanceCubeCK;
/*      */ import com.cedar.cp.dto.model.FinanceCubePK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeRefImpl;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
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
/*      */ import oracle.sql.CLOB;
/*      */ 
/*      */ public class CubeFormulaDAO extends AbstractDAO
/*      */ {
/*   46 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_LOBS = "select  FORMULA_TEXT from CUBE_FORMULA where    CUBE_FORMULA_ID = ? for update";
/*      */   private static final String SQL_SELECT_COLUMNS = "select CUBE_FORMULA.FORMULA_TEXT,CUBE_FORMULA.CUBE_FORMULA_ID,CUBE_FORMULA.FINANCE_CUBE_ID,CUBE_FORMULA.VIS_ID,CUBE_FORMULA.DESCRIPTION,CUBE_FORMULA.DEPLOYMENT_IND,CUBE_FORMULA.FORMULA_TYPE,CUBE_FORMULA.UPDATED_BY_USER_ID,CUBE_FORMULA.UPDATED_TIME,CUBE_FORMULA.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from CUBE_FORMULA where    CUBE_FORMULA_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into CUBE_FORMULA ( CUBE_FORMULA_ID,FINANCE_CUBE_ID,VIS_ID,DESCRIPTION,FORMULA_TEXT,DEPLOYMENT_IND,FORMULA_TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,empty_clob(),?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update CUBE_FORMULA set FINANCE_CUBE_ID = ?,VIS_ID = ?,DESCRIPTION = ?,DEPLOYMENT_IND = ?,FORMULA_TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CUBE_FORMULA_ID = ? ";
/*  425 */   protected static String SQL_ALL_CUBE_FORMULAS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,CUBE_FORMULA.CUBE_FORMULA_ID      ,CUBE_FORMULA.VIS_ID      ,CUBE_FORMULA.VIS_ID      ,CUBE_FORMULA.DESCRIPTION      ,CUBE_FORMULA.FORMULA_TYPE      ,CUBE_FORMULA.DEPLOYMENT_IND from CUBE_FORMULA    ,MODEL    ,FINANCE_CUBE where 1=1   and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID ";
/*      */   protected static String SQL_ALL_CUBE_FORMULAS_FOR_USER = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,CUBE_FORMULA.CUBE_FORMULA_ID      ,CUBE_FORMULA.VIS_ID      ,CUBE_FORMULA.VIS_ID      ,CUBE_FORMULA.DESCRIPTION      ,CUBE_FORMULA.FORMULA_TYPE      ,CUBE_FORMULA.DEPLOYMENT_IND from CUBE_FORMULA    ,MODEL    ,FINANCE_CUBE where 1=1   and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID ";
/*  567 */   protected static String SQL_CUBE_FORMULAE_FOR_FINANCE_CUBE = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,CUBE_FORMULA.CUBE_FORMULA_ID      ,CUBE_FORMULA.VIS_ID      ,CUBE_FORMULA.VIS_ID      ,CUBE_FORMULA.DESCRIPTION      ,CUBE_FORMULA.FORMULA_TYPE      ,CUBE_FORMULA.DEPLOYMENT_IND from CUBE_FORMULA    ,MODEL    ,FINANCE_CUBE where model.model_id in (select distinct model_id from budget_user where user_id = ?)  and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  CUBE_FORMULA.FINANCE_CUBE_ID = ?";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from CUBE_FORMULA where    CUBE_FORMULA_ID = ? ";
/*  849 */   private static String[][] SQL_DELETE_CHILDREN = { { "FORMULA_DEPLOYMENT_LINE", "delete from FORMULA_DEPLOYMENT_LINE where     FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = ? " } };
/*      */ 
/*  858 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "FORMULA_DEPLOYMENT_ENTRY", "delete from FORMULA_DEPLOYMENT_ENTRY FormulaDeploymentEntry where exists (select * from FORMULA_DEPLOYMENT_ENTRY,FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA where     FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and FormulaDeploymentEntry.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID " }, { "FORMULA_DEPLOYMENT_DT", "delete from FORMULA_DEPLOYMENT_DT FormulaDeploymentDt where exists (select * from FORMULA_DEPLOYMENT_DT,FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA where     FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and FormulaDeploymentDt.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID " } };
/*      */ 
/*  884 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and CUBE_FORMULA.CUBE_FORMULA_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from CUBE_FORMULA,FINANCE_CUBE where 1=1 and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  CUBE_FORMULA.FINANCE_CUBE_ID ,CUBE_FORMULA.CUBE_FORMULA_ID";
/*      */   protected static final String SQL_GET_ALL = " from CUBE_FORMULA where    FINANCE_CUBE_ID = ? ";
/*      */   protected FormulaDeploymentLineDAO mFormulaDeploymentLineDAO;
/*      */   protected CubeFormulaEVO mDetails;
/*      */   private CLOB mFormulaTextClob;
/*      */ 
/*      */   public CubeFormulaDAO(Connection connection)
/*      */   {
/*   53 */     super(connection);
/*      */   }
/*      */ 
/*      */   public CubeFormulaDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public CubeFormulaDAO(DataSource ds)
/*      */   {
/*   69 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected CubeFormulaPK getPK()
/*      */   {
/*   77 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(CubeFormulaEVO details)
/*      */   {
/*   86 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private void selectLobs(CubeFormulaEVO evo_)
/*      */   {
/*  100 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  102 */     PreparedStatement stmt = null;
/*  103 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  107 */       stmt = getConnection().prepareStatement("select  FORMULA_TEXT from CUBE_FORMULA where    CUBE_FORMULA_ID = ? for update");
/*      */ 
/*  109 */       putEvoKeysToJdbc(evo_, stmt, 1);
/*      */ 
/*  111 */       resultSet = stmt.executeQuery();
/*      */ 
/*  113 */       int col = 1;
/*  114 */       while (resultSet.next())
/*      */       {
/*  116 */         this.mFormulaTextClob = ((CLOB)resultSet.getClob(col++));
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  121 */       throw handleSQLException(evo_.getPK(), "select  FORMULA_TEXT from CUBE_FORMULA where    CUBE_FORMULA_ID = ? for update", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  125 */       closeResultSet(resultSet);
/*  126 */       closeStatement(stmt);
/*      */ 
/*  128 */       if (timer != null)
/*  129 */         timer.logDebug("selectLobs", evo_.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void putLobs(CubeFormulaEVO evo_) throws SQLException
/*      */   {
/*  135 */     updateClob(this.mFormulaTextClob, evo_.getFormulaText());
/*      */   }
/*      */ 
/*      */   private CubeFormulaEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  154 */     int col = 1;
/*  155 */     this.mFormulaTextClob = ((CLOB)resultSet_.getClob(col++));
/*  156 */     CubeFormulaEVO evo = new CubeFormulaEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), clobToString(this.mFormulaTextClob), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), null);
/*      */ 
/*  167 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  168 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  169 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  170 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(CubeFormulaEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  175 */     int col = startCol_;
/*  176 */     stmt_.setInt(col++, evo_.getCubeFormulaId());
/*  177 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(CubeFormulaEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  182 */     int col = startCol_;
/*  183 */     stmt_.setInt(col++, evo_.getFinanceCubeId());
/*  184 */     stmt_.setString(col++, evo_.getVisId());
/*  185 */     stmt_.setString(col++, evo_.getDescription());
/*      */ 
/*  187 */     if (evo_.getDeploymentInd())
/*  188 */       stmt_.setString(col++, "Y");
/*      */     else
/*  190 */       stmt_.setString(col++, " ");
/*  191 */     stmt_.setInt(col++, evo_.getFormulaType());
/*  192 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  193 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  194 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  195 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(CubeFormulaPK pk)
/*      */     throws ValidationException
/*      */   {
/*  211 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  213 */     PreparedStatement stmt = null;
/*  214 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  218 */       stmt = getConnection().prepareStatement("select CUBE_FORMULA.FORMULA_TEXT,CUBE_FORMULA.CUBE_FORMULA_ID,CUBE_FORMULA.FINANCE_CUBE_ID,CUBE_FORMULA.VIS_ID,CUBE_FORMULA.DESCRIPTION,CUBE_FORMULA.DEPLOYMENT_IND,CUBE_FORMULA.FORMULA_TYPE,CUBE_FORMULA.UPDATED_BY_USER_ID,CUBE_FORMULA.UPDATED_TIME,CUBE_FORMULA.CREATED_TIME from CUBE_FORMULA where    CUBE_FORMULA_ID = ? ");
/*      */ 
/*  221 */       int col = 1;
/*  222 */       stmt.setInt(col++, pk.getCubeFormulaId());
/*      */ 
/*  224 */       resultSet = stmt.executeQuery();
/*      */ 
/*  226 */       if (!resultSet.next()) {
/*  227 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  230 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  231 */       if (this.mDetails.isModified())
/*  232 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  236 */       throw handleSQLException(pk, "select CUBE_FORMULA.FORMULA_TEXT,CUBE_FORMULA.CUBE_FORMULA_ID,CUBE_FORMULA.FINANCE_CUBE_ID,CUBE_FORMULA.VIS_ID,CUBE_FORMULA.DESCRIPTION,CUBE_FORMULA.DEPLOYMENT_IND,CUBE_FORMULA.FORMULA_TYPE,CUBE_FORMULA.UPDATED_BY_USER_ID,CUBE_FORMULA.UPDATED_TIME,CUBE_FORMULA.CREATED_TIME from CUBE_FORMULA where    CUBE_FORMULA_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  240 */       closeResultSet(resultSet);
/*  241 */       closeStatement(stmt);
/*  242 */       closeConnection();
/*      */ 
/*  244 */       if (timer != null)
/*  245 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  282 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  283 */     this.mDetails.postCreateInit();
/*      */ 
/*  285 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  290 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  291 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  292 */       stmt = getConnection().prepareStatement("insert into CUBE_FORMULA ( CUBE_FORMULA_ID,FINANCE_CUBE_ID,VIS_ID,DESCRIPTION,FORMULA_TEXT,DEPLOYMENT_IND,FORMULA_TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,empty_clob(),?,?,?,?,?)");
/*      */ 
/*  295 */       int col = 1;
/*  296 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  297 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  300 */       int resultCount = stmt.executeUpdate();
/*  301 */       if (resultCount != 1)
/*      */       {
/*  303 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  308 */       selectLobs(this.mDetails);
/*  309 */       this._log.debug("doCreate", "calling putLobs");
/*  310 */       putLobs(this.mDetails);
/*      */ 
/*  312 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  316 */       throw handleSQLException(this.mDetails.getPK(), "insert into CUBE_FORMULA ( CUBE_FORMULA_ID,FINANCE_CUBE_ID,VIS_ID,DESCRIPTION,FORMULA_TEXT,DEPLOYMENT_IND,FORMULA_TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,empty_clob(),?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  320 */       closeStatement(stmt);
/*  321 */       closeConnection();
/*      */ 
/*  323 */       if (timer != null) {
/*  324 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  330 */       getFormulaDeploymentLineDAO().update(this.mDetails.getDeploymentsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  336 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  363 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  367 */     PreparedStatement stmt = null;
/*      */ 
/*  369 */     boolean mainChanged = this.mDetails.isModified();
/*  370 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  374 */       if (getFormulaDeploymentLineDAO().update(this.mDetails.getDeploymentsMap())) {
/*  375 */         dependantChanged = true;
/*      */       }
/*  377 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  380 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  381 */         stmt = getConnection().prepareStatement("update CUBE_FORMULA set FINANCE_CUBE_ID = ?,VIS_ID = ?,DESCRIPTION = ?,DEPLOYMENT_IND = ?,FORMULA_TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CUBE_FORMULA_ID = ? ");
/*      */ 
/*  383 */         selectLobs(this.mDetails);
/*  384 */         putLobs(this.mDetails);
/*      */ 
/*  387 */         int col = 1;
/*  388 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  389 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  392 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  395 */         if (resultCount != 1) {
/*  396 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  399 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  408 */       throw handleSQLException(getPK(), "update CUBE_FORMULA set FINANCE_CUBE_ID = ?,VIS_ID = ?,DESCRIPTION = ?,DEPLOYMENT_IND = ?,FORMULA_TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CUBE_FORMULA_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  412 */       closeStatement(stmt);
/*  413 */       closeConnection();
/*      */ 
/*  415 */       if ((timer != null) && (
/*  416 */         (mainChanged) || (dependantChanged)))
/*  417 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllCubeFormulasELO getAllCubeFormulas()
/*      */   {
/*  463 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  464 */     PreparedStatement stmt = null;
/*  465 */     ResultSet resultSet = null;
/*  466 */     AllCubeFormulasELO results = new AllCubeFormulasELO();
/*      */     try
/*      */     {
/*  469 */       stmt = getConnection().prepareStatement(SQL_ALL_CUBE_FORMULAS);
/*  470 */       int col = 1;
/*  471 */       resultSet = stmt.executeQuery();
/*  472 */       while (resultSet.next())
/*      */       {
/*  474 */         col = 2;
/*      */ 
/*  477 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  480 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  482 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  485 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  488 */         CubeFormulaPK pkCubeFormula = new CubeFormulaPK(resultSet.getInt(col++));
/*      */ 
/*  491 */         String textCubeFormula = resultSet.getString(col++);
/*      */ 
/*  496 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/*  502 */         CubeFormulaCK ckCubeFormula = new CubeFormulaCK(pkModel, pkFinanceCube, pkCubeFormula);
/*      */ 
/*  509 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  515 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/*  521 */         CubeFormulaRefImpl erCubeFormula = new CubeFormulaRefImpl(ckCubeFormula, textCubeFormula);
/*      */ 
/*  526 */         String col1 = resultSet.getString(col++);
/*  527 */         String col2 = resultSet.getString(col++);
/*  528 */         int col3 = resultSet.getInt(col++);
/*  529 */         String col4 = resultSet.getString(col++);
/*  530 */         if (resultSet.wasNull()) {
/*  531 */           col4 = "";
/*      */         }
/*      */ 
/*  534 */         results.add(erCubeFormula, erFinanceCube, erModel, col1, col2, col3, col4.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  547 */       throw handleSQLException(SQL_ALL_CUBE_FORMULAS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  551 */       closeResultSet(resultSet);
/*  552 */       closeStatement(stmt);
/*  553 */       closeConnection();
/*      */     }
/*      */ 
/*  556 */     if (timer != null) {
/*  557 */       timer.logDebug("getAllCubeFormulas", " items=" + results.size());
/*      */     }
/*      */ 
/*  561 */     return results;
/*      */   }
/*      */ 
/*      */   public CubeFormulaeForFinanceCubeELO getCubeFormulaeForFinanceCube(int param1)
/*      */   {
/*  607 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  608 */     PreparedStatement stmt = null;
/*  609 */     ResultSet resultSet = null;
/*  610 */     CubeFormulaeForFinanceCubeELO results = new CubeFormulaeForFinanceCubeELO();
/*      */     try
/*      */     {
/*  613 */       stmt = getConnection().prepareStatement(SQL_CUBE_FORMULAE_FOR_FINANCE_CUBE);
/*  614 */       int col = 1;
/*  615 */       stmt.setInt(col++, param1);
/*  616 */       resultSet = stmt.executeQuery();
/*  617 */       while (resultSet.next())
/*      */       {
/*  619 */         col = 2;
/*      */ 
/*  622 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  625 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  627 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  630 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  633 */         CubeFormulaPK pkCubeFormula = new CubeFormulaPK(resultSet.getInt(col++));
/*      */ 
/*  636 */         String textCubeFormula = resultSet.getString(col++);
/*      */ 
/*  641 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/*  647 */         CubeFormulaCK ckCubeFormula = new CubeFormulaCK(pkModel, pkFinanceCube, pkCubeFormula);
/*      */ 
/*  654 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  660 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/*  666 */         CubeFormulaRefImpl erCubeFormula = new CubeFormulaRefImpl(ckCubeFormula, textCubeFormula);
/*      */ 
/*  671 */         String col1 = resultSet.getString(col++);
/*  672 */         String col2 = resultSet.getString(col++);
/*  673 */         int col3 = resultSet.getInt(col++);
/*  674 */         String col4 = resultSet.getString(col++);
/*  675 */         if (resultSet.wasNull()) {
/*  676 */           col4 = "";
/*      */         }
/*      */ 
/*  679 */         results.add(erCubeFormula, erFinanceCube, erModel, col1, col2, col3, col4.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  692 */       throw handleSQLException(SQL_CUBE_FORMULAE_FOR_FINANCE_CUBE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  696 */       closeResultSet(resultSet);
/*  697 */       closeStatement(stmt);
/*  698 */       closeConnection();
/*      */     }
/*      */ 
/*  701 */     if (timer != null) {
/*  702 */       timer.logDebug("getCubeFormulaeForFinanceCube", " FinanceCubeId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  707 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  724 */     if (items == null) {
/*  725 */       return false;
/*      */     }
/*  727 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  728 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  730 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  734 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  735 */       while (iter3.hasNext())
/*      */       {
/*  737 */         this.mDetails = ((CubeFormulaEVO)iter3.next());
/*  738 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  740 */         somethingChanged = true;
/*      */ 
/*  743 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  747 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  748 */       while (iter2.hasNext())
/*      */       {
/*  750 */         this.mDetails = ((CubeFormulaEVO)iter2.next());
/*      */ 
/*  753 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  755 */         somethingChanged = true;
/*      */ 
/*  758 */         if (deleteStmt == null) {
/*  759 */           deleteStmt = getConnection().prepareStatement("delete from CUBE_FORMULA where    CUBE_FORMULA_ID = ? ");
/*      */         }
/*      */ 
/*  762 */         int col = 1;
/*  763 */         deleteStmt.setInt(col++, this.mDetails.getCubeFormulaId());
/*      */ 
/*  765 */         if (this._log.isDebugEnabled()) {
/*  766 */           this._log.debug("update", "CubeFormula deleting CubeFormulaId=" + this.mDetails.getCubeFormulaId());
/*      */         }
/*      */ 
/*  771 */         deleteStmt.addBatch();
/*      */ 
/*  774 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  779 */       if (deleteStmt != null)
/*      */       {
/*  781 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  783 */         deleteStmt.executeBatch();
/*      */ 
/*  785 */         if (timer2 != null) {
/*  786 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  790 */       Iterator iter1 = items.values().iterator();
/*  791 */       while (iter1.hasNext())
/*      */       {
/*  793 */         this.mDetails = ((CubeFormulaEVO)iter1.next());
/*      */ 
/*  795 */         if (this.mDetails.insertPending())
/*      */         {
/*  797 */           somethingChanged = true;
/*  798 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  801 */         if (this.mDetails.isModified())
/*      */         {
/*  803 */           somethingChanged = true;
/*  804 */           doStore(); continue;
/*      */         }
/*      */ 
/*  808 */         if ((this.mDetails.deletePending()) || 
/*  814 */           (!getFormulaDeploymentLineDAO().update(this.mDetails.getDeploymentsMap()))) continue;
/*  815 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  827 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  831 */       throw handleSQLException("delete from CUBE_FORMULA where    CUBE_FORMULA_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  835 */       if (deleteStmt != null)
/*      */       {
/*  837 */         closeStatement(deleteStmt);
/*  838 */         closeConnection();
/*      */       }
/*      */ 
/*  841 */       this.mDetails = null;
/*      */ 
/*  843 */       if ((somethingChanged) && 
/*  844 */         (timer != null))
/*  845 */         timer.logDebug("update", "collection"); 
/*  845 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(CubeFormulaPK pk)
/*      */   {
/*  893 */     Set emptyStrings = Collections.emptySet();
/*  894 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(CubeFormulaPK pk, Set<String> exclusionTables)
/*      */   {
/*  900 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  902 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  904 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  906 */       PreparedStatement stmt = null;
/*      */ 
/*  908 */       int resultCount = 0;
/*  909 */       String s = null;
/*      */       try
/*      */       {
/*  912 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  914 */         if (this._log.isDebugEnabled()) {
/*  915 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  917 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  920 */         int col = 1;
/*  921 */         stmt.setInt(col++, pk.getCubeFormulaId());
/*      */ 
/*  924 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  928 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  932 */         closeStatement(stmt);
/*  933 */         closeConnection();
/*      */ 
/*  935 */         if (timer != null) {
/*  936 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  940 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  942 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  944 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  946 */       PreparedStatement stmt = null;
/*      */ 
/*  948 */       int resultCount = 0;
/*  949 */       String s = null;
/*      */       try
/*      */       {
/*  952 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  954 */         if (this._log.isDebugEnabled()) {
/*  955 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  957 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  960 */         int col = 1;
/*  961 */         stmt.setInt(col++, pk.getCubeFormulaId());
/*      */ 
/*  964 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  968 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  972 */         closeStatement(stmt);
/*  973 */         closeConnection();
/*      */ 
/*  975 */         if (timer != null)
/*  976 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*      */   {
/* 1000 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1002 */     PreparedStatement stmt = null;
/* 1003 */     ResultSet resultSet = null;
/*      */ 
/* 1005 */     int itemCount = 0;
/*      */ 
/* 1007 */     FinanceCubeEVO owningEVO = null;
/* 1008 */     Iterator ownersIter = owners.iterator();
/* 1009 */     while (ownersIter.hasNext())
/*      */     {
/* 1011 */       owningEVO = (FinanceCubeEVO)ownersIter.next();
/* 1012 */       owningEVO.setCubeFormulaAllItemsLoaded(true);
/*      */     }
/* 1014 */     ownersIter = owners.iterator();
/* 1015 */     owningEVO = (FinanceCubeEVO)ownersIter.next();
/* 1016 */     Collection theseItems = null;
/*      */     try
/*      */     {
/* 1020 */       stmt = getConnection().prepareStatement("select CUBE_FORMULA.FORMULA_TEXT,CUBE_FORMULA.CUBE_FORMULA_ID,CUBE_FORMULA.FINANCE_CUBE_ID,CUBE_FORMULA.VIS_ID,CUBE_FORMULA.DESCRIPTION,CUBE_FORMULA.DEPLOYMENT_IND,CUBE_FORMULA.FORMULA_TYPE,CUBE_FORMULA.UPDATED_BY_USER_ID,CUBE_FORMULA.UPDATED_TIME,CUBE_FORMULA.CREATED_TIME from CUBE_FORMULA,FINANCE_CUBE where 1=1 and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  CUBE_FORMULA.FINANCE_CUBE_ID ,CUBE_FORMULA.CUBE_FORMULA_ID");
/*      */ 
/* 1022 */       int col = 1;
/* 1023 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/* 1025 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1028 */       while (resultSet.next())
/*      */       {
/* 1030 */         itemCount++;
/* 1031 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1036 */         while (this.mDetails.getFinanceCubeId() != owningEVO.getFinanceCubeId())
/*      */         {
/* 1040 */           if (!ownersIter.hasNext())
/*      */           {
/* 1042 */             this._log.debug("bulkGetAll", "can't find owning [FinanceCubeId=" + this.mDetails.getFinanceCubeId() + "] for " + this.mDetails.getPK());
/*      */ 
/* 1046 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 1047 */             ownersIter = owners.iterator();
/* 1048 */             while (ownersIter.hasNext())
/*      */             {
/* 1050 */               owningEVO = (FinanceCubeEVO)ownersIter.next();
/* 1051 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/* 1053 */             throw new IllegalStateException("can't find owner");
/*      */           }
/* 1055 */           owningEVO = (FinanceCubeEVO)ownersIter.next();
/*      */         }
/* 1057 */         if (owningEVO.getCubeFormula() == null)
/*      */         {
/* 1059 */           theseItems = new ArrayList();
/* 1060 */           owningEVO.setCubeFormula(theseItems);
/* 1061 */           owningEVO.setCubeFormulaAllItemsLoaded(true);
/*      */         }
/* 1063 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/* 1066 */       if (timer != null) {
/* 1067 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/* 1070 */       if ((itemCount > 0) && (dependants.indexOf("<5>") > -1))
/*      */       {
/* 1072 */         getFormulaDeploymentLineDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/* 1076 */       throw handleSQLException("select CUBE_FORMULA.FORMULA_TEXT,CUBE_FORMULA.CUBE_FORMULA_ID,CUBE_FORMULA.FINANCE_CUBE_ID,CUBE_FORMULA.VIS_ID,CUBE_FORMULA.DESCRIPTION,CUBE_FORMULA.DEPLOYMENT_IND,CUBE_FORMULA.FORMULA_TYPE,CUBE_FORMULA.UPDATED_BY_USER_ID,CUBE_FORMULA.UPDATED_TIME,CUBE_FORMULA.CREATED_TIME from CUBE_FORMULA,FINANCE_CUBE where 1=1 and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  CUBE_FORMULA.FINANCE_CUBE_ID ,CUBE_FORMULA.CUBE_FORMULA_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1080 */       closeResultSet(resultSet);
/* 1081 */       closeStatement(stmt);
/* 1082 */       closeConnection();
/*      */ 
/* 1084 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectFinanceCubeId, String dependants, Collection currentList)
/*      */   {
/* 1109 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1110 */     PreparedStatement stmt = null;
/* 1111 */     ResultSet resultSet = null;
/*      */ 
/* 1113 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/* 1117 */       stmt = getConnection().prepareStatement("select CUBE_FORMULA.FORMULA_TEXT,CUBE_FORMULA.CUBE_FORMULA_ID,CUBE_FORMULA.FINANCE_CUBE_ID,CUBE_FORMULA.VIS_ID,CUBE_FORMULA.DESCRIPTION,CUBE_FORMULA.DEPLOYMENT_IND,CUBE_FORMULA.FORMULA_TYPE,CUBE_FORMULA.UPDATED_BY_USER_ID,CUBE_FORMULA.UPDATED_TIME,CUBE_FORMULA.CREATED_TIME from CUBE_FORMULA where    FINANCE_CUBE_ID = ? ");
/*      */ 
/* 1119 */       int col = 1;
/* 1120 */       stmt.setInt(col++, selectFinanceCubeId);
/*      */ 
/* 1122 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1124 */       while (resultSet.next())
/*      */       {
/* 1126 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1129 */         getDependants(this.mDetails, dependants);
/*      */ 
/* 1132 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1135 */       if (currentList != null)
/*      */       {
/* 1138 */         ListIterator iter = items.listIterator();
/* 1139 */         CubeFormulaEVO currentEVO = null;
/* 1140 */         CubeFormulaEVO newEVO = null;
/* 1141 */         while (iter.hasNext())
/*      */         {
/* 1143 */           newEVO = (CubeFormulaEVO)iter.next();
/* 1144 */           Iterator iter2 = currentList.iterator();
/* 1145 */           while (iter2.hasNext())
/*      */           {
/* 1147 */             currentEVO = (CubeFormulaEVO)iter2.next();
/* 1148 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1150 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1156 */         Iterator iter2 = currentList.iterator();
/* 1157 */         while (iter2.hasNext())
/*      */         {
/* 1159 */           currentEVO = (CubeFormulaEVO)iter2.next();
/* 1160 */           if (currentEVO.insertPending()) {
/* 1161 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1165 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1169 */       throw handleSQLException("select CUBE_FORMULA.FORMULA_TEXT,CUBE_FORMULA.CUBE_FORMULA_ID,CUBE_FORMULA.FINANCE_CUBE_ID,CUBE_FORMULA.VIS_ID,CUBE_FORMULA.DESCRIPTION,CUBE_FORMULA.DEPLOYMENT_IND,CUBE_FORMULA.FORMULA_TYPE,CUBE_FORMULA.UPDATED_BY_USER_ID,CUBE_FORMULA.UPDATED_TIME,CUBE_FORMULA.CREATED_TIME from CUBE_FORMULA where    FINANCE_CUBE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1173 */       closeResultSet(resultSet);
/* 1174 */       closeStatement(stmt);
/* 1175 */       closeConnection();
/*      */ 
/* 1177 */       if (timer != null) {
/* 1178 */         timer.logDebug("getAll", " FinanceCubeId=" + selectFinanceCubeId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1183 */     return items;
/*      */   }
/*      */ 
/*      */   public CubeFormulaEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1204 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1207 */     if (this.mDetails == null) {
/* 1208 */       doLoad(((CubeFormulaCK)paramCK).getCubeFormulaPK());
/*      */     }
/* 1210 */     else if (!this.mDetails.getPK().equals(((CubeFormulaCK)paramCK).getCubeFormulaPK())) {
/* 1211 */       doLoad(((CubeFormulaCK)paramCK).getCubeFormulaPK());
/*      */     }
/*      */ 
/* 1214 */     if ((dependants.indexOf("<5>") > -1) && (!this.mDetails.isDeploymentsAllItemsLoaded()))
/*      */     {
/* 1219 */       this.mDetails.setDeployments(getFormulaDeploymentLineDAO().getAll(this.mDetails.getCubeFormulaId(), dependants, this.mDetails.getDeployments()));
/*      */ 
/* 1226 */       this.mDetails.setDeploymentsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1229 */     if ((paramCK instanceof FormulaDeploymentLineCK))
/*      */     {
/* 1231 */       if (this.mDetails.getDeployments() == null) {
/* 1232 */         this.mDetails.loadDeploymentsItem(getFormulaDeploymentLineDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1235 */         FormulaDeploymentLinePK pk = ((FormulaDeploymentLineCK)paramCK).getFormulaDeploymentLinePK();
/* 1236 */         FormulaDeploymentLineEVO evo = this.mDetails.getDeploymentsItem(pk);
/* 1237 */         if (evo == null)
/* 1238 */           this.mDetails.loadDeploymentsItem(getFormulaDeploymentLineDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1240 */           getFormulaDeploymentLineDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1245 */     CubeFormulaEVO details = new CubeFormulaEVO();
/* 1246 */     details = this.mDetails.deepClone();
/*      */ 
/* 1248 */     if (timer != null) {
/* 1249 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1251 */     return details;
/*      */   }
/*      */ 
/*      */   public CubeFormulaEVO getDetails(ModelCK paramCK, CubeFormulaEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1257 */     CubeFormulaEVO savedEVO = this.mDetails;
/* 1258 */     this.mDetails = paramEVO;
/* 1259 */     CubeFormulaEVO newEVO = getDetails(paramCK, dependants);
/* 1260 */     this.mDetails = savedEVO;
/* 1261 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public CubeFormulaEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1267 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1271 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1274 */     CubeFormulaEVO details = this.mDetails.deepClone();
/*      */ 
/* 1276 */     if (timer != null) {
/* 1277 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1279 */     return details;
/*      */   }
/*      */ 
/*      */   protected FormulaDeploymentLineDAO getFormulaDeploymentLineDAO()
/*      */   {
/* 1288 */     if (this.mFormulaDeploymentLineDAO == null)
/*      */     {
/* 1290 */       if (this.mDataSource != null)
/* 1291 */         this.mFormulaDeploymentLineDAO = new FormulaDeploymentLineDAO(this.mDataSource);
/*      */       else {
/* 1293 */         this.mFormulaDeploymentLineDAO = new FormulaDeploymentLineDAO(getConnection());
/*      */       }
/*      */     }
/* 1296 */     return this.mFormulaDeploymentLineDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1301 */     return "CubeFormula";
/*      */   }
/*      */ 
/*      */   public CubeFormulaRefImpl getRef(CubeFormulaPK paramCubeFormulaPK)
/*      */   {
/* 1306 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1307 */     PreparedStatement stmt = null;
/* 1308 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1311 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID,CUBE_FORMULA.VIS_ID from CUBE_FORMULA,MODEL,FINANCE_CUBE where 1=1 and CUBE_FORMULA.CUBE_FORMULA_ID = ? and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID");
/* 1312 */       int col = 1;
/* 1313 */       stmt.setInt(col++, paramCubeFormulaPK.getCubeFormulaId());
/*      */ 
/* 1315 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1317 */       if (!resultSet.next()) {
/* 1318 */         throw new RuntimeException(getEntityName() + " getRef " + paramCubeFormulaPK + " not found");
/*      */       }
/* 1320 */       col = 2;
/* 1321 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1325 */       FinanceCubePK newFinanceCubePK = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/* 1329 */       String textCubeFormula = resultSet.getString(col++);
/* 1330 */       CubeFormulaCK ckCubeFormula = new CubeFormulaCK(newModelPK, newFinanceCubePK, paramCubeFormulaPK);
/*      */ 
/* 1336 */       CubeFormulaRefImpl localCubeFormulaRefImpl = new CubeFormulaRefImpl(ckCubeFormula, textCubeFormula);
/*      */       return localCubeFormulaRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1341 */       throw handleSQLException(paramCubeFormulaPK, "select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID,CUBE_FORMULA.VIS_ID from CUBE_FORMULA,MODEL,FINANCE_CUBE where 1=1 and CUBE_FORMULA.CUBE_FORMULA_ID = ? and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1345 */       closeResultSet(resultSet);
/* 1346 */       closeStatement(stmt);
/* 1347 */       closeConnection();
/*      */ 
/* 1349 */       if (timer != null)
/* 1350 */         timer.logDebug("getRef", paramCubeFormulaPK); 
/* 1350 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1362 */     if (c == null)
/* 1363 */       return;
/* 1364 */     Iterator iter = c.iterator();
/* 1365 */     while (iter.hasNext())
/*      */     {
/* 1367 */       CubeFormulaEVO evo = (CubeFormulaEVO)iter.next();
/* 1368 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(CubeFormulaEVO evo, String dependants)
/*      */   {
/* 1382 */     if (evo.insertPending()) {
/* 1383 */       return;
/*      */     }
/* 1385 */     if (evo.getCubeFormulaId() < 1) {
/* 1386 */       return;
/*      */     }
/*      */ 
/* 1390 */     if ((dependants.indexOf("<5>") > -1) || (dependants.indexOf("<6>") > -1) || (dependants.indexOf("<7>") > -1))
/*      */     {
/* 1395 */       if (!evo.isDeploymentsAllItemsLoaded())
/*      */       {
/* 1397 */         evo.setDeployments(getFormulaDeploymentLineDAO().getAll(evo.getCubeFormulaId(), dependants, evo.getDeployments()));
/*      */ 
/* 1404 */         evo.setDeploymentsAllItemsLoaded(true);
/*      */       }
/* 1406 */       getFormulaDeploymentLineDAO().getDependants(evo.getDeployments(), dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateDeployedIndicator(int cubeFormulaId, boolean deployed)
/*      */   {
/* 1424 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/* 1428 */       ps = getConnection().prepareStatement("update cube_formula set deployment_ind = ? where cube_formula_id = ?");
/*      */ 
/* 1430 */       ps.setString(1, deployed ? "Y" : " ");
/* 1431 */       ps.setInt(2, cubeFormulaId);
/*      */ 
/* 1433 */       int rows = ps.executeUpdate();
/*      */ 
/* 1435 */       if (rows != 1) {
/* 1436 */         throw new IllegalStateException("Failed to update deployment_ind for formula id : " + cubeFormulaId);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1441 */       throw handleSQLException("update cube formula deployment_ind", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1445 */       closeStatement(ps);
/* 1446 */       closeConnection();
/*      */     }
/*      */   }
/*      */
			public AllCubeFormulasELO getAllCubeFormulasForLoggedUser(int userId) {
			   Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  464 */     PreparedStatement stmt = null;
/*  465 */     ResultSet resultSet = null;
/*  466 */     AllCubeFormulasELO results = new AllCubeFormulasELO();
/*      */     try
/*      */     {
/*  469 */       stmt = getConnection().prepareStatement(SQL_ALL_CUBE_FORMULAS_FOR_USER);
/*  470 */       int col = 1;
				 stmt.setInt(1, userId);
/*  471 */       resultSet = stmt.executeQuery();
/*  472 */       while (resultSet.next())
/*      */       {
/*  474 */         col = 2;
/*      */ 
/*  477 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  480 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  482 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  485 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  488 */         CubeFormulaPK pkCubeFormula = new CubeFormulaPK(resultSet.getInt(col++));
/*      */ 
/*  491 */         String textCubeFormula = resultSet.getString(col++);
/*      */ 
/*  496 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/*  502 */         CubeFormulaCK ckCubeFormula = new CubeFormulaCK(pkModel, pkFinanceCube, pkCubeFormula);
/*      */ 
/*  509 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  515 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/*  521 */         CubeFormulaRefImpl erCubeFormula = new CubeFormulaRefImpl(ckCubeFormula, textCubeFormula);
/*      */ 
/*  526 */         String col1 = resultSet.getString(col++);
/*  527 */         String col2 = resultSet.getString(col++);
/*  528 */         int col3 = resultSet.getInt(col++);
/*  529 */         String col4 = resultSet.getString(col++);
/*  530 */         if (resultSet.wasNull()) {
/*  531 */           col4 = "";
/*      */         }
/*      */ 
/*  534 */         results.add(erCubeFormula, erFinanceCube, erModel, col1, col2, col3, col4.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  547 */       throw handleSQLException(SQL_ALL_CUBE_FORMULAS_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  551 */       closeResultSet(resultSet);
/*  552 */       closeStatement(stmt);
/*  553 */       closeConnection();
/*      */     }
/*      */ 
/*  556 */     if (timer != null) {
/*  557 */       timer.logDebug("getAllCubeFormulasForLoggedUser", " items=" + results.size());
/*      */     }
/*      */ 
/*  561 */     return results;
			}
}

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cubeformula.CubeFormulaDAO
 * JD-Core Version:    0.6.0
 */