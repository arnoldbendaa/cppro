/*      */ package com.cedar.cp.ejb.impl.model.virement;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.model.ModelCK;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementAuthPointCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementAuthPointLinkCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementAuthPointLinkPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementAuthPointPK;
/*      */ import com.cedar.cp.dto.model.virement.VirementAuthPointRefImpl;
/*      */ import com.cedar.cp.dto.model.virement.VirementAuthorisersCK;
/*      */ import com.cedar.cp.dto.model.virement.VirementAuthorisersPK;
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
/*      */ public class VirementAuthPointDAO extends AbstractDAO
/*      */ {
/*   44 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select VIREMENT_AUTH_POINT.AUTH_POINT_ID,VIREMENT_AUTH_POINT.REQUEST_ID,VIREMENT_AUTH_POINT.POINT_STATUS,VIREMENT_AUTH_POINT.AUTH_USER_ID,VIREMENT_AUTH_POINT.NOTES,VIREMENT_AUTH_POINT.STRUCTURE_ELEMENT_ID,VIREMENT_AUTH_POINT.UPDATED_BY_USER_ID,VIREMENT_AUTH_POINT.UPDATED_TIME,VIREMENT_AUTH_POINT.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from VIREMENT_AUTH_POINT where    AUTH_POINT_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into VIREMENT_AUTH_POINT ( AUTH_POINT_ID,REQUEST_ID,POINT_STATUS,AUTH_USER_ID,NOTES,STRUCTURE_ELEMENT_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update VIREMENT_AUTH_POINT set REQUEST_ID = ?,POINT_STATUS = ?,AUTH_USER_ID = ?,NOTES = ?,STRUCTURE_ELEMENT_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AUTH_POINT_ID = ? ";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from VIREMENT_AUTH_POINT where    AUTH_POINT_ID = ? ";
/*  505 */   private static String[][] SQL_DELETE_CHILDREN = { { "VIREMENT_AUTHORISERS", "delete from VIREMENT_AUTHORISERS where     VIREMENT_AUTHORISERS.AUTH_POINT_ID = ? " }, { "VIREMENT_AUTH_POINT_LINK", "delete from VIREMENT_AUTH_POINT_LINK where     VIREMENT_AUTH_POINT_LINK.AUTH_POINT_ID = ? " } };
/*      */ 
/*  519 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/*  523 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and VIREMENT_AUTH_POINT.AUTH_POINT_ID = ?)";
/*      */   public static final String SQL_BULK_GET_ALL = " from VIREMENT_AUTH_POINT,VIREMENT_REQUEST where 1=1 and VIREMENT_AUTH_POINT.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_AUTH_POINT.REQUEST_ID ,VIREMENT_AUTH_POINT.AUTH_POINT_ID";
/*      */   protected static final String SQL_GET_ALL = " from VIREMENT_AUTH_POINT where    REQUEST_ID = ? ";
/*      */   protected VirementAuthorisersDAO mVirementAuthorisersDAO;
/*      */   protected VirementAuthPointLinkDAO mVirementAuthPointLinkDAO;
/*      */   protected VirementAuthPointEVO mDetails;
/*      */ 
/*      */   public VirementAuthPointDAO(Connection connection)
/*      */   {
/*   51 */     super(connection);
/*      */   }
/*      */ 
/*      */   public VirementAuthPointDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public VirementAuthPointDAO(DataSource ds)
/*      */   {
/*   67 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected VirementAuthPointPK getPK()
/*      */   {
/*   75 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(VirementAuthPointEVO details)
/*      */   {
/*   84 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private VirementAuthPointEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  104 */     int col = 1;
/*  105 */     VirementAuthPointEVO evo = new VirementAuthPointEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++), resultSet_.getString(col++), resultSet_.getInt(col++), null, null);
/*      */ 
/*  116 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  117 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  118 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  119 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(VirementAuthPointEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  124 */     int col = startCol_;
/*  125 */     stmt_.setInt(col++, evo_.getAuthPointId());
/*  126 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(VirementAuthPointEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  131 */     int col = startCol_;
/*  132 */     stmt_.setInt(col++, evo_.getRequestId());
/*  133 */     stmt_.setInt(col++, evo_.getPointStatus());
/*  134 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getAuthUserId());
/*  135 */     stmt_.setString(col++, evo_.getNotes());
/*  136 */     stmt_.setInt(col++, evo_.getStructureElementId());
/*  137 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  138 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  139 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  140 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(VirementAuthPointPK pk)
/*      */     throws ValidationException
/*      */   {
/*  156 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  158 */     PreparedStatement stmt = null;
/*  159 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  163 */       stmt = getConnection().prepareStatement("select VIREMENT_AUTH_POINT.AUTH_POINT_ID,VIREMENT_AUTH_POINT.REQUEST_ID,VIREMENT_AUTH_POINT.POINT_STATUS,VIREMENT_AUTH_POINT.AUTH_USER_ID,VIREMENT_AUTH_POINT.NOTES,VIREMENT_AUTH_POINT.STRUCTURE_ELEMENT_ID,VIREMENT_AUTH_POINT.UPDATED_BY_USER_ID,VIREMENT_AUTH_POINT.UPDATED_TIME,VIREMENT_AUTH_POINT.CREATED_TIME from VIREMENT_AUTH_POINT where    AUTH_POINT_ID = ? ");
/*      */ 
/*  166 */       int col = 1;
/*  167 */       stmt.setInt(col++, pk.getAuthPointId());
/*      */ 
/*  169 */       resultSet = stmt.executeQuery();
/*      */ 
/*  171 */       if (!resultSet.next()) {
/*  172 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  175 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  176 */       if (this.mDetails.isModified())
/*  177 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  181 */       throw handleSQLException(pk, "select VIREMENT_AUTH_POINT.AUTH_POINT_ID,VIREMENT_AUTH_POINT.REQUEST_ID,VIREMENT_AUTH_POINT.POINT_STATUS,VIREMENT_AUTH_POINT.AUTH_USER_ID,VIREMENT_AUTH_POINT.NOTES,VIREMENT_AUTH_POINT.STRUCTURE_ELEMENT_ID,VIREMENT_AUTH_POINT.UPDATED_BY_USER_ID,VIREMENT_AUTH_POINT.UPDATED_TIME,VIREMENT_AUTH_POINT.CREATED_TIME from VIREMENT_AUTH_POINT where    AUTH_POINT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  185 */       closeResultSet(resultSet);
/*  186 */       closeStatement(stmt);
/*  187 */       closeConnection();
/*      */ 
/*  189 */       if (timer != null)
/*  190 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  225 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  226 */     this.mDetails.postCreateInit();
/*      */ 
/*  228 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  233 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  234 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  235 */       stmt = getConnection().prepareStatement("insert into VIREMENT_AUTH_POINT ( AUTH_POINT_ID,REQUEST_ID,POINT_STATUS,AUTH_USER_ID,NOTES,STRUCTURE_ELEMENT_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  238 */       int col = 1;
/*  239 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  240 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  243 */       int resultCount = stmt.executeUpdate();
/*  244 */       if (resultCount != 1)
/*      */       {
/*  246 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  249 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  253 */       throw handleSQLException(this.mDetails.getPK(), "insert into VIREMENT_AUTH_POINT ( AUTH_POINT_ID,REQUEST_ID,POINT_STATUS,AUTH_USER_ID,NOTES,STRUCTURE_ELEMENT_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  257 */       closeStatement(stmt);
/*  258 */       closeConnection();
/*      */ 
/*  260 */       if (timer != null) {
/*  261 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  267 */       getVirementAuthorisersDAO().update(this.mDetails.getAuthUsersMap());
/*      */ 
/*  269 */       getVirementAuthPointLinkDAO().update(this.mDetails.getAuthPointLinksMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  275 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  302 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  306 */     PreparedStatement stmt = null;
/*      */ 
/*  308 */     boolean mainChanged = this.mDetails.isModified();
/*  309 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  313 */       if (getVirementAuthorisersDAO().update(this.mDetails.getAuthUsersMap())) {
/*  314 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  317 */       if (getVirementAuthPointLinkDAO().update(this.mDetails.getAuthPointLinksMap())) {
/*  318 */         dependantChanged = true;
/*      */       }
/*  320 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  323 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  324 */         stmt = getConnection().prepareStatement("update VIREMENT_AUTH_POINT set REQUEST_ID = ?,POINT_STATUS = ?,AUTH_USER_ID = ?,NOTES = ?,STRUCTURE_ELEMENT_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AUTH_POINT_ID = ? ");
/*      */ 
/*  327 */         int col = 1;
/*  328 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  329 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  332 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  335 */         if (resultCount != 1) {
/*  336 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  339 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  348 */       throw handleSQLException(getPK(), "update VIREMENT_AUTH_POINT set REQUEST_ID = ?,POINT_STATUS = ?,AUTH_USER_ID = ?,NOTES = ?,STRUCTURE_ELEMENT_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    AUTH_POINT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  352 */       closeStatement(stmt);
/*  353 */       closeConnection();
/*      */ 
/*  355 */       if ((timer != null) && (
/*  356 */         (mainChanged) || (dependantChanged)))
/*  357 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  376 */     if (items == null) {
/*  377 */       return false;
/*      */     }
/*  379 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  380 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  382 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  386 */       Iterator iter3 = new ArrayList(items.values()).iterator();
/*  387 */       while (iter3.hasNext())
/*      */       {
/*  389 */         this.mDetails = ((VirementAuthPointEVO)iter3.next());
/*  390 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  392 */         somethingChanged = true;
/*      */ 
/*  395 */         deleteDependants(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  399 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  400 */       while (iter2.hasNext())
/*      */       {
/*  402 */         this.mDetails = ((VirementAuthPointEVO)iter2.next());
/*      */ 
/*  405 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  407 */         somethingChanged = true;
/*      */ 
/*  410 */         if (deleteStmt == null) {
/*  411 */           deleteStmt = getConnection().prepareStatement("delete from VIREMENT_AUTH_POINT where    AUTH_POINT_ID = ? ");
/*      */         }
/*      */ 
/*  414 */         int col = 1;
/*  415 */         deleteStmt.setInt(col++, this.mDetails.getAuthPointId());
/*      */ 
/*  417 */         if (this._log.isDebugEnabled()) {
/*  418 */           this._log.debug("update", "VirementAuthPoint deleting AuthPointId=" + this.mDetails.getAuthPointId());
/*      */         }
/*      */ 
/*  423 */         deleteStmt.addBatch();
/*      */ 
/*  426 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  431 */       if (deleteStmt != null)
/*      */       {
/*  433 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  435 */         deleteStmt.executeBatch();
/*      */ 
/*  437 */         if (timer2 != null) {
/*  438 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  442 */       Iterator iter1 = items.values().iterator();
/*  443 */       while (iter1.hasNext())
/*      */       {
/*  445 */         this.mDetails = ((VirementAuthPointEVO)iter1.next());
/*      */ 
/*  447 */         if (this.mDetails.insertPending())
/*      */         {
/*  449 */           somethingChanged = true;
/*  450 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  453 */         if (this.mDetails.isModified())
/*      */         {
/*  455 */           somethingChanged = true;
/*  456 */           doStore(); continue;
/*      */         }
/*      */ 
/*  460 */         if (this.mDetails.deletePending())
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  466 */         if (getVirementAuthorisersDAO().update(this.mDetails.getAuthUsersMap())) {
/*  467 */           somethingChanged = true;
/*      */         }
/*      */ 
/*  470 */         if (getVirementAuthPointLinkDAO().update(this.mDetails.getAuthPointLinksMap())) {
/*  471 */           somethingChanged = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  483 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  487 */       throw handleSQLException("delete from VIREMENT_AUTH_POINT where    AUTH_POINT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  491 */       if (deleteStmt != null)
/*      */       {
/*  493 */         closeStatement(deleteStmt);
/*  494 */         closeConnection();
/*      */       }
/*      */ 
/*  497 */       this.mDetails = null;
/*      */ 
/*  499 */       if ((somethingChanged) && 
/*  500 */         (timer != null))
/*  501 */         timer.logDebug("update", "collection"); 
/*  501 */     }
/*      */   }
/*      */ 
/*      */   private void deleteDependants(VirementAuthPointPK pk)
/*      */   {
/*  532 */     Set emptyStrings = Collections.emptySet();
/*  533 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(VirementAuthPointPK pk, Set<String> exclusionTables)
/*      */   {
/*  539 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/*  541 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/*  543 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  545 */       PreparedStatement stmt = null;
/*      */ 
/*  547 */       int resultCount = 0;
/*  548 */       String s = null;
/*      */       try
/*      */       {
/*  551 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/*  553 */         if (this._log.isDebugEnabled()) {
/*  554 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  556 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  559 */         int col = 1;
/*  560 */         stmt.setInt(col++, pk.getAuthPointId());
/*      */ 
/*  563 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  567 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  571 */         closeStatement(stmt);
/*  572 */         closeConnection();
/*      */ 
/*  574 */         if (timer != null) {
/*  575 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/*  579 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/*  581 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/*  583 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  585 */       PreparedStatement stmt = null;
/*      */ 
/*  587 */       int resultCount = 0;
/*  588 */       String s = null;
/*      */       try
/*      */       {
/*  591 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/*  593 */         if (this._log.isDebugEnabled()) {
/*  594 */           this._log.debug("deleteDependants", s);
/*      */         }
/*  596 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/*  599 */         int col = 1;
/*  600 */         stmt.setInt(col++, pk.getAuthPointId());
/*      */ 
/*  603 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  607 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/*  611 */         closeStatement(stmt);
/*  612 */         closeConnection();
/*      */ 
/*  614 */         if (timer != null)
/*  615 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*      */   {
/*  639 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  641 */     PreparedStatement stmt = null;
/*  642 */     ResultSet resultSet = null;
/*      */ 
/*  644 */     int itemCount = 0;
/*      */ 
/*  646 */     VirementRequestEVO owningEVO = null;
/*  647 */     Iterator ownersIter = owners.iterator();
/*  648 */     while (ownersIter.hasNext())
/*      */     {
/*  650 */       owningEVO = (VirementRequestEVO)ownersIter.next();
/*  651 */       owningEVO.setAuthPointsAllItemsLoaded(true);
/*      */     }
/*  653 */     ownersIter = owners.iterator();
/*  654 */     owningEVO = (VirementRequestEVO)ownersIter.next();
/*  655 */     Collection theseItems = null;
/*      */     try
/*      */     {
/*  659 */       stmt = getConnection().prepareStatement("select VIREMENT_AUTH_POINT.AUTH_POINT_ID,VIREMENT_AUTH_POINT.REQUEST_ID,VIREMENT_AUTH_POINT.POINT_STATUS,VIREMENT_AUTH_POINT.AUTH_USER_ID,VIREMENT_AUTH_POINT.NOTES,VIREMENT_AUTH_POINT.STRUCTURE_ELEMENT_ID,VIREMENT_AUTH_POINT.UPDATED_BY_USER_ID,VIREMENT_AUTH_POINT.UPDATED_TIME,VIREMENT_AUTH_POINT.CREATED_TIME from VIREMENT_AUTH_POINT,VIREMENT_REQUEST where 1=1 and VIREMENT_AUTH_POINT.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_AUTH_POINT.REQUEST_ID ,VIREMENT_AUTH_POINT.AUTH_POINT_ID");
/*      */ 
/*  661 */       int col = 1;
/*  662 */       stmt.setInt(col++, entityPK.getModelId());
/*      */ 
/*  664 */       resultSet = stmt.executeQuery();
/*      */ 
/*  667 */       while (resultSet.next())
/*      */       {
/*  669 */         itemCount++;
/*  670 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  675 */         while (this.mDetails.getRequestId() != owningEVO.getRequestId())
/*      */         {
/*  679 */           if (!ownersIter.hasNext())
/*      */           {
/*  681 */             this._log.debug("bulkGetAll", "can't find owning [RequestId=" + this.mDetails.getRequestId() + "] for " + this.mDetails.getPK());
/*      */ 
/*  685 */             this._log.debug("bulkGetAll", "loaded owners are:");
/*  686 */             ownersIter = owners.iterator();
/*  687 */             while (ownersIter.hasNext())
/*      */             {
/*  689 */               owningEVO = (VirementRequestEVO)ownersIter.next();
/*  690 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*      */             }
/*  692 */             throw new IllegalStateException("can't find owner");
/*      */           }
/*  694 */           owningEVO = (VirementRequestEVO)ownersIter.next();
/*      */         }
/*  696 */         if (owningEVO.getAuthPoints() == null)
/*      */         {
/*  698 */           theseItems = new ArrayList();
/*  699 */           owningEVO.setAuthPoints(theseItems);
/*  700 */           owningEVO.setAuthPointsAllItemsLoaded(true);
/*      */         }
/*  702 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  705 */       if (timer != null) {
/*  706 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */ 
/*  709 */       if ((itemCount > 0) && (dependants.indexOf("<34>") > -1))
/*      */       {
/*  711 */         getVirementAuthorisersDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*  713 */       if ((itemCount > 0) && (dependants.indexOf("<35>") > -1))
/*      */       {
/*  715 */         getVirementAuthPointLinkDAO().bulkGetAll(entityPK, theseItems, dependants);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle) {
/*  719 */       throw handleSQLException("select VIREMENT_AUTH_POINT.AUTH_POINT_ID,VIREMENT_AUTH_POINT.REQUEST_ID,VIREMENT_AUTH_POINT.POINT_STATUS,VIREMENT_AUTH_POINT.AUTH_USER_ID,VIREMENT_AUTH_POINT.NOTES,VIREMENT_AUTH_POINT.STRUCTURE_ELEMENT_ID,VIREMENT_AUTH_POINT.UPDATED_BY_USER_ID,VIREMENT_AUTH_POINT.UPDATED_TIME,VIREMENT_AUTH_POINT.CREATED_TIME from VIREMENT_AUTH_POINT,VIREMENT_REQUEST where 1=1 and VIREMENT_AUTH_POINT.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.MODEL_ID = ? order by  VIREMENT_AUTH_POINT.REQUEST_ID ,VIREMENT_AUTH_POINT.AUTH_POINT_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  723 */       closeResultSet(resultSet);
/*  724 */       closeStatement(stmt);
/*  725 */       closeConnection();
/*      */ 
/*  727 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(int selectRequestId, String dependants, Collection currentList)
/*      */   {
/*  752 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  753 */     PreparedStatement stmt = null;
/*  754 */     ResultSet resultSet = null;
/*      */ 
/*  756 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  760 */       stmt = getConnection().prepareStatement("select VIREMENT_AUTH_POINT.AUTH_POINT_ID,VIREMENT_AUTH_POINT.REQUEST_ID,VIREMENT_AUTH_POINT.POINT_STATUS,VIREMENT_AUTH_POINT.AUTH_USER_ID,VIREMENT_AUTH_POINT.NOTES,VIREMENT_AUTH_POINT.STRUCTURE_ELEMENT_ID,VIREMENT_AUTH_POINT.UPDATED_BY_USER_ID,VIREMENT_AUTH_POINT.UPDATED_TIME,VIREMENT_AUTH_POINT.CREATED_TIME from VIREMENT_AUTH_POINT where    REQUEST_ID = ? ");
/*      */ 
/*  762 */       int col = 1;
/*  763 */       stmt.setInt(col++, selectRequestId);
/*      */ 
/*  765 */       resultSet = stmt.executeQuery();
/*      */ 
/*  767 */       while (resultSet.next())
/*      */       {
/*  769 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  772 */         getDependants(this.mDetails, dependants);
/*      */ 
/*  775 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  778 */       if (currentList != null)
/*      */       {
/*  781 */         ListIterator iter = items.listIterator();
/*  782 */         VirementAuthPointEVO currentEVO = null;
/*  783 */         VirementAuthPointEVO newEVO = null;
/*  784 */         while (iter.hasNext())
/*      */         {
/*  786 */           newEVO = (VirementAuthPointEVO)iter.next();
/*  787 */           Iterator iter2 = currentList.iterator();
/*  788 */           while (iter2.hasNext())
/*      */           {
/*  790 */             currentEVO = (VirementAuthPointEVO)iter2.next();
/*  791 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  793 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  799 */         Iterator iter2 = currentList.iterator();
/*  800 */         while (iter2.hasNext())
/*      */         {
/*  802 */           currentEVO = (VirementAuthPointEVO)iter2.next();
/*  803 */           if (currentEVO.insertPending()) {
/*  804 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  808 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  812 */       throw handleSQLException("select VIREMENT_AUTH_POINT.AUTH_POINT_ID,VIREMENT_AUTH_POINT.REQUEST_ID,VIREMENT_AUTH_POINT.POINT_STATUS,VIREMENT_AUTH_POINT.AUTH_USER_ID,VIREMENT_AUTH_POINT.NOTES,VIREMENT_AUTH_POINT.STRUCTURE_ELEMENT_ID,VIREMENT_AUTH_POINT.UPDATED_BY_USER_ID,VIREMENT_AUTH_POINT.UPDATED_TIME,VIREMENT_AUTH_POINT.CREATED_TIME from VIREMENT_AUTH_POINT where    REQUEST_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  816 */       closeResultSet(resultSet);
/*  817 */       closeStatement(stmt);
/*  818 */       closeConnection();
/*      */ 
/*  820 */       if (timer != null) {
/*  821 */         timer.logDebug("getAll", " RequestId=" + selectRequestId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  826 */     return items;
/*      */   }
/*      */ 
/*      */   public VirementAuthPointEVO getDetails(ModelCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  845 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  848 */     if (this.mDetails == null) {
/*  849 */       doLoad(((VirementAuthPointCK)paramCK).getVirementAuthPointPK());
/*      */     }
/*  851 */     else if (!this.mDetails.getPK().equals(((VirementAuthPointCK)paramCK).getVirementAuthPointPK())) {
/*  852 */       doLoad(((VirementAuthPointCK)paramCK).getVirementAuthPointPK());
/*      */     }
/*      */ 
/*  855 */     if ((dependants.indexOf("<34>") > -1) && (!this.mDetails.isAuthUsersAllItemsLoaded()))
/*      */     {
/*  860 */       this.mDetails.setAuthUsers(getVirementAuthorisersDAO().getAll(this.mDetails.getAuthPointId(), dependants, this.mDetails.getAuthUsers()));
/*      */ 
/*  867 */       this.mDetails.setAuthUsersAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  871 */     if ((dependants.indexOf("<35>") > -1) && (!this.mDetails.isAuthPointLinksAllItemsLoaded()))
/*      */     {
/*  876 */       this.mDetails.setAuthPointLinks(getVirementAuthPointLinkDAO().getAll(this.mDetails.getAuthPointId(), dependants, this.mDetails.getAuthPointLinks()));
/*      */ 
/*  883 */       this.mDetails.setAuthPointLinksAllItemsLoaded(true);
/*      */     }
/*      */ 
/*  886 */     if ((paramCK instanceof VirementAuthorisersCK))
/*      */     {
/*  888 */       if (this.mDetails.getAuthUsers() == null) {
/*  889 */         this.mDetails.loadAuthUsersItem(getVirementAuthorisersDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  892 */         VirementAuthorisersPK pk = ((VirementAuthorisersCK)paramCK).getVirementAuthorisersPK();
/*  893 */         VirementAuthorisersEVO evo = this.mDetails.getAuthUsersItem(pk);
/*  894 */         if (evo == null) {
/*  895 */           this.mDetails.loadAuthUsersItem(getVirementAuthorisersDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*  899 */     else if ((paramCK instanceof VirementAuthPointLinkCK))
/*      */     {
/*  901 */       if (this.mDetails.getAuthPointLinks() == null) {
/*  902 */         this.mDetails.loadAuthPointLinksItem(getVirementAuthPointLinkDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/*  905 */         VirementAuthPointLinkPK pk = ((VirementAuthPointLinkCK)paramCK).getVirementAuthPointLinkPK();
/*  906 */         VirementAuthPointLinkEVO evo = this.mDetails.getAuthPointLinksItem(pk);
/*  907 */         if (evo == null) {
/*  908 */           this.mDetails.loadAuthPointLinksItem(getVirementAuthPointLinkDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  913 */     VirementAuthPointEVO details = new VirementAuthPointEVO();
/*  914 */     details = this.mDetails.deepClone();
/*      */ 
/*  916 */     if (timer != null) {
/*  917 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/*  919 */     return details;
/*      */   }
/*      */ 
/*      */   public VirementAuthPointEVO getDetails(ModelCK paramCK, VirementAuthPointEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/*  925 */     VirementAuthPointEVO savedEVO = this.mDetails;
/*  926 */     this.mDetails = paramEVO;
/*  927 */     VirementAuthPointEVO newEVO = getDetails(paramCK, dependants);
/*  928 */     this.mDetails = savedEVO;
/*  929 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public VirementAuthPointEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/*  935 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  939 */     getDependants(this.mDetails, dependants);
/*      */ 
/*  942 */     VirementAuthPointEVO details = this.mDetails.deepClone();
/*      */ 
/*  944 */     if (timer != null) {
/*  945 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/*  947 */     return details;
/*      */   }
/*      */ 
/*      */   protected VirementAuthorisersDAO getVirementAuthorisersDAO()
/*      */   {
/*  956 */     if (this.mVirementAuthorisersDAO == null)
/*      */     {
/*  958 */       if (this.mDataSource != null)
/*  959 */         this.mVirementAuthorisersDAO = new VirementAuthorisersDAO(this.mDataSource);
/*      */       else {
/*  961 */         this.mVirementAuthorisersDAO = new VirementAuthorisersDAO(getConnection());
/*      */       }
/*      */     }
/*  964 */     return this.mVirementAuthorisersDAO;
/*      */   }
/*      */ 
/*      */   protected VirementAuthPointLinkDAO getVirementAuthPointLinkDAO()
/*      */   {
/*  973 */     if (this.mVirementAuthPointLinkDAO == null)
/*      */     {
/*  975 */       if (this.mDataSource != null)
/*  976 */         this.mVirementAuthPointLinkDAO = new VirementAuthPointLinkDAO(this.mDataSource);
/*      */       else {
/*  978 */         this.mVirementAuthPointLinkDAO = new VirementAuthPointLinkDAO(getConnection());
/*      */       }
/*      */     }
/*  981 */     return this.mVirementAuthPointLinkDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/*  986 */     return "VirementAuthPoint";
/*      */   }
/*      */ 
/*      */   public VirementAuthPointRefImpl getRef(VirementAuthPointPK paramVirementAuthPointPK)
/*      */   {
/*  991 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  992 */     PreparedStatement stmt = null;
/*  993 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  996 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,VIREMENT_REQUEST.REQUEST_ID from VIREMENT_AUTH_POINT,MODEL,VIREMENT_REQUEST where 1=1 and VIREMENT_AUTH_POINT.AUTH_POINT_ID = ? and VIREMENT_AUTH_POINT.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.REQUEST_ID = MODEL.REQUEST_ID");
/*  997 */       int col = 1;
/*  998 */       stmt.setInt(col++, paramVirementAuthPointPK.getAuthPointId());
/*      */ 
/* 1000 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1002 */       if (!resultSet.next()) {
/* 1003 */         throw new RuntimeException(getEntityName() + " getRef " + paramVirementAuthPointPK + " not found");
/*      */       }
/* 1005 */       col = 2;
/* 1006 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 1010 */       VirementRequestPK newVirementRequestPK = new VirementRequestPK(resultSet.getInt(col++));
/*      */ 
/* 1014 */       String textVirementAuthPoint = "";
/* 1015 */       VirementAuthPointCK ckVirementAuthPoint = new VirementAuthPointCK(newModelPK, newVirementRequestPK, paramVirementAuthPointPK);
/*      */ 
/* 1021 */       VirementAuthPointRefImpl localVirementAuthPointRefImpl = new VirementAuthPointRefImpl(ckVirementAuthPoint, textVirementAuthPoint);
/*      */       return localVirementAuthPointRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1026 */       throw handleSQLException(paramVirementAuthPointPK, "select 0,MODEL.MODEL_ID,VIREMENT_REQUEST.REQUEST_ID from VIREMENT_AUTH_POINT,MODEL,VIREMENT_REQUEST where 1=1 and VIREMENT_AUTH_POINT.AUTH_POINT_ID = ? and VIREMENT_AUTH_POINT.REQUEST_ID = VIREMENT_REQUEST.REQUEST_ID and VIREMENT_REQUEST.REQUEST_ID = MODEL.REQUEST_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1030 */       closeResultSet(resultSet);
/* 1031 */       closeStatement(stmt);
/* 1032 */       closeConnection();
/*      */ 
/* 1034 */       if (timer != null)
/* 1035 */         timer.logDebug("getRef", paramVirementAuthPointPK); 
/* 1035 */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1047 */     if (c == null)
/* 1048 */       return;
/* 1049 */     Iterator iter = c.iterator();
/* 1050 */     while (iter.hasNext())
/*      */     {
/* 1052 */       VirementAuthPointEVO evo = (VirementAuthPointEVO)iter.next();
/* 1053 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(VirementAuthPointEVO evo, String dependants)
/*      */   {
/* 1067 */     if (evo.insertPending()) {
/* 1068 */       return;
/*      */     }
/* 1070 */     if (evo.getAuthPointId() < 1) {
/* 1071 */       return;
/*      */     }
/*      */ 
/* 1075 */     if (dependants.indexOf("<34>") > -1)
/*      */     {
/* 1078 */       if (!evo.isAuthUsersAllItemsLoaded())
/*      */       {
/* 1080 */         evo.setAuthUsers(getVirementAuthorisersDAO().getAll(evo.getAuthPointId(), dependants, evo.getAuthUsers()));
/*      */ 
/* 1087 */         evo.setAuthUsersAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1092 */     if (dependants.indexOf("<35>") > -1)
/*      */     {
/* 1095 */       if (!evo.isAuthPointLinksAllItemsLoaded())
/*      */       {
/* 1097 */         evo.setAuthPointLinks(getVirementAuthPointLinkDAO().getAll(evo.getAuthPointId(), dependants, evo.getAuthPointLinks()));
/*      */ 
/* 1104 */         evo.setAuthPointLinksAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.virement.VirementAuthPointDAO
 * JD-Core Version:    0.6.0
 */