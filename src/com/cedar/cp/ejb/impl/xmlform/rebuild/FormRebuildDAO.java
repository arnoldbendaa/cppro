/*      */ package com.cedar.cp.ejb.impl.xmlform.rebuild;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.xmlform.rebuild.AllBudgetCyclesInRebuildsELO;
/*      */ import com.cedar.cp.dto.xmlform.rebuild.AllFormRebuildsELO;
/*      */ import com.cedar.cp.dto.xmlform.rebuild.FormRebuildCK;
/*      */ import com.cedar.cp.dto.xmlform.rebuild.FormRebuildPK;
/*      */ import com.cedar.cp.dto.xmlform.rebuild.FormRebuildRefImpl;
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
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class FormRebuildDAO extends AbstractDAO
/*      */ {
/*   35 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select FORM_REBUILD.FORM_REBUILD_ID,FORM_REBUILD.MODEL_ID,FORM_REBUILD.VIS_ID,FORM_REBUILD.DESCRIPTION,FORM_REBUILD.LAST_SUBMIT,FORM_REBUILD.XMLFORM_ID,FORM_REBUILD.BUDGET_CYCLE_ID,FORM_REBUILD.STRUCTURE_ID0,FORM_REBUILD.STRUCTURE_ID1,FORM_REBUILD.STRUCTURE_ID2,FORM_REBUILD.STRUCTURE_ID3,FORM_REBUILD.STRUCTURE_ID4,FORM_REBUILD.STRUCTURE_ID5,FORM_REBUILD.STRUCTURE_ID6,FORM_REBUILD.STRUCTURE_ID7,FORM_REBUILD.STRUCTURE_ID8,FORM_REBUILD.STRUCTURE_ELEMENT_ID0,FORM_REBUILD.STRUCTURE_ELEMENT_ID1,FORM_REBUILD.STRUCTURE_ELEMENT_ID2,FORM_REBUILD.STRUCTURE_ELEMENT_ID3,FORM_REBUILD.STRUCTURE_ELEMENT_ID4,FORM_REBUILD.STRUCTURE_ELEMENT_ID5,FORM_REBUILD.STRUCTURE_ELEMENT_ID6,FORM_REBUILD.STRUCTURE_ELEMENT_ID7,FORM_REBUILD.STRUCTURE_ELEMENT_ID8,FORM_REBUILD.DATA_TYPE,FORM_REBUILD.UPDATED_BY_USER_ID,FORM_REBUILD.UPDATED_TIME,FORM_REBUILD.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from FORM_REBUILD where    FORM_REBUILD_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into FORM_REBUILD ( FORM_REBUILD_ID,MODEL_ID,VIS_ID,DESCRIPTION,LAST_SUBMIT,XMLFORM_ID,BUDGET_CYCLE_ID,STRUCTURE_ID0,STRUCTURE_ID1,STRUCTURE_ID2,STRUCTURE_ID3,STRUCTURE_ID4,STRUCTURE_ID5,STRUCTURE_ID6,STRUCTURE_ID7,STRUCTURE_ID8,STRUCTURE_ELEMENT_ID0,STRUCTURE_ELEMENT_ID1,STRUCTURE_ELEMENT_ID2,STRUCTURE_ELEMENT_ID3,STRUCTURE_ELEMENT_ID4,STRUCTURE_ELEMENT_ID5,STRUCTURE_ELEMENT_ID6,STRUCTURE_ELEMENT_ID7,STRUCTURE_ELEMENT_ID8,DATA_TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update FORM_REBUILD set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,LAST_SUBMIT = ?,XMLFORM_ID = ?,BUDGET_CYCLE_ID = ?,STRUCTURE_ID0 = ?,STRUCTURE_ID1 = ?,STRUCTURE_ID2 = ?,STRUCTURE_ID3 = ?,STRUCTURE_ID4 = ?,STRUCTURE_ID5 = ?,STRUCTURE_ID6 = ?,STRUCTURE_ID7 = ?,STRUCTURE_ID8 = ?,STRUCTURE_ELEMENT_ID0 = ?,STRUCTURE_ELEMENT_ID1 = ?,STRUCTURE_ELEMENT_ID2 = ?,STRUCTURE_ELEMENT_ID3 = ?,STRUCTURE_ELEMENT_ID4 = ?,STRUCTURE_ELEMENT_ID5 = ?,STRUCTURE_ELEMENT_ID6 = ?,STRUCTURE_ELEMENT_ID7 = ?,STRUCTURE_ELEMENT_ID8 = ?,DATA_TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    FORM_REBUILD_ID = ? ";
/*  450 */   protected static String SQL_ALL_FORM_REBUILDS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FORM_REBUILD.FORM_REBUILD_ID      ,FORM_REBUILD.VIS_ID      ,FORM_REBUILD.DESCRIPTION      ,FORM_REBUILD.LAST_SUBMIT from FORM_REBUILD    ,MODEL where 1=1   and FORM_REBUILD.MODEL_ID = MODEL.MODEL_ID ";
/*      */   protected static String SQL_ALL_FORM_REBUILDS_FOR_USER = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FORM_REBUILD.FORM_REBUILD_ID      ,FORM_REBUILD.VIS_ID      ,FORM_REBUILD.DESCRIPTION      ,FORM_REBUILD.LAST_SUBMIT from FORM_REBUILD    ,MODEL where model.model_id in (select distinct model_id from budget_user where user_id = ?) and FORM_REBUILD.MODEL_ID = MODEL.MODEL_ID ";
/*  559 */   protected static String SQL_ALL_BUDGET_CYCLES_IN_REBUILDS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FORM_REBUILD.FORM_REBUILD_ID      ,FORM_REBUILD.VIS_ID      ,FORM_REBUILD.BUDGET_CYCLE_ID from FORM_REBUILD    ,MODEL where 1=1   and FORM_REBUILD.MODEL_ID = MODEL.MODEL_ID ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from FORM_REBUILD where    FORM_REBUILD_ID = ? ";
/*      */   public static final String SQL_BULK_GET_ALL = " from FORM_REBUILD where 1=1 and FORM_REBUILD.MODEL_ID = ? order by  FORM_REBUILD.FORM_REBUILD_ID";
/*      */   protected static final String SQL_GET_ALL = " from FORM_REBUILD where    MODEL_ID = ? ";
/* 1043 */   public static String sUpdateLastSubmit = "update form_rebuild set last_submit = sysdate where form_rebuild_id = ?";
/*      */   protected FormRebuildEVO mDetails;
/*      */ 
/*      */   public FormRebuildDAO(Connection connection)
/*      */   {
/*   42 */     super(connection);
/*      */   }
/*      */ 
/*      */   public FormRebuildDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public FormRebuildDAO(DataSource ds)
/*      */   {
/*   58 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected FormRebuildPK getPK()
/*      */   {
/*   66 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(FormRebuildEVO details)
/*      */   {
/*   75 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private FormRebuildEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  115 */     int col = 1;
/*  116 */     FormRebuildEVO evo = new FormRebuildEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getTimestamp(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++));
/*      */ 
/*  145 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  146 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  147 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  148 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(FormRebuildEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  153 */     int col = startCol_;
/*  154 */     stmt_.setInt(col++, evo_.getFormRebuildId());
/*  155 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(FormRebuildEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  160 */     int col = startCol_;
/*  161 */     stmt_.setInt(col++, evo_.getModelId());
/*  162 */     stmt_.setString(col++, evo_.getVisId());
/*  163 */     stmt_.setString(col++, evo_.getDescription());
/*  164 */     stmt_.setTimestamp(col++, evo_.getLastSubmit());
/*  165 */     stmt_.setInt(col++, evo_.getXmlformId());
/*  166 */     stmt_.setInt(col++, evo_.getBudgetCycleId());
/*  167 */     stmt_.setInt(col++, evo_.getStructureId0());
/*  168 */     stmt_.setInt(col++, evo_.getStructureId1());
/*  169 */     stmt_.setInt(col++, evo_.getStructureId2());
/*  170 */     stmt_.setInt(col++, evo_.getStructureId3());
/*  171 */     stmt_.setInt(col++, evo_.getStructureId4());
/*  172 */     stmt_.setInt(col++, evo_.getStructureId5());
/*  173 */     stmt_.setInt(col++, evo_.getStructureId6());
/*  174 */     stmt_.setInt(col++, evo_.getStructureId7());
/*  175 */     stmt_.setInt(col++, evo_.getStructureId8());
/*  176 */     stmt_.setInt(col++, evo_.getStructureElementId0());
/*  177 */     stmt_.setInt(col++, evo_.getStructureElementId1());
/*  178 */     stmt_.setInt(col++, evo_.getStructureElementId2());
/*  179 */     stmt_.setInt(col++, evo_.getStructureElementId3());
/*  180 */     stmt_.setInt(col++, evo_.getStructureElementId4());
/*  181 */     stmt_.setInt(col++, evo_.getStructureElementId5());
/*  182 */     stmt_.setInt(col++, evo_.getStructureElementId6());
/*  183 */     stmt_.setInt(col++, evo_.getStructureElementId7());
/*  184 */     stmt_.setInt(col++, evo_.getStructureElementId8());
/*  185 */     stmt_.setString(col++, evo_.getDataType());
/*  186 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  187 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  188 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  189 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(FormRebuildPK pk)
/*      */     throws ValidationException
/*      */   {
/*  205 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  207 */     PreparedStatement stmt = null;
/*  208 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  212 */       stmt = getConnection().prepareStatement("select FORM_REBUILD.FORM_REBUILD_ID,FORM_REBUILD.MODEL_ID,FORM_REBUILD.VIS_ID,FORM_REBUILD.DESCRIPTION,FORM_REBUILD.LAST_SUBMIT,FORM_REBUILD.XMLFORM_ID,FORM_REBUILD.BUDGET_CYCLE_ID,FORM_REBUILD.STRUCTURE_ID0,FORM_REBUILD.STRUCTURE_ID1,FORM_REBUILD.STRUCTURE_ID2,FORM_REBUILD.STRUCTURE_ID3,FORM_REBUILD.STRUCTURE_ID4,FORM_REBUILD.STRUCTURE_ID5,FORM_REBUILD.STRUCTURE_ID6,FORM_REBUILD.STRUCTURE_ID7,FORM_REBUILD.STRUCTURE_ID8,FORM_REBUILD.STRUCTURE_ELEMENT_ID0,FORM_REBUILD.STRUCTURE_ELEMENT_ID1,FORM_REBUILD.STRUCTURE_ELEMENT_ID2,FORM_REBUILD.STRUCTURE_ELEMENT_ID3,FORM_REBUILD.STRUCTURE_ELEMENT_ID4,FORM_REBUILD.STRUCTURE_ELEMENT_ID5,FORM_REBUILD.STRUCTURE_ELEMENT_ID6,FORM_REBUILD.STRUCTURE_ELEMENT_ID7,FORM_REBUILD.STRUCTURE_ELEMENT_ID8,FORM_REBUILD.DATA_TYPE,FORM_REBUILD.UPDATED_BY_USER_ID,FORM_REBUILD.UPDATED_TIME,FORM_REBUILD.CREATED_TIME from FORM_REBUILD where    FORM_REBUILD_ID = ? ");
/*      */ 
/*  215 */       int col = 1;
/*  216 */       stmt.setInt(col++, pk.getFormRebuildId());
/*      */ 
/*  218 */       resultSet = stmt.executeQuery();
/*      */ 
/*  220 */       if (!resultSet.next()) {
/*  221 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  224 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  225 */       if (this.mDetails.isModified())
/*  226 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  230 */       throw handleSQLException(pk, "select FORM_REBUILD.FORM_REBUILD_ID,FORM_REBUILD.MODEL_ID,FORM_REBUILD.VIS_ID,FORM_REBUILD.DESCRIPTION,FORM_REBUILD.LAST_SUBMIT,FORM_REBUILD.XMLFORM_ID,FORM_REBUILD.BUDGET_CYCLE_ID,FORM_REBUILD.STRUCTURE_ID0,FORM_REBUILD.STRUCTURE_ID1,FORM_REBUILD.STRUCTURE_ID2,FORM_REBUILD.STRUCTURE_ID3,FORM_REBUILD.STRUCTURE_ID4,FORM_REBUILD.STRUCTURE_ID5,FORM_REBUILD.STRUCTURE_ID6,FORM_REBUILD.STRUCTURE_ID7,FORM_REBUILD.STRUCTURE_ID8,FORM_REBUILD.STRUCTURE_ELEMENT_ID0,FORM_REBUILD.STRUCTURE_ELEMENT_ID1,FORM_REBUILD.STRUCTURE_ELEMENT_ID2,FORM_REBUILD.STRUCTURE_ELEMENT_ID3,FORM_REBUILD.STRUCTURE_ELEMENT_ID4,FORM_REBUILD.STRUCTURE_ELEMENT_ID5,FORM_REBUILD.STRUCTURE_ELEMENT_ID6,FORM_REBUILD.STRUCTURE_ELEMENT_ID7,FORM_REBUILD.STRUCTURE_ELEMENT_ID8,FORM_REBUILD.DATA_TYPE,FORM_REBUILD.UPDATED_BY_USER_ID,FORM_REBUILD.UPDATED_TIME,FORM_REBUILD.CREATED_TIME from FORM_REBUILD where    FORM_REBUILD_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  234 */       closeResultSet(resultSet);
/*  235 */       closeStatement(stmt);
/*  236 */       closeConnection();
/*      */ 
/*  238 */       if (timer != null)
/*  239 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  314 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  315 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  320 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  321 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  322 */       stmt = getConnection().prepareStatement("insert into FORM_REBUILD ( FORM_REBUILD_ID,MODEL_ID,VIS_ID,DESCRIPTION,LAST_SUBMIT,XMLFORM_ID,BUDGET_CYCLE_ID,STRUCTURE_ID0,STRUCTURE_ID1,STRUCTURE_ID2,STRUCTURE_ID3,STRUCTURE_ID4,STRUCTURE_ID5,STRUCTURE_ID6,STRUCTURE_ID7,STRUCTURE_ID8,STRUCTURE_ELEMENT_ID0,STRUCTURE_ELEMENT_ID1,STRUCTURE_ELEMENT_ID2,STRUCTURE_ELEMENT_ID3,STRUCTURE_ELEMENT_ID4,STRUCTURE_ELEMENT_ID5,STRUCTURE_ELEMENT_ID6,STRUCTURE_ELEMENT_ID7,STRUCTURE_ELEMENT_ID8,DATA_TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  325 */       int col = 1;
/*  326 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  327 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  330 */       int resultCount = stmt.executeUpdate();
/*  331 */       if (resultCount != 1)
/*      */       {
/*  333 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  336 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  340 */       throw handleSQLException(this.mDetails.getPK(), "insert into FORM_REBUILD ( FORM_REBUILD_ID,MODEL_ID,VIS_ID,DESCRIPTION,LAST_SUBMIT,XMLFORM_ID,BUDGET_CYCLE_ID,STRUCTURE_ID0,STRUCTURE_ID1,STRUCTURE_ID2,STRUCTURE_ID3,STRUCTURE_ID4,STRUCTURE_ID5,STRUCTURE_ID6,STRUCTURE_ID7,STRUCTURE_ID8,STRUCTURE_ELEMENT_ID0,STRUCTURE_ELEMENT_ID1,STRUCTURE_ELEMENT_ID2,STRUCTURE_ELEMENT_ID3,STRUCTURE_ELEMENT_ID4,STRUCTURE_ELEMENT_ID5,STRUCTURE_ELEMENT_ID6,STRUCTURE_ELEMENT_ID7,STRUCTURE_ELEMENT_ID8,DATA_TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  344 */       closeStatement(stmt);
/*  345 */       closeConnection();
/*      */ 
/*  347 */       if (timer != null)
/*  348 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  395 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  399 */     PreparedStatement stmt = null;
/*      */ 
/*  401 */     boolean mainChanged = this.mDetails.isModified();
/*  402 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  405 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  408 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  409 */         stmt = getConnection().prepareStatement("update FORM_REBUILD set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,LAST_SUBMIT = ?,XMLFORM_ID = ?,BUDGET_CYCLE_ID = ?,STRUCTURE_ID0 = ?,STRUCTURE_ID1 = ?,STRUCTURE_ID2 = ?,STRUCTURE_ID3 = ?,STRUCTURE_ID4 = ?,STRUCTURE_ID5 = ?,STRUCTURE_ID6 = ?,STRUCTURE_ID7 = ?,STRUCTURE_ID8 = ?,STRUCTURE_ELEMENT_ID0 = ?,STRUCTURE_ELEMENT_ID1 = ?,STRUCTURE_ELEMENT_ID2 = ?,STRUCTURE_ELEMENT_ID3 = ?,STRUCTURE_ELEMENT_ID4 = ?,STRUCTURE_ELEMENT_ID5 = ?,STRUCTURE_ELEMENT_ID6 = ?,STRUCTURE_ELEMENT_ID7 = ?,STRUCTURE_ELEMENT_ID8 = ?,DATA_TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    FORM_REBUILD_ID = ? ");
/*      */ 
/*  412 */         int col = 1;
/*  413 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  414 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  417 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  420 */         if (resultCount != 1) {
/*  421 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  424 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  433 */       throw handleSQLException(getPK(), "update FORM_REBUILD set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,LAST_SUBMIT = ?,XMLFORM_ID = ?,BUDGET_CYCLE_ID = ?,STRUCTURE_ID0 = ?,STRUCTURE_ID1 = ?,STRUCTURE_ID2 = ?,STRUCTURE_ID3 = ?,STRUCTURE_ID4 = ?,STRUCTURE_ID5 = ?,STRUCTURE_ID6 = ?,STRUCTURE_ID7 = ?,STRUCTURE_ID8 = ?,STRUCTURE_ELEMENT_ID0 = ?,STRUCTURE_ELEMENT_ID1 = ?,STRUCTURE_ELEMENT_ID2 = ?,STRUCTURE_ELEMENT_ID3 = ?,STRUCTURE_ELEMENT_ID4 = ?,STRUCTURE_ELEMENT_ID5 = ?,STRUCTURE_ELEMENT_ID6 = ?,STRUCTURE_ELEMENT_ID7 = ?,STRUCTURE_ELEMENT_ID8 = ?,DATA_TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    FORM_REBUILD_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  437 */       closeStatement(stmt);
/*  438 */       closeConnection();
/*      */ 
/*  440 */       if ((timer != null) && (
/*  441 */         (mainChanged) || (dependantChanged)))
/*  442 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllFormRebuildsELO getAllFormRebuilds()
/*      */   {
/*  480 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  481 */     PreparedStatement stmt = null;
/*  482 */     ResultSet resultSet = null;
/*  483 */     AllFormRebuildsELO results = new AllFormRebuildsELO();
/*      */     try
/*      */     {
/*  486 */       stmt = getConnection().prepareStatement(SQL_ALL_FORM_REBUILDS);
/*  487 */       int col = 1;
/*  488 */       resultSet = stmt.executeQuery();
/*  489 */       while (resultSet.next())
/*      */       {
/*  491 */         col = 2;
/*      */ 
/*  494 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  497 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  500 */         FormRebuildPK pkFormRebuild = new FormRebuildPK(resultSet.getInt(col++));
/*      */ 
/*  503 */         String textFormRebuild = resultSet.getString(col++);
/*      */ 
/*  508 */         FormRebuildCK ckFormRebuild = new FormRebuildCK(pkModel, pkFormRebuild);
/*      */ 
/*  514 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  520 */         FormRebuildRefImpl erFormRebuild = new FormRebuildRefImpl(ckFormRebuild, textFormRebuild);
/*      */ 
/*  525 */         String col1 = resultSet.getString(col++);
/*  526 */         Timestamp col2 = resultSet.getTimestamp(col++);
/*      */ 
/*  529 */         results.add(erFormRebuild, erModel, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  539 */       throw handleSQLException(SQL_ALL_FORM_REBUILDS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  543 */       closeResultSet(resultSet);
/*  544 */       closeStatement(stmt);
/*  545 */       closeConnection();
/*      */     }
/*      */ 
/*  548 */     if (timer != null) {
/*  549 */       timer.logDebug("getAllFormRebuilds", " items=" + results.size());
/*      */     }
/*      */ 
/*  553 */     return results;
/*      */   }
/*      */ 
/*      */   public AllBudgetCyclesInRebuildsELO getAllBudgetCyclesInRebuilds()
/*      */   {
/*  588 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  589 */     PreparedStatement stmt = null;
/*  590 */     ResultSet resultSet = null;
/*  591 */     AllBudgetCyclesInRebuildsELO results = new AllBudgetCyclesInRebuildsELO();
/*      */     try
/*      */     {
/*  594 */       stmt = getConnection().prepareStatement(SQL_ALL_BUDGET_CYCLES_IN_REBUILDS);
/*  595 */       int col = 1;
/*  596 */       resultSet = stmt.executeQuery();
/*  597 */       while (resultSet.next())
/*      */       {
/*  599 */         col = 2;
/*      */ 
/*  602 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  605 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  608 */         FormRebuildPK pkFormRebuild = new FormRebuildPK(resultSet.getInt(col++));
/*      */ 
/*  611 */         String textFormRebuild = resultSet.getString(col++);
/*      */ 
/*  616 */         FormRebuildCK ckFormRebuild = new FormRebuildCK(pkModel, pkFormRebuild);
/*      */ 
/*  622 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  628 */         FormRebuildRefImpl erFormRebuild = new FormRebuildRefImpl(ckFormRebuild, textFormRebuild);
/*      */ 
/*  633 */         int col1 = resultSet.getInt(col++);
/*      */ 
/*  636 */         results.add(erFormRebuild, erModel, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  645 */       throw handleSQLException(SQL_ALL_BUDGET_CYCLES_IN_REBUILDS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  649 */       closeResultSet(resultSet);
/*  650 */       closeStatement(stmt);
/*  651 */       closeConnection();
/*      */     }
/*      */ 
/*  654 */     if (timer != null) {
/*  655 */       timer.logDebug("getAllBudgetCyclesInRebuilds", " items=" + results.size());
/*      */     }
/*      */ 
/*  659 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  676 */     if (items == null) {
/*  677 */       return false;
/*      */     }
/*  679 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  680 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  682 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  687 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  688 */       while (iter2.hasNext())
/*      */       {
/*  690 */         this.mDetails = ((FormRebuildEVO)iter2.next());
/*      */ 
/*  693 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  695 */         somethingChanged = true;
/*      */ 
/*  698 */         if (deleteStmt == null) {
/*  699 */           deleteStmt = getConnection().prepareStatement("delete from FORM_REBUILD where    FORM_REBUILD_ID = ? ");
/*      */         }
/*      */ 
/*  702 */         int col = 1;
/*  703 */         deleteStmt.setInt(col++, this.mDetails.getFormRebuildId());
/*      */ 
/*  705 */         if (this._log.isDebugEnabled()) {
/*  706 */           this._log.debug("update", "FormRebuild deleting FormRebuildId=" + this.mDetails.getFormRebuildId());
/*      */         }
/*      */ 
/*  711 */         deleteStmt.addBatch();
/*      */ 
/*  714 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  719 */       if (deleteStmt != null)
/*      */       {
/*  721 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  723 */         deleteStmt.executeBatch();
/*      */ 
/*  725 */         if (timer2 != null) {
/*  726 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  730 */       Iterator iter1 = items.values().iterator();
/*  731 */       while (iter1.hasNext())
/*      */       {
/*  733 */         this.mDetails = ((FormRebuildEVO)iter1.next());
/*      */ 
/*  735 */         if (this.mDetails.insertPending())
/*      */         {
/*  737 */           somethingChanged = true;
/*  738 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  741 */         if (!this.mDetails.isModified())
/*      */           continue;
/*  743 */         somethingChanged = true;
/*  744 */         doStore();
/*      */       }
/*      */ 
/*  755 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  759 */       throw handleSQLException("delete from FORM_REBUILD where    FORM_REBUILD_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  763 */       if (deleteStmt != null)
/*      */       {
/*  765 */         closeStatement(deleteStmt);
/*  766 */         closeConnection();
/*      */       }
/*      */ 
/*  769 */       this.mDetails = null;
/*      */ 
/*  771 */       if ((somethingChanged) && 
/*  772 */         (timer != null))
/*  773 */         timer.logDebug("update", "collection"); 
/*  773 */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants)
/*      */   {
/*  792 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  794 */     PreparedStatement stmt = null;
/*  795 */     ResultSet resultSet = null;
/*      */ 
/*  797 */     int itemCount = 0;
/*      */ 
/*  799 */     Collection theseItems = new ArrayList();
/*  800 */     owningEVO.setFormRebuilds(theseItems);
/*  801 */     owningEVO.setFormRebuildsAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  805 */       stmt = getConnection().prepareStatement("select FORM_REBUILD.FORM_REBUILD_ID,FORM_REBUILD.MODEL_ID,FORM_REBUILD.VIS_ID,FORM_REBUILD.DESCRIPTION,FORM_REBUILD.LAST_SUBMIT,FORM_REBUILD.XMLFORM_ID,FORM_REBUILD.BUDGET_CYCLE_ID,FORM_REBUILD.STRUCTURE_ID0,FORM_REBUILD.STRUCTURE_ID1,FORM_REBUILD.STRUCTURE_ID2,FORM_REBUILD.STRUCTURE_ID3,FORM_REBUILD.STRUCTURE_ID4,FORM_REBUILD.STRUCTURE_ID5,FORM_REBUILD.STRUCTURE_ID6,FORM_REBUILD.STRUCTURE_ID7,FORM_REBUILD.STRUCTURE_ID8,FORM_REBUILD.STRUCTURE_ELEMENT_ID0,FORM_REBUILD.STRUCTURE_ELEMENT_ID1,FORM_REBUILD.STRUCTURE_ELEMENT_ID2,FORM_REBUILD.STRUCTURE_ELEMENT_ID3,FORM_REBUILD.STRUCTURE_ELEMENT_ID4,FORM_REBUILD.STRUCTURE_ELEMENT_ID5,FORM_REBUILD.STRUCTURE_ELEMENT_ID6,FORM_REBUILD.STRUCTURE_ELEMENT_ID7,FORM_REBUILD.STRUCTURE_ELEMENT_ID8,FORM_REBUILD.DATA_TYPE,FORM_REBUILD.UPDATED_BY_USER_ID,FORM_REBUILD.UPDATED_TIME,FORM_REBUILD.CREATED_TIME from FORM_REBUILD where 1=1 and FORM_REBUILD.MODEL_ID = ? order by  FORM_REBUILD.FORM_REBUILD_ID");
/*      */ 
/*  807 */       int col = 1;
/*  808 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  810 */       resultSet = stmt.executeQuery();
/*      */ 
/*  813 */       while (resultSet.next())
/*      */       {
/*  815 */         itemCount++;
/*  816 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  818 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  821 */       if (timer != null) {
/*  822 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  827 */       throw handleSQLException("select FORM_REBUILD.FORM_REBUILD_ID,FORM_REBUILD.MODEL_ID,FORM_REBUILD.VIS_ID,FORM_REBUILD.DESCRIPTION,FORM_REBUILD.LAST_SUBMIT,FORM_REBUILD.XMLFORM_ID,FORM_REBUILD.BUDGET_CYCLE_ID,FORM_REBUILD.STRUCTURE_ID0,FORM_REBUILD.STRUCTURE_ID1,FORM_REBUILD.STRUCTURE_ID2,FORM_REBUILD.STRUCTURE_ID3,FORM_REBUILD.STRUCTURE_ID4,FORM_REBUILD.STRUCTURE_ID5,FORM_REBUILD.STRUCTURE_ID6,FORM_REBUILD.STRUCTURE_ID7,FORM_REBUILD.STRUCTURE_ID8,FORM_REBUILD.STRUCTURE_ELEMENT_ID0,FORM_REBUILD.STRUCTURE_ELEMENT_ID1,FORM_REBUILD.STRUCTURE_ELEMENT_ID2,FORM_REBUILD.STRUCTURE_ELEMENT_ID3,FORM_REBUILD.STRUCTURE_ELEMENT_ID4,FORM_REBUILD.STRUCTURE_ELEMENT_ID5,FORM_REBUILD.STRUCTURE_ELEMENT_ID6,FORM_REBUILD.STRUCTURE_ELEMENT_ID7,FORM_REBUILD.STRUCTURE_ELEMENT_ID8,FORM_REBUILD.DATA_TYPE,FORM_REBUILD.UPDATED_BY_USER_ID,FORM_REBUILD.UPDATED_TIME,FORM_REBUILD.CREATED_TIME from FORM_REBUILD where 1=1 and FORM_REBUILD.MODEL_ID = ? order by  FORM_REBUILD.FORM_REBUILD_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  831 */       closeResultSet(resultSet);
/*  832 */       closeStatement(stmt);
/*  833 */       closeConnection();
/*      */ 
/*  835 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectModelId, String dependants, Collection currentList)
/*      */   {
/*  860 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  861 */     PreparedStatement stmt = null;
/*  862 */     ResultSet resultSet = null;
/*      */ 
/*  864 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  868 */       stmt = getConnection().prepareStatement("select FORM_REBUILD.FORM_REBUILD_ID,FORM_REBUILD.MODEL_ID,FORM_REBUILD.VIS_ID,FORM_REBUILD.DESCRIPTION,FORM_REBUILD.LAST_SUBMIT,FORM_REBUILD.XMLFORM_ID,FORM_REBUILD.BUDGET_CYCLE_ID,FORM_REBUILD.STRUCTURE_ID0,FORM_REBUILD.STRUCTURE_ID1,FORM_REBUILD.STRUCTURE_ID2,FORM_REBUILD.STRUCTURE_ID3,FORM_REBUILD.STRUCTURE_ID4,FORM_REBUILD.STRUCTURE_ID5,FORM_REBUILD.STRUCTURE_ID6,FORM_REBUILD.STRUCTURE_ID7,FORM_REBUILD.STRUCTURE_ID8,FORM_REBUILD.STRUCTURE_ELEMENT_ID0,FORM_REBUILD.STRUCTURE_ELEMENT_ID1,FORM_REBUILD.STRUCTURE_ELEMENT_ID2,FORM_REBUILD.STRUCTURE_ELEMENT_ID3,FORM_REBUILD.STRUCTURE_ELEMENT_ID4,FORM_REBUILD.STRUCTURE_ELEMENT_ID5,FORM_REBUILD.STRUCTURE_ELEMENT_ID6,FORM_REBUILD.STRUCTURE_ELEMENT_ID7,FORM_REBUILD.STRUCTURE_ELEMENT_ID8,FORM_REBUILD.DATA_TYPE,FORM_REBUILD.UPDATED_BY_USER_ID,FORM_REBUILD.UPDATED_TIME,FORM_REBUILD.CREATED_TIME from FORM_REBUILD where    MODEL_ID = ? ");
/*      */ 
/*  870 */       int col = 1;
/*  871 */       stmt.setInt(col++, selectModelId);
/*      */ 
/*  873 */       resultSet = stmt.executeQuery();
/*      */ 
/*  875 */       while (resultSet.next())
/*      */       {
/*  877 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  880 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  883 */       if (currentList != null)
/*      */       {
/*  886 */         ListIterator iter = items.listIterator();
/*  887 */         FormRebuildEVO currentEVO = null;
/*  888 */         FormRebuildEVO newEVO = null;
/*  889 */         while (iter.hasNext())
/*      */         {
/*  891 */           newEVO = (FormRebuildEVO)iter.next();
/*  892 */           Iterator iter2 = currentList.iterator();
/*  893 */           while (iter2.hasNext())
/*      */           {
/*  895 */             currentEVO = (FormRebuildEVO)iter2.next();
/*  896 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  898 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  904 */         Iterator iter2 = currentList.iterator();
/*  905 */         while (iter2.hasNext())
/*      */         {
/*  907 */           currentEVO = (FormRebuildEVO)iter2.next();
/*  908 */           if (currentEVO.insertPending()) {
/*  909 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  913 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  917 */       throw handleSQLException("select FORM_REBUILD.FORM_REBUILD_ID,FORM_REBUILD.MODEL_ID,FORM_REBUILD.VIS_ID,FORM_REBUILD.DESCRIPTION,FORM_REBUILD.LAST_SUBMIT,FORM_REBUILD.XMLFORM_ID,FORM_REBUILD.BUDGET_CYCLE_ID,FORM_REBUILD.STRUCTURE_ID0,FORM_REBUILD.STRUCTURE_ID1,FORM_REBUILD.STRUCTURE_ID2,FORM_REBUILD.STRUCTURE_ID3,FORM_REBUILD.STRUCTURE_ID4,FORM_REBUILD.STRUCTURE_ID5,FORM_REBUILD.STRUCTURE_ID6,FORM_REBUILD.STRUCTURE_ID7,FORM_REBUILD.STRUCTURE_ID8,FORM_REBUILD.STRUCTURE_ELEMENT_ID0,FORM_REBUILD.STRUCTURE_ELEMENT_ID1,FORM_REBUILD.STRUCTURE_ELEMENT_ID2,FORM_REBUILD.STRUCTURE_ELEMENT_ID3,FORM_REBUILD.STRUCTURE_ELEMENT_ID4,FORM_REBUILD.STRUCTURE_ELEMENT_ID5,FORM_REBUILD.STRUCTURE_ELEMENT_ID6,FORM_REBUILD.STRUCTURE_ELEMENT_ID7,FORM_REBUILD.STRUCTURE_ELEMENT_ID8,FORM_REBUILD.DATA_TYPE,FORM_REBUILD.UPDATED_BY_USER_ID,FORM_REBUILD.UPDATED_TIME,FORM_REBUILD.CREATED_TIME from FORM_REBUILD where    MODEL_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  921 */       closeResultSet(resultSet);
/*  922 */       closeStatement(stmt);
/*  923 */       closeConnection();
/*      */ 
/*  925 */       if (timer != null) {
/*  926 */         timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  931 */     return items;
/*      */   }
/*      */ 
/*      */   public FormRebuildEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  945 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  948 */     if (this.mDetails == null) {
/*  949 */       doLoad(((FormRebuildCK)paramCK).getFormRebuildPK());
/*      */     }
/*  951 */     else if (!this.mDetails.getPK().equals(((FormRebuildCK)paramCK).getFormRebuildPK())) {
/*  952 */       doLoad(((FormRebuildCK)paramCK).getFormRebuildPK());
/*      */     }
/*      */ 
/*  955 */     FormRebuildEVO details = new FormRebuildEVO();
/*  956 */     details = this.mDetails.deepClone();
/*      */ 
/*  958 */     if (timer != null) {
/*  959 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  961 */     return details;
/*      */   }
/*      */ 
/*      */   public FormRebuildEVO getDetails(ModelCK paramCK, FormRebuildEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  967 */     FormRebuildEVO savedEVO = this.mDetails;
/*  968 */     this.mDetails = paramEVO;
/*  969 */     FormRebuildEVO newEVO = getDetails(paramCK, dependants);
/*  970 */     this.mDetails = savedEVO;
/*  971 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public FormRebuildEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  977 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  981 */     FormRebuildEVO details = this.mDetails.deepClone();
/*      */ 
/*  983 */     if (timer != null) {
/*  984 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  986 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  991 */     return "FormRebuild";
/*      */   }
/*      */ 
/*      */   public FormRebuildRefImpl getRef(FormRebuildPK paramFormRebuildPK)
/*      */   {
/*  996 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  997 */     PreparedStatement stmt = null;
/*  998 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1001 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,FORM_REBUILD.VIS_ID from FORM_REBUILD,MODEL where 1=1 and FORM_REBUILD.FORM_REBUILD_ID = ? and FORM_REBUILD.MODEL_ID = MODEL.MODEL_ID");
/* 1002 */       int col = 1;
/* 1003 */       stmt.setInt(col++, paramFormRebuildPK.getFormRebuildId());
/*      */ 
/* 1005 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1007 */       if (!resultSet.next()) {
/* 1008 */         throw new RuntimeException(getEntityName() + " getRef " + paramFormRebuildPK + " not found");
/*      */       }
/* 1010 */       col = 2;
/* 1011 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1015 */       String textFormRebuild = resultSet.getString(col++);
/* 1016 */       FormRebuildCK ckFormRebuild = new FormRebuildCK(newModelPK, paramFormRebuildPK);
/*      */ 
/* 1021 */       FormRebuildRefImpl localFormRebuildRefImpl = new FormRebuildRefImpl(ckFormRebuild, textFormRebuild);
/*      */       return localFormRebuildRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1026 */       throw handleSQLException(paramFormRebuildPK, "select 0,MODEL.MODEL_ID,FORM_REBUILD.VIS_ID from FORM_REBUILD,MODEL where 1=1 and FORM_REBUILD.FORM_REBUILD_ID = ? and FORM_REBUILD.MODEL_ID = MODEL.MODEL_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1030 */       closeResultSet(resultSet);
/* 1031 */       closeStatement(stmt);
/* 1032 */       closeConnection();
/*      */ 
/* 1034 */       if (timer != null)
/* 1035 */         timer.logDebug("getRef", paramFormRebuildPK); 
/* 1035 */     }
/*      */   }
/*      */ 
/*      */   public void setLastSubmit(int id)
/*      */   {
/* 1047 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1048 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/* 1051 */       stmt = getConnection().prepareStatement(sUpdateLastSubmit);
/* 1052 */       stmt.setInt(1, id);
/*      */ 
/* 1054 */       stmt.executeUpdate();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1058 */       throw handleSQLException(sUpdateLastSubmit, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1062 */       closeStatement(stmt);
/* 1063 */       closeConnection();
/*      */ 
/* 1065 */       if (timer != null)
/* 1066 */         timer.logDebug("setLastUpdate");
/*      */     }
/*      */   }
/*      */
			public AllFormRebuildsELO getAllFormRebuildsForLoggedUser(int userId) {
				Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  481 */     PreparedStatement stmt = null;
/*  482 */     ResultSet resultSet = null;
/*  483 */     AllFormRebuildsELO results = new AllFormRebuildsELO();
/*      */     try
/*      */     {
/*  486 */       stmt = getConnection().prepareStatement(SQL_ALL_FORM_REBUILDS_FOR_USER);
/*  487 */       int col = 1;
				 stmt.setInt(1, userId);
/*  488 */       resultSet = stmt.executeQuery();
/*  489 */       while (resultSet.next())
/*      */       {
/*  491 */         col = 2;
/*      */ 
/*  494 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  497 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  500 */         FormRebuildPK pkFormRebuild = new FormRebuildPK(resultSet.getInt(col++));
/*      */ 
/*  503 */         String textFormRebuild = resultSet.getString(col++);
/*      */ 
/*  508 */         FormRebuildCK ckFormRebuild = new FormRebuildCK(pkModel, pkFormRebuild);
/*      */ 
/*  514 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  520 */         FormRebuildRefImpl erFormRebuild = new FormRebuildRefImpl(ckFormRebuild, textFormRebuild);
/*      */ 
/*  525 */         String col1 = resultSet.getString(col++);
/*  526 */         Timestamp col2 = resultSet.getTimestamp(col++);
/*      */ 
/*  529 */         results.add(erFormRebuild, erModel, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  539 */       throw handleSQLException(SQL_ALL_FORM_REBUILDS_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  543 */       closeResultSet(resultSet);
/*  544 */       closeStatement(stmt);
/*  545 */       closeConnection();
/*      */     }
/*      */ 
/*  548 */     if (timer != null) {
/*  549 */       timer.logDebug("getAllFormRebuildsForLoggedUser", " items=" + results.size());
/*      */     }
/*      */ 
/*  553 */     return results;
			}
}

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.xmlform.rebuild.FormRebuildDAO
 * JD-Core Version:    0.6.0
 */