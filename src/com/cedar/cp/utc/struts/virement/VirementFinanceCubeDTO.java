// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import java.io.Serializable;

public class VirementFinanceCubeDTO implements Serializable {

   private int mModelId;
   private String mModelVisId;
   private int mFinanceCubeId;
   private String mFinanceCubeVisId;


   public VirementFinanceCubeDTO() {}

   public VirementFinanceCubeDTO(int modelId, String modelVisId, int financeCubeId, String financeCubeVisId) {
      this.mModelId = modelId;
      this.mModelVisId = modelVisId;
      this.mFinanceCubeId = financeCubeId;
      this.mFinanceCubeVisId = financeCubeVisId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public String getModelVisId() {
      return this.mModelVisId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public String getFinanceCubeVisId() {
      return this.mFinanceCubeVisId;
   }
}
