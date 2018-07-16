package com.cedar.cp.ejb.impl.reset;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.reset.AllChallengeQuestionsELO;
import com.cedar.cp.dto.reset.AllQuestionsAndAnswersByUserIDELO;
import com.cedar.cp.dto.reset.ChallengeQuestionCK;
import com.cedar.cp.dto.reset.ChallengeQuestionPK;
import com.cedar.cp.dto.reset.ChallengeQuestionRefImpl;
import com.cedar.cp.dto.user.UserCK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import javax.sql.DataSource;

public class ChallengeQuestionDAO extends AbstractDAO
{
  Log _log = new Log(getClass());
  private static final String SQL_SELECT_COLUMNS = "select CHALLENGE_QUESTION.USER_ID,CHALLENGE_QUESTION.QUESTION_TEXT,CHALLENGE_QUESTION.QUESTION_ANSWER,CHALLENGE_QUESTION.VERSION_NUM,CHALLENGE_QUESTION.UPDATED_BY_USER_ID,CHALLENGE_QUESTION.UPDATED_TIME,CHALLENGE_QUESTION.CREATED_TIME";
  protected static final String SQL_LOAD = " from CHALLENGE_QUESTION where    USER_ID = ? AND QUESTION_TEXT = ? ";
  protected static final String SQL_CREATE = "insert into CHALLENGE_QUESTION ( USER_ID,QUESTION_TEXT,QUESTION_ANSWER,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)";
  protected static final String SQL_STORE = "update CHALLENGE_QUESTION set QUESTION_ANSWER = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    USER_ID = ? AND QUESTION_TEXT = ? AND VERSION_NUM = ?";
  protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from CHALLENGE_QUESTION where USER_ID = ?,QUESTION_TEXT = ?";
  protected static String SQL_ALL_CHALLENGE_QUESTIONS = "select 0       ,USR.USER_ID      ,USR.NAME      ,CHALLENGE_QUESTION.USER_ID      ,CHALLENGE_QUESTION.QUESTION_TEXT      ,CHALLENGE_QUESTION.QUESTION_TEXT      ,CHALLENGE_QUESTION.USER_ID      ,CHALLENGE_QUESTION.QUESTION_TEXT      ,CHALLENGE_QUESTION.QUESTION_ANSWER from CHALLENGE_QUESTION    ,USR where 1=1   and CHALLENGE_QUESTION.USER_ID = USR.USER_ID ";
  protected static String SQL_ALL_QUESTIONS_AND_ANSWERS_BY_USER_ID = "select 0, USR.USER_ID, USR.NAME  ,CHALLENGE_QUESTION.QUESTION_TEXT, CHALLENGE_QUESTION.QUESTION_ANSWER from CHALLENGE_QUESTION    ,USR where 1=1   and CHALLENGE_QUESTION.USER_ID = USR.USER_ID and CHALLENGE_QUESTION.USER_ID = ?";
  protected static final String SQL_DELETE_BATCH = "delete from CHALLENGE_QUESTION where    USER_ID = ? AND QUESTION_TEXT = ? ";
  public static final String SQL_BULK_GET_ALL = " from CHALLENGE_QUESTION where 1=1 and CHALLENGE_QUESTION.USER_ID = ? order by  CHALLENGE_QUESTION.USER_ID ,CHALLENGE_QUESTION.QUESTION_TEXT";
  protected static final String SQL_GET_ALL = " from CHALLENGE_QUESTION where    USER_ID = ? ";
  protected ChallengeQuestionEVO mDetails;
  
  protected static String GET_CHALLENGE_WORD = "select 0, USR.USER_ID, USR.NAME, CHALLENGE_WORD.WORD from CHALLENGE_WORD    ,USR where CHALLENGE_WORD.USER_ID = USR.USER_ID and  CHALLENGE_WORD.USER_ID = ?";
  protected static String INSERT_CHALLENGE_WORD = "insert into  CHALLENGE_WORD (USER_ID, WORD, UPDATED_TIME, CREATED_TIME) VALUES (?, ?, ?, ?)";
  protected static String UPDATE_CHALLENGE_WORD = "update CHALLENGE_WORD set WORD = ?, UPDATED_TIME = ? WHERE USER_ID = ?";

