/*      */ package com.cedar.cp.ejb.impl.model.virement;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.virement.AllVirementRequestGroupsELO;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestGroupCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestGroupPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestGroupRefImpl;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestLineCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestLinePK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementRequestRefImpl;
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
/*      */ public class VirementRequestGroupDAO extends AbstractDAO
/*      */ {
/*   41 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID,VIREMENT_REQUEST_GROUP.REQUEST_ID,VIREMENT_REQUEST_GROUP.NOTES,VIREMENT_REQUEST_GROUP.GROUP_IDX,VIREMENT_REQUEST_GROUP.UPDATED_BY_USER_ID,VIREMENT_REQUEST_GROUP.UPDATED_TIME,VIREMENT_REQUEST_GROUP.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from VIREMENT_REQUEST_GROUP where    REQUEST_GROUP_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into VIREMENT_REQUEST_GROUP ( REQUEST_GROUP_ID,REQUEST_ID,NOTES,GROUP_IDX,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update VIREMENT_REQUEST_GROUP set REQUEST_ID = ?,NOTES = ?,GROUP_IDX = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REQUEST_GROUP_ID = ? ";
/*  343 */   protected static String SQL_ALL_VIREMENT_REQUEST_GROUPS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,VIREMENT_REQUEST.REQUEST_ID      ,VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID      ,VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID      ,VIREMENT_REQUEST_GROUP.REQUEST_ID      ,VIREMENT_REQUEST_GROUP.NOTES from VIREMENT_REQUEST_GROUP    ,MODEL    ,VIREMENT_REQUEST where 1=1   and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID   and VIREMENT_REQUEST.MODEL_ID = MODEL.MODEL_ID   and VIREMENT_REQUEST.FINANCE_CUBE_ID = MODEL.FINANCE_CUBE_ID  order by VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from VIREMENT_REQUEST_GROUP where    REQUEST_GROUP_ID = ? ";
/*  615 */   private static String[][] SQL_DELETE_CHILDREN = { { "VIREMENT_REQUEST_LINE", "delete from VIREMENT_REQUEST_LINE where     VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID = ? " } };
/*      */ 
/*  624 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "VIREMENT_LINE_SPREAD", "delete from VIREMENT_LINE_SPREAD VirementLineSpread where exists (select * from VIREMENT_LINE_SPREAD,VIREMENT_REQUEST_LINE,VIREMENT_REQUEST_GROUP where     VIREMENT_LINE_SPREAD.REQUEST_LINE_ID = VIREMENT_REQUEST_LINE.REQUEST_LINE_ID and VIREMENT_REQUEST_LINE.REQUEST_GROUP_ID = VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID and VirementLineSpread.REQUEST_LINE_ID = VIREMENT_LINE_SPREAD.REQUEST_LINE_ID " } };
/*      */ 
/*  639 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from VIREMENT_REQUEST_GROUP,VIREMENT_REQUEST where 1=1 and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_REQUEST_GROUP.REQUEST_ID ,VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID";
/*      */   protected static final String SQL_GET_ALL = " from VIREMENT_REQUEST_GROUP where    REQUEST_ID = ? ";
/*      */   protected VirementRequestLineDAO mVirementRequestLineDAO;
/*      */   protected VirementRequestGroupEVO mDetails;
/*      */ 
/*      */   public VirementRequestGroupDAO(Connection connection)
/*      */   {
/*   48 */     super(connection);
/*      */   }
/*      */ 
/*      */   public VirementRequestGroupDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public VirementRequestGroupDAO(DataSource ds)
/*      */   {
/*   64 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected VirementRequestGroupPK getPK()
/*      */   {
/*   72 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(VirementRequestGroupEVO details)
/*      */   {
/*   81 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private VirementRequestGroupEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   99 */     int col = 1;
/*  100 */     VirementRequestGroupEVO evo = new VirementRequestGroupEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getInt(col++), null);
/*      */ 
/*  108 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  109 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  110 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  111 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(VirementRequestGroupEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  116 */     int col = startCol_;
/*  117 */     stmt_.setInt(col++, evo_.getRequestGroupId());
/*  118 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(VirementRequestGroupEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  123 */     int col = startCol_;
/*  124 */     stmt_.setInt(col++, evo_.getRequestId());
/*  125 */     stmt_.setString(col++, evo_.getNotes());
/*  126 */     stmt_.setInt(col++, evo_.getGroupIdx());
/*  127 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  128 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  129 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  130 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(VirementRequestGroupPK pk)
/*      */     throws ValidationException
/*      */   {
/*  146 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  148 */     PreparedStatement stmt = null;
/*  149 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  153 */       stmt = getConnection().prepareStatement("select VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID,VIREMENT_REQUEST_GROUP.REQUEST_ID,VIREMENT_REQUEST_GROUP.NOTES,VIREMENT_REQUEST_GROUP.GROUP_IDX,VIREMENT_REQUEST_GROUP.UPDATED_BY_USER_ID,VIREMENT_REQUEST_GROUP.UPDATED_TIME,VIREMENT_REQUEST_GROUP.CREATED_TIME from VIREMENT_REQUEST_GROUP where    REQUEST_GROUP_ID = ? ");
/*      */ 
/*  156 */       int col = 1;
/*  157 */       stmt.setInt(col++, pk.getRequestGroupId());
/*      */ 
/*  159 */       resultSet = stmt.executeQuery();
/*      */ 
/*  161 */       if (!resultSet.next()) {
/*  162 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  165 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  166 */       if (this.mDetails.isModified())
/*  167 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  171 */       throw handleSQLException(pk, "select VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID,VIREMENT_REQUEST_GROUP.REQUEST_ID,VIREMENT_REQUEST_GROUP.NOTES,VIREMENT_REQUEST_GROUP.GROUP_IDX,VIREMENT_REQUEST_GROUP.UPDATED_BY_USER_ID,VIREMENT_REQUEST_GROUP.UPDATED_TIME,VIREMENT_REQUEST_GROUP.CREATED_TIME from VIREMENT_REQUEST_GROUP where    REQUEST_GROUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  175 */       closeResultSet(resultSet);
/*  176 */       closeStatement(stmt);
/*  177 */       closeConnection();
/*      */ 
/*  179 */       if (timer != null)
/*  180 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  211 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  212 */     this.mDetails.postCreateInit();
/*      */ 
/*  214 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  219 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  220 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  221 */       stmt = getConnection().prepareStatement("insert into VIREMENT_REQUEST_GROUP ( REQUEST_GROUP_ID,REQUEST_ID,NOTES,GROUP_IDX,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*      */ 
/*  224 */       int col = 1;
/*  225 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  226 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  229 */       int resultCount = stmt.executeUpdate();
/*  230 */       if (resultCount != 1)
/*      */       {
/*  232 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  235 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  239 */       throw handleSQLException(this.mDetails.getPK(), "insert into VIREMENT_REQUEST_GROUP ( REQUEST_GROUP_ID,REQUEST_ID,NOTES,GROUP_IDX,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  243 */       closeStatement(stmt);
/*  244 */       closeConnection();
/*      */ 
/*  246 */       if (timer != null) {
/*  247 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  253 */       getVirementRequestLineDAO().update(this.mDetails.getLinesMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  259 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  284 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  288 */     PreparedStatement stmt = null;
/*      */ 
/*  290 */     boolean mainChanged = this.mDetails.isModified();
/*  291 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  295 */       if (getVirementRequestLineDAO().update(this.mDetails.getLinesMap())) {
/*  296 */         dependantChanged = true;
/*      */       }
/*  298 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  301 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  302 */         stmt = getConnection().prepareStatement("update VIREMENT_REQUEST_GROUP set REQUEST_ID = ?,NOTES = ?,GROUP_IDX = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REQUEST_GROUP_ID = ? ");
/*      */ 
/*  305 */         int col = 1;
/*  306 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  307 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  310 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  313 */         if (resultCount != 1) {
/*  314 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  317 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  326 */       throw handleSQLException(getPK(), "update VIREMENT_REQUEST_GROUP set REQUEST_ID = ?,NOTES = ?,GROUP_IDX = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    REQUEST_GROUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  330 */       closeStatement(stmt);
/*  331 */       closeConnection();
/*      */ 
/*  333 */       if ((timer != null) && (
/*  334 */         (mainChanged) || (dependantChanged)))
/*  335 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllVirementRequestGroupsELO getAllVirementRequestGroups()
/*      */   {
/*  379 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  380 */     PreparedStatement stmt = null;
/*  381 */     ResultSet resultSet = null;
/*  382 */     AllVirementRequestGroupsELO results = new AllVirementRequestGroupsELO();
/*      */     try
/*      */     {
/*  385 */       stmt = getConnection().prepareStatement(SQL_ALL_VIREMENT_REQUEST_GROUPS);
/*  386 */       int col = 1;
/*  387 */       resultSet = stmt.executeQuery();
/*  388 */       while (resultSet.next())
/*      */       {
/*  390 */         col = 2;
/*      */ 
/*  393 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  396 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  398 */         VirementRequestPK pkVirementRequest = new VirementRequestPK(resultSet.getInt(col++));
/*      */ 
/*  401 */         String textVirementRequest = "";
/*      */ 
/*  404 */         VirementRequestGroupPK pkVirementRequestGroup = new VirementRequestGroupPK(resultSet.getInt(col++));
/*      */ 
/*  407 */         String textVirementRequestGroup = "";
/*      */ 
/*  412 */         VirementRequestCK ckVirementRequest = new VirementRequestCK(pkModel, pkVirementRequest);
/*      */ 
/*  418 */         VirementRequestGroupCK ckVirementRequestGroup = new VirementRequestGroupCK(pkModel, pkVirementRequest, pkVirementRequestGroup);
/*      */ 
/*  425 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  431 */         VirementRequestRefImpl erVirementRequest = new VirementRequestRefImpl(ckVirementRequest, textVirementRequest);
/*      */ 
/*  437 */         VirementRequestGroupRefImpl erVirementRequestGroup = new VirementRequestGroupRefImpl(ckVirementRequestGroup, textVirementRequestGroup);
/*      */ 
/*  442 */         int col1 = resultSet.getInt(col++);
/*  443 */         int col2 = resultSet.getInt(col++);
/*  444 */         String col3 = resultSet.getString(col++);
/*      */ 
/*  447 */         results.add(erVirementRequestGroup, erVirementRequest, erModel, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  459 */       throw handleSQLException(SQL_ALL_VIREMENT_REQUEST_GROUPS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  463 */       closeResultSet(resultSet);
/*  464 */       closeStatement(stmt);
/*  465 */       closeConnection();
/*      */     }
/*      */ 
/*  468 */     if (timer != null) {
/*  469 */       timer.logDebug("getAllVirementRequestGroups", " items=" + results.size());
/*      */     }
/*      */ 
/*  473 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  490 */     if (items == null) {
/*  491 */       return false;
/*      */     }
/*  493 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  494 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  496 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  500 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  501 */       while (iter3.hasNext())
/*      */       {
/*  503 */         this.mDetails = ((VirementRequestGroupEVO)iter3.next());
/*  504 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  506 */         somethingChanged = true;
/*      */ 
/*  509 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  513 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  514 */       while (iter2.hasNext())
/*      */       {
/*  516 */         this.mDetails = ((VirementRequestGroupEVO)iter2.next());
/*      */ 
/*  519 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  521 */         somethingChanged = true;
/*      */ 
/*  524 */         if (deleteStmt == null) {
/*  525 */           deleteStmt = getConnection().prepareStatement("delete from VIREMENT_REQUEST_GROUP where    REQUEST_GROUP_ID = ? ");
/*      */         }
/*      */ 
/*  528 */         int col = 1;
/*  529 */         deleteStmt.setInt(col++, this.mDetails.getRequestGroupId());
/*      */ 
/*  531 */         if (this._log.isDebugEnabled()) {
/*  532 */           this._log.debug("update", "VirementRequestGroup deleting RequestGroupId=" + this.mDetails.getRequestGroupId());
/*      */         }
/*      */ 
/*  537 */         deleteStmt.addBatch();
/*      */ 
/*  540 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  545 */       if (deleteStmt != null)
/*      */       {
/*  547 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  549 */         deleteStmt.executeBatch();
/*      */ 
/*  551 */         if (timer2 != null) {
/*  552 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  556 */       Iterator iter1 = items.values().iterator();
/*  557 */       while (iter1.hasNext())
/*      */       {
/*  559 */         this.mDetails = ((VirementRequestGroupEVO)iter1.next());
/*      */ 
/*  561 */         if (this.mDetails.insertPending())
/*      */         {
/*  563 */           somethingChanged = true;
/*  564 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  567 */         if (this.mDetails.isModified())
/*      */         {
/*  569 */           somethingChanged = true;
/*  570 */           doStore(); continue;
/*      */         }
/*      */ 
/*  574 */         if ((this.mDetails.deletePending()) || 
/*  580 */           (!getVirementRequestLineDAO().update(this.mDetails.getLinesMap()))) continue;
/*  581 */         somethingChanged = true;
/*      */       }
/*      */ 
/*  593 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  597 */       throw handleSQLException("delete from VIREMENT_REQUEST_GROUP where    REQUEST_GROUP_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  601 */       if (deleteStmt != null)
/*      */       {
/*  603 */         closeStatement(deleteStmt);
/*  604 */         closeConnection();
/*      */       }
/*      */ 
/*  607 */       this.mDetails = null;
/*      */ 
/*  609 */       if ((somethingChanged) && 
/*  610 */         (timer != null))
/*  611 */         timer.logDebug("update", "collection"); 
/*  611 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(VirementRequestGroupPK pk)
/*      */   {
/*  648 */     Set emptyStrings = Collections.emptySet();
/*  649 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(VirementRequestGroupPK pk, Set<String> exclusionTables)
/*      */   {
/*  655 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  657 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  659 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  661 */       PreparedStatement stmt = null;
/*      */ 
/*  663 */       int resultCount = 0;
/*  664 */       String s = null;
/*      */       try
/*      */       {
/*  667 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  669 */         if (this._log.isDebugEnabled()) {
/*  670 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  672 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  675 */         int col = 1;
/*  676 */         stmt.setInt(col++, pk.getRequestGroupId());
/*      */ 
/*  679 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  683 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  687 */         closeStatement(stmt);
/*  688 */         closeConnection();
/*      */ 
/*  690 */         if (timer != null) {
/*  691 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  695 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  697 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  699 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  701 */       PreparedStatement stmt = null;
/*      */ 
/*  703 */       int resultCount = 0;
/*  704 */       String s = null;
/*      */       try
/*      */       {
/*  707 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  709 */         if (this._log.isDebugEnabled()) {
/*  710 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  712 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  715 */         int col = 1;
/*  716 */         stmt.setInt(col++, pk.getRequestGroupId());
/*      */ 
/*  719 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  723 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  727 */         closeStatement(stmt);
/*  728 */         closeConnection();
/*      */ 
/*  730 */         if (timer != null)
/*  731 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*      */   {
/*  755 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  757 */     PreparedStatement stmt = null;
/*  758 */     ResultSet resultSet = null;
/*      */ 
/*  760 */     int itemCount = 0;
/*      */ 
/*  762 */     VirementRequestEVO owningEVO = null;
/*  763 */     Iterator ownersIter = owners.iterator();
/*  764 */     while (ownersIter.hasNext())
/*      */     {
/*  766 */       owningEVO = (VirementRequestEVO)ownersIter.next();
/*  767 */       owningEVO.setGroupsAllItemsLoaded(true);
/*      */     }
/*  769 */     ownersIter = owners.iterator();
/*  770 */     owningEVO = (VirementRequestEVO)ownersIter.next();
/*  771 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  775 */       stmt = getConnection().prepareStatement("select VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID,VIREMENT_REQUEST_GROUP.REQUEST_ID,VIREMENT_REQUEST_GROUP.NOTES,VIREMENT_REQUEST_GROUP.GROUP_IDX,VIREMENT_REQUEST_GROUP.UPDATED_BY_USER_ID,VIREMENT_REQUEST_GROUP.UPDATED_TIME,VIREMENT_REQUEST_GROUP.CREATED_TIME from VIREMENT_REQUEST_GROUP,VIREMENT_REQUEST where 1=1 and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_REQUEST_GROUP.REQUEST_ID ,VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID");
/*      */ 
/*  777 */       int col = 1;
/*  778 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  780 */       resultSet = stmt.executeQuery();
/*      */ 
/*  783 */       while (resultSet.next())
/*      */       {
/*  785 */         itemCount++;
/*  786 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  791 */         while (this.mDetails.getRequestId() != owningEVO.getRequestId())
/*      */         {
/*  795 */           if (!ownersIter.hasNext())
/*      */           {
/*  797 */             this._log.debug("bulkGetAll", "can't find owning [RequestId=" + this.mDetails.getRequestId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  801 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  802 */             ownersIter = owners.iterator();
/*  803 */             while (ownersIter.hasNext())
/*      */             {
/*  805 */               owningEVO = (VirementRequestEVO)ownersIter.next();
/*  806 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  808 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  810 */           owningEVO = (VirementRequestEVO)ownersIter.next();
/*      */         }
/*  812 */         if (owningEVO.getGroups() == null)
/*      */         {
/*  814 */           theseItems = new ArrayList();
/*  815 */           owningEVO.setGroups(theseItems);
/*  816 */           owningEVO.setGroupsAllItemsLoaded(true);
/*      */         }
/*  818 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  821 */       if (timer != null) {
/*  822 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  825 */       if ((itemCount > 0) && (dependants.indexOf("<31>") > -1))
/*      */       {
/*  827 */         getVirementRequestLineDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  831 */       throw handleSQLException("select VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID,VIREMENT_REQUEST_GROUP.REQUEST_ID,VIREMENT_REQUEST_GROUP.NOTES,VIREMENT_REQUEST_GROUP.GROUP_IDX,VIREMENT_REQUEST_GROUP.UPDATED_BY_USER_ID,VIREMENT_REQUEST_GROUP.UPDATED_TIME,VIREMENT_REQUEST_GROUP.CREATED_TIME from VIREMENT_REQUEST_GROUP,VIREMENT_REQUEST where 1=1 and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_REQUEST_GROUP.REQUEST_ID ,VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  835 */       closeResultSet(resultSet);
/*  836 */       closeStatement(stmt);
/*  837 */       closeConnection();
/*      */ 
/*  839 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectRequestId, String dependants, Collection currentList)
/*      */   {
/*  864 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  865 */     PreparedStatement stmt = null;
/*  866 */     ResultSet resultSet = null;
/*      */ 
/*  868 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  872 */       stmt = getConnection().prepareStatement("select VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID,VIREMENT_REQUEST_GROUP.REQUEST_ID,VIREMENT_REQUEST_GROUP.NOTES,VIREMENT_REQUEST_GROUP.GROUP_IDX,VIREMENT_REQUEST_GROUP.UPDATED_BY_USER_ID,VIREMENT_REQUEST_GROUP.UPDATED_TIME,VIREMENT_REQUEST_GROUP.CREATED_TIME from VIREMENT_REQUEST_GROUP where    REQUEST_ID = ? ");
/*      */ 
/*  874 */       int col = 1;
/*  875 */       stmt.setInt(col++, selectRequestId);
/*      */ 
/*  877 */       resultSet = stmt.executeQuery();
/*      */ 
/*  879 */       while (resultSet.next())
/*      */       {
/*  881 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  884 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  887 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  890 */       if (currentList != null)
/*      */       {
/*  893 */         ListIterator iter = items.listIterator();
/*  894 */         VirementRequestGroupEVO currentEVO = null;
/*  895 */         VirementRequestGroupEVO newEVO = null;
/*  896 */         while (iter.hasNext())
/*      */         {
/*  898 */           newEVO = (VirementRequestGroupEVO)iter.next();
/*  899 */           Iterator iter2 = currentList.iterator();
/*  900 */           while (iter2.hasNext())
/*      */           {
/*  902 */             currentEVO = (VirementRequestGroupEVO)iter2.next();
/*  903 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  905 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  911 */         Iterator iter2 = currentList.iterator();
/*  912 */         while (iter2.hasNext())
/*      */         {
/*  914 */           currentEVO = (VirementRequestGroupEVO)iter2.next();
/*  915 */           if (currentEVO.insertPending()) {
/*  916 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  920 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  924 */       throw handleSQLException("select VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID,VIREMENT_REQUEST_GROUP.REQUEST_ID,VIREMENT_REQUEST_GROUP.NOTES,VIREMENT_REQUEST_GROUP.GROUP_IDX,VIREMENT_REQUEST_GROUP.UPDATED_BY_USER_ID,VIREMENT_REQUEST_GROUP.UPDATED_TIME,VIREMENT_REQUEST_GROUP.CREATED_TIME from VIREMENT_REQUEST_GROUP where    REQUEST_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  928 */       closeResultSet(resultSet);
/*  929 */       closeStatement(stmt);
/*  930 */       closeConnection();
/*      */ 
/*  932 */       if (timer != null) {
/*  933 */         timer.logDebug("getAll", " RequestId=" + selectRequestId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  938 */     return items;
/*      */   }
/*      */ 
/*      */   public VirementRequestGroupEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  957 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  960 */     if (this.mDetails == null) {
/*  961 */       doLoad(((VirementRequestGroupCK)paramCK).getVirementRequestGroupPK());
/*      */     }
/*  963 */     else if (!this.mDetails.getPK().equals(((VirementRequestGroupCK)paramCK).getVirementRequestGroupPK())) {
/*  964 */       doLoad(((VirementRequestGroupCK)paramCK).getVirementRequestGroupPK());
/*      */     }
/*      */ 
/*  967 */     if ((dependants.indexOf("<31>") > -1) && (!this.mDetails.isLinesAllItemsLoaded()))
/*      */     {
/*  972 */       this.mDetails.setLines(getVirementRequestLineDAO().getAll(this.mDetails.getRequestGroupId(), dependants, this.mDetails.getLines()));
/*      */ 
/*  979 */       this.mDetails.setLinesAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  982 */     if ((paramCK instanceof VirementRequestLineCK))
/*      */     {
/*  984 */       if (this.mDetails.getLines() == null) {
/*  985 */         this.mDetails.loadLinesItem(getVirementRequestLineDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  988 */         VirementRequestLinePK pk = ((VirementRequestLineCK)paramCK).getVirementRequestLinePK();
/*  989 */         VirementRequestLineEVO evo = this.mDetails.getLinesItem(pk);
/*  990 */         if (evo == null)
/*  991 */           this.mDetails.loadLinesItem(getVirementRequestLineDAO().getDetails(paramCK, dependants));
/*      */         else {
/*  993 */           getVirementRequestLineDAO().getDetails(paramCK, evo, dependants);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  998 */     VirementRequestGroupEVO details = new VirementRequestGroupEVO();
/*  999 */     details = this.mDetails.deepClone();
/*      */ 
/* 1001 */     if (timer != null) {
/* 1002 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1004 */     return details;
/*      */   }
/*      */ 
/*      */   public VirementRequestGroupEVO getDetails(ModelCK paramCK, VirementRequestGroupEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1010 */     VirementRequestGroupEVO savedEVO = this.mDetails;
/* 1011 */     this.mDetails = paramEVO;
/* 1012 */     VirementRequestGroupEVO newEVO = getDetails(paramCK, dependants);
/* 1013 */     this.mDetails = savedEVO;
/* 1014 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public VirementRequestGroupEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1020 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1024 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1027 */     VirementRequestGroupEVO details = this.mDetails.deepClone();
/*      */ 
/* 1029 */     if (timer != null) {
/* 1030 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1032 */     return details;
/*      */   }
/*      */ 
/*      */   protected VirementRequestLineDAO getVirementRequestLineDAO()
/*      */   {
/* 1041 */     if (this.mVirementRequestLineDAO == null)
/*      */     {
/* 1043 */       if (this.mDataSource != null)
/* 1044 */         this.mVirementRequestLineDAO = new VirementRequestLineDAO(this.mDataSource);
/*      */       else {
/* 1046 */         this.mVirementRequestLineDAO = new VirementRequestLineDAO(getConnection());
/*      */       }
/*      */     }
/* 1049 */     return this.mVirementRequestLineDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1054 */     return "VirementRequestGroup";
/*      */   }
/*      */ 
/*      */   public VirementRequestGroupRefImpl getRef(VirementRequestGroupPK paramVirementRequestGroupPK)
/*      */   {
/* 1059 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1060 */     PreparedStatement stmt = null;
/* 1061 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1064 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,VIREMENT_REQUEST.REQUEST_ID from VIREMENT_REQUEST_GROUP,MODEL,VIREMENT_REQUEST where 1=1 and VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID = ? and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.REQUEST_ID = MODEL.REQUEST_ID");
/* 1065 */       int col = 1;
/* 1066 */       stmt.setInt(col++, paramVirementRequestGroupPK.getRequestGroupId());
/*      */ 
/* 1068 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1070 */       if (!resultSet.next()) {
/* 1071 */         throw new RuntimeException(getEntityName() + " getRef " + paramVirementRequestGroupPK + " not found");
/*      */       }
/* 1073 */       col = 2;
/* 1074 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1078 */       VirementRequestPK newVirementRequestPK = new VirementRequestPK(resultSet.getInt(col++));
/*      */ 
/* 1082 */       String textVirementRequestGroup = "";
/* 1083 */       VirementRequestGroupCK ckVirementRequestGroup = new VirementRequestGroupCK(newModelPK, newVirementRequestPK, paramVirementRequestGroupPK);
/*      */ 
/* 1089 */       VirementRequestGroupRefImpl localVirementRequestGroupRefImpl = new VirementRequestGroupRefImpl(ckVirementRequestGroup, textVirementRequestGroup);
/*      */       return localVirementRequestGroupRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1094 */       throw handleSQLException(paramVirementRequestGroupPK, "select 0,MODEL.MODEL_ID,VIREMENT_REQUEST.REQUEST_ID from VIREMENT_REQUEST_GROUP,MODEL,VIREMENT_REQUEST where 1=1 and VIREMENT_REQUEST_GROUP.REQUEST_GROUP_ID = ? and VIREMENT_REQUEST_GROUP.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.REQUEST_ID = MODEL.REQUEST_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1098 */       closeResultSet(resultSet);
/* 1099 */       closeStatement(stmt);
/* 1100 */       closeConnection();
/*      */ 
/* 1102 */       if (timer != null)
/* 1103 */         timer.logDebug("getRef", paramVirementRequestGroupPK); 
/* 1103 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1115 */     if (c == null)
/* 1116 */       return;
/* 1117 */     Iterator iter = c.iterator();
/* 1118 */     while (iter.hasNext())
/*      */     {
/* 1120 */       VirementRequestGroupEVO evo = (VirementRequestGroupEVO)iter.next();
/* 1121 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(VirementRequestGroupEVO evo, String dependants)
/*      */   {
/* 1135 */     if (evo.insertPending()) {
/* 1136 */       return;
/*      */     }
/* 1138 */     if (evo.getRequestGroupId() < 1) {
/* 1139 */       return;
/*      */     }
/*      */ 
/* 1143 */     if ((dependants.indexOf("<31>") > -1) || (dependants.indexOf("<32>") > -1))
/*      */     {
/* 1147 */       if (!evo.isLinesAllItemsLoaded())
/*      */       {
/* 1149 */         evo.setLines(getVirementRequestLineDAO().getAll(evo.getRequestGroupId(), dependants, evo.getLines()));
/*      */ 
/* 1156 */         evo.setLinesAllItemsLoaded(true);
/*      */       }
/* 1158 */       getVirementRequestLineDAO().getDependants(evo.getLines(), dependants);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.virement.VirementRequestGroupDAO
 * JD-Core Version:    0.6.0
 */