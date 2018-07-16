/*     */ package com.cedar.cp.ejb.impl.extsys;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.extsys.ExtSysPropertyCK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysPropertyPK;
/*     */ import com.cedar.cp.dto.extsys.ExtSysPropertyRefImpl;
/*     */ import com.cedar.cp.dto.extsys.ExternalSystemCK;
/*     */ import com.cedar.cp.dto.extsys.ExternalSystemPK;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class ExtSysPropertyDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID,EXT_SYS_PROPERTY.PROPERTY_NAME,EXT_SYS_PROPERTY.PROPERTY_VALUE";
/*     */   protected static final String SQL_LOAD = " from EXT_SYS_PROPERTY where    EXTERNAL_SYSTEM_ID = ? AND PROPERTY_NAME = ? ";
/*     */   protected static final String SQL_CREATE = "insert into EXT_SYS_PROPERTY ( EXTERNAL_SYSTEM_ID,PROPERTY_NAME,PROPERTY_VALUE) values ( ?,?,?)";
/*     */   protected static final String SQL_STORE = "update EXT_SYS_PROPERTY set PROPERTY_VALUE = ? where    EXTERNAL_SYSTEM_ID = ? AND PROPERTY_NAME = ? ";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from EXT_SYS_PROPERTY where    EXTERNAL_SYSTEM_ID = ? AND PROPERTY_NAME = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from EXT_SYS_PROPERTY where 1=1 and EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID ,EXT_SYS_PROPERTY.PROPERTY_NAME";
/*     */   protected static final String SQL_GET_ALL = " from EXT_SYS_PROPERTY where    EXTERNAL_SYSTEM_ID = ? ";
/*     */   protected ExtSysPropertyEVO mDetails;
/*     */ 
/*     */   public ExtSysPropertyDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ExtSysPropertyDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ExtSysPropertyDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ExtSysPropertyPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ExtSysPropertyEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private ExtSysPropertyEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  86 */     int col = 1;
/*  87 */     ExtSysPropertyEVO evo = new ExtSysPropertyEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++));
/*     */ 
/*  93 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ExtSysPropertyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/*  98 */     int col = startCol_;
/*  99 */     stmt_.setInt(col++, evo_.getExternalSystemId());
/* 100 */     stmt_.setString(col++, evo_.getPropertyName());
/* 101 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ExtSysPropertyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 106 */     int col = startCol_;
/* 107 */     stmt_.setString(col++, evo_.getPropertyValue());
/* 108 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ExtSysPropertyPK pk)
/*     */     throws ValidationException
/*     */   {
/* 125 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 127 */     PreparedStatement stmt = null;
/* 128 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 132 */       stmt = getConnection().prepareStatement("select EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID,EXT_SYS_PROPERTY.PROPERTY_NAME,EXT_SYS_PROPERTY.PROPERTY_VALUE from EXT_SYS_PROPERTY where    EXTERNAL_SYSTEM_ID = ? AND PROPERTY_NAME = ? ");
/*     */ 
/* 135 */       int col = 1;
/* 136 */       stmt.setInt(col++, pk.getExternalSystemId());
/* 137 */       stmt.setString(col++, pk.getPropertyName());
/*     */ 
/* 139 */       resultSet = stmt.executeQuery();
/*     */ 
/* 141 */       if (!resultSet.next()) {
/* 142 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 145 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 146 */       if (this.mDetails.isModified())
/* 147 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 151 */       throw handleSQLException(pk, "select EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID,EXT_SYS_PROPERTY.PROPERTY_NAME,EXT_SYS_PROPERTY.PROPERTY_VALUE from EXT_SYS_PROPERTY where    EXTERNAL_SYSTEM_ID = ? AND PROPERTY_NAME = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 155 */       closeResultSet(resultSet);
/* 156 */       closeStatement(stmt);
/* 157 */       closeConnection();
/*     */ 
/* 159 */       if (timer != null)
/* 160 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 183 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 184 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 188 */       stmt = getConnection().prepareStatement("insert into EXT_SYS_PROPERTY ( EXTERNAL_SYSTEM_ID,PROPERTY_NAME,PROPERTY_VALUE) values ( ?,?,?)");
/*     */ 
/* 191 */       int col = 1;
/* 192 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 193 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 196 */       int resultCount = stmt.executeUpdate();
/* 197 */       if (resultCount != 1)
/*     */       {
/* 199 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 202 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 206 */       throw handleSQLException(this.mDetails.getPK(), "insert into EXT_SYS_PROPERTY ( EXTERNAL_SYSTEM_ID,PROPERTY_NAME,PROPERTY_VALUE) values ( ?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 210 */       closeStatement(stmt);
/* 211 */       closeConnection();
/*     */ 
/* 213 */       if (timer != null)
/* 214 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 235 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 239 */     PreparedStatement stmt = null;
/*     */ 
/* 241 */     boolean mainChanged = this.mDetails.isModified();
/* 242 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 245 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 247 */         stmt = getConnection().prepareStatement("update EXT_SYS_PROPERTY set PROPERTY_VALUE = ? where    EXTERNAL_SYSTEM_ID = ? AND PROPERTY_NAME = ? ");
/*     */ 
/* 250 */         int col = 1;
/* 251 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 252 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 255 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 258 */         if (resultCount != 1) {
/* 259 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 262 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 271 */       throw handleSQLException(getPK(), "update EXT_SYS_PROPERTY set PROPERTY_VALUE = ? where    EXTERNAL_SYSTEM_ID = ? AND PROPERTY_NAME = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 275 */       closeStatement(stmt);
/* 276 */       closeConnection();
/*     */ 
/* 278 */       if ((timer != null) && (
/* 279 */         (mainChanged) || (dependantChanged)))
/* 280 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 300 */     if (items == null) {
/* 301 */       return false;
/*     */     }
/* 303 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 304 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 306 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 311 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 312 */       while (iter2.hasNext())
/*     */       {
/* 314 */         this.mDetails = ((ExtSysPropertyEVO)iter2.next());
/*     */ 
/* 317 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 319 */         somethingChanged = true;
/*     */ 
/* 322 */         if (deleteStmt == null) {
/* 323 */           deleteStmt = getConnection().prepareStatement("delete from EXT_SYS_PROPERTY where    EXTERNAL_SYSTEM_ID = ? AND PROPERTY_NAME = ? ");
/*     */         }
/*     */ 
/* 326 */         int col = 1;
/* 327 */         deleteStmt.setInt(col++, this.mDetails.getExternalSystemId());
/* 328 */         deleteStmt.setString(col++, this.mDetails.getPropertyName());
/*     */ 
/* 330 */         if (this._log.isDebugEnabled()) {
/* 331 */           this._log.debug("update", "ExtSysProperty deleting ExternalSystemId=" + this.mDetails.getExternalSystemId() + ",PropertyName=" + this.mDetails.getPropertyName());
/*     */         }
/*     */ 
/* 337 */         deleteStmt.addBatch();
/*     */ 
/* 340 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 345 */       if (deleteStmt != null)
/*     */       {
/* 347 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 349 */         deleteStmt.executeBatch();
/*     */ 
/* 351 */         if (timer2 != null) {
/* 352 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 356 */       Iterator iter1 = items.values().iterator();
/* 357 */       while (iter1.hasNext())
/*     */       {
/* 359 */         this.mDetails = ((ExtSysPropertyEVO)iter1.next());
/*     */ 
/* 361 */         if (this.mDetails.insertPending())
/*     */         {
/* 363 */           somethingChanged = true;
/* 364 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 367 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 369 */         somethingChanged = true;
/* 370 */         doStore();
/*     */       }
/*     */ 
/* 381 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 385 */       throw handleSQLException("delete from EXT_SYS_PROPERTY where    EXTERNAL_SYSTEM_ID = ? AND PROPERTY_NAME = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 389 */       if (deleteStmt != null)
/*     */       {
/* 391 */         closeStatement(deleteStmt);
/* 392 */         closeConnection();
/*     */       }
/*     */ 
/* 395 */       this.mDetails = null;
/*     */ 
/* 397 */       if ((somethingChanged) && 
/* 398 */         (timer != null))
/* 399 */         timer.logDebug("update", "collection"); 
/* 399 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(ExternalSystemPK entityPK, ExternalSystemEVO owningEVO, String dependants)
/*     */   {
/* 419 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 421 */     PreparedStatement stmt = null;
/* 422 */     ResultSet resultSet = null;
/*     */ 
/* 424 */     int itemCount = 0;
/*     */ 
/* 426 */     Collection theseItems = new ArrayList();
/* 427 */     owningEVO.setExtSysProperties(theseItems);
/* 428 */     owningEVO.setExtSysPropertiesAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 432 */       stmt = getConnection().prepareStatement("select EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID,EXT_SYS_PROPERTY.PROPERTY_NAME,EXT_SYS_PROPERTY.PROPERTY_VALUE from EXT_SYS_PROPERTY where 1=1 and EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID ,EXT_SYS_PROPERTY.PROPERTY_NAME");
/*     */ 
/* 434 */       int col = 1;
/* 435 */       stmt.setInt(col++, entityPK.getExternalSystemId());
/*     */ 
/* 437 */       resultSet = stmt.executeQuery();
/*     */ 
/* 440 */       while (resultSet.next())
/*     */       {
/* 442 */         itemCount++;
/* 443 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 445 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 448 */       if (timer != null) {
/* 449 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 454 */       throw handleSQLException("select EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID,EXT_SYS_PROPERTY.PROPERTY_NAME,EXT_SYS_PROPERTY.PROPERTY_VALUE from EXT_SYS_PROPERTY where 1=1 and EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID = ? order by  EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID ,EXT_SYS_PROPERTY.PROPERTY_NAME", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 458 */       closeResultSet(resultSet);
/* 459 */       closeStatement(stmt);
/* 460 */       closeConnection();
/*     */ 
/* 462 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectExternalSystemId, String dependants, Collection currentList)
/*     */   {
/* 487 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 488 */     PreparedStatement stmt = null;
/* 489 */     ResultSet resultSet = null;
/*     */ 
/* 491 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 495 */       stmt = getConnection().prepareStatement("select EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID,EXT_SYS_PROPERTY.PROPERTY_NAME,EXT_SYS_PROPERTY.PROPERTY_VALUE from EXT_SYS_PROPERTY where    EXTERNAL_SYSTEM_ID = ? ");
/*     */ 
/* 497 */       int col = 1;
/* 498 */       stmt.setInt(col++, selectExternalSystemId);
/*     */ 
/* 500 */       resultSet = stmt.executeQuery();
/*     */ 
/* 502 */       while (resultSet.next())
/*     */       {
/* 504 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 507 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 510 */       if (currentList != null)
/*     */       {
/* 513 */         ListIterator iter = items.listIterator();
/* 514 */         ExtSysPropertyEVO currentEVO = null;
/* 515 */         ExtSysPropertyEVO newEVO = null;
/* 516 */         while (iter.hasNext())
/*     */         {
/* 518 */           newEVO = (ExtSysPropertyEVO)iter.next();
/* 519 */           Iterator iter2 = currentList.iterator();
/* 520 */           while (iter2.hasNext())
/*     */           {
/* 522 */             currentEVO = (ExtSysPropertyEVO)iter2.next();
/* 523 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 525 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 531 */         Iterator iter2 = currentList.iterator();
/* 532 */         while (iter2.hasNext())
/*     */         {
/* 534 */           currentEVO = (ExtSysPropertyEVO)iter2.next();
/* 535 */           if (currentEVO.insertPending()) {
/* 536 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 540 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 544 */       throw handleSQLException("select EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID,EXT_SYS_PROPERTY.PROPERTY_NAME,EXT_SYS_PROPERTY.PROPERTY_VALUE from EXT_SYS_PROPERTY where    EXTERNAL_SYSTEM_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 548 */       closeResultSet(resultSet);
/* 549 */       closeStatement(stmt);
/* 550 */       closeConnection();
/*     */ 
/* 552 */       if (timer != null) {
/* 553 */         timer.logDebug("getAll", " ExternalSystemId=" + selectExternalSystemId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 558 */     return items;
/*     */   }
/*     */ 
/*     */   public ExtSysPropertyEVO getDetails(ExternalSystemCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 572 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 575 */     if (this.mDetails == null) {
/* 576 */       doLoad(((ExtSysPropertyCK)paramCK).getExtSysPropertyPK());
/*     */     }
/* 578 */     else if (!this.mDetails.getPK().equals(((ExtSysPropertyCK)paramCK).getExtSysPropertyPK())) {
/* 579 */       doLoad(((ExtSysPropertyCK)paramCK).getExtSysPropertyPK());
/*     */     }
/*     */ 
/* 582 */     ExtSysPropertyEVO details = new ExtSysPropertyEVO();
/* 583 */     details = this.mDetails.deepClone();
/*     */ 
/* 585 */     if (timer != null) {
/* 586 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 588 */     return details;
/*     */   }
/*     */ 
/*     */   public ExtSysPropertyEVO getDetails(ExternalSystemCK paramCK, ExtSysPropertyEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 594 */     ExtSysPropertyEVO savedEVO = this.mDetails;
/* 595 */     this.mDetails = paramEVO;
/* 596 */     ExtSysPropertyEVO newEVO = getDetails(paramCK, dependants);
/* 597 */     this.mDetails = savedEVO;
/* 598 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public ExtSysPropertyEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 604 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 608 */     ExtSysPropertyEVO details = this.mDetails.deepClone();
/*     */ 
/* 610 */     if (timer != null) {
/* 611 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 613 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 618 */     return "ExtSysProperty";
/*     */   }
/*     */ 
/*     */   public ExtSysPropertyRefImpl getRef(ExtSysPropertyPK paramExtSysPropertyPK)
/*     */   {
/* 623 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 624 */     PreparedStatement stmt = null;
/* 625 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 628 */       stmt = getConnection().prepareStatement("select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID from EXT_SYS_PROPERTY,EXTERNAL_SYSTEM where 1=1 and EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_PROPERTY.PROPERTY_NAME = ? and EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID");
/* 629 */       int col = 1;
/* 630 */       stmt.setInt(col++, paramExtSysPropertyPK.getExternalSystemId());
/* 631 */       stmt.setString(col++, paramExtSysPropertyPK.getPropertyName());
/*     */ 
/* 633 */       resultSet = stmt.executeQuery();
/*     */ 
/* 635 */       if (!resultSet.next()) {
/* 636 */         throw new RuntimeException(getEntityName() + " getRef " + paramExtSysPropertyPK + " not found");
/*     */       }
/* 638 */       col = 2;
/* 639 */       ExternalSystemPK newExternalSystemPK = new ExternalSystemPK(resultSet.getInt(col++));
/*     */ 
/* 643 */       String textExtSysProperty = "";
/* 644 */       ExtSysPropertyCK ckExtSysProperty = new ExtSysPropertyCK(newExternalSystemPK, paramExtSysPropertyPK);
/*     */ 
/* 649 */       ExtSysPropertyRefImpl localExtSysPropertyRefImpl = new ExtSysPropertyRefImpl(ckExtSysProperty, textExtSysProperty);
/*     */       return localExtSysPropertyRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 654 */       throw handleSQLException(paramExtSysPropertyPK, "select 0,EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID from EXT_SYS_PROPERTY,EXTERNAL_SYSTEM where 1=1 and EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID = ? and EXT_SYS_PROPERTY.PROPERTY_NAME = ? and EXT_SYS_PROPERTY.EXTERNAL_SYSTEM_ID = EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 658 */       closeResultSet(resultSet);
/* 659 */       closeStatement(stmt);
/* 660 */       closeConnection();
/*     */ 
/* 662 */       if (timer != null)
/* 663 */         timer.logDebug("getRef", paramExtSysPropertyPK); 
/* 663 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.extsys.ExtSysPropertyDAO
 * JD-Core Version:    0.6.0
 */