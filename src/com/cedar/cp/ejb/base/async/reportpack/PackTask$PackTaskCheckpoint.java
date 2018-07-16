// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.reportpack;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.ArrayList;
import java.util.List;

public class PackTask$PackTaskCheckpoint extends AbstractTaskCheckpoint {

   private boolean mGroup;


   public boolean isGroup() {
      return this.mGroup;
   }

   public void setGroup(boolean group) {
      this.mGroup = group;
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("group =" + this.isGroup());
      return l;
   }
}
