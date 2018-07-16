// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.attachment;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.extendedattachment.ExtendedAttachment;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentEditorSession;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentsProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import java.io.ByteArrayInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ViewAttachmentSetup extends CPAction {

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      String id_param = request.getParameter("ID");
      if(id_param == null) {
         return mapping.findForward("fail");
      } else {
         int id = Integer.parseInt(id_param);
         CPContext context = this.getCPContext(request);
         CPConnection cnx = context.getCPConnection();
         ExtendedAttachmentsProcess process = cnx.getExtendedAttachmentsProcess();
         ExtendedAttachmentEditorSession session = process.getExtendedAttachmentEditorSessionForId(Integer.valueOf(id));
         ExtendedAttachment attachment = session.getExtendedAttachmentEditor().getExtendedAttachment();
         CPFileWrapper theAttatch = new CPFileWrapper(attachment.getAttatch(), attachment.getFileName());
         response.reset();
         StringBuilder sb = new StringBuilder();
         sb.append("inline;").append(" filename=").append(theAttatch.getEscapedName());
         response.setContentType(theAttatch.getMimeType(request));
         response.setHeader("Content-Disposition", sb.toString());
         ByteArrayInputStream in = new ByteArrayInputStream(theAttatch.getData());
         ServletOutputStream out = response.getOutputStream();

         int b;
         while((b = in.read()) != -1) {
            out.write(b);
         }

         out.flush();
         out.close();
         in.close();
         return null;
      }
   }
}
