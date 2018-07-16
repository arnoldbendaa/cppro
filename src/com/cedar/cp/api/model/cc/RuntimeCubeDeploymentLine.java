// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.model.cc.RuntimeCubeDeployment;
import com.cedar.cp.api.model.cc.RuntimeCubeDeploymentEntry;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RuntimeCubeDeploymentLine implements Serializable {

   private RuntimeCubeDeployment mRuntimeCubeDeployment;
   private int mOwnerLineId;
   private List<RuntimeCubeDeploymentEntry> mDeploymentEntries;
   private String[] mDataTypes;


   public RuntimeCubeDeploymentLine(RuntimeCubeDeployment runtimeCubeDeployment, int ownerLineId, List<RuntimeCubeDeploymentEntry> deploymentEntries, String[] dataTypes) {
      this.mRuntimeCubeDeployment = runtimeCubeDeployment;
      this.mOwnerLineId = ownerLineId;
      this.mDeploymentEntries = deploymentEntries;
      this.mDataTypes = dataTypes;
   }

   public RuntimeCubeDeployment getRuntimeCubeDeployment() {
      return this.mRuntimeCubeDeployment;
   }

   public int getOwnerLineId() {
      return this.mOwnerLineId;
   }

   public List<RuntimeCubeDeploymentEntry> getDeploymentEntries() {
      return this.mDeploymentEntries;
   }

   public String[] getDataTypes() {
      return this.mDataTypes;
   }

   public void setDataTypes(String[] dataTypes) {
      this.mDataTypes = dataTypes;
   }

   public boolean equals(Object obj) {
      if(obj == this) {
         return true;
      } else if(obj instanceof RuntimeCubeDeploymentLine) {
         RuntimeCubeDeploymentLine other = (RuntimeCubeDeploymentLine)obj;
         return other.getOwnerLineId() != this.getOwnerLineId()?false:Arrays.equals(other.getDataTypes(), this.getDataTypes());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.mOwnerLineId;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("ownerLineId:").append(this.mOwnerLineId);
      Iterator i$ = this.mDeploymentEntries.iterator();

      while(i$.hasNext()) {
         RuntimeCubeDeploymentEntry entry = (RuntimeCubeDeploymentEntry)i$.next();
         sb.append(entry);
      }

      sb.append(" dt:").append(Arrays.toString(this.mDataTypes));
      return sb.toString();
   }
}
