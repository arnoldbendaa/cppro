// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.model.cc.RuntimeCubeDeploymentLine;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RuntimeCubeDeployment implements Serializable {

   private int mModelId;
   private int mFinanceCubeId;
   private int mOwnerId;
   private int mOwnerType;
   private Map<Integer, RuntimeCubeDeploymentLine> mDeploymentLines;
   public static final int sOWNER_TYPE_CELL_CALC = 0;
   public static final int sOWNER_TYPE_CUBE_FORMULA = 1;


   public RuntimeCubeDeployment(int modelId, int financeCubeId, int ownerId, int ownerType) {
      this.mModelId = modelId;
      this.mFinanceCubeId = financeCubeId;
      this.mOwnerId = ownerId;
      this.mOwnerType = ownerType;
      this.mDeploymentLines = new HashMap();
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getOwnerId() {
      return this.mOwnerId;
   }

   public int getOwnerType() {
      return this.mOwnerType;
   }

   public Map<Integer, RuntimeCubeDeploymentLine> getDeploymentLines() {
      return this.mDeploymentLines;
   }
}
