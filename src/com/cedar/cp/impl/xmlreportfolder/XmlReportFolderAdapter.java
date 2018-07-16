// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlreportfolder;

import com.cedar.cp.api.xmlreportfolder.XmlReportFolder;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderImpl;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
import com.cedar.cp.impl.xmlreportfolder.XmlReportFolderEditorSessionImpl;

public class XmlReportFolderAdapter implements XmlReportFolder {

   private XmlReportFolderImpl mEditorData;
   private XmlReportFolderEditorSessionImpl mEditorSessionImpl;


   public XmlReportFolderAdapter(XmlReportFolderEditorSessionImpl e, XmlReportFolderImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected XmlReportFolderEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected XmlReportFolderImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(XmlReportFolderPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getParentFolderId() {
      return this.mEditorData.getParentFolderId();
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public int getUserId() {
      return this.mEditorData.getUserId();
   }

   public void setParentFolderId(int p) {
      this.mEditorData.setParentFolderId(p);
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setUserId(int p) {
      this.mEditorData.setUserId(p);
   }
}
