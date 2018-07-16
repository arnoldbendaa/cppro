// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.cellnote;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.model.FinanceCube;
import com.cedar.cp.api.model.FinanceCubeEditorSession;
import com.cedar.cp.api.model.FinanceCubesProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.message.MessageHelper;
import com.cedar.cp.utc.struts.cellnote.CellNoteForm;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CellNote extends CPAction {

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      if(form instanceof CellNoteForm) {
         CellNoteForm myForm = (CellNoteForm)form;
         if(myForm.getNewNote() != null && myForm.getNewNote().length() > 0) {
            CPContext context = this.getCPContext(request);
            CPConnection cnx = context.getCPConnection();
            FinanceCubesProcess process = cnx.getFinanceCubesProcess();
            FinanceCubeEditorSession session = process.getFinanceCubeEditorSession(myForm.getModelId(), myForm.getFinanceCubeId());
            FinanceCube fc = session.getFinanceCubeEditor().getFinanceCube();
            Date today = new Date(System.currentTimeMillis());
            StringBuffer sb = new StringBuffer();
            sb.append(this.getCPContext(request).getUserId());
            sb.append(" - ");
            sb.append(MessageHelper.sDateFormat.format(today));
            sb.append("\n");
            sb.append(myForm.getNewNote());
            sb.append("\n");
            sb.append(myForm.getOldNotes());
         }
      }

      return mapping.findForward("success");
   }
}
