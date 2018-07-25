/*      */ package com.cedar.cp.ejb.impl.model.mapping;
/*      */ 
/*      */ import com.cedar.cp.api.base.CPException;
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.model.ModelRef;
/*      */ import com.cedar.cp.api.model.mapping.MappedModelRef;
/*      */ import com.cedar.cp.dto.extsys.ExternalSystemPK;
/*      */ import com.cedar.cp.dto.extsys.ExternalSystemRefImpl;
/*      */ import com.cedar.cp.dto.model.FinanceCubePK;
/*      */ import com.cedar.cp.dto.model.FinanceCubeRefImpl;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.mapping.AllMappedModelsELO;
/*      */ import com.cedar.cp.dto.model.mapping.MappedCalendarYearCK;
/*      */ import com.cedar.cp.dto.model.mapping.MappedCalendarYearPK;
/*      */ import com.cedar.cp.dto.model.mapping.MappedDimensionCK;
/*      */ import com.cedar.cp.dto.model.mapping.MappedDimensionPK;
/*      */ import com.cedar.cp.dto.model.mapping.MappedFinanceCubeCK;
/*      */ import com.cedar.cp.dto.model.mapping.MappedFinanceCubePK;
/*      */ import com.cedar.cp.dto.model.mapping.MappedFinanceCubeRefImpl;
/*      */ import com.cedar.cp.dto.model.mapping.MappedFinanceCubesELO;
/*      */ import com.cedar.cp.dto.model.mapping.MappedModelCK;
/*      */ import com.cedar.cp.dto.model.mapping.MappedModelPK;
/*      */ import com.cedar.cp.dto.model.mapping.MappedModelRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class MappedModelDAO extends AbstractDAO
/*      */ {
/*   56 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select MAPPED_MODEL_ID from MAPPED_MODEL where    MAPPED_MODEL_ID = ? ";
			 protected static final String SQL_GET_MODEL_FROM_PRIMARY_KEY = "select MODEL_ID from MAPPED_MODEL where    MAPPED_MODEL_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select MAPPED_MODEL.MAPPED_MODEL_ID,MAPPED_MODEL.MODEL_ID,MAPPED_MODEL.EXTERNAL_SYSTEM_ID,MAPPED_MODEL.COMPANY_VIS_ID,MAPPED_MODEL.LEDGER_VIS_ID,MAPPED_MODEL.VERSION_NUM,MAPPED_MODEL.UPDATED_BY_USER_ID,MAPPED_MODEL.UPDATED_TIME,MAPPED_MODEL.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from MAPPED_MODEL where    MAPPED_MODEL_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into MAPPED_MODEL ( MAPPED_MODEL_ID,MODEL_ID,EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update MAPPED_MODEL_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from MAPPED_MODEL_SEQ";
/*      */   protected static final String SQL_STORE = "update MAPPED_MODEL set MODEL_ID = ?,EXTERNAL_SYSTEM_ID = ?,COMPANY_VIS_ID = ?,LEDGER_VIS_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_MODEL_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from MAPPED_MODEL where MAPPED_MODEL_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from MAPPED_MODEL where    MAPPED_MODEL_ID = ? ";
/*  690 */   protected static String SQL_ALL_MAPPED_MODELS = "select 0       ,MAPPED_MODEL.MAPPED_MODEL_ID      ,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID      ,EXTERNAL_SYSTEM.VIS_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MAPPED_MODEL.MAPPED_MODEL_ID      ,EXTERNAL_SYSTEM.SYSTEM_TYPE      ,MAPPED_MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MODEL.DESCRIPTION      ,EXTERNAL_SYSTEM.LOCATION      ,MAPPED_MODEL.COMPANY_VIS_ID      ,MAPPED_MODEL.LEDGER_VIS_ID from MAPPED_MODEL    ,EXTERNAL_SYSTEM    ,MODEL where MAPPED_MODEL.GLOBAL='N'  and  MAPPED_MODEL.MODEL_ID = MODEL.MODEL_ID and EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID = MAPPED_MODEL.EXTERNAL_SYSTEM_ID order by MODEL.VIS_ID";
/*      */   protected static String SQL_ALL_MAPPED_MODELS_FOR_USER = "select 0       ,MAPPED_MODEL.MAPPED_MODEL_ID      ,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID      ,EXTERNAL_SYSTEM.VIS_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MAPPED_MODEL.MAPPED_MODEL_ID      ,EXTERNAL_SYSTEM.SYSTEM_TYPE      ,MAPPED_MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MODEL.DESCRIPTION      ,EXTERNAL_SYSTEM.LOCATION      ,MAPPED_MODEL.COMPANY_VIS_ID      ,MAPPED_MODEL.LEDGER_VIS_ID from MAPPED_MODEL    ,EXTERNAL_SYSTEM    ,MODEL where model.model_id in (select distinct model_id from budget_user where user_id = ?) AND MAPPED_MODEL.GLOBAL='N'  and  MAPPED_MODEL.MODEL_ID = MODEL.MODEL_ID and EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID = MAPPED_MODEL.EXTERNAL_SYSTEM_ID order by MODEL.VIS_ID";
/*  822 */   protected static String SQL_MAPPED_FINANCE_CUBES = "select 0       ,MAPPED_MODEL.MAPPED_MODEL_ID      ,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID      ,EXTERNAL_SYSTEM.VIS_ID      ,MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MAPPED_MODEL.MAPPED_MODEL_ID      ,EXTERNAL_SYSTEM.SYSTEM_TYPE      ,MAPPED_MODEL.MODEL_ID      ,MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.DESCRIPTION from MAPPED_MODEL    ,EXTERNAL_SYSTEM    ,MAPPED_FINANCE_CUBE    ,FINANCE_CUBE    ,MODEL where 1=1  and  MAPPED_MODEL.MAPPED_MODEL_ID = ? and MAPPED_MODEL.MODEL_ID = MODEL.MODEL_ID and MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID and EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID = MAPPED_MODEL.EXTERNAL_SYSTEM_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MAPPED_FINANCE_CUBE.FINANCE_CUBE_ID order by FINANCE_CUBE.VIS_ID";
/*      */ 
/*  982 */   private static String[][] SQL_DELETE_CHILDREN = { { "MAPPED_CALENDAR_YEAR", "delete from MAPPED_CALENDAR_YEAR where     MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID = ? " }, { "MAPPED_FINANCE_CUBE", "delete from MAPPED_FINANCE_CUBE where     MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID = ? " }, { "MAPPED_DIMENSION", "delete from MAPPED_DIMENSION where     MAPPED_DIMENSION.MAPPED_MODEL_ID = ? " } };
/*      */ 
/* 1001 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "MAPPED_CALENDAR_ELEMENT", "delete from MAPPED_CALENDAR_ELEMENT MappedCalendarElement where exists (select * from MAPPED_CALENDAR_ELEMENT,MAPPED_CALENDAR_YEAR,MAPPED_MODEL where     MAPPED_CALENDAR_ELEMENT.MAPPED_CALENDAR_YEAR_ID = MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID and MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID and MappedCalendarElement.MAPPED_CALENDAR_YEAR_ID = MAPPED_CALENDAR_ELEMENT.MAPPED_CALENDAR_YEAR_ID " }, { "MAPPED_DATA_TYPE", "delete from MAPPED_DATA_TYPE MappedDataType where exists (select * from MAPPED_DATA_TYPE,MAPPED_FINANCE_CUBE,MAPPED_MODEL where     MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID = MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID and MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID and MappedDataType.MAPPED_FINANCE_CUBE_ID = MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID " }, { "MAPPED_DIMENSION_ELEMENT", "delete from MAPPED_DIMENSION_ELEMENT MappedDimensionElement where exists (select * from MAPPED_DIMENSION_ELEMENT,MAPPED_DIMENSION,MAPPED_MODEL where     MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID = MAPPED_DIMENSION.MAPPED_DIMENSION_ID and MAPPED_DIMENSION.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID and MappedDimensionElement.MAPPED_DIMENSION_ID = MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID " }, { "MAPPED_HIERARCHY", "delete from MAPPED_HIERARCHY MappedHierarchy where exists (select * from MAPPED_HIERARCHY,MAPPED_DIMENSION,MAPPED_MODEL where     MAPPED_HIERARCHY.MAPPED_DIMENSION_ID = MAPPED_DIMENSION.MAPPED_DIMENSION_ID and MAPPED_DIMENSION.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID and MappedHierarchy.MAPPED_DIMENSION_ID = MAPPED_HIERARCHY.MAPPED_DIMENSION_ID " } };
/*      */ 
/* 1049 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and MAPPED_MODEL.MAPPED_MODEL_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from MAPPED_MODEL where   MAPPED_MODEL_ID = ?";
/*      */   public static final String SQL_GET_MAPPED_CALENDAR_YEAR_REF = "select 0,MAPPED_MODEL.MAPPED_MODEL_ID from MAPPED_CALENDAR_YEAR,MAPPED_MODEL where 1=1 and MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID = ? and MAPPED_CALENDAR_YEAR.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID";
/*      */   public static final String SQL_GET_MAPPED_CALENDAR_ELEMENT_REF = "select 0,MAPPED_MODEL.MAPPED_MODEL_ID,MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID from MAPPED_CALENDAR_ELEMENT,MAPPED_MODEL,MAPPED_CALENDAR_YEAR where 1=1 and MAPPED_CALENDAR_ELEMENT.MAPPED_CALENDAR_ELEMENT_ID = ? and MAPPED_CALENDAR_ELEMENT.MAPPED_CALENDAR_YEAR_ID = MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID and MAPPED_CALENDAR_YEAR.MAPPED_CALENDAR_YEAR_ID = MAPPED_MODEL.MAPPED_CALENDAR_YEAR_ID";
/*      */   public static final String SQL_GET_MAPPED_FINANCE_CUBE_REF = "select 0,MAPPED_MODEL.MAPPED_MODEL_ID from MAPPED_FINANCE_CUBE,MAPPED_MODEL where 1=1 and MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID = ? and MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID";
/*      */   public static final String SQL_GET_MAPPED_DATA_TYPE_REF = "select 0,MAPPED_MODEL.MAPPED_MODEL_ID,MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID from MAPPED_DATA_TYPE,MAPPED_MODEL,MAPPED_FINANCE_CUBE where 1=1 and MAPPED_DATA_TYPE.MAPPED_DATA_TYPE_ID = ? and MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID = MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID and MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID = MAPPED_MODEL.MAPPED_FINANCE_CUBE_ID";
/*      */   public static final String SQL_GET_MAPPED_DIMENSION_REF = "select 0,MAPPED_MODEL.MAPPED_MODEL_ID from MAPPED_DIMENSION,MAPPED_MODEL where 1=1 and MAPPED_DIMENSION.MAPPED_DIMENSION_ID = ? and MAPPED_DIMENSION.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID";
/*      */   public static final String SQL_GET_MAPPED_DIMENSION_ELEMENT_REF = "select 0,MAPPED_MODEL.MAPPED_MODEL_ID,MAPPED_DIMENSION.MAPPED_DIMENSION_ID from MAPPED_DIMENSION_ELEMENT,MAPPED_MODEL,MAPPED_DIMENSION where 1=1 and MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ELEMENT_ID = ? and MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID = MAPPED_DIMENSION.MAPPED_DIMENSION_ID and MAPPED_DIMENSION.MAPPED_DIMENSION_ID = MAPPED_MODEL.MAPPED_DIMENSION_ID";
/*      */   public static final String SQL_GET_MAPPED_HIERARCHY_REF = "select 0,MAPPED_MODEL.MAPPED_MODEL_ID,MAPPED_DIMENSION.MAPPED_DIMENSION_ID from MAPPED_HIERARCHY,MAPPED_MODEL,MAPPED_DIMENSION where 1=1 and MAPPED_HIERARCHY.MAPPED_HIERARCHY_ID = ? and MAPPED_HIERARCHY.MAPPED_DIMENSION_ID = MAPPED_DIMENSION.MAPPED_DIMENSION_ID and MAPPED_DIMENSION.MAPPED_DIMENSION_ID = MAPPED_MODEL.MAPPED_DIMENSION_ID";
/*      */   private static final String GET_MAPPED_MODEL_SQL = "select mapped_model_id from mapped_model mm where mm.model_id = ?";
/*      */   protected MappedCalendarYearDAO mMappedCalendarYearDAO;
/*      */   protected MappedFinanceCubeDAO mMappedFinanceCubeDAO;
/*      */   protected MappedDimensionDAO mMappedDimensionDAO;
/*      */   protected MappedModelEVO mDetails;
/*      */ 
/*      */   public MappedModelDAO(Connection connection)
/*      */   {
/*   63 */     super(connection);
/*      */   }
/*      */ 
/*      */   public MappedModelDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public MappedModelDAO(DataSource ds)
/*      */   {
/*   79 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected MappedModelPK getPK()
/*      */   {
/*   87 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(MappedModelEVO details)
/*      */   {
/*   96 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public MappedModelEVO setAndGetDetails(MappedModelEVO details, String dependants)
/*      */   {
/*  107 */     setDetails(details);
/*  108 */     generateKeys();
/*  109 */     getDependants(this.mDetails, dependants);
/*  110 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public MappedModelPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  119 */     doCreate();
/*      */ 
/*  121 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(MappedModelPK pk)
/*      */     throws ValidationException
/*      */   {
/*  131 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  140 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  149 */     doRemove();
/*      */   }
/*      */ 
/*      */   public MappedModelPK findByPrimaryKey(MappedModelPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  158 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  159 */     if (exists(pk_))
/*      */     {
/*  161 */       if (timer != null) {
/*  162 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  164 */       return pk_;
/*      */     }
/*      */ 
/*  167 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(MappedModelPK pk)
/*      */   {
/*  185 */     PreparedStatement stmt = null;
/*  186 */     ResultSet resultSet = null;
/*  187 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  191 */       stmt = getConnection().prepareStatement("select MAPPED_MODEL_ID from MAPPED_MODEL where    MAPPED_MODEL_ID = ? ");
/*      */ 
/*  193 */       int col = 1;
/*  194 */       stmt.setInt(col++, pk.getMappedModelId());
/*      */ 
/*  196 */       resultSet = stmt.executeQuery();
/*      */ 
/*  198 */       if (!resultSet.next())
/*  199 */         returnValue = false;
/*      */       else
/*  201 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  205 */       throw handleSQLException(pk, "select MAPPED_MODEL_ID from MAPPED_MODEL where    MAPPED_MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  209 */       closeResultSet(resultSet);
/*  210 */       closeStatement(stmt);
/*  211 */       closeConnection();
/*      */     }
/*  213 */     return returnValue;
/*      */   }

			 public int getModelID(MappedModelPK pk)
/*      */   {
/*  185 */     PreparedStatement stmt = null;
/*  186 */     ResultSet resultSet = null;
/*  187 */     int returnValue;
/*      */     try
/*      */     {
/*  191 */       stmt = getConnection().prepareStatement(SQL_GET_MODEL_FROM_PRIMARY_KEY);
/*      */ 
/*  193 */       int col = 1;
/*  194 */       stmt.setInt(col++, pk.getMappedModelId());
/*      */ 
/*  196 */       resultSet = stmt.executeQuery();
/*      */ 
/*  198 */       if (!resultSet.next())
/*  199 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       else
/*  201 */         returnValue = resultSet.getInt(1);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  205 */       throw handleSQLException(pk, SQL_GET_MODEL_FROM_PRIMARY_KEY, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  209 */       closeResultSet(resultSet);
/*  210 */       closeStatement(stmt);
/*  211 */       closeConnection();
/*      */     }
/*  213 */     return returnValue;
/*      */   }
			 
			 public String getTaskStatus(String taskId){
				 PreparedStatement stmt = null;
				     ResultSet resultSet = null;
				      String returnValue;
				      try
				      {
				        stmt = getConnection().prepareStatement("select status from task where task_id=?");
				  
				        int col = 1;
				        stmt.setString(col++, taskId);
				  
				        resultSet = stmt.executeQuery();
				  
				        if (!resultSet.next())
				          throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
				        else
				          returnValue = resultSet.getString(1);
				      }
				      catch (SQLException sqle)
				      {
				        throw handleSQLException("select status from task where task_id=?", sqle);
				      }
				      finally
				      {
				        closeResultSet(resultSet);
				        closeStatement(stmt);
				        closeConnection();
				      }
				      return returnValue;			
			 }
			 public String getTaskTime(String taskId){
				 PreparedStatement stmt = null;
				     ResultSet resultSet = null;
				      String returnValue;
				      try
				      {
				        stmt = getConnection().prepareStatement("select step from task where task_id=?");
				  
				        int col = 1;
				        stmt.setString(col++, taskId);
				  
				        resultSet = stmt.executeQuery();
				  
				        if (!resultSet.next())
				          throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
				        else
				          returnValue = resultSet.getString(1);
				      }
				      catch (SQLException sqle)
				      {
				        throw handleSQLException("select status from task where task_id=?", sqle);
				      }
				      finally
				      {
				        closeResultSet(resultSet);
				        closeStatement(stmt);
				        closeConnection();
				      }
				      return returnValue;			
			 }
			 public String deleteFailedTask(String taskId){
				 PreparedStatement stmt = null;
				     ResultSet resultSet = null;
				      String returnValue;
				      try
				      {
				        stmt = getConnection().prepareStatement("delete from TASK where TASK_ID=?");
				  
				        int col = 1;
				        stmt.setString(col++, taskId);
				  
				        resultSet = stmt.executeQuery();
				  				        
				        String sql1 = "delete from CHANGE_MGMT";
				        stmt = getConnection().prepareStatement(sql1);
				        resultSet = stmt.executeQuery();
				        returnValue = "OK";
				      }
				      catch (SQLException sqle)
				      {
				        throw handleSQLException("delete from TASK where TASK_ID=?", sqle);
				      }
				      finally
				      {
				        closeResultSet(resultSet);
				        closeStatement(stmt);
				        closeConnection();
				      }
				      return returnValue;			
			 }
			 
/*      */ 
/*      */   private MappedModelEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  233 */     int col = 1;
/*  234 */     MappedModelEVO evo = new MappedModelEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), null, null, null);
/*      */ 
/*  246 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  247 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  248 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  249 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(MappedModelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  254 */     int col = startCol_;
/*  255 */     stmt_.setInt(col++, evo_.getMappedModelId());
/*  256 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(MappedModelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  261 */     int col = startCol_;
/*  262 */     stmt_.setInt(col++, evo_.getModelId());
/*  263 */     stmt_.setInt(col++, evo_.getExternalSystemId());
/*  264 */     stmt_.setString(col++, evo_.getCompanyVisId());
/*  265 */     stmt_.setString(col++, evo_.getLedgerVisId());
/*  266 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  267 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  268 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  269 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  270 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(MappedModelPK pk)
/*      */     throws ValidationException
/*      */   {
/*  286 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  288 */     PreparedStatement stmt = null;
/*  289 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  293 */       stmt = getConnection().prepareStatement("select MAPPED_MODEL.MAPPED_MODEL_ID,MAPPED_MODEL.MODEL_ID,MAPPED_MODEL.EXTERNAL_SYSTEM_ID,MAPPED_MODEL.COMPANY_VIS_ID,MAPPED_MODEL.LEDGER_VIS_ID,MAPPED_MODEL.VERSION_NUM,MAPPED_MODEL.UPDATED_BY_USER_ID,MAPPED_MODEL.UPDATED_TIME,MAPPED_MODEL.CREATED_TIME from MAPPED_MODEL where    MAPPED_MODEL_ID = ? ");
/*      */ 
/*  296 */       int col = 1;
/*  297 */       stmt.setInt(col++, pk.getMappedModelId());
/*      */ 
/*  299 */       resultSet = stmt.executeQuery();
/*      */ 
/*  301 */       if (!resultSet.next()) {
/*  302 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  305 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  306 */       if (this.mDetails.isModified())
/*  307 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  311 */       throw handleSQLException(pk, "select MAPPED_MODEL.MAPPED_MODEL_ID,MAPPED_MODEL.MODEL_ID,MAPPED_MODEL.EXTERNAL_SYSTEM_ID,MAPPED_MODEL.COMPANY_VIS_ID,MAPPED_MODEL.LEDGER_VIS_ID,MAPPED_MODEL.VERSION_NUM,MAPPED_MODEL.UPDATED_BY_USER_ID,MAPPED_MODEL.UPDATED_TIME,MAPPED_MODEL.CREATED_TIME from MAPPED_MODEL where    MAPPED_MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  315 */       closeResultSet(resultSet);
/*  316 */       closeStatement(stmt);
/*  317 */       closeConnection();
/*      */ 
/*  319 */       if (timer != null)
/*  320 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  355 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  356 */     generateKeys();
/*      */ 
/*  358 */     this.mDetails.postCreateInit();
/*      */ 
/*  360 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  365 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  366 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  367 */       stmt = getConnection().prepareStatement("insert into MAPPED_MODEL ( MAPPED_MODEL_ID,MODEL_ID,EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  370 */       int col = 1;
/*  371 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  372 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  375 */       int resultCount = stmt.executeUpdate();
/*  376 */       if (resultCount != 1)
/*      */       {
/*  378 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  381 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  385 */       throw handleSQLException(this.mDetails.getPK(), "insert into MAPPED_MODEL ( MAPPED_MODEL_ID,MODEL_ID,EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  389 */       closeStatement(stmt);
/*  390 */       closeConnection();
/*      */ 
/*  392 */       if (timer != null) {
/*  393 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  399 */       getMappedCalendarYearDAO().update(this.mDetails.getMappedCalendarYearsMap());
/*      */ 
/*  401 */       getMappedFinanceCubeDAO().update(this.mDetails.getMappedFinanceCubesMap());
/*      */ 
/*  403 */       getMappedDimensionDAO().update(this.mDetails.getMappedDimensionsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  409 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  429 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  431 */     PreparedStatement stmt = null;
/*  432 */     ResultSet resultSet = null;
/*  433 */     String sqlString = null;
/*      */     try
/*      */     {
/*  438 */       sqlString = "update MAPPED_MODEL_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  439 */       stmt = getConnection().prepareStatement("update MAPPED_MODEL_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  440 */       stmt.setInt(1, insertCount);
/*      */ 
/*  442 */       int resultCount = stmt.executeUpdate();
/*  443 */       if (resultCount != 1) {
/*  444 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  446 */       closeStatement(stmt);
/*      */ 
/*  449 */       sqlString = "select SEQ_NUM from MAPPED_MODEL_SEQ";
/*  450 */       stmt = getConnection().prepareStatement("select SEQ_NUM from MAPPED_MODEL_SEQ");
/*  451 */       resultSet = stmt.executeQuery();
/*  452 */       if (!resultSet.next())
/*  453 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  454 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  456 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  460 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  464 */       closeResultSet(resultSet);
/*  465 */       closeStatement(stmt);
/*  466 */       closeConnection();
/*      */ 
/*  468 */       if (timer != null)
/*  469 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  469 */     }
/*      */   }
/*      */ 
/*      */   public MappedModelPK generateKeys()
/*      */   {
/*  479 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  481 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  484 */     if (insertCount == 0) {
/*  485 */       return this.mDetails.getPK();
/*      */     }
/*  487 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  489 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  515 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  517 */     generateKeys();
/*      */ 
/*  522 */     PreparedStatement stmt = null;
/*      */ 
/*  524 */     boolean mainChanged = this.mDetails.isModified();
/*  525 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  529 */       if (getMappedCalendarYearDAO().update(this.mDetails.getMappedCalendarYearsMap())) {
/*  530 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  533 */       if (getMappedFinanceCubeDAO().update(this.mDetails.getMappedFinanceCubesMap())) {
/*  534 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  537 */       if (getMappedDimensionDAO().update(this.mDetails.getMappedDimensionsMap())) {
/*  538 */         dependantChanged = true;
/*      */       }
/*  540 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  543 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  546 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  547 */         stmt = getConnection().prepareStatement("update MAPPED_MODEL set MODEL_ID = ?,EXTERNAL_SYSTEM_ID = ?,COMPANY_VIS_ID = ?,LEDGER_VIS_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_MODEL_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  550 */         int col = 1;
/*  551 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  552 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  554 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  557 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  559 */         if (resultCount == 0) {
/*  560 */           checkVersionNum();
/*      */         }
/*  562 */         if (resultCount != 1) {
/*  563 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  566 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  575 */       throw handleSQLException(getPK(), "update MAPPED_MODEL set MODEL_ID = ?,EXTERNAL_SYSTEM_ID = ?,COMPANY_VIS_ID = ?,LEDGER_VIS_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_MODEL_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  579 */       closeStatement(stmt);
/*  580 */       closeConnection();
/*      */ 
/*  582 */       if ((timer != null) && (
/*  583 */         (mainChanged) || (dependantChanged)))
/*  584 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  596 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  597 */     PreparedStatement stmt = null;
/*  598 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  602 */       stmt = getConnection().prepareStatement("select VERSION_NUM from MAPPED_MODEL where MAPPED_MODEL_ID = ?");
/*      */ 
/*  605 */       int col = 1;
/*  606 */       stmt.setInt(col++, this.mDetails.getMappedModelId());
/*      */ 
/*  609 */       resultSet = stmt.executeQuery();
/*      */ 
/*  611 */       if (!resultSet.next()) {
/*  612 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  615 */       col = 1;
/*  616 */       int dbVersionNumber = resultSet.getInt(col++);
/*  617 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  618 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  624 */       throw handleSQLException(getPK(), "select VERSION_NUM from MAPPED_MODEL where MAPPED_MODEL_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  628 */       closeStatement(stmt);
/*  629 */       closeResultSet(resultSet);
/*      */ 
/*  631 */       if (timer != null)
/*  632 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  649 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  650 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  655 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  660 */       stmt = getConnection().prepareStatement("delete from MAPPED_MODEL where    MAPPED_MODEL_ID = ? ");
/*      */ 
/*  663 */       int col = 1;
/*  664 */       stmt.setInt(col++, this.mDetails.getMappedModelId());
/*      */ 
/*  666 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  668 */       if (resultCount != 1) {
/*  669 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  675 */       throw handleSQLException(getPK(), "delete from MAPPED_MODEL where    MAPPED_MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  679 */       closeStatement(stmt);
/*  680 */       closeConnection();
/*      */ 
/*  682 */       if (timer != null)
/*  683 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllMappedModelsELO getAllMappedModels()
/*      */   {
/*  727 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  728 */     PreparedStatement stmt = null;
/*  729 */     ResultSet resultSet = null;
/*  730 */     AllMappedModelsELO results = new AllMappedModelsELO();
/*      */     try
/*      */     {
/*  733 */       stmt = getConnection().prepareStatement(SQL_ALL_MAPPED_MODELS);
/*  734 */       int col = 1;
/*  735 */       resultSet = stmt.executeQuery();
/*  736 */       while (resultSet.next())
/*      */       {
/*  738 */         col = 2;
/*      */ 
/*  741 */         MappedModelPK pkMappedModel = new MappedModelPK(resultSet.getInt(col++));
/*      */ 
/*  744 */         String textMappedModel = "";
/*      */ 
/*  747 */         ExternalSystemPK pkExternalSystem = new ExternalSystemPK(resultSet.getInt(col++));
/*      */ 
/*  750 */         String textExternalSystem = resultSet.getString(col++);
/*      */ 
/*  752 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  755 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  758 */         MappedModelRefImpl erMappedModel = new MappedModelRefImpl(pkMappedModel, textMappedModel);
/*      */ 
/*  764 */         ExternalSystemRefImpl erExternalSystem = new ExternalSystemRefImpl(pkExternalSystem, textExternalSystem);
/*      */ 
/*  770 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  775 */         int col1 = resultSet.getInt(col++);
/*  776 */         int col2 = resultSet.getInt(col++);
/*  777 */         int col3 = resultSet.getInt(col++);
/*  778 */         String col4 = resultSet.getString(col++);
/*  779 */         String col5 = resultSet.getString(col++);
/*  780 */         String col6 = resultSet.getString(col++);
/*  781 */         String col7 = resultSet.getString(col++);
/*  782 */         String col8 = resultSet.getString(col++);
/*      */ 
/*  785 */         results.add(erMappedModel, erExternalSystem, erModel, col1, col2, col3, col4, col5, col6, col7, col8);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  802 */       throw handleSQLException(SQL_ALL_MAPPED_MODELS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  806 */       closeResultSet(resultSet);
/*  807 */       closeStatement(stmt);
/*  808 */       closeConnection();
/*      */     }
/*      */ 
/*  811 */     if (timer != null) {
/*  812 */       timer.logDebug("getAllMappedModels", " items=" + results.size());
/*      */     }
/*      */ 
/*  816 */     return results;
/*      */   }
/*      */ 
/*      */   public MappedFinanceCubesELO getMappedFinanceCubes(int mappedModelId)
/*      */   {
/*  866 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  867 */     PreparedStatement stmt = null;
/*  868 */     ResultSet resultSet = null;
/*  869 */     MappedFinanceCubesELO results = new MappedFinanceCubesELO();
/*      */     try
/*      */     {
/*  872 */       stmt = getConnection().prepareStatement(SQL_MAPPED_FINANCE_CUBES);
/*  873 */       int col = 1;
/*  874 */       stmt.setInt(col++, mappedModelId);
/*  875 */       resultSet = stmt.executeQuery();
/*  876 */       while (resultSet.next())
/*      */       {
/*  878 */         col = 2;
/*      */ 
/*  881 */         MappedModelPK pkMappedModel = new MappedModelPK(resultSet.getInt(col++));
/*      */ 
/*  884 */         String textMappedModel = "";
/*      */ 
/*  887 */         ExternalSystemPK pkExternalSystem = new ExternalSystemPK(resultSet.getInt(col++));
/*      */ 
/*  890 */         String textExternalSystem = resultSet.getString(col++);
/*      */ 
/*  892 */         MappedFinanceCubePK pkMappedFinanceCube = new MappedFinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  895 */         String textMappedFinanceCube = "";
/*      */ 
/*  897 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  900 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  902 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  905 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  908 */         MappedModelRefImpl erMappedModel = new MappedModelRefImpl(pkMappedModel, textMappedModel);
/*      */ 
/*  914 */         ExternalSystemRefImpl erExternalSystem = new ExternalSystemRefImpl(pkExternalSystem, textExternalSystem);
/*      */ 
/*  920 */         MappedFinanceCubeRefImpl erMappedFinanceCube = new MappedFinanceCubeRefImpl(pkMappedFinanceCube, textMappedFinanceCube);
/*      */ 
/*  926 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
/*      */ 
/*  932 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  937 */         int col1 = resultSet.getInt(col++);
/*  938 */         int col2 = resultSet.getInt(col++);
/*  939 */         int col3 = resultSet.getInt(col++);
/*  940 */         int col4 = resultSet.getInt(col++);
/*  941 */         String col5 = resultSet.getString(col++);
/*  942 */         String col6 = resultSet.getString(col++);
/*      */ 
/*  945 */         results.add(erMappedModel, erExternalSystem, erMappedFinanceCube, erFinanceCube, erModel, col1, col2, col3, col4, col5, col6);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  962 */       throw handleSQLException(SQL_MAPPED_FINANCE_CUBES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  966 */       closeResultSet(resultSet);
/*  967 */       closeStatement(stmt);
/*  968 */       closeConnection();
/*      */     }
/*      */ 
/*  971 */     if (timer != null) {
/*  972 */       timer.logDebug("getMappedFinanceCubes", " MappedModelId=" + mappedModelId + " items=" + results.size());
/*      */     }
/*      */ 
/*  977 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(MappedModelPK pk)
/*      */   {
/* 1058 */     Set emptyStrings = Collections.emptySet();
/* 1059 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(MappedModelPK pk, Set<String> exclusionTables)
/*      */   {
/* 1065 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1067 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1069 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1071 */       PreparedStatement stmt = null;
/*      */ 
/* 1073 */       int resultCount = 0;
/* 1074 */       String s = null;
/*      */       try
/*      */       {
/* 1077 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 1079 */         if (this._log.isDebugEnabled()) {
/* 1080 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1082 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1085 */         int col = 1;
/* 1086 */         stmt.setInt(col++, pk.getMappedModelId());
/*      */ 
/* 1089 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1093 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1097 */         closeStatement(stmt);
/* 1098 */         closeConnection();
/*      */ 
/* 1100 */         if (timer != null) {
/* 1101 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1105 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1107 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1109 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1111 */       PreparedStatement stmt = null;
/*      */ 
/* 1113 */       int resultCount = 0;
/* 1114 */       String s = null;
/*      */       try
/*      */       {
/* 1117 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1119 */         if (this._log.isDebugEnabled()) {
/* 1120 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1122 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1125 */         int col = 1;
/* 1126 */         stmt.setInt(col++, pk.getMappedModelId());
/*      */ 
/* 1129 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1133 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1137 */         closeStatement(stmt);
/* 1138 */         closeConnection();
/*      */ 
/* 1140 */         if (timer != null)
/* 1141 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public MappedModelEVO getDetails(MappedModelPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1166 */     return getDetails(new MappedModelCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public MappedModelEVO getDetails(MappedModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1195 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1198 */     if (this.mDetails == null) {
/* 1199 */       doLoad(paramCK.getMappedModelPK());
/*      */     }
/* 1201 */     else if (!this.mDetails.getPK().equals(paramCK.getMappedModelPK())) {
/* 1202 */       doLoad(paramCK.getMappedModelPK());
/*      */     }
/* 1204 */     else if (!checkIfValid())
/*      */     {
/* 1206 */       this._log.info("getDetails", "[ALERT] MappedModelEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1208 */       doLoad(paramCK.getMappedModelPK());
/*      */     }
/*      */ 
/* 1230 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isMappedCalendarYearsAllItemsLoaded()))
/*      */     {
/* 1235 */       this.mDetails.setMappedCalendarYears(getMappedCalendarYearDAO().getAll(this.mDetails.getMappedModelId(), dependants, this.mDetails.getMappedCalendarYears()));
/*      */ 
/* 1242 */       this.mDetails.setMappedCalendarYearsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1246 */     if ((dependants.indexOf("<2>") > -1) && (!this.mDetails.isMappedFinanceCubesAllItemsLoaded()))
/*      */     {
/* 1251 */       this.mDetails.setMappedFinanceCubes(getMappedFinanceCubeDAO().getAll(this.mDetails.getMappedModelId(), dependants, this.mDetails.getMappedFinanceCubes()));
/*      */ 
/* 1258 */       this.mDetails.setMappedFinanceCubesAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1262 */     if ((dependants.indexOf("<4>") > -1) && (!this.mDetails.isMappedDimensionsAllItemsLoaded()))
/*      */     {
/* 1267 */       this.mDetails.setMappedDimensions(getMappedDimensionDAO().getAll(this.mDetails.getMappedModelId(), dependants, this.mDetails.getMappedDimensions()));
/*      */ 
/* 1274 */       this.mDetails.setMappedDimensionsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1277 */     if ((paramCK instanceof MappedCalendarYearCK))
/*      */     {
/* 1279 */       if (this.mDetails.getMappedCalendarYears() == null) {
/* 1280 */         this.mDetails.loadMappedCalendarYearsItem(getMappedCalendarYearDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1283 */         MappedCalendarYearPK pk = ((MappedCalendarYearCK)paramCK).getMappedCalendarYearPK();
/* 1284 */         MappedCalendarYearEVO evo = this.mDetails.getMappedCalendarYearsItem(pk);
/* 1285 */         if (evo == null)
/* 1286 */           this.mDetails.loadMappedCalendarYearsItem(getMappedCalendarYearDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1288 */           getMappedCalendarYearDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/* 1292 */     else if ((paramCK instanceof MappedFinanceCubeCK))
/*      */     {
/* 1294 */       if (this.mDetails.getMappedFinanceCubes() == null) {
/* 1295 */         this.mDetails.loadMappedFinanceCubesItem(getMappedFinanceCubeDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1298 */         MappedFinanceCubePK pk = ((MappedFinanceCubeCK)paramCK).getMappedFinanceCubePK();
/* 1299 */         MappedFinanceCubeEVO evo = this.mDetails.getMappedFinanceCubesItem(pk);
/* 1300 */         if (evo == null)
/* 1301 */           this.mDetails.loadMappedFinanceCubesItem(getMappedFinanceCubeDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1303 */           getMappedFinanceCubeDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/* 1307 */     else if ((paramCK instanceof MappedDimensionCK))
/*      */     {
/* 1309 */       if (this.mDetails.getMappedDimensions() == null) {
/* 1310 */         this.mDetails.loadMappedDimensionsItem(getMappedDimensionDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1313 */         MappedDimensionPK pk = ((MappedDimensionCK)paramCK).getMappedDimensionPK();
/* 1314 */         MappedDimensionEVO evo = this.mDetails.getMappedDimensionsItem(pk);
/* 1315 */         if (evo == null)
/* 1316 */           this.mDetails.loadMappedDimensionsItem(getMappedDimensionDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1318 */           getMappedDimensionDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1323 */     MappedModelEVO details = new MappedModelEVO();
/* 1324 */     details = this.mDetails.deepClone();
/*      */ 
/* 1326 */     if (timer != null) {
/* 1327 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1329 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1339 */     boolean stillValid = false;
/* 1340 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1341 */     PreparedStatement stmt = null;
/* 1342 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1345 */       stmt = getConnection().prepareStatement("select VERSION_NUM from MAPPED_MODEL where   MAPPED_MODEL_ID = ?");
/* 1346 */       int col = 1;
/* 1347 */       stmt.setInt(col++, this.mDetails.getMappedModelId());
/*      */ 
/* 1349 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1351 */       if (!resultSet.next()) {
/* 1352 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1354 */       col = 1;
/* 1355 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1357 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1358 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1362 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from MAPPED_MODEL where   MAPPED_MODEL_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1366 */       closeResultSet(resultSet);
/* 1367 */       closeStatement(stmt);
/* 1368 */       closeConnection();
/*      */ 
///* 1370 */       if (timer != null) {
///* 1371 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
///*      */       }
/*      */     }
/* 1374 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public MappedModelEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1380 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1382 */     if (!checkIfValid())
/*      */     {
/* 1384 */       this._log.info("getDetails", "MappedModel " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1385 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1389 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1392 */     MappedModelEVO details = this.mDetails.deepClone();
/*      */ 
/* 1394 */     if (timer != null) {
/* 1395 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1397 */     return details;
/*      */   }
/*      */ 
/*      */   protected MappedCalendarYearDAO getMappedCalendarYearDAO()
/*      */   {
/* 1406 */     if (this.mMappedCalendarYearDAO == null)
/*      */     {
/* 1408 */       if (this.mDataSource != null)
/* 1409 */         this.mMappedCalendarYearDAO = new MappedCalendarYearDAO(this.mDataSource);
/*      */       else {
/* 1411 */         this.mMappedCalendarYearDAO = new MappedCalendarYearDAO(getConnection());
/*      */       }
/*      */     }
/* 1414 */     return this.mMappedCalendarYearDAO;
/*      */   }
/*      */ 
/*      */   protected MappedFinanceCubeDAO getMappedFinanceCubeDAO()
/*      */   {
/* 1423 */     if (this.mMappedFinanceCubeDAO == null)
/*      */     {
/* 1425 */       if (this.mDataSource != null)
/* 1426 */         this.mMappedFinanceCubeDAO = new MappedFinanceCubeDAO(this.mDataSource);
/*      */       else {
/* 1428 */         this.mMappedFinanceCubeDAO = new MappedFinanceCubeDAO(getConnection());
/*      */       }
/*      */     }
/* 1431 */     return this.mMappedFinanceCubeDAO;
/*      */   }
/*      */ 
/*      */   protected MappedDimensionDAO getMappedDimensionDAO()
/*      */   {
/* 1440 */     if (this.mMappedDimensionDAO == null)
/*      */     {
/* 1442 */       if (this.mDataSource != null)
/* 1443 */         this.mMappedDimensionDAO = new MappedDimensionDAO(this.mDataSource);
/*      */       else {
/* 1445 */         this.mMappedDimensionDAO = new MappedDimensionDAO(getConnection());
/*      */       }
/*      */     }
/* 1448 */     return this.mMappedDimensionDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1453 */     return "MappedModel";
/*      */   }
/*      */ 
/*      */   public MappedModelRef getRef(MappedModelPK paramMappedModelPK)
/*      */     throws ValidationException
/*      */   {
/* 1459 */     MappedModelEVO evo = getDetails(paramMappedModelPK, "");
/* 1460 */     return evo.getEntityRef("");
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1573 */     if (c == null)
/* 1574 */       return;
/* 1575 */     Iterator iter = c.iterator();
/* 1576 */     while (iter.hasNext())
/*      */     {
/* 1578 */       MappedModelEVO evo = (MappedModelEVO)iter.next();
/* 1579 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(MappedModelEVO evo, String dependants)
/*      */   {
/* 1593 */     if (evo.getMappedModelId() < 1) {
/* 1594 */       return;
/*      */     }
/*      */ 
/* 1610 */     if ((dependants.indexOf("<0>") > -1) || (dependants.indexOf("<1>") > -1))
/*      */     {
/* 1614 */       if (!evo.isMappedCalendarYearsAllItemsLoaded())
/*      */       {
/* 1616 */         evo.setMappedCalendarYears(getMappedCalendarYearDAO().getAll(evo.getMappedModelId(), dependants, evo.getMappedCalendarYears()));
/*      */ 
/* 1623 */         evo.setMappedCalendarYearsAllItemsLoaded(true);
/*      */       }
/* 1625 */       getMappedCalendarYearDAO().getDependants(evo.getMappedCalendarYears(), dependants);
/*      */     }
/*      */ 
/* 1629 */     if ((dependants.indexOf("<2>") > -1) || (dependants.indexOf("<3>") > -1))
/*      */     {
/* 1633 */       if (!evo.isMappedFinanceCubesAllItemsLoaded())
/*      */       {
/* 1635 */         evo.setMappedFinanceCubes(getMappedFinanceCubeDAO().getAll(evo.getMappedModelId(), dependants, evo.getMappedFinanceCubes()));
/*      */ 
/* 1642 */         evo.setMappedFinanceCubesAllItemsLoaded(true);
/*      */       }
/* 1644 */       getMappedFinanceCubeDAO().getDependants(evo.getMappedFinanceCubes(), dependants);
/*      */     }
/*      */ 
/* 1648 */     if ((dependants.indexOf("<4>") > -1) || (dependants.indexOf("<5>") > -1) || (dependants.indexOf("<6>") > -1))
/*      */     {
/* 1653 */       if (!evo.isMappedDimensionsAllItemsLoaded())
/*      */       {
/* 1655 */         evo.setMappedDimensions(getMappedDimensionDAO().getAll(evo.getMappedModelId(), dependants, evo.getMappedDimensions()));
/*      */ 
/* 1662 */         evo.setMappedDimensionsAllItemsLoaded(true);
/*      */       }
/* 1664 */       getMappedDimensionDAO().getDependants(evo.getMappedDimensions(), dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public MappedModelPK getMappedModel(ModelRef model)
/*      */   {
/* 1682 */     PreparedStatement ps = null;
/* 1683 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1686 */       ps = getConnection().prepareStatement("select mapped_model_id from mapped_model mm where mm.model_id = ?");
/* 1687 */       ps.setInt(1, ((ModelPK)model.getPrimaryKey()).getModelId());
/* 1688 */       rs = ps.executeQuery();
/* 1689 */       if (rs.next()) {
/* 1690 */         MappedModelPK localMappedModelPK = new MappedModelPK(rs.getInt(1));
/*      */         return localMappedModelPK;
/*      */       }
/* 1691 */       MappedModelPK localMappedModelPK = null;
/*      */       return localMappedModelPK;
/*      */     }
/*      */     catch (SQLException ex)
/*      */     {
/* 1695 */       throw new CPException("Failed to query mapped_model table:", ex);
/*      */     }
/*      */     finally
/*      */     {
/* 1699 */       closeStatement(ps);
/* 1700 */       closeConnection(); } //throw localObject;
/*      */   }
/*      */
			public AllMappedModelsELO getAllMappedModelsForLoggedUser(int userId) {
			   Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  728 */     PreparedStatement stmt = null;
/*  729 */     ResultSet resultSet = null;
/*  730 */     AllMappedModelsELO results = new AllMappedModelsELO();
/*      */     try
/*      */     {
/*  733 */       stmt = getConnection().prepareStatement(SQL_ALL_MAPPED_MODELS_FOR_USER);
/*  734 */       int col = 1;
				 stmt.setInt(1, userId);
/*  735 */       resultSet = stmt.executeQuery();
/*  736 */       while (resultSet.next())
/*      */       {
/*  738 */         col = 2;
/*      */ 
/*  741 */         MappedModelPK pkMappedModel = new MappedModelPK(resultSet.getInt(col++));
/*      */ 
/*  744 */         String textMappedModel = "";
/*      */ 
/*  747 */         ExternalSystemPK pkExternalSystem = new ExternalSystemPK(resultSet.getInt(col++));
/*      */ 
/*  750 */         String textExternalSystem = resultSet.getString(col++);
/*      */ 
/*  752 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  755 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  758 */         MappedModelRefImpl erMappedModel = new MappedModelRefImpl(pkMappedModel, textMappedModel);
/*      */ 
/*  764 */         ExternalSystemRefImpl erExternalSystem = new ExternalSystemRefImpl(pkExternalSystem, textExternalSystem);
/*      */ 
/*  770 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  775 */         int col1 = resultSet.getInt(col++);
/*  776 */         int col2 = resultSet.getInt(col++);
/*  777 */         int col3 = resultSet.getInt(col++);
/*  778 */         String col4 = resultSet.getString(col++);
/*  779 */         String col5 = resultSet.getString(col++);
/*  780 */         String col6 = resultSet.getString(col++);
/*  781 */         String col7 = resultSet.getString(col++);
/*  782 */         String col8 = resultSet.getString(col++);
/*      */ 
/*  785 */         results.add(erMappedModel, erExternalSystem, erModel, col1, col2, col3, col4, col5, col6, col7, col8);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  802 */       throw handleSQLException(SQL_ALL_MAPPED_MODELS_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  806 */       closeResultSet(resultSet);
/*  807 */       closeStatement(stmt);
/*  808 */       closeConnection();
/*      */     }
/*      */ 
/*  811 */     if (timer != null) {
/*  812 */       timer.logDebug("getAllMappedModelsForLoggedUser", " items=" + results.size());
/*      */     }
/*      */ 
/*  816 */     return results;
			}
}

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.mapping.MappedModelDAO
 * JD-Core Version:    0.6.0
 */