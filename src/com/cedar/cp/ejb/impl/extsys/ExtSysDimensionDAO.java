/*      */ package com.cedar.cp.ejb.impl.extsys;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysDimElementCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysDimElementPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysDimensionCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysDimensionPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysDimensionRefImpl;
/*      */ import com.cedar.cp.dto.extsys.ExtSysHierarchyCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysHierarchyPK;
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
/*      */ public class ExtSysDimensionDAO extends AbstractDAO
/*      */ {
/*   34 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID,EXT_SYS_DIMENSION.DESCRIPTION,EXT_SYS_DIMENSION.DIMENSION_TYPE,EXT_SYS_DIMENSION.IMPORT_COLUMN_INDEX";
/*      */   protected static final String SQL_LOAD = " from EXT_SYS_DIMENSION where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into EXT_SYS_DIMENSION ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,DESCRIPTION,DIMENSION_TYPE,IMPORT_COLUMN_INDEX) values ( ?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update EXT_SYS_DIMENSION set DESCRIPTION = ?,DIMENSION_TYPE = ?,IMPORT_COLUMN_INDEX = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from EXT_SYS_DIMENSION where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ";
/*  493 */   private static String[][] SQL_DELETE_CHILDREN = { { "EXT_SYS_DIM_ELEMENT", "delete from EXT_SYS_DIM_ELEMENT where     EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID = ? and EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID = ? and EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID = ? " }, { "EXT_SYS_HIERARCHY", "delete from EXT_SYS_HIERARCHY where     EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = ? and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = ? and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = ? " } };
/*      */ 
/*  513 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "EXT_SYS_HIER_ELEMENT", "delete from EXT_SYS_HIER_ELEMENT ExtSysHierElement where exists (select * from EXT_SYS_HIER_ELEMENT,EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION where     EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and ExtSysHierElement.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID and ExtSysHierElement.COMPANY_VIS_ID = EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID and ExtSysHierElement.LEDGER_VIS_ID = EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID and ExtSysHierElement.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID and ExtSysHierElement.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID " }, { "EXT_SYS_HIER_ELEM_FEED", "delete from EXT_SYS_HIER_ELEM_FEED ExtSysHierElemFeed where exists (select * from EXT_SYS_HIER_ELEM_FEED,EXT_SYS_HIER_ELEMENT,EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION where     EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID = EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID = EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID and EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID = EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and ExtSysHierElemFeed.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID and ExtSysHierElemFeed.COMPANY_VIS_ID = EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID and ExtSysHierElemFeed.LEDGER_VIS_ID = EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID and ExtSysHierElemFeed.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID and ExtSysHierElemFeed.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID and ExtSysHierElemFeed.HIER_ELEMENT_VIS_ID = EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID " } };
/*      */ 
/*  569 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = ?and EXT_SYS_DIMENSION.COMPANY_VIS_ID = ?and EXT_SYS_DIMENSION.LEDGER_VIS_ID = ?and EXT_SYS_DIMENSION.DIMENSION_VIS_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID ,EXT_SYS_DIMENSION.COMPANY_VIS_ID ,EXT_SYS_DIMENSION.LEDGER_VIS_ID ,EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID ,EXT_SYS_DIMENSION.COMPANY_VIS_ID ,EXT_SYS_DIMENSION.LEDGER_VIS_ID ,EXT_SYS_DIMENSION.DIMENSION_VIS_ID";
/*      */   protected static final String SQL_GET_ALL = " from EXT_SYS_DIMENSION where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ";
/*      */   protected ExtSysDimElementDAO mExtSysDimElementDAO;
/*      */   protected ExtSysHierarchyDAO mExtSysHierarchyDAO;
/*      */   protected ExtSysDimensionEVO mDetails;
/*      */ 
/*      */   public ExtSysDimensionDAO(Connection connection)
/*      */   {
/*   41 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ExtSysDimensionDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ExtSysDimensionDAO(DataSource ds)
/*      */   {
/*   57 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ExtSysDimensionPK getPK()
/*      */   {
/*   65 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ExtSysDimensionEVO details)
/*      */   {
/*   74 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private ExtSysDimensionEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   92 */     int col = 1;
/*   93 */     ExtSysDimensionEVO evo = new ExtSysDimensionEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), null, null);
/*      */ 
/*  105 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ExtSysDimensionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  110 */     int col = startCol_;
/*  111 */     stmt_.setInt(col++, evo_.getExternalSystemId());
/*  112 */     stmt_.setString(col++, evo_.getCompanyVisId());
/*  113 */     stmt_.setString(col++, evo_.getLedgerVisId());
/*  114 */     stmt_.setString(col++, evo_.getDimensionVisId());
/*  115 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ExtSysDimensionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  120 */     int col = startCol_;
/*  121 */     stmt_.setString(col++, evo_.getDescription());
/*  122 */     stmt_.setInt(col++, evo_.getDimensionType());
/*  123 */     stmt_.setInt(col++, evo_.getImportColumnIndex());
/*  124 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ExtSysDimensionPK pk)
/*      */     throws ValidationException
/*      */   {
/*  143 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  145 */     PreparedStatement stmt = null;
/*  146 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  150 */       stmt = getConnection().prepareStatement("select EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID,EXT_SYS_DIMENSION.DESCRIPTION,EXT_SYS_DIMENSION.DIMENSION_TYPE,EXT_SYS_DIMENSION.IMPORT_COLUMN_INDEX from EXT_SYS_DIMENSION where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ");
/*      */ 
/*  153 */       int col = 1;
/*  154 */       stmt.setInt(col++, pk.getExternalSystemId());
/*  155 */       stmt.setString(col++, pk.getCompanyVisId());
/*  156 */       stmt.setString(col++, pk.getLedgerVisId());
/*  157 */       stmt.setString(col++, pk.getDimensionVisId());
/*      */ 
/*  159 */       resultSet = stmt.executeQuery();
/*      */ 
/*  161 */       if (!resultSet.next()) {
/*  162 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  165 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  166 */       if (this.mDetails.isModified())
/*  167 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  171 */       throw handleSQLException(pk, "select EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID,EXT_SYS_DIMENSION.DESCRIPTION,EXT_SYS_DIMENSION.DIMENSION_TYPE,EXT_SYS_DIMENSION.IMPORT_COLUMN_INDEX from EXT_SYS_DIMENSION where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  175 */       closeResultSet(resultSet);
/*  176 */       closeStatement(stmt);
/*  177 */       closeConnection();
/*      */ 
/*  179 */       if (timer != null)
/*  180 */         timer.logDebug("doLoad", pk);
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
/*  218 */       stmt = getConnection().prepareStatement("insert into EXT_SYS_DIMENSION ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,DESCRIPTION,DIMENSION_TYPE,IMPORT_COLUMN_INDEX) values ( ?,?,?,?,?,?,?)");
/*      */ 
/*  221 */       int col = 1;
/*  222 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  223 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  226 */       int resultCount = stmt.executeUpdate();
/*  227 */       if (resultCount != 1)
/*      */       {
/*  229 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  232 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  236 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXT_SYS_DIMENSION ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,DESCRIPTION,DIMENSION_TYPE,IMPORT_COLUMN_INDEX) values ( ?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  240 */       closeStatement(stmt);
/*  241 */       closeConnection();
/*      */ 
/*  243 */       if (timer != null) {
/*  244 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  250 */       getExtSysDimElementDAO().update(this.mDetails.getExtSysDimElementsMap());
/*      */ 
/*  252 */       getExtSysHierarchyDAO().update(this.mDetails.getExtSysHierarchiesMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  258 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  283 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  287 */     PreparedStatement stmt = null;
/*      */ 
/*  289 */     boolean mainChanged = this.mDetails.isModified();
/*  290 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  294 */       if (getExtSysDimElementDAO().update(this.mDetails.getExtSysDimElementsMap())) {
/*  295 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  298 */       if (getExtSysHierarchyDAO().update(this.mDetails.getExtSysHierarchiesMap())) {
/*  299 */         dependantChanged = true;
/*      */       }
/*  301 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  303 */         stmt = getConnection().prepareStatement("update EXT_SYS_DIMENSION set DESCRIPTION = ?,DIMENSION_TYPE = ?,IMPORT_COLUMN_INDEX = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ");
/*      */ 
/*  306 */         int col = 1;
/*  307 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  308 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  311 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  314 */         if (resultCount != 1) {
/*  315 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  318 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  327 */       throw handleSQLException(getPK(), "update EXT_SYS_DIMENSION set DESCRIPTION = ?,DIMENSION_TYPE = ?,IMPORT_COLUMN_INDEX = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  331 */       closeStatement(stmt);
/*  332 */       closeConnection();
/*      */ 
/*  334 */       if ((timer != null) && (
/*  335 */         (mainChanged) || (dependantChanged)))
/*  336 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  358 */     if (items == null) {
/*  359 */       return false;
/*      */     }
/*  361 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  362 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  364 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  368 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  369 */       while (iter3.hasNext())
/*      */       {
/*  371 */         this.mDetails = ((ExtSysDimensionEVO)iter3.next());
/*  372 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  374 */         somethingChanged = true;
/*      */ 
/*  377 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  381 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  382 */       while (iter2.hasNext())
/*      */       {
/*  384 */         this.mDetails = ((ExtSysDimensionEVO)iter2.next());
/*      */ 
/*  387 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  389 */         somethingChanged = true;
/*      */ 
/*  392 */         if (deleteStmt == null) {
/*  393 */           deleteStmt = getConnection().prepareStatement("delete from EXT_SYS_DIMENSION where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ");
/*      */         }
/*      */ 
/*  396 */         int col = 1;
/*  397 */         deleteStmt.setInt(col++, this.mDetails.getExternalSystemId());
/*  398 */         deleteStmt.setString(col++, this.mDetails.getCompanyVisId());
/*  399 */         deleteStmt.setString(col++, this.mDetails.getLedgerVisId());
/*  400 */         deleteStmt.setString(col++, this.mDetails.getDimensionVisId());
/*      */ 
/*  402 */         if (this._log.isDebugEnabled()) {
/*  403 */           this._log.debug("update", "ExtSysDimension deleting ExternalSystemId=" + this.mDetails.getExternalSystemId() + ",CompanyVisId=" + this.mDetails.getCompanyVisId() + ",LedgerVisId=" + this.mDetails.getLedgerVisId() + ",DimensionVisId=" + this.mDetails.getDimensionVisId());
/*      */         }
/*      */ 
/*  411 */         deleteStmt.addBatch();
/*      */ 
/*  414 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  419 */       if (deleteStmt != null)
/*      */       {
/*  421 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  423 */         deleteStmt.executeBatch();
/*      */ 
/*  425 */         if (timer2 != null) {
/*  426 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  430 */       Iterator iter1 = items.values().iterator();
/*  431 */       while (iter1.hasNext())
/*      */       {
/*  433 */         this.mDetails = ((ExtSysDimensionEVO)iter1.next());
/*      */ 
/*  435 */         if (this.mDetails.insertPending())
/*      */         {
/*  437 */           somethingChanged = true;
/*  438 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  441 */         if (this.mDetails.isModified())
/*      */         {
/*  443 */           somethingChanged = true;
/*  444 */           doStore(); continue;
/*      */         }
/*      */ 
/*  448 */         if (this.mDetails.deletePending())
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  454 */         if (getExtSysDimElementDAO().update(this.mDetails.getExtSysDimElementsMap())) {
/*  455 */           somethingChanged = true;
/*      */         }
/*      */ 
/*  458 */         if (getExtSysHierarchyDAO().update(this.mDetails.getExtSysHierarchiesMap())) {
/*  459 */           somethingChanged = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  471 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  475 */       throw handleSQLException("delete from EXT_SYS_DIMENSION where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  479 */       if (deleteStmt != null)
/*      */       {
/*  481 */         closeStatement(deleteStmt);
/*  482 */         closeConnection();
/*      */       }
/*      */ 
/*  485 */       this.mDetails = null;
/*      */ 
/*  487 */       if ((somethingChanged) && 
/*  488 */         (timer != null))
/*  489 */         timer.logDebug("update", "collection"); 
/*  489 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ExtSysDimensionPK pk)
/*      */   {
/*  581 */     Set emptyStrings = Collections.emptySet();
/*  582 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ExtSysDimensionPK pk, Set<String> exclusionTables)
/*      */   {
/*  588 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  590 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  592 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  594 */       PreparedStatement stmt = null;
/*      */ 
/*  596 */       int resultCount = 0;
/*  597 */       String s = null;
/*      */       try
/*      */       {
/*  600 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  602 */         if (this._log.isDebugEnabled()) {
/*  603 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  605 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  608 */         int col = 1;
/*  609 */         stmt.setInt(col++, pk.getExternalSystemId());
/*  610 */         stmt.setString(col++, pk.getCompanyVisId());
/*  611 */         stmt.setString(col++, pk.getLedgerVisId());
/*  612 */         stmt.setString(col++, pk.getDimensionVisId());
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
/*  626 */         if (timer != null) {
/*  627 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  631 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  633 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  635 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  637 */       PreparedStatement stmt = null;
/*      */ 
/*  639 */       int resultCount = 0;
/*  640 */       String s = null;
/*      */       try
/*      */       {
/*  643 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  645 */         if (this._log.isDebugEnabled()) {
/*  646 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  648 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  651 */         int col = 1;
/*  652 */         stmt.setInt(col++, pk.getExternalSystemId());
/*  653 */         stmt.setString(col++, pk.getCompanyVisId());
/*  654 */         stmt.setString(col++, pk.getLedgerVisId());
/*  655 */         stmt.setString(col++, pk.getDimensionVisId());
/*      */ 
/*  658 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  662 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  666 */         closeStatement(stmt);
/*  667 */         closeConnection();
/*      */ 
/*  669 */         if (timer != null)
/*  670 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ExternalSystemPK entityPK, Collection owners, String dependants)
/*      */   {
/*  705 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  707 */     PreparedStatement stmt = null;
/*  708 */     ResultSet resultSet = null;
/*      */ 
/*  710 */     int itemCount = 0;
/*      */ 
/*  712 */     ExtSysLedgerEVO owningEVO = null;
/*  713 */     Iterator ownersIter = owners.iterator();
/*  714 */     while (ownersIter.hasNext())
/*      */     {
/*  716 */       owningEVO = (ExtSysLedgerEVO)ownersIter.next();
/*  717 */       owningEVO.setExtSysDimensionsAllItemsLoaded(true);
/*      */     }
/*  719 */     ownersIter = owners.iterator();
/*  720 */     owningEVO = (ExtSysLedgerEVO)ownersIter.next();
/*  721 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  725 */       stmt = getConnection().prepareStatement("select EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID,EXT_SYS_DIMENSION.DESCRIPTION,EXT_SYS_DIMENSION.DIMENSION_TYPE,EXT_SYS_DIMENSION.IMPORT_COLUMN_INDEX from EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID ,EXT_SYS_DIMENSION.COMPANY_VIS_ID ,EXT_SYS_DIMENSION.LEDGER_VIS_ID ,EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID ,EXT_SYS_DIMENSION.COMPANY_VIS_ID ,EXT_SYS_DIMENSION.LEDGER_VIS_ID ,EXT_SYS_DIMENSION.DIMENSION_VIS_ID");
/*      */ 
/*  727 */       int col = 1;
/*  728 */       stmt.setInt(col++, entityPK.getExternalSystemId());
/*      */ 
/*  730 */       resultSet = stmt.executeQuery();
/*      */ 
/*  733 */       while (resultSet.next())
/*      */       {
/*  735 */         itemCount++;
/*  736 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  741 */         while ((this.mDetails.getExternalSystemId() != owningEVO.getExternalSystemId()) || (!this.mDetails.getCompanyVisId().equals(owningEVO.getCompanyVisId())) || (!this.mDetails.getLedgerVisId().equals(owningEVO.getLedgerVisId())))
/*      */         {
/*  747 */           if (!ownersIter.hasNext())
/*      */           {
/*  749 */             this._log.debug("bulkGetAll", "can't find owning [ExternalSystemId=" + this.mDetails.getExternalSystemId() + "CompanyVisId=" + this.mDetails.getCompanyVisId() + "LedgerVisId=" + this.mDetails.getLedgerVisId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  755 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  756 */             ownersIter = owners.iterator();
/*  757 */             while (ownersIter.hasNext())
/*      */             {
/*  759 */               owningEVO = (ExtSysLedgerEVO)ownersIter.next();
/*  760 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  762 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  764 */           owningEVO = (ExtSysLedgerEVO)ownersIter.next();
/*      */         }
/*  766 */         if (owningEVO.getExtSysDimensions() == null)
/*      */         {
/*  768 */           theseItems = new ArrayList();
/*  769 */           owningEVO.setExtSysDimensions(theseItems);
/*  770 */           owningEVO.setExtSysDimensionsAllItemsLoaded(true);
/*      */         }
/*  772 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  775 */       if (timer != null) {
/*  776 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  779 */       if ((itemCount > 0) && (dependants.indexOf("<3>") > -1))
/*      */       {
/*  781 */         getExtSysDimElementDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*  783 */       if ((itemCount > 0) && (dependants.indexOf("<4>") > -1))
/*      */       {
/*  785 */         getExtSysHierarchyDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  789 */       throw handleSQLException("select EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID,EXT_SYS_DIMENSION.DESCRIPTION,EXT_SYS_DIMENSION.DIMENSION_TYPE,EXT_SYS_DIMENSION.IMPORT_COLUMN_INDEX from EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID ,EXT_SYS_DIMENSION.COMPANY_VIS_ID ,EXT_SYS_DIMENSION.LEDGER_VIS_ID ,EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID ,EXT_SYS_DIMENSION.COMPANY_VIS_ID ,EXT_SYS_DIMENSION.LEDGER_VIS_ID ,EXT_SYS_DIMENSION.DIMENSION_VIS_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  793 */       closeResultSet(resultSet);
/*  794 */       closeStatement(stmt);
/*  795 */       closeConnection();
/*      */ 
/*  797 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectExternalSystemId, String selectCompanyVisId, String selectLedgerVisId, String dependants, Collection currentList)
/*      */   {
/*  828 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  829 */     PreparedStatement stmt = null;
/*  830 */     ResultSet resultSet = null;
/*      */ 
/*  832 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  836 */       stmt = getConnection().prepareStatement("select EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID,EXT_SYS_DIMENSION.DESCRIPTION,EXT_SYS_DIMENSION.DIMENSION_TYPE,EXT_SYS_DIMENSION.IMPORT_COLUMN_INDEX from EXT_SYS_DIMENSION where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ");
/*      */ 
/*  838 */       int col = 1;
/*  839 */       stmt.setInt(col++, selectExternalSystemId);
/*  840 */       stmt.setString(col++, selectCompanyVisId);
/*  841 */       stmt.setString(col++, selectLedgerVisId);
/*      */ 
/*  843 */       resultSet = stmt.executeQuery();
/*      */ 
/*  845 */       while (resultSet.next())
/*      */       {
/*  847 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  850 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  853 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  856 */       if (currentList != null)
/*      */       {
/*  859 */         ListIterator iter = items.listIterator();
/*  860 */         ExtSysDimensionEVO currentEVO = null;
/*  861 */         ExtSysDimensionEVO newEVO = null;
/*  862 */         while (iter.hasNext())
/*      */         {
/*  864 */           newEVO = (ExtSysDimensionEVO)iter.next();
/*  865 */           Iterator iter2 = currentList.iterator();
/*  866 */           while (iter2.hasNext())
/*      */           {
/*  868 */             currentEVO = (ExtSysDimensionEVO)iter2.next();
/*  869 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  871 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  877 */         Iterator iter2 = currentList.iterator();
/*  878 */         while (iter2.hasNext())
/*      */         {
/*  880 */           currentEVO = (ExtSysDimensionEVO)iter2.next();
/*  881 */           if (currentEVO.insertPending()) {
/*  882 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  886 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  890 */       throw handleSQLException("select EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID,EXT_SYS_DIMENSION.DESCRIPTION,EXT_SYS_DIMENSION.DIMENSION_TYPE,EXT_SYS_DIMENSION.IMPORT_COLUMN_INDEX from EXT_SYS_DIMENSION where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  894 */       closeResultSet(resultSet);
/*  895 */       closeStatement(stmt);
/*  896 */       closeConnection();
/*      */ 
/*  898 */       if (timer != null) {
/*  899 */         timer.logDebug("getAll", " ExternalSystemId=" + selectExternalSystemId + ",CompanyVisId=" + selectCompanyVisId + ",LedgerVisId=" + selectLedgerVisId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  906 */     return items;
/*      */   }
/*      */ 
/*      */   public ExtSysDimensionEVO getDetails(ExternalSystemCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  929 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  932 */     if (this.mDetails == null) {
/*  933 */       doLoad(((ExtSysDimensionCK)paramCK).getExtSysDimensionPK());
/*      */     }
/*  935 */     else if (!this.mDetails.getPK().equals(((ExtSysDimensionCK)paramCK).getExtSysDimensionPK())) {
/*  936 */       doLoad(((ExtSysDimensionCK)paramCK).getExtSysDimensionPK());
/*      */     }
/*      */ 
/*  939 */     if ((dependants.indexOf("<3>") > -1) && (!this.mDetails.isExtSysDimElementsAllItemsLoaded()))
/*      */     {
/*  944 */       this.mDetails.setExtSysDimElements(getExtSysDimElementDAO().getAll(this.mDetails.getExternalSystemId(), this.mDetails.getCompanyVisId(), this.mDetails.getLedgerVisId(), this.mDetails.getDimensionVisId(), dependants, this.mDetails.getExtSysDimElements()));
/*      */ 
/*  954 */       this.mDetails.setExtSysDimElementsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  958 */     if ((dependants.indexOf("<4>") > -1) && (!this.mDetails.isExtSysHierarchiesAllItemsLoaded()))
/*      */     {
/*  963 */       this.mDetails.setExtSysHierarchies(getExtSysHierarchyDAO().getAll(this.mDetails.getExternalSystemId(), this.mDetails.getCompanyVisId(), this.mDetails.getLedgerVisId(), this.mDetails.getDimensionVisId(), dependants, this.mDetails.getExtSysHierarchies()));
/*      */ 
/*  973 */       this.mDetails.setExtSysHierarchiesAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  976 */     if ((paramCK instanceof ExtSysDimElementCK))
/*      */     {
/*  978 */       if (this.mDetails.getExtSysDimElements() == null) {
/*  979 */         this.mDetails.loadExtSysDimElementsItem(getExtSysDimElementDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  982 */         ExtSysDimElementPK pk = ((ExtSysDimElementCK)paramCK).getExtSysDimElementPK();
/*  983 */         ExtSysDimElementEVO evo = this.mDetails.getExtSysDimElementsItem(pk);
/*  984 */         if (evo == null) {
/*  985 */           this.mDetails.loadExtSysDimElementsItem(getExtSysDimElementDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*  989 */     else if ((paramCK instanceof ExtSysHierarchyCK))
/*      */     {
/*  991 */       if (this.mDetails.getExtSysHierarchies() == null) {
/*  992 */         this.mDetails.loadExtSysHierarchiesItem(getExtSysHierarchyDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  995 */         ExtSysHierarchyPK pk = ((ExtSysHierarchyCK)paramCK).getExtSysHierarchyPK();
/*  996 */         ExtSysHierarchyEVO evo = this.mDetails.getExtSysHierarchiesItem(pk);
/*  997 */         if (evo == null)
/*  998 */           this.mDetails.loadExtSysHierarchiesItem(getExtSysHierarchyDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1000 */           getExtSysHierarchyDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1005 */     ExtSysDimensionEVO details = new ExtSysDimensionEVO();
/* 1006 */     details = this.mDetails.deepClone();
/*      */ 
/* 1008 */     if (timer != null) {
/* 1009 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1011 */     return details;
/*      */   }
/*      */ 
/*      */   public ExtSysDimensionEVO getDetails(ExternalSystemCK paramCK, ExtSysDimensionEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1017 */     ExtSysDimensionEVO savedEVO = this.mDetails;
/* 1018 */     this.mDetails = paramEVO;
/* 1019 */     ExtSysDimensionEVO newEVO = getDetails(paramCK, dependants);
/* 1020 */     this.mDetails = savedEVO;
/* 1021 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public ExtSysDimensionEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1027 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1031 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1034 */     ExtSysDimensionEVO details = this.mDetails.deepClone();
/*      */ 
/* 1036 */     if (timer != null) {
/* 1037 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1039 */     return details;
/*      */   }
/*      */ 
/*      */   protected ExtSysDimElementDAO getExtSysDimElementDAO()
/*      */   {
/* 1048 */     if (this.mExtSysDimElementDAO == null)
/*      */     {
/* 1050 */       if (this.mDataSource != null)
/* 1051 */         this.mExtSysDimElementDAO = new ExtSysDimElementDAO(this.mDataSource);
/*      */       else {
/* 1053 */         this.mExtSysDimElementDAO = new ExtSysDimElementDAO(getConnection());
/*      */       }
/*      */     }
/* 1056 */     return this.mExtSysDimElementDAO;
/*      */   }
/*      */ 
/*      */   protected ExtSysHierarchyDAO getExtSysHierarchyDAO()
/*      */   {
/* 1065 */     if (this.mExtSysHierarchyDAO == null)
/*      */     {
/* 1067 */       if (this.mDataSource != null)
/* 1068 */         this.mExtSysHierarchyDAO = new ExtSysHierarchyDAO(this.mDataSource);
/*      */       else {
/* 1070 */         this.mExtSysHierarchyDAO = new ExtSysHierarchyDAO(getConnection());
/*      */       }
/*      */     }
/* 1073 */     return this.mExtSysHierarchyDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1078 */     return "ExtSysDimension";
/*      */   }
/*      */ 
/*      */   public ExtSysDimensionRefImpl getRef(ExtSysDimensionPK paramExtSysDimensionPK)
/*      */   {
/* 1083 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1084 */     PreparedStatement stmt = null;
/* 1085 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1088 */       stmt = getConnection().prepareStatement("select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID from EXT_SYS_DIMENSION,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_LEDGER where 1=1 and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_DIMENSION.COMPANY_VIS_ID = ? and EXT_SYS_DIMENSION.LEDGER_VIS_ID = ? and EXT_SYS_DIMENSION.DIMENSION_VIS_ID = ? and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_LEDGER.LEDGER_VIS_ID = EXT_SYS_COMPANY.LEDGER_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID");
/* 1089 */       int col = 1;
/* 1090 */       stmt.setInt(col++, paramExtSysDimensionPK.getExternalSystemId());
/* 1091 */       stmt.setString(col++, paramExtSysDimensionPK.getCompanyVisId());
/* 1092 */       stmt.setString(col++, paramExtSysDimensionPK.getLedgerVisId());
/* 1093 */       stmt.setString(col++, paramExtSysDimensionPK.getDimensionVisId());
/*      */ 
/* 1095 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1097 */       if (!resultSet.next()) {
/* 1098 */         throw new RuntimeException(getEntityName() + " getRef " + paramExtSysDimensionPK + " not found");
/*      */       }
/* 1100 */       col = 2;
/* 1101 */       ExternalSystemPK newExternalSystemPK = new ExternalSystemPK(resultSet.getInt(col++));
/*      */ 
/* 1105 */       ExtSysCompanyPK newExtSysCompanyPK = new ExtSysCompanyPK(resultSet.getInt(col++), resultSet.getString(col++));
/*      */ 
/* 1110 */       ExtSysLedgerPK newExtSysLedgerPK = new ExtSysLedgerPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++));
/*      */ 
/* 1116 */       String textExtSysDimension = "";
/* 1117 */       ExtSysDimensionCK ckExtSysDimension = new ExtSysDimensionCK(newExternalSystemPK, newExtSysCompanyPK, newExtSysLedgerPK, paramExtSysDimensionPK);
/*      */ 
/* 1124 */       ExtSysDimensionRefImpl localExtSysDimensionRefImpl = new ExtSysDimensionRefImpl(ckExtSysDimension, textExtSysDimension);
/*      */       return localExtSysDimensionRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1129 */       throw handleSQLException(paramExtSysDimensionPK, "select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID from EXT_SYS_DIMENSION,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_LEDGER where 1=1 and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_DIMENSION.COMPANY_VIS_ID = ? and EXT_SYS_DIMENSION.LEDGER_VIS_ID = ? and EXT_SYS_DIMENSION.DIMENSION_VIS_ID = ? and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_LEDGER.LEDGER_VIS_ID = EXT_SYS_COMPANY.LEDGER_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1133 */       closeResultSet(resultSet);
/* 1134 */       closeStatement(stmt);
/* 1135 */       closeConnection();
/*      */ 
/* 1137 */       if (timer != null)
/* 1138 */         timer.logDebug("getRef", paramExtSysDimensionPK); 
/* 1138 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1150 */     if (c == null)
/* 1151 */       return;
/* 1152 */     Iterator iter = c.iterator();
/* 1153 */     while (iter.hasNext())
/*      */     {
/* 1155 */       ExtSysDimensionEVO evo = (ExtSysDimensionEVO)iter.next();
/* 1156 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(ExtSysDimensionEVO evo, String dependants)
/*      */   {
/* 1170 */     if (evo.insertPending()) {
/* 1171 */       return;
/*      */     }
/*      */ 
/* 1175 */     if (dependants.indexOf("<3>") > -1)
/*      */     {
/* 1178 */       if (!evo.isExtSysDimElementsAllItemsLoaded())
/*      */       {
/* 1180 */         evo.setExtSysDimElements(getExtSysDimElementDAO().getAll(evo.getExternalSystemId(), evo.getCompanyVisId(), evo.getLedgerVisId(), evo.getDimensionVisId(), dependants, evo.getExtSysDimElements()));
/*      */ 
/* 1190 */         evo.setExtSysDimElementsAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1195 */     if ((dependants.indexOf("<4>") > -1) || (dependants.indexOf("<5>") > -1) || (dependants.indexOf("<6>") > -1))
/*      */     {
/* 1200 */       if (!evo.isExtSysHierarchiesAllItemsLoaded())
/*      */       {
/* 1202 */         evo.setExtSysHierarchies(getExtSysHierarchyDAO().getAll(evo.getExternalSystemId(), evo.getCompanyVisId(), evo.getLedgerVisId(), evo.getDimensionVisId(), dependants, evo.getExtSysHierarchies()));
/*      */ 
/* 1212 */         evo.setExtSysHierarchiesAllItemsLoaded(true);
/*      */       }
/* 1214 */       getExtSysHierarchyDAO().getDependants(evo.getExtSysHierarchies(), dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void insert(ExtSysDimensionEVO evo)
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/* 1231 */     setDetails(evo);
/* 1232 */     doCreate();
/*      */   }
/*      */ 
/*      */   public int checkConstraintViolations(int externalSystemId, int maxToReport, PrintWriter log)
/*      */   {
/* 1244 */     SqlExecutor sqlExecutor = null;
/* 1245 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1249 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "select external_system_id, company_vis_id, ledger_vis_id, dimension_vis_id", "from ext_sys_dimension", "where external_system_id = <externalSystemId>", "group by external_system_id, company_vis_id, ledger_vis_id, dimension_vis_id", "having count(*) > 1" });
/*      */ 
/* 1255 */       sqlExecutor = new SqlExecutor("checkConstraintViolations", getDataSource(), sqlBuilder, this._log);
/*      */ 
/* 1257 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/*      */ 
/* 1259 */       rs = sqlExecutor.getResultSet();
/*      */ 
/* 1261 */       int count = 0;
/* 1262 */       while ((rs.next()) && (count < maxToReport))
/*      */       {
/* 1264 */         log.print("Found duplicate dimension details: company:[" + rs.getString("COMPANY_VIS_ID") + "] " + " ledger:[" + rs.getString("LEDGER_VIS_ID") + "]" + " dimension:[" + rs.getString("DIMENSION_VIS_ID") + "]");
/*      */ 
/* 1268 */         count++;
/*      */       }
/*      */ 
/* 1271 */       int i = count;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1275 */       throw handleSQLException("checkConstraintViolations", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1279 */       closeResultSet(rs);
/* 1280 */       sqlExecutor.close(); } //throw localObject;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extsys.ExtSysDimensionDAO
 * JD-Core Version:    0.6.0
 */