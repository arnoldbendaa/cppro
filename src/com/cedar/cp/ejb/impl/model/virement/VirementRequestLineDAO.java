/*      */ package com.cedar.cp.ejb.impl.model.virement;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementLineSpreadCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementLineSpreadPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestGroupPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestLineCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestLinePK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestLineRefImpl;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestPK;
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
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class VirementRequestLineDAO extends AbstractDAO
/*      */ {
/*   38 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select VIREMENT_REQUEST_LINE.REQUEST_LINE_ID,VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID,VIREMENT_REQUEST_LINE.TARGET,VIREMENT_REQUEST_LINE.SPREAD_PROFILE_ID,VIREMENT_REQUEST_LINE.LINE_IDX,VIREMENT_REQUEST_LINE.TRANSFER_VALUE,VIREMENT_REQUEST_LINE.DATA_TYPE_ID,VIREMENT_REQUEST_LINE.DIM0,VIREMENT_REQUEST_LINE.DIM1,VIREMENT_REQUEST_LINE.DIM2,VIREMENT_REQUEST_LINE.DIM3,VIREMENT_REQUEST_LINE.DIM4,VIREMENT_REQUEST_LINE.DIM5,VIREMENT_REQUEST_LINE.DIM6,VIREMENT_REQUEST_LINE.DIM7,VIREMENT_REQUEST_LINE.DIM8,VIREMENT_REQUEST_LINE.DIM9,VIREMENT_REQUEST_LINE.REPEAT_VALUE,VIREMENT_REQUEST_LINE.UPDATED_BY_USER_ID,VIREMENT_REQUEST_LINE.UPDATED_TIME,VIREMENT_REQUEST_LINE.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from VIREMENT_REQUEST_LINE where    REQUEST_LINE_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into VIREMENT_REQUEST_LINE ( REQUEST_LINE_ID,REQUEST_GROUP_ID,TARGET,SPREAD_PROFILE_ID,LINE_IDX,TRANSFER_VALUE,DATA_TYPE_ID,DIM0,DIM1,DIM2,DIM3,DIM4,DIM5,DIM6,DIM7,DIM8,DIM9,REPEAT_VALUE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update VIREMENT_REQUEST_LINE set REQUEST_GROUP_ID = ?,TARGET = ?,SPREAD_PROFILE_ID = ?,LINE_IDX = ?,TRANSFER_VALUE = ?,DATA_TYPE_ID = ?,DIM0 = ?,DIM1 = ?,DIM2 = ?,DIM3 = ?,DIM4 = ?,DIM5 = ?,DIM6 = ?,DIM7 = ?,DIM8 = ?,DIM9 = ?,REPEAT_VALUE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REQUEST_LINE_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from VIREMENT_REQUEST_LINE where    REQUEST_LINE_ID = ? ";
/*  563 */   private static String[][] SQL_DELETE_CHILDREN = { { "VIREMENT_LINE_SPREAD", "delete from VIREMENT_LINE_SPREAD where     VIREMENT_LINE_SPREAD.REQUEST_LINE_ID = ? " } };
/*      */ 
/*  572 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  576 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and VIREMENT_REQUEST_LINE.REQUEST_LINE_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from VIREMENT_REQUEST_LINE,VIREMENT_REQUEST_GROUP,VIREMENT_REQUEST where 1=1 and VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID = VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID ,VIREMENT_REQUEST_LINE.REQUEST_LINE_ID";
/*      */   protected static final String SQL_GET_ALL = " from VIREMENT_REQUEST_LINE where    REQUEST_GROUP_ID = ? ";
/*      */   protected VirementLineSpreadDAO mVirementLineSpreadDAO;
/*      */   protected VirementRequestLineEVO mDetails;
/*      */ 
/*      */   public VirementRequestLineDAO(Connection connection)
/*      */   {
/*   45 */     super(connection);
/*      */   }
/*      */ 
/*      */   public VirementRequestLineDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public VirementRequestLineDAO(DataSource ds)
/*      */   {
/*   61 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected VirementRequestLinePK getPK()
/*      */   {
/*   69 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(VirementRequestLineEVO details)
/*      */   {
/*   78 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private VirementRequestLineEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  110 */     int col = 1;
/*  111 */     VirementRequestLineEVO evo = new VirementRequestLineEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getLong(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), getWrappedLongFromJdbc(resultSet_, col++), null);
/*      */ 
/*  133 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  134 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  135 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  136 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(VirementRequestLineEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  141 */     int col = startCol_;
/*  142 */     stmt_.setInt(col++, evo_.getRequestLineId());
/*  143 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(VirementRequestLineEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  148 */     int col = startCol_;
/*  149 */     stmt_.setInt(col++, evo_.getRequestGroupId());
/*  150 */     if (evo_.getTarget())
/*  151 */       stmt_.setString(col++, "Y");
/*      */     else
/*  153 */       stmt_.setString(col++, " ");
/*  154 */     stmt_.setInt(col++, evo_.getSpreadProfileId());
/*  155 */     stmt_.setInt(col++, evo_.getLineIdx());
/*  156 */     stmt_.setLong(col++, evo_.getTransferValue());
/*  157 */     stmt_.setInt(col++, evo_.getDataTypeId());
/*  158 */     stmt_.setInt(col++, evo_.getDim0());
/*  159 */     stmt_.setInt(col++, evo_.getDim1());
/*  160 */     stmt_.setInt(col++, evo_.getDim2());
/*  161 */     stmt_.setInt(col++, evo_.getDim3());
/*  162 */     stmt_.setInt(col++, evo_.getDim4());
/*  163 */     stmt_.setInt(col++, evo_.getDim5());
/*  164 */     stmt_.setInt(col++, evo_.getDim6());
/*  165 */     stmt_.setInt(col++, evo_.getDim7());
/*  166 */     stmt_.setInt(col++, evo_.getDim8());
/*  167 */     stmt_.setInt(col++, evo_.getDim9());
/*  168 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getRepeatValue());
/*  169 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  170 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  171 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  172 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(VirementRequestLinePK pk)
/*      */     throws ValidationException
/*      */   {
/*  188 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  190 */     PreparedStatement stmt = null;
/*  191 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  195 */       stmt = getConnection().prepareStatement("select VIREMENT_REQUEST_LINE.REQUEST_LINE_ID,VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID,VIREMENT_REQUEST_LINE.TARGET,VIREMENT_REQUEST_LINE.SPREAD_PROFILE_ID,VIREMENT_REQUEST_LINE.LINE_IDX,VIREMENT_REQUEST_LINE.TRANSFER_VALUE,VIREMENT_REQUEST_LINE.DATA_TYPE_ID,VIREMENT_REQUEST_LINE.DIM0,VIREMENT_REQUEST_LINE.DIM1,VIREMENT_REQUEST_LINE.DIM2,VIREMENT_REQUEST_LINE.DIM3,VIREMENT_REQUEST_LINE.DIM4,VIREMENT_REQUEST_LINE.DIM5,VIREMENT_REQUEST_LINE.DIM6,VIREMENT_REQUEST_LINE.DIM7,VIREMENT_REQUEST_LINE.DIM8,VIREMENT_REQUEST_LINE.DIM9,VIREMENT_REQUEST_LINE.REPEAT_VALUE,VIREMENT_REQUEST_LINE.UPDATED_BY_USER_ID,VIREMENT_REQUEST_LINE.UPDATED_TIME,VIREMENT_REQUEST_LINE.CREATED_TIME from VIREMENT_REQUEST_LINE where    REQUEST_LINE_ID = ? ");
/*      */ 
/*  198 */       int col = 1;
/*  199 */       stmt.setInt(col++, pk.getRequestLineId());
/*      */ 
/*  201 */       resultSet = stmt.executeQuery();
/*      */ 
/*  203 */       if (!resultSet.next()) {
/*  204 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  207 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  208 */       if (this.mDetails.isModified())
/*  209 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  213 */       throw handleSQLException(pk, "select VIREMENT_REQUEST_LINE.REQUEST_LINE_ID,VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID,VIREMENT_REQUEST_LINE.TARGET,VIREMENT_REQUEST_LINE.SPREAD_PROFILE_ID,VIREMENT_REQUEST_LINE.LINE_IDX,VIREMENT_REQUEST_LINE.TRANSFER_VALUE,VIREMENT_REQUEST_LINE.DATA_TYPE_ID,VIREMENT_REQUEST_LINE.DIM0,VIREMENT_REQUEST_LINE.DIM1,VIREMENT_REQUEST_LINE.DIM2,VIREMENT_REQUEST_LINE.DIM3,VIREMENT_REQUEST_LINE.DIM4,VIREMENT_REQUEST_LINE.DIM5,VIREMENT_REQUEST_LINE.DIM6,VIREMENT_REQUEST_LINE.DIM7,VIREMENT_REQUEST_LINE.DIM8,VIREMENT_REQUEST_LINE.DIM9,VIREMENT_REQUEST_LINE.REPEAT_VALUE,VIREMENT_REQUEST_LINE.UPDATED_BY_USER_ID,VIREMENT_REQUEST_LINE.UPDATED_TIME,VIREMENT_REQUEST_LINE.CREATED_TIME from VIREMENT_REQUEST_LINE where    REQUEST_LINE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  217 */       closeResultSet(resultSet);
/*  218 */       closeStatement(stmt);
/*  219 */       closeConnection();
/*      */ 
/*  221 */       if (timer != null)
/*  222 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  281 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  282 */     this.mDetails.postCreateInit();
/*      */ 
/*  284 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  289 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  290 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  291 */       stmt = getConnection().prepareStatement("insert into VIREMENT_REQUEST_LINE ( REQUEST_LINE_ID,REQUEST_GROUP_ID,TARGET,SPREAD_PROFILE_ID,LINE_IDX,TRANSFER_VALUE,DATA_TYPE_ID,DIM0,DIM1,DIM2,DIM3,DIM4,DIM5,DIM6,DIM7,DIM8,DIM9,REPEAT_VALUE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  294 */       int col = 1;
/*  295 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  296 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  299 */       int resultCount = stmt.executeUpdate();
/*  300 */       if (resultCount != 1)
/*      */       {
/*  302 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  305 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  309 */       throw handleSQLException(this.mDetails.getPK(), "insert into VIREMENT_REQUEST_LINE ( REQUEST_LINE_ID,REQUEST_GROUP_ID,TARGET,SPREAD_PROFILE_ID,LINE_IDX,TRANSFER_VALUE,DATA_TYPE_ID,DIM0,DIM1,DIM2,DIM3,DIM4,DIM5,DIM6,DIM7,DIM8,DIM9,REPEAT_VALUE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  313 */       closeStatement(stmt);
/*  314 */       closeConnection();
/*      */ 
/*  316 */       if (timer != null) {
/*  317 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  323 */       getVirementLineSpreadDAO().update(this.mDetails.getSpreadsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  329 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  368 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  372 */     PreparedStatement stmt = null;
/*      */ 
/*  374 */     boolean mainChanged = this.mDetails.isModified();
/*  375 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  379 */       if (getVirementLineSpreadDAO().update(this.mDetails.getSpreadsMap())) {
/*  380 */         dependantChanged = true;
/*      */       }
/*  382 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  385 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  386 */         stmt = getConnection().prepareStatement("update VIREMENT_REQUEST_LINE set REQUEST_GROUP_ID = ?,TARGET = ?,SPREAD_PROFILE_ID = ?,LINE_IDX = ?,TRANSFER_VALUE = ?,DATA_TYPE_ID = ?,DIM0 = ?,DIM1 = ?,DIM2 = ?,DIM3 = ?,DIM4 = ?,DIM5 = ?,DIM6 = ?,DIM7 = ?,DIM8 = ?,DIM9 = ?,REPEAT_VALUE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REQUEST_LINE_ID = ? ");
/*      */ 
/*  389 */         int col = 1;
/*  390 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  391 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  394 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  397 */         if (resultCount != 1) {
/*  398 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  401 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  410 */       throw handleSQLException(getPK(), "update VIREMENT_REQUEST_LINE set REQUEST_GROUP_ID = ?,TARGET = ?,SPREAD_PROFILE_ID = ?,LINE_IDX = ?,TRANSFER_VALUE = ?,DATA_TYPE_ID = ?,DIM0 = ?,DIM1 = ?,DIM2 = ?,DIM3 = ?,DIM4 = ?,DIM5 = ?,DIM6 = ?,DIM7 = ?,DIM8 = ?,DIM9 = ?,REPEAT_VALUE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REQUEST_LINE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  414 */       closeStatement(stmt);
/*  415 */       closeConnection();
/*      */ 
/*  417 */       if ((timer != null) && (
/*  418 */         (mainChanged) || (dependantChanged)))
/*  419 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  438 */     if (items == null) {
/*  439 */       return false;
/*      */     }
/*  441 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  442 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  444 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  448 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  449 */       while (iter3.hasNext())
/*      */       {
/*  451 */         this.mDetails = ((VirementRequestLineEVO)iter3.next());
/*  452 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  454 */         somethingChanged = true;
/*      */ 
/*  457 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  461 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  462 */       while (iter2.hasNext())
/*      */       {
/*  464 */         this.mDetails = ((VirementRequestLineEVO)iter2.next());
/*      */ 
/*  467 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  469 */         somethingChanged = true;
/*      */ 
/*  472 */         if (deleteStmt == null) {
/*  473 */           deleteStmt = getConnection().prepareStatement("delete from VIREMENT_REQUEST_LINE where    REQUEST_LINE_ID = ? ");
/*      */         }
/*      */ 
/*  476 */         int col = 1;
/*  477 */         deleteStmt.setInt(col++, this.mDetails.getRequestLineId());
/*      */ 
/*  479 */         if (this._log.isDebugEnabled()) {
/*  480 */           this._log.debug("update", "VirementRequestLine deleting RequestLineId=" + this.mDetails.getRequestLineId());
/*      */         }
/*      */ 
/*  485 */         deleteStmt.addBatch();
/*      */ 
/*  488 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  493 */       if (deleteStmt != null)
/*      */       {
/*  495 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  497 */         deleteStmt.executeBatch();
/*      */ 
/*  499 */         if (timer2 != null) {
/*  500 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  504 */       Iterator iter1 = items.values().iterator();
/*  505 */       while (iter1.hasNext())
/*      */       {
/*  507 */         this.mDetails = ((VirementRequestLineEVO)iter1.next());
/*      */ 
/*  509 */         if (this.mDetails.insertPending())
/*      */         {
/*  511 */           somethingChanged = true;
/*  512 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  515 */         if (this.mDetails.isModified())
/*      */         {
/*  517 */           somethingChanged = true;
/*  518 */           doStore(); continue;
/*      */         }
/*      */ 
/*  522 */         if ((this.mDetails.deletePending()) || 
/*  528 */           (!getVirementLineSpreadDAO().update(this.mDetails.getSpreadsMap()))) continue;
/*  529 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  541 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  545 */       throw handleSQLException("delete from VIREMENT_REQUEST_LINE where    REQUEST_LINE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  549 */       if (deleteStmt != null)
/*      */       {
/*  551 */         closeStatement(deleteStmt);
/*  552 */         closeConnection();
/*      */       }
/*      */ 
/*  555 */       this.mDetails = null;
/*      */ 
/*  557 */       if ((somethingChanged) && 
/*  558 */         (timer != null))
/*  559 */         timer.logDebug("update", "collection"); 
/*  559 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(VirementRequestLinePK pk)
/*      */   {
/*  585 */     Set emptyStrings = Collections.emptySet();
/*  586 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(VirementRequestLinePK pk, Set<String> exclusionTables)
/*      */   {
/*  592 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  594 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  596 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  598 */       PreparedStatement stmt = null;
/*      */ 
/*  600 */       int resultCount = 0;
/*  601 */       String s = null;
/*      */       try
/*      */       {
/*  604 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  606 */         if (this._log.isDebugEnabled()) {
/*  607 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  609 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  612 */         int col = 1;
/*  613 */         stmt.setInt(col++, pk.getRequestLineId());
/*      */ 
/*  616 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  620 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  624 */         closeStatement(stmt);
/*  625 */         closeConnection();
/*      */ 
/*  627 */         if (timer != null) {
/*  628 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  632 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  634 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  636 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  638 */       PreparedStatement stmt = null;
/*      */ 
/*  640 */       int resultCount = 0;
/*  641 */       String s = null;
/*      */       try
/*      */       {
/*  644 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  646 */         if (this._log.isDebugEnabled()) {
/*  647 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  649 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  652 */         int col = 1;
/*  653 */         stmt.setInt(col++, pk.getRequestLineId());
/*      */ 
/*  656 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  660 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  664 */         closeStatement(stmt);
/*  665 */         closeConnection();
/*      */ 
/*  667 */         if (timer != null)
/*  668 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*      */   {
/*  695 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  697 */     PreparedStatement stmt = null;
/*  698 */     ResultSet resultSet = null;
/*      */ 
/*  700 */     int itemCount = 0;
/*      */ 
/*  702 */     VirementRequestGroupEVO owningEVO = null;
/*  703 */     Iterator ownersIter = owners.iterator();
/*  704 */     while (ownersIter.hasNext())
/*      */     {
/*  706 */       owningEVO = (VirementRequestGroupEVO)ownersIter.next();
/*  707 */       owningEVO.setLinesAllItemsLoaded(true);
/*      */     }
/*  709 */     ownersIter = owners.iterator();
/*  710 */     owningEVO = (VirementRequestGroupEVO)ownersIter.next();
/*  711 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  715 */       stmt = getConnection().prepareStatement("select VIREMENT_REQUEST_LINE.REQUEST_LINE_ID,VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID,VIREMENT_REQUEST_LINE.TARGET,VIREMENT_REQUEST_LINE.SPREAD_PROFILE_ID,VIREMENT_REQUEST_LINE.LINE_IDX,VIREMENT_REQUEST_LINE.TRANSFER_VALUE,VIREMENT_REQUEST_LINE.DATA_TYPE_ID,VIREMENT_REQUEST_LINE.DIM0,VIREMENT_REQUEST_LINE.DIM1,VIREMENT_REQUEST_LINE.DIM2,VIREMENT_REQUEST_LINE.DIM3,VIREMENT_REQUEST_LINE.DIM4,VIREMENT_REQUEST_LINE.DIM5,VIREMENT_REQUEST_LINE.DIM6,VIREMENT_REQUEST_LINE.DIM7,VIREMENT_REQUEST_LINE.DIM8,VIREMENT_REQUEST_LINE.DIM9,VIREMENT_REQUEST_LINE.REPEAT_VALUE,VIREMENT_REQUEST_LINE.UPDATED_BY_USER_ID,VIREMENT_REQUEST_LINE.UPDATED_TIME,VIREMENT_REQUEST_LINE.CREATED_TIME from VIREMENT_REQUEST_LINE,VIREMENT_REQUEST_GROUP,VIREMENT_REQUEST where 1=1 and VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID = VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID ,VIREMENT_REQUEST_LINE.REQUEST_LINE_ID");
/*      */ 
/*  717 */       int col = 1;
/*  718 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  720 */       resultSet = stmt.executeQuery();
/*      */ 
/*  723 */       while (resultSet.next())
/*      */       {
/*  725 */         itemCount++;
/*  726 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  731 */         while (this.mDetails.getRequestGroupId() != owningEVO.getRequestGroupId())
/*      */         {
/*  735 */           if (!ownersIter.hasNext())
/*      */           {
/*  737 */             this._log.debug("bulkGetAll", "can't find owning [RequestGroupId=" + this.mDetails.getRequestGroupId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  741 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  742 */             ownersIter = owners.iterator();
/*  743 */             while (ownersIter.hasNext())
/*      */             {
/*  745 */               owningEVO = (VirementRequestGroupEVO)ownersIter.next();
/*  746 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  748 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  750 */           owningEVO = (VirementRequestGroupEVO)ownersIter.next();
/*      */         }
/*  752 */         if (owningEVO.getLines() == null)
/*      */         {
/*  754 */           theseItems = new ArrayList();
/*  755 */           owningEVO.setLines(theseItems);
/*  756 */           owningEVO.setLinesAllItemsLoaded(true);
/*      */         }
/*  758 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  761 */       if (timer != null) {
/*  762 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  765 */       if ((itemCount > 0) && (dependants.indexOf("<32>") > -1))
/*      */       {
/*  767 */         getVirementLineSpreadDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  771 */       throw handleSQLException("select VIREMENT_REQUEST_LINE.REQUEST_LINE_ID,VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID,VIREMENT_REQUEST_LINE.TARGET,VIREMENT_REQUEST_LINE.SPREAD_PROFILE_ID,VIREMENT_REQUEST_LINE.LINE_IDX,VIREMENT_REQUEST_LINE.TRANSFER_VALUE,VIREMENT_REQUEST_LINE.DATA_TYPE_ID,VIREMENT_REQUEST_LINE.DIM0,VIREMENT_REQUEST_LINE.DIM1,VIREMENT_REQUEST_LINE.DIM2,VIREMENT_REQUEST_LINE.DIM3,VIREMENT_REQUEST_LINE.DIM4,VIREMENT_REQUEST_LINE.DIM5,VIREMENT_REQUEST_LINE.DIM6,VIREMENT_REQUEST_LINE.DIM7,VIREMENT_REQUEST_LINE.DIM8,VIREMENT_REQUEST_LINE.DIM9,VIREMENT_REQUEST_LINE.REPEAT_VALUE,VIREMENT_REQUEST_LINE.UPDATED_BY_USER_ID,VIREMENT_REQUEST_LINE.UPDATED_TIME,VIREMENT_REQUEST_LINE.CREATED_TIME from VIREMENT_REQUEST_LINE,VIREMENT_REQUEST_GROUP,VIREMENT_REQUEST where 1=1 and VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID = VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID ,VIREMENT_REQUEST_LINE.REQUEST_LINE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  775 */       closeResultSet(resultSet);
/*  776 */       closeStatement(stmt);
/*  777 */       closeConnection();
/*      */ 
/*  779 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectRequestGroupId, String dependants, Collection currentList)
/*      */   {
/*  804 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  805 */     PreparedStatement stmt = null;
/*  806 */     ResultSet resultSet = null;
/*      */ 
/*  808 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  812 */       stmt = getConnection().prepareStatement("select VIREMENT_REQUEST_LINE.REQUEST_LINE_ID,VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID,VIREMENT_REQUEST_LINE.TARGET,VIREMENT_REQUEST_LINE.SPREAD_PROFILE_ID,VIREMENT_REQUEST_LINE.LINE_IDX,VIREMENT_REQUEST_LINE.TRANSFER_VALUE,VIREMENT_REQUEST_LINE.DATA_TYPE_ID,VIREMENT_REQUEST_LINE.DIM0,VIREMENT_REQUEST_LINE.DIM1,VIREMENT_REQUEST_LINE.DIM2,VIREMENT_REQUEST_LINE.DIM3,VIREMENT_REQUEST_LINE.DIM4,VIREMENT_REQUEST_LINE.DIM5,VIREMENT_REQUEST_LINE.DIM6,VIREMENT_REQUEST_LINE.DIM7,VIREMENT_REQUEST_LINE.DIM8,VIREMENT_REQUEST_LINE.DIM9,VIREMENT_REQUEST_LINE.REPEAT_VALUE,VIREMENT_REQUEST_LINE.UPDATED_BY_USER_ID,VIREMENT_REQUEST_LINE.UPDATED_TIME,VIREMENT_REQUEST_LINE.CREATED_TIME from VIREMENT_REQUEST_LINE where    REQUEST_GROUP_ID = ? ");
/*      */ 
/*  814 */       int col = 1;
/*  815 */       stmt.setInt(col++, selectRequestGroupId);
/*      */ 
/*  817 */       resultSet = stmt.executeQuery();
/*      */ 
/*  819 */       while (resultSet.next())
/*      */       {
/*  821 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  824 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  827 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  830 */       if (currentList != null)
/*      */       {
/*  833 */         ListIterator iter = items.listIterator();
/*  834 */         VirementRequestLineEVO currentEVO = null;
/*  835 */         VirementRequestLineEVO newEVO = null;
/*  836 */         while (iter.hasNext())
/*      */         {
/*  838 */           newEVO = (VirementRequestLineEVO)iter.next();
/*  839 */           Iterator iter2 = currentList.iterator();
/*  840 */           while (iter2.hasNext())
/*      */           {
/*  842 */             currentEVO = (VirementRequestLineEVO)iter2.next();
/*  843 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  845 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  851 */         Iterator iter2 = currentList.iterator();
/*  852 */         while (iter2.hasNext())
/*      */         {
/*  854 */           currentEVO = (VirementRequestLineEVO)iter2.next();
/*  855 */           if (currentEVO.insertPending()) {
/*  856 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  860 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  864 */       throw handleSQLException("select VIREMENT_REQUEST_LINE.REQUEST_LINE_ID,VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID,VIREMENT_REQUEST_LINE.TARGET,VIREMENT_REQUEST_LINE.SPREAD_PROFILE_ID,VIREMENT_REQUEST_LINE.LINE_IDX,VIREMENT_REQUEST_LINE.TRANSFER_VALUE,VIREMENT_REQUEST_LINE.DATA_TYPE_ID,VIREMENT_REQUEST_LINE.DIM0,VIREMENT_REQUEST_LINE.DIM1,VIREMENT_REQUEST_LINE.DIM2,VIREMENT_REQUEST_LINE.DIM3,VIREMENT_REQUEST_LINE.DIM4,VIREMENT_REQUEST_LINE.DIM5,VIREMENT_REQUEST_LINE.DIM6,VIREMENT_REQUEST_LINE.DIM7,VIREMENT_REQUEST_LINE.DIM8,VIREMENT_REQUEST_LINE.DIM9,VIREMENT_REQUEST_LINE.REPEAT_VALUE,VIREMENT_REQUEST_LINE.UPDATED_BY_USER_ID,VIREMENT_REQUEST_LINE.UPDATED_TIME,VIREMENT_REQUEST_LINE.CREATED_TIME from VIREMENT_REQUEST_LINE where    REQUEST_GROUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  868 */       closeResultSet(resultSet);
/*  869 */       closeStatement(stmt);
/*  870 */       closeConnection();
/*      */ 
/*  872 */       if (timer != null) {
/*  873 */         timer.logDebug("getAll", " RequestGroupId=" + selectRequestGroupId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  878 */     return items;
/*      */   }
/*      */ 
/*      */   public VirementRequestLineEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  895 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  898 */     if (this.mDetails == null) {
/*  899 */       doLoad(((VirementRequestLineCK)paramCK).getVirementRequestLinePK());
/*      */     }
/*  901 */     else if (!this.mDetails.getPK().equals(((VirementRequestLineCK)paramCK).getVirementRequestLinePK())) {
/*  902 */       doLoad(((VirementRequestLineCK)paramCK).getVirementRequestLinePK());
/*      */     }
/*      */ 
/*  905 */     if ((dependants.indexOf("<32>") > -1) && (!this.mDetails.isSpreadsAllItemsLoaded()))
/*      */     {
/*  910 */       this.mDetails.setSpreads(getVirementLineSpreadDAO().getAll(this.mDetails.getRequestLineId(), dependants, this.mDetails.getSpreads()));
/*      */ 
/*  917 */       this.mDetails.setSpreadsAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  920 */     if ((paramCK instanceof VirementLineSpreadCK))
/*      */     {
/*  922 */       if (this.mDetails.getSpreads() == null) {
/*  923 */         this.mDetails.loadSpreadsItem(getVirementLineSpreadDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  926 */         VirementLineSpreadPK pk = ((VirementLineSpreadCK)paramCK).getVirementLineSpreadPK();
/*  927 */         VirementLineSpreadEVO evo = this.mDetails.getSpreadsItem(pk);
/*  928 */         if (evo == null) {
/*  929 */           this.mDetails.loadSpreadsItem(getVirementLineSpreadDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  934 */     VirementRequestLineEVO details = new VirementRequestLineEVO();
/*  935 */     details = this.mDetails.deepClone();
/*      */ 
/*  937 */     if (timer != null) {
/*  938 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  940 */     return details;
/*      */   }
/*      */ 
/*      */   public VirementRequestLineEVO getDetails(ModelCK paramCK, VirementRequestLineEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  946 */     VirementRequestLineEVO savedEVO = this.mDetails;
/*  947 */     this.mDetails = paramEVO;
/*  948 */     VirementRequestLineEVO newEVO = getDetails(paramCK, dependants);
/*  949 */     this.mDetails = savedEVO;
/*  950 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public VirementRequestLineEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  956 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  960 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  963 */     VirementRequestLineEVO details = this.mDetails.deepClone();
/*      */ 
/*  965 */     if (timer != null) {
/*  966 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  968 */     return details;
/*      */   }
/*      */ 
/*      */   protected VirementLineSpreadDAO getVirementLineSpreadDAO()
/*      */   {
/*  977 */     if (this.mVirementLineSpreadDAO == null)
/*      */     {
/*  979 */       if (this.mDataSource != null)
/*  980 */         this.mVirementLineSpreadDAO = new VirementLineSpreadDAO(this.mDataSource);
/*      */       else {
/*  982 */         this.mVirementLineSpreadDAO = new VirementLineSpreadDAO(getConnection());
/*      */       }
/*      */     }
/*  985 */     return this.mVirementLineSpreadDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  990 */     return "VirementRequestLine";
/*      */   }
/*      */ 
/*      */   public VirementRequestLineRefImpl getRef(VirementRequestLinePK paramVirementRequestLinePK)
/*      */   {
/*  995 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  996 */     PreparedStatement stmt = null;
/*  997 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1000 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,VIREMENT_REQUEST.REQUEST_ID,VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID from VIREMENT_REQUEST_LINE,MODEL,VIREMENT_REQUEST,VIREMENT_REQUEST_GROUP where 1=1 and VIREMENT_REQUEST_LINE.REQUEST_LINE_ID = ? and VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID = VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID and VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID = VIREMENT_REQUEST.REQUEST_GROUP_ID and VIREMENT_REQUEST.REQUEST_ID = MODEL.REQUEST_ID");
/* 1001 */       int col = 1;
/* 1002 */       stmt.setInt(col++, paramVirementRequestLinePK.getRequestLineId());
/*      */ 
/* 1004 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1006 */       if (!resultSet.next()) {
/* 1007 */         throw new RuntimeException(getEntityName() + " getRef " + paramVirementRequestLinePK + " not found");
/*      */       }
/* 1009 */       col = 2;
/* 1010 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1014 */       VirementRequestPK newVirementRequestPK = new VirementRequestPK(resultSet.getInt(col++));
/*      */ 
/* 1018 */       VirementRequestGroupPK newVirementRequestGroupPK = new VirementRequestGroupPK(resultSet.getInt(col++));
/*      */ 
/* 1022 */       String textVirementRequestLine = "";
/* 1023 */       VirementRequestLineCK ckVirementRequestLine = new VirementRequestLineCK(newModelPK, newVirementRequestPK, newVirementRequestGroupPK, paramVirementRequestLinePK);
/*      */ 
/* 1030 */       VirementRequestLineRefImpl localVirementRequestLineRefImpl = new VirementRequestLineRefImpl(ckVirementRequestLine, textVirementRequestLine);
/*      */       return localVirementRequestLineRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1035 */       throw handleSQLException(paramVirementRequestLinePK, "select 0,MODEL.MODEL_ID,VIREMENT_REQUEST.REQUEST_ID,VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID from VIREMENT_REQUEST_LINE,MODEL,VIREMENT_REQUEST,VIREMENT_REQUEST_GROUP where 1=1 and VIREMENT_REQUEST_LINE.REQUEST_LINE_ID = ? and VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID = VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID and VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID = VIREMENT_REQUEST.REQUEST_GROUP_ID and VIREMENT_REQUEST.REQUEST_ID = MODEL.REQUEST_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1039 */       closeResultSet(resultSet);
/* 1040 */       closeStatement(stmt);
/* 1041 */       closeConnection();
/*      */ 
/* 1043 */       if (timer != null)
/* 1044 */         timer.logDebug("getRef", paramVirementRequestLinePK); 
/* 1044 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1056 */     if (c == null)
/* 1057 */       return;
/* 1058 */     Iterator iter = c.iterator();
/* 1059 */     while (iter.hasNext())
/*      */     {
/* 1061 */       VirementRequestLineEVO evo = (VirementRequestLineEVO)iter.next();
/* 1062 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(VirementRequestLineEVO evo, String dependants)
/*      */   {
/* 1076 */     if (evo.insertPending()) {
/* 1077 */       return;
/*      */     }
/* 1079 */     if (evo.getRequestLineId() < 1) {
/* 1080 */       return;
/*      */     }
/*      */ 
/* 1084 */     if (dependants.indexOf("<32>") > -1)
/*      */     {
/* 1087 */       if (!evo.isSpreadsAllItemsLoaded())
/*      */       {
/* 1089 */         evo.setSpreads(getVirementLineSpreadDAO().getAll(evo.getRequestLineId(), dependants, evo.getSpreads()));
/*      */ 
/* 1096 */         evo.setSpreadsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.virement.VirementRequestLineDAO
 * JD-Core Version:    0.6.0
 */