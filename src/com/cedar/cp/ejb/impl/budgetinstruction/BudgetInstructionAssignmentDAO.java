/*     */ package com.cedar.cp.ejb.impl.budgetinstruction;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionAssignmentsELO;
/*     */ import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentCK;
/*     */ import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentPK;
/*     */ import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentRefImpl;
/*     */ import com.cedar.cp.dto.budgetinstruction.BudgetInstructionCK;
/*     */ import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
/*     */ import com.cedar.cp.dto.budgetinstruction.BudgetInstructionRefImpl;
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
/*     */ public class BudgetInstructionAssignmentDAO extends AbstractDAO
/*     */ {
/*  44 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID,BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID,BDGT_INSTR_ASSGNMNT.MODEL_ID,BDGT_INSTR_ASSGNMNT.FINANCE_CUBE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_CYCLE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_HIER_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_ELEMENT_ID,BDGT_INSTR_ASSGNMNT.SELECT_CHILDREN,BDGT_INSTR_ASSGNMNT.VERSION_NUM,BDGT_INSTR_ASSGNMNT.UPDATED_BY_USER_ID,BDGT_INSTR_ASSGNMNT.UPDATED_TIME,BDGT_INSTR_ASSGNMNT.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from BDGT_INSTR_ASSGNMNT where    ASSIGNMENT_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into BDGT_INSTR_ASSGNMNT ( ASSIGNMENT_ID,BUDGET_INSTRUCTION_ID,MODEL_ID,FINANCE_CUBE_ID,BUDGET_CYCLE_ID,BUDGET_LOCATION_HIER_ID,BUDGET_LOCATION_ELEMENT_ID,SELECT_CHILDREN,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update BDGT_INSTR_ASSGNMNT set BUDGET_INSTRUCTION_ID = ?,MODEL_ID = ?,FINANCE_CUBE_ID = ?,BUDGET_CYCLE_ID = ?,BUDGET_LOCATION_HIER_ID = ?,BUDGET_LOCATION_ELEMENT_ID = ?,SELECT_CHILDREN = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ASSIGNMENT_ID = ? AND VERSION_NUM = ?";
/*     */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from BDGT_INSTR_ASSGNMNT where ASSIGNMENT_ID = ?";
/* 415 */   protected static String SQL_ALL_BUDGET_INSTRUCTION_ASSIGNMENTS = "select 0       ,BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID      ,BUDGET_INSTRUCTION.VIS_ID      ,BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID      ,BDGT_INSTR_ASSGNMNT.MODEL_ID      ,BDGT_INSTR_ASSGNMNT.FINANCE_CUBE_ID      ,BDGT_INSTR_ASSGNMNT.BUDGET_CYCLE_ID      ,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_ELEMENT_ID from BDGT_INSTR_ASSGNMNT    ,BUDGET_INSTRUCTION where 1=1   and BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID = BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from BDGT_INSTR_ASSGNMNT where    ASSIGNMENT_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from BDGT_INSTR_ASSGNMNT where 1=1 and BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID = ? order by  BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID";
/*     */   protected static final String SQL_GET_ALL = " from BDGT_INSTR_ASSGNMNT where    BUDGET_INSTRUCTION_ID = ? ";
/*     */   public static final String SQL_DELETE_ORPHAN = "delete from bdgt_instr_assgnmnt bia where bia.model_id = ? and bia.budget_location_element_id <> 0 and bia.budget_location_element_id not in  (select se.structure_element_id \t from model m, \t      structure_element se \twhere m.model_id = ?\t  and se.structure_id = m.budget_hierarchy_id)";
/*     */   protected BudgetInstructionAssignmentEVO mDetails;
/*     */ 
/*     */   public BudgetInstructionAssignmentDAO(Connection connection)
/*     */   {
/*  51 */     super(connection);
/*     */   }
/*     */ 
/*     */   public BudgetInstructionAssignmentDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BudgetInstructionAssignmentDAO(DataSource ds)
/*     */   {
/*  67 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected BudgetInstructionAssignmentPK getPK()
/*     */   {
/*  75 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(BudgetInstructionAssignmentEVO details)
/*     */   {
/*  84 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private BudgetInstructionAssignmentEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 107 */     int col = 1;
/* 108 */     BudgetInstructionAssignmentEVO evo = new BudgetInstructionAssignmentEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++));
/*     */ 
/* 120 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 121 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 122 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 123 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(BudgetInstructionAssignmentEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 128 */     int col = startCol_;
/* 129 */     stmt_.setInt(col++, evo_.getAssignmentId());
/* 130 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(BudgetInstructionAssignmentEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 135 */     int col = startCol_;
/* 136 */     stmt_.setInt(col++, evo_.getBudgetInstructionId());
/* 137 */     stmt_.setInt(col++, evo_.getModelId());
/* 138 */     stmt_.setInt(col++, evo_.getFinanceCubeId());
/* 139 */     stmt_.setInt(col++, evo_.getBudgetCycleId());
/* 140 */     stmt_.setInt(col++, evo_.getBudgetLocationHierId());
/* 141 */     stmt_.setInt(col++, evo_.getBudgetLocationElementId());
/* 142 */     if (evo_.getSelectChildren())
/* 143 */       stmt_.setString(col++, "Y");
/*     */     else
/* 145 */       stmt_.setString(col++, " ");
/* 146 */     stmt_.setInt(col++, evo_.getVersionNum());
/* 147 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 148 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 149 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 150 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(BudgetInstructionAssignmentPK pk)
/*     */     throws ValidationException
/*     */   {
/* 166 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 168 */     PreparedStatement stmt = null;
/* 169 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 173 */       stmt = getConnection().prepareStatement("select BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID,BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID,BDGT_INSTR_ASSGNMNT.MODEL_ID,BDGT_INSTR_ASSGNMNT.FINANCE_CUBE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_CYCLE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_HIER_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_ELEMENT_ID,BDGT_INSTR_ASSGNMNT.SELECT_CHILDREN,BDGT_INSTR_ASSGNMNT.VERSION_NUM,BDGT_INSTR_ASSGNMNT.UPDATED_BY_USER_ID,BDGT_INSTR_ASSGNMNT.UPDATED_TIME,BDGT_INSTR_ASSGNMNT.CREATED_TIME from BDGT_INSTR_ASSGNMNT where    ASSIGNMENT_ID = ? ");
/*     */ 
/* 176 */       int col = 1;
/* 177 */       stmt.setInt(col++, pk.getAssignmentId());
/*     */ 
/* 179 */       resultSet = stmt.executeQuery();
/*     */ 
/* 181 */       if (!resultSet.next()) {
/* 182 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 185 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 186 */       if (this.mDetails.isModified())
/* 187 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 191 */       throw handleSQLException(pk, "select BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID,BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID,BDGT_INSTR_ASSGNMNT.MODEL_ID,BDGT_INSTR_ASSGNMNT.FINANCE_CUBE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_CYCLE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_HIER_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_ELEMENT_ID,BDGT_INSTR_ASSGNMNT.SELECT_CHILDREN,BDGT_INSTR_ASSGNMNT.VERSION_NUM,BDGT_INSTR_ASSGNMNT.UPDATED_BY_USER_ID,BDGT_INSTR_ASSGNMNT.UPDATED_TIME,BDGT_INSTR_ASSGNMNT.CREATED_TIME from BDGT_INSTR_ASSGNMNT where    ASSIGNMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 195 */       closeResultSet(resultSet);
/* 196 */       closeStatement(stmt);
/* 197 */       closeConnection();
/*     */ 
/* 199 */       if (timer != null)
/* 200 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 241 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 242 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 247 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 248 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 249 */       stmt = getConnection().prepareStatement("insert into BDGT_INSTR_ASSGNMNT ( ASSIGNMENT_ID,BUDGET_INSTRUCTION_ID,MODEL_ID,FINANCE_CUBE_ID,BUDGET_CYCLE_ID,BUDGET_LOCATION_HIER_ID,BUDGET_LOCATION_ELEMENT_ID,SELECT_CHILDREN,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 252 */       int col = 1;
/* 253 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 254 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 257 */       int resultCount = stmt.executeUpdate();
/* 258 */       if (resultCount != 1)
/*     */       {
/* 260 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 263 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 267 */       throw handleSQLException(this.mDetails.getPK(), "insert into BDGT_INSTR_ASSGNMNT ( ASSIGNMENT_ID,BUDGET_INSTRUCTION_ID,MODEL_ID,FINANCE_CUBE_ID,BUDGET_CYCLE_ID,BUDGET_LOCATION_HIER_ID,BUDGET_LOCATION_ELEMENT_ID,SELECT_CHILDREN,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 271 */       closeStatement(stmt);
/* 272 */       closeConnection();
/*     */ 
/* 274 */       if (timer != null)
/* 275 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 306 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 310 */     PreparedStatement stmt = null;
/*     */ 
/* 312 */     boolean mainChanged = this.mDetails.isModified();
/* 313 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 316 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 319 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*     */ 
/* 322 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 323 */         stmt = getConnection().prepareStatement("update BDGT_INSTR_ASSGNMNT set BUDGET_INSTRUCTION_ID = ?,MODEL_ID = ?,FINANCE_CUBE_ID = ?,BUDGET_CYCLE_ID = ?,BUDGET_LOCATION_HIER_ID = ?,BUDGET_LOCATION_ELEMENT_ID = ?,SELECT_CHILDREN = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ASSIGNMENT_ID = ? AND VERSION_NUM = ?");
/*     */ 
/* 326 */         int col = 1;
/* 327 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 328 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 330 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*     */ 
/* 333 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 335 */         if (resultCount == 0) {
/* 336 */           checkVersionNum();
/*     */         }
/* 338 */         if (resultCount != 1) {
/* 339 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 342 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 351 */       throw handleSQLException(getPK(), "update BDGT_INSTR_ASSGNMNT set BUDGET_INSTRUCTION_ID = ?,MODEL_ID = ?,FINANCE_CUBE_ID = ?,BUDGET_CYCLE_ID = ?,BUDGET_LOCATION_HIER_ID = ?,BUDGET_LOCATION_ELEMENT_ID = ?,SELECT_CHILDREN = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    ASSIGNMENT_ID = ? AND VERSION_NUM = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 355 */       closeStatement(stmt);
/* 356 */       closeConnection();
/*     */ 
/* 358 */       if ((timer != null) && (
/* 359 */         (mainChanged) || (dependantChanged)))
/* 360 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkVersionNum()
/*     */     throws VersionValidationException
/*     */   {
/* 372 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 373 */     PreparedStatement stmt = null;
/* 374 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 378 */       stmt = getConnection().prepareStatement("select VERSION_NUM from BDGT_INSTR_ASSGNMNT where ASSIGNMENT_ID = ?");
/*     */ 
/* 381 */       int col = 1;
/* 382 */       stmt.setInt(col++, this.mDetails.getAssignmentId());
/*     */ 
/* 385 */       resultSet = stmt.executeQuery();
/*     */ 
/* 387 */       if (!resultSet.next()) {
/* 388 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 391 */       col = 1;
/* 392 */       int dbVersionNumber = resultSet.getInt(col++);
/* 393 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/* 394 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 400 */       throw handleSQLException(getPK(), "select VERSION_NUM from BDGT_INSTR_ASSGNMNT where ASSIGNMENT_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 404 */       closeStatement(stmt);
/* 405 */       closeResultSet(resultSet);
/*     */ 
/* 407 */       if (timer != null)
/* 408 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllBudgetInstructionAssignmentsELO getAllBudgetInstructionAssignments()
/*     */   {
/* 446 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 447 */     PreparedStatement stmt = null;
/* 448 */     ResultSet resultSet = null;
/* 449 */     AllBudgetInstructionAssignmentsELO results = new AllBudgetInstructionAssignmentsELO();
/*     */     try
/*     */     {
/* 452 */       stmt = getConnection().prepareStatement(SQL_ALL_BUDGET_INSTRUCTION_ASSIGNMENTS);
/* 453 */       int col = 1;
/* 454 */       resultSet = stmt.executeQuery();
/* 455 */       while (resultSet.next())
/*     */       {
/* 457 */         col = 2;
/*     */ 
/* 460 */         BudgetInstructionPK pkBudgetInstruction = new BudgetInstructionPK(resultSet.getInt(col++));
/*     */ 
/* 463 */         String textBudgetInstruction = resultSet.getString(col++);
/*     */ 
/* 466 */         BudgetInstructionAssignmentPK pkBudgetInstructionAssignment = new BudgetInstructionAssignmentPK(resultSet.getInt(col++));
/*     */ 
/* 469 */         String textBudgetInstructionAssignment = "";
/*     */ 
/* 474 */         BudgetInstructionAssignmentCK ckBudgetInstructionAssignment = new BudgetInstructionAssignmentCK(pkBudgetInstruction, pkBudgetInstructionAssignment);
/*     */ 
/* 480 */         BudgetInstructionRefImpl erBudgetInstruction = new BudgetInstructionRefImpl(pkBudgetInstruction, textBudgetInstruction);
/*     */ 
/* 486 */         BudgetInstructionAssignmentRefImpl erBudgetInstructionAssignment = new BudgetInstructionAssignmentRefImpl(ckBudgetInstructionAssignment, textBudgetInstructionAssignment);
/*     */ 
/* 491 */         int col1 = resultSet.getInt(col++);
/* 492 */         int col2 = resultSet.getInt(col++);
/* 493 */         int col3 = resultSet.getInt(col++);
/* 494 */         int col4 = resultSet.getInt(col++);
/*     */ 
/* 497 */         results.add(erBudgetInstructionAssignment, erBudgetInstruction, col1, col2, col3, col4);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 509 */       throw handleSQLException(SQL_ALL_BUDGET_INSTRUCTION_ASSIGNMENTS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 513 */       closeResultSet(resultSet);
/* 514 */       closeStatement(stmt);
/* 515 */       closeConnection();
/*     */     }
/*     */ 
/* 518 */     if (timer != null) {
/* 519 */       timer.logDebug("getAllBudgetInstructionAssignments", " items=" + results.size());
/*     */     }
/*     */ 
/* 523 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 540 */     if (items == null) {
/* 541 */       return false;
/*     */     }
/* 543 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 544 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 546 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 551 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 552 */       while (iter2.hasNext())
/*     */       {
/* 554 */         this.mDetails = ((BudgetInstructionAssignmentEVO)iter2.next());
/*     */ 
/* 557 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 559 */         somethingChanged = true;
/*     */ 
/* 562 */         if (deleteStmt == null) {
/* 563 */           deleteStmt = getConnection().prepareStatement("delete from BDGT_INSTR_ASSGNMNT where    ASSIGNMENT_ID = ? ");
/*     */         }
/*     */ 
/* 566 */         int col = 1;
/* 567 */         deleteStmt.setInt(col++, this.mDetails.getAssignmentId());
/*     */ 
/* 569 */         if (this._log.isDebugEnabled()) {
/* 570 */           this._log.debug("update", "BudgetInstructionAssignment deleting AssignmentId=" + this.mDetails.getAssignmentId());
/*     */         }
/*     */ 
/* 575 */         deleteStmt.addBatch();
/*     */ 
/* 578 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 583 */       if (deleteStmt != null)
/*     */       {
/* 585 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 587 */         deleteStmt.executeBatch();
/*     */ 
/* 589 */         if (timer2 != null) {
/* 590 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 594 */       Iterator iter1 = items.values().iterator();
/* 595 */       while (iter1.hasNext())
/*     */       {
/* 597 */         this.mDetails = ((BudgetInstructionAssignmentEVO)iter1.next());
/*     */ 
/* 599 */         if (this.mDetails.insertPending())
/*     */         {
/* 601 */           somethingChanged = true;
/* 602 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 605 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 607 */         somethingChanged = true;
/* 608 */         doStore();
/*     */       }
/*     */ 
/* 619 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 623 */       throw handleSQLException("delete from BDGT_INSTR_ASSGNMNT where    ASSIGNMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 627 */       if (deleteStmt != null)
/*     */       {
/* 629 */         closeStatement(deleteStmt);
/* 630 */         closeConnection();
/*     */       }
/*     */ 
/* 633 */       this.mDetails = null;
/*     */ 
/* 635 */       if ((somethingChanged) && 
/* 636 */         (timer != null))
/* 637 */         timer.logDebug("update", "collection"); 
/* 637 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(BudgetInstructionPK entityPK, BudgetInstructionEVO owningEVO, String dependants)
/*     */   {
/* 656 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 658 */     PreparedStatement stmt = null;
/* 659 */     ResultSet resultSet = null;
/*     */ 
/* 661 */     int itemCount = 0;
/*     */ 
/* 663 */     Collection theseItems = new ArrayList();
/* 664 */     owningEVO.setBudgetInstructionAssignments(theseItems);
/* 665 */     owningEVO.setBudgetInstructionAssignmentsAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 669 */       stmt = getConnection().prepareStatement("select BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID,BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID,BDGT_INSTR_ASSGNMNT.MODEL_ID,BDGT_INSTR_ASSGNMNT.FINANCE_CUBE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_CYCLE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_HIER_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_ELEMENT_ID,BDGT_INSTR_ASSGNMNT.SELECT_CHILDREN,BDGT_INSTR_ASSGNMNT.VERSION_NUM,BDGT_INSTR_ASSGNMNT.UPDATED_BY_USER_ID,BDGT_INSTR_ASSGNMNT.UPDATED_TIME,BDGT_INSTR_ASSGNMNT.CREATED_TIME from BDGT_INSTR_ASSGNMNT where 1=1 and BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID = ? order by  BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID");
/*     */ 
/* 671 */       int col = 1;
/* 672 */       stmt.setInt(col++, entityPK.getBudgetInstructionId());
/*     */ 
/* 674 */       resultSet = stmt.executeQuery();
/*     */ 
/* 677 */       while (resultSet.next())
/*     */       {
/* 679 */         itemCount++;
/* 680 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 682 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 685 */       if (timer != null) {
/* 686 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 691 */       throw handleSQLException("select BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID,BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID,BDGT_INSTR_ASSGNMNT.MODEL_ID,BDGT_INSTR_ASSGNMNT.FINANCE_CUBE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_CYCLE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_HIER_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_ELEMENT_ID,BDGT_INSTR_ASSGNMNT.SELECT_CHILDREN,BDGT_INSTR_ASSGNMNT.VERSION_NUM,BDGT_INSTR_ASSGNMNT.UPDATED_BY_USER_ID,BDGT_INSTR_ASSGNMNT.UPDATED_TIME,BDGT_INSTR_ASSGNMNT.CREATED_TIME from BDGT_INSTR_ASSGNMNT where 1=1 and BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID = ? order by  BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 695 */       closeResultSet(resultSet);
/* 696 */       closeStatement(stmt);
/* 697 */       closeConnection();
/*     */ 
/* 699 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectBudgetInstructionId, String dependants, Collection currentList)
/*     */   {
/* 724 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 725 */     PreparedStatement stmt = null;
/* 726 */     ResultSet resultSet = null;
/*     */ 
/* 728 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 732 */       stmt = getConnection().prepareStatement("select BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID,BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID,BDGT_INSTR_ASSGNMNT.MODEL_ID,BDGT_INSTR_ASSGNMNT.FINANCE_CUBE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_CYCLE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_HIER_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_ELEMENT_ID,BDGT_INSTR_ASSGNMNT.SELECT_CHILDREN,BDGT_INSTR_ASSGNMNT.VERSION_NUM,BDGT_INSTR_ASSGNMNT.UPDATED_BY_USER_ID,BDGT_INSTR_ASSGNMNT.UPDATED_TIME,BDGT_INSTR_ASSGNMNT.CREATED_TIME from BDGT_INSTR_ASSGNMNT where    BUDGET_INSTRUCTION_ID = ? ");
/*     */ 
/* 734 */       int col = 1;
/* 735 */       stmt.setInt(col++, selectBudgetInstructionId);
/*     */ 
/* 737 */       resultSet = stmt.executeQuery();
/*     */ 
/* 739 */       while (resultSet.next())
/*     */       {
/* 741 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 744 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 747 */       if (currentList != null)
/*     */       {
/* 750 */         ListIterator iter = items.listIterator();
/* 751 */         BudgetInstructionAssignmentEVO currentEVO = null;
/* 752 */         BudgetInstructionAssignmentEVO newEVO = null;
/* 753 */         while (iter.hasNext())
/*     */         {
/* 755 */           newEVO = (BudgetInstructionAssignmentEVO)iter.next();
/* 756 */           Iterator iter2 = currentList.iterator();
/* 757 */           while (iter2.hasNext())
/*     */           {
/* 759 */             currentEVO = (BudgetInstructionAssignmentEVO)iter2.next();
/* 760 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 762 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 768 */         Iterator iter2 = currentList.iterator();
/* 769 */         while (iter2.hasNext())
/*     */         {
/* 771 */           currentEVO = (BudgetInstructionAssignmentEVO)iter2.next();
/* 772 */           if (currentEVO.insertPending()) {
/* 773 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 777 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 781 */       throw handleSQLException("select BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID,BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID,BDGT_INSTR_ASSGNMNT.MODEL_ID,BDGT_INSTR_ASSGNMNT.FINANCE_CUBE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_CYCLE_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_HIER_ID,BDGT_INSTR_ASSGNMNT.BUDGET_LOCATION_ELEMENT_ID,BDGT_INSTR_ASSGNMNT.SELECT_CHILDREN,BDGT_INSTR_ASSGNMNT.VERSION_NUM,BDGT_INSTR_ASSGNMNT.UPDATED_BY_USER_ID,BDGT_INSTR_ASSGNMNT.UPDATED_TIME,BDGT_INSTR_ASSGNMNT.CREATED_TIME from BDGT_INSTR_ASSGNMNT where    BUDGET_INSTRUCTION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 785 */       closeResultSet(resultSet);
/* 786 */       closeStatement(stmt);
/* 787 */       closeConnection();
/*     */ 
/* 789 */       if (timer != null) {
/* 790 */         timer.logDebug("getAll", " BudgetInstructionId=" + selectBudgetInstructionId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 795 */     return items;
/*     */   }
/*     */ 
/*     */   public BudgetInstructionAssignmentEVO getDetails(BudgetInstructionCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 809 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 812 */     if (this.mDetails == null) {
/* 813 */       doLoad(((BudgetInstructionAssignmentCK)paramCK).getBudgetInstructionAssignmentPK());
/*     */     }
/* 815 */     else if (!this.mDetails.getPK().equals(((BudgetInstructionAssignmentCK)paramCK).getBudgetInstructionAssignmentPK())) {
/* 816 */       doLoad(((BudgetInstructionAssignmentCK)paramCK).getBudgetInstructionAssignmentPK());
/*     */     }
/*     */ 
/* 819 */     BudgetInstructionAssignmentEVO details = new BudgetInstructionAssignmentEVO();
/* 820 */     details = this.mDetails.deepClone();
/*     */ 
/* 822 */     if (timer != null) {
/* 823 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 825 */     return details;
/*     */   }
/*     */ 
/*     */   public BudgetInstructionAssignmentEVO getDetails(BudgetInstructionCK paramCK, BudgetInstructionAssignmentEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 831 */     BudgetInstructionAssignmentEVO savedEVO = this.mDetails;
/* 832 */     this.mDetails = paramEVO;
/* 833 */     BudgetInstructionAssignmentEVO newEVO = getDetails(paramCK, dependants);
/* 834 */     this.mDetails = savedEVO;
/* 835 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public BudgetInstructionAssignmentEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 841 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 845 */     BudgetInstructionAssignmentEVO details = this.mDetails.deepClone();
/*     */ 
/* 847 */     if (timer != null) {
/* 848 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 850 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 855 */     return "BudgetInstructionAssignment";
/*     */   }
/*     */ 
/*     */   public BudgetInstructionAssignmentRefImpl getRef(BudgetInstructionAssignmentPK paramBudgetInstructionAssignmentPK)
/*     */   {
/* 860 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 861 */     PreparedStatement stmt = null;
/* 862 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 865 */       stmt = getConnection().prepareStatement("select 0,BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID from BDGT_INSTR_ASSGNMNT,BUDGET_INSTRUCTION where 1=1 and BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID = ? and BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID = BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID");
/* 866 */       int col = 1;
/* 867 */       stmt.setInt(col++, paramBudgetInstructionAssignmentPK.getAssignmentId());
/*     */ 
/* 869 */       resultSet = stmt.executeQuery();
/*     */ 
/* 871 */       if (!resultSet.next()) {
/* 872 */         throw new RuntimeException(getEntityName() + " getRef " + paramBudgetInstructionAssignmentPK + " not found");
/*     */       }
/* 874 */       col = 2;
/* 875 */       BudgetInstructionPK newBudgetInstructionPK = new BudgetInstructionPK(resultSet.getInt(col++));
/*     */ 
/* 879 */       String textBudgetInstructionAssignment = "";
/* 880 */       BudgetInstructionAssignmentCK ckBudgetInstructionAssignment = new BudgetInstructionAssignmentCK(newBudgetInstructionPK, paramBudgetInstructionAssignmentPK);
/*     */ 
/* 885 */       BudgetInstructionAssignmentRefImpl localBudgetInstructionAssignmentRefImpl = new BudgetInstructionAssignmentRefImpl(ckBudgetInstructionAssignment, textBudgetInstructionAssignment);
/*     */       return localBudgetInstructionAssignmentRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 890 */       throw handleSQLException(paramBudgetInstructionAssignmentPK, "select 0,BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID from BDGT_INSTR_ASSGNMNT,BUDGET_INSTRUCTION where 1=1 and BDGT_INSTR_ASSGNMNT.ASSIGNMENT_ID = ? and BDGT_INSTR_ASSGNMNT.BUDGET_INSTRUCTION_ID = BUDGET_INSTRUCTION.BUDGET_INSTRUCTION_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 894 */       closeResultSet(resultSet);
/* 895 */       closeStatement(stmt);
/* 896 */       closeConnection();
/*     */ 
/* 898 */       if (timer != null)
/* 899 */         timer.logDebug("getRef", paramBudgetInstructionAssignmentPK); 
/* 899 */     }
/*     */   }
/*     */ 
/*     */   public void deleteOrphanLocations(int modelId)
/*     */   {
/* 919 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 920 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 923 */       stmt = getConnection().prepareStatement("delete from bdgt_instr_assgnmnt bia where bia.model_id = ? and bia.budget_location_element_id <> 0 and bia.budget_location_element_id not in  (select se.structure_element_id \t from model m, \t      structure_element se \twhere m.model_id = ?\t  and se.structure_id = m.budget_hierarchy_id)");
/* 924 */       int col = 1;
/* 925 */       stmt.setInt(col++, modelId);
/* 926 */       stmt.setInt(col++, modelId);
/*     */ 
/* 928 */       stmt.executeUpdate();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 932 */       throw handleSQLException(this.mDetails.getPK(), "delete from bdgt_instr_assgnmnt bia where bia.model_id = ? and bia.budget_location_element_id <> 0 and bia.budget_location_element_id not in  (select se.structure_element_id \t from model m, \t      structure_element se \twhere m.model_id = ?\t  and se.structure_id = m.budget_hierarchy_id)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 936 */       closeStatement(stmt);
/* 937 */       closeConnection();
/*     */ 
/* 939 */       if (timer != null)
/* 940 */         timer.logDebug("deleteOrphanLocations", "modelId=" + modelId);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionAssignmentDAO
 * JD-Core Version:    0.6.0
 */