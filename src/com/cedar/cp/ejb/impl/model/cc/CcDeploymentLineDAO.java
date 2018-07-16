/*      */ package com.cedar.cp.ejb.impl.model.cc;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentDataTypeCK;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentDataTypePK;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentEntryCK;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentEntryPK;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentLineCK;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentLinePK;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentLineRefImpl;
/*      */ import com.cedar.cp.dto.model.cc.CcDeploymentPK;
/*      */ import com.cedar.cp.dto.model.cc.CcMappingLineCK;
/*      */ import com.cedar.cp.dto.model.cc.CcMappingLinePK;
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
/*      */ public class CcDeploymentLineDAO extends AbstractDAO
/*      */ {
/*   47 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.LINE_INDEX,CC_DEPLOYMENT_LINE.CAL_LEVEL";
/*      */   protected static final String SQL_LOAD = " from CC_DEPLOYMENT_LINE where    CC_DEPLOYMENT_LINE_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into CC_DEPLOYMENT_LINE ( CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_ID,LINE_INDEX,CAL_LEVEL) values ( ?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update CC_DEPLOYMENT_LINE set CC_DEPLOYMENT_ID = ?,LINE_INDEX = ?,CAL_LEVEL = ? where    CC_DEPLOYMENT_LINE_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from CC_DEPLOYMENT_LINE where    CC_DEPLOYMENT_LINE_ID = ? ";
/*  484 */   private static String[][] SQL_DELETE_CHILDREN = { { "CC_DEPLOYMENT_ENTRY", "delete from CC_DEPLOYMENT_ENTRY where     CC_DEPLOYMENT_ENTRY.CC_DEPLOYMENT_LINE_ID = ? " }, { "CC_DEPLOYMENT_DATA_TYPE", "delete from CC_DEPLOYMENT_DATA_TYPE where     CC_DEPLOYMENT_DATA_TYPE.CC_DEPLOYMENT_LINE_ID = ? " }, { "CC_MAPPING_LINE", "delete from CC_MAPPING_LINE where     CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID = ? " } };
/*      */ 
/*  503 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "CC_MAPPING_ENTRY", "delete from CC_MAPPING_ENTRY CcMappingEntry where exists (select * from CC_MAPPING_ENTRY,CC_MAPPING_LINE,CC_DEPLOYMENT_LINE where     CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID = CC_MAPPING_LINE.CC_MAPPING_LINE_ID and CC_MAPPING_LINE.CC_DEPLOYMENT_LINE_ID = CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID and CcMappingEntry.CC_MAPPING_LINE_ID = CC_MAPPING_ENTRY.CC_MAPPING_LINE_ID " } };
/*      */ 
/*  518 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID ,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID";
/*      */   protected static final String SQL_GET_ALL = " from CC_DEPLOYMENT_LINE where    CC_DEPLOYMENT_ID = ? ";
/*      */   protected CcDeploymentEntryDAO mCcDeploymentEntryDAO;
/*      */   protected CcDeploymentDataTypeDAO mCcDeploymentDataTypeDAO;
/*      */   protected CcMappingLineDAO mCcMappingLineDAO;
/*      */   protected CcDeploymentLineEVO mDetails;
/*      */ 
/*      */   public CcDeploymentLineDAO(Connection connection)
/*      */   {
/*   54 */     super(connection);
/*      */   }
/*      */ 
/*      */   public CcDeploymentLineDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public CcDeploymentLineDAO(DataSource ds)
/*      */   {
/*   70 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected CcDeploymentLinePK getPK()
/*      */   {
/*   78 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(CcDeploymentLineEVO details)
/*      */   {
/*   87 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private CcDeploymentLineEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  102 */     int col = 1;
/*  103 */     CcDeploymentLineEVO evo = new CcDeploymentLineEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), null, null, null);
/*      */ 
/*  113 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(CcDeploymentLineEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  118 */     int col = startCol_;
/*  119 */     stmt_.setInt(col++, evo_.getCcDeploymentLineId());
/*  120 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(CcDeploymentLineEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  125 */     int col = startCol_;
/*  126 */     stmt_.setInt(col++, evo_.getCcDeploymentId());
/*  127 */     stmt_.setInt(col++, evo_.getLineIndex());
/*  128 */     stmt_.setInt(col++, evo_.getCalLevel());
/*  129 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(CcDeploymentLinePK pk)
/*      */     throws ValidationException
/*      */   {
/*  145 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  147 */     PreparedStatement stmt = null;
/*  148 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  152 */       stmt = getConnection().prepareStatement("select CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.LINE_INDEX,CC_DEPLOYMENT_LINE.CAL_LEVEL from CC_DEPLOYMENT_LINE where    CC_DEPLOYMENT_LINE_ID = ? ");
/*      */ 
/*  155 */       int col = 1;
/*  156 */       stmt.setInt(col++, pk.getCcDeploymentLineId());
/*      */ 
/*  158 */       resultSet = stmt.executeQuery();
/*      */ 
/*  160 */       if (!resultSet.next()) {
/*  161 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  164 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  165 */       if (this.mDetails.isModified())
/*  166 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  170 */       throw handleSQLException(pk, "select CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.LINE_INDEX,CC_DEPLOYMENT_LINE.CAL_LEVEL from CC_DEPLOYMENT_LINE where    CC_DEPLOYMENT_LINE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  174 */       closeResultSet(resultSet);
/*  175 */       closeStatement(stmt);
/*  176 */       closeConnection();
/*      */ 
/*  178 */       if (timer != null)
/*  179 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  204 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  205 */     this.mDetails.postCreateInit();
/*      */ 
/*  207 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  211 */       stmt = getConnection().prepareStatement("insert into CC_DEPLOYMENT_LINE ( CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_ID,LINE_INDEX,CAL_LEVEL) values ( ?,?,?,?)");
/*      */ 
/*  214 */       int col = 1;
/*  215 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  216 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  219 */       int resultCount = stmt.executeUpdate();
/*  220 */       if (resultCount != 1)
/*      */       {
/*  222 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  225 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  229 */       throw handleSQLException(this.mDetails.getPK(), "insert into CC_DEPLOYMENT_LINE ( CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_ID,LINE_INDEX,CAL_LEVEL) values ( ?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  233 */       closeStatement(stmt);
/*  234 */       closeConnection();
/*      */ 
/*  236 */       if (timer != null) {
/*  237 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  243 */       getCcDeploymentEntryDAO().update(this.mDetails.getCCDeploymentEntriesMap());
/*      */ 
/*  245 */       getCcDeploymentDataTypeDAO().update(this.mDetails.getCCDeploymentDataTypesMap());
/*      */ 
/*  247 */       getCcMappingLineDAO().update(this.mDetails.getCCMappingLinesMap());
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
/*  275 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  279 */     PreparedStatement stmt = null;
/*      */ 
/*  281 */     boolean mainChanged = this.mDetails.isModified();
/*  282 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  286 */       if (getCcDeploymentEntryDAO().update(this.mDetails.getCCDeploymentEntriesMap())) {
/*  287 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  290 */       if (getCcDeploymentDataTypeDAO().update(this.mDetails.getCCDeploymentDataTypesMap())) {
/*  291 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  294 */       if (getCcMappingLineDAO().update(this.mDetails.getCCMappingLinesMap())) {
/*  295 */         dependantChanged = true;
/*      */       }
/*  297 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  299 */         stmt = getConnection().prepareStatement("update CC_DEPLOYMENT_LINE set CC_DEPLOYMENT_ID = ?,LINE_INDEX = ?,CAL_LEVEL = ? where    CC_DEPLOYMENT_LINE_ID = ? ");
/*      */ 
/*  302 */         int col = 1;
/*  303 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  304 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  307 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  310 */         if (resultCount != 1) {
/*  311 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  314 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  323 */       throw handleSQLException(getPK(), "update CC_DEPLOYMENT_LINE set CC_DEPLOYMENT_ID = ?,LINE_INDEX = ?,CAL_LEVEL = ? where    CC_DEPLOYMENT_LINE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  327 */       closeStatement(stmt);
/*  328 */       closeConnection();
/*      */ 
/*  330 */       if ((timer != null) && (
/*  331 */         (mainChanged) || (dependantChanged)))
/*  332 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  351 */     if (items == null) {
/*  352 */       return false;
/*      */     }
/*  354 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  355 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  357 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  361 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  362 */       while (iter3.hasNext())
/*      */       {
/*  364 */         this.mDetails = ((CcDeploymentLineEVO)iter3.next());
/*  365 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  367 */         somethingChanged = true;
/*      */ 
/*  370 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  374 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  375 */       while (iter2.hasNext())
/*      */       {
/*  377 */         this.mDetails = ((CcDeploymentLineEVO)iter2.next());
/*      */ 
/*  380 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  382 */         somethingChanged = true;
/*      */ 
/*  385 */         if (deleteStmt == null) {
/*  386 */           deleteStmt = getConnection().prepareStatement("delete from CC_DEPLOYMENT_LINE where    CC_DEPLOYMENT_LINE_ID = ? ");
/*      */         }
/*      */ 
/*  389 */         int col = 1;
/*  390 */         deleteStmt.setInt(col++, this.mDetails.getCcDeploymentLineId());
/*      */ 
/*  392 */         if (this._log.isDebugEnabled()) {
/*  393 */           this._log.debug("update", "CcDeploymentLine deleting CcDeploymentLineId=" + this.mDetails.getCcDeploymentLineId());
/*      */         }
/*      */ 
/*  398 */         deleteStmt.addBatch();
/*      */ 
/*  401 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  406 */       if (deleteStmt != null)
/*      */       {
/*  408 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  410 */         deleteStmt.executeBatch();
/*      */ 
/*  412 */         if (timer2 != null) {
/*  413 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  417 */       Iterator iter1 = items.values().iterator();
/*  418 */       while (iter1.hasNext())
/*      */       {
/*  420 */         this.mDetails = ((CcDeploymentLineEVO)iter1.next());
/*      */ 
/*  422 */         if (this.mDetails.insertPending())
/*      */         {
/*  424 */           somethingChanged = true;
/*  425 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  428 */         if (this.mDetails.isModified())
/*      */         {
/*  430 */           somethingChanged = true;
/*  431 */           doStore(); continue;
/*      */         }
/*      */ 
/*  435 */         if (this.mDetails.deletePending())
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  441 */         if (getCcDeploymentEntryDAO().update(this.mDetails.getCCDeploymentEntriesMap())) {
/*  442 */           somethingChanged = true;
/*      */         }
/*      */ 
/*  445 */         if (getCcDeploymentDataTypeDAO().update(this.mDetails.getCCDeploymentDataTypesMap())) {
/*  446 */           somethingChanged = true;
/*      */         }
/*      */ 
/*  449 */         if (getCcMappingLineDAO().update(this.mDetails.getCCMappingLinesMap())) {
/*  450 */           somethingChanged = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  462 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  466 */       throw handleSQLException("delete from CC_DEPLOYMENT_LINE where    CC_DEPLOYMENT_LINE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  470 */       if (deleteStmt != null)
/*      */       {
/*  472 */         closeStatement(deleteStmt);
/*  473 */         closeConnection();
/*      */       }
/*      */ 
/*  476 */       this.mDetails = null;
/*      */ 
/*  478 */       if ((somethingChanged) && 
/*  479 */         (timer != null))
/*  480 */         timer.logDebug("update", "collection"); 
/*  480 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(CcDeploymentLinePK pk)
/*      */   {
/*  527 */     Set emptyStrings = Collections.emptySet();
/*  528 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(CcDeploymentLinePK pk, Set<String> exclusionTables)
/*      */   {
/*  534 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  536 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  538 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  540 */       PreparedStatement stmt = null;
/*      */ 
/*  542 */       int resultCount = 0;
/*  543 */       String s = null;
/*      */       try
/*      */       {
/*  546 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  548 */         if (this._log.isDebugEnabled()) {
/*  549 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  551 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  554 */         int col = 1;
/*  555 */         stmt.setInt(col++, pk.getCcDeploymentLineId());
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
/*  569 */         if (timer != null) {
/*  570 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  574 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  576 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  578 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  580 */       PreparedStatement stmt = null;
/*      */ 
/*  582 */       int resultCount = 0;
/*  583 */       String s = null;
/*      */       try
/*      */       {
/*  586 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  588 */         if (this._log.isDebugEnabled()) {
/*  589 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  591 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  594 */         int col = 1;
/*  595 */         stmt.setInt(col++, pk.getCcDeploymentLineId());
/*      */ 
/*  598 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  602 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  606 */         closeStatement(stmt);
/*  607 */         closeConnection();
/*      */ 
/*  609 */         if (timer != null)
/*  610 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*      */   {
/*  634 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  636 */     PreparedStatement stmt = null;
/*  637 */     ResultSet resultSet = null;
/*      */ 
/*  639 */     int itemCount = 0;
/*      */ 
/*  641 */     CcDeploymentEVO owningEVO = null;
/*  642 */     Iterator ownersIter = owners.iterator();
/*  643 */     while (ownersIter.hasNext())
/*      */     {
/*  645 */       owningEVO = (CcDeploymentEVO)ownersIter.next();
/*  646 */       owningEVO.setCCDeploymentLinesAllItemsLoaded(true);
/*      */     }
/*  648 */     ownersIter = owners.iterator();
/*  649 */     owningEVO = (CcDeploymentEVO)ownersIter.next();
/*  650 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  654 */       stmt = getConnection().prepareStatement("select CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.LINE_INDEX,CC_DEPLOYMENT_LINE.CAL_LEVEL from CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID ,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID");
/*      */ 
/*  656 */       int col = 1;
/*  657 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  659 */       resultSet = stmt.executeQuery();
/*      */ 
/*  662 */       while (resultSet.next())
/*      */       {
/*  664 */         itemCount++;
/*  665 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  670 */         while (this.mDetails.getCcDeploymentId() != owningEVO.getCcDeploymentId())
/*      */         {
/*  674 */           if (!ownersIter.hasNext())
/*      */           {
/*  676 */             this._log.debug("bulkGetAll", "can't find owning [CcDeploymentId=" + this.mDetails.getCcDeploymentId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  680 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  681 */             ownersIter = owners.iterator();
/*  682 */             while (ownersIter.hasNext())
/*      */             {
/*  684 */               owningEVO = (CcDeploymentEVO)ownersIter.next();
/*  685 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  687 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  689 */           owningEVO = (CcDeploymentEVO)ownersIter.next();
/*      */         }
/*  691 */         if (owningEVO.getCCDeploymentLines() == null)
/*      */         {
/*  693 */           theseItems = new ArrayList();
/*  694 */           owningEVO.setCCDeploymentLines(theseItems);
/*  695 */           owningEVO.setCCDeploymentLinesAllItemsLoaded(true);
/*      */         }
/*  697 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  700 */       if (timer != null) {
/*  701 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  704 */       if ((itemCount > 0) && (dependants.indexOf("<43>") > -1))
/*      */       {
/*  706 */         getCcDeploymentEntryDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*  708 */       if ((itemCount > 0) && (dependants.indexOf("<44>") > -1))
/*      */       {
/*  710 */         getCcDeploymentDataTypeDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*  712 */       if ((itemCount > 0) && (dependants.indexOf("<45>") > -1))
/*      */       {
/*  714 */         getCcMappingLineDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  718 */       throw handleSQLException("select CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.LINE_INDEX,CC_DEPLOYMENT_LINE.CAL_LEVEL from CC_DEPLOYMENT_LINE,CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.MODEL_ID = ? order by  CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID ,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  722 */       closeResultSet(resultSet);
/*  723 */       closeStatement(stmt);
/*  724 */       closeConnection();
/*      */ 
/*  726 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectCcDeploymentId, String dependants, Collection currentList)
/*      */   {
/*  751 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  752 */     PreparedStatement stmt = null;
/*  753 */     ResultSet resultSet = null;
/*      */ 
/*  755 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  759 */       stmt = getConnection().prepareStatement("select CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.LINE_INDEX,CC_DEPLOYMENT_LINE.CAL_LEVEL from CC_DEPLOYMENT_LINE where    CC_DEPLOYMENT_ID = ? ");
/*      */ 
/*  761 */       int col = 1;
/*  762 */       stmt.setInt(col++, selectCcDeploymentId);
/*      */ 
/*  764 */       resultSet = stmt.executeQuery();
/*      */ 
/*  766 */       while (resultSet.next())
/*      */       {
/*  768 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  771 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  774 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  777 */       if (currentList != null)
/*      */       {
/*  780 */         ListIterator iter = items.listIterator();
/*  781 */         CcDeploymentLineEVO currentEVO = null;
/*  782 */         CcDeploymentLineEVO newEVO = null;
/*  783 */         while (iter.hasNext())
/*      */         {
/*  785 */           newEVO = (CcDeploymentLineEVO)iter.next();
/*  786 */           Iterator iter2 = currentList.iterator();
/*  787 */           while (iter2.hasNext())
/*      */           {
/*  789 */             currentEVO = (CcDeploymentLineEVO)iter2.next();
/*  790 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  792 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  798 */         Iterator iter2 = currentList.iterator();
/*  799 */         while (iter2.hasNext())
/*      */         {
/*  801 */           currentEVO = (CcDeploymentLineEVO)iter2.next();
/*  802 */           if (currentEVO.insertPending()) {
/*  803 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  807 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  811 */       throw handleSQLException("select CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID,CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID,CC_DEPLOYMENT_LINE.LINE_INDEX,CC_DEPLOYMENT_LINE.CAL_LEVEL from CC_DEPLOYMENT_LINE where    CC_DEPLOYMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  815 */       closeResultSet(resultSet);
/*  816 */       closeStatement(stmt);
/*  817 */       closeConnection();
/*      */ 
/*  819 */       if (timer != null) {
/*  820 */         timer.logDebug("getAll", " CcDeploymentId=" + selectCcDeploymentId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  825 */     return items;
/*      */   }
/*      */ 
/*      */   public CcDeploymentLineEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  848 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  851 */     if (this.mDetails == null) {
/*  852 */       doLoad(((CcDeploymentLineCK)paramCK).getCcDeploymentLinePK());
/*      */     }
/*  854 */     else if (!this.mDetails.getPK().equals(((CcDeploymentLineCK)paramCK).getCcDeploymentLinePK())) {
/*  855 */       doLoad(((CcDeploymentLineCK)paramCK).getCcDeploymentLinePK());
/*      */     }
/*      */ 
/*  858 */     if ((dependants.indexOf("<43>") > -1) && (!this.mDetails.isCCDeploymentEntriesAllItemsLoaded()))
/*      */     {
/*  863 */       this.mDetails.setCCDeploymentEntries(getCcDeploymentEntryDAO().getAll(this.mDetails.getCcDeploymentLineId(), dependants, this.mDetails.getCCDeploymentEntries()));
/*      */ 
/*  870 */       this.mDetails.setCCDeploymentEntriesAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  874 */     if ((dependants.indexOf("<44>") > -1) && (!this.mDetails.isCCDeploymentDataTypesAllItemsLoaded()))
/*      */     {
/*  879 */       this.mDetails.setCCDeploymentDataTypes(getCcDeploymentDataTypeDAO().getAll(this.mDetails.getCcDeploymentLineId(), dependants, this.mDetails.getCCDeploymentDataTypes()));
/*      */ 
/*  886 */       this.mDetails.setCCDeploymentDataTypesAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  890 */     if ((dependants.indexOf("<45>") > -1) && (!this.mDetails.isCCMappingLinesAllItemsLoaded()))
/*      */     {
/*  895 */       this.mDetails.setCCMappingLines(getCcMappingLineDAO().getAll(this.mDetails.getCcDeploymentLineId(), dependants, this.mDetails.getCCMappingLines()));
/*      */ 
/*  902 */       this.mDetails.setCCMappingLinesAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  905 */     if ((paramCK instanceof CcDeploymentEntryCK))
/*      */     {
/*  907 */       if (this.mDetails.getCCDeploymentEntries() == null) {
/*  908 */         this.mDetails.loadCCDeploymentEntriesItem(getCcDeploymentEntryDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  911 */         CcDeploymentEntryPK pk = ((CcDeploymentEntryCK)paramCK).getCcDeploymentEntryPK();
/*  912 */         CcDeploymentEntryEVO evo = this.mDetails.getCCDeploymentEntriesItem(pk);
/*  913 */         if (evo == null) {
/*  914 */           this.mDetails.loadCCDeploymentEntriesItem(getCcDeploymentEntryDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*  918 */     else if ((paramCK instanceof CcDeploymentDataTypeCK))
/*      */     {
/*  920 */       if (this.mDetails.getCCDeploymentDataTypes() == null) {
/*  921 */         this.mDetails.loadCCDeploymentDataTypesItem(getCcDeploymentDataTypeDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  924 */         CcDeploymentDataTypePK pk = ((CcDeploymentDataTypeCK)paramCK).getCcDeploymentDataTypePK();
/*  925 */         CcDeploymentDataTypeEVO evo = this.mDetails.getCCDeploymentDataTypesItem(pk);
/*  926 */         if (evo == null) {
/*  927 */           this.mDetails.loadCCDeploymentDataTypesItem(getCcDeploymentDataTypeDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*  931 */     else if ((paramCK instanceof CcMappingLineCK))
/*      */     {
/*  933 */       if (this.mDetails.getCCMappingLines() == null) {
/*  934 */         this.mDetails.loadCCMappingLinesItem(getCcMappingLineDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  937 */         CcMappingLinePK pk = ((CcMappingLineCK)paramCK).getCcMappingLinePK();
/*  938 */         CcMappingLineEVO evo = this.mDetails.getCCMappingLinesItem(pk);
/*  939 */         if (evo == null)
/*  940 */           this.mDetails.loadCCMappingLinesItem(getCcMappingLineDAO().getDetails(paramCK, dependants));
/*      */         else {
/*  942 */           getCcMappingLineDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  947 */     CcDeploymentLineEVO details = new CcDeploymentLineEVO();
/*  948 */     details = this.mDetails.deepClone();
/*      */ 
/*  950 */     if (timer != null) {
/*  951 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  953 */     return details;
/*      */   }
/*      */ 
/*      */   public CcDeploymentLineEVO getDetails(ModelCK paramCK, CcDeploymentLineEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  959 */     CcDeploymentLineEVO savedEVO = this.mDetails;
/*  960 */     this.mDetails = paramEVO;
/*  961 */     CcDeploymentLineEVO newEVO = getDetails(paramCK, dependants);
/*  962 */     this.mDetails = savedEVO;
/*  963 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public CcDeploymentLineEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  969 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  973 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  976 */     CcDeploymentLineEVO details = this.mDetails.deepClone();
/*      */ 
/*  978 */     if (timer != null) {
/*  979 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  981 */     return details;
/*      */   }
/*      */ 
/*      */   protected CcDeploymentEntryDAO getCcDeploymentEntryDAO()
/*      */   {
/*  990 */     if (this.mCcDeploymentEntryDAO == null)
/*      */     {
/*  992 */       if (this.mDataSource != null)
/*  993 */         this.mCcDeploymentEntryDAO = new CcDeploymentEntryDAO(this.mDataSource);
/*      */       else {
/*  995 */         this.mCcDeploymentEntryDAO = new CcDeploymentEntryDAO(getConnection());
/*      */       }
/*      */     }
/*  998 */     return this.mCcDeploymentEntryDAO;
/*      */   }
/*      */ 
/*      */   protected CcDeploymentDataTypeDAO getCcDeploymentDataTypeDAO()
/*      */   {
/* 1007 */     if (this.mCcDeploymentDataTypeDAO == null)
/*      */     {
/* 1009 */       if (this.mDataSource != null)
/* 1010 */         this.mCcDeploymentDataTypeDAO = new CcDeploymentDataTypeDAO(this.mDataSource);
/*      */       else {
/* 1012 */         this.mCcDeploymentDataTypeDAO = new CcDeploymentDataTypeDAO(getConnection());
/*      */       }
/*      */     }
/* 1015 */     return this.mCcDeploymentDataTypeDAO;
/*      */   }
/*      */ 
/*      */   protected CcMappingLineDAO getCcMappingLineDAO()
/*      */   {
/* 1024 */     if (this.mCcMappingLineDAO == null)
/*      */     {
/* 1026 */       if (this.mDataSource != null)
/* 1027 */         this.mCcMappingLineDAO = new CcMappingLineDAO(this.mDataSource);
/*      */       else {
/* 1029 */         this.mCcMappingLineDAO = new CcMappingLineDAO(getConnection());
/*      */       }
/*      */     }
/* 1032 */     return this.mCcMappingLineDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1037 */     return "CcDeploymentLine";
/*      */   }
/*      */ 
/*      */   public CcDeploymentLineRefImpl getRef(CcDeploymentLinePK paramCcDeploymentLinePK)
/*      */   {
/* 1042 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1043 */     PreparedStatement stmt = null;
/* 1044 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1047 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,CC_DEPLOYMENT.CC_DEPLOYMENT_ID from CC_DEPLOYMENT_LINE,MODEL,CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID = ? and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.CC_DEPLOYMENT_ID = MODEL.CC_DEPLOYMENT_ID");
/* 1048 */       int col = 1;
/* 1049 */       stmt.setInt(col++, paramCcDeploymentLinePK.getCcDeploymentLineId());
/*      */ 
/* 1051 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1053 */       if (!resultSet.next()) {
/* 1054 */         throw new RuntimeException(getEntityName() + " getRef " + paramCcDeploymentLinePK + " not found");
/*      */       }
/* 1056 */       col = 2;
/* 1057 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1061 */       CcDeploymentPK newCcDeploymentPK = new CcDeploymentPK(resultSet.getInt(col++));
/*      */ 
/* 1065 */       String textCcDeploymentLine = "";
/* 1066 */       CcDeploymentLineCK ckCcDeploymentLine = new CcDeploymentLineCK(newModelPK, newCcDeploymentPK, paramCcDeploymentLinePK);
/*      */ 
/* 1072 */       CcDeploymentLineRefImpl localCcDeploymentLineRefImpl = new CcDeploymentLineRefImpl(ckCcDeploymentLine, textCcDeploymentLine);
/*      */       return localCcDeploymentLineRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1077 */       throw handleSQLException(paramCcDeploymentLinePK, "select 0,MODEL.MODEL_ID,CC_DEPLOYMENT.CC_DEPLOYMENT_ID from CC_DEPLOYMENT_LINE,MODEL,CC_DEPLOYMENT where 1=1 and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_LINE_ID = ? and CC_DEPLOYMENT_LINE.CC_DEPLOYMENT_ID = CC_DEPLOYMENT.CC_DEPLOYMENT_ID and CC_DEPLOYMENT.CC_DEPLOYMENT_ID = MODEL.CC_DEPLOYMENT_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1081 */       closeResultSet(resultSet);
/* 1082 */       closeStatement(stmt);
/* 1083 */       closeConnection();
/*      */ 
/* 1085 */       if (timer != null)
/* 1086 */         timer.logDebug("getRef", paramCcDeploymentLinePK); 
/* 1086 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1098 */     if (c == null)
/* 1099 */       return;
/* 1100 */     Iterator iter = c.iterator();
/* 1101 */     while (iter.hasNext())
/*      */     {
/* 1103 */       CcDeploymentLineEVO evo = (CcDeploymentLineEVO)iter.next();
/* 1104 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(CcDeploymentLineEVO evo, String dependants)
/*      */   {
/* 1118 */     if (evo.insertPending()) {
/* 1119 */       return;
/*      */     }
/* 1121 */     if (evo.getCcDeploymentLineId() < 1) {
/* 1122 */       return;
/*      */     }
/*      */ 
/* 1126 */     if (dependants.indexOf("<43>") > -1)
/*      */     {
/* 1129 */       if (!evo.isCCDeploymentEntriesAllItemsLoaded())
/*      */       {
/* 1131 */         evo.setCCDeploymentEntries(getCcDeploymentEntryDAO().getAll(evo.getCcDeploymentLineId(), dependants, evo.getCCDeploymentEntries()));
/*      */ 
/* 1138 */         evo.setCCDeploymentEntriesAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1143 */     if (dependants.indexOf("<44>") > -1)
/*      */     {
/* 1146 */       if (!evo.isCCDeploymentDataTypesAllItemsLoaded())
/*      */       {
/* 1148 */         evo.setCCDeploymentDataTypes(getCcDeploymentDataTypeDAO().getAll(evo.getCcDeploymentLineId(), dependants, evo.getCCDeploymentDataTypes()));
/*      */ 
/* 1155 */         evo.setCCDeploymentDataTypesAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1160 */     if ((dependants.indexOf("<45>") > -1) || (dependants.indexOf("<46>") > -1))
/*      */     {
/* 1164 */       if (!evo.isCCMappingLinesAllItemsLoaded())
/*      */       {
/* 1166 */         evo.setCCMappingLines(getCcMappingLineDAO().getAll(evo.getCcDeploymentLineId(), dependants, evo.getCCMappingLines()));
/*      */ 
/* 1173 */         evo.setCCMappingLinesAllItemsLoaded(true);
/*      */       }
/* 1175 */       getCcMappingLineDAO().getDependants(evo.getCCMappingLines(), dependants);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.cc.CcDeploymentLineDAO
 * JD-Core Version:    0.6.0
 */