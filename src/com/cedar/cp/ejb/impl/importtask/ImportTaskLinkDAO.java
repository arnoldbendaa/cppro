package com.cedar.cp.ejb.impl.importtask;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.importtask.OrderedChildrenELO;
import com.cedar.cp.dto.importtask.ImportTaskCK;
import com.cedar.cp.dto.importtask.ImportTaskLinkCK;
import com.cedar.cp.dto.importtask.ImportTaskLinkPK;
import com.cedar.cp.dto.importtask.ImportTaskLinkRefImpl;
import com.cedar.cp.dto.importtask.ImportTaskPK;
import com.cedar.cp.dto.importtask.ImportTaskRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
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

public class ImportTaskLinkDAO extends AbstractDAO
{
  Log _log = new Log(getClass());
  private static final String SQL_SELECT_COLUMNS = "select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME";
  protected static final String SQL_LOAD = " from TIDY_TASK_LINK where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ";
  protected static final String SQL_CREATE = "insert into TIDY_TASK_LINK ( TIDY_TASK_ID,TIDY_TASK_LINK_ID,SEQ,TYPE,CMD,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";
  protected static final String SQL_STORE = "update TIDY_TASK_LINK set SEQ = ?,TYPE = ?,CMD = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ";
  protected static String SQL_ORDERED_CHILDREN = "select 0       ,TIDY_TASK.TIDY_TASK_ID      ,TIDY_TASK.VIS_ID      ,TIDY_TASK_LINK.TIDY_TASK_ID      ,TIDY_TASK_LINK.TIDY_TASK_LINK_ID      ,TIDY_TASK_LINK.TYPE      ,TIDY_TASK_LINK.CMD from TIDY_TASK_LINK    ,TIDY_TASK where 1=1   and TIDY_TASK_LINK.TIDY_TASK_ID = TIDY_TASK.TIDY_TASK_ID  and  TIDY_TASK_LINK.TIDY_TASK_ID = ? order by SEQ";
  protected static final String SQL_DELETE_BATCH = "delete from TIDY_TASK_LINK where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ";
  public static final String SQL_BULK_GET_ALL = " from TIDY_TASK_LINK where 1=1 and TIDY_TASK_LINK.TIDY_TASK_ID = ? order by  TIDY_TASK_LINK.TIDY_TASK_ID ,TIDY_TASK_LINK.TIDY_TASK_LINK_ID";
  protected static final String SQL_GET_ALL = " from TIDY_TASK_LINK where    TIDY_TASK_ID = ? ";
  protected ImportTaskLinkEVO mDetails;

  public ImportTaskLinkDAO(Connection connection)
  {
    super(connection);
  }

  public ImportTaskLinkDAO()
  {
  }

  public ImportTaskLinkDAO(DataSource ds)
  {
    super(ds);
  }

  protected ImportTaskLinkPK getPK()
  {
    return mDetails.getPK();
  }

  public void setDetails(ImportTaskLinkEVO details)
  {
    mDetails = details.deepClone();
  }

