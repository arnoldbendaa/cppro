/*     */ package com.cedar.cp.ejb.impl.xmlform;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.xmlform.AllXMLFormUserLinkELO;
/*     */ import com.cedar.cp.dto.xmlform.CheckXMLFormUserLinkELO;
/*     */ import com.cedar.cp.dto.xmlform.XmlFormCK;
/*     */ import com.cedar.cp.dto.xmlform.XmlFormPK;
/*     */ import com.cedar.cp.dto.xmlform.XmlFormRefImpl;
/*     */ import com.cedar.cp.dto.xmlform.XmlFormUserLinkCK;
/*     */ import com.cedar.cp.dto.xmlform.XmlFormUserLinkPK;
/*     */ import com.cedar.cp.dto.xmlform.XmlFormUserLinkRefImpl;
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
		  import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class XmlFormUserLinkDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_COLUMNS = "select XML_FORM_USER_LINK.XML_FORM_ID,XML_FORM_USER_LINK.USER_ID,XML_FORM_USER_LINK.UPDATED_BY_USER_ID,XML_FORM_USER_LINK.UPDATED_TIME,XML_FORM_USER_LINK.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from XML_FORM_USER_LINK where    XML_FORM_ID = ? AND USER_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into XML_FORM_USER_LINK ( XML_FORM_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update XML_FORM_USER_LINK set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    XML_FORM_ID = ? AND USER_ID = ? ";
