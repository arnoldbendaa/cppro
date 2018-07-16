package com.cedar.cp.ejb.impl.masterquestion;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.masterquestion.AllMasterQuestionsELO;
import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
import com.cedar.cp.dto.masterquestion.QuestionByIDELO;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MasterQuestionAccessor
  implements Serializable
{
  private MasterQuestionLocalHome mLocalHome;
  private Hashtable mLocals = new Hashtable();
  private transient InitialContext mInitialContext;

  public MasterQuestionAccessor(InitialContext ctx)
  {
    mInitialContext = ctx;
  }

  private MasterQuestionLocalHome getLocalHome()
  {
    try
    {
      if (mLocalHome != null) {
        return mLocalHome;
      }
      mLocalHome = ((MasterQuestionLocalHome)mInitialContext.lookup("java:comp/env/ejb/MasterQuestionLocalHome"));

      return mLocalHome;
    }
    catch (NamingException ne)
    {
      throw new RuntimeException("error looking up MasterQuestionLocalHome", ne);
    }
  }

  private MasterQuestionLocal getLocal(MasterQuestionPK pk)
    throws Exception
  {
    MasterQuestionLocal local = (MasterQuestionLocal)mLocals.get(pk);

    if (local == null)
    {
      local = getLocalHome().findByPrimaryKey(pk);

      mLocals.put(pk, local);
    }

    return local;
  }

  public MasterQuestionEVO create(MasterQuestionEVO evo)
    throws Exception
  {
    MasterQuestionLocal local = getLocalHome().create(evo);

    MasterQuestionEVO newevo = local.getDetails("<UseLoadedEVOs>");

    MasterQuestionPK pk = newevo.getPK();

    mLocals.put(pk, local);

    return newevo;
  }

  public void remove(MasterQuestionPK pk)
    throws Exception
  {
    getLocal(pk).remove();
  }

  public MasterQuestionEVO getDetails(Object paramKey, String dependants)
    throws Exception
  {
    Object key = paramKey;

    if ((paramKey instanceof EntityRef)) {
      key = ((EntityRef)paramKey).getPrimaryKey();
    }
    if ((key instanceof MasterQuestionPK))
    {
      return getLocal((MasterQuestionPK)key).getDetails(dependants);
    }

    return null;
  }

  public void setDetails(MasterQuestionEVO evo)
    throws Exception
  {
    MasterQuestionPK pk = evo.getPK();

    getLocal(pk).setDetails(evo);
  }

  public MasterQuestionEVO setAndGetDetails(MasterQuestionEVO evo, String dependants)
    throws Exception
  {
    return getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
  }

  public MasterQuestionPK generateKeys(MasterQuestionPK pk)
    throws Exception
  {
    return getLocal(pk).generateKeys();
  }

  public AllMasterQuestionsELO getAllMasterQuestions()
  {
    MasterQuestionDAO dao = new MasterQuestionDAO();
    return dao.getAllMasterQuestions();
  }

  public QuestionByIDELO getQuestionByID(int param1)
  {
    MasterQuestionDAO dao = new MasterQuestionDAO();
    return dao.getQuestionByID(param1);
  }
}