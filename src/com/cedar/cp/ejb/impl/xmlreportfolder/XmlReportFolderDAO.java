/*      */ package com.cedar.cp.ejb.impl.xmlreportfolder;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.xmlreportfolder.XmlReportFolderRef;
/*      */ import com.cedar.cp.dto.xmlreportfolder.AllPublicXmlReportFoldersELO;
/*      */ import com.cedar.cp.dto.xmlreportfolder.AllXmlReportFoldersELO;
/*      */ import com.cedar.cp.dto.xmlreportfolder.AllXmlReportFoldersForUserELO;
/*      */ import com.cedar.cp.dto.xmlreportfolder.DecendentFoldersELO;
/*      */ import com.cedar.cp.dto.xmlreportfolder.ReportFolderWithIdELO;
/*      */ import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderCK;
/*      */ import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
/*      */ import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderRefImpl;
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
/*      */ 
/*      */ public class XmlReportFolderDAO extends AbstractDAO
/*      */ {
/*   32 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select XML_REPORT_FOLDER_ID from XML_REPORT_FOLDER where    XML_REPORT_FOLDER_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID,XML_REPORT_FOLDER.PARENT_FOLDER_ID,XML_REPORT_FOLDER.VIS_ID,XML_REPORT_FOLDER.USER_ID,XML_REPORT_FOLDER.VERSION_NUM,XML_REPORT_FOLDER.UPDATED_BY_USER_ID,XML_REPORT_FOLDER.UPDATED_TIME,XML_REPORT_FOLDER.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from XML_REPORT_FOLDER where    XML_REPORT_FOLDER_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into XML_REPORT_FOLDER ( XML_REPORT_FOLDER_ID,PARENT_FOLDER_ID,VIS_ID,USER_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update XML_REPORT_FOLDER_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from XML_REPORT_FOLDER_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_XMLREPORTNAME = "select count(*) from XML_REPORT_FOLDER where    USER_ID = ? AND VIS_ID = ? and not(    XML_REPORT_FOLDER_ID = ? )";
/*      */   protected static final String SQL_STORE = "update XML_REPORT_FOLDER set PARENT_FOLDER_ID = ?,VIS_ID = ?,USER_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    XML_REPORT_FOLDER_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from XML_REPORT_FOLDER where XML_REPORT_FOLDER_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from XML_REPORT_FOLDER where    XML_REPORT_FOLDER_ID = ? ";
/*  685 */   protected static String SQL_ALL_XML_REPORT_FOLDERS = "select 0       ,XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID      ,XML_REPORT_FOLDER.VIS_ID      ,XML_REPORT_FOLDER.USER_ID from XML_REPORT_FOLDER where 1=1  order by XML_REPORT_FOLDER.USER_ID, XML_REPORT_FOLDER.VIS_ID";
/*      */ 
/*  765 */   protected static String SQL_DECENDENT_FOLDERS = "select 0       ,XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID      ,XML_REPORT_FOLDER.VIS_ID      ,XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID from XML_REPORT_FOLDER where 1=1  and  1=1 START WITH XML_REPORT_FOLDER.PARENT_FOLDER_ID = ? CONNECT BY PRIOR XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID = XML_REPORT_FOLDER.PARENT_FOLDER_ID";
/*      */ 
/*  849 */   protected static String SQL_REPORT_FOLDER_WITH_ID = "select 0       ,XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID      ,XML_REPORT_FOLDER.VIS_ID      ,XML_REPORT_FOLDER.USER_ID from XML_REPORT_FOLDER where 1=1  and  XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID = ?";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from XML_REPORT_FOLDER where   XML_REPORT_FOLDER_ID = ?";
/* 1069 */   protected static String SQL_ALL_PUBLIC_XML_REPORT_FOLDERS = "select 0       ,XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID      ,XML_REPORT_FOLDER.VIS_ID      ,LEVEL      ,XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID from XML_REPORT_FOLDER where 1=1  and  XML_REPORT_FOLDER.USER_ID IS NULL OR XML_REPORT_FOLDER.USER_ID = 0 START WITH XML_REPORT_FOLDER.PARENT_FOLDER_ID = 0 CONNECT BY PRIOR XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID = XML_REPORT_FOLDER.PARENT_FOLDER_ID order siblings by XML_REPORT_FOLDER.VIS_ID";
/*      */ 
/* 1152 */   protected static String SQL_ALL_XML_REPORT_FOLDERS_FOR_USER = "select 0       ,XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID      ,XML_REPORT_FOLDER.VIS_ID      ,LEVEL      ,XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID from XML_REPORT_FOLDER where 1=1  and  XML_REPORT_FOLDER.USER_ID = ? START WITH XML_REPORT_FOLDER.PARENT_FOLDER_ID = 0 CONNECT BY PRIOR XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID = XML_REPORT_FOLDER.PARENT_FOLDER_ID order siblings by XML_REPORT_FOLDER.VIS_ID";
/*      */   protected XmlReportFolderEVO mDetails;
/*      */ 
/*      */   public XmlReportFolderDAO(Connection connection)
/*      */   {
/*   39 */     super(connection);
/*      */   }
/*      */ 
/*      */   public XmlReportFolderDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public XmlReportFolderDAO(DataSource ds)
/*      */   {
/*   55 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected XmlReportFolderPK getPK()
/*      */   {
/*   63 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(XmlReportFolderEVO details)
/*      */   {
/*   72 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public XmlReportFolderEVO setAndGetDetails(XmlReportFolderEVO details, String dependants)
/*      */   {
/*   83 */     setDetails(details);
/*   84 */     generateKeys();
/*   85 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public XmlReportFolderPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   94 */     doCreate();
/*      */ 
/*   96 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(XmlReportFolderPK pk)
/*      */     throws ValidationException
/*      */   {
/*  106 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  115 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  124 */     doRemove();
/*      */   }
/*      */ 
/*      */   public XmlReportFolderPK findByPrimaryKey(XmlReportFolderPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  133 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  134 */     if (exists(pk_))
/*      */     {
/*  136 */       if (timer != null) {
/*  137 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  139 */       return pk_;
/*      */     }
/*      */ 
/*  142 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(XmlReportFolderPK pk)
/*      */   {
/*  160 */     PreparedStatement stmt = null;
/*  161 */     ResultSet resultSet = null;
/*  162 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  166 */       stmt = getConnection().prepareStatement("select XML_REPORT_FOLDER_ID from XML_REPORT_FOLDER where    XML_REPORT_FOLDER_ID = ? ");
/*      */ 
/*  168 */       int col = 1;
/*  169 */       stmt.setInt(col++, pk.getXmlReportFolderId());
/*      */ 
/*  171 */       resultSet = stmt.executeQuery();
/*      */ 
/*  173 */       if (!resultSet.next())
/*  174 */         returnValue = false;
/*      */       else
/*  176 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  180 */       throw handleSQLException(pk, "select XML_REPORT_FOLDER_ID from XML_REPORT_FOLDER where    XML_REPORT_FOLDER_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  184 */       closeResultSet(resultSet);
/*  185 */       closeStatement(stmt);
/*  186 */       closeConnection();
/*      */     }
/*  188 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private XmlReportFolderEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  207 */     int col = 1;
/*  208 */     XmlReportFolderEVO evo = new XmlReportFolderEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*      */ 
/*  216 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  217 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  218 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  219 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(XmlReportFolderEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  224 */     int col = startCol_;
/*  225 */     stmt_.setInt(col++, evo_.getXmlReportFolderId());
/*  226 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(XmlReportFolderEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  231 */     int col = startCol_;
/*  232 */     stmt_.setInt(col++, evo_.getParentFolderId());
/*  233 */     stmt_.setString(col++, evo_.getVisId());
/*  234 */     stmt_.setInt(col++, evo_.getUserId());
/*  235 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  236 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  237 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  238 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  239 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(XmlReportFolderPK pk)
/*      */     throws ValidationException
/*      */   {
/*  255 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  257 */     PreparedStatement stmt = null;
/*  258 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  262 */       stmt = getConnection().prepareStatement("select XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID,XML_REPORT_FOLDER.PARENT_FOLDER_ID,XML_REPORT_FOLDER.VIS_ID,XML_REPORT_FOLDER.USER_ID,XML_REPORT_FOLDER.VERSION_NUM,XML_REPORT_FOLDER.UPDATED_BY_USER_ID,XML_REPORT_FOLDER.UPDATED_TIME,XML_REPORT_FOLDER.CREATED_TIME from XML_REPORT_FOLDER where    XML_REPORT_FOLDER_ID = ? ");
/*      */ 
/*  265 */       int col = 1;
/*  266 */       stmt.setInt(col++, pk.getXmlReportFolderId());
/*      */ 
/*  268 */       resultSet = stmt.executeQuery();
/*      */ 
/*  270 */       if (!resultSet.next()) {
/*  271 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  274 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  275 */       if (this.mDetails.isModified())
/*  276 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  280 */       throw handleSQLException(pk, "select XML_REPORT_FOLDER.XML_REPORT_FOLDER_ID,XML_REPORT_FOLDER.PARENT_FOLDER_ID,XML_REPORT_FOLDER.VIS_ID,XML_REPORT_FOLDER.USER_ID,XML_REPORT_FOLDER.VERSION_NUM,XML_REPORT_FOLDER.UPDATED_BY_USER_ID,XML_REPORT_FOLDER.UPDATED_TIME,XML_REPORT_FOLDER.CREATED_TIME from XML_REPORT_FOLDER where    XML_REPORT_FOLDER_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  284 */       closeResultSet(resultSet);
/*  285 */       closeStatement(stmt);
/*  286 */       closeConnection();
/*      */ 
/*  288 */       if (timer != null)
/*  289 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  322 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  323 */     generateKeys();
/*      */ 
/*  325 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  330 */       duplicateValueCheckXmlReportName();
/*      */ 
/*  332 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  333 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  334 */       stmt = getConnection().prepareStatement("insert into XML_REPORT_FOLDER ( XML_REPORT_FOLDER_ID,PARENT_FOLDER_ID,VIS_ID,USER_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");
/*      */ 
/*  337 */       int col = 1;
/*  338 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  339 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  342 */       int resultCount = stmt.executeUpdate();
/*  343 */       if (resultCount != 1)
/*      */       {
/*  345 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  348 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  352 */       throw handleSQLException(this.mDetails.getPK(), "insert into XML_REPORT_FOLDER ( XML_REPORT_FOLDER_ID,PARENT_FOLDER_ID,VIS_ID,USER_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  356 */       closeStatement(stmt);
/*  357 */       closeConnection();
/*      */ 
/*  359 */       if (timer != null)
/*  360 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  380 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  382 */     PreparedStatement stmt = null;
/*  383 */     ResultSet resultSet = null;
/*  384 */     String sqlString = null;
/*      */     try
/*      */     {
/*  389 */       sqlString = "update XML_REPORT_FOLDER_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  390 */       stmt = getConnection().prepareStatement("update XML_REPORT_FOLDER_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  391 */       stmt.setInt(1, insertCount);
/*      */ 
/*  393 */       int resultCount = stmt.executeUpdate();
/*  394 */       if (resultCount != 1) {
/*  395 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  397 */       closeStatement(stmt);
/*      */ 
/*  400 */       sqlString = "select SEQ_NUM from XML_REPORT_FOLDER_SEQ";
/*  401 */       stmt = getConnection().prepareStatement("select SEQ_NUM from XML_REPORT_FOLDER_SEQ");
/*  402 */       resultSet = stmt.executeQuery();
/*  403 */       if (!resultSet.next())
/*  404 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  405 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  407 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  411 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  415 */       closeResultSet(resultSet);
/*  416 */       closeStatement(stmt);
/*  417 */       closeConnection();
/*      */ 
/*  419 */       if (timer != null)
/*  420 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  420 */     }
/*      */   }
/*      */ 
/*      */   public XmlReportFolderPK generateKeys()
/*      */   {
/*  430 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  432 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  435 */     if (insertCount == 0) {
/*  436 */       return this.mDetails.getPK();
/*      */     }
/*  438 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  440 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckXmlReportName()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  454 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  455 */     PreparedStatement stmt = null;
/*  456 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  460 */       stmt = getConnection().prepareStatement("select count(*) from XML_REPORT_FOLDER where    USER_ID = ? AND VIS_ID = ? and not(    XML_REPORT_FOLDER_ID = ? )");
/*      */ 
/*  463 */       int col = 1;
/*  464 */       stmt.setInt(col++, this.mDetails.getUserId());
/*  465 */       stmt.setString(col++, this.mDetails.getVisId());
/*  466 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  469 */       resultSet = stmt.executeQuery();
/*      */ 
/*  471 */       if (!resultSet.next()) {
/*  472 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  476 */       col = 1;
/*  477 */       int count = resultSet.getInt(col++);
/*  478 */       if (count > 0) {
/*  479 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " XmlReportName");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  485 */       throw handleSQLException(getPK(), "select count(*) from XML_REPORT_FOLDER where    USER_ID = ? AND VIS_ID = ? and not(    XML_REPORT_FOLDER_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  489 */       closeResultSet(resultSet);
/*  490 */       closeStatement(stmt);
/*  491 */       closeConnection();
/*      */ 
/*  493 */       if (timer != null)
/*  494 */         timer.logDebug("duplicateValueCheckXmlReportName", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  520 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  522 */     generateKeys();
/*      */ 
/*  527 */     PreparedStatement stmt = null;
/*      */ 
/*  529 */     boolean mainChanged = this.mDetails.isModified();
/*  530 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  534 */       if (mainChanged)
/*  535 */         duplicateValueCheckXmlReportName();
/*  536 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  539 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  542 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  543 */         stmt = getConnection().prepareStatement("update XML_REPORT_FOLDER set PARENT_FOLDER_ID = ?,VIS_ID = ?,USER_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    XML_REPORT_FOLDER_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  546 */         int col = 1;
/*  547 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  548 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  550 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  553 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  555 */         if (resultCount == 0) {
/*  556 */           checkVersionNum();
/*      */         }
/*  558 */         if (resultCount != 1) {
/*  559 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  562 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  571 */       throw handleSQLException(getPK(), "update XML_REPORT_FOLDER set PARENT_FOLDER_ID = ?,VIS_ID = ?,USER_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    XML_REPORT_FOLDER_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  575 */       closeStatement(stmt);
/*  576 */       closeConnection();
/*      */ 
/*  578 */       if ((timer != null) && (
/*  579 */         (mainChanged) || (dependantChanged)))
/*  580 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  592 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  593 */     PreparedStatement stmt = null;
/*  594 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  598 */       stmt = getConnection().prepareStatement("select VERSION_NUM from XML_REPORT_FOLDER where XML_REPORT_FOLDER_ID = ?");
/*      */ 
/*  601 */       int col = 1;
/*  602 */       stmt.setInt(col++, this.mDetails.getXmlReportFolderId());
/*      */ 
/*  605 */       resultSet = stmt.executeQuery();
/*      */ 
/*  607 */       if (!resultSet.next()) {
/*  608 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  611 */       col = 1;
/*  612 */       int dbVersionNumber = resultSet.getInt(col++);
/*  613 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  614 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  620 */       throw handleSQLException(getPK(), "select VERSION_NUM from XML_REPORT_FOLDER where XML_REPORT_FOLDER_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  624 */       closeStatement(stmt);
/*  625 */       closeResultSet(resultSet);
/*      */ 
/*  627 */       if (timer != null)
/*  628 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  645 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  650 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  655 */       stmt = getConnection().prepareStatement("delete from XML_REPORT_FOLDER where    XML_REPORT_FOLDER_ID = ? ");
/*      */ 
/*  658 */       int col = 1;
/*  659 */       stmt.setInt(col++, this.mDetails.getXmlReportFolderId());
/*      */ 
/*  661 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  663 */       if (resultCount != 1) {
/*  664 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  670 */       throw handleSQLException(getPK(), "delete from XML_REPORT_FOLDER where    XML_REPORT_FOLDER_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  674 */       closeStatement(stmt);
/*  675 */       closeConnection();
/*      */ 
/*  677 */       if (timer != null)
/*  678 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllXmlReportFoldersELO getAllXmlReportFolders()
/*      */   {
/*  708 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  709 */     PreparedStatement stmt = null;
/*  710 */     ResultSet resultSet = null;
/*  711 */     AllXmlReportFoldersELO results = new AllXmlReportFoldersELO();
/*      */     try
/*      */     {
/*  714 */       stmt = getConnection().prepareStatement(SQL_ALL_XML_REPORT_FOLDERS);
/*  715 */       int col = 1;
/*  716 */       resultSet = stmt.executeQuery();
/*  717 */       while (resultSet.next())
/*      */       {
/*  719 */         col = 2;
/*      */ 
/*  722 */         XmlReportFolderPK pkXmlReportFolder = new XmlReportFolderPK(resultSet.getInt(col++));
/*      */ 
/*  725 */         String textXmlReportFolder = resultSet.getString(col++);
/*      */ 
/*  729 */         XmlReportFolderRefImpl erXmlReportFolder = new XmlReportFolderRefImpl(pkXmlReportFolder, textXmlReportFolder);
/*      */ 
/*  734 */         int col1 = resultSet.getInt(col++);
/*      */ 
/*  737 */         results.add(erXmlReportFolder, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  745 */       throw handleSQLException(SQL_ALL_XML_REPORT_FOLDERS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  749 */       closeResultSet(resultSet);
/*  750 */       closeStatement(stmt);
/*  751 */       closeConnection();
/*      */     }
/*      */ 
/*  754 */     if (timer != null) {
/*  755 */       timer.logDebug("getAllXmlReportFolders", " items=" + results.size());
/*      */     }
/*      */ 
/*  759 */     return results;
/*      */   }
/*      */ 
/*      */   public DecendentFoldersELO getDecendentFolders(int param1)
/*      */   {
/*  790 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  791 */     PreparedStatement stmt = null;
/*  792 */     ResultSet resultSet = null;
/*  793 */     DecendentFoldersELO results = new DecendentFoldersELO();
/*      */     try
/*      */     {
/*  796 */       stmt = getConnection().prepareStatement(SQL_DECENDENT_FOLDERS);
/*  797 */       int col = 1;
/*  798 */       stmt.setInt(col++, param1);
/*  799 */       resultSet = stmt.executeQuery();
/*  800 */       while (resultSet.next())
/*      */       {
/*  802 */         col = 2;
/*      */ 
/*  805 */         XmlReportFolderPK pkXmlReportFolder = new XmlReportFolderPK(resultSet.getInt(col++));
/*      */ 
/*  808 */         String textXmlReportFolder = resultSet.getString(col++);
/*      */ 
/*  812 */         XmlReportFolderRefImpl erXmlReportFolder = new XmlReportFolderRefImpl(pkXmlReportFolder, textXmlReportFolder);
/*      */ 
/*  817 */         int col1 = resultSet.getInt(col++);
/*      */ 
/*  820 */         results.add(erXmlReportFolder, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  828 */       throw handleSQLException(SQL_DECENDENT_FOLDERS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  832 */       closeResultSet(resultSet);
/*  833 */       closeStatement(stmt);
/*  834 */       closeConnection();
/*      */     }
/*      */ 
/*  837 */     if (timer != null) {
/*  838 */       timer.logDebug("getDecendentFolders", " ParentFolderId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  843 */     return results;
/*      */   }
/*      */ 
/*      */   public ReportFolderWithIdELO getReportFolderWithId(int param1)
/*      */   {
/*  874 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  875 */     PreparedStatement stmt = null;
/*  876 */     ResultSet resultSet = null;
/*  877 */     ReportFolderWithIdELO results = new ReportFolderWithIdELO();
/*      */     try
/*      */     {
/*  880 */       stmt = getConnection().prepareStatement(SQL_REPORT_FOLDER_WITH_ID);
/*  881 */       int col = 1;
/*  882 */       stmt.setInt(col++, param1);
/*  883 */       resultSet = stmt.executeQuery();
/*  884 */       while (resultSet.next())
/*      */       {
/*  886 */         col = 2;
/*      */ 
/*  889 */         XmlReportFolderPK pkXmlReportFolder = new XmlReportFolderPK(resultSet.getInt(col++));
/*      */ 
/*  892 */         String textXmlReportFolder = resultSet.getString(col++);
/*      */ 
/*  896 */         XmlReportFolderRefImpl erXmlReportFolder = new XmlReportFolderRefImpl(pkXmlReportFolder, textXmlReportFolder);
/*      */ 
/*  901 */         int col1 = resultSet.getInt(col++);
/*      */ 
/*  904 */         results.add(erXmlReportFolder, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  912 */       throw handleSQLException(SQL_REPORT_FOLDER_WITH_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  916 */       closeResultSet(resultSet);
/*  917 */       closeStatement(stmt);
/*  918 */       closeConnection();
/*      */     }
/*      */ 
/*  921 */     if (timer != null) {
/*  922 */       timer.logDebug("getReportFolderWithId", " XmlReportFolderId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  927 */     return results;
/*      */   }
/*      */ 
/*      */   public XmlReportFolderEVO getDetails(XmlReportFolderPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  945 */     return getDetails(new XmlReportFolderCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public XmlReportFolderEVO getDetails(XmlReportFolderCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  959 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  962 */     if (this.mDetails == null) {
/*  963 */       doLoad(paramCK.getXmlReportFolderPK());
/*      */     }
/*  965 */     else if (!this.mDetails.getPK().equals(paramCK.getXmlReportFolderPK())) {
/*  966 */       doLoad(paramCK.getXmlReportFolderPK());
/*      */     }
/*  968 */     else if (!checkIfValid())
/*      */     {
/*  970 */       this._log.info("getDetails", "[ALERT] XmlReportFolderEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/*  972 */       doLoad(paramCK.getXmlReportFolderPK());
/*      */     }
/*      */ 
/*  976 */     XmlReportFolderEVO details = new XmlReportFolderEVO();
/*  977 */     details = this.mDetails.deepClone();
/*      */ 
/*  979 */     if (timer != null) {
/*  980 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  982 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/*  992 */     boolean stillValid = false;
/*  993 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  994 */     PreparedStatement stmt = null;
/*  995 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  998 */       stmt = getConnection().prepareStatement("select VERSION_NUM from XML_REPORT_FOLDER where   XML_REPORT_FOLDER_ID = ?");
/*  999 */       int col = 1;
/* 1000 */       stmt.setInt(col++, this.mDetails.getXmlReportFolderId());
/*      */ 
/* 1002 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1004 */       if (!resultSet.next()) {
/* 1005 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1007 */       col = 1;
/* 1008 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1010 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1011 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1015 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from XML_REPORT_FOLDER where   XML_REPORT_FOLDER_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1019 */       closeResultSet(resultSet);
/* 1020 */       closeStatement(stmt);
/* 1021 */       closeConnection();
/*      */ 
/* 1023 */       if (timer != null) {
/* 1024 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1027 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public XmlReportFolderEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1033 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1035 */     if (!checkIfValid())
/*      */     {
/* 1037 */       this._log.info("getDetails", "XmlReportFolder " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1038 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1042 */     XmlReportFolderEVO details = this.mDetails.deepClone();
/*      */ 
/* 1044 */     if (timer != null) {
/* 1045 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1047 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1052 */     return "XmlReportFolder";
/*      */   }
/*      */ 
/*      */   public XmlReportFolderRef getRef(XmlReportFolderPK paramXmlReportFolderPK)
/*      */     throws ValidationException
/*      */   {
/* 1058 */     XmlReportFolderEVO evo = getDetails(paramXmlReportFolderPK, "");
/* 1059 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public AllPublicXmlReportFoldersELO getAllPublicXmlReportFolders()
/*      */   {
/* 1093 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1094 */     PreparedStatement stmt = null;
/* 1095 */     ResultSet resultSet = null;
/* 1096 */     AllPublicXmlReportFoldersELO results = new AllPublicXmlReportFoldersELO();
/*      */     try
/*      */     {
/* 1099 */       stmt = getConnection().prepareStatement(SQL_ALL_PUBLIC_XML_REPORT_FOLDERS);
/* 1100 */       int col = 1;
/* 1101 */       resultSet = stmt.executeQuery();
/* 1102 */       while (resultSet.next())
/*      */       {
/* 1104 */         col = 2;
/*      */ 
/* 1107 */         XmlReportFolderPK pkXmlReportFolder = new XmlReportFolderPK(resultSet.getInt(col++));
/*      */ 
/* 1110 */         String textXmlReportFolder = resultSet.getString(col++);
/*      */ 
/* 1114 */         XmlReportFolderRefImpl erXmlReportFolder = new XmlReportFolderRefImpl(pkXmlReportFolder, textXmlReportFolder);
/*      */ 
/* 1119 */         int level = resultSet.getInt(col++);
/* 1120 */         int col1 = resultSet.getInt(col++);
/*      */ 
/* 1123 */         results.add(erXmlReportFolder, level, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1132 */       throw handleSQLException(SQL_ALL_PUBLIC_XML_REPORT_FOLDERS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1136 */       closeResultSet(resultSet);
/* 1137 */       closeStatement(stmt);
/* 1138 */       closeConnection();
/*      */     }
/*      */ 
/* 1141 */     if (timer != null) {
/* 1142 */       timer.logDebug("getAllPublicXmlReportFolders", " items=" + results.size());
/*      */     }
/*      */ 
/* 1146 */     return results;
/*      */   }
/*      */ 
/*      */   public AllXmlReportFoldersForUserELO getAllXmlReportFoldersForUser(int param1)
/*      */   {
/* 1178 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1179 */     PreparedStatement stmt = null;
/* 1180 */     ResultSet resultSet = null;
/* 1181 */     AllXmlReportFoldersForUserELO results = new AllXmlReportFoldersForUserELO();
/*      */     try
/*      */     {
/* 1184 */       stmt = getConnection().prepareStatement(SQL_ALL_XML_REPORT_FOLDERS_FOR_USER);
/* 1185 */       int col = 1;
/* 1186 */       stmt.setInt(col++, param1);
/* 1187 */       resultSet = stmt.executeQuery();
/* 1188 */       while (resultSet.next())
/*      */       {
/* 1190 */         col = 2;
/*      */ 
/* 1193 */         XmlReportFolderPK pkXmlReportFolder = new XmlReportFolderPK(resultSet.getInt(col++));
/*      */ 
/* 1196 */         String textXmlReportFolder = resultSet.getString(col++);
/*      */ 
/* 1200 */         XmlReportFolderRefImpl erXmlReportFolder = new XmlReportFolderRefImpl(pkXmlReportFolder, textXmlReportFolder);
/*      */ 
/* 1205 */         int level = resultSet.getInt(col++);
/* 1206 */         int col1 = resultSet.getInt(col++);
/*      */ 
/* 1209 */         results.add(erXmlReportFolder, level, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1218 */       throw handleSQLException(SQL_ALL_XML_REPORT_FOLDERS_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1222 */       closeResultSet(resultSet);
/* 1223 */       closeStatement(stmt);
/* 1224 */       closeConnection();
/*      */     }
/*      */ 
/* 1227 */     if (timer != null) {
/* 1228 */       timer.logDebug("getAllXmlReportFoldersForUser", " UserId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1233 */     return results;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.xmlreportfolder.XmlReportFolderDAO
 * JD-Core Version:    0.6.0
 */