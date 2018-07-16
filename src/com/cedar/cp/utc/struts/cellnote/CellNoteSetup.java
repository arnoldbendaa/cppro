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
import com.cedar.cp.utc.struts.cellnote.CellNoteForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CellNoteSetup extends CPAction {

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      if(form instanceof CellNoteForm) {
         CellNoteForm myForm = (CellNoteForm)form;
         CPContext context = this.getCPContext(request);
         CPConnection cnx = context.getCPConnection();
         FinanceCubesProcess process = cnx.getFinanceCubesProcess();
         FinanceCubeEditorSession session = process.getFinanceCubeEditorSession(myForm.getModelId(), myForm.getFinanceCubeId());
         FinanceCube fc = session.getFinanceCubeEditor().getFinanceCube();
      }

      return mapping.findForward("success");
   }
}
