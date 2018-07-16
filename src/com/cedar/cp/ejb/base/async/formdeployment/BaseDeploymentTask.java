package com.cedar.cp.ejb.base.async.formdeployment;

import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.impl.model.BudgetUserDAO;
import com.cedar.cp.ejb.impl.user.DataEntryProfileDAO;
import com.cedar.cp.ejb.impl.user.DataEntryProfileEVO;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserEVO;
import javax.naming.InitialContext;

public abstract class BaseDeploymentTask extends AbstractTask
{
  private transient UserAccessor mUserccessor;
  private transient InitialContext mInitialContext;
  BudgetUserDAO mBudgetUserDAO;
  DataEntryProfileDAO mDataEntryProfileDAO;

  public int getReportType()
  {
    return 0;
  }

  protected String checkUnique(String id, UserEVO evo, int modelId, String prepend, Integer prependId)
  {
    StringBuilder sb = new StringBuilder();

    if (prepend != null) {
      sb.append(prepend);
    }
    if (prependId != null) {
      sb.append(prependId).append("_");
    }
    sb.append(id);

    String test = sb.toString();

    for (DataEntryProfileEVO deEvo : evo.getDataEntryProfiles())
    {
      if ((modelId == deEvo.getModelId()) && (test.equals(deEvo.getVisId())))
      {
        Integer localInteger1;
        if (prepend == null)
        {
          prepend = "fd_";
        }
        else if (prependId == null) {
          prependId = Integer.valueOf(0);
        } else {
          localInteger1 = prependId; Integer localInteger2 = prependId = Integer.valueOf(prependId.intValue() + 1);
        }

        return checkUnique(id, evo, modelId, prepend, prependId);
      }
    }

    return test;
  }

  public void setInitialContext(InitialContext initialContext)
    throws Exception
  {
    if (initialContext == null)
    {
      mInitialContext = new InitialContext();
    }
    else
    {
      mInitialContext = initialContext;
    }
  }

  protected InitialContext getInitialContext()
  {
    return mInitialContext;
  }

  protected UserAccessor getUserAccessor() throws Exception
  {
    if (mUserccessor == null) {
      mUserccessor = new UserAccessor(getInitialContext());
    }
    return mUserccessor;
  }

  protected BudgetUserDAO getBudgetUserDAO()
  {
    if (mBudgetUserDAO == null)
    {
      mBudgetUserDAO = new BudgetUserDAO();
    }

    return mBudgetUserDAO;
  }

  protected DataEntryProfileDAO getDataEntryProfileDAO()
  {
    if (mDataEntryProfileDAO == null)
    {
      mDataEntryProfileDAO = new DataEntryProfileDAO();
    }

    return mDataEntryProfileDAO;
  }
}