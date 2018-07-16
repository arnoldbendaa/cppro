// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.topdown.MassUpdateDTO;
import com.cedar.cp.utc.struts.topdown.MassUpdateForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MassUpdate extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      if(actionForm instanceof MassUpdateForm) {
         MassUpdateForm myForm = (MassUpdateForm)actionForm;
         String action = myForm.getPassedAction();
         if(action == null) {
            return actionMapping.findForward("success");
         }

         if(action.equals("massUpdate")) {
            CPContext cont = this.getCPContext(httpServletRequest);
            CPConnection conn = cont.getCPConnection();
            MassUpdateDTO dto = (MassUpdateDTO)httpServletRequest.getSession().getAttribute("massUpdate");
            myForm.getMassDTO().setHoldCells(dto.getHoldCells());
            myForm.getMassDTO().setChangeCells(dto.getChangeCells());
            int id = conn.getDataEntryProcess().issueMassUpdate(cont.getIntUserId(), myForm.getMassDTO());
            myForm.setTaskId(id);
            httpServletRequest.getSession().removeAttribute("massUpdate");
         }
      }

      return actionMapping.findForward("success");
   }
}
