package com.cedar.cp.dto.reset;

import com.cedar.cp.api.reset.ChallengeQuestionRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import java.io.Serializable;

public class ChallengeQuestionRefImpl extends EntityRefImpl
  implements ChallengeQuestionRef, Serializable
{
  public ChallengeQuestionRefImpl(ChallengeQuestionCK key, String narrative)
  {
    super(key, narrative);
  }

  public ChallengeQuestionRefImpl(ChallengeQuestionPK key, String narrative)
  {
    super(key, narrative);
  }

  public ChallengeQuestionPK getChallengeQuestionPK()
  {
    if ((mKey instanceof ChallengeQuestionCK))
      return ((ChallengeQuestionCK)mKey).getChallengeQuestionPK();
    return (ChallengeQuestionPK)mKey;
  }
}