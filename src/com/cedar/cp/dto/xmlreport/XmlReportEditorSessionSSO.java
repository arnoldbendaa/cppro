// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlreport;

import com.cedar.cp.dto.xmlreport.XmlReportImpl;
import java.io.Serializable;

public class XmlReportEditorSessionSSO implements Serializable {

   private XmlReportImpl mEditorData;


   public XmlReportEditorSessionSSO() {}

   public XmlReportEditorSessionSSO(XmlReportImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(XmlReportImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public XmlReportImpl getEditorData() {
      return this.mEditorData;
   }
}
