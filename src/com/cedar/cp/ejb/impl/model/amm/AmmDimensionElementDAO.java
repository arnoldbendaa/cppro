/*      */ package com.cedar.cp.ejb.impl.model.amm;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.amm.AmmDimensionElementCK;
/*      */ import com.cedar.cp.dto.model.amm.AmmDimensionElementPK;
/*      */ import com.cedar.cp.dto.model.amm.AmmDimensionElementRefImpl;
/*      */ import com.cedar.cp.dto.model.amm.AmmDimensionPK;
/*      */ import com.cedar.cp.dto.model.amm.AmmModelCK;
/*      */ import com.cedar.cp.dto.model.amm.AmmModelPK;
/*      */ import com.cedar.cp.dto.model.amm.AmmSrcStructureElementCK;
/*      */ import com.cedar.cp.dto.model.amm.AmmSrcStructureElementPK;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.SqlBuilder;
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
/*      */ public class AmmDimensionElementDAO extends AbstractDAO
/*      */ {
/*   33 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID,AMM_DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,AMM_DIMENSION_ELEMENT.UPDATED_TIME,AMM_DIMENSION_ELEMENT.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from AMM_DIMENSION_ELEMENT where    AMM_DIMENSION_ELEMENT_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into AMM_DIMENSION_ELEMENT ( AMM_DIMENSION_ELEMENT_ID,AMM_DIMENSION_ID,DIMENSION_ELEMENT_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update AMM_DIMENSION_ELEMENT set AMM_DIMENSION_ID = ?,DIMENSION_ELEMENT_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_DIMENSION_ELEMENT_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from AMM_DIMENSION_ELEMENT where    AMM_DIMENSION_ELEMENT_ID = ? ";
/*  465 */   private static String[][] SQL_DELETE_CHILDREN = { { "AMM_SRC_STRUCTURE_ELEMENT", "delete from AMM_SRC_STRUCTURE_ELEMENT where     AMM_SRC_STRUCTURE_ELEMENT.AMM_DIMENSION_ELEMENT_ID = ? " } };
/*      */ 
/*  474 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  478 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from AMM_DIMENSION_ELEMENT,AMM_DIMENSION where 1=1 and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID = AMM_DIMENSION.AMM_DIMENSION_ID and AMM_DIMENSION.AMM_MODEL_ID = ? order by  AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID ,AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID";
/*      */   protected static final String SQL_GET_ALL = " from AMM_DIMENSION_ELEMENT where    AMM_DIMENSION_ID = ? ";
/*      */   protected AmmSrcStructureElementDAO mAmmSrcStructureElementDAO;
/*      */   protected AmmDimensionElementEVO mDetails;
/*      */ 
/*      */   public AmmDimensionElementDAO(Connection connection)
/*      */   {
/*   40 */     super(connection);
/*      */   }
/*      */ 
/*      */   public AmmDimensionElementDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public AmmDimensionElementDAO(DataSource ds)
/*      */   {
/*   56 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected AmmDimensionElementPK getPK()
/*      */   {
/*   64 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(AmmDimensionElementEVO details)
/*      */   {
/*   73 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private AmmDimensionElementEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   90 */     int col = 1;
/*   91 */     AmmDimensionElementEVO evo = new AmmDimensionElementEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++), null);
/*      */ 
/*   98 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*   99 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  100 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  101 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(AmmDimensionElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  106 */     int col = startCol_;
/*  107 */     stmt_.setInt(col++, evo_.getAmmDimensionElementId());
/*  108 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(AmmDimensionElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  113 */     int col = startCol_;
/*  114 */     stmt_.setInt(col++, evo_.getAmmDimensionId());
/*  115 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDimensionElementId());
/*  116 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  117 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  118 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  119 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(AmmDimensionElementPK pk)
/*      */     throws ValidationException
/*      */   {
/*  135 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  137 */     PreparedStatement stmt = null;
/*  138 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  142 */       stmt = getConnection().prepareStatement("select AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID,AMM_DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,AMM_DIMENSION_ELEMENT.UPDATED_TIME,AMM_DIMENSION_ELEMENT.CREATED_TIME from AMM_DIMENSION_ELEMENT where    AMM_DIMENSION_ELEMENT_ID = ? ");
/*      */ 
/*  145 */       int col = 1;
/*  146 */       stmt.setInt(col++, pk.getAmmDimensionElementId());
/*      */ 
/*  148 */       resultSet = stmt.executeQuery();
/*      */ 
/*  150 */       if (!resultSet.next()) {
/*  151 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  154 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  155 */       if (this.mDetails.isModified())
/*  156 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  160 */       throw handleSQLException(pk, "select AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID,AMM_DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,AMM_DIMENSION_ELEMENT.UPDATED_TIME,AMM_DIMENSION_ELEMENT.CREATED_TIME from AMM_DIMENSION_ELEMENT where    AMM_DIMENSION_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  164 */       closeResultSet(resultSet);
/*  165 */       closeStatement(stmt);
/*  166 */       closeConnection();
/*      */ 
/*  168 */       if (timer != null)
/*  169 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  198 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  199 */     this.mDetails.postCreateInit();
/*      */ 
/*  201 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  206 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  207 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  208 */       stmt = getConnection().prepareStatement("insert into AMM_DIMENSION_ELEMENT ( AMM_DIMENSION_ELEMENT_ID,AMM_DIMENSION_ID,DIMENSION_ELEMENT_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)");
/*      */ 
/*  211 */       int col = 1;
/*  212 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  213 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  216 */       int resultCount = stmt.executeUpdate();
/*  217 */       if (resultCount != 1)
/*      */       {
/*  219 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  222 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  226 */       throw handleSQLException(this.mDetails.getPK(), "insert into AMM_DIMENSION_ELEMENT ( AMM_DIMENSION_ELEMENT_ID,AMM_DIMENSION_ID,DIMENSION_ELEMENT_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  230 */       closeStatement(stmt);
/*  231 */       closeConnection();
/*      */ 
/*  233 */       if (timer != null) {
/*  234 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  240 */       getAmmSrcStructureElementDAO().update(this.mDetails.getAmmSourceElementsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  246 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  270 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  274 */     PreparedStatement stmt = null;
/*      */ 
/*  276 */     boolean mainChanged = this.mDetails.isModified();
/*  277 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  281 */       if (getAmmSrcStructureElementDAO().update(this.mDetails.getAmmSourceElementsMap())) {
/*  282 */         dependantChanged = true;
/*      */       }
/*  284 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  287 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  288 */         stmt = getConnection().prepareStatement("update AMM_DIMENSION_ELEMENT set AMM_DIMENSION_ID = ?,DIMENSION_ELEMENT_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_DIMENSION_ELEMENT_ID = ? ");
/*      */ 
/*  291 */         int col = 1;
/*  292 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  293 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  296 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  299 */         if (resultCount != 1) {
/*  300 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  303 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  312 */       throw handleSQLException(getPK(), "update AMM_DIMENSION_ELEMENT set AMM_DIMENSION_ID = ?,DIMENSION_ELEMENT_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AMM_DIMENSION_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  316 */       closeStatement(stmt);
/*  317 */       closeConnection();
/*      */ 
/*  319 */       if ((timer != null) && (
/*  320 */         (mainChanged) || (dependantChanged)))
/*  321 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  340 */     if (items == null) {
/*  341 */       return false;
/*      */     }
/*  343 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  344 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  346 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  350 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  351 */       while (iter3.hasNext())
/*      */       {
/*  353 */         this.mDetails = ((AmmDimensionElementEVO)iter3.next());
/*  354 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  356 */         somethingChanged = true;
/*      */ 
/*  359 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  363 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  364 */       while (iter2.hasNext())
/*      */       {
/*  366 */         this.mDetails = ((AmmDimensionElementEVO)iter2.next());
/*      */ 
/*  369 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  371 */         somethingChanged = true;
/*      */ 
/*  374 */         if (deleteStmt == null) {
/*  375 */           deleteStmt = getConnection().prepareStatement("delete from AMM_DIMENSION_ELEMENT where    AMM_DIMENSION_ELEMENT_ID = ? ");
/*      */         }
/*      */ 
/*  378 */         int col = 1;
/*  379 */         deleteStmt.setInt(col++, this.mDetails.getAmmDimensionElementId());
/*      */ 
/*  381 */         if (this._log.isDebugEnabled()) {
/*  382 */           this._log.debug("update", "AmmDimensionElement deleting AmmDimensionElementId=" + this.mDetails.getAmmDimensionElementId());
/*      */         }
/*      */ 
/*  387 */         deleteStmt.addBatch();
/*      */ 
/*  390 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  395 */       if (deleteStmt != null)
/*      */       {
/*  397 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  399 */         deleteStmt.executeBatch();
/*      */ 
/*  401 */         if (timer2 != null) {
/*  402 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  406 */       Iterator iter1 = items.values().iterator();
/*  407 */       while (iter1.hasNext())
/*      */       {
/*  409 */         this.mDetails = ((AmmDimensionElementEVO)iter1.next());
/*      */ 
/*  411 */         if (this.mDetails.insertPending())
/*      */         {
/*  413 */           somethingChanged = true;
/*  414 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  417 */         if (this.mDetails.isModified())
/*      */         {
/*  419 */           somethingChanged = true;
/*  420 */           doStore(); continue;
/*      */         }
/*      */ 
/*  424 */         if ((this.mDetails.deletePending()) || 
/*  430 */           (!getAmmSrcStructureElementDAO().update(this.mDetails.getAmmSourceElementsMap()))) continue;
/*  431 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  443 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  447 */       throw handleSQLException("delete from AMM_DIMENSION_ELEMENT where    AMM_DIMENSION_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  451 */       if (deleteStmt != null)
/*      */       {
/*  453 */         closeStatement(deleteStmt);
/*  454 */         closeConnection();
/*      */       }
/*      */ 
/*  457 */       this.mDetails = null;
/*      */ 
/*  459 */       if ((somethingChanged) && 
/*  460 */         (timer != null))
/*  461 */         timer.logDebug("update", "collection"); 
/*  461 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(AmmDimensionElementPK pk)
/*      */   {
/*  487 */     Set emptyStrings = Collections.emptySet();
/*  488 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(AmmDimensionElementPK pk, Set<String> exclusionTables)
/*      */   {
/*  494 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  496 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  498 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  500 */       PreparedStatement stmt = null;
/*      */ 
/*  502 */       int resultCount = 0;
/*  503 */       String s = null;
/*      */       try
/*      */       {
/*  506 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  508 */         if (this._log.isDebugEnabled()) {
/*  509 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  511 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  514 */         int col = 1;
/*  515 */         stmt.setInt(col++, pk.getAmmDimensionElementId());
/*      */ 
/*  518 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  522 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  526 */         closeStatement(stmt);
/*  527 */         closeConnection();
/*      */ 
/*  529 */         if (timer != null) {
/*  530 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  534 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  536 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  538 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  540 */       PreparedStatement stmt = null;
/*      */ 
/*  542 */       int resultCount = 0;
/*  543 */       String s = null;
/*      */       try
/*      */       {
/*  546 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  548 */         if (this._log.isDebugEnabled()) {
/*  549 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  551 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  554 */         int col = 1;
/*  555 */         stmt.setInt(col++, pk.getAmmDimensionElementId());
/*      */ 
/*  558 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  562 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  566 */         closeStatement(stmt);
/*  567 */         closeConnection();
/*      */ 
/*  569 */         if (timer != null)
/*  570 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(AmmModelPK entityPK, Collection owners, String dependants)
/*      */   {
/*  594 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  596 */     PreparedStatement stmt = null;
/*  597 */     ResultSet resultSet = null;
/*      */ 
/*  599 */     int itemCount = 0;
/*      */ 
/*  601 */     AmmDimensionEVO owningEVO = null;
/*  602 */     Iterator ownersIter = owners.iterator();
/*  603 */     while (ownersIter.hasNext())
/*      */     {
/*  605 */       owningEVO = (AmmDimensionEVO)ownersIter.next();
/*  606 */       owningEVO.setAmmDimElementsAllItemsLoaded(true);
/*      */     }
/*  608 */     ownersIter = owners.iterator();
/*  609 */     owningEVO = (AmmDimensionEVO)ownersIter.next();
/*  610 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  614 */       stmt = getConnection().prepareStatement("select AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID,AMM_DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,AMM_DIMENSION_ELEMENT.UPDATED_TIME,AMM_DIMENSION_ELEMENT.CREATED_TIME from AMM_DIMENSION_ELEMENT,AMM_DIMENSION where 1=1 and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID = AMM_DIMENSION.AMM_DIMENSION_ID and AMM_DIMENSION.AMM_MODEL_ID = ? order by  AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID ,AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID");
/*      */ 
/*  616 */       int col = 1;
/*  617 */       stmt.setInt(col++, entityPK.getAmmModelId());
/*      */ 
/*  619 */       resultSet = stmt.executeQuery();
/*      */ 
/*  622 */       while (resultSet.next())
/*      */       {
/*  624 */         itemCount++;
/*  625 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  630 */         while (this.mDetails.getAmmDimensionId() != owningEVO.getAmmDimensionId())
/*      */         {
/*  634 */           if (!ownersIter.hasNext())
/*      */           {
/*  636 */             this._log.debug("bulkGetAll", "can't find owning [AmmDimensionId=" + this.mDetails.getAmmDimensionId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  640 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  641 */             ownersIter = owners.iterator();
/*  642 */             while (ownersIter.hasNext())
/*      */             {
/*  644 */               owningEVO = (AmmDimensionEVO)ownersIter.next();
/*  645 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  647 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  649 */           owningEVO = (AmmDimensionEVO)ownersIter.next();
/*      */         }
/*  651 */         if (owningEVO.getAmmDimElements() == null)
/*      */         {
/*  653 */           theseItems = new ArrayList();
/*  654 */           owningEVO.setAmmDimElements(theseItems);
/*  655 */           owningEVO.setAmmDimElementsAllItemsLoaded(true);
/*      */         }
/*  657 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  660 */       if (timer != null) {
/*  661 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  664 */       if ((itemCount > 0) && (dependants.indexOf("<2>") > -1))
/*      */       {
/*  666 */         getAmmSrcStructureElementDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  670 */       throw handleSQLException("select AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID,AMM_DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,AMM_DIMENSION_ELEMENT.UPDATED_TIME,AMM_DIMENSION_ELEMENT.CREATED_TIME from AMM_DIMENSION_ELEMENT,AMM_DIMENSION where 1=1 and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID = AMM_DIMENSION.AMM_DIMENSION_ID and AMM_DIMENSION.AMM_MODEL_ID = ? order by  AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID ,AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  674 */       closeResultSet(resultSet);
/*  675 */       closeStatement(stmt);
/*  676 */       closeConnection();
/*      */ 
/*  678 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectAmmDimensionId, String dependants, Collection currentList)
/*      */   {
/*  703 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  704 */     PreparedStatement stmt = null;
/*  705 */     ResultSet resultSet = null;
/*      */ 
/*  707 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  711 */       stmt = getConnection().prepareStatement("select AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID,AMM_DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,AMM_DIMENSION_ELEMENT.UPDATED_TIME,AMM_DIMENSION_ELEMENT.CREATED_TIME from AMM_DIMENSION_ELEMENT where    AMM_DIMENSION_ID = ? ");
/*      */ 
/*  713 */       int col = 1;
/*  714 */       stmt.setInt(col++, selectAmmDimensionId);
/*      */ 
/*  716 */       resultSet = stmt.executeQuery();
/*      */ 
/*  718 */       while (resultSet.next())
/*      */       {
/*  720 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  723 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  726 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  729 */       if (currentList != null)
/*      */       {
/*  732 */         ListIterator iter = items.listIterator();
/*  733 */         AmmDimensionElementEVO currentEVO = null;
/*  734 */         AmmDimensionElementEVO newEVO = null;
/*  735 */         while (iter.hasNext())
/*      */         {
/*  737 */           newEVO = (AmmDimensionElementEVO)iter.next();
/*  738 */           Iterator iter2 = currentList.iterator();
/*  739 */           while (iter2.hasNext())
/*      */           {
/*  741 */             currentEVO = (AmmDimensionElementEVO)iter2.next();
/*  742 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  744 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  750 */         Iterator iter2 = currentList.iterator();
/*  751 */         while (iter2.hasNext())
/*      */         {
/*  753 */           currentEVO = (AmmDimensionElementEVO)iter2.next();
/*  754 */           if (currentEVO.insertPending()) {
/*  755 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  759 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  763 */       throw handleSQLException("select AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID,AMM_DIMENSION_ELEMENT.DIMENSION_ELEMENT_ID,AMM_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,AMM_DIMENSION_ELEMENT.UPDATED_TIME,AMM_DIMENSION_ELEMENT.CREATED_TIME from AMM_DIMENSION_ELEMENT where    AMM_DIMENSION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  767 */       closeResultSet(resultSet);
/*  768 */       closeStatement(stmt);
/*  769 */       closeConnection();
/*      */ 
/*  771 */       if (timer != null) {
/*  772 */         timer.logDebug("getAll", " AmmDimensionId=" + selectAmmDimensionId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  777 */     return items;
/*      */   }
/*      */ 
/*      */   public AmmDimensionElementEVO getDetails(AmmModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  794 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  797 */     if (this.mDetails == null) {
/*  798 */       doLoad(((AmmDimensionElementCK)paramCK).getAmmDimensionElementPK());
/*      */     }
/*  800 */     else if (!this.mDetails.getPK().equals(((AmmDimensionElementCK)paramCK).getAmmDimensionElementPK())) {
/*  801 */       doLoad(((AmmDimensionElementCK)paramCK).getAmmDimensionElementPK());
/*      */     }
/*      */ 
/*  804 */     if ((dependants.indexOf("<2>") > -1) && (!this.mDetails.isAmmSourceElementsAllItemsLoaded()))
/*      */     {
/*  809 */       this.mDetails.setAmmSourceElements(getAmmSrcStructureElementDAO().getAll(this.mDetails.getAmmDimensionElementId(), dependants, this.mDetails.getAmmSourceElements()));
/*      */ 
/*  816 */       this.mDetails.setAmmSourceElementsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  819 */     if ((paramCK instanceof AmmSrcStructureElementCK))
/*      */     {
/*  821 */       if (this.mDetails.getAmmSourceElements() == null) {
/*  822 */         this.mDetails.loadAmmSourceElementsItem(getAmmSrcStructureElementDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  825 */         AmmSrcStructureElementPK pk = ((AmmSrcStructureElementCK)paramCK).getAmmSrcStructureElementPK();
/*  826 */         AmmSrcStructureElementEVO evo = this.mDetails.getAmmSourceElementsItem(pk);
/*  827 */         if (evo == null) {
/*  828 */           this.mDetails.loadAmmSourceElementsItem(getAmmSrcStructureElementDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  833 */     AmmDimensionElementEVO details = new AmmDimensionElementEVO();
/*  834 */     details = this.mDetails.deepClone();
/*      */ 
/*  836 */     if (timer != null) {
/*  837 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  839 */     return details;
/*      */   }
/*      */ 
/*      */   public AmmDimensionElementEVO getDetails(AmmModelCK paramCK, AmmDimensionElementEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  845 */     AmmDimensionElementEVO savedEVO = this.mDetails;
/*  846 */     this.mDetails = paramEVO;
/*  847 */     AmmDimensionElementEVO newEVO = getDetails(paramCK, dependants);
/*  848 */     this.mDetails = savedEVO;
/*  849 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public AmmDimensionElementEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  855 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  859 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  862 */     AmmDimensionElementEVO details = this.mDetails.deepClone();
/*      */ 
/*  864 */     if (timer != null) {
/*  865 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  867 */     return details;
/*      */   }
/*      */ 
/*      */   protected AmmSrcStructureElementDAO getAmmSrcStructureElementDAO()
/*      */   {
/*  876 */     if (this.mAmmSrcStructureElementDAO == null)
/*      */     {
/*  878 */       if (this.mDataSource != null)
/*  879 */         this.mAmmSrcStructureElementDAO = new AmmSrcStructureElementDAO(this.mDataSource);
/*      */       else {
/*  881 */         this.mAmmSrcStructureElementDAO = new AmmSrcStructureElementDAO(getConnection());
/*      */       }
/*      */     }
/*  884 */     return this.mAmmSrcStructureElementDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  889 */     return "AmmDimensionElement";
/*      */   }
/*      */ 
/*      */   public AmmDimensionElementRefImpl getRef(AmmDimensionElementPK paramAmmDimensionElementPK)
/*      */   {
/*  894 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  895 */     PreparedStatement stmt = null;
/*  896 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  899 */       stmt = getConnection().prepareStatement("select 0,AMM_MODEL.AMM_MODEL_ID,AMM_DIMENSION.AMM_DIMENSION_ID from AMM_DIMENSION_ELEMENT,AMM_MODEL,AMM_DIMENSION where 1=1 and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID = ? and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID = AMM_DIMENSION.AMM_DIMENSION_ID and AMM_DIMENSION.AMM_DIMENSION_ID = AMM_MODEL.AMM_DIMENSION_ID");
/*  900 */       int col = 1;
/*  901 */       stmt.setInt(col++, paramAmmDimensionElementPK.getAmmDimensionElementId());
/*      */ 
/*  903 */       resultSet = stmt.executeQuery();
/*      */ 
/*  905 */       if (!resultSet.next()) {
/*  906 */         throw new RuntimeException(getEntityName() + " getRef " + paramAmmDimensionElementPK + " not found");
/*      */       }
/*  908 */       col = 2;
/*  909 */       AmmModelPK newAmmModelPK = new AmmModelPK(resultSet.getInt(col++));
/*      */ 
/*  913 */       AmmDimensionPK newAmmDimensionPK = new AmmDimensionPK(resultSet.getInt(col++));
/*      */ 
/*  917 */       String textAmmDimensionElement = "";
/*  918 */       AmmDimensionElementCK ckAmmDimensionElement = new AmmDimensionElementCK(newAmmModelPK, newAmmDimensionPK, paramAmmDimensionElementPK);
/*      */ 
/*  924 */       AmmDimensionElementRefImpl localAmmDimensionElementRefImpl = new AmmDimensionElementRefImpl(ckAmmDimensionElement, textAmmDimensionElement);
/*      */       return localAmmDimensionElementRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  929 */       throw handleSQLException(paramAmmDimensionElementPK, "select 0,AMM_MODEL.AMM_MODEL_ID,AMM_DIMENSION.AMM_DIMENSION_ID from AMM_DIMENSION_ELEMENT,AMM_MODEL,AMM_DIMENSION where 1=1 and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ELEMENT_ID = ? and AMM_DIMENSION_ELEMENT.AMM_DIMENSION_ID = AMM_DIMENSION.AMM_DIMENSION_ID and AMM_DIMENSION.AMM_DIMENSION_ID = AMM_MODEL.AMM_DIMENSION_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  933 */       closeResultSet(resultSet);
/*  934 */       closeStatement(stmt);
/*  935 */       closeConnection();
/*      */ 
/*  937 */       if (timer != null)
/*  938 */         timer.logDebug("getRef", paramAmmDimensionElementPK); 
/*  938 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/*  950 */     if (c == null)
/*  951 */       return;
/*  952 */     Iterator iter = c.iterator();
/*  953 */     while (iter.hasNext())
/*      */     {
/*  955 */       AmmDimensionElementEVO evo = (AmmDimensionElementEVO)iter.next();
/*  956 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(AmmDimensionElementEVO evo, String dependants)
/*      */   {
/*  970 */     if (evo.insertPending()) {
/*  971 */       return;
/*      */     }
/*  973 */     if (evo.getAmmDimensionElementId() < 1) {
/*  974 */       return;
/*      */     }
/*      */ 
/*  978 */     if (dependants.indexOf("<2>") > -1)
/*      */     {
/*  981 */       if (!evo.isAmmSourceElementsAllItemsLoaded())
/*      */       {
/*  983 */         evo.setAmmSourceElements(getAmmSrcStructureElementDAO().getAll(evo.getAmmDimensionElementId(), dependants, evo.getAmmSourceElements()));
/*      */ 
/*  990 */         evo.setAmmSourceElementsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isDimensionElementMapped(int deId)
/*      */   {
/* 1000 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "select  1 from dual", "where   exists", "        (", "        select  1", "        from    AMM_DIMENSION_ELEMENT", "        where   DIMENSION_ELEMENT_ID = <deId>", "        )", "or      exists", "        (", "        select  1", "        from    AMM_SRC_STRUCTURE_ELEMENT", "        where   SRC_STRUCTURE_ELEMENT_ID = <deId>", "        )" });
/*      */ 
/* 1015 */     SqlExecutor sqle = new SqlExecutor("isDimensionElementMapped", getDataSource(), sqlb, this._log);
/* 1016 */     sqle.addBindVariable("<deId>", Integer.valueOf(deId));
/* 1017 */     ResultSet rs = sqle.getResultSet();
/*      */     try
/*      */     {
/* 1020 */       return rs.next();
/*      */     }
/*      */     catch (SQLException e) {
	/* 1024 */     throw new RuntimeException(e);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.amm.AmmDimensionElementDAO
 * JD-Core Version:    0.6.0
 */