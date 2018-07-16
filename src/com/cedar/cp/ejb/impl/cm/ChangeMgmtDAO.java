/*      */ package com.cedar.cp.ejb.impl.cm;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.cm.ChangeMgmtRef;
/*      */ import com.cedar.cp.dto.cm.AllChangeMgmtsELO;
/*      */ import com.cedar.cp.dto.cm.AllChangeMgmtsForModelELO;
/*      */ import com.cedar.cp.dto.cm.AllChangeMgmtsForModelWithXMLELO;
/*      */ import com.cedar.cp.dto.cm.ChangeMgmtCK;
/*      */ import com.cedar.cp.dto.cm.ChangeMgmtPK;
/*      */ import com.cedar.cp.dto.cm.ChangeMgmtRefImpl;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import javax.sql.DataSource;
/*      */ import oracle.sql.CLOB;
/*      */ 
/*      */ public class ChangeMgmtDAO extends AbstractDAO
/*      */ {
/*   46 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select CHANGE_MGMT_ID from CHANGE_MGMT where    CHANGE_MGMT_ID = ? ";
/*      */   private static final String SQL_SELECT_LOBS = "select  XML_TEXT from CHANGE_MGMT where    CHANGE_MGMT_ID = ? for update";
/*      */   private static final String SQL_SELECT_COLUMNS = "select CHANGE_MGMT.XML_TEXT,CHANGE_MGMT.CHANGE_MGMT_ID,CHANGE_MGMT.MODEL_ID,CHANGE_MGMT.CREATED_TIME,CHANGE_MGMT.TASK_ID,CHANGE_MGMT.SOURCE_SYSTEM,CHANGE_MGMT.VERSION_NUM";
/*      */   protected static final String SQL_LOAD = " from CHANGE_MGMT where    CHANGE_MGMT_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into CHANGE_MGMT ( CHANGE_MGMT_ID,MODEL_ID,CREATED_TIME,TASK_ID,SOURCE_SYSTEM,XML_TEXT,VERSION_NUM) values ( ?,?,?,?,?,empty_clob(),?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update CHANGE_MGMT_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from CHANGE_MGMT_SEQ";
/*      */   protected static final String SQL_STORE = "update CHANGE_MGMT set MODEL_ID = ?,CREATED_TIME = ?,TASK_ID = ?,SOURCE_SYSTEM = ?,VERSION_NUM = ? where    CHANGE_MGMT_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from CHANGE_MGMT where CHANGE_MGMT_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from CHANGE_MGMT where    CHANGE_MGMT_ID = ? ";
/*  684 */   protected static String SQL_ALL_CHANGE_MGMTS = "select 0       ,CHANGE_MGMT.CHANGE_MGMT_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,CHANGE_MGMT.CREATED_TIME      ,CHANGE_MGMT.SOURCE_SYSTEM      ,CHANGE_MGMT.TASK_ID from CHANGE_MGMT    ,MODEL where 1=1  and  CHANGE_MGMT.MODEL_ID = MODEL.MODEL_ID order by MODEL.VIS_ID,CHANGE_MGMT.CREATED_TIME";
/*      */ 
/*  785 */   protected static String SQL_ALL_CHANGE_MGMTS_FOR_MODEL = "select 0       ,CHANGE_MGMT.CHANGE_MGMT_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,CHANGE_MGMT.CREATED_TIME      ,CHANGE_MGMT.SOURCE_SYSTEM      ,CHANGE_MGMT.TASK_ID from CHANGE_MGMT    ,MODEL where 1=1  and  MODEL.MODEL_ID = ? and CHANGE_MGMT.MODEL_ID = MODEL.MODEL_ID order by MODEL.VIS_ID,CHANGE_MGMT.CREATED_TIME";
/*      */ 
/*  890 */   protected static String SQL_ALL_CHANGE_MGMTS_FOR_MODEL_WITH_X_M_L = "select 0       ,CHANGE_MGMT.CHANGE_MGMT_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,CHANGE_MGMT.CREATED_TIME      ,CHANGE_MGMT.SOURCE_SYSTEM      ,CHANGE_MGMT.TASK_ID      ,CHANGE_MGMT.XML_TEXT from CHANGE_MGMT    ,MODEL where 1=1  and  MODEL.MODEL_ID = ? and CHANGE_MGMT.MODEL_ID = MODEL.MODEL_ID order by MODEL.VIS_ID,CHANGE_MGMT.CREATED_TIME";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from CHANGE_MGMT where   CHANGE_MGMT_ID = ?";
/*      */   protected ChangeMgmtEVO mDetails;
/*      */   private CLOB mXmlTextClob;
/*      */ 
/*      */   public ChangeMgmtDAO(Connection connection)
/*      */   {
/*   53 */     super(connection);
/*      */   }
/*      */ 
/*      */   public ChangeMgmtDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ChangeMgmtDAO(DataSource ds)
/*      */   {
/*   69 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected ChangeMgmtPK getPK()
/*      */   {
/*   77 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(ChangeMgmtEVO details)
/*      */   {
/*   86 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public ChangeMgmtEVO setAndGetDetails(ChangeMgmtEVO details, String dependants)
/*      */   {
/*   97 */     setDetails(details);
/*   98 */     generateKeys();
/*   99 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public ChangeMgmtPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  108 */     doCreate();
/*      */ 
/*  110 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(ChangeMgmtPK pk)
/*      */     throws ValidationException
/*      */   {
/*  120 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  129 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  138 */     doRemove();
/*      */   }
/*      */ 
/*      */   public ChangeMgmtPK findByPrimaryKey(ChangeMgmtPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  147 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  148 */     if (exists(pk_))
/*      */     {
/*  150 */       if (timer != null) {
/*  151 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  153 */       return pk_;
/*      */     }
/*      */ 
/*  156 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(ChangeMgmtPK pk)
/*      */   {
/*  174 */     PreparedStatement stmt = null;
/*  175 */     ResultSet resultSet = null;
/*  176 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  180 */       stmt = getConnection().prepareStatement("select CHANGE_MGMT_ID from CHANGE_MGMT where    CHANGE_MGMT_ID = ? ");
/*      */ 
/*  182 */       int col = 1;
/*  183 */       stmt.setInt(col++, pk.getChangeMgmtId());
/*      */ 
/*  185 */       resultSet = stmt.executeQuery();
/*      */ 
/*  187 */       if (!resultSet.next())
/*  188 */         returnValue = false;
/*      */       else
/*  190 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  194 */       throw handleSQLException(pk, "select CHANGE_MGMT_ID from CHANGE_MGMT where    CHANGE_MGMT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  198 */       closeResultSet(resultSet);
/*  199 */       closeStatement(stmt);
/*  200 */       closeConnection();
/*      */     }
/*  202 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private void selectLobs(ChangeMgmtEVO evo_)
/*      */   {
/*  216 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  218 */     PreparedStatement stmt = null;
/*  219 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  223 */       stmt = getConnection().prepareStatement("select  XML_TEXT from CHANGE_MGMT where    CHANGE_MGMT_ID = ? for update");
/*      */ 
/*  225 */       putEvoKeysToJdbc(evo_, stmt, 1);
/*      */ 
/*  227 */       resultSet = stmt.executeQuery();
/*      */ 
/*  229 */       int col = 1;
/*  230 */       while (resultSet.next())
/*      */       {
/*  232 */         this.mXmlTextClob = ((CLOB)resultSet.getClob(col++));
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  237 */       throw handleSQLException(evo_.getPK(), "select  XML_TEXT from CHANGE_MGMT where    CHANGE_MGMT_ID = ? for update", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  241 */       closeResultSet(resultSet);
/*  242 */       closeStatement(stmt);
/*      */ 
/*  244 */       if (timer != null)
/*  245 */         timer.logDebug("selectLobs", evo_.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void putLobs(ChangeMgmtEVO evo_) throws SQLException
/*      */   {
/*  251 */     updateClob(this.mXmlTextClob, evo_.getXmlText());
/*      */   }
/*      */ 
/*      */   private ChangeMgmtEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  267 */     int col = 1;
/*  268 */     this.mXmlTextClob = ((CLOB)resultSet_.getClob(col++));
/*  269 */     ChangeMgmtEVO evo = new ChangeMgmtEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getTimestamp(col++), resultSet_.getInt(col++), resultSet_.getString(col++), clobToString(this.mXmlTextClob), resultSet_.getInt(col++));
/*      */ 
/*  279 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(ChangeMgmtEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  284 */     int col = startCol_;
/*  285 */     stmt_.setInt(col++, evo_.getChangeMgmtId());
/*  286 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(ChangeMgmtEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  291 */     int col = startCol_;
/*  292 */     stmt_.setInt(col++, evo_.getModelId());
/*  293 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  294 */     stmt_.setInt(col++, evo_.getTaskId());
/*  295 */     stmt_.setString(col++, evo_.getSourceSystem());
/*      */ 
/*  297 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  298 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(ChangeMgmtPK pk)
/*      */     throws ValidationException
/*      */   {
/*  314 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  316 */     PreparedStatement stmt = null;
/*  317 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  321 */       stmt = getConnection().prepareStatement("select CHANGE_MGMT.XML_TEXT,CHANGE_MGMT.CHANGE_MGMT_ID,CHANGE_MGMT.MODEL_ID,CHANGE_MGMT.CREATED_TIME,CHANGE_MGMT.TASK_ID,CHANGE_MGMT.SOURCE_SYSTEM,CHANGE_MGMT.VERSION_NUM from CHANGE_MGMT where    CHANGE_MGMT_ID = ? ");
/*      */ 
/*  324 */       int col = 1;
/*  325 */       stmt.setInt(col++, pk.getChangeMgmtId());
/*      */ 
/*  327 */       resultSet = stmt.executeQuery();
/*      */ 
/*  329 */       if (!resultSet.next()) {
/*  330 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  333 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  334 */       if (this.mDetails.isModified())
/*  335 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  339 */       throw handleSQLException(pk, "select CHANGE_MGMT.XML_TEXT,CHANGE_MGMT.CHANGE_MGMT_ID,CHANGE_MGMT.MODEL_ID,CHANGE_MGMT.CREATED_TIME,CHANGE_MGMT.TASK_ID,CHANGE_MGMT.SOURCE_SYSTEM,CHANGE_MGMT.VERSION_NUM from CHANGE_MGMT where    CHANGE_MGMT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  343 */       closeResultSet(resultSet);
/*  344 */       closeStatement(stmt);
/*  345 */       closeConnection();
/*      */ 
/*  347 */       if (timer != null)
/*  348 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  379 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  380 */     generateKeys();
/*      */ 
/*  382 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  386 */       stmt = getConnection().prepareStatement("insert into CHANGE_MGMT ( CHANGE_MGMT_ID,MODEL_ID,CREATED_TIME,TASK_ID,SOURCE_SYSTEM,XML_TEXT,VERSION_NUM) values ( ?,?,?,?,?,empty_clob(),?)");
/*      */ 
/*  389 */       int col = 1;
/*  390 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  391 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  394 */       int resultCount = stmt.executeUpdate();
/*  395 */       if (resultCount != 1)
/*      */       {
/*  397 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  402 */       selectLobs(this.mDetails);
/*  403 */       this._log.debug("doCreate", "calling putLobs");
/*  404 */       putLobs(this.mDetails);
/*      */ 
/*  406 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  410 */       throw handleSQLException(this.mDetails.getPK(), "insert into CHANGE_MGMT ( CHANGE_MGMT_ID,MODEL_ID,CREATED_TIME,TASK_ID,SOURCE_SYSTEM,XML_TEXT,VERSION_NUM) values ( ?,?,?,?,?,empty_clob(),?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  414 */       closeStatement(stmt);
/*  415 */       closeConnection();
/*      */ 
/*  417 */       if (timer != null)
/*  418 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  438 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  440 */     PreparedStatement stmt = null;
/*  441 */     ResultSet resultSet = null;
/*  442 */     String sqlString = null;
/*      */     try
/*      */     {
/*  447 */       sqlString = "update CHANGE_MGMT_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  448 */       stmt = getConnection().prepareStatement("update CHANGE_MGMT_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  449 */       stmt.setInt(1, insertCount);
/*      */ 
/*  451 */       int resultCount = stmt.executeUpdate();
/*  452 */       if (resultCount != 1) {
/*  453 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  455 */       closeStatement(stmt);
/*      */ 
/*  458 */       sqlString = "select SEQ_NUM from CHANGE_MGMT_SEQ";
/*  459 */       stmt = getConnection().prepareStatement("select SEQ_NUM from CHANGE_MGMT_SEQ");
/*  460 */       resultSet = stmt.executeQuery();
/*  461 */       if (!resultSet.next())
/*  462 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  463 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  465 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  469 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  473 */       closeResultSet(resultSet);
/*  474 */       closeStatement(stmt);
/*  475 */       closeConnection();
/*      */ 
/*  477 */       if (timer != null)
/*  478 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  478 */     }
/*      */   }
/*      */ 
/*      */   public ChangeMgmtPK generateKeys()
/*      */   {
/*  488 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  490 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  493 */     if (insertCount == 0) {
/*  494 */       return this.mDetails.getPK();
/*      */     }
/*  496 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  498 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  521 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  523 */     generateKeys();
/*      */ 
/*  528 */     PreparedStatement stmt = null;
/*      */ 
/*  530 */     boolean mainChanged = this.mDetails.isModified();
/*  531 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  534 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  537 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  539 */         stmt = getConnection().prepareStatement("update CHANGE_MGMT set MODEL_ID = ?,CREATED_TIME = ?,TASK_ID = ?,SOURCE_SYSTEM = ?,VERSION_NUM = ? where    CHANGE_MGMT_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  541 */         selectLobs(this.mDetails);
/*  542 */         putLobs(this.mDetails);
/*      */ 
/*  545 */         int col = 1;
/*  546 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  547 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  549 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  552 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  554 */         if (resultCount == 0) {
/*  555 */           checkVersionNum();
/*      */         }
/*  557 */         if (resultCount != 1) {
/*  558 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  561 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  570 */       throw handleSQLException(getPK(), "update CHANGE_MGMT set MODEL_ID = ?,CREATED_TIME = ?,TASK_ID = ?,SOURCE_SYSTEM = ?,VERSION_NUM = ? where    CHANGE_MGMT_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  574 */       closeStatement(stmt);
/*  575 */       closeConnection();
/*      */ 
/*  577 */       if ((timer != null) && (
/*  578 */         (mainChanged) || (dependantChanged)))
/*  579 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  591 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  592 */     PreparedStatement stmt = null;
/*  593 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  597 */       stmt = getConnection().prepareStatement("select VERSION_NUM from CHANGE_MGMT where CHANGE_MGMT_ID = ?");
/*      */ 
/*  600 */       int col = 1;
/*  601 */       stmt.setInt(col++, this.mDetails.getChangeMgmtId());
/*      */ 
/*  604 */       resultSet = stmt.executeQuery();
/*      */ 
/*  606 */       if (!resultSet.next()) {
/*  607 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  610 */       col = 1;
/*  611 */       int dbVersionNumber = resultSet.getInt(col++);
/*  612 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  613 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  619 */       throw handleSQLException(getPK(), "select VERSION_NUM from CHANGE_MGMT where CHANGE_MGMT_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  623 */       closeStatement(stmt);
/*  624 */       closeResultSet(resultSet);
/*      */ 
/*  626 */       if (timer != null)
/*  627 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  644 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  649 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  654 */       stmt = getConnection().prepareStatement("delete from CHANGE_MGMT where    CHANGE_MGMT_ID = ? ");
/*      */ 
/*  657 */       int col = 1;
/*  658 */       stmt.setInt(col++, this.mDetails.getChangeMgmtId());
/*      */ 
/*  660 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  662 */       if (resultCount != 1) {
/*  663 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  669 */       throw handleSQLException(getPK(), "delete from CHANGE_MGMT where    CHANGE_MGMT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  673 */       closeStatement(stmt);
/*  674 */       closeConnection();
/*      */ 
/*  676 */       if (timer != null)
/*  677 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllChangeMgmtsELO getAllChangeMgmts()
/*      */   {
/*  712 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  713 */     PreparedStatement stmt = null;
/*  714 */     ResultSet resultSet = null;
/*  715 */     AllChangeMgmtsELO results = new AllChangeMgmtsELO();
/*      */     try
/*      */     {
/*  718 */       stmt = getConnection().prepareStatement(SQL_ALL_CHANGE_MGMTS);
/*  719 */       int col = 1;
/*  720 */       resultSet = stmt.executeQuery();
/*  721 */       while (resultSet.next())
/*      */       {
/*  723 */         col = 2;
/*      */ 
/*  726 */         ChangeMgmtPK pkChangeMgmt = new ChangeMgmtPK(resultSet.getInt(col++));
/*      */ 
/*  729 */         String textChangeMgmt = "";
/*      */ 
/*  732 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  735 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  738 */         ChangeMgmtRefImpl erChangeMgmt = new ChangeMgmtRefImpl(pkChangeMgmt, textChangeMgmt);
/*      */ 
/*  744 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  749 */         Timestamp col1 = resultSet.getTimestamp(col++);
/*  750 */         String col2 = resultSet.getString(col++);
/*  751 */         int col3 = resultSet.getInt(col++);
/*      */ 
/*  754 */         results.add(erChangeMgmt, erModel, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  765 */       throw handleSQLException(SQL_ALL_CHANGE_MGMTS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  769 */       closeResultSet(resultSet);
/*  770 */       closeStatement(stmt);
/*  771 */       closeConnection();
/*      */     }
/*      */ 
/*  774 */     if (timer != null) {
/*  775 */       timer.logDebug("getAllChangeMgmts", " items=" + results.size());
/*      */     }
/*      */ 
/*  779 */     return results;
/*      */   }
/*      */ 
/*      */   public AllChangeMgmtsForModelELO getAllChangeMgmtsForModel(int param1)
/*      */   {
/*  815 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  816 */     PreparedStatement stmt = null;
/*  817 */     ResultSet resultSet = null;
/*  818 */     AllChangeMgmtsForModelELO results = new AllChangeMgmtsForModelELO();
/*      */     try
/*      */     {
/*  821 */       stmt = getConnection().prepareStatement(SQL_ALL_CHANGE_MGMTS_FOR_MODEL);
/*  822 */       int col = 1;
/*  823 */       stmt.setInt(col++, param1);
/*  824 */       resultSet = stmt.executeQuery();
/*  825 */       while (resultSet.next())
/*      */       {
/*  827 */         col = 2;
/*      */ 
/*  830 */         ChangeMgmtPK pkChangeMgmt = new ChangeMgmtPK(resultSet.getInt(col++));
/*      */ 
/*  833 */         String textChangeMgmt = "";
/*      */ 
/*  836 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  839 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  842 */         ChangeMgmtRefImpl erChangeMgmt = new ChangeMgmtRefImpl(pkChangeMgmt, textChangeMgmt);
/*      */ 
/*  848 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  853 */         Timestamp col1 = resultSet.getTimestamp(col++);
/*  854 */         String col2 = resultSet.getString(col++);
/*  855 */         int col3 = resultSet.getInt(col++);
/*      */ 
/*  858 */         results.add(erChangeMgmt, erModel, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  869 */       throw handleSQLException(SQL_ALL_CHANGE_MGMTS_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  873 */       closeResultSet(resultSet);
/*  874 */       closeStatement(stmt);
/*  875 */       closeConnection();
/*      */     }
/*      */ 
/*  878 */     if (timer != null) {
/*  879 */       timer.logDebug("getAllChangeMgmtsForModel", " ModelId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  884 */     return results;
/*      */   }
/*      */ 
/*      */   public AllChangeMgmtsForModelWithXMLELO getAllChangeMgmtsForModelWithXML(int param1)
/*      */   {
/*  921 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  922 */     PreparedStatement stmt = null;
/*  923 */     ResultSet resultSet = null;
/*  924 */     AllChangeMgmtsForModelWithXMLELO results = new AllChangeMgmtsForModelWithXMLELO();
/*      */     try
/*      */     {
/*  927 */       stmt = getConnection().prepareStatement(SQL_ALL_CHANGE_MGMTS_FOR_MODEL_WITH_X_M_L);
/*  928 */       int col = 1;
/*  929 */       stmt.setInt(col++, param1);
/*  930 */       resultSet = stmt.executeQuery();
/*  931 */       while (resultSet.next())
/*      */       {
/*  933 */         col = 2;
/*      */ 
/*  936 */         ChangeMgmtPK pkChangeMgmt = new ChangeMgmtPK(resultSet.getInt(col++));
/*      */ 
/*  939 */         String textChangeMgmt = "";
/*      */ 
/*  942 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  945 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  948 */         ChangeMgmtRefImpl erChangeMgmt = new ChangeMgmtRefImpl(pkChangeMgmt, textChangeMgmt);
/*      */ 
/*  954 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  959 */         Timestamp col1 = resultSet.getTimestamp(col++);
/*  960 */         String col2 = resultSet.getString(col++);
/*  961 */         int col3 = resultSet.getInt(col++);
/*  962 */         CLOB col4Clob = (CLOB)resultSet.getClob(col++);
/*  963 */         String col4 = clobToString(col4Clob);
/*      */ 
/*  966 */         results.add(erChangeMgmt, erModel, col1, col2, col3, col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  978 */       throw handleSQLException(SQL_ALL_CHANGE_MGMTS_FOR_MODEL_WITH_X_M_L, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  982 */       closeResultSet(resultSet);
/*  983 */       closeStatement(stmt);
/*  984 */       closeConnection();
/*      */     }
/*      */ 
/*  987 */     if (timer != null) {
/*  988 */       timer.logDebug("getAllChangeMgmtsForModelWithXML", " ModelId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  993 */     return results;
/*      */   }
/*      */ 
/*      */   public ChangeMgmtEVO getDetails(ChangeMgmtPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1011 */     return getDetails(new ChangeMgmtCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public ChangeMgmtEVO getDetails(ChangeMgmtCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1025 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1028 */     if (this.mDetails == null) {
/* 1029 */       doLoad(paramCK.getChangeMgmtPK());
/*      */     }
/* 1031 */     else if (!this.mDetails.getPK().equals(paramCK.getChangeMgmtPK())) {
/* 1032 */       doLoad(paramCK.getChangeMgmtPK());
/*      */     }
/* 1034 */     else if (!checkIfValid())
/*      */     {
/* 1036 */       this._log.info("getDetails", "[ALERT] ChangeMgmtEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1038 */       doLoad(paramCK.getChangeMgmtPK());
/*      */     }
/*      */ 
/* 1042 */     ChangeMgmtEVO details = new ChangeMgmtEVO();
/* 1043 */     details = this.mDetails.deepClone();
/*      */ 
/* 1045 */     if (timer != null) {
/* 1046 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1048 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1058 */     boolean stillValid = false;
/* 1059 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1060 */     PreparedStatement stmt = null;
/* 1061 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1064 */       stmt = getConnection().prepareStatement("select VERSION_NUM from CHANGE_MGMT where   CHANGE_MGMT_ID = ?");
/* 1065 */       int col = 1;
/* 1066 */       stmt.setInt(col++, this.mDetails.getChangeMgmtId());
/*      */ 
/* 1068 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1070 */       if (!resultSet.next()) {
/* 1071 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1073 */       col = 1;
/* 1074 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1076 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1077 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1081 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from CHANGE_MGMT where   CHANGE_MGMT_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1085 */       closeResultSet(resultSet);
/* 1086 */       closeStatement(stmt);
/* 1087 */       closeConnection();
/*      */ 
/* 1089 */       if (timer != null) {
/* 1090 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1093 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public ChangeMgmtEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1099 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1101 */     if (!checkIfValid())
/*      */     {
/* 1103 */       this._log.info("getDetails", "ChangeMgmt " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1104 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1108 */     ChangeMgmtEVO details = this.mDetails.deepClone();
/*      */ 
/* 1110 */     if (timer != null) {
/* 1111 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1113 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1118 */     return "ChangeMgmt";
/*      */   }
/*      */ 
/*      */   public ChangeMgmtRef getRef(ChangeMgmtPK paramChangeMgmtPK)
/*      */     throws ValidationException
/*      */   {
/* 1124 */     ChangeMgmtEVO evo = getDetails(paramChangeMgmtPK, "");
/* 1125 */     return evo.getEntityRef("");
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.ChangeMgmtDAO
 * JD-Core Version:    0.6.0
 */