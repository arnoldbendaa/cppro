// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.gadget.apps.dataentry;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.gadget.apps.dataentry.AppletViewForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AppletView extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      AppletViewForm myForm = (AppletViewForm)actionForm;
      myForm.setCPContextId(httpServletRequest);
      myForm.setNoDecoration(true);
      if(myForm.getProfileVisId() == null || myForm.getProfileVisId().trim().length() > 0) {
         CPContext context = this.getCPContext(httpServletRequest);
         EntityList eList = context.getCPConnection().getListHelper().getAllDataEntryProfilesForUser(context.getUserContext().getUserId(), myForm.getModelId(), myForm.getBudgetCycleId());
         int size = eList.getNumRows();

         for(int i = 0; i < size; ++i) {
            EntityRef ref = (EntityRef)eList.getValueAt(i, "DataEntryProfile");
            if(ref.getNarrative().equals(myForm.getProfileVisId())) {
               myForm.setProfileRef(ref.getTokenizedKey());
               break;
            }
         }
      }

      return actionMapping.findForward("success");
   }
}
