package com.cedar.cp.dto.masterquestion;

import com.cedar.cp.api.masterquestion.MasterQuestionRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import java.io.Serializable;

public class MasterQuestionRefImpl extends EntityRefImpl
  implements MasterQuestionRef, Serializable
{
  public MasterQuestionRefImpl(MasterQuestionPK key, String narrative)
  {
    super(key, narrative);
  }

  public MasterQuestionPK getMasterQuestionPK()
  {
    return (MasterQuestionPK)mKey;
  }
}