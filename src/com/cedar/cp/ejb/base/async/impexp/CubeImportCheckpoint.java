// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.impexp;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.ArrayList;
import java.util.List;

public class CubeImportCheckpoint extends AbstractTaskCheckpoint {

   private int mCurrentRowNo = 1;


   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("Checkpoint number:" + this.getCheckpointNumber() + " row number:" + this.mCurrentRowNo);
      return l;
   }

   public int getCurrentRowNo() {
      return this.mCurrentRowNo;
   }

   public void setCurrentRowNo(int currentRowNo) {
      this.mCurrentRowNo = currentRowNo;
   }
}
