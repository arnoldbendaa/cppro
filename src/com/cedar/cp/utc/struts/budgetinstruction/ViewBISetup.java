// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.budgetinstruction;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.budgetinstruction.BudgetInstruction;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionEditorSession;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionsProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import java.io.ByteArrayInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ViewBISetup extends CPAction {

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      String id_param = request.getParameter("BI_ID");
      if(id_param == null) {
         return mapping.findForward("fail");
      } else {
         int biid = Integer.parseInt(id_param);
         CPContext context = this.getCPContext(request);
         CPConnection cnx = context.getCPConnection();
         BudgetInstructionsProcess process = cnx.getBudgetInstructionsProcess();
         BudgetInstructionEditorSession session = process.getBudgetInstructionEditorSession(biid);
         BudgetInstruction bi = session.getBudgetInstructionEditor().getBudgetInstruction();
         CPFileWrapper theAttatch = new CPFileWrapper(bi.getDocument(), bi.getDocumentRef());
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
