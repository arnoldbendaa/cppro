// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.xmlreports;

import com.cedar.cp.utc.common.CPForm;

public class OrganiseReportsForm extends CPForm {

   private String mName;
   private int mParentFolderId;
   private String mFolderKey;
   private String mReportKey;
   private String mAction;
   private boolean mPublic;


   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public int getParentFolderId() {
      return this.mParentFolderId;
   }

   public void setParentFolderId(int parentFolderId) {
      this.mParentFolderId = parentFolderId;
   }

   public boolean isPublic() {
      return this.mPublic;
   }

   public void setPublic(boolean aPublic) {
      this.mPublic = aPublic;
   }

   public String getFolderKey() {
      return this.mFolderKey;
   }

   public void setFolderKey(String folderKey) {
      this.mFolderKey = folderKey;
   }

   public String getReportKey() {
      return this.mReportKey;
   }

   public void setReportKey(String reportKey) {
      this.mReportKey = reportKey;
   }

   public String getAction() {
      return this.mAction;
   }

   public void setAction(String action) {
      this.mAction = action;
   }
}
