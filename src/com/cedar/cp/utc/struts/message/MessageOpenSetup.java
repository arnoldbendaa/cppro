// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.message.MessageAttachmentDTO;
import com.cedar.cp.utc.struts.message.MessageDTO;
import com.cedar.cp.utc.struts.message.MessageOpenForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageOpenSetup extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      if(actionForm instanceof MessageOpenForm) {
         MessageOpenForm myForm = (MessageOpenForm)actionForm;
         CPContext cnt = this.getCPContext(httpServletRequest);
         CPConnection conn = cnt.getCPConnection();
         EntityList list;
         if(!myForm.getSource().equals("list") && !myForm.getSource().equals("last") && !myForm.getSource().equals("recent")) {
            list = conn.getListHelper().getMessageForIdSentItem(myForm.getMessageId(), cnt.getUserId());
         } else {
            list = conn.getListHelper().getMessageForId(myForm.getMessageId(), cnt.getUserId());
         }

         int size = list.getNumRows();
         MessageDTO dto = new MessageDTO();

         for(int i = 0; i < size; ++i) {
            dto.setMessageId(((Long)list.getValueAt(i, "MessageId")).longValue());
            dto.setMessageUserId(((Long)list.getValueAt(i, "MessageUserId")).longValue());
            dto.setSubject(list.getValueAt(i, "Subject"));
            dto.setContent(list.getValueAt(i, "Content"));
            dto.setRead(list.getValueAt(i, "Read"));
            dto.setDate(list.getValueAt(i, "CreatedTime"));
            EntityList attachments = conn.getListHelper().getAttatchmentForMessage(dto.getMessageId());

            for(int fromList = 0; fromList < attachments.getNumRows(); ++fromList) {
               MessageAttachmentDTO name = new MessageAttachmentDTO();
               String fullname = (String)attachments.getValueAt(fromList, "AttatchName");
               byte[] j = (byte[])((byte[])attachments.getValueAt(fromList, "Attatch"));
               name.setAttatchRead(fullname, j);
               dto.addAttatchment(name);
            }

            EntityList var17 = conn.getListHelper().getMessageFromUser(dto.getMessageId());
            StringBuffer var19 = new StringBuffer();
            StringBuffer var18 = new StringBuffer();

            for(int var20 = 0; var20 < var17.getNumRows(); ++var20) {
               if(var20 != 0) {
                  var19.append(";");
                  var18.append(";");
               }

               var19.append(var17.getValueAt(var20, "Name"));
               var18.append(var17.getValueAt(var20, "FullName"));
            }

            if(var19.length() > 0) {
               dto.setFromUser_VisID(var19.toString());
               dto.setFromUser(var18.toString());
            } else {
               dto.setFromUser_VisID("System");
               dto.setFromUser("System");
            }
         }

         myForm.setMessage(dto);
         conn.getMessagesProcess().markAsRead(dto.getMessageId(), dto.getMessageUserId());
      }

      return actionMapping.findForward("success");
   }
}
