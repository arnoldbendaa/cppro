/*     */ package com.cedar.cp.ejb.impl.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.model.globalmapping2.MappedDimensionElementCK;
/*     */ import com.cedar.cp.dto.model.globalmapping2.MappedDimensionElementPK;
/*     */ import com.cedar.cp.dto.model.globalmapping2.MappedDimensionElementRefImpl;
/*     */ import com.cedar.cp.dto.model.globalmapping2.MappedDimensionPK;
/*     */ import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2CK;
/*     */ import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
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
/*     */ public class MappedDimensionElementDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ELEMENT_ID,MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID,MAPPED_DIMENSION_ELEMENT.MAPPING_TYPE,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_1,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_2,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_3,MAPPED_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,MAPPED_DIMENSION_ELEMENT.UPDATED_TIME,MAPPED_DIMENSION_ELEMENT.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from MAPPED_DIMENSION_ELEMENT where    MAPPED_DIMENSION_ELEMENT_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into MAPPED_DIMENSION_ELEMENT ( MAPPED_DIMENSION_ELEMENT_ID,MAPPED_DIMENSION_ID,MAPPING_TYPE,ELEMENT_VIS_ID_1,ELEMENT_VIS_ID_2,ELEMENT_VIS_ID_3,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update MAPPED_DIMENSION_ELEMENT set MAPPED_DIMENSION_ID = ?,MAPPING_TYPE = ?,ELEMENT_VIS_ID_1 = ?,ELEMENT_VIS_ID_2 = ?,ELEMENT_VIS_ID_3 = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_DIMENSION_ELEMENT_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from MAPPED_DIMENSION_ELEMENT where    MAPPED_DIMENSION_ELEMENT_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from MAPPED_DIMENSION_ELEMENT,MAPPED_DIMENSION where 1=1 and MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID = MAPPED_DIMENSION.MAPPED_DIMENSION_ID and MAPPED_DIMENSION.MAPPED_MODEL_ID = ? order by  MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID ,MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ELEMENT_ID";
/*     */   protected static final String SQL_GET_ALL = " from MAPPED_DIMENSION_ELEMENT where    MAPPED_DIMENSION_ID = ? ";
/*     */   protected MappedDimensionElementEVO mDetails;
/*     */ 
/*     */   public MappedDimensionElementDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public MappedDimensionElementDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public MappedDimensionElementDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected MappedDimensionElementPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(MappedDimensionElementEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private MappedDimensionElementEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  92 */     int col = 1;
/*  93 */     MappedDimensionElementEVO evo = new MappedDimensionElementEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++));
/*     */ 
/* 102 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 103 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 104 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 105 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(MappedDimensionElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 110 */     int col = startCol_;
/* 111 */     stmt_.setInt(col++, evo_.getMappedDimensionElementId());
/* 112 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(MappedDimensionElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 117 */     int col = startCol_;
/* 118 */     stmt_.setInt(col++, evo_.getMappedDimensionId());
/* 119 */     stmt_.setInt(col++, evo_.getMappingType());
/* 120 */     stmt_.setString(col++, evo_.getElementVisId1());
/* 121 */     stmt_.setString(col++, evo_.getElementVisId2());
/* 122 */     stmt_.setString(col++, evo_.getElementVisId3());
			  stmt_.setString(col++, evo_.getCompanies());
