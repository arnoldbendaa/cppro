/*     */ package com.cedar.cp.ejb.impl.model.virement;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.dimension.StructureElementPK;
/*     */ import com.cedar.cp.dto.dimension.StructureElementRefImpl;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.ModelRefImpl;
/*     */ import com.cedar.cp.dto.model.virement.LocationsForCategoryELO;
/*     */ import com.cedar.cp.dto.model.virement.VirementCategoryCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementCategoryPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementCategoryRefImpl;
/*     */ import com.cedar.cp.dto.model.virement.VirementLocationCK;
/*     */ import com.cedar.cp.dto.model.virement.VirementLocationPK;
/*     */ import com.cedar.cp.dto.model.virement.VirementLocationRefImpl;
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
/*     */ public class VirementLocationDAO extends AbstractDAO
/*     */ {
/*  38 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select VIREMENT_LOCATION.VIREMENT_CATEGORY_ID,VIREMENT_LOCATION.STRUCTURE_ID,VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID,VIREMENT_LOCATION.VERSION_NUM,VIREMENT_LOCATION.UPDATED_BY_USER_ID,VIREMENT_LOCATION.UPDATED_TIME,VIREMENT_LOCATION.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from VIREMENT_LOCATION where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into VIREMENT_LOCATION ( VIREMENT_CATEGORY_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update VIREMENT_LOCATION set VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND VERSION_NUM = ?";
/*     */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from VIREMENT_LOCATION where VIREMENT_CATEGORY_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?";
/* 384 */   protected static String SQL_LOCATIONS_FOR_CATEGORY = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID      ,VIREMENT_CATEGORY.VIS_ID      ,VIREMENT_LOCATION.VIREMENT_CATEGORY_ID      ,VIREMENT_LOCATION.STRUCTURE_ID      ,VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION from VIREMENT_LOCATION    ,MODEL    ,VIREMENT_CATEGORY    ,STRUCTURE_ELEMENT where 1=1   and VIREMENT_LOCATION.VIREMENT_CATEGORY_ID = VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID   and VIREMENT_CATEGORY.MODEL_ID = MODEL.MODEL_ID  and  VIREMENT_LOCATION.VIREMENT_CATEGORY_ID = ? and VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID = STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from VIREMENT_LOCATION where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from VIREMENT_LOCATION,VIREMENT_CATEGORY where 1=1 and VIREMENT_LOCATION.VIREMENT_CATEGORY_ID = VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID and VIREMENT_CATEGORY.MODEL_ID = ? order by  VIREMENT_LOCATION.VIREMENT_CATEGORY_ID ,VIREMENT_LOCATION.VIREMENT_CATEGORY_ID ,VIREMENT_LOCATION.STRUCTURE_ID ,VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID";
/*     */   protected static final String SQL_GET_ALL = " from VIREMENT_LOCATION where    VIREMENT_CATEGORY_ID = ? ";
/*     */   private static final String ERASE_ORPHANS_SQL = "delete from virement_location vl \nwhere vl.rowid in \n( \n\tselect vl.rowid \n\tfrom virement_location vl, \n\t     virement_category vc \n\twhere vc.model_id = ? and \n\t      vc.virement_category_id = vl.virement_category_id and \n\t      vl.structure_element_id not in ( select sev.structure_element_id \n\t\t\t\t\t\t\t\t\t       from structure_element_view sev \n\t\t\t\t\t\t\t\t\t       where sev.structure_id = vl.structure_id ) \n)";
/*     */   protected VirementLocationEVO mDetails;
/*     */ 
/*     */   public VirementLocationDAO(Connection connection)
/*     */   {
/*  45 */     super(connection);
/*     */   }
/*     */ 
/*     */   public VirementLocationDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public VirementLocationDAO(DataSource ds)
/*     */   {
/*  61 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected VirementLocationPK getPK()
/*     */   {
/*  69 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(VirementLocationEVO details)
/*     */   {
/*  78 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private VirementLocationEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  96 */     int col = 1;
/*  97 */     VirementLocationEVO evo = new VirementLocationEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/* 104 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 105 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 106 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 107 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(VirementLocationEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 112 */     int col = startCol_;
/* 113 */     stmt_.setInt(col++, evo_.getVirementCategoryId());
/* 114 */     stmt_.setInt(col++, evo_.getStructureId());
/* 115 */     stmt_.setInt(col++, evo_.getStructureElementId());
/* 116 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(VirementLocationEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 121 */     int col = startCol_;
/* 122 */     stmt_.setInt(col++, evo_.getVersionNum());
/* 123 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 124 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 125 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 126 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(VirementLocationPK pk)
/*     */     throws ValidationException
/*     */   {
/* 144 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 146 */     PreparedStatement stmt = null;
/* 147 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 151 */       stmt = getConnection().prepareStatement("select VIREMENT_LOCATION.VIREMENT_CATEGORY_ID,VIREMENT_LOCATION.STRUCTURE_ID,VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID,VIREMENT_LOCATION.VERSION_NUM,VIREMENT_LOCATION.UPDATED_BY_USER_ID,VIREMENT_LOCATION.UPDATED_TIME,VIREMENT_LOCATION.CREATED_TIME from VIREMENT_LOCATION where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*     */ 
/* 154 */       int col = 1;
/* 155 */       stmt.setInt(col++, pk.getVirementCategoryId());
/* 156 */       stmt.setInt(col++, pk.getStructureId());
/* 157 */       stmt.setInt(col++, pk.getStructureElementId());
/*     */ 
/* 159 */       resultSet = stmt.executeQuery();
/*     */ 
/* 161 */       if (!resultSet.next()) {
/* 162 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 165 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 166 */       if (this.mDetails.isModified())
/* 167 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 171 */       throw handleSQLException(pk, "select VIREMENT_LOCATION.VIREMENT_CATEGORY_ID,VIREMENT_LOCATION.STRUCTURE_ID,VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID,VIREMENT_LOCATION.VERSION_NUM,VIREMENT_LOCATION.UPDATED_BY_USER_ID,VIREMENT_LOCATION.UPDATED_TIME,VIREMENT_LOCATION.CREATED_TIME from VIREMENT_LOCATION where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 175 */       closeResultSet(resultSet);
/* 176 */       closeStatement(stmt);
/* 177 */       closeConnection();
/*     */ 
/* 179 */       if (timer != null)
/* 180 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 211 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 212 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 217 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 218 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 219 */       stmt = getConnection().prepareStatement("insert into VIREMENT_LOCATION ( VIREMENT_CATEGORY_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");
/*     */ 
/* 222 */       int col = 1;
/* 223 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 224 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 227 */       int resultCount = stmt.executeUpdate();
/* 228 */       if (resultCount != 1)
/*     */       {
/* 230 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 233 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 237 */       throw handleSQLException(this.mDetails.getPK(), "insert into VIREMENT_LOCATION ( VIREMENT_CATEGORY_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 241 */       closeStatement(stmt);
/* 242 */       closeConnection();
/*     */ 
/* 244 */       if (timer != null)
/* 245 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 271 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 275 */     PreparedStatement stmt = null;
/*     */ 
/* 277 */     boolean mainChanged = this.mDetails.isModified();
/* 278 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 281 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 284 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*     */ 
/* 287 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 288 */         stmt = getConnection().prepareStatement("update VIREMENT_LOCATION set VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND VERSION_NUM = ?");
/*     */ 
/* 291 */         int col = 1;
/* 292 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 293 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 295 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*     */ 
/* 298 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 300 */         if (resultCount == 0) {
/* 301 */           checkVersionNum();
/*     */         }
/* 303 */         if (resultCount != 1) {
/* 304 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 307 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 316 */       throw handleSQLException(getPK(), "update VIREMENT_LOCATION set VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND VERSION_NUM = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 320 */       closeStatement(stmt);
/* 321 */       closeConnection();
/*     */ 
/* 323 */       if ((timer != null) && (
/* 324 */         (mainChanged) || (dependantChanged)))
/* 325 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkVersionNum()
/*     */     throws VersionValidationException
/*     */   {
/* 339 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 340 */     PreparedStatement stmt = null;
/* 341 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 345 */       stmt = getConnection().prepareStatement("select VERSION_NUM from VIREMENT_LOCATION where VIREMENT_CATEGORY_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?");
/*     */ 
/* 348 */       int col = 1;
/* 349 */       stmt.setInt(col++, this.mDetails.getVirementCategoryId());
/* 350 */       stmt.setInt(col++, this.mDetails.getStructureId());
/* 351 */       stmt.setInt(col++, this.mDetails.getStructureElementId());
/*     */ 
/* 354 */       resultSet = stmt.executeQuery();
/*     */ 
/* 356 */       if (!resultSet.next()) {
/* 357 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 360 */       col = 1;
/* 361 */       int dbVersionNumber = resultSet.getInt(col++);
/* 362 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/* 363 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 369 */       throw handleSQLException(getPK(), "select VERSION_NUM from VIREMENT_LOCATION where VIREMENT_CATEGORY_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 373 */       closeStatement(stmt);
/* 374 */       closeResultSet(resultSet);
/*     */ 
/* 376 */       if (timer != null)
/* 377 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public LocationsForCategoryELO getLocationsForCategory(int param1)
/*     */   {
/* 428 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 429 */     PreparedStatement stmt = null;
/* 430 */     ResultSet resultSet = null;
/* 431 */     LocationsForCategoryELO results = new LocationsForCategoryELO();
/*     */     try
/*     */     {
/* 434 */       stmt = getConnection().prepareStatement(SQL_LOCATIONS_FOR_CATEGORY);
/* 435 */       int col = 1;
/* 436 */       stmt.setInt(col++, param1);
/* 437 */       resultSet = stmt.executeQuery();
/* 438 */       while (resultSet.next())
/*     */       {
/* 440 */         col = 2;
/*     */ 
/* 443 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 446 */         String textModel = resultSet.getString(col++);
/*     */ 
/* 448 */         VirementCategoryPK pkVirementCategory = new VirementCategoryPK(resultSet.getInt(col++));
/*     */ 
/* 451 */         String textVirementCategory = resultSet.getString(col++);
/*     */ 
/* 454 */         VirementLocationPK pkVirementLocation = new VirementLocationPK(resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 459 */         String textVirementLocation = "";
/*     */ 
/* 462 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 466 */         String textStructureElement = resultSet.getString(col++);
/*     */ 
/* 470 */         VirementCategoryCK ckVirementCategory = new VirementCategoryCK(pkModel, pkVirementCategory);
/*     */ 
/* 476 */         VirementLocationCK ckVirementLocation = new VirementLocationCK(pkModel, pkVirementCategory, pkVirementLocation);
/*     */ 
/* 483 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*     */ 
/* 489 */         VirementCategoryRefImpl erVirementCategory = new VirementCategoryRefImpl(ckVirementCategory, textVirementCategory);
/*     */ 
/* 495 */         VirementLocationRefImpl erVirementLocation = new VirementLocationRefImpl(ckVirementLocation, textVirementLocation);
/*     */ 
/* 501 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*     */ 
/* 506 */         String col1 = resultSet.getString(col++);
/* 507 */         String col2 = resultSet.getString(col++);
/*     */ 
/* 510 */         results.add(erVirementLocation, erVirementCategory, erModel, erStructureElement, col1, col2);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 522 */       throw handleSQLException(SQL_LOCATIONS_FOR_CATEGORY, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 526 */       closeResultSet(resultSet);
/* 527 */       closeStatement(stmt);
/* 528 */       closeConnection();
/*     */     }
/*     */ 
/* 531 */     if (timer != null) {
/* 532 */       timer.logDebug("getLocationsForCategory", " VirementCategoryId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 537 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 556 */     if (items == null) {
/* 557 */       return false;
/*     */     }
/* 559 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 560 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 562 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 567 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 568 */       while (iter2.hasNext())
/*     */       {
/* 570 */         this.mDetails = ((VirementLocationEVO)iter2.next());
/*     */ 
/* 573 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 575 */         somethingChanged = true;
/*     */ 
/* 578 */         if (deleteStmt == null) {
/* 579 */           deleteStmt = getConnection().prepareStatement("delete from VIREMENT_LOCATION where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*     */         }
/*     */ 
/* 582 */         int col = 1;
/* 583 */         deleteStmt.setInt(col++, this.mDetails.getVirementCategoryId());
/* 584 */         deleteStmt.setInt(col++, this.mDetails.getStructureId());
/* 585 */         deleteStmt.setInt(col++, this.mDetails.getStructureElementId());
/*     */ 
/* 587 */         if (this._log.isDebugEnabled()) {
/* 588 */           this._log.debug("update", "VirementLocation deleting VirementCategoryId=" + this.mDetails.getVirementCategoryId() + ",StructureId=" + this.mDetails.getStructureId() + ",StructureElementId=" + this.mDetails.getStructureElementId());
/*     */         }
/*     */ 
/* 595 */         deleteStmt.addBatch();
/*     */ 
/* 598 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 603 */       if (deleteStmt != null)
/*     */       {
/* 605 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 607 */         deleteStmt.executeBatch();
/*     */ 
/* 609 */         if (timer2 != null) {
/* 610 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 614 */       Iterator iter1 = items.values().iterator();
/* 615 */       while (iter1.hasNext())
/*     */       {
/* 617 */         this.mDetails = ((VirementLocationEVO)iter1.next());
/*     */ 
/* 619 */         if (this.mDetails.insertPending())
/*     */         {
/* 621 */           somethingChanged = true;
/* 622 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 625 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 627 */         somethingChanged = true;
/* 628 */         doStore();
/*     */       }
/*     */ 
/* 639 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 643 */       throw handleSQLException("delete from VIREMENT_LOCATION where    VIREMENT_CATEGORY_ID = ? AND STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 647 */       if (deleteStmt != null)
/*     */       {
/* 649 */         closeStatement(deleteStmt);
/* 650 */         closeConnection();
/*     */       }
/*     */ 
/* 653 */       this.mDetails = null;
/*     */ 
/* 655 */       if ((somethingChanged) && 
/* 656 */         (timer != null))
/* 657 */         timer.logDebug("update", "collection"); 
/* 657 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants)
/*     */   {
/* 682 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 684 */     PreparedStatement stmt = null;
/* 685 */     ResultSet resultSet = null;
/*     */ 
/* 687 */     int itemCount = 0;
/*     */ 
/* 689 */     VirementCategoryEVO owningEVO = null;
/* 690 */     Iterator ownersIter = owners.iterator();
/* 691 */     while (ownersIter.hasNext())
/*     */     {
/* 693 */       owningEVO = (VirementCategoryEVO)ownersIter.next();
/* 694 */       owningEVO.setVirementResponsibilityAreasAllItemsLoaded(true);
/*     */     }
/* 696 */     ownersIter = owners.iterator();
/* 697 */     owningEVO = (VirementCategoryEVO)ownersIter.next();
/* 698 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 702 */       stmt = getConnection().prepareStatement("select VIREMENT_LOCATION.VIREMENT_CATEGORY_ID,VIREMENT_LOCATION.STRUCTURE_ID,VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID,VIREMENT_LOCATION.VERSION_NUM,VIREMENT_LOCATION.UPDATED_BY_USER_ID,VIREMENT_LOCATION.UPDATED_TIME,VIREMENT_LOCATION.CREATED_TIME from VIREMENT_LOCATION,VIREMENT_CATEGORY where 1=1 and VIREMENT_LOCATION.VIREMENT_CATEGORY_ID = VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID and VIREMENT_CATEGORY.MODEL_ID = ? order by  VIREMENT_LOCATION.VIREMENT_CATEGORY_ID ,VIREMENT_LOCATION.VIREMENT_CATEGORY_ID ,VIREMENT_LOCATION.STRUCTURE_ID ,VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID");
/*     */ 
/* 704 */       int col = 1;
/* 705 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 707 */       resultSet = stmt.executeQuery();
/*     */ 
/* 710 */       while (resultSet.next())
/*     */       {
/* 712 */         itemCount++;
/* 713 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 718 */         while (this.mDetails.getVirementCategoryId() != owningEVO.getVirementCategoryId())
/*     */         {
/* 722 */           if (!ownersIter.hasNext())
/*     */           {
/* 724 */             this._log.debug("bulkGetAll", "can't find owning [VirementCategoryId=" + this.mDetails.getVirementCategoryId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 728 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 729 */             ownersIter = owners.iterator();
/* 730 */             while (ownersIter.hasNext())
/*     */             {
/* 732 */               owningEVO = (VirementCategoryEVO)ownersIter.next();
/* 733 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 735 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 737 */           owningEVO = (VirementCategoryEVO)ownersIter.next();
/*     */         }
/* 739 */         if (owningEVO.getVirementResponsibilityAreas() == null)
/*     */         {
/* 741 */           theseItems = new ArrayList();
/* 742 */           owningEVO.setVirementResponsibilityAreas(theseItems);
/* 743 */           owningEVO.setVirementResponsibilityAreasAllItemsLoaded(true);
/*     */         }
/* 745 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 748 */       if (timer != null) {
/* 749 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 754 */       throw handleSQLException("select VIREMENT_LOCATION.VIREMENT_CATEGORY_ID,VIREMENT_LOCATION.STRUCTURE_ID,VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID,VIREMENT_LOCATION.VERSION_NUM,VIREMENT_LOCATION.UPDATED_BY_USER_ID,VIREMENT_LOCATION.UPDATED_TIME,VIREMENT_LOCATION.CREATED_TIME from VIREMENT_LOCATION,VIREMENT_CATEGORY where 1=1 and VIREMENT_LOCATION.VIREMENT_CATEGORY_ID = VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID and VIREMENT_CATEGORY.MODEL_ID = ? order by  VIREMENT_LOCATION.VIREMENT_CATEGORY_ID ,VIREMENT_LOCATION.VIREMENT_CATEGORY_ID ,VIREMENT_LOCATION.STRUCTURE_ID ,VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 758 */       closeResultSet(resultSet);
/* 759 */       closeStatement(stmt);
/* 760 */       closeConnection();
/*     */ 
/* 762 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectVirementCategoryId, String dependants, Collection currentList)
/*     */   {
/* 787 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 788 */     PreparedStatement stmt = null;
/* 789 */     ResultSet resultSet = null;
/*     */ 
/* 791 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 795 */       stmt = getConnection().prepareStatement("select VIREMENT_LOCATION.VIREMENT_CATEGORY_ID,VIREMENT_LOCATION.STRUCTURE_ID,VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID,VIREMENT_LOCATION.VERSION_NUM,VIREMENT_LOCATION.UPDATED_BY_USER_ID,VIREMENT_LOCATION.UPDATED_TIME,VIREMENT_LOCATION.CREATED_TIME from VIREMENT_LOCATION where    VIREMENT_CATEGORY_ID = ? ");
/*     */ 
/* 797 */       int col = 1;
/* 798 */       stmt.setInt(col++, selectVirementCategoryId);
/*     */ 
/* 800 */       resultSet = stmt.executeQuery();
/*     */ 
/* 802 */       while (resultSet.next())
/*     */       {
/* 804 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 807 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 810 */       if (currentList != null)
/*     */       {
/* 813 */         ListIterator iter = items.listIterator();
/* 814 */         VirementLocationEVO currentEVO = null;
/* 815 */         VirementLocationEVO newEVO = null;
/* 816 */         while (iter.hasNext())
/*     */         {
/* 818 */           newEVO = (VirementLocationEVO)iter.next();
/* 819 */           Iterator iter2 = currentList.iterator();
/* 820 */           while (iter2.hasNext())
/*     */           {
/* 822 */             currentEVO = (VirementLocationEVO)iter2.next();
/* 823 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 825 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 831 */         Iterator iter2 = currentList.iterator();
/* 832 */         while (iter2.hasNext())
/*     */         {
/* 834 */           currentEVO = (VirementLocationEVO)iter2.next();
/* 835 */           if (currentEVO.insertPending()) {
/* 836 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 840 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 844 */       throw handleSQLException("select VIREMENT_LOCATION.VIREMENT_CATEGORY_ID,VIREMENT_LOCATION.STRUCTURE_ID,VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID,VIREMENT_LOCATION.VERSION_NUM,VIREMENT_LOCATION.UPDATED_BY_USER_ID,VIREMENT_LOCATION.UPDATED_TIME,VIREMENT_LOCATION.CREATED_TIME from VIREMENT_LOCATION where    VIREMENT_CATEGORY_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 848 */       closeResultSet(resultSet);
/* 849 */       closeStatement(stmt);
/* 850 */       closeConnection();
/*     */ 
/* 852 */       if (timer != null) {
/* 853 */         timer.logDebug("getAll", " VirementCategoryId=" + selectVirementCategoryId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 858 */     return items;
/*     */   }
/*     */ 
/*     */   public VirementLocationEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 872 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 875 */     if (this.mDetails == null) {
/* 876 */       doLoad(((VirementLocationCK)paramCK).getVirementLocationPK());
/*     */     }
/* 878 */     else if (!this.mDetails.getPK().equals(((VirementLocationCK)paramCK).getVirementLocationPK())) {
/* 879 */       doLoad(((VirementLocationCK)paramCK).getVirementLocationPK());
/*     */     }
/*     */ 
/* 882 */     VirementLocationEVO details = new VirementLocationEVO();
/* 883 */     details = this.mDetails.deepClone();
/*     */ 
/* 885 */     if (timer != null) {
/* 886 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 888 */     return details;
/*     */   }
/*     */ 
/*     */   public VirementLocationEVO getDetails(ModelCK paramCK, VirementLocationEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 894 */     VirementLocationEVO savedEVO = this.mDetails;
/* 895 */     this.mDetails = paramEVO;
/* 896 */     VirementLocationEVO newEVO = getDetails(paramCK, dependants);
/* 897 */     this.mDetails = savedEVO;
/* 898 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public VirementLocationEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 904 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 908 */     VirementLocationEVO details = this.mDetails.deepClone();
/*     */ 
/* 910 */     if (timer != null) {
/* 911 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 913 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 918 */     return "VirementLocation";
/*     */   }
/*     */ 
/*     */   public VirementLocationRefImpl getRef(VirementLocationPK paramVirementLocationPK)
/*     */   {
/* 923 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 924 */     PreparedStatement stmt = null;
/* 925 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 928 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID from VIREMENT_LOCATION,MODEL,VIREMENT_CATEGORY where 1=1 and VIREMENT_LOCATION.VIREMENT_CATEGORY_ID = ? and VIREMENT_LOCATION.STRUCTURE_ID = ? and VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID = ? and VIREMENT_LOCATION.VIREMENT_CATEGORY_ID = VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID and VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID = MODEL.VIREMENT_CATEGORY_ID");
/* 929 */       int col = 1;
/* 930 */       stmt.setInt(col++, paramVirementLocationPK.getVirementCategoryId());
/* 931 */       stmt.setInt(col++, paramVirementLocationPK.getStructureId());
/* 932 */       stmt.setInt(col++, paramVirementLocationPK.getStructureElementId());
/*     */ 
/* 934 */       resultSet = stmt.executeQuery();
/*     */ 
/* 936 */       if (!resultSet.next()) {
/* 937 */         throw new RuntimeException(getEntityName() + " getRef " + paramVirementLocationPK + " not found");
/*     */       }
/* 939 */       col = 2;
/* 940 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 944 */       VirementCategoryPK newVirementCategoryPK = new VirementCategoryPK(resultSet.getInt(col++));
/*     */ 
/* 948 */       String textVirementLocation = "";
/* 949 */       VirementLocationCK ckVirementLocation = new VirementLocationCK(newModelPK, newVirementCategoryPK, paramVirementLocationPK);
/*     */ 
/* 955 */       VirementLocationRefImpl localVirementLocationRefImpl = new VirementLocationRefImpl(ckVirementLocation, textVirementLocation);
/*     */       return localVirementLocationRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 960 */       throw handleSQLException(paramVirementLocationPK, "select 0,MODEL.MODEL_ID,VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID from VIREMENT_LOCATION,MODEL,VIREMENT_CATEGORY where 1=1 and VIREMENT_LOCATION.VIREMENT_CATEGORY_ID = ? and VIREMENT_LOCATION.STRUCTURE_ID = ? and VIREMENT_LOCATION.STRUCTURE_ELEMENT_ID = ? and VIREMENT_LOCATION.VIREMENT_CATEGORY_ID = VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID and VIREMENT_CATEGORY.VIREMENT_CATEGORY_ID = MODEL.VIREMENT_CATEGORY_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 964 */       closeResultSet(resultSet);
/* 965 */       closeStatement(stmt);
/* 966 */       closeConnection();
/*     */ 
/* 968 */       if (timer != null)
/* 969 */         timer.logDebug("getRef", paramVirementLocationPK); 
/* 969 */     }
/*     */   }
/*     */ 
/*     */   public void eraseOrphanedLocations(int modelId)
/*     */   {
/* 996 */     PreparedStatement ps = null;
/*     */     try
/*     */     {
/* 999 */       ps = getConnection().prepareStatement("delete from virement_location vl \nwhere vl.rowid in \n( \n\tselect vl.rowid \n\tfrom virement_location vl, \n\t     virement_category vc \n\twhere vc.model_id = ? and \n\t      vc.virement_category_id = vl.virement_category_id and \n\t      vl.structure_element_id not in ( select sev.structure_element_id \n\t\t\t\t\t\t\t\t\t       from structure_element_view sev \n\t\t\t\t\t\t\t\t\t       where sev.structure_id = vl.structure_id ) \n)");
/* 1000 */       ps.setInt(1, modelId);
/* 1001 */       ps.executeUpdate();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 1005 */       handleSQLException("eraseOrphanedLocations", e);
/*     */     }
/*     */     finally
/*     */     {
/* 1009 */       closeStatement(ps);
/* 1010 */       closeConnection();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.virement.VirementLocationDAO
 * JD-Core Version:    0.6.0
 */