package com.cedar.cp.dto.reset;

import com.cedar.cp.api.reset.ChallengeQuestionRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllChallengeQuestionsELO extends AbstractELO
  implements Serializable
{
  private static final String[] mEntity = { "ChallengeQuestion", "User" };
  private transient ChallengeQuestionRef mChallengeQuestionEntityRef;
  private transient UserRef mUserEntityRef;
  private transient int mUserId;
  private transient String mQuestionText;
  private transient String mQuestionAnswer;

  public AllChallengeQuestionsELO()
  {
    super(new String[] { "ChallengeQuestion", "User", "UserId", "QuestionText", "QuestionAnswer" });
  }

  public void add(ChallengeQuestionRef eRefChallengeQuestion, UserRef eRefUser, int col1, String col2, String col3)
  {
    List l = new ArrayList();
    l.add(eRefChallengeQuestion);
    l.add(eRefUser);
    l.add(new Integer(col1));
    l.add(col2);
    l.add(col3);
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
    mUserId = ((Integer)l.get(index++)).intValue();
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

  public int getUserId()
  {
    return mUserId;
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