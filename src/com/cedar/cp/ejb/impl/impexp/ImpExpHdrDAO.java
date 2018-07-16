/*     */ package com.cedar.cp.ejb.impl.impexp;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
/*     */ import com.cedar.cp.api.impexp.ImpExpHdrRef;
/*     */ import com.cedar.cp.dto.impexp.AllImpExpHdrsELO;
/*     */ import com.cedar.cp.dto.impexp.ImpExpHdrCK;
/*     */ import com.cedar.cp.dto.impexp.ImpExpHdrPK;
/*     */ import com.cedar.cp.dto.impexp.ImpExpHdrRefImpl;
/*     */ import com.cedar.cp.dto.impexp.ImpExpRowCK;
/*     */ import com.cedar.cp.dto.impexp.ImpExpRowPK;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class ImpExpHdrDAO extends AbstractDAO
/*     */ {
/*  32 */   Log _log = new Log(getClass());
/*     */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select BATCH_ID from IMP_EXP_HDR where    BATCH_ID = ? ";
/*     */   private static final String SQL_SELECT_COLUMNS = "select IMP_EXP_HDR.BATCH_ID,IMP_EXP_HDR.BATCH_TS,IMP_EXP_HDR.FINANCE_CUBE_ID,IMP_EXP_HDR.BATCH_TYPE";
/*     */   protected static final String SQL_LOAD = " from IMP_EXP_HDR where    BATCH_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into IMP_EXP_HDR ( BATCH_ID,BATCH_TS,FINANCE_CUBE_ID,BATCH_TYPE) values ( ?,?,?,?)";
/*     */   protected static final String SQL_UPDATE_SEQ_NUM = "update IMP_EXP_HDR_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*     */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from IMP_EXP_HDR_SEQ";
/*     */   protected static final String SQL_STORE = "update IMP_EXP_HDR set BATCH_TS = ?,FINANCE_CUBE_ID = ?,BATCH_TYPE = ? where    BATCH_ID = ? ";
/*     */   protected static final String SQL_REMOVE = "delete from IMP_EXP_HDR where    BATCH_ID = ? ";
/* 562 */   protected static String SQL_ALL_IMP_EXP_HDRS = "select 0       ,IMP_EXP_HDR.BATCH_ID      ,IMP_EXP_HDR.BATCH_TS      ,IMP_EXP_HDR.FINANCE_CUBE_ID      ,IMP_EXP_HDR.BATCH_TYPE from IMP_EXP_HDR where 1=1 ";
/*     */ 
/* 646 */   private static String[][] SQL_DELETE_CHILDREN = { { "IMP_EXP_ROW", "delete from IMP_EXP_ROW where     IMP_EXP_ROW.BATCH_ID = ? " } };
/*     */ 
/* 655 */   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
/*     */ 
/* 659 */   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and IMP_EXP_HDR.BATCH_ID = ?)";
/*     */   public static final String SQL_GET_IMP_EXP_ROW_REF = "select 0,IMP_EXP_HDR.BATCH_ID from IMP_EXP_ROW,IMP_EXP_HDR where 1=1 and IMP_EXP_ROW.BATCH_ID = ? and IMP_EXP_ROW.ROW_NO = ? and IMP_EXP_ROW.BATCH_ID = IMP_EXP_HDR.BATCH_ID";
/*     */   protected ImpExpRowDAO mImpExpRowDAO;
/*     */   protected ImpExpHdrEVO mDetails;
/*     */ 
/*     */   public ImpExpHdrDAO(Connection connection)
/*     */   {
/*  39 */     super(connection);
/*     */   }
/*     */ 
/*     */   public ImpExpHdrDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ImpExpHdrDAO(DataSource ds)
/*     */   {
/*  55 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected ImpExpHdrPK getPK()
/*     */   {
/*  63 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(ImpExpHdrEVO details)
/*     */   {
/*  72 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   public ImpExpHdrEVO setAndGetDetails(ImpExpHdrEVO details, String dependants)
/*     */   {
/*  83 */     setDetails(details);
/*  84 */     generateKeys();
/*  85 */     getDependants(this.mDetails, dependants);
/*  86 */     return this.mDetails.deepClone();
/*     */   }
/*     */ 
/*     */   public ImpExpHdrPK create()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/*  95 */     doCreate();
/*     */ 
/*  97 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void load(ImpExpHdrPK pk)
/*     */     throws ValidationException
/*     */   {
/* 107 */     doLoad(pk);
/*     */   }
/*     */ 
/*     */   public void store()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 116 */     doStore();
/*     */   }
/*     */ 
/*     */   public void remove()
/*     */   {
/* 125 */     doRemove();
/*     */   }
/*     */ 
/*     */   public ImpExpHdrPK findByPrimaryKey(ImpExpHdrPK pk_)
/*     */     throws ValidationException
/*     */   {
/* 134 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 135 */     if (exists(pk_))
/*     */     {
/* 137 */       if (timer != null) {
/* 138 */         timer.logDebug("findByPrimaryKey", pk_);
/*     */       }
/* 140 */       return pk_;
/*     */     }
/*     */ 
/* 143 */     throw new ValidationException(pk_ + " not found");
/*     */   }
/*     */ 
/*     */   protected boolean exists(ImpExpHdrPK pk)
/*     */   {
/* 161 */     PreparedStatement stmt = null;
/* 162 */     ResultSet resultSet = null;
/* 163 */     boolean returnValue = false;
/*     */     try
/*     */     {
/* 167 */       stmt = getConnection().prepareStatement("select BATCH_ID from IMP_EXP_HDR where    BATCH_ID = ? ");
/*     */ 
/* 169 */       int col = 1;
/* 170 */       stmt.setInt(col++, pk.getBatchId());
/*     */ 
/* 172 */       resultSet = stmt.executeQuery();
/*     */ 
/* 174 */       if (!resultSet.next())
/* 175 */         returnValue = false;
/*     */       else
/* 177 */         returnValue = true;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 181 */       throw handleSQLException(pk, "select BATCH_ID from IMP_EXP_HDR where    BATCH_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 185 */       closeResultSet(resultSet);
/* 186 */       closeStatement(stmt);
/* 187 */       closeConnection();
/*     */     }
/* 189 */     return returnValue;
/*     */   }
/*     */ 
/*     */   private ImpExpHdrEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 204 */     int col = 1;
/* 205 */     ImpExpHdrEVO evo = new ImpExpHdrEVO(resultSet_.getInt(col++), resultSet_.getTimestamp(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), null);
/*     */ 
/* 213 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(ImpExpHdrEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 218 */     int col = startCol_;
/* 219 */     stmt_.setInt(col++, evo_.getBatchId());
/* 220 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(ImpExpHdrEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 225 */     int col = startCol_;
/* 226 */     stmt_.setTimestamp(col++, evo_.getBatchTs());
/* 227 */     stmt_.setInt(col++, evo_.getFinanceCubeId());
/* 228 */     stmt_.setInt(col++, evo_.getBatchType());
/* 229 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(ImpExpHdrPK pk)
/*     */     throws ValidationException
/*     */   {
/* 245 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 247 */     PreparedStatement stmt = null;
/* 248 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 252 */       stmt = getConnection().prepareStatement("select IMP_EXP_HDR.BATCH_ID,IMP_EXP_HDR.BATCH_TS,IMP_EXP_HDR.FINANCE_CUBE_ID,IMP_EXP_HDR.BATCH_TYPE from IMP_EXP_HDR where    BATCH_ID = ? ");
/*     */ 
/* 255 */       int col = 1;
/* 256 */       stmt.setInt(col++, pk.getBatchId());
/*     */ 
/* 258 */       resultSet = stmt.executeQuery();
/*     */ 
/* 260 */       if (!resultSet.next()) {
/* 261 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 264 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 265 */       if (this.mDetails.isModified())
/* 266 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 270 */       throw handleSQLException(pk, "select IMP_EXP_HDR.BATCH_ID,IMP_EXP_HDR.BATCH_TS,IMP_EXP_HDR.FINANCE_CUBE_ID,IMP_EXP_HDR.BATCH_TYPE from IMP_EXP_HDR where    BATCH_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 274 */       closeResultSet(resultSet);
/* 275 */       closeStatement(stmt);
/* 276 */       closeConnection();
/*     */ 
/* 278 */       if (timer != null)
/* 279 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 304 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 305 */     generateKeys();
/*     */ 
/* 307 */     this.mDetails.postCreateInit();
/*     */ 
/* 309 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 313 */       stmt = getConnection().prepareStatement("insert into IMP_EXP_HDR ( BATCH_ID,BATCH_TS,FINANCE_CUBE_ID,BATCH_TYPE) values ( ?,?,?,?)");
/*     */ 
/* 316 */       int col = 1;
/* 317 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 318 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 321 */       int resultCount = stmt.executeUpdate();
/* 322 */       if (resultCount != 1)
/*     */       {
/* 324 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 327 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 331 */       throw handleSQLException(this.mDetails.getPK(), "insert into IMP_EXP_HDR ( BATCH_ID,BATCH_TS,FINANCE_CUBE_ID,BATCH_TYPE) values ( ?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 335 */       closeStatement(stmt);
/* 336 */       closeConnection();
/*     */ 
/* 338 */       if (timer != null) {
/* 339 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 345 */       getImpExpRowDAO().update(this.mDetails.getIMP_EXP_ROWMap());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 351 */       throw new RuntimeException("unexpected exception", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int reserveIds(int insertCount)
/*     */   {
/* 371 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 373 */     PreparedStatement stmt = null;
/* 374 */     ResultSet resultSet = null;
/* 375 */     String sqlString = null;
/*     */     try
/*     */     {
/* 380 */       sqlString = "update IMP_EXP_HDR_SEQ set SEQ_NUM = SEQ_NUM + ?";
/* 381 */       stmt = getConnection().prepareStatement("update IMP_EXP_HDR_SEQ set SEQ_NUM = SEQ_NUM + ?");
/* 382 */       stmt.setInt(1, insertCount);
/*     */ 
/* 384 */       int resultCount = stmt.executeUpdate();
/* 385 */       if (resultCount != 1) {
/* 386 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*     */       }
/* 388 */       closeStatement(stmt);
/*     */ 
/* 391 */       sqlString = "select SEQ_NUM from IMP_EXP_HDR_SEQ";
/* 392 */       stmt = getConnection().prepareStatement("select SEQ_NUM from IMP_EXP_HDR_SEQ");
/* 393 */       resultSet = stmt.executeQuery();
/* 394 */       if (!resultSet.next())
/* 395 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/* 396 */       int latestKey = resultSet.getInt(1);
/*     */ 
/* 398 */       int i = latestKey - insertCount;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 402 */       throw handleSQLException(sqlString, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 406 */       closeResultSet(resultSet);
/* 407 */       closeStatement(stmt);
/* 408 */       closeConnection();
/*     */ 
/* 410 */       if (timer != null)
/* 411 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/* 411 */     }
/*     */   }
/*     */ 
/*     */   public ImpExpHdrPK generateKeys()
/*     */   {
/* 421 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 423 */     int insertCount = this.mDetails.getInsertCount(0);
/*     */ 
/* 426 */     if (insertCount == 0) {
/* 427 */       return this.mDetails.getPK();
/*     */     }
/* 429 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*     */ 
/* 431 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 451 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 453 */     generateKeys();
/*     */ 
/* 458 */     PreparedStatement stmt = null;
/*     */ 
/* 460 */     boolean mainChanged = this.mDetails.isModified();
/* 461 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 465 */       if (getImpExpRowDAO().update(this.mDetails.getIMP_EXP_ROWMap())) {
/* 466 */         dependantChanged = true;
/*     */       }
/* 468 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 470 */         stmt = getConnection().prepareStatement("update IMP_EXP_HDR set BATCH_TS = ?,FINANCE_CUBE_ID = ?,BATCH_TYPE = ? where    BATCH_ID = ? ");
/*     */ 
/* 473 */         int col = 1;
/* 474 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 475 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 478 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 481 */         if (resultCount != 1) {
/* 482 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 485 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 494 */       throw handleSQLException(getPK(), "update IMP_EXP_HDR set BATCH_TS = ?,FINANCE_CUBE_ID = ?,BATCH_TYPE = ? where    BATCH_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 498 */       closeStatement(stmt);
/* 499 */       closeConnection();
/*     */ 
/* 501 */       if ((timer != null) && (
/* 502 */         (mainChanged) || (dependantChanged)))
/* 503 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doRemove()
/*     */   {
/* 521 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 522 */     deleteDependants(this.mDetails.getPK());
/*     */ 
/* 527 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 532 */       stmt = getConnection().prepareStatement("delete from IMP_EXP_HDR where    BATCH_ID = ? ");
/*     */ 
/* 535 */       int col = 1;
/* 536 */       stmt.setInt(col++, this.mDetails.getBatchId());
/*     */ 
/* 538 */       int resultCount = stmt.executeUpdate();
/*     */ 
/* 540 */       if (resultCount != 1) {
/* 541 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 547 */       throw handleSQLException(getPK(), "delete from IMP_EXP_HDR where    BATCH_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 551 */       closeStatement(stmt);
/* 552 */       closeConnection();
/*     */ 
/* 554 */       if (timer != null)
/* 555 */         timer.logDebug("remove", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllImpExpHdrsELO getAllImpExpHdrs()
/*     */   {
/* 586 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 587 */     PreparedStatement stmt = null;
/* 588 */     ResultSet resultSet = null;
/* 589 */     AllImpExpHdrsELO results = new AllImpExpHdrsELO();
/*     */     try
/*     */     {
/* 592 */       stmt = getConnection().prepareStatement(SQL_ALL_IMP_EXP_HDRS);
/* 593 */       int col = 1;
/* 594 */       resultSet = stmt.executeQuery();
/* 595 */       while (resultSet.next())
/*     */       {
/* 597 */         col = 2;
/*     */ 
/* 600 */         ImpExpHdrPK pkImpExpHdr = new ImpExpHdrPK(resultSet.getInt(col++));
/*     */ 
/* 603 */         String textImpExpHdr = "";
/*     */ 
/* 607 */         ImpExpHdrRefImpl erImpExpHdr = new ImpExpHdrRefImpl(pkImpExpHdr, textImpExpHdr);
/*     */ 
/* 612 */         Timestamp col1 = resultSet.getTimestamp(col++);
/* 613 */         int col2 = resultSet.getInt(col++);
/* 614 */         int col3 = resultSet.getInt(col++);
/*     */ 
/* 617 */         results.add(erImpExpHdr, col1, col2, col3);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 627 */       throw handleSQLException(SQL_ALL_IMP_EXP_HDRS, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 631 */       closeResultSet(resultSet);
/* 632 */       closeStatement(stmt);
/* 633 */       closeConnection();
/*     */     }
/*     */ 
/* 636 */     if (timer != null) {
/* 637 */       timer.logDebug("getAllImpExpHdrs", " items=" + results.size());
/*     */     }
/*     */ 
/* 641 */     return results;
/*     */   }
/*     */ 
/*     */   private void deleteDependants(ImpExpHdrPK pk)
/*     */   {
/* 668 */     Set emptyStrings = Collections.emptySet();
/* 669 */     deleteDependants(pk, emptyStrings);
/*     */   }
/*     */ 
/*     */   private void deleteDependants(ImpExpHdrPK pk, Set<String> exclusionTables)
/*     */   {
/* 675 */     for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
/*     */     {
/* 677 */       if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
/*     */         continue;
/* 679 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 681 */       PreparedStatement stmt = null;
/*     */ 
/* 683 */       int resultCount = 0;
/* 684 */       String s = null;
/*     */       try
/*     */       {
/* 687 */         s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
/*     */ 
/* 689 */         if (this._log.isDebugEnabled()) {
/* 690 */           this._log.debug("deleteDependants", s);
/*     */         }
/* 692 */         stmt = getConnection().prepareStatement(s);
/*     */ 
/* 695 */         int col = 1;
/* 696 */         stmt.setInt(col++, pk.getBatchId());
/*     */ 
/* 699 */         resultCount = stmt.executeUpdate();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 703 */         throw handleSQLException(pk, s, sqle);
/*     */       }
/*     */       finally
/*     */       {
/* 707 */         closeStatement(stmt);
/* 708 */         closeConnection();
/*     */ 
/* 710 */         if (timer != null) {
/* 711 */           timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
/*     */         }
/*     */       }
/*     */     }
/* 715 */     for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
/*     */     {
/* 717 */       if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
/*     */         continue;
/* 719 */       Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 721 */       PreparedStatement stmt = null;
/*     */ 
/* 723 */       int resultCount = 0;
/* 724 */       String s = null;
/*     */       try
/*     */       {
/* 727 */         s = SQL_DELETE_CHILDREN[i][1];
/*     */ 
/* 729 */         if (this._log.isDebugEnabled()) {
/* 730 */           this._log.debug("deleteDependants", s);
/*     */         }
/* 732 */         stmt = getConnection().prepareStatement(s);
/*     */ 
/* 735 */         int col = 1;
/* 736 */         stmt.setInt(col++, pk.getBatchId());
/*     */ 
/* 739 */         resultCount = stmt.executeUpdate();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 743 */         throw handleSQLException(pk, s, sqle);
/*     */       }
/*     */       finally
/*     */       {
/* 747 */         closeStatement(stmt);
/* 748 */         closeConnection();
/*     */ 
/* 750 */         if (timer != null)
/* 751 */           timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ImpExpHdrEVO getDetails(ImpExpHdrPK pk, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 770 */     return getDetails(new ImpExpHdrCK(pk), dependants);
/*     */   }
/*     */ 
/*     */   public ImpExpHdrEVO getDetails(ImpExpHdrCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 787 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 790 */     if (this.mDetails == null) {
/* 791 */       doLoad(paramCK.getImpExpHdrPK());
/*     */     }
/* 793 */     else if (!this.mDetails.getPK().equals(paramCK.getImpExpHdrPK())) {
/* 794 */       doLoad(paramCK.getImpExpHdrPK());
/*     */     }
/*     */ 
/* 803 */     if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isIMP_EXP_ROWAllItemsLoaded()))
/*     */     {
/* 808 */       this.mDetails.setIMP_EXP_ROW(getImpExpRowDAO().getAll(this.mDetails.getBatchId(), dependants, this.mDetails.getIMP_EXP_ROW()));
/*     */ 
/* 815 */       this.mDetails.setIMP_EXP_ROWAllItemsLoaded(true);
/*     */     }
/*     */ 
/* 818 */     if ((paramCK instanceof ImpExpRowCK))
/*     */     {
/* 820 */       if (this.mDetails.getIMP_EXP_ROW() == null) {
/* 821 */         this.mDetails.loadIMP_EXP_ROWItem(getImpExpRowDAO().getDetails(paramCK, dependants));
/*     */       }
/*     */       else {
/* 824 */         ImpExpRowPK pk = ((ImpExpRowCK)paramCK).getImpExpRowPK();
/* 825 */         ImpExpRowEVO evo = this.mDetails.getIMP_EXP_ROWItem(pk);
/* 826 */         if (evo == null) {
/* 827 */           this.mDetails.loadIMP_EXP_ROWItem(getImpExpRowDAO().getDetails(paramCK, dependants));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 832 */     ImpExpHdrEVO details = new ImpExpHdrEVO();
/* 833 */     details = this.mDetails.deepClone();
/*     */ 
/* 835 */     if (timer != null) {
/* 836 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 838 */     return details;
/*     */   }
/*     */ 
/*     */   public ImpExpHdrEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 844 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 848 */     getDependants(this.mDetails, dependants);
/*     */ 
/* 851 */     ImpExpHdrEVO details = this.mDetails.deepClone();
/*     */ 
/* 853 */     if (timer != null) {
/* 854 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 856 */     return details;
/*     */   }
/*     */ 
/*     */   protected ImpExpRowDAO getImpExpRowDAO()
/*     */   {
/* 865 */     if (this.mImpExpRowDAO == null)
/*     */     {
/* 867 */       if (this.mDataSource != null)
/* 868 */         this.mImpExpRowDAO = new ImpExpRowDAO(this.mDataSource);
/*     */       else {
/* 870 */         this.mImpExpRowDAO = new ImpExpRowDAO(getConnection());
/*     */       }
/*     */     }
/* 873 */     return this.mImpExpRowDAO;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 878 */     return "ImpExpHdr";
/*     */   }
/*     */ 
/*     */   public ImpExpHdrRef getRef(ImpExpHdrPK paramImpExpHdrPK)
/*     */     throws ValidationException
/*     */   {
/* 884 */     ImpExpHdrEVO evo = getDetails(paramImpExpHdrPK, "");
/* 885 */     return evo.getEntityRef("");
/*     */   }
/*     */ 
/*     */   public void getDependants(Collection c, String dependants)
/*     */   {
/* 911 */     if (c == null)
/* 912 */       return;
/* 913 */     Iterator iter = c.iterator();
/* 914 */     while (iter.hasNext())
/*     */     {
/* 916 */       ImpExpHdrEVO evo = (ImpExpHdrEVO)iter.next();
/* 917 */       getDependants(evo, dependants);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void getDependants(ImpExpHdrEVO evo, String dependants)
/*     */   {
/* 931 */     if (evo.getBatchId() < 1) {
/* 932 */       return;
/*     */     }
/*     */ 
/* 940 */     if (dependants.indexOf("<0>") > -1)
/*     */     {
/* 943 */       if (!evo.isIMP_EXP_ROWAllItemsLoaded())
/*     */       {
/* 945 */         evo.setIMP_EXP_ROW(getImpExpRowDAO().getAll(evo.getBatchId(), dependants, evo.getIMP_EXP_ROW()));
/*     */ 
/* 952 */         evo.setIMP_EXP_ROWAllItemsLoaded(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.impexp.ImpExpHdrDAO
 * JD-Core Version:    0.6.0
 */