/*     */ package com.cedar.cp.ejb.impl.report.distribution;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.report.distribution.CheckExternalDestinationELO;
/*     */ import com.cedar.cp.dto.report.distribution.CheckInternalDestinationELO;
/*     */ import com.cedar.cp.dto.report.distribution.DistributionCK;
/*     */ import com.cedar.cp.dto.report.distribution.DistributionLinkCK;
/*     */ import com.cedar.cp.dto.report.distribution.DistributionLinkPK;
/*     */ import com.cedar.cp.dto.report.distribution.DistributionLinkRefImpl;
/*     */ import com.cedar.cp.dto.report.distribution.DistributionPK;
/*     */ import com.cedar.cp.dto.report.distribution.DistributionRefImpl;
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
/*     */ public class DistributionLinkDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select DISTRIBUTION_LINK.DISTRIBUTION_ID,DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID,DISTRIBUTION_LINK.DESTINATION_ID,DISTRIBUTION_LINK.DESTINATION_TYPE,DISTRIBUTION_LINK.UPDATED_BY_USER_ID,DISTRIBUTION_LINK.UPDATED_TIME,DISTRIBUTION_LINK.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from DISTRIBUTION_LINK where    DISTRIBUTION_ID = ? AND DISTRIBUTION_LINK_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into DISTRIBUTION_LINK ( DISTRIBUTION_ID,DISTRIBUTION_LINK_ID,DESTINATION_ID,DESTINATION_TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update DISTRIBUTION_LINK set DESTINATION_ID = ?,DESTINATION_TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DISTRIBUTION_ID = ? AND DISTRIBUTION_LINK_ID = ? ";
/* 317 */   protected static String SQL_CHECK_INTERNAL_DESTINATION = "select 0       ,DISTRIBUTION.DISTRIBUTION_ID      ,DISTRIBUTION.VIS_ID      ,DISTRIBUTION_LINK.DISTRIBUTION_ID      ,DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID      ,DISTRIBUTION_LINK.DESTINATION_ID from DISTRIBUTION_LINK    ,DISTRIBUTION where 1=1   and DISTRIBUTION_LINK.DISTRIBUTION_ID = DISTRIBUTION.DISTRIBUTION_ID  and  DISTRIBUTION_LINK.DESTINATION_ID = ? and DISTRIBUTION_LINK.DESTINATION_TYPE = 0";
/*     */ 
/* 428 */   protected static String SQL_CHECK_EXTERNAL_DESTINATION = "select 0       ,DISTRIBUTION.DISTRIBUTION_ID      ,DISTRIBUTION.VIS_ID      ,DISTRIBUTION_LINK.DISTRIBUTION_ID      ,DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID      ,DISTRIBUTION_LINK.DESTINATION_ID from DISTRIBUTION_LINK    ,DISTRIBUTION where 1=1   and DISTRIBUTION_LINK.DISTRIBUTION_ID = DISTRIBUTION.DISTRIBUTION_ID  and  DISTRIBUTION_LINK.DESTINATION_ID = ? and DISTRIBUTION_LINK.DESTINATION_TYPE = 1";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from DISTRIBUTION_LINK where    DISTRIBUTION_ID = ? AND DISTRIBUTION_LINK_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from DISTRIBUTION_LINK where 1=1 and DISTRIBUTION_LINK.DISTRIBUTION_ID = ? order by  DISTRIBUTION_LINK.DISTRIBUTION_ID ,DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID";
/*     */   protected static final String SQL_GET_ALL = " from DISTRIBUTION_LINK where    DISTRIBUTION_ID = ? ";
/*     */   protected DistributionLinkEVO mDetails;
/*     */ 
/*     */   public DistributionLinkDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public DistributionLinkDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DistributionLinkDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected DistributionLinkPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(DistributionLinkEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private DistributionLinkEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  90 */     int col = 1;
/*  91 */     DistributionLinkEVO evo = new DistributionLinkEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++));
/*     */ 
/*  98 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  99 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 100 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 101 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(DistributionLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 106 */     int col = startCol_;
/* 107 */     stmt_.setInt(col++, evo_.getDistributionId());
/* 108 */     stmt_.setInt(col++, evo_.getDistributionLinkId());
/* 109 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(DistributionLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 114 */     int col = startCol_;
/* 115 */     stmt_.setInt(col++, evo_.getDestinationId());
/* 116 */     setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDestinationType());
/* 117 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 118 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 119 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 120 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(DistributionLinkPK pk)
/*     */     throws ValidationException
/*     */   {
/* 137 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 139 */     PreparedStatement stmt = null;
/* 140 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 144 */       stmt = getConnection().prepareStatement("select DISTRIBUTION_LINK.DISTRIBUTION_ID,DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID,DISTRIBUTION_LINK.DESTINATION_ID,DISTRIBUTION_LINK.DESTINATION_TYPE,DISTRIBUTION_LINK.UPDATED_BY_USER_ID,DISTRIBUTION_LINK.UPDATED_TIME,DISTRIBUTION_LINK.CREATED_TIME from DISTRIBUTION_LINK where    DISTRIBUTION_ID = ? AND DISTRIBUTION_LINK_ID = ? ");
/*     */ 
/* 147 */       int col = 1;
/* 148 */       stmt.setInt(col++, pk.getDistributionId());
/* 149 */       stmt.setInt(col++, pk.getDistributionLinkId());
/*     */ 
/* 151 */       resultSet = stmt.executeQuery();
/*     */ 
/* 153 */       if (!resultSet.next()) {
/* 154 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 157 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 158 */       if (this.mDetails.isModified())
/* 159 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 163 */       throw handleSQLException(pk, "select DISTRIBUTION_LINK.DISTRIBUTION_ID,DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID,DISTRIBUTION_LINK.DESTINATION_ID,DISTRIBUTION_LINK.DESTINATION_TYPE,DISTRIBUTION_LINK.UPDATED_BY_USER_ID,DISTRIBUTION_LINK.UPDATED_TIME,DISTRIBUTION_LINK.CREATED_TIME from DISTRIBUTION_LINK where    DISTRIBUTION_ID = ? AND DISTRIBUTION_LINK_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 167 */       closeResultSet(resultSet);
/* 168 */       closeStatement(stmt);
/* 169 */       closeConnection();
/*     */ 
/* 171 */       if (timer != null)
/* 172 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 203 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 204 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 209 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 210 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 211 */       stmt = getConnection().prepareStatement("insert into DISTRIBUTION_LINK ( DISTRIBUTION_ID,DISTRIBUTION_LINK_ID,DESTINATION_ID,DESTINATION_TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*     */ 
/* 214 */       int col = 1;
/* 215 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 216 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 219 */       int resultCount = stmt.executeUpdate();
/* 220 */       if (resultCount != 1)
/*     */       {
/* 222 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 225 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 229 */       throw handleSQLException(this.mDetails.getPK(), "insert into DISTRIBUTION_LINK ( DISTRIBUTION_ID,DISTRIBUTION_LINK_ID,DESTINATION_ID,DESTINATION_TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 233 */       closeStatement(stmt);
/* 234 */       closeConnection();
/*     */ 
/* 236 */       if (timer != null)
/* 237 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 262 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 266 */     PreparedStatement stmt = null;
/*     */ 
/* 268 */     boolean mainChanged = this.mDetails.isModified();
/* 269 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 272 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 275 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 276 */         stmt = getConnection().prepareStatement("update DISTRIBUTION_LINK set DESTINATION_ID = ?,DESTINATION_TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DISTRIBUTION_ID = ? AND DISTRIBUTION_LINK_ID = ? ");
/*     */ 
/* 279 */         int col = 1;
/* 280 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 281 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 284 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 287 */         if (resultCount != 1) {
/* 288 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 291 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 300 */       throw handleSQLException(getPK(), "update DISTRIBUTION_LINK set DESTINATION_ID = ?,DESTINATION_TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DISTRIBUTION_ID = ? AND DISTRIBUTION_LINK_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 304 */       closeStatement(stmt);
/* 305 */       closeConnection();
/*     */ 
/* 307 */       if ((timer != null) && (
/* 308 */         (mainChanged) || (dependantChanged)))
/* 309 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public CheckInternalDestinationELO getCheckInternalDestination(int param1)
/*     */   {
/* 348 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 349 */     PreparedStatement stmt = null;
/* 350 */     ResultSet resultSet = null;
/* 351 */     CheckInternalDestinationELO results = new CheckInternalDestinationELO();
/*     */     try
/*     */     {
/* 354 */       stmt = getConnection().prepareStatement(SQL_CHECK_INTERNAL_DESTINATION);
/* 355 */       int col = 1;
/* 356 */       stmt.setInt(col++, param1);
/* 357 */       resultSet = stmt.executeQuery();
/* 358 */       while (resultSet.next())
/*     */       {
/* 360 */         col = 2;
/*     */ 
/* 363 */         DistributionPK pkDistribution = new DistributionPK(resultSet.getInt(col++));
/*     */ 
/* 366 */         String textDistribution = resultSet.getString(col++);
/*     */ 
/* 369 */         DistributionLinkPK pkDistributionLink = new DistributionLinkPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 373 */         String textDistributionLink = "";
/*     */ 
/* 378 */         DistributionLinkCK ckDistributionLink = new DistributionLinkCK(pkDistribution, pkDistributionLink);
/*     */ 
/* 384 */         DistributionRefImpl erDistribution = new DistributionRefImpl(pkDistribution, textDistribution);
/*     */ 
/* 390 */         DistributionLinkRefImpl erDistributionLink = new DistributionLinkRefImpl(ckDistributionLink, textDistributionLink);
/*     */ 
/* 395 */         int col1 = resultSet.getInt(col++);
/*     */ 
/* 398 */         results.add(erDistributionLink, erDistribution, col1);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 407 */       throw handleSQLException(SQL_CHECK_INTERNAL_DESTINATION, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 411 */       closeResultSet(resultSet);
/* 412 */       closeStatement(stmt);
/* 413 */       closeConnection();
/*     */     }
/*     */ 
/* 416 */     if (timer != null) {
/* 417 */       timer.logDebug("getCheckInternalDestination", " DestinationId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 422 */     return results;
/*     */   }
/*     */ 
/*     */   public CheckExternalDestinationELO getCheckExternalDestination(int param1)
/*     */   {
/* 459 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 460 */     PreparedStatement stmt = null;
/* 461 */     ResultSet resultSet = null;
/* 462 */     CheckExternalDestinationELO results = new CheckExternalDestinationELO();
/*     */     try
/*     */     {
/* 465 */       stmt = getConnection().prepareStatement(SQL_CHECK_EXTERNAL_DESTINATION);
/* 466 */       int col = 1;
/* 467 */       stmt.setInt(col++, param1);
/* 468 */       resultSet = stmt.executeQuery();
/* 469 */       while (resultSet.next())
/*     */       {
/* 471 */         col = 2;
/*     */ 
/* 474 */         DistributionPK pkDistribution = new DistributionPK(resultSet.getInt(col++));
/*     */ 
/* 477 */         String textDistribution = resultSet.getString(col++);
/*     */ 
/* 480 */         DistributionLinkPK pkDistributionLink = new DistributionLinkPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 484 */         String textDistributionLink = "";
/*     */ 
/* 489 */         DistributionLinkCK ckDistributionLink = new DistributionLinkCK(pkDistribution, pkDistributionLink);
/*     */ 
/* 495 */         DistributionRefImpl erDistribution = new DistributionRefImpl(pkDistribution, textDistribution);
/*     */ 
/* 501 */         DistributionLinkRefImpl erDistributionLink = new DistributionLinkRefImpl(ckDistributionLink, textDistributionLink);
/*     */ 
/* 506 */         int col1 = resultSet.getInt(col++);
/*     */ 
/* 509 */         results.add(erDistributionLink, erDistribution, col1);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 518 */       throw handleSQLException(SQL_CHECK_EXTERNAL_DESTINATION, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 522 */       closeResultSet(resultSet);
/* 523 */       closeStatement(stmt);
/* 524 */       closeConnection();
/*     */     }
/*     */ 
/* 527 */     if (timer != null) {
/* 528 */       timer.logDebug("getCheckExternalDestination", " DestinationId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 533 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 551 */     if (items == null) {
/* 552 */       return false;
/*     */     }
/* 554 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 555 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 557 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 562 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 563 */       while (iter2.hasNext())
/*     */       {
/* 565 */         this.mDetails = ((DistributionLinkEVO)iter2.next());
/*     */ 
/* 568 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 570 */         somethingChanged = true;
/*     */ 
/* 573 */         if (deleteStmt == null) {
/* 574 */           deleteStmt = getConnection().prepareStatement("delete from DISTRIBUTION_LINK where    DISTRIBUTION_ID = ? AND DISTRIBUTION_LINK_ID = ? ");
/*     */         }
/*     */ 
/* 577 */         int col = 1;
/* 578 */         deleteStmt.setInt(col++, this.mDetails.getDistributionId());
/* 579 */         deleteStmt.setInt(col++, this.mDetails.getDistributionLinkId());
/*     */ 
/* 581 */         if (this._log.isDebugEnabled()) {
/* 582 */           this._log.debug("update", "DistributionLink deleting DistributionId=" + this.mDetails.getDistributionId() + ",DistributionLinkId=" + this.mDetails.getDistributionLinkId());
/*     */         }
/*     */ 
/* 588 */         deleteStmt.addBatch();
/*     */ 
/* 591 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 596 */       if (deleteStmt != null)
/*     */       {
/* 598 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 600 */         deleteStmt.executeBatch();
/*     */ 
/* 602 */         if (timer2 != null) {
/* 603 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 607 */       Iterator iter1 = items.values().iterator();
/* 608 */       while (iter1.hasNext())
/*     */       {
/* 610 */         this.mDetails = ((DistributionLinkEVO)iter1.next());
/*     */ 
/* 612 */         if (this.mDetails.insertPending())
/*     */         {
/* 614 */           somethingChanged = true;
/* 615 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 618 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 620 */         somethingChanged = true;
/* 621 */         doStore();
/*     */       }
/*     */ 
/* 632 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 636 */       throw handleSQLException("delete from DISTRIBUTION_LINK where    DISTRIBUTION_ID = ? AND DISTRIBUTION_LINK_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 640 */       if (deleteStmt != null)
/*     */       {
/* 642 */         closeStatement(deleteStmt);
/* 643 */         closeConnection();
/*     */       }
/*     */ 
/* 646 */       this.mDetails = null;
/*     */ 
/* 648 */       if ((somethingChanged) && 
/* 649 */         (timer != null))
/* 650 */         timer.logDebug("update", "collection"); 
/* 650 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(DistributionPK entityPK, DistributionEVO owningEVO, String dependants)
/*     */   {
/* 670 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 672 */     PreparedStatement stmt = null;
/* 673 */     ResultSet resultSet = null;
/*     */ 
/* 675 */     int itemCount = 0;
/*     */ 
/* 677 */     Collection theseItems = new ArrayList();
/* 678 */     owningEVO.setDistributionDestinationList(theseItems);
/* 679 */     owningEVO.setDistributionDestinationListAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 683 */       stmt = getConnection().prepareStatement("select DISTRIBUTION_LINK.DISTRIBUTION_ID,DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID,DISTRIBUTION_LINK.DESTINATION_ID,DISTRIBUTION_LINK.DESTINATION_TYPE,DISTRIBUTION_LINK.UPDATED_BY_USER_ID,DISTRIBUTION_LINK.UPDATED_TIME,DISTRIBUTION_LINK.CREATED_TIME from DISTRIBUTION_LINK where 1=1 and DISTRIBUTION_LINK.DISTRIBUTION_ID = ? order by  DISTRIBUTION_LINK.DISTRIBUTION_ID ,DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID");
/*     */ 
/* 685 */       int col = 1;
/* 686 */       stmt.setInt(col++, entityPK.getDistributionId());
/*     */ 
/* 688 */       resultSet = stmt.executeQuery();
/*     */ 
/* 691 */       while (resultSet.next())
/*     */       {
/* 693 */         itemCount++;
/* 694 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 696 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 699 */       if (timer != null) {
/* 700 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 705 */       throw handleSQLException("select DISTRIBUTION_LINK.DISTRIBUTION_ID,DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID,DISTRIBUTION_LINK.DESTINATION_ID,DISTRIBUTION_LINK.DESTINATION_TYPE,DISTRIBUTION_LINK.UPDATED_BY_USER_ID,DISTRIBUTION_LINK.UPDATED_TIME,DISTRIBUTION_LINK.CREATED_TIME from DISTRIBUTION_LINK where 1=1 and DISTRIBUTION_LINK.DISTRIBUTION_ID = ? order by  DISTRIBUTION_LINK.DISTRIBUTION_ID ,DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 709 */       closeResultSet(resultSet);
/* 710 */       closeStatement(stmt);
/* 711 */       closeConnection();
/*     */ 
/* 713 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectDistributionId, String dependants, Collection currentList)
/*     */   {
/* 738 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 739 */     PreparedStatement stmt = null;
/* 740 */     ResultSet resultSet = null;
/*     */ 
/* 742 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 746 */       stmt = getConnection().prepareStatement("select DISTRIBUTION_LINK.DISTRIBUTION_ID,DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID,DISTRIBUTION_LINK.DESTINATION_ID,DISTRIBUTION_LINK.DESTINATION_TYPE,DISTRIBUTION_LINK.UPDATED_BY_USER_ID,DISTRIBUTION_LINK.UPDATED_TIME,DISTRIBUTION_LINK.CREATED_TIME from DISTRIBUTION_LINK where    DISTRIBUTION_ID = ? ");
/*     */ 
/* 748 */       int col = 1;
/* 749 */       stmt.setInt(col++, selectDistributionId);
/*     */ 
/* 751 */       resultSet = stmt.executeQuery();
/*     */ 
/* 753 */       while (resultSet.next())
/*     */       {
/* 755 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 758 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 761 */       if (currentList != null)
/*     */       {
/* 764 */         ListIterator iter = items.listIterator();
/* 765 */         DistributionLinkEVO currentEVO = null;
/* 766 */         DistributionLinkEVO newEVO = null;
/* 767 */         while (iter.hasNext())
/*     */         {
/* 769 */           newEVO = (DistributionLinkEVO)iter.next();
/* 770 */           Iterator iter2 = currentList.iterator();
/* 771 */           while (iter2.hasNext())
/*     */           {
/* 773 */             currentEVO = (DistributionLinkEVO)iter2.next();
/* 774 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 776 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 782 */         Iterator iter2 = currentList.iterator();
/* 783 */         while (iter2.hasNext())
/*     */         {
/* 785 */           currentEVO = (DistributionLinkEVO)iter2.next();
/* 786 */           if (currentEVO.insertPending()) {
/* 787 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 791 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 795 */       throw handleSQLException("select DISTRIBUTION_LINK.DISTRIBUTION_ID,DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID,DISTRIBUTION_LINK.DESTINATION_ID,DISTRIBUTION_LINK.DESTINATION_TYPE,DISTRIBUTION_LINK.UPDATED_BY_USER_ID,DISTRIBUTION_LINK.UPDATED_TIME,DISTRIBUTION_LINK.CREATED_TIME from DISTRIBUTION_LINK where    DISTRIBUTION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 799 */       closeResultSet(resultSet);
/* 800 */       closeStatement(stmt);
/* 801 */       closeConnection();
/*     */ 
/* 803 */       if (timer != null) {
/* 804 */         timer.logDebug("getAll", " DistributionId=" + selectDistributionId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 809 */     return items;
/*     */   }
/*     */ 
/*     */   public DistributionLinkEVO getDetails(DistributionCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 823 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 826 */     if (this.mDetails == null) {
/* 827 */       doLoad(((DistributionLinkCK)paramCK).getDistributionLinkPK());
/*     */     }
/* 829 */     else if (!this.mDetails.getPK().equals(((DistributionLinkCK)paramCK).getDistributionLinkPK())) {
/* 830 */       doLoad(((DistributionLinkCK)paramCK).getDistributionLinkPK());
/*     */     }
/*     */ 
/* 833 */     DistributionLinkEVO details = new DistributionLinkEVO();
/* 834 */     details = this.mDetails.deepClone();
/*     */ 
/* 836 */     if (timer != null) {
/* 837 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 839 */     return details;
/*     */   }
/*     */ 
/*     */   public DistributionLinkEVO getDetails(DistributionCK paramCK, DistributionLinkEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 845 */     DistributionLinkEVO savedEVO = this.mDetails;
/* 846 */     this.mDetails = paramEVO;
/* 847 */     DistributionLinkEVO newEVO = getDetails(paramCK, dependants);
/* 848 */     this.mDetails = savedEVO;
/* 849 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public DistributionLinkEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 855 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 859 */     DistributionLinkEVO details = this.mDetails.deepClone();
/*     */ 
/* 861 */     if (timer != null) {
/* 862 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 864 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 869 */     return "DistributionLink";
/*     */   }
/*     */ 
/*     */   public DistributionLinkRefImpl getRef(DistributionLinkPK paramDistributionLinkPK)
/*     */   {
/* 874 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 875 */     PreparedStatement stmt = null;
/* 876 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 879 */       stmt = getConnection().prepareStatement("select 0,DISTRIBUTION.DISTRIBUTION_ID from DISTRIBUTION_LINK,DISTRIBUTION where 1=1 and DISTRIBUTION_LINK.DISTRIBUTION_ID = ? and DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID = ? and DISTRIBUTION_LINK.DISTRIBUTION_ID = DISTRIBUTION.DISTRIBUTION_ID");
/* 880 */       int col = 1;
/* 881 */       stmt.setInt(col++, paramDistributionLinkPK.getDistributionId());
/* 882 */       stmt.setInt(col++, paramDistributionLinkPK.getDistributionLinkId());
/*     */ 
/* 884 */       resultSet = stmt.executeQuery();
/*     */ 
/* 886 */       if (!resultSet.next()) {
/* 887 */         throw new RuntimeException(getEntityName() + " getRef " + paramDistributionLinkPK + " not found");
/*     */       }
/* 889 */       col = 2;
/* 890 */       DistributionPK newDistributionPK = new DistributionPK(resultSet.getInt(col++));
/*     */ 
/* 894 */       String textDistributionLink = "";
/* 895 */       DistributionLinkCK ckDistributionLink = new DistributionLinkCK(newDistributionPK, paramDistributionLinkPK);
/*     */ 
/* 900 */       DistributionLinkRefImpl localDistributionLinkRefImpl = new DistributionLinkRefImpl(ckDistributionLink, textDistributionLink);
/*     */       return localDistributionLinkRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 905 */       throw handleSQLException(paramDistributionLinkPK, "select 0,DISTRIBUTION.DISTRIBUTION_ID from DISTRIBUTION_LINK,DISTRIBUTION where 1=1 and DISTRIBUTION_LINK.DISTRIBUTION_ID = ? and DISTRIBUTION_LINK.DISTRIBUTION_LINK_ID = ? and DISTRIBUTION_LINK.DISTRIBUTION_ID = DISTRIBUTION.DISTRIBUTION_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 909 */       closeResultSet(resultSet);
/* 910 */       closeStatement(stmt);
/* 911 */       closeConnection();
/*     */ 
/* 913 */       if (timer != null)
/* 914 */         timer.logDebug("getRef", paramDistributionLinkPK); 
/* 914 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.report.distribution.DistributionLinkDAO
 * JD-Core Version:    0.6.0
 */