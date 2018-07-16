/*      */ package com.cedar.cp.ejb.impl.model.virement;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.dimension.StructureElementKey;
/*      */ import com.cedar.cp.api.user.UserRef;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelDimensionsELO;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.virement.AllVirementRequestsELO;
/*      */ import com.cedar.cp.dto.model.virement.VirementAuthPointCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementAuthPointPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestGroupCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestGroupPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestRefImpl;
/*      */ import com.cedar.cp.dto.user.UserPK;
/*      */ import com.cedar.cp.dto.user.UserRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*      */ import com.cedar.cp.ejb.impl.dimension.StructureElementEVO;
/*      */ import com.cedar.cp.ejb.impl.model.ModelDAO;
/*      */ import com.cedar.cp.ejb.impl.model.ModelEVO;
/*      */ import com.cedar.cp.util.GeneralUtils;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.SqlBuilder;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import com.cedar.cp.util.XmlUtils;
/*      */ import com.cedar.cp.util.common.JdbcUtils;
/*      */ import com.cedar.cp.util.common.JdbcUtils.ColType;
/*      */ import com.cedar.cp.util.common.JdbcUtils.ModelRefColType;
/*      */ import com.cedar.cp.util.common.JdbcUtils.UserRefColType;
/*      */ import com.cedar.cp.util.common.JdbcUtils.VirementRequestRefColType;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class VirementRequestDAO extends AbstractDAO
/*      */ {
/*   67 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select VIREMENT_REQUEST.REQUEST_ID,VIREMENT_REQUEST.MODEL_ID,VIREMENT_REQUEST.FINANCE_CUBE_ID,VIREMENT_REQUEST.BUDGET_CYCLE_ID,VIREMENT_REQUEST.REQUEST_STATUS,VIREMENT_REQUEST.USER_ID,VIREMENT_REQUEST.REASON,VIREMENT_REQUEST.REFERENCE,VIREMENT_REQUEST.DATE_SUBMITTED,VIREMENT_REQUEST.BUDGET_ACTIVITY_ID,VIREMENT_REQUEST.VERSION_NUM,VIREMENT_REQUEST.UPDATED_BY_USER_ID,VIREMENT_REQUEST.UPDATED_TIME,VIREMENT_REQUEST.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from VIREMENT_REQUEST where    REQUEST_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into VIREMENT_REQUEST ( REQUEST_ID,MODEL_ID,FINANCE_CUBE_ID,BUDGET_CYCLE_ID,REQUEST_STATUS,USER_ID,REASON,REFERENCE,DATE_SUBMITTED,BUDGET_ACTIVITY_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update VIREMENT_REQUEST set MODEL_ID = ?,FINANCE_CUBE_ID = ?,BUDGET_CYCLE_ID = ?,REQUEST_STATUS = ?,USER_ID = ?,REASON = ?,REFERENCE = ?,DATE_SUBMITTED = ?,BUDGET_ACTIVITY_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REQUEST_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from VIREMENT_REQUEST where REQUEST_ID = ?";
/*  473 */   protected static String SQL_ALL_VIREMENT_REQUESTS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,VIREMENT_REQUEST.REQUEST_ID      ,VIREMENT_REQUEST.REQUEST_ID      ,VIREMENT_REQUEST.USER_ID      ,VIREMENT_REQUEST.MODEL_ID      ,VIREMENT_REQUEST.CREATED_TIME      ,VIREMENT_REQUEST.BUDGET_ACTIVITY_ID from VIREMENT_REQUEST    ,MODEL where 1=1   and VIREMENT_REQUEST.MODEL_ID = MODEL.MODEL_ID  order by VIREMENT_REQUEST.REQUEST_ID";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from VIREMENT_REQUEST where    REQUEST_ID = ? ";
/*  730 */   private static String[][] SQL_DELETE_CHILDREN = { { "VIREMENT_REQUEST_GROUP", "delete from VIREMENT_REQUEST_GROUP where     VIREMENT_REQUEST_GROUP.REQUEST_ID = ? " }, { "VIREMENT_AUTH_POINT", "delete from VIREMENT_AUTH_POINT where     VIREMENT_AUTH_POINT.REQUEST_ID = ? " } };
/*      */ 
/*  744 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "VIREMENT_REQUEST_LINE", "delete from VIREMENT_REQUEST_LINE VirementRequestLine where exists (select * from VIREMENT_REQUEST_LINE,VIREMENT_REQUEST_GROUP,VIREMENT_REQUEST where     VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID = VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VirementRequestLine.REQUEST_GROUP_ID = VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID " }, { "VIREMENT_LINE_SPREAD", "delete from VIREMENT_LINE_SPREAD VirementLineSpread where exists (select * from VIREMENT_LINE_SPREAD,VIREMENT_REQUEST_LINE,VIREMENT_REQUEST_GROUP,VIREMENT_REQUEST where     VIREMENT_LINE_SPREAD.REQUEST_LINE_ID = VIREMENT_REQUEST_LINE.REQUEST_LINE_ID and VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID = VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VirementLineSpread.REQUEST_LINE_ID = VIREMENT_LINE_SPREAD.REQUEST_LINE_ID " }, { "VIREMENT_AUTHORISERS", "delete from VIREMENT_AUTHORISERS VirementAuthorisers where exists (select * from VIREMENT_AUTHORISERS,VIREMENT_AUTH_POINT,VIREMENT_REQUEST where     VIREMENT_AUTHORISERS.AUTH_POINT_ID = VIREMENT_AUTH_POINT.AUTH_POINT_ID and VIREMENT_AUTH_POINT.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VirementAuthorisers.AUTH_POINT_ID = VIREMENT_AUTHORISERS.AUTH_POINT_ID " }, { "VIREMENT_AUTH_POINT_LINK", "delete from VIREMENT_AUTH_POINT_LINK VirementAuthPointLink where exists (select * from VIREMENT_AUTH_POINT_LINK,VIREMENT_AUTH_POINT,VIREMENT_REQUEST where     VIREMENT_AUTH_POINT_LINK.AUTH_POINT_ID = VIREMENT_AUTH_POINT.AUTH_POINT_ID and VIREMENT_AUTH_POINT.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VirementAuthPointLink.AUTH_POINT_ID = VIREMENT_AUTH_POINT_LINK.AUTH_POINT_ID " } };
/*      */ 
/*  794 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and VIREMENT_REQUEST.REQUEST_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from VIREMENT_REQUEST where 1=1 and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_REQUEST.REQUEST_ID";
/*      */   protected static final String SQL_GET_ALL = " from VIREMENT_REQUEST where    MODEL_ID = ? ";
/*      */   private static final String QUERY_SES_FOR_REQUEST = "select distinct se.*\nfrom structure_element se, virement_request vr, virement_request_group vrg, virement_request_line vrl, virement_auth_point vap\nwhere vr.request_id = ? and\n      vr.request_id = vrg.request_id and\n      vrg.request_group_id = vrl.request_group_id and\n      vr.request_id = vap.request_id (+) and\n      se.structure_element_id in ( vrl.dim0, vrl.dim1, vrl.dim2, vrl.dim3, vrl.dim4,\n                                   vrl.dim5, vrl.dim6, vrl.dim7, vrl.dim8, vrl.dim9,\n                                   nvl(vap.structure_element_id,-1) )\nunion all \nselect distinct se.*\nfrom structure_element se, virement_request vr, virement_request_group vrg, virement_request_line vrl, virement_line_spread vls\nwhere vr.request_id = ? and\n      vrl.request_line_id = vls.request_line_id and\n      vr.request_id = vrg.request_id and\n      vrg.request_group_id = vrl.request_group_id and\n      se.structure_element_id = vls.structure_element_id";
/*      */   private static final String QUERY_USERS_FOR_REQUEST = "select u.user_id, u.name, u.full_name\nfrom usr u, virement_request vr, virement_request_group vrg, virement_auth_point vap, virement_authorisers va\nwhere vr.request_id = ? and\n      vr.request_id = vrg.request_id and\n      vap.request_id = vr.request_id and \n      va.auth_point_id = vap.auth_point_id and\n      u.user_id in (va.user_id, nvl(vap.auth_user_id,-1))";
/*      */   public static final String QUERY_VIREMENTS_REQUIRING_USER_AUTHORISATION = "select count( distinct vr.request_id )\nfrom virement_authorisers va, virement_auth_point vap, virement_request vr\nwhere va.user_id = ? and\n      vr.request_status = ? and\n      vr.request_id = vap.request_id and\n      va.auth_point_id = vap.auth_point_id and\n      vap.point_status = ?";
/* 1539 */   protected static String SQL_UPDATE_BUDGET_ACTIVITY_ID = "update VIREMENT_REQUEST set VIREMENT_REQUEST.BUDGET_ACTIVITY_ID=? where VIREMENT_REQUEST.REQUEST_ID= ?";
/*      */ 
/* 1582 */   private static final JdbcUtils.ColType[] VIREMENTS_AWAITING_AUTHORISATION_COL_INFO = { new JdbcUtils.ColType("requestId", "request_id", 0), new JdbcUtils.VirementRequestRefColType("request", "request_id", null, "model_id"), new JdbcUtils.ColType("ownerId", "user_id", 0), new JdbcUtils.UserRefColType("owner", "user_id", "user_visid"), new JdbcUtils.ColType("reason", "reason", 1), new JdbcUtils.ColType("reference", "reference", 1), new JdbcUtils.ColType("createdTime", "created_time", 6), new JdbcUtils.ColType("dateSubmitted", "date_submitted", 6), new JdbcUtils.ModelRefColType("model", "model_id", "model_visid"), new JdbcUtils.ColType("requestStatus", "request_status", 0), new JdbcUtils.UserRefColType("authoriser1", "auth1", "auth1_visid"), new JdbcUtils.UserRefColType("authoriser2", "auth2", "auth2_visid"), new JdbcUtils.UserRefColType("authoriser3", "auth3", "auth3_visid"), new JdbcUtils.UserRefColType("authoriser4", "auth4", "auth4_visid"), new JdbcUtils.UserRefColType("authoriser5", "auth5", "auth5_visid"), new JdbcUtils.UserRefColType("authoriser6", "auth6", "auth6_visid"), new JdbcUtils.UserRefColType("authoriser7", "auth7", "auth7_visid"), new JdbcUtils.UserRefColType("authoriser8", "auth8", "auth8_visid"), new JdbcUtils.UserRefColType("authoriser9", "auth9", "auth9_visid"), new JdbcUtils.UserRefColType("authoriser10", "auth10", "auth10_visid") };
/*      */   private static final String QUERY_ALL_VIREMENTS_AWAITING_AUTHORISATION = "select request_id, user_id, \n       (select full_name from usr u where u.user_id = r.user_id) as user_visid, \n       reason, reference, created_time, date_submitted,   \n        model_id, model_visid, request_status, \n \t\tmax(auth1) as auth1,   \n\t\tmax(auth1_visid) as auth1_visid,   \n\t\tmax(auth2) as auth2,   \n\t\tmax(auth2_visid) as auth2_visid,   \n\t\tmax(auth3) as auth3,   \n\t\tmax(auth3_visid) as auth3_visid,   \n\t\tmax(auth4) as auth4,   \n\t\tmax(auth4_visid) as auth4_visid,   \n\t\tmax(auth5) as auth5,   \n\t\tmax(auth5_visid) as auth5_visid,   \n\t\tmax(auth6) as auth6,   \n\t\tmax(auth6_visid) as auth6_visid,   \n\t\tmax(auth7) as auth7,   \n\t\tmax(auth7_visid) as auth7_visid,   \n\t\tmax(auth8) as auth8,   \n\t\tmax(auth8_visid) as auth8_visid,   \n\t\tmax(auth9) as auth9,   \n\t\tmax(auth9_visid) as auth9_visid,   \n\t\tmax(auth10) as auth10,   \n\t\tmax(auth10_visid) as auth10_visid   \nfrom   \n(   \n\tselect r.request_id, user_id, reason, reference, created_time, date_submitted,  \n\t\t\tmodel_id, request_status, model_visid,  \n\t\t\tcase when r.rk = 1 then r.auth_id end as auth1,  \n\t\t\tcase when r.rk = 1 then r.auth_visid end as auth1_visid,  \n\t\t\tcase when r.rk = 2 then r.auth_id end as auth2,  \n\t\t\tcase when r.rk = 2 then r.auth_visid end as auth2_visid,  \n\t\t\tcase when r.rk = 3 then r.auth_id end as auth3, \n\t\t\tcase when r.rk = 3 then r.auth_visid end as auth3_visid,  \n\t\t\tcase when r.rk = 4 then r.auth_id end as auth4,  \n\t\t\tcase when r.rk = 4 then r.auth_visid end as auth4_visid,  \n\t\t\tcase when r.rk = 5 then r.auth_id end as auth5,  \n\t\t\tcase when r.rk = 5 then r.auth_visid end as auth5_visid,  \n\t\t\tcase when r.rk = 6 then r.auth_id end as auth6,  \n\t\t\tcase when r.rk = 6 then r.auth_visid end as auth6_visid,  \n\t\t\tcase when r.rk = 7 then r.auth_id end as auth7,  \n\t\t\tcase when r.rk = 7 then r.auth_visid end as auth7_visid,  \n\t\t\tcase when r.rk = 8 then r.auth_id end as auth8,  \n\t\t\tcase when r.rk = 8 then r.auth_visid end as auth8_visid,  \n\t\t\tcase when r.rk = 9 then r.auth_id end as auth9,  \n\t\t\tcase when r.rk = 9 then r.auth_visid end as auth9_visid,  \n\t\t\tcase when r.rk = 10 then r.auth_id end as auth10,  \n\t\t\tcase when r.rk = 10 then r.auth_visid end as auth10_visid  \nfrom  \n(  \n\tselect distinct vr.request_id,  \n\t       vr.user_id,  \n\t       vr.reference,  \n\t       vr.reason,  \n\t       vr.created_time,  \n\t\t   vr.date_submitted,  \n\t       vr.model_id,   \n\t       vr.request_status,  \n\t       ( select m.vis_id from model m where m.model_id = vr.model_id ) as model_visid,  \n\t       va.user_id auth_id,   \n\t       u.full_name as auth_visid,  \n\t       dense_rank() over ( partition by vr.request_id order by u.full_name asc ) rk   \n\tfrom virement_request vr,   \n\t     virement_auth_point vap,   \n\t     virement_authorisers va,   \n\t     usr u   \n\twhere vr.request_id = vap.request_id and    \n\t      vap.auth_point_id = va.auth_point_id and    \n\t      va.user_id = u.user_id and\t\t  vr.request_status = ? and \t\t  ( vr.request_id in    \n         \t(  \n         \t\tselect distinct ivap.request_id    \n           \tfrom virement_request vr,    \n               \t virement_auth_point ivap,    \n\t\t\t\t\t virement_authorisers iva    \n           \twhere vr.request_id = ivap.request_id and    \n\t\t\t\t\t  ivap.point_status = ? and    \n               \t  ivap.auth_point_id = iva.auth_point_id and \n\t\t\t\t\t  iva.user_id = ? \n \t\t\t) \t\t\tor vr.request_id in \t\t\t(\n \t\t\t\tselect distinct ivap.request_id\n\t\t\t\tfrom virement_request vr,      \n\t\t\t\t\t virement_auth_point ivap,\n\t\t\t\t\t model im,\n\t\t\t\t\t structure_element vse,\n\t\t\t\t\t structure_element bse,\n\t\t\t\t\t budget_user bu\n\t\t\t\twhere vr.request_id = ivap.request_id and \n\t\t\t\t\t  ivap.point_status = ? and \n\t\t\t\t\t  vr.model_id = im.model_id and \n\t\t\t\t\t  vse.structure_id = im.budget_hierarchy_id and \n\t\t\t\t\t  ivap.structure_element_id = vse.structure_element_id and \n\t\t\t\t\t  bu.model_id = im.model_id and \n\t\t\t\t\t  bu.structure_element_id = bse.structure_element_id and \n\t\t\t\t\t  bse.structure_id = im.budget_hierarchy_id and \n\t\t\t\t\t  bse.position <= vse.position and vse.position <= vse.end_position and \n\t\t\t\t\t  bu.user_id = ? \n \t\t\t)           or vr.user_id = ? \n        ) \n\t) r \n) r \ngroup by request_id, user_id, reason, reference, created_time, date_submitted, model_id, request_status, model_visid   \nunion all \nselect vr.request_id, \n       u.user_id, u.full_name as user_visid, \n       vr.reason, vr.reference, vr.created_time, vr.date_submitted, \n       m.model_id, m.vis_id as model_visid, \n       vr.request_status, \n       null as auth1,  null as auth1_visid, \n       null as auth2,  null as auth2_visid, \n       null as auth3,  null as auth3_visid, \n       null as auth4,  null as auth4_visid, \n       null as auth5,  null as auth5_visid, \n       null as auth6,  null as auth6_visid, \n       null as auth7,  null as auth7_visid, \n       null as auth8,  null as auth8_visid, \n       null as auth9,  null as auth9_visid, \n       null as auth10, null as auth10_visid \nfrom virement_request vr,\n     model m, \n     usr u \nwhere vr.user_id = u.user_id and \n      u.user_id = ? and \n      vr.model_id = m.model_id and       vr.request_status = ? ";
/*      */   protected VirementRequestGroupDAO mVirementRequestGroupDAO;
/*      */   protected VirementAuthPointDAO mVirementAuthPointDAO;
/*      */   protected VirementRequestEVO mDetails;
/*      */ 
/*      */   public VirementRequestDAO(Connection connection)
/*      */   {
/*   74 */     super(connection);
/*      */   }
/*      */ 
/*      */   public VirementRequestDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public VirementRequestDAO(DataSource ds)
/*      */   {
/*   90 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected VirementRequestPK getPK()
/*      */   {
/*   98 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(VirementRequestEVO details)
/*      */   {
/*  107 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private VirementRequestEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  132 */     int col = 1;
/*  133 */     VirementRequestEVO evo = new VirementRequestEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getTimestamp(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), null, null);
/*      */ 
/*  149 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  150 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  151 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  152 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(VirementRequestEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  157 */     int col = startCol_;
/*  158 */     stmt_.setInt(col++, evo_.getRequestId());
/*  159 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(VirementRequestEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  164 */     int col = startCol_;
/*  165 */     stmt_.setInt(col++, evo_.getModelId());
/*  166 */     stmt_.setInt(col++, evo_.getFinanceCubeId());
/*  167 */     stmt_.setInt(col++, evo_.getBudgetCycleId());
/*  168 */     stmt_.setInt(col++, evo_.getRequestStatus());
/*  169 */     stmt_.setInt(col++, evo_.getUserId());
/*  170 */     stmt_.setString(col++, evo_.getReason());
/*  171 */     stmt_.setString(col++, evo_.getReference());
/*  172 */     stmt_.setTimestamp(col++, evo_.getDateSubmitted());
/*  173 */     stmt_.setInt(col++, evo_.getBudgetActivityId());
/*  174 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  175 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  176 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  177 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  178 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(VirementRequestPK pk)
/*      */     throws ValidationException
/*      */   {
/*  194 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  196 */     PreparedStatement stmt = null;
/*  197 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  201 */       stmt = getConnection().prepareStatement("select VIREMENT_REQUEST.REQUEST_ID,VIREMENT_REQUEST.MODEL_ID,VIREMENT_REQUEST.FINANCE_CUBE_ID,VIREMENT_REQUEST.BUDGET_CYCLE_ID,VIREMENT_REQUEST.REQUEST_STATUS,VIREMENT_REQUEST.USER_ID,VIREMENT_REQUEST.REASON,VIREMENT_REQUEST.REFERENCE,VIREMENT_REQUEST.DATE_SUBMITTED,VIREMENT_REQUEST.BUDGET_ACTIVITY_ID,VIREMENT_REQUEST.VERSION_NUM,VIREMENT_REQUEST.UPDATED_BY_USER_ID,VIREMENT_REQUEST.UPDATED_TIME,VIREMENT_REQUEST.CREATED_TIME from VIREMENT_REQUEST where    REQUEST_ID = ? ");
/*      */ 
/*  204 */       int col = 1;
/*  205 */       stmt.setInt(col++, pk.getRequestId());
/*      */ 
/*  207 */       resultSet = stmt.executeQuery();
/*      */ 
/*  209 */       if (!resultSet.next()) {
/*  210 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  213 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  214 */       if (this.mDetails.isModified())
/*  215 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  219 */       throw handleSQLException(pk, "select VIREMENT_REQUEST.REQUEST_ID,VIREMENT_REQUEST.MODEL_ID,VIREMENT_REQUEST.FINANCE_CUBE_ID,VIREMENT_REQUEST.BUDGET_CYCLE_ID,VIREMENT_REQUEST.REQUEST_STATUS,VIREMENT_REQUEST.USER_ID,VIREMENT_REQUEST.REASON,VIREMENT_REQUEST.REFERENCE,VIREMENT_REQUEST.DATE_SUBMITTED,VIREMENT_REQUEST.BUDGET_ACTIVITY_ID,VIREMENT_REQUEST.VERSION_NUM,VIREMENT_REQUEST.UPDATED_BY_USER_ID,VIREMENT_REQUEST.UPDATED_TIME,VIREMENT_REQUEST.CREATED_TIME from VIREMENT_REQUEST where    REQUEST_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  223 */       closeResultSet(resultSet);
/*  224 */       closeStatement(stmt);
/*  225 */       closeConnection();
/*      */ 
/*  227 */       if (timer != null)
/*  228 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  273 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  274 */     this.mDetails.postCreateInit();
/*      */ 
/*  276 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  281 */       this.mDetails.setCreatedTime(new Timestamp(new java.util.Date().getTime()));
/*  282 */       this.mDetails.setUpdatedTime(new Timestamp(new java.util.Date().getTime()));
/*  283 */       stmt = getConnection().prepareStatement("insert into VIREMENT_REQUEST ( REQUEST_ID,MODEL_ID,FINANCE_CUBE_ID,BUDGET_CYCLE_ID,REQUEST_STATUS,USER_ID,REASON,REFERENCE,DATE_SUBMITTED,BUDGET_ACTIVITY_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  286 */       int col = 1;
/*  287 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  288 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  291 */       int resultCount = stmt.executeUpdate();
/*  292 */       if (resultCount != 1)
/*      */       {
/*  294 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  297 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  301 */       throw handleSQLException(this.mDetails.getPK(), "insert into VIREMENT_REQUEST ( REQUEST_ID,MODEL_ID,FINANCE_CUBE_ID,BUDGET_CYCLE_ID,REQUEST_STATUS,USER_ID,REASON,REFERENCE,DATE_SUBMITTED,BUDGET_ACTIVITY_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  305 */       closeStatement(stmt);
/*  306 */       closeConnection();
/*      */ 
/*  308 */       if (timer != null) {
/*  309 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  315 */       getVirementRequestGroupDAO().update(this.mDetails.getGroupsMap());
/*      */ 
/*  317 */       getVirementAuthPointDAO().update(this.mDetails.getAuthPointsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  323 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  356 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  360 */     PreparedStatement stmt = null;
/*      */ 
/*  362 */     boolean mainChanged = this.mDetails.isModified();
/*  363 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  367 */       if (getVirementRequestGroupDAO().update(this.mDetails.getGroupsMap())) {
/*  368 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  371 */       if (getVirementAuthPointDAO().update(this.mDetails.getAuthPointsMap())) {
/*  372 */         dependantChanged = true;
/*      */       }
/*  374 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  377 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  380 */         this.mDetails.setUpdatedTime(new Timestamp(new java.util.Date().getTime()));
/*  381 */         stmt = getConnection().prepareStatement("update VIREMENT_REQUEST set MODEL_ID = ?,FINANCE_CUBE_ID = ?,BUDGET_CYCLE_ID = ?,REQUEST_STATUS = ?,USER_ID = ?,REASON = ?,REFERENCE = ?,DATE_SUBMITTED = ?,BUDGET_ACTIVITY_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REQUEST_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  384 */         int col = 1;
/*  385 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  386 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  388 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  391 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  393 */         if (resultCount == 0) {
/*  394 */           checkVersionNum();
/*      */         }
/*  396 */         if (resultCount != 1) {
/*  397 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  400 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  409 */       throw handleSQLException(getPK(), "update VIREMENT_REQUEST set MODEL_ID = ?,FINANCE_CUBE_ID = ?,BUDGET_CYCLE_ID = ?,REQUEST_STATUS = ?,USER_ID = ?,REASON = ?,REFERENCE = ?,DATE_SUBMITTED = ?,BUDGET_ACTIVITY_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REQUEST_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  413 */       closeStatement(stmt);
/*  414 */       closeConnection();
/*      */ 
/*  416 */       if ((timer != null) && (
/*  417 */         (mainChanged) || (dependantChanged)))
/*  418 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  430 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  431 */     PreparedStatement stmt = null;
/*  432 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  436 */       stmt = getConnection().prepareStatement("select VERSION_NUM from VIREMENT_REQUEST where REQUEST_ID = ?");
/*      */ 
/*  439 */       int col = 1;
/*  440 */       stmt.setInt(col++, this.mDetails.getRequestId());
/*      */ 
/*  443 */       resultSet = stmt.executeQuery();
/*      */ 
/*  445 */       if (!resultSet.next()) {
/*  446 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  449 */       col = 1;
/*  450 */       int dbVersionNumber = resultSet.getInt(col++);
/*  451 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  452 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  458 */       throw handleSQLException(getPK(), "select VERSION_NUM from VIREMENT_REQUEST where REQUEST_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  462 */       closeStatement(stmt);
/*  463 */       closeResultSet(resultSet);
/*      */ 
/*  465 */       if (timer != null)
/*  466 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllVirementRequestsELO getAllVirementRequests()
/*      */   {
/*  505 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  506 */     PreparedStatement stmt = null;
/*  507 */     ResultSet resultSet = null;
/*  508 */     AllVirementRequestsELO results = new AllVirementRequestsELO();
/*      */     try
/*      */     {
/*  511 */       stmt = getConnection().prepareStatement(SQL_ALL_VIREMENT_REQUESTS);
/*  512 */       int col = 1;
/*  513 */       resultSet = stmt.executeQuery();
/*  514 */       while (resultSet.next())
/*      */       {
/*  516 */         col = 2;
/*      */ 
/*  519 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  522 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  525 */         VirementRequestPK pkVirementRequest = new VirementRequestPK(resultSet.getInt(col++));
/*      */ 
/*  528 */         String textVirementRequest = "";
/*      */ 
/*  533 */         VirementRequestCK ckVirementRequest = new VirementRequestCK(pkModel, pkVirementRequest);
/*      */ 
/*  539 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  545 */         VirementRequestRefImpl erVirementRequest = new VirementRequestRefImpl(ckVirementRequest, textVirementRequest);
/*      */ 
/*  550 */         int col1 = resultSet.getInt(col++);
/*  551 */         int col2 = resultSet.getInt(col++);
/*  552 */         int col3 = resultSet.getInt(col++);
/*  553 */         Timestamp col4 = resultSet.getTimestamp(col++);
/*  554 */         int col5 = resultSet.getInt(col++);
/*      */ 
/*  557 */         results.add(erVirementRequest, erModel, col1, col2, col3, col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  570 */       throw handleSQLException(SQL_ALL_VIREMENT_REQUESTS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  574 */       closeResultSet(resultSet);
/*  575 */       closeStatement(stmt);
/*  576 */       closeConnection();
/*      */     }
/*      */ 
/*  579 */     if (timer != null) {
/*  580 */       timer.logDebug("getAllVirementRequests", " items=" + results.size());
/*      */     }
/*      */ 
/*  584 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  601 */     if (items == null) {
/*  602 */       return false;
/*      */     }
/*  604 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  605 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  607 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  611 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  612 */       while (iter3.hasNext())
/*      */       {
/*  614 */         this.mDetails = ((VirementRequestEVO)iter3.next());
/*  615 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  617 */         somethingChanged = true;
/*      */ 
/*  620 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  624 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  625 */       while (iter2.hasNext())
/*      */       {
/*  627 */         this.mDetails = ((VirementRequestEVO)iter2.next());
/*      */ 
/*  630 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  632 */         somethingChanged = true;
/*      */ 
/*  635 */         if (deleteStmt == null) {
/*  636 */           deleteStmt = getConnection().prepareStatement("delete from VIREMENT_REQUEST where    REQUEST_ID = ? ");
/*      */         }
/*      */ 
/*  639 */         int col = 1;
/*  640 */         deleteStmt.setInt(col++, this.mDetails.getRequestId());
/*      */ 
/*  642 */         if (this._log.isDebugEnabled()) {
/*  643 */           this._log.debug("update", "VirementRequest deleting RequestId=" + this.mDetails.getRequestId());
/*      */         }
/*      */ 
/*  648 */         deleteStmt.addBatch();
/*      */ 
/*  651 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  656 */       if (deleteStmt != null)
/*      */       {
/*  658 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  660 */         deleteStmt.executeBatch();
/*      */ 
/*  662 */         if (timer2 != null) {
/*  663 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  667 */       Iterator iter1 = items.values().iterator();
/*  668 */       while (iter1.hasNext())
/*      */       {
/*  670 */         this.mDetails = ((VirementRequestEVO)iter1.next());
/*      */ 
/*  672 */         if (this.mDetails.insertPending())
/*      */         {
/*  674 */           somethingChanged = true;
/*  675 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  678 */         if (this.mDetails.isModified())
/*      */         {
/*  680 */           somethingChanged = true;
/*  681 */           doStore(); continue;
/*      */         }
/*      */ 
/*  685 */         if (this.mDetails.deletePending())
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  691 */         if (getVirementRequestGroupDAO().update(this.mDetails.getGroupsMap())) {
/*  692 */           somethingChanged = true;
/*      */         }
/*      */ 
/*  695 */         if (getVirementAuthPointDAO().update(this.mDetails.getAuthPointsMap())) {
/*  696 */           somethingChanged = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  708 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  712 */       throw handleSQLException("delete from VIREMENT_REQUEST where    REQUEST_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  716 */       if (deleteStmt != null)
/*      */       {
/*  718 */         closeStatement(deleteStmt);
/*  719 */         closeConnection();
/*      */       }
/*      */ 
/*  722 */       this.mDetails = null;
/*      */ 
/*  724 */       if ((somethingChanged) && 
/*  725 */         (timer != null))
/*  726 */         timer.logDebug("update", "collection"); 
/*  726 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(VirementRequestPK pk)
/*      */   {
/*  803 */     Set emptyStrings = Collections.emptySet();
/*  804 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(VirementRequestPK pk, Set<String> exclusionTables)
/*      */   {
/*  810 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  812 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  814 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  816 */       PreparedStatement stmt = null;
/*      */ 
/*  818 */       int resultCount = 0;
/*  819 */       String s = null;
/*      */       try
/*      */       {
/*  822 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  824 */         if (this._log.isDebugEnabled()) {
/*  825 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  827 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  830 */         int col = 1;
/*  831 */         stmt.setInt(col++, pk.getRequestId());
/*      */ 
/*  834 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  838 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  842 */         closeStatement(stmt);
/*  843 */         closeConnection();
/*      */ 
/*  845 */         if (timer != null) {
/*  846 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  850 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  852 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  854 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  856 */       PreparedStatement stmt = null;
/*      */ 
/*  858 */       int resultCount = 0;
/*  859 */       String s = null;
/*      */       try
/*      */       {
/*  862 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  864 */         if (this._log.isDebugEnabled()) {
/*  865 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  867 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  870 */         int col = 1;
/*  871 */         stmt.setInt(col++, pk.getRequestId());
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
/*  885 */         if (timer != null)
/*  886 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants)
/*      */   {
/*  906 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  908 */     PreparedStatement stmt = null;
/*  909 */     ResultSet resultSet = null;
/*      */ 
/*  911 */     int itemCount = 0;
/*      */ 
/*  913 */     Collection theseItems = new ArrayList();
/*  914 */     owningEVO.setVirementRequests(theseItems);
/*  915 */     owningEVO.setVirementRequestsAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  919 */       stmt = getConnection().prepareStatement("select VIREMENT_REQUEST.REQUEST_ID,VIREMENT_REQUEST.MODEL_ID,VIREMENT_REQUEST.FINANCE_CUBE_ID,VIREMENT_REQUEST.BUDGET_CYCLE_ID,VIREMENT_REQUEST.REQUEST_STATUS,VIREMENT_REQUEST.USER_ID,VIREMENT_REQUEST.REASON,VIREMENT_REQUEST.REFERENCE,VIREMENT_REQUEST.DATE_SUBMITTED,VIREMENT_REQUEST.BUDGET_ACTIVITY_ID,VIREMENT_REQUEST.VERSION_NUM,VIREMENT_REQUEST.UPDATED_BY_USER_ID,VIREMENT_REQUEST.UPDATED_TIME,VIREMENT_REQUEST.CREATED_TIME from VIREMENT_REQUEST where 1=1 and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_REQUEST.REQUEST_ID");
/*      */ 
/*  921 */       int col = 1;
/*  922 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  924 */       resultSet = stmt.executeQuery();
/*      */ 
/*  927 */       while (resultSet.next())
/*      */       {
/*  929 */         itemCount++;
/*  930 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  932 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  935 */       if (timer != null) {
/*  936 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  939 */       if ((itemCount > 0) && (dependants.indexOf("<30>") > -1))
/*      */       {
/*  941 */         getVirementRequestGroupDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*  943 */       if ((itemCount > 0) && (dependants.indexOf("<33>") > -1))
/*      */       {
/*  945 */         getVirementAuthPointDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  949 */       throw handleSQLException("select VIREMENT_REQUEST.REQUEST_ID,VIREMENT_REQUEST.MODEL_ID,VIREMENT_REQUEST.FINANCE_CUBE_ID,VIREMENT_REQUEST.BUDGET_CYCLE_ID,VIREMENT_REQUEST.REQUEST_STATUS,VIREMENT_REQUEST.USER_ID,VIREMENT_REQUEST.REASON,VIREMENT_REQUEST.REFERENCE,VIREMENT_REQUEST.DATE_SUBMITTED,VIREMENT_REQUEST.BUDGET_ACTIVITY_ID,VIREMENT_REQUEST.VERSION_NUM,VIREMENT_REQUEST.UPDATED_BY_USER_ID,VIREMENT_REQUEST.UPDATED_TIME,VIREMENT_REQUEST.CREATED_TIME from VIREMENT_REQUEST where 1=1 and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_REQUEST.REQUEST_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  953 */       closeResultSet(resultSet);
/*  954 */       closeStatement(stmt);
/*  955 */       closeConnection();
/*      */ 
/*  957 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectModelId, String dependants, Collection currentList)
/*      */   {
/*  982 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  983 */     PreparedStatement stmt = null;
/*  984 */     ResultSet resultSet = null;
/*      */ 
/*  986 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  990 */       stmt = getConnection().prepareStatement("select VIREMENT_REQUEST.REQUEST_ID,VIREMENT_REQUEST.MODEL_ID,VIREMENT_REQUEST.FINANCE_CUBE_ID,VIREMENT_REQUEST.BUDGET_CYCLE_ID,VIREMENT_REQUEST.REQUEST_STATUS,VIREMENT_REQUEST.USER_ID,VIREMENT_REQUEST.REASON,VIREMENT_REQUEST.REFERENCE,VIREMENT_REQUEST.DATE_SUBMITTED,VIREMENT_REQUEST.BUDGET_ACTIVITY_ID,VIREMENT_REQUEST.VERSION_NUM,VIREMENT_REQUEST.UPDATED_BY_USER_ID,VIREMENT_REQUEST.UPDATED_TIME,VIREMENT_REQUEST.CREATED_TIME from VIREMENT_REQUEST where    MODEL_ID = ? ");
/*      */ 
/*  992 */       int col = 1;
/*  993 */       stmt.setInt(col++, selectModelId);
/*      */ 
/*  995 */       resultSet = stmt.executeQuery();
/*      */ 
/*  997 */       while (resultSet.next())
/*      */       {
/*  999 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1002 */         getDependants(this.mDetails, dependants);
/*      */ 
/* 1005 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1008 */       if (currentList != null)
/*      */       {
/* 1011 */         ListIterator iter = items.listIterator();
/* 1012 */         VirementRequestEVO currentEVO = null;
/* 1013 */         VirementRequestEVO newEVO = null;
/* 1014 */         while (iter.hasNext())
/*      */         {
/* 1016 */           newEVO = (VirementRequestEVO)iter.next();
/* 1017 */           Iterator iter2 = currentList.iterator();
/* 1018 */           while (iter2.hasNext())
/*      */           {
/* 1020 */             currentEVO = (VirementRequestEVO)iter2.next();
/* 1021 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1023 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1029 */         Iterator iter2 = currentList.iterator();
/* 1030 */         while (iter2.hasNext())
/*      */         {
/* 1032 */           currentEVO = (VirementRequestEVO)iter2.next();
/* 1033 */           if (currentEVO.insertPending()) {
/* 1034 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1038 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1042 */       throw handleSQLException("select VIREMENT_REQUEST.REQUEST_ID,VIREMENT_REQUEST.MODEL_ID,VIREMENT_REQUEST.FINANCE_CUBE_ID,VIREMENT_REQUEST.BUDGET_CYCLE_ID,VIREMENT_REQUEST.REQUEST_STATUS,VIREMENT_REQUEST.USER_ID,VIREMENT_REQUEST.REASON,VIREMENT_REQUEST.REFERENCE,VIREMENT_REQUEST.DATE_SUBMITTED,VIREMENT_REQUEST.BUDGET_ACTIVITY_ID,VIREMENT_REQUEST.VERSION_NUM,VIREMENT_REQUEST.UPDATED_BY_USER_ID,VIREMENT_REQUEST.UPDATED_TIME,VIREMENT_REQUEST.CREATED_TIME from VIREMENT_REQUEST where    MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1046 */       closeResultSet(resultSet);
/* 1047 */       closeStatement(stmt);
/* 1048 */       closeConnection();
/*      */ 
/* 1050 */       if (timer != null) {
/* 1051 */         timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1056 */     return items;
/*      */   }
/*      */ 
/*      */   public VirementRequestEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1083 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1086 */     if (this.mDetails == null) {
/* 1087 */       doLoad(((VirementRequestCK)paramCK).getVirementRequestPK());
/*      */     }
/* 1089 */     else if (!this.mDetails.getPK().equals(((VirementRequestCK)paramCK).getVirementRequestPK())) {
/* 1090 */       doLoad(((VirementRequestCK)paramCK).getVirementRequestPK());
/*      */     }
/*      */ 
/* 1093 */     if ((dependants.indexOf("<30>") > -1) && (!this.mDetails.isGroupsAllItemsLoaded()))
/*      */     {
/* 1098 */       this.mDetails.setGroups(getVirementRequestGroupDAO().getAll(this.mDetails.getRequestId(), dependants, this.mDetails.getGroups()));
/*      */ 
/* 1105 */       this.mDetails.setGroupsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1109 */     if ((dependants.indexOf("<33>") > -1) && (!this.mDetails.isAuthPointsAllItemsLoaded()))
/*      */     {
/* 1114 */       this.mDetails.setAuthPoints(getVirementAuthPointDAO().getAll(this.mDetails.getRequestId(), dependants, this.mDetails.getAuthPoints()));
/*      */ 
/* 1121 */       this.mDetails.setAuthPointsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1124 */     if ((paramCK instanceof VirementRequestGroupCK))
/*      */     {
/* 1126 */       if (this.mDetails.getGroups() == null) {
/* 1127 */         this.mDetails.loadGroupsItem(getVirementRequestGroupDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1130 */         VirementRequestGroupPK pk = ((VirementRequestGroupCK)paramCK).getVirementRequestGroupPK();
/* 1131 */         VirementRequestGroupEVO evo = this.mDetails.getGroupsItem(pk);
/* 1132 */         if (evo == null)
/* 1133 */           this.mDetails.loadGroupsItem(getVirementRequestGroupDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1135 */           getVirementRequestGroupDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/* 1139 */     else if ((paramCK instanceof VirementAuthPointCK))
/*      */     {
/* 1141 */       if (this.mDetails.getAuthPoints() == null) {
/* 1142 */         this.mDetails.loadAuthPointsItem(getVirementAuthPointDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1145 */         VirementAuthPointPK pk = ((VirementAuthPointCK)paramCK).getVirementAuthPointPK();
/* 1146 */         VirementAuthPointEVO evo = this.mDetails.getAuthPointsItem(pk);
/* 1147 */         if (evo == null)
/* 1148 */           this.mDetails.loadAuthPointsItem(getVirementAuthPointDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1150 */           getVirementAuthPointDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1155 */     VirementRequestEVO details = new VirementRequestEVO();
/* 1156 */     details = this.mDetails.deepClone();
/*      */ 
/* 1158 */     if (timer != null) {
/* 1159 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1161 */     return details;
/*      */   }
/*      */ 
/*      */   public VirementRequestEVO getDetails(ModelCK paramCK, VirementRequestEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1167 */     VirementRequestEVO savedEVO = this.mDetails;
/* 1168 */     this.mDetails = paramEVO;
/* 1169 */     VirementRequestEVO newEVO = getDetails(paramCK, dependants);
/* 1170 */     this.mDetails = savedEVO;
/* 1171 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public VirementRequestEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1177 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1181 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1184 */     VirementRequestEVO details = this.mDetails.deepClone();
/*      */ 
/* 1186 */     if (timer != null) {
/* 1187 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1189 */     return details;
/*      */   }
/*      */ 
/*      */   protected VirementRequestGroupDAO getVirementRequestGroupDAO()
/*      */   {
/* 1198 */     if (this.mVirementRequestGroupDAO == null)
/*      */     {
/* 1200 */       if (this.mDataSource != null)
/* 1201 */         this.mVirementRequestGroupDAO = new VirementRequestGroupDAO(this.mDataSource);
/*      */       else {
/* 1203 */         this.mVirementRequestGroupDAO = new VirementRequestGroupDAO(getConnection());
/*      */       }
/*      */     }
/* 1206 */     return this.mVirementRequestGroupDAO;
/*      */   }
/*      */ 
/*      */   protected VirementAuthPointDAO getVirementAuthPointDAO()
/*      */   {
/* 1215 */     if (this.mVirementAuthPointDAO == null)
/*      */     {
/* 1217 */       if (this.mDataSource != null)
/* 1218 */         this.mVirementAuthPointDAO = new VirementAuthPointDAO(this.mDataSource);
/*      */       else {
/* 1220 */         this.mVirementAuthPointDAO = new VirementAuthPointDAO(getConnection());
/*      */       }
/*      */     }
/* 1223 */     return this.mVirementAuthPointDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1228 */     return "VirementRequest";
/*      */   }
/*      */ 
/*      */   public VirementRequestRefImpl getRef(VirementRequestPK paramVirementRequestPK)
/*      */   {
/* 1233 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1234 */     PreparedStatement stmt = null;
/* 1235 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1238 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID from VIREMENT_REQUEST,MODEL where 1=1 and VIREMENT_REQUEST.REQUEST_ID = ? and VIREMENT_REQUEST.MODEL_ID = MODEL.MODEL_ID");
/* 1239 */       int col = 1;
/* 1240 */       stmt.setInt(col++, paramVirementRequestPK.getRequestId());
/*      */ 
/* 1242 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1244 */       if (!resultSet.next()) {
/* 1245 */         throw new RuntimeException(getEntityName() + " getRef " + paramVirementRequestPK + " not found");
/*      */       }
/* 1247 */       col = 2;
/* 1248 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1252 */       String textVirementRequest = "";
/* 1253 */       VirementRequestCK ckVirementRequest = new VirementRequestCK(newModelPK, paramVirementRequestPK);
/*      */ 
/* 1258 */       VirementRequestRefImpl localVirementRequestRefImpl = new VirementRequestRefImpl(ckVirementRequest, textVirementRequest);
/*      */       return localVirementRequestRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1263 */       throw handleSQLException(paramVirementRequestPK, "select 0,MODEL.MODEL_ID from VIREMENT_REQUEST,MODEL where 1=1 and VIREMENT_REQUEST.REQUEST_ID = ? and VIREMENT_REQUEST.MODEL_ID = MODEL.MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1267 */       closeResultSet(resultSet);
/* 1268 */       closeStatement(stmt);
/* 1269 */       closeConnection();
/*      */ 
/* 1271 */       if (timer != null)
/* 1272 */         timer.logDebug("getRef", paramVirementRequestPK); 
/* 1272 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1284 */     if (c == null)
/* 1285 */       return;
/* 1286 */     Iterator iter = c.iterator();
/* 1287 */     while (iter.hasNext())
/*      */     {
/* 1289 */       VirementRequestEVO evo = (VirementRequestEVO)iter.next();
/* 1290 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(VirementRequestEVO evo, String dependants)
/*      */   {
/* 1304 */     if (evo.insertPending()) {
/* 1305 */       return;
/*      */     }
/* 1307 */     if (evo.getRequestId() < 1) {
/* 1308 */       return;
/*      */     }
/*      */ 
/* 1312 */     if ((dependants.indexOf("<30>") > -1) || (dependants.indexOf("<31>") > -1) || (dependants.indexOf("<32>") > -1))
/*      */     {
/* 1317 */       if (!evo.isGroupsAllItemsLoaded())
/*      */       {
/* 1319 */         evo.setGroups(getVirementRequestGroupDAO().getAll(evo.getRequestId(), dependants, evo.getGroups()));
/*      */ 
/* 1326 */         evo.setGroupsAllItemsLoaded(true);
/*      */       }
/* 1328 */       getVirementRequestGroupDAO().getDependants(evo.getGroups(), dependants);
/*      */     }
/*      */ 
/* 1332 */     if ((dependants.indexOf("<33>") > -1) || (dependants.indexOf("<34>") > -1) || (dependants.indexOf("<35>") > -1))
/*      */     {
/* 1337 */       if (!evo.isAuthPointsAllItemsLoaded())
/*      */       {
/* 1339 */         evo.setAuthPoints(getVirementAuthPointDAO().getAll(evo.getRequestId(), dependants, evo.getAuthPoints()));
/*      */ 
/* 1346 */         evo.setAuthPointsAllItemsLoaded(true);
/*      */       }
/* 1348 */       getVirementAuthPointDAO().getDependants(evo.getAuthPoints(), dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Map queryStructureElementsForRequest(int requestId)
/*      */   {
/* 1382 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1383 */     PreparedStatement stmt = null;
/* 1384 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1388 */       Map result = new HashMap();
/* 1389 */       stmt = getConnection().prepareStatement("select distinct se.*\nfrom structure_element se, virement_request vr, virement_request_group vrg, virement_request_line vrl, virement_auth_point vap\nwhere vr.request_id = ? and\n      vr.request_id = vrg.request_id and\n      vrg.request_group_id = vrl.request_group_id and\n      vr.request_id = vap.request_id (+) and\n      se.structure_element_id in ( vrl.dim0, vrl.dim1, vrl.dim2, vrl.dim3, vrl.dim4,\n                                   vrl.dim5, vrl.dim6, vrl.dim7, vrl.dim8, vrl.dim9,\n                                   nvl(vap.structure_element_id,-1) )\nunion all \nselect distinct se.*\nfrom structure_element se, virement_request vr, virement_request_group vrg, virement_request_line vrl, virement_line_spread vls\nwhere vr.request_id = ? and\n      vrl.request_line_id = vls.request_line_id and\n      vr.request_id = vrg.request_id and\n      vrg.request_group_id = vrl.request_group_id and\n      se.structure_element_id = vls.structure_element_id");
/* 1390 */       stmt.setInt(1, requestId);
/* 1391 */       stmt.setInt(2, requestId);
/* 1392 */       rs = stmt.executeQuery();
/*      */ 
/* 1394 */       while (rs.next())
/*      */       {
/* 1396 */         StructureElementEVO seEVO = new StructureElementEVO(rs.getInt("structure_id"), rs.getInt("structure_element_id"), rs.getString("vis_id"), rs.getString("description"), rs.getInt("parent_id"), rs.getInt("child_index"), rs.getInt("depth"), rs.getInt("position"), rs.getInt("end_position"), rs.getString("leaf").equalsIgnoreCase("Y"), rs.getString("is_credit").equalsIgnoreCase("Y"), rs.getString("disabled").equalsIgnoreCase("Y"), rs.getString("not_plannable").equalsIgnoreCase("Y"), -1, null, null);
/*      */ 
/* 1414 */         result.put(new Integer(seEVO.getStructureElementId()), seEVO);
/*      */       }
/*      */ 
/* 1417 */       if (timer != null) {
/* 1418 */         timer.logDebug("queryStructureElementsForRequest");
/*      */       }
/*      */       return result;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1424 */       throw handleSQLException("select distinct se.*\nfrom structure_element se, virement_request vr, virement_request_group vrg, virement_request_line vrl, virement_auth_point vap\nwhere vr.request_id = ? and\n      vr.request_id = vrg.request_id and\n      vrg.request_group_id = vrl.request_group_id and\n      vr.request_id = vap.request_id (+) and\n      se.structure_element_id in ( vrl.dim0, vrl.dim1, vrl.dim2, vrl.dim3, vrl.dim4,\n                                   vrl.dim5, vrl.dim6, vrl.dim7, vrl.dim8, vrl.dim9,\n                                   nvl(vap.structure_element_id,-1) )\nunion all \nselect distinct se.*\nfrom structure_element se, virement_request vr, virement_request_group vrg, virement_request_line vrl, virement_line_spread vls\nwhere vr.request_id = ? and\n      vrl.request_line_id = vls.request_line_id and\n      vr.request_id = vrg.request_id and\n      vrg.request_group_id = vrl.request_group_id and\n      se.structure_element_id = vls.structure_element_id", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1428 */       closeResultSet(rs);
/* 1429 */       closeStatement(stmt);
/* 1430 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public Map queryUsersForRequest(int requestId)
/*      */   {
/* 1450 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1451 */     PreparedStatement stmt = null;
/* 1452 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1456 */       Map result = new HashMap();
/* 1457 */       stmt = getConnection().prepareStatement("select u.user_id, u.name, u.full_name\nfrom usr u, virement_request vr, virement_request_group vrg, virement_auth_point vap, virement_authorisers va\nwhere vr.request_id = ? and\n      vr.request_id = vrg.request_id and\n      vap.request_id = vr.request_id and \n      va.auth_point_id = vap.auth_point_id and\n      u.user_id in (va.user_id, nvl(vap.auth_user_id,-1))");
/* 1458 */       stmt.setInt(1, requestId);
/* 1459 */       rs = stmt.executeQuery();
/*      */ 
/* 1461 */       while (rs.next())
/*      */       {
/* 1463 */         UserRefImpl userRef = new UserRefImpl(new UserPK(rs.getInt("user_id")), rs.getString("full_name"));
/*      */ 
/* 1466 */         result.put(userRef.getUserPK(), userRef);
/*      */       }
/*      */ 
/* 1469 */       if (timer != null) {
/* 1470 */         timer.logDebug("queryUserssForRequest");
/*      */       }
/*      */       return result;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1476 */       throw handleSQLException("select u.user_id, u.name, u.full_name\nfrom usr u, virement_request vr, virement_request_group vrg, virement_auth_point vap, virement_authorisers va\nwhere vr.request_id = ? and\n      vr.request_id = vrg.request_id and\n      vap.request_id = vr.request_id and \n      va.auth_point_id = vap.auth_point_id and\n      u.user_id in (va.user_id, nvl(vap.auth_user_id,-1))", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1480 */       closeResultSet(rs);
/* 1481 */       closeStatement(stmt);
/* 1482 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public int countVirementsRequringUserAuthorisation(int userId)
/*      */   {
/* 1502 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1503 */     PreparedStatement stmt = null;
/* 1504 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1508 */       stmt = getConnection().prepareStatement("select count( distinct vr.request_id )\nfrom virement_authorisers va, virement_auth_point vap, virement_request vr\nwhere va.user_id = ? and\n      vr.request_status = ? and\n      vr.request_id = vap.request_id and\n      va.auth_point_id = vap.auth_point_id and\n      vap.point_status = ?");
/* 1509 */       stmt.setInt(1, userId);
/* 1510 */       stmt.setInt(2, 1);
/* 1511 */       stmt.setInt(3, 0);
/*      */ 
/* 1513 */       rs = stmt.executeQuery();
/*      */       int result;
/* 1517 */       if (rs.next())
/* 1518 */         result = rs.getInt(1);
/*      */       else
/* 1520 */         throw new IllegalStateException("Failed to query virements requiring authorisation");

/* 1522 */       if (timer != null) {
/* 1523 */         timer.logDebug("countVirementsRequringUserAuthorisation");
/*      */       }
/* 1525 */       int i = result;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1529 */       throw handleSQLException("select count( distinct vr.request_id )\nfrom virement_authorisers va, virement_auth_point vap, virement_request vr\nwhere va.user_id = ? and\n      vr.request_status = ? and\n      vr.request_id = vap.request_id and\n      va.auth_point_id = vap.auth_point_id and\n      vap.point_status = ?", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1533 */       closeResultSet(rs);
/* 1534 */       closeStatement(stmt);
/* 1535 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public boolean updateBudgetActivityId(int virementRequestId, int budgetActivityId)
/*      */   {
/* 1543 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1545 */     PreparedStatement stmt = null;
/* 1546 */     ResultSet resultSet = null;
/* 1547 */     String sqlString = null;
/*      */     try
/*      */     {
/* 1552 */       stmt = getConnection().prepareStatement(SQL_UPDATE_BUDGET_ACTIVITY_ID);
/* 1553 */       stmt.setInt(1, budgetActivityId);
/* 1554 */       stmt.setInt(2, virementRequestId);
/*      */ 
/* 1556 */       int resultCount = stmt.executeUpdate();
/* 1557 */       if (resultCount != 1)
/*      */       {
/* 1559 */         throw new RuntimeException(getEntityName() + " updateBudgetActivityId: update failed: resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1565 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1569 */       closeResultSet(resultSet);
/* 1570 */       closeStatement(stmt);
/* 1571 */       closeConnection();
/*      */ 
/* 1573 */       if (timer != null)
/*      */       {
/* 1575 */         timer.logDebug("updateBudgetActivityId", "Sucessful. Key=" + virementRequestId);
/*      */       }
/*      */     }
/* 1578 */     return true;
/*      */   }
/*      */ 
/*      */   public EntityList queryVirementRequests(int userId, boolean includeInherited)
/*      */   {
/* 1750 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1751 */     PreparedStatement stmt = null;
/* 1752 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1756 */       stmt = getConnection().prepareStatement("select request_id, user_id, \n       (select full_name from usr u where u.user_id = r.user_id) as user_visid, \n       reason, reference, created_time, date_submitted,   \n        model_id, model_visid, request_status, \n \t\tmax(auth1) as auth1,   \n\t\tmax(auth1_visid) as auth1_visid,   \n\t\tmax(auth2) as auth2,   \n\t\tmax(auth2_visid) as auth2_visid,   \n\t\tmax(auth3) as auth3,   \n\t\tmax(auth3_visid) as auth3_visid,   \n\t\tmax(auth4) as auth4,   \n\t\tmax(auth4_visid) as auth4_visid,   \n\t\tmax(auth5) as auth5,   \n\t\tmax(auth5_visid) as auth5_visid,   \n\t\tmax(auth6) as auth6,   \n\t\tmax(auth6_visid) as auth6_visid,   \n\t\tmax(auth7) as auth7,   \n\t\tmax(auth7_visid) as auth7_visid,   \n\t\tmax(auth8) as auth8,   \n\t\tmax(auth8_visid) as auth8_visid,   \n\t\tmax(auth9) as auth9,   \n\t\tmax(auth9_visid) as auth9_visid,   \n\t\tmax(auth10) as auth10,   \n\t\tmax(auth10_visid) as auth10_visid   \nfrom   \n(   \n\tselect r.request_id, user_id, reason, reference, created_time, date_submitted,  \n\t\t\tmodel_id, request_status, model_visid,  \n\t\t\tcase when r.rk = 1 then r.auth_id end as auth1,  \n\t\t\tcase when r.rk = 1 then r.auth_visid end as auth1_visid,  \n\t\t\tcase when r.rk = 2 then r.auth_id end as auth2,  \n\t\t\tcase when r.rk = 2 then r.auth_visid end as auth2_visid,  \n\t\t\tcase when r.rk = 3 then r.auth_id end as auth3, \n\t\t\tcase when r.rk = 3 then r.auth_visid end as auth3_visid,  \n\t\t\tcase when r.rk = 4 then r.auth_id end as auth4,  \n\t\t\tcase when r.rk = 4 then r.auth_visid end as auth4_visid,  \n\t\t\tcase when r.rk = 5 then r.auth_id end as auth5,  \n\t\t\tcase when r.rk = 5 then r.auth_visid end as auth5_visid,  \n\t\t\tcase when r.rk = 6 then r.auth_id end as auth6,  \n\t\t\tcase when r.rk = 6 then r.auth_visid end as auth6_visid,  \n\t\t\tcase when r.rk = 7 then r.auth_id end as auth7,  \n\t\t\tcase when r.rk = 7 then r.auth_visid end as auth7_visid,  \n\t\t\tcase when r.rk = 8 then r.auth_id end as auth8,  \n\t\t\tcase when r.rk = 8 then r.auth_visid end as auth8_visid,  \n\t\t\tcase when r.rk = 9 then r.auth_id end as auth9,  \n\t\t\tcase when r.rk = 9 then r.auth_visid end as auth9_visid,  \n\t\t\tcase when r.rk = 10 then r.auth_id end as auth10,  \n\t\t\tcase when r.rk = 10 then r.auth_visid end as auth10_visid  \nfrom  \n(  \n\tselect distinct vr.request_id,  \n\t       vr.user_id,  \n\t       vr.reference,  \n\t       vr.reason,  \n\t       vr.created_time,  \n\t\t   vr.date_submitted,  \n\t       vr.model_id,   \n\t       vr.request_status,  \n\t       ( select m.vis_id from model m where m.model_id = vr.model_id ) as model_visid,  \n\t       va.user_id auth_id,   \n\t       u.full_name as auth_visid,  \n\t       dense_rank() over ( partition by vr.request_id order by u.full_name asc ) rk   \n\tfrom virement_request vr,   \n\t     virement_auth_point vap,   \n\t     virement_authorisers va,   \n\t     usr u   \n\twhere vr.request_id = vap.request_id and    \n\t      vap.auth_point_id = va.auth_point_id and    \n\t      va.user_id = u.user_id and\t\t  vr.request_status = ? and \t\t  ( vr.request_id in    \n         \t(  \n         \t\tselect distinct ivap.request_id    \n           \tfrom virement_request vr,    \n               \t virement_auth_point ivap,    \n\t\t\t\t\t virement_authorisers iva    \n           \twhere vr.request_id = ivap.request_id and    \n\t\t\t\t\t  ivap.point_status = ? and    \n               \t  ivap.auth_point_id = iva.auth_point_id and \n\t\t\t\t\t  iva.user_id = ? \n \t\t\t) \t\t\tor vr.request_id in \t\t\t(\n \t\t\t\tselect distinct ivap.request_id\n\t\t\t\tfrom virement_request vr,      \n\t\t\t\t\t virement_auth_point ivap,\n\t\t\t\t\t model im,\n\t\t\t\t\t structure_element vse,\n\t\t\t\t\t structure_element bse,\n\t\t\t\t\t budget_user bu\n\t\t\t\twhere vr.request_id = ivap.request_id and \n\t\t\t\t\t  ivap.point_status = ? and \n\t\t\t\t\t  vr.model_id = im.model_id and \n\t\t\t\t\t  vse.structure_id = im.budget_hierarchy_id and \n\t\t\t\t\t  ivap.structure_element_id = vse.structure_element_id and \n\t\t\t\t\t  bu.model_id = im.model_id and \n\t\t\t\t\t  bu.structure_element_id = bse.structure_element_id and \n\t\t\t\t\t  bse.structure_id = im.budget_hierarchy_id and \n\t\t\t\t\t  bse.position <= vse.position and vse.position <= vse.end_position and \n\t\t\t\t\t  bu.user_id = ? \n \t\t\t)           or vr.user_id = ? \n        ) \n\t) r \n) r \ngroup by request_id, user_id, reason, reference, created_time, date_submitted, model_id, request_status, model_visid   \nunion all \nselect vr.request_id, \n       u.user_id, u.full_name as user_visid, \n       vr.reason, vr.reference, vr.created_time, vr.date_submitted, \n       m.model_id, m.vis_id as model_visid, \n       vr.request_status, \n       null as auth1,  null as auth1_visid, \n       null as auth2,  null as auth2_visid, \n       null as auth3,  null as auth3_visid, \n       null as auth4,  null as auth4_visid, \n       null as auth5,  null as auth5_visid, \n       null as auth6,  null as auth6_visid, \n       null as auth7,  null as auth7_visid, \n       null as auth8,  null as auth8_visid, \n       null as auth9,  null as auth9_visid, \n       null as auth10, null as auth10_visid \nfrom virement_request vr,\n     model m, \n     usr u \nwhere vr.user_id = u.user_id and \n      u.user_id = ? and \n      vr.model_id = m.model_id and       vr.request_status = ? ");
/*      */ 
/* 1758 */       int paramNo = 1;
/* 1759 */       stmt.setInt(paramNo++, 1);
/* 1760 */       stmt.setInt(paramNo++, 0);
/* 1761 */       stmt.setInt(paramNo++, userId);
/*      */ 
/* 1763 */       stmt.setInt(paramNo++, 0);
/* 1764 */       stmt.setInt(paramNo++, includeInherited ? userId : -1);
/* 1765 */       stmt.setInt(paramNo++, userId);
/* 1766 */       stmt.setInt(paramNo++, userId);
/* 1767 */       stmt.setInt(paramNo++, 0);
/*      */ 
/* 1769 */       rs = stmt.executeQuery();
/*      */ 
/* 1771 */       EntityList result = JdbcUtils.extractToEntityListImpl(VIREMENTS_AWAITING_AUTHORISATION_COL_INFO, rs);
/*      */ 
/* 1773 */       if (timer != null) {
/* 1774 */         timer.logDebug("queryVirementRequests");
/*      */       }
/* 1776 */       EntityList localEntityList1 = result;
/*      */       return localEntityList1;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1780 */       throw handleSQLException("select request_id, user_id, \n       (select full_name from usr u where u.user_id = r.user_id) as user_visid, \n       reason, reference, created_time, date_submitted,   \n        model_id, model_visid, request_status, \n \t\tmax(auth1) as auth1,   \n\t\tmax(auth1_visid) as auth1_visid,   \n\t\tmax(auth2) as auth2,   \n\t\tmax(auth2_visid) as auth2_visid,   \n\t\tmax(auth3) as auth3,   \n\t\tmax(auth3_visid) as auth3_visid,   \n\t\tmax(auth4) as auth4,   \n\t\tmax(auth4_visid) as auth4_visid,   \n\t\tmax(auth5) as auth5,   \n\t\tmax(auth5_visid) as auth5_visid,   \n\t\tmax(auth6) as auth6,   \n\t\tmax(auth6_visid) as auth6_visid,   \n\t\tmax(auth7) as auth7,   \n\t\tmax(auth7_visid) as auth7_visid,   \n\t\tmax(auth8) as auth8,   \n\t\tmax(auth8_visid) as auth8_visid,   \n\t\tmax(auth9) as auth9,   \n\t\tmax(auth9_visid) as auth9_visid,   \n\t\tmax(auth10) as auth10,   \n\t\tmax(auth10_visid) as auth10_visid   \nfrom   \n(   \n\tselect r.request_id, user_id, reason, reference, created_time, date_submitted,  \n\t\t\tmodel_id, request_status, model_visid,  \n\t\t\tcase when r.rk = 1 then r.auth_id end as auth1,  \n\t\t\tcase when r.rk = 1 then r.auth_visid end as auth1_visid,  \n\t\t\tcase when r.rk = 2 then r.auth_id end as auth2,  \n\t\t\tcase when r.rk = 2 then r.auth_visid end as auth2_visid,  \n\t\t\tcase when r.rk = 3 then r.auth_id end as auth3, \n\t\t\tcase when r.rk = 3 then r.auth_visid end as auth3_visid,  \n\t\t\tcase when r.rk = 4 then r.auth_id end as auth4,  \n\t\t\tcase when r.rk = 4 then r.auth_visid end as auth4_visid,  \n\t\t\tcase when r.rk = 5 then r.auth_id end as auth5,  \n\t\t\tcase when r.rk = 5 then r.auth_visid end as auth5_visid,  \n\t\t\tcase when r.rk = 6 then r.auth_id end as auth6,  \n\t\t\tcase when r.rk = 6 then r.auth_visid end as auth6_visid,  \n\t\t\tcase when r.rk = 7 then r.auth_id end as auth7,  \n\t\t\tcase when r.rk = 7 then r.auth_visid end as auth7_visid,  \n\t\t\tcase when r.rk = 8 then r.auth_id end as auth8,  \n\t\t\tcase when r.rk = 8 then r.auth_visid end as auth8_visid,  \n\t\t\tcase when r.rk = 9 then r.auth_id end as auth9,  \n\t\t\tcase when r.rk = 9 then r.auth_visid end as auth9_visid,  \n\t\t\tcase when r.rk = 10 then r.auth_id end as auth10,  \n\t\t\tcase when r.rk = 10 then r.auth_visid end as auth10_visid  \nfrom  \n(  \n\tselect distinct vr.request_id,  \n\t       vr.user_id,  \n\t       vr.reference,  \n\t       vr.reason,  \n\t       vr.created_time,  \n\t\t   vr.date_submitted,  \n\t       vr.model_id,   \n\t       vr.request_status,  \n\t       ( select m.vis_id from model m where m.model_id = vr.model_id ) as model_visid,  \n\t       va.user_id auth_id,   \n\t       u.full_name as auth_visid,  \n\t       dense_rank() over ( partition by vr.request_id order by u.full_name asc ) rk   \n\tfrom virement_request vr,   \n\t     virement_auth_point vap,   \n\t     virement_authorisers va,   \n\t     usr u   \n\twhere vr.request_id = vap.request_id and    \n\t      vap.auth_point_id = va.auth_point_id and    \n\t      va.user_id = u.user_id and\t\t  vr.request_status = ? and \t\t  ( vr.request_id in    \n         \t(  \n         \t\tselect distinct ivap.request_id    \n           \tfrom virement_request vr,    \n               \t virement_auth_point ivap,    \n\t\t\t\t\t virement_authorisers iva    \n           \twhere vr.request_id = ivap.request_id and    \n\t\t\t\t\t  ivap.point_status = ? and    \n               \t  ivap.auth_point_id = iva.auth_point_id and \n\t\t\t\t\t  iva.user_id = ? \n \t\t\t) \t\t\tor vr.request_id in \t\t\t(\n \t\t\t\tselect distinct ivap.request_id\n\t\t\t\tfrom virement_request vr,      \n\t\t\t\t\t virement_auth_point ivap,\n\t\t\t\t\t model im,\n\t\t\t\t\t structure_element vse,\n\t\t\t\t\t structure_element bse,\n\t\t\t\t\t budget_user bu\n\t\t\t\twhere vr.request_id = ivap.request_id and \n\t\t\t\t\t  ivap.point_status = ? and \n\t\t\t\t\t  vr.model_id = im.model_id and \n\t\t\t\t\t  vse.structure_id = im.budget_hierarchy_id and \n\t\t\t\t\t  ivap.structure_element_id = vse.structure_element_id and \n\t\t\t\t\t  bu.model_id = im.model_id and \n\t\t\t\t\t  bu.structure_element_id = bse.structure_element_id and \n\t\t\t\t\t  bse.structure_id = im.budget_hierarchy_id and \n\t\t\t\t\t  bse.position <= vse.position and vse.position <= vse.end_position and \n\t\t\t\t\t  bu.user_id = ? \n \t\t\t)           or vr.user_id = ? \n        ) \n\t) r \n) r \ngroup by request_id, user_id, reason, reference, created_time, date_submitted, model_id, request_status, model_visid   \nunion all \nselect vr.request_id, \n       u.user_id, u.full_name as user_visid, \n       vr.reason, vr.reference, vr.created_time, vr.date_submitted, \n       m.model_id, m.vis_id as model_visid, \n       vr.request_status, \n       null as auth1,  null as auth1_visid, \n       null as auth2,  null as auth2_visid, \n       null as auth3,  null as auth3_visid, \n       null as auth4,  null as auth4_visid, \n       null as auth5,  null as auth5_visid, \n       null as auth6,  null as auth6_visid, \n       null as auth7,  null as auth7_visid, \n       null as auth8,  null as auth8_visid, \n       null as auth9,  null as auth9_visid, \n       null as auth10, null as auth10_visid \nfrom virement_request vr,\n     model m, \n     usr u \nwhere vr.user_id = u.user_id and \n      u.user_id = ? and \n      vr.model_id = m.model_id and       vr.request_status = ? ", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1784 */       closeResultSet(rs);
/* 1785 */       closeStatement(stmt);
/* 1786 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public List<UserRef> queryVirementOriginators(int financeCubeId)
/*      */   {
/* 1797 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1798 */     ResultSet rs = null;
/* 1799 */     SqlExecutor sqle = null;
/*      */ 
/* 1801 */     SqlBuilder builder = new SqlBuilder(new String[] { "select distinct user_id,", "                nvl(name,user_id) as name,  ", "                nvl(full_name,user_id) as full_name", "from virement_request vr", "left outer join usr u using(user_id)", "where vr.finance_cube_id = <financeCubeId>", "order by name" });
/*      */     try
/*      */     {
/* 1812 */       List result = new ArrayList();
/*      */ 
/* 1814 */       sqle = new SqlExecutor("queryVirementOriginators", getDataSource(), builder, this._log);
/*      */ 
/* 1816 */       sqle.addBindVariable("<financeCubeId>", Integer.valueOf(financeCubeId));
/*      */ 
/* 1818 */       rs = sqle.getResultSet();
/*      */ 
/* 1820 */       result.add(new UserRefImpl(new UserPK(-1), "Any User"));
/*      */ 
/* 1822 */       while (rs.next()) {
/* 1823 */         result.add(new UserRefImpl(new UserPK(rs.getInt("user_id")), rs.getString("name") + " - " + rs.getString("full_name")));
/*      */       }
/*      */ 
/* 1826 */       if (timer != null) {
/* 1827 */         timer.logDebug("queryVirementRequests");
/*      */       }
/* 1829 */       List localList1 = result;
/*      */       return localList1;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1833 */       throw handleSQLException(builder.toString(), e);
/*      */     }
/*      */     finally
/*      */     {
/* 1837 */       if (sqle != null)
/* 1838 */         sqle.close(); 
/* 1838 */     }
/*      */   }
/*      */ 
/*      */   public List<UserRef> queryVirementAuthorisers(int financeCubeId)
/*      */   {
/* 1849 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1850 */     ResultSet rs = null;
/* 1851 */     SqlExecutor sqle = null;
/*      */ 
/* 1853 */     SqlBuilder builder = new SqlBuilder(new String[] { "select distinct va.user_id,", "                nvl(name,va.user_id) as name,", "                nvl(full_name,va.user_id) as full_name", "from virement_authorisers va", "left join virement_auth_point vap using(auth_point_id)", "left join virement_request vr using(request_id)", "left outer join usr u on u.user_id = va.user_id", "where vr.finance_cube_id = <financeCubeId>", "order by name" });
/*      */     try
/*      */     {
/* 1866 */       List result = new ArrayList();
/*      */ 
/* 1868 */       sqle = new SqlExecutor("queryVirementAuthorisers", getDataSource(), builder, this._log);
/*      */ 
/* 1870 */       sqle.addBindVariable("<financeCubeId>", Integer.valueOf(financeCubeId));
/*      */ 
/* 1872 */       rs = sqle.getResultSet();
/*      */ 
/* 1874 */       result.add(new UserRefImpl(new UserPK(-1), "Any Authoriser"));
/*      */ 
/* 1876 */       while (rs.next()) {
/* 1877 */         result.add(new UserRefImpl(new UserPK(rs.getInt("user_id")), rs.getString("name") + " - " + rs.getString("full_name")));
/*      */       }
/*      */ 
/* 1880 */       if (timer != null) {
/* 1881 */         timer.logDebug("queryVirementAuthorisers");
/*      */       }
/* 1883 */       List localList1 = result;
/*      */       return localList1;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1887 */       throw handleSQLException(builder.toString(), e);
/*      */     }
/*      */     finally
/*      */     {
/* 1891 */       if (sqle != null)
/* 1892 */         sqle.close(); 
/* 1892 */     }
/*      */   }
/*      */ 
/*      */   public String queryVirementRequest(int requestId)
/*      */   {
/* 1905 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1906 */     ResultSet rs = null;
/* 1907 */     SqlExecutor sqle = null;
/*      */ 
/* 1909 */     SqlBuilder builder = new SqlBuilder(new String[] { "select request_id, model_id, finance_cube_id, reason, reference, request_status, to_char(date_submitted,'DD/MM/YYYY') as date_submitted,        to_char(vr.created_time,'DD/MM/YYYY') as date_created,", "\t\tuser_id, full_name, nvl(name,user_id) as user_vis_id,", "       cursor( select vrg.*,", "                      cursor( select vrl.*,nvl(wp.vis_id,vrl.spread_profile_id) profile_vis_id, dt.vis_id as data_type_vis_id, dt.description, ", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim0) vis_id0,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim1) vis_id1,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim2) vis_id2,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim3) vis_id3,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim4) vis_id4,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim5) vis_id5,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim6) vis_id6,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim7) vis_id7,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim8) vis_id8,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim9) vis_id9,", "                              cursor( select vls.*, ", "                                      (select vis_id||' - '||description", "                                       from structure_element se", "                                       where se.structure_element_id = vls.structure_element_id) vis_id", "                                      from virement_line_spread vls", "                                      where vls.request_line_id = vrl.request_line_id", "\t\t\t\t\t\t\t\t\t   order by line_idx ) as spreads", "                              from virement_request_line vrl", "                              left join weighting_profile wp on (vrl.spread_profile_id = wp.profile_id)", "                              left join data_type dt on ( vrl.data_type_id = dt.data_type_id )", "                              where vrl.request_group_id = vrg.request_group_id", "\t\t\t\t\t\t\t   order by line_idx ) as lines", "               from virement_request_group vrg", "               where vrg.request_id = vr.request_id) as groups,", "       cursor( select vap.*, nvl(u.name,vap.auth_user_id) as user_vis_id,u.full_name as authorisers_usr_full_name,", "\t\t\t\tse.vis_id || ' - ' || se.description as se_vis_id,", "\t\t\t\tto_char(vap.updated_time,'DD/MM/YYYY') as date_updated,", "               cursor( select vapl.*", "               \t\tfrom virement_auth_point_link vapl", "                       where vapl.auth_point_id = vap.auth_point_id ) as links,", "               cursor( select va.auth_point_id, nvl(u.name,user_id) as user_vis_id", "                       from virement_authorisers va", "                       left join usr u using (user_id)", "                       where va.auth_point_id = vap.auth_point_id ) as authorisers", "               from virement_auth_point vap", "\t\t\t\tleft join structure_element se on ( vap.structure_element_id = se.structure_element_id )", "               left join usr u on ( vap.auth_user_id = u.user_id)", "               where vap.request_id = vr.request_id ) as authorisers", "from virement_request vr", "left join usr u using (user_id)", "where request_id = <requestId>" });
/*      */     try
/*      */     {
/* 1959 */       sqle = new SqlExecutor("queryVirementRequest", getDataSource(), builder, this._log);
/*      */ 
/* 1961 */       sqle.addBindVariable("<requestId>", Integer.valueOf(requestId));
/*      */ 
/* 1963 */       rs = sqle.getResultSet();
/*      */ 
/* 1966 */       if (rs.next())
/*      */       {
/* 1968 */         int modelId = rs.getInt("model_id");
/* 1969 */         ModelDAO modelDAO = new ModelDAO();
/* 1970 */         ModelDimensionsELO modelDimensionELO = modelDAO.getModelDimensions(modelId);
/* 1971 */         String str = convertRequestToXML(rs, modelDimensionELO.getNumRows(), modelDimensionELO);
/*      */         return str;
/*      */       }
/*      */       return null;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1978 */       throw handleSQLException(builder.toString(), e);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1982 */       throw new RuntimeException("queryVirementReqeusts", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1986 */       if (sqle != null) {
/* 1987 */         sqle.close();
/*      */       }
/* 1989 */       if (timer != null)
/* 1990 */         timer.logDebug("queryVirementRequests"); 
/* 1990 */     }
/*      */   }
/*      */ 
/*      */   public List<String> queryVirementRequests(int modelId, int numDims, Integer creator, Integer authoriser, Integer virementId, Integer status, List<StructureElementKey> structureElements, Double minimumValue, Double maximumValue, java.util.Date fromDate, java.util.Date toDate)
/*      */   {
/* 2016 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2017 */     ResultSet rs = null;
/* 2018 */     SqlExecutor sqle = null;
/*      */ 
/* 2020 */     SqlBuilder builder = new SqlBuilder(new String[] { "select request_id, model_id, finance_cube_id, reason, reference, request_status, to_char(date_submitted,'DD/MM/YYYY') as date_submitted,        to_char(vr.created_time,'DD/MM/YYYY') as date_created,", "\t\tuser_id, full_name, nvl(name,user_id) as user_vis_id,", "       cursor( select vrg.*,", "                      cursor( select vrl.*,nvl(wp.vis_id,vrl.spread_profile_id) profile_vis_id, dt.vis_id as data_type_vis_id, dt.description, ", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim0) vis_id0,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim1) vis_id1,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim2) vis_id2,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim3) vis_id3,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim4) vis_id4,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim5) vis_id5,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim6) vis_id6,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim7) vis_id7,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim8) vis_id8,", "                              (select vis_id||' - '||description from structure_element se where se.structure_element_id = vrl.dim9) vis_id9,", "                              cursor( select vls.*, ", "                                      (select vis_id||' - '||description", "                                       from structure_element se", "                                       where se.structure_element_id = vls.structure_element_id) vis_id", "                                      from virement_line_spread vls", "                                      where vls.request_line_id = vrl.request_line_id", "\t\t\t\t\t\t\t\t\t   order by line_idx ) as spreads", "                              from virement_request_line vrl", "                              left join weighting_profile wp on (vrl.spread_profile_id = wp.profile_id)", "                              left join data_type dt on ( vrl.data_type_id = dt.data_type_id )", "                              where vrl.request_group_id = vrg.request_group_id", "\t\t\t\t\t\t\t   order by line_idx ) as lines", "               from virement_request_group vrg", "               where vrg.request_id = vr.request_id) as groups,", "       cursor( select vap.*, nvl(u.name,vap.auth_user_id) as user_vis_id,u.full_name as authorisers_usr_full_name,", "\t\t\t\tse.vis_id || ' - ' || se.description as se_vis_id,", "\t\t\t\tto_char(vap.updated_time,'DD/MM/YYYY') as date_updated,", "               cursor( select vapl.*", "               \t\tfrom virement_auth_point_link vapl", "                       where vapl.auth_point_id = vap.auth_point_id ) as links,", "               cursor( select va.auth_point_id, nvl(u.name,user_id) as user_vis_id", "                       from virement_authorisers va", "                       left join usr u using (user_id)", "                       where va.auth_point_id = vap.auth_point_id ) as authorisers", "               from virement_auth_point vap", "\t\t\t\tleft join structure_element se on ( vap.structure_element_id = se.structure_element_id )", "               left join usr u on ( vap.auth_user_id = u.user_id)", "               where vap.request_id = vr.request_id ) as authorisers", "from virement_request vr", "left join usr u using (user_id)", "where request_id in", "(", "    select distinct request_id", "    from", "    (", "        <seQueryOne>", "        select user_id, request_id, sum( decode(target,'Y',transfer_value,0) ) total_value", "        from virement_request vr", "        left join virement_request_group vrg using (request_id)", "        left join virement_request_line vrl using(request_group_id)", "        <seQueryTwo>", "        cross join se_pos", "        where vr.model_id = <modelId>", "        <datePredicate>", "        <seQueryThree>", "        group by user_id, request_id", "    ) vr", "    left join virement_auth_point vap using(request_id)", "    where vr.model_id = <modelId>" });
/*      */ 
/* 2087 */     if (!structureElements.isEmpty())
/*      */     {
/* 2089 */       SqlBuilder seQueryOne = new SqlBuilder(new String[] { "with hiers as", "(", "  select model_id, dimension_seq_num, hierarchy_id", "  from model_dimension_rel mdr", "  left join hierarchy h using(dimension_id)", "  where model_id = <modelId>", "),", "se_pos as", "(", "  select", "\t\t<selectSeParams>", "  from dual", ")" });
/*      */ 
/* 2104 */       seQueryOne.substituteRepeatingLines("<selectSeParams>", structureElements.size(), ",", new String[] { "${separator}(select position", " from structure_element se", " where se.structure_id in (select hierarchy_id from hiers where dimension_seq_num = ${index})", "   and se.structure_element_id = <se${index}Id>) sp${index},", "(select end_position", " from structure_element se", " where se.structure_id in (select hierarchy_id from hiers where dimension_seq_num = ${index})", "   and se.structure_element_id = <se${index}Id>) ep${index}" });
/*      */ 
/* 2114 */       builder.substituteLines("<seQueryOne>", seQueryOne);
/*      */ 
/* 2116 */       builder.substituteRepeatingLines("<seQueryTwo>", structureElements.size(), "", new String[] { "  left join structure_element se${index}", "    on (dim${index} = se${index}.structure_element_id)", "   and se${index}.structure_id in (select hierarchy_id from hiers where dimension_seq_num = ${index})" });
/*      */ 
/* 2121 */       SqlBuilder seQueryThree = new SqlBuilder(new String[] { "and <seQuery>" });
/* 2122 */       seQueryThree.substituteRepeatingLines("<seQuery>", structureElements.size(), "and", new String[] { "${separator} sp${index} <= se${index}.position and se${index}.position <= ep${index}" });
/*      */ 
/* 2124 */       builder.substitute(new String[] { "<seQueryThree>", seQueryThree.toString() });
/*      */     }
/*      */     else
/*      */     {
/* 2128 */       builder.substitute(new String[] { "<seQueryOne>", "" });
/* 2129 */       builder.substitute(new String[] { "<seQueryTwo>", "" });
/* 2130 */       builder.substitute(new String[] { "<seQueryTHree", "" });
/*      */     }
/*      */ 
/* 2134 */     SqlBuilder datePredicate = new SqlBuilder();
/* 2135 */     if (fromDate != null)
/* 2136 */       datePredicate.addLines(new String[] { "and <fromDate> <= vr.date_submitted" });
/* 2137 */     if (toDate != null)
/* 2138 */       datePredicate.addLines(new String[] { "and vr.date_submitted <= <toDate>" });
/* 2139 */     builder.substitute(new String[] { "<datePredicate>", datePredicate.toString() });
/*      */ 
/* 2142 */     if ((authoriser != null) && (authoriser.intValue() != -1)) {
/* 2143 */       builder.addLine("and vap.auth_user_id = <authId>");
/*      */     }
/* 2145 */     if ((creator != null) && (creator.intValue() != -1)) {
/* 2146 */       builder.addLine("and user_id = <userId>");
/*      */     }
/* 2148 */     if (minimumValue != null) {
/* 2149 */       builder.addLine("and total_value >= <minimumValue>");
/*      */     }
/* 2151 */     if (maximumValue != null) {
/* 2152 */       builder.addLine("and total_value <= <maximumValue>");
/*      */     }
/* 2154 */     if (virementId != null) {
/* 2155 */       builder.addLine("and request_id = <requestId>");
/*      */     }
/* 2157 */     if (status != null) {
/* 2158 */       builder.addLine("and vr.request_status = <requestStatus>");
/*      */     }
/* 2160 */     builder.addLine(")");
/*      */ 
/* 2162 */     builder.addLine("order by vr.request_id");
/*      */     try
/*      */     {
/* 2166 */       List result = new ArrayList();
/*      */ 
/* 2168 */       sqle = new SqlExecutor("queryVirementRequests", getDataSource(), builder, this._log);
/*      */ 
/* 2170 */       sqle.addBindVariable("<modelId>", Integer.valueOf(modelId));
/*      */ 
/* 2172 */       for (int i = 0; i < structureElements.size(); i++) {
/* 2173 */         sqle.addBindVariable("<se" + i + "Id>", Integer.valueOf(((StructureElementKey)structureElements.get(i)).getId()));
/*      */       }
/* 2175 */       if (fromDate != null) {
/* 2176 */         sqle.addBindVariable("<fromDate>", new java.sql.Date(fromDate.getTime()));
/*      */       }
/* 2178 */       if (toDate != null) {
/* 2179 */         sqle.addBindVariable("<toDate>", new java.sql.Date(toDate.getTime()));
/*      */       }
/* 2181 */       if (authoriser != null) {
/* 2182 */         sqle.addBindVariable("<authId>", authoriser);
/*      */       }
/* 2184 */       if (authoriser != null) {
/* 2185 */         sqle.addBindVariable("<userId>", creator);
/*      */       }
/* 2187 */       if (minimumValue != null) {
/* 2188 */         sqle.addBindVariable("<minimumValue>", minimumValue);
/*      */       }
/* 2190 */       if (maximumValue != null) {
/* 2191 */         sqle.addBindVariable("<maximumValue>", maximumValue);
/*      */       }
/* 2193 */       if (virementId != null) {
/* 2194 */         sqle.addBindVariable("<requestId>", virementId);
/*      */       }
/* 2196 */       if (status != null) {
/* 2197 */         sqle.addBindVariable("<requestStatus>", status);
/*      */       }
/* 2199 */       rs = sqle.getResultSet();
/*      */ 
/* 2202 */       ModelDAO modelDAO = new ModelDAO();
/* 2203 */       ModelDimensionsELO modelDimensionELO = modelDAO.getModelDimensions(modelId);
/*      */ 
/* 2205 */       while (rs.next()) {
/* 2206 */         result.add(convertRequestToXML(rs, numDims, modelDimensionELO));
/*      */       }
/* 2208 */       if (timer != null) {
/* 2209 */         timer.logDebug("queryVirementRequests");
/*      */       }
/* 2211 */       List localList1 = result;
/*      */       return localList1;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2215 */       throw handleSQLException(builder.toString(), e);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 2219 */       throw new RuntimeException("queryVirementReqeusts", e);
/*      */     }
/*      */     finally
/*      */     {
/* 2223 */       if (sqle != null)
/* 2224 */         sqle.close(); 
/* 2224 */     }
/*      */   }
/*      */ 
/*      */   private String convertRequestToXML(ResultSet rs, int numDims, ModelDimensionsELO modelDimensions)
/*      */     throws SQLException, IOException
/*      */   {
/* 2231 */     StringWriter sw = new StringWriter();
/* 2232 */     PrintWriter pw = new PrintWriter(sw);
/*      */ 
/* 2234 */     pw.print("<VirementRequest ");
/* 2235 */     XmlUtils.outputAttribute(pw, "requestId", Integer.valueOf(rs.getInt("request_id")));
/* 2236 */     XmlUtils.outputAttribute(pw, "modelId", Integer.valueOf(rs.getInt("model_id")));
/* 2237 */     XmlUtils.outputAttribute(pw, "financeCubeId", Integer.valueOf(rs.getInt("finance_cube_id")));
/* 2238 */     XmlUtils.outputAttribute(pw, "requestStatus", queryVirementStatus(rs.getInt("request_status")));
/* 2239 */     XmlUtils.outputAttribute(pw, "reason", rs.getString("reason"));
/* 2240 */     XmlUtils.outputAttribute(pw, "reference", rs.getString("reference"));
/* 2241 */     XmlUtils.outputAttribute(pw, "dateSubmitted", rs.getString("date_submitted"));
/* 2242 */     XmlUtils.outputAttribute(pw, "dateCreated", rs.getString("date_created"));
/* 2243 */     XmlUtils.outputAttribute(pw, "userId", Integer.valueOf(rs.getInt("user_id")));
/* 2244 */     XmlUtils.outputAttribute(pw, "userFullName", rs.getString("full_name"));
/* 2245 */     XmlUtils.outputAttribute(pw, "userVisId", rs.getString("user_vis_id"));
/* 2246 */     pw.print(">");
/*      */ 
/* 2248 */     convertRequestGroupsToXML(pw, (ResultSet)rs.getObject("groups"), numDims, modelDimensions);
/*      */ 
/* 2250 */     convertRequestAuthPointsToXML(pw, (ResultSet)rs.getObject("authorisers"));
/*      */ 
/* 2252 */     pw.print("</VirementRequest>");
/*      */ 
/* 2254 */     pw.flush();
/* 2255 */     pw.close();
/* 2256 */     return sw.getBuffer().toString();
/*      */   }
/*      */ 
/*      */   private void convertRequestAuthPointsToXML(PrintWriter pw, ResultSet rs)
/*      */     throws SQLException, IOException
/*      */   {
/* 2262 */     while (rs.next())
/*      */     {
/* 2264 */       pw.print("<VirementAuthPoint");
/* 2265 */       XmlUtils.outputAttribute(pw, "seVisId", rs.getString("se_vis_id"));
/* 2266 */       XmlUtils.outputAttribute(pw, "userVisId", rs.getString("user_vis_id"));
/* 2267 */       XmlUtils.outputAttribute(pw, "authorisersUserFullName", rs.getString("authorisers_usr_full_name"));
/* 2268 */       XmlUtils.outputAttribute(pw, "notes", rs.getString("notes"));
/* 2269 */       XmlUtils.outputAttribute(pw, "pointStatus", queryPointStatus(rs.getInt("point_status")));
/* 2270 */       XmlUtils.outputAttribute(pw, "dateUpdated", rs.getString("date_updated"));
/* 2271 */       pw.print(">");
/* 2272 */       pw.print("</VirementAuthPoint>");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void convertRequestGroupsToXML(PrintWriter pw, ResultSet rs, int numDims, ModelDimensionsELO modelDimensions)
/*      */     throws SQLException, IOException
/*      */   {
/* 2279 */     while (rs.next())
/*      */     {
/* 2281 */       pw.print("<VirementRequestGroup ");
/* 2282 */       XmlUtils.outputAttribute(pw, "notes", rs.getString("notes"));
/* 2283 */       pw.print(">");
/* 2284 */       for (int i = 0; i < modelDimensions.getNumRows(); i++)
/*      */       {
/* 2286 */         pw.print("<DimHeader>");
/* 2287 */         pw.println(XmlUtils.escapeStringForXML(String.valueOf(modelDimensions.getValueAt(i, "Dimension"))));
/* 2288 */         pw.print(" - ");
/* 2289 */         pw.print(XmlUtils.escapeStringForXML(String.valueOf(modelDimensions.getValueAt(i, "Description"))));
/* 2290 */         pw.print("</DimHeader>");
/*      */       }
/* 2292 */       convertRequestLinesToXML(pw, (ResultSet)rs.getObject("lines"), numDims);
/* 2293 */       pw.print("</VirementRequestGroup>");
/*      */     }
/* 2295 */     rs.close();
/*      */   }
/*      */ 
/*      */   private void convertRequestLinesToXML(PrintWriter pw, ResultSet rs, int numDims)
/*      */     throws SQLException, IOException
/*      */   {
/* 2301 */     while (rs.next())
/*      */     {
/* 2303 */       pw.print("<VirementRequestLine ");
/* 2304 */       XmlUtils.outputAttribute(pw, "target", rs.getString("target"));
/* 2305 */       double value = GeneralUtils.convertDBToFinancialValue(rs.getLong("transfer_value"));
/* 2306 */       XmlUtils.outputAttribute(pw, "transferValue", Double.valueOf(value));
/* 2307 */       XmlUtils.outputAttribute(pw, "dataTypeVisId", rs.getString("data_type_vis_id"));
/* 2308 */       XmlUtils.outputAttribute(pw, "profileVisId", rs.getString("profile_vis_id"));
/* 2309 */       XmlUtils.outputAttribute(pw, "dataTypeDescription", rs.getString("description"));
/* 2310 */       pw.print(">");
/* 2311 */       for (int i = 0; i < numDims; i++)
/*      */       {
/* 2313 */         pw.print("<DimVisId>");
/* 2314 */         pw.print(XmlUtils.escapeStringForXML(rs.getString("vis_id" + i)));
/* 2315 */         pw.print("</DimVisId>");
/*      */       }
/* 2317 */       convertRequestLineSpreadsToXML(pw, (ResultSet)rs.getObject("spreads"), value);
/* 2318 */       pw.print("</VirementRequestLine>");
/*      */     }
/* 2320 */     rs.close();
/*      */   }
/*      */ 
/*      */   private void convertRequestLineSpreadsToXML(PrintWriter pw, ResultSet rs, double value)
/*      */     throws SQLException, IOException
/*      */   {
/* 2326 */     while (rs.next())
/*      */     {
/* 2328 */       pw.print("<VirementLineSpread ");
/* 2329 */       XmlUtils.outputAttribute(pw, "vis_id", rs.getString("vis_id"));
/* 2330 */       XmlUtils.outputAttribute(pw, "held", Boolean.valueOf(rs.getString("held").equalsIgnoreCase("Y")));
/* 2331 */       XmlUtils.outputAttribute(pw, "weighting", Integer.valueOf(rs.getInt("weighting")));
/* 2332 */       pw.print("/>");
/*      */     }
/* 2334 */     rs.close();
/*      */   }
/*      */ 
/*      */   private String queryPointStatus(int pointStatus)
/*      */   {
/* 2339 */     switch (pointStatus) {
/*      */     case 0:
/* 2341 */       return "Not authorised";
/*      */     case 1:
/* 2342 */       return "Authorised";
/*      */     case 2:
/* 2343 */       return "Rejected";
/*      */     }
/* 2345 */     throw new IllegalArgumentException("Unknown virement point status:" + pointStatus);
/*      */   }
/*      */ 
/*      */   private String queryVirementStatus(int virementStatus)
/*      */   {
/* 2351 */     switch (virementStatus) {
/*      */     case 2:
/* 2353 */       return "Authorise";
/*      */     case 1:
/* 2354 */       return "Not authorised";
/*      */     case 0:
/* 2355 */       return "Not submitted";
/*      */     case 3:
/* 2356 */       return "Processed";
/*      */     }
/* 2358 */     throw new IllegalArgumentException("Unknown virement status:" + virementStatus);
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.virement.VirementRequestDAO
 * JD-Core Version:    0.6.0
 */