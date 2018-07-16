// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.message.CPSystemMessage;
import com.cedar.cp.utc.struts.message.MessageDTO;
import com.cedar.cp.utc.struts.message.MessageNewForm;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageNew extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      ArrayList errors = new ArrayList();
      if(actionForm instanceof MessageNewForm) {
         MessageNewForm myForm = (MessageNewForm)actionForm;
         MessageDTO dto = myForm.getMessage();
         CPContext ctxt = this.getCPContext(httpServletRequest);
         dto.setFromUser(ctxt.getUserName());
         dto.setFromUser_VisID(ctxt.getUserId());
         EntityList entity = ctxt.getCPConnection().getUsersProcess().getUserMessageAttributes();

         for(int system_sender = 0; system_sender < entity.getNumRows(); ++system_sender) {
            String name = (String)entity.getValueAt(system_sender, "Name");
            String mailAddress = (String)entity.getValueAt(system_sender, "EMailAddress");
            if(ctxt.getUserId().equals(name)) {
               dto.setFromUserEmailAddress(mailAddress);
               break;
            }
         }

         CPSystemMessage var13 = new CPSystemMessage(ctxt);
         var13.send(dto, true);
         if(errors.size() > 0) {
            myForm.setMessageSendErrors(errors);
            return actionMapping.findForward("errors");
         }
      }

      return actionMapping.findForward("success");
   }
}
