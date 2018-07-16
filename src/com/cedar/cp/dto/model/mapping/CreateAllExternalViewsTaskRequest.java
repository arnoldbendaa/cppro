// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class CreateAllExternalViewsTaskRequest extends AbstractTaskRequest implements TaskRequest {

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("(re)creating export views");
      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.CreateAllExternalViews";
   }
}
