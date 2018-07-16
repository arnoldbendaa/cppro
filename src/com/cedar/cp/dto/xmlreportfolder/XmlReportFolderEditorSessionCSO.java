// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlreportfolder;

import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderImpl;
import java.io.Serializable;

public class XmlReportFolderEditorSessionCSO implements Serializable {

   private int mUserId;
   private XmlReportFolderImpl mEditorData;


   public XmlReportFolderEditorSessionCSO(int userId, XmlReportFolderImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public XmlReportFolderImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
