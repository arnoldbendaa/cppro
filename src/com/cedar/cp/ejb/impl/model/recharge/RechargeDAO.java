/*      */ package com.cedar.cp.ejb.impl.model.recharge;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.recharge.AllRechargesELO;
/*      */ import com.cedar.cp.dto.model.recharge.AllRechargesWithModelELO;
/*      */ import com.cedar.cp.dto.model.recharge.RechargeCK;
/*      */ import com.cedar.cp.dto.model.recharge.RechargeCellsCK;
/*      */ import com.cedar.cp.dto.model.recharge.RechargeCellsPK;
/*      */ import com.cedar.cp.dto.model.recharge.RechargePK;
/*      */ import com.cedar.cp.dto.model.recharge.RechargeRefImpl;
/*      */ import com.cedar.cp.dto.model.recharge.SingleRechargeELO;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.model.ModelEVO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class RechargeDAO extends AbstractDAO
/*      */ {
/*   38 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select RECHARGE.RECHARGE_ID,RECHARGE.MODEL_ID,RECHARGE.VIS_ID,RECHARGE.DESCRIPTION,RECHARGE.REASON,RECHARGE.REFERENCE,RECHARGE.ALLOCATION_PERCENTAGE,RECHARGE.MANUAL_RATIOS,RECHARGE.ALLOCATION_DATA_TYPE_ID,RECHARGE.DIFF_ACCOUNT,RECHARGE.ACCOUNT_STRUCTURE_ID,RECHARGE.ACCOUNT_STRUCTURE_ELEMENT_ID,RECHARGE.RATIO_TYPE,RECHARGE.VERSION_NUM,RECHARGE.UPDATED_BY_USER_ID,RECHARGE.UPDATED_TIME,RECHARGE.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from RECHARGE where    RECHARGE_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into RECHARGE ( RECHARGE_ID,MODEL_ID,VIS_ID,DESCRIPTION,REASON,REFERENCE,ALLOCATION_PERCENTAGE,MANUAL_RATIOS,ALLOCATION_DATA_TYPE_ID,DIFF_ACCOUNT,ACCOUNT_STRUCTURE_ID,ACCOUNT_STRUCTURE_ELEMENT_ID,RATIO_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_RECHARGENAME = "select count(*) from RECHARGE where    MODEL_ID = ? AND VIS_ID = ? and not(    RECHARGE_ID = ? )";
/*      */   protected static final String SQL_STORE = "update RECHARGE set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,REASON = ?,REFERENCE = ?,ALLOCATION_PERCENTAGE = ?,MANUAL_RATIOS = ?,ALLOCATION_DATA_TYPE_ID = ?,DIFF_ACCOUNT = ?,ACCOUNT_STRUCTURE_ID = ?,ACCOUNT_STRUCTURE_ELEMENT_ID = ?,RATIO_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RECHARGE_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from RECHARGE where RECHARGE_ID = ?";
/*  521 */   protected static String SQL_ALL_RECHARGES = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,RECHARGE.RECHARGE_ID      ,RECHARGE.VIS_ID      ,RECHARGE.MODEL_ID      ,RECHARGE.RECHARGE_ID      ,RECHARGE.DESCRIPTION from RECHARGE    ,MODEL where 1=1   and RECHARGE.MODEL_ID = MODEL.MODEL_ID ";
/*      */ 
/*  633 */   protected static String SQL_ALL_RECHARGES_WITH_MODEL = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,RECHARGE.RECHARGE_ID      ,RECHARGE.VIS_ID      ,RECHARGE.MODEL_ID      ,RECHARGE.RECHARGE_ID      ,RECHARGE.DESCRIPTION from RECHARGE    ,MODEL where 1=1   and RECHARGE.MODEL_ID = MODEL.MODEL_ID  and  RECHARGE.MODEL_ID = ?";
/*      */ 
/*  749 */   protected static String SQL_SINGLE_RECHARGE = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,RECHARGE.RECHARGE_ID      ,RECHARGE.VIS_ID      ,RECHARGE.MODEL_ID      ,RECHARGE.RECHARGE_ID      ,RECHARGE.DESCRIPTION from RECHARGE    ,MODEL where 1=1   and RECHARGE.MODEL_ID = MODEL.MODEL_ID  and  RECHARGE.RECHARGE_ID = ?";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from RECHARGE where    RECHARGE_ID = ? ";
/* 1001 */   private static String[][] SQL_DELETE_CHILDREN = { { "RECHARGE_CELLS", "delete from RECHARGE_CELLS where     RECHARGE_CELLS.RECHARGE_ID = ? " } };
/*      */ 
/* 1010 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/* 1014 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and RECHARGE.RECHARGE_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from RECHARGE where 1=1 and RECHARGE.MODEL_ID = ? order by  RECHARGE.RECHARGE_ID";
/*      */   protected static final String SQL_GET_ALL = " from RECHARGE where    MODEL_ID = ? ";
/*      */   protected RechargeCellsDAO mRechargeCellsDAO;
/*      */   protected RechargeEVO mDetails;
/*      */ 
/*      */   public RechargeDAO(Connection connection)
/*      */   {
/*   45 */     super(connection);
/*      */   }
/*      */ 
/*      */   public RechargeDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public RechargeDAO(DataSource ds)
/*      */   {
/*   61 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected RechargePK getPK()
/*      */   {
/*   69 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(RechargeEVO details)
/*      */   {
/*   78 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private RechargeEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  106 */     int col = 1;
/*  107 */     RechargeEVO evo = new RechargeEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getBigDecimal(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  125 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  126 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  127 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  128 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(RechargeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  133 */     int col = startCol_;
/*  134 */     stmt_.setInt(col++, evo_.getRechargeId());
/*  135 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(RechargeEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  140 */     int col = startCol_;
/*  141 */     stmt_.setInt(col++, evo_.getModelId());
/*  142 */     stmt_.setString(col++, evo_.getVisId());
/*  143 */     stmt_.setString(col++, evo_.getDescription());
/*  144 */     stmt_.setString(col++, evo_.getReason());
/*  145 */     stmt_.setString(col++, evo_.getReference());
/*  146 */     stmt_.setBigDecimal(col++, evo_.getAllocationPercentage());
/*  147 */     if (evo_.getManualRatios())
/*  148 */       stmt_.setString(col++, "Y");
/*      */     else
/*  150 */       stmt_.setString(col++, " ");
/*  151 */     stmt_.setInt(col++, evo_.getAllocationDataTypeId());
/*  152 */     if (evo_.getDiffAccount())
/*  153 */       stmt_.setString(col++, "Y");
/*      */     else
/*  155 */       stmt_.setString(col++, " ");
/*  156 */     stmt_.setInt(col++, evo_.getAccountStructureId());
/*  157 */     stmt_.setInt(col++, evo_.getAccountStructureElementId());
/*  158 */     stmt_.setInt(col++, evo_.getRatioType());
/*  159 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  160 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  161 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  162 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  163 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(RechargePK pk)
/*      */     throws ValidationException
/*      */   {
/*  179 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  181 */     PreparedStatement stmt = null;
/*  182 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  186 */       stmt = getConnection().prepareStatement("select RECHARGE.RECHARGE_ID,RECHARGE.MODEL_ID,RECHARGE.VIS_ID,RECHARGE.DESCRIPTION,RECHARGE.REASON,RECHARGE.REFERENCE,RECHARGE.ALLOCATION_PERCENTAGE,RECHARGE.MANUAL_RATIOS,RECHARGE.ALLOCATION_DATA_TYPE_ID,RECHARGE.DIFF_ACCOUNT,RECHARGE.ACCOUNT_STRUCTURE_ID,RECHARGE.ACCOUNT_STRUCTURE_ELEMENT_ID,RECHARGE.RATIO_TYPE,RECHARGE.VERSION_NUM,RECHARGE.UPDATED_BY_USER_ID,RECHARGE.UPDATED_TIME,RECHARGE.CREATED_TIME from RECHARGE where    RECHARGE_ID = ? ");
/*      */ 
/*  189 */       int col = 1;
/*  190 */       stmt.setInt(col++, pk.getRechargeId());
/*      */ 
/*  192 */       resultSet = stmt.executeQuery();
/*      */ 
/*  194 */       if (!resultSet.next()) {
/*  195 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  198 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  199 */       if (this.mDetails.isModified())
/*  200 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  204 */       throw handleSQLException(pk, "select RECHARGE.RECHARGE_ID,RECHARGE.MODEL_ID,RECHARGE.VIS_ID,RECHARGE.DESCRIPTION,RECHARGE.REASON,RECHARGE.REFERENCE,RECHARGE.ALLOCATION_PERCENTAGE,RECHARGE.MANUAL_RATIOS,RECHARGE.ALLOCATION_DATA_TYPE_ID,RECHARGE.DIFF_ACCOUNT,RECHARGE.ACCOUNT_STRUCTURE_ID,RECHARGE.ACCOUNT_STRUCTURE_ELEMENT_ID,RECHARGE.RATIO_TYPE,RECHARGE.VERSION_NUM,RECHARGE.UPDATED_BY_USER_ID,RECHARGE.UPDATED_TIME,RECHARGE.CREATED_TIME from RECHARGE where    RECHARGE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  208 */       closeResultSet(resultSet);
/*  209 */       closeStatement(stmt);
/*  210 */       closeConnection();
/*      */ 
/*  212 */       if (timer != null)
/*  213 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  264 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  265 */     this.mDetails.postCreateInit();
/*      */ 
/*  267 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  272 */       duplicateValueCheckRechargeName();
/*      */ 
/*  274 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  275 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  276 */       stmt = getConnection().prepareStatement("insert into RECHARGE ( RECHARGE_ID,MODEL_ID,VIS_ID,DESCRIPTION,REASON,REFERENCE,ALLOCATION_PERCENTAGE,MANUAL_RATIOS,ALLOCATION_DATA_TYPE_ID,DIFF_ACCOUNT,ACCOUNT_STRUCTURE_ID,ACCOUNT_STRUCTURE_ELEMENT_ID,RATIO_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  279 */       int col = 1;
/*  280 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  281 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  284 */       int resultCount = stmt.executeUpdate();
/*  285 */       if (resultCount != 1)
/*      */       {
/*  287 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  290 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  294 */       throw handleSQLException(this.mDetails.getPK(), "insert into RECHARGE ( RECHARGE_ID,MODEL_ID,VIS_ID,DESCRIPTION,REASON,REFERENCE,ALLOCATION_PERCENTAGE,MANUAL_RATIOS,ALLOCATION_DATA_TYPE_ID,DIFF_ACCOUNT,ACCOUNT_STRUCTURE_ID,ACCOUNT_STRUCTURE_ELEMENT_ID,RATIO_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  298 */       closeStatement(stmt);
/*  299 */       closeConnection();
/*      */ 
/*  301 */       if (timer != null) {
/*  302 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  308 */       getRechargeCellsDAO().update(this.mDetails.getRechargeCellsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  314 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckRechargeName()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  330 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  331 */     PreparedStatement stmt = null;
/*  332 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  336 */       stmt = getConnection().prepareStatement("select count(*) from RECHARGE where    MODEL_ID = ? AND VIS_ID = ? and not(    RECHARGE_ID = ? )");
/*      */ 
/*  339 */       int col = 1;
/*  340 */       stmt.setInt(col++, this.mDetails.getModelId());
/*  341 */       stmt.setString(col++, this.mDetails.getVisId());
/*  342 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  345 */       resultSet = stmt.executeQuery();
/*      */ 
/*  347 */       if (!resultSet.next()) {
/*  348 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  352 */       col = 1;
/*  353 */       int count = resultSet.getInt(col++);
/*  354 */       if (count > 0) {
/*  355 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " RechargeName");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  361 */       throw handleSQLException(getPK(), "select count(*) from RECHARGE where    MODEL_ID = ? AND VIS_ID = ? and not(    RECHARGE_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  365 */       closeResultSet(resultSet);
/*  366 */       closeStatement(stmt);
/*  367 */       closeConnection();
/*      */ 
/*  369 */       if (timer != null)
/*  370 */         timer.logDebug("duplicateValueCheckRechargeName", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  405 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  409 */     PreparedStatement stmt = null;
/*      */ 
/*  411 */     boolean mainChanged = this.mDetails.isModified();
/*  412 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  416 */       if (mainChanged) {
/*  417 */         duplicateValueCheckRechargeName();
/*      */       }
/*  419 */       if (getRechargeCellsDAO().update(this.mDetails.getRechargeCellsMap())) {
/*  420 */         dependantChanged = true;
/*      */       }
/*  422 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  425 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  428 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  429 */         stmt = getConnection().prepareStatement("update RECHARGE set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,REASON = ?,REFERENCE = ?,ALLOCATION_PERCENTAGE = ?,MANUAL_RATIOS = ?,ALLOCATION_DATA_TYPE_ID = ?,DIFF_ACCOUNT = ?,ACCOUNT_STRUCTURE_ID = ?,ACCOUNT_STRUCTURE_ELEMENT_ID = ?,RATIO_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RECHARGE_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  432 */         int col = 1;
/*  433 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  434 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  436 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  439 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  441 */         if (resultCount == 0) {
/*  442 */           checkVersionNum();
/*      */         }
/*  444 */         if (resultCount != 1) {
/*  445 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  448 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  457 */       throw handleSQLException(getPK(), "update RECHARGE set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,REASON = ?,REFERENCE = ?,ALLOCATION_PERCENTAGE = ?,MANUAL_RATIOS = ?,ALLOCATION_DATA_TYPE_ID = ?,DIFF_ACCOUNT = ?,ACCOUNT_STRUCTURE_ID = ?,ACCOUNT_STRUCTURE_ELEMENT_ID = ?,RATIO_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RECHARGE_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  461 */       closeStatement(stmt);
/*  462 */       closeConnection();
/*      */ 
/*  464 */       if ((timer != null) && (
/*  465 */         (mainChanged) || (dependantChanged)))
/*  466 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  478 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  479 */     PreparedStatement stmt = null;
/*  480 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  484 */       stmt = getConnection().prepareStatement("select VERSION_NUM from RECHARGE where RECHARGE_ID = ?");
/*      */ 
/*  487 */       int col = 1;
/*  488 */       stmt.setInt(col++, this.mDetails.getRechargeId());
/*      */ 
/*  491 */       resultSet = stmt.executeQuery();
/*      */ 
/*  493 */       if (!resultSet.next()) {
/*  494 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  497 */       col = 1;
/*  498 */       int dbVersionNumber = resultSet.getInt(col++);
/*  499 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  500 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  506 */       throw handleSQLException(getPK(), "select VERSION_NUM from RECHARGE where RECHARGE_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  510 */       closeStatement(stmt);
/*  511 */       closeResultSet(resultSet);
/*      */ 
/*  513 */       if (timer != null)
/*  514 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllRechargesELO getAllRecharges()
/*      */   {
/*  552 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  553 */     PreparedStatement stmt = null;
/*  554 */     ResultSet resultSet = null;
/*  555 */     AllRechargesELO results = new AllRechargesELO();
/*      */     try
/*      */     {
/*  558 */       stmt = getConnection().prepareStatement(SQL_ALL_RECHARGES);
/*  559 */       int col = 1;
/*  560 */       resultSet = stmt.executeQuery();
/*  561 */       while (resultSet.next())
/*      */       {
/*  563 */         col = 2;
/*      */ 
/*  566 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  569 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  572 */         RechargePK pkRecharge = new RechargePK(resultSet.getInt(col++));
/*      */ 
/*  575 */         String textRecharge = resultSet.getString(col++);
/*      */ 
/*  580 */         RechargeCK ckRecharge = new RechargeCK(pkModel, pkRecharge);
/*      */ 
/*  586 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  592 */         RechargeRefImpl erRecharge = new RechargeRefImpl(ckRecharge, textRecharge);
/*      */ 
/*  597 */         int col1 = resultSet.getInt(col++);
/*  598 */         int col2 = resultSet.getInt(col++);
/*  599 */         String col3 = resultSet.getString(col++);
/*      */ 
/*  602 */         results.add(erRecharge, erModel, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  613 */       throw handleSQLException(SQL_ALL_RECHARGES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  617 */       closeResultSet(resultSet);
/*  618 */       closeStatement(stmt);
/*  619 */       closeConnection();
/*      */     }
/*      */ 
/*  622 */     if (timer != null) {
/*  623 */       timer.logDebug("getAllRecharges", " items=" + results.size());
/*      */     }
/*      */ 
/*  627 */     return results;
/*      */   }
/*      */ 
/*      */   public AllRechargesWithModelELO getAllRechargesWithModel(int param1)
/*      */   {
/*  666 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  667 */     PreparedStatement stmt = null;
/*  668 */     ResultSet resultSet = null;
/*  669 */     AllRechargesWithModelELO results = new AllRechargesWithModelELO();
/*      */     try
/*      */     {
/*  672 */       stmt = getConnection().prepareStatement(SQL_ALL_RECHARGES_WITH_MODEL);
/*  673 */       int col = 1;
/*  674 */       stmt.setInt(col++, param1);
/*  675 */       resultSet = stmt.executeQuery();
/*  676 */       while (resultSet.next())
/*      */       {
/*  678 */         col = 2;
/*      */ 
/*  681 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  684 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  687 */         RechargePK pkRecharge = new RechargePK(resultSet.getInt(col++));
/*      */ 
/*  690 */         String textRecharge = resultSet.getString(col++);
/*      */ 
/*  695 */         RechargeCK ckRecharge = new RechargeCK(pkModel, pkRecharge);
/*      */ 
/*  701 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  707 */         RechargeRefImpl erRecharge = new RechargeRefImpl(ckRecharge, textRecharge);
/*      */ 
/*  712 */         int col1 = resultSet.getInt(col++);
/*  713 */         int col2 = resultSet.getInt(col++);
/*  714 */         String col3 = resultSet.getString(col++);
/*      */ 
/*  717 */         results.add(erRecharge, erModel, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  728 */       throw handleSQLException(SQL_ALL_RECHARGES_WITH_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  732 */       closeResultSet(resultSet);
/*  733 */       closeStatement(stmt);
/*  734 */       closeConnection();
/*      */     }
/*      */ 
/*  737 */     if (timer != null) {
/*  738 */       timer.logDebug("getAllRechargesWithModel", " ModelId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  743 */     return results;
/*      */   }
/*      */ 
/*      */   public SingleRechargeELO getSingleRecharge(int param1)
/*      */   {
/*  782 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  783 */     PreparedStatement stmt = null;
/*  784 */     ResultSet resultSet = null;
/*  785 */     SingleRechargeELO results = new SingleRechargeELO();
/*      */     try
/*      */     {
/*  788 */       stmt = getConnection().prepareStatement(SQL_SINGLE_RECHARGE);
/*  789 */       int col = 1;
/*  790 */       stmt.setInt(col++, param1);
/*  791 */       resultSet = stmt.executeQuery();
/*  792 */       while (resultSet.next())
/*      */       {
/*  794 */         col = 2;
/*      */ 
/*  797 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  800 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  803 */         RechargePK pkRecharge = new RechargePK(resultSet.getInt(col++));
/*      */ 
/*  806 */         String textRecharge = resultSet.getString(col++);
/*      */ 
/*  811 */         RechargeCK ckRecharge = new RechargeCK(pkModel, pkRecharge);
/*      */ 
/*  817 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  823 */         RechargeRefImpl erRecharge = new RechargeRefImpl(ckRecharge, textRecharge);
/*      */ 
/*  828 */         int col1 = resultSet.getInt(col++);
/*  829 */         int col2 = resultSet.getInt(col++);
/*  830 */         String col3 = resultSet.getString(col++);
/*      */ 
/*  833 */         results.add(erRecharge, erModel, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  844 */       throw handleSQLException(SQL_SINGLE_RECHARGE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  848 */       closeResultSet(resultSet);
/*  849 */       closeStatement(stmt);
/*  850 */       closeConnection();
/*      */     }
/*      */ 
/*  853 */     if (timer != null) {
/*  854 */       timer.logDebug("getSingleRecharge", " RechargeId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  859 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  876 */     if (items == null) {
/*  877 */       return false;
/*      */     }
/*  879 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  880 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  882 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  886 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  887 */       while (iter3.hasNext())
/*      */       {
/*  889 */         this.mDetails = ((RechargeEVO)iter3.next());
/*  890 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  892 */         somethingChanged = true;
/*      */ 
/*  895 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  899 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  900 */       while (iter2.hasNext())
/*      */       {
/*  902 */         this.mDetails = ((RechargeEVO)iter2.next());
/*      */ 
/*  905 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  907 */         somethingChanged = true;
/*      */ 
/*  910 */         if (deleteStmt == null) {
/*  911 */           deleteStmt = getConnection().prepareStatement("delete from RECHARGE where    RECHARGE_ID = ? ");
/*      */         }
/*      */ 
/*  914 */         int col = 1;
/*  915 */         deleteStmt.setInt(col++, this.mDetails.getRechargeId());
/*      */ 
/*  917 */         if (this._log.isDebugEnabled()) {
/*  918 */           this._log.debug("update", "Recharge deleting RechargeId=" + this.mDetails.getRechargeId());
/*      */         }
/*      */ 
/*  923 */         deleteStmt.addBatch();
/*      */ 
/*  926 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  931 */       if (deleteStmt != null)
/*      */       {
/*  933 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  935 */         deleteStmt.executeBatch();
/*      */ 
/*  937 */         if (timer2 != null) {
/*  938 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  942 */       Iterator iter1 = items.values().iterator();
/*  943 */       while (iter1.hasNext())
/*      */       {
/*  945 */         this.mDetails = ((RechargeEVO)iter1.next());
/*      */ 
/*  947 */         if (this.mDetails.insertPending())
/*      */         {
/*  949 */           somethingChanged = true;
/*  950 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  953 */         if (this.mDetails.isModified())
/*      */         {
/*  955 */           somethingChanged = true;
/*  956 */           doStore(); continue;
/*      */         }
/*      */ 
/*  960 */         if ((this.mDetails.deletePending()) || 
/*  966 */           (!getRechargeCellsDAO().update(this.mDetails.getRechargeCellsMap()))) continue;
/*  967 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  979 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  983 */       throw handleSQLException("delete from RECHARGE where    RECHARGE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  987 */       if (deleteStmt != null)
/*      */       {
/*  989 */         closeStatement(deleteStmt);
/*  990 */         closeConnection();
/*      */       }
/*      */ 
/*  993 */       this.mDetails = null;
/*      */ 
/*  995 */       if ((somethingChanged) && 
/*  996 */         (timer != null))
/*  997 */         timer.logDebug("update", "collection"); 
/*  997 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(RechargePK pk)
/*      */   {
/* 1023 */     Set emptyStrings = Collections.emptySet();
/* 1024 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(RechargePK pk, Set<String> exclusionTables)
/*      */   {
/* 1030 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1032 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1034 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1036 */       PreparedStatement stmt = null;
/*      */ 
/* 1038 */       int resultCount = 0;
/* 1039 */       String s = null;
/*      */       try
/*      */       {
/* 1042 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 1044 */         if (this._log.isDebugEnabled()) {
/* 1045 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1047 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1050 */         int col = 1;
/* 1051 */         stmt.setInt(col++, pk.getRechargeId());
/*      */ 
/* 1054 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1058 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1062 */         closeStatement(stmt);
/* 1063 */         closeConnection();
/*      */ 
/* 1065 */         if (timer != null) {
/* 1066 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1070 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1072 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1074 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1076 */       PreparedStatement stmt = null;
/*      */ 
/* 1078 */       int resultCount = 0;
/* 1079 */       String s = null;
/*      */       try
/*      */       {
/* 1082 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1084 */         if (this._log.isDebugEnabled()) {
/* 1085 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1087 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1090 */         int col = 1;
/* 1091 */         stmt.setInt(col++, pk.getRechargeId());
/*      */ 
/* 1094 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1098 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1102 */         closeStatement(stmt);
/* 1103 */         closeConnection();
/*      */ 
/* 1105 */         if (timer != null)
/* 1106 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants)
/*      */   {
/* 1126 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1128 */     PreparedStatement stmt = null;
/* 1129 */     ResultSet resultSet = null;
/*      */ 
/* 1131 */     int itemCount = 0;
/*      */ 
/* 1133 */     Collection theseItems = new ArrayList();
/* 1134 */     owningEVO.setRecharge(theseItems);
/* 1135 */     owningEVO.setRechargeAllItemsLoaded(true);
/*      */     try
/*      */     {
/* 1139 */       stmt = getConnection().prepareStatement("select RECHARGE.RECHARGE_ID,RECHARGE.MODEL_ID,RECHARGE.VIS_ID,RECHARGE.DESCRIPTION,RECHARGE.REASON,RECHARGE.REFERENCE,RECHARGE.ALLOCATION_PERCENTAGE,RECHARGE.MANUAL_RATIOS,RECHARGE.ALLOCATION_DATA_TYPE_ID,RECHARGE.DIFF_ACCOUNT,RECHARGE.ACCOUNT_STRUCTURE_ID,RECHARGE.ACCOUNT_STRUCTURE_ELEMENT_ID,RECHARGE.RATIO_TYPE,RECHARGE.VERSION_NUM,RECHARGE.UPDATED_BY_USER_ID,RECHARGE.UPDATED_TIME,RECHARGE.CREATED_TIME from RECHARGE where 1=1 and RECHARGE.MODEL_ID = ? order by  RECHARGE.RECHARGE_ID");
/*      */ 
/* 1141 */       int col = 1;
/* 1142 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/* 1144 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1147 */       while (resultSet.next())
/*      */       {
/* 1149 */         itemCount++;
/* 1150 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1152 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/* 1155 */       if (timer != null) {
/* 1156 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/* 1159 */       if ((itemCount > 0) && (dependants.indexOf("<26>") > -1))
/*      */       {
/* 1161 */         getRechargeCellsDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/* 1165 */       throw handleSQLException("select RECHARGE.RECHARGE_ID,RECHARGE.MODEL_ID,RECHARGE.VIS_ID,RECHARGE.DESCRIPTION,RECHARGE.REASON,RECHARGE.REFERENCE,RECHARGE.ALLOCATION_PERCENTAGE,RECHARGE.MANUAL_RATIOS,RECHARGE.ALLOCATION_DATA_TYPE_ID,RECHARGE.DIFF_ACCOUNT,RECHARGE.ACCOUNT_STRUCTURE_ID,RECHARGE.ACCOUNT_STRUCTURE_ELEMENT_ID,RECHARGE.RATIO_TYPE,RECHARGE.VERSION_NUM,RECHARGE.UPDATED_BY_USER_ID,RECHARGE.UPDATED_TIME,RECHARGE.CREATED_TIME from RECHARGE where 1=1 and RECHARGE.MODEL_ID = ? order by  RECHARGE.RECHARGE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1169 */       closeResultSet(resultSet);
/* 1170 */       closeStatement(stmt);
/* 1171 */       closeConnection();
/*      */ 
/* 1173 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectModelId, String dependants, Collection currentList)
/*      */   {
/* 1198 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1199 */     PreparedStatement stmt = null;
/* 1200 */     ResultSet resultSet = null;
/*      */ 
/* 1202 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/* 1206 */       stmt = getConnection().prepareStatement("select RECHARGE.RECHARGE_ID,RECHARGE.MODEL_ID,RECHARGE.VIS_ID,RECHARGE.DESCRIPTION,RECHARGE.REASON,RECHARGE.REFERENCE,RECHARGE.ALLOCATION_PERCENTAGE,RECHARGE.MANUAL_RATIOS,RECHARGE.ALLOCATION_DATA_TYPE_ID,RECHARGE.DIFF_ACCOUNT,RECHARGE.ACCOUNT_STRUCTURE_ID,RECHARGE.ACCOUNT_STRUCTURE_ELEMENT_ID,RECHARGE.RATIO_TYPE,RECHARGE.VERSION_NUM,RECHARGE.UPDATED_BY_USER_ID,RECHARGE.UPDATED_TIME,RECHARGE.CREATED_TIME from RECHARGE where    MODEL_ID = ? ");
/*      */ 
/* 1208 */       int col = 1;
/* 1209 */       stmt.setInt(col++, selectModelId);
/*      */ 
/* 1211 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1213 */       while (resultSet.next())
/*      */       {
/* 1215 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1218 */         getDependants(this.mDetails, dependants);
/*      */ 
/* 1221 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1224 */       if (currentList != null)
/*      */       {
/* 1227 */         ListIterator iter = items.listIterator();
/* 1228 */         RechargeEVO currentEVO = null;
/* 1229 */         RechargeEVO newEVO = null;
/* 1230 */         while (iter.hasNext())
/*      */         {
/* 1232 */           newEVO = (RechargeEVO)iter.next();
/* 1233 */           Iterator iter2 = currentList.iterator();
/* 1234 */           while (iter2.hasNext())
/*      */           {
/* 1236 */             currentEVO = (RechargeEVO)iter2.next();
/* 1237 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1239 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1245 */         Iterator iter2 = currentList.iterator();
/* 1246 */         while (iter2.hasNext())
/*      */         {
/* 1248 */           currentEVO = (RechargeEVO)iter2.next();
/* 1249 */           if (currentEVO.insertPending()) {
/* 1250 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1254 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1258 */       throw handleSQLException("select RECHARGE.RECHARGE_ID,RECHARGE.MODEL_ID,RECHARGE.VIS_ID,RECHARGE.DESCRIPTION,RECHARGE.REASON,RECHARGE.REFERENCE,RECHARGE.ALLOCATION_PERCENTAGE,RECHARGE.MANUAL_RATIOS,RECHARGE.ALLOCATION_DATA_TYPE_ID,RECHARGE.DIFF_ACCOUNT,RECHARGE.ACCOUNT_STRUCTURE_ID,RECHARGE.ACCOUNT_STRUCTURE_ELEMENT_ID,RECHARGE.RATIO_TYPE,RECHARGE.VERSION_NUM,RECHARGE.UPDATED_BY_USER_ID,RECHARGE.UPDATED_TIME,RECHARGE.CREATED_TIME from RECHARGE where    MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1262 */       closeResultSet(resultSet);
/* 1263 */       closeStatement(stmt);
/* 1264 */       closeConnection();
/*      */ 
/* 1266 */       if (timer != null) {
/* 1267 */         timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1272 */     return items;
/*      */   }
/*      */ 
/*      */   public RechargeEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1289 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1292 */     if (this.mDetails == null) {
/* 1293 */       doLoad(((RechargeCK)paramCK).getRechargePK());
/*      */     }
/* 1295 */     else if (!this.mDetails.getPK().equals(((RechargeCK)paramCK).getRechargePK())) {
/* 1296 */       doLoad(((RechargeCK)paramCK).getRechargePK());
/*      */     }
/*      */ 
/* 1299 */     if ((dependants.indexOf("<26>") > -1) && (!this.mDetails.isRechargeCellsAllItemsLoaded()))
/*      */     {
/* 1304 */       this.mDetails.setRechargeCells(getRechargeCellsDAO().getAll(this.mDetails.getRechargeId(), dependants, this.mDetails.getRechargeCells()));
/*      */ 
/* 1311 */       this.mDetails.setRechargeCellsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1314 */     if ((paramCK instanceof RechargeCellsCK))
/*      */     {
/* 1316 */       if (this.mDetails.getRechargeCells() == null) {
/* 1317 */         this.mDetails.loadRechargeCellsItem(getRechargeCellsDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1320 */         RechargeCellsPK pk = ((RechargeCellsCK)paramCK).getRechargeCellsPK();
/* 1321 */         RechargeCellsEVO evo = this.mDetails.getRechargeCellsItem(pk);
/* 1322 */         if (evo == null) {
/* 1323 */           this.mDetails.loadRechargeCellsItem(getRechargeCellsDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1328 */     RechargeEVO details = new RechargeEVO();
/* 1329 */     details = this.mDetails.deepClone();
/*      */ 
/* 1331 */     if (timer != null) {
/* 1332 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1334 */     return details;
/*      */   }
/*      */ 
/*      */   public RechargeEVO getDetails(ModelCK paramCK, RechargeEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1340 */     RechargeEVO savedEVO = this.mDetails;
/* 1341 */     this.mDetails = paramEVO;
/* 1342 */     RechargeEVO newEVO = getDetails(paramCK, dependants);
/* 1343 */     this.mDetails = savedEVO;
/* 1344 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public RechargeEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1350 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1354 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1357 */     RechargeEVO details = this.mDetails.deepClone();
/*      */ 
/* 1359 */     if (timer != null) {
/* 1360 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1362 */     return details;
/*      */   }
/*      */ 
/*      */   protected RechargeCellsDAO getRechargeCellsDAO()
/*      */   {
/* 1371 */     if (this.mRechargeCellsDAO == null)
/*      */     {
/* 1373 */       if (this.mDataSource != null)
/* 1374 */         this.mRechargeCellsDAO = new RechargeCellsDAO(this.mDataSource);
/*      */       else {
/* 1376 */         this.mRechargeCellsDAO = new RechargeCellsDAO(getConnection());
/*      */       }
/*      */     }
/* 1379 */     return this.mRechargeCellsDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1384 */     return "Recharge";
/*      */   }
/*      */ 
/*      */   public RechargeRefImpl getRef(RechargePK paramRechargePK)
/*      */   {
/* 1389 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1390 */     PreparedStatement stmt = null;
/* 1391 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1394 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,RECHARGE.VIS_ID from RECHARGE,MODEL where 1=1 and RECHARGE.RECHARGE_ID = ? and RECHARGE.MODEL_ID = MODEL.MODEL_ID");
/* 1395 */       int col = 1;
/* 1396 */       stmt.setInt(col++, paramRechargePK.getRechargeId());
/*      */ 
/* 1398 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1400 */       if (!resultSet.next()) {
/* 1401 */         throw new RuntimeException(getEntityName() + " getRef " + paramRechargePK + " not found");
/*      */       }
/* 1403 */       col = 2;
/* 1404 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1408 */       String textRecharge = resultSet.getString(col++);
/* 1409 */       RechargeCK ckRecharge = new RechargeCK(newModelPK, paramRechargePK);
/*      */ 
/* 1414 */       RechargeRefImpl localRechargeRefImpl = new RechargeRefImpl(ckRecharge, textRecharge);
/*      */       return localRechargeRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1419 */       throw handleSQLException(paramRechargePK, "select 0,MODEL.MODEL_ID,RECHARGE.VIS_ID from RECHARGE,MODEL where 1=1 and RECHARGE.RECHARGE_ID = ? and RECHARGE.MODEL_ID = MODEL.MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1423 */       closeResultSet(resultSet);
/* 1424 */       closeStatement(stmt);
/* 1425 */       closeConnection();
/*      */ 
/* 1427 */       if (timer != null)
/* 1428 */         timer.logDebug("getRef", paramRechargePK); 
/* 1428 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1440 */     if (c == null)
/* 1441 */       return;
/* 1442 */     Iterator iter = c.iterator();
/* 1443 */     while (iter.hasNext())
/*      */     {
/* 1445 */       RechargeEVO evo = (RechargeEVO)iter.next();
/* 1446 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(RechargeEVO evo, String dependants)
/*      */   {
/* 1460 */     if (evo.insertPending()) {
/* 1461 */       return;
/*      */     }
/* 1463 */     if (evo.getRechargeId() < 1) {
/* 1464 */       return;
/*      */     }
/*      */ 
/* 1468 */     if (dependants.indexOf("<26>") > -1)
/*      */     {
/* 1471 */       if (!evo.isRechargeCellsAllItemsLoaded())
/*      */       {
/* 1473 */         evo.setRechargeCells(getRechargeCellsDAO().getAll(evo.getRechargeId(), dependants, evo.getRechargeCells()));
/*      */ 
/* 1480 */         evo.setRechargeCellsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.recharge.RechargeDAO
 * JD-Core Version:    0.6.0
 */