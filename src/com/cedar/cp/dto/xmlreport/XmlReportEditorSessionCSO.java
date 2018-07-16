// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlreport;

import com.cedar.cp.dto.xmlreport.XmlReportImpl;
import java.io.Serializable;

public class XmlReportEditorSessionCSO implements Serializable {

   private int mUserId;
   private XmlReportImpl mEditorData;


   public XmlReportEditorSessionCSO(int userId, XmlReportImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public XmlReportImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
