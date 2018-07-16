// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.gadget.apps.dataentry;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.gadget.apps.dataentry.ProfileForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class Profiles extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      CPContext context = this.getCPContext(httpServletRequest);
      CPConnection cnx = context.getCPConnection();
      ProfileForm myForm = (ProfileForm)actionForm;
      if(myForm.getTopNodeId() == 0) {
         myForm.setTopNodeId(myForm.getSelectedStructureElementId());
      }

      EntityList profiles = cnx.getListHelper().getAllDataEntryProfilesForUser(context.getUserContext().getUserId(), myForm.getModelId(), myForm.getBudgetCycleId());
      myForm.setProfiles(profiles);
      return actionMapping.findForward("success");
   }
}
