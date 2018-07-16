/*      */ package com.cedar.cp.ejb.impl.model.amm;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.datatype.DataTypeRef;
/*      */ import com.cedar.cp.dto.datatype.DataTypePK;
/*      */ import com.cedar.cp.dto.datatype.DataTypeRefImpl;
/*      */ import com.cedar.cp.dto.model.amm.AmmDataTypeCK;
/*      */ import com.cedar.cp.dto.model.amm.AmmDataTypePK;
/*      */ import com.cedar.cp.dto.model.amm.AmmFinanceCubeCK;
/*      */ import com.cedar.cp.dto.model.amm.AmmFinanceCubePK;
/*      */ import com.cedar.cp.dto.model.amm.AmmFinanceCubeRefImpl;
/*      */ import com.cedar.cp.dto.model.amm.AmmModelCK;
/*      */ import com.cedar.cp.dto.model.amm.AmmModelPK;
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
/*      */ public class AmmFinanceCubeDAO extends AbstractDAO
/*      */ {
/*   35 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.AMM_MODEL_ID,AMM_FINANCE_CUBE.FINANCE_CUBE_ID,AMM_FINANCE_CUBE.SRC_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.UPDATED_BY_USER_ID,AMM_FINANCE_CUBE.UPDATED_TIME,AMM_FINANCE_CUBE.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from AMM_FINANCE_CUBE where    AMM_FINANCE_CUBE_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into AMM_FINANCE_CUBE ( AMM_FINANCE_CUBE_ID,AMM_MODEL_ID,FINANCE_CUBE_ID,SRC_FINANCE_CUBE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update AMM_FINANCE_CUBE set AMM_MODEL_ID = ?,FINANCE_CUBE_ID = ?,SRC_FINANCE_CUBE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_FINANCE_CUBE_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from AMM_FINANCE_CUBE where    AMM_FINANCE_CUBE_ID = ? ";
/*  473 */   private static String[][] SQL_DELETE_CHILDREN = { { "AMM_DATA_TYPE", "delete from AMM_DATA_TYPE where     AMM_DATA_TYPE.AMM_FINANCE_CUBE_ID = ? " } };
/*      */ 
/*  482 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  486 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from AMM_FINANCE_CUBE where 1=1 and AMM_FINANCE_CUBE.AMM_MODEL_ID = ? order by  AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID";
/*      */   protected static final String SQL_GET_ALL = " from AMM_FINANCE_CUBE where    AMM_MODEL_ID = ? ";
/*  961 */   private static String QUERY_IS_MAPPED_FINANCE_CUBE = "select count(*) as nmfc from amm_finance_cube mfc where mfc.finance_cube_id = ? or    mfc.src_finance_cube_id = ?";
/*      */   private static final String QUERY_MAPPED_DATA_TYPES_SQL = "with params as\n(\n  select ? as finance_cube_id \n  from dual\n)\nselect dt.data_type_id, dt.vis_id, dt.description, dt.sub_type, dt.measure_class \nfrom data_type dt \nwhere dt.data_type_id in \n(\n  select data_type_id \n  from  params,\n        amm_data_type mdt\n  join  amm_finance_cube mfc using (amm_finance_cube_id)\n  where mfc.finance_cube_id     = params.finance_cube_id\n  or    mfc.src_finance_cube_id = params.finance_cube_id\n)";
/*      */   protected AmmDataTypeDAO mAmmDataTypeDAO;
/*      */   protected AmmFinanceCubeEVO mDetails;
/*      */ 
/*      */   public AmmFinanceCubeDAO(Connection connection)
/*      */   {
/*   42 */     super(connection);
/*      */   }
/*      */ 
/*      */   public AmmFinanceCubeDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public AmmFinanceCubeDAO(DataSource ds)
/*      */   {
/*   58 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected AmmFinanceCubePK getPK()
/*      */   {
/*   66 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(AmmFinanceCubeEVO details)
/*      */   {
/*   75 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private AmmFinanceCubeEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   93 */     int col = 1;
/*   94 */     AmmFinanceCubeEVO evo = new AmmFinanceCubeEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  102 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  103 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  104 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  105 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(AmmFinanceCubeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  110 */     int col = startCol_;
/*  111 */     stmt_.setInt(col++, evo_.getAmmFinanceCubeId());
/*  112 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(AmmFinanceCubeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  117 */     int col = startCol_;
/*  118 */     stmt_.setInt(col++, evo_.getAmmModelId());
/*  119 */     stmt_.setInt(col++, evo_.getFinanceCubeId());
/*  120 */     stmt_.setInt(col++, evo_.getSrcFinanceCubeId());
/*  121 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  122 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  123 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  124 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(AmmFinanceCubePK pk)
/*      */     throws ValidationException
/*      */   {
/*  140 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  142 */     PreparedStatement stmt = null;
/*  143 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  147 */       stmt = getConnection().prepareStatement("select AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.AMM_MODEL_ID,AMM_FINANCE_CUBE.FINANCE_CUBE_ID,AMM_FINANCE_CUBE.SRC_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.UPDATED_BY_USER_ID,AMM_FINANCE_CUBE.UPDATED_TIME,AMM_FINANCE_CUBE.CREATED_TIME from AMM_FINANCE_CUBE where    AMM_FINANCE_CUBE_ID = ? ");
/*      */ 
/*  150 */       int col = 1;
/*  151 */       stmt.setInt(col++, pk.getAmmFinanceCubeId());
/*      */ 
/*  153 */       resultSet = stmt.executeQuery();
/*      */ 
/*  155 */       if (!resultSet.next()) {
/*  156 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  159 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  160 */       if (this.mDetails.isModified())
/*  161 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  165 */       throw handleSQLException(pk, "select AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.AMM_MODEL_ID,AMM_FINANCE_CUBE.FINANCE_CUBE_ID,AMM_FINANCE_CUBE.SRC_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.UPDATED_BY_USER_ID,AMM_FINANCE_CUBE.UPDATED_TIME,AMM_FINANCE_CUBE.CREATED_TIME from AMM_FINANCE_CUBE where    AMM_FINANCE_CUBE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  169 */       closeResultSet(resultSet);
/*  170 */       closeStatement(stmt);
/*  171 */       closeConnection();
/*      */ 
/*  173 */       if (timer != null)
/*  174 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  205 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  206 */     this.mDetails.postCreateInit();
/*      */ 
/*  208 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  213 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  214 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  215 */       stmt = getConnection().prepareStatement("insert into AMM_FINANCE_CUBE ( AMM_FINANCE_CUBE_ID,AMM_MODEL_ID,FINANCE_CUBE_ID,SRC_FINANCE_CUBE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*      */ 
/*  218 */       int col = 1;
/*  219 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  220 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  223 */       int resultCount = stmt.executeUpdate();
/*  224 */       if (resultCount != 1)
/*      */       {
/*  226 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  229 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  233 */       throw handleSQLException(this.mDetails.getPK(), "insert into AMM_FINANCE_CUBE ( AMM_FINANCE_CUBE_ID,AMM_MODEL_ID,FINANCE_CUBE_ID,SRC_FINANCE_CUBE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  237 */       closeStatement(stmt);
/*  238 */       closeConnection();
/*      */ 
/*  240 */       if (timer != null) {
/*  241 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  247 */       getAmmDataTypeDAO().update(this.mDetails.getAmmDataTypesMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  253 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  278 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  282 */     PreparedStatement stmt = null;
/*      */ 
/*  284 */     boolean mainChanged = this.mDetails.isModified();
/*  285 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  289 */       if (getAmmDataTypeDAO().update(this.mDetails.getAmmDataTypesMap())) {
/*  290 */         dependantChanged = true;
/*      */       }
/*  292 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  295 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  296 */         stmt = getConnection().prepareStatement("update AMM_FINANCE_CUBE set AMM_MODEL_ID = ?,FINANCE_CUBE_ID = ?,SRC_FINANCE_CUBE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_FINANCE_CUBE_ID = ? ");
/*      */ 
/*  299 */         int col = 1;
/*  300 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  301 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  304 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  307 */         if (resultCount != 1) {
/*  308 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  311 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  320 */       throw handleSQLException(getPK(), "update AMM_FINANCE_CUBE set AMM_MODEL_ID = ?,FINANCE_CUBE_ID = ?,SRC_FINANCE_CUBE_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_FINANCE_CUBE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  324 */       closeStatement(stmt);
/*  325 */       closeConnection();
/*      */ 
/*  327 */       if ((timer != null) && (
/*  328 */         (mainChanged) || (dependantChanged)))
/*  329 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  348 */     if (items == null) {
/*  349 */       return false;
/*      */     }
/*  351 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  352 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  354 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  358 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  359 */       while (iter3.hasNext())
/*      */       {
/*  361 */         this.mDetails = ((AmmFinanceCubeEVO)iter3.next());
/*  362 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  364 */         somethingChanged = true;
/*      */ 
/*  367 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  371 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  372 */       while (iter2.hasNext())
/*      */       {
/*  374 */         this.mDetails = ((AmmFinanceCubeEVO)iter2.next());
/*      */ 
/*  377 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  379 */         somethingChanged = true;
/*      */ 
/*  382 */         if (deleteStmt == null) {
/*  383 */           deleteStmt = getConnection().prepareStatement("delete from AMM_FINANCE_CUBE where    AMM_FINANCE_CUBE_ID = ? ");
/*      */         }
/*      */ 
/*  386 */         int col = 1;
/*  387 */         deleteStmt.setInt(col++, this.mDetails.getAmmFinanceCubeId());
/*      */ 
/*  389 */         if (this._log.isDebugEnabled()) {
/*  390 */           this._log.debug("update", "AmmFinanceCube deleting AmmFinanceCubeId=" + this.mDetails.getAmmFinanceCubeId());
/*      */         }
/*      */ 
/*  395 */         deleteStmt.addBatch();
/*      */ 
/*  398 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  403 */       if (deleteStmt != null)
/*      */       {
/*  405 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  407 */         deleteStmt.executeBatch();
/*      */ 
/*  409 */         if (timer2 != null) {
/*  410 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  414 */       Iterator iter1 = items.values().iterator();
/*  415 */       while (iter1.hasNext())
/*      */       {
/*  417 */         this.mDetails = ((AmmFinanceCubeEVO)iter1.next());
/*      */ 
/*  419 */         if (this.mDetails.insertPending())
/*      */         {
/*  421 */           somethingChanged = true;
/*  422 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  425 */         if (this.mDetails.isModified())
/*      */         {
/*  427 */           somethingChanged = true;
/*  428 */           doStore(); continue;
/*      */         }
/*      */ 
/*  432 */         if ((this.mDetails.deletePending()) || 
/*  438 */           (!getAmmDataTypeDAO().update(this.mDetails.getAmmDataTypesMap()))) continue;
/*  439 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  451 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  455 */       throw handleSQLException("delete from AMM_FINANCE_CUBE where    AMM_FINANCE_CUBE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  459 */       if (deleteStmt != null)
/*      */       {
/*  461 */         closeStatement(deleteStmt);
/*  462 */         closeConnection();
/*      */       }
/*      */ 
/*  465 */       this.mDetails = null;
/*      */ 
/*  467 */       if ((somethingChanged) && 
/*  468 */         (timer != null))
/*  469 */         timer.logDebug("update", "collection"); 
/*  469 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(AmmFinanceCubePK pk)
/*      */   {
/*  495 */     Set emptyStrings = Collections.emptySet();
/*  496 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(AmmFinanceCubePK pk, Set<String> exclusionTables)
/*      */   {
/*  502 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  504 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  506 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  508 */       PreparedStatement stmt = null;
/*      */ 
/*  510 */       int resultCount = 0;
/*  511 */       String s = null;
/*      */       try
/*      */       {
/*  514 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  516 */         if (this._log.isDebugEnabled()) {
/*  517 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  519 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  522 */         int col = 1;
/*  523 */         stmt.setInt(col++, pk.getAmmFinanceCubeId());
/*      */ 
/*  526 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  530 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  534 */         closeStatement(stmt);
/*  535 */         closeConnection();
/*      */ 
/*  537 */         if (timer != null) {
/*  538 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  542 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  544 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  546 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  548 */       PreparedStatement stmt = null;
/*      */ 
/*  550 */       int resultCount = 0;
/*  551 */       String s = null;
/*      */       try
/*      */       {
/*  554 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  556 */         if (this._log.isDebugEnabled()) {
/*  557 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  559 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  562 */         int col = 1;
/*  563 */         stmt.setInt(col++, pk.getAmmFinanceCubeId());
/*      */ 
/*  566 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  570 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  574 */         closeStatement(stmt);
/*  575 */         closeConnection();
/*      */ 
/*  577 */         if (timer != null)
/*  578 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(AmmModelPK entityPK, AmmModelEVO owningEVO, String dependants)
/*      */   {
/*  598 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  600 */     PreparedStatement stmt = null;
/*  601 */     ResultSet resultSet = null;
/*      */ 
/*  603 */     int itemCount = 0;
/*      */ 
/*  605 */     Collection theseItems = new ArrayList();
/*  606 */     owningEVO.setAmmFinanceCubes(theseItems);
/*  607 */     owningEVO.setAmmFinanceCubesAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  611 */       stmt = getConnection().prepareStatement("select AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.AMM_MODEL_ID,AMM_FINANCE_CUBE.FINANCE_CUBE_ID,AMM_FINANCE_CUBE.SRC_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.UPDATED_BY_USER_ID,AMM_FINANCE_CUBE.UPDATED_TIME,AMM_FINANCE_CUBE.CREATED_TIME from AMM_FINANCE_CUBE where 1=1 and AMM_FINANCE_CUBE.AMM_MODEL_ID = ? order by  AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID");
/*      */ 
/*  613 */       int col = 1;
/*  614 */       stmt.setInt(col++, entityPK.getAmmModelId());
/*      */ 
/*  616 */       resultSet = stmt.executeQuery();
/*      */ 
/*  619 */       while (resultSet.next())
/*      */       {
/*  621 */         itemCount++;
/*  622 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  624 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  627 */       if (timer != null) {
/*  628 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  631 */       if ((itemCount > 0) && (dependants.indexOf("<4>") > -1))
/*      */       {
/*  633 */         getAmmDataTypeDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  637 */       throw handleSQLException("select AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.AMM_MODEL_ID,AMM_FINANCE_CUBE.FINANCE_CUBE_ID,AMM_FINANCE_CUBE.SRC_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.UPDATED_BY_USER_ID,AMM_FINANCE_CUBE.UPDATED_TIME,AMM_FINANCE_CUBE.CREATED_TIME from AMM_FINANCE_CUBE where 1=1 and AMM_FINANCE_CUBE.AMM_MODEL_ID = ? order by  AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  641 */       closeResultSet(resultSet);
/*  642 */       closeStatement(stmt);
/*  643 */       closeConnection();
/*      */ 
/*  645 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectAmmModelId, String dependants, Collection currentList)
/*      */   {
/*  670 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  671 */     PreparedStatement stmt = null;
/*  672 */     ResultSet resultSet = null;
/*      */ 
/*  674 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  678 */       stmt = getConnection().prepareStatement("select AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.AMM_MODEL_ID,AMM_FINANCE_CUBE.FINANCE_CUBE_ID,AMM_FINANCE_CUBE.SRC_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.UPDATED_BY_USER_ID,AMM_FINANCE_CUBE.UPDATED_TIME,AMM_FINANCE_CUBE.CREATED_TIME from AMM_FINANCE_CUBE where    AMM_MODEL_ID = ? ");
/*      */ 
/*  680 */       int col = 1;
/*  681 */       stmt.setInt(col++, selectAmmModelId);
/*      */ 
/*  683 */       resultSet = stmt.executeQuery();
/*      */ 
/*  685 */       while (resultSet.next())
/*      */       {
/*  687 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  690 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  693 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  696 */       if (currentList != null)
/*      */       {
/*  699 */         ListIterator iter = items.listIterator();
/*  700 */         AmmFinanceCubeEVO currentEVO = null;
/*  701 */         AmmFinanceCubeEVO newEVO = null;
/*  702 */         while (iter.hasNext())
/*      */         {
/*  704 */           newEVO = (AmmFinanceCubeEVO)iter.next();
/*  705 */           Iterator iter2 = currentList.iterator();
/*  706 */           while (iter2.hasNext())
/*      */           {
/*  708 */             currentEVO = (AmmFinanceCubeEVO)iter2.next();
/*  709 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  711 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  717 */         Iterator iter2 = currentList.iterator();
/*  718 */         while (iter2.hasNext())
/*      */         {
/*  720 */           currentEVO = (AmmFinanceCubeEVO)iter2.next();
/*  721 */           if (currentEVO.insertPending()) {
/*  722 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  726 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  730 */       throw handleSQLException("select AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.AMM_MODEL_ID,AMM_FINANCE_CUBE.FINANCE_CUBE_ID,AMM_FINANCE_CUBE.SRC_FINANCE_CUBE_ID,AMM_FINANCE_CUBE.UPDATED_BY_USER_ID,AMM_FINANCE_CUBE.UPDATED_TIME,AMM_FINANCE_CUBE.CREATED_TIME from AMM_FINANCE_CUBE where    AMM_MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  734 */       closeResultSet(resultSet);
/*  735 */       closeStatement(stmt);
/*  736 */       closeConnection();
/*      */ 
/*  738 */       if (timer != null) {
/*  739 */         timer.logDebug("getAll", " AmmModelId=" + selectAmmModelId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  744 */     return items;
/*      */   }
/*      */ 
/*      */   public AmmFinanceCubeEVO getDetails(AmmModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  761 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  764 */     if (this.mDetails == null) {
/*  765 */       doLoad(((AmmFinanceCubeCK)paramCK).getAmmFinanceCubePK());
/*      */     }
/*  767 */     else if (!this.mDetails.getPK().equals(((AmmFinanceCubeCK)paramCK).getAmmFinanceCubePK())) {
/*  768 */       doLoad(((AmmFinanceCubeCK)paramCK).getAmmFinanceCubePK());
/*      */     }
/*      */ 
/*  771 */     if ((dependants.indexOf("<4>") > -1) && (!this.mDetails.isAmmDataTypesAllItemsLoaded()))
/*      */     {
/*  776 */       this.mDetails.setAmmDataTypes(getAmmDataTypeDAO().getAll(this.mDetails.getAmmFinanceCubeId(), dependants, this.mDetails.getAmmDataTypes()));
/*      */ 
/*  783 */       this.mDetails.setAmmDataTypesAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  786 */     if ((paramCK instanceof AmmDataTypeCK))
/*      */     {
/*  788 */       if (this.mDetails.getAmmDataTypes() == null) {
/*  789 */         this.mDetails.loadAmmDataTypesItem(getAmmDataTypeDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  792 */         AmmDataTypePK pk = ((AmmDataTypeCK)paramCK).getAmmDataTypePK();
/*  793 */         AmmDataTypeEVO evo = this.mDetails.getAmmDataTypesItem(pk);
/*  794 */         if (evo == null) {
/*  795 */           this.mDetails.loadAmmDataTypesItem(getAmmDataTypeDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  800 */     AmmFinanceCubeEVO details = new AmmFinanceCubeEVO();
/*  801 */     details = this.mDetails.deepClone();
/*      */ 
/*  803 */     if (timer != null) {
/*  804 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  806 */     return details;
/*      */   }
/*      */ 
/*      */   public AmmFinanceCubeEVO getDetails(AmmModelCK paramCK, AmmFinanceCubeEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  812 */     AmmFinanceCubeEVO savedEVO = this.mDetails;
/*  813 */     this.mDetails = paramEVO;
/*  814 */     AmmFinanceCubeEVO newEVO = getDetails(paramCK, dependants);
/*  815 */     this.mDetails = savedEVO;
/*  816 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public AmmFinanceCubeEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  822 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  826 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  829 */     AmmFinanceCubeEVO details = this.mDetails.deepClone();
/*      */ 
/*  831 */     if (timer != null) {
/*  832 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  834 */     return details;
/*      */   }
/*      */ 
/*      */   protected AmmDataTypeDAO getAmmDataTypeDAO()
/*      */   {
/*  843 */     if (this.mAmmDataTypeDAO == null)
/*      */     {
/*  845 */       if (this.mDataSource != null)
/*  846 */         this.mAmmDataTypeDAO = new AmmDataTypeDAO(this.mDataSource);
/*      */       else {
/*  848 */         this.mAmmDataTypeDAO = new AmmDataTypeDAO(getConnection());
/*      */       }
/*      */     }
/*  851 */     return this.mAmmDataTypeDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  856 */     return "AmmFinanceCube";
/*      */   }
/*      */ 
/*      */   public AmmFinanceCubeRefImpl getRef(AmmFinanceCubePK paramAmmFinanceCubePK)
/*      */   {
/*  861 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  862 */     PreparedStatement stmt = null;
/*  863 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  866 */       stmt = getConnection().prepareStatement("select 0,AMM_MODEL.AMM_MODEL_ID from AMM_FINANCE_CUBE,AMM_MODEL where 1=1 and AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID = ? and AMM_FINANCE_CUBE.AMM_MODEL_ID = AMM_MODEL.AMM_MODEL_ID");
/*  867 */       int col = 1;
/*  868 */       stmt.setInt(col++, paramAmmFinanceCubePK.getAmmFinanceCubeId());
/*      */ 
/*  870 */       resultSet = stmt.executeQuery();
/*      */ 
/*  872 */       if (!resultSet.next()) {
/*  873 */         throw new RuntimeException(getEntityName() + " getRef " + paramAmmFinanceCubePK + " not found");
/*      */       }
/*  875 */       col = 2;
/*  876 */       AmmModelPK newAmmModelPK = new AmmModelPK(resultSet.getInt(col++));
/*      */ 
/*  880 */       String textAmmFinanceCube = "";
/*  881 */       AmmFinanceCubeCK ckAmmFinanceCube = new AmmFinanceCubeCK(newAmmModelPK, paramAmmFinanceCubePK);
/*      */ 
/*  886 */       AmmFinanceCubeRefImpl localAmmFinanceCubeRefImpl = new AmmFinanceCubeRefImpl(ckAmmFinanceCube, textAmmFinanceCube);
/*      */       return localAmmFinanceCubeRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  891 */       throw handleSQLException(paramAmmFinanceCubePK, "select 0,AMM_MODEL.AMM_MODEL_ID from AMM_FINANCE_CUBE,AMM_MODEL where 1=1 and AMM_FINANCE_CUBE.AMM_FINANCE_CUBE_ID = ? and AMM_FINANCE_CUBE.AMM_MODEL_ID = AMM_MODEL.AMM_MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  895 */       closeResultSet(resultSet);
/*  896 */       closeStatement(stmt);
/*  897 */       closeConnection();
/*      */ 
/*  899 */       if (timer != null)
/*  900 */         timer.logDebug("getRef", paramAmmFinanceCubePK); 
/*  900 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/*  912 */     if (c == null)
/*  913 */       return;
/*  914 */     Iterator iter = c.iterator();
/*  915 */     while (iter.hasNext())
/*      */     {
/*  917 */       AmmFinanceCubeEVO evo = (AmmFinanceCubeEVO)iter.next();
/*  918 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(AmmFinanceCubeEVO evo, String dependants)
/*      */   {
/*  932 */     if (evo.insertPending()) {
/*  933 */       return;
/*      */     }
/*  935 */     if (evo.getAmmFinanceCubeId() < 1) {
/*  936 */       return;
/*      */     }
/*      */ 
/*  940 */     if (dependants.indexOf("<4>") > -1)
/*      */     {
/*  943 */       if (!evo.isAmmDataTypesAllItemsLoaded())
/*      */       {
/*  945 */         evo.setAmmDataTypes(getAmmDataTypeDAO().getAll(evo.getAmmFinanceCubeId(), dependants, evo.getAmmDataTypes()));
/*      */ 
/*  952 */         evo.setAmmDataTypesAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isFinanceCubeMapped(int financeCubeId)
/*      */   {
/*  973 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  974 */     PreparedStatement stmt = null;
/*  975 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  978 */       stmt = getConnection().prepareStatement(QUERY_IS_MAPPED_FINANCE_CUBE);
/*      */ 
/*  980 */       stmt.setInt(1, financeCubeId);
/*  981 */       stmt.setInt(2, financeCubeId);
/*      */ 
/*  983 */       resultSet = stmt.executeQuery();
/*      */ 
/*  985 */       boolean i = (resultSet.next()) && (resultSet.getInt("nmfc") > 0) ? true : false;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  989 */       throw handleSQLException(QUERY_IS_MAPPED_FINANCE_CUBE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  993 */       closeResultSet(resultSet);
/*  994 */       closeStatement(stmt);
/*  995 */       closeConnection();
/*      */ 
/*  997 */       if (timer != null)
/*  998 */         timer.logDebug("isFinanceCubeMapped", Integer.valueOf(financeCubeId)); 
/*  998 */     }
/*      */   }
/*      */ 
/*      */   public Set<DataTypeRef> queryMappedDataTypes(int financeCubeId)
/*      */   {
/* 1027 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1028 */     PreparedStatement stmt = null;
/* 1029 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1032 */       stmt = getConnection().prepareStatement("with params as\n(\n  select ? as finance_cube_id \n  from dual\n)\nselect dt.data_type_id, dt.vis_id, dt.description, dt.sub_type, dt.measure_class \nfrom data_type dt \nwhere dt.data_type_id in \n(\n  select data_type_id \n  from  params,\n        amm_data_type mdt\n  join  amm_finance_cube mfc using (amm_finance_cube_id)\n  where mfc.finance_cube_id     = params.finance_cube_id\n  or    mfc.src_finance_cube_id = params.finance_cube_id\n)");
/*      */ 
/* 1034 */       int col = 1;
/* 1035 */       stmt.setInt(col, financeCubeId);
/*      */ 
/* 1037 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1039 */       Set dataTypes = new TreeSet();
/*      */ 
/* 1041 */       while (resultSet.next())
/*      */       {
/* 1043 */         short dataTypeId = resultSet.getShort("data_type_id");
/* 1044 */         String vis_id = resultSet.getString("vis_id");
/* 1045 */         String description = resultSet.getString("description");
/* 1046 */         int subType = resultSet.getInt("sub_type");
/* 1047 */         int measureClass = resultSet.getInt("measure_class");
/* 1048 */         boolean measureClassNull = resultSet.wasNull();
/*      */ 
/* 1050 */         DataTypeRefImpl dataType = new DataTypeRefImpl(new DataTypePK(dataTypeId), vis_id, description, subType, measureClassNull ? null : Integer.valueOf(measureClass), null);
/*      */ 
/* 1054 */         dataTypes.add(dataType);
/*      */       }
/*      */ 
/*      */       return dataTypes;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1061 */       throw handleSQLException("with params as\n(\n  select ? as finance_cube_id \n  from dual\n)\nselect dt.data_type_id, dt.vis_id, dt.description, dt.sub_type, dt.measure_class \nfrom data_type dt \nwhere dt.data_type_id in \n(\n  select data_type_id \n  from  params,\n        amm_data_type mdt\n  join  amm_finance_cube mfc using (amm_finance_cube_id)\n  where mfc.finance_cube_id     = params.finance_cube_id\n  or    mfc.src_finance_cube_id = params.finance_cube_id\n)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1065 */       closeResultSet(resultSet);
/* 1066 */       closeStatement(stmt);
/* 1067 */       closeConnection();
/*      */ 
/* 1069 */       if (timer != null)
/* 1070 */         timer.logDebug("queryMappedDataTypes", Integer.valueOf(financeCubeId)); 
/* 1070 */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.amm.AmmFinanceCubeDAO
 * JD-Core Version:    0.6.0
 */