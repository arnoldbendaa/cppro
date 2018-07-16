/*      */ package com.cedar.cp.ejb.impl.message;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.message.MessageRef;
/*      */ import com.cedar.cp.dto.message.AllMessagesELO;
import com.cedar.cp.dto.message.InBoxDetailForUserELO;
/*      */ import com.cedar.cp.dto.message.InBoxForUserELO;
/*      */ import com.cedar.cp.dto.message.MessageAttatchCK;
/*      */ import com.cedar.cp.dto.message.MessageAttatchPK;
/*      */ import com.cedar.cp.dto.message.MessageCK;
/*      */ import com.cedar.cp.dto.message.MessageCountELO;
/*      */ import com.cedar.cp.dto.message.MessageForIdELO;
/*      */ import com.cedar.cp.dto.message.MessageForIdSentItemELO;
/*      */ import com.cedar.cp.dto.message.MessagePK;
/*      */ import com.cedar.cp.dto.message.MessageRefImpl;
/*      */ import com.cedar.cp.dto.message.MessageUserCK;
import com.cedar.cp.dto.message.MessageUserDetailELO;
/*      */ import com.cedar.cp.dto.message.MessageUserPK;
/*      */ import com.cedar.cp.dto.message.MessageUserRefImpl;
/*      */ import com.cedar.cp.dto.message.SentItemsForUserELO;
/*      */ import com.cedar.cp.dto.message.UnreadInBoxForUserELO;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
import oracle.sql.CLOB;
/*      */ 
/*      */ public class MessageDAO extends AbstractDAO
/*      */ {
/*   52 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select MESSAGE_ID from MESSAGE where    MESSAGE_ID = ? ";
/*      */   private static final String SQL_SELECT_LOBS = "select  CONTENT from MESSAGE where    MESSAGE_ID = ? for update";
/*      */   private static final String SQL_SELECT_COLUMNS = "select MESSAGE.CONTENT,MESSAGE.MESSAGE_ID,MESSAGE.SUBJECT,MESSAGE.MESSAGE_TYPE,MESSAGE.VERSION_NUM,MESSAGE.UPDATED_BY_USER_ID,MESSAGE.UPDATED_TIME,MESSAGE.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from MESSAGE where    MESSAGE_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into MESSAGE ( MESSAGE_ID,SUBJECT,CONTENT,MESSAGE_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,empty_clob(),?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update MESSAGE_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from MESSAGE_SEQ";
/*      */   protected static final String SQL_STORE = "update MESSAGE set SUBJECT = ?,MESSAGE_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MESSAGE_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from MESSAGE where MESSAGE_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from MESSAGE where    MESSAGE_ID = ? ";
/*  729 */   protected static String SQL_ALL_MESSAGES = "select 0       ,MESSAGE.MESSAGE_ID      ,MESSAGE.MESSAGE_ID from MESSAGE where 1=1 ";
/*      */ 
/*  808 */   protected static String SQL_IN_BOX_FOR_USER = "select 0       ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,MESSAGE.SUBJECT      ,MESSAGE_USER.READ      ,MESSAGE.CREATED_TIME      ,(select distinct 'Y' from message m, MESSAGE_ATTATCH ma where m.message_id = ma.message_id and m.message_id = message.message_id ) from MESSAGE    ,MESSAGE_USER where 1=1  and  MESSAGE.MESSAGE_ID = MESSAGE_USER.MESSAGE_ID and MESSAGE_USER.USER_ID = ? and MESSAGE_USER.TYPE = 0 and MESSAGE_USER.DELETED <> 'Y'";
/*      */ 
/*  927 */   protected static String SQL_UNREAD_IN_BOX_FOR_USER = "select 0       ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,MESSAGE.SUBJECT      ,MESSAGE_USER.READ      ,MESSAGE.CREATED_TIME      ,(select distinct 'Y' from message m, MESSAGE_ATTATCH ma where m.message_id = ma.message_id and m.message_id = message.message_id  ) from MESSAGE    ,MESSAGE_USER where 1=1  and  MESSAGE.MESSAGE_ID = MESSAGE_USER.MESSAGE_ID and MESSAGE_USER.USER_ID = ? and MESSAGE_USER.TYPE = 0 and MESSAGE_USER.READ <> 'Y' and MESSAGE_USER.DELETED <> 'Y'";
/*      */ 
/* 1046 */   protected static String SQL_SENT_ITEMS_FOR_USER = "select 0       ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,MESSAGE.SUBJECT      ,MESSAGE_USER.READ      ,MESSAGE.CREATED_TIME      ,(select distinct 'Y' from message m, MESSAGE_ATTATCH ma where m.message_id = ma.message_id and m.message_id = message.message_id  ) from MESSAGE    ,MESSAGE_USER where 1=1  and  MESSAGE.MESSAGE_ID = MESSAGE_USER.MESSAGE_ID and MESSAGE_USER.USER_ID = ? and MESSAGE_USER.TYPE = 1 and MESSAGE_USER.DELETED <> 'Y'";
/*      */ 
/* 1165 */   protected static String SQL_MESSAGE_FOR_ID = "select 0       ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,MESSAGE.SUBJECT      ,MESSAGE.CONTENT      ,MESSAGE_USER.READ      ,MESSAGE.CREATED_TIME from MESSAGE    ,MESSAGE_USER where 1=1  and  MESSAGE.MESSAGE_ID = ? and MESSAGE.MESSAGE_ID = MESSAGE_USER.MESSAGE_ID and MESSAGE_USER.USER_ID = ? and MESSAGE_USER.TYPE = 0 and MESSAGE_USER.DELETED <> 'Y'";
/*      */ 
/* 1287 */   protected static String SQL_MESSAGE_FOR_ID_SENT_ITEM = "select 0       ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,MESSAGE.SUBJECT      ,MESSAGE.CONTENT      ,MESSAGE_USER.READ      ,MESSAGE.CREATED_TIME from MESSAGE    ,MESSAGE_USER where 1=1  and  MESSAGE.MESSAGE_ID = ? and MESSAGE.MESSAGE_ID = MESSAGE_USER.MESSAGE_ID and MESSAGE_USER.USER_ID = ? and MESSAGE_USER.TYPE = 1 and MESSAGE_USER.DELETED <> 'Y'";
/*      */ 
/* 1409 */   protected static String SQL_MESSAGE_COUNT = "select 0       ,MESSAGE.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_ID      ,MESSAGE_USER.MESSAGE_USER_ID      ,MESSAGE.MESSAGE_ID from MESSAGE    ,MESSAGE_USER where 1=1  and  MESSAGE.MESSAGE_ID = ? and MESSAGE.MESSAGE_ID = MESSAGE_USER.MESSAGE_ID and MESSAGE_USER.DELETED <> 'Y'";
/*      */ 
/* 1508 */   private static String[][] SQL_DELETE_CHILDREN = { { "MESSAGE_USER", "delete from MESSAGE_USER where     MESSAGE_USER.MESSAGE_ID = ? " }, { "MESSAGE_ATTATCH", "delete from MESSAGE_ATTATCH where     MESSAGE_ATTATCH.MESSAGE_ID = ? " } };
/*      */ 
/* 1522 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*      */ 
/* 1526 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and MESSAGE.MESSAGE_ID = ?)";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from MESSAGE where   MESSAGE_ID = ?";
/*      */   public static final String SQL_GET_MESSAGE_USER_REF = "select 0,MESSAGE.MESSAGE_ID from MESSAGE_USER,MESSAGE where 1=1 and MESSAGE_USER.MESSAGE_ID = ? and MESSAGE_USER.MESSAGE_USER_ID = ? and MESSAGE_USER.MESSAGE_ID = MESSAGE.MESSAGE_ID";
/*      */   public static final String SQL_GET_MESSAGE_ATTATCH_REF = "select 0,MESSAGE.MESSAGE_ID from MESSAGE_ATTATCH,MESSAGE where 1=1 and MESSAGE_ATTATCH.MESSAGE_ID = ? and MESSAGE_ATTATCH.MESSAGE_ATTATCH_ID = ? and MESSAGE_ATTATCH.MESSAGE_ID = MESSAGE.MESSAGE_ID";
/* 1973 */   protected final String sSummaryUnread = "select  * from ( select MESSAGE.MESSAGE_ID col1 ,MESSAGE_USER.MESSAGE_ID col2 ,MESSAGE_USER.MESSAGE_USER_ID col3 ,MESSAGE.MESSAGE_ID col4 ,MESSAGE_USER.MESSAGE_USER_ID col5 ,MESSAGE.SUBJECT col6 ,MESSAGE_USER.READ col7 ,MESSAGE.CREATED_TIME col8 ,(select distinct 'Y' from message m , MESSAGE_ATTATCH ma where m.message_id = ma.message_id and m.message_id = message.message_id ) col9 , rank() over ( order by MESSAGE.CREATED_TIME desc ) rk from MESSAGE , MESSAGE_USER where MESSAGE.MESSAGE_ID = MESSAGE_USER.MESSAGE_ID and MESSAGE_USER.USER_ID = ? and MESSAGE_USER.TYPE = 0 and MESSAGE_USER.READ <> 'Y' and MESSAGE_USER.DELETED <> 'Y'      )  where rk < 6";
/*      */   protected MessageUserDAO mMessageUserDAO;
/*      */   protected MessageAttatchDAO mMessageAttatchDAO;
/*      */   protected MessageEVO mDetails;
/*      */   private CLOB mContentClob;
/*      */ 
/*      */   public MessageDAO(Connection connection)
/*      */   {
/*   59 */     super(connection);
/*      */   }
/*      */ 
/*      */   public MessageDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public MessageDAO(DataSource ds)
/*      */   {
/*   75 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected MessagePK getPK()
/*      */   {
/*   83 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(MessageEVO details)
/*      */   {
/*   92 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public MessageEVO setAndGetDetails(MessageEVO details, String dependants)
/*      */   {
/*  103 */     setDetails(details);
/*  104 */     generateKeys();
/*  105 */     getDependants(this.mDetails, dependants);
/*  106 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public MessagePK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  115 */     doCreate();
/*      */ 
/*  117 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(MessagePK pk)
/*      */     throws ValidationException
/*      */   {
/*  127 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  136 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  145 */     doRemove();
/*      */   }
/*      */ 
/*      */   public MessagePK findByPrimaryKey(MessagePK pk_)
/*      */     throws ValidationException
/*      */   {
/*  154 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  155 */     if (exists(pk_))
/*      */     {
/*  157 */       if (timer != null) {
/*  158 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  160 */       return pk_;
/*      */     }
/*      */ 
/*  163 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(MessagePK pk)
/*      */   {
/*  181 */     PreparedStatement stmt = null;
/*  182 */     ResultSet resultSet = null;
/*  183 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  187 */       stmt = getConnection().prepareStatement("select MESSAGE_ID from MESSAGE where    MESSAGE_ID = ? ");
/*      */ 
/*  189 */       int col = 1;
/*  190 */       stmt.setLong(col++, pk.getMessageId());
/*      */ 
/*  192 */       resultSet = stmt.executeQuery();
/*      */ 
/*  194 */       if (!resultSet.next())
/*  195 */         returnValue = false;
/*      */       else
/*  197 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  201 */       throw handleSQLException(pk, "select MESSAGE_ID from MESSAGE where    MESSAGE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  205 */       closeResultSet(resultSet);
/*  206 */       closeStatement(stmt);
/*  207 */       closeConnection();
/*      */     }
/*  209 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private void selectLobs(MessageEVO evo_)
/*      */   {
/*  223 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  225 */     PreparedStatement stmt = null;
/*  226 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  230 */       stmt = getConnection().prepareStatement("select  CONTENT from MESSAGE where    MESSAGE_ID = ? for update");
/*      */ 
/*  232 */       putEvoKeysToJdbc(evo_, stmt, 1);
/*      */ 
/*  234 */       resultSet = stmt.executeQuery();
/*      */ 
/*  236 */       int col = 1;
/*  237 */       while (resultSet.next())
/*      */       {
/*  239 */         this.mContentClob = ((CLOB)resultSet.getClob(col++));
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  244 */       throw handleSQLException(evo_.getPK(), "select  CONTENT from MESSAGE where    MESSAGE_ID = ? for update", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  248 */       closeResultSet(resultSet);
/*  249 */       closeStatement(stmt);
/*      */ 
/*  251 */       if (timer != null)
/*  252 */         timer.logDebug("selectLobs", evo_.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void putLobs(MessageEVO evo_) throws SQLException
/*      */   {
/*  258 */     updateClob(this.mContentClob, evo_.getContent());
/*      */   }
/*      */ 
/*      */   private MessageEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  275 */     int col = 1;
/*  276 */     this.mContentClob = ((CLOB)resultSet_.getClob(col++));
/*  277 */     MessageEVO evo = new MessageEVO(resultSet_.getLong(col++), resultSet_.getString(col++), clobToString(this.mContentClob), resultSet_.getInt(col++), resultSet_.getInt(col++), null, null);
/*      */ 
/*  287 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  288 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  289 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  290 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(MessageEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  295 */     int col = startCol_;
/*  296 */     stmt_.setLong(col++, evo_.getMessageId());
/*  297 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(MessageEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  302 */     int col = startCol_;
/*  303 */     stmt_.setString(col++, evo_.getSubject());
/*      */ 
/*  305 */     stmt_.setInt(col++, evo_.getMessageType());
/*  306 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  307 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  308 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  309 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  310 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(MessagePK pk)
/*      */     throws ValidationException
/*      */   {
/*  326 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  328 */     PreparedStatement stmt = null;
/*  329 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  333 */       stmt = getConnection().prepareStatement("select MESSAGE.CONTENT,MESSAGE.MESSAGE_ID,MESSAGE.SUBJECT,MESSAGE.MESSAGE_TYPE,MESSAGE.VERSION_NUM,MESSAGE.UPDATED_BY_USER_ID,MESSAGE.UPDATED_TIME,MESSAGE.CREATED_TIME from MESSAGE where    MESSAGE_ID = ? ");
/*      */ 
/*  336 */       int col = 1;
/*  337 */       stmt.setLong(col++, pk.getMessageId());
/*      */ 
/*  339 */       resultSet = stmt.executeQuery();
/*      */ 
/*  341 */       if (!resultSet.next()) {
/*  342 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  345 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  346 */       if (this.mDetails.isModified())
/*  347 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  351 */       throw handleSQLException(pk, "select MESSAGE.CONTENT,MESSAGE.MESSAGE_ID,MESSAGE.SUBJECT,MESSAGE.MESSAGE_TYPE,MESSAGE.VERSION_NUM,MESSAGE.UPDATED_BY_USER_ID,MESSAGE.UPDATED_TIME,MESSAGE.CREATED_TIME from MESSAGE where    MESSAGE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  355 */       closeResultSet(resultSet);
/*  356 */       closeStatement(stmt);
/*  357 */       closeConnection();
/*      */ 
/*  359 */       if (timer != null)
/*  360 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  393 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  394 */     generateKeys();
/*      */ 
/*  396 */     this.mDetails.postCreateInit();
/*      */ 
/*  398 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  403 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  404 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  405 */       stmt = getConnection().prepareStatement("insert into MESSAGE ( MESSAGE_ID,SUBJECT,CONTENT,MESSAGE_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,empty_clob(),?,?,?,?,?)");
/*      */ 
/*  408 */       int col = 1;
/*  409 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  410 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  413 */       int resultCount = stmt.executeUpdate();
/*  414 */       if (resultCount != 1)
/*      */       {
/*  416 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  421 */       selectLobs(this.mDetails);
/*  422 */       this._log.debug("doCreate", "calling putLobs");
/*  423 */       putLobs(this.mDetails);
/*      */ 
/*  425 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  429 */       throw handleSQLException(this.mDetails.getPK(), "insert into MESSAGE ( MESSAGE_ID,SUBJECT,CONTENT,MESSAGE_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,empty_clob(),?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  433 */       closeStatement(stmt);
/*  434 */       closeConnection();
/*      */ 
/*  436 */       if (timer != null) {
/*  437 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  443 */       getMessageUserDAO().update(this.mDetails.getMessageUsersMap());
/*      */ 
/*  445 */       getMessageAttatchDAO().update(this.mDetails.getMessageAttatchmentsMap());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  451 */       throw new RuntimeException("unexpected exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  471 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  473 */     PreparedStatement stmt = null;
/*  474 */     ResultSet resultSet = null;
/*  475 */     String sqlString = null;
/*      */     try
/*      */     {
/*  480 */       sqlString = "update MESSAGE_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  481 */       stmt = getConnection().prepareStatement("update MESSAGE_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  482 */       stmt.setInt(1, insertCount);
/*      */ 
/*  484 */       int resultCount = stmt.executeUpdate();
/*  485 */       if (resultCount != 1) {
/*  486 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*      */       }
/*  488 */       closeStatement(stmt);
/*      */ 
/*  491 */       sqlString = "select SEQ_NUM from MESSAGE_SEQ";
/*  492 */       stmt = getConnection().prepareStatement("select SEQ_NUM from MESSAGE_SEQ");
/*  493 */       resultSet = stmt.executeQuery();
/*  494 */       if (!resultSet.next())
/*  495 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/*  496 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  498 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  502 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  506 */       closeResultSet(resultSet);
/*  507 */       closeStatement(stmt);
/*  508 */       closeConnection();
/*      */ 
/*  510 */       if (timer != null)
/*  511 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/*  511 */     }
/*      */   }
/*      */ 
/*      */   public MessagePK generateKeys()
/*      */   {
/*  521 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  523 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  526 */     if (insertCount == 0) {
/*  527 */       return this.mDetails.getPK();
/*      */     }
/*  529 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  531 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  555 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  557 */     generateKeys();
/*      */ 
/*  562 */     PreparedStatement stmt = null;
/*      */ 
/*  564 */     boolean mainChanged = this.mDetails.isModified();
/*  565 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  569 */       if (getMessageUserDAO().update(this.mDetails.getMessageUsersMap())) {
/*  570 */         dependantChanged = true;
/*      */       }
/*      */ 
/*  573 */       if (getMessageAttatchDAO().update(this.mDetails.getMessageAttatchmentsMap())) {
/*  574 */         dependantChanged = true;
/*      */       }
/*  576 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  579 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  582 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  583 */         stmt = getConnection().prepareStatement("update MESSAGE set SUBJECT = ?,MESSAGE_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MESSAGE_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  585 */         selectLobs(this.mDetails);
/*  586 */         putLobs(this.mDetails);
/*      */ 
/*  589 */         int col = 1;
/*  590 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  591 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  593 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  596 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  598 */         if (resultCount == 0) {
/*  599 */           checkVersionNum();
/*      */         }
/*  601 */         if (resultCount != 1) {
/*  602 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  605 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  614 */       throw handleSQLException(getPK(), "update MESSAGE set SUBJECT = ?,MESSAGE_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MESSAGE_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  618 */       closeStatement(stmt);
/*  619 */       closeConnection();
/*      */ 
/*  621 */       if ((timer != null) && (
/*  622 */         (mainChanged) || (dependantChanged)))
/*  623 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  635 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  636 */     PreparedStatement stmt = null;
/*  637 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  641 */       stmt = getConnection().prepareStatement("select VERSION_NUM from MESSAGE where MESSAGE_ID = ?");
/*      */ 
/*  644 */       int col = 1;
/*  645 */       stmt.setLong(col++, this.mDetails.getMessageId());
/*      */ 
/*  648 */       resultSet = stmt.executeQuery();
/*      */ 
/*  650 */       if (!resultSet.next()) {
/*  651 */         throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
/*      */       }
/*      */ 
/*  654 */       col = 1;
/*  655 */       int dbVersionNumber = resultSet.getInt(col++);
/*  656 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  657 */         throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  663 */       throw handleSQLException(getPK(), "select VERSION_NUM from MESSAGE where MESSAGE_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  667 */       closeStatement(stmt);
/*  668 */       closeResultSet(resultSet);
/*      */ 
/*  670 */       if (timer != null)
/*  671 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  688 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  689 */     deleteDependants(this.mDetails.getPK());
/*      */ 
/*  694 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  699 */       stmt = getConnection().prepareStatement("delete from MESSAGE where    MESSAGE_ID = ? ");
/*      */ 
/*  702 */       int col = 1;
/*  703 */       stmt.setLong(col++, this.mDetails.getMessageId());
/*      */ 
/*  705 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  707 */       if (resultCount != 1) {
/*  708 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  714 */       throw handleSQLException(getPK(), "delete from MESSAGE where    MESSAGE_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  718 */       closeStatement(stmt);
/*  719 */       closeConnection();
/*      */ 
/*  721 */       if (timer != null)
/*  722 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllMessagesELO getAllMessages()
/*      */   {
/*  751 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  752 */     PreparedStatement stmt = null;
/*  753 */     ResultSet resultSet = null;
/*  754 */     AllMessagesELO results = new AllMessagesELO();
/*      */     try
/*      */     {
/*  757 */       stmt = getConnection().prepareStatement(SQL_ALL_MESSAGES);
/*  758 */       int col = 1;
/*  759 */       resultSet = stmt.executeQuery();
/*  760 */       while (resultSet.next())
/*      */       {
/*  762 */         col = 2;
/*      */ 
/*  765 */         MessagePK pkMessage = new MessagePK(resultSet.getLong(col++));
/*      */ 
/*  768 */         String textMessage = "";
/*      */ 
/*  772 */         MessageRefImpl erMessage = new MessageRefImpl(pkMessage, textMessage);
/*      */ 
/*  777 */         long col1 = resultSet.getLong(col++);
/*      */ 
/*  780 */         results.add(erMessage, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  788 */       throw handleSQLException(SQL_ALL_MESSAGES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  792 */       closeResultSet(resultSet);
/*  793 */       closeStatement(stmt);
/*  794 */       closeConnection();
/*      */     }
/*      */ 
/*  797 */     if (timer != null) {
/*  798 */       timer.logDebug("getAllMessages", " items=" + results.size());
/*      */     }
/*      */ 
/*  802 */     return results;
/*      */   }
/*      */ 
/*      */   public InBoxForUserELO getInBoxForUser(String param1)
/*      */   {
/*  841 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  842 */     PreparedStatement stmt = null;
/*  843 */     ResultSet resultSet = null;
/*  844 */     InBoxForUserELO results = new InBoxForUserELO();
/*      */     try
/*      */     {
/*  847 */       stmt = getConnection().prepareStatement(SQL_IN_BOX_FOR_USER);
/*  848 */       int col = 1;
/*  849 */       stmt.setString(col++, param1);
/*  850 */       resultSet = stmt.executeQuery();
/*  851 */       while (resultSet.next())
/*      */       {
/*  853 */         col = 2;
/*      */ 
/*  856 */         MessagePK pkMessage = new MessagePK(resultSet.getLong(col++));
/*      */ 
/*  859 */         String textMessage = "";
/*      */ 
/*  862 */         MessageUserPK pkMessageUser = new MessageUserPK(resultSet.getLong(col++), resultSet.getLong(col++));
/*      */ 
/*  866 */         String textMessageUser = "";
/*      */ 
/*  869 */         MessageRefImpl erMessage = new MessageRefImpl(pkMessage, textMessage);
/*      */ 
/*  875 */         MessageUserRefImpl erMessageUser = new MessageUserRefImpl(pkMessageUser, textMessageUser);
/*      */ 
/*  880 */         long col1 = resultSet.getLong(col++);
/*  881 */         long col2 = resultSet.getLong(col++);
/*  882 */         String col3 = resultSet.getString(col++);
/*  883 */         String col4 = resultSet.getString(col++);
/*  884 */         if (resultSet.wasNull())
/*  885 */           col4 = "";
/*  886 */         Timestamp col5 = resultSet.getTimestamp(col++);
/*  887 */         String col6 = resultSet.getString(col++);
/*  888 */         if (resultSet.wasNull()) {
/*  889 */           col6 = "";
/*      */         }
/*      */ 
/*  892 */         results.add(erMessage, erMessageUser, col1, col2, col3, col4.equals("Y"), col5, col6.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  906 */       throw handleSQLException(SQL_IN_BOX_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  910 */       closeResultSet(resultSet);
/*  911 */       closeStatement(stmt);
/*  912 */       closeConnection();
/*      */     }
/*      */ 
/*  915 */     if (timer != null) {
/*  916 */       timer.logDebug("getInBoxForUser", " UserId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  921 */     return results;
/*      */   }
/*      */ 
/*      */   public UnreadInBoxForUserELO getUnreadInBoxForUser(String param1)
/*      */   {
/*  960 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  961 */     PreparedStatement stmt = null;
/*  962 */     ResultSet resultSet = null;
/*  963 */     UnreadInBoxForUserELO results = new UnreadInBoxForUserELO();
/*      */     try
/*      */     {
/*  966 */       stmt = getConnection().prepareStatement(SQL_UNREAD_IN_BOX_FOR_USER);
/*  967 */       int col = 1;
/*  968 */       stmt.setString(col++, param1);
/*  969 */       resultSet = stmt.executeQuery();
/*  970 */       while (resultSet.next())
/*      */       {
/*  972 */         col = 2;
/*      */ 
/*  975 */         MessagePK pkMessage = new MessagePK(resultSet.getLong(col++));
/*      */ 
/*  978 */         String textMessage = "";
/*      */ 
/*  981 */         MessageUserPK pkMessageUser = new MessageUserPK(resultSet.getLong(col++), resultSet.getLong(col++));
/*      */ 
/*  985 */         String textMessageUser = "";
/*      */ 
/*  988 */         MessageRefImpl erMessage = new MessageRefImpl(pkMessage, textMessage);
/*      */ 
/*  994 */         MessageUserRefImpl erMessageUser = new MessageUserRefImpl(pkMessageUser, textMessageUser);
/*      */ 
/*  999 */         long col1 = resultSet.getLong(col++);
/* 1000 */         long col2 = resultSet.getLong(col++);
/* 1001 */         String col3 = resultSet.getString(col++);
/* 1002 */         String col4 = resultSet.getString(col++);
/* 1003 */         if (resultSet.wasNull())
/* 1004 */           col4 = "";
/* 1005 */         Timestamp col5 = resultSet.getTimestamp(col++);
/* 1006 */         String col6 = resultSet.getString(col++);
/* 1007 */         if (resultSet.wasNull()) {
/* 1008 */           col6 = "";
/*      */         }
/*      */ 
/* 1011 */         results.add(erMessage, erMessageUser, col1, col2, col3, col4.equals("Y"), col5, col6.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1025 */       throw handleSQLException(SQL_UNREAD_IN_BOX_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1029 */       closeResultSet(resultSet);
/* 1030 */       closeStatement(stmt);
/* 1031 */       closeConnection();
/*      */     }
/*      */ 
/* 1034 */     if (timer != null) {
/* 1035 */       timer.logDebug("getUnreadInBoxForUser", " UserId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1040 */     return results;
/*      */   }
/*      */ 
/*      */   public SentItemsForUserELO getSentItemsForUser(String param1)
/*      */   {
/* 1079 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1080 */     PreparedStatement stmt = null;
/* 1081 */     ResultSet resultSet = null;
/* 1082 */     SentItemsForUserELO results = new SentItemsForUserELO();
/*      */     try
/*      */     {
/* 1085 */       stmt = getConnection().prepareStatement(SQL_SENT_ITEMS_FOR_USER);
/* 1086 */       int col = 1;
/* 1087 */       stmt.setString(col++, param1);
/* 1088 */       resultSet = stmt.executeQuery();
/* 1089 */       while (resultSet.next())
/*      */       {
/* 1091 */         col = 2;
/*      */ 
/* 1094 */         MessagePK pkMessage = new MessagePK(resultSet.getLong(col++));
/*      */ 
/* 1097 */         String textMessage = "";
/*      */ 
/* 1100 */         MessageUserPK pkMessageUser = new MessageUserPK(resultSet.getLong(col++), resultSet.getLong(col++));
/*      */ 
/* 1104 */         String textMessageUser = "";
/*      */ 
/* 1107 */         MessageRefImpl erMessage = new MessageRefImpl(pkMessage, textMessage);
/*      */ 
/* 1113 */         MessageUserRefImpl erMessageUser = new MessageUserRefImpl(pkMessageUser, textMessageUser);
/*      */ 
/* 1118 */         long col1 = resultSet.getLong(col++);
/* 1119 */         long col2 = resultSet.getLong(col++);
/* 1120 */         String col3 = resultSet.getString(col++);
/* 1121 */         String col4 = resultSet.getString(col++);
/* 1122 */         if (resultSet.wasNull())
/* 1123 */           col4 = "";
/* 1124 */         Timestamp col5 = resultSet.getTimestamp(col++);
/* 1125 */         String col6 = resultSet.getString(col++);
/* 1126 */         if (resultSet.wasNull()) {
/* 1127 */           col6 = "";
/*      */         }
/*      */ 
/* 1130 */         results.add(erMessage, erMessageUser, col1, col2, col3, col4.equals("Y"), col5, col6.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1144 */       throw handleSQLException(SQL_SENT_ITEMS_FOR_USER, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1148 */       closeResultSet(resultSet);
/* 1149 */       closeStatement(stmt);
/* 1150 */       closeConnection();
/*      */     }
/*      */ 
/* 1153 */     if (timer != null) {
/* 1154 */       timer.logDebug("getSentItemsForUser", " UserId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1159 */     return results;
/*      */   }
/*      */ 
/*      */   public MessageForIdELO getMessageForId(long param1, String param2)
/*      */   {
/* 1200 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1201 */     PreparedStatement stmt = null;
/* 1202 */     ResultSet resultSet = null;
/* 1203 */     MessageForIdELO results = new MessageForIdELO();
/*      */     try
/*      */     {
/* 1206 */       stmt = getConnection().prepareStatement(SQL_MESSAGE_FOR_ID);
/* 1207 */       int col = 1;
/* 1208 */       stmt.setLong(col++, param1);
/* 1209 */       stmt.setString(col++, param2);
/* 1210 */       resultSet = stmt.executeQuery();
/* 1211 */       while (resultSet.next())
/*      */       {
/* 1213 */         col = 2;
/*      */ 
/* 1216 */         MessagePK pkMessage = new MessagePK(resultSet.getLong(col++));
/*      */ 
/* 1219 */         String textMessage = "";
/*      */ 
/* 1222 */         MessageUserPK pkMessageUser = new MessageUserPK(resultSet.getLong(col++), resultSet.getLong(col++));
/*      */ 
/* 1226 */         String textMessageUser = "";
/*      */ 
/* 1229 */         MessageRefImpl erMessage = new MessageRefImpl(pkMessage, textMessage);
/*      */ 
/* 1235 */         MessageUserRefImpl erMessageUser = new MessageUserRefImpl(pkMessageUser, textMessageUser);
/*      */ 
/* 1240 */         long col1 = resultSet.getLong(col++);
/* 1241 */         long col2 = resultSet.getLong(col++);
/* 1242 */         String col3 = resultSet.getString(col++);
/* 1243 */         CLOB col4Clob = (CLOB)resultSet.getClob(col++);
/* 1244 */         String col4 = clobToString(col4Clob);
/* 1245 */         String col5 = resultSet.getString(col++);
/* 1246 */         if (resultSet.wasNull())
/* 1247 */           col5 = "";
/* 1248 */         Timestamp col6 = resultSet.getTimestamp(col++);
/*      */ 
/* 1251 */         results.add(erMessage, erMessageUser, col1, col2, col3, col4, col5.equals("Y"), col6);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1265 */       throw handleSQLException(SQL_MESSAGE_FOR_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1269 */       closeResultSet(resultSet);
/* 1270 */       closeStatement(stmt);
/* 1271 */       closeConnection();
/*      */     }
/*      */ 
/* 1274 */     if (timer != null) {
/* 1275 */       timer.logDebug("getMessageForId", " MessageId=" + param1 + ",UserId=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1281 */     return results;
/*      */   }
/*      */ 
/*      */   public MessageForIdSentItemELO getMessageForIdSentItem(long param1, String param2)
/*      */   {
/* 1322 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1323 */     PreparedStatement stmt = null;
/* 1324 */     ResultSet resultSet = null;
/* 1325 */     MessageForIdSentItemELO results = new MessageForIdSentItemELO();
/*      */     try
/*      */     {
/* 1328 */       stmt = getConnection().prepareStatement(SQL_MESSAGE_FOR_ID_SENT_ITEM);
/* 1329 */       int col = 1;
/* 1330 */       stmt.setLong(col++, param1);
/* 1331 */       stmt.setString(col++, param2);
/* 1332 */       resultSet = stmt.executeQuery();
/* 1333 */       while (resultSet.next())
/*      */       {
/* 1335 */         col = 2;
/*      */ 
/* 1338 */         MessagePK pkMessage = new MessagePK(resultSet.getLong(col++));
/*      */ 
/* 1341 */         String textMessage = "";
/*      */ 
/* 1344 */         MessageUserPK pkMessageUser = new MessageUserPK(resultSet.getLong(col++), resultSet.getLong(col++));
/*      */ 
/* 1348 */         String textMessageUser = "";
/*      */ 
/* 1351 */         MessageRefImpl erMessage = new MessageRefImpl(pkMessage, textMessage);
/*      */ 
/* 1357 */         MessageUserRefImpl erMessageUser = new MessageUserRefImpl(pkMessageUser, textMessageUser);
/*      */ 
/* 1362 */         long col1 = resultSet.getLong(col++);
/* 1363 */         long col2 = resultSet.getLong(col++);
/* 1364 */         String col3 = resultSet.getString(col++);
/* 1365 */         CLOB col4Clob = (CLOB)resultSet.getClob(col++);
/* 1366 */         String col4 = clobToString(col4Clob);
/* 1367 */         String col5 = resultSet.getString(col++);
/* 1368 */         if (resultSet.wasNull())
/* 1369 */           col5 = "";
/* 1370 */         Timestamp col6 = resultSet.getTimestamp(col++);
/*      */ 
/* 1373 */         results.add(erMessage, erMessageUser, col1, col2, col3, col4, col5.equals("Y"), col6);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1387 */       throw handleSQLException(SQL_MESSAGE_FOR_ID_SENT_ITEM, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1391 */       closeResultSet(resultSet);
/* 1392 */       closeStatement(stmt);
/* 1393 */       closeConnection();
/*      */     }
/*      */ 
/* 1396 */     if (timer != null) {
/* 1397 */       timer.logDebug("getMessageForIdSentItem", " MessageId=" + param1 + ",UserId=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1403 */     return results;
/*      */   }
/*      */ 
/*      */   public MessageCountELO getMessageCount(long param1)
/*      */   {
/* 1437 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1438 */     PreparedStatement stmt = null;
/* 1439 */     ResultSet resultSet = null;
/* 1440 */     MessageCountELO results = new MessageCountELO();
/*      */     try
/*      */     {
/* 1443 */       stmt = getConnection().prepareStatement(SQL_MESSAGE_COUNT);
/* 1444 */       int col = 1;
/* 1445 */       stmt.setLong(col++, param1);
/* 1446 */       resultSet = stmt.executeQuery();
/* 1447 */       while (resultSet.next())
/*      */       {
/* 1449 */         col = 2;
/*      */ 
/* 1452 */         MessagePK pkMessage = new MessagePK(resultSet.getLong(col++));
/*      */ 
/* 1455 */         String textMessage = "";
/*      */ 
/* 1458 */         MessageUserPK pkMessageUser = new MessageUserPK(resultSet.getLong(col++), resultSet.getLong(col++));
/*      */ 
/* 1462 */         String textMessageUser = "";
/*      */ 
/* 1465 */         MessageRefImpl erMessage = new MessageRefImpl(pkMessage, textMessage);
/*      */ 
/* 1471 */         MessageUserRefImpl erMessageUser = new MessageUserRefImpl(pkMessageUser, textMessageUser);
/*      */ 
/* 1476 */         long col1 = resultSet.getLong(col++);
/*      */ 
/* 1479 */         results.add(erMessage, erMessageUser, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1488 */       throw handleSQLException(SQL_MESSAGE_COUNT, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1492 */       closeResultSet(resultSet);
/* 1493 */       closeStatement(stmt);
/* 1494 */       closeConnection();
/*      */     }
/*      */ 
/* 1497 */     if (timer != null) {
/* 1498 */       timer.logDebug("getMessageCount", " MessageId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1503 */     return results;
/*      */   }
/*      */ 
/*      */   private void deleteDependants(MessagePK pk)
/*      */   {
/* 1535 */     Set emptyStrings = Collections.emptySet();
/* 1536 */     deleteDependants(pk, emptyStrings);
/*      */   }
/*      */ 
/*      */   private void deleteDependants(MessagePK pk, Set<String> exclusionTables)
/*      */   {
/* 1542 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*      */     {
/* 1544 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*      */         continue;
/* 1546 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1548 */       PreparedStatement stmt = null;
/*      */ 
/* 1550 */       int resultCount = 0;
/* 1551 */       String s = null;
/*      */       try
/*      */       {
/* 1554 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*      */ 
/* 1556 */         if (this._log.isDebugEnabled()) {
/* 1557 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1559 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1562 */         int col = 1;
/* 1563 */         stmt.setLong(col++, pk.getMessageId());
/*      */ 
/* 1566 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1570 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1574 */         closeStatement(stmt);
/* 1575 */         closeConnection();
/*      */ 
/* 1577 */         if (timer != null) {
/* 1578 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*      */         }
/*      */       }
/*      */     }
/* 1582 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*      */     {
/* 1584 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*      */         continue;
/* 1586 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1588 */       PreparedStatement stmt = null;
/*      */ 
/* 1590 */       int resultCount = 0;
/* 1591 */       String s = null;
/*      */       try
/*      */       {
/* 1594 */         s = SQL_DELETE_CHILDREN[i][1];
/*      */ 
/* 1596 */         if (this._log.isDebugEnabled()) {
/* 1597 */           this._log.debug("deleteDependants", s);
/*      */         }
/* 1599 */         stmt = getConnection().prepareStatement(s);
/*      */ 
/* 1602 */         int col = 1;
/* 1603 */         stmt.setLong(col++, pk.getMessageId());
/*      */ 
/* 1606 */         resultCount = stmt.executeUpdate();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1610 */         throw handleSQLException(pk, s, sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 1614 */         closeStatement(stmt);
/* 1615 */         closeConnection();
/*      */ 
/* 1617 */         if (timer != null)
/* 1618 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public MessageEVO getDetails(MessagePK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1638 */     return getDetails(new MessageCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public MessageEVO getDetails(MessageCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1657 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1660 */     if (this.mDetails == null) {
/* 1661 */       doLoad(paramCK.getMessagePK());
/*      */     }
/* 1663 */     else if (!this.mDetails.getPK().equals(paramCK.getMessagePK())) {
/* 1664 */       doLoad(paramCK.getMessagePK());
/*      */     }
/* 1666 */     else if (!checkIfValid())
/*      */     {
/* 1668 */       this._log.info("getDetails", "[ALERT] MessageEVO " + this.mDetails.getPK() + " no longer valid - reloading");
/*      */ 
/* 1670 */       doLoad(paramCK.getMessagePK());
/*      */     }
/*      */ 
/* 1686 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isMessageUsersAllItemsLoaded()))
/*      */     {
/* 1691 */       this.mDetails.setMessageUsers(getMessageUserDAO().getAll(this.mDetails.getMessageId(), dependants, this.mDetails.getMessageUsers()));
/*      */ 
/* 1698 */       this.mDetails.setMessageUsersAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1702 */     if ((dependants.indexOf("<1>") > -1) && (!this.mDetails.isMessageAttatchmentsAllItemsLoaded()))
/*      */     {
/* 1707 */       this.mDetails.setMessageAttatchments(getMessageAttatchDAO().getAll(this.mDetails.getMessageId(), dependants, this.mDetails.getMessageAttatchments()));
/*      */ 
/* 1714 */       this.mDetails.setMessageAttatchmentsAllItemsLoaded(true);
/*      */     }
/*      */ 
/* 1717 */     if ((paramCK instanceof MessageUserCK))
/*      */     {
/* 1719 */       if (this.mDetails.getMessageUsers() == null) {
/* 1720 */         this.mDetails.loadMessageUsersItem(getMessageUserDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1723 */         MessageUserPK pk = ((MessageUserCK)paramCK).getMessageUserPK();
/* 1724 */         MessageUserEVO evo = this.mDetails.getMessageUsersItem(pk);
/* 1725 */         if (evo == null) {
/* 1726 */           this.mDetails.loadMessageUsersItem(getMessageUserDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/* 1730 */     else if ((paramCK instanceof MessageAttatchCK))
/*      */     {
/* 1732 */       if (this.mDetails.getMessageAttatchments() == null) {
/* 1733 */         this.mDetails.loadMessageAttatchmentsItem(getMessageAttatchDAO().getDetails(paramCK, dependants));
/*      */       }
/*      */       else {
/* 1736 */         MessageAttatchPK pk = ((MessageAttatchCK)paramCK).getMessageAttatchPK();
/* 1737 */         MessageAttatchEVO evo = this.mDetails.getMessageAttatchmentsItem(pk);
/* 1738 */         if (evo == null) {
/* 1739 */           this.mDetails.loadMessageAttatchmentsItem(getMessageAttatchDAO().getDetails(paramCK, dependants));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1744 */     MessageEVO details = new MessageEVO();
/* 1745 */     details = this.mDetails.deepClone();
/*      */ 
/* 1747 */     if (timer != null) {
/* 1748 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 1750 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1760 */     boolean stillValid = false;
/* 1761 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1762 */     PreparedStatement stmt = null;
/* 1763 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1766 */       stmt = getConnection().prepareStatement("select VERSION_NUM from MESSAGE where   MESSAGE_ID = ?");
/* 1767 */       int col = 1;
/* 1768 */       stmt.setLong(col++, this.mDetails.getMessageId());
/*      */ 
/* 1770 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1772 */       if (!resultSet.next()) {
/* 1773 */         throw new RuntimeException(getEntityName() + " checkIfValid " + this.mDetails.getPK() + " not found");
/*      */       }
/* 1775 */       col = 1;
/* 1776 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1778 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1779 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1783 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from MESSAGE where   MESSAGE_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1787 */       closeResultSet(resultSet);
/* 1788 */       closeStatement(stmt);
/* 1789 */       closeConnection();
/*      */ 
/* 1791 */       if (timer != null) {
/* 1792 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1795 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public MessageEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1801 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1803 */     if (!checkIfValid())
/*      */     {
/* 1805 */       this._log.info("getDetails", "Message " + this.mDetails.getPK() + " no longer valid - reloading");
/* 1806 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1810 */     getDependants(this.mDetails, dependants);
/*      */ 
/* 1813 */     MessageEVO details = this.mDetails.deepClone();
/*      */ 
/* 1815 */     if (timer != null) {
/* 1816 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 1818 */     return details;
/*      */   }
/*      */ 
/*      */   protected MessageUserDAO getMessageUserDAO()
/*      */   {
/* 1827 */     if (this.mMessageUserDAO == null)
/*      */     {
/* 1829 */       if (this.mDataSource != null)
/* 1830 */         this.mMessageUserDAO = new MessageUserDAO(this.mDataSource);
/*      */       else {
/* 1832 */         this.mMessageUserDAO = new MessageUserDAO(getConnection());
/*      */       }
/*      */     }
/* 1835 */     return this.mMessageUserDAO;
/*      */   }
/*      */ 
/*      */   protected MessageAttatchDAO getMessageAttatchDAO()
/*      */   {
/* 1844 */     if (this.mMessageAttatchDAO == null)
/*      */     {
/* 1846 */       if (this.mDataSource != null)
/* 1847 */         this.mMessageAttatchDAO = new MessageAttatchDAO(this.mDataSource);
/*      */       else {
/* 1849 */         this.mMessageAttatchDAO = new MessageAttatchDAO(getConnection());
/*      */       }
/*      */     }
/* 1852 */     return this.mMessageAttatchDAO;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1857 */     return "Message";
/*      */   }
/*      */ 
/*      */   public MessageRef getRef(MessagePK paramMessagePK)
/*      */     throws ValidationException
/*      */   {
/* 1863 */     MessageEVO evo = getDetails(paramMessagePK, "");
/* 1864 */     return evo.getEntityRef("");
/*      */   }
/*      */ 
/*      */   public void getDependants(Collection c, String dependants)
/*      */   {
/* 1903 */     if (c == null)
/* 1904 */       return;
/* 1905 */     Iterator iter = c.iterator();
/* 1906 */     while (iter.hasNext())
/*      */     {
/* 1908 */       MessageEVO evo = (MessageEVO)iter.next();
/* 1909 */       getDependants(evo, dependants);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getDependants(MessageEVO evo, String dependants)
/*      */   {
/* 1923 */     if (evo.getMessageId() < 1L) {
/* 1924 */       return;
/*      */     }
/*      */ 
/* 1936 */     if (dependants.indexOf("<0>") > -1)
/*      */     {
/* 1939 */       if (!evo.isMessageUsersAllItemsLoaded())
/*      */       {
/* 1941 */         evo.setMessageUsers(getMessageUserDAO().getAll(evo.getMessageId(), dependants, evo.getMessageUsers()));
/*      */ 
/* 1948 */         evo.setMessageUsersAllItemsLoaded(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1953 */     if (dependants.indexOf("<1>") > -1)
/*      */     {
/* 1956 */       if (!evo.isMessageAttatchmentsAllItemsLoaded())
/*      */       {
/* 1958 */         evo.setMessageAttatchments(getMessageAttatchDAO().getAll(evo.getMessageId(), dependants, evo.getMessageAttatchments()));
/*      */ 
/* 1965 */         evo.setMessageAttatchmentsAllItemsLoaded(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public UnreadInBoxForUserELO getSummaryUnreadMessagesForUser(String param1)
/*      */   {
/* 2001 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2002 */     PreparedStatement stmt = null;
/* 2003 */     ResultSet resultSet = null;
/* 2004 */     UnreadInBoxForUserELO results = new UnreadInBoxForUserELO();
/*      */     try
/*      */     {
/* 2007 */       stmt = getConnection().prepareStatement("select  * from ( select MESSAGE.MESSAGE_ID col1 ,MESSAGE_USER.MESSAGE_ID col2 ,MESSAGE_USER.MESSAGE_USER_ID col3 ,MESSAGE.MESSAGE_ID col4 ,MESSAGE_USER.MESSAGE_USER_ID col5 ,MESSAGE.SUBJECT col6 ,MESSAGE_USER.READ col7 ,MESSAGE.CREATED_TIME col8 ,(select distinct 'Y' from message m , MESSAGE_ATTATCH ma where m.message_id = ma.message_id and m.message_id = message.message_id ) col9 , rank() over ( order by MESSAGE.CREATED_TIME desc ) rk from MESSAGE , MESSAGE_USER where MESSAGE.MESSAGE_ID = MESSAGE_USER.MESSAGE_ID and MESSAGE_USER.USER_ID = ? and MESSAGE_USER.TYPE = 0 and MESSAGE_USER.READ <> 'Y' and MESSAGE_USER.DELETED <> 'Y'      )  where rk < 6");
/* 2008 */       int col = 1;
/* 2009 */       stmt.setString(col++, param1);
/* 2010 */       resultSet = stmt.executeQuery();
/* 2011 */       while (resultSet.next())
/*      */       {
/* 2013 */         col = 1;
/*      */ 
/* 2016 */         MessagePK pkMessage = new MessagePK(resultSet.getLong(col++));
/*      */ 
/* 2019 */         String textMessage = "";
/*      */ 
/* 2022 */         MessageUserPK pkMessageUser = new MessageUserPK(resultSet.getLong(col++), resultSet.getLong(col++));
/*      */ 
/* 2026 */         String textMessageUser = "";
/*      */ 
/* 2029 */         MessageRefImpl erMessage = new MessageRefImpl(pkMessage, textMessage);
/*      */ 
/* 2035 */         MessageUserRefImpl erMessageUser = new MessageUserRefImpl(pkMessageUser, textMessageUser);
/*      */ 
/* 2040 */         long col1 = resultSet.getLong(col++);
/* 2041 */         long col2 = resultSet.getLong(col++);
/* 2042 */         String col3 = resultSet.getString(col++);
/* 2043 */         String col4 = resultSet.getString(col++);
/* 2044 */         if (resultSet.wasNull())
/* 2045 */           col4 = "";
/* 2046 */         Timestamp col5 = resultSet.getTimestamp(col++);
/* 2047 */         String col6 = resultSet.getString(col++);
/* 2048 */         if (resultSet.wasNull()) {
/* 2049 */           col6 = "";
/*      */         }
/*      */ 
/* 2052 */         results.add(erMessage, erMessageUser, col1, col2, col3, col4.equals("Y"), col5, col6.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2066 */       throw handleSQLException("select  * from ( select MESSAGE.MESSAGE_ID col1 ,MESSAGE_USER.MESSAGE_ID col2 ,MESSAGE_USER.MESSAGE_USER_ID col3 ,MESSAGE.MESSAGE_ID col4 ,MESSAGE_USER.MESSAGE_USER_ID col5 ,MESSAGE.SUBJECT col6 ,MESSAGE_USER.READ col7 ,MESSAGE.CREATED_TIME col8 ,(select distinct 'Y' from message m , MESSAGE_ATTATCH ma where m.message_id = ma.message_id and m.message_id = message.message_id ) col9 , rank() over ( order by MESSAGE.CREATED_TIME desc ) rk from MESSAGE , MESSAGE_USER where MESSAGE.MESSAGE_ID = MESSAGE_USER.MESSAGE_ID and MESSAGE_USER.USER_ID = ? and MESSAGE_USER.TYPE = 0 and MESSAGE_USER.READ <> 'Y' and MESSAGE_USER.DELETED <> 'Y'      )  where rk < 6", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2070 */       closeResultSet(resultSet);
/* 2071 */       closeStatement(stmt);
/* 2072 */       closeConnection();
/*      */     }
/*      */ 
/* 2075 */     if (timer != null) {
/* 2076 */       timer.logDebug("getUnreadInBoxForUser", " UserId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 2081 */     return results;
/*      */   }
/*      */ 

	public EntityList getMailDetailForUser(String userName, int type, int from, int to) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		ResultSet fromUserResultSet = null;
		ResultSet toUserResultSet = null;
		InBoxDetailForUserELO results = new InBoxDetailForUserELO();
		int rowIndex = 0;
		try {
			stmt = getConnection().prepareStatement("select \nm.message_id, \nm.subject, \nm.created_time,\n(select distinct 'Y' from MESSAGE_ATTATCH ma where ma.message_id = m.message_id ) attach, \nmu.MESSAGE_USER_ID, \nmu.READ, \ncursor( \n  select u.name, u.full_name \n  from message_user mufrom, usr u \n  where mufrom.message_id = mu.message_id \n  and mufrom.type = 1 \n  and mufrom.user_id = u.name \n) message_from_user, \ncursor( \n  select u.name, u.full_name \n  from message_user muto, usr u \n  where muto.message_id = mu.message_id \n  and muto.type = 0 \n  and muto.user_id = u.name \n) message_to_user \nfrom message m, MESSAGE_USER mu \nwhere m.message_id = mu.message_id \nand mu.USER_ID = ? and mu.TYPE = ? and mu.DELETED <> 'Y' \norder by m.created_time desc");
			int col = 1;
			stmt.setString(col++, userName);
			stmt.setInt(col++, type);
			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				rowIndex++;
				boolean doAdd = true;
				if (rowIndex < from) {
					doAdd = false;
				} else if (rowIndex > to) {
					break;
				}

				if (doAdd) {
					col = 1;

					long messaageId = resultSet.getLong(col++);
					String subject = resultSet.getString(col++);
					Timestamp createdTime = resultSet.getTimestamp(col++);
					String attach = resultSet.getString(col++);
					if (resultSet.wasNull()) {
						attach = "";
					}
					long messageUserId = resultSet.getLong(col++);

					String read = resultSet.getString(col++);
					if (resultSet.wasNull()) {
						read = "";
					}
					fromUserResultSet = (ResultSet) resultSet.getObject(col++);
					MessageUserDetailELO fromUser = new MessageUserDetailELO();
					while (fromUserResultSet.next()) {
						int userCol = 1;
						String name = fromUserResultSet.getString(userCol++);
						String fullName = fromUserResultSet.getString(userCol++);

						fromUser.add(name, fullName);
					}
					fromUserResultSet.close();

					toUserResultSet = (ResultSet) resultSet.getObject(col++);
					MessageUserDetailELO toUser = new MessageUserDetailELO();
					while (toUserResultSet.next()) {
						int userCol = 1;
						String name = toUserResultSet.getString(userCol++);
						String fullName = toUserResultSet.getString(userCol++);

						toUser.add(name, fullName);
					}
					toUserResultSet.close();

					results.add(messaageId, subject, createdTime, attach.equals("Y"), messageUserId, read.equals("Y"), fromUser, toUser);
				} else {
					fromUserResultSet = (ResultSet) resultSet.getObject(7);
					fromUserResultSet.close();
					toUserResultSet = (ResultSet) resultSet.getObject(8);
					toUserResultSet.close();
				}
			}
		} catch (SQLException sqle) {
			throw handleSQLException("select  * from ( select MESSAGE.MESSAGE_ID col1 ,MESSAGE_USER.MESSAGE_ID col2 ,MESSAGE_USER.MESSAGE_USER_ID col3 ,MESSAGE.MESSAGE_ID col4 ,MESSAGE_USER.MESSAGE_USER_ID col5 ,MESSAGE.SUBJECT col6 ,MESSAGE_USER.READ col7 ,MESSAGE.CREATED_TIME col8 ,(select distinct 'Y' from message m , MESSAGE_ATTATCH ma where m.message_id = ma.message_id and m.message_id = message.message_id ) col9 , rank() over ( order by MESSAGE.CREATED_TIME desc ) rk from MESSAGE , MESSAGE_USER where MESSAGE.MESSAGE_ID = MESSAGE_USER.MESSAGE_ID and MESSAGE_USER.USER_ID = ? and MESSAGE_USER.TYPE = 0 and MESSAGE_USER.READ <> 'Y' and MESSAGE_USER.DELETED <> 'Y'      )  where rk < 6", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}

		if (timer != null) {
			timer.logDebug("getMailDetailForUser", " UserId=" + userName + " type=" + type + " items=" + results.size() + " processed =" + rowIndex);
		}

		return results;
	}
	
	public void emptyFolder(int type, String userId) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement stmt = null;
		int rowsUpdated;
		try {
			stmt = getConnection().prepareStatement("update message_user set deleted = 'Y' where user_id = ? and type = ?");
			int col = 1;

			stmt.setString(col++, userId);
			stmt.setInt(col++, type);
			rowsUpdated = stmt.executeUpdate();
		} catch (SQLException sqle) {
			throw handleSQLException("select  * from ( select MESSAGE.MESSAGE_ID col1 ,MESSAGE_USER.MESSAGE_ID col2 ,MESSAGE_USER.MESSAGE_USER_ID col3 ,MESSAGE.MESSAGE_ID col4 ,MESSAGE_USER.MESSAGE_USER_ID col5 ,MESSAGE.SUBJECT col6 ,MESSAGE_USER.READ col7 ,MESSAGE.CREATED_TIME col8 ,(select distinct 'Y' from message m , MESSAGE_ATTATCH ma where m.message_id = ma.message_id and m.message_id = message.message_id ) col9 , rank() over ( order by MESSAGE.CREATED_TIME desc ) rk from MESSAGE , MESSAGE_USER where MESSAGE.MESSAGE_ID = MESSAGE_USER.MESSAGE_ID and MESSAGE_USER.USER_ID = ? and MESSAGE_USER.TYPE = 0 and MESSAGE_USER.READ <> 'Y' and MESSAGE_USER.DELETED <> 'Y'      )  where rk < 6", sqle);
		} finally {
			closeStatement(stmt);
			closeConnection();
		}

		if (timer != null)
			timer.logDebug("emptyFolder", " UserId=" + userId + " type=" + type + " updated=" + rowsUpdated);
	}

}
/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.message.MessageDAO
 * JD-Core Version:    0.6.0
 */