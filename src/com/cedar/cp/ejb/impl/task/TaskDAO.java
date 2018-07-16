package com.cedar.cp.ejb.impl.task;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.task.TaskRef;
import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.task.AllWebTasksELO;
import com.cedar.cp.dto.task.AllWebTasksForUserELO;
import com.cedar.cp.dto.task.TaskCK;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.dto.task.TaskRefImpl;
import com.cedar.cp.dto.task.WebTasksDetailsELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.base.SqlExecutor;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.SqlBuilder;
import com.cedar.cp.util.Timer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import oracle.sql.BLOB;

public class TaskDAO extends AbstractDAO
{
  Log _log = new Log(getClass());
  protected static final String SQL_FIND_BY_PRIMARY_KEY = "select TASK_ID from TASK where    TASK_ID = ? ";
  private static final String SQL_SELECT_LOBS = "select  OBJECTS from TASK where    TASK_ID = ? for update";
  private static final String SQL_SELECT_COLUMNS = "select TASK.OBJECTS,TASK.TASK_ID,TASK.TASK_TYPE,TASK.SYSTEM_TIME_MILLIS,TASK.TASK_NAME,TASK.USER_ID,TASK.ORIGINAL_TASK_ID,TASK.STATUS,TASK.MUST_COMPLETE,TASK.CREATE_DATE,TASK.END_DATE,TASK.STEP";
  protected static final String SQL_LOAD = " from TASK where    TASK_ID = ? ";
  protected static final String SQL_CREATE = "insert into TASK ( TASK_ID,TASK_TYPE,SYSTEM_TIME_MILLIS,TASK_NAME,USER_ID,ORIGINAL_TASK_ID,STATUS,MUST_COMPLETE,OBJECTS,CREATE_DATE,END_DATE,STEP) values ( ?,?,?,?,?,?,?,?,empty_blob(),?,?,?)";
  protected static final String SQL_UPDATE_SEQ_NUM = "update TASK_SEQ set SEQ_NUM = SEQ_NUM + ?";
  protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from TASK_SEQ";
  protected static final String SQL_STORE = "update TASK set TASK_TYPE = ?,SYSTEM_TIME_MILLIS = ?,TASK_NAME = ?,USER_ID = ?,ORIGINAL_TASK_ID = ?,STATUS = ?,MUST_COMPLETE = ?,CREATE_DATE = ?,END_DATE = ?,STEP = ? where    TASK_ID = ? ";
  protected static final String SQL_REMOVE = "delete from TASK where    TASK_ID = ? ";
  protected static String SQL_ALL_WEB_TASKS = "select 0       ,TASK.TASK_ID      ,TASK.TASK_NAME      ,USR.USER_ID      ,USR.NAME      ,TASK.TASK_ID      ,TASK.TASK_NAME      ,TASK.USER_ID      ,USR.NAME      ,TASK.ORIGINAL_TASK_ID      ,TASK.STATUS      ,TASK.CREATE_DATE      ,TASK.END_DATE      ,TASK.STEP from TASK    ,USR where 1=1  and  USR.USER_ID = TASK.USER_ID order by CREATE_DATE desc";

  protected static String SQL_ALL_WEB_TASKS_FOR_USER = "select 0       ,TASK.TASK_ID      ,TASK.TASK_NAME      ,USR.USER_ID      ,USR.NAME      ,TASK.TASK_ID      ,TASK.TASK_NAME      ,TASK.USER_ID      ,USR.NAME      ,TASK.ORIGINAL_TASK_ID      ,TASK.STATUS      ,TASK.CREATE_DATE      ,TASK.END_DATE      ,TASK.STEP from TASK    ,USR where 1=1  and  USR.USER_ID = TASK.USER_ID and USR.NAME = ? order by CREATE_DATE";

