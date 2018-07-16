package com.cedar.cp.dto.model.globalmapping2;

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
