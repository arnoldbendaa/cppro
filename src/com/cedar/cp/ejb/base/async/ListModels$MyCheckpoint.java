// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.ArrayList;
import java.util.List;

public class ListModels$MyCheckpoint extends AbstractTaskCheckpoint {

   private int mCheckpointNumber;
   private AllModelsELO mAllModels;
   private int mLastKey;


   public void setAllModels(AllModelsELO allModels) {
      this.mCheckpointNumber = 0;
      this.mAllModels = allModels;
   }

   public AllModelsELO getAllModels() {
      return this.mAllModels;
   }

   public void setLastKey(int p) {
      this.mLastKey = p;
   }

   public int getLastKey() {
      return this.mLastKey;
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("checkpoint=" + this.mCheckpointNumber + ", key = " + this.mLastKey);
      return l;
   }

   public int setCheckpointNumberUp() {
      ++this.mCheckpointNumber;
      return this.mCheckpointNumber;
   }

   public int getCheckpointNumber() {
      return this.mCheckpointNumber;
   }
}
