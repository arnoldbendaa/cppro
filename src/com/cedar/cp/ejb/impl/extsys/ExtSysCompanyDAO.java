/*      */ package com.cedar.cp.ejb.impl.extsys;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.extsys.AllExternalSystemCompainesELO;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCalendarYearCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCalendarYearPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCompanyCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCompanyRefImpl;
/*      */ import com.cedar.cp.dto.extsys.ExtSysLedgerCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysLedgerPK;
/*      */ import com.cedar.cp.dto.extsys.ExternalSystemCK;
/*      */ import com.cedar.cp.dto.extsys.ExternalSystemPK;
/*      */ import com.cedar.cp.dto.extsys.ExternalSystemRefImpl;
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
/*      */ public class ExtSysCompanyDAO extends AbstractDAO
/*      */ {
/*   34 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_COMPANY.DESCRIPTION,EXT_SYS_COMPANY.DUMMY,EXT_SYS_COMPANY.IMPORT_COLUMN_CALENDAR_INDEX";
/*      */   protected static final String SQL_LOAD = " from EXT_SYS_COMPANY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into EXT_SYS_COMPANY ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,DESCRIPTION,DUMMY,IMPORT_COLUMN_CALENDAR_INDEX) values ( ?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update EXT_SYS_COMPANY set DESCRIPTION = ?,DUMMY = ?,IMPORT_COLUMN_CALENDAR_INDEX = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ";
/*  331 */   protected static String SQL_ALL_EXTERNAL_SYSTEM_COMPAINES = "select 0       ,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID      ,EXTERNAL_SYSTEM.VIS_ID      ,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID      ,EXT_SYS_COMPANY.COMPANY_VIS_ID      ,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID      ,EXT_SYS_COMPANY.DESCRIPTION      ,EXT_SYS_COMPANY.DUMMY      ,EXT_SYS_COMPANY.IMPORT_COLUMN_CALENDAR_INDEX from EXT_SYS_COMPANY    ,EXTERNAL_SYSTEM where 1=1   and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from EXT_SYS_COMPANY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ";
/*  592 */   private static String[][] SQL_DELETE_CHILDREN = { { "EXT_SYS_LEDGER", "delete from EXT_SYS_LEDGER where     EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_LEDGER.COMPANY_VIS_ID = ? " }, { "EXT_SYS_CALENDAR_YEAR", "delete from EXT_SYS_CALENDAR_YEAR where     EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = ? " } };
/*      */ 
/*  608 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "EXT_SYS_DIMENSION", "delete from EXT_SYS_DIMENSION ExtSysDimension where exists (select * from EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where     EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and ExtSysDimension.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and ExtSysDimension.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and ExtSysDimension.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID " }, { "EXT_SYS_DIM_ELEMENT", "delete from EXT_SYS_DIM_ELEMENT ExtSysDimElement where exists (select * from EXT_SYS_DIM_ELEMENT,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where     EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and ExtSysDimElement.EXTERNAL_SYSTEM_ID = EXT_SYS_DIM_ELEMENT.EXTERNAL_SYSTEM_ID and ExtSysDimElement.COMPANY_VIS_ID = EXT_SYS_DIM_ELEMENT.COMPANY_VIS_ID and ExtSysDimElement.LEDGER_VIS_ID = EXT_SYS_DIM_ELEMENT.LEDGER_VIS_ID and ExtSysDimElement.DIMENSION_VIS_ID = EXT_SYS_DIM_ELEMENT.DIMENSION_VIS_ID " }, { "EXT_SYS_HIERARCHY", "delete from EXT_SYS_HIERARCHY ExtSysHierarchy where exists (select * from EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where     EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and ExtSysHierarchy.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and ExtSysHierarchy.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and ExtSysHierarchy.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and ExtSysHierarchy.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID " }, { "EXT_SYS_HIER_ELEMENT", "delete from EXT_SYS_HIER_ELEMENT ExtSysHierElement where exists (select * from EXT_SYS_HIER_ELEMENT,EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where     EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and ExtSysHierElement.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID and ExtSysHierElement.COMPANY_VIS_ID = EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID and ExtSysHierElement.LEDGER_VIS_ID = EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID and ExtSysHierElement.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID and ExtSysHierElement.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID " }, { "EXT_SYS_HIER_ELEM_FEED", "delete from EXT_SYS_HIER_ELEM_FEED ExtSysHierElemFeed where exists (select * from EXT_SYS_HIER_ELEM_FEED,EXT_SYS_HIER_ELEMENT,EXT_SYS_HIERARCHY,EXT_SYS_DIMENSION,EXT_SYS_LEDGER,EXT_SYS_COMPANY where     EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID = EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID = EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID and EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID and EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID = EXT_SYS_HIER_ELEMENT.HIER_ELEMENT_VIS_ID and EXT_SYS_HIER_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID and EXT_SYS_HIER_ELEMENT.COMPANY_VIS_ID = EXT_SYS_HIERARCHY.COMPANY_VIS_ID and EXT_SYS_HIER_ELEMENT.LEDGER_VIS_ID = EXT_SYS_HIERARCHY.LEDGER_VIS_ID and EXT_SYS_HIER_ELEMENT.DIMENSION_VIS_ID = EXT_SYS_HIERARCHY.DIMENSION_VIS_ID and EXT_SYS_HIER_ELEMENT.HIERARCHY_VIS_ID = EXT_SYS_HIERARCHY.HIERARCHY_VIS_ID and EXT_SYS_HIERARCHY.EXTERNAL_SYSTEM_ID = EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID and EXT_SYS_HIERARCHY.COMPANY_VIS_ID = EXT_SYS_DIMENSION.COMPANY_VIS_ID and EXT_SYS_HIERARCHY.LEDGER_VIS_ID = EXT_SYS_DIMENSION.LEDGER_VIS_ID and EXT_SYS_HIERARCHY.DIMENSION_VIS_ID = EXT_SYS_DIMENSION.DIMENSION_VIS_ID and EXT_SYS_DIMENSION.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_DIMENSION.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_DIMENSION.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and ExtSysHierElemFeed.EXTERNAL_SYSTEM_ID = EXT_SYS_HIER_ELEM_FEED.EXTERNAL_SYSTEM_ID and ExtSysHierElemFeed.COMPANY_VIS_ID = EXT_SYS_HIER_ELEM_FEED.COMPANY_VIS_ID and ExtSysHierElemFeed.LEDGER_VIS_ID = EXT_SYS_HIER_ELEM_FEED.LEDGER_VIS_ID and ExtSysHierElemFeed.DIMENSION_VIS_ID = EXT_SYS_HIER_ELEM_FEED.DIMENSION_VIS_ID and ExtSysHierElemFeed.HIERARCHY_VIS_ID = EXT_SYS_HIER_ELEM_FEED.HIERARCHY_VIS_ID and ExtSysHierElemFeed.HIER_ELEMENT_VIS_ID = EXT_SYS_HIER_ELEM_FEED.HIER_ELEMENT_VIS_ID " }, { "EXT_SYS_VALUE_TYPE", "delete from EXT_SYS_VALUE_TYPE ExtSysValueType where exists (select * from EXT_SYS_VALUE_TYPE,EXT_SYS_LEDGER,EXT_SYS_COMPANY where     EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and ExtSysValueType.EXTERNAL_SYSTEM_ID = EXT_SYS_VALUE_TYPE.EXTERNAL_SYSTEM_ID and ExtSysValueType.COMPANY_VIS_ID = EXT_SYS_VALUE_TYPE.COMPANY_VIS_ID and ExtSysValueType.LEDGER_VIS_ID = EXT_SYS_VALUE_TYPE.LEDGER_VIS_ID " }, { "EXT_SYS_CURRENCY", "delete from EXT_SYS_CURRENCY ExtSysCurrency where exists (select * from EXT_SYS_CURRENCY,EXT_SYS_LEDGER,EXT_SYS_COMPANY where     EXT_SYS_CURRENCY.EXTERNAL_SYSTEM_ID = EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID and EXT_SYS_CURRENCY.COMPANY_VIS_ID = EXT_SYS_LEDGER.COMPANY_VIS_ID and EXT_SYS_CURRENCY.LEDGER_VIS_ID = EXT_SYS_LEDGER.LEDGER_VIS_ID and EXT_SYS_LEDGER.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_LEDGER.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and ExtSysCurrency.EXTERNAL_SYSTEM_ID = EXT_SYS_CURRENCY.EXTERNAL_SYSTEM_ID and ExtSysCurrency.COMPANY_VIS_ID = EXT_SYS_CURRENCY.COMPANY_VIS_ID and ExtSysCurrency.LEDGER_VIS_ID = EXT_SYS_CURRENCY.LEDGER_VIS_ID " }, { "EXT_SYS_CAL_ELEMENT", "delete from EXT_SYS_CAL_ELEMENT ExtSysCalElement where exists (select * from EXT_SYS_CAL_ELEMENT,EXT_SYS_CALENDAR_YEAR,EXT_SYS_COMPANY where     EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID = EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID and EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID = EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID and EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID = EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and ExtSysCalElement.EXTERNAL_SYSTEM_ID = EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID and ExtSysCalElement.COMPANY_VIS_ID = EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID and ExtSysCalElement.CALENDAR_YEAR_VIS_ID = EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID " } };
/*      */ 
/*  786 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ?and EXT_SYS_COMPANY.COMPANY_VIS_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from EXT_SYS_COMPANY where 1=1 and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID ,EXT_SYS_COMPANY.COMPANY_VIS_ID";
/*      */   protected static final String SQL_GET_ALL = " from EXT_SYS_COMPANY where    EXTERNAL_SYSTEM_ID = ? ";
/*      */   protected ExtSysLedgerDAO mExtSysLedgerDAO;
/*      */   protected ExtSysCalendarYearDAO mExtSysCalendarYearDAO;
/*      */   protected ExtSysCompanyEVO mDetails;
/*      */ 
/*      */   public ExtSysCompanyDAO(Connection connection)
/*      */   {
/*   41 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ExtSysCompanyDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ExtSysCompanyDAO(DataSource ds)
/*      */   {
/*   57 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ExtSysCompanyPK getPK()
/*      */   {
/*   65 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ExtSysCompanyEVO details)
/*      */   {
/*   74 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private ExtSysCompanyEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   90 */     int col = 1;
/*   91 */     ExtSysCompanyEVO evo = new ExtSysCompanyEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), null, null);
/*      */ 
/*  101 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ExtSysCompanyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  106 */     int col = startCol_;
/*  107 */     stmt_.setInt(col++, evo_.getExternalSystemId());
/*  108 */     stmt_.setString(col++, evo_.getCompanyVisId());
/*  109 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ExtSysCompanyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  114 */     int col = startCol_;
/*  115 */     stmt_.setString(col++, evo_.getDescription());
/*  116 */     if (evo_.getDummy())
/*  117 */       stmt_.setString(col++, "Y");
/*      */     else
/*  119 */       stmt_.setString(col++, " ");
/*  120 */     stmt_.setInt(col++, evo_.getImportColumnCalendarIndex());
/*  121 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ExtSysCompanyPK pk)
/*      */     throws ValidationException
/*      */   {
/*  138 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  140 */     PreparedStatement stmt = null;
/*  141 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  145 */       stmt = getConnection().prepareStatement("select EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_COMPANY.DESCRIPTION,EXT_SYS_COMPANY.DUMMY,EXT_SYS_COMPANY.IMPORT_COLUMN_CALENDAR_INDEX from EXT_SYS_COMPANY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ");
/*      */ 
/*  148 */       int col = 1;
/*  149 */       stmt.setInt(col++, pk.getExternalSystemId());
/*  150 */       stmt.setString(col++, pk.getCompanyVisId());
/*      */ 
/*  152 */       resultSet = stmt.executeQuery();
/*      */ 
/*  154 */       if (!resultSet.next()) {
/*  155 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  158 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  159 */       if (this.mDetails.isModified())
/*  160 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  164 */       throw handleSQLException(pk, "select EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_COMPANY.DESCRIPTION,EXT_SYS_COMPANY.DUMMY,EXT_SYS_COMPANY.IMPORT_COLUMN_CALENDAR_INDEX from EXT_SYS_COMPANY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  168 */       closeResultSet(resultSet);
/*  169 */       closeStatement(stmt);
/*  170 */       closeConnection();
/*      */ 
/*  172 */       if (timer != null)
/*  173 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  200 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  201 */     this.mDetails.postCreateInit();
/*      */ 
/*  203 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  207 */       stmt = getConnection().prepareStatement("insert into EXT_SYS_COMPANY ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,DESCRIPTION,DUMMY,IMPORT_COLUMN_CALENDAR_INDEX) values ( ?,?,?,?,?)");
/*      */ 
/*  210 */       int col = 1;
/*  211 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  212 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  215 */       int resultCount = stmt.executeUpdate();
/*  216 */       if (resultCount != 1)
/*      */       {
/*  218 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  221 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  225 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXT_SYS_COMPANY ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,DESCRIPTION,DUMMY,IMPORT_COLUMN_CALENDAR_INDEX) values ( ?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  229 */       closeStatement(stmt);
/*  230 */       closeConnection();
/*      */ 
/*  232 */       if (timer != null) {
/*  233 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  239 */       getExtSysLedgerDAO().update(this.mDetails.getExtSysLedgerMap());
/*      */ 
/*  241 */       getExtSysCalendarYearDAO().update(this.mDetails.getExtSysCalendarYearsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  247 */       throw new RuntimeException("unexpected exception", e);
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
/*  281 */       if (getExtSysLedgerDAO().update(this.mDetails.getExtSysLedgerMap())) {
/*  282 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  285 */       if (getExtSysCalendarYearDAO().update(this.mDetails.getExtSysCalendarYearsMap())) {
/*  286 */         dependantChanged = true;
/*      */       }
/*  288 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  290 */         stmt = getConnection().prepareStatement("update EXT_SYS_COMPANY set DESCRIPTION = ?,DUMMY = ?,IMPORT_COLUMN_CALENDAR_INDEX = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ");
/*      */ 
/*  293 */         int col = 1;
/*  294 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  295 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  298 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  301 */         if (resultCount != 1) {
/*  302 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  305 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  314 */       throw handleSQLException(getPK(), "update EXT_SYS_COMPANY set DESCRIPTION = ?,DUMMY = ?,IMPORT_COLUMN_CALENDAR_INDEX = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  318 */       closeStatement(stmt);
/*  319 */       closeConnection();
/*      */ 
/*  321 */       if ((timer != null) && (
/*  322 */         (mainChanged) || (dependantChanged)))
/*  323 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllExternalSystemCompainesELO getAllExternalSystemCompaines()
/*      */   {
/*  363 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  364 */     PreparedStatement stmt = null;
/*  365 */     ResultSet resultSet = null;
/*  366 */     AllExternalSystemCompainesELO results = new AllExternalSystemCompainesELO();
/*      */     try
/*      */     {
/*  369 */       stmt = getConnection().prepareStatement(SQL_ALL_EXTERNAL_SYSTEM_COMPAINES);
/*  370 */       int col = 1;
/*  371 */       resultSet = stmt.executeQuery();
/*  372 */       while (resultSet.next())
/*      */       {
/*  374 */         col = 2;
/*      */ 
/*  377 */         ExternalSystemPK pkExternalSystem = new ExternalSystemPK(resultSet.getInt(col++));
/*      */ 
/*  380 */         String textExternalSystem = resultSet.getString(col++);
/*      */ 
/*  383 */         ExtSysCompanyPK pkExtSysCompany = new ExtSysCompanyPK(resultSet.getInt(col++), resultSet.getString(col++));
/*      */ 
/*  387 */         String textExtSysCompany = "";
/*      */ 
/*  392 */         ExtSysCompanyCK ckExtSysCompany = new ExtSysCompanyCK(pkExternalSystem, pkExtSysCompany);
/*      */ 
/*  398 */         ExternalSystemRefImpl erExternalSystem = new ExternalSystemRefImpl(pkExternalSystem, textExternalSystem);
/*      */ 
/*  404 */         ExtSysCompanyRefImpl erExtSysCompany = new ExtSysCompanyRefImpl(ckExtSysCompany, textExtSysCompany);
/*      */ 
/*  409 */         int col1 = resultSet.getInt(col++);
/*  410 */         String col2 = resultSet.getString(col++);
/*  411 */         String col3 = resultSet.getString(col++);
/*  412 */         if (resultSet.wasNull())
/*  413 */           col3 = "";
/*  414 */         int col4 = resultSet.getInt(col++);
/*      */ 
/*  417 */         results.add(erExtSysCompany, erExternalSystem, col1, col2, col3.equals("Y"), col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  429 */       throw handleSQLException(SQL_ALL_EXTERNAL_SYSTEM_COMPAINES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  433 */       closeResultSet(resultSet);
/*  434 */       closeStatement(stmt);
/*  435 */       closeConnection();
/*      */     }
/*      */ 
/*  438 */     if (timer != null) {
/*  439 */       timer.logDebug("getAllExternalSystemCompaines", " items=" + results.size());
/*      */     }
/*      */ 
/*  443 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  461 */     if (items == null) {
/*  462 */       return false;
/*      */     }
/*  464 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  465 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  467 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  471 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  472 */       while (iter3.hasNext())
/*      */       {
/*  474 */         this.mDetails = ((ExtSysCompanyEVO)iter3.next());
/*  475 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  477 */         somethingChanged = true;
/*      */ 
/*  480 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  484 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  485 */       while (iter2.hasNext())
/*      */       {
/*  487 */         this.mDetails = ((ExtSysCompanyEVO)iter2.next());
/*      */ 
/*  490 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  492 */         somethingChanged = true;
/*      */ 
/*  495 */         if (deleteStmt == null) {
/*  496 */           deleteStmt = getConnection().prepareStatement("delete from EXT_SYS_COMPANY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ");
/*      */         }
/*      */ 
/*  499 */         int col = 1;
/*  500 */         deleteStmt.setInt(col++, this.mDetails.getExternalSystemId());
/*  501 */         deleteStmt.setString(col++, this.mDetails.getCompanyVisId());
/*      */ 
/*  503 */         if (this._log.isDebugEnabled()) {
/*  504 */           this._log.debug("update", "ExtSysCompany deleting ExternalSystemId=" + this.mDetails.getExternalSystemId() + ",CompanyVisId=" + this.mDetails.getCompanyVisId());
/*      */         }
/*      */ 
/*  510 */         deleteStmt.addBatch();
/*      */ 
/*  513 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  518 */       if (deleteStmt != null)
/*      */       {
/*  520 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  522 */         deleteStmt.executeBatch();
/*      */ 
/*  524 */         if (timer2 != null) {
/*  525 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  529 */       Iterator iter1 = items.values().iterator();
/*  530 */       while (iter1.hasNext())
/*      */       {
/*  532 */         this.mDetails = ((ExtSysCompanyEVO)iter1.next());
/*      */ 
/*  534 */         if (this.mDetails.insertPending())
/*      */         {
/*  536 */           somethingChanged = true;
/*  537 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  540 */         if (this.mDetails.isModified())
/*      */         {
/*  542 */           somethingChanged = true;
/*  543 */           doStore(); continue;
/*      */         }
/*      */ 
/*  547 */         if (this.mDetails.deletePending())
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  553 */         if (getExtSysLedgerDAO().update(this.mDetails.getExtSysLedgerMap())) {
/*  554 */           somethingChanged = true;
/*      */         }
/*      */ 
/*  557 */         if (getExtSysCalendarYearDAO().update(this.mDetails.getExtSysCalendarYearsMap())) {
/*  558 */           somethingChanged = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  570 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  574 */       throw handleSQLException("delete from EXT_SYS_COMPANY where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  578 */       if (deleteStmt != null)
/*      */       {
/*  580 */         closeStatement(deleteStmt);
/*  581 */         closeConnection();
/*      */       }
/*      */ 
/*  584 */       this.mDetails = null;
/*      */ 
/*  586 */       if ((somethingChanged) && 
/*  587 */         (timer != null))
/*  588 */         timer.logDebug("update", "collection"); 
/*  588 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ExtSysCompanyPK pk)
/*      */   {
/*  796 */     Set emptyStrings = Collections.emptySet();
/*  797 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ExtSysCompanyPK pk, Set<String> exclusionTables)
/*      */   {
/*  803 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  805 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  807 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  809 */       PreparedStatement stmt = null;
/*      */ 
/*  811 */       int resultCount = 0;
/*  812 */       String s = null;
/*      */       try
/*      */       {
/*  815 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  817 */         if (this._log.isDebugEnabled()) {
/*  818 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  820 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  823 */         int col = 1;
/*  824 */         stmt.setInt(col++, pk.getExternalSystemId());
/*  825 */         stmt.setString(col++, pk.getCompanyVisId());
/*      */ 
/*  828 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  832 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  836 */         closeStatement(stmt);
/*  837 */         closeConnection();
/*      */ 
/*  839 */         if (timer != null) {
/*  840 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  844 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  846 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  848 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  850 */       PreparedStatement stmt = null;
/*      */ 
/*  852 */       int resultCount = 0;
/*  853 */       String s = null;
/*      */       try
/*      */       {
/*  856 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  858 */         if (this._log.isDebugEnabled()) {
/*  859 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  861 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  864 */         int col = 1;
/*  865 */         stmt.setInt(col++, pk.getExternalSystemId());
/*  866 */         stmt.setString(col++, pk.getCompanyVisId());
/*      */ 
/*  869 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  873 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  877 */         closeStatement(stmt);
/*  878 */         closeConnection();
/*      */ 
/*  880 */         if (timer != null)
/*  881 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ExternalSystemPK entityPK, ExternalSystemEVO owningEVO, String dependants)
/*      */   {
/*  902 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  904 */     PreparedStatement stmt = null;
/*  905 */     ResultSet resultSet = null;
/*      */ 
/*  907 */     int itemCount = 0;
/*      */ 
/*  909 */     Collection theseItems = new ArrayList();
/*  910 */     owningEVO.setExtSysCompanies(theseItems);
/*  911 */     owningEVO.setExtSysCompaniesAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  915 */       stmt = getConnection().prepareStatement("select EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_COMPANY.DESCRIPTION,EXT_SYS_COMPANY.DUMMY,EXT_SYS_COMPANY.IMPORT_COLUMN_CALENDAR_INDEX from EXT_SYS_COMPANY where 1=1 and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID ,EXT_SYS_COMPANY.COMPANY_VIS_ID");
/*      */ 
/*  917 */       int col = 1;
/*  918 */       stmt.setInt(col++, entityPK.getExternalSystemId());
/*      */ 
/*  920 */       resultSet = stmt.executeQuery();
/*      */ 
/*  923 */       while (resultSet.next())
/*      */       {
/*  925 */         itemCount++;
/*  926 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  928 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  931 */       if (timer != null) {
/*  932 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  935 */       if ((itemCount > 0) && (dependants.indexOf("<1>") > -1))
/*      */       {
/*  937 */         getExtSysLedgerDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*  939 */       if ((itemCount > 0) && (dependants.indexOf("<9>") > -1))
/*      */       {
/*  941 */         getExtSysCalendarYearDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  945 */       throw handleSQLException("select EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_COMPANY.DESCRIPTION,EXT_SYS_COMPANY.DUMMY,EXT_SYS_COMPANY.IMPORT_COLUMN_CALENDAR_INDEX from EXT_SYS_COMPANY where 1=1 and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID ,EXT_SYS_COMPANY.COMPANY_VIS_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  949 */       closeResultSet(resultSet);
/*  950 */       closeStatement(stmt);
/*  951 */       closeConnection();
/*      */ 
/*  953 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectExternalSystemId, String dependants, Collection currentList)
/*      */   {
/*  978 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  979 */     PreparedStatement stmt = null;
/*  980 */     ResultSet resultSet = null;
/*      */ 
/*  982 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  986 */       stmt = getConnection().prepareStatement("select EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_COMPANY.DESCRIPTION,EXT_SYS_COMPANY.DUMMY,EXT_SYS_COMPANY.IMPORT_COLUMN_CALENDAR_INDEX from EXT_SYS_COMPANY where    EXTERNAL_SYSTEM_ID = ? ");
/*      */ 
/*  988 */       int col = 1;
/*  989 */       stmt.setInt(col++, selectExternalSystemId);
/*      */ 
/*  991 */       resultSet = stmt.executeQuery();
/*      */ 
/*  993 */       while (resultSet.next())
/*      */       {
/*  995 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  998 */         getDependants(this.mDetails, dependants);
/*      */ 
/* 1001 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1004 */       if (currentList != null)
/*      */       {
/* 1007 */         ListIterator iter = items.listIterator();
/* 1008 */         ExtSysCompanyEVO currentEVO = null;
/* 1009 */         ExtSysCompanyEVO newEVO = null;
/* 1010 */         while (iter.hasNext())
/*      */         {
/* 1012 */           newEVO = (ExtSysCompanyEVO)iter.next();
/* 1013 */           Iterator iter2 = currentList.iterator();
/* 1014 */           while (iter2.hasNext())
/*      */           {
/* 1016 */             currentEVO = (ExtSysCompanyEVO)iter2.next();
/* 1017 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1019 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1025 */         Iterator iter2 = currentList.iterator();
/* 1026 */         while (iter2.hasNext())
/*      */         {
/* 1028 */           currentEVO = (ExtSysCompanyEVO)iter2.next();
/* 1029 */           if (currentEVO.insertPending()) {
/* 1030 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1034 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1038 */       throw handleSQLException("select EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID,EXT_SYS_COMPANY.DESCRIPTION,EXT_SYS_COMPANY.DUMMY,EXT_SYS_COMPANY.IMPORT_COLUMN_CALENDAR_INDEX from EXT_SYS_COMPANY where    EXTERNAL_SYSTEM_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1042 */       closeResultSet(resultSet);
/* 1043 */       closeStatement(stmt);
/* 1044 */       closeConnection();
/*      */ 
/* 1046 */       if (timer != null) {
/* 1047 */         timer.logDebug("getAll", " ExternalSystemId=" + selectExternalSystemId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1052 */     return items;
/*      */   }
/*      */ 
/*      */   public ExtSysCompanyEVO getDetails(ExternalSystemCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1087 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1090 */     if (this.mDetails == null) {
/* 1091 */       doLoad(((ExtSysCompanyCK)paramCK).getExtSysCompanyPK());
/*      */     }
/* 1093 */     else if (!this.mDetails.getPK().equals(((ExtSysCompanyCK)paramCK).getExtSysCompanyPK())) {
/* 1094 */       doLoad(((ExtSysCompanyCK)paramCK).getExtSysCompanyPK());
/*      */     }
/*      */ 
/* 1097 */     if ((dependants.indexOf("<1>") > -1) && (!this.mDetails.isExtSysLedgerAllItemsLoaded()))
/*      */     {
/* 1102 */       this.mDetails.setExtSysLedger(getExtSysLedgerDAO().getAll(this.mDetails.getExternalSystemId(), this.mDetails.getCompanyVisId(), dependants, this.mDetails.getExtSysLedger()));
/*      */ 
/* 1110 */       this.mDetails.setExtSysLedgerAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1114 */     if ((dependants.indexOf("<9>") > -1) && (!this.mDetails.isExtSysCalendarYearsAllItemsLoaded()))
/*      */     {
/* 1119 */       this.mDetails.setExtSysCalendarYears(getExtSysCalendarYearDAO().getAll(this.mDetails.getExternalSystemId(), this.mDetails.getCompanyVisId(), dependants, this.mDetails.getExtSysCalendarYears()));
/*      */ 
/* 1127 */       this.mDetails.setExtSysCalendarYearsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1130 */     if ((paramCK instanceof ExtSysLedgerCK))
/*      */     {
/* 1132 */       if (this.mDetails.getExtSysLedger() == null) {
/* 1133 */         this.mDetails.loadExtSysLedgerItem(getExtSysLedgerDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1136 */         ExtSysLedgerPK pk = ((ExtSysLedgerCK)paramCK).getExtSysLedgerPK();
/* 1137 */         ExtSysLedgerEVO evo = this.mDetails.getExtSysLedgerItem(pk);
/* 1138 */         if (evo == null)
/* 1139 */           this.mDetails.loadExtSysLedgerItem(getExtSysLedgerDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1141 */           getExtSysLedgerDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/* 1145 */     else if ((paramCK instanceof ExtSysCalendarYearCK))
/*      */     {
/* 1147 */       if (this.mDetails.getExtSysCalendarYears() == null) {
/* 1148 */         this.mDetails.loadExtSysCalendarYearsItem(getExtSysCalendarYearDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1151 */         ExtSysCalendarYearPK pk = ((ExtSysCalendarYearCK)paramCK).getExtSysCalendarYearPK();
/* 1152 */         ExtSysCalendarYearEVO evo = this.mDetails.getExtSysCalendarYearsItem(pk);
/* 1153 */         if (evo == null)
/* 1154 */           this.mDetails.loadExtSysCalendarYearsItem(getExtSysCalendarYearDAO().getDetails(paramCK, dependants));
/*      */         else {
/* 1156 */           getExtSysCalendarYearDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1161 */     ExtSysCompanyEVO details = new ExtSysCompanyEVO();
/* 1162 */     details = this.mDetails.deepClone();
/*      */ 
/* 1164 */     if (timer != null) {
/* 1165 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1167 */     return details;
/*      */   }
/*      */ 
/*      */   public ExtSysCompanyEVO getDetails(ExternalSystemCK paramCK, ExtSysCompanyEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1173 */     ExtSysCompanyEVO savedEVO = this.mDetails;
/* 1174 */     this.mDetails = paramEVO;
/* 1175 */     ExtSysCompanyEVO newEVO = getDetails(paramCK, dependants);
/* 1176 */     this.mDetails = savedEVO;
/* 1177 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public ExtSysCompanyEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1183 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1187 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1190 */     ExtSysCompanyEVO details = this.mDetails.deepClone();
/*      */ 
/* 1192 */     if (timer != null) {
/* 1193 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1195 */     return details;
/*      */   }
/*      */ 
/*      */   protected ExtSysLedgerDAO getExtSysLedgerDAO()
/*      */   {
/* 1204 */     if (this.mExtSysLedgerDAO == null)
/*      */     {
/* 1206 */       if (this.mDataSource != null)
/* 1207 */         this.mExtSysLedgerDAO = new ExtSysLedgerDAO(this.mDataSource);
/*      */       else {
/* 1209 */         this.mExtSysLedgerDAO = new ExtSysLedgerDAO(getConnection());
/*      */       }
/*      */     }
/* 1212 */     return this.mExtSysLedgerDAO;
/*      */   }
/*      */ 
/*      */   protected ExtSysCalendarYearDAO getExtSysCalendarYearDAO()
/*      */   {
/* 1221 */     if (this.mExtSysCalendarYearDAO == null)
/*      */     {
/* 1223 */       if (this.mDataSource != null)
/* 1224 */         this.mExtSysCalendarYearDAO = new ExtSysCalendarYearDAO(this.mDataSource);
/*      */       else {
/* 1226 */         this.mExtSysCalendarYearDAO = new ExtSysCalendarYearDAO(getConnection());
/*      */       }
/*      */     }
/* 1229 */     return this.mExtSysCalendarYearDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1234 */     return "ExtSysCompany";
/*      */   }
/*      */ 
/*      */   public ExtSysCompanyRefImpl getRef(ExtSysCompanyPK paramExtSysCompanyPK)
/*      */   {
/* 1239 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1240 */     PreparedStatement stmt = null;
/* 1241 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1244 */       stmt = getConnection().prepareStatement("select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID from EXT_SYS_COMPANY,EXTERNAL_SYSTEM where 1=1 and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_COMPANY.COMPANY_VIS_ID = ? and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID");
/* 1245 */       int col = 1;
/* 1246 */       stmt.setInt(col++, paramExtSysCompanyPK.getExternalSystemId());
/* 1247 */       stmt.setString(col++, paramExtSysCompanyPK.getCompanyVisId());
/*      */ 
/* 1249 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1251 */       if (!resultSet.next()) {
/* 1252 */         throw new RuntimeException(getEntityName() + " getRef " + paramExtSysCompanyPK + " not found");
/*      */       }
/* 1254 */       col = 2;
/* 1255 */       ExternalSystemPK newExternalSystemPK = new ExternalSystemPK(resultSet.getInt(col++));
/*      */ 
/* 1259 */       String textExtSysCompany = "";
/* 1260 */       ExtSysCompanyCK ckExtSysCompany = new ExtSysCompanyCK(newExternalSystemPK, paramExtSysCompanyPK);
/*      */ 
/* 1265 */       ExtSysCompanyRefImpl localExtSysCompanyRefImpl = new ExtSysCompanyRefImpl(ckExtSysCompany, textExtSysCompany);
/*      */       return localExtSysCompanyRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1270 */       throw handleSQLException(paramExtSysCompanyPK, "select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID from EXT_SYS_COMPANY,EXTERNAL_SYSTEM where 1=1 and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_COMPANY.COMPANY_VIS_ID = ? and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1274 */       closeResultSet(resultSet);
/* 1275 */       closeStatement(stmt);
/* 1276 */       closeConnection();
/*      */ 
/* 1278 */       if (timer != null)
/* 1279 */         timer.logDebug("getRef", paramExtSysCompanyPK); 
/* 1279 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1291 */     if (c == null)
/* 1292 */       return;
/* 1293 */     Iterator iter = c.iterator();
/* 1294 */     while (iter.hasNext())
/*      */     {
/* 1296 */       ExtSysCompanyEVO evo = (ExtSysCompanyEVO)iter.next();
/* 1297 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(ExtSysCompanyEVO evo, String dependants)
/*      */   {
/* 1311 */     if (evo.insertPending()) {
/* 1312 */       return;
/*      */     }
/*      */ 
/* 1316 */     if ((dependants.indexOf("<1>") > -1) || (dependants.indexOf("<2>") > -1) || (dependants.indexOf("<3>") > -1) || (dependants.indexOf("<4>") > -1) || (dependants.indexOf("<5>") > -1) || (dependants.indexOf("<6>") > -1) || (dependants.indexOf("<7>") > -1) || (dependants.indexOf("<8>") > -1))
/*      */     {
/* 1326 */       if (!evo.isExtSysLedgerAllItemsLoaded())
/*      */       {
/* 1328 */         evo.setExtSysLedger(getExtSysLedgerDAO().getAll(evo.getExternalSystemId(), evo.getCompanyVisId(), dependants, evo.getExtSysLedger()));
/*      */ 
/* 1336 */         evo.setExtSysLedgerAllItemsLoaded(true);
/*      */       }
/* 1338 */       getExtSysLedgerDAO().getDependants(evo.getExtSysLedger(), dependants);
/*      */     }
/*      */ 
/* 1342 */     if ((dependants.indexOf("<9>") > -1) || (dependants.indexOf("<10>") > -1))
/*      */     {
/* 1346 */       if (!evo.isExtSysCalendarYearsAllItemsLoaded())
/*      */       {
/* 1348 */         evo.setExtSysCalendarYears(getExtSysCalendarYearDAO().getAll(evo.getExternalSystemId(), evo.getCompanyVisId(), dependants, evo.getExtSysCalendarYears()));
/*      */ 
/* 1356 */         evo.setExtSysCalendarYearsAllItemsLoaded(true);
/*      */       }
/* 1358 */       getExtSysCalendarYearDAO().getDependants(evo.getExtSysCalendarYears(), dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void insert(ExtSysCompanyEVO evo)
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/* 1375 */     setDetails(evo);
/* 1376 */     doCreate();
/*      */   }
/*      */ 
/*      */   public int checkConstraintViolations(int externalSystemId, int maxToReport, PrintWriter log)
/*      */   {
/* 1388 */     SqlExecutor sqlExecutor = null;
/* 1389 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1393 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "select external_system_id, company_vis_id, count(*) as counter", "from ext_sys_company", "where external_system_id = <externalSystemId>", "group by external_system_id, company_vis_id", "having count(*) > 1" });
/*      */ 
/* 1399 */       sqlExecutor = new SqlExecutor("checkConstraintViolations", getDataSource(), sqlBuilder, this._log);
/*      */ 
/* 1401 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/*      */ 
/* 1403 */       rs = sqlExecutor.getResultSet();
/*      */ 
/* 1405 */       int count = 0;
/* 1406 */       while ((rs.next()) && (count < maxToReport))
/*      */       {
/* 1408 */         log.print("Found duplicate company : [" + rs.getString("COMPANY_VIS_ID") + "]");
/* 1409 */         count++;
/*      */       }
/*      */ 
/* 1412 */       int i = count;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1416 */       throw handleSQLException("checkConstraintViolations", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1420 */       closeResultSet(rs);
/* 1421 */       sqlExecutor.close(); } //throw localObject;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extsys.ExtSysCompanyDAO
 * JD-Core Version:    0.6.0
 */