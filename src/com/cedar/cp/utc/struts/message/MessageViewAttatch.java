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
import java.io.ByteArrayInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageViewAttatch extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      MessageAttachmentDTO theAttatch = null;
      if(actionForm instanceof MessageOpenForm) {
         MessageOpenForm sb = (MessageOpenForm)actionForm;
         CPContext in = this.getCPContext(httpServletRequest);
         CPConnection out = in.getCPConnection();
         EntityList b = out.getListHelper().getMessageForId(sb.getMessageId(), in.getUserId());
         int size = b.getNumRows();
         MessageDTO dto = new MessageDTO();

         for(int i = 0; i < size; ++i) {
            dto.setMessageId(((Long)b.getValueAt(i, "MessageId")).longValue());
            dto.setMessageUserId(((Long)b.getValueAt(i, "MessageUserId")).longValue());
            dto.setSubject(b.getValueAt(i, "Subject"));
            dto.setContent(b.getValueAt(i, "Content"));
            dto.setRead(b.getValueAt(i, "Read"));
            dto.setDate(b.getValueAt(i, "CreatedTime"));
            EntityList attachments = out.getListHelper().getAttatchmentForMessage(dto.getMessageId());
            MessageAttachmentDTO attatchDTO = new MessageAttachmentDTO();
            String name = (String)attachments.getValueAt(sb.getAttachId(), "AttatchName");
            byte[] data = (byte[])((byte[])attachments.getValueAt(sb.getAttachId(), "Attatch"));
            attatchDTO.setAttatchRead(name, data);
            theAttatch = attatchDTO;
         }

         sb.setMessage(dto);
      }

      if(theAttatch == null) {
         return actionMapping.findForward("fail");
      } else {
         httpServletResponse.reset();
         StringBuilder var17 = new StringBuilder();
         var17.append("inline;").append(" filename=").append(theAttatch.getAttatchRead().getEscapedName());
         httpServletResponse.setContentType(theAttatch.getAttatchRead().getMimeType(httpServletRequest));
         httpServletResponse.setHeader("Content-Disposition", var17.toString());
         ByteArrayInputStream var18 = new ByteArrayInputStream(theAttatch.getAttatchRead().getData());
         ServletOutputStream var19 = httpServletResponse.getOutputStream();

         int var20;
         while((var20 = var18.read()) != -1) {
            var19.write(var20);
         }

         var19.flush();
         var19.close();
         var18.close();
         return null;
      }
   }
}
