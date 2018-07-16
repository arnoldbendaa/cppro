// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.VirementBaseAction;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO$SessionMgr;
import com.cedar.cp.utc.struts.virement.VirementDataEntryForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VirementDataEntrySetupAction extends VirementBaseAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      String requestIdStr = httpServletRequest.getParameter("requestId");
      VirementDataEntryDTO dto = null;
      VirementDataEntryForm form = (VirementDataEntryForm)actionForm;
      if(requestIdStr != null) {
         dto = this.loadRequest(requestIdStr, httpServletRequest);
      } else {
         dto = new VirementDataEntryDTO();
         dto.setNewKey();
      }

      form.setdTO(dto);
      VirementDataEntryDTO$SessionMgr.save(httpServletRequest.getSession(), dto);
      return actionMapping.findForward("success");
   }
}