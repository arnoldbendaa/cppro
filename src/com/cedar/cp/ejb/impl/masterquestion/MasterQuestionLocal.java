package com.cedar.cp.ejb.impl.masterquestion;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
import javax.ejb.EJBLocalObject;

public abstract interface MasterQuestionLocal extends EJBLocalObject
{
  public abstract MasterQuestionEVO getDetails(String paramString)
    throws ValidationException;

  public abstract MasterQuestionPK generateKeys();

  public abstract void setDetails(MasterQuestionEVO paramMasterQuestionEVO);

  public abstract MasterQuestionEVO setAndGetDetails(MasterQuestionEVO paramMasterQuestionEVO, String paramString);
}