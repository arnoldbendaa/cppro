package com.cedar.cp.dto.masterquestion;

import com.cedar.cp.api.masterquestion.MasterQuestionRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllMasterQuestionsELO extends AbstractELO
  implements Serializable
{
  private static final String[] mEntity = { "MasterQuestion" };
  private transient MasterQuestionRef mMasterQuestionEntityRef;
  private transient int mQuestionId;
  private transient String mQuestionText;

  public AllMasterQuestionsELO()
  {
    super(new String[] { "MasterQuestion", "QuestionId", "QuestionText" });
  }

  public void add(MasterQuestionRef eRefMasterQuestion, int col1, String col2)
  {
    List l = new ArrayList();
    l.add(eRefMasterQuestion);
    l.add(new Integer(col1));
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
    mMasterQuestionEntityRef = ((MasterQuestionRef)l.get(index++));
    mQuestionId = ((Integer)l.get(index++)).intValue();
    mQuestionText = ((String)l.get(index++));
  }

  public MasterQuestionRef getMasterQuestionEntityRef()
  {
    return mMasterQuestionEntityRef;
  }

  public int getQuestionId()
  {
    return mQuestionId;
  }

  public String getQuestionText()
  {
    return mQuestionText;
  }

  public boolean includesEntity(String name)
  {
    for (int i = 0; i < mEntity.length; i++)
      if (name.equals(mEntity[i]))
        return true;
    return false;
  }
}