  protected static String SQL_WEB_TASKS_DETAILS = "select 0       ,TASK.TASK_ID      ,TASK.TASK_NAME      ,USR.USER_ID      ,USR.NAME      ,TASK.TASK_ID      ,TASK.TASK_NAME      ,TASK.USER_ID      ,USR.NAME      ,TASK.ORIGINAL_TASK_ID      ,TASK.STATUS      ,TASK.CREATE_DATE      ,TASK.END_DATE      ,TASK.STEP from TASK    ,USR where 1=1  and  USR.USER_ID = TASK.USER_ID and TASK.TASK_ID = ? order by CREATE_DATE";
  protected TaskEVO mDetails;
  private BLOB mObjectsBlob;

  public TaskDAO(Connection connection)
  {
    super(connection);
  }

  public TaskDAO()
  {
  }

  public TaskDAO(DataSource ds)
  {
    super(ds);
  }

  protected TaskPK getPK()
  {
    return mDetails.getPK();
  }

  public void setDetails(TaskEVO details)
  {
    mDetails = details.deepClone();
  }

  public TaskEVO setAndGetDetails(TaskEVO details, String dependants)
  {
    setDetails(details);
    generateKeys();
    return mDetails.deepClone();
  }

  public TaskPK create()
    throws DuplicateNameValidationException, ValidationException
  {
    doCreate();

    return mDetails.getPK();
  }

  public void load(TaskPK pk)
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

  public TaskPK findByPrimaryKey(TaskPK pk_)
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

  protected boolean exists(TaskPK pk)
  {
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    boolean returnValue = false;
    try
    {
      stmt = getConnection().prepareStatement("select TASK_ID from TASK where    TASK_ID = ? ");

      int col = 1;
      stmt.setInt(col++, pk.getTaskId());

      resultSet = stmt.executeQuery();

      if (!resultSet.next())
        returnValue = false;
      else
        returnValue = true;
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(pk, "select TASK_ID from TASK where    TASK_ID = ? ", sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }
    return returnValue;
  }

  private void selectLobs(TaskEVO evo_)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    try
    {
      stmt = getConnection().prepareStatement("select  OBJECTS from TASK where    TASK_ID = ? for update");

      putEvoKeysToJdbc(evo_, stmt, 1);

      resultSet = stmt.executeQuery();

      int col = 1;
      while (resultSet.next())
      {
        mObjectsBlob = ((BLOB)resultSet.getBlob(col++));
      }
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(evo_.getPK(), "select  OBJECTS from TASK where    TASK_ID = ? for update", sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);

      if (timer != null)
        timer.logDebug("selectLobs", evo_.getPK());
    }
  }

  private void putLobs(TaskEVO evo_) throws SQLException
  {
    updateBlob(mObjectsBlob, evo_.getObjects());
  }

  private TaskEVO getEvoFromJdbc(ResultSet resultSet_)
    throws SQLException
  {
    int col = 1;
    mObjectsBlob = ((BLOB)resultSet_.getBlob(col++));
    TaskEVO evo = new TaskEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getLong(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), blobToByteArray(mObjectsBlob), resultSet_.getTimestamp(col++), resultSet_.getTimestamp(col++), resultSet_.getString(col++));

