/*      */ package com.cedar.cp.ejb.impl.extsys;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysDimensionPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysHierElemFeedCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysHierElemFeedPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysHierElementCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysHierElementPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysHierElementRefImpl;
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
/*      */ public class ExtSysHierElementDAO extends AbstractDAO
/*      */ {
/*   34 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEMENT.DESCRIPTION,EXT_SYS_HIER_ELEMENT.PARENT_VIS_ID,EXT_SYS_HIER_ELEMENT.IDX,EXT_SYS_HIER_ELEMENT.CREDIT_DEBIT";
/*      */   protected static final String SQL_LOAD = " from EXT_SYS_HIER_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into EXT_SYS_HIER_ELEMENT ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,HIERARCHY_VIS_ID,HIER_ELEMENT_VIS_ID,DESCRIPTION,PARENT_VIS_ID,IDX,CREDIT_DEBIT) values ( ?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update EXT_SYS_HIER_ELEMENT set DESCRIPTION = ?,PARENT_VIS_ID = ?,IDX = ?,CREDIT_DEBIT = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from EXT_SYS_HIER_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? ";
/*  510 */   private static String[][] SQL_DELETE_CHILDREN = { { "EXT_SYS_HIER_ELEM_FEED", "delete from EXT_SYS_HIER_ELEM_FEED where     EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID = ? and EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID = ? " } };
/*      */ 
/*  524 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  528 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = ?and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = ?and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = ?and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = ?and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = ?and EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from EXT_SYS_HIER_ELEMENT,EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID ,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID ,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID ,EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID ,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID ,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID ,EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID";
/*      */   protected static final String SQL_GET_ALL = " from EXT_SYS_HIER_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? ";
/*      */   protected ExtSysHierElemFeedDAO mExtSysHierElemFeedDAO;
/*      */   protected ExtSysHierElementEVO mDetails;
/*      */ 
/*      */   public ExtSysHierElementDAO(Connection connection)
/*      */   {
/*   41 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ExtSysHierElementDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ExtSysHierElementDAO(DataSource ds)
/*      */   {
/*   57 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ExtSysHierElementPK getPK()
/*      */   {
/*   65 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ExtSysHierElementEVO details)
/*      */   {
/*   74 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private ExtSysHierElementEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   95 */     int col = 1;
/*   96 */     ExtSysHierElementEVO evo = new ExtSysHierElementEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  110 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ExtSysHierElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  115 */     int col = startCol_;
/*  116 */     stmt_.setInt(col++, evo_.getExternalSystemId());
/*  117 */     stmt_.setString(col++, evo_.getCompanyVisId());
/*  118 */     stmt_.setString(col++, evo_.getLedgerVisId());
/*  119 */     stmt_.setString(col++, evo_.getDimensionVisId());
/*  120 */     stmt_.setString(col++, evo_.getHierarchyVisId());
/*  121 */     stmt_.setString(col++, evo_.getHierElementVisId());
/*  122 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ExtSysHierElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  127 */     int col = startCol_;
/*  128 */     stmt_.setString(col++, evo_.getDescription());
/*  129 */     stmt_.setString(col++, evo_.getParentVisId());
/*  130 */     stmt_.setInt(col++, evo_.getIdx());
/*  131 */     stmt_.setInt(col++, evo_.getCreditDebit());
/*  132 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ExtSysHierElementPK pk)
/*      */     throws ValidationException
/*      */   {
/*  153 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  155 */     PreparedStatement stmt = null;
/*  156 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  160 */       stmt = getConnection().prepareStatement("select EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEMENT.DESCRIPTION,EXT_SYS_HIER_ELEMENT.PARENT_VIS_ID,EXT_SYS_HIER_ELEMENT.IDX,EXT_SYS_HIER_ELEMENT.CREDIT_DEBIT from EXT_SYS_HIER_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? ");
/*      */ 
/*  163 */       int col = 1;
/*  164 */       stmt.setInt(col++, pk.getExternalSystemId());
/*  165 */       stmt.setString(col++, pk.getCompanyVisId());
/*  166 */       stmt.setString(col++, pk.getLedgerVisId());
/*  167 */       stmt.setString(col++, pk.getDimensionVisId());
/*  168 */       stmt.setString(col++, pk.getHierarchyVisId());
/*  169 */       stmt.setString(col++, pk.getHierElementVisId());
/*      */ 
/*  171 */       resultSet = stmt.executeQuery();
/*      */ 
/*  173 */       if (!resultSet.next()) {
/*  174 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  177 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  178 */       if (this.mDetails.isModified())
/*  179 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  183 */       throw handleSQLException(pk, "select EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEMENT.DESCRIPTION,EXT_SYS_HIER_ELEMENT.PARENT_VIS_ID,EXT_SYS_HIER_ELEMENT.IDX,EXT_SYS_HIER_ELEMENT.CREDIT_DEBIT from EXT_SYS_HIER_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  187 */       closeResultSet(resultSet);
/*  188 */       closeStatement(stmt);
/*  189 */       closeConnection();
/*      */ 
/*  191 */       if (timer != null)
/*  192 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  229 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  230 */     this.mDetails.postCreateInit();
/*      */ 
/*  232 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  236 */       stmt = getConnection().prepareStatement("insert into EXT_SYS_HIER_ELEMENT ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,HIERARCHY_VIS_ID,HIER_ELEMENT_VIS_ID,DESCRIPTION,PARENT_VIS_ID,IDX,CREDIT_DEBIT) values ( ?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  239 */       int col = 1;
/*  240 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  241 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  244 */       int resultCount = stmt.executeUpdate();
/*  245 */       if (resultCount != 1)
/*      */       {
/*  247 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  250 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  254 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXT_SYS_HIER_ELEMENT ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DIMENSION_VIS_ID,HIERARCHY_VIS_ID,HIER_ELEMENT_VIS_ID,DESCRIPTION,PARENT_VIS_ID,IDX,CREDIT_DEBIT) values ( ?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  258 */       closeStatement(stmt);
/*  259 */       closeConnection();
/*      */ 
/*  261 */       if (timer != null) {
/*  262 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  268 */       getExtSysHierElemFeedDAO().update(this.mDetails.getExtSysHierElemFeedsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  274 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  302 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  306 */     PreparedStatement stmt = null;
/*      */ 
/*  308 */     boolean mainChanged = this.mDetails.isModified();
/*  309 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  313 */       if (getExtSysHierElemFeedDAO().update(this.mDetails.getExtSysHierElemFeedsMap())) {
/*  314 */         dependantChanged = true;
/*      */       }
/*  316 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  318 */         stmt = getConnection().prepareStatement("update EXT_SYS_HIER_ELEMENT set DESCRIPTION = ?,PARENT_VIS_ID = ?,IDX = ?,CREDIT_DEBIT = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? ");
/*      */ 
/*  321 */         int col = 1;
/*  322 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  323 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  326 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  329 */         if (resultCount != 1) {
/*  330 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  333 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  342 */       throw handleSQLException(getPK(), "update EXT_SYS_HIER_ELEMENT set DESCRIPTION = ?,PARENT_VIS_ID = ?,IDX = ?,CREDIT_DEBIT = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  346 */       closeStatement(stmt);
/*  347 */       closeConnection();
/*      */ 
/*  349 */       if ((timer != null) && (
/*  350 */         (mainChanged) || (dependantChanged)))
/*  351 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  375 */     if (items == null) {
/*  376 */       return false;
/*      */     }
/*  378 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  379 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  381 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  385 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  386 */       while (iter3.hasNext())
/*      */       {
/*  388 */         this.mDetails = ((ExtSysHierElementEVO)iter3.next());
/*  389 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  391 */         somethingChanged = true;
/*      */ 
/*  394 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  398 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  399 */       while (iter2.hasNext())
/*      */       {
/*  401 */         this.mDetails = ((ExtSysHierElementEVO)iter2.next());
/*      */ 
/*  404 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  406 */         somethingChanged = true;
/*      */ 
/*  409 */         if (deleteStmt == null) {
/*  410 */           deleteStmt = getConnection().prepareStatement("delete from EXT_SYS_HIER_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? ");
/*      */         }
/*      */ 
/*  413 */         int col = 1;
/*  414 */         deleteStmt.setInt(col++, this.mDetails.getExternalSystemId());
/*  415 */         deleteStmt.setString(col++, this.mDetails.getCompanyVisId());
/*  416 */         deleteStmt.setString(col++, this.mDetails.getLedgerVisId());
/*  417 */         deleteStmt.setString(col++, this.mDetails.getDimensionVisId());
/*  418 */         deleteStmt.setString(col++, this.mDetails.getHierarchyVisId());
/*  419 */         deleteStmt.setString(col++, this.mDetails.getHierElementVisId());
/*      */ 
/*  421 */         if (this._log.isDebugEnabled()) {
/*  422 */           this._log.debug("update", "ExtSysHierElement deleting ExternalSystemId=" + this.mDetails.getExternalSystemId() + ",CompanyVisId=" + this.mDetails.getCompanyVisId() + ",LedgerVisId=" + this.mDetails.getLedgerVisId() + ",DimensionVisId=" + this.mDetails.getDimensionVisId() + ",HierarchyVisId=" + this.mDetails.getHierarchyVisId() + ",HierElementVisId=" + this.mDetails.getHierElementVisId());
/*      */         }
/*      */ 
/*  432 */         deleteStmt.addBatch();
/*      */ 
/*  435 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  440 */       if (deleteStmt != null)
/*      */       {
/*  442 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  444 */         deleteStmt.executeBatch();
/*      */ 
/*  446 */         if (timer2 != null) {
/*  447 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  451 */       Iterator iter1 = items.values().iterator();
/*  452 */       while (iter1.hasNext())
/*      */       {
/*  454 */         this.mDetails = ((ExtSysHierElementEVO)iter1.next());
/*      */ 
/*  456 */         if (this.mDetails.insertPending())
/*      */         {
/*  458 */           somethingChanged = true;
/*  459 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  462 */         if (this.mDetails.isModified())
/*      */         {
/*  464 */           somethingChanged = true;
/*  465 */           doStore(); continue;
/*      */         }
/*      */ 
/*  469 */         if ((this.mDetails.deletePending()) || 
/*  475 */           (!getExtSysHierElemFeedDAO().update(this.mDetails.getExtSysHierElemFeedsMap()))) continue;
/*  476 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  488 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  492 */       throw handleSQLException("delete from EXT_SYS_HIER_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? AND HIER_ELEMENT_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  496 */       if (deleteStmt != null)
/*      */       {
/*  498 */         closeStatement(deleteStmt);
/*  499 */         closeConnection();
/*      */       }
/*      */ 
/*  502 */       this.mDetails = null;
/*      */ 
/*  504 */       if ((somethingChanged) && 
/*  505 */         (timer != null))
/*  506 */         timer.logDebug("update", "collection"); 
/*  506 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ExtSysHierElementPK pk)
/*      */   {
/*  542 */     Set emptyStrings = Collections.emptySet();
/*  543 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ExtSysHierElementPK pk, Set<String> exclusionTables)
/*      */   {
/*  549 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  551 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  553 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  555 */       PreparedStatement stmt = null;
/*      */ 
/*  557 */       int resultCount = 0;
/*  558 */       String s = null;
/*      */       try
/*      */       {
/*  561 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  563 */         if (this._log.isDebugEnabled()) {
/*  564 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  566 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  569 */         int col = 1;
/*  570 */         stmt.setInt(col++, pk.getExternalSystemId());
/*  571 */         stmt.setString(col++, pk.getCompanyVisId());
/*  572 */         stmt.setString(col++, pk.getLedgerVisId());
/*  573 */         stmt.setString(col++, pk.getDimensionVisId());
/*  574 */         stmt.setString(col++, pk.getHierarchyVisId());
/*  575 */         stmt.setString(col++, pk.getHierElementVisId());
/*      */ 
/*  578 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  582 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  586 */         closeStatement(stmt);
/*  587 */         closeConnection();
/*      */ 
/*  589 */         if (timer != null) {
/*  590 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  594 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  596 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  598 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  600 */       PreparedStatement stmt = null;
/*      */ 
/*  602 */       int resultCount = 0;
/*  603 */       String s = null;
/*      */       try
/*      */       {
/*  606 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  608 */         if (this._log.isDebugEnabled()) {
/*  609 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  611 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  614 */         int col = 1;
/*  615 */         stmt.setInt(col++, pk.getExternalSystemId());
/*  616 */         stmt.setString(col++, pk.getCompanyVisId());
/*  617 */         stmt.setString(col++, pk.getLedgerVisId());
/*  618 */         stmt.setString(col++, pk.getDimensionVisId());
/*  619 */         stmt.setString(col++, pk.getHierarchyVisId());
/*  620 */         stmt.setString(col++, pk.getHierElementVisId());
/*      */ 
/*  623 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  627 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  631 */         closeStatement(stmt);
/*  632 */         closeConnection();
/*      */ 
/*  634 */         if (timer != null)
/*  635 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ExternalSystemPK entityPK, Collection owners, String dependants)
/*      */   {
/*  687 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  689 */     PreparedStatement stmt = null;
/*  690 */     ResultSet resultSet = null;
/*      */ 
/*  692 */     int itemCount = 0;
/*      */ 
/*  694 */     ExtSysHierarchyEVO owningEVO = null;
/*  695 */     Iterator ownersIter = owners.iterator();
/*  696 */     while (ownersIter.hasNext())
/*      */     {
/*  698 */       owningEVO = (ExtSysHierarchyEVO)ownersIter.next();
/*  699 */       owningEVO.setExtSysHierElementsAllItemsLoaded(true);
/*      */     }
/*  701 */     ownersIter = owners.iterator();
/*  702 */     owningEVO = (ExtSysHierarchyEVO)ownersIter.next();
/*  703 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  707 */       stmt = getConnection().prepareStatement("select EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEMENT.DESCRIPTION,EXT_SYS_HIER_ELEMENT.PARENT_VIS_ID,EXT_SYS_HIER_ELEMENT.IDX,EXT_SYS_HIER_ELEMENT.CREDIT_DEBIT from EXT_SYS_HIER_ELEMENT,EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID ,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID ,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID ,EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID ,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID ,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID ,EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID");
/*      */ 
/*  709 */       int col = 1;
/*  710 */       stmt.setInt(col++, entityPK.getExternalSystemId());
/*      */ 
/*  712 */       resultSet = stmt.executeQuery();
/*      */ 
/*  715 */       while (resultSet.next())
/*      */       {
/*  717 */         itemCount++;
/*  718 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  723 */         while ((this.mDetails.getExternalSystemId() != owningEVO.getExternalSystemId()) || (!this.mDetails.getCompanyVisId().equals(owningEVO.getCompanyVisId())) || (!this.mDetails.getLedgerVisId().equals(owningEVO.getLedgerVisId())) || (!this.mDetails.getDimensionVisId().equals(owningEVO.getDimensionVisId())) || (!this.mDetails.getHierarchyVisId().equals(owningEVO.getHierarchyVisId())))
/*      */         {
/*  731 */           if (!ownersIter.hasNext())
/*      */           {
/*  733 */             this._log.debug("bulkGetAll", "can't find owning [ExternalSystemId=" + this.mDetails.getExternalSystemId() + "CompanyVisId=" + this.mDetails.getCompanyVisId() + "LedgerVisId=" + this.mDetails.getLedgerVisId() + "DimensionVisId=" + this.mDetails.getDimensionVisId() + "HierarchyVisId=" + this.mDetails.getHierarchyVisId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  741 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  742 */             ownersIter = owners.iterator();
/*  743 */             while (ownersIter.hasNext())
/*      */             {
/*  745 */               owningEVO = (ExtSysHierarchyEVO)ownersIter.next();
/*  746 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  748 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  750 */           owningEVO = (ExtSysHierarchyEVO)ownersIter.next();
/*      */         }
/*  752 */         if (owningEVO.getExtSysHierElements() == null)
/*      */         {
/*  754 */           theseItems = new ArrayList();
/*  755 */           owningEVO.setExtSysHierElements(theseItems);
/*  756 */           owningEVO.setExtSysHierElementsAllItemsLoaded(true);
/*      */         }
/*  758 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  761 */       if (timer != null) {
/*  762 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  765 */       if ((itemCount > 0) && (dependants.indexOf("<6>") > -1))
/*      */       {
/*  767 */         getExtSysHierElemFeedDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  771 */       throw handleSQLException("select EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEMENT.DESCRIPTION,EXT_SYS_HIER_ELEMENT.PARENT_VIS_ID,EXT_SYS_HIER_ELEMENT.IDX,EXT_SYS_HIER_ELEMENT.CREDIT_DEBIT from EXT_SYS_HIER_ELEMENT,EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID ,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID ,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID ,EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID ,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID ,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID ,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID ,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID ,EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  775 */       closeResultSet(resultSet);
/*  776 */       closeStatement(stmt);
/*  777 */       closeConnection();
/*      */ 
/*  779 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectExternalSystemId, String selectCompanyVisId, String selectLedgerVisId, String selectDimensionVisId, String selectHierarchyVisId, String dependants, Collection currentList)
/*      */   {
/*  816 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  817 */     PreparedStatement stmt = null;
/*  818 */     ResultSet resultSet = null;
/*      */ 
/*  820 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  824 */       stmt = getConnection().prepareStatement("select EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEMENT.DESCRIPTION,EXT_SYS_HIER_ELEMENT.PARENT_VIS_ID,EXT_SYS_HIER_ELEMENT.IDX,EXT_SYS_HIER_ELEMENT.CREDIT_DEBIT from EXT_SYS_HIER_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? ");
/*      */ 
/*  826 */       int col = 1;
/*  827 */       stmt.setInt(col++, selectExternalSystemId);
/*  828 */       stmt.setString(col++, selectCompanyVisId);
/*  829 */       stmt.setString(col++, selectLedgerVisId);
/*  830 */       stmt.setString(col++, selectDimensionVisId);
/*  831 */       stmt.setString(col++, selectHierarchyVisId);
/*      */ 
/*  833 */       resultSet = stmt.executeQuery();
/*      */ 
/*  835 */       while (resultSet.next())
/*      */       {
/*  837 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  840 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  843 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  846 */       if (currentList != null)
/*      */       {
/*  849 */         ListIterator iter = items.listIterator();
/*  850 */         ExtSysHierElementEVO currentEVO = null;
/*  851 */         ExtSysHierElementEVO newEVO = null;
/*  852 */         while (iter.hasNext())
/*      */         {
/*  854 */           newEVO = (ExtSysHierElementEVO)iter.next();
/*  855 */           Iterator iter2 = currentList.iterator();
/*  856 */           while (iter2.hasNext())
/*      */           {
/*  858 */             currentEVO = (ExtSysHierElementEVO)iter2.next();
/*  859 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  861 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  867 */         Iterator iter2 = currentList.iterator();
/*  868 */         while (iter2.hasNext())
/*      */         {
/*  870 */           currentEVO = (ExtSysHierElementEVO)iter2.next();
/*  871 */           if (currentEVO.insertPending()) {
/*  872 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  876 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  880 */       throw handleSQLException("select EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID,EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID,EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID,EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID,EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID,EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID,EXT_SYS_HIER_ELEMENT.DESCRIPTION,EXT_SYS_HIER_ELEMENT.PARENT_VIS_ID,EXT_SYS_HIER_ELEMENT.IDX,EXT_SYS_HIER_ELEMENT.CREDIT_DEBIT from EXT_SYS_HIER_ELEMENT where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? AND DIMENSION_VIS_ID = ? AND HIERARCHY_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  884 */       closeResultSet(resultSet);
/*  885 */       closeStatement(stmt);
/*  886 */       closeConnection();
/*      */ 
/*  888 */       if (timer != null) {
/*  889 */         timer.logDebug("getAll", " ExternalSystemId=" + selectExternalSystemId + ",CompanyVisId=" + selectCompanyVisId + ",LedgerVisId=" + selectLedgerVisId + ",DimensionVisId=" + selectDimensionVisId + ",HierarchyVisId=" + selectHierarchyVisId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  898 */     return items;
/*      */   }
/*      */ 
/*      */   public ExtSysHierElementEVO getDetails(ExternalSystemCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  915 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  918 */     if (this.mDetails == null) {
/*  919 */       doLoad(((ExtSysHierElementCK)paramCK).getExtSysHierElementPK());
/*      */     }
/*  921 */     else if (!this.mDetails.getPK().equals(((ExtSysHierElementCK)paramCK).getExtSysHierElementPK())) {
/*  922 */       doLoad(((ExtSysHierElementCK)paramCK).getExtSysHierElementPK());
/*      */     }
/*      */ 
/*  925 */     if ((dependants.indexOf("<6>") > -1) && (!this.mDetails.isExtSysHierElemFeedsAllItemsLoaded()))
/*      */     {
/*  930 */       this.mDetails.setExtSysHierElemFeeds(getExtSysHierElemFeedDAO().getAll(this.mDetails.getExternalSystemId(), this.mDetails.getCompanyVisId(), this.mDetails.getLedgerVisId(), this.mDetails.getDimensionVisId(), this.mDetails.getHierarchyVisId(), this.mDetails.getHierElementVisId(), dependants, this.mDetails.getExtSysHierElemFeeds()));
/*      */ 
/*  942 */       this.mDetails.setExtSysHierElemFeedsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  945 */     if ((paramCK instanceof ExtSysHierElemFeedCK))
/*      */     {
/*  947 */       if (this.mDetails.getExtSysHierElemFeeds() == null) {
/*  948 */         this.mDetails.loadExtSysHierElemFeedsItem(getExtSysHierElemFeedDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  951 */         ExtSysHierElemFeedPK pk = ((ExtSysHierElemFeedCK)paramCK).getExtSysHierElemFeedPK();
/*  952 */         ExtSysHierElemFeedEVO evo = this.mDetails.getExtSysHierElemFeedsItem(pk);
/*  953 */         if (evo == null) {
/*  954 */           this.mDetails.loadExtSysHierElemFeedsItem(getExtSysHierElemFeedDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  959 */     ExtSysHierElementEVO details = new ExtSysHierElementEVO();
/*  960 */     details = this.mDetails.deepClone();
/*      */ 
/*  962 */     if (timer != null) {
/*  963 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  965 */     return details;
/*      */   }
/*      */ 
/*      */   public ExtSysHierElementEVO getDetails(ExternalSystemCK paramCK, ExtSysHierElementEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  971 */     ExtSysHierElementEVO savedEVO = this.mDetails;
/*  972 */     this.mDetails = paramEVO;
/*  973 */     ExtSysHierElementEVO newEVO = getDetails(paramCK, dependants);
/*  974 */     this.mDetails = savedEVO;
/*  975 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public ExtSysHierElementEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  981 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  985 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  988 */     ExtSysHierElementEVO details = this.mDetails.deepClone();
/*      */ 
/*  990 */     if (timer != null) {
/*  991 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  993 */     return details;
/*      */   }
/*      */ 
/*      */   protected ExtSysHierElemFeedDAO getExtSysHierElemFeedDAO()
/*      */   {
/* 1002 */     if (this.mExtSysHierElemFeedDAO == null)
/*      */     {
/* 1004 */       if (this.mDataSource != null)
/* 1005 */         this.mExtSysHierElemFeedDAO = new ExtSysHierElemFeedDAO(this.mDataSource);
/*      */       else {
/* 1007 */         this.mExtSysHierElemFeedDAO = new ExtSysHierElemFeedDAO(getConnection());
/*      */       }
/*      */     }
/* 1010 */     return this.mExtSysHierElemFeedDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1015 */     return "ExtSysHierElement";
/*      */   }
/*      */ 
/*      */   public ExtSysHierElementRefImpl getRef(ExtSysHierElementPK paramExtSysHierElementPK)
/*      */   {
/* 1020 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1021 */     PreparedStatement stmt = null;
/* 1022 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1025 */       stmt = getConnection().prepareStatement("select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID,EXT_SYS_HIERARCHY.COMPANY_VIS_ID,EXT_SYS_HIERARCHY.LEDGER_VIS_ID,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID from EXT_SYS_HIER_ELEMENT,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_LEDGER,EXT_SYS_DIMENSION,EXT_SYS_HIERARCHY where 1=1 and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = ? and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = ? and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = ? and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = ? and EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID = ? and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID = EXT_SYS_DIMENSION.HIERARCHY_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_DIMENSION.DIMENSION_VIS_ID = EXT_SYS_LEDGER.DIMENSION_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_LEDGER.LEDGER_VIS_ID = EXT_SYS_COMPANY.LEDGER_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID");
/* 1026 */       int col = 1;
/* 1027 */       stmt.setInt(col++, paramExtSysHierElementPK.getExternalSystemId());
/* 1028 */       stmt.setString(col++, paramExtSysHierElementPK.getCompanyVisId());
/* 1029 */       stmt.setString(col++, paramExtSysHierElementPK.getLedgerVisId());
/* 1030 */       stmt.setString(col++, paramExtSysHierElementPK.getDimensionVisId());
/* 1031 */       stmt.setString(col++, paramExtSysHierElementPK.getHierarchyVisId());
/* 1032 */       stmt.setString(col++, paramExtSysHierElementPK.getHierElementVisId());
/*      */ 
/* 1034 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1036 */       if (!resultSet.next()) {
/* 1037 */         throw new RuntimeException(getEntityName() + " getRef " + paramExtSysHierElementPK + " not found");
/*      */       }
/* 1039 */       col = 2;
/* 1040 */       ExternalSystemPK newExternalSystemPK = new ExternalSystemPK(resultSet.getInt(col++));
/*      */ 
/* 1044 */       ExtSysCompanyPK newExtSysCompanyPK = new ExtSysCompanyPK(resultSet.getInt(col++), resultSet.getString(col++));
/*      */ 
/* 1049 */       ExtSysLedgerPK newExtSysLedgerPK = new ExtSysLedgerPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++));
/*      */ 
/* 1055 */       ExtSysDimensionPK newExtSysDimensionPK = new ExtSysDimensionPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++));
/*      */ 
/* 1062 */       ExtSysHierarchyPK newExtSysHierarchyPK = new ExtSysHierarchyPK(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++));
/*      */ 
/* 1070 */       String textExtSysHierElement = "";
/* 1071 */       ExtSysHierElementCK ckExtSysHierElement = new ExtSysHierElementCK(newExternalSystemPK, newExtSysCompanyPK, newExtSysLedgerPK, newExtSysDimensionPK, newExtSysHierarchyPK, paramExtSysHierElementPK);
/*      */ 
/* 1080 */       ExtSysHierElementRefImpl localExtSysHierElementRefImpl = new ExtSysHierElementRefImpl(ckExtSysHierElement, textExtSysHierElement);
/*      */       return localExtSysHierElementRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1085 */       throw handleSQLException(paramExtSysHierElementPK, "select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID,EXT_SYS_DIMENSION.COMPANY_VIS_ID,EXT_SYS_DIMENSION.LEDGER_VIS_ID,EXT_SYS_DIMENSION.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID,EXT_SYS_HIERARCHY.COMPANY_VIS_ID,EXT_SYS_HIERARCHY.LEDGER_VIS_ID,EXT_SYS_HIERARCHY.DIMENSION_VIS_ID,EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID from EXT_SYS_HIER_ELEMENT,EXTERNAL_SYSTEM,EXT_SYS_COMPANY,EXT_SYS_LEDGER,EXT_SYS_DIMENSION,EXT_SYS_HIERARCHY where 1=1 and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = ? and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = ? and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = ? and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = ? and EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID = ? and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID = EXT_SYS_DIMENSION.HIERARCHY_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_DIMENSION.DIMENSION_VIS_ID = EXT_SYS_LEDGER.DIMENSION_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_LEDGER.LEDGER_VIS_ID = EXT_SYS_COMPANY.LEDGER_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1089 */       closeResultSet(resultSet);
/* 1090 */       closeStatement(stmt);
/* 1091 */       closeConnection();
/*      */ 
/* 1093 */       if (timer != null)
/* 1094 */         timer.logDebug("getRef", paramExtSysHierElementPK); 
/* 1094 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1106 */     if (c == null)
/* 1107 */       return;
/* 1108 */     Iterator iter = c.iterator();
/* 1109 */     while (iter.hasNext())
/*      */     {
/* 1111 */       ExtSysHierElementEVO evo = (ExtSysHierElementEVO)iter.next();
/* 1112 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(ExtSysHierElementEVO evo, String dependants)
/*      */   {
/* 1126 */     if (evo.insertPending()) {
/* 1127 */       return;
/*      */     }
/*      */ 
/* 1131 */     if (dependants.indexOf("<6>") > -1)
/*      */     {
/* 1134 */       if (!evo.isExtSysHierElemFeedsAllItemsLoaded())
/*      */       {
/* 1136 */         evo.setExtSysHierElemFeeds(getExtSysHierElemFeedDAO().getAll(evo.getExternalSystemId(), evo.getCompanyVisId(), evo.getLedgerVisId(), evo.getDimensionVisId(), evo.getHierarchyVisId(), evo.getHierElementVisId(), dependants, evo.getExtSysHierElemFeeds()));
/*      */ 
/* 1148 */         evo.setExtSysHierElemFeedsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int checkConstraintViolations(int externalSystemId, int maxToReport, PrintWriter log)
/*      */   {
/* 1165 */     SqlExecutor sqlExecutor = null;
/* 1166 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1170 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "with elements as ", "(", "  select he.external_system_id,", "         he.ledger_vis_id,", "         he.company_vis_id,", "         he.dimension_vis_id,", "         he.hierarchy_vis_id,", "         nvl(hef.dim_element_vis_id,he.hier_element_vis_id) element_vis_id", "  from ext_sys_hier_element he", "  left join ext_sys_hier_elem_feed hef", "  on", "  ( ", "    he.external_system_id = hef.external_system_id and", "    he.company_vis_id =  hef.company_vis_id and", "    he.ledger_vis_id = hef.ledger_vis_id and", "    he.dimension_vis_id = hef.dimension_vis_id and", "    he.hierarchy_vis_id = hef.hierarchy_vis_id and", "    he.hier_element_vis_id = hef.hier_element_vis_id", "  )", "  where he.external_system_id = <externalSystemId>", ")", "select * from elements", "group by external_system_id,", "         ledger_vis_id,", "         company_vis_id,", "         dimension_vis_id,", "         hierarchy_vis_id,", "         element_vis_id", "having count(*) > 1" });
/*      */ 
/* 1201 */       sqlExecutor = new SqlExecutor("checkConstraintViolations", getDataSource(), sqlBuilder, this._log);
/*      */ 
/* 1203 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/*      */ 
/* 1205 */       rs = sqlExecutor.getResultSet();
/*      */ 
/* 1207 */       int count = 0;
/* 1208 */       while ((rs.next()) && (count < maxToReport))
/*      */       {
/* 1210 */         log.println("Found duplicate hierarchy element details: company:[" + rs.getString("COMPANY_VIS_ID") + "]" + " ledger:[" + rs.getString("LEDGER_VIS_ID") + "]" + " dimension:[" + rs.getString("DIMENSION_VIS_ID") + "]" + " hierarchy:[" + rs.getString("HIERARCHY_VIS_ID") + "]" + " hierarchy element:[" + rs.getString("ELEMENT_VIS_ID") + "]");
/*      */ 
/* 1216 */         count++;
/*      */       }
/*      */ 
/* 1219 */       int i = count;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1223 */       throw handleSQLException("checkConstraintViolations", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1227 */       closeResultSet(rs);
/* 1228 */       sqlExecutor.close(); } //throw localObject;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extsys.ExtSysHierElementDAO
 * JD-Core Version:    0.6.0
 */