package com.cedar.cp.ejb.impl.masterquestion;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.masterquestion.MasterQuestionRef;
import com.cedar.cp.dto.masterquestion.AllMasterQuestionsELO;
import com.cedar.cp.dto.masterquestion.MasterQuestionCK;
import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
import com.cedar.cp.dto.masterquestion.MasterQuestionRefImpl;
import com.cedar.cp.dto.masterquestion.QuestionByIDELO;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.sql.DataSource;

public class MasterQuestionDAO extends AbstractDAO
{
  Log _log = new Log(getClass());
  protected static final String SQL_FIND_BY_PRIMARY_KEY = "select QUESTION_ID from MASTER_QUESTION where    QUESTION_ID = ? ";
  private static final String SQL_SELECT_COLUMNS = "select MASTER_QUESTION.QUESTION_ID,MASTER_QUESTION.QUESTION_TEXT,MASTER_QUESTION.VERSION_NUM,MASTER_QUESTION.UPDATED_BY_USER_ID,MASTER_QUESTION.UPDATED_TIME,MASTER_QUESTION.CREATED_TIME";
  protected static final String SQL_LOAD = " from MASTER_QUESTION where    QUESTION_ID = ? ";
  protected static final String SQL_CREATE = "insert into MASTER_QUESTION ( QUESTION_ID,QUESTION_TEXT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)";
  protected static final String SQL_UPDATE_SEQ_NUM = "update MASTER_QUESTION_SEQ set SEQ_NUM = SEQ_NUM + ?";
  protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from MASTER_QUESTION_SEQ";
  protected static final String SQL_STORE = "update MASTER_QUESTION set QUESTION_TEXT = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    QUESTION_ID = ? AND VERSION_NUM = ?";
  protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from MASTER_QUESTION where QUESTION_ID = ?";
  protected static final String SQL_REMOVE = "delete from MASTER_QUESTION where    QUESTION_ID = ? ";
  protected static String SQL_ALL_MASTER_QUESTIONS = "select 0       ,MASTER_QUESTION.QUESTION_ID      ,MASTER_QUESTION.QUESTION_TEXT      ,MASTER_QUESTION.QUESTION_ID      ,MASTER_QUESTION.QUESTION_TEXT from MASTER_QUESTION where 1=1 ";

  protected static String SQL_QUESTION_BY_I_D = "select 0       ,MASTER_QUESTION.QUESTION_ID      ,MASTER_QUESTION.QUESTION_TEXT      ,MASTER_QUESTION.QUESTION_TEXT from MASTER_QUESTION where 1=1  and  MASTER_QUESTION.QUESTION_ID = ?";
  private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from MASTER_QUESTION where   QUESTION_ID = ?";
  protected MasterQuestionEVO mDetails;

  public MasterQuestionDAO(Connection connection)
  {
    super(connection);
  }

  public MasterQuestionDAO()
  {
  }

  public MasterQuestionDAO(DataSource ds)
  {
    super(ds);
  }

  protected MasterQuestionPK getPK()
  {
    return mDetails.getPK();
  }

  public void setDetails(MasterQuestionEVO details)
  {
    mDetails = details.deepClone();
  }

  public MasterQuestionEVO setAndGetDetails(MasterQuestionEVO details, String dependants)
  {
    setDetails(details);
    generateKeys();
    return mDetails.deepClone();
  }

  public MasterQuestionPK create()
    throws DuplicateNameValidationException, ValidationException
  {
    doCreate();

    return mDetails.getPK();
  }

  public void load(MasterQuestionPK pk)
    throws ValidationException
  {
    doLoad(pk);
  }

  public void store()
    throws DuplicateNameValidationException, VersionValidationException, ValidationException
  {
    doStore();
  }

  public void remove()
  {
    doRemove();
  }

  public MasterQuestionPK findByPrimaryKey(MasterQuestionPK pk_)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    if (exists(pk_))
    {
      if (timer != null) {
        timer.logDebug("findByPrimaryKey", pk_);
      }
      return pk_;
    }

