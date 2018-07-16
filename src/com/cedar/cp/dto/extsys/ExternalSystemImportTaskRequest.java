// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class ExternalSystemImportTaskRequest extends AbstractTaskRequest {

   private ExternalSystemRef mExternalSystemRef;
   private String mSourceURL;


   public ExternalSystemImportTaskRequest(ExternalSystemRef externalSYstemRef, String sourceURL) {
      this.mExternalSystemRef = externalSYstemRef;
      this.mSourceURL = sourceURL;
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("External System Defintion Import For:" + this.mExternalSystemRef);
      l.add(" SourceURL:" + this.mSourceURL);
      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.extsys.ExternalSystemImportTask";
   }

   public ExternalSystemRef getExternalSystemRef() {
      return this.mExternalSystemRef;
   }

   public String getSourceURL() {
      return this.mSourceURL;
   }
}
