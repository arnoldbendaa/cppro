/*      */ package com.cedar.cp.ejb.impl.model.act;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.act.ActivitiesForCycleandElementELO;
/*      */ import com.cedar.cp.dto.model.act.ActivityDetailsELO;
/*      */ import com.cedar.cp.dto.model.act.ActivityFullDetailsELO;
/*      */ import com.cedar.cp.dto.model.act.BudgetActivityCK;
/*      */ import com.cedar.cp.dto.model.act.BudgetActivityLinkCK;
/*      */ import com.cedar.cp.dto.model.act.BudgetActivityLinkPK;
/*      */ import com.cedar.cp.dto.model.act.BudgetActivityLinkRefImpl;
/*      */ import com.cedar.cp.dto.model.act.BudgetActivityPK;
/*      */ import com.cedar.cp.dto.model.act.BudgetActivityRefImpl;
/*      */ import com.cedar.cp.dto.user.UserPK;
/*      */ import com.cedar.cp.dto.user.UserRefImpl;
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
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ import oracle.sql.CLOB;
/*      */ 
/*      */ public class BudgetActivityDAO extends AbstractDAO
/*      */ {
/*   49 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_LOBS = "select  DETAILS from BUDGET_ACTIVITY where    BUDGET_ACTIVITY_ID = ? for update";
/*      */   private static final String SQL_SELECT_COLUMNS = "select BUDGET_ACTIVITY.DETAILS,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY.MODEL_ID,BUDGET_ACTIVITY.USER_ID,BUDGET_ACTIVITY.CREATED,BUDGET_ACTIVITY.ACTIVITY_TYPE,BUDGET_ACTIVITY.DESCRIPTION,BUDGET_ACTIVITY.UNDO_ENTRY,BUDGET_ACTIVITY.OWNER_ID";
/*      */   protected static final String SQL_LOAD = " from BUDGET_ACTIVITY where    BUDGET_ACTIVITY_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into BUDGET_ACTIVITY ( BUDGET_ACTIVITY_ID,MODEL_ID,USER_ID,CREATED,ACTIVITY_TYPE,DESCRIPTION,DETAILS,UNDO_ENTRY,OWNER_ID) values ( ?,?,?,?,?,?,empty_clob(),?,?)";
/*      */   protected static final String SQL_STORE = "update BUDGET_ACTIVITY set MODEL_ID = ?,USER_ID = ?,CREATED = ?,ACTIVITY_TYPE = ?,DESCRIPTION = ?,UNDO_ENTRY = ?,OWNER_ID = ? where    BUDGET_ACTIVITY_ID = ? ";
/*  414 */   protected static String SQL_ACTIVITIES_FOR_CYCLEAND_ELEMENT = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID      ,USR.USER_ID      ,USR.NAME      ,BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID      ,BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID      ,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID      ,BUDGET_ACTIVITY.CREATED      ,BUDGET_ACTIVITY.ACTIVITY_TYPE      ,BUDGET_ACTIVITY.DESCRIPTION      ,BUDGET_ACTIVITY.USER_ID      ,BUDGET_ACTIVITY.OWNER_ID      ,USR.FULL_NAME from BUDGET_ACTIVITY    ,MODEL    ,USR    ,BUDGET_ACTIVITY_LINK where 1=1   and BUDGET_ACTIVITY.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_ACTIVITY.USER_ID = USR.NAME and BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID = BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID and BUDGET_ACTIVITY.MODEL_ID = ? and (( BUDGET_ACTIVITY_LINK.BUDGET_CYCLE_ID = ? or BUDGET_ACTIVITY_LINK.BUDGET_CYCLE_ID is null) and BUDGET_ACTIVITY_LINK.STRUCTURE_ELEMENT_ID = ?) order by BUDGET_ACTIVITY.CREATED desc";
/*      */ 
/*  582 */   protected static String SQL_ACTIVITY_DETAILS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID      ,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID      ,BUDGET_ACTIVITY.CREATED      ,BUDGET_ACTIVITY.ACTIVITY_TYPE      ,BUDGET_ACTIVITY.DESCRIPTION      ,BUDGET_ACTIVITY.USER_ID      ,BUDGET_ACTIVITY.OWNER_ID from BUDGET_ACTIVITY    ,MODEL where 1=1   and BUDGET_ACTIVITY.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID = ?";
/*      */ 
/*  706 */   protected static String SQL_ACTIVITY_FULL_DETAILS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID      ,USR.USER_ID      ,USR.NAME      ,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID      ,BUDGET_ACTIVITY.CREATED      ,BUDGET_ACTIVITY.ACTIVITY_TYPE      ,BUDGET_ACTIVITY.DESCRIPTION      ,BUDGET_ACTIVITY.DETAILS      ,BUDGET_ACTIVITY.USER_ID      ,BUDGET_ACTIVITY.UNDO_ENTRY      ,BUDGET_ACTIVITY.OWNER_ID      ,USR.FULL_NAME      ,MODEL.MODEL_ID from BUDGET_ACTIVITY    ,MODEL    ,USR where 1=1   and BUDGET_ACTIVITY.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID = ? and BUDGET_ACTIVITY.USER_ID = USR.NAME";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from BUDGET_ACTIVITY where    BUDGET_ACTIVITY_ID = ? ";
/*  995 */   private static String[][] SQL_DELETE_CHILDREN = { { "BUDGET_ACTIVITY_LINK", "delete from BUDGET_ACTIVITY_LINK where     BUDGET_ACTIVITY_LINK.BUDGET_ACTIVITY_ID = ? " } };
/*      */ 
/* 1004 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/* 1008 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from BUDGET_ACTIVITY where 1=1 and BUDGET_ACTIVITY.MODEL_ID = ? order by  BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID";
/*      */   protected static final String SQL_GET_ALL = " from BUDGET_ACTIVITY where    MODEL_ID = ? ";
/*      */   protected BudgetActivityLinkDAO mBudgetActivityLinkDAO;
/*      */   protected BudgetActivityEVO mDetails;
/*      */   private CLOB mDetailsClob;
/*      */ 
/*      */   public BudgetActivityDAO(Connection connection)
/*      */   {
/*   56 */     super(connection);
/*      */   }
/*      */ 
/*      */   public BudgetActivityDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public BudgetActivityDAO(DataSource ds)
/*      */   {
/*   72 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected BudgetActivityPK getPK()
/*      */   {
/*   80 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(BudgetActivityEVO details)
/*      */   {
/*   89 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private void selectLobs(BudgetActivityEVO evo_)
/*      */   {
/*  103 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  105 */     PreparedStatement stmt = null;
/*  106 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  110 */       stmt = getConnection().prepareStatement("select  DETAILS from BUDGET_ACTIVITY where    BUDGET_ACTIVITY_ID = ? for update");
/*      */ 
/*  112 */       putEvoKeysToJdbc(evo_, stmt, 1);
/*      */ 
/*  114 */       resultSet = stmt.executeQuery();
/*      */ 
/*  116 */       int col = 1;
/*  117 */       while (resultSet.next())
/*      */       {
/*  119 */         this.mDetailsClob = ((CLOB)resultSet.getClob(col++));
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  124 */       throw handleSQLException(evo_.getPK(), "select  DETAILS from BUDGET_ACTIVITY where    BUDGET_ACTIVITY_ID = ? for update", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  128 */       closeResultSet(resultSet);
/*  129 */       closeStatement(stmt);
/*      */ 
/*  131 */       if (timer != null)
/*  132 */         timer.logDebug("selectLobs", evo_.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void putLobs(BudgetActivityEVO evo_) throws SQLException
/*      */   {
/*  138 */     updateClob(this.mDetailsClob, evo_.getDetails());
/*      */   }
/*      */ 
/*      */   private BudgetActivityEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  156 */     int col = 1;
/*  157 */     this.mDetailsClob = ((CLOB)resultSet_.getClob(col++));
/*  158 */     BudgetActivityEVO evo = new BudgetActivityEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getTimestamp(col++), resultSet_.getInt(col++), resultSet_.getString(col++), clobToString(this.mDetailsClob), getWrappedBooleanFromJdbc(resultSet_, col++), resultSet_.getInt(col++), null);
/*      */ 
/*  171 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(BudgetActivityEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  176 */     int col = startCol_;
/*  177 */     stmt_.setInt(col++, evo_.getBudgetActivityId());
/*  178 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(BudgetActivityEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  183 */     int col = startCol_;
/*  184 */     stmt_.setInt(col++, evo_.getModelId());
/*  185 */     stmt_.setString(col++, evo_.getUserId());
/*  186 */     stmt_.setTimestamp(col++, evo_.getCreated());
/*  187 */     stmt_.setInt(col++, evo_.getActivityType());
/*  188 */     stmt_.setString(col++, evo_.getDescription());
/*      */ 
/*  190 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getUndoEntry());
/*  191 */     stmt_.setInt(col++, evo_.getOwnerId());
/*  192 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(BudgetActivityPK pk)
/*      */     throws ValidationException
/*      */   {
/*  208 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  210 */     PreparedStatement stmt = null;
/*  211 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  215 */       stmt = getConnection().prepareStatement("select BUDGET_ACTIVITY.DETAILS,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY.MODEL_ID,BUDGET_ACTIVITY.USER_ID,BUDGET_ACTIVITY.CREATED,BUDGET_ACTIVITY.ACTIVITY_TYPE,BUDGET_ACTIVITY.DESCRIPTION,BUDGET_ACTIVITY.UNDO_ENTRY,BUDGET_ACTIVITY.OWNER_ID from BUDGET_ACTIVITY where    BUDGET_ACTIVITY_ID = ? ");
/*      */ 
/*  218 */       int col = 1;
/*  219 */       stmt.setInt(col++, pk.getBudgetActivityId());
/*      */ 
/*  221 */       resultSet = stmt.executeQuery();
/*      */ 
/*  223 */       if (!resultSet.next()) {
/*  224 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  227 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  228 */       if (this.mDetails.isModified())
/*  229 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  233 */       throw handleSQLException(pk, "select BUDGET_ACTIVITY.DETAILS,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY.MODEL_ID,BUDGET_ACTIVITY.USER_ID,BUDGET_ACTIVITY.CREATED,BUDGET_ACTIVITY.ACTIVITY_TYPE,BUDGET_ACTIVITY.DESCRIPTION,BUDGET_ACTIVITY.UNDO_ENTRY,BUDGET_ACTIVITY.OWNER_ID from BUDGET_ACTIVITY where    BUDGET_ACTIVITY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  237 */       closeResultSet(resultSet);
/*  238 */       closeStatement(stmt);
/*  239 */       closeConnection();
/*      */ 
/*  241 */       if (timer != null)
/*  242 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  277 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  278 */     this.mDetails.postCreateInit();
/*      */ 
/*  280 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  284 */       stmt = getConnection().prepareStatement("insert into BUDGET_ACTIVITY ( BUDGET_ACTIVITY_ID,MODEL_ID,USER_ID,CREATED,ACTIVITY_TYPE,DESCRIPTION,DETAILS,UNDO_ENTRY,OWNER_ID) values ( ?,?,?,?,?,?,empty_clob(),?,?)");
/*      */ 
/*  287 */       int col = 1;
/*  288 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  289 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  292 */       int resultCount = stmt.executeUpdate();
/*  293 */       if (resultCount != 1)
/*      */       {
/*  295 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  300 */       selectLobs(this.mDetails);
/*  301 */       this._log.debug("doCreate", "calling putLobs");
/*  302 */       putLobs(this.mDetails);
/*      */ 
/*  304 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  308 */       throw handleSQLException(this.mDetails.getPK(), "insert into BUDGET_ACTIVITY ( BUDGET_ACTIVITY_ID,MODEL_ID,USER_ID,CREATED,ACTIVITY_TYPE,DESCRIPTION,DETAILS,UNDO_ENTRY,OWNER_ID) values ( ?,?,?,?,?,?,empty_clob(),?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  312 */       closeStatement(stmt);
/*  313 */       closeConnection();
/*      */ 
/*  315 */       if (timer != null) {
/*  316 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  322 */       getBudgetActivityLinkDAO().update(this.mDetails.getActivityLinksMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  328 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  354 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  358 */     PreparedStatement stmt = null;
/*      */ 
/*  360 */     boolean mainChanged = this.mDetails.isModified();
/*  361 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  365 */       if (getBudgetActivityLinkDAO().update(this.mDetails.getActivityLinksMap())) {
/*  366 */         dependantChanged = true;
/*      */       }
/*  368 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  370 */         stmt = getConnection().prepareStatement("update BUDGET_ACTIVITY set MODEL_ID = ?,USER_ID = ?,CREATED = ?,ACTIVITY_TYPE = ?,DESCRIPTION = ?,UNDO_ENTRY = ?,OWNER_ID = ? where    BUDGET_ACTIVITY_ID = ? ");
/*      */ 
/*  372 */         selectLobs(this.mDetails);
/*  373 */         putLobs(this.mDetails);
/*      */ 
/*  376 */         int col = 1;
/*  377 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  378 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  381 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  384 */         if (resultCount != 1) {
/*  385 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  388 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  397 */       throw handleSQLException(getPK(), "update BUDGET_ACTIVITY set MODEL_ID = ?,USER_ID = ?,CREATED = ?,ACTIVITY_TYPE = ?,DESCRIPTION = ?,UNDO_ENTRY = ?,OWNER_ID = ? where    BUDGET_ACTIVITY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  401 */       closeStatement(stmt);
/*  402 */       closeConnection();
/*      */ 
/*  404 */       if ((timer != null) && (
/*  405 */         (mainChanged) || (dependantChanged)))
/*  406 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public ActivitiesForCycleandElementELO getActivitiesForCycleandElement(int param1, Integer param2, int param3)
/*      */   {
/*  462 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  463 */     PreparedStatement stmt = null;
/*  464 */     ResultSet resultSet = null;
/*  465 */     ActivitiesForCycleandElementELO results = new ActivitiesForCycleandElementELO();
/*      */     try
/*      */     {
/*  468 */       stmt = getConnection().prepareStatement(SQL_ACTIVITIES_FOR_CYCLEAND_ELEMENT);
/*  469 */       int col = 1;
/*  470 */       stmt.setInt(col++, param1);
/*  471 */       setWrappedPrimitiveToJdbc(stmt, col++, param2);
/*  472 */       stmt.setInt(col++, param3);
/*  473 */       resultSet = stmt.executeQuery();
/*  474 */       while (resultSet.next())
/*      */       {
/*  476 */         col = 2;
/*      */ 
/*  479 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  482 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  485 */         BudgetActivityPK pkBudgetActivity = new BudgetActivityPK(resultSet.getInt(col++));
/*      */ 
/*  488 */         String textBudgetActivity = "";
/*      */ 
/*  491 */         UserPK pkUser = new UserPK(resultSet.getInt(col++));
/*      */ 
/*  494 */         String textUser = resultSet.getString(col++);
/*      */ 
/*  496 */         BudgetActivityLinkPK pkBudgetActivityLink = new BudgetActivityLinkPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  500 */         String textBudgetActivityLink = "";
/*      */ 
/*  504 */         BudgetActivityCK ckBudgetActivity = new BudgetActivityCK(pkModel, pkBudgetActivity);
/*      */ 
/*  510 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  516 */         BudgetActivityRefImpl erBudgetActivity = new BudgetActivityRefImpl(ckBudgetActivity, textBudgetActivity);
/*      */ 
/*  522 */         UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
/*      */ 
/*  528 */         BudgetActivityLinkRefImpl erBudgetActivityLink = new BudgetActivityLinkRefImpl(pkBudgetActivityLink, textBudgetActivityLink);
/*      */ 
/*  533 */         int col1 = resultSet.getInt(col++);
/*  534 */         Timestamp col2 = resultSet.getTimestamp(col++);
/*  535 */         int col3 = resultSet.getInt(col++);
/*  536 */         String col4 = resultSet.getString(col++);
/*  537 */         String col5 = resultSet.getString(col++);
/*  538 */         int col6 = resultSet.getInt(col++);
/*  539 */         String col7 = resultSet.getString(col++);
/*      */ 
/*  542 */         results.add(erBudgetActivity, erModel, erUser, erBudgetActivityLink, col1, col2, col3, col4, col5, col6, col7);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  559 */       throw handleSQLException(SQL_ACTIVITIES_FOR_CYCLEAND_ELEMENT, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  563 */       closeResultSet(resultSet);
/*  564 */       closeStatement(stmt);
/*  565 */       closeConnection();
/*      */     }
/*      */ 
/*  568 */     if (timer != null) {
/*  569 */       timer.logDebug("getActivitiesForCycleandElement", " ModelId=" + param1 + ",BudgetCycleId=" + param2 + ",StructureElementId=" + param3 + " items=" + results.size());
/*      */     }
/*      */ 
/*  576 */     return results;
/*      */   }
/*      */ 
/*      */   public ActivityDetailsELO getActivityDetails(int param1)
/*      */   {
/*  617 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  618 */     PreparedStatement stmt = null;
/*  619 */     ResultSet resultSet = null;
/*  620 */     ActivityDetailsELO results = new ActivityDetailsELO();
/*      */     try
/*      */     {
/*  623 */       stmt = getConnection().prepareStatement(SQL_ACTIVITY_DETAILS);
/*  624 */       int col = 1;
/*  625 */       stmt.setInt(col++, param1);
/*  626 */       resultSet = stmt.executeQuery();
/*  627 */       while (resultSet.next())
/*      */       {
/*  629 */         col = 2;
/*      */ 
/*  632 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  635 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  638 */         BudgetActivityPK pkBudgetActivity = new BudgetActivityPK(resultSet.getInt(col++));
/*      */ 
/*  641 */         String textBudgetActivity = "";
/*      */ 
/*  646 */         BudgetActivityCK ckBudgetActivity = new BudgetActivityCK(pkModel, pkBudgetActivity);
/*      */ 
/*  652 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  658 */         BudgetActivityRefImpl erBudgetActivity = new BudgetActivityRefImpl(ckBudgetActivity, textBudgetActivity);
/*      */ 
/*  663 */         int col1 = resultSet.getInt(col++);
/*  664 */         Timestamp col2 = resultSet.getTimestamp(col++);
/*  665 */         int col3 = resultSet.getInt(col++);
/*  666 */         String col4 = resultSet.getString(col++);
/*  667 */         String col5 = resultSet.getString(col++);
/*  668 */         int col6 = resultSet.getInt(col++);
/*      */ 
/*  671 */         results.add(erBudgetActivity, erModel, col1, col2, col3, col4, col5, col6);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  685 */       throw handleSQLException(SQL_ACTIVITY_DETAILS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  689 */       closeResultSet(resultSet);
/*  690 */       closeStatement(stmt);
/*  691 */       closeConnection();
/*      */     }
/*      */ 
/*  694 */     if (timer != null) {
/*  695 */       timer.logDebug("getActivityDetails", " BudgetActivityId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  700 */     return results;
/*      */   }
/*      */ 
/*      */   public ActivityFullDetailsELO getActivityFullDetails(int param1)
/*      */   {
/*  749 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  750 */     PreparedStatement stmt = null;
/*  751 */     ResultSet resultSet = null;
/*  752 */     ActivityFullDetailsELO results = new ActivityFullDetailsELO();
/*      */     try
/*      */     {
/*  755 */       stmt = getConnection().prepareStatement(SQL_ACTIVITY_FULL_DETAILS);
/*  756 */       int col = 1;
/*  757 */       stmt.setInt(col++, param1);
/*  758 */       resultSet = stmt.executeQuery();
/*  759 */       while (resultSet.next())
/*      */       {
/*  761 */         col = 2;
/*      */ 
/*  764 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  767 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  770 */         BudgetActivityPK pkBudgetActivity = new BudgetActivityPK(resultSet.getInt(col++));
/*      */ 
/*  773 */         String textBudgetActivity = "";
/*      */ 
/*  776 */         UserPK pkUser = new UserPK(resultSet.getInt(col++));
/*      */ 
/*  779 */         String textUser = resultSet.getString(col++);
/*      */ 
/*  783 */         BudgetActivityCK ckBudgetActivity = new BudgetActivityCK(pkModel, pkBudgetActivity);
/*      */ 
/*  789 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  795 */         BudgetActivityRefImpl erBudgetActivity = new BudgetActivityRefImpl(ckBudgetActivity, textBudgetActivity);
/*      */ 
/*  801 */         UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
/*      */ 
/*  806 */         int col1 = resultSet.getInt(col++);
/*  807 */         Timestamp col2 = resultSet.getTimestamp(col++);
/*  808 */         int col3 = resultSet.getInt(col++);
/*  809 */         String col4 = resultSet.getString(col++);
/*  810 */         CLOB col5Clob = (CLOB)resultSet.getClob(col++);
/*  811 */         String col5 = clobToString(col5Clob);
/*  812 */         String col6 = resultSet.getString(col++);
/*  813 */         Boolean col7 = getWrappedBooleanFromJdbc(resultSet, col++);
/*  814 */         int col8 = resultSet.getInt(col++);
/*  815 */         String col9 = resultSet.getString(col++);
/*  816 */         int col10 = resultSet.getInt(col++);
/*      */ 
/*  819 */         results.add(erBudgetActivity, erModel, erUser, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  838 */       throw handleSQLException(SQL_ACTIVITY_FULL_DETAILS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  842 */       closeResultSet(resultSet);
/*  843 */       closeStatement(stmt);
/*  844 */       closeConnection();
/*      */     }
/*      */ 
/*  847 */     if (timer != null) {
/*  848 */       timer.logDebug("getActivityFullDetails", " BudgetActivityId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  853 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  870 */     if (items == null) {
/*  871 */       return false;
/*      */     }
/*  873 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  874 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  876 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  880 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  881 */       while (iter3.hasNext())
/*      */       {
/*  883 */         this.mDetails = ((BudgetActivityEVO)iter3.next());
/*  884 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  886 */         somethingChanged = true;
/*      */ 
/*  889 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  893 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  894 */       while (iter2.hasNext())
/*      */       {
/*  896 */         this.mDetails = ((BudgetActivityEVO)iter2.next());
/*      */ 
/*  899 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  901 */         somethingChanged = true;
/*      */ 
/*  904 */         if (deleteStmt == null) {
/*  905 */           deleteStmt = getConnection().prepareStatement("delete from BUDGET_ACTIVITY where    BUDGET_ACTIVITY_ID = ? ");
/*      */         }
/*      */ 
/*  908 */         int col = 1;
/*  909 */         deleteStmt.setInt(col++, this.mDetails.getBudgetActivityId());
/*      */ 
/*  911 */         if (this._log.isDebugEnabled()) {
/*  912 */           this._log.debug("update", "BudgetActivity deleting BudgetActivityId=" + this.mDetails.getBudgetActivityId());
/*      */         }
/*      */ 
/*  917 */         deleteStmt.addBatch();
/*      */ 
/*  920 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  925 */       if (deleteStmt != null)
/*      */       {
/*  927 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  929 */         deleteStmt.executeBatch();
/*      */ 
/*  931 */         if (timer2 != null) {
/*  932 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  936 */       Iterator iter1 = items.values().iterator();
/*  937 */       while (iter1.hasNext())
/*      */       {
/*  939 */         this.mDetails = ((BudgetActivityEVO)iter1.next());
/*      */ 
/*  941 */         if (this.mDetails.insertPending())
/*      */         {
/*  943 */           somethingChanged = true;
/*  944 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  947 */         if (this.mDetails.isModified())
/*      */         {
/*  949 */           somethingChanged = true;
/*  950 */           doStore(); continue;
/*      */         }
/*      */ 
/*  954 */         if ((this.mDetails.deletePending()) || 
/*  960 */           (!getBudgetActivityLinkDAO().update(this.mDetails.getActivityLinksMap()))) continue;
/*  961 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  973 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  977 */       throw handleSQLException("delete from BUDGET_ACTIVITY where    BUDGET_ACTIVITY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  981 */       if (deleteStmt != null)
/*      */       {
/*  983 */         closeStatement(deleteStmt);
/*  984 */         closeConnection();
/*      */       }
/*      */ 
/*  987 */       this.mDetails = null;
/*      */ 
/*  989 */       if ((somethingChanged) && 
/*  990 */         (timer != null))
/*  991 */         timer.logDebug("update", "collection"); 
/*  991 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(BudgetActivityPK pk)
/*      */   {
/* 1017 */     Set emptyStrings = Collections.emptySet();
/* 1018 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(BudgetActivityPK pk, Set<String> exclusionTables)
/*      */   {
/* 1024 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1026 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1028 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1030 */       PreparedStatement stmt = null;
/*      */ 
/* 1032 */       int resultCount = 0;
/* 1033 */       String s = null;
/*      */       try
/*      */       {
/* 1036 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 1038 */         if (this._log.isDebugEnabled()) {
/* 1039 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1041 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1044 */         int col = 1;
/* 1045 */         stmt.setInt(col++, pk.getBudgetActivityId());
/*      */ 
/* 1048 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1052 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1056 */         closeStatement(stmt);
/* 1057 */         closeConnection();
/*      */ 
/* 1059 */         if (timer != null) {
/* 1060 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1064 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1066 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1068 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1070 */       PreparedStatement stmt = null;
/*      */ 
/* 1072 */       int resultCount = 0;
/* 1073 */       String s = null;
/*      */       try
/*      */       {
/* 1076 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1078 */         if (this._log.isDebugEnabled()) {
/* 1079 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1081 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1084 */         int col = 1;
/* 1085 */         stmt.setInt(col++, pk.getBudgetActivityId());
/*      */ 
/* 1088 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1092 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1096 */         closeStatement(stmt);
/* 1097 */         closeConnection();
/*      */ 
/* 1099 */         if (timer != null)
/* 1100 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants)
/*      */   {
/* 1120 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1122 */     PreparedStatement stmt = null;
/* 1123 */     ResultSet resultSet = null;
/*      */ 
/* 1125 */     int itemCount = 0;
/*      */ 
/* 1127 */     Collection theseItems = new ArrayList();
/* 1128 */     owningEVO.setBudgetActivities(theseItems);
/* 1129 */     owningEVO.setBudgetActivitiesAllItemsLoaded(true);
/*      */     try
/*      */     {
/* 1133 */       stmt = getConnection().prepareStatement("select BUDGET_ACTIVITY.DETAILS,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY.MODEL_ID,BUDGET_ACTIVITY.USER_ID,BUDGET_ACTIVITY.CREATED,BUDGET_ACTIVITY.ACTIVITY_TYPE,BUDGET_ACTIVITY.DESCRIPTION,BUDGET_ACTIVITY.UNDO_ENTRY,BUDGET_ACTIVITY.OWNER_ID from BUDGET_ACTIVITY where 1=1 and BUDGET_ACTIVITY.MODEL_ID = ? order by  BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID");
/*      */ 
/* 1135 */       int col = 1;
/* 1136 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/* 1138 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1141 */       while (resultSet.next())
/*      */       {
/* 1143 */         itemCount++;
/* 1144 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1146 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/* 1149 */       if (timer != null) {
/* 1150 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/* 1153 */       if ((itemCount > 0) && (dependants.indexOf("<28>") > -1))
/*      */       {
/* 1155 */         getBudgetActivityLinkDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/* 1159 */       throw handleSQLException("select BUDGET_ACTIVITY.DETAILS,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY.MODEL_ID,BUDGET_ACTIVITY.USER_ID,BUDGET_ACTIVITY.CREATED,BUDGET_ACTIVITY.ACTIVITY_TYPE,BUDGET_ACTIVITY.DESCRIPTION,BUDGET_ACTIVITY.UNDO_ENTRY,BUDGET_ACTIVITY.OWNER_ID from BUDGET_ACTIVITY where 1=1 and BUDGET_ACTIVITY.MODEL_ID = ? order by  BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1163 */       closeResultSet(resultSet);
/* 1164 */       closeStatement(stmt);
/* 1165 */       closeConnection();
/*      */ 
/* 1167 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectModelId, String dependants, Collection currentList)
/*      */   {
/* 1192 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1193 */     PreparedStatement stmt = null;
/* 1194 */     ResultSet resultSet = null;
/*      */ 
/* 1196 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/* 1200 */       stmt = getConnection().prepareStatement("select BUDGET_ACTIVITY.DETAILS,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY.MODEL_ID,BUDGET_ACTIVITY.USER_ID,BUDGET_ACTIVITY.CREATED,BUDGET_ACTIVITY.ACTIVITY_TYPE,BUDGET_ACTIVITY.DESCRIPTION,BUDGET_ACTIVITY.UNDO_ENTRY,BUDGET_ACTIVITY.OWNER_ID from BUDGET_ACTIVITY where    MODEL_ID = ? ");
/*      */ 
/* 1202 */       int col = 1;
/* 1203 */       stmt.setInt(col++, selectModelId);
/*      */ 
/* 1205 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1207 */       while (resultSet.next())
/*      */       {
/* 1209 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/* 1212 */         getDependants(this.mDetails, dependants);
/*      */ 
/* 1215 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/* 1218 */       if (currentList != null)
/*      */       {
/* 1221 */         ListIterator iter = items.listIterator();
/* 1222 */         BudgetActivityEVO currentEVO = null;
/* 1223 */         BudgetActivityEVO newEVO = null;
/* 1224 */         while (iter.hasNext())
/*      */         {
/* 1226 */           newEVO = (BudgetActivityEVO)iter.next();
/* 1227 */           Iterator iter2 = currentList.iterator();
/* 1228 */           while (iter2.hasNext())
/*      */           {
/* 1230 */             currentEVO = (BudgetActivityEVO)iter2.next();
/* 1231 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/* 1233 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1239 */         Iterator iter2 = currentList.iterator();
/* 1240 */         while (iter2.hasNext())
/*      */         {
/* 1242 */           currentEVO = (BudgetActivityEVO)iter2.next();
/* 1243 */           if (currentEVO.insertPending()) {
/* 1244 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/* 1248 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1252 */       throw handleSQLException("select BUDGET_ACTIVITY.DETAILS,BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID,BUDGET_ACTIVITY.MODEL_ID,BUDGET_ACTIVITY.USER_ID,BUDGET_ACTIVITY.CREATED,BUDGET_ACTIVITY.ACTIVITY_TYPE,BUDGET_ACTIVITY.DESCRIPTION,BUDGET_ACTIVITY.UNDO_ENTRY,BUDGET_ACTIVITY.OWNER_ID from BUDGET_ACTIVITY where    MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1256 */       closeResultSet(resultSet);
/* 1257 */       closeStatement(stmt);
/* 1258 */       closeConnection();
/*      */ 
/* 1260 */       if (timer != null) {
/* 1261 */         timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1266 */     return items;
/*      */   }
/*      */ 
/*      */   public BudgetActivityEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1283 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1286 */     if (this.mDetails == null) {
/* 1287 */       doLoad(((BudgetActivityCK)paramCK).getBudgetActivityPK());
/*      */     }
/* 1289 */     else if (!this.mDetails.getPK().equals(((BudgetActivityCK)paramCK).getBudgetActivityPK())) {
/* 1290 */       doLoad(((BudgetActivityCK)paramCK).getBudgetActivityPK());
/*      */     }
/*      */ 
/* 1293 */     if ((dependants.indexOf("<28>") > -1) && (!this.mDetails.isActivityLinksAllItemsLoaded()))
/*      */     {
/* 1298 */       this.mDetails.setActivityLinks(getBudgetActivityLinkDAO().getAll(this.mDetails.getBudgetActivityId(), dependants, this.mDetails.getActivityLinks()));
/*      */ 
/* 1305 */       this.mDetails.setActivityLinksAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1308 */     if ((paramCK instanceof BudgetActivityLinkCK))
/*      */     {
/* 1310 */       if (this.mDetails.getActivityLinks() == null) {
/* 1311 */         this.mDetails.loadActivityLinksItem(getBudgetActivityLinkDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1314 */         BudgetActivityLinkPK pk = ((BudgetActivityLinkCK)paramCK).getBudgetActivityLinkPK();
/* 1315 */         BudgetActivityLinkEVO evo = this.mDetails.getActivityLinksItem(pk);
/* 1316 */         if (evo == null) {
/* 1317 */           this.mDetails.loadActivityLinksItem(getBudgetActivityLinkDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1322 */     BudgetActivityEVO details = new BudgetActivityEVO();
/* 1323 */     details = this.mDetails.deepClone();
/*      */ 
/* 1325 */     if (timer != null) {
/* 1326 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1328 */     return details;
/*      */   }
/*      */ 
/*      */   public BudgetActivityEVO getDetails(ModelCK paramCK, BudgetActivityEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1334 */     BudgetActivityEVO savedEVO = this.mDetails;
/* 1335 */     this.mDetails = paramEVO;
/* 1336 */     BudgetActivityEVO newEVO = getDetails(paramCK, dependants);
/* 1337 */     this.mDetails = savedEVO;
/* 1338 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public BudgetActivityEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1344 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1348 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1351 */     BudgetActivityEVO details = this.mDetails.deepClone();
/*      */ 
/* 1353 */     if (timer != null) {
/* 1354 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1356 */     return details;
/*      */   }
/*      */ 
/*      */   protected BudgetActivityLinkDAO getBudgetActivityLinkDAO()
/*      */   {
/* 1365 */     if (this.mBudgetActivityLinkDAO == null)
/*      */     {
/* 1367 */       if (this.mDataSource != null)
/* 1368 */         this.mBudgetActivityLinkDAO = new BudgetActivityLinkDAO(this.mDataSource);
/*      */       else {
/* 1370 */         this.mBudgetActivityLinkDAO = new BudgetActivityLinkDAO(getConnection());
/*      */       }
/*      */     }
/* 1373 */     return this.mBudgetActivityLinkDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1378 */     return "BudgetActivity";
/*      */   }
/*      */ 
/*      */   public BudgetActivityRefImpl getRef(BudgetActivityPK paramBudgetActivityPK)
/*      */   {
/* 1383 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1384 */     PreparedStatement stmt = null;
/* 1385 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1388 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID from BUDGET_ACTIVITY,MODEL where 1=1 and BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID = ? and BUDGET_ACTIVITY.MODEL_ID = MODEL.MODEL_ID");
/* 1389 */       int col = 1;
/* 1390 */       stmt.setInt(col++, paramBudgetActivityPK.getBudgetActivityId());
/*      */ 
/* 1392 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1394 */       if (!resultSet.next()) {
/* 1395 */         throw new RuntimeException(getEntityName() + " getRef " + paramBudgetActivityPK + " not found");
/*      */       }
/* 1397 */       col = 2;
/* 1398 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1402 */       String textBudgetActivity = "";
/* 1403 */       BudgetActivityCK ckBudgetActivity = new BudgetActivityCK(newModelPK, paramBudgetActivityPK);
/*      */ 
/* 1408 */       BudgetActivityRefImpl localBudgetActivityRefImpl = new BudgetActivityRefImpl(ckBudgetActivity, textBudgetActivity);
/*      */       return localBudgetActivityRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1413 */       throw handleSQLException(paramBudgetActivityPK, "select 0,MODEL.MODEL_ID from BUDGET_ACTIVITY,MODEL where 1=1 and BUDGET_ACTIVITY.BUDGET_ACTIVITY_ID = ? and BUDGET_ACTIVITY.MODEL_ID = MODEL.MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1417 */       closeResultSet(resultSet);
/* 1418 */       closeStatement(stmt);
/* 1419 */       closeConnection();
/*      */ 
/* 1421 */       if (timer != null)
/* 1422 */         timer.logDebug("getRef", paramBudgetActivityPK); 
/* 1422 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1434 */     if (c == null)
/* 1435 */       return;
/* 1436 */     Iterator iter = c.iterator();
/* 1437 */     while (iter.hasNext())
/*      */     {
/* 1439 */       BudgetActivityEVO evo = (BudgetActivityEVO)iter.next();
/* 1440 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(BudgetActivityEVO evo, String dependants)
/*      */   {
/* 1454 */     if (evo.insertPending()) {
/* 1455 */       return;
/*      */     }
/* 1457 */     if (evo.getBudgetActivityId() < 1) {
/* 1458 */       return;
/*      */     }
/*      */ 
/* 1462 */     if (dependants.indexOf("<28>") > -1)
/*      */     {
/* 1465 */       if (!evo.isActivityLinksAllItemsLoaded())
/*      */       {
/* 1467 */         evo.setActivityLinks(getBudgetActivityLinkDAO().getAll(evo.getBudgetActivityId(), dependants, evo.getActivityLinks()));
/*      */ 
/* 1474 */         evo.setActivityLinksAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.act.BudgetActivityDAO
 * JD-Core Version:    0.6.0
 */