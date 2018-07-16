// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import com.cedar.cp.util.Pair;
import java.util.Arrays;
import java.util.List;

public class DynamicCellCalcImportTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private List<Pair<String, String>> mClientServerURLs;


   public List toDisplay() {
      return Arrays.asList(new String[]{"Dynamic cell calculation data import", "Client side URL:", this.getClientURL(), "Server side tmp URL:", this.getServerURL()});
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.cc.DynamicCellCalcImportTask";
   }

   public String getClientURL() {
      return this.mClientServerURLs.size() > 0?(String)((Pair)this.mClientServerURLs.get(0)).getChild1():null;
   }

   public String getServerURL() {
      return this.mClientServerURLs.size() > 0?(String)((Pair)this.mClientServerURLs.get(0)).getChild2():null;
   }

   public List<Pair<String, String>> getClientServerURLs() {
      return this.mClientServerURLs;
   }

   public void setClientServerURLs(List<Pair<String, String>> clientServerURLs) {
      this.mClientServerURLs = clientServerURLs;
   }
}
