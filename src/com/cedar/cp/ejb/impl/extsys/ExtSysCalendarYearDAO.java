/*      */ package com.cedar.cp.ejb.impl.extsys;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCalElementCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCalElementPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCalendarYearCK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCalendarYearPK;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCalendarYearRefImpl;
/*      */ import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
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
/*      */ public class ExtSysCalendarYearDAO extends AbstractDAO
/*      */ {
/*   34 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID,EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID,EXT_SYS_CALENDAR_YEAR.YEAR";
/*      */   protected static final String SQL_LOAD = " from EXT_SYS_CALENDAR_YEAR where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into EXT_SYS_CALENDAR_YEAR ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,CALENDAR_YEAR_VIS_ID,YEAR) values ( ?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update EXT_SYS_CALENDAR_YEAR set YEAR = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from EXT_SYS_CALENDAR_YEAR where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? ";
/*  459 */   private static String[][] SQL_DELETE_CHILDREN = { { "EXT_SYS_CAL_ELEMENT", "delete from EXT_SYS_CAL_ELEMENT where     EXT_SYS_CAL_ELEMENT.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_CAL_ELEMENT.COMPANY_VIS_ID = ? and EXT_SYS_CAL_ELEMENT.CALENDAR_YEAR_VIS_ID = ? " } };
/*      */ 
/*  470 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  474 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = ?and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = ?and EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from EXT_SYS_CALENDAR_YEAR,EXT_SYS_COMPANY where 1=1 and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID ,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID ,EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID ,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID ,EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID";
/*      */   protected static final String SQL_GET_ALL = " from EXT_SYS_CALENDAR_YEAR where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ";
/*      */   protected ExtSysCalElementDAO mExtSysCalElementDAO;
/*      */   protected ExtSysCalendarYearEVO mDetails;
/*      */ 
/*      */   public ExtSysCalendarYearDAO(Connection connection)
/*      */   {
/*   41 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ExtSysCalendarYearDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ExtSysCalendarYearDAO(DataSource ds)
/*      */   {
/*   57 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ExtSysCalendarYearPK getPK()
/*      */   {
/*   65 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ExtSysCalendarYearEVO details)
/*      */   {
/*   74 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private ExtSysCalendarYearEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   89 */     int col = 1;
/*   90 */     ExtSysCalendarYearEVO evo = new ExtSysCalendarYearEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), null);
/*      */ 
/*   98 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ExtSysCalendarYearEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  103 */     int col = startCol_;
/*  104 */     stmt_.setInt(col++, evo_.getExternalSystemId());
/*  105 */     stmt_.setString(col++, evo_.getCompanyVisId());
/*  106 */     stmt_.setString(col++, evo_.getCalendarYearVisId());
/*  107 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ExtSysCalendarYearEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  112 */     int col = startCol_;
/*  113 */     stmt_.setInt(col++, evo_.getYear());
/*  114 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ExtSysCalendarYearPK pk)
/*      */     throws ValidationException
/*      */   {
/*  132 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  134 */     PreparedStatement stmt = null;
/*  135 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  139 */       stmt = getConnection().prepareStatement("select EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID,EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID,EXT_SYS_CALENDAR_YEAR.YEAR from EXT_SYS_CALENDAR_YEAR where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? ");
/*      */ 
/*  142 */       int col = 1;
/*  143 */       stmt.setInt(col++, pk.getExternalSystemId());
/*  144 */       stmt.setString(col++, pk.getCompanyVisId());
/*  145 */       stmt.setString(col++, pk.getCalendarYearVisId());
/*      */ 
/*  147 */       resultSet = stmt.executeQuery();
/*      */ 
/*  149 */       if (!resultSet.next()) {
/*  150 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  153 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  154 */       if (this.mDetails.isModified())
/*  155 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  159 */       throw handleSQLException(pk, "select EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID,EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID,EXT_SYS_CALENDAR_YEAR.YEAR from EXT_SYS_CALENDAR_YEAR where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  163 */       closeResultSet(resultSet);
/*  164 */       closeStatement(stmt);
/*  165 */       closeConnection();
/*      */ 
/*  167 */       if (timer != null)
/*  168 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  193 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  194 */     this.mDetails.postCreateInit();
/*      */ 
/*  196 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  200 */       stmt = getConnection().prepareStatement("insert into EXT_SYS_CALENDAR_YEAR ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,CALENDAR_YEAR_VIS_ID,YEAR) values ( ?,?,?,?)");
/*      */ 
/*  203 */       int col = 1;
/*  204 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  205 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  208 */       int resultCount = stmt.executeUpdate();
/*  209 */       if (resultCount != 1)
/*      */       {
/*  211 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  214 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  218 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXT_SYS_CALENDAR_YEAR ( EXTERNAL_SYSTEM_ID,COMPANY_VIS_ID,CALENDAR_YEAR_VIS_ID,YEAR) values ( ?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  222 */       closeStatement(stmt);
/*  223 */       closeConnection();
/*      */ 
/*  225 */       if (timer != null) {
/*  226 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  232 */       getExtSysCalElementDAO().update(this.mDetails.getExtSysCalendarElementsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  238 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  260 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  264 */     PreparedStatement stmt = null;
/*      */ 
/*  266 */     boolean mainChanged = this.mDetails.isModified();
/*  267 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  271 */       if (getExtSysCalElementDAO().update(this.mDetails.getExtSysCalendarElementsMap())) {
/*  272 */         dependantChanged = true;
/*      */       }
/*  274 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  276 */         stmt = getConnection().prepareStatement("update EXT_SYS_CALENDAR_YEAR set YEAR = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? ");
/*      */ 
/*  279 */         int col = 1;
/*  280 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  281 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  284 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  287 */         if (resultCount != 1) {
/*  288 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  291 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  300 */       throw handleSQLException(getPK(), "update EXT_SYS_CALENDAR_YEAR set YEAR = ? where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  304 */       closeStatement(stmt);
/*  305 */       closeConnection();
/*      */ 
/*  307 */       if ((timer != null) && (
/*  308 */         (mainChanged) || (dependantChanged)))
/*  309 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  330 */     if (items == null) {
/*  331 */       return false;
/*      */     }
/*  333 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  334 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  336 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  340 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  341 */       while (iter3.hasNext())
/*      */       {
/*  343 */         this.mDetails = ((ExtSysCalendarYearEVO)iter3.next());
/*  344 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  346 */         somethingChanged = true;
/*      */ 
/*  349 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  353 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  354 */       while (iter2.hasNext())
/*      */       {
/*  356 */         this.mDetails = ((ExtSysCalendarYearEVO)iter2.next());
/*      */ 
/*  359 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  361 */         somethingChanged = true;
/*      */ 
/*  364 */         if (deleteStmt == null) {
/*  365 */           deleteStmt = getConnection().prepareStatement("delete from EXT_SYS_CALENDAR_YEAR where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? ");
/*      */         }
/*      */ 
/*  368 */         int col = 1;
/*  369 */         deleteStmt.setInt(col++, this.mDetails.getExternalSystemId());
/*  370 */         deleteStmt.setString(col++, this.mDetails.getCompanyVisId());
/*  371 */         deleteStmt.setString(col++, this.mDetails.getCalendarYearVisId());
/*      */ 
/*  373 */         if (this._log.isDebugEnabled()) {
/*  374 */           this._log.debug("update", "ExtSysCalendarYear deleting ExternalSystemId=" + this.mDetails.getExternalSystemId() + ",CompanyVisId=" + this.mDetails.getCompanyVisId() + ",CalendarYearVisId=" + this.mDetails.getCalendarYearVisId());
/*      */         }
/*      */ 
/*  381 */         deleteStmt.addBatch();
/*      */ 
/*  384 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  389 */       if (deleteStmt != null)
/*      */       {
/*  391 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  393 */         deleteStmt.executeBatch();
/*      */ 
/*  395 */         if (timer2 != null) {
/*  396 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  400 */       Iterator iter1 = items.values().iterator();
/*  401 */       while (iter1.hasNext())
/*      */       {
/*  403 */         this.mDetails = ((ExtSysCalendarYearEVO)iter1.next());
/*      */ 
/*  405 */         if (this.mDetails.insertPending())
/*      */         {
/*  407 */           somethingChanged = true;
/*  408 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  411 */         if (this.mDetails.isModified())
/*      */         {
/*  413 */           somethingChanged = true;
/*  414 */           doStore(); continue;
/*      */         }
/*      */ 
/*  418 */         if ((this.mDetails.deletePending()) || 
/*  424 */           (!getExtSysCalElementDAO().update(this.mDetails.getExtSysCalendarElementsMap()))) continue;
/*  425 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  437 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  441 */       throw handleSQLException("delete from EXT_SYS_CALENDAR_YEAR where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? AND CALENDAR_YEAR_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  445 */       if (deleteStmt != null)
/*      */       {
/*  447 */         closeStatement(deleteStmt);
/*  448 */         closeConnection();
/*      */       }
/*      */ 
/*  451 */       this.mDetails = null;
/*      */ 
/*  453 */       if ((somethingChanged) && 
/*  454 */         (timer != null))
/*  455 */         timer.logDebug("update", "collection"); 
/*  455 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ExtSysCalendarYearPK pk)
/*      */   {
/*  485 */     Set emptyStrings = Collections.emptySet();
/*  486 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(ExtSysCalendarYearPK pk, Set<String> exclusionTables)
/*      */   {
/*  492 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  494 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  496 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  498 */       PreparedStatement stmt = null;
/*      */ 
/*  500 */       int resultCount = 0;
/*  501 */       String s = null;
/*      */       try
/*      */       {
/*  504 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  506 */         if (this._log.isDebugEnabled()) {
/*  507 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  509 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  512 */         int col = 1;
/*  513 */         stmt.setInt(col++, pk.getExternalSystemId());
/*  514 */         stmt.setString(col++, pk.getCompanyVisId());
/*  515 */         stmt.setString(col++, pk.getCalendarYearVisId());
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
/*  555 */         stmt.setInt(col++, pk.getExternalSystemId());
/*  556 */         stmt.setString(col++, pk.getCompanyVisId());
/*  557 */         stmt.setString(col++, pk.getCalendarYearVisId());
/*      */ 
/*  560 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  564 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  568 */         closeStatement(stmt);
/*  569 */         closeConnection();
/*      */ 
/*  571 */         if (timer != null)
/*  572 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ExternalSystemPK entityPK, Collection owners, String dependants)
/*      */   {
/*  600 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  602 */     PreparedStatement stmt = null;
/*  603 */     ResultSet resultSet = null;
/*      */ 
/*  605 */     int itemCount = 0;
/*      */ 
/*  607 */     ExtSysCompanyEVO owningEVO = null;
/*  608 */     Iterator ownersIter = owners.iterator();
/*  609 */     while (ownersIter.hasNext())
/*      */     {
/*  611 */       owningEVO = (ExtSysCompanyEVO)ownersIter.next();
/*  612 */       owningEVO.setExtSysCalendarYearsAllItemsLoaded(true);
/*      */     }
/*  614 */     ownersIter = owners.iterator();
/*  615 */     owningEVO = (ExtSysCompanyEVO)ownersIter.next();
/*  616 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  620 */       stmt = getConnection().prepareStatement("select EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID,EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID,EXT_SYS_CALENDAR_YEAR.YEAR from EXT_SYS_CALENDAR_YEAR,EXT_SYS_COMPANY where 1=1 and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID ,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID ,EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID ,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID ,EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID");
/*      */ 
/*  622 */       int col = 1;
/*  623 */       stmt.setInt(col++, entityPK.getExternalSystemId());
/*      */ 
/*  625 */       resultSet = stmt.executeQuery();
/*      */ 
/*  628 */       while (resultSet.next())
/*      */       {
/*  630 */         itemCount++;
/*  631 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  636 */         while ((this.mDetails.getExternalSystemId() != owningEVO.getExternalSystemId()) || (!this.mDetails.getCompanyVisId().equals(owningEVO.getCompanyVisId())))
/*      */         {
/*  641 */           if (!ownersIter.hasNext())
/*      */           {
/*  643 */             this._log.debug("bulkGetAll", "can't find owning [ExternalSystemId=" + this.mDetails.getExternalSystemId() + "CompanyVisId=" + this.mDetails.getCompanyVisId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  648 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  649 */             ownersIter = owners.iterator();
/*  650 */             while (ownersIter.hasNext())
/*      */             {
/*  652 */               owningEVO = (ExtSysCompanyEVO)ownersIter.next();
/*  653 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  655 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  657 */           owningEVO = (ExtSysCompanyEVO)ownersIter.next();
/*      */         }
/*  659 */         if (owningEVO.getExtSysCalendarYears() == null)
/*      */         {
/*  661 */           theseItems = new ArrayList();
/*  662 */           owningEVO.setExtSysCalendarYears(theseItems);
/*  663 */           owningEVO.setExtSysCalendarYearsAllItemsLoaded(true);
/*      */         }
/*  665 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  668 */       if (timer != null) {
/*  669 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  672 */       if ((itemCount > 0) && (dependants.indexOf("<10>") > -1))
/*      */       {
/*  674 */         getExtSysCalElementDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  678 */       throw handleSQLException("select EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID,EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID,EXT_SYS_CALENDAR_YEAR.YEAR from EXT_SYS_CALENDAR_YEAR,EXT_SYS_COMPANY where 1=1 and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID ,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID ,EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID ,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID ,EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  682 */       closeResultSet(resultSet);
/*  683 */       closeStatement(stmt);
/*  684 */       closeConnection();
/*      */ 
/*  686 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectExternalSystemId, String selectCompanyVisId, String dependants, Collection currentList)
/*      */   {
/*  714 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  715 */     PreparedStatement stmt = null;
/*  716 */     ResultSet resultSet = null;
/*      */ 
/*  718 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  722 */       stmt = getConnection().prepareStatement("select EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID,EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID,EXT_SYS_CALENDAR_YEAR.YEAR from EXT_SYS_CALENDAR_YEAR where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ");
/*      */ 
/*  724 */       int col = 1;
/*  725 */       stmt.setInt(col++, selectExternalSystemId);
/*  726 */       stmt.setString(col++, selectCompanyVisId);
/*      */ 
/*  728 */       resultSet = stmt.executeQuery();
/*      */ 
/*  730 */       while (resultSet.next())
/*      */       {
/*  732 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  735 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  738 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  741 */       if (currentList != null)
/*      */       {
/*  744 */         ListIterator iter = items.listIterator();
/*  745 */         ExtSysCalendarYearEVO currentEVO = null;
/*  746 */         ExtSysCalendarYearEVO newEVO = null;
/*  747 */         while (iter.hasNext())
/*      */         {
/*  749 */           newEVO = (ExtSysCalendarYearEVO)iter.next();
/*  750 */           Iterator iter2 = currentList.iterator();
/*  751 */           while (iter2.hasNext())
/*      */           {
/*  753 */             currentEVO = (ExtSysCalendarYearEVO)iter2.next();
/*  754 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  756 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  762 */         Iterator iter2 = currentList.iterator();
/*  763 */         while (iter2.hasNext())
/*      */         {
/*  765 */           currentEVO = (ExtSysCalendarYearEVO)iter2.next();
/*  766 */           if (currentEVO.insertPending()) {
/*  767 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  771 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  775 */       throw handleSQLException("select EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID,EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID,EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID,EXT_SYS_CALENDAR_YEAR.YEAR from EXT_SYS_CALENDAR_YEAR where    EXTERNAL_SYSTEM_ID = ? AND COMPANY_VIS_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  779 */       closeResultSet(resultSet);
/*  780 */       closeStatement(stmt);
/*  781 */       closeConnection();
/*      */ 
/*  783 */       if (timer != null) {
/*  784 */         timer.logDebug("getAll", " ExternalSystemId=" + selectExternalSystemId + ",CompanyVisId=" + selectCompanyVisId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  790 */     return items;
/*      */   }
/*      */ 
/*      */   public ExtSysCalendarYearEVO getDetails(ExternalSystemCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  807 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  810 */     if (this.mDetails == null) {
/*  811 */       doLoad(((ExtSysCalendarYearCK)paramCK).getExtSysCalendarYearPK());
/*      */     }
/*  813 */     else if (!this.mDetails.getPK().equals(((ExtSysCalendarYearCK)paramCK).getExtSysCalendarYearPK())) {
/*  814 */       doLoad(((ExtSysCalendarYearCK)paramCK).getExtSysCalendarYearPK());
/*      */     }
/*      */ 
/*  817 */     if ((dependants.indexOf("<10>") > -1) && (!this.mDetails.isExtSysCalendarElementsAllItemsLoaded()))
/*      */     {
/*  822 */       this.mDetails.setExtSysCalendarElements(getExtSysCalElementDAO().getAll(this.mDetails.getExternalSystemId(), this.mDetails.getCompanyVisId(), this.mDetails.getCalendarYearVisId(), dependants, this.mDetails.getExtSysCalendarElements()));
/*      */ 
/*  831 */       this.mDetails.setExtSysCalendarElementsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  834 */     if ((paramCK instanceof ExtSysCalElementCK))
/*      */     {
/*  836 */       if (this.mDetails.getExtSysCalendarElements() == null) {
/*  837 */         this.mDetails.loadExtSysCalendarElementsItem(getExtSysCalElementDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  840 */         ExtSysCalElementPK pk = ((ExtSysCalElementCK)paramCK).getExtSysCalElementPK();
/*  841 */         ExtSysCalElementEVO evo = this.mDetails.getExtSysCalendarElementsItem(pk);
/*  842 */         if (evo == null) {
/*  843 */           this.mDetails.loadExtSysCalendarElementsItem(getExtSysCalElementDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  848 */     ExtSysCalendarYearEVO details = new ExtSysCalendarYearEVO();
/*  849 */     details = this.mDetails.deepClone();
/*      */ 
/*  851 */     if (timer != null) {
/*  852 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  854 */     return details;
/*      */   }
/*      */ 
/*      */   public ExtSysCalendarYearEVO getDetails(ExternalSystemCK paramCK, ExtSysCalendarYearEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  860 */     ExtSysCalendarYearEVO savedEVO = this.mDetails;
/*  861 */     this.mDetails = paramEVO;
/*  862 */     ExtSysCalendarYearEVO newEVO = getDetails(paramCK, dependants);
/*  863 */     this.mDetails = savedEVO;
/*  864 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public ExtSysCalendarYearEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  870 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  874 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  877 */     ExtSysCalendarYearEVO details = this.mDetails.deepClone();
/*      */ 
/*  879 */     if (timer != null) {
/*  880 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  882 */     return details;
/*      */   }
/*      */ 
/*      */   protected ExtSysCalElementDAO getExtSysCalElementDAO()
/*      */   {
/*  891 */     if (this.mExtSysCalElementDAO == null)
/*      */     {
/*  893 */       if (this.mDataSource != null)
/*  894 */         this.mExtSysCalElementDAO = new ExtSysCalElementDAO(this.mDataSource);
/*      */       else {
/*  896 */         this.mExtSysCalElementDAO = new ExtSysCalElementDAO(getConnection());
/*      */       }
/*      */     }
/*  899 */     return this.mExtSysCalElementDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  904 */     return "ExtSysCalendarYear";
/*      */   }
/*      */ 
/*      */   public ExtSysCalendarYearRefImpl getRef(ExtSysCalendarYearPK paramExtSysCalendarYearPK)
/*      */   {
/*  909 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  910 */     PreparedStatement stmt = null;
/*  911 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  914 */       stmt = getConnection().prepareStatement("select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID from EXT_SYS_CALENDAR_YEAR,EXTERNAL_SYSTEM,EXT_SYS_COMPANY where 1=1 and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = ? and EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID = ? and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID");
/*  915 */       int col = 1;
/*  916 */       stmt.setInt(col++, paramExtSysCalendarYearPK.getExternalSystemId());
/*  917 */       stmt.setString(col++, paramExtSysCalendarYearPK.getCompanyVisId());
/*  918 */       stmt.setString(col++, paramExtSysCalendarYearPK.getCalendarYearVisId());
/*      */ 
/*  920 */       resultSet = stmt.executeQuery();
/*      */ 
/*  922 */       if (!resultSet.next()) {
/*  923 */         throw new RuntimeException(getEntityName() + " getRef " + paramExtSysCalendarYearPK + " not found");
/*      */       }
/*  925 */       col = 2;
/*  926 */       ExternalSystemPK newExternalSystemPK = new ExternalSystemPK(resultSet.getInt(col++));
/*      */ 
/*  930 */       ExtSysCompanyPK newExtSysCompanyPK = new ExtSysCompanyPK(resultSet.getInt(col++), resultSet.getString(col++));
/*      */ 
/*  935 */       String textExtSysCalendarYear = "";
/*  936 */       ExtSysCalendarYearCK ckExtSysCalendarYear = new ExtSysCalendarYearCK(newExternalSystemPK, newExtSysCompanyPK, paramExtSysCalendarYearPK);
/*      */ 
/*  942 */       ExtSysCalendarYearRefImpl localExtSysCalendarYearRefImpl = new ExtSysCalendarYearRefImpl(ckExtSysCalendarYear, textExtSysCalendarYear);
/*      */       return localExtSysCalendarYearRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  947 */       throw handleSQLException(paramExtSysCalendarYearPK, "select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID,EXT_SYS_COMPANY.COMPANY_VIS_ID from EXT_SYS_CALENDAR_YEAR,EXTERNAL_SYSTEM,EXT_SYS_COMPANY where 1=1 and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = ? and EXT_SYS_CALENDAR_YEAR.CALENDAR_YEAR_VIS_ID = ? and EXT_SYS_CALENDAR_YEAR.EXTERNAL_SYSTEM_ID = EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID and EXT_SYS_CALENDAR_YEAR.COMPANY_VIS_ID = EXT_SYS_COMPANY.COMPANY_VIS_ID and EXT_SYS_COMPANY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID and EXT_SYS_COMPANY.COMPANY_VIS_ID = EXTERNAL_SYSTEM.COMPANY_VIS_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  951 */       closeResultSet(resultSet);
/*  952 */       closeStatement(stmt);
/*  953 */       closeConnection();
/*      */ 
/*  955 */       if (timer != null)
/*  956 */         timer.logDebug("getRef", paramExtSysCalendarYearPK); 
/*  956 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/*  968 */     if (c == null)
/*  969 */       return;
/*  970 */     Iterator iter = c.iterator();
/*  971 */     while (iter.hasNext())
/*      */     {
/*  973 */       ExtSysCalendarYearEVO evo = (ExtSysCalendarYearEVO)iter.next();
/*  974 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(ExtSysCalendarYearEVO evo, String dependants)
/*      */   {
/*  988 */     if (evo.insertPending()) {
/*  989 */       return;
/*      */     }
/*      */ 
/*  993 */     if (dependants.indexOf("<10>") > -1)
/*      */     {
/*  996 */       if (!evo.isExtSysCalendarElementsAllItemsLoaded())
/*      */       {
/*  998 */         evo.setExtSysCalendarElements(getExtSysCalElementDAO().getAll(evo.getExternalSystemId(), evo.getCompanyVisId(), evo.getCalendarYearVisId(), dependants, evo.getExtSysCalendarElements()));
/*      */ 
/* 1007 */         evo.setExtSysCalendarElementsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void insert(ExtSysCalendarYearEVO evo)
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/* 1025 */     setDetails(evo);
/* 1026 */     doCreate();
/*      */   }
/*      */ 
/*      */   public int checkConstraintViolations(int externalSystemId, int maxToReport, PrintWriter log)
/*      */   {
/* 1038 */     SqlExecutor sqlExecutor = null;
/* 1039 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1044 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "select external_system_id, company_vis_id, calendar_year_vis_id, year", "from ext_sys_calendar_year", "where external_system_id = <externalSystemId>", "group by external_system_id, company_vis_id, calendar_year_vis_id, year", "having count(*) > 1" });
/*      */ 
/* 1052 */       sqlExecutor = new SqlExecutor("checkConstraintViolations", getDataSource(), sqlBuilder, this._log);
/*      */ 
/* 1054 */       sqlExecutor.addBindVariable("<externalSystemId>", Integer.valueOf(externalSystemId));
/*      */ 
/* 1056 */       rs = sqlExecutor.getResultSet();
/*      */ 
/* 1058 */       int count = 0;
/* 1059 */       while ((rs.next()) && (count < maxToReport))
/*      */       {
/* 1061 */         log.print("Found calendar year duplicate details: company:[" + rs.getString("COMPANY_VIS_ID") + "]" + " calendar vis id:[" + rs.getString("CALENDAR_YEAR_VIS_ID") + "]" + " year:[" + rs.getString("YEAR") + "]");
/*      */ 
/* 1065 */         count++;
/*      */       }
/*      */ 
/* 1068 */       int i = count;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1072 */       throw handleSQLException("checkConstraintViolations", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1076 */       closeResultSet(rs);
/* 1077 */       sqlExecutor.close(); } //throw localObject;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extsys.ExtSysCalendarYearDAO
 * JD-Core Version:    0.6.0
 */