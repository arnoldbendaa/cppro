// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.extsys;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.Arrays;
import java.util.List;

class ExternalSystemExportTask$ExternalSystemExportCheckpoint extends AbstractTaskCheckpoint {

   private int mCheckpointNumber;


   public int getCheckpointNumber() {
      return this.mCheckpointNumber;
   }

   public int setCheckpointNumberUp() {
      return ++this.mCheckpointNumber;
   }

   public List toDisplay() {
      return Arrays.asList(new String[]{"External System Export Task Checkpoint Number:" + this.mCheckpointNumber});
   }
}
