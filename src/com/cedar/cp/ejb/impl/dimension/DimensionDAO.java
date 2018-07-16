/*      */ package com.cedar.cp.ejb.impl.dimension;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.dimension.DimensionRef;
/*      */ import com.cedar.cp.dto.base.EntityListImpl;
/*      */ import com.cedar.cp.dto.dimension.AllDimensionsELO;
/*      */ import com.cedar.cp.dto.dimension.AllDimensionsForModelELO;
/*      */ import com.cedar.cp.dto.dimension.AvailableDimensionsELO;
/*      */ import com.cedar.cp.dto.dimension.CalendarSpecCK;
/*      */ import com.cedar.cp.dto.dimension.CalendarSpecPK;
/*      */ import com.cedar.cp.dto.dimension.CalendarYearSpecCK;
/*      */ import com.cedar.cp.dto.dimension.CalendarYearSpecPK;
/*      */ import com.cedar.cp.dto.dimension.DimensionCK;
/*      */ import com.cedar.cp.dto.dimension.DimensionElementCK;
/*      */ import com.cedar.cp.dto.dimension.DimensionElementPK;
/*      */ import com.cedar.cp.dto.dimension.DimensionElementRefImpl;
/*      */ import com.cedar.cp.dto.dimension.DimensionPK;
/*      */ import com.cedar.cp.dto.dimension.DimensionRefImpl;
/*      */ import com.cedar.cp.dto.dimension.HierarchyCK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyPK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyRefImpl;
/*      */ import com.cedar.cp.dto.dimension.ImportableDimensionsELO;
/*      */ import com.cedar.cp.dto.dimension.SecurityRangeCK;
/*      */ import com.cedar.cp.dto.dimension.SecurityRangePK;
/*      */ import com.cedar.cp.dto.model.ModelDimensionRelPK;
/*      */ import com.cedar.cp.dto.model.ModelDimensionRelRefImpl;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
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
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class DimensionDAO extends AbstractDAO
/*      */ {
/*   45 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select DIMENSION_ID from DIMENSION where    DIMENSION_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select DIMENSION.DIMENSION_ID,DIMENSION.VIS_ID,DIMENSION.DESCRIPTION,DIMENSION.TYPE,DIMENSION.EXTERNAL_SYSTEM_REF,DIMENSION.NULL_ELEMENT_ID,DIMENSION.VERSION_NUM,DIMENSION.UPDATED_BY_USER_ID,DIMENSION.UPDATED_TIME,DIMENSION.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from DIMENSION where    DIMENSION_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into DIMENSION ( DIMENSION_ID,VIS_ID,DESCRIPTION,TYPE,EXTERNAL_SYSTEM_REF,NULL_ELEMENT_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update DIMENSION_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from DIMENSION_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_DIMENSIONVID = "select count(*) from DIMENSION where    VIS_ID = ? AND TYPE = ? and not(    DIMENSION_ID = ? )";
/*      */   protected static final String SQL_STORE = "update DIMENSION set VIS_ID = ?,DESCRIPTION = ?,TYPE = ?,EXTERNAL_SYSTEM_REF = ?,NULL_ELEMENT_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DIMENSION_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from DIMENSION where DIMENSION_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from DIMENSION where    DIMENSION_ID = ? ";
/*      */   protected static final String SQL_FIND_ALL = "select DIMENSION_ID from DIMENSION order by TYPE, VIS_ID";
/*  813 */   protected static String SQL_ALL_DIMENSIONS = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,DIMENSION.TYPE      ,DIMENSION.DESCRIPTION      ,MODEL_DIMENSION_REL.DIMENSION_SEQ_NUM      ,( select count(*) from hierarchy where dimension_id = dimension.dimension_id ) HIERARCHY_COUNT from DIMENSION    ,MODEL_DIMENSION_REL    ,MODEL where 1=1  and  DIMENSION.DIMENSION_ID = MODEL_DIMENSION_REL.DIMENSION_ID (+) and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID (+) order by MODEL.VIS_ID, DIMENSION.VIS_ID";
/*      */   protected static String SQL_ALL_DIMENSIONS_FOR_USER = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,DIMENSION.TYPE      ,DIMENSION.DESCRIPTION      ,MODEL_DIMENSION_REL.DIMENSION_SEQ_NUM      ,( select count(*) from hierarchy where dimension_id = dimension.dimension_id ) HIERARCHY_COUNT from DIMENSION    ,MODEL_DIMENSION_REL    ,MODEL where model.model_id in (select distinct model_id from budget_user where user_id = ?)  and  DIMENSION.DIMENSION_ID = MODEL_DIMENSION_REL.DIMENSION_ID (+) and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID (+) order by MODEL.VIS_ID, DIMENSION.VIS_ID";
/*  938 */   protected static String SQL_AVAILABLE_DIMENSIONS = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,DIMENSION.TYPE      ,DIMENSION.DESCRIPTION from DIMENSION where 1=1  and  DIMENSION.DIMENSION_ID not in ( select distinct DIMENSION_ID from MODEL_DIMENSION_REL) order by DIMENSION.VIS_ID";
/*      */ 
/* 1024 */   protected static String SQL_IMPORTABLE_DIMENSIONS = "select distinct 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID      ,DIMENSION_ELEMENT.VIS_ID      ,DIMENSION_ELEMENT.CREDIT_DEBIT      ,DIMENSION.DESCRIPTION from DIMENSION    ,DIMENSION_ELEMENT where 1=1  and  DIMENSION.DIMENSION_ID = DIMENSION_ELEMENT.DIMENSION_ID (+) and DIMENSION_ELEMENT.DIMENSION_ID is null order by DIMENSION.VIS_ID";
/*      */ 
/* 1127 */   protected static String SQL_ALL_DIMENSIONS_FOR_MODEL = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.DESCRIPTION      ,DIMENSION.TYPE      ,(select VIS_ID from DIMENSION_ELEMENT where DIMENSION_ELEMENT_ID = NULL_ELEMENT_ID) from DIMENSION    ,MODEL_DIMENSION_REL where 1=1  and  MODEL_DIMENSION_REL.MODEL_ID = ? and MODEL_DIMENSION_REL.DIMENSION_ID = DIMENSION.DIMENSION_ID order by model_dimension_rel.dimension_seq_num";
/*      */ 
/* 1242 */   private static String[][] SQL_DELETE_CHILDREN = { { "DIMENSION_ELEMENT", "delete from DIMENSION_ELEMENT where     DIMENSION_ELEMENT.DIMENSION_ID = ? " }, { "CALENDAR_SPEC", "delete from CALENDAR_SPEC where     CALENDAR_SPEC.DIMENSION_ID = ? " }, { "CALENDAR_YEAR_SPEC", "delete from CALENDAR_YEAR_SPEC where     CALENDAR_YEAR_SPEC.DIMENSION_ID = ? " }, { "HIERARCHY", "delete from HIERARCHY where     HIERARCHY.DIMENSION_ID = ? " }, { "SECURITY_RANGE", "delete from SECURITY_RANGE where     SECURITY_RANGE.DIMENSION_ID = ? " } };
/*      */ 
/* 1271 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "HIERARCHY_ELEMENT", "delete from HIERARCHY_ELEMENT HierarchyElement where exists (select * from HIERARCHY_ELEMENT,HIERARCHY,DIMENSION where     HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID and HierarchyElement.HIERARCHY_ID = HIERARCHY_ELEMENT.HIERARCHY_ID " }, { "HIERARCHY_ELEMENT_FEED", "delete from HIERARCHY_ELEMENT_FEED HierarchyElementFeed where exists (select * from HIERARCHY_ELEMENT_FEED,HIERARCHY_ELEMENT,HIERARCHY,DIMENSION where     HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID = HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID and HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID and HierarchyElementFeed.HIERARCHY_ELEMENT_ID = HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID " }, { "AUG_HIERARCHY_ELEMENT", "delete from AUG_HIERARCHY_ELEMENT AugHierarchyElement where exists (select * from AUG_HIERARCHY_ELEMENT,HIERARCHY,DIMENSION where     AUG_HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID and AugHierarchyElement.HIERARCHY_ID = AUG_HIERARCHY_ELEMENT.HIERARCHY_ID " }, { "SECURITY_RANGE_ROW", "delete from SECURITY_RANGE_ROW SecurityRangeRow where exists (select * from SECURITY_RANGE_ROW,SECURITY_RANGE,DIMENSION where     SECURITY_RANGE_ROW.SECURITY_RANGE_ID = SECURITY_RANGE.SECURITY_RANGE_ID and SECURITY_RANGE.DIMENSION_ID = DIMENSION.DIMENSION_ID and SecurityRangeRow.SECURITY_RANGE_ID = SECURITY_RANGE_ROW.SECURITY_RANGE_ID " } };
/*      */ 
/* 1321 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and DIMENSION.DIMENSION_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from DIMENSION where   DIMENSION_ID = ?";
/*      */   public static final String SQL_GET_DIMENSION_ELEMENT_REF = "select 0,DIMENSION.DIMENSION_ID,DIMENSION_ELEMENT.VIS_ID,DIMENSION_ELEMENT.CREDIT_DEBIT from DIMENSION_ELEMENT,DIMENSION where 1=1 and DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID = ? and DIMENSION_ELEMENT.DIMENSION_ID = DIMENSION.DIMENSION_ID";
/*      */   public static final String SQL_GET_CALENDAR_SPEC_REF = "select 0,DIMENSION.DIMENSION_ID from CALENDAR_SPEC,DIMENSION where 1=1 and CALENDAR_SPEC.CALENDAR_SPEC_ID = ? and CALENDAR_SPEC.DIMENSION_ID = DIMENSION.DIMENSION_ID";
/*      */   public static final String SQL_GET_CALENDAR_YEAR_SPEC_REF = "select 0,DIMENSION.DIMENSION_ID from CALENDAR_YEAR_SPEC,DIMENSION where 1=1 and CALENDAR_YEAR_SPEC.CALENDAR_YEAR_SPEC_ID = ? and CALENDAR_YEAR_SPEC.DIMENSION_ID = DIMENSION.DIMENSION_ID";
/*      */   public static final String SQL_GET_HIERARCHY_REF = "select 0,DIMENSION.DIMENSION_ID,HIERARCHY.VIS_ID from HIERARCHY,DIMENSION where 1=1 and HIERARCHY.HIERARCHY_ID = ? and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID";
/*      */   public static final String SQL_GET_HIERARCHY_ELEMENT_REF = "select 0,DIMENSION.DIMENSION_ID,HIERARCHY.HIERARCHY_ID,HIERARCHY_ELEMENT.VIS_ID from HIERARCHY_ELEMENT,DIMENSION,HIERARCHY where 1=1 and HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID = ? and HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.HIERARCHY_ID = DIMENSION.HIERARCHY_ID";
/*      */   public static final String SQL_GET_HIERARCHY_ELEMENT_FEED_REF = "select 0,DIMENSION.DIMENSION_ID,HIERARCHY.HIERARCHY_ID,HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID from HIERARCHY_ELEMENT_FEED,DIMENSION,HIERARCHY,HIERARCHY_ELEMENT where 1=1 and HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID = ? and HIERARCHY_ELEMENT_FEED.DIMENSION_ELEMENT_ID = ? and HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID = HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID and HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID = HIERARCHY.HIERARCHY_ELEMENT_ID and HIERARCHY.HIERARCHY_ID = DIMENSION.HIERARCHY_ID";
/*      */   public static final String SQL_GET_AUG_HIERARCHY_ELEMENT_REF = "select 0,DIMENSION.DIMENSION_ID,HIERARCHY.HIERARCHY_ID,AUG_HIERARCHY_ELEMENT.VIS_ID from AUG_HIERARCHY_ELEMENT,DIMENSION,HIERARCHY where 1=1 and AUG_HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID = ? and AUG_HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.HIERARCHY_ID = DIMENSION.HIERARCHY_ID";
/*      */   public static final String SQL_GET_SECURITY_RANGE_REF = "select 0,DIMENSION.DIMENSION_ID,SECURITY_RANGE.VIS_ID from SECURITY_RANGE,DIMENSION where 1=1 and SECURITY_RANGE.SECURITY_RANGE_ID = ? and SECURITY_RANGE.DIMENSION_ID = DIMENSION.DIMENSION_ID";
/*      */   public static final String SQL_GET_SECURITY_RANGE_ROW_REF = "select 0,DIMENSION.DIMENSION_ID,SECURITY_RANGE.SECURITY_RANGE_ID from SECURITY_RANGE_ROW,DIMENSION,SECURITY_RANGE where 1=1 and SECURITY_RANGE_ROW.SECURITY_RANGE_ROW_ID = ? and SECURITY_RANGE_ROW.SECURITY_RANGE_ID = SECURITY_RANGE.SECURITY_RANGE_ID and SECURITY_RANGE.SECURITY_RANGE_ID = DIMENSION.SECURITY_RANGE_ID";
/*      */   private static final String QUERY_MODEL_OWNER_SQL = "select m.MODEL_ID, m.VIS_ID \nfrom MODEL_DIMENSION_REL mdr, MODEL m\nwhere DIMENSION_ID = ? and \n      m.model_id = mdr.model_id";
/*      */   private static final String AVAILABLE_HIERARCHY_OWNERS_SQL = "select d.dimension_id, d.vis_id, mdr.model_id,\n( select vis_id from model m where mdr.model_id = m.model_id ) model_visid \nfrom dimension d, model_dimension_rel mdr\nwhere d.type = ? and \nd.dimension_id not in \n(\n\tselect dimension_id from \n\t(\n\t\tselect d.dimension_id, count(*) as nh\n\t\tfrom dimension d, hierarchy h\n\t\twhere d.dimension_id = h.dimension_id and\n\t\t\t  d.type = 3 \n\t\tgroup by d.dimension_id\n\t) \n\twhere nh >= 1\n\tunion all \n\tselect -1 from dual\n) and d.external_system_ref is null and d.dimension_id = mdr.dimension_id (+) ";
/*      */   private static final String TERM_HIERARCHY_SQL = "{ call DIMENSION_UTILS.TERM_DIMENSION( ? ) }";
/*      */   private static final String PRIME_DIMENSION_SQL = "{ call DIMENSION_UTILS.INIT_DIMENSION( ? ) }";
/*      */   protected DimensionElementDAO mDimensionElementDAO;
/*      */   protected CalendarSpecDAO mCalendarSpecDAO;
/*      */   protected CalendarYearSpecDAO mCalendarYearSpecDAO;
/*      */   protected HierarchyDAO mHierarchyDAO;
/*      */   protected SecurityRangeDAO mSecurityRangeDAO;
/*      */   protected  DimensionEVO mDetails;
/*      */ 
/*      */   public DimensionDAO(Connection connection)
/*      */   {
/*   52 */     super(connection);
/*      */   }
/*      */ 
/*      */   public DimensionDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public DimensionDAO(DataSource ds)
/*      */   {
/*   68 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected DimensionPK getPK()
/*      */   {
/*   76 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(DimensionEVO details)
/*      */   {
/*   85 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public DimensionEVO setAndGetDetails(DimensionEVO details, String dependants)
/*      */   {
/*   96 */     setDetails(details);
/*   97 */     generateKeys();
/*   98 */     getDependants(this.mDetails, dependants);
/*   99 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public DimensionPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  108 */     doCreate();
/*      */ 
/*  110 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(DimensionPK pk)
/*      */     throws ValidationException
/*      */   {
/*  120 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  129 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  138 */     doRemove();
/*      */   }
/*      */ 
/*      */   public DimensionPK findByPrimaryKey(DimensionPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  147 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  148 */     if (exists(pk_))
/*      */     {
/*  150 */       if (timer != null) {
/*  151 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  153 */       return pk_;
/*      */     }
/*      */ 
/*  156 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(DimensionPK pk)
/*      */   {
/*  174 */     PreparedStatement stmt = null;
/*  175 */     ResultSet resultSet = null;
/*  176 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  180 */       stmt = getConnection().prepareStatement("select DIMENSION_ID from DIMENSION where    DIMENSION_ID = ? ");
/*      */ 
/*  182 */       int col = 1;
/*  183 */       stmt.setInt(col++, pk.getDimensionId());
/*      */ 
/*  185 */       resultSet = stmt.executeQuery();
/*      */ 
/*  187 */       if (!resultSet.next())
/*  188 */         returnValue = false;
/*      */       else
/*  190 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  194 */       throw handleSQLException(pk, "select DIMENSION_ID from DIMENSION where    DIMENSION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  198 */       closeResultSet(resultSet);
/*  199 */       closeStatement(stmt);
/*  200 */       closeConnection();
/*      */     }
/*  202 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private DimensionEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  223 */     int col = 1;
/*  224 */     DimensionEVO evo = new DimensionEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), resultSet_.getInt(col++), null, null, null, null, null);
/*      */ 
/*  239 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  240 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  241 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  242 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(DimensionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  247 */     int col = startCol_;
/*  248 */     stmt_.setInt(col++, evo_.getDimensionId());
/*  249 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(DimensionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  254 */     int col = startCol_;
/*  255 */     stmt_.setString(col++, evo_.getVisId());
/*  256 */     stmt_.setString(col++, evo_.getDescription());
/*  257 */     stmt_.setInt(col++, evo_.getType());
/*  258 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getExternalSystemRef());
/*  259 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getNullElementId());
/*  260 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  261 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  262 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  263 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  264 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(DimensionPK pk)
/*      */     throws ValidationException
/*      */   {
/*  280 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  282 */     PreparedStatement stmt = null;
/*  283 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  287 */       stmt = getConnection().prepareStatement("select DIMENSION.DIMENSION_ID,DIMENSION.VIS_ID,DIMENSION.DESCRIPTION,DIMENSION.TYPE,DIMENSION.EXTERNAL_SYSTEM_REF,DIMENSION.NULL_ELEMENT_ID,DIMENSION.VERSION_NUM,DIMENSION.UPDATED_BY_USER_ID,DIMENSION.UPDATED_TIME,DIMENSION.CREATED_TIME from DIMENSION where    DIMENSION_ID = ? ");
/*      */ 
/*  290 */       int col = 1;
/*  291 */       stmt.setInt(col++, pk.getDimensionId());
/*      */ 
/*  293 */       resultSet = stmt.executeQuery();
/*      */ 
/*  295 */       if (!resultSet.next()) {
/*  296 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  299 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  300 */       if (this.mDetails.isModified())
/*  301 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  305 */       throw handleSQLException(pk, "select DIMENSION.DIMENSION_ID,DIMENSION.VIS_ID,DIMENSION.DESCRIPTION,DIMENSION.TYPE,DIMENSION.EXTERNAL_SYSTEM_REF,DIMENSION.NULL_ELEMENT_ID,DIMENSION.VERSION_NUM,DIMENSION.UPDATED_BY_USER_ID,DIMENSION.UPDATED_TIME,DIMENSION.CREATED_TIME from DIMENSION where    DIMENSION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  309 */       closeResultSet(resultSet);
/*  310 */       closeStatement(stmt);
/*  311 */       closeConnection();
/*      */ 
/*  313 */       if (timer != null)
/*  314 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  351 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  352 */     generateKeys();
/*      */ 
/*  354 */     this.mDetails.postCreateInit();
/*      */ 
/*  356 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  361 */       duplicateValueCheckDimensionVid();
/*      */ 
/*  363 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  364 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  365 */       stmt = getConnection().prepareStatement("insert into DIMENSION ( DIMENSION_ID,VIS_ID,DESCRIPTION,TYPE,EXTERNAL_SYSTEM_REF,NULL_ELEMENT_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  368 */       int col = 1;
/*  369 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  370 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  373 */       int resultCount = stmt.executeUpdate();
/*  374 */       if (resultCount != 1)
/*      */       {
/*  376 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  379 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  383 */       throw handleSQLException(this.mDetails.getPK(), "insert into DIMENSION ( DIMENSION_ID,VIS_ID,DESCRIPTION,TYPE,EXTERNAL_SYSTEM_REF,NULL_ELEMENT_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  387 */       closeStatement(stmt);
/*  388 */       closeConnection();
/*      */ 
/*  390 */       if (timer != null) {
/*  391 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  397 */       getDimensionElementDAO().update(this.mDetails.getElementsMap());
/*      */ 
/*  399 */       getCalendarSpecDAO().update(this.mDetails.getCalendarSpecMap());
/*      */ 
/*  401 */       getCalendarYearSpecDAO().update(this.mDetails.getCalendarYearSpecsMap());
/*      */ 
/*  403 */       getHierarchyDAO().update(this.mDetails.getHierarchiesMap());
/*      */ 
/*  405 */       getSecurityRangeDAO().update(this.mDetails.getSecurityRangesMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  411 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  431 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  433 */     PreparedStatement stmt = null;
/*  434 */     ResultSet resultSet = null;
/*  435 */     String sqlString = null;
/*      */     try
/*      */     {
/*  440 */       sqlString = "update DIMENSION_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  441 */       stmt = getConnection().prepareStatement("update DIMENSION_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  442 */       stmt.setInt(1, insertCount);
/*      */ 
/*  444 */       int resultCount = stmt.executeUpdate();
/*  445 */       if (resultCount != 1) {
/*  446 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  448 */       closeStatement(stmt);
/*      */ 
/*  451 */       sqlString = "select SEQ_NUM from DIMENSION_SEQ";
/*  452 */       stmt = getConnection().prepareStatement("select SEQ_NUM from DIMENSION_SEQ");
/*  453 */       resultSet = stmt.executeQuery();
/*  454 */       if (!resultSet.next())
/*  455 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  456 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  458 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  462 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  466 */       closeResultSet(resultSet);
/*  467 */       closeStatement(stmt);
/*  468 */       closeConnection();
/*      */ 
/*  470 */       if (timer != null)
/*  471 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  471 */     }
/*      */   }
/*      */ 
/*      */   public DimensionPK generateKeys()
/*      */   {
/*  481 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  483 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  486 */     if (insertCount == 0) {
/*  487 */       return this.mDetails.getPK();
/*      */     }
/*  489 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  491 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckDimensionVid()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  505 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  506 */     PreparedStatement stmt = null;
/*  507 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  511 */       stmt = getConnection().prepareStatement("select count(*) from DIMENSION where    VIS_ID = ? and not(    DIMENSION_ID = ? )");
/*      */ 
/*  514 */       int col = 1;
/*  515 */       stmt.setString(col++, this.mDetails.getVisId());
/*  516 */       //stmt.setInt(col++, this.mDetails.getType());
/*  517 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  520 */       resultSet = stmt.executeQuery();
/*      */ 
/*  522 */       if (!resultSet.next()) {
/*  523 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  527 */       col = 1;
/*  528 */       int count = resultSet.getInt(col++);
/*  529 */       if (count > 0) {
/*  530 */         throw new DuplicateNameValidationException("Duplicate name (" + this.mDetails.getVisId() + "). Please re-name and retry");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  536 */       throw handleSQLException(getPK(), "select count(*) from DIMENSION where    VIS_ID = ? AND TYPE = ? and not(    DIMENSION_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  540 */       closeResultSet(resultSet);
/*  541 */       closeStatement(stmt);
/*  542 */       closeConnection();
/*      */ 
/*  544 */       if (timer != null)
/*  545 */         timer.logDebug("duplicateValueCheckDimensionVid", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  573 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  575 */     generateKeys();
/*      */ 
/*  580 */     PreparedStatement stmt = null;
/*      */ 
/*  582 */     boolean mainChanged = this.mDetails.isModified();
/*  583 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  587 */       if (mainChanged) {
/*  588 */         duplicateValueCheckDimensionVid();
/*      */       }
/*  590 */       if (getDimensionElementDAO().update(this.mDetails.getElementsMap())) {
/*  591 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  594 */       if (getCalendarSpecDAO().update(this.mDetails.getCalendarSpecMap())) {
/*  595 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  598 */       if (getCalendarYearSpecDAO().update(this.mDetails.getCalendarYearSpecsMap())) {
/*  599 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  602 */       if (getHierarchyDAO().update(this.mDetails.getHierarchiesMap())) {
/*  603 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  606 */       if (getSecurityRangeDAO().update(this.mDetails.getSecurityRangesMap())) {
/*  607 */         dependantChanged = true;
/*      */       }
/*  609 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  612 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  615 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  616 */         stmt = getConnection().prepareStatement("update DIMENSION set VIS_ID = ?,DESCRIPTION = ?,TYPE = ?,EXTERNAL_SYSTEM_REF = ?,NULL_ELEMENT_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DIMENSION_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  619 */         int col = 1;
/*  620 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  621 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  623 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  626 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  628 */         if (resultCount == 0) {
/*  629 */           checkVersionNum();
/*      */         }
/*  631 */         if (resultCount != 1) {
/*  632 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  635 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  646 */       throw handleSQLException(getPK(), "update DIMENSION set VIS_ID = ?,DESCRIPTION = ?,TYPE = ?,EXTERNAL_SYSTEM_REF = ?,NULL_ELEMENT_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DIMENSION_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  650 */       closeStatement(stmt);
/*  651 */       closeConnection();
/*      */ 
/*  653 */       if ((timer != null) && (
/*  654 */         (mainChanged) || (dependantChanged)))
/*  655 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  667 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  668 */     PreparedStatement stmt = null;
/*  669 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  673 */       stmt = getConnection().prepareStatement("select VERSION_NUM from DIMENSION where DIMENSION_ID = ?");
/*      */ 
/*  676 */       int col = 1;
/*  677 */       stmt.setInt(col++, this.mDetails.getDimensionId());
/*      */ 
/*  680 */       resultSet = stmt.executeQuery();
/*      */ 
/*  682 */       if (!resultSet.next()) {
/*  683 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  686 */       col = 1;
/*  687 */       int dbVersionNumber = resultSet.getInt(col++);
/*  688 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  689 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  695 */       throw handleSQLException(getPK(), "select VERSION_NUM from DIMENSION where DIMENSION_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  699 */       closeStatement(stmt);
/*  700 */       closeResultSet(resultSet);
/*      */ 
/*  702 */       if (timer != null)
/*  703 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  720 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  721 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  726 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  731 */       stmt = getConnection().prepareStatement("delete from DIMENSION where    DIMENSION_ID = ? ");
/*      */ 
/*  734 */       int col = 1;
/*  735 */       stmt.setInt(col++, this.mDetails.getDimensionId());
/*      */ 
/*  737 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  739 */       if (resultCount != 1) {
/*  740 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  746 */       throw handleSQLException(getPK(), "delete from DIMENSION where    DIMENSION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  750 */       closeStatement(stmt);
/*  751 */       closeConnection();
/*      */ 
/*  753 */       if (timer != null)
/*  754 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }

			public void doRemove(int dimId)
/*      */   {
/*  720 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  721 */     deleteDependants(dimId);
/*      */ 
/*  726 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  731 */       stmt = getConnection().prepareStatement("delete from DIMENSION where    DIMENSION_ID = ? ");
/*      */ 
/*  734 */       int col = 1;
/*  735 */       stmt.setInt(col++, dimId);
/*      */ 
/*  737 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  739 */       if (resultCount != 1) {
/*  740 */         throw new RuntimeException(getEntityName() + " delete failed (" + dimId + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*      */     }
/*      */     finally
/*      */     {
/*  750 */       closeStatement(stmt);
/*  751 */       closeConnection();
/*      */ 
/*  753 */       if (timer != null)
/*  754 */         timer.logDebug("remove", dimId);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection findAll()
/*      */   {
/*  774 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  775 */     PreparedStatement stmt = null;
/*  776 */     ResultSet resultSet = null;
/*  777 */     Vector keys = new Vector();
/*      */     try
/*      */     {
/*  780 */       stmt = getConnection().prepareStatement("select DIMENSION_ID from DIMENSION order by TYPE, VIS_ID");
/*  781 */       int col = 1;
/*  782 */       resultSet = stmt.executeQuery();
/*  783 */       while (resultSet.next())
/*      */       {
/*  785 */         col = 1;
/*  786 */         DimensionPK pk = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  789 */         keys.addElement(pk);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  794 */       throw handleSQLException("select DIMENSION_ID from DIMENSION order by TYPE, VIS_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  798 */       closeResultSet(resultSet);
/*  799 */       closeStatement(stmt);
/*  800 */       closeConnection();
/*      */     }
/*      */ 
/*  803 */     if (timer != null) {
/*  804 */       timer.logDebug("findAll", "");
/*      */     }
/*      */ 
/*  807 */     return keys;
/*      */   }
/*      */ 
/*      */   public AllDimensionsELO getAllDimensions()
/*      */   {
/*  848 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  849 */     PreparedStatement stmt = null;
/*  850 */     ResultSet resultSet = null;
/*  851 */     AllDimensionsELO results = new AllDimensionsELO();
/*      */     try
/*      */     {
/*  854 */       stmt = getConnection().prepareStatement(SQL_ALL_DIMENSIONS);
/*  855 */       int col = 1;
/*  856 */       resultSet = stmt.executeQuery();
/*  857 */       while (resultSet.next())
/*      */       {
/*  859 */         col = 2;
/*      */ 
/*  862 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  865 */         String textDimension = resultSet.getString(col++);
/*  866 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/*  869 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  873 */         String textModelDimensionRel = "";
/*      */ 
/*  875 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  878 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  881 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/*  888 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
/*      */ 
/*  894 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  899 */         int col1 = resultSet.getInt(col++);
/*  900 */         String col2 = resultSet.getString(col++);
/*  901 */         int col3 = resultSet.getInt(col++);
/*  902 */         int col4 = resultSet.getInt(col++);
/*      */ 
/*  905 */         results.add(erDimension, erModelDimensionRel, erModel, col1, col2, col3, col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  918 */       throw handleSQLException(SQL_ALL_DIMENSIONS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  922 */       closeResultSet(resultSet);
/*  923 */       closeStatement(stmt);
/*  924 */       closeConnection();
/*      */     }
/*      */ 
/*  927 */     if (timer != null) {
/*  928 */       timer.logDebug("getAllDimensions", " items=" + results.size());
/*      */     }
/*      */ 
/*  932 */     return results;
/*      */   }
/*      */ 
/*      */   public AvailableDimensionsELO getAvailableDimensions()
/*      */   {
/*  963 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  964 */     PreparedStatement stmt = null;
/*  965 */     ResultSet resultSet = null;
/*  966 */     AvailableDimensionsELO results = new AvailableDimensionsELO();
/*      */     try
/*      */     {
/*  969 */       stmt = getConnection().prepareStatement(SQL_AVAILABLE_DIMENSIONS);
/*  970 */       int col = 1;
/*  971 */       resultSet = stmt.executeQuery();
/*  972 */       while (resultSet.next())
/*      */       {
/*  974 */         col = 2;
/*      */ 
/*  977 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  980 */         String textDimension = resultSet.getString(col++);
/*  981 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/*  985 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/*  991 */         int col1 = resultSet.getInt(col++);
/*  992 */         String col2 = resultSet.getString(col++);
/*      */ 
/*  995 */         results.add(erDimension, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1004 */       throw handleSQLException(SQL_AVAILABLE_DIMENSIONS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1008 */       closeResultSet(resultSet);
/* 1009 */       closeStatement(stmt);
/* 1010 */       closeConnection();
/*      */     }
/*      */ 
/* 1013 */     if (timer != null) {
/* 1014 */       timer.logDebug("getAvailableDimensions", " items=" + results.size());
/*      */     }
/*      */ 
/* 1018 */     return results;
/*      */   }
/*      */ 
/*      */   public ImportableDimensionsELO getImportableDimensions()
/*      */   {
/* 1054 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1055 */     PreparedStatement stmt = null;
/* 1056 */     ResultSet resultSet = null;
/* 1057 */     ImportableDimensionsELO results = new ImportableDimensionsELO();
/*      */     try
/*      */     {
/* 1060 */       stmt = getConnection().prepareStatement(SQL_IMPORTABLE_DIMENSIONS);
/* 1061 */       int col = 1;
/* 1062 */       resultSet = stmt.executeQuery();
/* 1063 */       while (resultSet.next())
/*      */       {
/* 1065 */         col = 2;
/*      */ 
/* 1068 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/* 1071 */         String textDimension = resultSet.getString(col++);
/* 1072 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/* 1075 */         DimensionElementPK pkDimensionElement = new DimensionElementPK(resultSet.getInt(col++));
/*      */ 
/* 1078 */         String textDimensionElement = resultSet.getString(col++);
/* 1079 */         int erDimensionElementCreditDebit = resultSet.getInt(col++);
/*      */ 
/* 1082 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/* 1089 */         DimensionElementRefImpl erDimensionElement = new DimensionElementRefImpl(pkDimensionElement, textDimensionElement, erDimensionElementCreditDebit);
/*      */ 
/* 1095 */         String col1 = resultSet.getString(col++);
/*      */ 
/* 1098 */         results.add(erDimension, erDimensionElement, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1107 */       throw handleSQLException(SQL_IMPORTABLE_DIMENSIONS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1111 */       closeResultSet(resultSet);
/* 1112 */       closeStatement(stmt);
/* 1113 */       closeConnection();
/*      */     }
/*      */ 
/* 1116 */     if (timer != null) {
/* 1117 */       timer.logDebug("getImportableDimensions", " items=" + results.size());
/*      */     }
/*      */ 
/* 1121 */     return results;
/*      */   }
/*      */ 
/*      */   public AllDimensionsForModelELO getAllDimensionsForModel(int modelId)
/*      */   {
/* 1161 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1162 */     PreparedStatement stmt = null;
/* 1163 */     ResultSet resultSet = null;
/* 1164 */     AllDimensionsForModelELO results = new AllDimensionsForModelELO();
/*      */     try
/*      */     {
/* 1167 */       stmt = getConnection().prepareStatement(SQL_ALL_DIMENSIONS_FOR_MODEL);
/* 1168 */       int col = 1;
/* 1169 */       stmt.setInt(col++, modelId);
/* 1170 */       resultSet = stmt.executeQuery();
/* 1171 */       while (resultSet.next())
/*      */       {
/* 1173 */         col = 2;
/*      */ 
/* 1176 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/* 1179 */         String textDimension = resultSet.getString(col++);
/* 1180 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/* 1183 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1187 */         String textModelDimensionRel = "";
/*      */ 
/* 1190 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/* 1197 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
/*      */ 
/* 1202 */         int col1 = resultSet.getInt(col++);
/* 1203 */         String col2 = resultSet.getString(col++);
/* 1204 */         String col3 = resultSet.getString(col++);
/* 1205 */         int col4 = resultSet.getInt(col++);
/* 1206 */         String col5 = resultSet.getString(col++);
/*      */ 
/* 1209 */         results.add(erDimension, erModelDimensionRel, col1, col2, col3, col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1222 */       throw handleSQLException(SQL_ALL_DIMENSIONS_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1226 */       closeResultSet(resultSet);
/* 1227 */       closeStatement(stmt);
/* 1228 */       closeConnection();
/*      */     }
/*      */ 
/* 1231 */     if (timer != null) {
/* 1232 */       timer.logDebug("getAllDimensionsForModel", " ModelId=" + modelId + " items=" + results.size());
/*      */     }
/*      */ 
/* 1237 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(DimensionPK pk)
/*      */   {
/* 1330 */     Set emptyStrings = Collections.emptySet();
/* 1331 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(DimensionPK pk, Set<String> exclusionTables)
/*      */   {
/* 1336 */     tidyHierarchies(pk);
/*      */ 
/* 1338 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1340 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1342 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1344 */       PreparedStatement stmt = null;
/*      */ 
/* 1346 */       int resultCount = 0;
/* 1347 */       String s = null;
/*      */       try
/*      */       {
/* 1350 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 1352 */         if (this._log.isDebugEnabled()) {
/* 1353 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1355 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1358 */         int col = 1;
/* 1359 */         stmt.setInt(col++, pk.getDimensionId());
/*      */ 
/* 1362 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1366 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1370 */         closeStatement(stmt);
/* 1371 */         closeConnection();
/*      */ 
/* 1373 */         if (timer != null) {
/* 1374 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1378 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1380 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1382 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1384 */       PreparedStatement stmt = null;
/*      */ 
/* 1386 */       int resultCount = 0;
/* 1387 */       String s = null;
/*      */       try
/*      */       {
/* 1390 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1392 */         if (this._log.isDebugEnabled()) {
/* 1393 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1395 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1398 */         int col = 1;
/* 1399 */         stmt.setInt(col++, pk.getDimensionId());
/*      */ 
/* 1402 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1406 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1410 */         closeStatement(stmt);
/* 1411 */         closeConnection();
/*      */ 
/* 1413 */         if (timer != null)
/* 1414 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }

			private void deleteDependants(int id)
/*      */   {
/* 1330 */     Set emptyStrings = Collections.emptySet();
/* 1331 */     deleteDependants(id, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(int id, Set<String> exclusionTables)
/*      */   {
/* 1336 */     tidyHierarchies(id);
/*      */ 
/* 1338 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1340 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1342 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1344 */       PreparedStatement stmt = null;
/*      */ 
/* 1346 */       int resultCount = 0;
/* 1347 */       String s = null;
/*      */       try
/*      */       {
/* 1350 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 1352 */         if (this._log.isDebugEnabled()) {
/* 1353 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1355 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1358 */         int col = 1;
/* 1359 */         stmt.setInt(col++, id);
/*      */ 
/* 1362 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*      */       }
/*      */       finally
/*      */       {
/* 1370 */         closeStatement(stmt);
/* 1371 */         closeConnection();
/*      */ 
/* 1373 */         if (timer != null) {
/* 1374 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1378 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1380 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1382 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1384 */       PreparedStatement stmt = null;
/*      */ 
/* 1386 */       int resultCount = 0;
/* 1387 */       String s = null;
/*      */       try
/*      */       {
/* 1390 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1392 */         if (this._log.isDebugEnabled()) {
/* 1393 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1395 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1398 */         int col = 1;
/* 1399 */         stmt.setInt(col++, id);
/*      */ 
/* 1402 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*      */       }
/*      */       finally
/*      */       {
/* 1410 */         closeStatement(stmt);
/* 1411 */         closeConnection();
/*      */ 
/* 1413 */         if (timer != null)
/* 1414 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public DimensionEVO getDetails(DimensionPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1441 */     return getDetails(new DimensionCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public DimensionEVO getDetails(DimensionCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1474 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1477 */     if (this.mDetails == null) {
/* 1478 */       doLoad(paramCK.getDimensionPK());
/*      */     }
/* 1480 */     else if (!this.mDetails.getPK().equals(paramCK.getDimensionPK())) {
/* 1481 */       doLoad(paramCK.getDimensionPK());
/*      */     }
/* 1483 */     else if (!checkIfValid())
/*      */     {
/* 1485 */       this._log.info("getDetails", "[ALERT] DimensionEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1487 */       doLoad(paramCK.getDimensionPK());
/*      */     }
/*      */ 
/* 1521 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isElementsAllItemsLoaded()))
/*      */     {
/* 1526 */       this.mDetails.setElements(getDimensionElementDAO().getAll(this.mDetails.getDimensionId(), dependants, this.mDetails.getElements()));
/*      */ 
/* 1533 */       this.mDetails.setElementsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1537 */     if ((dependants.indexOf("<1>") > -1) && (!this.mDetails.isCalendarSpecAllItemsLoaded()))
/*      */     {
/* 1542 */       this.mDetails.setCalendarSpec(getCalendarSpecDAO().getAll(this.mDetails.getDimensionId(), dependants, this.mDetails.getCalendarSpec()));
/*      */ 
/* 1549 */       this.mDetails.setCalendarSpecAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1553 */     if ((dependants.indexOf("<2>") > -1) && (!this.mDetails.isCalendarYearSpecsAllItemsLoaded()))
/*      */     {
/* 1558 */       this.mDetails.setCalendarYearSpecs(getCalendarYearSpecDAO().getAll(this.mDetails.getDimensionId(), dependants, this.mDetails.getCalendarYearSpecs()));
/*      */ 
/* 1565 */       this.mDetails.setCalendarYearSpecsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1569 */     if ((dependants.indexOf("<3>") > -1) && (!this.mDetails.isHierarchiesAllItemsLoaded()))
/*      */     {
/* 1574 */       this.mDetails.setHierarchies(getHierarchyDAO().getAll(this.mDetails.getDimensionId(), dependants, this.mDetails.getHierarchies()));
/*      */ 
/* 1581 */       this.mDetails.setHierarchiesAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1585 */     if ((dependants.indexOf("<7>") > -1) && (!this.mDetails.isSecurityRangesAllItemsLoaded()))
/*      */     {
/* 1590 */       this.mDetails.setSecurityRanges(getSecurityRangeDAO().getAll(this.mDetails.getDimensionId(), dependants, this.mDetails.getSecurityRanges()));
/*      */ 
/* 1597 */       this.mDetails.setSecurityRangesAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1600 */     if ((paramCK instanceof DimensionElementCK))
/*      */     {
/* 1602 */       if (this.mDetails.getElements() == null) {
/* 1603 */         this.mDetails.loadElementsItem(getDimensionElementDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1606 */         DimensionElementPK pk = ((DimensionElementCK)paramCK).getDimensionElementPK();
/* 1607 */         DimensionElementEVO evo = this.mDetails.getElementsItem(pk);
/* 1608 */         if (evo == null) {
/* 1609 */           this.mDetails.loadElementsItem(getDimensionElementDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/* 1613 */     else if ((paramCK instanceof CalendarSpecCK))
/*      */     {
/* 1615 */       if (this.mDetails.getCalendarSpec() == null) {
/* 1616 */         this.mDetails.loadCalendarSpecItem(getCalendarSpecDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1619 */         CalendarSpecPK pk = ((CalendarSpecCK)paramCK).getCalendarSpecPK();
/* 1620 */         CalendarSpecEVO evo = this.mDetails.getCalendarSpecItem(pk);
/* 1621 */         if (evo == null) {
/* 1622 */           this.mDetails.loadCalendarSpecItem(getCalendarSpecDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/* 1626 */     else if ((paramCK instanceof CalendarYearSpecCK))
/*      */     {
/* 1628 */       if (this.mDetails.getCalendarYearSpecs() == null) {
/* 1629 */         this.mDetails.loadCalendarYearSpecsItem(getCalendarYearSpecDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1632 */         CalendarYearSpecPK pk = ((CalendarYearSpecCK)paramCK).getCalendarYearSpecPK();
/* 1633 */         CalendarYearSpecEVO evo = this.mDetails.getCalendarYearSpecsItem(pk);
/* 1634 */         if (evo == null) {
/* 1635 */           this.mDetails.loadCalendarYearSpecsItem(getCalendarYearSpecDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/* 1639 */     else if ((paramCK instanceof HierarchyCK))
/*      */     {
/* 1641 */       if (this.mDetails.getHierarchies() == null) {
/* 1642 */         this.mDetails.loadHierarchiesItem(getHierarchyDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1645 */         HierarchyPK pk = ((HierarchyCK)paramCK).getHierarchyPK();
/* 1646 */         HierarchyEVO evo = this.mDetails.getHierarchiesItem(pk);
/* 1647 */         if (evo == null)
/* 1648 */           this.mDetails.loadHierarchiesItem(getHierarchyDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1650 */           getHierarchyDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/* 1654 */     else if ((paramCK instanceof SecurityRangeCK))
/*      */     {
/* 1656 */       if (this.mDetails.getSecurityRanges() == null) {
/* 1657 */         this.mDetails.loadSecurityRangesItem(getSecurityRangeDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1660 */         SecurityRangePK pk = ((SecurityRangeCK)paramCK).getSecurityRangePK();
/* 1661 */         SecurityRangeEVO evo = this.mDetails.getSecurityRangesItem(pk);
/* 1662 */         if (evo == null)
/* 1663 */           this.mDetails.loadSecurityRangesItem(getSecurityRangeDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1665 */           getSecurityRangeDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1670 */     DimensionEVO details = new DimensionEVO();
/* 1671 */     details = this.mDetails.deepClone();
/*      */ 
/* 1673 */     if (timer != null) {
/* 1674 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1676 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1686 */     boolean stillValid = false;
/* 1687 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1688 */     PreparedStatement stmt = null;
/* 1689 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1692 */       stmt = getConnection().prepareStatement("select VERSION_NUM from DIMENSION where   DIMENSION_ID = ?");
/* 1693 */       int col = 1;
/* 1694 */       stmt.setInt(col++, this.mDetails.getDimensionId());
/*      */ 
/* 1696 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1698 */       if (!resultSet.next()) {
/* 1699 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1701 */       col = 1;
/* 1702 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1704 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1705 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1709 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from DIMENSION where   DIMENSION_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1713 */       closeResultSet(resultSet);
/* 1714 */       closeStatement(stmt);
/* 1715 */       closeConnection();
/*      */ 
/* 1717 */       if (timer != null) {
/* 1718 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1721 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public DimensionEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1727 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1729 */     if (!checkIfValid())
/*      */     {
/* 1731 */       this._log.info("getDetails", "Dimension " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1732 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1736 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1739 */     DimensionEVO details = this.mDetails.deepClone();
/*      */ 
/* 1741 */     if (timer != null) {
/* 1742 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1744 */     return details;
/*      */   }
/*      */ 
/*      */   protected DimensionElementDAO getDimensionElementDAO()
/*      */   {
/* 1753 */     if (this.mDimensionElementDAO == null)
/*      */     {
/* 1755 */       if (this.mDataSource != null)
/* 1756 */         this.mDimensionElementDAO = new DimensionElementDAO(this.mDataSource);
/*      */       else {
/* 1758 */         this.mDimensionElementDAO = new DimensionElementDAO(getConnection());
/*      */       }
/*      */     }
/* 1761 */     return this.mDimensionElementDAO;
/*      */   }
/*      */ 
/*      */   protected CalendarSpecDAO getCalendarSpecDAO()
/*      */   {
/* 1770 */     if (this.mCalendarSpecDAO == null)
/*      */     {
/* 1772 */       if (this.mDataSource != null)
/* 1773 */         this.mCalendarSpecDAO = new CalendarSpecDAO(this.mDataSource);
/*      */       else {
/* 1775 */         this.mCalendarSpecDAO = new CalendarSpecDAO(getConnection());
/*      */       }
/*      */     }
/* 1778 */     return this.mCalendarSpecDAO;
/*      */   }
/*      */ 
/*      */   protected CalendarYearSpecDAO getCalendarYearSpecDAO()
/*      */   {
/* 1787 */     if (this.mCalendarYearSpecDAO == null)
/*      */     {
/* 1789 */       if (this.mDataSource != null)
/* 1790 */         this.mCalendarYearSpecDAO = new CalendarYearSpecDAO(this.mDataSource);
/*      */       else {
/* 1792 */         this.mCalendarYearSpecDAO = new CalendarYearSpecDAO(getConnection());
/*      */       }
/*      */     }
/* 1795 */     return this.mCalendarYearSpecDAO;
/*      */   }
/*      */ 
/*      */   protected HierarchyDAO getHierarchyDAO()
/*      */   {
/* 1804 */     if (this.mHierarchyDAO == null)
/*      */     {
/* 1806 */       if (this.mDataSource != null)
/* 1807 */         this.mHierarchyDAO = new HierarchyDAO(this.mDataSource);
/*      */       else {
/* 1809 */         this.mHierarchyDAO = new HierarchyDAO(getConnection());
/*      */       }
/*      */     }
/* 1812 */     return this.mHierarchyDAO;
/*      */   }
/*      */ 
/*      */   protected SecurityRangeDAO getSecurityRangeDAO()
/*      */   {
/* 1821 */     if (this.mSecurityRangeDAO == null)
/*      */     {
/* 1823 */       if (this.mDataSource != null)
/* 1824 */         this.mSecurityRangeDAO = new SecurityRangeDAO(this.mDataSource);
/*      */       else {
/* 1826 */         this.mSecurityRangeDAO = new SecurityRangeDAO(getConnection());
/*      */       }
/*      */     }
/* 1829 */     return this.mSecurityRangeDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1834 */     return "Dimension";
/*      */   }
/*      */ 
/*      */   public DimensionRef getRef(DimensionPK paramDimensionPK)
/*      */     throws ValidationException
/*      */   {
/* 1840 */     DimensionEVO evo = getDetails(paramDimensionPK, "");
/* 1841 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public HierarchyCK getHierarchyCK(HierarchyPK paramHierarchyPK)
/*      */   {
/* 1982 */     HierarchyRefImpl ref = new HierarchyDAO().getRef(paramHierarchyPK);
/* 1983 */     return (HierarchyCK)ref.getPrimaryKey();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1994 */     if (c == null)
/* 1995 */       return;
/* 1996 */     Iterator iter = c.iterator();
/* 1997 */     while (iter.hasNext())
/*      */     {
/* 1999 */       DimensionEVO evo = (DimensionEVO)iter.next();
/* 2000 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(DimensionEVO evo, String dependants)
/*      */   {
/* 2014 */     if (evo.getDimensionId() < 1) {
/* 2015 */       return;
/*      */     }
/*      */ 
/* 2039 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 2042 */       if (!evo.isElementsAllItemsLoaded())
/*      */       {
/* 2044 */         evo.setElements(getDimensionElementDAO().getAll(evo.getDimensionId(), dependants, evo.getElements()));
/*      */ 
/* 2051 */         evo.setElementsAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2056 */     if (dependants.indexOf("<1>") > -1)
/*      */     {
/* 2059 */       if (!evo.isCalendarSpecAllItemsLoaded())
/*      */       {
/* 2061 */         evo.setCalendarSpec(getCalendarSpecDAO().getAll(evo.getDimensionId(), dependants, evo.getCalendarSpec()));
/*      */ 
/* 2068 */         evo.setCalendarSpecAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2073 */     if (dependants.indexOf("<2>") > -1)
/*      */     {
/* 2076 */       if (!evo.isCalendarYearSpecsAllItemsLoaded())
/*      */       {
/* 2078 */         evo.setCalendarYearSpecs(getCalendarYearSpecDAO().getAll(evo.getDimensionId(), dependants, evo.getCalendarYearSpecs()));
/*      */ 
/* 2085 */         evo.setCalendarYearSpecsAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2090 */     if ((dependants.indexOf("<3>") > -1) || (dependants.indexOf("<4>") > -1) || (dependants.indexOf("<5>") > -1) || (dependants.indexOf("<6>") > -1))
/*      */     {
/* 2096 */       if (!evo.isHierarchiesAllItemsLoaded())
/*      */       {
/* 2098 */         evo.setHierarchies(getHierarchyDAO().getAll(evo.getDimensionId(), dependants, evo.getHierarchies()));
/*      */ 
/* 2105 */         evo.setHierarchiesAllItemsLoaded(true);
/*      */       }
/* 2107 */       getHierarchyDAO().getDependants(evo.getHierarchies(), dependants);
/*      */     }
/*      */ 
/* 2111 */     if ((dependants.indexOf("<7>") > -1) || (dependants.indexOf("<8>") > -1))
/*      */     {
/* 2115 */       if (!evo.isSecurityRangesAllItemsLoaded())
/*      */       {
/* 2117 */         evo.setSecurityRanges(getSecurityRangeDAO().getAll(evo.getDimensionId(), dependants, evo.getSecurityRanges()));
/*      */ 
/* 2124 */         evo.setSecurityRangesAllItemsLoaded(true);
/*      */       }
/* 2126 */       getSecurityRangeDAO().getDependants(evo.getSecurityRanges(), dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public ModelRefImpl queryModelOwner(DimensionPK dimPK)
/*      */   {
/* 2146 */     PreparedStatement stmt = null;
/* 2147 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 2150 */       stmt = getConnection().prepareStatement("select m.MODEL_ID, m.VIS_ID \nfrom MODEL_DIMENSION_REL mdr, MODEL m\nwhere DIMENSION_ID = ? and \n      m.model_id = mdr.model_id");
/* 2151 */       stmt.setInt(1, dimPK.getDimensionId());
/* 2152 */       resultSet = stmt.executeQuery();
/* 2157 */       ModelRefImpl localModelRefImpl = null;
/* 2153 */       if (resultSet.next()) {
/* 2154 */         localModelRefImpl = new ModelRefImpl(new ModelPK(resultSet.getInt("MODEL_ID")), resultSet.getString("VIS_ID"));
/*      */         return localModelRefImpl;
/*      */       }
/*      */       return localModelRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2161 */       throw new RuntimeException(getEntityName() + " isInUse", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2165 */       closeResultSet(resultSet);
/* 2166 */       closeStatement(stmt);
/* 2167 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public EntityList queryDimensionOwners(int dimensionType)
/*      */   {
/* 2203 */     PreparedStatement ps = null;
/* 2204 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2207 */       	EntityListImpl e = new EntityListImpl(new String[]{"Dimension"}, new Object[0][]);
					ps = this.getConnection().prepareStatement("select d.dimension_id, d.vis_id, mdr.model_id,\n( select vis_id from model m where mdr.model_id = m.model_id ) model_visid \nfrom dimension d, model_dimension_rel mdr\nwhere d.type = ? and \nd.dimension_id not in \n(\n\tselect dimension_id from \n\t(\n\t\tselect d.dimension_id, count(*) as nh\n\t\tfrom dimension d, hierarchy h\n\t\twhere d.dimension_id = h.dimension_id and\n\t\t\t  d.type = 3 \n\t\tgroup by d.dimension_id\n\t) \n\twhere nh >= 1\n\tunion all \n\tselect -1 from dual\n) and d.external_system_ref is null and d.dimension_id = mdr.dimension_id (+) ");
					ps.setInt(1, dimensionType);
					rs = ps.executeQuery();
					
					while(rs.next()) {
					   int id = rs.getInt("dimension_id");
					   String visId = rs.getString("vis_id");
					   DimensionRefImpl dimRef = new DimensionRefImpl(new DimensionPK(id), visId, dimensionType);
					   ArrayList l = new ArrayList();
					   l.add(dimRef);
					   e.add(l);
					}
					
					EntityListImpl id1 = e;
					return id1;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2226 */       throw handleSQLException("queryDimensionOwners", e);
/*      */     }
/*      */     finally
/*      */     {
/* 2230 */       closeResultSet(rs);
/* 2231 */       closeStatement(ps);
/* 2232 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */ 
/*      */   public void tidyHierarchies(DimensionPK dimensionPK)
/*      */   {
/* 2239 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2240 */     CallableStatement cs = null;
/*      */ 
/* 2242 */     this._log.info("tidyHierarchies()", " dimensionId=" + this.mDetails.getPK());
/*      */     try
/*      */     {
/* 2246 */       cs = getConnection().prepareCall("{ call DIMENSION_UTILS.TERM_DIMENSION( ? ) }");
/* 2247 */       cs.setInt(1, dimensionPK.getDimensionId());
/* 2248 */       cs.execute();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2252 */       throw handleSQLException("Failed calling dimension_utils.term_dimension", e);
/*      */     }
/*      */     finally
/*      */     {
/* 2256 */       closeStatement(cs);
/* 2257 */       closeConnection();
/*      */     }
/*      */ 
/* 2260 */     if (timer != null)
/* 2261 */       timer.logDebug("dimension_utils.term_dimension", this.mDetails.getPK());
/*      */   }
			 
			 public void tidyHierarchies(int id)
/*      */   {
/* 2239 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2240 */     CallableStatement cs = null;
/*      */ 
/* 2242 */     this._log.info("tidyHierarchies()", " dimensionId=" + id);
/*      */     try
/*      */     {
/* 2246 */       cs = getConnection().prepareCall("{ call DIMENSION_UTILS.TERM_DIMENSION( ? ) }");
/* 2247 */       cs.setInt(1, id);
/* 2248 */       cs.execute();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2252 */       throw handleSQLException("Failed calling dimension_utils.term_dimension", e);
/*      */     }
/*      */     finally
/*      */     {
/* 2256 */       closeStatement(cs);
/* 2257 */       closeConnection();
/*      */     }
/*      */ 
/* 2260 */     if (timer != null)
/* 2261 */       timer.logDebug("dimension_utils.term_dimension", id);
/*      */   }
/*      */ 
/*      */   public void primeDimensionRuntime()
/*      */     throws SQLException
/*      */   {
/* 2268 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2269 */     CallableStatement cs = null;
/*      */ 
/* 2271 */     this._log.info("primeDimensionRuntime()", " dimensionId=" + this.mDetails.getPK());
/*      */     try
/*      */     {
/* 2275 */       cs = getConnection().prepareCall("{ call DIMENSION_UTILS.INIT_DIMENSION( ? ) }");
/* 2276 */       cs.setInt(1, this.mDetails.getDimensionId());
/* 2277 */       cs.execute();
/*      */     }
/*      */     finally
/*      */     {
/* 2281 */       closeStatement(cs);
/* 2282 */       closeConnection();
/*      */     }
/*      */ 
/* 2285 */     if (timer != null)
/* 2286 */       timer.logDebug("primeDimensionRuntime", this.mDetails.getPK());
/*      */   }

			public AllDimensionsELO getDimensionsForLoggedUser(int userId) {
			   Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  849 */     PreparedStatement stmt = null;
/*  850 */     ResultSet resultSet = null;
/*  851 */     AllDimensionsELO results = new AllDimensionsELO();
/*      */     try
/*      */     {
/*  854 */       stmt = getConnection().prepareStatement(SQL_ALL_DIMENSIONS_FOR_USER);
/*  855 */       int col = 1;
				 stmt.setInt(col++, userId);
/*  856 */       resultSet = stmt.executeQuery();
/*  857 */       while (resultSet.next())
/*      */       {
/*  859 */         col = 2;
/*      */ 
/*  862 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  865 */         String textDimension = resultSet.getString(col++);
/*  866 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/*  869 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  873 */         String textModelDimensionRel = "";
/*      */ 
/*  875 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  878 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  881 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/*  888 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
/*      */ 
/*  894 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  899 */         int col1 = resultSet.getInt(col++);
/*  900 */         String col2 = resultSet.getString(col++);
/*  901 */         int col3 = resultSet.getInt(col++);
/*  902 */         int col4 = resultSet.getInt(col++);
/*      */ 
/*  905 */         results.add(erDimension, erModelDimensionRel, erModel, col1, col2, col3, col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  918 */       throw handleSQLException(SQL_ALL_DIMENSIONS_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  922 */       closeResultSet(resultSet);
/*  923 */       closeStatement(stmt);
/*  924 */       closeConnection();
/*      */     }
/*      */ 
/*  927 */     if (timer != null) {
/*  928 */       timer.logDebug("getAllDimensions", " items=" + results.size());
/*      */     }
/*      */ 
/*  932 */     return results;
			}

/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.dimension.DimensionDAO
 * JD-Core Version:    0.6.0
 */