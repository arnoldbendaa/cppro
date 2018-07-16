// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import com.cedar.cp.dto.model.virement.VirementRequestImpl;
import java.util.ArrayList;
import java.util.List;

public class VirementTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private VirementRequestImpl mRequest;


   public VirementTaskRequest(VirementRequestImpl request) {
      this.mRequest = request;
   }

   public VirementRequestImpl getVirementRequest() {
      return this.mRequest;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.virement.VirementTask";
   }

   public List toDisplay() {
      ArrayList list = new ArrayList();
      list.add("Budget Transfer");
      list.add("Model=" + this.mRequest.getModelRef().getNarrative());
      return list;
   }
}
