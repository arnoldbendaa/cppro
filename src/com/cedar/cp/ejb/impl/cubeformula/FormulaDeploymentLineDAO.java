/*      */ package com.cedar.cp.ejb.impl.cubeformula;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
/*      */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtCK;
/*      */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentDtPK;
/*      */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryCK;
/*      */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryPK;
/*      */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentLineCK;
/*      */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentLinePK;
/*      */ import com.cedar.cp.dto.cubeformula.FormulaDeploymentLineRefImpl;
/*      */ import com.cedar.cp.dto.model.FinanceCubePK;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class FormulaDeploymentLineDAO extends AbstractDAO
/*      */ {
/*   41 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID,FORMULA_DEPLOYMENT_LINE.LINE_INDEX,FORMULA_DEPLOYMENT_LINE.CONTEXT";
/*      */   protected static final String SQL_LOAD = " from FORMULA_DEPLOYMENT_LINE where    FORMULA_DEPLOYMENT_LINE_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into FORMULA_DEPLOYMENT_LINE ( FORMULA_DEPLOYMENT_LINE_ID,CUBE_FORMULA_ID,LINE_INDEX,CONTEXT) values ( ?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update FORMULA_DEPLOYMENT_LINE set CUBE_FORMULA_ID = ?,LINE_INDEX = ?,CONTEXT = ? where    FORMULA_DEPLOYMENT_LINE_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from FORMULA_DEPLOYMENT_LINE where    FORMULA_DEPLOYMENT_LINE_ID = ? ";
/*  467 */   private static String[][] SQL_DELETE_CHILDREN = { { "FORMULA_DEPLOYMENT_ENTRY", "delete from FORMULA_DEPLOYMENT_ENTRY where     FORMULA_DEPLOYMENT_ENTRY.FORMULA_DEPLOYMENT_LINE_ID = ? " }, { "FORMULA_DEPLOYMENT_DT", "delete from FORMULA_DEPLOYMENT_DT where     FORMULA_DEPLOYMENT_DT.FORMULA_DEPLOYMENT_LINE_ID = ? " } };
/*      */ 
/*  481 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  485 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA,FINANCE_CUBE where 1=1 and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID ,FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID";
/*      */   protected static final String SQL_GET_ALL = " from FORMULA_DEPLOYMENT_LINE where    CUBE_FORMULA_ID = ? ";
/*      */   protected FormulaDeploymentEntryDAO mFormulaDeploymentEntryDAO;
/*      */   protected FormulaDeploymentDtDAO mFormulaDeploymentDtDAO;
/*      */   protected FormulaDeploymentLineEVO mDetails;
/*      */ 
/*      */   public FormulaDeploymentLineDAO(Connection connection)
/*      */   {
/*   48 */     super(connection);
/*      */   }
/*      */ 
/*      */   public FormulaDeploymentLineDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public FormulaDeploymentLineDAO(DataSource ds)
/*      */   {
/*   64 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected FormulaDeploymentLinePK getPK()
/*      */   {
/*   72 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(FormulaDeploymentLineEVO details)
/*      */   {
/*   81 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private FormulaDeploymentLineEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   96 */     int col = 1;
/*   97 */     FormulaDeploymentLineEVO evo = new FormulaDeploymentLineEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), null, null);
/*      */ 
/*  106 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(FormulaDeploymentLineEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  111 */     int col = startCol_;
/*  112 */     stmt_.setInt(col++, evo_.getFormulaDeploymentLineId());
/*  113 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(FormulaDeploymentLineEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  118 */     int col = startCol_;
/*  119 */     stmt_.setInt(col++, evo_.getCubeFormulaId());
/*  120 */     stmt_.setInt(col++, evo_.getLineIndex());
/*  121 */     stmt_.setString(col++, evo_.getContext());
/*  122 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(FormulaDeploymentLinePK pk)
/*      */     throws ValidationException
/*      */   {
/*  138 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  140 */     PreparedStatement stmt = null;
/*  141 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  145 */       stmt = getConnection().prepareStatement("select FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID,FORMULA_DEPLOYMENT_LINE.LINE_INDEX,FORMULA_DEPLOYMENT_LINE.CONTEXT from FORMULA_DEPLOYMENT_LINE where    FORMULA_DEPLOYMENT_LINE_ID = ? ");
/*      */ 
/*  148 */       int col = 1;
/*  149 */       stmt.setInt(col++, pk.getFormulaDeploymentLineId());
/*      */ 
/*  151 */       resultSet = stmt.executeQuery();
/*      */ 
/*  153 */       if (!resultSet.next()) {
/*  154 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  157 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  158 */       if (this.mDetails.isModified())
/*  159 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  163 */       throw handleSQLException(pk, "select FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID,FORMULA_DEPLOYMENT_LINE.LINE_INDEX,FORMULA_DEPLOYMENT_LINE.CONTEXT from FORMULA_DEPLOYMENT_LINE where    FORMULA_DEPLOYMENT_LINE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  167 */       closeResultSet(resultSet);
/*  168 */       closeStatement(stmt);
/*  169 */       closeConnection();
/*      */ 
/*  171 */       if (timer != null)
/*  172 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  197 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  198 */     this.mDetails.postCreateInit();
/*      */ 
/*  200 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  204 */       stmt = getConnection().prepareStatement("insert into FORMULA_DEPLOYMENT_LINE ( FORMULA_DEPLOYMENT_LINE_ID,CUBE_FORMULA_ID,LINE_INDEX,CONTEXT) values ( ?,?,?,?)");
/*      */ 
/*  207 */       int col = 1;
/*  208 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  209 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  212 */       int resultCount = stmt.executeUpdate();
/*  213 */       if (resultCount != 1)
/*      */       {
/*  215 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  218 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  222 */       throw handleSQLException(this.mDetails.getPK(), "insert into FORMULA_DEPLOYMENT_LINE ( FORMULA_DEPLOYMENT_LINE_ID,CUBE_FORMULA_ID,LINE_INDEX,CONTEXT) values ( ?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  226 */       closeStatement(stmt);
/*  227 */       closeConnection();
/*      */ 
/*  229 */       if (timer != null) {
/*  230 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  236 */       getFormulaDeploymentEntryDAO().update(this.mDetails.getDeploymentEntriesMap());
/*      */ 
/*  238 */       getFormulaDeploymentDtDAO().update(this.mDetails.getDeploymentDataTypesMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  244 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  266 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  270 */     PreparedStatement stmt = null;
/*      */ 
/*  272 */     boolean mainChanged = this.mDetails.isModified();
/*  273 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  277 */       if (getFormulaDeploymentEntryDAO().update(this.mDetails.getDeploymentEntriesMap())) {
/*  278 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  281 */       if (getFormulaDeploymentDtDAO().update(this.mDetails.getDeploymentDataTypesMap())) {
/*  282 */         dependantChanged = true;
/*      */       }
/*  284 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  286 */         stmt = getConnection().prepareStatement("update FORMULA_DEPLOYMENT_LINE set CUBE_FORMULA_ID = ?,LINE_INDEX = ?,CONTEXT = ? where    FORMULA_DEPLOYMENT_LINE_ID = ? ");
/*      */ 
/*  289 */         int col = 1;
/*  290 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  291 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  294 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  297 */         if (resultCount != 1) {
/*  298 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  301 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  310 */       throw handleSQLException(getPK(), "update FORMULA_DEPLOYMENT_LINE set CUBE_FORMULA_ID = ?,LINE_INDEX = ?,CONTEXT = ? where    FORMULA_DEPLOYMENT_LINE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  314 */       closeStatement(stmt);
/*  315 */       closeConnection();
/*      */ 
/*  317 */       if ((timer != null) && (
/*  318 */         (mainChanged) || (dependantChanged)))
/*  319 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  338 */     if (items == null) {
/*  339 */       return false;
/*      */     }
/*  341 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  342 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  344 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  348 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  349 */       while (iter3.hasNext())
/*      */       {
/*  351 */         this.mDetails = ((FormulaDeploymentLineEVO)iter3.next());
/*  352 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  354 */         somethingChanged = true;
/*      */ 
/*  357 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  361 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  362 */       while (iter2.hasNext())
/*      */       {
/*  364 */         this.mDetails = ((FormulaDeploymentLineEVO)iter2.next());
/*      */ 
/*  367 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  369 */         somethingChanged = true;
/*      */ 
/*  372 */         if (deleteStmt == null) {
/*  373 */           deleteStmt = getConnection().prepareStatement("delete from FORMULA_DEPLOYMENT_LINE where    FORMULA_DEPLOYMENT_LINE_ID = ? ");
/*      */         }
/*      */ 
/*  376 */         int col = 1;
/*  377 */         deleteStmt.setInt(col++, this.mDetails.getFormulaDeploymentLineId());
/*      */ 
/*  379 */         if (this._log.isDebugEnabled()) {
/*  380 */           this._log.debug("update", "FormulaDeploymentLine deleting FormulaDeploymentLineId=" + this.mDetails.getFormulaDeploymentLineId());
/*      */         }
/*      */ 
/*  385 */         deleteStmt.addBatch();
/*      */ 
/*  388 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  393 */       if (deleteStmt != null)
/*      */       {
/*  395 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  397 */         deleteStmt.executeBatch();
/*      */ 
/*  399 */         if (timer2 != null) {
/*  400 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  404 */       Iterator iter1 = items.values().iterator();
/*  405 */       while (iter1.hasNext())
/*      */       {
/*  407 */         this.mDetails = ((FormulaDeploymentLineEVO)iter1.next());
/*      */ 
/*  409 */         if (this.mDetails.insertPending())
/*      */         {
/*  411 */           somethingChanged = true;
/*  412 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  415 */         if (this.mDetails.isModified())
/*      */         {
/*  417 */           somethingChanged = true;
/*  418 */           doStore(); continue;
/*      */         }
/*      */ 
/*  422 */         if (this.mDetails.deletePending())
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  428 */         if (getFormulaDeploymentEntryDAO().update(this.mDetails.getDeploymentEntriesMap())) {
/*  429 */           somethingChanged = true;
/*      */         }
/*      */ 
/*  432 */         if (getFormulaDeploymentDtDAO().update(this.mDetails.getDeploymentDataTypesMap())) {
/*  433 */           somethingChanged = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  445 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  449 */       throw handleSQLException("delete from FORMULA_DEPLOYMENT_LINE where    FORMULA_DEPLOYMENT_LINE_ID = ? ", sqle);
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
/*      */   private void deleteDependants(FormulaDeploymentLinePK pk)
/*      */   {
/*  494 */     Set emptyStrings = Collections.emptySet();
/*  495 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(FormulaDeploymentLinePK pk, Set<String> exclusionTables)
/*      */   {
/*  501 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  503 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  505 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  507 */       PreparedStatement stmt = null;
/*      */ 
/*  509 */       int resultCount = 0;
/*  510 */       String s = null;
/*      */       try
/*      */       {
/*  513 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  515 */         if (this._log.isDebugEnabled()) {
/*  516 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  518 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  521 */         int col = 1;
/*  522 */         stmt.setInt(col++, pk.getFormulaDeploymentLineId());
/*      */ 
/*  525 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  529 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  533 */         closeStatement(stmt);
/*  534 */         closeConnection();
/*      */ 
/*  536 */         if (timer != null) {
/*  537 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  541 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  543 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  545 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  547 */       PreparedStatement stmt = null;
/*      */ 
/*  549 */       int resultCount = 0;
/*  550 */       String s = null;
/*      */       try
/*      */       {
/*  553 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  555 */         if (this._log.isDebugEnabled()) {
/*  556 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  558 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  561 */         int col = 1;
/*  562 */         stmt.setInt(col++, pk.getFormulaDeploymentLineId());
/*      */ 
/*  565 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  569 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  573 */         closeStatement(stmt);
/*  574 */         closeConnection();
/*      */ 
/*  576 */         if (timer != null)
/*  577 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*      */   {
/*  604 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  606 */     PreparedStatement stmt = null;
/*  607 */     ResultSet resultSet = null;
/*      */ 
/*  609 */     int itemCount = 0;
/*      */ 
/*  611 */     CubeFormulaEVO owningEVO = null;
/*  612 */     Iterator ownersIter = owners.iterator();
/*  613 */     while (ownersIter.hasNext())
/*      */     {
/*  615 */       owningEVO = (CubeFormulaEVO)ownersIter.next();
/*  616 */       owningEVO.setDeploymentsAllItemsLoaded(true);
/*      */     }
/*  618 */     ownersIter = owners.iterator();
/*  619 */     owningEVO = (CubeFormulaEVO)ownersIter.next();
/*  620 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  624 */       stmt = getConnection().prepareStatement("select FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID,FORMULA_DEPLOYMENT_LINE.LINE_INDEX,FORMULA_DEPLOYMENT_LINE.CONTEXT from FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA,FINANCE_CUBE where 1=1 and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID ,FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID");
/*      */ 
/*  626 */       int col = 1;
/*  627 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  629 */       resultSet = stmt.executeQuery();
/*      */ 
/*  632 */       while (resultSet.next())
/*      */       {
/*  634 */         itemCount++;
/*  635 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  640 */         while (this.mDetails.getCubeFormulaId() != owningEVO.getCubeFormulaId())
/*      */         {
/*  644 */           if (!ownersIter.hasNext())
/*      */           {
/*  646 */             this._log.debug("bulkGetAll", "can't find owning [CubeFormulaId=" + this.mDetails.getCubeFormulaId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  650 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  651 */             ownersIter = owners.iterator();
/*  652 */             while (ownersIter.hasNext())
/*      */             {
/*  654 */               owningEVO = (CubeFormulaEVO)ownersIter.next();
/*  655 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  657 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  659 */           owningEVO = (CubeFormulaEVO)ownersIter.next();
/*      */         }
/*  661 */         if (owningEVO.getDeployments() == null)
/*      */         {
/*  663 */           theseItems = new ArrayList();
/*  664 */           owningEVO.setDeployments(theseItems);
/*  665 */           owningEVO.setDeploymentsAllItemsLoaded(true);
/*      */         }
/*  667 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  670 */       if (timer != null) {
/*  671 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  674 */       if ((itemCount > 0) && (dependants.indexOf("<6>") > -1))
/*      */       {
/*  676 */         getFormulaDeploymentEntryDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*  678 */       if ((itemCount > 0) && (dependants.indexOf("<7>") > -1))
/*      */       {
/*  680 */         getFormulaDeploymentDtDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  684 */       throw handleSQLException("select FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID,FORMULA_DEPLOYMENT_LINE.LINE_INDEX,FORMULA_DEPLOYMENT_LINE.CONTEXT from FORMULA_DEPLOYMENT_LINE,CUBE_FORMULA,FINANCE_CUBE where 1=1 and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID and FINANCE_CUBE.MODEL_ID = ? order by  FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID ,FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  688 */       closeResultSet(resultSet);
/*  689 */       closeStatement(stmt);
/*  690 */       closeConnection();
/*      */ 
/*  692 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectCubeFormulaId, String dependants, Collection currentList)
/*      */   {
/*  717 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  718 */     PreparedStatement stmt = null;
/*  719 */     ResultSet resultSet = null;
/*      */ 
/*  721 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  725 */       stmt = getConnection().prepareStatement("select FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID,FORMULA_DEPLOYMENT_LINE.LINE_INDEX,FORMULA_DEPLOYMENT_LINE.CONTEXT from FORMULA_DEPLOYMENT_LINE where    CUBE_FORMULA_ID = ? ");
/*      */ 
/*  727 */       int col = 1;
/*  728 */       stmt.setInt(col++, selectCubeFormulaId);
/*      */ 
/*  730 */       resultSet = stmt.executeQuery();
/*      */ 
/*  732 */       while (resultSet.next())
/*      */       {
/*  734 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  737 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  740 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  743 */       if (currentList != null)
/*      */       {
/*  746 */         ListIterator iter = items.listIterator();
/*  747 */         FormulaDeploymentLineEVO currentEVO = null;
/*  748 */         FormulaDeploymentLineEVO newEVO = null;
/*  749 */         while (iter.hasNext())
/*      */         {
/*  751 */           newEVO = (FormulaDeploymentLineEVO)iter.next();
/*  752 */           Iterator iter2 = currentList.iterator();
/*  753 */           while (iter2.hasNext())
/*      */           {
/*  755 */             currentEVO = (FormulaDeploymentLineEVO)iter2.next();
/*  756 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  758 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  764 */         Iterator iter2 = currentList.iterator();
/*  765 */         while (iter2.hasNext())
/*      */         {
/*  767 */           currentEVO = (FormulaDeploymentLineEVO)iter2.next();
/*  768 */           if (currentEVO.insertPending()) {
/*  769 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  773 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  777 */       throw handleSQLException("select FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID,FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID,FORMULA_DEPLOYMENT_LINE.LINE_INDEX,FORMULA_DEPLOYMENT_LINE.CONTEXT from FORMULA_DEPLOYMENT_LINE where    CUBE_FORMULA_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  781 */       closeResultSet(resultSet);
/*  782 */       closeStatement(stmt);
/*  783 */       closeConnection();
/*      */ 
/*  785 */       if (timer != null) {
/*  786 */         timer.logDebug("getAll", " CubeFormulaId=" + selectCubeFormulaId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  791 */     return items;
/*      */   }
/*      */ 
/*      */   public FormulaDeploymentLineEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  810 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  813 */     if (this.mDetails == null) {
/*  814 */       doLoad(((FormulaDeploymentLineCK)paramCK).getFormulaDeploymentLinePK());
/*      */     }
/*  816 */     else if (!this.mDetails.getPK().equals(((FormulaDeploymentLineCK)paramCK).getFormulaDeploymentLinePK())) {
/*  817 */       doLoad(((FormulaDeploymentLineCK)paramCK).getFormulaDeploymentLinePK());
/*      */     }
/*      */ 
/*  820 */     if ((dependants.indexOf("<6>") > -1) && (!this.mDetails.isDeploymentEntriesAllItemsLoaded()))
/*      */     {
/*  825 */       this.mDetails.setDeploymentEntries(getFormulaDeploymentEntryDAO().getAll(this.mDetails.getFormulaDeploymentLineId(), dependants, this.mDetails.getDeploymentEntries()));
/*      */ 
/*  832 */       this.mDetails.setDeploymentEntriesAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  836 */     if ((dependants.indexOf("<7>") > -1) && (!this.mDetails.isDeploymentDataTypesAllItemsLoaded()))
/*      */     {
/*  841 */       this.mDetails.setDeploymentDataTypes(getFormulaDeploymentDtDAO().getAll(this.mDetails.getFormulaDeploymentLineId(), dependants, this.mDetails.getDeploymentDataTypes()));
/*      */ 
/*  848 */       this.mDetails.setDeploymentDataTypesAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  851 */     if ((paramCK instanceof FormulaDeploymentEntryCK))
/*      */     {
/*  853 */       if (this.mDetails.getDeploymentEntries() == null) {
/*  854 */         this.mDetails.loadDeploymentEntriesItem(getFormulaDeploymentEntryDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  857 */         FormulaDeploymentEntryPK pk = ((FormulaDeploymentEntryCK)paramCK).getFormulaDeploymentEntryPK();
/*  858 */         FormulaDeploymentEntryEVO evo = this.mDetails.getDeploymentEntriesItem(pk);
/*  859 */         if (evo == null) {
/*  860 */           this.mDetails.loadDeploymentEntriesItem(getFormulaDeploymentEntryDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*  864 */     else if ((paramCK instanceof FormulaDeploymentDtCK))
/*      */     {
/*  866 */       if (this.mDetails.getDeploymentDataTypes() == null) {
/*  867 */         this.mDetails.loadDeploymentDataTypesItem(getFormulaDeploymentDtDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  870 */         FormulaDeploymentDtPK pk = ((FormulaDeploymentDtCK)paramCK).getFormulaDeploymentDtPK();
/*  871 */         FormulaDeploymentDtEVO evo = this.mDetails.getDeploymentDataTypesItem(pk);
/*  872 */         if (evo == null) {
/*  873 */           this.mDetails.loadDeploymentDataTypesItem(getFormulaDeploymentDtDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  878 */     FormulaDeploymentLineEVO details = new FormulaDeploymentLineEVO();
/*  879 */     details = this.mDetails.deepClone();
/*      */ 
/*  881 */     if (timer != null) {
/*  882 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  884 */     return details;
/*      */   }
/*      */ 
/*      */   public FormulaDeploymentLineEVO getDetails(ModelCK paramCK, FormulaDeploymentLineEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  890 */     FormulaDeploymentLineEVO savedEVO = this.mDetails;
/*  891 */     this.mDetails = paramEVO;
/*  892 */     FormulaDeploymentLineEVO newEVO = getDetails(paramCK, dependants);
/*  893 */     this.mDetails = savedEVO;
/*  894 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public FormulaDeploymentLineEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  900 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  904 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  907 */     FormulaDeploymentLineEVO details = this.mDetails.deepClone();
/*      */ 
/*  909 */     if (timer != null) {
/*  910 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  912 */     return details;
/*      */   }
/*      */ 
/*      */   protected FormulaDeploymentEntryDAO getFormulaDeploymentEntryDAO()
/*      */   {
/*  921 */     if (this.mFormulaDeploymentEntryDAO == null)
/*      */     {
/*  923 */       if (this.mDataSource != null)
/*  924 */         this.mFormulaDeploymentEntryDAO = new FormulaDeploymentEntryDAO(this.mDataSource);
/*      */       else {
/*  926 */         this.mFormulaDeploymentEntryDAO = new FormulaDeploymentEntryDAO(getConnection());
/*      */       }
/*      */     }
/*  929 */     return this.mFormulaDeploymentEntryDAO;
/*      */   }
/*      */ 
/*      */   protected FormulaDeploymentDtDAO getFormulaDeploymentDtDAO()
/*      */   {
/*  938 */     if (this.mFormulaDeploymentDtDAO == null)
/*      */     {
/*  940 */       if (this.mDataSource != null)
/*  941 */         this.mFormulaDeploymentDtDAO = new FormulaDeploymentDtDAO(this.mDataSource);
/*      */       else {
/*  943 */         this.mFormulaDeploymentDtDAO = new FormulaDeploymentDtDAO(getConnection());
/*      */       }
/*      */     }
/*  946 */     return this.mFormulaDeploymentDtDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  951 */     return "FormulaDeploymentLine";
/*      */   }
/*      */ 
/*      */   public FormulaDeploymentLineRefImpl getRef(FormulaDeploymentLinePK paramFormulaDeploymentLinePK)
/*      */   {
/*  956 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  957 */     PreparedStatement stmt = null;
/*  958 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  961 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID,CUBE_FORMULA.CUBE_FORMULA_ID from FORMULA_DEPLOYMENT_LINE,MODEL,FINANCE_CUBE,CUBE_FORMULA where 1=1 and FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID = ? and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.CUBE_FORMULA_ID = FINANCE_CUBE.CUBE_FORMULA_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID");
/*  962 */       int col = 1;
/*  963 */       stmt.setInt(col++, paramFormulaDeploymentLinePK.getFormulaDeploymentLineId());
/*      */ 
/*  965 */       resultSet = stmt.executeQuery();
/*      */ 
/*  967 */       if (!resultSet.next()) {
/*  968 */         throw new RuntimeException(getEntityName() + " getRef " + paramFormulaDeploymentLinePK + " not found");
/*      */       }
/*  970 */       col = 2;
/*  971 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  975 */       FinanceCubePK newFinanceCubePK = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  979 */       CubeFormulaPK newCubeFormulaPK = new CubeFormulaPK(resultSet.getInt(col++));
/*      */ 
/*  983 */       String textFormulaDeploymentLine = "";
/*  984 */       FormulaDeploymentLineCK ckFormulaDeploymentLine = new FormulaDeploymentLineCK(newModelPK, newFinanceCubePK, newCubeFormulaPK, paramFormulaDeploymentLinePK);
/*      */ 
/*  991 */       FormulaDeploymentLineRefImpl localFormulaDeploymentLineRefImpl = new FormulaDeploymentLineRefImpl(ckFormulaDeploymentLine, textFormulaDeploymentLine);
/*      */       return localFormulaDeploymentLineRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  996 */       throw handleSQLException(paramFormulaDeploymentLinePK, "select 0,MODEL.MODEL_ID,FINANCE_CUBE.FINANCE_CUBE_ID,CUBE_FORMULA.CUBE_FORMULA_ID from FORMULA_DEPLOYMENT_LINE,MODEL,FINANCE_CUBE,CUBE_FORMULA where 1=1 and FORMULA_DEPLOYMENT_LINE.FORMULA_DEPLOYMENT_LINE_ID = ? and FORMULA_DEPLOYMENT_LINE.CUBE_FORMULA_ID = CUBE_FORMULA.CUBE_FORMULA_ID and CUBE_FORMULA.CUBE_FORMULA_ID = FINANCE_CUBE.CUBE_FORMULA_ID and FINANCE_CUBE.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1000 */       closeResultSet(resultSet);
/* 1001 */       closeStatement(stmt);
/* 1002 */       closeConnection();
/*      */ 
/* 1004 */       if (timer != null)
/* 1005 */         timer.logDebug("getRef", paramFormulaDeploymentLinePK); 
/* 1005 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1017 */     if (c == null)
/* 1018 */       return;
/* 1019 */     Iterator iter = c.iterator();
/* 1020 */     while (iter.hasNext())
/*      */     {
/* 1022 */       FormulaDeploymentLineEVO evo = (FormulaDeploymentLineEVO)iter.next();
/* 1023 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(FormulaDeploymentLineEVO evo, String dependants)
/*      */   {
/* 1037 */     if (evo.insertPending()) {
/* 1038 */       return;
/*      */     }
/* 1040 */     if (evo.getFormulaDeploymentLineId() < 1) {
/* 1041 */       return;
/*      */     }
/*      */ 
/* 1045 */     if (dependants.indexOf("<6>") > -1)
/*      */     {
/* 1048 */       if (!evo.isDeploymentEntriesAllItemsLoaded())
/*      */       {
/* 1050 */         evo.setDeploymentEntries(getFormulaDeploymentEntryDAO().getAll(evo.getFormulaDeploymentLineId(), dependants, evo.getDeploymentEntries()));
/*      */ 
/* 1057 */         evo.setDeploymentEntriesAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1062 */     if (dependants.indexOf("<7>") > -1)
/*      */     {
/* 1065 */       if (!evo.isDeploymentDataTypesAllItemsLoaded())
/*      */       {
/* 1067 */         evo.setDeploymentDataTypes(getFormulaDeploymentDtDAO().getAll(evo.getFormulaDeploymentLineId(), dependants, evo.getDeploymentDataTypes()));
/*      */ 
/* 1074 */         evo.setDeploymentDataTypesAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentLineDAO
 * JD-Core Version:    0.6.0
 */