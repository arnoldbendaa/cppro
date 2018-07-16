/*      */ package com.cedar.cp.ejb.impl.model.cc;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.model.cc.RuntimeCcDeployment;
/*      */ import com.cedar.cp.api.model.cc.RuntimeCcDeploymentLine;
/*      */ import com.cedar.cp.api.model.cc.RuntimeCcDeploymentMapping;
/*      */ import com.cedar.cp.api.model.cc.RuntimeCcDeploymentTarget;
/*      */ import com.cedar.cp.api.model.cc.RuntimeCubeDeployment;
/*      */ import com.cedar.cp.api.model.cc.RuntimeCubeDeploymentEntry;
/*      */ import com.cedar.cp.api.model.cc.RuntimeCubeDeploymentLine;
/*      */ import com.cedar.cp.api.model.cc.RuntimeCubeFormulaDeployment;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.cc.AllCcDeploymentsELO;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentCK;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentCellPickerInfoELO;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentLineCK;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentLinePK;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentPK;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentRefImpl;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentXMLFormTypeELO;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentsForLookupTableELO;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentsForModelELO;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentsForXmlFormELO;
/*      */ import com.cedar.cp.dto.xmlform.XmlFormPK;
/*      */ import com.cedar.cp.dto.xmlform.XmlFormRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*      */ import com.cedar.cp.ejb.impl.model.ModelEVO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Pair;
/*      */ import com.cedar.cp.util.SqlBuilder;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import com.cedar.cp.util.common.JdbcUtils;
/*      */ import com.cedar.cp.util.common.JdbcUtils.ColType;
/*      */ import com.cedar.cp.util.xmlform.CalendarElementNode;
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
/*      */ import org.antlr.stringtemplate.StringTemplate;
/*      */ 
/*      */ public class CcDeploymentDAO extends AbstractDAO
/*      */ {
/*   64 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT.MODEL_ID,CC_DEPLOYMENT.VIS_ID,CC_DEPLOYMENT.DESCRIPTION,CC_DEPLOYMENT.XMLFORM_ID,CC_DEPLOYMENT.DIM_CONTEXT0,CC_DEPLOYMENT.DIM_CONTEXT1,CC_DEPLOYMENT.DIM_CONTEXT2,CC_DEPLOYMENT.DIM_CONTEXT3,CC_DEPLOYMENT.DIM_CONTEXT4,CC_DEPLOYMENT.DIM_CONTEXT5,CC_DEPLOYMENT.DIM_CONTEXT6,CC_DEPLOYMENT.DIM_CONTEXT7,CC_DEPLOYMENT.DIM_CONTEXT8,CC_DEPLOYMENT.DIM_CONTEXT9,CC_DEPLOYMENT.VERSION_NUM,CC_DEPLOYMENT.UPDATED_BY_USER_ID,CC_DEPLOYMENT.UPDATED_TIME,CC_DEPLOYMENT.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from CC_DEPLOYMENT where    CC_DEPLOYMENT_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into CC_DEPLOYMENT ( CC_DEPLOYMENT_ID,MODEL_ID,VIS_ID,DESCRIPTION,XMLFORM_ID,DIM_CONTEXT0,DIM_CONTEXT1,DIM_CONTEXT2,DIM_CONTEXT3,DIM_CONTEXT4,DIM_CONTEXT5,DIM_CONTEXT6,DIM_CONTEXT7,DIM_CONTEXT8,DIM_CONTEXT9,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update CC_DEPLOYMENT set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,XMLFORM_ID = ?,DIM_CONTEXT0 = ?,DIM_CONTEXT1 = ?,DIM_CONTEXT2 = ?,DIM_CONTEXT3 = ?,DIM_CONTEXT4 = ?,DIM_CONTEXT5 = ?,DIM_CONTEXT6 = ?,DIM_CONTEXT7 = ?,DIM_CONTEXT8 = ?,DIM_CONTEXT9 = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CC_DEPLOYMENT_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from CC_DEPLOYMENT where CC_DEPLOYMENT_ID = ?";
/*  493 */   protected static String SQL_ALL_CC_DEPLOYMENTS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,CC_DEPLOYMENT.CC_DEPLOYMENT_ID      ,CC_DEPLOYMENT.VIS_ID      ,CC_DEPLOYMENT.DESCRIPTION      ,CC_DEPLOYMENT.CC_DEPLOYMENT_ID from CC_DEPLOYMENT    ,MODEL where 1=1   and CC_DEPLOYMENT.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID, CC_DEPLOYMENT.VIS_ID";
/*      */ 
/*  602 */   protected static String SQL_CC_DEPLOYMENTS_FOR_LOOKUP_TABLE = "select distinct 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,CC_DEPLOYMENT.CC_DEPLOYMENT_ID      ,CC_DEPLOYMENT.VIS_ID      ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,CC_DEPLOYMENT.DESCRIPTION from CC_DEPLOYMENT    ,MODEL    ,XML_FORM where 1=1   and CC_DEPLOYMENT.MODEL_ID = MODEL.MODEL_ID  and  XML_FORM.TYPE < 3 AND XML_FORM.DEFINITION LIKE ? AND XML_FORM.XML_FORM_ID = CC_DEPLOYMENT.XMLFORM_ID";
/*      */ 
/*  729 */   protected static String SQL_CC_DEPLOYMENTS_FOR_XML_FORM = "select distinct 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,CC_DEPLOYMENT.CC_DEPLOYMENT_ID      ,CC_DEPLOYMENT.VIS_ID      ,CC_DEPLOYMENT.DESCRIPTION from CC_DEPLOYMENT    ,MODEL where 1=1   and CC_DEPLOYMENT.MODEL_ID = MODEL.MODEL_ID  and  XMLFORM_ID = ?";
/*      */ 
/*  840 */   protected static String SQL_CC_DEPLOYMENTS_FOR_MODEL = "select distinct 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,CC_DEPLOYMENT.CC_DEPLOYMENT_ID      ,CC_DEPLOYMENT.VIS_ID      ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,CC_DEPLOYMENT.DESCRIPTION      ,XML_FORM.XML_FORM_ID from CC_DEPLOYMENT    ,MODEL    ,XML_FORM where 1=1   and CC_DEPLOYMENT.MODEL_ID = MODEL.MODEL_ID  and  CC_DEPLOYMENT.MODEL_ID = ? and CC_DEPLOYMENT.XMLFORM_ID = XML_FORM.XML_FORM_ID";
/*      */ 
/*  970 */   protected static String SQL_CC_DEPLOYMENT_CELL_PICKER_INFO = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,CC_DEPLOYMENT.CC_DEPLOYMENT_ID      ,CC_DEPLOYMENT.VIS_ID      ,CC_DEPLOYMENT.MODEL_ID from CC_DEPLOYMENT    ,MODEL where 1=1   and CC_DEPLOYMENT.MODEL_ID = MODEL.MODEL_ID  and  CC_DEPLOYMENT.CC_DEPLOYMENT_ID = ?";
/*      */ 
/* 1080 */   protected static String SQL_CC_DEPLOYMENT_X_M_L_FORM_TYPE = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,CC_DEPLOYMENT.CC_DEPLOYMENT_ID      ,CC_DEPLOYMENT.VIS_ID      ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,CC_DEPLOYMENT.CC_DEPLOYMENT_ID      ,XML_FORM.XML_FORM_ID      ,XML_FORM.TYPE from CC_DEPLOYMENT    ,MODEL    ,XML_FORM where 1=1   and CC_DEPLOYMENT.MODEL_ID = MODEL.MODEL_ID  and  CC_DEPLOYMENT.CC_DEPLOYMENT_ID = ? and CC_DEPLOYMENT.XMLFORM_ID = XML_FORM.XML_FORM_ID";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from CC_DEPLOYMENT where    CC_DEPLOYMENT_ID = ? ";
/* 1348 */   private static String[][] SQL_DELETE_CHILDREN = { { "CC_DEPLOYMENT_LINE", "delete from CC_DEPLOYMENT_LINE where     CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = ? " } };
/*      */ 
/* 1357 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "CC_DEPLOYMENT_ENTRY", "delete from CC_DEPLOYMENT_ENTRY CcDeploymentEntry where exists (select * from CC_DEPLOYMENT_ENTRY,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where     CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CcDeploymentEntry.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID " }, { "CC_DEPLOYMENT_DATA_TYPE", "delete from CC_DEPLOYMENT_DATA_TYPE CcDeploymentDataType where exists (select * from CC_DEPLOYMENT_DATA_TYPE,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where     CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CcDeploymentDataType.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID " }, { "CC_MAPPING_LINE", "delete from CC_MAPPING_LINE CcMappingLine where exists (select * from CC_MAPPING_LINE,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where     CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CcMappingLine.CC_DEPLOYMENT_LINE_ID = CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID " }, { "CC_MAPPING_ENTRY", "delete from CC_MAPPING_ENTRY CcMappingEntry where exists (select * from CC_MAPPING_ENTRY,CC_MAPPING_LINE,CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where     CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID = CC_MAPPING_LINE.CC_MAPPING_LINE_ID and CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CcMappingEntry.CC_MAPPING_LINE_ID = CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID " } };
/*      */ 
/* 1407 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and CC_DEPLOYMENT.CC_DEPLOYMENT_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_DEPLOYMENT.CC_DEPLOYMENT_ID";
/*      */   protected static final String SQL_GET_ALL = " from CC_DEPLOYMENT where    MODEL_ID = ? ";
/* 2522 */   private static final String[] QUERY_INVALID_DEPLOYMENT_LINES = { "select ccd.cc_deployment_id, ccd.vis_id, ccdl.cc_deployment_line_id, ", "       ccdl.line_index, ccde.cc_deployment_entry_id ", "from cc_deployment_line ccdl, ", "     cc_deployment_entry ccde, ", "     cc_deployment ccd ", "where ccd.model_id = ? ", "  and ccd.cc_deployment_id = ccdl.cc_deployment_id ", "  and ccdl.cc_deployment_line_id = ccde.cc_deployment_line_id ", "  and ( ccde.structure_id, ccde.structure_element_id ) not in ", "      ( select se.structure_id, se.structure_element_id ", "        from structure_element_view se ) " };
/*      */ 
/* 2537 */   private static final String[] QUERY_DEPLOYMENT_LINE_WITH_ZERO_DIM_ENTRIES = { "select cc_deployment_line_id", "from", "(", "  select cc_deployment_line_id ", "        ,${OuterSumDimCount} ", "  from ", "  ( ", "    select ccde.cc_deployment_line_id, ", "           ${InnerDimSeqSumCaseCount}", "    from cc_deployment ccd,", "         cc_deployment_line ccdl,", "         cc_deployment_entry ccde ", "    where ccd.model_id = ? ", "      and ccd.cc_deployment_id = ccdl.cc_deployment_id ", "      and ccdl.cc_deployment_line_id = ccde.cc_deployment_line_id ", "      and ccde.selected_ind = 'Y'", "    group by ccde.cc_deployment_line_id, ccde.dim_seq ", "  )", "  group by cc_deployment_line_id", ")", "where ${DimCountWherePredicate} " };
/*      */ 
/* 2562 */   private static final JdbcUtils.ColType[] ORPHAN_DEPLOYMENT_LINES_COL_INFO = { new JdbcUtils.ColType("cc_deployment_id", 0), new JdbcUtils.ColType("vis_id", "vis_id", 1), new JdbcUtils.ColType("cc_deployment_line_id", 0), new JdbcUtils.ColType("line_index", 0) };
/*      */ 
/* 2571 */   private static final String[] DELETE_DEPLOYMENT_ENTRIES = { "delete from cc_deployment_entry ccde", "where ccde.cc_deployment_entry_id in", "(", "  select cc_deployment_entry_id ", "  from ( ${OrphanQuery} ) ", ")" };
/*      */ 
/* 2581 */   private static final String[] DELETE_DEPLOYMENT_LINES = { "delete from cc_deployment_line ccdl", "where ccdl.cc_deployment_line_id in", "(", "  select cc_deployment_line_id ", "  from ( ${OrphanQuery} ) ", ")" };
/*      */   private static final String QUERY_ORPHAN_MAPPING_LINES = "select ccd.cc_deployment_id, ccd.vis_id, ccdl.cc_deployment_line_id, \n       ccml.cc_mapping_line_id \nfrom cc_deployment ccd, \n     cc_deployment_line ccdl, \n     cc_mapping_line ccml, \n     cc_mapping_entry ccme \nwhere ccd.model_id = ? \n  and ccd.cc_deployment_id = ccdl.cc_deployment_id \n  and ccdl.cc_deployment_line_id = ccml.cc_deployment_line_id \n  and ccme.cc_mapping_line_id (+) = ccml.cc_mapping_line_id \n  and ( ccme.structure_id, ccme.structure_element_id ) not in \n      ( select se.structure_id, se.structure_element_id \n        from structure_element_view se )";
/* 2680 */   private static final JdbcUtils.ColType[] ORPHAN_MAPPING_LINES_COL_INFO = { new JdbcUtils.ColType("cc_deployment_id", 0), new JdbcUtils.ColType("vis_id", "vis_id", 1), new JdbcUtils.ColType("cc_deployment_line_id", 0), new JdbcUtils.ColType("cc_mapping_line_id", 0) };
/*      */ 
/* 2689 */   private static final String[] DELETE_ORPHAN_MAPPING_LINES = { "delete from cc_mapping_line ccml", "where ccml.cc_mapping_line_id in", "( ", "  select distinct cc_mapping_line_id", "  from ( ${OrphanQuery} ) ", ") " };
/*      */ 
/* 2749 */   private static final String[] DELETE_ORPHAN_MAPPING_ENTRIES = { "delete from cc_mapping_entry ccme ", "where ccme.cc_mapping_line_id ", "not in ", "( ", "  select cc_mapping_line_id from cc_mapping_line ", ") " };
/*      */ 
/* 2785 */   private static String sQUERY_CELL_CALC_DETAILS = "select short_id, cell_calc_id from cft${} where {1}";
/*      */   protected CcDeploymentLineDAO mCcDeploymentLineDAO;
/*      */   protected CcDeploymentEVO mDetails;
/*      */ 
/*      */   public CcDeploymentDAO(Connection connection)
/*      */   {
/*   71 */     super(connection);
/*      */   }
/*      */ 
/*      */   public CcDeploymentDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public CcDeploymentDAO(DataSource ds)
/*      */   {
/*   87 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected CcDeploymentPK getPK()
/*      */   {
/*   95 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(CcDeploymentEVO details)
/*      */   {
/*  104 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private CcDeploymentEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  134 */     int col = 1;
/*  135 */     CcDeploymentEVO evo = new CcDeploymentEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), resultSet_.getInt(col++), null);
/*      */ 
/*  155 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  156 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  157 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  158 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(CcDeploymentEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  163 */     int col = startCol_;
/*  164 */     stmt_.setInt(col++, evo_.getCcDeploymentId());
/*  165 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(CcDeploymentEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  170 */     int col = startCol_;
/*  171 */     stmt_.setInt(col++, evo_.getModelId());
/*  172 */     stmt_.setString(col++, evo_.getVisId());
/*  173 */     stmt_.setString(col++, evo_.getDescription());
/*  174 */     stmt_.setInt(col++, evo_.getXmlformId());
/*  175 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDimContext0());
/*  176 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDimContext1());
/*  177 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDimContext2());
/*  178 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDimContext3());
/*  179 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDimContext4());
/*  180 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDimContext5());
/*  181 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDimContext6());
/*  182 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDimContext7());
/*  183 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDimContext8());
/*  184 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDimContext9());
/*  185 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  186 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  187 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  188 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  189 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(CcDeploymentPK pk)
/*      */     throws ValidationException
/*      */   {
/*  205 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  207 */     PreparedStatement stmt = null;
/*  208 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  212 */       stmt = getConnection().prepareStatement("select CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT.MODEL_ID,CC_DEPLOYMENT.VIS_ID,CC_DEPLOYMENT.DESCRIPTION,CC_DEPLOYMENT.XMLFORM_ID,CC_DEPLOYMENT.DIM_CONTEXT0,CC_DEPLOYMENT.DIM_CONTEXT1,CC_DEPLOYMENT.DIM_CONTEXT2,CC_DEPLOYMENT.DIM_CONTEXT3,CC_DEPLOYMENT.DIM_CONTEXT4,CC_DEPLOYMENT.DIM_CONTEXT5,CC_DEPLOYMENT.DIM_CONTEXT6,CC_DEPLOYMENT.DIM_CONTEXT7,CC_DEPLOYMENT.DIM_CONTEXT8,CC_DEPLOYMENT.DIM_CONTEXT9,CC_DEPLOYMENT.VERSION_NUM,CC_DEPLOYMENT.UPDATED_BY_USER_ID,CC_DEPLOYMENT.UPDATED_TIME,CC_DEPLOYMENT.CREATED_TIME from CC_DEPLOYMENT where    CC_DEPLOYMENT_ID = ? ");
/*      */ 
/*  215 */       int col = 1;
/*  216 */       stmt.setInt(col++, pk.getCcDeploymentId());
/*      */ 
/*  218 */       resultSet = stmt.executeQuery();
/*      */ 
/*  220 */       if (!resultSet.next()) {
/*  221 */         throw new ValidationException(new StringBuilder().append(getEntityName()).append(" select of ").append(pk).append(" not found").toString());
/*      */       }
/*      */ 
/*  224 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  225 */       if (this.mDetails.isModified())
/*  226 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  230 */       throw handleSQLException(pk, "select CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT.MODEL_ID,CC_DEPLOYMENT.VIS_ID,CC_DEPLOYMENT.DESCRIPTION,CC_DEPLOYMENT.XMLFORM_ID,CC_DEPLOYMENT.DIM_CONTEXT0,CC_DEPLOYMENT.DIM_CONTEXT1,CC_DEPLOYMENT.DIM_CONTEXT2,CC_DEPLOYMENT.DIM_CONTEXT3,CC_DEPLOYMENT.DIM_CONTEXT4,CC_DEPLOYMENT.DIM_CONTEXT5,CC_DEPLOYMENT.DIM_CONTEXT6,CC_DEPLOYMENT.DIM_CONTEXT7,CC_DEPLOYMENT.DIM_CONTEXT8,CC_DEPLOYMENT.DIM_CONTEXT9,CC_DEPLOYMENT.VERSION_NUM,CC_DEPLOYMENT.UPDATED_BY_USER_ID,CC_DEPLOYMENT.UPDATED_TIME,CC_DEPLOYMENT.CREATED_TIME from CC_DEPLOYMENT where    CC_DEPLOYMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  234 */       closeResultSet(resultSet);
/*  235 */       closeStatement(stmt);
/*  236 */       closeConnection();
/*      */ 
/*  238 */       if (timer != null)
/*  239 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  294 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  295 */     this.mDetails.postCreateInit();
/*      */ 
/*  297 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  302 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  303 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  304 */       stmt = getConnection().prepareStatement("insert into CC_DEPLOYMENT ( CC_DEPLOYMENT_ID,MODEL_ID,VIS_ID,DESCRIPTION,XMLFORM_ID,DIM_CONTEXT0,DIM_CONTEXT1,DIM_CONTEXT2,DIM_CONTEXT3,DIM_CONTEXT4,DIM_CONTEXT5,DIM_CONTEXT6,DIM_CONTEXT7,DIM_CONTEXT8,DIM_CONTEXT9,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  307 */       int col = 1;
/*  308 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  309 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  312 */       int resultCount = stmt.executeUpdate();
/*  313 */       if (resultCount != 1)
/*      */       {
/*  315 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" insert failed (").append(this.mDetails.getPK()).append("): resultCount=").append(resultCount).toString());
/*      */       }
/*      */ 
/*  318 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  322 */       throw handleSQLException(this.mDetails.getPK(), "insert into CC_DEPLOYMENT ( CC_DEPLOYMENT_ID,MODEL_ID,VIS_ID,DESCRIPTION,XMLFORM_ID,DIM_CONTEXT0,DIM_CONTEXT1,DIM_CONTEXT2,DIM_CONTEXT3,DIM_CONTEXT4,DIM_CONTEXT5,DIM_CONTEXT6,DIM_CONTEXT7,DIM_CONTEXT8,DIM_CONTEXT9,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  326 */       closeStatement(stmt);
/*  327 */       closeConnection();
/*      */ 
/*  329 */       if (timer != null) {
/*  330 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  336 */       getCcDeploymentLineDAO().update(this.mDetails.getCCDeploymentLinesMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  342 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  380 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  384 */     PreparedStatement stmt = null;
/*      */ 
/*  386 */     boolean mainChanged = this.mDetails.isModified();
/*  387 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  391 */       if (getCcDeploymentLineDAO().update(this.mDetails.getCCDeploymentLinesMap())) {
/*  392 */         dependantChanged = true;
/*      */       }
/*  394 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  397 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  400 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  401 */         stmt = getConnection().prepareStatement("update CC_DEPLOYMENT set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,XMLFORM_ID = ?,DIM_CONTEXT0 = ?,DIM_CONTEXT1 = ?,DIM_CONTEXT2 = ?,DIM_CONTEXT3 = ?,DIM_CONTEXT4 = ?,DIM_CONTEXT5 = ?,DIM_CONTEXT6 = ?,DIM_CONTEXT7 = ?,DIM_CONTEXT8 = ?,DIM_CONTEXT9 = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CC_DEPLOYMENT_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  404 */         int col = 1;
/*  405 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  406 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  408 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  411 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  413 */         if (resultCount == 0) {
/*  414 */           checkVersionNum();
/*      */         }
/*  416 */         if (resultCount != 1) {
/*  417 */           throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" update failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
/*      */         }
/*      */ 
/*  420 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  429 */       throw handleSQLException(getPK(), "update CC_DEPLOYMENT set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,XMLFORM_ID = ?,DIM_CONTEXT0 = ?,DIM_CONTEXT1 = ?,DIM_CONTEXT2 = ?,DIM_CONTEXT3 = ?,DIM_CONTEXT4 = ?,DIM_CONTEXT5 = ?,DIM_CONTEXT6 = ?,DIM_CONTEXT7 = ?,DIM_CONTEXT8 = ?,DIM_CONTEXT9 = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    CC_DEPLOYMENT_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  433 */       closeStatement(stmt);
/*  434 */       closeConnection();
/*      */ 
/*  436 */       if ((timer != null) && (
/*  437 */         (mainChanged) || (dependantChanged)))
/*  438 */         timer.logDebug("store", new StringBuilder().append(this.mDetails.getPK()).append("(").append(mainChanged).append(",").append(dependantChanged).append(")").toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  450 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  451 */     PreparedStatement stmt = null;
/*  452 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  456 */       stmt = getConnection().prepareStatement("select VERSION_NUM from CC_DEPLOYMENT where CC_DEPLOYMENT_ID = ?");
/*      */ 
/*  459 */       int col = 1;
/*  460 */       stmt.setInt(col++, this.mDetails.getCcDeploymentId());
/*      */ 
/*  463 */       resultSet = stmt.executeQuery();
/*      */ 
/*  465 */       if (!resultSet.next()) {
/*  466 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" checkVersionNum: select of ").append(getPK()).append(" not found").toString());
/*      */       }
/*      */ 
/*  469 */       col = 1;
/*  470 */       int dbVersionNumber = resultSet.getInt(col++);
/*  471 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  472 */         throw new VersionValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" expected:").append(this.mDetails.getVersionNum() - 1).append(" found:").append(dbVersionNumber).toString());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  478 */       throw handleSQLException(getPK(), "select VERSION_NUM from CC_DEPLOYMENT where CC_DEPLOYMENT_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  482 */       closeStatement(stmt);
/*  483 */       closeResultSet(resultSet);
/*      */ 
/*  485 */       if (timer != null)
/*  486 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllCcDeploymentsELO getAllCcDeployments()
/*      */   {
/*  523 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  524 */     PreparedStatement stmt = null;
/*  525 */     ResultSet resultSet = null;
/*  526 */     AllCcDeploymentsELO results = new AllCcDeploymentsELO();
/*      */     try
/*      */     {
/*  529 */       stmt = getConnection().prepareStatement(SQL_ALL_CC_DEPLOYMENTS);
/*  530 */       int col = 1;
/*  531 */       resultSet = stmt.executeQuery();
/*  532 */       while (resultSet.next())
/*      */       {
/*  534 */         col = 2;
/*      */ 
/*  537 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  540 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  543 */         CcDeploymentPK pkCcDeployment = new CcDeploymentPK(resultSet.getInt(col++));
/*      */ 
/*  546 */         String textCcDeployment = resultSet.getString(col++);
/*      */ 
/*  551 */         CcDeploymentCK ckCcDeployment = new CcDeploymentCK(pkModel, pkCcDeployment);
/*      */ 
/*  557 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  563 */         CcDeploymentRefImpl erCcDeployment = new CcDeploymentRefImpl(ckCcDeployment, textCcDeployment);
/*      */ 
/*  568 */         String col1 = resultSet.getString(col++);
/*  569 */         int col2 = resultSet.getInt(col++);
/*      */ 
/*  572 */         results.add(erCcDeployment, erModel, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  582 */       throw handleSQLException(SQL_ALL_CC_DEPLOYMENTS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  586 */       closeResultSet(resultSet);
/*  587 */       closeStatement(stmt);
/*  588 */       closeConnection();
/*      */     }
/*      */ 
/*  591 */     if (timer != null) {
/*  592 */       timer.logDebug("getAllCcDeployments", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  596 */     return results;
/*      */   }
/*      */ 
/*      */   public CcDeploymentsForLookupTableELO getCcDeploymentsForLookupTable(String param1)
/*      */   {
/*  638 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  639 */     PreparedStatement stmt = null;
/*  640 */     ResultSet resultSet = null;
/*  641 */     CcDeploymentsForLookupTableELO results = new CcDeploymentsForLookupTableELO();
/*      */     try
/*      */     {
/*  644 */       stmt = getConnection().prepareStatement(SQL_CC_DEPLOYMENTS_FOR_LOOKUP_TABLE);
/*  645 */       int col = 1;
/*  646 */       stmt.setString(col++, param1);
/*  647 */       resultSet = stmt.executeQuery();
/*  648 */       while (resultSet.next())
/*      */       {
/*  650 */         col = 2;
/*      */ 
/*  653 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  656 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  659 */         CcDeploymentPK pkCcDeployment = new CcDeploymentPK(resultSet.getInt(col++));
/*      */ 
/*  662 */         String textCcDeployment = resultSet.getString(col++);
/*      */ 
/*  665 */         XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
/*      */ 
/*  668 */         String textXmlForm = resultSet.getString(col++);
/*      */ 
/*  672 */         CcDeploymentCK ckCcDeployment = new CcDeploymentCK(pkModel, pkCcDeployment);
/*      */ 
/*  678 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  684 */         CcDeploymentRefImpl erCcDeployment = new CcDeploymentRefImpl(ckCcDeployment, textCcDeployment);
/*      */ 
/*  690 */         XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
/*      */ 
/*  695 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  698 */         results.add(erCcDeployment, erModel, erXmlForm, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  708 */       throw handleSQLException(SQL_CC_DEPLOYMENTS_FOR_LOOKUP_TABLE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  712 */       closeResultSet(resultSet);
/*  713 */       closeStatement(stmt);
/*  714 */       closeConnection();
/*      */     }
/*      */ 
/*  717 */     if (timer != null) {
/*  718 */       timer.logDebug("getCcDeploymentsForLookupTable", new StringBuilder().append(" Description=").append(param1).append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  723 */     return results;
/*      */   }
/*      */ 
/*      */   public CcDeploymentsForXmlFormELO getCcDeploymentsForXmlForm(int param1)
/*      */   {
/*  761 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  762 */     PreparedStatement stmt = null;
/*  763 */     ResultSet resultSet = null;
/*  764 */     CcDeploymentsForXmlFormELO results = new CcDeploymentsForXmlFormELO();
/*      */     try
/*      */     {
/*  767 */       stmt = getConnection().prepareStatement(SQL_CC_DEPLOYMENTS_FOR_XML_FORM);
/*  768 */       int col = 1;
/*  769 */       stmt.setInt(col++, param1);
/*  770 */       resultSet = stmt.executeQuery();
/*  771 */       while (resultSet.next())
/*      */       {
/*  773 */         col = 2;
/*      */ 
/*  776 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  779 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  782 */         CcDeploymentPK pkCcDeployment = new CcDeploymentPK(resultSet.getInt(col++));
/*      */ 
/*  785 */         String textCcDeployment = resultSet.getString(col++);
/*      */ 
/*  790 */         CcDeploymentCK ckCcDeployment = new CcDeploymentCK(pkModel, pkCcDeployment);
/*      */ 
/*  796 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  802 */         CcDeploymentRefImpl erCcDeployment = new CcDeploymentRefImpl(ckCcDeployment, textCcDeployment);
/*      */ 
/*  807 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  810 */         results.add(erCcDeployment, erModel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  819 */       throw handleSQLException(SQL_CC_DEPLOYMENTS_FOR_XML_FORM, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  823 */       closeResultSet(resultSet);
/*  824 */       closeStatement(stmt);
/*  825 */       closeConnection();
/*      */     }
/*      */ 
/*  828 */     if (timer != null) {
/*  829 */       timer.logDebug("getCcDeploymentsForXmlForm", new StringBuilder().append(" XmlformId=").append(param1).append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  834 */     return results;
/*      */   }
/*      */ 
/*      */   public CcDeploymentsForModelELO getCcDeploymentsForModel(int param1)
/*      */   {
/*  877 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  878 */     PreparedStatement stmt = null;
/*  879 */     ResultSet resultSet = null;
/*  880 */     CcDeploymentsForModelELO results = new CcDeploymentsForModelELO();
/*      */     try
/*      */     {
/*  883 */       stmt = getConnection().prepareStatement(SQL_CC_DEPLOYMENTS_FOR_MODEL);
/*  884 */       int col = 1;
/*  885 */       stmt.setInt(col++, param1);
/*  886 */       resultSet = stmt.executeQuery();
/*  887 */       while (resultSet.next())
/*      */       {
/*  889 */         col = 2;
/*      */ 
/*  892 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  895 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  898 */         CcDeploymentPK pkCcDeployment = new CcDeploymentPK(resultSet.getInt(col++));
/*      */ 
/*  901 */         String textCcDeployment = resultSet.getString(col++);
/*      */ 
/*  904 */         XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
/*      */ 
/*  907 */         String textXmlForm = resultSet.getString(col++);
/*      */ 
/*  911 */         CcDeploymentCK ckCcDeployment = new CcDeploymentCK(pkModel, pkCcDeployment);
/*      */ 
/*  917 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  923 */         CcDeploymentRefImpl erCcDeployment = new CcDeploymentRefImpl(ckCcDeployment, textCcDeployment);
/*      */ 
/*  929 */         XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
/*      */ 
/*  934 */         String col1 = resultSet.getString(col++);
/*  935 */         int col2 = resultSet.getInt(col++);
/*      */ 
/*  938 */         results.add(erCcDeployment, erModel, erXmlForm, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  949 */       throw handleSQLException(SQL_CC_DEPLOYMENTS_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  953 */       closeResultSet(resultSet);
/*  954 */       closeStatement(stmt);
/*  955 */       closeConnection();
/*      */     }
/*      */ 
/*  958 */     if (timer != null) {
/*  959 */       timer.logDebug("getCcDeploymentsForModel", new StringBuilder().append(" ModelId=").append(param1).append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  964 */     return results;
/*      */   }
/*      */ 
/*      */   public CcDeploymentCellPickerInfoELO getCcDeploymentCellPickerInfo(int param1)
/*      */   {
/* 1001 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1002 */     PreparedStatement stmt = null;
/* 1003 */     ResultSet resultSet = null;
/* 1004 */     CcDeploymentCellPickerInfoELO results = new CcDeploymentCellPickerInfoELO();
/*      */     try
/*      */     {
/* 1007 */       stmt = getConnection().prepareStatement(SQL_CC_DEPLOYMENT_CELL_PICKER_INFO);
/* 1008 */       int col = 1;
/* 1009 */       stmt.setInt(col++, param1);
/* 1010 */       resultSet = stmt.executeQuery();
/* 1011 */       while (resultSet.next())
/*      */       {
/* 1013 */         col = 2;
/*      */ 
/* 1016 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1019 */         String textModel = resultSet.getString(col++);
/*      */ 
/* 1022 */         CcDeploymentPK pkCcDeployment = new CcDeploymentPK(resultSet.getInt(col++));
/*      */ 
/* 1025 */         String textCcDeployment = resultSet.getString(col++);
/*      */ 
/* 1030 */         CcDeploymentCK ckCcDeployment = new CcDeploymentCK(pkModel, pkCcDeployment);
/*      */ 
/* 1036 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/* 1042 */         CcDeploymentRefImpl erCcDeployment = new CcDeploymentRefImpl(ckCcDeployment, textCcDeployment);
/*      */ 
/* 1047 */         int col1 = resultSet.getInt(col++);
/*      */ 
/* 1050 */         results.add(erCcDeployment, erModel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1059 */       throw handleSQLException(SQL_CC_DEPLOYMENT_CELL_PICKER_INFO, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1063 */       closeResultSet(resultSet);
/* 1064 */       closeStatement(stmt);
/* 1065 */       closeConnection();
/*      */     }
/*      */ 
/* 1068 */     if (timer != null) {
/* 1069 */       timer.logDebug("getCcDeploymentCellPickerInfo", new StringBuilder().append(" CcDeploymentId=").append(param1).append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/* 1074 */     return results;
/*      */   }
/*      */ 
/*      */   public CcDeploymentXMLFormTypeELO getCcDeploymentXMLFormType(int param1)
/*      */   {
/* 1117 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1118 */     PreparedStatement stmt = null;
/* 1119 */     ResultSet resultSet = null;
/* 1120 */     CcDeploymentXMLFormTypeELO results = new CcDeploymentXMLFormTypeELO();
/*      */     try
/*      */     {
/* 1123 */       stmt = getConnection().prepareStatement(SQL_CC_DEPLOYMENT_X_M_L_FORM_TYPE);
/* 1124 */       int col = 1;
/* 1125 */       stmt.setInt(col++, param1);
/* 1126 */       resultSet = stmt.executeQuery();
/* 1127 */       while (resultSet.next())
/*      */       {
/* 1129 */         col = 2;
/*      */ 
/* 1132 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1135 */         String textModel = resultSet.getString(col++);
/*      */ 
/* 1138 */         CcDeploymentPK pkCcDeployment = new CcDeploymentPK(resultSet.getInt(col++));
/*      */ 
/* 1141 */         String textCcDeployment = resultSet.getString(col++);
/*      */ 
/* 1144 */         XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
/*      */ 
/* 1147 */         String textXmlForm = resultSet.getString(col++);
/*      */ 
/* 1151 */         CcDeploymentCK ckCcDeployment = new CcDeploymentCK(pkModel, pkCcDeployment);
/*      */ 
/* 1157 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/* 1163 */         CcDeploymentRefImpl erCcDeployment = new CcDeploymentRefImpl(ckCcDeployment, textCcDeployment);
/*      */ 
/* 1169 */         XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
/*      */ 
/* 1174 */         int col1 = resultSet.getInt(col++);
/* 1175 */         int col2 = resultSet.getInt(col++);
/* 1176 */         int col3 = resultSet.getInt(col++);
/*      */ 
/* 1179 */         results.add(erCcDeployment, erModel, erXmlForm, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1191 */       throw handleSQLException(SQL_CC_DEPLOYMENT_X_M_L_FORM_TYPE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1195 */       closeResultSet(resultSet);
/* 1196 */       closeStatement(stmt);
/* 1197 */       closeConnection();
/*      */     }
/*      */ 
/* 1200 */     if (timer != null) {
/* 1201 */       timer.logDebug("getCcDeploymentXMLFormType", new StringBuilder().append(" CcDeploymentId=").append(param1).append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/* 1206 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/* 1223 */     if (items == null) {
/* 1224 */       return false;
/*      */     }
/* 1226 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1227 */     PreparedStatement deleteStmt = null;
/*      */ 
/* 1229 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/* 1233 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/* 1234 */       while (iter3.hasNext())
/*      */       {
/* 1236 */         this.mDetails = ((CcDeploymentEVO)iter3.next());
/* 1237 */         if (!this.mDetails.deletePending())
/*      */           continue;
/* 1239 */         somethingChanged = true;
/*      */ 
/* 1242 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/* 1246 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 1247 */       while (iter2.hasNext())
/*      */       {
/* 1249 */         this.mDetails = ((CcDeploymentEVO)iter2.next());
/*      */ 
/* 1252 */         if (!this.mDetails.deletePending())
/*      */           continue;
/* 1254 */         somethingChanged = true;
/*      */ 
/* 1257 */         if (deleteStmt == null) {
/* 1258 */           deleteStmt = getConnection().prepareStatement("delete from CC_DEPLOYMENT where    CC_DEPLOYMENT_ID = ? ");
/*      */         }
/*      */ 
/* 1261 */         int col = 1;
/* 1262 */         deleteStmt.setInt(col++, this.mDetails.getCcDeploymentId());
/*      */ 
/* 1264 */         if (this._log.isDebugEnabled()) {
/* 1265 */           this._log.debug("update", new StringBuilder().append("CcDeployment deleting CcDeploymentId=").append(this.mDetails.getCcDeploymentId()).toString());
/*      */         }
/*      */ 
/* 1270 */         deleteStmt.addBatch();
/*      */ 
/* 1273 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/* 1278 */       if (deleteStmt != null)
/*      */       {
/* 1280 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1282 */         deleteStmt.executeBatch();
/*      */ 
/* 1284 */         if (timer2 != null) {
/* 1285 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/* 1289 */       Iterator iter1 = items.values().iterator();
/* 1290 */       while (iter1.hasNext())
/*      */       {
/* 1292 */         this.mDetails = ((CcDeploymentEVO)iter1.next());
/*      */ 
/* 1294 */         if (this.mDetails.insertPending())
/*      */         {
/* 1296 */           somethingChanged = true;
/* 1297 */           doCreate(); continue;
/*      */         }
/*      */ 
/* 1300 */         if (this.mDetails.isModified())
/*      */         {
/* 1302 */           somethingChanged = true;
/* 1303 */           doStore(); continue;
/*      */         }
/*      */ 
/* 1307 */         if ((this.mDetails.deletePending()) || 
/* 1313 */           (!getCcDeploymentLineDAO().update(this.mDetails.getCCDeploymentLinesMap()))) continue;
/* 1314 */         somethingChanged = true;
/*      */       }
/*      */ 
/* 1326 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1330 */       throw handleSQLException("delete from CC_DEPLOYMENT where    CC_DEPLOYMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1334 */       if (deleteStmt != null)
/*      */       {
/* 1336 */         closeStatement(deleteStmt);
/* 1337 */         closeConnection();
/*      */       }
/*      */ 
/* 1340 */       this.mDetails = null;
/*      */ 
/* 1342 */       if ((somethingChanged) && 
/* 1343 */         (timer != null))
/* 1344 */         timer.logDebug("update", "collection"); 
/* 1344 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(CcDeploymentPK pk)
/*      */   {
/* 1416 */     Set emptyStrings = Collections.emptySet();
/* 1417 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(CcDeploymentPK pk, Set<String> exclusionTables)
/*      */   {
/* 1423 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1425 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1427 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1429 */       PreparedStatement stmt = null;
/*      */ 
/* 1431 */       int resultCount = 0;
/* 1432 */       String s = null;
/*      */       try
/*      */       {
/* 1435 */         s = new StringBuilder().append(SQL_DELETE_CHILDRENS_DEPENDANTS[i][1]).append(SQL_DELETE_DEPENDANT_CRITERIA).toString();
/*      */ 
/* 1437 */         if (this._log.isDebugEnabled()) {
/* 1438 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1440 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1443 */         int col = 1;
/* 1444 */         stmt.setInt(col++, pk.getCcDeploymentId());
/*      */ 
/* 1447 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1451 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1455 */         closeStatement(stmt);
/* 1456 */         closeConnection();
/*      */ 
/* 1458 */         if (timer != null) {
/* 1459 */           timer.logDebug("deleteDependants", new StringBuilder().append("A[").append(i).append("] count=").append(resultCount).toString());
/*      */         }
/*      */       }
/*      */     }
/* 1463 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1465 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1467 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1469 */       PreparedStatement stmt = null;
/*      */ 
/* 1471 */       int resultCount = 0;
/* 1472 */       String s = null;
/*      */       try
/*      */       {
/* 1475 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1477 */         if (this._log.isDebugEnabled()) {
/* 1478 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1480 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1483 */         int col = 1;
/* 1484 */         stmt.setInt(col++, pk.getCcDeploymentId());
/*      */ 
/* 1487 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1491 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1495 */         closeStatement(stmt);
/* 1496 */         closeConnection();
/*      */ 
/* 1498 */         if (timer != null)
/* 1499 */           timer.logDebug("deleteDependants", new StringBuilder().append("B[").append(i).append("] count=").append(resultCount).toString());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants)
/*      */   {
/* 1519 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1521 */     PreparedStatement stmt = null;
/* 1522 */     ResultSet resultSet = null;
/*      */ 
/* 1524 */     int itemCount = 0;
/*      */ 
/* 1526 */     Collection theseItems = new ArrayList();
/* 1527 */     owningEVO.setCellCalcDeployments(theseItems);
/* 1528 */     owningEVO.setCellCalcDeploymentsAllItemsLoaded(true);
/*      */     try
/*      */     {
/* 1532 */       stmt = getConnection().prepareStatement("select CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT.MODEL_ID,CC_DEPLOYMENT.VIS_ID,CC_DEPLOYMENT.DESCRIPTION,CC_DEPLOYMENT.XMLFORM_ID,CC_DEPLOYMENT.DIM_CONTEXT0,CC_DEPLOYMENT.DIM_CONTEXT1,CC_DEPLOYMENT.DIM_CONTEXT2,CC_DEPLOYMENT.DIM_CONTEXT3,CC_DEPLOYMENT.DIM_CONTEXT4,CC_DEPLOYMENT.DIM_CONTEXT5,CC_DEPLOYMENT.DIM_CONTEXT6,CC_DEPLOYMENT.DIM_CONTEXT7,CC_DEPLOYMENT.DIM_CONTEXT8,CC_DEPLOYMENT.DIM_CONTEXT9,CC_DEPLOYMENT.VERSION_NUM,CC_DEPLOYMENT.UPDATED_BY_USER_ID,CC_DEPLOYMENT.UPDATED_TIME,CC_DEPLOYMENT.CREATED_TIME from CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_DEPLOYMENT.CC_DEPLOYMENT_ID");
/*      */ 
/* 1534 */       int col = 1;
/* 1535 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/* 1537 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1540 */       while (resultSet.next())
/*      */       {
/* 1542 */         itemCount++;
/* 1543 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1545 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/* 1548 */       if (timer != null) {
/* 1549 */         timer.logDebug("bulkGetAll", new StringBuilder().append("items=").append(itemCount).toString());
/*      */       }
/*      */ 
/* 1552 */       if ((itemCount > 0) && (dependants.indexOf("<42>") > -1))
/*      */       {
/* 1554 */         getCcDeploymentLineDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/* 1558 */       throw handleSQLException("select CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT.MODEL_ID,CC_DEPLOYMENT.VIS_ID,CC_DEPLOYMENT.DESCRIPTION,CC_DEPLOYMENT.XMLFORM_ID,CC_DEPLOYMENT.DIM_CONTEXT0,CC_DEPLOYMENT.DIM_CONTEXT1,CC_DEPLOYMENT.DIM_CONTEXT2,CC_DEPLOYMENT.DIM_CONTEXT3,CC_DEPLOYMENT.DIM_CONTEXT4,CC_DEPLOYMENT.DIM_CONTEXT5,CC_DEPLOYMENT.DIM_CONTEXT6,CC_DEPLOYMENT.DIM_CONTEXT7,CC_DEPLOYMENT.DIM_CONTEXT8,CC_DEPLOYMENT.DIM_CONTEXT9,CC_DEPLOYMENT.VERSION_NUM,CC_DEPLOYMENT.UPDATED_BY_USER_ID,CC_DEPLOYMENT.UPDATED_TIME,CC_DEPLOYMENT.CREATED_TIME from CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_DEPLOYMENT.CC_DEPLOYMENT_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1562 */       closeResultSet(resultSet);
/* 1563 */       closeStatement(stmt);
/* 1564 */       closeConnection();
/*      */ 
/* 1566 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectModelId, String dependants, Collection currentList)
/*      */   {
/* 1591 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1592 */     PreparedStatement stmt = null;
/* 1593 */     ResultSet resultSet = null;
/*      */ 
/* 1595 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/* 1599 */       stmt = getConnection().prepareStatement("select CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT.MODEL_ID,CC_DEPLOYMENT.VIS_ID,CC_DEPLOYMENT.DESCRIPTION,CC_DEPLOYMENT.XMLFORM_ID,CC_DEPLOYMENT.DIM_CONTEXT0,CC_DEPLOYMENT.DIM_CONTEXT1,CC_DEPLOYMENT.DIM_CONTEXT2,CC_DEPLOYMENT.DIM_CONTEXT3,CC_DEPLOYMENT.DIM_CONTEXT4,CC_DEPLOYMENT.DIM_CONTEXT5,CC_DEPLOYMENT.DIM_CONTEXT6,CC_DEPLOYMENT.DIM_CONTEXT7,CC_DEPLOYMENT.DIM_CONTEXT8,CC_DEPLOYMENT.DIM_CONTEXT9,CC_DEPLOYMENT.VERSION_NUM,CC_DEPLOYMENT.UPDATED_BY_USER_ID,CC_DEPLOYMENT.UPDATED_TIME,CC_DEPLOYMENT.CREATED_TIME from CC_DEPLOYMENT where    MODEL_ID = ? ");
/*      */ 
/* 1601 */       int col = 1;
/* 1602 */       stmt.setInt(col++, selectModelId);
/*      */ 
/* 1604 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1606 */       while (resultSet.next())
/*      */       {
/* 1608 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1611 */         getDependants(this.mDetails, dependants);
/*      */ 
/* 1614 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1617 */       if (currentList != null)
/*      */       {
/* 1620 */         ListIterator iter = items.listIterator();
/* 1621 */         CcDeploymentEVO currentEVO = null;
/* 1622 */         CcDeploymentEVO newEVO = null;
/* 1623 */         while (iter.hasNext())
/*      */         {
/* 1625 */           newEVO = (CcDeploymentEVO)iter.next();
/* 1626 */           Iterator iter2 = currentList.iterator();
/* 1627 */           while (iter2.hasNext())
/*      */           {
/* 1629 */             currentEVO = (CcDeploymentEVO)iter2.next();
/* 1630 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1632 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1638 */         Iterator iter2 = currentList.iterator();
/* 1639 */         while (iter2.hasNext())
/*      */         {
/* 1641 */           currentEVO = (CcDeploymentEVO)iter2.next();
/* 1642 */           if (currentEVO.insertPending()) {
/* 1643 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1647 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1651 */       throw handleSQLException("select CC_DEPLOYMENT.CC_DEPLOYMENT_ID,CC_DEPLOYMENT.MODEL_ID,CC_DEPLOYMENT.VIS_ID,CC_DEPLOYMENT.DESCRIPTION,CC_DEPLOYMENT.XMLFORM_ID,CC_DEPLOYMENT.DIM_CONTEXT0,CC_DEPLOYMENT.DIM_CONTEXT1,CC_DEPLOYMENT.DIM_CONTEXT2,CC_DEPLOYMENT.DIM_CONTEXT3,CC_DEPLOYMENT.DIM_CONTEXT4,CC_DEPLOYMENT.DIM_CONTEXT5,CC_DEPLOYMENT.DIM_CONTEXT6,CC_DEPLOYMENT.DIM_CONTEXT7,CC_DEPLOYMENT.DIM_CONTEXT8,CC_DEPLOYMENT.DIM_CONTEXT9,CC_DEPLOYMENT.VERSION_NUM,CC_DEPLOYMENT.UPDATED_BY_USER_ID,CC_DEPLOYMENT.UPDATED_TIME,CC_DEPLOYMENT.CREATED_TIME from CC_DEPLOYMENT where    MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1655 */       closeResultSet(resultSet);
/* 1656 */       closeStatement(stmt);
/* 1657 */       closeConnection();
/*      */ 
/* 1659 */       if (timer != null) {
/* 1660 */         timer.logDebug("getAll", new StringBuilder().append(" ModelId=").append(selectModelId).append(" items=").append(items.size()).toString());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1665 */     return items;
/*      */   }
/*      */ 
/*      */   public CcDeploymentEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1690 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1693 */     if (this.mDetails == null) {
/* 1694 */       doLoad(((CcDeploymentCK)paramCK).getCcDeploymentPK());
/*      */     }
/* 1696 */     else if (!this.mDetails.getPK().equals(((CcDeploymentCK)paramCK).getCcDeploymentPK())) {
/* 1697 */       doLoad(((CcDeploymentCK)paramCK).getCcDeploymentPK());
/*      */     }
/*      */ 
/* 1700 */     if ((dependants.indexOf("<42>") > -1) && (!this.mDetails.isCCDeploymentLinesAllItemsLoaded()))
/*      */     {
/* 1705 */       this.mDetails.setCCDeploymentLines(getCcDeploymentLineDAO().getAll(this.mDetails.getCcDeploymentId(), dependants, this.mDetails.getCCDeploymentLines()));
/*      */ 
/* 1712 */       this.mDetails.setCCDeploymentLinesAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1715 */     if ((paramCK instanceof CcDeploymentLineCK))
/*      */     {
/* 1717 */       if (this.mDetails.getCCDeploymentLines() == null) {
/* 1718 */         this.mDetails.loadCCDeploymentLinesItem(getCcDeploymentLineDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1721 */         CcDeploymentLinePK pk = ((CcDeploymentLineCK)paramCK).getCcDeploymentLinePK();
/* 1722 */         CcDeploymentLineEVO evo = this.mDetails.getCCDeploymentLinesItem(pk);
/* 1723 */         if (evo == null)
/* 1724 */           this.mDetails.loadCCDeploymentLinesItem(getCcDeploymentLineDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1726 */           getCcDeploymentLineDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1731 */     CcDeploymentEVO details = new CcDeploymentEVO();
/* 1732 */     details = this.mDetails.deepClone();
/*      */ 
/* 1734 */     if (timer != null) {
/* 1735 */       timer.logDebug("getDetails", new StringBuilder().append(paramCK).append(" ").append(dependants).toString());
/*      */     }
/* 1737 */     return details;
/*      */   }
/*      */ 
/*      */   public CcDeploymentEVO getDetails(ModelCK paramCK, CcDeploymentEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1743 */     CcDeploymentEVO savedEVO = this.mDetails;
/* 1744 */     this.mDetails = paramEVO;
/* 1745 */     CcDeploymentEVO newEVO = getDetails(paramCK, dependants);
/* 1746 */     this.mDetails = savedEVO;
/* 1747 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public CcDeploymentEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1753 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1757 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1760 */     CcDeploymentEVO details = this.mDetails.deepClone();
/*      */ 
/* 1762 */     if (timer != null) {
/* 1763 */       timer.logDebug("getDetails", new StringBuilder().append(this.mDetails.getPK()).append(" ").append(dependants).toString());
/*      */     }
/* 1765 */     return details;
/*      */   }
/*      */ 
/*      */   protected CcDeploymentLineDAO getCcDeploymentLineDAO()
/*      */   {
/* 1774 */     if (this.mCcDeploymentLineDAO == null)
/*      */     {
/* 1776 */       if (this.mDataSource != null)
/* 1777 */         this.mCcDeploymentLineDAO = new CcDeploymentLineDAO(this.mDataSource);
/*      */       else {
/* 1779 */         this.mCcDeploymentLineDAO = new CcDeploymentLineDAO(getConnection());
/*      */       }
/*      */     }
/* 1782 */     return this.mCcDeploymentLineDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1787 */     return "CcDeployment";
/*      */   }
/*      */ 
/*      */   public CcDeploymentRefImpl getRef(CcDeploymentPK paramCcDeploymentPK)
/*      */   {
/* 1792 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1793 */     PreparedStatement stmt = null;
/* 1794 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1797 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,CC_DEPLOYMENT.VIS_ID from CC_DEPLOYMENT,MODEL where 1=1 and CC_DEPLOYMENT.CC_DEPLOYMENT_ID = ? and CC_DEPLOYMENT.MODEL_ID = MODEL.MODEL_ID");
/* 1798 */       int col = 1;
/* 1799 */       stmt.setInt(col++, paramCcDeploymentPK.getCcDeploymentId());
/*      */ 
/* 1801 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1803 */       if (!resultSet.next()) {
/* 1804 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getRef ").append(paramCcDeploymentPK).append(" not found").toString());
/*      */       }
/* 1806 */       col = 2;
/* 1807 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1811 */       String textCcDeployment = resultSet.getString(col++);
/* 1812 */       CcDeploymentCK ckCcDeployment = new CcDeploymentCK(newModelPK, paramCcDeploymentPK);
/*      */ 
/* 1817 */       CcDeploymentRefImpl localCcDeploymentRefImpl = new CcDeploymentRefImpl(ckCcDeployment, textCcDeployment);
/*      */       return localCcDeploymentRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1822 */       throw handleSQLException(paramCcDeploymentPK, "select 0,MODEL.MODEL_ID,CC_DEPLOYMENT.VIS_ID from CC_DEPLOYMENT,MODEL where 1=1 and CC_DEPLOYMENT.CC_DEPLOYMENT_ID = ? and CC_DEPLOYMENT.MODEL_ID = MODEL.MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1826 */       closeResultSet(resultSet);
/* 1827 */       closeStatement(stmt);
/* 1828 */       closeConnection();
/*      */ 
/* 1830 */       if (timer != null)
/* 1831 */         timer.logDebug("getRef", paramCcDeploymentPK); 
/* 1831 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1843 */     if (c == null)
/* 1844 */       return;
/* 1845 */     Iterator iter = c.iterator();
/* 1846 */     while (iter.hasNext())
/*      */     {
/* 1848 */       CcDeploymentEVO evo = (CcDeploymentEVO)iter.next();
/* 1849 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(CcDeploymentEVO evo, String dependants)
/*      */   {
/* 1863 */     if (evo.insertPending()) {
/* 1864 */       return;
/*      */     }
/* 1866 */     if (evo.getCcDeploymentId() < 1) {
/* 1867 */       return;
/*      */     }
/*      */ 
/* 1871 */     if ((dependants.indexOf("<42>") > -1) || (dependants.indexOf("<43>") > -1) || (dependants.indexOf("<44>") > -1) || (dependants.indexOf("<45>") > -1) || (dependants.indexOf("<46>") > -1))
/*      */     {
/* 1878 */       if (!evo.isCCDeploymentLinesAllItemsLoaded())
/*      */       {
/* 1880 */         evo.setCCDeploymentLines(getCcDeploymentLineDAO().getAll(evo.getCcDeploymentId(), dependants, evo.getCCDeploymentLines()));
/*      */ 
/* 1887 */         evo.setCCDeploymentLinesAllItemsLoaded(true);
/*      */       }
/* 1889 */       getCcDeploymentLineDAO().getDependants(evo.getCCDeploymentLines(), dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<RuntimeCcDeploymentTarget>[] getTargetCellCalculations(int modelId, int[] selections, int[] checkElements, int checkIndex, int[] relevantCellCalcs)
/*      */   {
/* 1910 */     Timer t = new Timer(this._log);
/* 1911 */     List[] results = new List[checkElements.length];
/*      */ 
/* 1913 */     if ((relevantCellCalcs.length == 0) || (checkElements.length == 0)) {
/* 1914 */       return results;
/*      */     }
/* 1916 */     SqlBuilder template = new SqlBuilder(new String[] { "-- Cell Calculation Query II", "-- Determine the cell calc id for each cell address", "with fixed_address as -- Fixed address input params", "(", "  select ${fixedParamSelect}", "  from dual", "),", "repeating_address as -- Repeating address input params", "(", "  ${repeatingAddressSelect}", "),", "applicable_ccs as -- Cell calcs to consider input param", "(", "  ${applicableCCSSelect}", "),", "cell_addresses as -- Combined input params", "(", "  select ${allDimSelect}", "  from fixed_address fa,", "       repeating_address ra", "),", "origin_se as -- Join input params with structure_element", "(", "  select distinct", "         ${seOriginColumns}", "  from cell_addresses ca, ", "       ${seOriginTables}", "  where ${seOriginWherePredicate}", "),", "deployments as", "(", "  select *", "  from rt_cube_deployment", "  join rt_cube_deployment_entry using( rt_cube_deployment_id )", "  join rt_cube_deployment_dt using( rt_cube_deployment_id, owner_line_id )", "  join applicable_ccs ccs on ( owner_id = ccs.cc_deployment_id )", "  where model_id = <modelId>", ")", "select ", "       distinct ose.se${checkIndex}, deps0.owner_id as cc_deployment_id, deps0.owner_line_id as cc_deployment_line_id ", "from origin_se ose", "     ${deploymentJoins}" });
/*      */ 
/* 1962 */     StringBuffer fixedParamSelect = new StringBuffer();
/* 1963 */     for (int i = 0; i < selections.length; i++)
/*      */     {
/* 1965 */       if (checkIndex == i)
/*      */         continue;
/* 1967 */       if (fixedParamSelect.toString().length() > 0)
/* 1968 */         fixedParamSelect.append(',');
/* 1969 */       fixedParamSelect.append(new StringBuilder().append("<fixedDim.").append(i).append("> as dim").append(i).toString());
/*      */     }
/* 1971 */     template.substitute(new String[] { "${fixedParamSelect}", fixedParamSelect.toString(), "${checkIndex}", String.valueOf(checkIndex) });
/*      */ 
/* 1976 */     template.substituteRepeatingLines("${repeatingAddressSelect}", checkElements.length, "union", new String[] { new StringBuilder().append("${separator} select <checkDim.${index}> as dim").append(checkIndex).append(" from dual ").toString() });
/*      */ 
/* 1981 */     template.substituteRepeatingLines("${applicableCCSSelect}", relevantCellCalcs.length, "union", new String[] { "${separator} select <ccDeploymentId.${index}> as cc_deployment_id from dual " });
/*      */ 
/* 1986 */     template.substituteRepeatingLines("${allDimSelect}", selections.length, ",", new String[] { "${separator}dim${index}" });
/*      */ 
/* 1991 */     template.substituteRepeatingLines("${seOriginColumns}", selections.length, ",", new String[] { "${separator}se${index}.structure_id as s${index}, se${index}.structure_element_id as se${index},", "se${index}.position as p${index}, se${index}.end_position as ep${index}, se${index}.leaf as leaf${index}" });
/*      */ 
/* 1997 */     template.substituteRepeatingLines("${seOriginTables}", selections.length, ",", new String[] { "${separator}structure_element se${index}" });
/*      */ 
/* 2002 */     template.substituteRepeatingLines("${seOriginWherePredicate}", selections.length, "and", new String[] { "${separator} ca.dim${index} = se${index}.structure_element_id" });
/*      */ 
/* 2007 */     StringBuilder depJoins = new StringBuilder();
/* 2008 */     for (int i = 0; i < selections.length; i++)
/*      */     {
/* 2010 */       StringTemplate st = getTemplate("getTargetCellCalculationDepJoinSQL");
/* 2011 */       st.setAttribute("index", i);
/* 2012 */       st.setAttribute("priorIndex", i - 1);
/* 2013 */       st.setAttribute("includePriorJoin", Boolean.valueOf(i > 0));
/* 2014 */       depJoins.append(st.toString());
/*      */     }
/* 2016 */     template.substitute(new String[] { "${deploymentJoins}", depJoins.toString() });
/*      */ 
/* 2018 */     SqlExecutor sqle = null;
/*      */     try
/*      */     {
/* 2021 */       sqle = new SqlExecutor("getTargetCellCalculations", getDataSource(), template, this._log);
/* 2022 */       ResultSet resultSet = null;
/*      */ 
/* 2024 */       sqle.addBindVariable("<modelId>", Integer.valueOf(modelId));
/*      */ 
/* 2027 */       for (int i = 0; i < selections.length; i++)
/*      */       {
/* 2029 */         if (i == checkIndex)
/*      */           continue;
/* 2031 */         sqle.addBindVariable(new StringBuilder().append("<fixedDim.").append(i).append(">").toString(), Integer.valueOf(selections[i]));
/*      */       }
/*      */ 
/* 2035 */       for (int i = 0; i < checkElements.length; i++) {
/* 2036 */         sqle.addBindVariable(new StringBuilder().append("<checkDim.").append(i).append(">").toString(), Integer.valueOf(checkElements[i]));
/*      */       }
/*      */ 
/* 2039 */       for (int i = 0; i < relevantCellCalcs.length; i++) {
/* 2040 */         sqle.addBindVariable(new StringBuilder().append("<ccDeploymentId.").append(i).append(">").toString(), Integer.valueOf(relevantCellCalcs[i]));
/*      */       }
/* 2042 */       resultSet = sqle.getResultSet();
/*      */ 
/* 2044 */       Map seToCcIdMap = new HashMap();
/* 2045 */       String seColumName = new StringBuilder().append("se").append(checkIndex).toString();
/* 2046 */       while (resultSet.next())
/*      */       {
/* 2048 */         int seId = resultSet.getInt(seColumName);
/* 2049 */         int ccDeploymentId = resultSet.getInt("cc_deployment_id");
/* 2050 */         int ccDeploymentLineId = resultSet.getInt("cc_deployment_line_id");
/* 2051 */         List targetsList = (List)seToCcIdMap.get(Integer.valueOf(seId));
/* 2052 */         if (targetsList == null)
/* 2053 */           targetsList = new ArrayList();
/* 2054 */         targetsList.add(new RuntimeCcDeploymentTarget(ccDeploymentId, ccDeploymentLineId));
/* 2055 */         seToCcIdMap.put(Integer.valueOf(seId), targetsList);
/*      */       }
/*      */ 
/* 2058 */       for (int i = 0; i < checkElements.length; i++) {
/* 2059 */         results[i] = ((List)seToCcIdMap.get(Integer.valueOf(checkElements[i])));
/*      */       }
/*      */       return results;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2065 */       throw handleSQLException(template.toString(), e);
/*      */     }
/*      */     finally
/*      */     {
/* 2069 */       if (sqle != null)
/* 2070 */         sqle.close();
/* 2071 */       t.logInfo("getTargetCellCalculations", new StringBuilder().append("rows=").append(results.length).toString()); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public List<RuntimeCubeDeployment> getRelevantCubeDeployments(int modelId, int financeCubeId, int[] selections, Set<String> dataTypes, List<Integer> formDimensionIndexes, boolean includeCubeFormula)
/*      */   {
/* 2152 */     Timer t = new Timer(this._log);
/*      */ 
/* 2154 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "with getDeployments as", "(", "select  distinct dep0.model_id, dep0.finance_cube_id, ", "                 dep0.owner_type, dep0.owner_id, dep0.owner_line_id", "from    (", "        select  ${cellAddressParams}", "        from    dual", "        )", "${matchDims}", ")", "select  dep.model_id, dep.finance_cube_id,", "        dep.owner_type, dep.owner_id, dep.owner_line_id", "        ,cursor", "        (", "            select owner_line_id, rtcde.dim_seq, rtcde.structure_id, rtcde.start_se_id, rtcde.start_pos,", "                   rtcde.end_se_id, rtcde.end_pos, rtcddt.data_type_id, rtcddt.data_type", "            from rt_cube_deployment rtcd", "            join rt_cube_deployment_entry rtcde using( RT_CUBE_DEPLOYMENT_ID )", "            join rt_cube_deployment_dt rtcddt using( RT_CUBE_DEPLOYMENT_ID, OWNER_LINE_ID )", "            where rtcd.model_id = dep.model_id", "            and   ( rtcd.finance_cube_id is null or rtcd.finance_cube_id = dep.finance_cube_id )", "            and   rtcd.owner_id = dep.owner_id", "            and   owner_line_id = dep.owner_line_id", "            order by dim_seq", "        ) as deployment_lines", "/* the following columns are cell calculation specific */", "        ,ccd.xmlform_id", "        ,ccd.dim_context0, ccd.dim_context1, ccd.dim_context2, ccd.dim_context3, ccd.dim_context4", "        ,ccd.dim_context5, ccd.dim_context6, ccd.dim_context7, ccd.dim_context8, ccd.dim_context9", "        ,cursor", "        (", "        select  ccde.*", "        from    CC_DEPLOYMENT_ENTRY ccde", "        where   ccdl.CC_DEPLOYMENT_LINE_ID = ccde.CC_DEPLOYMENT_LINE_ID", "        order   by ccde.DIM_SEQ", "        ) as old_entries", "        ,cursor", "        (", "        select structure_element_id, se.position, ccme.dim_seq, cc_mapping_line_id,", "               ccml.form_field_name, dt.vis_id as data_type_visid", "        from CC_MAPPING_LINE ccml", "        join DATA_TYPE dt using( DATA_TYPE_ID )", "        left join CC_MAPPING_ENTRY ccme using( CC_MAPPING_LINE_ID )", "        left join STRUCTURE_ELEMENT se using( STRUCTURE_ID, STRUCTURE_ELEMENT_ID )", "        where CC_DEPLOYMENT_LINE_ID = ccdl.CC_DEPLOYMENT_LINE_ID", "        order by CC_MAPPING_LINE_ID, ccme.DIM_SEQ", "        ) as mapping_entries", "/* the following columns(s) are cube formula specific */", "        ,cf.formula_text", "from    getDeployments dep", "left join CC_DEPLOYMENT ccd on ( dep.OWNER_ID = ccd.CC_DEPLOYMENT_ID )", "left join CC_DEPLOYMENT_LINE ccdl on ", "(", "  ccdl.CC_DEPLOYMENT_ID = dep.OWNER_ID and ", "  ccdl.CC_DEPLOYMENT_LINE_ID = dep.OWNER_LINE_ID and ", "  dep.OWNER_TYPE = 0 ", ")", "left join CUBE_FORMULA cf on ( cf.CUBE_FORMULA_ID = dep.OWNER_ID and dep.OWNER_TYPE = 1 )", "where dep.OWNER_TYPE in (${ownerTypes})", "order by dep.OWNER_ID, dep.OWNER_LINE_ID" });
/*      */ 
/* 2225 */     SqlBuilder matchDims = new SqlBuilder();
/* 2226 */     for (int i = 0; i < selections.length; i++)
/*      */     {
/* 2228 */       matchDims.addLines(new String[] { "join    STRUCTURE_ELEMENT se${index}", "        on (", "               se${index}.STRUCTURE_ELEMENT_ID = DIM${index}", "           )", "join    (", "        select  MODEL_ID, FINANCE_CUBE_ID", "               ,OWNER_ID, OWNER_LINE_ID, OWNER_TYPE", "                ,DIM_SEQ, STRUCTURE_ID, START_POS, END_POS", "        from    RT_CUBE_DEPLOYMENT", "        join    RT_CUBE_DEPLOYMENT_ENTRY using (RT_CUBE_DEPLOYMENT_ID)", "        ${joinDataTypes}", "        where RT_CUBE_DEPLOYMENT.MODEL_ID = <modelId>", "          and ( RT_CUBE_DEPLOYMENT.FINANCE_CUBE_ID is NULL or ", "                RT_CUBE_DEPLOYMENT.FINANCE_CUBE_ID = <financeCubeId> )", "        ) dep${index}", "        on (", "               dep${index}.DIM_SEQ = ${index}", "           ${matchDeployment}", "           and se${index}.STRUCTURE_ID = dep${index}.STRUCTURE_ID", "           and (", "                  (DYN${index} = ' ' and se${index}.POSITION between dep${index}.START_POS and dep${index}.END_POS)", "               or (", "                      DYN${index} = 'Y'", "                  and not (", "                              se${index}.POSITION > dep${index}.END_POS", "                          or  se${index}.END_POSITION < dep${index}.START_POS", "                          )", "                  )", "               )", "           )" });
/*      */ 
/* 2264 */       if (i == 0)
/*      */       {
/* 2266 */         matchDims.substituteLines("${joinDataTypes}", new String[] { "join    RT_CUBE_DEPLOYMENT_DT    using (RT_CUBE_DEPLOYMENT_ID, OWNER_LINE_ID)", "join    (", "        ${getDataTypes}", "        )", "        using (DATA_TYPE)" });
/*      */ 
/* 2273 */         matchDims.substitute(new String[] { "${matchDeployment}", null });
/*      */       }
/*      */       else
/*      */       {
/* 2277 */         matchDims.substitute(new String[] { "${joinDataTypes}", null });
/* 2278 */         matchDims.substituteLines("${matchDeployment}", new String[] { "           and dep${index}.OWNER_ID       = dep0.OWNER_ID", "           and dep${index}.OWNER_LINE_ID  = dep0.OWNER_LINE_ID", "           and dep${index}.OWNER_TYPE     = dep0.OWNER_TYPE" });
/*      */       }
/*      */ 
/* 2284 */       matchDims.substitute(new String[] { "${index}", String.valueOf(i) });
/*      */     }
/* 2286 */     sqlb.substituteLines("${matchDims}", matchDims);
/*      */ 
/* 2288 */     sqlb.substituteRepeatingLines("${getDataTypes}", dataTypes.size(), "union", new String[] { "${separator} select <dataTypeId.${index}> as DATA_TYPE from dual " });
/*      */ 
/* 2292 */     sqlb.substituteRepeatingLines("${cellAddressParams}", selections.length, ",", new String[] { "${separator}<dimAddr.${index}> as dim${index}, <dimSel.${index}> as dyn${index}" });
/*      */ 
/* 2296 */     sqlb.substitute(new String[] { "${ownerTypes}", includeCubeFormula ? "0,1" : "0" });
/*      */ 
/* 2298 */     SqlExecutor sqle = new SqlExecutor("getRelevantCubeDeployments", getConnection(), sqlb, this._log);
/* 2299 */     for (int i = 0; i < selections.length; i++)
/*      */     {
/* 2301 */       sqle.addBindVariable("<modelId>", Integer.valueOf(modelId));
/* 2302 */       sqle.addBindVariable("<financeCubeId>", Integer.valueOf(financeCubeId));
/* 2303 */       sqle.addBindVariable(new StringBuilder().append("<dimAddr.").append(i).append(">").toString(), Integer.valueOf(selections[i]));
/* 2304 */       sqle.addBindVariable(new StringBuilder().append("<dimSel.").append(i).append(">").toString(), (formDimensionIndexes.contains(Integer.valueOf(i))) || (i == selections.length - 1) ? "Y" : " ");
/*      */     }
/*      */ 
/* 2310 */     int i = 0;
/* 2311 */     for (Iterator i$ = dataTypes.iterator(); i$.hasNext(); ) { 
				  String dataType = (String)i$.next();
/*      */ 
/* 2313 */       sqle.addBindVariable(new StringBuilder().append("<dataTypeId.").append(i).append(">").toString(), dataType);
/* 2314 */       i++;
/*      */     }
/*      */     String dataType;
/* 2317 */     List retList = null;
/*      */     try
/*      */     {
/* 2320 */       retList = loadRuntimeCubeDeployment(modelId, financeCubeId, sqle.getResultSet());
/*      */       return retList;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2325 */       throw handleSQLException(sqlb.toString(), e);
/*      */     }
/*      */     finally
/*      */     {
/* 2329 */       t.logDebug("getRelevantCubeDeployments", retList != null ? new StringBuilder().append("items=").append(retList.size()).toString() : "");
/* 2330 */       sqle.close();
/* 2331 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   private List<RuntimeCubeDeployment> loadRuntimeCubeDeployment(int modelId, int financeCubeId, ResultSet resultSet)
/*      */     throws SQLException
/*      */   {
/* 2338 */     List deployments = new ArrayList();
/*      */ 
/* 2340 */     RuntimeCubeDeployment currentDeployment = null;
/*      */ 
/* 2342 */     while (resultSet.next())
/*      */     {
/* 2345 */       int ownerId = resultSet.getInt("owner_id");
/* 2346 */       int ownerLineId = resultSet.getInt("owner_line_id");
/* 2347 */       int ownerType = resultSet.getInt("owner_type");
/*      */ 
/* 2350 */       ResultSet deploymentRS = (ResultSet)resultSet.getObject("deployment_lines");
/*      */ 
/* 2353 */       int XmlFormId = resultSet.getInt("xmlform_id");
/* 2354 */       int[] contextDefs = loadContextDefs(resultSet);
/* 2355 */       int explicitDimensionCount = countExplictDims(contextDefs);
/* 2356 */       ResultSet oldRS = (ResultSet)resultSet.getObject("old_entries");
/* 2357 */       ResultSet mappingsRS = (ResultSet)resultSet.getObject("mapping_entries");
/*      */ 
/* 2360 */       String cubeFormulaText = resultSet.getString("formula_text");
/*      */ 
/* 2362 */       if ((currentDeployment == null) || ((currentDeployment != null) && (currentDeployment.getOwnerId() != ownerId)))
/*      */       {
/* 2366 */         if (ownerType == 0)
/* 2367 */           currentDeployment = new RuntimeCcDeployment(modelId, financeCubeId, ownerId, contextDefs, XmlFormId);
/* 2368 */         else if (ownerType == 1)
/* 2369 */           currentDeployment = new RuntimeCubeFormulaDeployment(modelId, financeCubeId, ownerId, cubeFormulaText);
/*      */         else
/* 2371 */           throw new IllegalStateException(new StringBuilder().append("Unexpected owner type in runtime deployment : ").append(ownerType).toString());
/* 2372 */         deployments.add(currentDeployment);
/*      */       }
/*      */       RuntimeCubeDeploymentLine deploymentLine;
/* 2377 */       if (ownerType == 0)
/*      */       {
/* 2379 */         deploymentLine = new RuntimeCcDeploymentLine((RuntimeCcDeployment)currentDeployment, ownerLineId, new ArrayList(), null);
/*      */       }
/*      */       else
/*      */       {
/* 2382 */         if (ownerType == 1)
/*      */         {
/* 2384 */           deploymentLine = new RuntimeCubeDeploymentLine(currentDeployment, ownerLineId, new ArrayList(), null);
/*      */         }
/*      */         else
/*      */         {
/* 2388 */           throw new IllegalStateException(new StringBuilder().append("Unexpected runtime deployment type:").append(ownerType).toString());
/*      */         }
/*      */       }
/* 2390 */       Set dataTypes = new HashSet();
/*      */ 
/* 2395 */       while (deploymentRS.next())
/*      */       {
/* 2397 */         int dimSeq = deploymentRS.getInt("dim_seq");
/* 2398 */         int nestedOwnerLineId = deploymentRS.getInt("owner_line_id");
/* 2399 */         int structureId = deploymentRS.getInt("structure_id");
/* 2400 */         int startSeId = deploymentRS.getInt("start_se_id");
/* 2401 */         int startPos = deploymentRS.getInt("start_pos");
/* 2402 */         int endSeId = deploymentRS.getInt("end_se_id");
/* 2403 */         int endPos = deploymentRS.getInt("end_pos");
/* 2404 */         String dataType = deploymentRS.getString("data_type");
/* 2405 */         deploymentLine.getDeploymentEntries().add(new RuntimeCubeDeploymentEntry(dimSeq, structureId, startSeId, startPos, endSeId, endPos));
/* 2406 */         dataTypes.add(dataType);
/*      */       }
/*      */ 
/* 2419 */       deploymentLine.setDataTypes((String[])dataTypes.toArray(new String[0]));
/*      */ 
/* 2421 */       deploymentRS.close();
/*      */ 
/* 2423 */       if (ownerType == 0)
/*      */       {
/* 2425 */         while (oldRS.next())
/*      */         {
/* 2427 */           int calStructureElementId = oldRS.getInt("structure_element_id");
/* 2428 */           int dimSeq = oldRS.getInt("dim_seq");
/* 2429 */           boolean selected = oldRS.getString("selected_ind").equalsIgnoreCase("Y");
/*      */ 
/* 2431 */           if ((selected) && (dimSeq == contextDefs.length))
/* 2432 */             ((RuntimeCcDeploymentLine)deploymentLine).getCalendarElementIds().add(Integer.valueOf(calStructureElementId));
/*      */         }
/* 2434 */         oldRS.close();
/*      */ 
/* 2436 */         RuntimeCcDeploymentMapping currentMapping = null;
/* 2437 */         int currentMappingLineId = -1;
/*      */ 
/* 2439 */         int explicitIndex = 0;
/* 2440 */         while (mappingsRS.next())
/*      */         {
/* 2442 */           int structureElementId = mappingsRS.getInt("structure_element_id");
/* 2443 */           int sePosition = mappingsRS.getInt("position");
/* 2444 */           int dimSeq = mappingsRS.getInt("dim_seq");
/* 2445 */           int mappingLineId = mappingsRS.getInt("cc_mapping_line_id");
/* 2446 */           String formFieldName = mappingsRS.getString("form_field_name");
/* 2447 */           String dataType = mappingsRS.getString("data_type_visid");
/*      */ 
/* 2449 */           if (currentMappingLineId != mappingLineId)
/*      */           {
/* 2451 */             currentMapping = new RuntimeCcDeploymentMapping();
/* 2452 */             currentMapping.setDataType(dataType);
/* 2453 */             currentMapping.setSummaryFieldName(formFieldName);
/* 2454 */             currentMapping.setCcDeploymentLineId(ownerLineId);
/* 2455 */             currentMapping.setExplicitStructureElementIds(new int[explicitDimensionCount]);
/* 2456 */             currentMapping.setExplicitStructureElementPositions(new int[explicitDimensionCount]);
/* 2457 */             currentMappingLineId = mappingLineId;
/* 2458 */             explicitIndex = 0;
/*      */ 
/* 2460 */             ((RuntimeCcDeploymentLine)deploymentLine).getCcDeploymentMappings().add(currentMapping);
/*      */           }
/*      */ 
/* 2463 */           if (explicitIndex < explicitDimensionCount)
/*      */           {
/* 2465 */             currentMapping.getExplicitStructureElementIds()[explicitIndex] = structureElementId;
/* 2466 */             currentMapping.getExplicitStructureElementPositions()[explicitIndex] = sePosition;
/* 2467 */             explicitIndex++;
/*      */           }
/*      */         }
/* 2470 */         mappingsRS.close();
/*      */       }
/*      */ 
/* 2473 */       currentDeployment.getDeploymentLines().put(Integer.valueOf(ownerLineId), deploymentLine);
/*      */     }
/*      */ 
/* 2476 */     return deployments;
/*      */   }
/*      */ 
/*      */   private int countExplictDims(int[] dimensionContexts)
/*      */   {
/* 2482 */     int count = 0;
/* 2483 */     for (int context : dimensionContexts)
/* 2484 */       if (context == 1)
/* 2485 */         count++;
/* 2486 */     return count;
/*      */   }
/*      */ 
/*      */   private int[] loadContextDefs(ResultSet rs)
/*      */     throws SQLException
/*      */   {
/* 2493 */     List contexts = new ArrayList();
/* 2494 */     for (int i = 0; i < 10; i++)
/*      */     {
/* 2496 */       int value = rs.getInt(new StringBuilder().append("dim_context").append(i).toString());
/* 2497 */       if (rs.wasNull())
/*      */         break;
/* 2499 */       contexts.add(Integer.valueOf(value));
/*      */     }
/* 2501 */     int[] result = new int[contexts.size()];
/* 2502 */     for (int i = 0; i < result.length; i++)
/* 2503 */       result[i] = ((Integer)contexts.get(i)).intValue();
/* 2504 */     return result;
/*      */   }
/*      */ 
/*      */   public void tidyCellCalculationMetaData(int modelId, int dimCount)
/*      */     throws SQLException
/*      */   {
/* 2517 */     EntityList orphanMappingLines = tidyOrphanMappingLines(modelId, dimCount);
/* 2518 */     EntityList orphanDeploymentLines = tidyOrphanDeploymentLines(modelId, dimCount);
/*      */   }
/*      */ 
/*      */   private EntityList tidyOrphanDeploymentLines(int modelId, int dimCount)
/*      */     throws SQLException
/*      */   {
/* 2594 */     PreparedStatement ps = null;
/* 2595 */     ResultSet rs = null;
/* 2596 */     EntityList result = null;
/*      */     try
/*      */     {
/* 2602 */       SqlBuilder sqlBuilder = new SqlBuilder(QUERY_INVALID_DEPLOYMENT_LINES);
/* 2603 */       ps = getConnection().prepareStatement(sqlBuilder.toString());
/* 2604 */       ps.setInt(1, modelId);
/* 2605 */       rs = ps.executeQuery();
/* 2606 */       result = JdbcUtils.extractToEntityListImpl(ORPHAN_DEPLOYMENT_LINES_COL_INFO, rs);
/*      */     }
/*      */     finally
/*      */     {
/* 2610 */       closeResultSet(rs);
/* 2611 */       closeStatement(ps);
/* 2612 */       closeConnection();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2618 */       SqlBuilder sqlBuilder = new SqlBuilder(DELETE_DEPLOYMENT_ENTRIES);
/* 2619 */       sqlBuilder.substitute(new String[] { "${OrphanQuery}", new SqlBuilder(QUERY_INVALID_DEPLOYMENT_LINES).toString() });
/* 2620 */       ps = getConnection().prepareStatement(sqlBuilder.toString());
/* 2621 */       ps.setInt(1, modelId);
/* 2622 */       ps.executeUpdate();
/*      */     }
/*      */     finally
/*      */     {
/* 2626 */       closeResultSet(rs);
/* 2627 */       closeStatement(ps);
/* 2628 */       closeConnection();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2634 */       SqlBuilder query = new SqlBuilder(QUERY_DEPLOYMENT_LINE_WITH_ZERO_DIM_ENTRIES);
/* 2635 */       query.substituteRepeatingLines("${OuterSumDimCount}", dimCount, ", ", new String[] { "${separator} sum(dim${index}_count) dim${index}_count" });
/*      */ 
/* 2639 */       query.substituteRepeatingLines("${InnerDimSeqSumCaseCount}", dimCount, ", ", new String[] { "${separator} sum( case when dim_seq=${index} then 1 else 0 end ) as dim${index}_count" });
/*      */ 
/* 2643 */       query.substituteRepeatingLines("${DimCountWherePredicate}", dimCount, "or ", new String[] { "${separator} dim${index}_count = 0" });
/*      */ 
/* 2647 */       SqlBuilder sqlBuilder = new SqlBuilder(DELETE_DEPLOYMENT_LINES);
/* 2648 */       sqlBuilder.substitute(new String[] { "${OrphanQuery}", query.toString() });
/* 2649 */       ps = getConnection().prepareStatement(sqlBuilder.toString());
/* 2650 */       ps.setInt(1, modelId);
/* 2651 */       ps.executeUpdate();
/*      */     }
/*      */     finally
/*      */     {
/* 2655 */       closeResultSet(rs);
/* 2656 */       closeStatement(ps);
/* 2657 */       closeConnection();
/*      */     }
/*      */ 
/* 2660 */     deleteOrphanMappingLineEntries();
/*      */ 
/* 2662 */     return result;
/*      */   }
/*      */ 
/*      */   private EntityList tidyOrphanMappingLines(int modelId, int dimCount)
/*      */     throws SQLException
/*      */   {
/* 2709 */     PreparedStatement ps = null;
/* 2710 */     ResultSet rs = null;
/* 2711 */     EntityList result = null;
/*      */     try
/*      */     {
/* 2715 */       ps = getConnection().prepareStatement("select ccd.cc_deployment_id, ccd.vis_id, ccdl.cc_deployment_line_id, \n       ccml.cc_mapping_line_id \nfrom cc_deployment ccd, \n     cc_deployment_line ccdl, \n     cc_mapping_line ccml, \n     cc_mapping_entry ccme \nwhere ccd.model_id = ? \n  and ccd.cc_deployment_id = ccdl.cc_deployment_id \n  and ccdl.cc_deployment_line_id = ccml.cc_deployment_line_id \n  and ccme.cc_mapping_line_id (+) = ccml.cc_mapping_line_id \n  and ( ccme.structure_id, ccme.structure_element_id ) not in \n      ( select se.structure_id, se.structure_element_id \n        from structure_element_view se )");
/* 2716 */       ps.setInt(1, modelId);
/* 2717 */       rs = ps.executeQuery();
/* 2718 */       result = JdbcUtils.extractToEntityListImpl(ORPHAN_MAPPING_LINES_COL_INFO, rs);
/*      */     }
/*      */     finally
/*      */     {
/* 2722 */       closeResultSet(rs);
/* 2723 */       closeStatement(ps);
/* 2724 */       closeConnection();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2730 */       SqlBuilder sqlBuilder = new SqlBuilder(DELETE_ORPHAN_MAPPING_LINES);
/* 2731 */       sqlBuilder.substitute(new String[] { "${OrphanQuery}", "select ccd.cc_deployment_id, ccd.vis_id, ccdl.cc_deployment_line_id, \n       ccml.cc_mapping_line_id \nfrom cc_deployment ccd, \n     cc_deployment_line ccdl, \n     cc_mapping_line ccml, \n     cc_mapping_entry ccme \nwhere ccd.model_id = ? \n  and ccd.cc_deployment_id = ccdl.cc_deployment_id \n  and ccdl.cc_deployment_line_id = ccml.cc_deployment_line_id \n  and ccme.cc_mapping_line_id (+) = ccml.cc_mapping_line_id \n  and ( ccme.structure_id, ccme.structure_element_id ) not in \n      ( select se.structure_id, se.structure_element_id \n        from structure_element_view se )" });
/* 2732 */       ps = getConnection().prepareStatement(sqlBuilder.toString());
/* 2733 */       ps.setInt(1, modelId);
/* 2734 */       ps.executeUpdate();
/*      */     }
/*      */     finally
/*      */     {
/* 2738 */       closeResultSet(rs);
/* 2739 */       closeStatement(ps);
/* 2740 */       closeConnection();
/*      */     }
/*      */ 
/* 2744 */     deleteOrphanMappingLineEntries();
/*      */ 
/* 2746 */     return result;
/*      */   }
/*      */ 
/*      */   private void deleteOrphanMappingLineEntries()
/*      */     throws SQLException
/*      */   {
/* 2767 */     PreparedStatement ps = null;
/* 2768 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2772 */       SqlBuilder sqlBuilder = new SqlBuilder(DELETE_ORPHAN_MAPPING_ENTRIES);
/* 2773 */       ps = getConnection().prepareStatement(sqlBuilder.toString());
/* 2774 */       ps.executeUpdate();
/*      */     }
/*      */     finally
/*      */     {
/* 2778 */       closeResultSet(rs);
/* 2779 */       closeStatement(ps);
/* 2780 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int[] queryCellCalcDetailsForCell(int financeCubeId, int[] address, String dataType)
/*      */   {
/* 2797 */     SqlBuilder builder = new SqlBuilder(new String[] { "select short_id, cell_calc_id", "from cft${financeCubeId}", "where ${dimPredicates} ", "and data_type = <dataType>" });
/*      */ 
/* 2802 */     SqlExecutor sqle = null;
/* 2803 */     ResultSet rs = null;
/* 2804 */     int short_id = -1;
/*      */ 
/* 2806 */     builder.substitute(new String[] { "${financeCubeId}", String.valueOf(financeCubeId) });
/* 2807 */     builder.substituteRepeatingLines("${dimPredicates}", address.length, "and", new String[] { "${separator} dim${index} = <dim${index}>" });
/*      */     try
/*      */     {
/* 2815 */       sqle = new SqlExecutor("queryCellCalcDetailsForCell", getConnection(), builder, this._log);
/*      */ 
/* 2819 */       for (int i = 0; i < address.length; i++) {
/* 2820 */         sqle.addBindVariable(new StringBuilder().append("<dim").append(i).append(">").toString(), Integer.valueOf(address[i]));
/*      */       }
/* 2822 */       sqle.addBindVariable("<dataType>", dataType);
/*      */ 
/* 2824 */       rs = sqle.getResultSet();
/*      */ 
/* 2826 */       if (rs.next()) {
/* 2827 */         return new int[] { rs.getInt("short_id"), rs.getInt("cell_calc_id") };
/*      */       }
/*      */       return null;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2833 */       throw handleSQLException(builder.toString(), e);
/*      */     }
/*      */     finally
/*      */     {
/* 2837 */       if (rs != null)
/* 2838 */         closeResultSet(rs);
/* 2839 */       if (sqle != null)
/* 2840 */         sqle.close();
/* 2841 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public int storeCellCalculatorInstancesDetails(int modelId, int financeCubeId, int calStructureId, int taskId, int calIndex, List<String> ccDeploymentVisIds, List<Pair<CalendarElementNode, CalendarElementNode>> calendarRanges)
/*      */   {
/* 2861 */     StringTemplate st = getTemplate("storeCalculatorInstancesInScope");
/*      */ 
/* 2863 */     st.setAttribute("financeCubeId", new Integer(financeCubeId));
/* 2864 */     st.setAttribute("taskId", new Integer(taskId));
/* 2865 */     st.setAttribute("calIndex", new Integer(calIndex));
/* 2866 */     st.setAttribute("calSel", calendarRanges);
/* 2867 */     st.setAttribute("depVisIds", ccDeploymentVisIds);
/*      */ 
/* 2869 */     SqlBuilder builder = new SqlBuilder(new String[] { st.toString() });
/* 2870 */     SqlExecutor sqle = null;
/*      */     try
/*      */     {
/* 2874 */       sqle = new SqlExecutor("storeCalculatorInstancesInScope", getConnection(), builder, this._log);
/*      */ 
/* 2876 */       sqle.addBindVariable("${modelId}", Integer.valueOf(modelId));
/* 2877 */       sqle.addBindVariable("${structureId}", Integer.valueOf(calStructureId));
/* 2878 */       sqle.addBindVariable("${financeCubeId}", Integer.valueOf(financeCubeId));
/*      */ 
/* 2880 */       for (int i = 0; i < calendarRanges.size(); i++)
/*      */       {
/* 2882 */         Pair entry = (Pair)calendarRanges.get(i);
/* 2883 */         sqle.addBindVariable(new StringBuilder().append("${structureElement").append(i).append("From}").toString(), Integer.valueOf(((CalendarElementNode)entry.getChild1()).getStructureElementId()));
/* 2884 */         sqle.addBindVariable(new StringBuilder().append("${structureElement").append(i).append("To}").toString(), Integer.valueOf(((CalendarElementNode)entry.getChild2()).getStructureElementId()));
/*      */       }
/*      */ 
/*      */       return sqle.executeUpdate();
/*      */     }
/*      */     finally
/*      */     {
/* 2891 */       if (sqle != null)
/* 2892 */         sqle.close();
/* 2893 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public void removeCalculatorInstanceDetails(int financeCubeId, int taskId, Set<Pair<Integer, Integer>> calcIds, int batchSize)
/*      */   {
/* 2907 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/* 2910 */       String statement = "delete from import_calc_work where finance_cube_id = ? and task_id = ? and cc_deployment_id = ? and cc_short_id = ?";
/*      */ 
/* 2917 */       ps = getConnection().prepareStatement(statement);
/*      */ 
/* 2919 */       int deletesInBatchCount = 0;
/* 2920 */       for (Pair entry : calcIds)
/*      */       {
/* 2922 */         int paramNo = 1;
/* 2923 */         ps.setInt(paramNo++, financeCubeId);
/* 2924 */         ps.setInt(paramNo++, taskId);
/* 2925 */         ps.setInt(paramNo++, ((Integer)entry.getChild1()).intValue());
/* 2926 */         ps.setInt(paramNo++, ((Integer)entry.getChild2()).intValue());
/* 2927 */         ps.addBatch();
/*      */ 
/* 2929 */         deletesInBatchCount++;
/*      */ 
/* 2931 */         if (deletesInBatchCount >= batchSize)
/*      */         {
/* 2933 */           ps.executeBatch();
/* 2934 */           deletesInBatchCount = 0;
/*      */         }
/*      */       }
/*      */ 
/* 2938 */       if (deletesInBatchCount > 0)
/* 2939 */         ps.executeBatch();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2943 */       throw handleSQLException("removeCalculatorInstanceDetails", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2947 */       closeStatement(ps);
/* 2948 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<Pair<Integer, Integer>> loadCalculatorInstanceDetailsBatch(int financeCubeId, int taskId, int startRow, int batchSize)
/*      */   {
/* 2963 */     SqlBuilder builder = new SqlBuilder(new String[] { "select row_number, cc_deployment_id, cc_short_id", "     from", "     (", "       select rownum as row_number, cc_deployment_id, cc_short_id", "       from import_calc_work where finance_cube_id = ${financeCubeId} and task_id = ${taskId}", "       order by cc_deployment_id, cc_short_id", "     ) where row_number between ${startRow} and ${endRow}" });
/*      */ 
/* 2971 */     SqlExecutor sqle = null;
/* 2972 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2975 */       sqle = new SqlExecutor("queryCellCalcDetailsForCell", getConnection(), builder, this._log);
/*      */ 
/* 2977 */       sqle.addBindVariable("${financeCubeId}", Integer.valueOf(financeCubeId));
/* 2978 */       sqle.addBindVariable("${taskId}", Integer.valueOf(taskId));
/* 2979 */       sqle.addBindVariable("${startRow}", Integer.valueOf(startRow));
/* 2980 */       sqle.addBindVariable("${endRow}", Integer.valueOf(startRow + batchSize - 1));
/*      */ 
/* 2982 */       rs = sqle.getResultSet();
/*      */ 
/* 2984 */       List result = new ArrayList();
/*      */ 
/* 2986 */       while (rs.next()) {
/* 2987 */         result.add(new Pair(Integer.valueOf(rs.getInt("cc_deployment_id")), Integer.valueOf(rs.getInt("cc_short_id"))));
/*      */       }
/* 2989 */       List localList1 = result;
/*      */       return localList1;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2993 */       throw handleSQLException(builder.toString(), e);
/*      */     }
/*      */     finally
/*      */     {
/* 2997 */       if (rs != null)
/* 2998 */         closeResultSet(rs);
/* 2999 */       if (sqle != null)
/* 3000 */         sqle.close();
/* 3001 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public void deleteImportCalcWorkRows(int financeCubeId, int taskId)
/*      */   {
/* 3010 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/* 3013 */       ps = getConnection().prepareStatement("delete from import_calc_work where task_id = ? and finance_cube_id = ?");
/* 3014 */       ps.setInt(1, taskId);
/* 3015 */       ps.setInt(2, financeCubeId);
/* 3016 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3020 */       throw handleSQLException("deleteImportCalcWorkRows", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3024 */       closeStatement(ps);
/* 3025 */       closeConnection();
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.cc.CcDeploymentDAO
 * JD-Core Version:    0.6.0
 */