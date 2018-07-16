// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cm;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class ChangeManagementTaskRequest extends AbstractTaskRequest {

   private ModelRef mModelRef;
   private int mNumDimensions;
   private List mRequests;


   public ChangeManagementTaskRequest(ModelRef modelRef, int dimensionCount, List requests) {
      this.mModelRef = modelRef;
      this.addExclusiveAccess(modelRef.getPrimaryKey().toString());
      this.mNumDimensions = dimensionCount;
      this.mRequests = requests;
   }

   public List toDisplay() {
      ArrayList list = new ArrayList();
      list.add("ModelRef=" + this.mModelRef);
      return list;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.cm.ChangeManagementTask";
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public void setModelRef(ModelRef model) {
      this.mModelRef = model;
   }

   public int getNumDimensions() {
      return this.mNumDimensions;
   }

   public void setNumDimensions(int numDimensions) {
      this.mNumDimensions = numDimensions;
   }

   public List getRequests() {
      return this.mRequests;
   }
}
