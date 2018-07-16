// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.report;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.report.ReportDTO;
import com.cedar.cp.utc.struts.report.ReportForm;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReportListSetup extends CPAction {

   private static final String[] typePrefixes = new String[]{"__", "CA", "TD", "VT", "ID", "CM", "CI", "CC", "TT", "**"};


   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      if(actionForm instanceof ReportForm) {
         ReportForm myForm = (ReportForm)actionForm;
         CPContext ctx = this.getCPContext(httpServletRequest);
         ArrayList tasks = new ArrayList();
         ReportDTO dto = null;
         EntityList reportEntityList = null;
         if(ctx.getIsUserAdministrator()) {
            reportEntityList = ctx.getCPConnection().getListHelper().getAllReportsForAdmin();
         } else {
            reportEntityList = ctx.getCPConnection().getListHelper().getAllReportsForUser(ctx.getUserContext().getUserId());
         }

         int count = reportEntityList.getNumRows();

         for(int i = 0; i < count; ++i) {
            dto = new ReportDTO();
            dto.setTaskId(((Integer)reportEntityList.getValueAt(i, "TaskId")).intValue());
            EntityRef ref = (EntityRef)reportEntityList.getValueAt(i, "Report");
            Integer id = (Integer)reportEntityList.getValueAt(i, "ReportId");
            Integer type = (Integer)reportEntityList.getValueAt(i, "ReportType");
            dto.setReportKey(ref.getTokenizedKey());
            dto.setReportId(id.intValue());
            dto.setReportName(buildReportName(id, type));
            dto.setReportType(type.intValue());
            dto.setCreatedDate((Timestamp)reportEntityList.getValueAt(i, "CreatedTime"));
            tasks.add(dto);
         }

         myForm.setReports(tasks);
      }

      return actionMapping.findForward("success");
   }

   public static String buildReportName(Integer id, Integer type) {
      StringBuffer name = new StringBuffer();
      if(type.intValue() >= typePrefixes.length - 1) {
         name.append("**");
      } else {
         name.append(typePrefixes[type.intValue()]);
      }

      NumberFormat formatter = NumberFormat.getIntegerInstance();
      formatter.setGroupingUsed(false);
      formatter.setMinimumIntegerDigits(6);
      name.append(formatter.format((long)id.intValue()));
      return name.toString();
   }

}
