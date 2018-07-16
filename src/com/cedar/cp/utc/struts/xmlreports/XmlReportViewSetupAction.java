// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.xmlreports;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.xmlreports.XmlReportsForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class XmlReportViewSetupAction extends CPAction {

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    XmlReportsForm reportFormXml = (XmlReportsForm)form;

	    if ((reportFormXml.getReportId() != null) && (!reportFormXml.getReportId().equals("new"))) {
	      CPContext cnt = getCPContext(request);
	      if (!reportFormXml.getReportId().startsWith("XmlReportPK|")) {
	        EntityList list = cnt.getCPConnection().getListHelper().getSingleXmlReport(cnt.getIntUserId(), reportFormXml.getReportId());
	        if (list.getNumRows() > 0) {
	          EntityRef result = (EntityRef)list.getValueAt(0, "XmlReport");
	          reportFormXml.setReportId(result.getPrimaryKey().toString());
	        } else {
	          return mapping.findForward("noReport");
	        }
	      }
	    }

	    reportFormXml.setCPContextId(request);

	    return mapping.findForward("success");
   }
}
