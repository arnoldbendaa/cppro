/*     */ package com.cedar.cp.ejb.impl.datatype;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.datatype.DataTypeCK;
/*     */ import com.cedar.cp.dto.datatype.DataTypePK;
/*     */ import com.cedar.cp.dto.datatype.DataTypeRelCK;
/*     */ import com.cedar.cp.dto.datatype.DataTypeRelPK;
/*     */ import com.cedar.cp.dto.datatype.DataTypeRelRefImpl;
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
/*     */ public class DataTypeRelDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select DATA_TYPE_REL.DATA_TYPE_ID,DATA_TYPE_REL.REF_DATA_TYPE_ID,DATA_TYPE_REL.UPDATED_BY_USER_ID,DATA_TYPE_REL.UPDATED_TIME,DATA_TYPE_REL.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from DATA_TYPE_REL where    DATA_TYPE_ID = ? AND REF_DATA_TYPE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into DATA_TYPE_REL ( DATA_TYPE_ID,REF_DATA_TYPE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update DATA_TYPE_REL set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DATA_TYPE_ID = ? AND REF_DATA_TYPE_ID = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from DATA_TYPE_REL where    DATA_TYPE_ID = ? AND REF_DATA_TYPE_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from DATA_TYPE_REL where 1=1 and DATA_TYPE_REL.DATA_TYPE_ID = ? order by  DATA_TYPE_REL.DATA_TYPE_ID ,DATA_TYPE_REL.REF_DATA_TYPE_ID";
/*     */   protected static final String SQL_GET_ALL = " from DATA_TYPE_REL where    DATA_TYPE_ID = ? ";
/*     */   protected DataTypeRelEVO mDetails;
/*     */ 
/*     */   public DataTypeRelDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public DataTypeRelDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DataTypeRelDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected DataTypeRelPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(DataTypeRelEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private DataTypeRelEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  88 */     int col = 1;
/*  89 */     DataTypeRelEVO evo = new DataTypeRelEVO(resultSet_.getShort(col++), resultSet_.getShort(col++));
/*     */ 
/*  94 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  95 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  96 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  97 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(DataTypeRelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 102 */     int col = startCol_;
/* 103 */     stmt_.setShort(col++, evo_.getDataTypeId());
/* 104 */     stmt_.setShort(col++, evo_.getRefDataTypeId());
/* 105 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(DataTypeRelEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 110 */     int col = startCol_;
/* 111 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 112 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 113 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 114 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(DataTypeRelPK pk)
/*     */     throws ValidationException
/*     */   {
/* 131 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 133 */     PreparedStatement stmt = null;
/* 134 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 138 */       stmt = getConnection().prepareStatement("select DATA_TYPE_REL.DATA_TYPE_ID,DATA_TYPE_REL.REF_DATA_TYPE_ID,DATA_TYPE_REL.UPDATED_BY_USER_ID,DATA_TYPE_REL.UPDATED_TIME,DATA_TYPE_REL.CREATED_TIME from DATA_TYPE_REL where    DATA_TYPE_ID = ? AND REF_DATA_TYPE_ID = ? ");
/*     */ 
/* 141 */       int col = 1;
/* 142 */       stmt.setShort(col++, pk.getDataTypeId());
/* 143 */       stmt.setShort(col++, pk.getRefDataTypeId());
/*     */ 
/* 145 */       resultSet = stmt.executeQuery();
/*     */ 
/* 147 */       if (!resultSet.next()) {
/* 148 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 151 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 152 */       if (this.mDetails.isModified())
/* 153 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 157 */       throw handleSQLException(pk, "select DATA_TYPE_REL.DATA_TYPE_ID,DATA_TYPE_REL.REF_DATA_TYPE_ID,DATA_TYPE_REL.UPDATED_BY_USER_ID,DATA_TYPE_REL.UPDATED_TIME,DATA_TYPE_REL.CREATED_TIME from DATA_TYPE_REL where    DATA_TYPE_ID = ? AND REF_DATA_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 161 */       closeResultSet(resultSet);
/* 162 */       closeStatement(stmt);
/* 163 */       closeConnection();
/*     */ 
/* 165 */       if (timer != null)
/* 166 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 193 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 194 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 199 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 200 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 201 */       stmt = getConnection().prepareStatement("insert into DATA_TYPE_REL ( DATA_TYPE_ID,REF_DATA_TYPE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)");
/*     */ 
/* 204 */       int col = 1;
/* 205 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 206 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 209 */       int resultCount = stmt.executeUpdate();
/* 210 */       if (resultCount != 1)
/*     */       {
/* 212 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 215 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 219 */       throw handleSQLException(this.mDetails.getPK(), "insert into DATA_TYPE_REL ( DATA_TYPE_ID,REF_DATA_TYPE_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 223 */       closeStatement(stmt);
/* 224 */       closeConnection();
/*     */ 
/* 226 */       if (timer != null)
/* 227 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 250 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 254 */     PreparedStatement stmt = null;
/*     */ 
/* 256 */     boolean mainChanged = this.mDetails.isModified();
/* 257 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 260 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 263 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 264 */         stmt = getConnection().prepareStatement("update DATA_TYPE_REL set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DATA_TYPE_ID = ? AND REF_DATA_TYPE_ID = ? ");
/*     */ 
/* 267 */         int col = 1;
/* 268 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 269 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 272 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 275 */         if (resultCount != 1) {
/* 276 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 279 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 288 */       throw handleSQLException(getPK(), "update DATA_TYPE_REL set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DATA_TYPE_ID = ? AND REF_DATA_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 292 */       closeStatement(stmt);
/* 293 */       closeConnection();
/*     */ 
/* 295 */       if ((timer != null) && (
/* 296 */         (mainChanged) || (dependantChanged)))
/* 297 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 317 */     if (items == null) {
/* 318 */       return false;
/*     */     }
/* 320 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 321 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 323 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 328 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 329 */       while (iter2.hasNext())
/*     */       {
/* 331 */         this.mDetails = ((DataTypeRelEVO)iter2.next());
/*     */ 
/* 334 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 336 */         somethingChanged = true;
/*     */ 
/* 339 */         if (deleteStmt == null) {
/* 340 */           deleteStmt = getConnection().prepareStatement("delete from DATA_TYPE_REL where    DATA_TYPE_ID = ? AND REF_DATA_TYPE_ID = ? ");
/*     */         }
/*     */ 
/* 343 */         int col = 1;
/* 344 */         deleteStmt.setShort(col++, this.mDetails.getDataTypeId());
/* 345 */         deleteStmt.setShort(col++, this.mDetails.getRefDataTypeId());
/*     */ 
/* 347 */         if (this._log.isDebugEnabled()) {
/* 348 */           this._log.debug("update", "DataTypeRel deleting DataTypeId=" + this.mDetails.getDataTypeId() + ",RefDataTypeId=" + this.mDetails.getRefDataTypeId());
/*     */         }
/*     */ 
/* 354 */         deleteStmt.addBatch();
/*     */ 
/* 357 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 362 */       if (deleteStmt != null)
/*     */       {
/* 364 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 366 */         deleteStmt.executeBatch();
/*     */ 
/* 368 */         if (timer2 != null) {
/* 369 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 373 */       Iterator iter1 = items.values().iterator();
/* 374 */       while (iter1.hasNext())
/*     */       {
/* 376 */         this.mDetails = ((DataTypeRelEVO)iter1.next());
/*     */ 
/* 378 */         if (this.mDetails.insertPending())
/*     */         {
/* 380 */           somethingChanged = true;
/* 381 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 384 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 386 */         somethingChanged = true;
/* 387 */         doStore();
/*     */       }
/*     */ 
/* 398 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 402 */       throw handleSQLException("delete from DATA_TYPE_REL where    DATA_TYPE_ID = ? AND REF_DATA_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 406 */       if (deleteStmt != null)
/*     */       {
/* 408 */         closeStatement(deleteStmt);
/* 409 */         closeConnection();
/*     */       }
/*     */ 
/* 412 */       this.mDetails = null;
/*     */ 
/* 414 */       if ((somethingChanged) && 
/* 415 */         (timer != null))
/* 416 */         timer.logDebug("update", "collection"); 
/* 416 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(DataTypePK entityPK, DataTypeEVO owningEVO, String dependants)
/*     */   {
/* 436 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 438 */     PreparedStatement stmt = null;
/* 439 */     ResultSet resultSet = null;
/*     */ 
/* 441 */     int itemCount = 0;
/*     */ 
/* 443 */     Collection theseItems = new ArrayList();
/* 444 */     owningEVO.setDataTypeDependencies(theseItems);
/* 445 */     owningEVO.setDataTypeDependenciesAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 449 */       stmt = getConnection().prepareStatement("select DATA_TYPE_REL.DATA_TYPE_ID,DATA_TYPE_REL.REF_DATA_TYPE_ID,DATA_TYPE_REL.UPDATED_BY_USER_ID,DATA_TYPE_REL.UPDATED_TIME,DATA_TYPE_REL.CREATED_TIME from DATA_TYPE_REL where 1=1 and DATA_TYPE_REL.DATA_TYPE_ID = ? order by  DATA_TYPE_REL.DATA_TYPE_ID ,DATA_TYPE_REL.REF_DATA_TYPE_ID");
/*     */ 
/* 451 */       int col = 1;
/* 452 */       stmt.setShort(col++, entityPK.getDataTypeId());
/*     */ 
/* 454 */       resultSet = stmt.executeQuery();
/*     */ 
/* 457 */       while (resultSet.next())
/*     */       {
/* 459 */         itemCount++;
/* 460 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 462 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 465 */       if (timer != null) {
/* 466 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 471 */       throw handleSQLException("select DATA_TYPE_REL.DATA_TYPE_ID,DATA_TYPE_REL.REF_DATA_TYPE_ID,DATA_TYPE_REL.UPDATED_BY_USER_ID,DATA_TYPE_REL.UPDATED_TIME,DATA_TYPE_REL.CREATED_TIME from DATA_TYPE_REL where 1=1 and DATA_TYPE_REL.DATA_TYPE_ID = ? order by  DATA_TYPE_REL.DATA_TYPE_ID ,DATA_TYPE_REL.REF_DATA_TYPE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 475 */       closeResultSet(resultSet);
/* 476 */       closeStatement(stmt);
/* 477 */       closeConnection();
/*     */ 
/* 479 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(short selectDataTypeId, String dependants, Collection currentList)
/*     */   {
/* 504 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 505 */     PreparedStatement stmt = null;
/* 506 */     ResultSet resultSet = null;
/*     */ 
/* 508 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 512 */       stmt = getConnection().prepareStatement("select DATA_TYPE_REL.DATA_TYPE_ID,DATA_TYPE_REL.REF_DATA_TYPE_ID,DATA_TYPE_REL.UPDATED_BY_USER_ID,DATA_TYPE_REL.UPDATED_TIME,DATA_TYPE_REL.CREATED_TIME from DATA_TYPE_REL where    DATA_TYPE_ID = ? ");
/*     */ 
/* 514 */       int col = 1;
/* 515 */       stmt.setShort(col++, selectDataTypeId);
/*     */ 
/* 517 */       resultSet = stmt.executeQuery();
/*     */ 
/* 519 */       while (resultSet.next())
/*     */       {
/* 521 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 524 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 527 */       if (currentList != null)
/*     */       {
/* 530 */         ListIterator iter = items.listIterator();
/* 531 */         DataTypeRelEVO currentEVO = null;
/* 532 */         DataTypeRelEVO newEVO = null;
/* 533 */         while (iter.hasNext())
/*     */         {
/* 535 */           newEVO = (DataTypeRelEVO)iter.next();
/* 536 */           Iterator iter2 = currentList.iterator();
/* 537 */           while (iter2.hasNext())
/*     */           {
/* 539 */             currentEVO = (DataTypeRelEVO)iter2.next();
/* 540 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 542 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 548 */         Iterator iter2 = currentList.iterator();
/* 549 */         while (iter2.hasNext())
/*     */         {
/* 551 */           currentEVO = (DataTypeRelEVO)iter2.next();
/* 552 */           if (currentEVO.insertPending()) {
/* 553 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 557 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 561 */       throw handleSQLException("select DATA_TYPE_REL.DATA_TYPE_ID,DATA_TYPE_REL.REF_DATA_TYPE_ID,DATA_TYPE_REL.UPDATED_BY_USER_ID,DATA_TYPE_REL.UPDATED_TIME,DATA_TYPE_REL.CREATED_TIME from DATA_TYPE_REL where    DATA_TYPE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 565 */       closeResultSet(resultSet);
/* 566 */       closeStatement(stmt);
/* 567 */       closeConnection();
/*     */ 
/* 569 */       if (timer != null) {
/* 570 */         timer.logDebug("getAll", " DataTypeId=" + selectDataTypeId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 575 */     return items;
/*     */   }
/*     */ 
/*     */   public DataTypeRelEVO getDetails(DataTypeCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 589 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 592 */     if (this.mDetails == null) {
/* 593 */       doLoad(((DataTypeRelCK)paramCK).getDataTypeRelPK());
/*     */     }
/* 595 */     else if (!this.mDetails.getPK().equals(((DataTypeRelCK)paramCK).getDataTypeRelPK())) {
/* 596 */       doLoad(((DataTypeRelCK)paramCK).getDataTypeRelPK());
/*     */     }
/*     */ 
/* 599 */     DataTypeRelEVO details = new DataTypeRelEVO();
/* 600 */     details = this.mDetails.deepClone();
/*     */ 
/* 602 */     if (timer != null) {
/* 603 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 605 */     return details;
/*     */   }
/*     */ 
/*     */   public DataTypeRelEVO getDetails(DataTypeCK paramCK, DataTypeRelEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 611 */     DataTypeRelEVO savedEVO = this.mDetails;
/* 612 */     this.mDetails = paramEVO;
/* 613 */     DataTypeRelEVO newEVO = getDetails(paramCK, dependants);
/* 614 */     this.mDetails = savedEVO;
/* 615 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public DataTypeRelEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 621 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 625 */     DataTypeRelEVO details = this.mDetails.deepClone();
/*     */ 
/* 627 */     if (timer != null) {
/* 628 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 630 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 635 */     return "DataTypeRel";
/*     */   }
/*     */ 
/*     */   public DataTypeRelRefImpl getRef(DataTypeRelPK paramDataTypeRelPK)
/*     */   {
/* 640 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 641 */     PreparedStatement stmt = null;
/* 642 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 645 */       stmt = getConnection().prepareStatement("select 0,DATA_TYPE.DATA_TYPE_ID from DATA_TYPE_REL,DATA_TYPE where 1=1 and DATA_TYPE_REL.DATA_TYPE_ID = ? and DATA_TYPE_REL.REF_DATA_TYPE_ID = ? and DATA_TYPE_REL.DATA_TYPE_ID = DATA_TYPE.DATA_TYPE_ID");
/* 646 */       int col = 1;
/* 647 */       stmt.setShort(col++, paramDataTypeRelPK.getDataTypeId());
/* 648 */       stmt.setShort(col++, paramDataTypeRelPK.getRefDataTypeId());
/*     */ 
/* 650 */       resultSet = stmt.executeQuery();
/*     */ 
/* 652 */       if (!resultSet.next()) {
/* 653 */         throw new RuntimeException(getEntityName() + " getRef " + paramDataTypeRelPK + " not found");
/*     */       }
/* 655 */       col = 2;
/* 656 */       DataTypePK newDataTypePK = new DataTypePK(resultSet.getShort(col++));
/*     */ 
/* 660 */       String textDataTypeRel = "";
/* 661 */       DataTypeRelCK ckDataTypeRel = new DataTypeRelCK(newDataTypePK, paramDataTypeRelPK);
/*     */ 
/* 666 */       DataTypeRelRefImpl localDataTypeRelRefImpl = new DataTypeRelRefImpl(ckDataTypeRel, textDataTypeRel);
/*     */       return localDataTypeRelRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 671 */       throw handleSQLException(paramDataTypeRelPK, "select 0,DATA_TYPE.DATA_TYPE_ID from DATA_TYPE_REL,DATA_TYPE where 1=1 and DATA_TYPE_REL.DATA_TYPE_ID = ? and DATA_TYPE_REL.REF_DATA_TYPE_ID = ? and DATA_TYPE_REL.DATA_TYPE_ID = DATA_TYPE.DATA_TYPE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 675 */       closeResultSet(resultSet);
/* 676 */       closeStatement(stmt);
/* 677 */       closeConnection();
/*     */ 
/* 679 */       if (timer != null)
/* 680 */         timer.logDebug("getRef", paramDataTypeRelPK); 
/* 680 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.datatype.DataTypeRelDAO
 * JD-Core Version:    0.6.0
 */