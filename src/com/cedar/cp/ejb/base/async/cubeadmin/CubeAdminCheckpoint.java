// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cubeadmin;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.ArrayList;
import java.util.List;

public class CubeAdminCheckpoint extends AbstractTaskCheckpoint {

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("Cube Admin Step Number[" + this.getCheckpointNumber() + "]");
      return l;
   }
}
