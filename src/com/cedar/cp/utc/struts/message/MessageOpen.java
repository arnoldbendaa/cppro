// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.message.MessageOpenForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageOpen extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      if(actionForm instanceof MessageOpenForm) {
         MessageOpenForm myForm = (MessageOpenForm)actionForm;
         CPContext cont = this.getCPContext(httpServletRequest);
         CPConnection conn = cont.getCPConnection();
         conn.getMessagesProcess().deleteObject(myForm.getMessage().getMessageId(), myForm.getMessage().getMessageUserId());
      }

      return actionMapping.findForward("success");
   }
}
