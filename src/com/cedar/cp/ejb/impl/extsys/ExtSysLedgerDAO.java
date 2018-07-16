/*      */ package com.cedar.cp.ejb.impl.extsys;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCurrencyCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCurrencyPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysDimensionCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysDimensionPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysLedgerCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysLedgerPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysLedgerRefImpl;
/*      */ import com.cedar.cp.dto.extsys.ExtSysValueTypeCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysValueTypePK;
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
/*      */ public class ExtSysLedgerDAO extends AbstractDAO
/*      */ {
/*   34 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_LEDGER.DESCRIPTION,EXT_SYS_LEDGER.DUMMY";
/*      */   protected static final String SQL_LOAD = " from EXT_SYS_LEDGER where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into EXT_SYS_LEDGER ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DESCRIPTION,DUMMY) values ( ?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update EXT_SYS_LEDGER set DESCRIPTION = ?,DUMMY = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from EXT_SYS_LEDGER where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ";
/*  490 */   private static String[][] SQL_DELETE_CHILDREN = { { "EXT_SYS_DIMENSION", "delete from EXT_SYS_DIMENSION where     EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_DIMENSION.COMPANY_VIS_ID = ? and EXT_SYS_DIMENSION.LEDGER_VIS_ID = ? " }, { "EXT_SYS_VALUE_TYPE", "delete from EXT_SYS_VALUE_TYPE where     EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID = ? and EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID = ? " }, { "EXT_SYS_CURRENCY", "delete from EXT_SYS_CURRENCY where     EXT_SYS_CURRENCY.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_CURRENCY.COMPANY_VIS_ID = ? and EXT_SYS_CURRENCY.LEDGER_VIS_ID = ? " } };
/*      */ 
/*  515 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "EXT_SYS_DIM_ELEMENT", "delete from EXT_SYS_DIM_ELEMENT ExtSysDimElement where exists (select * from EXT_SYS_DIM_ELEMENT,EXT_SYS_DIMENSION,EXT_SYS_LEDGER where     EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and ExtSysDimElement.EXTERNAL_SYSTEM_ID = EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID and ExtSysDimElement.COMPANY_VIS_ID = EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID and ExtSysDimElement.LEDGER_VIS_ID = EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID and ExtSysDimElement.DIMENSION_VIS_ID = EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID " }, { "EXT_SYS_HIERARCHY", "delete from EXT_SYS_HIERARCHY ExtSysHierarchy where exists (select * from EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER where     EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and ExtSysHierarchy.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and ExtSysHierarchy.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and ExtSysHierarchy.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and ExtSysHierarchy.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID " }, { "EXT_SYS_HIER_ELEMENT", "delete from EXT_SYS_HIER_ELEMENT ExtSysHierElement where exists (select * from EXT_SYS_HIER_ELEMENT,EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER where     EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and ExtSysHierElement.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID and ExtSysHierElement.COMPANY_VIS_ID = EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID and ExtSysHierElement.LEDGER_VIS_ID = EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID and ExtSysHierElement.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID and ExtSysHierElement.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID " }, { "EXT_SYS_HIER_ELEM_FEED", "delete from EXT_SYS_HIER_ELEM_FEED ExtSysHierElemFeed where exists (select * from EXT_SYS_HIER_ELEM_FEED,EXT_SYS_HIER_ELEMENT,EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER where     EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID = EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID = EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID and EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID = EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and ExtSysHierElemFeed.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID and ExtSysHierElemFeed.COMPANY_VIS_ID = EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID and ExtSysHierElemFeed.LEDGER_VIS_ID = EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID and ExtSysHierElemFeed.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID and ExtSysHierElemFeed.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID and ExtSysHierElemFeed.HIER_ELEMENT_VIS_ID = EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID " } };
/*      */ 
/*  617 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = ?and EXT_SYS_LEDGER.COMPANY_VIS_ID = ?and EXT_SYS_LEDGER.LEDGER_VIS_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID ,EXT_SYS_LEDGER.COMPANY_VIS_ID ,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID ,EXT_SYS_LEDGER.COMPANY_VIS_ID ,EXT_SYS_LEDGER.LEDGER_VIS_ID";
/*      */   protected static final String SQL_GET_ALL = " from EXT_SYS_LEDGER where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ";
/*      */   protected ExtSysDimensionDAO mExtSysDimensionDAO;
/*      */   protected ExtSysValueTypeDAO mExtSysValueTypeDAO;
/*      */   protected ExtSysCurrencyDAO mExtSysCurrencyDAO;
/*      */   protected ExtSysLedgerEVO mDetails;
/*      */ 
/*      */   public ExtSysLedgerDAO(Connection connection)
/*      */   {
/*   41 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ExtSysLedgerDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ExtSysLedgerDAO(DataSource ds)
/*      */   {
/*   57 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ExtSysLedgerPK getPK()
/*      */   {
/*   65 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ExtSysLedgerEVO details)
/*      */   {
/*   74 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private ExtSysLedgerEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   90 */     int col = 1;
/*   91 */     ExtSysLedgerEVO evo = new ExtSysLedgerEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++).equals("Y"), null, null, null);
/*      */ 
/*  102 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ExtSysLedgerEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  107 */     int col = startCol_;
/*  108 */     stmt_.setInt(col++, evo_.getExternalSystemId());
/*  109 */     stmt_.setString(col++, evo_.getCompanyVisId());
/*  110 */     stmt_.setString(col++, evo_.getLedgerVisId());
/*  111 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ExtSysLedgerEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  116 */     int col = startCol_;
/*  117 */     stmt_.setString(col++, evo_.getDescription());
/*  118 */     if (evo_.getDummy())
/*  119 */       stmt_.setString(col++, "Y");
/*      */     else
/*  121 */       stmt_.setString(col++, " ");
/*  122 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ExtSysLedgerPK pk)
/*      */     throws ValidationException
/*      */   {
/*  140 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  142 */     PreparedStatement stmt = null;
/*  143 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  147 */       stmt = getConnection().prepareStatement("select EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_LEDGER.DESCRIPTION,EXT_SYS_LEDGER.DUMMY from EXT_SYS_LEDGER where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ");
/*      */ 
/*  150 */       int col = 1;
/*  151 */       stmt.setInt(col++, pk.getExternalSystemId());
/*  152 */       stmt.setString(col++, pk.getCompanyVisId());
/*  153 */       stmt.setString(col++, pk.getLedgerVisId());
/*      */ 
/*  155 */       resultSet = stmt.executeQuery();
/*      */ 
/*  157 */       if (!resultSet.next()) {
/*  158 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  161 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  162 */       if (this.mDetails.isModified())
/*  163 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  167 */       throw handleSQLException(pk, "select EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_LEDGER.DESCRIPTION,EXT_SYS_LEDGER.DUMMY from EXT_SYS_LEDGER where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  171 */       closeResultSet(resultSet);
/*  172 */       closeStatement(stmt);
/*  173 */       closeConnection();
/*      */ 
/*  175 */       if (timer != null)
/*  176 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  203 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  204 */     this.mDetails.postCreateInit();
/*      */ 
/*  206 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  210 */       stmt = getConnection().prepareStatement("insert into EXT_SYS_LEDGER ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DESCRIPTION,DUMMY) values ( ?,?,?,?,?)");
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
/*  228 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXT_SYS_LEDGER ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,LEDGER_VIS_ID,DESCRIPTION,DUMMY) values ( ?,?,?,?,?)", sqle);
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
/*  242 */       getExtSysDimensionDAO().update(this.mDetails.getExtSysDimensionsMap());
/*      */ 
/*  244 */       getExtSysValueTypeDAO().update(this.mDetails.getExtSysValueTypesMap());
/*      */ 
/*  246 */       getExtSysCurrencyDAO().update(this.mDetails.getExtSysCurrenciesMap());
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
/*  275 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  279 */     PreparedStatement stmt = null;
/*      */ 
/*  281 */     boolean mainChanged = this.mDetails.isModified();
/*  282 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  286 */       if (getExtSysDimensionDAO().update(this.mDetails.getExtSysDimensionsMap())) {
/*  287 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  290 */       if (getExtSysValueTypeDAO().update(this.mDetails.getExtSysValueTypesMap())) {
/*  291 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  294 */       if (getExtSysCurrencyDAO().update(this.mDetails.getExtSysCurrenciesMap())) {
/*  295 */         dependantChanged = true;
/*      */       }
/*  297 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  299 */         stmt = getConnection().prepareStatement("update EXT_SYS_LEDGER set DESCRIPTION = ?,DUMMY = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ");
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
/*  323 */       throw handleSQLException(getPK(), "update EXT_SYS_LEDGER set DESCRIPTION = ?,DUMMY = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ", sqle);
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
/*  353 */     if (items == null) {
/*  354 */       return false;
/*      */     }
/*  356 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  357 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  359 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  363 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  364 */       while (iter3.hasNext())
/*      */       {
/*  366 */         this.mDetails = ((ExtSysLedgerEVO)iter3.next());
/*  367 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  369 */         somethingChanged = true;
/*      */ 
/*  372 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  376 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  377 */       while (iter2.hasNext())
/*      */       {
/*  379 */         this.mDetails = ((ExtSysLedgerEVO)iter2.next());
/*      */ 
/*  382 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  384 */         somethingChanged = true;
/*      */ 
/*  387 */         if (deleteStmt == null) {
/*  388 */           deleteStmt = getConnection().prepareStatement("delete from EXT_SYS_LEDGER where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ");
/*      */         }
/*      */ 
/*  391 */         int col = 1;
/*  392 */         deleteStmt.setInt(col++, this.mDetails.getExternalSystemId());
/*  393 */         deleteStmt.setString(col++, this.mDetails.getCompanyVisId());
/*  394 */         deleteStmt.setString(col++, this.mDetails.getLedgerVisId());
/*      */ 
/*  396 */         if (this._log.isDebugEnabled()) {
/*  397 */           this._log.debug("update", "ExtSysLedger deleting ExternalSystemId=" + this.mDetails.getExternalSystemId() + ",CompanyVisId=" + this.mDetails.getCompanyVisId() + ",LedgerVisId=" + this.mDetails.getLedgerVisId());
/*      */         }
/*      */ 
/*  404 */         deleteStmt.addBatch();
/*      */ 
/*  407 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  412 */       if (deleteStmt != null)
/*      */       {
/*  414 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  416 */         deleteStmt.executeBatch();
/*      */ 
/*  418 */         if (timer2 != null) {
/*  419 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  423 */       Iterator iter1 = items.values().iterator();
/*  424 */       while (iter1.hasNext())
/*      */       {
/*  426 */         this.mDetails = ((ExtSysLedgerEVO)iter1.next());
/*      */ 
/*  428 */         if (this.mDetails.insertPending())
/*      */         {
/*  430 */           somethingChanged = true;
/*  431 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  434 */         if (this.mDetails.isModified())
/*      */         {
/*  436 */           somethingChanged = true;
/*  437 */           doStore(); continue;
/*      */         }
/*      */ 
/*  441 */         if (this.mDetails.deletePending())
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  447 */         if (getExtSysDimensionDAO().update(this.mDetails.getExtSysDimensionsMap())) {
/*  448 */           somethingChanged = true;
/*      */         }
/*      */ 
/*  451 */         if (getExtSysValueTypeDAO().update(this.mDetails.getExtSysValueTypesMap())) {
/*  452 */           somethingChanged = true;
/*      */         }
/*      */ 
/*  455 */         if (getExtSysCurrencyDAO().update(this.mDetails.getExtSysCurrenciesMap())) {
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
/*  472 */       throw handleSQLException("delete from EXT_SYS_LEDGER where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND LEDGER_VIS_ID = ? ", sqle);
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
/*      */   private void deleteDependants(ExtSysLedgerPK pk)
/*      */   {
/*  628 */     Set emptyStrings = Collections.emptySet();
/*  629 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ExtSysLedgerPK pk, Set<String> exclusionTables)
/*      */   {
/*  635 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  637 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  639 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  641 */       PreparedStatement stmt = null;
/*      */ 
/*  643 */       int resultCount = 0;
/*  644 */       String s = null;
/*      */       try
/*      */       {
/*  647 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  649 */         if (this._log.isDebugEnabled()) {
/*  650 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  652 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  655 */         int col = 1;
/*  656 */         stmt.setInt(col++, pk.getExternalSystemId());
/*  657 */         stmt.setString(col++, pk.getCompanyVisId());
/*  658 */         stmt.setString(col++, pk.getLedgerVisId());
/*      */ 
/*  661 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  665 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  669 */         closeStatement(stmt);
/*  670 */         closeConnection();
/*      */ 
/*  672 */         if (timer != null) {
/*  673 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  677 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  679 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  681 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  683 */       PreparedStatement stmt = null;
/*      */ 
/*  685 */       int resultCount = 0;
/*  686 */       String s = null;
/*      */       try
/*      */       {
/*  689 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  691 */         if (this._log.isDebugEnabled()) {
/*  692 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  694 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  697 */         int col = 1;
/*  698 */         stmt.setInt(col++, pk.getExternalSystemId());
/*  699 */         stmt.setString(col++, pk.getCompanyVisId());
/*  700 */         stmt.setString(col++, pk.getLedgerVisId());
/*      */ 
/*  703 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  707 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  711 */         closeStatement(stmt);
/*  712 */         closeConnection();
/*      */ 
/*  714 */         if (timer != null)
/*  715 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ExternalSystemPK entityPK, Collection owners, String dependants)
/*      */   {
/*  743 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  745 */     PreparedStatement stmt = null;
/*  746 */     ResultSet resultSet = null;
/*      */ 
/*  748 */     int itemCount = 0;
/*      */ 
/*  750 */     ExtSysCompanyEVO owningEVO = null;
/*  751 */     Iterator ownersIter = owners.iterator();
/*  752 */     while (ownersIter.hasNext())
/*      */     {
/*  754 */       owningEVO = (ExtSysCompanyEVO)ownersIter.next();
/*  755 */       owningEVO.setExtSysLedgerAllItemsLoaded(true);
/*      */     }
/*  757 */     ownersIter = owners.iterator();
/*  758 */     owningEVO = (ExtSysCompanyEVO)ownersIter.next();
/*  759 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  763 */       stmt = getConnection().prepareStatement("select EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_LEDGER.DESCRIPTION,EXT_SYS_LEDGER.DUMMY from EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID ,EXT_SYS_LEDGER.COMPANY_VIS_ID ,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID ,EXT_SYS_LEDGER.COMPANY_VIS_ID ,EXT_SYS_LEDGER.LEDGER_VIS_ID");
/*      */ 
/*  765 */       int col = 1;
/*  766 */       stmt.setInt(col++, entityPK.getExternalSystemId());
/*      */ 
/*  768 */       resultSet = stmt.executeQuery();
/*      */ 
/*  771 */       while (resultSet.next())
/*      */       {
/*  773 */         itemCount++;
/*  774 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  779 */         while ((this.mDetails.getExternalSystemId() != owningEVO.getExternalSystemId()) || (!this.mDetails.getCompanyVisId().equals(owningEVO.getCompanyVisId())))
/*      */         {
/*  784 */           if (!ownersIter.hasNext())
/*      */           {
/*  786 */             this._log.debug("bulkGetAll", "can't find owning [ExternalSystemId=" + this.mDetails.getExternalSystemId() + "CompanyVisId=" + this.mDetails.getCompanyVisId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  791 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  792 */             ownersIter = owners.iterator();
/*  793 */             while (ownersIter.hasNext())
/*      */             {
/*  795 */               owningEVO = (ExtSysCompanyEVO)ownersIter.next();
/*  796 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  798 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  800 */           owningEVO = (ExtSysCompanyEVO)ownersIter.next();
/*      */         }
/*  802 */         if (owningEVO.getExtSysLedger() == null)
/*      */         {
/*  804 */           theseItems = new ArrayList();
/*  805 */           owningEVO.setExtSysLedger(theseItems);
/*  806 */           owningEVO.setExtSysLedgerAllItemsLoaded(true);
/*      */         }
/*  808 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  811 */       if (timer != null) {
/*  812 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  815 */       if ((itemCount > 0) && (dependants.indexOf("<2>") > -1))
/*      */       {
/*  817 */         getExtSysDimensionDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*  819 */       if ((itemCount > 0) && (dependants.indexOf("<7>") > -1))
/*      */       {
/*  821 */         getExtSysValueTypeDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*  823 */       if ((itemCount > 0) && (dependants.indexOf("<8>") > -1))
/*      */       {
/*  825 */         getExtSysCurrencyDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  829 */       throw handleSQLException("select EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_LEDGER.DESCRIPTION,EXT_SYS_LEDGER.DUMMY from EXT_SYS_LEDGER,EXT_SYS_COMPANY where 1=1 and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID ,EXT_SYS_LEDGER.COMPANY_VIS_ID ,EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID ,EXT_SYS_LEDGER.COMPANY_VIS_ID ,EXT_SYS_LEDGER.LEDGER_VIS_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  833 */       closeResultSet(resultSet);
/*  834 */       closeStatement(stmt);
/*  835 */       closeConnection();
/*      */ 
/*  837 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectExternalSystemId, String selectCompanyVisId, String dependants, Collection currentList)
/*      */   {
/*  865 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  866 */     PreparedStatement stmt = null;
/*  867 */     ResultSet resultSet = null;
/*      */ 
/*  869 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  873 */       stmt = getConnection().prepareStatement("select EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_LEDGER.DESCRIPTION,EXT_SYS_LEDGER.DUMMY from EXT_SYS_LEDGER where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ");
/*      */ 
/*  875 */       int col = 1;
/*  876 */       stmt.setInt(col++, selectExternalSystemId);
/*  877 */       stmt.setString(col++, selectCompanyVisId);
/*      */ 
/*  879 */       resultSet = stmt.executeQuery();
/*      */ 
/*  881 */       while (resultSet.next())
/*      */       {
/*  883 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  886 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  889 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  892 */       if (currentList != null)
/*      */       {
/*  895 */         ListIterator iter = items.listIterator();
/*  896 */         ExtSysLedgerEVO currentEVO = null;
/*  897 */         ExtSysLedgerEVO newEVO = null;
/*  898 */         while (iter.hasNext())
/*      */         {
/*  900 */           newEVO = (ExtSysLedgerEVO)iter.next();
/*  901 */           Iterator iter2 = currentList.iterator();
/*  902 */           while (iter2.hasNext())
/*      */           {
/*  904 */             currentEVO = (ExtSysLedgerEVO)iter2.next();
/*  905 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  907 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  913 */         Iterator iter2 = currentList.iterator();
/*  914 */         while (iter2.hasNext())
/*      */         {
/*  916 */           currentEVO = (ExtSysLedgerEVO)iter2.next();
/*  917 */           if (currentEVO.insertPending()) {
/*  918 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  922 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  926 */       throw handleSQLException("select EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID,EXT_SYS_LEDGER.COMPANY_VIS_ID,EXT_SYS_LEDGER.LEDGER_VIS_ID,EXT_SYS_LEDGER.DESCRIPTION,EXT_SYS_LEDGER.DUMMY from EXT_SYS_LEDGER where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  930 */       closeResultSet(resultSet);
/*  931 */       closeStatement(stmt);
/*  932 */       closeConnection();
/*      */ 
/*  934 */       if (timer != null) {
/*  935 */         timer.logDebug("getAll", " ExternalSystemId=" + selectExternalSystemId + ",CompanyVisId=" + selectCompanyVisId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  941 */     return items;
/*      */   }
/*      */ 
/*      */   public ExtSysLedgerEVO getDetails(ExternalSystemCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  970 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  973 */     if (this.mDetails == null) {
/*  974 */       doLoad(((ExtSysLedgerCK)paramCK).getExtSysLedgerPK());
/*      */     }
/*  976 */     else if (!this.mDetails.getPK().equals(((ExtSysLedgerCK)paramCK).getExtSysLedgerPK())) {
/*  977 */       doLoad(((ExtSysLedgerCK)paramCK).getExtSysLedgerPK());
/*      */     }
/*      */ 
/*  980 */     if ((dependants.indexOf("<2>") > -1) && (!this.mDetails.isExtSysDimensionsAllItemsLoaded()))
/*      */     {
/*  985 */       this.mDetails.setExtSysDimensions(getExtSysDimensionDAO().getAll(this.mDetails.getExternalSystemId(), this.mDetails.getCompanyVisId(), this.mDetails.getLedgerVisId(), dependants, this.mDetails.getExtSysDimensions()));
/*      */ 
/*  994 */       this.mDetails.setExtSysDimensionsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  998 */     if ((dependants.indexOf("<7>") > -1) && (!this.mDetails.isExtSysValueTypesAllItemsLoaded()))
/*      */     {
/* 1003 */       this.mDetails.setExtSysValueTypes(getExtSysValueTypeDAO().getAll(this.mDetails.getExternalSystemId(), this.mDetails.getCompanyVisId(), this.mDetails.getLedgerVisId(), dependants, this.mDetails.getExtSysValueTypes()));
/*      */ 
/* 1012 */       this.mDetails.setExtSysValueTypesAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1016 */     if ((dependants.indexOf("<8>") > -1) && (!this.mDetails.isExtSysCurrenciesAllItemsLoaded()))
/*      */     {
/* 1021 */       this.mDetails.setExtSysCurrencies(getExtSysCurrencyDAO().getAll(this.mDetails.getExternalSystemId(), this.mDetails.getCompanyVisId(), this.mDetails.getLedgerVisId(), dependants, this.mDetails.getExtSysCurrencies()));
/*      */ 
/* 1030 */       this.mDetails.setExtSysCurrenciesAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1033 */     if ((paramCK instanceof ExtSysDimensionCK))
/*      */     {
/* 1035 */       if (this.mDetails.getExtSysDimensions() == null) {
/* 1036 */         this.mDetails.loadExtSysDimensionsItem(getExtSysDimensionDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1039 */         ExtSysDimensionPK pk = ((ExtSysDimensionCK)paramCK).getExtSysDimensionPK();
/* 1040 */         ExtSysDimensionEVO evo = this.mDetails.getExtSysDimensionsItem(pk);
/* 1041 */         if (evo == null)
/* 1042 */           this.mDetails.loadExtSysDimensionsItem(getExtSysDimensionDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1044 */           getExtSysDimensionDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/* 1048 */     else if ((paramCK instanceof ExtSysValueTypeCK))
/*      */     {
/* 1050 */       if (this.mDetails.getExtSysValueTypes() == null) {
/* 1051 */         this.mDetails.loadExtSysValueTypesItem(getExtSysValueTypeDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1054 */         ExtSysValueTypePK pk = ((ExtSysValueTypeCK)paramCK).getExtSysValueTypePK();
/* 1055 */         ExtSysValueTypeEVO evo = this.mDetails.getExtSysValueTypesItem(pk);
/* 1056 */         if (evo == null) {
/* 1057 */           this.mDetails.loadExtSysValueTypesItem(getExtSysValueTypeDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/* 1061 */     else if ((paramCK instanceof ExtSysCurrencyCK))
/*      */     {
/* 1063 */       if (this.mDetails.getExtSysCurrencies() == null) {
/* 1064 */         this.mDetails.loadExtSysCurrenciesItem(getExtSysCurrencyDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1067 */         ExtSysCurrencyPK pk = ((ExtSysCurrencyCK)paramCK).getExtSysCurrencyPK();
/* 1068 */         ExtSysCurrencyEVO evo = this.mDetails.getExtSysCurrenciesItem(pk);
/* 1069 */         if (evo == null) {
/* 1070 */           this.mDetails.loadExtSysCurrenciesItem(getExtSysCurrencyDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1075 */     ExtSysLedgerEVO details = new ExtSysLedgerEVO();
/* 1076 */     details = this.mDetails.deepClone();
/*      */ 
/* 1078 */     if (timer != null) {
/* 1079 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1081 */     return details;
/*      */   }
/*      */ 
/*      */   public ExtSysLedgerEVO getDetails(ExternalSystemCK paramCK, ExtSysLedgerEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1087 */     ExtSysLedgerEVO savedEVO = this.mDetails;
/* 1088 */     this.mDetails = paramEVO;
/* 1089 */     ExtSysLedgerEVO newEVO = getDetails(paramCK, dependants);
/* 1090 */     this.mDetails = savedEVO;
/* 1091 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public ExtSysLedgerEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1097 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1101 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1104 */     ExtSysLedgerEVO details = this.mDetails.deepClone();
/*      */ 
/* 1106 */     if (timer != null) {
/* 1107 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1109 */     return details;
/*      */   }
/*      */ 
/*      */   protected ExtSysDimensionDAO getExtSysDimensionDAO()
/*      */   {
/* 1118 */     if (this.mExtSysDimensionDAO == null)
/*      */     {
/* 1120 */       if (this.mDataSource != null)
/* 1121 */         this.mExtSysDimensionDAO = new ExtSysDimensionDAO(this.mDataSource);
/*      */       else {
/* 1123 */         this.mExtSysDimensionDAO = new ExtSysDimensionDAO(getConnection());
/*      */       }
/*      */     }
/* 1126 */     return this.mExtSysDimensionDAO;
/*      */   }
/*      */ 
/*      */   protected ExtSysValueTypeDAO getExtSysValueTypeDAO()
/*      */   {
/* 1135 */     if (this.mExtSysValueTypeDAO == null)
/*      */     {
/* 1137 */       if (this.mDataSource != null)
/* 1138 */         this.mExtSysValueTypeDAO = new ExtSysValueTypeDAO(this.mDataSource);
/*      */       else {
/* 1140 */         this.mExtSysValueTypeDAO = new ExtSysValueTypeDAO(getConnection());
/*      */       }
/*      */     }
/* 1143 */     return this.mExtSysValueTypeDAO;
/*      */   }
/*      */ 
/*      */   protected ExtSysCurrencyDAO getExtSysCurrencyDAO()
/*      */   {
/* 1152 */     if (this.mExtSysCurrencyDAO == null)
/*      */     {
/* 1154 */       if (this.mDataSource != null)
/* 1155 */         this.mExtSysCurrencyDAO = new ExtSysCurrencyDAO(this.mDataSource);
/*      */       else {
/* 1157 */         this.mExtSysCurrencyDAO = new ExtSysCurrencyDAO(getConnection());
/*      */       }
/*      */     }
/* 1160 */     return this.mExtSysCurrencyDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1165 */     return "ExtSysLedger";
/*      */   }
/*      */ 
/*      */   public ExtSysLedgerRefImpl getRef(ExtSysLedgerPK paramExtSysLedgerPK)
/*      */   {
/* 1170 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1171 */     PreparedStatement stmt = null;
/* 1172 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1175 */       stmt = getConnection().prepareStatement("select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID from EXT_SYS_LEDGER,EXTERNAL_SYSTEM,EXT_SYS_COMPANY where 1=1 and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_LEDGER.COMPANY_VIS_ID = ? and EXT_SYS_LEDGER.LEDGER_VIS_ID = ? and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID");
/* 1176 */       int col = 1;
/* 1177 */       stmt.setInt(col++, paramExtSysLedgerPK.getExternalSystemId());
/* 1178 */       stmt.setString(col++, paramExtSysLedgerPK.getCompanyVisId());
/* 1179 */       stmt.setString(col++, paramExtSysLedgerPK.getLedgerVisId());
/*      */ 
/* 1181 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1183 */       if (!resultSet.next()) {
/* 1184 */         throw new RuntimeException(getEntityName() + " getRef " + paramExtSysLedgerPK + " not found");
/*      */       }
/* 1186 */       col = 2;
/* 1187 */       ExternalSystemPK newExternalSystemPK = new ExternalSystemPK(resultSet.getInt(col++));
/*      */ 
/* 1191 */       ExtSysCompanyPK newExtSysCompanyPK = new ExtSysCompanyPK(resultSet.getInt(col++), resultSet.getString(col++));
/*      */ 
/* 1196 */       String textExtSysLedger = "";
/* 1197 */       ExtSysLedgerCK ckExtSysLedger = new ExtSysLedgerCK(newExternalSystemPK, newExtSysCompanyPK, paramExtSysLedgerPK);
/*      */ 
/* 1203 */       ExtSysLedgerRefImpl localExtSysLedgerRefImpl = new ExtSysLedgerRefImpl(ckExtSysLedger, textExtSysLedger);
/*      */       return localExtSysLedgerRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1208 */       throw handleSQLException(paramExtSysLedgerPK, "select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID from EXT_SYS_LEDGER,EXTERNAL_SYSTEM,EXT_SYS_COMPANY where 1=1 and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_LEDGER.COMPANY_VIS_ID = ? and EXT_SYS_LEDGER.LEDGER_VIS_ID = ? and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1212 */       closeResultSet(resultSet);
/* 1213 */       closeStatement(stmt);
/* 1214 */       closeConnection();
/*      */ 
/* 1216 */       if (timer != null)
/* 1217 */         timer.logDebug("getRef", paramExtSysLedgerPK); 
/* 1217 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1229 */     if (c == null)
/* 1230 */       return;
/* 1231 */     Iterator iter = c.iterator();
/* 1232 */     while (iter.hasNext())
/*      */     {
/* 1234 */       ExtSysLedgerEVO evo = (ExtSysLedgerEVO)iter.next();
/* 1235 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(ExtSysLedgerEVO evo, String dependants)
/*      */   {
/* 1249 */     if (evo.insertPending()) {
/* 1250 */       return;
/*      */     }
/*      */ 
/* 1254 */     if ((dependants.indexOf("<2>") > -1) || (dependants.indexOf("<3>") > -1) || (dependants.indexOf("<4>") > -1) || (dependants.indexOf("<5>") > -1) || (dependants.indexOf("<6>") > -1))
/*      */     {
/* 1261 */       if (!evo.isExtSysDimensionsAllItemsLoaded())
/*      */       {
/* 1263 */         evo.setExtSysDimensions(getExtSysDimensionDAO().getAll(evo.getExternalSystemId(), evo.getCompanyVisId(), evo.getLedgerVisId(), dependants, evo.getExtSysDimensions()));
/*      */ 
/* 1272 */         evo.setExtSysDimensionsAllItemsLoaded(true);
/*      */       }
/* 1274 */       getExtSysDimensionDAO().getDependants(evo.getExtSysDimensions(), dependants);
/*      */     }
/*      */ 
/* 1278 */     if (dependants.indexOf("<7>") > -1)
/*      */     {
/* 1281 */       if (!evo.isExtSysValueTypesAllItemsLoaded())
/*      */       {
/* 1283 */         evo.setExtSysValueTypes(getExtSysValueTypeDAO().getAll(evo.getExternalSystemId(), evo.getCompanyVisId(), evo.getLedgerVisId(), dependants, evo.getExtSysValueTypes()));
/*      */ 
/* 1292 */         evo.setExtSysValueTypesAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1297 */     if (dependants.indexOf("<8>") > -1)
/*      */     {
/* 1300 */       if (!evo.isExtSysCurrenciesAllItemsLoaded())
/*      */       {
/* 1302 */         evo.setExtSysCurrencies(getExtSysCurrencyDAO().getAll(evo.getExternalSystemId(), evo.getCompanyVisId(), evo.getLedgerVisId(), dependants, evo.getExtSysCurrencies()));
/*      */ 
/* 1311 */         evo.setExtSysCurrenciesAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void insert(ExtSysLedgerEVO evo)
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/* 1329 */     setDetails(evo);
/* 1330 */     doCreate();
/*      */   }
/*      */ 
/*      */   public int checkConstraintViolations(int externalSystemId, int maxToReport, PrintWriter log)
/*      */   {
/* 1343 */     SqlExecutor sqlExecutor = null;
/* 1344 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1348 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "select external_system_id, company_vis_id, ledger_vis_id", "from ext_sys_ledger", "where external_system_id = <externalSystemId>", "group by external_system_id, company_vis_id, ledger_vis_id", "having count(*) > 1" });
/*      */ 
/* 1354 */       sqlExecutor = new SqlExecutor("checkConstraintViolations", getDataSource(), sqlBuilder, this._log);
/*      */ 
/* 1356 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/*      */ 
/* 1358 */       rs = sqlExecutor.getResultSet();
/*      */ 
/* 1360 */       int count = 0;
/* 1361 */       while ((rs.next()) && (count < maxToReport))
/*      */       {
/* 1363 */         log.print("Found duplicate ledger details: company:[" + rs.getString("COMPANY_VIS_ID") + "] " + " ledger:[" + rs.getString("LEDGER_VIS_ID") + "]");
/*      */ 
/* 1366 */         count++;
/*      */       }
/*      */ 
/* 1369 */       int i = count;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1373 */       throw handleSQLException("checkConstraintViolations", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1377 */       closeResultSet(rs);
/* 1378 */       sqlExecutor.close(); } //throw localObject;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extsys.ExtSysLedgerDAO
 * JD-Core Version:    0.6.0
 */