package com.cedar.cp.ejb.impl.reset;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.reset.UserResetLinkCK;
import com.cedar.cp.dto.reset.UserResetLinkEditorSessionCSO;
import com.cedar.cp.dto.reset.UserResetLinkEditorSessionSSO;
import com.cedar.cp.dto.reset.UserResetLinkImpl;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class UserResetLinkEditorSessionSEJB extends AbstractSession
{
  private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
  private static final String DEPENDANTS_FOR_INSERT = "";
  private static final String DEPENDANTS_FOR_COPY = "";
  private static final String DEPENDANTS_FOR_UPDATE = "";
  private static final String DEPENDANTS_FOR_DELETE = "";
  private transient Log mLog = new Log(getClass());
  private transient SessionContext mSessionContext;
  private transient UserAccessor mUserAccessor;
  private UserResetLinkEditorSessionSSO mSSO;
  private UserResetLinkCK mThisTableKey;
  private UserEVO mUserEVO;
  private UserResetLinkEVO mUserResetLinkEVO;

  public UserResetLinkEditorSessionSSO getItemData(int userId, Object paramKey)
    throws ValidationException, EJBException
  {
    setUserId(userId);

    if (mLog.isDebugEnabled()) {
      mLog.debug("getItemData", paramKey);
    }
    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
    mThisTableKey = ((UserResetLinkCK)paramKey);
    try
    {
      mUserEVO = getUserAccessor().getDetails(mThisTableKey, "");

      mUserResetLinkEVO = mUserEVO.getResetLinkItem(mThisTableKey.getUserResetLinkPK());

      makeItemData();

      return mSSO;
    }
    catch (ValidationException e)
    {
      throw e;
    }
    catch (EJBException e)
    {
      if ((e.getCause() instanceof ValidationException))
        throw ((ValidationException)e.getCause());
      throw e;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new EJBException(e.getMessage(), e);
    }
    finally
    {
      setUserId(0);
      if (timer != null)
        timer.logInfo("getItemData", mThisTableKey);
    }
  }

  private void makeItemData()
    throws Exception
  {
    mSSO = new UserResetLinkEditorSessionSSO();

    UserResetLinkImpl editorData = buildUserResetLinkEditData(mThisTableKey);

    completeGetItemData(editorData);

    mSSO.setEditorData(editorData);
  }

  private void completeGetItemData(UserResetLinkImpl editorData)
    throws Exception
  {
  }

  private UserResetLinkImpl buildUserResetLinkEditData(Object thisKey)
    throws Exception
  {
    UserResetLinkImpl editorData = new UserResetLinkImpl(thisKey);

    editorData.setUserRef(new UserRefImpl(mUserEVO.getPK(), mUserEVO.getName()));

    completeUserResetLinkEditData(editorData);

    return editorData;
  }

  private void completeUserResetLinkEditData(UserResetLinkImpl editorData)
    throws Exception
  {
  }

  public UserResetLinkEditorSessionSSO getNewItemData(int userId)
    throws EJBException
  {
    mLog.debug("getNewItemData");

    setUserId(userId);
    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
    try
    {
      mSSO = new UserResetLinkEditorSessionSSO();

      UserResetLinkImpl editorData = new UserResetLinkImpl(null);

      completeGetNewItemData(editorData);

      mSSO.setEditorData(editorData);

      return mSSO;
    }
    catch (EJBException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new EJBException(e.getMessage(), e);
    }
    finally
    {
      setUserId(0);
      if (timer != null)
        timer.logInfo("getNewItemData", "");
    }
  }

  private void completeGetNewItemData(UserResetLinkImpl editorData)
    throws Exception
  {
  }

  public UserResetLinkCK insert(UserResetLinkEditorSessionCSO cso)
    throws ValidationException, EJBException
  {
    mLog.debug("insert");

    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

    setUserId(cso.getUserId());

    UserResetLinkImpl editorData = cso.getEditorData();
    try
    {
      mUserEVO = getUserAccessor().getDetails(editorData.getUserRef(), "");

      mUserResetLinkEVO = new UserResetLinkEVO();

      updateUserResetLinkRelationships(editorData);

      completeInsertSetup(editorData);

      validateInsert();

      mUserEVO.addResetLinkItem(mUserResetLinkEVO);

      mUserEVO = getUserAccessor().setAndGetDetails(mUserEVO, "<5>");

      Iterator iter = mUserEVO.getResetLink().iterator();
      while (iter.hasNext())
      {
        mUserResetLinkEVO = ((UserResetLinkEVO)iter.next());
        if (mUserResetLinkEVO.insertPending()) {
          break;
        }
      }
      insertIntoAdditionalTables(editorData, true);

      sendEntityEventMessage("UserResetLink", mUserResetLinkEVO.getPK(), 1);

      return new UserResetLinkCK(mUserEVO.getPK(), mUserResetLinkEVO.getPK());
    }
    catch (ValidationException ve)
    {
      throw new EJBException(ve);
    }
    catch (EJBException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new EJBException(e.getMessage(), e);
    }
    finally
    {
      setUserId(0);
      if (timer != null)
        timer.logInfo("insert", "");
    }
  }

  private void updateUserResetLinkRelationships(UserResetLinkImpl editorData)
    throws ValidationException
  {
  }

  private void completeInsertSetup(UserResetLinkImpl editorData)
    throws Exception
  {
  }

  private void insertIntoAdditionalTables(UserResetLinkImpl editorData, boolean isInsert)
    throws Exception
  {
  }

  private void validateInsert()
    throws ValidationException
  {
  }

  public UserResetLinkCK copy(UserResetLinkEditorSessionCSO cso)
    throws ValidationException, EJBException
  {
    mLog.debug("copy");

    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

    setUserId(cso.getUserId());
    UserResetLinkImpl editorData = cso.getEditorData();

    mThisTableKey = ((UserResetLinkCK)editorData.getPrimaryKey());
    try
    {
      mUserEVO = getUserAccessor().getDetails(mThisTableKey, "");

      UserResetLinkEVO origUserResetLink = mUserEVO.getResetLinkItem(mThisTableKey.getUserResetLinkPK());

      mUserResetLinkEVO = origUserResetLink.deepClone();

      updateUserResetLinkRelationships(editorData);

      completeCopySetup(editorData);

      validateCopy();

      UserPK parentKey = (UserPK)editorData.getUserRef().getPrimaryKey();

      if (!parentKey.equals(mUserEVO.getPK()))
      {
        mUserEVO = getUserAccessor().getDetails(parentKey, "");
      }

      mUserResetLinkEVO.prepareForInsert(null);

      mUserEVO.addResetLinkItem(mUserResetLinkEVO);

      mUserEVO = getUserAccessor().setAndGetDetails(mUserEVO, "<5>");

      Iterator iter = mUserEVO.getResetLink().iterator();
      while (iter.hasNext())
      {
        mUserResetLinkEVO = ((UserResetLinkEVO)iter.next());
        if (mUserResetLinkEVO.insertPending()) {
          break;
        }
      }

      mThisTableKey = new UserResetLinkCK(mUserEVO.getPK(), mUserResetLinkEVO.getPK());

      insertIntoAdditionalTables(editorData, false);

      sendEntityEventMessage("UserResetLink", mUserResetLinkEVO.getPK(), 1);

      return mThisTableKey;
    }
    catch (ValidationException ve)
    {
      throw new EJBException(ve);
    }
    catch (EJBException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new EJBException(e);
    }
    finally
    {
      setUserId(0);
      if (timer != null)
        timer.logInfo("copy", mThisTableKey);
    }
  }

  private void validateCopy()
    throws ValidationException
  {
  }

  private void completeCopySetup(UserResetLinkImpl editorData)
    throws Exception
  {
  }

  public void update(UserResetLinkEditorSessionCSO cso)
    throws ValidationException, EJBException
  {
    mLog.debug("update");

    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

    setUserId(cso.getUserId());
    UserResetLinkImpl editorData = cso.getEditorData();

    mThisTableKey = ((UserResetLinkCK)editorData.getPrimaryKey());
    try
    {
      mUserEVO = getUserAccessor().getDetails(mThisTableKey, "");

      mUserResetLinkEVO = mUserEVO.getResetLinkItem(mThisTableKey.getUserResetLinkPK());

      preValidateUpdate(editorData);

      updateUserResetLinkRelationships(editorData);

      completeUpdateSetup(editorData);

      postValidateUpdate();

      getUserAccessor().setDetails(mUserEVO);

      updateAdditionalTables(editorData);

      sendEntityEventMessage("UserResetLink", mUserResetLinkEVO.getPK(), 3);
    }
    catch (ValidationException ve)
    {
      throw new EJBException(ve);
    }
    catch (EJBException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new EJBException(e.getMessage(), e);
    }
    finally
    {
      setUserId(0);
      if (timer != null)
        timer.logInfo("update", mThisTableKey);
    }
  }

  private void preValidateUpdate(UserResetLinkImpl editorData)
    throws ValidationException
  {
  }

  private void postValidateUpdate()
    throws ValidationException
  {
  }

  private void completeUpdateSetup(UserResetLinkImpl editorData)
    throws Exception
  {
  }

  public EntityList getOwnershipData(int userId, Object paramKey)
    throws EJBException
  {
    mLog.debug("getOwnershipData");

    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

    setUserId(userId);
    mThisTableKey = ((UserResetLinkCK)paramKey);
    try
    {
      return getUserAccessor().getAllUsers();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new EJBException(e.getMessage(), e);
    }
    finally
    {
      setUserId(0);
      if (timer != null)
        timer.logInfo("getOwnershipData", "");
    }
  }

  private void updateAdditionalTables(UserResetLinkImpl editorData)
    throws Exception
  {
  }

  public void delete(int userId, Object paramKey)
    throws ValidationException, EJBException
  {
    if (mLog.isDebugEnabled()) {
      mLog.debug("delete", paramKey);
    }
    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

    setUserId(userId);
    mThisTableKey = ((UserResetLinkCK)paramKey);
    try
    {
      mUserEVO = getUserAccessor().getDetails(mThisTableKey, "");

      mUserResetLinkEVO = mUserEVO.getResetLinkItem(mThisTableKey.getUserResetLinkPK());

      validateDelete();

      deleteDataFromOtherTables();

      mUserEVO.deleteResetLinkItem(mThisTableKey.getUserResetLinkPK());

      getUserAccessor().setDetails(mUserEVO);

      sendEntityEventMessage("UserResetLink", mThisTableKey.getUserResetLinkPK(), 2);
    }
    catch (ValidationException ve)
    {
      throw ve;
    }
    catch (EJBException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new EJBException(e.getMessage(), e);
    }
    finally
    {
      setUserId(0);
      if (timer != null)
        timer.logInfo("delete", mThisTableKey);
    }
  }

  private void deleteDataFromOtherTables()
    throws Exception
  {
  }

  private void validateDelete()
    throws ValidationException, Exception
  {
  }

  public void ejbCreate()
    throws EJBException
  {
  }

  public void ejbRemove()
  {
  }

  public void setSessionContext(SessionContext context)
  {
    mSessionContext = context;
  }

  public void ejbActivate()
  {
  }

  public void ejbPassivate()
  {
  }

  private UserAccessor getUserAccessor()
    throws Exception
  {
    if (mUserAccessor == null) {
      mUserAccessor = new UserAccessor(getInitialContext());
    }
    return mUserAccessor;
  }

  private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType)
  {
    try
    {
      JmsConnection jms = new JmsConnectionImpl(getInitialContext(), 3, "entityEventTopic");
      jms.createSession();
      EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, getClass().getName());
      mLog.debug("update", "sending event message: " + em.toString());
      jms.send(em);
      jms.closeSession();
      jms.closeConnection();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}