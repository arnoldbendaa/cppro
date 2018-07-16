// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.virement;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.Arrays;
import java.util.List;

class VirementTask$VirementCheckpoint extends AbstractTaskCheckpoint {

   public List toDisplay() {
      return Arrays.asList(new String[]{"Budget Transfer Task Checkpoint number:" + this.getCheckpointNumber()});
   }
}
