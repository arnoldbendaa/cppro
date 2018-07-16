// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.test;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class TestTaskRequest extends AbstractTaskRequest implements TaskRequest {

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("TestTask");
      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.test.TestTask";
   }
}
