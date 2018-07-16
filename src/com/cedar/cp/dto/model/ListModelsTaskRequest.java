// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class ListModelsTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private String mParam1;
   private int mParam2;


   public ListModelsTaskRequest(String param1, int param2) {
      this.mParam1 = param1;
      this.mParam2 = param2;
   }

   public List toDisplay() {
      ArrayList list = new ArrayList();
      list.add("param1=" + this.mParam1);
      list.add("param2=" + this.mParam2);
      return list;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.ListModels";
   }
}
