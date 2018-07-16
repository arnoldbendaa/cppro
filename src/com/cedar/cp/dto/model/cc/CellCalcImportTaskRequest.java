// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.Arrays;
import java.util.List;

public class CellCalcImportTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private String mClientURL;
   private String mSourceURL;


   public List toDisplay() {
      return Arrays.asList(new String[]{"Cell calculation data import", "Client side URL:", this.getClientURL(), "Server side tmp URL:", this.getSourceURL()});
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.cc.CellCalcImportTask";
   }

   public String getClientURL() {
      return this.mClientURL;
   }

   public void setClientURL(String clientURL) {
      this.mClientURL = clientURL;
   }

   public String getSourceURL() {
      return this.mSourceURL;
   }

   public void setSourceURL(String sourceURL) {
      this.mSourceURL = sourceURL;
   }
}
