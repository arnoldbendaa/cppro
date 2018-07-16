/*      */ package com.cedar.cp.ejb.impl.extsys;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysDimensionPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysHierElementCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysHierElementPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysHierarchyCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysHierarchyPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysHierarchyRefImpl;
/*      */ import com.cedar.cp.dto.extsys.ExtSysLedgerPK;
/*      */ import com.cedar.cp.dto.extsys.ExternalSystemCK;
/*      */ import com.cedar.cp.dto.extsys.ExternalSystemPK;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.SqlBuilder;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.io.PrintWriter;
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
/*      */ public class ExtSysHierarchyDAO extends AbstractDAO
/*      */ {
/*   34 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID,EXT_SYS_HIERARCHY.COMPANY_VIS_ID,EXT_SYS_HIERARCHY.LEDGER_VIS_ID,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID,EXT_SYS_HIERARCHY.DESCRIPTION";
/*      */   protected static final String SQL_LOAD = " from EXT_SYS_HIERARCHY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into EXT_SYS_HIERARCHY ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,HIERARCHY_VIS_ID,DESCRIPTION) values ( ?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update EXT_SYS_HIERARCHY set DESCRIPTION = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from EXT_SYS_HIERARCHY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? ";
/*  481 */   private static String[][] SQL_DELETE_CHILDREN = { { "EXT_SYS_HIER_ELEMENT", "delete from EXT_SYS_HIER_ELEMENT where     EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = ? and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = ? and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = ? and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = ? " } };
/*      */ 
/*  494 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "EXT_SYS_HIER_ELEM_FEED", "delete from EXT_SYS_HIER_ELEM_FEED ExtSysHierElemFeed where exists (select * from EXT_SYS_HIER_ELEM_FEED,EXT_SYS_HIER_ELEMENT,EXT_SYS_HIERARCHY where     EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID = EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID = EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID and EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID = EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and ExtSysHierElemFeed.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID and ExtSysHierElemFeed.COMPANY_VIS_ID = EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID and ExtSysHierElemFeed.LEDGER_VIS_ID = EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID and ExtSysHierElemFeed.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID and ExtSysHierElemFeed.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID and ExtSysHierElemFeed.HIER_ELEMENT_VIS_ID = EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID " } };
/*      */ 
/*  523 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = ?and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = ?and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = ?and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = ?and EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIERARCHY.COMPANY_VIS_ID ,EXT_SYS_HIERARCHY.LEDGER_VIS_ID ,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID ,EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIERARCHY.COMPANY_VIS_ID ,EXT_SYS_HIERARCHY.LEDGER_VIS_ID ,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID ,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID";
/*      */   protected static final String SQL_GET_ALL = " from EXT_SYS_HIERARCHY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ";
/*      */   protected ExtSysHierElementDAO mExtSysHierElementDAO;
/*      */   protected ExtSysHierarchyEVO mDetails;
/*      */ 
/*      */   public ExtSysHierarchyDAO(Connection connection)
/*      */   {
/*   41 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ExtSysHierarchyDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ExtSysHierarchyDAO(DataSource ds)
/*      */   {
/*   57 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ExtSysHierarchyPK getPK()
/*      */   {
/*   65 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ExtSysHierarchyEVO details)
/*      */   {
/*   74 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private ExtSysHierarchyEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   91 */     int col = 1;
/*   92 */     ExtSysHierarchyEVO evo = new ExtSysHierarchyEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), null);
/*      */ 
/*  102 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ExtSysHierarchyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  107 */     int col = startCol_;
/*  108 */     stmt_.setInt(col++, evo_.getExternalSystemId());
/*  109 */     stmt_.setString(col++, evo_.getCompanyVisId());
/*  110 */     stmt_.setString(col++, evo_.getLedgerVisId());
/*  111 */     stmt_.setString(col++, evo_.getDimensionVisId());
/*  112 */     stmt_.setString(col++, evo_.getHierarchyVisId());
/*  113 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ExtSysHierarchyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  118 */     int col = startCol_;
/*  119 */     stmt_.setString(col++, evo_.getDescription());
/*  120 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ExtSysHierarchyPK pk)
/*      */     throws ValidationException
/*      */   {
/*  140 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  142 */     PreparedStatement stmt = null;
/*  143 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  147 */       stmt = getConnection().prepareStatement("select EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID,EXT_SYS_HIERARCHY.COMPANY_VIS_ID,EXT_SYS_HIERARCHY.LEDGER_VIS_ID,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID,EXT_SYS_HIERARCHY.DESCRIPTION from EXT_SYS_HIERARCHY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? ");
/*      */ 
/*  150 */       int col = 1;
/*  151 */       stmt.setInt(col++, pk.getExternalSystemId());
/*  152 */       stmt.setString(col++, pk.getCompanyVisId());
/*  153 */       stmt.setString(col++, pk.getLedgerVisId());
/*  154 */       stmt.setString(col++, pk.getDimensionVisId());
/*  155 */       stmt.setString(col++, pk.getHierarchyVisId());
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
/*  169 */       throw handleSQLException(pk, "select EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID,EXT_SYS_HIERARCHY.COMPANY_VIS_ID,EXT_SYS_HIERARCHY.LEDGER_VIS_ID,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID,EXT_SYS_HIERARCHY.DESCRIPTION from EXT_SYS_HIERARCHY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? ", sqle);
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
/*  207 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  208 */     this.mDetails.postCreateInit();
/*      */ 
/*  210 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  214 */       stmt = getConnection().prepareStatement("insert into EXT_SYS_HIERARCHY ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,HIERARCHY_VIS_ID,DESCRIPTION) values ( ?,?,?,?,?,?)");
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
/*  232 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXT_SYS_HIERARCHY ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,HIERARCHY_VIS_ID,DESCRIPTION) values ( ?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  236 */       closeStatement(stmt);
/*  237 */       closeConnection();
/*      */ 
/*  239 */       if (timer != null) {
/*  240 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  246 */       getExtSysHierElementDAO().update(this.mDetails.getExtSysHierElementsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  252 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  276 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  280 */     PreparedStatement stmt = null;
/*      */ 
/*  282 */     boolean mainChanged = this.mDetails.isModified();
/*  283 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  287 */       if (getExtSysHierElementDAO().update(this.mDetails.getExtSysHierElementsMap())) {
/*  288 */         dependantChanged = true;
/*      */       }
/*  290 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  292 */         stmt = getConnection().prepareStatement("update EXT_SYS_HIERARCHY set DESCRIPTION = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? ");
/*      */ 
/*  295 */         int col = 1;
/*  296 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  297 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  300 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  303 */         if (resultCount != 1) {
/*  304 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  307 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  316 */       throw handleSQLException(getPK(), "update EXT_SYS_HIERARCHY set DESCRIPTION = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  320 */       closeStatement(stmt);
/*  321 */       closeConnection();
/*      */ 
/*  323 */       if ((timer != null) && (
/*  324 */         (mainChanged) || (dependantChanged)))
/*  325 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
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
/*  361 */         this.mDetails = ((ExtSysHierarchyEVO)iter3.next());
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
/*  374 */         this.mDetails = ((ExtSysHierarchyEVO)iter2.next());
/*      */ 
/*  377 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  379 */         somethingChanged = true;
/*      */ 
/*  382 */         if (deleteStmt == null) {
/*  383 */           deleteStmt = getConnection().prepareStatement("delete from EXT_SYS_HIERARCHY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? ");
/*      */         }
/*      */ 
/*  386 */         int col = 1;
/*  387 */         deleteStmt.setInt(col++, this.mDetails.getExternalSystemId());
/*  388 */         deleteStmt.setString(col++, this.mDetails.getCompanyVisId());
/*  389 */         deleteStmt.setString(col++, this.mDetails.getLedgerVisId());
/*  390 */         deleteStmt.setString(col++, this.mDetails.getDimensionVisId());
/*  391 */         deleteStmt.setString(col++, this.mDetails.getHierarchyVisId());
/*      */ 
/*  393 */         if (this._log.isDebugEnabled()) {
/*  394 */           this._log.debug("update", "ExtSysHierarchy deleting ExternalSystemId=" + this.mDetails.getExternalSystemId() + ",CompanyVisId=" + this.mDetails.getCompanyVisId() + ",LedgerVisId=" + this.mDetails.getLedgerVisId() + ",DimensionVisId=" + this.mDetails.getDimensionVisId() + ",HierarchyVisId=" + this.mDetails.getHierarchyVisId());
/*      */         }
/*      */ 
/*  403 */         deleteStmt.addBatch();
/*      */ 
/*  406 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  411 */       if (deleteStmt != null)
/*      */       {
/*  413 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  415 */         deleteStmt.executeBatch();
/*      */ 
/*  417 */         if (timer2 != null) {
/*  418 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  422 */       Iterator iter1 = items.values().iterator();
/*  423 */       while (iter1.hasNext())
/*      */       {
/*  425 */         this.mDetails = ((ExtSysHierarchyEVO)iter1.next());
/*      */ 
/*  427 */         if (this.mDetails.insertPending())
/*      */         {
/*  429 */           somethingChanged = true;
/*  430 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  433 */         if (this.mDetails.isModified())
/*      */         {
/*  435 */           somethingChanged = true;
/*  436 */           doStore(); continue;
/*      */         }
/*      */ 
/*  440 */         if ((this.mDetails.deletePending()) || 
/*  446 */           (!getExtSysHierElementDAO().update(this.mDetails.getExtSysHierElementsMap()))) continue;
/*  447 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  459 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  463 */       throw handleSQLException("delete from EXT_SYS_HIERARCHY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  467 */       if (deleteStmt != null)
/*      */       {
/*  469 */         closeStatement(deleteStmt);
/*  470 */         closeConnection();
/*      */       }
/*      */ 
/*  473 */       this.mDetails = null;
/*      */ 
/*  475 */       if ((somethingChanged) && 
/*  476 */         (timer != null))
/*  477 */         timer.logDebug("update", "collection"); 
/*  477 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ExtSysHierarchyPK pk)
/*      */   {
/*  536 */     Set emptyStrings = Collections.emptySet();
/*  537 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ExtSysHierarchyPK pk, Set<String> exclusionTables)
/*      */   {
/*  543 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  545 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  547 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  549 */       PreparedStatement stmt = null;
/*      */ 
/*  551 */       int resultCount = 0;
/*  552 */       String s = null;
/*      */       try
/*      */       {
/*  555 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  557 */         if (this._log.isDebugEnabled()) {
/*  558 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  560 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  563 */         int col = 1;
/*  564 */         stmt.setInt(col++, pk.getExternalSystemId());
/*  565 */         stmt.setString(col++, pk.getCompanyVisId());
/*  566 */         stmt.setString(col++, pk.getLedgerVisId());
/*  567 */         stmt.setString(col++, pk.getDimensionVisId());
/*  568 */         stmt.setString(col++, pk.getHierarchyVisId());
/*      */ 
/*  571 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  575 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  579 */         closeStatement(stmt);
/*  580 */         closeConnection();
/*      */ 
/*  582 */         if (timer != null) {
/*  583 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  587 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  589 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  591 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  593 */       PreparedStatement stmt = null;
/*      */ 
/*  595 */       int resultCount = 0;
/*  596 */       String s = null;
/*      */       try
/*      */       {
/*  599 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  601 */         if (this._log.isDebugEnabled()) {
/*  602 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  604 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  607 */         int col = 1;
/*  608 */         stmt.setInt(col++, pk.getExternalSystemId());
/*  609 */         stmt.setString(col++, pk.getCompanyVisId());
/*  610 */         stmt.setString(col++, pk.getLedgerVisId());
/*  611 */         stmt.setString(col++, pk.getDimensionVisId());
/*  612 */         stmt.setString(col++, pk.getHierarchyVisId());
/*      */ 
/*  615 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  619 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  623 */         closeStatement(stmt);
/*  624 */         closeConnection();
/*      */ 
/*  626 */         if (timer != null)
/*  627 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ExternalSystemPK entityPK, Collection owners, String dependants)
/*      */   {
/*  670 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  672 */     PreparedStatement stmt = null;
/*  673 */     ResultSet resultSet = null;
/*      */ 
/*  675 */     int itemCount = 0;
/*      */ 
/*  677 */     ExtSysDimensionEVO owningEVO = null;
/*  678 */     Iterator ownersIter = owners.iterator();
/*  679 */     while (ownersIter.hasNext())
/*      */     {
/*  681 */       owningEVO = (ExtSysDimensionEVO)ownersIter.next();
/*  682 */       owningEVO.setExtSysHierarchiesAllItemsLoaded(true);
/*      */     }
/*  684 */     ownersIter = owners.iterator();
/*  685 */     owningEVO = (ExtSysDimensionEVO)ownersIter.next();
/*  686 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  690 */       stmt = getConnection().prepareStatement("select EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID,EXT_SYS_HIERARCHY.COMPANY_VIS_ID,EXT_SYS_HIERARCHY.LEDGER_VIS_ID,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID,EXT_SYS_HIERARCHY.DESCRIPTION from EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIERARCHY.COMPANY_VIS_ID ,EXT_SYS_HIERARCHY.LEDGER_VIS_ID ,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID ,EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIERARCHY.COMPANY_VIS_ID ,EXT_SYS_HIERARCHY.LEDGER_VIS_ID ,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID ,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID");
/*      */ 
/*  692 */       int col = 1;
/*  693 */       stmt.setInt(col++, entityPK.getExternalSystemId());
/*      */ 
/*  695 */       resultSet = stmt.executeQuery();
/*      */ 
/*  698 */       while (resultSet.next())
/*      */       {
/*  700 */         itemCount++;
/*  701 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  706 */         while ((this.mDetails.getExternalSystemId() != owningEVO.getExternalSystemId()) || (!this.mDetails.getCompanyVisId().equals(owningEVO.getCompanyVisId())) || (!this.mDetails.getLedgerVisId().equals(owningEVO.getLedgerVisId())) || (!this.mDetails.getDimensionVisId().equals(owningEVO.getDimensionVisId())))
/*      */         {
/*  713 */           if (!ownersIter.hasNext())
/*      */           {
/*  715 */             this._log.debug("bulkGetAll", "can't find owning [ExternalSystemId=" + this.mDetails.getExternalSystemId() + "CompanyVisId=" + this.mDetails.getCompanyVisId() + "LedgerVisId=" + this.mDetails.getLedgerVisId() + "DimensionVisId=" + this.mDetails.getDimensionVisId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  722 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  723 */             ownersIter = owners.iterator();
/*  724 */             while (ownersIter.hasNext())
/*      */             {
/*  726 */               owningEVO = (ExtSysDimensionEVO)ownersIter.next();
/*  727 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  729 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  731 */           owningEVO = (ExtSysDimensionEVO)ownersIter.next();
/*      */         }
/*  733 */         if (owningEVO.getExtSysHierarchies() == null)
/*      */         {
/*  735 */           theseItems = new ArrayList();
/*  736 */           owningEVO.setExtSysHierarchies(theseItems);
/*  737 */           owningEVO.setExtSysHierarchiesAllItemsLoaded(true);
/*      */         }
/*  739 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  742 */       if (timer != null) {
/*  743 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  746 */       if ((itemCount > 0) && (dependants.indexOf("<5>") > -1))
/*      */       {
/*  748 */         getExtSysHierElementDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  752 */       throw handleSQLException("select EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID,EXT_SYS_HIERARCHY.COMPANY_VIS_ID,EXT_SYS_HIERARCHY.LEDGER_VIS_ID,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID,EXT_SYS_HIERARCHY.DESCRIPTION from EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIERARCHY.COMPANY_VIS_ID ,EXT_SYS_HIERARCHY.LEDGER_VIS_ID ,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID ,EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIERARCHY.COMPANY_VIS_ID ,EXT_SYS_HIERARCHY.LEDGER_VIS_ID ,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID ,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  756 */       closeResultSet(resultSet);
/*  757 */       closeStatement(stmt);
/*  758 */       closeConnection();
/*      */ 
/*  760 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectExternalSystemId, String selectCompanyVisId, String selectLedgerVisId, String selectDimensionVisId, String dependants, Collection currentList)
/*      */   {
/*  794 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  795 */     PreparedStatement stmt = null;
/*  796 */     ResultSet resultSet = null;
/*      */ 
/*  798 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  802 */       stmt = getConnection().prepareStatement("select EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID,EXT_SYS_HIERARCHY.COMPANY_VIS_ID,EXT_SYS_HIERARCHY.LEDGER_VIS_ID,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID,EXT_SYS_HIERARCHY.DESCRIPTION from EXT_SYS_HIERARCHY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ");
/*      */ 
/*  804 */       int col = 1;
/*  805 */       stmt.setInt(col++, selectExternalSystemId);
/*  806 */       stmt.setString(col++, selectCompanyVisId);
/*  807 */       stmt.setString(col++, selectLedgerVisId);
/*  808 */       stmt.setString(col++, selectDimensionVisId);
/*      */ 
/*  810 */       resultSet = stmt.executeQuery();
/*      */ 
/*  812 */       while (resultSet.next())
/*      */       {
/*  814 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  817 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  820 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  823 */       if (currentList != null)
/*      */       {
/*  826 */         ListIterator iter = items.listIterator();
/*  827 */         ExtSysHierarchyEVO currentEVO = null;
/*  828 */         ExtSysHierarchyEVO newEVO = null;
/*  829 */         while (iter.hasNext())
/*      */         {
/*  831 */           newEVO = (ExtSysHierarchyEVO)iter.next();
/*  832 */           Iterator iter2 = currentList.iterator();
/*  833 */           while (iter2.hasNext())
/*      */           {
/*  835 */             currentEVO = (ExtSysHierarchyEVO)iter2.next();
/*  836 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  838 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  844 */         Iterator iter2 = currentList.iterator();
/*  845 */         while (iter2.hasNext())
/*      */         {
/*  847 */           currentEVO = (ExtSysHierarchyEVO)iter2.next();
/*  848 */           if (currentEVO.insertPending()) {
/*  849 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  853 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  857 */       throw handleSQLException("select EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID,EXT_SYS_HIERARCHY.COMPANY_VIS_ID,EXT_SYS_HIERARCHY.LEDGER_VIS_ID,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID,EXT_SYS_HIERARCHY.DESCRIPTION from EXT_SYS_HIERARCHY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  861 */       closeResultSet(resultSet);
/*  862 */       closeStatement(stmt);
/*  863 */       closeConnection();
/*      */ 
/*  865 */       if (timer != null) {
/*  866 */         timer.logDebug("getAll", " ExternalSystemId=" + selectExternalSystemId + ",CompanyVisId=" + selectCompanyVisId + ",LedgerVisId=" + selectLedgerVisId + ",DimensionVisId=" + selectDimensionVisId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  874 */     return items;
/*      */   }
/*      */ 
/*      */   public ExtSysHierarchyEVO getDetails(ExternalSystemCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  893 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  896 */     if (this.mDetails == null) {
/*  897 */       doLoad(((ExtSysHierarchyCK)paramCK).getExtSysHierarchyPK());
/*      */     }
/*  899 */     else if (!this.mDetails.getPK().equals(((ExtSysHierarchyCK)paramCK).getExtSysHierarchyPK())) {
/*  900 */       doLoad(((ExtSysHierarchyCK)paramCK).getExtSysHierarchyPK());
/*      */     }
/*      */ 
/*  903 */     if ((dependants.indexOf("<5>") > -1) && (!this.mDetails.isExtSysHierElementsAllItemsLoaded()))
/*      */     {
/*  908 */       this.mDetails.setExtSysHierElements(getExtSysHierElementDAO().getAll(this.mDetails.getExternalSystemId(), this.mDetails.getCompanyVisId(), this.mDetails.getLedgerVisId(), this.mDetails.getDimensionVisId(), this.mDetails.getHierarchyVisId(), dependants, this.mDetails.getExtSysHierElements()));
/*      */ 
/*  919 */       this.mDetails.setExtSysHierElementsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  922 */     if ((paramCK instanceof ExtSysHierElementCK))
/*      */     {
/*  924 */       if (this.mDetails.getExtSysHierElements() == null) {
/*  925 */         this.mDetails.loadExtSysHierElementsItem(getExtSysHierElementDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  928 */         ExtSysHierElementPK pk = ((ExtSysHierElementCK)paramCK).getExtSysHierElementPK();
/*  929 */         ExtSysHierElementEVO evo = this.mDetails.getExtSysHierElementsItem(pk);
/*  930 */         if (evo == null)
/*  931 */           this.mDetails.loadExtSysHierElementsItem(getExtSysHierElementDAO().getDetails(paramCK, dependants));
/*      */         else {
/*  933 */           getExtSysHierElementDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  938 */     ExtSysHierarchyEVO details = new ExtSysHierarchyEVO();
/*  939 */     details = this.mDetails.deepClone();
/*      */ 
/*  941 */     if (timer != null) {
/*  942 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  944 */     return details;
/*      */   }
/*      */ 
/*      */   public ExtSysHierarchyEVO getDetails(ExternalSystemCK paramCK, ExtSysHierarchyEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  950 */     ExtSysHierarchyEVO savedEVO = this.mDetails;
/*  951 */     this.mDetails = paramEVO;
/*  952 */     ExtSysHierarchyEVO newEVO = getDetails(paramCK, dependants);
/*  953 */     this.mDetails = savedEVO;
/*  954 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public ExtSysHierarchyEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  960 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  964 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  967 */     ExtSysHierarchyEVO details = this.mDetails.deepClone();
/*      */ 
/*  969 */     if (timer != null) {
/*  970 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  972 */     return details;
/*      */   }
/*      */ 
/*      */   protected ExtSysHierElementDAO getExtSysHierElementDAO()
/*      */   {
/*  981 */     if (this.mExtSysHierElementDAO == null)
/*      */     {
/*  983 */       if (this.mDataSource != null)
/*  984 */         this.mExtSysHierElementDAO = new ExtSysHierElementDAO(this.mDataSource);
/*      */       else {
/*  986 */         this.mExtSysHierElementDAO = new ExtSysHierElementDAO(getConnection());
/*      */       }
/*      */     }
/*  989 */     return this.mExtSysHierElementDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  994 */     return "ExtSysHierarchy";
/*      */   }
/*      */ 
/*      */   public ExtSysHierarchyRefImpl getRef(ExtSysHierarchyPK paramExtSysHierarchyPK)
/*      */   {
/*  999 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1000 */     PreparedStatement stmt = null;
/* 1001 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1004 */       stmt = getConnection().prepareStatement("select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID from EXT_SYS_HIERARCHY,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_LEDGER,EXT_SYS_DIMENSION where 1=1 and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = ? and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = ? and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = ? and EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID = ? and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_DIMENSION.DIMENSION_VIS_ID = EXT_SYS_LEDGER.DIMENSION_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_LEDGER.LEDGER_VIS_ID = EXT_SYS_COMPANY.LEDGER_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID");
/* 1005 */       int col = 1;
/* 1006 */       stmt.setInt(col++, paramExtSysHierarchyPK.getExternalSystemId());
/* 1007 */       stmt.setString(col++, paramExtSysHierarchyPK.getCompanyVisId());
/* 1008 */       stmt.setString(col++, paramExtSysHierarchyPK.getLedgerVisId());
/* 1009 */       stmt.setString(col++, paramExtSysHierarchyPK.getDimensionVisId());
/* 1010 */       stmt.setString(col++, paramExtSysHierarchyPK.getHierarchyVisId());
/*      */ 
/* 1012 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1014 */       if (!resultSet.next()) {
/* 1015 */         throw new RuntimeException(getEntityName() + " getRef " + paramExtSysHierarchyPK + " not found");
/*      */       }
/* 1017 */       col = 2;
/* 1018 */       ExternalSystemPK newExternalSystemPK = new ExternalSystemPK(resultSet.getInt(col++));
/*      */ 
/* 1022 */       ExtSysCompanyPK newExtSysCompanyPK = new ExtSysCompanyPK(resultSet.getInt(col++), resultSet.getString(col++));
/*      */ 
/* 1027 */       ExtSysLedgerPK newExtSysLedgerPK = new ExtSysLedgerPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++));
/*      */ 
/* 1033 */       ExtSysDimensionPK newExtSysDimensionPK = new ExtSysDimensionPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++));
/*      */ 
/* 1040 */       String textExtSysHierarchy = "";
/* 1041 */       ExtSysHierarchyCK ckExtSysHierarchy = new ExtSysHierarchyCK(newExternalSystemPK, newExtSysCompanyPK, newExtSysLedgerPK, newExtSysDimensionPK, paramExtSysHierarchyPK);
/*      */ 
/* 1049 */       ExtSysHierarchyRefImpl localExtSysHierarchyRefImpl = new ExtSysHierarchyRefImpl(ckExtSysHierarchy, textExtSysHierarchy);
/*      */       return localExtSysHierarchyRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1054 */       throw handleSQLException(paramExtSysHierarchyPK, "select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID from EXT_SYS_HIERARCHY,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_LEDGER,EXT_SYS_DIMENSION where 1=1 and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = ? and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = ? and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = ? and EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID = ? and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_DIMENSION.DIMENSION_VIS_ID = EXT_SYS_LEDGER.DIMENSION_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_LEDGER.LEDGER_VIS_ID = EXT_SYS_COMPANY.LEDGER_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1058 */       closeResultSet(resultSet);
/* 1059 */       closeStatement(stmt);
/* 1060 */       closeConnection();
/*      */ 
/* 1062 */       if (timer != null)
/* 1063 */         timer.logDebug("getRef", paramExtSysHierarchyPK); 
/* 1063 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1075 */     if (c == null)
/* 1076 */       return;
/* 1077 */     Iterator iter = c.iterator();
/* 1078 */     while (iter.hasNext())
/*      */     {
/* 1080 */       ExtSysHierarchyEVO evo = (ExtSysHierarchyEVO)iter.next();
/* 1081 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(ExtSysHierarchyEVO evo, String dependants)
/*      */   {
/* 1095 */     if (evo.insertPending()) {
/* 1096 */       return;
/*      */     }
/*      */ 
/* 1100 */     if ((dependants.indexOf("<5>") > -1) || (dependants.indexOf("<6>") > -1))
/*      */     {
/* 1104 */       if (!evo.isExtSysHierElementsAllItemsLoaded())
/*      */       {
/* 1106 */         evo.setExtSysHierElements(getExtSysHierElementDAO().getAll(evo.getExternalSystemId(), evo.getCompanyVisId(), evo.getLedgerVisId(), evo.getDimensionVisId(), evo.getHierarchyVisId(), dependants, evo.getExtSysHierElements()));
/*      */ 
/* 1117 */         evo.setExtSysHierElementsAllItemsLoaded(true);
/*      */       }
/* 1119 */       getExtSysHierElementDAO().getDependants(evo.getExtSysHierElements(), dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void insert(ExtSysHierarchyEVO evo)
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/* 1136 */     setDetails(evo);
/* 1137 */     doCreate();
/*      */   }
/*      */ 
/*      */   public int checkConstraintViolations(int externalSystemId, int maxToReport, PrintWriter log)
/*      */   {
/* 1149 */     SqlExecutor sqlExecutor = null;
/* 1150 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1154 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "select external_system_id, company_vis_id, ledger_vis_id, dimension_vis_id, hierarchy_vis_id", "from ext_sys_hierarchy", "where external_system_id = <externalSystemId>", "group by external_system_id, company_vis_id, ledger_vis_id, dimension_vis_id, hierarchy_vis_id", "having count(*) > 1" });
/*      */ 
/* 1161 */       sqlExecutor = new SqlExecutor("checkConstraintViolations", getDataSource(), sqlBuilder, this._log);
/*      */ 
/* 1163 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/*      */ 
/* 1165 */       rs = sqlExecutor.getResultSet();
/*      */ 
/* 1167 */       int count = 0;
/* 1168 */       while ((rs.next()) && (count < maxToReport))
/*      */       {
/* 1170 */         log.print("Found duplicate hierarchy details: company:[" + rs.getString("COMPANY_VIS_ID") + "] " + " ledger:[" + rs.getString("LEDGER_VIS_ID") + "]" + " dimension:[" + rs.getString("DIMENSION_VIS_ID") + "]" + " hierarchy:[" + rs.getString("HIERARCHY_VIS_ID") + "]");
/*      */ 
/* 1175 */         count++;
/*      */       }
/*      */ 
/* 1178 */       int i = count;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1182 */       throw handleSQLException("checkConstraintViolations", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1186 */       closeResultSet(rs);
/* 1187 */       sqlExecutor.close(); } //throw localObject;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extsys.ExtSysHierarchyDAO
 * JD-Core Version:    0.6.0
 */