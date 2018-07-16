// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.xmlreports;

import com.cedar.cp.utc.struts.xmlreports.FolderReportDTO;
import java.util.ArrayList;
import java.util.List;

public class FolderDTO {

   private String mFolderKey;
   private String mFolderId;
   private String mReportsId;
   private String mName;
   private List mReports = new ArrayList();
   private int mLevel;
   private boolean mPublic;
   private boolean mEditable;


   public String getFolderKey() {
      return this.mFolderKey;
   }

   public void setFolderKey(String folderKey) {
      this.mFolderKey = folderKey;
   }

   public String getFolderId() {
      return this.mFolderId;
   }

   public void setFolderId(String folderId) {
      this.mFolderId = folderId;
   }

   public String getReportsId() {
      return this.mReportsId;
   }

   public void setReportsId(String reportsId) {
      this.mReportsId = reportsId;
   }

   public boolean isPublic() {
      return this.mPublic;
   }

   public void setPublic(boolean aPublic) {
      this.mPublic = aPublic;
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public List getReports() {
      return this.mReports;
   }

   public void setReports(List reports) {
      this.mReports = reports;
   }

   public void addReport(FolderReportDTO report) {
      this.mReports.add(report);
   }

   public int getLevel() {
      return this.mLevel;
   }

   public void setLevel(int level) {
      this.mLevel = level;
   }

   public boolean isEditable() {
      return this.mEditable;
   }

   public void setEditable(boolean editable) {
      this.mEditable = editable;
   }

   public String getOptionLabel() {
      StringBuffer buffer = new StringBuffer();

      for(int i = 1; i < this.mLevel; ++i) {
         buffer.append("&nbsp;&nbsp;");
      }

      buffer.append(this.mName);
      return buffer.toString();
   }
}
