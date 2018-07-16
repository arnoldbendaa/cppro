package com.cedar.cp.utc.struts.admin.reset;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.UserContext;
import com.cedar.cp.api.user.UserEditor;
import com.cedar.cp.api.user.UserEditorSession;
import com.cedar.cp.api.user.UsersProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChallengeSubmit extends CPAction
{
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    CPContext cpContext = getCPContext(request);
    ChallengeForm myform = (ChallengeForm)form;

    List challenges = (List)myform.getChallenges();
    List challForUpdate = new ArrayList();

    for (Object challenge1 : challenges.toArray())
    {
    	ChallengeDTO challenge = (ChallengeDTO)challenge1;
      Map chall = new HashMap();
      chall.put("question", challenge.getQuestion());
      chall.put("answer", challenge.getAnswer());
      challForUpdate.add(chall);
    }

    UsersProcess process = null;
    UserEditorSession editorSession = null;
    try
    {
      Object userKey = cpContext.getUserContext().getPrimaryKey();
      process = cpContext.getCPConnection().getUsersProcess();
      editorSession = process.getUserEditorSession(userKey);

      UserEditor editor = editorSession.getUserEditor();

      editor.setUserChallenges(challForUpdate);
      editorSession.commit(false);
    }
    catch (CPException cpe)
    {
      cpe.printStackTrace();
    }
    finally
    {
      if (editorSession != null)
      {
        process.terminateSession(editorSession);
      }
    }
    cpContext.setForceQuestions(false);

    return mapping.findForward("success");
  }
}