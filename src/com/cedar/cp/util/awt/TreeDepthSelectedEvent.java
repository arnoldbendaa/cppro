// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;

public class TreeDepthSelectedEvent extends AWTEvent {

   int depth;
   long when;
   int modifiers;


   public TreeDepthSelectedEvent(Object source, int id, int depth) {
      this(source, id, depth, 0);
   }

   public TreeDepthSelectedEvent(Object source, int id, int depth, int modifiers) {
      this(source, id, depth, 0L, modifiers);
   }

   public TreeDepthSelectedEvent(Object source, int id, int depth, long when, int modifiers) {
      super(source, id);
      this.depth = depth;
      this.when = when;
      this.modifiers = modifiers;
   }

   public int getDepth() {
      return this.depth;
   }

   public long getWhen() {
      return this.when;
   }

   public int getModifiers() {
      return this.modifiers;
   }

   public String paramString() {
      return "TREE_DEPTH_SELECTED,depth=" + this.depth + ",when=" + this.when + ",modifiers=" + KeyEvent.getKeyModifiersText(this.modifiers);
   }
}
