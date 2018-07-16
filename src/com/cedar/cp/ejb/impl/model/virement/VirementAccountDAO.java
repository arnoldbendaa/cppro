/*      */ package com.cedar.cp.ejb.impl.model.virement;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.dimension.StructureElementPK;
/*      */ import com.cedar.cp.dto.dimension.StructureElementRefImpl;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.virement.AccountsForCategoryELO;
/*      */ import com.cedar.cp.dto.model.virement.VirementAccountCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementAccountPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementAccountRefImpl;
/*      */ import com.cedar.cp.dto.model.virement.VirementCategoryCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementCategoryPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementCategoryRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class VirementAccountDAO extends AbstractDAO
/*      */ {
/*   38 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID,VIREMENT_ACCOUNT.STRUCTURE_ID,VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID,VIREMENT_ACCOUNT.TRAN_LIMIT,VIREMENT_ACCOUNT.TOTAL_LIMIT_IN,VIREMENT_ACCOUNT.TOTAL_LIMIT_OUT,VIREMENT_ACCOUNT.IN_FLAG,VIREMENT_ACCOUNT.OUT_FLAG,VIREMENT_ACCOUNT.VERSION_NUM,VIREMENT_ACCOUNT.UPDATED_BY_USER_ID,VIREMENT_ACCOUNT.UPDATED_TIME,VIREMENT_ACCOUNT.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from VIREMENT_ACCOUNT where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into VIREMENT_ACCOUNT ( VIREMENT_CATEGORY_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,TRAN_LIMIT,TOTAL_LIMIT_IN,TOTAL_LIMIT_OUT,IN_FLAG,OUT_FLAG,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update VIREMENT_ACCOUNT set TRAN_LIMIT = ?,TOTAL_LIMIT_IN = ?,TOTAL_LIMIT_OUT = ?,IN_FLAG = ?,OUT_FLAG = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from VIREMENT_ACCOUNT where VIREMENT_CATEGORY_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?";
/*  420 */   protected static String SQL_ACCOUNTS_FOR_CATEGORY = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID      ,VIREMENT_CATEGORY.VIS_ID      ,VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID      ,VIREMENT_ACCOUNT.STRUCTURE_ID      ,VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,VIREMENT_ACCOUNT.TRAN_LIMIT      ,VIREMENT_ACCOUNT.TOTAL_LIMIT_IN      ,VIREMENT_ACCOUNT.TOTAL_LIMIT_OUT      ,VIREMENT_ACCOUNT.IN_FLAG      ,VIREMENT_ACCOUNT.OUT_FLAG from VIREMENT_ACCOUNT    ,MODEL    ,VIREMENT_CATEGORY    ,STRUCTURE_ELEMENT where 1=1   and VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID = VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID   and VIREMENT_CATEGORY.MODEL_ID = MODEL.MODEL_ID  and  VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID = ? and VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID = STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from VIREMENT_ACCOUNT where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*      */   public static final String SQL_BULK_GET_ALL = " from VIREMENT_ACCOUNT,VIREMENT_CATEGORY where 1=1 and VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID = VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID and VIREMENT_CATEGORY.MODEL_ID = ? order by  VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID ,VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID ,VIREMENT_ACCOUNT.STRUCTURE_ID ,VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID";
/*      */   protected static final String SQL_GET_ALL = " from VIREMENT_ACCOUNT where    VIREMENT_CATEGORY_ID = ? ";
/*      */   private static final String ERASE_ORPHANS_SQL = "delete from virement_account va \nwhere va.rowid in \n( \n\tselect va.rowid \n\tfrom virement_account va, \n\t     virement_category vc \n\twhere vc.model_id = ? and \n\t      vc.virement_category_id = va.virement_category_id and \n\t      va.structure_element_id not in ( select sev.structure_element_id \n\t\t\t\t\t\t\t\t\t\t   from structure_element_view sev \n\t\t\t\t\t\t\t\t\t\t   where sev.structure_id = va.structure_id ) \n)";
/*      */   protected VirementAccountEVO mDetails;
/*      */ 
/*      */   public VirementAccountDAO(Connection connection)
/*      */   {
/*   45 */     super(connection);
/*      */   }
/*      */ 
/*      */   public VirementAccountDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public VirementAccountDAO(DataSource ds)
/*      */   {
/*   61 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected VirementAccountPK getPK()
/*      */   {
/*   69 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(VirementAccountEVO details)
/*      */   {
/*   78 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private VirementAccountEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  101 */     int col = 1;
/*  102 */     VirementAccountEVO evo = new VirementAccountEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getLong(col++), resultSet_.getLong(col++), resultSet_.getLong(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++));
/*      */ 
/*  114 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  115 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  116 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  117 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(VirementAccountEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  122 */     int col = startCol_;
/*  123 */     stmt_.setInt(col++, evo_.getVirementCategoryId());
/*  124 */     stmt_.setInt(col++, evo_.getStructureId());
/*  125 */     stmt_.setInt(col++, evo_.getStructureElementId());
/*  126 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(VirementAccountEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  131 */     int col = startCol_;
/*  132 */     stmt_.setLong(col++, evo_.getTranLimit());
/*  133 */     stmt_.setLong(col++, evo_.getTotalLimitIn());
/*  134 */     stmt_.setLong(col++, evo_.getTotalLimitOut());
/*  135 */     if (evo_.getInFlag())
/*  136 */       stmt_.setString(col++, "Y");
/*      */     else
/*  138 */       stmt_.setString(col++, " ");
/*  139 */     if (evo_.getOutFlag())
/*  140 */       stmt_.setString(col++, "Y");
/*      */     else
/*  142 */       stmt_.setString(col++, " ");
/*  143 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  144 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  145 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  146 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  147 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(VirementAccountPK pk)
/*      */     throws ValidationException
/*      */   {
/*  165 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  167 */     PreparedStatement stmt = null;
/*  168 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  172 */       stmt = getConnection().prepareStatement("select VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID,VIREMENT_ACCOUNT.STRUCTURE_ID,VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID,VIREMENT_ACCOUNT.TRAN_LIMIT,VIREMENT_ACCOUNT.TOTAL_LIMIT_IN,VIREMENT_ACCOUNT.TOTAL_LIMIT_OUT,VIREMENT_ACCOUNT.IN_FLAG,VIREMENT_ACCOUNT.OUT_FLAG,VIREMENT_ACCOUNT.VERSION_NUM,VIREMENT_ACCOUNT.UPDATED_BY_USER_ID,VIREMENT_ACCOUNT.UPDATED_TIME,VIREMENT_ACCOUNT.CREATED_TIME from VIREMENT_ACCOUNT where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*      */ 
/*  175 */       int col = 1;
/*  176 */       stmt.setInt(col++, pk.getVirementCategoryId());
/*  177 */       stmt.setInt(col++, pk.getStructureId());
/*  178 */       stmt.setInt(col++, pk.getStructureElementId());
/*      */ 
/*  180 */       resultSet = stmt.executeQuery();
/*      */ 
/*  182 */       if (!resultSet.next()) {
/*  183 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  186 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  187 */       if (this.mDetails.isModified())
/*  188 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  192 */       throw handleSQLException(pk, "select VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID,VIREMENT_ACCOUNT.STRUCTURE_ID,VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID,VIREMENT_ACCOUNT.TRAN_LIMIT,VIREMENT_ACCOUNT.TOTAL_LIMIT_IN,VIREMENT_ACCOUNT.TOTAL_LIMIT_OUT,VIREMENT_ACCOUNT.IN_FLAG,VIREMENT_ACCOUNT.OUT_FLAG,VIREMENT_ACCOUNT.VERSION_NUM,VIREMENT_ACCOUNT.UPDATED_BY_USER_ID,VIREMENT_ACCOUNT.UPDATED_TIME,VIREMENT_ACCOUNT.CREATED_TIME from VIREMENT_ACCOUNT where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  196 */       closeResultSet(resultSet);
/*  197 */       closeStatement(stmt);
/*  198 */       closeConnection();
/*      */ 
/*  200 */       if (timer != null)
/*  201 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  242 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  243 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  248 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  249 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  250 */       stmt = getConnection().prepareStatement("insert into VIREMENT_ACCOUNT ( VIREMENT_CATEGORY_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,TRAN_LIMIT,TOTAL_LIMIT_IN,TOTAL_LIMIT_OUT,IN_FLAG,OUT_FLAG,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  253 */       int col = 1;
/*  254 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  255 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  258 */       int resultCount = stmt.executeUpdate();
/*  259 */       if (resultCount != 1)
/*      */       {
/*  261 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  264 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  268 */       throw handleSQLException(this.mDetails.getPK(), "insert into VIREMENT_ACCOUNT ( VIREMENT_CATEGORY_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,TRAN_LIMIT,TOTAL_LIMIT_IN,TOTAL_LIMIT_OUT,IN_FLAG,OUT_FLAG,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  272 */       closeStatement(stmt);
/*  273 */       closeConnection();
/*      */ 
/*  275 */       if (timer != null)
/*  276 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  307 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  311 */     PreparedStatement stmt = null;
/*      */ 
/*  313 */     boolean mainChanged = this.mDetails.isModified();
/*  314 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  317 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  320 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  323 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  324 */         stmt = getConnection().prepareStatement("update VIREMENT_ACCOUNT set TRAN_LIMIT = ?,TOTAL_LIMIT_IN = ?,TOTAL_LIMIT_OUT = ?,IN_FLAG = ?,OUT_FLAG = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  327 */         int col = 1;
/*  328 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  329 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  331 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  334 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  336 */         if (resultCount == 0) {
/*  337 */           checkVersionNum();
/*      */         }
/*  339 */         if (resultCount != 1) {
/*  340 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  343 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  352 */       throw handleSQLException(getPK(), "update VIREMENT_ACCOUNT set TRAN_LIMIT = ?,TOTAL_LIMIT_IN = ?,TOTAL_LIMIT_OUT = ?,IN_FLAG = ?,OUT_FLAG = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  356 */       closeStatement(stmt);
/*  357 */       closeConnection();
/*      */ 
/*  359 */       if ((timer != null) && (
/*  360 */         (mainChanged) || (dependantChanged)))
/*  361 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  375 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  376 */     PreparedStatement stmt = null;
/*  377 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  381 */       stmt = getConnection().prepareStatement("select VERSION_NUM from VIREMENT_ACCOUNT where VIREMENT_CATEGORY_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?");
/*      */ 
/*  384 */       int col = 1;
/*  385 */       stmt.setInt(col++, this.mDetails.getVirementCategoryId());
/*  386 */       stmt.setInt(col++, this.mDetails.getStructureId());
/*  387 */       stmt.setInt(col++, this.mDetails.getStructureElementId());
/*      */ 
/*  390 */       resultSet = stmt.executeQuery();
/*      */ 
/*  392 */       if (!resultSet.next()) {
/*  393 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  396 */       col = 1;
/*  397 */       int dbVersionNumber = resultSet.getInt(col++);
/*  398 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  399 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  405 */       throw handleSQLException(getPK(), "select VERSION_NUM from VIREMENT_ACCOUNT where VIREMENT_CATEGORY_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  409 */       closeStatement(stmt);
/*  410 */       closeResultSet(resultSet);
/*      */ 
/*  412 */       if (timer != null)
/*  413 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AccountsForCategoryELO getAccountsForCategory(int param1)
/*      */   {
/*  469 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  470 */     PreparedStatement stmt = null;
/*  471 */     ResultSet resultSet = null;
/*  472 */     AccountsForCategoryELO results = new AccountsForCategoryELO();
/*      */     try
/*      */     {
/*  475 */       stmt = getConnection().prepareStatement(SQL_ACCOUNTS_FOR_CATEGORY);
/*  476 */       int col = 1;
/*  477 */       stmt.setInt(col++, param1);
/*  478 */       resultSet = stmt.executeQuery();
/*  479 */       while (resultSet.next())
/*      */       {
/*  481 */         col = 2;
/*      */ 
/*  484 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  487 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  489 */         VirementCategoryPK pkVirementCategory = new VirementCategoryPK(resultSet.getInt(col++));
/*      */ 
/*  492 */         String textVirementCategory = resultSet.getString(col++);
/*      */ 
/*  495 */         VirementAccountPK pkVirementAccount = new VirementAccountPK(resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  500 */         String textVirementAccount = "";
/*      */ 
/*  503 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  507 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/*  511 */         VirementCategoryCK ckVirementCategory = new VirementCategoryCK(pkModel, pkVirementCategory);
/*      */ 
/*  517 */         VirementAccountCK ckVirementAccount = new VirementAccountCK(pkModel, pkVirementCategory, pkVirementAccount);
/*      */ 
/*  524 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  530 */         VirementCategoryRefImpl erVirementCategory = new VirementCategoryRefImpl(ckVirementCategory, textVirementCategory);
/*      */ 
/*  536 */         VirementAccountRefImpl erVirementAccount = new VirementAccountRefImpl(ckVirementAccount, textVirementAccount);
/*      */ 
/*  542 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/*  547 */         String col1 = resultSet.getString(col++);
/*  548 */         String col2 = resultSet.getString(col++);
/*  549 */         long col3 = resultSet.getLong(col++);
/*  550 */         long col4 = resultSet.getLong(col++);
/*  551 */         long col5 = resultSet.getLong(col++);
/*  552 */         String col6 = resultSet.getString(col++);
/*  553 */         if (resultSet.wasNull())
/*  554 */           col6 = "";
/*  555 */         String col7 = resultSet.getString(col++);
/*  556 */         if (resultSet.wasNull()) {
/*  557 */           col7 = "";
/*      */         }
/*      */ 
/*  560 */         results.add(erVirementAccount, erVirementCategory, erModel, erStructureElement, col1, col2, col3, col4, col5, col6.equals("Y"), col7.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  577 */       throw handleSQLException(SQL_ACCOUNTS_FOR_CATEGORY, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  581 */       closeResultSet(resultSet);
/*  582 */       closeStatement(stmt);
/*  583 */       closeConnection();
/*      */     }
/*      */ 
/*  586 */     if (timer != null) {
/*  587 */       timer.logDebug("getAccountsForCategory", " VirementCategoryId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  592 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  611 */     if (items == null) {
/*  612 */       return false;
/*      */     }
/*  614 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  615 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  617 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  622 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  623 */       while (iter2.hasNext())
/*      */       {
/*  625 */         this.mDetails = ((VirementAccountEVO)iter2.next());
/*      */ 
/*  628 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  630 */         somethingChanged = true;
/*      */ 
/*  633 */         if (deleteStmt == null) {
/*  634 */           deleteStmt = getConnection().prepareStatement("delete from VIREMENT_ACCOUNT where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*      */         }
/*      */ 
/*  637 */         int col = 1;
/*  638 */         deleteStmt.setInt(col++, this.mDetails.getVirementCategoryId());
/*  639 */         deleteStmt.setInt(col++, this.mDetails.getStructureId());
/*  640 */         deleteStmt.setInt(col++, this.mDetails.getStructureElementId());
/*      */ 
/*  642 */         if (this._log.isDebugEnabled()) {
/*  643 */           this._log.debug("update", "VirementAccount deleting VirementCategoryId=" + this.mDetails.getVirementCategoryId() + ",StructureId=" + this.mDetails.getStructureId() + ",StructureElementId=" + this.mDetails.getStructureElementId());
/*      */         }
/*      */ 
/*  650 */         deleteStmt.addBatch();
/*      */ 
/*  653 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  658 */       if (deleteStmt != null)
/*      */       {
/*  660 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  662 */         deleteStmt.executeBatch();
/*      */ 
/*  664 */         if (timer2 != null) {
/*  665 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  669 */       Iterator iter1 = items.values().iterator();
/*  670 */       while (iter1.hasNext())
/*      */       {
/*  672 */         this.mDetails = ((VirementAccountEVO)iter1.next());
/*      */ 
/*  674 */         if (this.mDetails.insertPending())
/*      */         {
/*  676 */           somethingChanged = true;
/*  677 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  680 */         if (!this.mDetails.isModified())
/*      */           continue;
/*  682 */         somethingChanged = true;
/*  683 */         doStore();
/*      */       }
/*      */ 
/*  694 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  698 */       throw handleSQLException("delete from VIREMENT_ACCOUNT where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  702 */       if (deleteStmt != null)
/*      */       {
/*  704 */         closeStatement(deleteStmt);
/*  705 */         closeConnection();
/*      */       }
/*      */ 
/*  708 */       this.mDetails = null;
/*      */ 
/*  710 */       if ((somethingChanged) && 
/*  711 */         (timer != null))
/*  712 */         timer.logDebug("update", "collection"); 
/*  712 */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*      */   {
/*  737 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  739 */     PreparedStatement stmt = null;
/*  740 */     ResultSet resultSet = null;
/*      */ 
/*  742 */     int itemCount = 0;
/*      */ 
/*  744 */     VirementCategoryEVO owningEVO = null;
/*  745 */     Iterator ownersIter = owners.iterator();
/*  746 */     while (ownersIter.hasNext())
/*      */     {
/*  748 */       owningEVO = (VirementCategoryEVO)ownersIter.next();
/*  749 */       owningEVO.setVirementAccountsAllItemsLoaded(true);
/*      */     }
/*  751 */     ownersIter = owners.iterator();
/*  752 */     owningEVO = (VirementCategoryEVO)ownersIter.next();
/*  753 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  757 */       stmt = getConnection().prepareStatement("select VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID,VIREMENT_ACCOUNT.STRUCTURE_ID,VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID,VIREMENT_ACCOUNT.TRAN_LIMIT,VIREMENT_ACCOUNT.TOTAL_LIMIT_IN,VIREMENT_ACCOUNT.TOTAL_LIMIT_OUT,VIREMENT_ACCOUNT.IN_FLAG,VIREMENT_ACCOUNT.OUT_FLAG,VIREMENT_ACCOUNT.VERSION_NUM,VIREMENT_ACCOUNT.UPDATED_BY_USER_ID,VIREMENT_ACCOUNT.UPDATED_TIME,VIREMENT_ACCOUNT.CREATED_TIME from VIREMENT_ACCOUNT,VIREMENT_CATEGORY where 1=1 and VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID = VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID and VIREMENT_CATEGORY.MODEL_ID = ? order by  VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID ,VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID ,VIREMENT_ACCOUNT.STRUCTURE_ID ,VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID");
/*      */ 
/*  759 */       int col = 1;
/*  760 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  762 */       resultSet = stmt.executeQuery();
/*      */ 
/*  765 */       while (resultSet.next())
/*      */       {
/*  767 */         itemCount++;
/*  768 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  773 */         while (this.mDetails.getVirementCategoryId() != owningEVO.getVirementCategoryId())
/*      */         {
/*  777 */           if (!ownersIter.hasNext())
/*      */           {
/*  779 */             this._log.debug("bulkGetAll", "can't find owning [VirementCategoryId=" + this.mDetails.getVirementCategoryId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  783 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  784 */             ownersIter = owners.iterator();
/*  785 */             while (ownersIter.hasNext())
/*      */             {
/*  787 */               owningEVO = (VirementCategoryEVO)ownersIter.next();
/*  788 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  790 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  792 */           owningEVO = (VirementCategoryEVO)ownersIter.next();
/*      */         }
/*  794 */         if (owningEVO.getVirementAccounts() == null)
/*      */         {
/*  796 */           theseItems = new ArrayList();
/*  797 */           owningEVO.setVirementAccounts(theseItems);
/*  798 */           owningEVO.setVirementAccountsAllItemsLoaded(true);
/*      */         }
/*  800 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  803 */       if (timer != null) {
/*  804 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  809 */       throw handleSQLException("select VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID,VIREMENT_ACCOUNT.STRUCTURE_ID,VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID,VIREMENT_ACCOUNT.TRAN_LIMIT,VIREMENT_ACCOUNT.TOTAL_LIMIT_IN,VIREMENT_ACCOUNT.TOTAL_LIMIT_OUT,VIREMENT_ACCOUNT.IN_FLAG,VIREMENT_ACCOUNT.OUT_FLAG,VIREMENT_ACCOUNT.VERSION_NUM,VIREMENT_ACCOUNT.UPDATED_BY_USER_ID,VIREMENT_ACCOUNT.UPDATED_TIME,VIREMENT_ACCOUNT.CREATED_TIME from VIREMENT_ACCOUNT,VIREMENT_CATEGORY where 1=1 and VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID = VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID and VIREMENT_CATEGORY.MODEL_ID = ? order by  VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID ,VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID ,VIREMENT_ACCOUNT.STRUCTURE_ID ,VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  813 */       closeResultSet(resultSet);
/*  814 */       closeStatement(stmt);
/*  815 */       closeConnection();
/*      */ 
/*  817 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectVirementCategoryId, String dependants, Collection currentList)
/*      */   {
/*  842 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  843 */     PreparedStatement stmt = null;
/*  844 */     ResultSet resultSet = null;
/*      */ 
/*  846 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  850 */       stmt = getConnection().prepareStatement("select VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID,VIREMENT_ACCOUNT.STRUCTURE_ID,VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID,VIREMENT_ACCOUNT.TRAN_LIMIT,VIREMENT_ACCOUNT.TOTAL_LIMIT_IN,VIREMENT_ACCOUNT.TOTAL_LIMIT_OUT,VIREMENT_ACCOUNT.IN_FLAG,VIREMENT_ACCOUNT.OUT_FLAG,VIREMENT_ACCOUNT.VERSION_NUM,VIREMENT_ACCOUNT.UPDATED_BY_USER_ID,VIREMENT_ACCOUNT.UPDATED_TIME,VIREMENT_ACCOUNT.CREATED_TIME from VIREMENT_ACCOUNT where    VIREMENT_CATEGORY_ID = ? ");
/*      */ 
/*  852 */       int col = 1;
/*  853 */       stmt.setInt(col++, selectVirementCategoryId);
/*      */ 
/*  855 */       resultSet = stmt.executeQuery();
/*      */ 
/*  857 */       while (resultSet.next())
/*      */       {
/*  859 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  862 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  865 */       if (currentList != null)
/*      */       {
/*  868 */         ListIterator iter = items.listIterator();
/*  869 */         VirementAccountEVO currentEVO = null;
/*  870 */         VirementAccountEVO newEVO = null;
/*  871 */         while (iter.hasNext())
/*      */         {
/*  873 */           newEVO = (VirementAccountEVO)iter.next();
/*  874 */           Iterator iter2 = currentList.iterator();
/*  875 */           while (iter2.hasNext())
/*      */           {
/*  877 */             currentEVO = (VirementAccountEVO)iter2.next();
/*  878 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  880 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  886 */         Iterator iter2 = currentList.iterator();
/*  887 */         while (iter2.hasNext())
/*      */         {
/*  889 */           currentEVO = (VirementAccountEVO)iter2.next();
/*  890 */           if (currentEVO.insertPending()) {
/*  891 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  895 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  899 */       throw handleSQLException("select VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID,VIREMENT_ACCOUNT.STRUCTURE_ID,VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID,VIREMENT_ACCOUNT.TRAN_LIMIT,VIREMENT_ACCOUNT.TOTAL_LIMIT_IN,VIREMENT_ACCOUNT.TOTAL_LIMIT_OUT,VIREMENT_ACCOUNT.IN_FLAG,VIREMENT_ACCOUNT.OUT_FLAG,VIREMENT_ACCOUNT.VERSION_NUM,VIREMENT_ACCOUNT.UPDATED_BY_USER_ID,VIREMENT_ACCOUNT.UPDATED_TIME,VIREMENT_ACCOUNT.CREATED_TIME from VIREMENT_ACCOUNT where    VIREMENT_CATEGORY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  903 */       closeResultSet(resultSet);
/*  904 */       closeStatement(stmt);
/*  905 */       closeConnection();
/*      */ 
/*  907 */       if (timer != null) {
/*  908 */         timer.logDebug("getAll", " VirementCategoryId=" + selectVirementCategoryId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  913 */     return items;
/*      */   }
/*      */ 
/*      */   public VirementAccountEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  927 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  930 */     if (this.mDetails == null) {
/*  931 */       doLoad(((VirementAccountCK)paramCK).getVirementAccountPK());
/*      */     }
/*  933 */     else if (!this.mDetails.getPK().equals(((VirementAccountCK)paramCK).getVirementAccountPK())) {
/*  934 */       doLoad(((VirementAccountCK)paramCK).getVirementAccountPK());
/*      */     }
/*      */ 
/*  937 */     VirementAccountEVO details = new VirementAccountEVO();
/*  938 */     details = this.mDetails.deepClone();
/*      */ 
/*  940 */     if (timer != null) {
/*  941 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  943 */     return details;
/*      */   }
/*      */ 
/*      */   public VirementAccountEVO getDetails(ModelCK paramCK, VirementAccountEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  949 */     VirementAccountEVO savedEVO = this.mDetails;
/*  950 */     this.mDetails = paramEVO;
/*  951 */     VirementAccountEVO newEVO = getDetails(paramCK, dependants);
/*  952 */     this.mDetails = savedEVO;
/*  953 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public VirementAccountEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  959 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  963 */     VirementAccountEVO details = this.mDetails.deepClone();
/*      */ 
/*  965 */     if (timer != null) {
/*  966 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  968 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  973 */     return "VirementAccount";
/*      */   }
/*      */ 
/*      */   public VirementAccountRefImpl getRef(VirementAccountPK paramVirementAccountPK)
/*      */   {
/*  978 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  979 */     PreparedStatement stmt = null;
/*  980 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  983 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID from VIREMENT_ACCOUNT,MODEL,VIREMENT_CATEGORY where 1=1 and VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID = ? and VIREMENT_ACCOUNT.STRUCTURE_ID = ? and VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID = ? and VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID = VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID and VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID = MODEL.VIREMENT_CATEGORY_ID");
/*  984 */       int col = 1;
/*  985 */       stmt.setInt(col++, paramVirementAccountPK.getVirementCategoryId());
/*  986 */       stmt.setInt(col++, paramVirementAccountPK.getStructureId());
/*  987 */       stmt.setInt(col++, paramVirementAccountPK.getStructureElementId());
/*      */ 
/*  989 */       resultSet = stmt.executeQuery();
/*      */ 
/*  991 */       if (!resultSet.next()) {
/*  992 */         throw new RuntimeException(getEntityName() + " getRef " + paramVirementAccountPK + " not found");
/*      */       }
/*  994 */       col = 2;
/*  995 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  999 */       VirementCategoryPK newVirementCategoryPK = new VirementCategoryPK(resultSet.getInt(col++));
/*      */ 
/* 1003 */       String textVirementAccount = "";
/* 1004 */       VirementAccountCK ckVirementAccount = new VirementAccountCK(newModelPK, newVirementCategoryPK, paramVirementAccountPK);
/*      */ 
/* 1010 */       VirementAccountRefImpl localVirementAccountRefImpl = new VirementAccountRefImpl(ckVirementAccount, textVirementAccount);
/*      */       return localVirementAccountRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1015 */       throw handleSQLException(paramVirementAccountPK, "select 0,MODEL.MODEL_ID,VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID from VIREMENT_ACCOUNT,MODEL,VIREMENT_CATEGORY where 1=1 and VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID = ? and VIREMENT_ACCOUNT.STRUCTURE_ID = ? and VIREMENT_ACCOUNT.STRUCTURE_ELEMENT_ID = ? and VIREMENT_ACCOUNT.VIREMENT_CATEGORY_ID = VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID and VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID = MODEL.VIREMENT_CATEGORY_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1019 */       closeResultSet(resultSet);
/* 1020 */       closeStatement(stmt);
/* 1021 */       closeConnection();
/*      */ 
/* 1023 */       if (timer != null)
/* 1024 */         timer.logDebug("getRef", paramVirementAccountPK); 
/* 1024 */     }
/*      */   }
/*      */ 
/*      */   public void eraseOrphanedAccounts(int modelId)
/*      */   {
/* 1051 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/* 1054 */       ps = getConnection().prepareStatement("delete from virement_account va \nwhere va.rowid in \n( \n\tselect va.rowid \n\tfrom virement_account va, \n\t     virement_category vc \n\twhere vc.model_id = ? and \n\t      vc.virement_category_id = va.virement_category_id and \n\t      va.structure_element_id not in ( select sev.structure_element_id \n\t\t\t\t\t\t\t\t\t\t   from structure_element_view sev \n\t\t\t\t\t\t\t\t\t\t   where sev.structure_id = va.structure_id ) \n)");
/* 1055 */       ps.setInt(1, modelId);
/* 1056 */       ps.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1060 */       throw handleSQLException("eraseOrphanedAccount", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1064 */       closeStatement(ps);
/* 1065 */       closeConnection();
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.virement.VirementAccountDAO
 * JD-Core Version:    0.6.0
 */