/* 305 */   protected static String SQL_ALL_X_M_L_FORM_USER_LINK = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,XML_FORM_USER_LINK.XML_FORM_ID      ,XML_FORM_USER_LINK.USER_ID      ,XML_FORM_USER_LINK.USER_ID from XML_FORM_USER_LINK    ,XML_FORM where 1=1   and XML_FORM_USER_LINK.XML_FORM_ID = XML_FORM.XML_FORM_ID ";
/*     */ 
/* 412 */   protected static String SQL_CHECK_X_M_L_FORM_USER_LINK = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,XML_FORM_USER_LINK.XML_FORM_ID      ,XML_FORM_USER_LINK.USER_ID      ,XML_FORM_USER_LINK.USER_ID from XML_FORM_USER_LINK    ,XML_FORM where 1=1   and XML_FORM_USER_LINK.XML_FORM_ID = XML_FORM.XML_FORM_ID  and  XML_FORM_USER_LINK.USER_ID = ?";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from XML_FORM_USER_LINK where    XML_FORM_ID = ? AND USER_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from XML_FORM_USER_LINK where 1=1 and XML_FORM_USER_LINK.XML_FORM_ID = ? order by  XML_FORM_USER_LINK.XML_FORM_ID ,XML_FORM_USER_LINK.USER_ID";
/*     */   protected static final String SQL_GET_ALL = " from XML_FORM_USER_LINK where    XML_FORM_ID = ? ";
/*     */   protected static final String SQL_DELETE_FOR_USER = "delete from XML_FORM_USER_LINK where    USER_ID = ? ";
/*     */   protected XmlFormUserLinkEVO mDetails;
/*     */ 
/*     */   public XmlFormUserLinkDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public XmlFormUserLinkDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public XmlFormUserLinkDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected XmlFormUserLinkPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(XmlFormUserLinkEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private XmlFormUserLinkEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/*  88 */     int col = 1;
/*  89 */     XmlFormUserLinkEVO evo = new XmlFormUserLinkEVO(resultSet_.getInt(col++), resultSet_.getInt(col++));
/*     */ 
/*  94 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  95 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  96 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  97 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(XmlFormUserLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 102 */     int col = startCol_;
/* 103 */     stmt_.setInt(col++, evo_.getXmlFormId());
/* 104 */     stmt_.setInt(col++, evo_.getUserId());
/* 105 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(XmlFormUserLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 110 */     int col = startCol_;
/* 111 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 112 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 113 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 114 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(XmlFormUserLinkPK pk)
/*     */     throws ValidationException
/*     */   {
/* 131 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 133 */     PreparedStatement stmt = null;
/* 134 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 138 */       stmt = getConnection().prepareStatement("select XML_FORM_USER_LINK.XML_FORM_ID,XML_FORM_USER_LINK.USER_ID,XML_FORM_USER_LINK.UPDATED_BY_USER_ID,XML_FORM_USER_LINK.UPDATED_TIME,XML_FORM_USER_LINK.CREATED_TIME from XML_FORM_USER_LINK where    XML_FORM_ID = ? AND USER_ID = ? ");
/*     */ 
/* 141 */       int col = 1;
/* 142 */       stmt.setInt(col++, pk.getXmlFormId());
/* 143 */       stmt.setInt(col++, pk.getUserId());
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
/* 157 */       throw handleSQLException(pk, "select XML_FORM_USER_LINK.XML_FORM_ID,XML_FORM_USER_LINK.USER_ID,XML_FORM_USER_LINK.UPDATED_BY_USER_ID,XML_FORM_USER_LINK.UPDATED_TIME,XML_FORM_USER_LINK.CREATED_TIME from XML_FORM_USER_LINK where    XML_FORM_ID = ? AND USER_ID = ? ", sqle);
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
/* 201 */       stmt = getConnection().prepareStatement("insert into XML_FORM_USER_LINK ( XML_FORM_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)");
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
/* 219 */       throw handleSQLException(this.mDetails.getPK(), "insert into XML_FORM_USER_LINK ( XML_FORM_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)", sqle);
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
/* 264 */         stmt = getConnection().prepareStatement("update XML_FORM_USER_LINK set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    XML_FORM_ID = ? AND USER_ID = ? ");
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
/* 288 */       throw handleSQLException(getPK(), "update XML_FORM_USER_LINK set UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    XML_FORM_ID = ? AND USER_ID = ? ", sqle);
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
/*     */   public AllXMLFormUserLinkELO getAllXMLFormUserLink()
/*     */   {
/* 334 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 335 */     PreparedStatement stmt = null;
/* 336 */     ResultSet resultSet = null;
/* 337 */     AllXMLFormUserLinkELO results = new AllXMLFormUserLinkELO();
/*     */     try
/*     */     {
/* 340 */       stmt = getConnection().prepareStatement(SQL_ALL_X_M_L_FORM_USER_LINK);
/* 341 */       int col = 1;
/* 342 */       resultSet = stmt.executeQuery();
/* 343 */       while (resultSet.next())
/*     */       {
/* 345 */         col = 2;
/*     */ 
/* 348 */         XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
/*     */ 
/* 351 */         String textXmlForm = resultSet.getString(col++);
/*     */ 
/* 354 */         XmlFormUserLinkPK pkXmlFormUserLink = new XmlFormUserLinkPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 358 */         String textXmlFormUserLink = "";
/*     */ 
/* 363 */         XmlFormUserLinkCK ckXmlFormUserLink = new XmlFormUserLinkCK(pkXmlForm, pkXmlFormUserLink);
/*     */ 
/* 369 */         XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
/*     */ 
/* 375 */         XmlFormUserLinkRefImpl erXmlFormUserLink = new XmlFormUserLinkRefImpl(ckXmlFormUserLink, textXmlFormUserLink);
/*     */ 
/* 380 */         int col1 = resultSet.getInt(col++);
/*     */ 
/* 383 */         results.add(erXmlFormUserLink, erXmlForm, col1);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 392 */       throw handleSQLException(SQL_ALL_X_M_L_FORM_USER_LINK, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 396 */       closeResultSet(resultSet);
/* 397 */       closeStatement(stmt);
/* 398 */       closeConnection();
/*     */     }
/*     */ 
/* 401 */     if (timer != null) {
/* 402 */       timer.logDebug("getAllXMLFormUserLink", " items=" + results.size());
/*     */     }
/*     */ 
/* 406 */     return results;
/*     */   }
/*     */ 
/*     */   public CheckXMLFormUserLinkELO getCheckXMLFormUserLink(int param1)
/*     */   {
/* 443 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 444 */     PreparedStatement stmt = null;
/* 445 */     ResultSet resultSet = null;
/* 446 */     CheckXMLFormUserLinkELO results = new CheckXMLFormUserLinkELO();
/*     */     try
/*     */     {
/* 449 */       stmt = getConnection().prepareStatement(SQL_CHECK_X_M_L_FORM_USER_LINK);
/* 450 */       int col = 1;
/* 451 */       stmt.setInt(col++, param1);
/* 452 */       resultSet = stmt.executeQuery();
/* 453 */       while (resultSet.next())
/*     */       {
/* 455 */         col = 2;
/*     */ 
/* 458 */         XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
/*     */ 
/* 461 */         String textXmlForm = resultSet.getString(col++);
/*     */ 
/* 464 */         XmlFormUserLinkPK pkXmlFormUserLink = new XmlFormUserLinkPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*     */ 
/* 468 */         String textXmlFormUserLink = "";
/*     */ 
/* 473 */         XmlFormUserLinkCK ckXmlFormUserLink = new XmlFormUserLinkCK(pkXmlForm, pkXmlFormUserLink);
/*     */ 
/* 479 */         XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
/*     */ 
/* 485 */         XmlFormUserLinkRefImpl erXmlFormUserLink = new XmlFormUserLinkRefImpl(ckXmlFormUserLink, textXmlFormUserLink);
/*     */ 
/* 490 */         int col1 = resultSet.getInt(col++);
/*     */ 
/* 493 */         results.add(erXmlFormUserLink, erXmlForm, col1);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 502 */       throw handleSQLException(SQL_CHECK_X_M_L_FORM_USER_LINK, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 506 */       closeResultSet(resultSet);
/* 507 */       closeStatement(stmt);
/* 508 */       closeConnection();
/*     */     }
/*     */ 
/* 511 */     if (timer != null) {
/* 512 */       timer.logDebug("getCheckXMLFormUserLink", " UserId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 517 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 535 */     if (items == null) {
/* 536 */       return false;
/*     */     }
/* 538 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 539 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 541 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 546 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 547 */       while (iter2.hasNext())
/*     */       {
/* 549 */         this.mDetails = ((XmlFormUserLinkEVO)iter2.next());
/*     */ 
/* 552 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 554 */         somethingChanged = true;
/*     */ 
/* 557 */         if (deleteStmt == null) {
/* 558 */           deleteStmt = getConnection().prepareStatement("delete from XML_FORM_USER_LINK where    XML_FORM_ID = ? AND USER_ID = ? ");
/*     */         }
/*     */ 
/* 561 */         int col = 1;
/* 562 */         deleteStmt.setInt(col++, this.mDetails.getXmlFormId());
/* 563 */         deleteStmt.setInt(col++, this.mDetails.getUserId());
/*     */ 
/* 565 */         if (this._log.isDebugEnabled()) {
/* 566 */           this._log.debug("update", "XmlFormUserLink deleting XmlFormId=" + this.mDetails.getXmlFormId() + ",UserId=" + this.mDetails.getUserId());
/*     */         }
/*     */ 
/* 572 */         deleteStmt.addBatch();
/*     */ 
/* 575 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 580 */       if (deleteStmt != null)
/*     */       {
/* 582 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 584 */         deleteStmt.executeBatch();
/*     */ 
/* 586 */         if (timer2 != null) {
/* 587 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 591 */       Iterator iter1 = items.values().iterator();
/* 592 */       while (iter1.hasNext())
/*     */       {
/* 594 */         this.mDetails = ((XmlFormUserLinkEVO)iter1.next());
/*     */ 
/* 596 */         if (this.mDetails.insertPending())
/*     */         {
/* 598 */           somethingChanged = true;
/* 599 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 602 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 604 */         somethingChanged = true;
/* 605 */         doStore();
/*     */       }
/*     */ 
/* 616 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 620 */       throw handleSQLException("delete from XML_FORM_USER_LINK where    XML_FORM_ID = ? AND USER_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 624 */       if (deleteStmt != null)
/*     */       {
/* 626 */         closeStatement(deleteStmt);
/* 627 */         closeConnection();
/*     */       }
/*     */ 
/* 630 */       this.mDetails = null;
/*     */ 
/* 632 */       if ((somethingChanged) && 
/* 633 */         (timer != null))
/* 634 */         timer.logDebug("update", "collection"); 
/* 634 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(XmlFormPK entityPK, XmlFormEVO owningEVO, String dependants)
/*     */   {
/* 654 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 656 */     PreparedStatement stmt = null;
/* 657 */     ResultSet resultSet = null;
/*     */ 
/* 659 */     int itemCount = 0;
/*     */ 
/* 661 */     Collection theseItems = new ArrayList();
/* 662 */     owningEVO.setUserList(theseItems);
/* 663 */     owningEVO.setUserListAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 667 */       stmt = getConnection().prepareStatement("select XML_FORM_USER_LINK.XML_FORM_ID,  XML_FORM_USER_LINK.USER_ID,  XML_FORM_USER_LINK.UPDATED_BY_USER_ID,  XML_FORM_USER_LINK.UPDATED_TIME, XML_FORM_USER_LINK.CREATED_TIME  from  XML_FORM_USER_LINK  where  1=1  and  XML_FORM_USER_LINK.XML_FORM_ID = ?  order by  XML_FORM_USER_LINK.XML_FORM_ID ,XML_FORM_USER_LINK.USER_ID");
/*     */ 
/* 669 */       int col = 1;
/* 670 */       stmt.setInt(col++, entityPK.getXmlFormId());
/*     */ 
/* 672 */       resultSet = stmt.executeQuery();
/*     */ 
/* 675 */       while (resultSet.next())
/*     */       {
/* 677 */         itemCount++;
/* 678 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 680 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 683 */       if (timer != null) {
/* 684 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 689 */       throw handleSQLException("select XML_FORM_USER_LINK.XML_FORM_ID,XML_FORM_USER_LINK.USER_ID,XML_FORM_USER_LINK.UPDATED_BY_USER_ID,XML_FORM_USER_LINK.UPDATED_TIME,XML_FORM_USER_LINK.CREATED_TIME from XML_FORM_USER_LINK where 1=1 and XML_FORM_USER_LINK.XML_FORM_ID = ? order by  XML_FORM_USER_LINK.XML_FORM_ID ,XML_FORM_USER_LINK.USER_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 693 */       closeResultSet(resultSet);
/* 694 */       closeStatement(stmt);
/* 695 */       closeConnection();
/*     */ 
/* 697 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(int selectXmlFormId, String dependants, Collection currentList)
/*     */   {
/* 722 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 723 */     PreparedStatement stmt = null;
/* 724 */     ResultSet resultSet = null;
/*     */ 
/* 726 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 730 */       stmt = getConnection().prepareStatement("select XML_FORM_USER_LINK.XML_FORM_ID,  XML_FORM_USER_LINK.USER_ID,  XML_FORM_USER_LINK.UPDATED_BY_USER_ID,  XML_FORM_USER_LINK.UPDATED_TIME,  XML_FORM_USER_LINK.CREATED_TIME  from  XML_FORM_USER_LINK  where  XML_FORM_ID = ? ");
/*     */ 
/* 732 */       int col = 1;
/* 733 */       stmt.setInt(col++, selectXmlFormId);
/*     */ 
/* 735 */       resultSet = stmt.executeQuery();
/*     */ 
/* 737 */       while (resultSet.next())
/*     */       {
/* 739 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 742 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 745 */       if (currentList != null)
/*     */       {
/* 748 */         ListIterator iter = items.listIterator();
/* 749 */         XmlFormUserLinkEVO currentEVO = null;
/* 750 */         XmlFormUserLinkEVO newEVO = null;
/* 751 */         while (iter.hasNext())
/*     */         {
/* 753 */           newEVO = (XmlFormUserLinkEVO)iter.next();
/* 754 */           Iterator iter2 = currentList.iterator();
/* 755 */           while (iter2.hasNext())
/*     */           {
/* 757 */             currentEVO = (XmlFormUserLinkEVO)iter2.next();
/* 758 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 760 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 766 */         Iterator iter2 = currentList.iterator();
/* 767 */         while (iter2.hasNext())
/*     */         {
/* 769 */           currentEVO = (XmlFormUserLinkEVO)iter2.next();
/* 770 */           if (currentEVO.insertPending()) {
/* 771 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 775 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 779 */       throw handleSQLException("select XML_FORM_USER_LINK.XML_FORM_ID,XML_FORM_USER_LINK.USER_ID,XML_FORM_USER_LINK.UPDATED_BY_USER_ID,XML_FORM_USER_LINK.UPDATED_TIME,XML_FORM_USER_LINK.CREATED_TIME from XML_FORM_USER_LINK where    XML_FORM_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 783 */       closeResultSet(resultSet);
/* 784 */       closeStatement(stmt);
/* 785 */       closeConnection();
/*     */ 
/* 787 */       if (timer != null) {
/* 788 */         timer.logDebug("getAll", " XmlFormId=" + selectXmlFormId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 793 */     return items;
/*     */   }
/*     */ 
/*     */   public XmlFormUserLinkEVO getDetails(XmlFormCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 807 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 810 */     if (this.mDetails == null) {
/* 811 */       doLoad(((XmlFormUserLinkCK)paramCK).getXmlFormUserLinkPK());
/*     */     }
/* 813 */     else if (!this.mDetails.getPK().equals(((XmlFormUserLinkCK)paramCK).getXmlFormUserLinkPK())) {
/* 814 */       doLoad(((XmlFormUserLinkCK)paramCK).getXmlFormUserLinkPK());
/*     */     }
/*     */ 
/* 817 */     XmlFormUserLinkEVO details = new XmlFormUserLinkEVO();
/* 818 */     details = this.mDetails.deepClone();
/*     */ 
/* 820 */     if (timer != null) {
/* 821 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 823 */     return details;
/*     */   }
/*     */ 
/*     */   public XmlFormUserLinkEVO getDetails(XmlFormCK paramCK, XmlFormUserLinkEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 829 */     XmlFormUserLinkEVO savedEVO = this.mDetails;
/* 830 */     this.mDetails = paramEVO;
/* 831 */     XmlFormUserLinkEVO newEVO = getDetails(paramCK, dependants);
/* 832 */     this.mDetails = savedEVO;
/* 833 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public XmlFormUserLinkEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 839 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 843 */     XmlFormUserLinkEVO details = this.mDetails.deepClone();
/*     */ 
/* 845 */     if (timer != null) {
/* 846 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 848 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 853 */     return "XmlFormUserLink";
/*     */   }
/*     */ 
/*     */   public XmlFormUserLinkRefImpl getRef(XmlFormUserLinkPK paramXmlFormUserLinkPK)
/*     */   {
/* 858 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 859 */     PreparedStatement stmt = null;
/* 860 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 863 */       stmt = getConnection().prepareStatement("select 0,XML_FORM.XML_FORM_ID from XML_FORM_USER_LINK,XML_FORM where 1=1 and XML_FORM_USER_LINK.XML_FORM_ID = ? and XML_FORM_USER_LINK.USER_ID = ? and XML_FORM_USER_LINK.XML_FORM_ID = XML_FORM.XML_FORM_ID");
/* 864 */       int col = 1;
/* 865 */       stmt.setInt(col++, paramXmlFormUserLinkPK.getXmlFormId());
/* 866 */       stmt.setInt(col++, paramXmlFormUserLinkPK.getUserId());
/*     */ 
/* 868 */       resultSet = stmt.executeQuery();
/*     */ 
/* 870 */       if (!resultSet.next()) {
/* 871 */         throw new RuntimeException(getEntityName() + " getRef " + paramXmlFormUserLinkPK + " not found");
/*     */       }
/* 873 */       col = 2;
/* 874 */       XmlFormPK newXmlFormPK = new XmlFormPK(resultSet.getInt(col++));
/*     */ 
/* 878 */       String textXmlFormUserLink = "";
/* 879 */       XmlFormUserLinkCK ckXmlFormUserLink = new XmlFormUserLinkCK(newXmlFormPK, paramXmlFormUserLinkPK);
/*     */ 
/* 884 */       XmlFormUserLinkRefImpl localXmlFormUserLinkRefImpl = new XmlFormUserLinkRefImpl(ckXmlFormUserLink, textXmlFormUserLink);
/*     */       return localXmlFormUserLinkRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 889 */       throw handleSQLException(paramXmlFormUserLinkPK, "select 0,XML_FORM.XML_FORM_ID from XML_FORM_USER_LINK,XML_FORM where 1=1 and XML_FORM_USER_LINK.XML_FORM_ID = ? and XML_FORM_USER_LINK.USER_ID = ? and XML_FORM_USER_LINK.XML_FORM_ID = XML_FORM.XML_FORM_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 893 */       closeResultSet(resultSet);
/* 894 */       closeStatement(stmt);
/* 895 */       closeConnection();
/*     */ 
/* 897 */       if (timer != null)
/* 898 */         timer.logDebug("getRef", paramXmlFormUserLinkPK); 
/* 898 */     }
/*     */   }
/*     */ 
/*     */   public void deleteForUser(int userId)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 918 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 919 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 922 */       stmt = getConnection().prepareStatement("delete from XML_FORM_USER_LINK where    USER_ID = ? ");
/* 923 */       int col = 1;
/* 924 */       stmt.setInt(col++, userId);
/*     */ 
/* 926 */       stmt.executeUpdate();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 930 */       throw handleSQLException("delete from XML_FORM_USER_LINK where    USER_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 934 */       closeStatement(stmt);
/* 935 */       closeConnection();
/*     */ 
/* 937 */       if (timer != null)
/* 938 */         timer.logDebug("deleteForUser", Integer.valueOf(userId));
/*     */     }
/*     */   }

	/**
	 * Insert XMLForms links to current user, option available in: User->Users->XMLForms
	 */

	public void insert(List<Object[]> insert) {
		Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		PreparedStatement stmtInsert = null;

		try {
			stmtInsert = getConnection().prepareStatement("insert into XML_FORM_USER_LINK ( XML_FORM_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)");
			for (Object[] add : insert) {
				int xmlFormId = (Integer) add[0];
				int userId = (Integer) add[1];
				int updatedByUserId = 0;
				Timestamp createdTime = new Timestamp(new Date().getTime());
				Timestamp updatedTime = new Timestamp(new Date().getTime());
				stmtInsert.setInt(1, xmlFormId);
				stmtInsert.setInt(2, userId);
				stmtInsert.setInt(3, updatedByUserId);
				stmtInsert.setTimestamp(4, updatedTime);
				stmtInsert.setTimestamp(5, createdTime);
				stmtInsert.addBatch();

			}
			stmtInsert.executeBatch();
		} catch (SQLException sqle) {
			throw handleSQLException("insert into XML_FORM_USER_LINK ( XML_FORM_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)", sqle);
		} finally {
			closeStatement(stmtInsert);
			closeConnection();

			if (timer != null)
				timer.logDebug("getRef", insert);
		}

	}

	/**
	 * Delete XMLForms links to current user, option available in: User->Users->XMLForms
	 */

	public void delete(List<Object[]> delete) {
		PreparedStatement deleteStmt = null;
		int ifNotAvailableForAll = 0;

		try {
			deleteStmt = getConnection().prepareStatement("DELETE FROM XML_FORM_USER_LINK WHERE XML_FORM_ID = ? and USER_ID = ?");

			for (Object[] add : delete) {

				int xmlFormId = (Integer) add[0];
				int userId = (Integer) add[1];
				if (isXmlFormAvailableForAll(xmlFormId)) {
					blockAvailableForAllXmlFormForUser(xmlFormId, userId);
				} else {
					deleteStmt.setInt(1, xmlFormId);
					deleteStmt.setInt(2, userId);
					deleteStmt.addBatch();
					ifNotAvailableForAll++;
				}

			}
			if (ifNotAvailableForAll > 0)
				deleteStmt.executeBatch();

		} catch (SQLException sqle) {
			throw handleSQLException("DELETE FROM XML_FORM_USER_LINK WHERE XML_FORM_ID = ? and USER_ID = ?", sqle);
		} finally {
			closeStatement(deleteStmt);
			closeConnection();
		}

	}

	/**
	 * If user want to delete XMLForm available default for all users, all other users are linking to this XMLForm without him.
	 * 
	 */

	public void blockAvailableForAllXmlFormForUser(int xmlFormId, int blockedUserId) throws SQLException {
		Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		PreparedStatement stmt = null;
		PreparedStatement stmtInsert = null;
		ResultSet resultSet = null;
		List<Integer> userIdList = new ArrayList<Integer>();

		stmt = getConnection().prepareStatement("SELECT USER_ID FROM USR WHERE USER_ID <> ?");
		stmt.setInt(1, blockedUserId);
		resultSet = stmt.executeQuery();
		try {
			while (resultSet.next()) {
				userIdList.add(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			throw handleSQLException("SELECT USER_ID FROM USR WHERE USER_ID <> ?", e);
		} finally {
			closeResultSet(resultSet);
			stmt.close();
		}

		try {
			stmtInsert = getConnection().prepareStatement("insert into XML_FORM_USER_LINK ( XML_FORM_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)");
			Timestamp createdTime = new Timestamp(new Date().getTime());
			Timestamp updatedTime = new Timestamp(new Date().getTime());
			for (Integer add : userIdList) {
				int userId = (Integer) add;
				int updatedByUserId = 0;
				stmtInsert.setInt(1, xmlFormId);
				stmtInsert.setInt(2, userId);
				stmtInsert.setInt(3, updatedByUserId);
				stmtInsert.setTimestamp(4, updatedTime);
				stmtInsert.setTimestamp(5, createdTime);
				stmtInsert.addBatch();

			}
			stmtInsert.executeBatch();
		} catch (SQLException sqle) {
			throw handleSQLException("insert into XML_FORM_USER_LINK ( XML_FORM_ID,USER_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?)", sqle);
		} finally {
			closeStatement(stmtInsert);
			stmtInsert.close();

		}

	}

	/**
	 * Check XMLForm is available for all users (anyone user has linked to it), return true if is available for all, otherwise false.
	 */

	public boolean isXmlFormAvailableForAll(int xmlFormId) {
		Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		try {
			stmt = getConnection().prepareStatement("SELECT USER_ID FROM XML_FORM_USER_LINK WHERE XML_FORM_ID = ?");
			stmt.setInt(1, xmlFormId);

			resultSet = stmt.executeQuery();

			if (!resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException sqle) {
			throw handleSQLException("SELECT USER_ID FROM XML_FORM_USER_LINK WHERE XML_FORM_ID = " + xmlFormId, sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);

			if (timer != null)
				timer.logDebug("getRef", xmlFormId);
		}

	}

}

/*     */ 

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.xmlform.XmlFormUserLinkDAO
 * JD-Core Version:    0.6.0
 */