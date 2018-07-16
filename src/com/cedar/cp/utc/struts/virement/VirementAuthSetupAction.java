// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.utc.struts.virement.VirementBaseAction;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO$SessionMgr;
import com.cedar.cp.utc.struts.virement.VirementDataEntryForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class VirementAuthSetupAction extends VirementBaseAction {

   public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
      try {
         HttpSession ve = request.getSession();
         String errors1 = request.getParameter("requestId");
         VirementDataEntryDTO dto = null;
         VirementDataEntryForm form = (VirementDataEntryForm)actionForm;
         if(errors1 != null) {
            dto = this.loadRequest(errors1, request);
            dto.setReadOnly(true);
            VirementDataEntryDTO$SessionMgr.save(ve, dto);
         } else {
            dto = VirementDataEntryDTO$SessionMgr.load(ve, form.getData().getKey());
         }

         dto.setReadOnly(true);
         form.setdTO(dto);
         form.getData().setDimensionHeaders(this.queryDimensionHeaders(request, form.getData().getModelId()));
         return mapping.findForward("success");
      } catch (ValidationException var9) {
         ActionErrors errors = new ActionErrors();
         errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.virement.validation.error", var9.getMessage()));
         this.addErrors(request, errors);
         return mapping.findForward("success");
      }
   }
}
