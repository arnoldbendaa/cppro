// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.dataEntry.DataEntryProcess;
import com.cedar.cp.utc.common.CPBaseAJAXAction;
import com.cedar.cp.utc.common.CPContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.json.JSONObject;

public class AJAXValidateCal extends CPBaseAJAXAction {

   public Object processRequest(ActionForm actionForm, HttpServletRequest httpServletRequest, CPContext context, CPConnection conn) throws Exception {
      Integer financeCubeId = Integer.valueOf(Integer.parseInt(httpServletRequest.getParameter("cubeId")));
      Integer srcCalElem = Integer.valueOf(Integer.parseInt(httpServletRequest.getParameter("srcCalElem")));
      Integer targetCalElem = Integer.valueOf(Integer.parseInt(httpServletRequest.getParameter("targetCalElem")));
      DataEntryProcess process = conn.getDataEntryProcess();
      String result = process.validateMassUpdate(financeCubeId.intValue(), srcCalElem.intValue(), targetCalElem.intValue());
      JSONObject returnObject = new JSONObject();
      returnObject.put("valid", result == null);
      if(result != null) {
         returnObject.put("reason", result);
      }

      return returnObject;
   }
}
