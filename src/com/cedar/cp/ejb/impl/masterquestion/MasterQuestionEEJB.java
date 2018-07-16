package com.cedar.cp.ejb.impl.masterquestion;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.masterquestion.MasterQuestionCK;
import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

public class MasterQuestionEEJB
  implements EntityBean
{
  protected EntityContext mEntityContext;
  private MasterQuestionDAO mMasterQuestionDAO;
  private transient Log mLog = new Log(getClass());

  public MasterQuestionPK ejbCreate(MasterQuestionEVO details)
    throws CreateException, EJBException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      MasterQuestionDAO dao = getDAO();

      dao.setDetails(details);

      return dao.create();
    }
    catch (DuplicateNameValidationException dnve)
    {
      throw new EJBException(dnve);
    }
    catch (ValidationException e)
    {
      throw new EJBException(e);
    }
    finally
    {
      if (timer != null)
        timer.logDebug("ejbCreate", "");
    }
  }

  public void ejbPostCreate(MasterQuestionEVO details)
    throws CreateException, EJBException
  {
  }

  public void ejbRemove()
    throws RemoveException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      getDAO().remove();
    }
    catch (Exception ve)
    {
      throw new CPException("unable to remove " + getPK());
    }
    finally
    {
      if (timer != null)
        timer.logDebug("ejbRemove", "");
    }
  }

  public MasterQuestionPK ejbFindByPrimaryKey(MasterQuestionPK pk)
    throws FinderException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      return getDAO().findByPrimaryKey(pk);
    }
    catch (ValidationException ve)
    {
      throw new CPException(ve.getMessage(), ve);
    }
    finally
    {
      if (timer != null)
        timer.logDebug("ejbFindByPrimaryKey", pk.toString());
    }
  }

  public void ejbLoad()
  {
    MasterQuestionPK pk = (MasterQuestionPK)mEntityContext.getPrimaryKey();

    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      getDAO().load(pk);
    }
    catch (ValidationException ve)
    {
      throw new EJBException(ve);
    }
    finally
    {
      if (timer != null)
        timer.logDebug("ejbLoad", pk.toString());
    }
  }

  public void ejbStore()
    throws EJBException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      getDAO().store();
    }
    catch (EJBException e)
    {
      mLog.error("ejbStore", "failed", e);
      throw e;
    }
    catch (Exception e)
    {
      mLog.error("ejbStore", "failed", e);
      throw new EJBException(e);
    }
    finally
    {
      if (timer != null)
        timer.logDebug("ejbStore", getPK().toString());
    }
  }

  private MasterQuestionPK getPK()
  {
    return (MasterQuestionPK)mEntityContext.getPrimaryKey();
  }

  public void ejbActivate()
  {
  }

  public void ejbPassivate()
  {
    mLog.debug("ejbPassivate", getPK().toString());

    mMasterQuestionDAO = null;
  }

  public void setEntityContext(EntityContext entityContext)
  {
    mEntityContext = entityContext;
  }

  public void unsetEntityContext()
  {
    mEntityContext = null;
  }

  protected MasterQuestionDAO getDAO()
  {
    if (mMasterQuestionDAO == null) {
      mMasterQuestionDAO = new MasterQuestionDAO();
    }
    return mMasterQuestionDAO;
  }

  public MasterQuestionEVO getDetails(MasterQuestionCK paramCK, String dependants)
    throws ValidationException
  {
    return getDAO().getDetails(paramCK, dependants);
  }

  public MasterQuestionEVO getDetails(String dependants)
    throws ValidationException
  {
    Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
    try
    {
      return getDAO().getDetails(dependants);
    }
    finally
    {
      if (timer != null)
        timer.logDebug("getDetails", dependants);
    }
  }

  public void setDetails(MasterQuestionEVO details)
  {
    getDAO().setDetails(details);
  }

  public MasterQuestionEVO setAndGetDetails(MasterQuestionEVO evo, String dependants)
  {
    return getDAO().setAndGetDetails(evo, dependants);
  }

  public MasterQuestionPK generateKeys()
  {
    return getDAO().generateKeys();
  }
}