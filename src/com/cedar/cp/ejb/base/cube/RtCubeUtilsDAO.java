/*     */ package com.cedar.cp.ejb.base.cube;
/*     */ 
/*     */ import com.cedar.cp.api.base.EntityList;
/*     */ import com.cedar.cp.dto.base.EntityListImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.SqlBuilder;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import com.cedar.cp.util.common.JdbcUtils;
/*     */ import com.cedar.cp.util.common.JdbcUtils.ColType;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class RtCubeUtilsDAO extends AbstractDAO
/*     */ {
/*  36 */   public static int RT_CUBE_CELL_CALC_TYPE = 0;
/*     */ 
/*  40 */   public static int RT_CUBE_CUBE_FORMULA_TYPE = 1;
/*     */ 
/*     */   public RtCubeUtilsDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public RtCubeUtilsDAO(AbstractDAO abstractDAO)
/*     */   {
/*  25 */     super(abstractDAO);
/*     */   }
/*     */ 
/*     */   public RtCubeUtilsDAO(Connection connection)
/*     */   {
/*  30 */     super(connection);
/*     */   }
/*     */ 
/*     */   public void updateRtCubeDeployments(int modelId, int financeCubeId, int ownerId, int ownerType)
/*     */   {
/*  53 */     CallableStatement cs = null;
/*  54 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */     try
/*     */     {
/*  63 */       cs = getConnection().prepareCall("{ call RT_CUBE_UTILS.UPDATERUNTIMETABLES( ?, ?, ?, ? ) }");
/*  64 */       cs.setInt(1, modelId);
/*  65 */       cs.setInt(2, financeCubeId);
/*  66 */       cs.setInt(3, ownerId);
/*  67 */       cs.setInt(4, ownerType);
/*  68 */       cs.execute();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*  72 */       throw handleSQLException("updateRtCubeDeployments", sqle);
/*     */     }
/*     */     finally
/*     */     {
/*  76 */       closeStatement(cs);
/*  77 */       closeConnection();
/*     */     }
/*  79 */     if (timer != null)
/*  80 */       timer.logDebug("updateRtCubeDeployments");
/*     */   }
/*     */ 
/*     */   public EntityList queryDeploymentIntersections(int model_id, int deployment_id)
/*     */   {
/*  91 */     Timer t = new Timer(this._log);
/*     */ 
/*  93 */     SqlBuilder builder = new SqlBuilder(new String[] { "/* Query for overlap with specific deployment - all dimension approach */", "with params as", "(", "select   <modelId>       as PARAM_MODEL_ID", "        ,<deploymentId>  as PARAM_OWNER_ID", "from    dual", ")", "/* Query deployment lines for model */", ",lines as", "(", "select   RT_CUBE_DEPLOYMENT_ID, FINANCE_CUBE_ID, OWNER_TYPE, OWNER_ID, OWNER_LINE_ID", "        ,DIM_SEQ,STRUCTURE_ID, START_POS, END_POS", "from    RT_CUBE_DEPLOYMENT", "join    RT_CUBE_DEPLOYMENT_ENTRY using (RT_CUBE_DEPLOYMENT_ID)", "join    PARAMS on (MODEL_ID = PARAM_MODEL_ID)", ")", "/* Query overlapping deployment lines */", ",match_lines as", "(", "select   a.RT_CUBE_DEPLOYMENT_ID  A_DEP_ID", "        ,a.OWNER_TYPE             A_OWNER_TYPE", "        ,a.OWNER_ID               A_OWNER_ID", "        ,a.OWNER_LINE_ID          A_LINE_ID", "        ,b.RT_CUBE_DEPLOYMENT_ID  B_DEP_ID", "        ,b.OWNER_TYPE             B_OWNER_TYPE", "        ,b.OWNER_ID               B_OWNER_ID", "        ,b.OWNER_LINE_ID          B_LINE_ID", "        ,a.DIM_SEQ", "from    lines  a", "join    (", "        select  *", "        from    lines", "        join    params on (lines.OWNER_ID = params.PARAM_OWNER_ID)", "        ) b", "on      (", "            (", "                a.FINANCE_CUBE_ID is null", "            or  b.FINANCE_CUBE_ID is null", "            or  a.FINANCE_CUBE_ID = b.FINANCE_CUBE_ID", "            )", "        and a.DIM_SEQ = b.DIM_SEQ", "        and a.OWNER_LINE_ID != b.OWNER_LINE_ID", "        and a.STRUCTURE_ID = b.STRUCTURE_ID", "        and not", "            (", "                a.START_POS > b.END_POS", "            or  a.END_POS < b.START_POS", "            )", "        )", ")", "/* Determine the deployments which overlap in all dimensions */", ",match_grid as", "(", "select   A_DEP_ID, A_OWNER_TYPE, A_OWNER_ID, A_LINE_ID", "        ,B_DEP_ID, B_OWNER_TYPE, B_OWNER_ID, B_LINE_ID", "from    match_lines", "group   by A_DEP_ID, A_OWNER_TYPE, A_OWNER_ID, A_LINE_ID", "          ,B_DEP_ID, B_OWNER_TYPE, B_OWNER_ID, B_LINE_ID", "having  count(DISTINCT DIM_SEQ) =", "        (", "        select  count(*) as DIMENSION_COUNT", "        from    params", "        join    MODEL_DIMENSION_REL", "                on (MODEL_ID = PARAM_MODEL_ID)", "        )", ")", "/* Finally check data types also overlap */", ",full_match as", "(", "select   A_DEP_ID, A_OWNER_TYPE, A_OWNER_ID, A_LINE_ID", "        ,B_DEP_ID, B_OWNER_TYPE, B_OWNER_ID, B_LINE_ID", "        ,a.DATA_TYPE_ID DATA_TYPE_ID", "from    match_grid", "join    (", "        select  RT_CUBE_DEPLOYMENT_ID DEP_ID", "                ,FINANCE_CUBE_ID", "                ,OWNER_TYPE, OWNER_ID, OWNER_LINE_ID, DATA_TYPE_ID", "        from    params", "        join    RT_CUBE_DEPLOYMENT       on (MODEL_ID = PARAM_MODEL_ID)", "        join    RT_CUBE_DEPLOYMENT_DT using (RT_CUBE_DEPLOYMENT_ID)", "        ) a", "        on", "        (", "            a.DEP_ID = A_DEP_ID", "        and a.OWNER_TYPE = A_OWNER_TYPE", "        and a.OWNER_ID = A_OWNER_ID", "        and a.OWNER_LINE_ID = A_LINE_ID", "        )", "join    (", "        select  RT_CUBE_DEPLOYMENT_ID DEP_ID", "                ,FINANCE_CUBE_ID", "                ,OWNER_TYPE, OWNER_ID, OWNER_LINE_ID, DATA_TYPE_ID", "        from    params", "        join    RT_CUBE_DEPLOYMENT       on (MODEL_ID = PARAM_MODEL_ID)", "        join    RT_CUBE_DEPLOYMENT_DT using (RT_CUBE_DEPLOYMENT_ID)", "        ) b", "        on", "        (", "            b.DEP_ID = B_DEP_ID", "        and b.OWNER_TYPE = B_OWNER_TYPE", "        and b.OWNER_ID = B_OWNER_ID", "        and b.OWNER_LINE_ID = B_LINE_ID", "        and a.DATA_TYPE_ID = b.DATA_TYPE_ID", "        )", ")", "/*", "* Join matched overlapping results back to source cell calc", "* or cube formula to include visual ids and line index info", "*/", ",cc_info as", "(", "select   CC_DEPLOYMENT_ID      DEP_ID", "        ,CC_DEPLOYMENT_LINE_ID LINE_ID", "        ,VIS_ID                VIS_ID", "        ,LINE_INDEX+1          LINE_INDEX", "from    CC_DEPLOYMENT", "join    CC_DEPLOYMENT_LINE using (CC_DEPLOYMENT_ID)", ")", ",for_info as", "(", "select   CUBE_FORMULA_ID            DEP_ID", "        ,FORMULA_DEPLOYMENT_LINE_ID LINE_ID", "        ,VIS_ID                     VIS_ID", "        ,LINE_INDEX+1               LINE_INDEX", "from    CUBE_FORMULA", "join    FORMULA_DEPLOYMENT_LINE  using (CUBE_FORMULA_ID)", ")", "select  /*+ no_query_transformation */", "         A_DEP_ID", "        ,nvl(a_cc.VIS_ID,a_for.VIS_ID) A_VIS_ID", "        ,decode(A_OWNER_TYPE,0,'Cell Calculation',1,'Cube Formula','Unknown') A_OWNER_TYPE", "        ,A_OWNER_ID", "        ,A_LINE_ID", "        ,nvl(a_cc.LINE_INDEX,a_for.LINE_INDEX) A_LINE_INDEX", "        ,B_DEP_ID", "        ,nvl(b_cc.VIS_ID,b_for.VIS_ID) B_VIS_ID", "        ,decode(B_OWNER_TYPE,0,'Cell Calculation',1,'Cube Formula','Unknown') B_OWNER_TYPE", "        ,B_OWNER_ID", "        ,B_LINE_ID", "        ,nvl(b_cc.LINE_INDEX,b_for.LINE_INDEX) B_LINE_INDEX", "        ,dt.VIS_ID DATA_TYPE", "from    full_match fm", "join    DATA_TYPE dt using( DATA_TYPE_ID )", "left", "join    cc_info a_cc", "        on", "        (", "          a_cc.DEP_ID = A_OWNER_ID and", "          a_cc.LINE_ID = A_LINE_ID", "        )", "left", "join    cc_info b_cc", "        on", "        (", "          b_cc.DEP_ID = B_OWNER_ID and", "          b_cc.LINE_ID = B_LINE_ID", "        )", "left", "join    for_info a_for", "        on", "        (", "          a_for.DEP_ID = A_OWNER_ID and", "          a_for.LINE_ID = A_LINE_ID", "        )", "left    join for_info b_for", "        on", "        (", "          b_for.DEP_ID = B_OWNER_ID and", "          b_for.LINE_ID = B_LINE_ID", "        )" });
/*     */ 
/* 266 */     SqlExecutor sqle = null;
/* 267 */     ResultSet rs = null;
/*     */ 
/* 269 */     JdbcUtils.ColType[] resultSetMetaData = { new JdbcUtils.ColType("a_dep_id", 0), new JdbcUtils.ColType("a_vis_id", 1), new JdbcUtils.ColType("a_owner_type", 1), new JdbcUtils.ColType("a_owner_id", 1), new JdbcUtils.ColType("a_line_id", 0), new JdbcUtils.ColType("a_line_index", 0), new JdbcUtils.ColType("b_dep_id", 0), new JdbcUtils.ColType("b_vis_id", 1), new JdbcUtils.ColType("b_owner_type", 1), new JdbcUtils.ColType("b_owner_id", 1), new JdbcUtils.ColType("b_line_id", 0), new JdbcUtils.ColType("b_line_index", 0), new JdbcUtils.ColType("data_type", 1) };
/*     */     try
/*     */     {
/* 291 */       sqle = new SqlExecutor("queryDeploymentIntersections", getDataSource(), builder, this._log);
/*     */ 
/* 293 */       sqle.addBindVariable("<modelId>", Integer.valueOf(model_id));
/* 294 */       sqle.addBindVariable("<deploymentId>", Integer.valueOf(deployment_id));
/*     */ 
/* 296 */       EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(resultSetMetaData, sqle.getResultSet());
/*     */       return localEntityListImpl;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 300 */       throw handleSQLException(builder.toString(), e);
/*     */     }
/*     */     finally
/*     */     {
/* 304 */       if (sqle != null)
/* 305 */         sqle.close();
/* 306 */       t.logDebug("queryDeploymentIntersections", ""); } //throw localObject;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 313 */     return "RtCubeUtils";
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.base.cube.RtCubeUtilsDAO
 * JD-Core Version:    0.6.0
 */