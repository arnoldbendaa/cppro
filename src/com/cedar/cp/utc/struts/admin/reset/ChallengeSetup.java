package com.cedar.cp.utc.struts.admin.reset;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.masterquestion.MasterQuestionsProcess;
import com.cedar.cp.api.reset.ChallengeQuestionsProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPSystemProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChallengeSetup extends CPAction
{
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ChallengeForm myForm = (ChallengeForm)form;
    CPSystemProperties sysProps = getCPSystemProperties(request);
    CPContext cpContext = getCPContext(request);

    int challengeCount = sysProps.getNumberOfChallengesForSetup();
    List challenges = new ArrayList(challengeCount);

    EntityList masterQuestions = null;
    EntityList myQuestions = cpContext.getCPConnection().getChallengeQuestionsProcess().getAllQuestionsAndAnswersByUserID(cpContext.getIntUserId());
    if (myQuestions.getNumRows() < challengeCount)
    {
      masterQuestions = cpContext.getCPConnection().getMasterQuestionsProcess().getAllMasterQuestions();
    }

    EntityList el = myQuestions;

    for (int i = 0; i < challengeCount; i++)
    {
      if (myQuestions.getNumRows() <= i)
        el = masterQuestions;
      ChallengeDTO dto;
      if ((el != null) && (i < el.getNumRows()))
      {
        String text = (String)el.getValueAt(i, "QuestionText");
        String answer = null;
        if (Arrays.asList(el.getHeadings()).contains("QuestionAnswer"))
          answer = (String)el.getValueAt(i, "QuestionAnswer");
        dto = new ChallengeDTO(text, answer);
      }
      else
      {
        dto = new ChallengeDTO("", "");
      }
      challenges.add(dto);
    }

    myForm.setChallenges(challenges);
    return mapping.findForward("success");
  }
}