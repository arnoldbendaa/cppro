/*      */ package com.cedar.cp.ejb.impl.model.udwp;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.udwp.AllWeightingDeploymentsELO;
/*      */ import com.cedar.cp.dto.model.udwp.WeightingDeploymentCK;
/*      */ import com.cedar.cp.dto.model.udwp.WeightingDeploymentLineCK;
/*      */ import com.cedar.cp.dto.model.udwp.WeightingDeploymentLinePK;
/*      */ import com.cedar.cp.dto.model.udwp.WeightingDeploymentPK;
/*      */ import com.cedar.cp.dto.model.udwp.WeightingDeploymentRefImpl;
/*      */ import com.cedar.cp.dto.model.udwp.WeightingProfileCK;
/*      */ import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
/*      */ import com.cedar.cp.dto.model.udwp.WeightingProfileRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
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
/*      */ 
/*      */ public class WeightingDeploymentDAO extends AbstractDAO
/*      */ {
/*   39 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT.PROFILE_ID,WEIGHTING_DEPLOYMENT.ANY_ACCOUNT,WEIGHTING_DEPLOYMENT.ANY_BUSINESS,WEIGHTING_DEPLOYMENT.ANY_DATA_TYPE,WEIGHTING_DEPLOYMENT.WEIGHTING,WEIGHTING_DEPLOYMENT.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT.UPDATED_TIME,WEIGHTING_DEPLOYMENT.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from WEIGHTING_DEPLOYMENT where    DEPLOYMENT_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into WEIGHTING_DEPLOYMENT ( DEPLOYMENT_ID,PROFILE_ID,ANY_ACCOUNT,ANY_BUSINESS,ANY_DATA_TYPE,WEIGHTING,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update WEIGHTING_DEPLOYMENT set PROFILE_ID = ?,ANY_ACCOUNT = ?,ANY_BUSINESS = ?,ANY_DATA_TYPE = ?,WEIGHTING = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DEPLOYMENT_ID = ? ";
/*  362 */   protected static String SQL_ALL_WEIGHTING_DEPLOYMENTS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,WEIGHTING_PROFILE.PROFILE_ID      ,WEIGHTING_PROFILE.VIS_ID      ,WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID from WEIGHTING_DEPLOYMENT    ,MODEL    ,WEIGHTING_PROFILE where 1=1   and WEIGHTING_DEPLOYMENT.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID   and WEIGHTING_PROFILE.MODEL_ID = MODEL.MODEL_ID ";
//			 protected static String SQL_ALL_WEIGHTING_DEPLOYMENTS_FOR_USER = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,WEIGHTING_PROFILE.PROFILE_ID      ,WEIGHTING_PROFILE.VIS_ID      ,WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID from WEIGHTING_DEPLOYMENT    ,MODEL    ,WEIGHTING_PROFILE where model.model_id in (select distinct model_id from budget_user where user_id = ?) and WEIGHTING_DEPLOYMENT.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID   and WEIGHTING_PROFILE.MODEL_ID = MODEL.MODEL_ID ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from WEIGHTING_DEPLOYMENT where    DEPLOYMENT_ID = ? ";
/*  625 */   private static String[][] SQL_DELETE_CHILDREN = { { "WEIGHTING_DEPLOYMENT_LINE", "delete from WEIGHTING_DEPLOYMENT_LINE where     WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID = ? " } };
/*      */ 
/*  634 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  638 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from WEIGHTING_DEPLOYMENT,WEIGHTING_PROFILE where 1=1 and WEIGHTING_DEPLOYMENT.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WEIGHTING_PROFILE.MODEL_ID = ? order by  WEIGHTING_DEPLOYMENT.PROFILE_ID ,WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID";
/*      */   protected static final String SQL_GET_ALL = " from WEIGHTING_DEPLOYMENT where    PROFILE_ID = ? ";
/* 1187 */   private static final JdbcUtils.ColType[] ALL_DEPLOYMENTS_COL_INFO = { new JdbcUtils.ColType("any_account", "any_account", 1), new JdbcUtils.ColType("act0", "act0", 1), new JdbcUtils.ColType("act1", "act1", 1), new JdbcUtils.ColType("act2", "act2", 1), new JdbcUtils.ColType("act3", "act3", 1), new JdbcUtils.ColType("act_more", "act_more", 1), new JdbcUtils.ColType("any_business", "any_business", 1), new JdbcUtils.ColType("bus0", "bus0", 1), new JdbcUtils.ColType("bus1", "bus1", 1), new JdbcUtils.ColType("bus2", "bus2", 1), new JdbcUtils.ColType("bus3", "bus3", 1), new JdbcUtils.ColType("bus_more", "bus_more", 1), new JdbcUtils.ColType("any_data_type", "any_data_type", 1), new JdbcUtils.ColType("dt0", "dt0", 1), new JdbcUtils.ColType("dt1", "dt1", 1), new JdbcUtils.ColType("dt2", "dt2", 1), new JdbcUtils.ColType("dt3", "dt3", 1), new JdbcUtils.ColType("dt_more", "dt_more", 1), new JdbcUtils.ColType("weighting_profile", "vis_id", 1), new JdbcUtils.ColType("model", "model_visid", 1), new JdbcUtils.ColType("weighting", "weighting", 0), new JdbcUtils.ColType("deployment_id", "deployment_id", 0), new WeightingDeploymentCKColType("deployment", "model_id", "profile_id", "deployment_id") };
/*      */   private static final String QUERY_ALL_DEPLOYMENTS = "select max(any_account) as any_account,\n       max(act0) as act0, max(act1) as act1, max(act2) as act2,\n                max(act3) as act3, max(act_more) as act_more,\n       max(any_business) as any_business,\n       max(bus0) as bus0, max(bus1) as bus1, max(bus2) as bus2,\n                max(bus3) as bus3, max(bus_more) as bus_more,\n       max(any_data_type) as any_data_type,\n       max(dt0) as dt0, max(dt1) as dt1, max(dt2) as dt2,  +\n                max(dt3) as dt3, max(dt_more) as dt_more,\n       vis_id, weighting, deployment_id, profile_id, model_visid, model_id\nfrom\n(\n   select any_account,\n          case when line_idx = 0 then act end as act0,\n          case when line_idx = 1 then act end as act1,\n          case when line_idx = 2 then act end as act2,\n          case when line_idx = 3 then act end as act3,\n          case when line_idx > 3 then act end as act_more,\n          any_business,\n          case when line_idx = 0 then bus end as bus0,\n          case when line_idx = 1 then bus end as bus1,\n          case when line_idx = 2 then bus end as bus2,\n          case when line_idx = 3 then bus end as bus3,\n          case when line_idx > 3 then bus end as bus_more,\n          any_data_type,\n          case when line_idx = 0 then dt end as dt0,\n          case when line_idx = 1 then dt end as dt1,\n          case when line_idx = 2 then dt end as dt2,\n          case when line_idx = 3 then dt end as dt3,\n          case when line_idx > 3 then dt end as dt_more,\n          vis_id,\n          weighting,\n          profile_id,\n          deployment_id,\n          model_visid,\n          model_id\n   from\n   (\n       select wdp.any_account, decode(wdp.account_selection_flag,'Y',act_se.vis_id,('('||act_se.vis_id||')'))  as act,\n              wdp.any_business, decode(wdp.business_selection_flag,'Y',bus_se.vis_id,('('||bus_se.vis_id||')')) as bus, \n              wdp.any_data_type, dt.vis_id as dt,\n              wdp.vis_id,\n              wdp.weighting,\n              wdp.deployment_id,\n              wdp.profile_id,\n              wdp.line_idx,\n              m.vis_id as model_visid,\n              m.model_id\n       from\n       (\n           select wd.any_account, wdl.account_structure_id, wdl.account_structure_element_id, wdl.account_selection_flag,\n                  wd.any_business, wdl.business_structure_id, wdl.business_structure_element_id, wdl.business_selection_flag,\n                  wd.any_data_type, wdl.data_type_id,\n                  wp.vis_id,\n                  wd.weighting,\n                  wd.deployment_id,\n                  wp.profile_id,\n                  wdl.line_idx,\n                  wp.model_id\n           from weighting_deployment_line wdl,\n                weighting_deployment wd,\n                weighting_profile wp\n           where wdl.deployment_id (+) = wd.deployment_id and\n                 wd.profile_id = wp.profile_id                                   \n           order by wp.vis_id, wd.deployment_id, wdl.line_idx\n       ) wdp,\n       structure_element act_se,\n       structure_element bus_se,\n       data_type dt,\n       model m\n       where wdp.account_structure_id = act_se.structure_id (+) and\n             wdp.account_structure_element_id = act_se.structure_element_id (+) and\n             wdp.business_structure_id = bus_se.structure_id (+) and\n             wdp.business_structure_element_id = bus_se.structure_element_id (+) and\n             wdp.data_type_id = dt.data_type_id (+) and\n             wdp.model_id = m.model_id\n   )\n) group by vis_id, weighting, deployment_id, profile_id, model_visid, model_id";
/*      */   protected WeightingDeploymentLineDAO mWeightingDeploymentLineDAO;
/*      */   protected WeightingDeploymentEVO mDetails;
/*      */ 
/*      */   public WeightingDeploymentDAO(Connection connection)
/*      */   {
/*   46 */     super(connection);
/*      */   }
/*      */ 
/*      */   public WeightingDeploymentDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public WeightingDeploymentDAO(DataSource ds)
/*      */   {
/*   62 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected WeightingDeploymentPK getPK()
/*      */   {
/*   70 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(WeightingDeploymentEVO details)
/*      */   {
/*   79 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private WeightingDeploymentEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   99 */     int col = 1;
/*  100 */     WeightingDeploymentEVO evo = new WeightingDeploymentEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), null);
/*      */ 
/*  110 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  111 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  112 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  113 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(WeightingDeploymentEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  118 */     int col = startCol_;
/*  119 */     stmt_.setInt(col++, evo_.getDeploymentId());
/*  120 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(WeightingDeploymentEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  125 */     int col = startCol_;
/*  126 */     stmt_.setInt(col++, evo_.getProfileId());
/*  127 */     if (evo_.getAnyAccount())
/*  128 */       stmt_.setString(col++, "Y");
/*      */     else
/*  130 */       stmt_.setString(col++, " ");
/*  131 */     if (evo_.getAnyBusiness())
/*  132 */       stmt_.setString(col++, "Y");
/*      */     else
/*  134 */       stmt_.setString(col++, " ");
/*  135 */     if (evo_.getAnyDataType())
/*  136 */       stmt_.setString(col++, "Y");
/*      */     else
/*  138 */       stmt_.setString(col++, " ");
/*  139 */     stmt_.setInt(col++, evo_.getWeighting());
/*  140 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  141 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  142 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  143 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(WeightingDeploymentPK pk)
/*      */     throws ValidationException
/*      */   {
/*  159 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  161 */     PreparedStatement stmt = null;
/*  162 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  166 */       stmt = getConnection().prepareStatement("select WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT.PROFILE_ID,WEIGHTING_DEPLOYMENT.ANY_ACCOUNT,WEIGHTING_DEPLOYMENT.ANY_BUSINESS,WEIGHTING_DEPLOYMENT.ANY_DATA_TYPE,WEIGHTING_DEPLOYMENT.WEIGHTING,WEIGHTING_DEPLOYMENT.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT.UPDATED_TIME,WEIGHTING_DEPLOYMENT.CREATED_TIME from WEIGHTING_DEPLOYMENT where    DEPLOYMENT_ID = ? ");
/*      */ 
/*  169 */       int col = 1;
/*  170 */       stmt.setInt(col++, pk.getDeploymentId());
/*      */ 
/*  172 */       resultSet = stmt.executeQuery();
/*      */ 
/*  174 */       if (!resultSet.next()) {
/*  175 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  178 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  179 */       if (this.mDetails.isModified())
/*  180 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  184 */       throw handleSQLException(pk, "select WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT.PROFILE_ID,WEIGHTING_DEPLOYMENT.ANY_ACCOUNT,WEIGHTING_DEPLOYMENT.ANY_BUSINESS,WEIGHTING_DEPLOYMENT.ANY_DATA_TYPE,WEIGHTING_DEPLOYMENT.WEIGHTING,WEIGHTING_DEPLOYMENT.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT.UPDATED_TIME,WEIGHTING_DEPLOYMENT.CREATED_TIME from WEIGHTING_DEPLOYMENT where    DEPLOYMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  188 */       closeResultSet(resultSet);
/*  189 */       closeStatement(stmt);
/*  190 */       closeConnection();
/*      */ 
/*  192 */       if (timer != null)
/*  193 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  228 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  229 */     this.mDetails.postCreateInit();
/*      */ 
/*  231 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  236 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  237 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  238 */       stmt = getConnection().prepareStatement("insert into WEIGHTING_DEPLOYMENT ( DEPLOYMENT_ID,PROFILE_ID,ANY_ACCOUNT,ANY_BUSINESS,ANY_DATA_TYPE,WEIGHTING,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  241 */       int col = 1;
/*  242 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  243 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  246 */       int resultCount = stmt.executeUpdate();
/*  247 */       if (resultCount != 1)
/*      */       {
/*  249 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  252 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  256 */       throw handleSQLException(this.mDetails.getPK(), "insert into WEIGHTING_DEPLOYMENT ( DEPLOYMENT_ID,PROFILE_ID,ANY_ACCOUNT,ANY_BUSINESS,ANY_DATA_TYPE,WEIGHTING,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  260 */       closeStatement(stmt);
/*  261 */       closeConnection();
/*      */ 
/*  263 */       if (timer != null) {
/*  264 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  270 */       getWeightingDeploymentLineDAO().update(this.mDetails.getDeploymentLinesMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  276 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  303 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  307 */     PreparedStatement stmt = null;
/*      */ 
/*  309 */     boolean mainChanged = this.mDetails.isModified();
/*  310 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  314 */       if (getWeightingDeploymentLineDAO().update(this.mDetails.getDeploymentLinesMap())) {
/*  315 */         dependantChanged = true;
/*      */       }
/*  317 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  320 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  321 */         stmt = getConnection().prepareStatement("update WEIGHTING_DEPLOYMENT set PROFILE_ID = ?,ANY_ACCOUNT = ?,ANY_BUSINESS = ?,ANY_DATA_TYPE = ?,WEIGHTING = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DEPLOYMENT_ID = ? ");
/*      */ 
/*  324 */         int col = 1;
/*  325 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  326 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  329 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  332 */         if (resultCount != 1) {
/*  333 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  336 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  345 */       throw handleSQLException(getPK(), "update WEIGHTING_DEPLOYMENT set PROFILE_ID = ?,ANY_ACCOUNT = ?,ANY_BUSINESS = ?,ANY_DATA_TYPE = ?,WEIGHTING = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DEPLOYMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  349 */       closeStatement(stmt);
/*  350 */       closeConnection();
/*      */ 
/*  352 */       if ((timer != null) && (
/*  353 */         (mainChanged) || (dependantChanged)))
/*  354 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllWeightingDeploymentsELO getAllWeightingDeployments()
/*      */   {
/*  395 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  396 */     PreparedStatement stmt = null;
/*  397 */     ResultSet resultSet = null;
/*  398 */     AllWeightingDeploymentsELO results = new AllWeightingDeploymentsELO();
/*      */     try
/*      */     {
/*  401 */       stmt = getConnection().prepareStatement(SQL_ALL_WEIGHTING_DEPLOYMENTS);
/*  402 */       int col = 1;
/*  403 */       resultSet = stmt.executeQuery();
/*  404 */       while (resultSet.next())
/*      */       {
/*  406 */         col = 2;
/*      */ 
/*  409 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  412 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  414 */         WeightingProfilePK pkWeightingProfile = new WeightingProfilePK(resultSet.getInt(col++));
/*      */ 
/*  417 */         String textWeightingProfile = resultSet.getString(col++);
/*      */ 
/*  420 */         WeightingDeploymentPK pkWeightingDeployment = new WeightingDeploymentPK(resultSet.getInt(col++));
/*      */ 
/*  423 */         String textWeightingDeployment = "";
/*      */ 
/*  428 */         WeightingProfileCK ckWeightingProfile = new WeightingProfileCK(pkModel, pkWeightingProfile);
/*      */ 
/*  434 */         WeightingDeploymentCK ckWeightingDeployment = new WeightingDeploymentCK(pkModel, pkWeightingProfile, pkWeightingDeployment);
/*      */ 
/*  441 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  447 */         WeightingProfileRefImpl erWeightingProfile = new WeightingProfileRefImpl(ckWeightingProfile, textWeightingProfile);
/*      */ 
/*  453 */         WeightingDeploymentRefImpl erWeightingDeployment = new WeightingDeploymentRefImpl(ckWeightingDeployment, textWeightingDeployment);
/*      */ 
/*  460 */         results.add(erWeightingDeployment, erWeightingProfile, erModel);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  469 */       throw handleSQLException(SQL_ALL_WEIGHTING_DEPLOYMENTS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  473 */       closeResultSet(resultSet);
/*  474 */       closeStatement(stmt);
/*  475 */       closeConnection();
/*      */     }
/*      */ 
/*  478 */     if (timer != null) {
/*  479 */       timer.logDebug("getAllWeightingDeployments", " items=" + results.size());
/*      */     }
/*      */ 
/*  483 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  500 */     if (items == null) {
/*  501 */       return false;
/*      */     }
/*  503 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  504 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  506 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  510 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  511 */       while (iter3.hasNext())
/*      */       {
/*  513 */         this.mDetails = ((WeightingDeploymentEVO)iter3.next());
/*  514 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  516 */         somethingChanged = true;
/*      */ 
/*  519 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  523 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  524 */       while (iter2.hasNext())
/*      */       {
/*  526 */         this.mDetails = ((WeightingDeploymentEVO)iter2.next());
/*      */ 
/*  529 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  531 */         somethingChanged = true;
/*      */ 
/*  534 */         if (deleteStmt == null) {
/*  535 */           deleteStmt = getConnection().prepareStatement("delete from WEIGHTING_DEPLOYMENT where    DEPLOYMENT_ID = ? ");
/*      */         }
/*      */ 
/*  538 */         int col = 1;
/*  539 */         deleteStmt.setInt(col++, this.mDetails.getDeploymentId());
/*      */ 
/*  541 */         if (this._log.isDebugEnabled()) {
/*  542 */           this._log.debug("update", "WeightingDeployment deleting DeploymentId=" + this.mDetails.getDeploymentId());
/*      */         }
/*      */ 
/*  547 */         deleteStmt.addBatch();
/*      */ 
/*  550 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  555 */       if (deleteStmt != null)
/*      */       {
/*  557 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  559 */         deleteStmt.executeBatch();
/*      */ 
/*  561 */         if (timer2 != null) {
/*  562 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  566 */       Iterator iter1 = items.values().iterator();
/*  567 */       while (iter1.hasNext())
/*      */       {
/*  569 */         this.mDetails = ((WeightingDeploymentEVO)iter1.next());
/*      */ 
/*  571 */         if (this.mDetails.insertPending())
/*      */         {
/*  573 */           somethingChanged = true;
/*  574 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  577 */         if (this.mDetails.isModified())
/*      */         {
/*  579 */           somethingChanged = true;
/*  580 */           doStore(); continue;
/*      */         }
/*      */ 
/*  584 */         if ((this.mDetails.deletePending()) || 
/*  590 */           (!getWeightingDeploymentLineDAO().update(this.mDetails.getDeploymentLinesMap()))) continue;
/*  591 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  603 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  607 */       throw handleSQLException("delete from WEIGHTING_DEPLOYMENT where    DEPLOYMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  611 */       if (deleteStmt != null)
/*      */       {
/*  613 */         closeStatement(deleteStmt);
/*  614 */         closeConnection();
/*      */       }
/*      */ 
/*  617 */       this.mDetails = null;
/*      */ 
/*  619 */       if ((somethingChanged) && 
/*  620 */         (timer != null))
/*  621 */         timer.logDebug("update", "collection"); 
/*  621 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(WeightingDeploymentPK pk)
/*      */   {
/*  647 */     Set emptyStrings = Collections.emptySet();
/*  648 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(WeightingDeploymentPK pk, Set<String> exclusionTables)
/*      */   {
/*  654 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  656 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  658 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  660 */       PreparedStatement stmt = null;
/*      */ 
/*  662 */       int resultCount = 0;
/*  663 */       String s = null;
/*      */       try
/*      */       {
/*  666 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  668 */         if (this._log.isDebugEnabled()) {
/*  669 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  671 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  674 */         int col = 1;
/*  675 */         stmt.setInt(col++, pk.getDeploymentId());
/*      */ 
/*  678 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  682 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  686 */         closeStatement(stmt);
/*  687 */         closeConnection();
/*      */ 
/*  689 */         if (timer != null) {
/*  690 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  694 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  696 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  698 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  700 */       PreparedStatement stmt = null;
/*      */ 
/*  702 */       int resultCount = 0;
/*  703 */       String s = null;
/*      */       try
/*      */       {
/*  706 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  708 */         if (this._log.isDebugEnabled()) {
/*  709 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  711 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  714 */         int col = 1;
/*  715 */         stmt.setInt(col++, pk.getDeploymentId());
/*      */ 
/*  718 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  722 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  726 */         closeStatement(stmt);
/*  727 */         closeConnection();
/*      */ 
/*  729 */         if (timer != null)
/*  730 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*      */   {
/*  754 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  756 */     PreparedStatement stmt = null;
/*  757 */     ResultSet resultSet = null;
/*      */ 
/*  759 */     int itemCount = 0;
/*      */ 
/*  761 */     WeightingProfileEVO owningEVO = null;
/*  762 */     Iterator ownersIter = owners.iterator();
/*  763 */     while (ownersIter.hasNext())
/*      */     {
/*  765 */       owningEVO = (WeightingProfileEVO)ownersIter.next();
/*  766 */       owningEVO.setWeightingDeploymentsAllItemsLoaded(true);
/*      */     }
/*  768 */     ownersIter = owners.iterator();
/*  769 */     owningEVO = (WeightingProfileEVO)ownersIter.next();
/*  770 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  774 */       stmt = getConnection().prepareStatement("select WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT.PROFILE_ID,WEIGHTING_DEPLOYMENT.ANY_ACCOUNT,WEIGHTING_DEPLOYMENT.ANY_BUSINESS,WEIGHTING_DEPLOYMENT.ANY_DATA_TYPE,WEIGHTING_DEPLOYMENT.WEIGHTING,WEIGHTING_DEPLOYMENT.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT.UPDATED_TIME,WEIGHTING_DEPLOYMENT.CREATED_TIME from WEIGHTING_DEPLOYMENT,WEIGHTING_PROFILE where 1=1 and WEIGHTING_DEPLOYMENT.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WEIGHTING_PROFILE.MODEL_ID = ? order by  WEIGHTING_DEPLOYMENT.PROFILE_ID ,WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID");
/*      */ 
/*  776 */       int col = 1;
/*  777 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  779 */       resultSet = stmt.executeQuery();
/*      */ 
/*  782 */       while (resultSet.next())
/*      */       {
/*  784 */         itemCount++;
/*  785 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  790 */         while (this.mDetails.getProfileId() != owningEVO.getProfileId())
/*      */         {
/*  794 */           if (!ownersIter.hasNext())
/*      */           {
/*  796 */             this._log.debug("bulkGetAll", "can't find owning [ProfileId=" + this.mDetails.getProfileId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  800 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  801 */             ownersIter = owners.iterator();
/*  802 */             while (ownersIter.hasNext())
/*      */             {
/*  804 */               owningEVO = (WeightingProfileEVO)ownersIter.next();
/*  805 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  807 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  809 */           owningEVO = (WeightingProfileEVO)ownersIter.next();
/*      */         }
/*  811 */         if (owningEVO.getWeightingDeployments() == null)
/*      */         {
/*  813 */           theseItems = new ArrayList();
/*  814 */           owningEVO.setWeightingDeployments(theseItems);
/*  815 */           owningEVO.setWeightingDeploymentsAllItemsLoaded(true);
/*      */         }
/*  817 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  820 */       if (timer != null) {
/*  821 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  824 */       if ((itemCount > 0) && (dependants.indexOf("<40>") > -1))
/*      */       {
/*  826 */         getWeightingDeploymentLineDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  830 */       throw handleSQLException("select WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT.PROFILE_ID,WEIGHTING_DEPLOYMENT.ANY_ACCOUNT,WEIGHTING_DEPLOYMENT.ANY_BUSINESS,WEIGHTING_DEPLOYMENT.ANY_DATA_TYPE,WEIGHTING_DEPLOYMENT.WEIGHTING,WEIGHTING_DEPLOYMENT.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT.UPDATED_TIME,WEIGHTING_DEPLOYMENT.CREATED_TIME from WEIGHTING_DEPLOYMENT,WEIGHTING_PROFILE where 1=1 and WEIGHTING_DEPLOYMENT.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WEIGHTING_PROFILE.MODEL_ID = ? order by  WEIGHTING_DEPLOYMENT.PROFILE_ID ,WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  834 */       closeResultSet(resultSet);
/*  835 */       closeStatement(stmt);
/*  836 */       closeConnection();
/*      */ 
/*  838 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectProfileId, String dependants, Collection currentList)
/*      */   {
/*  863 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  864 */     PreparedStatement stmt = null;
/*  865 */     ResultSet resultSet = null;
/*      */ 
/*  867 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  871 */       stmt = getConnection().prepareStatement("select WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT.PROFILE_ID,WEIGHTING_DEPLOYMENT.ANY_ACCOUNT,WEIGHTING_DEPLOYMENT.ANY_BUSINESS,WEIGHTING_DEPLOYMENT.ANY_DATA_TYPE,WEIGHTING_DEPLOYMENT.WEIGHTING,WEIGHTING_DEPLOYMENT.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT.UPDATED_TIME,WEIGHTING_DEPLOYMENT.CREATED_TIME from WEIGHTING_DEPLOYMENT where    PROFILE_ID = ? ");
/*      */ 
/*  873 */       int col = 1;
/*  874 */       stmt.setInt(col++, selectProfileId);
/*      */ 
/*  876 */       resultSet = stmt.executeQuery();
/*      */ 
/*  878 */       while (resultSet.next())
/*      */       {
/*  880 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  883 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  886 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  889 */       if (currentList != null)
/*      */       {
/*  892 */         ListIterator iter = items.listIterator();
/*  893 */         WeightingDeploymentEVO currentEVO = null;
/*  894 */         WeightingDeploymentEVO newEVO = null;
/*  895 */         while (iter.hasNext())
/*      */         {
/*  897 */           newEVO = (WeightingDeploymentEVO)iter.next();
/*  898 */           Iterator iter2 = currentList.iterator();
/*  899 */           while (iter2.hasNext())
/*      */           {
/*  901 */             currentEVO = (WeightingDeploymentEVO)iter2.next();
/*  902 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  904 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  910 */         Iterator iter2 = currentList.iterator();
/*  911 */         while (iter2.hasNext())
/*      */         {
/*  913 */           currentEVO = (WeightingDeploymentEVO)iter2.next();
/*  914 */           if (currentEVO.insertPending()) {
/*  915 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  919 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  923 */       throw handleSQLException("select WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID,WEIGHTING_DEPLOYMENT.PROFILE_ID,WEIGHTING_DEPLOYMENT.ANY_ACCOUNT,WEIGHTING_DEPLOYMENT.ANY_BUSINESS,WEIGHTING_DEPLOYMENT.ANY_DATA_TYPE,WEIGHTING_DEPLOYMENT.WEIGHTING,WEIGHTING_DEPLOYMENT.UPDATED_BY_USER_ID,WEIGHTING_DEPLOYMENT.UPDATED_TIME,WEIGHTING_DEPLOYMENT.CREATED_TIME from WEIGHTING_DEPLOYMENT where    PROFILE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  927 */       closeResultSet(resultSet);
/*  928 */       closeStatement(stmt);
/*  929 */       closeConnection();
/*      */ 
/*  931 */       if (timer != null) {
/*  932 */         timer.logDebug("getAll", " ProfileId=" + selectProfileId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  937 */     return items;
/*      */   }
/*      */ 
/*      */   public WeightingDeploymentEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  954 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  957 */     if (this.mDetails == null) {
/*  958 */       doLoad(((WeightingDeploymentCK)paramCK).getWeightingDeploymentPK());
/*      */     }
/*  960 */     else if (!this.mDetails.getPK().equals(((WeightingDeploymentCK)paramCK).getWeightingDeploymentPK())) {
/*  961 */       doLoad(((WeightingDeploymentCK)paramCK).getWeightingDeploymentPK());
/*      */     }
/*      */ 
/*  964 */     if ((dependants.indexOf("<40>") > -1) && (!this.mDetails.isDeploymentLinesAllItemsLoaded()))
/*      */     {
/*  969 */       this.mDetails.setDeploymentLines(getWeightingDeploymentLineDAO().getAll(this.mDetails.getDeploymentId(), dependants, this.mDetails.getDeploymentLines()));
/*      */ 
/*  976 */       this.mDetails.setDeploymentLinesAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  979 */     if ((paramCK instanceof WeightingDeploymentLineCK))
/*      */     {
/*  981 */       if (this.mDetails.getDeploymentLines() == null) {
/*  982 */         this.mDetails.loadDeploymentLinesItem(getWeightingDeploymentLineDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  985 */         WeightingDeploymentLinePK pk = ((WeightingDeploymentLineCK)paramCK).getWeightingDeploymentLinePK();
/*  986 */         WeightingDeploymentLineEVO evo = this.mDetails.getDeploymentLinesItem(pk);
/*  987 */         if (evo == null) {
/*  988 */           this.mDetails.loadDeploymentLinesItem(getWeightingDeploymentLineDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  993 */     WeightingDeploymentEVO details = new WeightingDeploymentEVO();
/*  994 */     details = this.mDetails.deepClone();
/*      */ 
/*  996 */     if (timer != null) {
/*  997 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  999 */     return details;
/*      */   }
/*      */ 
/*      */   public WeightingDeploymentEVO getDetails(ModelCK paramCK, WeightingDeploymentEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1005 */     WeightingDeploymentEVO savedEVO = this.mDetails;
/* 1006 */     this.mDetails = paramEVO;
/* 1007 */     WeightingDeploymentEVO newEVO = getDetails(paramCK, dependants);
/* 1008 */     this.mDetails = savedEVO;
/* 1009 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public WeightingDeploymentEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1015 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1019 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1022 */     WeightingDeploymentEVO details = this.mDetails.deepClone();
/*      */ 
/* 1024 */     if (timer != null) {
/* 1025 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1027 */     return details;
/*      */   }
/*      */ 
/*      */   protected WeightingDeploymentLineDAO getWeightingDeploymentLineDAO()
/*      */   {
/* 1036 */     if (this.mWeightingDeploymentLineDAO == null)
/*      */     {
/* 1038 */       if (this.mDataSource != null)
/* 1039 */         this.mWeightingDeploymentLineDAO = new WeightingDeploymentLineDAO(this.mDataSource);
/*      */       else {
/* 1041 */         this.mWeightingDeploymentLineDAO = new WeightingDeploymentLineDAO(getConnection());
/*      */       }
/*      */     }
/* 1044 */     return this.mWeightingDeploymentLineDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1049 */     return "WeightingDeployment";
/*      */   }
/*      */ 
/*      */   public WeightingDeploymentRefImpl getRef(WeightingDeploymentPK paramWeightingDeploymentPK)
/*      */   {
/* 1054 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1055 */     PreparedStatement stmt = null;
/* 1056 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1059 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,WEIGHTING_PROFILE.PROFILE_ID from WEIGHTING_DEPLOYMENT,MODEL,WEIGHTING_PROFILE where 1=1 and WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID = ? and WEIGHTING_DEPLOYMENT.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WEIGHTING_PROFILE.PROFILE_ID = MODEL.PROFILE_ID");
/* 1060 */       int col = 1;
/* 1061 */       stmt.setInt(col++, paramWeightingDeploymentPK.getDeploymentId());
/*      */ 
/* 1063 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1065 */       if (!resultSet.next()) {
/* 1066 */         throw new RuntimeException(getEntityName() + " getRef " + paramWeightingDeploymentPK + " not found");
/*      */       }
/* 1068 */       col = 2;
/* 1069 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1073 */       WeightingProfilePK newWeightingProfilePK = new WeightingProfilePK(resultSet.getInt(col++));
/*      */ 
/* 1077 */       String textWeightingDeployment = "";
/* 1078 */       WeightingDeploymentCK ckWeightingDeployment = new WeightingDeploymentCK(newModelPK, newWeightingProfilePK, paramWeightingDeploymentPK);
/*      */ 
/* 1084 */       WeightingDeploymentRefImpl localWeightingDeploymentRefImpl = new WeightingDeploymentRefImpl(ckWeightingDeployment, textWeightingDeployment);
/*      */       return localWeightingDeploymentRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1089 */       throw handleSQLException(paramWeightingDeploymentPK, "select 0,MODEL.MODEL_ID,WEIGHTING_PROFILE.PROFILE_ID from WEIGHTING_DEPLOYMENT,MODEL,WEIGHTING_PROFILE where 1=1 and WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID = ? and WEIGHTING_DEPLOYMENT.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WEIGHTING_PROFILE.PROFILE_ID = MODEL.PROFILE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1093 */       closeResultSet(resultSet);
/* 1094 */       closeStatement(stmt);
/* 1095 */       closeConnection();
/*      */ 
/* 1097 */       if (timer != null)
/* 1098 */         timer.logDebug("getRef", paramWeightingDeploymentPK); 
/* 1098 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1110 */     if (c == null)
/* 1111 */       return;
/* 1112 */     Iterator iter = c.iterator();
/* 1113 */     while (iter.hasNext())
/*      */     {
/* 1115 */       WeightingDeploymentEVO evo = (WeightingDeploymentEVO)iter.next();
/* 1116 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(WeightingDeploymentEVO evo, String dependants)
/*      */   {
/* 1130 */     if (evo.insertPending()) {
/* 1131 */       return;
/*      */     }
/* 1133 */     if (evo.getDeploymentId() < 1) {
/* 1134 */       return;
/*      */     }
/*      */ 
/* 1138 */     if (dependants.indexOf("<40>") > -1)
/*      */     {
/* 1141 */       if (!evo.isDeploymentLinesAllItemsLoaded())
/*      */       {
/* 1143 */         evo.setDeploymentLines(getWeightingDeploymentLineDAO().getAll(evo.getDeploymentId(), dependants, evo.getDeploymentLines()));
/*      */ 
/* 1150 */         evo.setDeploymentLinesAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public EntityList queryAllDeployments()
/*      */   {
/* 1300 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1301 */     PreparedStatement stmt = null;
/* 1302 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1305 */       stmt = getConnection().prepareStatement("select max(any_account) as any_account,\n       max(act0) as act0, max(act1) as act1, max(act2) as act2,\n                max(act3) as act3, max(act_more) as act_more,\n       max(any_business) as any_business,\n       max(bus0) as bus0, max(bus1) as bus1, max(bus2) as bus2,\n                max(bus3) as bus3, max(bus_more) as bus_more,\n       max(any_data_type) as any_data_type,\n       max(dt0) as dt0, max(dt1) as dt1, max(dt2) as dt2,  +\n                max(dt3) as dt3, max(dt_more) as dt_more,\n       vis_id, weighting, deployment_id, profile_id, model_visid, model_id\nfrom\n(\n   select any_account,\n          case when line_idx = 0 then act end as act0,\n          case when line_idx = 1 then act end as act1,\n          case when line_idx = 2 then act end as act2,\n          case when line_idx = 3 then act end as act3,\n          case when line_idx > 3 then act end as act_more,\n          any_business,\n          case when line_idx = 0 then bus end as bus0,\n          case when line_idx = 1 then bus end as bus1,\n          case when line_idx = 2 then bus end as bus2,\n          case when line_idx = 3 then bus end as bus3,\n          case when line_idx > 3 then bus end as bus_more,\n          any_data_type,\n          case when line_idx = 0 then dt end as dt0,\n          case when line_idx = 1 then dt end as dt1,\n          case when line_idx = 2 then dt end as dt2,\n          case when line_idx = 3 then dt end as dt3,\n          case when line_idx > 3 then dt end as dt_more,\n          vis_id,\n          weighting,\n          profile_id,\n          deployment_id,\n          model_visid,\n          model_id\n   from\n   (\n       select wdp.any_account, decode(wdp.account_selection_flag,'Y',act_se.vis_id,('('||act_se.vis_id||')'))  as act,\n              wdp.any_business, decode(wdp.business_selection_flag,'Y',bus_se.vis_id,('('||bus_se.vis_id||')')) as bus, \n              wdp.any_data_type, dt.vis_id as dt,\n              wdp.vis_id,\n              wdp.weighting,\n              wdp.deployment_id,\n              wdp.profile_id,\n              wdp.line_idx,\n              m.vis_id as model_visid,\n              m.model_id\n       from\n       (\n           select wd.any_account, wdl.account_structure_id, wdl.account_structure_element_id, wdl.account_selection_flag,\n                  wd.any_business, wdl.business_structure_id, wdl.business_structure_element_id, wdl.business_selection_flag,\n                  wd.any_data_type, wdl.data_type_id,\n                  wp.vis_id,\n                  wd.weighting,\n                  wd.deployment_id,\n                  wp.profile_id,\n                  wdl.line_idx,\n                  wp.model_id\n           from weighting_deployment_line wdl,\n                weighting_deployment wd,\n                weighting_profile wp\n           where wdl.deployment_id (+) = wd.deployment_id and\n                 wd.profile_id = wp.profile_id                                   \n           order by wp.vis_id, wd.deployment_id, wdl.line_idx\n       ) wdp,\n       structure_element act_se,\n       structure_element bus_se,\n       data_type dt,\n       model m\n       where wdp.account_structure_id = act_se.structure_id (+) and\n             wdp.account_structure_element_id = act_se.structure_element_id (+) and\n             wdp.business_structure_id = bus_se.structure_id (+) and\n             wdp.business_structure_element_id = bus_se.structure_element_id (+) and\n             wdp.data_type_id = dt.data_type_id (+) and\n             wdp.model_id = m.model_id\n   )\n) group by vis_id, weighting, deployment_id, profile_id, model_visid, model_id");
/* 1306 */       rs = stmt.executeQuery();
/* 1307 */       EntityList result = JdbcUtils.extractToEntityListImpl(ALL_DEPLOYMENTS_COL_INFO, rs);
/* 1308 */       if (timer != null)
/* 1309 */         timer.logDebug("queryAllDeployments");
/* 1310 */       EntityList localEntityList1 = result;
/*      */       return localEntityList1;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1314 */       throw handleSQLException("select max(any_account) as any_account,\n       max(act0) as act0, max(act1) as act1, max(act2) as act2,\n                max(act3) as act3, max(act_more) as act_more,\n       max(any_business) as any_business,\n       max(bus0) as bus0, max(bus1) as bus1, max(bus2) as bus2,\n                max(bus3) as bus3, max(bus_more) as bus_more,\n       max(any_data_type) as any_data_type,\n       max(dt0) as dt0, max(dt1) as dt1, max(dt2) as dt2,  +\n                max(dt3) as dt3, max(dt_more) as dt_more,\n       vis_id, weighting, deployment_id, profile_id, model_visid, model_id\nfrom\n(\n   select any_account,\n          case when line_idx = 0 then act end as act0,\n          case when line_idx = 1 then act end as act1,\n          case when line_idx = 2 then act end as act2,\n          case when line_idx = 3 then act end as act3,\n          case when line_idx > 3 then act end as act_more,\n          any_business,\n          case when line_idx = 0 then bus end as bus0,\n          case when line_idx = 1 then bus end as bus1,\n          case when line_idx = 2 then bus end as bus2,\n          case when line_idx = 3 then bus end as bus3,\n          case when line_idx > 3 then bus end as bus_more,\n          any_data_type,\n          case when line_idx = 0 then dt end as dt0,\n          case when line_idx = 1 then dt end as dt1,\n          case when line_idx = 2 then dt end as dt2,\n          case when line_idx = 3 then dt end as dt3,\n          case when line_idx > 3 then dt end as dt_more,\n          vis_id,\n          weighting,\n          profile_id,\n          deployment_id,\n          model_visid,\n          model_id\n   from\n   (\n       select wdp.any_account, decode(wdp.account_selection_flag,'Y',act_se.vis_id,('('||act_se.vis_id||')'))  as act,\n              wdp.any_business, decode(wdp.business_selection_flag,'Y',bus_se.vis_id,('('||bus_se.vis_id||')')) as bus, \n              wdp.any_data_type, dt.vis_id as dt,\n              wdp.vis_id,\n              wdp.weighting,\n              wdp.deployment_id,\n              wdp.profile_id,\n              wdp.line_idx,\n              m.vis_id as model_visid,\n              m.model_id\n       from\n       (\n           select wd.any_account, wdl.account_structure_id, wdl.account_structure_element_id, wdl.account_selection_flag,\n                  wd.any_business, wdl.business_structure_id, wdl.business_structure_element_id, wdl.business_selection_flag,\n                  wd.any_data_type, wdl.data_type_id,\n                  wp.vis_id,\n                  wd.weighting,\n                  wd.deployment_id,\n                  wp.profile_id,\n                  wdl.line_idx,\n                  wp.model_id\n           from weighting_deployment_line wdl,\n                weighting_deployment wd,\n                weighting_profile wp\n           where wdl.deployment_id (+) = wd.deployment_id and\n                 wd.profile_id = wp.profile_id                                   \n           order by wp.vis_id, wd.deployment_id, wdl.line_idx\n       ) wdp,\n       structure_element act_se,\n       structure_element bus_se,\n       data_type dt,\n       model m\n       where wdp.account_structure_id = act_se.structure_id (+) and\n             wdp.account_structure_element_id = act_se.structure_element_id (+) and\n             wdp.business_structure_id = bus_se.structure_id (+) and\n             wdp.business_structure_element_id = bus_se.structure_element_id (+) and\n             wdp.data_type_id = dt.data_type_id (+) and\n             wdp.model_id = m.model_id\n   )\n) group by vis_id, weighting, deployment_id, profile_id, model_visid, model_id", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1318 */       closeResultSet(rs);
/* 1319 */       closeStatement(stmt);
/* 1320 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public static class WeightingDeploymentCKColType extends JdbcUtils.ColType
/*      */   {
/*      */     private String mModelIdColName;
/*      */     private String mProfileIdColName;
/*      */ 
/*      */     public WeightingDeploymentCKColType(String name, String modelIdColname, String profileIdColName, String deploymentIdColName)
/*      */     {
/* 1170 */       super(name, deploymentIdColName, 0);
/* 1171 */       this.mModelIdColName = modelIdColname;
/* 1172 */       this.mProfileIdColName = profileIdColName;
/*      */     }
/*      */ 
/*      */     public Object extract(ResultSet rs) throws SQLException
/*      */     {
/* 1177 */       ModelPK modelPK = new ModelPK(rs.getInt(this.mModelIdColName));
/* 1178 */       WeightingProfilePK profilePK = new WeightingProfilePK(rs.getInt(this.mProfileIdColName));
/* 1179 */       WeightingDeploymentPK deploymentPK = new WeightingDeploymentPK(rs.getInt(this.mColName));
/* 1180 */       return new WeightingDeploymentCK(modelPK, profilePK, deploymentPK);
/*      */     }
/*      */   }
/*      */
			public EntityList getAllWeightingDeploymentsForLoggedUser(int userId) {
				Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  396 */     PreparedStatement stmt = null;
/*  397 */     ResultSet resultSet = null;
			   EntityList result = null;
/*      */     try
/*      */     {
	/* 1305 */   stmt = getConnection().prepareStatement("select max(any_account) as any_account,\n       max(act0) as act0, max(act1) as act1, max(act2) as act2,\n                max(act3) as act3, max(act_more) as act_more,\n       max(any_business) as any_business,\n       max(bus0) as bus0, max(bus1) as bus1, max(bus2) as bus2,\n                max(bus3) as bus3, max(bus_more) as bus_more,\n       max(any_data_type) as any_data_type,\n       max(dt0) as dt0, max(dt1) as dt1, max(dt2) as dt2,  +\n                max(dt3) as dt3, max(dt_more) as dt_more,\n       vis_id, weighting, deployment_id, profile_id, model_visid, model_id\nfrom\n(\n   select any_account,\n          case when line_idx = 0 then act end as act0,\n          case when line_idx = 1 then act end as act1,\n          case when line_idx = 2 then act end as act2,\n          case when line_idx = 3 then act end as act3,\n          case when line_idx > 3 then act end as act_more,\n          any_business,\n          case when line_idx = 0 then bus end as bus0,\n          case when line_idx = 1 then bus end as bus1,\n          case when line_idx = 2 then bus end as bus2,\n          case when line_idx = 3 then bus end as bus3,\n          case when line_idx > 3 then bus end as bus_more,\n          any_data_type,\n          case when line_idx = 0 then dt end as dt0,\n          case when line_idx = 1 then dt end as dt1,\n          case when line_idx = 2 then dt end as dt2,\n          case when line_idx = 3 then dt end as dt3,\n          case when line_idx > 3 then dt end as dt_more,\n          vis_id,\n          weighting,\n          profile_id,\n          deployment_id,\n          model_visid,\n          model_id\n   from\n   (\n       select wdp.any_account, decode(wdp.account_selection_flag,'Y',act_se.vis_id,('('||act_se.vis_id||')'))  as act,\n              wdp.any_business, decode(wdp.business_selection_flag,'Y',bus_se.vis_id,('('||bus_se.vis_id||')')) as bus, \n              wdp.any_data_type, dt.vis_id as dt,\n              wdp.vis_id,\n              wdp.weighting,\n              wdp.deployment_id,\n              wdp.profile_id,\n              wdp.line_idx,\n              m.vis_id as model_visid,\n              m.model_id\n       from\n       (\n           select wd.any_account, wdl.account_structure_id, wdl.account_structure_element_id, wdl.account_selection_flag,\n                  wd.any_business, wdl.business_structure_id, wdl.business_structure_element_id, wdl.business_selection_flag,\n                  wd.any_data_type, wdl.data_type_id,\n                  wp.vis_id,\n                  wd.weighting,\n                  wd.deployment_id,\n                  wp.profile_id,\n                  wdl.line_idx,\n                  wp.model_id\n           from weighting_deployment_line wdl,\n                weighting_deployment wd,\n                weighting_profile wp\n           where wdl.deployment_id (+) = wd.deployment_id and\n                 wd.profile_id = wp.profile_id                                   \n           order by wp.vis_id, wd.deployment_id, wdl.line_idx\n       ) wdp,\n       structure_element act_se,\n       structure_element bus_se,\n       data_type dt,\n       model m\n       where wdp.account_structure_id = act_se.structure_id (+) and\n             wdp.account_structure_element_id = act_se.structure_element_id (+) and\n             wdp.business_structure_id = bus_se.structure_id (+) and\n             wdp.business_structure_element_id = bus_se.structure_element_id (+) and\n             wdp.data_type_id = dt.data_type_id (+) and\n             wdp.model_id = m.model_id\n and m.model_id in (select distinct model_id from budget_user where user_id = ?)  )\n) group by vis_id, weighting, deployment_id, profile_id, model_visid, model_id");
/*  402 */       int col = 1;
				 stmt.setInt(1, userId);
/*  403 */       resultSet = stmt.executeQuery();
/* 1307 */       result = JdbcUtils.extractToEntityListImpl(ALL_DEPLOYMENTS_COL_INFO, resultSet);
/* 1308 */       if (timer != null)
/* 1309 */         timer.logDebug("queryAllDeployments");
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  469 */       throw handleSQLException("long sql", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  473 */       closeResultSet(resultSet);
/*  474 */       closeStatement(stmt);
/*  475 */       closeConnection();
/*      */     }
/*      */ 
/*  483 */     return result;
			}

}

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.udwp.WeightingDeploymentDAO
 * JD-Core Version:    0.6.0
 */