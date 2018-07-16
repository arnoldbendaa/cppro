// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.VirementBaseAction;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO$SessionMgr;
import com.cedar.cp.utc.struts.virement.VirementDataEntryForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VirementViewSetupAction extends VirementBaseAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      HttpSession session = httpServletRequest.getSession();
      String requestIdStr = httpServletRequest.getParameter("requestId");
      VirementDataEntryDTO dto = null;
      VirementDataEntryForm form = (VirementDataEntryForm)actionForm;
      if(requestIdStr != null) {
         dto = this.loadRequest(requestIdStr, httpServletRequest);
         VirementDataEntryDTO$SessionMgr.save(httpServletRequest.getSession(), dto);
      } else {
         dto = VirementDataEntryDTO$SessionMgr.load(session, form.getData().getKey());
      }

      form.setdTO(dto);
      form.getData().setReadOnly(true);
      if(form.getUserAction() != null && form.getUserAction().equalsIgnoreCase("showSpreadPage")) {
         VirementDataEntryDTO$SessionMgr.save(session, form.getData());
         httpServletRequest.setAttribute("requestId", form.getData().getKey());
         form.setUserAction("");
         return actionMapping.findForward("spread");
      } else {
         return actionMapping.findForward("success");
      }
   }
}
