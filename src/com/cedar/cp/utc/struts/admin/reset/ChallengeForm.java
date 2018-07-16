package com.cedar.cp.utc.struts.admin.reset;

import com.cedar.cp.utc.common.CPForm;
import com.cedar.cp.utc.struts.virement.LazyList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.Factory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ChallengeForm extends CPForm
{
  Collection mChallenges;
  String mUserID = null;
  private String mEmail = null;

  public ChallengeForm()
  {
    mChallenges = new LazyList(new Factory()
    {
      public Object create()
      {
        return new ChallengeDTO();
      }
    });
  }

  public String getUserID()
  {
    return mUserID;
  }

  public void setUserID(String userID)
  {
    mUserID = userID;
  }

  public String getEmail()
  {
    return mEmail;
  }

  public void setEmail(String mEmail)
  {
    this.mEmail = mEmail;
  }

  public Collection getChallenges()
  {
    return mChallenges;
  }

  public void setChallenges(Collection challs)
  {
    mChallenges = challs;
  }

  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest)
  {
    ActionErrors errors = new ActionErrors();

    List question = new ArrayList();
    for (Iterator i$ = mChallenges.iterator(); i$.hasNext(); ) { Object challenge = i$.next();

      ChallengeDTO chall = (ChallengeDTO)challenge;
      if (chall == null)
      {
        ActionMessage error = new ActionMessage("cp.props.edit.error", "please provide a valid challenge");
        errors.add("cp.props.edit.error", error);
      }
      else
      {
        if (chall.getQuestion().trim().length() == 0)
        {
          ActionMessage error = new ActionMessage("cp.props.edit.error", "please provide a valid question");
          errors.add("cp.props.edit.error", error);
        }
        if (chall.getAnswer().trim().length() == 0)
        {
          ActionMessage error = new ActionMessage("cp.props.edit.error", "please provide answers to all the questions");
          errors.add("cp.props.edit.error", error);
        }
        if (question.contains(chall.getQuestion().trim()))
        {
          ActionMessage error = new ActionMessage("cp.props.edit.error", "questions must be unique");
          errors.add("cp.props.edit.error", error);
        }
        question.add(chall.getQuestion().trim());
      }

      if (errors.size() > 0)
      {
        break;
      }
    }

    return errors;
  }
}