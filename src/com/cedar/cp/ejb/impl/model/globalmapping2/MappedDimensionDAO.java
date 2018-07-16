/*      */ package com.cedar.cp.ejb.impl.model.globalmapping2;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.globalmapping2.MappedDimensionCK;
/*      */ import com.cedar.cp.dto.model.globalmapping2.MappedDimensionElementCK;
/*      */ import com.cedar.cp.dto.model.globalmapping2.MappedDimensionElementPK;
/*      */ import com.cedar.cp.dto.model.globalmapping2.MappedDimensionPK;
/*      */ import com.cedar.cp.dto.model.globalmapping2.MappedDimensionRefImpl;
/*      */ import com.cedar.cp.dto.model.globalmapping2.MappedHierarchyCK;
/*      */ import com.cedar.cp.dto.model.globalmapping2.MappedHierarchyPK;
/*      */ import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2CK;
/*      */ import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
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
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class MappedDimensionDAO extends AbstractDAO
/*      */ {
/*   32 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select MAPPED_DIMENSION.MAPPED_DIMENSION_ID,MAPPED_DIMENSION.MAPPED_MODEL_ID,MAPPED_DIMENSION.DIMENSION_ID,MAPPED_DIMENSION.PATH_VIS_ID,MAPPED_DIMENSION.EXCLUDE_DISABLED_LEAF_NODES,MAPPED_DIMENSION.UPDATED_BY_USER_ID,MAPPED_DIMENSION.UPDATED_TIME,MAPPED_DIMENSION.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from MAPPED_DIMENSION where    MAPPED_DIMENSION_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into MAPPED_DIMENSION ( MAPPED_DIMENSION_ID,MAPPED_MODEL_ID,DIMENSION_ID,PATH_VIS_ID,EXCLUDE_DISABLED_LEAF_NODES,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update MAPPED_DIMENSION set MAPPED_MODEL_ID = ?,DIMENSION_ID = ?,PATH_VIS_ID = ?,EXCLUDE_DISABLED_LEAF_NODES = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_DIMENSION_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from MAPPED_DIMENSION where    MAPPED_DIMENSION_ID = ? ";
/*  490 */   private static String[][] SQL_DELETE_CHILDREN = { { "MAPPED_DIMENSION_ELEMENT", "delete from MAPPED_DIMENSION_ELEMENT where     MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID = ? " }, { "MAPPED_HIERARCHY", "delete from MAPPED_HIERARCHY where     MAPPED_HIERARCHY.MAPPED_DIMENSION_ID = ? " } };
/*      */ 
/*  504 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  508 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and MAPPED_DIMENSION.MAPPED_DIMENSION_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from MAPPED_DIMENSION where 1=1 and MAPPED_DIMENSION.MAPPED_MODEL_ID = ? order by  MAPPED_DIMENSION.MAPPED_DIMENSION_ID";
/*      */   protected static final String SQL_GET_ALL = " from MAPPED_DIMENSION where    MAPPED_MODEL_ID = ? ";
/*      */   protected MappedDimensionElementDAO mMappedDimensionElementDAO;
/*      */   protected MappedHierarchyDAO mMappedHierarchyDAO;
/*      */   protected MappedDimensionEVO mDetails;
/*      */ 
/*      */   public MappedDimensionDAO(Connection connection)
/*      */   {
/*   39 */     super(connection);
/*      */   }
/*      */ 
/*      */   public MappedDimensionDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public MappedDimensionDAO(DataSource ds)
/*      */   {
/*   55 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected MappedDimensionPK getPK()
/*      */   {
/*   63 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(MappedDimensionEVO details)
/*      */   {
/*   72 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private MappedDimensionEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   91 */     int col = 1;
/*   92 */     MappedDimensionEVO evo = new MappedDimensionEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++).equals("Y"), null, null);
/*      */ 
/*  102 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  103 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  104 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  105 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(MappedDimensionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  110 */     int col = startCol_;
/*  111 */     stmt_.setInt(col++, evo_.getMappedDimensionId());
/*  112 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(MappedDimensionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  117 */     int col = startCol_;
/*  118 */     stmt_.setInt(col++, evo_.getMappedModelId());
/*  119 */     stmt_.setInt(col++, evo_.getDimensionId());
/*  120 */     stmt_.setString(col++, evo_.getPathVisId());
/*  121 */     if (evo_.getExcludeDisabledLeafNodes())
/*  122 */       stmt_.setString(col++, "Y");
/*      */     else
/*  124 */       stmt_.setString(col++, " ");
/*  125 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  126 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  127 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  128 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(MappedDimensionPK pk)
/*      */     throws ValidationException
/*      */   {
/*  144 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  146 */     PreparedStatement stmt = null;
/*  147 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  151 */       stmt = getConnection().prepareStatement("select MAPPED_DIMENSION.MAPPED_DIMENSION_ID,MAPPED_DIMENSION.MAPPED_MODEL_ID,MAPPED_DIMENSION.DIMENSION_ID,MAPPED_DIMENSION.PATH_VIS_ID,MAPPED_DIMENSION.EXCLUDE_DISABLED_LEAF_NODES,MAPPED_DIMENSION.UPDATED_BY_USER_ID,MAPPED_DIMENSION.UPDATED_TIME,MAPPED_DIMENSION.CREATED_TIME from MAPPED_DIMENSION where    MAPPED_DIMENSION_ID = ? ");
/*      */ 
/*  154 */       int col = 1;
/*  155 */       stmt.setInt(col++, pk.getMappedDimensionId());
/*      */ 
/*  157 */       resultSet = stmt.executeQuery();
/*      */ 
/*  159 */       if (!resultSet.next()) {
/*  160 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  163 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  164 */       if (this.mDetails.isModified())
/*  165 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  169 */       throw handleSQLException(pk, "select MAPPED_DIMENSION.MAPPED_DIMENSION_ID,MAPPED_DIMENSION.MAPPED_MODEL_ID,MAPPED_DIMENSION.DIMENSION_ID,MAPPED_DIMENSION.PATH_VIS_ID,MAPPED_DIMENSION.EXCLUDE_DISABLED_LEAF_NODES,MAPPED_DIMENSION.UPDATED_BY_USER_ID,MAPPED_DIMENSION.UPDATED_TIME,MAPPED_DIMENSION.CREATED_TIME from MAPPED_DIMENSION where    MAPPED_DIMENSION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  173 */       closeResultSet(resultSet);
/*  174 */       closeStatement(stmt);
/*  175 */       closeConnection();
/*      */ 
/*  177 */       if (timer != null)
/*  178 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  211 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  212 */     this.mDetails.postCreateInit();
/*      */ 
/*  214 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  219 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  220 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  221 */       stmt = getConnection().prepareStatement("insert into MAPPED_DIMENSION ( MAPPED_DIMENSION_ID,MAPPED_MODEL_ID,DIMENSION_ID,PATH_VIS_ID,EXCLUDE_DISABLED_LEAF_NODES,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");
/*      */ 
/*  224 */       int col = 1;
/*  225 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  226 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  229 */       int resultCount = stmt.executeUpdate();
/*  230 */       if (resultCount != 1)
/*      */       {
/*  232 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  235 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  239 */       throw handleSQLException(this.mDetails.getPK(), "insert into MAPPED_DIMENSION ( MAPPED_DIMENSION_ID,MAPPED_MODEL_ID,DIMENSION_ID,PATH_VIS_ID,EXCLUDE_DISABLED_LEAF_NODES,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  243 */       closeStatement(stmt);
/*  244 */       closeConnection();
/*      */ 
/*  246 */       if (timer != null) {
/*  247 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  253 */       getMappedDimensionElementDAO().update(this.mDetails.getMappedDimensionElementsMap());
/*      */ 
/*  255 */       getMappedHierarchyDAO().update(this.mDetails.getMappedHierarchysMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  261 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  287 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  291 */     PreparedStatement stmt = null;
/*      */ 
/*  293 */     boolean mainChanged = this.mDetails.isModified();
/*  294 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  298 */       if (getMappedDimensionElementDAO().update(this.mDetails.getMappedDimensionElementsMap())) {
/*  299 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  302 */       if (getMappedHierarchyDAO().update(this.mDetails.getMappedHierarchysMap())) {
/*  303 */         dependantChanged = true;
/*      */       }
/*  305 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  308 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  309 */         stmt = getConnection().prepareStatement("update MAPPED_DIMENSION set MAPPED_MODEL_ID = ?,DIMENSION_ID = ?,PATH_VIS_ID = ?,EXCLUDE_DISABLED_LEAF_NODES = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_DIMENSION_ID = ? ");
/*      */ 
/*  312 */         int col = 1;
/*  313 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  314 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  317 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  320 */         if (resultCount != 1) {
/*  321 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  324 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  333 */       throw handleSQLException(getPK(), "update MAPPED_DIMENSION set MAPPED_MODEL_ID = ?,DIMENSION_ID = ?,PATH_VIS_ID = ?,EXCLUDE_DISABLED_LEAF_NODES = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_DIMENSION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  337 */       closeStatement(stmt);
/*  338 */       closeConnection();
/*      */ 
/*  340 */       if ((timer != null) && (
/*  341 */         (mainChanged) || (dependantChanged)))
/*  342 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  361 */     if (items == null) {
/*  362 */       return false;
/*      */     }
/*  364 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  365 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  367 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  371 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  372 */       while (iter3.hasNext())
/*      */       {
/*  374 */         this.mDetails = ((MappedDimensionEVO)iter3.next());
/*  375 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  377 */         somethingChanged = true;
/*      */ 
/*  380 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  384 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  385 */       while (iter2.hasNext())
/*      */       {
/*  387 */         this.mDetails = ((MappedDimensionEVO)iter2.next());
/*      */ 
/*  390 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  392 */         somethingChanged = true;
/*      */ 
/*  395 */         if (deleteStmt == null) {
/*  396 */           deleteStmt = getConnection().prepareStatement("delete from MAPPED_DIMENSION where    MAPPED_DIMENSION_ID = ? ");
/*      */         }
/*      */ 
/*  399 */         int col = 1;
/*  400 */         deleteStmt.setInt(col++, this.mDetails.getMappedDimensionId());
/*      */ 
/*  402 */         if (this._log.isDebugEnabled()) {
/*  403 */           this._log.debug("update", "MappedDimension deleting MappedDimensionId=" + this.mDetails.getMappedDimensionId());
/*      */         }
/*      */ 
/*  408 */         deleteStmt.addBatch();
/*      */ 
/*  411 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  416 */       if (deleteStmt != null)
/*      */       {
/*  418 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  420 */         deleteStmt.executeBatch();
/*      */ 
/*  422 */         if (timer2 != null) {
/*  423 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  427 */       Iterator iter1 = items.values().iterator();
/*  428 */       while (iter1.hasNext())
/*      */       {
/*  430 */         this.mDetails = ((MappedDimensionEVO)iter1.next());
/*      */ 
/*  432 */         if (this.mDetails.insertPending())
/*      */         {
/*  434 */           somethingChanged = true;
/*  435 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  438 */         if (this.mDetails.isModified())
/*      */         {
/*  440 */           somethingChanged = true;
/*  441 */           doStore(); continue;
/*      */         }
/*      */ 
/*  445 */         if (this.mDetails.deletePending())
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  451 */         if (getMappedDimensionElementDAO().update(this.mDetails.getMappedDimensionElementsMap())) {
/*  452 */           somethingChanged = true;
/*      */         }
/*      */ 
/*  455 */         if (getMappedHierarchyDAO().update(this.mDetails.getMappedHierarchysMap())) {
/*  456 */           somethingChanged = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  468 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  472 */       throw handleSQLException("delete from MAPPED_DIMENSION where    MAPPED_DIMENSION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  476 */       if (deleteStmt != null)
/*      */       {
/*  478 */         closeStatement(deleteStmt);
/*  479 */         closeConnection();
/*      */       }
/*      */ 
/*  482 */       this.mDetails = null;
/*      */ 
/*  484 */       if ((somethingChanged) && 
/*  485 */         (timer != null))
/*  486 */         timer.logDebug("update", "collection"); 
/*  486 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(MappedDimensionPK pk)
/*      */   {
/*  517 */     Set emptyStrings = Collections.emptySet();
/*  518 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(MappedDimensionPK pk, Set<String> exclusionTables)
/*      */   {
/*  524 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  526 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  528 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  530 */       PreparedStatement stmt = null;
/*      */ 
/*  532 */       int resultCount = 0;
/*  533 */       String s = null;
/*      */       try
/*      */       {
/*  536 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  538 */         if (this._log.isDebugEnabled()) {
/*  539 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  541 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  544 */         int col = 1;
/*  545 */         stmt.setInt(col++, pk.getMappedDimensionId());
/*      */ 
/*  548 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  552 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  556 */         closeStatement(stmt);
/*  557 */         closeConnection();
/*      */ 
/*  559 */         if (timer != null) {
/*  560 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  564 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  566 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  568 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  570 */       PreparedStatement stmt = null;
/*      */ 
/*  572 */       int resultCount = 0;
/*  573 */       String s = null;
/*      */       try
/*      */       {
/*  576 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  578 */         if (this._log.isDebugEnabled()) {
/*  579 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  581 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  584 */         int col = 1;
/*  585 */         stmt.setInt(col++, pk.getMappedDimensionId());
/*      */ 
/*  588 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  592 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  596 */         closeStatement(stmt);
/*  597 */         closeConnection();
/*      */ 
/*  599 */         if (timer != null)
/*  600 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(GlobalMappedModel2PK entityPK, GlobalMappedModel2EVO owningEVO, String dependants)
/*      */   {
/*  620 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  622 */     PreparedStatement stmt = null;
/*  623 */     ResultSet resultSet = null;
/*      */ 
/*  625 */     int itemCount = 0;
/*      */ 
/*  627 */     Collection theseItems = new ArrayList();
/*  628 */     owningEVO.setMappedDimensions(theseItems);
/*  629 */     owningEVO.setMappedDimensionsAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  633 */       stmt = getConnection().prepareStatement("select MAPPED_DIMENSION.MAPPED_DIMENSION_ID,MAPPED_DIMENSION.MAPPED_MODEL_ID,MAPPED_DIMENSION.DIMENSION_ID,MAPPED_DIMENSION.PATH_VIS_ID,MAPPED_DIMENSION.EXCLUDE_DISABLED_LEAF_NODES,MAPPED_DIMENSION.UPDATED_BY_USER_ID,MAPPED_DIMENSION.UPDATED_TIME,MAPPED_DIMENSION.CREATED_TIME from MAPPED_DIMENSION where 1=1 and MAPPED_DIMENSION.MAPPED_MODEL_ID = ? order by  MAPPED_DIMENSION.MAPPED_DIMENSION_ID");
/*      */ 
/*  635 */       int col = 1;
/*  636 */       stmt.setInt(col++, entityPK.getMappedModelId());
/*      */ 
/*  638 */       resultSet = stmt.executeQuery();
/*      */ 
/*  641 */       while (resultSet.next())
/*      */       {
/*  643 */         itemCount++;
/*  644 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  646 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  649 */       if (timer != null) {
/*  650 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  653 */       if ((itemCount > 0) && (dependants.indexOf("<5>") > -1))
/*      */       {
/*  655 */         getMappedDimensionElementDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*  657 */       if ((itemCount > 0) && (dependants.indexOf("<6>") > -1))
/*      */       {
/*  659 */         getMappedHierarchyDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  663 */       throw handleSQLException("select MAPPED_DIMENSION.MAPPED_DIMENSION_ID,MAPPED_DIMENSION.MAPPED_MODEL_ID,MAPPED_DIMENSION.DIMENSION_ID,MAPPED_DIMENSION.PATH_VIS_ID,MAPPED_DIMENSION.EXCLUDE_DISABLED_LEAF_NODES,MAPPED_DIMENSION.UPDATED_BY_USER_ID,MAPPED_DIMENSION.UPDATED_TIME,MAPPED_DIMENSION.CREATED_TIME from MAPPED_DIMENSION where 1=1 and MAPPED_DIMENSION.MAPPED_MODEL_ID = ? order by  MAPPED_DIMENSION.MAPPED_DIMENSION_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  667 */       closeResultSet(resultSet);
/*  668 */       closeStatement(stmt);
/*  669 */       closeConnection();
/*      */ 
/*  671 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectMappedModelId, String dependants, Collection currentList)
/*      */   {
/*  696 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  697 */     PreparedStatement stmt = null;
/*  698 */     ResultSet resultSet = null;
/*      */ 
/*  700 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  704 */       stmt = getConnection().prepareStatement("select MAPPED_DIMENSION.MAPPED_DIMENSION_ID,MAPPED_DIMENSION.MAPPED_MODEL_ID,MAPPED_DIMENSION.DIMENSION_ID,MAPPED_DIMENSION.PATH_VIS_ID,MAPPED_DIMENSION.EXCLUDE_DISABLED_LEAF_NODES,MAPPED_DIMENSION.UPDATED_BY_USER_ID,MAPPED_DIMENSION.UPDATED_TIME,MAPPED_DIMENSION.CREATED_TIME from MAPPED_DIMENSION where    MAPPED_MODEL_ID = ? ");
/*      */ 
/*  706 */       int col = 1;
/*  707 */       stmt.setInt(col++, selectMappedModelId);
/*      */ 
/*  709 */       resultSet = stmt.executeQuery();
/*      */ 
/*  711 */       while (resultSet.next())
/*      */       {
/*  713 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  716 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  719 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  722 */       if (currentList != null)
/*      */       {
/*  725 */         ListIterator iter = items.listIterator();
/*  726 */         MappedDimensionEVO currentEVO = null;
/*  727 */         MappedDimensionEVO newEVO = null;
/*  728 */         while (iter.hasNext())
/*      */         {
/*  730 */           newEVO = (MappedDimensionEVO)iter.next();
/*  731 */           Iterator iter2 = currentList.iterator();
/*  732 */           while (iter2.hasNext())
/*      */           {
/*  734 */             currentEVO = (MappedDimensionEVO)iter2.next();
/*  735 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  737 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  743 */         Iterator iter2 = currentList.iterator();
/*  744 */         while (iter2.hasNext())
/*      */         {
/*  746 */           currentEVO = (MappedDimensionEVO)iter2.next();
/*  747 */           if (currentEVO.insertPending()) {
/*  748 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  752 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  756 */       throw handleSQLException("select MAPPED_DIMENSION.MAPPED_DIMENSION_ID,MAPPED_DIMENSION.MAPPED_MODEL_ID,MAPPED_DIMENSION.DIMENSION_ID,MAPPED_DIMENSION.PATH_VIS_ID,MAPPED_DIMENSION.EXCLUDE_DISABLED_LEAF_NODES,MAPPED_DIMENSION.UPDATED_BY_USER_ID,MAPPED_DIMENSION.UPDATED_TIME,MAPPED_DIMENSION.CREATED_TIME from MAPPED_DIMENSION where    MAPPED_MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  760 */       closeResultSet(resultSet);
/*  761 */       closeStatement(stmt);
/*  762 */       closeConnection();
/*      */ 
/*  764 */       if (timer != null) {
/*  765 */         timer.logDebug("getAll", " MappedModelId=" + selectMappedModelId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  770 */     return items;
/*      */   }
/*      */ 
/*      */   public MappedDimensionEVO getDetails(GlobalMappedModel2CK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  789 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  792 */     if (this.mDetails == null) {
/*  793 */       doLoad(((MappedDimensionCK)paramCK).getMappedDimensionPK());
/*      */     }
/*  795 */     else if (!this.mDetails.getPK().equals(((MappedDimensionCK)paramCK).getMappedDimensionPK())) {
/*  796 */       doLoad(((MappedDimensionCK)paramCK).getMappedDimensionPK());
/*      */     }
/*      */ 
/*  799 */     if ((dependants.indexOf("<5>") > -1) && (!this.mDetails.isMappedDimensionElementsAllItemsLoaded()))
/*      */     {
/*  804 */       this.mDetails.setMappedDimensionElements(getMappedDimensionElementDAO().getAll(this.mDetails.getMappedDimensionId(), dependants, this.mDetails.getMappedDimensionElements()));
/*      */ 
/*  811 */       this.mDetails.setMappedDimensionElementsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  815 */     if ((dependants.indexOf("<6>") > -1) && (!this.mDetails.isMappedHierarchysAllItemsLoaded()))
/*      */     {
/*  820 */       this.mDetails.setMappedHierarchys(getMappedHierarchyDAO().getAll(this.mDetails.getMappedDimensionId(), dependants, this.mDetails.getMappedHierarchys()));
/*      */ 
/*  827 */       this.mDetails.setMappedHierarchysAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  830 */     if ((paramCK instanceof MappedDimensionElementCK))
/*      */     {
/*  832 */       if (this.mDetails.getMappedDimensionElements() == null) {
/*  833 */         this.mDetails.loadMappedDimensionElementsItem(getMappedDimensionElementDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  836 */         MappedDimensionElementPK pk = ((MappedDimensionElementCK)paramCK).getMappedDimensionElementPK();
/*  837 */         MappedDimensionElementEVO evo = this.mDetails.getMappedDimensionElementsItem(pk);
/*  838 */         if (evo == null) {
/*  839 */           this.mDetails.loadMappedDimensionElementsItem(getMappedDimensionElementDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*  843 */     else if ((paramCK instanceof MappedHierarchyCK))
/*      */     {
/*  845 */       if (this.mDetails.getMappedHierarchys() == null) {
/*  846 */         this.mDetails.loadMappedHierarchysItem(getMappedHierarchyDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  849 */         MappedHierarchyPK pk = ((MappedHierarchyCK)paramCK).getMappedHierarchyPK();
/*  850 */         MappedHierarchyEVO evo = this.mDetails.getMappedHierarchysItem(pk);
/*  851 */         if (evo == null) {
/*  852 */           this.mDetails.loadMappedHierarchysItem(getMappedHierarchyDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  857 */     MappedDimensionEVO details = new MappedDimensionEVO();
/*  858 */     details = this.mDetails.deepClone();
/*      */ 
/*  860 */     if (timer != null) {
/*  861 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  863 */     return details;
/*      */   }
/*      */ 
/*      */   public MappedDimensionEVO getDetails(GlobalMappedModel2CK paramCK, MappedDimensionEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  869 */     MappedDimensionEVO savedEVO = this.mDetails;
/*  870 */     this.mDetails = paramEVO;
/*  871 */     MappedDimensionEVO newEVO = getDetails(paramCK, dependants);
/*  872 */     this.mDetails = savedEVO;
/*  873 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public MappedDimensionEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  879 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  883 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  886 */     MappedDimensionEVO details = this.mDetails.deepClone();
/*      */ 
/*  888 */     if (timer != null) {
/*  889 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  891 */     return details;
/*      */   }
/*      */ 
/*      */   protected MappedDimensionElementDAO getMappedDimensionElementDAO()
/*      */   {
/*  900 */     if (this.mMappedDimensionElementDAO == null)
/*      */     {
/*  902 */       if (this.mDataSource != null)
/*  903 */         this.mMappedDimensionElementDAO = new MappedDimensionElementDAO(this.mDataSource);
/*      */       else {
/*  905 */         this.mMappedDimensionElementDAO = new MappedDimensionElementDAO(getConnection());
/*      */       }
/*      */     }
/*  908 */     return this.mMappedDimensionElementDAO;
/*      */   }
/*      */ 
/*      */   protected MappedHierarchyDAO getMappedHierarchyDAO()
/*      */   {
/*  917 */     if (this.mMappedHierarchyDAO == null)
/*      */     {
/*  919 */       if (this.mDataSource != null)
/*  920 */         this.mMappedHierarchyDAO = new MappedHierarchyDAO(this.mDataSource);
/*      */       else {
/*  922 */         this.mMappedHierarchyDAO = new MappedHierarchyDAO(getConnection());
/*      */       }
/*      */     }
/*  925 */     return this.mMappedHierarchyDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  930 */     return "MappedDimension";
/*      */   }
/*      */ 
/*      */   public MappedDimensionRefImpl getRef(MappedDimensionPK paramMappedDimensionPK)
/*      */   {
/*  935 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  936 */     PreparedStatement stmt = null;
/*  937 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  940 */       stmt = getConnection().prepareStatement("select 0,MAPPED_MODEL.MAPPED_MODEL_ID from MAPPED_DIMENSION,MAPPED_MODEL where 1=1 and MAPPED_DIMENSION.MAPPED_DIMENSION_ID = ? and MAPPED_DIMENSION.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID");
/*  941 */       int col = 1;
/*  942 */       stmt.setInt(col++, paramMappedDimensionPK.getMappedDimensionId());
/*      */ 
/*  944 */       resultSet = stmt.executeQuery();
/*      */ 
/*  946 */       if (!resultSet.next()) {
/*  947 */         throw new RuntimeException(getEntityName() + " getRef " + paramMappedDimensionPK + " not found");
/*      */       }
/*  949 */       col = 2;
/*  950 */       GlobalMappedModel2PK newMappedModelPK = new GlobalMappedModel2PK(resultSet.getInt(col++));
/*      */ 
/*  954 */       String textMappedDimension = "";
/*  955 */       MappedDimensionCK ckMappedDimension = new MappedDimensionCK(newMappedModelPK, paramMappedDimensionPK);
/*      */ 
/*  960 */       MappedDimensionRefImpl localMappedDimensionRefImpl = new MappedDimensionRefImpl(ckMappedDimension, textMappedDimension);
/*      */       return localMappedDimensionRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  965 */       throw handleSQLException(paramMappedDimensionPK, "select 0,MAPPED_MODEL.MAPPED_MODEL_ID from MAPPED_DIMENSION,MAPPED_MODEL where 1=1 and MAPPED_DIMENSION.MAPPED_DIMENSION_ID = ? and MAPPED_DIMENSION.MAPPED_MODEL_ID = MAPPED_MODEL.MAPPED_MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  969 */       closeResultSet(resultSet);
/*  970 */       closeStatement(stmt);
/*  971 */       closeConnection();
/*      */ 
/*  973 */       if (timer != null)
/*  974 */         timer.logDebug("getRef", paramMappedDimensionPK); 
/*  974 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/*  986 */     if (c == null)
/*  987 */       return;
/*  988 */     Iterator iter = c.iterator();
/*  989 */     while (iter.hasNext())
/*      */     {
/*  991 */       MappedDimensionEVO evo = (MappedDimensionEVO)iter.next();
/*  992 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(MappedDimensionEVO evo, String dependants)
/*      */   {
/* 1006 */     if (evo.insertPending()) {
/* 1007 */       return;
/*      */     }
/* 1009 */     if (evo.getMappedDimensionId() < 1) {
/* 1010 */       return;
/*      */     }
/*      */ 
/* 1014 */     if (dependants.indexOf("<5>") > -1)
/*      */     {
/* 1017 */       if (!evo.isMappedDimensionElementsAllItemsLoaded())
/*      */       {
/* 1019 */         evo.setMappedDimensionElements(getMappedDimensionElementDAO().getAll(evo.getMappedDimensionId(), dependants, evo.getMappedDimensionElements()));
/*      */ 
/* 1026 */         evo.setMappedDimensionElementsAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1031 */     if (dependants.indexOf("<6>") > -1)
/*      */     {
/* 1034 */       if (!evo.isMappedHierarchysAllItemsLoaded())
/*      */       {
/* 1036 */         evo.setMappedHierarchys(getMappedHierarchyDAO().getAll(evo.getMappedDimensionId(), dependants, evo.getMappedHierarchys()));
/*      */ 
/* 1043 */         evo.setMappedHierarchysAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.mapping.MappedDimensionDAO
 * JD-Core Version:    0.6.0
 */