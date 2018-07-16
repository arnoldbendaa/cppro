package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class ImportGlobalMappedModel2TaskRequest extends AbstractTaskRequest implements TaskRequest {

   private boolean mSafeMode;
   private int[] mMappedModelIds;


   public ImportGlobalMappedModel2TaskRequest(boolean safeMode, int[] mappedModelIds) {
      this.mSafeMode = safeMode;
      this.mMappedModelIds = mappedModelIds;
      this.addExclusiveAccess("ImportGlobalMappedModel2");
   }

   public int[] getMappedModelIds() {
      return this.mMappedModelIds;
   }

   public boolean isSafeMode() {
      return this.mSafeMode;
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("Import Global Mapped Model 2");
      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.importglobaltask2.ImportGlobalMappedModel2Task";
   }

   public String getIdentifier() {
      return "ImportGlobalModel2";
   }
}
