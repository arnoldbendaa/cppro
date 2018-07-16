// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.InterpreterException;
import com.cedar.cp.util.xmlform.Column;
import java.io.Serializable;

public class FormulaParseException extends RuntimeException implements Serializable {

   public FormulaParseException(InterpreterException ie) {
      super("Unable to process formula", ie);
   }

   public FormulaParseException(InterpreterException ie, Column column) {
      super("Unable to process formula for column <" + column.getId() + "> formula <" + column.getFormula() + ">", ie);
   }
}
