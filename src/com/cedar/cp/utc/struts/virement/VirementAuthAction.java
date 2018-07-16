// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.utc.struts.virement.VirementAuthEntryForm;
import com.cedar.cp.utc.struts.virement.VirementAuthPointDTO;
import com.cedar.cp.utc.struts.virement.VirementBaseAction;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO$SessionMgr;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class VirementAuthAction extends VirementBaseAction {

   public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession();
      VirementAuthEntryForm form = (VirementAuthEntryForm)actionForm;

      ActionForward e;
      try {
         VirementAuthPointDTO e1;
         if(form.getUserAction() != null && form.getUserAction().equalsIgnoreCase("authorise")) {
            e1 = form.getData().findAuthPoint(form.getCurrentAuthPointKey());
            this.updateAuthPoint(request, form.getData().getKey(), form.getCurrentAuthPointKey(), e1.getNotes(), true);
         } else if(form.getUserAction() != null && form.getUserAction().equalsIgnoreCase("reject")) {
            e1 = form.getData().findAuthPoint(form.getCurrentAuthPointKey());
            this.updateAuthPoint(request, form.getData().getKey(), form.getCurrentAuthPointKey(), e1.getNotes(), false);
         } else if(form.getUserAction() != null && form.getUserAction().equalsIgnoreCase("showSpreadPage")) {
            form.setData(VirementDataEntryDTO$SessionMgr.load(session, form.getData().getKey()));
            form.getData().setReadOnly(true);
            request.setAttribute("parentPage", "auth");
            form.setUserAction("");
            e = mapping.findForward("spread");
            return e;
         }

         e = mapping.findForward("success");
      } catch (ValidationException var13) {
         ActionErrors errors = new ActionErrors();
         errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.virement.validation.error", var13.getMessage()));
         this.addErrors(request, errors);
         ActionForward var9 = mapping.findForward("auth");
         return var9;
      } finally {
         form.setUserAction((String)null);
         form.setCurrentAuthPointKey((String)null);
      }

      return e;
   }
}
