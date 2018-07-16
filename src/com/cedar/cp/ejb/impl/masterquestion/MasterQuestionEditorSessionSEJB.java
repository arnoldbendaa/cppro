package com.cedar.cp.ejb.impl.masterquestion;

import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.masterquestion.MasterQuestionEditorSessionCSO;
import com.cedar.cp.dto.masterquestion.MasterQuestionEditorSessionSSO;
import com.cedar.cp.dto.masterquestion.MasterQuestionImpl;
import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class MasterQuestionEditorSessionSEJB extends AbstractSession
{
  private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
  private static final String DEPENDANTS_FOR_INSERT = "";
  private static final String DEPENDANTS_FOR_COPY = "";
  private static final String DEPENDANTS_FOR_UPDATE = "";
  private static final String DEPENDANTS_FOR_DELETE = "";
  private transient Log mLog = new Log(getClass());
  private transient SessionContext mSessionContext;
  private transient MasterQuestionAccessor mMasterQuestionAccessor;
  private MasterQuestionEditorSessionSSO mSSO;
  private MasterQuestionPK mThisTableKey;
  private MasterQuestionEVO mMasterQuestionEVO;

  public MasterQuestionEditorSessionSSO getItemData(int userId, Object paramKey)
    throws ValidationException, EJBException
  {
    setUserId(userId);

    if (mLog.isDebugEnabled()) {
      mLog.debug("getItemData", paramKey);
    }
    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
    mThisTableKey = ((MasterQuestionPK)paramKey);
    try
    {
      mMasterQuestionEVO = getMasterQuestionAccessor().getDetails(mThisTableKey, "");

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
    mSSO = new MasterQuestionEditorSessionSSO();

    MasterQuestionImpl editorData = buildMasterQuestionEditData(mThisTableKey);

    completeGetItemData(editorData);

    mSSO.setEditorData(editorData);
  }

  private void completeGetItemData(MasterQuestionImpl editorData)
    throws Exception
  {
  }

  private MasterQuestionImpl buildMasterQuestionEditData(Object thisKey)
    throws Exception
  {
    MasterQuestionImpl editorData = new MasterQuestionImpl(thisKey);

    editorData.setQuestionText(mMasterQuestionEVO.getQuestionText());

    editorData.setVersionNum(mMasterQuestionEVO.getVersionNum());

    completeMasterQuestionEditData(editorData);

    return editorData;
  }

  private void completeMasterQuestionEditData(MasterQuestionImpl editorData)
    throws Exception
  {
  }

  public MasterQuestionEditorSessionSSO getNewItemData(int userId)
    throws EJBException
  {
    mLog.debug("getNewItemData");

    setUserId(userId);
    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
    try
    {
      mSSO = new MasterQuestionEditorSessionSSO();

      MasterQuestionImpl editorData = new MasterQuestionImpl(null);

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

  private void completeGetNewItemData(MasterQuestionImpl editorData)
    throws Exception
  {
  }

  public MasterQuestionPK insert(MasterQuestionEditorSessionCSO cso)
    throws ValidationException, EJBException
  {
    mLog.debug("insert");

    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

    setUserId(cso.getUserId());

    MasterQuestionImpl editorData = cso.getEditorData();
    try
    {
      mMasterQuestionEVO = new MasterQuestionEVO();

      mMasterQuestionEVO.setQuestionText(editorData.getQuestionText());

      updateMasterQuestionRelationships(editorData);

      completeInsertSetup(editorData);

      validateInsert();

      mMasterQuestionEVO = getMasterQuestionAccessor().create(mMasterQuestionEVO);

      insertIntoAdditionalTables(editorData, true);

      sendEntityEventMessage("MasterQuestion", mMasterQuestionEVO.getPK(), 1);

      return mMasterQuestionEVO.getPK();
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

  private void updateMasterQuestionRelationships(MasterQuestionImpl editorData)
    throws ValidationException
  {
  }

  private void completeInsertSetup(MasterQuestionImpl editorData)
    throws Exception
  {
  }

  private void insertIntoAdditionalTables(MasterQuestionImpl editorData, boolean isInsert)
    throws Exception
  {
  }

  private void validateInsert()
    throws ValidationException
  {
  }

  public MasterQuestionPK copy(MasterQuestionEditorSessionCSO cso)
    throws ValidationException, EJBException
  {
    mLog.debug("copy");

    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

    setUserId(cso.getUserId());
    MasterQuestionImpl editorData = cso.getEditorData();

    mThisTableKey = ((MasterQuestionPK)editorData.getPrimaryKey());
    try
    {
      MasterQuestionEVO origMasterQuestion = getMasterQuestionAccessor().getDetails(mThisTableKey, "");

      mMasterQuestionEVO = origMasterQuestion.deepClone();

      mMasterQuestionEVO.setQuestionText(editorData.getQuestionText());
      mMasterQuestionEVO.setVersionNum(0);

      updateMasterQuestionRelationships(editorData);

      completeCopySetup(editorData);

      validateCopy();

      mMasterQuestionEVO.prepareForInsert();

      mMasterQuestionEVO = getMasterQuestionAccessor().create(mMasterQuestionEVO);

      mThisTableKey = mMasterQuestionEVO.getPK();

      insertIntoAdditionalTables(editorData, false);

      sendEntityEventMessage("MasterQuestion", mMasterQuestionEVO.getPK(), 1);

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

  private void completeCopySetup(MasterQuestionImpl editorData)
    throws Exception
  {
  }

  public void update(MasterQuestionEditorSessionCSO cso)
    throws ValidationException, EJBException
  {
    mLog.debug("update");

    Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

    setUserId(cso.getUserId());
    MasterQuestionImpl editorData = cso.getEditorData();

    mThisTableKey = ((MasterQuestionPK)editorData.getPrimaryKey());
    try
    {
      mMasterQuestionEVO = getMasterQuestionAccessor().getDetails(mThisTableKey, "");

      preValidateUpdate(editorData);

      mMasterQuestionEVO.setQuestionText(editorData.getQuestionText());

      if (editorData.getVersionNum() != mMasterQuestionEVO.getVersionNum()) {
        throw new VersionValidationException(mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + mMasterQuestionEVO.getVersionNum());
      }

      updateMasterQuestionRelationships(editorData);

      completeUpdateSetup(editorData);

      postValidateUpdate();

      getMasterQuestionAccessor().setDetails(mMasterQuestionEVO);

      updateAdditionalTables(editorData);

      sendEntityEventMessage("MasterQuestion", mMasterQuestionEVO.getPK(), 3);
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

  private void preValidateUpdate(MasterQuestionImpl editorData)
    throws ValidationException
  {
  }

  private void postValidateUpdate()
    throws ValidationException
  {
  }

  private void completeUpdateSetup(MasterQuestionImpl editorData)
    throws Exception
  {
  }

  private void updateAdditionalTables(MasterQuestionImpl editorData)
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
    mThisTableKey = ((MasterQuestionPK)paramKey);
    try
    {
      mMasterQuestionEVO = getMasterQuestionAccessor().getDetails(mThisTableKey, "");

      validateDelete();

      deleteDataFromOtherTables();

      mMasterQuestionAccessor.remove(mThisTableKey);

      sendEntityEventMessage("MasterQuestion", mThisTableKey, 2);
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

  private MasterQuestionAccessor getMasterQuestionAccessor()
    throws Exception
  {
    if (mMasterQuestionAccessor == null) {
      mMasterQuestionAccessor = new MasterQuestionAccessor(getInitialContext());
    }
    return mMasterQuestionAccessor;
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