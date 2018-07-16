/*      */ package com.cedar.cp.ejb.impl.model;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.cubeformula.CubeFormulaCK;
/*      */ import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
/*      */ import com.cedar.cp.dto.cubeformula.CubeFormulaPackageCK;
/*      */ import com.cedar.cp.dto.cubeformula.CubeFormulaPackagePK;
/*      */ import com.cedar.cp.dto.dimension.DimensionPK;
/*      */ import com.cedar.cp.dto.dimension.DimensionRefImpl;
/*      */ import com.cedar.cp.dto.dimension.HierarchyPK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyRefImpl;
/*      */ import com.cedar.cp.dto.model.AllDataTypesAttachedToFinanceCubeForModelELO;
/*      */ import com.cedar.cp.dto.model.AllFinanceCubesELO;
/*      */ import com.cedar.cp.dto.model.AllFinanceCubesWebELO;
/*      */ import com.cedar.cp.dto.model.AllFinanceCubesWebForModelELO;
/*      */ import com.cedar.cp.dto.model.AllFinanceCubesWebForUserELO;
/*      */ import com.cedar.cp.dto.model.AllSimpleFinanceCubesELO;
/*      */ import com.cedar.cp.dto.model.FinanceCubeAllDimensionsAndHierachiesELO;
/*      */ import com.cedar.cp.dto.model.FinanceCubeCK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeDataTypeCK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeDataTypePK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeDataTypeRefImpl;
/*      */ import com.cedar.cp.dto.model.FinanceCubeDetailsELO;
/*      */ import com.cedar.cp.dto.model.FinanceCubeDimensionsAndHierachiesELO;
/*      */ import com.cedar.cp.dto.model.FinanceCubePK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeRefImpl;
/*      */ import com.cedar.cp.dto.model.FinanceCubesForModelELO;
/*      */ import com.cedar.cp.dto.model.FinanceCubesUsingDataTypeELO;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelDimensionRelPK;
/*      */ import com.cedar.cp.dto.model.ModelDimensionRelRefImpl;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.RollUpRuleCK;
/*      */ import com.cedar.cp.dto.model.RollUpRulePK;
/*      */ import com.cedar.cp.dto.model.budgetlimit.BudgetLimitCK;
/*      */ import com.cedar.cp.dto.model.budgetlimit.BudgetLimitPK;
/*      */ import com.cedar.cp.ejb.base.cube.formula.FinanceCubeInfo;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaDAO;
/*      */ import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaEVO;
/*      */ import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaPackageDAO;
/*      */ import com.cedar.cp.ejb.impl.cubeformula.CubeFormulaPackageEVO;
/*      */ import com.cedar.cp.ejb.impl.model.budgetlimit.BudgetLimitDAO;
/*      */ import com.cedar.cp.ejb.impl.model.budgetlimit.BudgetLimitEVO;
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
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class FinanceCubeDAO extends AbstractDAO
/*      */ {
/*   82 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select FINANCE_CUBE.FINANCE_CUBE_ID,FINANCE_CUBE.MODEL_ID,FINANCE_CUBE.VIS_ID,FINANCE_CUBE.DESCRIPTION,FINANCE_CUBE.LOCKED_BY_TASK_ID,FINANCE_CUBE.HAS_DATA,FINANCE_CUBE.AUDITED,FINANCE_CUBE.CUBE_FORMULA_ENABLED,FINANCE_CUBE.VERSION_NUM,FINANCE_CUBE.UPDATED_BY_USER_ID,FINANCE_CUBE.UPDATED_TIME,FINANCE_CUBE.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from FINANCE_CUBE where    FINANCE_CUBE_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into FINANCE_CUBE ( FINANCE_CUBE_ID,MODEL_ID,VIS_ID,DESCRIPTION,LOCKED_BY_TASK_ID,HAS_DATA,AUDITED,CUBE_FORMULA_ENABLED,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_FINANCECUBENAME = "select count(*) from FINANCE_CUBE where    MODEL_ID = ? AND VIS_ID = ? and not(    FINANCE_CUBE_ID = ? )";
/*      */   protected static final String SQL_STORE = "update FINANCE_CUBE set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,LOCKED_BY_TASK_ID = ?,HAS_DATA = ?,AUDITED = ?,CUBE_FORMULA_ENABLED = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    FINANCE_CUBE_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from FINANCE_CUBE where FINANCE_CUBE_ID = ?";
/*  566 */   protected static String SQL_ALL_FINANCE_CUBES = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.DESCRIPTION      ,FINANCE_CUBE.LOCKED_BY_TASK_ID      ,FINANCE_CUBE.HAS_DATA      ,(select 'Y' from dual where exists(select 1 from AMM_FINANCE_CUBE where AMM_FINANCE_CUBE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID))      ,(select 'Y' from dual where exists(select 1 from AMM_FINANCE_CUBE where AMM_FINANCE_CUBE.SRC_FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID)) from FINANCE_CUBE    ,MODEL where 1=1   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID, FINANCE_CUBE.VIS_ID";
/*      */   protected static String SQL_ALL_FINANCE_CUBES_FOR_USER = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.DESCRIPTION      ,FINANCE_CUBE.LOCKED_BY_TASK_ID      ,FINANCE_CUBE.HAS_DATA      ,(select 'Y' from dual where exists(select 1 from AMM_FINANCE_CUBE where AMM_FINANCE_CUBE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID))      ,(select 'Y' from dual where exists(select 1 from AMM_FINANCE_CUBE where AMM_FINANCE_CUBE.SRC_FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID)) from FINANCE_CUBE    ,MODEL where model.model_id in (select distinct model_id from budget_user where user_id = ?)  and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID, FINANCE_CUBE.VIS_ID";
/*  686 */   protected static String SQL_ALL_SIMPLE_FINANCE_CUBES = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID from FINANCE_CUBE    ,MODEL where model.model_id in (select distinct model_id from budget_user where user_id = ?) and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  order by FINANCE_CUBE.VIS_ID";
/*      */ 
/*  792 */   protected static String SQL_ALL_DATA_TYPES_ATTACHED_TO_FINANCE_CUBE_FOR_MODEL = "select distinct 0       ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID from FINANCE_CUBE    ,MODEL    ,DATA_TYPE    ,FINANCE_CUBE_DATA_TYPE where FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  FINANCE_CUBE.MODEL_ID = ? and FINANCE_CUBE.FINANCE_CUBE_ID = FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID and FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID = DATA_TYPE.DATA_TYPE_ID and ( DATA_TYPE.SUB_TYPE = 0 or DATA_TYPE.SUB_TYPE = 4 )";
/*      */ 
/*  865 */   protected static String SQL_FINANCE_CUBES_FOR_MODEL = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.DESCRIPTION      ,FINANCE_CUBE.LOCKED_BY_TASK_ID      ,FINANCE_CUBE.HAS_DATA      ,FINANCE_CUBE.CUBE_FORMULA_ENABLED from FINANCE_CUBE    ,MODEL where 1=1   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  FINANCE_CUBE.MODEL_ID = ? order by FINANCE_CUBE.VIS_ID";
/*      */ 
/*  991 */   protected static String SQL_FINANCE_CUBE_DIMENSIONS_AND_HIERACHIES = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,( select count(*) from hierarchy where dimension_id = dimension.dimension_id ) HIERARCHY_COUNT      ,decode( HIERARCHY.HIERARCHY_ID, null, 'N', 'Y' ) RESP_DIM from FINANCE_CUBE    ,MODEL    ,MODEL_DIMENSION_REL    ,DIMENSION    ,HIERARCHY where 1=1   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  FINANCE_CUBE.FINANCE_CUBE_ID = ? and MODEL.MODEL_ID = FINANCE_CUBE.MODEL_ID and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID and MODEL_DIMENSION_REL.DIMENSION_ID = DIMENSION.DIMENSION_ID and HIERARCHY.HIERARCHY_ID (+) = MODEL.BUDGET_HIERARCHY_ID order by MODEL_DIMENSION_REL.DIMENSION_SEQ_NUM";
/*      */ 
/* 1156 */   protected static String SQL_FINANCE_CUBE_ALL_DIMENSIONS_AND_HIERACHIES = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID from FINANCE_CUBE    ,MODEL    ,MODEL_DIMENSION_REL    ,DIMENSION    ,HIERARCHY where 1=1   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  FINANCE_CUBE.FINANCE_CUBE_ID = ? and MODEL.MODEL_ID = FINANCE_CUBE.MODEL_ID and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID and MODEL_DIMENSION_REL.DIMENSION_ID = DIMENSION.DIMENSION_ID and HIERARCHY.DIMENSION_ID (+) = MODEL_DIMENSION_REL.DIMENSION_ID order by MODEL_DIMENSION_REL.DIMENSION_SEQ_NUM,HIERARCHY.VIS_ID";
/*      */ 
/* 1315 */   protected static String SQL_ALL_FINANCE_CUBES_WEB = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.MODEL_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.DESCRIPTION from FINANCE_CUBE    ,MODEL where 1=1   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  order by FINANCE_CUBE.FINANCE_CUBE_ID";
/*      */ 
/* 1430 */   protected static String SQL_ALL_FINANCE_CUBES_WEB_FOR_MODEL = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.DESCRIPTION from FINANCE_CUBE    ,MODEL where 1=1   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  FINANCE_CUBE.MODEL_ID = ?";
/*      */ 
/* 1546 */   protected static String SQL_ALL_FINANCE_CUBES_WEB_FOR_USER = "select distinct 0       ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.DESCRIPTION from FINANCE_CUBE    ,MODEL    ,BUDGET_USER where 1=1   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID and BUDGET_USER.MODEL_ID = MODEL.MODEL_ID AND BUDGET_USER.USER_ID = ?";
/*      */ 
/* 1620 */   protected static String SQL_FINANCE_CUBE_DETAILS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.MODEL_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.DESCRIPTION      ,FINANCE_CUBE.CUBE_FORMULA_ENABLED from FINANCE_CUBE    ,MODEL where 1=1   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  FINANCE_CUBE_ID = ?";
/*      */ 
/* 1744 */   protected static String SQL_FINANCE_CUBES_USING_DATA_TYPE = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID      ,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID      ,FINANCE_CUBE.MODEL_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.DESCRIPTION from FINANCE_CUBE    ,MODEL    ,FINANCE_CUBE_DATA_TYPE where 1=1   and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  and  FINANCE_CUBE.FINANCE_CUBE_ID = FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID and FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID = ?";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from FINANCE_CUBE where    FINANCE_CUBE_ID = ? ";
/* 2029 */   private static String[][] SQL_DELETE_CHILDREN = { { "FINANCE_CUBE_DATA_TYPE", "delete from FINANCE_CUBE_DATA_TYPE where     FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID = ? " }, { "BUDGET_LIMIT", "delete from BUDGET_LIMIT where     BUDGET_LIMIT.FINANCE_CUBE_ID = ? " }, { "ROLL_UP_RULE", "delete from ROLL_UP_RULE where     ROLL_UP_RULE.FINANCE_CUBE_ID = ? " }, { "CUBE_FORMULA", "delete from CUBE_FORMULA where     CUBE_FORMULA.FINANCE_CUBE_ID = ? " }, { "CUBE_FORMULA_PACKAGE", "delete from CUBE_FORMULA_PACKAGE where     CUBE_FORMULA_PACKAGE.FINANCE_CUBE_ID = ? " } };
/*      */ 
/* 2058 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "FORMULA_DEPLOYMENT_LINE", "delete from FORMULA_DEPLOYMENT_LINE FormulaDeploymentLine where exists (select * from FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA,FINANCE_CUBE where     FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FormulaDeploymentLine.CUBE_FORMULA_ID = FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID " }, { "FORMULA_DEPLOYMENT_ENTRY", "delete from FORMULA_DEPLOYMENT_ENTRY FormulaDeploymentEntry where exists (select * from FORMULA_DEPLOYMENT_ENTRY,FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA,FINANCE_CUBE where     FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FormulaDeploymentEntry.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID " }, { "FORMULA_DEPLOYMENT_DT", "delete from FORMULA_DEPLOYMENT_DT FormulaDeploymentDt where exists (select * from FORMULA_DEPLOYMENT_DT,FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA,FINANCE_CUBE where     FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FormulaDeploymentDt.FORMULA_DEPLOYMENT_LINE_ID = FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID " } };
/*      */ 
/* 2099 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and FINANCE_CUBE.FINANCE_CUBE_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from FINANCE_CUBE where 1=1 and FINANCE_CUBE.MODEL_ID = ? order by  FINANCE_CUBE.FINANCE_CUBE_ID";
/*      */   protected static final String SQL_GET_ALL = " from FINANCE_CUBE where    MODEL_ID = ? ";
/*      */   private static final String ROLL_UP_RULE_QUERY_SQL = "with my_model_dimension_rels as\n(\n  select fc.finance_cube_id, mdr.dimension_id, mdr.dimension_seq_num\n  from model_dimension_rel mdr, \n       finance_cube fc\n  where mdr.model_id = fc.model_id \n    and fc.finance_cube_id = ?\n  order by mdr.dimension_seq_num\n),\nnum_dims as\n(\n  select count(*) num_dims from my_model_dimension_rels\n)\nselect num_dims, fcdt.data_type_id, dt.vis_id, rur.roll_up\nfrom num_dims, \n     my_model_dimension_rels mdr, \n     finance_cube_data_type fcdt,\n     roll_up_rule rur,\n     data_type dt\nwhere rur.finance_cube_id = mdr.finance_cube_id \nand rur.dimension_id = mdr.dimension_id\nand rur.data_type_id = fcdt.data_type_id\nand rur.finance_cube_id = fcdt.finance_cube_id\nand dt.data_type_id = fcdt.data_type_id\norder by fcdt.data_type_id, mdr.dimension_seq_num";
/*      */   protected FinanceCubeDataTypeDAO mFinanceCubeDataTypeDAO;
/*      */   protected BudgetLimitDAO mBudgetLimitDAO;
/*      */   protected RollUpRuleDAO mRollUpRuleDAO;
/*      */   protected CubeFormulaDAO mCubeFormulaDAO;
/*      */   protected CubeFormulaPackageDAO mCubeFormulaPackageDAO;
/*      */   protected FinanceCubeEVO mDetails;
/*      */ 
/*      */   public FinanceCubeDAO(Connection connection)
/*      */   {
/*   89 */     super(connection);
/*      */   }
/*      */ 
/*      */   public FinanceCubeDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public FinanceCubeDAO(DataSource ds)
/*      */   {
/*  105 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected FinanceCubePK getPK()
/*      */   {
/*  113 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(FinanceCubeEVO details)
/*      */   {
/*  122 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private FinanceCubeEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  145 */     int col = 1;
/*  146 */     FinanceCubeEVO evo = new FinanceCubeEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), getWrappedIntegerFromJdbc(resultSet_, col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), null, null, null, null, null);
/*      */ 
/*  163 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  164 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  165 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  166 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(FinanceCubeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  171 */     int col = startCol_;
/*  172 */     stmt_.setInt(col++, evo_.getFinanceCubeId());
/*  173 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(FinanceCubeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  178 */     int col = startCol_;
/*  179 */     stmt_.setInt(col++, evo_.getModelId());
/*  180 */     stmt_.setString(col++, evo_.getVisId());
/*  181 */     stmt_.setString(col++, evo_.getDescription());
/*  182 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getLockedByTaskId());
/*  183 */     if (evo_.getHasData())
/*  184 */       stmt_.setString(col++, "Y");
/*      */     else
/*  186 */       stmt_.setString(col++, " ");
/*  187 */     if (evo_.getAudited())
/*  188 */       stmt_.setString(col++, "Y");
/*      */     else
/*  190 */       stmt_.setString(col++, " ");
/*  191 */     if (evo_.getCubeFormulaEnabled())
/*  192 */       stmt_.setString(col++, "Y");
/*      */     else
/*  194 */       stmt_.setString(col++, " ");
/*  195 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  196 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  197 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  198 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  199 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(FinanceCubePK pk)
/*      */     throws ValidationException
/*      */   {
/*  215 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  217 */     PreparedStatement stmt = null;
/*  218 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  222 */       stmt = getConnection().prepareStatement("select FINANCE_CUBE.FINANCE_CUBE_ID,FINANCE_CUBE.MODEL_ID,FINANCE_CUBE.VIS_ID,FINANCE_CUBE.DESCRIPTION,FINANCE_CUBE.LOCKED_BY_TASK_ID,FINANCE_CUBE.HAS_DATA,FINANCE_CUBE.AUDITED,FINANCE_CUBE.CUBE_FORMULA_ENABLED,FINANCE_CUBE.VERSION_NUM,FINANCE_CUBE.UPDATED_BY_USER_ID,FINANCE_CUBE.UPDATED_TIME,FINANCE_CUBE.CREATED_TIME from FINANCE_CUBE where    FINANCE_CUBE_ID = ? ");
/*      */ 
/*  225 */       int col = 1;
/*  226 */       stmt.setInt(col++, pk.getFinanceCubeId());
/*      */ 
/*  228 */       resultSet = stmt.executeQuery();
/*      */ 
/*  230 */       if (!resultSet.next()) {
/*  231 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  234 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  235 */       if (this.mDetails.isModified())
/*  236 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  240 */       throw handleSQLException(pk, "select FINANCE_CUBE.FINANCE_CUBE_ID,FINANCE_CUBE.MODEL_ID,FINANCE_CUBE.VIS_ID,FINANCE_CUBE.DESCRIPTION,FINANCE_CUBE.LOCKED_BY_TASK_ID,FINANCE_CUBE.HAS_DATA,FINANCE_CUBE.AUDITED,FINANCE_CUBE.CUBE_FORMULA_ENABLED,FINANCE_CUBE.VERSION_NUM,FINANCE_CUBE.UPDATED_BY_USER_ID,FINANCE_CUBE.UPDATED_TIME,FINANCE_CUBE.CREATED_TIME from FINANCE_CUBE where    FINANCE_CUBE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  244 */       closeResultSet(resultSet);
/*  245 */       closeStatement(stmt);
/*  246 */       closeConnection();
/*      */ 
/*  248 */       if (timer != null)
/*  249 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  290 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  291 */     this.mDetails.postCreateInit();
/*      */ 
/*  293 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  298 */       duplicateValueCheckFinanceCubeName();
/*      */ 
/*  300 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  301 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  302 */       stmt = getConnection().prepareStatement("insert into FINANCE_CUBE ( FINANCE_CUBE_ID,MODEL_ID,VIS_ID,DESCRIPTION,LOCKED_BY_TASK_ID,HAS_DATA,AUDITED,CUBE_FORMULA_ENABLED,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  305 */       int col = 1;
/*  306 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  307 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  310 */       int resultCount = stmt.executeUpdate();
/*  311 */       if (resultCount != 1)
/*      */       {
/*  313 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  316 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  320 */       throw handleSQLException(this.mDetails.getPK(), "insert into FINANCE_CUBE ( FINANCE_CUBE_ID,MODEL_ID,VIS_ID,DESCRIPTION,LOCKED_BY_TASK_ID,HAS_DATA,AUDITED,CUBE_FORMULA_ENABLED,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  324 */       closeStatement(stmt);
/*  325 */       closeConnection();
/*      */ 
/*  327 */       if (timer != null) {
/*  328 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  334 */       getFinanceCubeDataTypeDAO().update(this.mDetails.getFinanceCubeDataTypesMap());
/*      */ 
/*  336 */       getBudgetLimitDAO().update(this.mDetails.getBudgetLimitsMap());
/*      */ 
/*  338 */       getRollUpRuleDAO().update(this.mDetails.getRollUpRulesMap());
/*      */ 
/*  340 */       getCubeFormulaDAO().update(this.mDetails.getCubeFormulaMap());
/*      */ 
/*  342 */       getCubeFormulaPackageDAO().update(this.mDetails.getCubeFormulaPackagesMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  348 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckFinanceCubeName()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  364 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  365 */     PreparedStatement stmt = null;
/*  366 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  370 */       stmt = getConnection().prepareStatement("select count(*) from FINANCE_CUBE where    MODEL_ID = ? AND VIS_ID = ? and not(    FINANCE_CUBE_ID = ? )");
/*      */ 
/*  373 */       int col = 1;
/*  374 */       stmt.setInt(col++, this.mDetails.getModelId());
/*  375 */       stmt.setString(col++, this.mDetails.getVisId());
/*  376 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  379 */       resultSet = stmt.executeQuery();
/*      */ 
/*  381 */       if (!resultSet.next()) {
/*  382 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  386 */       col = 1;
/*  387 */       int count = resultSet.getInt(col++);
/*  388 */       if (count > 0) {
/*  389 */         throw new DuplicateNameValidationException("Duplicate name (" + this.mDetails.getVisId() + "). Please re-name and retry");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  395 */       throw handleSQLException(getPK(), "select count(*) from FINANCE_CUBE where    MODEL_ID = ? AND VIS_ID = ? and not(    FINANCE_CUBE_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  399 */       closeResultSet(resultSet);
/*  400 */       closeStatement(stmt);
/*  401 */       closeConnection();
/*      */ 
/*  403 */       if (timer != null)
/*  404 */         timer.logDebug("duplicateValueCheckFinanceCubeName", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  434 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  438 */     PreparedStatement stmt = null;
/*      */ 
/*  440 */     boolean mainChanged = this.mDetails.isModified();
/*  441 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  445 */       if (mainChanged) {
/*  446 */         duplicateValueCheckFinanceCubeName();
/*      */       }
/*  448 */       if (getFinanceCubeDataTypeDAO().update(this.mDetails.getFinanceCubeDataTypesMap())) {
/*  449 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  452 */       if (getBudgetLimitDAO().update(this.mDetails.getBudgetLimitsMap())) {
/*  453 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  456 */       if (getRollUpRuleDAO().update(this.mDetails.getRollUpRulesMap())) {
/*  457 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  460 */       if (getCubeFormulaDAO().update(this.mDetails.getCubeFormulaMap())) {
/*  461 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  464 */       if (getCubeFormulaPackageDAO().update(this.mDetails.getCubeFormulaPackagesMap())) {
/*  465 */         dependantChanged = true;
/*      */       }
/*  467 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  470 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  473 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  474 */         stmt = getConnection().prepareStatement("update FINANCE_CUBE set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,LOCKED_BY_TASK_ID = ?,HAS_DATA = ?,AUDITED = ?,CUBE_FORMULA_ENABLED = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    FINANCE_CUBE_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  477 */         int col = 1;
/*  478 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  479 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  481 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  484 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  486 */         if (resultCount == 0) {
/*  487 */           checkVersionNum();
/*      */         }
/*  489 */         if (resultCount != 1) {
/*  490 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  493 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  502 */       throw handleSQLException(getPK(), "update FINANCE_CUBE set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,LOCKED_BY_TASK_ID = ?,HAS_DATA = ?,AUDITED = ?,CUBE_FORMULA_ENABLED = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    FINANCE_CUBE_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  506 */       closeStatement(stmt);
/*  507 */       closeConnection();
/*      */ 
/*  509 */       if ((timer != null) && (
/*  510 */         (mainChanged) || (dependantChanged)))
/*  511 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  523 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  524 */     PreparedStatement stmt = null;
/*  525 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  529 */       stmt = getConnection().prepareStatement("select VERSION_NUM from FINANCE_CUBE where FINANCE_CUBE_ID = ?");
/*      */ 
/*  532 */       int col = 1;
/*  533 */       stmt.setInt(col++, this.mDetails.getFinanceCubeId());
/*      */ 
/*  536 */       resultSet = stmt.executeQuery();
/*      */ 
/*  538 */       if (!resultSet.next()) {
/*  539 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  542 */       col = 1;
/*  543 */       int dbVersionNumber = resultSet.getInt(col++);
/*  544 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  545 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  551 */       throw handleSQLException(getPK(), "select VERSION_NUM from FINANCE_CUBE where FINANCE_CUBE_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  555 */       closeStatement(stmt);
/*  556 */       closeResultSet(resultSet);
/*      */ 
/*  558 */       if (timer != null)
/*  559 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllFinanceCubesELO getAllFinanceCubes()
/*      */   {
/*  599 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  600 */     PreparedStatement stmt = null;
/*  601 */     ResultSet resultSet = null;
/*  602 */     AllFinanceCubesELO results = new AllFinanceCubesELO();
/*      */     try
/*      */     {
/*  605 */       stmt = getConnection().prepareStatement(SQL_ALL_FINANCE_CUBES);
/*  606 */       int col = 1;
/*  607 */       resultSet = stmt.executeQuery();
/*  608 */       while (resultSet.next())
/*      */       {
/*  610 */         col = 2;
/*      */ 
/*  613 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  616 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  619 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  622 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  627 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/*  633 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  639 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/*  644 */         String col1 = resultSet.getString(col++);
/*  645 */         Integer col2 = getWrappedIntegerFromJdbc(resultSet, col++);
/*  646 */         String col3 = resultSet.getString(col++);
/*  647 */         if (resultSet.wasNull())
/*  648 */           col3 = "";
/*  649 */         Boolean col4 = getWrappedBooleanFromJdbc(resultSet, col++);
/*  650 */         Boolean col5 = getWrappedBooleanFromJdbc(resultSet, col++);
/*      */ 
/*  653 */         results.add(erFinanceCube, erModel, col1, col2, col3.equals("Y"), col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  666 */       throw handleSQLException(SQL_ALL_FINANCE_CUBES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  670 */       closeResultSet(resultSet);
/*  671 */       closeStatement(stmt);
/*  672 */       closeConnection();
/*      */     }
/*      */ 
/*  675 */     if (timer != null) {
/*  676 */       timer.logDebug("getAllFinanceCubes", " items=" + results.size());
/*      */     }
/*      */ 
/*  680 */     return results;
/*      */   }
/*      */ 
/*      */   public AllSimpleFinanceCubesELO getAllSimpleFinanceCubes(int userId)
/*      */   {
/*  715 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  716 */     PreparedStatement stmt = null;
/*  717 */     ResultSet resultSet = null;
/*  718 */     AllSimpleFinanceCubesELO results = new AllSimpleFinanceCubesELO();
/*      */     try
/*      */     {
/*  721 */       stmt = getConnection().prepareStatement(SQL_ALL_SIMPLE_FINANCE_CUBES);
/*  722 */       int col = 1;
				 stmt.setInt(1, userId);
/*  723 */       resultSet = stmt.executeQuery();
/*  724 */       while (resultSet.next())
/*      */       {
/*  726 */         col = 2;
/*      */ 
/*  729 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  732 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  735 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  738 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  743 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/*  749 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  755 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/*  760 */         int col1 = resultSet.getInt(col++);
/*      */ 
/*  763 */         results.add(erFinanceCube, erModel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  772 */       throw handleSQLException(SQL_ALL_SIMPLE_FINANCE_CUBES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  776 */       closeResultSet(resultSet);
/*  777 */       closeStatement(stmt);
/*  778 */       closeConnection();
/*      */     }
/*      */ 
/*  781 */     if (timer != null) {
/*  782 */       timer.logDebug("getAllSimpleFinanceCubes", " items=" + results.size());
/*      */     }
/*      */ 
/*  786 */     return results;
/*      */   }
/*      */ 
/*      */   public AllDataTypesAttachedToFinanceCubeForModelELO getAllDataTypesAttachedToFinanceCubeForModel(int param1)
/*      */   {
/*  821 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  822 */     PreparedStatement stmt = null;
/*  823 */     ResultSet resultSet = null;
/*  824 */     AllDataTypesAttachedToFinanceCubeForModelELO results = new AllDataTypesAttachedToFinanceCubeForModelELO();
/*      */     try
/*      */     {
/*  827 */       stmt = getConnection().prepareStatement(SQL_ALL_DATA_TYPES_ATTACHED_TO_FINANCE_CUBE_FOR_MODEL);
/*  828 */       int col = 1;
/*  829 */       stmt.setInt(col++, param1);
/*  830 */       resultSet = stmt.executeQuery();
/*  831 */       while (resultSet.next())
/*      */       {
/*  833 */         col = 2;
/*      */ 
/*  836 */         results.add(resultSet.getShort(col++), resultSet.getString(col++));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  844 */       throw handleSQLException(SQL_ALL_DATA_TYPES_ATTACHED_TO_FINANCE_CUBE_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  848 */       closeResultSet(resultSet);
/*  849 */       closeStatement(stmt);
/*  850 */       closeConnection();
/*      */     }
/*      */ 
/*  853 */     if (timer != null) {
/*  854 */       timer.logDebug("getAllDataTypesAttachedToFinanceCubeForModel", " ModelId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  859 */     return results;
/*      */   }
/*      */ 
/*      */   public FinanceCubesForModelELO getFinanceCubesForModel(int modelId)
/*      */   {
/*  900 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  901 */     PreparedStatement stmt = null;
/*  902 */     ResultSet resultSet = null;
/*  903 */     FinanceCubesForModelELO results = new FinanceCubesForModelELO();
/*      */     try
/*      */     {
/*  906 */       stmt = getConnection().prepareStatement(SQL_FINANCE_CUBES_FOR_MODEL);
/*  907 */       int col = 1;
/*  908 */       stmt.setInt(col++, modelId);
/*  909 */       resultSet = stmt.executeQuery();
/*  910 */       while (resultSet.next())
/*      */       {
/*  912 */         col = 2;
/*      */ 
/*  915 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  918 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  921 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  924 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  929 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/*  935 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  941 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/*  946 */         int col1 = resultSet.getInt(col++);
/*  947 */         String col2 = resultSet.getString(col++);
/*  948 */         Integer col3 = getWrappedIntegerFromJdbc(resultSet, col++);
/*  949 */         String col4 = resultSet.getString(col++);
/*  950 */         if (resultSet.wasNull())
/*  951 */           col4 = "";
/*  952 */         String col5 = resultSet.getString(col++);
/*  953 */         if (resultSet.wasNull()) {
/*  954 */           col5 = "";
/*      */         }
/*      */ 
/*  957 */         results.add(erFinanceCube, erModel, col1, col2, col3, col4.equals("Y"), col5.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  970 */       throw handleSQLException(SQL_FINANCE_CUBES_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  974 */       closeResultSet(resultSet);
/*  975 */       closeStatement(stmt);
/*  976 */       closeConnection();
/*      */     }
/*      */ 
/*  979 */     if (timer != null) {
/*  980 */       timer.logDebug("getFinanceCubesForModel", " ModelId=" + modelId + " items=" + results.size());
/*      */     }
/*      */ 
/*  985 */     return results;
/*      */   }
/*      */ 
/*      */   public FinanceCubeDimensionsAndHierachiesELO getFinanceCubeDimensionsAndHierachies(int param1)
/*      */   {
/* 1036 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1037 */     PreparedStatement stmt = null;
/* 1038 */     ResultSet resultSet = null;
/* 1039 */     FinanceCubeDimensionsAndHierachiesELO results = new FinanceCubeDimensionsAndHierachiesELO();
/*      */     try
/*      */     {
/* 1042 */       stmt = getConnection().prepareStatement(SQL_FINANCE_CUBE_DIMENSIONS_AND_HIERACHIES);
/* 1043 */       int col = 1;
/* 1044 */       stmt.setInt(col++, param1);
/* 1045 */       resultSet = stmt.executeQuery();
/* 1046 */       while (resultSet.next())
/*      */       {
/* 1048 */         col = 2;
/*      */ 
/* 1051 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1054 */         String textModel = resultSet.getString(col++);
/*      */ 
/* 1057 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/* 1060 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/* 1063 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1067 */         String textModelDimensionRel = "";
/*      */ 
/* 1069 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/* 1072 */         String textDimension = resultSet.getString(col++);
/* 1073 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/* 1075 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/* 1078 */         String textHierarchy = resultSet.getString(col++);
/*      */ 
/* 1082 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/* 1088 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/* 1094 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/* 1100 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
/*      */ 
/* 1106 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/* 1113 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(pkHierarchy, textHierarchy);
/*      */ 
/* 1118 */         int col1 = resultSet.getInt(col++);
/* 1119 */         String col2 = resultSet.getString(col++);
/*      */ 
/* 1122 */         results.add(erFinanceCube, erModel, erModelDimensionRel, erDimension, erHierarchy, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1135 */       throw handleSQLException(SQL_FINANCE_CUBE_DIMENSIONS_AND_HIERACHIES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1139 */       closeResultSet(resultSet);
/* 1140 */       closeStatement(stmt);
/* 1141 */       closeConnection();
/*      */     }
/*      */ 
/* 1144 */     if (timer != null) {
/* 1145 */       timer.logDebug("getFinanceCubeDimensionsAndHierachies", " FinanceCubeId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1150 */     return results;
/*      */   }
/*      */ 
/*      */   public FinanceCubeAllDimensionsAndHierachiesELO getFinanceCubeAllDimensionsAndHierachies(int param1)
/*      */   {
/* 1199 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1200 */     PreparedStatement stmt = null;
/* 1201 */     ResultSet resultSet = null;
/* 1202 */     FinanceCubeAllDimensionsAndHierachiesELO results = new FinanceCubeAllDimensionsAndHierachiesELO();
/*      */     try
/*      */     {
/* 1205 */       stmt = getConnection().prepareStatement(SQL_FINANCE_CUBE_ALL_DIMENSIONS_AND_HIERACHIES);
/* 1206 */       int col = 1;
/* 1207 */       stmt.setInt(col++, param1);
/* 1208 */       resultSet = stmt.executeQuery();
/* 1209 */       while (resultSet.next())
/*      */       {
/* 1211 */         col = 2;
/*      */ 
/* 1214 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1217 */         String textModel = resultSet.getString(col++);
/*      */ 
/* 1220 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/* 1223 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/* 1226 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1230 */         String textModelDimensionRel = "";
/*      */ 
/* 1232 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/* 1235 */         String textDimension = resultSet.getString(col++);
/* 1236 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/* 1238 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/* 1241 */         String textHierarchy = resultSet.getString(col++);
/*      */ 
/* 1245 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/* 1251 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/* 1257 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/* 1263 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
/*      */ 
/* 1269 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/* 1276 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(pkHierarchy, textHierarchy);
/*      */ 
/* 1283 */         results.add(erFinanceCube, erModel, erModelDimensionRel, erDimension, erHierarchy);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1294 */       throw handleSQLException(SQL_FINANCE_CUBE_ALL_DIMENSIONS_AND_HIERACHIES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1298 */       closeResultSet(resultSet);
/* 1299 */       closeStatement(stmt);
/* 1300 */       closeConnection();
/*      */     }
/*      */ 
/* 1303 */     if (timer != null) {
/* 1304 */       timer.logDebug("getFinanceCubeAllDimensionsAndHierachies", " FinanceCubeId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1309 */     return results;
/*      */   }
/*      */ 
/*      */   public AllFinanceCubesWebELO getAllFinanceCubesWeb()
/*      */   {
/* 1347 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1348 */     PreparedStatement stmt = null;
/* 1349 */     ResultSet resultSet = null;
/* 1350 */     AllFinanceCubesWebELO results = new AllFinanceCubesWebELO();
/*      */     try
/*      */     {
/* 1353 */       stmt = getConnection().prepareStatement(SQL_ALL_FINANCE_CUBES_WEB);
/* 1354 */       int col = 1;
/* 1355 */       resultSet = stmt.executeQuery();
/* 1356 */       while (resultSet.next())
/*      */       {
/* 1358 */         col = 2;
/*      */ 
/* 1361 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1364 */         String textModel = resultSet.getString(col++);
/*      */ 
/* 1367 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/* 1370 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/* 1375 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/* 1381 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/* 1387 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/* 1392 */         int col1 = resultSet.getInt(col++);
/* 1393 */         int col2 = resultSet.getInt(col++);
/* 1394 */         String col3 = resultSet.getString(col++);
/* 1395 */         String col4 = resultSet.getString(col++);
/*      */ 
/* 1398 */         results.add(erFinanceCube, erModel, col1, col2, col3, col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1410 */       throw handleSQLException(SQL_ALL_FINANCE_CUBES_WEB, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1414 */       closeResultSet(resultSet);
/* 1415 */       closeStatement(stmt);
/* 1416 */       closeConnection();
/*      */     }
/*      */ 
/* 1419 */     if (timer != null) {
/* 1420 */       timer.logDebug("getAllFinanceCubesWeb", " items=" + results.size());
/*      */     }
/*      */ 
/* 1424 */     return results;
/*      */   }
/*      */ 
/*      */   public AllFinanceCubesWebForModelELO getAllFinanceCubesWebForModel(int param1)
/*      */   {
/* 1463 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1464 */     PreparedStatement stmt = null;
/* 1465 */     ResultSet resultSet = null;
/* 1466 */     AllFinanceCubesWebForModelELO results = new AllFinanceCubesWebForModelELO();
/*      */     try
/*      */     {
/* 1469 */       stmt = getConnection().prepareStatement(SQL_ALL_FINANCE_CUBES_WEB_FOR_MODEL);
/* 1470 */       int col = 1;
/* 1471 */       stmt.setInt(col++, param1);
/* 1472 */       resultSet = stmt.executeQuery();
/* 1473 */       while (resultSet.next())
/*      */       {
/* 1475 */         col = 2;
/*      */ 
/* 1478 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1481 */         String textModel = resultSet.getString(col++);
/*      */ 
/* 1484 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/* 1487 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/* 1492 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/* 1498 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/* 1504 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/* 1509 */         int col1 = resultSet.getInt(col++);
/* 1510 */         String col2 = resultSet.getString(col++);
/* 1511 */         String col3 = resultSet.getString(col++);
/*      */ 
/* 1514 */         results.add(erFinanceCube, erModel, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1525 */       throw handleSQLException(SQL_ALL_FINANCE_CUBES_WEB_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1529 */       closeResultSet(resultSet);
/* 1530 */       closeStatement(stmt);
/* 1531 */       closeConnection();
/*      */     }
/*      */ 
/* 1534 */     if (timer != null) {
/* 1535 */       timer.logDebug("getAllFinanceCubesWebForModel", " ModelId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1540 */     return results;
/*      */   }
/*      */ 
/*      */   public AllFinanceCubesWebForUserELO getAllFinanceCubesWebForUser(int param1)
/*      */   {
/* 1575 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1576 */     PreparedStatement stmt = null;
/* 1577 */     ResultSet resultSet = null;
/* 1578 */     AllFinanceCubesWebForUserELO results = new AllFinanceCubesWebForUserELO();
/*      */     try
/*      */     {
/* 1581 */       stmt = getConnection().prepareStatement(SQL_ALL_FINANCE_CUBES_WEB_FOR_USER);
/* 1582 */       int col = 1;
/* 1583 */       stmt.setInt(col++, param1);
/* 1584 */       resultSet = stmt.executeQuery();
/* 1585 */       while (resultSet.next())
/*      */       {
/* 1587 */         col = 2;
/*      */ 
/* 1590 */         results.add(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1599 */       throw handleSQLException(SQL_ALL_FINANCE_CUBES_WEB_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1603 */       closeResultSet(resultSet);
/* 1604 */       closeStatement(stmt);
/* 1605 */       closeConnection();
/*      */     }
/*      */ 
/* 1608 */     if (timer != null) {
/* 1609 */       timer.logDebug("getAllFinanceCubesWebForUser", " UserId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1614 */     return results;
/*      */   }
/*      */ 
/*      */   public FinanceCubeDetailsELO getFinanceCubeDetails(int param1)
/*      */   {
/* 1655 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1656 */     PreparedStatement stmt = null;
/* 1657 */     ResultSet resultSet = null;
/* 1658 */     FinanceCubeDetailsELO results = new FinanceCubeDetailsELO();
/*      */     try
/*      */     {
/* 1661 */       stmt = getConnection().prepareStatement(SQL_FINANCE_CUBE_DETAILS);
/* 1662 */       int col = 1;
/* 1663 */       stmt.setInt(col++, param1);
/* 1664 */       resultSet = stmt.executeQuery();
/* 1665 */       while (resultSet.next())
/*      */       {
/* 1667 */         col = 2;
/*      */ 
/* 1670 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1673 */         String textModel = resultSet.getString(col++);
/*      */ 
/* 1676 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/* 1679 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/* 1684 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/* 1690 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/* 1696 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/* 1701 */         int col1 = resultSet.getInt(col++);
/* 1702 */         int col2 = resultSet.getInt(col++);
/* 1703 */         String col3 = resultSet.getString(col++);
/* 1704 */         String col4 = resultSet.getString(col++);
/* 1705 */         String col5 = resultSet.getString(col++);
/* 1706 */         if (resultSet.wasNull()) {
/* 1707 */           col5 = "";
/*      */         }
/*      */ 
/* 1710 */         results.add(erFinanceCube, erModel, col1, col2, col3, col4, col5.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1723 */       throw handleSQLException(SQL_FINANCE_CUBE_DETAILS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1727 */       closeResultSet(resultSet);
/* 1728 */       closeStatement(stmt);
/* 1729 */       closeConnection();
/*      */     }
/*      */ 
/* 1732 */     if (timer != null) {
/* 1733 */       timer.logDebug("getFinanceCubeDetails", " FinanceCubeId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1738 */     return results;
/*      */   }
/*      */ 
/*      */   public FinanceCubesUsingDataTypeELO getFinanceCubesUsingDataType(short param1)
/*      */   {
/* 1781 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1782 */     PreparedStatement stmt = null;
/* 1783 */     ResultSet resultSet = null;
/* 1784 */     FinanceCubesUsingDataTypeELO results = new FinanceCubesUsingDataTypeELO();
/*      */     try
/*      */     {
/* 1787 */       stmt = getConnection().prepareStatement(SQL_FINANCE_CUBES_USING_DATA_TYPE);
/* 1788 */       int col = 1;
/* 1789 */       stmt.setShort(col++, param1);
/* 1790 */       resultSet = stmt.executeQuery();
/* 1791 */       while (resultSet.next())
/*      */       {
/* 1793 */         col = 2;
/*      */ 
/* 1796 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1799 */         String textModel = resultSet.getString(col++);
/*      */ 
/* 1802 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/* 1805 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/* 1808 */         FinanceCubeDataTypePK pkFinanceCubeDataType = new FinanceCubeDataTypePK(resultSet.getInt(col++), resultSet.getShort(col++));
/*      */ 
/* 1812 */         String textFinanceCubeDataType = "";
/*      */ 
/* 1816 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/* 1822 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/* 1828 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/* 1834 */         FinanceCubeDataTypeRefImpl erFinanceCubeDataType = new FinanceCubeDataTypeRefImpl(pkFinanceCubeDataType, textFinanceCubeDataType);
/*      */ 
/* 1839 */         int col1 = resultSet.getInt(col++);
/* 1840 */         int col2 = resultSet.getInt(col++);
/* 1841 */         String col3 = resultSet.getString(col++);
/*      */ 
/* 1844 */         results.add(erFinanceCube, erModel, erFinanceCubeDataType, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1856 */       throw handleSQLException(SQL_FINANCE_CUBES_USING_DATA_TYPE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1860 */       closeResultSet(resultSet);
/* 1861 */       closeStatement(stmt);
/* 1862 */       closeConnection();
/*      */     }
/*      */ 
/* 1865 */     if (timer != null) {
/* 1866 */       timer.logDebug("getFinanceCubesUsingDataType", " DataTypeId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1871 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/* 1888 */     if (items == null) {
/* 1889 */       return false;
/*      */     }
/* 1891 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1892 */     PreparedStatement deleteStmt = null;
/*      */ 
/* 1894 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/* 1898 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/* 1899 */       while (iter3.hasNext())
/*      */       {
/* 1901 */         this.mDetails = ((FinanceCubeEVO)iter3.next());
/* 1902 */         if (!this.mDetails.deletePending())
/*      */           continue;
/* 1904 */         somethingChanged = true;
/*      */ 
/* 1907 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/* 1911 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 1912 */       while (iter2.hasNext())
/*      */       {
/* 1914 */         this.mDetails = ((FinanceCubeEVO)iter2.next());
/*      */ 
/* 1917 */         if (!this.mDetails.deletePending())
/*      */           continue;
/* 1919 */         somethingChanged = true;
/*      */ 
/* 1922 */         if (deleteStmt == null) {
/* 1923 */           deleteStmt = getConnection().prepareStatement("delete from FINANCE_CUBE where    FINANCE_CUBE_ID = ? ");
/*      */         }
/*      */ 
/* 1926 */         int col = 1;
/* 1927 */         deleteStmt.setInt(col++, this.mDetails.getFinanceCubeId());
/*      */ 
/* 1929 */         if (this._log.isDebugEnabled()) {
/* 1930 */           this._log.debug("update", "FinanceCube deleting FinanceCubeId=" + this.mDetails.getFinanceCubeId());
/*      */         }
/*      */ 
/* 1935 */         deleteStmt.addBatch();
/*      */ 
/* 1938 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/* 1943 */       if (deleteStmt != null)
/*      */       {
/* 1945 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1947 */         deleteStmt.executeBatch();
/*      */ 
/* 1949 */         if (timer2 != null) {
/* 1950 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/* 1954 */       Iterator iter1 = items.values().iterator();
/* 1955 */       while (iter1.hasNext())
/*      */       {
/* 1957 */         this.mDetails = ((FinanceCubeEVO)iter1.next());
/*      */ 
/* 1959 */         if (this.mDetails.insertPending())
/*      */         {
/* 1961 */           somethingChanged = true;
/* 1962 */           doCreate(); continue;
/*      */         }
/*      */ 
/* 1965 */         if (this.mDetails.isModified())
/*      */         {
/* 1967 */           somethingChanged = true;
/* 1968 */           doStore(); continue;
/*      */         }
/*      */ 
/* 1972 */         if (this.mDetails.deletePending())
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/* 1978 */         if (getFinanceCubeDataTypeDAO().update(this.mDetails.getFinanceCubeDataTypesMap())) {
/* 1979 */           somethingChanged = true;
/*      */         }
/*      */ 
/* 1982 */         if (getBudgetLimitDAO().update(this.mDetails.getBudgetLimitsMap())) {
/* 1983 */           somethingChanged = true;
/*      */         }
/*      */ 
/* 1986 */         if (getRollUpRuleDAO().update(this.mDetails.getRollUpRulesMap())) {
/* 1987 */           somethingChanged = true;
/*      */         }
/*      */ 
/* 1990 */         if (getCubeFormulaDAO().update(this.mDetails.getCubeFormulaMap())) {
/* 1991 */           somethingChanged = true;
/*      */         }
/*      */ 
/* 1994 */         if (getCubeFormulaPackageDAO().update(this.mDetails.getCubeFormulaPackagesMap())) {
/* 1995 */           somethingChanged = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2007 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2011 */       throw handleSQLException("delete from FINANCE_CUBE where    FINANCE_CUBE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2015 */       if (deleteStmt != null)
/*      */       {
/* 2017 */         closeStatement(deleteStmt);
/* 2018 */         closeConnection();
/*      */       }
/*      */ 
/* 2021 */       this.mDetails = null;
/*      */ 
/* 2023 */       if ((somethingChanged) && 
/* 2024 */         (timer != null))
/* 2025 */         timer.logDebug("update", "collection"); 
/* 2025 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(FinanceCubePK pk)
/*      */   {
/* 2108 */     Set emptyStrings = Collections.emptySet();
/* 2109 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(FinanceCubePK pk, Set<String> exclusionTables)
/*      */   {
/* 2116 */     exclusionTables = new HashSet();
/* 2117 */     exclusionTables.add("CUBE_FORMULA_PACKAGE");
/*      */ 
/* 2119 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 2121 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 2123 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 2125 */       PreparedStatement stmt = null;
/*      */ 
/* 2127 */       int resultCount = 0;
/* 2128 */       String s = null;
/*      */       try
/*      */       {
/* 2131 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 2133 */         if (this._log.isDebugEnabled()) {
/* 2134 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 2136 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 2139 */         int col = 1;
/* 2140 */         stmt.setInt(col++, pk.getFinanceCubeId());
/*      */ 
/* 2143 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 2147 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 2151 */         closeStatement(stmt);
/* 2152 */         closeConnection();
/*      */ 
/* 2154 */         if (timer != null) {
/* 2155 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 2159 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 2161 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 2163 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 2165 */       PreparedStatement stmt = null;
/*      */ 
/* 2167 */       int resultCount = 0;
/* 2168 */       String s = null;
/*      */       try
/*      */       {
/* 2171 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 2173 */         if (this._log.isDebugEnabled()) {
/* 2174 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 2176 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 2179 */         int col = 1;
/* 2180 */         stmt.setInt(col++, pk.getFinanceCubeId());
/*      */ 
/* 2183 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 2187 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 2191 */         closeStatement(stmt);
/* 2192 */         closeConnection();
/*      */ 
/* 2194 */         if (timer != null)
/* 2195 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants)
/*      */   {
/* 2215 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 2217 */     PreparedStatement stmt = null;
/* 2218 */     ResultSet resultSet = null;
/*      */ 
/* 2220 */     int itemCount = 0;
/*      */ 
/* 2222 */     Collection theseItems = new ArrayList();
/* 2223 */     owningEVO.setFinanceCubes(theseItems);
/* 2224 */     owningEVO.setFinanceCubesAllItemsLoaded(true);
/*      */     try
/*      */     {
/* 2228 */       stmt = getConnection().prepareStatement("select FINANCE_CUBE.FINANCE_CUBE_ID,FINANCE_CUBE.MODEL_ID,FINANCE_CUBE.VIS_ID,FINANCE_CUBE.DESCRIPTION,FINANCE_CUBE.LOCKED_BY_TASK_ID,FINANCE_CUBE.HAS_DATA,FINANCE_CUBE.AUDITED,FINANCE_CUBE.CUBE_FORMULA_ENABLED,FINANCE_CUBE.VERSION_NUM,FINANCE_CUBE.UPDATED_BY_USER_ID,FINANCE_CUBE.UPDATED_TIME,FINANCE_CUBE.CREATED_TIME from FINANCE_CUBE where 1=1 and FINANCE_CUBE.MODEL_ID = ? order by  FINANCE_CUBE.FINANCE_CUBE_ID");
/*      */ 
/* 2230 */       int col = 1;
/* 2231 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/* 2233 */       resultSet = stmt.executeQuery();
/*      */ 
/* 2236 */       while (resultSet.next())
/*      */       {
/* 2238 */         itemCount++;
/* 2239 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 2241 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/* 2244 */       if (timer != null) {
/* 2245 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/* 2248 */       if ((itemCount > 0) && (dependants.indexOf("<1>") > -1))
/*      */       {
/* 2250 */         getFinanceCubeDataTypeDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/* 2252 */       if ((itemCount > 0) && (dependants.indexOf("<2>") > -1))
/*      */       {
/* 2254 */         getBudgetLimitDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/* 2256 */       if ((itemCount > 0) && (dependants.indexOf("<3>") > -1))
/*      */       {
/* 2258 */         getRollUpRuleDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/* 2260 */       if ((itemCount > 0) && (dependants.indexOf("<4>") > -1))
/*      */       {
/* 2262 */         getCubeFormulaDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/* 2264 */       if ((itemCount > 0) && (dependants.indexOf("<8>") > -1))
/*      */       {
/* 2266 */         getCubeFormulaPackageDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/* 2270 */       throw handleSQLException("select FINANCE_CUBE.FINANCE_CUBE_ID,FINANCE_CUBE.MODEL_ID,FINANCE_CUBE.VIS_ID,FINANCE_CUBE.DESCRIPTION,FINANCE_CUBE.LOCKED_BY_TASK_ID,FINANCE_CUBE.HAS_DATA,FINANCE_CUBE.AUDITED,FINANCE_CUBE.CUBE_FORMULA_ENABLED,FINANCE_CUBE.VERSION_NUM,FINANCE_CUBE.UPDATED_BY_USER_ID,FINANCE_CUBE.UPDATED_TIME,FINANCE_CUBE.CREATED_TIME from FINANCE_CUBE where 1=1 and FINANCE_CUBE.MODEL_ID = ? order by  FINANCE_CUBE.FINANCE_CUBE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2274 */       closeResultSet(resultSet);
/* 2275 */       closeStatement(stmt);
/* 2276 */       closeConnection();
/*      */ 
/* 2278 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectModelId, String dependants, Collection currentList)
/*      */   {
/* 2303 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2304 */     PreparedStatement stmt = null;
/* 2305 */     ResultSet resultSet = null;
/*      */ 
/* 2307 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/* 2311 */       stmt = getConnection().prepareStatement("select FINANCE_CUBE.FINANCE_CUBE_ID,FINANCE_CUBE.MODEL_ID,FINANCE_CUBE.VIS_ID,FINANCE_CUBE.DESCRIPTION,FINANCE_CUBE.LOCKED_BY_TASK_ID,FINANCE_CUBE.HAS_DATA,FINANCE_CUBE.AUDITED,FINANCE_CUBE.CUBE_FORMULA_ENABLED,FINANCE_CUBE.VERSION_NUM,FINANCE_CUBE.UPDATED_BY_USER_ID,FINANCE_CUBE.UPDATED_TIME,FINANCE_CUBE.CREATED_TIME from FINANCE_CUBE where    MODEL_ID = ? ");
/*      */ 
/* 2313 */       int col = 1;
/* 2314 */       stmt.setInt(col++, selectModelId);
/*      */ 
/* 2316 */       resultSet = stmt.executeQuery();
/*      */ 
/* 2318 */       while (resultSet.next())
/*      */       {
/* 2320 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 2323 */         getDependants(this.mDetails, dependants);
/*      */ 
/* 2326 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 2329 */       if (currentList != null)
/*      */       {
/* 2332 */         ListIterator iter = items.listIterator();
/* 2333 */         FinanceCubeEVO currentEVO = null;
/* 2334 */         FinanceCubeEVO newEVO = null;
/* 2335 */         while (iter.hasNext())
/*      */         {
/* 2337 */           newEVO = (FinanceCubeEVO)iter.next();
/* 2338 */           Iterator iter2 = currentList.iterator();
/* 2339 */           while (iter2.hasNext())
/*      */           {
/* 2341 */             currentEVO = (FinanceCubeEVO)iter2.next();
/* 2342 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 2344 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2350 */         Iterator iter2 = currentList.iterator();
/* 2351 */         while (iter2.hasNext())
/*      */         {
/* 2353 */           currentEVO = (FinanceCubeEVO)iter2.next();
/* 2354 */           if (currentEVO.insertPending()) {
/* 2355 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 2359 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2363 */       throw handleSQLException("select FINANCE_CUBE.FINANCE_CUBE_ID,FINANCE_CUBE.MODEL_ID,FINANCE_CUBE.VIS_ID,FINANCE_CUBE.DESCRIPTION,FINANCE_CUBE.LOCKED_BY_TASK_ID,FINANCE_CUBE.HAS_DATA,FINANCE_CUBE.AUDITED,FINANCE_CUBE.CUBE_FORMULA_ENABLED,FINANCE_CUBE.VERSION_NUM,FINANCE_CUBE.UPDATED_BY_USER_ID,FINANCE_CUBE.UPDATED_TIME,FINANCE_CUBE.CREATED_TIME from FINANCE_CUBE where    MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2367 */       closeResultSet(resultSet);
/* 2368 */       closeStatement(stmt);
/* 2369 */       closeConnection();
/*      */ 
/* 2371 */       if (timer != null) {
/* 2372 */         timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2377 */     return items;
/*      */   }
/*      */ 
/*      */   public FinanceCubeEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 2408 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 2411 */     if (this.mDetails == null) {
/* 2412 */       doLoad(((FinanceCubeCK)paramCK).getFinanceCubePK());
/*      */     }
/* 2414 */     else if (!this.mDetails.getPK().equals(((FinanceCubeCK)paramCK).getFinanceCubePK())) {
/* 2415 */       doLoad(((FinanceCubeCK)paramCK).getFinanceCubePK());
/*      */     }
/*      */ 
/* 2418 */     if ((dependants.indexOf("<1>") > -1) && (!this.mDetails.isFinanceCubeDataTypesAllItemsLoaded()))
/*      */     {
/* 2423 */       this.mDetails.setFinanceCubeDataTypes(getFinanceCubeDataTypeDAO().getAll(this.mDetails.getFinanceCubeId(), dependants, this.mDetails.getFinanceCubeDataTypes()));
/*      */ 
/* 2430 */       this.mDetails.setFinanceCubeDataTypesAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 2434 */     if ((dependants.indexOf("<2>") > -1) && (!this.mDetails.isBudgetLimitsAllItemsLoaded()))
/*      */     {
/* 2439 */       this.mDetails.setBudgetLimits(getBudgetLimitDAO().getAll(this.mDetails.getFinanceCubeId(), dependants, this.mDetails.getBudgetLimits()));
/*      */ 
/* 2446 */       this.mDetails.setBudgetLimitsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 2450 */     if ((dependants.indexOf("<3>") > -1) && (!this.mDetails.isRollUpRulesAllItemsLoaded()))
/*      */     {
/* 2455 */       this.mDetails.setRollUpRules(getRollUpRuleDAO().getAll(this.mDetails.getFinanceCubeId(), dependants, this.mDetails.getRollUpRules()));
/*      */ 
/* 2462 */       this.mDetails.setRollUpRulesAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 2466 */     if ((dependants.indexOf("<4>") > -1) && (!this.mDetails.isCubeFormulaAllItemsLoaded()))
/*      */     {
/* 2471 */       this.mDetails.setCubeFormula(getCubeFormulaDAO().getAll(this.mDetails.getFinanceCubeId(), dependants, this.mDetails.getCubeFormula()));
/*      */ 
/* 2478 */       this.mDetails.setCubeFormulaAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 2482 */     if ((dependants.indexOf("<8>") > -1) && (!this.mDetails.isCubeFormulaPackagesAllItemsLoaded()))
/*      */     {
/* 2487 */       this.mDetails.setCubeFormulaPackages(getCubeFormulaPackageDAO().getAll(this.mDetails.getFinanceCubeId(), dependants, this.mDetails.getCubeFormulaPackages()));
/*      */ 
/* 2494 */       this.mDetails.setCubeFormulaPackagesAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 2497 */     if ((paramCK instanceof FinanceCubeDataTypeCK))
/*      */     {
/* 2499 */       if (this.mDetails.getFinanceCubeDataTypes() == null) {
/* 2500 */         this.mDetails.loadFinanceCubeDataTypesItem(getFinanceCubeDataTypeDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 2503 */         FinanceCubeDataTypePK pk = ((FinanceCubeDataTypeCK)paramCK).getFinanceCubeDataTypePK();
/* 2504 */         FinanceCubeDataTypeEVO evo = this.mDetails.getFinanceCubeDataTypesItem(pk);
/* 2505 */         if (evo == null) {
/* 2506 */           this.mDetails.loadFinanceCubeDataTypesItem(getFinanceCubeDataTypeDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/* 2510 */     else if ((paramCK instanceof BudgetLimitCK))
/*      */     {
/* 2512 */       if (this.mDetails.getBudgetLimits() == null) {
/* 2513 */         this.mDetails.loadBudgetLimitsItem(getBudgetLimitDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 2516 */         BudgetLimitPK pk = ((BudgetLimitCK)paramCK).getBudgetLimitPK();
/* 2517 */         BudgetLimitEVO evo = this.mDetails.getBudgetLimitsItem(pk);
/* 2518 */         if (evo == null) {
/* 2519 */           this.mDetails.loadBudgetLimitsItem(getBudgetLimitDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/* 2523 */     else if ((paramCK instanceof RollUpRuleCK))
/*      */     {
/* 2525 */       if (this.mDetails.getRollUpRules() == null) {
/* 2526 */         this.mDetails.loadRollUpRulesItem(getRollUpRuleDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 2529 */         RollUpRulePK pk = ((RollUpRuleCK)paramCK).getRollUpRulePK();
/* 2530 */         RollUpRuleEVO evo = this.mDetails.getRollUpRulesItem(pk);
/* 2531 */         if (evo == null) {
/* 2532 */           this.mDetails.loadRollUpRulesItem(getRollUpRuleDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/* 2536 */     else if ((paramCK instanceof CubeFormulaCK))
/*      */     {
/* 2538 */       if (this.mDetails.getCubeFormula() == null) {
/* 2539 */         this.mDetails.loadCubeFormulaItem(getCubeFormulaDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 2542 */         CubeFormulaPK pk = ((CubeFormulaCK)paramCK).getCubeFormulaPK();
/* 2543 */         CubeFormulaEVO evo = this.mDetails.getCubeFormulaItem(pk);
/* 2544 */         if (evo == null)
/* 2545 */           this.mDetails.loadCubeFormulaItem(getCubeFormulaDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 2547 */           getCubeFormulaDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/* 2551 */     else if ((paramCK instanceof CubeFormulaPackageCK))
/*      */     {
/* 2553 */       if (this.mDetails.getCubeFormulaPackages() == null) {
/* 2554 */         this.mDetails.loadCubeFormulaPackagesItem(getCubeFormulaPackageDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 2557 */         CubeFormulaPackagePK pk = ((CubeFormulaPackageCK)paramCK).getCubeFormulaPackagePK();
/* 2558 */         CubeFormulaPackageEVO evo = this.mDetails.getCubeFormulaPackagesItem(pk);
/* 2559 */         if (evo == null) {
/* 2560 */           this.mDetails.loadCubeFormulaPackagesItem(getCubeFormulaPackageDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2565 */     FinanceCubeEVO details = new FinanceCubeEVO();
/* 2566 */     details = this.mDetails.deepClone();
/*      */ 
/* 2568 */     if (timer != null) {
/* 2569 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 2571 */     return details;
/*      */   }
/*      */ 
/*      */   public FinanceCubeEVO getDetails(ModelCK paramCK, FinanceCubeEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 2577 */     FinanceCubeEVO savedEVO = this.mDetails;
/* 2578 */     this.mDetails = paramEVO;
/* 2579 */     FinanceCubeEVO newEVO = getDetails(paramCK, dependants);
/* 2580 */     this.mDetails = savedEVO;
/* 2581 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public FinanceCubeEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 2587 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 2591 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 2594 */     FinanceCubeEVO details = this.mDetails.deepClone();
/*      */ 
/* 2596 */     if (timer != null) {
/* 2597 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 2599 */     return details;
/*      */   }
/*      */ 
/*      */   protected FinanceCubeDataTypeDAO getFinanceCubeDataTypeDAO()
/*      */   {
/* 2608 */     if (this.mFinanceCubeDataTypeDAO == null)
/*      */     {
/* 2610 */       if (this.mDataSource != null)
/* 2611 */         this.mFinanceCubeDataTypeDAO = new FinanceCubeDataTypeDAO(this.mDataSource);
/*      */       else {
/* 2613 */         this.mFinanceCubeDataTypeDAO = new FinanceCubeDataTypeDAO(getConnection());
/*      */       }
/*      */     }
/* 2616 */     return this.mFinanceCubeDataTypeDAO;
/*      */   }
/*      */ 
/*      */   protected BudgetLimitDAO getBudgetLimitDAO()
/*      */   {
/* 2625 */     if (this.mBudgetLimitDAO == null)
/*      */     {
/* 2627 */       if (this.mDataSource != null)
/* 2628 */         this.mBudgetLimitDAO = new BudgetLimitDAO(this.mDataSource);
/*      */       else {
/* 2630 */         this.mBudgetLimitDAO = new BudgetLimitDAO(getConnection());
/*      */       }
/*      */     }
/* 2633 */     return this.mBudgetLimitDAO;
/*      */   }
/*      */ 
/*      */   protected RollUpRuleDAO getRollUpRuleDAO()
/*      */   {
/* 2642 */     if (this.mRollUpRuleDAO == null)
/*      */     {
/* 2644 */       if (this.mDataSource != null)
/* 2645 */         this.mRollUpRuleDAO = new RollUpRuleDAO(this.mDataSource);
/*      */       else {
/* 2647 */         this.mRollUpRuleDAO = new RollUpRuleDAO(getConnection());
/*      */       }
/*      */     }
/* 2650 */     return this.mRollUpRuleDAO;
/*      */   }
/*      */ 
/*      */   protected CubeFormulaDAO getCubeFormulaDAO()
/*      */   {
/* 2659 */     if (this.mCubeFormulaDAO == null)
/*      */     {
/* 2661 */       if (this.mDataSource != null)
/* 2662 */         this.mCubeFormulaDAO = new CubeFormulaDAO(this.mDataSource);
/*      */       else {
/* 2664 */         this.mCubeFormulaDAO = new CubeFormulaDAO(getConnection());
/*      */       }
/*      */     }
/* 2667 */     return this.mCubeFormulaDAO;
/*      */   }
/*      */ 
/*      */   protected CubeFormulaPackageDAO getCubeFormulaPackageDAO()
/*      */   {
/* 2676 */     if (this.mCubeFormulaPackageDAO == null)
/*      */     {
/* 2678 */       if (this.mDataSource != null)
/* 2679 */         this.mCubeFormulaPackageDAO = new CubeFormulaPackageDAO(this.mDataSource);
/*      */       else {
/* 2681 */         this.mCubeFormulaPackageDAO = new CubeFormulaPackageDAO(getConnection());
/*      */       }
/*      */     }
/* 2684 */     return this.mCubeFormulaPackageDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 2689 */     return "FinanceCube";
/*      */   }
/*      */ 
/*      */   public FinanceCubeRefImpl getRef(FinanceCubePK paramFinanceCubePK)
/*      */   {
/* 2694 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2695 */     PreparedStatement stmt = null;
/* 2696 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 2699 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,FINANCE_CUBE.VIS_ID from FINANCE_CUBE,MODEL where 1=1 and FINANCE_CUBE.FINANCE_CUBE_ID = ? and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID");
/* 2700 */       int col = 1;
/* 2701 */       stmt.setInt(col++, paramFinanceCubePK.getFinanceCubeId());
/*      */ 
/* 2703 */       resultSet = stmt.executeQuery();
/*      */ 
/* 2705 */       if (!resultSet.next()) {
/* 2706 */         throw new RuntimeException(getEntityName() + " getRef " + paramFinanceCubePK + " not found");
/*      */       }
/* 2708 */       col = 2;
/* 2709 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 2713 */       String textFinanceCube = resultSet.getString(col++);
/* 2714 */       FinanceCubeCK ckFinanceCube = new FinanceCubeCK(newModelPK, paramFinanceCubePK);
/*      */ 
/* 2719 */       FinanceCubeRefImpl localFinanceCubeRefImpl = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */       return localFinanceCubeRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2724 */       throw handleSQLException(paramFinanceCubePK, "select 0,MODEL.MODEL_ID,FINANCE_CUBE.VIS_ID from FINANCE_CUBE,MODEL where 1=1 and FINANCE_CUBE.FINANCE_CUBE_ID = ? and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2728 */       closeResultSet(resultSet);
/* 2729 */       closeStatement(stmt);
/* 2730 */       closeConnection();
/*      */ 
/* 2732 */       if (timer != null)
/* 2733 */         timer.logDebug("getRef", paramFinanceCubePK); 
/* 2733 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 2745 */     if (c == null)
/* 2746 */       return;
/* 2747 */     Iterator iter = c.iterator();
/* 2748 */     while (iter.hasNext())
/*      */     {
/* 2750 */       FinanceCubeEVO evo = (FinanceCubeEVO)iter.next();
/* 2751 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(FinanceCubeEVO evo, String dependants)
/*      */   {
/* 2765 */     if (evo.insertPending()) {
/* 2766 */       return;
/*      */     }
/* 2768 */     if (evo.getFinanceCubeId() < 1) {
/* 2769 */       return;
/*      */     }
/*      */ 
/* 2773 */     if (dependants.indexOf("<1>") > -1)
/*      */     {
/* 2776 */       if (!evo.isFinanceCubeDataTypesAllItemsLoaded())
/*      */       {
/* 2778 */         evo.setFinanceCubeDataTypes(getFinanceCubeDataTypeDAO().getAll(evo.getFinanceCubeId(), dependants, evo.getFinanceCubeDataTypes()));
/*      */ 
/* 2785 */         evo.setFinanceCubeDataTypesAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2790 */     if (dependants.indexOf("<2>") > -1)
/*      */     {
/* 2793 */       if (!evo.isBudgetLimitsAllItemsLoaded())
/*      */       {
/* 2795 */         evo.setBudgetLimits(getBudgetLimitDAO().getAll(evo.getFinanceCubeId(), dependants, evo.getBudgetLimits()));
/*      */ 
/* 2802 */         evo.setBudgetLimitsAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2807 */     if (dependants.indexOf("<3>") > -1)
/*      */     {
/* 2810 */       if (!evo.isRollUpRulesAllItemsLoaded())
/*      */       {
/* 2812 */         evo.setRollUpRules(getRollUpRuleDAO().getAll(evo.getFinanceCubeId(), dependants, evo.getRollUpRules()));
/*      */ 
/* 2819 */         evo.setRollUpRulesAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2824 */     if ((dependants.indexOf("<4>") > -1) || (dependants.indexOf("<5>") > -1) || (dependants.indexOf("<6>") > -1) || (dependants.indexOf("<7>") > -1))
/*      */     {
/* 2830 */       if (!evo.isCubeFormulaAllItemsLoaded())
/*      */       {
/* 2832 */         evo.setCubeFormula(getCubeFormulaDAO().getAll(evo.getFinanceCubeId(), dependants, evo.getCubeFormula()));
/*      */ 
/* 2839 */         evo.setCubeFormulaAllItemsLoaded(true);
/*      */       }
/* 2841 */       getCubeFormulaDAO().getDependants(evo.getCubeFormula(), dependants);
/*      */     }
/*      */ 
/* 2845 */     if (dependants.indexOf("<8>") > -1)
/*      */     {
/* 2848 */       if (!evo.isCubeFormulaPackagesAllItemsLoaded())
/*      */       {
/* 2850 */         evo.setCubeFormulaPackages(getCubeFormulaPackageDAO().getAll(evo.getFinanceCubeId(), dependants, evo.getCubeFormulaPackages()));
/*      */ 
/* 2857 */         evo.setCubeFormulaPackagesAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void insertCellNote(List keys, String note, int structureElementId, int userId)
/*      */   {
/* 2867 */     Iterator iter = keys.iterator();
/* 2868 */     while (iter.hasNext())
/*      */     {
/* 2870 */       List l = (List)iter.next();
/* 2871 */       List newList = new ArrayList(l.size());
/* 2872 */       newList.add(l.get(0));
/* 2873 */       newList.add(new Integer(structureElementId));
/* 2874 */       for (int i = 1; i < l.size(); i++) {
/* 2875 */         newList.add(l.get(i));
/*      */       }
/* 2877 */       insertCellNote(newList, note, userId);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void insertCellNote(List keys, String note, int userId)
/*      */   {
/* 2883 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 2885 */     Integer fcid = (Integer)keys.get(0);
/* 2886 */     int last = keys.size() - 1;
/* 2887 */     int size = keys.size() - 2;
/* 2888 */     int[] dims = new int[size];
/* 2889 */     for (int i = 0; i < size; i++)
/*      */     {
/* 2891 */       dims[i] = ((Integer)keys.get(i + 1)).intValue();
/*      */     }
/* 2893 */     String datatype = keys.get(last).toString();
/*      */ 
/* 2895 */     StringBuffer sql = new StringBuffer();
/* 2896 */     sql.append("insert into SFT");
/* 2897 */     sql.append(fcid);
/* 2898 */     sql.append(" (");
/* 2899 */     for (int i = 0; i < dims.length; i++)
/*      */     {
/* 2901 */       sql.append(" dim");
/* 2902 */       sql.append(i);
/* 2903 */       sql.append(" ,");
/*      */     }
/* 2905 */     sql.append(" data_type, seq, created, user_id, user_name, string_value, link_id, link_type ) ");
/* 2906 */     sql.append(" values ( ");
/* 2907 */     for (int i = 0; i < dims.length; i++)
/*      */     {
/* 2909 */       sql.append(" ?,");
/*      */     }
/* 2911 */     sql.append(" ? ,SFTSEQ" + fcid + ".NEXTVAL" + ",systimestamp, ?, (select name from usr where user_id = ?), ?, 0, 1 ) ");
/*      */ 
/* 2916 */     PreparedStatement stmt = null;
/* 2917 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 2920 */       stmt = getConnection().prepareStatement(sql.toString());
/* 2921 */       int col = 1;
/*      */ 
/* 2923 */       for (int i = 0; i < dims.length; i++)
/*      */       {
/* 2925 */         stmt.setInt(col++, dims[i]);
/*      */       }
/* 2927 */       stmt.setString(col++, datatype);
/* 2928 */       stmt.setInt(col++, userId);
/* 2929 */       stmt.setInt(col++, userId);
/* 2930 */       stmt.setString(col, note);
/*      */ 
/* 2932 */       if (this._log.isDebugEnabled())
/*      */       {
/* 2934 */         this._log.debug("performInsert sql string", sql.toString());
/* 2935 */         this._log.debug("*********** bind vars start");
/* 2936 */         for (int i = 0; i < dims.length; i++)
/* 2937 */           this._log.debug("dim" + i + " " + dims[i]);
/* 2938 */         this._log.debug("data type :" + datatype);
/* 2939 */         this._log.debug("note :" + note);
/* 2940 */         this._log.debug("userId :" + userId);
/* 2941 */         this._log.debug("*********** bind vars end");
/*      */       }
/*      */ 
/* 2944 */       stmt.executeUpdate();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2948 */       sqle.printStackTrace();
/* 2949 */       throw new RuntimeException("Error doing insert cell note");
/*      */     }
/*      */     finally
/*      */     {
/* 2953 */       closeResultSet(resultSet);
/* 2954 */       closeStatement(stmt);
/* 2955 */       closeConnection();
/*      */ 
/* 2957 */       if (timer != null)
/* 2958 */         timer.logDebug("insertCellNote");
/*      */     }
/*      */   }
/*      */ 
/*      */   public Map<String, boolean[]> getRollUpRules(int financeCubeId)
/*      */   {
/* 2996 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2997 */     Map rollUpRules = new HashMap();
/*      */ 
/* 2999 */     PreparedStatement stmt = null;
/* 3000 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 3003 */       stmt = getConnection().prepareStatement("with my_model_dimension_rels as\n(\n  select fc.finance_cube_id, mdr.dimension_id, mdr.dimension_seq_num\n  from model_dimension_rel mdr, \n       finance_cube fc\n  where mdr.model_id = fc.model_id \n    and fc.finance_cube_id = ?\n  order by mdr.dimension_seq_num\n),\nnum_dims as\n(\n  select count(*) num_dims from my_model_dimension_rels\n)\nselect num_dims, fcdt.data_type_id, dt.vis_id, rur.roll_up\nfrom num_dims, \n     my_model_dimension_rels mdr, \n     finance_cube_data_type fcdt,\n     roll_up_rule rur,\n     data_type dt\nwhere rur.finance_cube_id = mdr.finance_cube_id \nand rur.dimension_id = mdr.dimension_id\nand rur.data_type_id = fcdt.data_type_id\nand rur.finance_cube_id = fcdt.finance_cube_id\nand dt.data_type_id = fcdt.data_type_id\norder by fcdt.data_type_id, mdr.dimension_seq_num");
/*      */ 
/* 3005 */       stmt.setInt(1, financeCubeId);
/*      */ 
/* 3007 */       rs = stmt.executeQuery();
/*      */ 
/* 3009 */       boolean[] curRollUpRules = null;
/* 3010 */       int boolIdx = 0;
/*      */ 
/* 3012 */       while (rs.next())
/*      */       {
/* 3014 */         int numDims = rs.getInt("num_dims");
/* 3015 */         int dataTypeId = rs.getInt("data_type_id");
/* 3016 */         String visId = rs.getString("vis_id");
/* 3017 */         boolean rollUp = rs.getString("roll_up").equalsIgnoreCase("Y");
/*      */ 
/* 3019 */         if (curRollUpRules == null) {
/* 3020 */           curRollUpRules = new boolean[numDims];
/*      */         }
/* 3022 */         curRollUpRules[boolIdx] = rollUp;
/* 3023 */         boolIdx++;
/*      */ 
/* 3025 */         if (boolIdx == numDims)
/*      */         {
/* 3027 */           rollUpRules.put(visId, curRollUpRules);
/* 3028 */           boolIdx = 0;
/* 3029 */           curRollUpRules = null;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3035 */       throw handleSQLException("queryRollUpRules", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3039 */       closeResultSet(rs);
/* 3040 */       closeStatement(stmt);
/* 3041 */       closeConnection();
/* 3042 */       if (timer != null)
/* 3043 */         timer.logDebug("queryRollUpRules");
/*      */     }
/* 3045 */     return rollUpRules;
/*      */   }
/*      */ 
/*      */   public FinanceCubeInfo getFinanceCubeInfo(int financeCubeId)
/*      */     throws ValidationException
/*      */   {
/* 3056 */     PreparedStatement ps = null;
/* 3057 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 3061 */       ps = getConnection().prepareStatement("select model_id, finance_cube_id, cube_formula_enabled,\n       count(*) as num_dims, max(h.hierarchy_id) as cal_hier_id\nfrom finance_cube fc\njoin model m using( model_id )\njoin model_dimension_rel mdr using( model_id )\nleft join hierarchy h on ( h.dimension_id = mdr.dimension_id and dimension_type = 3)\nwhere fc.finance_cube_id = ?\ngroup by model_id, finance_cube_id, cube_formula_enabled");
/*      */ 
/* 3071 */       ps.setInt(1, financeCubeId);
/*      */ 
/* 3073 */       rs = ps.executeQuery();
/*      */ 
/* 3075 */       if (rs.next())
/*      */       {
/* 3077 */         FinanceCubeInfo localFinanceCubeInfo = new FinanceCubeInfo(rs.getInt("model_id"), rs.getInt("finance_cube_id"), rs.getInt("num_dims"), rs.getInt("cal_hier_id"), rs.getString("cube_formula_enabled").equalsIgnoreCase("Y"));
/*      */         return localFinanceCubeInfo;
/*      */       }
/* 3084 */       throw new ValidationException("Unable to locate finance cube [" + financeCubeId + "]");
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3088 */       throw handleSQLException("getFinanceCubeInfo", e);
/*      */     }
/*      */     finally
/*      */     {
/* 3092 */       closeResultSet(rs);
/* 3093 */       closeStatement(ps);
/* 3094 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */
			public AllFinanceCubesELO getAllFinanceCubesForLoggedUser(int userId) {
			   Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  600 */     PreparedStatement stmt = null;
/*  601 */     ResultSet resultSet = null;
/*  602 */     AllFinanceCubesELO results = new AllFinanceCubesELO();
/*      */     try
/*      */     {
/*  605 */       stmt = getConnection().prepareStatement(SQL_ALL_FINANCE_CUBES_FOR_USER);
/*  606 */       int col = 1;
				 stmt.setInt(1, userId);
/*  607 */       resultSet = stmt.executeQuery();
/*  608 */       while (resultSet.next())
/*      */       {
/*  610 */         col = 2;
/*      */ 
/*  613 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  616 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  619 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  622 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  627 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/*  633 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  639 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/*  644 */         String col1 = resultSet.getString(col++);
/*  645 */         Integer col2 = getWrappedIntegerFromJdbc(resultSet, col++);
/*  646 */         String col3 = resultSet.getString(col++);
/*  647 */         if (resultSet.wasNull())
/*  648 */           col3 = "";
/*  649 */         Boolean col4 = getWrappedBooleanFromJdbc(resultSet, col++);
/*  650 */         Boolean col5 = getWrappedBooleanFromJdbc(resultSet, col++);
/*      */ 
/*  653 */         results.add(erFinanceCube, erModel, col1, col2, col3.equals("Y"), col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  666 */       throw handleSQLException(SQL_ALL_FINANCE_CUBES_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  670 */       closeResultSet(resultSet);
/*  671 */       closeStatement(stmt);
/*  672 */       closeConnection();
/*      */     }
/*      */ 
/*  675 */     if (timer != null) {
/*  676 */       timer.logDebug("getAllFinanceCubesForLoggedUser", " items=" + results.size());
/*      */     }
/*      */ 
/*  680 */     return results;
			}

}

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.FinanceCubeDAO
 * JD-Core Version:    0.6.0
 */