// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class ImportMappedModelTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private boolean mSafeMode;
   private int[] mMappedModelIds;


   public ImportMappedModelTaskRequest(boolean safeMode, int[] mappedModelIds) {
      this.mSafeMode = safeMode;
      this.mMappedModelIds = mappedModelIds;
      this.addExclusiveAccess("ImportMappedModel");
   }

   public int[] getMappedModelIds() {
      return this.mMappedModelIds;
   }

   public boolean isSafeMode() {
      return this.mSafeMode;
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("Import Mapped Model");
      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.ImportMappedModelTask";
   }

   public String getIdentifier() {
      return "ImportModel";
   }
}
