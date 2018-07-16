package com.cedar.cp.ejb.impl.reset;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.reset.AllUserResetLinksELO;
import com.cedar.cp.dto.reset.LinkByUserIDELO;
import com.cedar.cp.dto.reset.UserResetLinkCK;
import com.cedar.cp.dto.reset.UserResetLinkPK;
import com.cedar.cp.dto.reset.UserResetLinkRefImpl;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import javax.sql.DataSource;

public class UserResetLinkDAO extends AbstractDAO
{
  Log _log = new Log(getClass());
  private static final String SQL_SELECT_COLUMNS = "select USER_RESET_LINK.USER_ID,USER_RESET_LINK.PWD_LINK";
  protected static final String SQL_LOAD = " from USER_RESET_LINK where    USER_ID = ? AND PWD_LINK = ? ";
  protected static final String SQL_CREATE = "insert into USER_RESET_LINK ( USER_ID,PWD_LINK) values ( ?,?)";
  protected static final String SQL_STORE = "update USER_RESET_LINK set where    USER_ID = ? AND PWD_LINK = ? ";
  protected static String SQL_ALL_USER_RESET_LINKS = "select 0       ,USR.USER_ID      ,USR.NAME      ,USER_RESET_LINK.USER_ID      ,USER_RESET_LINK.PWD_LINK      ,USER_RESET_LINK.PWD_LINK      ,USER_RESET_LINK.USER_ID      ,USER_RESET_LINK.PWD_LINK from USER_RESET_LINK    ,USR where 1=1   and USER_RESET_LINK.USER_ID = USR.USER_ID ";

  protected static String SQL_LINK_BY_USER_I_D = "select 0       ,USR.USER_ID      ,USR.NAME      ,USER_RESET_LINK.USER_ID      ,USER_RESET_LINK.PWD_LINK      ,USER_RESET_LINK.PWD_LINK      ,USER_RESET_LINK.PWD_LINK from USER_RESET_LINK    ,USR where 1=1   and USER_RESET_LINK.USER_ID = USR.USER_ID  and  USER_RESET_LINK.USER_ID = ?";
  protected static final String SQL_DELETE_BATCH = "delete from USER_RESET_LINK where    USER_ID = ? AND PWD_LINK = ? ";
  public static final String SQL_BULK_GET_ALL = " from USER_RESET_LINK where 1=1 and USER_RESET_LINK.USER_ID = ? order by  USER_RESET_LINK.USER_ID ,USER_RESET_LINK.PWD_LINK";
  protected static final String SQL_GET_ALL = " from USER_RESET_LINK where    USER_ID = ? ";
  protected UserResetLinkEVO mDetails;

  public UserResetLinkDAO(Connection connection)
  {
    super(connection);
  }

  public UserResetLinkDAO()
  {
  }

  public UserResetLinkDAO(DataSource ds)
  {
    super(ds);
  }

  protected UserResetLinkPK getPK()
  {
    return mDetails.getPK();
  }

  public void setDetails(UserResetLinkEVO details)
  {
    mDetails = details.deepClone();
  }

  private UserResetLinkEVO getEvoFromJdbc(ResultSet resultSet_)
    throws SQLException
  {
    int col = 1;
    UserResetLinkEVO evo = new UserResetLinkEVO(resultSet_.getInt(col++), resultSet_.getString(col++));

    return evo;
  }

