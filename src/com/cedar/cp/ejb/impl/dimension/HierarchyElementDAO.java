/*      */ package com.cedar.cp.ejb.impl.dimension;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.dimension.DimensionCK;
/*      */ import com.cedar.cp.dto.dimension.DimensionPK;
/*      */ import com.cedar.cp.dto.dimension.DimensionRefImpl;
/*      */ import com.cedar.cp.dto.dimension.HierarachyElementELO;
/*      */ import com.cedar.cp.dto.dimension.HierarchyCK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyElementCK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyElementFeedCK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyElementPK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyElementRefImpl;
/*      */ import com.cedar.cp.dto.dimension.HierarchyPK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyRefImpl;
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
/*      */ public class HierarchyElementDAO extends AbstractDAO
/*      */ {
/*   32 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT.HIERARCHY_ID,HIERARCHY_ELEMENT.PARENT_ID,HIERARCHY_ELEMENT.CHILD_INDEX,HIERARCHY_ELEMENT.VIS_ID,HIERARCHY_ELEMENT.DESCRIPTION,HIERARCHY_ELEMENT.CREDIT_DEBIT,HIERARCHY_ELEMENT.AUG_PARENT_ID,HIERARCHY_ELEMENT.AUG_CHILD_INDEX,HIERARCHY_ELEMENT.AUG_CREDIT_DEBIT,HIERARCHY_ELEMENT.CAL_ELEM_TYPE";
/*      */   protected static final String SQL_LOAD = " from HIERARCHY_ELEMENT where    HIERARCHY_ELEMENT_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into HIERARCHY_ELEMENT ( HIERARCHY_ELEMENT_ID,HIERARCHY_ID,PARENT_ID,CHILD_INDEX,VIS_ID,DESCRIPTION,CREDIT_DEBIT,AUG_PARENT_ID,AUG_CHILD_INDEX,AUG_CREDIT_DEBIT,CAL_ELEM_TYPE) values ( ?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update HIERARCHY_ELEMENT set HIERARCHY_ID = ?,PARENT_ID = ?,CHILD_INDEX = ?,VIS_ID = ?,DESCRIPTION = ?,CREDIT_DEBIT = ?,AUG_PARENT_ID = ?,AUG_CHILD_INDEX = ?,AUG_CREDIT_DEBIT = ?,CAL_ELEM_TYPE = ? where    HIERARCHY_ELEMENT_ID = ? ";
/*  353 */   protected static String SQL_HIERARACHY_ELEMENT = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID      ,HIERARCHY_ELEMENT.VIS_ID from HIERARCHY_ELEMENT    ,DIMENSION    ,HIERARCHY where 1=1   and HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID   and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  HIERARCHY_ELEMENT_ID = ?";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from HIERARCHY_ELEMENT where    HIERARCHY_ELEMENT_ID = ? ";
/*  624 */   private static String[][] SQL_DELETE_CHILDREN = { { "HIERARCHY_ELEMENT_FEED", "delete from HIERARCHY_ELEMENT_FEED where     HIERARCHY_ELEMENT_FEED.HIERARCHY_ELEMENT_ID = ? " } };
/*      */ 
/*  633 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  637 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from HIERARCHY_ELEMENT,HIERARCHY where 1=1 and HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.DIMENSION_ID = ? order by  HIERARCHY_ELEMENT.HIERARCHY_ID ,HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID";
/*      */   protected static final String SQL_GET_ALL = " from HIERARCHY_ELEMENT where    HIERARCHY_ID = ? ";
/*      */   protected HierarchyElementFeedDAO mHierarchyElementFeedDAO;
/*      */   protected HierarchyElementEVO mDetails;
/*      */ 
/*      */   public HierarchyElementDAO(Connection connection)
/*      */   {
/*   39 */     super(connection);
/*      */   }
/*      */ 
/*      */   public HierarchyElementDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public HierarchyElementDAO(DataSource ds)
/*      */   {
/*   55 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected HierarchyElementPK getPK()
/*      */   {
/*   63 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(HierarchyElementEVO details)
/*      */   {
/*   72 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private HierarchyElementEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   94 */     int col = 1;
/*   95 */     HierarchyElementEVO evo = new HierarchyElementEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedIntegerFromJdbc(resultSet_, col++), null);
/*      */ 
/*  110 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(HierarchyElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  115 */     int col = startCol_;
/*  116 */     stmt_.setInt(col++, evo_.getHierarchyElementId());
/*  117 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(HierarchyElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  122 */     int col = startCol_;
/*  123 */     stmt_.setInt(col++, evo_.getHierarchyId());
/*  124 */     stmt_.setInt(col++, evo_.getParentId());
/*  125 */     stmt_.setInt(col++, evo_.getChildIndex());
/*  126 */     stmt_.setString(col++, evo_.getVisId());
/*  127 */     stmt_.setString(col++, evo_.getDescription());
/*  128 */     stmt_.setInt(col++, evo_.getCreditDebit());
/*  129 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getAugParentId());
/*  130 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getAugChildIndex());
/*  131 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getAugCreditDebit());
/*  132 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getCalElemType());
/*  133 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(HierarchyElementPK pk)
/*      */     throws ValidationException
/*      */   {
/*  149 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  151 */     PreparedStatement stmt = null;
/*  152 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  156 */       stmt = getConnection().prepareStatement("select HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT.HIERARCHY_ID,HIERARCHY_ELEMENT.PARENT_ID,HIERARCHY_ELEMENT.CHILD_INDEX,HIERARCHY_ELEMENT.VIS_ID,HIERARCHY_ELEMENT.DESCRIPTION,HIERARCHY_ELEMENT.CREDIT_DEBIT,HIERARCHY_ELEMENT.AUG_PARENT_ID,HIERARCHY_ELEMENT.AUG_CHILD_INDEX,HIERARCHY_ELEMENT.AUG_CREDIT_DEBIT,HIERARCHY_ELEMENT.CAL_ELEM_TYPE from HIERARCHY_ELEMENT where    HIERARCHY_ELEMENT_ID = ? ");
/*      */ 
/*  159 */       int col = 1;
/*  160 */       stmt.setInt(col++, pk.getHierarchyElementId());
/*      */ 
/*  162 */       resultSet = stmt.executeQuery();
/*      */ 
/*  164 */       if (!resultSet.next()) {
/*  165 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  168 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  169 */       if (this.mDetails.isModified())
/*  170 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  174 */       throw handleSQLException(pk, "select HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT.HIERARCHY_ID,HIERARCHY_ELEMENT.PARENT_ID,HIERARCHY_ELEMENT.CHILD_INDEX,HIERARCHY_ELEMENT.VIS_ID,HIERARCHY_ELEMENT.DESCRIPTION,HIERARCHY_ELEMENT.CREDIT_DEBIT,HIERARCHY_ELEMENT.AUG_PARENT_ID,HIERARCHY_ELEMENT.AUG_CHILD_INDEX,HIERARCHY_ELEMENT.AUG_CREDIT_DEBIT,HIERARCHY_ELEMENT.CAL_ELEM_TYPE from HIERARCHY_ELEMENT where    HIERARCHY_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  178 */       closeResultSet(resultSet);
/*  179 */       closeStatement(stmt);
/*  180 */       closeConnection();
/*      */ 
/*  182 */       if (timer != null)
/*  183 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  222 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  223 */     this.mDetails.postCreateInit();
/*      */ 
/*  225 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  229 */       stmt = getConnection().prepareStatement("insert into HIERARCHY_ELEMENT ( HIERARCHY_ELEMENT_ID,HIERARCHY_ID,PARENT_ID,CHILD_INDEX,VIS_ID,DESCRIPTION,CREDIT_DEBIT,AUG_PARENT_ID,AUG_CHILD_INDEX,AUG_CREDIT_DEBIT,CAL_ELEM_TYPE) values ( ?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  232 */       int col = 1;
/*  233 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  234 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  237 */       int resultCount = stmt.executeUpdate();
/*  238 */       if (resultCount != 1)
/*      */       {
/*  240 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  243 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  247 */       throw handleSQLException(this.mDetails.getPK(), "insert into HIERARCHY_ELEMENT ( HIERARCHY_ELEMENT_ID,HIERARCHY_ID,PARENT_ID,CHILD_INDEX,VIS_ID,DESCRIPTION,CREDIT_DEBIT,AUG_PARENT_ID,AUG_CHILD_INDEX,AUG_CREDIT_DEBIT,CAL_ELEM_TYPE) values ( ?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  251 */       closeStatement(stmt);
/*  252 */       closeConnection();
/*      */ 
/*  254 */       if (timer != null) {
/*  255 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  261 */       getHierarchyElementFeedDAO().update(this.mDetails.getFeederElementsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  267 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  296 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  300 */     PreparedStatement stmt = null;
/*      */ 
/*  302 */     boolean mainChanged = this.mDetails.isModified();
/*  303 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  307 */       if (getHierarchyElementFeedDAO().update(this.mDetails.getFeederElementsMap())) {
/*  308 */         dependantChanged = true;
/*      */       }
/*  310 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  312 */         stmt = getConnection().prepareStatement("update HIERARCHY_ELEMENT set HIERARCHY_ID = ?,PARENT_ID = ?,CHILD_INDEX = ?,VIS_ID = ?,DESCRIPTION = ?,CREDIT_DEBIT = ?,AUG_PARENT_ID = ?,AUG_CHILD_INDEX = ?,AUG_CREDIT_DEBIT = ?,CAL_ELEM_TYPE = ? where    HIERARCHY_ELEMENT_ID = ? ");
/*      */ 
/*  315 */         int col = 1;
/*  316 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  317 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  320 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  323 */         if (resultCount != 1) {
/*  324 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  327 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  336 */       throw handleSQLException(getPK(), "update HIERARCHY_ELEMENT set HIERARCHY_ID = ?,PARENT_ID = ?,CHILD_INDEX = ?,VIS_ID = ?,DESCRIPTION = ?,CREDIT_DEBIT = ?,AUG_PARENT_ID = ?,AUG_CHILD_INDEX = ?,AUG_CREDIT_DEBIT = ?,CAL_ELEM_TYPE = ? where    HIERARCHY_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  340 */       closeStatement(stmt);
/*  341 */       closeConnection();
/*      */ 
/*  343 */       if ((timer != null) && (
/*  344 */         (mainChanged) || (dependantChanged)))
/*  345 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public HierarachyElementELO getHierarachyElement(int param1)
/*      */   {
/*  390 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  391 */     PreparedStatement stmt = null;
/*  392 */     ResultSet resultSet = null;
/*  393 */     HierarachyElementELO results = new HierarachyElementELO();
/*      */     try
/*      */     {
/*  396 */       stmt = getConnection().prepareStatement(SQL_HIERARACHY_ELEMENT);
/*  397 */       int col = 1;
/*  398 */       stmt.setInt(col++, param1);
/*  399 */       resultSet = stmt.executeQuery();
/*  400 */       while (resultSet.next())
/*      */       {
/*  402 */         col = 2;
/*      */ 
/*  405 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  408 */         String textDimension = resultSet.getString(col++);
/*  409 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/*  411 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/*  414 */         String textHierarchy = resultSet.getString(col++);
/*      */ 
/*  417 */         HierarchyElementPK pkHierarchyElement = new HierarchyElementPK(resultSet.getInt(col++));
/*      */ 
/*  420 */         String textHierarchyElement = resultSet.getString(col++);
/*      */ 
/*  425 */         HierarchyCK ckHierarchy = new HierarchyCK(pkDimension, pkHierarchy);
/*      */ 
/*  431 */         HierarchyElementCK ckHierarchyElement = new HierarchyElementCK(pkDimension, pkHierarchy, pkHierarchyElement);
/*      */ 
/*  438 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/*  445 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(ckHierarchy, textHierarchy);
/*      */ 
/*  451 */         HierarchyElementRefImpl erHierarchyElement = new HierarchyElementRefImpl(ckHierarchyElement, textHierarchyElement);
/*      */ 
/*  458 */         results.add(erHierarchyElement, erHierarchy, erDimension);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  467 */       throw handleSQLException(SQL_HIERARACHY_ELEMENT, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  471 */       closeResultSet(resultSet);
/*  472 */       closeStatement(stmt);
/*  473 */       closeConnection();
/*      */     }
/*      */ 
/*  476 */     if (timer != null) {
/*  477 */       timer.logDebug("getHierarachyElement", " HierarchyElementId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  482 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  499 */     if (items == null) {
/*  500 */       return false;
/*      */     }
/*  502 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  503 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  505 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  509 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  510 */       while (iter3.hasNext())
/*      */       {
/*  512 */         this.mDetails = ((HierarchyElementEVO)iter3.next());
/*  513 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  515 */         somethingChanged = true;
/*      */ 
/*  518 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  522 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  523 */       while (iter2.hasNext())
/*      */       {
/*  525 */         this.mDetails = ((HierarchyElementEVO)iter2.next());
/*      */ 
/*  528 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  530 */         somethingChanged = true;
/*      */ 
/*  533 */         if (deleteStmt == null) {
/*  534 */           deleteStmt = getConnection().prepareStatement("delete from HIERARCHY_ELEMENT where    HIERARCHY_ELEMENT_ID = ? ");
/*      */         }
/*      */ 
/*  537 */         int col = 1;
/*  538 */         deleteStmt.setInt(col++, this.mDetails.getHierarchyElementId());
/*      */ 
/*  540 */         if (this._log.isDebugEnabled()) {
/*  541 */           this._log.debug("update", "HierarchyElement deleting HierarchyElementId=" + this.mDetails.getHierarchyElementId());
/*      */         }
/*      */ 
/*  546 */         deleteStmt.addBatch();
/*      */ 
/*  549 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  554 */       if (deleteStmt != null)
/*      */       {
/*  556 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  558 */         deleteStmt.executeBatch();
/*      */ 
/*  560 */         if (timer2 != null) {
/*  561 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  565 */       Iterator iter1 = items.values().iterator();
/*  566 */       while (iter1.hasNext())
/*      */       {
/*  568 */         this.mDetails = ((HierarchyElementEVO)iter1.next());
/*      */ 
/*  570 */         if (this.mDetails.insertPending())
/*      */         {
/*  572 */           somethingChanged = true;
/*  573 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  576 */         if (this.mDetails.isModified())
/*      */         {
/*  578 */           somethingChanged = true;
/*  579 */           doStore(); continue;
/*      */         }
/*      */ 
/*  583 */         if ((this.mDetails.deletePending()) || 
/*  589 */           (!getHierarchyElementFeedDAO().update(this.mDetails.getFeederElementsMap()))) continue;
/*  590 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  602 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  606 */       throw handleSQLException("delete from HIERARCHY_ELEMENT where    HIERARCHY_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  610 */       if (deleteStmt != null)
/*      */       {
/*  612 */         closeStatement(deleteStmt);
/*  613 */         closeConnection();
/*      */       }
/*      */ 
/*  616 */       this.mDetails = null;
/*      */ 
/*  618 */       if ((somethingChanged) && 
/*  619 */         (timer != null))
/*  620 */         timer.logDebug("update", "collection"); 
/*  620 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(HierarchyElementPK pk)
/*      */   {
/*  646 */     Set emptyStrings = Collections.emptySet();
/*  647 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(HierarchyElementPK pk, Set<String> exclusionTables)
/*      */   {
/*  653 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  655 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  657 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  659 */       PreparedStatement stmt = null;
/*      */ 
/*  661 */       int resultCount = 0;
/*  662 */       String s = null;
/*      */       try
/*      */       {
/*  665 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  667 */         if (this._log.isDebugEnabled()) {
/*  668 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  670 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  673 */         int col = 1;
/*  674 */         stmt.setInt(col++, pk.getHierarchyElementId());
/*      */ 
/*  677 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  681 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  685 */         closeStatement(stmt);
/*  686 */         closeConnection();
/*      */ 
/*  688 */         if (timer != null) {
/*  689 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  693 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  695 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  697 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  699 */       PreparedStatement stmt = null;
/*      */ 
/*  701 */       int resultCount = 0;
/*  702 */       String s = null;
/*      */       try
/*      */       {
/*  705 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  707 */         if (this._log.isDebugEnabled()) {
/*  708 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  710 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  713 */         int col = 1;
/*  714 */         stmt.setInt(col++, pk.getHierarchyElementId());
/*      */ 
/*  717 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  721 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  725 */         closeStatement(stmt);
/*  726 */         closeConnection();
/*      */ 
/*  728 */         if (timer != null)
/*  729 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(DimensionPK entityPK, Collection owners, String dependants)
/*      */   {
/*  753 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  755 */     PreparedStatement stmt = null;
/*  756 */     ResultSet resultSet = null;
/*      */ 
/*  758 */     int itemCount = 0;
/*      */ 
/*  760 */     HierarchyEVO owningEVO = null;
/*  761 */     Iterator ownersIter = owners.iterator();
/*  762 */     while (ownersIter.hasNext())
/*      */     {
/*  764 */       owningEVO = (HierarchyEVO)ownersIter.next();
/*  765 */       owningEVO.setHierarchyElementsAllItemsLoaded(true);
/*      */     }
/*  767 */     ownersIter = owners.iterator();
/*  768 */     owningEVO = (HierarchyEVO)ownersIter.next();
/*  769 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  773 */       stmt = getConnection().prepareStatement("select HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT.HIERARCHY_ID,HIERARCHY_ELEMENT.PARENT_ID,HIERARCHY_ELEMENT.CHILD_INDEX,HIERARCHY_ELEMENT.VIS_ID,HIERARCHY_ELEMENT.DESCRIPTION,HIERARCHY_ELEMENT.CREDIT_DEBIT,HIERARCHY_ELEMENT.AUG_PARENT_ID,HIERARCHY_ELEMENT.AUG_CHILD_INDEX,HIERARCHY_ELEMENT.AUG_CREDIT_DEBIT,HIERARCHY_ELEMENT.CAL_ELEM_TYPE from HIERARCHY_ELEMENT,HIERARCHY where 1=1 and HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.DIMENSION_ID = ? order by  HIERARCHY_ELEMENT.HIERARCHY_ID ,HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID");
/*      */ 
/*  775 */       int col = 1;
/*  776 */       stmt.setInt(col++, entityPK.getDimensionId());
/*      */ 
/*  778 */       resultSet = stmt.executeQuery();
/*      */ 
/*  780 */       resultSet.setFetchSize(5000);
/*      */ 
/*  782 */       while (resultSet.next())
/*      */       {
/*  784 */         itemCount++;
/*  785 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  790 */         while (this.mDetails.getHierarchyId() != owningEVO.getHierarchyId())
/*      */         {
/*  794 */           if (!ownersIter.hasNext())
/*      */           {
/*  796 */             this._log.debug("bulkGetAll", "can't find owning [HierarchyId=" + this.mDetails.getHierarchyId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  800 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  801 */             ownersIter = owners.iterator();
/*  802 */             while (ownersIter.hasNext())
/*      */             {
/*  804 */               owningEVO = (HierarchyEVO)ownersIter.next();
/*  805 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  807 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  809 */           owningEVO = (HierarchyEVO)ownersIter.next();
/*      */         }
/*  811 */         if (owningEVO.getHierarchyElements() == null)
/*      */         {
/*  813 */           theseItems = new ArrayList();
/*  814 */           owningEVO.setHierarchyElements(theseItems);
/*  815 */           owningEVO.setHierarchyElementsAllItemsLoaded(true);
/*      */         }
/*  817 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  820 */       if (timer != null) {
/*  821 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  824 */       if ((itemCount > 0) && (dependants.indexOf("<5>") > -1))
/*      */       {
/*  826 */         getHierarchyElementFeedDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  830 */       throw handleSQLException("select HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT.HIERARCHY_ID,HIERARCHY_ELEMENT.PARENT_ID,HIERARCHY_ELEMENT.CHILD_INDEX,HIERARCHY_ELEMENT.VIS_ID,HIERARCHY_ELEMENT.DESCRIPTION,HIERARCHY_ELEMENT.CREDIT_DEBIT,HIERARCHY_ELEMENT.AUG_PARENT_ID,HIERARCHY_ELEMENT.AUG_CHILD_INDEX,HIERARCHY_ELEMENT.AUG_CREDIT_DEBIT,HIERARCHY_ELEMENT.CAL_ELEM_TYPE from HIERARCHY_ELEMENT,HIERARCHY where 1=1 and HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.DIMENSION_ID = ? order by  HIERARCHY_ELEMENT.HIERARCHY_ID ,HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  834 */       closeResultSet(resultSet);
/*  835 */       closeStatement(stmt);
/*  836 */       closeConnection();
/*      */ 
/*  838 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectHierarchyId, String dependants, Collection currentList)
/*      */   {
/*  863 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  864 */     PreparedStatement stmt = null;
/*  865 */     ResultSet resultSet = null;
/*      */ 
/*  867 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  871 */       stmt = getConnection().prepareStatement("select HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT.HIERARCHY_ID,HIERARCHY_ELEMENT.PARENT_ID,HIERARCHY_ELEMENT.CHILD_INDEX,HIERARCHY_ELEMENT.VIS_ID,HIERARCHY_ELEMENT.DESCRIPTION,HIERARCHY_ELEMENT.CREDIT_DEBIT,HIERARCHY_ELEMENT.AUG_PARENT_ID,HIERARCHY_ELEMENT.AUG_CHILD_INDEX,HIERARCHY_ELEMENT.AUG_CREDIT_DEBIT,HIERARCHY_ELEMENT.CAL_ELEM_TYPE from HIERARCHY_ELEMENT where    HIERARCHY_ID = ? ");
/*      */ 
/*  873 */       int col = 1;
/*  874 */       stmt.setInt(col++, selectHierarchyId);
/*      */ 
/*  876 */       resultSet = stmt.executeQuery();
/*      */ 
/*  878 */       while (resultSet.next())
/*      */       {
/*  880 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  883 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  886 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  889 */       if (currentList != null)
/*      */       {
/*  892 */         ListIterator iter = items.listIterator();
/*  893 */         HierarchyElementEVO currentEVO = null;
/*  894 */         HierarchyElementEVO newEVO = null;
/*  895 */         while (iter.hasNext())
/*      */         {
/*  897 */           newEVO = (HierarchyElementEVO)iter.next();
/*  898 */           Iterator iter2 = currentList.iterator();
/*  899 */           while (iter2.hasNext())
/*      */           {
/*  901 */             currentEVO = (HierarchyElementEVO)iter2.next();
/*  902 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  904 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  910 */         Iterator iter2 = currentList.iterator();
/*  911 */         while (iter2.hasNext())
/*      */         {
/*  913 */           currentEVO = (HierarchyElementEVO)iter2.next();
/*  914 */           if (currentEVO.insertPending()) {
/*  915 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  919 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  923 */       throw handleSQLException("select HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID,HIERARCHY_ELEMENT.HIERARCHY_ID,HIERARCHY_ELEMENT.PARENT_ID,HIERARCHY_ELEMENT.CHILD_INDEX,HIERARCHY_ELEMENT.VIS_ID,HIERARCHY_ELEMENT.DESCRIPTION,HIERARCHY_ELEMENT.CREDIT_DEBIT,HIERARCHY_ELEMENT.AUG_PARENT_ID,HIERARCHY_ELEMENT.AUG_CHILD_INDEX,HIERARCHY_ELEMENT.AUG_CREDIT_DEBIT,HIERARCHY_ELEMENT.CAL_ELEM_TYPE from HIERARCHY_ELEMENT where    HIERARCHY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  927 */       closeResultSet(resultSet);
/*  928 */       closeStatement(stmt);
/*  929 */       closeConnection();
/*      */ 
/*  931 */       if (timer != null) {
/*  932 */         timer.logDebug("getAll", " HierarchyId=" + selectHierarchyId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  937 */     return items;
/*      */   }
/*      */ 
/*      */   public HierarchyElementEVO getDetails(DimensionCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  954 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  957 */     if (this.mDetails == null) {
/*  958 */       doLoad(((HierarchyElementCK)paramCK).getHierarchyElementPK());
/*      */     }
/*  960 */     else if (!this.mDetails.getPK().equals(((HierarchyElementCK)paramCK).getHierarchyElementPK())) {
/*  961 */       doLoad(((HierarchyElementCK)paramCK).getHierarchyElementPK());
/*      */     }
/*      */ 
/*  964 */     if ((dependants.indexOf("<5>") > -1) && (!this.mDetails.isFeederElementsAllItemsLoaded()))
/*      */     {
/*  969 */       this.mDetails.setFeederElements(getHierarchyElementFeedDAO().getAll(this.mDetails.getHierarchyElementId(), dependants, this.mDetails.getFeederElements()));
/*      */ 
/*  976 */       this.mDetails.setFeederElementsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  979 */     if ((paramCK instanceof HierarchyElementFeedCK))
/*      */     {
/*  981 */       if (this.mDetails.getFeederElements() == null) {
/*  982 */         this.mDetails.loadFeederElementsItem(getHierarchyElementFeedDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  985 */         HierarchyElementFeedPK pk = ((HierarchyElementFeedCK)paramCK).getHierarchyElementFeedPK();
/*  986 */         HierarchyElementFeedEVO evo = this.mDetails.getFeederElementsItem(pk);
/*  987 */         if (evo == null) {
/*  988 */           this.mDetails.loadFeederElementsItem(getHierarchyElementFeedDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  993 */     HierarchyElementEVO details = new HierarchyElementEVO();
/*  994 */     details = this.mDetails.deepClone();
/*      */ 
/*  996 */     if (timer != null) {
/*  997 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  999 */     return details;
/*      */   }
/*      */ 
/*      */   public HierarchyElementEVO getDetails(DimensionCK paramCK, HierarchyElementEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1005 */     HierarchyElementEVO savedEVO = this.mDetails;
/* 1006 */     this.mDetails = paramEVO;
/* 1007 */     HierarchyElementEVO newEVO = getDetails(paramCK, dependants);
/* 1008 */     this.mDetails = savedEVO;
/* 1009 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public HierarchyElementEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1015 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1019 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1022 */     HierarchyElementEVO details = this.mDetails.deepClone();
/*      */ 
/* 1024 */     if (timer != null) {
/* 1025 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1027 */     return details;
/*      */   }
/*      */ 
/*      */   protected HierarchyElementFeedDAO getHierarchyElementFeedDAO()
/*      */   {
/* 1036 */     if (this.mHierarchyElementFeedDAO == null)
/*      */     {
/* 1038 */       if (this.mDataSource != null)
/* 1039 */         this.mHierarchyElementFeedDAO = new HierarchyElementFeedDAO(this.mDataSource);
/*      */       else {
/* 1041 */         this.mHierarchyElementFeedDAO = new HierarchyElementFeedDAO(getConnection());
/*      */       }
/*      */     }
/* 1044 */     return this.mHierarchyElementFeedDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1049 */     return "HierarchyElement";
/*      */   }
/*      */ 
/*      */   public HierarchyElementRefImpl getRef(HierarchyElementPK paramHierarchyElementPK)
/*      */   {
/* 1054 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1055 */     PreparedStatement stmt = null;
/* 1056 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1059 */       stmt = getConnection().prepareStatement("select 0,DIMENSION.DIMENSION_ID,HIERARCHY.HIERARCHY_ID,HIERARCHY_ELEMENT.VIS_ID from HIERARCHY_ELEMENT,DIMENSION,HIERARCHY where 1=1 and HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID = ? and HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.HIERARCHY_ID = DIMENSION.HIERARCHY_ID");
/* 1060 */       int col = 1;
/* 1061 */       stmt.setInt(col++, paramHierarchyElementPK.getHierarchyElementId());
/*      */ 
/* 1063 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1065 */       if (!resultSet.next()) {
/* 1066 */         throw new RuntimeException(getEntityName() + " getRef " + paramHierarchyElementPK + " not found");
/*      */       }
/* 1068 */       col = 2;
/* 1069 */       DimensionPK newDimensionPK = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/* 1073 */       HierarchyPK newHierarchyPK = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/* 1077 */       String textHierarchyElement = resultSet.getString(col++);
/* 1078 */       HierarchyElementCK ckHierarchyElement = new HierarchyElementCK(newDimensionPK, newHierarchyPK, paramHierarchyElementPK);
/*      */ 
/* 1084 */       HierarchyElementRefImpl localHierarchyElementRefImpl = new HierarchyElementRefImpl(ckHierarchyElement, textHierarchyElement);
/*      */       return localHierarchyElementRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1089 */       throw handleSQLException(paramHierarchyElementPK, "select 0,DIMENSION.DIMENSION_ID,HIERARCHY.HIERARCHY_ID,HIERARCHY_ELEMENT.VIS_ID from HIERARCHY_ELEMENT,DIMENSION,HIERARCHY where 1=1 and HIERARCHY_ELEMENT.HIERARCHY_ELEMENT_ID = ? and HIERARCHY_ELEMENT.HIERARCHY_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.HIERARCHY_ID = DIMENSION.HIERARCHY_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1093 */       closeResultSet(resultSet);
/* 1094 */       closeStatement(stmt);
/* 1095 */       closeConnection();
/*      */ 
/* 1097 */       if (timer != null)
/* 1098 */         timer.logDebug("getRef", paramHierarchyElementPK); 
/* 1098 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1110 */     if (c == null)
/* 1111 */       return;
/* 1112 */     Iterator iter = c.iterator();
/* 1113 */     while (iter.hasNext())
/*      */     {
/* 1115 */       HierarchyElementEVO evo = (HierarchyElementEVO)iter.next();
/* 1116 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(HierarchyElementEVO evo, String dependants)
/*      */   {
/* 1130 */     if (evo.insertPending()) {
/* 1131 */       return;
/*      */     }
/* 1133 */     if (evo.getHierarchyElementId() < 1) {
/* 1134 */       return;
/*      */     }
/*      */ 
/* 1138 */     if (dependants.indexOf("<5>") > -1)
/*      */     {
/* 1141 */       if (!evo.isFeederElementsAllItemsLoaded())
/*      */       {
/* 1143 */         evo.setFeederElements(getHierarchyElementFeedDAO().getAll(evo.getHierarchyElementId(), dependants, evo.getFeederElements()));
/*      */ 
/* 1150 */         evo.setFeederElementsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.dimension.HierarchyElementDAO
 * JD-Core Version:    0.6.0
 */