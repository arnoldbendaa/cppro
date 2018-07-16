// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import javax.swing.tree.TreeNode;

final class CalendarElementNodeImpl1 implements Enumeration<TreeNode> {

   public boolean hasMoreElements() {
      return false;
   }

   public TreeNode nextElement() {
      throw new NoSuchElementException("No more elements");
   }
}
