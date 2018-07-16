/*     */ package com.cedar.cp.ejb.impl.extendedattachment;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.api.extendedattachment.ExtendedAttachmentRef;
/*     */ import com.cedar.cp.dto.extendedattachment.AllExtendedAttachmentsELO;
/*     */ import com.cedar.cp.dto.extendedattachment.AllImageExtendedAttachmentsELO;
/*     */ import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentCK;
/*     */ import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
/*     */ import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentRefImpl;
/*     */ import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentsForIdELO;
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
/*     */ import javax.sql.DataSource;
/*     */ import oracle.sql.BLOB;
/*     */ 
/*     */ public class ExtendedAttachmentDAO extends AbstractDAO
/*     */ {
/*  34 */   Log _log = new Log(getClass());
/*     */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select EXTENDED_ATTACHMENT_ID from EXTENDED_ATTACHMENT where    EXTENDED_ATTACHMENT_ID = ? ";
/*     */   private static final String SQL_SELECT_LOBS = "select  ATTATCH from EXTENDED_ATTACHMENT where    EXTENDED_ATTACHMENT_ID = ? for update";
/*     */   private static final String SQL_SELECT_COLUMNS = "select EXTENDED_ATTACHMENT.ATTATCH,EXTENDED_ATTACHMENT.EXTENDED_ATTACHMENT_ID,EXTENDED_ATTACHMENT.FILE_NAME";
/*     */   protected static final String SQL_LOAD = " from EXTENDED_ATTACHMENT where    EXTENDED_ATTACHMENT_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into EXTENDED_ATTACHMENT ( EXTENDED_ATTACHMENT_ID,FILE_NAME,ATTATCH) values ( ?,?,empty_blob())";
/*     */   protected static final String SQL_UPDATE_SEQ_NUM = "update EXTENDED_ATTACHMENT_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*     */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from EXTENDED_ATTACHMENT_SEQ";
/*     */   protected static final String SQL_STORE = "update EXTENDED_ATTACHMENT set FILE_NAME = ? where    EXTENDED_ATTACHMENT_ID = ? ";
/*     */   protected static final String SQL_REMOVE = "delete from EXTENDED_ATTACHMENT where    EXTENDED_ATTACHMENT_ID = ? ";
/* 610 */   protected static String SQL_ALL_EXTENDED_ATTACHMENTS = "select 0       ,EXTENDED_ATTACHMENT.EXTENDED_ATTACHMENT_ID      ,EXTENDED_ATTACHMENT.FILE_NAME      ,EXTENDED_ATTACHMENT.EXTENDED_ATTACHMENT_ID from EXTENDED_ATTACHMENT where 1=1 ";
/*     */ 
/* 690 */   protected static String SQL_EXTENDED_ATTACHMENTS_FOR_ID = "select 0       ,EXTENDED_ATTACHMENT.EXTENDED_ATTACHMENT_ID      ,EXTENDED_ATTACHMENT.FILE_NAME      ,EXTENDED_ATTACHMENT.EXTENDED_ATTACHMENT_ID      ,EXTENDED_ATTACHMENT.FILE_NAME      ,EXTENDED_ATTACHMENT.ATTATCH from EXTENDED_ATTACHMENT where 1=1  and  EXTENDED_ATTACHMENT_ID = ?";
/*     */ 
/* 781 */   protected static String SQL_ALL_IMAGE_EXTENDED_ATTACHMENTS = "select 0       ,EXTENDED_ATTACHMENT.EXTENDED_ATTACHMENT_ID      ,EXTENDED_ATTACHMENT.FILE_NAME from EXTENDED_ATTACHMENT where 1=1  and  EXTENDED_ATTACHMENT.FILE_NAME like '%jpg' or EXTENDED_ATTACHMENT.FILE_NAME like '%jpeg' or EXTENDED_ATTACHMENT.FILE_NAME like '%png' or EXTENDED_ATTACHMENT.FILE_NAME like '%gif' order by FILE_NAME";
/*     */   protected ExtendedAttachmentEVO mDetails;
/*     */   private BLOB mAttatchBlob;
/*     */ 
/*     */   public ExtendedAttachmentDAO(Connection connection)
/*     */   {
/*  41 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ExtendedAttachmentDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ExtendedAttachmentDAO(DataSource ds)
/*     */   {
/*  57 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ExtendedAttachmentPK getPK()
/*     */   {
/*  65 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ExtendedAttachmentEVO details)
/*     */   {
/*  74 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   public ExtendedAttachmentEVO setAndGetDetails(ExtendedAttachmentEVO details, String dependants)
/*     */   {
/*  85 */     setDetails(details);
/*  86 */     generateKeys();
/*  87 */     return this.mDetails.deepClone();
/*     */   }
/*     */ 
/*     */   public ExtendedAttachmentPK create()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/*  96 */     doCreate();
/*     */ 
/*  98 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void load(ExtendedAttachmentPK pk)
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
/*     */   public ExtendedAttachmentPK findByPrimaryKey(ExtendedAttachmentPK pk_)
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
/*     */   protected boolean exists(ExtendedAttachmentPK pk)
/*     */   {
/* 162 */     PreparedStatement stmt = null;
/* 163 */     ResultSet resultSet = null;
/* 164 */     boolean returnValue = false;
/*     */     try
/*     */     {
/* 168 */       stmt = getConnection().prepareStatement("select EXTENDED_ATTACHMENT_ID from EXTENDED_ATTACHMENT where    EXTENDED_ATTACHMENT_ID = ? ");
/*     */ 
/* 170 */       int col = 1;
/* 171 */       stmt.setInt(col++, pk.getExtendedAttachmentId());
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
/* 182 */       throw handleSQLException(pk, "select EXTENDED_ATTACHMENT_ID from EXTENDED_ATTACHMENT where    EXTENDED_ATTACHMENT_ID = ? ", sqle);
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
/*     */   private void selectLobs(ExtendedAttachmentEVO evo_)
/*     */   {
/* 204 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 206 */     PreparedStatement stmt = null;
/* 207 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 211 */       stmt = getConnection().prepareStatement("select  ATTATCH from EXTENDED_ATTACHMENT where    EXTENDED_ATTACHMENT_ID = ? for update");
/*     */ 
/* 213 */       putEvoKeysToJdbc(evo_, stmt, 1);
/*     */ 
/* 215 */       resultSet = stmt.executeQuery();
/*     */ 
/* 217 */       int col = 1;
/* 218 */       while (resultSet.next())
/*     */       {
/* 220 */         this.mAttatchBlob = ((BLOB)resultSet.getBlob(col++));
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 225 */       throw handleSQLException(evo_.getPK(), "select  ATTATCH from EXTENDED_ATTACHMENT where    EXTENDED_ATTACHMENT_ID = ? for update", sqle);
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
/*     */   private void putLobs(ExtendedAttachmentEVO evo_) throws SQLException
/*     */   {
/* 239 */     updateBlob(this.mAttatchBlob, evo_.getAttatch());
/*     */   }
/*     */ 
/*     */   private ExtendedAttachmentEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 251 */     int col = 1;
/* 252 */     this.mAttatchBlob = ((BLOB)resultSet_.getBlob(col++));
/* 253 */     ExtendedAttachmentEVO evo = new ExtendedAttachmentEVO(resultSet_.getInt(col++), resultSet_.getString(col++), blobToByteArray(this.mAttatchBlob));
/*     */ 
/* 259 */     return evo;
/*     */   }
/*     */ 
/*     */   private byte[] getByteArray(InputStream bis_)
/*     */   {
/*     */     try
/*     */     {
/* 266 */       ByteArrayOutputStream bos = new ByteArrayOutputStream(bis_.available());
/*     */       int chunk;
/* 268 */       while ((chunk = bis_.read()) != -1)
/* 269 */         bos.write(chunk);
/* 270 */       return bos.toByteArray();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 274 */       this._log.error("getByteArray", e);
				throw new RuntimeException(e.getMessage());
/* 275 */     }
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ExtendedAttachmentEVO evo_, PreparedStatement stmt_, int startCol_)
/*     */     throws SQLException
/*     */   {
/* 281 */     int col = startCol_;
/* 282 */     stmt_.setInt(col++, evo_.getExtendedAttachmentId());
/* 283 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ExtendedAttachmentEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 288 */     int col = startCol_;
/* 289 */     stmt_.setString(col++, evo_.getFileName());
/*     */ 
/* 291 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ExtendedAttachmentPK pk)
/*     */     throws ValidationException
/*     */   {
/* 307 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 309 */     PreparedStatement stmt = null;
/* 310 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 314 */       stmt = getConnection().prepareStatement("select EXTENDED_ATTACHMENT.ATTATCH,EXTENDED_ATTACHMENT.EXTENDED_ATTACHMENT_ID,EXTENDED_ATTACHMENT.FILE_NAME from EXTENDED_ATTACHMENT where    EXTENDED_ATTACHMENT_ID = ? ");
/*     */ 
/* 317 */       int col = 1;
/* 318 */       stmt.setInt(col++, pk.getExtendedAttachmentId());
/*     */ 
/* 320 */       resultSet = stmt.executeQuery();
/*     */ 
/* 322 */       if (!resultSet.next()) {
/* 323 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 326 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 327 */       if (this.mDetails.isModified())
/* 328 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 332 */       throw handleSQLException(pk, "select EXTENDED_ATTACHMENT.ATTATCH,EXTENDED_ATTACHMENT.EXTENDED_ATTACHMENT_ID,EXTENDED_ATTACHMENT.FILE_NAME from EXTENDED_ATTACHMENT where    EXTENDED_ATTACHMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 336 */       closeResultSet(resultSet);
/* 337 */       closeStatement(stmt);
/* 338 */       closeConnection();
/*     */ 
/* 340 */       if (timer != null)
/* 341 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 364 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 365 */     generateKeys();
/*     */ 
/* 367 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 371 */       stmt = getConnection().prepareStatement("insert into EXTENDED_ATTACHMENT ( EXTENDED_ATTACHMENT_ID,FILE_NAME,ATTATCH) values ( ?,?,empty_blob())");
/*     */ 
/* 374 */       int col = 1;
/* 375 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 376 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 379 */       int resultCount = stmt.executeUpdate();
/* 380 */       if (resultCount != 1)
/*     */       {
/* 382 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 387 */       selectLobs(this.mDetails);
/* 388 */       this._log.debug("doCreate", "calling putLobs");
/* 389 */       putLobs(this.mDetails);
/*     */ 
/* 391 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 395 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXTENDED_ATTACHMENT ( EXTENDED_ATTACHMENT_ID,FILE_NAME,ATTATCH) values ( ?,?,empty_blob())", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 399 */       closeStatement(stmt);
/* 400 */       closeConnection();
/*     */ 
/* 402 */       if (timer != null)
/* 403 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int reserveIds(int insertCount)
/*     */   {
/* 423 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 425 */     PreparedStatement stmt = null;
/* 426 */     ResultSet resultSet = null;
/* 427 */     String sqlString = null;
/*     */     try
/*     */     {
/* 432 */       sqlString = "update EXTENDED_ATTACHMENT_SEQ set SEQ_NUM = SEQ_NUM + ?";
/* 433 */       stmt = getConnection().prepareStatement("update EXTENDED_ATTACHMENT_SEQ set SEQ_NUM = SEQ_NUM + ?");
/* 434 */       stmt.setInt(1, insertCount);
/*     */ 
/* 436 */       int resultCount = stmt.executeUpdate();
/* 437 */       if (resultCount != 1) {
/* 438 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*     */       }
/* 440 */       closeStatement(stmt);
/*     */ 
/* 443 */       sqlString = "select SEQ_NUM from EXTENDED_ATTACHMENT_SEQ";
/* 444 */       stmt = getConnection().prepareStatement("select SEQ_NUM from EXTENDED_ATTACHMENT_SEQ");
/* 445 */       resultSet = stmt.executeQuery();
/* 446 */       if (!resultSet.next())
/* 447 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/* 448 */       int latestKey = resultSet.getInt(1);
/*     */ 
/* 450 */       int i = latestKey - insertCount;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 454 */       throw handleSQLException(sqlString, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 458 */       closeResultSet(resultSet);
/* 459 */       closeStatement(stmt);
/* 460 */       closeConnection();
/*     */ 
/* 462 */       if (timer != null)
/* 463 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/* 463 */     }
/*     */   }
/*     */ 
/*     */   public ExtendedAttachmentPK generateKeys()
/*     */   {
/* 473 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 475 */     int insertCount = this.mDetails.getInsertCount(0);
/*     */ 
/* 478 */     if (insertCount == 0) {
/* 479 */       return this.mDetails.getPK();
/*     */     }
/* 481 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*     */ 
/* 483 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 501 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 503 */     generateKeys();
/*     */ 
/* 508 */     PreparedStatement stmt = null;
/*     */ 
/* 510 */     boolean mainChanged = this.mDetails.isModified();
/* 511 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 514 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 516 */         stmt = getConnection().prepareStatement("update EXTENDED_ATTACHMENT set FILE_NAME = ? where    EXTENDED_ATTACHMENT_ID = ? ");
/*     */ 
/* 518 */         selectLobs(this.mDetails);
/* 519 */         putLobs(this.mDetails);
/*     */ 
/* 522 */         int col = 1;
/* 523 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 524 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 527 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 530 */         if (resultCount != 1) {
/* 531 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 534 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 543 */       throw handleSQLException(getPK(), "update EXTENDED_ATTACHMENT set FILE_NAME = ? where    EXTENDED_ATTACHMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 547 */       closeStatement(stmt);
/* 548 */       closeConnection();
/*     */ 
/* 550 */       if ((timer != null) && (
/* 551 */         (mainChanged) || (dependantChanged)))
/* 552 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doRemove()
/*     */   {
/* 570 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 575 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 580 */       stmt = getConnection().prepareStatement("delete from EXTENDED_ATTACHMENT where    EXTENDED_ATTACHMENT_ID = ? ");
/*     */ 
/* 583 */       int col = 1;
/* 584 */       stmt.setInt(col++, this.mDetails.getExtendedAttachmentId());
/*     */ 
/* 586 */       int resultCount = stmt.executeUpdate();
/*     */ 
/* 588 */       if (resultCount != 1) {
/* 589 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 595 */       throw handleSQLException(getPK(), "delete from EXTENDED_ATTACHMENT where    EXTENDED_ATTACHMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 599 */       closeStatement(stmt);
/* 600 */       closeConnection();
/*     */ 
/* 602 */       if (timer != null)
/* 603 */         timer.logDebug("remove", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllExtendedAttachmentsELO getAllExtendedAttachments()
/*     */   {
/* 633 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 634 */     PreparedStatement stmt = null;
/* 635 */     ResultSet resultSet = null;
/* 636 */     AllExtendedAttachmentsELO results = new AllExtendedAttachmentsELO();
/*     */     try
/*     */     {
/* 639 */       stmt = getConnection().prepareStatement(SQL_ALL_EXTENDED_ATTACHMENTS);
/* 640 */       int col = 1;
/* 641 */       resultSet = stmt.executeQuery();
/* 642 */       while (resultSet.next())
/*     */       {
/* 644 */         col = 2;
/*     */ 
/* 647 */         ExtendedAttachmentPK pkExtendedAttachment = new ExtendedAttachmentPK(resultSet.getInt(col++));
/*     */ 
/* 650 */         String textExtendedAttachment = resultSet.getString(col++);
/*     */ 
/* 654 */         ExtendedAttachmentRefImpl erExtendedAttachment = new ExtendedAttachmentRefImpl(pkExtendedAttachment, textExtendedAttachment);
/*     */ 
/* 659 */         int col1 = resultSet.getInt(col++);
/*     */ 
/* 662 */         results.add(erExtendedAttachment, col1);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 670 */       throw handleSQLException(SQL_ALL_EXTENDED_ATTACHMENTS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 674 */       closeResultSet(resultSet);
/* 675 */       closeStatement(stmt);
/* 676 */       closeConnection();
/*     */     }
/*     */ 
/* 679 */     if (timer != null) {
/* 680 */       timer.logDebug("getAllExtendedAttachments", " items=" + results.size());
/*     */     }
/*     */ 
/* 684 */     return results;
/*     */   }
/*     */ 
/*     */   public ExtendedAttachmentsForIdELO getExtendedAttachmentsForId(int param1)
/*     */   {
/* 717 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 718 */     PreparedStatement stmt = null;
/* 719 */     ResultSet resultSet = null;
/* 720 */     ExtendedAttachmentsForIdELO results = new ExtendedAttachmentsForIdELO();
/*     */     try
/*     */     {
/* 723 */       stmt = getConnection().prepareStatement(SQL_EXTENDED_ATTACHMENTS_FOR_ID);
/* 724 */       int col = 1;
/* 725 */       stmt.setInt(col++, param1);
/* 726 */       resultSet = stmt.executeQuery();
/* 727 */       while (resultSet.next())
/*     */       {
/* 729 */         col = 2;
/*     */ 
/* 732 */         ExtendedAttachmentPK pkExtendedAttachment = new ExtendedAttachmentPK(resultSet.getInt(col++));
/*     */ 
/* 735 */         String textExtendedAttachment = resultSet.getString(col++);
/*     */ 
/* 739 */         ExtendedAttachmentRefImpl erExtendedAttachment = new ExtendedAttachmentRefImpl(pkExtendedAttachment, textExtendedAttachment);
/*     */ 
/* 744 */         int col1 = resultSet.getInt(col++);
/* 745 */         String col2 = resultSet.getString(col++);
/* 746 */         BLOB col3Blob = (BLOB)resultSet.getBlob(col++);
/* 747 */         byte[] col3 = blobToByteArray(col3Blob);
/*     */ 
/* 750 */         results.add(erExtendedAttachment, col1, col2, col3);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 760 */       throw handleSQLException(SQL_EXTENDED_ATTACHMENTS_FOR_ID, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 764 */       closeResultSet(resultSet);
/* 765 */       closeStatement(stmt);
/* 766 */       closeConnection();
/*     */     }
/*     */ 
/* 769 */     if (timer != null) {
/* 770 */       timer.logDebug("getExtendedAttachmentsForId", " ExtendedAttachmentId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 775 */     return results;
/*     */   }
/*     */ 
/*     */   public AllImageExtendedAttachmentsELO getAllImageExtendedAttachments()
/*     */   {
/* 802 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 803 */     PreparedStatement stmt = null;
/* 804 */     ResultSet resultSet = null;
/* 805 */     AllImageExtendedAttachmentsELO results = new AllImageExtendedAttachmentsELO();
/*     */     try
/*     */     {
/* 808 */       stmt = getConnection().prepareStatement(SQL_ALL_IMAGE_EXTENDED_ATTACHMENTS);
/* 809 */       int col = 1;
/* 810 */       resultSet = stmt.executeQuery();
/* 811 */       while (resultSet.next())
/*     */       {
/* 813 */         col = 2;
/*     */ 
/* 816 */         results.add(resultSet.getInt(col++), resultSet.getString(col++));
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 824 */       throw handleSQLException(SQL_ALL_IMAGE_EXTENDED_ATTACHMENTS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 828 */       closeResultSet(resultSet);
/* 829 */       closeStatement(stmt);
/* 830 */       closeConnection();
/*     */     }
/*     */ 
/* 833 */     if (timer != null) {
/* 834 */       timer.logDebug("getAllImageExtendedAttachments", " items=" + results.size());
/*     */     }
/*     */ 
/* 838 */     return results;
/*     */   }
/*     */ 
/*     */   public ExtendedAttachmentEVO getDetails(ExtendedAttachmentPK pk, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 856 */     return getDetails(new ExtendedAttachmentCK(pk), dependants);
/*     */   }
/*     */ 
/*     */   public ExtendedAttachmentEVO getDetails(ExtendedAttachmentCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 870 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 873 */     if (this.mDetails == null) {
/* 874 */       doLoad(paramCK.getExtendedAttachmentPK());
/*     */     }
/* 876 */     else if (!this.mDetails.getPK().equals(paramCK.getExtendedAttachmentPK())) {
/* 877 */       doLoad(paramCK.getExtendedAttachmentPK());
/*     */     }
/*     */ 
/* 880 */     ExtendedAttachmentEVO details = new ExtendedAttachmentEVO();
/* 881 */     details = this.mDetails.deepClone();
/*     */ 
/* 883 */     if (timer != null) {
/* 884 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 886 */     return details;
/*     */   }
/*     */ 
/*     */   public ExtendedAttachmentEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 892 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 896 */     ExtendedAttachmentEVO details = this.mDetails.deepClone();
/*     */ 
/* 898 */     if (timer != null) {
/* 899 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 901 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 906 */     return "ExtendedAttachment";
/*     */   }
/*     */ 
/*     */   public ExtendedAttachmentRef getRef(ExtendedAttachmentPK paramExtendedAttachmentPK)
/*     */     throws ValidationException
/*     */   {
/* 912 */     ExtendedAttachmentEVO evo = getDetails(paramExtendedAttachmentPK, "");
/* 913 */     return evo.getEntityRef();
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extendedattachment.ExtendedAttachmentDAO
 * JD-Core Version:    0.6.0
 */