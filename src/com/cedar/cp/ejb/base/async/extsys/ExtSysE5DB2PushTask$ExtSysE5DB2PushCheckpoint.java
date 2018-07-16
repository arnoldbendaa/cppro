// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.extsys;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.ArrayList;
import java.util.List;

public class ExtSysE5DB2PushTask$ExtSysE5DB2PushCheckpoint extends AbstractTaskCheckpoint {

   private List<FinanceCubeRef> mCubeRefs;


   public List toDisplay() {
      ArrayList l = new ArrayList();
      if(this.getCheckpointNumber() > 0 && this.getCheckpointNumber() <= this.mCubeRefs.size()) {
         l.add(((FinanceCubeRef)this.mCubeRefs.get(this.getCheckpointNumber() - 1)).toString());
      }

      return l;
   }

   public void setFinanceCubes(List<FinanceCubeRef> cubeRefs) {
      this.mCubeRefs = cubeRefs;
   }
}
