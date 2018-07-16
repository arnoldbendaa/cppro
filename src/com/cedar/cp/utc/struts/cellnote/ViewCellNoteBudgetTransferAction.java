// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.cellnote;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.struts.virement.VirementQueryForm;
import com.cedar.cp.utc.struts.virement.VirementQueryTransform;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ViewCellNoteBudgetTransferAction extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
      VirementQueryForm form = (VirementQueryForm)actionForm;
      if(this.getErrors(request) != null) {
         this.getErrors(request).clear();
      }

      String requestIdStr = request.getParameter("requestId");
      if(requestIdStr != null && !requestIdStr.isEmpty()) {
         int requestId = -1;
         if(!requestIdStr.isEmpty()) {
            try {
               requestId = Integer.parseInt(requestIdStr);
            } catch (NumberFormatException var11) {
               this.addFieldError(request, "requestId", var11.getMessage());
               return actionMapping.findForward("error");
            }
         }

         if(requestId > -1) {
            CPConnection connection = this.getCPContext(request).getCPConnection();
            String virementXML = connection.getVirementRequestsProcess().queryVirementRequest(requestId);
            if(virementXML == null) {
               ActionErrors errors1 = new ActionErrors();
               errors1.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.virement.validation.error", "No budget transfers found for this selection criteria."));
               this.addErrors(request, errors1);
               return actionMapping.findForward("error");
            }

            VirementQueryTransform errors = new VirementQueryTransform();
            form.setOutputText(errors.transform(virementXML));
         }

         return actionMapping.findForward("success");
      } else {
         this.addFieldError(request, "requestId", "Absence request Id");
         return actionMapping.findForward("error");
      }
   }

   private void addFieldError(HttpServletRequest request, String propertyName, String message) {
      ActionErrors errors = new ActionErrors();
      errors.add(propertyName, new ActionMessage("cp.virement.validation.error", message));
      this.addErrors(request, errors);
   }
}
