/*     */ package com.cedar.cp.ejb.impl.model.recharge;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.recharge.RechargeCellsCK;
/*     */ import com.cedar.cp.dto.model.recharge.RechargeCellsPK;
/*     */ import com.cedar.cp.dto.model.recharge.RechargeCellsRefImpl;
/*     */ import com.cedar.cp.dto.model.recharge.RechargePK;
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
/*     */ public class RechargeCellsDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select RECHARGE_CELLS.RECHARGE_CELL_ID,RECHARGE_CELLS.RECHARGE_ID,RECHARGE_CELLS.CELL_TYPE,RECHARGE_CELLS.DIM0_STRUCTURE_ID,RECHARGE_CELLS.DIM0_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DATA_TYPE,RECHARGE_CELLS.RATIO,RECHARGE_CELLS.UPDATED_BY_USER_ID,RECHARGE_CELLS.UPDATED_TIME,RECHARGE_CELLS.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from RECHARGE_CELLS where    RECHARGE_CELL_ID = ? AND RECHARGE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into RECHARGE_CELLS ( RECHARGE_CELL_ID,RECHARGE_ID,CELL_TYPE,DIM0_STRUCTURE_ID,DIM0_STRUCTURE_ELEMENT_ID,DIM1_STRUCTURE_ID,DIM1_STRUCTURE_ELEMENT_ID,DIM2_STRUCTURE_ID,DIM2_STRUCTURE_ELEMENT_ID,DIM3_STRUCTURE_ID,DIM3_STRUCTURE_ELEMENT_ID,DIM4_STRUCTURE_ID,DIM4_STRUCTURE_ELEMENT_ID,DIM5_STRUCTURE_ID,DIM5_STRUCTURE_ELEMENT_ID,DIM6_STRUCTURE_ID,DIM6_STRUCTURE_ELEMENT_ID,DIM7_STRUCTURE_ID,DIM7_STRUCTURE_ELEMENT_ID,DIM8_STRUCTURE_ID,DIM8_STRUCTURE_ELEMENT_ID,DIM9_STRUCTURE_ID,DIM9_STRUCTURE_ELEMENT_ID,DATA_TYPE,RATIO,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update RECHARGE_CELLS set CELL_TYPE = ?,DIM0_STRUCTURE_ID = ?,DIM0_STRUCTURE_ELEMENT_ID = ?,DIM1_STRUCTURE_ID = ?,DIM1_STRUCTURE_ELEMENT_ID = ?,DIM2_STRUCTURE_ID = ?,DIM2_STRUCTURE_ELEMENT_ID = ?,DIM3_STRUCTURE_ID = ?,DIM3_STRUCTURE_ELEMENT_ID = ?,DIM4_STRUCTURE_ID = ?,DIM4_STRUCTURE_ELEMENT_ID = ?,DIM5_STRUCTURE_ID = ?,DIM5_STRUCTURE_ELEMENT_ID = ?,DIM6_STRUCTURE_ID = ?,DIM6_STRUCTURE_ELEMENT_ID = ?,DIM7_STRUCTURE_ID = ?,DIM7_STRUCTURE_ELEMENT_ID = ?,DIM8_STRUCTURE_ID = ?,DIM8_STRUCTURE_ELEMENT_ID = ?,DIM9_STRUCTURE_ID = ?,DIM9_STRUCTURE_ELEMENT_ID = ?,DATA_TYPE = ?,RATIO = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RECHARGE_CELL_ID = ? AND RECHARGE_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from RECHARGE_CELLS where    RECHARGE_CELL_ID = ? AND RECHARGE_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from RECHARGE_CELLS,RECHARGE where 1=1 and RECHARGE_CELLS.RECHARGE_ID = RECHARGE.RECHARGE_ID and RECHARGE.MODEL_ID = ? order by  RECHARGE_CELLS.RECHARGE_ID ,RECHARGE_CELLS.RECHARGE_CELL_ID ,RECHARGE_CELLS.RECHARGE_ID";
/*     */   protected static final String SQL_GET_ALL = " from RECHARGE_CELLS where    RECHARGE_ID = ? ";
/*     */   protected RechargeCellsEVO mDetails;
/*     */ 
/*     */   public RechargeCellsDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public RechargeCellsDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public RechargeCellsDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected RechargeCellsPK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(RechargeCellsEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private RechargeCellsEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 114 */     int col = 1;
/* 115 */     RechargeCellsEVO evo = new RechargeCellsEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getBigDecimal(col++));
/*     */ 
/* 143 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 144 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 145 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 146 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(RechargeCellsEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 151 */     int col = startCol_;
/* 152 */     stmt_.setInt(col++, evo_.getRechargeCellId());
/* 153 */     stmt_.setInt(col++, evo_.getRechargeId());
/* 154 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(RechargeCellsEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 159 */     int col = startCol_;
/* 160 */     stmt_.setInt(col++, evo_.getCellType());
/* 161 */     stmt_.setInt(col++, evo_.getDim0StructureId());
/* 162 */     stmt_.setInt(col++, evo_.getDim0StructureElementId());
/* 163 */     stmt_.setInt(col++, evo_.getDim1StructureId());
/* 164 */     stmt_.setInt(col++, evo_.getDim1StructureElementId());
/* 165 */     stmt_.setInt(col++, evo_.getDim2StructureId());
/* 166 */     stmt_.setInt(col++, evo_.getDim2StructureElementId());
/* 167 */     stmt_.setInt(col++, evo_.getDim3StructureId());
/* 168 */     stmt_.setInt(col++, evo_.getDim3StructureElementId());
/* 169 */     stmt_.setInt(col++, evo_.getDim4StructureId());
/* 170 */     stmt_.setInt(col++, evo_.getDim4StructureElementId());
/* 171 */     stmt_.setInt(col++, evo_.getDim5StructureId());
/* 172 */     stmt_.setInt(col++, evo_.getDim5StructureElementId());
/* 173 */     stmt_.setInt(col++, evo_.getDim6StructureId());
/* 174 */     stmt_.setInt(col++, evo_.getDim6StructureElementId());
/* 175 */     stmt_.setInt(col++, evo_.getDim7StructureId());
/* 176 */     stmt_.setInt(col++, evo_.getDim7StructureElementId());
/* 177 */     stmt_.setInt(col++, evo_.getDim8StructureId());
/* 178 */     stmt_.setInt(col++, evo_.getDim8StructureElementId());
/* 179 */     stmt_.setInt(col++, evo_.getDim9StructureId());
/* 180 */     stmt_.setInt(col++, evo_.getDim9StructureElementId());
/* 181 */     stmt_.setString(col++, evo_.getDataType());
/* 182 */     stmt_.setBigDecimal(col++, evo_.getRatio());
/* 183 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 184 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 185 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 186 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(RechargeCellsPK pk)
/*     */     throws ValidationException
/*     */   {
/* 203 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 205 */     PreparedStatement stmt = null;
/* 206 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 210 */       stmt = getConnection().prepareStatement("select RECHARGE_CELLS.RECHARGE_CELL_ID,RECHARGE_CELLS.RECHARGE_ID,RECHARGE_CELLS.CELL_TYPE,RECHARGE_CELLS.DIM0_STRUCTURE_ID,RECHARGE_CELLS.DIM0_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DATA_TYPE,RECHARGE_CELLS.RATIO,RECHARGE_CELLS.UPDATED_BY_USER_ID,RECHARGE_CELLS.UPDATED_TIME,RECHARGE_CELLS.CREATED_TIME from RECHARGE_CELLS where    RECHARGE_CELL_ID = ? AND RECHARGE_ID = ? ");
/*     */ 
/* 213 */       int col = 1;
/* 214 */       stmt.setInt(col++, pk.getRechargeCellId());
/* 215 */       stmt.setInt(col++, pk.getRechargeId());
/*     */ 
/* 217 */       resultSet = stmt.executeQuery();
/*     */ 
/* 219 */       if (!resultSet.next()) {
/* 220 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 223 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 224 */       if (this.mDetails.isModified())
/* 225 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 229 */       throw handleSQLException(pk, "select RECHARGE_CELLS.RECHARGE_CELL_ID,RECHARGE_CELLS.RECHARGE_ID,RECHARGE_CELLS.CELL_TYPE,RECHARGE_CELLS.DIM0_STRUCTURE_ID,RECHARGE_CELLS.DIM0_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DATA_TYPE,RECHARGE_CELLS.RATIO,RECHARGE_CELLS.UPDATED_BY_USER_ID,RECHARGE_CELLS.UPDATED_TIME,RECHARGE_CELLS.CREATED_TIME from RECHARGE_CELLS where    RECHARGE_CELL_ID = ? AND RECHARGE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 233 */       closeResultSet(resultSet);
/* 234 */       closeStatement(stmt);
/* 235 */       closeConnection();
/*     */ 
/* 237 */       if (timer != null)
/* 238 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 311 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 312 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 317 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 318 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 319 */       stmt = getConnection().prepareStatement("insert into RECHARGE_CELLS ( RECHARGE_CELL_ID,RECHARGE_ID,CELL_TYPE,DIM0_STRUCTURE_ID,DIM0_STRUCTURE_ELEMENT_ID,DIM1_STRUCTURE_ID,DIM1_STRUCTURE_ELEMENT_ID,DIM2_STRUCTURE_ID,DIM2_STRUCTURE_ELEMENT_ID,DIM3_STRUCTURE_ID,DIM3_STRUCTURE_ELEMENT_ID,DIM4_STRUCTURE_ID,DIM4_STRUCTURE_ELEMENT_ID,DIM5_STRUCTURE_ID,DIM5_STRUCTURE_ELEMENT_ID,DIM6_STRUCTURE_ID,DIM6_STRUCTURE_ELEMENT_ID,DIM7_STRUCTURE_ID,DIM7_STRUCTURE_ELEMENT_ID,DIM8_STRUCTURE_ID,DIM8_STRUCTURE_ELEMENT_ID,DIM9_STRUCTURE_ID,DIM9_STRUCTURE_ELEMENT_ID,DATA_TYPE,RATIO,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 322 */       int col = 1;
/* 323 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 324 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 327 */       int resultCount = stmt.executeUpdate();
/* 328 */       if (resultCount != 1)
/*     */       {
/* 330 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 333 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 337 */       throw handleSQLException(this.mDetails.getPK(), "insert into RECHARGE_CELLS ( RECHARGE_CELL_ID,RECHARGE_ID,CELL_TYPE,DIM0_STRUCTURE_ID,DIM0_STRUCTURE_ELEMENT_ID,DIM1_STRUCTURE_ID,DIM1_STRUCTURE_ELEMENT_ID,DIM2_STRUCTURE_ID,DIM2_STRUCTURE_ELEMENT_ID,DIM3_STRUCTURE_ID,DIM3_STRUCTURE_ELEMENT_ID,DIM4_STRUCTURE_ID,DIM4_STRUCTURE_ELEMENT_ID,DIM5_STRUCTURE_ID,DIM5_STRUCTURE_ELEMENT_ID,DIM6_STRUCTURE_ID,DIM6_STRUCTURE_ELEMENT_ID,DIM7_STRUCTURE_ID,DIM7_STRUCTURE_ELEMENT_ID,DIM8_STRUCTURE_ID,DIM8_STRUCTURE_ELEMENT_ID,DIM9_STRUCTURE_ID,DIM9_STRUCTURE_ELEMENT_ID,DATA_TYPE,RATIO,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 341 */       closeStatement(stmt);
/* 342 */       closeConnection();
/*     */ 
/* 344 */       if (timer != null)
/* 345 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 391 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 395 */     PreparedStatement stmt = null;
/*     */ 
/* 397 */     boolean mainChanged = this.mDetails.isModified();
/* 398 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 401 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 404 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 405 */         stmt = getConnection().prepareStatement("update RECHARGE_CELLS set CELL_TYPE = ?,DIM0_STRUCTURE_ID = ?,DIM0_STRUCTURE_ELEMENT_ID = ?,DIM1_STRUCTURE_ID = ?,DIM1_STRUCTURE_ELEMENT_ID = ?,DIM2_STRUCTURE_ID = ?,DIM2_STRUCTURE_ELEMENT_ID = ?,DIM3_STRUCTURE_ID = ?,DIM3_STRUCTURE_ELEMENT_ID = ?,DIM4_STRUCTURE_ID = ?,DIM4_STRUCTURE_ELEMENT_ID = ?,DIM5_STRUCTURE_ID = ?,DIM5_STRUCTURE_ELEMENT_ID = ?,DIM6_STRUCTURE_ID = ?,DIM6_STRUCTURE_ELEMENT_ID = ?,DIM7_STRUCTURE_ID = ?,DIM7_STRUCTURE_ELEMENT_ID = ?,DIM8_STRUCTURE_ID = ?,DIM8_STRUCTURE_ELEMENT_ID = ?,DIM9_STRUCTURE_ID = ?,DIM9_STRUCTURE_ELEMENT_ID = ?,DATA_TYPE = ?,RATIO = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RECHARGE_CELL_ID = ? AND RECHARGE_ID = ? ");
/*     */ 
/* 408 */         int col = 1;
/* 409 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 410 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 413 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 416 */         if (resultCount != 1) {
/* 417 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 420 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 429 */       throw handleSQLException(getPK(), "update RECHARGE_CELLS set CELL_TYPE = ?,DIM0_STRUCTURE_ID = ?,DIM0_STRUCTURE_ELEMENT_ID = ?,DIM1_STRUCTURE_ID = ?,DIM1_STRUCTURE_ELEMENT_ID = ?,DIM2_STRUCTURE_ID = ?,DIM2_STRUCTURE_ELEMENT_ID = ?,DIM3_STRUCTURE_ID = ?,DIM3_STRUCTURE_ELEMENT_ID = ?,DIM4_STRUCTURE_ID = ?,DIM4_STRUCTURE_ELEMENT_ID = ?,DIM5_STRUCTURE_ID = ?,DIM5_STRUCTURE_ELEMENT_ID = ?,DIM6_STRUCTURE_ID = ?,DIM6_STRUCTURE_ELEMENT_ID = ?,DIM7_STRUCTURE_ID = ?,DIM7_STRUCTURE_ELEMENT_ID = ?,DIM8_STRUCTURE_ID = ?,DIM8_STRUCTURE_ELEMENT_ID = ?,DIM9_STRUCTURE_ID = ?,DIM9_STRUCTURE_ELEMENT_ID = ?,DATA_TYPE = ?,RATIO = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RECHARGE_CELL_ID = ? AND RECHARGE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 433 */       closeStatement(stmt);
/* 434 */       closeConnection();
/*     */ 
/* 436 */       if ((timer != null) && (
/* 437 */         (mainChanged) || (dependantChanged)))
/* 438 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 458 */     if (items == null) {
/* 459 */       return false;
/*     */     }
/* 461 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 462 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 464 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 469 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 470 */       while (iter2.hasNext())
/*     */       {
/* 472 */         this.mDetails = ((RechargeCellsEVO)iter2.next());
/*     */ 
/* 475 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 477 */         somethingChanged = true;
/*     */ 
/* 480 */         if (deleteStmt == null) {
/* 481 */           deleteStmt = getConnection().prepareStatement("delete from RECHARGE_CELLS where    RECHARGE_CELL_ID = ? AND RECHARGE_ID = ? ");
/*     */         }
/*     */ 
/* 484 */         int col = 1;
/* 485 */         deleteStmt.setInt(col++, this.mDetails.getRechargeCellId());
/* 486 */         deleteStmt.setInt(col++, this.mDetails.getRechargeId());
/*     */ 
/* 488 */         if (this._log.isDebugEnabled()) {
/* 489 */           this._log.debug("update", "RechargeCells deleting RechargeCellId=" + this.mDetails.getRechargeCellId() + ",RechargeId=" + this.mDetails.getRechargeId());
/*     */         }
/*     */ 
/* 495 */         deleteStmt.addBatch();
/*     */ 
/* 498 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 503 */       if (deleteStmt != null)
/*     */       {
/* 505 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 507 */         deleteStmt.executeBatch();
/*     */ 
/* 509 */         if (timer2 != null) {
/* 510 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 514 */       Iterator iter1 = items.values().iterator();
/* 515 */       while (iter1.hasNext())
/*     */       {
/* 517 */         this.mDetails = ((RechargeCellsEVO)iter1.next());
/*     */ 
/* 519 */         if (this.mDetails.insertPending())
/*     */         {
/* 521 */           somethingChanged = true;
/* 522 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 525 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 527 */         somethingChanged = true;
/* 528 */         doStore();
/*     */       }
/*     */ 
/* 539 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 543 */       throw handleSQLException("delete from RECHARGE_CELLS where    RECHARGE_CELL_ID = ? AND RECHARGE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 547 */       if (deleteStmt != null)
/*     */       {
/* 549 */         closeStatement(deleteStmt);
/* 550 */         closeConnection();
/*     */       }
/*     */ 
/* 553 */       this.mDetails = null;
/*     */ 
/* 555 */       if ((somethingChanged) && 
/* 556 */         (timer != null))
/* 557 */         timer.logDebug("update", "collection"); 
/* 557 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 581 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 583 */     PreparedStatement stmt = null;
/* 584 */     ResultSet resultSet = null;
/*     */ 
/* 586 */     int itemCount = 0;
/*     */ 
/* 588 */     RechargeEVO owningEVO = null;
/* 589 */     Iterator ownersIter = owners.iterator();
/* 590 */     while (ownersIter.hasNext())
/*     */     {
/* 592 */       owningEVO = (RechargeEVO)ownersIter.next();
/* 593 */       owningEVO.setRechargeCellsAllItemsLoaded(true);
/*     */     }
/* 595 */     ownersIter = owners.iterator();
/* 596 */     owningEVO = (RechargeEVO)ownersIter.next();
/* 597 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 601 */       stmt = getConnection().prepareStatement("select RECHARGE_CELLS.RECHARGE_CELL_ID,RECHARGE_CELLS.RECHARGE_ID,RECHARGE_CELLS.CELL_TYPE,RECHARGE_CELLS.DIM0_STRUCTURE_ID,RECHARGE_CELLS.DIM0_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DATA_TYPE,RECHARGE_CELLS.RATIO,RECHARGE_CELLS.UPDATED_BY_USER_ID,RECHARGE_CELLS.UPDATED_TIME,RECHARGE_CELLS.CREATED_TIME from RECHARGE_CELLS,RECHARGE where 1=1 and RECHARGE_CELLS.RECHARGE_ID = RECHARGE.RECHARGE_ID and RECHARGE.MODEL_ID = ? order by  RECHARGE_CELLS.RECHARGE_ID ,RECHARGE_CELLS.RECHARGE_CELL_ID ,RECHARGE_CELLS.RECHARGE_ID");
/*     */ 
/* 603 */       int col = 1;
/* 604 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 606 */       resultSet = stmt.executeQuery();
/*     */ 
/* 609 */       while (resultSet.next())
/*     */       {
/* 611 */         itemCount++;
/* 612 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 617 */         while (this.mDetails.getRechargeId() != owningEVO.getRechargeId())
/*     */         {
/* 621 */           if (!ownersIter.hasNext())
/*     */           {
/* 623 */             this._log.debug("bulkGetAll", "can't find owning [RechargeId=" + this.mDetails.getRechargeId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 627 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 628 */             ownersIter = owners.iterator();
/* 629 */             while (ownersIter.hasNext())
/*     */             {
/* 631 */               owningEVO = (RechargeEVO)ownersIter.next();
/* 632 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 634 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 636 */           owningEVO = (RechargeEVO)ownersIter.next();
/*     */         }
/* 638 */         if (owningEVO.getRechargeCells() == null)
/*     */         {
/* 640 */           theseItems = new ArrayList();
/* 641 */           owningEVO.setRechargeCells(theseItems);
/* 642 */           owningEVO.setRechargeCellsAllItemsLoaded(true);
/*     */         }
/* 644 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 647 */       if (timer != null) {
/* 648 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 653 */       throw handleSQLException("select RECHARGE_CELLS.RECHARGE_CELL_ID,RECHARGE_CELLS.RECHARGE_ID,RECHARGE_CELLS.CELL_TYPE,RECHARGE_CELLS.DIM0_STRUCTURE_ID,RECHARGE_CELLS.DIM0_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DATA_TYPE,RECHARGE_CELLS.RATIO,RECHARGE_CELLS.UPDATED_BY_USER_ID,RECHARGE_CELLS.UPDATED_TIME,RECHARGE_CELLS.CREATED_TIME from RECHARGE_CELLS,RECHARGE where 1=1 and RECHARGE_CELLS.RECHARGE_ID = RECHARGE.RECHARGE_ID and RECHARGE.MODEL_ID = ? order by  RECHARGE_CELLS.RECHARGE_ID ,RECHARGE_CELLS.RECHARGE_CELL_ID ,RECHARGE_CELLS.RECHARGE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 657 */       closeResultSet(resultSet);
/* 658 */       closeStatement(stmt);
/* 659 */       closeConnection();
/*     */ 
/* 661 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectRechargeId, String dependants, Collection currentList)
/*     */   {
/* 686 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 687 */     PreparedStatement stmt = null;
/* 688 */     ResultSet resultSet = null;
/*     */ 
/* 690 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 694 */       stmt = getConnection().prepareStatement("select RECHARGE_CELLS.RECHARGE_CELL_ID,RECHARGE_CELLS.RECHARGE_ID,RECHARGE_CELLS.CELL_TYPE,RECHARGE_CELLS.DIM0_STRUCTURE_ID,RECHARGE_CELLS.DIM0_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DATA_TYPE,RECHARGE_CELLS.RATIO,RECHARGE_CELLS.UPDATED_BY_USER_ID,RECHARGE_CELLS.UPDATED_TIME,RECHARGE_CELLS.CREATED_TIME from RECHARGE_CELLS where    RECHARGE_ID = ? ");
/*     */ 
/* 696 */       int col = 1;
/* 697 */       stmt.setInt(col++, selectRechargeId);
/*     */ 
/* 699 */       resultSet = stmt.executeQuery();
/*     */ 
/* 701 */       while (resultSet.next())
/*     */       {
/* 703 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 706 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 709 */       if (currentList != null)
/*     */       {
/* 712 */         ListIterator iter = items.listIterator();
/* 713 */         RechargeCellsEVO currentEVO = null;
/* 714 */         RechargeCellsEVO newEVO = null;
/* 715 */         while (iter.hasNext())
/*     */         {
/* 717 */           newEVO = (RechargeCellsEVO)iter.next();
/* 718 */           Iterator iter2 = currentList.iterator();
/* 719 */           while (iter2.hasNext())
/*     */           {
/* 721 */             currentEVO = (RechargeCellsEVO)iter2.next();
/* 722 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 724 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 730 */         Iterator iter2 = currentList.iterator();
/* 731 */         while (iter2.hasNext())
/*     */         {
/* 733 */           currentEVO = (RechargeCellsEVO)iter2.next();
/* 734 */           if (currentEVO.insertPending()) {
/* 735 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 739 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 743 */       throw handleSQLException("select RECHARGE_CELLS.RECHARGE_CELL_ID,RECHARGE_CELLS.RECHARGE_ID,RECHARGE_CELLS.CELL_TYPE,RECHARGE_CELLS.DIM0_STRUCTURE_ID,RECHARGE_CELLS.DIM0_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ID,RECHARGE_CELLS.DIM1_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ID,RECHARGE_CELLS.DIM2_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ID,RECHARGE_CELLS.DIM3_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ID,RECHARGE_CELLS.DIM4_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ID,RECHARGE_CELLS.DIM5_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ID,RECHARGE_CELLS.DIM6_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ID,RECHARGE_CELLS.DIM7_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ID,RECHARGE_CELLS.DIM8_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ID,RECHARGE_CELLS.DIM9_STRUCTURE_ELEMENT_ID,RECHARGE_CELLS.DATA_TYPE,RECHARGE_CELLS.RATIO,RECHARGE_CELLS.UPDATED_BY_USER_ID,RECHARGE_CELLS.UPDATED_TIME,RECHARGE_CELLS.CREATED_TIME from RECHARGE_CELLS where    RECHARGE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 747 */       closeResultSet(resultSet);
/* 748 */       closeStatement(stmt);
/* 749 */       closeConnection();
/*     */ 
/* 751 */       if (timer != null) {
/* 752 */         timer.logDebug("getAll", " RechargeId=" + selectRechargeId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 757 */     return items;
/*     */   }
/*     */ 
/*     */   public RechargeCellsEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 771 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 774 */     if (this.mDetails == null) {
/* 775 */       doLoad(((RechargeCellsCK)paramCK).getRechargeCellsPK());
/*     */     }
/* 777 */     else if (!this.mDetails.getPK().equals(((RechargeCellsCK)paramCK).getRechargeCellsPK())) {
/* 778 */       doLoad(((RechargeCellsCK)paramCK).getRechargeCellsPK());
/*     */     }
/*     */ 
/* 781 */     RechargeCellsEVO details = new RechargeCellsEVO();
/* 782 */     details = this.mDetails.deepClone();
/*     */ 
/* 784 */     if (timer != null) {
/* 785 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 787 */     return details;
/*     */   }
/*     */ 
/*     */   public RechargeCellsEVO getDetails(ModelCK paramCK, RechargeCellsEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 793 */     RechargeCellsEVO savedEVO = this.mDetails;
/* 794 */     this.mDetails = paramEVO;
/* 795 */     RechargeCellsEVO newEVO = getDetails(paramCK, dependants);
/* 796 */     this.mDetails = savedEVO;
/* 797 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public RechargeCellsEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 803 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 807 */     RechargeCellsEVO details = this.mDetails.deepClone();
/*     */ 
/* 809 */     if (timer != null) {
/* 810 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 812 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 817 */     return "RechargeCells";
/*     */   }
/*     */ 
/*     */   public RechargeCellsRefImpl getRef(RechargeCellsPK paramRechargeCellsPK)
/*     */   {
/* 822 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 823 */     PreparedStatement stmt = null;
/* 824 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 827 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,RECHARGE.RECHARGE_ID from RECHARGE_CELLS,MODEL,RECHARGE where 1=1 and RECHARGE_CELLS.RECHARGE_CELL_ID = ? and RECHARGE_CELLS.RECHARGE_ID = ? and RECHARGE_CELLS.RECHARGE_ID = RECHARGE.RECHARGE_ID and RECHARGE.RECHARGE_ID = MODEL.RECHARGE_ID");
/* 828 */       int col = 1;
/* 829 */       stmt.setInt(col++, paramRechargeCellsPK.getRechargeCellId());
/* 830 */       stmt.setInt(col++, paramRechargeCellsPK.getRechargeId());
/*     */ 
/* 832 */       resultSet = stmt.executeQuery();
/*     */ 
/* 834 */       if (!resultSet.next()) {
/* 835 */         throw new RuntimeException(getEntityName() + " getRef " + paramRechargeCellsPK + " not found");
/*     */       }
/* 837 */       col = 2;
/* 838 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 842 */       RechargePK newRechargePK = new RechargePK(resultSet.getInt(col++));
/*     */ 
/* 846 */       String textRechargeCells = "";
/* 847 */       RechargeCellsCK ckRechargeCells = new RechargeCellsCK(newModelPK, newRechargePK, paramRechargeCellsPK);
/*     */ 
/* 853 */       RechargeCellsRefImpl localRechargeCellsRefImpl = new RechargeCellsRefImpl(ckRechargeCells, textRechargeCells);
/*     */       return localRechargeCellsRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 858 */       throw handleSQLException(paramRechargeCellsPK, "select 0,MODEL.MODEL_ID,RECHARGE.RECHARGE_ID from RECHARGE_CELLS,MODEL,RECHARGE where 1=1 and RECHARGE_CELLS.RECHARGE_CELL_ID = ? and RECHARGE_CELLS.RECHARGE_ID = ? and RECHARGE_CELLS.RECHARGE_ID = RECHARGE.RECHARGE_ID and RECHARGE.RECHARGE_ID = MODEL.RECHARGE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 862 */       closeResultSet(resultSet);
/* 863 */       closeStatement(stmt);
/* 864 */       closeConnection();
/*     */ 
/* 866 */       if (timer != null)
/* 867 */         timer.logDebug("getRef", paramRechargeCellsPK); 
/* 867 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.recharge.RechargeCellsDAO
 * JD-Core Version:    0.6.0
 */