/*      */ package com.cedar.cp.ejb.impl.xmlreport;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.xmlreport.XmlReportRef;
/*      */ import com.cedar.cp.dto.user.UserPK;
/*      */ import com.cedar.cp.dto.user.UserRefImpl;
/*      */ import com.cedar.cp.dto.xmlreport.AllPublicXmlReportsELO;
/*      */ import com.cedar.cp.dto.xmlreport.AllXmlReportsELO;
/*      */ import com.cedar.cp.dto.xmlreport.AllXmlReportsForUserELO;
/*      */ import com.cedar.cp.dto.xmlreport.SingleXmlReportELO;
/*      */ import com.cedar.cp.dto.xmlreport.XmlReportCK;
/*      */ import com.cedar.cp.dto.xmlreport.XmlReportPK;
/*      */ import com.cedar.cp.dto.xmlreport.XmlReportRefImpl;
/*      */ import com.cedar.cp.dto.xmlreport.XmlReportsForFolderELO;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Date;
/*      */ import javax.sql.DataSource;
/*      */ import oracle.sql.CLOB;
/*      */ 
/*      */ public class XmlReportDAO extends AbstractDAO
/*      */ {
/*   37 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select XML_REPORT_ID from XML_REPORT where    XML_REPORT_ID = ? ";
/*      */   private static final String SQL_SELECT_LOBS = "select  DEFINITION from XML_REPORT where    XML_REPORT_ID = ? for update";
/*      */   private static final String SQL_SELECT_COLUMNS = "select XML_REPORT.DEFINITION,XML_REPORT.XML_REPORT_ID,XML_REPORT.XML_REPORT_FOLDER_ID,XML_REPORT.VIS_ID,XML_REPORT.USER_ID,XML_REPORT.VERSION_NUM,XML_REPORT.UPDATED_BY_USER_ID,XML_REPORT.UPDATED_TIME,XML_REPORT.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from XML_REPORT where    XML_REPORT_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into XML_REPORT ( XML_REPORT_ID,XML_REPORT_FOLDER_ID,VIS_ID,USER_ID,DEFINITION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,empty_clob(),?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update XML_REPORT_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from XML_REPORT_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_XMLREPORTNAME = "select count(*) from XML_REPORT where    USER_ID = ? AND VIS_ID = ? and not(    XML_REPORT_ID = ? )";
/*      */   protected static final String SQL_STORE = "update XML_REPORT set XML_REPORT_FOLDER_ID = ?,VIS_ID = ?,USER_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    XML_REPORT_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from XML_REPORT where XML_REPORT_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from XML_REPORT where    XML_REPORT_ID = ? ";
/*  752 */   protected static String SQL_ALL_XML_REPORTS = "select 0       ,XML_REPORT.XML_REPORT_ID      ,XML_REPORT.VIS_ID      ,USR.USER_ID      ,USR.NAME      ,XML_REPORT.USER_ID      ,USR.NAME from XML_REPORT    ,USR where 1=1  and  XML_REPORT.USER_ID = USR.USER_ID (+) order by XML_REPORT.VIS_ID, USR.NAME";
/*      */ 
/*  851 */   protected static String SQL_ALL_PUBLIC_XML_REPORTS = "select 0       ,XML_REPORT.XML_REPORT_ID      ,XML_REPORT.VIS_ID      ,XML_REPORT.XML_REPORT_FOLDER_ID from XML_REPORT where 1=1  and  XML_REPORT.USER_ID IS NULL OR XML_REPORT.USER_ID = 0 order by XML_REPORT.XML_REPORT_FOLDER_ID, XML_REPORT.VIS_ID";
/*      */ 
/*  931 */   protected static String SQL_ALL_XML_REPORTS_FOR_USER = "select 0       ,XML_REPORT.XML_REPORT_ID      ,XML_REPORT.VIS_ID      ,XML_REPORT.XML_REPORT_FOLDER_ID from XML_REPORT where 1=1  and  XML_REPORT.USER_ID = ? order by XML_REPORT.XML_REPORT_FOLDER_ID, XML_REPORT.VIS_ID";
/*      */ 
/* 1015 */   protected static String SQL_XML_REPORTS_FOR_FOLDER = "select 0       ,XML_REPORT.XML_REPORT_ID      ,XML_REPORT.VIS_ID from XML_REPORT where 1=1  and  XML_REPORT.XML_REPORT_FOLDER_ID = ?";
/*      */ 
/* 1096 */   protected static String SQL_SINGLE_XML_REPORT = "select 0       ,XML_REPORT.XML_REPORT_ID      ,XML_REPORT.VIS_ID      ,XML_REPORT.USER_ID from XML_REPORT where 1=1  and  XML_REPORT.USER_ID = ? AND XML_REPORT.VIS_ID = ? order by XML_REPORT.VIS_ID";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from XML_REPORT where   XML_REPORT_ID = ?";
/*      */   protected XmlReportEVO mDetails;
/*      */   private CLOB mDefinitionClob;
/*      */ 
/*      */   public XmlReportDAO(Connection connection)
/*      */   {
/*   44 */     super(connection);
/*      */   }
/*      */ 
/*      */   public XmlReportDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public XmlReportDAO(DataSource ds)
/*      */   {
/*   60 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected XmlReportPK getPK()
/*      */   {
/*   68 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(XmlReportEVO details)
/*      */   {
/*   77 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public XmlReportEVO setAndGetDetails(XmlReportEVO details, String dependants)
/*      */   {
/*   88 */     setDetails(details);
/*   89 */     generateKeys();
/*   90 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public XmlReportPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   99 */     doCreate();
/*      */ 
/*  101 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(XmlReportPK pk)
/*      */     throws ValidationException
/*      */   {
/*  111 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  120 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  129 */     doRemove();
/*      */   }
/*      */ 
/*      */   public XmlReportPK findByPrimaryKey(XmlReportPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  138 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  139 */     if (exists(pk_))
/*      */     {
/*  141 */       if (timer != null) {
/*  142 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  144 */       return pk_;
/*      */     }
/*      */ 
/*  147 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(XmlReportPK pk)
/*      */   {
/*  165 */     PreparedStatement stmt = null;
/*  166 */     ResultSet resultSet = null;
/*  167 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  171 */       stmt = getConnection().prepareStatement("select XML_REPORT_ID from XML_REPORT where    XML_REPORT_ID = ? ");
/*      */ 
/*  173 */       int col = 1;
/*  174 */       stmt.setInt(col++, pk.getXmlReportId());
/*      */ 
/*  176 */       resultSet = stmt.executeQuery();
/*      */ 
/*  178 */       if (!resultSet.next())
/*  179 */         returnValue = false;
/*      */       else
/*  181 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  185 */       throw handleSQLException(pk, "select XML_REPORT_ID from XML_REPORT where    XML_REPORT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  189 */       closeResultSet(resultSet);
/*  190 */       closeStatement(stmt);
/*  191 */       closeConnection();
/*      */     }
/*  193 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private void selectLobs(XmlReportEVO evo_)
/*      */   {
/*  207 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  209 */     PreparedStatement stmt = null;
/*  210 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  214 */       stmt = getConnection().prepareStatement("select  DEFINITION from XML_REPORT where    XML_REPORT_ID = ? for update");
/*      */ 
/*  216 */       putEvoKeysToJdbc(evo_, stmt, 1);
/*      */ 
/*  218 */       resultSet = stmt.executeQuery();
/*      */ 
/*  220 */       int col = 1;
/*  221 */       while (resultSet.next())
/*      */       {
/*  223 */         this.mDefinitionClob = ((CLOB)resultSet.getClob(col++));
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  228 */       throw handleSQLException(evo_.getPK(), "select  DEFINITION from XML_REPORT where    XML_REPORT_ID = ? for update", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  232 */       closeResultSet(resultSet);
/*  233 */       closeStatement(stmt);
/*      */ 
/*  235 */       if (timer != null)
/*  236 */         timer.logDebug("selectLobs", evo_.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void putLobs(XmlReportEVO evo_) throws SQLException
/*      */   {
/*  242 */     updateClob(this.mDefinitionClob, evo_.getDefinition());
/*      */   }
/*      */ 
/*      */   private XmlReportEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  260 */     int col = 1;
/*  261 */     this.mDefinitionClob = ((CLOB)resultSet_.getClob(col++));
/*  262 */     XmlReportEVO evo = new XmlReportEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getInt(col++), clobToString(this.mDefinitionClob), resultSet_.getInt(col++));
/*      */ 
/*  271 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  272 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  273 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  274 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(XmlReportEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  279 */     int col = startCol_;
/*  280 */     stmt_.setInt(col++, evo_.getXmlReportId());
/*  281 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(XmlReportEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  286 */     int col = startCol_;
/*  287 */     stmt_.setInt(col++, evo_.getXmlReportFolderId());
/*  288 */     stmt_.setString(col++, evo_.getVisId());
/*  289 */     stmt_.setInt(col++, evo_.getUserId());
/*      */ 
/*  291 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  292 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  293 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  294 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  295 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(XmlReportPK pk)
/*      */     throws ValidationException
/*      */   {
/*  311 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  313 */     PreparedStatement stmt = null;
/*  314 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  318 */       stmt = getConnection().prepareStatement("select XML_REPORT.DEFINITION,XML_REPORT.XML_REPORT_ID,XML_REPORT.XML_REPORT_FOLDER_ID,XML_REPORT.VIS_ID,XML_REPORT.USER_ID,XML_REPORT.VERSION_NUM,XML_REPORT.UPDATED_BY_USER_ID,XML_REPORT.UPDATED_TIME,XML_REPORT.CREATED_TIME from XML_REPORT where    XML_REPORT_ID = ? ");
/*      */ 
/*  321 */       int col = 1;
/*  322 */       stmt.setInt(col++, pk.getXmlReportId());
/*      */ 
/*  324 */       resultSet = stmt.executeQuery();
/*      */ 
/*  326 */       if (!resultSet.next()) {
/*  327 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  330 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  331 */       if (this.mDetails.isModified())
/*  332 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  336 */       throw handleSQLException(pk, "select XML_REPORT.DEFINITION,XML_REPORT.XML_REPORT_ID,XML_REPORT.XML_REPORT_FOLDER_ID,XML_REPORT.VIS_ID,XML_REPORT.USER_ID,XML_REPORT.VERSION_NUM,XML_REPORT.UPDATED_BY_USER_ID,XML_REPORT.UPDATED_TIME,XML_REPORT.CREATED_TIME from XML_REPORT where    XML_REPORT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  340 */       closeResultSet(resultSet);
/*  341 */       closeStatement(stmt);
/*  342 */       closeConnection();
/*      */ 
/*  344 */       if (timer != null)
/*  345 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  380 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  381 */     generateKeys();
/*      */ 
/*  383 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  388 */       duplicateValueCheckXmlReportName();
/*      */ 
/*  390 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  391 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  392 */       stmt = getConnection().prepareStatement("insert into XML_REPORT ( XML_REPORT_ID,XML_REPORT_FOLDER_ID,VIS_ID,USER_ID,DEFINITION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,empty_clob(),?,?,?,?)");
/*      */ 
/*  395 */       int col = 1;
/*  396 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  397 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  400 */       int resultCount = stmt.executeUpdate();
/*  401 */       if (resultCount != 1)
/*      */       {
/*  403 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  408 */       selectLobs(this.mDetails);
/*  409 */       this._log.debug("doCreate", "calling putLobs");
/*  410 */       putLobs(this.mDetails);
/*      */ 
/*  412 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  416 */       throw handleSQLException(this.mDetails.getPK(), "insert into XML_REPORT ( XML_REPORT_ID,XML_REPORT_FOLDER_ID,VIS_ID,USER_ID,DEFINITION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,empty_clob(),?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  420 */       closeStatement(stmt);
/*  421 */       closeConnection();
/*      */ 
/*  423 */       if (timer != null)
/*  424 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  444 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  446 */     PreparedStatement stmt = null;
/*  447 */     ResultSet resultSet = null;
/*  448 */     String sqlString = null;
/*      */     try
/*      */     {
/*  453 */       sqlString = "update XML_REPORT_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  454 */       stmt = getConnection().prepareStatement("update XML_REPORT_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  455 */       stmt.setInt(1, insertCount);
/*      */ 
/*  457 */       int resultCount = stmt.executeUpdate();
/*  458 */       if (resultCount != 1) {
/*  459 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  461 */       closeStatement(stmt);
/*      */ 
/*  464 */       sqlString = "select SEQ_NUM from XML_REPORT_SEQ";
/*  465 */       stmt = getConnection().prepareStatement("select SEQ_NUM from XML_REPORT_SEQ");
/*  466 */       resultSet = stmt.executeQuery();
/*  467 */       if (!resultSet.next())
/*  468 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  469 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  471 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  475 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  479 */       closeResultSet(resultSet);
/*  480 */       closeStatement(stmt);
/*  481 */       closeConnection();
/*      */ 
/*  483 */       if (timer != null)
/*  484 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  484 */     }
/*      */   }
/*      */ 
/*      */   public XmlReportPK generateKeys()
/*      */   {
/*  494 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  496 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  499 */     if (insertCount == 0) {
/*  500 */       return this.mDetails.getPK();
/*      */     }
/*  502 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  504 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckXmlReportName()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  518 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  519 */     PreparedStatement stmt = null;
/*  520 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  524 */       stmt = getConnection().prepareStatement("select count(*) from XML_REPORT where    USER_ID = ? AND VIS_ID = ? and not(    XML_REPORT_ID = ? )");
/*      */ 
/*  527 */       int col = 1;
/*  528 */       stmt.setInt(col++, this.mDetails.getUserId());
/*  529 */       stmt.setString(col++, this.mDetails.getVisId());
/*  530 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  533 */       resultSet = stmt.executeQuery();
/*      */ 
/*  535 */       if (!resultSet.next()) {
/*  536 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  540 */       col = 1;
/*  541 */       int count = resultSet.getInt(col++);
/*  542 */       if (count > 0) {
/*  543 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " XmlReportName");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  549 */       throw handleSQLException(getPK(), "select count(*) from XML_REPORT where    USER_ID = ? AND VIS_ID = ? and not(    XML_REPORT_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  553 */       closeResultSet(resultSet);
/*  554 */       closeStatement(stmt);
/*  555 */       closeConnection();
/*      */ 
/*  557 */       if (timer != null)
/*  558 */         timer.logDebug("duplicateValueCheckXmlReportName", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  584 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  586 */     generateKeys();
/*      */ 
/*  591 */     PreparedStatement stmt = null;
/*      */ 
/*  593 */     boolean mainChanged = this.mDetails.isModified();
/*  594 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  598 */       if (mainChanged)
/*  599 */         duplicateValueCheckXmlReportName();
/*  600 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  603 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  606 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  607 */         stmt = getConnection().prepareStatement("update XML_REPORT set XML_REPORT_FOLDER_ID = ?,VIS_ID = ?,USER_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    XML_REPORT_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  609 */         selectLobs(this.mDetails);
/*  610 */         putLobs(this.mDetails);
/*      */ 
/*  613 */         int col = 1;
/*  614 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  615 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  617 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  620 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  622 */         if (resultCount == 0) {
/*  623 */           checkVersionNum();
/*      */         }
/*  625 */         if (resultCount != 1) {
/*  626 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  629 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  638 */       throw handleSQLException(getPK(), "update XML_REPORT set XML_REPORT_FOLDER_ID = ?,VIS_ID = ?,USER_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    XML_REPORT_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  642 */       closeStatement(stmt);
/*  643 */       closeConnection();
/*      */ 
/*  645 */       if ((timer != null) && (
/*  646 */         (mainChanged) || (dependantChanged)))
/*  647 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  659 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  660 */     PreparedStatement stmt = null;
/*  661 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  665 */       stmt = getConnection().prepareStatement("select VERSION_NUM from XML_REPORT where XML_REPORT_ID = ?");
/*      */ 
/*  668 */       int col = 1;
/*  669 */       stmt.setInt(col++, this.mDetails.getXmlReportId());
/*      */ 
/*  672 */       resultSet = stmt.executeQuery();
/*      */ 
/*  674 */       if (!resultSet.next()) {
/*  675 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  678 */       col = 1;
/*  679 */       int dbVersionNumber = resultSet.getInt(col++);
/*  680 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  681 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  687 */       throw handleSQLException(getPK(), "select VERSION_NUM from XML_REPORT where XML_REPORT_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  691 */       closeStatement(stmt);
/*  692 */       closeResultSet(resultSet);
/*      */ 
/*  694 */       if (timer != null)
/*  695 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  712 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  717 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  722 */       stmt = getConnection().prepareStatement("delete from XML_REPORT where    XML_REPORT_ID = ? ");
/*      */ 
/*  725 */       int col = 1;
/*  726 */       stmt.setInt(col++, this.mDetails.getXmlReportId());
/*      */ 
/*  728 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  730 */       if (resultCount != 1) {
/*  731 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  737 */       throw handleSQLException(getPK(), "delete from XML_REPORT where    XML_REPORT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  741 */       closeStatement(stmt);
/*  742 */       closeConnection();
/*      */ 
/*  744 */       if (timer != null)
/*  745 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllXmlReportsELO getAllXmlReports()
/*      */   {
/*  780 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  781 */     PreparedStatement stmt = null;
/*  782 */     ResultSet resultSet = null;
/*  783 */     AllXmlReportsELO results = new AllXmlReportsELO();
/*      */     try
/*      */     {
/*  786 */       stmt = getConnection().prepareStatement(SQL_ALL_XML_REPORTS);
/*  787 */       int col = 1;
/*  788 */       resultSet = stmt.executeQuery();
/*  789 */       while (resultSet.next())
/*      */       {
/*  791 */         col = 2;
/*      */ 
/*  794 */         XmlReportPK pkXmlReport = new XmlReportPK(resultSet.getInt(col++));
/*      */ 
/*  797 */         String textXmlReport = resultSet.getString(col++);
/*      */ 
/*  800 */         UserPK pkUser = new UserPK(resultSet.getInt(col++));
/*      */ 
/*  803 */         String textUser = resultSet.getString(col++);
/*      */ 
/*  806 */         XmlReportRefImpl erXmlReport = new XmlReportRefImpl(pkXmlReport, textXmlReport);
/*      */ 
/*  812 */         UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
/*      */ 
/*  817 */         int col1 = resultSet.getInt(col++);
/*  818 */         String col2 = resultSet.getString(col++);
/*      */ 
/*  821 */         results.add(erXmlReport, erUser, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  831 */       throw handleSQLException(SQL_ALL_XML_REPORTS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  835 */       closeResultSet(resultSet);
/*  836 */       closeStatement(stmt);
/*  837 */       closeConnection();
/*      */     }
/*      */ 
/*  840 */     if (timer != null) {
/*  841 */       timer.logDebug("getAllXmlReports", " items=" + results.size());
/*      */     }
/*      */ 
/*  845 */     return results;
/*      */   }
/*      */ 
/*      */   public AllPublicXmlReportsELO getAllPublicXmlReports()
/*      */   {
/*  874 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  875 */     PreparedStatement stmt = null;
/*  876 */     ResultSet resultSet = null;
/*  877 */     AllPublicXmlReportsELO results = new AllPublicXmlReportsELO();
/*      */     try
/*      */     {
/*  880 */       stmt = getConnection().prepareStatement(SQL_ALL_PUBLIC_XML_REPORTS);
/*  881 */       int col = 1;
/*  882 */       resultSet = stmt.executeQuery();
/*  883 */       while (resultSet.next())
/*      */       {
/*  885 */         col = 2;
/*      */ 
/*  888 */         XmlReportPK pkXmlReport = new XmlReportPK(resultSet.getInt(col++));
/*      */ 
/*  891 */         String textXmlReport = resultSet.getString(col++);
/*      */ 
/*  895 */         XmlReportRefImpl erXmlReport = new XmlReportRefImpl(pkXmlReport, textXmlReport);
/*      */ 
/*  900 */         int col1 = resultSet.getInt(col++);
/*      */ 
/*  903 */         results.add(erXmlReport, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  911 */       throw handleSQLException(SQL_ALL_PUBLIC_XML_REPORTS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  915 */       closeResultSet(resultSet);
/*  916 */       closeStatement(stmt);
/*  917 */       closeConnection();
/*      */     }
/*      */ 
/*  920 */     if (timer != null) {
/*  921 */       timer.logDebug("getAllPublicXmlReports", " items=" + results.size());
/*      */     }
/*      */ 
/*  925 */     return results;
/*      */   }
/*      */ 
/*      */   public AllXmlReportsForUserELO getAllXmlReportsForUser(int param1)
/*      */   {
/*  956 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  957 */     PreparedStatement stmt = null;
/*  958 */     ResultSet resultSet = null;
/*  959 */     AllXmlReportsForUserELO results = new AllXmlReportsForUserELO();
/*      */     try
/*      */     {
/*  962 */       stmt = getConnection().prepareStatement(SQL_ALL_XML_REPORTS_FOR_USER);
/*  963 */       int col = 1;
/*  964 */       stmt.setInt(col++, param1);
/*  965 */       resultSet = stmt.executeQuery();
/*  966 */       while (resultSet.next())
/*      */       {
/*  968 */         col = 2;
/*      */ 
/*  971 */         XmlReportPK pkXmlReport = new XmlReportPK(resultSet.getInt(col++));
/*      */ 
/*  974 */         String textXmlReport = resultSet.getString(col++);
/*      */ 
/*  978 */         XmlReportRefImpl erXmlReport = new XmlReportRefImpl(pkXmlReport, textXmlReport);
/*      */ 
/*  983 */         int col1 = resultSet.getInt(col++);
/*      */ 
/*  986 */         results.add(erXmlReport, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  994 */       throw handleSQLException(SQL_ALL_XML_REPORTS_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  998 */       closeResultSet(resultSet);
/*  999 */       closeStatement(stmt);
/* 1000 */       closeConnection();
/*      */     }
/*      */ 
/* 1003 */     if (timer != null) {
/* 1004 */       timer.logDebug("getAllXmlReportsForUser", " UserId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1009 */     return results;
/*      */   }
/*      */ 
/*      */   public XmlReportsForFolderELO getXmlReportsForFolder(int param1)
/*      */   {
/* 1039 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1040 */     PreparedStatement stmt = null;
/* 1041 */     ResultSet resultSet = null;
/* 1042 */     XmlReportsForFolderELO results = new XmlReportsForFolderELO();
/*      */     try
/*      */     {
/* 1045 */       stmt = getConnection().prepareStatement(SQL_XML_REPORTS_FOR_FOLDER);
/* 1046 */       int col = 1;
/* 1047 */       stmt.setInt(col++, param1);
/* 1048 */       resultSet = stmt.executeQuery();
/* 1049 */       while (resultSet.next())
/*      */       {
/* 1051 */         col = 2;
/*      */ 
/* 1054 */         XmlReportPK pkXmlReport = new XmlReportPK(resultSet.getInt(col++));
/*      */ 
/* 1057 */         String textXmlReport = resultSet.getString(col++);
/*      */ 
/* 1061 */         XmlReportRefImpl erXmlReport = new XmlReportRefImpl(pkXmlReport, textXmlReport);
/*      */ 
/* 1068 */         results.add(erXmlReport);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1075 */       throw handleSQLException(SQL_XML_REPORTS_FOR_FOLDER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1079 */       closeResultSet(resultSet);
/* 1080 */       closeStatement(stmt);
/* 1081 */       closeConnection();
/*      */     }
/*      */ 
/* 1084 */     if (timer != null) {
/* 1085 */       timer.logDebug("getXmlReportsForFolder", " XmlReportFolderId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1090 */     return results;
/*      */   }
/*      */ 
/*      */   public SingleXmlReportELO getSingleXmlReport(int param1, String param2)
/*      */   {
/* 1123 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1124 */     PreparedStatement stmt = null;
/* 1125 */     ResultSet resultSet = null;
/* 1126 */     SingleXmlReportELO results = new SingleXmlReportELO();
/*      */     try
/*      */     {
/* 1129 */       stmt = getConnection().prepareStatement(SQL_SINGLE_XML_REPORT);
/* 1130 */       int col = 1;
/* 1131 */       stmt.setInt(col++, param1);
/* 1132 */       stmt.setString(col++, param2);
/* 1133 */       resultSet = stmt.executeQuery();
/* 1134 */       while (resultSet.next())
/*      */       {
/* 1136 */         col = 2;
/*      */ 
/* 1139 */         XmlReportPK pkXmlReport = new XmlReportPK(resultSet.getInt(col++));
/*      */ 
/* 1142 */         String textXmlReport = resultSet.getString(col++);
/*      */ 
/* 1146 */         XmlReportRefImpl erXmlReport = new XmlReportRefImpl(pkXmlReport, textXmlReport);
/*      */ 
/* 1151 */         int col1 = resultSet.getInt(col++);
/*      */ 
/* 1154 */         results.add(erXmlReport, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1162 */       throw handleSQLException(SQL_SINGLE_XML_REPORT, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1166 */       closeResultSet(resultSet);
/* 1167 */       closeStatement(stmt);
/* 1168 */       closeConnection();
/*      */     }
/*      */ 
/* 1171 */     if (timer != null) {
/* 1172 */       timer.logDebug("getSingleXmlReport", " UserId=" + param1 + ",VisId=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1178 */     return results;
/*      */   }
/*      */ 
/*      */   public XmlReportEVO getDetails(XmlReportPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1196 */     return getDetails(new XmlReportCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public XmlReportEVO getDetails(XmlReportCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1210 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1213 */     if (this.mDetails == null) {
/* 1214 */       doLoad(paramCK.getXmlReportPK());
/*      */     }
/* 1216 */     else if (!this.mDetails.getPK().equals(paramCK.getXmlReportPK())) {
/* 1217 */       doLoad(paramCK.getXmlReportPK());
/*      */     }
/* 1219 */     else if (!checkIfValid())
/*      */     {
/* 1221 */       this._log.info("getDetails", "[ALERT] XmlReportEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1223 */       doLoad(paramCK.getXmlReportPK());
/*      */     }
/*      */ 
/* 1227 */     XmlReportEVO details = new XmlReportEVO();
/* 1228 */     details = this.mDetails.deepClone();
/*      */ 
/* 1230 */     if (timer != null) {
/* 1231 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1233 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1243 */     boolean stillValid = false;
/* 1244 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1245 */     PreparedStatement stmt = null;
/* 1246 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1249 */       stmt = getConnection().prepareStatement("select VERSION_NUM from XML_REPORT where   XML_REPORT_ID = ?");
/* 1250 */       int col = 1;
/* 1251 */       stmt.setInt(col++, this.mDetails.getXmlReportId());
/*      */ 
/* 1253 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1255 */       if (!resultSet.next()) {
/* 1256 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1258 */       col = 1;
/* 1259 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1261 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1262 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1266 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from XML_REPORT where   XML_REPORT_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1270 */       closeResultSet(resultSet);
/* 1271 */       closeStatement(stmt);
/* 1272 */       closeConnection();
/*      */ 
/* 1274 */       if (timer != null) {
/* 1275 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1278 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public XmlReportEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1284 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1286 */     if (!checkIfValid())
/*      */     {
/* 1288 */       this._log.info("getDetails", "XmlReport " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1289 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1293 */     XmlReportEVO details = this.mDetails.deepClone();
/*      */ 
/* 1295 */     if (timer != null) {
/* 1296 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1298 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1303 */     return "XmlReport";
/*      */   }
/*      */ 
/*      */   public XmlReportRef getRef(XmlReportPK paramXmlReportPK)
/*      */     throws ValidationException
/*      */   {
/* 1309 */     XmlReportEVO evo = getDetails(paramXmlReportPK, "");
/* 1310 */     return evo.getEntityRef();
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.xmlreport.XmlReportDAO
 * JD-Core Version:    0.6.0
 */