    return evo;
  }

  private byte[] getByteArray(InputStream bis_)
  {
    try
    {
      ByteArrayOutputStream bos = new ByteArrayOutputStream(bis_.available());
      int chunk;
      while ((chunk = bis_.read()) != -1)
        bos.write(chunk);
      return bos.toByteArray();
    }
    catch (IOException e)
    {
      _log.error("getByteArray", e);
      throw new RuntimeException(e.getMessage());
    }
  }

  private int putEvoKeysToJdbc(TaskEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
  {
    int col = startCol_;
    stmt_.setInt(col++, evo_.getTaskId());
    return col;
  }

  private int putEvoDataToJdbc(TaskEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
  {
    int col = startCol_;
    stmt_.setInt(col++, evo_.getTaskType());
    stmt_.setLong(col++, evo_.getSystemTimeMillis());
    stmt_.setString(col++, evo_.getTaskName());
    stmt_.setInt(col++, evo_.getUserId());
    stmt_.setInt(col++, evo_.getOriginalTaskId());
    stmt_.setInt(col++, evo_.getStatus());
    if (evo_.getMustComplete())
      stmt_.setString(col++, "Y");
    else {
      stmt_.setString(col++, " ");
    }
    stmt_.setTimestamp(col++, evo_.getCreateDate());
    stmt_.setTimestamp(col++, evo_.getEndDate());
    stmt_.setString(col++, evo_.getStep());
    return col;
  }

  protected void doLoad(TaskPK pk)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    try
    {
      stmt = getConnection().prepareStatement("select TASK.OBJECTS,TASK.TASK_ID,TASK.TASK_TYPE,TASK.SYSTEM_TIME_MILLIS,TASK.TASK_NAME,TASK.USER_ID,TASK.ORIGINAL_TASK_ID,TASK.STATUS,TASK.MUST_COMPLETE,TASK.CREATE_DATE,TASK.END_DATE,TASK.STEP from TASK where    TASK_ID = ? ");

      int col = 1;
      stmt.setInt(col++, pk.getTaskId());

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
      throw handleSQLException(pk, "select TASK.OBJECTS,TASK.TASK_ID,TASK.TASK_TYPE,TASK.SYSTEM_TIME_MILLIS,TASK.TASK_NAME,TASK.USER_ID,TASK.ORIGINAL_TASK_ID,TASK.STATUS,TASK.MUST_COMPLETE,TASK.CREATE_DATE,TASK.END_DATE,TASK.STEP from TASK where    TASK_ID = ? ", sqle);
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
      stmt = getConnection().prepareStatement("insert into TASK ( TASK_ID,TASK_TYPE,SYSTEM_TIME_MILLIS,TASK_NAME,USER_ID,ORIGINAL_TASK_ID,STATUS,MUST_COMPLETE,OBJECTS,CREATE_DATE,END_DATE,STEP) values ( ?,?,?,?,?,?,?,?,empty_blob(),?,?,?)");

      int col = 1;
      col = putEvoKeysToJdbc(mDetails, stmt, col);
      col = putEvoDataToJdbc(mDetails, stmt, col);

      int resultCount = stmt.executeUpdate();
      if (resultCount != 1)
      {
        throw new RuntimeException(getEntityName() + " insert failed (" + mDetails.getPK() + "): resultCount=" + resultCount);
      }

      selectLobs(mDetails);
      _log.debug("doCreate", "calling putLobs");
      putLobs(mDetails);

      mDetails.reset();
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(mDetails.getPK(), "insert into TASK ( TASK_ID,TASK_TYPE,SYSTEM_TIME_MILLIS,TASK_NAME,USER_ID,ORIGINAL_TASK_ID,STATUS,MUST_COMPLETE,OBJECTS,CREATE_DATE,END_DATE,STEP) values ( ?,?,?,?,?,?,?,?,empty_blob(),?,?,?)", sqle);
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
      sqlString = "update TASK_SEQ set SEQ_NUM = SEQ_NUM + ?";
      stmt = getConnection().prepareStatement("update TASK_SEQ set SEQ_NUM = SEQ_NUM + ?");
      stmt.setInt(1, insertCount);

      int resultCount = stmt.executeUpdate();
      if (resultCount != 1) {
        throw new RuntimeException(getEntityName() + " reserveIds: update failed: resultCount=" + resultCount);
      }
      closeStatement(stmt);

      sqlString = "select SEQ_NUM from TASK_SEQ";
      stmt = getConnection().prepareStatement("select SEQ_NUM from TASK_SEQ");
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

  public TaskPK generateKeys()
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
        stmt = getConnection().prepareStatement("update TASK set TASK_TYPE = ?,SYSTEM_TIME_MILLIS = ?,TASK_NAME = ?,USER_ID = ?,ORIGINAL_TASK_ID = ?,STATUS = ?,MUST_COMPLETE = ?,CREATE_DATE = ?,END_DATE = ?,STEP = ? where    TASK_ID = ? ");

        selectLobs(mDetails);
        putLobs(mDetails);

        int col = 1;
        col = putEvoDataToJdbc(mDetails, stmt, col);
        col = putEvoKeysToJdbc(mDetails, stmt, col);

        int resultCount = stmt.executeUpdate();

        if (resultCount != 1) {
          throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
        }

        mDetails.reset();
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(getPK(), "update TASK set TASK_TYPE = ?,SYSTEM_TIME_MILLIS = ?,TASK_NAME = ?,USER_ID = ?,ORIGINAL_TASK_ID = ?,STATUS = ?,MUST_COMPLETE = ?,CREATE_DATE = ?,END_DATE = ?,STEP = ? where    TASK_ID = ? ", sqle);
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

  protected void doRemove()
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    String deleteEvents = "delete from TASK_EVENT where TASK_ID = ?";
    PreparedStatement stmt = null;
    try
    {
      stmt = getConnection().prepareStatement(deleteEvents);

      int col = 1;
      stmt.setInt(col++, mDetails.getTaskId());

      int resultCount = stmt.executeUpdate();

      _log.info("doRemove", "deleted taskEvents=" + resultCount);
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(getPK(), deleteEvents, sqle);
    }
    finally
    {
      closeStatement(stmt);
      closeConnection();
    }

    String deleteCMs = "delete from CHANGE_MGMT where TASK_ID = ?";
    try
    {
      stmt = getConnection().prepareStatement(deleteCMs);

      int col = 1;
      stmt.setInt(col++, mDetails.getTaskId());

      int resultCount = stmt.executeUpdate();

      _log.info("doRemove", "deleted changeMgmts=" + resultCount);
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(getPK(), deleteCMs, sqle);
    }
    finally
    {
      closeStatement(stmt);
      closeConnection();
    }

    try
    {
      stmt = getConnection().prepareStatement("delete from TASK where    TASK_ID = ? ");

      int col = 1;
      stmt.setInt(col++, mDetails.getTaskId());

      int resultCount = stmt.executeUpdate();

      if (resultCount != 1) {
        throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(getPK(), "delete from TASK where    TASK_ID = ? ", sqle);
    }
    finally
    {
      closeStatement(stmt);
      closeConnection();

      if (timer != null)
        timer.logDebug("remove", mDetails.getPK());
    }
  }

  public AllWebTasksELO getAllWebTasks()
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    AllWebTasksELO results = new AllWebTasksELO();
    try
    {
      stmt = getConnection().prepareStatement(SQL_ALL_WEB_TASKS);
      int col = 1;
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        col = 2;

        TaskPK pkTask = new TaskPK(resultSet.getInt(col++));

        String textTask = resultSet.getString(col++);

        UserPK pkUser = new UserPK(resultSet.getInt(col++));

        String textUser = resultSet.getString(col++);

        TaskRefImpl erTask = new TaskRefImpl(pkTask, textTask);

        UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

        int col1 = resultSet.getInt(col++);
        String col2 = resultSet.getString(col++);
        int col3 = resultSet.getInt(col++);
        String col4 = resultSet.getString(col++);
        int col5 = resultSet.getInt(col++);
        int col6 = resultSet.getInt(col++);
        Timestamp col7 = resultSet.getTimestamp(col++);
        Timestamp col8 = resultSet.getTimestamp(col++);
        String col9 = resultSet.getString(col++);

        results.add(erTask, erUser, col1, col2, col3, col4, col5, col6, col7, col8, col9);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(SQL_ALL_WEB_TASKS, sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }

    if (timer != null) {
      timer.logDebug("getAllWebTasks", " items=" + results.size());
    }

    return results;
  }

  public AllWebTasksForUserELO getAllWebTasksForUser(String param1)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    AllWebTasksForUserELO results = new AllWebTasksForUserELO();
    try
    {
      stmt = getConnection().prepareStatement(SQL_ALL_WEB_TASKS_FOR_USER);
      int col = 1;
      stmt.setString(col++, param1);
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        col = 2;

        TaskPK pkTask = new TaskPK(resultSet.getInt(col++));

        String textTask = resultSet.getString(col++);

        UserPK pkUser = new UserPK(resultSet.getInt(col++));

        String textUser = resultSet.getString(col++);

        TaskRefImpl erTask = new TaskRefImpl(pkTask, textTask);

        UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

        int col1 = resultSet.getInt(col++);
        String col2 = resultSet.getString(col++);
        int col3 = resultSet.getInt(col++);
        String col4 = resultSet.getString(col++);
        int col5 = resultSet.getInt(col++);
        int col6 = resultSet.getInt(col++);
        Timestamp col7 = resultSet.getTimestamp(col++);
        Timestamp col8 = resultSet.getTimestamp(col++);
        String col9 = resultSet.getString(col++);

        results.add(erTask, erUser, col1, col2, col3, col4, col5, col6, col7, col8, col9);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(SQL_ALL_WEB_TASKS_FOR_USER, sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }

    if (timer != null) {
      timer.logDebug("getAllWebTasksForUser", " Name=" + param1 + " items=" + results.size());
    }

    return results;
  }

  public WebTasksDetailsELO getWebTasksDetails(int param1)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    WebTasksDetailsELO results = new WebTasksDetailsELO();
    try
    {
      stmt = getConnection().prepareStatement(SQL_WEB_TASKS_DETAILS);
      int col = 1;
      stmt.setInt(col++, param1);
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        col = 2;

        TaskPK pkTask = new TaskPK(resultSet.getInt(col++));

        String textTask = resultSet.getString(col++);

        UserPK pkUser = new UserPK(resultSet.getInt(col++));

        String textUser = resultSet.getString(col++);

        TaskRefImpl erTask = new TaskRefImpl(pkTask, textTask);

        UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

        int col1 = resultSet.getInt(col++);
        String col2 = resultSet.getString(col++);
        int col3 = resultSet.getInt(col++);
        String col4 = resultSet.getString(col++);
        int col5 = resultSet.getInt(col++);
        int col6 = resultSet.getInt(col++);
        Timestamp col7 = resultSet.getTimestamp(col++);
        Timestamp col8 = resultSet.getTimestamp(col++);
        String col9 = resultSet.getString(col++);

        results.add(erTask, erUser, col1, col2, col3, col4, col5, col6, col7, col8, col9);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(SQL_WEB_TASKS_DETAILS, sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }

    if (timer != null) {
      timer.logDebug("getWebTasksDetails", " TaskId=" + param1 + " items=" + results.size());
    }

    return results;
  }

  public TaskEVO getDetails(TaskPK pk, String dependants)
    throws ValidationException
  {
    return getDetails(new TaskCK(pk), dependants);
  }

  public TaskEVO getDetails(TaskCK paramCK, String dependants)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    if (mDetails == null) {
      doLoad(paramCK.getTaskPK());
    }
    else if (!mDetails.getPK().equals(paramCK.getTaskPK())) {
      doLoad(paramCK.getTaskPK());
    }

    TaskEVO details = new TaskEVO();
    details = mDetails.deepClone();

    if (timer != null) {
      timer.logDebug("getDetails", paramCK + " " + dependants);
    }
    return details;
  }

  public TaskEVO getDetails(String dependants)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    TaskEVO details = mDetails.deepClone();

    if (timer != null) {
      timer.logDebug("getDetails", mDetails.getPK() + " " + dependants);
    }
    return details;
  }

  public String getEntityName()
  {
    return "Task";
  }

  public TaskRef getRef(TaskPK paramTaskPK)
    throws ValidationException
  {
    TaskEVO evo = getDetails(paramTaskPK, "");
    return evo.getEntityRef();
  }

  public int getNewTaskId()
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    try
    {
      stmt = getConnection().prepareStatement("update TASK_SEQ set SEQ_NUM = SEQ_NUM + ?");
      stmt.setInt(1, 1);

      int resultCount = stmt.executeUpdate();
      if (resultCount != 1) {
        throw new RuntimeException(getEntityName() + " getNewTaskId: update failed: resultCount=" + resultCount);
      }
      closeStatement(stmt);

      stmt = getConnection().prepareStatement("select SEQ_NUM from TASK_SEQ");
      resultSet = stmt.executeQuery();
      if (!resultSet.next())
        throw new RuntimeException(getEntityName() + " getNewTaskId: select failed");
      return resultSet.getInt(1);
    }
    catch (SQLException sqle)
    {
      sqle.printStackTrace();
      throw new RuntimeException(getEntityName() + " getNewTaskId", sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();

      if (timer != null)
        timer.logDebug("getNewTaskId", "");
    }
  }

  public void autonomousUpdateTask(int taskId, int status, long systemTime, String step) throws SQLException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    if ((step != null) && (step.length() > 255))
      step = step.substring(0, 250) + " ...";
    CallableStatement stmt = null;
    try
    {
      stmt = getConnection().prepareCall("begin task_utils.autonomousUpdateTask(?,?,?,?); end; ");
      stmt.setInt(1, taskId);
      stmt.setInt(2, status);
      stmt.setLong(3, systemTime);
      stmt.setString(4, step);
      stmt.execute();
    }
    catch (SQLException sqle)
    {
      throw sqle;
    }
    finally
    {
      if (timer != null)
        timer.logDebug("autonomousUpdateTask");
      closeStatement(stmt);
      closeConnection();
    }
  }

  public int resetRunningTasks()
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;

    String sql = "insert into TASK_EVENT\nselect TASK_ID\n       ,TASK_EVENT_SEQ.NEXTVAL\n       ,sysdate\n       ," + String.valueOf(1) + "\n" + "       ,'**** task failed due to Application Server termination ****'\n" + "from   TASK\n" + "where  STATUS = " + 3;

    int eventCount = 0;
    try
    {
      stmt = getConnection().prepareStatement(sql);
      eventCount = stmt.executeUpdate();
    }
    catch (SQLException sqle)
    {
      sqle.printStackTrace();
    }
    finally
    {
      closeStatement(stmt);
      closeConnection();

      if (timer != null) {
        timer.logDebug("resetRunningTasks", "events=" + eventCount);
      }
    }
    sql = "update task\nset STATUS = 4\n   ,STEP = trim(substr('task was running when system terminated - ' || STEP,1,255))\nwhere STATUS = 3";

    int updateCount = 0;
    try
    {
      stmt = getConnection().prepareStatement(sql);
      updateCount = stmt.executeUpdate();
    }
    catch (SQLException sqle)
    {
      sqle.printStackTrace();
    }
    finally
    {
      closeStatement(stmt);
      closeConnection();

      if (timer != null)
        timer.logDebug("resetRunningTasks", "count=" + updateCount);
    }
    return updateCount;
  }

  public int resetNewTasks()
  {
    String sql = "update task set STATUS = 1\n where STATUS in  (0,2)";

    int updateCount = 0;
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    try
    {
      stmt = getConnection().prepareStatement(sql);
      updateCount = stmt.executeUpdate();
    }
    catch (SQLException sqle)
    {
      sqle.printStackTrace();
    }
    finally
    {
      closeStatement(stmt);
      closeConnection();

      if (timer != null)
        timer.logDebug("resetNewTasks", "count=" + updateCount);
    }
    return updateCount;
  }

  public void logEvent(int taskId, int eventType, String eventText)
  {
    if (eventText == null) {
      return;
    }
    int eventLength = eventText.length();
    int maxSize = 4000;
    int startCount = 0;
    int endCount = 1;
    String singleEventText = "";

    boolean doWork = true;
    while (doWork)
    {
      if (endCount * maxSize <= eventLength)
      {
        singleEventText = eventText.substring(startCount * maxSize, endCount * maxSize);
      }
      else
      {
        singleEventText = eventText.substring(startCount * maxSize, eventLength);
        doWork = false;
      }

      CallableStatement stmt = null;
      int col = 1;
      try
      {
        stmt = getConnection().prepareCall("begin cp_utils.logTaskEvent(?,?,?); end;");
        stmt.setInt(col++, taskId);
        stmt.setInt(col++, eventType);
        stmt.setString(col++, singleEventText);
        stmt.execute();
      }
      catch (SQLException sqle)
      {
        System.err.println(sqle);
        sqle.printStackTrace();

        throw new RuntimeException(getEntityName() + " ", sqle);
      }
      finally
      {
        closeStatement(stmt);
        closeConnection();
      }

      startCount++;
      endCount++;
    }
  }

  public EntityList getEvents(int taskId)
  {
    SqlBuilder sqlb = new SqlBuilder(new String[] { "select  EVENT_TYPE", "       ,EVENT_TIME", "       ,EVENT_TEXT", "from   TASK_EVENT", "where  TASK_ID = <taskId>", "order  by EVENT_ID" });

    SqlExecutor sqle = new SqlExecutor("getEvents", getDataSource(), sqlb, _log);
    sqle.addBindVariable("<taskId>", Integer.valueOf(taskId));
    sqle.setLogSql(false);
    return sqle.getEntityList();
  }

  public EntityList getTasks()
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    String[] headings = { "Task", "User", "TaskId", "TaskName", "isGroup", "UserName", "OriginalTaskId", "Status", "MustComplete", "CreateDate", "EndDate", "Step" };

    EntityListImpl results = new EntityListImpl(headings, new Object[0][headings.length]);

    String sql = "select TASK_ID, TASK_TYPE\n,TASK_NAME, USER_ID, ORIGINAL_TASK_ID, STATUS, MUST_COMPLETE\n,CREATE_DATE, END_DATE, STEP\n,u.NAME as USERNAME\nfrom TASK \nleft join USR u using (USER_ID)\norder by TASK_ID desc";
    try
    {
      stmt = getConnection().prepareStatement(sql);
      int col = 1;
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        List l = new ArrayList();
        l.add(new EntityRefImpl(new TaskPK(resultSet.getInt("TASK_ID")), ""));
        l.add(new EntityRefImpl(new UserPK(resultSet.getInt("USER_ID")), resultSet.getString("USERNAME")));
        l.add(new Integer(resultSet.getInt("TASK_ID")));
        l.add(resultSet.getString("TASK_NAME"));
        l.add(Boolean.valueOf(resultSet.getInt("TASK_TYPE") == 1));
        l.add(resultSet.getString("USERNAME"));
        l.add(Integer.valueOf(resultSet.getInt("ORIGINAL_TASK_ID")));
        l.add(new Integer(resultSet.getInt("STATUS")));
        l.add(Boolean.valueOf(resultSet.getString("MUST_COMPLETE").equals("Y")));
        l.add(resultSet.getTimestamp("CREATE_DATE"));
        l.add(resultSet.getTimestamp("END_DATE"));
        l.add(resultSet.getString("STEP"));

        results.add(l);
      }
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(sql, sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }

    if (timer != null) {
      timer.logDebug("getTasks", "items=" + results.getNumRows());
    }
    return results;
  }
  
  public EntityList getPageTasks(int page, int offset)
    {
      Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
      PreparedStatement stmt = null;
      ResultSet resultSet = null;
      String[] headings = { "Task", "User", "TaskId", "TaskName", "isGroup", "UserName", "OriginalTaskId", "Status", "MustComplete", "CreateDate", "EndDate", "Step" };
      
      EntityListImpl results = new EntityListImpl(headings, new Object[0][headings.length]);
      
      String sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(order by task.CREATE_DATE DESC) AS rn, task.TASK_ID, task.TASK_TYPE, task.TASK_NAME, task.USER_ID, task.ORIGINAL_TASK_ID, task.STATUS, task.MUST_COMPLETE, task.CREATE_DATE, task.END_DATE, STEP, u.NAME as USERNAME FROM TASK task LEFT JOIN USR u  ON (task.USER_ID   =u.USER_ID))WHERE rn > ? * ? AND rn <= ? * (? + 1)";
      try
      {
        stmt = getConnection().prepareStatement(sql);
        int col = 1;
        stmt.setInt(col++, offset);
        stmt.setInt(col++, page);
        stmt.setInt(col++, offset);
        stmt.setInt(col++, page);
        resultSet = stmt.executeQuery();
        while (resultSet.next())
        {
          List l = new ArrayList();
          l.add(new EntityRefImpl(new TaskPK(resultSet.getInt("TASK_ID")), ""));
          l.add(new EntityRefImpl(new UserPK(resultSet.getInt("USER_ID")), resultSet.getString("USERNAME")));
          l.add(new Integer(resultSet.getInt("TASK_ID")));
          l.add(resultSet.getString("TASK_NAME"));
          l.add(Boolean.valueOf(resultSet.getInt("TASK_TYPE") == 1));
          l.add(resultSet.getString("USERNAME"));
          l.add(Integer.valueOf(resultSet.getInt("ORIGINAL_TASK_ID")));
          l.add(new Integer(resultSet.getInt("STATUS")));
          l.add(Boolean.valueOf(resultSet.getString("MUST_COMPLETE").equals("Y")));
          l.add(resultSet.getTimestamp("CREATE_DATE"));
          l.add(resultSet.getTimestamp("END_DATE"));
          l.add(resultSet.getString("STEP"));
          
          results.add(l);
        }
      }
      catch (SQLException sqle)
      {
        throw handleSQLException(sql, sqle);
      }
      finally
      {
        closeResultSet(resultSet);
        closeStatement(stmt);
        closeConnection();
      }
      
      if (timer != null) {
        timer.logDebug("getPageTasks", "items=" + results.getNumRows());
      }
      return results;
    }

  public EntityList getIncompleteTasks()
  {
    SqlBuilder sqlb = new SqlBuilder(new String[] { "select  TASK_ID,TASK_TYPE,ORIGINAL_TASK_ID,TASK_NAME,USER_ID,STATUS,OBJECTS", "from    TASK", "where   STATUS not in (5,9,10)", "order   by TASK_ID" });

    SqlExecutor sqle = new SqlExecutor("getIncompleteTasks", getDataSource(), sqlb, _log);
    sqle.setLogSql(false);
    return sqle.getEntityList();
  }

  public boolean areAllIssuedTasksComplete(int originalTaskId, int finishingTaskId)
  {
    SqlBuilder sqlb = new SqlBuilder(new String[] { "select  TASK_ID from TASK", "where   ORIGINAL_TASK_ID = <originalTaskId>", "and     STATUS not in (5,9,10)", "and     TASK_ID <> <finishingTaskId>" });

    SqlExecutor sqle = new SqlExecutor("areAllIssuedTasksComplete", getDataSource(), sqlb, _log);
    sqle.addBindVariable("<originalTaskId>", Integer.valueOf(originalTaskId));
    sqle.addBindVariable("<finishingTaskId>", Integer.valueOf(finishingTaskId));
    try
    {
      boolean allComplete = true;
      ResultSet rs = sqle.getResultSet();
      while (rs.next())
      {
        _log.debug("areAllIssuedTasksComplete", "TaskId=" + rs.getInt(1) + " is not finished");
        allComplete = false;
      }
      return allComplete;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
    finally
    {
      sqle.close();
    }
  }

  public EntityList getRestartableTaskGroups()
  {
    SqlBuilder sqlb = new SqlBuilder(new String[] { "select  TASK_ID from TASK g", "where   TASK_TYPE = 1", "and     STATUS = 6", "and     not exists", "        (", "        select  1", "        from    TASK i", "        where   i.ORIGINAL_TASK_ID = g.TASK_ID", "        and     i.STATUS not in (5,9,10)", "        )" });

    SqlExecutor sqle = new SqlExecutor("getRestartableTaskGroups", getDataSource(), sqlb, _log);
    sqle.setLogSql(false);
    return sqle.getEntityList();
  }
}