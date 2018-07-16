package com.cedar.cp.ejb.impl.reset;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.reset.ChallengeQuestionCK;
import com.cedar.cp.dto.reset.ChallengeQuestionEditorSessionCSO;
import com.cedar.cp.dto.reset.ChallengeQuestionEditorSessionSSO;
import com.cedar.cp.dto.reset.ChallengeQuestionImpl;
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

public class ChallengeQuestionEditorSessionSEJB extends AbstractSession
{
  private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
  private static final String DEPENDANTS_FOR_INSERT = "";
  private static final String DEPENDANTS_FOR_COPY = "";
  private static final String DEPENDANTS_FOR_UPDATE = "";
  private static final String DEPENDANTS_FOR_DELETE = "";
  private transient Log mLog = new Log(getClass());
  private transient SessionContext mSessionContext;
  private transient UserAccessor mUserAccessor;
  private ChallengeQuestionEditorSessionSSO mSSO;
  private ChallengeQuestionCK mThisTableKey;
  private UserEVO mUserEVO;
  private ChallengeQuestionEVO mChallengeQuestionEVO;

  public ChallengeQuestionEditorSessionSSO getItemData(int userId, Object paramKey)
    throws ValidationException, EJBException
  {
    setUserId(userId);

    if (mLog.isDebugEnabled()) {
      mLog.debug("getItemData", paramKey);
    }
    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
    mThisTableKey = ((ChallengeQuestionCK)paramKey);
    try
    {
      mUserEVO = getUserAccessor().getDetails(mThisTableKey, "");

      mChallengeQuestionEVO = mUserEVO.getChallengeQuestionsItem(mThisTableKey.getChallengeQuestionPK());

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
    mSSO = new ChallengeQuestionEditorSessionSSO();

    ChallengeQuestionImpl editorData = buildChallengeQuestionEditData(mThisTableKey);

    completeGetItemData(editorData);

    mSSO.setEditorData(editorData);
  }

  private void completeGetItemData(ChallengeQuestionImpl editorData)
    throws Exception
  {
  }

  private ChallengeQuestionImpl buildChallengeQuestionEditData(Object thisKey)
    throws Exception
  {
    ChallengeQuestionImpl editorData = new ChallengeQuestionImpl(thisKey);

    editorData.setQuestionAnswer(mChallengeQuestionEVO.getQuestionAnswer());

    editorData.setVersionNum(mChallengeQuestionEVO.getVersionNum());

    editorData.setUserRef(new UserRefImpl(mUserEVO.getPK(), mUserEVO.getName()));

    completeChallengeQuestionEditData(editorData);

    return editorData;
  }

  private void completeChallengeQuestionEditData(ChallengeQuestionImpl editorData)
    throws Exception
  {
  }

  public ChallengeQuestionEditorSessionSSO getNewItemData(int userId)
    throws EJBException
  {
    mLog.debug("getNewItemData");

    setUserId(userId);
    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
    try
    {
      mSSO = new ChallengeQuestionEditorSessionSSO();

      ChallengeQuestionImpl editorData = new ChallengeQuestionImpl(null);

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

  private void completeGetNewItemData(ChallengeQuestionImpl editorData)
    throws Exception
  {
  }

  public ChallengeQuestionCK insert(ChallengeQuestionEditorSessionCSO cso)
    throws ValidationException, EJBException
  {
    mLog.debug("insert");

    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

    setUserId(cso.getUserId());

    ChallengeQuestionImpl editorData = cso.getEditorData();
    try
    {
      mUserEVO = getUserAccessor().getDetails(editorData.getUserRef(), "");

      mChallengeQuestionEVO = new ChallengeQuestionEVO();

      mChallengeQuestionEVO.setQuestionAnswer(editorData.getQuestionAnswer());

      updateChallengeQuestionRelationships(editorData);

      completeInsertSetup(editorData);

      validateInsert();

      mUserEVO.addChallengeQuestionsItem(mChallengeQuestionEVO);

      mUserEVO = getUserAccessor().setAndGetDetails(mUserEVO, "<4>");

      Iterator iter = mUserEVO.getChallengeQuestions().iterator();
      while (iter.hasNext())
      {
        mChallengeQuestionEVO = ((ChallengeQuestionEVO)iter.next());
        if (mChallengeQuestionEVO.insertPending()) {
          break;
        }
      }
      insertIntoAdditionalTables(editorData, true);

      sendEntityEventMessage("ChallengeQuestion", mChallengeQuestionEVO.getPK(), 1);

      return new ChallengeQuestionCK(mUserEVO.getPK(), mChallengeQuestionEVO.getPK());
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

  private void updateChallengeQuestionRelationships(ChallengeQuestionImpl editorData)
    throws ValidationException
  {
  }

  private void completeInsertSetup(ChallengeQuestionImpl editorData)
    throws Exception
  {
  }

  private void insertIntoAdditionalTables(ChallengeQuestionImpl editorData, boolean isInsert)
    throws Exception
  {
  }

  private void validateInsert()
    throws ValidationException
  {
  }

  public ChallengeQuestionCK copy(ChallengeQuestionEditorSessionCSO cso)
    throws ValidationException, EJBException
  {
    mLog.debug("copy");

    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

    setUserId(cso.getUserId());
    ChallengeQuestionImpl editorData = cso.getEditorData();

    mThisTableKey = ((ChallengeQuestionCK)editorData.getPrimaryKey());
    try
    {
      mUserEVO = getUserAccessor().getDetails(mThisTableKey, "");

      ChallengeQuestionEVO origChallengeQuestion = mUserEVO.getChallengeQuestionsItem(mThisTableKey.getChallengeQuestionPK());

      mChallengeQuestionEVO = origChallengeQuestion.deepClone();

      mChallengeQuestionEVO.setQuestionAnswer(editorData.getQuestionAnswer());
      mChallengeQuestionEVO.setVersionNum(0);

      updateChallengeQuestionRelationships(editorData);

      completeCopySetup(editorData);

      validateCopy();

      UserPK parentKey = (UserPK)editorData.getUserRef().getPrimaryKey();

      if (!parentKey.equals(mUserEVO.getPK()))
      {
        mUserEVO = getUserAccessor().getDetails(parentKey, "");
      }

      mChallengeQuestionEVO.prepareForInsert(null);

      mUserEVO.addChallengeQuestionsItem(mChallengeQuestionEVO);

      mUserEVO = getUserAccessor().setAndGetDetails(mUserEVO, "<4>");

      Iterator iter = mUserEVO.getChallengeQuestions().iterator();
      while (iter.hasNext())
      {
        mChallengeQuestionEVO = ((ChallengeQuestionEVO)iter.next());
        if (mChallengeQuestionEVO.insertPending()) {
          break;
        }
      }

      mThisTableKey = new ChallengeQuestionCK(mUserEVO.getPK(), mChallengeQuestionEVO.getPK());

      insertIntoAdditionalTables(editorData, false);

      sendEntityEventMessage("ChallengeQuestion", mChallengeQuestionEVO.getPK(), 1);

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

  private void completeCopySetup(ChallengeQuestionImpl editorData)
    throws Exception
  {
  }

  public void update(ChallengeQuestionEditorSessionCSO cso)
    throws ValidationException, EJBException
  {
    mLog.debug("update");

    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

    setUserId(cso.getUserId());
    ChallengeQuestionImpl editorData = cso.getEditorData();

    mThisTableKey = ((ChallengeQuestionCK)editorData.getPrimaryKey());
    try
    {
      mUserEVO = getUserAccessor().getDetails(mThisTableKey, "");

      mChallengeQuestionEVO = mUserEVO.getChallengeQuestionsItem(mThisTableKey.getChallengeQuestionPK());

      preValidateUpdate(editorData);

      mChallengeQuestionEVO.setQuestionAnswer(editorData.getQuestionAnswer());

      if (editorData.getVersionNum() != mChallengeQuestionEVO.getVersionNum()) {
        throw new VersionValidationException(mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + mChallengeQuestionEVO.getVersionNum());
      }

      updateChallengeQuestionRelationships(editorData);

      completeUpdateSetup(editorData);

      postValidateUpdate();

      getUserAccessor().setDetails(mUserEVO);

      updateAdditionalTables(editorData);

      sendEntityEventMessage("ChallengeQuestion", mChallengeQuestionEVO.getPK(), 3);
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

  private void preValidateUpdate(ChallengeQuestionImpl editorData)
    throws ValidationException
  {
  }

  private void postValidateUpdate()
    throws ValidationException
  {
  }

  private void completeUpdateSetup(ChallengeQuestionImpl editorData)
    throws Exception
  {
  }

  public EntityList getOwnershipData(int userId, Object paramKey)
    throws EJBException
  {
    mLog.debug("getOwnershipData");

    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

    setUserId(userId);
    mThisTableKey = ((ChallengeQuestionCK)paramKey);
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

  private void updateAdditionalTables(ChallengeQuestionImpl editorData)
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
    mThisTableKey = ((ChallengeQuestionCK)paramKey);
    try
    {
      mUserEVO = getUserAccessor().getDetails(mThisTableKey, "");

      mChallengeQuestionEVO = mUserEVO.getChallengeQuestionsItem(mThisTableKey.getChallengeQuestionPK());

      validateDelete();

      deleteDataFromOtherTables();

      mUserEVO.deleteChallengeQuestionsItem(mThisTableKey.getChallengeQuestionPK());

      getUserAccessor().setDetails(mUserEVO);

      sendEntityEventMessage("ChallengeQuestion", mThisTableKey.getChallengeQuestionPK(), 2);
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