// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlreportfolder;

import com.cedar.cp.api.xmlreportfolder.XmlReportFolder;
import com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK;
import java.io.Serializable;

public class XmlReportFolderImpl implements XmlReportFolder, Serializable, Cloneable {

   private Object mPrimaryKey;
   private int mParentFolderId;
   private String mVisId;
   private int mUserId;
   private int mVersionNum;


   public XmlReportFolderImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mParentFolderId = 0;
      this.mVisId = "";
      this.mUserId = 0;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (XmlReportFolderPK)paramKey;
   }

   public int getParentFolderId() {
      return this.mParentFolderId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setParentFolderId(int paramParentFolderId) {
      this.mParentFolderId = paramParentFolderId;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setUserId(int paramUserId) {
      this.mUserId = paramUserId;
   }
}
