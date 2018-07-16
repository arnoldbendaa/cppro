package com.cedar.cp.utc.struts.admin.reset;

import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPSystemProperties;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class PasswordResetFromLink extends CPAction
{
  public static final int MILLISECONDS_IN_DAY = 86400000;

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    CPSystemProperties sysProps = getCPSystemProperties(request);
    Date today = new Date();

    int numdays = sysProps.getResetLinkValidity();

    int ddl = numdays * 86400000;

    String link = request.getParameter("unqid");

    String created = link.substring(link.length() - 12);

    ResetAccessor accessor = null;
    ResetAccessor.UserResetDTO resetDTO = null;
    try
    {
      accessor = new ResetAccessor();
      accessor.runUserLink(created);
      resetDTO = accessor.getReset();
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
      errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.changePassword.error", "No valid link record"));
      addErrors(request, errors);
      return mapping.findForward("fail");
    }

    Date createDate;
    try
    {
      createDate = ResetLinkGenerator.sLinkFormat.parse(created);
    }
    catch (ParseException pe)
    {
      ActionErrors errors = new ActionErrors();
      errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.changePassword.error", "Link is invalid"));
      addErrors(request, errors);
      return mapping.findForward("fail");
    }

    long daysdiff = today.getTime() - createDate.getTime();

    if ((createDate.after(today)) || (daysdiff > ddl))
    {
      ActionErrors errors = new ActionErrors();
      errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.changePassword.error", "Link record has timed out"));
      addErrors(request, errors);
      return mapping.findForward("fail");
    }

    ChallengeForm myForm = (ChallengeForm)form;
    myForm.setUserID(resetDTO.getName());
    return mapping.findForward("success");
  }
}