/* 123 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 124 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 125 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 126 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(MappedDimensionElementPK pk)
/*     */     throws ValidationException
/*     */   {
/* 142 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 144 */     PreparedStatement stmt = null;
/* 145 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 149 */       stmt = getConnection().prepareStatement("select MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ELEMENT_ID,MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID,MAPPED_DIMENSION_ELEMENT.MAPPING_TYPE,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_1,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_2, MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_3, MAPPED_DIMENSION_ELEMENT.COMPANIES, MAPPED_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,MAPPED_DIMENSION_ELEMENT.UPDATED_TIME,MAPPED_DIMENSION_ELEMENT.CREATED_TIME from MAPPED_DIMENSION_ELEMENT where    MAPPED_DIMENSION_ELEMENT_ID = ? ");
/*     */ 
/* 152 */       int col = 1;
/* 153 */       stmt.setInt(col++, pk.getMappedDimensionElementId());
/*     */ 
/* 155 */       resultSet = stmt.executeQuery();
/*     */ 
/* 157 */       if (!resultSet.next()) {
/* 158 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 161 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 162 */       if (this.mDetails.isModified())
/* 163 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 167 */       throw handleSQLException(pk, "select MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ELEMENT_ID,MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID,MAPPED_DIMENSION_ELEMENT.MAPPING_TYPE,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_1,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_2,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_3, MAPPED_DIMENSION_ELEMENT.COMPANIES, MAPPED_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,MAPPED_DIMENSION_ELEMENT.UPDATED_TIME,MAPPED_DIMENSION_ELEMENT.CREATED_TIME from MAPPED_DIMENSION_ELEMENT where    MAPPED_DIMENSION_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 171 */       closeResultSet(resultSet);
/* 172 */       closeStatement(stmt);
/* 173 */       closeConnection();
/*     */ 
/* 175 */       if (timer != null)
/* 176 */         timer.logDebug("doLoad", pk);
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
/* 219 */       stmt = getConnection().prepareStatement("insert into MAPPED_DIMENSION_ELEMENT ( MAPPED_DIMENSION_ELEMENT_ID, MAPPED_DIMENSION_ID, MAPPING_TYPE, ELEMENT_VIS_ID_1, ELEMENT_VIS_ID_2, ELEMENT_VIS_ID_3, COMPANIES, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,? )");
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
/* 237 */       throw handleSQLException(this.mDetails.getPK(), "insert into MAPPED_DIMENSION_ELEMENT ( MAPPED_DIMENSION_ELEMENT_ID,MAPPED_DIMENSION_ID,MAPPING_TYPE,ELEMENT_VIS_ID_1,ELEMENT_VIS_ID_2,ELEMENT_VIS_ID_3,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
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
/* 272 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 276 */     PreparedStatement stmt = null;
/*     */ 
/* 278 */     boolean mainChanged = this.mDetails.isModified();
/* 279 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 282 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 285 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 286 */         stmt = getConnection().prepareStatement("update MAPPED_DIMENSION_ELEMENT set MAPPED_DIMENSION_ID = ?, MAPPING_TYPE = ?, ELEMENT_VIS_ID_1 = ?, ELEMENT_VIS_ID_2 = ?, ELEMENT_VIS_ID_3 = ?, COMPANIES = ?, UPDATED_BY_USER_ID = ?, UPDATED_TIME = ?, CREATED_TIME = ? where    MAPPED_DIMENSION_ELEMENT_ID = ? ");
/*     */ 
/* 289 */         int col = 1;
/* 290 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 291 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 294 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 297 */         if (resultCount != 1) {
/* 298 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 301 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 310 */       throw handleSQLException(getPK(), "update MAPPED_DIMENSION_ELEMENT set MAPPED_DIMENSION_ID = ?,MAPPING_TYPE = ?,ELEMENT_VIS_ID_1 = ?,ELEMENT_VIS_ID_2 = ?,ELEMENT_VIS_ID_3 = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MAPPED_DIMENSION_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 314 */       closeStatement(stmt);
/* 315 */       closeConnection();
/*     */ 
/* 317 */       if ((timer != null) && (
/* 318 */         (mainChanged) || (dependantChanged)))
/* 319 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 338 */     if (items == null) {
/* 339 */       return false;
/*     */     }
/* 341 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 342 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 344 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 349 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 350 */       while (iter2.hasNext())
/*     */       {
/* 352 */         this.mDetails = ((MappedDimensionElementEVO)iter2.next());
/*     */ 
/* 355 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 357 */         somethingChanged = true;
/*     */ 
/* 360 */         if (deleteStmt == null) {
/* 361 */           deleteStmt = getConnection().prepareStatement("delete from MAPPED_DIMENSION_ELEMENT where    MAPPED_DIMENSION_ELEMENT_ID = ? ");
/*     */         }
/*     */ 
/* 364 */         int col = 1;
/* 365 */         deleteStmt.setInt(col++, this.mDetails.getMappedDimensionElementId());
/*     */ 
/* 367 */         if (this._log.isDebugEnabled()) {
/* 368 */           this._log.debug("update", "MappedDimensionElement deleting MappedDimensionElementId=" + this.mDetails.getMappedDimensionElementId());
/*     */         }
/*     */ 
/* 373 */         deleteStmt.addBatch();
/*     */ 
/* 376 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 381 */       if (deleteStmt != null)
/*     */       {
/* 383 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 385 */         deleteStmt.executeBatch();
/*     */ 
/* 387 */         if (timer2 != null) {
/* 388 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 392 */       Iterator iter1 = items.values().iterator();
/* 393 */       while (iter1.hasNext())
/*     */       {
/* 395 */         this.mDetails = ((MappedDimensionElementEVO)iter1.next());
/*     */ 
/* 397 */         if (this.mDetails.insertPending())
/*     */         {
/* 399 */           somethingChanged = true;
/* 400 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 403 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 405 */         somethingChanged = true;
/* 406 */         doStore();
/*     */       }
/*     */ 
/* 417 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 421 */       throw handleSQLException("delete from MAPPED_DIMENSION_ELEMENT where    MAPPED_DIMENSION_ELEMENT_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 425 */       if (deleteStmt != null)
/*     */       {
/* 427 */         closeStatement(deleteStmt);
/* 428 */         closeConnection();
/*     */       }
/*     */ 
/* 431 */       this.mDetails = null;
/*     */ 
/* 433 */       if ((somethingChanged) && 
/* 434 */         (timer != null))
/* 435 */         timer.logDebug("update", "collection"); 
/* 435 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(GlobalMappedModel2PK entityPK, Collection owners, String dependants)
/*     */   {
/* 458 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 460 */     PreparedStatement stmt = null;
/* 461 */     ResultSet resultSet = null;
/*     */ 
/* 463 */     int itemCount = 0;
/*     */ 
/* 465 */     MappedDimensionEVO owningEVO = null;
/* 466 */     Iterator ownersIter = owners.iterator();
/* 467 */     while (ownersIter.hasNext())
/*     */     {
/* 469 */       owningEVO = (MappedDimensionEVO)ownersIter.next();
/* 470 */       owningEVO.setMappedDimensionElementsAllItemsLoaded(true);
/*     */     }
/* 472 */     ownersIter = owners.iterator();
/* 473 */     owningEVO = (MappedDimensionEVO)ownersIter.next();
/* 474 */     Collection theseItems = null;
/*     */     try
/*     */     {
/* 478 */       stmt = getConnection().prepareStatement("select MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ELEMENT_ID,MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID,MAPPED_DIMENSION_ELEMENT.MAPPING_TYPE,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_1,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_2,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_3, MAPPED_DIMENSION_ELEMENT.COMPANIES, MAPPED_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,MAPPED_DIMENSION_ELEMENT.UPDATED_TIME,MAPPED_DIMENSION_ELEMENT.CREATED_TIME from MAPPED_DIMENSION_ELEMENT,MAPPED_DIMENSION where 1=1 and MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID = MAPPED_DIMENSION.MAPPED_DIMENSION_ID and MAPPED_DIMENSION.MAPPED_MODEL_ID = ? order by  MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID ,MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ELEMENT_ID");
/*     */ 
/* 480 */       int col = 1;
/* 481 */       stmt.setInt(col++, entityPK.getMappedModelId());
/*     */ 
/* 483 */       resultSet = stmt.executeQuery();
/*     */ 
/* 486 */       while (resultSet.next())
/*     */       {
/* 488 */         itemCount++;
/* 489 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 494 */         while (this.mDetails.getMappedDimensionId() != owningEVO.getMappedDimensionId())
/*     */         {
/* 498 */           if (!ownersIter.hasNext())
/*     */           {
/* 500 */             this._log.debug("bulkGetAll", "can't find owning [MappedDimensionId=" + this.mDetails.getMappedDimensionId() + "] for " + this.mDetails.getPK());
/*     */ 
/* 504 */             this._log.debug("bulkGetAll", "loaded owners are:");
/* 505 */             ownersIter = owners.iterator();
/* 506 */             while (ownersIter.hasNext())
/*     */             {
/* 508 */               owningEVO = (MappedDimensionEVO)ownersIter.next();
/* 509 */               this._log.debug("bulkGetAll", "    " + owningEVO.toString());
/*     */             }
/* 511 */             throw new IllegalStateException("can't find owner");
/*     */           }
/* 513 */           owningEVO = (MappedDimensionEVO)ownersIter.next();
/*     */         }
/* 515 */         if (owningEVO.getMappedDimensionElements() == null)
/*     */         {
/* 517 */           theseItems = new ArrayList();
/* 518 */           owningEVO.setMappedDimensionElements(theseItems);
/* 519 */           owningEVO.setMappedDimensionElementsAllItemsLoaded(true);
/*     */         }
/* 521 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 524 */       if (timer != null) {
/* 525 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 530 */       throw handleSQLException("select MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ELEMENT_ID,MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID,MAPPED_DIMENSION_ELEMENT.MAPPING_TYPE,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_1,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_2,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_3, MAPPED_DIMENSION_ELEMENT.COMPANIES, MAPPED_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,MAPPED_DIMENSION_ELEMENT.UPDATED_TIME,MAPPED_DIMENSION_ELEMENT.CREATED_TIME from MAPPED_DIMENSION_ELEMENT,MAPPED_DIMENSION where 1=1 and MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID = MAPPED_DIMENSION.MAPPED_DIMENSION_ID and MAPPED_DIMENSION.MAPPED_MODEL_ID = ? order by  MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID ,MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ELEMENT_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 534 */       closeResultSet(resultSet);
/* 535 */       closeStatement(stmt);
/* 536 */       closeConnection();
/*     */ 
/* 538 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectMappedDimensionId, String dependants, Collection currentList)
/*     */   {
/* 563 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 564 */     PreparedStatement stmt = null;
/* 565 */     ResultSet resultSet = null;
/*     */ 
/* 567 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 571 */       stmt = getConnection().prepareStatement("select MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ELEMENT_ID,MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID,MAPPED_DIMENSION_ELEMENT.MAPPING_TYPE,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_1,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_2,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_3, MAPPED_DIMENSION_ELEMENT.COMPANIES, MAPPED_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,MAPPED_DIMENSION_ELEMENT.UPDATED_TIME,MAPPED_DIMENSION_ELEMENT.CREATED_TIME from MAPPED_DIMENSION_ELEMENT where    MAPPED_DIMENSION_ID = ? ");
/*     */ 
/* 573 */       int col = 1;
/* 574 */       stmt.setInt(col++, selectMappedDimensionId);
/*     */ 
/* 576 */       resultSet = stmt.executeQuery();
/*     */ 
/* 578 */       while (resultSet.next())
/*     */       {
/* 580 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 583 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 586 */       if (currentList != null)
/*     */       {
/* 589 */         ListIterator iter = items.listIterator();
/* 590 */         MappedDimensionElementEVO currentEVO = null;
/* 591 */         MappedDimensionElementEVO newEVO = null;
/* 592 */         while (iter.hasNext())
/*     */         {
/* 594 */           newEVO = (MappedDimensionElementEVO)iter.next();
/* 595 */           Iterator iter2 = currentList.iterator();
/* 596 */           while (iter2.hasNext())
/*     */           {
/* 598 */             currentEVO = (MappedDimensionElementEVO)iter2.next();
/* 599 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 601 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 607 */         Iterator iter2 = currentList.iterator();
/* 608 */         while (iter2.hasNext())
/*     */         {
/* 610 */           currentEVO = (MappedDimensionElementEVO)iter2.next();
/* 611 */           if (currentEVO.insertPending()) {
/* 612 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 616 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 620 */       throw handleSQLException("select MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ELEMENT_ID,MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID,MAPPED_DIMENSION_ELEMENT.MAPPING_TYPE,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_1,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_2,MAPPED_DIMENSION_ELEMENT.ELEMENT_VIS_ID_3, MAPPED_DIMENSION_ELEMENT.COMPANIES, MAPPED_DIMENSION_ELEMENT.UPDATED_BY_USER_ID,MAPPED_DIMENSION_ELEMENT.UPDATED_TIME,MAPPED_DIMENSION_ELEMENT.CREATED_TIME from MAPPED_DIMENSION_ELEMENT where    MAPPED_DIMENSION_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 624 */       closeResultSet(resultSet);
/* 625 */       closeStatement(stmt);
/* 626 */       closeConnection();
/*     */ 
/* 628 */       if (timer != null) {
/* 629 */         timer.logDebug("getAll", " MappedDimensionId=" + selectMappedDimensionId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 634 */     return items;
/*     */   }
/*     */ 
/*     */   public MappedDimensionElementEVO getDetails(GlobalMappedModel2CK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 648 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 651 */     if (this.mDetails == null) {
/* 652 */       doLoad(((MappedDimensionElementCK)paramCK).getMappedDimensionElementPK());
/*     */     }
/* 654 */     else if (!this.mDetails.getPK().equals(((MappedDimensionElementCK)paramCK).getMappedDimensionElementPK())) {
/* 655 */       doLoad(((MappedDimensionElementCK)paramCK).getMappedDimensionElementPK());
/*     */     }
/*     */ 
/* 658 */     MappedDimensionElementEVO details = new MappedDimensionElementEVO();
/* 659 */     details = this.mDetails.deepClone();
/*     */ 
/* 661 */     if (timer != null) {
/* 662 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 664 */     return details;
/*     */   }
/*     */ 
/*     */   public MappedDimensionElementEVO getDetails(GlobalMappedModel2CK paramCK, MappedDimensionElementEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 670 */     MappedDimensionElementEVO savedEVO = this.mDetails;
/* 671 */     this.mDetails = paramEVO;
/* 672 */     MappedDimensionElementEVO newEVO = getDetails(paramCK, dependants);
/* 673 */     this.mDetails = savedEVO;
/* 674 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public MappedDimensionElementEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 680 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 684 */     MappedDimensionElementEVO details = this.mDetails.deepClone();
/*     */ 
/* 686 */     if (timer != null) {
/* 687 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 689 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 694 */     return "MappedDimensionElement";
/*     */   }
/*     */ 
/*     */   public MappedDimensionElementRefImpl getRef(MappedDimensionElementPK paramMappedDimensionElementPK)
/*     */   {
/* 699 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 700 */     PreparedStatement stmt = null;
/* 701 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 704 */       stmt = getConnection().prepareStatement("select 0,MAPPED_MODEL.MAPPED_MODEL_ID,MAPPED_DIMENSION.MAPPED_DIMENSION_ID from MAPPED_DIMENSION_ELEMENT,MAPPED_MODEL,MAPPED_DIMENSION where 1=1 and MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ELEMENT_ID = ? and MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID = MAPPED_DIMENSION.MAPPED_DIMENSION_ID and MAPPED_DIMENSION.MAPPED_DIMENSION_ID = MAPPED_MODEL.MAPPED_DIMENSION_ID");
/* 705 */       int col = 1;
/* 706 */       stmt.setInt(col++, paramMappedDimensionElementPK.getMappedDimensionElementId());
/*     */ 
/* 708 */       resultSet = stmt.executeQuery();
/*     */ 
/* 710 */       if (!resultSet.next()) {
/* 711 */         throw new RuntimeException(getEntityName() + " getRef " + paramMappedDimensionElementPK + " not found");
/*     */       }
/* 713 */       col = 2;
/* 714 */       GlobalMappedModel2PK newMappedModelPK = new GlobalMappedModel2PK(resultSet.getInt(col++));
/*     */ 
/* 718 */       MappedDimensionPK newMappedDimensionPK = new MappedDimensionPK(resultSet.getInt(col++));
/*     */ 
/* 722 */       String textMappedDimensionElement = "";
/* 723 */       MappedDimensionElementCK ckMappedDimensionElement = new MappedDimensionElementCK(newMappedModelPK, newMappedDimensionPK, paramMappedDimensionElementPK);
/*     */ 
/* 729 */       MappedDimensionElementRefImpl localMappedDimensionElementRefImpl = new MappedDimensionElementRefImpl(ckMappedDimensionElement, textMappedDimensionElement);
/*     */       return localMappedDimensionElementRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 734 */       throw handleSQLException(paramMappedDimensionElementPK, "select 0,MAPPED_MODEL.MAPPED_MODEL_ID,MAPPED_DIMENSION.MAPPED_DIMENSION_ID from MAPPED_DIMENSION_ELEMENT,MAPPED_MODEL,MAPPED_DIMENSION where 1=1 and MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ELEMENT_ID = ? and MAPPED_DIMENSION_ELEMENT.MAPPED_DIMENSION_ID = MAPPED_DIMENSION.MAPPED_DIMENSION_ID and MAPPED_DIMENSION.MAPPED_DIMENSION_ID = MAPPED_MODEL.MAPPED_DIMENSION_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 738 */       closeResultSet(resultSet);
/* 739 */       closeStatement(stmt);
/* 740 */       closeConnection();
/*     */ 
/* 742 */       if (timer != null)
/* 743 */         timer.logDebug("getRef", paramMappedDimensionElementPK); 
/* 743 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.mapping.MappedDimensionElementDAO
 * JD-Core Version:    0.6.0
 */