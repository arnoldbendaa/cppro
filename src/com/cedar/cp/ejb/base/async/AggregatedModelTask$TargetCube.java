// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.ejb.base.async.AggregatedModelTask$SourceCube;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class AggregatedModelTask$TargetCube implements Serializable {

   private Integer mTargetCubeId;
   private String mTargetCubeVisId;
   private List<AggregatedModelTask$SourceCube> mSourceCubes = new ArrayList();
   private int mSourceCubeIndex = 0;


   public AggregatedModelTask$TargetCube(Integer targetCubeId, String targetCubeVisId) {
      this.mTargetCubeId = targetCubeId;
      this.mTargetCubeVisId = targetCubeVisId;
   }

   public void addSourceCube(AggregatedModelTask$SourceCube sc) {
      this.mSourceCubes.add(sc);
   }

   public Integer getTargetCubeId() {
      return this.mTargetCubeId;
   }

   public String getTargetCubeVisId() {
      return this.mTargetCubeVisId;
   }

   public void increaseSourceCubeIndex() {
      ++this.mSourceCubeIndex;
   }

   public AggregatedModelTask$SourceCube getNextSourceCube() {
      return this.mSourceCubeIndex >= this.mSourceCubes.size()?null:(AggregatedModelTask$SourceCube)this.mSourceCubes.get(this.mSourceCubeIndex);
   }

   public List<AggregatedModelTask$SourceCube> getSourceCubes() {
      return this.mSourceCubes;
   }

   public boolean noSourcesRemaining() {
      return this.mSourceCubeIndex >= this.mSourceCubes.size();
   }
}
