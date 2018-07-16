// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.xmlreports;

import com.cedar.cp.utc.common.CPForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

public class MoveXmlReportForm extends CPForm {

   private String mReportKey;
   private int mNewFolderId;
   private List mFolders;


   public void reset(ActionMapping mapping, HttpServletRequest request) {}

   public List getFolders() {
      return this.mFolders;
   }

   public void setFolders(List folders) {
      this.mFolders = folders;
   }

   public void setReportKey(String key) {
      this.mReportKey = key;
   }

   public String getReportKey() {
      return this.mReportKey;
   }

   public int getNewFolderId() {
      return this.mNewFolderId;
   }

   public void setNewFolderId(int newFolderId) {
      this.mNewFolderId = newFolderId;
   }
}
