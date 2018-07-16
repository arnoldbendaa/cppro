// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.VirementBaseAction;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO$SessionMgr;
import com.cedar.cp.utc.struts.virement.VirementGroupDTO;
import com.cedar.cp.utc.struts.virement.VirementLineDTO;
import com.cedar.cp.utc.struts.virement.VirementSpreadForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VirementSpreadSetupAction extends VirementBaseAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      VirementSpreadForm form = (VirementSpreadForm)actionForm;
      VirementDataEntryDTO dto = form.getData();
      dto = VirementDataEntryDTO$SessionMgr.load(httpServletRequest.getSession(), form.getData().getKey());
      form.setData(dto);
      List groups = (List)dto.getGroups();
      VirementGroupDTO group = (VirementGroupDTO)groups.get(form.getCurrentGroup());
      List lines = (List)group.getLines();
      VirementLineDTO line = (VirementLineDTO)lines.get(form.getCurrentLine());
      form.setLine(line);
      form.getData().setDimensionHeaders(this.queryDimensionHeaders(httpServletRequest, form.getData().getModelId()));
      this.validateStructureElements(this.getCPContext(httpServletRequest).getCPConnection(), httpServletRequest, form);
      form.setProfiles(this.queryWeightingProfiles(this.getCPContext(httpServletRequest).getCPConnection(), form.getData(), line, true));
      form.setNumRows(line.getSpreadProfile().size());
      if(httpServletRequest.getAttribute("parentPage") != null) {
         form.setParentPage((String)httpServletRequest.getAttribute("parentPage"));
      }

      return actionMapping.findForward("success");
   }
}
