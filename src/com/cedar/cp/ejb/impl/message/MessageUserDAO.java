/*      */ package com.cedar.cp.ejb.impl.message;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.dto.message.AllMessagesToUserELO;
/*      */ import com.cedar.cp.dto.message.MessageCK;
/*      */ import com.cedar.cp.dto.message.MessageFromUserELO;
/*      */ import com.cedar.cp.dto.message.MessagePK;
/*      */ import com.cedar.cp.dto.message.MessageRefImpl;
/*      */ import com.cedar.cp.dto.message.MessageToUserELO;
/*      */ import com.cedar.cp.dto.message.MessageUserCK;
/*      */ import com.cedar.cp.dto.message.MessageUserPK;
/*      */ import com.cedar.cp.dto.message.MessageUserRefImpl;
/*      */ import com.cedar.cp.dto.user.UserPK;
/*      */ import com.cedar.cp.dto.user.UserRefImpl;
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
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class MessageUserDAO extends AbstractDAO
/*      */ {
/*   38 */   Log _log = new Log(getClass());
/*      */   private static final String SQL_SELECT_COLUMNS = "select MESSAGE_USER.MESSAGE_ID,MESSAGE_USER.MESSAGE_USER_ID,MESSAGE_USER.USER_ID,MESSAGE_USER.READ,MESSAGE_USER.DELETED,MESSAGE_USER.TYPE,MESSAGE_USER.UPDATED_BY_USER_ID,MESSAGE_USER.UPDATED_TIME,MESSAGE_USER.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from MESSAGE_USER where    MESSAGE_ID = ? AND MESSAGE_USER_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into MESSAGE_USER ( MESSAGE_ID,MESSAGE_USER_ID,USER_ID,READ,DELETED,TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update MESSAGE_USER set USER_ID = ?,READ = ?,DELETED = ?,TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MESSAGE_ID = ? AND MESSAGE_USER_ID = ? ";
/*  341 */   protected static String SQL_MESSAGE_FROM_USER = "select 0       ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,USR.USER_ID      ,USR.NAME      ,MESSAGE_USER.MESSAGE_ID      ,MESSAGE_USER.USER_ID      ,USR.NAME      ,USR.FULL_NAME      ,USR.E_MAIL_ADDRESS from MESSAGE_USER    ,MESSAGE    ,USR where 1=1   and MESSAGE_USER.MESSAGE_ID = MESSAGE.MESSAGE_ID  and  MESSAGE_USER.USER_ID = USR.NAME and MESSAGE_USER.TYPE = 1 and MESSAGE_USER.MESSAGE_ID = ?";
/*      */ 
/*  479 */   protected static String SQL_MESSAGE_TO_USER = "select 0       ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,USR.USER_ID      ,USR.NAME      ,MESSAGE_USER.MESSAGE_ID      ,MESSAGE_USER.USER_ID      ,USR.NAME      ,USR.FULL_NAME from MESSAGE_USER    ,MESSAGE    ,USR where 1=1   and MESSAGE_USER.MESSAGE_ID = MESSAGE.MESSAGE_ID  and  MESSAGE_USER.USER_ID = USR.NAME and MESSAGE_USER.TYPE = 0 and MESSAGE_USER.MESSAGE_ID = ?";
/*      */ 
/*  614 */   protected static String SQL_ALL_MESSAGES_TO_USER = "select 0       ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,MESSAGE_USER.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,MESSAGE_USER.USER_ID from MESSAGE_USER    ,MESSAGE where 1=1   and MESSAGE_USER.MESSAGE_ID = MESSAGE.MESSAGE_ID  and  MESSAGE_USER.MESSAGE_ID = ? and MESSAGE_USER.TYPE = 0";
/*      */   protected static final String SQL_DELETE_BATCH = "delete from MESSAGE_USER where    MESSAGE_ID = ? AND MESSAGE_USER_ID = ? ";
/*      */   public static final String SQL_BULK_GET_ALL = " from MESSAGE_USER where 1=1 and MESSAGE_USER.MESSAGE_ID = ? order by  MESSAGE_USER.MESSAGE_ID ,MESSAGE_USER.MESSAGE_USER_ID";
/*      */   protected static final String SQL_GET_ALL = " from MESSAGE_USER where    MESSAGE_ID = ? ";
/*      */   protected MessageUserEVO mDetails;
/*      */ 
/*      */   public MessageUserDAO(Connection connection)
/*      */   {
/*   45 */     super(connection);
/*      */   }
/*      */ 
/*      */   public MessageUserDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public MessageUserDAO(DataSource ds)
/*      */   {
/*   61 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected MessageUserPK getPK()
/*      */   {
/*   69 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(MessageUserEVO details)
/*      */   {
/*   78 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   private MessageUserEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*   98 */     int col = 1;
/*   99 */     MessageUserEVO evo = new MessageUserEVO(resultSet_.getLong(col++), resultSet_.getLong(col++), resultSet_.getString(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++));
/*      */ 
/*  108 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  109 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  110 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  111 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(MessageUserEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  116 */     int col = startCol_;
/*  117 */     stmt_.setLong(col++, evo_.getMessageId());
/*  118 */     stmt_.setLong(col++, evo_.getMessageUserId());
/*  119 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(MessageUserEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  124 */     int col = startCol_;
/*  125 */     stmt_.setString(col++, evo_.getUserId());
/*  126 */     if (evo_.getRead())
/*  127 */       stmt_.setString(col++, "Y");
/*      */     else
/*  129 */       stmt_.setString(col++, " ");
/*  130 */     if (evo_.getDeleted())
/*  131 */       stmt_.setString(col++, "Y");
/*      */     else
/*  133 */       stmt_.setString(col++, " ");
/*  134 */     stmt_.setInt(col++, evo_.getType());
/*  135 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  136 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  137 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  138 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(MessageUserPK pk)
/*      */     throws ValidationException
/*      */   {
/*  155 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  157 */     PreparedStatement stmt = null;
/*  158 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  162 */       stmt = getConnection().prepareStatement("select MESSAGE_USER.MESSAGE_ID,MESSAGE_USER.MESSAGE_USER_ID,MESSAGE_USER.USER_ID,MESSAGE_USER.READ,MESSAGE_USER.DELETED,MESSAGE_USER.TYPE,MESSAGE_USER.UPDATED_BY_USER_ID,MESSAGE_USER.UPDATED_TIME,MESSAGE_USER.CREATED_TIME from MESSAGE_USER where    MESSAGE_ID = ? AND MESSAGE_USER_ID = ? ");
/*      */ 
/*  165 */       int col = 1;
/*  166 */       stmt.setLong(col++, pk.getMessageId());
/*  167 */       stmt.setLong(col++, pk.getMessageUserId());
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
/*  181 */       throw handleSQLException(pk, "select MESSAGE_USER.MESSAGE_ID,MESSAGE_USER.MESSAGE_USER_ID,MESSAGE_USER.USER_ID,MESSAGE_USER.READ,MESSAGE_USER.DELETED,MESSAGE_USER.TYPE,MESSAGE_USER.UPDATED_BY_USER_ID,MESSAGE_USER.UPDATED_TIME,MESSAGE_USER.CREATED_TIME from MESSAGE_USER where    MESSAGE_ID = ? AND MESSAGE_USER_ID = ? ", sqle);
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
/*  226 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  231 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  232 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  233 */       stmt = getConnection().prepareStatement("insert into MESSAGE_USER ( MESSAGE_ID,MESSAGE_USER_ID,USER_ID,READ,DELETED,TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  236 */       int col = 1;
/*  237 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  238 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  241 */       int resultCount = stmt.executeUpdate();
/*  242 */       if (resultCount != 1)
/*      */       {
/*  244 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  247 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  251 */       throw handleSQLException(this.mDetails.getPK(), "insert into MESSAGE_USER ( MESSAGE_ID,MESSAGE_USER_ID,USER_ID,READ,DELETED,TYPE,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  255 */       closeStatement(stmt);
/*  256 */       closeConnection();
/*      */ 
/*  258 */       if (timer != null)
/*  259 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  286 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  290 */     PreparedStatement stmt = null;
/*      */ 
/*  292 */     boolean mainChanged = this.mDetails.isModified();
/*  293 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  296 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  299 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  300 */         stmt = getConnection().prepareStatement("update MESSAGE_USER set USER_ID = ?,READ = ?,DELETED = ?,TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MESSAGE_ID = ? AND MESSAGE_USER_ID = ? ");
/*      */ 
/*  303 */         int col = 1;
/*  304 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  305 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  308 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  311 */         if (resultCount != 1) {
/*  312 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  315 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  324 */       throw handleSQLException(getPK(), "update MESSAGE_USER set USER_ID = ?,READ = ?,DELETED = ?,TYPE = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MESSAGE_ID = ? AND MESSAGE_USER_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  328 */       closeStatement(stmt);
/*  329 */       closeConnection();
/*      */ 
/*  331 */       if ((timer != null) && (
/*  332 */         (mainChanged) || (dependantChanged)))
/*  333 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   public MessageFromUserELO getMessageFromUser(long param1)
/*      */   {
/*  379 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  380 */     PreparedStatement stmt = null;
/*  381 */     ResultSet resultSet = null;
/*  382 */     MessageFromUserELO results = new MessageFromUserELO();
/*      */     try
/*      */     {
/*  385 */       stmt = getConnection().prepareStatement(SQL_MESSAGE_FROM_USER);
/*  386 */       int col = 1;
/*  387 */       stmt.setLong(col++, param1);
/*  388 */       resultSet = stmt.executeQuery();
/*  389 */       while (resultSet.next())
/*      */       {
/*  391 */         col = 2;
/*      */ 
/*  394 */         MessagePK pkMessage = new MessagePK(resultSet.getLong(col++));
/*      */ 
/*  397 */         String textMessage = "";
/*      */ 
/*  400 */         MessageUserPK pkMessageUser = new MessageUserPK(resultSet.getLong(col++), resultSet.getLong(col++));
/*      */ 
/*  404 */         String textMessageUser = "";
/*      */ 
/*  407 */         UserPK pkUser = new UserPK(resultSet.getInt(col++));
/*      */ 
/*  410 */         String textUser = resultSet.getString(col++);
/*      */ 
/*  414 */         MessageUserCK ckMessageUser = new MessageUserCK(pkMessage, pkMessageUser);
/*      */ 
/*  420 */         MessageRefImpl erMessage = new MessageRefImpl(pkMessage, textMessage);
/*      */ 
/*  426 */         MessageUserRefImpl erMessageUser = new MessageUserRefImpl(ckMessageUser, textMessageUser);
/*      */ 
/*  432 */         UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
/*      */ 
/*  437 */         long col1 = resultSet.getLong(col++);
/*  438 */         String col2 = resultSet.getString(col++);
/*  439 */         String col3 = resultSet.getString(col++);
/*  440 */         String col4 = resultSet.getString(col++);
/*  441 */         String col5 = resultSet.getString(col++);
/*      */ 
/*  444 */         results.add(erMessageUser, erMessage, erUser, col1, col2, col3, col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  458 */       throw handleSQLException(SQL_MESSAGE_FROM_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  462 */       closeResultSet(resultSet);
/*  463 */       closeStatement(stmt);
/*  464 */       closeConnection();
/*      */     }
/*      */ 
/*  467 */     if (timer != null) {
/*  468 */       timer.logDebug("getMessageFromUser", " MessageId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  473 */     return results;
/*      */   }
/*      */ 
/*      */   public MessageToUserELO getMessageToUser(long param1)
/*      */   {
/*  516 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  517 */     PreparedStatement stmt = null;
/*  518 */     ResultSet resultSet = null;
/*  519 */     MessageToUserELO results = new MessageToUserELO();
/*      */     try
/*      */     {
/*  522 */       stmt = getConnection().prepareStatement(SQL_MESSAGE_TO_USER);
/*  523 */       int col = 1;
/*  524 */       stmt.setLong(col++, param1);
/*  525 */       resultSet = stmt.executeQuery();
/*  526 */       while (resultSet.next())
/*      */       {
/*  528 */         col = 2;
/*      */ 
/*  531 */         MessagePK pkMessage = new MessagePK(resultSet.getLong(col++));
/*      */ 
/*  534 */         String textMessage = "";
/*      */ 
/*  537 */         MessageUserPK pkMessageUser = new MessageUserPK(resultSet.getLong(col++), resultSet.getLong(col++));
/*      */ 
/*  541 */         String textMessageUser = "";
/*      */ 
/*  544 */         UserPK pkUser = new UserPK(resultSet.getInt(col++));
/*      */ 
/*  547 */         String textUser = resultSet.getString(col++);
/*      */ 
/*  551 */         MessageUserCK ckMessageUser = new MessageUserCK(pkMessage, pkMessageUser);
/*      */ 
/*  557 */         MessageRefImpl erMessage = new MessageRefImpl(pkMessage, textMessage);
/*      */ 
/*  563 */         MessageUserRefImpl erMessageUser = new MessageUserRefImpl(ckMessageUser, textMessageUser);
/*      */ 
/*  569 */         UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
/*      */ 
/*  574 */         long col1 = resultSet.getLong(col++);
/*  575 */         String col2 = resultSet.getString(col++);
/*  576 */         String col3 = resultSet.getString(col++);
/*  577 */         String col4 = resultSet.getString(col++);
/*      */ 
/*  580 */         results.add(erMessageUser, erMessage, erUser, col1, col2, col3, col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  593 */       throw handleSQLException(SQL_MESSAGE_TO_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  597 */       closeResultSet(resultSet);
/*  598 */       closeStatement(stmt);
/*  599 */       closeConnection();
/*      */     }
/*      */ 
/*  602 */     if (timer != null) {
/*  603 */       timer.logDebug("getMessageToUser", " MessageId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  608 */     return results;
/*      */   }
/*      */ 
/*      */   public AllMessagesToUserELO getAllMessagesToUser(long param1)
/*      */   {
/*  646 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  647 */     PreparedStatement stmt = null;
/*  648 */     ResultSet resultSet = null;
/*  649 */     AllMessagesToUserELO results = new AllMessagesToUserELO();
/*      */     try
/*      */     {
/*  652 */       stmt = getConnection().prepareStatement(SQL_ALL_MESSAGES_TO_USER);
/*  653 */       int col = 1;
/*  654 */       stmt.setLong(col++, param1);
/*  655 */       resultSet = stmt.executeQuery();
/*  656 */       while (resultSet.next())
/*      */       {
/*  658 */         col = 2;
/*      */ 
/*  661 */         MessagePK pkMessage = new MessagePK(resultSet.getLong(col++));
/*      */ 
/*  664 */         String textMessage = "";
/*      */ 
/*  667 */         MessageUserPK pkMessageUser = new MessageUserPK(resultSet.getLong(col++), resultSet.getLong(col++));
/*      */ 
/*  671 */         String textMessageUser = "";
/*      */ 
/*  676 */         MessageUserCK ckMessageUser = new MessageUserCK(pkMessage, pkMessageUser);
/*      */ 
/*  682 */         MessageRefImpl erMessage = new MessageRefImpl(pkMessage, textMessage);
/*      */ 
/*  688 */         MessageUserRefImpl erMessageUser = new MessageUserRefImpl(ckMessageUser, textMessageUser);
/*      */ 
/*  693 */         long col1 = resultSet.getLong(col++);
/*  694 */         long col2 = resultSet.getLong(col++);
/*  695 */         String col3 = resultSet.getString(col++);
/*      */ 
/*  698 */         results.add(erMessageUser, erMessage, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  709 */       throw handleSQLException(SQL_ALL_MESSAGES_TO_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  713 */       closeResultSet(resultSet);
/*  714 */       closeStatement(stmt);
/*  715 */       closeConnection();
/*      */     }
/*      */ 
/*  718 */     if (timer != null) {
/*  719 */       timer.logDebug("getAllMessagesToUser", " MessageId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  724 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean update(Map items)
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  742 */     if (items == null) {
/*  743 */       return false;
/*      */     }
/*  745 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  746 */     PreparedStatement deleteStmt = null;
/*      */ 
/*  748 */     boolean somethingChanged = false;
/*      */     try
/*      */     {
/*  753 */       Iterator iter2 = new ArrayList(items.values()).iterator();
/*  754 */       while (iter2.hasNext())
/*      */       {
/*  756 */         this.mDetails = ((MessageUserEVO)iter2.next());
/*      */ 
/*  759 */         if (!this.mDetails.deletePending())
/*      */           continue;
/*  761 */         somethingChanged = true;
/*      */ 
/*  764 */         if (deleteStmt == null) {
/*  765 */           deleteStmt = getConnection().prepareStatement("delete from MESSAGE_USER where    MESSAGE_ID = ? AND MESSAGE_USER_ID = ? ");
/*      */         }
/*      */ 
/*  768 */         int col = 1;
/*  769 */         deleteStmt.setLong(col++, this.mDetails.getMessageId());
/*  770 */         deleteStmt.setLong(col++, this.mDetails.getMessageUserId());
/*      */ 
/*  772 */         if (this._log.isDebugEnabled()) {
/*  773 */           this._log.debug("update", "MessageUser deleting MessageId=" + this.mDetails.getMessageId() + ",MessageUserId=" + this.mDetails.getMessageUserId());
/*      */         }
/*      */ 
/*  779 */         deleteStmt.addBatch();
/*      */ 
/*  782 */         items.remove(this.mDetails.getPK());
/*      */       }
/*      */ 
/*  787 */       if (deleteStmt != null)
/*      */       {
/*  789 */         Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  791 */         deleteStmt.executeBatch();
/*      */ 
/*  793 */         if (timer2 != null) {
/*  794 */           timer2.logDebug("update", "delete batch");
/*      */         }
/*      */       }
/*      */ 
/*  798 */       Iterator iter1 = items.values().iterator();
/*  799 */       while (iter1.hasNext())
/*      */       {
/*  801 */         this.mDetails = ((MessageUserEVO)iter1.next());
/*      */ 
/*  803 */         if (this.mDetails.insertPending())
/*      */         {
/*  805 */           somethingChanged = true;
/*  806 */           doCreate(); continue;
/*      */         }
/*      */ 
/*  809 */         if (!this.mDetails.isModified())
/*      */           continue;
/*  811 */         somethingChanged = true;
/*  812 */         doStore();
/*      */       }
/*      */ 
/*  823 */       boolean bool1 = somethingChanged;
/*      */       return bool1;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  827 */       throw handleSQLException("delete from MESSAGE_USER where    MESSAGE_ID = ? AND MESSAGE_USER_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  831 */       if (deleteStmt != null)
/*      */       {
/*  833 */         closeStatement(deleteStmt);
/*  834 */         closeConnection();
/*      */       }
/*      */ 
/*  837 */       this.mDetails = null;
/*      */ 
/*  839 */       if ((somethingChanged) && 
/*  840 */         (timer != null))
/*  841 */         timer.logDebug("update", "collection"); 
/*  841 */     }
/*      */   }
/*      */ 
/*      */   public void bulkGetAll(MessagePK entityPK, MessageEVO owningEVO, String dependants)
/*      */   {
/*  861 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  863 */     PreparedStatement stmt = null;
/*  864 */     ResultSet resultSet = null;
/*      */ 
/*  866 */     int itemCount = 0;
/*      */ 
/*  868 */     Collection theseItems = new ArrayList();
/*  869 */     owningEVO.setMessageUsers(theseItems);
/*  870 */     owningEVO.setMessageUsersAllItemsLoaded(true);
/*      */     try
/*      */     {
/*  874 */       stmt = getConnection().prepareStatement("select MESSAGE_USER.MESSAGE_ID,MESSAGE_USER.MESSAGE_USER_ID,MESSAGE_USER.USER_ID,MESSAGE_USER.READ,MESSAGE_USER.DELETED,MESSAGE_USER.TYPE,MESSAGE_USER.UPDATED_BY_USER_ID,MESSAGE_USER.UPDATED_TIME,MESSAGE_USER.CREATED_TIME from MESSAGE_USER where 1=1 and MESSAGE_USER.MESSAGE_ID = ? order by  MESSAGE_USER.MESSAGE_ID ,MESSAGE_USER.MESSAGE_USER_ID");
/*      */ 
/*  876 */       int col = 1;
/*  877 */       stmt.setLong(col++, entityPK.getMessageId());
/*      */ 
/*  879 */       resultSet = stmt.executeQuery();
/*      */ 
/*  882 */       while (resultSet.next())
/*      */       {
/*  884 */         itemCount++;
/*  885 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  887 */         theseItems.add(this.mDetails);
/*      */       }
/*      */ 
/*  890 */       if (timer != null) {
/*  891 */         timer.logDebug("bulkGetAll", "items=" + itemCount);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  896 */       throw handleSQLException("select MESSAGE_USER.MESSAGE_ID,MESSAGE_USER.MESSAGE_USER_ID,MESSAGE_USER.USER_ID,MESSAGE_USER.READ,MESSAGE_USER.DELETED,MESSAGE_USER.TYPE,MESSAGE_USER.UPDATED_BY_USER_ID,MESSAGE_USER.UPDATED_TIME,MESSAGE_USER.CREATED_TIME from MESSAGE_USER where 1=1 and MESSAGE_USER.MESSAGE_ID = ? order by  MESSAGE_USER.MESSAGE_ID ,MESSAGE_USER.MESSAGE_USER_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  900 */       closeResultSet(resultSet);
/*  901 */       closeStatement(stmt);
/*  902 */       closeConnection();
/*      */ 
/*  904 */       this.mDetails = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Collection getAll(long selectMessageId, String dependants, Collection currentList)
/*      */   {
/*  929 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  930 */     PreparedStatement stmt = null;
/*  931 */     ResultSet resultSet = null;
/*      */ 
/*  933 */     ArrayList items = new ArrayList();
/*      */     try
/*      */     {
/*  937 */       stmt = getConnection().prepareStatement("select MESSAGE_USER.MESSAGE_ID,MESSAGE_USER.MESSAGE_USER_ID,MESSAGE_USER.USER_ID,MESSAGE_USER.READ,MESSAGE_USER.DELETED,MESSAGE_USER.TYPE,MESSAGE_USER.UPDATED_BY_USER_ID,MESSAGE_USER.UPDATED_TIME,MESSAGE_USER.CREATED_TIME from MESSAGE_USER where    MESSAGE_ID = ? ");
/*      */ 
/*  939 */       int col = 1;
/*  940 */       stmt.setLong(col++, selectMessageId);
/*      */ 
/*  942 */       resultSet = stmt.executeQuery();
/*      */ 
/*  944 */       while (resultSet.next())
/*      */       {
/*  946 */         this.mDetails = getEvoFromJdbc(resultSet);
/*      */ 
/*  949 */         items.add(this.mDetails);
/*      */       }
/*      */ 
/*  952 */       if (currentList != null)
/*      */       {
/*  955 */         ListIterator iter = items.listIterator();
/*  956 */         MessageUserEVO currentEVO = null;
/*  957 */         MessageUserEVO newEVO = null;
/*  958 */         while (iter.hasNext())
/*      */         {
/*  960 */           newEVO = (MessageUserEVO)iter.next();
/*  961 */           Iterator iter2 = currentList.iterator();
/*  962 */           while (iter2.hasNext())
/*      */           {
/*  964 */             currentEVO = (MessageUserEVO)iter2.next();
/*  965 */             if (!currentEVO.getPK().equals(newEVO.getPK()))
/*      */               continue;
/*  967 */             iter.set(currentEVO);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  973 */         Iterator iter2 = currentList.iterator();
/*  974 */         while (iter2.hasNext())
/*      */         {
/*  976 */           currentEVO = (MessageUserEVO)iter2.next();
/*  977 */           if (currentEVO.insertPending()) {
/*  978 */             items.add(currentEVO);
/*      */           }
/*      */         }
/*      */       }
/*  982 */       this.mDetails = null;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  986 */       throw handleSQLException("select MESSAGE_USER.MESSAGE_ID,MESSAGE_USER.MESSAGE_USER_ID,MESSAGE_USER.USER_ID,MESSAGE_USER.READ,MESSAGE_USER.DELETED,MESSAGE_USER.TYPE,MESSAGE_USER.UPDATED_BY_USER_ID,MESSAGE_USER.UPDATED_TIME,MESSAGE_USER.CREATED_TIME from MESSAGE_USER where    MESSAGE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  990 */       closeResultSet(resultSet);
/*  991 */       closeStatement(stmt);
/*  992 */       closeConnection();
/*      */ 
/*  994 */       if (timer != null) {
/*  995 */         timer.logDebug("getAll", " MessageId=" + selectMessageId + " items=" + items.size());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1000 */     return items;
/*      */   }
/*      */ 
/*      */   public MessageUserEVO getDetails(MessageCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1014 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1017 */     if (this.mDetails == null) {
/* 1018 */       doLoad(((MessageUserCK)paramCK).getMessageUserPK());
/*      */     }
/* 1020 */     else if (!this.mDetails.getPK().equals(((MessageUserCK)paramCK).getMessageUserPK())) {
/* 1021 */       doLoad(((MessageUserCK)paramCK).getMessageUserPK());
/*      */     }
/*      */ 
/* 1024 */     MessageUserEVO details = new MessageUserEVO();
/* 1025 */     details = this.mDetails.deepClone();
/*      */ 
/* 1027 */     if (timer != null) {
/* 1028 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1030 */     return details;
/*      */   }
/*      */ 
/*      */   public MessageUserEVO getDetails(MessageCK paramCK, MessageUserEVO paramEVO, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1036 */     MessageUserEVO savedEVO = this.mDetails;
/* 1037 */     this.mDetails = paramEVO;
/* 1038 */     MessageUserEVO newEVO = getDetails(paramCK, dependants);
/* 1039 */     this.mDetails = savedEVO;
/* 1040 */     return newEVO;
/*      */   }
/*      */ 
/*      */   public MessageUserEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1046 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1050 */     MessageUserEVO details = this.mDetails.deepClone();
/*      */ 
/* 1052 */     if (timer != null) {
/* 1053 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1055 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1060 */     return "MessageUser";
/*      */   }
/*      */ 
/*      */   public MessageUserRefImpl getRef(MessageUserPK paramMessageUserPK)
/*      */   {
/* 1065 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1066 */     PreparedStatement stmt = null;
/* 1067 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1070 */       stmt = getConnection().prepareStatement("select 0,MESSAGE.MESSAGE_ID from MESSAGE_USER,MESSAGE where 1=1 and MESSAGE_USER.MESSAGE_ID = ? and MESSAGE_USER.MESSAGE_USER_ID = ? and MESSAGE_USER.MESSAGE_ID = MESSAGE.MESSAGE_ID");
/* 1071 */       int col = 1;
/* 1072 */       stmt.setLong(col++, paramMessageUserPK.getMessageId());
/* 1073 */       stmt.setLong(col++, paramMessageUserPK.getMessageUserId());
/*      */ 
/* 1075 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1077 */       if (!resultSet.next()) {
/* 1078 */         throw new RuntimeException(getEntityName() + " getRef " + paramMessageUserPK + " not found");
/*      */       }
/* 1080 */       col = 2;
/* 1081 */       MessagePK newMessagePK = new MessagePK(resultSet.getLong(col++));
/*      */ 
/* 1085 */       String textMessageUser = "";
/* 1086 */       MessageUserCK ckMessageUser = new MessageUserCK(newMessagePK, paramMessageUserPK);
/*      */ 
/* 1091 */       MessageUserRefImpl localMessageUserRefImpl = new MessageUserRefImpl(ckMessageUser, textMessageUser);
/*      */       return localMessageUserRefImpl;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1096 */       throw handleSQLException(paramMessageUserPK, "select 0,MESSAGE.MESSAGE_ID from MESSAGE_USER,MESSAGE where 1=1 and MESSAGE_USER.MESSAGE_ID = ? and MESSAGE_USER.MESSAGE_USER_ID = ? and MESSAGE_USER.MESSAGE_ID = MESSAGE.MESSAGE_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1100 */       closeResultSet(resultSet);
/* 1101 */       closeStatement(stmt);
/* 1102 */       closeConnection();
/*      */ 
/* 1104 */       if (timer != null)
/* 1105 */         timer.logDebug("getRef", paramMessageUserPK); 
/* 1105 */     }
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.message.MessageUserDAO
 * JD-Core Version:    0.6.0
 */