// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.admin.tidy;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.Arrays;
import java.util.List;

class TidyTask$TidyCheckpoint extends AbstractTaskCheckpoint {

   public List toDisplay() {
      return Arrays.asList(new String[]{"Tidy Task Checkpoint number:" + this.getCheckpointNumber()});
   }
}
