/*     */ package com.cedar.cp.ejb.impl.report.task;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.report.task.ReportGroupingCK;
/*     */ import com.cedar.cp.dto.report.task.ReportGroupingFileCK;
/*     */ import com.cedar.cp.dto.report.task.ReportGroupingFilePK;
/*     */ import com.cedar.cp.dto.report.task.ReportGroupingFileRefImpl;
/*     */ import com.cedar.cp.dto.report.task.ReportGroupingPK;
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
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import javax.sql.DataSource;
/*     */ import oracle.sql.BLOB;
/*     */ 
/*     */ public class ReportGroupingFileDAO extends AbstractDAO
/*     */ {
/*  34 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_LOBS = "select  FILE_DATA from REPORT_GROUPING_FILE where    REPORT_GROUPING_ID = ? AND REPORT_GROUPING_FILE_ID = ? for update";
/*     */   private static final String SQL_SELECT_COLUMNS = "select REPORT_GROUPING_FILE.FILE_DATA,REPORT_GROUPING_FILE.REPORT_GROUPING_ID,REPORT_GROUPING_FILE.REPORT_GROUPING_FILE_ID,REPORT_GROUPING_FILE.FILE_NAME,REPORT_GROUPING_FILE.UPDATED_BY_USER_ID,REPORT_GROUPING_FILE.UPDATED_TIME,REPORT_GROUPING_FILE.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from REPORT_GROUPING_FILE where    REPORT_GROUPING_ID = ? AND REPORT_GROUPING_FILE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into REPORT_GROUPING_FILE ( REPORT_GROUPING_ID,REPORT_GROUPING_FILE_ID,FILE_NAME,FILE_DATA,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,empty_blob(),?,?,?)";
/*     */   protected static final String SQL_STORE = "update REPORT_GROUPING_FILE set FILE_NAME = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_GROUPING_ID = ? AND REPORT_GROUPING_FILE_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from REPORT_GROUPING_FILE where    REPORT_GROUPING_ID = ? AND REPORT_GROUPING_FILE_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from REPORT_GROUPING_FILE where 1=1 and REPORT_GROUPING_FILE.REPORT_GROUPING_ID = ? order by  REPORT_GROUPING_FILE.REPORT_GROUPING_ID ,REPORT_GROUPING_FILE.REPORT_GROUPING_FILE_ID";
/*     */   protected static final String SQL_GET_ALL = " from REPORT_GROUPING_FILE where    REPORT_GROUPING_ID = ? ";
/*     */   protected ReportGroupingFileEVO mDetails;
/*     */   private BLOB mFileDataBlob;
/*     */ 
/*     */   public ReportGroupingFileDAO(Connection connection)
/*     */   {
/*  41 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ReportGroupingFileDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReportGroupingFileDAO(DataSource ds)
/*     */   {
/*  57 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ReportGroupingFilePK getPK()
/*     */   {
/*  65 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ReportGroupingFileEVO details)
/*     */   {
/*  74 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private void selectLobs(ReportGroupingFileEVO evo_)
/*     */   {
/*  89 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/*  91 */     PreparedStatement stmt = null;
/*  92 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/*  96 */       stmt = getConnection().prepareStatement("select  FILE_DATA from REPORT_GROUPING_FILE where    REPORT_GROUPING_ID = ? AND REPORT_GROUPING_FILE_ID = ? for update");
/*     */ 
/*  98 */       putEvoKeysToJdbc(evo_, stmt, 1);
/*     */ 
/* 100 */       resultSet = stmt.executeQuery();
/*     */ 
/* 102 */       int col = 1;
/* 103 */       while (resultSet.next())
/*     */       {
/* 105 */         this.mFileDataBlob = ((BLOB)resultSet.getBlob(col++));
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 110 */       throw handleSQLException(evo_.getPK(), "select  FILE_DATA from REPORT_GROUPING_FILE where    REPORT_GROUPING_ID = ? AND REPORT_GROUPING_FILE_ID = ? for update", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 114 */       closeResultSet(resultSet);
/* 115 */       closeStatement(stmt);
/*     */ 
/* 117 */       if (timer != null)
/* 118 */         timer.logDebug("selectLobs", evo_.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void putLobs(ReportGroupingFileEVO evo_) throws SQLException
/*     */   {
/* 124 */     updateBlob(this.mFileDataBlob, evo_.getFileData());
/*     */   }
/*     */ 
/*     */   private ReportGroupingFileEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 140 */     int col = 1;
/* 141 */     this.mFileDataBlob = ((BLOB)resultSet_.getBlob(col++));
/* 142 */     ReportGroupingFileEVO evo = new ReportGroupingFileEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), blobToByteArray(this.mFileDataBlob));
/*     */ 
/* 149 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 150 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 151 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 152 */     return evo;
/*     */   }
/*     */ 
/*     */   private byte[] getByteArray(InputStream bis_)
/*     */   {
/*     */     try
/*     */     {
/* 159 */       ByteArrayOutputStream bos = new ByteArrayOutputStream(bis_.available());
/*     */       int chunk;
/* 161 */       while ((chunk = bis_.read()) != -1)
/* 162 */         bos.write(chunk);
/* 163 */       return bos.toByteArray();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 167 */       this._log.error("getByteArray", e);
				throw new RuntimeException(e.getMessage());
/* 168 */     }
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ReportGroupingFileEVO evo_, PreparedStatement stmt_, int startCol_)
/*     */     throws SQLException
/*     */   {
/* 174 */     int col = startCol_;
/* 175 */     stmt_.setInt(col++, evo_.getReportGroupingId());
/* 176 */     stmt_.setInt(col++, evo_.getReportGroupingFileId());
/* 177 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ReportGroupingFileEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 182 */     int col = startCol_;
/* 183 */     stmt_.setString(col++, evo_.getFileName());
/*     */ 
/* 185 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 186 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 187 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 188 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ReportGroupingFilePK pk)
/*     */     throws ValidationException
/*     */   {
/* 205 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 207 */     PreparedStatement stmt = null;
/* 208 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 212 */       stmt = getConnection().prepareStatement("select REPORT_GROUPING_FILE.FILE_DATA,REPORT_GROUPING_FILE.REPORT_GROUPING_ID,REPORT_GROUPING_FILE.REPORT_GROUPING_FILE_ID,REPORT_GROUPING_FILE.FILE_NAME,REPORT_GROUPING_FILE.UPDATED_BY_USER_ID,REPORT_GROUPING_FILE.UPDATED_TIME,REPORT_GROUPING_FILE.CREATED_TIME from REPORT_GROUPING_FILE where    REPORT_GROUPING_ID = ? AND REPORT_GROUPING_FILE_ID = ? ");
/*     */ 
/* 215 */       int col = 1;
/* 216 */       stmt.setInt(col++, pk.getReportGroupingId());
/* 217 */       stmt.setInt(col++, pk.getReportGroupingFileId());
/*     */ 
/* 219 */       resultSet = stmt.executeQuery();
/*     */ 
/* 221 */       if (!resultSet.next()) {
/* 222 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 225 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 226 */       if (this.mDetails.isModified())
/* 227 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 231 */       throw handleSQLException(pk, "select REPORT_GROUPING_FILE.FILE_DATA,REPORT_GROUPING_FILE.REPORT_GROUPING_ID,REPORT_GROUPING_FILE.REPORT_GROUPING_FILE_ID,REPORT_GROUPING_FILE.FILE_NAME,REPORT_GROUPING_FILE.UPDATED_BY_USER_ID,REPORT_GROUPING_FILE.UPDATED_TIME,REPORT_GROUPING_FILE.CREATED_TIME from REPORT_GROUPING_FILE where    REPORT_GROUPING_ID = ? AND REPORT_GROUPING_FILE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 235 */       closeResultSet(resultSet);
/* 236 */       closeStatement(stmt);
/* 237 */       closeConnection();
/*     */ 
/* 239 */       if (timer != null)
/* 240 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 271 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 272 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 277 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 278 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 279 */       stmt = getConnection().prepareStatement("insert into REPORT_GROUPING_FILE ( REPORT_GROUPING_ID,REPORT_GROUPING_FILE_ID,FILE_NAME,FILE_DATA,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,empty_blob(),?,?,?)");
/*     */ 
/* 282 */       int col = 1;
/* 283 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 284 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 287 */       int resultCount = stmt.executeUpdate();
/* 288 */       if (resultCount != 1)
/*     */       {
/* 290 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 295 */       selectLobs(this.mDetails);
/* 296 */       this._log.debug("doCreate", "calling putLobs");
/* 297 */       putLobs(this.mDetails);
/*     */ 
/* 299 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 303 */       throw handleSQLException(this.mDetails.getPK(), "insert into REPORT_GROUPING_FILE ( REPORT_GROUPING_ID,REPORT_GROUPING_FILE_ID,FILE_NAME,FILE_DATA,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,empty_blob(),?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 307 */       closeStatement(stmt);
/* 308 */       closeConnection();
/*     */ 
/* 310 */       if (timer != null)
/* 311 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 335 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 339 */     PreparedStatement stmt = null;
/*     */ 
/* 341 */     boolean mainChanged = this.mDetails.isModified();
/* 342 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 345 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 348 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 349 */         stmt = getConnection().prepareStatement("update REPORT_GROUPING_FILE set FILE_NAME = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_GROUPING_ID = ? AND REPORT_GROUPING_FILE_ID = ? ");
/*     */ 
/* 351 */         selectLobs(this.mDetails);
/* 352 */         putLobs(this.mDetails);
/*     */ 
/* 355 */         int col = 1;
/* 356 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 357 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 360 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 363 */         if (resultCount != 1) {
/* 364 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 367 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 376 */       throw handleSQLException(getPK(), "update REPORT_GROUPING_FILE set FILE_NAME = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_GROUPING_ID = ? AND REPORT_GROUPING_FILE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 380 */       closeStatement(stmt);
/* 381 */       closeConnection();
/*     */ 
/* 383 */       if ((timer != null) && (
/* 384 */         (mainChanged) || (dependantChanged)))
/* 385 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 405 */     if (items == null) {
/* 406 */       return false;
/*     */     }
/* 408 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 409 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 411 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 416 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 417 */       while (iter2.hasNext())
/*     */       {
/* 419 */         this.mDetails = ((ReportGroupingFileEVO)iter2.next());
/*     */ 
/* 422 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 424 */         somethingChanged = true;
/*     */ 
/* 427 */         if (deleteStmt == null) {
/* 428 */           deleteStmt = getConnection().prepareStatement("delete from REPORT_GROUPING_FILE where    REPORT_GROUPING_ID = ? AND REPORT_GROUPING_FILE_ID = ? ");
/*     */         }
/*     */ 
/* 431 */         int col = 1;
/* 432 */         deleteStmt.setInt(col++, this.mDetails.getReportGroupingId());
/* 433 */         deleteStmt.setInt(col++, this.mDetails.getReportGroupingFileId());
/*     */ 
/* 435 */         if (this._log.isDebugEnabled()) {
/* 436 */           this._log.debug("update", "ReportGroupingFile deleting ReportGroupingId=" + this.mDetails.getReportGroupingId() + ",ReportGroupingFileId=" + this.mDetails.getReportGroupingFileId());
/*     */         }
/*     */ 
/* 442 */         deleteStmt.addBatch();
/*     */ 
/* 445 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 450 */       if (deleteStmt != null)
/*     */       {
/* 452 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 454 */         deleteStmt.executeBatch();
/*     */ 
/* 456 */         if (timer2 != null) {
/* 457 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 461 */       Iterator iter1 = items.values().iterator();
/* 462 */       while (iter1.hasNext())
/*     */       {
/* 464 */         this.mDetails = ((ReportGroupingFileEVO)iter1.next());
/*     */ 
/* 466 */         if (this.mDetails.insertPending())
/*     */         {
/* 468 */           somethingChanged = true;
/* 469 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 472 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 474 */         somethingChanged = true;
/* 475 */         doStore();
/*     */       }
/*     */ 
/* 486 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 490 */       throw handleSQLException("delete from REPORT_GROUPING_FILE where    REPORT_GROUPING_ID = ? AND REPORT_GROUPING_FILE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 494 */       if (deleteStmt != null)
/*     */       {
/* 496 */         closeStatement(deleteStmt);
/* 497 */         closeConnection();
/*     */       }
/*     */ 
/* 500 */       this.mDetails = null;
/*     */ 
/* 502 */       if ((somethingChanged) && 
/* 503 */         (timer != null))
/* 504 */         timer.logDebug("update", "collection"); 
/* 504 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ReportGroupingPK entityPK, ReportGroupingEVO owningEVO, String dependants)
/*     */   {
/* 524 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 526 */     PreparedStatement stmt = null;
/* 527 */     ResultSet resultSet = null;
/*     */ 
/* 529 */     int itemCount = 0;
/*     */ 
/* 531 */     Collection theseItems = new ArrayList();
/* 532 */     owningEVO.setReportGroupFiles(theseItems);
/* 533 */     owningEVO.setReportGroupFilesAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 537 */       stmt = getConnection().prepareStatement("select REPORT_GROUPING_FILE.FILE_DATA,REPORT_GROUPING_FILE.REPORT_GROUPING_ID,REPORT_GROUPING_FILE.REPORT_GROUPING_FILE_ID,REPORT_GROUPING_FILE.FILE_NAME,REPORT_GROUPING_FILE.UPDATED_BY_USER_ID,REPORT_GROUPING_FILE.UPDATED_TIME,REPORT_GROUPING_FILE.CREATED_TIME from REPORT_GROUPING_FILE where 1=1 and REPORT_GROUPING_FILE.REPORT_GROUPING_ID = ? order by  REPORT_GROUPING_FILE.REPORT_GROUPING_ID ,REPORT_GROUPING_FILE.REPORT_GROUPING_FILE_ID");
/*     */ 
/* 539 */       int col = 1;
/* 540 */       stmt.setInt(col++, entityPK.getReportGroupingId());
/*     */ 
/* 542 */       resultSet = stmt.executeQuery();
/*     */ 
/* 545 */       while (resultSet.next())
/*     */       {
/* 547 */         itemCount++;
/* 548 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 550 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 553 */       if (timer != null) {
/* 554 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 559 */       throw handleSQLException("select REPORT_GROUPING_FILE.FILE_DATA,REPORT_GROUPING_FILE.REPORT_GROUPING_ID,REPORT_GROUPING_FILE.REPORT_GROUPING_FILE_ID,REPORT_GROUPING_FILE.FILE_NAME,REPORT_GROUPING_FILE.UPDATED_BY_USER_ID,REPORT_GROUPING_FILE.UPDATED_TIME,REPORT_GROUPING_FILE.CREATED_TIME from REPORT_GROUPING_FILE where 1=1 and REPORT_GROUPING_FILE.REPORT_GROUPING_ID = ? order by  REPORT_GROUPING_FILE.REPORT_GROUPING_ID ,REPORT_GROUPING_FILE.REPORT_GROUPING_FILE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 563 */       closeResultSet(resultSet);
/* 564 */       closeStatement(stmt);
/* 565 */       closeConnection();
/*     */ 
/* 567 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectReportGroupingId, String dependants, Collection currentList)
/*     */   {
/* 592 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 593 */     PreparedStatement stmt = null;
/* 594 */     ResultSet resultSet = null;
/*     */ 
/* 596 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 600 */       stmt = getConnection().prepareStatement("select REPORT_GROUPING_FILE.FILE_DATA,REPORT_GROUPING_FILE.REPORT_GROUPING_ID,REPORT_GROUPING_FILE.REPORT_GROUPING_FILE_ID,REPORT_GROUPING_FILE.FILE_NAME,REPORT_GROUPING_FILE.UPDATED_BY_USER_ID,REPORT_GROUPING_FILE.UPDATED_TIME,REPORT_GROUPING_FILE.CREATED_TIME from REPORT_GROUPING_FILE where    REPORT_GROUPING_ID = ? ");
/*     */ 
/* 602 */       int col = 1;
/* 603 */       stmt.setInt(col++, selectReportGroupingId);
/*     */ 
/* 605 */       resultSet = stmt.executeQuery();
/*     */ 
/* 607 */       while (resultSet.next())
/*     */       {
/* 609 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 612 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 615 */       if (currentList != null)
/*     */       {
/* 618 */         ListIterator iter = items.listIterator();
/* 619 */         ReportGroupingFileEVO currentEVO = null;
/* 620 */         ReportGroupingFileEVO newEVO = null;
/* 621 */         while (iter.hasNext())
/*     */         {
/* 623 */           newEVO = (ReportGroupingFileEVO)iter.next();
/* 624 */           Iterator iter2 = currentList.iterator();
/* 625 */           while (iter2.hasNext())
/*     */           {
/* 627 */             currentEVO = (ReportGroupingFileEVO)iter2.next();
/* 628 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 630 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 636 */         Iterator iter2 = currentList.iterator();
/* 637 */         while (iter2.hasNext())
/*     */         {
/* 639 */           currentEVO = (ReportGroupingFileEVO)iter2.next();
/* 640 */           if (currentEVO.insertPending()) {
/* 641 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 645 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 649 */       throw handleSQLException("select REPORT_GROUPING_FILE.FILE_DATA,REPORT_GROUPING_FILE.REPORT_GROUPING_ID,REPORT_GROUPING_FILE.REPORT_GROUPING_FILE_ID,REPORT_GROUPING_FILE.FILE_NAME,REPORT_GROUPING_FILE.UPDATED_BY_USER_ID,REPORT_GROUPING_FILE.UPDATED_TIME,REPORT_GROUPING_FILE.CREATED_TIME from REPORT_GROUPING_FILE where    REPORT_GROUPING_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 653 */       closeResultSet(resultSet);
/* 654 */       closeStatement(stmt);
/* 655 */       closeConnection();
/*     */ 
/* 657 */       if (timer != null) {
/* 658 */         timer.logDebug("getAll", " ReportGroupingId=" + selectReportGroupingId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 663 */     return items;
/*     */   }
/*     */ 
/*     */   public ReportGroupingFileEVO getDetails(ReportGroupingCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 677 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 680 */     if (this.mDetails == null) {
/* 681 */       doLoad(((ReportGroupingFileCK)paramCK).getReportGroupingFilePK());
/*     */     }
/* 683 */     else if (!this.mDetails.getPK().equals(((ReportGroupingFileCK)paramCK).getReportGroupingFilePK())) {
/* 684 */       doLoad(((ReportGroupingFileCK)paramCK).getReportGroupingFilePK());
/*     */     }
/*     */ 
/* 687 */     ReportGroupingFileEVO details = new ReportGroupingFileEVO();
/* 688 */     details = this.mDetails.deepClone();
/*     */ 
/* 690 */     if (timer != null) {
/* 691 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 693 */     return details;
/*     */   }
/*     */ 
/*     */   public ReportGroupingFileEVO getDetails(ReportGroupingCK paramCK, ReportGroupingFileEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 699 */     ReportGroupingFileEVO savedEVO = this.mDetails;
/* 700 */     this.mDetails = paramEVO;
/* 701 */     ReportGroupingFileEVO newEVO = getDetails(paramCK, dependants);
/* 702 */     this.mDetails = savedEVO;
/* 703 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public ReportGroupingFileEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 709 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 713 */     ReportGroupingFileEVO details = this.mDetails.deepClone();
/*     */ 
/* 715 */     if (timer != null) {
/* 716 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 718 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 723 */     return "ReportGroupingFile";
/*     */   }
/*     */ 
/*     */   public ReportGroupingFileRefImpl getRef(ReportGroupingFilePK paramReportGroupingFilePK)
/*     */   {
/* 728 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 729 */     PreparedStatement stmt = null;
/* 730 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 733 */       stmt = getConnection().prepareStatement("select 0,REPORT_GROUPING.REPORT_GROUPING_ID from REPORT_GROUPING_FILE,REPORT_GROUPING where 1=1 and REPORT_GROUPING_FILE.REPORT_GROUPING_ID = ? and REPORT_GROUPING_FILE.REPORT_GROUPING_FILE_ID = ? and REPORT_GROUPING_FILE.REPORT_GROUPING_ID = REPORT_GROUPING.REPORT_GROUPING_ID");
/* 734 */       int col = 1;
/* 735 */       stmt.setInt(col++, paramReportGroupingFilePK.getReportGroupingId());
/* 736 */       stmt.setInt(col++, paramReportGroupingFilePK.getReportGroupingFileId());
/*     */ 
/* 738 */       resultSet = stmt.executeQuery();
/*     */ 
/* 740 */       if (!resultSet.next()) {
/* 741 */         throw new RuntimeException(getEntityName() + " getRef " + paramReportGroupingFilePK + " not found");
/*     */       }
/* 743 */       col = 2;
/* 744 */       ReportGroupingPK newReportGroupingPK = new ReportGroupingPK(resultSet.getInt(col++));
/*     */ 
/* 748 */       String textReportGroupingFile = "";
/* 749 */       ReportGroupingFileCK ckReportGroupingFile = new ReportGroupingFileCK(newReportGroupingPK, paramReportGroupingFilePK);
/*     */ 
/* 754 */       ReportGroupingFileRefImpl localReportGroupingFileRefImpl = new ReportGroupingFileRefImpl(ckReportGroupingFile, textReportGroupingFile);
/*     */       return localReportGroupingFileRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 759 */       throw handleSQLException(paramReportGroupingFilePK, "select 0,REPORT_GROUPING.REPORT_GROUPING_ID from REPORT_GROUPING_FILE,REPORT_GROUPING where 1=1 and REPORT_GROUPING_FILE.REPORT_GROUPING_ID = ? and REPORT_GROUPING_FILE.REPORT_GROUPING_FILE_ID = ? and REPORT_GROUPING_FILE.REPORT_GROUPING_ID = REPORT_GROUPING.REPORT_GROUPING_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 763 */       closeResultSet(resultSet);
/* 764 */       closeStatement(stmt);
/* 765 */       closeConnection();
/*     */ 
/* 767 */       if (timer != null)
/* 768 */         timer.logDebug("getRef", paramReportGroupingFilePK); 
/* 768 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.task.ReportGroupingFileDAO
 * JD-Core Version:    0.6.0
 */