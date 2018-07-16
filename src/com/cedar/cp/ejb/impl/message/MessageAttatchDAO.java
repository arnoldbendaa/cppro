/*     */ package com.cedar.cp.ejb.impl.message;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.dto.message.AttatchmentForMessageELO;
/*     */ import com.cedar.cp.dto.message.MessageAttatchCK;
/*     */ import com.cedar.cp.dto.message.MessageAttatchPK;
/*     */ import com.cedar.cp.dto.message.MessageAttatchRefImpl;
/*     */ import com.cedar.cp.dto.message.MessageCK;
/*     */ import com.cedar.cp.dto.message.MessagePK;
/*     */ import com.cedar.cp.dto.message.MessageRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ import oracle.sql.BLOB;
/*     */ 
/*     */ public class MessageAttatchDAO extends AbstractDAO
/*     */ {
/*  34 */   Log _log = new Log(getClass());
/*     */   private static final String SQL_SELECT_LOBS = "select  ATTATCH from MESSAGE_ATTATCH where    MESSAGE_ID = ? AND MESSAGE_ATTATCH_ID = ? for update";
/*     */   private static final String SQL_SELECT_COLUMNS = "select MESSAGE_ATTATCH.ATTATCH,MESSAGE_ATTATCH.MESSAGE_ID,MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID,MESSAGE_ATTATCH.ATTATCH_NAME,MESSAGE_ATTATCH.UPDATED_BY_USER_ID,MESSAGE_ATTATCH.UPDATED_TIME,MESSAGE_ATTATCH.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from MESSAGE_ATTATCH where    MESSAGE_ID = ? AND MESSAGE_ATTATCH_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into MESSAGE_ATTATCH ( MESSAGE_ID,MESSAGE_ATTATCH_ID,ATTATCH,ATTATCH_NAME,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,empty_blob(),?,?,?,?)";
/*     */   protected static final String SQL_STORE = "update MESSAGE_ATTATCH set ATTATCH_NAME = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MESSAGE_ID = ? AND MESSAGE_ATTATCH_ID = ? ";
/* 393 */   protected static String SQL_ATTATCHMENT_FOR_MESSAGE = "select 0       ,MESSAGE.MESSAGE_ID      ,MESSAGE_ATTATCH.MESSAGE_ID      ,MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID      ,MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID      ,MESSAGE_ATTATCH.ATTATCH      ,MESSAGE_ATTATCH.ATTATCH_NAME from MESSAGE_ATTATCH    ,MESSAGE where 1=1   and MESSAGE_ATTATCH.MESSAGE_ID = MESSAGE.MESSAGE_ID  and  MESSAGE_ATTATCH.MESSAGE_ID = ?";
/*     */   protected static final String SQL_DELETE_BATCH = "delete from MESSAGE_ATTATCH where    MESSAGE_ID = ? AND MESSAGE_ATTATCH_ID = ? ";
/*     */   public static final String SQL_BULK_GET_ALL = " from MESSAGE_ATTATCH where 1=1 and MESSAGE_ATTATCH.MESSAGE_ID = ? order by  MESSAGE_ATTATCH.MESSAGE_ID ,MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID";
/*     */   protected static final String SQL_GET_ALL = " from MESSAGE_ATTATCH where    MESSAGE_ID = ? ";
/*     */   protected MessageAttatchEVO mDetails;
/*     */   private BLOB mAttatchBlob;
/*     */ 
/*     */   public MessageAttatchDAO(Connection connection)
/*     */   {
/*  41 */     super(connection);
/*     */   }
/*     */ 
/*     */   public MessageAttatchDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public MessageAttatchDAO(DataSource ds)
/*     */   {
/*  57 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected MessageAttatchPK getPK()
/*     */   {
/*  65 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(MessageAttatchEVO details)
/*     */   {
/*  74 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   private void selectLobs(MessageAttatchEVO evo_)
/*     */   {
/*  89 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/*  91 */     PreparedStatement stmt = null;
/*  92 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/*  96 */       stmt = getConnection().prepareStatement("select  ATTATCH from MESSAGE_ATTATCH where    MESSAGE_ID = ? AND MESSAGE_ATTATCH_ID = ? for update");
/*     */ 
/*  98 */       putEvoKeysToJdbc(evo_, stmt, 1);
/*     */ 
/* 100 */       resultSet = stmt.executeQuery();
/*     */ 
/* 102 */       int col = 1;
/* 103 */       while (resultSet.next())
/*     */       {
/* 105 */         this.mAttatchBlob = ((BLOB)resultSet.getBlob(col++));
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 110 */       throw handleSQLException(evo_.getPK(), "select  ATTATCH from MESSAGE_ATTATCH where    MESSAGE_ID = ? AND MESSAGE_ATTATCH_ID = ? for update", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 114 */       closeResultSet(resultSet);
/* 115 */       closeStatement(stmt);
/*     */ 
/* 117 */       if (timer != null)
/* 118 */         timer.logDebug("selectLobs", evo_.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void putLobs(MessageAttatchEVO evo_) throws SQLException
/*     */   {
/* 124 */     updateBlob(this.mAttatchBlob, evo_.getAttatch());
/*     */   }
/*     */ 
/*     */   private MessageAttatchEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 140 */     int col = 1;
/* 141 */     this.mAttatchBlob = ((BLOB)resultSet_.getBlob(col++));
/* 142 */     MessageAttatchEVO evo = new MessageAttatchEVO(resultSet_.getLong(col++), resultSet_.getLong(col++), blobToByteArray(this.mAttatchBlob), resultSet_.getString(col++));
/*     */ 
/* 149 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 150 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 151 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 152 */     return evo;
/*     */   }
/*     */ 
/*     */   private byte[] getByteArray(InputStream bis_)
/*     */   {
/*     */     try
/*     */     {
/* 159 */       ByteArrayOutputStream bos = new ByteArrayOutputStream(bis_.available());
/*     */       int chunk;
/* 161 */       while ((chunk = bis_.read()) != -1)
/* 162 */         bos.write(chunk);
/* 163 */       return bos.toByteArray();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 167 */       this._log.error("getByteArray", e);
				throw new RuntimeException(e.getMessage());
/* 168 */     }
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(MessageAttatchEVO evo_, PreparedStatement stmt_, int startCol_)
/*     */     throws SQLException
/*     */   {
/* 174 */     int col = startCol_;
/* 175 */     stmt_.setLong(col++, evo_.getMessageId());
/* 176 */     stmt_.setLong(col++, evo_.getMessageAttatchId());
/* 177 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(MessageAttatchEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 182 */     int col = startCol_;
/*     */ 
/* 184 */     stmt_.setString(col++, evo_.getAttatchName());
/* 185 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 186 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 187 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 188 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(MessageAttatchPK pk)
/*     */     throws ValidationException
/*     */   {
/* 205 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 207 */     PreparedStatement stmt = null;
/* 208 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 212 */       stmt = getConnection().prepareStatement("select MESSAGE_ATTATCH.ATTATCH,MESSAGE_ATTATCH.MESSAGE_ID,MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID,MESSAGE_ATTATCH.ATTATCH_NAME,MESSAGE_ATTATCH.UPDATED_BY_USER_ID,MESSAGE_ATTATCH.UPDATED_TIME,MESSAGE_ATTATCH.CREATED_TIME from MESSAGE_ATTATCH where    MESSAGE_ID = ? AND MESSAGE_ATTATCH_ID = ? ");
/*     */ 
/* 215 */       int col = 1;
/* 216 */       stmt.setLong(col++, pk.getMessageId());
/* 217 */       stmt.setLong(col++, pk.getMessageAttatchId());
/*     */ 
/* 219 */       resultSet = stmt.executeQuery();
/*     */ 
/* 221 */       if (!resultSet.next()) {
/* 222 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 225 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 226 */       if (this.mDetails.isModified())
/* 227 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 231 */       throw handleSQLException(pk, "select MESSAGE_ATTATCH.ATTATCH,MESSAGE_ATTATCH.MESSAGE_ID,MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID,MESSAGE_ATTATCH.ATTATCH_NAME,MESSAGE_ATTATCH.UPDATED_BY_USER_ID,MESSAGE_ATTATCH.UPDATED_TIME,MESSAGE_ATTATCH.CREATED_TIME from MESSAGE_ATTATCH where    MESSAGE_ID = ? AND MESSAGE_ATTATCH_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 235 */       closeResultSet(resultSet);
/* 236 */       closeStatement(stmt);
/* 237 */       closeConnection();
/*     */ 
/* 239 */       if (timer != null)
/* 240 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 271 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 272 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 277 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 278 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 279 */       stmt = getConnection().prepareStatement("insert into MESSAGE_ATTATCH ( MESSAGE_ID,MESSAGE_ATTATCH_ID,ATTATCH,ATTATCH_NAME,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,empty_blob(),?,?,?,?)");
/*     */ 
/* 282 */       int col = 1;
/* 283 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 284 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 287 */       int resultCount = stmt.executeUpdate();
/* 288 */       if (resultCount != 1)
/*     */       {
/* 290 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 295 */       selectLobs(this.mDetails);
/* 296 */       this._log.debug("doCreate", "calling putLobs");
/* 297 */       putLobs(this.mDetails);
/*     */ 
/* 299 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 303 */       throw handleSQLException(this.mDetails.getPK(), "insert into MESSAGE_ATTATCH ( MESSAGE_ID,MESSAGE_ATTATCH_ID,ATTATCH,ATTATCH_NAME,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,empty_blob(),?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 307 */       closeStatement(stmt);
/* 308 */       closeConnection();
/*     */ 
/* 310 */       if (timer != null)
/* 311 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 335 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 339 */     PreparedStatement stmt = null;
/*     */ 
/* 341 */     boolean mainChanged = this.mDetails.isModified();
/* 342 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 345 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 348 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 349 */         stmt = getConnection().prepareStatement("update MESSAGE_ATTATCH set ATTATCH_NAME = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MESSAGE_ID = ? AND MESSAGE_ATTATCH_ID = ? ");
/*     */ 
/* 351 */         selectLobs(this.mDetails);
/* 352 */         putLobs(this.mDetails);
/*     */ 
/* 355 */         int col = 1;
/* 356 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 357 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 360 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 363 */         if (resultCount != 1) {
/* 364 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 367 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 376 */       throw handleSQLException(getPK(), "update MESSAGE_ATTATCH set ATTATCH_NAME = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MESSAGE_ID = ? AND MESSAGE_ATTATCH_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 380 */       closeStatement(stmt);
/* 381 */       closeConnection();
/*     */ 
/* 383 */       if ((timer != null) && (
/* 384 */         (mainChanged) || (dependantChanged)))
/* 385 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   public AttatchmentForMessageELO getAttatchmentForMessage(long param1)
/*     */   {
/* 425 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 426 */     PreparedStatement stmt = null;
/* 427 */     ResultSet resultSet = null;
/* 428 */     AttatchmentForMessageELO results = new AttatchmentForMessageELO();
/*     */     try
/*     */     {
/* 431 */       stmt = getConnection().prepareStatement(SQL_ATTATCHMENT_FOR_MESSAGE);
/* 432 */       int col = 1;
/* 433 */       stmt.setLong(col++, param1);
/* 434 */       resultSet = stmt.executeQuery();
/* 435 */       while (resultSet.next())
/*     */       {
/* 437 */         col = 2;
/*     */ 
/* 440 */         MessagePK pkMessage = new MessagePK(resultSet.getLong(col++));
/*     */ 
/* 443 */         String textMessage = "";
/*     */ 
/* 446 */         MessageAttatchPK pkMessageAttatch = new MessageAttatchPK(resultSet.getLong(col++), resultSet.getLong(col++));
/*     */ 
/* 450 */         String textMessageAttatch = "";
/*     */ 
/* 455 */         MessageAttatchCK ckMessageAttatch = new MessageAttatchCK(pkMessage, pkMessageAttatch);
/*     */ 
/* 461 */         MessageRefImpl erMessage = new MessageRefImpl(pkMessage, textMessage);
/*     */ 
/* 467 */         MessageAttatchRefImpl erMessageAttatch = new MessageAttatchRefImpl(ckMessageAttatch, textMessageAttatch);
/*     */ 
/* 472 */         long col1 = resultSet.getLong(col++);
/* 473 */         BLOB col2Blob = (BLOB)resultSet.getBlob(col++);
/* 474 */         byte[] col2 = blobToByteArray(col2Blob);
/* 475 */         String col3 = resultSet.getString(col++);
/*     */ 
/* 478 */         results.add(erMessageAttatch, erMessage, col1, col2, col3);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 489 */       throw handleSQLException(SQL_ATTATCHMENT_FOR_MESSAGE, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 493 */       closeResultSet(resultSet);
/* 494 */       closeStatement(stmt);
/* 495 */       closeConnection();
/*     */     }
/*     */ 
/* 498 */     if (timer != null) {
/* 499 */       timer.logDebug("getAttatchmentForMessage", " MessageId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 504 */     return results;
/*     */   }
/*     */ 
/*     */   public boolean update(Map items)
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 522 */     if (items == null) {
/* 523 */       return false;
/*     */     }
/* 525 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 526 */     PreparedStatement deleteStmt = null;
/*     */ 
/* 528 */     boolean somethingChanged = false;
/*     */     try
/*     */     {
/* 533 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/* 534 */       while (iter2.hasNext())
/*     */       {
/* 536 */         this.mDetails = ((MessageAttatchEVO)iter2.next());
/*     */ 
/* 539 */         if (!this.mDetails.deletePending())
/*     */           continue;
/* 541 */         somethingChanged = true;
/*     */ 
/* 544 */         if (deleteStmt == null) {
/* 545 */           deleteStmt = getConnection().prepareStatement("delete from MESSAGE_ATTATCH where    MESSAGE_ID = ? AND MESSAGE_ATTATCH_ID = ? ");
/*     */         }
/*     */ 
/* 548 */         int col = 1;
/* 549 */         deleteStmt.setLong(col++, this.mDetails.getMessageId());
/* 550 */         deleteStmt.setLong(col++, this.mDetails.getMessageAttatchId());
/*     */ 
/* 552 */         if (this._log.isDebugEnabled()) {
/* 553 */           this._log.debug("update", "MessageAttatch deleting MessageId=" + this.mDetails.getMessageId() + ",MessageAttatchId=" + this.mDetails.getMessageAttatchId());
/*     */         }
/*     */ 
/* 559 */         deleteStmt.addBatch();
/*     */ 
/* 562 */         items.remove(this.mDetails.getPK());
/*     */       }
/*     */ 
/* 567 */       if (deleteStmt != null)
/*     */       {
/* 569 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 571 */         deleteStmt.executeBatch();
/*     */ 
/* 573 */         if (timer2 != null) {
/* 574 */           timer2.logDebug("update", "delete batch");
/*     */         }
/*     */       }
/*     */ 
/* 578 */       Iterator iter1 = items.values().iterator();
/* 579 */       while (iter1.hasNext())
/*     */       {
/* 581 */         this.mDetails = ((MessageAttatchEVO)iter1.next());
/*     */ 
/* 583 */         if (this.mDetails.insertPending())
/*     */         {
/* 585 */           somethingChanged = true;
/* 586 */           doCreate(); continue;
/*     */         }
/*     */ 
/* 589 */         if (!this.mDetails.isModified())
/*     */           continue;
/* 591 */         somethingChanged = true;
/* 592 */         doStore();
/*     */       }
/*     */ 
/* 603 */       boolean bool1 = somethingChanged;
/*     */       return bool1;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 607 */       throw handleSQLException("delete from MESSAGE_ATTATCH where    MESSAGE_ID = ? AND MESSAGE_ATTATCH_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 611 */       if (deleteStmt != null)
/*     */       {
/* 613 */         closeStatement(deleteStmt);
/* 614 */         closeConnection();
/*     */       }
/*     */ 
/* 617 */       this.mDetails = null;
/*     */ 
/* 619 */       if ((somethingChanged) && 
/* 620 */         (timer != null))
/* 621 */         timer.logDebug("update", "collection"); 
/* 621 */     }
/*     */   }
/*     */ 
/*     */   public void bulkGetAll(MessagePK entityPK, MessageEVO owningEVO, String dependants)
/*     */   {
/* 641 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 643 */     PreparedStatement stmt = null;
/* 644 */     ResultSet resultSet = null;
/*     */ 
/* 646 */     int itemCount = 0;
/*     */ 
/* 648 */     Collection theseItems = new ArrayList();
/* 649 */     owningEVO.setMessageAttatchments(theseItems);
/* 650 */     owningEVO.setMessageAttatchmentsAllItemsLoaded(true);
/*     */     try
/*     */     {
/* 654 */       stmt = getConnection().prepareStatement("select MESSAGE_ATTATCH.ATTATCH,MESSAGE_ATTATCH.MESSAGE_ID,MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID,MESSAGE_ATTATCH.ATTATCH_NAME,MESSAGE_ATTATCH.UPDATED_BY_USER_ID,MESSAGE_ATTATCH.UPDATED_TIME,MESSAGE_ATTATCH.CREATED_TIME from MESSAGE_ATTATCH where 1=1 and MESSAGE_ATTATCH.MESSAGE_ID = ? order by  MESSAGE_ATTATCH.MESSAGE_ID ,MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID");
/*     */ 
/* 656 */       int col = 1;
/* 657 */       stmt.setLong(col++, entityPK.getMessageId());
/*     */ 
/* 659 */       resultSet = stmt.executeQuery();
/*     */ 
/* 662 */       while (resultSet.next())
/*     */       {
/* 664 */         itemCount++;
/* 665 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 667 */         theseItems.add(this.mDetails);
/*     */       }
/*     */ 
/* 670 */       if (timer != null) {
/* 671 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*     */       }
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 676 */       throw handleSQLException("select MESSAGE_ATTATCH.ATTATCH,MESSAGE_ATTATCH.MESSAGE_ID,MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID,MESSAGE_ATTATCH.ATTATCH_NAME,MESSAGE_ATTATCH.UPDATED_BY_USER_ID,MESSAGE_ATTATCH.UPDATED_TIME,MESSAGE_ATTATCH.CREATED_TIME from MESSAGE_ATTATCH where 1=1 and MESSAGE_ATTATCH.MESSAGE_ID = ? order by  MESSAGE_ATTATCH.MESSAGE_ID ,MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 680 */       closeResultSet(resultSet);
/* 681 */       closeStatement(stmt);
/* 682 */       closeConnection();
/*     */ 
/* 684 */       this.mDetails = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getAll(long selectMessageId, String dependants, Collection currentList)
/*     */   {
/* 709 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 710 */     PreparedStatement stmt = null;
/* 711 */     ResultSet resultSet = null;
/*     */ 
/* 713 */     ArrayList items = new ArrayList();
/*     */     try
/*     */     {
/* 717 */       stmt = getConnection().prepareStatement("select MESSAGE_ATTATCH.ATTATCH,MESSAGE_ATTATCH.MESSAGE_ID,MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID,MESSAGE_ATTATCH.ATTATCH_NAME,MESSAGE_ATTATCH.UPDATED_BY_USER_ID,MESSAGE_ATTATCH.UPDATED_TIME,MESSAGE_ATTATCH.CREATED_TIME from MESSAGE_ATTATCH where    MESSAGE_ID = ? ");
/*     */ 
/* 719 */       int col = 1;
/* 720 */       stmt.setLong(col++, selectMessageId);
/*     */ 
/* 722 */       resultSet = stmt.executeQuery();
/*     */ 
/* 724 */       while (resultSet.next())
/*     */       {
/* 726 */         this.mDetails = getEvoFromJdbc(resultSet);
/*     */ 
/* 729 */         items.add(this.mDetails);
/*     */       }
/*     */ 
/* 732 */       if (currentList != null)
/*     */       {
/* 735 */         ListIterator iter = items.listIterator();
/* 736 */         MessageAttatchEVO currentEVO = null;
/* 737 */         MessageAttatchEVO newEVO = null;
/* 738 */         while (iter.hasNext())
/*     */         {
/* 740 */           newEVO = (MessageAttatchEVO)iter.next();
/* 741 */           Iterator iter2 = currentList.iterator();
/* 742 */           while (iter2.hasNext())
/*     */           {
/* 744 */             currentEVO = (MessageAttatchEVO)iter2.next();
/* 745 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*     */               continue;
/* 747 */             iter.set(currentEVO);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 753 */         Iterator iter2 = currentList.iterator();
/* 754 */         while (iter2.hasNext())
/*     */         {
/* 756 */           currentEVO = (MessageAttatchEVO)iter2.next();
/* 757 */           if (currentEVO.insertPending()) {
/* 758 */             items.add(currentEVO);
/*     */           }
/*     */         }
/*     */       }
/* 762 */       this.mDetails = null;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 766 */       throw handleSQLException("select MESSAGE_ATTATCH.ATTATCH,MESSAGE_ATTATCH.MESSAGE_ID,MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID,MESSAGE_ATTATCH.ATTATCH_NAME,MESSAGE_ATTATCH.UPDATED_BY_USER_ID,MESSAGE_ATTATCH.UPDATED_TIME,MESSAGE_ATTATCH.CREATED_TIME from MESSAGE_ATTATCH where    MESSAGE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 770 */       closeResultSet(resultSet);
/* 771 */       closeStatement(stmt);
/* 772 */       closeConnection();
/*     */ 
/* 774 */       if (timer != null) {
/* 775 */         timer.logDebug("getAll", " MessageId=" + selectMessageId + " items=" + items.size());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 780 */     return items;
/*     */   }
/*     */ 
/*     */   public MessageAttatchEVO getDetails(MessageCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 794 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 797 */     if (this.mDetails == null) {
/* 798 */       doLoad(((MessageAttatchCK)paramCK).getMessageAttatchPK());
/*     */     }
/* 800 */     else if (!this.mDetails.getPK().equals(((MessageAttatchCK)paramCK).getMessageAttatchPK())) {
/* 801 */       doLoad(((MessageAttatchCK)paramCK).getMessageAttatchPK());
/*     */     }
/*     */ 
/* 804 */     MessageAttatchEVO details = new MessageAttatchEVO();
/* 805 */     details = this.mDetails.deepClone();
/*     */ 
/* 807 */     if (timer != null) {
/* 808 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 810 */     return details;
/*     */   }
/*     */ 
/*     */   public MessageAttatchEVO getDetails(MessageCK paramCK, MessageAttatchEVO paramEVO, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 816 */     MessageAttatchEVO savedEVO = this.mDetails;
/* 817 */     this.mDetails = paramEVO;
/* 818 */     MessageAttatchEVO newEVO = getDetails(paramCK, dependants);
/* 819 */     this.mDetails = savedEVO;
/* 820 */     return newEVO;
/*     */   }
/*     */ 
/*     */   public MessageAttatchEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 826 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 830 */     MessageAttatchEVO details = this.mDetails.deepClone();
/*     */ 
/* 832 */     if (timer != null) {
/* 833 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 835 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 840 */     return "MessageAttatch";
/*     */   }
/*     */ 
/*     */   public MessageAttatchRefImpl getRef(MessageAttatchPK paramMessageAttatchPK)
/*     */   {
/* 845 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 846 */     PreparedStatement stmt = null;
/* 847 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 850 */       stmt = getConnection().prepareStatement("select 0,MESSAGE.MESSAGE_ID from MESSAGE_ATTATCH,MESSAGE where 1=1 and MESSAGE_ATTATCH.MESSAGE_ID = ? and MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID = ? and MESSAGE_ATTATCH.MESSAGE_ID = MESSAGE.MESSAGE_ID");
/* 851 */       int col = 1;
/* 852 */       stmt.setLong(col++, paramMessageAttatchPK.getMessageId());
/* 853 */       stmt.setLong(col++, paramMessageAttatchPK.getMessageAttatchId());
/*     */ 
/* 855 */       resultSet = stmt.executeQuery();
/*     */ 
/* 857 */       if (!resultSet.next()) {
/* 858 */         throw new RuntimeException(getEntityName() + " getRef " + paramMessageAttatchPK + " not found");
/*     */       }
/* 860 */       col = 2;
/* 861 */       MessagePK newMessagePK = new MessagePK(resultSet.getLong(col++));
/*     */ 
/* 865 */       String textMessageAttatch = "";
/* 866 */       MessageAttatchCK ckMessageAttatch = new MessageAttatchCK(newMessagePK, paramMessageAttatchPK);
/*     */ 
/* 871 */       MessageAttatchRefImpl localMessageAttatchRefImpl = new MessageAttatchRefImpl(ckMessageAttatch, textMessageAttatch);
/*     */       return localMessageAttatchRefImpl;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 876 */       throw handleSQLException(paramMessageAttatchPK, "select 0,MESSAGE.MESSAGE_ID from MESSAGE_ATTATCH,MESSAGE where 1=1 and MESSAGE_ATTATCH.MESSAGE_ID = ? and MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID = ? and MESSAGE_ATTATCH.MESSAGE_ID = MESSAGE.MESSAGE_ID", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 880 */       closeResultSet(resultSet);
/* 881 */       closeStatement(stmt);
/* 882 */       closeConnection();
/*     */ 
/* 884 */       if (timer != null)
/* 885 */         timer.logDebug("getRef", paramMessageAttatchPK); 
/* 885 */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.message.MessageAttatchDAO
 * JD-Core Version:    0.6.0
 */