// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.Arrays;
import java.util.List;

class ReportUpdateTask$ReportUpdateCheckpoint extends AbstractTaskCheckpoint {

   public List toDisplay() {
      return Arrays.asList(new Object[]{"Report Update - checkpoint number:" + this.getCheckpointNumber()});
   }
}
