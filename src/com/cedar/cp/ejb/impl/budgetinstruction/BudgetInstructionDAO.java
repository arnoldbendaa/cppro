/*      */ package com.cedar.cp.ejb.impl.budgetinstruction;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.sql.DataSource;

import oracle.sql.BLOB;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionRef;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForCycleELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForLocationELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForModelELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsWebELO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentCK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentPK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionCK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
/*      */ 
/*      */ public class BudgetInstructionDAO extends AbstractDAO
/*      */ {
/*   43 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select BUDGET_INSTRUCTION_ID from BUDGET_INSTRUCTION where    BUDGET_INSTRUCTION_ID = ? ";
/*      */   private static final String SQL_SELECT_LOBS = "select  DOCUMENT from BUDGET_INSTRUCTION where    BUDGET_INSTRUCTION_ID = ? for update";
/*      */   private static final String SQL_SELECT_COLUMNS = "select BUDGET_INSTRUCTION.DOCUMENT,BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID,BUDGET_INSTRUCTION.VIS_ID,BUDGET_INSTRUCTION.DOCUMENT_REF,BUDGET_INSTRUCTION.VERSION_NUM,BUDGET_INSTRUCTION.UPDATED_BY_USER_ID,BUDGET_INSTRUCTION.UPDATED_TIME,BUDGET_INSTRUCTION.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from BUDGET_INSTRUCTION where    BUDGET_INSTRUCTION_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into BUDGET_INSTRUCTION ( BUDGET_INSTRUCTION_ID,VIS_ID,DOCUMENT_REF,DOCUMENT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,empty_blob(),?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update BUDGET_INSTRUCTION_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from BUDGET_INSTRUCTION_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_BUDGETINSTRUCTIONIDENTIFIER = "select count(*) from BUDGET_INSTRUCTION where    VIS_ID = ? and not(    BUDGET_INSTRUCTION_ID = ? )";
/*      */   protected static final String SQL_STORE = "update BUDGET_INSTRUCTION set VIS_ID = ?,DOCUMENT_REF = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    BUDGET_INSTRUCTION_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from BUDGET_INSTRUCTION where BUDGET_INSTRUCTION_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from BUDGET_INSTRUCTION where    BUDGET_INSTRUCTION_ID = ? ";
/*  788 */   protected static String SQL_ALL_BUDGET_INSTRUCTIONS = "select 0       ,BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID      ,BUDGET_INSTRUCTION.VIS_ID      ,BUDGET_INSTRUCTION.DOCUMENT_REF from BUDGET_INSTRUCTION where 1=1  order by BUDGET_INSTRUCTION.VIS_ID";
/*      */ 
/*  868 */   protected static String SQL_ALL_BUDGET_INSTRUCTIONS_WEB = "select 0       ,BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID      ,BUDGET_INSTRUCTION.VIS_ID      ,BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID      ,BUDGET_INSTRUCTION.DOCUMENT_REF from BUDGET_INSTRUCTION where 1=1  order by BUDGET_INSTRUCTION.VIS_ID";
/*      */ 
/*  951 */   protected static String SQL_ALL_BUDGET_INSTRUCTIONS_FOR_MODEL = "select distinct 0       ,BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID      ,BUDGET_INSTRUCTION.VIS_ID from BUDGET_INSTRUCTION    ,BDGT_INSTR_ASSGNMNT where 1=1  and  BDGT_INSTR_ASSGNMNT.MODEL_ID = ? AND BDGT_INSTR_ASSGNMNT.BUDGET_CYCLE_ID = 0 AND BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_HIER_ID = 0 AND BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID = BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID order by BUDGET_INSTRUCTION.VIS_ID";
/*      */ 
/* 1020 */   protected static String SQL_ALL_BUDGET_INSTRUCTIONS_FOR_CYCLE = "select distinct 0       ,BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID      ,BUDGET_INSTRUCTION.VIS_ID from BUDGET_INSTRUCTION    ,BDGT_INSTR_ASSGNMNT where 1=1  and  BDGT_INSTR_ASSGNMNT.BUDGET_CYCLE_ID = ? AND BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID = BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID order by BUDGET_INSTRUCTION.VIS_ID";
/*      */ 
/* 1089 */   protected static String SQL_ALL_BUDGET_INSTRUCTIONS_FOR_LOCATION = "select distinct 0       ,BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID      ,BUDGET_INSTRUCTION.VIS_ID from BUDGET_INSTRUCTION    ,BDGT_INSTR_ASSGNMNT where 1=1  and  BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_ELEMENT_ID = ? AND BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID = BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID order by BUDGET_INSTRUCTION.VIS_ID";
/*      */ 
/* 1157 */   private static String[][] SQL_DELETE_CHILDREN = { { "BDGT_INSTR_ASSGNMNT", "delete from BDGT_INSTR_ASSGNMNT where     BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID = ? " } };
/*      */ 
/* 1166 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/* 1170 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from BUDGET_INSTRUCTION where   BUDGET_INSTRUCTION_ID = ?";
/*      */   public static final String SQL_GET_BDGT_INSTR_ASSGNMNT_REF = "select 0,BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID from BDGT_INSTR_ASSGNMNT,BUDGET_INSTRUCTION where 1=1 and BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID = ? and BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID = BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID";
/*      */   protected BudgetInstructionAssignmentDAO mBudgetInstructionAssignmentDAO;
/*      */   protected BudgetInstructionEVO mDetails;
/*      */   private BLOB mDocumentBlob;
/*      */ 
/*      */   public BudgetInstructionDAO(Connection connection)
/*      */   {
/*   50 */     super(connection);
/*      */   }
/*      */ 
/*      */   public BudgetInstructionDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public BudgetInstructionDAO(DataSource ds)
/*      */   {
/*   66 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected BudgetInstructionPK getPK()
/*      */   {
/*   74 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(BudgetInstructionEVO details)
/*      */   {
/*   83 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public BudgetInstructionEVO setAndGetDetails(BudgetInstructionEVO details, String dependants)
/*      */   {
/*   94 */     setDetails(details);
/*   95 */     generateKeys();
/*   96 */     getDependants(this.mDetails, dependants);
/*   97 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public BudgetInstructionPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  106 */     doCreate();
/*      */ 
/*  108 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(BudgetInstructionPK pk)
/*      */     throws ValidationException
/*      */   {
/*  118 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  127 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  136 */     doRemove();
/*      */   }
/*      */ 
/*      */   public BudgetInstructionPK findByPrimaryKey(BudgetInstructionPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  145 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  146 */     if (exists(pk_))
/*      */     {
/*  148 */       if (timer != null) {
/*  149 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  151 */       return pk_;
/*      */     }
/*      */ 
/*  154 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(BudgetInstructionPK pk)
/*      */   {
/*  172 */     PreparedStatement stmt = null;
/*  173 */     ResultSet resultSet = null;
/*  174 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  178 */       stmt = getConnection().prepareStatement("select BUDGET_INSTRUCTION_ID from BUDGET_INSTRUCTION where    BUDGET_INSTRUCTION_ID = ? ");
/*      */ 
/*  180 */       int col = 1;
/*  181 */       stmt.setInt(col++, pk.getBudgetInstructionId());
/*      */ 
/*  183 */       resultSet = stmt.executeQuery();
/*      */ 
/*  185 */       if (!resultSet.next())
/*  186 */         returnValue = false;
/*      */       else
/*  188 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  192 */       throw handleSQLException(pk, "select BUDGET_INSTRUCTION_ID from BUDGET_INSTRUCTION where    BUDGET_INSTRUCTION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  196 */       closeResultSet(resultSet);
/*  197 */       closeStatement(stmt);
/*  198 */       closeConnection();
/*      */     }
/*  200 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private void selectLobs(BudgetInstructionEVO evo_)
/*      */   {
/*  214 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  216 */     PreparedStatement stmt = null;
/*  217 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  221 */       stmt = getConnection().prepareStatement("select  DOCUMENT from BUDGET_INSTRUCTION where    BUDGET_INSTRUCTION_ID = ? for update");
/*      */ 
/*  223 */       putEvoKeysToJdbc(evo_, stmt, 1);
/*      */ 
/*  225 */       resultSet = stmt.executeQuery();
/*      */ 
/*  227 */       int col = 1;
/*  228 */       while (resultSet.next())
/*      */       {
/*  230 */         this.mDocumentBlob = ((BLOB)resultSet.getBlob(col++));
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  235 */       throw handleSQLException(evo_.getPK(), "select  DOCUMENT from BUDGET_INSTRUCTION where    BUDGET_INSTRUCTION_ID = ? for update", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  239 */       closeResultSet(resultSet);
/*  240 */       closeStatement(stmt);
/*      */ 
/*  242 */       if (timer != null)
/*  243 */         timer.logDebug("selectLobs", evo_.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void putLobs(BudgetInstructionEVO evo_) throws SQLException
/*      */   {
/*  249 */     updateBlob(this.mDocumentBlob, evo_.getDocument());
/*      */   }
/*      */ 
/*      */   private BudgetInstructionEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  266 */     int col = 1;
/*  267 */     this.mDocumentBlob = ((BLOB)resultSet_.getBlob(col++));
/*  268 */     BudgetInstructionEVO evo = new BudgetInstructionEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), blobToByteArray(this.mDocumentBlob), resultSet_.getInt(col++), null);
/*      */ 
/*  277 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  278 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  279 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  280 */     return evo;
/*      */   }
/*      */ 
/*      */   private byte[] getByteArray(InputStream bis_)
/*      */   {
/*      */     try
/*      */     {
/*  287 */       ByteArrayOutputStream bos = new ByteArrayOutputStream(bis_.available());
/*      */       int chunk;
/*  289 */       while ((chunk = bis_.read()) != -1)
/*  290 */         bos.write(chunk);
/*  291 */       return bos.toByteArray();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  295 */       this._log.error("getByteArray", e);
					throw new RuntimeException(e.getMessage());
/*  296 */     }
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(BudgetInstructionEVO evo_, PreparedStatement stmt_, int startCol_)
/*      */     throws SQLException
/*      */   {
/*  302 */     int col = startCol_;
/*  303 */     stmt_.setInt(col++, evo_.getBudgetInstructionId());
/*  304 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(BudgetInstructionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  309 */     int col = startCol_;
/*  310 */     stmt_.setString(col++, evo_.getVisId());
/*  311 */     stmt_.setString(col++, evo_.getDocumentRef());
/*      */ 
/*  313 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  314 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  315 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  316 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  317 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(BudgetInstructionPK pk)
/*      */     throws ValidationException
/*      */   {
/*  333 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  335 */     PreparedStatement stmt = null;
/*  336 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  340 */       stmt = getConnection().prepareStatement("select BUDGET_INSTRUCTION.DOCUMENT,BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID,BUDGET_INSTRUCTION.VIS_ID,BUDGET_INSTRUCTION.DOCUMENT_REF,BUDGET_INSTRUCTION.VERSION_NUM,BUDGET_INSTRUCTION.UPDATED_BY_USER_ID,BUDGET_INSTRUCTION.UPDATED_TIME,BUDGET_INSTRUCTION.CREATED_TIME from BUDGET_INSTRUCTION where    BUDGET_INSTRUCTION_ID = ? ");
/*      */ 
/*  343 */       int col = 1;
/*  344 */       stmt.setInt(col++, pk.getBudgetInstructionId());
/*      */ 
/*  346 */       resultSet = stmt.executeQuery();
/*      */ 
/*  348 */       if (!resultSet.next()) {
/*  349 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  352 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  353 */       if (this.mDetails.isModified())
/*  354 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  358 */       throw handleSQLException(pk, "select BUDGET_INSTRUCTION.DOCUMENT,BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID,BUDGET_INSTRUCTION.VIS_ID,BUDGET_INSTRUCTION.DOCUMENT_REF,BUDGET_INSTRUCTION.VERSION_NUM,BUDGET_INSTRUCTION.UPDATED_BY_USER_ID,BUDGET_INSTRUCTION.UPDATED_TIME,BUDGET_INSTRUCTION.CREATED_TIME from BUDGET_INSTRUCTION where    BUDGET_INSTRUCTION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  362 */       closeResultSet(resultSet);
/*  363 */       closeStatement(stmt);
/*  364 */       closeConnection();
/*      */ 
/*  366 */       if (timer != null)
/*  367 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  400 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  401 */     generateKeys();
/*      */ 
/*  403 */     this.mDetails.postCreateInit();
/*      */ 
/*  405 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  410 */       duplicateValueCheckBudgetInstructionIdentifier();
/*      */ 
/*  412 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  413 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  414 */       stmt = getConnection().prepareStatement("insert into BUDGET_INSTRUCTION ( BUDGET_INSTRUCTION_ID,VIS_ID,DOCUMENT_REF,DOCUMENT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,empty_blob(),?,?,?,?)");
/*      */ 
/*  417 */       int col = 1;
/*  418 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  419 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  422 */       int resultCount = stmt.executeUpdate();
/*  423 */       if (resultCount != 1)
/*      */       {
/*  425 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  430 */       selectLobs(this.mDetails);
/*  431 */       this._log.debug("doCreate", "calling putLobs");
/*  432 */       putLobs(this.mDetails);
/*      */ 
/*  434 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  438 */       throw handleSQLException(this.mDetails.getPK(), "insert into BUDGET_INSTRUCTION ( BUDGET_INSTRUCTION_ID,VIS_ID,DOCUMENT_REF,DOCUMENT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,empty_blob(),?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  442 */       closeStatement(stmt);
/*  443 */       closeConnection();
/*      */ 
/*  445 */       if (timer != null) {
/*  446 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  452 */       getBudgetInstructionAssignmentDAO().update(this.mDetails.getBudgetInstructionAssignmentsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  458 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  478 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  480 */     PreparedStatement stmt = null;
/*  481 */     ResultSet resultSet = null;
/*  482 */     String sqlString = null;
/*      */     try
/*      */     {
/*  487 */       sqlString = "update BUDGET_INSTRUCTION_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  488 */       stmt = getConnection().prepareStatement("update BUDGET_INSTRUCTION_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  489 */       stmt.setInt(1, insertCount);
/*      */ 
/*  491 */       int resultCount = stmt.executeUpdate();
/*  492 */       if (resultCount != 1) {
/*  493 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  495 */       closeStatement(stmt);
/*      */ 
/*  498 */       sqlString = "select SEQ_NUM from BUDGET_INSTRUCTION_SEQ";
/*  499 */       stmt = getConnection().prepareStatement("select SEQ_NUM from BUDGET_INSTRUCTION_SEQ");
/*  500 */       resultSet = stmt.executeQuery();
/*  501 */       if (!resultSet.next())
/*  502 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  503 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  505 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  509 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  513 */       closeResultSet(resultSet);
/*  514 */       closeStatement(stmt);
/*  515 */       closeConnection();
/*      */ 
/*  517 */       if (timer != null)
/*  518 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  518 */     }
/*      */   }
/*      */ 
/*      */   public BudgetInstructionPK generateKeys()
/*      */   {
/*  528 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  530 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  533 */     if (insertCount == 0) {
/*  534 */       return this.mDetails.getPK();
/*      */     }
/*  536 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  538 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckBudgetInstructionIdentifier()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  551 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  552 */     PreparedStatement stmt = null;
/*  553 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  557 */       stmt = getConnection().prepareStatement("select count(*) from BUDGET_INSTRUCTION where    VIS_ID = ? and not(    BUDGET_INSTRUCTION_ID = ? )");
/*      */ 
/*  560 */       int col = 1;
/*  561 */       stmt.setString(col++, this.mDetails.getVisId());
/*  562 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  565 */       resultSet = stmt.executeQuery();
/*      */ 
/*  567 */       if (!resultSet.next()) {
/*  568 */         throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  572 */       col = 1;
/*  573 */       int count = resultSet.getInt(col++);
/*  574 */       if (count > 0) {
///*  575 */         throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " BudgetInstructionIdentifier");
                       throw new DuplicateNameValidationException("This identifier already exists");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  581 */       throw handleSQLException(getPK(), "select count(*) from BUDGET_INSTRUCTION where    VIS_ID = ? and not(    BUDGET_INSTRUCTION_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  585 */       closeResultSet(resultSet);
/*  586 */       closeStatement(stmt);
/*  587 */       closeConnection();
/*      */ 
/*  589 */       if (timer != null)
/*  590 */         timer.logDebug("duplicateValueCheckBudgetInstructionIdentifier", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  615 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  617 */     generateKeys();
/*      */ 
/*  622 */     PreparedStatement stmt = null;
/*      */ 
/*  624 */     boolean mainChanged = this.mDetails.isModified();
/*  625 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  629 */       if (mainChanged) {
/*  630 */         duplicateValueCheckBudgetInstructionIdentifier();
/*      */       }
/*  632 */       if (getBudgetInstructionAssignmentDAO().update(this.mDetails.getBudgetInstructionAssignmentsMap())) {
/*  633 */         dependantChanged = true;
/*      */       }
/*  635 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  638 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  641 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  642 */         stmt = getConnection().prepareStatement("update BUDGET_INSTRUCTION set VIS_ID = ?,DOCUMENT_REF = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    BUDGET_INSTRUCTION_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  644 */         selectLobs(this.mDetails);
/*  645 */         putLobs(this.mDetails);
/*      */ 
/*  648 */         int col = 1;
/*  649 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  650 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  652 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  655 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  657 */         if (resultCount == 0) {
/*  658 */           checkVersionNum();
/*      */         }
/*  660 */         if (resultCount != 1) {
/*  661 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  664 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  673 */       throw handleSQLException(getPK(), "update BUDGET_INSTRUCTION set VIS_ID = ?,DOCUMENT_REF = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    BUDGET_INSTRUCTION_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  677 */       closeStatement(stmt);
/*  678 */       closeConnection();
/*      */ 
/*  680 */       if ((timer != null) && (
/*  681 */         (mainChanged) || (dependantChanged)))
/*  682 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  694 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  695 */     PreparedStatement stmt = null;
/*  696 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  700 */       stmt = getConnection().prepareStatement("select VERSION_NUM from BUDGET_INSTRUCTION where BUDGET_INSTRUCTION_ID = ?");
/*      */ 
/*  703 */       int col = 1;
/*  704 */       stmt.setInt(col++, this.mDetails.getBudgetInstructionId());
/*      */ 
/*  707 */       resultSet = stmt.executeQuery();
/*      */ 
/*  709 */       if (!resultSet.next()) {
/*  710 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  713 */       col = 1;
/*  714 */       int dbVersionNumber = resultSet.getInt(col++);
/*  715 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  716 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  722 */       throw handleSQLException(getPK(), "select VERSION_NUM from BUDGET_INSTRUCTION where BUDGET_INSTRUCTION_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  726 */       closeStatement(stmt);
/*  727 */       closeResultSet(resultSet);
/*      */ 
/*  729 */       if (timer != null)
/*  730 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  747 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  748 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  753 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  758 */       stmt = getConnection().prepareStatement("delete from BUDGET_INSTRUCTION where    BUDGET_INSTRUCTION_ID = ? ");
/*      */ 
/*  761 */       int col = 1;
/*  762 */       stmt.setInt(col++, this.mDetails.getBudgetInstructionId());
/*      */ 
/*  764 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  766 */       if (resultCount != 1) {
/*  767 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  773 */       throw handleSQLException(getPK(), "delete from BUDGET_INSTRUCTION where    BUDGET_INSTRUCTION_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  777 */       closeStatement(stmt);
/*  778 */       closeConnection();
/*      */ 
/*  780 */       if (timer != null)
/*  781 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllBudgetInstructionsELO getAllBudgetInstructions()
/*      */   {
/*  811 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  812 */     PreparedStatement stmt = null;
/*  813 */     ResultSet resultSet = null;
/*  814 */     AllBudgetInstructionsELO results = new AllBudgetInstructionsELO();
/*      */     try
/*      */     {
/*  817 */       stmt = getConnection().prepareStatement(SQL_ALL_BUDGET_INSTRUCTIONS);
/*  818 */       int col = 1;
/*  819 */       resultSet = stmt.executeQuery();
/*  820 */       while (resultSet.next())
/*      */       {
/*  822 */         col = 2;
/*      */ 
/*  825 */         BudgetInstructionPK pkBudgetInstruction = new BudgetInstructionPK(resultSet.getInt(col++));
/*      */ 
/*  828 */         String textBudgetInstruction = resultSet.getString(col++);
/*      */ 
/*  832 */         BudgetInstructionRefImpl erBudgetInstruction = new BudgetInstructionRefImpl(pkBudgetInstruction, textBudgetInstruction);
/*      */ 
/*  837 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  840 */         results.add(erBudgetInstruction, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  848 */       throw handleSQLException(SQL_ALL_BUDGET_INSTRUCTIONS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  852 */       closeResultSet(resultSet);
/*  853 */       closeStatement(stmt);
/*  854 */       closeConnection();
/*      */     }
/*      */ 
/*  857 */     if (timer != null) {
/*  858 */       timer.logDebug("getAllBudgetInstructions", " items=" + results.size());
/*      */     }
/*      */ 
/*  862 */     return results;
/*      */   }
/*      */ 
/*      */   public AllBudgetInstructionsWebELO getAllBudgetInstructionsWeb()
/*      */   {
/*  892 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  893 */     PreparedStatement stmt = null;
/*  894 */     ResultSet resultSet = null;
/*  895 */     AllBudgetInstructionsWebELO results = new AllBudgetInstructionsWebELO();
/*      */     try
/*      */     {
/*  898 */       stmt = getConnection().prepareStatement(SQL_ALL_BUDGET_INSTRUCTIONS_WEB);
/*  899 */       int col = 1;
/*  900 */       resultSet = stmt.executeQuery();
/*  901 */       while (resultSet.next())
/*      */       {
/*  903 */         col = 2;
/*      */ 
/*  906 */         BudgetInstructionPK pkBudgetInstruction = new BudgetInstructionPK(resultSet.getInt(col++));
/*      */ 
/*  909 */         String textBudgetInstruction = resultSet.getString(col++);
/*      */ 
/*  913 */         BudgetInstructionRefImpl erBudgetInstruction = new BudgetInstructionRefImpl(pkBudgetInstruction, textBudgetInstruction);
/*      */ 
/*  918 */         int col1 = resultSet.getInt(col++);
/*  919 */         String col2 = resultSet.getString(col++);
/*      */ 
/*  922 */         results.add(erBudgetInstruction, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  931 */       throw handleSQLException(SQL_ALL_BUDGET_INSTRUCTIONS_WEB, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  935 */       closeResultSet(resultSet);
/*  936 */       closeStatement(stmt);
/*  937 */       closeConnection();
/*      */     }
/*      */ 
/*  940 */     if (timer != null) {
/*  941 */       timer.logDebug("getAllBudgetInstructionsWeb", " items=" + results.size());
/*      */     }
/*      */ 
/*  945 */     return results;
/*      */   }
/*      */ 
/*      */   public AllBudgetInstructionsForModelELO getAllBudgetInstructionsForModel(int param1)
/*      */   {
/*  976 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  977 */     PreparedStatement stmt = null;
/*  978 */     ResultSet resultSet = null;
/*  979 */     AllBudgetInstructionsForModelELO results = new AllBudgetInstructionsForModelELO();
/*      */     try
/*      */     {
/*  982 */       stmt = getConnection().prepareStatement(SQL_ALL_BUDGET_INSTRUCTIONS_FOR_MODEL);
/*  983 */       int col = 1;
/*  984 */       stmt.setInt(col++, param1);
/*  985 */       resultSet = stmt.executeQuery();
/*  986 */       while (resultSet.next())
/*      */       {
/*  988 */         col = 2;
/*      */ 
/*  991 */         results.add(resultSet.getInt(col++), resultSet.getString(col++));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  999 */       throw handleSQLException(SQL_ALL_BUDGET_INSTRUCTIONS_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1003 */       closeResultSet(resultSet);
/* 1004 */       closeStatement(stmt);
/* 1005 */       closeConnection();
/*      */     }
/*      */ 
/* 1008 */     if (timer != null) {
/* 1009 */       timer.logDebug("getAllBudgetInstructionsForModel", " ModelId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1014 */     return results;
/*      */   }
/*      */ 
/*      */   public AllBudgetInstructionsForCycleELO getAllBudgetInstructionsForCycle(int param1)
/*      */   {
/* 1045 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1046 */     PreparedStatement stmt = null;
/* 1047 */     ResultSet resultSet = null;
/* 1048 */     AllBudgetInstructionsForCycleELO results = new AllBudgetInstructionsForCycleELO();
/*      */     try
/*      */     {
/* 1051 */       stmt = getConnection().prepareStatement(SQL_ALL_BUDGET_INSTRUCTIONS_FOR_CYCLE);
/* 1052 */       int col = 1;
/* 1053 */       stmt.setInt(col++, param1);
/* 1054 */       resultSet = stmt.executeQuery();
/* 1055 */       while (resultSet.next())
/*      */       {
/* 1057 */         col = 2;
/*      */ 
/* 1060 */         results.add(resultSet.getInt(col++), resultSet.getString(col++));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1068 */       throw handleSQLException(SQL_ALL_BUDGET_INSTRUCTIONS_FOR_CYCLE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1072 */       closeResultSet(resultSet);
/* 1073 */       closeStatement(stmt);
/* 1074 */       closeConnection();
/*      */     }
/*      */ 
/* 1077 */     if (timer != null) {
/* 1078 */       timer.logDebug("getAllBudgetInstructionsForCycle", " BudgetCycleId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1083 */     return results;
/*      */   }
/*      */ 
/*      */   public AllBudgetInstructionsForLocationELO getAllBudgetInstructionsForLocation(int param1)
/*      */   {
/* 1114 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1115 */     PreparedStatement stmt = null;
/* 1116 */     ResultSet resultSet = null;
/* 1117 */     AllBudgetInstructionsForLocationELO results = new AllBudgetInstructionsForLocationELO();
/*      */     try
/*      */     {
/* 1120 */       stmt = getConnection().prepareStatement(SQL_ALL_BUDGET_INSTRUCTIONS_FOR_LOCATION);
/* 1121 */       int col = 1;
/* 1122 */       stmt.setInt(col++, param1);
/* 1123 */       resultSet = stmt.executeQuery();
/* 1124 */       while (resultSet.next())
/*      */       {
/* 1126 */         col = 2;
/*      */ 
/* 1129 */         results.add(resultSet.getInt(col++), resultSet.getString(col++));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1137 */       throw handleSQLException(SQL_ALL_BUDGET_INSTRUCTIONS_FOR_LOCATION, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1141 */       closeResultSet(resultSet);
/* 1142 */       closeStatement(stmt);
/* 1143 */       closeConnection();
/*      */     }
/*      */ 
/* 1146 */     if (timer != null) {
/* 1147 */       timer.logDebug("getAllBudgetInstructionsForLocation", " BudgetLocationElementId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1152 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(BudgetInstructionPK pk)
/*      */   {
/* 1179 */     Set emptyStrings = Collections.emptySet();
/* 1180 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(BudgetInstructionPK pk, Set<String> exclusionTables)
/*      */   {
/* 1186 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1188 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1190 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1192 */       PreparedStatement stmt = null;
/*      */ 
/* 1194 */       int resultCount = 0;
/* 1195 */       String s = null;
/*      */       try
/*      */       {
/* 1198 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 1200 */         if (this._log.isDebugEnabled()) {
/* 1201 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1203 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1206 */         int col = 1;
/* 1207 */         stmt.setInt(col++, pk.getBudgetInstructionId());
/*      */ 
/* 1210 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1214 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1218 */         closeStatement(stmt);
/* 1219 */         closeConnection();
/*      */ 
/* 1221 */         if (timer != null) {
/* 1222 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1226 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1228 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1230 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1232 */       PreparedStatement stmt = null;
/*      */ 
/* 1234 */       int resultCount = 0;
/* 1235 */       String s = null;
/*      */       try
/*      */       {
/* 1238 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1240 */         if (this._log.isDebugEnabled()) {
/* 1241 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1243 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1246 */         int col = 1;
/* 1247 */         stmt.setInt(col++, pk.getBudgetInstructionId());
/*      */ 
/* 1250 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1254 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1258 */         closeStatement(stmt);
/* 1259 */         closeConnection();
/*      */ 
/* 1261 */         if (timer != null)
/* 1262 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public BudgetInstructionEVO getDetails(BudgetInstructionPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1281 */     return getDetails(new BudgetInstructionCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public BudgetInstructionEVO getDetails(BudgetInstructionCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1298 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1301 */     if (this.mDetails == null) {
/* 1302 */       doLoad(paramCK.getBudgetInstructionPK());
/*      */     }
/* 1304 */     else if (!this.mDetails.getPK().equals(paramCK.getBudgetInstructionPK())) {
/* 1305 */       doLoad(paramCK.getBudgetInstructionPK());
/*      */     }
/* 1307 */     else if (!checkIfValid())
/*      */     {
/* 1309 */       this._log.info("getDetails", "[ALERT] BudgetInstructionEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1311 */       doLoad(paramCK.getBudgetInstructionPK());
/*      */     }
/*      */ 
/* 1321 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isBudgetInstructionAssignmentsAllItemsLoaded()))
/*      */     {
/* 1326 */       this.mDetails.setBudgetInstructionAssignments(getBudgetInstructionAssignmentDAO().getAll(this.mDetails.getBudgetInstructionId(), dependants, this.mDetails.getBudgetInstructionAssignments()));
/*      */ 
/* 1333 */       this.mDetails.setBudgetInstructionAssignmentsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1336 */     if ((paramCK instanceof BudgetInstructionAssignmentCK))
/*      */     {
/* 1338 */       if (this.mDetails.getBudgetInstructionAssignments() == null) {
/* 1339 */         this.mDetails.loadBudgetInstructionAssignmentsItem(getBudgetInstructionAssignmentDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1342 */         BudgetInstructionAssignmentPK pk = ((BudgetInstructionAssignmentCK)paramCK).getBudgetInstructionAssignmentPK();
/* 1343 */         BudgetInstructionAssignmentEVO evo = this.mDetails.getBudgetInstructionAssignmentsItem(pk);
/* 1344 */         if (evo == null) {
/* 1345 */           this.mDetails.loadBudgetInstructionAssignmentsItem(getBudgetInstructionAssignmentDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1350 */     BudgetInstructionEVO details = new BudgetInstructionEVO();
/* 1351 */     details = this.mDetails.deepClone();
/*      */ 
/* 1353 */     if (timer != null) {
/* 1354 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1356 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1366 */     boolean stillValid = false;
/* 1367 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1368 */     PreparedStatement stmt = null;
/* 1369 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1372 */       stmt = getConnection().prepareStatement("select VERSION_NUM from BUDGET_INSTRUCTION where   BUDGET_INSTRUCTION_ID = ?");
/* 1373 */       int col = 1;
/* 1374 */       stmt.setInt(col++, this.mDetails.getBudgetInstructionId());
/*      */ 
/* 1376 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1378 */       if (!resultSet.next()) {
/* 1379 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1381 */       col = 1;
/* 1382 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1384 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1385 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1389 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from BUDGET_INSTRUCTION where   BUDGET_INSTRUCTION_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1393 */       closeResultSet(resultSet);
/* 1394 */       closeStatement(stmt);
/* 1395 */       closeConnection();
/*      */ 
/* 1397 */       if (timer != null) {
/* 1398 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1401 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public BudgetInstructionEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1407 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1409 */     if (!checkIfValid())
/*      */     {
/* 1411 */       this._log.info("getDetails", "BudgetInstruction " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1412 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1416 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1419 */     BudgetInstructionEVO details = this.mDetails.deepClone();
/*      */ 
/* 1421 */     if (timer != null) {
/* 1422 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1424 */     return details;
/*      */   }
/*      */ 
/*      */   protected BudgetInstructionAssignmentDAO getBudgetInstructionAssignmentDAO()
/*      */   {
/* 1433 */     if (this.mBudgetInstructionAssignmentDAO == null)
/*      */     {
/* 1435 */       if (this.mDataSource != null)
/* 1436 */         this.mBudgetInstructionAssignmentDAO = new BudgetInstructionAssignmentDAO(this.mDataSource);
/*      */       else {
/* 1438 */         this.mBudgetInstructionAssignmentDAO = new BudgetInstructionAssignmentDAO(getConnection());
/*      */       }
/*      */     }
/* 1441 */     return this.mBudgetInstructionAssignmentDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1446 */     return "BudgetInstruction";
/*      */   }
/*      */ 
/*      */   public BudgetInstructionRef getRef(BudgetInstructionPK paramBudgetInstructionPK)
/*      */     throws ValidationException
/*      */   {
/* 1452 */     BudgetInstructionEVO evo = getDetails(paramBudgetInstructionPK, "");
/* 1453 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1477 */     if (c == null)
/* 1478 */       return;
/* 1479 */     Iterator iter = c.iterator();
/* 1480 */     while (iter.hasNext())
/*      */     {
/* 1482 */       BudgetInstructionEVO evo = (BudgetInstructionEVO)iter.next();
/* 1483 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(BudgetInstructionEVO evo, String dependants)
/*      */   {
/* 1497 */     if (evo.getBudgetInstructionId() < 1) {
/* 1498 */       return;
/*      */     }
/*      */ 
/* 1506 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1509 */       if (!evo.isBudgetInstructionAssignmentsAllItemsLoaded())
/*      */       {
/* 1511 */         evo.setBudgetInstructionAssignments(getBudgetInstructionAssignmentDAO().getAll(evo.getBudgetInstructionId(), dependants, evo.getBudgetInstructionAssignments()));
/*      */ 
/* 1518 */         evo.setBudgetInstructionAssignmentsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionDAO
 * JD-Core Version:    0.6.0
 */