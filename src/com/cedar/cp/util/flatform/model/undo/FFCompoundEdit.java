// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.undo;

import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoableEdit;

public class FFCompoundEdit extends CompoundEdit {

   public String toString() {
      return this.edits.size() == 1?((UndoableEdit)this.edits.firstElement()).toString():"Multiple cell changes";
   }
   
   public UndoableEdit getLastEdit() {
	   return this.lastEdit();
   }
}
