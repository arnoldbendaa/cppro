// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.xmlreports;

import com.cedar.cp.utc.common.CPForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

public class XmlReportsForm extends CPForm {

   private List mPublicReports;
   private List mPrivateReports;
   private String mReportId;
   private List mFolders;


   public void reset(ActionMapping mapping, HttpServletRequest request) {}

   public List getFolders() {
      return this.mFolders;
   }

   public void setFolders(List folders) {
      this.mFolders = folders;
   }

   public void setPublicReports(List reports) {
      this.mPublicReports = reports;
   }

   public List getPublicReports() {
      return this.mPublicReports;
   }

   public void setPrivateReports(List reports) {
      this.mPrivateReports = reports;
   }

   public List getPrivateReports() {
      return this.mPrivateReports;
   }

   public void setReportId(String id) {
      this.mReportId = id;
   }

   public String getReportId() {
      return this.mReportId;
   }
}
