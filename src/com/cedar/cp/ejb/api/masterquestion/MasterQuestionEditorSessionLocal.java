package com.cedar.cp.ejb.api.masterquestion;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.masterquestion.MasterQuestionEditorSessionCSO;
import com.cedar.cp.dto.masterquestion.MasterQuestionEditorSessionSSO;
import com.cedar.cp.dto.masterquestion.MasterQuestionPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public abstract interface MasterQuestionEditorSessionLocal extends EJBLocalObject
{
  public abstract MasterQuestionEditorSessionSSO getItemData(int paramInt, Object paramObject)
    throws ValidationException, EJBException;

  public abstract MasterQuestionEditorSessionSSO getNewItemData(int paramInt)
    throws EJBException;

  public abstract MasterQuestionPK insert(MasterQuestionEditorSessionCSO paramMasterQuestionEditorSessionCSO)
    throws ValidationException, EJBException;

  public abstract MasterQuestionPK copy(MasterQuestionEditorSessionCSO paramMasterQuestionEditorSessionCSO)
    throws ValidationException, EJBException;

  public abstract void update(MasterQuestionEditorSessionCSO paramMasterQuestionEditorSessionCSO)
    throws ValidationException, EJBException;

  public abstract void delete(int paramInt, Object paramObject)
    throws ValidationException, EJBException;
}