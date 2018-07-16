/*     */ package com.cedar.cp.ejb.impl.report.definition;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.report.definition.AllReportDefMappedExcelcByModelIdELO;
/*     */ import com.cedar.cp.dto.report.definition.AllReportDefMappedExcelcByReportTemplateIdELO;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefMappedExcelCK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefMappedExcelPK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefMappedExcelRefImpl;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefinitionCK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
/*     */ import com.cedar.cp.dto.report.definition.ReportDefinitionRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
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
/*     */ 
/*     */ public class ReportDefMappedExcelDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID,REPORT_DEF_MAPPED_EXCEL.MODEL_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ELEMENT_ID,REPORT_DEF_MAPPED_EXCEL.REPORT_DEPTH,REPORT_DEF_MAPPED_EXCEL.REPORT_TEMPLATE_ID,REPORT_DEF_MAPPED_EXCEL.PARAM,REPORT_DEF_MAPPED_EXCEL.UPDATED_BY_USER_ID,REPORT_DEF_MAPPED_EXCEL.UPDATED_TIME,REPORT_DEF_MAPPED_EXCEL.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from REPORT_DEF_MAPPED_EXCEL where    REPORT_DEFINITION_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into REPORT_DEF_MAPPED_EXCEL ( REPORT_DEFINITION_ID,MODEL_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,REPORT_DEPTH,REPORT_TEMPLATE_ID,PARAM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update REPORT_DEF_MAPPED_EXCEL set MODEL_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,REPORT_DEPTH = ?,REPORT_TEMPLATE_ID = ?,PARAM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? ";
/* 333 */   protected static String SQL_ALL_REPORT_DEF_MAPPED_EXCELC_BY_REPORT_TEMPLATE_ID = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID      ,REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID      ,REPORT_DEF_MAPPED_EXCEL.MODEL_ID      ,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ID      ,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ELEMENT_ID      ,REPORT_DEF_MAPPED_EXCEL.REPORT_TEMPLATE_ID from REPORT_DEF_MAPPED_EXCEL    ,REPORT_DEFINITION where 1=1   and REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID  and  REPORT_TEMPLATE_ID = ?";
/*     */ 
/* 454 */   protected static String SQL_ALL_REPORT_DEF_MAPPED_EXCELC_BY_MODEL_ID = "select 0       ,REPORT_DEFINITION.REPORT_DEFINITION_ID      ,REPORT_DEFINITION.VIS_ID      ,REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID      ,REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID      ,REPORT_DEF_MAPPED_EXCEL.MODEL_ID      ,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ID      ,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ELEMENT_ID      ,REPORT_DEF_MAPPED_EXCEL.REPORT_TEMPLATE_ID from REPORT_DEF_MAPPED_EXCEL    ,REPORT_DEFINITION where 1=1   and REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID  and  MODEL_ID = ?";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from REPORT_DEF_MAPPED_EXCEL where    REPORT_DEFINITION_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from REPORT_DEF_MAPPED_EXCEL where 1=1 and REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID = ? order by  REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID";
/*     */   protected static final String SQL_GET_ALL = " from REPORT_DEF_MAPPED_EXCEL where    REPORT_DEFINITION_ID = ? ";
/*     */   protected ReportDefMappedExcelEVO mDetails;
/*     */ 
/*     */   public ReportDefMappedExcelDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ReportDefMappedExcelDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReportDefMappedExcelDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ReportDefMappedExcelPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ReportDefMappedExcelEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private ReportDefMappedExcelEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  93 */     int col = 1;
/*  94 */     ReportDefMappedExcelEVO evo = new ReportDefMappedExcelEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++));
/*     */ 
/* 104 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 105 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 106 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 107 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ReportDefMappedExcelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 112 */     int col = startCol_;
/* 113 */     stmt_.setInt(col++, evo_.getReportDefinitionId());
/* 114 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ReportDefMappedExcelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 119 */     int col = startCol_;
/* 120 */     stmt_.setInt(col++, evo_.getModelId());
/* 121 */     stmt_.setInt(col++, evo_.getStructureId());
/* 122 */     stmt_.setInt(col++, evo_.getStructureElementId());
/* 123 */     stmt_.setInt(col++, evo_.getReportDepth());
/* 124 */     stmt_.setInt(col++, evo_.getReportTemplateId());
/* 125 */     stmt_.setString(col++, evo_.getParam());
/* 126 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 127 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 128 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 129 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ReportDefMappedExcelPK pk)
/*     */     throws ValidationException
/*     */   {
/* 145 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 147 */     PreparedStatement stmt = null;
/* 148 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 152 */       stmt = getConnection().prepareStatement("select REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID,REPORT_DEF_MAPPED_EXCEL.MODEL_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ELEMENT_ID,REPORT_DEF_MAPPED_EXCEL.REPORT_DEPTH,REPORT_DEF_MAPPED_EXCEL.REPORT_TEMPLATE_ID,REPORT_DEF_MAPPED_EXCEL.PARAM,REPORT_DEF_MAPPED_EXCEL.UPDATED_BY_USER_ID,REPORT_DEF_MAPPED_EXCEL.UPDATED_TIME,REPORT_DEF_MAPPED_EXCEL.CREATED_TIME from REPORT_DEF_MAPPED_EXCEL where    REPORT_DEFINITION_ID = ? ");
/*     */ 
/* 155 */       int col = 1;
/* 156 */       stmt.setInt(col++, pk.getReportDefinitionId());
/*     */ 
/* 158 */       resultSet = stmt.executeQuery();
/*     */ 
/* 160 */       if (!resultSet.next()) {
/* 161 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 164 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 165 */       if (this.mDetails.isModified())
/* 166 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 170 */       throw handleSQLException(pk, "select REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID,REPORT_DEF_MAPPED_EXCEL.MODEL_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ELEMENT_ID,REPORT_DEF_MAPPED_EXCEL.REPORT_DEPTH,REPORT_DEF_MAPPED_EXCEL.REPORT_TEMPLATE_ID,REPORT_DEF_MAPPED_EXCEL.PARAM,REPORT_DEF_MAPPED_EXCEL.UPDATED_BY_USER_ID,REPORT_DEF_MAPPED_EXCEL.UPDATED_TIME,REPORT_DEF_MAPPED_EXCEL.CREATED_TIME from REPORT_DEF_MAPPED_EXCEL where    REPORT_DEFINITION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 174 */       closeResultSet(resultSet);
/* 175 */       closeStatement(stmt);
/* 176 */       closeConnection();
/*     */ 
/* 178 */       if (timer != null)
/* 179 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 216 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 217 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 222 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 223 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 224 */       stmt = getConnection().prepareStatement("insert into REPORT_DEF_MAPPED_EXCEL ( REPORT_DEFINITION_ID,MODEL_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,REPORT_DEPTH,REPORT_TEMPLATE_ID,PARAM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 227 */       int col = 1;
/* 228 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 229 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 232 */       int resultCount = stmt.executeUpdate();
/* 233 */       if (resultCount != 1)
/*     */       {
/* 235 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 238 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 242 */       throw handleSQLException(this.mDetails.getPK(), "insert into REPORT_DEF_MAPPED_EXCEL ( REPORT_DEFINITION_ID,MODEL_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,REPORT_DEPTH,REPORT_TEMPLATE_ID,PARAM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 246 */       closeStatement(stmt);
/* 247 */       closeConnection();
/*     */ 
/* 249 */       if (timer != null)
/* 250 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 278 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 282 */     PreparedStatement stmt = null;
/*     */ 
/* 284 */     boolean mainChanged = this.mDetails.isModified();
/* 285 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 288 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 291 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 292 */         stmt = getConnection().prepareStatement("update REPORT_DEF_MAPPED_EXCEL set MODEL_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,REPORT_DEPTH = ?,REPORT_TEMPLATE_ID = ?,PARAM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? ");
/*     */ 
/* 295 */         int col = 1;
/* 296 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 297 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 300 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 303 */         if (resultCount != 1) {
/* 304 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 307 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 316 */       throw handleSQLException(getPK(), "update REPORT_DEF_MAPPED_EXCEL set MODEL_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,REPORT_DEPTH = ?,REPORT_TEMPLATE_ID = ?,PARAM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_DEFINITION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 320 */       closeStatement(stmt);
/* 321 */       closeConnection();
/*     */ 
/* 323 */       if ((timer != null) && (
/* 324 */         (mainChanged) || (dependantChanged)))
/* 325 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllReportDefMappedExcelcByReportTemplateIdELO getAllReportDefMappedExcelcByReportTemplateId(int param1)
/*     */   {
/* 367 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 368 */     PreparedStatement stmt = null;
/* 369 */     ResultSet resultSet = null;
/* 370 */     AllReportDefMappedExcelcByReportTemplateIdELO results = new AllReportDefMappedExcelcByReportTemplateIdELO();
/*     */     try
/*     */     {
/* 373 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_DEF_MAPPED_EXCELC_BY_REPORT_TEMPLATE_ID);
/* 374 */       int col = 1;
/* 375 */       stmt.setInt(col++, param1);
/* 376 */       resultSet = stmt.executeQuery();
/* 377 */       while (resultSet.next())
/*     */       {
/* 379 */         col = 2;
/*     */ 
/* 382 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*     */ 
/* 385 */         String textReportDefinition = resultSet.getString(col++);
/*     */ 
/* 388 */         ReportDefMappedExcelPK pkReportDefMappedExcel = new ReportDefMappedExcelPK(resultSet.getInt(col++));
/*     */ 
/* 391 */         String textReportDefMappedExcel = "";
/*     */ 
/* 396 */         ReportDefMappedExcelCK ckReportDefMappedExcel = new ReportDefMappedExcelCK(pkReportDefinition, pkReportDefMappedExcel);
/*     */ 
/* 402 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*     */ 
/* 408 */         ReportDefMappedExcelRefImpl erReportDefMappedExcel = new ReportDefMappedExcelRefImpl(ckReportDefMappedExcel, textReportDefMappedExcel);
/*     */ 
/* 413 */         int col1 = resultSet.getInt(col++);
/* 414 */         int col2 = resultSet.getInt(col++);
/* 415 */         int col3 = resultSet.getInt(col++);
/* 416 */         int col4 = resultSet.getInt(col++);
/* 417 */         int col5 = resultSet.getInt(col++);
/*     */ 
/* 420 */         results.add(erReportDefMappedExcel, erReportDefinition, col1, col2, col3, col4, col5);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 433 */       throw handleSQLException(SQL_ALL_REPORT_DEF_MAPPED_EXCELC_BY_REPORT_TEMPLATE_ID, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 437 */       closeResultSet(resultSet);
/* 438 */       closeStatement(stmt);
/* 439 */       closeConnection();
/*     */     }
/*     */ 
/* 442 */     if (timer != null) {
/* 443 */       timer.logDebug("getAllReportDefMappedExcelcByReportTemplateId", " ReportTemplateId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 448 */     return results;
/*     */   }
/*     */ 
/*     */   public AllReportDefMappedExcelcByModelIdELO getAllReportDefMappedExcelcByModelId(int param1)
/*     */   {
/* 488 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 489 */     PreparedStatement stmt = null;
/* 490 */     ResultSet resultSet = null;
/* 491 */     AllReportDefMappedExcelcByModelIdELO results = new AllReportDefMappedExcelcByModelIdELO();
/*     */     try
/*     */     {
/* 494 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_DEF_MAPPED_EXCELC_BY_MODEL_ID);
/* 495 */       int col = 1;
/* 496 */       stmt.setInt(col++, param1);
/* 497 */       resultSet = stmt.executeQuery();
/* 498 */       while (resultSet.next())
/*     */       {
/* 500 */         col = 2;
/*     */ 
/* 503 */         ReportDefinitionPK pkReportDefinition = new ReportDefinitionPK(resultSet.getInt(col++));
/*     */ 
/* 506 */         String textReportDefinition = resultSet.getString(col++);
/*     */ 
/* 509 */         ReportDefMappedExcelPK pkReportDefMappedExcel = new ReportDefMappedExcelPK(resultSet.getInt(col++));
/*     */ 
/* 512 */         String textReportDefMappedExcel = "";
/*     */ 
/* 517 */         ReportDefMappedExcelCK ckReportDefMappedExcel = new ReportDefMappedExcelCK(pkReportDefinition, pkReportDefMappedExcel);
/*     */ 
/* 523 */         ReportDefinitionRefImpl erReportDefinition = new ReportDefinitionRefImpl(pkReportDefinition, textReportDefinition);
/*     */ 
/* 529 */         ReportDefMappedExcelRefImpl erReportDefMappedExcel = new ReportDefMappedExcelRefImpl(ckReportDefMappedExcel, textReportDefMappedExcel);
/*     */ 
/* 534 */         int col1 = resultSet.getInt(col++);
/* 535 */         int col2 = resultSet.getInt(col++);
/* 536 */         int col3 = resultSet.getInt(col++);
/* 537 */         int col4 = resultSet.getInt(col++);
/* 538 */         int col5 = resultSet.getInt(col++);
/*     */ 
/* 541 */         results.add(erReportDefMappedExcel, erReportDefinition, col1, col2, col3, col4, col5);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 554 */       throw handleSQLException(SQL_ALL_REPORT_DEF_MAPPED_EXCELC_BY_MODEL_ID, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 558 */       closeResultSet(resultSet);
/* 559 */       closeStatement(stmt);
/* 560 */       closeConnection();
/*     */     }
/*     */ 
/* 563 */     if (timer != null) {
/* 564 */       timer.logDebug("getAllReportDefMappedExcelcByModelId", " ModelId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 569 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 586 */     if (items == null) {
/* 587 */       return false;
/*     */     }
/* 589 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 590 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 592 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 597 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 598 */       while (iter2.hasNext())
/*     */       {
/* 600 */         this.mDetails = ((ReportDefMappedExcelEVO)iter2.next());
/*     */ 
/* 603 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 605 */         somethingChanged = true;
/*     */ 
/* 608 */         if (deleteStmt == null) {
/* 609 */           deleteStmt = getConnection().prepareStatement("delete from REPORT_DEF_MAPPED_EXCEL where    REPORT_DEFINITION_ID = ? ");
/*     */         }
/*     */ 
/* 612 */         int col = 1;
/* 613 */         deleteStmt.setInt(col++, this.mDetails.getReportDefinitionId());
/*     */ 
/* 615 */         if (this._log.isDebugEnabled()) {
/* 616 */           this._log.debug("update", "ReportDefMappedExcel deleting ReportDefinitionId=" + this.mDetails.getReportDefinitionId());
/*     */         }
/*     */ 
/* 621 */         deleteStmt.addBatch();
/*     */ 
/* 624 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 629 */       if (deleteStmt != null)
/*     */       {
/* 631 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 633 */         deleteStmt.executeBatch();
/*     */ 
/* 635 */         if (timer2 != null) {
/* 636 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 640 */       Iterator iter1 = items.values().iterator();
/* 641 */       while (iter1.hasNext())
/*     */       {
/* 643 */         this.mDetails = ((ReportDefMappedExcelEVO)iter1.next());
/*     */ 
/* 645 */         if (this.mDetails.insertPending())
/*     */         {
/* 647 */           somethingChanged = true;
/* 648 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 651 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 653 */         somethingChanged = true;
/* 654 */         doStore();
/*     */       }
/*     */ 
/* 665 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 669 */       throw handleSQLException("delete from REPORT_DEF_MAPPED_EXCEL where    REPORT_DEFINITION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 673 */       if (deleteStmt != null)
/*     */       {
/* 675 */         closeStatement(deleteStmt);
/* 676 */         closeConnection();
/*     */       }
/*     */ 
/* 679 */       this.mDetails = null;
/*     */ 
/* 681 */       if ((somethingChanged) && 
/* 682 */         (timer != null))
/* 683 */         timer.logDebug("update", "collection"); 
/* 683 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ReportDefinitionPK entityPK, ReportDefinitionEVO owningEVO, String dependants)
/*     */   {
/* 702 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 704 */     PreparedStatement stmt = null;
/* 705 */     ResultSet resultSet = null;
/*     */ 
/* 707 */     int itemCount = 0;
/*     */ 
/* 709 */     Collection theseItems = new ArrayList();
/* 710 */     owningEVO.setReportMappedExcel(theseItems);
/* 711 */     owningEVO.setReportMappedExcelAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 715 */       stmt = getConnection().prepareStatement("select REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID,REPORT_DEF_MAPPED_EXCEL.MODEL_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ELEMENT_ID,REPORT_DEF_MAPPED_EXCEL.REPORT_DEPTH,REPORT_DEF_MAPPED_EXCEL.REPORT_TEMPLATE_ID,REPORT_DEF_MAPPED_EXCEL.PARAM,REPORT_DEF_MAPPED_EXCEL.UPDATED_BY_USER_ID,REPORT_DEF_MAPPED_EXCEL.UPDATED_TIME,REPORT_DEF_MAPPED_EXCEL.CREATED_TIME from REPORT_DEF_MAPPED_EXCEL where 1=1 and REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID = ? order by  REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID");
/*     */ 
/* 717 */       int col = 1;
/* 718 */       stmt.setInt(col++, entityPK.getReportDefinitionId());
/*     */ 
/* 720 */       resultSet = stmt.executeQuery();
/*     */ 
/* 723 */       while (resultSet.next())
/*     */       {
/* 725 */         itemCount++;
/* 726 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 728 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 731 */       if (timer != null) {
/* 732 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 737 */       throw handleSQLException("select REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID,REPORT_DEF_MAPPED_EXCEL.MODEL_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ELEMENT_ID,REPORT_DEF_MAPPED_EXCEL.REPORT_DEPTH,REPORT_DEF_MAPPED_EXCEL.REPORT_TEMPLATE_ID,REPORT_DEF_MAPPED_EXCEL.PARAM,REPORT_DEF_MAPPED_EXCEL.UPDATED_BY_USER_ID,REPORT_DEF_MAPPED_EXCEL.UPDATED_TIME,REPORT_DEF_MAPPED_EXCEL.CREATED_TIME from REPORT_DEF_MAPPED_EXCEL where 1=1 and REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID = ? order by  REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 741 */       closeResultSet(resultSet);
/* 742 */       closeStatement(stmt);
/* 743 */       closeConnection();
/*     */ 
/* 745 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectReportDefinitionId, String dependants, Collection currentList)
/*     */   {
/* 770 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 771 */     PreparedStatement stmt = null;
/* 772 */     ResultSet resultSet = null;
/*     */ 
/* 774 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 778 */       stmt = getConnection().prepareStatement("select REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID,REPORT_DEF_MAPPED_EXCEL.MODEL_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ELEMENT_ID,REPORT_DEF_MAPPED_EXCEL.REPORT_DEPTH,REPORT_DEF_MAPPED_EXCEL.REPORT_TEMPLATE_ID,REPORT_DEF_MAPPED_EXCEL.PARAM,REPORT_DEF_MAPPED_EXCEL.UPDATED_BY_USER_ID,REPORT_DEF_MAPPED_EXCEL.UPDATED_TIME,REPORT_DEF_MAPPED_EXCEL.CREATED_TIME from REPORT_DEF_MAPPED_EXCEL where    REPORT_DEFINITION_ID = ? ");
/*     */ 
/* 780 */       int col = 1;
/* 781 */       stmt.setInt(col++, selectReportDefinitionId);
/*     */ 
/* 783 */       resultSet = stmt.executeQuery();
/*     */ 
/* 785 */       while (resultSet.next())
/*     */       {
/* 787 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 790 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 793 */       if (currentList != null)
/*     */       {
/* 796 */         ListIterator iter = items.listIterator();
/* 797 */         ReportDefMappedExcelEVO currentEVO = null;
/* 798 */         ReportDefMappedExcelEVO newEVO = null;
/* 799 */         while (iter.hasNext())
/*     */         {
/* 801 */           newEVO = (ReportDefMappedExcelEVO)iter.next();
/* 802 */           Iterator iter2 = currentList.iterator();
/* 803 */           while (iter2.hasNext())
/*     */           {
/* 805 */             currentEVO = (ReportDefMappedExcelEVO)iter2.next();
/* 806 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 808 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 814 */         Iterator iter2 = currentList.iterator();
/* 815 */         while (iter2.hasNext())
/*     */         {
/* 817 */           currentEVO = (ReportDefMappedExcelEVO)iter2.next();
/* 818 */           if (currentEVO.insertPending()) {
/* 819 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 823 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 827 */       throw handleSQLException("select REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID,REPORT_DEF_MAPPED_EXCEL.MODEL_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ID,REPORT_DEF_MAPPED_EXCEL.STRUCTURE_ELEMENT_ID,REPORT_DEF_MAPPED_EXCEL.REPORT_DEPTH,REPORT_DEF_MAPPED_EXCEL.REPORT_TEMPLATE_ID,REPORT_DEF_MAPPED_EXCEL.PARAM,REPORT_DEF_MAPPED_EXCEL.UPDATED_BY_USER_ID,REPORT_DEF_MAPPED_EXCEL.UPDATED_TIME,REPORT_DEF_MAPPED_EXCEL.CREATED_TIME from REPORT_DEF_MAPPED_EXCEL where    REPORT_DEFINITION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 831 */       closeResultSet(resultSet);
/* 832 */       closeStatement(stmt);
/* 833 */       closeConnection();
/*     */ 
/* 835 */       if (timer != null) {
/* 836 */         timer.logDebug("getAll", " ReportDefinitionId=" + selectReportDefinitionId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 841 */     return items;
/*     */   }
/*     */ 
/*     */   public ReportDefMappedExcelEVO getDetails(ReportDefinitionCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 855 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 858 */     if (this.mDetails == null) {
/* 859 */       doLoad(((ReportDefMappedExcelCK)paramCK).getReportDefMappedExcelPK());
/*     */     }
/* 861 */     else if (!this.mDetails.getPK().equals(((ReportDefMappedExcelCK)paramCK).getReportDefMappedExcelPK())) {
/* 862 */       doLoad(((ReportDefMappedExcelCK)paramCK).getReportDefMappedExcelPK());
/*     */     }
/*     */ 
/* 865 */     ReportDefMappedExcelEVO details = new ReportDefMappedExcelEVO();
/* 866 */     details = this.mDetails.deepClone();
/*     */ 
/* 868 */     if (timer != null) {
/* 869 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 871 */     return details;
/*     */   }
/*     */ 
/*     */   public ReportDefMappedExcelEVO getDetails(ReportDefinitionCK paramCK, ReportDefMappedExcelEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 877 */     ReportDefMappedExcelEVO savedEVO = this.mDetails;
/* 878 */     this.mDetails = paramEVO;
/* 879 */     ReportDefMappedExcelEVO newEVO = getDetails(paramCK, dependants);
/* 880 */     this.mDetails = savedEVO;
/* 881 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public ReportDefMappedExcelEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 887 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 891 */     ReportDefMappedExcelEVO details = this.mDetails.deepClone();
/*     */ 
/* 893 */     if (timer != null) {
/* 894 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 896 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 901 */     return "ReportDefMappedExcel";
/*     */   }
/*     */ 
/*     */   public ReportDefMappedExcelRefImpl getRef(ReportDefMappedExcelPK paramReportDefMappedExcelPK)
/*     */   {
/* 906 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 907 */     PreparedStatement stmt = null;
/* 908 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 911 */       stmt = getConnection().prepareStatement("select 0,REPORT_DEFINITION.REPORT_DEFINITION_ID from REPORT_DEF_MAPPED_EXCEL,REPORT_DEFINITION where 1=1 and REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID = ? and REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID");
/* 912 */       int col = 1;
/* 913 */       stmt.setInt(col++, paramReportDefMappedExcelPK.getReportDefinitionId());
/*     */ 
/* 915 */       resultSet = stmt.executeQuery();
/*     */ 
/* 917 */       if (!resultSet.next()) {
/* 918 */         throw new RuntimeException(getEntityName() + " getRef " + paramReportDefMappedExcelPK + " not found");
/*     */       }
/* 920 */       col = 2;
/* 921 */       ReportDefinitionPK newReportDefinitionPK = new ReportDefinitionPK(resultSet.getInt(col++));
/*     */ 
/* 925 */       String textReportDefMappedExcel = "";
/* 926 */       ReportDefMappedExcelCK ckReportDefMappedExcel = new ReportDefMappedExcelCK(newReportDefinitionPK, paramReportDefMappedExcelPK);
/*     */ 
/* 931 */       ReportDefMappedExcelRefImpl localReportDefMappedExcelRefImpl = new ReportDefMappedExcelRefImpl(ckReportDefMappedExcel, textReportDefMappedExcel);
/*     */       return localReportDefMappedExcelRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 936 */       throw handleSQLException(paramReportDefMappedExcelPK, "select 0,REPORT_DEFINITION.REPORT_DEFINITION_ID from REPORT_DEF_MAPPED_EXCEL,REPORT_DEFINITION where 1=1 and REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID = ? and REPORT_DEF_MAPPED_EXCEL.REPORT_DEFINITION_ID = REPORT_DEFINITION.REPORT_DEFINITION_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 940 */       closeResultSet(resultSet);
/* 941 */       closeStatement(stmt);
/* 942 */       closeConnection();
/*     */ 
/* 944 */       if (timer != null)
/* 945 */         timer.logDebug("getRef", paramReportDefMappedExcelPK); 
/* 945 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.definition.ReportDefMappedExcelDAO
 * JD-Core Version:    0.6.0
 */