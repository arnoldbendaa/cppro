package com.cedar.cp.ejb.impl.masterquestion;

import com.cedar.cp.api.masterquestion.MasterQuestionRef;
import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
import com.cedar.cp.dto.masterquestion.MasterQuestionRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class MasterQuestionEVO
  implements Serializable
{
  private transient MasterQuestionPK mPK;
  private int mQuestionId;
  private String mQuestionText;
  private int mVersionNum;
  private int mUpdatedByUserId;
  private Timestamp mUpdatedTime;
  private Timestamp mCreatedTime;
  private boolean mModified;

  public MasterQuestionEVO()
  {
  }

  public MasterQuestionEVO(int newQuestionId, String newQuestionText, int newVersionNum)
  {
    mQuestionId = newQuestionId;
    mQuestionText = newQuestionText;
    mVersionNum = newVersionNum;
  }

  public MasterQuestionPK getPK()
  {
    if (mPK == null)
    {
      mPK = new MasterQuestionPK(mQuestionId);
    }

    return mPK;
  }

  public boolean isModified()
  {
    return mModified;
  }

  public int getQuestionId()
  {
    return mQuestionId;
  }

  public String getQuestionText()
  {
    return mQuestionText;
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

  public void setQuestionId(int newQuestionId)
  {
    if (mQuestionId == newQuestionId)
      return;
    mModified = true;
    mQuestionId = newQuestionId;
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

  protected void setUpdatedTime(Timestamp newUpdatedTime)
  {
    mUpdatedTime = newUpdatedTime;
  }

  protected void setCreatedTime(Timestamp newCreatedTime)
  {
    mCreatedTime = newCreatedTime;
  }

  public void setDetails(MasterQuestionEVO newDetails)
  {
    setQuestionId(newDetails.getQuestionId());
    setQuestionText(newDetails.getQuestionText());
    setVersionNum(newDetails.getVersionNum());
    setUpdatedByUserId(newDetails.getUpdatedByUserId());
    setUpdatedTime(newDetails.getUpdatedTime());
    setCreatedTime(newDetails.getCreatedTime());
  }

  public MasterQuestionEVO deepClone()
  {
    MasterQuestionEVO cloned = new MasterQuestionEVO();

    cloned.mModified = mModified;

    cloned.mQuestionId = mQuestionId;
    cloned.mVersionNum = mVersionNum;
    cloned.mUpdatedByUserId = mUpdatedByUserId;
    if (mQuestionText != null)
      cloned.mQuestionText = mQuestionText;
    if (mUpdatedTime != null)
      cloned.mUpdatedTime = Timestamp.valueOf(mUpdatedTime.toString());
    if (mCreatedTime != null)
      cloned.mCreatedTime = Timestamp.valueOf(mCreatedTime.toString());
    return cloned;
  }

  public void prepareForInsert()
  {
    boolean newKey = false;

    if (mQuestionId > 0)
    {
      newKey = true;
      mQuestionId = 0;
    }
    else if (mQuestionId < 1) {
      newKey = true;
    }

    setVersionNum(0);
  }

  public int getInsertCount(int startCount)
  {
    int returnCount = startCount;

    if (mQuestionId < 1)
      returnCount++;
    return returnCount;
  }

  public int assignNextKey(int startKey)
  {
    int nextKey = startKey;

    if (mQuestionId < 1)
    {
      mQuestionId = nextKey;
      nextKey++;
    }

    return nextKey;
  }

  protected void reset()
  {
    mModified = false;
  }

  public MasterQuestionRef getEntityRef()
  {
    return new MasterQuestionRefImpl(getPK(), mQuestionText);
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("QuestionId=");
    sb.append(String.valueOf(mQuestionId));
    sb.append(' ');
    sb.append("QuestionText=");
    sb.append(String.valueOf(mQuestionText));
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
    sb.append("MasterQuestion: ");
    sb.append(toString());
    return sb.toString();
  }
}