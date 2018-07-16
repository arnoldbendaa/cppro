/*      */ package com.cedar.cp.ejb.impl.model.mapping;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.datatype.DataTypeRef;
/*      */ import com.cedar.cp.dto.datatype.DataTypePK;
/*      */ import com.cedar.cp.dto.datatype.DataTypeRefImpl;
/*      */ import com.cedar.cp.dto.model.mapping.MappedDataTypeCK;
/*      */ import com.cedar.cp.dto.model.mapping.MappedDataTypePK;
/*      */ import com.cedar.cp.dto.model.mapping.MappedFinanceCubeCK;
/*      */ import com.cedar.cp.dto.model.mapping.MappedFinanceCubePK;
/*      */ import com.cedar.cp.dto.model.mapping.MappedFinanceCubeRefImpl;
/*      */ import com.cedar.cp.dto.model.mapping.MappedModelCK;
/*      */ import com.cedar.cp.dto.model.mapping.MappedModelPK;
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
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class MappedFinanceCubeDAO extends AbstractDAO
/*      */ {
/*   35 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID,MAPPED_FINANCE_CUBE.FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.UPDATED_BY_USER_ID,MAPPED_FINANCE_CUBE.UPDATED_TIME,MAPPED_FINANCE_CUBE.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from MAPPED_FINANCE_CUBE where    MAPPED_FINANCE_CUBE_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into MAPPED_FINANCE_CUBE ( MAPPED_FINANCE_CUBE_ID,MAPPED_MODEL_ID,FINANCE_CUBE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update MAPPED_FINANCE_CUBE set MAPPED_MODEL_ID = ?,FINANCE_CUBE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_FINANCE_CUBE_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from MAPPED_FINANCE_CUBE where    MAPPED_FINANCE_CUBE_ID = ? ";
/*  467 */   private static String[][] SQL_DELETE_CHILDREN = { { "MAPPED_DATA_TYPE", "delete from MAPPED_DATA_TYPE where     MAPPED_DATA_TYPE.MAPPED_FINANCE_CUBE_ID = ? " } };
/*      */ 
/*  476 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  480 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from MAPPED_FINANCE_CUBE where 1=1 and MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID = ? order by  MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID";
/*      */   protected static final String SQL_GET_ALL = " from MAPPED_FINANCE_CUBE where    MAPPED_MODEL_ID = ? ";
/*      */   private static final String QUERY_MAPPED_DATA_TYPES_SQL = "with params as\n(\n  select ? as model_id,\n         ? as finance_cube_id \n  from dual\n)\nselect dt.data_type_id, dt.vis_id, dt.description, dt.sub_type, dt.measure_class \nfrom data_type dt \nwhere dt.data_type_id in \n(\n  select data_type_id \n  from mapped_data_type mdt,\n       mapped_finance_cube mfc,\n       mapped_model mm\n  where mm.model_id = (select model_id from params) and\n        mfc.mapped_model_id = mm.mapped_model_id and \n        mfc.finance_cube_id = (select finance_cube_id from params) and\n        mdt.mapped_finance_cube_id = mfc.mapped_finance_cube_id\n)";
/* 1032 */   private static String QUERY_IS_MAPPED_FIANNCE_CUBE = "select count(*) as nmfc from mapped_finance_cube mfc where mfc.finance_cube_id = ?";
/*      */   protected MappedDataTypeDAO mMappedDataTypeDAO;
/*      */   protected MappedFinanceCubeEVO mDetails;
/*      */ 
/*      */   public MappedFinanceCubeDAO(Connection connection)
/*      */   {
/*   42 */     super(connection);
/*      */   }
/*      */ 
/*      */   public MappedFinanceCubeDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public MappedFinanceCubeDAO(DataSource ds)
/*      */   {
/*   58 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected MappedFinanceCubePK getPK()
/*      */   {
/*   66 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(MappedFinanceCubeEVO details)
/*      */   {
/*   75 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private MappedFinanceCubeEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   92 */     int col = 1;
/*   93 */     MappedFinanceCubeEVO evo = new MappedFinanceCubeEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  100 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  101 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  102 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  103 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(MappedFinanceCubeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  108 */     int col = startCol_;
/*  109 */     stmt_.setInt(col++, evo_.getMappedFinanceCubeId());
/*  110 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(MappedFinanceCubeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  115 */     int col = startCol_;
/*  116 */     stmt_.setInt(col++, evo_.getMappedModelId());
/*  117 */     stmt_.setInt(col++, evo_.getFinanceCubeId());
/*  118 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  119 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  120 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  121 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(MappedFinanceCubePK pk)
/*      */     throws ValidationException
/*      */   {
/*  137 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  139 */     PreparedStatement stmt = null;
/*  140 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  144 */       stmt = getConnection().prepareStatement("select MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID,MAPPED_FINANCE_CUBE.FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.UPDATED_BY_USER_ID,MAPPED_FINANCE_CUBE.UPDATED_TIME,MAPPED_FINANCE_CUBE.CREATED_TIME from MAPPED_FINANCE_CUBE where    MAPPED_FINANCE_CUBE_ID = ? ");
/*      */ 
/*  147 */       int col = 1;
/*  148 */       stmt.setInt(col++, pk.getMappedFinanceCubeId());
/*      */ 
/*  150 */       resultSet = stmt.executeQuery();
/*      */ 
/*  152 */       if (!resultSet.next()) {
/*  153 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  156 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  157 */       if (this.mDetails.isModified())
/*  158 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  162 */       throw handleSQLException(pk, "select MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID,MAPPED_FINANCE_CUBE.FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.UPDATED_BY_USER_ID,MAPPED_FINANCE_CUBE.UPDATED_TIME,MAPPED_FINANCE_CUBE.CREATED_TIME from MAPPED_FINANCE_CUBE where    MAPPED_FINANCE_CUBE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  166 */       closeResultSet(resultSet);
/*  167 */       closeStatement(stmt);
/*  168 */       closeConnection();
/*      */ 
/*  170 */       if (timer != null)
/*  171 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  200 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  201 */     this.mDetails.postCreateInit();
/*      */ 
/*  203 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  208 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  209 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  210 */       stmt = getConnection().prepareStatement("insert into MAPPED_FINANCE_CUBE ( MAPPED_FINANCE_CUBE_ID,MAPPED_MODEL_ID,FINANCE_CUBE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)");
/*      */ 
/*  213 */       int col = 1;
/*  214 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  215 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  218 */       int resultCount = stmt.executeUpdate();
/*  219 */       if (resultCount != 1)
/*      */       {
/*  221 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  224 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  228 */       throw handleSQLException(this.mDetails.getPK(), "insert into MAPPED_FINANCE_CUBE ( MAPPED_FINANCE_CUBE_ID,MAPPED_MODEL_ID,FINANCE_CUBE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  232 */       closeStatement(stmt);
/*  233 */       closeConnection();
/*      */ 
/*  235 */       if (timer != null) {
/*  236 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  242 */       getMappedDataTypeDAO().update(this.mDetails.getMappedDataTypesMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  248 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  272 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  276 */     PreparedStatement stmt = null;
/*      */ 
/*  278 */     boolean mainChanged = this.mDetails.isModified();
/*  279 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  283 */       if (getMappedDataTypeDAO().update(this.mDetails.getMappedDataTypesMap())) {
/*  284 */         dependantChanged = true;
/*      */       }
/*  286 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  289 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  290 */         stmt = getConnection().prepareStatement("update MAPPED_FINANCE_CUBE set MAPPED_MODEL_ID = ?,FINANCE_CUBE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_FINANCE_CUBE_ID = ? ");
/*      */ 
/*  293 */         int col = 1;
/*  294 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  295 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  298 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  301 */         if (resultCount != 1) {
/*  302 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  305 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  314 */       throw handleSQLException(getPK(), "update MAPPED_FINANCE_CUBE set MAPPED_MODEL_ID = ?,FINANCE_CUBE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_FINANCE_CUBE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  318 */       closeStatement(stmt);
/*  319 */       closeConnection();
/*      */ 
/*  321 */       if ((timer != null) && (
/*  322 */         (mainChanged) || (dependantChanged)))
/*  323 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  342 */     if (items == null) {
/*  343 */       return false;
/*      */     }
/*  345 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  346 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  348 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  352 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  353 */       while (iter3.hasNext())
/*      */       {
/*  355 */         this.mDetails = ((MappedFinanceCubeEVO)iter3.next());
/*  356 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  358 */         somethingChanged = true;
/*      */ 
/*  361 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  365 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  366 */       while (iter2.hasNext())
/*      */       {
/*  368 */         this.mDetails = ((MappedFinanceCubeEVO)iter2.next());
/*      */ 
/*  371 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  373 */         somethingChanged = true;
/*      */ 
/*  376 */         if (deleteStmt == null) {
/*  377 */           deleteStmt = getConnection().prepareStatement("delete from MAPPED_FINANCE_CUBE where    MAPPED_FINANCE_CUBE_ID = ? ");
/*      */         }
/*      */ 
/*  380 */         int col = 1;
/*  381 */         deleteStmt.setInt(col++, this.mDetails.getMappedFinanceCubeId());
/*      */ 
/*  383 */         if (this._log.isDebugEnabled()) {
/*  384 */           this._log.debug("update", "MappedFinanceCube deleting MappedFinanceCubeId=" + this.mDetails.getMappedFinanceCubeId());
/*      */         }
/*      */ 
/*  389 */         deleteStmt.addBatch();
/*      */ 
/*  392 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  397 */       if (deleteStmt != null)
/*      */       {
/*  399 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  401 */         deleteStmt.executeBatch();
/*      */ 
/*  403 */         if (timer2 != null) {
/*  404 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  408 */       Iterator iter1 = items.values().iterator();
/*  409 */       while (iter1.hasNext())
/*      */       {
/*  411 */         this.mDetails = ((MappedFinanceCubeEVO)iter1.next());
/*      */ 
/*  413 */         if (this.mDetails.insertPending())
/*      */         {
/*  415 */           somethingChanged = true;
/*  416 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  419 */         if (this.mDetails.isModified())
/*      */         {
/*  421 */           somethingChanged = true;
/*  422 */           doStore(); continue;
/*      */         }
/*      */ 
/*  426 */         if ((this.mDetails.deletePending()) || 
/*  432 */           (!getMappedDataTypeDAO().update(this.mDetails.getMappedDataTypesMap()))) continue;
/*  433 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  445 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  449 */       throw handleSQLException("delete from MAPPED_FINANCE_CUBE where    MAPPED_FINANCE_CUBE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  453 */       if (deleteStmt != null)
/*      */       {
/*  455 */         closeStatement(deleteStmt);
/*  456 */         closeConnection();
/*      */       }
/*      */ 
/*  459 */       this.mDetails = null;
/*      */ 
/*  461 */       if ((somethingChanged) && 
/*  462 */         (timer != null))
/*  463 */         timer.logDebug("update", "collection"); 
/*  463 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(MappedFinanceCubePK pk)
/*      */   {
/*  489 */     Set emptyStrings = Collections.emptySet();
/*  490 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(MappedFinanceCubePK pk, Set<String> exclusionTables)
/*      */   {
/*  496 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  498 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  500 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  502 */       PreparedStatement stmt = null;
/*      */ 
/*  504 */       int resultCount = 0;
/*  505 */       String s = null;
/*      */       try
/*      */       {
/*  508 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  510 */         if (this._log.isDebugEnabled()) {
/*  511 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  513 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  516 */         int col = 1;
/*  517 */         stmt.setInt(col++, pk.getMappedFinanceCubeId());
/*      */ 
/*  520 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  524 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  528 */         closeStatement(stmt);
/*  529 */         closeConnection();
/*      */ 
/*  531 */         if (timer != null) {
/*  532 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  536 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  538 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  540 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  542 */       PreparedStatement stmt = null;
/*      */ 
/*  544 */       int resultCount = 0;
/*  545 */       String s = null;
/*      */       try
/*      */       {
/*  548 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  550 */         if (this._log.isDebugEnabled()) {
/*  551 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  553 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  556 */         int col = 1;
/*  557 */         stmt.setInt(col++, pk.getMappedFinanceCubeId());
/*      */ 
/*  560 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  564 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  568 */         closeStatement(stmt);
/*  569 */         closeConnection();
/*      */ 
/*  571 */         if (timer != null)
/*  572 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(MappedModelPK entityPK, MappedModelEVO owningEVO, String dependants)
/*      */   {
/*  592 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  594 */     PreparedStatement stmt = null;
/*  595 */     ResultSet resultSet = null;
/*      */ 
/*  597 */     int itemCount = 0;
/*      */ 
/*  599 */     Collection theseItems = new ArrayList();
/*  600 */     owningEVO.setMappedFinanceCubes(theseItems);
/*  601 */     owningEVO.setMappedFinanceCubesAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  605 */       stmt = getConnection().prepareStatement("select MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID,MAPPED_FINANCE_CUBE.FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.UPDATED_BY_USER_ID,MAPPED_FINANCE_CUBE.UPDATED_TIME,MAPPED_FINANCE_CUBE.CREATED_TIME from MAPPED_FINANCE_CUBE where 1=1 and MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID = ? order by  MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID");
/*      */ 
/*  607 */       int col = 1;
/*  608 */       stmt.setInt(col++, entityPK.getMappedModelId());
/*      */ 
/*  610 */       resultSet = stmt.executeQuery();
/*      */ 
/*  613 */       while (resultSet.next())
/*      */       {
/*  615 */         itemCount++;
/*  616 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  618 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  621 */       if (timer != null) {
/*  622 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  625 */       if ((itemCount > 0) && (dependants.indexOf("<3>") > -1))
/*      */       {
/*  627 */         getMappedDataTypeDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  631 */       throw handleSQLException("select MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID,MAPPED_FINANCE_CUBE.FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.UPDATED_BY_USER_ID,MAPPED_FINANCE_CUBE.UPDATED_TIME,MAPPED_FINANCE_CUBE.CREATED_TIME from MAPPED_FINANCE_CUBE where 1=1 and MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID = ? order by  MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  635 */       closeResultSet(resultSet);
/*  636 */       closeStatement(stmt);
/*  637 */       closeConnection();
/*      */ 
/*  639 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectMappedModelId, String dependants, Collection currentList)
/*      */   {
/*  664 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  665 */     PreparedStatement stmt = null;
/*  666 */     ResultSet resultSet = null;
/*      */ 
/*  668 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  672 */       stmt = getConnection().prepareStatement("select MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID,MAPPED_FINANCE_CUBE.FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.UPDATED_BY_USER_ID,MAPPED_FINANCE_CUBE.UPDATED_TIME,MAPPED_FINANCE_CUBE.CREATED_TIME from MAPPED_FINANCE_CUBE where    MAPPED_MODEL_ID = ? ");
/*      */ 
/*  674 */       int col = 1;
/*  675 */       stmt.setInt(col++, selectMappedModelId);
/*      */ 
/*  677 */       resultSet = stmt.executeQuery();
/*      */ 
/*  679 */       while (resultSet.next())
/*      */       {
/*  681 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  684 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  687 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  690 */       if (currentList != null)
/*      */       {
/*  693 */         ListIterator iter = items.listIterator();
/*  694 */         MappedFinanceCubeEVO currentEVO = null;
/*  695 */         MappedFinanceCubeEVO newEVO = null;
/*  696 */         while (iter.hasNext())
/*      */         {
/*  698 */           newEVO = (MappedFinanceCubeEVO)iter.next();
/*  699 */           Iterator iter2 = currentList.iterator();
/*  700 */           while (iter2.hasNext())
/*      */           {
/*  702 */             currentEVO = (MappedFinanceCubeEVO)iter2.next();
/*  703 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  705 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  711 */         Iterator iter2 = currentList.iterator();
/*  712 */         while (iter2.hasNext())
/*      */         {
/*  714 */           currentEVO = (MappedFinanceCubeEVO)iter2.next();
/*  715 */           if (currentEVO.insertPending()) {
/*  716 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  720 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  724 */       throw handleSQLException("select MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID,MAPPED_FINANCE_CUBE.FINANCE_CUBE_ID,MAPPED_FINANCE_CUBE.UPDATED_BY_USER_ID,MAPPED_FINANCE_CUBE.UPDATED_TIME,MAPPED_FINANCE_CUBE.CREATED_TIME from MAPPED_FINANCE_CUBE where    MAPPED_MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  728 */       closeResultSet(resultSet);
/*  729 */       closeStatement(stmt);
/*  730 */       closeConnection();
/*      */ 
/*  732 */       if (timer != null) {
/*  733 */         timer.logDebug("getAll", " MappedModelId=" + selectMappedModelId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  738 */     return items;
/*      */   }
/*      */ 
/*      */   public MappedFinanceCubeEVO getDetails(MappedModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  755 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  758 */     if (this.mDetails == null) {
/*  759 */       doLoad(((MappedFinanceCubeCK)paramCK).getMappedFinanceCubePK());
/*      */     }
/*  761 */     else if (!this.mDetails.getPK().equals(((MappedFinanceCubeCK)paramCK).getMappedFinanceCubePK())) {
/*  762 */       doLoad(((MappedFinanceCubeCK)paramCK).getMappedFinanceCubePK());
/*      */     }
/*      */ 
/*  765 */     if ((dependants.indexOf("<3>") > -1) && (!this.mDetails.isMappedDataTypesAllItemsLoaded()))
/*      */     {
/*  770 */       this.mDetails.setMappedDataTypes(getMappedDataTypeDAO().getAll(this.mDetails.getMappedFinanceCubeId(), dependants, this.mDetails.getMappedDataTypes()));
/*      */ 
/*  777 */       this.mDetails.setMappedDataTypesAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  780 */     if ((paramCK instanceof MappedDataTypeCK))
/*      */     {
/*  782 */       if (this.mDetails.getMappedDataTypes() == null) {
/*  783 */         this.mDetails.loadMappedDataTypesItem(getMappedDataTypeDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  786 */         MappedDataTypePK pk = ((MappedDataTypeCK)paramCK).getMappedDataTypePK();
/*  787 */         MappedDataTypeEVO evo = this.mDetails.getMappedDataTypesItem(pk);
/*  788 */         if (evo == null) {
/*  789 */           this.mDetails.loadMappedDataTypesItem(getMappedDataTypeDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  794 */     MappedFinanceCubeEVO details = new MappedFinanceCubeEVO();
/*  795 */     details = this.mDetails.deepClone();
/*      */ 
/*  797 */     if (timer != null) {
/*  798 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  800 */     return details;
/*      */   }
/*      */ 
/*      */   public MappedFinanceCubeEVO getDetails(MappedModelCK paramCK, MappedFinanceCubeEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  806 */     MappedFinanceCubeEVO savedEVO = this.mDetails;
/*  807 */     this.mDetails = paramEVO;
/*  808 */     MappedFinanceCubeEVO newEVO = getDetails(paramCK, dependants);
/*  809 */     this.mDetails = savedEVO;
/*  810 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public MappedFinanceCubeEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  816 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  820 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  823 */     MappedFinanceCubeEVO details = this.mDetails.deepClone();
/*      */ 
/*  825 */     if (timer != null) {
/*  826 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  828 */     return details;
/*      */   }
/*      */ 
/*      */   protected MappedDataTypeDAO getMappedDataTypeDAO()
/*      */   {
/*  837 */     if (this.mMappedDataTypeDAO == null)
/*      */     {
/*  839 */       if (this.mDataSource != null)
/*  840 */         this.mMappedDataTypeDAO = new MappedDataTypeDAO(this.mDataSource);
/*      */       else {
/*  842 */         this.mMappedDataTypeDAO = new MappedDataTypeDAO(getConnection());
/*      */       }
/*      */     }
/*  845 */     return this.mMappedDataTypeDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  850 */     return "MappedFinanceCube";
/*      */   }
/*      */ 
/*      */   public MappedFinanceCubeRefImpl getRef(MappedFinanceCubePK paramMappedFinanceCubePK)
/*      */   {
/*  855 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  856 */     PreparedStatement stmt = null;
/*  857 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  860 */       stmt = getConnection().prepareStatement("select 0,MAPPED_MODEL.MAPPED_MODEL_ID from MAPPED_FINANCE_CUBE,MAPPED_MODEL where 1=1 and MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID = ? and MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID");
/*  861 */       int col = 1;
/*  862 */       stmt.setInt(col++, paramMappedFinanceCubePK.getMappedFinanceCubeId());
/*      */ 
/*  864 */       resultSet = stmt.executeQuery();
/*      */ 
/*  866 */       if (!resultSet.next()) {
/*  867 */         throw new RuntimeException(getEntityName() + " getRef " + paramMappedFinanceCubePK + " not found");
/*      */       }
/*  869 */       col = 2;
/*  870 */       MappedModelPK newMappedModelPK = new MappedModelPK(resultSet.getInt(col++));
/*      */ 
/*  874 */       String textMappedFinanceCube = "";
/*  875 */       MappedFinanceCubeCK ckMappedFinanceCube = new MappedFinanceCubeCK(newMappedModelPK, paramMappedFinanceCubePK);
/*      */ 
/*  880 */       MappedFinanceCubeRefImpl localMappedFinanceCubeRefImpl = new MappedFinanceCubeRefImpl(ckMappedFinanceCube, textMappedFinanceCube);
/*      */       return localMappedFinanceCubeRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  885 */       throw handleSQLException(paramMappedFinanceCubePK, "select 0,MAPPED_MODEL.MAPPED_MODEL_ID from MAPPED_FINANCE_CUBE,MAPPED_MODEL where 1=1 and MAPPED_FINANCE_CUBE.MAPPED_FINANCE_CUBE_ID = ? and MAPPED_FINANCE_CUBE.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  889 */       closeResultSet(resultSet);
/*  890 */       closeStatement(stmt);
/*  891 */       closeConnection();
/*      */ 
/*  893 */       if (timer != null)
/*  894 */         timer.logDebug("getRef", paramMappedFinanceCubePK); 
/*  894 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/*  906 */     if (c == null)
/*  907 */       return;
/*  908 */     Iterator iter = c.iterator();
/*  909 */     while (iter.hasNext())
/*      */     {
/*  911 */       MappedFinanceCubeEVO evo = (MappedFinanceCubeEVO)iter.next();
/*  912 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(MappedFinanceCubeEVO evo, String dependants)
/*      */   {
/*  926 */     if (evo.insertPending()) {
/*  927 */       return;
/*      */     }
/*  929 */     if (evo.getMappedFinanceCubeId() < 1) {
/*  930 */       return;
/*      */     }
/*      */ 
/*  934 */     if (dependants.indexOf("<3>") > -1)
/*      */     {
/*  937 */       if (!evo.isMappedDataTypesAllItemsLoaded())
/*      */       {
/*  939 */         evo.setMappedDataTypes(getMappedDataTypeDAO().getAll(evo.getMappedFinanceCubeId(), dependants, evo.getMappedDataTypes()));
/*      */ 
/*  946 */         evo.setMappedDataTypesAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Set<DataTypeRef> queryMappedDataTypes(int model, int financeCubeId)
/*      */   {
/*  984 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  985 */     PreparedStatement stmt = null;
/*  986 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  989 */       stmt = getConnection().prepareStatement("with params as\n(\n  select ? as model_id,\n         ? as finance_cube_id \n  from dual\n)\nselect dt.data_type_id, dt.vis_id, dt.description, dt.sub_type, dt.measure_class \nfrom data_type dt \nwhere dt.data_type_id in \n(\n  select data_type_id \n  from mapped_data_type mdt,\n       mapped_finance_cube mfc,\n       mapped_model mm\n  where mm.model_id = (select model_id from params) and\n        mfc.mapped_model_id = mm.mapped_model_id and \n        mfc.finance_cube_id = (select finance_cube_id from params) and\n        mdt.mapped_finance_cube_id = mfc.mapped_finance_cube_id\n)");
/*      */ 
/*  991 */       int col = 1;
/*  992 */       stmt.setInt(col++, model);
/*  993 */       stmt.setInt(col, financeCubeId);
/*      */ 
/*  995 */       resultSet = stmt.executeQuery();
/*      */ 
/*  997 */       Set dataTypes = new TreeSet();
/*      */ 
/*  999 */       while (resultSet.next())
/*      */       {
/* 1001 */         short dataTypeId = resultSet.getShort("data_type_id");
/* 1002 */         String vis_id = resultSet.getString("vis_id");
/* 1003 */         String description = resultSet.getString("description");
/* 1004 */         int subType = resultSet.getInt("sub_type");
/* 1005 */         int measureClass = resultSet.getInt("measure_class");
/* 1006 */         boolean measureClassNull = resultSet.wasNull();
/*      */ 
/* 1008 */         DataTypeRefImpl dataType = new DataTypeRefImpl(new DataTypePK(dataTypeId), vis_id, description, subType, measureClassNull ? null : Integer.valueOf(measureClass), null);
/*      */ 
/* 1012 */         dataTypes.add(dataType);
/*      */       }
/*      */ 
/*      */       return dataTypes;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1019 */       throw handleSQLException("with params as\n(\n  select ? as model_id,\n         ? as finance_cube_id \n  from dual\n)\nselect dt.data_type_id, dt.vis_id, dt.description, dt.sub_type, dt.measure_class \nfrom data_type dt \nwhere dt.data_type_id in \n(\n  select data_type_id \n  from mapped_data_type mdt,\n       mapped_finance_cube mfc,\n       mapped_model mm\n  where mm.model_id = (select model_id from params) and\n        mfc.mapped_model_id = mm.mapped_model_id and \n        mfc.finance_cube_id = (select finance_cube_id from params) and\n        mdt.mapped_finance_cube_id = mfc.mapped_finance_cube_id\n)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1023 */       closeResultSet(resultSet);
/* 1024 */       closeStatement(stmt);
/* 1025 */       closeConnection();
/*      */ 
/* 1027 */       if (timer != null)
/* 1028 */         timer.logDebug("queryMappedDataTypes", Integer.valueOf(financeCubeId)); 
/* 1028 */     }
/*      */   }
/*      */ 

		public boolean isFinanceCubeMapped(int financeCubeId) {
		    Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
		    PreparedStatement stmt = null;
		    ResultSet resultSet = null;
		
		    boolean var6;
		    try {
		       stmt = this.getConnection().prepareStatement(QUERY_IS_MAPPED_FIANNCE_CUBE);
		       byte sqle = 1;
		       stmt.setInt(sqle, financeCubeId);
		       resultSet = stmt.executeQuery();
		       var6 = resultSet.next() && resultSet.getInt("nmfc") > 0;
		    } catch (SQLException var10) {
		       throw this.handleSQLException(QUERY_IS_MAPPED_FIANNCE_CUBE, var10);
		    } finally {
		       this.closeResultSet(resultSet);
		       this.closeStatement(stmt);
		       this.closeConnection();
		       if(timer != null) {
		          timer.logDebug("isFinanceCubeMapped", Integer.valueOf(financeCubeId));
		       }
		
		    }
		
		    return var6;
		 }

}
/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.mapping.MappedFinanceCubeDAO
 * JD-Core Version:    0.6.0
 */