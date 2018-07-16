// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.report;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.report.Report;
import com.cedar.cp.api.report.ReportEditorSession;
import com.cedar.cp.api.report.ReportRef;
import com.cedar.cp.api.report.ReportsProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.struts.report.ReportDTO;
import com.cedar.cp.utc.struts.report.ReportDetailForm;
import com.cedar.cp.utc.struts.report.ReportListSetup;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReportDetailSetup extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      CPConnection cpConnection = this.getCPContext(httpServletRequest).getCPConnection();
      ReportDetailForm myForm = (ReportDetailForm)actionForm;
      EntityList list = cpConnection.getListHelper().getWebReportDetails(myForm.getReportId());
      if(list != null && list.getNumRows() == 1) {
         ReportDTO report = new ReportDTO();
         report.setTaskId(((Integer)list.getValueAt(0, "TaskId")).intValue());
         Integer id = (Integer)list.getValueAt(0, "ReportId");
         Integer type = (Integer)list.getValueAt(0, "ReportType");
         report.setReportId(id.intValue());
         report.setReportName(ReportListSetup.buildReportName(id, type));
         report.setReportTypeText(ReportDTO.sTypes[type.intValue()]);
         report.setCreatedDate((Timestamp)list.getValueAt(0, "CreatedTime"));
         report.setHasUpdates(((Boolean)list.getValueAt(0, "HasUpdates")).booleanValue());
         report.setUpdatesApplied(((Boolean)list.getValueAt(0, "UpdatesApplied")).booleanValue());
         ReportRef reportRef = (ReportRef)list.getValueAt(0, "Report");
         ReportsProcess process = cpConnection.getReportsProcess();
         ReportEditorSession session = process.getReportEditorSession(reportRef.getPrimaryKey());
         Report reportXML = session.getReportEditor().getReport();
         String reportHTML = this.transformReportXMLToHTML(reportXML.getReportText());
         report.setReportText(reportHTML);
         myForm.setReportDetails(report);
         return actionMapping.findForward("success");
      } else {
         return null;
      }
   }

   public String transformReportXMLToHTML(String xml) throws Exception {
      StringWriter writer = new StringWriter();
      TransformerFactory tFactory = TransformerFactory.newInstance();
      InputStream xslStream = this.getClass().getResourceAsStream("report.xsl");
      Transformer transformer = tFactory.newTransformer(new StreamSource(xslStream));
      StreamSource source = new StreamSource(new StringReader(xml));
      transformer.transform(source, new StreamResult(writer));
      StringBuffer result = new StringBuffer(writer.toString());
      writer.close();
      return result.toString();
   }
}
