/*      */ package com.cedar.cp.ejb.impl.model.virement;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.virement.AllVirementCategorysELO;
/*      */ import com.cedar.cp.dto.model.virement.VirementAccountCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementAccountPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementCategoryCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementCategoryPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementCategoryRefImpl;
/*      */ import com.cedar.cp.dto.model.virement.VirementLocationCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementLocationPK;
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
/*      */ public class VirementCategoryDAO extends AbstractDAO
/*      */ {
/*   41 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID,VIREMENT_CATEGORY.MODEL_ID,VIREMENT_CATEGORY.VIS_ID,VIREMENT_CATEGORY.DESCRIPTION,VIREMENT_CATEGORY.TRAN_LIMIT,VIREMENT_CATEGORY.TOTAL_LIMIT_IN,VIREMENT_CATEGORY.TOTAL_LIMIT_OUT,VIREMENT_CATEGORY.VERSION_NUM,VIREMENT_CATEGORY.UPDATED_BY_USER_ID,VIREMENT_CATEGORY.UPDATED_TIME,VIREMENT_CATEGORY.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from VIREMENT_CATEGORY where    VIREMENT_CATEGORY_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into VIREMENT_CATEGORY ( VIREMENT_CATEGORY_ID,MODEL_ID,VIS_ID,DESCRIPTION,TRAN_LIMIT,TOTAL_LIMIT_IN,TOTAL_LIMIT_OUT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_VIREMENTGROUPNAME = "select count(*) from VIREMENT_CATEGORY where    MODEL_ID = ? AND VIS_ID = ? and not(    VIREMENT_CATEGORY_ID = ? )";
/*      */   protected static final String SQL_STORE = "update VIREMENT_CATEGORY set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,TRAN_LIMIT = ?,TOTAL_LIMIT_IN = ?,TOTAL_LIMIT_OUT = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    VIREMENT_CATEGORY_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from VIREMENT_CATEGORY where VIREMENT_CATEGORY_ID = ?";
/*  489 */   protected static String SQL_ALL_VIREMENT_CATEGORYS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID      ,VIREMENT_CATEGORY.VIS_ID      ,VIREMENT_CATEGORY.DESCRIPTION from VIREMENT_CATEGORY    ,MODEL where 1=1   and VIREMENT_CATEGORY.MODEL_ID = MODEL.MODEL_ID ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from VIREMENT_CATEGORY where    VIREMENT_CATEGORY_ID = ? ";
/*  735 */   private static String[][] SQL_DELETE_CHILDREN = { { "VIREMENT_LOCATION", "delete from VIREMENT_LOCATION where     VIREMENT_LOCATION.VIREMENT_CATEGORY_ID = ? " }, { "VIREMENT_ACCOUNT", "delete from VIREMENT_ACCOUNT where     VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID = ? " } };
/*      */ 
/*  749 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  753 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from VIREMENT_CATEGORY where 1=1 and VIREMENT_CATEGORY.MODEL_ID = ? order by  VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID";
/*      */   protected static final String SQL_GET_ALL = " from VIREMENT_CATEGORY where    MODEL_ID = ? ";
/*      */   protected VirementLocationDAO mVirementLocationDAO;
/*      */   protected VirementAccountDAO mVirementAccountDAO;
/*      */   protected VirementCategoryEVO mDetails;
/*      */ 
/*      */   public VirementCategoryDAO(Connection connection)
/*      */   {
/*   48 */     super(connection);
/*      */   }
/*      */ 
/*      */   public VirementCategoryDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public VirementCategoryDAO(DataSource ds)
/*      */   {
/*   64 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected VirementCategoryPK getPK()
/*      */   {
/*   72 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(VirementCategoryEVO details)
/*      */   {
/*   81 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private VirementCategoryEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  103 */     int col = 1;
/*  104 */     VirementCategoryEVO evo = new VirementCategoryEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getLong(col++), resultSet_.getLong(col++), resultSet_.getLong(col++), resultSet_.getInt(col++), null, null);
/*      */ 
/*  117 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  118 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  119 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  120 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(VirementCategoryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  125 */     int col = startCol_;
/*  126 */     stmt_.setInt(col++, evo_.getVirementCategoryId());
/*  127 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(VirementCategoryEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  132 */     int col = startCol_;
/*  133 */     stmt_.setInt(col++, evo_.getModelId());
/*  134 */     stmt_.setString(col++, evo_.getVisId());
/*  135 */     stmt_.setString(col++, evo_.getDescription());
/*  136 */     stmt_.setLong(col++, evo_.getTranLimit());
/*  137 */     stmt_.setLong(col++, evo_.getTotalLimitIn());
/*  138 */     stmt_.setLong(col++, evo_.getTotalLimitOut());
/*  139 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  140 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  141 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  142 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  143 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(VirementCategoryPK pk)
/*      */     throws ValidationException
/*      */   {
/*  159 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  161 */     PreparedStatement stmt = null;
/*  162 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  166 */       stmt = getConnection().prepareStatement("select VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID,VIREMENT_CATEGORY.MODEL_ID,VIREMENT_CATEGORY.VIS_ID,VIREMENT_CATEGORY.DESCRIPTION,VIREMENT_CATEGORY.TRAN_LIMIT,VIREMENT_CATEGORY.TOTAL_LIMIT_IN,VIREMENT_CATEGORY.TOTAL_LIMIT_OUT,VIREMENT_CATEGORY.VERSION_NUM,VIREMENT_CATEGORY.UPDATED_BY_USER_ID,VIREMENT_CATEGORY.UPDATED_TIME,VIREMENT_CATEGORY.CREATED_TIME from VIREMENT_CATEGORY where    VIREMENT_CATEGORY_ID = ? ");
/*      */ 
/*  169 */       int col = 1;
/*  170 */       stmt.setInt(col++, pk.getVirementCategoryId());
/*      */ 
/*  172 */       resultSet = stmt.executeQuery();
/*      */ 
/*  174 */       if (!resultSet.next()) {
/*  175 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  178 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  179 */       if (this.mDetails.isModified())
/*  180 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  184 */       throw handleSQLException(pk, "select VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID,VIREMENT_CATEGORY.MODEL_ID,VIREMENT_CATEGORY.VIS_ID,VIREMENT_CATEGORY.DESCRIPTION,VIREMENT_CATEGORY.TRAN_LIMIT,VIREMENT_CATEGORY.TOTAL_LIMIT_IN,VIREMENT_CATEGORY.TOTAL_LIMIT_OUT,VIREMENT_CATEGORY.VERSION_NUM,VIREMENT_CATEGORY.UPDATED_BY_USER_ID,VIREMENT_CATEGORY.UPDATED_TIME,VIREMENT_CATEGORY.CREATED_TIME from VIREMENT_CATEGORY where    VIREMENT_CATEGORY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  188 */       closeResultSet(resultSet);
/*  189 */       closeStatement(stmt);
/*  190 */       closeConnection();
/*      */ 
/*  192 */       if (timer != null)
/*  193 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  232 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  233 */     this.mDetails.postCreateInit();
/*      */ 
/*  235 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  240 */       duplicateValueCheckVirementGroupname();
/*      */ 
/*  242 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  243 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  244 */       stmt = getConnection().prepareStatement("insert into VIREMENT_CATEGORY ( VIREMENT_CATEGORY_ID,MODEL_ID,VIS_ID,DESCRIPTION,TRAN_LIMIT,TOTAL_LIMIT_IN,TOTAL_LIMIT_OUT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  247 */       int col = 1;
/*  248 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  249 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  252 */       int resultCount = stmt.executeUpdate();
/*  253 */       if (resultCount != 1)
/*      */       {
/*  255 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  258 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  262 */       throw handleSQLException(this.mDetails.getPK(), "insert into VIREMENT_CATEGORY ( VIREMENT_CATEGORY_ID,MODEL_ID,VIS_ID,DESCRIPTION,TRAN_LIMIT,TOTAL_LIMIT_IN,TOTAL_LIMIT_OUT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  266 */       closeStatement(stmt);
/*  267 */       closeConnection();
/*      */ 
/*  269 */       if (timer != null) {
/*  270 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  276 */       getVirementLocationDAO().update(this.mDetails.getVirementResponsibilityAreasMap());
/*      */ 
/*  278 */       getVirementAccountDAO().update(this.mDetails.getVirementAccountsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  284 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckVirementGroupname()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  300 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  301 */     PreparedStatement stmt = null;
/*  302 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  306 */       stmt = getConnection().prepareStatement("select count(*) from VIREMENT_CATEGORY where    MODEL_ID = ? AND VIS_ID = ? and not(    VIREMENT_CATEGORY_ID = ? )");
/*      */ 
/*  309 */       int col = 1;
/*  310 */       stmt.setInt(col++, this.mDetails.getModelId());
/*  311 */       stmt.setString(col++, this.mDetails.getVisId());
/*  312 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  315 */       resultSet = stmt.executeQuery();
/*      */ 
/*  317 */       if (!resultSet.next()) {
/*  318 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  322 */       col = 1;
/*  323 */       int count = resultSet.getInt(col++);
/*  324 */       if (count > 0) {
/*  325 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " VirementGroupname");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  331 */       throw handleSQLException(getPK(), "select count(*) from VIREMENT_CATEGORY where    MODEL_ID = ? AND VIS_ID = ? and not(    VIREMENT_CATEGORY_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  335 */       closeResultSet(resultSet);
/*  336 */       closeStatement(stmt);
/*  337 */       closeConnection();
/*      */ 
/*  339 */       if (timer != null)
/*  340 */         timer.logDebug("duplicateValueCheckVirementGroupname", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  369 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  373 */     PreparedStatement stmt = null;
/*      */ 
/*  375 */     boolean mainChanged = this.mDetails.isModified();
/*  376 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  380 */       if (mainChanged) {
/*  381 */         duplicateValueCheckVirementGroupname();
/*      */       }
/*  383 */       if (getVirementLocationDAO().update(this.mDetails.getVirementResponsibilityAreasMap())) {
/*  384 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  387 */       if (getVirementAccountDAO().update(this.mDetails.getVirementAccountsMap())) {
/*  388 */         dependantChanged = true;
/*      */       }
/*  390 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  393 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  396 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  397 */         stmt = getConnection().prepareStatement("update VIREMENT_CATEGORY set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,TRAN_LIMIT = ?,TOTAL_LIMIT_IN = ?,TOTAL_LIMIT_OUT = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    VIREMENT_CATEGORY_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  400 */         int col = 1;
/*  401 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  402 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  404 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  407 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  409 */         if (resultCount == 0) {
/*  410 */           checkVersionNum();
/*      */         }
/*  412 */         if (resultCount != 1) {
/*  413 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  416 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  425 */       throw handleSQLException(getPK(), "update VIREMENT_CATEGORY set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,TRAN_LIMIT = ?,TOTAL_LIMIT_IN = ?,TOTAL_LIMIT_OUT = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    VIREMENT_CATEGORY_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  429 */       closeStatement(stmt);
/*  430 */       closeConnection();
/*      */ 
/*  432 */       if ((timer != null) && (
/*  433 */         (mainChanged) || (dependantChanged)))
/*  434 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  446 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  447 */     PreparedStatement stmt = null;
/*  448 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  452 */       stmt = getConnection().prepareStatement("select VERSION_NUM from VIREMENT_CATEGORY where VIREMENT_CATEGORY_ID = ?");
/*      */ 
/*  455 */       int col = 1;
/*  456 */       stmt.setInt(col++, this.mDetails.getVirementCategoryId());
/*      */ 
/*  459 */       resultSet = stmt.executeQuery();
/*      */ 
/*  461 */       if (!resultSet.next()) {
/*  462 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  465 */       col = 1;
/*  466 */       int dbVersionNumber = resultSet.getInt(col++);
/*  467 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  468 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  474 */       throw handleSQLException(getPK(), "select VERSION_NUM from VIREMENT_CATEGORY where VIREMENT_CATEGORY_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  478 */       closeStatement(stmt);
/*  479 */       closeResultSet(resultSet);
/*      */ 
/*  481 */       if (timer != null)
/*  482 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllVirementCategorysELO getAllVirementCategorys()
/*      */   {
/*  518 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  519 */     PreparedStatement stmt = null;
/*  520 */     ResultSet resultSet = null;
/*  521 */     AllVirementCategorysELO results = new AllVirementCategorysELO();
/*      */     try
/*      */     {
/*  524 */       stmt = getConnection().prepareStatement(SQL_ALL_VIREMENT_CATEGORYS);
/*  525 */       int col = 1;
/*  526 */       resultSet = stmt.executeQuery();
/*  527 */       while (resultSet.next())
/*      */       {
/*  529 */         col = 2;
/*      */ 
/*  532 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  535 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  538 */         VirementCategoryPK pkVirementCategory = new VirementCategoryPK(resultSet.getInt(col++));
/*      */ 
/*  541 */         String textVirementCategory = resultSet.getString(col++);
/*      */ 
/*  546 */         VirementCategoryCK ckVirementCategory = new VirementCategoryCK(pkModel, pkVirementCategory);
/*      */ 
/*  552 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  558 */         VirementCategoryRefImpl erVirementCategory = new VirementCategoryRefImpl(ckVirementCategory, textVirementCategory);
/*      */ 
/*  563 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  566 */         results.add(erVirementCategory, erModel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  575 */       throw handleSQLException(SQL_ALL_VIREMENT_CATEGORYS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  579 */       closeResultSet(resultSet);
/*  580 */       closeStatement(stmt);
/*  581 */       closeConnection();
/*      */     }
/*      */ 
/*  584 */     if (timer != null) {
/*  585 */       timer.logDebug("getAllVirementCategorys", " items=" + results.size());
/*      */     }
/*      */ 
/*  589 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  606 */     if (items == null) {
/*  607 */       return false;
/*      */     }
/*  609 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  610 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  612 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  616 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  617 */       while (iter3.hasNext())
/*      */       {
/*  619 */         this.mDetails = ((VirementCategoryEVO)iter3.next());
/*  620 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  622 */         somethingChanged = true;
/*      */ 
/*  625 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  629 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  630 */       while (iter2.hasNext())
/*      */       {
/*  632 */         this.mDetails = ((VirementCategoryEVO)iter2.next());
/*      */ 
/*  635 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  637 */         somethingChanged = true;
/*      */ 
/*  640 */         if (deleteStmt == null) {
/*  641 */           deleteStmt = getConnection().prepareStatement("delete from VIREMENT_CATEGORY where    VIREMENT_CATEGORY_ID = ? ");
/*      */         }
/*      */ 
/*  644 */         int col = 1;
/*  645 */         deleteStmt.setInt(col++, this.mDetails.getVirementCategoryId());
/*      */ 
/*  647 */         if (this._log.isDebugEnabled()) {
/*  648 */           this._log.debug("update", "VirementCategory deleting VirementCategoryId=" + this.mDetails.getVirementCategoryId());
/*      */         }
/*      */ 
/*  653 */         deleteStmt.addBatch();
/*      */ 
/*  656 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  661 */       if (deleteStmt != null)
/*      */       {
/*  663 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  665 */         deleteStmt.executeBatch();
/*      */ 
/*  667 */         if (timer2 != null) {
/*  668 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  672 */       Iterator iter1 = items.values().iterator();
/*  673 */       while (iter1.hasNext())
/*      */       {
/*  675 */         this.mDetails = ((VirementCategoryEVO)iter1.next());
/*      */ 
/*  677 */         if (this.mDetails.insertPending())
/*      */         {
/*  679 */           somethingChanged = true;
/*  680 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  683 */         if (this.mDetails.isModified())
/*      */         {
/*  685 */           somethingChanged = true;
/*  686 */           doStore(); continue;
/*      */         }
/*      */ 
/*  690 */         if (this.mDetails.deletePending())
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  696 */         if (getVirementLocationDAO().update(this.mDetails.getVirementResponsibilityAreasMap())) {
/*  697 */           somethingChanged = true;
/*      */         }
/*      */ 
/*  700 */         if (getVirementAccountDAO().update(this.mDetails.getVirementAccountsMap())) {
/*  701 */           somethingChanged = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  713 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  717 */       throw handleSQLException("delete from VIREMENT_CATEGORY where    VIREMENT_CATEGORY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  721 */       if (deleteStmt != null)
/*      */       {
/*  723 */         closeStatement(deleteStmt);
/*  724 */         closeConnection();
/*      */       }
/*      */ 
/*  727 */       this.mDetails = null;
/*      */ 
/*  729 */       if ((somethingChanged) && 
/*  730 */         (timer != null))
/*  731 */         timer.logDebug("update", "collection"); 
/*  731 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(VirementCategoryPK pk)
/*      */   {
/*  762 */     Set emptyStrings = Collections.emptySet();
/*  763 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(VirementCategoryPK pk, Set<String> exclusionTables)
/*      */   {
/*  769 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  771 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  773 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  775 */       PreparedStatement stmt = null;
/*      */ 
/*  777 */       int resultCount = 0;
/*  778 */       String s = null;
/*      */       try
/*      */       {
/*  781 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  783 */         if (this._log.isDebugEnabled()) {
/*  784 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  786 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  789 */         int col = 1;
/*  790 */         stmt.setInt(col++, pk.getVirementCategoryId());
/*      */ 
/*  793 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  797 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  801 */         closeStatement(stmt);
/*  802 */         closeConnection();
/*      */ 
/*  804 */         if (timer != null) {
/*  805 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  809 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  811 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  813 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  815 */       PreparedStatement stmt = null;
/*      */ 
/*  817 */       int resultCount = 0;
/*  818 */       String s = null;
/*      */       try
/*      */       {
/*  821 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  823 */         if (this._log.isDebugEnabled()) {
/*  824 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  826 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  829 */         int col = 1;
/*  830 */         stmt.setInt(col++, pk.getVirementCategoryId());
/*      */ 
/*  833 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  837 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  841 */         closeStatement(stmt);
/*  842 */         closeConnection();
/*      */ 
/*  844 */         if (timer != null)
/*  845 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants)
/*      */   {
/*  865 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  867 */     PreparedStatement stmt = null;
/*  868 */     ResultSet resultSet = null;
/*      */ 
/*  870 */     int itemCount = 0;
/*      */ 
/*  872 */     Collection theseItems = new ArrayList();
/*  873 */     owningEVO.setVirementGroups(theseItems);
/*  874 */     owningEVO.setVirementGroupsAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  878 */       stmt = getConnection().prepareStatement("select VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID,VIREMENT_CATEGORY.MODEL_ID,VIREMENT_CATEGORY.VIS_ID,VIREMENT_CATEGORY.DESCRIPTION,VIREMENT_CATEGORY.TRAN_LIMIT,VIREMENT_CATEGORY.TOTAL_LIMIT_IN,VIREMENT_CATEGORY.TOTAL_LIMIT_OUT,VIREMENT_CATEGORY.VERSION_NUM,VIREMENT_CATEGORY.UPDATED_BY_USER_ID,VIREMENT_CATEGORY.UPDATED_TIME,VIREMENT_CATEGORY.CREATED_TIME from VIREMENT_CATEGORY where 1=1 and VIREMENT_CATEGORY.MODEL_ID = ? order by  VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID");
/*      */ 
/*  880 */       int col = 1;
/*  881 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  883 */       resultSet = stmt.executeQuery();
/*      */ 
/*  886 */       while (resultSet.next())
/*      */       {
/*  888 */         itemCount++;
/*  889 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  891 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  894 */       if (timer != null) {
/*  895 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  898 */       if ((itemCount > 0) && (dependants.indexOf("<23>") > -1))
/*      */       {
/*  900 */         getVirementLocationDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*  902 */       if ((itemCount > 0) && (dependants.indexOf("<24>") > -1))
/*      */       {
/*  904 */         getVirementAccountDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  908 */       throw handleSQLException("select VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID,VIREMENT_CATEGORY.MODEL_ID,VIREMENT_CATEGORY.VIS_ID,VIREMENT_CATEGORY.DESCRIPTION,VIREMENT_CATEGORY.TRAN_LIMIT,VIREMENT_CATEGORY.TOTAL_LIMIT_IN,VIREMENT_CATEGORY.TOTAL_LIMIT_OUT,VIREMENT_CATEGORY.VERSION_NUM,VIREMENT_CATEGORY.UPDATED_BY_USER_ID,VIREMENT_CATEGORY.UPDATED_TIME,VIREMENT_CATEGORY.CREATED_TIME from VIREMENT_CATEGORY where 1=1 and VIREMENT_CATEGORY.MODEL_ID = ? order by  VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  912 */       closeResultSet(resultSet);
/*  913 */       closeStatement(stmt);
/*  914 */       closeConnection();
/*      */ 
/*  916 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectModelId, String dependants, Collection currentList)
/*      */   {
/*  941 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  942 */     PreparedStatement stmt = null;
/*  943 */     ResultSet resultSet = null;
/*      */ 
/*  945 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  949 */       stmt = getConnection().prepareStatement("select VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID,VIREMENT_CATEGORY.MODEL_ID,VIREMENT_CATEGORY.VIS_ID,VIREMENT_CATEGORY.DESCRIPTION,VIREMENT_CATEGORY.TRAN_LIMIT,VIREMENT_CATEGORY.TOTAL_LIMIT_IN,VIREMENT_CATEGORY.TOTAL_LIMIT_OUT,VIREMENT_CATEGORY.VERSION_NUM,VIREMENT_CATEGORY.UPDATED_BY_USER_ID,VIREMENT_CATEGORY.UPDATED_TIME,VIREMENT_CATEGORY.CREATED_TIME from VIREMENT_CATEGORY where    MODEL_ID = ? ");
/*      */ 
/*  951 */       int col = 1;
/*  952 */       stmt.setInt(col++, selectModelId);
/*      */ 
/*  954 */       resultSet = stmt.executeQuery();
/*      */ 
/*  956 */       while (resultSet.next())
/*      */       {
/*  958 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  961 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  964 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  967 */       if (currentList != null)
/*      */       {
/*  970 */         ListIterator iter = items.listIterator();
/*  971 */         VirementCategoryEVO currentEVO = null;
/*  972 */         VirementCategoryEVO newEVO = null;
/*  973 */         while (iter.hasNext())
/*      */         {
/*  975 */           newEVO = (VirementCategoryEVO)iter.next();
/*  976 */           Iterator iter2 = currentList.iterator();
/*  977 */           while (iter2.hasNext())
/*      */           {
/*  979 */             currentEVO = (VirementCategoryEVO)iter2.next();
/*  980 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  982 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  988 */         Iterator iter2 = currentList.iterator();
/*  989 */         while (iter2.hasNext())
/*      */         {
/*  991 */           currentEVO = (VirementCategoryEVO)iter2.next();
/*  992 */           if (currentEVO.insertPending()) {
/*  993 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  997 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1001 */       throw handleSQLException("select VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID,VIREMENT_CATEGORY.MODEL_ID,VIREMENT_CATEGORY.VIS_ID,VIREMENT_CATEGORY.DESCRIPTION,VIREMENT_CATEGORY.TRAN_LIMIT,VIREMENT_CATEGORY.TOTAL_LIMIT_IN,VIREMENT_CATEGORY.TOTAL_LIMIT_OUT,VIREMENT_CATEGORY.VERSION_NUM,VIREMENT_CATEGORY.UPDATED_BY_USER_ID,VIREMENT_CATEGORY.UPDATED_TIME,VIREMENT_CATEGORY.CREATED_TIME from VIREMENT_CATEGORY where    MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1005 */       closeResultSet(resultSet);
/* 1006 */       closeStatement(stmt);
/* 1007 */       closeConnection();
/*      */ 
/* 1009 */       if (timer != null) {
/* 1010 */         timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1015 */     return items;
/*      */   }
/*      */ 
/*      */   public VirementCategoryEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1034 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1037 */     if (this.mDetails == null) {
/* 1038 */       doLoad(((VirementCategoryCK)paramCK).getVirementCategoryPK());
/*      */     }
/* 1040 */     else if (!this.mDetails.getPK().equals(((VirementCategoryCK)paramCK).getVirementCategoryPK())) {
/* 1041 */       doLoad(((VirementCategoryCK)paramCK).getVirementCategoryPK());
/*      */     }
/*      */ 
/* 1044 */     if ((dependants.indexOf("<23>") > -1) && (!this.mDetails.isVirementResponsibilityAreasAllItemsLoaded()))
/*      */     {
/* 1049 */       this.mDetails.setVirementResponsibilityAreas(getVirementLocationDAO().getAll(this.mDetails.getVirementCategoryId(), dependants, this.mDetails.getVirementResponsibilityAreas()));
/*      */ 
/* 1056 */       this.mDetails.setVirementResponsibilityAreasAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1060 */     if ((dependants.indexOf("<24>") > -1) && (!this.mDetails.isVirementAccountsAllItemsLoaded()))
/*      */     {
/* 1065 */       this.mDetails.setVirementAccounts(getVirementAccountDAO().getAll(this.mDetails.getVirementCategoryId(), dependants, this.mDetails.getVirementAccounts()));
/*      */ 
/* 1072 */       this.mDetails.setVirementAccountsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1075 */     if ((paramCK instanceof VirementLocationCK))
/*      */     {
/* 1077 */       if (this.mDetails.getVirementResponsibilityAreas() == null) {
/* 1078 */         this.mDetails.loadVirementResponsibilityAreasItem(getVirementLocationDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1081 */         VirementLocationPK pk = ((VirementLocationCK)paramCK).getVirementLocationPK();
/* 1082 */         VirementLocationEVO evo = this.mDetails.getVirementResponsibilityAreasItem(pk);
/* 1083 */         if (evo == null) {
/* 1084 */           this.mDetails.loadVirementResponsibilityAreasItem(getVirementLocationDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/* 1088 */     else if ((paramCK instanceof VirementAccountCK))
/*      */     {
/* 1090 */       if (this.mDetails.getVirementAccounts() == null) {
/* 1091 */         this.mDetails.loadVirementAccountsItem(getVirementAccountDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1094 */         VirementAccountPK pk = ((VirementAccountCK)paramCK).getVirementAccountPK();
/* 1095 */         VirementAccountEVO evo = this.mDetails.getVirementAccountsItem(pk);
/* 1096 */         if (evo == null) {
/* 1097 */           this.mDetails.loadVirementAccountsItem(getVirementAccountDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1102 */     VirementCategoryEVO details = new VirementCategoryEVO();
/* 1103 */     details = this.mDetails.deepClone();
/*      */ 
/* 1105 */     if (timer != null) {
/* 1106 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1108 */     return details;
/*      */   }
/*      */ 
/*      */   public VirementCategoryEVO getDetails(ModelCK paramCK, VirementCategoryEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1114 */     VirementCategoryEVO savedEVO = this.mDetails;
/* 1115 */     this.mDetails = paramEVO;
/* 1116 */     VirementCategoryEVO newEVO = getDetails(paramCK, dependants);
/* 1117 */     this.mDetails = savedEVO;
/* 1118 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public VirementCategoryEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1124 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1128 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1131 */     VirementCategoryEVO details = this.mDetails.deepClone();
/*      */ 
/* 1133 */     if (timer != null) {
/* 1134 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1136 */     return details;
/*      */   }
/*      */ 
/*      */   protected VirementLocationDAO getVirementLocationDAO()
/*      */   {
/* 1145 */     if (this.mVirementLocationDAO == null)
/*      */     {
/* 1147 */       if (this.mDataSource != null)
/* 1148 */         this.mVirementLocationDAO = new VirementLocationDAO(this.mDataSource);
/*      */       else {
/* 1150 */         this.mVirementLocationDAO = new VirementLocationDAO(getConnection());
/*      */       }
/*      */     }
/* 1153 */     return this.mVirementLocationDAO;
/*      */   }
/*      */ 
/*      */   protected VirementAccountDAO getVirementAccountDAO()
/*      */   {
/* 1162 */     if (this.mVirementAccountDAO == null)
/*      */     {
/* 1164 */       if (this.mDataSource != null)
/* 1165 */         this.mVirementAccountDAO = new VirementAccountDAO(this.mDataSource);
/*      */       else {
/* 1167 */         this.mVirementAccountDAO = new VirementAccountDAO(getConnection());
/*      */       }
/*      */     }
/* 1170 */     return this.mVirementAccountDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1175 */     return "VirementCategory";
/*      */   }
/*      */ 
/*      */   public VirementCategoryRefImpl getRef(VirementCategoryPK paramVirementCategoryPK)
/*      */   {
/* 1180 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1181 */     PreparedStatement stmt = null;
/* 1182 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1185 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,VIREMENT_CATEGORY.VIS_ID from VIREMENT_CATEGORY,MODEL where 1=1 and VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID = ? and VIREMENT_CATEGORY.MODEL_ID = MODEL.MODEL_ID");
/* 1186 */       int col = 1;
/* 1187 */       stmt.setInt(col++, paramVirementCategoryPK.getVirementCategoryId());
/*      */ 
/* 1189 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1191 */       if (!resultSet.next()) {
/* 1192 */         throw new RuntimeException(getEntityName() + " getRef " + paramVirementCategoryPK + " not found");
/*      */       }
/* 1194 */       col = 2;
/* 1195 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1199 */       String textVirementCategory = resultSet.getString(col++);
/* 1200 */       VirementCategoryCK ckVirementCategory = new VirementCategoryCK(newModelPK, paramVirementCategoryPK);
/*      */ 
/* 1205 */       VirementCategoryRefImpl localVirementCategoryRefImpl = new VirementCategoryRefImpl(ckVirementCategory, textVirementCategory);
/*      */       return localVirementCategoryRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1210 */       throw handleSQLException(paramVirementCategoryPK, "select 0,MODEL.MODEL_ID,VIREMENT_CATEGORY.VIS_ID from VIREMENT_CATEGORY,MODEL where 1=1 and VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID = ? and VIREMENT_CATEGORY.MODEL_ID = MODEL.MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1214 */       closeResultSet(resultSet);
/* 1215 */       closeStatement(stmt);
/* 1216 */       closeConnection();
/*      */ 
/* 1218 */       if (timer != null)
/* 1219 */         timer.logDebug("getRef", paramVirementCategoryPK); 
/* 1219 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1231 */     if (c == null)
/* 1232 */       return;
/* 1233 */     Iterator iter = c.iterator();
/* 1234 */     while (iter.hasNext())
/*      */     {
/* 1236 */       VirementCategoryEVO evo = (VirementCategoryEVO)iter.next();
/* 1237 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(VirementCategoryEVO evo, String dependants)
/*      */   {
/* 1251 */     if (evo.insertPending()) {
/* 1252 */       return;
/*      */     }
/* 1254 */     if (evo.getVirementCategoryId() < 1) {
/* 1255 */       return;
/*      */     }
/*      */ 
/* 1259 */     if (dependants.indexOf("<23>") > -1)
/*      */     {
/* 1262 */       if (!evo.isVirementResponsibilityAreasAllItemsLoaded())
/*      */       {
/* 1264 */         evo.setVirementResponsibilityAreas(getVirementLocationDAO().getAll(evo.getVirementCategoryId(), dependants, evo.getVirementResponsibilityAreas()));
/*      */ 
/* 1271 */         evo.setVirementResponsibilityAreasAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1276 */     if (dependants.indexOf("<24>") > -1)
/*      */     {
/* 1279 */       if (!evo.isVirementAccountsAllItemsLoaded())
/*      */       {
/* 1281 */         evo.setVirementAccounts(getVirementAccountDAO().getAll(evo.getVirementCategoryId(), dependants, evo.getVirementAccounts()));
/*      */ 
/* 1288 */         evo.setVirementAccountsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.virement.VirementCategoryDAO
 * JD-Core Version:    0.6.0
 */