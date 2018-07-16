// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cubeadmin;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.Arrays;
import java.util.List;

class CubeRebuildTask$CubeRebuildCheckpoint extends AbstractTaskCheckpoint {

   private List<EntityRef> mRebuildList;
   private int mRebuildCubeIndex;
   private int mRebuildStep;
   private String[] mStepName = new String[]{"check work tables", "create work tables", "rebuild", "drop work tables"};


   public void setRebuildList(List<EntityRef> l) {
      this.mRebuildList = l;
   }

   public void setRebuildCubeIndex(int n) {
      this.mRebuildCubeIndex = n;
      this.mRebuildStep = 0;
   }

   public void setRebuildStep(int n) {
      this.mRebuildStep = n;
   }

   public int getRebuildCubeIndex() {
      return this.mRebuildCubeIndex;
   }

   public int getRebuildStep() {
      return this.mRebuildStep;
   }

   public List toDisplay() {
      return Arrays.asList(new Object[]{this.mRebuildList.get(this.mRebuildCubeIndex) + " - " + this.mStepName[this.mRebuildStep]});
   }
}
