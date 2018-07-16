/*     */ package com.cedar.cp.ejb.impl.report.mappingtemplate;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.api.report.mappingtemplate.ReportMappingTemplateRef;
/*     */ import com.cedar.cp.dto.report.mappingtemplate.AllReportMappingTemplatesELO;
/*     */ import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateCK;
/*     */ import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
/*     */ import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Date;
/*     */ import javax.sql.DataSource;
/*     */ import oracle.sql.BLOB;
/*     */ 
/*     */ public class ReportMappingTemplateDAO extends AbstractDAO
/*     */ {
/*  34 */   Log _log = new Log(getClass());
/*     */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select REPORT_MAPPING_TEMPLATE_ID from REPORT_MAPPING_TEMPLATE where    REPORT_MAPPING_TEMPLATE_ID = ? ";
/*     */   private static final String SQL_SELECT_LOBS = "select  DOCUMENT from REPORT_MAPPING_TEMPLATE where    REPORT_MAPPING_TEMPLATE_ID = ? for update";
/*     */   private static final String SQL_SELECT_COLUMNS = "select REPORT_MAPPING_TEMPLATE.DOCUMENT,REPORT_MAPPING_TEMPLATE.REPORT_MAPPING_TEMPLATE_ID,REPORT_MAPPING_TEMPLATE.VIS_ID,REPORT_MAPPING_TEMPLATE.DESCRIPTION,REPORT_MAPPING_TEMPLATE.DOCUMENT_NAME,REPORT_MAPPING_TEMPLATE.VERSION_NUM,REPORT_MAPPING_TEMPLATE.UPDATED_BY_USER_ID,REPORT_MAPPING_TEMPLATE.UPDATED_TIME,REPORT_MAPPING_TEMPLATE.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from REPORT_MAPPING_TEMPLATE where    REPORT_MAPPING_TEMPLATE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into REPORT_MAPPING_TEMPLATE ( REPORT_MAPPING_TEMPLATE_ID,VIS_ID,DESCRIPTION,DOCUMENT_NAME,DOCUMENT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,empty_blob(),?,?,?,?)";
/*     */   protected static final String SQL_UPDATE_SEQ_NUM = "update REPORT_MAPPING_TEMPLATE_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*     */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from REPORT_MAPPING_TEMPLATE_SEQ";
/*     */   protected static final String SQL_DUPLICATE_VALUE_CHECK_IDENTIFIER = "select count(*) from REPORT_MAPPING_TEMPLATE where    VIS_ID = ? and not(    REPORT_MAPPING_TEMPLATE_ID = ? )";
/*     */   protected static final String SQL_STORE = "update REPORT_MAPPING_TEMPLATE set VIS_ID = ?,DESCRIPTION = ?,DOCUMENT_NAME = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_MAPPING_TEMPLATE_ID = ? AND VERSION_NUM = ?";
/*     */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from REPORT_MAPPING_TEMPLATE where REPORT_MAPPING_TEMPLATE_ID = ?";
/*     */   protected static final String SQL_REMOVE = "delete from REPORT_MAPPING_TEMPLATE where    REPORT_MAPPING_TEMPLATE_ID = ? ";
/* 764 */   protected static String SQL_ALL_REPORT_MAPPING_TEMPLATES = "select 0       ,REPORT_MAPPING_TEMPLATE.REPORT_MAPPING_TEMPLATE_ID      ,REPORT_MAPPING_TEMPLATE.VIS_ID      ,REPORT_MAPPING_TEMPLATE.REPORT_MAPPING_TEMPLATE_ID      ,REPORT_MAPPING_TEMPLATE.DESCRIPTION from REPORT_MAPPING_TEMPLATE where 1=1 ";
/*     */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from REPORT_MAPPING_TEMPLATE where   REPORT_MAPPING_TEMPLATE_ID = ?";
/*     */   protected ReportMappingTemplateEVO mDetails;
/*     */   private BLOB mDocumentBlob;
/*     */ 
/*     */   public ReportMappingTemplateDAO(Connection connection)
/*     */   {
/*  41 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ReportMappingTemplateDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReportMappingTemplateDAO(DataSource ds)
/*     */   {
/*  57 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ReportMappingTemplatePK getPK()
/*     */   {
/*  65 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ReportMappingTemplateEVO details)
/*     */   {
/*  74 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   public ReportMappingTemplateEVO setAndGetDetails(ReportMappingTemplateEVO details, String dependants)
/*     */   {
/*  85 */     setDetails(details);
/*  86 */     generateKeys();
/*  87 */     return this.mDetails.deepClone();
/*     */   }
/*     */ 
/*     */   public ReportMappingTemplatePK create()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/*  96 */     doCreate();
/*     */ 
/*  98 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void load(ReportMappingTemplatePK pk)
/*     */     throws ValidationException
/*     */   {
/* 108 */     doLoad(pk);
/*     */   }
/*     */ 
/*     */   public void store()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 117 */     doStore();
/*     */   }
/*     */ 
/*     */   public void remove()
/*     */   {
/* 126 */     doRemove();
/*     */   }
/*     */ 
/*     */   public ReportMappingTemplatePK findByPrimaryKey(ReportMappingTemplatePK pk_)
/*     */     throws ValidationException
/*     */   {
/* 135 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 136 */     if (exists(pk_))
/*     */     {
/* 138 */       if (timer != null) {
/* 139 */         timer.logDebug("findByPrimaryKey", pk_);
/*     */       }
/* 141 */       return pk_;
/*     */     }
/*     */ 
/* 144 */     throw new ValidationException(pk_ + " not found");
/*     */   }
/*     */ 
/*     */   protected boolean exists(ReportMappingTemplatePK pk)
/*     */   {
/* 162 */     PreparedStatement stmt = null;
/* 163 */     ResultSet resultSet = null;
/* 164 */     boolean returnValue = false;
/*     */     try
/*     */     {
/* 168 */       stmt = getConnection().prepareStatement("select REPORT_MAPPING_TEMPLATE_ID from REPORT_MAPPING_TEMPLATE where    REPORT_MAPPING_TEMPLATE_ID = ? ");
/*     */ 
/* 170 */       int col = 1;
/* 171 */       stmt.setInt(col++, pk.getReportMappingTemplateId());
/*     */ 
/* 173 */       resultSet = stmt.executeQuery();
/*     */ 
/* 175 */       if (!resultSet.next())
/* 176 */         returnValue = false;
/*     */       else
/* 178 */         returnValue = true;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 182 */       throw handleSQLException(pk, "select REPORT_MAPPING_TEMPLATE_ID from REPORT_MAPPING_TEMPLATE where    REPORT_MAPPING_TEMPLATE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 186 */       closeResultSet(resultSet);
/* 187 */       closeStatement(stmt);
/* 188 */       closeConnection();
/*     */     }
/* 190 */     return returnValue;
/*     */   }
/*     */ 
/*     */   private void selectLobs(ReportMappingTemplateEVO evo_)
/*     */   {
/* 204 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 206 */     PreparedStatement stmt = null;
/* 207 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 211 */       stmt = getConnection().prepareStatement("select  DOCUMENT from REPORT_MAPPING_TEMPLATE where    REPORT_MAPPING_TEMPLATE_ID = ? for update");
/*     */ 
/* 213 */       putEvoKeysToJdbc(evo_, stmt, 1);
/*     */ 
/* 215 */       resultSet = stmt.executeQuery();
/*     */ 
/* 217 */       int col = 1;
/* 218 */       while (resultSet.next())
/*     */       {
/* 220 */         this.mDocumentBlob = ((BLOB)resultSet.getBlob(col++));
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 225 */       throw handleSQLException(evo_.getPK(), "select  DOCUMENT from REPORT_MAPPING_TEMPLATE where    REPORT_MAPPING_TEMPLATE_ID = ? for update", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 229 */       closeResultSet(resultSet);
/* 230 */       closeStatement(stmt);
/*     */ 
/* 232 */       if (timer != null)
/* 233 */         timer.logDebug("selectLobs", evo_.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void putLobs(ReportMappingTemplateEVO evo_) throws SQLException
/*     */   {
/* 239 */     updateBlob(this.mDocumentBlob, evo_.getDocument());
/*     */   }
/*     */ 
/*     */   private ReportMappingTemplateEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 257 */     int col = 1;
/* 258 */     this.mDocumentBlob = ((BLOB)resultSet_.getBlob(col++));
/* 259 */     ReportMappingTemplateEVO evo = new ReportMappingTemplateEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), blobToByteArray(this.mDocumentBlob), resultSet_.getInt(col++));
/*     */ 
/* 268 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 269 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 270 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 271 */     return evo;
/*     */   }
/*     */ 
/*     */   private byte[] getByteArray(InputStream bis_)
/*     */   {
/*     */     try
/*     */     {
/* 278 */       ByteArrayOutputStream bos = new ByteArrayOutputStream(bis_.available());
/*     */       int chunk;
/* 280 */       while ((chunk = bis_.read()) != -1)
/* 281 */         bos.write(chunk);
/* 282 */       return bos.toByteArray();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 286 */       this._log.error("getByteArray", e);
				throw new RuntimeException(e.getMessage());
/* 287 */     }
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ReportMappingTemplateEVO evo_, PreparedStatement stmt_, int startCol_)
/*     */     throws SQLException
/*     */   {
/* 293 */     int col = startCol_;
/* 294 */     stmt_.setInt(col++, evo_.getReportMappingTemplateId());
/* 295 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ReportMappingTemplateEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 300 */     int col = startCol_;
/* 301 */     stmt_.setString(col++, evo_.getVisId());
/* 302 */     stmt_.setString(col++, evo_.getDescription());
/* 303 */     stmt_.setString(col++, evo_.getDocumentName());
/*     */ 
/* 305 */     stmt_.setInt(col++, evo_.getVersionNum());
/* 306 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 307 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 308 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 309 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ReportMappingTemplatePK pk)
/*     */     throws ValidationException
/*     */   {
/* 325 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 327 */     PreparedStatement stmt = null;
/* 328 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 332 */       stmt = getConnection().prepareStatement("select REPORT_MAPPING_TEMPLATE.DOCUMENT,REPORT_MAPPING_TEMPLATE.REPORT_MAPPING_TEMPLATE_ID,REPORT_MAPPING_TEMPLATE.VIS_ID,REPORT_MAPPING_TEMPLATE.DESCRIPTION,REPORT_MAPPING_TEMPLATE.DOCUMENT_NAME,REPORT_MAPPING_TEMPLATE.VERSION_NUM,REPORT_MAPPING_TEMPLATE.UPDATED_BY_USER_ID,REPORT_MAPPING_TEMPLATE.UPDATED_TIME,REPORT_MAPPING_TEMPLATE.CREATED_TIME from REPORT_MAPPING_TEMPLATE where    REPORT_MAPPING_TEMPLATE_ID = ? ");
/*     */ 
/* 335 */       int col = 1;
/* 336 */       stmt.setInt(col++, pk.getReportMappingTemplateId());
/*     */ 
/* 338 */       resultSet = stmt.executeQuery();
/*     */ 
/* 340 */       if (!resultSet.next()) {
/* 341 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 344 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 345 */       if (this.mDetails.isModified())
/* 346 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 350 */       throw handleSQLException(pk, "select REPORT_MAPPING_TEMPLATE.DOCUMENT,REPORT_MAPPING_TEMPLATE.REPORT_MAPPING_TEMPLATE_ID,REPORT_MAPPING_TEMPLATE.VIS_ID,REPORT_MAPPING_TEMPLATE.DESCRIPTION,REPORT_MAPPING_TEMPLATE.DOCUMENT_NAME,REPORT_MAPPING_TEMPLATE.VERSION_NUM,REPORT_MAPPING_TEMPLATE.UPDATED_BY_USER_ID,REPORT_MAPPING_TEMPLATE.UPDATED_TIME,REPORT_MAPPING_TEMPLATE.CREATED_TIME from REPORT_MAPPING_TEMPLATE where    REPORT_MAPPING_TEMPLATE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 354 */       closeResultSet(resultSet);
/* 355 */       closeStatement(stmt);
/* 356 */       closeConnection();
/*     */ 
/* 358 */       if (timer != null)
/* 359 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 394 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 395 */     generateKeys();
/*     */ 
/* 397 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 402 */       duplicateValueCheckIdentifier();
/*     */ 
/* 404 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 405 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 406 */       stmt = getConnection().prepareStatement("insert into REPORT_MAPPING_TEMPLATE ( REPORT_MAPPING_TEMPLATE_ID,VIS_ID,DESCRIPTION,DOCUMENT_NAME,DOCUMENT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,empty_blob(),?,?,?,?)");
/*     */ 
/* 409 */       int col = 1;
/* 410 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 411 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 414 */       int resultCount = stmt.executeUpdate();
/* 415 */       if (resultCount != 1)
/*     */       {
/* 417 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 422 */       selectLobs(this.mDetails);
/* 423 */       this._log.debug("doCreate", "calling putLobs");
/* 424 */       putLobs(this.mDetails);
/*     */ 
/* 426 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 430 */       throw handleSQLException(this.mDetails.getPK(), "insert into REPORT_MAPPING_TEMPLATE ( REPORT_MAPPING_TEMPLATE_ID,VIS_ID,DESCRIPTION,DOCUMENT_NAME,DOCUMENT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,empty_blob(),?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 434 */       closeStatement(stmt);
/* 435 */       closeConnection();
/*     */ 
/* 437 */       if (timer != null)
/* 438 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int reserveIds(int insertCount)
/*     */   {
/* 458 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 460 */     PreparedStatement stmt = null;
/* 461 */     ResultSet resultSet = null;
/* 462 */     String sqlString = null;
/*     */     try
/*     */     {
/* 467 */       sqlString = "update REPORT_MAPPING_TEMPLATE_SEQ set SEQ_NUM = SEQ_NUM + ?";
/* 468 */       stmt = getConnection().prepareStatement("update REPORT_MAPPING_TEMPLATE_SEQ set SEQ_NUM = SEQ_NUM + ?");
/* 469 */       stmt.setInt(1, insertCount);
/*     */ 
/* 471 */       int resultCount = stmt.executeUpdate();
/* 472 */       if (resultCount != 1) {
/* 473 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*     */       }
/* 475 */       closeStatement(stmt);
/*     */ 
/* 478 */       sqlString = "select SEQ_NUM from REPORT_MAPPING_TEMPLATE_SEQ";
/* 479 */       stmt = getConnection().prepareStatement("select SEQ_NUM from REPORT_MAPPING_TEMPLATE_SEQ");
/* 480 */       resultSet = stmt.executeQuery();
/* 481 */       if (!resultSet.next())
/* 482 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/* 483 */       int latestKey = resultSet.getInt(1);
/*     */ 
/* 485 */       int i = latestKey - insertCount;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 489 */       throw handleSQLException(sqlString, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 493 */       closeResultSet(resultSet);
/* 494 */       closeStatement(stmt);
/* 495 */       closeConnection();
/*     */ 
/* 497 */       if (timer != null)
/* 498 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/* 498 */     }
/*     */   }
/*     */ 
/*     */   public ReportMappingTemplatePK generateKeys()
/*     */   {
/* 508 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 510 */     int insertCount = this.mDetails.getInsertCount(0);
/*     */ 
/* 513 */     if (insertCount == 0) {
/* 514 */       return this.mDetails.getPK();
/*     */     }
/* 516 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*     */ 
/* 518 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   protected void duplicateValueCheckIdentifier()
/*     */     throws DuplicateNameValidationException
/*     */   {
/* 531 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 532 */     PreparedStatement stmt = null;
/* 533 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 537 */       stmt = getConnection().prepareStatement("select count(*) from REPORT_MAPPING_TEMPLATE where    VIS_ID = ? and not(    REPORT_MAPPING_TEMPLATE_ID = ? )");
/*     */ 
/* 540 */       int col = 1;
/* 541 */       stmt.setString(col++, this.mDetails.getVisId());
/* 542 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 545 */       resultSet = stmt.executeQuery();
/*     */ 
/* 547 */       if (!resultSet.next()) {
/* 548 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 552 */       col = 1;
/* 553 */       int count = resultSet.getInt(col++);
/* 554 */       if (count > 0) {
/* 555 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " Identifier");
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 561 */       throw handleSQLException(getPK(), "select count(*) from REPORT_MAPPING_TEMPLATE where    VIS_ID = ? and not(    REPORT_MAPPING_TEMPLATE_ID = ? )", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 565 */       closeResultSet(resultSet);
/* 566 */       closeStatement(stmt);
/* 567 */       closeConnection();
/*     */ 
/* 569 */       if (timer != null)
/* 570 */         timer.logDebug("duplicateValueCheckIdentifier", "");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 596 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 598 */     generateKeys();
/*     */ 
/* 603 */     PreparedStatement stmt = null;
/*     */ 
/* 605 */     boolean mainChanged = this.mDetails.isModified();
/* 606 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 610 */       if (mainChanged)
/* 611 */         duplicateValueCheckIdentifier();
/* 612 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 615 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*     */ 
/* 618 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 619 */         stmt = getConnection().prepareStatement("update REPORT_MAPPING_TEMPLATE set VIS_ID = ?,DESCRIPTION = ?,DOCUMENT_NAME = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_MAPPING_TEMPLATE_ID = ? AND VERSION_NUM = ?");
/*     */ 
/* 621 */         selectLobs(this.mDetails);
/* 622 */         putLobs(this.mDetails);
/*     */ 
/* 625 */         int col = 1;
/* 626 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 627 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 629 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*     */ 
/* 632 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 634 */         if (resultCount == 0) {
/* 635 */           checkVersionNum();
/*     */         }
/* 637 */         if (resultCount != 1) {
/* 638 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 641 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 650 */       throw handleSQLException(getPK(), "update REPORT_MAPPING_TEMPLATE set VIS_ID = ?,DESCRIPTION = ?,DOCUMENT_NAME = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_MAPPING_TEMPLATE_ID = ? AND VERSION_NUM = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 654 */       closeStatement(stmt);
/* 655 */       closeConnection();
/*     */ 
/* 657 */       if ((timer != null) && (
/* 658 */         (mainChanged) || (dependantChanged)))
/* 659 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkVersionNum()
/*     */     throws VersionValidationException
/*     */   {
/* 671 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 672 */     PreparedStatement stmt = null;
/* 673 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 677 */       stmt = getConnection().prepareStatement("select VERSION_NUM from REPORT_MAPPING_TEMPLATE where REPORT_MAPPING_TEMPLATE_ID = ?");
/*     */ 
/* 680 */       int col = 1;
/* 681 */       stmt.setInt(col++, this.mDetails.getReportMappingTemplateId());
/*     */ 
/* 684 */       resultSet = stmt.executeQuery();
/*     */ 
/* 686 */       if (!resultSet.next()) {
/* 687 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 690 */       col = 1;
/* 691 */       int dbVersionNumber = resultSet.getInt(col++);
/* 692 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/* 693 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 699 */       throw handleSQLException(getPK(), "select VERSION_NUM from REPORT_MAPPING_TEMPLATE where REPORT_MAPPING_TEMPLATE_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 703 */       closeStatement(stmt);
/* 704 */       closeResultSet(resultSet);
/*     */ 
/* 706 */       if (timer != null)
/* 707 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doRemove()
/*     */   {
/* 724 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 729 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 734 */       stmt = getConnection().prepareStatement("delete from REPORT_MAPPING_TEMPLATE where    REPORT_MAPPING_TEMPLATE_ID = ? ");
/*     */ 
/* 737 */       int col = 1;
/* 738 */       stmt.setInt(col++, this.mDetails.getReportMappingTemplateId());
/*     */ 
/* 740 */       int resultCount = stmt.executeUpdate();
/*     */ 
/* 742 */       if (resultCount != 1) {
/* 743 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 749 */       throw handleSQLException(getPK(), "delete from REPORT_MAPPING_TEMPLATE where    REPORT_MAPPING_TEMPLATE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 753 */       closeStatement(stmt);
/* 754 */       closeConnection();
/*     */ 
/* 756 */       if (timer != null)
/* 757 */         timer.logDebug("remove", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllReportMappingTemplatesELO getAllReportMappingTemplates()
/*     */   {
/* 788 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 789 */     PreparedStatement stmt = null;
/* 790 */     ResultSet resultSet = null;
/* 791 */     AllReportMappingTemplatesELO results = new AllReportMappingTemplatesELO();
/*     */     try
/*     */     {
/* 794 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_MAPPING_TEMPLATES);
/* 795 */       int col = 1;
/* 796 */       resultSet = stmt.executeQuery();
/* 797 */       while (resultSet.next())
/*     */       {
/* 799 */         col = 2;
/*     */ 
/* 802 */         ReportMappingTemplatePK pkReportMappingTemplate = new ReportMappingTemplatePK(resultSet.getInt(col++));
/*     */ 
/* 805 */         String textReportMappingTemplate = resultSet.getString(col++);
/*     */ 
/* 809 */         ReportMappingTemplateRefImpl erReportMappingTemplate = new ReportMappingTemplateRefImpl(pkReportMappingTemplate, textReportMappingTemplate);
/*     */ 
/* 814 */         int col1 = resultSet.getInt(col++);
/* 815 */         String col2 = resultSet.getString(col++);
/*     */ 
/* 818 */         results.add(erReportMappingTemplate, col1, col2);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 827 */       throw handleSQLException(SQL_ALL_REPORT_MAPPING_TEMPLATES, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 831 */       closeResultSet(resultSet);
/* 832 */       closeStatement(stmt);
/* 833 */       closeConnection();
/*     */     }
/*     */ 
/* 836 */     if (timer != null) {
/* 837 */       timer.logDebug("getAllReportMappingTemplates", " items=" + results.size());
/*     */     }
/*     */ 
/* 841 */     return results;
/*     */   }
/*     */ 
/*     */   public ReportMappingTemplateEVO getDetails(ReportMappingTemplatePK pk, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 859 */     return getDetails(new ReportMappingTemplateCK(pk), dependants);
/*     */   }
/*     */ 
/*     */   public ReportMappingTemplateEVO getDetails(ReportMappingTemplateCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 873 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 876 */     if (this.mDetails == null) {
/* 877 */       doLoad(paramCK.getReportMappingTemplatePK());
/*     */     }
/* 879 */     else if (!this.mDetails.getPK().equals(paramCK.getReportMappingTemplatePK())) {
/* 880 */       doLoad(paramCK.getReportMappingTemplatePK());
/*     */     }
/* 882 */     else if (!checkIfValid())
/*     */     {
/* 884 */       this._log.info("getDetails", "[ALERT] ReportMappingTemplateEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*     */ 
/* 886 */       doLoad(paramCK.getReportMappingTemplatePK());
/*     */     }
/*     */ 
/* 890 */     ReportMappingTemplateEVO details = new ReportMappingTemplateEVO();
/* 891 */     details = this.mDetails.deepClone();
/*     */ 
/* 893 */     if (timer != null) {
/* 894 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 896 */     return details;
/*     */   }
/*     */ 
/*     */   private boolean checkIfValid()
/*     */   {
/* 906 */     boolean stillValid = false;
/* 907 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 908 */     PreparedStatement stmt = null;
/* 909 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 912 */       stmt = getConnection().prepareStatement("select VERSION_NUM from REPORT_MAPPING_TEMPLATE where   REPORT_MAPPING_TEMPLATE_ID = ?");
/* 913 */       int col = 1;
/* 914 */       stmt.setInt(col++, this.mDetails.getReportMappingTemplateId());
/*     */ 
/* 916 */       resultSet = stmt.executeQuery();
/*     */ 
/* 918 */       if (!resultSet.next()) {
/* 919 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*     */       }
/* 921 */       col = 1;
/* 922 */       int dbVersionNum = resultSet.getInt(col++);
/*     */ 
/* 924 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 925 */         stillValid = true;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 929 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from REPORT_MAPPING_TEMPLATE where   REPORT_MAPPING_TEMPLATE_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 933 */       closeResultSet(resultSet);
/* 934 */       closeStatement(stmt);
/* 935 */       closeConnection();
/*     */ 
/* 937 */       if (timer != null) {
/* 938 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*     */       }
/*     */     }
/* 941 */     return stillValid;
/*     */   }
/*     */ 
/*     */   public ReportMappingTemplateEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 947 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 949 */     if (!checkIfValid())
/*     */     {
/* 951 */       this._log.info("getDetails", "ReportMappingTemplate " + this.mDetails.getPK() + " no longer valid - reloading");
/* 952 */       doLoad(this.mDetails.getPK());
/*     */     }
/*     */ 
/* 956 */     ReportMappingTemplateEVO details = this.mDetails.deepClone();
/*     */ 
/* 958 */     if (timer != null) {
/* 959 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 961 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 966 */     return "ReportMappingTemplate";
/*     */   }
/*     */ 
/*     */   public ReportMappingTemplateRef getRef(ReportMappingTemplatePK paramReportMappingTemplatePK)
/*     */     throws ValidationException
/*     */   {
/* 972 */     ReportMappingTemplateEVO evo = getDetails(paramReportMappingTemplatePK, "");
/* 973 */     return evo.getEntityRef();
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.mappingtemplate.ReportMappingTemplateDAO
 * JD-Core Version:    0.6.0
 */