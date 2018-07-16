// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import com.cedar.cp.dto.model.cc.CcDeploymentCK;
import java.util.ArrayList;
import java.util.List;

public class CellCalcRebuildTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private CcDeploymentCK mCcDeploymentCK;
   private String mCcDeploymentVisId;
   private String mCcDeploymentDescription;
   private boolean mLookupChanged;
   private boolean mContextChanged;
   private boolean mLinesChanged;
   private boolean mMappingsChanged;


   public CellCalcRebuildTaskRequest(int modelId, CcDeploymentCK deploymentCK, String deploymentVisId, String deploymentDescr) {
      this.addExclusiveAccess(String.valueOf(modelId));
      this.mCcDeploymentCK = deploymentCK;
      this.mCcDeploymentVisId = deploymentVisId;
      this.mCcDeploymentDescription = deploymentDescr;
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("CcDeployment=" + this.getCcDeploymentVisId());
      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.CellCalcRebuildTask";
   }

   public CcDeploymentCK getCcDeploymentCK() {
      return this.mCcDeploymentCK;
   }

   public void setCellCalcId(CcDeploymentCK deploymentCK) {
      this.mCcDeploymentCK = deploymentCK;
   }

   public String getCcDeploymentVisId() {
      return this.mCcDeploymentVisId;
   }

   public void setCcDeploymentVisId(String ccDeploymentVisId) {
      this.mCcDeploymentVisId = ccDeploymentVisId;
   }

   public String getCcDeploymentDescription() {
      return this.mCcDeploymentDescription;
   }

   public void setCcDeploymentDescription(String ccDeploymentDescription) {
      this.mCcDeploymentDescription = ccDeploymentDescription;
   }

   public boolean isLookupChanged() {
      return this.mLookupChanged;
   }

   public void setLookupChanged(boolean lookupChanged) {
      this.mLookupChanged = lookupChanged;
   }

   public boolean isContextChanged() {
      return this.mContextChanged;
   }

   public void setContextChanged(boolean contextChanged) {
      this.mContextChanged = contextChanged;
   }

   public boolean isLinesChanged() {
      return this.mLinesChanged;
   }

   public void setLinesChanged(boolean linesChanged) {
      this.mLinesChanged = linesChanged;
   }

   public boolean isMappingsChanged() {
      return this.mMappingsChanged;
   }

   public void setMappingsChanged(boolean mappingsChanged) {
      this.mMappingsChanged = mappingsChanged;
   }
}
