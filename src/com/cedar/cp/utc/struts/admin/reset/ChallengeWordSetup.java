package com.cedar.cp.utc.struts.admin.reset;

import com.cedar.cp.utc.common.CPAction;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChallengeWordSetup extends CPAction
{
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ChallengeForm myForm = (ChallengeForm)form;
    // load blank password text input
    List<ChallengeDTO> challenges = new ArrayList<ChallengeDTO>(1);
    ChallengeDTO dto = new ChallengeDTO("HIDDEN_2ND_PASSWORD", "");
    challenges.add(dto);
    myForm.setChallenges(challenges);
    return mapping.findForward("success");
  }
}