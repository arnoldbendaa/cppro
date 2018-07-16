// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.util.Interval;
import java.io.Serializable;

public class RuntimeCubeDeploymentEntry extends Interval implements Serializable {

   private int mDimSeq;
   private int mStructureId;
   private int mStartId;
   private int mEndId;


   public RuntimeCubeDeploymentEntry(int dimSeq, int structureId, int startId, int startPos, int endId, int endPos) {
      super(startPos, endPos);
      this.mDimSeq = dimSeq;
      this.mStructureId = structureId;
      this.mStartId = startId;
      this.mEndId = endId;
   }

   public int getDimSeq() {
      return this.mDimSeq;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public int getStartId() {
      return this.mStartId;
   }

   public int getEndId() {
      return this.mEndId;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(" dimSeq:").append(this.mDimSeq);
      sb.append(" structureId:").append(this.mStructureId).append(" ");
      sb.append(super.toString());
      return sb.toString();
   }
}
