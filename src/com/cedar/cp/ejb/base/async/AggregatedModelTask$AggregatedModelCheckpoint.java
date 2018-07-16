// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import com.cedar.cp.ejb.base.async.AggregatedModelTask$TargetCube;
import java.util.ArrayList;
import java.util.List;

class AggregatedModelTask$AggregatedModelCheckpoint extends AbstractTaskCheckpoint {

   private List<AggregatedModelTask$TargetCube> mTargetCubes = new ArrayList();
   private int mTargetCubeIndex;
   private int mStoredProcedureStep;
   private String[] mStepName = new String[]{"check work tables", "create work tables", "create aggregation transactions", "transaction update", "drop work tables"};


   public void addTargetCube(AggregatedModelTask$TargetCube uow) {
      this.mTargetCubes.add(0, uow);
   }

   public void increaseTargetCubeIndex() {
      ++this.mTargetCubeIndex;
      this.mStoredProcedureStep = 0;
   }

   public void increaseStoredProcedureStep() {
      ++this.mStoredProcedureStep;
   }

   public int getTargetCubeIndex() {
      return this.mTargetCubeIndex;
   }

   public AggregatedModelTask$TargetCube getTargetCube() {
      return (AggregatedModelTask$TargetCube)this.mTargetCubes.get(this.getTargetCubeIndex());
   }

   public int getStoredProcedureStep() {
      return this.mStoredProcedureStep;
   }

   public List toDisplay() {
      ArrayList displayList = new ArrayList();
      displayList.add(this.getTargetCube().getTargetCubeVisId() + " - " + this.mStepName[this.getStoredProcedureStep()]);
      return displayList;
   }

   public boolean noTargetCubesRemaining() {
      return this.getTargetCubeIndex() >= this.mTargetCubes.size();
   }

   public boolean noStepsRemaining() {
      return this.getStoredProcedureStep() >= 5;
   }
}