    throw new ValidationException(pk_ + " not found");
  }

  protected boolean exists(MasterQuestionPK pk)
  {
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    boolean returnValue = false;
    try
    {
      stmt = getConnection().prepareStatement("select QUESTION_ID from MASTER_QUESTION where    QUESTION_ID = ? ");

      int col = 1;
      stmt.setInt(col++, pk.getQuestionId());

      resultSet = stmt.executeQuery();

      if (!resultSet.next())
        returnValue = false;
      else
        returnValue = true;
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(pk, "select QUESTION_ID from MASTER_QUESTION where    QUESTION_ID = ? ", sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }
    return returnValue;
  }

  private MasterQuestionEVO getEvoFromJdbc(ResultSet resultSet_)
    throws SQLException
  {
    int col = 1;
    MasterQuestionEVO evo = new MasterQuestionEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getInt(col++));

    evo.setUpdatedByUserId(resultSet_.getInt(col++));
    evo.setUpdatedTime(resultSet_.getTimestamp(col++));
    evo.setCreatedTime(resultSet_.getTimestamp(col++));
    return evo;
  }

  private int putEvoKeysToJdbc(MasterQuestionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
  {
    int col = startCol_;
    stmt_.setInt(col++, evo_.getQuestionId());
    return col;
  }

  private int putEvoDataToJdbc(MasterQuestionEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
  {
    int col = startCol_;
    stmt_.setString(col++, evo_.getQuestionText());
    stmt_.setInt(col++, evo_.getVersionNum());
    stmt_.setInt(col++, evo_.getUpdatedByUserId());
    stmt_.setTimestamp(col++, evo_.getUpdatedTime());
    stmt_.setTimestamp(col++, evo_.getCreatedTime());
    return col;
  }

  protected void doLoad(MasterQuestionPK pk)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    try
    {
      stmt = getConnection().prepareStatement("select MASTER_QUESTION.QUESTION_ID,MASTER_QUESTION.QUESTION_TEXT,MASTER_QUESTION.VERSION_NUM,MASTER_QUESTION.UPDATED_BY_USER_ID,MASTER_QUESTION.UPDATED_TIME,MASTER_QUESTION.CREATED_TIME from MASTER_QUESTION where    QUESTION_ID = ? ");

      int col = 1;
      stmt.setInt(col++, pk.getQuestionId());

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
      throw handleSQLException(pk, "select MASTER_QUESTION.QUESTION_ID,MASTER_QUESTION.QUESTION_TEXT,MASTER_QUESTION.VERSION_NUM,MASTER_QUESTION.UPDATED_BY_USER_ID,MASTER_QUESTION.UPDATED_TIME,MASTER_QUESTION.CREATED_TIME from MASTER_QUESTION where    QUESTION_ID = ? ", sqle);
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
    generateKeys();

    PreparedStatement stmt = null;
    try
    {
      mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
      mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
      stmt = getConnection().prepareStatement("insert into MASTER_QUESTION ( QUESTION_ID,QUESTION_TEXT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)");

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
      throw handleSQLException(mDetails.getPK(), "insert into MASTER_QUESTION ( QUESTION_ID,QUESTION_TEXT,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?)", sqle);
    }
    finally
    {
      closeStatement(stmt);
      closeConnection();

      if (timer != null)
        timer.logDebug("doCreate", mDetails.toString());
    }
  }

  public int reserveIds(int insertCount)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    String sqlString = null;
    try
    {
      sqlString = "update MASTER_QUESTION_SEQ set SEQ_NUM = SEQ_NUM + ?";
      stmt = getConnection().prepareStatement("update MASTER_QUESTION_SEQ set SEQ_NUM = SEQ_NUM + ?");
      stmt.setInt(1, insertCount);

      int resultCount = stmt.executeUpdate();
      if (resultCount != 1) {
        throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
      }
      closeStatement(stmt);

      sqlString = "select SEQ_NUM from MASTER_QUESTION_SEQ";
      stmt = getConnection().prepareStatement("select SEQ_NUM from MASTER_QUESTION_SEQ");
      resultSet = stmt.executeQuery();
      if (!resultSet.next())
        throw new RuntimeException(getEntityName() + " reserveIds: select failed");
      int latestKey = resultSet.getInt(1);

      return latestKey - insertCount;
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(sqlString, sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();

      if (timer != null)
        timer.logDebug("reserveIds", "keys=" + insertCount);
    }
  }

  public MasterQuestionPK generateKeys()
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    int insertCount = mDetails.getInsertCount(0);

    if (insertCount == 0) {
      return mDetails.getPK();
    }
    mDetails.assignNextKey(reserveIds(insertCount));

    return mDetails.getPK();
  }

  protected void doStore()
    throws DuplicateNameValidationException, VersionValidationException, ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    generateKeys();

    PreparedStatement stmt = null;

    boolean mainChanged = mDetails.isModified();
    boolean dependantChanged = false;
    try
    {
      if ((mainChanged) || (dependantChanged))
      {
        mDetails.setVersionNum(mDetails.getVersionNum() + 1);

        mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
        stmt = getConnection().prepareStatement("update MASTER_QUESTION set QUESTION_TEXT = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    QUESTION_ID = ? AND VERSION_NUM = ?");

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
      throw handleSQLException(getPK(), "update MASTER_QUESTION set QUESTION_TEXT = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    QUESTION_ID = ? AND VERSION_NUM = ?", sqle);
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
      stmt = getConnection().prepareStatement("select VERSION_NUM from MASTER_QUESTION where QUESTION_ID = ?");

      int col = 1;
      stmt.setInt(col++, mDetails.getQuestionId());

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
      throw handleSQLException(getPK(), "select VERSION_NUM from MASTER_QUESTION where QUESTION_ID = ?", sqle);
    }
    finally
    {
      closeStatement(stmt);
      closeResultSet(resultSet);

      if (timer != null)
        timer.logDebug("checkVersionNum", mDetails.getPK());
    }
  }

  protected void doRemove()
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    PreparedStatement stmt = null;
    try
    {
      stmt = getConnection().prepareStatement("delete from MASTER_QUESTION where    QUESTION_ID = ? ");

      int col = 1;
      stmt.setInt(col++, mDetails.getQuestionId());

      int resultCount = stmt.executeUpdate();

      if (resultCount != 1) {
        throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(getPK(), "delete from MASTER_QUESTION where    QUESTION_ID = ? ", sqle);
    }
    finally
    {
      closeStatement(stmt);
      closeConnection();

      if (timer != null)
        timer.logDebug("remove", mDetails.getPK());
    }
  }

  public AllMasterQuestionsELO getAllMasterQuestions()
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    AllMasterQuestionsELO results = new AllMasterQuestionsELO();
    try
    {
      stmt = getConnection().prepareStatement(SQL_ALL_MASTER_QUESTIONS);
      int col = 1;
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        col = 2;

        MasterQuestionPK pkMasterQuestion = new MasterQuestionPK(resultSet.getInt(col++));

        String textMasterQuestion = resultSet.getString(col++);

        MasterQuestionRefImpl erMasterQuestion = new MasterQuestionRefImpl(pkMasterQuestion, textMasterQuestion);

        int col1 = resultSet.getInt(col++);
        String col2 = resultSet.getString(col++);

        results.add(erMasterQuestion, col1, col2);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(SQL_ALL_MASTER_QUESTIONS, sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }

    if (timer != null) {
      timer.logDebug("getAllMasterQuestions", " items=" + results.size());
    }

    return results;
  }

  public QuestionByIDELO getQuestionByID(int param1)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    QuestionByIDELO results = new QuestionByIDELO();
    try
    {
      stmt = getConnection().prepareStatement(SQL_QUESTION_BY_I_D);
      int col = 1;
      stmt.setInt(col++, param1);
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        col = 2;

        MasterQuestionPK pkMasterQuestion = new MasterQuestionPK(resultSet.getInt(col++));

        String textMasterQuestion = resultSet.getString(col++);

        MasterQuestionRefImpl erMasterQuestion = new MasterQuestionRefImpl(pkMasterQuestion, textMasterQuestion);

        String col1 = resultSet.getString(col++);

        results.add(erMasterQuestion, col1);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(SQL_QUESTION_BY_I_D, sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }

    if (timer != null) {
      timer.logDebug("getQuestionByID", " QuestionId=" + param1 + " items=" + results.size());
    }

    return results;
  }

  public MasterQuestionEVO getDetails(MasterQuestionPK pk, String dependants)
    throws ValidationException
  {
    return getDetails(new MasterQuestionCK(pk), dependants);
  }

  public MasterQuestionEVO getDetails(MasterQuestionCK paramCK, String dependants)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    if (mDetails == null) {
      doLoad(paramCK.getMasterQuestionPK());
    }
    else if (!mDetails.getPK().equals(paramCK.getMasterQuestionPK())) {
      doLoad(paramCK.getMasterQuestionPK());
    }
    else if (!checkIfValid())
    {
      _log.info("getDetails", "[ALERT] MasterQuestionEVO " + mDetails.getPK() + " no longer valid - reloading");

      doLoad(paramCK.getMasterQuestionPK());
    }

    MasterQuestionEVO details = new MasterQuestionEVO();
    details = mDetails.deepClone();

    if (timer != null) {
      timer.logDebug("getDetails", paramCK + " " + dependants);
    }
    return details;
  }

  private boolean checkIfValid()
  {
    boolean stillValid = false;
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    try
    {
      stmt = getConnection().prepareStatement("select VERSION_NUM from MASTER_QUESTION where   QUESTION_ID = ?");
      int col = 1;
      stmt.setInt(col++, mDetails.getQuestionId());

      resultSet = stmt.executeQuery();

      if (!resultSet.next()) {
        throw new RuntimeException(getEntityName() + " checkIfValid " + mDetails.getPK() + " not found");
      }
      col = 1;
      int dbVersionNum = resultSet.getInt(col++);

      if (dbVersionNum == mDetails.getVersionNum())
        stillValid = true;
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(mDetails.getPK(), "select VERSION_NUM from MASTER_QUESTION where   QUESTION_ID = ?", sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();

      if (timer != null) {
        timer.logDebug("checkIfValid", mDetails.getPK());
      }
    }
    return stillValid;
  }

  public MasterQuestionEVO getDetails(String dependants)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    if (!checkIfValid())
    {
      _log.info("getDetails", "MasterQuestion " + mDetails.getPK() + " no longer valid - reloading");
      doLoad(mDetails.getPK());
    }

    MasterQuestionEVO details = mDetails.deepClone();

    if (timer != null) {
      timer.logDebug("getDetails", mDetails.getPK() + " " + dependants);
    }
    return details;
  }

  public String getEntityName()
  {
    return "MasterQuestion";
  }

  public MasterQuestionRef getRef(MasterQuestionPK paramMasterQuestionPK)
    throws ValidationException
  {
    MasterQuestionEVO evo = getDetails(paramMasterQuestionPK, "");
    return evo.getEntityRef();
  }
}