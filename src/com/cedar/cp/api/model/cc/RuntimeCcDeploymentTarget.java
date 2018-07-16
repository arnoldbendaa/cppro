// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import java.io.Serializable;

public class RuntimeCcDeploymentTarget implements Serializable {

   private int mCcDeploymentId;
   private int mCcDeploymentLineId;


   public RuntimeCcDeploymentTarget() {}

   public RuntimeCcDeploymentTarget(int ccDeploymentId, int ccDeploymentLineId) {
      this.mCcDeploymentId = ccDeploymentId;
      this.mCcDeploymentLineId = ccDeploymentLineId;
   }

   public int getCcDeploymentId() {
      return this.mCcDeploymentId;
   }

   public void setCcDeploymentId(int ccDeploymentId) {
      this.mCcDeploymentId = ccDeploymentId;
   }

   public int getCcDeploymentLineId() {
      return this.mCcDeploymentLineId;
   }

   public void setCcDeploymentLineId(int ccDeploymentLineId) {
      this.mCcDeploymentLineId = ccDeploymentLineId;
   }

   public String toString() {
      return this.mCcDeploymentId + "," + this.mCcDeploymentLineId;
   }

   public boolean equals(Object obj) {
      if(!(obj instanceof RuntimeCcDeploymentTarget)) {
         return false;
      } else {
         RuntimeCcDeploymentTarget target = (RuntimeCcDeploymentTarget)obj;
         return this.mCcDeploymentId == target.mCcDeploymentId && this.mCcDeploymentLineId == target.mCcDeploymentLineId;
      }
   }

   public int hashCode() {
      return this.mCcDeploymentId + this.mCcDeploymentLineId;
   }
}