  private int putEvoKeysToJdbc(UserResetLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
  {
    int col = startCol_;
    stmt_.setInt(col++, evo_.getUserId());
    stmt_.setString(col++, evo_.getPwdLink());
    return col;
  }

  private int putEvoDataToJdbc(UserResetLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
  {
    int col = startCol_;
    return col;
  }

  protected void doLoad(UserResetLinkPK pk)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    try
    {
      stmt = getConnection().prepareStatement("select USER_RESET_LINK.USER_ID,USER_RESET_LINK.PWD_LINK from USER_RESET_LINK where    USER_ID = ? AND PWD_LINK = ? ");

      int col = 1;
      stmt.setInt(col++, pk.getUserId());
      stmt.setString(col++, pk.getPwdLink());

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
      throw handleSQLException(pk, "select USER_RESET_LINK.USER_ID,USER_RESET_LINK.PWD_LINK from USER_RESET_LINK where    USER_ID = ? AND PWD_LINK = ? ", sqle);
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
      stmt = getConnection().prepareStatement("insert into USER_RESET_LINK ( USER_ID,PWD_LINK) values ( ?,?)");

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
      throw handleSQLException(mDetails.getPK(), "insert into USER_RESET_LINK ( USER_ID,PWD_LINK) values ( ?,?)", sqle);
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
        stmt = getConnection().prepareStatement("update USER_RESET_LINK set where    USER_ID = ? AND PWD_LINK = ? ");

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
      throw handleSQLException(getPK(), "update USER_RESET_LINK set where    USER_ID = ? AND PWD_LINK = ? ", sqle);
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

  public AllUserResetLinksELO getAllUserResetLinks()
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    AllUserResetLinksELO results = new AllUserResetLinksELO();
    try
    {
      stmt = getConnection().prepareStatement(SQL_ALL_USER_RESET_LINKS);
      int col = 1;
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        col = 2;

        UserPK pkUser = new UserPK(resultSet.getInt(col++));

        String textUser = resultSet.getString(col++);

        UserResetLinkPK pkUserResetLink = new UserResetLinkPK(resultSet.getInt(col++), resultSet.getString(col++));

        String textUserResetLink = resultSet.getString(col++);

        UserResetLinkCK ckUserResetLink = new UserResetLinkCK(pkUser, pkUserResetLink);

        UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

        UserResetLinkRefImpl erUserResetLink = new UserResetLinkRefImpl(ckUserResetLink, textUserResetLink);

        int col1 = resultSet.getInt(col++);
        String col2 = resultSet.getString(col++);

        results.add(erUserResetLink, erUser, col1, col2);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(SQL_ALL_USER_RESET_LINKS, sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }

    if (timer != null) {
      timer.logDebug("getAllUserResetLinks", " items=" + results.size());
    }

    return results;
  }

  public LinkByUserIDELO getLinkByUserID(int param1)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    LinkByUserIDELO results = new LinkByUserIDELO();
    try
    {
      stmt = getConnection().prepareStatement(SQL_LINK_BY_USER_I_D);
      int col = 1;
      stmt.setInt(col++, param1);
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {
        col = 2;

        UserPK pkUser = new UserPK(resultSet.getInt(col++));

        String textUser = resultSet.getString(col++);

        UserResetLinkPK pkUserResetLink = new UserResetLinkPK(resultSet.getInt(col++), resultSet.getString(col++));

        String textUserResetLink = resultSet.getString(col++);

        UserResetLinkCK ckUserResetLink = new UserResetLinkCK(pkUser, pkUserResetLink);

        UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

        UserResetLinkRefImpl erUserResetLink = new UserResetLinkRefImpl(ckUserResetLink, textUserResetLink);

        String col1 = resultSet.getString(col++);

        results.add(erUserResetLink, erUser, col1);
      }

    }
    catch (SQLException sqle)
    {
      throw handleSQLException(SQL_LINK_BY_USER_I_D, sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();
    }

    if (timer != null) {
      timer.logDebug("getLinkByUserID", " UserId=" + param1 + " items=" + results.size());
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
        mDetails = ((UserResetLinkEVO)iter2.next());

        if (mDetails.deletePending())
        {
          somethingChanged = true;

          if (deleteStmt == null) {
            deleteStmt = getConnection().prepareStatement("delete from USER_RESET_LINK where    USER_ID = ? AND PWD_LINK = ? ");
          }

          int col = 1;
          deleteStmt.setInt(col++, mDetails.getUserId());
          deleteStmt.setString(col++, mDetails.getPwdLink());

          if (_log.isDebugEnabled()) {
            _log.debug("update", "UserResetLink deleting UserId=" + mDetails.getUserId() + ",PwdLink=" + mDetails.getPwdLink());
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
        mDetails = ((UserResetLinkEVO)iter1.next());

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
      throw handleSQLException("delete from USER_RESET_LINK where    USER_ID = ? AND PWD_LINK = ? ", sqle);
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
    owningEVO.setResetLink(theseItems);
    owningEVO.setResetLinkAllItemsLoaded(true);
    try
    {
      stmt = getConnection().prepareStatement("select USER_RESET_LINK.USER_ID,USER_RESET_LINK.PWD_LINK from USER_RESET_LINK where 1=1 and USER_RESET_LINK.USER_ID = ? order by  USER_RESET_LINK.USER_ID ,USER_RESET_LINK.PWD_LINK");

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
      throw handleSQLException("select USER_RESET_LINK.USER_ID,USER_RESET_LINK.PWD_LINK from USER_RESET_LINK where 1=1 and USER_RESET_LINK.USER_ID = ? order by  USER_RESET_LINK.USER_ID ,USER_RESET_LINK.PWD_LINK", sqle);
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
      stmt = getConnection().prepareStatement("select USER_RESET_LINK.USER_ID,USER_RESET_LINK.PWD_LINK from USER_RESET_LINK where    USER_ID = ? ");

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
        UserResetLinkEVO currentEVO = null;
        UserResetLinkEVO newEVO = null;
        while (iter.hasNext())
        {
          newEVO = (UserResetLinkEVO)iter.next();
          Iterator iter2 = currentList.iterator();
          while (iter2.hasNext())
          {
            currentEVO = (UserResetLinkEVO)iter2.next();
            if (currentEVO.getPK().equals(newEVO.getPK()))
            {
              iter.set(currentEVO);
            }
          }

        }

        Iterator iter2 = currentList.iterator();
        while (iter2.hasNext())
        {
          currentEVO = (UserResetLinkEVO)iter2.next();
          if (currentEVO.insertPending()) {
            items.add(currentEVO);
          }
        }
      }
      mDetails = null;
    }
    catch (SQLException sqle)
    {
      throw handleSQLException("select USER_RESET_LINK.USER_ID,USER_RESET_LINK.PWD_LINK from USER_RESET_LINK where    USER_ID = ? ", sqle);
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

  public UserResetLinkEVO getDetails(UserCK paramCK, String dependants)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    if (mDetails == null) {
      doLoad(((UserResetLinkCK)paramCK).getUserResetLinkPK());
    }
    else if (!mDetails.getPK().equals(((UserResetLinkCK)paramCK).getUserResetLinkPK())) {
      doLoad(((UserResetLinkCK)paramCK).getUserResetLinkPK());
    }

    UserResetLinkEVO details = new UserResetLinkEVO();
    details = mDetails.deepClone();

    if (timer != null) {
      timer.logDebug("getDetails", paramCK + " " + dependants);
    }
    return details;
  }

  public UserResetLinkEVO getDetails(UserCK paramCK, UserResetLinkEVO paramEVO, String dependants)
    throws ValidationException
  {
    UserResetLinkEVO savedEVO = mDetails;
    mDetails = paramEVO;
    UserResetLinkEVO newEVO = getDetails(paramCK, dependants);
    mDetails = savedEVO;
    return newEVO;
  }

  public UserResetLinkEVO getDetails(String dependants)
    throws ValidationException
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

    UserResetLinkEVO details = mDetails.deepClone();

    if (timer != null) {
      timer.logDebug("getDetails", mDetails.getPK() + " " + dependants);
    }
    return details;
  }

  public String getEntityName()
  {
    return "UserResetLink";
  }

  public UserResetLinkRefImpl getRef(UserResetLinkPK paramUserResetLinkPK)
  {
    Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    try
    {
      stmt = getConnection().prepareStatement("select 0,USR.USER_ID,USER_RESET_LINK.PWD_LINK from USER_RESET_LINK\njoin USR on (1=1\nand USER_RESET_LINK.USER_ID = USR.USER_ID\n) where 1=1 and USER_RESET_LINK.USER_ID = ? and USER_RESET_LINK.PWD_LINK = ?");
      int col = 1;
      stmt.setInt(col++, paramUserResetLinkPK.getUserId());
      stmt.setString(col++, paramUserResetLinkPK.getPwdLink());

      resultSet = stmt.executeQuery();

      if (!resultSet.next()) {
        throw new RuntimeException(getEntityName() + " getRef " + paramUserResetLinkPK + " not found");
      }
      col = 2;
      UserPK newUserPK = new UserPK(resultSet.getInt(col++));

      String textUserResetLink = resultSet.getString(col++);
      UserResetLinkCK ckUserResetLink = new UserResetLinkCK(newUserPK, paramUserResetLinkPK);

      return new UserResetLinkRefImpl(ckUserResetLink, textUserResetLink);
    }
    catch (SQLException sqle)
    {
      throw handleSQLException(paramUserResetLinkPK, "select 0,USR.USER_ID,USER_RESET_LINK.PWD_LINK from USER_RESET_LINK\njoin USR on (1=1\nand USER_RESET_LINK.USER_ID = USR.USER_ID\n) where 1=1 and USER_RESET_LINK.USER_ID = ? and USER_RESET_LINK.PWD_LINK = ?", sqle);
    }
    finally
    {
      closeResultSet(resultSet);
      closeStatement(stmt);
      closeConnection();

      if (timer != null)
        timer.logDebug("getRef", paramUserResetLinkPK);
    }
  }
}