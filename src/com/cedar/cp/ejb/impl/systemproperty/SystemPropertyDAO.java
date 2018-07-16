/*      */ package com.cedar.cp.ejb.impl.systemproperty;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.systemproperty.SystemPropertyRef;
/*      */ import com.cedar.cp.dto.systemproperty.AllMailPropsELO;
/*      */ import com.cedar.cp.dto.systemproperty.AllSystemPropertysELO;
/*      */ import com.cedar.cp.dto.systemproperty.AllSystemPropertysUncachedELO;
/*      */ import com.cedar.cp.dto.systemproperty.SystemPropertyCK;
/*      */ import com.cedar.cp.dto.systemproperty.SystemPropertyELO;
/*      */ import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
/*      */ import com.cedar.cp.dto.systemproperty.SystemPropertyRefImpl;
/*      */ import com.cedar.cp.dto.systemproperty.WebSystemPropertyELO;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Date;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ public class SystemPropertyDAO extends AbstractDAO
/*      */ {
/*   32 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select SYSTEM_PROPERTY_ID from SYSTEM_PROPERTY where    SYSTEM_PROPERTY_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select SYSTEM_PROPERTY.SYSTEM_PROPERTY_ID,SYSTEM_PROPERTY.PRPRTY,SYSTEM_PROPERTY.VALUE,SYSTEM_PROPERTY.DESCRIPTION,SYSTEM_PROPERTY.VALIDATE_EXP,SYSTEM_PROPERTY.VALIDATE_TXT,SYSTEM_PROPERTY.VERSION_NUM,SYSTEM_PROPERTY.UPDATED_BY_USER_ID,SYSTEM_PROPERTY.UPDATED_TIME,SYSTEM_PROPERTY.CREATED_TIME";
/*      */   protected static final String SQL_LOAD = " from SYSTEM_PROPERTY where    SYSTEM_PROPERTY_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into SYSTEM_PROPERTY ( SYSTEM_PROPERTY_ID,PRPRTY,VALUE,DESCRIPTION,VALIDATE_EXP,VALIDATE_TXT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_UPDATE_SEQ_NUM = "update SYSTEM_PROPERTY_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*      */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from SYSTEM_PROPERTY_SEQ";
/*      */   protected static final String SQL_DUPLICATE_VALUE_CHECK_PROPERTY = "select count(*) from SYSTEM_PROPERTY where    PRPRTY = ? and not(    SYSTEM_PROPERTY_ID = ? )";
/*      */   protected static final String SQL_STORE = "update SYSTEM_PROPERTY set PRPRTY = ?,VALUE = ?,DESCRIPTION = ?,VALIDATE_EXP = ?,VALIDATE_TXT = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SYSTEM_PROPERTY_ID = ? AND VERSION_NUM = ?";
/*      */   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from SYSTEM_PROPERTY where SYSTEM_PROPERTY_ID = ?";
/*      */   protected static final String SQL_REMOVE = "delete from SYSTEM_PROPERTY where    SYSTEM_PROPERTY_ID = ? ";
/*  695 */   protected static String SQL_ALL_SYSTEM_PROPERTYS = "select 0       ,SYSTEM_PROPERTY.SYSTEM_PROPERTY_ID      ,SYSTEM_PROPERTY.PRPRTY      ,SYSTEM_PROPERTY.DESCRIPTION      ,SYSTEM_PROPERTY.VALUE from SYSTEM_PROPERTY where 1=1  order by SYSTEM_PROPERTY.PRPRTY, SYSTEM_PROPERTY.SYSTEM_PROPERTY_ID";
/*      */ 
/*  778 */   protected static String SQL_ALL_SYSTEM_PROPERTYS_UNCACHED = "select 0       ,SYSTEM_PROPERTY.SYSTEM_PROPERTY_ID      ,SYSTEM_PROPERTY.PRPRTY      ,SYSTEM_PROPERTY.VALUE from SYSTEM_PROPERTY where 1=1  order by SYSTEM_PROPERTY.PRPRTY, SYSTEM_PROPERTY.SYSTEM_PROPERTY_ID";
/*      */ 
/*  858 */   protected static String SQL_ALL_MAIL_PROPS = "select 0       ,SYSTEM_PROPERTY.SYSTEM_PROPERTY_ID      ,SYSTEM_PROPERTY.PRPRTY      ,SYSTEM_PROPERTY.VALUE from SYSTEM_PROPERTY where 1=1  and  SYSTEM_PROPERTY.PRPRTY like 'MAIL:%' and value <> ' '";
/*      */ 
/*  938 */   protected static String SQL_SYSTEM_PROPERTY = "select 0       ,SYSTEM_PROPERTY.SYSTEM_PROPERTY_ID      ,SYSTEM_PROPERTY.PRPRTY      ,SYSTEM_PROPERTY.VALUE from SYSTEM_PROPERTY where 1=1  and  SYSTEM_PROPERTY.PRPRTY = ?";
/*      */ 
/* 1022 */   protected static String SQL_WEB_SYSTEM_PROPERTY = "select 0       ,SYSTEM_PROPERTY.SYSTEM_PROPERTY_ID      ,SYSTEM_PROPERTY.PRPRTY      ,SYSTEM_PROPERTY.PRPRTY      ,SYSTEM_PROPERTY.VALUE      ,SYSTEM_PROPERTY.DESCRIPTION      ,SYSTEM_PROPERTY.VALIDATE_EXP      ,SYSTEM_PROPERTY.VALIDATE_TXT from SYSTEM_PROPERTY where 1=1  and  SYSTEM_PROPERTY.PRPRTY = ?";
/*      */   private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from SYSTEM_PROPERTY where   SYSTEM_PROPERTY_ID = ?";
/* 1251 */   protected static String SQL_GET_VALUE = "select VALUE from SYSTEM_PROPERTY\nwhere SYSTEM_PROPERTY.PRPRTY = ?";
/*      */   protected SystemPropertyEVO mDetails;
/*      */ 
/*      */   public SystemPropertyDAO(Connection connection)
/*      */   {
/*   39 */     super(connection);
/*      */   }
/*      */ 
/*      */   public SystemPropertyDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public SystemPropertyDAO(DataSource ds)
/*      */   {
/*   55 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected SystemPropertyPK getPK()
/*      */   {
/*   63 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(SystemPropertyEVO details)
/*      */   {
/*   72 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public SystemPropertyEVO setAndGetDetails(SystemPropertyEVO details, String dependants)
/*      */   {
/*   83 */     setDetails(details);
/*   84 */     generateKeys();
/*   85 */     return this.mDetails.deepClone();
/*      */   }
/*      */ 
/*      */   public SystemPropertyPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*   94 */     doCreate();
/*      */ 
/*   96 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(SystemPropertyPK pk)
/*      */     throws ValidationException
/*      */   {
/*  106 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  115 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  124 */     doRemove();
/*      */   }
/*      */ 
/*      */   public SystemPropertyPK findByPrimaryKey(SystemPropertyPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  133 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  134 */     if (exists(pk_))
/*      */     {
/*  136 */       if (timer != null) {
/*  137 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  139 */       return pk_;
/*      */     }
/*      */ 
/*  142 */     throw new ValidationException(new StringBuilder().append(pk_).append(" not found").toString());
/*      */   }
/*      */ 
/*      */   protected boolean exists(SystemPropertyPK pk)
/*      */   {
/*  160 */     PreparedStatement stmt = null;
/*  161 */     ResultSet resultSet = null;
/*  162 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  166 */       stmt = getConnection().prepareStatement("select SYSTEM_PROPERTY_ID from SYSTEM_PROPERTY where    SYSTEM_PROPERTY_ID = ? ");
/*      */ 
/*  168 */       int col = 1;
/*  169 */       stmt.setInt(col++, pk.getSystemPropertyId());
/*      */ 
/*  171 */       resultSet = stmt.executeQuery();
/*      */ 
/*  173 */       if (!resultSet.next())
/*  174 */         returnValue = false;
/*      */       else
/*  176 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  180 */       throw handleSQLException(pk, "select SYSTEM_PROPERTY_ID from SYSTEM_PROPERTY where    SYSTEM_PROPERTY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  184 */       closeResultSet(resultSet);
/*  185 */       closeStatement(stmt);
/*  186 */       closeConnection();
/*      */     }
/*  188 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private SystemPropertyEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  209 */     int col = 1;
/*  210 */     SystemPropertyEVO evo = new SystemPropertyEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++));
/*      */ 
/*  220 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/*  221 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/*  222 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/*  223 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(SystemPropertyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  228 */     int col = startCol_;
/*  229 */     stmt_.setInt(col++, evo_.getSystemPropertyId());
/*  230 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(SystemPropertyEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  235 */     int col = startCol_;
/*  236 */     stmt_.setString(col++, evo_.getProperty());
/*  237 */     stmt_.setString(col++, evo_.getValue());
/*  238 */     stmt_.setString(col++, evo_.getDescription());
/*  239 */     stmt_.setString(col++, evo_.getValidateExp());
/*  240 */     stmt_.setString(col++, evo_.getValidateTxt());
/*  241 */     stmt_.setInt(col++, evo_.getVersionNum());
/*  242 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/*  243 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/*  244 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/*  245 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(SystemPropertyPK pk)
/*      */     throws ValidationException
/*      */   {
/*  261 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  263 */     PreparedStatement stmt = null;
/*  264 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  268 */       stmt = getConnection().prepareStatement("select SYSTEM_PROPERTY.SYSTEM_PROPERTY_ID,SYSTEM_PROPERTY.PRPRTY,SYSTEM_PROPERTY.VALUE,SYSTEM_PROPERTY.DESCRIPTION,SYSTEM_PROPERTY.VALIDATE_EXP,SYSTEM_PROPERTY.VALIDATE_TXT,SYSTEM_PROPERTY.VERSION_NUM,SYSTEM_PROPERTY.UPDATED_BY_USER_ID,SYSTEM_PROPERTY.UPDATED_TIME,SYSTEM_PROPERTY.CREATED_TIME from SYSTEM_PROPERTY where    SYSTEM_PROPERTY_ID = ? ");
/*      */ 
/*  271 */       int col = 1;
/*  272 */       stmt.setInt(col++, pk.getSystemPropertyId());
/*      */ 
/*  274 */       resultSet = stmt.executeQuery();
/*      */ 
/*  276 */       if (!resultSet.next()) {
/*  277 */         throw new ValidationException(new StringBuilder().append(getEntityName()).append(" select of ").append(pk).append(" not found").toString());
/*      */       }
/*      */ 
/*  280 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  281 */       if (this.mDetails.isModified())
/*  282 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  286 */       throw handleSQLException(pk, "select SYSTEM_PROPERTY.SYSTEM_PROPERTY_ID,SYSTEM_PROPERTY.PRPRTY,SYSTEM_PROPERTY.VALUE,SYSTEM_PROPERTY.DESCRIPTION,SYSTEM_PROPERTY.VALIDATE_EXP,SYSTEM_PROPERTY.VALIDATE_TXT,SYSTEM_PROPERTY.VERSION_NUM,SYSTEM_PROPERTY.UPDATED_BY_USER_ID,SYSTEM_PROPERTY.UPDATED_TIME,SYSTEM_PROPERTY.CREATED_TIME from SYSTEM_PROPERTY where    SYSTEM_PROPERTY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  290 */       closeResultSet(resultSet);
/*  291 */       closeStatement(stmt);
/*  292 */       closeConnection();
/*      */ 
/*  294 */       if (timer != null)
/*  295 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  332 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  333 */     generateKeys();
/*      */ 
/*  335 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  340 */       duplicateValueCheckProperty();
/*      */ 
/*  342 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/*  343 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  344 */       stmt = getConnection().prepareStatement("insert into SYSTEM_PROPERTY ( SYSTEM_PROPERTY_ID,PRPRTY,VALUE,DESCRIPTION,VALIDATE_EXP,VALIDATE_TXT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  347 */       int col = 1;
/*  348 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  349 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  352 */       int resultCount = stmt.executeUpdate();
/*  353 */       if (resultCount != 1)
/*      */       {
/*  355 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" insert failed (").append(this.mDetails.getPK()).append("): resultCount=").append(resultCount).toString());
/*      */       }
/*      */ 
/*  358 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  362 */       throw handleSQLException(this.mDetails.getPK(), "insert into SYSTEM_PROPERTY ( SYSTEM_PROPERTY_ID,PRPRTY,VALUE,DESCRIPTION,VALIDATE_EXP,VALIDATE_TXT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  366 */       closeStatement(stmt);
/*  367 */       closeConnection();
/*      */ 
/*  369 */       if (timer != null)
/*  370 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   public int reserveIds(int insertCount)
/*      */   {
/*  390 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  392 */     PreparedStatement stmt = null;
/*  393 */     ResultSet resultSet = null;
/*  394 */     String sqlString = null;
/*      */     try
/*      */     {
/*  399 */       sqlString = "update SYSTEM_PROPERTY_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*  400 */       stmt = getConnection().prepareStatement("update SYSTEM_PROPERTY_SEQ set SEQ_NUM = SEQ_NUM + ?");
/*  401 */       stmt.setInt(1, insertCount);
/*      */ 
/*  403 */       int resultCount = stmt.executeUpdate();
/*  404 */       if (resultCount != 1) {
/*  405 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" reserveIds: update failed: resultCount=").append(resultCount).toString());
/*      */       }
/*  407 */       closeStatement(stmt);
/*      */ 
/*  410 */       sqlString = "select SEQ_NUM from SYSTEM_PROPERTY_SEQ";
/*  411 */       stmt = getConnection().prepareStatement("select SEQ_NUM from SYSTEM_PROPERTY_SEQ");
/*  412 */       resultSet = stmt.executeQuery();
/*  413 */       if (!resultSet.next())
/*  414 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" reserveIds: select failed").toString());
/*  415 */       int latestKey = resultSet.getInt(1);
/*      */ 
/*  417 */       int i = latestKey - insertCount;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  421 */       throw handleSQLException(sqlString, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  425 */       closeResultSet(resultSet);
/*  426 */       closeStatement(stmt);
/*  427 */       closeConnection();
/*      */ 
/*  429 */       if (timer != null)
/*  430 */         timer.logDebug("reserveIds", new StringBuilder().append("keys=").append(insertCount).toString()); 
/*  430 */     }
/*      */   }
/*      */ 
/*      */   public SystemPropertyPK generateKeys()
/*      */   {
/*  440 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  442 */     int insertCount = this.mDetails.getInsertCount(0);
/*      */ 
/*  445 */     if (insertCount == 0) {
/*  446 */       return this.mDetails.getPK();
/*      */     }
/*  448 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*      */ 
/*  450 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   protected void duplicateValueCheckProperty()
/*      */     throws DuplicateNameValidationException
/*      */   {
/*  463 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  464 */     PreparedStatement stmt = null;
/*  465 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  469 */       stmt = getConnection().prepareStatement("select count(*) from SYSTEM_PROPERTY where    PRPRTY = ? and not(    SYSTEM_PROPERTY_ID = ? )");
/*      */ 
/*  472 */       int col = 1;
/*  473 */       stmt.setString(col++, this.mDetails.getProperty());
/*  474 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  477 */       resultSet = stmt.executeQuery();
/*      */ 
/*  479 */       if (!resultSet.next()) {
/*  480 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" select of ").append(getPK()).append(" not found").toString());
/*      */       }
/*      */ 
/*  484 */       col = 1;
/*  485 */       int count = resultSet.getInt(col++);
/*  486 */       if (count > 0) {
/*  487 */         throw new DuplicateNameValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" Property").toString());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  493 */       throw handleSQLException(getPK(), "select count(*) from SYSTEM_PROPERTY where    PRPRTY = ? and not(    SYSTEM_PROPERTY_ID = ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  497 */       closeResultSet(resultSet);
/*  498 */       closeStatement(stmt);
/*  499 */       closeConnection();
/*      */ 
/*  501 */       if (timer != null)
/*  502 */         timer.logDebug("duplicateValueCheckProperty", "");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  530 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  532 */     generateKeys();
/*      */ 
/*  537 */     PreparedStatement stmt = null;
/*      */ 
/*  539 */     boolean mainChanged = this.mDetails.isModified();
/*  540 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  544 */       if (mainChanged)
/*  545 */         duplicateValueCheckProperty();
/*  546 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  549 */         this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
/*      */ 
/*  552 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/*  553 */         stmt = getConnection().prepareStatement("update SYSTEM_PROPERTY set PRPRTY = ?,VALUE = ?,DESCRIPTION = ?,VALIDATE_EXP = ?,VALIDATE_TXT = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SYSTEM_PROPERTY_ID = ? AND VERSION_NUM = ?");
/*      */ 
/*  556 */         int col = 1;
/*  557 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  558 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  560 */         stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
/*      */ 
/*  563 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  565 */         if (resultCount == 0) {
/*  566 */           checkVersionNum();
/*      */         }
/*  568 */         if (resultCount != 1) {
/*  569 */           throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" update failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
/*      */         }
/*      */ 
/*  572 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  581 */       throw handleSQLException(getPK(), "update SYSTEM_PROPERTY set PRPRTY = ?,VALUE = ?,DESCRIPTION = ?,VALIDATE_EXP = ?,VALIDATE_TXT = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    SYSTEM_PROPERTY_ID = ? AND VERSION_NUM = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  585 */       closeStatement(stmt);
/*  586 */       closeConnection();
/*      */ 
/*  588 */       if ((timer != null) && (
/*  589 */         (mainChanged) || (dependantChanged)))
/*  590 */         timer.logDebug("store", new StringBuilder().append(this.mDetails.getPK()).append("(").append(mainChanged).append(",").append(dependantChanged).append(")").toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void checkVersionNum()
/*      */     throws VersionValidationException
/*      */   {
/*  602 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  603 */     PreparedStatement stmt = null;
/*  604 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  608 */       stmt = getConnection().prepareStatement("select VERSION_NUM from SYSTEM_PROPERTY where SYSTEM_PROPERTY_ID = ?");
/*      */ 
/*  611 */       int col = 1;
/*  612 */       stmt.setInt(col++, this.mDetails.getSystemPropertyId());
/*      */ 
/*  615 */       resultSet = stmt.executeQuery();
/*      */ 
/*  617 */       if (!resultSet.next()) {
/*  618 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" checkVersionNum: select of ").append(getPK()).append(" not found").toString());
/*      */       }
/*      */ 
/*  621 */       col = 1;
/*  622 */       int dbVersionNumber = resultSet.getInt(col++);
/*  623 */       if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
/*  624 */         throw new VersionValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" expected:").append(this.mDetails.getVersionNum() - 1).append(" found:").append(dbVersionNumber).toString());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  630 */       throw handleSQLException(getPK(), "select VERSION_NUM from SYSTEM_PROPERTY where SYSTEM_PROPERTY_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  634 */       closeStatement(stmt);
/*  635 */       closeResultSet(resultSet);
/*      */ 
/*  637 */       if (timer != null)
/*  638 */         timer.logDebug("checkVersionNum", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  655 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  660 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  665 */       stmt = getConnection().prepareStatement("delete from SYSTEM_PROPERTY where    SYSTEM_PROPERTY_ID = ? ");
/*      */ 
/*  668 */       int col = 1;
/*  669 */       stmt.setInt(col++, this.mDetails.getSystemPropertyId());
/*      */ 
/*  671 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  673 */       if (resultCount != 1) {
/*  674 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" delete failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  680 */       throw handleSQLException(getPK(), "delete from SYSTEM_PROPERTY where    SYSTEM_PROPERTY_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  684 */       closeStatement(stmt);
/*  685 */       closeConnection();
/*      */ 
/*  687 */       if (timer != null)
/*  688 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllSystemPropertysELO getAllSystemPropertys()
/*      */   {
/*  719 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  720 */     PreparedStatement stmt = null;
/*  721 */     ResultSet resultSet = null;
/*  722 */     AllSystemPropertysELO results = new AllSystemPropertysELO();
/*      */     try
/*      */     {
/*  725 */       stmt = getConnection().prepareStatement(SQL_ALL_SYSTEM_PROPERTYS);
/*  726 */       int col = 1;
/*  727 */       resultSet = stmt.executeQuery();
/*  728 */       while (resultSet.next())
/*      */       {
/*  730 */         col = 2;
/*      */ 
/*  733 */         SystemPropertyPK pkSystemProperty = new SystemPropertyPK(resultSet.getInt(col++));
/*      */ 
/*  736 */         String textSystemProperty = resultSet.getString(col++);
/*      */ 
/*  740 */         SystemPropertyRefImpl erSystemProperty = new SystemPropertyRefImpl(pkSystemProperty, textSystemProperty);
/*      */ 
/*  745 */         String col1 = resultSet.getString(col++);
/*  746 */         String col2 = resultSet.getString(col++);
/*      */ 
/*  749 */         results.add(erSystemProperty, col1, col2);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  758 */       throw handleSQLException(SQL_ALL_SYSTEM_PROPERTYS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  762 */       closeResultSet(resultSet);
/*  763 */       closeStatement(stmt);
/*  764 */       closeConnection();
/*      */     }
/*      */ 
/*  767 */     if (timer != null) {
/*  768 */       timer.logDebug("getAllSystemPropertys", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  772 */     return results;
/*      */   }
/*      */ 
/*      */   public AllSystemPropertysUncachedELO getAllSystemPropertysUncached()
/*      */   {
/*  801 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  802 */     PreparedStatement stmt = null;
/*  803 */     ResultSet resultSet = null;
/*  804 */     AllSystemPropertysUncachedELO results = new AllSystemPropertysUncachedELO();
/*      */     try
/*      */     {
/*  807 */       stmt = getConnection().prepareStatement(SQL_ALL_SYSTEM_PROPERTYS_UNCACHED);
/*  808 */       int col = 1;
/*  809 */       resultSet = stmt.executeQuery();
/*  810 */       while (resultSet.next())
/*      */       {
/*  812 */         col = 2;
/*      */ 
/*  815 */         SystemPropertyPK pkSystemProperty = new SystemPropertyPK(resultSet.getInt(col++));
/*      */ 
/*  818 */         String textSystemProperty = resultSet.getString(col++);
/*      */ 
/*  822 */         SystemPropertyRefImpl erSystemProperty = new SystemPropertyRefImpl(pkSystemProperty, textSystemProperty);
/*      */ 
/*  827 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  830 */         results.add(erSystemProperty, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  838 */       throw handleSQLException(SQL_ALL_SYSTEM_PROPERTYS_UNCACHED, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  842 */       closeResultSet(resultSet);
/*  843 */       closeStatement(stmt);
/*  844 */       closeConnection();
/*      */     }
/*      */ 
/*  847 */     if (timer != null) {
/*  848 */       timer.logDebug("getAllSystemPropertysUncached", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  852 */     return results;
/*      */   }
/*      */ 
/*      */   public AllMailPropsELO getAllMailProps()
/*      */   {
/*  881 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  882 */     PreparedStatement stmt = null;
/*  883 */     ResultSet resultSet = null;
/*  884 */     AllMailPropsELO results = new AllMailPropsELO();
/*      */     try
/*      */     {
/*  887 */       stmt = getConnection().prepareStatement(SQL_ALL_MAIL_PROPS);
/*  888 */       int col = 1;
/*  889 */       resultSet = stmt.executeQuery();
/*  890 */       while (resultSet.next())
/*      */       {
/*  892 */         col = 2;
/*      */ 
/*  895 */         SystemPropertyPK pkSystemProperty = new SystemPropertyPK(resultSet.getInt(col++));
/*      */ 
/*  898 */         String textSystemProperty = resultSet.getString(col++);
/*      */ 
/*  902 */         SystemPropertyRefImpl erSystemProperty = new SystemPropertyRefImpl(pkSystemProperty, textSystemProperty);
/*      */ 
/*  907 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  910 */         results.add(erSystemProperty, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  918 */       throw handleSQLException(SQL_ALL_MAIL_PROPS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  922 */       closeResultSet(resultSet);
/*  923 */       closeStatement(stmt);
/*  924 */       closeConnection();
/*      */     }
/*      */ 
/*  927 */     if (timer != null) {
/*  928 */       timer.logDebug("getAllMailProps", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/*  932 */     return results;
/*      */   }
/*      */ 
/*      */   public SystemPropertyELO getSystemProperty(String param1)
/*      */   {
/*  963 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  964 */     PreparedStatement stmt = null;
/*  965 */     ResultSet resultSet = null;
/*  966 */     SystemPropertyELO results = new SystemPropertyELO();
/*      */     try
/*      */     {
/*  969 */       stmt = getConnection().prepareStatement(SQL_SYSTEM_PROPERTY);
/*  970 */       int col = 1;
/*  971 */       stmt.setString(col++, param1);
/*  972 */       resultSet = stmt.executeQuery();
/*  973 */       while (resultSet.next())
/*      */       {
/*  975 */         col = 2;
/*      */ 
/*  978 */         SystemPropertyPK pkSystemProperty = new SystemPropertyPK(resultSet.getInt(col++));
/*      */ 
/*  981 */         String textSystemProperty = resultSet.getString(col++);
/*      */ 
/*  985 */         SystemPropertyRefImpl erSystemProperty = new SystemPropertyRefImpl(pkSystemProperty, textSystemProperty);
/*      */ 
/*  990 */         String col1 = resultSet.getString(col++);
/*      */ 
/*  993 */         results.add(erSystemProperty, col1);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1001 */       throw handleSQLException(SQL_SYSTEM_PROPERTY, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1005 */       closeResultSet(resultSet);
/* 1006 */       closeStatement(stmt);
/* 1007 */       closeConnection();
/*      */     }
/*      */ 
/* 1010 */     if (timer != null) {
/* 1011 */       timer.logDebug("getSystemProperty", new StringBuilder().append(" Property=").append(param1).append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/* 1016 */     return results;
/*      */   }
/*      */ 
/*      */   public WebSystemPropertyELO getWebSystemProperty(String param1)
/*      */   {
/* 1051 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1052 */     PreparedStatement stmt = null;
/* 1053 */     ResultSet resultSet = null;
/* 1054 */     WebSystemPropertyELO results = new WebSystemPropertyELO();
/*      */     try
/*      */     {
/* 1057 */       stmt = getConnection().prepareStatement(SQL_WEB_SYSTEM_PROPERTY);
/* 1058 */       int col = 1;
/* 1059 */       stmt.setString(col++, param1);
/* 1060 */       resultSet = stmt.executeQuery();
/* 1061 */       while (resultSet.next())
/*      */       {
/* 1063 */         col = 2;
/*      */ 
/* 1066 */         SystemPropertyPK pkSystemProperty = new SystemPropertyPK(resultSet.getInt(col++));
/*      */ 
/* 1069 */         String textSystemProperty = resultSet.getString(col++);
/*      */ 
/* 1073 */         SystemPropertyRefImpl erSystemProperty = new SystemPropertyRefImpl(pkSystemProperty, textSystemProperty);
/*      */ 
/* 1078 */         String col1 = resultSet.getString(col++);
/* 1079 */         String col2 = resultSet.getString(col++);
/* 1080 */         String col3 = resultSet.getString(col++);
/* 1081 */         String col4 = resultSet.getString(col++);
/* 1082 */         String col5 = resultSet.getString(col++);
/*      */ 
/* 1085 */         results.add(erSystemProperty, col1, col2, col3, col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1097 */       throw handleSQLException(SQL_WEB_SYSTEM_PROPERTY, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1101 */       closeResultSet(resultSet);
/* 1102 */       closeStatement(stmt);
/* 1103 */       closeConnection();
/*      */     }
/*      */ 
/* 1106 */     if (timer != null) {
/* 1107 */       timer.logDebug("getWebSystemProperty", new StringBuilder().append(" Property=").append(param1).append(" items=").append(results.size()).toString());
/*      */     }
/*      */ 
/* 1112 */     return results;
/*      */   }
/*      */ 
/*      */   public SystemPropertyEVO getDetails(SystemPropertyPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1130 */     return getDetails(new SystemPropertyCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public SystemPropertyEVO getDetails(SystemPropertyCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1144 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1147 */     if (this.mDetails == null) {
/* 1148 */       doLoad(paramCK.getSystemPropertyPK());
/*      */     }
/* 1150 */     else if (!this.mDetails.getPK().equals(paramCK.getSystemPropertyPK())) {
/* 1151 */       doLoad(paramCK.getSystemPropertyPK());
/*      */     }
/* 1153 */     else if (!checkIfValid())
/*      */     {
/* 1155 */       this._log.info("getDetails", new StringBuilder().append("[ALERT] SystemPropertyEVO ").append(this.mDetails.getPK()).append(" no longer valid - reloading").toString());
/*      */ 
/* 1157 */       doLoad(paramCK.getSystemPropertyPK());
/*      */     }
/*      */ 
/* 1161 */     SystemPropertyEVO details = new SystemPropertyEVO();
/* 1162 */     details = this.mDetails.deepClone();
/*      */ 
/* 1164 */     if (timer != null) {
/* 1165 */       timer.logDebug("getDetails", new StringBuilder().append(paramCK).append(" ").append(dependants).toString());
/*      */     }
/* 1167 */     return details;
/*      */   }
/*      */ 
/*      */   private boolean checkIfValid()
/*      */   {
/* 1177 */     boolean stillValid = false;
/* 1178 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1179 */     PreparedStatement stmt = null;
/* 1180 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1183 */       stmt = getConnection().prepareStatement("select VERSION_NUM from SYSTEM_PROPERTY where   SYSTEM_PROPERTY_ID = ?");
/* 1184 */       int col = 1;
/* 1185 */       stmt.setInt(col++, this.mDetails.getSystemPropertyId());
/*      */ 
/* 1187 */       resultSet = stmt.executeQuery();
/*      */ 
/* 1189 */       if (!resultSet.next()) {
/* 1190 */         throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" checkIfValid ").append(this.mDetails.getPK()).append(" not found").toString());
/*      */       }
/* 1192 */       col = 1;
/* 1193 */       int dbVersionNum = resultSet.getInt(col++);
/*      */ 
/* 1195 */       if (dbVersionNum == this.mDetails.getVersionNum())
/* 1196 */         stillValid = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1200 */       throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from SYSTEM_PROPERTY where   SYSTEM_PROPERTY_ID = ?", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1204 */       closeResultSet(resultSet);
/* 1205 */       closeStatement(stmt);
/* 1206 */       closeConnection();
/*      */ 
/* 1208 */       if (timer != null) {
/* 1209 */         timer.logDebug("checkIfValid", this.mDetails.getPK());
/*      */       }
/*      */     }
/* 1212 */     return stillValid;
/*      */   }
/*      */ 
/*      */   public SystemPropertyEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 1218 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1220 */     if (!checkIfValid())
/*      */     {
/* 1222 */       this._log.info("getDetails", new StringBuilder().append("SystemProperty ").append(this.mDetails.getPK()).append(" no longer valid - reloading").toString());
/* 1223 */       doLoad(this.mDetails.getPK());
/*      */     }
/*      */ 
/* 1227 */     SystemPropertyEVO details = this.mDetails.deepClone();
/*      */ 
/* 1229 */     if (timer != null) {
/* 1230 */       timer.logDebug("getDetails", new StringBuilder().append(this.mDetails.getPK()).append(" ").append(dependants).toString());
/*      */     }
/* 1232 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 1237 */     return "SystemProperty";
/*      */   }
/*      */ 
/*      */   public SystemPropertyRef getRef(SystemPropertyPK paramSystemPropertyPK)
/*      */     throws ValidationException
/*      */   {
/* 1243 */     SystemPropertyEVO evo = getDetails(paramSystemPropertyPK, "");
/* 1244 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public String getValue(String propertyName)
/*      */   {
/* 1258 */     String result = "";
/* 1259 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1260 */     PreparedStatement stmt = null;
/* 1261 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1264 */       stmt = getConnection().prepareStatement(SQL_GET_VALUE);
/* 1265 */       stmt.setString(1, propertyName);
/* 1266 */       resultSet = stmt.executeQuery();
/* 1267 */       if (resultSet.next())
/*      */       {
/* 1269 */         result = resultSet.getString(1);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1274 */       throw handleSQLException(SQL_GET_VALUE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1278 */       closeResultSet(resultSet);
/* 1279 */       closeStatement(stmt);
/* 1280 */       closeConnection();
/*      */     }
/*      */ 
/* 1283 */     if (timer != null) {
/* 1284 */       timer.logDebug("getValue", new StringBuilder().append("property=").append(propertyName).append(" value=").append(result != null ? result : "not found").toString());
/*      */     }
/*      */ 
/* 1287 */     return result;
/*      */   }
/*      */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO
 * JD-Core Version:    0.6.0
 */