/*      */ package com.cedar.cp.ejb.impl.dimension;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
			import com.cedar.cp.api.dimension.HierarchyRef;
/*      */ import com.cedar.cp.dto.dimension.AllHierarchysELO;
/*      */ import com.cedar.cp.dto.dimension.AugHierarchyElementCK;
/*      */ import com.cedar.cp.dto.dimension.AugHierarchyElementPK;
/*      */ import com.cedar.cp.dto.dimension.CalendarForFinanceCubeELO;
/*      */ import com.cedar.cp.dto.dimension.CalendarForModelELO;
/*      */ import com.cedar.cp.dto.dimension.CalendarForModelVisIdELO;
/*      */ import com.cedar.cp.dto.dimension.DimensionCK;
/*      */ import com.cedar.cp.dto.dimension.DimensionPK;
/*      */ import com.cedar.cp.dto.dimension.DimensionRefImpl;
/*      */ import com.cedar.cp.dto.dimension.HierarchyCK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyElementCK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyElementPK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyPK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyRefImpl;
/*      */ import com.cedar.cp.dto.dimension.HierarcyDetailsFromDimIdELO;
/*      */ import com.cedar.cp.dto.dimension.HierarcyDetailsIncRootNodeFromDimIdELO;
/*      */ import com.cedar.cp.dto.dimension.ImportableHierarchiesELO;
			import com.cedar.cp.dto.dimension.SelectedHierarchysELO;
/*      */ import com.cedar.cp.dto.dimension.StructureElementPK;
/*      */ import com.cedar.cp.dto.dimension.StructureElementRefImpl;
/*      */ import com.cedar.cp.dto.model.FinanceCubePK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeRefImpl;
/*      */ import com.cedar.cp.dto.model.ModelDimensionRelPK;
/*      */ import com.cedar.cp.dto.model.ModelDimensionRelRefImpl;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import com.cedar.cp.util.common.JdbcUtils;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
			import java.util.HashMap;
			import java.util.HashSet;
/*      */ import java.util.Iterator;
			/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
			import javax.sql.DataSource;
