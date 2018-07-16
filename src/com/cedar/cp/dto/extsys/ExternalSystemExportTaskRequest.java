// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExternalSystemExportTaskRequest extends AbstractTaskRequest {

   private int mMappedModelId;
   private String mMappedModelVisId;
   private List<EntityRef> mFinanceCubes;


   public ExternalSystemExportTaskRequest(int mappedModelId, String mappedModelVisId, List<EntityRef> financeCubes) {
      this.mMappedModelId = mappedModelId;
      this.mMappedModelVisId = mappedModelVisId;
      this.mFinanceCubes = financeCubes;
   }

   public List toDisplay() {
      ArrayList lineText = new ArrayList();
      lineText.add("External system defintion export task for model: " + this.mMappedModelVisId + " finance cubes:");
      Iterator i$ = this.mFinanceCubes.iterator();

      while(i$.hasNext()) {
         EntityRef er = (EntityRef)i$.next();
         lineText.add(" " + er.getNarrative());
      }

      lineText.add(".");
      return lineText;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.extsys.ExternalSystemExportTask";
   }

   public int getMappedModelId() {
      return this.mMappedModelId;
   }

   public String getMappedModelVisId() {
      return this.mMappedModelVisId;
   }

   public List<EntityRef> getFinanceCubes() {
      return this.mFinanceCubes;
   }
}
