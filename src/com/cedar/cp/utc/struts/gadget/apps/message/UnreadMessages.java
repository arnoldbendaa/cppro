// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.gadget.apps.message;

import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.dwr.RecentMessages;
import com.cedar.cp.utc.struts.homepage.HomePageForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UnreadMessages extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      HomePageForm myForm = (HomePageForm)actionForm;
      RecentMessages dwrMessages = new RecentMessages();
      List messages = dwrMessages.getLastMessages(httpServletRequest);
      myForm.setMessages(messages);
      return actionMapping.findForward("success");
   }
}