  public ChallengeQuestionDAO(Connection connection)
  {
    super(connection);
  }

  public ChallengeQuestionDAO()
  {
  }

  public ChallengeQuestionDAO(DataSource ds)
  {
    super(ds);
  }

  protected ChallengeQuestionPK getPK()
  {
    return mDetails.getPK();
  }

  public void setDetails(ChallengeQuestionEVO details)
  {
    mDetails = details.deepClone();
  }

  private ChallengeQuestionEVO getEvoFromJdbc(ResultSet resultSet_)
    throws SQLException
  {
    int col = 1;
    ChallengeQuestionEVO evo = new ChallengeQuestionEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++));

    evo.setUpdatedByUserId(resultSet_.getInt(col++));
    evo.setUpdatedTime(resultSet_.getTimestamp(col++));
    evo.setCreatedTime(resultSet_.getTimestamp(col++));
    return evo;
  }

  private int putEvoKeysToJdbc(ChallengeQuestionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
  {
    int col = startCol_;
    stmt_.setInt(col++, evo_.getUserId());
    stmt_.setString(col++, evo_.getQuestionText());
    return col;
  }

  private int putEvoDataToJdbc(ChallengeQuestionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
  {
    int col = startCol_;
    stmt_.setString(col++, evo_.getQuestionAnswer());
    stmt_.setInt(col++, evo_.getVersionNum());
    stmt_.setInt(col++, evo_.getUpdatedByUserId());
    stmt_.setTimestamp(col++, evo_.getUpdatedTime());
    stmt_.setTimestamp(col++, evo_.getCreatedTime());
    return col;
  }

  protected void doLoad(ChallengeQuestionPK pk)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    try
    {
      stmt = getConnection().prepareStatement("select CHALLENGE_QUESTION.USER_ID,CHALLENGE_QUESTION.QUESTION_TEXT,CHALLENGE_QUESTION.QUESTION_ANSWER,CHALLENGE_QUESTION.VERSION_NUM,CHALLENGE_QUESTION.UPDATED_BY_USER_ID,CHALLENGE_QUESTION.UPDATED_TIME,CHALLENGE_QUESTION.CREATED_TIME from CHALLENGE_QUESTION where    USER_ID = ? AND QUESTION_TEXT = ? ");

      int col = 1;
      stmt.setInt(col++, pk.getUserId());
      stmt.setString(col++, pk.getQuestionText());

      resultSet = stmt.executeQuery();

      if (!resultSet.next()) {
        throw new ValidationException(getEntityName() + " select of " + pk + " not found");
      }

      mDetails = getEvoFromJdbc(resultSet);
      if (mDetails.isModified())
        _log.info("doLoad", mDetails);
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(pk, "select CHALLENGE_QUESTION.USER_ID,CHALLENGE_QUESTION.QUESTION_TEXT,CHALLENGE_QUESTION.QUESTION_ANSWER,CHALLENGE_QUESTION.VERSION_NUM,CHALLENGE_QUESTION.UPDATED_BY_USER_ID,CHALLENGE_QUESTION.UPDATED_TIME,CHALLENGE_QUESTION.CREATED_TIME from CHALLENGE_QUESTION where    USER_ID = ? AND QUESTION_TEXT = ? ", sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();

      if (timer != null)
        timer.logDebug("doLoad", pk);
    }
  }

  protected void doCreate()
    throws DuplicateNameValidationException, ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    try
    {
      mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
      mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
      stmt = getConnection().prepareStatement("insert into CHALLENGE_QUESTION ( USER_ID,QUESTION_TEXT,QUESTION_ANSWER,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)");

      int col = 1;
      col = putEvoKeysToJdbc(mDetails, stmt, col);
      col = putEvoDataToJdbc(mDetails, stmt, col);

      int resultCount = stmt.executeUpdate();
      if (resultCount != 1)
      {
        throw new RuntimeException(getEntityName() + " insert failed (" + mDetails.getPK() + "): resultCount=" + resultCount);
      }

      mDetails.reset();
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(mDetails.getPK(), "insert into CHALLENGE_QUESTION ( USER_ID,QUESTION_TEXT,QUESTION_ANSWER,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?)", sqle);
    }
    finally
    {
      closeStatement(stmt);
      closeConnection();

      if (timer != null)
        timer.logDebug("doCreate", mDetails.toString());
    }
  }

  protected void doStore()
    throws DuplicateNameValidationException, VersionValidationException, ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    PreparedStatement stmt = null;

    boolean mainChanged = mDetails.isModified();
    boolean dependantChanged = false;
    try
    {
      if ((mainChanged) || (dependantChanged))
      {
        mDetails.setVersionNum(mDetails.getVersionNum() + 1);

        mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
        stmt = getConnection().prepareStatement("update CHALLENGE_QUESTION set QUESTION_ANSWER = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    USER_ID = ? AND QUESTION_TEXT = ? AND VERSION_NUM = ?");

        int col = 1;
        col = putEvoDataToJdbc(mDetails, stmt, col);
        col = putEvoKeysToJdbc(mDetails, stmt, col);

        stmt.setInt(col++, mDetails.getVersionNum() - 1);

        int resultCount = stmt.executeUpdate();

        if (resultCount == 0) {
          checkVersionNum();
        }
        if (resultCount != 1) {
          throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
        }

        mDetails.reset();
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(getPK(), "update CHALLENGE_QUESTION set QUESTION_ANSWER = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    USER_ID = ? AND QUESTION_TEXT = ? AND VERSION_NUM = ?", sqle);
    }
    finally
    {
      closeStatement(stmt);
      closeConnection();

      if ((timer != null) && (
        (mainChanged) || (dependantChanged)))
        timer.logDebug("store", mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
    }
  }

  private void checkVersionNum()
    throws VersionValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    try
    {
      stmt = getConnection().prepareStatement("select VERSION_NUM from CHALLENGE_QUESTION where USER_ID = ?,QUESTION_TEXT = ?");

      int col = 1;
      stmt.setInt(col++, mDetails.getUserId());
      stmt.setString(col++, mDetails.getQuestionText());

      resultSet = stmt.executeQuery();

      if (!resultSet.next()) {
        throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
      }

      col = 1;
      int dbVersionNumber = resultSet.getInt(col++);
      if (mDetails.getVersionNum() - 1 != dbVersionNumber) {
        throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(getPK(), "select VERSION_NUM from CHALLENGE_QUESTION where USER_ID = ?,QUESTION_TEXT = ?", sqle);
    }
    finally
    {
      closeStatement(stmt);
      closeResultSet(resultSet);

      if (timer != null)
        timer.logDebug("checkVersionNum", mDetails.getPK());
    }
  }

  public AllChallengeQuestionsELO getAllChallengeQuestions()
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    AllChallengeQuestionsELO results = new AllChallengeQuestionsELO();
    try
    {
      stmt = getConnection().prepareStatement(SQL_ALL_CHALLENGE_QUESTIONS);
      int col = 1;
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        col = 2;

        UserPK pkUser = new UserPK(resultSet.getInt(col++));

        String textUser = resultSet.getString(col++);

        ChallengeQuestionPK pkChallengeQuestion = new ChallengeQuestionPK(resultSet.getInt(col++), resultSet.getString(col++));

        String textChallengeQuestion = resultSet.getString(col++);

        ChallengeQuestionCK ckChallengeQuestion = new ChallengeQuestionCK(pkUser, pkChallengeQuestion);

        UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

        ChallengeQuestionRefImpl erChallengeQuestion = new ChallengeQuestionRefImpl(ckChallengeQuestion, textChallengeQuestion);

        int col1 = resultSet.getInt(col++);
        String col2 = resultSet.getString(col++);
        String col3 = resultSet.getString(col++);

        results.add(erChallengeQuestion, erUser, col1, col2, col3);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(SQL_ALL_CHALLENGE_QUESTIONS, sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }

    if (timer != null) {
      timer.logDebug("getAllChallengeQuestions", " items=" + results.size());
    }

    return results;
  }

  public AllQuestionsAndAnswersByUserIDELO getAllQuestionsAndAnswersByUserID(int param1)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    AllQuestionsAndAnswersByUserIDELO results = new AllQuestionsAndAnswersByUserIDELO();
    try
    {
      stmt = getConnection().prepareStatement(SQL_ALL_QUESTIONS_AND_ANSWERS_BY_USER_ID);
      int col = 1;
      stmt.setInt(col++, param1);
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        col = 2;

        UserPK pkUser = new UserPK(resultSet.getInt(2));

        String textUser = resultSet.getString(3);
        
        String textChallengeQuestion = resultSet.getString(4);
        
        ChallengeQuestionPK pkChallengeQuestion = new ChallengeQuestionPK(resultSet.getInt(2), resultSet.getString(4));        

        ChallengeQuestionCK ckChallengeQuestion = new ChallengeQuestionCK(pkUser, pkChallengeQuestion);

        UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

        ChallengeQuestionRefImpl erChallengeQuestion = new ChallengeQuestionRefImpl(ckChallengeQuestion, textChallengeQuestion);

        String answer = resultSet.getString(5);

        results.add(erChallengeQuestion, erUser, textChallengeQuestion , answer);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(SQL_ALL_QUESTIONS_AND_ANSWERS_BY_USER_ID, sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }

    if (timer != null) {
      timer.logDebug("getAllQuestionsAndAnswersByUserID", " UserId=" + param1 + " items=" + results.size());
    }

    return results;
  }
  
  public boolean update(Map items)
    throws DuplicateNameValidationException, VersionValidationException, ValidationException
  {
    if (items == null) {
      return false;
    }
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement deleteStmt = null;

    boolean somethingChanged = false;
    try
    {
      Iterator iter2 = new ArrayList(items.values()).iterator();
      while (iter2.hasNext())
      {
        mDetails = ((ChallengeQuestionEVO)iter2.next());

        if (mDetails.deletePending())
        {
          somethingChanged = true;

          if (deleteStmt == null) {
            deleteStmt = getConnection().prepareStatement("delete from CHALLENGE_QUESTION where    USER_ID = ? AND QUESTION_TEXT = ? ");
          }

          int col = 1;
          deleteStmt.setInt(col++, mDetails.getUserId());
          deleteStmt.setString(col++, mDetails.getQuestionText());

          if (_log.isDebugEnabled()) {
            _log.debug("update", "ChallengeQuestion deleting UserId=" + mDetails.getUserId() + ",QuestionText=" + mDetails.getQuestionText());
          }

          deleteStmt.addBatch();

          items.remove(mDetails.getPK());
        }

      }

      if (deleteStmt != null)
      {
        Timer timer2 = _log.isDebugEnabled() ? new Timer(_log) : null;

        deleteStmt.executeBatch();

        if (timer2 != null) {
          timer2.logDebug("update", "delete batch");
        }
      }

      Iterator iter1 = items.values().iterator();
      while (iter1.hasNext())
      {
        mDetails = ((ChallengeQuestionEVO)iter1.next());

        if (mDetails.insertPending())
        {
          somethingChanged = true;
          doCreate();
        }
        else if (mDetails.isModified())
        {
          somethingChanged = true;
          doStore();
        }

      }

      return somethingChanged;
    }
    catch (SQLException sqle)
    {
      throw handleSQLException("delete from CHALLENGE_QUESTION where    USER_ID = ? AND QUESTION_TEXT = ? ", sqle);
    }
    finally
    {
      if (deleteStmt != null)
      {
        closeStatement(deleteStmt);
        closeConnection();
      }

      mDetails = null;

      if ((somethingChanged) && 
        (timer != null))
        timer.logDebug("update", "collection");
    }
  }

  public void bulkGetAll(UserPK entityPK, UserEVO owningEVO, String dependants)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    PreparedStatement stmt = null;
    ResultSet resultSet = null;

    int itemCount = 0;

    Collection theseItems = new ArrayList();
    owningEVO.setChallengeQuestions(theseItems);
    owningEVO.setChallengeQuestionsAllItemsLoaded(true);
    try
    {
      stmt = getConnection().prepareStatement("select CHALLENGE_QUESTION.USER_ID,CHALLENGE_QUESTION.QUESTION_TEXT,CHALLENGE_QUESTION.QUESTION_ANSWER,CHALLENGE_QUESTION.VERSION_NUM,CHALLENGE_QUESTION.UPDATED_BY_USER_ID,CHALLENGE_QUESTION.UPDATED_TIME,CHALLENGE_QUESTION.CREATED_TIME from CHALLENGE_QUESTION where 1=1 and CHALLENGE_QUESTION.USER_ID = ? order by  CHALLENGE_QUESTION.USER_ID ,CHALLENGE_QUESTION.QUESTION_TEXT");

      int col = 1;
      stmt.setInt(col++, entityPK.getUserId());

      resultSet = stmt.executeQuery();

      while (resultSet.next())
      {
        itemCount++;
        mDetails = getEvoFromJdbc(resultSet);

        theseItems.add(mDetails);
      }

      if (timer != null) {
        timer.logDebug("bulkGetAll", "items=" + itemCount);
      }
    }
    catch (SQLException sqle)
    {
      throw handleSQLException("select CHALLENGE_QUESTION.USER_ID,CHALLENGE_QUESTION.QUESTION_TEXT,CHALLENGE_QUESTION.QUESTION_ANSWER,CHALLENGE_QUESTION.VERSION_NUM,CHALLENGE_QUESTION.UPDATED_BY_USER_ID,CHALLENGE_QUESTION.UPDATED_TIME,CHALLENGE_QUESTION.CREATED_TIME from CHALLENGE_QUESTION where 1=1 and CHALLENGE_QUESTION.USER_ID = ? order by  CHALLENGE_QUESTION.USER_ID ,CHALLENGE_QUESTION.QUESTION_TEXT", sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();

      mDetails = null;
    }
  }

  public Collection getAll(int selectUserId, String dependants, Collection currentList)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;

    ArrayList items = new ArrayList();
    try
    {
      stmt = getConnection().prepareStatement("select CHALLENGE_QUESTION.USER_ID,CHALLENGE_QUESTION.QUESTION_TEXT,CHALLENGE_QUESTION.QUESTION_ANSWER,CHALLENGE_QUESTION.VERSION_NUM,CHALLENGE_QUESTION.UPDATED_BY_USER_ID,CHALLENGE_QUESTION.UPDATED_TIME,CHALLENGE_QUESTION.CREATED_TIME from CHALLENGE_QUESTION where    USER_ID = ? ");

      int col = 1;
      stmt.setInt(col++, selectUserId);

      resultSet = stmt.executeQuery();

      while (resultSet.next())
      {
        mDetails = getEvoFromJdbc(resultSet);

        items.add(mDetails);
      }

      if (currentList != null)
      {
        ListIterator iter = items.listIterator();
        ChallengeQuestionEVO currentEVO = null;
        ChallengeQuestionEVO newEVO = null;
        while (iter.hasNext())
        {
          newEVO = (ChallengeQuestionEVO)iter.next();
          Iterator iter2 = currentList.iterator();
          while (iter2.hasNext())
          {
            currentEVO = (ChallengeQuestionEVO)iter2.next();
            if (currentEVO.getPK().equals(newEVO.getPK()))
            {
              iter.set(currentEVO);
            }
          }

        }

        Iterator iter2 = currentList.iterator();
        while (iter2.hasNext())
        {
          currentEVO = (ChallengeQuestionEVO)iter2.next();
          if (currentEVO.insertPending()) {
            items.add(currentEVO);
          }
        }
      }
      mDetails = null;
    }
    catch (SQLException sqle)
    {
      throw handleSQLException("select CHALLENGE_QUESTION.USER_ID,CHALLENGE_QUESTION.QUESTION_TEXT,CHALLENGE_QUESTION.QUESTION_ANSWER,CHALLENGE_QUESTION.VERSION_NUM,CHALLENGE_QUESTION.UPDATED_BY_USER_ID,CHALLENGE_QUESTION.UPDATED_TIME,CHALLENGE_QUESTION.CREATED_TIME from CHALLENGE_QUESTION where    USER_ID = ? ", sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();

      if (timer != null) {
        timer.logDebug("getAll", " UserId=" + selectUserId + " items=" + items.size());
      }

    }

    return items;
  }

  public ChallengeQuestionEVO getDetails(UserCK paramCK, String dependants)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    if (mDetails == null) {
      doLoad(((ChallengeQuestionCK)paramCK).getChallengeQuestionPK());
    }
    else if (!mDetails.getPK().equals(((ChallengeQuestionCK)paramCK).getChallengeQuestionPK())) {
      doLoad(((ChallengeQuestionCK)paramCK).getChallengeQuestionPK());
    }

    ChallengeQuestionEVO details = new ChallengeQuestionEVO();
    details = mDetails.deepClone();

    if (timer != null) {
      timer.logDebug("getDetails", paramCK + " " + dependants);
    }
    return details;
  }

  public ChallengeQuestionEVO getDetails(UserCK paramCK, ChallengeQuestionEVO paramEVO, String dependants)
    throws ValidationException
  {
    ChallengeQuestionEVO savedEVO = mDetails;
    mDetails = paramEVO;
    ChallengeQuestionEVO newEVO = getDetails(paramCK, dependants);
    mDetails = savedEVO;
    return newEVO;
  }

  public ChallengeQuestionEVO getDetails(String dependants)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    ChallengeQuestionEVO details = mDetails.deepClone();

    if (timer != null) {
      timer.logDebug("getDetails", mDetails.getPK() + " " + dependants);
    }
    return details;
  }

  public String getEntityName()
  {
    return "ChallengeQuestion";
  }

  public ChallengeQuestionRefImpl getRef(ChallengeQuestionPK paramChallengeQuestionPK)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    try
    {
      stmt = getConnection().prepareStatement("select 0,USR.USER_ID,CHALLENGE_QUESTION.QUESTION_TEXT from CHALLENGE_QUESTION\njoin USR on (1=1\nand CHALLENGE_QUESTION.USER_ID = USR.USER_ID\n) where 1=1 and CHALLENGE_QUESTION.USER_ID = ? and CHALLENGE_QUESTION.QUESTION_TEXT = ?");
      int col = 1;
      stmt.setInt(col++, paramChallengeQuestionPK.getUserId());
      stmt.setString(col++, paramChallengeQuestionPK.getQuestionText());

      resultSet = stmt.executeQuery();

      if (!resultSet.next()) {
        throw new RuntimeException(getEntityName() + " getRef " + paramChallengeQuestionPK + " not found");
      }
      col = 2;
      UserPK newUserPK = new UserPK(resultSet.getInt(col++));

      String textChallengeQuestion = resultSet.getString(col++);
      ChallengeQuestionCK ckChallengeQuestion = new ChallengeQuestionCK(newUserPK, paramChallengeQuestionPK);

      return new ChallengeQuestionRefImpl(ckChallengeQuestion, textChallengeQuestion);
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(paramChallengeQuestionPK, "select 0,USR.USER_ID,CHALLENGE_QUESTION.QUESTION_TEXT from CHALLENGE_QUESTION\njoin USR on (1=1\nand CHALLENGE_QUESTION.USER_ID = USR.USER_ID\n) where 1=1 and CHALLENGE_QUESTION.USER_ID = ? and CHALLENGE_QUESTION.QUESTION_TEXT = ?", sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();

      if (timer != null)
        timer.logDebug("getRef", paramChallengeQuestionPK);
    }
  }
  
  public AllQuestionsAndAnswersByUserIDELO getChallengeWord(int userId)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    AllQuestionsAndAnswersByUserIDELO results = new AllQuestionsAndAnswersByUserIDELO();
    try
    {
      stmt = getConnection().prepareStatement(GET_CHALLENGE_WORD);
      int col = 1;
      stmt.setInt(col++, userId);
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        col = 2;

        UserPK pkUser = new UserPK(resultSet.getInt(2));

        String textUser = resultSet.getString(3);
        
        String fakeQuestion = "HIDDEN_2ND_PASSWORD";
        
        ChallengeQuestionPK pkChallengeQuestion = new ChallengeQuestionPK(resultSet.getInt(2), fakeQuestion);

        ChallengeQuestionCK ckChallengeQuestion = new ChallengeQuestionCK(pkUser, pkChallengeQuestion);

        UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

        ChallengeQuestionRefImpl erChallengeQuestion = new ChallengeQuestionRefImpl(ckChallengeQuestion, fakeQuestion);

        String word = resultSet.getString(4);

        results.add(erChallengeQuestion, erUser, fakeQuestion , word);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(GET_CHALLENGE_WORD, sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }

    if (timer != null) {
      timer.logDebug("getChallengeWord", " UserId=" + userId + " items=" + results.size());
    }

    return results;
  }

	
	public void setChallengeWord(int userId, String word) {
		if (word != null && word.length() > 0) {			
			Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		    PreparedStatement stmt = null;
		    PreparedStatement stmt1 = null;
		    boolean insert = true;
		    try
		    {
		      stmt = getConnection().prepareStatement(GET_CHALLENGE_WORD);
		      stmt.setInt(1, userId);
		      ResultSet resultSet = stmt.executeQuery();
		      while (resultSet.next())
		      {
		    	  insert = false;
		      }
		      closeResultSet(resultSet);
		      if (insert) {
		    	  stmt1 = getConnection().prepareStatement(INSERT_CHALLENGE_WORD);
		    	  stmt1.setInt(1, userId);
			      stmt1.setString(2, word);
			      Timestamp now = new Timestamp(new Date().getTime());
			      stmt1.setTimestamp(3, now);
			      stmt1.setTimestamp(4, now);
		      } else {
		    	  stmt1 = getConnection().prepareStatement(UPDATE_CHALLENGE_WORD);
			      stmt1.setString(1, word);
			      Timestamp now = new Timestamp(new Date().getTime());
			      stmt1.setTimestamp(2, now);
		    	  stmt1.setInt(3, userId);
			      
		      }
		      if (stmt1.executeUpdate() == 0) {
		    	  throw new SQLException("Can't set the challenge word.");
		      }
		    }
		    catch (SQLException sqle)
		    {
		      throw handleSQLException(GET_CHALLENGE_WORD, sqle);
		    }
		    finally
		    {
		      closeStatement(stmt);
		      closeStatement(stmt1);
		      closeConnection();
		    }
	
		    if (timer != null) {
		      timer.logDebug("setChallengeWord", " UserId=" + userId);
		    }	
		}
	}
}