// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.test;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.dataEntry.DataEntryProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.admin.test.DataEntryTestForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DataEntryTest extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      CPContext context = this.getCPContext(httpServletRequest);
      CPConnection cnx = context.getCPConnection();
      DataEntryTestForm myForm = (DataEntryTestForm)actionForm;
      DataEntryProcess process = cnx.getDataEntryProcess();
      process.executeForegroundCubeUpdate(myForm.getTestXML());
      myForm.setTestXML("Complete");
      return actionMapping.findForward("success");
   }
}
