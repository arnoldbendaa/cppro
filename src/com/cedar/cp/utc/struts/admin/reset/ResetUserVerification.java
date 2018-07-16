package com.cedar.cp.utc.struts.admin.reset;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPPrincipal;
import com.cedar.cp.api.base.DriverManager;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.utc.message.CPSystemMessage;
import com.cedar.cp.utc.struts.message.MessageDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ResetUserVerification extends CPAction
{
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ChallengeForm myForm = (ChallengeForm)form;
    CPSystemProperties sysProps = getCPSystemProperties(request);

    List challenges = null;
    ResetAccessor accessor = null;
    String result = "fail";

    ResetAccessor.UserResetDTO resetDTO = null;
    try
    {
      accessor = new ResetAccessor();
      accessor.runUserDetsQueryName(myForm.getUserID());
      resetDTO = accessor.getReset();

      if (!resetDTO.isValid())
      {
        accessor.runUserDetsQueryEMail(myForm.getEmail());
        resetDTO = accessor.getReset();
      }

      
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (accessor != null) {
        accessor.close();
      }
    }
    if ((resetDTO == null) || (!resetDTO.isValid()))
    {
      ActionErrors errors = new ActionErrors();
      String message = "No valid user found";
      if (((myForm.getUserID() == null) || (myForm.getUserID().length() == 0)) && (myForm.getEmail() != null) && (myForm.getEmail().length() > 0))
        message = "The system cannot find a unique User identifier from the Email address entered";
      errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.changePassword.error", message));
      addErrors(request, errors);
      return mapping.findForward(result);
    }

    if (resetDTO.getStrikes() >= sysProps.getResetStrikesAllowed())
    {
      ActionErrors errors = new ActionErrors();
      String message = "You have exceeded the number of attempts to reset your password, as determined by your site system control settings. Your User Identifier has been set to disabled. Please contact your CP administrator for further assistance: " + sysProps.getMailto();
      errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.changePassword.error", message));
      addErrors(request, errors);
      return mapping.findForward("strikes");
    }

    boolean doQuestions = false;

    if (sysProps.getResetMethod().equals("Email"))
    {
      try
      {
        if ((resetDTO.getEmail() == null) || (resetDTO.getEmail().trim().length() == 0))
        {
          doQuestions = true;
        }
        else
        {
          ResetLinkGenerator gen = new ResetLinkGenerator(sysProps.getRootUrl());
          sendMessage(resetDTO, sysProps, gen);
          accessor = new ResetAccessor();
          accessor.updateUserLink(resetDTO.getUserId(), gen.getTimeStamp());
        }
      }
      catch (Exception e)
      {
        ActionErrors errors = new ActionErrors();
        errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.changePassword.error", "Failed to send email : " + e.getMessage()));
        addErrors(request, errors);
        return mapping.findForward("fail");
      }
      finally
      {
        if (accessor != null)
          accessor.close();
      }
      result = "successEmail";

    } else if (sysProps.getResetMethod().equals("Word")) {
    	// load blank password text input
        challenges = new ArrayList<ChallengeDTO>(1);
        ChallengeDTO dto = new ChallengeDTO("HIDDEN_2ND_PASSWORD", "");
        challenges.add(dto);
        myForm.setChallenges(challenges);
        myForm.setUserID(resetDTO.getName());
        if (accessor != null)
            accessor.close();
        result = "successWord";
    }
    if ((doQuestions) || (sysProps.getResetMethod().equals("Question")))
    {
      if (resetDTO.isValid()) {
        challenges = accessor.getUserChallenges(resetDTO.getUserId(), false);
      }
      int fullQs = sysProps.getNumberOfChallengesForSetup();
      int numQs = sysProps.getNumberOfChallengeQuestions();
      if (numQs > fullQs)
        numQs = fullQs;
      int i = 0;

      if ((challenges == null) || (challenges.size() < numQs))
      {
        ActionErrors errors = new ActionErrors();
        errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.changePassword.error", "no challenge questions exist for the user"));
        addErrors(request, errors);
        return mapping.findForward("fail");
      }

      Collections.shuffle(challenges);
      while (challenges.size() > numQs)
      {
        challenges.remove(i);
        i++;
      }

      myForm.setChallenges(challenges);

      myForm.setUserID(resetDTO.getName());

      result = "successQuestion";
    }

    return mapping.findForward(result);
  }

  private String sendMessage(ResetAccessor.UserResetDTO dto, CPSystemProperties sysProps, ResetLinkGenerator gen)
    throws Exception
  {
    MessageDTO mdto = new MessageDTO();
    mdto.setToUser(dto.getName());
    mdto.setToUserEmailAddress(dto.getEmail());
    mdto.setMessageType(1);
    mdto.setSubject(sysProps.getSystemName() + " Password Reset");

    String url = gen.generateLink(mdto.getToUserEmailAddress());

    StringBuilder sb = new StringBuilder();
    sb.append("<h2>You have asked to reset your password </h2>");
    sb.append("<p>either click the following link or copy and paste it into a browser.</p>");
    sb.append("<a href=\"").append(url).append("\">").append(url).append("</a>");
    sb.append("<p> If you have any problems with this please contact your CP Administrator: ").append(sysProps.getMailto()).append("</p>");

    mdto.setContent(sb.toString());

    CPConnection conn = DriverManager.getConnection(sysProps.getConnectionURL(), new CPPrincipal(dto.getName(), ""), true, CPConnection.ConnectionContext.SERVER_SESSION);
    CPSystemMessage sender = new CPSystemMessage(conn);
    sender.send(mdto, false);

    return url;
  }
}