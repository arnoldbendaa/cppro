package com.cedar.cp.dto.reset;

import com.cedar.cp.api.reset.ChallengeQuestionRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllQuestionsAndAnswersByUserIDELO extends AbstractELO
  implements Serializable
{
  private static final String[] mEntity = { "ChallengeQuestion", "User" };
  private transient ChallengeQuestionRef mChallengeQuestionEntityRef;
  private transient UserRef mUserEntityRef;
  private transient String mQuestionText;
  private transient String mQuestionAnswer;

  public AllQuestionsAndAnswersByUserIDELO()
  {
    super(new String[] { "ChallengeQuestion", "User", "QuestionText", "QuestionAnswer" });
  }

  public void add(ChallengeQuestionRef eRefChallengeQuestion, UserRef eRefUser, String col1, String col2)
  {
    List l = new ArrayList();
    l.add(eRefChallengeQuestion);
    l.add(eRefUser);
    l.add(col1);
    l.add(col2);
    mCollection.add(l);
  }

  public void next()
  {
    if (mIterator == null) {
      reset();
    }
    mCurrRowIndex += 1;
    List l = (List)mIterator.next();
    int index = 0;
    mChallengeQuestionEntityRef = ((ChallengeQuestionRef)l.get(index++));
    mUserEntityRef = ((UserRef)l.get(index++));
    mQuestionText = ((String)l.get(index++));
    mQuestionAnswer = ((String)l.get(index++));
  }

  public ChallengeQuestionRef getChallengeQuestionEntityRef()
  {
    return mChallengeQuestionEntityRef;
  }

  public UserRef getUserEntityRef()
  {
    return mUserEntityRef;
  }

  public String getQuestionText()
  {
    return mQuestionText;
  }

  public String getQuestionAnswer()
  {
    return mQuestionAnswer;
  }

  public boolean includesEntity(String name)
  {
    for (int i = 0; i < mEntity.length; i++)
      if (name.equals(mEntity[i]))
        return true;
    return false;
  }
}