// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.undo;

import com.cedar.cp.util.flatform.model.undo.FFCompoundEdit;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoableEditSupport;

public class FFUndoableEditSupport extends UndoableEditSupport {

   public FFUndoableEditSupport(Object r) {
      super(r);
   }

   protected CompoundEdit createCompoundEdit() {
      return new FFCompoundEdit();
   }
}