/*      */ 
/*      */ public class HierarchyDAO extends AbstractDAO
/*      */ {
/*   63 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select HIERARCHY.HIERARCHY_ID,HIERARCHY.DIMENSION_ID,HIERARCHY.VIS_ID,HIERARCHY.DESCRIPTION,HIERARCHY.VERSION_NUM,HIERARCHY.UPDATED_BY_USER_ID,HIERARCHY.UPDATED_TIME,HIERARCHY.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from HIERARCHY where    HIERARCHY_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into HIERARCHY ( HIERARCHY_ID,DIMENSION_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_HIERARCHYNAME = "select count(*) from HIERARCHY where    VIS_ID = ? AND DIMENSION_ID = ? and not(    HIERARCHY_ID = ? )";
/*      */   protected static final String SQL_STORE = "update HIERARCHY set DIMENSION_ID = ?,VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    HIERARCHY_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from HIERARCHY where HIERARCHY_ID = ?";
/*  495 */   protected static String SQL_ALL_HIERARCHYS = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,HIERARCHY.DESCRIPTION      ,DIMENSION.TYPE from HIERARCHY    ,DIMENSION    ,MODEL_DIMENSION_REL    ,MODEL where 1=1   and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  HIERARCHY.DIMENSION_ID = MODEL_DIMENSION_REL.DIMENSION_ID (+) and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID (+) order by MODEL.VIS_ID, DIMENSION.VIS_ID, HIERARCHY.VIS_ID";
/*      */   protected static String SQL_ALL_HIERARCHYS_FOR_USER = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,HIERARCHY.DESCRIPTION      ,DIMENSION.TYPE from HIERARCHY    ,DIMENSION    ,MODEL_DIMENSION_REL    ,MODEL where model.model_id in (select distinct model_id from budget_user where user_id = ?)  and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  HIERARCHY.DIMENSION_ID = MODEL_DIMENSION_REL.DIMENSION_ID (+) and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID (+) order by MODEL.VIS_ID, DIMENSION.VIS_ID, HIERARCHY.VIS_ID";
			 protected static String SQL_SELECTED_HIERARCHYS = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,HIERARCHY.DESCRIPTION      ,DIMENSION.TYPE,   COMPANIES    from HIERARCHY    ,DIMENSION    ,MODEL_DIMENSION_REL    ,MODEL , MAPPED_HIERARCHY where 1=1 and HIERARCHY.HIERARCHY_ID = MAPPED_HIERARCHY.HIERARCHY_ID  and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  HIERARCHY.DIMENSION_ID = MODEL_DIMENSION_REL.DIMENSION_ID (+) and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID (+) order by MODEL.VIS_ID, DIMENSION.VIS_ID, HIERARCHY.VIS_ID";
/*      */ 
/*  640 */   protected static String SQL_IMPORTABLE_HIERARCHIES = "select distinct 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,HIERARCHY.DESCRIPTION from HIERARCHY    ,DIMENSION where 1=1   and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  DIMENSION.DIMENSION_ID = ? order by HIERARCHY.VIS_ID";
/*      */ 
/*  754 */   protected static String SQL_HIERARCY_DETAILS_FROM_DIM_ID = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.DIMENSION_ID      ,HIERARCHY.VIS_ID      ,HIERARCHY.DESCRIPTION from HIERARCHY    ,DIMENSION where 1=1   and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  HIERARCHY.DIMENSION_ID = ?";
/*      */ 
/*  876 */   protected static String SQL_HIERARCY_DETAILS_INC_ROOT_NODE_FROM_DIM_ID = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.DIMENSION_ID      ,HIERARCHY.VIS_ID      ,HIERARCHY.DESCRIPTION      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID from HIERARCHY    ,DIMENSION    ,STRUCTURE_ELEMENT where 1=1   and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  HIERARCHY.DIMENSION_ID = ? and HIERARCHY.HIERARCHY_ID = STRUCTURE_ELEMENT.STRUCTURE_ID and STRUCTURE_ELEMENT.DEPTH = 0";
/*      */ 
/* 1019 */   protected static String SQL_CALENDAR_FOR_MODEL = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,HIERARCHY.HIERARCHY_ID from HIERARCHY    ,DIMENSION    ,MODEL    ,MODEL_DIMENSION_REL where 1=1   and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  MODEL.MODEL_ID = ? and MODEL.MODEL_ID = MODEL_DIMENSION_REL.MODEL_ID and MODEL_DIMENSION_REL.DIMENSION_ID = DIMENSION.DIMENSION_ID and DIMENSION.TYPE = 3 and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID";
/*      */ 
/* 1165 */   protected static String SQL_CALENDAR_FOR_MODEL_VIS_ID = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,HIERARCHY.HIERARCHY_ID from HIERARCHY    ,DIMENSION    ,MODEL    ,MODEL_DIMENSION_REL where 1=1   and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  MODEL.VIS_ID = ? and MODEL.MODEL_ID = MODEL_DIMENSION_REL.MODEL_ID and MODEL_DIMENSION_REL.DIMENSION_ID = DIMENSION.DIMENSION_ID and DIMENSION.TYPE = 3 and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID";
/*      */ 
/* 1311 */   protected static String SQL_CALENDAR_FOR_FINANCE_CUBE = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,HIERARCHY.HIERARCHY_ID from HIERARCHY    ,DIMENSION    ,FINANCE_CUBE    ,MODEL    ,MODEL_DIMENSION_REL where 1=1   and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  FINANCE_CUBE.FINANCE_CUBE_ID = ? and MODEL.MODEL_ID = FINANCE_CUBE.MODEL_ID and MODEL.MODEL_ID = MODEL_DIMENSION_REL.MODEL_ID and MODEL_DIMENSION_REL.DIMENSION_ID = DIMENSION.DIMENSION_ID and DIMENSION.TYPE = 3 and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from HIERARCHY where    HIERARCHY_ID = ? ";
/* 1617 */   private static String[][] SQL_DELETE_CHILDREN = { { "HIERARCHY_ELEMENT", "delete from HIERARCHY_ELEMENT where     HIERARCHY_ELEMENT.HIERARCHY_ID = ? " }, { "AUG_HIERARCHY_ELEMENT", "delete from AUG_HIERARCHY_ELEMENT where     AUG_HIERARCHY_ELEMENT.HIERARCHY_ID = ? " } };
/*      */ 
/* 1631 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "HIERARCHY_ELEMENT_FEED", "delete from HIERARCHY_ELEMENT_FEED HierarchyElementFeed where exists (select * from HIERARCHY_ELEMENT_FEED,HIERARCHY_ELEMENT,HIERARCHY where     HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID = HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID and HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HierarchyElementFeed.HIERARCHY_ELEMENT_ID = HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID " } };
/*      */ 
/* 1646 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and HIERARCHY.HIERARCHY_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from HIERARCHY where 1=1 and HIERARCHY.DIMENSION_ID = ? order by  HIERARCHY.HIERARCHY_ID";
/*      */   protected static final String SQL_GET_ALL = " from HIERARCHY where    DIMENSION_ID = ? ";
/*      */   private static final String sQUERY_DUPLICTAE_HIERARCHY_ELEMENTS = "with se as\n(\n select /*+ materialize */ structure_id, structure_element_id, vis_id \n  from structure_element_view \n  where structure_id = ?\n )\nselect structure_id, structure_element_id, vis_id\nfrom se \nwhere (structure_id, structure_element_id) \nin \n(  \n  select structure_id, structure_element_id \n  from se \n  group by structure_id, structure_element_id\n  having count(*) > 1\n)";
/*      */   private static final String PRIME_HIERARCHY_SQL = "{ call DIMENSION_UTILS.INIT_HIERARCHY( ? ) }";
/*      */   private static final String QUERY_HIERARCHIES_WITH_DIMENSION_ELEMENT_ID = "select distinct de.dimension_id, h.hierarchy_id\nfrom dimension_element de, \n     hierarchy h,\n     hierarchy_element he,\n     hierarchy_element_feed hef\nwhere h.dimension_id = de.dimension_id \n  and h.hierarchy_id = he.hierarchy_id\n  and hef.hierarchy_element_id = he.hierarchy_element_id\n  and de.dimension_element_id = hef.dimension_element_id\n  and de.dimension_id = ?\n  and de.dimension_element_id = ?";
/* 2304 */   private static final JdbcUtils.ColType[] QUERY_HIERARCHIES_WITH_DIMENSION_ELEMENT_ID_COL_INFO = { new JdbcUtils.ColType("dimensionId", "dimension_id", 0), new JdbcUtils.ColType("hierarchyId", "hierarchy_id", 0) };
/*      */   private static final String QUERY_HIERARCHIES_WITH_DIMENSION_ELEMENT_VIS_ID = "select distinct de.dimension_id, h.hierarchy_id\nfrom dimension_element de,\n     hierarchy h,\n     hierarchy_element he,\n     hierarchy_element_feed hef\nwhere h.dimension_id = de.dimension_id\n  and h.hierarchy_id = he.hierarchy_id\n  and hef.hierarchy_element_id = he.hierarchy_element_id\n  and de.dimension_element_id = hef.dimension_element_id\n  and de.dimension_id = ?\n  and de.vis_id = ?";
/* 2365 */   private static final JdbcUtils.ColType[] QUERY_HIERARCHIES_WITH_DIMENSION_ELEMENT_VIS_ID_COL_INFO = { new JdbcUtils.ColType("dimensionId", "dimension_id", 0), new JdbcUtils.ColType("hierarchyId", "hierarchy_id", 0) };
/*      */   protected HierarchyElementDAO mHierarchyElementDAO;
/*      */   protected AugHierarchyElementDAO mAugHierarchyElementDAO;
/*      */   protected HierarchyEVO mDetails;
/*      */ 
/*      */   public HierarchyDAO(Connection connection)
/*      */   {
/*   70 */     super(connection);
/*      */   }
/*      */ 
/*      */   public HierarchyDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public HierarchyDAO(DataSource ds)
/*      */   {
/*   86 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected HierarchyPK getPK()
/*      */   {
/*   94 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(HierarchyEVO details)
/*      */   {
/*  103 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private HierarchyEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  122 */     int col = 1;
/*  123 */     HierarchyEVO evo = new HierarchyEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), null, null);
/*      */ 
/*  133 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  134 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  135 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  136 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(HierarchyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  141 */     int col = startCol_;
/*  142 */     stmt_.setInt(col++, evo_.getHierarchyId());
/*  143 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(HierarchyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  148 */     int col = startCol_;
/*  149 */     stmt_.setInt(col++, evo_.getDimensionId());
/*  150 */     stmt_.setString(col++, evo_.getVisId());
/*  151 */     stmt_.setString(col++, evo_.getDescription());
/*  152 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  153 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  154 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  155 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  156 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(HierarchyPK pk)
/*      */     throws ValidationException
/*      */   {
/*  172 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  174 */     PreparedStatement stmt = null;
/*  175 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  179 */       stmt = getConnection().prepareStatement("select HIERARCHY.HIERARCHY_ID,HIERARCHY.DIMENSION_ID,HIERARCHY.VIS_ID,HIERARCHY.DESCRIPTION,HIERARCHY.VERSION_NUM,HIERARCHY.UPDATED_BY_USER_ID,HIERARCHY.UPDATED_TIME,HIERARCHY.CREATED_TIME from HIERARCHY where    HIERARCHY_ID = ? ");
/*      */ 
/*  182 */       int col = 1;
/*  183 */       stmt.setInt(col++, pk.getHierarchyId());
/*      */ 
/*  185 */       resultSet = stmt.executeQuery();
/*      */ 
/*  187 */       if (!resultSet.next()) {
/*  188 */         throw new ValidationException(new StringBuilder().append(getEntityName()).append(" select of ").append(pk).append(" not found").toString());
/*      */       }
/*      */ 
/*  191 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  192 */       if (this.mDetails.isModified())
/*  193 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  197 */       throw handleSQLException(pk, "select HIERARCHY.HIERARCHY_ID,HIERARCHY.DIMENSION_ID,HIERARCHY.VIS_ID,HIERARCHY.DESCRIPTION,HIERARCHY.VERSION_NUM,HIERARCHY.UPDATED_BY_USER_ID,HIERARCHY.UPDATED_TIME,HIERARCHY.CREATED_TIME from HIERARCHY where    HIERARCHY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  201 */       closeResultSet(resultSet);
/*  202 */       closeStatement(stmt);
/*  203 */       closeConnection();
/*      */ 
/*  205 */       if (timer != null)
/*  206 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  239 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  240 */     this.mDetails.postCreateInit();
/*      */ 
/*  242 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  247 */       duplicateValueCheckHierarchyName();
/*      */ 
/*  249 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  250 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  251 */       stmt = getConnection().prepareStatement("insert into HIERARCHY ( HIERARCHY_ID,DIMENSION_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");
/*      */ 
/*  254 */       int col = 1;
/*  255 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  256 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  259 */       int resultCount = stmt.executeUpdate();
/*  260 */       if (resultCount != 1)
/*      */       {
/*  262 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" insert failed (").append(this.mDetails.getPK()).append("): resultCount=").append(resultCount).toString());
/*      */       }
/*      */ 
/*  265 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  269 */       throw handleSQLException(this.mDetails.getPK(), "insert into HIERARCHY ( HIERARCHY_ID,DIMENSION_ID,VIS_ID,DESCRIPTION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  273 */       closeStatement(stmt);
/*  274 */       closeConnection();
/*      */ 
/*  276 */       if (timer != null) {
/*  277 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  283 */       getHierarchyElementDAO().update(this.mDetails.getHierarchyElementsMap());
/*      */ 
/*  285 */       getAugHierarchyElementDAO().update(this.mDetails.getAugHierarchyElementsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  291 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckHierarchyName()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  307 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  308 */     PreparedStatement stmt = null;
/*  309 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  313 */       stmt = getConnection().prepareStatement("select count(*) from HIERARCHY where    VIS_ID = ? AND DIMENSION_ID = ? and not(    HIERARCHY_ID = ? )");
/*      */ 
/*  316 */       int col = 1;
/*  317 */       stmt.setString(col++, this.mDetails.getVisId());
/*  318 */       stmt.setInt(col++, this.mDetails.getDimensionId());
/*  319 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  322 */       resultSet = stmt.executeQuery();
/*      */ 
/*  324 */       if (!resultSet.next()) {
/*  325 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" select of ").append(getPK()).append(" not found").toString());
/*      */       }
/*      */ 
/*  329 */       col = 1;
/*  330 */       int count = resultSet.getInt(col++);
/*  331 */       if (count > 0) {
/*  332 */         throw new DuplicateNameValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" HierarchyName").toString());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  338 */       throw handleSQLException(getPK(), "select count(*) from HIERARCHY where    VIS_ID = ? AND DIMENSION_ID = ? and not(    HIERARCHY_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  342 */       closeResultSet(resultSet);
/*  343 */       closeStatement(stmt);
/*  344 */       closeConnection();
/*      */ 
/*  346 */       if (timer != null)
/*  347 */         timer.logDebug("duplicateValueCheckHierarchyName", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  373 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  376 */     this._log.info("doStore()", this.mDetails);
/*      */ 
/*  378 */     PreparedStatement stmt = null;
/*      */ 
/*  380 */     boolean mainChanged = this.mDetails.isModified();
/*  381 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  385 */       if (mainChanged) {
/*  386 */         duplicateValueCheckHierarchyName();
/*      */       }
/*  388 */       if (getHierarchyElementDAO().update(this.mDetails.getHierarchyElementsMap())) {
/*  389 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  392 */       if (getAugHierarchyElementDAO().update(this.mDetails.getAugHierarchyElementsMap())) {
/*  393 */         dependantChanged = true;
/*      */       }
/*  395 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  398 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  401 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  402 */         stmt = getConnection().prepareStatement("update HIERARCHY set DIMENSION_ID = ?,VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    HIERARCHY_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  405 */         int col = 1;
/*  406 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  407 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  409 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  412 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  414 */         if (resultCount == 0) {
/*  415 */           checkVersionNum();
/*      */         }
/*  417 */         if (resultCount != 1) {
/*  418 */           throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" update failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
/*      */         }
/*      */ 
/*  421 */         this.mDetails.reset();
/*      */ 
/*  424 */         primeHierarchyRuntime();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  431 */       throw handleSQLException(getPK(), "update HIERARCHY set DIMENSION_ID = ?,VIS_ID = ?,DESCRIPTION = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    HIERARCHY_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  435 */       closeStatement(stmt);
/*  436 */       closeConnection();
/*      */ 
/*  438 */       if ((timer != null) && (
/*  439 */         (mainChanged) || (dependantChanged)))
/*  440 */         timer.logDebug("store", new StringBuilder().append(this.mDetails.getPK()).append("(").append(mainChanged).append(",").append(dependantChanged).append(")").toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  452 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  453 */     PreparedStatement stmt = null;
/*  454 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  458 */       stmt = getConnection().prepareStatement("select VERSION_NUM from HIERARCHY where HIERARCHY_ID = ?");
/*      */ 
/*  461 */       int col = 1;
/*  462 */       stmt.setInt(col++, this.mDetails.getHierarchyId());
/*      */ 
/*  465 */       resultSet = stmt.executeQuery();
/*      */ 
/*  467 */       if (!resultSet.next()) {
/*  468 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" checkVersionNum: select of ").append(getPK()).append(" not found").toString());
/*      */       }
/*      */ 
/*  471 */       col = 1;
/*  472 */       int dbVersionNumber = resultSet.getInt(col++);
/*  473 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  474 */         throw new VersionValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" expected:").append(this.mDetails.getVersionNum() - 1).append(" found:").append(dbVersionNumber).toString());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  480 */       throw handleSQLException(getPK(), "select VERSION_NUM from HIERARCHY where HIERARCHY_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  484 */       closeStatement(stmt);
/*  485 */       closeResultSet(resultSet);
/*      */ 
/*  487 */       if (timer != null)
/*  488 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllHierarchysELO getAllHierarchys()
/*      */   {
/*  534 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  535 */     PreparedStatement stmt = null;
/*  536 */     ResultSet resultSet = null;
/*  537 */     AllHierarchysELO results = new AllHierarchysELO();
/*      */     try
/*      */     {
/*  540 */       stmt = getConnection().prepareStatement(SQL_ALL_HIERARCHYS);
/*  541 */       int col = 1;
/*  542 */       resultSet = stmt.executeQuery();
/*  543 */       while (resultSet.next())
/*      */       {
/*  545 */         col = 2;
/*      */ 
/*  548 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  551 */         String textDimension = resultSet.getString(col++);
/*  552 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/*  555 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/*  558 */         String textHierarchy = resultSet.getString(col++);
/*      */ 
/*  561 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  565 */         String textModelDimensionRel = "";
/*      */ 
/*  567 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  570 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  574 */         HierarchyCK ckHierarchy = new HierarchyCK(pkDimension, pkHierarchy);
/*      */ 
/*  580 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/*  587 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(ckHierarchy, textHierarchy);
/*      */ 
/*  593 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
/*      */ 
/*  599 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  604 */         String col1 = resultSet.getString(col++);
/*  605 */         int col2 = resultSet.getInt(col++);
/*      */ 
/*  608 */         results.add(erHierarchy, erDimension, erModelDimensionRel, erModel, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  620 */       throw handleSQLException(SQL_ALL_HIERARCHYS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  624 */       closeResultSet(resultSet);
/*  625 */       closeStatement(stmt);
/*  626 */       closeConnection();
/*      */     }
/*      */ 
/*  629 */     if (timer != null) {
/*  630 */       timer.logDebug("getAllHierarchys", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  634 */     return results;
/*      */   }

	public SelectedHierarchysELO getSelectedHierarchys()	{
		Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		SelectedHierarchysELO results = new SelectedHierarchysELO();
		try	{
			stmt = getConnection().prepareStatement(SQL_SELECTED_HIERARCHYS);
			int col = 1;
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				col = 2;

				DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));

				String textDimension = resultSet.getString(col++);
				int erDimensionType = resultSet.getInt(col++);

				HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));

				String textHierarchy = resultSet.getString(col++);

				ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));

				String textModelDimensionRel = "";

				ModelPK pkModel = new ModelPK(resultSet.getInt(col++));

				String textModel = resultSet.getString(col++);

				HierarchyCK ckHierarchy = new HierarchyCK(pkDimension, pkHierarchy);

				DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);

				HierarchyRefImpl erHierarchy = new HierarchyRefImpl(ckHierarchy, textHierarchy);

				ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);

				ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);

				String col1 = resultSet.getString(col++);
				int col2 = resultSet.getInt(col++);
				
				String companies = resultSet.getString(col++);

				results.add(erHierarchy, erDimension, erModelDimensionRel, erModel, col1, col2, companies);
			}
		} catch (SQLException sqle)	{
			throw handleSQLException(SQL_SELECTED_HIERARCHYS, sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}

		if (timer != null) {
			timer.logDebug("getSelectedHierarchys", new StringBuilder().append(" items=").append(results.size()).toString());
		}

		return results;
	}

/*      */   public ImportableHierarchiesELO getImportableHierarchies(int param1)
/*      */   {
/*  673 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  674 */     PreparedStatement stmt = null;
/*  675 */     ResultSet resultSet = null;
/*  676 */     ImportableHierarchiesELO results = new ImportableHierarchiesELO();
/*      */     try
/*      */     {
/*  679 */       stmt = getConnection().prepareStatement(SQL_IMPORTABLE_HIERARCHIES);
/*  680 */       int col = 1;
/*  681 */       stmt.setInt(col++, param1);
/*  682 */       resultSet = stmt.executeQuery();
/*  683 */       while (resultSet.next())
/*      */       {
/*  685 */         col = 2;
/*      */ 
/*  688 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  691 */         String textDimension = resultSet.getString(col++);
/*  692 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/*  695 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/*  698 */         String textHierarchy = resultSet.getString(col++);
/*      */ 
/*  703 */         HierarchyCK ckHierarchy = new HierarchyCK(pkDimension, pkHierarchy);
/*      */ 
/*  709 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/*  716 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(ckHierarchy, textHierarchy);
/*      */ 
/*  721 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  724 */         results.add(erHierarchy, erDimension, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  733 */       throw handleSQLException(SQL_IMPORTABLE_HIERARCHIES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  737 */       closeResultSet(resultSet);
/*  738 */       closeStatement(stmt);
/*  739 */       closeConnection();
/*      */     }
/*      */ 
/*  742 */     if (timer != null) {
/*  743 */       timer.logDebug("getImportableHierarchies", new StringBuilder().append(" DimensionId=").append(param1).append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  748 */     return results;
/*      */   }
/*      */ 
/*      */   public HierarcyDetailsFromDimIdELO getHierarcyDetailsFromDimId(int param1)
/*      */   {
/*  789 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  790 */     PreparedStatement stmt = null;
/*  791 */     ResultSet resultSet = null;
/*  792 */     HierarcyDetailsFromDimIdELO results = new HierarcyDetailsFromDimIdELO();
/*      */     try
/*      */     {
/*  795 */       stmt = getConnection().prepareStatement(SQL_HIERARCY_DETAILS_FROM_DIM_ID);
/*  796 */       int col = 1;
/*  797 */       stmt.setInt(col++, param1);
/*  798 */       resultSet = stmt.executeQuery();
/*  799 */       while (resultSet.next())
/*      */       {
/*  801 */         col = 2;
/*      */ 
/*  804 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  807 */         String textDimension = resultSet.getString(col++);
/*  808 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/*  811 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/*  814 */         String textHierarchy = resultSet.getString(col++);
/*      */ 
/*  819 */         HierarchyCK ckHierarchy = new HierarchyCK(pkDimension, pkHierarchy);
/*      */ 
/*  825 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/*  832 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(ckHierarchy, textHierarchy);
/*      */ 
/*  837 */         int col1 = resultSet.getInt(col++);
/*  838 */         int col2 = resultSet.getInt(col++);
/*  839 */         String col3 = resultSet.getString(col++);
/*  840 */         String col4 = resultSet.getString(col++);
/*      */ 
/*  843 */         results.add(erHierarchy, erDimension, col1, col2, col3, col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  855 */       throw handleSQLException(SQL_HIERARCY_DETAILS_FROM_DIM_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  859 */       closeResultSet(resultSet);
/*  860 */       closeStatement(stmt);
/*  861 */       closeConnection();
/*      */     }
/*      */ 
/*  864 */     if (timer != null) {
/*  865 */       timer.logDebug("getHierarcyDetailsFromDimId", new StringBuilder().append(" DimensionId=").append(param1).append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  870 */     return results;
/*      */   }
/*      */ 
/*      */   public HierarcyDetailsIncRootNodeFromDimIdELO getHierarcyDetailsIncRootNodeFromDimId(int param1)
/*      */   {
/*  917 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  918 */     PreparedStatement stmt = null;
/*  919 */     ResultSet resultSet = null;
/*  920 */     HierarcyDetailsIncRootNodeFromDimIdELO results = new HierarcyDetailsIncRootNodeFromDimIdELO();
/*      */     try
/*      */     {
/*  923 */       stmt = getConnection().prepareStatement(SQL_HIERARCY_DETAILS_INC_ROOT_NODE_FROM_DIM_ID);
/*  924 */       int col = 1;
/*  925 */       stmt.setInt(col++, param1);
/*  926 */       resultSet = stmt.executeQuery();
/*  927 */       while (resultSet.next())
/*      */       {
/*  929 */         col = 2;
/*      */ 
/*  932 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  935 */         String textDimension = resultSet.getString(col++);
/*  936 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/*  939 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/*  942 */         String textHierarchy = resultSet.getString(col++);
/*      */ 
/*  945 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  949 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/*  953 */         HierarchyCK ckHierarchy = new HierarchyCK(pkDimension, pkHierarchy);
/*      */ 
/*  959 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/*  966 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(ckHierarchy, textHierarchy);
/*      */ 
/*  972 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/*  977 */         int col1 = resultSet.getInt(col++);
/*  978 */         int col2 = resultSet.getInt(col++);
/*  979 */         String col3 = resultSet.getString(col++);
/*  980 */         String col4 = resultSet.getString(col++);
/*  981 */         int col5 = resultSet.getInt(col++);
/*      */ 
/*  984 */         results.add(erHierarchy, erDimension, erStructureElement, col1, col2, col3, col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  998 */       throw handleSQLException(SQL_HIERARCY_DETAILS_INC_ROOT_NODE_FROM_DIM_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1002 */       closeResultSet(resultSet);
/* 1003 */       closeStatement(stmt);
/* 1004 */       closeConnection();
/*      */     }
/*      */ 
/* 1007 */     if (timer != null) {
/* 1008 */       timer.logDebug("getHierarcyDetailsIncRootNodeFromDimId", new StringBuilder().append(" DimensionId=").append(param1).append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/* 1013 */     return results;
/*      */   }
/*      */ 
/*      */   public CalendarForModelELO getCalendarForModel(int param1)
/*      */   {
/* 1059 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1060 */     PreparedStatement stmt = null;
/* 1061 */     ResultSet resultSet = null;
/* 1062 */     CalendarForModelELO results = new CalendarForModelELO();
/*      */     try
/*      */     {
/* 1065 */       stmt = getConnection().prepareStatement(SQL_CALENDAR_FOR_MODEL);
/* 1066 */       int col = 1;
/* 1067 */       stmt.setInt(col++, param1);
/* 1068 */       resultSet = stmt.executeQuery();
/* 1069 */       while (resultSet.next())
/*      */       {
/* 1071 */         col = 2;
/*      */ 
/* 1074 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/* 1077 */         String textDimension = resultSet.getString(col++);
/* 1078 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/* 1081 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/* 1084 */         String textHierarchy = resultSet.getString(col++);
/*      */ 
/* 1087 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1090 */         String textModel = resultSet.getString(col++);
/*      */ 
/* 1092 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1096 */         String textModelDimensionRel = "";
/*      */ 
/* 1100 */         HierarchyCK ckHierarchy = new HierarchyCK(pkDimension, pkHierarchy);
/*      */ 
/* 1106 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/* 1113 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(ckHierarchy, textHierarchy);
/*      */ 
/* 1119 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/* 1125 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
/*      */ 
/* 1130 */         int col1 = resultSet.getInt(col++);
/*      */ 
/* 1133 */         results.add(erHierarchy, erDimension, erModel, erModelDimensionRel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1144 */       throw handleSQLException(SQL_CALENDAR_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1148 */       closeResultSet(resultSet);
/* 1149 */       closeStatement(stmt);
/* 1150 */       closeConnection();
/*      */     }
/*      */ 
/* 1153 */     if (timer != null) {
/* 1154 */       timer.logDebug("getCalendarForModel", new StringBuilder().append(" ModelId=").append(param1).append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/* 1159 */     return results;
/*      */   }
/*      */ 
/*      */   public CalendarForModelVisIdELO getCalendarForModelVisId(String param1)
/*      */   {
/* 1205 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1206 */     PreparedStatement stmt = null;
/* 1207 */     ResultSet resultSet = null;
/* 1208 */     CalendarForModelVisIdELO results = new CalendarForModelVisIdELO();
/*      */     try
/*      */     {
/* 1211 */       stmt = getConnection().prepareStatement(SQL_CALENDAR_FOR_MODEL_VIS_ID);
/* 1212 */       int col = 1;
/* 1213 */       stmt.setString(col++, param1);
/* 1214 */       resultSet = stmt.executeQuery();
/* 1215 */       while (resultSet.next())
/*      */       {
/* 1217 */         col = 2;
/*      */ 
/* 1220 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/* 1223 */         String textDimension = resultSet.getString(col++);
/* 1224 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/* 1227 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/* 1230 */         String textHierarchy = resultSet.getString(col++);
/*      */ 
/* 1233 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1236 */         String textModel = resultSet.getString(col++);
/*      */ 
/* 1238 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1242 */         String textModelDimensionRel = "";
/*      */ 
/* 1246 */         HierarchyCK ckHierarchy = new HierarchyCK(pkDimension, pkHierarchy);
/*      */ 
/* 1252 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/* 1259 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(ckHierarchy, textHierarchy);
/*      */ 
/* 1265 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/* 1271 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
/*      */ 
/* 1276 */         int col1 = resultSet.getInt(col++);
/*      */ 
/* 1279 */         results.add(erHierarchy, erDimension, erModel, erModelDimensionRel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1290 */       throw handleSQLException(SQL_CALENDAR_FOR_MODEL_VIS_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1294 */       closeResultSet(resultSet);
/* 1295 */       closeStatement(stmt);
/* 1296 */       closeConnection();
/*      */     }
/*      */ 
/* 1299 */     if (timer != null) {
/* 1300 */       timer.logDebug("getCalendarForModelVisId", new StringBuilder().append(" VisId=").append(param1).append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/* 1305 */     return results;
/*      */   }
/*      */ 
/*      */   public CalendarForFinanceCubeELO getCalendarForFinanceCube(int param1)
/*      */   {
/* 1355 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1356 */     PreparedStatement stmt = null;
/* 1357 */     ResultSet resultSet = null;
/* 1358 */     CalendarForFinanceCubeELO results = new CalendarForFinanceCubeELO();
/*      */     try
/*      */     {
/* 1361 */       stmt = getConnection().prepareStatement(SQL_CALENDAR_FOR_FINANCE_CUBE);
/* 1362 */       int col = 1;
/* 1363 */       stmt.setInt(col++, param1);
/* 1364 */       resultSet = stmt.executeQuery();
/* 1365 */       while (resultSet.next())
/*      */       {
/* 1367 */         col = 2;
/*      */ 
/* 1370 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/* 1373 */         String textDimension = resultSet.getString(col++);
/* 1374 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/* 1377 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/* 1380 */         String textHierarchy = resultSet.getString(col++);
/*      */ 
/* 1383 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/* 1386 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/* 1388 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1391 */         String textModel = resultSet.getString(col++);
/*      */ 
/* 1393 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1397 */         String textModelDimensionRel = "";
/*      */ 
/* 1401 */         HierarchyCK ckHierarchy = new HierarchyCK(pkDimension, pkHierarchy);
/*      */ 
/* 1407 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/* 1414 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(ckHierarchy, textHierarchy);
/*      */ 
/* 1420 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
/*      */ 
/* 1426 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/* 1432 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
/*      */ 
/* 1437 */         int col1 = resultSet.getInt(col++);
/*      */ 
/* 1440 */         results.add(erHierarchy, erDimension, erFinanceCube, erModel, erModelDimensionRel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1452 */       throw handleSQLException(SQL_CALENDAR_FOR_FINANCE_CUBE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1456 */       closeResultSet(resultSet);
/* 1457 */       closeStatement(stmt);
/* 1458 */       closeConnection();
/*      */     }
/*      */ 
/* 1461 */     if (timer != null) {
/* 1462 */       timer.logDebug("getCalendarForFinanceCube", new StringBuilder().append(" FinanceCubeId=").append(param1).append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/* 1467 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/* 1484 */     if (items == null) {
/* 1485 */       return false;
/*      */     }
/* 1487 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1488 */     PreparedStatement deleteStmt = null;
/*      */ 
/* 1490 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/* 1494 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/* 1495 */       while (iter3.hasNext())
/*      */       {
/* 1497 */         this.mDetails = ((HierarchyEVO)iter3.next());
/* 1498 */         if (!this.mDetails.deletePending())
/*      */           continue;
/* 1500 */         somethingChanged = true;
/*      */ 
/* 1503 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/* 1507 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 1508 */       while (iter2.hasNext())
/*      */       {
/* 1510 */         this.mDetails = ((HierarchyEVO)iter2.next());
/*      */ 
/* 1513 */         if (!this.mDetails.deletePending())
/*      */           continue;
/* 1515 */         somethingChanged = true;
/*      */ 
/* 1518 */         if (deleteStmt == null) {
/* 1519 */           deleteStmt = getConnection().prepareStatement("delete from HIERARCHY where    HIERARCHY_ID = ? ");
/*      */         }
/*      */ 
/* 1522 */         int col = 1;
/* 1523 */         deleteStmt.setInt(col++, this.mDetails.getHierarchyId());
/*      */ 
/* 1525 */         if (this._log.isDebugEnabled()) {
/* 1526 */           this._log.debug("update", new StringBuilder().append("Hierarchy deleting HierarchyId=").append(this.mDetails.getHierarchyId()).toString());
/*      */         }
/*      */ 
/* 1531 */         deleteStmt.addBatch();
/*      */ 
/* 1534 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/* 1539 */       if (deleteStmt != null)
/*      */       {
/* 1541 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1543 */         deleteStmt.executeBatch();
/*      */ 
/* 1545 */         if (timer2 != null) {
/* 1546 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/* 1550 */       Iterator iter1 = items.values().iterator();
/* 1551 */       while (iter1.hasNext())
/*      */       {
/* 1553 */         this.mDetails = ((HierarchyEVO)iter1.next());
/*      */ 
/* 1555 */         if (this.mDetails.insertPending())
/*      */         {
/* 1557 */           somethingChanged = true;
/* 1558 */           doCreate();
/*      */         }
/* 1561 */         else if (this.mDetails.isModified())
/*      */         {
/* 1563 */           somethingChanged = true;
/* 1564 */           doStore();
/*      */         }
/* 1568 */         else if (!this.mDetails.deletePending())
/*      */         {
/* 1574 */           if (getHierarchyElementDAO().update(this.mDetails.getHierarchyElementsMap())) {
/* 1575 */             somethingChanged = true;
/*      */           }
/*      */ 
/* 1578 */           if (getAugHierarchyElementDAO().update(this.mDetails.getAugHierarchyElementsMap())) {
/* 1579 */             somethingChanged = true;
/*      */           }
/*      */         }
/*      */ 
/* 1583 */         if (!somethingChanged)
/*      */           continue;
/* 1585 */         primeHierarchyRuntime();
/*      */       }
/*      */ 
/* 1595 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1599 */       throw handleSQLException("delete from HIERARCHY where    HIERARCHY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1603 */       if (deleteStmt != null)
/*      */       {
/* 1605 */         closeStatement(deleteStmt);
/* 1606 */         closeConnection();
/*      */       }
/*      */ 
/* 1609 */       this.mDetails = null;
/*      */ 
/* 1611 */       if ((somethingChanged) && 
/* 1612 */         (timer != null))
/* 1613 */         timer.logDebug("update", "collection"); 
/* 1613 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(HierarchyPK pk)
/*      */   {
/* 1655 */     Set emptyStrings = Collections.emptySet();
/* 1656 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(HierarchyPK pk, Set<String> exclusionTables)
/*      */   {
/* 1662 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1664 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1666 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1668 */       PreparedStatement stmt = null;
/*      */ 
/* 1670 */       int resultCount = 0;
/* 1671 */       String s = null;
/*      */       try
/*      */       {
/* 1674 */         s = new StringBuilder().append(SQL_DELETE_CHILDRENS_DEPENDANTS[i][1]).append(SQL_DELETE_DEPENDANT_CRITERIA).toString();
/*      */ 
/* 1676 */         if (this._log.isDebugEnabled()) {
/* 1677 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1679 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1682 */         int col = 1;
/* 1683 */         stmt.setInt(col++, pk.getHierarchyId());
/*      */ 
/* 1686 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1690 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1694 */         closeStatement(stmt);
/* 1695 */         closeConnection();
/*      */ 
/* 1697 */         if (timer != null) {
/* 1698 */           timer.logDebug("deleteDependants", new StringBuilder().append("A[").append(i).append("] count=").append(resultCount).toString());
/*      */         }
/*      */       }
/*      */     }
/* 1702 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1704 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1706 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1708 */       PreparedStatement stmt = null;
/*      */ 
/* 1710 */       int resultCount = 0;
/* 1711 */       String s = null;
/*      */       try
/*      */       {
/* 1714 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1716 */         if (this._log.isDebugEnabled()) {
/* 1717 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1719 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1722 */         int col = 1;
/* 1723 */         stmt.setInt(col++, pk.getHierarchyId());
/*      */ 
/* 1726 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1730 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1734 */         closeStatement(stmt);
/* 1735 */         closeConnection();
/*      */ 
/* 1737 */         if (timer != null)
/* 1738 */           timer.logDebug("deleteDependants", new StringBuilder().append("B[").append(i).append("] count=").append(resultCount).toString());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(DimensionPK entityPK, DimensionEVO owningEVO, String dependants)
/*      */   {
/* 1758 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1760 */     PreparedStatement stmt = null;
/* 1761 */     ResultSet resultSet = null;
/*      */ 
/* 1763 */     int itemCount = 0;
/*      */ 
/* 1765 */     Collection theseItems = new ArrayList();
/* 1766 */     owningEVO.setHierarchies(theseItems);
/* 1767 */     owningEVO.setHierarchiesAllItemsLoaded(true);
/*      */     try
/*      */     {
/* 1771 */       stmt = getConnection().prepareStatement("select HIERARCHY.HIERARCHY_ID,HIERARCHY.DIMENSION_ID,HIERARCHY.VIS_ID,HIERARCHY.DESCRIPTION,HIERARCHY.VERSION_NUM,HIERARCHY.UPDATED_BY_USER_ID,HIERARCHY.UPDATED_TIME,HIERARCHY.CREATED_TIME from HIERARCHY where 1=1 and HIERARCHY.DIMENSION_ID = ? order by  HIERARCHY.HIERARCHY_ID");
/*      */ 
/* 1773 */       int col = 1;
/* 1774 */       stmt.setInt(col++, entityPK.getDimensionId());
/*      */ 
/* 1776 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1779 */       while (resultSet.next())
/*      */       {
/* 1781 */         itemCount++;
/* 1782 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1784 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/* 1787 */       if (timer != null) {
/* 1788 */         timer.logDebug("bulkGetAll", new StringBuilder().append("items=").append(itemCount).toString());
/*      */       }
/*      */ 
/* 1791 */       if ((itemCount > 0) && (dependants.indexOf("<4>") > -1))
/*      */       {
/* 1793 */         getHierarchyElementDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/* 1795 */       if ((itemCount > 0) && (dependants.indexOf("<6>") > -1))
/*      */       {
/* 1797 */         getAugHierarchyElementDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/* 1801 */       throw handleSQLException("select HIERARCHY.HIERARCHY_ID,HIERARCHY.DIMENSION_ID,HIERARCHY.VIS_ID,HIERARCHY.DESCRIPTION,HIERARCHY.VERSION_NUM,HIERARCHY.UPDATED_BY_USER_ID,HIERARCHY.UPDATED_TIME,HIERARCHY.CREATED_TIME from HIERARCHY where 1=1 and HIERARCHY.DIMENSION_ID = ? order by  HIERARCHY.HIERARCHY_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1805 */       closeResultSet(resultSet);
/* 1806 */       closeStatement(stmt);
/* 1807 */       closeConnection();
/*      */ 
/* 1809 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectDimensionId, String dependants, Collection currentList)
/*      */   {
/* 1834 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1835 */     PreparedStatement stmt = null;
/* 1836 */     ResultSet resultSet = null;
/*      */ 
/* 1838 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/* 1842 */       stmt = getConnection().prepareStatement("select HIERARCHY.HIERARCHY_ID,HIERARCHY.DIMENSION_ID,HIERARCHY.VIS_ID,HIERARCHY.DESCRIPTION,HIERARCHY.VERSION_NUM,HIERARCHY.UPDATED_BY_USER_ID,HIERARCHY.UPDATED_TIME,HIERARCHY.CREATED_TIME from HIERARCHY where    DIMENSION_ID = ? ");
/*      */ 
/* 1844 */       int col = 1;
/* 1845 */       stmt.setInt(col++, selectDimensionId);
/*      */ 
/* 1847 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1849 */       while (resultSet.next())
/*      */       {
/* 1851 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1854 */         getDependants(this.mDetails, dependants);
/*      */ 
/* 1857 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1860 */       if (currentList != null)
/*      */       {
/* 1863 */         ListIterator iter = items.listIterator();
/* 1864 */         HierarchyEVO currentEVO = null;
/* 1865 */         HierarchyEVO newEVO = null;
/* 1866 */         while (iter.hasNext())
/*      */         {
/* 1868 */           newEVO = (HierarchyEVO)iter.next();
/* 1869 */           Iterator iter2 = currentList.iterator();
/* 1870 */           while (iter2.hasNext())
/*      */           {
/* 1872 */             currentEVO = (HierarchyEVO)iter2.next();
/* 1873 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1875 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1881 */         Iterator iter2 = currentList.iterator();
/* 1882 */         while (iter2.hasNext())
/*      */         {
/* 1884 */           currentEVO = (HierarchyEVO)iter2.next();
/* 1885 */           if (currentEVO.insertPending()) {
/* 1886 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1890 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1894 */       throw handleSQLException("select HIERARCHY.HIERARCHY_ID,HIERARCHY.DIMENSION_ID,HIERARCHY.VIS_ID,HIERARCHY.DESCRIPTION,HIERARCHY.VERSION_NUM,HIERARCHY.UPDATED_BY_USER_ID,HIERARCHY.UPDATED_TIME,HIERARCHY.CREATED_TIME from HIERARCHY where    DIMENSION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1898 */       closeResultSet(resultSet);
/* 1899 */       closeStatement(stmt);
/* 1900 */       closeConnection();
/*      */ 
/* 1902 */       if (timer != null) {
/* 1903 */         timer.logDebug("getAll", new StringBuilder().append(" DimensionId=").append(selectDimensionId).append(" items=").append(items.size()).toString());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1908 */     return items;
/*      */   }
/*      */ 
/*      */   public HierarchyEVO getDetails(DimensionCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1929 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1932 */     if (this.mDetails == null) {
/* 1933 */       doLoad(((HierarchyCK)paramCK).getHierarchyPK());
/*      */     }
/* 1935 */     else if (!this.mDetails.getPK().equals(((HierarchyCK)paramCK).getHierarchyPK())) {
/* 1936 */       doLoad(((HierarchyCK)paramCK).getHierarchyPK());
/*      */     }
/*      */ 
/* 1939 */     if ((dependants.indexOf("<4>") > -1) && (!this.mDetails.isHierarchyElementsAllItemsLoaded()))
/*      */     {
/* 1944 */       this.mDetails.setHierarchyElements(getHierarchyElementDAO().getAll(this.mDetails.getHierarchyId(), dependants, this.mDetails.getHierarchyElements()));
/*      */ 
/* 1951 */       this.mDetails.setHierarchyElementsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1955 */     if ((dependants.indexOf("<6>") > -1) && (!this.mDetails.isAugHierarchyElementsAllItemsLoaded()))
/*      */     {
/* 1960 */       this.mDetails.setAugHierarchyElements(getAugHierarchyElementDAO().getAll(this.mDetails.getHierarchyId(), dependants, this.mDetails.getAugHierarchyElements()));
/*      */ 
/* 1967 */       this.mDetails.setAugHierarchyElementsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1970 */     if ((paramCK instanceof HierarchyElementCK))
/*      */     {
/* 1972 */       if (this.mDetails.getHierarchyElements() == null) {
/* 1973 */         this.mDetails.loadHierarchyElementsItem(getHierarchyElementDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1976 */         HierarchyElementPK pk = ((HierarchyElementCK)paramCK).getHierarchyElementPK();
/* 1977 */         HierarchyElementEVO evo = this.mDetails.getHierarchyElementsItem(pk);
/* 1978 */         if (evo == null)
/* 1979 */           this.mDetails.loadHierarchyElementsItem(getHierarchyElementDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1981 */           getHierarchyElementDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/* 1985 */     else if ((paramCK instanceof AugHierarchyElementCK))
/*      */     {
/* 1987 */       if (this.mDetails.getAugHierarchyElements() == null) {
/* 1988 */         this.mDetails.loadAugHierarchyElementsItem(getAugHierarchyElementDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1991 */         AugHierarchyElementPK pk = ((AugHierarchyElementCK)paramCK).getAugHierarchyElementPK();
/* 1992 */         AugHierarchyElementEVO evo = this.mDetails.getAugHierarchyElementsItem(pk);
/* 1993 */         if (evo == null) {
/* 1994 */           this.mDetails.loadAugHierarchyElementsItem(getAugHierarchyElementDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1999 */     HierarchyEVO details = new HierarchyEVO();
/* 2000 */     details = this.mDetails.deepClone();
/*      */ 
/* 2002 */     if (timer != null) {
/* 2003 */       timer.logDebug("getDetails", new StringBuilder().append(paramCK).append(" ").append(dependants).toString());
/*      */     }
/* 2005 */     return details;
/*      */   }
/*      */ 
/*      */   public HierarchyEVO getDetails(DimensionCK paramCK, HierarchyEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 2011 */     HierarchyEVO savedEVO = this.mDetails;
/* 2012 */     this.mDetails = paramEVO;
/* 2013 */     HierarchyEVO newEVO = getDetails(paramCK, dependants);
/* 2014 */     this.mDetails = savedEVO;
/* 2015 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public HierarchyEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 2021 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 2025 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 2028 */     HierarchyEVO details = this.mDetails.deepClone();
/*      */ 
/* 2030 */     if (timer != null) {
/* 2031 */       timer.logDebug("getDetails", new StringBuilder().append(this.mDetails.getPK()).append(" ").append(dependants).toString());
/*      */     }
/* 2033 */     return details;
/*      */   }
/*      */ 
/*      */   protected HierarchyElementDAO getHierarchyElementDAO()
/*      */   {
/* 2042 */     if (this.mHierarchyElementDAO == null)
/*      */     {
/* 2044 */       if (this.mDataSource != null)
/* 2045 */         this.mHierarchyElementDAO = new HierarchyElementDAO(this.mDataSource);
/*      */       else {
/* 2047 */         this.mHierarchyElementDAO = new HierarchyElementDAO(getConnection());
/*      */       }
/*      */     }
/* 2050 */     return this.mHierarchyElementDAO;
/*      */   }
/*      */ 
/*      */   protected AugHierarchyElementDAO getAugHierarchyElementDAO()
/*      */   {
/* 2059 */     if (this.mAugHierarchyElementDAO == null)
/*      */     {
/* 2061 */       if (this.mDataSource != null)
/* 2062 */         this.mAugHierarchyElementDAO = new AugHierarchyElementDAO(this.mDataSource);
/*      */       else {
/* 2064 */         this.mAugHierarchyElementDAO = new AugHierarchyElementDAO(getConnection());
/*      */       }
/*      */     }
/* 2067 */     return this.mAugHierarchyElementDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 2072 */     return "Hierarchy";
/*      */   }
/*      */ 
/*      */   public HierarchyRefImpl getRef(HierarchyPK paramHierarchyPK)
/*      */   {
/* 2077 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2078 */     PreparedStatement stmt = null;
/* 2079 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 2082 */       stmt = getConnection().prepareStatement("select 0,DIMENSION.DIMENSION_ID,HIERARCHY.VIS_ID from HIERARCHY,DIMENSION where 1=1 and HIERARCHY.HIERARCHY_ID = ? and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID");
/* 2083 */       int col = 1;
/* 2084 */       stmt.setInt(col++, paramHierarchyPK.getHierarchyId());
/*      */ 
/* 2086 */       resultSet = stmt.executeQuery();
/*      */ 
/* 2088 */       if (!resultSet.next()) {
/* 2089 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getRef ").append(paramHierarchyPK).append(" not found").toString());
/*      */       }
/* 2091 */       col = 2;
/* 2092 */       DimensionPK newDimensionPK = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/* 2096 */       String textHierarchy = resultSet.getString(col++);
/* 2097 */       HierarchyCK ckHierarchy = new HierarchyCK(newDimensionPK, paramHierarchyPK);
/*      */ 
/* 2102 */       HierarchyRefImpl localHierarchyRefImpl = new HierarchyRefImpl(ckHierarchy, textHierarchy);
/*      */       return localHierarchyRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2107 */       throw handleSQLException(paramHierarchyPK, "select 0,DIMENSION.DIMENSION_ID,HIERARCHY.VIS_ID from HIERARCHY,DIMENSION where 1=1 and HIERARCHY.HIERARCHY_ID = ? and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2111 */       closeResultSet(resultSet);
/* 2112 */       closeStatement(stmt);
/* 2113 */       closeConnection();
/*      */ 
/* 2115 */       if (timer != null)
/* 2116 */         timer.logDebug("getRef", paramHierarchyPK); 
/* 2116 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 2128 */     if (c == null)
/* 2129 */       return;
/* 2130 */     Iterator iter = c.iterator();
/* 2131 */     while (iter.hasNext())
/*      */     {
/* 2133 */       HierarchyEVO evo = (HierarchyEVO)iter.next();
/* 2134 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(HierarchyEVO evo, String dependants)
/*      */   {
/* 2148 */     if (evo.insertPending()) {
/* 2149 */       return;
/*      */     }
/* 2151 */     if (evo.getHierarchyId() < 1) {
/* 2152 */       return;
/*      */     }
/*      */ 
/* 2156 */     if ((dependants.indexOf("<4>") > -1) || (dependants.indexOf("<5>") > -1))
/*      */     {
/* 2160 */       if (!evo.isHierarchyElementsAllItemsLoaded())
/*      */       {
/* 2162 */         evo.setHierarchyElements(getHierarchyElementDAO().getAll(evo.getHierarchyId(), dependants, evo.getHierarchyElements()));
/*      */ 
/* 2169 */         evo.setHierarchyElementsAllItemsLoaded(true);
/*      */       }
/* 2171 */       getHierarchyElementDAO().getDependants(evo.getHierarchyElements(), dependants);
/*      */     }
/*      */ 
/* 2175 */     if (dependants.indexOf("<6>") > -1)
/*      */     {
/* 2178 */       if (!evo.isAugHierarchyElementsAllItemsLoaded())
/*      */       {
/* 2180 */         evo.setAugHierarchyElements(getAugHierarchyElementDAO().getAll(evo.getHierarchyId(), dependants, evo.getAugHierarchyElements()));
/*      */ 
/* 2187 */         evo.setAugHierarchyElementsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private String debugQueryDuplicateHierarchyElements(int hierarchyId)
/*      */     throws SQLException
/*      */   {
/* 2217 */     PreparedStatement ps = null;
/* 2218 */     ResultSet rs = null;
/* 2219 */     StringBuilder sb = new StringBuilder();
/*      */     try
/*      */     {
/* 2223 */       ps = getConnection().prepareStatement("with se as\n(\n select /*+ materialize */ structure_id, structure_element_id, vis_id \n  from structure_element_view \n  where structure_id = ?\n )\nselect structure_id, structure_element_id, vis_id\nfrom se \nwhere (structure_id, structure_element_id) \nin \n(  \n  select structure_id, structure_element_id \n  from se \n  group by structure_id, structure_element_id\n  having count(*) > 1\n)");
/* 2224 */       ps.setInt(1, hierarchyId);
/* 2225 */       rs = ps.executeQuery();
/* 2226 */       while (rs.next())
/*      */       {
/* 2228 */         sb.append("Structure:[").append(rs.getInt("structure_id")).append("]").append(" StuctureElementId:").append(rs.getInt("structure_element_id")).append("]").append(" VisId:[").append(rs.getString("vis_id")).append("]").append("\n");
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 2236 */       closeResultSet(rs);
/* 2237 */       closeStatement(ps);
/* 2238 */       closeConnection();
/*      */     }
/*      */ 
/* 2241 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public void primeHierarchyRuntime()
/*      */     throws SQLException
/*      */   {
/* 2248 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2249 */     CallableStatement cs = null;
/*      */ 
/* 2251 */     this._log.info("primeHierarchyRuntime", new StringBuilder().append("hierarchy:").append(this.mDetails).toString());
/*      */ 
/* 2253 */     if (this._log.isDebugEnabled())
/*      */     {
/* 2255 */       String duplicates = debugQueryDuplicateHierarchyElements(this.mDetails.getHierarchyId());
/*      */ 
/* 2257 */       if (duplicates.length() > 0)
/*      */       {
/* 2259 */         this._log.debug("***********************************************************************");
/* 2260 */         this._log.debug("* DUPLICATE STRUCTURE ELEMENTS - START                                *");
/* 2261 */         this._log.debug("***********************************************************************");
/*      */ 
/* 2263 */         this._log.debug(duplicates);
/*      */ 
/* 2265 */         this._log.debug("***********************************************************************");
/* 2266 */         this._log.debug("* DUPLICATE STRUCTURE ELEMENTS - END                                  *");
/* 2267 */         this._log.debug("***********************************************************************");
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2273 */       cs = getConnection().prepareCall("{ call DIMENSION_UTILS.INIT_HIERARCHY( ? ) }");
/* 2274 */       cs.setInt(1, this.mDetails.getHierarchyId());
/* 2275 */       cs.execute();
/*      */     }
/*      */     finally
/*      */     {
/* 2283 */       closeStatement(cs);
/* 2284 */       closeConnection();
/*      */     }
/*      */ 
/* 2287 */     if (timer != null)
/* 2288 */       timer.logDebug("primeHierarchyRuntime", this.mDetails.getPK());
/*      */   }
/*      */ 
/*      */   public EntityList queryDimensionHierarchiesWithElement(int dimensionId, int dimensionElementId)
/*      */     throws SQLException
/*      */   {
/* 2321 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2322 */     PreparedStatement ps = null;
/* 2323 */     ResultSet rs = null;
/*      */ 
/* 2325 */     this._log.info("queryDimensionHierarchiesWithElement", new StringBuilder().append("dimensionElementId:").append(dimensionElementId).toString());
/*      */     try
/*      */     {
/* 2329 */       ps = getConnection().prepareStatement("select distinct de.dimension_id, h.hierarchy_id\nfrom dimension_element de, \n     hierarchy h,\n     hierarchy_element he,\n     hierarchy_element_feed hef\nwhere h.dimension_id = de.dimension_id \n  and h.hierarchy_id = he.hierarchy_id\n  and hef.hierarchy_element_id = he.hierarchy_element_id\n  and de.dimension_element_id = hef.dimension_element_id\n  and de.dimension_id = ?\n  and de.dimension_element_id = ?");
/*      */ 
/* 2331 */       ps.setInt(1, dimensionId);
/* 2332 */       ps.setInt(2, dimensionElementId);
/*      */ 
/* 2334 */       rs = ps.executeQuery();
/*      */ 
/* 2336 */       EntityList result = JdbcUtils.extractToEntityListImpl(QUERY_HIERARCHIES_WITH_DIMENSION_ELEMENT_ID_COL_INFO, rs);
/*      */ 
/* 2339 */       if (timer != null) {
/* 2340 */         timer.logDebug("queryDimensionHierarchiesWithElement", Integer.valueOf(dimensionElementId));
/*      */       }
/* 2342 */       EntityList localEntityList1 = result;
/*      */       return localEntityList1;
/*      */     }
/*      */     finally
/*      */     {
/* 2346 */       closeResultSet(rs);
/* 2347 */       closeStatement(ps);
/* 2348 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public EntityList queryDimensionHierarchiesWithElement(int dimensionId, String dimensionElementVisId)
/*      */     throws SQLException
/*      */   {
/* 2382 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2383 */     PreparedStatement ps = null;
/* 2384 */     ResultSet rs = null;
/*      */ 
/* 2386 */     this._log.info("queryDimensionHierarchiesWithElement", new StringBuilder().append("dimensionElementVisId:").append(dimensionElementVisId).toString());
/*      */     try
/*      */     {
/* 2390 */       ps = getConnection().prepareStatement("select distinct de.dimension_id, h.hierarchy_id\nfrom dimension_element de,\n     hierarchy h,\n     hierarchy_element he,\n     hierarchy_element_feed hef\nwhere h.dimension_id = de.dimension_id\n  and h.hierarchy_id = he.hierarchy_id\n  and hef.hierarchy_element_id = he.hierarchy_element_id\n  and de.dimension_element_id = hef.dimension_element_id\n  and de.dimension_id = ?\n  and de.vis_id = ?");
/*      */ 
/* 2392 */       ps.setInt(1, dimensionId);
/* 2393 */       ps.setString(2, dimensionElementVisId);
/*      */ 
/* 2395 */       rs = ps.executeQuery();
/*      */ 
/* 2397 */       EntityList result = JdbcUtils.extractToEntityListImpl(QUERY_HIERARCHIES_WITH_DIMENSION_ELEMENT_VIS_ID_COL_INFO, rs);
/*      */ 
/* 2400 */       if (timer != null) {
/* 2401 */         timer.logDebug("queryDimensionHierarchiesWithElement", dimensionElementVisId);
/*      */       }
/* 2403 */       EntityList localEntityList1 = result;
/*      */       return localEntityList1;
/*      */     }
/*      */     finally
/*      */     {
/* 2407 */       closeResultSet(rs);
/* 2408 */       closeStatement(ps);
/* 2409 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */
			public AllHierarchysELO getHierarchiesForLoggedUser(int userId) {
				Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  535 */     PreparedStatement stmt = null;
/*  536 */     ResultSet resultSet = null;
/*  537 */     AllHierarchysELO results = new AllHierarchysELO();
/*      */     try
/*      */     {
/*  540 */       stmt = getConnection().prepareStatement(SQL_ALL_HIERARCHYS_FOR_USER);
/*  541 */       int col = 1;
				 stmt.setInt(1, userId);
/*  542 */       resultSet = stmt.executeQuery();
/*  543 */       while (resultSet.next())
/*      */       {
/*  545 */         col = 2;
/*      */ 
/*  548 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  551 */         String textDimension = resultSet.getString(col++);
/*  552 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/*  555 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/*  558 */         String textHierarchy = resultSet.getString(col++);
/*      */ 
/*  561 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  565 */         String textModelDimensionRel = "";
/*      */ 
/*  567 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  570 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  574 */         HierarchyCK ckHierarchy = new HierarchyCK(pkDimension, pkHierarchy);
/*      */ 
/*  580 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/*  587 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(ckHierarchy, textHierarchy);
/*      */ 
/*  593 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
/*      */ 
/*  599 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  604 */         String col1 = resultSet.getString(col++);
/*  605 */         int col2 = resultSet.getInt(col++);
/*      */ 
/*  608 */         results.add(erHierarchy, erDimension, erModelDimensionRel, erModel, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  620 */       throw handleSQLException(SQL_ALL_HIERARCHYS_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  624 */       closeResultSet(resultSet);
/*  625 */       closeStatement(stmt);
/*  626 */       closeConnection();
/*      */     }
/*      */ 
/*  629 */     if (timer != null) {
/*  630 */       timer.logDebug("getHierarchiesForLoggedUser", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  634 */     return results;
			}
			

			public HashMap<String, ArrayList<HierarchyRef>> getCalendarForModels(HashSet<String> models) {
//				Date date = new Date();
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				HashMap<String, ArrayList<HierarchyRef>> results = new HashMap<String, ArrayList<HierarchyRef>>();
				String sql = "";
				try {
					int i = 0, length = models.toArray().length;
					for (Object model : models.toArray()) {
					    sql += "(SELECT '" + model.toString() + "' AS model, "+
					            "  s.structure_element_id, "+
					            "  s.CAL_VIS_ID_PREFIX "+
					            "  || s.vis_id as label , "+
					            "    CASE "+
					            "      WHEN h.parent_id = 0 "+
					            "      THEN 0 "+
					            "      ELSE to_number(s.vis_id) "+
					            "    END AS month, "+
					            "    CASE "+
					            "      WHEN h.parent_id = 0 "+
					            "      THEN  to_number(s.vis_id) "+
					            "      ELSE to_number(h.vis_id) "+
					            "    END AS year "+
					            "FROM hierarchy_element h "+
					            "JOIN structure_element s "+
					            "ON h.hierarchy_element_id = s.parent_id "+
					            "WHERE h.hierarchy_id      = "+
					            "  ( SELECT hierarchy_id FROM hierarchy WHERE vis_id = '" + model.toString() + "-Cal' )) ";
					    
						if (i < length - 1) {
							sql += "union all\n" + 
									"\n" + 
									"";
						}
						i++;
					}
					sql += "order by model, year, month";
					// TODO sometimes in DB s.vis_id == 'Open'???
					stmt = getConnection().prepareStatement(sql);
					resultSet = stmt.executeQuery();
					ArrayList<HierarchyRef> periods = null;
					String model = "";
					while (resultSet.next()) {
						if (!resultSet.getString(1).equals(model)) {
						    if (periods != null) {
						        results.put(model, periods);                                                        
						    }
						    model = resultSet.getString(1);
						    periods = new ArrayList<HierarchyRef>();
						}
						String narrative = resultSet.getString(3);
						periods.add(new HierarchyRefImpl(new HierarchyPK(resultSet.getInt(2)), narrative));
					}	
					results.put(model, periods);
				} catch (SQLException sqle) {
					System.err.println(sqle);
					sqle.printStackTrace();
					throw new RuntimeException(new StringBuilder().append(getEntityName()).append("getCalendarForModels").toString(), sqle);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(new StringBuilder().append(getEntityName()).append("getCalendarForModels").toString(), e);
				} finally {
					closeResultSet(resultSet);
					closeStatement(stmt);
					closeConnection();
				}
//				SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss.SSS");
//				System.out.println(time.format(new Date()) + " " + time.format(date));
				return results;
			}

}

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.dimension.HierarchyDAO
 * JD-Core Version:    0.6.0
 */