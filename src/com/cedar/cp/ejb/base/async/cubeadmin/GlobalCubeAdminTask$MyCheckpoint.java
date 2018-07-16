// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cubeadmin;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.ArrayList;
import java.util.List;

public class GlobalCubeAdminTask$MyCheckpoint extends AbstractTaskCheckpoint {

   private int mAction;


   public GlobalCubeAdminTask$MyCheckpoint(int action) {
      this.mAction = action;
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      switch(this.mAction) {
      case 0:
      case 1:
         l.add(GlobalCubeAdminTask.mNames[this.getCheckpointNumber()]);
         break;
      case 2:
         l.add("Re-create finance cube views");
         break;
      case 3:
         l.add("Create cube formula runtime tables and packages");
         break;
      case 4:
         l.add(this.getCheckpointNumber() == 0?"Delete cube formula runtime data":"Drop cube formula runtime tables");
      }

      return l;
   }
}
