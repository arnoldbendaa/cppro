package com.cedar.cp.ejb.impl.reset;

import com.cedar.cp.api.reset.ChallengeQuestionRef;
import com.cedar.cp.dto.reset.ChallengeQuestionCK;
import com.cedar.cp.dto.reset.ChallengeQuestionPK;
import com.cedar.cp.dto.reset.ChallengeQuestionRefImpl;
import com.cedar.cp.ejb.impl.user.UserEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class ChallengeQuestionEVO
  implements Serializable
{
  private transient ChallengeQuestionPK mPK;
  private int mUserId;
  private String mQuestionText;
  private String mQuestionAnswer;
  private int mVersionNum;
  private int mUpdatedByUserId;
  private Timestamp mUpdatedTime;
  private Timestamp mCreatedTime;
  private boolean mInsertPending;
  private boolean mDeletePending;
  private boolean mModified;

  public ChallengeQuestionEVO()
  {
  }

  public ChallengeQuestionEVO(int newUserId, String newQuestionText, String newQuestionAnswer, int newVersionNum)
  {
    mUserId = newUserId;
    mQuestionText = newQuestionText;
    mQuestionAnswer = newQuestionAnswer;
    mVersionNum = newVersionNum;
  }

  public ChallengeQuestionPK getPK()
  {
    if (mPK == null)
    {
      mPK = new ChallengeQuestionPK(mUserId, mQuestionText);
    }

    return mPK;
  }

  public boolean isModified()
  {
    return mModified;
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

  public int getVersionNum()
  {
    return mVersionNum;
  }

  public int getUpdatedByUserId()
  {
    return mUpdatedByUserId;
  }

  public Timestamp getUpdatedTime()
  {
    return mUpdatedTime;
  }

  public Timestamp getCreatedTime()
  {
    return mCreatedTime;
  }

  public void setUserId(int newUserId)
  {
    if (mUserId == newUserId)
      return;
    mModified = true;
    mUserId = newUserId;
    mPK = null;
  }

  public void setVersionNum(int newVersionNum)
  {
    if (mVersionNum == newVersionNum)
      return;
    mModified = true;
    mVersionNum = newVersionNum;
  }

  public void setUpdatedByUserId(int newUpdatedByUserId)
  {
    mUpdatedByUserId = newUpdatedByUserId;
  }

  public void setQuestionText(String newQuestionText)
  {
    if (((mQuestionText != null) && (newQuestionText == null)) || ((mQuestionText == null) && (newQuestionText != null)) || ((mQuestionText != null) && (newQuestionText != null) && (!mQuestionText.equals(newQuestionText))))
    {
      mQuestionText = newQuestionText;
      mModified = true;
    }
  }

  public void setQuestionAnswer(String newQuestionAnswer)
  {
    if (((mQuestionAnswer != null) && (newQuestionAnswer == null)) || ((mQuestionAnswer == null) && (newQuestionAnswer != null)) || ((mQuestionAnswer != null) && (newQuestionAnswer != null) && (!mQuestionAnswer.equals(newQuestionAnswer))))
    {
      mQuestionAnswer = newQuestionAnswer;
      mModified = true;
    }
  }

  protected void setUpdatedTime(Timestamp newUpdatedTime)
  {
    mUpdatedTime = newUpdatedTime;
  }

  protected void setCreatedTime(Timestamp newCreatedTime)
  {
    mCreatedTime = newCreatedTime;
  }

  public void setDetails(ChallengeQuestionEVO newDetails)
  {
    setUserId(newDetails.getUserId());
    setQuestionText(newDetails.getQuestionText());
    setQuestionAnswer(newDetails.getQuestionAnswer());
    setVersionNum(newDetails.getVersionNum());
    setUpdatedByUserId(newDetails.getUpdatedByUserId());
    setUpdatedTime(newDetails.getUpdatedTime());
    setCreatedTime(newDetails.getCreatedTime());
  }

  public ChallengeQuestionEVO deepClone()
  {
    ChallengeQuestionEVO cloned = new ChallengeQuestionEVO();

    cloned.mModified = mModified;
    cloned.mInsertPending = mInsertPending;
    cloned.mDeletePending = mDeletePending;

    cloned.mUserId = mUserId;
    cloned.mVersionNum = mVersionNum;
    cloned.mUpdatedByUserId = mUpdatedByUserId;
    if (mQuestionText != null)
      cloned.mQuestionText = mQuestionText;
    if (mQuestionAnswer != null)
      cloned.mQuestionAnswer = mQuestionAnswer;
    if (mUpdatedTime != null)
      cloned.mUpdatedTime = Timestamp.valueOf(mUpdatedTime.toString());
    if (mCreatedTime != null)
      cloned.mCreatedTime = Timestamp.valueOf(mCreatedTime.toString());
    return cloned;
  }

  public void prepareForInsert(UserEVO parent)
  {
    boolean newKey = insertPending();

    setVersionNum(0);
  }

  public int getInsertCount(int startCount)
  {
    int returnCount = startCount;

    return returnCount;
  }

  public int assignNextKey(UserEVO parent, int startKey)
  {
    int nextKey = startKey;

    return nextKey;
  }

  public void setInsertPending()
  {
    mInsertPending = true;
  }

  public boolean insertPending()
  {
    return mInsertPending;
  }

  public void setDeletePending()
  {
    mDeletePending = true;
  }

  public boolean deletePending()
  {
    return mDeletePending;
  }

  protected void reset()
  {
    mModified = false;
    mInsertPending = false;
  }

  public ChallengeQuestionRef getEntityRef(UserEVO evoUser)
  {
    return new ChallengeQuestionRefImpl(new ChallengeQuestionCK(evoUser.getPK(), getPK()), mQuestionText);
  }

  public ChallengeQuestionCK getCK(UserEVO evoUser)
  {
    return new ChallengeQuestionCK(evoUser.getPK(), getPK());
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("UserId=");
    sb.append(String.valueOf(mUserId));
    sb.append(' ');
    sb.append("QuestionText=");
    sb.append(String.valueOf(mQuestionText));
    sb.append(' ');
    sb.append("QuestionAnswer=");
    sb.append(String.valueOf(mQuestionAnswer));
    sb.append(' ');
    sb.append("VersionNum=");
    sb.append(String.valueOf(mVersionNum));
    sb.append(' ');
    sb.append("UpdatedByUserId=");
    sb.append(String.valueOf(mUpdatedByUserId));
    sb.append(' ');
    sb.append("UpdatedTime=");
    sb.append(String.valueOf(mUpdatedTime));
    sb.append(' ');
    sb.append("CreatedTime=");
    sb.append(String.valueOf(mCreatedTime));
    sb.append(' ');
    if (mModified)
      sb.append("modified ");
    if (mInsertPending)
      sb.append("insertPending ");
    if (mDeletePending)
      sb.append("deletePending ");
    return sb.toString();
  }

  public String print()
  {
    return print(0);
  }

  public String print(int indent)
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < indent; i++)
      sb.append(' ');
    sb.append("ChallengeQuestion: ");
    sb.append(toString());
    return sb.toString();
  }
}