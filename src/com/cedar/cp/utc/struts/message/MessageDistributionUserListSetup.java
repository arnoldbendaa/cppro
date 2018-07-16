// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.admin.report.user.UserDTO;
import com.cedar.cp.utc.struts.message.MessageDistributionUserListForm;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageDistributionUserListSetup extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      CPContext ctxt = this.getCPContext(httpServletRequest);
      MessageDistributionUserListForm myForm = (MessageDistributionUserListForm)actionForm;
      EntityList eList = null;
      boolean size = false;
      ArrayList users = new ArrayList();
      UserDTO dto;
      int i;
      int var12;
      if(myForm.getInternal()) {
         eList = ctxt.getCPConnection().getInternalDestinationsProcess().getAllUsersForInternalDestinationId(myForm.getListId());
         var12 = eList.getNumRows();

         for(i = 0; i < var12; ++i) {
            dto = new UserDTO();
            dto.setName(eList.getValueAt(i, "Name").toString());
            dto.setFullName(eList.getValueAt(i, "FullName").toString());
            users.add(dto);
         }
      } else {
         eList = ctxt.getCPConnection().getExternalDestinationsProcess().getAllUsersForExternalDestinationId(myForm.getListId());
         var12 = eList.getNumRows();

         for(i = 0; i < var12; ++i) {
            dto = new UserDTO();
            dto.setEmailAddress(eList.getValueAt(i, "EmailAddress").toString());
            users.add(dto);
         }
      }

      myForm.setUsers(users);
      return actionMapping.findForward("success");
   }
}
