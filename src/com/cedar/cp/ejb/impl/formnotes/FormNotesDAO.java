/*     */ package com.cedar.cp.ejb.impl.formnotes;
/*     */ 
/*     */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*     */ import com.cedar.cp.api.base.ValidationException;
/*     */ import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.dimension.StructureElementRef;
/*     */ import com.cedar.cp.api.formnotes.FormNotesRef;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
/*     */ import com.cedar.cp.dto.formnotes.AllFormNotesForBudgetLocationELO;
/*     */ import com.cedar.cp.dto.formnotes.AllFormNotesForFormAndBudgetLocationELO;
/*     */ import com.cedar.cp.dto.formnotes.FormNotesCK;
/*     */ import com.cedar.cp.dto.formnotes.FormNotesPK;
/*     */ import com.cedar.cp.dto.formnotes.FormNotesRefImpl;
/*     */ import com.cedar.cp.dto.user.UserPK;
/*     */ import com.cedar.cp.dto.user.UserRefImpl;
/*     */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*     */ import com.cedar.cp.util.Log;
/*     */ import com.cedar.cp.util.Timer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class FormNotesDAO extends AbstractDAO
/*     */ {
/*  35 */   Log _log = new Log(getClass());
/*     */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select FORM_NOTE_ID from FORM_NOTES where    FORM_NOTE_ID = ? ";
/*     */   private static final String SQL_SELECT_COLUMNS = "select FORM_NOTES.FORM_NOTE_ID,FORM_NOTES.FORM_ID,FORM_NOTES.STRUCTURE_ELEMENT_ID,FORM_NOTES.NOTE,FORM_NOTES.ATTACHMENT_ID,FORM_NOTES.UPDATED_BY_USER_ID,FORM_NOTES.UPDATED_TIME,FORM_NOTES.CREATED_TIME";
/*     */   protected static final String SQL_LOAD = " from FORM_NOTES where    FORM_NOTE_ID = ? ";
/*     */   protected static final String SQL_CREATE = "insert into FORM_NOTES ( FORM_NOTE_ID,FORM_ID,STRUCTURE_ELEMENT_ID,NOTE,ATTACHMENT_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
/*     */   protected static final String SQL_UPDATE_SEQ_NUM = "update FORM_NOTES_SEQ set SEQ_NUM = SEQ_NUM + ?";
/*     */   protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from FORM_NOTES_SEQ";
/*     */   protected static final String SQL_STORE = "update FORM_NOTES set FORM_ID = ?,STRUCTURE_ELEMENT_ID = ?,NOTE = ?,ATTACHMENT_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    FORM_NOTE_ID = ? ";
/*     */   protected static final String SQL_REMOVE = "delete from FORM_NOTES where    FORM_NOTE_ID = ? ";
/* 573 */   protected static String SQL_ALL_FORM_NOTES_FOR_BUDGET_LOCATION = "select 0       ,FORM_NOTES.FORM_NOTE_ID      ,FORM_NOTES.FORM_ID      ,FORM_NOTES.STRUCTURE_ELEMENT_ID      ,FORM_NOTES.NOTE      ,FORM_NOTES.ATTACHMENT_ID from FORM_NOTES where 1=1  and  FORM_NOTES.STRUCTURE_ELEMENT_ID = ? and FORM_NOTES.FORM_ID = 0 order by FORM_NOTES.CREATED_TIME";
/*     */ 
/* 665 */   protected static String SQL_ALL_FORM_NOTES_FOR_FORM_AND_BUDGET_LOCATION = "select 0       ,FORM_NOTES.FORM_NOTE_ID      ,USR.USER_ID      ,USR.NAME      ,FORM_NOTES.FORM_ID      ,FORM_NOTES.STRUCTURE_ELEMENT_ID      ,FORM_NOTES.CREATED_TIME      ,FORM_NOTES.NOTE      ,FORM_NOTES.ATTACHMENT_ID from FORM_NOTES    ,USR where 1=1  and  FORM_NOTES.FORM_ID = ? and FORM_NOTES.STRUCTURE_ELEMENT_ID = ? and FORM_NOTES.UPDATED_BY_USER_ID = USR.USER_ID (+) order by FORM_NOTES.CREATED_TIME";
/*     */   protected FormNotesEVO mDetails;
/*     */ 
/*     */   public FormNotesDAO(Connection connection)
/*     */   {
/*  42 */     super(connection);
/*     */   }
/*     */ 
/*     */   public FormNotesDAO()
/*     */   {
/*     */   }
/*     */ 
/*     */   public FormNotesDAO(DataSource ds)
/*     */   {
/*  58 */     super(ds);
/*     */   }
/*     */ 
/*     */   protected FormNotesPK getPK()
/*     */   {
/*  66 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void setDetails(FormNotesEVO details)
/*     */   {
/*  75 */     this.mDetails = details.deepClone();
/*     */   }
/*     */ 
/*     */   public FormNotesEVO setAndGetDetails(FormNotesEVO details, String dependants)
/*     */   {
/*  86 */     setDetails(details);
/*  87 */     generateKeys();
/*  88 */     return this.mDetails.deepClone();
/*     */   }
/*     */ 
/*     */   public FormNotesPK create()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/*  97 */     doCreate();
/*     */ 
/*  99 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   public void load(FormNotesPK pk)
/*     */     throws ValidationException
/*     */   {
/* 109 */     doLoad(pk);
/*     */   }
/*     */ 
/*     */   public void store()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 118 */     doStore();
/*     */   }
/*     */ 
/*     */   public void remove()
/*     */   {
/* 127 */     doRemove();
/*     */   }
/*     */ 
/*     */   public FormNotesPK findByPrimaryKey(FormNotesPK pk_)
/*     */     throws ValidationException
/*     */   {
/* 136 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 137 */     if (exists(pk_))
/*     */     {
/* 139 */       if (timer != null) {
/* 140 */         timer.logDebug("findByPrimaryKey", pk_);
/*     */       }
/* 142 */       return pk_;
/*     */     }
/*     */ 
/* 145 */     throw new ValidationException(pk_ + " not found");
/*     */   }
/*     */ 
/*     */   protected boolean exists(FormNotesPK pk)
/*     */   {
/* 163 */     PreparedStatement stmt = null;
/* 164 */     ResultSet resultSet = null;
/* 165 */     boolean returnValue = false;
/*     */     try
/*     */     {
/* 169 */       stmt = getConnection().prepareStatement("select FORM_NOTE_ID from FORM_NOTES where    FORM_NOTE_ID = ? ");
/*     */ 
/* 171 */       int col = 1;
/* 172 */       stmt.setInt(col++, pk.getFormNoteId());
/*     */ 
/* 174 */       resultSet = stmt.executeQuery();
/*     */ 
/* 176 */       if (!resultSet.next())
/* 177 */         returnValue = false;
/*     */       else
/* 179 */         returnValue = true;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 183 */       throw handleSQLException(pk, "select FORM_NOTE_ID from FORM_NOTES where    FORM_NOTE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 187 */       closeResultSet(resultSet);
/* 188 */       closeStatement(stmt);
/* 189 */       closeConnection();
/*     */     }
/* 191 */     return returnValue;
/*     */   }
/*     */ 
/*     */   private FormNotesEVO getEvoFromJdbc(ResultSet resultSet_)
/*     */     throws SQLException
/*     */   {
/* 210 */     int col = 1;
/* 211 */     FormNotesEVO evo = new FormNotesEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getInt(col++));
/*     */ 
/* 219 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
/* 220 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
/* 221 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
/* 222 */     return evo;
/*     */   }
/*     */ 
/*     */   private int putEvoKeysToJdbc(FormNotesEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 227 */     int col = startCol_;
/* 228 */     stmt_.setInt(col++, evo_.getFormNoteId());
/* 229 */     return col;
/*     */   }
/*     */ 
/*     */   private int putEvoDataToJdbc(FormNotesEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*     */   {
/* 234 */     int col = startCol_;
/* 235 */     stmt_.setInt(col++, evo_.getFormId());
/* 236 */     stmt_.setInt(col++, evo_.getStructureElementId());
/* 237 */     stmt_.setString(col++, evo_.getNote());
/* 238 */     stmt_.setInt(col++, evo_.getAttachmentId());
/* 239 */     stmt_.setInt(col++, evo_.getUpdatedByUserId());
/* 240 */     stmt_.setTimestamp(col++, evo_.getUpdatedTime());
/* 241 */     stmt_.setTimestamp(col++, evo_.getCreatedTime());
/* 242 */     return col;
/*     */   }
/*     */ 
/*     */   protected void doLoad(FormNotesPK pk)
/*     */     throws ValidationException
/*     */   {
/* 258 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 260 */     PreparedStatement stmt = null;
/* 261 */     ResultSet resultSet = null;
/*     */     try
/*     */     {
/* 265 */       stmt = getConnection().prepareStatement("select FORM_NOTES.FORM_NOTE_ID,FORM_NOTES.FORM_ID,FORM_NOTES.STRUCTURE_ELEMENT_ID,FORM_NOTES.NOTE,FORM_NOTES.ATTACHMENT_ID,FORM_NOTES.UPDATED_BY_USER_ID,FORM_NOTES.UPDATED_TIME,FORM_NOTES.CREATED_TIME from FORM_NOTES where    FORM_NOTE_ID = ? ");
/*     */ 
/* 268 */       int col = 1;
/* 269 */       stmt.setInt(col++, pk.getFormNoteId());
/*     */ 
/* 271 */       resultSet = stmt.executeQuery();
/*     */ 
/* 273 */       if (!resultSet.next()) {
/* 274 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*     */       }
/*     */ 
/* 277 */       this.mDetails = getEvoFromJdbc(resultSet);
/* 278 */       if (this.mDetails.isModified())
/* 279 */         this._log.info("doLoad", this.mDetails);
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 283 */       throw handleSQLException(pk, "select FORM_NOTES.FORM_NOTE_ID,FORM_NOTES.FORM_ID,FORM_NOTES.STRUCTURE_ELEMENT_ID,FORM_NOTES.NOTE,FORM_NOTES.ATTACHMENT_ID,FORM_NOTES.UPDATED_BY_USER_ID,FORM_NOTES.UPDATED_TIME,FORM_NOTES.CREATED_TIME from FORM_NOTES where    FORM_NOTE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 287 */       closeResultSet(resultSet);
/* 288 */       closeStatement(stmt);
/* 289 */       closeConnection();
/*     */ 
/* 291 */       if (timer != null)
/* 292 */         timer.logDebug("doLoad", pk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doCreate()
/*     */     throws DuplicateNameValidationException, ValidationException
/*     */   {
/* 325 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 326 */     generateKeys();
/*     */ 
/* 328 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 333 */       this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
/* 334 */       this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 335 */       stmt = getConnection().prepareStatement("insert into FORM_NOTES ( FORM_NOTE_ID,FORM_ID,STRUCTURE_ELEMENT_ID,NOTE,ATTACHMENT_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");
/*     */ 
/* 338 */       int col = 1;
/* 339 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/* 340 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 343 */       int resultCount = stmt.executeUpdate();
/* 344 */       if (resultCount != 1)
/*     */       {
/* 346 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/* 349 */       this.mDetails.reset();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 353 */       throw handleSQLException(this.mDetails.getPK(), "insert into FORM_NOTES ( FORM_NOTE_ID,FORM_ID,STRUCTURE_ELEMENT_ID,NOTE,ATTACHMENT_ID,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 357 */       closeStatement(stmt);
/* 358 */       closeConnection();
/*     */ 
/* 360 */       if (timer != null)
/* 361 */         timer.logDebug("doCreate", this.mDetails.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int reserveIds(int insertCount)
/*     */   {
/* 381 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 383 */     PreparedStatement stmt = null;
/* 384 */     ResultSet resultSet = null;
/* 385 */     String sqlString = null;
/*     */     try
/*     */     {
/* 390 */       sqlString = "update FORM_NOTES_SEQ set SEQ_NUM = SEQ_NUM + ?";
/* 391 */       stmt = getConnection().prepareStatement("update FORM_NOTES_SEQ set SEQ_NUM = SEQ_NUM + ?");
/* 392 */       stmt.setInt(1, insertCount);
/*     */ 
/* 394 */       int resultCount = stmt.executeUpdate();
/* 395 */       if (resultCount != 1) {
/* 396 */         throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
/*     */       }
/* 398 */       closeStatement(stmt);
/*     */ 
/* 401 */       sqlString = "select SEQ_NUM from FORM_NOTES_SEQ";
/* 402 */       stmt = getConnection().prepareStatement("select SEQ_NUM from FORM_NOTES_SEQ");
/* 403 */       resultSet = stmt.executeQuery();
/* 404 */       if (!resultSet.next())
/* 405 */         throw new RuntimeException(getEntityName() + " reserveIds: select failed");
/* 406 */       int latestKey = resultSet.getInt(1);
/*     */ 
/* 408 */       int i = latestKey - insertCount;
/*     */       return i;
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 412 */       throw handleSQLException(sqlString, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 416 */       closeResultSet(resultSet);
/* 417 */       closeStatement(stmt);
/* 418 */       closeConnection();
/*     */ 
/* 420 */       if (timer != null)
/* 421 */         timer.logDebug("reserveIds", "keys=" + insertCount); 
/* 421 */     }
/*     */   }
/*     */ 
/*     */   public FormNotesPK generateKeys()
/*     */   {
/* 431 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 433 */     int insertCount = this.mDetails.getInsertCount(0);
/*     */ 
/* 436 */     if (insertCount == 0) {
/* 437 */       return this.mDetails.getPK();
/*     */     }
/* 439 */     this.mDetails.assignNextKey(reserveIds(insertCount));
/*     */ 
/* 441 */     return this.mDetails.getPK();
/*     */   }
/*     */ 
/*     */   protected void doStore()
/*     */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*     */   {
/* 465 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 467 */     generateKeys();
/*     */ 
/* 472 */     PreparedStatement stmt = null;
/*     */ 
/* 474 */     boolean mainChanged = this.mDetails.isModified();
/* 475 */     boolean dependantChanged = false;
/*     */     try
/*     */     {
/* 478 */       if ((mainChanged) || (dependantChanged))
/*     */       {
/* 481 */         this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
/* 482 */         stmt = getConnection().prepareStatement("update FORM_NOTES set FORM_ID = ?,STRUCTURE_ELEMENT_ID = ?,NOTE = ?,ATTACHMENT_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    FORM_NOTE_ID = ? ");
/*     */ 
/* 485 */         int col = 1;
/* 486 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/* 487 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*     */ 
/* 490 */         int resultCount = stmt.executeUpdate();
/*     */ 
/* 493 */         if (resultCount != 1) {
/* 494 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*     */         }
/*     */ 
/* 497 */         this.mDetails.reset();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 506 */       throw handleSQLException(getPK(), "update FORM_NOTES set FORM_ID = ?,STRUCTURE_ELEMENT_ID = ?,NOTE = ?,ATTACHMENT_ID = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    FORM_NOTE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 510 */       closeStatement(stmt);
/* 511 */       closeConnection();
/*     */ 
/* 513 */       if ((timer != null) && (
/* 514 */         (mainChanged) || (dependantChanged)))
/* 515 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doRemove()
/*     */   {
/* 533 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 538 */     PreparedStatement stmt = null;
/*     */     try
/*     */     {
/* 543 */       stmt = getConnection().prepareStatement("delete from FORM_NOTES where    FORM_NOTE_ID = ? ");
/*     */ 
/* 546 */       int col = 1;
/* 547 */       stmt.setInt(col++, this.mDetails.getFormNoteId());
/*     */ 
/* 549 */       int resultCount = stmt.executeUpdate();
/*     */ 
/* 551 */       if (resultCount != 1) {
/* 552 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 558 */       throw handleSQLException(getPK(), "delete from FORM_NOTES where    FORM_NOTE_ID = ? ", sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 562 */       closeStatement(stmt);
/* 563 */       closeConnection();
/*     */ 
/* 565 */       if (timer != null)
/* 566 */         timer.logDebug("remove", this.mDetails.getPK());
/*     */     }
/*     */   }
/*     */ 
/*     */   public AllFormNotesForBudgetLocationELO getAllFormNotesForBudgetLocation(int param1)
/*     */   {
/* 600 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 601 */     PreparedStatement stmt = null;
/* 602 */     ResultSet resultSet = null;
/* 603 */     AllFormNotesForBudgetLocationELO results = new AllFormNotesForBudgetLocationELO();
/*     */     try
/*     */     {
/* 606 */       stmt = getConnection().prepareStatement(SQL_ALL_FORM_NOTES_FOR_BUDGET_LOCATION);
/* 607 */       int col = 1;
/* 608 */       stmt.setInt(col++, param1);
/* 609 */       resultSet = stmt.executeQuery();
/* 610 */       while (resultSet.next())
/*     */       {
/* 612 */         col = 2;
/*     */ 
/* 615 */         FormNotesPK pkFormNotes = new FormNotesPK(resultSet.getInt(col++));
/*     */ 
/* 618 */         String textFormNotes = "";
/*     */ 
/* 622 */         FormNotesRefImpl erFormNotes = new FormNotesRefImpl(pkFormNotes, textFormNotes);
/*     */ 
/* 627 */         int col1 = resultSet.getInt(col++);
/* 628 */         int col2 = resultSet.getInt(col++);
/* 629 */         String col3 = resultSet.getString(col++);
/* 630 */         int col4 = resultSet.getInt(col++);
/*     */ 
/* 633 */         results.add(erFormNotes, col1, col2, col3, col4);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 644 */       throw handleSQLException(SQL_ALL_FORM_NOTES_FOR_BUDGET_LOCATION, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 648 */       closeResultSet(resultSet);
/* 649 */       closeStatement(stmt);
/* 650 */       closeConnection();
/*     */     }
/*     */ 
/* 653 */     if (timer != null) {
/* 654 */       timer.logDebug("getAllFormNotesForBudgetLocation", " StructureElementId=" + param1 + " items=" + results.size());
/*     */     }
/*     */ 
/* 659 */     return results;
/*     */   }
/*     */ 
/*     */   public AllFormNotesForFormAndBudgetLocationELO getAllFormNotesForFormAndBudgetLocation(int param1, int param2)
/*     */   {
/* 699 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 700 */     PreparedStatement stmt = null;
/* 701 */     ResultSet resultSet = null;
/* 702 */     AllFormNotesForFormAndBudgetLocationELO results = new AllFormNotesForFormAndBudgetLocationELO();
/*     */     try
/*     */     {
/* 705 */       stmt = getConnection().prepareStatement(SQL_ALL_FORM_NOTES_FOR_FORM_AND_BUDGET_LOCATION);
/* 706 */       int col = 1;
/* 707 */       stmt.setInt(col++, param1);
/* 708 */       stmt.setInt(col++, param2);
/* 709 */       resultSet = stmt.executeQuery();
/* 710 */       while (resultSet.next())
/*     */       {
/* 712 */         col = 2;
/*     */ 
/* 715 */         FormNotesPK pkFormNotes = new FormNotesPK(resultSet.getInt(col++));
/*     */ 
/* 718 */         String textFormNotes = "";
/*     */ 
/* 721 */         UserPK pkUser = new UserPK(resultSet.getInt(col++));
/*     */ 
/* 724 */         String textUser = resultSet.getString(col++);
/*     */ 
/* 727 */         FormNotesRefImpl erFormNotes = new FormNotesRefImpl(pkFormNotes, textFormNotes);
/*     */ 
/* 733 */         UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
/*     */ 
/* 738 */         int col1 = resultSet.getInt(col++);
/* 739 */         int col2 = resultSet.getInt(col++);
/* 740 */         Timestamp col3 = resultSet.getTimestamp(col++);
/* 741 */         String col4 = resultSet.getString(col++);
/* 742 */         int col5 = resultSet.getInt(col++);
/*     */ 
/* 745 */         results.add(erFormNotes, erUser, col1, col2, col3, col4, col5);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 758 */       throw handleSQLException(SQL_ALL_FORM_NOTES_FOR_FORM_AND_BUDGET_LOCATION, sqle);
/*     */     }
/*     */     finally
/*     */     {
/* 762 */       closeResultSet(resultSet);
/* 763 */       closeStatement(stmt);
/* 764 */       closeConnection();
/*     */     }
/*     */ 
/* 767 */     if (timer != null) {
/* 768 */       timer.logDebug("getAllFormNotesForFormAndBudgetLocation", " FormId=" + param1 + ",StructureElementId=" + param2 + " items=" + results.size());
/*     */     }
/*     */ 
/* 774 */     return results;
/*     */   }
/*     */ 
/*     */   public FormNotesEVO getDetails(FormNotesPK pk, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 792 */     return getDetails(new FormNotesCK(pk), dependants);
/*     */   }
/*     */ 
/*     */   public FormNotesEVO getDetails(FormNotesCK paramCK, String dependants)
/*     */     throws ValidationException
/*     */   {
/* 806 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 809 */     if (this.mDetails == null) {
/* 810 */       doLoad(paramCK.getFormNotesPK());
/*     */     }
/* 812 */     else if (!this.mDetails.getPK().equals(paramCK.getFormNotesPK())) {
/* 813 */       doLoad(paramCK.getFormNotesPK());
/*     */     }
/*     */ 
/* 816 */     FormNotesEVO details = new FormNotesEVO();
/* 817 */     details = this.mDetails.deepClone();
/*     */ 
/* 819 */     if (timer != null) {
/* 820 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*     */     }
/* 822 */     return details;
/*     */   }
/*     */ 
/*     */   public FormNotesEVO getDetails(String dependants)
/*     */     throws ValidationException
/*     */   {
/* 828 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*     */ 
/* 832 */     FormNotesEVO details = this.mDetails.deepClone();
/*     */ 
/* 834 */     if (timer != null) {
/* 835 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*     */     }
/* 837 */     return details;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 842 */     return "FormNotes";
/*     */   }
/*     */ 
/*     */   public FormNotesRef getRef(FormNotesPK paramFormNotesPK)
/*     */     throws ValidationException
/*     */   {
/* 848 */     FormNotesEVO evo = getDetails(paramFormNotesPK, "");
/* 849 */     return evo.getEntityRef("");
/*     */   }

			public ArrayList<Object[]> getNotesForCostCenters(ArrayList<Integer> costCenters, int financeCubeId, String fromDate, String toDate) {
/* 699 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 700 */     PreparedStatement stmt = null;
/* 701 */     ResultSet resultSet = null;
/* 702 */     ArrayList<Object[]> results = new ArrayList<Object[]>();
			  if (costCenters.size() > 0) {
	/*     */     try
	/*     */     {
					StringBuilder mainSQL = new StringBuilder("select usr.name, usr.user_id, se.structure_element_id, se.vis_id, created, string_value, notes.budget_cycle_id, notes.data_entry_profile_id, notes.dim1 from sft");
					mainSQL.append(financeCubeId); // for example ...from SFT4 notes...
					mainSQL.append(" notes, usr, structure_element se where usr.user_id = notes.user_id and se.structure_element_id = notes.dim0");
					mainSQL.append(" and notes.created between to_date('");
					mainSQL.append(fromDate);
					mainSQL.append(" 00:00:00', 'yyyy/mm/dd HH24:MI:SS') and to_date('");
					mainSQL.append(toDate);
					mainSQL.append(" 23:59:59', 'yyyy/mm/dd HH24:MI:SS') order by usr.name, created");

	/* 705 */       stmt = getConnection().prepareStatement(mainSQL.toString());
	/* 709 */       resultSet = stmt.executeQuery();
	/* 710 */       while (resultSet.next())
	/*     */       {
						if (costCenters.contains(resultSet.getInt(3))) {
					  
						  StructureElementPK sePk = new StructureElementPK(0, resultSet.getInt(3));
						  StructureElementRef seRef = new StructureElementRefImpl(sePk, resultSet.getString(4));
		/* 712 */         
						  Object[] row = new Object[8];
		/* 738 */         row[0] = resultSet.getString(1);	
		/* 739 */         row[1] = seRef;
		/* 740 */         row[2] = resultSet.getTimestamp(5);
		/* 741 */         row[3] = resultSet.getString(6);
						  row[4] = resultSet.getInt(7); // bc
						  row[5] = resultSet.getInt(8); // dep
						  row[6] = resultSet.getInt(9); // dim1 - id
						  row[7] = resultSet.getInt(2);
		/* 745 */         results.add(row);
						}
	/*     */       }
	/*     */ 
	/*     */     }
	/*     */     catch (SQLException sqle)
	/*     */     {
	/* 758 */       throw handleSQLException("", sqle);
	/*     */     }
	/*     */     finally
	/*     */     {
	/* 762 */       closeResultSet(resultSet);
	/* 763 */       closeStatement(stmt);
	/* 764 */       closeConnection();
	/*     */     }
/*     */ 	  }
/* 767 */     if (timer != null) {
/* 768 */       timer.logDebug("getNotesForCostCenters", " items=" + results.size());
/*     */     }
/*     */ 
/* 774 */     return results;
/*     */   }
			
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.formnotes.FormNotesDAO
 * JD-Core Version:    0.6.0
 */