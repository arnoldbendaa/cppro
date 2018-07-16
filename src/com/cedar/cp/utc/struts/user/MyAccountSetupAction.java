// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.user;

//import com.adv.stats.perf.J2EEPerformanceLogger;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.user.User;
import com.cedar.cp.api.user.UserEditor;
import com.cedar.cp.api.user.UserEditorSession;
import com.cedar.cp.api.user.UsersProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.user.MyAccountForm;
import com.cedar.cp.util.Timer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyAccountSetupAction extends CPAction {

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      if(form instanceof MyAccountForm) {
         Timer timer = new Timer();
         timer.start();
         MyAccountForm myAccountForm = (MyAccountForm)form;
         CPContext context = this.getCPContext(request);
         CPConnection cnx = context.getCPConnection();
         UsersProcess process = null;
         UserEditorSession editorSession = null;

         try {
            process = cnx.getUsersProcess();
            Object cpe = context.getUserContext().getPrimaryKey();
            editorSession = process.getUserEditorSession(cpe);
            UserEditor editor = editorSession.getUserEditor();
            User user = editor.getUser();
            myAccountForm.setName(user.getFullName());
            myAccountForm.setEmail(user.getEMailAddress());
            myAccountForm.setFinanceUser(user.getExternalSystemUserName());
//            (new J2EEPerformanceLogger()).log(context.getUserId(), "CP", "UpdateUserAccount", 0, timer.stop(), System.currentTimeMillis());
         } catch (CPException var17) {
            var17.printStackTrace();
         } finally {
            if(editorSession != null) {
               process.terminateSession(editorSession);
            }

         }
      }

      return mapping.findForward("success");
   }
}