  private ImportTaskLinkEVO getEvoFromJdbc(ResultSet resultSet_)
    throws SQLException
  {
    int col = 1;
    ImportTaskLinkEVO evo = new ImportTaskLinkEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++));

    evo.setUpdatedByUserId(resultSet_.getInt(col++));
    evo.setUpdatedTime(resultSet_.getTimestamp(col++));
    evo.setCreatedTime(resultSet_.getTimestamp(col++));
    return evo;
  }

  private int putEvoKeysToJdbc(ImportTaskLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
  {
    int col = startCol_;
    stmt_.setInt(col++, evo_.getImportTaskId());
    stmt_.setInt(col++, evo_.getImportTaskLinkId());
    return col;
  }

  private int putEvoDataToJdbc(ImportTaskLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
  {
    int col = startCol_;
    stmt_.setInt(col++, evo_.getSeq());
    stmt_.setInt(col++, evo_.getType());
    stmt_.setString(col++, evo_.getCmd());
    stmt_.setInt(col++, evo_.getUpdatedByUserId());
    stmt_.setTimestamp(col++, evo_.getUpdatedTime());
    stmt_.setTimestamp(col++, evo_.getCreatedTime());
    return col;
  }

  protected void doLoad(ImportTaskLinkPK pk)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    try
    {
      stmt = getConnection().prepareStatement("select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME from TIDY_TASK_LINK where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ");

      int col = 1;
      stmt.setInt(col++, pk.getImportTaskId());
      stmt.setInt(col++, pk.getImportTaskLinkId());

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
      throw handleSQLException(pk, "select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME from TIDY_TASK_LINK where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ", sqle);
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
      stmt = getConnection().prepareStatement("insert into TIDY_TASK_LINK ( TIDY_TASK_ID,TIDY_TASK_LINK_ID,SEQ,TYPE,CMD,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)");

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
      throw handleSQLException(mDetails.getPK(), "insert into TIDY_TASK_LINK ( TIDY_TASK_ID,TIDY_TASK_LINK_ID,SEQ,TYPE,CMD,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)", sqle);
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
        mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
        stmt = getConnection().prepareStatement("update TIDY_TASK_LINK set SEQ = ?,TYPE = ?,CMD = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ");

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
      throw handleSQLException(getPK(), "update TIDY_TASK_LINK set SEQ = ?,TYPE = ?,CMD = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ", sqle);
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

  public OrderedChildrenELO getOrderedChildren(int param1)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    OrderedChildrenELO results = new OrderedChildrenELO();
    try
    {
      stmt = getConnection().prepareStatement(SQL_ORDERED_CHILDREN);
      int col = 1;
      stmt.setInt(col++, param1);
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        col = 2;

        ImportTaskPK pkImportTask = new ImportTaskPK(resultSet.getInt(col++));

        String textImportTask = resultSet.getString(col++);

        ImportTaskLinkPK pkImportTaskLink = new ImportTaskLinkPK(resultSet.getInt(col++), resultSet.getInt(col++));

        String textImportTaskLink = "";

        ImportTaskLinkCK ckImportTaskLink = new ImportTaskLinkCK(pkImportTask, pkImportTaskLink);

        ImportTaskRefImpl erImportTask = new ImportTaskRefImpl(pkImportTask, textImportTask);

        ImportTaskLinkRefImpl erImportTaskLink = new ImportTaskLinkRefImpl(ckImportTaskLink, textImportTaskLink);

        int col1 = resultSet.getInt(col++);
        String col2 = resultSet.getString(col++);

        results.add(erImportTaskLink, erImportTask, col1, col2);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(SQL_ORDERED_CHILDREN, sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }

    if (timer != null) {
      timer.logDebug("getOrderedChildren", " ImportTaskId=" + param1 + " items=" + results.size());
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
        mDetails = ((ImportTaskLinkEVO)iter2.next());

        if (mDetails.deletePending())
        {
          somethingChanged = true;

          if (deleteStmt == null) {
            deleteStmt = getConnection().prepareStatement("delete from TIDY_TASK_LINK where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ");
          }

          int col = 1;
          deleteStmt.setInt(col++, mDetails.getImportTaskId());
          deleteStmt.setInt(col++, mDetails.getImportTaskLinkId());

          if (_log.isDebugEnabled()) {
            _log.debug("update", "ImportTaskLink deleting ImportTaskId=" + mDetails.getImportTaskId() + ",ImportTaskLinkId=" + mDetails.getImportTaskLinkId());
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
        mDetails = ((ImportTaskLinkEVO)iter1.next());

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
      throw handleSQLException("delete from TIDY_TASK_LINK where    TIDY_TASK_ID = ? AND TIDY_TASK_LINK_ID = ? ", sqle);
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

  public void bulkGetAll(ImportTaskPK entityPK, ImportTaskEVO owningEVO, String dependants)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    PreparedStatement stmt = null;
    ResultSet resultSet = null;

    int itemCount = 0;

    Collection theseItems = new ArrayList();
    owningEVO.setImportTasksEvents(theseItems);
    owningEVO.setImportTasksEventsAllItemsLoaded(true);
    try
    {
      stmt = getConnection().prepareStatement("select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME from TIDY_TASK_LINK where 1=1 and TIDY_TASK_LINK.TIDY_TASK_ID = ? order by  TIDY_TASK_LINK.TIDY_TASK_ID ,TIDY_TASK_LINK.TIDY_TASK_LINK_ID");

      int col = 1;
      stmt.setInt(col++, entityPK.getImportTaskId());

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
      throw handleSQLException("select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME from TIDY_TASK_LINK where 1=1 and TIDY_TASK_LINK.TIDY_TASK_ID = ? order by  TIDY_TASK_LINK.TIDY_TASK_ID ,TIDY_TASK_LINK.TIDY_TASK_LINK_ID", sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();

      mDetails = null;
    }
  }

  public Collection getAll(int selectImportTaskId, String dependants, Collection currentList)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;

    ArrayList items = new ArrayList();
    try
    {
      stmt = getConnection().prepareStatement("select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME from TIDY_TASK_LINK where    TIDY_TASK_ID = ? ");

      int col = 1;
      stmt.setInt(col++, selectImportTaskId);

      resultSet = stmt.executeQuery();

      while (resultSet.next())
      {
        mDetails = getEvoFromJdbc(resultSet);

        items.add(mDetails);
      }

      if (currentList != null)
      {
        ListIterator iter = items.listIterator();
        ImportTaskLinkEVO currentEVO = null;
        ImportTaskLinkEVO newEVO = null;
        while (iter.hasNext())
        {
          newEVO = (ImportTaskLinkEVO)iter.next();
          Iterator iter2 = currentList.iterator();
          while (iter2.hasNext())
          {
            currentEVO = (ImportTaskLinkEVO)iter2.next();
            if (currentEVO.getPK().equals(newEVO.getPK()))
            {
              iter.set(currentEVO);
            }
          }

        }

        Iterator iter2 = currentList.iterator();
        while (iter2.hasNext())
        {
          currentEVO = (ImportTaskLinkEVO)iter2.next();
          if (currentEVO.insertPending()) {
            items.add(currentEVO);
          }
        }
      }
      mDetails = null;
    }
    catch (SQLException sqle)
    {
      throw handleSQLException("select TIDY_TASK_LINK.TIDY_TASK_ID,TIDY_TASK_LINK.TIDY_TASK_LINK_ID,TIDY_TASK_LINK.SEQ,TIDY_TASK_LINK.TYPE,TIDY_TASK_LINK.CMD,TIDY_TASK_LINK.UPDATED_BY_USER_ID,TIDY_TASK_LINK.UPDATED_TIME,TIDY_TASK_LINK.CREATED_TIME from TIDY_TASK_LINK where    TIDY_TASK_ID = ? ", sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();

      if (timer != null) {
        timer.logDebug("getAll", " ImportTaskId=" + selectImportTaskId + " items=" + items.size());
      }

    }

    return items;
  }

  public ImportTaskLinkEVO getDetails(ImportTaskCK paramCK, String dependants)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    if (mDetails == null) {
      doLoad(((ImportTaskLinkCK)paramCK).getImportTaskLinkPK());
    }
    else if (!mDetails.getPK().equals(((ImportTaskLinkCK)paramCK).getImportTaskLinkPK())) {
      doLoad(((ImportTaskLinkCK)paramCK).getImportTaskLinkPK());
    }

    ImportTaskLinkEVO details = new ImportTaskLinkEVO();
    details = mDetails.deepClone();

    if (timer != null) {
      timer.logDebug("getDetails", paramCK + " " + dependants);
    }
    return details;
  }

  public ImportTaskLinkEVO getDetails(ImportTaskCK paramCK, ImportTaskLinkEVO paramEVO, String dependants)
    throws ValidationException
  {
    ImportTaskLinkEVO savedEVO = mDetails;
    mDetails = paramEVO;
    ImportTaskLinkEVO newEVO = getDetails(paramCK, dependants);
    mDetails = savedEVO;
    return newEVO;
  }

  public ImportTaskLinkEVO getDetails(String dependants)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    ImportTaskLinkEVO details = mDetails.deepClone();

    if (timer != null) {
      timer.logDebug("getDetails", mDetails.getPK() + " " + dependants);
    }
    return details;
  }

  public String getEntityName()
  {
    return "ImportTaskLink";
  }

  public ImportTaskLinkRefImpl getRef(ImportTaskLinkPK paramImportTaskLinkPK)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    try
    {
      stmt = getConnection().prepareStatement("select 0,TIDY_TASK.TIDY_TASK_ID from TIDY_TASK_LINK\njoin TIDY_TASK on (1=1\nand TIDY_TASK_LINK.TIDY_TASK_ID = TIDY_TASK.TIDY_TASK_ID\n) where 1=1 and TIDY_TASK_LINK.TIDY_TASK_ID = ? and TIDY_TASK_LINK.TIDY_TASK_LINK_ID = ?");
      int col = 1;
      stmt.setInt(col++, paramImportTaskLinkPK.getImportTaskId());
      stmt.setInt(col++, paramImportTaskLinkPK.getImportTaskLinkId());

      resultSet = stmt.executeQuery();

      if (!resultSet.next()) {
        throw new RuntimeException(getEntityName() + " getRef " + paramImportTaskLinkPK + " not found");
      }
      col = 2;
      ImportTaskPK newImportTaskPK = new ImportTaskPK(resultSet.getInt(col++));

      String textImportTaskLink = "";
      ImportTaskLinkCK ckImportTaskLink = new ImportTaskLinkCK(newImportTaskPK, paramImportTaskLinkPK);

      return new ImportTaskLinkRefImpl(ckImportTaskLink, textImportTaskLink);
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(paramImportTaskLinkPK, "select 0,TIDY_TASK.TIDY_TASK_ID from TIDY_TASK_LINK\njoin TIDY_TASK on (1=1\nand TIDY_TASK_LINK.TIDY_TASK_ID = TIDY_TASK.TIDY_TASK_ID\n) where 1=1 and TIDY_TASK_LINK.TIDY_TASK_ID = ? and TIDY_TASK_LINK.TIDY_TASK_LINK_ID = ?", sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();

      if (timer != null)
        timer.logDebug("getRef", paramImportTaskLinkPK);
    }
  }
}