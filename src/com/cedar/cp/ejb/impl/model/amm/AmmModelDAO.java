/*      */ package com.cedar.cp.ejb.impl.model.amm;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.model.amm.AmmMap;
/*      */ import com.cedar.cp.api.model.amm.AmmModelRef;
/*      */ import com.cedar.cp.dto.dimension.DimensionElementPK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyElementPK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyPK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.amm.AllAmmModelsELO;
/*      */ import com.cedar.cp.dto.model.amm.AmmCubeMapImpl;
/*      */ import com.cedar.cp.dto.model.amm.AmmDataTypeMapImpl;
/*      */ import com.cedar.cp.dto.model.amm.AmmDimensionCK;
/*      */ import com.cedar.cp.dto.model.amm.AmmDimensionPK;
/*      */ import com.cedar.cp.dto.model.amm.AmmFinanceCubeCK;
/*      */ import com.cedar.cp.dto.model.amm.AmmFinanceCubePK;
/*      */ import com.cedar.cp.dto.model.amm.AmmMapImpl;
/*      */ import com.cedar.cp.dto.model.amm.AmmModelCK;
/*      */ import com.cedar.cp.dto.model.amm.AmmModelMapImpl;
/*      */ import com.cedar.cp.dto.model.amm.AmmModelPK;
/*      */ import com.cedar.cp.dto.model.amm.AmmModelRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.SqlBuilder;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class AmmModelDAO extends AbstractDAO
/*      */ {
/*   42 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select AMM_MODEL_ID from AMM_MODEL where    AMM_MODEL_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select AMM_MODEL.AMM_MODEL_ID,AMM_MODEL.MODEL_ID,AMM_MODEL.SRC_MODEL_ID,AMM_MODEL.INVALIDATED_BY_TASK_ID,AMM_MODEL.VERSION_NUM,AMM_MODEL.UPDATED_BY_USER_ID,AMM_MODEL.UPDATED_TIME,AMM_MODEL.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from AMM_MODEL where    AMM_MODEL_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into AMM_MODEL ( AMM_MODEL_ID,MODEL_ID,SRC_MODEL_ID,INVALIDATED_BY_TASK_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update AMM_MODEL_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from AMM_MODEL_SEQ";
/*      */   protected static final String SQL_STORE = "update AMM_MODEL set MODEL_ID = ?,SRC_MODEL_ID = ?,INVALIDATED_BY_TASK_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_MODEL_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from AMM_MODEL where AMM_MODEL_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from AMM_MODEL where    AMM_MODEL_ID = ? ";
/*  663 */   protected static String SQL_ALL_AMM_MODELS = "select 0       ,AMM_MODEL.AMM_MODEL_ID      ,AMM_MODEL.MODEL_ID      ,(select VIS_ID from MODEL m where m.MODEL_ID = AMM_MODEL.MODEL_ID) MODEL_VIS_ID      ,(select DESCRIPTION from MODEL m where m.MODEL_ID = AMM_MODEL.MODEL_ID)      ,AMM_MODEL.SRC_MODEL_ID      ,(select VIS_ID from MODEL m where m.MODEL_ID = AMM_MODEL.SRC_MODEL_ID)      ,(select DESCRIPTION from MODEL m where m.MODEL_ID = AMM_MODEL.SRC_MODEL_ID)      ,AMM_MODEL.INVALIDATED_BY_TASK_ID from AMM_MODEL where 1=1  order by 4,7";
/*      */   protected static String SQL_ALL_AMM_MODELS_FOR_USER = "select 0       ,AMM_MODEL.AMM_MODEL_ID      ,AMM_MODEL.MODEL_ID      ,(select VIS_ID from MODEL m where m.MODEL_ID = AMM_MODEL.MODEL_ID) MODEL_VIS_ID      ,(select DESCRIPTION from MODEL m where m.MODEL_ID = AMM_MODEL.MODEL_ID)      ,AMM_MODEL.SRC_MODEL_ID      ,(select VIS_ID from MODEL m where m.MODEL_ID = AMM_MODEL.SRC_MODEL_ID)      ,(select DESCRIPTION from MODEL m where m.MODEL_ID = AMM_MODEL.SRC_MODEL_ID)      ,AMM_MODEL.INVALIDATED_BY_TASK_ID from AMM_MODEL where AMM_MODEL.model_id in (select distinct model_id from budget_user where user_id = ?) order by 4,7";
/*  759 */   private static String[][] SQL_DELETE_CHILDREN = { { "AMM_DIMENSION", "delete from AMM_DIMENSION where     AMM_DIMENSION.AMM_MODEL_ID = ? " }, { "AMM_FINANCE_CUBE", "delete from AMM_FINANCE_CUBE where     AMM_FINANCE_CUBE.AMM_MODEL_ID = ? " } };
/*      */ 
/*  773 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "AMM_DIMENSION_ELEMENT", "delete from AMM_DIMENSION_ELEMENT AmmDimensionElement where exists (select * from AMM_DIMENSION_ELEMENT,AMM_DIMENSION,AMM_MODEL where     AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID = AMM_DIMENSION.AMM_DIMENSION_ID and AMM_DIMENSION.AMM_MODEL_ID = AMM_MODEL.AMM_MODEL_ID and AmmDimensionElement.AMM_DIMENSION_ID = AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID " }, { "AMM_SRC_STRUCTURE_ELEMENT", "delete from AMM_SRC_STRUCTURE_ELEMENT AmmSrcStructureElement where exists (select * from AMM_SRC_STRUCTURE_ELEMENT,AMM_DIMENSION_ELEMENT,AMM_DIMENSION,AMM_MODEL where     AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID = AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID = AMM_DIMENSION.AMM_DIMENSION_ID and AMM_DIMENSION.AMM_MODEL_ID = AMM_MODEL.AMM_MODEL_ID and AmmSrcStructureElement.AMM_DIMENSION_ELEMENT_ID = AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID " }, { "AMM_DATA_TYPE", "delete from AMM_DATA_TYPE AmmDataType where exists (select * from AMM_DATA_TYPE,AMM_FINANCE_CUBE,AMM_MODEL where     AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID = AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID and AMM_FINANCE_CUBE.AMM_MODEL_ID = AMM_MODEL.AMM_MODEL_ID and AmmDataType.AMM_FINANCE_CUBE_ID = AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID " } };
/*      */ 
/*  812 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and AMM_MODEL.AMM_MODEL_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from AMM_MODEL where   AMM_MODEL_ID = ?";
/*      */   public static final String SQL_GET_AMM_DIMENSION_REF = "select 0,AMM_MODEL.AMM_MODEL_ID from AMM_DIMENSION,AMM_MODEL where 1=1 and AMM_DIMENSION.AMM_DIMENSION_ID = ? and AMM_DIMENSION.AMM_MODEL_ID = AMM_MODEL.AMM_MODEL_ID";
/*      */   public static final String SQL_GET_AMM_DIMENSION_ELEMENT_REF = "select 0,AMM_MODEL.AMM_MODEL_ID,AMM_DIMENSION.AMM_DIMENSION_ID from AMM_DIMENSION_ELEMENT,AMM_MODEL,AMM_DIMENSION where 1=1 and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID = ? and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID = AMM_DIMENSION.AMM_DIMENSION_ID and AMM_DIMENSION.AMM_DIMENSION_ID = AMM_MODEL.AMM_DIMENSION_ID";
/*      */   public static final String SQL_GET_AMM_SRC_STRUCTURE_ELEMENT_REF = "select 0,AMM_MODEL.AMM_MODEL_ID,AMM_DIMENSION.AMM_DIMENSION_ID,AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID from AMM_SRC_STRUCTURE_ELEMENT,AMM_MODEL,AMM_DIMENSION,AMM_DIMENSION_ELEMENT where 1=1 and AMM_SRC_STRUCTURE_ELEMENT.AMM_SRC_STRUCTURE_ELEMENT_ID = ? and AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID = AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID = AMM_DIMENSION.AMM_DIMENSION_ELEMENT_ID and AMM_DIMENSION.AMM_DIMENSION_ID = AMM_MODEL.AMM_DIMENSION_ID";
/*      */   public static final String SQL_GET_AMM_FINANCE_CUBE_REF = "select 0,AMM_MODEL.AMM_MODEL_ID from AMM_FINANCE_CUBE,AMM_MODEL where 1=1 and AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID = ? and AMM_FINANCE_CUBE.AMM_MODEL_ID = AMM_MODEL.AMM_MODEL_ID";
/*      */   public static final String SQL_GET_AMM_DATA_TYPE_REF = "select 0,AMM_MODEL.AMM_MODEL_ID,AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID from AMM_DATA_TYPE,AMM_MODEL,AMM_FINANCE_CUBE where 1=1 and AMM_DATA_TYPE.AMM_DATA_TYPE_ID = ? and AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID = AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID and AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID = AMM_MODEL.AMM_FINANCE_CUBE_ID";
/*      */   protected AmmDimensionDAO mAmmDimensionDAO;
/*      */   protected AmmFinanceCubeDAO mAmmFinanceCubeDAO;
/*      */   protected AmmModelEVO mDetails;
/*      */ 
/*      */   public AmmModelDAO(Connection connection)
/*      */   {
/*   49 */     super(connection);
/*      */   }
/*      */ 
/*      */   public AmmModelDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public AmmModelDAO(DataSource ds)
/*      */   {
/*   65 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected AmmModelPK getPK()
/*      */   {
/*   73 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(AmmModelEVO details)
/*      */   {
/*   82 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public AmmModelEVO setAndGetDetails(AmmModelEVO details, String dependants)
/*      */   {
/*   93 */     setDetails(details);
/*   94 */     generateKeys();
/*   95 */     getDependants(this.mDetails, dependants);
/*   96 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public AmmModelPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  105 */     doCreate();
/*      */ 
/*  107 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(AmmModelPK pk)
/*      */     throws ValidationException
/*      */   {
/*  117 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  126 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  135 */     doRemove();
/*      */   }
/*      */ 
/*      */   public AmmModelPK findByPrimaryKey(AmmModelPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  144 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  145 */     if (exists(pk_))
/*      */     {
/*  147 */       if (timer != null) {
/*  148 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  150 */       return pk_;
/*      */     }
/*      */ 
/*  153 */     throw new ValidationException(new StringBuilder().append(pk_).append(" not found").toString());
/*      */   }
/*      */ 
/*      */   protected boolean exists(AmmModelPK pk)
/*      */   {
/*  171 */     PreparedStatement stmt = null;
/*  172 */     ResultSet resultSet = null;
/*  173 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  177 */       stmt = getConnection().prepareStatement("select AMM_MODEL_ID from AMM_MODEL where    AMM_MODEL_ID = ? ");
/*      */ 
/*  179 */       int col = 1;
/*  180 */       stmt.setInt(col++, pk.getAmmModelId());
/*      */ 
/*  182 */       resultSet = stmt.executeQuery();
/*      */ 
/*  184 */       if (!resultSet.next())
/*  185 */         returnValue = false;
/*      */       else
/*  187 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  191 */       throw handleSQLException(pk, "select AMM_MODEL_ID from AMM_MODEL where    AMM_MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  195 */       closeResultSet(resultSet);
/*  196 */       closeStatement(stmt);
/*  197 */       closeConnection();
/*      */     }
/*  199 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private AmmModelEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  218 */     int col = 1;
/*  219 */     AmmModelEVO evo = new AmmModelEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++), resultSet_.getInt(col++), null, null);
/*      */ 
/*  229 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  230 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  231 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  232 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(AmmModelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  237 */     int col = startCol_;
/*  238 */     stmt_.setInt(col++, evo_.getAmmModelId());
/*  239 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(AmmModelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  244 */     int col = startCol_;
/*  245 */     stmt_.setInt(col++, evo_.getModelId());
/*  246 */     stmt_.setInt(col++, evo_.getSrcModelId());
/*  247 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getInvalidatedByTaskId());
/*  248 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  249 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  250 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  251 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  252 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(AmmModelPK pk)
/*      */     throws ValidationException
/*      */   {
/*  268 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  270 */     PreparedStatement stmt = null;
/*  271 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  275 */       stmt = getConnection().prepareStatement("select AMM_MODEL.AMM_MODEL_ID,AMM_MODEL.MODEL_ID,AMM_MODEL.SRC_MODEL_ID,AMM_MODEL.INVALIDATED_BY_TASK_ID,AMM_MODEL.VERSION_NUM,AMM_MODEL.UPDATED_BY_USER_ID,AMM_MODEL.UPDATED_TIME,AMM_MODEL.CREATED_TIME from AMM_MODEL where    AMM_MODEL_ID = ? ");
/*      */ 
/*  278 */       int col = 1;
/*  279 */       stmt.setInt(col++, pk.getAmmModelId());
/*      */ 
/*  281 */       resultSet = stmt.executeQuery();
/*      */ 
/*  283 */       if (!resultSet.next()) {
/*  284 */         throw new ValidationException(new StringBuilder().append(getEntityName()).append(" select of ").append(pk).append(" not found").toString());
/*      */       }
/*      */ 
/*  287 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  288 */       if (this.mDetails.isModified())
/*  289 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  293 */       throw handleSQLException(pk, "select AMM_MODEL.AMM_MODEL_ID,AMM_MODEL.MODEL_ID,AMM_MODEL.SRC_MODEL_ID,AMM_MODEL.INVALIDATED_BY_TASK_ID,AMM_MODEL.VERSION_NUM,AMM_MODEL.UPDATED_BY_USER_ID,AMM_MODEL.UPDATED_TIME,AMM_MODEL.CREATED_TIME from AMM_MODEL where    AMM_MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  297 */       closeResultSet(resultSet);
/*  298 */       closeStatement(stmt);
/*  299 */       closeConnection();
/*      */ 
/*  301 */       if (timer != null)
/*  302 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  335 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  336 */     generateKeys();
/*      */ 
/*  338 */     this.mDetails.postCreateInit();
/*      */ 
/*  340 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  345 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  346 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  347 */       stmt = getConnection().prepareStatement("insert into AMM_MODEL ( AMM_MODEL_ID,MODEL_ID,SRC_MODEL_ID,INVALIDATED_BY_TASK_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");
/*      */ 
/*  350 */       int col = 1;
/*  351 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  352 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  355 */       int resultCount = stmt.executeUpdate();
/*  356 */       if (resultCount != 1)
/*      */       {
/*  358 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" insert failed (").append(this.mDetails.getPK()).append("): resultCount=").append(resultCount).toString());
/*      */       }
/*      */ 
/*  361 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  365 */       throw handleSQLException(this.mDetails.getPK(), "insert into AMM_MODEL ( AMM_MODEL_ID,MODEL_ID,SRC_MODEL_ID,INVALIDATED_BY_TASK_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  369 */       closeStatement(stmt);
/*  370 */       closeConnection();
/*      */ 
/*  372 */       if (timer != null) {
/*  373 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  379 */       getAmmDimensionDAO().update(this.mDetails.getAmmDimensionsMap());
/*      */ 
/*  381 */       getAmmFinanceCubeDAO().update(this.mDetails.getAmmFinanceCubesMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  387 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  407 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  409 */     PreparedStatement stmt = null;
/*  410 */     ResultSet resultSet = null;
/*  411 */     String sqlString = null;
/*      */     try
/*      */     {
/*  416 */       sqlString = "update AMM_MODEL_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  417 */       stmt = getConnection().prepareStatement("update AMM_MODEL_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  418 */       stmt.setInt(1, insertCount);
/*      */ 
/*  420 */       int resultCount = stmt.executeUpdate();
/*  421 */       if (resultCount != 1) {
/*  422 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" reserveIds: update failed: resultCount=").append(resultCount).toString());
/*      */       }
/*  424 */       closeStatement(stmt);
/*      */ 
/*  427 */       sqlString = "select SEQ_NUM from AMM_MODEL_SEQ";
/*  428 */       stmt = getConnection().prepareStatement("select SEQ_NUM from AMM_MODEL_SEQ");
/*  429 */       resultSet = stmt.executeQuery();
/*  430 */       if (!resultSet.next())
/*  431 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" reserveIds: select failed").toString());
/*  432 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  434 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  438 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  442 */       closeResultSet(resultSet);
/*  443 */       closeStatement(stmt);
/*  444 */       closeConnection();
/*      */ 
/*  446 */       if (timer != null)
/*  447 */         timer.logDebug("reserveIds", new StringBuilder().append("keys=").append(insertCount).toString()); 
/*  447 */     }
/*      */   }
/*      */ 
/*      */   public AmmModelPK generateKeys()
/*      */   {
/*  457 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  459 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  462 */     if (insertCount == 0) {
/*  463 */       return this.mDetails.getPK();
/*      */     }
/*  465 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  467 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  492 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  494 */     generateKeys();
/*      */ 
/*  499 */     PreparedStatement stmt = null;
/*      */ 
/*  501 */     boolean mainChanged = this.mDetails.isModified();
/*  502 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  506 */       if (getAmmDimensionDAO().update(this.mDetails.getAmmDimensionsMap())) {
/*  507 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  510 */       if (getAmmFinanceCubeDAO().update(this.mDetails.getAmmFinanceCubesMap())) {
/*  511 */         dependantChanged = true;
/*      */       }
/*  513 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  516 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  519 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  520 */         stmt = getConnection().prepareStatement("update AMM_MODEL set MODEL_ID = ?,SRC_MODEL_ID = ?,INVALIDATED_BY_TASK_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_MODEL_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  523 */         int col = 1;
/*  524 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  525 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  527 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  530 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  532 */         if (resultCount == 0) {
/*  533 */           checkVersionNum();
/*      */         }
/*  535 */         if (resultCount != 1) {
/*  536 */           throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" update failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
/*      */         }
/*      */ 
/*  539 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  548 */       throw handleSQLException(getPK(), "update AMM_MODEL set MODEL_ID = ?,SRC_MODEL_ID = ?,INVALIDATED_BY_TASK_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_MODEL_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  552 */       closeStatement(stmt);
/*  553 */       closeConnection();
/*      */ 
/*  555 */       if ((timer != null) && (
/*  556 */         (mainChanged) || (dependantChanged)))
/*  557 */         timer.logDebug("store", new StringBuilder().append(this.mDetails.getPK()).append("(").append(mainChanged).append(",").append(dependantChanged).append(")").toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  569 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  570 */     PreparedStatement stmt = null;
/*  571 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  575 */       stmt = getConnection().prepareStatement("select VERSION_NUM from AMM_MODEL where AMM_MODEL_ID = ?");
/*      */ 
/*  578 */       int col = 1;
/*  579 */       stmt.setInt(col++, this.mDetails.getAmmModelId());
/*      */ 
/*  582 */       resultSet = stmt.executeQuery();
/*      */ 
/*  584 */       if (!resultSet.next()) {
/*  585 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" checkVersionNum: select of ").append(getPK()).append(" not found").toString());
/*      */       }
/*      */ 
/*  588 */       col = 1;
/*  589 */       int dbVersionNumber = resultSet.getInt(col++);
/*  590 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  591 */         throw new VersionValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" expected:").append(this.mDetails.getVersionNum() - 1).append(" found:").append(dbVersionNumber).toString());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  597 */       throw handleSQLException(getPK(), "select VERSION_NUM from AMM_MODEL where AMM_MODEL_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  601 */       closeStatement(stmt);
/*  602 */       closeResultSet(resultSet);
/*      */ 
/*  604 */       if (timer != null)
/*  605 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  622 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  623 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  628 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  633 */       stmt = getConnection().prepareStatement("delete from AMM_MODEL where    AMM_MODEL_ID = ? ");
/*      */ 
/*  636 */       int col = 1;
/*  637 */       stmt.setInt(col++, this.mDetails.getAmmModelId());
/*      */ 
/*  639 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  641 */       if (resultCount != 1) {
/*  642 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" delete failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  648 */       throw handleSQLException(getPK(), "delete from AMM_MODEL where    AMM_MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  652 */       closeStatement(stmt);
/*  653 */       closeConnection();
/*      */ 
/*  655 */       if (timer != null)
/*  656 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllAmmModelsELO getAllAmmModels()
/*      */   {
/*  691 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  692 */     PreparedStatement stmt = null;
/*  693 */     ResultSet resultSet = null;
/*  694 */     AllAmmModelsELO results = new AllAmmModelsELO();
/*      */     try
/*      */     {
/*  697 */       stmt = getConnection().prepareStatement(SQL_ALL_AMM_MODELS);
/*  698 */       int col = 1;
/*  699 */       resultSet = stmt.executeQuery();
/*  700 */       while (resultSet.next())
/*      */       {
/*  702 */         col = 2;
/*      */ 
/*  705 */         AmmModelPK pkAmmModel = new AmmModelPK(resultSet.getInt(col++));
/*      */ 
/*  708 */         String textAmmModel = "";
/*      */ 
/*  712 */         AmmModelRefImpl erAmmModel = new AmmModelRefImpl(pkAmmModel, textAmmModel);
/*      */ 
/*  717 */         int col1 = resultSet.getInt(col++);
/*  718 */         String col2 = resultSet.getString(col++);
/*  719 */         String col3 = resultSet.getString(col++);
/*  720 */         int col4 = resultSet.getInt(col++);
/*  721 */         String col5 = resultSet.getString(col++);
/*  722 */         String col6 = resultSet.getString(col++);
/*  723 */         Integer col7 = getWrappedIntegerFromJdbc(resultSet, col++);
/*      */ 
/*  726 */         results.add(erAmmModel, col1, col2, col3, col4, col5, col6, col7);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  740 */       throw handleSQLException(SQL_ALL_AMM_MODELS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  744 */       closeResultSet(resultSet);
/*  745 */       closeStatement(stmt);
/*  746 */       closeConnection();
/*      */     }
/*      */ 
/*  749 */     if (timer != null) {
/*  750 */       timer.logDebug("getAllAmmModels", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  754 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(AmmModelPK pk)
/*      */   {
/*  821 */     Set emptyStrings = Collections.emptySet();
/*  822 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(AmmModelPK pk, Set<String> exclusionTables)
/*      */   {
/*  828 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  830 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  832 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  834 */       PreparedStatement stmt = null;
/*      */ 
/*  836 */       int resultCount = 0;
/*  837 */       String s = null;
/*      */       try
/*      */       {
/*  840 */         s = new StringBuilder().append(SQL_DELETE_CHILDRENS_DEPENDANTS[i][1]).append(SQL_DELETE_DEPENDANT_CRITERIA).toString();
/*      */ 
/*  842 */         if (this._log.isDebugEnabled()) {
/*  843 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  845 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  848 */         int col = 1;
/*  849 */         stmt.setInt(col++, pk.getAmmModelId());
/*      */ 
/*  852 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  856 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  860 */         closeStatement(stmt);
/*  861 */         closeConnection();
/*      */ 
/*  863 */         if (timer != null) {
/*  864 */           timer.logDebug("deleteDependants", new StringBuilder().append("A[").append(i).append("] count=").append(resultCount).toString());
/*      */         }
/*      */       }
/*      */     }
/*  868 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  870 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  872 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  874 */       PreparedStatement stmt = null;
/*      */ 
/*  876 */       int resultCount = 0;
/*  877 */       String s = null;
/*      */       try
/*      */       {
/*  880 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  882 */         if (this._log.isDebugEnabled()) {
/*  883 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  885 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  888 */         int col = 1;
/*  889 */         stmt.setInt(col++, pk.getAmmModelId());
/*      */ 
/*  892 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  896 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  900 */         closeStatement(stmt);
/*  901 */         closeConnection();
/*      */ 
/*  903 */         if (timer != null)
/*  904 */           timer.logDebug("deleteDependants", new StringBuilder().append("B[").append(i).append("] count=").append(resultCount).toString());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public AmmModelEVO getDetails(AmmModelPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  927 */     return getDetails(new AmmModelCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public AmmModelEVO getDetails(AmmModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  952 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  955 */     if (this.mDetails == null) {
/*  956 */       doLoad(paramCK.getAmmModelPK());
/*      */     }
/*  958 */     else if (!this.mDetails.getPK().equals(paramCK.getAmmModelPK())) {
/*  959 */       doLoad(paramCK.getAmmModelPK());
/*      */     }
/*  961 */     else if (!checkIfValid())
/*      */     {
/*  963 */       this._log.info("getDetails", new StringBuilder().append("[ALERT] AmmModelEVO ").append(this.mDetails.getPK()).append(" no longer valid - reloading").toString());
/*      */ 
/*  965 */       doLoad(paramCK.getAmmModelPK());
/*      */     }
/*      */ 
/*  981 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isAmmDimensionsAllItemsLoaded()))
/*      */     {
/*  986 */       this.mDetails.setAmmDimensions(getAmmDimensionDAO().getAll(this.mDetails.getAmmModelId(), dependants, this.mDetails.getAmmDimensions()));
/*      */ 
/*  993 */       this.mDetails.setAmmDimensionsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  997 */     if ((dependants.indexOf("<3>") > -1) && (!this.mDetails.isAmmFinanceCubesAllItemsLoaded()))
/*      */     {
/* 1002 */       this.mDetails.setAmmFinanceCubes(getAmmFinanceCubeDAO().getAll(this.mDetails.getAmmModelId(), dependants, this.mDetails.getAmmFinanceCubes()));
/*      */ 
/* 1009 */       this.mDetails.setAmmFinanceCubesAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1012 */     if ((paramCK instanceof AmmDimensionCK))
/*      */     {
/* 1014 */       if (this.mDetails.getAmmDimensions() == null) {
/* 1015 */         this.mDetails.loadAmmDimensionsItem(getAmmDimensionDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1018 */         AmmDimensionPK pk = ((AmmDimensionCK)paramCK).getAmmDimensionPK();
/* 1019 */         AmmDimensionEVO evo = this.mDetails.getAmmDimensionsItem(pk);
/* 1020 */         if (evo == null)
/* 1021 */           this.mDetails.loadAmmDimensionsItem(getAmmDimensionDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1023 */           getAmmDimensionDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/* 1027 */     else if ((paramCK instanceof AmmFinanceCubeCK))
/*      */     {
/* 1029 */       if (this.mDetails.getAmmFinanceCubes() == null) {
/* 1030 */         this.mDetails.loadAmmFinanceCubesItem(getAmmFinanceCubeDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1033 */         AmmFinanceCubePK pk = ((AmmFinanceCubeCK)paramCK).getAmmFinanceCubePK();
/* 1034 */         AmmFinanceCubeEVO evo = this.mDetails.getAmmFinanceCubesItem(pk);
/* 1035 */         if (evo == null)
/* 1036 */           this.mDetails.loadAmmFinanceCubesItem(getAmmFinanceCubeDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1038 */           getAmmFinanceCubeDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1043 */     AmmModelEVO details = new AmmModelEVO();
/* 1044 */     details = this.mDetails.deepClone();
/*      */ 
/* 1046 */     if (timer != null) {
/* 1047 */       timer.logDebug("getDetails", new StringBuilder().append(paramCK).append(" ").append(dependants).toString());
/*      */     }
/* 1049 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1059 */     boolean stillValid = false;
/* 1060 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1061 */     PreparedStatement stmt = null;
/* 1062 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1065 */       stmt = getConnection().prepareStatement("select VERSION_NUM from AMM_MODEL where   AMM_MODEL_ID = ?");
/* 1066 */       int col = 1;
/* 1067 */       stmt.setInt(col++, this.mDetails.getAmmModelId());
/*      */ 
/* 1069 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1071 */       if (!resultSet.next()) {
/* 1072 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" checkIfValid ").append(this.mDetails.getPK()).append(" not found").toString());
/*      */       }
/* 1074 */       col = 1;
/* 1075 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1077 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1078 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1082 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from AMM_MODEL where   AMM_MODEL_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1086 */       closeResultSet(resultSet);
/* 1087 */       closeStatement(stmt);
/* 1088 */       closeConnection();
/*      */ 
/* 1090 */       if (timer != null) {
/* 1091 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1094 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public AmmModelEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1100 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1102 */     if (!checkIfValid())
/*      */     {
/* 1104 */       this._log.info("getDetails", new StringBuilder().append("AmmModel ").append(this.mDetails.getPK()).append(" no longer valid - reloading").toString());
/* 1105 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1109 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1112 */     AmmModelEVO details = this.mDetails.deepClone();
/*      */ 
/* 1114 */     if (timer != null) {
/* 1115 */       timer.logDebug("getDetails", new StringBuilder().append(this.mDetails.getPK()).append(" ").append(dependants).toString());
/*      */     }
/* 1117 */     return details;
/*      */   }
/*      */ 
/*      */   protected AmmDimensionDAO getAmmDimensionDAO()
/*      */   {
/* 1126 */     if (this.mAmmDimensionDAO == null)
/*      */     {
/* 1128 */       if (this.mDataSource != null)
/* 1129 */         this.mAmmDimensionDAO = new AmmDimensionDAO(this.mDataSource);
/*      */       else {
/* 1131 */         this.mAmmDimensionDAO = new AmmDimensionDAO(getConnection());
/*      */       }
/*      */     }
/* 1134 */     return this.mAmmDimensionDAO;
/*      */   }
/*      */ 
/*      */   protected AmmFinanceCubeDAO getAmmFinanceCubeDAO()
/*      */   {
/* 1143 */     if (this.mAmmFinanceCubeDAO == null)
/*      */     {
/* 1145 */       if (this.mDataSource != null)
/* 1146 */         this.mAmmFinanceCubeDAO = new AmmFinanceCubeDAO(this.mDataSource);
/*      */       else {
/* 1148 */         this.mAmmFinanceCubeDAO = new AmmFinanceCubeDAO(getConnection());
/*      */       }
/*      */     }
/* 1151 */     return this.mAmmFinanceCubeDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1156 */     return "AmmModel";
/*      */   }
/*      */ 
/*      */   public AmmModelRef getRef(AmmModelPK paramAmmModelPK)
/*      */     throws ValidationException
/*      */   {
/* 1162 */     AmmModelEVO evo = getDetails(paramAmmModelPK, "");
/* 1163 */     return evo.getEntityRef("");
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1252 */     if (c == null)
/* 1253 */       return;
/* 1254 */     Iterator iter = c.iterator();
/* 1255 */     while (iter.hasNext())
/*      */     {
/* 1257 */       AmmModelEVO evo = (AmmModelEVO)iter.next();
/* 1258 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(AmmModelEVO evo, String dependants)
/*      */   {
/* 1272 */     if (evo.getAmmModelId() < 1) {
/* 1273 */       return;
/*      */     }
/*      */ 
/* 1285 */     if ((dependants.indexOf("<0>") > -1) || (dependants.indexOf("<1>") > -1) || (dependants.indexOf("<2>") > -1))
/*      */     {
/* 1290 */       if (!evo.isAmmDimensionsAllItemsLoaded())
/*      */       {
/* 1292 */         evo.setAmmDimensions(getAmmDimensionDAO().getAll(evo.getAmmModelId(), dependants, evo.getAmmDimensions()));
/*      */ 
/* 1299 */         evo.setAmmDimensionsAllItemsLoaded(true);
/*      */       }
/* 1301 */       getAmmDimensionDAO().getDependants(evo.getAmmDimensions(), dependants);
/*      */     }
/*      */ 
/* 1305 */     if ((dependants.indexOf("<3>") > -1) || (dependants.indexOf("<4>") > -1))
/*      */     {
/* 1309 */       if (!evo.isAmmFinanceCubesAllItemsLoaded())
/*      */       {
/* 1311 */         evo.setAmmFinanceCubes(getAmmFinanceCubeDAO().getAll(evo.getAmmModelId(), dependants, evo.getAmmFinanceCubes()));
/*      */ 
/* 1318 */         evo.setAmmFinanceCubesAllItemsLoaded(true);
/*      */       }
/* 1320 */       getAmmFinanceCubeDAO().getDependants(evo.getAmmFinanceCubes(), dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void performStep(Integer step, Integer taskId, Integer targetCubeId, Integer sourceCubeId, String sourceDataTypeIds)
/*      */   {
/* 1329 */     Timer t = new Timer(this._log);
/*      */ 
/* 1331 */     CallableStatement stmt = null;
/*      */     try
/*      */     {
/* 1334 */       stmt = getConnection().prepareCall("{ call model_utils.runStep(?,?,?,?,?) }");
/* 1335 */       stmt.setInt(1, step.intValue());
/* 1336 */       stmt.setInt(2, taskId.intValue());
/* 1337 */       stmt.setInt(3, targetCubeId.intValue());
/* 1338 */       stmt.setInt(4, sourceCubeId != null ? sourceCubeId.intValue() : -1);
/* 1339 */       stmt.setString(5, sourceDataTypeIds);
/*      */ 
/* 1341 */       stmt.execute();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1345 */       e.printStackTrace();
/* 1346 */       throw new RuntimeException(e);
/*      */     }
/*      */     finally
/*      */     {
/* 1350 */       t.logDebug("performStep", new StringBuilder().append("model_utils.runStep(step=").append(step).append(",taskId=").append(taskId).append(",targetCubeId=").append(targetCubeId).append(",sourceCubeId=").append(sourceCubeId).append(",sourceDataTypeIds=").append(sourceDataTypeIds != null ? new StringBuilder().append("'").append(sourceDataTypeIds).append("'").toString() : "null").append(")").toString());
/*      */ 
/* 1357 */       closeStatement(stmt);
/* 1358 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public EntityList getCalendarYearView(int calDimId)
/*      */   {
/* 1369 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "select  * from CALENDAR_YEAR_VIEW", "where   DIMENSION_ID = <calDimId>", "order   by CALENDAR_YEAR desc" });
/*      */ 
/* 1374 */     SqlExecutor sqle = new SqlExecutor("getCalendarYearView", getDataSource(), sqlb, this._log);
/* 1375 */     sqle.addBindVariable("<calDimId>", Integer.valueOf(calDimId));
/* 1376 */     sqle.setLogSql(false);
/* 1377 */     return sqle.getEntityList();
/*      */   }
/*      */ 
/*      */   public EntityList getMappingDisplay(int ammModelId)
/*      */   {
/* 1385 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "with getCalDimElems as", "(", "select  distinct mde.DIMENSION_ELEMENT_ID", "        ,calendar_utils.queryPathToRoot(mde.DIMENSION_ELEMENT_ID) FULL_VIS_ID", "        ,se.POSITION", "from    AMM_DIMENSION md", "join    HIERARCHY h on (h.DIMENSION_ID = md.DIMENSION_ID)", "join    DIMENSION d on (d.TYPE = 3 and d.DIMENSION_ID = md.DIMENSION_ID)", "join    AMM_DIMENSION_ELEMENT mde using (AMM_DIMENSION_ID)", "join    STRUCTURE_ELEMENT se", "        on", "        (", "            se.STRUCTURE_ID = h.HIERARCHY_ID", "        and se.STRUCTURE_ELEMENT_ID = mde.DIMENSION_ELEMENT_ID", "        )", "order   by se.POSITION", ")", ",getSrcCalStrucElems as", "(", "select  STRUCTURE_ID", "        ,se.STRUCTURE_ELEMENT_ID", "        ,calendar_utils.queryPathToRoot(sse.SRC_STRUCTURE_ELEMENT_ID) FULL_VIS_ID", "from    AMM_DIMENSION md", "join    DIMENSION d on (d.TYPE = 3 and d.DIMENSION_ID = md.DIMENSION_ID)", "join    AMM_DIMENSION_ELEMENT mde using (AMM_DIMENSION_ID)", "join    AMM_SRC_STRUCTURE_ELEMENT sse using (AMM_DIMENSION_ELEMENT_ID)", "join    STRUCTURE_ELEMENT se", "        on", "        (", "            se.STRUCTURE_ID = md.SRC_HIERARCHY_ID", "        and se.STRUCTURE_ELEMENT_ID = sse.SRC_STRUCTURE_ELEMENT_ID", "        )", "order   by se.POSITION", ")", "select  tm.VIS_ID  as MODEL_VIS_ID", "        ,mm.MODEL_ID", "        ,sm.VIS_ID as SRC_MODEL_VIS_ID", "        ,SRC_MODEL_ID", "        -- dimensions:", "        ,cursor", "        (", "        select  td.VIS_ID  as DIM_VIS_ID", "                ,md.DIMENSION_ID", "                ,sd.VIS_ID as SRC_DIM_VIS_ID", "                ,SRC_DIMENSION_ID", "                ,h.VIS_ID  as SRC_HIERARCHY_VIS_ID", "                ,SRC_HIERARCHY_ID", "                -- dimension elements:", "                ,cursor", "                (", "                select  nvl(FULL_VIS_ID,VIS_ID) as DIM_ELEM_VIS_ID", "                        ,DIMENSION_ELEMENT_ID", "                        -- source structure elements:", "                        ,cursor", "                        (", "                        select  nvl(FULL_VIS_ID,VIS_ID) as SRC_SE_VIS_ID", "                                ,mse.SRC_STRUCTURE_ELEMENT_ID", "                        from    AMM_SRC_STRUCTURE_ELEMENT mse", "                                ,STRUCTURE_ELEMENT se", "                        ,getSrcCalStrucElems sse", "                        where   mse.AMM_DIMENSION_ELEMENT_ID = mde.AMM_DIMENSION_ELEMENT_ID", "                        and     se.STRUCTURE_ID              = md.SRC_HIERARCHY_ID", "                        and     se.STRUCTURE_ELEMENT_ID      = mse.SRC_STRUCTURE_ELEMENT_ID", "                        and     sse.STRUCTURE_ID (+)         = se.STRUCTURE_ID", "                        and     sse.STRUCTURE_ELEMENT_ID (+) = se.STRUCTURE_ELEMENT_ID", "                        order   by se.POSITION", "                        ) SRC_ELEMS", "                from    AMM_DIMENSION_ELEMENT mde", "                left    join DIMENSION_ELEMENT  using (DIMENSION_ELEMENT_ID)", "                left    join getCalDimElems sdc using (DIMENSION_ELEMENT_ID)", "                where   mde.AMM_DIMENSION_ID = md.AMM_DIMENSION_ID", "                order   by sdc.POSITION", "                ) DIM_ELEMS", "        from    AMM_DIMENSION md", "        left    join DIMENSION td             on (td.DIMENSION_ID   = md.DIMENSION_ID)", "        left    join MODEL_DIMENSION_REL mdr  on (mdr.DIMENSION_ID  = md.DIMENSION_ID)", "        left    join DIMENSION sd             on (sd.DIMENSION_ID   = md.SRC_DIMENSION_ID)", "        left    join MODEL_DIMENSION_REL smdr on (smdr.DIMENSION_ID = md.DIMENSION_ID)", "        left    join HIERARCHY h              on (h.HIERARCHY_ID    = md.SRC_HIERARCHY_ID)", "        where   md.AMM_MODEL_ID = mm.AMM_MODEL_ID", "        order   by mdr.DIMENSION_SEQ_NUM nulls last, smdr.DIMENSION_SEQ_NUM", "        ) DIMS", "        -- cubes:", "        ,cursor", "        (", "        select  tc.VIS_ID  as CUBE_VIS_ID", "                ,mc.FINANCE_CUBE_ID", "                ,sc.VIS_ID as SRC_CUBE_VIS_ID", "                ,mc.SRC_FINANCE_CUBE_ID", "                -- data types:", "                ,cursor", "                (", "                select  tdt.VIS_ID  as DATA_TYPE", "                        ,md.DATA_TYPE_ID", "                        ,sdt.VIS_ID as SRC_DATA_TYPE", "                        ,md.SRC_DATA_TYPE_ID", "                        ,SRC_START_YEAR_OFFSET", "                        ,SRC_END_YEAR_OFFSET", "                from    AMM_DATA_TYPE md", "                join    DATA_TYPE tdt on (tdt.DATA_TYPE_ID = md.DATA_TYPE_ID)", "                join    DATA_TYPE sdt on (sdt.DATA_TYPE_ID = md.SRC_DATA_TYPE_ID)", "                where   md.AMM_FINANCE_CUBE_ID = mc.AMM_FINANCE_CUBE_ID", "                ) DATA_TYPES", "        from    AMM_FINANCE_CUBE mc", "        join    FINANCE_CUBE tc on (tc.FINANCE_CUBE_ID = mc.FINANCE_CUBE_ID)", "        join    FINANCE_CUBE sc on (sc.FINANCE_CUBE_ID = mc.SRC_FINANCE_CUBE_ID)", "        where   mc.AMM_MODEL_ID = mm.AMM_MODEL_ID", "        ) CUBES", "from    AMM_MODEL mm", "join    MODEL tm on (tm.MODEL_ID = mm.MODEL_ID)", "join    MODEL sm on (sm.MODEL_ID = mm.SRC_MODEL_ID)", "where   AMM_MODEL_ID = <ammModelId>" });
/*      */ 
/* 1500 */     SqlExecutor sqle = new SqlExecutor("displayMappingDetails", getDataSource(), sqlb, this._log);
/* 1501 */     sqle.addBindVariable("<ammModelId>", Integer.valueOf(ammModelId));
/* 1502 */     sqle.setLogSql(false);
/* 1503 */     return sqle.getEntityList();
/*      */   }
/*      */ 
/*      */   public AmmMap getAmmMap()
/*      */   {
/* 1511 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "select  tm.VIS_ID       as MODEL_VIS_ID", "        ,tm.DESCRIPTION as MODEL_DESCR", "        ,mm.MODEL_ID", "        ,sm.VIS_ID      as SRC_MODEL_VIS_ID", "        ,sm.DESCRIPTION as SRC_MODEL_DESCR", "        ,SRC_MODEL_ID", "        ,mm.AMM_MODEL_ID", "        ,mm.UPDATED_TIME", "        ,fm.UPDATED_TIME as FMS_MAPPING_UPDATED_TIME", "        -- cubes:", "        ,cursor", "        (", "        select  tc.VIS_ID       as CUBE_VIS_ID", "                ,tc.DESCRIPTION as CUBE_DESCR", "                ,mc.FINANCE_CUBE_ID", "                ,sc.VIS_ID      as SRC_CUBE_VIS_ID", "                ,sc.DESCRIPTION as SRC_CUBE_DESCR", "                ,mc.SRC_FINANCE_CUBE_ID", "                ,mc.AMM_FINANCE_CUBE_ID", "                -- data types:", "                ,cursor", "                (", "                 select  md.DATA_TYPE_ID", "                        ,td.VIS_ID                   as DATA_TYPE_VIS_ID", "                        ,td.DESCRIPTION              as DATA_TYPE_DESCR", "                        ,cdt.CUBE_LAST_UPDATED_TIME  as LAST_UPDATE", "                        ,SRC_DATA_TYPE_ID", "                        ,sd.VIS_ID                   as SRC_DATA_TYPE_VIS_ID", "                        ,sd.DESCRIPTION              as SRC_DATA_TYPE_DESCR", "                        ,scdt.CUBE_LAST_UPDATED_TIME as SRC_LAST_UPDATE", "                        ,md.AMM_DATA_TYPE_ID", "                 from    AMM_DATA_TYPE md", "                 join    DATA_TYPE    td on (td.DATA_TYPE_ID = md.DATA_TYPE_ID)", "                 join    DATA_TYPE    sd on (sd.DATA_TYPE_ID = SRC_DATA_TYPE_ID)", "                         ,FINANCE_CUBE_DATA_TYPE cdt", "                         ,FINANCE_CUBE_DATA_TYPE scdt", "                 where   md.AMM_FINANCE_CUBE_ID = mc.AMM_FINANCE_CUBE_ID", "                 and     cdt.FINANCE_CUBE_ID    = mc.FINANCE_CUBE_ID", "                 and     cdt.DATA_TYPE_ID       = md.DATA_TYPE_ID", "                 and     scdt.FINANCE_CUBE_ID   = mc.SRC_FINANCE_CUBE_ID", "                 and     scdt.DATA_TYPE_ID      = SRC_DATA_TYPE_ID", "                ) DATA_TYPES", "        from    AMM_FINANCE_CUBE mc", "        join    FINANCE_CUBE tc on (tc.FINANCE_CUBE_ID = mc.FINANCE_CUBE_ID)", "        join    FINANCE_CUBE sc on (sc.FINANCE_CUBE_ID = mc.SRC_FINANCE_CUBE_ID)", "        where   mc.AMM_MODEL_ID = mm.AMM_MODEL_ID", "        ) CUBES", "from    AMM_MODEL mm", "join    MODEL tm on (tm.MODEL_ID = mm.MODEL_ID)", "join    MODEL sm on (sm.MODEL_ID = mm.SRC_MODEL_ID)", "left", "join    MAPPED_MODEL fm on (fm.MODEL_ID = mm.SRC_MODEL_ID)" });
/*      */ 
/* 1566 */     SqlExecutor sqle = new SqlExecutor("getAmmMap", getDataSource(), sqlb, this._log);
/* 1567 */     sqle.setLogSql(true);
/* 1568 */     ResultSet modelRS = sqle.getResultSet();
/* 1569 */     AmmMapImpl fullMap = new AmmMapImpl();
/*      */     try
/*      */     {
/* 1572 */       while (modelRS.next())
/*      */       {
/* 1574 */         Integer trgModelId = Integer.valueOf(modelRS.getInt("MODEL_ID"));
/* 1575 */         String trgModelVisId = modelRS.getString("MODEL_VIS_ID");
/* 1576 */         String trgModelDescr = modelRS.getString("MODEL_DESCR");
/* 1577 */         Integer srcModelId = Integer.valueOf(modelRS.getInt("SRC_MODEL_ID"));
/* 1578 */         String srcModelVisId = modelRS.getString("SRC_MODEL_VIS_ID");
/* 1579 */         String srcModelDescr = modelRS.getString("SRC_MODEL_DESCR");
/* 1580 */         Integer ammModelId = Integer.valueOf(modelRS.getInt("AMM_MODEL_ID"));
/* 1581 */         Timestamp mappingLastUpdate = modelRS.getTimestamp("UPDATED_TIME");
/* 1582 */         Timestamp fmsMappingLastUpdate = modelRS.getTimestamp("FMS_MAPPING_UPDATED_TIME");
/* 1583 */         ResultSet cubeRS = (ResultSet)modelRS.getObject("CUBES");
/*      */ 
/* 1586 */         AmmModelMapImpl tm = fullMap.findModel(trgModelId);
/* 1587 */         if (tm == null)
/*      */         {
/* 1589 */           tm = new AmmModelMapImpl(trgModelId, trgModelVisId, trgModelDescr, null, null);
/* 1590 */           fullMap.addModelMap(tm);
/*      */         }
/*      */ 
/* 1594 */         AmmModelMapImpl currSm = fullMap.findRootModel(srcModelId);
/*      */ 
/* 1597 */         AmmModelMapImpl sm = new AmmModelMapImpl(srcModelId, srcModelVisId, srcModelDescr, trgModelId, ammModelId);
/* 1598 */         tm.addModelMap(sm);
/*      */ 
/* 1601 */         if ((currSm != null) && (currSm.getParentId() == null))
/*      */         {
/* 1603 */           fullMap.removeRootModel(srcModelId);
/* 1604 */           sm.setModelMaps(currSm.getModelMaps());
/*      */         }
/*      */ 
/* 1607 */         while (cubeRS.next())
/*      */         {
/* 1609 */           Integer trgCubeId = Integer.valueOf(cubeRS.getInt("FINANCE_CUBE_ID"));
/* 1610 */           String trgCubeVisId = cubeRS.getString("CUBE_VIS_ID");
/* 1611 */           String trgCubeDescr = cubeRS.getString("CUBE_DESCR");
/* 1612 */           Integer srcCubeId = Integer.valueOf(cubeRS.getInt("SRC_FINANCE_CUBE_ID"));
/* 1613 */           String srcCubeVisId = cubeRS.getString("SRC_CUBE_VIS_ID");
/* 1614 */           String srcCubeDescr = cubeRS.getString("SRC_CUBE_DESCR");
/* 1615 */           ResultSet dataTypeRS = (ResultSet)cubeRS.getObject("DATA_TYPES");
/* 1616 */           AmmCubeMapImpl cm = new AmmCubeMapImpl(trgCubeId, trgCubeVisId, trgCubeDescr, srcCubeId, srcCubeVisId, srcCubeDescr, ammModelId);
/* 1617 */           sm.addFinanceCubeMap(cm);
/*      */ 
/* 1619 */           while (dataTypeRS.next())
/*      */           {
/* 1621 */             Integer trgDtId = Integer.valueOf(dataTypeRS.getInt("DATA_TYPE_ID"));
/* 1622 */             String trgDtVisId = dataTypeRS.getString("DATA_TYPE_VIS_ID");
/* 1623 */             String trgDtDescr = dataTypeRS.getString("DATA_TYPE_DESCR");
/* 1624 */             Timestamp trgLastUpdate = dataTypeRS.getTimestamp("LAST_UPDATE");
/* 1625 */             Integer srcDtId = Integer.valueOf(dataTypeRS.getInt("SRC_DATA_TYPE_ID"));
/* 1626 */             String srcDtVisId = dataTypeRS.getString("SRC_DATA_TYPE_VIS_ID");
/* 1627 */             String srcDtDescr = dataTypeRS.getString("SRC_DATA_TYPE_DESCR");
/* 1628 */             Timestamp srcLastUpdate = dataTypeRS.getTimestamp("SRC_LAST_UPDATE");
/* 1629 */             Integer ammDataTypeId = Integer.valueOf(dataTypeRS.getInt("AMM_DATA_TYPE_ID"));
/* 1630 */             AmmDataTypeMapImpl dtm = new AmmDataTypeMapImpl(trgDtId.intValue(), trgDtVisId, trgDtDescr, trgLastUpdate, srcDtId.intValue(), srcDtVisId, srcDtDescr, srcLastUpdate, ammDataTypeId.intValue(), mappingLastUpdate, fmsMappingLastUpdate, cm);
/*      */ 
/* 1634 */             cm.addDataTypeMap(dtm);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1641 */       throw new RuntimeException(e);
/*      */     }
/*      */     finally
/*      */     {
/* 1645 */       sqle.close();
/*      */     }
/* 1647 */     return fullMap;
/*      */   }
/*      */ 
/*      */   public boolean checkReferentialIntegrity(int ammModelId, int taskId)
/*      */   {
/* 1652 */     boolean mappingInvalidated = false;
/*      */ 
/* 1655 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "update  AMM_DIMENSION", "set     SRC_HIERARCHY_ID =", "        (", "        select  HIERARCHY_ID", "        from    (", "                select  DIMENSION_ID, HIERARCHY_ID", "                        ,rank() over", "                            (partition by DIMENSION_ID", "                             order by HIERARCHY_ID desc) rank", "                from    HIERARCHY", "                )", "        where   DIMENSION_ID = SRC_DIMENSION_ID", "        and     rank = 1", "        )", "where   AMM_MODEL_ID = <ammModelId>", "and     not exists", "        (", "        select  1", "        from    HIERARCHY", "        where   HIERARCHY_ID = SRC_HIERARCHY_ID", "        )" });
/*      */ 
/* 1678 */     SqlExecutor sqle = new SqlExecutor("handle deleted src hierarchies", getDataSource(), sqlb, this._log);
/* 1679 */     sqle.addBindVariable("<ammModelId>", Integer.valueOf(ammModelId));
/* 1680 */     int changeCount = sqle.executeUpdate();
/* 1681 */     mappingInvalidated = (mappingInvalidated) || (changeCount > 0);
/*      */ 
/* 1684 */     sqlb = new SqlBuilder(new String[] { "delete  from AMM_SRC_STRUCTURE_ELEMENT", "where   AMM_SRC_STRUCTURE_ELEMENT_ID", "        in", "        (", "        select  AMM_SRC_STRUCTURE_ELEMENT_ID", "        from    AMM_DIMENSION", "        join    AMM_DIMENSION_ELEMENT     using (AMM_DIMENSION_ID)", "        join    AMM_SRC_STRUCTURE_ELEMENT using (AMM_DIMENSION_ELEMENT_ID)", "        left", "        join    STRUCTURE_ELEMENT se", "                on", "                (", "                    STRUCTURE_ID = SRC_HIERARCHY_ID", "                and STRUCTURE_ELEMENT_ID = SRC_STRUCTURE_ELEMENT_ID", "                )", "        where   AMM_MODEL_ID = <ammModelId>", "        and     STRUCTURE_ID is null", "        )" });
/*      */ 
/* 1704 */     sqle = new SqlExecutor("remove invalid src structureElements", getDataSource(), sqlb, this._log);
/* 1705 */     sqle.addBindVariable("<ammModelId>", Integer.valueOf(ammModelId));
/* 1706 */     changeCount = sqle.executeUpdate();
/* 1707 */     mappingInvalidated = (mappingInvalidated) || (changeCount > 0);
/*      */ 
/* 1711 */     sqlb = new SqlBuilder(new String[] { "delete  from AMM_SRC_STRUCTURE_ELEMENT", "where   AMM_DIMENSION_ELEMENT_ID", "        in", "        (", "        select  AMM_DIMENSION_ELEMENT_ID", "        from    AMM_DIMENSION", "        join    AMM_DIMENSION_ELEMENT using (AMM_DIMENSION_ID)", "        left", "        join    DIMENSION_ELEMENT de  using (DIMENSION_ELEMENT_ID)", "        where   AMM_MODEL_ID = <ammModelId>", "        and     de.DIMENSION_ID is null", "        )" });
/*      */ 
/* 1725 */     sqle = new SqlExecutor("remove src structureElements with invalid owners", getDataSource(), sqlb, this._log);
/* 1726 */     sqle.addBindVariable("<ammModelId>", Integer.valueOf(ammModelId));
/* 1727 */     changeCount = sqle.executeUpdate();
/* 1728 */     mappingInvalidated = (mappingInvalidated) || (changeCount > 0);
/*      */ 
/* 1731 */     sqlb = new SqlBuilder(new String[] { "delete  from AMM_DIMENSION_ELEMENT", "where   AMM_DIMENSION_ELEMENT_ID", "        in", "        (", "        select  AMM_DIMENSION_ELEMENT_ID", "        from    AMM_DIMENSION", "        join    AMM_DIMENSION_ELEMENT using (AMM_DIMENSION_ID)", "        left", "        join    DIMENSION_ELEMENT de  using (DIMENSION_ELEMENT_ID)", "        where   AMM_MODEL_ID = <ammModelId>", "        and     de.DIMENSION_ID is null", "        )" });
/*      */ 
/* 1745 */     sqle = new SqlExecutor("remove invalid dimensionElements", getDataSource(), sqlb, this._log);
/* 1746 */     sqle.addBindVariable("<ammModelId>", Integer.valueOf(ammModelId));
/* 1747 */     changeCount = sqle.executeUpdate();
/* 1748 */     mappingInvalidated = (mappingInvalidated) || (changeCount > 0);
/*      */ 
/* 1752 */     sqlb = new SqlBuilder(new String[] { "delete  from AMM_DIMENSION_ELEMENT", "where   AMM_DIMENSION_ELEMENT_ID", "        in", "        (", "        select  AMM_DIMENSION_ELEMENT_ID", "        from    AMM_DIMENSION", "        join    AMM_DIMENSION_ELEMENT     using (AMM_DIMENSION_ID)", "        left", "        join    AMM_SRC_STRUCTURE_ELEMENT using (AMM_DIMENSION_ELEMENT_ID)", "        where   AMM_MODEL_ID = <ammModelId>", "        and     AMM_SRC_STRUCTURE_ELEMENT_ID is null", "        )" });
/*      */ 
/* 1766 */     sqle = new SqlExecutor("remove dimensionElements with no sources", getDataSource(), sqlb, this._log);
/* 1767 */     sqle.addBindVariable("<ammModelId>", Integer.valueOf(ammModelId));
/* 1768 */     changeCount = sqle.executeUpdate();
/* 1769 */     mappingInvalidated = (mappingInvalidated) || (changeCount > 0);
/*      */ 
/* 1772 */     if (mappingInvalidated)
/*      */     {
/* 1774 */       sqlb = new SqlBuilder(new String[] { "update  AMM_MODEL", "set     INVALIDATED_BY_TASK_ID = <taskId>", "        ,VERSION_NUM = VERSION_NUM + 1", "where   AMM_MODEL_ID = <ammModelId>" });
/*      */ 
/* 1780 */       sqle = new SqlExecutor("check src hierarchy", getDataSource(), sqlb, this._log);
/* 1781 */       sqle.addBindVariable("<taskId>", Integer.valueOf(taskId));
/* 1782 */       sqle.addBindVariable("<ammModelId>", Integer.valueOf(ammModelId));
/* 1783 */       changeCount = sqle.executeUpdate();
/* 1784 */       if (changeCount == 0) {
/* 1785 */         throw new IllegalStateException("failed to update AMM_MODEL");
/*      */       }
/*      */     }
/* 1788 */     return mappingInvalidated;
/*      */   }
/*      */ 
/*      */   public boolean isHierarchyReferenced(ModelPK modelPk, List<HierarchyPK> hierarchyPkList)
/*      */   {
/* 1793 */     if (hierarchyPkList.isEmpty()) {
/* 1794 */       return false;
/*      */     }
/* 1796 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "select  1 from dual", "where   exists", "        (", "        select 1", "        from   AMM_MODEL", "        join   AMM_DIMENSION using (AMM_MODEL_ID)", "        where  (   MODEL_ID     = ${modelId}", "               or  SRC_MODEL_ID = ${modelId}", "               )", "        and    SRC_HIERARCHY_ID in (${hierarchyIds})", "        )" });
/*      */ 
/* 1809 */     sqlb.substitute(new String[] { "${modelId}", String.valueOf(modelPk.getModelId()) });
/* 1810 */     StringBuilder sb = new StringBuilder();
/* 1811 */     for (HierarchyPK hpk : hierarchyPkList)
/*      */     {
/* 1813 */       if (sb.length() > 0)
/* 1814 */         sb.append(',');
/* 1815 */       sb.append(String.valueOf(hpk.getHierarchyId()));
/*      */     }
/* 1817 */     sqlb.substitute(new String[] { "${hierarchyIds}", sb.toString() });
/* 1818 */     SqlExecutor sqle = new SqlExecutor("isHierarchyReferenced", getDataSource(), sqlb, this._log);
/* 1819 */     ResultSet rs = sqle.getResultSet();
/* 1820 */     boolean retValue = false;
/*      */     try
/*      */     {
/* 1823 */       retValue = rs.next();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1827 */       throw new RuntimeException(e);
/*      */     }
/*      */     finally
/*      */     {
/* 1831 */       sqle.close();
/* 1832 */       this._log.debug("isHierarchyReferenced", String.valueOf(retValue));
/*      */     }
/*      */ 
/* 1835 */     return retValue;
/*      */   }
/*      */ 
/*      */   public boolean isDimensionElementReferenced(ModelPK modelPk, List<DimensionElementPK> elemPkList)
/*      */   {
/* 1840 */     if (elemPkList.isEmpty()) {
/* 1841 */       return false;
/*      */     }
/* 1843 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "select  1 from dual", "where   exists", "        (", "        select 1", "        from   AMM_MODEL", "        join   AMM_DIMENSION         using (AMM_MODEL_ID)", "        join   AMM_DIMENSION_ELEMENT using (AMM_DIMENSION_ID)", "        left", "        join   AMM_SRC_STRUCTURE_ELEMENT using (AMM_DIMENSION_ELEMENT_ID)", "        where  (   MODEL_ID     = ${modelId}", "               or  SRC_MODEL_ID = ${modelId}", "               )", "        and    (", "                   DIMENSION_ELEMENT_ID     in (${elemIds})", "               or  SRC_STRUCTURE_ELEMENT_ID in (${elemIds})", "               )", "        )" });
/*      */ 
/* 1862 */     sqlb.substitute(new String[] { "${modelId}", String.valueOf(modelPk.getModelId()) });
/* 1863 */     StringBuilder sb = new StringBuilder();
/* 1864 */     for (DimensionElementPK hpk : elemPkList)
/*      */     {
/* 1866 */       if (sb.length() > 0)
/* 1867 */         sb.append(',');
/* 1868 */       sb.append(String.valueOf(hpk.getDimensionElementId()));
/*      */     }
/* 1870 */     sqlb.substitute(new String[] { "${elemIds}", sb.toString() });
/* 1871 */     SqlExecutor sqle = new SqlExecutor("isDimElementReferenced", getDataSource(), sqlb, this._log);
/* 1872 */     ResultSet rs = sqle.getResultSet();
/* 1873 */     boolean retValue = false;
/*      */     try
/*      */     {
/* 1876 */       retValue = rs.next();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1880 */       throw new RuntimeException(e);
/*      */     }
/*      */     finally
/*      */     {
/* 1884 */       sqle.close();
/* 1885 */       this._log.debug("isDimElementReferenced", String.valueOf(retValue));
/*      */     }
/*      */ 
/* 1888 */     return retValue;
/*      */   }
/*      */ 
/*      */   public boolean isHierarchyElementReferenced(ModelPK modelPk, List<HierarchyElementPK> elemPkList)
/*      */   {
/* 1893 */     if (elemPkList.isEmpty()) {
/* 1894 */       return false;
/*      */     }
/* 1896 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "select  1 from dual", "where   exists", "        (", "        select 1", "        from   AMM_MODEL", "        join   AMM_DIMENSION         using (AMM_MODEL_ID)", "        join   AMM_DIMENSION_ELEMENT using (AMM_DIMENSION_ID)", "        join   AMM_SRC_STRUCTURE_ELEMENT using (AMM_DIMENSION_ELEMENT_ID)", "        where  (   MODEL_ID     = ${modelId}", "               or  SRC_MODEL_ID = ${modelId}", "               )", "        and    SRC_STRUCTURE_ELEMENT_ID in (${elemIds})", "        )" });
/*      */ 
/* 1911 */     sqlb.substitute(new String[] { "${modelId}", String.valueOf(modelPk.getModelId()) });
/* 1912 */     StringBuilder sb = new StringBuilder();
/* 1913 */     for (HierarchyElementPK hpk : elemPkList)
/*      */     {
/* 1915 */       this._log.debug("isHierarchyElementReferenced", new StringBuilder().append("hierarchyElementPK=").append(String.valueOf(hpk.getHierarchyElementId())).toString());
/*      */     }
/* 1917 */     for (HierarchyElementPK hpk : elemPkList)
/*      */     {
/* 1919 */       if (sb.length() > 0)
/* 1920 */         sb.append(',');
/* 1921 */       sb.append(String.valueOf(hpk.getHierarchyElementId()));
/*      */     }
/* 1923 */     sqlb.substitute(new String[] { "${elemIds}", sb.toString() });
/* 1924 */     SqlExecutor sqle = new SqlExecutor("isHierarchyElementReferenced", getDataSource(), sqlb, this._log);
/* 1925 */     ResultSet rs = sqle.getResultSet();
/* 1926 */     boolean retValue = false;
/*      */     try
/*      */     {
/* 1929 */       retValue = rs.next();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1933 */       throw new RuntimeException(e);
/*      */     }
/*      */     finally
/*      */     {
/* 1937 */       sqle.close();
/* 1938 */       this._log.debug("isHierarchyElementReferenced", String.valueOf(retValue));
/*      */     }
/*      */ 
/* 1941 */     return retValue;
/*      */   }
/*      */ 
/*      */   public int deleteMappingsForModel(ModelPK pk)
/*      */   {
/* 1946 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "select  AMM_MODEL_ID", "from    AMM_MODEL", "where   MODEL_ID = <modelId>", "or      SRC_MODEL_ID = <modelId>" });
/*      */ 
/* 1953 */     SqlExecutor sqle = new SqlExecutor("deleteMappingsForModel", getDataSource(), sqlb, this._log);
/* 1954 */     sqle.addBindVariable("<modelId>", Integer.valueOf(pk.getModelId()));
/* 1955 */     ResultSet rs = sqle.getResultSet();
/* 1956 */     int deleteCount = 0;
/*      */     try
/*      */     {
/* 1959 */       while (rs.next())
/*      */       {
/* 1961 */         int ammModelId = rs.getInt("AMM_MODEL_ID");
/* 1962 */         this.mDetails = new AmmModelEVO();
/* 1963 */         this.mDetails.setAmmModelId(ammModelId);
/* 1964 */         remove();
/* 1965 */         deleteCount++;
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1970 */       throw new RuntimeException(e);
/*      */     }
/*      */     finally
/*      */     {
/* 1974 */       sqle.close();
/*      */     }
/* 1976 */     return deleteCount;
/*      */   }
/*      */ 
/*      */   public AllAmmModelsELO getAllAmmModelsExceptThis(Object pk)
/*      */   {
/* 1981 */     AllAmmModelsELO fullList = getAllAmmModels();
/* 1982 */     AllAmmModelsELO myList = new AllAmmModelsELO();
/*      */ 
/* 1984 */     while (fullList.hasNext())
/*      */     {
/* 1986 */       fullList.next();
/* 1987 */       if (fullList.getAmmModelEntityRef().getPrimaryKey().equals(pk)) {
/*      */         continue;
/*      */       }
/* 1990 */       myList.add(fullList.getAmmModelEntityRef(), fullList.getModelId(), fullList.getCol2(), fullList.getCol3(), fullList.getSrcModelId(), fullList.getCol5(), fullList.getCol6(), fullList.getInvalidatedByTaskId());
/*      */     }
/*      */ 
/* 1994 */     return myList;
/*      */   }
/*      */
			public AllAmmModelsELO getAllAmmModelsForLoggedUser(int userId) {
			   Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  692 */     PreparedStatement stmt = null;
/*  693 */     ResultSet resultSet = null;
/*  694 */     AllAmmModelsELO results = new AllAmmModelsELO();
/*      */     try
/*      */     {
/*  697 */       stmt = getConnection().prepareStatement(SQL_ALL_AMM_MODELS_FOR_USER);
/*  698 */       int col = 1;
				 stmt.setInt(1, userId);
/*  699 */       resultSet = stmt.executeQuery();
/*  700 */       while (resultSet.next())
/*      */       {
/*  702 */         col = 2;
/*      */ 
/*  705 */         AmmModelPK pkAmmModel = new AmmModelPK(resultSet.getInt(col++));
/*      */ 
/*  708 */         String textAmmModel = "";
/*      */ 
/*  712 */         AmmModelRefImpl erAmmModel = new AmmModelRefImpl(pkAmmModel, textAmmModel);
/*      */ 
/*  717 */         int col1 = resultSet.getInt(col++);
/*  718 */         String col2 = resultSet.getString(col++);
/*  719 */         String col3 = resultSet.getString(col++);
/*  720 */         int col4 = resultSet.getInt(col++);
/*  721 */         String col5 = resultSet.getString(col++);
/*  722 */         String col6 = resultSet.getString(col++);
/*  723 */         Integer col7 = getWrappedIntegerFromJdbc(resultSet, col++);
/*      */ 
/*  726 */         results.add(erAmmModel, col1, col2, col3, col4, col5, col6, col7);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  740 */       throw handleSQLException(SQL_ALL_AMM_MODELS_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  744 */       closeResultSet(resultSet);
/*  745 */       closeStatement(stmt);
/*  746 */       closeConnection();
/*      */     }
/*      */ 
/*  749 */     if (timer != null) {
/*  750 */       timer.logDebug("getAllAmmModelsForLoggedUser", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  754 */     return results;
/*      */   }
/*      */ 

}

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.amm.AmmModelDAO
 * JD-Core Version:    0.6.0
 */