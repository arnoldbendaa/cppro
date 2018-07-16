// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.logonhistory;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.logonhistory.LogonHistorysProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.admin.logonhistory.LogonHistoryDTO;
import com.cedar.cp.utc.struts.admin.logonhistory.LogonHistoryForm;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogonHistoryAction extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      CPContext ctx = this.getCPContext(httpServletRequest);
      CPConnection conn = ctx.getCPConnection();
      LogonHistorysProcess logonHistoryProcess = conn.getLogonHistorysProcess();
      LogonHistoryForm form = (LogonHistoryForm)actionForm;
      EntityList logonHistoryEntityList = logonHistoryProcess.getAllLogonHistorysReport(form.getUsernameSearch(), form.getEventDate(), form.getEventType());
      int size = logonHistoryEntityList.getNumRows();
      String forward = "success";
      if(form.isDoScroll()) {
         form.setPotentialSize(size);
         form.setMaxResult(size);
      } else {
         forward = "popup";
      }

      ArrayList list = new ArrayList();

      for(int i = 0; i < size; ++i) {
         LogonHistoryDTO dto = new LogonHistoryDTO();
         dto.setUsername(String.valueOf(logonHistoryEntityList.getValueAt(i, "UserName")));
         dto.setEventType(((Integer)logonHistoryEntityList.getValueAt(i, "EventType")).intValue());
         dto.setEventDate((Timestamp)logonHistoryEntityList.getValueAt(i, "EventDate"));
         list.add(dto);
      }

      form.setDtoList(list);
      return actionMapping.findForward(forward);
   }
}
