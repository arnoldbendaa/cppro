// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlreportfolder;

import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderImpl;
import java.io.Serializable;

public class XmlReportFolderEditorSessionSSO implements Serializable {

   private XmlReportFolderImpl mEditorData;


   public XmlReportFolderEditorSessionSSO() {}

   public XmlReportFolderEditorSessionSSO(XmlReportFolderImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(XmlReportFolderImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public XmlReportFolderImpl getEditorData() {
      return this.mEditorData;
   }
}
