/*      */ package com.cedar.cp.ejb.impl.model;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.dimension.DimensionPK;
/*      */ import com.cedar.cp.dto.dimension.DimensionRefImpl;
/*      */ import com.cedar.cp.dto.model.AllModelBusAndAccDimensionsELO;
/*      */ import com.cedar.cp.dto.model.AllModelBusinessDimensionsELO;
/*      */ import com.cedar.cp.dto.model.BudgetDimensionIdForModelELO;
/*      */ import com.cedar.cp.dto.model.DimensionIdForModelDimTypeELO;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelDimensionRelCK;
/*      */ import com.cedar.cp.dto.model.ModelDimensionRelPK;
/*      */ import com.cedar.cp.dto.model.ModelDimensionRelRefImpl;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class ModelDimensionRelDAO extends AbstractDAO
/*      */ {
/*   35 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select MODEL_DIMENSION_REL.MODEL_ID,MODEL_DIMENSION_REL.DIMENSION_ID,MODEL_DIMENSION_REL.DIMENSION_TYPE,MODEL_DIMENSION_REL.DIMENSION_SEQ_NUM,MODEL_DIMENSION_REL.UPDATED_BY_USER_ID,MODEL_DIMENSION_REL.UPDATED_TIME,MODEL_DIMENSION_REL.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from MODEL_DIMENSION_REL where    MODEL_ID = ? AND DIMENSION_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into MODEL_DIMENSION_REL ( MODEL_ID,DIMENSION_ID,DIMENSION_TYPE,DIMENSION_SEQ_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update MODEL_DIMENSION_REL set DIMENSION_TYPE = ?,DIMENSION_SEQ_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MODEL_ID = ? AND DIMENSION_ID = ? ";
/*  320 */   protected static String SQL_ALL_MODEL_BUSINESS_DIMENSIONS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,MODEL_DIMENSION_REL.MODEL_ID from MODEL_DIMENSION_REL    ,MODEL where 1=1   and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID  and  MODEL_DIMENSION_REL.DIMENSION_TYPE = 2 order by MODEL_DIMENSION_REL.DIMENSION_ID";
/*      */ 
/*  430 */   protected static String SQL_ALL_MODEL_BUS_AND_ACC_DIMENSIONS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,DIMENSION.VIS_ID from MODEL_DIMENSION_REL    ,MODEL    ,DIMENSION where 1=1   and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID  and  MODEL_DIMENSION_REL.DIMENSION_ID = DIMENSION.DIMENSION_ID AND DIMENSION.TYPE IN (1,2) order by MODEL.VIS_ID,DIMENSION.VIS_ID";
/*      */ 
/*  562 */   protected static String SQL_BUDGET_DIMENSION_ID_FOR_MODEL = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID from MODEL_DIMENSION_REL    ,MODEL where 1=1   and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID  and  MODEL_DIMENSION_REL.MODEL_ID = ? and MODEL_DIMENSION_REL.DIMENSION_SEQ_NUM = ?";
/*      */ 
/*  677 */   protected static String SQL_DIMENSION_ID_FOR_MODEL_DIM_TYPE = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID from MODEL_DIMENSION_REL    ,MODEL where 1=1   and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID  and  MODEL_DIMENSION_REL.MODEL_ID = ? and MODEL_DIMENSION_REL.DIMENSION_TYPE = ?";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from MODEL_DIMENSION_REL where    MODEL_ID = ? AND DIMENSION_ID = ? ";
/*      */   public static final String SQL_BULK_GET_ALL = " from MODEL_DIMENSION_REL where 1=1 and MODEL_DIMENSION_REL.MODEL_ID = ? order by  MODEL_DIMENSION_REL.MODEL_ID ,MODEL_DIMENSION_REL.DIMENSION_ID";
/*      */   protected static final String SQL_GET_ALL = " from MODEL_DIMENSION_REL where    MODEL_ID = ? ";
/*      */   protected ModelDimensionRelEVO mDetails;
/*      */ 
/*      */   public ModelDimensionRelDAO(Connection connection)
/*      */   {
/*   42 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ModelDimensionRelDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ModelDimensionRelDAO(DataSource ds)
/*      */   {
/*   58 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ModelDimensionRelPK getPK()
/*      */   {
/*   66 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ModelDimensionRelEVO details)
/*      */   {
/*   75 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private ModelDimensionRelEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   93 */     int col = 1;
/*   94 */     ModelDimensionRelEVO evo = new ModelDimensionRelEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*      */ 
/*  101 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  102 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  103 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  104 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ModelDimensionRelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  109 */     int col = startCol_;
/*  110 */     stmt_.setInt(col++, evo_.getModelId());
/*  111 */     stmt_.setInt(col++, evo_.getDimensionId());
/*  112 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ModelDimensionRelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  117 */     int col = startCol_;
/*  118 */     stmt_.setInt(col++, evo_.getDimensionType());
/*  119 */     stmt_.setInt(col++, evo_.getDimensionSeqNum());
/*  120 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  121 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  122 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  123 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ModelDimensionRelPK pk)
/*      */     throws ValidationException
/*      */   {
/*  140 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  142 */     PreparedStatement stmt = null;
/*  143 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  147 */       stmt = getConnection().prepareStatement("select MODEL_DIMENSION_REL.MODEL_ID,MODEL_DIMENSION_REL.DIMENSION_ID,MODEL_DIMENSION_REL.DIMENSION_TYPE,MODEL_DIMENSION_REL.DIMENSION_SEQ_NUM,MODEL_DIMENSION_REL.UPDATED_BY_USER_ID,MODEL_DIMENSION_REL.UPDATED_TIME,MODEL_DIMENSION_REL.CREATED_TIME from MODEL_DIMENSION_REL where    MODEL_ID = ? AND DIMENSION_ID = ? ");
/*      */ 
/*  150 */       int col = 1;
/*  151 */       stmt.setInt(col++, pk.getModelId());
/*  152 */       stmt.setInt(col++, pk.getDimensionId());
/*      */ 
/*  154 */       resultSet = stmt.executeQuery();
/*      */ 
/*  156 */       if (!resultSet.next()) {
/*  157 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  160 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  161 */       if (this.mDetails.isModified())
/*  162 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  166 */       throw handleSQLException(pk, "select MODEL_DIMENSION_REL.MODEL_ID,MODEL_DIMENSION_REL.DIMENSION_ID,MODEL_DIMENSION_REL.DIMENSION_TYPE,MODEL_DIMENSION_REL.DIMENSION_SEQ_NUM,MODEL_DIMENSION_REL.UPDATED_BY_USER_ID,MODEL_DIMENSION_REL.UPDATED_TIME,MODEL_DIMENSION_REL.CREATED_TIME from MODEL_DIMENSION_REL where    MODEL_ID = ? AND DIMENSION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  170 */       closeResultSet(resultSet);
/*  171 */       closeStatement(stmt);
/*  172 */       closeConnection();
/*      */ 
/*  174 */       if (timer != null)
/*  175 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  206 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  207 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  212 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  213 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  214 */       stmt = getConnection().prepareStatement("insert into MODEL_DIMENSION_REL ( MODEL_ID,DIMENSION_ID,DIMENSION_TYPE,DIMENSION_SEQ_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*      */ 
/*  217 */       int col = 1;
/*  218 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  219 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  222 */       int resultCount = stmt.executeUpdate();
/*  223 */       if (resultCount != 1)
/*      */       {
/*  225 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  228 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  232 */       throw handleSQLException(this.mDetails.getPK(), "insert into MODEL_DIMENSION_REL ( MODEL_ID,DIMENSION_ID,DIMENSION_TYPE,DIMENSION_SEQ_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  236 */       closeStatement(stmt);
/*  237 */       closeConnection();
/*      */ 
/*  239 */       if (timer != null)
/*  240 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  265 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  269 */     PreparedStatement stmt = null;
/*      */ 
/*  271 */     boolean mainChanged = this.mDetails.isModified();
/*  272 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  275 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  278 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  279 */         stmt = getConnection().prepareStatement("update MODEL_DIMENSION_REL set DIMENSION_TYPE = ?,DIMENSION_SEQ_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MODEL_ID = ? AND DIMENSION_ID = ? ");
/*      */ 
/*  282 */         int col = 1;
/*  283 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  284 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  287 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  290 */         if (resultCount != 1) {
/*  291 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  294 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  303 */       throw handleSQLException(getPK(), "update MODEL_DIMENSION_REL set DIMENSION_TYPE = ?,DIMENSION_SEQ_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MODEL_ID = ? AND DIMENSION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  307 */       closeStatement(stmt);
/*  308 */       closeConnection();
/*      */ 
/*  310 */       if ((timer != null) && (
/*  311 */         (mainChanged) || (dependantChanged)))
/*  312 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllModelBusinessDimensionsELO getAllModelBusinessDimensions()
/*      */   {
/*  350 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  351 */     PreparedStatement stmt = null;
/*  352 */     ResultSet resultSet = null;
/*  353 */     AllModelBusinessDimensionsELO results = new AllModelBusinessDimensionsELO();
/*      */     try
/*      */     {
/*  356 */       stmt = getConnection().prepareStatement(SQL_ALL_MODEL_BUSINESS_DIMENSIONS);
/*  357 */       int col = 1;
/*  358 */       resultSet = stmt.executeQuery();
/*  359 */       while (resultSet.next())
/*      */       {
/*  361 */         col = 2;
/*      */ 
/*  364 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  367 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  370 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  374 */         String textModelDimensionRel = "";
/*      */ 
/*  379 */         ModelDimensionRelCK ckModelDimensionRel = new ModelDimensionRelCK(pkModel, pkModelDimensionRel);
/*      */ 
/*  385 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  391 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(ckModelDimensionRel, textModelDimensionRel);
/*      */ 
/*  396 */         int col1 = resultSet.getInt(col++);
/*  397 */         int col2 = resultSet.getInt(col++);
/*      */ 
/*  400 */         results.add(erModelDimensionRel, erModel, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  410 */       throw handleSQLException(SQL_ALL_MODEL_BUSINESS_DIMENSIONS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  414 */       closeResultSet(resultSet);
/*  415 */       closeStatement(stmt);
/*  416 */       closeConnection();
/*      */     }
/*      */ 
/*  419 */     if (timer != null) {
/*  420 */       timer.logDebug("getAllModelBusinessDimensions", " items=" + results.size());
/*      */     }
/*      */ 
/*  424 */     return results;
/*      */   }
/*      */ 
/*      */   public AllModelBusAndAccDimensionsELO getAllModelBusAndAccDimensions()
/*      */   {
/*  466 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  467 */     PreparedStatement stmt = null;
/*  468 */     ResultSet resultSet = null;
/*  469 */     AllModelBusAndAccDimensionsELO results = new AllModelBusAndAccDimensionsELO();
/*      */     try
/*      */     {
/*  472 */       stmt = getConnection().prepareStatement(SQL_ALL_MODEL_BUS_AND_ACC_DIMENSIONS);
/*  473 */       int col = 1;
/*  474 */       resultSet = stmt.executeQuery();
/*  475 */       while (resultSet.next())
/*      */       {
/*  477 */         col = 2;
/*      */ 
/*  480 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  483 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  486 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  490 */         String textModelDimensionRel = "";
/*      */ 
/*  493 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  496 */         String textDimension = resultSet.getString(col++);
/*  497 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/*  501 */         ModelDimensionRelCK ckModelDimensionRel = new ModelDimensionRelCK(pkModel, pkModelDimensionRel);
/*      */ 
/*  507 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  513 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(ckModelDimensionRel, textModelDimensionRel);
/*      */ 
/*  519 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/*  525 */         int col1 = resultSet.getInt(col++);
/*  526 */         int col2 = resultSet.getInt(col++);
/*  527 */         String col3 = resultSet.getString(col++);
/*      */ 
/*  530 */         results.add(erModelDimensionRel, erModel, erDimension, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  542 */       throw handleSQLException(SQL_ALL_MODEL_BUS_AND_ACC_DIMENSIONS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  546 */       closeResultSet(resultSet);
/*  547 */       closeStatement(stmt);
/*  548 */       closeConnection();
/*      */     }
/*      */ 
/*  551 */     if (timer != null) {
/*  552 */       timer.logDebug("getAllModelBusAndAccDimensions", " items=" + results.size());
/*      */     }
/*      */ 
/*  556 */     return results;
/*      */   }
/*      */ 
/*      */   public BudgetDimensionIdForModelELO getBudgetDimensionIdForModel(int param1, int param2)
/*      */   {
/*  595 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  596 */     PreparedStatement stmt = null;
/*  597 */     ResultSet resultSet = null;
/*  598 */     BudgetDimensionIdForModelELO results = new BudgetDimensionIdForModelELO();
/*      */     try
/*      */     {
/*  601 */       stmt = getConnection().prepareStatement(SQL_BUDGET_DIMENSION_ID_FOR_MODEL);
/*  602 */       int col = 1;
/*  603 */       stmt.setInt(col++, param1);
/*  604 */       stmt.setInt(col++, param2);
/*  605 */       resultSet = stmt.executeQuery();
/*  606 */       while (resultSet.next())
/*      */       {
/*  608 */         col = 2;
/*      */ 
/*  611 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  614 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  617 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  621 */         String textModelDimensionRel = "";
/*      */ 
/*  626 */         ModelDimensionRelCK ckModelDimensionRel = new ModelDimensionRelCK(pkModel, pkModelDimensionRel);
/*      */ 
/*  632 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  638 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(ckModelDimensionRel, textModelDimensionRel);
/*      */ 
/*  643 */         int col1 = resultSet.getInt(col++);
/*      */ 
/*  646 */         results.add(erModelDimensionRel, erModel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  655 */       throw handleSQLException(SQL_BUDGET_DIMENSION_ID_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  659 */       closeResultSet(resultSet);
/*  660 */       closeStatement(stmt);
/*  661 */       closeConnection();
/*      */     }
/*      */ 
/*  664 */     if (timer != null) {
/*  665 */       timer.logDebug("getBudgetDimensionIdForModel", " ModelId=" + param1 + ",DimensionSeqNum=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/*  671 */     return results;
/*      */   }
/*      */ 
/*      */   public DimensionIdForModelDimTypeELO getDimensionIdForModelDimType(int param1, int param2)
/*      */   {
/*  710 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  711 */     PreparedStatement stmt = null;
/*  712 */     ResultSet resultSet = null;
/*  713 */     DimensionIdForModelDimTypeELO results = new DimensionIdForModelDimTypeELO();
/*      */     try
/*      */     {
/*  716 */       stmt = getConnection().prepareStatement(SQL_DIMENSION_ID_FOR_MODEL_DIM_TYPE);
/*  717 */       int col = 1;
/*  718 */       stmt.setInt(col++, param1);
/*  719 */       stmt.setInt(col++, param2);
/*  720 */       resultSet = stmt.executeQuery();
/*  721 */       while (resultSet.next())
/*      */       {
/*  723 */         col = 2;
/*      */ 
/*  726 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  729 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  732 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  736 */         String textModelDimensionRel = "";
/*      */ 
/*  741 */         ModelDimensionRelCK ckModelDimensionRel = new ModelDimensionRelCK(pkModel, pkModelDimensionRel);
/*      */ 
/*  747 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  753 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(ckModelDimensionRel, textModelDimensionRel);
/*      */ 
/*  758 */         int col1 = resultSet.getInt(col++);
/*      */ 
/*  761 */         results.add(erModelDimensionRel, erModel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  770 */       throw handleSQLException(SQL_DIMENSION_ID_FOR_MODEL_DIM_TYPE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  774 */       closeResultSet(resultSet);
/*  775 */       closeStatement(stmt);
/*  776 */       closeConnection();
/*      */     }
/*      */ 
/*  779 */     if (timer != null) {
/*  780 */       timer.logDebug("getDimensionIdForModelDimType", " ModelId=" + param1 + ",DimensionType=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/*  786 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  804 */     if (items == null) {
/*  805 */       return false;
/*      */     }
/*  807 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  808 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  810 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  815 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  816 */       while (iter2.hasNext())
/*      */       {
/*  818 */         this.mDetails = ((ModelDimensionRelEVO)iter2.next());
/*      */ 
/*  821 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  823 */         somethingChanged = true;
/*      */ 
/*  826 */         if (deleteStmt == null) {
/*  827 */           deleteStmt = getConnection().prepareStatement("delete from MODEL_DIMENSION_REL where    MODEL_ID = ? AND DIMENSION_ID = ? ");
/*      */         }
/*      */ 
/*  830 */         int col = 1;
/*  831 */         deleteStmt.setInt(col++, this.mDetails.getModelId());
/*  832 */         deleteStmt.setInt(col++, this.mDetails.getDimensionId());
/*      */ 
/*  834 */         if (this._log.isDebugEnabled()) {
/*  835 */           this._log.debug("update", "ModelDimensionRel deleting ModelId=" + this.mDetails.getModelId() + ",DimensionId=" + this.mDetails.getDimensionId());
/*      */         }
/*      */ 
/*  841 */         deleteStmt.addBatch();
/*      */ 
/*  844 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  849 */       if (deleteStmt != null)
/*      */       {
/*  851 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  853 */         deleteStmt.executeBatch();
/*      */ 
/*  855 */         if (timer2 != null) {
/*  856 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  860 */       Iterator iter1 = items.values().iterator();
/*  861 */       while (iter1.hasNext())
/*      */       {
/*  863 */         this.mDetails = ((ModelDimensionRelEVO)iter1.next());
/*      */ 
/*  865 */         if (this.mDetails.insertPending())
/*      */         {
/*  867 */           somethingChanged = true;
/*  868 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  871 */         if (!this.mDetails.isModified())
/*      */           continue;
/*  873 */         somethingChanged = true;
/*  874 */         doStore();
/*      */       }
/*      */ 
/*  885 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  889 */       throw handleSQLException("delete from MODEL_DIMENSION_REL where    MODEL_ID = ? AND DIMENSION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  893 */       if (deleteStmt != null)
/*      */       {
/*  895 */         closeStatement(deleteStmt);
/*  896 */         closeConnection();
/*      */       }
/*      */ 
/*  899 */       this.mDetails = null;
/*      */ 
/*  901 */       if ((somethingChanged) && 
/*  902 */         (timer != null))
/*  903 */         timer.logDebug("update", "collection"); 
/*  903 */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants)
/*      */   {
/*  923 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  925 */     PreparedStatement stmt = null;
/*  926 */     ResultSet resultSet = null;
/*      */ 
/*  928 */     int itemCount = 0;
/*      */ 
/*  930 */     Collection theseItems = new ArrayList();
/*  931 */     owningEVO.setModelDimensionRels(theseItems);
/*  932 */     owningEVO.setModelDimensionRelsAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  936 */       stmt = getConnection().prepareStatement("select MODEL_DIMENSION_REL.MODEL_ID,MODEL_DIMENSION_REL.DIMENSION_ID,MODEL_DIMENSION_REL.DIMENSION_TYPE,MODEL_DIMENSION_REL.DIMENSION_SEQ_NUM,MODEL_DIMENSION_REL.UPDATED_BY_USER_ID,MODEL_DIMENSION_REL.UPDATED_TIME,MODEL_DIMENSION_REL.CREATED_TIME from MODEL_DIMENSION_REL where 1=1 and MODEL_DIMENSION_REL.MODEL_ID = ? order by  MODEL_DIMENSION_REL.MODEL_ID ,MODEL_DIMENSION_REL.DIMENSION_ID");
/*      */ 
/*  938 */       int col = 1;
/*  939 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  941 */       resultSet = stmt.executeQuery();
/*      */ 
/*  944 */       while (resultSet.next())
/*      */       {
/*  946 */         itemCount++;
/*  947 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  949 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  952 */       if (timer != null) {
/*  953 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  958 */       throw handleSQLException("select MODEL_DIMENSION_REL.MODEL_ID,MODEL_DIMENSION_REL.DIMENSION_ID,MODEL_DIMENSION_REL.DIMENSION_TYPE,MODEL_DIMENSION_REL.DIMENSION_SEQ_NUM,MODEL_DIMENSION_REL.UPDATED_BY_USER_ID,MODEL_DIMENSION_REL.UPDATED_TIME,MODEL_DIMENSION_REL.CREATED_TIME from MODEL_DIMENSION_REL where 1=1 and MODEL_DIMENSION_REL.MODEL_ID = ? order by  MODEL_DIMENSION_REL.MODEL_ID ,MODEL_DIMENSION_REL.DIMENSION_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  962 */       closeResultSet(resultSet);
/*  963 */       closeStatement(stmt);
/*  964 */       closeConnection();
/*      */ 
/*  966 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectModelId, String dependants, Collection currentList)
/*      */   {
/*  991 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  992 */     PreparedStatement stmt = null;
/*  993 */     ResultSet resultSet = null;
/*      */ 
/*  995 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  999 */       stmt = getConnection().prepareStatement("select MODEL_DIMENSION_REL.MODEL_ID,MODEL_DIMENSION_REL.DIMENSION_ID,MODEL_DIMENSION_REL.DIMENSION_TYPE,MODEL_DIMENSION_REL.DIMENSION_SEQ_NUM,MODEL_DIMENSION_REL.UPDATED_BY_USER_ID,MODEL_DIMENSION_REL.UPDATED_TIME,MODEL_DIMENSION_REL.CREATED_TIME from MODEL_DIMENSION_REL where    MODEL_ID = ? ");
/*      */ 
/* 1001 */       int col = 1;
/* 1002 */       stmt.setInt(col++, selectModelId);
/*      */ 
/* 1004 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1006 */       while (resultSet.next())
/*      */       {
/* 1008 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1011 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1014 */       if (currentList != null)
/*      */       {
/* 1017 */         ListIterator iter = items.listIterator();
/* 1018 */         ModelDimensionRelEVO currentEVO = null;
/* 1019 */         ModelDimensionRelEVO newEVO = null;
/* 1020 */         while (iter.hasNext())
/*      */         {
/* 1022 */           newEVO = (ModelDimensionRelEVO)iter.next();
/* 1023 */           Iterator iter2 = currentList.iterator();
/* 1024 */           while (iter2.hasNext())
/*      */           {
/* 1026 */             currentEVO = (ModelDimensionRelEVO)iter2.next();
/* 1027 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1029 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1035 */         Iterator iter2 = currentList.iterator();
/* 1036 */         while (iter2.hasNext())
/*      */         {
/* 1038 */           currentEVO = (ModelDimensionRelEVO)iter2.next();
/* 1039 */           if (currentEVO.insertPending()) {
/* 1040 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1044 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1048 */       throw handleSQLException("select MODEL_DIMENSION_REL.MODEL_ID,MODEL_DIMENSION_REL.DIMENSION_ID,MODEL_DIMENSION_REL.DIMENSION_TYPE,MODEL_DIMENSION_REL.DIMENSION_SEQ_NUM,MODEL_DIMENSION_REL.UPDATED_BY_USER_ID,MODEL_DIMENSION_REL.UPDATED_TIME,MODEL_DIMENSION_REL.CREATED_TIME from MODEL_DIMENSION_REL where    MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1052 */       closeResultSet(resultSet);
/* 1053 */       closeStatement(stmt);
/* 1054 */       closeConnection();
/*      */ 
/* 1056 */       if (timer != null) {
/* 1057 */         timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1062 */     return items;
/*      */   }
/*      */ 
/*      */   public ModelDimensionRelEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1076 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1079 */     if (this.mDetails == null) {
/* 1080 */       doLoad(((ModelDimensionRelCK)paramCK).getModelDimensionRelPK());
/*      */     }
/* 1082 */     else if (!this.mDetails.getPK().equals(((ModelDimensionRelCK)paramCK).getModelDimensionRelPK())) {
/* 1083 */       doLoad(((ModelDimensionRelCK)paramCK).getModelDimensionRelPK());
/*      */     }
/*      */ 
/* 1086 */     ModelDimensionRelEVO details = new ModelDimensionRelEVO();
/* 1087 */     details = this.mDetails.deepClone();
/*      */ 
/* 1089 */     if (timer != null) {
/* 1090 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1092 */     return details;
/*      */   }
/*      */ 
/*      */   public ModelDimensionRelEVO getDetails(ModelCK paramCK, ModelDimensionRelEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1098 */     ModelDimensionRelEVO savedEVO = this.mDetails;
/* 1099 */     this.mDetails = paramEVO;
/* 1100 */     ModelDimensionRelEVO newEVO = getDetails(paramCK, dependants);
/* 1101 */     this.mDetails = savedEVO;
/* 1102 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public ModelDimensionRelEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1108 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1112 */     ModelDimensionRelEVO details = this.mDetails.deepClone();
/*      */ 
/* 1114 */     if (timer != null) {
/* 1115 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1117 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1122 */     return "ModelDimensionRel";
/*      */   }
/*      */ 
/*      */   public ModelDimensionRelRefImpl getRef(ModelDimensionRelPK paramModelDimensionRelPK)
/*      */   {
/* 1127 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1128 */     PreparedStatement stmt = null;
/* 1129 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1132 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID from MODEL_DIMENSION_REL,MODEL where 1=1 and MODEL_DIMENSION_REL.MODEL_ID = ? and MODEL_DIMENSION_REL.DIMENSION_ID = ? and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID");
/* 1133 */       int col = 1;
/* 1134 */       stmt.setInt(col++, paramModelDimensionRelPK.getModelId());
/* 1135 */       stmt.setInt(col++, paramModelDimensionRelPK.getDimensionId());
/*      */ 
/* 1137 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1139 */       if (!resultSet.next()) {
/* 1140 */         throw new RuntimeException(getEntityName() + " getRef " + paramModelDimensionRelPK + " not found");
/*      */       }
/* 1142 */       col = 2;
/* 1143 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1147 */       String textModelDimensionRel = "";
/* 1148 */       ModelDimensionRelCK ckModelDimensionRel = new ModelDimensionRelCK(newModelPK, paramModelDimensionRelPK);
/*      */ 
/* 1153 */       ModelDimensionRelRefImpl localModelDimensionRelRefImpl = new ModelDimensionRelRefImpl(ckModelDimensionRel, textModelDimensionRel);
/*      */       return localModelDimensionRelRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1158 */       throw handleSQLException(paramModelDimensionRelPK, "select 0,MODEL.MODEL_ID from MODEL_DIMENSION_REL,MODEL where 1=1 and MODEL_DIMENSION_REL.MODEL_ID = ? and MODEL_DIMENSION_REL.DIMENSION_ID = ? and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1162 */       closeResultSet(resultSet);
/* 1163 */       closeStatement(stmt);
/* 1164 */       closeConnection();
/*      */ 
/* 1166 */       if (timer != null)
/* 1167 */         timer.logDebug("getRef", paramModelDimensionRelPK); 
/* 1167 */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.ModelDimensionRelDAO
 * JD-Core Version:    0.6.0
 */