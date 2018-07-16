/*     */ package com.cedar.cp.ejb.impl.report.type.param;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.report.type.ReportTypeCK;
/*     */ import com.cedar.cp.dto.report.type.ReportTypePK;
/*     */ import com.cedar.cp.dto.report.type.ReportTypeRefImpl;
/*     */ import com.cedar.cp.dto.report.type.param.AllReportTypeParamsELO;
/*     */ import com.cedar.cp.dto.report.type.param.AllReportTypeParamsforTypeELO;
/*     */ import com.cedar.cp.dto.report.type.param.ReportTypeParamCK;
/*     */ import com.cedar.cp.dto.report.type.param.ReportTypeParamPK;
/*     */ import com.cedar.cp.dto.report.type.param.ReportTypeParamRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.ejb.impl.report.type.ReportTypeEVO;
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
/*     */ public class ReportTypeParamDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID,REPORT_TYPE_PARAM.REPORT_TYPE_ID,REPORT_TYPE_PARAM.SEQ,REPORT_TYPE_PARAM.CONTROL,REPORT_TYPE_PARAM.DESCRIPTION,REPORT_TYPE_PARAM.PARAM_DISPLAY,REPORT_TYPE_PARAM.PARAM_ENTITY,REPORT_TYPE_PARAM.DEPENDENT_ENTITY,REPORT_TYPE_PARAM.VALIDATION_EXP,REPORT_TYPE_PARAM.VALIDATION_TXT,REPORT_TYPE_PARAM.UPDATED_BY_USER_ID,REPORT_TYPE_PARAM.UPDATED_TIME,REPORT_TYPE_PARAM.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from REPORT_TYPE_PARAM where    REPORT_TYPE_PARAM_ID = ? AND REPORT_TYPE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into REPORT_TYPE_PARAM ( REPORT_TYPE_PARAM_ID,REPORT_TYPE_ID,SEQ,CONTROL,DESCRIPTION,PARAM_DISPLAY,PARAM_ENTITY,DEPENDENT_ENTITY,VALIDATION_EXP,VALIDATION_TXT,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update REPORT_TYPE_PARAM set SEQ = ?,CONTROL = ?,DESCRIPTION = ?,PARAM_DISPLAY = ?,PARAM_ENTITY = ?,DEPENDENT_ENTITY = ?,VALIDATION_EXP = ?,VALIDATION_TXT = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_TYPE_PARAM_ID = ? AND REPORT_TYPE_ID = ? ";
/* 356 */   protected static String SQL_ALL_REPORT_TYPE_PARAMS = "select 0       ,REPORT_TYPE.REPORT_TYPE_ID      ,REPORT_TYPE.VIS_ID      ,REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID      ,REPORT_TYPE_PARAM.REPORT_TYPE_ID      ,REPORT_TYPE_PARAM.SEQ      ,REPORT_TYPE_PARAM.CONTROL      ,REPORT_TYPE_PARAM.VALIDATION_EXP      ,REPORT_TYPE_PARAM.VALIDATION_TXT from REPORT_TYPE_PARAM    ,REPORT_TYPE where 1=1   and REPORT_TYPE_PARAM.REPORT_TYPE_ID = REPORT_TYPE.REPORT_TYPE_ID  order by REPORT_TYPE_ID";
/*     */ 
/* 472 */   protected static String SQL_ALL_REPORT_TYPE_PARAMSFOR_TYPE = "select 0       ,REPORT_TYPE.REPORT_TYPE_ID      ,REPORT_TYPE.VIS_ID      ,REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID      ,REPORT_TYPE_PARAM.REPORT_TYPE_ID      ,REPORT_TYPE_PARAM.SEQ      ,REPORT_TYPE_PARAM.CONTROL      ,REPORT_TYPE_PARAM.DESCRIPTION      ,REPORT_TYPE_PARAM.PARAM_DISPLAY      ,REPORT_TYPE_PARAM.PARAM_ENTITY      ,REPORT_TYPE_PARAM.DEPENDENT_ENTITY      ,REPORT_TYPE_PARAM.VALIDATION_EXP      ,REPORT_TYPE_PARAM.VALIDATION_TXT from REPORT_TYPE_PARAM    ,REPORT_TYPE where 1=1   and REPORT_TYPE_PARAM.REPORT_TYPE_ID = REPORT_TYPE.REPORT_TYPE_ID  and  REPORT_TYPE_PARAM.REPORT_TYPE_ID = ? order by REPORT_TYPE_PARAM.SEQ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from REPORT_TYPE_PARAM where    REPORT_TYPE_PARAM_ID = ? AND REPORT_TYPE_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from REPORT_TYPE_PARAM where 1=1 and REPORT_TYPE_PARAM.REPORT_TYPE_ID = ? order by  REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID ,REPORT_TYPE_PARAM.REPORT_TYPE_ID";
/*     */   protected static final String SQL_GET_ALL = " from REPORT_TYPE_PARAM where    REPORT_TYPE_ID = ? ";
/*     */   protected ReportTypeParamEVO mDetails;
/*     */ 
/*     */   public ReportTypeParamDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ReportTypeParamDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReportTypeParamDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ReportTypeParamPK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ReportTypeParamEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private ReportTypeParamEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  99 */     int col = 1;
/* 100 */     ReportTypeParamEVO evo = new ReportTypeParamEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++));
/*     */ 
/* 113 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 114 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 115 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 116 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ReportTypeParamEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 121 */     int col = startCol_;
/* 122 */     stmt_.setInt(col++, evo_.getReportTypeParamId());
/* 123 */     stmt_.setInt(col++, evo_.getReportTypeId());
/* 124 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ReportTypeParamEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 129 */     int col = startCol_;
/* 130 */     stmt_.setInt(col++, evo_.getSeq());
/* 131 */     stmt_.setInt(col++, evo_.getControl());
/* 132 */     stmt_.setString(col++, evo_.getDescription());
/* 133 */     stmt_.setString(col++, evo_.getParamDisplay());
/* 134 */     stmt_.setString(col++, evo_.getParamEntity());
/* 135 */     stmt_.setString(col++, evo_.getDependentEntity());
/* 136 */     stmt_.setString(col++, evo_.getValidationExp());
/* 137 */     stmt_.setString(col++, evo_.getValidationTxt());
/* 138 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 139 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 140 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 141 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ReportTypeParamPK pk)
/*     */     throws ValidationException
/*     */   {
/* 158 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 160 */     PreparedStatement stmt = null;
/* 161 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 165 */       stmt = getConnection().prepareStatement("select REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID,REPORT_TYPE_PARAM.REPORT_TYPE_ID,REPORT_TYPE_PARAM.SEQ,REPORT_TYPE_PARAM.CONTROL,REPORT_TYPE_PARAM.DESCRIPTION,REPORT_TYPE_PARAM.PARAM_DISPLAY,REPORT_TYPE_PARAM.PARAM_ENTITY,REPORT_TYPE_PARAM.DEPENDENT_ENTITY,REPORT_TYPE_PARAM.VALIDATION_EXP,REPORT_TYPE_PARAM.VALIDATION_TXT,REPORT_TYPE_PARAM.UPDATED_BY_USER_ID,REPORT_TYPE_PARAM.UPDATED_TIME,REPORT_TYPE_PARAM.CREATED_TIME from REPORT_TYPE_PARAM where    REPORT_TYPE_PARAM_ID = ? AND REPORT_TYPE_ID = ? ");
/*     */ 
/* 168 */       int col = 1;
/* 169 */       stmt.setInt(col++, pk.getReportTypeParamId());
/* 170 */       stmt.setInt(col++, pk.getReportTypeId());
/*     */ 
/* 172 */       resultSet = stmt.executeQuery();
/*     */ 
/* 174 */       if (!resultSet.next()) {
/* 175 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 178 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 179 */       if (this.mDetails.isModified())
/* 180 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 184 */       throw handleSQLException(pk, "select REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID,REPORT_TYPE_PARAM.REPORT_TYPE_ID,REPORT_TYPE_PARAM.SEQ,REPORT_TYPE_PARAM.CONTROL,REPORT_TYPE_PARAM.DESCRIPTION,REPORT_TYPE_PARAM.PARAM_DISPLAY,REPORT_TYPE_PARAM.PARAM_ENTITY,REPORT_TYPE_PARAM.DEPENDENT_ENTITY,REPORT_TYPE_PARAM.VALIDATION_EXP,REPORT_TYPE_PARAM.VALIDATION_TXT,REPORT_TYPE_PARAM.UPDATED_BY_USER_ID,REPORT_TYPE_PARAM.UPDATED_TIME,REPORT_TYPE_PARAM.CREATED_TIME from REPORT_TYPE_PARAM where    REPORT_TYPE_PARAM_ID = ? AND REPORT_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 188 */       closeResultSet(resultSet);
/* 189 */       closeStatement(stmt);
/* 190 */       closeConnection();
/*     */ 
/* 192 */       if (timer != null)
/* 193 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 236 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 237 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 242 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 243 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 244 */       stmt = getConnection().prepareStatement("insert into REPORT_TYPE_PARAM ( REPORT_TYPE_PARAM_ID,REPORT_TYPE_ID,SEQ,CONTROL,DESCRIPTION,PARAM_DISPLAY,PARAM_ENTITY,DEPENDENT_ENTITY,VALIDATION_EXP,VALIDATION_TXT,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 247 */       int col = 1;
/* 248 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 249 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 252 */       int resultCount = stmt.executeUpdate();
/* 253 */       if (resultCount != 1)
/*     */       {
/* 255 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 258 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 262 */       throw handleSQLException(this.mDetails.getPK(), "insert into REPORT_TYPE_PARAM ( REPORT_TYPE_PARAM_ID,REPORT_TYPE_ID,SEQ,CONTROL,DESCRIPTION,PARAM_DISPLAY,PARAM_ENTITY,DEPENDENT_ENTITY,VALIDATION_EXP,VALIDATION_TXT,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 266 */       closeStatement(stmt);
/* 267 */       closeConnection();
/*     */ 
/* 269 */       if (timer != null)
/* 270 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 301 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 305 */     PreparedStatement stmt = null;
/*     */ 
/* 307 */     boolean mainChanged = this.mDetails.isModified();
/* 308 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 311 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 314 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 315 */         stmt = getConnection().prepareStatement("update REPORT_TYPE_PARAM set SEQ = ?,CONTROL = ?,DESCRIPTION = ?,PARAM_DISPLAY = ?,PARAM_ENTITY = ?,DEPENDENT_ENTITY = ?,VALIDATION_EXP = ?,VALIDATION_TXT = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_TYPE_PARAM_ID = ? AND REPORT_TYPE_ID = ? ");
/*     */ 
/* 318 */         int col = 1;
/* 319 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 320 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 323 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 326 */         if (resultCount != 1) {
/* 327 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 330 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 339 */       throw handleSQLException(getPK(), "update REPORT_TYPE_PARAM set SEQ = ?,CONTROL = ?,DESCRIPTION = ?,PARAM_DISPLAY = ?,PARAM_ENTITY = ?,DEPENDENT_ENTITY = ?,VALIDATION_EXP = ?,VALIDATION_TXT = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REPORT_TYPE_PARAM_ID = ? AND REPORT_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 343 */       closeStatement(stmt);
/* 344 */       closeConnection();
/*     */ 
/* 346 */       if ((timer != null) && (
/* 347 */         (mainChanged) || (dependantChanged)))
/* 348 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllReportTypeParamsELO getAllReportTypeParams()
/*     */   {
/* 388 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 389 */     PreparedStatement stmt = null;
/* 390 */     ResultSet resultSet = null;
/* 391 */     AllReportTypeParamsELO results = new AllReportTypeParamsELO();
/*     */     try
/*     */     {
/* 394 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_TYPE_PARAMS);
/* 395 */       int col = 1;
/* 396 */       resultSet = stmt.executeQuery();
/* 397 */       while (resultSet.next())
/*     */       {
/* 399 */         col = 2;
/*     */ 
/* 402 */         ReportTypePK pkReportType = new ReportTypePK(resultSet.getInt(col++));
/*     */ 
/* 405 */         String textReportType = resultSet.getString(col++);
/*     */ 
/* 408 */         ReportTypeParamPK pkReportTypeParam = new ReportTypeParamPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 412 */         String textReportTypeParam = "";
/*     */ 
/* 417 */         ReportTypeParamCK ckReportTypeParam = new ReportTypeParamCK(pkReportType, pkReportTypeParam);
/*     */ 
/* 423 */         ReportTypeRefImpl erReportType = new ReportTypeRefImpl(pkReportType, textReportType);
/*     */ 
/* 429 */         ReportTypeParamRefImpl erReportTypeParam = new ReportTypeParamRefImpl(ckReportTypeParam, textReportTypeParam);
/*     */ 
/* 434 */         int col1 = resultSet.getInt(col++);
/* 435 */         int col2 = resultSet.getInt(col++);
/* 436 */         String col3 = resultSet.getString(col++);
/* 437 */         String col4 = resultSet.getString(col++);
/*     */ 
/* 440 */         results.add(erReportTypeParam, erReportType, col1, col2, col3, col4);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 452 */       throw handleSQLException(SQL_ALL_REPORT_TYPE_PARAMS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 456 */       closeResultSet(resultSet);
/* 457 */       closeStatement(stmt);
/* 458 */       closeConnection();
/*     */     }
/*     */ 
/* 461 */     if (timer != null) {
/* 462 */       timer.logDebug("getAllReportTypeParams", " items=" + results.size());
/*     */     }
/*     */ 
/* 466 */     return results;
/*     */   }
/*     */ 
/*     */   public AllReportTypeParamsforTypeELO getAllReportTypeParamsforType(int param1)
/*     */   {
/* 510 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 511 */     PreparedStatement stmt = null;
/* 512 */     ResultSet resultSet = null;
/* 513 */     AllReportTypeParamsforTypeELO results = new AllReportTypeParamsforTypeELO();
/*     */     try
/*     */     {
/* 516 */       stmt = getConnection().prepareStatement(SQL_ALL_REPORT_TYPE_PARAMSFOR_TYPE);
/* 517 */       int col = 1;
/* 518 */       stmt.setInt(col++, param1);
/* 519 */       resultSet = stmt.executeQuery();
/* 520 */       while (resultSet.next())
/*     */       {
/* 522 */         col = 2;
/*     */ 
/* 525 */         ReportTypePK pkReportType = new ReportTypePK(resultSet.getInt(col++));
/*     */ 
/* 528 */         String textReportType = resultSet.getString(col++);
/*     */ 
/* 531 */         ReportTypeParamPK pkReportTypeParam = new ReportTypeParamPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 535 */         String textReportTypeParam = "";
/*     */ 
/* 540 */         ReportTypeParamCK ckReportTypeParam = new ReportTypeParamCK(pkReportType, pkReportTypeParam);
/*     */ 
/* 546 */         ReportTypeRefImpl erReportType = new ReportTypeRefImpl(pkReportType, textReportType);
/*     */ 
/* 552 */         ReportTypeParamRefImpl erReportTypeParam = new ReportTypeParamRefImpl(ckReportTypeParam, textReportTypeParam);
/*     */ 
/* 557 */         int col1 = resultSet.getInt(col++);
/* 558 */         int col2 = resultSet.getInt(col++);
/* 559 */         String col3 = resultSet.getString(col++);
/* 560 */         String col4 = resultSet.getString(col++);
/* 561 */         String col5 = resultSet.getString(col++);
/* 562 */         String col6 = resultSet.getString(col++);
/* 563 */         String col7 = resultSet.getString(col++);
/* 564 */         String col8 = resultSet.getString(col++);
/*     */ 
/* 567 */         results.add(erReportTypeParam, erReportType, col1, col2, col3, col4, col5, col6, col7, col8);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 583 */       throw handleSQLException(SQL_ALL_REPORT_TYPE_PARAMSFOR_TYPE, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 587 */       closeResultSet(resultSet);
/* 588 */       closeStatement(stmt);
/* 589 */       closeConnection();
/*     */     }
/*     */ 
/* 592 */     if (timer != null) {
/* 593 */       timer.logDebug("getAllReportTypeParamsforType", " ReportTypeId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 598 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 616 */     if (items == null) {
/* 617 */       return false;
/*     */     }
/* 619 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 620 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 622 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 627 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 628 */       while (iter2.hasNext())
/*     */       {
/* 630 */         this.mDetails = ((ReportTypeParamEVO)iter2.next());
/*     */ 
/* 633 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 635 */         somethingChanged = true;
/*     */ 
/* 638 */         if (deleteStmt == null) {
/* 639 */           deleteStmt = getConnection().prepareStatement("delete from REPORT_TYPE_PARAM where    REPORT_TYPE_PARAM_ID = ? AND REPORT_TYPE_ID = ? ");
/*     */         }
/*     */ 
/* 642 */         int col = 1;
/* 643 */         deleteStmt.setInt(col++, this.mDetails.getReportTypeParamId());
/* 644 */         deleteStmt.setInt(col++, this.mDetails.getReportTypeId());
/*     */ 
/* 646 */         if (this._log.isDebugEnabled()) {
/* 647 */           this._log.debug("update", "ReportTypeParam deleting ReportTypeParamId=" + this.mDetails.getReportTypeParamId() + ",ReportTypeId=" + this.mDetails.getReportTypeId());
/*     */         }
/*     */ 
/* 653 */         deleteStmt.addBatch();
/*     */ 
/* 656 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 661 */       if (deleteStmt != null)
/*     */       {
/* 663 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 665 */         deleteStmt.executeBatch();
/*     */ 
/* 667 */         if (timer2 != null) {
/* 668 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 672 */       Iterator iter1 = items.values().iterator();
/* 673 */       while (iter1.hasNext())
/*     */       {
/* 675 */         this.mDetails = ((ReportTypeParamEVO)iter1.next());
/*     */ 
/* 677 */         if (this.mDetails.insertPending())
/*     */         {
/* 679 */           somethingChanged = true;
/* 680 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 683 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 685 */         somethingChanged = true;
/* 686 */         doStore();
/*     */       }
/*     */ 
/* 697 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 701 */       throw handleSQLException("delete from REPORT_TYPE_PARAM where    REPORT_TYPE_PARAM_ID = ? AND REPORT_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 705 */       if (deleteStmt != null)
/*     */       {
/* 707 */         closeStatement(deleteStmt);
/* 708 */         closeConnection();
/*     */       }
/*     */ 
/* 711 */       this.mDetails = null;
/*     */ 
/* 713 */       if ((somethingChanged) && 
/* 714 */         (timer != null))
/* 715 */         timer.logDebug("update", "collection"); 
/* 715 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ReportTypePK entityPK, ReportTypeEVO owningEVO, String dependants)
/*     */   {
/* 735 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 737 */     PreparedStatement stmt = null;
/* 738 */     ResultSet resultSet = null;
/*     */ 
/* 740 */     int itemCount = 0;
/*     */ 
/* 742 */     Collection theseItems = new ArrayList();
/* 743 */     owningEVO.setReportTypeParams(theseItems);
/* 744 */     owningEVO.setReportTypeParamsAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 748 */       stmt = getConnection().prepareStatement("select REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID,REPORT_TYPE_PARAM.REPORT_TYPE_ID,REPORT_TYPE_PARAM.SEQ,REPORT_TYPE_PARAM.CONTROL,REPORT_TYPE_PARAM.DESCRIPTION,REPORT_TYPE_PARAM.PARAM_DISPLAY,REPORT_TYPE_PARAM.PARAM_ENTITY,REPORT_TYPE_PARAM.DEPENDENT_ENTITY,REPORT_TYPE_PARAM.VALIDATION_EXP,REPORT_TYPE_PARAM.VALIDATION_TXT,REPORT_TYPE_PARAM.UPDATED_BY_USER_ID,REPORT_TYPE_PARAM.UPDATED_TIME,REPORT_TYPE_PARAM.CREATED_TIME from REPORT_TYPE_PARAM where 1=1 and REPORT_TYPE_PARAM.REPORT_TYPE_ID = ? order by  REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID ,REPORT_TYPE_PARAM.REPORT_TYPE_ID");
/*     */ 
/* 750 */       int col = 1;
/* 751 */       stmt.setInt(col++, entityPK.getReportTypeId());
/*     */ 
/* 753 */       resultSet = stmt.executeQuery();
/*     */ 
/* 756 */       while (resultSet.next())
/*     */       {
/* 758 */         itemCount++;
/* 759 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 761 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 764 */       if (timer != null) {
/* 765 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 770 */       throw handleSQLException("select REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID,REPORT_TYPE_PARAM.REPORT_TYPE_ID,REPORT_TYPE_PARAM.SEQ,REPORT_TYPE_PARAM.CONTROL,REPORT_TYPE_PARAM.DESCRIPTION,REPORT_TYPE_PARAM.PARAM_DISPLAY,REPORT_TYPE_PARAM.PARAM_ENTITY,REPORT_TYPE_PARAM.DEPENDENT_ENTITY,REPORT_TYPE_PARAM.VALIDATION_EXP,REPORT_TYPE_PARAM.VALIDATION_TXT,REPORT_TYPE_PARAM.UPDATED_BY_USER_ID,REPORT_TYPE_PARAM.UPDATED_TIME,REPORT_TYPE_PARAM.CREATED_TIME from REPORT_TYPE_PARAM where 1=1 and REPORT_TYPE_PARAM.REPORT_TYPE_ID = ? order by  REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID ,REPORT_TYPE_PARAM.REPORT_TYPE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 774 */       closeResultSet(resultSet);
/* 775 */       closeStatement(stmt);
/* 776 */       closeConnection();
/*     */ 
/* 778 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectReportTypeId, String dependants, Collection currentList)
/*     */   {
/* 803 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 804 */     PreparedStatement stmt = null;
/* 805 */     ResultSet resultSet = null;
/*     */ 
/* 807 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 811 */       stmt = getConnection().prepareStatement("select REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID,REPORT_TYPE_PARAM.REPORT_TYPE_ID,REPORT_TYPE_PARAM.SEQ,REPORT_TYPE_PARAM.CONTROL,REPORT_TYPE_PARAM.DESCRIPTION,REPORT_TYPE_PARAM.PARAM_DISPLAY,REPORT_TYPE_PARAM.PARAM_ENTITY,REPORT_TYPE_PARAM.DEPENDENT_ENTITY,REPORT_TYPE_PARAM.VALIDATION_EXP,REPORT_TYPE_PARAM.VALIDATION_TXT,REPORT_TYPE_PARAM.UPDATED_BY_USER_ID,REPORT_TYPE_PARAM.UPDATED_TIME,REPORT_TYPE_PARAM.CREATED_TIME from REPORT_TYPE_PARAM where    REPORT_TYPE_ID = ? ");
/*     */ 
/* 813 */       int col = 1;
/* 814 */       stmt.setInt(col++, selectReportTypeId);
/*     */ 
/* 816 */       resultSet = stmt.executeQuery();
/*     */ 
/* 818 */       while (resultSet.next())
/*     */       {
/* 820 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 823 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 826 */       if (currentList != null)
/*     */       {
/* 829 */         ListIterator iter = items.listIterator();
/* 830 */         ReportTypeParamEVO currentEVO = null;
/* 831 */         ReportTypeParamEVO newEVO = null;
/* 832 */         while (iter.hasNext())
/*     */         {
/* 834 */           newEVO = (ReportTypeParamEVO)iter.next();
/* 835 */           Iterator iter2 = currentList.iterator();
/* 836 */           while (iter2.hasNext())
/*     */           {
/* 838 */             currentEVO = (ReportTypeParamEVO)iter2.next();
/* 839 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 841 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 847 */         Iterator iter2 = currentList.iterator();
/* 848 */         while (iter2.hasNext())
/*     */         {
/* 850 */           currentEVO = (ReportTypeParamEVO)iter2.next();
/* 851 */           if (currentEVO.insertPending()) {
/* 852 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 856 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 860 */       throw handleSQLException("select REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID,REPORT_TYPE_PARAM.REPORT_TYPE_ID,REPORT_TYPE_PARAM.SEQ,REPORT_TYPE_PARAM.CONTROL,REPORT_TYPE_PARAM.DESCRIPTION,REPORT_TYPE_PARAM.PARAM_DISPLAY,REPORT_TYPE_PARAM.PARAM_ENTITY,REPORT_TYPE_PARAM.DEPENDENT_ENTITY,REPORT_TYPE_PARAM.VALIDATION_EXP,REPORT_TYPE_PARAM.VALIDATION_TXT,REPORT_TYPE_PARAM.UPDATED_BY_USER_ID,REPORT_TYPE_PARAM.UPDATED_TIME,REPORT_TYPE_PARAM.CREATED_TIME from REPORT_TYPE_PARAM where    REPORT_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 864 */       closeResultSet(resultSet);
/* 865 */       closeStatement(stmt);
/* 866 */       closeConnection();
/*     */ 
/* 868 */       if (timer != null) {
/* 869 */         timer.logDebug("getAll", " ReportTypeId=" + selectReportTypeId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 874 */     return items;
/*     */   }
/*     */ 
/*     */   public ReportTypeParamEVO getDetails(ReportTypeCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 888 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 891 */     if (this.mDetails == null) {
/* 892 */       doLoad(((ReportTypeParamCK)paramCK).getReportTypeParamPK());
/*     */     }
/* 894 */     else if (!this.mDetails.getPK().equals(((ReportTypeParamCK)paramCK).getReportTypeParamPK())) {
/* 895 */       doLoad(((ReportTypeParamCK)paramCK).getReportTypeParamPK());
/*     */     }
/*     */ 
/* 898 */     ReportTypeParamEVO details = new ReportTypeParamEVO();
/* 899 */     details = this.mDetails.deepClone();
/*     */ 
/* 901 */     if (timer != null) {
/* 902 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 904 */     return details;
/*     */   }
/*     */ 
/*     */   public ReportTypeParamEVO getDetails(ReportTypeCK paramCK, ReportTypeParamEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 910 */     ReportTypeParamEVO savedEVO = this.mDetails;
/* 911 */     this.mDetails = paramEVO;
/* 912 */     ReportTypeParamEVO newEVO = getDetails(paramCK, dependants);
/* 913 */     this.mDetails = savedEVO;
/* 914 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public ReportTypeParamEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 920 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 924 */     ReportTypeParamEVO details = this.mDetails.deepClone();
/*     */ 
/* 926 */     if (timer != null) {
/* 927 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 929 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 934 */     return "ReportTypeParam";
/*     */   }
/*     */ 
/*     */   public ReportTypeParamRefImpl getRef(ReportTypeParamPK paramReportTypeParamPK)
/*     */   {
/* 939 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 940 */     PreparedStatement stmt = null;
/* 941 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 944 */       stmt = getConnection().prepareStatement("select 0,REPORT_TYPE.REPORT_TYPE_ID from REPORT_TYPE_PARAM,REPORT_TYPE where 1=1 and REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID = ? and REPORT_TYPE_PARAM.REPORT_TYPE_ID = ? and REPORT_TYPE_PARAM.REPORT_TYPE_ID = REPORT_TYPE.REPORT_TYPE_ID");
/* 945 */       int col = 1;
/* 946 */       stmt.setInt(col++, paramReportTypeParamPK.getReportTypeParamId());
/* 947 */       stmt.setInt(col++, paramReportTypeParamPK.getReportTypeId());
/*     */ 
/* 949 */       resultSet = stmt.executeQuery();
/*     */ 
/* 951 */       if (!resultSet.next()) {
/* 952 */         throw new RuntimeException(getEntityName() + " getRef " + paramReportTypeParamPK + " not found");
/*     */       }
/* 954 */       col = 2;
/* 955 */       ReportTypePK newReportTypePK = new ReportTypePK(resultSet.getInt(col++));
/*     */ 
/* 959 */       String textReportTypeParam = "";
/* 960 */       ReportTypeParamCK ckReportTypeParam = new ReportTypeParamCK(newReportTypePK, paramReportTypeParamPK);
/*     */ 
/* 965 */       ReportTypeParamRefImpl localReportTypeParamRefImpl = new ReportTypeParamRefImpl(ckReportTypeParam, textReportTypeParam);
/*     */       return localReportTypeParamRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 970 */       throw handleSQLException(paramReportTypeParamPK, "select 0,REPORT_TYPE.REPORT_TYPE_ID from REPORT_TYPE_PARAM,REPORT_TYPE where 1=1 and REPORT_TYPE_PARAM.REPORT_TYPE_PARAM_ID = ? and REPORT_TYPE_PARAM.REPORT_TYPE_ID = ? and REPORT_TYPE_PARAM.REPORT_TYPE_ID = REPORT_TYPE.REPORT_TYPE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 974 */       closeResultSet(resultSet);
/* 975 */       closeStatement(stmt);
/* 976 */       closeConnection();
/*     */ 
/* 978 */       if (timer != null)
/* 979 */         timer.logDebug("getRef", paramReportTypeParamPK); 
/* 979 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.type.param.ReportTypeParamDAO
 * JD-Core Version:    0.6.0
 */