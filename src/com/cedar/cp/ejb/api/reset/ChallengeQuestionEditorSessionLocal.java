package com.cedar.cp.ejb.api.reset;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.reset.ChallengeQuestionCK;
import com.cedar.cp.dto.reset.ChallengeQuestionEditorSessionCSO;
import com.cedar.cp.dto.reset.ChallengeQuestionEditorSessionSSO;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public abstract interface ChallengeQuestionEditorSessionLocal extends EJBLocalObject
{
  public abstract ChallengeQuestionEditorSessionSSO getItemData(int paramInt, Object paramObject)
    throws ValidationException, EJBException;

  public abstract ChallengeQuestionEditorSessionSSO getNewItemData(int paramInt)
    throws EJBException;

  public abstract ChallengeQuestionCK insert(ChallengeQuestionEditorSessionCSO paramChallengeQuestionEditorSessionCSO)
    throws ValidationException, EJBException;

  public abstract EntityList getOwnershipData(int paramInt, Object paramObject)
    throws EJBException;

  public abstract ChallengeQuestionCK copy(ChallengeQuestionEditorSessionCSO paramChallengeQuestionEditorSessionCSO)
    throws ValidationException, EJBException;

  public abstract void update(ChallengeQuestionEditorSessionCSO paramChallengeQuestionEditorSessionCSO)
    throws ValidationException, EJBException;

  public abstract void delete(int paramInt, Object paramObject)
    throws ValidationException, EJBException;
}