/*     */ package com.cedar.cp.ejb.impl.model.ra;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.BudgetUserPK;
/*     */ import com.cedar.cp.dto.model.BudgetUserRefImpl;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import com.cedar.cp.dto.model.ModelRefImpl;
/*     */ import com.cedar.cp.dto.model.ra.AllResponsibilityAreasELO;
/*     */ import com.cedar.cp.dto.model.ra.ResponsibilityAreaCK;
/*     */ import com.cedar.cp.dto.model.ra.ResponsibilityAreaPK;
/*     */ import com.cedar.cp.dto.model.ra.ResponsibilityAreaRefImpl;
/*     */ import com.cedar.cp.dto.user.UserPK;
/*     */ import com.cedar.cp.dto.user.UserRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.ejb.impl.model.ModelEVO;
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
/*     */ public class ResponsibilityAreaDAO extends AbstractDAO
/*     */ {
/*  44 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID,RESPONSIBILITY_AREA.MODEL_ID,RESPONSIBILITY_AREA.STRUCTURE_ID,RESPONSIBILITY_AREA.STRUCTURE_ELEMENT_ID,RESPONSIBILITY_AREA.VIREMENT_AUTH_STATUS,RESPONSIBILITY_AREA.VERSION_NUM,RESPONSIBILITY_AREA.UPDATED_BY_USER_ID,RESPONSIBILITY_AREA.UPDATED_TIME,RESPONSIBILITY_AREA.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from RESPONSIBILITY_AREA where    RESPONSIBILITY_AREA_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into RESPONSIBILITY_AREA ( RESPONSIBILITY_AREA_ID,MODEL_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,VIREMENT_AUTH_STATUS,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update RESPONSIBILITY_AREA set MODEL_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,VIREMENT_AUTH_STATUS = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RESPONSIBILITY_AREA_ID = ? AND VERSION_NUM = ?";
/*     */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from RESPONSIBILITY_AREA where RESPONSIBILITY_AREA_ID = ?";
/* 394 */   protected static String SQL_ALL_RESPONSIBILITY_AREAS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID      ,USR.NAME      ,USR.USER_ID      ,USR.NAME      ,BUDGET_USER.MODEL_ID      ,BUDGET_USER.STRUCTURE_ELEMENT_ID      ,BUDGET_USER.USER_ID from RESPONSIBILITY_AREA    ,MODEL    ,USR    ,BUDGET_USER where 1=1   and RESPONSIBILITY_AREA.MODEL_ID = MODEL.MODEL_ID  and  USR.USER_ID = BUDGET_USER.USER_ID order by USR.USER_ID";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from RESPONSIBILITY_AREA where    RESPONSIBILITY_AREA_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from RESPONSIBILITY_AREA where 1=1 and RESPONSIBILITY_AREA.MODEL_ID = ? order by  RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID";
/*     */   protected static final String SQL_GET_ALL = " from RESPONSIBILITY_AREA where    MODEL_ID = ? ";
/*     */   protected ResponsibilityAreaEVO mDetails;
/*     */ 
/*     */   public ResponsibilityAreaDAO(Connection connection)
/*     */   {
/*  51 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ResponsibilityAreaDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ResponsibilityAreaDAO(DataSource ds)
/*     */   {
/*  67 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ResponsibilityAreaPK getPK()
/*     */   {
/*  75 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ResponsibilityAreaEVO details)
/*     */   {
/*  84 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private ResponsibilityAreaEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 104 */     int col = 1;
/* 105 */     ResponsibilityAreaEVO evo = new ResponsibilityAreaEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/* 114 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 115 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 116 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 117 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ResponsibilityAreaEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 122 */     int col = startCol_;
/* 123 */     stmt_.setInt(col++, evo_.getResponsibilityAreaId());
/* 124 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ResponsibilityAreaEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 129 */     int col = startCol_;
/* 130 */     stmt_.setInt(col++, evo_.getModelId());
/* 131 */     stmt_.setInt(col++, evo_.getStructureId());
/* 132 */     stmt_.setInt(col++, evo_.getStructureElementId());
/* 133 */     stmt_.setInt(col++, evo_.getVirementAuthStatus());
/* 134 */     stmt_.setInt(col++, evo_.getVersionNum());
/* 135 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 136 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 137 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 138 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ResponsibilityAreaPK pk)
/*     */     throws ValidationException
/*     */   {
/* 154 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 156 */     PreparedStatement stmt = null;
/* 157 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 161 */       stmt = getConnection().prepareStatement("select RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID,RESPONSIBILITY_AREA.MODEL_ID,RESPONSIBILITY_AREA.STRUCTURE_ID,RESPONSIBILITY_AREA.STRUCTURE_ELEMENT_ID,RESPONSIBILITY_AREA.VIREMENT_AUTH_STATUS,RESPONSIBILITY_AREA.VERSION_NUM,RESPONSIBILITY_AREA.UPDATED_BY_USER_ID,RESPONSIBILITY_AREA.UPDATED_TIME,RESPONSIBILITY_AREA.CREATED_TIME from RESPONSIBILITY_AREA where    RESPONSIBILITY_AREA_ID = ? ");
/*     */ 
/* 164 */       int col = 1;
/* 165 */       stmt.setInt(col++, pk.getResponsibilityAreaId());
/*     */ 
/* 167 */       resultSet = stmt.executeQuery();
/*     */ 
/* 169 */       if (!resultSet.next()) {
/* 170 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 173 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 174 */       if (this.mDetails.isModified())
/* 175 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 179 */       throw handleSQLException(pk, "select RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID,RESPONSIBILITY_AREA.MODEL_ID,RESPONSIBILITY_AREA.STRUCTURE_ID,RESPONSIBILITY_AREA.STRUCTURE_ELEMENT_ID,RESPONSIBILITY_AREA.VIREMENT_AUTH_STATUS,RESPONSIBILITY_AREA.VERSION_NUM,RESPONSIBILITY_AREA.UPDATED_BY_USER_ID,RESPONSIBILITY_AREA.UPDATED_TIME,RESPONSIBILITY_AREA.CREATED_TIME from RESPONSIBILITY_AREA where    RESPONSIBILITY_AREA_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 183 */       closeResultSet(resultSet);
/* 184 */       closeStatement(stmt);
/* 185 */       closeConnection();
/*     */ 
/* 187 */       if (timer != null)
/* 188 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 223 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 224 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 229 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 230 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 231 */       stmt = getConnection().prepareStatement("insert into RESPONSIBILITY_AREA ( RESPONSIBILITY_AREA_ID,MODEL_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,VIREMENT_AUTH_STATUS,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*     */ 
/* 234 */       int col = 1;
/* 235 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 236 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 239 */       int resultCount = stmt.executeUpdate();
/* 240 */       if (resultCount != 1)
/*     */       {
/* 242 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 245 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 249 */       throw handleSQLException(this.mDetails.getPK(), "insert into RESPONSIBILITY_AREA ( RESPONSIBILITY_AREA_ID,MODEL_ID,STRUCTURE_ID,STRUCTURE_ELEMENT_ID,VIREMENT_AUTH_STATUS,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 253 */       closeStatement(stmt);
/* 254 */       closeConnection();
/*     */ 
/* 256 */       if (timer != null)
/* 257 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 285 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 289 */     PreparedStatement stmt = null;
/*     */ 
/* 291 */     boolean mainChanged = this.mDetails.isModified();
/* 292 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 295 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 298 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*     */ 
/* 301 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 302 */         stmt = getConnection().prepareStatement("update RESPONSIBILITY_AREA set MODEL_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,VIREMENT_AUTH_STATUS = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RESPONSIBILITY_AREA_ID = ? AND VERSION_NUM = ?");
/*     */ 
/* 305 */         int col = 1;
/* 306 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 307 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 309 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*     */ 
/* 312 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 314 */         if (resultCount == 0) {
/* 315 */           checkVersionNum();
/*     */         }
/* 317 */         if (resultCount != 1) {
/* 318 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 321 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 330 */       throw handleSQLException(getPK(), "update RESPONSIBILITY_AREA set MODEL_ID = ?,STRUCTURE_ID = ?,STRUCTURE_ELEMENT_ID = ?,VIREMENT_AUTH_STATUS = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    RESPONSIBILITY_AREA_ID = ? AND VERSION_NUM = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 334 */       closeStatement(stmt);
/* 335 */       closeConnection();
/*     */ 
/* 337 */       if ((timer != null) && (
/* 338 */         (mainChanged) || (dependantChanged)))
/* 339 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkVersionNum()
/*     */     throws VersionValidationException
/*     */   {
/* 351 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 352 */     PreparedStatement stmt = null;
/* 353 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 357 */       stmt = getConnection().prepareStatement("select VERSION_NUM from RESPONSIBILITY_AREA where RESPONSIBILITY_AREA_ID = ?");
/*     */ 
/* 360 */       int col = 1;
/* 361 */       stmt.setInt(col++, this.mDetails.getResponsibilityAreaId());
/*     */ 
/* 364 */       resultSet = stmt.executeQuery();
/*     */ 
/* 366 */       if (!resultSet.next()) {
/* 367 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*     */       }
/*     */ 
/* 370 */       col = 1;
/* 371 */       int dbVersionNumber = resultSet.getInt(col++);
/* 372 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/* 373 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 379 */       throw handleSQLException(getPK(), "select VERSION_NUM from RESPONSIBILITY_AREA where RESPONSIBILITY_AREA_ID = ?", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 383 */       closeStatement(stmt);
/* 384 */       closeResultSet(resultSet);
/*     */ 
/* 386 */       if (timer != null)
/* 387 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllResponsibilityAreasELO getAllResponsibilityAreas()
/*     */   {
/* 431 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 432 */     PreparedStatement stmt = null;
/* 433 */     ResultSet resultSet = null;
/* 434 */     AllResponsibilityAreasELO results = new AllResponsibilityAreasELO();
/*     */     try
/*     */     {
/* 437 */       stmt = getConnection().prepareStatement(SQL_ALL_RESPONSIBILITY_AREAS);
/* 438 */       int col = 1;
/* 439 */       resultSet = stmt.executeQuery();
/* 440 */       while (resultSet.next())
/*     */       {
/* 442 */         col = 2;
/*     */ 
/* 445 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 448 */         String textModel = resultSet.getString(col++);
/*     */ 
/* 451 */         ResponsibilityAreaPK pkResponsibilityArea = new ResponsibilityAreaPK(resultSet.getInt(col++));
/*     */ 
/* 454 */         String textResponsibilityArea = resultSet.getString(col++);
/*     */ 
/* 457 */         UserPK pkUser = new UserPK(resultSet.getInt(col++));
/*     */ 
/* 460 */         String textUser = resultSet.getString(col++);
/*     */ 
/* 462 */         BudgetUserPK pkBudgetUser = new BudgetUserPK(resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 467 */         String textBudgetUser = "";
/*     */ 
/* 471 */         ResponsibilityAreaCK ckResponsibilityArea = new ResponsibilityAreaCK(pkModel, pkResponsibilityArea);
/*     */ 
/* 477 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*     */ 
/* 483 */         ResponsibilityAreaRefImpl erResponsibilityArea = new ResponsibilityAreaRefImpl(ckResponsibilityArea, textResponsibilityArea);
/*     */ 
/* 489 */         UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
/*     */ 
/* 495 */         BudgetUserRefImpl erBudgetUser = new BudgetUserRefImpl(pkBudgetUser, textBudgetUser);
/*     */ 
/* 502 */         results.add(erResponsibilityArea, erModel, erUser, erBudgetUser);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 512 */       throw handleSQLException(SQL_ALL_RESPONSIBILITY_AREAS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 516 */       closeResultSet(resultSet);
/* 517 */       closeStatement(stmt);
/* 518 */       closeConnection();
/*     */     }
/*     */ 
/* 521 */     if (timer != null) {
/* 522 */       timer.logDebug("getAllResponsibilityAreas", " items=" + results.size());
/*     */     }
/*     */ 
/* 526 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 543 */     if (items == null) {
/* 544 */       return false;
/*     */     }
/* 546 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 547 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 549 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 554 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 555 */       while (iter2.hasNext())
/*     */       {
/* 557 */         this.mDetails = ((ResponsibilityAreaEVO)iter2.next());
/*     */ 
/* 560 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 562 */         somethingChanged = true;
/*     */ 
/* 565 */         if (deleteStmt == null) {
/* 566 */           deleteStmt = getConnection().prepareStatement("delete from RESPONSIBILITY_AREA where    RESPONSIBILITY_AREA_ID = ? ");
/*     */         }
/*     */ 
/* 569 */         int col = 1;
/* 570 */         deleteStmt.setInt(col++, this.mDetails.getResponsibilityAreaId());
/*     */ 
/* 572 */         if (this._log.isDebugEnabled()) {
/* 573 */           this._log.debug("update", "ResponsibilityArea deleting ResponsibilityAreaId=" + this.mDetails.getResponsibilityAreaId());
/*     */         }
/*     */ 
/* 578 */         deleteStmt.addBatch();
/*     */ 
/* 581 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 586 */       if (deleteStmt != null)
/*     */       {
/* 588 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 590 */         deleteStmt.executeBatch();
/*     */ 
/* 592 */         if (timer2 != null) {
/* 593 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 597 */       Iterator iter1 = items.values().iterator();
/* 598 */       while (iter1.hasNext())
/*     */       {
/* 600 */         this.mDetails = ((ResponsibilityAreaEVO)iter1.next());
/*     */ 
/* 602 */         if (this.mDetails.insertPending())
/*     */         {
/* 604 */           somethingChanged = true;
/* 605 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 608 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 610 */         somethingChanged = true;
/* 611 */         doStore();
/*     */       }
/*     */ 
/* 622 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 626 */       throw handleSQLException("delete from RESPONSIBILITY_AREA where    RESPONSIBILITY_AREA_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 630 */       if (deleteStmt != null)
/*     */       {
/* 632 */         closeStatement(deleteStmt);
/* 633 */         closeConnection();
/*     */       }
/*     */ 
/* 636 */       this.mDetails = null;
/*     */ 
/* 638 */       if ((somethingChanged) && 
/* 639 */         (timer != null))
/* 640 */         timer.logDebug("update", "collection"); 
/* 640 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants)
/*     */   {
/* 659 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 661 */     PreparedStatement stmt = null;
/* 662 */     ResultSet resultSet = null;
/*     */ 
/* 664 */     int itemCount = 0;
/*     */ 
/* 666 */     Collection theseItems = new ArrayList();
/* 667 */     owningEVO.setResponsibilityAreas(theseItems);
/* 668 */     owningEVO.setResponsibilityAreasAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 672 */       stmt = getConnection().prepareStatement("select RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID,RESPONSIBILITY_AREA.MODEL_ID,RESPONSIBILITY_AREA.STRUCTURE_ID,RESPONSIBILITY_AREA.STRUCTURE_ELEMENT_ID,RESPONSIBILITY_AREA.VIREMENT_AUTH_STATUS,RESPONSIBILITY_AREA.VERSION_NUM,RESPONSIBILITY_AREA.UPDATED_BY_USER_ID,RESPONSIBILITY_AREA.UPDATED_TIME,RESPONSIBILITY_AREA.CREATED_TIME from RESPONSIBILITY_AREA where 1=1 and RESPONSIBILITY_AREA.MODEL_ID = ? order by  RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID");
/*     */ 
/* 674 */       int col = 1;
/* 675 */       stmt.setInt(col++, entityPK.getModelId());
/*     */ 
/* 677 */       resultSet = stmt.executeQuery();
/*     */ 
/* 680 */       while (resultSet.next())
/*     */       {
/* 682 */         itemCount++;
/* 683 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 685 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 688 */       if (timer != null) {
/* 689 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 694 */       throw handleSQLException("select RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID,RESPONSIBILITY_AREA.MODEL_ID,RESPONSIBILITY_AREA.STRUCTURE_ID,RESPONSIBILITY_AREA.STRUCTURE_ELEMENT_ID,RESPONSIBILITY_AREA.VIREMENT_AUTH_STATUS,RESPONSIBILITY_AREA.VERSION_NUM,RESPONSIBILITY_AREA.UPDATED_BY_USER_ID,RESPONSIBILITY_AREA.UPDATED_TIME,RESPONSIBILITY_AREA.CREATED_TIME from RESPONSIBILITY_AREA where 1=1 and RESPONSIBILITY_AREA.MODEL_ID = ? order by  RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 698 */       closeResultSet(resultSet);
/* 699 */       closeStatement(stmt);
/* 700 */       closeConnection();
/*     */ 
/* 702 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectModelId, String dependants, Collection currentList)
/*     */   {
/* 727 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 728 */     PreparedStatement stmt = null;
/* 729 */     ResultSet resultSet = null;
/*     */ 
/* 731 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 735 */       stmt = getConnection().prepareStatement("select RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID,RESPONSIBILITY_AREA.MODEL_ID,RESPONSIBILITY_AREA.STRUCTURE_ID,RESPONSIBILITY_AREA.STRUCTURE_ELEMENT_ID,RESPONSIBILITY_AREA.VIREMENT_AUTH_STATUS,RESPONSIBILITY_AREA.VERSION_NUM,RESPONSIBILITY_AREA.UPDATED_BY_USER_ID,RESPONSIBILITY_AREA.UPDATED_TIME,RESPONSIBILITY_AREA.CREATED_TIME from RESPONSIBILITY_AREA where    MODEL_ID = ? ");
/*     */ 
/* 737 */       int col = 1;
/* 738 */       stmt.setInt(col++, selectModelId);
/*     */ 
/* 740 */       resultSet = stmt.executeQuery();
/*     */ 
/* 742 */       while (resultSet.next())
/*     */       {
/* 744 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 747 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 750 */       if (currentList != null)
/*     */       {
/* 753 */         ListIterator iter = items.listIterator();
/* 754 */         ResponsibilityAreaEVO currentEVO = null;
/* 755 */         ResponsibilityAreaEVO newEVO = null;
/* 756 */         while (iter.hasNext())
/*     */         {
/* 758 */           newEVO = (ResponsibilityAreaEVO)iter.next();
/* 759 */           Iterator iter2 = currentList.iterator();
/* 760 */           while (iter2.hasNext())
/*     */           {
/* 762 */             currentEVO = (ResponsibilityAreaEVO)iter2.next();
/* 763 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 765 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 771 */         Iterator iter2 = currentList.iterator();
/* 772 */         while (iter2.hasNext())
/*     */         {
/* 774 */           currentEVO = (ResponsibilityAreaEVO)iter2.next();
/* 775 */           if (currentEVO.insertPending()) {
/* 776 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 780 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 784 */       throw handleSQLException("select RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID,RESPONSIBILITY_AREA.MODEL_ID,RESPONSIBILITY_AREA.STRUCTURE_ID,RESPONSIBILITY_AREA.STRUCTURE_ELEMENT_ID,RESPONSIBILITY_AREA.VIREMENT_AUTH_STATUS,RESPONSIBILITY_AREA.VERSION_NUM,RESPONSIBILITY_AREA.UPDATED_BY_USER_ID,RESPONSIBILITY_AREA.UPDATED_TIME,RESPONSIBILITY_AREA.CREATED_TIME from RESPONSIBILITY_AREA where    MODEL_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 788 */       closeResultSet(resultSet);
/* 789 */       closeStatement(stmt);
/* 790 */       closeConnection();
/*     */ 
/* 792 */       if (timer != null) {
/* 793 */         timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 798 */     return items;
/*     */   }
/*     */ 
/*     */   public ResponsibilityAreaEVO getDetails(ModelCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 812 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 815 */     if (this.mDetails == null) {
/* 816 */       doLoad(((ResponsibilityAreaCK)paramCK).getResponsibilityAreaPK());
/*     */     }
/* 818 */     else if (!this.mDetails.getPK().equals(((ResponsibilityAreaCK)paramCK).getResponsibilityAreaPK())) {
/* 819 */       doLoad(((ResponsibilityAreaCK)paramCK).getResponsibilityAreaPK());
/*     */     }
/*     */ 
/* 822 */     ResponsibilityAreaEVO details = new ResponsibilityAreaEVO();
/* 823 */     details = this.mDetails.deepClone();
/*     */ 
/* 825 */     if (timer != null) {
/* 826 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 828 */     return details;
/*     */   }
/*     */ 
/*     */   public ResponsibilityAreaEVO getDetails(ModelCK paramCK, ResponsibilityAreaEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 834 */     ResponsibilityAreaEVO savedEVO = this.mDetails;
/* 835 */     this.mDetails = paramEVO;
/* 836 */     ResponsibilityAreaEVO newEVO = getDetails(paramCK, dependants);
/* 837 */     this.mDetails = savedEVO;
/* 838 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public ResponsibilityAreaEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 844 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 848 */     ResponsibilityAreaEVO details = this.mDetails.deepClone();
/*     */ 
/* 850 */     if (timer != null) {
/* 851 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 853 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 858 */     return "ResponsibilityArea";
/*     */   }
/*     */ 
/*     */   public ResponsibilityAreaRefImpl getRef(ResponsibilityAreaPK paramResponsibilityAreaPK)
/*     */   {
/* 863 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 864 */     PreparedStatement stmt = null;
/* 865 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 868 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID from RESPONSIBILITY_AREA,MODEL where 1=1 and RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID = ? and RESPONSIBILITY_AREA.MODEL_ID = MODEL.MODEL_ID");
/* 869 */       int col = 1;
/* 870 */       stmt.setInt(col++, paramResponsibilityAreaPK.getResponsibilityAreaId());
/*     */ 
/* 872 */       resultSet = stmt.executeQuery();
/*     */ 
/* 874 */       if (!resultSet.next()) {
/* 875 */         throw new RuntimeException(getEntityName() + " getRef " + paramResponsibilityAreaPK + " not found");
/*     */       }
/* 877 */       col = 2;
/* 878 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
/*     */ 
/* 882 */       String textResponsibilityArea = "";
/* 883 */       ResponsibilityAreaCK ckResponsibilityArea = new ResponsibilityAreaCK(newModelPK, paramResponsibilityAreaPK);
/*     */ 
/* 888 */       ResponsibilityAreaRefImpl localResponsibilityAreaRefImpl = new ResponsibilityAreaRefImpl(ckResponsibilityArea, textResponsibilityArea);
/*     */       return localResponsibilityAreaRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 893 */       throw handleSQLException(paramResponsibilityAreaPK, "select 0,MODEL.MODEL_ID from RESPONSIBILITY_AREA,MODEL where 1=1 and RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID = ? and RESPONSIBILITY_AREA.MODEL_ID = MODEL.MODEL_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 897 */       closeResultSet(resultSet);
/* 898 */       closeStatement(stmt);
/* 899 */       closeConnection();
/*     */ 
/* 901 */       if (timer != null)
/* 902 */         timer.logDebug("getRef", paramResponsibilityAreaPK); 
/* 902 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.ra.ResponsibilityAreaDAO
 * JD-Core Version:    0.6.0
 */